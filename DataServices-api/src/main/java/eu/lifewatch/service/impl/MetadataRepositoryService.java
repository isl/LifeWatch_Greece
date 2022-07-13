package eu.lifewatch.service.impl;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import eu.lifewatch.common.ResourceType;
import static eu.lifewatch.common.ResourceType.LITERAL;
import eu.lifewatch.common.Resources;
import eu.lifewatch.core.api.RepositoryManager;
import eu.lifewatch.core.model.CommonNameStruct;
import eu.lifewatch.core.model.DirectoryStruct;
import eu.lifewatch.core.model.EnvironmentalStruct;
import eu.lifewatch.core.model.GensDatasetStruct;
import eu.lifewatch.core.model.GensSampleStruct;
import eu.lifewatch.core.model.IdentificationStruct;
import eu.lifewatch.core.model.IncomingNodeStruct;
import eu.lifewatch.core.model.MeasurementStruct;
import eu.lifewatch.core.model.MicroCTPostProcessingStruct;
import eu.lifewatch.core.model.MicroCTReconstructionStruct;
import eu.lifewatch.core.model.MicroCTScanningStruct;
import eu.lifewatch.core.model.MicroCTSpecimenStruct;
import eu.lifewatch.core.model.MorphometricsStruct;
import eu.lifewatch.core.model.OccurrenceStatsAbundanceStruct;
import eu.lifewatch.core.model.OccurrenceStatsTempStruct;
import eu.lifewatch.core.model.OccurrenceStruct;
import eu.lifewatch.core.model.OutgoingNodeStruct;
import eu.lifewatch.core.model.ScientificNamingStruct;
import eu.lifewatch.core.model.SpecimenCollectionStruct;
import eu.lifewatch.core.model.SpecimenStruct;
import eu.lifewatch.core.model.StatsStruct;
import eu.lifewatch.core.model.SynonymStruct;
import eu.lifewatch.core.model.TaxonomyStruct;
import eu.lifewatch.core.model.Triple;
import eu.lifewatch.exception.DataImportException;
import eu.lifewatch.exception.QueryExecutionException;
import eu.lifewatch.exception.URIValidationException;
import eu.lifewatch.service.api.Service;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import org.apache.log4j.Logger;
import org.openrdf.query.BindingSet;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.rio.RDFFormat;

/**
 * MetadataRepositoryService provides the methods to search, add, delete and
 * update information in a particular repository.
 *
 * @author Nikos Minadakis (minadakn 'at' ics 'dot' forth 'dot' gr)
 * @author Yannis Marketakis (marketak 'at' ics 'dot' forth 'dot' gr)
 */
public class MetadataRepositoryService implements Service {

    private final RepositoryManager repoManager;
    private final DirectoryService directoryService;
    private static final Logger logger = Logger.getLogger(MetadataRepositoryService.class);

    public MetadataRepositoryService(RepositoryManager repositoryManager) {
        this.repoManager = repositoryManager;
        this.directoryService=new DirectoryService(repositoryManager);
    }

    /**
     * Imports the the file found in the given file path in the repository under
     * the given graphspace.
     *
     * @param fileFullPath the path of the file that will be imported
     * @param directoryGraph the graphspace that will be used
     * @throws DataImportException is thrown if any error is occurred
     */
    @Override
    public void importFile(String fileFullPath, String directoryGraph) throws DataImportException {
        File file = new File(fileFullPath);
        if (!file.exists()) {
            throw new DataImportException("The given file (\"" + fileFullPath + "\") does not exist");
        }
        RDFFormat chosenFormat = RDFFormat.NTRIPLES;
        if (file.getAbsolutePath().toLowerCase().endsWith(Resources.rdfDefaultExtension)) {
            chosenFormat = RDFFormat.RDFXML;
            logger.debug("Recognized the format of the file (" + file.getName() + "): " + chosenFormat);
        } else if (file.getAbsolutePath().toLowerCase().endsWith(Resources.ntriplesDefaultExtension1)
                || file.getAbsolutePath().toLowerCase().endsWith(Resources.ntriplesDefaultExtension2)) {
            chosenFormat = RDFFormat.NTRIPLES;
            logger.debug("Recognized the format of the file (" + file.getName() + "): " + chosenFormat);
        } else {
            logger.debug("Couldn't recognize the format of the file (" + file.getName() + "): Trying with " + chosenFormat);
        }
        this.repoManager.importData(file, chosenFormat, directoryGraph);
    }

    /**
     * Imports the files found in the given directory in the repository under
     * the given graphspace. It automatically recognizes the format of the files
     * (by inspecting its file extension) and imports them in the repository.
     * Currently only formats expressed in RDF and NTriples format are
     * supported. The method searches for files under the given folder (it does
     * not recursively search its subdirectories).
     *
     * @param directoryGraph the graphspace that will be used for storing the
     * contents of the files
     * @param rootDirPath the path of the directory containing the files that
     * will be imported
     * @throws DataImportException is thrown if any error occurs during
     * importing
     */
    @Override
    public void importFiles(String rootDirPath, String directoryGraph) throws DataImportException {
        File directory = new File(rootDirPath);
        if (!directory.exists()) {
            throw new DataImportException("The given folder (\"" + rootDirPath + "\") does not exist");
        } else if (!directory.isDirectory()) {
            throw new DataImportException("The given path (\"" + rootDirPath + "\") does not refer to a directory");
        }
        File[] directoryListing = directory.listFiles();
        List<File> filesInRDF = new ArrayList<>();
        List<File> filesInNtriples = new ArrayList<>();
        for (File file : directoryListing) {
            if (file.isFile() && (file.getAbsolutePath().toLowerCase().endsWith(Resources.ntriplesDefaultExtension1)
                    || file.getAbsolutePath().toLowerCase().endsWith(Resources.ntriplesDefaultExtension2))) {
                filesInNtriples.add(file);
            } else if (file.isFile() && file.getAbsolutePath().toLowerCase().endsWith(Resources.rdfDefaultExtension)) {
                filesInRDF.add(file);
            }
        }
        if (!filesInRDF.isEmpty()) {
            logger.debug("Importing " + filesInRDF + " files in " + RDFFormat.RDFXML + " format");
            this.repoManager.importData(filesInRDF, RDFFormat.RDFXML, directoryGraph);
        }
        if (!filesInNtriples.isEmpty()) {
            logger.debug("Importing " + filesInNtriples + " files in " + RDFFormat.NTRIPLES + " format");
            this.repoManager.importData(filesInNtriples, RDFFormat.NTRIPLES, directoryGraph);
        }
    }
    
    public List<OccurrenceStruct> searchOccurrence(String speciesName, String place, String date, String datasetURI, int offset, int limit, String repositoryGraph) throws QueryExecutionException {
        logger.info("Request for occurrence search with parameters "
                   +"speciesName: ["+speciesName+"], "
                   +"place(locality OR country OR waterarea): ["+place+"], "
                   +"date: ["+date+"], "
                   +"datasetURI: ["+datasetURI+"], "
                   +"limit: ["+(limit<0?"N/A":String.valueOf(limit))+"], "
                   +"offset: ["+(offset<0?"N/A":String.valueOf(offset))+"], "
                   +"reposytoryGraph: ["+repositoryGraph+"], ");
        String queryString="SELECT DISTINCT ?occurrenceEventURI ?datasetURI ?datasetName ?speciesURI ?speciesName ?individualURI ?individualLabel ?occurrenceEventLabel "
                                          +"?localityURI ?localityName ?countryURI ?countryName ?waterAreaURI ?waterAreaName "
                                          +"?habitatURI ?habitatName ?equipmentURI ?equipmentName ?actorURI ?actorName ?description "
                                          +"?timespan ?stationURI ?samplingProtocol ?samplingProtocolURI ?bibliographicCitation ?bibliographicCitationURI "
                                          +"?coordinates ?stationNotes ?ecosystemURI ?ecosystemName ?max_depth ?min_depth "
                +"FROM<"+repositoryGraph+"> "
                +"WHERE{ "
                +"?occurrenceEventURI <"+Resources.rdfTypeLabel+"> <"+Resources.encounterEventLabel+">. "
                +"?occurrenceEventURI <"+Resources.hasFoundAt+"> ?stationURI. "
                +"?occurrenceEventURI <"+Resources.rdfsLabel+"> ?occurrenceEventLabel. "
                +"?occurrenceEventURI <"+Resources.hasTimespan+"> ?timespan. "
                +"?occurrenceEventURI <"+Resources.carriedOutBy+"> ?actorURI. "
                +"?actorURI <"+Resources.rdfsLabel+"> ?actorName. "
                +"?occurrenceEventURI <"+Resources.hasFoundObject+"> ?individualURI. "
                +"?individualURI <"+Resources.rdfsLabel+"> ?individualLabel. "
                +"?individualURI <"+Resources.isComposedOf+"> ?tempAggregateUri. "
                +"?tempAggregateUri <"+Resources.belongsTo+"> ?speciesURI. "
                +"?speciesURI <"+Resources.rdfsLabel+"> ?speciesName. "
                +"?datasetURI <"+Resources.rdfTypeLabel+"> <"+Resources.datasetLabel+">. "
                +"?datasetURI <"+Resources.refersTo+"> ?occurrenceEventURI. "
                +"?datasetURI <"+Resources.rdfsLabel+"> ?datasetName. "
                +"OPTIONAL { "
                    +"?occurrenceEventURI <"+Resources.consistsOf+"> ?measurement_uri. "
                    +"?measurement_uri <"+Resources.rdfTypeLabel+"> <"+Resources.measurementEventLabel+">. "
                    +"?measurement_uri <"+Resources.observedDimension+"> ?min_dimension_uri. "
                    +"?min_dimension_uri <"+Resources.rdfTypeLabel+"> <"+Resources.dimensionLabel+">. "
                    +"?min_dimension_uri <"+Resources.rdfsLabel+"> ?min_depth. "
                    +"?min_dimension_uri <"+Resources.hasType+"> ?min_type_uri. "
                    +"?min_type_uri <"+Resources.rdfsLabel+"> \"minimum depth\". "
                +"} "
                +"OPTIONAL { "
                    +"?occurrenceEventURI <"+Resources.consistsOf+"> ?measurement_uri. "
                    +"?measurement_uri <"+Resources.rdfTypeLabel+"> <"+Resources.measurementEventLabel+">. "
                    +"?measurement_uri <"+Resources.observedDimension+"> ?max_dimension_uri. "
                    +"?max_dimension_uri <"+Resources.rdfTypeLabel+"> <"+Resources.dimensionLabel+">. "
                    +"?max_dimension_uri <"+Resources.rdfsLabel+"> ?max_depth. "
                    +"?max_dimension_uri <"+Resources.hasType+"> ?max_type_uri. "
                    +"?max_type_uri <"+Resources.rdfsLabel+"> \"maximum depth\". "
                +"} "
                +"OPTIONAL{"
                    +"?stationURI <"+Resources.hasNote+"> ?stationNotes. "
                +"} "
                +"OPTIONAL{ "
                    +"?stationURI <"+Resources.isIdentifiedBy+"> ?coordinates. "
                +"} "
                +"OPTIONAL{ "
                    +"?occurrenceEventURI <"+Resources.hasNote+"> ?description. "
                +"} "
                +"OPTIONAL{ "
                    +"?occurrenceEventURI <"+Resources.usedObjectOfType+"> ?equipmentURI. "
                    +"?equipmentURI <"+Resources.rdfsLabel+"> ?equipmentName. "
                +"} "
                +"OPTIONAL{ "
                    +"?occurrenceEventURI <"+Resources.usedSpecificTechnique+"> ?samplingProtocolURI. "
                    +"?samplingProtocolURI <"+Resources.rdfsLabel+"> ?samplingProtocol. "
                +"} "
                +"OPTIONAL{ "
                    +"?occurrenceEventURI <"+Resources.isReferredToBy+"> ?bibliographicCitationURI. "
                    +"?bibliographicCitationURI <"+Resources.rdfTypeLabel+"> <"+Resources.conceptualObjectLabel+">. "
                    +"?bibliographicCitationURI <" + Resources.rdfsLabel + "> ?bibliographicCitation. "
                +"} " 
                +"OPTIONAL{ "
                    +"?stationURI <"+Resources.hasType+"> ?habitatURI. "
                    +"?habitatURI <"+Resources.rdfTypeLabel+"> <"+Resources.ecosystemTypeLabel+">. "
                    +"?habitatURI <"+Resources.rdfsLabel+"> ?habitatName. "
                +"} "
                +"OPTIONAL{ "
                    +"?stationURI <"+Resources.fallsWithin+"> ?localityURI. "
                    +"?localityURI <"+Resources.rdfTypeLabel+"> <"+Resources.ecosystemEnvironmentLabel+">. "
                    +"?localityURI <"+Resources.rdfsLabel+"> ?localityName. "
                +"} "
                +"OPTIONAL{ "
                    +"?stationURI <"+Resources.fallsWithin+"> ?countryURI. "
                    +"?countryURI <"+Resources.rdfTypeLabel+"> <"+Resources.countryLabel+">. "
                    +"?countryURI <"+Resources.rdfsLabel+"> ?countryName. "
                +"} "
                +"OPTIONAL{ "
                    +"?stationURI <"+Resources.fallsWithin+"> ?ecosystemURI. "
                    +"?ecosystemURI <"+Resources.rdfTypeLabel+"> <"+Resources.ecosystemLabel+">. "
                    +"?ecosystemURI <" + Resources.rdfsLabel+ "> ?ecosystemName. "
                +"} "
                +"OPTIONAL{ "
                    +"?stationURI <"+Resources.fallsWithin+"> ?waterAreaURI. "
                    +"?waterAreaURI <"+Resources.rdfTypeLabel+"> <"+Resources.waterAreaLabel+">. "
                    +"?waterAreaURI <"+Resources.rdfsLabel+"> ?waterAreaName. "
                +"} ";
        if(date!=null && !date.isEmpty()){
            queryString+="FILTER CONTAINS(LCASE(?timespan),\""+date.toLowerCase()+"\"). ";
        }
        if(speciesName!=null && !speciesName.isEmpty()){
            queryString+="FILTER CONTAINS(LCASE(?speciesName),\""+speciesName.toLowerCase()+"\"). ";
        }
        if(place!=null && !place.isEmpty()){
            queryString+="FILTER (CONTAINS(LCASE(?localityName),\""+place.toLowerCase()+"\") ";
            queryString+="|| CONTAINS(LCASE(?countryName),\""+place.toLowerCase()+"\") ";
            queryString+="|| CONTAINS(LCASE(?waterAreaName),\""+place.toLowerCase()+"\") ). ";
        }
        if(datasetURI!=null && !datasetURI.isEmpty()){
            queryString+="FILTER CONTAINS(LCASE(STR(?datasetURI)),\""+datasetURI.toLowerCase()+"\"). ";
        }
        queryString+="} ";
        if(limit>0 && offset>=0){
            queryString+="LIMIT "+limit+" "
                        +"OFFSET "+offset;
        }
        logger.debug("Submitting the query: \"" + queryString + "\"");
        List<BindingSet> sparqlresults = this.repoManager.query(queryString);
        logger.debug("The SPARQL query returned " + sparqlresults.size() + " results (RAW SPARQL results)");
        Map<String, OccurrenceStruct> map = new HashMap<>();
        for (BindingSet result : sparqlresults) {
            if (!map.containsKey(result.getValue("occurrenceEventURI").stringValue())) {
                OccurrenceStruct struct = new OccurrenceStruct()
                        .withOccurrenceEventURI(result.getValue("occurrenceEventURI").stringValue())
                        .withOccurrenceEvent(result.getValue("occurrenceEventLabel").stringValue())
                        .withDatasetURI(result.getValue("datasetURI").stringValue())
                        .withDatasetTitle(result.getValue("datasetName").stringValue())
                        .withLocalityURI(result.getValue("localityURI").stringValue())
                        .withLocalityName(result.getValue("localityName").stringValue())
                        .withActor(result.getValue("actorURI").stringValue(), result.getValue("actorName").stringValue())
                        .withSpeciesURI(result.getValue("speciesURI").stringValue())
                        .withSpeciesName(result.getValue("speciesName").stringValue())
                        .withIndividualURI(result.getValue("individualURI").stringValue())
                        .withIndividualLabel(result.getValue("individualLabel").stringValue())
                        .withStationURI(result.getValue("stationURI").stringValue());
                if (result.getValue("description") != null) {
                    struct.withDescription(result.getValue("description").stringValue());
                }
                if (result.getValue("waterAreaURI") != null) {
                    struct.withWaterAreaURI(result.getValue("waterAreaURI").stringValue());
                }
                if (result.getValue("waterAreaName") != null) {
                    struct.withWaterAreaName(result.getValue("waterAreaName").stringValue());
                }
                if (result.getValue("countryURI") != null) {
                    struct.withCountryURI(result.getValue("countryURI").stringValue());
                }
                if (result.getValue("countryName") != null) {
                    struct.withCountryName(result.getValue("countryName").stringValue());
                }
                if (result.getValue("habitatURI") != null) {
                    struct.withHabitatURI(result.getValue("habitatURI").stringValue());
                }
                if (result.getValue("habitatName") != null) {
                    struct.withHabitatName(result.getValue("habitatName").stringValue());
                }
                if (result.getValue("ecosystemURI") != null) {
                    struct.withEcosystemURI(result.getValue("ecosystemURI").stringValue());
                }
                if (result.getValue("ecosystemName") != null) {
                    struct.withEcosystemName(result.getValue("ecosystemName").stringValue());
                }
                if (result.getValue("equipmentName") != null) {
                    struct.withEquipmentTypeName(result.getValue("equipmentName").stringValue());
                }
                if (result.getValue("equipmentURI") != null) {
                    struct.withEquipmentTypeURI(result.getValue("equipmentURI").stringValue());
                }
                if (result.getValue("timespan") != null) {
                    struct.withTimeSpan(result.getValue("timespan").stringValue());
                }

                if (result.getValue("bibliographicCitationURI") != null) {
                    struct.withBibliographicCitationURI(result.getValue("bibliographicCitationURI").stringValue());
                }
                if (result.getValue("bibliographicCitation") != null) {
                    struct.withBibliographicCitation(result.getValue("bibliographicCitation").stringValue());
                }

                if (result.getValue("samplingProtocolURI") != null) {
                    struct.withSamplingProtocolURI(result.getValue("samplingProtocolURI").stringValue());
                }
                if (result.getValue("samplingProtocol") != null) {
                    struct.withSamplingProtocol(result.getValue("samplingProtocol").stringValue());
                }
                if (result.getValue("stationNotes") != null) {
                    struct.withStationNotes(result.getValue("stationNotes").stringValue());
                }
                if (result.getValue("coordinates") != null) {
                    struct.withCoordinates(result.getValue("coordinates").stringValue());
                }
                if (result.getValue("min_depth") != null) {
                    struct.withMinimumDepth(result.getValue("min_depth").stringValue());
                }
                if (result.getValue("max_depth") != null) {
                    struct.withMaximumDepth(result.getValue("max_depth").stringValue());
                }
                map.put(struct.getOccurrenceEventURI(), struct);
            } else {
                OccurrenceStruct struct = map.get(result.getValue("occurrenceEventURI").stringValue());
                struct.withActor(result.getValue("actorURI").stringValue(), result.getValue("actorName").stringValue());
                map.put(struct.getOccurrenceEventURI(), struct);
            }
        }
        logger.debug("The query returned "+map.values().size()+" occurrence objects");
        return new ArrayList<>(map.values());
    }
    
    public List<DirectoryStruct> searchOccurrenceDatasets(String speciesName, String place, String date, String datasetURI, int offset, int limit, String repositoryGraph, String directoryGraph) throws QueryExecutionException {
        logger.info("Request for occurrence search with parameters "
                   +"speciesName: ["+speciesName+"], "
                   +"place(locality OR country OR waterarea): ["+place+"], "
                   +"date: ["+date+"], "
                   +"datasetURI: ["+datasetURI+"], "
                   +"limit: ["+(limit<0?"N/A":String.valueOf(limit))+"], "
                   +"offset: ["+(offset<0?"N/A":String.valueOf(offset))+"], "
                   +"reposytoryGraph: ["+repositoryGraph+"], ");
        String queryString="SELECT DISTINCT ?dataset_uri  "
                +"FROM <"+repositoryGraph+"> "
                +"WHERE{ "
                +"?dataset_uri <"+Resources.refersTo+"> ?occurrence_event_uri. "
                +"?occurrence_event_uri <"+Resources.rdfTypeLabel+"> <"+Resources.encounterEventLabel+">. "
                +"?occurrence_event_uri <"+Resources.hasFoundObject+"> ?biotic_element_uri. "
                +"?biotic_element_uri <"+Resources.rdfTypeLabel+"> <"+Resources.bioticElementLabel+">. "
                +"?biotic_element_uri <"+Resources.belongsTo+"> ?species_uri."
                +"?species_uri <"+Resources.rdfTypeLabel+"> <"+Resources.speciesLabel+">. "
                +"?species_uri <"+Resources.rdfsLabel+"> ?species_name. "
                
                +"?occurrence_event_uri <"+Resources.hasFoundAt+"> ?location_uri. "
                +"?location_uri <"+Resources.rdfsLabel+"> ?location. "
                
                +"?occurrence_event_uri <"+Resources.hasTimespan+"> ?timespan_uri. "
                +"?timespan_uri <"+Resources.rdfTypeLabel+"> <"+Resources.timespanLabel+">. "
                +"?timespan_uri <"+Resources.rdfsLabel+"> ?timespan. ";
                
        if(date!=null && !date.isEmpty()){
            queryString+="FILTER CONTAINS(LCASE(?timespan),\""+date.toLowerCase()+"\"). ";
        }
        if(speciesName!=null && !speciesName.isEmpty()){
            queryString+="FILTER CONTAINS(LCASE(?species_name),\""+speciesName.toLowerCase()+"\"). ";
        }
        if(place!=null && !place.isEmpty()){
            queryString+="FILTER CONTAINS(LCASE(?location),\""+place.toLowerCase()+"\") ";
        }
        if(datasetURI!=null && !datasetURI.isEmpty()){
            queryString+="FILTER CONTAINS(LCASE(STR(?datasetURI)),\""+datasetURI.toLowerCase()+"\"). ";
        }
        queryString+="} ";
        
        logger.debug("Submitting the query: \"" + queryString + "\"");
        List<BindingSet> sparqlresults = this.repoManager.query(queryString);
        logger.debug("The SPARQL query returned " + sparqlresults.size() + " results (RAW SPARQL results)");
        List<String> datasetUris=new ArrayList<>();
        for (BindingSet result : sparqlresults) {
            datasetUris.add(result.getValue("dataset_uri").stringValue());
        }
        if(datasetUris.isEmpty()){
            logger.debug("No dataset URIs were found");
            return new ArrayList<>();
        }else if(datasetUris.size()<offset){
            logger.debug("No more dataset URIs were found");
            return new ArrayList<>();
        }else{
            if(offset>=0 && limit>0){
		if(datasetUris.size()>(offset+limit)){
			logger.debug("Retrieve information for dataset URIs with OFFSET/LIMIT "+offset+"/"+limit);
			datasetUris=datasetUris.subList(offset, offset+limit);
		}else{
			logger.debug("Retrieve information for dataset URIs with OFFSET/LIMIT "+offset+"/"+datasetUris.size());
			datasetUris=datasetUris.subList(offset, datasetUris.size());
		}
            }
        }
        Map<String,DirectoryStruct> datasetsMap=this.directoryService.searchDatasets(datasetUris, directoryGraph);
        return new ArrayList<>(datasetsMap.values());
    }

    public List<OccurrenceStatsTempStruct> searchOccurenceStatsTemp(String speciesName, String place, String date, String numberOfParts, String datasetURI, int offset, int limit, String repositoryGraph) throws QueryExecutionException {
        logger.info("Request for occurrenceStatsTemp search with parameters "
                   +"speciesName: ["+speciesName+"], "
                   +"place(locality OR country OR waterarea): ["+place+"], "
                   +"date: ["+date+"], "
                   +"numberOfParts: ["+numberOfParts+"], "
                   +"datasetURI: ["+datasetURI+"], "
                   +"limit: ["+(limit<0?"N/A":String.valueOf(limit))+"], "
                   +"offset: ["+(offset<0?"N/A":String.valueOf(offset))+"], "
                   +"reposytoryGraph: ["+repositoryGraph+"], ");
        String queryString="SELECT DISTINCT ?occurrenceEventURI ?occurrenceEventLabel ?datasetURI ?datasetName "
                                          +"?speciesURI ?speciesName ?individualURI ?individualLabel "
                                          +"?localityURI ?localityName ?countryURI ?countryName ?waterAreaURI ?waterAreaName "
                                          +"?habitatURI ?habitatName ?equipmentURI ?equipmentName ?actorURI ?actorName ?description "
                                          +"?timespan ?stationURI ?samplingProtocol ?samplingProtocolURI ?bibliographicCitation ?bibliographicCitationURI "
                                          +"?stationNotes ?ecosystemURI ?ecosystemName ?tempURI ?tempName ?numberOfParts ?min_depth ?max_depth "
                +"FROM <"+repositoryGraph+"> "
                +"WHERE{ "
                +"?occurrenceEventURI <"+Resources.rdfTypeLabel+"> <"+Resources.encounterEventLabel+">. "
                +"?occurrenceEventURI <"+Resources.hasFoundAt+"> ?stationURI. "
                +"?occurrenceEventURI <"+Resources.rdfsLabel+"> ?occurrenceEventLabel. "
                +"?occurrenceEventURI <"+Resources.hasTimespan+"> ?timespan. "
                +"?occurrenceEventURI <"+Resources.carriedOutBy+"> ?actorURI. "
                +"?actorURI <"+Resources.rdfsLabel+"> ?actorName. "
                +"?occurrenceEventURI <"+Resources.hasFoundObject+"> ?individualURI. "
                +"?individualURI <"+Resources.rdfTypeLabel+"> <"+Resources.physicalObjectLabel+">. "
                +"?individualURI <"+Resources.rdfsLabel+"> ?individualLabel. "
                +"?individualURI <"+Resources.isComposedOf+"> ?tempURI. "
                +"?tempURI <"+Resources.belongsTo+"> ?speciesURI. "
                +"?tempURI <"+Resources.rdfsLabel+"> ?tempName. "
                +"?speciesURI <"+Resources.rdfsLabel+"> ?speciesName. "
                +"?datasetURI <"+Resources.rdfTypeLabel+"> <"+Resources.datasetLabel+">. "
                +"?datasetURI <"+Resources.refersTo+"> ?occurrenceEventURI. "
                +"?datasetURI <"+Resources.rdfsLabel+"> ?datasetName. "
                +"OPTIONAL { "
                    +"?occurrenceEventURI <"+Resources.consistsOf+"> ?measurement_uri. "
                    +"?measurement_uri <"+Resources.rdfTypeLabel+"> <"+Resources.measurementEventLabel+">. "
                    +"?measurement_uri <"+Resources.observedDimension+"> ?min_dimension_uri. "
                    +"?min_dimension_uri <"+Resources.rdfTypeLabel+"> <"+Resources.dimensionLabel+">. "
                    +"?min_dimension_uri <"+Resources.rdfsLabel+"> ?min_depth. "
                    +"?min_dimension_uri <"+Resources.hasType+"> ?min_type_uri. "
                    +"?min_type_uri <"+Resources.rdfsLabel+"> \"minimum depth\". "
                +"} "
                +"OPTIONAL { "
                    +"?occurrenceEventURI <"+Resources.consistsOf+"> ?measurement_uri. "
                    +"?measurement_uri <"+Resources.rdfTypeLabel+"> <"+Resources.measurementEventLabel+">. "
                    +"?measurement_uri <"+Resources.observedDimension+"> ?max_dimension_uri. "
                    +"?max_dimension_uri <"+Resources.rdfTypeLabel+"> <"+Resources.dimensionLabel+">. "
                    +"?max_dimension_uri <"+Resources.rdfsLabel+"> ?max_depth. "
                    +"?max_dimension_uri <"+Resources.hasType+"> ?max_type_uri. "
                    +"?max_type_uri <"+Resources.rdfsLabel+"> \"maximum depth\". "
                +"} "
                +"OPTIONAL { "
                    +"?stationURI <"+Resources.hasNote+"> ?stationNotes. "
                +"} "
                +"OPTIONAL { "
                    +"?occurrenceEventURI <"+Resources.hasNote+"> ?description. "
                +"} "
                +"OPTIONAL { "
                    +"?tempURI <"+Resources.hasNumberOfParts+"> ?numberOfParts. "
                +"} "
                +"OPTIONAL { "
                    +"?occurrenceEventURI <"+Resources.usedObjectOfType+"> ?equipmentURI. "
                    +"?equipmentURI <"+Resources.rdfsLabel+"> ?equipmentName. "
                +"} "
                +"OPTIONAL { "
                    +"?occurrenceEventURI <"+Resources.usedSpecificTechnique+"> ?samplingProtocolURI. "
                    +"?samplingProtocolURI <"+Resources.rdfsLabel+"> ?samplingProtocol. "
                +"} "
                +"OPTIONAL { "
                    +"?occurrenceEventURI <"+Resources.isReferredToBy+"> ?bibliographicCitationURI. "
                    +"?bibliographicCitationURI <"+Resources.rdfTypeLabel+"> <"+Resources.conceptualObjectLabel+">. "
                    +"?bibliographicCitationURI <"+Resources.rdfsLabel+"> ?bibliographicCitation. "
                +"} "
                +"OPTIONAL { "
                    +"?stationURI <" + Resources.fallsWithin + "> ?localityURI. "
                    +"?localityURI <"+Resources.rdfTypeLabel+"> <"+Resources.ecosystemEnvironmentLabel+">. "
                    +"?localityURI <" + Resources.rdfsLabel + "> ?localityName. "
                +"} "
                +"OPTIONAL { "
                    +"?stationURI <" + Resources.fallsWithin + "> ?localityURI. "
                    +"?localityURI <"+Resources.rdfTypeLabel+"> <"+Resources.ecosystemEnvironmentLabel+">. "
                    +"?localityURI <"+Resources.hasType+"> ?habitatURI. "
                    +"?habitatURI <"+Resources.rdfTypeLabel+"> <"+Resources.ecosystemTypeLabel+">. "
                    +"?habitatURI <"+Resources.rdfsLabel+"> ?habitatName. "
                +"} "
                +"OPTIONAL { "
                    +"?stationURI <"+Resources.fallsWithin+"> ?countryURI. "
                    +"?countryURI <"+Resources.rdfTypeLabel+"> <"+Resources.countryLabel+">. "
                    +"?countryURI <"+Resources.rdfsLabel+"> ?countryName. "
                +"} "
                +"OPTIONAL { "
                    +"?stationURI <"+Resources.fallsWithin+"> ?ecosystemURI. "
                    +"?ecosystemURI <"+Resources.rdfTypeLabel+"> <"+Resources.ecosystemLabel+">. "
                    +"?ecosystemURI <"+Resources.rdfsLabel+"> ?ecosystemName. "
                +"} "
                +"OPTIONAL { "
                    +"?stationURI <"+Resources.fallsWithin+"> ?waterAreaURI. "
                    +"?waterAreaURI <"+Resources.rdfTypeLabel+"> <"+Resources.waterAreaLabel+">.  "
                    +"?waterAreaURI <"+Resources.rdfsLabel+"> ?waterAreaName. "
                +"} ";
        if(date!=null && !date.isEmpty()){
            queryString+="FILTER CONTAINS(LCASE(?timespan),\""+date.toLowerCase()+"\"). ";
        }
        if(speciesName!=null && !speciesName.isEmpty()){
            queryString+="FILTER CONTAINS(LCASE(?speciesName),\""+speciesName.toLowerCase()+"\"). ";
        }
        if(place!=null && !place.isEmpty()){
            queryString+="FILTER (CONTAINS(LCASE(?localityName),\""+place.toLowerCase()+"\") ";
            queryString+="|| CONTAINS(LCASE(?countryName),\""+place.toLowerCase()+"\") ";
            queryString+="|| CONTAINS(LCASE(?waterAreaName),\""+place.toLowerCase()+"\") ). ";
        }
        if(datasetURI!=null && !datasetURI.isEmpty()){
            queryString+="FILTER CONTAINS(LCASE(STR(?datasetURI)),\""+datasetURI.toLowerCase()+"\"). ";
        }
        if(numberOfParts!=null && !numberOfParts.isEmpty()){
            queryString+="FILTER (?numberOfParts=\""+numberOfParts+"\"). ";
        }
        queryString+="} ";
        if(limit>0 && offset>=0){
            queryString+="LIMIT "+limit+" "
                        +"OFFSET "+offset;
        }
        
        logger.debug("Submitting the query: \"" + queryString + "\"");
        List<BindingSet> sparqlresults = this.repoManager.query(queryString);
        logger.debug("The SPARQL query returned " + sparqlresults.size() + " results (RAW SPARQL results)");
        Map<String, OccurrenceStatsTempStruct> map = new HashMap<>();
        List<OccurrenceStatsTempStruct> results = new ArrayList();
        for (BindingSet result : sparqlresults) {
            if (!map.containsKey(result.getValue("occurrenceEventURI").stringValue())) {
                OccurrenceStatsTempStruct struct = new OccurrenceStatsTempStruct()
                        .withOccurrenceEventURI(result.getValue("occurrenceEventURI").stringValue())
                        .withOccurrenceEvent(result.getValue("occurrenceEventLabel").stringValue())
                        .withDatasetURI(result.getValue("datasetURI").stringValue())
                        .withDatasetTitle(result.getValue("datasetName").stringValue())
                        .withLocalityURI(result.getValue("localityURI").stringValue())
                        .withLocalityName(result.getValue("localityName").stringValue())
                        .withActor(result.getValue("actorURI").stringValue(), result.getValue("actorName").stringValue())
                        .withSpeciesURI(result.getValue("speciesURI").stringValue())
                        .withSpeciesName(result.getValue("speciesName").stringValue())
                        .withTemporaryAggregateURI(result.getValue("tempURI").stringValue())
                        
                        .withPhysicalObjectURI(result.getValue("individualURI").stringValue())
                        .withPhysicalObject(result.getValue("individualLabel").stringValue())
                        .withStationURI(result.getValue("stationURI").stringValue());
                if (result.getValue("description") != null) {
                    struct.withDescription(result.getValue("description").stringValue());
                }
                if (result.getValue("waterAreaURI") != null) {
                    struct.withWaterAreaURI(result.getValue("waterAreaURI").stringValue());
                }
                if (result.getValue("waterAreaName") != null) {
                    struct.withWaterAreaName(result.getValue("waterAreaName").stringValue());
                }
                if (result.getValue("tempName") != null) {
                    struct.withTemporaryAggregate(result.getValue("tempName").stringValue());
                }
                if (result.getValue("numberOfParts") != null) {
                    struct.withNumberOfParts(result.getValue("numberOfParts").stringValue());
                }
                if (result.getValue("min_depth") != null) {
                    struct.withMinimumDepth(result.getValue("min_depth").stringValue());
                }
                if (result.getValue("max_depth") != null) {
                    struct.withMaximumDepth(result.getValue("max_depth").stringValue());
                }
                if (result.getValue("countryURI") != null) {
                    struct.withCountryURI(result.getValue("countryURI").stringValue());
                }
                if (result.getValue("countryName") != null) {
                    struct.withCountryName(result.getValue("countryName").stringValue());
                }
                if (result.getValue("habitatURI") != null) {
                    struct.withHabitatURI(result.getValue("habitatURI").stringValue());
                }
                if (result.getValue("habitatName") != null) {
                    struct.withHabitatName(result.getValue("habitatName").stringValue());
                }
                if (result.getValue("ecosystemURI") != null) {
                    struct.withEcosystemURI(result.getValue("ecosystemURI").stringValue());
                }
                if (result.getValue("ecosystemName") != null) {
                    struct.withEcosystemName(result.getValue("ecosystemName").stringValue());
                }
                if (result.getValue("equipmentName") != null) {
                    struct.withEquipmentTypeName(result.getValue("equipmentName").stringValue());
                }
                if (result.getValue("equipmentURI") != null) {
                    struct.withEquipmentTypeURI(result.getValue("equipmentURI").stringValue());
                }
                if (result.getValue("timespan") != null) {
                    struct.withTimeSpan(result.getValue("timespan").stringValue());
                }
                if (result.getValue("bibliographicCitationURI") != null) {
                    struct.withBibliographicCitationURI(result.getValue("bibliographicCitationURI").stringValue());
                }
                if (result.getValue("bibliographicCitation") != null) {
                    struct.withBibliographicCitation(result.getValue("bibliographicCitation").stringValue());
                }
                if (result.getValue("samplingProtocolURI") != null) {
                    struct.withSamplingProtocolURI(result.getValue("samplingProtocolURI").stringValue());
                }
                if (result.getValue("samplingProtocol") != null) {
                    struct.withSamplingProtocolName(result.getValue("samplingProtocol").stringValue());
                }
                if (result.getValue("stationNotes") != null) {
                    struct.withStationNotes(result.getValue("stationNotes").stringValue());
                }
                map.put(struct.getOccurrenceEventURI(), struct);
            } else {
                OccurrenceStatsTempStruct struct = map.get(result.getValue("occurrenceEventURI").stringValue());
                struct.withActor(result.getValue("actorURI").stringValue(), result.getValue("actorName").stringValue());
                map.put(struct.getOccurrenceEventURI(), struct);
            }
        }
        logger.debug("The query returned "+map.values().size()+" occurrence stats temp objects");
        return new ArrayList<>(map.values());
    }

//    public List<OccurrenceStatsAbundanceStruct> searchOccurenceStatsAbundance(String speciesName, String temp, String repositoryGraph) throws QueryExecutionException {
//        String queryString = "SELECT DISTINCT ?speciesURI ?datasetURI ?datasetTitle ?dimensionName ?speciesName ?dimensionURI ?dimensionTypeURI ?tempURI ?unit ?value "
//                + "FROM <" + repositoryGraph + "> "
//                + "WHERE{ "
//                + " ?datasetURI <" + Resources.rdfTypeLabel + "> <" + Resources.datasetLabel + "> . "
//                + " ?datasetURI <" + Resources.refersTo + "> ?tempURI . "
//                + " ?datasetURI <" + Resources.isIdentifiedBy + "> ?datasetTitle . "
//                + " ?dimensionTypeURI <" + Resources.isIdentifiedBy + "> ?dimensionName . "
//                + " ?tempURI <" + Resources.hasDimension + "> ?dimensionURI. "
//                + " ?dimensionURI <" + Resources.rdfTypeLabel + "> <" + Resources.dimensionLabel + "> . "
//                + " ?dimensionURI <" + Resources.hasType + "> ?dimensionTypeURI . "
//                //                          +" ?dimensionTypeURI <"+Resources.rdfTypeLabel+"> <"+Resources.dimensionTypeLabel+". } "
//                + " ?tempURI <" + Resources.belongsTo + "> ?speciesURI. "
//                + " ?speciesURI <" + Resources.isIdentifiedBy + "> ?speciesName. "
//                + " OPTIONAL{?dimensionURI <" + Resources.hasValue + "> ?value. } "
//                + " OPTIONAL{?dimensionURI <" + Resources.hasUnit + "> ?unit. } "
//                + "FILTER regex(?tempURI,'" + temp + "',\"i\") "
//                + "FILTER regex(?speciesName,'" + speciesName + "',\"i\")}";
//
//        logger.debug("Submitting the query: \"" + queryString + "\"");
//        List<BindingSet> sparqlresults = this.repoManager.query(queryString);
//        logger.debug("The result returned " + sparqlresults.size() + " results");
//        List<OccurrenceStatsAbundanceStruct> results = new ArrayList<>();
//        for (BindingSet result : sparqlresults) {
//            OccurrenceStatsAbundanceStruct struct = new OccurrenceStatsAbundanceStruct()
//                    .withDatasetURI(result.getValue("datasetURI").stringValue())
//                    .withDatasetName(result.getValue("datasetTitle").stringValue())
//                    .withDimensionURI(result.getValue("dimensionURI").stringValue())
//                    .withDimensionType(result.getValue("dimensionTypeURI").stringValue())
//                    .withDimensionName(result.getValue("dimensionName").stringValue())
//                    .withTemporaryAggregateURI(result.getValue("tempURI").stringValue())
//                    .withSpeciesName(result.getValue("speciesName").stringValue())
//                    .withSpeciesURI(result.getValue("speciesURI").stringValue());
//
//            if (result.getValue("unit") != null) {
//                struct.withDimensionUnit(result.getValue("unit").stringValue());
//            }
//            if (result.getValue("value") != null) {
//                struct.withDimensionValue(result.getValue("value").stringValue());
//            }
//            results.add(struct);
//        }
//        return results;
//
//    }

