package eu.lifewatch.dataservices.middleware.client.metadata;

import eu.lifewatch.dataservices.middleware.client.Commons;
import java.util.Arrays;
import org.apache.log4j.Logger;

/**
 * @author Yannis Marketakis (marketak 'at' ics 'dot' forth 'dot' gr)
 * @author Nikos Minadakis (minadakn 'at' ics 'dot' forth 'dot' gr)
 */
public class InsertClient {
    private static final Logger logger=Logger.getLogger(InsertClient.class);
    private static final String METADATA_CAT_GRAPHSPACE="http://www.ics.forth.gr/isl/lifewatch/metadata";
    
    private static void insert(){
        try{
            MetadataRepositoryInsert_Service service = new MetadataRepositoryInsert_Service();
            MetadataRepositoryInsert port = service.getMetadataRepositoryInsertPort();
            DirectoryStruct struct = new DirectoryStruct();
            struct.setDatasetURI("http://localhost/directory/dataset");
            struct.setDatasetName("dataset");
            struct.setDatasetID("dataset ID");
            struct.setDatasetType("dataset type");
            struct.setAccessMethod("access method");
            struct.setAccessMethodURI("http://localhost/directory/access_method");
            struct.setAccessRights("access rights");
            struct.setAccessRightsURI("http://localhost/directory/access_rights");
            struct.setAttributeAssignmentEventURI("http://localhost/directory/attribute_assignment");
            struct.setContactPoint("contact point");
            struct.setCreationDate("creation date");
            struct.setCreationEventURI("http://localhost/directory/creatio_event");
            struct.setCreatorName("creator name");
            struct.setCreatorURI("http://localhost/directory/creator");
            struct.setCuratorName("curator name");
            struct.setCuratorURI("http://localhost/directory/curator");
            struct.setDescription("description");
            struct.setEmbargoPeriod("embargo period");
            struct.setEmbargoState("embargo state");
            struct.setImageTitle("image title");
            struct.setImageURI("http://localhost/directory/image");
            struct.setKeeperName("keeper name");
            struct.setKeeperURI("http://localhost/directory/keeper");
            struct.setLocationURL("http://localhost/directory/location");
            struct.setOwnerName("owner name");
            struct.setOwnerURI("http://localhost/directory/owner");
            struct.setParentDatasetName("parent dataset");
            struct.setParentDatasetURI("http://localhost/directory/parent_dataset");
            struct.setPublicationDate("publication date");
            struct.setPublicationEventURI("http://localhost/directory/publication_event");
            struct.setPublisherName("publisher name");
            struct.setPublisherURI("http://localhost/directory/publisher");
            struct.setRightsHolderName("rights holder");
            struct.setRightsHolderURI("http://localhost/directory/rights_holder");
            Pair contributor1=new Pair();
            contributor1.setKey("http://localhost/directory/contributor1");
            contributor1.setValue("contributor 1");
            Pair contributor2=new Pair();
            contributor2.setKey("http://localhost/directory/contributor2");
            contributor2.setValue("contributor 2");
            struct.contributors=Arrays.asList(contributor1,contributor2);
            System.out.println("Adding new (Directory) metadata with the following details: "+
                               "\tDataset Name: "+struct.getDatasetName()+
                               "\tOwner Name: "+struct.getOwnerName()+
                               "\tDataset URI: "+struct.getDatasetURI()+
                               "\tDataset Type: "+struct.getDatasetType()+
                               "\tGraphspace: "+METADATA_CAT_GRAPHSPACE+" ...");
            boolean result = port.insert(struct, METADATA_CAT_GRAPHSPACE);
            System.out.println("Result= "+result);
        } catch (Exception ex) {
            logger.error("An error occured while adding new (Directory) metadata",ex);
        }
    }
    
    private static void insertMesurement(){
        try{
            MetadataRepositoryInsert_Service service = new MetadataRepositoryInsert_Service();
            MetadataRepositoryInsert port = service.getMetadataRepositoryInsertPort();
            MeasurementStruct struct = new MeasurementStruct();
            struct.setDatasetURI("http://localhost/measurement/dataset");
            struct.setDatasetName("measurement dataset");
            struct.setDimensionName("measurement dimension name");
            struct.setDimensionTypeURI("http://localhost/measurement/dimension_type");
            struct.setDimensionURI("http://localhost/measurement/dimension");
            struct.setDimensionUnit("measurement dimension unit");
            struct.setDimensionValue("measurement dimension value");
            struct.setMeasurementEventURI("http://localhost/measurement/event");
            struct.setSpeciesName("measurement species name");
            struct.setSpeciesURI("http://localhost/measurement/species");
            struct.setSpecimenName("measurement specimen name");
            struct.setSpecimenURI("http://localhost/measurement/specimen");
            struct.setTimeSpan("measurement timespan");
            Pair actor1=new Pair();
            actor1.setKey("http://localhost/measurement/actor1");
            actor1.setValue("measurement actor 1");
            Pair actor2=new Pair();
            actor2.setKey("http://localhost/measurement/actor2");
            actor2.setValue("measurement actor 2");
            struct.actors=Arrays.asList(actor1,actor2);
            System.out.println("Adding new (Measurement) metadata with the following details: "+
                               "\tDataset Name: "+struct.getDatasetName()+
                               "\tDataset URI: "+struct.getDatasetURI()+
                               "\tMeasurementEvent URI: "+struct.getMeasurementEventURI()+
                               "\tGraphspace: "+METADATA_CAT_GRAPHSPACE+" ...");
            boolean result = port.insertMeasurement(struct, METADATA_CAT_GRAPHSPACE);
            System.out.println("Result= "+result);
        } catch (Exception ex) {
            logger.error("An error occured while adding new (Measurement) metadata",ex);
        }
    }
    
