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
            results.forEach(result -> System.out.println(result.getCommonName()+"\t"+
                                                         result.getCommonNameURI()+"\t"+
                                                         result.getSpeciesURI()+"..."));
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
            results.forEach(result -> System.out.println(result.getCommonName()+"\t"+
                                                         result.getCommonNameURI()+"\t"+
                                                         result.getSpeciesURI()+"..."));
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
            results.forEach(result -> System.out.println(result.getMeasurementEventURI()+"\t"+
                                                         result.getDimensionURI()+"\t"+
                                                         result.getDimensionValue()+"..."));
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
            results.forEach(result -> System.out.println(result.getMeasurementEventURI()+"\t"+
                                                         result.getDimensionURI()+"\t"+
                                                         result.getDimensionValue()+"..."));
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
            results.forEach(result -> System.out.println(result.getSequencingEventURI()+"\t"+
                                                         result.getDeviceTypeURI()+"\t"+
                                                         result.getEcosystemName()+"..."));
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
            results.forEach(result -> System.out.println(result.getSequencingEventURI()+"\t"+
                                                         result.getDeviceTypeURI()+"\t"+
                                                         result.getEcosystemName()+"..."));
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
            results.forEach(result -> System.out.println(result.getSampleURI()+"\t"+
                                                         result.getSequencingURI()+"\t"+
                                                         result.getDatasetName()+"..."));
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
            results.forEach(result -> System.out.println(result.getSampleURI()+"\t"+
                                                         result.getSequencingURI()+"\t"+
                                                         result.getDatasetName()+"..."));
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
            results.forEach(result -> System.out.println(result.getIdentificationEventURI()+"\t"+
                                                         result.getIdentificationReferencesName()+"\t"+
                                                         result.getIdentificationReferencesURI()+"..."));
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
            results.forEach(result -> System.out.println(result.getIdentificationEventURI()+"\t"+
                                                         result.getIdentificationReferencesName()+"\t"+
                                                         result.getIdentificationReferencesURI()+"..."));
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
            results.forEach(result -> System.out.println(result.getMeasurementEventURI()+"\t"+
                                                         result.getDimensionName()+"\t"+
                                                         result.getDimensionURI()+"..."));
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
            results.forEach(result ->System.out.println(result.getMeasurementEventURI()+"\t"+
                                                        result.getDimensionName()+"\t"+
                                                        result.getDimensionURI()+"..."));
        } catch (Exception ex) {
            logger.error("An error occured while searching for measurement metadata",ex);
        }   
    }
    
    private static void searchMicroCTSpecimen(){
        try{
            MetadataRepositorySearch_Service service = new MetadataRepositorySearch_Service();
            MetadataRepositorySearch port = service.getMetadataRepositorySearchPort();
            String specimen="specimen";
            String collection="collection";
            String species="species";
            String provider="provider";
            String datasetURI= "http://localhost/microct/dataset";
            System.out.println("Searching for microCT specimen metadata with the following details: "+
                               "\tSpecimen: "+specimen+
                               "\tCollection: "+collection+
                               "\tSpecies: "+species+
                               "\tProvider: "+provider+
                               "\tDataset URI: "+datasetURI+
                               "\tGraphspace: "+METADATA_CAT_GRAPHSPACE);
            List<MicroCTSpecimenStruct> results = port.searchMicroCTSpecimen(specimen, collection, species, provider, datasetURI, METADATA_CAT_GRAPHSPACE);
            System.out.println("Found "+results.size()+" results");
            results.forEach(result -> System.out.println(result.getSpeciesURI()+"\t"+
                                                         result.getSpecimenName()+"\t"+
                                                         result.getDatasetName()+"..."));
        } catch (Exception ex) {
            logger.error("An error occured while searching for microCT postprocessing metadata",ex);
        }   
    }
    
    private static void searchMicroCTSpecimenWithinRange(){
        try{
            MetadataRepositorySearch_Service service = new MetadataRepositorySearch_Service();
            MetadataRepositorySearch port = service.getMetadataRepositorySearchPort();
            String specimen="specimen";
            String collection="collection";
            String species="species";
            String provider="provider";
            int offset=0;
            int limit=1;
            String datasetURI= "http://localhost/microct/dataset";
            System.out.println("Searching for microCT specimen metadata within a specific range with the following details: "+
                               "\tSpecimen: "+specimen+
                               "\tCollection: "+collection+
                               "\tSpecies: "+species+
                               "\tProvider: "+provider+
                               "\tDataset URI: "+datasetURI+
                               "\tOffset: "+offset+
                               "\tLimit: "+limit+
                               "\tGraphspace: "+METADATA_CAT_GRAPHSPACE);
            List<MicroCTSpecimenStruct> results = port.searchMicroCTSpecimenWithinRange(specimen, collection, species, provider, datasetURI, offset, limit, METADATA_CAT_GRAPHSPACE);
            System.out.println("Found "+results.size()+" results");
            results.forEach(result -> System.out.println(result.getSpeciesURI()+"\t"+
                                                         result.getSpecimenName()+"\t"+
                                                         result.getDatasetName()+"..."));
        } catch (Exception ex) {
            logger.error("An error occured while searching for microCT specimen metadata",ex);
        }   
    }

    private static void searchMicroCTScanning(){
        try{
            MetadataRepositorySearch_Service service = new MetadataRepositorySearch_Service();
            MetadataRepositorySearch port = service.getMetadataRepositorySearchPort();
            String device="device";
            String specimen="specimen";
            String species="species";
            String contrastMethod="contrast";
            String datasetURI= "http://localhost/microct/dataset";
            System.out.println("Searching for microCT scanning metadata with the following details: "+
                               "\tDevice: "+device+
                               "\tSpecimen: "+specimen+
                               "\tSpecies: "+species+
                               "\tContrast Method: "+contrastMethod+
                               "\tDataset URI: "+datasetURI+
                               "\tGraphspace: "+METADATA_CAT_GRAPHSPACE);
            List<MicroCTScanningStruct> results = port.searchMicroCTScanning(device, specimen, species, contrastMethod, datasetURI, METADATA_CAT_GRAPHSPACE);
            System.out.println("Found "+results.size()+" results");
            results.forEach(result -> System.out.println(result.getScanningURI()+"\t"+
                                                         result.getSpecimenURI()+"\t"+
                                                         result.getDatasetName()+"..."));
        } catch (Exception ex) {
            logger.error("An error occured while searching for microCT scanning metadata",ex);
        }   
    }
    
    private static void searchMicroCTScanningWithinRange(){
        try{
            MetadataRepositorySearch_Service service = new MetadataRepositorySearch_Service();
            MetadataRepositorySearch port = service.getMetadataRepositorySearchPort();
            String device="device";
            String specimen="specimen";
            String species="species";
            String contrastMethod="contrast";
            int offset=0;
            int limit=1;
            String datasetURI= "http://localhost/microct/dataset";
            System.out.println("Searching for microCT scanning metadata within a specific range with the following details: "+
                               "\tDevice: "+device+
                               "\tSpecimen: "+specimen+
                               "\tSpecies: "+species+
                               "\tContrast Method: "+contrastMethod+
                               "\tDataset URI: "+datasetURI+
                               "\tOffset: "+offset+
                               "\tLimit: "+limit+
                               "\tGraphspace: "+METADATA_CAT_GRAPHSPACE);
            List<MicroCTScanningStruct> results = port.searchMicroCTScanningWithinRange(device, specimen, species, contrastMethod, datasetURI, offset, limit, METADATA_CAT_GRAPHSPACE);
            System.out.println("Found "+results.size()+" results");
            results.forEach(result -> System.out.println(result.getScanningURI()+"\t"+
                                                         result.getSpecimenURI()+"\t"+
                                                         result.getDatasetName()+"..."));
        } catch (Exception ex) {
            logger.error("An error occured while searching for microCT scanning metadata",ex);
        }   
    }
    
    private static void searchMicroCTReconstruction(){
        try{
            MetadataRepositorySearch_Service service = new MetadataRepositorySearch_Service();
            MetadataRepositorySearch port = service.getMetadataRepositorySearchPort();
            String species="species";
            String specimen="specimen";
            String input="product";
            String datasetURI= "http://localhost/microct/dataset";
            System.out.println("Searching for microCT reconstruction metadata with the following details: "+
                               "\tSpecies: "+species+
                               "\tSpecimen: "+specimen+
                               "\tInput: "+input+
                               "\tDataset URI: "+datasetURI+
                               "\tGraphspace: "+METADATA_CAT_GRAPHSPACE);
            List<MicroCTReconstructionStruct> results = port.searchMicroCTReconstruction(species, specimen, input, datasetURI, METADATA_CAT_GRAPHSPACE);
            System.out.println("Found "+results.size()+" results");
            results.forEach(result -> System.out.println(result.getReconstructionURI()+"\t"+
                                                         result.getDatasetTitle()+"\t"+
                                                         result.getDatasetURI()+"..."));
        } catch (Exception ex) {
            logger.error("An error occured while searching for microCT reconstruction metadata",ex);
        }   
    }
    
    private static void searchMicroCTReconstructionWithinRange(){
        try{
            MetadataRepositorySearch_Service service = new MetadataRepositorySearch_Service();
            MetadataRepositorySearch port = service.getMetadataRepositorySearchPort();
            String species="species";
            String specimen="specimen";
            String input="product";
            int offset=0;
            int limit=1;
            String datasetURI= "http://localhost/microct/dataset";
            System.out.println("Searching for microCT reconstruction metadata with the following details: "+
                               "\tSpecies: "+species+
                               "\tSpecimen: "+specimen+
                               "\tInput: "+input+
                               "\tDataset URI: "+datasetURI+
                               "\tOffset: "+offset+
                               "\tLimit: "+limit+
                               "\tGraphspace: "+METADATA_CAT_GRAPHSPACE);
            List<MicroCTReconstructionStruct> results = port.searchMicroCTReconstructionWithinRange(species, specimen, input, datasetURI, offset, limit, METADATA_CAT_GRAPHSPACE);
            System.out.println("Found "+results.size()+" results");
            results.forEach(result -> System.out.println(result.getReconstructionURI()+"\t"+
                                                         result.getDatasetTitle()+"\t"+
                                                         result.getDatasetURI()+"..."));
        } catch (Exception ex) {
            logger.error("An error occured while searching for microCT reconstruction metadata",ex);
        }   
    }
    
    private static void searchMicroCTPostProcessing(){
        try{
            MetadataRepositorySearch_Service service = new MetadataRepositorySearch_Service();
            MetadataRepositorySearch port = service.getMetadataRepositorySearchPort();
            String speciesName="species";
            String specimen="specimen";
            String input="product";
            String datasetURI="http://localhost/microct/dataset";
            System.out.println("Searching for microCT postprocessing metadata with the following details: "+
                               "\tSpecies Name: "+speciesName+
                               "\tSpecimen: "+specimen+
                               "\tInput: "+input+
                               "\tDataset URI: "+datasetURI+
                               "\tGraphspace: "+METADATA_CAT_GRAPHSPACE);
            List<MicroCTPostProcessingStruct> results = port.searchMicroCTPostProcessing(speciesName, specimen, input, datasetURI, METADATA_CAT_GRAPHSPACE);
            System.out.println("Found "+results.size()+" results");
            results.forEach(result -> System.out.println(result.getPostProcessingURI()+"\t"+
                                                         result.getDatasetTitle()+"\t"+
                                                         result.getDescription()+"..."));
        } catch (Exception ex) {
            logger.error("An error occured while searching for microCT postprocessing metadata",ex);
        }   
    }
    
    private static void searchMicroCTPostProcessingWithinRange(){
        try{
            MetadataRepositorySearch_Service service = new MetadataRepositorySearch_Service();
            MetadataRepositorySearch port = service.getMetadataRepositorySearchPort();
            String speciesName="species";
            String specimen="specimen";
            String input="product";
            String datasetURI= "http://localhost/microct/dataset";
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
            results.forEach(result -> System.out.println(result.getPostProcessingURI()+"\t"+
                                                         result.getDatasetTitle()+"\t"+
                                                         result.getDescription()+"..."));
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
            results.forEach(result -> System.out.println(result.getAttributeAssignmentEventURI()+"\t"+
                                                         result.getDimensionValue()+"\t"+
                                                         result.getDimensionURI()+"..."));
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
            results.forEach(result -> System.out.println(result.getAttributeAssignmentEventURI()+"\t"+
                                                         result.getDimensionValue()+"\t"+
                                                         result.getDimensionURI()+"..."));
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
            results.forEach(result -> System.out.println(result.getOccurrenceEventURI()+"\t"+
                                                         result.getCoordinates()+"\t"+
                                                         result.getBibliographicCitation()+"..."));
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
            results.forEach(result -> System.out.println(result.getOccurrenceEventURI()+"\t"+
                                                         result.getCoordinates()+"\t"+
                                                         result.getBibliographicCitation()+"..."));
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
            results.forEach(result -> System.out.println(result.getOccurrenceEventURI()+"\t"+
                                                         result.getCoordinates()+"\t"+
                                                         result.getBibliographicCitation()+"..."));
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
            results.forEach(result -> System.out.println(result.getOccurrenceEventURI()+"\t"+
                                                         result.getCoordinates()+"\t"+
                                                         result.getBibliographicCitation()+"..."));
        } catch (Exception ex) {
            logger.error("An error occured while searching for occurrence metadata",ex);
        }   
    }
    
    private static void searchScientificNaming(){
        try{
            MetadataRepositorySearch_Service service = new MetadataRepositorySearch_Service();
            MetadataRepositorySearch port = service.getMetadataRepositorySearchPort();
            String speciesName="species";
            String date="timespan";
            String actor="actor";
            String scientificName="appellation";
            String datasetURI= "http://localhost/scientificnaming/dataset";
            System.out.println("Searching for scientific naming metadata with the following details: "+
                               "\tSpecies Name: "+speciesName+
                               "\tDate: "+date+
                               "\tActor: "+actor+
                               "\tScientific Name: "+scientificName+
                               "\tDataset URI: "+datasetURI+
                               "\tGraphspace: "+METADATA_CAT_GRAPHSPACE);
            List<ScientificNamingStruct> results = port.searchScientificNaming(speciesName, date, actor, datasetURI, scientificName, METADATA_CAT_GRAPHSPACE);
            System.out.println("Found "+results.size()+" results");
            results.forEach(result -> System.out.println(result.getScientificNameAssignmentEventURI()+"\t"+
                                                         result.getAppellationURI()+"\t"+
                                                         result.getDatasetName()+"..."));
        } catch (Exception ex) {
            logger.error("An error occured while searching for scientific naming metadata",ex);
        }   
    }
    
    private static void searchScientificNamingWithinRange(){
        try{
            MetadataRepositorySearch_Service service = new MetadataRepositorySearch_Service();
            MetadataRepositorySearch port = service.getMetadataRepositorySearchPort();
            String speciesName="species";
            String date="timespan";
            String actor="actor";
            String scientificName="appellation";
            String datasetURI= "http://localhost/scientificnaming/dataset";
            int offset=0;
            int limit=1;
            System.out.println("Searching for scientific naming metadata within a specific range with the following details: "+
                               "\tSpecies Name: "+speciesName+
                               "\tDate: "+date+
                               "\tActor: "+actor+
                               "\tScientific Name: "+scientificName+
                               "\tDataset URI: "+datasetURI+
                               "\tOffset: "+offset+
                               "\tLimit: "+limit+
                               "\tGraphspace: "+METADATA_CAT_GRAPHSPACE);
            List<ScientificNamingStruct> results = port.searchScientificNamingWithinRange(speciesName, date, actor, datasetURI, scientificName, offset, limit, METADATA_CAT_GRAPHSPACE);
            System.out.println("Found "+results.size()+" results");
            results.forEach(result -> System.out.println(result.getScientificNameAssignmentEventURI()+"\t"+
                                                         result.getAppellationURI()+"\t"+
                                                         result.getDatasetName()+"..."));
        } catch (Exception ex) {
            logger.error("An error occured while searching for scientific naming metadata",ex);
        }   
    }
    
    private static void searchSpecimen(){
        try{
            MetadataRepositorySearch_Service service = new MetadataRepositorySearch_Service();
            MetadataRepositorySearch port = service.getMetadataRepositorySearchPort();
            String specimen="specimen";
            String speciesName="species";
            String collection="collection";
            String datasetURI= "http://localhost/specimen/dataset";
            System.out.println("Searching for specimen metadata with the following details: "+
                               "\tSpecimen: "+specimen+
                               "\tSpecies Name: "+speciesName+
                               "\tCollection: "+collection+
                               "\tDataset URI: "+datasetURI+
                               "\tGraphspace: "+METADATA_CAT_GRAPHSPACE);
            List<SpecimenStruct> results = port.searchSpecimen(specimen, speciesName, collection, datasetURI, METADATA_CAT_GRAPHSPACE);
            System.out.println("Found "+results.size()+" results");
            results.forEach(result -> System.out.println(result.getSpecimenURI()+"\t"+
                                                         result.getIndividualURI()+"\t"+
                                                         result.getMethodName()+"..."));
        } catch (Exception ex) {
            logger.error("An error occured while searching for specimen metadata",ex);
        }   
    }
    
    private static void searchSpecimenWithinRange(){
        try{
            MetadataRepositorySearch_Service service = new MetadataRepositorySearch_Service();
            MetadataRepositorySearch port = service.getMetadataRepositorySearchPort();
            String specimen="specimen";
            String speciesName="species";
            String collection="collection";
            int offset=0;
            int limit=1;
            String datasetURI= "http://localhost/specimen/dataset";
            System.out.println("Searching for specimen metadata within a specific range with the following details: "+
                               "\tSpecimen: "+specimen+
                               "\tSpecies Name: "+speciesName+
                               "\tCollection: "+collection+
                               "\tDataset URI: "+datasetURI+
                               "\tOffset: "+offset+
                               "\tLimit: "+limit+
                               "\tGraphspace: "+METADATA_CAT_GRAPHSPACE);
            List<SpecimenStruct> results = port.searchSpecimenWithinRange(specimen, speciesName, collection, datasetURI, offset, limit, METADATA_CAT_GRAPHSPACE);
            System.out.println("Found "+results.size()+" results");
            results.forEach(result -> System.out.println(result.getSpecimenURI()+"\t"+
                                                         result.getIndividualURI()+"\t"+
                                                         result.getMethodName()+"..."));
        } catch (Exception ex) {
            logger.error("An error occured while searching for specimen metadata",ex);
        }   
    }
    
    private static void searchSpecimenCollection(){
        try{
            MetadataRepositorySearch_Service service = new MetadataRepositorySearch_Service();
            MetadataRepositorySearch port = service.getMetadataRepositorySearchPort();
            String collection="collection";
            String owner="owner";
            String datasetURI= "http://localhost/specimencollection/dataset";
            System.out.println("Searching for specimen collection metadata with the following details: "+
                               "\tCollection: "+collection+
                               "\tOwner: "+owner+
                               "\tDataset URI: "+datasetURI+
                               "\tGraphspace: "+METADATA_CAT_GRAPHSPACE);
            List<SpecimenCollectionStruct> results = port.searchSpecimenCollection(collection, owner, datasetURI, METADATA_CAT_GRAPHSPACE);
            System.out.println("Found "+results.size()+" results");
            results.forEach(result -> System.out.println(result.getCollectionURI()+"\t"+
                                                         result.getCollectionName()+"\t"+
                                                         result.getDatasetName()+"..."));
        } catch (Exception ex) {
            logger.error("An error occured while searching for specimen collection metadata",ex);
        }   
    }
    
    private static void searchSpecimenCollectionWithinRange(){
        try{
            MetadataRepositorySearch_Service service = new MetadataRepositorySearch_Service();
            MetadataRepositorySearch port = service.getMetadataRepositorySearchPort();
            String collection="collection";
            String owner="owner";
            int offset=0;
            int limit=1;
            String datasetURI= "http://localhost/specimencollection/dataset";
            System.out.println("Searching for specimen collection metadata within a specific range with the following details: "+
                               "\tCollection: "+collection+
                               "\tOwner: "+owner+
                               "\tDataset URI: "+datasetURI+
                               "\tOffset: "+offset+
                               "\tLimit: "+limit+
                               "\tGraphspace: "+METADATA_CAT_GRAPHSPACE);
            List<SpecimenCollectionStruct> results = port.searchSpecimenCollectionWithinRange(collection, owner, datasetURI, offset, limit, METADATA_CAT_GRAPHSPACE);
            System.out.println("Found "+results.size()+" results");
            results.forEach(result -> System.out.println(result.getCollectionURI()+"\t"+
                                                         result.getCollectionName()+"\t"+
                                                         result.getDatasetName()+"..."));
        } catch (Exception ex) {
            logger.error("An error occured while searching for specimen collection metadata",ex);
        }   
    }
    
    private static void searchStats(){
        try{
            MetadataRepositorySearch_Service service = new MetadataRepositorySearch_Service();
            MetadataRepositorySearch port = service.getMetadataRepositorySearchPort();
            String speciesName="species";
            String dimension="dimension";
            String datasetURI= "http://localhost/stats/dataset";
            System.out.println("Searching for stats metadata with the following details: "+
                               "\tSpecies Name: "+speciesName+
                               "\tDimension: "+dimension+
                               "\tDataset URI: "+datasetURI+
                               "\tGraphspace: "+METADATA_CAT_GRAPHSPACE);
            List<StatsStruct> results = port.searchStats(speciesName, dimension, datasetURI, METADATA_CAT_GRAPHSPACE);
            System.out.println("Found "+results.size()+" results");
            results.forEach(result -> System.out.println(result.getSpecimenName()+"\t"+
                                                         result.getSpecimenURI()+"\t"+
                                                         result.getDatasetName()+"..."));
        } catch (Exception ex) {
            logger.error("An error occured while searching for stats metadata",ex);
        }   
    }
    
    private static void searchStatsWithinRange(){
        try{
            MetadataRepositorySearch_Service service = new MetadataRepositorySearch_Service();
            MetadataRepositorySearch port = service.getMetadataRepositorySearchPort();
            String speciesName="species";
            String dimension="dimension";
            int offset=0;
            int limit=1;
            String datasetURI= "http://localhost/stats/dataset";
            System.out.println("Searching for stats metadata within a specific range with the following details: "+
                               "\tSpecies Name: "+speciesName+
                               "\tDimension: "+dimension+
                               "\tDataset URI: "+datasetURI+
                               "\tOffset: "+offset+
                               "\tLimit: "+limit+
                               "\tGraphspace: "+METADATA_CAT_GRAPHSPACE);
            List<StatsStruct> results = port.searchStatsWithinRange(speciesName, dimension, datasetURI, offset, limit, METADATA_CAT_GRAPHSPACE);
            System.out.println("Found "+results.size()+" results");
            results.forEach(result -> System.out.println(result.getSpecimenName()+"\t"+
                                                         result.getSpecimenURI()+"\t"+
                                                         result.getDatasetName()+"..."));
        } catch (Exception ex) {
            logger.error("An error occured while searching for stats metadata",ex);
        }   
    }
    
    private static void searchSynonym(){
        try{
            MetadataRepositorySearch_Service service = new MetadataRepositorySearch_Service();
            MetadataRepositorySearch port = service.getMetadataRepositorySearchPort();
            String speciesName="species";
            String scientificName="appellation";
            String synonym="synonym";
            String datasetURI= "http://localhost/synonym/dataset";
            System.out.println("Searching for synonym metadata with the following details: "+
                               "\tSpecies Name: "+speciesName+
                               "\tScientific Name: "+scientificName+
                               "\tSynonym Name: "+synonym+
                               "\tDataset URI: "+datasetURI+
                               "\tGraphspace: "+METADATA_CAT_GRAPHSPACE);
            List<SynonymStruct> results = port.searchSynonym(speciesName, scientificName, synonym, datasetURI, METADATA_CAT_GRAPHSPACE);
            System.out.println("Found "+results.size()+" results");
            results.forEach(result -> System.out.println(result.getAppellation()+"\t"+
                                                         result.getAppellationURI()+"\t"+
                                                         result.getDatasetName()+"..."));
        } catch (Exception ex) {
            logger.error("An error occured while searching for synonym metadata",ex);
        }   
    }
    
    private static void searchSynonymWithinRange(){
        try{
            MetadataRepositorySearch_Service service = new MetadataRepositorySearch_Service();
            MetadataRepositorySearch port = service.getMetadataRepositorySearchPort();
            String speciesName="species";
            String scientificName="appellation";
            String synonym="synonym";
            int offset=0;
            int limit=1;
            String datasetURI= "http://localhost/synonym/dataset";
            System.out.println("Searching for synonym metadata within a specific range with the following details: "+
                               "\tSpecies Name: "+speciesName+
                               "\tScientific Name: "+scientificName+
                               "\tSynonym Name: "+synonym+
                               "\tDataset URI: "+datasetURI+
                               "\tOffset: "+offset+
                               "\tLimit: "+limit+
                               "\tGraphspace: "+METADATA_CAT_GRAPHSPACE);
            List<SynonymStruct> results = port.searchSynonymWithinRange(speciesName, scientificName, synonym, datasetURI, offset, limit, METADATA_CAT_GRAPHSPACE);
            System.out.println("Found "+results.size()+" results");
            results.forEach(result -> System.out.println(result.getAppellation()+"\t"+
                                                         result.getAppellationURI()+"\t"+
                                                         result.getDatasetName()+"..."));
        } catch (Exception ex) {
            logger.error("An error occured while searching for synonym metadata",ex);
        }   
    }
    
    private static void searchTaxonomy(){
        try{
            MetadataRepositorySearch_Service service = new MetadataRepositorySearch_Service();
            MetadataRepositorySearch port = service.getMetadataRepositorySearchPort();
            String speciesName="species";
            String genus="genus";
            String family="family";
            String order="order";
            String classs="class";
            String kingdom="kingdom";
            String phylum="phylum";
            String datasetURI= "http://localhost/taxonomy/dataset";
            System.out.println("Searching for taxonomy metadata with the following details: "+
                               "\tSpecies Name: "+speciesName+
                               "\tGenus Name: "+genus+
                               "\tFamily Name: "+family+
                               "\tOrder Name: "+order+
                               "\tClass Name: "+classs+
                               "\tKingdom Name: "+kingdom+
                               "\tPhylum Name: "+phylum+
                               "\tDataset URI: "+datasetURI+
                               "\tGraphspace: "+METADATA_CAT_GRAPHSPACE);
            List<TaxonomyStruct> results = port.searchTaxonomy(speciesName, genus, family, order, classs, kingdom, phylum, datasetURI, METADATA_CAT_GRAPHSPACE);
            System.out.println("Found "+results.size()+" results");
            results.forEach(result -> System.out.println(result.getSpeciesURI()+"\t"+
                                                         result.getFamilyURI()+"\t"+
                                                         result.getClassURI()+"..."));
        } catch (Exception ex) {
            logger.error("An error occured while searching for taxonomy metadata",ex);
        }   
    }
    
    private static void searchTaxonomyWithinRange(){
        try{
            MetadataRepositorySearch_Service service = new MetadataRepositorySearch_Service();
            MetadataRepositorySearch port = service.getMetadataRepositorySearchPort();
            String speciesName="species";
            String genus="genus";
            String family="family";
            String order="order";
            String classs="class";
            String kingdom="kingdom";
            String phylum="phylum";
            int offset=0;
            int limit=1;
            String datasetURI= "http://localhost/taxonomy/dataset";
            System.out.println("Searching for taxonomy metadata within a specific range with the following details: "+
                               "\tSpecies Name: "+speciesName+
                               "\tGenus Name: "+genus+
                               "\tFamily Name: "+family+
                               "\tOrder Name: "+order+
                               "\tClass Name: "+classs+
                               "\tKingdom Name: "+kingdom+
                               "\tPhylum Name: "+phylum+
                               "\tDataset URI: "+datasetURI+
                               "\tOffset: "+offset+
                               "\tLimit: "+limit+
                               "\tGraphspace: "+METADATA_CAT_GRAPHSPACE);
            List<TaxonomyStruct> results = port.searchTaxonomyWithinRange(speciesName, genus, family, order, classs, kingdom, phylum, datasetURI, offset, limit, METADATA_CAT_GRAPHSPACE);
            System.out.println("Found "+results.size()+" results");
            results.forEach(result -> System.out.println(result.getSpeciesURI()+"\t"+
                                                         result.getFamilyURI()+"\t"+
                                                         result.getClassURI()+"..."));
        } catch (Exception ex) {
            logger.error("An error occured while searching for taxonomy metadata",ex);
        }   
    }
    
    private static void searchResource(){
        try{
            MetadataRepositorySearch_Service service = new MetadataRepositorySearch_Service();
            MetadataRepositorySearch port = service.getMetadataRepositorySearchPort();
            String resourceURI= "http://localhost/commonname/dataset";
            System.out.println("Searching for a resource URI in the metadata catalogue with the following details: "+
                               "\tResource URI: "+resourceURI+
                               "\tGraphspace: "+METADATA_CAT_GRAPHSPACE);
            List<Triple> results = port.searchResource(resourceURI, METADATA_CAT_GRAPHSPACE);
            System.out.println("Found "+results.size()+" results");
            results.forEach(result -> System.out.println(result.getSubject()+"\t"+
                                                         result.getPredicate()+"\t"+
                                                         result.getObject()+"..."));
        } catch (Exception ex) {
            logger.error("An error occured while searching for a resource URI in the metadata catalogue",ex);
        }   
    }
    
    private static void searchLiteral(){
        try{
            MetadataRepositorySearch_Service service = new MetadataRepositorySearch_Service();
            MetadataRepositorySearch port = service.getMetadataRepositorySearchPort();
            String literalValue= "timespan";
            System.out.println("Searching for a literal value in the metadata catalogue with the following details: "+
                               "\tLiteral value: "+literalValue+
                               "\tGraphspace: "+METADATA_CAT_GRAPHSPACE);
            List<Triple> results = port.searchLiteral(literalValue, METADATA_CAT_GRAPHSPACE);
            System.out.println("Found "+results.size()+" results");
            results.forEach(result -> System.out.println(result.getSubject()+"\t"+
                                                         result.getPredicate()+"\t"+
                                                         result.getObject()+"..."));
        } catch (Exception ex) {
            logger.error("An error occured while searching for a literal value in the metadata catalogue",ex);
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
        searchMicroCTSpecimen();
        Commons.printSeparator();
//        searchMicroCTSpecimenWithinRange();
//        Commons.printSeparator();
        searchMicroCTScanning();
        Commons.printSeparator();
//        searchMicroCTScanningWithinRange();
//        Commons.printSeparator();
        searchMicroCTReconstruction();
        Commons.printSeparator();
//        searchMicroCTReconstructionWithinRange();
//        Commons.printSeparator();
        searchMicroCTPostProcessing();
        Commons.printSeparator();
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
//        searchOccurrence();
//        Commons.printSeparator();
//        searchOccurrenceWithinRange();
//        Commons.printSeparator();
//        searchScientificNaming();
//        Commons.printSeparator();
//        searchScientificNamingWithinRange();
//        Commons.printSeparator();
//        searchSpecimen();
//        Commons.printSeparator();
//        searchSpecimenWithinRange();
//        Commons.printSeparator();
//        searchSpecimenCollection();
//        Commons.printSeparator();
//        searchSpecimenCollectionWithinRange();
//        Commons.printSeparator();
//        searchStats();
//        Commons.printSeparator();
//        searchStatsWithinRange();
//        Commons.printSeparator();
//        searchSynonym();
//        Commons.printSeparator();
//        searchSynonymWithinRange();
//        Commons.printSeparator();
//        searchTaxonomy();
//        Commons.printSeparator();
//        searchTaxonomyWithinRange();
//        Commons.printSeparator();
//        searchResource();
//        Commons.printSeparator();
//        searchLiteral();
//        Commons.printSeparator();
    }
}