    public List<EnvironmentalStruct> searchEnvironmental(String dimension, String place, String datasetURI, String repositoryGraph) throws QueryExecutionException {
        String queryString = "SELECT DISTINCT ?datasetURI ?datasetName "
                + " ?measurementEventURI ?measurementEventLabel ?dimensionTypeURI ?dimensionName "
                + " ?placeURI ?placeName ?date ?unit ?value ?dimensionURI ?actorName ?actorURI "
                + "FROM <" + repositoryGraph + "> "
                + " WHERE{ "
                + " ?datasetURI <" + Resources.rdfTypeLabel + "> <" + Resources.datasetLabel + "> . "
                + " ?datasetURI <" + Resources.refersTo + "> ?measurementEventURI . "
                + " ?datasetURI <" + Resources.rdfsLabel + "> ?datasetName. "
                + " ?measurementEventURI <" + Resources.rdfTypeLabel + "> <" + Resources.measurementEventLabel + "> . "
                + " ?measurementEventURI <" + Resources.measured + "> ?station . "
                + " ?measurementEventURI <" + Resources.rdfsLabel + "> ?measurementEventLabel . "
                
                + " ?station <" + Resources.rdfTypeLabel + "> <" + Resources.ecosystemEnvironmentLabel + "> . "
                + " ?measurementEventURI <" + Resources.carriedOutBy + "> ?actorURI . "
                + " ?actorURI <" + Resources.rdfsLabel + "> ?actorName.  "
                + " ?station <" + Resources.fallsWithin + "> ?placeURI.  "
                + " ?placeURI <" + Resources.rdfTypeLabel + "> <" + Resources.placeLabel + "> . "
                + " ?placeURI <" + Resources.rdfsLabel + "> ?placeName.  "
                + " ?measurementEventURI <" + Resources.observedDimension + "> ?dimensionURI . "
                + "OPTIONAL { ?measurementEventURI <" + Resources.hasTimespan + "> ?date .} "
                + "?dimensionURI <" + Resources.rdfTypeLabel + "> <" + Resources.dimensionLabel + "> . "
                //+"?dimensionTypeURI <"+Resources.rdfTypeLabel+"> <"+Resources.dimensionTypeLabel+">.  "
                + "?dimensionTypeURI <" + Resources.rdfsLabel + "> ?dimensionName.  "
                + " ?dimensionURI <" + Resources.hasType + "> ?dimensionTypeURI.  "
                + " OPTIONAL { ?dimensionURI <" + Resources.hasValue + "> ?value. } "
                + " OPTIONAL { ?dimensionURI <" + Resources.hasUnit + "> ?unit.  } "
                + "FILTER regex(?placeName,'" + place + "',\"i\") "
                + " FILTER regex(?datasetURI,'" + datasetURI + "',\"i\")  "
                + "FILTER regex(?dimensionName,'" + dimension + "',\"i\")}";

        logger.debug("Submitting the query: \"" + queryString + "\"");
        List<BindingSet> sparqlresults = this.repoManager.query(queryString);
        logger.debug("The query returned " + sparqlresults.size() + " results");
        Map<String, EnvironmentalStruct> map = new HashMap<>();
        for (BindingSet result : sparqlresults) {
            if (!map.containsKey(result.getValue("measurementEventURI").stringValue())) {
                EnvironmentalStruct struct = new EnvironmentalStruct().withMeasurementEventURI(result.getValue("measurementEventURI").stringValue())
                        .withMeasurementEvent(result.getValue("measurementEventLabel").stringValue())
                        .withDimensionName(result.getValue("dimensionName").stringValue())
                        .withDatasetURI(result.getValue("datasetURI").stringValue())
                        .withDatasetName(result.getValue("datasetName").stringValue())
                        .withDimensionTypeURI(result.getValue("dimensionTypeURI").stringValue())
                        .withDimensionURI(result.getValue("dimensionURI").stringValue())
                        .withActor(result.getValue("actorName").stringValue(), result.getValue("actorURI").stringValue())
                        .withPlaceName(result.getValue("placeName").stringValue())
                        .withPlaceURI(result.getValue("placeURI").stringValue());
                if (result.getValue("date") != null) {
                    struct.withTimeSpan(result.getValue("date").stringValue());
                }
                if (result.getValue("value") != null) {
                    struct.withDimensionValue(result.getValue("value").stringValue());
                }
                if (result.getValue("unit") != null) {
                    struct.withDimensionUnit(result.getValue("unit").stringValue());
                }
                map.put(struct.getMeasurementEventURI(), struct);
            } else {
                EnvironmentalStruct struct = map.get(result.getValue("measurementEventURI").stringValue());
                struct.withActor(result.getValue("actorName").stringValue(), result.getValue("actorURI").stringValue());
                map.put(struct.getMeasurementEventURI(), struct);
            }
        }
        return new ArrayList<>(map.values());
    }

    public List<EnvironmentalStruct> searchEnvironmental(String dimension, String place, String datasetURI,int offset, int limit, String repositoryGraph) throws QueryExecutionException {
        String queryString = "SELECT DISTINCT ?datasetURI ?datasetName "
                + " ?measurementEventURI ?measurementEventLabel ?dimensionTypeURI ?dimensionName "
                + " ?placeURI ?placeName ?date ?unit ?value ?dimensionURI ?actorName ?actorURI "
                + "FROM <" + repositoryGraph + "> "
                + " WHERE{ "
                + " ?datasetURI <" + Resources.rdfTypeLabel + "> <" + Resources.datasetLabel + "> . "
                + " ?datasetURI <" + Resources.refersTo + "> ?measurementEventURI . "
                + " ?datasetURI <" + Resources.rdfsLabel + "> ?datasetName. "
                + " ?measurementEventURI <" + Resources.rdfTypeLabel + "> <" + Resources.measurementEventLabel + "> . "
                + " ?measurementEventURI <" + Resources.measured + "> ?station . "
                + " ?measurementEventURI <" + Resources.rdfsLabel + "> ?measurementEventLabel . "
                + " ?station <" + Resources.rdfTypeLabel + "> <" + Resources.ecosystemEnvironmentLabel + "> . "
                + " ?measurementEventURI <" + Resources.carriedOutBy + "> ?actorURI . "
                + " ?actorURI <" + Resources.rdfsLabel + "> ?actorName.  "
                + " ?station <" + Resources.fallsWithin + "> ?placeURI.  "
                + " ?placeURI <" + Resources.rdfTypeLabel + "> <" + Resources.placeLabel + "> . "
                + " ?placeURI <" + Resources.rdfsLabel + "> ?placeName.  "
                + " ?measurementEventURI <" + Resources.observedDimension + "> ?dimensionURI . "
                + "OPTIONAL { ?measurementEventURI <" + Resources.hasTimespan + "> ?date .} "
                + "?dimensionURI <" + Resources.rdfTypeLabel + "> <" + Resources.dimensionLabel + "> . "
                //+"?dimensionTypeURI <"+Resources.rdfTypeLabel+"> <"+Resources.dimensionTypeLabel+">.  "
                + "?dimensionTypeURI <" + Resources.rdfsLabel + "> ?dimensionName.  "
                + " ?dimensionURI <" + Resources.hasType + "> ?dimensionTypeURI.  "
                + " OPTIONAL { ?dimensionURI <" + Resources.hasValue + "> ?value. } "
                + " OPTIONAL { ?dimensionURI <" + Resources.hasUnit + "> ?unit.  } "
                + "FILTER regex(?placeName,'" + place + "',\"i\") "
                + " FILTER regex(?datasetURI,'" + datasetURI + "',\"i\")  "
                + "FILTER regex(?dimensionName,'" + dimension + "',\"i\")}"
                + " LIMIT " + limit
                + " OFFSET " + offset;

        logger.debug("Submitting the query: \"" + queryString + "\"");
        List<BindingSet> sparqlresults = this.repoManager.query(queryString);
        logger.debug("The query returned " + sparqlresults.size() + " results");
        //Map<String, EnvironmentalStruct> map = new HashMap<>();
        List<EnvironmentalStruct> results = new ArrayList();
        for (BindingSet result : sparqlresults) {
           // if (!map.containsKey(result.getValue("measurementEventURI").stringValue())) {
                EnvironmentalStruct struct = new EnvironmentalStruct().withMeasurementEventURI(result.getValue("measurementEventURI").stringValue())
                        .withMeasurementEvent(result.getValue("measurementEventLabel").stringValue())
                        .withDimensionName(result.getValue("dimensionName").stringValue())
                        .withDatasetURI(result.getValue("datasetURI").stringValue())
                        .withDatasetName(result.getValue("datasetName").stringValue())
                        .withDimensionTypeURI(result.getValue("dimensionTypeURI").stringValue())
                        .withDimensionURI(result.getValue("dimensionURI").stringValue())
                        .withActor(result.getValue("actorName").stringValue(), result.getValue("actorURI").stringValue())
                        .withPlaceName(result.getValue("placeName").stringValue())
                        .withPlaceURI(result.getValue("placeURI").stringValue());
                if (result.getValue("date") != null) {
                    struct.withTimeSpan(result.getValue("date").stringValue());
                }
                if (result.getValue("value") != null) {
                    struct.withDimensionValue(result.getValue("value").stringValue());
                }
                if (result.getValue("unit") != null) {
                    struct.withDimensionUnit(result.getValue("unit").stringValue());
                }
                results.add(struct);
//                map.put(struct.getMeasurementEventURI(), struct);
//            } else {
//                EnvironmentalStruct struct = map.get(result.getValue("measurementEventURI").stringValue());
//                struct.withActor(result.getValue("actorName").stringValue(), result.getValue("actorURI").stringValue());
//                map.put(struct.getMeasurementEventURI(), struct);
//            }
        }
        return results;
        //return new ArrayList<>(map.values());
    }

    public List<MeasurementStruct> searchMeasurement(String specimen, String species, String dimension, String datasetURI, String repositoryGraph) throws QueryExecutionException {
        String queryString = "SELECT DISTINCT ?measurementEventURI ?measurementEventLabel ?dimensionURI ?datasetURI ?datasetTitle ?dimensionTypeURI ?dimensionName ?specimenURI ?specimenName ?date ?unit ?value "
                + "?speciesURI ?speciesName ?actorName ?actorURI "
                + "FROM <" + repositoryGraph + "> "
                + "WHERE{ "
                //+ "?datasetURI <" + Resources.rdfTypeLabel + "> <" + Resources.datasetLabel + "> . "
                + "?datasetURI <" + Resources.refersTo + "> ?measurementEventURI . "
                + "?datasetURI <" + Resources.rdfsLabel+ "> ?datasetTitle . "
                + " ?measurementEventURI <" + Resources.rdfTypeLabel + "> <" + Resources.measurementEventLabel + "> . "
                + " ?measurementEventURI <" + Resources.rdfsLabel + "> ?measurementEventLabel . "
                + " ?measurementEventURI <" + Resources.measured + "> ?specimenURI . "
                + " ?measurementEventURI <" + Resources.observedDimension + "> ?dimensionURI . "
                + " OPTIONAL { ?measurementEventURI <" + Resources.hasTimespan + "> ?date .}  "
                + " ?measurementEventURI <" + Resources.carriedOutBy + "> ?actorURI . "
                + " ?actorURI <" + Resources.rdfsLabel + "> ?actorName.  "
                + " ?specimenURI <" + Resources.belongsTo + "> ?speciesURI .  "
                + " ?speciesURI <" + Resources.rdfsLabel + "> ?speciesName . "
                //+ " ?speciesURI <" + Resources.rdfTypeLabel + "> <" + Resources.speciesLabel + "> . "
                + " ?dimensionURI <" + Resources.rdfTypeLabel + "> <" + Resources.dimensionLabel + "> . "
                // +" ?dimensionTypeURI <"+Resources.rdfTypeLabel+"> <"+Resources.dimensionTypeLabel+"> . "
                + " ?dimensionTypeURI <" + Resources.rdfsLabel + "> ?dimensionName. "
                + " ?dimensionURI <" + Resources.hasType + "> ?dimensionTypeURI. "
                + " OPTIONAL { ?dimensionURI <" + Resources.hasValue + "> ?value. } "
                + " OPTIONAL { ?dimensionURI <" + Resources.hasUnit + "> ?unit.  } "
                + " ?specimenURI <" + Resources.rdfTypeLabel + "> <" + Resources.specimenLabel + "> . "
                + " ?specimenURI <" + Resources.rdfsLabel+ "> ?specimenName. "
                + " FILTER regex(?speciesName,'" + species + "',\"i\") "
                + " FILTER regex(?specimenName,'" + specimen + "',\"i\") "
                + " FILTER regex(?datasetURI,'" + datasetURI + "',\"i\")  "
                + " FILTER regex(?dimensionName,'" + dimension + "',\"i\")}";

        logger.debug("Submitting the query: \"" + queryString + "\"");
        List<BindingSet> sparqlresults = this.repoManager.query(queryString);
        logger.debug("The result returned " + sparqlresults.size() + " results");
        Map<String, MeasurementStruct> map = new HashMap<>();
        for (BindingSet result : sparqlresults) {
            if (!map.containsKey(result.getValue("measurementEventURI").stringValue())) {
                MeasurementStruct struct = new MeasurementStruct().withMeasurementEventURI(result.getValue("measurementEventURI").stringValue())
                        .withMeasurementEvent(result.getValue("measurementEventLabel").stringValue())
                        .withDatasetURI(result.getValue("datasetURI").stringValue())
                        .withDatasetName(result.getValue("datasetTitle").stringValue())
                        .withSpeciesURI(result.getValue("speciesURI").stringValue())
                        .withSpeciesName(result.getValue("speciesName").stringValue())
                        .withDimensionName(result.getValue("dimensionName").stringValue())
                        .withDimensionType(result.getValue("dimensionTypeURI").stringValue())
                        .withDimensionURI(result.getValue("dimensionURI").stringValue())
                        .withSpecimenName(result.getValue("specimenName").stringValue())
                        .withSpecimenURI(result.getValue("specimenURI").stringValue());
                if (result.getValue("date") != null) {
                    struct.withTimeSpan(result.getValue("date").stringValue());
                }
                if (result.getValue("value") != null) {
                    struct.withDimensionValue(result.getValue("value").stringValue());
                }
                if (result.getValue("unit") != null) {
                    struct.withDimensionUnit(result.getValue("unit").stringValue());
                }
                if (result.getValue("actorName") != null && result.getValue("actorURI") != null) {
                    struct.withActor(result.getValue("actorURI").stringValue(), result.getValue("actorName").stringValue());
                }
                map.put(struct.getMeasurementEventURI(), struct);
            } else {
                MeasurementStruct struct = map.get(result.getValue("measurementEventURI").stringValue());
                struct.withActor(result.getValue("actorURI").stringValue(), result.getValue("actorName").stringValue());
                map.put(struct.getMeasurementEventURI(), struct);
            }
        }
        return new ArrayList<>(map.values());
    }

      public List<MeasurementStruct> searchMeasurement(String specimen, String species, String dimension, String datasetURI, int offset, int limit, String repositoryGraph) throws QueryExecutionException {
        String queryString = "SELECT DISTINCT ?measurementEventURI ?measurementEventLabel ?dimensionURI ?datasetURI ?datasetTitle ?dimensionTypeURI ?dimensionName ?specimenURI ?specimenName ?date ?unit ?value "
                + "?speciesURI ?speciesName ?actorName ?actorURI "
                + "FROM <" + repositoryGraph + "> "
                + "WHERE{ "
                //+ "?datasetURI <" + Resources.rdfTypeLabel + "> <" + Resources.datasetLabel + "> . "
                + "?datasetURI <" + Resources.refersTo + "> ?measurementEventURI . "
                + "?datasetURI <" + Resources.rdfsLabel+ "> ?datasetTitle . "
                + " ?measurementEventURI <" + Resources.rdfTypeLabel + "> <" + Resources.measurementEventLabel + "> . "
                + " ?measurementEventURI <" + Resources.rdfsLabel + "> ?measurementEventLabel . "
                + " ?measurementEventURI <" + Resources.measured + "> ?specimenURI . "
                + " ?measurementEventURI <" + Resources.observedDimension + "> ?dimensionURI . "
                + " OPTIONAL { ?measurementEventURI <" + Resources.hasTimespan + "> ?date .}  "
                + " ?measurementEventURI <" + Resources.carriedOutBy + "> ?actorURI . "
                + " ?actorURI <" + Resources.rdfsLabel + "> ?actorName.  "
                + " ?specimenURI <" + Resources.belongsTo + "> ?speciesURI .  "
                + " ?speciesURI <" + Resources.rdfsLabel + "> ?speciesName . "
                //+ " ?speciesURI <" + Resources.rdfTypeLabel + "> <" + Resources.speciesLabel + "> . "
                + " ?dimensionURI <" + Resources.rdfTypeLabel + "> <" + Resources.dimensionLabel + "> . "
                // +" ?dimensionTypeURI <"+Resources.rdfTypeLabel+"> <"+Resources.dimensionTypeLabel+"> . "
                + " ?dimensionTypeURI <" + Resources.rdfsLabel + "> ?dimensionName. "
                + " ?dimensionURI <" + Resources.hasType + "> ?dimensionTypeURI. "
                + " OPTIONAL { ?dimensionURI <" + Resources.hasValue + "> ?value. } "
                + " OPTIONAL { ?dimensionURI <" + Resources.hasUnit + "> ?unit.  } "
                + " ?specimenURI <" + Resources.rdfTypeLabel + "> <" + Resources.specimenLabel + "> . "
                + " ?specimenURI <" + Resources.rdfsLabel+ "> ?specimenName. "
                + " FILTER regex(?speciesName,'" + species + "',\"i\") "
                + " FILTER regex(?specimenName,'" + specimen + "',\"i\") "
                + " FILTER regex(?datasetURI,'" + datasetURI + "',\"i\")  "
                + " FILTER regex(?dimensionName,'" + dimension + "',\"i\")}"
                + " LIMIT " + limit
                + " OFFSET " + offset;

        logger.debug("Submitting the query: \"" + queryString + "\"");
        List<BindingSet> sparqlresults = this.repoManager.query(queryString);
        logger.debug("The result returned " + sparqlresults.size() + " results");
        Map<String, MeasurementStruct> map = new HashMap<>();
        List<MeasurementStruct> results = new ArrayList();
        for (BindingSet result : sparqlresults) {
          //  if (!map.containsKey(result.getValue("measurementEventURI").stringValue())) {
                MeasurementStruct struct = new MeasurementStruct().withMeasurementEventURI(result.getValue("measurementEventURI").stringValue())
                        .withMeasurementEvent(result.getValue("measurementEventLabel").stringValue())
                        .withDatasetURI(result.getValue("datasetURI").stringValue())
                        .withDatasetName(result.getValue("datasetTitle").stringValue())
                        .withSpeciesURI(result.getValue("speciesURI").stringValue())
                        .withSpeciesName(result.getValue("speciesName").stringValue())
                        .withDimensionName(result.getValue("dimensionName").stringValue())
                        .withDimensionType(result.getValue("dimensionTypeURI").stringValue())
                        .withDimensionURI(result.getValue("dimensionURI").stringValue())
                        .withSpecimenName(result.getValue("specimenName").stringValue())
                        .withSpecimenURI(result.getValue("specimenURI").stringValue());
                if (result.getValue("date") != null) {
                    struct.withTimeSpan(result.getValue("date").stringValue());
                }
                if (result.getValue("value") != null) {
                    struct.withDimensionValue(result.getValue("value").stringValue());
                }
                if (result.getValue("unit") != null) {
                    struct.withDimensionUnit(result.getValue("unit").stringValue());
                }
                if (result.getValue("actorName") != null && result.getValue("actorURI") != null) {
                    struct.withActor(result.getValue("actorURI").stringValue(), result.getValue("actorName").stringValue());
                }
                
                results.add(struct);
//                map.put(struct.getMeasurementEventURI(), struct);
//            } else {
//                MeasurementStruct struct = map.get(result.getValue("measurementEventURI").stringValue());
//                struct.withActor(result.getValue("actorURI").stringValue(), result.getValue("actorName").stringValue());
//                map.put(struct.getMeasurementEventURI(), struct);
//            }
        }
        return results;
        //return new ArrayList<>(map.values());
    }

    public List<MorphometricsStruct> searchMorphometrics(String species, String dimension, String datasetURI,String repositoryGraph) throws QueryExecutionException {
        String queryString = "SELECT DISTINCT ?attributeAssignmentEventURI ?attributeAssignmentEventLabel ?dimensionURI ?datasetURI ?datasetTitle "
                + "?dimensionTypeURI ?publicationURI ?publicationName ?dimensionName ?actorName ?actorURI  "
                + "?speciesURI ?speciesName ?date ?unit ?value ?description  "
                + "FROM <" + repositoryGraph + ">  "
                + "WHERE{ "
                //+ "?datasetURI <" + Resources.rdfTypeLabel + "> <" + Resources.datasetLabel + "> .  "
                + "?datasetURI <" + Resources.refersTo + "> ?attributeAssignmentEventURI .  "
                + "?datasetURI <" + Resources.rdfsLabel + "> ?datasetTitle .  "
                + " ?attributeAssignmentEventURI <" + Resources.rdfTypeLabel + "> <" + Resources.attributeAssignmentEventLabel + "> . "
                + " ?attributeAssignmentEventURI <" + Resources.assignedAttributeTo + "> ?speciesURI .  "
                + " ?attributeAssignmentEventURI  <" + Resources.assigned + "> ?dimensionURI .  "
                + " ?attributeAssignmentEventURI  <" + Resources.hasNote + "> ?description .  "
                + " ?attributeAssignmentEventURI <" + Resources.rdfsLabel + "> ?attributeAssignmentEventLabel . "
                + " OPTIONAL { ?attributeAssignmentEventURI  <" + Resources.hasTimespan + "> ?date .}  "
                + " ?attributeAssignmentEventURI <" + Resources.carriedOutBy + "> ?actorURI . "
                + " ?actorURI <" + Resources.rdfsLabel + "> ?actorName.  "
                + " OPTIONAL { ?publicationURI  <" + Resources.refersTo + "> ?attributeAssignmentEventURI . "
                + "  ?publicationURI <" + Resources.rdfTypeLabel + "> <" + Resources.publicationLabel + "> .  "
                + "  ?publicationURI <" + Resources.rdfsLabel+ "> ?publicationName.}  "
                + " ?dimensionURI <" + Resources.rdfTypeLabel + "> <" + Resources.dimensionLabel + "> .  "
                //                            +" ?dimensionTypeURI <"+Resources.rdfTypeLabel+"> <"+Resources.dimensionTypeLabel+">.  "
                + " ?dimensionTypeURI <" + Resources.rdfsLabel + "> ?dimensionName.  "
                + " ?dimensionURI <" + Resources.hasType + "> ?dimensionTypeURI.  "
                + " OPTIONAL { ?dimensionURI <" + Resources.hasValue + "> ?value. } "
                + " OPTIONAL { ?dimensionURI <" + Resources.hasUnit + "> ?unit.  } "
                //+ " ?speciesURI <" + Resources.rdfTypeLabel + "> <" + Resources.speciesLabel + "> .  "
                + " ?speciesURI <" + Resources.rdfsLabel + "> ?speciesName.  "
                + " FILTER regex(?speciesName,'" + species + "',\"i\")  "
                + " FILTER regex(?datasetURI,'" + datasetURI + "',\"i\")  "
                + " FILTER regex(?dimensionName,'" + dimension + "',\"i\")} ";

        logger.debug("Submitting the query: \"" + queryString + "\"");
        List<BindingSet> sparqlresults = this.repoManager.query(queryString);
        logger.debug("The query returned " + sparqlresults.size() + " results");
        Map<String, MorphometricsStruct> map = new HashMap<>();
        for (BindingSet result : sparqlresults) {
            if (!map.containsKey(result.getValue("attributeAssignmentEventURI").stringValue())) {
                MorphometricsStruct struct = new MorphometricsStruct().withAttributeAssignmentEventURI(result.getValue("attributeAssignmentEventURI").stringValue())
                        .withAttributeAssignmentEvent(result.getValue("attributeAssignmentEventLabel").stringValue())
                        .withDatasetURI(result.getValue("datasetURI").stringValue())
                        .withDatasetName(result.getValue("datasetTitle").stringValue())
                        .withDimensionName(result.getValue("dimensionName").stringValue())
                        .withDimensionType(result.getValue("dimensionTypeURI").stringValue())
                        .withDimensionURI(result.getValue("dimensionURI").stringValue())
                        .withSpeciesName(result.getValue("speciesName").stringValue())
                        .withActor(result.getValue("actorURI").stringValue(), result.getValue("actorName").stringValue())
                        .withSpeciesURI(result.getValue("speciesURI").stringValue());
                if (result.getValue("date") != null) {
                    struct.withTimeSpan(result.getValue("date").stringValue());
                }
                if (result.getValue("value") != null) {
                    struct.withDimensionValue(result.getValue("value").stringValue());
                }
                if (result.getValue("unit") != null) {
                    struct.withDimensionUnit(result.getValue("unit").stringValue());
                }
                if (result.getValue("publicationName") != null) {
                    struct.withPublicationName(result.getValue("publicationName").stringValue());
                }
                if (result.getValue("publicationURI") != null) {
                    struct.withPublicationURI(result.getValue("publicationURI").stringValue());
                }
                if (result.getValue("description") != null) {
                    struct.withDescription(result.getValue("description").stringValue());
                }
                map.put(struct.getAttributeAssignmentEventURI(), struct);
            } else {
                MorphometricsStruct struct = map.get(result.getValue("attributeAssignmentEventURI").stringValue());
                struct.withActor(result.getValue("actorURI").stringValue(), result.getValue("actorName").stringValue());
                map.put(struct.getAttributeAssignmentEventURI(), struct);
            }
        }
        return new ArrayList<>(map.values());
    }

      public List<MorphometricsStruct> searchMorphometrics(String species, String dimension, String datasetURI, int offset, int limit, String repositoryGraph) throws QueryExecutionException {
        String queryString = "SELECT DISTINCT ?attributeAssignmentEventURI ?attributeAssignmentEventLabel ?dimensionURI ?datasetURI ?datasetTitle "
                + "?dimensionTypeURI ?publicationURI ?publicationName ?dimensionName ?actorName ?actorURI  "
                + "?speciesURI ?speciesName ?date ?unit ?value ?description  "
                + "FROM <" + repositoryGraph + ">  "
                + "WHERE{ "
                //+ "?datasetURI <" + Resources.rdfTypeLabel + "> <" + Resources.datasetLabel + "> .  "
                + "?datasetURI <" + Resources.refersTo + "> ?attributeAssignmentEventURI .  "
                + "?datasetURI <" + Resources.rdfsLabel + "> ?datasetTitle .  "
                + " ?attributeAssignmentEventURI <" + Resources.rdfTypeLabel + "> <" + Resources.attributeAssignmentEventLabel + "> . "
                + " ?attributeAssignmentEventURI <" + Resources.assignedAttributeTo + "> ?speciesURI .  "
                + " ?attributeAssignmentEventURI  <" + Resources.assigned + "> ?dimensionURI .  "
                + " ?attributeAssignmentEventURI  <" + Resources.hasNote + "> ?description .  "
                + " ?attributeAssignmentEventURI  <" + Resources.rdfsLabel + "> ?attributeAssignmentEventLabel  . "
                + " OPTIONAL { ?attributeAssignmentEventURI  <" + Resources.hasTimespan + "> ?date .}  "
                + " ?attributeAssignmentEventURI <" + Resources.carriedOutBy + "> ?actorURI . "
                + " ?actorURI <" + Resources.rdfsLabel + "> ?actorName.  "
                + " OPTIONAL { ?publicationURI  <" + Resources.refersTo + "> ?attributeAssignmentEventURI . "
                + "  ?publicationURI <" + Resources.rdfTypeLabel + "> <" + Resources.publicationLabel + "> .  "
                + "  ?publicationURI <" + Resources.rdfsLabel+ "> ?publicationName.}  "
                + " ?dimensionURI <" + Resources.rdfTypeLabel + "> <" + Resources.dimensionLabel + "> .  "
                //                            +" ?dimensionTypeURI <"+Resources.rdfTypeLabel+"> <"+Resources.dimensionTypeLabel+">.  "
                + " ?dimensionTypeURI <" + Resources.rdfsLabel + "> ?dimensionName.  "
                + " ?dimensionURI <" + Resources.hasType + "> ?dimensionTypeURI.  "
                + " OPTIONAL { ?dimensionURI <" + Resources.hasValue + "> ?value. } "
                + " OPTIONAL { ?dimensionURI <" + Resources.hasUnit + "> ?unit.  } "
                //+ " ?speciesURI <" + Resources.rdfTypeLabel + "> <" + Resources.speciesLabel + "> .  "
                + " ?speciesURI <" + Resources.rdfsLabel + "> ?speciesName.  "
                + " FILTER regex(?speciesName,'" + species + "',\"i\")  "
                + " FILTER regex(?datasetURI,'" + datasetURI + "',\"i\")  "
                + " FILTER regex(?dimensionName,'" + dimension + "',\"i\")} "
                + " LIMIT " + limit
                + " OFFSET " + offset;

        logger.debug("Submitting the query: \"" + queryString + "\"");
        List<BindingSet> sparqlresults = this.repoManager.query(queryString);
        logger.debug("The query returned " + sparqlresults.size() + " results");
        Map<String, MorphometricsStruct> map = new HashMap<>();
        for (BindingSet result : sparqlresults) {
            if (!map.containsKey(result.getValue("attributeAssignmentEventURI").stringValue())) {
                MorphometricsStruct struct = new MorphometricsStruct().withAttributeAssignmentEventURI(result.getValue("attributeAssignmentEventURI").stringValue())
                        .withAttributeAssignmentEvent(result.getValue("attributeAssignmentEventLabel").stringValue())
                        .withDatasetURI(result.getValue("datasetURI").stringValue())
                        .withDatasetName(result.getValue("datasetTitle").stringValue())
                        .withDimensionName(result.getValue("dimensionName").stringValue())
                        .withDimensionType(result.getValue("dimensionTypeURI").stringValue())
                        .withDimensionURI(result.getValue("dimensionURI").stringValue())
                        .withSpeciesName(result.getValue("speciesName").stringValue())
                        .withActor(result.getValue("actorURI").stringValue(), result.getValue("actorName").stringValue())
                        .withSpeciesURI(result.getValue("speciesURI").stringValue());
                if (result.getValue("date") != null) {
                    struct.withTimeSpan(result.getValue("date").stringValue());
                }
                if (result.getValue("value") != null) {
                    struct.withDimensionValue(result.getValue("value").stringValue());
                }
                if (result.getValue("unit") != null) {
                    struct.withDimensionUnit(result.getValue("unit").stringValue());
                }
                if (result.getValue("publicationName") != null) {
                    struct.withPublicationName(result.getValue("publicationName").stringValue());
                }
                if (result.getValue("publicationURI") != null) {
                    struct.withPublicationURI(result.getValue("publicationURI").stringValue());
                }
                if (result.getValue("description") != null) {
                    struct.withDescription(result.getValue("description").stringValue());
                }
                map.put(struct.getAttributeAssignmentEventURI(), struct);
            } else {
                MorphometricsStruct struct = map.get(result.getValue("attributeAssignmentEventURI").stringValue());
                struct.withActor(result.getValue("actorURI").stringValue(), result.getValue("actorName").stringValue());
                map.put(struct.getAttributeAssignmentEventURI(), struct);
            }
        }
        return new ArrayList<>(map.values());
    }

