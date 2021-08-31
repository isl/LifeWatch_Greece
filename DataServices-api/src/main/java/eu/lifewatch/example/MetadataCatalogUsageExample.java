package eu.lifewatch.example;

import eu.lifewatch.core.model.CommonNameStruct;
import eu.lifewatch.core.model.DirectoryStruct;
import eu.lifewatch.core.model.EnvironmentalStruct;
import eu.lifewatch.core.model.GensDatasetStruct;
import eu.lifewatch.core.model.GensSampleStruct;
import eu.lifewatch.core.model.IdentificationStruct;
import eu.lifewatch.core.model.MeasurementStruct;
import eu.lifewatch.core.model.MicroCTPostProcessingStruct;
import eu.lifewatch.core.model.MicroCTReconstructionStruct;
import eu.lifewatch.core.model.MicroCTScanningStruct;
import eu.lifewatch.core.model.MicroCTSpecimenStruct;
import eu.lifewatch.core.model.MorphometricsStruct;
import eu.lifewatch.core.model.OccurrenceStatsAbundanceStruct;
import eu.lifewatch.core.model.OccurrenceStatsTempStruct;
import eu.lifewatch.core.model.OccurrenceStruct;
import eu.lifewatch.core.model.ScientificNamingStruct;
import eu.lifewatch.core.model.SpecimenCollectionStruct;
import eu.lifewatch.core.model.SpecimenStruct;
import eu.lifewatch.core.model.StatsStruct;
import eu.lifewatch.core.model.SynonymStruct;
import eu.lifewatch.core.model.TaxonomyStruct;
import eu.lifewatch.exception.QueryExecutionException;
import eu.lifewatch.exception.URIValidationException;
import eu.lifewatch.service.impl.MetadataRepositoryService;
import gr.forth.ics.isl.timer.TimeUnit;
import gr.forth.ics.isl.timer.Timer;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
  * This class is responsible for demonstrating the usage of the Directory Service.
 * In particular at fisrt we create some data, ingest them to the repository and exploit the 
 * DirectoryService API to retrieve them. Information about the virtuoso repository that 
 * is being used can be found at beans.xml file.
 * 
 * @author Nikos Minadakis (minadakn 'at' ics 'dot' forth 'dot' gr)
 * @author Yannis Marketakis (marketak 'at' ics 'dot' forth 'dot' gr)
 */
public class MetadataCatalogUsageExample {
    private MetadataRepositoryService mrService;
    private List<EnvironmentalStruct> environmentalStructs;
    private List<CommonNameStruct> commonNameStructs;
    private List<IdentificationStruct> identificationStructs;
    private List<MeasurementStruct> measurementStructs;
    private List<MicroCTPostProcessingStruct> microCTPostProcessingStructs;
    private List<MicroCTReconstructionStruct> microCTReconstructionStructs;
    private List<MicroCTScanningStruct> microCTScanningStructs;
    private List<MorphometricsStruct> morphometricsStructs;
    private List<OccurrenceStruct> occurrenceStructs;
    private List<ScientificNamingStruct> scientificNameStructs;
    private List<SpecimenStruct> specimenStructs;
    private List<StatsStruct> statsStructs;
    private List<SynonymStruct> synonymStructs;
    private List<GensDatasetStruct> gensDatasetStructs;
    private List<GensSampleStruct> gensSampleStructs;
    private List<OccurrenceStatsTempStruct> occurrenceStatsTempStructs;
    private List<MicroCTSpecimenStruct> microCTSpecimenStructs;
    private List<OccurrenceStatsAbundanceStruct> occurrenceStatsAbundanceStructs;
    private List<SpecimenCollectionStruct> specimenCollectionStructs;
    private List<TaxonomyStruct> taxonomyStructs;
    private List<DirectoryStruct> directoryStructs;
    private final static int SIZE=10000;
    
    public MetadataCatalogUsageExample createMetadataCatalogClient(){
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        this.mrService=context.getBean(MetadataRepositoryService.class);
        this.environmentalStructs=this.createEnvironmentalStructs(SIZE);
        this.commonNameStructs=this.createCommonNameStructs(SIZE);
        this.identificationStructs=this.createIdentificationStructs(SIZE);
        this.measurementStructs=this.createMeasurementStructs(SIZE);
        this.microCTPostProcessingStructs=this.createMicroCTPostProcessingStructs(SIZE);
        this.microCTReconstructionStructs=this.createMicroCTReconstructionStructs(SIZE);
        this.microCTScanningStructs=this.createMicroCTScanningStructs(SIZE);
        this.morphometricsStructs=this.createMorphometricsStructs(SIZE);
        this.occurrenceStructs=this.createOccurrenceStructs(SIZE);
        this.scientificNameStructs=this.createScientificNameStructs(SIZE);
        this.specimenStructs=this.createSpecimenStructs(SIZE);
        this.statsStructs=this.createStatsStructs(SIZE);
        this.synonymStructs=this.createSynonymStructs(SIZE);
        this.gensDatasetStructs=this.createGensDatasetStructs(SIZE);
        this.gensSampleStructs=this.createGensSampleStructs(SIZE);
        this.occurrenceStatsTempStructs=this.createOccurrenceStatsTempStructs(SIZE);
        this.microCTSpecimenStructs=this.createMicroCTSpecimenStructs(SIZE);
        this.occurrenceStatsAbundanceStructs=this.createOccurrenceStatsAbundanceStructs(SIZE);
        this.specimenCollectionStructs=this.createSpecimenCollectionStructs(SIZE);
        this.taxonomyStructs=this.createTaxonomyStructs(SIZE);
        this.directoryStructs=this.createDirectoryStructs(SIZE);
      
        return this;
    }
    
    private MetadataCatalogUsageExample deleteOldData(){
          try{
            for(CommonNameStruct struct : this.commonNameStructs){
                this.mrService.deleteTriples(null, null, null, struct.getDatasetURI());  //remove everything
            }
            for(EnvironmentalStruct struct : this.environmentalStructs){
                this.mrService.deleteTriples(null, null, null, struct.getDatasetURI());  //remove everything
            }
            for(IdentificationStruct struct : this.identificationStructs){
                this.mrService.deleteTriples(null, null, null, struct.getDatasetURI());  //remove everything
            }
            for(MeasurementStruct struct : this.measurementStructs){
                this.mrService.deleteTriples(null, null, null, struct.getDatasetURI());  //remove everything
            }
            for(MicroCTPostProcessingStruct struct : this.microCTPostProcessingStructs){
                this.mrService.deleteTriples(null, null, null, struct.getDatasetURI());  //remove everything
            }
            for(MicroCTReconstructionStruct struct : this.microCTReconstructionStructs){
                this.mrService.deleteTriples(null, null, null, struct.getDatasetURI());  //remove everything
            }
            for(MicroCTScanningStruct struct : this.microCTScanningStructs){
                this.mrService.deleteTriples(null, null, null, struct.getDatasetURI());  //remove everything
            }
            for(MorphometricsStruct struct : this.morphometricsStructs){
                this.mrService.deleteTriples(null, null, null, struct.getDatasetURI());  //remove everything
            }
            for(OccurrenceStruct struct : this.occurrenceStructs){
                this.mrService.deleteTriples(null, null, null, struct.getDatasetURI());  //remove everything
            }
            for(ScientificNamingStruct struct : this.scientificNameStructs){
                this.mrService.deleteTriples(null, null, null, struct.getDatasetURI());  //remove everything
            }
            for(SpecimenStruct struct : this.specimenStructs){
                this.mrService.deleteTriples(null, null, null, struct.getDatasetURI());  //remove everything
            }
            for(StatsStruct struct : this.statsStructs){
                this.mrService.deleteTriples(null, null, null, struct.getDatasetURI());  //remove everything
            }
            for(SynonymStruct struct : this.synonymStructs){
                this.mrService.deleteTriples(null, null, null, struct.getDatasetURI());  //remove everything
            }
            for(GensDatasetStruct struct : this.gensDatasetStructs){
                this.mrService.deleteTriples(null, null, null, struct.getDatasetURI());  //remove everything
            }
            for(GensSampleStruct struct : this.gensSampleStructs){
                this.mrService.deleteTriples(null, null, null, struct.getDatasetURI());  //remove everything
            }
            for(OccurrenceStatsTempStruct struct : this.occurrenceStatsTempStructs){
                this.mrService.deleteTriples(null, null, null, struct.getDatasetURI());  //remove everything
            }
            for(MicroCTSpecimenStruct struct : this.microCTSpecimenStructs){
                this.mrService.deleteTriples(null, null, null, struct.getDatasetURI());  //remove everything
            }
            for(OccurrenceStatsAbundanceStruct struct : this.occurrenceStatsAbundanceStructs){
                this.mrService.deleteTriples(null, null, null, struct.getDatasetURI());  //remove everything
            }
            for(SpecimenCollectionStruct struct : this.specimenCollectionStructs){
                this.mrService.deleteTriples(null, null, null, struct.getDatasetURI());  //remove everything
            }
            for(TaxonomyStruct struct : this.taxonomyStructs){
                this.mrService.deleteTriples(null, null, null, struct.getDatasetURI());  //remove everything
            }
            for(DirectoryStruct struct : this.directoryStructs){
                this.mrService.deleteTriples(null, null, null, struct.getDatasetURI());  //remove everything
            }
        }catch(QueryExecutionException ex){
            System.err.println(ex);
        }
        return this;
    }
    