    private static void insertCommonName(){
         try{
            MetadataRepositoryInsert_Service service = new MetadataRepositoryInsert_Service();
            MetadataRepositoryInsert port = service.getMetadataRepositoryInsertPort();
            CommonNameStruct struct = new CommonNameStruct();
            struct.setDatasetURI("http://localhost/commonname/dataset");
            struct.setDatasetName("common name dataset");
            struct.setSpeciesName("common name species name");
            struct.setSpeciesURI("http://localhost/commonname/species");
            struct.setCommonName("common name name");
            struct.setCommonNameURI("http://localhost/commonname/common_name");
            struct.setLanguage("common name language");
            struct.setLanguageURI("http://localhost/commonname/language");
            Pair place1=new Pair();
            place1.setKey("http://localhost/commonname/place1");
            place1.setValue("common name place 1");
            Pair place2=new Pair();
            place2.setKey("http://localhost/commonname/place2");
            place2.setValue("common name place 2");
            struct.places=Arrays.asList(place1,place2);
            System.out.println("Adding new (Common Name) metadata with the following details: "+
                               "\tDataset Name: "+struct.getDatasetName()+
                               "\tDataset URI: "+struct.getDatasetURI()+
                               "\tCommon Name URI: "+struct.getCommonNameURI()+
                               "\tCommon Name: "+struct.getCommonName()+
                               "\tGraphspace: "+METADATA_CAT_GRAPHSPACE+" ...");
            boolean result = port.insertCommonName(struct, METADATA_CAT_GRAPHSPACE);
            System.out.println("Result= "+result);
        } catch (Exception ex) {
            logger.error("An error occured while adding new (Common Name) metadata",ex);
        }
    }
    
    private static void insertEnvironmental(){
        try{
            MetadataRepositoryInsert_Service service = new MetadataRepositoryInsert_Service();
            MetadataRepositoryInsert port = service.getMetadataRepositoryInsertPort();
            EnvironmentalStruct struct = new EnvironmentalStruct();
            struct.setDatasetURI("http://localhost/environmental/dataset");
            struct.setDatasetName("environmental dataset");
            struct.setDimensionName("environmetal dimension name");
            struct.setDimensionTypeURI("http://localhost/environmental/type");
            struct.setDimensionURI("http://localhost/environmental/environmental");
            struct.setDimensionUnit("environmental unit");
            struct.setDimensionValue("environmental value");
            struct.setMeasurementEventURI("http://localhost/environmental/mesurement_event");
            struct.setPlaceName("environmental place");
            struct.setPlaceURI("http://localhost/environmental/place");
            struct.setStationName("environmental station");
            struct.setStationURI("http://localhost/environmental/station");
            struct.setTimeSpan("environmental timespan");
            Pair actor1=new Pair();
            actor1.setKey("http://localhost/environmental/actor1");
            actor1.setValue("environmental actor 1");
            Pair actor2=new Pair();
            actor2.setKey("http://localhost/environmental/actor2");
            actor2.setValue("environmental actor 2");
            struct.actors=Arrays.asList(actor1,actor2);
            System.out.println("Adding new (Environmental) metadata with the following details: "+
                               "\tDataset Name: "+struct.getDatasetName()+
                               "\tDataset URI: "+struct.getDatasetURI()+
                               "\tEnvironmental Value: "+struct.getDimensionValue()+
                               "\tEnvironmental Unit: "+struct.getDimensionUnit()+
                               "\tGraphspace: "+METADATA_CAT_GRAPHSPACE+" ...");
            boolean result = port.insertEnvironmental(struct, METADATA_CAT_GRAPHSPACE);
            System.out.println("Result= "+result);
        } catch (Exception ex) {
            logger.error("An error occured while adding new (Environmental) metadata",ex);
        }
    }
    
    private static void insertIdentification(){
        try{
            MetadataRepositoryInsert_Service service = new MetadataRepositoryInsert_Service();
            MetadataRepositoryInsert port = service.getMetadataRepositoryInsertPort();
            IdentificationStruct struct = new IdentificationStruct();
            struct.setDatasetURI("http://localhost/identification/dataset");
            struct.setDatasetTitle("identification dataset");
            struct.setIdentificationEventURI("http://localhost/identification/event");
            struct.setIdentificationReferencesName("identification references name");
            struct.setIdentificationReferencesURI("http://localhost/identification/references");
            struct.setIndividualURI("http://localhost/identification/individual");
            struct.setLocalityName("identification locality name");
            struct.setLocalityURI("http://localhost/identification/locality");
            struct.setSpeciesName("identification species name");
            struct.setSpeciesURI("http://localhost/identification/species");
            struct.setTimeSpan("identification timespan");
            Pair actor1=new Pair();
            actor1.setKey("http://localhost/identification/actor1");
            actor1.setValue("identification actor 1");
            Pair actor2=new Pair();
            actor2.setKey("http://localhost/identification/actor2");
            actor2.setValue("identification actor 2");
            struct.actors=Arrays.asList(actor1,actor2);
            System.out.println("Adding new (Identification) metadata with the following details: "+
                               "\tDataset Name: "+struct.getDatasetTitle()+
                               "\tDataset URI: "+struct.getDatasetURI()+
                               "\tIdentification URI: "+struct.getIdentificationEventURI()+
                               "\tGraphspace: "+METADATA_CAT_GRAPHSPACE+" ...");
            boolean result = port.insertIdentification(struct, METADATA_CAT_GRAPHSPACE);
            System.out.println("Result= "+result);
        } catch (Exception ex) {
            logger.error("An error occured while adding new (Identification) metadata",ex);
        }
    }
    
