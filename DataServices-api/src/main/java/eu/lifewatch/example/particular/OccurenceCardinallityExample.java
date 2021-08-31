package eu.lifewatch.example.particular;

import eu.lifewatch.core.model.OccurrenceStruct;
import eu.lifewatch.exception.QueryExecutionException;
import eu.lifewatch.exception.URIValidationException;
import eu.lifewatch.service.impl.MetadataRepositoryService;
import java.util.List;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * This class is used for testing the cardinality issues of OccurrenceStruct. 
 * Information about the virtuoso repository that is being used can be found at beans.xml file.
 * 
 * @author Nikos Minadakis (minadakn 'at' ics 'dot' forth 'dot' gr)
 * @author Yannis Marketakis (marketak 'at' ics 'dot' forth 'dot' gr)
 */
public class OccurenceCardinallityExample {
    private MetadataRepositoryService mrService;
    private static final String defaultGraphspace="http://www.ics.forth.gr/isl/directoryService/cardinality/occurence/example";
    
    public OccurenceCardinallityExample createMRManager(){
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        this.mrService=context.getBean(MetadataRepositoryService.class);
        try{
            this.mrService.deleteTriples(null, null, null, defaultGraphspace);  //remove everything
        }catch(QueryExecutionException ex){
            System.err.println(ex);
        }
        return this;
    }
    
    public OccurenceCardinallityExample insertSomeData(){
        OccurrenceStruct struct1=new OccurrenceStruct().withOccurrenceEventURI("http://localhost/occurence1")
                                                       .withActor("http://localhost/actor1", "actor 1")
                                                       .withBibliographicCitation("http://localhost/citation1")
                                                       .withCountryName("country 1")
                                                       .withCountryURI("http://localhost/country1")
                                                       .withDatasetTitle("dataset 1")
                                                       .withDatasetURI("http://localhost/dataset1")
                                                       .withEcosystemName("ecosystem 1")
                                                       .withEcosystemURI("http://localhost/ecosystem1")
                                                       .withEquipmentTypeName("equipement type 1")
                                                       .withEquipmentTypeURI("http://localhost/equipmentType1")
                                                      // .withGeodeticDatum("geodetic datum 1")
                                                       .withHabitatName("habitat 1")
                                                       .withHabitatURI("http://localhost/habitat1")
                                                       .withIndividualURI("http://localhost/individual1")
                                                       .withLatitude("latitude 1")
                                                       .withLocalityName("locality 1")
                                                       .withLocalityURI("http://localhost/locality1")
                                                       .withLongitude("logitude 1")
                                                       .withMaximumDepth("maximum depth 1")
                                                       .withMinimumDepth("minimum depth 1")
                                                      // .withSamplingProtocolName("sampling protocol 1")
                                                       .withSamplingProtocolURI("http://localhost/samplingProtocol1")
                                                       .withSpeciesName("species 1")
                                                       .withSpeciesURI("http://localhost/species1")
                                                       .withTimeSpan("timespan 1")
                                                       .withWaterAreaName("water area 1")
                                                       .withWaterAreaURI("http://localhost/waterArea1");
        OccurrenceStruct struct2=new OccurrenceStruct().withOccurrenceEventURI("http://localhost/occurence2")
                                                       .withActor("http://localhost/actor2", "actor 2")
                                                       .withActor("http://localhost/actor3", "actor 3")
                                                       .withBibliographicCitation("http://localhost/citation2")
                                                       .withCountryName("country 2")
                                                       .withCountryURI("http://localhost/country2")
                                                       .withDatasetTitle("dataset 2")
                                                       .withDatasetURI("http://localhost/dataset2")
                                                       .withEcosystemName("ecosystem 2")
                                                       .withEcosystemURI("http://localhost/ecosystem2")
                                                       .withEquipmentTypeName("equipement type 2")
                                                       .withEquipmentTypeURI("http://localhost/equipmentType2")
                                                     //  .withGeodeticDatum("geodetic datum 2")
                                                       .withHabitatName("habitat 2")
                                                       .withHabitatURI("http://localhost/habitat2")
                                                       .withIndividualURI("http://localhost/individual2")
                                                       .withLatitude("latitude 2")
                                                       .withLocalityName("locality 2")
                                                       .withLocalityURI("http://localhost/locality2")
                                                       .withLongitude("logitude 2")
                                                       .withMaximumDepth("maximum depth 2")
                                                       .withMinimumDepth("minimum depth 2")
                                                     //  .withSamplingProtocolName("sampling protocol 2")
                                                       .withSamplingProtocolURI("http://localhost/samplingProtocol2")
                                                       .withSpeciesName("species 2")
                                                       .withSpeciesURI("http://localhost/species2")
                                                       .withTimeSpan("timespan 2")
                                                       .withWaterAreaName("water area 2")
                                                       .withWaterAreaURI("http://localhost/waterArea2");

        try{
            System.out.println("Inserting some data");
            this.mrService.insertStruct(struct1,defaultGraphspace);
            this.mrService.insertStruct(struct2,defaultGraphspace);
        }catch(QueryExecutionException | URIValidationException ex){
            System.err.println(ex);
        }
        return this;
    }
    
    public OccurenceCardinallityExample search(){
        try{
            List<OccurrenceStruct> structs=this.mrService.searchOccurrence("species", "locality", "timespan","",0,0,defaultGraphspace);
            System.out.println("Found "+structs.size()+" structs");
            for(OccurrenceStruct struct : structs){
                System.out.println(struct);
            }
        }catch(QueryExecutionException ex){
            System.err.println(ex);
        }
        return this;
    }
   
    public static void main(String[] args){
        new OccurenceCardinallityExample().createMRManager()
                          .insertSomeData()
                          .search();
    }
}