    private List<CommonNameStruct> createCommonNameStructs(int total){
        List<CommonNameStruct> retList=new ArrayList<>();
        for(int i=1;i<=total;i++){
            CommonNameStruct struct=new CommonNameStruct().withDatasetURI("http://localhost/commonName/dataset"+i)
                                                          .withDatasetName("dataset "+i)
                                                          .withCommonName("common name "+i)
                                                          .withCommonNameURI("http://localhost/commonName"+i)
                                                          .withLanguage("language "+i)
                                                          .withLanguageURI("http://localhost/language"+i)
                                                          .withPlace("http://localhost/place"+i, "place "+i)
                                                          .withSpeciesName("species "+i)
                                                          .withSpeciesURI("http://localhost/species"+i);
            retList.add(struct);
        }
        return retList;
    }
    
    private List<EnvironmentalStruct> createEnvironmentalStructs(int total){
        List<EnvironmentalStruct> retList=new ArrayList<>();
        for(int i=1;i<=total;i++){
            EnvironmentalStruct struct=new EnvironmentalStruct().withDatasetURI("http://localhost/environmental/dataset"+i)
                                                                .withDatasetName("dataset "+i)
                                                                .withActor("http://localhost/actor"+i, "actor "+i)
                                                                .withDimensionName("dimension "+i)
                                                                .withDimensionTypeURI("http://localhost/dimensionType"+i)
                                                                .withDimensionURI("http://localhost/dimension"+i)
                                                                .withDimensionUnit("dimension unit "+i)
                                                                .withDimensionValue("dimension value "+i)
                                                                .withMeasurementEventURI("http://localhost/measurementEvent"+i)
                                                                .withMeasurementEvent("measurement event "+i)
                                                                .withPlaceName("place "+i)
                                                                .withPlaceURI("http://localhost/place"+i)
                                                                .withStationName("station name "+i)
                                                                .withStationURI("http://localhost/station"+i)
                                                                .withTimeSpan("timespan "+i);
            retList.add(struct);
        }
        return retList;
    }
    
    private List<IdentificationStruct> createIdentificationStructs(int total){
        List<IdentificationStruct> retList=new ArrayList<>();
        for(int i=1;i<=total;i++){
            IdentificationStruct struct=new IdentificationStruct().withDatasetURI("http://localhost/identification/dataset"+i)
                                                                .withDatasetTitle("dataset "+i)
                                                                .withActor("http://localhost/actor"+i, "actor "+i)
                                                                .withIdentificationEventURI("http://localhost/identificationEvent"+i)
                                                                .withIdentificationEvent("identification "+i)
                                                                .withIdentificationReferencesName("identification reference "+i)
                                                                .withIdentificationReferencesURI("http://localhost/identificationReference"+i)
                                                                .withIndividualURI("http://localhost/individual"+i)
                                                                .withIndividualLabel("individual "+i)
                                                                .withLocalityName("locality "+i)
                                                                .withLocalityURI("http://localhost/locality"+i)
                                                                .withSpeciesName("species "+i)
                                                                .withSpeciesURI("http://localhost/species"+i)
                                                                .withTimeSpan("timespan "+i);
            retList.add(struct);
        }
        return retList;
    }
    
    private List<MeasurementStruct> createMeasurementStructs(int total){
        List<MeasurementStruct> retList=new ArrayList<>();
        for(int i=1;i<=total;i++){
            MeasurementStruct struct=new MeasurementStruct().withDatasetURI("http://localhost/measurement/dataset"+i)
                                                                .withDatasetName("dataset "+i)
                                                                .withActor("http://localhost/actor"+i, "actor "+i)
                                                                .withDimensionName("dimension "+i)
                                                                .withDimensionType("http://localhost/dimensionType"+i)
                                                                .withDimensionURI("http://localhost/dimension"+i)
                                                                .withDimensionUnit("dimension unit "+i)
                                                                .withDimensionValue("dimension value "+i)
                                                                .withMeasurementEventURI("http://localhost/measurementEvent"+i)
                                                                .withSpecimenName("specimen "+i)
                                                                .withSpecimenURI("http://localhost/specimen"+i)
                                                                .withSpeciesName("species "+i)
                                                                .withSpeciesURI("http://localhost/species"+i)
                                                                .withTimeSpan("timespan "+i);
            retList.add(struct);
        }
        return retList;
    }
    
    private List<MicroCTPostProcessingStruct> createMicroCTPostProcessingStructs(int total){
        List<MicroCTPostProcessingStruct> retList=new ArrayList<>();
        for(int i=1;i<=total;i++){
            MicroCTPostProcessingStruct struct=new MicroCTPostProcessingStruct().withDatasetURI("http://localhost/microCTPostProcessing/dataset"+i)
                                                                .withDatasetTitle("dataset "+i)
                                                                .withActorName("actor "+i)
                                                                .withActorURI("http://localhost/actor"+i)
                                                                .withDescription("description "+i)
                                                                .withInput("http://localhost/reconstructionproduct"+i, "reconstructionproduct "+i)
                                                                .withPostProcessingURI("http://localhost/postprocessing"+i)
                                                                .withPostProcessing("post processing "+i)
                                                                .withProduct("http://localhost/finalproduct"+i, "finalproduct "+i);            
            retList.add(struct);
        }
        return retList;
    }
    
    private List<MicroCTReconstructionStruct> createMicroCTReconstructionStructs(int total){
        List<MicroCTReconstructionStruct> retList=new ArrayList<>();
        for(int i=1;i<=total;i++){
            MicroCTReconstructionStruct struct=new MicroCTReconstructionStruct().withDatasetURI("http://localhost/microCTReconstruction/dataset"+i)
                                                                .withDatasetTitle("dataset "+i)
                                                                .withActorName("actor "+i)
                                                                .withActorURI("http://localhost/actor"+i)
                                                                .withDescription("description "+i)
                                                                .withInput("http://localhost/scaninput"+i, "scaninput "+i)
                                                                .withReconstructionURI("http://localhost/reconstruction"+i)
                                                                .withReconstruction("reconstruction "+i)
                                                                .withTimespan("timespan "+i)
                                                                .withProduct("http://localhost/reconstructionproduct"+i, "reconstructionproduct "+i);
            retList.add(struct);
        }
        return retList;
    }
    