    private static void insertScientificNaming(){
        try{
            MetadataRepositoryInsert_Service service = new MetadataRepositoryInsert_Service();
            MetadataRepositoryInsert port = service.getMetadataRepositoryInsertPort();
            ScientificNamingStruct struct = new ScientificNamingStruct();
            struct.setDatasetURI("http://localhost/scientificnaming/dataset");
            struct.setDatasetName("scientific naming dataset");
            struct.setSpeciesName("scientific naming species name");
            struct.setSpeciesURI("http://localhost/scientificnaming/species");
            struct.setTimeSpan("scientific naming timespan");
            struct.setAppellation("scientific naming appellation");
            struct.setAppellationURI("http://localhost/scientificnaming/appellation");
            struct.setNomenclaturalCodeName("scientific naming nomenclature code name");
            struct.setNomenclaturalCodeURI("http://locahost/scientificnaming/nomenclature_code");
            struct.setScientificNameAssignmentEventURI("http://localhost/scientificnaming/scientific_name_assignment_event");
            Pair actor1=new Pair();
            actor1.setKey("http://localhost/scientificnaming/actor1");
            actor1.setValue("scientific naming actor 1");
            Pair actor2=new Pair();
            actor2.setKey("http://localhost/scientificnaming/actor2");
            actor2.setValue("scientific naming actor 2");
            struct.actors=Arrays.asList(actor1,actor2);
            System.out.println("Adding new (Scientific Naming) metadata with the following details: "+
                               "\tDataset Name: "+struct.getDatasetName()+
                               "\tDataset URI: "+struct.getDatasetURI()+
                               "\tScientific Naming Assignment EventURI: "+struct.getScientificNameAssignmentEventURI()+
                               "\tGraphspace: "+METADATA_CAT_GRAPHSPACE+" ...");
            boolean result = port.insertScientificNaming(struct, METADATA_CAT_GRAPHSPACE);
            System.out.println("Result= "+result);
        } catch (Exception ex) {
            logger.error("An error occured while adding new (Scientific Naming) metadata",ex);
        }
    }
    
    private static void insertTaxonomy(){
        try{
            MetadataRepositoryInsert_Service service = new MetadataRepositoryInsert_Service();
            MetadataRepositoryInsert port = service.getMetadataRepositoryInsertPort();
            TaxonomyStruct struct = new TaxonomyStruct();
            struct.setDatasetURI("http://localhost/taxonomy/dataset");
            struct.setDatasetName("taxonomy dataset");
            struct.setSpeciesName("taxonomy species name");
            struct.setSpeciesURI("http://localhost/taxonomy/species");
            struct.setClassName("taxonomy class name");
            struct.setClassURI("http://localhost/taxonomy/class");
            struct.setFamilyName("taxonomy family");
            struct.setFamilyURI("http://localhost/taxonomy/family");
            struct.setGenusName("taxonomy genus");
            struct.setGenusURI("http://localhost/taxonomy/genus");
            struct.setKingdomName("taxonomy kingdom");
            struct.setKingdomURI("http://localhost/taxonomy/kingdom");
            struct.setOrderName("taxonomy order");
            struct.setOrderURI("http://localhost/taxonomy/order");
            struct.setPhylumName("taxonomy phylum");
            struct.setPhylumURI("http://localhost/taxonomy/phylum");
            System.out.println("Adding new (Taxonomy) metadata with the following details: "+
                               "\tDataset Name: "+struct.getDatasetName()+
                               "\tDataset URI: "+struct.getDatasetURI()+
                               "\tSpecies Uri: "+struct.getSpeciesURI()+
                               "\tGraphspace: "+METADATA_CAT_GRAPHSPACE+" ...");
            boolean result = port.insertTaxonomy(struct, METADATA_CAT_GRAPHSPACE);
            System.out.println("Result= "+result);
        } catch (Exception ex) {
            logger.error("An error occured while adding new (Taxonomy) metadata",ex);
        }
    }
    
    private static void inserMicroCTPostProcessing(){
        try{
            MetadataRepositoryInsert_Service service = new MetadataRepositoryInsert_Service();
            MetadataRepositoryInsert port = service.getMetadataRepositoryInsertPort();
            MicroCTPostProcessingStruct struct = new MicroCTPostProcessingStruct();
            struct.setDatasetURI("http://localhost/microctpostprocessing/dataset");
            struct.setDatasetTitle("microct postprocessing dataset");
            struct.setActorName("microct postprocessing actor");
            struct.setActorURI("http://localhost/microctpostprocessing/actor");
            struct.setDescription("microct postprocessing desccription");
            struct.setPostProcessingURI("http://localhost/microctpostprocessing/postprocessing");
            Pair input1=new Pair();
            input1.setKey("http://localhost/microctpostprocessing/input1");
            input1.setValue("microct postprocessing input 1");
            Pair input2=new Pair();
            input2.setKey("http://localhost/microctpostprocessing/input2");
            input2.setValue("microct postprocessing input 2");
            struct.inputs=Arrays.asList(input1,input2);
            Pair product1=new Pair();
            product1.setKey("http://localhost/microctpostprocessing/product1");
            product1.setValue("microct postprocessing product 1");
            Pair product2=new Pair();
            product2.setKey("http://localhost/microctpostprocessing/product2");
            product2.setValue("microct postprocessing product 2");
            struct.products=Arrays.asList(product1,product2);
            System.out.println("Adding new (MicroCT PostProcessing) metadata with the following details: "+
                               "\tDataset Name: "+struct.getDatasetTitle()+
                               "\tDataset URI: "+struct.getDatasetURI()+
                               "\tMicroCT PostProcessing URI: "+struct.getPostProcessingURI()+
                               "\tGraphspace: "+METADATA_CAT_GRAPHSPACE+" ...");
            boolean result = port.insertMicroCTPostProcessing(struct, METADATA_CAT_GRAPHSPACE);
            System.out.println("Result= "+result);
        } catch (Exception ex) {
            logger.error("An error occured while adding new (MicroCT PostProcessing) metadata",ex);
        }
    }
    
