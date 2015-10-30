package eu.lifewatch.example.particular;

import eu.lifewatch.core.model.EnvironmentalStruct;
import eu.lifewatch.exception.QueryExecutionException;
import eu.lifewatch.exception.URIValidationException;
import eu.lifewatch.service.impl.MetadataRepositoryService;
import java.util.List;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * This class is used for testing the cardinality issues of EnvironmentalStruct. 
 * Information about the virtuoso repository that is being used can be found at beans.xml file.
 * 
 * @author Nikos Minadakis (minadakn 'at' ics 'dot' forth 'dot' gr)
 * @author Yannis Marketakis (marketak 'at' ics 'dot' forth 'dot' gr)
 */
public class EnvironmentalCardinallityExample {
    private MetadataRepositoryService mrService;
    private static final String defaultGraphspace="http://www.ics.forth.gr/isl/directoryService/cardinality/environmental/example";
    
    public EnvironmentalCardinallityExample createMRManager(){
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        this.mrService=context.getBean(MetadataRepositoryService.class);
        try{
            this.mrService.deleteTriples(null, null, null, defaultGraphspace);  //remove everything
        }catch(QueryExecutionException ex){
            System.err.println(ex);
        }
        return this;
    }
    
    public EnvironmentalCardinallityExample insertSomeData(){
        EnvironmentalStruct struct1=new EnvironmentalStruct().withMeasurementEventURI("http://localhost/measurementEvent1")
                                                             .withActor("http://localhost/actor1","actor 1")
                                                             .withDatasetURI("http://localhost/dataset1")
                                                             .withDatasetName("dataset 1")
                                                             .withDimensionName("dimension 1")
                                                             .withDimensionTypeURI("http://locahost/dimensionType1")
                                                             .withDimensionURI("http://localhost/dimension1")
                                                             .withDimensionUnit("dimension unit 1")
                                                             .withDimensionValue("dimension value 1")
                                                             .withPlaceName("place 1")
                                                             .withPlaceURI("http://localhost/place1")
                                                             .withStationURI("http://localhost/station1")
                                                             .withStationName("station 1")
                                                             .withTimeSpan("timespan1");
        EnvironmentalStruct struct2=new EnvironmentalStruct().withMeasurementEventURI("http://localhost/measurementEvent2")
                                                             .withActor("http://localhost/actor2","actor 2")
                                                             .withActor("http://localhost/actor3","actor 3")
                                                             .withDatasetURI("http://localhost/dataset2")
                                                             .withDatasetName("dataset 2")
                                                             .withDimensionName("dimension 2")
                                                             .withDimensionTypeURI("http://locahost/dimensionType2")
                                                             .withDimensionURI("http://localhost/dimension2")
                                                             .withDimensionUnit("dimension unit 2")
                                                             .withDimensionValue("dimension value 2")
                                                             .withPlaceName("place 2")
                                                             .withPlaceURI("http://localhost/place2")
                                                             .withStationURI("http://localhost/station2")
                                                             .withStationName("station 2")
                                                             .withTimeSpan("timespan2");
        try{
            System.out.println("Inserting some data");
            this.mrService.insertStruct(struct1,defaultGraphspace);
            this.mrService.insertStruct(struct2,defaultGraphspace);
        }catch(QueryExecutionException | URIValidationException ex){
            System.err.println(ex);
        }
        return this;
    }
    
    public EnvironmentalCardinallityExample search(){
        try{
            List<EnvironmentalStruct> structs=this.mrService.searchEnvironmental("dimension", "place", "",defaultGraphspace);
            System.out.println("Found "+structs.size()+" structs");
            for(EnvironmentalStruct struct : structs){
                System.out.println(struct);
            }
        }catch(QueryExecutionException ex){
            System.err.println(ex);
        }
        return this;
    }
   
    public static void main(String[] args){
        new EnvironmentalCardinallityExample().createMRManager()
                          .insertSomeData()
                          .search();
    }
}