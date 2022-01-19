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
    private final boolean storeLocally;
    private Archive dwcArchive;
    private String datasetURI;
    private String datasetTitle;
    private String archiveFolderName;
       
    private static final String GRAPHSPACE_DIRECTORY="http://www.ics.forth.gr/isl/lifewatch/directory";
    private static final String GRAPHSPACE_METADATA="http://www.ics.forth.gr/isl/lifewatch/metadata";
    private static final String HCMR_LABEL="Hellenic Center for Marine Research";
    
    public DwCArchiveParser(File archive, boolean importInTriplestore, boolean storeLocally) throws IOException{
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
        this.storeLocally=storeLocally;
        this.archiveFolderName=archive.getParentFile().getName();
        if(storeLocally){
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
        }
        if(this.storeLocally){
            log.info("Storing locally dataset metadata");
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
                parseOccurrenceTemporaryAggregate(this.dwcArchive, null);
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
                parseOccurrenceTemporaryAggregate(this.dwcArchive, archiveFile.getRowType());
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
                directoryStruct.setCreationEventURI(Utils.hashUri(Resources.defaultNamespaceForURIs, "creation", this.datasetURI));
                directoryStruct.setCreationEvent("Dataset creation");
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
        Elements metadataProviderElements=metadataDoc.getElementsByTag(Resources.METADATA_PROVIDER);
        for(Element metadataProviderElement : metadataProviderElements){
            Elements metadataProviderNameElements=metadataProviderElement.getElementsByTag(Resources.INDIVIDUAL_NAME);
            if(metadataProviderNameElements!=null){
                directoryStruct.withContributor(Utils.hashUri(Resources.defaultNamespaceForURIs, "person", metadataProviderNameElements.get(0).text()), metadataProviderNameElements.get(0).text());
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
            directoryStruct.setRightsHolderURI(Utils.hashUri(Resources.defaultNamespaceForURIs,"rights_holder",HCMR_LABEL));
            directoryStruct.setRightsHolderName(HCMR_LABEL);
        }
        Elements keywordSets=metadataDoc.getElementsByTag(Resources.KEYWORD_SET);
        if(keywordSets!=null){
            for(Element keywordSet : keywordSets){
                if(keywordSet.getElementsByTag(Resources.KEYWORD_THESAURUS)!=null && keywordSet.getElementsByTag(Resources.KEYWORD_THESAURUS).text().contains(Resources.GBIF_THESAURUS_KEYWORD)){
                    directoryStruct.setDatasetType(keywordSet.getElementsByTag(Resources.KEYWORD).text()+" Dataset");
                }
            }
        }
        Elements logoElements=metadataDoc.getElementsByTag(Resources.RESOURCE_LOGO_URL);
        if(logoElements!=null){
            directoryStruct.withImageURI(logoElements.text());
            directoryStruct.withImageTitle(this.datasetTitle+" logo");
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
                OccurrenceStatsTempStruct occurenceTempStruct=this.retrieveOccurenceTemp(rec);                
//                OccurrenceStruct occurenceStruct=this.retrieveOccurence(rec);
                MeasurementStruct measurementStruct=this.retrieveMeasurementEvent(rec);
                log.debug("Taxonomy struct: "+taxonomyStruct);
                log.debug("Scientific name struct: "+scNameStruct);
                log.debug("Occurence temporary aggregate struct: "+occurenceTempStruct);
//                log.debug("Occurence struct: "+occurenceStruct);
                log.debug("Measurement struct: "+measurementStruct);
                if(this.importDatasets){
                    log.info("Importing taxonomy struct with species URI "+taxonomyStruct.getSpeciesURI());
                    this.mrManager.insertStruct(taxonomyStruct, GRAPHSPACE_METADATA);
                    log.info("Importing scientific name struct with species URI "+scNameStruct.getSpeciesURI());
                    this.mrManager.insertStruct(scNameStruct, GRAPHSPACE_METADATA);
                    log.info("Importing Occurence temporary aggregate struct with URI: "+occurenceTempStruct.getTemporaryAggregateURI());
                    this.mrManager.insertStruct(occurenceTempStruct, GRAPHSPACE_METADATA);
//                    log.info("Importing occurence struct");
//                    this.mrManager.insertStruct(occurenceStruct, GRAPHSPACE_METADATA);
                    log.info("Importing measurement struct with URI "+measurementStruct.getMeasurementEventURI());
                    this.mrManager.insertStruct(measurementStruct, GRAPHSPACE_METADATA);
                }
                if(this.storeLocally){
                    log.info("Storing locally metadata");
                    this.storeLocally(taxonomyStruct);
                    this.storeLocally(scNameStruct);
                    this.storeLocally(occurenceTempStruct);
//                    this.storeLocally(occurenceStruct);
                    this.storeLocally(measurementStruct);
                }
            }
        }else{
            for(StarRecord rec : dwcArchive){
                TaxonomyStruct taxonomyStruct=this.retrieveTaxonomy(rec.core());
                ScientificNamingStruct scNameStruct=this.retrieveScName(rec.core());
//                OccurrenceStruct occurenceStruct=this.retrieveOccurence(rec.core());
                OccurrenceStatsTempStruct occurenceTempStruct=this.retrieveOccurenceTemp(rec.core());
                MeasurementStruct measurementStruct=this.retrieveMeasurementEvent(rec.core());
                log.debug("Taxonomy struct: "+taxonomyStruct);
                log.debug("Scientific name struct: "+scNameStruct);
                log.debug("Occurence temp struct: "+occurenceTempStruct);
//                log.debug("Occurence struct: "+occurenceStruct);
                log.debug("Measurement struct: "+measurementStruct);
                if(this.importDatasets){
                    log.info("Importing taxonomy struct with species URI "+taxonomyStruct.getSpeciesURI());
                    this.mrManager.insertStruct(taxonomyStruct, GRAPHSPACE_METADATA);
                    log.info("Importing scientific name struct with species URI "+scNameStruct.getSpeciesURI());
                    this.mrManager.insertStruct(scNameStruct, GRAPHSPACE_METADATA);
                    log.info("Importing Occurence temporary aggregate struct with URI: "+occurenceTempStruct.getTemporaryAggregateURI());
                    this.mrManager.insertStruct(occurenceTempStruct, GRAPHSPACE_METADATA);
//                    log.info("Importing occurence struct");
//                    this.mrManager.insertStruct(occurenceStruct, GRAPHSPACE_METADATA);
                    log.info("Importing measurement struct with URI "+measurementStruct.getMeasurementEventURI());
                    this.mrManager.insertStruct(measurementStruct, GRAPHSPACE_METADATA);
                }
                if(this.storeLocally){
                    log.info("Storing locally metadata");
                    this.storeLocally(taxonomyStruct);
                    this.storeLocally(scNameStruct);
                    this.storeLocally(occurenceTempStruct);
//                    this.storeLocally(occurenceStruct);
                    this.storeLocally(measurementStruct);
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
                }
                if(this.storeLocally){
                    log.info("Storing locally metadata");
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
                }
                if(this.storeLocally){
                    log.info("Storing locally metadata");
                    this.storeLocally(measurementStruct);
                }
            }
        }
    }
    
    private void parseOccurrenceTemporaryAggregate(Archive dwcArchive, Term term) throws URIValidationException, QueryExecutionException, UnsupportedEncodingException, IOException{
        if(term!=null){
            for(Record rec : dwcArchive.getExtension(term)){
                OccurrenceStatsTempStruct occurenceTempStruct=this.retrieveOccurenceTemp(rec);
                log.debug("Occurrence temporary aggregate struct: "+occurenceTempStruct);
                MeasurementStruct measurementStruct=this.retrieveMeasurementEvent(rec);
                log.debug("Measurement struct: "+measurementStruct);
                if(this.importDatasets){
                    log.info("Importing occurrence temporary aggregate struct with URI "+occurenceTempStruct.getOccurrenceEventURI());
                    this.mrManager.insertStruct(occurenceTempStruct, GRAPHSPACE_METADATA);
                    log.info("Importing measurement struct with URI "+measurementStruct.getMeasurementEventURI());
                    this.mrManager.insertStruct(measurementStruct, GRAPHSPACE_METADATA);
                }
                if(this.storeLocally){
                    log.info("Storing locally metadata occurrence temporary aggregate with URI "+occurenceTempStruct.getOccurrenceEventURI());
                    this.storeLocally(occurenceTempStruct);
                    log.info("Storing locally metadata measurement with URI "+measurementStruct.getMeasurementEventURI());
                    this.storeLocally(measurementStruct);
                }
            }
        }else{
            for(StarRecord rec : dwcArchive){
                OccurrenceStatsTempStruct occurenceTempStruct=this.retrieveOccurenceTemp(rec.core());
                log.debug("Occurrence temporary aggregate struct: "+occurenceTempStruct);
                MeasurementStruct measurementStruct=this.retrieveMeasurementEvent(rec.core());
                log.debug("Measurement struct: "+measurementStruct);
                if(this.importDatasets){
                    log.info("Importing occurrence temporary aggregate struct with URI "+occurenceTempStruct.getOccurrenceEventURI());
                    this.mrManager.insertStruct(occurenceTempStruct, GRAPHSPACE_METADATA);
                    log.info("Importing measurement struct with URI "+measurementStruct.getMeasurementEventURI());
                    this.mrManager.insertStruct(measurementStruct, GRAPHSPACE_METADATA);
                }
                if(this.storeLocally){
                    log.info("Storing locally metadata occurrence temporary aggregate with URI "+occurenceTempStruct.getOccurrenceEventURI());
                    this.storeLocally(occurenceTempStruct);
                    log.info("Storing locally metadata measurement with URI "+measurementStruct.getMeasurementEventURI());
                    this.storeLocally(measurementStruct);
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
        if(rec.value(DwcTerm.identifiedBy)!=null){
            String[] actors=rec.value(DwcTerm.identifiedBy).split(",");
            for(String actor : actors){
                actor=actor.trim();
                if(!actor.isEmpty()){
                    occurrenceStruct.withActor(Utils.hashUri(Resources.defaultNamespaceForURIs, "person", actor),actor);
                }
            }
        }
        if(rec.value(DwcTerm.decimalLatitude)!=null){
            occurrenceStruct.withLatitude(rec.value(DwcTerm.decimalLatitude));
        }
        if(rec.value(DwcTerm.decimalLongitude)!=null){
            occurrenceStruct.withLongitude(rec.value(DwcTerm.decimalLongitude));
        }
        if(rec.value(DwcTerm.scientificName)!=null){
            occurrenceStruct.withIndividualURI(Utils.hashUri(Resources.defaultNamespaceForURIs, "individual_biotic_element",rec.value(DwcTerm.scientificName)));
            occurrenceStruct.withIndividualLabel("Individual instance of species "+rec.value(DwcTerm.scientificName));
            occurrenceStruct.withSpeciesURI(Utils.hashUri(Resources.defaultNamespaceForURIs, "species",rec.value(DwcTerm.scientificName)));
            occurrenceStruct.withSpeciesName(rec.value(DwcTerm.scientificName));
        }
        if(rec.value(DwcTerm.minimumDepthInMeters)!=null){
            occurrenceStruct.withMinimumDepth(rec.value(DwcTerm.minimumDepthInMeters));
        }
        if(rec.value(DwcTerm.maximumDepthInMeters)!=null){
            occurrenceStruct.withMaximumDepth(rec.value(DwcTerm.maximumDepthInMeters));
        }
        if(rec.value(DwcTerm.locality)!=null){
            occurrenceStruct.withLocalityURI(Utils.hashUri(Resources.defaultNamespaceForURIs, "locality",rec.value(DwcTerm.locality)));
            occurrenceStruct.withLocalityName(rec.value(DwcTerm.locality));
        }
        if(rec.value(DwcTerm.samplingProtocol)!=null){
            occurrenceStruct.withSamplingProtocolURI(Utils.hashUri(Resources.defaultNamespaceForURIs, "sampling_protocol",rec.value(DwcTerm.samplingProtocol)));
            occurrenceStruct.withSamplingProtocol(rec.value(DwcTerm.samplingProtocol).replaceAll("\"", "'"));
        }
        if(rec.value(DwcTerm.habitat)!=null){
            occurrenceStruct.withHabitatURI(Utils.hashUri(Resources.defaultNamespaceForURIs, "habitat",rec.value(DwcTerm.habitat)));
            occurrenceStruct.withHabitatName(rec.value(DwcTerm.habitat));
        }
        if(rec.value(DwcTerm.identificationReferences)!=null){
            occurrenceStruct.withBibliographicCitationURI(Utils.hashUri(Resources.defaultNamespaceForURIs, "bibliographic_citation",rec.value(DwcTerm.identificationReferences)));
            occurrenceStruct.withBibliographicCitation(rec.value(DwcTerm.identificationReferences));
        }
        if(rec.value(DwcTerm.eventID)!=null){
            occurrenceStruct.withTimeSpanURI(Utils.hashUri(Resources.defaultNamespaceForURIs, "event_timespan",rec.value(DwcTerm.eventID)));
        }
        return occurrenceStruct;
    }
    
    private OccurrenceStatsTempStruct retrieveOccurenceTemp(Record rec) throws UnsupportedEncodingException{
        OccurrenceStatsTempStruct occurrenceTempStruct=new OccurrenceStatsTempStruct()
                    .withDatasetTitle(this.datasetTitle)
                    .withDatasetURI(this.datasetURI);
        String placeCoordinates="";
        if(rec.value(DwcTerm.eventID)!=null){
            occurrenceTempStruct.withOccurrenceEventURI(Utils.hashUri(Resources.defaultNamespaceForURIs, "encounter_event", rec.value(DwcTerm.eventID)));
            occurrenceTempStruct.withOccurrenceEvent(rec.value(DwcTerm.eventID));
            occurrenceTempStruct.withPhysicalObjectURI(Utils.hashUri(Resources.defaultNamespaceForURIs, "physical_object", rec.value(DwcTerm.eventID)));
            occurrenceTempStruct.withPhysicalObject("Object found in event with ID "+rec.value(DwcTerm.eventID));
        }
        if(rec.value(DwcTerm.eventDate)!=null){
            occurrenceTempStruct.withTimeSpan(rec.value(DwcTerm.eventDate));
        }
        if(rec.value(DwcTerm.samplingProtocol)!=null){
            occurrenceTempStruct.withSamplingProtocolURI(Utils.hashUri(Resources.defaultNamespaceForURIs, "sampling_protocol",rec.value(DwcTerm.samplingProtocol)));
            occurrenceTempStruct.withSamplingProtocolName(rec.value(DwcTerm.samplingProtocol).replaceAll("\"", "'"));
        }
        if(rec.value(DwcTerm.habitat)!=null){
            occurrenceTempStruct.withHabitatURI(Utils.hashUri(Resources.defaultNamespaceForURIs, "habitat",rec.value(DwcTerm.habitat)));
            occurrenceTempStruct.withHabitatName(rec.value(DwcTerm.habitat));
        }
        if(rec.value(DwcTerm.eventRemarks)!=null){
            occurrenceTempStruct.withDescription(rec.value(DwcTerm.eventRemarks).replaceAll("\"", "'"));
        }
        if(rec.value(DwcTerm.minimumDepthInMeters)!=null){
            occurrenceTempStruct.withMinimumDepth(rec.value(DwcTerm.minimumDepthInMeters));
        }
        if(rec.value(DwcTerm.maximumDepthInMeters)!=null){
            occurrenceTempStruct.withMaximumDepth(rec.value(DwcTerm.maximumDepthInMeters));
        }
        if(rec.value(DwcTerm.locality)!=null){
            occurrenceTempStruct.withLocalityURI(Utils.hashUri(Resources.defaultNamespaceForURIs, "locality",rec.value(DwcTerm.locality)));
            occurrenceTempStruct.withLocalityName(rec.value(DwcTerm.locality));
        }      
        if(rec.value(DwcTerm.eventID)!=null){
            occurrenceTempStruct.withOccurrenceEventURI(Utils.hashUri(Resources.defaultNamespaceForURIs, "encounter_event", rec.value(DwcTerm.eventID)));
//            occurrenceTempStruct.withOccurrenceEvent(rec.value(DwcTerm.occurrenceID));        // to avoid the construction of several labels
        }
        if(rec.value(DwcTerm.decimalLongitude)!=null){
            occurrenceTempStruct.withLongitude(rec.value(DwcTerm.decimalLongitude));
            placeCoordinates+="Longitude: "+rec.value(DwcTerm.decimalLongitude)+"\t";
        }
        if(rec.value(DwcTerm.decimalLatitude)!=null){
            occurrenceTempStruct.withLatitude(rec.value(DwcTerm.decimalLatitude));
            placeCoordinates+="Latitude: "+rec.value(DwcTerm.decimalLatitude);
        }
        if(rec.value(DwcTerm.locationID)!=null){
            occurrenceTempStruct.withStationURI(Utils.hashUri(Resources.defaultNamespaceForURIs, "place", rec.value(DwcTerm.locationID)));
            occurrenceTempStruct.withCoordinates(rec.value(DwcTerm.locationID));
            if(!placeCoordinates.isEmpty()){
                occurrenceTempStruct.withStationNotes(placeCoordinates);
            }
        }
        if(rec.value(DwcTerm.occurrenceID)!=null){
            occurrenceTempStruct.withTemporaryAggregateURI(Utils.hashUri(Resources.defaultNamespaceForURIs, "temporary_aggregate", rec.value(DwcTerm.occurrenceID)));
            occurrenceTempStruct.withTemporaryAggregate(rec.value(DwcTerm.occurrenceID));
        }
        if(rec.value(DwcTerm.individualCount)!=null){
            occurrenceTempStruct.withNumberOfParts(rec.value(DwcTerm.individualCount));
        }
        if(rec.value(DwcTerm.identifiedBy)!=null){
            String[] actors=rec.value(DwcTerm.identifiedBy).split(",");
            for(String actor : actors){
                actor=actor.trim();
                if(!actor.isEmpty()){
                    occurrenceTempStruct.withActor(Utils.hashUri(Resources.defaultNamespaceForURIs, "person", actor),actor);
                }
            }
        }
        if(rec.value(DwcTerm.scientificName)!=null){
            occurrenceTempStruct.withSpeciesURI(Utils.hashUri(Resources.defaultNamespaceForURIs, "species",rec.value(DwcTerm.scientificName)));
            occurrenceTempStruct.withSpeciesName(rec.value(DwcTerm.scientificName));
        }
        if(rec.value(DwcTerm.identificationReferences)!=null){
            occurrenceTempStruct.withBibliographicCitationURI(Utils.hashUri(Resources.defaultNamespaceForURIs, "bibliographic_citation",rec.value(DwcTerm.identificationReferences)));
            occurrenceTempStruct.withBibliographicCitation(rec.value(DwcTerm.identificationReferences));
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
    
    private MeasurementStruct retrieveMeasurementEvent(Record rec) throws UnsupportedEncodingException{
        MeasurementStruct measurementStruct=new MeasurementStruct()
                    .withDatasetName(this.datasetTitle)
                    .withDatasetURI(this.datasetURI);    
        if(rec.column(0)!=null){
            measurementStruct.withMeasurementEventURI(Utils.hashUri(Resources.defaultNamespaceForURIs,"measurement_event", rec.column(0)));
            measurementStruct.withMeasurementEvent("Measurement "+rec.column(0));
            measurementStruct.withSpecimenURI(Utils.hashUri(Resources.defaultNamespaceForURIs,"specimen", rec.column(0)));
            measurementStruct.withSpecimenName("Specimen with ID "+rec.column(0));
        }
        if(rec.value(DwcTerm.eventDate)!=null){
            measurementStruct.withTimeSpan(rec.value(DwcTerm.eventDate));
        }
        if(rec.value(DwcTerm.identifiedBy)!=null){
            String[] actors=rec.value(DwcTerm.identifiedBy).split(",");
            for(String actor : actors){
                actor=actor.trim();
                if(!actor.isEmpty()){
                    measurementStruct.withActor(Utils.hashUri(Resources.defaultNamespaceForURIs, "person", actor),actor);
                }
            }
        }
        if(rec.value(DwcTerm.scientificName)!=null){
            measurementStruct.withSpeciesURI(Utils.hashUri(Resources.defaultNamespaceForURIs, "species",rec.value(DwcTerm.scientificName)));
            measurementStruct.withSpeciesName(rec.value(DwcTerm.scientificName));
        }
        return measurementStruct;
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
//        new DwCArchiveParser(new File("D:/temp/ipt/resources_from_hcmr/easternmedsyllids/dwca-1.15.zip"),true,true).parseData();
        new DwCArchiveParser(new File("D:/temp/ipt/resources/zoobenthos_in_amvrakikos_wetlands/dwca-1.17.zip"),false,true).parseData();
    }   
}