    private static void insertGensDataset(){
        try{
            MetadataRepositoryInsert_Service service = new MetadataRepositoryInsert_Service();
            MetadataRepositoryInsert port = service.getMetadataRepositoryInsertPort();
            GensDatasetStruct struct = new GensDatasetStruct();
            struct.setDatasetURI("http://localhost/gensdataset/dataset");
            struct.setDatasetTitle("gensdataset dataset");
            struct.setDescription("gens dataset desccription");
            struct.setDevice("gens dataset device");
            struct.setDeviceType("gens dataset device type");
            struct.setDeviceTypeURI("http://localhost/gensdataset/device_type");
            struct.setDeviceURI("http://localhost/gensdataset/device");
            struct.setEcosystemName("gens dataset ecosystem");
            struct.setEcosystemURI("http://localhost/gensdataset/ecosystem");
            struct.setHabitatName("gens dataset habitat");
            struct.setHabitatURI("http://localhost/gensdataset/habitat");
            struct.setProducedFile("gens dataset produced file");
            struct.setProducedFileURI("http://localhost/gensdataset/produced_file");
            struct.setSampleName("gens dataset sample name");
            struct.setSampleTakingURI("http://localhost/gensdataset/sample_taking");
            struct.setSampleURI("http://localhost/gensdataset/sample");
            struct.setSequencingEventURI("http://localhost/gensdataset/sequencing_event");
            struct.setSpeciesName("gens dataset species");
            struct.setSpeciesURI("http://localhost/gensdataset/species");
            struct.setTimeSpan("gens dataset timespan");
            System.out.println("Adding new (Gens Dataset) metadata with the following details: "+
                               "\tDataset Name: "+struct.getDatasetTitle()+
                               "\tDataset URI: "+struct.getDatasetURI()+
                               "\tSample URI: "+struct.getSampleURI()+
                               "\tGraphspace: "+METADATA_CAT_GRAPHSPACE+" ...");
            boolean result = port.insertGensDataset(struct, METADATA_CAT_GRAPHSPACE);
            System.out.println("Result= "+result);
        } catch (Exception ex) {
            logger.error("An error occured while adding new (Gens Dataset) metadata",ex);
        }
    }
    
    private static void insertOccurrenceStatsTemp(){
        try{
            MetadataRepositoryInsert_Service service = new MetadataRepositoryInsert_Service();
            MetadataRepositoryInsert port = service.getMetadataRepositoryInsertPort();
            OccurrenceStatsTempStruct struct = new OccurrenceStatsTempStruct();
            struct.setDatasetURI("http://localhost/occurrencestatstemp/dataset");
            struct.setDatasetTitle("occurrence stats temp dataset");
            struct.setEcosystemName("occurrence stats temp ecosystem");
            struct.setEcosystemURI("http://localhost/occurrencestatstemp/ecosystem");
            struct.setHabitatName("occurrence stats temp habitat");
            struct.setHabitatURI("http://localhost/occurrencestatstemp/habitat");
            struct.setTimeSpan("occurrence stats temp timespan");
            struct.setBibliographicCitation("occurrence stats temp bibliographic citation");
            struct.setCoordinates("occurrence stats temp coordinates");
            struct.setCountryName("occurrence stats temp country name");
            struct.setCountryURI("http://localhost/occurrencestatstemp/country");
            struct.setDescription("occurrence stats temp description");
            struct.setEquipmentTypeName("occurrence stats temp equipment type");
            struct.setEquipmentTypeURI("http://localhost/occurrencestatstemp/equipment_type");
            struct.setLatitude("occurrence stats temp latitude");
            struct.setLongitude("occurrence stats temp longitude");
            struct.setLocalityName("occurrence stats temp locality name");
            struct.setLocalityURI("http://localhost/occurrencestatstemp/locality_name");
            struct.setMaximumDepth("occurrence stats temp maximum depth");
            struct.setMinimumDepth("occurrence stats temp minimum depth");
            struct.setNumberOfParts("occurrence stats temp number of parts");
            struct.setOccurrenceEventURI("http://localhost/occurrencestatstemp/occurrence_event");
            struct.setPhysicalObjectURI("http://localhost/occurrencestatstemp/physical_object");
            struct.setSamplingProtocolName("occurrence stats temp sampling protocol name");
            struct.setSamplingProtocolURI("http://localhost/occurrencestatstemp/sampling_protocol");
            struct.setSpeciesName("occurrence stats temp species name");
            struct.setSpeciesURI("http://localhost/occurrencestatstemp/species");
            struct.setStationURI("http://localhost/occurrencestatstemp/station");
            struct.setTemporaryAggregateURI("http://localhost/occurrencestatstemp/temporary_aggregate");
            struct.setTemporaryAggregate("occurrence stats temp temporary aggregate");
            struct.setWaterAreaName("occurrence stats temp water area");
            struct.setWaterAreaURI("http://localhost/occurrencestatstemp/water_area");
            Pair actor1=new Pair();
            actor1.setKey("http://localhost/occurrencestatstemp/actor1");
            actor1.setValue("occurrence stats temp actor 1");
            Pair actor2=new Pair();
            actor2.setKey("http://localhost/occurrencestatstemp/actor2");
            actor2.setValue("occurrence stats temp actor 2");
            struct.actors=Arrays.asList(actor1,actor2);
            System.out.println("Adding new (Occurrence Stats Temp) metadata with the following details: "+
                               "\tDataset Name: "+struct.getDatasetTitle()+
                               "\tDataset URI: "+struct.getDatasetURI()+
                               "\tOccurrence Event URI: "+struct.getOccurrenceEventURI()+
                               "\tGraphspace: "+METADATA_CAT_GRAPHSPACE+" ...");
            boolean result = port.insertOccurrenceStatsTemp(struct, METADATA_CAT_GRAPHSPACE);
            System.out.println("Result= "+result);
        } catch (Exception ex) {
            logger.error("An error occured while adding new (Occurrence Stats Temp) metadata",ex);
        }
    }
    
