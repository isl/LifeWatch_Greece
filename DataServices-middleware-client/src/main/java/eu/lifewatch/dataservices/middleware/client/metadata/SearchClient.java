package eu.lifewatch.dataservices.middleware.client.metadata;

import eu.lifewatch.dataservices.middleware.client.Commons;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * @author Yannis Marketakis (marketak 'at' ics 'dot' forth 'dot' gr)
 * @author Nikos Minadakis (minadakn 'at' ics 'dot' forth 'dot' gr)
 */
public class SearchClient {
    private static final Logger logger=Logger.getLogger(SearchClient.class);
    private static final String METADATA_CAT_GRAPHSPACE="http://www.ics.forth.gr/isl/lifewatch/metadata";
    
    private static void searchCommonName(){
        try{
            MetadataRepositorySearch_Service service = new MetadataRepositorySearch_Service();
            MetadataRepositorySearch port = service.getMetadataRepositorySearchPort();
            String speciesName = "species";
            String commonName = "common";
            String place = "place";
            String language = "language";
            String datasetURI = "http://localhost/commonname/dataset";
            System.out.println("Searching for common name metadata with the following details: "+
                               "\tSpecies Name: "+speciesName+
                               "\tCommon Name: "+commonName+
                               "\tPlace: "+place+
                               "\tLanguage: "+language+
                               "\tDataset URI: "+datasetURI+
                               "\tGraphspace: "+METADATA_CAT_GRAPHSPACE);
            List<CommonNameStruct> results = port.searchCommonName(speciesName, commonName, place, language, datasetURI, METADATA_CAT_GRAPHSPACE);
            System.out.println("Found "+results.size()+" results");
            for(CommonNameStruct struct : results){
                System.out.println(struct.getCommonName()+"\t"+
                                   struct.getCommonNameURI()+"\t"+
                                   struct.getSpeciesURI()+"...");
            }
        } catch (Exception ex) {
            logger.error("An error occured while searching for common name metadata",ex);
        }
    }
    
    private static void searchCommonNameWithinRange(){
        try{
            MetadataRepositorySearch_Service service = new MetadataRepositorySearch_Service();
            MetadataRepositorySearch port = service.getMetadataRepositorySearchPort();
            String speciesName = "species";
            String commonName = "common";
            String place = "place";
            String language = "language";
            int offset = 0;
            int limit = 1;
            String datasetURI = "http://localhost/commonname/dataset";
            System.out.println("Searching for common name metadata within specific range with the following details: "+
                               "\tSpecies Name: "+speciesName+
                               "\tCommon Name: "+commonName+
                               "\tPlace: "+place+
                               "\tLanguage: "+language+
                               "\tDataset URI: "+datasetURI+
                               "\tOffset: "+offset+
                               "\tLimit: "+limit+
                               "\tGraphspace: "+METADATA_CAT_GRAPHSPACE);
            List<CommonNameStruct> results = port.searchCommonNameWithinRange(speciesName, commonName, place, language, datasetURI, offset, limit, METADATA_CAT_GRAPHSPACE);
            System.out.println("Found "+results.size()+" results");
            for(CommonNameStruct struct : results){
                System.out.println(struct.getCommonName()+"\t"+
                                   struct.getCommonNameURI()+"\t"+
                                   struct.getSpeciesURI()+"...");
            }
        } catch (Exception ex) {
            logger.error("An error occured while searching for common name metadata",ex);
        }
    }
    
    private static void searchEnvironmental(){
        try{
            MetadataRepositorySearch_Service service = new MetadataRepositorySearch_Service();
            MetadataRepositorySearch port = service.getMetadataRepositorySearchPort();
            String dimension = "dimension";
            String place = "place";
            String datasetURI = "http://localhost/environmental/dataset";
            System.out.println("Searching for environmental metadata with the following details: "+
                               "\tDimension: "+dimension+
                               "\tPlace: "+place+
                               "\tDataset URI: "+datasetURI+
                               "\tGraphspace: "+METADATA_CAT_GRAPHSPACE);
            List<EnvironmentalStruct> results = port.searchEnvironmental(dimension, place, datasetURI, METADATA_CAT_GRAPHSPACE);
            System.out.println("Found "+results.size()+" results");
            for(EnvironmentalStruct struct : results){
                System.out.println(struct.getMeasurementEventURI()+"\t"+
                                   struct.getDimensionURI()+"\t"+
                                   struct.getDimensionValue()+"...");
            }
        } catch (Exception ex) {
            logger.error("An error occured while searching for environmental metadata",ex);
        }   
    }
    
