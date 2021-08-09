package eu.lifewatch.core.impl;

import eu.lifewatch.common.Resources;
import eu.lifewatch.core.model.DirectoryStruct;
import eu.lifewatch.core.model.EnvironmentalStruct;
import eu.lifewatch.core.model.MeasurementStruct;
import eu.lifewatch.core.model.OccurrenceStatsTempStruct;
import eu.lifewatch.core.model.OccurrenceStruct;
import eu.lifewatch.core.model.ScientificNamingStruct;
import eu.lifewatch.core.model.TaxonomyStruct;
import eu.lifewatch.exception.QueryExecutionException;
import eu.lifewatch.exception.URIValidationException;
import eu.lifewatch.service.impl.DirectoryService;
import eu.lifewatch.service.impl.MetadataRepositoryService;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.gbif.dwc.Archive;
import org.gbif.dwc.ArchiveFile;
import org.gbif.dwc.DwcFiles;
import org.gbif.dwc.MetadataException;
import org.gbif.dwc.record.Record;
import org.gbif.dwc.record.StarRecord;
import org.gbif.dwc.terms.DwcTerm;
import org.gbif.dwc.terms.Term;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Yannis Marketakis (marketak 'at' ics 'dot' forth 'dot' gr)
 */
public class DwCArchiveParser {
    private static final Logger log=Logger.getLogger(DwCArchiveParser.class);
    private DirectoryService dsManager;
    private MetadataRepositoryService mrManager;
    private final boolean importDatasets;
    private Archive dwcArchive;
    private String datasetURI;
    private String datasetTitle;
    private String archiveFolderName;
    
    private static final String GRAPHSPACE_DIRECTORY="http://www.ics.forth.gr/isl/lifewatch/directory";
    private static final String GRAPHSPACE_METADATA="http://www.ics.forth.gr/isl/lifewatch/metadata";
    
    public DwCArchiveParser(File archive, boolean importInTriplestore) throws IOException{
        log.info("Parsing archive found in path "+archive.getAbsolutePath()+". Importing in triplestore: "+importInTriplestore);
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        this.dsManager=context.getBean(DirectoryService.class);
        this.mrManager=context.getBean(MetadataRepositoryService.class);
        
        Path myArchiveFile = Paths.get(archive.getAbsolutePath());
        Path extractToFolder = Paths.get("arch");
        this.dwcArchive = DwcFiles.fromCompressed(myArchiveFile, extractToFolder);
        this.datasetURI=Resources.defaultNamespaceForURIs+"/dataset/"+UUID.randomUUID().toString();
        this.dwcArchive.getCore().setEncoding("UTF-8");
        this.importDatasets=importInTriplestore;
        this.archiveFolderName=archive.getParentFile().getName();
        if(!importInTriplestore){
            this.createLocalFolder();
        }
    } 
    
    public void parseData() throws IOException, MetadataException, URIValidationException, QueryExecutionException{
        log.debug("Parsing dataset metadata");
        DirectoryStruct directoryStruct=this.parseDatasetMetadata(this.dwcArchive.getMetadata());
        log.debug("Core dataset metadata: "+directoryStruct);
        if(this.importDatasets){
            log.info("Importing dataset metadata");
            this.importDatasetInfo(directoryStruct);
        }else{
            log.info("Skipping dataset metadata import. Import in triplestore is disabled");
            this.storeLocally(directoryStruct);
        }
        
        
        log.info("Archive rowtype: " + this.dwcArchive.getCore().getRowType() + ", "+ this.dwcArchive.getExtensions().size() + " extension(s)");
        switch(this.dwcArchive.getCore().getRowType().simpleName()){
            case "Occurrence":
                    parseOccurrenceArchive(this.dwcArchive, null);
                    break;
            case "ExtendedMeasurementOrFact":
                parseMeasurementArchive(this.dwcArchive, null);
                break;
            case "Event":
                parseEventArchive(this.dwcArchive, null);
                break;
            default:
                log.error("No parser for "+this.dwcArchive.getCore().getRowType());     
        }
        
        for(ArchiveFile archiveFile : this.dwcArchive.getExtensions()){
            switch(archiveFile.getRowType().simpleName()){
            case "Occurrence":
                    parseOccurrenceArchive(this.dwcArchive, archiveFile.getRowType());
                    break;
            case "ExtendedMeasurementOrFact":
                    parseMeasurementArchive(this.dwcArchive, archiveFile.getRowType());
                    break;
            case "Event":
                    parseEventArchive(this.dwcArchive, archiveFile.getRowType());
                    break;
            default:
                log.error("No parser for "+archiveFile.getRowType().simpleName());     
            }
        }
    }
    