    private static void insertSpecimen(){
        try{
            MetadataRepositoryInsert_Service service = new MetadataRepositoryInsert_Service();
            MetadataRepositoryInsert port = service.getMetadataRepositoryInsertPort();
            SpecimenStruct struct = new SpecimenStruct();
            struct.setDatasetURI("http://localhost/specimen/dataset");
            struct.setDatasetName("specimen dataset");
            struct.setTimeSpan("specimen timespan");
            struct.setCollectionName("specimen collection");
            struct.setCollectionURI("http://localhost/specimen/collection");
            struct.setIndividualURI("http://localhost/specimen/individual");
            struct.setMethodName("specimen method");
            struct.setMethodURI("http://localhost/specimen/method");
            struct.setSpeciesName("specimen species");
            struct.setSpeciesURI("http://localhost/specimen/species");
            struct.setSpecimenName("specimen specimen");
            struct.setSpecimenURI("http://localhost/specimen/specimen");
            struct.setTransformationEventURI("http://localhost/specimen/transformation_event");
            Pair actor1=new Pair();
            actor1.setKey("http://localhost/specimen/actor1");
            actor1.setValue("specimen actor 1");
            Pair actor2=new Pair();
            actor2.setKey("http://localhost/specimen/actor2");
            actor2.setValue("specimen actor 2");
            struct.actors=Arrays.asList(actor1,actor2);
            System.out.println("Adding new (Specimen) metadata with the following details: "+
                               "\tDataset Name: "+struct.getDatasetName()+
                               "\tDataset URI: "+struct.getDatasetURI()+
                               "\tSpecimen URI: "+struct.getSpecimenURI()+
                               "\tGraphspace: "+METADATA_CAT_GRAPHSPACE+" ...");
            boolean result = port.insertSpecimen(struct, METADATA_CAT_GRAPHSPACE);
            System.out.println("Result= "+result);
        } catch (Exception ex) {
            logger.error("An error occured while adding new (Specimen) metadata",ex);
        }
    }
    
    private static void insertSpecimenCollection(){
        try{
            MetadataRepositoryInsert_Service service = new MetadataRepositoryInsert_Service();
            MetadataRepositoryInsert port = service.getMetadataRepositoryInsertPort();
            SpecimenCollectionStruct struct = new SpecimenCollectionStruct();
            struct.setDatasetURI("http://localhost/specimencollection/dataset");
            struct.setDatasetName("specimen collection dataset");
            struct.setCollectionName("specimen collection collection");
            struct.setCollectionURI("http://localhost/specimencollection/collection");
            struct.setContactPoint("specimen collection contact point");
            struct.setCreationEventURI("http://localhost/specimencollection/creation_event");
            struct.setCreatorName("specimen collection creator");
            struct.setCreatorURI("http://localhost/specimencollection/creator");
            struct.setCuratorName("specimen collection curator");
            struct.setCuratorURI("http://localhost/specimencollection/curator");
            struct.setKeeperName("specimen collection keeper");
            struct.setKeeperURI("http://localhost/specimencollection/keeper");
            struct.setNote("specimen collection description");
            struct.setOwnerName("specimen collection owner");
            struct.setOwnerURI("http://localhost/specimencollection/owner");
            struct.setTimespan("specimen collection timespan");
            System.out.println("Adding new (Specimen Collection) metadata with the following details: "+
                               "\tDataset Name: "+struct.getDatasetName()+
                               "\tDataset URI: "+struct.getDatasetURI()+
                               "\tCollection URI: "+struct.getCollectionURI()+
                               "\tGraphspace: "+METADATA_CAT_GRAPHSPACE+" ...");
            boolean result = port.insertSpecimenCollection(struct, METADATA_CAT_GRAPHSPACE);
            System.out.println("Result= "+result);
        } catch (Exception ex) {
            logger.error("An error occured while adding new (Specimen Collection) metadata",ex);
        }
    }
    
    private static void insertMicroCTReconstruction(){
        try{
            MetadataRepositoryInsert_Service service = new MetadataRepositoryInsert_Service();
            MetadataRepositoryInsert port = service.getMetadataRepositoryInsertPort();
            MicroCTReconstructionStruct struct = new MicroCTReconstructionStruct();
            struct.setDatasetURI("http://localhost/microctreconstruction/dataset");
            struct.setDatasetTitle("microct reconstruction dataset");
            struct.setTimespan("microct reconstruction timespan");
            struct.setActorName("microct reconstruction actor");
            struct.setActorURI("http://localhost/microctreconstruction/actor");
            struct.setDescription("microct reconstruction description");
            struct.setReconstructionURI("http://localhost/microctreconstruction/reconstruction");
            Pair input1=new Pair();
            input1.setKey("http://localhost/microctreconstruction/input1");
            input1.setValue("microct reconstruction input 1");
            Pair input2=new Pair();
            input2.setKey("http://localhost/microctreconstruction/input2");
            input2.setValue("microct reconstruction input 2");
            struct.inputs=Arrays.asList(input1,input2);
            Pair product1=new Pair();
            product1.setKey("http://localhost/microctreconstruction/product1");
            product1.setValue("microct reconstruction product 1");
            Pair product2=new Pair();
            product2.setKey("http://localhost/microctreconstruction/product2");
            product2.setValue("microct reconstruction product 2");
            struct.products=Arrays.asList(product1,product2);
            System.out.println("Adding new (MicroCT Reconstruction) metadata with the following details: "+
                               "\tDataset Name: "+struct.getDatasetTitle()+
                               "\tDataset URI: "+struct.getDatasetURI()+
                               "\tReconstruction URI: "+struct.getReconstructionURI()+
                               "\tGraphspace: "+METADATA_CAT_GRAPHSPACE+" ...");
            boolean result = port.insertMicroCTReconstruction(struct, METADATA_CAT_GRAPHSPACE);
            System.out.println("Result= "+result);
        } catch (Exception ex) {
            logger.error("An error occured while adding new (MicroCT Reconstruction) metadata",ex);
        }
    }
    