    private static void searchEnvironmentalWithinRange(){
        try{
            MetadataRepositorySearch_Service service = new MetadataRepositorySearch_Service();
            MetadataRepositorySearch port = service.getMetadataRepositorySearchPort();
            String dimension = "dimension";
            String place = "place";
            String datasetURI = "http://localhost/environmental/dataset";
            int offset= 0;
            int limit= 1;
            System.out.println("Searching for environmental metadata within specific range with the following details: "+
                               "\tDimension: "+dimension+
                               "\tPlace: "+place+
                               "\tDataset URI: "+datasetURI+
                               "\tOffset: "+offset+
                               "\tLimit: "+limit+
                               "\tGraphspace: "+METADATA_CAT_GRAPHSPACE);
            List<EnvironmentalStruct> results = port.searchEnvironmentalWithinRange(dimension, place, datasetURI, offset, limit, METADATA_CAT_GRAPHSPACE);
            System.out.println("Found "+results.size()+" results");
            for(EnvironmentalStruct struct : results){
                System.out.println(struct.getMeasurementEventURI()+"\t"+
                                   struct.getDimensionURI()+"\t"+
                                   struct.getDimensionValue()+"...");
            }
        } catch (Exception ex) {
            logger.error("An error occured while searching for environmental metadata",ex);
        }
    }
    
    private static void searchGenetics(){
        try{
            MetadataRepositorySearch_Service service = new MetadataRepositorySearch_Service();
            MetadataRepositorySearch port = service.getMetadataRepositorySearchPort();
            String speciesName="species";
            String sample="sample";
            String place="ecosystem";
            String device="device";
            String datasetURI= "http://localhost/gensdataset/dataset";
            System.out.println("Searching for genetics metadata with the following details: "+
                               "\tSpecies: "+speciesName+
                               "\tSample: "+sample+
                               "\tPlace: "+place+
                               "\tDevice: "+device+
                               "\tDataset URI: "+datasetURI+
                               "\tGraphspace: "+METADATA_CAT_GRAPHSPACE);
            List<GensDatasetStruct> results = port.searchGenetics(speciesName, sample, place, device, datasetURI, METADATA_CAT_GRAPHSPACE);
            System.out.println("Found "+results.size()+" results");
            for(GensDatasetStruct struct : results){
                System.out.println(struct.getSequencingEventURI()+"\t"+
                                   struct.getDeviceTypeURI()+"\t"+
                                   struct.getEcosystemName()+"...");
            }
        } catch (Exception ex) {
            logger.error("An error occured while searching for genetics metadata",ex);
        }   
    }
    
    private static void searchGeneticsWithinRange(){
        try{
            MetadataRepositorySearch_Service service = new MetadataRepositorySearch_Service();
            MetadataRepositorySearch port = service.getMetadataRepositorySearchPort();
            String speciesName="species";
            String sample="sample";
            String place="ecosystem";
            String device="device";
            String datasetURI= "http://localhost/gensdataset/dataset";
            int offset=0;
            int limit=1;
            System.out.println("Searching for genetics metadata within a specific range with the following details: "+
                               "\tSpecies: "+speciesName+
                               "\tSample: "+sample+
                               "\tPlace: "+place+
                               "\tDevice: "+device+
                               "\tDataset URI: "+datasetURI+
                               "\tOffset: "+offset+
                               "\tLimit: "+limit+
                               "\tGraphspace: "+METADATA_CAT_GRAPHSPACE);
            List<GensDatasetStruct> results = port.searchGeneticsWithinRange(speciesName, sample, place, device, datasetURI, offset, limit, METADATA_CAT_GRAPHSPACE);
            System.out.println("Found "+results.size()+" results");
            for(GensDatasetStruct struct : results){
                System.out.println(struct.getSequencingEventURI()+"\t"+
                                   struct.getDeviceTypeURI()+"\t"+
                                   struct.getEcosystemName()+"...");
            }
        } catch (Exception ex) {
            logger.error("An error occured while searching for genetics metadata",ex);
        }   
    }
    
