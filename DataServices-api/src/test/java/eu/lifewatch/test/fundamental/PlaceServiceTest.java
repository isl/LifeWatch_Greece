package eu.lifewatch.test.fundamental;

import eu.lifewatch.common.Resources;
import eu.lifewatch.core.impl.VirtuosoRepositoryManager;
import eu.lifewatch.core.model.DirectoryStruct;
import eu.lifewatch.exception.QueryExecutionException;
import eu.lifewatch.exception.RepositoryConnectionException;
import eu.lifewatch.service.impl.fundamental.PlaceService;
import eu.lifewatch.test.TestResources;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import junit.framework.TestCase;
import static junit.framework.TestCase.fail;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.RDFParseException;
import virtuoso.sesame2.driver.VirtuosoRepository;

/**
 * @author Nikos Minadakis (minadakn 'at' ics 'dot' forth 'dot' gr)
 * @author Yannis Marketakis (marketak 'at' ics 'dot' forth 'dot' gr)
 */
public class PlaceServiceTest extends TestCase{
    private PlaceService service;
    private List<DirectoryStruct> structs;
    private Map<String,DirectoryStruct> structMap;
    private Repository repo;
    
    public PlaceServiceTest(){
        this.structs=new ArrayList<>();
        this.structMap=new HashMap<>();
    }
        