    private static void insertMicroCTScanning(){
        try{
            MetadataRepositoryInsert_Service service = new MetadataRepositoryInsert_Service();
            MetadataRepositoryInsert port = service.getMetadataRepositoryInsertPort();
            MicroCTScanningStruct struct = new MicroCTScanningStruct();
            struct.setDatasetURI("http://localhost/microctscanning/dataset");
            struct.setDatasetName("microct scanning dataset");
            struct.setTimespan("microct scanning timespan");
            struct.setActorName("microct scanning actor");
            struct.setActorURI("http://localhost/microctscanning/actor");
            struct.setDescription("microct scanning description");
            struct.setContrastMethod("microct scanning contrast method");
            struct.setDeviceName("microct scanning device");
            struct.setDeviceType("microct scanning device type");
            struct.setDeviceURI("http://localhost/microctscanning/device");
            struct.setEquipmentURI("http://localhost/microctscanning/equipment");
            struct.setMethodName("microct scanning method");
            struct.setMethodURI("http://localhost/microctscanning/method");
            struct.setScanningURI("http://localhost/microctscanning/scanning");
            struct.setSpecimenName("microct scanning specimen");
            struct.setSpecimenURI("http://localhost/microctscanning/specimen");
            Pair product1=new Pair();
            product1.setKey("http://localhost/microctscanning/product1");
            product1.setValue("microct scanning product 1");
            Pair product2=new Pair();
            product2.setKey("http://localhost/microctscanning/product2");
            product2.setValue("microct scanning product 2");
            struct.products=Arrays.asList(product1,product2);
            System.out.println("Adding new (MicroCT Scanning) metadata with the following details: "+
                               "\tDataset Name: "+struct.getDatasetName()+
                               "\tDataset URI: "+struct.getDatasetURI()+
                               "\tScanning URI: "+struct.getScanningURI()+
                               "\tGraphspace: "+METADATA_CAT_GRAPHSPACE+" ...");
            boolean result = port.insertMicroCTScanning(struct, METADATA_CAT_GRAPHSPACE);
            System.out.println("Result= "+result);
        } catch (Exception ex) {
            logger.error("An error occured while adding new (MicroCT Scanning) metadata",ex);
        }
    }
    
    private static void insertStats(){
        try{
            MetadataRepositoryInsert_Service service = new MetadataRepositoryInsert_Service();
            MetadataRepositoryInsert port = service.getMetadataRepositoryInsertPort();
            StatsStruct struct = new StatsStruct();
            struct.setDatasetURI("http://localhost/stats/dataset");
            struct.setDatasetName("stats dataset");
            struct.setDataEvaluationURI("http://localhost/stats/data_evaluation");
            struct.setDescription("stats description");
            struct.setSpecimenName("stats specimen");
            struct.setSpecimenURI("http://localhost/stats/specimen");
            struct.setDimensionName("stats dimension");
            struct.setDimensionTypeURI("http://localhost/stats/dimension_type");
            struct.setDimensionURI("http://localhost/stats/dimension");
            struct.setDimensionUnit("stats dimension unit");
            struct.setDimensionValue("stats dimension value");
            struct.setPublicationName("stats publication");
            struct.setPublicationURI("http://localhost/stats/publication");
            struct.setSpeciesName("stats species");
            struct.setSpeciesURI("http://localhost/stats/species");
            struct.setTimeSpan("stats timespan");
            Pair actor1=new Pair();
            actor1.setKey("http://localhost/stats/actor1");
            actor1.setValue("stats actor 1");
            Pair actor2=new Pair();
            actor2.setKey("http://localhost/stats/actor2");
            actor2.setValue("stats actor 2");
            struct.actors=Arrays.asList(actor1,actor2);
            System.out.println("Adding new (Stats) metadata with the following details: "+
                               "\tDataset Name: "+struct.getDatasetName()+
                               "\tDataset URI: "+struct.getDatasetURI()+
                               "\tDimenion URI: "+struct.getDimensionURI()+
                               "\tGraphspace: "+METADATA_CAT_GRAPHSPACE+" ...");
            boolean result = port.insertStats(struct, METADATA_CAT_GRAPHSPACE);
            System.out.println("Result= "+result);
        } catch (Exception ex) {
            logger.error("An error occured while adding new (Stats) metadata",ex);
        }
    }
    
    private static void insertGensSample(){
        try{
            MetadataRepositoryInsert_Service service = new MetadataRepositoryInsert_Service();
            MetadataRepositoryInsert port = service.getMetadataRepositoryInsertPort();
            GensSampleStruct struct = new GensSampleStruct();
            struct.setDatasetURI("http://localhost/genssample/dataset");
            struct.setDatasetName("gens sample dataset");
            struct.setDescription("gens sample description");
            struct.setDimensionName("gens sample dimension");
            struct.setDimensionTypeURI("http://localhost/genssample/dimension_type");
            struct.setDimensionURI("http://localhost/genssample/dimension");
            struct.setDimensionUnit("gens sample dimension unit");
            struct.setDimensionValue("gens sample dimension value");
            struct.setSpeciesName("gens sample species");
            struct.setSpeciesURI("http://localhost/genssample/species");
            struct.setDeviceType("gens sample device type");
            struct.setDeviceName("gens sample device name");
            struct.setDeviceURI("http://localhost/genssample/device");
            struct.setPlaceName("gens sample place");
            struct.setPlaceURI("http://localhost/place");
            struct.setPostProcessingURI("http://localhost/genssample/postprocessing");
            struct.setPostProductName("gens sample post product name");
            struct.setPostProductURI("http://localhost/genssample/post_product");
            struct.setProductName("gens sample product name");
            struct.setProductURI("http://localhost/genssample/product");
            struct.setSampleName("gens sample sample name");
            struct.setSampleURI("http://localhost/genssample/sample");
            struct.setSequencingURI("http://localhost/genssample/sequencing");
            struct.setTransformationURI("http://localhost/genssample/transformation");
            struct.setTransformedSampleName("gens sample transformed sample name");
            struct.setTransformedSampleURI("http://localhost/genssample/transformed_sample");
            System.out.println("Adding new (Gens Sample) metadata with the following details: "+
                               "\tDataset Name: "+struct.getDatasetName()+
                               "\tDataset URI: "+struct.getDatasetURI()+
                               "\tSample URI: "+struct.getSampleURI()+
                               "\tGraphspace: "+METADATA_CAT_GRAPHSPACE+" ...");
            boolean result = port.insertGensSample(struct, METADATA_CAT_GRAPHSPACE);
            System.out.println("Result= "+result);
        } catch (Exception ex) {
            logger.error("An error occured while adding new (Gens Sample) metadata",ex);
        }
    }
    
