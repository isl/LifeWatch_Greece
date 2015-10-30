package eu.lifewatch.example.particular;

import eu.lifewatch.core.model.IdentificationStruct;
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
public class IdentificationlCardinallityExample {
    private MetadataRepositoryService mrService;
    private static final String defaultGraphspace="http://www.ics.forth.gr/isl/directoryService/cardinality/identification/example";
    
    public IdentificationlCardinallityExample createMRManager(){
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        this.mrService=context.getBean(MetadataRepositoryService.class);
        try{
            this.mrService.deleteTriples(null, null, null, defaultGraphspace);  //remove everything
        }catch(QueryExecutionException ex){
            System.err.println(ex);
        }
        return this;
    }
    
    public IdentificationlCardinallityExample insertSomeData(){
        IdentificationStruct struct1=new IdentificationStruct().withIdentificationEventURI("http://localhost/identification1")
                                                               .withActor("http://locahost/actor1", "actor 1")
                                                               .withDatasetTitle("dataset 1")
                                                               .withDatasetURI("http://locahost/dataset1")
                                                               .withIdentificationReferencesName("identification reference 1")
                                                               .withIdentificationReferencesURI("http://localhost/identification/reference1")
                                                               .withIndividualURI("http://localhost/individual1")
                                                               .withLocalityName("locality 1")
                                                               .withLocalityURI("http://localhost/locality1")
                                                               .withSpeciesName("species 1")
                                                               .withSpeciesURI("http://localhost/species1")
                                                               .withTimeSpan("timespan 1");
        IdentificationStruct struct2=new IdentificationStruct().withIdentificationEventURI("http://localhost/identification2")
                                                               .withActor("http://locahost/actor2", "actor 2")
                                                               .withActor("http://locahost/actor3", "actor 3")
                                                               .withDatasetTitle("dataset 2")
                                                               .withDatasetURI("http://locahost/dataset2")
                                                               .withIdentificationReferencesName("identification reference 2")
                                                               .withIdentificationReferencesURI("http://localhost/identification/reference2")
                                                               .withIndividualURI("http://localhost/individual2")
                                                               .withLocalityName("locality 2")
                                                               .withLocalityURI("http://localhost/locality2")
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
    
    public IdentificationlCardinallityExample search(){
        try{
            List<IdentificationStruct> structs=this.mrService.searchIdentification("species", "timespan", "actor", "locality", "http://localhost/individual","", defaultGraphspace);
            System.out.println("Found "+structs.size()+" structs");
            for(IdentificationStruct struct : structs){
                System.out.println(struct);
            }
        }catch(QueryExecutionException ex){
            System.err.println(ex);
        }
        return this;
    }
   
    public static void main(String[] args){
        new IdentificationlCardinallityExample().createMRManager()
                          .insertSomeData()
                          .search();
    }
}