    private static void searchGensSample(){
        try{
            MetadataRepositorySearch_Service service = new MetadataRepositorySearch_Service();
            MetadataRepositorySearch port = service.getMetadataRepositorySearchPort();
            String speciesName="species";
            String device="device";
            String sample="sample";
            String datasetURI= "http://localhost/genssample/dataset";
            System.out.println("Searching for gens sample metadata with the following details: "+
                               "\tSpecies: "+speciesName+
                               "\tDevice: "+device+
                               "\tSample: "+sample+
                               "\tDataset URI: "+datasetURI+
                               "\tGraphspace: "+METADATA_CAT_GRAPHSPACE);
            List<GensSampleStruct> results = port.searchGensSample(speciesName, device, sample, datasetURI, METADATA_CAT_GRAPHSPACE);
            System.out.println("Found "+results.size()+" results");
            for(GensSampleStruct struct : results){
                System.out.println(struct.getSampleURI()+"\t"+
                                   struct.getSequencingURI()+"\t"+
                                   struct.getDatasetName()+"...");
            }
        } catch (Exception ex) {
            logger.error("An error occured while searching for gens sample metadata",ex);
        }   
    }
    
    private static void searchGensSampleWithinRange(){
        try{
            MetadataRepositorySearch_Service service = new MetadataRepositorySearch_Service();
            MetadataRepositorySearch port = service.getMetadataRepositorySearchPort();
            String speciesName="species";
            String device="device";
            String sample="sample";
            String datasetURI= "http://localhost/genssample/dataset";
            int offset=0;
            int limit=1;
            System.out.println("Searching for gens sample metadata within a specific range with the following details: "+
                               "\tSpecies: "+speciesName+
                               "\tDevice: "+device+
                               "\tSample: "+sample+
                               "\tDataset URI: "+datasetURI+
                               "\tOffset: "+offset+
                               "\tLimit: "+limit+
                               "\tGraphspace: "+METADATA_CAT_GRAPHSPACE);
            List<GensSampleStruct> results = port.searchGensSampleWithinRange(speciesName, device, sample, datasetURI, offset, limit, METADATA_CAT_GRAPHSPACE);
            System.out.println("Found "+results.size()+" results");
            for(GensSampleStruct struct : results){
                System.out.println(struct.getSampleURI()+"\t"+
                                   struct.getSequencingURI()+"\t"+
                                   struct.getDatasetName()+"...");
            }
        } catch (Exception ex) {
            logger.error("An error occured while searching for gens sample metadata",ex);
        }   
    }
    
    private static void searchIdentification(){
        try{
            MetadataRepositorySearch_Service service = new MetadataRepositorySearch_Service();
            MetadataRepositorySearch port = service.getMetadataRepositorySearchPort();
            String speciesName="species";
            String date="timespan";
            String actor="actor";
            String place="locality";
            String individual="http://localhost/identification/individual";
            String datasetURI= "http://localhost/identification/dataset";
            System.out.println("Searching for identification metadata with the following details: "+
                               "\tSpecies: "+speciesName+
                               "\tDate: "+date+
                               "\tActor: "+actor+
                               "\tPlace: "+place+
                               "\tIndividual URI: "+individual+
                               "\tDataset URI: "+datasetURI+
                               "\tGraphspace: "+METADATA_CAT_GRAPHSPACE);
            List<IdentificationStruct> results = port.searchIdentification(speciesName, date, actor, place, individual, datasetURI, METADATA_CAT_GRAPHSPACE);
            System.out.println("Found "+results.size()+" results");
            for(IdentificationStruct struct : results){
                System.out.println(struct.getIdentificationEventURI()+"\t"+
                                   struct.getIdentificationReferencesName()+"\t"+
                                   struct.getIdentificationReferencesURI()+"...");
            }
        } catch (Exception ex) {
            logger.error("An error occured while searching for identification metadata",ex);
        }   
    }
    