    private static void insertMicroCTSpecimen(){
        try{
            MetadataRepositoryInsert_Service service = new MetadataRepositoryInsert_Service();
            MetadataRepositoryInsert port = service.getMetadataRepositoryInsertPort();
            MicroCTSpecimenStruct struct = new MicroCTSpecimenStruct();
            struct.setDatasetURI("http://localhost/microctspecimen/dataset");
            struct.setDatasetName("microct specimen dataset");
            struct.setDescription("microct specimen description");
            struct.setDimensionName("microct specimen dimension");
            struct.setDimensionTypeURI("http://localhost/microctspecimen/dimension_type");
            struct.setDimensionURI("http://localhost/microctspecimen/dimension");
            struct.setDimensionUnit("microct specimen dimension unit");
            struct.setDimensionValue("microct specimen dimension value");
            struct.setSpeciesName("microct specimen species");
            struct.setSpeciesURI("http://localhost/microctspecimen/species");
            struct.setCollectionURI("http://localhost/microctspecimen/collection");
            struct.setCollectionName("microct specimen collection");
            struct.setFixationType("microct specimen fixation type");
            struct.setInstitutionName("microct specimen institution");
            struct.setInstitutionURI("http://localhost/microctspecimen/institution");
            struct.setPreservationType("microct specimen preservation type");
            struct.setProviderName("microct specimen provider");
            struct.setProviderURI("http://localhost/microctspecimen/provider");
            struct.setSpecimenName("microct specimen specimen");
            struct.setSpecimenType("microct specimen specimen type");
            struct.setSpecimenURI("http://localhost/microctspecimen/specimen");
            System.out.println("Adding new (MicroCT Specimen) metadata with the following details: "+
                               "\tDataset Name: "+struct.getDatasetName()+
                               "\tDataset URI: "+struct.getDatasetURI()+
                               "\tSpecimen URI: "+struct.getSpecimenURI()+
                               "\tGraphspace: "+METADATA_CAT_GRAPHSPACE+" ...");
            boolean result = port.insertMicroCTSpecimen(struct, METADATA_CAT_GRAPHSPACE);
            System.out.println("Result= "+result);
        } catch (Exception ex) {
            logger.error("An error occured while adding new (MicroCT Specimen) metadata",ex);
        }
    }
    
    private static void insertOccurrenceStatsAbundance(){
        try{
            MetadataRepositoryInsert_Service service = new MetadataRepositoryInsert_Service();
            MetadataRepositoryInsert port = service.getMetadataRepositoryInsertPort();
            OccurrenceStatsAbundanceStruct struct = new OccurrenceStatsAbundanceStruct();
            struct.setDatasetURI("http://localhost/occurrencestatsabundance/dataset");
            struct.setDatasetName("occurrence stats abundance dataset");
            struct.setDimensionName("occurrence stats abundance dimension");
            struct.setDimensionTypeURI("http://localhost/occurrencestatsabundance/dimension_type");
            struct.setDimensionURI("http://localhost/occurrencestatsabundance/dimension");
            struct.setDimensionUnit("occurrence stats abundance dimension unit");
            struct.setDimensionValue("occurrence stats abundance dimension value");
            struct.setSpeciesName("occurrence stats abundance species");
            struct.setSpeciesURI("http://localhost/occurrencestatsabundance/species");
            struct.setTemporaryAggregate("http://localhost/occurrencestatsabundance/temporary_aggregate");
            System.out.println("Adding new (Occurrence Stats Abundance) metadata with the following details: "+
                               "\tDataset Name: "+struct.getDatasetName()+
                               "\tDataset URI: "+struct.getDatasetURI()+
                               "\tTemporary Aggregate URI: "+struct.getTemporaryAggregate()+
                               "\tGraphspace: "+METADATA_CAT_GRAPHSPACE+" ...");
            boolean result = port.insertOccurrenceStatsAbundance(struct, METADATA_CAT_GRAPHSPACE);
            System.out.println("Result= "+result);
        } catch (Exception ex) {
            logger.error("An error occured while adding new (Occurrence Stats Abundance) metadata",ex);
        }
    }
    
    private static void insertMorphometrics(){
        try{
            MetadataRepositoryInsert_Service service = new MetadataRepositoryInsert_Service();
            MetadataRepositoryInsert port = service.getMetadataRepositoryInsertPort();
            MorphometricsStruct struct = new MorphometricsStruct();
            struct.setDatasetURI("http://localhost/morphometrics/dataset");
            struct.setDatasetName("morphometrics dataset");
            struct.setDimensionName("morphometrics dimension");
            struct.setDimensionTypeURI("http://localhost/morphometrics/dimension_type");
            struct.setDimensionURI("http://localhost/morphometrics/dimension");
            struct.setDimensionUnit("morphometrics dimension unit");
            struct.setDimensionValue("morphometrics dimension value");
            struct.setSpeciesName("morphometrics species");
            struct.setSpeciesURI("http://localhost/morphometrics/species");
            struct.setAttributeAssignmentEventURI("http://localhost/morphometrics/attribute_assignment_event");
            struct.setDescription("morphometrics desrciption");
            struct.setPublicationName("morphometrics publication");
            struct.setPublicationURI("http://localhost/morphometrics/publication");
            struct.setTimeSpan("morphometrics timespan");
            Pair actor1=new Pair();
            actor1.setKey("http://localhost/morphometrics/actor1");
            actor1.setValue("morphometrics actor 1");
            Pair actor2=new Pair();
            actor2.setKey("http://localhost/morphometrics/actor2");
            actor2.setValue("morphometrics actor 2");
            struct.actors=Arrays.asList(actor1,actor2);
            System.out.println("Adding new (Morphometrics) metadata with the following details: "+
                               "\tDataset Name: "+struct.getDatasetName()+
                               "\tDataset URI: "+struct.getDatasetURI()+
                               "\tAttribute Assignment Event URI: "+struct.getAttributeAssignmentEventURI()+
                               "\tGraphspace: "+METADATA_CAT_GRAPHSPACE+" ...");
            boolean result = port.insertMorphometrics(struct, METADATA_CAT_GRAPHSPACE);
            System.out.println("Result= "+result);
        } catch (Exception ex) {
            logger.error("An error occured while adding new (Morphometrics) metadata",ex);
        }
    }
    