    private void importDatasetInfo(DirectoryStruct directoryStruct) throws URIValidationException, QueryExecutionException{
        this.dsManager.insertStruct(directoryStruct, GRAPHSPACE_DIRECTORY);
    }
    
    private void storeLocally(DirectoryStruct directoryStruct) throws FileNotFoundException, UnsupportedEncodingException, IOException{
        OutputStreamWriter writer=new OutputStreamWriter(new FileOutputStream(new File(Resources.LOCAL_DATASET_INSTANCES+"/"+this.archiveFolderName+"/"+Resources.DIRECTORY_N3_FILENAME)), "UTF-8");
        writer.append(directoryStruct.toNtriples());
        writer.flush();
        writer.close();
    }
    
    private void storeLocally(TaxonomyStruct taxonomyStruct) throws FileNotFoundException, UnsupportedEncodingException, IOException{
        OutputStreamWriter writer=new OutputStreamWriter(new FileOutputStream(new File(Resources.LOCAL_DATASET_INSTANCES+"/"+this.archiveFolderName+"/"+Resources.TAXONOMY_N3_FILENAME),true), "UTF-8");
        writer.append(taxonomyStruct.toNtriples());
        writer.flush();
        writer.close();
    }
    
    private void storeLocally(ScientificNamingStruct scNameStruct) throws FileNotFoundException, UnsupportedEncodingException, IOException{
        OutputStreamWriter writer=new OutputStreamWriter(new FileOutputStream(new File(Resources.LOCAL_DATASET_INSTANCES+"/"+this.archiveFolderName+"/"+Resources.SC_NAME_N3_FILENAME),true), "UTF-8");
        writer.append(scNameStruct.toNtriples());
        writer.flush();
        writer.close();
    }
    
    private void storeLocally(OccurrenceStatsTempStruct occurenceTempStruct) throws FileNotFoundException, UnsupportedEncodingException, IOException{
        OutputStreamWriter writer=new OutputStreamWriter(new FileOutputStream(new File(Resources.LOCAL_DATASET_INSTANCES+"/"+this.archiveFolderName+"/"+Resources.OCCURRENCE_TEMP_N3_FILENAME),true), "UTF-8");
        writer.append(occurenceTempStruct.toNtriples());
        writer.flush();
        writer.close();
    }
    
    private void storeLocally(OccurrenceStruct occurenceStruct) throws FileNotFoundException, UnsupportedEncodingException, IOException{
        OutputStreamWriter writer=new OutputStreamWriter(new FileOutputStream(new File(Resources.LOCAL_DATASET_INSTANCES+"/"+this.archiveFolderName+"/"+Resources.OCCURENCE_N3_FILENAME),true), "UTF-8");
        writer.append(occurenceStruct.toNtriples());
        writer.flush();
        writer.close();
    }
    
    private void storeLocally(EnvironmentalStruct environmentalStruct) throws FileNotFoundException, UnsupportedEncodingException, IOException{
        OutputStreamWriter writer=new OutputStreamWriter(new FileOutputStream(new File(Resources.LOCAL_DATASET_INSTANCES+"/"+this.archiveFolderName+"/"+Resources.ENVIRONMENTAL_N3_FILENAME),true), "UTF-8");
        writer.append(environmentalStruct.toNtriples());
        writer.flush();
        writer.close();
    }
    
    private void storeLocally(MeasurementStruct measurementStruct) throws FileNotFoundException, UnsupportedEncodingException, IOException{
        OutputStreamWriter writer=new OutputStreamWriter(new FileOutputStream(new File(Resources.LOCAL_DATASET_INSTANCES+"/"+this.archiveFolderName+"/"+Resources.MEASUREMENT_N3_FILENAME),true), "UTF-8");
        writer.append(measurementStruct.toNtriples());
        writer.flush();
        writer.close();
    }
    