    private static void searchIdentificationWithinRange(){
        try{
            MetadataRepositorySearch_Service service = new MetadataRepositorySearch_Service();
            MetadataRepositorySearch port = service.getMetadataRepositorySearchPort();
            String speciesName="species";
            String date="timespan";
            String actor="actor";
            String place="locality";
            String individual="http://localhost/identification/individual";
            String datasetURI= "http://localhost/identification/dataset";
            int offset=0;
            int limit=1;
            System.out.println("Searching for identification metadata within a specific range with the following details: "+
                               "\tSpecies: "+speciesName+
                               "\tDate: "+date+
                               "\tActor: "+actor+
                               "\tPlace: "+place+
                               "\tIndividual URI: "+individual+
                               "\tDataset URI: "+datasetURI+
                               "\tOffset: "+offset+
                               "\tLimit: "+limit+
                               "\tGraphspace: "+METADATA_CAT_GRAPHSPACE);
            List<IdentificationStruct> results = port.searchIdentificationWithinRange(speciesName, date, actor, place, individual, datasetURI, offset, limit, METADATA_CAT_GRAPHSPACE);
            System.out.println("Found "+results.size()+" results");
            for(IdentificationStruct struct : results){
                System.out.println(struct.getIdentificationEventURI()+"\t"+
                                   struct.getIdentificationReferencesName()+"\t"+
                                   struct.getIdentificationReferencesURI()+"...");
            }
        } catch (Exception ex) {
            logger.error("An error occured while searching for identification metadata",ex);
        }   
    }
    
    private static void searchMeasurement(){
        try{
            MetadataRepositorySearch_Service service = new MetadataRepositorySearch_Service();
            MetadataRepositorySearch port = service.getMetadataRepositorySearchPort();
            String specimen="specimen";
            String speciesName="species";
            String dimension="dimension";
            String datasetURI= "http://localhost/measurement/dataset";
            System.out.println("Searching for measurement metadata with the following details: "+
                               "\tSpecimen: "+specimen+
                               "\tSpecies Name: "+speciesName+
                               "\tDimension: "+dimension+
                               "\tDataset URI: "+datasetURI+
                               "\tGraphspace: "+METADATA_CAT_GRAPHSPACE);
            List<MeasurementStruct> results = port.searchMeasurement(specimen, speciesName, dimension, datasetURI, METADATA_CAT_GRAPHSPACE);
            System.out.println("Found "+results.size()+" results");
            for(MeasurementStruct struct : results){
                System.out.println(struct.getMeasurementEventURI()+"\t"+
                                   struct.getDimensionName()+"\t"+
                                   struct.getDimensionURI()+"...");
            }
        } catch (Exception ex) {
            logger.error("An error occured while searching for measurement metadata",ex);
        }   
    }
    
    private static void searchMeasurementWithinRange(){
        try{
            MetadataRepositorySearch_Service service = new MetadataRepositorySearch_Service();
            MetadataRepositorySearch port = service.getMetadataRepositorySearchPort();
            String specimen="specimen";
            String speciesName="species";
            String dimension="dimension";
            String datasetURI= "http://localhost/measurement/dataset";
            int offset=0;
            int limit=1;
            System.out.println("Searching for measurement metadata within a specific range with the following details: "+
                               "\tSpecimen: "+specimen+
                               "\tSpecies Name: "+speciesName+
                               "\tDimension: "+dimension+
                               "\tDataset URI: "+datasetURI+
                               "\tOffset: "+offset+
                               "\tLimit: "+limit+
                               "\tGraphspace: "+METADATA_CAT_GRAPHSPACE);
            List<MeasurementStruct> results = port.searchMeasurementWithinRange(specimen, speciesName, dimension, datasetURI, offset, limit, METADATA_CAT_GRAPHSPACE);
            System.out.println("Found "+results.size()+" results");
            for(MeasurementStruct struct : results){
                System.out.println(struct.getMeasurementEventURI()+"\t"+
                                   struct.getDimensionName()+"\t"+
                                   struct.getDimensionURI()+"...");
            }
        } catch (Exception ex) {
            logger.error("An error occured while searching for measurement metadata",ex);
        }   
    }
    