    @Test
    public void testDummy(){
        assertTrue(true);
    }
    
//    @Override
//    public void setUp(){
//        try{
//            Properties properties=new Properties();
//            properties.load(ClassLoader.getSystemResourceAsStream(TestResources.testConfigFilename));
//            String url=properties.getProperty(TestResources.testVirtuosoRepoUrlLabel);
//            String port=properties.getProperty(TestResources.testVirtuosoRepoPortLabel);
//            String username=properties.getProperty(TestResources.testVirtuosoRepoUsernameLabel);
//            String password=properties.getProperty(TestResources.testVirtuosoRepoPasswordLabel);
//            String graphspace=properties.getProperty(TestResources.testVirtuosoRepoGraphspaceFundamental);
//            if(url==null || url.isEmpty()){
//                fail("The URL of the repository has not been provided, or it is empty. "
//                    +"Check the configuration file for the property \""+TestResources.testVirtuosoRepoUrlLabel+"\"");
//            }
//            if(port==null || port.isEmpty()){
//                fail("The PORT of the repository has not been provided, or it is empty. "
//                    +"Check the configuration file for the property \""+TestResources.testVirtuosoRepoPortLabel+"\"");
//            }
//            if(username==null || username.isEmpty()){
//                fail("The USERNAME of the repository has not been provided, or it is empty. "
//                    +"Check the configuration file for the property \""+TestResources.testVirtuosoRepoUsernameLabel+"\"");
//            }
//            if(password==null || password.isEmpty()){
//                fail("The PASSWORD of the repository has not been provided, or it is empty. "
//                    +"Check the configuration file for the property \""+TestResources.testVirtuosoRepoPasswordLabel+"\"");
//            }
//            if(graphspace==null || graphspace.isEmpty()){
//                fail("The GRAPHSPACE-FUNDAMENTAL of the repository has not been provided, or it is empty. "
//                    +"Check the configuration file for the property \""+TestResources.testVirtuosoRepoGraphspaceFundamental+"\"");
//            }
//            this.service=new PlaceService(new VirtuosoRepositoryManager(url, port, username, password));
//            this.initializeRepo(url, port, username, password, graphspace);
//        }catch(IOException ex){
//            fail("Unable to find the testConfig.properties files\n"+ex.toString());
//        }catch(RepositoryConnectionException ex){
//            fail("Unable to connect to the repository\n"+ex.toString());
//        }catch(RDFParseException | RepositoryException ex){
//            fail("An error occured while loading sample data in the repository for testing\n"+ex.toString());
//        }
//    }
//    
//    /*At first we will just test that it will not fail. After finalizing the template SPARQL queries we will fix it. */
//    @Test
//    public void testPlaceHasDimension(){
//        try{
//            List<Map<String,String>> results=this.service.getPlaceHasDimension("dimension_value");
//            assertNotNull(results);
//        }catch(QueryExecutionException ex){
//            fail("At this point we should encounter this\n"+ex);
//        }
//    }
//    
//    /*At first we will just test that it will not fail. After finalizing the template SPARQL queries we will fix it. */
//    @Test
//    public void testPlaceHasMetThing(){
//        try{
//            List<Map<String,String>> results=this.service.getPlaceHasMetThing("http://localhost/thing");
//            assertNotNull(results);
//        }catch(QueryExecutionException ex){
//            fail("At this point we should encounter this\n"+ex);
//        }
//    }
//    
//    /*At first we will just test that it will not fail. After finalizing the template SPARQL queries we will fix it. */
//    @Test
//    public void testPlaceHasPartPlace(){
//        try{
//            List<Map<String,String>> results=this.service.getPlaceHasPartPlace("http://localhost/place");
//            assertNotNull(results);
//        }catch(QueryExecutionException ex){
//            fail("At this point we should encounter this\n"+ex);
//        }
//    }
//    
//    /*At first we will just test that it will not fail. After finalizing the template SPARQL queries we will fix it. */
//    @Test
//    public void testPlaceIsPartOfPlace(){
//        try{
//            List<Map<String,String>> results=this.service.getPlaceIsPartOfPlace("http://localhost/place");
//            assertNotNull(results);
//        }catch(QueryExecutionException ex){
//            fail("At this point we should encounter this\n"+ex);
//        }
//    }
//    
//    private void initializeRepo(String url, String port, String username, String password, String graphspace) throws RepositoryException, RDFParseException{
//        this.connectToRepo(url, port, username, password);
//        try{
//            /*The values of the structs are being used in the junit tests. If change they will break junit tests*/
//            this.structs.add(new DirectoryStruct().withDatasetURI("http://localhost/dataset1")
//                                       .withDatasetName("dataset 1")
//                                       .withOwnerURI("http://localhost/owner1")
//                                       .withOwnerName("owner 1")
//                                       .withKeeperURI("http://localhost/keeper1")
//                                       .withKeeperName("keeper 1")
//                                       .withCuratorURI("http://localhost/curator1")
//                                       .withCuratorName("curator 1")
//                                       .withRightsHolderURI("http://localhost/licenseOwner")
//                                       .withAccessMethod("an access method")
//                                       .withNote("a note")
//                                       .withLocation("a location")
//                                       .withContactPoint("a contact point")
//                                       .withParentDatasetURI("http://localhost/dataset1"));    //there is a bug with junit/virtuoso
//            this.structs.add(new DirectoryStruct().withDatasetURI("http://localhost/dataset2")
//                                       .withDatasetName("dataset 2")
//                                       .withOwnerURI("http://localhost/owner1")
//                                       .withOwnerName("owner 1")
//                                       .withKeeperURI("http://localhost/keeper2")
//                                       .withKeeperName("keeper 2")
//                                       .withCuratorURI("http://localhost/curator1")
//                                       .withCuratorName("curator 1")
//                                       .withRightsHolderURI("http://localhost/licenseOwner")
//                                       .withAccessMethod("an access method")
//                                       .withNote("a note")
//                                       .withLocation("a location")
//                                       .withContactPoint("a contact point")
//                                       .withParentDatasetURI("http://localhost/dataset1"));
//            this.structs.add(new DirectoryStruct().withDatasetURI("http://localhost/dataset3")
//                                        .withDatasetName("dataset 3")
//                                        .withOwnerURI("http://localhost/owner2")
//                                        .withOwnerName("owner 2")
//                                        .withKeeperURI("http://localhost/keeper2")
//                                        .withKeeperName("keeper 2")
//                                        .withCuratorURI("http://localhost/curator1")
//                                        .withCuratorName("curator 1")
//                                        .withRightsHolderURI("http://localhost/licenseOwner")
//                                        .withAccessMethod("an access method")
//                                        .withNote("a note")
//                                        .withLocation("a location")
//                                        .withContactPoint("a contact point")
//                                        .withParentDatasetURI("http://localhost/dataset2"));
//            for(DirectoryStruct strc : this.structs){
//                this.structMap.put(strc.getDatasetName(), strc);
//            }
//            RepositoryConnection repoConn=this.repo.getConnection();
//            repoConn.clear(this.repo.getValueFactory().createURI(graphspace));
//            for(DirectoryStruct struct : this.structs){
//                repoConn.add(new StringReader(struct.toNtriples()), graphspace,RDFFormat.NTRIPLES, this.repo.getValueFactory().createURI(graphspace));
//            }
//            repoConn.close();
//        }catch(IOException ex){
//            fail("Unable to find test data to import them to the repository for testing\n"+ex.toString());            
//        }
//    }
//    
//    private void connectToRepo(String url, String port, String username, String password) throws RepositoryException{
//        if(url.startsWith(Resources.defaultUrlPrefix)){
//            url=url.replace(Resources.defaultUrlPrefix, "");
//        }
//        if(url.endsWith("/")){
//            url=url.substring(0, url.length()-1); 
//        }
//        this.repo=new VirtuosoRepository(Resources.defaultVirtuosoJdbcUrlPrefix+url+":"+port, username, password);
//        RepositoryConnection repoConn=this.repo.getConnection();
//        repoConn.close();
//    }
}