    private List<MicroCTScanningStruct> createMicroCTScanningStructs(int total){
        List<MicroCTScanningStruct> retList=new ArrayList<>();
        for(int i=1;i<=total;i++){
            MicroCTScanningStruct struct=new MicroCTScanningStruct().withDatasetURI("http://localhost/microCTScanning/dataset"+i)
                                                                .withDatasetName("dataset "+i)
                                                                .withActorName("actor "+i)
                                                                .withActorURI("http://localhost/actor"+i)
                                                                .withContrastMethod("contrast "+i)
                                                                .withDescription("description "+i)
                                                                .withDeviceName("device "+i)
                                                                .withDeviceType("device type "+i)
                                                                .withDeviceURI("http://localhost/device"+i)
                                                                .withEquipmentURI("http://localhost/equipment"+i)
                                                                .withEquipment("equipment "+i)
                                                                .withExposureTime("exposure time "+i)
                                                                .withFilter("filter "+i)
                                                                .withTimespan("timespan "+i)
                                                                .withMethodName("method "+i)
                                                                .withMethodURI("http://localhost/method"+i)
                                                                .withScanningURI("http://localhost/scanning"+i)
                                                                .withScanning("scanning "+i)
                                                                .withScanningLabel("scanning label "+i)
                                                                .withSpecimenName("specimen "+i)
                                                                .withSpecimenURI("http://localhost/specimen"+i)
                                                                .withVoltage("voltage "+i)
                                                                .withZoom("zoom "+i)
                                                                .withProduct("http://localhost/scaninput"+i, "scaninput "+i);
            retList.add(struct);
        }
        return retList;
    }
    
    private List<MorphometricsStruct> createMorphometricsStructs(int total){
        List<MorphometricsStruct> retList=new ArrayList<>();
        for(int i=1;i<=total;i++){
            MorphometricsStruct struct=new MorphometricsStruct().withDatasetURI("http://localhost/morphometrics/dataset"+i)
                                                                .withDatasetName("dataset "+i)
                                                                .withActor("http://localhost/actor"+i, "actor "+i)
                                                                .withAttributeAssignmentEventURI("http://localhost/attributeAssignmentEvent"+i)
                                                                .withAttributeAssignmentEvent("attribute assignment event "+i)
                                                                .withDimensionName("dimension "+i)
                                                                .withDimensionType("http://localhost/dimensionType"+i)
                                                                .withDimensionURI("http://localhost/dimension"+i)
                                                                .withDimensionUnit("dimension unit "+i)
                                                                .withDimensionValue("dimension value "+i)
                                                                .withPublicationName("publication "+i)
                                                                .withPublicationURI("http://localhost/publication"+i)
                                                                .withSpeciesName("species "+i)
                                                                .withSpeciesURI("http://localhost/species"+i)
                                                                .withTimeSpan("timespan "+i)
                                                                .withDescription("description "+i);                                     
            retList.add(struct);
        }
        return retList;
    }
    
    private List<OccurrenceStruct> createOccurrenceStructs(int total){
        List<OccurrenceStruct> retList=new ArrayList<>();
        for(int i=1;i<=total;i++){
            OccurrenceStruct struct=new OccurrenceStruct().withDatasetURI("http://localhost/occurrence/dataset"+i)
                                                                .withDatasetTitle("dataset "+i)
                                                                .withActor("http://localhost/actor"+i, "actor "+i)
                                                                .withBibliographicCitation("bibliographic citation "+i)
                                                                .withBibliographicCitationURI("http://localhost/bilbiographicCitation"+i)
                                                                .withCoordinates("coordinates "+i)
                                                                .withCountryName("country "+i)
                                                                .withCountryURI("http://localhost/country"+i)
                                                                .withEcosystemName("ecosystem "+i)
                                                                .withEcosystemURI("http://localhost/ecosystem"+i)
                                                                .withEquipmentTypeName("equipment type "+i)
                                                                .withEquipmentTypeURI("http://localhost/equipmentType"+i)
                                                                .withHabitatName("habitat "+i)
                                                                .withHabitatURI("http://localhost/habitat"+i)
                                                                .withIndividualURI("http://localhost/individual"+i)
                                                                .withIndividualLabel("individual "+i)
                                                                .withLatitude("latitude "+i)
                                                                .withLocalityName("locality "+i)
                                                                .withLocalityURI("http://localhost/locality"+i)
                                                                .withLongitude("longitude "+i)
                                                                .withMaximumDepth("maximum depth "+i)
                                                                .withMinimumDepth("minimum depth "+i)
                                                                .withOccurrenceEventURI("http://localhost/occurrenceEvent"+i)
                                                                .withOccurrenceEvent("occurrence event "+i)
                                                                .withSamplingProtocol("sampling protocol "+i)
                                                                .withSamplingProtocolURI("http://localhost/samplingProtocol"+i)
                                                                .withStationNotes("station notes "+i)
                                                                .withStationURI("http://localhost/station"+i)
                                                                .withWaterAreaName("water area "+i)
                                                                .withWaterAreaURI("http://localhost/waterarea"+i)
                                                                .withSpeciesName("species "+i)
                                                                .withSpeciesURI("http://localhost/species"+i)
                                                                .withTimeSpan("timespan "+i)
                                                                .withDescription("description "+i);                                     
            retList.add(struct);
        }
        return retList;
    }
    
    private List<ScientificNamingStruct> createScientificNameStructs(int total){
        List<ScientificNamingStruct> retList=new ArrayList<>();
        for(int i=1;i<=total;i++){
            ScientificNamingStruct struct=new ScientificNamingStruct().withDatasetURI("http://localhost/scientificName/dataset"+i)
                                                                .withDatasetName("dataset "+i)
                                                                .withActor("http://localhost/actor"+i, "actor "+i)
                                                                .withAppellation("appellation "+i)
                                                                .withAppellationURI("http://localhost/appellation"+i)
                                                                .withNomenclaturalCodeName("nomenclatural code name "+i)
                                                                .withNomenclaturalCodeURI("http://localhost/nomenclaturalcodename"+i)
                                                                .withScientificNameAssignmentEventURI("http://localhost/scientificnameassignment"+i)
                                                                .withScientificNameAssignmentEvent("scientificnameassignment "+i)
                                                                .withSpeciesName("species "+i)
                                                                .withSpeciesURI("http://localhost/species"+i)
                                                                .withTimeSpan("timespan "+i);
            retList.add(struct);
        }
        return retList;
    }
    
    private List<SpecimenStruct> createSpecimenStructs(int total){
        List<SpecimenStruct> retList=new ArrayList<>();
        for(int i=1;i<=total;i++){
            SpecimenStruct struct=new SpecimenStruct().withDatasetURI("http://localhost/specimen/dataset"+i)
                                                                .withDatasetName("dataset "+i)
                                                                .withActor("http://localhost/actor"+i, "actor "+i)
                                                                .withCollectionName("collection "+i)
                                                                .withCollectionURI("http://localhost/collection"+i)
                                                                .withIndividualURI("http://localhost/individual"+i)
                                                                .withMethodName("method name "+i)
                                                                .withMethodURI("http://localhost/method"+i)
                                                                .withSpecimenName("specimen "+i)
                                                                .withSpecimenURI("http://localhost/specimen"+i)
                                                                .withTransformationEventURI("http://localhost/transformationEvent"+i)
                                                                .withTransformationEvent("transformation "+i)
                                                                .withSpeciesName("species "+i)
                                                                .withSpeciesURI("http://localhost/species"+i)
                                                                .withTimeSpan("timespan "+i);
            retList.add(struct);
        }
        return retList;
    }
    
    private List<StatsStruct> createStatsStructs(int total){
        List<StatsStruct> retList=new ArrayList<>();
        for(int i=1;i<=total;i++){
            StatsStruct struct=new StatsStruct().withDatasetURI("http://localhost/stats/dataset"+i)
                                                                .withDatasetName("dataset "+i)
                                                                .withActor("http://localhost/actor"+i, "actor "+i)
                                                                .withDataEvaluationURI("http://localhost/dataEvaluation"+i)
                                                                .withDataEvaluation("dataEvaluation "+i)
                                                                .withDescription("description "+i)
                                                                .withDimensionName("dimension "+i)
                                                                .withDimensionType("http://localhost/dimensionType"+i)
                                                                .withDimensionURI("http://localhost/dimension"+i)
                                                                .withDimensionUnit("dimension unit "+i)
                                                                .withDimensionValue("dimension value "+i)
                                                                .withPublicationName("publication "+i)
                                                                .withPublicationURI("http://localhost/publication"+i)
                                                                .withSpecimenName("specimen "+i)
                                                                .withSpecimenURI("http://localhost/specimen"+i)
                                                                .withSpeciesName("species "+i)
                                                                .withSpeciesURI("http://localhost/species"+i)
                                                                .withTimeSpan("timespan "+i);
            retList.add(struct);
        }
        return retList;
    }
    