    private static void searchMicroCTPostProcessing(){
        try{
            MetadataRepositorySearch_Service service = new MetadataRepositorySearch_Service();
            MetadataRepositorySearch port = service.getMetadataRepositorySearchPort();
            String speciesName="species";
            String specimen="specimen";
            String input="input";
            String datasetURI= "http://localhost/microctpostprocessing/dataset";
            System.out.println("Searching for microCT postprocessing metadata with the following details: "+
                               "\tSpecies Name: "+speciesName+
                               "\tSpecimen: "+specimen+
                               "\tInput: "+input+
                               "\tDataset URI: "+datasetURI+
                               "\tGraphspace: "+METADATA_CAT_GRAPHSPACE);
            List<MicroCTPostProcessingStruct> results = port.searchMicroCTPostProcessing(speciesName, specimen, input, datasetURI, METADATA_CAT_GRAPHSPACE);
            System.out.println("Found "+results.size()+" results");
            for(MicroCTPostProcessingStruct struct : results){
                System.out.println(struct.getPostProcessingURI()+"\t"+
                                   struct.getDatasetTitle()+"\t"+
                                   struct.getDescription()+"...");
            }
        } catch (Exception ex) {
            logger.error("An error occured while searching for microCT postprocessing metadata",ex);
        }   
    }
    
    private static void searchMicroCTPostProcessingWithinRange(){
        try{
            MetadataRepositorySearch_Service service = new MetadataRepositorySearch_Service();
            MetadataRepositorySearch port = service.getMetadataRepositorySearchPort();
            String speciesName="";
            String specimen="specimen";
            String input="input";
            String datasetURI= "http://localhost/microctpostprocessing/dataset";
            int offset=0;
            int limit=1;
            System.out.println("Searching for microCT postprocessing metadata within a specific range with the following details: "+
                               "\tSpecies Name: "+speciesName+
                               "\tSpecimen: "+specimen+
                               "\tInput: "+input+
                               "\tDataset URI: "+datasetURI+
                               "\tOffset: "+offset+
                               "\tLimit: "+limit+
                               "\tGraphspace: "+METADATA_CAT_GRAPHSPACE);
            List<MicroCTPostProcessingStruct> results = port.searchMicroCTPostProcessingWithinRange(speciesName, specimen, input, datasetURI, offset, limit, METADATA_CAT_GRAPHSPACE);
            System.out.println("Found "+results.size()+" results");
            for(MicroCTPostProcessingStruct struct : results){
                System.out.println(struct.getPostProcessingURI()+"\t"+
                                   struct.getDatasetTitle()+"\t"+
                                   struct.getDescription()+"...");
            }
        } catch (Exception ex) {
            logger.error("An error occured while searching for microCT postprocessing metadata",ex);
        }   
    }
    
    private static void searchMorphometrics(){
        try{
            MetadataRepositorySearch_Service service = new MetadataRepositorySearch_Service();
            MetadataRepositorySearch port = service.getMetadataRepositorySearchPort();
            String speciesName="species";
            String dimension="dimension";
            String datasetURI= "http://localhost/morphometrics/dataset";
            System.out.println("Searching for morphometrics metadata with the following details: "+
                               "\tSpecies Name: "+speciesName+
                               "\tDimension: "+dimension+
                               "\tDataset URI: "+datasetURI+
                               "\tGraphspace: "+METADATA_CAT_GRAPHSPACE);
            List<MorphometricsStruct> results = port.searchMorphometrics(speciesName, dimension, datasetURI, METADATA_CAT_GRAPHSPACE);
            System.out.println("Found "+results.size()+" results");
            for(MorphometricsStruct struct : results){
                System.out.println(struct.getAttributeAssignmentEventURI()+"\t"+
                                   struct.getDimensionValue()+"\t"+
                                   struct.getDimensionURI()+"...");
            }
        } catch (Exception ex) {
            logger.error("An error occured while searching for morphometrics metadata",ex);
        }   
    }
    