    private DirectoryStruct parseDatasetMetadata(String metadataContents) throws UnsupportedEncodingException{
        DirectoryStruct directoryStruct=new DirectoryStruct();
        Document metadataDoc=Jsoup.parse(metadataContents);
        Elements idElements=metadataDoc.getElementsByTag(Resources.ALTERNATE_IDENTIFIER);
        for(Element idElement : idElements){
            if(idElement.text().startsWith("http:")){
                directoryStruct.setDatasetID(idElement.text());
            }else{
                this.datasetURI=Resources.defaultNamespaceForURIs+"/dataset/"+idElement.text();
                directoryStruct.setDatasetURI(this.datasetURI);
            }
        }
        Elements titleElements=metadataDoc.getElementsByTag(Resources.TITLE);
        if(titleElements!=null){
            this.datasetTitle=titleElements.get(0).text();
            directoryStruct.setDatasetName(this.datasetTitle);
        }
        Elements abstractElements=metadataDoc.getElementsByTag(Resources.ABSTRACT);
        if(abstractElements!=null){
            directoryStruct.withDescription(abstractElements.text());
        }
        Elements creatorElements=metadataDoc.getElementsByTag(Resources.CREATOR);
        if(creatorElements!=null){
            Elements creatorNameElements=creatorElements.get(0).getElementsByTag(Resources.INDIVIDUAL_NAME);
            if(creatorNameElements!=null){
                directoryStruct.setCreatorName(creatorNameElements.get(0).text());
                directoryStruct.setCreatorURI(Utils.hashUri(Resources.defaultNamespaceForURIs, "person", creatorNameElements.get(0).text()));
                directoryStruct.setOwnerName(creatorNameElements.get(0).text());
                directoryStruct.setOwnerURI(Utils.hashUri(Resources.defaultNamespaceForURIs, "person", creatorNameElements.get(0).text()));
                directoryStruct.setKeeperName(creatorNameElements.get(0).text());
                directoryStruct.setKeeperURI(Utils.hashUri(Resources.defaultNamespaceForURIs, "person", creatorNameElements.get(0).text()));
                directoryStruct.setCuratorName(creatorNameElements.get(0).text());
                directoryStruct.setCuratorURI(Utils.hashUri(Resources.defaultNamespaceForURIs, "person", creatorNameElements.get(0).text()));
            }
        }
        Elements contributorElements=metadataDoc.getElementsByTag(Resources.ASSOCIATED_PARTY);
        for(Element contributorElement : contributorElements){
            Elements contributorNameElements=contributorElement.getElementsByTag(Resources.INDIVIDUAL_NAME);
            if(contributorNameElements!=null){
                directoryStruct.withContributor(Utils.hashUri(Resources.defaultNamespaceForURIs, "person", contributorNameElements.get(0).text()), contributorNameElements.get(0).text());
            }
        }
        Elements pubDateElements=metadataDoc.getElementsByTag(Resources.PUB_DATE);
        if(pubDateElements!=null){
            directoryStruct.setPublicationDate(pubDateElements.get(0).text());
            directoryStruct.setPublicationEventURI(Utils.hashUri(Resources.defaultNamespaceForURIs, "publication", pubDateElements.get(0).text()));
        }
        Elements contactElemets=metadataDoc.getElementsByTag(Resources.CONTACT);
        if(contactElemets!=null){
            directoryStruct.setContactPoint(contactElemets.get(0).text());
        }
        Elements intellectualRightsElements=metadataDoc.getElementsByTag(Resources.INTELLECTUAL_RIGHTS);
        if(intellectualRightsElements!=null){
            directoryStruct.setAccessRights(intellectualRightsElements.get(0).text());
            directoryStruct.setAccessRightsURI(Utils.hashUri(Resources.defaultNamespaceForURIs,"accessrights",intellectualRightsElements.get(0).text()));
        }
        Elements keywordSets=metadataDoc.getElementsByTag(Resources.KEYWORD_SET);
        if(keywordSets!=null){
            for(Element keywordSet : keywordSets){
                if(keywordSet.getElementsByTag(Resources.KEYWORD_THESAURUS)!=null && keywordSet.getElementsByTag(Resources.KEYWORD_THESAURUS).text().contains(Resources.GBIF_THESAURUS_KEYWORD)){
                    directoryStruct.setDatasetType(keywordSet.getElementsByTag(Resources.KEYWORD).text());
                }
            }
        }
        directoryStruct.withAccessMethodURI(Utils.hashUri(Resources.defaultNamespaceForURIs, "access_method", "available from http://ipt.medobis.eu"));
        directoryStruct.withAccessMethod("available from http://ipt.medobis.eu");
        return directoryStruct;
    }
    
