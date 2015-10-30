package eu.lifewatch.example.particular;

import eu.lifewatch.core.model.SpecimenStruct;
import eu.lifewatch.exception.QueryExecutionException;
import eu.lifewatch.exception.URIValidationException;
import eu.lifewatch.service.impl.MetadataRepositoryService;
import java.util.List;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * This class is used for testing the cardinality issues of SpecimenStruct. 
 * Information about the virtuoso repository that is being used can be found at beans.xml file.
 * 
 * @author Nikos Minadakis (minadakn 'at' ics 'dot' forth 'dot' gr)
 * @author Yannis Marketakis (marketak 'at' ics 'dot' forth 'dot' gr)
 */
public class SpecimenCardinallityExample {
    private MetadataRepositoryService mrService;
    private static final String defaultGraphspace="http://www.ics.forth.gr/isl/directoryService/cardinality/specimen/example";
    
    public SpecimenCardinallityExample createMRManager(){
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        this.mrService=context.getBean(MetadataRepositoryService.class);
        try{
            this.mrService.deleteTriples(null, null, null, defaultGraphspace);  //remove everything
        }catch(QueryExecutionException ex){
            System.err.println(ex);
        }
        return this;
    }
    
    public SpecimenCardinallityExample insertSomeData(){
        SpecimenStruct struct1=new SpecimenStruct().withSpecimenURI("http://localhost/specimen1")
                                                   .withSpecimenName("specimen 1")
                                                   .withActor("http://localhost/actor1", "actor 1")
                                                   .withCollectionName("collection 1")
                                                   .withCollectionURI("http://localhost/collection1")
                                                   .withDatasetName("dataset 1")
                                                   .withDatasetURI("http://localhost/dataset1")
                                                   .withIndividualURI("http://localhost/individual1")
                                                   .withMethodName("method 1")
                                                   .withSpeciesName("species 1")
                                                   .withSpeciesURI("http://localhost/species1")
                                                   .withTimeSpan("timespan 1")
                                                   .withTransformationEventURI("http://localhost/transformation1");
        SpecimenStruct struct2=new SpecimenStruct().withSpecimenURI("http://localhost/specimen2")
                                                   .withSpecimenName("specimen 2")
                                                   .withActor("http://localhost/actor2", "actor 2")
                                                   .withActor("http://localhost/actor3", "actor 3")
                                                   .withCollectionName("collection 2")
                                                   .withCollectionURI("http://localhost/collection2")
                                                   .withDatasetName("dataset 2")
                                                   .withDatasetURI("http://localhost/dataset2")
                                                   .withIndividualURI("http://localhost/individual2")
                                                   .withMethodName("method 2")
                                                   .withSpeciesName("species 2")
                                                   .withSpeciesURI("http://localhost/species2")
                                                   .withTimeSpan("timespan 2")
                                                   .withTransformationEventURI("http://localhost/transformation2");
        try{
            System.out.println("Inserting some data");
            this.mrService.insertStruct(struct1,defaultGraphspace);
            this.mrService.insertStruct(struct2,defaultGraphspace);
        }catch(QueryExecutionException | URIValidationException ex){
            System.err.println(ex);
        }
        return this;
    }
    
    public SpecimenCardinallityExample search(){
        try{
            List<SpecimenStruct> structs=this.mrService.searchSpecimen("specimen", "species", "collection","", defaultGraphspace);
            System.out.println("Found "+structs.size()+" structs");
            for(SpecimenStruct struct : structs){
                System.out.println(struct);
            }
        }catch(QueryExecutionException ex){
            System.err.println(ex);
        }
        return this;
    }
   
    public static void main(String[] args){
        new SpecimenCardinallityExample().createMRManager()
                          .insertSomeData()
                          .search();
    }
}