    private List<SynonymStruct> createSynonymStructs(int total){
        List<SynonymStruct> retList=new ArrayList<>();
        for(int i=1;i<=total;i++){
            SynonymStruct struct=new SynonymStruct().withDatasetURI("http://localhost/synonym/dataset"+i)
                                                                .withDatasetName("dataset "+i)
                                                                .withAppellation("appellation "+i)
                                                                .withAppellationURI("http://localhost/appellation"+i)
                                                                .withSpeciesName("species "+i)
                                                                .withSpeciesURI("http://localhost/species"+i)
                                                                .withSynonym("http://localhost/synonym"+i, "synonym "+i);
            retList.add(struct);
        }
        return retList;
    }
    
    private List<GensDatasetStruct> createGensDatasetStructs(int total){
        List<GensDatasetStruct> retList=new ArrayList<>();
        for(int i=1;i<=total;i++){
            GensDatasetStruct struct=new GensDatasetStruct().withDatasetURI("http://localhost/gensDataset/dataset"+i)
                                                                .withDatasetTitle("dataset "+i)
                                                                .withDescription("description "+i)
                                                                .withDevice("device "+i)
                                                                .withDeviceType("device type "+i)
                                                                .withDeviceTypeURI("http://localhost/deviceType"+i)
                                                                .withDeviceURI("http://localhost/device"+i)
                                                                .withEcosystemName("ecosystem "+i)
                                                                .withEcosystemURI("http://localhost/ecosystem"+i)
                                                                .withHabitatName("habitat "+i)
                                                                .withHabitatURI("http://localhost/habitat"+i)
                                                                .withProducedFile("produced file "+i)
                                                                .withProducedFileURI("http://localhost/producedFile"+i)
                                                                .withSampleName("sample "+i)
                                                                .withSampleURI("http://localhost/sample"+i)
                                                                .withSequencingEventURI("http://localhost/sequencingEvent"+i)
                                                                .withSpeciesName("species "+i)
                                                                .withSpeciesURI("http://localhost/species"+i)
                                                                .withTimeSpan("timespan "+i);
            retList.add(struct);
        }
        return retList;
    }
    
    private List<GensSampleStruct> createGensSampleStructs(int total){
        List<GensSampleStruct> retList=new ArrayList<>();
        for(int i=1;i<=total;i++){
            GensSampleStruct struct=new GensSampleStruct().withDatasetURI("http://localhost/gensSample/dataset"+i)
                                                                .withDatasetName("dataset "+i)
                                                                .withDescription("description "+i)
                                                                .withDeviceName("device "+i)
                                                                .withDeviceType("device type "+i)
                                                                .withDeviceURI("http://localhost/device"+i)
                                                                .withDimensionName("dimension "+i)
                                                                .withDimensionType("http://localhost/dimensionType"+i)
                                                                .withDimensionURI("http://localhost/dimension"+i)
                                                                .withDimensionUnit("dimension unit "+i)
                                                                .withDimensionValue("dimension value "+i)
                                                                .withPlaceName("place "+i)
                                                                .withPlaceURI("http://localhost/place"+i)
                                                                .withPostProcessingURI("http://localhost/postProcessing"+i)
                                                                .withPostProductName("post product name "+i)
                                                                .withPostProductURI("http://localhost/postProduct"+i)
                                                                .withProductName("product name "+i)
                                                                .withProductURI("http://localhost/product"+i)
                                                                .withSequencingURI("http://localhost/sequencing"+i)
                                                                .withSampleName("sample "+i)
                                                                .withSampleURI("http://localhost/sample"+i)
                                                                .withSpeciesName("species "+i)
                                                                .withSpeciesURI("http://localhost/species"+i)
                                                                .withTransformationURI("http://localhost/transformation"+i)
                                                                .withTransformedSampleName("transformed sample "+i)
                                                                .withTransformedSampleURI("http://localhost/transformedSample"+i);
            retList.add(struct);
        }
        return retList;
    }
    
    private List<OccurrenceStatsTempStruct> createOccurrenceStatsTempStructs(int total){
        List<OccurrenceStatsTempStruct> retList=new ArrayList<>();
        for(int i=1;i<=total;i++){
            OccurrenceStatsTempStruct struct=new OccurrenceStatsTempStruct().withDatasetURI("http://localhost/occurrenceStatsTemp/dataset"+i)
                                                                .withDatasetTitle("dataset "+i)
                                                                .withDescription("description "+i)
                                                                .withBibliographicCitation("bibliographic citation "+i)
                                                                .withBibliographicCitationURI("http://localhost/bibliographicCitation"+i)
                                                                .withCoordinates("coordinates "+i)
                                                                .withCountryName("country "+i)
                                                                .withCountryURI("http://localhost/country"+i)
                                                                .withEcosystemName("ecosystem "+i)
                                                                .withEcosystemURI("http://localhost/ecosystem"+i)
                                                                .withEquipmentTypeName("equipment type "+i)
                                                                .withEquipmentTypeURI("http://localhost/equipmentType"+i)
                                                                .withGeodeticDatum("geodetic datum "+i)
                                                                .withHabitatName("habitat "+i)
                                                                .withHabitatURI("http://localhost/habitat"+i)
                                                                .withLatitude("latitude "+i)
                                                                .withLocalityName("locality "+i)
                                                                .withLocalityURI("http://localhost/locality"+i)
                                                                .withLongitude("longitude "+i)
                                                                .withMaximumDepth("maximum depth "+i)
                                                                .withMinimumDepth("minimum depth "+i)
                                                                .withNumberOfParts("number of parts "+i)
                                                                .withOccurrenceEventURI("http://localhost/occurrenceEvent"+i)
                                                                .withPhysicalObjectURI("http://localhost/physicalObject"+i)
                                                                .withSamplingProtocolName("sampling protocol "+i)
                                                                .withSamplingProtocolURI("http://localhost/samplingProtocol"+i)
                                                                .withStationNotes("station notes "+i)
                                                                .withStationURI("http://localhost/station"+i)
                                                                .withSpeciesName("species "+i)
                                                                .withSpeciesURI("http://localhost/species"+i)
                                                                .withTemporaryAggregate("temporary aggregate "+i)
                                                                .withTemporaryAggregateURI("http://localhost/temporaryAggregate"+i)
                                                                .withTimeSpan("timespan "+i)
                                                                .withWaterAreaName("water area "+i)
                                                                .withWaterAreaURI("http://localhost/waterArea"+i);
            retList.add(struct);
        }
        return retList;
    }
    
    private List<MicroCTSpecimenStruct> createMicroCTSpecimenStructs(int total){
        List<MicroCTSpecimenStruct> retList=new ArrayList<>();
        for(int i=1;i<=total;i++){
            MicroCTSpecimenStruct struct=new MicroCTSpecimenStruct().withDatasetURI("http://localhost/microCTSpecimen/dataset"+i)
                                                                .withDatasetName("dataset "+i)
                                                                .withDescription("description "+i)
                                                                .withDimensionName("dimension "+i)
                                                                .withDimensionTypeURI("http://localhost/dimensionType"+i)
                                                                .withDimensionURI("http://localhost/dimension"+i)
                                                                .withDimensionUnit("dimension unit "+i)
                                                                .withDimensionValue("dimension value "+i)
                                                                .withCollectionName("collection "+i)
                                                                .withCollectionURI("http://localhost/collection"+i)
                                                                .withInstitutionName("institution "+i)
                                                                .withInstitutionURI("http://localhost/institution"+i)
                                                                .withProviderName("provider "+i)
                                                                .withProviderURI("http://localhost/provider"+i)
                                                                .withSpeciesName("species "+i)
                                                                .withSpeciesURI("http://localhost/species"+i)
                                                                .withSpecimenName("specimen "+i)
                                                                .withSpecimenType("specimen type "+i)
                                                                .withSpecimenURI("http://localhost/specimen"+i);
            retList.add(struct);
        }
        return retList;
    }
    
