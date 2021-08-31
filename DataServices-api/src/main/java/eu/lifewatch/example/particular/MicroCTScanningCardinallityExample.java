package eu.lifewatch.example.particular;

import eu.lifewatch.core.model.MicroCTScanningStruct;
import eu.lifewatch.exception.QueryExecutionException;
import eu.lifewatch.exception.URIValidationException;
import eu.lifewatch.service.impl.MetadataRepositoryService;
import java.util.List;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/** NOT TESTED
 * 
 * This class is used for testing the cardinality issues of MicroCTScanningStruct. 
 * Information about the virtuoso repository that is being used can be found at beans.xml file.
 * 
 * @author Nikos Minadakis (minadakn 'at' ics 'dot' forth 'dot' gr)
 * @author Yannis Marketakis (marketak 'at' ics 'dot' forth 'dot' gr)
 */
public class MicroCTScanningCardinallityExample {
    private MetadataRepositoryService mrService;
    private static final String defaultGraphspace="http://www.ics.forth.gr/isl/directoryService/cardinality/microctreconstruction/example";
    
    public MicroCTScanningCardinallityExample createMRManager(){
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        this.mrService=context.getBean(MetadataRepositoryService.class);
        try{
            this.mrService.deleteTriples(null, null, null, defaultGraphspace);  //remove everything
        }catch(QueryExecutionException ex){
            System.err.println(ex);
        }
        return this;
    }
    
    public MicroCTScanningCardinallityExample insertSomeData(){
        MicroCTScanningStruct struct1=new MicroCTScanningStruct().withScanningURI("http://localhost/scanning1")
                                                                 .withActorName("actor 1")
                                                                 .withActorURI("http://localhost/actor1")
                                                                 .withDatasetName("dataset 1")
                                                                 .withDatasetURI("http://localhost/dataset1")
                                                                 .withDescription("description 1")
                                                                 .withDeviceName("device 1")
                                                                 .withDeviceType("device type 1")
                                                                 .withDeviceURI("http://locahost/device1")
                                                                 .withEquipmentURI("http://locahost/equipement1")
                                                                 .withMethodName("method 1")
                                                                 .withMethodURI("http://locahost/method1")
                                                                 .withProduct("http://locahost/product1", "product 1")
                                                                 .withSpecimenName("specimen 1")
                                                                 .withSpecimenURI("http://localhost/specimen1")
                                                                 .withTimespan("timespan 1");
        MicroCTScanningStruct struct2=new MicroCTScanningStruct().withScanningURI("http://localhost/scanning2")
                                                                 .withActorName("actor 2")
                                                                 .withActorURI("http://localhost/actor2")
                                                                 .withDatasetName("dataset 2")
                                                                 .withDatasetURI("http://localhost/dataset2")
                                                                 .withDescription("description 2")
                                                                 .withDeviceName("device 2")
                                                                 .withDeviceType("device type 2")
                                                                 .withDeviceURI("http://locahost/device2")
                                                                 .withEquipmentURI("http://locahost/equipement2")
                                                                 .withMethodName("method 2")
                                                                 .withMethodURI("http://locahost/method2")
                                                                 .withProduct("http://locahost/product2", "product 2")
                                                                 .withProduct("http://locahost/product3", "product 3")
                                                                 .withSpecimenName("specimen 2")
                                                                 .withSpecimenURI("http://localhost/specimen2")
                                                                 .withTimespan("timespan 2");
        try{
            System.out.println("Inserting some data");
            this.mrService.insertStruct(struct1,defaultGraphspace);
            this.mrService.insertStruct(struct2,defaultGraphspace);
        }catch(QueryExecutionException | URIValidationException ex){
            System.err.println(ex);
        }
        return this;
    }
    
    public MicroCTScanningCardinallityExample search(){
        try{
            List<MicroCTScanningStruct> structs=this.mrService.searchMicroCTScanning("device", "specimen", "species","", "","",0,0,defaultGraphspace);
            System.out.println("Found "+structs.size()+" structs");
            for(MicroCTScanningStruct struct : structs){
                System.out.println(struct);
            }
        }catch(QueryExecutionException ex){
            System.err.println(ex);
        }
        return this;
    }
   
    public static void main(String[] args){
        new MicroCTScanningCardinallityExample().createMRManager()
                          .insertSomeData()
                          .search();
    }
}