    private void parseOccurrenceArchive(Archive dwcArchive, Term term) throws URIValidationException, QueryExecutionException, UnsupportedEncodingException, IOException{
        if(term!=null){
            for(Record rec : dwcArchive.getExtension(term)){
                TaxonomyStruct taxonomyStruct=this.retrieveTaxonomy(rec);
                ScientificNamingStruct scNameStruct=this.retrieveScName(rec);
                OccurrenceStruct occurenceStruct=this.retrieveOccurence(rec);
                OccurrenceStatsTempStruct occurenceTempStruct=this.retrieveOccurenceTemp(rec);
                log.debug("Taxonomy struct: "+taxonomyStruct);
                log.debug("Scientific name struct: "+scNameStruct);
                log.debug("Occurence temp struct: "+occurenceTempStruct);
                log.debug("Occurence struct: "+occurenceStruct);
                if(this.importDatasets){
                    log.info("Importing taxonomy struct");
                    this.mrManager.insertStruct(taxonomyStruct, GRAPHSPACE_METADATA);
                    log.info("Importing scientific name struct");
                    this.mrManager.insertStruct(scNameStruct, GRAPHSPACE_METADATA);
                    log.info("Importing occurence temp struct");
                    this.mrManager.insertStruct(occurenceTempStruct, GRAPHSPACE_METADATA);
                    log.info("Importing occurence struct");
                    this.mrManager.insertStruct(occurenceStruct, GRAPHSPACE_METADATA);
                }else{
                    log.info("Skipping metadata import. Import in triplestore is disabled");
                    this.storeLocally(taxonomyStruct);
                    this.storeLocally(scNameStruct);
                    this.storeLocally(occurenceTempStruct);
                    this.storeLocally(occurenceStruct);
                }
            }
        }else{
            for(StarRecord rec : dwcArchive){
                TaxonomyStruct taxonomyStruct=this.retrieveTaxonomy(rec.core());
                ScientificNamingStruct scNameStruct=this.retrieveScName(rec.core());
                OccurrenceStruct occurenceStruct=this.retrieveOccurence(rec.core());
                OccurrenceStatsTempStruct occurenceTempStruct=this.retrieveOccurenceTemp(rec.core());
                log.debug("Taxonomy struct: "+taxonomyStruct);
                log.debug("Scientific name struct: "+scNameStruct);
                log.debug("Occurence temp struct: "+occurenceTempStruct);
                log.debug("Occurence struct: "+occurenceStruct);
                if(this.importDatasets){
                    log.info("Importing taxonomy struct");
                    this.mrManager.insertStruct(taxonomyStruct, GRAPHSPACE_METADATA);
                    log.info("Importing scientific name struct");
                    this.mrManager.insertStruct(scNameStruct, GRAPHSPACE_METADATA);
                    log.info("Importing occurence temp struct");
                    this.mrManager.insertStruct(occurenceTempStruct, GRAPHSPACE_METADATA);
                    log.info("Importing occurence struct");
                    this.mrManager.insertStruct(occurenceStruct, GRAPHSPACE_METADATA);
                }else{
                    log.info("Skipping metadata import. Import in triplestore is disabled");
                    this.storeLocally(taxonomyStruct);
                    this.storeLocally(scNameStruct);
                    this.storeLocally(occurenceTempStruct);
                    this.storeLocally(occurenceStruct);
                }
            }
        }
    }
    
    private void parseMeasurementArchive(Archive dwcArchive, Term term) throws QueryExecutionException, URIValidationException, UnsupportedEncodingException, IOException{
        if(term!=null){
            for(Record rec : dwcArchive.getExtension(term)){
                MeasurementStruct measurementStruct=this.retrieveMeasurement(rec);
                log.debug("Measurement struct: "+measurementStruct);
                if(this.importDatasets){
                    log.info("Importing measurement struct");
                    this.mrManager.insertStruct(measurementStruct, GRAPHSPACE_METADATA);
                }else{
                    log.info("Skipping metadata import. Import in triplestore is disabled");
                    this.storeLocally(measurementStruct);
                }
            }
        }else{
            for(StarRecord rec : dwcArchive){
                MeasurementStruct measurementStruct=this.retrieveMeasurement(rec.core());
                log.debug("Measurement struct: "+measurementStruct);
                if(this.importDatasets){
                    log.info("Importing measurement struct");
                    this.mrManager.insertStruct(measurementStruct, GRAPHSPACE_METADATA);
                }else{
                    log.info("Skipping metadata import. Import in triplestore is disabled");
                    this.storeLocally(measurementStruct);
                }
            }
        }
    }
    
