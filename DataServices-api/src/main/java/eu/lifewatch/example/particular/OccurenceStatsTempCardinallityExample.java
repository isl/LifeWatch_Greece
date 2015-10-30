package eu.lifewatch.example.particular;

import eu.lifewatch.core.model.OccurrenceStatsTempStruct;
import eu.lifewatch.exception.QueryExecutionException;
import eu.lifewatch.exception.URIValidationException;
import eu.lifewatch.service.impl.MetadataRepositoryService;
import java.util.List;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * This class is used for testing the cardinality issues of OccurrenceStatsTempStruct. 
 * Information about the virtuoso repository that is being used can be found at beans.xml file.
 * 
 * @author Nikos Minadakis (minadakn 'at' ics 'dot' forth 'dot' gr)
 * @author Yannis Marketakis (marketak 'at' ics 'dot' forth 'dot' gr)
 */
public class OccurenceStatsTempCardinallityExample {
    private MetadataRepositoryService mrService;
    private static final String defaultGraphspace="http://www.ics.forth.gr/isl/directoryService/cardinality/occurenceStatsTemp/example";
    
    public OccurenceStatsTempCardinallityExample createMRManager(){
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        this.mrService=context.getBean(MetadataRepositoryService.class);
        try{
            this.mrService.deleteTriples(null, null, null, defaultGraphspace);  //remove everything
        }catch(QueryExecutionException ex){
            System.err.println(ex);
        }
        return this;
    }
    