    private static void searchMorphometricsWithinRange(){
        try{
            MetadataRepositorySearch_Service service = new MetadataRepositorySearch_Service();
            MetadataRepositorySearch port = service.getMetadataRepositorySearchPort();
            String speciesName="species";
            String dimension="dimension";
            String datasetURI= "http://localhost/morphometrics/dataset";
            int offset=0;
            int limit=1;
            System.out.println("Searching for morphometrics metadata within a specific range with the following details: "+
                               "\tSpecies Name: "+speciesName+
                               "\tDimension: "+dimension+
                               "\tDataset URI: "+datasetURI+
                               "\tOffset: "+offset+
                               "\tLimit: "+limit+
                               "\tGraphspace: "+METADATA_CAT_GRAPHSPACE);
            List<MorphometricsStruct> results = port.searchMorphometricsWithinRange(speciesName, dimension, datasetURI, offset, limit, METADATA_CAT_GRAPHSPACE);
            System.out.println("Found "+results.size()+" results");
            for(MorphometricsStruct struct : results){
                System.out.println(struct.getAttributeAssignmentEventURI()+"\t"+
                                   struct.getDimensionValue()+"\t"+
                                   struct.getDimensionURI()+"...");
            }
        } catch (Exception ex) {
            logger.error("An error occured while searching for morphometrics metadata",ex);
        }   
    }
    
    private static void searchOccurrenceStatsTemp(){
        try{
            MetadataRepositorySearch_Service service = new MetadataRepositorySearch_Service();
            MetadataRepositorySearch port = service.getMetadataRepositorySearchPort();
            String speciesName="species";
            String place="locality";
            String date="timespan";
            String numberOfParts="number";
            String datasetURI= "http://localhost/occurrencestatstemp/dataset";
            System.out.println("Searching for occurrence stats temp metadata with the following details: "+
                               "\tSpecies Name: "+speciesName+
                               "\tPlace: "+place+
                               "\tDate: "+date+
                               "\tNumberOfParts: "+numberOfParts+
                               "\tDataset URI: "+datasetURI+
                               "\tGraphspace: "+METADATA_CAT_GRAPHSPACE);
            List<OccurrenceStatsTempStruct> results = port.searchOccurrenceStatsTemp(speciesName, place, date, numberOfParts, datasetURI, METADATA_CAT_GRAPHSPACE);
            System.out.println("Found "+results.size()+" results");
            for(OccurrenceStatsTempStruct struct : results){
                System.out.println(struct.getOccurrenceEventURI()+"\t"+
                                   struct.getCoordinates()+"\t"+
                                   struct.getBibliographicCitation()+"...");
            }
        } catch (Exception ex) {
            logger.error("An error occured while searching for occurrence stats temp metadata",ex);
        }   
    }
    
    private static void searchOccurrenceStatsTempWithinRange(){
        try{
            MetadataRepositorySearch_Service service = new MetadataRepositorySearch_Service();
            MetadataRepositorySearch port = service.getMetadataRepositorySearchPort();
            String speciesName="species";
            String place="locality";
            String date="timespan";
            String numberOfParts="number";
            String datasetURI= "http://localhost/occurrencestatstemp/dataset";
            int offset=0;
            int limit=1;
            System.out.println("Searching for occurrence stats temp metadata within a specific range with the following details: "+
                               "\tSpecies Name: "+speciesName+
                               "\tPlace: "+place+
                               "\tDate: "+date+
                               "\tNumberOfParts: "+numberOfParts+
                               "\tDataset URI: "+datasetURI+
                               "\tOffset: "+offset+
                               "\tLimit: "+limit+
                               "\tGraphspace: "+METADATA_CAT_GRAPHSPACE);
            List<OccurrenceStatsTempStruct> results = port.searchOccurrenceStatsTempWithinRange(speciesName, place, date, numberOfParts, datasetURI, offset, limit, METADATA_CAT_GRAPHSPACE);
            System.out.println("Found "+results.size()+" results");
            for(OccurrenceStatsTempStruct struct : results){
                System.out.println(struct.getOccurrenceEventURI()+"\t"+
                                   struct.getCoordinates()+"\t"+
                                   struct.getBibliographicCitation()+"...");
            }
        } catch (Exception ex) {
            logger.error("An error occured while searching for occurrence stats temp metadata",ex);
        }   
    }
    