    private List<OccurrenceStatsAbundanceStruct> createOccurrenceStatsAbundanceStructs(int total){
        List<OccurrenceStatsAbundanceStruct> retList=new ArrayList<>();
        for(int i=1;i<=total;i++){
            OccurrenceStatsAbundanceStruct struct=new OccurrenceStatsAbundanceStruct().withDatasetURI("http://localhost/occurrenceStatsAbundance/dataset"+i)
                                                                .withDatasetName("dataset "+i)
                                                                .withDimensionName("dimension "+i)
                                                                .withDimensionType("http://localhost/dimensionType"+i)
                                                                .withDimensionURI("http://localhost/dimension"+i)
                                                                .withDimensionUnit("dimension unit "+i)
                                                                .withDimensionValue("dimension value "+i)
                                                                .withSpeciesName("species "+i)
                                                                .withSpeciesURI("http://localhost/species"+i)
                                                                .withTemporaryAggregateURI("http://localhost/temporaryAggregate"+i);
            retList.add(struct);
        }
        return retList;
    }
    
    private List<SpecimenCollectionStruct> createSpecimenCollectionStructs(int total){
        List<SpecimenCollectionStruct> retList=new ArrayList<>();
        for(int i=1;i<=total;i++){
            SpecimenCollectionStruct struct=new SpecimenCollectionStruct().withDatasetURI("http://localhost/specimenCollection/dataset"+i)
                                                                .withDatasetName("dataset "+i)
                                                                .withCollectionName("collection "+i)
                                                                .withCollectionURI("http://localhost/collection"+i)
                                                                .withContactPoint("contact point "+i)
                                                                .withCreationEventURI("http://localhost/creationEvent"+i)
                                                                .withCreatorName("creator "+i)
                                                                .withCreatorURI("http://localhost/creator"+i)
                                                                .withCuratorName("curator "+i)
                                                                .withCuratorURI("http://localhost/curator"+i)
                                                                .withKeeperName("keeper "+i)
                                                                .withKeeperURI("http://localhost/keeper"+i)
                                                                .withNote("note "+i)
                                                                .withOwnerName("owner "+i)
                                                                .withOwnerURI("http://localhost/owner"+i)
                                                                .withTimespan("timespan "+i);
            retList.add(struct);
        }
        return retList;
    }
    
    private List<TaxonomyStruct> createTaxonomyStructs(int total){
        List<TaxonomyStruct> retList=new ArrayList<>();
        for(int i=1;i<=total;i++){
            TaxonomyStruct struct=new TaxonomyStruct().withDatasetURI("http://localhost/taxonomy/dataset"+i)
                                                                .withDatasetName("dataset "+i)
                                                                .withClassName("clas "+i)
                                                                .withClassURI("http://localhost/class"+i)
                                                                .withFamilyName("family "+i)
                                                                .withFamilyURI("http://localhost/family"+i)
                                                                .withGenusName("genus "+i)
                                                                .withGenusURI("http://localhost/genus"+i)
                                                                .withKingdomName("kingdom "+i)
                                                                .withKingdomURI("http://localhost/kingdom"+i)
                                                                .withOrderName("order "+i)
                                                                .withOrderURI("http://localhost/order"+i)
                                                                .withPhylumName("phylum "+i)
                                                                .withPhylumURI("http://localhost/phylum"+i)
                                                                .withSpeciesName("species "+i)
                                                                .withSpeciesURI("http://localhost/species"+i);
            retList.add(struct);
        }
        return retList;
    }
    
    private List<DirectoryStruct> createDirectoryStructs(int total){
        List<DirectoryStruct> retList=new ArrayList<>();
        for(int i=1;i<=total;i++){
            DirectoryStruct struct=new DirectoryStruct().withDatasetURI("http://localhost/directory/dataset"+i)
                                                                .withDatasetName("dataset "+i)
                                                                .withAccessMethod("access method "+i)
                                                                .withAccessMethodURI("http://localhost/accessMethod"+i)
                                                                .withAccessRights("access rights "+i)
                                                                .withAccessRightsURI("http://localhost/accessRights"+i)
                                                                .withAttributeAssignmentEventURI("http://localhost/attributeAssignmentEvent"+i)
                                                                .withContactPoint("contact point "+i)
                                                                .withContributor("http://localhost/contributor"+i, "contributor "+i)
                                                                .withCreationDate("creation date "+i)
                                                                .withCreationEventURI("http://localhost/creationEvent"+i)
                                                                .withCreatorName("creator name "+i)
                                                                .withCreatorURI("http://localhost/creator"+i)
                                                                .withCuratorName("curator "+i)
                                                                .withCuratorURI("http://localhost/curator"+i)
                                                                .withDatasetType("dataset type "+i)
                                                                .withDatasetID("dataset id "+i)
                                                                .withDescription("description "+i)
                                                                .withEmbargoPeriod("embargo period "+i)
                                                                .withEmbargoState("embargo state "+i)
                                                                .withImageTitle("image title "+i)
                                                                .withImageURI("http://localhost/image"+i)
                                                                .withKeeperName("keeper "+i)
                                                                .withKeeperURI("http://localhost/keeper"+i)
                                                                .withLocationURL("http://localhost/location"+i)
                                                                .withOwnerName("owner "+i)
                                                                .withOwnerURI("http://localhost/owner"+i)
                                                                .withParentDatasetName("parent dataset "+i)
                                                                .withParentDatasetURI("http://localhost/parentDataset"+i)
                                                                .withPublicationDate("publication date "+i)
                                                                .withPublicationEventURI("http://localhost/publicationEvent"+i)
                                                                .withPublisherName("publisher name "+i)
                                                                .withPublisherURI("http://localhost/publisher"+i)
                                                                .withRightsHolderName("rights holder name "+i)
                                                                .withRightsHolderURI("http://localhost/rightsHolder"+i);
            retList.add(struct);
        }
        return retList;
    }
    
    public MetadataCatalogUsageExample addStructs(){
        this.printSeparator();
        return this.
             addCommonNameStructs().
             addEnvironmentalStructs().
             addIdentificationStructs().
             addMeasurementStructs().
             addMicroCTPostProcessingStructs().
             addMicroCTReconstructionStructs().
             addMicroCTScanningStructs().
             addMorphometricsStructs().
             addOccurrenceStructs().
             addScientificNameStructs().
             addSpecimenStructs().
             addStatsStructs().
             addSynonymStructs().
//             addGensDatasetStructs().
//             addGensSampleStructs().
//             addOccurrenceStatsTempStructs().
             addMicroCTSpecimenStructs();
//             addOccurrenceStatsAbundanceStructs().
//             addSpecimenCollectionStructs().
//             addTaxonomyStructs().
//             addDirectoryStructs();
    }
    

    
    public MetadataCatalogUsageExample addCommonNameStructs(){
        System.out.print("Adding CommonNameStructs...");
        int cnt=0;
        for(CommonNameStruct struct : this.commonNameStructs){
            try{
                this.mrService.insertStruct(struct, "http://lifewatchgreece.com");
                cnt+=1;
            }catch(QueryExecutionException | URIValidationException ex){
                ex.printStackTrace();
            }
        }
        System.out.println(" Added "+cnt);
        return this;
    }
    
    public MetadataCatalogUsageExample addEnvironmentalStructs(){
        System.out.print("Adding EnvironmentalStructs...");
        int cnt=0;
        for(EnvironmentalStruct struct : this.environmentalStructs){
            try{
                this.mrService.insertStruct(struct, "http://lifewatchgreece.com");
                cnt+=1;
            }catch(QueryExecutionException | URIValidationException ex){
                ex.printStackTrace();
            }
        }
        System.out.println(" Added "+cnt);
        return this;
    }
    