    public OccurenceStatsTempCardinallityExample insertSomeData(){
        OccurrenceStatsTempStruct struct1=new OccurrenceStatsTempStruct().withOccurrenceEventURI("http://localhost/occurenceEvent1")
                                                                         .withActor("http://localhost/actor1", "actor 1")
                                                                         .withBibliographicCitation("http://localhost/citation1")
                                                                         .withCountryName("country 1")
                                                                         .withCountryURI("http://localhost/country1")
                                                                         .withDatasetTitle("dataset 1")
                                                                         .withDatasetURI("http://localhost/dataset1")
                                                                         .withEcosystemName("ecosystem 1")
                                                                         .withEcosystemURI("http://localhost/ecosystem1")
                                                                         .withEquipmentTypeName("equipment 1")
                                                                         .withEquipmentTypeURI("http://localhost/equipment1")
                                                                         .withGeodeticDatum("geodetic datum 1")
                                                                         .withHabitatName("habitat 1")
                                                                         .withHabitatURI("http://localhost/habitat1")
                                                                         .withLatitude("latitude 1")
                                                                         .withLocalityName("locality 1")
                                                                         .withLocalityURI("http://localhost/locality1")
                                                                         .withLongitude("longitude 1")
                                                                         .withMaximumDepth("maximum depth 1")
                                                                         .withMinimumDepth("minimum depth 1")
                                                                         .withPhysicalObjectURI("http://localhost/physicalObject1")
                                                                         .withSamplingProtocolName("sampling protocol name 1")
                                                                         .withSamplingProtocolURI("http://localhost/samplingProtocal1")
                                                                        // .withTemporaryAggregate("http://localhost/temporary1", "http://localhost/species1", "species 1", "num 1")
                                                                         .withTimeSpan("timespan 1")
                                                                         .withWaterAreaName("water area 1")
                                                                         .withWaterAreaURI("http://localhost/waterarea1");
        OccurrenceStatsTempStruct struct2=new OccurrenceStatsTempStruct().withOccurrenceEventURI("http://localhost/occurenceEvent2")
                                                                         .withActor("http://localhost/actor21", "actor 21")
                                                                         .withActor("http://localhost/actor22", "actor 22")
                                                                         .withBibliographicCitation("http://localhost/citation2")
                                                                         .withCountryName("country 2")
                                                                         .withCountryURI("http://localhost/country2")
                                                                         .withDatasetTitle("dataset 2")
                                                                         .withDatasetURI("http://localhost/dataset2")
                                                                         .withEcosystemName("ecosystem 2")
                                                                         .withEcosystemURI("http://localhost/ecosystem2")
                                                                         .withEquipmentTypeName("equipment 2")
                                                                         .withEquipmentTypeURI("http://localhost/equipment2")
                                                                         .withGeodeticDatum("geodetic datum 2")
                                                                         .withHabitatName("habitat 2")
                                                                         .withHabitatURI("http://localhost/habitat2")
                                                                         .withLatitude("latitude 2")
                                                                         .withLocalityName("locality 2")
                                                                         .withLocalityURI("http://localhost/locality2")
                                                                         .withLongitude("longitude 2")
                                                                         .withMaximumDepth("maximum depth 2")
                                                                         .withMinimumDepth("minimum depth 2")
                                                                         .withPhysicalObjectURI("http://localhost/physicalObject2")
                                                                         .withSamplingProtocolName("sampling protocol name 2")
                                                                         .withSamplingProtocolURI("http://localhost/samplingProtocal2")
                                                                       //  .withTemporaryAggregate("http://localhost/temporary2", "http://localhost/species2", "species 2", "num 2")
                                                                         .withTimeSpan("timespan 2")
                                                                         .withWaterAreaName("water area 2")
                                                                         .withWaterAreaURI("http://localhost/waterarea2");
        OccurrenceStatsTempStruct struct3=new OccurrenceStatsTempStruct().withOccurrenceEventURI("http://localhost/occurenceEvent3")
                                                                         .withActor("http://localhost/actor3", "actor 3")
                                                                         .withBibliographicCitation("http://localhost/citation3")
                                                                         .withCountryName("country 3")
                                                                         .withCountryURI("http://localhost/country3")
                                                                         .withDatasetTitle("dataset 3")
                                                                         .withDatasetURI("http://localhost/dataset3")
                                                                         .withEcosystemName("ecosystem 3")
                                                                         .withEcosystemURI("http://localhost/ecosystem3")
                                                                         .withEquipmentTypeName("equipment 3")
                                                                         .withEquipmentTypeURI("http://localhost/equipment3")
                                                                         .withGeodeticDatum("geodetic datum 3")
                                                                         .withHabitatName("habitat 3")
                                                                         .withHabitatURI("http://localhost/habitat3")
                                                                         .withLatitude("latitude 3")
                                                                         .withLocalityName("locality 3")
                                                                         .withLocalityURI("http://localhost/locality3")
                                                                         .withLongitude("longitude 3")
                                                                         .withMaximumDepth("maximum depth 3")
                                                                         .withMinimumDepth("minimum depth 3")
                                                                         .withPhysicalObjectURI("http://localhost/physicalObject3")
                                                                         .withSamplingProtocolName("sampling protocol name 3")
                                                                         .withSamplingProtocolURI("http://localhost/samplingProtocal3")
                                                                        // .withTemporaryAggregate("http://localhost/temporary31", "http://localhost/species31", "species 31", "num 31")
                                                                        // .withTemporaryAggregate("http://localhost/temporary32", "http://localhost/species32", "species 32", "num 32")
                                                                         .withTimeSpan("timespan 3")
                                                                         .withWaterAreaName("water area 3")
                                                                         .withWaterAreaURI("http://localhost/waterarea");
        try{
            System.out.println("Inserting some data");
            this.mrService.insertStruct(struct1,defaultGraphspace);
            this.mrService.insertStruct(struct2,defaultGraphspace);
            this.mrService.insertStruct(struct3,defaultGraphspace);
        }catch(QueryExecutionException | URIValidationException ex){
            System.err.println(ex);
        }
        return this;
    }
    
    public OccurenceStatsTempCardinallityExample search(){
        try{
            List<OccurrenceStatsTempStruct> structs=this.mrService.searchOccurenceStatsTemp("species 3", "locality 3", "timespan 3", "num 3","", defaultGraphspace);
            System.out.println("Found "+structs.size()+" structs");
            for(OccurrenceStatsTempStruct struct : structs){
                System.out.println(struct);
            }
        }catch(QueryExecutionException ex){
            System.err.println(ex);
        }
        return this;
    }
   
    public static void main(String[] args){
        new OccurenceStatsTempCardinallityExample().createMRManager()
                          .insertSomeData()
                          .search();
    }
}