    public List<StatsStruct> searchStats(String species, String dimension,String datasetURI, String repositoryGraph) throws QueryExecutionException {
        String queryString = "SELECT DISTINCT ?dataEvaluationURI ?dataEvaluationLabel ?dimensionURI ?dimensionTypeURI ?publicationURI ?publicationName ?dimensionName "
                + "?speciesURI ?speciesName ?date ?unit ?value ?description ?actorName ?actorURI ?datasetURI ?datasetTitle ?specimenName ?specimenURI "
                + "FROM <" + repositoryGraph + "> "
                + "WHERE{ "
                + "?datasetURI <" + Resources.rdfTypeLabel + "> <" + Resources.datasetLabel + "> . "
                + "?datasetURI <" + Resources.refersTo + "> ?dataEvaluationURI  . "
                + "?datasetURI <" + Resources.rdfsLabel+ "> ?datasetTitle . "
                + " ?dataEvaluationURI <" + Resources.rdfTypeLabel + "> <" + Resources.dataEvaluationLabel + "> . "
                + " ?dataEvaluationURI <" + Resources.describes + "> ?specimenURI . "
                + " ?dataEvaluationURI  <" + Resources.assignedDimension + "> ?dimensionURI .  "
                + " ?dataEvaluationURI <" + Resources.rdfsLabel+ "> ?dataEvaluationLabel . "
                + " OPTIONAL { ?dataEvaluationURI  <" + Resources.hasTimespan + "> ?date .} "
                + " ?specimenURI <" + Resources.belongsTo + "> ?speciesURI . "
                + " ?specimenURI <" + Resources.rdfTypeLabel + "> <" + Resources.specimenLabel + "> . "
                + " ?specimenURI <" + Resources.rdfsLabel+ "> ?specimenName. "
                + " ?dataEvaluationURI  <" + Resources.carriedOutBy + "> ?actorURI . "
                + "?actorURI <" + Resources.rdfsLabel + "> ?actorName . "
                + " OPTIONAL { ?dataEvaluationURI  <" + Resources.hasNote + "> ?description . } "
                + " OPTIONAL { ?publicationURI  <" + Resources.refersTo + "> ?dataEvaluationURI . "
                + " ?publicationURI <" + Resources.rdfTypeLabel + "> <" + Resources.publicationLabel + "> . "
                + "  ?publicationURI <" + Resources.rdfsLabel + "> ?publicationName. }  "
                + " ?dimensionURI <" + Resources.rdfTypeLabel + "> <" + Resources.dimensionLabel + "> . "
                //                          +" ?dimensionTypeURI <"+Resources.rdfTypeLabel+"> <"+Resources.dimensionTypeLabel+">. "
                + " ?dimensionTypeURI <" + Resources.rdfsLabel + "> ?dimensionName. "
                + " ?dimensionURI <" + Resources.hasType + "> ?dimensionTypeURI. "
                + " OPTIONAL { ?dimensionURI <" + Resources.hasValue + "> ?value. } "
                + " OPTIONAL { ?dimensionURI <" + Resources.hasUnit + "> ?unit.  } "
                //                          +" ?speciesURI <"+Resources.rdfTypeLabel+"> <"+Resources.speciesLabel+"> . "
                + " ?speciesURI <" + Resources.rdfsLabel + "> ?speciesName. "
                + " FILTER regex(?speciesName,'" + species + "',\"i\") "
                + " FILTER regex(?datasetURI,'" + datasetURI + "',\"i\")  "
                + " FILTER regex(?dimensionName,'" + dimension + "',\"i\")}";

        logger.debug("Submitting the query: \"" + queryString + "\"");
        List<BindingSet> sparqlresults = this.repoManager.query(queryString);
        logger.debug("The query returned " + sparqlresults.size() + " results");
        Map<String, StatsStruct> map = new HashMap<>();
        for (BindingSet result : sparqlresults) {
            if (!map.containsKey(result.getValue("dataEvaluationURI").stringValue())) {
                StatsStruct struct = new StatsStruct().withDataEvaluationURI(result.getValue("dataEvaluationURI").stringValue())
                        .withDataEvaluation(result.getValue("dataEvaluationLabel").stringValue())
                        .withDatasetURI(result.getValue("datasetURI").stringValue())
                        .withDatasetName(result.getValue("datasetTitle").stringValue())
                        .withDimensionName(result.getValue("dimensionName").stringValue())
                        .withDimensionType(result.getValue("dimensionTypeURI").stringValue())
                        .withDimensionURI(result.getValue("dimensionURI").stringValue())
                        .withSpeciesName(result.getValue("speciesName").stringValue())
                        .withSpecimenURI(result.getValue("specimenURI").stringValue())
                        .withSpecimenName(result.getValue("specimenName").stringValue())
                        .withActor(result.getValue("actorURI").stringValue(), result.getValue("actorName").stringValue())
                        .withSpeciesURI(result.getValue("speciesURI").stringValue());
                if (result.getValue("date") != null) {
                    struct.withTimeSpan(result.getValue("date").stringValue());
                }
                if (result.getValue("value") != null) {
                    struct.withDimensionValue(result.getValue("value").stringValue());
                }
                if (result.getValue("unit") != null) {
                    struct.withDimensionUnit(result.getValue("unit").stringValue());
                }
                if (result.getValue("description") != null) {
                    struct.withDescription(result.getValue("description").stringValue());
                }
                if (result.getValue("publicationName") != null) {
                    struct.withPublicationName(result.getValue("publicationName").stringValue());
                }
                if (result.getValue("publicationURI") != null) {
                    struct.withPublicationURI(result.getValue("publicationURI").stringValue());
                }
                map.put(struct.getDataEvaluationURI(), struct);
            } else {
                StatsStruct struct = map.get(result.getValue("dataEvaluationURI").stringValue());
                struct.withActor(result.getValue("actorURI").stringValue(), result.getValue("actorName").stringValue());
                map.put(struct.getDataEvaluationURI(), struct);
            }
        }
        return new ArrayList<>(map.values());
    }

    public List<StatsStruct> searchStats(String species, String dimension,String datasetURI, int offset, int limit, String repositoryGraph) throws QueryExecutionException {
        String queryString = "SELECT DISTINCT ?dataEvaluationURI ?dataEvaluationLabel ?dimensionURI ?dimensionTypeURI ?publicationURI ?publicationName ?dimensionName "
                + "?speciesURI ?speciesName ?date ?unit ?value ?description ?actorName ?actorURI ?datasetURI ?datasetTitle ?specimenName ?specimenURI "
                + "FROM <" + repositoryGraph + "> "
                + "WHERE{ "
                + "?datasetURI <" + Resources.rdfTypeLabel + "> <" + Resources.datasetLabel + "> . "
                + "?datasetURI <" + Resources.refersTo + "> ?dataEvaluationURI  . "
                + "?datasetURI <" + Resources.rdfsLabel+ "> ?datasetTitle . "
                + " ?dataEvaluationURI <" + Resources.rdfTypeLabel + "> <" + Resources.dataEvaluationLabel + "> . "
                + " ?dataEvaluationURI <" + Resources.describes + "> ?specimenURI . "
                + " ?dataEvaluationURI  <" + Resources.assignedDimension + "> ?dimensionURI .  "
                + " ?dataEvaluationURI <" + Resources.rdfsLabel+ "> ?dataEvaluationLabel . "
                + " OPTIONAL { ?dataEvaluationURI  <" + Resources.hasTimespan + "> ?date .} "
                + " ?specimenURI <" + Resources.belongsTo + "> ?speciesURI . "
                + " ?specimenURI <" + Resources.rdfTypeLabel + "> <" + Resources.specimenLabel + "> . "
                + " ?specimenURI <" + Resources.rdfsLabel+ "> ?specimenName. "
                + " ?dataEvaluationURI  <" + Resources.carriedOutBy + "> ?actorURI . "
                + "?actorURI <" + Resources.rdfsLabel + "> ?actorName . "
                + " OPTIONAL { ?dataEvaluationURI  <" + Resources.hasNote + "> ?description . } "
                + " OPTIONAL { ?publicationURI  <" + Resources.refersTo + "> ?dataEvaluationURI . "
                + " ?publicationURI <" + Resources.rdfTypeLabel + "> <" + Resources.publicationLabel + "> . "
                + " ?publicationURI <" + Resources.rdfsLabel + "> ?publicationName. }  "
                + " ?dimensionURI <" + Resources.rdfTypeLabel + "> <" + Resources.dimensionLabel + "> . "
                //                          +" ?dimensionTypeURI <"+Resources.rdfTypeLabel+"> <"+Resources.dimensionTypeLabel+">. "
                + " ?dimensionTypeURI <" + Resources.rdfsLabel + "> ?dimensionName. "
                + " ?dimensionURI <" + Resources.hasType + "> ?dimensionTypeURI. "
                + " OPTIONAL { ?dimensionURI <" + Resources.hasValue + "> ?value. } "
                + " OPTIONAL { ?dimensionURI <" + Resources.hasUnit + "> ?unit.  } "
                //                          +" ?speciesURI <"+Resources.rdfTypeLabel+"> <"+Resources.speciesLabel+"> . "
                + " ?speciesURI <" + Resources.rdfsLabel + "> ?speciesName. "
                + " FILTER regex(?speciesName,'" + species + "',\"i\") "
                + " FILTER regex(?datasetURI,'" + datasetURI + "',\"i\")  "
                + " FILTER regex(?dimensionName,'" + dimension + "',\"i\")}"
                + " LIMIT " + limit
                + " OFFSET " + offset;

        logger.debug("Submitting the query: \"" + queryString + "\"");
        List<BindingSet> sparqlresults = this.repoManager.query(queryString);
        logger.debug("The query returned " + sparqlresults.size() + " results");
        //Map<String, StatsStruct> map = new HashMap<>();
        List<StatsStruct> results = new ArrayList();
        for (BindingSet result : sparqlresults) {
           // if (!map.containsKey(result.getValue("dataEvaluationURI").stringValue())) {
                StatsStruct struct = new StatsStruct().withDataEvaluationURI(result.getValue("dataEvaluationURI").stringValue())
                        .withDataEvaluation(result.getValue("dataEvaluationLabel").stringValue())
                        .withDatasetURI(result.getValue("datasetURI").stringValue())
                        .withDatasetName(result.getValue("datasetTitle").stringValue())
                        .withDimensionName(result.getValue("dimensionName").stringValue())
                        .withDimensionType(result.getValue("dimensionTypeURI").stringValue())
                        .withDimensionURI(result.getValue("dimensionURI").stringValue())
                        .withSpeciesName(result.getValue("speciesName").stringValue())
                        .withSpecimenURI(result.getValue("specimenURI").stringValue())
                        .withSpecimenName(result.getValue("specimenName").stringValue())
                        .withActor(result.getValue("actorURI").stringValue(), result.getValue("actorName").stringValue())
                        .withSpeciesURI(result.getValue("speciesURI").stringValue());
                if (result.getValue("date") != null) {
                    struct.withTimeSpan(result.getValue("date").stringValue());
                }
                if (result.getValue("value") != null) {
                    struct.withDimensionValue(result.getValue("value").stringValue());
                }
                if (result.getValue("unit") != null) {
                    struct.withDimensionUnit(result.getValue("unit").stringValue());
                }
                if (result.getValue("description") != null) {
                    struct.withDescription(result.getValue("description").stringValue());
                }
                if (result.getValue("publicationName") != null) {
                    struct.withPublicationName(result.getValue("publicationName").stringValue());
                }
                if (result.getValue("publicationURI") != null) {
                    struct.withPublicationURI(result.getValue("publicationURI").stringValue());
                }
                results.add(struct);
//                map.put(struct.getDataEvaluationURI(), struct);
//            } else {
//                StatsStruct struct = map.get(result.getValue("dataEvaluationURI").stringValue());
//                struct.withActor(result.getValue("actorURI").stringValue(), result.getValue("actorName").stringValue());
//                map.put(struct.getDataEvaluationURI(), struct);
//            }
        }
        return results;
       // return new ArrayList<>(map.values());
    }

    public List<SpecimenStruct> searchSpecimen(String specimen, String species, String collection, String datasetURI,String repositoryGraph) throws QueryExecutionException {
        String queryString = "SELECT DISTINCT ?transformationEventURI ?transformationEventLabel ?methodName ?methodURI"
                + "  ?actorURI ?actorName ?individualURI ?speciesName ?speciesURI ?specimenURI ?specimenName "
                + " ?date ?collectionName ?collectionURI ?datasetTitle ?datasetURI "
                + "FROM <" + repositoryGraph + "> "
                + "WHERE{  "
                + "?datasetURI <" + Resources.rdfTypeLabel + "> <" + Resources.datasetLabel + "> .  "
                + "?datasetURI <" + Resources.refersTo + "> ?specimenURI .  "
                + "?datasetURI <" + Resources.rdfsLabel + "> ?datasetTitle .  "
                + "?specimenURI <" + Resources.formsPartOf + "> ?collectionURI .  "
                + "?collectionURI <" + Resources.rdfTypeLabel + "> <" + Resources.collectionLabel + "> .  "
                + "?collectionURI <" + Resources.rdfsLabel + "> ?collectionName .  "
                + "?specimenURI <" + Resources.belongsTo + "> ?speciesURI .  "
                + "?speciesURI <" + Resources.rdfTypeLabel + "> <" + Resources.speciesLabel + "> .  "
                + "?speciesURI <" + Resources.rdfsLabel+ "> ?speciesName .  "
                + " ?transformationEventURI <" + Resources.rdfTypeLabel + "> <" + Resources.transformationEventLabel + "> .  "
                + " ?transformationEventURI <" + Resources.transformed + "> ?individualURI .  "
                + " ?transformationEventURI <" + Resources.resultedIn + "> ?specimenURI . "
                + " ?transformationEventURI <" + Resources.rdfsLabel+ "> ?transformationEventLabel .  "
                + " OPTIONAL { ?transformationEventURI <" + Resources.hasTimespan + "> ?date .}  "
                + " OPTIONAL { ?transformationEventURI <" + Resources.usedSpecificTechnique + "> ?methodURI .}  "
                + " OPTIONAL { ?transformationEventURI <" + Resources.carriedOutBy + "> ?actorURI . } "
                //                          +" OPTIONAL { ?actorURI <"+Resources.rdfTypeLabel+"> <"+Resources.actorLabel+"> .  } "
                + " OPTIONAL { ?actorURI <" + Resources.rdfsLabel+ "> ?actorName.}  "
                //                          +" OPTIONAL { ?methodURI <"+Resources.rdfTypeLabel+"> <"+Resources.+"> . "
                + " OPTIONAL { ?methodURI<" + Resources.rdfsLabel+ "> ?methodName.}  "
                + " ?individualURI <" + Resources.rdfTypeLabel + "> <" + Resources.bioticElementLabel + "> .  "
                //                          +" ?individualURI <"+Resources.isIdentifiedBy+"> ?specimenName. }"
                //SPECIEEEEEEES HEREE
                + " ?specimenURI <" + Resources.rdfTypeLabel + "> <" + Resources.specimenLabel + "> .  "
                + " ?specimenURI <" + Resources.rdfsLabel+ "> ?specimenName.  "
                + " FILTER regex(?collectionName,'" + collection + "',\"i\") "
                + " FILTER regex(?specimenName,'" + specimen + "',\"i\") "
                + " FILTER regex(?datasetURI,'" + datasetURI + "',\"i\")  "
                + " FILTER regex(?speciesName,'" + species + "',\"i\")}";

        logger.debug("Submitting the query: \"" + queryString + "\"");
        List<BindingSet> sparqlresults = this.repoManager.query(queryString);
        logger.debug("The query returned " + sparqlresults.size() + " results");
        Map<String, SpecimenStruct> map = new HashMap<>();
        for (BindingSet result : sparqlresults) {
            if (!map.containsKey(result.getValue("specimenURI").stringValue())) {
                SpecimenStruct struct = new SpecimenStruct().withTransformationEventURI(result.getValue("transformationEventURI").stringValue())
                        .withTransformationEvent(result.getValue("transformationEventLabel").stringValue())
                        .withSpecimenName(result.getValue("specimenName").stringValue())
                        .withSpecimenURI(result.getValue("specimenURI").stringValue())
                        .withIndividualURI(result.getValue("individualURI").stringValue())
                        .withDatasetURI(result.getValue("datasetURI").stringValue())
                        .withDatasetName(result.getValue("datasetTitle").stringValue())
                        .withCollectionURI(result.getValue("collectionURI").stringValue())
                        .withCollectionName(result.getValue("collectionName").stringValue())
                        .withSpeciesName(result.getValue("speciesName").stringValue())
                        .withSpeciesURI(result.getValue("specimenURI").stringValue());
                if (result.getValue("date") != null) {
                    struct.withTimeSpan(result.getValue("date").stringValue());
                }
                if (result.getValue("actorName") != null && result.getValue("actorURI") != null) {
                    struct.withActor(result.getValue("actorURI").stringValue(), result.getValue("actorName").stringValue());
                }
                if (result.getValue("methodName") != null) {
                    struct.withMethodName(result.getValue("methodName").stringValue());
                }
                if (result.getValue("methodURI") != null) {
                    struct.withMethodURI(result.getValue("methodURI").stringValue());
                }
                map.put(struct.getSpecimenURI(), struct);
            } else {
                SpecimenStruct struct = map.get(result.getValue("specimenURI").stringValue());
                struct.withActor(result.getValue("actorURI").stringValue(), result.getValue("actorName").stringValue());
                map.put(struct.getSpecimenURI(), struct);
            }
        }
        return new ArrayList<>(map.values());
    }

     public List<SpecimenStruct> searchSpecimen(String specimen, String species, String collection, String datasetURI, int offset, int limit, String repositoryGraph) throws QueryExecutionException {
        String queryString = "SELECT DISTINCT ?transformationEventURI ?transformationEventLabel ?methodName ?methodURI"
                + "  ?actorURI ?actorName ?individualURI ?speciesName ?speciesURI ?specimenURI ?specimenName "
                + " ?date ?collectionName ?collectionURI ?datasetTitle ?datasetURI "
                + "FROM <" + repositoryGraph + "> "
                + "WHERE{  "
                + "?datasetURI <" + Resources.rdfTypeLabel + "> <" + Resources.datasetLabel + "> .  "
                + "?datasetURI <" + Resources.refersTo + "> ?specimenURI .  "
                + "?datasetURI <" + Resources.rdfsLabel + "> ?datasetTitle .  "
                + "?specimenURI <" + Resources.formsPartOf + "> ?collectionURI .  "
                + "?collectionURI <" + Resources.rdfTypeLabel + "> <" + Resources.collectionLabel + "> .  "
                + "?collectionURI <" + Resources.rdfsLabel + "> ?collectionName .  "
                + "?specimenURI <" + Resources.belongsTo + "> ?speciesURI .  "
                + "?speciesURI <" + Resources.rdfTypeLabel + "> <" + Resources.speciesLabel + "> .  "
                + "?speciesURI <" + Resources.rdfsLabel+ "> ?speciesName .  "
                + " ?transformationEventURI <" + Resources.rdfTypeLabel + "> <" + Resources.transformationEventLabel + "> .  "
                + " ?transformationEventURI <" + Resources.transformed + "> ?individualURI .  "
                + " ?transformationEventURI <" + Resources.resultedIn + "> ?specimenURI . "
                + " ?transformationEventURI <" + Resources.rdfsLabel+ "> ?transformationEventLabel .  "
                + " OPTIONAL { ?transformationEventURI <" + Resources.hasTimespan + "> ?date .}  "
                + " OPTIONAL { ?transformationEventURI <" + Resources.usedSpecificTechnique + "> ?methodURI .}  "
                + " OPTIONAL { ?transformationEventURI <" + Resources.carriedOutBy + "> ?actorURI . } "
                //                          +" OPTIONAL { ?actorURI <"+Resources.rdfTypeLabel+"> <"+Resources.actorLabel+"> .  } "
                + " OPTIONAL { ?actorURI <" + Resources.rdfsLabel+ "> ?actorName.}  "
                //                          +" OPTIONAL { ?methodURI <"+Resources.rdfTypeLabel+"> <"+Resources.+"> . "
                + " OPTIONAL { ?methodURI<" + Resources.rdfsLabel+ "> ?methodName.}  "
                + " ?individualURI <" + Resources.rdfTypeLabel + "> <" + Resources.bioticElementLabel + "> .  "
                //                          +" ?individualURI <"+Resources.isIdentifiedBy+"> ?specimenName. }"
                //SPECIEEEEEEES HEREE
                + " ?specimenURI <" + Resources.rdfTypeLabel + "> <" + Resources.specimenLabel + "> .  "
                + " ?specimenURI <" + Resources.rdfsLabel+ "> ?specimenName.  "
                + " FILTER regex(?collectionName,'" + collection + "',\"i\") "
                + " FILTER regex(?specimenName,'" + specimen + "',\"i\") "
                + " FILTER regex(?datasetURI,'" + datasetURI + "',\"i\")  "
                + " FILTER regex(?speciesName,'" + species + "',\"i\")}"
                + " LIMIT " + limit
                + " OFFSET " + offset;

        logger.debug("Submitting the query: \"" + queryString + "\"");
        List<BindingSet> sparqlresults = this.repoManager.query(queryString);
        logger.debug("The query returned " + sparqlresults.size() + " results");
        Map<String, SpecimenStruct> map = new HashMap<>();
        List<SpecimenStruct> results = new ArrayList();
        for (BindingSet result : sparqlresults) {
          //  if (!map.containsKey(result.getValue("specimenURI").stringValue())) {
                SpecimenStruct struct = new SpecimenStruct().withTransformationEventURI(result.getValue("transformationEventURI").stringValue())
                        .withTransformationEvent(result.getValue("transformationEventLabel").stringValue())
                        .withSpecimenName(result.getValue("specimenName").stringValue())
                        .withSpecimenURI(result.getValue("specimenURI").stringValue())
                        .withIndividualURI(result.getValue("individualURI").stringValue())
                        .withDatasetURI(result.getValue("datasetURI").stringValue())
                        .withDatasetName(result.getValue("datasetTitle").stringValue())
                        .withCollectionURI(result.getValue("collectionURI").stringValue())
                        .withCollectionName(result.getValue("collectionName").stringValue())
                        .withSpeciesName(result.getValue("speciesName").stringValue())
                        .withSpeciesURI(result.getValue("specimenURI").stringValue());
                if (result.getValue("date") != null) {
                    struct.withTimeSpan(result.getValue("date").stringValue());
                }
                if (result.getValue("actorName") != null && result.getValue("actorURI") != null) {
                    struct.withActor(result.getValue("actorURI").stringValue(), result.getValue("actorName").stringValue());
                }
                if (result.getValue("methodName") != null) {
                    struct.withMethodName(result.getValue("methodName").stringValue());
                }
                if (result.getValue("methodURI") != null) {
                    struct.withMethodURI(result.getValue("methodURI").stringValue());
                }
                results.add(struct);
//                map.put(struct.getSpecimenURI(), struct);
//            } else {
//                SpecimenStruct struct = map.get(result.getValue("specimenURI").stringValue());
//                struct.withActor(result.getValue("actorURI").stringValue(), result.getValue("actorName").stringValue());
//                map.put(struct.getSpecimenURI(), struct);
//            }
        }
        return results;
      //  return new ArrayList<>(map.values());
    }

    public List<SpecimenCollectionStruct> searchSpecimenCollection(String collectionName, String ownerName,String datasetURI, String repositoryGraph) throws QueryExecutionException {
        String queryString = "SELECT DISTINCT ?collectionName ?collectionURI ?datasetURI ?datasetName  "
                + "?ownerURI ?ownerName ?keeperURI ?keeperName  "
                + "?curatorURI ?curatorName"
                + "?contactPoint ?creationEvent ?creationEventLabel ?creatorURI ?creatorName ?creationDate  ?description  "
                + "FROM <" + repositoryGraph + "> "
                + "WHERE{ "
                + "?collectionURI <" + Resources.rdfTypeLabel + "> <" + Resources.collectionLabel + "> . "
                + "?collectionURI <" + Resources.rdfsLabel + "> ?collectionName . "
                + "?collectionURI <" + Resources.hasCurrentOwner + "> ?ownerURI . "
                + "?ownerURI <" + Resources.rdfTypeLabel + "> <" + Resources.actorLabel + "> . "
                + "?ownerURI <" + Resources.rdfsLabel + "> ?ownerName . "
                + "?collectionURI <" + Resources.hasCurator + "> ?curatorURI. "
                + "?curatorURI <" + Resources.rdfTypeLabel + "> <" + Resources.actorLabel + "> . "
                + "?curatorURI <" + Resources.rdfsLabel + "> ?curatorName.  "
                + "?curatorURI <" + Resources.hasContactPoint + "> ?contactPoint.  "
                + "?datasetURI <" + Resources.rdfTypeLabel + "> <" + Resources.datasetLabel + "> . "
                + "?datasetURI <" + Resources.rdfsLabel + "> ?datasetName . "
                + "?datasetURI <" + Resources.refersTo + "> ?collectionURI . "
                + "OPTIONAL{ ?collectionURI <" + Resources.hasCurrentKeeper + "> ?keeperURI . "
                + "?keeperURI <" + Resources.rdfTypeLabel + "> <" + Resources.actorLabel + "> }. "
                + "OPTIONAL{ ?keeperURI <" + Resources.rdfsLabel+ "> ?keeperName }. "
                + "OPTIONAL{ ?collectionURI <" + Resources.wasCreatedBy + "> ?creationEvent . "
                + "?creationEvent <" +  Resources.rdfsLabel + "> ?creationEventLabel }. "
                + "OPTIONAL{ ?creationEvent <" + Resources.carriedOutBy + "> ?creatorURI . "
                + "?creatorURI <" + Resources.rdfTypeLabel + "> <" + Resources.actorLabel + "> }. "
                + "OPTIONAL{ ?creatorURI <" + Resources.rdfsLabel + "> ?creatorName }. "
                + "OPTIONAL{ ?creationEvent <" + Resources.hasTimespan + "> ?creationDate }. "
              
                + "OPTIONAL{ ?collectionURI <" + Resources.hasNote + "> ?description}. "
                + "FILTER regex(?ownerName,'" + ownerName + "',\"i\") "
                + " FILTER regex(?datasetURI,'" + datasetURI + "',\"i\")  "
                + "FILTER regex(?collectionName,'" + collectionName + "',\"i\")}";

        logger.debug("Submitting the query: \"" + queryString + "\"");
        List<BindingSet> sparqlresults = this.repoManager.query(queryString);
        logger.debug("The result returned " + sparqlresults.size() + " results");
        List<SpecimenCollectionStruct> results = new ArrayList<>();
        for (BindingSet result : sparqlresults) {
            SpecimenCollectionStruct struct = new SpecimenCollectionStruct()
                    .withDatasetURI(result.getValue("datasetURI").stringValue())
                    .withDatasetName(result.getValue("datasetName").stringValue())
                    .withCollectionURI(result.getValue("collectionURI").stringValue())
                    .withCollectionName(result.getValue("collectionName").stringValue())
                    .withOwnerURI(result.getValue("ownerURI").stringValue())
                    .withOwnerName(result.getValue("ownerName").stringValue());
            if (result.getValue("keeperURI") != null) {
                struct.withKeeperURI(result.getValue("keeperURI").stringValue());
            }
            if (result.getValue("keeperName") != null) {
                struct.withKeeperName(result.getValue("keeperName").stringValue());
            }
            if (result.getValue("curatorURI") != null) {
                struct.withCuratorURI(result.getValue("curatorURI").stringValue());
            }
            if (result.getValue("curatorName") != null) {
                struct.withCuratorName(result.getValue("curatorName").stringValue());
            }
            if (result.getValue("contactPoint") != null) {
                struct.withContactPoint(result.getValue("contactPoint").stringValue());
            }

           

            if (result.getValue("creatorName") != null) {
                struct.withCreatorName(result.getValue("creatorName").stringValue());
            }
            if (result.getValue("creatorURI") != null) {
                struct.withCreatorURI(result.getValue("creatorURI").stringValue());
            }

            if (result.getValue("creationDate") != null) {
                struct.withTimespan(result.getValue("creationDate").stringValue());
            }

            if (result.getValue("creationEvent") != null) {
                struct.withCreationEventURI(result.getValue("creationEvent").stringValue());
                struct.withCreationEvent(result.getValue("creationEventLabel").stringValue());
            }


            if (result.getValue("description") != null) {
                struct.withNote(result.getValue("description").stringValue());
            }

            results.add(struct);
        }
        return results;
    }

     public List<SpecimenCollectionStruct> searchSpecimenCollection(String collectionName, String ownerName,String datasetURI, int offset, int limit, String repositoryGraph) throws QueryExecutionException {
        String queryString = "SELECT DISTINCT ?collectionName ?collectionURI ?datasetURI ?datasetName  "
                + "?ownerURI ?ownerName ?keeperURI ?keeperName  "
                + "?curatorURI ?curatorName"
                + "?contactPoint ?creationEvent ?creationEventLabel ?creatorURI ?creatorName ?creationDate  ?description  "
                + "FROM <" + repositoryGraph + "> "
                + "WHERE{ "
                + "?collectionURI <" + Resources.rdfTypeLabel + "> <" + Resources.collectionLabel + "> . "
                + "?collectionURI <" + Resources.rdfsLabel + "> ?collectionName . "
                + "?collectionURI <" + Resources.hasCurrentOwner + "> ?ownerURI . "
                + "?ownerURI <" + Resources.rdfTypeLabel + "> <" + Resources.actorLabel + "> . "
                + "?ownerURI <" + Resources.rdfsLabel + "> ?ownerName . "
                + "?collectionURI <" + Resources.hasCurator + "> ?curatorURI. "
                + "?curatorURI <" + Resources.rdfTypeLabel + "> <" + Resources.actorLabel + "> . "
                + "?curatorURI <" + Resources.rdfsLabel + "> ?curatorName.  "
                + "?curatorURI <" + Resources.hasContactPoint + "> ?contactPoint.  "
                + "?datasetURI <" + Resources.rdfTypeLabel + "> <" + Resources.datasetLabel + "> . "
                + "?datasetURI <" + Resources.rdfsLabel + "> ?datasetName . "
                + "?datasetURI <" + Resources.refersTo + "> ?collectionURI . "
                + "OPTIONAL{ ?collectionURI <" + Resources.hasCurrentKeeper + "> ?keeperURI . "
                + "?keeperURI <" + Resources.rdfTypeLabel + "> <" + Resources.actorLabel + "> }. "
                + "OPTIONAL{ ?keeperURI <" + Resources.rdfsLabel+ "> ?keeperName }. "
                + "OPTIONAL{ ?collectionURI <" + Resources.wasCreatedBy + "> ?creationEvent . "
                + "?creationEvent <" +  Resources.rdfsLabel + "> ?creationEventLabel }. "
                + "OPTIONAL{ ?creationEvent <" + Resources.carriedOutBy + "> ?creatorURI . "
                + "?creatorURI <" + Resources.rdfTypeLabel + "> <" + Resources.actorLabel + "> }. "
                + "OPTIONAL{ ?creatorURI <" + Resources.rdfsLabel + "> ?creatorName }. "
                + "OPTIONAL{ ?creationEvent <" + Resources.hasTimespan + "> ?creationDate }. "
              
                + "OPTIONAL{ ?collectionURI <" + Resources.hasNote + "> ?description}. "
                + "FILTER regex(?ownerName,'" + ownerName + "',\"i\") "
                + " FILTER regex(?datasetURI,'" + datasetURI + "',\"i\")  "
                + "FILTER regex(?collectionName,'" + collectionName + "',\"i\")}"
                + " LIMIT " + limit
                + " OFFSET " + offset;

        logger.debug("Submitting the query: \"" + queryString + "\"");
        List<BindingSet> sparqlresults = this.repoManager.query(queryString);
        logger.debug("The result returned " + sparqlresults.size() + " results");
        List<SpecimenCollectionStruct> results = new ArrayList<>();
        for (BindingSet result : sparqlresults) {
            SpecimenCollectionStruct struct = new SpecimenCollectionStruct()
                    .withDatasetURI(result.getValue("datasetURI").stringValue())
                    .withDatasetName(result.getValue("datasetName").stringValue())
                    .withCollectionURI(result.getValue("collectionURI").stringValue())
                    .withCollectionName(result.getValue("collectionName").stringValue())
                    .withOwnerURI(result.getValue("ownerURI").stringValue())
                    .withOwnerName(result.getValue("ownerName").stringValue());
            if (result.getValue("keeperURI") != null) {
                struct.withKeeperURI(result.getValue("keeperURI").stringValue());
            }
            if (result.getValue("keeperName") != null) {
                struct.withKeeperName(result.getValue("keeperName").stringValue());
            }
            if (result.getValue("curatorURI") != null) {
                struct.withCuratorURI(result.getValue("curatorURI").stringValue());
            }
            if (result.getValue("curatorName") != null) {
                struct.withCuratorName(result.getValue("curatorName").stringValue());
            }
            if (result.getValue("contactPoint") != null) {
                struct.withContactPoint(result.getValue("contactPoint").stringValue());
            }

           

            if (result.getValue("creatorName") != null) {
                struct.withCreatorName(result.getValue("creatorName").stringValue());
            }
            if (result.getValue("creatorURI") != null) {
                struct.withCreatorURI(result.getValue("creatorURI").stringValue());
            }

            if (result.getValue("creationDate") != null) {
                struct.withTimespan(result.getValue("creationDate").stringValue());
            }

            if (result.getValue("creationEvent") != null) {
                struct.withCreationEventURI(result.getValue("creationEvent").stringValue());
                struct.withCreationEvent(result.getValue("creationEventLabel").stringValue());
            }


            if (result.getValue("description") != null) {
                struct.withNote(result.getValue("description").stringValue());
            }

            results.add(struct);
        }
        return results;
    }
    
    public List<TaxonomyStruct> searchTaxonomy(String species, String genus, String family, String order, String classs, String kingdom, String phylum,
                                               String datasetURI, int offset, int limit, String repositoryGraph) throws QueryExecutionException {
        logger.info("Request for taxonomy search with parameters "
                   +"species: ["+species+"], "
                   +"genus: ["+genus+"], "
                   +"family: ["+family+"], "
                   +"order: ["+order+"], "
                   +"class: ["+classs+"], "
                   +"kingdom: ["+kingdom+"], "
                   +"phylum: ["+phylum+"], "
                   +"datasetURI: ["+datasetURI+"], "
                   +"limit: ["+(limit<0?"N/A":String.valueOf(limit))+"], "
                   +"offset: ["+(offset<0?"N/A":String.valueOf(offset))+"], "
                   +"reposytoryGraph: ["+repositoryGraph+"], ");
        String queryString = "SELECT DISTINCT ?speciesURI ?speciesName ?genusName ?familyName ?orderName ?className ?kingdomName ?phylumName "
                                            +"?genusURI ?familyURI ?orderURI ?classURI ?kingdomURI ?phylumURI ?datasetURI ?datasetName ?scNameIdentifier "
                +"FROM <" + repositoryGraph + "> "
                +"WHERE{ "
                +"?speciesURI <"+Resources.rdfTypeLabel+"> <"+Resources.speciesLabel+">. "
                +"?speciesURI <"+Resources.rdfsLabel+"> ?speciesName. "
                +"?datasetURI <"+Resources.rdfTypeLabel+"> <"+Resources.datasetLabel+">. "
                +"?datasetURI <"+Resources.refersTo+"> ?speciesURI. "
                +"?datasetURI <"+Resources.rdfsLabel+"> ?datasetName. "
                +"OPTIONAL{ "
                    +"?speciesURI <"+Resources.belongsTo+"> ?genusURI. "
                    +"?genusURI <"+ Resources.rdfTypeLabel+"> <"+Resources.genusLabel+">. "
                    +"?genusURI <"+ Resources.rdfsLabel+"> ?genusName. "
                +"} "
                +"OPTIONAL{ "
                    +"?speciesURI <"+ Resources.belongsTo+"> ?familyURI. "
                    +"?familyURI <"+ Resources.rdfTypeLabel+"> <"+Resources.familyLabel+">. "
                    +"?familyURI <"+ Resources.rdfsLabel+"> ?familyName. "
                +"} "
                +"OPTIONAL{ "
                    +"?speciesURI <"+ Resources.belongsTo+"> ?orderURI. "
                    +"?orderURI <"+ Resources.rdfTypeLabel+"> <"+Resources.orderLabel+">. "
                    +"?orderURI <"+ Resources.rdfsLabel+"> ?orderName. "
                +"} "
                +"OPTIONAL{ "
                    +"?speciesURI <"+ Resources.belongsTo+"> ?classURI. "
                    +"?classURI <"+ Resources.rdfTypeLabel+"> <"+Resources.classLabel+">. "
                    +"?classURI <"+ Resources.rdfsLabel+"> ?className. "
                +"} "
                +"OPTIONAL{ "
                    +"?speciesURI <"+ Resources.belongsTo+"> ?phylumURI. "
                    +"?phylumURI <"+ Resources.rdfTypeLabel+"> <"+Resources.phylumLabel+">. "
                    +"?phylumURI <"+ Resources.rdfsLabel+"> ?phylumName. "
                +"} "
                +"OPTIONAL{ "
                    +"?speciesURI <"+ Resources.belongsTo+"> ?kingdomURI. "
                    +"?kingdomURI <"+ Resources.rdfTypeLabel+"> <"+Resources.kingdomLabel+">. "
                    +"?kingdomURI <"+ Resources.rdfsLabel+"> ?kingdomName. "
                +"} "
                +"OPTIONAL{ "
                    +"?speciesURI <"+ Resources.isIdentifiedBy+"> ?scNameIdentifierUri. "
                    +"?scNameIdentifierUri <"+ Resources.rdfTypeLabel+"> <"+Resources.identifierLabel+">. "
                    +"?scNameIdentifierUri <"+ Resources.rdfsLabel+"> ?scNameIdentifier. "
                +"} ";
        
        if(species!=null && !species.isEmpty()){
            queryString+="FILTER CONTAINS(LCASE(?speciesName),\""+species.toLowerCase()+"\"). ";
        }
        if(genus!=null && !genus.isEmpty()){
            queryString+="FILTER CONTAINS(LCASE(?genusName),\""+genus.toLowerCase()+"\"). ";
        }
        if(family!=null && !family.isEmpty()){
            queryString+="FILTER CONTAINS(LCASE(?familyName),\""+family.toLowerCase()+"\"). ";
        }
        if(order!=null && !order.isEmpty()){
            queryString+="FILTER CONTAINS(LCASE(?orderName),\""+order.toLowerCase()+"\"). ";
        }
        if(classs!=null && !classs.isEmpty()){
            queryString+="FILTER CONTAINS(LCASE(?className),\""+classs.toLowerCase()+"\"). ";
        }
        if(phylum!=null && !phylum.isEmpty()){
            queryString+="FILTER CONTAINS(LCASE(?phylumName),\""+phylum.toLowerCase()+"\"). ";
        }
        if(kingdom!=null && !kingdom.isEmpty()){
            queryString+="FILTER CONTAINS(LCASE(?kingdomName),\""+kingdom.toLowerCase()+"\"). ";
        }        
        if(datasetURI!=null && !datasetURI.isEmpty()){
            queryString+="FILTER CONTAINS(LCASE(STR(?datasetURI)),\""+datasetURI.toLowerCase()+"\"). ";
        }
        queryString+="} ";
        if(limit>0 && offset>=0){
            queryString+="LIMIT "+limit+" "
                        +"OFFSET "+offset;
        }
        
        logger.debug("Submitting the query: \"" + queryString + "\"");
        List<BindingSet> sparqlresults = this.repoManager.query(queryString);
        logger.debug("The SPARQL query returned " + sparqlresults.size() + " results (RAW SPARQL results)");
        List<TaxonomyStruct> results = new ArrayList<>();
        for (BindingSet result : sparqlresults) {
            TaxonomyStruct struct = new TaxonomyStruct()
                    .withSpeciesName(result.getValue("speciesName").stringValue())
                    .withSpeciesURI(result.getValue("speciesURI").stringValue())
                    .withDatasetName(result.getValue("datasetName").stringValue())
                    .withDatasetURI(result.getValue("datasetURI").stringValue());
            if(result.getValue("genusURI")!=null && result.getValue("genusName")!=null){
                struct.withGenusName(result.getValue("genusName").stringValue())
                      .withGenusURI(result.getValue("genusURI").stringValue());
            }
            if(result.getValue("familyURI")!=null && result.getValue("familyName")!=null){
                struct.withFamilyName(result.getValue("familyName").stringValue())
                      .withFamilyURI(result.getValue("familyURI").stringValue());
            }
            if(result.getValue("orderURI")!=null && result.getValue("orderName")!=null){
                struct.withOrderName(result.getValue("orderName").stringValue())
                      .withOrderURI(result.getValue("orderURI").stringValue());
            }
            if(result.getValue("classURI")!=null && result.getValue("className")!=null){
                struct.withClassName(result.getValue("className").stringValue())
                      .withClassURI(result.getValue("classURI").stringValue());
            }
            if(result.getValue("phylumURI")!=null && result.getValue("phylumName")!=null){
                struct.withPhylumName(result.getValue("phylumName").stringValue())
                      .withPhylumURI(result.getValue("phylumURI").stringValue());
            }
            if(result.getValue("kingdomName")!=null && result.getValue("kingdomURI")!=null){
                struct.withKingdomName(result.getValue("kingdomName").stringValue())
                      .withKingdomURI(result.getValue("kingdomURI").stringValue());
            }
            if(result.getValue("scNameIdentifier")!=null){
                struct.withScNameId(result.getValue("scNameIdentifier").stringValue());
            }
            results.add(struct);
        }
        logger.debug("The query returned "+results.size()+" taxonomy objects");
        return results;
    }
    