    private void parseEventArchive(Archive dwcArchive, Term term) throws URIValidationException, QueryExecutionException, UnsupportedEncodingException, IOException{
        if(term!=null){
            for(Record rec : dwcArchive.getExtension(term)){
                EnvironmentalStruct environmentalStruct=this.retrieveEnvironmental(rec);
                log.debug("Environmental struct: "+environmentalStruct);
                if(this.importDatasets){
                    log.info("Importing environmental struct");
                    this.mrManager.insertStruct(environmentalStruct, GRAPHSPACE_METADATA);
                }else{
                    log.info("Skipping metadata import. Import in triplestore is disabled");
                    this.storeLocally(environmentalStruct);
                }
            }
        }else{
            for(StarRecord rec : dwcArchive){
                EnvironmentalStruct environmentalStruct=this.retrieveEnvironmental(rec.core());
                log.debug("Environmental struct: "+environmentalStruct);
                if(this.importDatasets){
                    log.info("Importing environmental struct");
                    this.mrManager.insertStruct(environmentalStruct, GRAPHSPACE_METADATA);
                }else{
                    log.info("Skipping metadata import. Import in triplestore is disabled");
                    this.storeLocally(environmentalStruct);
                }
            }
        }
    }
    
    private TaxonomyStruct retrieveTaxonomy(Record rec) throws UnsupportedEncodingException{
        TaxonomyStruct taxonomyStruct=new TaxonomyStruct()
                    .withDatasetName(this.datasetTitle)
                    .withDatasetURI(this.datasetURI);
            if(rec.value(DwcTerm.scientificName)!=null){
                taxonomyStruct.withSpeciesURI(Utils.hashUri(Resources.defaultNamespaceForURIs, "species",rec.value(DwcTerm.scientificName)));
                taxonomyStruct.withSpeciesName(rec.value(DwcTerm.scientificName));
            }
            if(rec.value(DwcTerm.kingdom)!=null){
                taxonomyStruct.withKingdomURI(Utils.hashUri(Resources.defaultNamespaceForURIs, "kingdom", rec.value(DwcTerm.kingdom)))
                              .withKingdomName(rec.value(DwcTerm.kingdom));
            }
            if(rec.value(DwcTerm.phylum)!=null){
                taxonomyStruct.withPhylumURI(Utils.hashUri(Resources.defaultNamespaceForURIs, "phylum", rec.value(DwcTerm.phylum)))
                              .withPhylumName(rec.value(DwcTerm.phylum));
            }
            if(rec.value(DwcTerm.class_)!=null){
                taxonomyStruct.withClassURI(Utils.hashUri(Resources.defaultNamespaceForURIs, "class", rec.value(DwcTerm.class_)))
                              .withClassName(rec.value(DwcTerm.class_));
            }
            if(rec.value(DwcTerm.order)!=null){
                taxonomyStruct.withOrderURI(Utils.hashUri(Resources.defaultNamespaceForURIs, "order", rec.value(DwcTerm.order)))
                              .withOrderName(rec.value(DwcTerm.order));
            }
            if(rec.value(DwcTerm.family)!=null){
                taxonomyStruct.withFamilyURI(Utils.hashUri(Resources.defaultNamespaceForURIs, "family", rec.value(DwcTerm.family)))
                              .withFamilyName(rec.value(DwcTerm.family));
            }
            if(rec.value(DwcTerm.genus)!=null){
                taxonomyStruct.withGenusURI(Utils.hashUri(Resources.defaultNamespaceForURIs, "genus", rec.value(DwcTerm.genus)))
                              .withGenusName(rec.value(DwcTerm.genus));
            }
            return taxonomyStruct;
    }
    
