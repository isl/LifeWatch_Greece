package eu.lifewatch.example.particular;

import eu.lifewatch.core.model.MorphometricsStruct;
import eu.lifewatch.exception.QueryExecutionException;
import eu.lifewatch.exception.URIValidationException;
import eu.lifewatch.service.impl.MetadataRepositoryService;
import java.util.List;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * This class is used for testing the cardinality issues of MorphometricsStruct. 
 * Information about the virtuoso repository that is being used can be found at beans.xml file.
 * 
 * @author Nikos Minadakis (minadakn 'at' ics 'dot' forth 'dot' gr)
 * @author Yannis Marketakis (marketak 'at' ics 'dot' forth 'dot' gr)
 */
public class MorphometricsCardinallityExample {
    private MetadataRepositoryService mrService;
    private static final String defaultGraphspace="http://www.ics.forth.gr/isl/directoryService/cardinality/morphometrics/example";
    
    public MorphometricsCardinallityExample createMRManager(){
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        this.mrService=context.getBean(MetadataRepositoryService.class);
        try{
            this.mrService.deleteTriples(null, null, null, defaultGraphspace);  //remove everything
        }catch(QueryExecutionException ex){
            System.err.println(ex);
        }
        return this;
    }
    
    public MorphometricsCardinallityExample insertSomeData(){
        MorphometricsStruct struct1=new MorphometricsStruct().withAttributeAssignmentEventURI("http://localhost/morphometricsEvent1")
                                                             .withActor("http://localhost/actor1", "actor 1")
                                                             .withDatasetName("dataset 1")
                                                             .withDatasetURI("http://localhost/dataset1")
                                                             .withDescription("description 1")
                                                             .withDimensionName("dimension name 1")
                                                             .withDimensionType("http://localhost/dimensionType1")
                                                             .withDimensionURI("http://localhost/dimension1")
                                                             .withDimensionUnit("dimension uri 1")
                                                             .withDimensionValue("dimension value 1")
                                                             .withPublicationName("publication 1")
                                                             .withPublicationURI("http://localhost/publication1")
                                                             .withSpeciesName("species 1")
                                                             .withSpeciesURI("http://localhost/species1")
                                                             .withTimeSpan("timespan 1");
        MorphometricsStruct struct2=new MorphometricsStruct().withAttributeAssignmentEventURI("http://localhost/morphometricsEvent2")
                                                             .withActor("http://localhost/actor2", "actor 2")
                                                             .withActor("http://localhost/actor3", "actor 3")
                                                             .withDatasetName("dataset 2")
                                                             .withDatasetURI("http://localhost/dataset2")
                                                             .withDescription("description 2")
                                                             .withDimensionName("dimension name 2")
                                                             .withDimensionType("dhttp://localhost/dimensionType2")
                                                             .withDimensionURI("http://localhost/dimension2")
                                                             .withDimensionUnit("dimension uri 2")
                                                             .withDimensionValue("dimension value 2")
                                                             .withPublicationName("publication 2")
                                                             .withPublicationURI("http://localhost/publication2")
                                                             .withSpeciesName("species 2")
                                                             .withSpeciesURI("http://localhost/species2")
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
    
    public MorphometricsCardinallityExample search(){
        try{
            List<MorphometricsStruct> structs=this.mrService.searchMorphometrics("species", "dimension name", "",defaultGraphspace);
            System.out.println("Found "+structs.size()+" structs");
            for(MorphometricsStruct struct : structs){
                System.out.println(struct);
            }
        }catch(QueryExecutionException ex){
            System.err.println(ex);
        }
        return this;
    }
   
    public static void main(String[] args){
        new MorphometricsCardinallityExample().createMRManager()
                          .insertSomeData()
                          .search();
    }
}