     public List<OutgoingNodeStruct> selectOutgoing(String resourceURI) throws QueryExecutionException {

        String queryString = "SELECT DISTINCT ?predicate ?object ?objectType ?objectName"
                + " WHERE { "
                + "  <" + resourceURI + "> ?predicate ?object ."
                + " OPTIONAL { ?object <"+Resources.rdfsLabel+"> ?objectName } ."
                + " OPTIONAL { ?object a ?objectType }} ";
       
        System.out.println("QUERY"+queryString);
        logger.debug("Submitting the query: \"" + queryString + "\"");
        List<BindingSet> sparqlresults = this.repoManager.query(queryString);
        logger.debug("The result returned " + sparqlresults.size() + " results");
        List<OutgoingNodeStruct> results = new ArrayList<>();
        for (BindingSet result : sparqlresults) {
           
            
            
            OutgoingNodeStruct struct = new OutgoingNodeStruct()
                    .withObject(result.getValue("object").stringValue())
                    .withPredicate(result.getValue("predicate").stringValue());
             if (result.getValue("objectType") != null) {
                    struct.withObjectType(result.getValue("objectType").stringValue());
                }
              if (result.getValue("objectName") != null) {
                    struct.withObjectName(result.getValue("objectName").stringValue());
                }
            results.add(struct);
        }
        return results;
    }
    
     public List<OutgoingNodeStruct> selectOutgoing(String resourceURI, String directoryGraph, String metadataRepositoryGraph) throws QueryExecutionException {

        String queryString = "SELECT DISTINCT ?predicate ?object ?objectType ?objectName"
                + " FROM <"+directoryGraph+"> "
                + " FROM <"+metadataRepositoryGraph+"> "
                + " WHERE { "
                + "  <" + resourceURI + "> ?predicate ?object ."
                + " OPTIONAL { ?object <"+Resources.rdfsLabel+"> ?objectName } ."
                + " OPTIONAL { ?object a ?objectType }} ";
       
        System.out.println("QUERY"+queryString);
        logger.debug("Submitting the query: \"" + queryString + "\"");
        List<BindingSet> sparqlresults = this.repoManager.query(queryString);
        logger.debug("The result returned " + sparqlresults.size() + " results");
        List<OutgoingNodeStruct> results = new ArrayList<>();
        for (BindingSet result : sparqlresults) {
           
            
            
            OutgoingNodeStruct struct = new OutgoingNodeStruct()
                    .withObject(result.getValue("object").stringValue())
                    .withPredicate(result.getValue("predicate").stringValue());
             if (result.getValue("objectType") != null) {
                    struct.withObjectType(result.getValue("objectType").stringValue());
                }
              if (result.getValue("objectName") != null) {
                    struct.withObjectName(result.getValue("objectName").stringValue());
                }
            results.add(struct);
        }
        return results;
    }
     
      public List<IncomingNodeStruct> selectIncoming(String resourceURI) throws QueryExecutionException {

        String queryString = "SELECT DISTINCT ?predicate ?subject ?subjectType ?subjectName"
                + " WHERE { "
                + " ?subject ?predicate <" + resourceURI + "> ."
                + " OPTIONAL { ?subject <" + Resources.rdfsLabel+"> ?subjectName } ."
                + " OPTIONAL { ?subject a ?subjectType }} ";
       

        logger.debug("Submitting the query: \"" + queryString + "\"");
        List<BindingSet> sparqlresults = this.repoManager.query(queryString);
        logger.debug("The result returned " + sparqlresults.size() + " results");
        List<IncomingNodeStruct> results = new ArrayList<>();
        for (BindingSet result : sparqlresults) {
           
            
            
            IncomingNodeStruct struct = new IncomingNodeStruct()
                    .withSubject(result.getValue("subject").stringValue())
                    .withPredicate(result.getValue("predicate").stringValue());
             if (result.getValue("subjectType") != null) {
                    struct.withSubjectType(result.getValue("subjectType").stringValue());
                }
              if (result.getValue("subjectName") != null) {
                    struct.withSubjectName(result.getValue("subjectName").stringValue());
                }
            results.add(struct);
        }
        return results;
    }
      
      public List<IncomingNodeStruct> selectIncoming(String resourceURI, String directoryGraph, String metadataRepositoryGraph) throws QueryExecutionException {

        String queryString = "SELECT DISTINCT ?predicate ?subject ?subjectType ?subjectName"
                + " FROM <"+directoryGraph+"> "
                + " FROM <"+metadataRepositoryGraph+"> "
                + " WHERE { "
                + " ?subject ?predicate <" + resourceURI + "> ."
                + " OPTIONAL { ?subject <" + Resources.rdfsLabel+"> ?subjectName } ."
                + " OPTIONAL { ?subject a ?subjectType }} ";
       

        logger.debug("Submitting the query: \"" + queryString + "\"");
        List<BindingSet> sparqlresults = this.repoManager.query(queryString);
        logger.debug("The result returned " + sparqlresults.size() + " results");
        List<IncomingNodeStruct> results = new ArrayList<>();
        for (BindingSet result : sparqlresults) {
           
            
            
            IncomingNodeStruct struct = new IncomingNodeStruct()
                    .withSubject(result.getValue("subject").stringValue())
                    .withPredicate(result.getValue("predicate").stringValue());
             if (result.getValue("subjectType") != null) {
                    struct.withSubjectType(result.getValue("subjectType").stringValue());
                }
              if (result.getValue("subjectName") != null) {
                    struct.withSubjectName(result.getValue("subjectName").stringValue());
                }
            results.add(struct);
        }
        return results;
    }
    

    public String produceText(String scName, String browseURL, String repositoryGraph, String datasetsGraph) throws QueryExecutionException {
        logger.debug("Request for producing text with scientific name "+scName+" in graphs "+repositoryGraph+" and "+datasetsGraph);
         
        String scNameQuery="SELECT ?resource_uri ?resource_type ?resource_label "
                          +"FROM <"+repositoryGraph+"> "
                          +"WHERE{ "
                          +"?resource_uri <"+Resources.rdfTypeLabel+"> ?resource_type. "
                          +"?resource_uri <"+Resources.rdfsLabel+"> ?resource_label. "
                          +"FILTER(LCASE(?resource_label)=\""+scName.toLowerCase()+"\"). "
                          +"FILTER(?resource_type=<"+Resources.speciesLabel+"> || "
                                +"?resource_type=<"+Resources.genusLabel+"> || "
                                +"?resource_type=<"+Resources.familyLabel+"> || "
                                +"?resource_type=<"+Resources.orderLabel+"> || "
                                +"?resource_type=<"+Resources.classLabel+"> || "
                                +"?resource_type=<"+Resources.phylumLabel+"> || "
                                +"?resource_type=<"+Resources.kingdomLabel+">)"
                          +"}";
        List<BindingSet> scNameResults=this.repoManager.query(scNameQuery);
        if(!scNameResults.isEmpty()){
            String textMessage="";
            BindingSet result=scNameResults.get(0);
            String nameURI=result.getValue("resource_uri").stringValue();
            String nameValid=result.getValue("resource_label").stringValue();
            String nameType=result.getValue("resource_type").stringValue();
            
            textMessage="The scientific name <b><i>"+nameValid+"</i></b> is of type <b>"+eu.core.utils.Utils.getUserFriendlyClassName(nameType)+"</b>.<br>";
            String previousUri=nameURI;
            while(true){
                String taxonomyQuery="SELECT ?resource_uri ?resource_type ?resource_label "
                                    +"FROM <"+repositoryGraph+"> "
                                    +"WHERE{ "
                                    +"<"+previousUri+"> <"+Resources.belongsTo+"> ?resource_uri. "
                                    +"?resource_uri <"+Resources.rdfTypeLabel+"> ?resource_type. "
                                    +"?resource_uri <"+Resources.rdfsLabel+"> ?resource_label. "
                                    +"}";
                logger.debug("Retrieve taxonomy query: "+taxonomyQuery);
                List<BindingSet> taxaResults=this.repoManager.query(taxonomyQuery);
                if(taxaResults.isEmpty()){
                   break; 
                }else{
                    result=taxaResults.get(0);
                    String resourceURI=result.getValue("resource_uri").stringValue();
                    String resourceName=result.getValue("resource_label").stringValue();
                    String resourceType=result.getValue("resource_type").stringValue();
                    textMessage+="It belongs to <b><i>"+resourceName+"</i></b>(type: <b>"+eu.core.utils.Utils.getUserFriendlyClassName(resourceType)+"</b>).<br>";
                    previousUri=resourceURI;
                }
            }
            
            String relatedDatasetQuery="SELECT DISTINCT ?dataset_name "
                                          +"FROM <"+repositoryGraph+"> "
                                          +"FROM <"+datasetsGraph+"> "
                                          +"WHERE{ "
                                          +"?dataset_uri <"+Resources.rdfTypeLabel+"> <"+Resources.datasetLabel+">. "
                                          +"?dataset_uri <"+Resources.hasPreferredIdentifier+"> ?dataset_name. "
                                          +"?dataset_uri <"+Resources.refersTo+"> <"+nameURI+">. "
                                          +"}";
            logger.debug("Query to retrieve related datasets: "+relatedDatasetQuery);
            List<BindingSet> datasetResults=this.repoManager.query(relatedDatasetQuery);
            String relatedDatasets="The scientific name is not referred from any datasets.";
            if(!datasetResults.isEmpty()){
                relatedDatasets="The scientific name is referred from the following datasets: <ul>";
                for(BindingSet dResult : datasetResults){
                    relatedDatasets+="<li>"+dResult.getValue("dataset_name").stringValue()+"</li>";
                }
                relatedDatasets+="</ul><br>";
            }
            String relatedMicroctScannQuery="SELECT DISTINCT ?microct_scan_label "
                                          +"FROM <"+repositoryGraph+"> "
                                          +"WHERE{ "
                                          +"?microct_scan_uri <"+Resources.rdfTypeLabel+"> <"+Resources.digitizationProcessLabel+">. "
                                          +"?microct_scan_uri <"+Resources.rdfsLabel+"> ?microct_scan_label. "
                                          +"?microct_scan_uri <"+Resources.digitized+"> ?specimen_uri. "
                                          +"?specimen_uri <"+Resources.rdfTypeLabel+"> <"+Resources.specimenLabel+">. "
                                          +"?specimen_uri <"+Resources.belongsTo+"> ?species_uri. "
                                          +"?species_uri <"+Resources.rdfTypeLabel+"> <"+Resources.speciesLabel+">. "
                                          +"FILTER(?species_uri=<"+nameURI+">) "
                                          +"}";
            logger.debug("Query to retrieve related microCT Scans: "+relatedMicroctScannQuery);
            List<BindingSet> microctResults=this.repoManager.query(relatedMicroctScannQuery);
            String relatedMicroCtScans="The scientific names does not appear in any MicroCT Scan.";
            if(!microctResults.isEmpty()){
                relatedMicroCtScans="The scientific names appear in the following MicroCT Scans: <ul>";
                for(BindingSet mctResult : microctResults){
                    relatedMicroCtScans+="<li>"+mctResult.getValue("microct_scan_label").stringValue()+"</li>";
                }
                relatedMicroCtScans+="</ul><br>";
            }
                
        textMessage+=relatedDatasets+relatedMicroCtScans;
        return textMessage;
            
            
        
        }else{
            return "No information were found for the scientific name <b>"+scName+"</b>.";
        }
            
            
            
         
//         List<TaxonomyStruct> TaxonomyResults = searchTaxonomy(scName,"","","",
//                   "","","","",0,0,repositoryGraph);
//           
//            String TaxonomyInfo = "";
//            String relatedDatasets="";
//            
//            if(!TaxonomyResults.isEmpty()) {
//                String speciesURI = TaxonomyResults.get(0).getSpeciesURI();
//
//                String genus = TaxonomyResults.get(0).getGenusName();
//                String genusURI = TaxonomyResults.get(0).getGenusURI();
//
//                String family = TaxonomyResults.get(0).getFamilyName();
//                String familyURI = TaxonomyResults.get(0).getFamilyURI();
//
//                String classN = TaxonomyResults.get(0).getClassName();
//                String classURI = TaxonomyResults.get(0).getClassURI();
//
//                String phylum= TaxonomyResults.get(0).getPhylumName();
//                String phylumURI = TaxonomyResults.get(0).getPhylumURI();
//
//                String kingdom = TaxonomyResults.get(0).getKingdomName();
//                String kingdomURI = TaxonomyResults.get(0).getKingdomURI();
//
//                if(!genus.isEmpty()){
//                    if(browseURL==null || browseURL.isEmpty()){
//                        TaxonomyInfo += "The <b><i>"+scName+"</i></b> species belongs to the <b><i>"+genus+"</i></b> genus";
//                    }else{
//                        TaxonomyInfo += "The <b><i><a href=\""+browseURL+speciesURI+"\">"+species+"</a></i></b> species belongs to the <b><i><a href=\""+browseURL+genusURI+"\">"+genus+"</a></i></b> genus";
//                    }
//                }
//                if(!family.isEmpty()){
//                    if(browseURL==null || browseURL.isEmpty()){
//                        TaxonomyInfo += ", to the <b><i>"+family+"</i></b> family";
//                    }else{
//                        TaxonomyInfo += ", to the <b><i><a href=\""+browseURL+familyURI+"\">"+family+"</a></i></b> family";
//                    }
//                }
//                if(!classN.isEmpty()){
//                    if(browseURL==null || browseURL.isEmpty()){
//                        TaxonomyInfo += ", to the <b><i>"+classN+"</i></b> class";
//                    }else{
//                        TaxonomyInfo += ", to the <b><i><a href=\""+browseURL+classURI+"\">"+classN+"</a></i></b> class";
//                    }
//                }
//                if(!phylum.isEmpty()){
//                    if(browseURL==null || browseURL.isEmpty()){
//                        TaxonomyInfo += ", to the <b><i>"+phylum+"</i></b> phylum";
//                    }else{
//                        TaxonomyInfo += ", to the <b><i><a href=\""+browseURL+phylumURI+"\">"+phylum+"</a></i></b> phylum";
//                    }
//                }
//                if(!kingdom.isEmpty()){
//                    if(browseURL==null || browseURL.isEmpty()){
//                        TaxonomyInfo += ", to the <b><i>"+kingdom+"</i></b> kingdom";
//                    }else{
//                        TaxonomyInfo += ", to the <b><i><a href=\""+browseURL+kingdomURI+"\">"+kingdom+"</a></i></b> kingdom";                
//                    }
//                }
//                TaxonomyInfo += ".<br>";
                
//                
//            }
            
//            List<ScientificNamingStruct> SNameResults = searchScientificNaming("","","","",species,0,0,repositoryGraph);
//          
//            String SNameInfo = "";
//            
//            if(!SNameResults.isEmpty()) {
//            String speciesURI = SNameResults.get(0).getSpeciesURI();
//            String actor = SNameResults.get(0).getActors().get(0).getValue();
//            String actorURI = SNameResults.get(0).getActors().get(0).getKey();
//            String date = SNameResults.get(0).getTimeSpan();
//
//     
//            if(!actor.isEmpty())
//                SNameInfo += "<b><i><a href=\""+browseURL+speciesURI+"\">"+species+"</a></i></b> was discovered by <b><i><a href=\""+browseURL+actorURI+"\">"+actor+"</a></i></b>";
//            
//            if(!date.isEmpty())
//                SNameInfo += " on "+date;
//
//            SNameInfo += ".";
//            }
//            
//
//            List<OccurrenceStruct> OccurrenceResults = searchOccurrence(species,"","","",0,0,repositoryGraph);
//         
//            String OccurrenceInfo = "";
//            
//            if(!OccurrenceResults.isEmpty()) {
//            String speciesOccURI = OccurrenceResults.get(0).getSpeciesURI();
//            
//            String occActor = OccurrenceResults.get(0).getActors().get(0).getValue();
//            String occActorURI = OccurrenceResults.get(0).getActors().get(0).getKey();
//            
//            String occDate = OccurrenceResults.get(0).getTimeSpan();
//            
//            String occLocality = OccurrenceResults.get(0).getLocalityName();
//            String occLocalityURI = OccurrenceResults.get(0).getLocalityURI();    
//                    
//            String occCountry = OccurrenceResults.get(0).getCountryName();
//            String occCountryURI = OccurrenceResults.get(0).getCountryURI();  
//
//            if(!occLocality.isEmpty())
//                OccurrenceInfo += "Individuals of the <b><i><a href=\""+browseURL+speciesOccURI+"\">"+species+"</a></i></b> species have been observed in <b><i><a href=\""+browseURL+occLocalityURI+"\">"+occLocality+"</a></i></b>";
//
//            if(!occCountry.isEmpty())
//                OccurrenceInfo += ", in <b><i><a href=\""+browseURL+occCountryURI+"\">"+occCountry+"</a></i></b>";
//
//            if(!occActor.isEmpty())
//                OccurrenceInfo += " by <b><i><a href=\""+browseURL+occActorURI+"\">"+occActor+"</a></i></b>";
//
//            if(!occDate.isEmpty())
//                OccurrenceInfo += " on "+occDate;
//
//            OccurrenceInfo += ".";
//            
//            }
//            
//            
//            List<IdentificationStruct> IdentificationResults = searchIdentification(species,"","","","", "",repositoryGraph);
//           
//            String IdentificationInfo = "";
//         
//            if(!IdentificationResults.isEmpty()) {
//                
//            String speciesURI = IdentificationResults.get(0).getSpeciesURI();
//            
//            String individual = IdentificationResults.get(0).getIndividualLabel();
//            String individualURI = IdentificationResults.get(0).getIndividualURI();
//            
//            String actor = IdentificationResults.get(0).getActors().get(0).getValue();
//            String actorURI = IdentificationResults.get(0).getActors().get(0).getKey();
//            
//            String date = IdentificationResults.get(0).getTimeSpan();
//            
//            String locality = IdentificationResults.get(0).getLocalityName();
//            String localityURI = IdentificationResults.get(0).getLocalityURI();    
//                    
//
//            if(!individual.isEmpty())
//                IdentificationInfo  += "Individuals of the <b><i><a href=\""+browseURL+speciesURI+"\">"+species+"</a></i></b> species, such as <b><i><a href=\""+browseURL+individualURI+"\">"+individual+"</a></i></b>, have been identified";
//
//            if(!locality.isEmpty())
//                IdentificationInfo += " in <b><i><a href=\""+browseURL+localityURI+"\">"+locality+"</a></i></b>";
//
//            if(!actor.isEmpty())
//                IdentificationInfo += " by <b><i><a href=\""+browseURL+actorURI+"\">"+actor+"</a></i></b>";
//
//            if(!date.isEmpty())
//                IdentificationInfo += " on "+date;
//
//            IdentificationInfo += ".";
//            
//            }
//            
//            
//            List <MicroCTScanningStruct> MicroCTResults = searchMicroCTScanning("","",species,"", "","",0,0,repositoryGraph);
//           
//            String MicroCTInfo = "";
//         
//            if(!MicroCTResults.isEmpty()) {
//            
//            String specimen = MicroCTResults.get(0).getSpecimenName();
//            String specimenURI = MicroCTResults.get(0).getSpecimenURI();
//            
//            String actor = MicroCTResults.get(0).getActorName();
//            String actorURI = MicroCTResults.get(0).getActorURI();
//            
//            String date = MicroCTResults.get(0).getTimespan();
//            
//            String device = MicroCTResults.get(0).getDeviceName();
//            String deviceURI = MicroCTResults.get(0).getDeviceURI(); 
//                
//            String product = MicroCTResults.get(0).getProducts().get(0).getValue();
//            String productURI = MicroCTResults.get(0).getProducts().get(0).getKey();
//
//            
//            if(!specimen.isEmpty())
//                MicroCTInfo  += "Specimens of the <b><i>"+species+"</i></b> species, such as <b><i><a href=\""+browseURL+specimenURI+"\">"+specimen+"</a></i></b>, have been scanned";
//
//            if(!actor.isEmpty())
//                MicroCTInfo += " by <b><i><a href=\""+browseURL+actorURI+"\">"+actor+"</a></i></b>";
//
//            if(!date.isEmpty())
//                MicroCTInfo += " on "+date;
//
//            if(!device.isEmpty())
//                MicroCTInfo += ", using a <b><i><a href=\""+browseURL+deviceURI+"\">"+device+"</a></i></b> scanner";
//
//            if(!device.isEmpty())
//                MicroCTInfo += " and resulting in <b><i><a href=\""+browseURL+productURI+"\">"+product+"</a></i></b>";
//
//            MicroCTInfo += ".";
//
//            }  

//            return fullInfo = TaxonomyInfo+" "+SNameInfo+" "+OccurrenceInfo+" "+IdentificationInfo+" "+MicroCTInfo;
//            return fullInfo = TaxonomyInfo+" "+relatedDatasets ;
 
    }  
      
     public boolean materialize(String species, String browseURL, String repositoryGraph) throws QueryExecutionException, FileNotFoundException, IOException {
                  
            CodeSource src = MetadataRepositoryService.class.getProtectionDomain().getCodeSource();

            if (src != null) {
                URL jar = src.getLocation();
                ZipInputStream zip = new ZipInputStream(jar.openStream());
                while(true) {
                    ZipEntry e = zip.getNextEntry();
                    if (e == null)
                         break;
                    String name = e.getName();
                  
                    if(name.contains(Resources.materializationQueriesFolder)){
                        StringBuilder sb=new StringBuilder();
                        BufferedReader br=new BufferedReader(new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream(name)));
            
                        String line;
                        while((line=br.readLine())!=null){
                            sb.append(line);
                            sb.append("\n");
                        }
                
                        String queryString= sb.toString();
                        //System.out.println(queryString);
                        this.repoManager.update(queryString);
                    }
                }
            }

            return true;
        
    }  

    public List<ScientificNamingStruct> searchScientificNaming(String speciesUri, String date, String actor,String datasetURI, String sname, int offset, int limit, String repositoryGraph)throws QueryExecutionException {
        logger.info("Request for scientificNaming search with parameters "
                   +"speciesURI: ["+speciesUri+"], "
                   +"date: ["+date+"], "
                   +"actor: ["+actor+"], "
                   +"datasetURI: ["+datasetURI+"], "
                   +"scientificName: ["+sname+"], "
                   +"limit: ["+(limit<0?"N/A":String.valueOf(limit))+"], "
                   +"offset: ["+(offset<0?"N/A":String.valueOf(offset))+"], "
                   +"reposytoryGraph: ["+repositoryGraph+"], ");
        String queryString="SELECT DISTINCT ?scientificNameAssignmentEventURI ?scientificNameAssignmentEventLabel ?speciesURI ?actorURI ?actorName "
                                          +"?date ?ncodeURI ?ncodeName ?datasetURI ?datasetName ?sname ?snameURI "
                +"FROM <"+repositoryGraph+"> "
                +"WHERE{ "
                +"?scientificNameAssignmentEventURI <"+Resources.rdfTypeLabel+"> <"+Resources.scientificNameAssignmentEventLabel+">.  "
                +"?scientificNameAssignmentEventURI <"+Resources.assignedAttributeTo+"> ?speciesURI. "
                +"?scientificNameAssignmentEventURI <"+Resources.assigned+"> ?snameURI. "
                +"?scientificNameAssignmentEventURI <"+Resources.carriedOutBy+"> ?actorURI. "
                +"?scientificNameAssignmentEventURI <"+Resources.rdfsLabel+"> ?scientificNameAssignmentEventLabel. "
                +"?datasetURI <"+Resources.rdfTypeLabel+"> <"+Resources.datasetLabel+">. "
                +"?datasetURI <"+Resources.refersTo+"> ?scientificNameAssignmentEventURI. "
                +"?datasetURI <"+Resources.rdfsLabel+"> ?datasetName. "
                +"?snameURI <"+Resources.rdfsLabel+"> ?sname. "
                +"?snameURI <"+Resources.rdfTypeLabel+"> <"+Resources.appellationLabel+">.  "
                +"?actorURI <"+Resources.rdfsLabel+"> ?actorName. "
                +"OPTIONAL { "
                    +"?scientificNameAssignmentEventURI <"+Resources.hasTimespan+"> ?date. "
                +"} "
                +"OPTIONAL { "
                    +"?scientificNameAssignmentEventURI <"+Resources.usedSpecificTechnique+"> ?ncodeURI. "
                    +"?ncodeURI <"+Resources.rdfsLabel+"> ?ncodeName. "
                +"} ";
        if(sname!=null && !sname.isEmpty()){
            queryString+="FILTER CONTAINS(LCASE(?sname),\""+sname.toLowerCase()+"\"). ";
        }
        if(date!=null && !date.isEmpty()){
            queryString+="FILTER (?date=\""+date+"\"). ";
        }
        if(actor!=null && !actor.isEmpty()){
            queryString+="FILTER CONTAINS(LCASE(?actorName),\""+actor.toLowerCase()+"\"). ";
        }
        if(speciesUri!=null && !speciesUri.isEmpty()){
            queryString+="FILTER CONTAINS(LCASE(STR(?speciesURI)),\""+speciesUri.toLowerCase()+"\"). ";
        }
        if(datasetURI!=null && !datasetURI.isEmpty()){
            queryString+="FILTER CONTAINS(LCASE(STR(?datasetURI)),\""+datasetURI.toLowerCase()+"\"). ";
        }
        queryString+="} ";
        if(limit>0 && offset>=0){
            queryString+="LIMIT "+limit+" "
                        +"OFFSET "+offset;
        }
        logger.debug("Submitting the query: \"" + queryString + "\"");
        List<BindingSet> sparqlresults = this.repoManager.query(queryString);
        logger.debug("The SPARQL query returned " + sparqlresults.size() + " results (RAW SPARQL results)");
        List<ScientificNamingStruct> results = new ArrayList();
        for (BindingSet result : sparqlresults) {
                ScientificNamingStruct struct = new ScientificNamingStruct().withScientificNameAssignmentEventURI(result.getValue("scientificNameAssignmentEventURI").stringValue())
                        .withScientificNameAssignmentEvent(result.getValue("scientificNameAssignmentEventLabel").stringValue())
                        .withActor(result.getValue("actorURI").stringValue(), result.getValue("actorName").stringValue())
                        .withAppellation(result.getValue("sname").stringValue())
                        .withAppellationURI(result.getValue("snameURI").stringValue())
                        .withSpeciesURI(result.getValue("speciesURI").stringValue())
                        .withSpeciesName(result.getValue("sname").stringValue())
                        .withDatasetURI(result.getValue("datasetURI").stringValue())
                        .withDatasetName(result.getValue("datasetName").stringValue());
                if (result.getValue("date") != null) {
                    struct.withTimeSpan(result.getValue("date").stringValue());
                }
                if (result.getValue("ncodeURI") != null) {
                    struct.withNomenclaturalCodeURI(result.getValue("ncodeURI").stringValue());
                }
                if (result.getValue("ncodeName") != null) {
                    struct.withNomenclaturalCodeName(result.getValue("ncodeName").stringValue());
                }
                results.add(struct);
        }
        logger.debug("The query returned "+results.size()+" scientific naming objects");
        return results;
    }

    public List<CommonNameStruct> searchCommonName(String species, String commonName, String place, String language, String datasetURI, String repositoryGraph)
            throws QueryExecutionException {

        String queryString = "SELECT DISTINCT "
                + "?speciesURI ?speciesName  ?commonName ?commonNameURI ?datasetName ?datasetURI "
                + "?language ?languageURI ?placeName ?placeURI "
                + "FROM <" + repositoryGraph + ">  "
                + "WHERE{   "
                + "?datasetURI <" + Resources.rdfTypeLabel + "> <" + Resources.datasetLabel + "> .  "
                + "?datasetURI <" + Resources.refersTo + "> ?commonNameURI .  "
                + "?datasetURI <" + Resources.rdfsLabel + "> ?datasetName .  "
                + "?commonNameURI <" + Resources.rdfTypeLabel + "> <" + Resources.appellationLabel + "> .  "
                + "?commonNameURI <" + Resources.rdfsLabel + "> ?commonName.   "
                + " OPTIONAL{?commonNameURI <" + Resources.isUsedIn + "> ?placeURI.  "
                + " ?placeURI <" + Resources.rdfTypeLabel + "> <" + Resources.placeLabel + "> .  "
                + " ?placeURI <" + Resources.rdfsLabel + "> ?placeName. } "
                + " ?commonNameURI <" + Resources.hasLanguage + "> ?languageURI.   "
                + " ?speciesURI <" + Resources.rdfTypeLabel + "> <" + Resources.speciesLabel + "> .  "
                + " ?speciesURI <" + Resources.isAlsoIdentifiedBy + "> ?commonNameURI.  " 
                + " ?speciesURI <" + Resources.rdfsLabel + "> ?speciesName .  "
                + " ?languageURI <" + Resources.rdfTypeLabel + "> <" + Resources.languageLabel + "> .  "
                + " ?languageURI <" + Resources.rdfsLabel + "> ?language.  "
                + " FILTER regex(?language,'" + language + "',\"i\")  "
                + " FILTER regex(?datasetURI,'" + datasetURI + "',\"i\")  "
                + " FILTER regex(?commonName,'" + commonName + "',\"i\")  "
                + " FILTER regex(?speciesName,'" + species + "',\"i\")}";

        logger.debug("Submitting the query: \"" + queryString + "\"");
        List<BindingSet> sparqlresults = this.repoManager.query(queryString);
        logger.debug("The query returned " + sparqlresults.size() + " results");
        Map<String, CommonNameStruct> map = new HashMap<>();
        for (BindingSet result : sparqlresults) {
            if (!map.containsKey(result.getValue("commonNameURI").stringValue())) {
                CommonNameStruct struct = new CommonNameStruct().withCommonNameURI(result.getValue("commonNameURI").stringValue())
                        .withDatasetURI(result.getValue("datasetURI").stringValue())
                        .withDatasetName(result.getValue("datasetName").stringValue())
                        .withCommonName(result.getValue("commonName").stringValue())
                        .withSpeciesName(result.getValue("speciesName").stringValue())
                        .withSpeciesURI(result.getValue("speciesURI").stringValue());
                if (result.getValue("language") != null) {
                    struct.withLanguage(result.getValue("language").stringValue());
                }
                if (result.getValue("languageURI") != null) {
                    struct.withLanguageURI(result.getValue("languageURI").stringValue());
                }
                if (result.getValue("placeURI") != null && result.getValue("placeName") != null) {
                    struct.withPlace(result.getValue("placeURI").stringValue(), result.getValue("placeName").stringValue());
                }
                map.put(struct.getCommonNameURI(), struct);
            } else {
                CommonNameStruct struct = map.get(result.getValue("commonNameURI").stringValue());
                if (result.getValue("placeURI") != null && result.getValue("placeName") != null) {
                    struct.withPlace(result.getValue("placeURI").stringValue(), result.getValue("placeName").stringValue());
                }
                map.put(struct.getCommonNameURI(), struct);
            }
        }
        if (!map.isEmpty()) {
            String query = "SELECT ?commonNameURI ?placeURI ?placeName "
                    + "WHERE{ "
                    + "OPTIONAL{?commonNameURI <" + Resources.isUsedIn + "> ?placeURI. "
                    + " ?placeURI <" + Resources.rdfTypeLabel + "> <" + Resources.placeLabel + "> .  "
                    + " ?placeURI <" + Resources.rdfsLabel + "> ?placeName. } "
                    + " FILTER (";
            for (CommonNameStruct struct : map.values()) {
                query += " ?commonNameURI = <" + struct.getCommonNameURI() + "> || ";
            }
            query = query.substring(0, query.length() - 3) + ")}";
            logger.debug("Query for retrieving all the avaialble places: " + query);
            sparqlresults = this.repoManager.query(query);
            logger.debug("The query returned " + sparqlresults.size() + " results");
            for (BindingSet result : sparqlresults) {
                CommonNameStruct struct = map.get(result.getValue("commonNameURI").stringValue());
                struct.withPlace(result.getValue("placeURI").stringValue(), result.getValue("placeName").stringValue());
                map.put(struct.getCommonNameURI(), struct);
            }
        }
        return new ArrayList<>(map.values());
    }
