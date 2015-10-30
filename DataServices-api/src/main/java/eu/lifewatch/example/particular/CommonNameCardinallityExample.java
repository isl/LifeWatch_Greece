package eu.lifewatch.example.particular;

import eu.lifewatch.service.impl.DirectoryService;
import eu.lifewatch.common.Resources;
import eu.lifewatch.core.model.CommonNameStruct;
import eu.lifewatch.core.model.DirectoryStruct;
import eu.lifewatch.core.model.Triple;
import eu.lifewatch.exception.QueryExecutionException;
import eu.lifewatch.exception.URIValidationException;
import eu.lifewatch.service.impl.MetadataRepositoryService;
import java.util.List;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * This class is used for testing the cardinality issues of CommonNameStruct. 
 * Information about the virtuoso repository that 
 * is being used can be found at beans.xml file.
 * 
 * @author Nikos Minadakis (minadakn 'at' ics 'dot' forth 'dot' gr)
 * @author Yannis Marketakis (marketak 'at' ics 'dot' forth 'dot' gr)
 */
public class CommonNameCardinallityExample {
    private MetadataRepositoryService mrService;
    private static final String defaultGraphspace="http://www.ics.forth.gr/isl/directoryService/cardinality/commonname/example";
    
    public CommonNameCardinallityExample createMRManager(){
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        this.mrService=context.getBean(MetadataRepositoryService.class);
        try{
            this.mrService.deleteTriples(null, null, null, defaultGraphspace);  //remove everything
        }catch(QueryExecutionException ex){
            System.err.println(ex);
        }
        return this;
    }
    
    public CommonNameCardinallityExample insertSomeData(){
        CommonNameStruct struct1=new CommonNameStruct().withCommonNameURI("http://localhost/commonname1")
                                                      .withCommonName("common name 1")
                                                      .withDatasetURI("http://localhost/dataset1")
                                                      .withDatasetName("dataset 1")
                                                      .withLanguageURI("http://localhost/language1")
                                                      .withLanguage("language 1")
                                                      .withPlace("http://localhost/place1","place 1")
                                                      .withSpeciesURI("http://localhost/species1")
                                                      .withSpeciesName("species 1");
        CommonNameStruct struct2=new CommonNameStruct().withCommonNameURI("http://localhost/commonname2")
                                                      .withCommonName("common name 2")
                                                      .withDatasetURI("http://localhost/dataset2")
                                                      .withDatasetName("dataset 2")
                                                      .withLanguageURI("http://localhost/language2")
                                                      .withLanguage("language 2")
                                                      .withPlace("http://localhost/place2","place 2")
                                                      .withPlace("http://localhost/place3","place 3")
                                                      .withSpeciesURI("http://localhost/species2")
                                                      .withSpeciesName("species 2");
        try{
            System.out.println("Inserting some data");
            this.mrService.insertStruct(struct1,defaultGraphspace);
            this.mrService.insertStruct(struct2,defaultGraphspace);
        }catch(QueryExecutionException | URIValidationException ex){
            System.err.println(ex);
        }
        return this;
    }
    
    public CommonNameCardinallityExample search(){
        try{
            List<CommonNameStruct> structs=this.mrService.searchCommonName("species 1", "common name", "place", "language", "",defaultGraphspace);
            System.out.println("Found "+structs.size()+" structs");
            for(CommonNameStruct struct : structs){
                System.out.println("\t"+struct.toString().replaceAll("\n", "\n\t"));
            }
        }catch(QueryExecutionException ex){
            System.err.println(ex);
        }
        return this;
    }
    
    public static void main(String[] args){
        new CommonNameCardinallityExample().createMRManager()
                          .insertSomeData()
                          .search();
    }
}