    public MetadataCatalogUsageExample addIdentificationStructs(){
        System.out.print("Adding IdentificationStructs...");
        int cnt=0;
        for(IdentificationStruct struct : this.identificationStructs){
            try{
                this.mrService.insertStruct(struct, "http://lifewatchgreece.com");
                cnt+=1;
            }catch(QueryExecutionException | URIValidationException ex){
                ex.printStackTrace();
            }
        }
        System.out.println(" Added "+cnt);
        return this;
    }
    
    public MetadataCatalogUsageExample addMeasurementStructs(){
        System.out.print("Adding MeasurementStructs...");
        int cnt=0;
        for(MeasurementStruct struct : this.measurementStructs){
            try{
                this.mrService.insertStruct(struct, "http://lifewatchgreece.com");
                cnt+=1;
            }catch(QueryExecutionException | URIValidationException ex){
                ex.printStackTrace();
            }
        }
        System.out.println(" Added "+cnt);
        return this;
    }
    
    public MetadataCatalogUsageExample addMicroCTPostProcessingStructs(){
        System.out.print("Adding MicroCTPostProcessingStructs...");
        int cnt=0;
        for(MicroCTPostProcessingStruct struct : this.microCTPostProcessingStructs){
            try{
                this.mrService.insertStruct(struct, "http://lifewatchgreece.com");
                cnt+=1;
            }catch(QueryExecutionException | URIValidationException ex){
                ex.printStackTrace();
            }
        }
        System.out.println(" Added "+cnt);
        return this;
    }
    
    public MetadataCatalogUsageExample addMicroCTReconstructionStructs(){
        System.out.print("Adding MicroCTReconstructionStructs...");
        int cnt=0;
        for(MicroCTReconstructionStruct struct : this.microCTReconstructionStructs){
            try{
                this.mrService.insertStruct(struct, "http://lifewatchgreece.com");
                cnt+=1;
            }catch(QueryExecutionException | URIValidationException ex){
                ex.printStackTrace();
            }
        }
        System.out.println(" Added "+cnt);
        return this;
    }
    
    public MetadataCatalogUsageExample addMicroCTScanningStructs(){
        System.out.print("Adding MicroCTScanningStructs...");
        int cnt=0;
        for(MicroCTScanningStruct struct : this.microCTScanningStructs){
            try{
                this.mrService.insertStruct(struct, "http://lifewatchgreece.com");
                cnt+=1;
            }catch(QueryExecutionException | URIValidationException ex){
                ex.printStackTrace();
            }
        }
        System.out.println(" Added "+cnt);
        return this;
    }
    
    public MetadataCatalogUsageExample addMorphometricsStructs(){
        System.out.print("Adding MorphometricsStructs...");
        int cnt=0;
        for(MorphometricsStruct struct : this.morphometricsStructs){
            try{
                this.mrService.insertStruct(struct, "http://lifewatchgreece.com");
                cnt+=1;
            }catch(QueryExecutionException | URIValidationException ex){
                ex.printStackTrace();
            }
        }
        System.out.println(" Added "+cnt);
        return this;
    }
    
    public MetadataCatalogUsageExample addOccurrenceStructs(){
        System.out.print("Adding OccurrenceStructs...");
        int cnt=0;
        for(OccurrenceStruct struct : this.occurrenceStructs){
            try{
                this.mrService.insertStruct(struct, "http://lifewatchgreece.com");
                cnt+=1;
            }catch(QueryExecutionException | URIValidationException ex){
                ex.printStackTrace();
            }
        }
        System.out.println(" Added "+cnt);
        return this;
    }
    
    public MetadataCatalogUsageExample addScientificNameStructs(){
        System.out.print("Adding ScientificNameStructs...");
        int cnt=0;
        for(ScientificNamingStruct struct : this.scientificNameStructs){
            try{
                this.mrService.insertStruct(struct, "http://lifewatchgreece.com");
                cnt+=1;
            }catch(QueryExecutionException | URIValidationException ex){
                ex.printStackTrace();
            }
        }
        System.out.println(" Added "+cnt);
        return this;
    }
    
    public MetadataCatalogUsageExample addSpecimenStructs(){
        System.out.print("Adding SpecimenStructs...");
        int cnt=0;
        for(SpecimenStruct struct : this.specimenStructs){
            try{
                this.mrService.insertStruct(struct, "http://lifewatchgreece.com");
                cnt+=1;
            }catch(QueryExecutionException | URIValidationException ex){
                ex.printStackTrace();
            }
        }
        System.out.println(" Added "+cnt);
        return this;
    }
    
    public MetadataCatalogUsageExample addStatsStructs(){
        System.out.print("Adding StatsStructs...");
        int cnt=0;
        for(StatsStruct struct : this.statsStructs){
            try{
                this.mrService.insertStruct(struct, "http://lifewatchgreece.com");
                cnt+=1;
            }catch(QueryExecutionException | URIValidationException ex){
                ex.printStackTrace();
            }
        }
        System.out.println(" Added "+cnt);
        return this;
    }
    
    public MetadataCatalogUsageExample addSynonymStructs(){
        System.out.print("Adding SynonymStructs...");
        int cnt=0;
        for(SynonymStruct struct : this.synonymStructs){
            try{
                this.mrService.insertStruct(struct, "http://lifewatchgreece.com");
                cnt+=1;
            }catch(QueryExecutionException | URIValidationException ex){
                ex.printStackTrace();
            }
        }
        System.out.println(" Added "+cnt);
        return this;
    }
    
    public MetadataCatalogUsageExample addGensDatasetStructs(){
        System.out.print("Adding GensDatasetStructs...");
        int cnt=0;
        for(GensDatasetStruct struct : this.gensDatasetStructs){
            try{
                this.mrService.insertStruct(struct, "http://lifewatchgreece.com");
                cnt+=1;
            }catch(QueryExecutionException | URIValidationException ex){
                ex.printStackTrace();
            }
        }
        System.out.println(" Added "+cnt);
        return this;
    }
    
    public MetadataCatalogUsageExample addGensSampleStructs(){
        System.out.print("Adding GensSampleStructs...");
        int cnt=0;
        for(GensSampleStruct struct : this.gensSampleStructs){
            try{
                this.mrService.insertStruct(struct, "http://lifewatchgreece.com");
                cnt+=1;
            }catch(QueryExecutionException | URIValidationException ex){
                ex.printStackTrace();
            }
        }
        System.out.println(" Added "+cnt);
        return this;
    }
    
    public MetadataCatalogUsageExample addOccurrenceStatsTempStructs(){
        System.out.print("Adding OccurrenceStatsTempStructs...");
        int cnt=0;
        for(OccurrenceStatsTempStruct struct : this.occurrenceStatsTempStructs){
            try{
                this.mrService.insertStruct(struct, "http://lifewatchgreece.com");
                cnt+=1;
            }catch(QueryExecutionException | URIValidationException ex){
                ex.printStackTrace();
            }
        }
        System.out.println(" Added "+cnt);
        return this;
    }
    
    public MetadataCatalogUsageExample addMicroCTSpecimenStructs(){
        System.out.print("Adding MicroCTSpecimenStructs...");
        int cnt=0;
        for(MicroCTSpecimenStruct struct : this.microCTSpecimenStructs){
            try{
                this.mrService.insertStruct(struct, "http://lifewatchgreece.com");
                cnt+=1;
            }catch(QueryExecutionException | URIValidationException ex){
                ex.printStackTrace();
            }
        }
        System.out.println(" Added "+cnt);
        return this;
    }
    
    public MetadataCatalogUsageExample addOccurrenceStatsAbundanceStructs(){
        System.out.print("Adding OccurrenceStatsAbundanceStructs...");
        int cnt=0;
        for(OccurrenceStatsAbundanceStruct struct : this.occurrenceStatsAbundanceStructs){
            try{
                this.mrService.insertStruct(struct, "http://lifewatchgreece.com");
                cnt+=1;
            }catch(QueryExecutionException | URIValidationException ex){
                ex.printStackTrace();
            }
        }
        System.out.println(" Added "+cnt);
        return this;
    }
    