    private ScientificNamingStruct retrieveScName(Record rec) throws UnsupportedEncodingException{
        ScientificNamingStruct scNameStruct=new ScientificNamingStruct()
                    .withDatasetName(this.datasetTitle)
                    .withDatasetURI(this.datasetURI);
        if(rec.value(DwcTerm.scientificName)!=null){
            scNameStruct.withSpeciesURI(Utils.hashUri(Resources.defaultNamespaceForURIs, "species",rec.value(DwcTerm.scientificName)));
            scNameStruct.withSpeciesName(rec.value(DwcTerm.scientificName));
            scNameStruct.withScientificNameAssignmentEventURI(Utils.hashUri(Resources.defaultNamespaceForURIs, "scientific_name_assignment_event", rec.value(DwcTerm.scientificName)));
            scNameStruct.withScientificNameAssignmentEvent("Assignment of Scientific name for species "+rec.value(DwcTerm.scientificName));
            scNameStruct.withAppellationURI(Utils.hashUri(Resources.defaultNamespaceForURIs, "appellation", rec.value(DwcTerm.scientificName)));
            scNameStruct.withAppellation(rec.value(DwcTerm.scientificName));
            
        }
        if(rec.value(DwcTerm.scientificNameAuthorship)!=null){
            
            String trimmedScNameAuthorship=rec.value(DwcTerm.scientificNameAuthorship);
            if(trimmedScNameAuthorship.startsWith("(") && trimmedScNameAuthorship.endsWith(")")){
                trimmedScNameAuthorship=trimmedScNameAuthorship.substring(1, trimmedScNameAuthorship.length()-1);
            }
            int commaCharIndex=trimmedScNameAuthorship.lastIndexOf(",");
            if(commaCharIndex>0){
                String authorshipName=trimmedScNameAuthorship.substring(0,commaCharIndex).trim();
                String authorshipYear=trimmedScNameAuthorship.substring(commaCharIndex+1).trim();
                scNameStruct.withActor(Utils.hashUri(Resources.defaultNamespaceForURIs, "person", authorshipName), authorshipName);
                scNameStruct.withTimeSpan(authorshipYear);
            }
            
        }
        if(rec.value(DwcTerm.nomenclaturalCode)!=null){
            scNameStruct.withNomenclaturalCodeURI(Utils.hashUri(Resources.defaultNamespaceForURIs, "nomenclaturalCode", rec.value(DwcTerm.nomenclaturalCode)));
            scNameStruct.withNomenclaturalCodeName(rec.value(DwcTerm.nomenclaturalCode));
        }
        return scNameStruct;
    }
    
    private OccurrenceStruct retrieveOccurence(Record rec) throws UnsupportedEncodingException{
        OccurrenceStruct occurrenceStruct=new OccurrenceStruct()
                    .withDatasetTitle(this.datasetTitle)
                    .withDatasetURI(this.datasetURI);
        if(rec.value(DwcTerm.occurrenceID)!=null){
            occurrenceStruct.withOccurrenceEventURI(Utils.hashUri(Resources.defaultNamespaceForURIs, "encounter_event", rec.value(DwcTerm.occurrenceID)));
            occurrenceStruct.withOccurrenceEvent(rec.value(DwcTerm.occurrenceID));
        }
        if(rec.value(DwcTerm.eventDate)!=null){
            occurrenceStruct.withTimeSpan(rec.value(DwcTerm.eventDate));
        }
        if(rec.value(DwcTerm.decimalLatitude)!=null){
            occurrenceStruct.withLatitude(rec.value(DwcTerm.decimalLatitude));
        }
        if(rec.value(DwcTerm.decimalLongitude)!=null){
            occurrenceStruct.withLongitude(rec.value(DwcTerm.decimalLongitude));
        }
        if(rec.value(DwcTerm.scientificName)!=null){
            occurrenceStruct.withSpeciesURI(Utils.hashUri(Resources.defaultNamespaceForURIs, "species",rec.value(DwcTerm.scientificName)));
            occurrenceStruct.withSpeciesName(rec.value(DwcTerm.scientificName));
        }
        return occurrenceStruct;
    }
    