    private static void insertOccurrence(){
        try{
            MetadataRepositoryInsert_Service service = new MetadataRepositoryInsert_Service();
            MetadataRepositoryInsert port = service.getMetadataRepositoryInsertPort();
            OccurrenceStruct struct = new OccurrenceStruct();
            struct.setDatasetURI("http://localhost/occurrence/dataset");
            struct.setDatasetTitle("occurrence dataset");
            struct.setSpeciesName("occurrence species");
            struct.setSpeciesURI("http://localhost/occurrence/species");
            struct.setDescription("occurrence desrciption");
            struct.setTimeSpan("occurrence timespan");
            struct.setBibliographicCitation("occurrence bibliographic citation");
            struct.setBibliographicCitationURI("http://localhost/occurrence/bilbiographic_citation");
            struct.setCoordinates("occurrence coordinates");
            struct.setCountryName("occurrence country");
            struct.setCountryURI("http://localhost/occurrence/country");
            struct.setEcosystemName("occurrence ecosystem");
            struct.setEcosystemURI("http://localhost/occurrence/ecosystem");
            struct.setEquipmentTypeName("occurrence equipment type");
            struct.setEquipmentTypeURI("http://localhost/occurrence/equipment_type");
            struct.setHabitatName("occurrence habitat");
            struct.setHabitatURI("http://localhost/occurrence/habitat");
            struct.setIndividualURI("http://localhost/occurrence/individual");
            struct.setLatitude("occurrence latitude");
            struct.setLocalityName("occurrence locality name");
            struct.setLocalityURI("http://localhost/occurrence/locality");
            struct.setLongitude("occurrence longitude");
            struct.setMaximumDepth("occurrence maximum depth");
            struct.setMinimumDepth("occurrence minimum depth");
            struct.setOccurrenceEventURI("http://localhost/occurrence/occurrence");
            struct.setSamplingProtocol("occurrence sampling protocol");
            struct.setSamplingProtocolURI("http://localhost/occurrence/sampling_protocol");
            struct.setStationNotes("occurrence station notes");
            struct.setStationURI("http://localhost/occurrence/station");
            struct.setWaterAreaName("occurrence water area");
            struct.setWaterAreaURI("http://localhost/occurrence/water_area");
            Pair actor1=new Pair();
            actor1.setKey("http://localhost/occurrence/actor1");
            actor1.setValue("occurrence actor 1");
            Pair actor2=new Pair();
            actor2.setKey("http://localhost/occurrence/actor2");
            actor2.setValue("occurrence actor 2");
            struct.actors=Arrays.asList(actor1,actor2);
            System.out.println("Adding new (Occurrence) metadata with the following details: "+
                               "\tDataset Name: "+struct.getDatasetTitle()+
                               "\tDataset URI: "+struct.getDatasetURI()+
                               "\tOccurrence Event URI: "+struct.getOccurrenceEventURI()+
                               "\tGraphspace: "+METADATA_CAT_GRAPHSPACE+" ...");
            boolean result = port.insertOccurrence(struct, METADATA_CAT_GRAPHSPACE);
            System.out.println("Result= "+result);
        } catch (Exception ex) {
            logger.error("An error occured while adding new (Occurrence) metadata",ex);
        }
    }
    
    private static void insertSynonym(){
        try{
            MetadataRepositoryInsert_Service service = new MetadataRepositoryInsert_Service();
            MetadataRepositoryInsert port = service.getMetadataRepositoryInsertPort();
            SynonymStruct struct = new SynonymStruct();
            struct.setDatasetURI("http://localhost/synonym/dataset");
            struct.setDatasetName("synonym dataset");
            struct.setSpeciesName("synonym species");
            struct.setSpeciesURI("http://localhost/synonym/species");
            struct.setAppellation("synonym appellation");
            struct.setAppellationURI("http://localhost/synonym/appellation");
            Pair synonym1=new Pair();
            synonym1.setKey("http://localhost/synonym/synonym2");
            synonym1.setValue("synonym synonym 1");
            Pair synonym2=new Pair();
            synonym2.setKey("http://localhost/synonym/synonym2");
            synonym2.setValue("synonym synonym 2");
            struct.synonyms=Arrays.asList(synonym1,synonym2);
            System.out.println("Adding new (Synonym) metadata with the following details: "+
                               "\tDataset Name: "+struct.getDatasetName()+
                               "\tDataset URI: "+struct.getDatasetURI()+
                               "\tAppellation URI: "+struct.getAppellationURI()+
                               "\tGraphspace: "+METADATA_CAT_GRAPHSPACE+" ...");
            boolean result = port.insertSynonym(struct, METADATA_CAT_GRAPHSPACE);
            System.out.println("Result= "+result);
        } catch (Exception ex) {
            logger.error("An error occured while adding new (Synonym) metadata",ex);
        }
    }
    
    public static void main(String[] args){
        insert();
        Commons.printSeparator();
        insertMesurement();
        Commons.printSeparator();
        insertCommonName();
        Commons.printSeparator();
        insertEnvironmental();
        Commons.printSeparator();
        insertIdentification();
        Commons.printSeparator();
        insertScientificNaming();
        Commons.printSeparator();
        insertTaxonomy();
        Commons.printSeparator();
        inserMicroCTPostProcessing();
        Commons.printSeparator();
        insertGensDataset();
        Commons.printSeparator();
        insertOccurrenceStatsTemp();
        Commons.printSeparator();
        insertSpecimen();
        Commons.printSeparator();
        insertSpecimenCollection();
        Commons.printSeparator();
        insertMicroCTReconstruction();
        Commons.printSeparator();
        insertMicroCTScanning();
        Commons.printSeparator();
        insertStats();
        Commons.printSeparator();
        insertGensSample();
        Commons.printSeparator();
        insertMicroCTSpecimen();
        Commons.printSeparator();
        insertOccurrenceStatsAbundance();
        Commons.printSeparator();
        insertMorphometrics();
        Commons.printSeparator();
        insertOccurrence();
        Commons.printSeparator();
        insertSynonym();
    }
}