    private static void searchOccurrence(){
        try{
            MetadataRepositorySearch_Service service = new MetadataRepositorySearch_Service();
            MetadataRepositorySearch port = service.getMetadataRepositorySearchPort();
            String speciesName="species";
            String place="locality";
            String date="timespan";
            String datasetURI= "http://localhost/occurrence/dataset";
            System.out.println("Searching for occurrence metadata with the following details: "+
                               "\tSpecies Name: "+speciesName+
                               "\tPlace: "+place+
                               "\tDate: "+date+
                               "\tDataset URI: "+datasetURI+
                               "\tGraphspace: "+METADATA_CAT_GRAPHSPACE);
            List<OccurrenceStruct> results = port.searchOccurrence(speciesName, place, date, datasetURI, METADATA_CAT_GRAPHSPACE);
            System.out.println("Found "+results.size()+" results");
            for(OccurrenceStruct struct : results){
                System.out.println(struct.getOccurrenceEventURI()+"\t"+
                                   struct.getCoordinates()+"\t"+
                                   struct.getBibliographicCitation()+"...");
            }
        } catch (Exception ex) {
            logger.error("An error occured while searching for occurrence metadata",ex);
        }   
    }
    
    private static void searchOccurrenceWithinRange(){
        try{
            MetadataRepositorySearch_Service service = new MetadataRepositorySearch_Service();
            MetadataRepositorySearch port = service.getMetadataRepositorySearchPort();
            String speciesName="species";
            String place="locality";
            String date="timespan";
            String datasetURI= "http://localhost/occurrence/dataset";
            int offset=0;
            int limit=1;
            System.out.println("Searching for occurrence metadata within a specific range with the following details: "+
                               "\tSpecies Name: "+speciesName+
                               "\tPlace: "+place+
                               "\tDate: "+date+
                               "\tDataset URI: "+datasetURI+
                               "\tOffset: "+offset+
                               "\tLimit: "+limit+
                               "\tGraphspace: "+METADATA_CAT_GRAPHSPACE);
            List<OccurrenceStruct> results = port.searchOccurrenceWithinRange(speciesName, place, date, datasetURI, offset, limit, METADATA_CAT_GRAPHSPACE);
            System.out.println("Found "+results.size()+" results");
            for(OccurrenceStruct struct : results){
                System.out.println(struct.getOccurrenceEventURI()+"\t"+
                                   struct.getCoordinates()+"\t"+
                                   struct.getBibliographicCitation()+"...");
            }
        } catch (Exception ex) {
            logger.error("An error occured while searching for occurrence metadata",ex);
        }   
    }
    
    public static void main(String[] args){
//        searchCommonName();
//        Commons.printSeparator();
//        searchCommonNameWithinRange();
//        Commons.printSeparator();
//        searchEnvironmental();
//        Commons.printSeparator();
//        searchEnvironmentalWithinRange();
//        Commons.printSeparator();
//        searchGenetics();
//        Commons.printSeparator();
//        searchGeneticsWithinRange();
//        Commons.printSeparator();
//        searchGensSample();
//        Commons.printSeparator();
//        searchGensSampleWithinRange();
//        Commons.printSeparator();
//        searchIdentification();
//        Commons.printSeparator();
//        searchIdentificationWithinRange();
//        Commons.printSeparator();
//        searchMeasurement();
//        Commons.printSeparator();
//        searchMeasurementWithinRange();
//        Commons.printSeparator();
//        searchMicroCTPostProcessing();
//        Commons.printSeparator();
//        searchMicroCTPostProcessingWithinRange();
//        Commons.printSeparator();
//        searchMorphometrics();
//        Commons.printSeparator();
//        searchMorphometricsWithinRange();
//        Commons.printSeparator();
//        searchOccurrenceStatsTemp();
//        Commons.printSeparator();
//        searchOccurrenceStatsTempWithinRange();
//        Commons.printSeparator();
        searchOccurrence();
        Commons.printSeparator();
        searchOccurrenceWithinRange();
        Commons.printSeparator();
    }
}