public List<CommonNameStruct> searchCommonName(String species, String commonName, String place, String language, String datasetURI, int offset, int limit,  String repositoryGraph)
            throws QueryExecutionException {

        String queryString = "SELECT DISTINCT "
                + "?speciesURI ?speciesName  ?commonName ?commonNameURI ?datasetName ?datasetURI "
                + "?language ?languageURI ?placeName ?placeURI "
                + "FROM <" + repositoryGraph + ">  "
                + "WHERE{   "
                + "?datasetURI <" + Resources.rdfTypeLabel + "> <" + Resources.datasetLabel + "> .  "
                + "?datasetURI <" + Resources.refersTo + "> ?commonNameURI .  "
                + "?datasetURI <" + Resources.rdfsLabel + "> ?datasetName .  "
                + "?commonNameURI <" + Resources.rdfTypeLabel + "> <" + Resources.appellationLabel + "> .  "
                + "?commonNameURI <" + Resources.rdfsLabel + "> ?commonName.   "
                + " OPTIONAL{?commonNameURI <" + Resources.isUsedIn + "> ?placeURI.  "
                + " ?placeURI <" + Resources.rdfTypeLabel + "> <" + Resources.placeLabel + "> .  "
                + " ?placeURI <" + Resources.rdfsLabel + "> ?placeName. } "
                + " ?commonNameURI <" + Resources.hasLanguage + "> ?languageURI.   "
                + " ?speciesURI <" + Resources.rdfTypeLabel + "> <" + Resources.speciesLabel + "> .  "
                + " ?speciesURI <" + Resources.isAlsoIdentifiedBy + "> ?commonNameURI.  " 
                + " ?speciesURI <" + Resources.rdfsLabel + "> ?speciesName .  "
                + " ?languageURI <" + Resources.rdfTypeLabel + "> <" + Resources.languageLabel + "> .  "
                + " ?languageURI <" + Resources.rdfsLabel + "> ?language.  "
                + " FILTER regex(?language,'" + language + "',\"i\")  "
                + " FILTER regex(?datasetURI,'" + datasetURI + "',\"i\")  "
                + " FILTER regex(?commonName,'" + commonName + "',\"i\")  "
                + " FILTER regex(?speciesName,'" + species + "',\"i\")}"
                + " LIMIT " + limit
                + " OFFSET " + offset;

        logger.debug("Submitting the query: \"" + queryString + "\"");
        List<BindingSet> sparqlresults = this.repoManager.query(queryString);
        logger.debug("The query returned " + sparqlresults.size() + " results");
        //Map<String, CommonNameStruct> map = new HashMap<>();
        List<CommonNameStruct> results = new ArrayList();
        for (BindingSet result : sparqlresults) {
           // if (!map.containsKey(result.getValue("commonNameURI").stringValue())) {
                CommonNameStruct struct = new CommonNameStruct().withCommonNameURI(result.getValue("commonNameURI").stringValue())
                        .withDatasetURI(result.getValue("datasetURI").stringValue())
                        .withDatasetName(result.getValue("datasetName").stringValue())
                        .withCommonName(result.getValue("commonName").stringValue())
                        .withSpeciesName(result.getValue("speciesName").stringValue())
                        .withSpeciesURI(result.getValue("speciesURI").stringValue());
                if (result.getValue("language") != null) {
                    struct.withLanguage(result.getValue("language").stringValue());
                }
                if (result.getValue("languageURI") != null) {
                    struct.withLanguageURI(result.getValue("languageURI").stringValue());
                }
                if (result.getValue("placeURI") != null && result.getValue("placeName") != null) {
                    struct.withPlace(result.getValue("placeURI").stringValue(), result.getValue("placeName").stringValue());
                }
                
                results.add(struct);
//                map.put(struct.getCommonNameURI(), struct);
//            } else {
//                CommonNameStruct struct = map.get(result.getValue("commonNameURI").stringValue());
//                if (result.getValue("placeURI") != null && result.getValue("placeName") != null) {
//                    struct.withPlace(result.getValue("placeURI").stringValue(), result.getValue("placeName").stringValue());
//                }
//                map.put(struct.getCommonNameURI(), struct);
//            }
//        }
//        if (!map.isEmpty()) {
//            String query = "SELECT ?commonNameURI ?placeURI ?placeName "
//                    + "WHERE{ "
//                    + "OPTIONAL{?commonNameURI <" + Resources.isUsedIn + "> ?placeURI. "
//                    + " ?placeURI <" + Resources.rdfTypeLabel + "> <" + Resources.placeLabel + "> .  "
//                    + " ?placeURI <" + Resources.rdfsLabel + "> ?placeName. } "
//                    + " FILTER (";
//            for (CommonNameStruct struct : map.values()) {
//                query += " ?commonNameURI = <" + struct.getCommonNameURI() + "> || ";
//            }
//            query = query.substring(0, query.length() - 3) + ")}";
//            logger.debug("Query for retrieving all the avaialble places: " + query);
//            sparqlresults = this.repoManager.query(query);
//            logger.debug("The query returned " + sparqlresults.size() + " results");
//            for (BindingSet result : sparqlresults) {
//                CommonNameStruct struct = map.get(result.getValue("commonNameURI").stringValue());
//                struct.withPlace(result.getValue("placeURI").stringValue(), result.getValue("placeName").stringValue());
//                map.put(struct.getCommonNameURI(), struct);
//            }
        }
        return results;
       // return new ArrayList<>(map.values());
    }

    public List<SynonymStruct> searchSynonym(String species, String scientificName, String synonym, String datasetURI,String repositoryGraph)
            throws QueryExecutionException {
        String queryString = "SELECT DISTINCT"
                + " ?speciesURI ?appellationURI ?appellation ?synonymURI ?synonym ?datasetName ?datasetURI "
                + "FROM <" + repositoryGraph + "> "
                + "WHERE{ "
                + " ?appellationURI <" + Resources.rdfTypeLabel + "> <" + Resources.appellationLabel + "> . "
                + " ?appellationURI <" + Resources.rdfsLabel + "> ?appellation. "
                + " ?synonymURI <" + Resources.rdfTypeLabel + "> <" + Resources.appellationLabel + "> . "
                + " ?synonymURI <" + Resources.rdfsLabel + "> ?synonym. "
                + " ?appellationURI <" + Resources.hasAlternativeForm + "> ?synonymURI. "
                + " ?datasetURI <" + Resources.rdfTypeLabel + "> <" + Resources.datasetLabel + "> . "
                + " ?datasetURI <" + Resources.refersTo + "> ?synonymURI .  "
                + " ?datasetURI <" + Resources.rdfsLabel + "> ?datasetName . "
                + " ?speciesURI <" + Resources.rdfTypeLabel + "> <" + Resources.speciesLabel + "> . "
                + " ?speciesURI <" + Resources.isAlsoIdentifiedBy + "> ?synonymURI. "
                + " FILTER regex(?synonym,'" + synonym + "',\"i\") "
                + " FILTER regex(?appellation,'" + scientificName + "',\"i\") "
                + " FILTER regex(?datasetURI,'" + datasetURI + "',\"i\")  "
                + " FILTER regex(?speciesURI,'" + species + "',\"i\")}";

        logger.debug("Submitting the query: \"" + queryString + "\"");
        List<BindingSet> sparqlresults = this.repoManager.query(queryString);
        logger.debug("The query returned " + sparqlresults.size() + " results");
        Map<String, SynonymStruct> map = new HashMap<>();
        for (BindingSet result : sparqlresults) {
            if (!map.containsKey(result.getValue("appellationURI").stringValue())) {
                SynonymStruct struct = new SynonymStruct().withAppellationURI(result.getValue("appellationURI").stringValue())
                        .withAppellation(result.getValue("appellation").stringValue())
                        .withSynonym(result.getValue("synonymURI").stringValue(), result.getValue("synonym").stringValue())
                        .withSpeciesURI(result.getValue("speciesURI").stringValue())
                        //.withSpeciesName(result.getValue("speciesName").stringValue())
                        .withDatasetURI(result.getValue("datasetURI").stringValue())
                        .withDatasetName(result.getValue("datasetName").stringValue());
    //            if(result.getValue("language")!=null){
                //                struct.withLanguage(result.getValue("languageName").stringValue());
                //            }      
                //            if(result.getValue("genusURI")!=null){
                //                struct.withLanguageURI(result.getValue("languageURI").stringValue());
                //            }

                map.put(struct.getAppellationURI(), struct);
            } else {
                SynonymStruct struct = map.get(result.getValue("appellationURI").stringValue());
                struct.withSynonym(result.getValue("synonymURI").stringValue(), result.getValue("synonym").stringValue());
                map.put(struct.getAppellationURI(), struct);
            }
        }
        if (!map.isEmpty()) {
            String query = "SELECT ?appellationURI ?synonymURI ?synonym "
                    + "FROM <" + repositoryGraph + "> "
                    + "WHERE{ "
                    + " ?synonymURI <" + Resources.rdfsLabel + "> ?synonym. "
                    + " ?appellationURI <" + Resources.hasAlternativeForm + "> ?synonymURI. "
                    + " FILTER ( ";
            for (SynonymStruct struct : map.values()) {
                query += " ?appellationURI = <" + struct.getAppellationURI() + "> || ";
            }
            query = query.substring(0, query.length() - 3) + ")}";
            logger.debug("Getting more synonyms using the query: \"" + query + "\"");
            sparqlresults = this.repoManager.query(query);
            logger.debug("The query returned " + sparqlresults.size() + " results");
            for (BindingSet result : sparqlresults) {
                SynonymStruct struct = map.get(result.getValue("appellationURI").stringValue());
                struct.withSynonym(result.getValue("synonymURI").stringValue(), result.getValue("synonym").stringValue());
                map.put(struct.getAppellationURI(), struct);
            }
        }
        return new ArrayList<>(map.values());
    }

    public List<SynonymStruct> searchSynonym(String species, String scientificName, String synonym, String datasetURI, int offset, int limit, String repositoryGraph)
            throws QueryExecutionException {
        String queryString = "SELECT DISTINCT"
                + " ?speciesURI ?appellationURI ?appellation ?synonymURI ?synonym ?datasetName ?datasetURI "
                + "FROM <" + repositoryGraph + "> "
                + "WHERE{ "
                + " ?appellationURI <" + Resources.rdfTypeLabel + "> <" + Resources.appellationLabel + "> . "
                + " ?appellationURI <" + Resources.rdfsLabel + "> ?appellation. "
                + " ?synonymURI <" + Resources.rdfTypeLabel + "> <" + Resources.appellationLabel + "> . "
                + " ?synonymURI <" + Resources.rdfsLabel + "> ?synonym. "
                + " ?appellationURI <" + Resources.hasAlternativeForm + "> ?synonymURI. "
                + " ?datasetURI <" + Resources.rdfTypeLabel + "> <" + Resources.datasetLabel + "> . "
                + " ?datasetURI <" + Resources.refersTo + "> ?synonymURI .  "
                + " ?datasetURI <" + Resources.rdfsLabel + "> ?datasetName . "
                + " ?speciesURI <" + Resources.rdfTypeLabel + "> <" + Resources.speciesLabel + "> . "
                + " ?speciesURI <" + Resources.isAlsoIdentifiedBy + "> ?synonymURI. "
                + " FILTER regex(?synonym,'" + synonym + "',\"i\") "
                + " FILTER regex(?appellation,'" + scientificName + "',\"i\") "
                + " FILTER regex(?datasetURI,'" + datasetURI + "',\"i\")  "
                + " FILTER regex(?speciesURI,'" + species + "',\"i\")}"
                + " LIMIT " + limit
                + " OFFSET " + offset;

        logger.debug("Submitting the query: \"" + queryString + "\"");
        List<BindingSet> sparqlresults = this.repoManager.query(queryString);
        logger.debug("The query returned " + sparqlresults.size() + " results");
       // Map<String, SynonymStruct> map = new HashMap<>();
        List<SynonymStruct> results = new ArrayList();
        for (BindingSet result : sparqlresults) {
          //  if (!map.containsKey(result.getValue("appellationURI").stringValue())) {
                SynonymStruct struct = new SynonymStruct().withAppellationURI(result.getValue("appellationURI").stringValue())
                        .withAppellation(result.getValue("appellation").stringValue())
                        .withSynonym(result.getValue("synonymURI").stringValue(), result.getValue("synonym").stringValue())
                        .withSpeciesURI(result.getValue("speciesURI").stringValue())
                        //.withSpeciesName(result.getValue("speciesName").stringValue())
                        .withDatasetURI(result.getValue("datasetURI").stringValue())
                        .withDatasetName(result.getValue("datasetName").stringValue());
    //            if(result.getValue("language")!=null){
                //                struct.withLanguage(result.getValue("languageName").stringValue());
                //            }      
                //            if(result.getValue("genusURI")!=null){
                //                struct.withLanguageURI(result.getValue("languageURI").stringValue());
                //            }

//                map.put(struct.getAppellationURI(), struct);
//            } else {
//                SynonymStruct struct = map.get(result.getValue("appellationURI").stringValue());
//                struct.withSynonym(result.getValue("synonymURI").stringValue(), result.getValue("synonym").stringValue());
//                map.put(struct.getAppellationURI(), struct);
//            }
//        }
//        if (!map.isEmpty()) {
//            String query = "SELECT ?appellationURI ?synonymURI ?synonym "
//                    + "FROM <" + repositoryGraph + "> "
//                    + "WHERE{ "
//                    + " ?synonymURI <" + Resources.rdfsLabel + "> ?synonym. "
//                    + " ?appellationURI <" + Resources.hasAlternativeForm + "> ?synonymURI. "
//                    + " FILTER ( ";
//            for (SynonymStruct struct : map.values()) {
//                query += " ?appellationURI = <" + struct.getAppellationURI() + "> || ";
//            }
//            query = query.substring(0, query.length() - 3) + ")}";
//            logger.debug("Getting more synonyms using the query: \"" + query + "\"");
//            sparqlresults = this.repoManager.query(query);
//            logger.debug("The query returned " + sparqlresults.size() + " results");
//            for (BindingSet result : sparqlresults) {
//                SynonymStruct struct = map.get(result.getValue("appellationURI").stringValue());
//                struct.withSynonym(result.getValue("synonymURI").stringValue(), result.getValue("synonym").stringValue());
//                map.put(struct.getAppellationURI(), struct);
//            }
        results.add(struct);
        }
        return results;
        //return new ArrayList<>(map.values());
    }

    public List<IdentificationStruct> searchIdentification(String species, String date, String actor, String place, String individual, String datasetURI,String repositoryGraph)
            throws QueryExecutionException {

        String queryString = "SELECT DISTINCT"
                + " ?identificationEventURI ?identificationEventLabel ?speciesURI ?speciesName  ?actorURI ?actorName "
                + " ?date ?individualURI ?individualLabel ?publication ?publicationURI ?datasetName ?datasetURI "
                + " ?placeName ?placeURI "
                + "FROM <" + repositoryGraph + "> "
                + "WHERE{ "
                + " ?identificationEventURI <" + Resources.rdfTypeLabel + "> <" + Resources.identificationEventLabel + "> . "
                + " ?identificationEventURI  <" + Resources.assigned + "> ?speciesURI . "
                + " ?identificationEventURI  <" + Resources.classified + "> ?individualURI . "
                + " ?identificationEventURI  <" + Resources.carriedOutBy + "> ?actorURI . "
                + " ?identificationEventURI  <" + Resources.hasTimespan + "> ?date . "
                + " ?identificationEventURI <" + Resources.rdfsLabel + "> ?identificationEventLabel . "
                + " OPTIONAL { ?identificationEventURI  <" + Resources.usedSpecificObject + "> ?publicationURI . "
                + " ?publicationURI <" + Resources.rdfTypeLabel + "> <" + Resources.publicationLabel + "> . "
                + " ?publicationURI <" + Resources.rdfsLabel + "> ?publication. } "
                //+" ?actorURI <"+Resources.rdfTypeLabel+"> <"+Resources.actorLabel+"> . "
                + " ?actorURI <" + Resources.rdfsLabel + "> ?actorName.  "
                + " OPTIONAL { ?identificationEventURI  <" + Resources.tookPlaceAt + "> ?placeURI .} "
                + " OPTIONAL{ ?placeURI <" + Resources.rdfTypeLabel + "> <" + Resources.placeLabel + "> . } "
                + " OPTIONAL{ ?placeURI <" + Resources.rdfsLabel + "> ?placeName. } "
                + " ?datasetURI <" + Resources.rdfTypeLabel + "> <" + Resources.datasetLabel + "> . "
                + " ?datasetURI <" + Resources.rdfsLabel + "> ?datasetName. "
                + " ?datasetURI <" + Resources.refersTo + "> ?identificationEventURI . "
                + " ?speciesURI <" + Resources.rdfTypeLabel + "> <" + Resources.speciesLabel + "> . "
                + " ?speciesURI <" + Resources.rdfsLabel + "> ?speciesName. "
                + " ?individualURI <" + Resources.rdfTypeLabel + "> <" + Resources.bioticElementLabel + "> . "
                + " ?individualURI <" + Resources.rdfsLabel + "> ?individualLabel. "
                // +" ?individualURI <"+Resources.isIdentifiedBy+"> ?indiviualName. }"
                + " FILTER regex(?placeName,'" + place + "',\"i\")  "
                + " FILTER regex(?datasetURI,'" + datasetURI + "',\"i\")  "
                + " FILTER regex(?individualURI,'" + individual + "',\"i\") "
                + " FILTER regex(?date,'" + date + "',\"i\") "
                + " FILTER regex(?actorName,'" + actor + "',\"i\") "
                + " FILTER regex(?speciesName,'" + species + "',\"i\")}";

        logger.debug("Submitting the query: \"" + queryString + "\"");
        List<BindingSet> sparqlresults = this.repoManager.query(queryString);
        logger.debug("The query returned " + sparqlresults.size() + " results");
        Map<String, IdentificationStruct> map = new HashMap<>();
        for (BindingSet result : sparqlresults) {
            if (!map.containsKey(result.getValue("identificationEventURI").stringValue())) {
                IdentificationStruct struct = new IdentificationStruct().withIdentificationEventURI(result.getValue("identificationEventURI").stringValue())
                        .withIdentificationEvent(result.getValue("identificationEventLabel").stringValue())
                        .withIndividualLabel(result.getValue("individualLabel").stringValue())
                        .withIndividualURI(result.getValue("individualURI").stringValue())
                        .withActor(result.getValue("actorURI").stringValue(), result.getValue("actorName").stringValue())
                        .withSpeciesName(result.getValue("speciesName").stringValue())
                        .withSpeciesURI(result.getValue("speciesURI").stringValue())
                        .withLocalityName(result.getValue("placeName").stringValue())
                        .withLocalityURI(result.getValue("placeURI").stringValue())
                        .withTimeSpan(result.getValue("date").stringValue())
                        .withDatasetURI(result.getValue("datasetURI").stringValue())
                        .withDatasetTitle(result.getValue("datasetName").stringValue());

                if (result.getValue("publicationURI") != null) {
                    struct.withIdentificationReferencesURI(result.getValue("publicationURI").stringValue());
                }

                if (result.getValue("publication") != null) {
                    struct.withIdentificationReferencesName(result.getValue("publication").stringValue());
                }
                map.put(struct.getIdentificationEventURI(), struct);
            } else {
                IdentificationStruct struct = map.get(result.getValue("identificationEventURI").stringValue());
                struct.withActor(result.getValue("actorURI").stringValue(), result.getValue("actorName").stringValue());
                map.put(struct.getIdentificationEventURI(), struct);
            }
        }
        if (!map.isEmpty()) {
            String query = "SELECT ?identificationEventURI ?actorURI ?actorName "
                    + "FROM <" + repositoryGraph + "> "
                    + "WHERE{ "
                    + "?identificationEventURI  <" + Resources.carriedOutBy + "> ?actorURI . "
                    + "?actorURI <" + Resources.rdfsLabel + "> ?actorName . "
                    + "FILTER( ";
            for (IdentificationStruct struct : map.values()) {
                query += "?identificationEventURI = <" + struct.getIdentificationEventURI() + "> || ";
            }
            query = query.substring(0, query.length() - 3) + " )}";
            logger.debug("Retrieving the actor using the query: \"" + query + "\"");
            sparqlresults = this.repoManager.query(query);
            for (BindingSet result : sparqlresults) {
                IdentificationStruct struct = map.get(result.getValue("identificationEventURI").stringValue());
                struct.withActor(result.getValue("actorURI").stringValue(), result.getValue("actorName").stringValue());
                map.put(struct.getIdentificationEventURI(), struct);
            }
        }
        return new ArrayList<>(map.values());
    }

    public List<IdentificationStruct> searchIdentification(String species, String date, String actor, String place, String individual, String datasetURI,int offset, int limit, String repositoryGraph)
            throws QueryExecutionException {

        String queryString = "SELECT DISTINCT"
                + " ?identificationEventURI ?identificationEventLabel ?speciesURI ?speciesName  ?actorURI ?actorName "
                + " ?date ?individualURI ?individualLabel ?publication ?publicationURI ?datasetName ?datasetURI "
                + " ?placeName ?placeURI "
                + "FROM <" + repositoryGraph + "> "
                + "WHERE{ "
                + " ?identificationEventURI <" + Resources.rdfTypeLabel + "> <" + Resources.identificationEventLabel + "> . "
                + " ?identificationEventURI  <" + Resources.assigned + "> ?speciesURI . "
                + " ?identificationEventURI  <" + Resources.classified + "> ?individualURI . "
                + " ?identificationEventURI  <" + Resources.carriedOutBy + "> ?actorURI . "
                + " ?identificationEventURI  <" + Resources.hasTimespan + "> ?date . "
                + " ?identificationEventURI  <" + Resources.rdfsLabel + "> ?identificationEventLabel . "
                + " OPTIONAL { ?identificationEventURI  <" + Resources.usedSpecificObject + "> ?publicationURI . "
                + " ?publicationURI <" + Resources.rdfTypeLabel + "> <" + Resources.publicationLabel + "> . "
                + " ?publicationURI <" + Resources.rdfsLabel + "> ?publication. } "
                //+" ?actorURI <"+Resources.rdfTypeLabel+"> <"+Resources.actorLabel+"> . "
                + " ?actorURI <" + Resources.rdfsLabel + "> ?actorName.  "
                + " OPTIONAL { ?identificationEventURI  <" + Resources.tookPlaceAt + "> ?placeURI .} "
                + " OPTIONAL{ ?placeURI <" + Resources.rdfTypeLabel + "> <" + Resources.placeLabel + "> . } "
                + " OPTIONAL{ ?placeURI <" + Resources.rdfsLabel + "> ?placeName. } "
                + " ?datasetURI <" + Resources.rdfTypeLabel + "> <" + Resources.datasetLabel + "> . "
                + " ?datasetURI <" + Resources.rdfsLabel + "> ?datasetName. "
                + " ?datasetURI <" + Resources.refersTo + "> ?identificationEventURI . "
                + " ?speciesURI <" + Resources.rdfTypeLabel + "> <" + Resources.speciesLabel + "> . "
                + " ?speciesURI <" + Resources.rdfsLabel + "> ?speciesName. "
                + " ?individualURI <" + Resources.rdfTypeLabel + "> <" + Resources.bioticElementLabel + "> . "
                + " ?individualURI   <" + Resources.rdfsLabel + "> ?individualLabel . "
                // +" ?individualURI <"+Resources.isIdentifiedBy+"> ?indiviualName. }"
                + " FILTER regex(?placeName,'" + place + "',\"i\")  "
                + " FILTER regex(?datasetURI,'" + datasetURI + "',\"i\")  "
                + " FILTER regex(?individualURI,'" + individual + "',\"i\") "
                + " FILTER regex(?date,'" + date + "',\"i\") "
                + " FILTER regex(?actorName,'" + actor + "',\"i\") "
                + " FILTER regex(?speciesName,'" + species + "',\"i\")}"
                + " LIMIT " + limit
                + " OFFSET " + offset;

        logger.debug("Submitting the query: \"" + queryString + "\"");
        List<BindingSet> sparqlresults = this.repoManager.query(queryString);
        logger.debug("The query returned " + sparqlresults.size() + " results");
        Map<String, IdentificationStruct> map = new HashMap<>();
        for (BindingSet result : sparqlresults) {
            if (!map.containsKey(result.getValue("identificationEventURI").stringValue())) {
                IdentificationStruct struct = new IdentificationStruct().withIdentificationEventURI(result.getValue("identificationEventURI").stringValue())
                        .withIdentificationEvent(result.getValue("identificationEventLabel").stringValue())
                        .withIndividualLabel(result.getValue("individualLabel").stringValue())
                        .withIndividualURI(result.getValue("individualURI").stringValue())
                        .withActor(result.getValue("actorURI").stringValue(), result.getValue("actorName").stringValue())
                        .withSpeciesName(result.getValue("speciesName").stringValue())
                        .withSpeciesURI(result.getValue("speciesURI").stringValue())
                        .withLocalityName(result.getValue("placeName").stringValue())
                        .withLocalityURI(result.getValue("placeURI").stringValue())
                        .withTimeSpan(result.getValue("date").stringValue())
                        .withDatasetURI(result.getValue("datasetURI").stringValue())
                        .withDatasetTitle(result.getValue("datasetName").stringValue());

                if (result.getValue("publicationURI") != null) {
                    struct.withIdentificationReferencesURI(result.getValue("publicationURI").stringValue());
                }

                if (result.getValue("publication") != null) {
                    struct.withIdentificationReferencesName(result.getValue("publication").stringValue());
                }
                map.put(struct.getIdentificationEventURI(), struct);
            } else {
                IdentificationStruct struct = map.get(result.getValue("identificationEventURI").stringValue());
                struct.withActor(result.getValue("actorURI").stringValue(), result.getValue("actorName").stringValue());
                map.put(struct.getIdentificationEventURI(), struct);
            }
        }
        if (!map.isEmpty()) {
            String query = "SELECT ?identificationEventURI ?actorURI ?actorName "
                    + "FROM <" + repositoryGraph + "> "
                    + "WHERE{ "
                    + "?identificationEventURI  <" + Resources.carriedOutBy + "> ?actorURI . "
                    + "?actorURI <" + Resources.rdfsLabel + "> ?actorName . "
                    + "FILTER( ";
            for (IdentificationStruct struct : map.values()) {
                query += "?identificationEventURI = <" + struct.getIdentificationEventURI() + "> || ";
            }
            query = query.substring(0, query.length() - 3) + " )}";
            logger.debug("Retrieving the actor using the query: \"" + query + "\"");
            sparqlresults = this.repoManager.query(query);
            for (BindingSet result : sparqlresults) {
                IdentificationStruct struct = map.get(result.getValue("identificationEventURI").stringValue());
                struct.withActor(result.getValue("actorURI").stringValue(), result.getValue("actorName").stringValue());
                map.put(struct.getIdentificationEventURI(), struct);
            }
        }
        return new ArrayList<>(map.values());
    }

    public List<GensDatasetStruct> searchGenetics(String species, String sample, String place, String device,String datasetURI, String repositoryGraph) throws QueryExecutionException {
        String queryString = "SELECT DISTINCT ?sampleTakingEventURI ?sampleTakingEventLabel ?datasetURI ?datasetName "
                + " ?placeURI ?placeName ?sampleURI ?sampleName "
                + " ?speciesName ?speciesURI ?habitatURI ?habitatName ?timespan "

                + " ?sequencingURI ?sequencingLabel ?deviceURI ?deviceName ?deviceType ?deviceTypeURI"
                + " ?productURI ?productName ?description "
             
                + "FROM <" + repositoryGraph + "> "
                + "WHERE{ "
                + "?sampleTakingEventURI <" + Resources.rdfTypeLabel + "> <" + Resources.sampleTakingLabel + "> . "
                + "?sampleTakingEventURI <" + Resources.removed + "> ?sampleURI . "
                + "?sampleTakingEventURI <" + Resources.rdfsLabel + "> ?sampleTakingEventLabel. "
                + " OPTIONAL {?sampleTakingEventURI <" + Resources.hasNote + "> ?description . } "
                
                + "?sampleURI <" + Resources.rdfTypeLabel + "> <" + Resources.sampleLabel + "> . "
                + "?sampleURI <" + Resources.rdfsLabel + "> ?sampleName . "
                + "?sampleURI <" + Resources.belongsTo + "> ?speciesURI . "
               
                + "?speciesURI <" + Resources.rdfTypeLabel + "> <" + Resources.speciesLabel + "> . "
                + "?speciesURI <" + Resources.rdfsLabel + "> ?speciesName . "
               
                + " OPTIONAL {?sampleTakingEventURI <" + Resources.tookPlaceAt + "> ?placeURI . "
                + " ?placeURI <" + Resources.rdfTypeLabel + "> <" + Resources.ecosystemEnvironmentLabel + "> . "
                + " ?placeURI <" + Resources.rdfsLabel + "> ?placeName . }"
                
                + " OPTIONAL {?sampleTakingEventURI<" + Resources.hasTimespan + "> ?timespan .} "
               
                + " ?datasetURI <" + Resources.rdfTypeLabel + "> <" + Resources.datasetLabel + "> . "
                + " ?datasetURI <" + Resources.refersTo + "> ?sampleTakingEventURI  . "
                + " ?datasetURI <" + Resources.rdfsLabel + "> ?datasetName . "

                + " OPTIONAL {?placeURI <" + Resources.hasType + "> ?habitatURI . "
                + " ?habitatURI <" + Resources.rdfTypeLabel + "> <" + Resources.ecosystemTypeLabel + "> . "
                + " ?habitatURI <" + Resources.rdfsLabel + "> ?habitatName .} "
              
                + " OPTIONAL { ?sequencingURI <" + Resources.rdfTypeLabel + "> <" + Resources.digitizationProcessLabel + "> . "
                + " ?sequencingURI <" + Resources.hasType + "> \"Sequencing\" . "
                + " ?sequencingURI <" + Resources.digitized + "> ?sampleURI . "
                + " ?sequencingURI <" + Resources.rdfsLabel + "> ?sequencingLabel. "
                + " ?productURI <" + Resources.rdfTypeLabel + "> <" + Resources.dataObjectLabel + "> . "
                + " ?productURI <" + Resources.rdfsLabel + "> ?productName . "
                + " ?sequencingURI <" + Resources.hasCreated+ "> ?productURI .}"
                
                
                + "OPTIONAL { ?deviceURI <" + Resources.rdfTypeLabel + "> <" + Resources.digitalDeviceLabel + "> . "
                + " ?deviceURI <" + Resources.rdfsLabel + "> ?deviceName . "
                + " ?deviceURI <" + Resources.hasType + "> ?deviceType. }"
                
              
                + " FILTER regex(?speciesName,'" + species + "',\"i\") "
                + " FILTER regex(?datasetURI,'" + datasetURI + "',\"i\")  "
                + " FILTER regex(?deviceType,'" + device + "',\"i\") "
                + " FILTER regex(?sampleName,'" + sample + "',\"i\")"
                + " FILTER regex(?placeName,'" + place + "',\"i\")}";

        logger.debug("Submitting the query: \"" + queryString + "\"");
        List<BindingSet> sparqlresults = this.repoManager.query(queryString);
        logger.debug("The result returned " + sparqlresults.size() + " results");
        List<GensDatasetStruct> results = new ArrayList<>();
        for (BindingSet result : sparqlresults) {
            GensDatasetStruct struct = new GensDatasetStruct().withSampleTakingURI(result.getValue("sampleTakingEventURI").stringValue())
                    .withSampleTaking(result.getValue("sampleTakingEventLabel").stringValue())
                    .withDatasetURI(result.getValue("datasetURI").stringValue())
                    .withDatasetTitle(result.getValue("datasetName").stringValue())
                    .withSpeciesURI(result.getValue("speciesURI").stringValue())
                    .withSpeciesName(result.getValue("speciesName").stringValue())  
                    .withSampleURI(result.getValue("sampleURI").stringValue())
                    .withSampleName(result.getValue("sampleName").stringValue());



            if (result.getValue("timespan") != null) {
                struct.withTimeSpan(result.getValue("timespan").stringValue());
            }

            if (result.getValue("sequencingURI") != null) {
                struct.withSequencingEventURI(result.getValue("sequencingURI").stringValue());
                struct.withSequencingEvent(result.getValue("sequencingLabel").stringValue());
            }

            if (result.getValue("deviceURI") != null) {
                struct.withDeviceURI(result.getValue("deviceURI").stringValue());
            }

            if (result.getValue("deviceTypeURI") != null) {
                struct.withDeviceTypeURI(result.getValue("deviceTypeURI").stringValue());
            }
            
            if (result.getValue("habitatURI") != null) {
                struct.withHabitatURI(result.getValue("habitatURI").stringValue());
            }

            if (result.getValue("habitatName") != null) {
                struct.withHabitatName(result.getValue("habitatName").stringValue());
            }

            if (result.getValue("placeURI") != null) {
                struct.withEcosystemURI(result.getValue("placeURI").stringValue());
            }

            if (result.getValue("placeName") != null) {
                struct.withEcosystemName(result.getValue("placeName").stringValue());
            }
            
             if (result.getValue("deviceName") != null) {
                struct.withDevice(result.getValue("deviceName").stringValue());
            }

              if (result.getValue("deviceType") != null) {
                struct.withDeviceType(result.getValue("deviceType").stringValue());
            }
              
               if (result.getValue("description") != null) {
                struct.withDescription(result.getValue("description").stringValue());
            }
              
                if (result.getValue("productName") != null) {
                struct.withProducedFile(result.getValue("productName").stringValue());
            }

              if (result.getValue("producedFileURI") != null) {
                struct.withProducedFileURI(result.getValue("producedFileURI").stringValue());
            }

            results.add(struct);
        }
        return results;
    }

    public List<GensDatasetStruct> searchGenetics(String species, String sample, String place, String device,String datasetURI,int offset, int limit, String repositoryGraph) throws QueryExecutionException {
        String queryString = "SELECT DISTINCT ?sampleTakingEventURI ?sampleTakingEventLabel ?datasetURI ?datasetName "
                + " ?placeURI ?placeName ?sampleURI ?sampleName "
                + " ?speciesName ?speciesURI ?habitatURI ?habitatName ?timespan "

                + " ?sequencingURI ?sequencingLabel ?deviceURI ?deviceName ?deviceType ?deviceTypeURI"
                + " ?productURI ?productName ?description "
             
                + "FROM <" + repositoryGraph + "> "
                + "WHERE{ "
                + "?sampleTakingEventURI <" + Resources.rdfTypeLabel + "> <" + Resources.sampleTakingLabel + "> . "
                + "?sampleTakingEventURI <" + Resources.removed + "> ?sampleURI . "
                + "?sampleTakingEventURI <" + Resources.rdfsLabel + "> ?sampleTakingEventLabel. "
                + " OPTIONAL {?sampleTakingEventURI <" + Resources.hasNote + "> ?description . } "
                
                + "?sampleURI <" + Resources.rdfTypeLabel + "> <" + Resources.sampleLabel + "> . "
                + "?sampleURI <" + Resources.rdfsLabel + "> ?sampleName . "
                + "?sampleURI <" + Resources.belongsTo + "> ?speciesURI . "
               
                + "?speciesURI <" + Resources.rdfTypeLabel + "> <" + Resources.speciesLabel + "> . "
                + "?speciesURI <" + Resources.rdfsLabel + "> ?speciesName . "
               
                + " OPTIONAL {?sampleTakingEventURI <" + Resources.tookPlaceAt + "> ?placeURI . "
                + " ?placeURI <" + Resources.rdfTypeLabel + "> <" + Resources.ecosystemEnvironmentLabel + "> . "
                + " ?placeURI <" + Resources.rdfsLabel + "> ?placeName . }"
                
                + " OPTIONAL {?sampleTakingEventURI<" + Resources.hasTimespan + "> ?timespan .} "
               
                + " ?datasetURI <" + Resources.rdfTypeLabel + "> <" + Resources.datasetLabel + "> . "
                + " ?datasetURI <" + Resources.refersTo + "> ?sampleTakingEventURI  . "
                + " ?datasetURI <" + Resources.rdfsLabel + "> ?datasetName . "

                + " OPTIONAL {?placeURI <" + Resources.hasType + "> ?habitatURI . "
                + " ?habitatURI <" + Resources.rdfTypeLabel + "> <" + Resources.ecosystemTypeLabel + "> . "
                + " ?habitatURI <" + Resources.rdfsLabel + "> ?habitatName .} "
              
                + " OPTIONAL { ?sequencingURI <" + Resources.rdfTypeLabel + "> <" + Resources.digitizationProcessLabel + "> . "
                + " ?sequencingURI <" + Resources.hasType + "> \"Sequencing\" . "
                + " ?sequencingURI <" + Resources.rdfsLabel + "> ?sequencingLabel. "
                + " ?sequencingURI <" + Resources.digitized + "> ?sampleURI . "
                + " ?productURI <" + Resources.rdfTypeLabel + "> <" + Resources.dataObjectLabel + "> . "
                + " ?productURI <" + Resources.rdfsLabel + "> ?productName . "
                + " ?sequencingURI <" + Resources.hasCreated+ "> ?productURI .}"
                
                
                + "OPTIONAL { ?deviceURI <" + Resources.rdfTypeLabel + "> <" + Resources.digitalDeviceLabel + "> . "
                + " ?deviceURI <" + Resources.rdfsLabel + "> ?deviceName . "
                + " ?deviceURI <" + Resources.hasType + "> ?deviceType. }"
                
              
                + " FILTER regex(?speciesName,'" + species + "',\"i\") "
                + " FILTER regex(?datasetURI,'" + datasetURI + "',\"i\")  "
                + " FILTER regex(?deviceType,'" + device + "',\"i\") "
                + " FILTER regex(?sampleName,'" + sample + "',\"i\")"
                + " FILTER regex(?placeName,'" + place + "',\"i\")}"
                + " LIMIT " + limit
                + " OFFSET " + offset;

        logger.debug("Submitting the query: \"" + queryString + "\"");
        List<BindingSet> sparqlresults = this.repoManager.query(queryString);
        logger.debug("The result returned " + sparqlresults.size() + " results");
        List<GensDatasetStruct> results = new ArrayList<>();
        for (BindingSet result : sparqlresults) {
            GensDatasetStruct struct = new GensDatasetStruct().withSampleTakingURI(result.getValue("sampleTakingEventURI").stringValue())
                    .withSampleTaking(result.getValue("sampleTakingEventLabel").stringValue())
                    .withDatasetURI(result.getValue("datasetURI").stringValue())
                    .withDatasetTitle(result.getValue("datasetName").stringValue())
                    .withSpeciesURI(result.getValue("speciesURI").stringValue())
                    .withSpeciesName(result.getValue("speciesName").stringValue())  
                    .withSampleURI(result.getValue("sampleURI").stringValue())
                    .withSampleName(result.getValue("sampleName").stringValue());



            if (result.getValue("timespan") != null) {
                struct.withTimeSpan(result.getValue("timespan").stringValue());
            }

            if (result.getValue("sequencingURI") != null) {
                struct.withSequencingEventURI(result.getValue("sequencingURI").stringValue());
                struct.withSequencingEvent(result.getValue("sequencingLabel").stringValue());
            }

            if (result.getValue("deviceURI") != null) {
                struct.withDeviceURI(result.getValue("deviceURI").stringValue());
            }

            if (result.getValue("deviceTypeURI") != null) {
                struct.withDeviceTypeURI(result.getValue("deviceTypeURI").stringValue());
            }
            
            if (result.getValue("habitatURI") != null) {
                struct.withHabitatURI(result.getValue("habitatURI").stringValue());
            }

            if (result.getValue("habitatName") != null) {
                struct.withHabitatName(result.getValue("habitatName").stringValue());
            }

            if (result.getValue("placeURI") != null) {
                struct.withEcosystemURI(result.getValue("placeURI").stringValue());
            }

            if (result.getValue("placeName") != null) {
                struct.withEcosystemName(result.getValue("placeName").stringValue());
            }
            
             if (result.getValue("deviceName") != null) {
                struct.withDevice(result.getValue("deviceName").stringValue());
            }

              if (result.getValue("deviceType") != null) {
                struct.withDeviceType(result.getValue("deviceType").stringValue());
            }
              
               if (result.getValue("description") != null) {
                struct.withDescription(result.getValue("description").stringValue());
            }
              
                if (result.getValue("productName") != null) {
                struct.withProducedFile(result.getValue("productName").stringValue());
            }

              if (result.getValue("producedFileURI") != null) {
                struct.withProducedFileURI(result.getValue("producedFileURI").stringValue());
            }

            results.add(struct);
        }
        return results;
    }

    public List<GensSampleStruct> searchGensSample(String species, String device, String sample, String datasetURI,String repositoryGraph) throws QueryExecutionException {
        String queryString = "SELECT DISTINCT ?transformationEventURI ?sampleURI ?sampleName ?transformedSampleURI ?transformedSampleName "
                + "?placeURI ?placeName ?speciesURI ?speciesName ?sequencingURI ?deviceURI ?deviceName ?deviceType "
                + "?productURI ?productName ?postProductURI ?postProductName ?postprocessingURI ?description "
                + "?dimensionURI ?dimensionName ?dimensionTypeURI ?dimensionUnit ?dimensionValue ?datasetURI ?datasetName "
                + "FROM <" + repositoryGraph + "> "
                + "WHERE{ "
                + " ?transformationEventURI <" + Resources.rdfTypeLabel + "> <" + Resources.transformationEventLabel + "> . "
                + " ?transformationEventURI <" + Resources.transformed + "> ?sampleURI . "
                + " ?sampleURI <" + Resources.rdfTypeLabel + "> <" + Resources.sampleLabel + ">. "
                + " ?sampleURI <" + Resources.rdfsLabel + "> ?sampleName. "
                + " OPTIONAL{ ?transformationEventURI <" + Resources.resultedIn + "> ?transformedSampleURI . "
                + " ?transformedSampleURI <" + Resources.rdfTypeLabel + "> <" + Resources.sampleLabel + ">. "
                + " ?transformedSampleURI <" + Resources.rdfsLabel + "> ?transformedSampleName. } "
                + " OPTIONAL{ ?sampleURI <" + Resources.hasCurrentLocation + "> ?placeURI . } "
                + " OPTIONAL{ ?transformedSampleURI <" + Resources.hasCurrentLocation + "> ?placeURI . } "
                + " OPTIONAL {?placeURI <" + Resources.rdfTypeLabel + "> <" + Resources.placeLabel + ">. "
                + " ?placeURI <" + Resources.rdfsLabel + "> ?placeName. } "
                + " ?sampleURI <" + Resources.belongsTo + "> ?speciesURI. "
                + " ?speciesURI <" + Resources.rdfTypeLabel + "> <" + Resources.speciesLabel + ">. "
                + " ?speciesURI <" + Resources.rdfsLabel + "> ?speciesName. "
                + " ?sequencingURI <" + Resources.rdfTypeLabel + "> <" + Resources.digitizationProcessLabel + "> . "
                + " ?sequencingURI <" + Resources.hasType + "> \"Sequencing\" . "
                + " ?sequencingURI <" + Resources.digitized + "> ?sampleURI . "
                + " OPTIONAL {?sequencingURI <" + Resources.hasNote + "> ?description . } "
                + " ?sequencingURI <" + Resources.happenedOnDevice + "> ?deviceURI . "
                + " ?sequencingURI <" + Resources.createdDerivative + "> ?productURI . "
                + " ?datasetURI <" + Resources.rdfTypeLabel + "> <" + Resources.datasetLabel + "> . "
                + " ?datasetURI <" + Resources.refersTo + "> ?sequencingURI  . "
                + " ?datasetURI <" + Resources.rdfsLabel + "> ?datasetName . "
                + " ?deviceURI <" + Resources.rdfTypeLabel + "> <" + Resources.digitalDeviceLabel + "> . "
                + " ?deviceURI <" + Resources.rdfsLabel + "> ?deviceName . "
                + " ?deviceURI <" + Resources.hasType + "> ?deviceType. "
                + " ?postprocessingURI <" + Resources.rdfTypeLabel + "> <" + Resources.formalDerivationEventLabel + "> . "
                + " ?postprocessingURI <" + Resources.hasCreated + "> ?postProductURI . "
                + " ?productURI <" + Resources.wasDerivationSourceFor + "> ?postprocessingURI . "
                + " ?productURI <" + Resources.rdfTypeLabel + "> <" + Resources.dataObjectLabel + "> . "
                + " ?productURI <" + Resources.rdfsLabel + "> ?productName . "
                + " ?postProductURI <" + Resources.rdfTypeLabel + "> <" + Resources.dataObjectLabel + "> . "
                + " ?postProductURI <" + Resources.rdfsLabel + "> ?postProductName . "
                + " OPTIONAL{ ?placeURI <" + Resources.hasDimension + "> ?dimensionURI . "
                + " ?dimensionURI <" + Resources.rdfTypeLabel + "> <" + Resources.dimensionLabel + "> . "
                //                        +" ?dimensionTypeURI <"+Resources.rdfTypeLabel+"> <"+Resources.dimensionTypeLabel+">. "
                + " ?dimensionTypeURI <" + Resources.rdfsLabel + "> ?dimensionName. "
                + " ?dimensionURI <" + Resources.hasType + "> ?dimensionTypeURI. }"
                + " OPTIONAL { ?dimensionURI <" + Resources.hasValue + "> ?dimensionValue. } "
                + " OPTIONAL { ?dimensionURI <" + Resources.hasUnit + "> ?dimensionUnit.  }"
                + " FILTER regex(?speciesName,'" + species + "',\"i\") "
                + " FILTER regex(?datasetURI,'" + datasetURI + "',\"i\")  "
                + " FILTER regex(?deviceName,'" + device + "',\"i\") "
                + " FILTER regex(?sampleName,'" + sample + "',\"i\")}";

        logger.debug("Submitting the query: \"" + queryString + "\"");
        List<BindingSet> sparqlresults = this.repoManager.query(queryString);
        logger.debug("The result returned " + sparqlresults.size() + " results");
        List<GensSampleStruct> results = new ArrayList<>();
        for (BindingSet result : sparqlresults) {
            GensSampleStruct struct = new GensSampleStruct()
                    .withTransformationURI(result.getValue("transformationEventURI").stringValue())
                    .withSampleURI(result.getValue("sampleURI").stringValue())
                    .withSampleName(result.getValue("sampleName").stringValue())
                    .withPostProcessingURI(result.getValue("postprocessingURI").stringValue())
                    .withSequencingURI(result.getValue("sequencingURI").stringValue())
                    .withSpeciesName(result.getValue("speciesName").stringValue())
                    .withSpeciesURI(result.getValue("speciesURI").stringValue())
                    .withProductName(result.getValue("productName").stringValue())
                    .withProductURI(result.getValue("productURI").stringValue())
                    .withPostProductName(result.getValue("postProductName").stringValue())
                    .withPostProductURI(result.getValue("postProductURI").stringValue())
                    .withDeviceName(result.getValue("deviceName").stringValue())
                    .withDeviceURI(result.getValue("deviceURI").stringValue())
                    .withDeviceType(result.getValue("deviceType").stringValue())
                    .withDatasetName(result.getValue("datasetName").stringValue())
                    .withDatasetURI(result.getValue("datasetURI").stringValue());

            if (result.getValue("placeURI") != null) {
                struct.withPlaceURI(result.getValue("placeURI").stringValue());
            }

            if (result.getValue("placeName") != null) {
                struct.withPlaceName(result.getValue("placeName").stringValue());
            }

            if (result.getValue("description") != null) {
                struct.withDescription(result.getValue("description").stringValue());
            }

            if (result.getValue("transformedSampleURI") != null) {
                struct.withTransformedSampleURI(result.getValue("transformedSampleURI").stringValue());
            }

            if (result.getValue("transformedSampleName") != null) {
                struct.withTransformedSampleName(result.getValue("transformedSampleName").stringValue());
            }

            if (result.getValue("dimensionName") != null) {
                struct.withDimensionName(result.getValue("dimensionName").stringValue());
            }

            if (result.getValue("dimensionURI") != null) {
                struct.withDimensionURI(result.getValue("dimensionURI").stringValue());
            }

            if (result.getValue("dimensionValue") != null) {
                struct.withDimensionValue(result.getValue("dimensionValue").stringValue());
            }

            if (result.getValue("dimensionUnit") != null) {
                struct.withDimensionUnit(result.getValue("dimensionUnit").stringValue());
            }

            if (result.getValue("dimensionTypeURI") != null) {
                struct.withDimensionType(result.getValue("dimensionTypeURI").stringValue());
            }

            results.add(struct);
        }
        return results;
    }

   
     public List<GensSampleStruct> searchGensSample(String species, String device, String sample, String datasetURI, int offset, int limit, String repositoryGraph) throws QueryExecutionException {
        String queryString = "SELECT DISTINCT ?transformationEventURI ?sampleURI ?sampleName ?transformedSampleURI ?transformedSampleName "
                + "?placeURI ?placeName ?speciesURI ?speciesName ?sequencingURI ?deviceURI ?deviceName ?deviceType "
                + "?productURI ?productName ?postProductURI ?postProductName ?postprocessingURI ?description "
                + "?dimensionURI ?dimensionName ?dimensionTypeURI ?dimensionUnit ?dimensionValue ?datasetURI ?datasetName "
                + "FROM <" + repositoryGraph + "> "
                + "WHERE{ "
                + " ?transformationEventURI <" + Resources.rdfTypeLabel + "> <" + Resources.transformationEventLabel + "> . "
                + " ?transformationEventURI <" + Resources.transformed + "> ?sampleURI . "
                + " ?sampleURI <" + Resources.rdfTypeLabel + "> <" + Resources.sampleLabel + ">. "
                + " ?sampleURI <" + Resources.rdfsLabel + "> ?sampleName. "
                + " OPTIONAL{ ?transformationEventURI <" + Resources.resultedIn + "> ?transformedSampleURI . "
                + " ?transformedSampleURI <" + Resources.rdfTypeLabel + "> <" + Resources.sampleLabel + ">. "
                + " ?transformedSampleURI <" + Resources.rdfsLabel + "> ?transformedSampleName. } "
                + " OPTIONAL{ ?sampleURI <" + Resources.hasCurrentLocation + "> ?placeURI . } "
                + " OPTIONAL{ ?transformedSampleURI <" + Resources.hasCurrentLocation + "> ?placeURI . } "
                + " OPTIONAL {?placeURI <" + Resources.rdfTypeLabel + "> <" + Resources.placeLabel + ">. "
                + " ?placeURI <" + Resources.rdfsLabel + "> ?placeName. } "
                + " ?sampleURI <" + Resources.belongsTo + "> ?speciesURI. "
                + " ?speciesURI <" + Resources.rdfTypeLabel + "> <" + Resources.speciesLabel + ">. "
                + " ?speciesURI <" + Resources.rdfsLabel + "> ?speciesName. "
                + " ?sequencingURI <" + Resources.rdfTypeLabel + "> <" + Resources.digitizationProcessLabel + "> . "
                + " ?sequencingURI <" + Resources.hasType + "> \"Sequencing\" . "
                + " ?sequencingURI <" + Resources.digitized + "> ?sampleURI . "
                + " OPTIONAL {?sequencingURI <" + Resources.hasNote + "> ?description . } "
                + " ?sequencingURI <" + Resources.happenedOnDevice + "> ?deviceURI . "
                + " ?sequencingURI <" + Resources.createdDerivative + "> ?productURI . "
                + " ?datasetURI <" + Resources.rdfTypeLabel + "> <" + Resources.datasetLabel + "> . "
                + " ?datasetURI <" + Resources.refersTo + "> ?sequencingURI  . "
                + " ?datasetURI <" + Resources.rdfsLabel + "> ?datasetName . "
                + " ?deviceURI <" + Resources.rdfTypeLabel + "> <" + Resources.digitalDeviceLabel + "> . "
                + " ?deviceURI <" + Resources.rdfsLabel + "> ?deviceName . "
                + " ?deviceURI <" + Resources.hasType + "> ?deviceType. "
                + " ?postprocessingURI <" + Resources.rdfTypeLabel + "> <" + Resources.formalDerivationEventLabel + "> . "
                + " ?postprocessingURI <" + Resources.hasCreated + "> ?postProductURI . "
                + " ?productURI <" + Resources.wasDerivationSourceFor + "> ?postprocessingURI . "
                + " ?productURI <" + Resources.rdfTypeLabel + "> <" + Resources.dataObjectLabel + "> . "
                + " ?productURI <" + Resources.rdfsLabel + "> ?productName . "
                + " ?postProductURI <" + Resources.rdfTypeLabel + "> <" + Resources.dataObjectLabel + "> . "
                + " ?postProductURI <" + Resources.rdfsLabel + "> ?postProductName . "
                + " OPTIONAL{ ?placeURI <" + Resources.hasDimension + "> ?dimensionURI . "
                + " ?dimensionURI <" + Resources.rdfTypeLabel + "> <" + Resources.dimensionLabel + "> . "
                //                        +" ?dimensionTypeURI <"+Resources.rdfTypeLabel+"> <"+Resources.dimensionTypeLabel+">. "
                + " ?dimensionTypeURI <" + Resources.rdfsLabel + "> ?dimensionName. "
                + " ?dimensionURI <" + Resources.hasType + "> ?dimensionTypeURI. }"
                + " OPTIONAL { ?dimensionURI <" + Resources.hasValue + "> ?dimensionValue. } "
                + " OPTIONAL { ?dimensionURI <" + Resources.hasUnit + "> ?dimensionUnit.  }"
                + " FILTER regex(?speciesName,'" + species + "',\"i\") "
                + " FILTER regex(?datasetURI,'" + datasetURI + "',\"i\")  "
                + " FILTER regex(?deviceName,'" + device + "',\"i\") "
                + " FILTER regex(?sampleName,'" + sample + "',\"i\")} "
                + " LIMIT " + limit
                + " OFFSET " + offset;

        logger.debug("Submitting the query: \"" + queryString + "\"");
        List<BindingSet> sparqlresults = this.repoManager.query(queryString);
        logger.debug("The result returned " + sparqlresults.size() + " results");
        List<GensSampleStruct> results = new ArrayList<>();
        for (BindingSet result : sparqlresults) {
            GensSampleStruct struct = new GensSampleStruct()
                    .withTransformationURI(result.getValue("transformationEventURI").stringValue())
                    .withSampleURI(result.getValue("sampleURI").stringValue())
                    .withSampleName(result.getValue("sampleName").stringValue())
                    .withPostProcessingURI(result.getValue("postprocessingURI").stringValue())
                    .withSequencingURI(result.getValue("sequencingURI").stringValue())
                    .withSpeciesName(result.getValue("speciesName").stringValue())
                    .withSpeciesURI(result.getValue("speciesURI").stringValue())
                    .withProductName(result.getValue("productName").stringValue())
                    .withProductURI(result.getValue("productURI").stringValue())
                    .withPostProductName(result.getValue("postProductName").stringValue())
                    .withPostProductURI(result.getValue("postProductURI").stringValue())
                    .withDeviceName(result.getValue("deviceName").stringValue())
                    .withDeviceURI(result.getValue("deviceURI").stringValue())
                    .withDeviceType(result.getValue("deviceType").stringValue())
                    .withDatasetName(result.getValue("datasetName").stringValue())
                    .withDatasetURI(result.getValue("datasetURI").stringValue());

            if (result.getValue("placeURI") != null) {
                struct.withPlaceURI(result.getValue("placeURI").stringValue());
            }

            if (result.getValue("placeName") != null) {
                struct.withPlaceName(result.getValue("placeName").stringValue());
            }

            if (result.getValue("description") != null) {
                struct.withDescription(result.getValue("description").stringValue());
            }

            if (result.getValue("transformedSampleURI") != null) {
                struct.withTransformedSampleURI(result.getValue("transformedSampleURI").stringValue());
            }

            if (result.getValue("transformedSampleName") != null) {
                struct.withTransformedSampleName(result.getValue("transformedSampleName").stringValue());
            }

            if (result.getValue("dimensionName") != null) {
                struct.withDimensionName(result.getValue("dimensionName").stringValue());
            }

            if (result.getValue("dimensionURI") != null) {
                struct.withDimensionURI(result.getValue("dimensionURI").stringValue());
            }

            if (result.getValue("dimensionValue") != null) {
                struct.withDimensionValue(result.getValue("dimensionValue").stringValue());
            }

            if (result.getValue("dimensionUnit") != null) {
                struct.withDimensionUnit(result.getValue("dimensionUnit").stringValue());
            }

            if (result.getValue("dimensionTypeURI") != null) {
                struct.withDimensionType(result.getValue("dimensionTypeURI").stringValue());
            }

            results.add(struct);
        }
        return results;
    }    
     
    public List<MicroCTSpecimenStruct> searchMicroCTSpecimen(String specimen, String collection, String species, String provider, String datasetURI, int offset, int limit, String repositoryGraph) throws QueryExecutionException {
        logger.info("Request for MicroCT specimen search with parameters "
                   +"specimen: ["+specimen+"], "
                   +"collection: ["+collection+"], "
                   +"species: ["+species+"], "
                   +"provider: ["+provider+"], "
                   +"datasetURI: ["+datasetURI+"], "
                   +"limit: ["+(limit<0?"N/A":String.valueOf(limit))+"], "
                   +"offset: ["+(offset<0?"N/A":String.valueOf(offset))+"], "
                   +"reposytoryGraph: ["+repositoryGraph+"], ");
        String queryString = "SELECT DISTINCT ?specimenName ?specimenURI ?specimen_id ?collectionName ?collectionURI ?providerName ?providerURI "
                                            +"?speciesName ?speciesURI ?dimensionTypeURI ?dimensionName ?dimensionURI ?dimensionValue ?dimensionUnit "
                                            +"?institutionURI ?institutionName ?datasetURI ?datasetName ?description ?fixation ?preservationMedium ?storagePlace "
                                            +"?material ?taxonomic_group "
                +"FROM <"+repositoryGraph+"> "
                +"WHERE{ "
                +"?specimenURI <"+Resources.rdfTypeLabel+"> <"+Resources.specimenLabel+">. "
                +"?specimenURI <"+Resources.rdfsLabel+"> ?specimenName. "
                +"?specimenURI <"+Resources.isIdentifiedBy+"> ?specimen_id_uri. "
                +"?specimen_id_uri <"+Resources.rdfsLabel+"> ?specimen_id. "
                +"?specimenURI <"+Resources.hasNote+"> ?description. "
                +"?datasetURI <"+Resources.rdfTypeLabel+"> <"+Resources.datasetLabel+">. "
                +"?datasetURI <"+Resources.refersTo+"> ?specimenURI. "
                +"?datasetURI <"+Resources.rdfsLabel+"> ?datasetName. "
                +"?specimenURI <"+Resources.wasProvidedBy+"> ?providerURI. "
                +"?providerURI <"+Resources.rdfsLabel+"> ?providerName. "
                +"?specimenURI <"+Resources.hasDimension+"> ?dimensionURI. "
                +"?dimensionURI <"+Resources.rdfTypeLabel+"> <"+Resources.dimensionLabel+">. "
                +"?dimensionURI <"+Resources.hasType+"> ?dimensionTypeURI. "
                +"?dimensionTypeURI <"+Resources.rdfsLabel+"> ?dimensionName.  "
                +"?dimensionURI <"+Resources.hasValue+"> ?dimensionValue. "
                +"?dimensionURI <"+Resources.hasUnit+"> ?dimensionUnit. "
                +"?specimenURI <"+Resources.LC12_wasAttributedBy+"> ?fixationUri. "
                +"?fixationUri <"+Resources.hasType+"> ?fixationTypeUri. "
                +"?fixationUri <"+Resources.rdfsLabel+"> ?fixation. "
                +"?fixationTypeUri <"+Resources.rdfTypeLabel+"> <"+Resources.typeLabel+">. " 
                +"?fixationTypeUri <"+Resources.rdfsLabel+"> \""+Resources.fixationLabel+"\". "
                +"?specimenURI <"+Resources.LC12_wasAttributedBy+"> ?preservationMediumUri. "
                +"?preservationMediumUri <"+Resources.hasType+"> ?preservationMediumTypeUri. "
                +"?preservationMediumUri <"+Resources.rdfsLabel+"> ?preservationMedium. "
                +"?preservationMediumTypeUri <"+Resources.rdfTypeLabel+"> <"+Resources.typeLabel+">. " 
                +"?preservationMediumTypeUri <"+Resources.rdfsLabel+"> \""+Resources.preservationMediumLabel+"\". "
                +"OPTIONAL { "
                    +"?specimenURI <"+Resources.LC16_isComposedOf+"> ?material_uri. "
                    +"?material_uri <"+Resources.rdfsLabel+"> ?material. "
                +" } "
                +"OPTIONAL { "
                    +"?specimenURI <"+Resources.hasSection+"> ?storagePlaceUri. "
                    +"?storagePlaceUri <"+Resources.rdfsLabel+"> ?storagePlace. "
                +"} "
                +"OPTIONAL { "
                    +"?specimenURI <"+Resources.formsPartOf+"> ?collectionURI. "
                    +"?collectionURI <"+Resources.rdfTypeLabel+"> <"+Resources.collectionLabel+">. "
                    +"?collectionURI <"+Resources.rdfsLabel+"> ?collectionName."
                +"} "
                +"OPTIONAL { "
                    +"?specimenURI <"+Resources.belongsTo+"> ?speciesURI. "
                    +"?speciesURI <"+Resources.rdfTypeLabel+"> <"+Resources.speciesLabel+">. "
                    +"?speciesURI <"+Resources.rdfsLabel+"> ?speciesName. "
                +"} "
                +"OPTIONAL { "
                    +"?specimenURI <"+Resources.belongsTo+"> ?speciesURI. "
                    +"?speciesURI <"+Resources.rdfTypeLabel+"> <"+Resources.speciesLabel+">. " 
                    +"?speciesURI <"+Resources.belongsTo+"> ?taxonomic_group_uri. " 
                    +"?taxonomic_group_uri <"+Resources.rdfTypeLabel+"> <"+Resources.bioticElementTypeLabel+">. " 
                    +"?taxonomic_group_uri <"+Resources.rdfsLabel+"> ?taxonomic_group. "
                +"} "
                +"OPTIONAL { "
                    +"?providerURI <"+Resources.isCurrentMemberOf+"> ?institutionURI. "
                    +"?institutionURI <"+Resources.rdfsLabel+"> ?institutionName. "
                +"} ";
        if(collection!=null && !collection.isEmpty()){
            queryString+="FILTER CONTAINS(LCASE(?collectionName),\""+collection.toLowerCase()+"\"). ";
        }
        if(provider!=null && !provider.isEmpty()){
            queryString+="FILTER CONTAINS(LCASE(?providerName),\""+provider.toLowerCase()+"\"). ";
        }
        if(specimen!=null && !specimen.isEmpty()){
            queryString+="FILTER CONTAINS(LCASE(?specimenName),\""+specimen.toLowerCase()+"\"). ";
        }
        if(datasetURI!=null && !datasetURI.isEmpty()){
            queryString+="FILTER CONTAINS(LCASE(STR(?datasetURI)),\""+datasetURI.toLowerCase()+"\"). ";
        }
        if(species!=null && !species.isEmpty()){
            queryString+="FILTER CONTAINS(LCASE(?speciesName),\""+species.toLowerCase()+"\"). ";
        }
        queryString+=" } ";
        if(limit>0 && offset>=0){
            queryString+="LIMIT "+limit+" "
                        +"OFFSET "+offset;
        }

        logger.debug("Submitting the query: \"" + queryString + "\"");
        List<BindingSet> sparqlresults = this.repoManager.query(queryString);
        logger.debug("The SPARQL query returned " + sparqlresults.size() + " results (RAW SPARQL results)");
        List<MicroCTSpecimenStruct> results = new ArrayList<>();
        for (BindingSet result : sparqlresults) {
            MicroCTSpecimenStruct struct = new MicroCTSpecimenStruct().withSpecimenURI(result.getValue("specimenURI").stringValue())
                    .withSpecimenName(result.getValue("specimenName").stringValue())
                    .withSpecimenID(result.getValue("specimen_id").stringValue())
                    .withDatasetURI(result.getValue("datasetURI").stringValue())
                    .withDatasetName(result.getValue("datasetName").stringValue());
            if(result.getValue("speciesURI")!=null){
                struct.withSpeciesURI(result.getValue("speciesURI").stringValue())
                      .withSpeciesName(result.getValue("speciesName").stringValue());
            }
            if (result.getValue("collectionName") != null) {
                struct.withCollectionName(result.getValue("collectionName").stringValue());
            }
            if (result.getValue("collectionURI") != null) {
                struct.withCollectionURI(result.getValue("collectionURI").stringValue());
            }

            if (result.getValue("providerName") != null) {
                struct.withProviderName(result.getValue("providerName").stringValue());
            }
            if (result.getValue("providerURI") != null) {
                struct.withProviderURI(result.getValue("providerURI").stringValue());
            }

            if (result.getValue("institutionName") != null) {
                struct.withInstitutionName(result.getValue("institutionName").stringValue());
            }
            if (result.getValue("institutionURI") != null) {
                struct.withInstitutionURI(result.getValue("institutionURI").stringValue());
            }

            if (result.getValue("description") != null) {
                struct.withDescription(result.getValue("description").stringValue());
            }

            if (result.getValue("dimensionName") != null) {
                struct.withDimensionName(result.getValue("dimensionName").stringValue());
            }

            if (result.getValue("dimensionURI") != null) {
                struct.withDimensionURI(result.getValue("dimensionURI").stringValue());
            }

            if (result.getValue("dimensionValue") != null) {
                struct.withDimensionValue(result.getValue("dimensionValue").stringValue());
            }

            if (result.getValue("dimensionUnit") != null) {
                struct.withDimensionUnit(result.getValue("dimensionUnit").stringValue());
            }

            if (result.getValue("dimensionTypeURI") != null) {
                struct.withDimensionTypeURI(result.getValue("dimensionTypeURI").stringValue());
            }
            
            if (result.getValue("fixation") != null) {
                struct.withFixationType(result.getValue("fixation").stringValue());
            }
            if (result.getValue("preservationMedium") != null) {
                struct.withPreservationType(result.getValue("preservationMedium").stringValue());
            }
            if (result.getValue("storagePlace") != null) {
                struct.withStoragePlace(result.getValue("storagePlace").stringValue());
            }
            if (result.getValue("material") != null) {
                struct.withMaterial(result.getValue("material").stringValue());
            }
            if (result.getValue("taxonomic_group") != null) {
                struct.withTaxonomicGroup(result.getValue("taxonomic_group").stringValue());
            }
            results.add(struct);
        }
        logger.debug("The query returned "+results.size()+" MicroCT specimen objects");
        return results;
    }

    public List<MicroCTScanningStruct> searchMicroCTScanning(String speciesName, String filter, String contrastMethod, int offset, int limit, String repositoryGraph) throws QueryExecutionException {
        logger.info("Request for MicroCT scanning search with parameters "
                   +"species: ["+speciesName+"], "
                   +"filter: ["+filter+"], "
                   +"contrastMethod: ["+contrastMethod+"], "
                   +"limit: ["+(limit<0?"N/A":String.valueOf(limit))+"], "
                   +"offset: ["+(offset<0?"N/A":String.valueOf(offset))+"], "
                   +"reposytoryGraph: ["+repositoryGraph+"], ");
        String queryString="SELECT ?microct_scan_uri "
                          +"FROM <"+repositoryGraph+"> "
                          +"WHERE{ "
                          +"?microct_scan_uri <"+Resources.rdfTypeLabel+"> <"+Resources.digitizationProcessLabel+">. ";
        if(speciesName!=null && !speciesName.isBlank()){
            queryString+="?microct_scan_uri <"+Resources.digitized+"> ?specimen_uri. "
                        +"?specimen_uri <"+Resources.belongsTo+"> ?species_uri. "
                        +"?species_uri <"+Resources.rdfsLabel+"> ?species_name. "
                        +"FILTER CONTAINS(LCASE(?species_name),\""+speciesName.toLowerCase()+"\"). ";
        }
        if(filter!=null && !filter.isBlank()){
            queryString+="?microct_scan_uri <"+Resources.observedDimension+"> ?filter_dimension_uri. "
                        +"?filter_dimension_uri <"+Resources.hasType+"> ?filter_dimension_type_uri. "
                        +"?filter_dimension_uri <"+Resources.hasValue+"> ?filter. "
                        +"?filter_dimension_type_uri <"+Resources.rdfsLabel+"> \"filter\". "
                        +"FILTER (LCASE(?filter)=\""+filter.toLowerCase()+"\"). ";
        }
        if(contrastMethod!=null && !contrastMethod.isBlank()){
            queryString+="?microct_scan_uri <"+Resources.hasContrastMethod+"> ?contrast_method. "
                        +"FILTER (LCASE(?contrast_method)=\""+contrastMethod.toLowerCase()+"\"). ";
        }
        queryString+="}";
        
        logger.debug("microct URI selection query: "+queryString);
        List<BindingSet> sparqlresults = this.repoManager.query(queryString); 
        logger.debug("microct URI selection query returned "+sparqlresults.size()+" results");
        List<String> microctUris=new ArrayList<>();
        for(BindingSet result : sparqlresults) {
            microctUris.add(result.getValue("microct_scan_uri").stringValue());
        }
        if(microctUris.isEmpty()){
            logger.debug("No MicroCT URIs were found");
            return new ArrayList<>();
        }else if(microctUris.size()<offset){
            logger.debug("No more MicroCT URIs were found");
            return new ArrayList<>();
        }else{
            if(offset>=0 && limit>0){
                if(microctUris.size()>(offset+limit)){
                    logger.debug("Retrieve information for MicroCT URIs with OFFSET/LIMIT "+offset+"/"+limit);
                    microctUris=microctUris.subList(offset, offset+limit);
                }else{
                    logger.debug("Retrieve information for MicroCT URIs with OFFSET/LIMIT "+offset+"/"+microctUris.size());
                    microctUris=microctUris.subList(offset, microctUris.size());
                }
            }
        }
       
        String microctQuery="SELECT ?microct_scan_uri ?scan_id ?contrast_method ?protocol ?preparation_timespan_begin ?preparation_timespan_end ?notes "
                                  +"?scan_scope ?sample_holder ?scanning_medium ?scanned_part ?person ?scan_date ?scan_duration ?device ?voltage ?current "
                                  +"?filter ?zoom ?camera_resolution ?frame_averaging ?random_movement ?scan_degrees ?exposure_time ?oversize_settings "
                                  +"?file_location "
                           +"FROM <"+repositoryGraph+"> "
                           +"WHERE{ "
                            +"?microct_scan_uri <"+Resources.isIdentifiedBy+"> ?scan_id_uri. " 
                            +"?scan_id_uri <"+Resources.rdfsLabel+"> ?scan_id. " 
                            +"?microct_scan_uri <"+Resources.hasContrastMethod+"> ?contrast_method. "
                            +"OPTIONAL{ "
                                +"?microct_scan_uri <"+Resources.hasProtocol+"> ?protocol. "
                            +"} "
                            +"OPTIONAL{ "
                                +"?microct_scan_uri <"+Resources.hasTimespan+"> ?preparation_timespan_uri. "
                                +"?preparation_timespan_uri <"+Resources.beginOfTheBegin+"> ?preparation_timespan_begin. "
                                +"?preparation_timespan_uri <"+Resources.endOfTheEnd+"> ?preparation_timespan_end. "
                                +"?preparation_timespan_uri <"+Resources.hasType+"> ?preparation_timespan_type_uri. "
                                +"?preparation_timespan_type_uri <"+Resources.rdfsLabel+"> \"preparation time\". "
                            +"} "
                            +"OPTIONAL{ "
                                +"?microct_scan_uri <"+Resources.hasNote+"> ?notes. "
                            +"} "
                            +"?microct_scan_uri <"+Resources.hadSpecificPurpose+"> ?scan_scope_uri. "
                            +"?scan_scope_uri <"+Resources.rdfsLabel+"> ?scan_scope. "
                            +"?microct_scan_uri <"+Resources.usingSampleHolder+"> ?sample_holder_uri. "
                            +"?sample_holder_uri <"+Resources.rdfsLabel+"> ?sample_holder. "
                            +"?microct_scan_uri <"+Resources.usingScanningMedium+"> ?scanning_medium_uri. "
                            +"?scanning_medium_uri <"+Resources.rdfsLabel+"> ?scanning_medium. "
                            +"OPTIONAL{ "
                                +"?microct_scan_uri <"+Resources.hasType+"> ?scanned_part_uri. "
                                +"?scanned_part_uri <"+Resources.rdfsLabel+"> ?scanned_part. "
                                +"?scanned_part_uri <"+Resources.hasType+"> ?scanned_part_type_uri. "
                                +"?scanned_part_type_uri <"+Resources.rdfsLabel+"> \"scanned part\". "
                            +"} "
                            +"?microct_scan_uri <"+Resources.carriedOutBy+"> ?person_uri. "
                            +"?person_uri <"+Resources.rdfsLabel+"> ?person. "
                            +"?microct_scan_uri <"+Resources.hasTimespan+"> ?scan_date_uri. "
                            +"?scan_date_uri <"+Resources.atSomeTimeWithin+"> ?scan_date. "
                            +"?scan_date_uri <"+Resources.hasType+"> ?scan_date_type_uri. "
                            +"?scan_date_type_uri <"+Resources.rdfsLabel+"> \"scanning\". "
                            +"OPTIONAL{ "
                                +"?microct_scan_uri <"+Resources.hasTimespan+"> ?scan_duration_timespan_uri. "
                                +"?scan_duration_timespan_uri <"+Resources.hadAtMostDuration+"> ?scan_duration_uri. "
                                +"?scan_duration_uri <"+Resources.rdfsLabel+"> ?scan_duration. "
                                +"?scan_duration_timespan_uri <"+Resources.hasType+"> ?scan_duration_type_uri. "
                                +"?scan_duration_type_uri <"+Resources.rdfsLabel+"> \"scanning duration\". "
                            +"} "
                            +"?microct_scan_uri <"+Resources.happenedOnDevice+"> ?device_uri. "
                            +"?device_uri <"+Resources.rdfsLabel+"> ?device. "
                            +"?microct_scan_uri <"+Resources.observedDimension+"> ?voltage_dimension_uri. "
                            +"?voltage_dimension_uri <"+Resources.hasValue+"> ?voltage. "
                            +"?voltage_dimension_uri <"+Resources.hasType+"> ?voltage_dimension_type_uri. "
                            +"?voltage_dimension_type_uri <"+Resources.rdfsLabel+"> \"voltage\". "
                            +"?microct_scan_uri <"+Resources.observedDimension+"> ?current_dimension_uri. "
                            +"?current_dimension_uri <"+Resources.hasValue+"> ?current. "
                            +"?current_dimension_uri <"+Resources.hasType+"> ?current_dimension_type_uri. "
                            +"?current_dimension_type_uri <"+Resources.rdfsLabel+"> \"current\". "
                            +"?microct_scan_uri <"+Resources.observedDimension+"> ?filter_dimension_uri. "
                            +"?filter_dimension_uri <"+Resources.hasValue+"> ?filter. "
                            +"?filter_dimension_uri <"+Resources.hasType+"> ?filter_dimension_type_uri. "
                            +"?filter_dimension_type_uri <"+Resources.rdfsLabel+"> \"filter\". "
                            +"?microct_scan_uri <"+Resources.observedDimension+"> ?zoom_dimension_uri. "
                            +"?zoom_dimension_uri <"+Resources.hasValue+"> ?zoom. "
                            +"?zoom_dimension_uri <"+Resources.hasType+"> ?zoom_dimension_type_uri. "
                            +"?zoom_dimension_type_uri <"+Resources.rdfsLabel+"> \"zoom\". "
                            +"?microct_scan_uri <"+Resources.observedDimension+"> ?camera_resolution_dimension_uri. "
                            +"?camera_resolution_dimension_uri <"+Resources.hasValue+"> ?camera_resolution. "
                            +"?camera_resolution_dimension_uri <"+Resources.hasType+"> ?camera_resolution_dimension_type_uri. "
                            +"?camera_resolution_dimension_type_uri <"+Resources.rdfsLabel+"> \"camera resolution\". "
                            +"OPTIONAL{ "
                                +"?microct_scan_uri <"+Resources.observedDimension+"> ?frame_averaging_dimension_uri. "
                                +"?frame_averaging_dimension_uri <"+Resources.hasValue+"> ?frame_averaging. "
                                +"?frame_averaging_dimension_uri <"+Resources.hasType+"> ?frame_averaging_dimension_type_uri. "
                                +"?frame_averaging_dimension_type_uri <"+Resources.rdfsLabel+"> \"frame averaging\". "
                            +"} "
                            +"OPTIONAL{ "
                                +"?microct_scan_uri <"+Resources.observedDimension+"> ?random_movement_dimension_uri. "
                                +"?random_movement_dimension_uri <"+Resources.hasValue+"> ?random_movement. "
                                +"?random_movement_dimension_uri <"+Resources.hasType+"> ?random_movement_dimension_type_uri. "
                                +"?random_movement_dimension_type_uri <"+Resources.rdfsLabel+"> \"random movement\". "
                            +"} "
                            +"?microct_scan_uri <"+Resources.observedDimension+"> ?scan_degrees_dimension_uri. "
                            +"?scan_degrees_dimension_uri <"+Resources.hasValue+"> ?scan_degrees. "
                            +"?scan_degrees_dimension_uri <"+Resources.hasType+"> ?scan_degrees_dimension_type_uri. "
                            +"?scan_degrees_dimension_type_uri <"+Resources.rdfsLabel+"> \"scan degrees\". "
                            +"OPTIONAL{ "
                                +"?microct_scan_uri <"+Resources.observedDimension+"> ?exposure_time_dimension_uri. "
                                +"?exposure_time_dimension_uri <"+Resources.hasValue+"> ?exposure_time. "
                                +"?exposure_time_dimension_uri <"+Resources.hasType+"> ?exposure_time_dimension_type_uri. "
                                +"?exposure_time_dimension_type_uri <"+Resources.rdfsLabel+"> \"exposure time\". "
                            +"} "
                            +"OPTIONAL{ "
                                +"?microct_scan_uri <"+Resources.observedDimension+"> ?oversize_settings_dimension_uri. "
                                +"?oversize_settings_dimension_uri <"+Resources.hasValue+"> ?oversize_settings. "
                                +"?oversize_settings_dimension_uri <"+Resources.hasType+"> ?oversize_settings_dimension_type_uri. "
                                +"?oversize_settings_dimension_type_uri <"+Resources.rdfsLabel+"> \"οversize settings\". "
                            +"} "
                            +"OPTIONAL{ "
                                +"?microct_scan_uri <"+Resources.hadOutput+"> ?output_uri. "
                                +"?output_uri <"+Resources.isStoredOn+"> ?file_location_uri. "
                                +"?file_location_uri <"+Resources.rdfsLabel+"> ?file_location. "
                            +"} ";
        microctQuery+="FILTER( ";
        for(String microctURI : microctUris){
            microctQuery+="?microct_scan_uri=<"+microctURI+"> || ";
        }
        microctQuery=microctQuery.substring(0, microctQuery.length()-3);
        microctQuery+=").}";
        logger.debug("microct search query: "+microctQuery);
        sparqlresults = this.repoManager.query(microctQuery);
        logger.debug("microct search query returned "+sparqlresults.size()+" (RAW) results");
        Map<String,MicroCTScanningStruct> retMap=new HashMap<>();
        for(BindingSet result : sparqlresults){
            String microctURI=result.getValue("microct_scan_uri").stringValue();
            if(!retMap.containsKey(microctURI)){
                MicroCTScanningStruct microctStruct=new MicroCTScanningStruct();
                microctStruct.withScanningURI(microctURI);
                microctStruct.withScanningLabel(result.getValue("scan_id").stringValue());
                microctStruct.withContrastMethod(result.getValue("contrast_method").stringValue());
                if(result.getValue("protocol")!=null){
                    microctStruct.withProtocol(result.getValue("protocol").stringValue());
                }
                if(result.getValue("preparation_timespan_begin")!=null){
                    microctStruct.withPreparationTimestampStart(result.getValue("preparation_timespan_begin").stringValue());
                }
                if(result.getValue("preparation_timespan_end")!=null){
                    microctStruct.withPreparationTimestampEnd(result.getValue("preparation_timespan_end").stringValue());
                }
                if(result.getValue("notes")!=null){
                    microctStruct.withDescription(result.getValue("notes").stringValue());
                }
                microctStruct.withScopeOfScan(result.getValue("scan_scope").stringValue());
                microctStruct.withSampleHolder(result.getValue("sample_holder").stringValue());
                microctStruct.withScanningMedium(result.getValue("scanning_medium").stringValue());
                if(result.getValue("scanned_part")!=null){
                    microctStruct.withScannedPart(result.getValue("scanned_part").stringValue());
                }
                microctStruct.withActorName(result.getValue("person").stringValue());
                microctStruct.withScanDate(result.getValue("scan_date").stringValue());
                if(result.getValue("scan_duration")!=null){
                    microctStruct.withScanningDuration(result.getValue("scan_duration").stringValue());
                }
                microctStruct.withDeviceName(result.getValue("device").stringValue());
                microctStruct.withVoltage(result.getValue("voltage").stringValue());
                microctStruct.withCurrent(result.getValue("current").stringValue());
                microctStruct.withFilter(result.getValue("filter").stringValue());
                microctStruct.withZoom(result.getValue("zoom").stringValue());
                microctStruct.withCameraResolution(result.getValue("camera_resolution").stringValue());
                if(result.getValue("frame_averaging")!=null){
                    microctStruct.withAveraging(result.getValue("frame_averaging").stringValue());
                }
                if(result.getValue("random_movement")!=null){
                    microctStruct.withRandomMovement(result.getValue("random_movement").stringValue());
                }
                microctStruct.withScanDegrees(result.getValue("scan_degrees").stringValue());
                if(result.getValue("exposure_time")!=null){
                    microctStruct.withExposureTime(result.getValue("exposure_time").stringValue());
                }
                if(result.getValue("oversize_settings")!=null){
                    microctStruct.withOversizeSettings(result.getValue("oversize_settings").stringValue());
                }
                if(result.getValue("file_location")!=null){
                    microctStruct.withFileLocation(result.getValue("file_location").stringValue());
                }
                retMap.put(microctURI, microctStruct);
            }else{
                MicroCTScanningStruct microctStruct=retMap.get(microctURI);
                String description=microctStruct.getDescription();
                if(result.getValue("notes")!=null){
                    microctStruct.withDescription(description+result.getValue("notes").stringValue());
                }
                retMap.put(microctURI, microctStruct);
            }
        }
        String specimenQuery="SELECT ?microct_scan_uri ?specimen_uri ?specimen_id ?specimen_label ?person ?institution ?note ?material "
                                   +"?species ?size ?fixation ?preservation_medium ?storage_place ?taxonomic_group ?collection "
                           +"FROM <"+repositoryGraph+"> "
                           +"WHERE{ "
                           +"?microct_scan_uri <"+Resources.digitized+"> ?specimen_uri. "
                           +"?specimen_uri <"+Resources.isIdentifiedBy+"> ?specimen_id_uri. "
                           +"?specimen_id_uri <"+Resources.rdfsLabel+"> ?specimen_id. "
                           +"OPTIONAL{ "
                                +"?specimen_uri <"+Resources.rdfsLabel+"> ?specimen_label. " 
                           +"} "
                           +"OPTIONAL{ "
                                +"?specimen_uri <"+Resources.wasProvidedBy+"> ?person_uri. "
                                +"?person_uri <"+Resources.rdfsLabel+"> ?person. "
                                +"?person_uri <"+Resources.isCurrentMemberOf+"> ?institution_uri. "
                                +"?institution_uri <"+Resources.rdfsLabel+"> ?institution. "
                           +"} "
                           +"OPTIONAL{ "
                                +"?specimen_uri <"+Resources.hasNote+"> ?note. "
                           +"} "
                           +"?specimen_uri <"+Resources.LC16_isComposedOf+"> ?material_uri. "
                           +"?material_uri <"+Resources.rdfsLabel+"> ?material. "
                           +"OPTIONAL{ "
                                +"?specimen_uri <"+Resources.belongsTo+"> ?species_uri. "
                                +"?species_uri <"+Resources.rdfsLabel+"> ?species. "
                                +"?species_uri <"+Resources.belongsTo+"> ?taxonomic_group_uri. "
                                +"?taxonomic_group_uri <"+Resources.rdfsLabel+"> ?taxonomic_group. "
                                +"?taxonomic_group_uri <"+Resources.hasType+"> ?taxonomic_group_type_uri. "
                                +"?taxonomic_group_type_uri <"+Resources.rdfsLabel+"> \"taxonomic group\". "
                                
                           +"} "
                           +"OPTIONAL{ "
                                +"?specimen_uri <"+Resources.hasDimension+"> ?size_uri. "
                                +"?size_uri <"+Resources.hasValue+"> ?size. "
                           +"} "
                           +"OPTIONAL{ "
                                +"?specimen_uri <"+Resources.LC12_wasAttributedBy+"> ?fixation_uri. "
                                +"?fixation_uri <"+Resources.rdfsLabel+"> ?fixation. "
                                +"?fixation_uri <"+Resources.hasType+"> ?fixation_type_uri. "
                                +"?fixation_type_uri <"+Resources.rdfsLabel+"> \"fixation\". "
                           +"} "
                           +"OPTIONAL{ "
                                +"?specimen_uri <"+Resources.LC12_wasAttributedBy+"> ?preservation_medium_uri. "
                                +"?preservation_medium_uri <"+Resources.rdfsLabel+"> ?preservation_medium. "
                                +"?preservation_medium_uri <"+Resources.hasType+"> ?preservation_medium_type_uri. "
                                +"?preservation_medium_type_uri <"+Resources.rdfsLabel+"> \"preservation medium\". "
                           +"} "
                           +"OPTIONAL{ "
                                +"?specimen_uri <"+Resources.hasSection+"> ?storage_place_uri. "
                                +"?storage_place_uri <"+Resources.rdfsLabel+"> ?storage_place. "
                           +"} "
                           +"OPTIONAL{ "
                                +"?specimen_uri <"+Resources.formsPartOf+"> ?collection_uri. "
                                +"?collection_uri <"+Resources.rdfsLabel+"> ?collection. "
                           +"} "
                
                
                ;
        specimenQuery+="FILTER(";
        for(String microctURI : retMap.keySet()){
            specimenQuery+="?microct_scan_uri=<"+microctURI+"> || ";
        }
        specimenQuery=specimenQuery.substring(0, specimenQuery.length()-3);
        specimenQuery+=")}";
        logger.debug("specimens search query: "+specimenQuery);
        sparqlresults = this.repoManager.query(specimenQuery);
        logger.debug("specimens search query returned "+sparqlresults.size()+" (RAW) results");
        Map<String,MicroCTSpecimenStruct> specimensMap=new HashMap<>();
        Map<String,String> microctScanAndSpecimens=new HashMap<>();
        for(BindingSet result : sparqlresults){
            String microctScanUri=result.getValue("microct_scan_uri").stringValue();
            String specimenUri=result.getValue("specimen_uri").stringValue();
            microctScanAndSpecimens.put(microctScanUri, specimenUri);
            if(!specimensMap.containsKey(specimenUri)){
                MicroCTSpecimenStruct specimenStruct=new MicroCTSpecimenStruct();
                specimenStruct.withSpecimenURI(specimenUri);
                specimenStruct.withSpecimenID(result.getValue("specimen_id").stringValue());
                if(result.getValue("specimen_label")!=null){
                    specimenStruct.withSpecimenName(result.getValue("specimen_label").stringValue());
                }
                if(result.getValue("person")!=null){
                    specimenStruct.withProviderName(result.getValue("person").stringValue());
                }
                if(result.getValue("institution")!=null){
                    specimenStruct.withInstitutionName(result.getValue("institution").stringValue());
                }
                if(result.getValue("note")!=null){
                    specimenStruct.withDescription(result.getValue("note").stringValue());
                }
                specimenStruct.withMaterial(result.getValue("material").stringValue());
                if(result.getValue("species")!=null){
                    specimenStruct.withSpeciesName(result.getValue("species").stringValue());
                }
                if(result.getValue("size")!=null){
                    specimenStruct.withDimensionValue(result.getValue("size").stringValue());
                }
                if(result.getValue("fixation")!=null){
                    specimenStruct.withFixationType(result.getValue("fixation").stringValue());
                }
                if(result.getValue("preservation_medium")!=null){
                    specimenStruct.withPreservationType(result.getValue("preservation_medium").stringValue());
                }
                if(result.getValue("storage_place")!=null){
                    specimenStruct.withStoragePlace(result.getValue("storage_place").stringValue());
                }
                if(result.getValue("taxonomic_group")!=null){
                    specimenStruct.withTaxonomicGroup(result.getValue("taxonomic_group").stringValue());
                }
                if(result.getValue("collection")!=null){
                    specimenStruct.withCollectionName(result.getValue("collection").stringValue());
                }
                
                specimensMap.put(specimenUri, specimenStruct);
            }else{
                // do something else (do we have multi values?)
            }
        }
        for(MicroCTScanningStruct microCtScan : retMap.values()){
            String specimenUri=microctScanAndSpecimens.get(microCtScan.getScanningURI());
            if(specimenUri!=null){
                MicroCTSpecimenStruct specimenStruct=specimensMap.get(specimenUri);
                if(specimenStruct!=null){
                    microCtScan.withSpecimen(specimenStruct);
                }else{
                    logger.warn("Unable to find specimen struct with URI "+specimenUri);
                }
            }else{
                logger.warn("Unable to find specimen URI for microCT Scanning with URI "+microCtScan.getScanningURI());
            }
        }
        
        return new ArrayList<>(retMap.values());
    }

    public List<MicroCTReconstructionStruct> searchMicroCTReconstruction(String species, String specimen, String input,String datasetURI, String repositoryGraph) throws QueryExecutionException {
        String queryString = "SELECT DISTINCT ?reconstructionURI ?reconstructionLabel ?description ?timespan ?productURI ?productName "
                + "?inputURI ?inputName ?actorURI ?actorName ?datasetName ?datasetURI "
                + "FROM <" + repositoryGraph + "> "
                + "WHERE{ "
                //+ " ?reconstructionURI <" + Resources.rdfTypeLabel + "> <" + Resources.formalDerivationEventLabel + "> . "
                + " ?reconstructionURI <" + Resources.hasType + "> \"Reconstruction\" . "
                + " ?reconstructionURI <" + Resources.hasNote + "> ?description . "
                + " ?reconstructionURI <" + Resources.hasTimespan + "> ?timespan . "
                + " ?reconstructionURI  <" + Resources.rdfsLabel + "> ?reconstructionLabel . "
                + " ?reconstructionURI  <" + Resources.createdDerivative + "> ?productURI . "
                //+ " ?productURI <" + Resources.rdfTypeLabel + "> <" + Resources.dataObjectLabel + "> . "
                + " ?productURI <" + Resources.rdfsLabel + "> ?productName. "
                + " ?datasetURI <" + Resources.refersTo + "> ?reconstructionURI . "
                //+ " ?datasetURI <" + Resources.rdfTypeLabel + "> <" + Resources.datasetLabel + "> . "
                + " ?datasetURI <" + Resources.rdfsLabel + "> ?datasetName. "
                + " ?reconstructionURI  <" + Resources.carriedOutBy + "> ?actorURI . "
                + " ?actorURI <" + Resources.rdfsLabel + "> ?actorName. "
                + " ?inputURI <" + Resources.wasDerivationSourceFor + "> ?reconstructionURI . "
                //+ " ?inputURI <" + Resources.rdfTypeLabel + "> <" + Resources.dataObjectLabel + "> . "
                + " ?inputURI <" + Resources.rdfsLabel + "> ?inputName. "
                //+ " ?scanningURI <" + Resources.rdfTypeLabel + "> <" + Resources.digitizationProcessLabel + "> . "
                + " ?scanningURI <" + Resources.digitized + "> ?specimenURI . "
                + " ?scanningURI <" + Resources.createdDerivative + "> ?inputURI . "
                //+ " ?specimenURI <" + Resources.rdfTypeLabel + "> <" + Resources.specimenLabel + "> . "
                + " ?specimenURI <" + Resources.rdfsLabel + "> ?specimenName. "
                + " ?specimenURI <" + Resources.belongsTo + "> ?speciesURI . "
                //+ " ?speciesURI <" + Resources.rdfTypeLabel + "> <" + Resources.speciesLabel + "> . "
                + " ?speciesURI <" + Resources.rdfsLabel + "> ?speciesName. "
               // + " FILTER regex(?inputName,'" + input + "',\"i\") "
                + " FILTER regex(?specimenName,'" + specimen + "',\"i\") "
                //+ " FILTER regex(?datasetURI,'" + datasetURI + "',\"i\")  "
                + " FILTER regex(?speciesName,'" + species + "',\"i\")}";
        logger.debug("Submitting the query: \"" + queryString + "\"");
        List<BindingSet> sparqlresults = this.repoManager.query(queryString);
        logger.debug("The query returned " + sparqlresults.size() + " results");
        Map<String, MicroCTReconstructionStruct> map = new HashMap<>();
        for (BindingSet result : sparqlresults) {
            if (!map.containsKey(result.getValue("reconstructionURI").stringValue())) {
                MicroCTReconstructionStruct struct = new MicroCTReconstructionStruct().withReconstructionURI(result.getValue("reconstructionURI").stringValue())
                        .withReconstruction(result.getValue("reconstructionLabel").stringValue())
                        .withInput(result.getValue("inputURI").stringValue(), result.getValue("inputName").stringValue())
                        .withDatasetURI(result.getValue("datasetURI").stringValue())
                        .withDatasetTitle(result.getValue("datasetName").stringValue())
                        .withProduct(result.getValue("productURI").stringValue(), result.getValue("productName").stringValue())
                        .withDescription(result.getValue("description").stringValue())
                        .withActorURI(result.getValue("actorURI").stringValue())
                        .withActorName(result.getValue("actorName").stringValue())
                        .withTimespan(result.getValue("timespan").stringValue());
                map.put(struct.getReconstructionURI(), struct);
            } else {
                MicroCTReconstructionStruct struct = map.get(result.getValue("reconstructionURI").stringValue());
                struct.withProduct(result.getValue("productURI").stringValue(), result.getValue("productName").stringValue());
                map.put(struct.getReconstructionURI(), struct);
            }
        }
        if (!map.isEmpty()) {
            String query = "SELECT ?reconstructionURI ?inputURI ?inputName "
                    + "FROM <" + repositoryGraph + "> "
                    + "WHERE{ "
                    + " ?inputURI <" + Resources.wasDerivationSourceFor + "> ?reconstructionURI . "
                    + " ?inputURI <" + Resources.isIdentifiedBy + "> ?inputName. "
                    + "FILTER( ";
            for (MicroCTReconstructionStruct struct : map.values()) {
                query += "?reconstructionURI = <" + struct.getReconstructionURI() + "> || ";
            }
            query = query.substring(0, query.length() - 3) + ")}";
            logger.debug("Retrieving the products using query: \"" + query + "\"");
            sparqlresults = this.repoManager.query(query);
            logger.debug("The query returned " + sparqlresults.size() + " results");
            for (BindingSet result : sparqlresults) {
                MicroCTReconstructionStruct struct = map.get(result.getValue("reconstructionURI").stringValue());
                struct.withInput(result.getValue("inputURI").stringValue(), result.getValue("inputName").stringValue());
                map.put(struct.getReconstructionURI(), struct);
            }
        }
        return new ArrayList<>(map.values());
    }

    public List<MicroCTReconstructionStruct> searchMicroCTReconstruction(String species, String specimen, String input,String datasetURI, int offset, int limit, String repositoryGraph) throws QueryExecutionException {
        String queryString = "SELECT DISTINCT ?reconstructionURI ?reconstructionLabel ?description ?timespan ?productURI ?productName "
                + "?inputURI ?inputName ?actorURI ?actorName ?datasetName ?datasetURI "
                + "FROM <" + repositoryGraph + "> "
                + "WHERE{ "
                //+ " ?reconstructionURI <" + Resources.rdfTypeLabel + "> <" + Resources.formalDerivationEventLabel + "> . "
                + " ?reconstructionURI <" + Resources.hasType + "> \"Reconstruction\" . "
                + " ?reconstructionURI <" + Resources.hasNote + "> ?description . "
                + " ?reconstructionURI <" + Resources.hasTimespan + "> ?timespan . "
                + " ?reconstructionURI  <" + Resources.rdfsLabel + "> ?reconstructionLabel . "
                + " ?reconstructionURI  <" + Resources.createdDerivative + "> ?productURI . "
                //+ " ?productURI <" + Resources.rdfTypeLabel + "> <" + Resources.dataObjectLabel + "> . "
                + " ?productURI <" + Resources.rdfsLabel + "> ?productName. "
                + " ?datasetURI <" + Resources.refersTo + "> ?reconstructionURI . "
                //+ " ?datasetURI <" + Resources.rdfTypeLabel + "> <" + Resources.datasetLabel + "> . "
                + " ?datasetURI <" + Resources.rdfsLabel + "> ?datasetName. "
                + " ?reconstructionURI  <" + Resources.carriedOutBy + "> ?actorURI . "
                + " ?actorURI <" + Resources.rdfsLabel + "> ?actorName. "
                + " ?inputURI <" + Resources.wasDerivationSourceFor + "> ?reconstructionURI . "
                //+ " ?inputURI <" + Resources.rdfTypeLabel + "> <" + Resources.dataObjectLabel + "> . "
                + " ?inputURI <" + Resources.rdfsLabel + "> ?inputName. "
                //+ " ?scanningURI <" + Resources.rdfTypeLabel + "> <" + Resources.digitizationProcessLabel + "> . "
                + " ?scanningURI <" + Resources.digitized + "> ?specimenURI . "
                + " ?scanningURI <" + Resources.createdDerivative + "> ?inputURI . "
                //+ " ?specimenURI <" + Resources.rdfTypeLabel + "> <" + Resources.specimenLabel + "> . "
                + " ?specimenURI <" + Resources.rdfsLabel + "> ?specimenName. "
                + " ?specimenURI <" + Resources.belongsTo + "> ?speciesURI . "
                //+ " ?speciesURI <" + Resources.rdfTypeLabel + "> <" + Resources.speciesLabel + "> . "
                + " ?speciesURI <" + Resources.rdfsLabel + "> ?speciesName. "
                //+ " FILTER regex(?inputName,'" + input + "',\"i\") "
                + " FILTER regex(?specimenName,'" + specimen + "',\"i\") "
               // + " FILTER regex(?datasetURI,'" + datasetURI + "',\"i\")  "
                + " FILTER regex(?speciesName,'" + species + "',\"i\")}"
                + " LIMIT " + limit
                + " OFFSET " + offset;
        logger.debug("Submitting the query: \"" + queryString + "\"");
        List<BindingSet> sparqlresults = this.repoManager.query(queryString);
        logger.debug("The query returned " + sparqlresults.size() + " results");
        Map<String, MicroCTReconstructionStruct> map = new HashMap<>();
        for (BindingSet result : sparqlresults) {
            if (!map.containsKey(result.getValue("reconstructionURI").stringValue())) {
                MicroCTReconstructionStruct struct = new MicroCTReconstructionStruct().withReconstructionURI(result.getValue("reconstructionURI").stringValue())
                        .withReconstruction(result.getValue("reconstructionLabel").stringValue())
                        .withInput(result.getValue("inputURI").stringValue(), result.getValue("inputName").stringValue())
                        .withDatasetURI(result.getValue("datasetURI").stringValue())
                        .withDatasetTitle(result.getValue("datasetName").stringValue())
                        .withProduct(result.getValue("productURI").stringValue(), result.getValue("productName").stringValue())
                        .withDescription(result.getValue("description").stringValue())
                        .withActorURI(result.getValue("actorURI").stringValue())
                        .withActorName(result.getValue("actorName").stringValue())
                        .withTimespan(result.getValue("timespan").stringValue());
                map.put(struct.getReconstructionURI(), struct);
            } else {
                MicroCTReconstructionStruct struct = map.get(result.getValue("reconstructionURI").stringValue());
                struct.withProduct(result.getValue("productURI").stringValue(), result.getValue("productName").stringValue());
                map.put(struct.getReconstructionURI(), struct);
            }
        }
        if (!map.isEmpty()) {
            String query = "SELECT ?reconstructionURI ?inputURI ?inputName "
                    + "FROM <" + repositoryGraph + "> "
                    + "WHERE{ "
                    + " ?inputURI <" + Resources.wasDerivationSourceFor + "> ?reconstructionURI . "
                    + " ?inputURI <" + Resources.isIdentifiedBy + "> ?inputName. "
                    + "FILTER( ";
            for (MicroCTReconstructionStruct struct : map.values()) {
                query += "?reconstructionURI = <" + struct.getReconstructionURI() + "> || ";
            }
            query = query.substring(0, query.length() - 3) + ")}";
            logger.debug("Retrieving the products using query: \"" + query + "\"");
            sparqlresults = this.repoManager.query(query);
            logger.debug("The query returned " + sparqlresults.size() + " results");
            for (BindingSet result : sparqlresults) {
                MicroCTReconstructionStruct struct = map.get(result.getValue("reconstructionURI").stringValue());
                struct.withInput(result.getValue("inputURI").stringValue(), result.getValue("inputName").stringValue());
                map.put(struct.getReconstructionURI(), struct);
            }
        }
        return new ArrayList<>(map.values());
    }

    public List<MicroCTPostProcessingStruct> searchMicroCTPostProcessing(String species, String specimen, String input,String datasetURI, String repositoryGraph) throws QueryExecutionException {
        String queryString = "SELECT DISTINCT ?postprocessingURI ?postprocessingLabel ?inputURI ?inputName ?productURI ?productName "
                + "?actorURI ?actorName ?description ?datasetName ?datasetURI "
                + " FROM <" + repositoryGraph + "> "
                + " WHERE{ "
                + " ?postprocessingURI <" + Resources.rdfTypeLabel + "> <" + Resources.formalDerivationEventLabel + "> . "
                + " ?postprocessingURI <" + Resources.hasType + "> \"PostProcessing\" . "
                + " ?postprocessingURI <" + Resources.rdfsLabel + "> ?postprocessingLabel. "
                + " OPTIONAL{ ?postprocessingURI <" + Resources.hasNote + "> ?description . } "
                + " ?postprocessingURI <" + Resources.hasCreated + "> ?productURI . "
                + " ?inputURI <" + Resources.wasDerivationSourceFor + "> ?postprocessingURI . "
                //+ " ?inputURI <" + Resources.rdfTypeLabel + "> <" + Resources.dataObjectLabel + "> . "
                + " ?inputURI <" + Resources.rdfsLabel + "> ?inputName. "
                + " ?postprocessingURI  <" + Resources.carriedOutBy + "> ?actorURI . "
                + " ?actorURI <" + Resources.rdfsLabel + "> ?actorName. "
                + " ?productURI <" + Resources.rdfTypeLabel + "> <" + Resources.dataObjectLabel + "> . "
                + " ?productURI <" + Resources.rdfsLabel + "> ?productName. "
                + " ?datasetURI <" + Resources.refersTo + "> ?postprocessingURI . "
                //+ " ?datasetURI <" + Resources.rdfTypeLabel + "> <" + Resources.datasetLabel + "> . "
                + " ?datasetURI <" + Resources.rdfsLabel + "> ?datasetName. "
                + " ?reconstructionURI <" + Resources.rdfTypeLabel + "> <" + Resources.formalDerivationEventLabel + "> . "
                + " ?reconstructionURI <" + Resources.hasType + "> \"Reconstruction\" . "
                + " ?reconstructionURI  <" + Resources.createdDerivative + "> ?inputURI . "
                + " ?input2URI <" + Resources.wasDerivationSourceFor + "> ?reconstructionURI . "
                + " ?scanningURI <" + Resources.rdfTypeLabel + "> <" + Resources.digitizationProcessLabel + "> . "
                + " ?scanningURI <" + Resources.digitized + "> ?specimenURI . "
                + " ?scanningURI <" + Resources.createdDerivative + "> ?input2URI . "
                //+ " ?specimenURI <" + Resources.rdfTypeLabel + "> <" + Resources.specimenLabel + "> . "
                + " ?specimenURI <" + Resources.rdfsLabel + "> ?specimenName. "
                + " ?specimenURI <" + Resources.belongsTo + "> ?speciesURI . "
                //+ " ?speciesURI <" + Resources.rdfTypeLabel + "> <" + Resources.speciesLabel + "> . "
                + " ?speciesURI <" + Resources.rdfsLabel + "> ?speciesName. "
                + " FILTER regex(?inputName,'" + input + "',\"i\") "
                + " FILTER regex(?datasetURI,'" + datasetURI + "',\"i\")  "
                + " FILTER regex(?specimenName,'" + specimen + "',\"i\") "
                + " FILTER regex(?speciesName,'" + species + "',\"i\")}";
        logger.debug("Submitting the query: \"" + queryString + "\"");
        List<BindingSet> sparqlresults = this.repoManager.query(queryString);
        logger.debug("The query returned " + sparqlresults.size() + " results");
        Map<String, MicroCTPostProcessingStruct> map = new HashMap<>();
        for (BindingSet result : sparqlresults) {
            if (!map.containsKey(result.getValue("postprocessingURI").stringValue())) {
                MicroCTPostProcessingStruct struct = new MicroCTPostProcessingStruct().withPostProcessingURI(result.getValue("postprocessingURI").stringValue())
                        .withPostProcessing(result.getValue("postprocessingLabel").stringValue())
                        .withInput(result.getValue("inputURI").stringValue(), result.getValue("inputName").stringValue())
                        .withProduct(result.getValue("productURI").stringValue(), result.getValue("productName").stringValue())
                        .withActorName(result.getValue("actorName").stringValue())
                        .withActorURI(result.getValue("actorURI").stringValue())
                        .withDatasetURI(result.getValue("datasetURI").stringValue())
                        .withDatasetTitle(result.getValue("datasetName").stringValue());
                if (result.getValue("description") != null) {
                    struct.withDescription(result.getValue("description").stringValue());
                }
                map.put(struct.getPostProcessingURI(), struct);
            } else {
                MicroCTPostProcessingStruct struct = map.get(result.getValue("postprocessingURI").stringValue());
                struct.withProduct(result.getValue("productURI").stringValue(), result.getValue("productName").stringValue());
                map.put(struct.getPostProcessingURI(), struct);
            }
        }
        if (!map.isEmpty()) {
            String query = "SELECT DISTINCT ?postprocessingURI ?inputURI ?inputName "
                    + "FROM <" + repositoryGraph + "> "
                    + "WHERE{ "
                    + "?inputURI <" + Resources.wasDerivationSourceFor + "> ?postprocessingURI . "
                    + "?inputURI <" + Resources.rdfTypeLabel + "> <" + Resources.dataObjectLabel + "> . "
                    + "?inputURI <" + Resources.rdfsLabel + "> ?inputName. "
                    + "FILTER( ";
            for (MicroCTPostProcessingStruct struct : map.values()) {
                query += "?postprocessingURI = <" + struct.getPostProcessingURI() + "> || ";
            }
            query = query.substring(0, query.length() - 3) + ")}";
            logger.debug("Getting the input values using the query: \"" + query + "\"");
            sparqlresults = this.repoManager.query(query);
            logger.debug("The query returned " + sparqlresults.size() + " results");
            for (BindingSet result : sparqlresults) {
                MicroCTPostProcessingStruct struct = map.get(result.getValue("postprocessingURI").stringValue());
                struct.withInput(result.getValue("inputURI").stringValue(), result.getValue("inputName").stringValue());
                map.put(struct.getPostProcessingURI(), struct);
            }
        }
        return new ArrayList<>(map.values());
    }

     public List<MicroCTPostProcessingStruct> searchMicroCTPostProcessing(String species, String specimen, String input,String datasetURI,int offset, int limit, String repositoryGraph) throws QueryExecutionException {
        String queryString = "SELECT DISTINCT ?postprocessingURI ?postprocessingLabel ?inputURI ?inputName ?productURI ?productName "
                + "?actorURI ?actorName ?description ?datasetName ?datasetURI "
                + " FROM <" + repositoryGraph + "> "
                + " WHERE{ "
                + " ?postprocessingURI <" + Resources.rdfTypeLabel + "> <" + Resources.formalDerivationEventLabel + "> . "
                + " ?postprocessingURI <" + Resources.hasType + "> \"PostProcessing\" . "
                + " OPTIONAL{ ?postprocessingURI <" + Resources.hasNote + "> ?description . } "
                + " ?postprocessingURI <" + Resources.hasCreated + "> ?productURI . "
                + " ?postprocessingURI <" + Resources.rdfsLabel + "> ?postprocessingLabel. "
                + " ?inputURI <" + Resources.wasDerivationSourceFor + "> ?postprocessingURI . "
                //+ " ?inputURI <" + Resources.rdfTypeLabel + "> <" + Resources.dataObjectLabel + "> . "
                + " ?inputURI <" + Resources.rdfsLabel + "> ?inputName. "
                + " ?postprocessingURI  <" + Resources.carriedOutBy + "> ?actorURI . "
                + " ?actorURI <" + Resources.rdfsLabel + "> ?actorName. "
                //+ " ?productURI <" + Resources.rdfTypeLabel + "> <" + Resources.dataObjectLabel + "> . "
                + " ?productURI <" + Resources.rdfsLabel + "> ?productName. "
                + " ?datasetURI <" + Resources.refersTo + "> ?postprocessingURI . "
                + " ?datasetURI <" + Resources.rdfTypeLabel + "> <" + Resources.datasetLabel + "> . "
                + " ?datasetURI <" + Resources.rdfsLabel + "> ?datasetName. "
                + " ?reconstructionURI <" + Resources.rdfTypeLabel + "> <" + Resources.formalDerivationEventLabel + "> . "
                + " ?reconstructionURI <" + Resources.hasType + "> \"Reconstruction\" . "
                + " ?reconstructionURI  <" + Resources.createdDerivative + "> ?inputURI . "
                + " ?input2URI <" + Resources.wasDerivationSourceFor + "> ?reconstructionURI . "
                + " ?scanningURI <" + Resources.rdfTypeLabel + "> <" + Resources.digitizationProcessLabel + "> . "
                + " ?scanningURI <" + Resources.digitized + "> ?specimenURI . "
                + " ?scanningURI <" + Resources.createdDerivative + "> ?input2URI . "
                //+ " ?specimenURI <" + Resources.rdfTypeLabel + "> <" + Resources.specimenLabel + "> . "
                + " ?specimenURI <" + Resources.rdfsLabel + "> ?specimenName. "
                + " ?specimenURI <" + Resources.belongsTo + "> ?speciesURI . "
                //+ " ?speciesURI <" + Resources.rdfTypeLabel + "> <" + Resources.speciesLabel + "> . "
                + " ?speciesURI <" + Resources.rdfsLabel + "> ?speciesName. "
                + " FILTER regex(?inputName,'" + input + "',\"i\") "
                + " FILTER regex(?datasetURI,'" + datasetURI + "',\"i\")  "
                + " FILTER regex(?specimenName,'" + specimen + "',\"i\") "
                + " FILTER regex(?speciesName,'" + species + "',\"i\")}"
                + " LIMIT " + limit
                + " OFFSET " + offset;
        logger.debug("Submitting the query: \"" + queryString + "\"");
        List<BindingSet> sparqlresults = this.repoManager.query(queryString);
        logger.debug("The query returned " + sparqlresults.size() + " results");
        Map<String, MicroCTPostProcessingStruct> map = new HashMap<>();
        for (BindingSet result : sparqlresults) {
            if (!map.containsKey(result.getValue("postprocessingURI").stringValue())) {
                MicroCTPostProcessingStruct struct = new MicroCTPostProcessingStruct().withPostProcessingURI(result.getValue("postprocessingURI").stringValue())
                        .withPostProcessing(result.getValue("postprocessingLabel").stringValue())
                        .withInput(result.getValue("inputURI").stringValue(), result.getValue("inputName").stringValue())
                        .withProduct(result.getValue("productURI").stringValue(), result.getValue("productName").stringValue())
                        .withActorName(result.getValue("actorName").stringValue())
                        .withActorURI(result.getValue("actorURI").stringValue())
                        .withDatasetURI(result.getValue("datasetURI").stringValue())
                        .withDatasetTitle(result.getValue("datasetName").stringValue());
                if (result.getValue("description") != null) {
                    struct.withDescription(result.getValue("description").stringValue());
                }
                map.put(struct.getPostProcessingURI(), struct);
            } else {
                MicroCTPostProcessingStruct struct = map.get(result.getValue("postprocessingURI").stringValue());
                struct.withProduct(result.getValue("productURI").stringValue(), result.getValue("productName").stringValue());
                map.put(struct.getPostProcessingURI(), struct);
            }
        }
        if (!map.isEmpty()) {
            String query = "SELECT DISTINCT ?postprocessingURI ?inputURI ?inputName "
                    + "FROM <" + repositoryGraph + "> "
                    + "WHERE{ "
                    + "?inputURI <" + Resources.wasDerivationSourceFor + "> ?postprocessingURI . "
                    + "?inputURI <" + Resources.rdfTypeLabel + "> <" + Resources.dataObjectLabel + "> . "
                    + "?inputURI <" + Resources.rdfsLabel + "> ?inputName. "
                    + "FILTER( ";
            for (MicroCTPostProcessingStruct struct : map.values()) {
                query += "?postprocessingURI = <" + struct.getPostProcessingURI() + "> || ";
            }
            query = query.substring(0, query.length() - 3) + ")}";
            logger.debug("Getting the input values using the query: \"" + query + "\"");
            sparqlresults = this.repoManager.query(query);
            logger.debug("The query returned " + sparqlresults.size() + " results");
            for (BindingSet result : sparqlresults) {
                MicroCTPostProcessingStruct struct = map.get(result.getValue("postprocessingURI").stringValue());
                struct.withInput(result.getValue("inputURI").stringValue(), result.getValue("inputName").stringValue());
                map.put(struct.getPostProcessingURI(), struct);
            }
        }
        return new ArrayList<>(map.values());
    }

    /**
     * Searches for triples containing the given resource. In particular it
     * searches the repository, under the given graphspace, for triples
     * containing the given resource as a subject or as an object.
     *
     * @param resourceURI the resource that will be searched for
     * @param repositoryGraph the graphspace under which the resource will be
     * searched for
     * @return a list containing all the triples that were found and contain the
     * resource
     * @throws QueryExecutionException is thrown for any error that might occur
     */
    @Override
    public List<Triple> searchResource(String resourceURI, String repositoryGraph) throws QueryExecutionException {
        List<Triple> results = new ArrayList<>();
        String query = "SELECT <" + resourceURI + "> as ?s ?p ?o  from <" + repositoryGraph + "> "
                + "WHERE{ <" + resourceURI + "> ?p ?o }";
        logger.debug("Submitting the query: " + query);
        List<BindingSet> sparqlResults = this.repoManager.query(query);
        for (BindingSet bset : sparqlResults) {
            Triple triple = new Triple(bset.getBinding("s").getValue().stringValue(),
                    bset.getBinding("p").getValue().stringValue(),
                    bset.getBinding("o").getValue().stringValue());
            results.add(triple);
        }
        query = "SELECT ?s ?p <" + resourceURI + "> as ?o  from <" + repositoryGraph + "> "
                + "WHERE{ ?s ?p <" + resourceURI + ">}";
        logger.debug("Submitting the query: " + query);
        sparqlResults = this.repoManager.query(query);
        for (BindingSet bset : sparqlResults) {
            Triple triple = new Triple(bset.getBinding("s").getValue().stringValue(),
                    bset.getBinding("p").getValue().stringValue(),
                    bset.getBinding("o").getValue().stringValue());
            results.add(triple);
        }
        logger.debug("The queries returned " + results.size() + " results in total");
        return results;
    }

    /**
     * Searches for triples containing the given literal value. It will search
     * in the repository under the given graphspace, for triples containing the
     * literal value (as an object), by ignoring case (comparing literals in
     * lowercase).
     *
     * @param literalValue the literal value that will be searched for
     * @param repositoryGraph the graphspace that will be used
     * @return a list with the triples containing the givel literal value
     * @throws QueryExecutionException is thrown for any error that might occur
     */
    @Override
    public List<Triple> searchLiteral(String literalValue, String repositoryGraph) throws QueryExecutionException {
        List<Triple> results = new ArrayList<>();
        String query = "SELECT ?s ?p ?o  from <" + repositoryGraph + "> "
                + "WHERE{ ?s ?p ?o . "
                + "FILTER REGEX(?o, \"" + literalValue + "\", \"i\")}";
        logger.debug("Submitting the query: " + query);
        List<BindingSet> sparqlResults = this.repoManager.query(query);
        for (BindingSet bset : sparqlResults) {
            Triple triple = new Triple(bset.getBinding("s").getValue().stringValue(),
                    bset.getBinding("p").getValue().stringValue(),
                    bset.getBinding("o").getValue().stringValue());
            results.add(triple);
        }
        logger.debug("The queries returned " + results.size() + " results in total");
        return results;
    }

    /**
     * Insert the information of the given entry (which is expressed as a struct
     * object) in the repository under the given graphspace.
     *
     * @param directoryStruct the entry that will be inserted
     * @param directoryGraph the graphspace that will be used for adding the new
     * information
     * @throws URIValidationException is thrown if the entry contains invalid
     * URIs
     * @throws QueryExecutionException is thrown for any error that might occur
     * during updating the repository
     */
    @Override
    public void insertStruct(DirectoryStruct directoryStruct, String directoryGraph) throws URIValidationException, QueryExecutionException {
        directoryStruct.validateURIs();
        String insertQuery = "INSERT INTO <" + directoryGraph + "> { " + directoryStruct.toNtriples() + " }";
        logger.debug("Submitting the insert query: " + insertQuery);
        this.repoManager.update(insertQuery);
    }

    /**
     * Insert the information of the given entry (which is expressed as a struct
     * object) in the repository under the given graphspace.
     *
     * @param struct the entry that will be inserted
     * @param directoryGraph the graphspace that will be used for adding the new
     * information
     * @throws URIValidationException is thrown if the entry contains invalid
     * URIs
     * @throws QueryExecutionException is thrown for any error that might occur
     * during updating the repository
     */
    public void insertStruct(CommonNameStruct struct, String directoryGraph) throws URIValidationException, QueryExecutionException {
        struct.validateURIs();
        String insertQuery = "INSERT INTO <" + directoryGraph + "> { " + struct.toNtriples() + " }";
        logger.debug("Submitting the insert query: " + insertQuery);
        this.repoManager.update(insertQuery);
    }

    /**
     * Insert the information of the given entry (which is expressed as a struct
     * object) in the repository under the given graphspace.
     *
     * @param struct the entry that will be inserted
     * @param directoryGraph the graphspace that will be used for adding the new
     * information
     * @throws URIValidationException is thrown if the entry contains invalid
     * URIs
     * @throws QueryExecutionException is thrown for any error that might occur
     * during updating the repository
     */
    public void insertStruct(EnvironmentalStruct struct, String directoryGraph) throws URIValidationException, QueryExecutionException {
        struct.validateURIs();
        String insertQuery = "INSERT INTO <" + directoryGraph + "> { " + struct.toNtriples() + " }";
        logger.debug("Submitting the insert query: " + insertQuery);
        this.repoManager.update(insertQuery);
    }

    /**
     * Insert the information of the given entry (which is expressed as a struct
     * object) in the repository under the given graphspace.
     *
     * @param struct the entry that will be inserted
     * @param directoryGraph the graphspace that will be used for adding the new
     * information
     * @throws URIValidationException is thrown if the entry contains invalid
     * URIs
     * @throws QueryExecutionException is thrown for any error that might occur
     * during updating the repository
     */
    public void insertStruct(IdentificationStruct struct, String directoryGraph) throws URIValidationException, QueryExecutionException {
        struct.validateURIs();
        String insertQuery = "INSERT INTO <" + directoryGraph + "> { " + struct.toNtriples() + " }";
        logger.debug("Submitting the insert query: " + insertQuery);
        this.repoManager.update(insertQuery);
    }

    /**
     * Insert the information of the given entry (which is expressed as a struct
     * object) in the repository under the given graphspace.
     *
     * @param struct the entry that will be inserted
     * @param directoryGraph the graphspace that will be used for adding the new
     * information
     * @throws URIValidationException is thrown if the entry contains invalid
     * URIs
     * @throws QueryExecutionException is thrown for any error that might occur
     * during updating the repository
     */
    public void insertStruct(MeasurementStruct struct, String directoryGraph) throws URIValidationException, QueryExecutionException {
        struct.validateURIs();
        String insertQuery = "INSERT INTO <" + directoryGraph + "> { " + struct.toNtriples() + " }";
        logger.debug("Submitting the insert query: " + insertQuery);
        this.repoManager.update(insertQuery);
    }

    /**
     * Insert the information of the given entry (which is expressed as a struct
     * object) in the repository under the given graphspace.
     *
     * @param struct the entry that will be inserted
     * @param directoryGraph the graphspace that will be used for adding the new
     * information
     * @throws URIValidationException is thrown if the entry contains invalid
     * URIs
     * @throws QueryExecutionException is thrown for any error that might occur
     * during updating the repository
     */
    public void insertStruct(MicroCTPostProcessingStruct struct, String directoryGraph) throws URIValidationException, QueryExecutionException {
        struct.validateURIs();
        String insertQuery = "INSERT INTO <" + directoryGraph + "> { " + struct.toNtriples() + " }";
        logger.debug("Submitting the insert query: " + insertQuery);
        this.repoManager.update(insertQuery);
    }

    /**
     * Insert the information of the given entry (which is expressed as a struct
     * object) in the repository under the given graphspace.
     *
     * @param struct the entry that will be inserted
     * @param directoryGraph the graphspace that will be used for adding the new
     * information
     * @throws URIValidationException is thrown if the entry contains invalid
     * URIs
     * @throws QueryExecutionException is thrown for any error that might occur
     * during updating the repository
     */
    public void insertStruct(MicroCTReconstructionStruct struct, String directoryGraph) throws URIValidationException, QueryExecutionException {
        struct.validateURIs();
        String insertQuery = "INSERT INTO <" + directoryGraph + "> { " + struct.toNtriples() + " }";
        logger.debug("Submitting the insert query: " + insertQuery);
        this.repoManager.update(insertQuery);
    }

    /**
     * Insert the information of the given entry (which is expressed as a struct
     * object) in the repository under the given graphspace.
     *
     * @param struct the entry that will be inserted
     * @param directoryGraph the graphspace that will be used for adding the new
     * information
     * @throws URIValidationException is thrown if the entry contains invalid
     * URIs
     * @throws QueryExecutionException is thrown for any error that might occur
     * during updating the repository
     */
    public void insertStruct(MicroCTScanningStruct struct, String directoryGraph) throws URIValidationException, QueryExecutionException {
        struct.validateURIs();
        String insertQuery = "INSERT INTO <" + directoryGraph + "> { " + struct.toNtriples() + " }";
        logger.debug("Submitting the insert query: " + insertQuery);
        this.repoManager.update(insertQuery);
    }

    /**
     * Insert the information of the given entry (which is expressed as a struct
     * object) in the repository under the given graphspace.
     *
     * @param struct the entry that will be inserted
     * @param directoryGraph the graphspace that will be used for adding the new
     * information
     * @throws URIValidationException is thrown if the entry contains invalid
     * URIs
     * @throws QueryExecutionException is thrown for any error that might occur
     * during updating the repository
     */
    public void insertStruct(MorphometricsStruct struct, String directoryGraph) throws URIValidationException, QueryExecutionException {
        struct.validateURIs();
        String insertQuery = "INSERT INTO <" + directoryGraph + "> { " + struct.toNtriples() + " }";
        logger.debug("Submitting the insert query: " + insertQuery);
        this.repoManager.update(insertQuery);
    }

    /**
     * Insert the information of the given entry (which is expressed as a struct
     * object) in the repository under the given graphspace.
     *
     * @param struct the entry that will be inserted
     * @param directoryGraph the graphspace that will be used for adding the new
     * information
     * @throws URIValidationException is thrown if the entry contains invalid
     * URIs
     * @throws QueryExecutionException is thrown for any error that might occur
     * during updating the repository
     */
    public void insertStruct(OccurrenceStruct struct, String directoryGraph) throws URIValidationException, QueryExecutionException {
        struct.validateURIs();
        String insertQuery = "INSERT INTO <" + directoryGraph + "> { " + struct.toNtriples() + " }";
        logger.debug("Submitting the insert query: " + insertQuery);
        this.repoManager.update(insertQuery);
    }

    /**
     * Insert the information of the given entry (which is expressed as a struct
     * object) in the repository under the given graphspace.
     *
     * @param struct the entry that will be inserted
     * @param directoryGraph the graphspace that will be used for adding the new
     * information
     * @throws URIValidationException is thrown if the entry contains invalid
     * URIs
     * @throws QueryExecutionException is thrown for any error that might occur
     * during updating the repository
     */
    public void insertStruct(ScientificNamingStruct struct, String directoryGraph) throws URIValidationException, QueryExecutionException {
        struct.validateURIs();
        String insertQuery = "INSERT INTO <" + directoryGraph + "> { " + struct.toNtriples() + " }";
        logger.debug("Submitting the insert query: " + insertQuery);
        this.repoManager.update(insertQuery);
    }

    /**
     * Insert the information of the given entry (which is expressed as a struct
     * object) in the repository under the given graphspace.
     *
     * @param struct the entry that will be inserted
     * @param directoryGraph the graphspace that will be used for adding the new
     * information
     * @throws URIValidationException is thrown if the entry contains invalid
     * URIs
     * @throws QueryExecutionException is thrown for any error that might occur
     * during updating the repository
     */
    public void insertStruct(SpecimenStruct struct, String directoryGraph) throws URIValidationException, QueryExecutionException {
        struct.validateURIs();
        String insertQuery = "INSERT INTO <" + directoryGraph + "> { " + struct.toNtriples() + " }";
        logger.debug("Submitting the insert query: " + insertQuery);
        this.repoManager.update(insertQuery);
    }

    /**
     * Insert the information of the given entry (which is expressed as a struct
     * object) in the repository under the given graphspace.
     *
     * @param struct the entry that will be inserted
     * @param directoryGraph the graphspace that will be used for adding the new
     * information
     * @throws URIValidationException is thrown if the entry contains invalid
     * URIs
     * @throws QueryExecutionException is thrown for any error that might occur
     * during updating the repository
     */
    public void insertStruct(StatsStruct struct, String directoryGraph) throws URIValidationException, QueryExecutionException {
        struct.validateURIs();
        String insertQuery = "INSERT INTO <" + directoryGraph + "> { " + struct.toNtriples() + " }";
        logger.debug("Submitting the insert query: " + insertQuery);
        this.repoManager.update(insertQuery);
    }

    /**
     * Insert the information of the given entry (which is expressed as a struct
     * object) in the repository under the given graphspace.
     *
     * @param struct the entry that will be inserted
     * @param directoryGraph the graphspace that will be used for adding the new
     * information
     * @throws URIValidationException is thrown if the entry contains invalid
     * URIs
     * @throws QueryExecutionException is thrown for any error that might occur
     * during updating the repository
     */
    public void insertStruct(SynonymStruct struct, String directoryGraph) throws URIValidationException, QueryExecutionException {
        struct.validateURIs();
        String insertQuery = "INSERT INTO <" + directoryGraph + "> { " + struct.toNtriples() + " }";
        logger.debug("Submitting the insert query: " + insertQuery);
        this.repoManager.update(insertQuery);
    }

    /**
     * Insert the information of the given entry (which is expressed as a struct
     * object) in the repository under the given graphspace.
     *
     * @param struct the entry that will be inserted
     * @param directoryGraph the graphspace that will be used for adding the new
     * information
     * @throws URIValidationException is thrown if the entry contains invalid
     * URIs
     * @throws QueryExecutionException is thrown for any error that might occur
     * during updating the repository
     */
    public void insertStruct(GensDatasetStruct struct, String directoryGraph) throws URIValidationException, QueryExecutionException {
        struct.validateURIs();
        String insertQuery = "INSERT INTO <" + directoryGraph + "> { " + struct.toNtriples() + " }";
        logger.debug("Submitting the insert query: " + insertQuery);
        this.repoManager.update(insertQuery);
    }

    /**
     * Insert the information of the given entry (which is expressed as a struct
     * object) in the repository under the given graphspace.
     *
     * @param struct the entry that will be inserted
     * @param directoryGraph the graphspace that will be used for adding the new
     * information
     * @throws URIValidationException is thrown if the entry contains invalid
     * URIs
     * @throws QueryExecutionException is thrown for any error that might occur
     * during updating the repository
     */
    public void insertStruct(GensSampleStruct struct, String directoryGraph) throws URIValidationException, QueryExecutionException {
        struct.validateURIs();
        String insertQuery = "INSERT INTO <" + directoryGraph + "> { " + struct.toNtriples() + " }";
        logger.debug("Submitting the insert query: " + insertQuery);
        this.repoManager.update(insertQuery);
    }

    /**
     * Insert the information of the given entry (which is expressed as a struct
     * object) in the repository under the given graphspace.
     *
     * @param struct the entry that will be inserted
     * @param directoryGraph the graphspace that will be used for adding the new
     * information
     * @throws URIValidationException is thrown if the entry contains invalid
     * URIs
     * @throws QueryExecutionException is thrown for any error that might occur
     * during updating the repository
     */
    public void insertStruct(OccurrenceStatsTempStruct struct, String directoryGraph) throws URIValidationException, QueryExecutionException {
        struct.validateURIs();
        String insertQuery = "INSERT INTO <" + directoryGraph + "> { " + struct.toNtriples() + " }";
        logger.debug("Submitting the insert query: " + insertQuery);
        this.repoManager.update(insertQuery);
    }

    /**
     * Insert the information of the given entry (which is expressed as a struct
     * object) in the repository under the given graphspace.
     *
     * @param struct the entry that will be inserted
     * @param directoryGraph the graphspace that will be used for adding the new
     * information
     * @throws URIValidationException is thrown if the entry contains invalid
     * URIs
     * @throws QueryExecutionException is thrown for any error that might occur
     * during updating the repository
     */
    public void insertStruct(MicroCTSpecimenStruct struct, String directoryGraph) throws URIValidationException, QueryExecutionException {
        struct.validateURIs();
        String insertQuery = "INSERT INTO <" + directoryGraph + "> { " + struct.toNtriples() + " }";
        logger.debug("Submitting the insert query: " + insertQuery);
        this.repoManager.update(insertQuery);
    }

    /**
     * Insert the information of the given entry (which is expressed as a struct
     * object) in the repository under the given graphspace.
     *
     * @param struct the entry that will be inserted
     * @param directoryGraph the graphspace that will be used for adding the new
     * information
     * @throws URIValidationException is thrown if the entry contains invalid
     * URIs
     * @throws QueryExecutionException is thrown for any error that might occur
     * during updating the repository
     */
    public void insertStruct(OccurrenceStatsAbundanceStruct struct, String directoryGraph) throws URIValidationException, QueryExecutionException {
        struct.validateURIs();
        String insertQuery = "INSERT INTO <" + directoryGraph + "> { " + struct.toNtriples() + " }";
        logger.debug("Submitting the insert query: " + insertQuery);
        this.repoManager.update(insertQuery);
    }

    /**
     * Insert the information of the given entry (which is expressed as a struct
     * object) in the repository under the given graphspace.
     *
     * @param struct the entry that will be inserted
     * @param directoryGraph the graphspace that will be used for adding the new
     * information
     * @throws URIValidationException is thrown if the entry contains invalid
     * URIs
     * @throws QueryExecutionException is thrown for any error that might occur
     * during updating the repository
     */
    public void insertStruct(SpecimenCollectionStruct struct, String directoryGraph) throws URIValidationException, QueryExecutionException {
        struct.validateURIs();
        String insertQuery = "INSERT INTO <" + directoryGraph + "> { " + struct.toNtriples() + " }";
        logger.debug("Submitting the insert query: " + insertQuery);
        this.repoManager.update(insertQuery);
    }

    /**
     * Insert the information of the given entry (which is expressed as a struct
     * object) in the repository under the given graphspace.
     *
     * @param struct the entry that will be inserted
     * @param directoryGraph the graphspace that will be used for adding the new
     * information
     * @throws URIValidationException is thrown if the entry contains invalid
     * URIs
     * @throws QueryExecutionException is thrown for any error that might occur
     * during updating the repository
     */
    public void insertStruct(TaxonomyStruct struct, String directoryGraph) throws URIValidationException, QueryExecutionException {
        struct.validateURIs();
        String insertQuery = "INSERT INTO <" + directoryGraph + "> { " + struct.toNtriples() + " }";
        logger.debug("Submitting the insert query: " + insertQuery);
        this.repoManager.update(insertQuery);
    }

    /**
     * Deletes the triples that contain the given resource, from the given
     * graphspace. More specifically it removes the triples containing the
     * resource as an object, or as a subject.
     *
     * @param resourceURI the resource URI that will be removed
     * @param repositoryGraph the graphspace that will be used
     * @throws QueryExecutionException is thrown for any error that might occur
     */
    @Override
    public void deleteTriplesHavingResource(String resourceURI, String repositoryGraph) throws QueryExecutionException {
        String query = "DELETE FROM <" + repositoryGraph + "> {?s ?p ?o} "
                + "WHERE{"
                + "{?s ?p ?o. FILTER(?s=<" + resourceURI + ">)} "
                + "UNION "
                + "{?s ?p ?o. FILTER(?o=<" + resourceURI + ">)} } ";
        logger.debug("Submitting the delete query: " + query);
        this.repoManager.update(query);
    }

    /**
     * Deletes the triples that contain the given literal value (as an object)
     * in the given graphspace.
     *
     * @param literalValue the literal value that will be removed
     * @param repositoryGraph the graphspace that will be used
     * @throws QueryExecutionException is thrown for any error that might occur
     */
    @Override
    public void deleteTriplesHavingLiteral(String literalValue, String repositoryGraph) throws QueryExecutionException {
        String query = "DELETE FROM <" + repositoryGraph + "> {?s ?p ?o} "
                + "WHERE{ ?s ?p ?o "
                + "FILTER REGEX(?o,\"" + literalValue + "\",\"i\")}";
        logger.debug("Submitting the delete query: " + query);
        this.repoManager.update(query);
    }

    /**
     * Deletes the triples that contain the given property in the given
     * graphspace
     *
     * @param property the uri of the property that will be removed
     * @param repositoryGraph the graphspace that will be used
     * @throws QueryExecutionException is thrown for any error that might occur
     */
    @Override
    public void deleteTriplesHavingProperty(String property, String repositoryGraph) throws QueryExecutionException {
        String deleteQuery = "DELETE FROM <" + repositoryGraph + "> {?s ?p ?o} "
                + "WHERE{ ?s ?p ?o . "
                + "FILTER(?p=<" + property + ">)}";
        logger.debug("Submitting the delete the query: " + deleteQuery);
        this.repoManager.update(deleteQuery);
    }

    /**
     * Deletes the triples containing the given resources/values from the given
     * graphspace. More specifically the user can choose to declare the triples
     * containing a specific subject, a specific property, a specific object or
     * combinations of the above. If a null value is given it means that we do
     * not need to restrict the deletion for the given value. For example the
     * following call of the method (deleteTriples(Dataset1, null, null,
     * graphUri) will remove all triples having Dataset1 as a uri. In case where
     * the given object value is a literal value then the comparison of literals
     * will be made by ignoring the case (upper, lower). The identification of a
     * literal value, or URI is being made automatically.
     *
     * @param subject the value for the subject of the triple
     * @param property the value for the property of the triple
     * @param object the value for the object of the triple
     * @param repositoryGraph the graphspace that will be used
     * @throws QueryExecutionException is thrown for any error that might occur
     */
    @Override
    public void deleteTriples(String subject, String property, String object, String repositoryGraph) throws QueryExecutionException {
        if(subject==null && property==null && object==null){
            logger.debug("dropping all the contents from the graphspace: " + repositoryGraph);
            this.repoManager.clearGraph(repositoryGraph);
        }else{
            String deleteQuery = "DELETE FROM <" + repositoryGraph + "> {?s ?p ?o} "
                    + "WHERE{ ?s ?p ?o . ";
            if (subject != null) {
                deleteQuery += "FILTER(?s=<" + subject + ">). ";
            }
            if (property != null) {
                deleteQuery += "FILTER(?p=<" + property + ">). ";
            }
            if (object != null) {
                try {
                    new URI(object);
                    deleteQuery += "FILTER(?o=<" + object + ">)";
                } catch (URISyntaxException ex) {
                    deleteQuery += "FILTER REGEX(?o,\"" + object + "\",\"i\")";
                }
            }
            deleteQuery += "}";
            logger.debug("Submitting the delete query: " + deleteQuery);
            this.repoManager.update(deleteQuery);
        }
    }
    /**
     * Updates the object of a triple from the given graphspace. It takes as
     * input the original triple and replaces its value with the given resource.
     *
     * @param subject the original subject of the triple
     * @param property the original predicate of the triple
     * @param object the original object of the triple
     * @param newObject the new object of the triple
     * @param repositoryGraph the graphspace that will be used
     * @throws QueryExecutionException is thrown for any error that might occur
     * @deprecated this method will break the links of linked data if not used
     * carefully. For example is can update all the occurrences of a uri found
     * in the subject of the triples but will not do anything for the
     * occurrences of the same uri in the object of the triples.
     */
    @Override
    public void updateResource(String subject, String property, String object, String newObject, String repositoryGraph) throws QueryExecutionException {
        boolean oldObjectIsUri, newObjectIsUri;
        try {
            new URI(object);
            oldObjectIsUri = true;
        } catch (URISyntaxException ex) {
            oldObjectIsUri = false;
        }
        try {
            new URI(newObject);
            newObjectIsUri = true;
        } catch (URISyntaxException ex) {
            newObjectIsUri = false;
        }
        String updateQuery;
        if (oldObjectIsUri && newObjectIsUri) {
            updateQuery = this.prepareUpdateQueryUri2Uri(subject, property, object, newObject, repositoryGraph);
        } else if (oldObjectIsUri && !newObjectIsUri) {
            updateQuery = this.prepareUpdateQueryUri2Literal(subject, property, object, newObject, repositoryGraph);
        } else if (!oldObjectIsUri && newObjectIsUri) {
            updateQuery = this.prepareUpdateQueryLiteral2Uri(subject, property, object, newObject, repositoryGraph);
        } else {
            updateQuery = this.prepareUpdateQueryLiteral2Literal(subject, property, object, newObject, repositoryGraph);
        }
        logger.debug("Executing the update query: " + updateQuery);
        this.repoManager.update(updateQuery);
    }

    /**
     * Updates the value of a resource found in the given graphspace. The
     * resource can be found either in the subject of a triple or in the object.
     *
     * @param originalResource the original value of the resource
     * @param newResource the new value of the resource
     * @param directoryGraph the graphspace that will be used
     * @throws QueryExecutionException is thrown for any error that might occur
     */
    @Override
    public void updateResource(String originalResource, String newResource, String directoryGraph) throws QueryExecutionException {
        String updateQuery = "INSERT INTO <" + directoryGraph + "> { "
                + "<" + newResource + "> ?p ?o} "
                + "WHERE {GRAPH <" + directoryGraph + "> { "
                + "?s ?p ?o. "
                + "FILTER(?s=<" + originalResource + ">)}} "
                + "INSERT INTO <" + directoryGraph + "> { "
                + "?s ?p <" + newResource + ">} "
                + "WHERE {GRAPH <" + directoryGraph + "> { "
                + "?s ?p ?o. "
                + "FILTER(?o=<" + originalResource + ">)}} "
                + "DELETE FROM <" + directoryGraph + "> { "
                + "?s ?p ?o} "
                + "WHERE {GRAPH <" + directoryGraph + "> { "
                + "?s ?p ?o . "
                + "FILTER(?s=<" + originalResource + "> || ?o=<" + originalResource + ">)}}";
        logger.debug("Executing the update query: " + updateQuery);
        this.repoManager.update(updateQuery);
    }

    /**
     * Updates the value of a property found in the given graphspace.
     *
     * @param originalProperty the original value of the property
     * @param newProperty the new value of the property
     * @param directoryGraph the graphspace the will be used
     * @throws QueryExecutionException is thrown for any error that might occur
     */
    @Override
    public void updateProperty(String originalProperty, String newProperty, String directoryGraph) throws QueryExecutionException {
        String updateQuery = "INSERT INTO <" + directoryGraph + "> { "
                + "?s <" + newProperty + "> ?o } "
                + "WHERE {GRAPH <" + directoryGraph + "> { "
                + "?s ?p ?o ."
                + "FILTER(?p=<" + originalProperty + ">)}} "
                + "DELETE FROM <" + directoryGraph + "> { "
                + "?s ?p ?o } "
                + "WHERE {GRAPH <" + directoryGraph + "> { "
                + "?s ?p ?o . "
                + "FILTER(?p=<" + originalProperty + ">)}}";
        logger.debug("Executing the update query: " + updateQuery);
        this.repoManager.update(updateQuery);
    }

    /**
     * Updates the value of a literal found in the given graphspace. The new
     * resource will be added as a literal.
     *
     * @param originalLiteral the original value of the literal
     * @param newLiteral the new value of the literal
     * @param directoryGraph the graphspace that will be used
     * @throws QueryExecutionException is thrown for any error that might occur
     */
    @Override
    public void updateLiteral(String originalLiteral, String newLiteral, String directoryGraph) throws QueryExecutionException {
        List<Triple> triples=this.searchLiteral(originalLiteral, directoryGraph);
        logger.debug("Found "+triples.size()+" with the literal value \""+originalLiteral+"\" to be updated");
        String deleteQuery = "DELETE DATA FROM <"+directoryGraph+"> { ";
        String insertQuery = "INSERT DATA INTO <"+directoryGraph+"> { ";
        for(Triple triple : triples){
            deleteQuery+="<"+triple.getSubject()+"> <"+triple.getPredicate()+"> \""+triple.getObject()+"\". ";
            insertQuery+="<"+triple.getSubject()+"> <"+triple.getPredicate()+"> \""+newLiteral+"\". ";
        }   
        String updateQuery=deleteQuery+"} "+insertQuery+" }";
        logger.debug("Executing the update query: " + updateQuery);
        this.repoManager.update(updateQuery);
    }

    private String prepareUpdateQueryUri2Uri(String subject, String property, String object, String newObject, String graph) {
        return "INSERT INTO <" + graph + "> {?s ?p <" + newObject + ">} "
                + "WHERE {GRAPH <" + graph + "> { ?s ?p ?o . "
                + "FILTER(?s=<" + subject + ">). "
                + "FILTER(?p=<" + property + ">). "
                + "FILTER(?o=<" + object + ">)}} "
                + "DELETE FROM <" + graph + "> {?s ?p ?o} "
                + "WHERE {GRAPH <" + graph + "> {?s ?p ?o . "
                + "FILTER(?s=<" + subject + ">). "
                + "FILTER(?p=<" + property + ">). "
                + "FILTER(?o=<" + object + ">)}}";
    }

    private String prepareUpdateQueryLiteral2Literal(String subject, String property, String object, String newObject, String graph) {
        return "INSERT INTO <" + graph + "> {?s ?p \"" + newObject + "\"} "
                + "WHERE {GRAPH <" + graph + "> { ?s ?p ?o . "
                + "FILTER(?s=<" + subject + ">). "
                + "FILTER(?p=<" + property + ">). "
                + "FILTER REGEX(?o,\"" + object + "\")}} "
                + "DELETE FROM <" + graph + "> {?s ?p ?o} "
                + "WHERE {GRAPH <" + graph + "> {?s ?p ?o . "
                + "FILTER(?s=<" + subject + ">). "
                + "FILTER(?p=<" + property + ">). "
                + "FILTER REGEX(?o,\"" + object + "\")}}";
    }

    private String prepareUpdateQueryUri2Literal(String subject, String property, String object, String newObject, String graph) {
        return "INSERT INTO <" + graph + "> {?s ?p \"" + newObject + "\"} "
                + "WHERE {GRAPH <" + graph + "> { ?s ?p ?o . "
                + "FILTER(?s=<" + subject + ">). "
                + "FILTER(?p=<" + property + ">). "
                + "FILTER(?o=<" + object + ">)}} "
                + "DELETE FROM <" + graph + "> {?s ?p ?o} "
                + "WHERE {GRAPH <" + graph + "> {?s ?p ?o . "
                + "FILTER(?s=<" + subject + ">). "
                + "FILTER(?p=<" + property + ">). "
                + "FILTER(?o=<" + object + ">)}}";
    }

    private String prepareUpdateQueryLiteral2Uri(String subject, String property, String object, String newObject, String graph) {
        return "INSERT INTO <" + graph + "> {?s ?p <" + newObject + ">} "
                + "WHERE {GRAPH <" + graph + "> { ?s ?p ?o . "
                + "FILTER(?s=<" + subject + ">). "
                + "FILTER(?p=<" + property + ">). "
                + "FILTER REGEX(?o,\"" + object + "\")}} "
                + "DELETE FROM <" + graph + "> {?s ?p ?o} "
                + "WHERE {GRAPH <" + graph + "> {?s ?p ?o . "
                + "FILTER(?s=<" + subject + ">). "
                + "FILTER(?p=<" + property + ">). "
                + "FILTER REGEX(?o,\"" + object + "\")}}";
    }

    /**
     * @deprecated this method will break the links of linked data if not used
     * carefully. For example is can update all the occurrences of a uri found
     * in the subject of the triples but will not do anything for the
     * occurrences of the same uri in the object of the triples.
     */
    @Deprecated
    @Override
    public void updateResource(String oldResource, String newResource, ResourceType resourceType, String repositoryGraph) throws QueryExecutionException {
        String updateQuery = "";
        switch (resourceType) {
            case SUBJECT:
                updateQuery = "INSERT INTO <" + repositoryGraph + "> {<" + newResource + "> ?p ?o} "
                        + "WHERE {GRAPH <" + repositoryGraph + "> {?s ?p ?o . "
                        + "FILTER(?s=<" + oldResource + ">)}} "
                        + "DELETE FROM <" + repositoryGraph + "> {?s ?p ?o} "
                        + "WHERE {GRAPH <" + repositoryGraph + "> {?s ?p ?o . "
                        + "FILTER(?s=<" + oldResource + ">)}}";
                break;
            case PREDICATE:
                updateQuery = "INSERT INTO <" + repositoryGraph + "> {?s <" + newResource + "> ?o} "
                        + "WHERE {GRAPH <" + repositoryGraph + "> {?s ?p ?o . "
                        + "FILTER(?p=<" + oldResource + ">)}} "
                        + "DELETE FROM <" + repositoryGraph + "> {?s ?p ?o} "
                        + "WHERE {GRAPH <" + repositoryGraph + "> {?s ?p ?o . "
                        + "FILTER(?p=<" + oldResource + ">)}}";
                break;
            case OBJECT:
                updateQuery = "INSERT INTO <" + repositoryGraph + "> {?s ?p <" + newResource + ">} "
                        + "WHERE {GRAPH <" + repositoryGraph + "> {?s ?p ?o . "
                        + "FILTER(?o=<" + oldResource + ">)}} "
                        + "DELETE FROM <" + repositoryGraph + "> {?s ?p ?o} "
                        + "WHERE {GRAPH <" + repositoryGraph + "> {?s ?p ?o . "
                        + "FILTER(?o=<" + oldResource + ">)}}";
                break;
            case LITERAL:
                updateQuery = "INSERT INTO <" + repositoryGraph + "> {?s ?p \"" + newResource + "\"} "
                        + "WHERE {GRAPH <" + repositoryGraph + "> {?s ?p ?o . "
                        + "FILTER REGEX(?o, \"" + oldResource + "\")}} "
                        + "DELETE FROM <" + repositoryGraph + "> {?s ?p ?o} "
                        + "WHERE {GRAPH <" + repositoryGraph + "> {?s ?p ?o . "
                        + "FILTER REGEX(?o, \"" + oldResource + "\")}}";
                break;
        }
        logger.debug("Submitting the update query: " + updateQuery);
        this.repoManager.query(updateQuery);
    }

    @Override
    @Deprecated
    public List<DirectoryStruct> searchDataset(String datasetName, String hostingInstitutionName, String repositoryGraph) throws QueryExecutionException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
