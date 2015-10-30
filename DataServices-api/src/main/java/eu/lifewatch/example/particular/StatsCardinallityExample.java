package eu.lifewatch.example.particular;

import eu.lifewatch.core.model.StatsStruct;
import eu.lifewatch.exception.QueryExecutionException;
import eu.lifewatch.exception.URIValidationException;
import eu.lifewatch.service.impl.MetadataRepositoryService;
import java.util.List;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * This class is used for testing the cardinality issues of StatsStruct. 
 * Information about the virtuoso repository that is being used can be found at beans.xml file.
 * 
 * @author Nikos Minadakis (minadakn 'at' ics 'dot' forth 'dot' gr)
 * @author Yannis Marketakis (marketak 'at' ics 'dot' forth 'dot' gr)
 */
public class StatsCardinallityExample {
    private MetadataRepositoryService mrService;
    private static final String defaultGraphspace="http://www.ics.forth.gr/isl/directoryService/cardinality/stats/example";
    
    public StatsCardinallityExample createMRManager(){
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        this.mrService=context.getBean(MetadataRepositoryService.class);
        try{
            this.mrService.deleteTriples(null, null, null, defaultGraphspace);  //remove everything
        }catch(QueryExecutionException ex){
            System.err.println(ex);
        }
        return this;
    }
    
    public StatsCardinallityExample insertSomeData(){
        StatsStruct struct1=new StatsStruct().withDataEvaluationURI("http://localhost/attributeAssignment1")
                                             .withActor("http://localhost/actor1", "actor 1")
                                             .withDatasetName("dataset 1")
                                             .withDatasetURI("dataset1")
                                             .withDescription("desccription 1")
                                             .withDimensionName("dimension 1")
                                             .withDimensionType("http://localhost/dimensionType1")
                                             .withDimensionURI("http://localhost/dimension1")
                                             .withDimensionUnit("dimension unit 1")
                                             .withDimensionValue("dimension value 1")
                                             .withPublicationName("publication 1")
                                             .withPublicationURI("http://localhost/publication1")
                                             .withSpeciesName("species 1")
                                             .withSpeciesURI("http://localhost/species1")
                                             .withSpecimenName("specimen 1")
                                             .withSpecimenURI("http://localhost/specimen1")
                                             .withTimeSpan("timespan 1");
        StatsStruct struct2=new StatsStruct().withDataEvaluationURI("http://localhost/attributeAssignment2")
                                             .withActor("http://localhost/actor2", "actor 2")
                                             .withActor("http://localhost/actor3", "actor 3")
                                             .withDatasetName("dataset 2")
                                             .withDatasetURI("dataset2")
                                             .withDescription("desccription 2")
                                             .withDimensionName("dimension 2")
                                             .withDimensionType("http://localhost/dimensionType2")
                                             .withDimensionURI("http://localhost/dimension2")
                                             .withDimensionUnit("dimension unit 2")
                                             .withDimensionValue("dimension value 2")
                                             .withPublicationName("publication 2")
                                             .withPublicationURI("http://localhost/publication2")
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
    
    public StatsCardinallityExample search(){
        try{
            List<StatsStruct> structs=this.mrService.searchStats("species", "dimension","", defaultGraphspace);
            System.out.println("Found "+structs.size()+" structs");
            for(StatsStruct struct : structs){
                System.out.println(struct);
            }
        }catch(QueryExecutionException ex){
            System.err.println(ex);
        }
        return this;
    }
   
    public static void main(String[] args){
        new StatsCardinallityExample().createMRManager()
                          .insertSomeData()
                          .search();
    }
}