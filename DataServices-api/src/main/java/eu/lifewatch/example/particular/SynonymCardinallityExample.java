package eu.lifewatch.example.particular;

import eu.lifewatch.core.model.SynonymStruct;
import eu.lifewatch.exception.QueryExecutionException;
import eu.lifewatch.exception.URIValidationException;
import eu.lifewatch.service.impl.MetadataRepositoryService;
import java.util.List;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * This class is used for testing the cardinality issues of SynonymStruct. 
 * Information about the virtuoso repository that is being used can be found at beans.xml file.
 * 
 * @author Nikos Minadakis (minadakn 'at' ics 'dot' forth 'dot' gr)
 * @author Yannis Marketakis (marketak 'at' ics 'dot' forth 'dot' gr)
 */
public class SynonymCardinallityExample {
    private MetadataRepositoryService mrService;
    private static final String defaultGraphspace="http://www.ics.forth.gr/isl/directoryService/cardinality/synonnym/example";
    
    public SynonymCardinallityExample createMRManager(){
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        this.mrService=context.getBean(MetadataRepositoryService.class);
        try{
            this.mrService.deleteTriples(null, null, null, defaultGraphspace);  //remove everything
        }catch(QueryExecutionException ex){
            System.err.println(ex);
        }
        return this;
    }
    
    public SynonymCardinallityExample insertSomeData(){
        SynonymStruct struct1=new SynonymStruct().withAppellation("appellation 1")
                                                 .withAppellationURI("http://localhost/appellation1")
                                                 .withDatasetName("dataset 1")
                                                 .withDatasetURI("http://localhost/dataset1")
                                                 .withSpeciesName("species 1")
                                                 .withSpeciesURI("http://localhost/species1")
                                                 .withSynonym("http://localhost/synonym1", "synonym 1");
        SynonymStruct struct2=new SynonymStruct().withAppellation("appellation 2")
                                                 .withAppellationURI("http://localhost/appellation2")
                                                 .withDatasetName("dataset 2")
                                                 .withDatasetURI("http://localhost/dataset2")
                                                 .withSpeciesName("species 2")
                                                 .withSpeciesURI("http://localhost/species2")
                                                 .withSynonym("http://localhost/synonym2", "synonym 2")
                                                 .withSynonym("http://localhost/synonym3", "synonym 3");
        
        try{
            System.out.println("Inserting some data");
            this.mrService.insertStruct(struct1,defaultGraphspace);
            this.mrService.insertStruct(struct2,defaultGraphspace);
        }catch(QueryExecutionException | URIValidationException ex){
            System.err.println(ex);
        }
        return this;
    }
    
    public SynonymCardinallityExample search(){
        try{
            List<SynonymStruct> structs=this.mrService.searchSynonym("species", "appellation", "synonym","", defaultGraphspace);
            System.out.println("Found "+structs.size()+" structs");
            for(SynonymStruct struct : structs){
                System.out.println(struct);
            }
        }catch(QueryExecutionException ex){
            System.err.println(ex);
        }
        return this;
    }
   
    public static void main(String[] args){
        new SynonymCardinallityExample().createMRManager()
                          .insertSomeData()
                          .search();
    }
}