    private OccurrenceStatsTempStruct retrieveOccurenceTemp(Record rec) throws UnsupportedEncodingException{
        OccurrenceStatsTempStruct occurrenceTempStruct=new OccurrenceStatsTempStruct()
                    .withDatasetTitle(this.datasetTitle)
                    .withDatasetURI(this.datasetURI);
        String placeCoordinates="";
        if(rec.value(DwcTerm.occurrenceID)!=null){
            occurrenceTempStruct.withOccurrenceEventURI(Utils.hashUri(Resources.defaultNamespaceForURIs, "encounter_event", rec.value(DwcTerm.occurrenceID)));
            occurrenceTempStruct.withOccurrenceEvent(rec.value(DwcTerm.occurrenceID));
            occurrenceTempStruct.withPhysicalObjectURI(Utils.hashUri(Resources.defaultNamespaceForURIs, "biotic_element", rec.value(DwcTerm.occurrenceID)));
        }
        if(rec.value(DwcTerm.eventDate)!=null){
            occurrenceTempStruct.withTimeSpan(rec.value(DwcTerm.eventDate));
        }
        if(rec.value(DwcTerm.decimalLongitude)!=null){
            occurrenceTempStruct.withLongitude(rec.value(DwcTerm.decimalLongitude));
            placeCoordinates+="Longitude: "+rec.value(DwcTerm.decimalLongitude)+"\t";
        }
        if(rec.value(DwcTerm.decimalLatitude)!=null){
            occurrenceTempStruct.withLatitude(rec.value(DwcTerm.decimalLatitude));
            placeCoordinates+="Latitude: "+rec.value(DwcTerm.decimalLatitude);
        }
        if(rec.value(DwcTerm.scientificName)!=null){
            occurrenceTempStruct.withSpeciesURI(Utils.hashUri(Resources.defaultNamespaceForURIs, "species",rec.value(DwcTerm.scientificName)));
            occurrenceTempStruct.withSpeciesName(rec.value(DwcTerm.scientificName));
            occurrenceTempStruct.withTemporaryAggregateURI(Utils.hashUri(Resources.defaultNamespaceForURIs, "temporary_aggregate",rec.value(DwcTerm.scientificName)));
            occurrenceTempStruct.withTemporaryAggregate("Temporary Aggregate of species "+rec.value(DwcTerm.scientificName));
        }
        if(rec.value(DwcTerm.locationID)!=null){
            occurrenceTempStruct.withStationURI(Utils.hashUri(Resources.defaultNamespaceForURIs, "place", rec.value(DwcTerm.locationID)));
            occurrenceTempStruct.withCoordinates(rec.value(DwcTerm.locationID));
            if(!placeCoordinates.isEmpty()){
                occurrenceTempStruct.withStationNotes(placeCoordinates);
            }
        }
        if(rec.value(DwcTerm.individualCount)!=null){
            occurrenceTempStruct.withNumberOfParts(rec.value(DwcTerm.individualCount));
        }
        return occurrenceTempStruct;
    }
    
    private MeasurementStruct retrieveMeasurement(Record rec) throws UnsupportedEncodingException{
        MeasurementStruct measurementStruct=new MeasurementStruct()
                    .withDatasetName(this.datasetTitle)
                    .withDatasetURI(this.datasetURI);    
        if(rec.column(0)!=null && rec.value(DwcTerm.measurementID)!=null){
            measurementStruct.withMeasurementEventURI(Utils.hashUri(Resources.defaultNamespaceForURIs,"measurement_event", rec.column(0)));
            measurementStruct.withMeasurementEvent("Measurement "+rec.column(0));
            measurementStruct.withDimensionURI(Utils.hashUri(Resources.defaultNamespaceForURIs,"measurement_dimension", rec.column(0)+"-"+rec.value(DwcTerm.measurementID)));
        }
        if(rec.value(DwcTerm.measurementType)!=null){
            measurementStruct.withDimensionType(Utils.hashUri(Resources.defaultNamespaceForURIs,"dimension_type", rec.value(DwcTerm.measurementType)));
            measurementStruct.withDimensionName(rec.value(DwcTerm.measurementType));
        }
        if(rec.value(DwcTerm.measurementValue)!=null){
            measurementStruct.withDimensionValue(rec.value(DwcTerm.measurementValue));
        }
        if(rec.value(DwcTerm.measurementUnit)!=null){
            measurementStruct.withDimensionUnit(rec.value(DwcTerm.measurementUnit));
        }
        if(rec.value(DwcTerm.measurementDeterminedBy)!=null){
            measurementStruct.withActor(Utils.hashUri(Resources.defaultNamespaceForURIs, "person", rec.value(DwcTerm.measurementDeterminedBy)), rec.value(DwcTerm.measurementDeterminedBy));
        }
        
        return measurementStruct;
    }
    
