package eu.lifewatch.example.particular;

import eu.lifewatch.core.model.MeasurementStruct;
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
public class MeasurementlCardinallityExample {
    private MetadataRepositoryService mrService;
    private static final String defaultGraphspace="http://www.ics.forth.gr/isl/directoryService/cardinality/measurement/example";
    
    public MeasurementlCardinallityExample createMRManager(){
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        this.mrService=context.getBean(MetadataRepositoryService.class);
        try{
            this.mrService.deleteTriples(null, null, null, defaultGraphspace);  //remove everything
        }catch(QueryExecutionException ex){
            System.err.println(ex);
        }
        return this;
    }
    
    public MeasurementlCardinallityExample insertSomeData(){
        MeasurementStruct struct1=new MeasurementStruct().withMeasurementEventURI("http://localhost/measurementEvent1")
                                                         .withActor("http://localhost/actor1", "actor 1")
                                                         .withDatasetName("dataset 1")
                                                         .withDatasetURI("http://localhost/dataset1")
                                                         .withDimensionName("dimension 1")
                                                         .withDimensionType("http://localhost/dimensiontype1")
                                                         .withDimensionURI("http://localohost/dimesion1")
                                                         .withDimensionUnit("dimension unit 1")
                                                         .withDimensionValue("dimension value 1")
                                                         .withSpeciesName("species 1")
                                                         .withSpeciesURI("http://localhost/species1")
                                                         .withSpecimenName("specimen 1")
                                                         .withSpecimenURI("http://localhost/specimen1")
                                                         .withTimeSpan("timespan 1");
        MeasurementStruct struct2=new MeasurementStruct().withMeasurementEventURI("http://localhost/measurementEvent2")
                                                         .withActor("http://localhost/actor2", "actor 2")
                                                         .withActor("http://localhost/actor3", "actor 3")
                                                         .withDatasetName("dataset 2")
                                                         .withDatasetURI("http://localhost/dataset2")
                                                         .withDimensionName("dimension 2")
                                                         .withDimensionType("http://localhost/dimensiontype2")
                                                         .withDimensionURI("http://localohost/dimesion2")
                                                         .withDimensionUnit("dimension unit 2")
                                                         .withDimensionValue("dimension value 2")
                                                         .withSpeciesName("species 2")
                                                         .withSpeciesURI("http://localhost/species2")
                                                         .withSpecimenName("specimen 2")
                                                         .withSpecimenURI("http://localhost/specimen2")
                                                         .withTimeSpan("timespan 2");
        try{
            System.out.println("Inserting some data");
            this.mrService.insertStruct(struct1,defaultGraphspace);
            this.mrService.insertStruct(struct2,defaultGraphspace);
        }catch(QueryExecutionException | URIValidationException ex){
            System.err.println(ex);
        }
        return this;
    }
    
    public MeasurementlCardinallityExample search(){
        try{
            List<MeasurementStruct> structs=this.mrService.searchMeasurement("specimen", "species", "dimension", "",defaultGraphspace);
            System.out.println("Found "+structs.size()+" structs");
            for(MeasurementStruct struct : structs){
                System.out.println(struct);
            }
        }catch(QueryExecutionException ex){
            System.err.println(ex);
        }
        return this;
    }
   
    public static void main(String[] args){
        new MeasurementlCardinallityExample().createMRManager()
                          .insertSomeData()
                          .search();
    }
}