    public MetadataCatalogUsageExample addSpecimenCollectionStructs(){
        System.out.print("Adding OccurrenceStatsAbundanceStructs...");
        int cnt=0;
        for(SpecimenCollectionStruct struct : this.specimenCollectionStructs){
            try{
                this.mrService.insertStruct(struct, "http://lifewatchgreece.com");
                cnt+=1;
            }catch(QueryExecutionException | URIValidationException ex){
                ex.printStackTrace();
            }
        }
        System.out.println(" Added "+cnt);
        return this;
    }
    
    public MetadataCatalogUsageExample addTaxonomyStructs(){
        System.out.print("Adding TaxonomyStructs...");
        int cnt=0;
        for(TaxonomyStruct struct : this.taxonomyStructs){
            try{
                this.mrService.insertStruct(struct, "http://lifewatchgreece.com");
                cnt+=1;
            }catch(QueryExecutionException | URIValidationException ex){
                ex.printStackTrace();
            }
        }
        System.out.println(" Added "+cnt);
        return this;
    }
    
    public MetadataCatalogUsageExample addDirectoryStructs(){
        System.out.print("Adding DirectoryStructs...");
        int cnt=0;
        for(DirectoryStruct struct : this.directoryStructs){
            try{
                this.mrService.insertStruct(struct, "http://lifewatchgreece.com");
                cnt+=1;
            }catch(QueryExecutionException | URIValidationException ex){
                ex.printStackTrace();
            }
        }
        System.out.println(" Added "+cnt);
        return this;
    }
    
    public MetadataCatalogUsageExample searchCommonNames(){
        this.printSeparator();
        System.out.println("Searching for CommonName structs. ");
        try{
            List<CommonNameStruct> results=this.mrService.searchCommonName("species", "common name","place", "language", "","http://lifewatchgreece.com");
            for(CommonNameStruct struct : results){
                System.out.println(struct.getCommonName());
            }
        }catch(QueryExecutionException ex){
            ex.printStackTrace();
        }
        return this;
    }
    
    public MetadataCatalogUsageExample searchMeasurement(){
        this.printSeparator();
        System.out.println("Searching for Measurement structs. ");
        try{
            List<MeasurementStruct> results=this.mrService.searchMeasurement("specimen", "species","dimension","", "http://lifewatchgreece.com");
            for(MeasurementStruct struct : results)
                System.out.println(struct.getMeasurementEventURI());
            
        }catch(QueryExecutionException ex){
            ex.printStackTrace();
        }
        return this;
    }
    
    public MetadataCatalogUsageExample searchOccurence(){
        this.printSeparator();
        System.out.println("Searching for Occurrence structs. ");
        try{
            List<OccurrenceStruct> results=this.mrService.searchOccurrence("species", "locality", "timespan","", "http://lifewatchgreece.com");
            for(OccurrenceStruct struct : results)
                System.out.println(struct);
            
        }catch(QueryExecutionException ex){
            ex.printStackTrace();
        }
        return this;
    }
    
    public MetadataCatalogUsageExample searchEnvironmental(){
        this.printSeparator();
        System.out.println("Searching for Environmental structs. ");
        try{
            List<EnvironmentalStruct> results=this.mrService.searchEnvironmental("dimension", "place","", "http://lifewatchgreece.com");
            for(EnvironmentalStruct struct : results){
                System.out.println(struct.getPlaceName());
            }
        }catch(QueryExecutionException ex){
            ex.printStackTrace();
        }
        return this;
    }
    
    public MetadataCatalogUsageExample searchIdentification(){
        this.printSeparator();
        System.out.println("Searching for Identification structs. ");
        try{
            List<IdentificationStruct> results=this.mrService.searchIdentification("species", "timespan", "actor", "locality", "individual","", "http://lifewatchgreece.com");
            for(IdentificationStruct struct : results){
                System.out.println(struct.getIdentificationEventURI());
            }
        }catch(QueryExecutionException ex){
            ex.printStackTrace();
        }
        return this;
    }
    
    public MetadataCatalogUsageExample searchMicroCTPostProcessingStructs(){
        this.printSeparator();
        System.out.println("Searching for MicroCTPostProcessingStructs structs. ");
        try{
            List<MicroCTPostProcessingStruct> results=this.mrService.searchMicroCTPostProcessing("species", "specimen", "reconstruction", "","http://lifewatchgreece.com");
            for(MicroCTPostProcessingStruct struct : results){
                System.out.println(struct.getPostProcessingURI());
            }
        }catch(QueryExecutionException ex){
            ex.printStackTrace();
        }
        return this;
    }
    
    public MetadataCatalogUsageExample searchMicroCTReconstructionStructs(){
        this.printSeparator();
        System.out.println("Searching for MicroCTReconstruction structs. ");
        try{
            List<MicroCTReconstructionStruct> results=this.mrService.searchMicroCTReconstruction("species", "specimen", "input", "","http://lifewatchgreece.com");
            for(MicroCTReconstructionStruct struct : results){
                System.out.println(struct.getReconstructionURI());
            }
        }catch(QueryExecutionException ex){
            ex.printStackTrace();
        }
        return this;
    }
    
    public MetadataCatalogUsageExample searchMicroCTSpecimenStructs(){
        this.printSeparator();
        System.out.println("Searching for MicroCTSpecimen structs.");
        try{
            List<MicroCTSpecimenStruct> results=this.mrService.searchMicroCTSpecimen("specimen", "collection", "species", "provider", "",0,0,"http://lifewatchgreece.com");
            for(MicroCTSpecimenStruct struct : results){
                System.out.println(struct.getSpecimenName());
            }
        }catch(QueryExecutionException ex){
            ex.printStackTrace();
        }
        return this;
    }
    
    public MetadataCatalogUsageExample searchMicroCTScanning(){
        this.printSeparator();
        System.out.println("Searching for MicroCTScanning structs.");
        try{
            List<MicroCTScanningStruct> results=this.mrService.searchMicroCTScanning("device", "specimen", "species","","","",0,0, "http://lifewatchgreece.com");
            for(MicroCTScanningStruct struct : results){
                System.out.println(struct.getScanningURI());
            }
        }catch(QueryExecutionException ex){
            ex.printStackTrace();
        }
        return this;
    }
    
    public MetadataCatalogUsageExample searchMorphometrics(){
        this.printSeparator();
        System.out.println("Searching for Morphometrics structs.");
        try{
            List<MorphometricsStruct> results=this.mrService.searchMorphometrics("species", "dimension","", "http://lifewatchgreece.com");
            for(MorphometricsStruct struct : results){
                System.out.println(struct.getAttributeAssignmentEventURI());
            }
        }catch(QueryExecutionException ex){
            ex.printStackTrace();
        }
        return this;
    }
    
    public MetadataCatalogUsageExample searchScientificNaming(){
        this.printSeparator();
        System.out.println("Searching for ScientificNaming structs.");
        try{
            List<ScientificNamingStruct> results=this.mrService.searchScientificNaming("species", "timespan", "actor", "dataset","", 0,0, "http://lifewatchgreece.com");
            for(ScientificNamingStruct struct : results){
                System.out.println(struct.getScientificNameAssignmentEventURI());
            }
        }catch(QueryExecutionException ex){
            ex.printStackTrace();
        }
        return this;
    }
    
    public MetadataCatalogUsageExample searchSpecimen(){
        this.printSeparator();
        System.out.println("Searching for Specimen structs.");
        try{
            List<SpecimenStruct> results=this.mrService.searchSpecimen("specimen", "species", "collection", "","http://lifewatchgreece.com");
            for(SpecimenStruct struct : results){
                System.out.println(struct.getSpecimenURI());
            }
        }catch(QueryExecutionException ex){
            ex.printStackTrace();
        }
        return this;
    }
    
