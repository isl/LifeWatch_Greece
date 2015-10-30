package eu.lifewatch.example.particular;

import eu.lifewatch.core.model.MicroCTReconstructionStruct;
import eu.lifewatch.exception.QueryExecutionException;
import eu.lifewatch.exception.URIValidationException;
import eu.lifewatch.service.impl.MetadataRepositoryService;
import java.util.List;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/** NOT TESTED
 * 
 * This class is used for testing the cardinality issues of MicroCTReconstructionStruct. 
 * Information about the virtuoso repository that is being used can be found at beans.xml file.
 * 
 * @author Nikos Minadakis (minadakn 'at' ics 'dot' forth 'dot' gr)
 * @author Yannis Marketakis (marketak 'at' ics 'dot' forth 'dot' gr)
 */
public class MicroCTReconstructionlCardinallityExample {
    private MetadataRepositoryService mrService;
    private static final String defaultGraphspace="http://www.ics.forth.gr/isl/directoryService/cardinality/microctreconstruction/example";
    
    public MicroCTReconstructionlCardinallityExample createMRManager(){
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        this.mrService=context.getBean(MetadataRepositoryService.class);
        try{
            this.mrService.deleteTriples(null, null, null, defaultGraphspace);  //remove everything
        }catch(QueryExecutionException ex){
            System.err.println(ex);
        }
        return this;
    }
    
    public MicroCTReconstructionlCardinallityExample insertSomeData(){
        MicroCTReconstructionStruct struct1=new MicroCTReconstructionStruct().withReconstructionURI("http://localhost/reconstruction1")
                                                                              .withActorName("actor 1")
                                                                              .withActorURI("http://localhost/actor1")
                                                                              .withDatasetTitle("dataset 1")
                                                                              .withDatasetURI("http://localhost/dataset1")
                                                                              .withDescription("description 1")
                                                                              .withInput("http://localhost/input1", "input 1")
                                                                              .withProduct("http://localhost/product1", "product 1")
                                                                              .withTimespan("timespan 1");
        MicroCTReconstructionStruct struct2=new MicroCTReconstructionStruct().withReconstructionURI("http://localhost/reconstruction2")
                                                                              .withActorName("actor 2")
                                                                              .withActorURI("http://localhost/actor2")
                                                                              .withDatasetTitle("dataset 2")
                                                                              .withDatasetURI("http://localhost/dataset2")
                                                                              .withDescription("description 2")
                                                                              .withInput("http://localhost/input21", "input 21")
                                                                              .withInput("http://localhost/input22", "input 22")
                                                                              .withProduct("http://localhost/product2", "product 2")
                                                                              .withTimespan("timespan 2");
        MicroCTReconstructionStruct struct3=new MicroCTReconstructionStruct().withReconstructionURI("http://localhost/reconstruction3")
                                                                              .withActorName("actor 3")
                                                                              .withActorURI("http://localhost/actor3")
                                                                              .withDatasetTitle("dataset 3")
                                                                              .withDatasetURI("http://localhost/dataset3")
                                                                              .withDescription("description 3")
                                                                              .withInput("http://localhost/input3", "input 3")
                                                                              .withProduct("http://localhost/product31", "product 31")
                                                                              .withProduct("http://localhost/product32", "product 32")
                                                                              .withTimespan("timespan 3");
        MicroCTReconstructionStruct struct4=new MicroCTReconstructionStruct().withReconstructionURI("http://localhost/reconstruction4")
                                                                              .withActorName("actor 4")
                                                                              .withActorURI("http://localhost/actor4")
                                                                              .withDatasetTitle("dataset 4")
                                                                              .withDatasetURI("http://localhost/dataset4")
                                                                              .withDescription("description 4")
                                                                              .withInput("http://localhost/input41", "input 41")
                                                                              .withInput("http://localhost/input42", "input 42")
                                                                              .withProduct("http://localhost/product41", "product 41")
                                                                              .withProduct("http://localhost/product42", "product 42")
                                                                              .withTimespan("timespan 4");
        try{
            System.out.println("Inserting some data");
            this.mrService.insertStruct(struct1,defaultGraphspace);
            this.mrService.insertStruct(struct2,defaultGraphspace);
            this.mrService.insertStruct(struct3,defaultGraphspace);
            this.mrService.insertStruct(struct4,defaultGraphspace);
        }catch(QueryExecutionException | URIValidationException ex){
            System.err.println(ex);
        }
        return this;
    }
    
    public MicroCTReconstructionlCardinallityExample search(){
        try{
            List<MicroCTReconstructionStruct> structs=this.mrService.searchMicroCTReconstruction("species","specimen", "input","", defaultGraphspace);
            System.out.println("Found "+structs.size()+" structs");
            for(MicroCTReconstructionStruct struct : structs){
                System.out.println(struct);
            }
        }catch(QueryExecutionException ex){
            System.err.println(ex);
        }
        return this;
    }
   
    public static void main(String[] args){
        new MicroCTReconstructionlCardinallityExample().createMRManager()
                          .insertSomeData()
                          .search();
    }
}