    private EnvironmentalStruct retrieveEnvironmental(Record rec) throws UnsupportedEncodingException{
        EnvironmentalStruct environmentalStruct=new EnvironmentalStruct()
                    .withDatasetName(this.datasetTitle)
                    .withDatasetURI(this.datasetURI);    
        if(rec.value(DwcTerm.eventID)!=null){
            environmentalStruct.withMeasurementEventURI(Utils.hashUri(Resources.defaultNamespaceForURIs,"measurement_event", rec.value(DwcTerm.eventID)));
            environmentalStruct.withMeasurementEvent("Measurement "+rec.value(DwcTerm.eventID));
        }
        if(rec.value(DwcTerm.eventDate)!=null){
            environmentalStruct.withTimeSpan(rec.value(DwcTerm.eventDate));
        }
        if(rec.value(DwcTerm.locationID)!=null){
            environmentalStruct.withPlaceURI(Utils.hashUri(Resources.defaultNamespaceForURIs, "place", rec.value(DwcTerm.locationID)));
        }else if(rec.value(DwcTerm.locality)!=null){
            environmentalStruct.withPlaceURI(Utils.hashUri(Resources.defaultNamespaceForURIs, "place", rec.value(DwcTerm.locality)));
        }
        if(rec.value(DwcTerm.locality)!=null){
            environmentalStruct.withPlaceName(rec.value(DwcTerm.locality));
        }
        if(rec.value(DwcTerm.minimumDepthInMeters)!=null){
            environmentalStruct.withDimensionURI(Utils.hashUri(Resources.defaultNamespaceForURIs, "dimension", "min depth in meters"+rec.value(DwcTerm.minimumDepthInMeters)));
            environmentalStruct.withDimensionTypeURI(Utils.hashUri(Resources.defaultNamespaceForURIs, "dimension_type", "min depth in meters"));
            environmentalStruct.withDimensionName("Minimum Depth in Meters : "+rec.value(DwcTerm.minimumDepthInMeters));
            environmentalStruct.withDimensionUnit("Meters");
            environmentalStruct.withDimensionValue(rec.value(DwcTerm.minimumDepthInMeters));
        }
        if(rec.value(DwcTerm.maximumDepthInMeters)!=null){
            environmentalStruct.withDimensionURI(Utils.hashUri(Resources.defaultNamespaceForURIs, "dimension", "max depth in meters"+rec.value(DwcTerm.maximumDepthInMeters)));
            environmentalStruct.withDimensionTypeURI(Utils.hashUri(Resources.defaultNamespaceForURIs, "dimension_type", "max depth in meters"));
            environmentalStruct.withDimensionName("Maximum Depth in Meters : "+rec.value(DwcTerm.maximumDepthInMeters));
            environmentalStruct.withDimensionUnit("Meters");
            environmentalStruct.withDimensionValue(rec.value(DwcTerm.maximumDepthInMeters));
        }
        return environmentalStruct;
    }
    
    private void createLocalFolder() throws IOException{
        new File(Resources.LOCAL_DATASET_INSTANCES).mkdir();
        if(!new File(Resources.LOCAL_DATASET_INSTANCES+"/"+this.archiveFolderName).exists()){
            new File(Resources.LOCAL_DATASET_INSTANCES+"/"+this.archiveFolderName).mkdir();
        }else{
            FileUtils.deleteDirectory(new File(Resources.LOCAL_DATASET_INSTANCES+"/"+this.archiveFolderName));
            new File(Resources.LOCAL_DATASET_INSTANCES+"/"+this.archiveFolderName).mkdir();
        }
    }
    
    public static void main(String[] args) throws IOException, MetadataException, URIValidationException, QueryExecutionException{
//        new DwCArchiveParser(new File("D:/temp/ipt/resources/biomaerl/dwca-1.22.zip"),false).parseData();
        new DwCArchiveParser(new File("D:/temp/ipt/resources/biomaerl/dwca-1.22.zip"),false).parseData();
    }   
}