    public MetadataCatalogUsageExample searchStats(){
        this.printSeparator();
        System.out.println("Searching for Stats structs.");
        try{
            List<StatsStruct> results=this.mrService.searchStats("species", "dimension","", "http://lifewatchgreece.com");
            for(StatsStruct struct : results){
                System.out.println(struct.getDataEvaluationURI());
            }
        }catch(QueryExecutionException ex){
            ex.printStackTrace();
        }
        return this;
    }
    
    public MetadataCatalogUsageExample searchSynonym(){
        this.printSeparator();
        System.out.println("Searching for Synonym structs.");
        try{
            List<SynonymStruct> results=this.mrService.searchSynonym("species", "appellation", "synonym","", "http://lifewatchgreece.com");
            for(SynonymStruct struct : results){
                System.out.println(struct.getAppellation());
            }
        }catch(QueryExecutionException ex){
            ex.printStackTrace();
        }
        return this;
    }
    
    public MetadataCatalogUsageExample exportStructs() throws IOException{
        BufferedWriter bw = new BufferedWriter(new FileWriter(new File("commonNames.ntriples")));
        for(CommonNameStruct struct : this.commonNameStructs){
            bw.write(struct.toNtriples());
        }
        bw.flush();
        bw.close();
        bw = new BufferedWriter(new FileWriter(new File("environmental.ntriples")));
        for(EnvironmentalStruct struct : this.environmentalStructs){
            bw.write(struct.toNtriples());
        }
        bw.flush();
        bw.close();
        bw = new BufferedWriter(new FileWriter(new File("identification.ntriples")));
        for(IdentificationStruct struct : this.identificationStructs){
            bw.write(struct.toNtriples());
        }
        bw.flush();
        bw.close();
        bw = new BufferedWriter(new FileWriter(new File("mesaurement.ntriples")));
        for(MeasurementStruct struct : this.measurementStructs){
            bw.write(struct.toNtriples());
        }
        bw.flush();
        bw.close();
        bw = new BufferedWriter(new FileWriter(new File("microctpostprocessing.ntriples")));
        for(MicroCTPostProcessingStruct struct : this.microCTPostProcessingStructs){
            bw.write(struct.toNtriples());
        }
        bw.flush();
        bw.close();
        bw = new BufferedWriter(new FileWriter(new File("microctreconstruction.ntriples")));
        for(MicroCTReconstructionStruct struct : this.microCTReconstructionStructs){
            bw.write(struct.toNtriples());
        }
        bw.flush();
        bw.close();
        bw = new BufferedWriter(new FileWriter(new File("microctscanning.ntriples")));
        for(MicroCTScanningStruct struct : this.microCTScanningStructs){
            bw.write(struct.toNtriples());
        }
        bw.flush();
        bw.close();
        bw = new BufferedWriter(new FileWriter(new File("morphometrics.ntriples")));
        for(MorphometricsStruct struct : this.morphometricsStructs){
            bw.write(struct.toNtriples());
        }
        bw.flush();
        bw.close();
        bw = new BufferedWriter(new FileWriter(new File("occurence.ntriples")));
        for(OccurrenceStruct struct : this.occurrenceStructs){
            bw.write(struct.toNtriples());
        }
        bw.flush();
        bw.close();
        bw = new BufferedWriter(new FileWriter(new File("scientificName.ntriples")));
        for(ScientificNamingStruct struct : this.scientificNameStructs){
            bw.write(struct.toNtriples());
        }
        bw.flush();
        bw.close();
        bw = new BufferedWriter(new FileWriter(new File("specimen.ntriples")));
        for(SpecimenStruct struct : this.specimenStructs){
            bw.write(struct.toNtriples());
        }
        bw.flush();
        bw.close();
        bw = new BufferedWriter(new FileWriter(new File("stats.ntriples")));
        for(StatsStruct struct : this.statsStructs){
            bw.write(struct.toNtriples());
        }
        bw.flush();
        bw.close();
        bw = new BufferedWriter(new FileWriter(new File("synonyms.ntriples")));
        for(SynonymStruct struct : this.synonymStructs){
            bw.write(struct.toNtriples());
        }
        bw.flush();
        bw.close();
        bw = new BufferedWriter(new FileWriter(new File("microctspecimen.ntriples")));
        for(MicroCTSpecimenStruct struct : this.microCTSpecimenStructs){
            bw.write(struct.toNtriples());
        }
        bw.flush();
        bw.close();
            
        return this;
    }
    
    public static void main(String[] args) throws IOException{
        MetadataCatalogUsageExample mcExample=new MetadataCatalogUsageExample().
                                                  createMetadataCatalogClient().
                                                  deleteOldData().
                                                  addStructs();
                                                  Timer.start("cn");
                                                  mcExample.searchCommonNames();
                                                  Timer.stop("cn");
                                                  System.out.println("in "+Timer.report("cn", TimeUnit.MILLISECONDS)+" msec");
                                                  Timer.start("env");
                                                  mcExample.searchEnvironmental();
                                                  Timer.stop("env");
                                                  System.out.println("in "+Timer.report("env", TimeUnit.MILLISECONDS)+" msec");
                                                  Timer.start("ide");
                                                  mcExample.searchIdentification();
                                                  Timer.stop("ide");
                                                  System.out.println("in "+Timer.report("ide", TimeUnit.MILLISECONDS)+" msec");
                                                  Timer.start("meas");
                                                  mcExample.searchMeasurement();
                                                  Timer.stop("meas");
                                                  System.out.println("in "+Timer.report("meas", TimeUnit.MILLISECONDS)+" msec");
                                                  Timer.start("mctss");
                                                  mcExample.searchMicroCTSpecimenStructs();
                                                  Timer.stop("mctss");
                                                  System.out.println("in "+Timer.report("mctss", TimeUnit.MILLISECONDS)+" msec");
                                                  Timer.start("mcts");
                                                  mcExample.searchMicroCTScanning();
                                                  Timer.stop("mcts");
                                                  System.out.println("in "+Timer.report("mcts", TimeUnit.MILLISECONDS)+" msec");
                                                  Timer.start("mctr");
                                                  mcExample.searchMicroCTReconstructionStructs();
                                                  Timer.stop("mctr");
                                                  System.out.println("in "+Timer.report("mctr", TimeUnit.MILLISECONDS)+" msec");
                                                  Timer.start("mctpp");
                                                  mcExample.searchMicroCTPostProcessingStructs();
                                                  Timer.stop("mctpp");
                                                  System.out.println("in "+Timer.report("mctpp", TimeUnit.MILLISECONDS)+" msec");
                                                  Timer.start("mo");
                                                  mcExample.searchMorphometrics();
                                                  Timer.stop("mo");
                                                  System.out.println("in "+Timer.report("mo", TimeUnit.MILLISECONDS)+" msec");
                                                  Timer.start("occ");
                                                  mcExample.searchOccurence();
                                                  Timer.stop("occ");
                                                  System.out.println("in "+Timer.report("occ", TimeUnit.MILLISECONDS)+" msec");
                                                  Timer.start("scn");
                                                  mcExample.searchScientificNaming();
                                                  Timer.stop("scn");
                                                  System.out.println("in "+Timer.report("scn", TimeUnit.MILLISECONDS)+" msec");
                                                  Timer.start("spec");
                                                  mcExample.searchSpecimen();
                                                  Timer.stop("spec");
                                                  System.out.println("in "+Timer.report("spec", TimeUnit.MILLISECONDS)+" msec");
                                                  Timer.start("stats");
                                                  mcExample.searchStats();
                                                  Timer.stop("stats");
                                                  System.out.println("in "+Timer.report("stats", TimeUnit.MILLISECONDS)+" msec");
                                                  Timer.start("syn");
                                                  mcExample.searchSynonym();
                                                  Timer.stop("syn");
                                                  System.out.println("in "+Timer.report("syn", TimeUnit.MILLISECONDS)+" msec");                                                    
    }
   
    private void printSeparator(){
        System.out.println("-----------------------------------------------");
    }
}