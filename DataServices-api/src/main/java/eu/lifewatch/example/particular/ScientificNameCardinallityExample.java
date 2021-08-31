package eu.lifewatch.example.particular;

import eu.lifewatch.core.model.ScientificNamingStruct;
import eu.lifewatch.exception.QueryExecutionException;
import eu.lifewatch.exception.URIValidationException;
import eu.lifewatch.service.impl.MetadataRepositoryService;
import java.util.List;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * This class is used for testing the cardinality issues of ScientificNamingStruct. 
 * Information about the virtuoso repository that is being used can be found at beans.xml file.
 * 
 * @author Nikos Minadakis (minadakn 'at' ics 'dot' forth 'dot' gr)
 * @author Yannis Marketakis (marketak 'at' ics 'dot' forth 'dot' gr)
 */
public class ScientificNameCardinallityExample {
    private MetadataRepositoryService mrService;
    private static final String defaultGraphspace="http://www.ics.forth.gr/isl/directoryService/cardinality/scientificName/example";
    
    public ScientificNameCardinallityExample createMRManager(){
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        this.mrService=context.getBean(MetadataRepositoryService.class);
        try{
            this.mrService.deleteTriples(null, null, null, defaultGraphspace);  //remove everything
        }catch(QueryExecutionException ex){
            System.err.println(ex);
        }
        return this;
    }
    
    public ScientificNameCardinallityExample insertSomeData(){
        ScientificNamingStruct struct1=new ScientificNamingStruct().withScientificNameAssignmentEventURI("http://localhost/scientificName1")
                                                                   .withActor("http://localhost/actor1","actor 1")
                                                                   .withAppellation("appellation 1")
                                                                   .withAppellationURI("http://localhost/appellation1")
                                                                   .withDatasetName("dataset 1")
                                                                   .withDatasetURI("http://localhost/dataset1")
                                                                   .withNomenclaturalCodeName("nomenclature code 1")
                                                                   .withNomenclaturalCodeURI("http://localhost/nomenclatureCode1")
                                                                   .withSpeciesName("species 1")
                                                                   .withSpeciesURI("http://localhost/species1")
                                                                   .withTimeSpan("timespan 1");
        ScientificNamingStruct struct2=new ScientificNamingStruct().withScientificNameAssignmentEventURI("http://localhost/scientificName2")
                                                                   .withActor("http://localhost/actor2","actor 2")
                                                                   .withActor("http://localhost/actor3","actor 3")
                                                                   .withAppellation("appellation 2")
                                                                   .withAppellationURI("http://localhost/appellation2")
                                                                   .withDatasetName("dataset 2")
                                                                   .withDatasetURI("http://localhost/dataset2")
                                                                   .withNomenclaturalCodeName("nomenclature code 2")
                                                                   .withNomenclaturalCodeURI("http://localhost/nomenclatureCode2")
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
    
    public ScientificNameCardinallityExample search(){
        try{
            List<ScientificNamingStruct> structs=this.mrService.searchScientificNaming("species", "timespan", "actor", "appellation", "",0,0,defaultGraphspace);
            System.out.println("Found "+structs.size()+" structs");
            for(ScientificNamingStruct struct : structs){
                System.out.println(struct);
            }
        }catch(QueryExecutionException ex){
            System.err.println(ex);
        }
        return this;
    }
   
    public static void main(String[] args){
        new ScientificNameCardinallityExample().createMRManager()
                          .insertSomeData()
                          .search();
    }
}