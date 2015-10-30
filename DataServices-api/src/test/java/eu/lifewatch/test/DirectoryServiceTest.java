package eu.lifewatch.test;

import eu.lifewatch.common.ResourceType;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import junit.framework.TestCase;
import eu.lifewatch.common.Resources;
import eu.lifewatch.core.impl.VirtuosoRepositoryManager;
import eu.lifewatch.core.model.DirectoryStruct;
import eu.lifewatch.core.model.Triple;
import eu.lifewatch.service.impl.DirectoryService;
import eu.lifewatch.exception.DataImportException;
import eu.lifewatch.exception.QueryExecutionException;
import eu.lifewatch.exception.RepositoryConnectionException;
import eu.lifewatch.exception.URIValidationException;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.openrdf.model.Literal;
import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.model.impl.LiteralImpl;
import org.openrdf.query.MalformedQueryException;
import org.openrdf.query.QueryEvaluationException;
import org.openrdf.query.QueryLanguage;
import org.openrdf.query.TupleQueryResult;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.RepositoryResult;
import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.RDFParseException;
import virtuoso.sesame2.driver.VirtuosoRepository;

/**
 * @author Nikos Minadakis (minadakn 'at' ics 'dot' forth 'dot' gr)
 * @author Yannis Marketakis (marketak 'at' ics 'dot' forth 'dot' gr)
 */
public class DirectoryServiceTest extends TestCase{
    DirectoryService manager;
    Repository repo;
    List<DirectoryStruct> structs;
    Map<String,DirectoryStruct> structMap;
    private static final Logger logger=Logger.getLogger(DirectoryService.class);

    public DirectoryServiceTest(String name){
        super(name);
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
//            this.manager=new DirectoryService(new VirtuosoRepositoryManager(url, port, username, password));
//            this.initializeRepo(url, port, username, password);
//            this.clearGraphs();
//        }catch(IOException ex){
//            fail("Unable to find the testConfig.properties files\n"+ex.toString());
//        }catch(RepositoryConnectionException ex){
//            fail("Unable to connect to the repository\n"+ex.toString());
//        }catch(RDFParseException | RepositoryException ex){
//            fail("An error occured while loading sample data in the repository for testing\n"+ex.toString());
//        }
//    }
//    
//    @Override
//    public void tearDown() throws RepositoryException{
//        
//    }
//
//    @Test
//    public void testImportData(){
//        /* Try importing a file in ttl format*/
//        try{
//            RepositoryConnection repoConn=this.repo.getConnection();
//            String sparqlQuery="SELECT COUNT(?x) as ?count FROM <"+TestResources.testDataGraphSpaceForUpdates+"> WHERE{?x ?y ?z}";
//            TupleQueryResult results=repoConn.prepareTupleQuery(QueryLanguage.SPARQL, sparqlQuery).evaluate();
//            assertTrue(results.hasNext());
//            String retVal=results.next().getBinding("count").getValue().stringValue();
//            assertEquals(0,Integer.parseInt(retVal));
//            this.manager.importFile(TestResources.testResourcesPath+TestResources.testDataImportSingle_Ntriples, TestResources.testDataGraphSpaceForUpdates);
//            results=repoConn.prepareTupleQuery(QueryLanguage.SPARQL, sparqlQuery).evaluate();
//            assertTrue(results.hasNext());
//            retVal=results.next().getBinding("count").getValue().stringValue();
//            assertTrue(Integer.parseInt(retVal)>0);
//            repoConn.close();
//        }catch(DataImportException ex){
//            fail("At this point we shouldn't encounter this. The given file is correct\n"+ex.toString());
//        }catch(RepositoryException | MalformedQueryException | QueryEvaluationException ex){
//            fail("At this point we shouldn't encounter this. The given SPARQL query is correct\n"+ex.toString());
//        }
//        /* Try importing a file in rdf format*/
//        try{
//            RepositoryConnection repoConn=this.repo.getConnection();
//            repoConn.clear(this.repo.getValueFactory().createURI(TestResources.testDataGraphSpaceForUpdates));
//            String sparqlQuery="SELECT COUNT(?x) as ?count FROM <"+TestResources.testDataGraphSpaceForUpdates+"> WHERE{?x ?y ?z}";
//            TupleQueryResult results=repoConn.prepareTupleQuery(QueryLanguage.SPARQL, sparqlQuery).evaluate();
//            assertTrue(results.hasNext());
//            String retVal=results.next().getBinding("count").getValue().stringValue();
//            assertEquals(0,Integer.parseInt(retVal));
//            this.manager.importFile(TestResources.testResourcesPath+TestResources.testDataImportSingle_rdf, TestResources.testDataGraphSpaceForUpdates);
//            results=repoConn.prepareTupleQuery(QueryLanguage.SPARQL, sparqlQuery).evaluate();
//            assertTrue(results.hasNext());
//            retVal=results.next().getBinding("count").getValue().stringValue();
//            assertTrue(Integer.parseInt(retVal)>0);
//            repoConn.close();
//        }catch(DataImportException ex){
//            fail("At this point we shouldn't encounter this. The given file is correct\n"+ex.toString());
//        }catch(RepositoryException | MalformedQueryException | QueryEvaluationException ex){
//            fail("At this point we shouldn't encounter this. The given SPARQL query is correct\n"+ex.toString());
//        }
//        /* Try importing wrong data*/
//        try{
//            this.manager.importFile(TestResources.testResourcesPath+TestResources.testConfigFilename, TestResources.testDataGraphSpaceForUpdates);
//            fail("The given file is incorrect. It should fail here.");
//        }catch(DataImportException ex){
//            assertTrue("Successfully caught Exception",true);
//        }
//        /* Try importing data that do not exist*/
//        try{
//            this.manager.importFile("path_to_a_file_that_does_not_exist.ttl", TestResources.testDataGraphSpaceForUpdates);
//            fail("The given file does not exist. It should fail here.");
//        }catch(DataImportException ex){
//            assertTrue("Successfully caught Exception",true);
//        }
//    }
//    
//    @Test
//    public void testImportFiles(){
//        /* Try importing some files*/
//        try{
//            RepositoryConnection repoConn=this.repo.getConnection();
//            String sparqlQuery="SELECT COUNT(?x) as ?count FROM <"+TestResources.testDataGraphSpaceForUpdates+"> WHERE{?x ?y ?z}";
//            TupleQueryResult results=repoConn.prepareTupleQuery(QueryLanguage.SPARQL, sparqlQuery).evaluate();
//            assertTrue(results.hasNext());
//            String retVal=results.next().getBinding("count").getValue().stringValue();
//            assertEquals(0,Integer.parseInt(retVal));
//            this.manager.importFiles(TestResources.testResourcesPath, TestResources.testDataGraphSpaceForUpdates);
//            results=repoConn.prepareTupleQuery(QueryLanguage.SPARQL, sparqlQuery).evaluate();
//            assertTrue(results.hasNext());
//            retVal=results.next().getBinding("count").getValue().stringValue();
//            assertTrue(Integer.parseInt(retVal)>0);
//            repoConn.close();
//        }catch(DataImportException ex){
//            fail("At this point we shouldn't encounter this. The given file is correct\n"+ex.toString());
//        }catch(RepositoryException | MalformedQueryException | QueryEvaluationException ex){
//            fail("At this point we shouldn't encounter this. The given SPARQL query is correct\n"+ex.toString());
//        }
//        /* Try importing with a folder containing no (related) data */
//        try{
//            RepositoryConnection repoConn=this.repo.getConnection();
//            repoConn.clear(this.repo.getValueFactory().createURI(TestResources.testDataGraphSpaceForUpdates));
//            String sparqlQuery="SELECT COUNT(?x) as ?count FROM <"+TestResources.testDataGraphSpaceForUpdates+"> WHERE{?x ?y ?z}";
//            TupleQueryResult results=repoConn.prepareTupleQuery(QueryLanguage.SPARQL, sparqlQuery).evaluate();
//            assertTrue(results.hasNext());
//            String retVal=results.next().getBinding("count").getValue().stringValue();
//            assertEquals(0,Integer.parseInt(retVal));
//            this.manager.importFiles("src/main/", TestResources.testDataGraphSpaceForUpdates);
//            results=repoConn.prepareTupleQuery(QueryLanguage.SPARQL, sparqlQuery).evaluate();
//            assertTrue(results.hasNext());
//            retVal=results.next().getBinding("count").getValue().stringValue();
//            assertEquals(0,Integer.parseInt(retVal));
//            repoConn.close();
//        }catch(DataImportException ex){
//            fail("At this point we shouldn't encounter this. The given file is correct\n"+ex.toString());
//        }catch(RepositoryException | MalformedQueryException | QueryEvaluationException ex){
//            fail("At this point we shouldn't encounter this. The given SPARQL query is correct\n"+ex.toString());
//        }
//        /* Try importing with a folder that does not exist */
//        try{
//            this.manager.importFiles("a_folder_that_does_not_exist", TestResources.testDataGraphSpaceForUpdates);
//            fail("The given folder does not exist. It should fail here");
//        }catch(DataImportException ex){
//            assertTrue("Successfully caught exception",true);
//        }
//    }
//    
////    @Test
////    public void testSearchDataset(){
//////        try{    //get nothing
//////            List<Struct> retrievedList=this.manager.searchDataset("something_that_does_not_exist", "something_that_does_not_exist", TestResources.testDataGraphspace);
//////            assertNotNull(retrievedList);
//////            assertEquals(0,retrievedList.size());
//////        }catch(QueryExecutionException ex){
//////            fail("At this point we shouldn't encounter this");
//////        }
//////        try{    //get all of them
//////            List<Struct> retrievedList=this.manager.searchDataset("dataset", "own", TestResources.testDataGraphspace);
//////            assertNotNull(retrievedList);
//////            assertEquals(this.structs.size(), retrievedList.size());
//////            for(DirectoryStruct retrievedStruct : retrievedList){
//////                DirectoryStruct expectedStruct=this.structMap.get(retrievedStruct.getDatasetName());
//////                assertNotNull(expectedStruct);
//////                assertEquals(expectedStruct.getAccessMethod(), retrievedStruct.getAccessMethod());
//////                assertEquals(expectedStruct.getContactPoint(), retrievedStruct.getContactPoint());
//////                assertEquals(expectedStruct.getContributorName(), retrievedStruct.getContributorName());
//////                assertEquals(expectedStruct.getContributorURI(), retrievedStruct.getContributorURI());
//////                assertEquals(expectedStruct.getCuratorName(), retrievedStruct.getCuratorName());
//////                assertEquals(expectedStruct.getCuratorURI(), retrievedStruct.getCuratorURI());
//////                assertEquals(expectedStruct.getDatasetName(), retrievedStruct.getDatasetName());
//////                assertEquals(expectedStruct.getDatasetURI(), retrievedStruct.getDatasetURI());
//////                assertEquals(expectedStruct.getKeeperName(), retrievedStruct.getKeeperName());
//////                assertEquals(expectedStruct.getKeeperURI(), retrievedStruct.getKeeperURI());
//////                assertEquals(expectedStruct.getLicense(), retrievedStruct.getLicense());
//////                assertEquals(expectedStruct.getRightsHolder(), retrievedStruct.getRightsHolder());
////////                assertEquals(expectedStruct.getLocation(), retrievedStruct.getLocation());
//////                assertEquals(expectedStruct.getNote(), retrievedStruct.getNote());
//////                assertEquals(expectedStruct.getOwnerName(), retrievedStruct.getOwnerName());
//////                assertEquals(expectedStruct.getOwnerURI(), retrievedStruct.getOwnerURI());
//////                assertEquals(expectedStruct.getParentDataset(), retrievedStruct.getParentDataset());
//////            }
//////        }catch(QueryExecutionException ex){
//////            fail("At this point we shouldn't encounter an exception\n"+ex.toString());
//////        }
////        try{    //get a specific dataset
////            Collections.shuffle(this.structs);
////            DirectoryStruct expectedStruct=this.structs.get(0);
////            List<Struct> retrievedList=this.manager.searchDataset(expectedStruct.getDatasetName(), expectedStruct.getOwnerName(), TestResources.testDataGraphspace);
////            assertNotNull(retrievedList);
////            assertEquals(1, retrievedList.size());
////            DirectoryStruct retrievedStruct=retrievedList.get(0);
////            assertEquals(expectedStruct.getAccessMethod(), retrievedStruct.getAccessMethod());
////            assertEquals(expectedStruct.getContactPoint(), retrievedStruct.getContactPoint());
////            assertTrue(expectedStruct.getContributorNames().containsAll(retrievedStruct.getContributorNames()));
////            assertTrue(retrievedStruct.getContributorNames().containsAll(expectedStruct.getContributorNames()));
////            assertTrue(expectedStruct.getContributorURIs().containsAll(retrievedStruct.getContributorURIs()));
////            assertTrue(retrievedStruct.getContributorURIs().containsAll(expectedStruct.getContributorURIs()));
////            assertEquals(expectedStruct.getCuratorName(), retrievedStruct.getCuratorName());
////            assertEquals(expectedStruct.getCuratorURI(), retrievedStruct.getCuratorURI());
////            assertEquals(expectedStruct.getDatasetName(), retrievedStruct.getDatasetName());
////            assertEquals(expectedStruct.getDatasetURI(), retrievedStruct.getDatasetURI());
////            assertEquals(expectedStruct.getKeeperName(), retrievedStruct.getKeeperName());
////            assertEquals(expectedStruct.getKeeperURI(), retrievedStruct.getKeeperURI());
////            assertEquals(expectedStruct.getRightsHolderURI(), retrievedStruct.getRightsHolderURI());
//////            assertEquals(expectedStruct.getLocation(), retrievedStruct.getLocation());
////            assertEquals(expectedStruct.getNote(), retrievedStruct.getNote());
////            assertEquals(expectedStruct.getOwnerName(), retrievedStruct.getOwnerName());
////            assertEquals(expectedStruct.getOwnerURI(), retrievedStruct.getOwnerURI());
////            assertEquals(expectedStruct.getParentDatasetURI(), retrievedStruct.getParentDatasetURI());
////        }catch(QueryExecutionException ex){
////            fail("At this point we shouldn't encounter an excpetion\n"+ex.toString());
////        }
////    }
//    
//    @Test
//    public void testSearchResource(){
//        try{    //find nothing
//            URI uri=this.repo.getValueFactory().createURI("http://a_resource_that_does_not_exist");
//            List<Triple> retrievedTriples=this.manager.searchResource(uri.stringValue(), TestResources.testDataGraphspace);
//            assertNotNull(retrievedTriples);
//            RepositoryConnection repoConn=this.repo.getConnection();
//            List<Triple> expectedTriples=new ArrayList<>();
//            RepositoryResult<Statement> results1=repoConn.getStatements(uri, null, null, false, this.repo.getValueFactory().createURI(TestResources.testDataGraphspace));
//            RepositoryResult<Statement> results2=repoConn.getStatements(null, null, uri, false, this.repo.getValueFactory().createURI(TestResources.testDataGraphspace));
//            while(results1.hasNext()){
//                Statement res=results1.next();
//                expectedTriples.add(new Triple(res.getSubject().stringValue(), res.getPredicate().stringValue(), res.getObject().stringValue()));
//            }
//            while(results2.hasNext()){
//                Statement res=results2.next();
//                expectedTriples.add(new Triple(res.getSubject().stringValue(), res.getPredicate().stringValue(), res.getObject().stringValue()));
//            }
//            repoConn.close();
//            assertEquals(expectedTriples.size(), retrievedTriples.size());
//        }catch(QueryExecutionException | RepositoryException ex){
//            fail("At this point we shouldn't encounter this");
//        }
//        try{    //find something
//            Collections.shuffle(this.structs);
//            URI uri=this.repo.getValueFactory().createURI(this.structs.get(0).getDatasetURI());
//            logger.info("Trying to find the triples containing the uri \""+uri.stringValue()+"\"");
//            List<Triple> retrievedList=this.manager.searchResource(uri.stringValue(), TestResources.testDataGraphspace);
//            assertNotNull(retrievedList);
//            assertTrue(!retrievedList.isEmpty());
//            RepositoryConnection repoConn=this.repo.getConnection();
//            List<Triple> expectedTriples=new ArrayList<>();
//            RepositoryResult<Statement> results1=repoConn.getStatements(uri, null, null, false, this.repo.getValueFactory().createURI(TestResources.testDataGraphspace));
//            RepositoryResult<Statement> results2=repoConn.getStatements(null, null, uri, false, this.repo.getValueFactory().createURI(TestResources.testDataGraphspace));
//            while(results1.hasNext()){
//                Statement res=results1.next();
//                expectedTriples.add(new Triple(res.getSubject().stringValue(),res.getPredicate().stringValue(),res.getObject().stringValue()));
//            }
//            while(results2.hasNext()){
//                Statement res=results2.next();
//                expectedTriples.add(new Triple(res.getSubject().stringValue(),res.getPredicate().stringValue(),res.getObject().stringValue()));
//            }
//            repoConn.close();
//            assertEquals(expectedTriples.size(), retrievedList.size());
//            logger.info("Found "+retrievedList.size()+" number of triples containing the uri \""+uri.stringValue()+"\"");
//            assertTrue(expectedTriples.containsAll(retrievedList));
//            assertTrue(retrievedList.containsAll(expectedTriples));
//        }catch(QueryExecutionException | RepositoryException ex){
//            fail("At this point we shouldn't encounter this\n"+ex.toString());
//        }
//    }
//    
//    @Test
//    public void testSearchLiteral(){
//        try{    //find nothing
//            List<Triple> emptyList=this.manager.searchLiteral("this is a value that does not exist", TestResources.testDataGraphspace);
//            assertNotNull(emptyList);
//            assertTrue(emptyList.isEmpty());
//        }catch(QueryExecutionException ex){
//            fail("At this point we shouldn't encounter this\n"+ex.toString());
//        }
//        try{    //find something
//            Collections.shuffle(this.structs);
//            String literalValue=this.structs.get(0).getDatasetName();
//            List<Triple> retrievedList=this.manager.searchLiteral(literalValue, TestResources.testDataGraphspace);
//            logger.info("Retrieved "+retrievedList.size()+" results");
//            assertNotNull(retrievedList);
//            assertTrue(!retrievedList.isEmpty());
//            RepositoryConnection repoConn=this.repo.getConnection();
//            RepositoryResult<Statement> results=repoConn.getStatements(null, null, new LiteralImpl(literalValue), false, this.repo.getValueFactory().createURI(TestResources.testDataGraphspace));
//            List<Triple> expectedResults=new ArrayList<>();
//            while(results.hasNext()){
//                Statement res=results.next();
//                expectedResults.add(new Triple(res.getSubject().stringValue(), res.getPredicate().stringValue(), res.getObject().stringValue()));
//            }
//            assertEquals(expectedResults.size(), retrievedList.size());
//            assertTrue(expectedResults.containsAll(retrievedList));
//            assertTrue(retrievedList.containsAll(expectedResults));
//            repoConn.close();
//        }catch(QueryExecutionException | RepositoryException ex){
//            fail("At this point we shouldn't encounter this\n"+ex.toString());
//        }
//    }
//    
////    @Test
////    public void testInsertStruct(){
////        int previousCounter=0;
////        try{    //try to import if there are invalid URIs
////            DirectoryStruct struct=new DirectoryStruct().withDatasetURI("http://validDatasetUri")
////                                      .withKeeperURI("invalid URI");
////            RepositoryConnection repoConn=this.repo.getConnection();
////            RepositoryResult<Statement> results=repoConn.getStatements(null, null, null, false, this.repo.getValueFactory().createURI(TestResources.testDataGraphspace));
////            while(results.hasNext()){
////                previousCounter+=1;
////                results.next();
////            }
////            repoConn.close();
////            logger.info("Checking the number of resources before importing the struct: "+previousCounter);
////            this.manager.insertStruct(struct, TestResources.testDataGraphspace);
////            fail("One of the given URIs is invalid. We should encounter a URIValidationException here");
////        }catch(URIValidationException ex){
////            assertTrue("Successfully caught exception",true);
////        }catch(QueryExecutionException | RepositoryException ex){
////            fail("At this point we shouldn't encounter this");
////        }
////        try{    //check that nothing has been imported
////            RepositoryConnection repoConn=this.repo.getConnection();
////            RepositoryResult<Statement> results=repoConn.getStatements(null, null, null, false, this.repo.getValueFactory().createURI(TestResources.testDataGraphspace));
////            int afterCounter=0;
////            while(results.hasNext()){
////                afterCounter+=1;
////                results.next();
////            }
////            repoConn.close();
////            logger.info("Checking the number of resources after the importing of the invalid struct: "+afterCounter); 
////            assertEquals(previousCounter, afterCounter);
////        }catch(RepositoryException ex){
////            fail("At this point we shouldn't encounter this");
////        }
////        try{    //add a new struct with data (due to Sesame-virt bug we cannot test it when giving only some fields)
////            DirectoryStruct newStruct=new DirectoryStruct().withDatasetURI("http://localhost/dataset_new_1")
////                                         .withDatasetName("Dataset New 1")
////                                         .withKeeperURI("http://localhost/keeper_new_1")
////                                         .withKeeperName("Keeper New 1")
////                                         .withCuratorURI("http://locahost/curator_new_1")
////                                         .withCuratorName("Curator New 1")
////                                         .withOwnerURI("http://localhost/owner_new_1")
////                                         .withOwnerName("Owner New 1")
////                                         .withRightsHolderURI("http://localhost/licenseOwner_new_1")
////                                         .withAccessMethod("A New Access Method")
////                                         .withNote("Note new 1")
////                                         .withContributor("http://localhost/contributor1", "contributor 1") //junit tests fail if there are empty values
//////                                         .withLocation("Location New 1")
////                                         .withContactPoint("A New Contact Point")
////                                         .withParentDatasetURI("http://locahost/parent_dataset_new_1");
////            this.manager.insertStruct(newStruct, TestResources.testDataGraphspace);
////            List<Struct> retrievedList=this.manager.searchDataset(newStruct.getDatasetName(), newStruct.getOwnerName(), TestResources.testDataGraphspace);
////            assertTrue(!retrievedList.isEmpty());
////            DirectoryStruct retrievedStruct=retrievedList.get(0);
////            assertEquals(retrievedStruct,newStruct);
////        }catch(QueryExecutionException | URIValidationException ex){
////            fail("At this point we shouldn't encounter this\n"+ex.toString());
////        }
////    }
////    
//    @Test
//    public void testDeleteTriplesHavingResource(){
//        try{    //delete something that does not exist
//            URI uri=this.repo.getValueFactory().createURI("http://locahost_value_that_doesnt_exist");
//            RepositoryConnection repoConn=this.repo.getConnection();
//            RepositoryResult<Statement> results=repoConn.getStatements(uri, null, null, false, this.repo.getValueFactory().createURI(TestResources.testDataGraphspace));
//            RepositoryResult<Statement> results2=repoConn.getStatements(null, null, uri, false, this.repo.getValueFactory().createURI(TestResources.testDataGraphspace));
//            assertTrue(!results.hasNext());
//            results=repoConn.getStatements(null, null, uri, false, this.repo.getValueFactory().createURI(TestResources.testDataGraphspace));
//            assertTrue(!results.hasNext());
//            int beforeCounter=0;
//            results=repoConn.getStatements(null, null, null, false, this.repo.getValueFactory().createURI(TestResources.testDataGraphspace));
//            while(results.hasNext()){
//                beforeCounter+=1;
//                results.next();
//            }
//            assertTrue(beforeCounter>0);        //there are triples before deletions
//            this.manager.deleteTriplesHavingResource(uri.stringValue(), TestResources.testDataGraphspace);
//            int afterCounter=0;
//            results=repoConn.getStatements(null, null, null, false, this.repo.getValueFactory().createURI(TestResources.testDataGraphspace));
//            while(results.hasNext()){
//                afterCounter+=1;
//                results.next();
//            }
//            assertEquals(beforeCounter,afterCounter);        //there are triples before deletions
//            repoConn.close();
//        }catch(QueryExecutionException | RepositoryException ex){
//            fail("At this point we shouldn't encounter this\n"+ex.toString());
//        }
//        try{
//            Collections.shuffle(structs);
//            URI uri=this.repo.getValueFactory().createURI(structs.get(0).getDatasetURI());
//            logger.debug("Removing all the resources with URI \""+uri.stringValue()+"\"");
//            List<Triple> triplesList=this.manager.searchResource(uri.stringValue(), TestResources.testDataGraphspace);
//            assertTrue(!triplesList.isEmpty());
//            logger.debug("size before: "+triplesList.size());
//            this.manager.deleteTriplesHavingResource(uri.stringValue(), TestResources.testDataGraphspace);
//            triplesList=this.manager.searchResource(uri.stringValue(), TestResources.testDataGraphspace);
//            logger.debug("size after: "+triplesList.size());
//            assertTrue(triplesList.isEmpty());
//        }catch(QueryExecutionException ex){
//            fail("At this point we shouldn't encounter this\n"+ex.toString());
//        }
//        try{    //give invalid resourceURI
//            this.manager.deleteTriplesHavingResource("invalid resource uri", TestResources.testDataGraphspace);
//            fail("The given resource URI is invalid. An exception should be thrown here");
//        }catch(QueryExecutionException ex){
//            assertTrue("Successfully caught excpetion",true);
//        }
//    }
//    
//    @Test
//    public void testDeleteTriplesHavingLiteral(){
//        int beforeCounter=0;
//        try{    //delete nothing
//            String nonExistingLiteralValue="this is a literal value that does not exist";
//            List<Triple> emptyList=this.manager.searchLiteral(nonExistingLiteralValue, TestResources.testDataGraphspace);
//            assertTrue(emptyList.isEmpty());
//            beforeCounter=0;
//            RepositoryConnection repoConn=this.repo.getConnection();
//            RepositoryResult<Statement> results=repoConn.getStatements(null, null, null, false, this.repo.getValueFactory().createURI(TestResources.testDataGraphspace));
//            while(results.hasNext()){
//                beforeCounter+=1;
//                results.next();
//            }
//            this.manager.deleteTriplesHavingLiteral(nonExistingLiteralValue, TestResources.testDataGraphspace);
//            results=repoConn.getStatements(null, null, null, false, this.repo.getValueFactory().createURI(TestResources.testDataGraphspace));
//            int afterCounter=0;
//            while(results.hasNext()){
//                afterCounter+=1;
//                results.next();
//            }
//            repoConn.close();
//            assertEquals(beforeCounter, afterCounter);
//        }catch(QueryExecutionException | RepositoryException ex){
//            fail("At this point we shouldn't encounter this\n"+ex.getMessage());
//        }
//        try{    //remove something
//            Collections.shuffle(this.structs);
//            String litValue=this.structs.get(0).getDatasetName();
//            List<Triple> listBefore=this.manager.searchLiteral(litValue, TestResources.testDataGraphspace);
//            assertTrue(!listBefore.isEmpty());
//            this.manager.deleteTriplesHavingLiteral(litValue, TestResources.testDataGraphspace);
//            List<Triple> listAfter=this.manager.searchLiteral(litValue, TestResources.testDataGraphspace);
//            assertTrue(listAfter.isEmpty());
//        }catch(QueryExecutionException ex){
//            fail("At this point we shouldn't encounter this\n"+ex.toString());
//        }
//    }
//    
//    @Test
//    public void testDeleteTriplesHavingProperty(){
//        try{    //delete something that does not exist (it doesn't remove anything)
//            URI uri=this.repo.getValueFactory().createURI("http://locahost_property_that_doesnt_exist");
//            RepositoryConnection repoConn=this.repo.getConnection();
//            RepositoryResult<Statement> results=repoConn.getStatements(null, uri, null, false, this.repo.getValueFactory().createURI(TestResources.testDataGraphspace));
//            assertTrue(!results.hasNext());
//            int beforeCounter=0;
//            results=repoConn.getStatements(null, null, null, false, this.repo.getValueFactory().createURI(TestResources.testDataGraphspace));
//            while(results.hasNext()){
//                beforeCounter+=1;
//                results.next();
//            }
//            assertTrue(beforeCounter>0);        //there are triples before deletions
//            this.manager.deleteTriplesHavingProperty(uri.stringValue(), TestResources.testDataGraphspace);
//            int afterCounter=0;
//            results=repoConn.getStatements(null, null, null, false, this.repo.getValueFactory().createURI(TestResources.testDataGraphspace));
//            while(results.hasNext()){
//                afterCounter+=1;
//                results.next();
//            }
//            assertEquals(beforeCounter,afterCounter);        //there are triples before deletions
//            repoConn.close();
//        }catch(QueryExecutionException | RepositoryException ex){
//            fail("At this point we shouldn't encounter this\n"+ex.toString());
//        }
//        try{    //remove a property
//            URI uri=this.repo.getValueFactory().createURI(Resources.isIdentifiedBy);
//            RepositoryConnection repoConn=this.repo.getConnection();
//            RepositoryResult<Statement> results=repoConn.getStatements(null, uri, null, false, this.repo.getValueFactory().createURI(TestResources.testDataGraphspace));
//            int beforeCounter=0;
//            while(results.hasNext()){
//                beforeCounter+=1;
//                results.next();
//            }
//            logger.info("Number of triples containing the property to be deleted "+beforeCounter);
//            assertTrue(beforeCounter>0);
//            this.manager.deleteTriplesHavingProperty(uri.stringValue(), TestResources.testDataGraphspace);
//            results=repoConn.getStatements(null, uri, null, false, this.repo.getValueFactory().createURI(TestResources.testDataGraphspace));
//            int afterCounter=0;
//            while(results.hasNext()){
//                afterCounter+=1;
//                results.next();
//            }
//            assertEquals(0, afterCounter);
//            repoConn.close();
//        }catch(QueryExecutionException | RepositoryException ex){
//            fail("At this point we shouldn't encounter this\n"+ex.toString());
//        }
//        try{    //give invalid resourceURI
//            this.manager.deleteTriplesHavingProperty("invalid resource uri", TestResources.testDataGraphspace);
//            fail("The given resource URI is invalid. An exception should be thrown here");
//        }catch(QueryExecutionException ex){
//            assertTrue("Successfully caught excpetion",true);
//        }
//    }
//    
//    @Test
//    public void testDeleteTriplesWithSubject(){
//        try{    
//            Collections.shuffle(this.structs);
//            URI subjUri=this.repo.getValueFactory().createURI(this.structs.get(0).getDatasetURI());
//            RepositoryConnection repoConn=this.repo.getConnection();
//            RepositoryResult<Statement> results=repoConn.getStatements(subjUri, null, null, false, this.repo.getValueFactory().createURI(TestResources.testDataGraphspace));
//            int beforeCounter=0;
//            while(results.hasNext()){
//                beforeCounter+=1;
//                results.next();
//            }
//            logger.info("Number of triples containing the subject to be deleted "+beforeCounter);
//            assertTrue(beforeCounter>0);
//            this.manager.deleteTriples(subjUri.stringValue(), null, null, TestResources.testDataGraphspace);
//            results=repoConn.getStatements(subjUri, null, null, false, this.repo.getValueFactory().createURI(TestResources.testDataGraphspace));
//            int afterCounter=0;
//            while(results.hasNext()){
//                afterCounter+=1;
//                results.next();
//            }
//            assertEquals(0, afterCounter);
//            repoConn.close();
//        }catch(QueryExecutionException | RepositoryException ex){
//            fail("At this point we shouldn't encounter this\n"+ex.toString());
//        }
//    }
//    
//    @Test
//    public void testDeleteTriplesWithProperty(){
//        try{    
//            URI propUri=this.repo.getValueFactory().createURI(Resources.isIdentifiedBy);
//            RepositoryConnection repoConn=this.repo.getConnection();
//            RepositoryResult<Statement> results=repoConn.getStatements(null, propUri, null, false, this.repo.getValueFactory().createURI(TestResources.testDataGraphspace));
//            int beforeCounter=0;
//            while(results.hasNext()){
//                beforeCounter+=1;
//                results.next();
//            }
//            logger.info("Number of triples containing the property to be deleted "+beforeCounter);
//            assertTrue(beforeCounter>0);
//            this.manager.deleteTriples(null, propUri.stringValue(), null, TestResources.testDataGraphspace);
//            results=repoConn.getStatements(null, propUri, null, false, this.repo.getValueFactory().createURI(TestResources.testDataGraphspace));
//            int afterCounter=0;
//            while(results.hasNext()){
//                afterCounter+=1;
//                results.next();
//            }
//            assertEquals(0, afterCounter);
//            repoConn.close();
//        }catch(QueryExecutionException | RepositoryException ex){
//            fail("At this point we shouldn't encounter this\n"+ex.toString());
//        }
//    }
//    
//    @Test
//    public void testDeleteTriplesWithObject(){
//        try{    
//            Collections.shuffle(this.structs);
//            URI objUri=this.repo.getValueFactory().createURI(this.structs.get(0).getKeeperURI());
//            RepositoryConnection repoConn=this.repo.getConnection();
//            RepositoryResult<Statement> results=repoConn.getStatements(null, null, objUri, false, this.repo.getValueFactory().createURI(TestResources.testDataGraphspace));
//            int beforeCounter=0;
//            while(results.hasNext()){
//                beforeCounter+=1;
//                results.next();
//            }
//            logger.info("Number of triples containing the object to be deleted "+beforeCounter);
//            assertTrue(beforeCounter>0);
//            this.manager.deleteTriples(null, null, objUri.stringValue(), TestResources.testDataGraphspace);
//            results=repoConn.getStatements(null, null, objUri, false, this.repo.getValueFactory().createURI(TestResources.testDataGraphspace));
//            int afterCounter=0;
//            while(results.hasNext()){
//                afterCounter+=1;
//                results.next();
//            }
//            assertEquals(0, afterCounter);
//            repoConn.close();
//        }catch(QueryExecutionException | RepositoryException ex){
//            fail("At this point we shouldn't encounter this\n"+ex.toString());
//        }
//    }
//    
//    @Test
//    public void testDeleteAllTriples(){
//        try{    
//            RepositoryConnection repoConn=this.repo.getConnection();
//            RepositoryResult<Statement> results=repoConn.getStatements(null, null, null, false, this.repo.getValueFactory().createURI(TestResources.testDataGraphspace));
//            int beforeCounter=0;
//            while(results.hasNext()){
//                beforeCounter+=1;
//                results.next();
//            }
//            logger.info("Number of triples containing the object to be deleted "+beforeCounter);
//            assertTrue(beforeCounter>0);
//            this.manager.deleteTriples(null, null, null, TestResources.testDataGraphspace);
//            results=repoConn.getStatements(null, null, null, false, this.repo.getValueFactory().createURI(TestResources.testDataGraphspace));
//            int afterCounter=0;
//            while(results.hasNext()){
//                afterCounter+=1;
//                results.next();
//            }
//            assertEquals(0, afterCounter);
//            repoConn.close();
//        }catch(QueryExecutionException | RepositoryException ex){
//            fail("At this point we shouldn't encounter this\n"+ex.toString());
//        }
//    }
//    
//    @Test
//    public void testDeleteResourceWithSubjAndProperty(){
//        try{    //delete something that does not exist (it doesn't remove anything)
//            URI subjUri=this.repo.getValueFactory().createURI("http://locahost_subject_that_doesnt_exist");
//            URI predUri=this.repo.getValueFactory().createURI("http://locahost_property_that_doesnt_exist");
//            URI objUri=this.repo.getValueFactory().createURI("http://locahost_property_that_doesnt_exist");
//            RepositoryConnection repoConn=this.repo.getConnection();
//            RepositoryResult<Statement> results=repoConn.getStatements(subjUri, predUri, null, false, this.repo.getValueFactory().createURI(TestResources.testDataGraphspace));
//            assertTrue(!results.hasNext());     //check that they do not exist
//            int beforeCounter=0;
//            results=repoConn.getStatements(null, null, null, false, this.repo.getValueFactory().createURI(TestResources.testDataGraphspace));
//            while(results.hasNext()){
//                beforeCounter+=1;
//                results.next();
//            }
//            assertTrue(beforeCounter>0);        //there are triples before deletions
//            this.manager.deleteTriples(subjUri.stringValue(), predUri.stringValue(), objUri.stringValue(), TestResources.testDataGraphspace);
//            int afterCounter=0;
//            results=repoConn.getStatements(null, null, null, false, this.repo.getValueFactory().createURI(TestResources.testDataGraphspace));
//            while(results.hasNext()){
//                afterCounter+=1;
//                results.next();
//            }
//            assertEquals(beforeCounter, afterCounter);        //there are the same triples after the deletion
//            repoConn.close();
//        }catch(QueryExecutionException | RepositoryException ex){
//            fail("At this point we shouldn't encounter this\n"+ex.toString());
//        }
//        try{    //remove something
//            Collections.shuffle(this.structs);
//            URI subjUri=this.repo.getValueFactory().createURI(this.structs.get(0).getDatasetURI());
//            URI predUri=this.repo.getValueFactory().createURI(Resources.isIdentifiedBy);
//            RepositoryConnection repoConn=this.repo.getConnection();
//            RepositoryResult<Statement> results=repoConn.getStatements(subjUri, predUri, null, false, this.repo.getValueFactory().createURI(TestResources.testDataGraphspace));
//            int beforeCounter=0;
//            while(results.hasNext()){
//                beforeCounter+=1;
//                results.next();
//            }
//            logger.info("Number of triples containing the property to be deleted "+beforeCounter);
//            assertTrue(beforeCounter>0);
//            results=repoConn.getStatements(subjUri, null, null, false, this.repo.getValueFactory().createURI(TestResources.testDataGraphspace));
//            int sameSubjCounter=0;
//            while(results.hasNext()){
//                sameSubjCounter+=1;
//                results.next();
//            }
//            assertTrue(sameSubjCounter>beforeCounter);   //assert that there are triples having the same subj and other predicates
//            results=repoConn.getStatements(null, predUri, null, false, this.repo.getValueFactory().createURI(TestResources.testDataGraphspace));
//            int samePredCounter=0;
//            while(results.hasNext()){
//                samePredCounter+=1;
//                results.next();
//            }
//            assertTrue(samePredCounter>beforeCounter);  //assert that there are triples having the same predicate and other subjects
//            this.manager.deleteTriples(subjUri.stringValue(), predUri.stringValue(), null, TestResources.testDataGraphspace);
//            results=repoConn.getStatements(subjUri, predUri, null, false, this.repo.getValueFactory().createURI(TestResources.testDataGraphspace));
//            int afterCounter=0;
//            while(results.hasNext()){
//                afterCounter+=1;
//                results.next();
//            }
//            assertEquals(0, afterCounter);  //the specific triples have been deleted
//            results=repoConn.getStatements(subjUri, null, null, false, this.repo.getValueFactory().createURI(TestResources.testDataGraphspace));
//            sameSubjCounter=0;
//            while(results.hasNext()){
//                sameSubjCounter+=1;
//                results.next();
//            }
//            assertTrue(sameSubjCounter>0);   //assert that the triples having the same subj and other predicates haven't been deleted
//            results=repoConn.getStatements(null, predUri, null, false, this.repo.getValueFactory().createURI(TestResources.testDataGraphspace));
//            samePredCounter=0;
//            while(results.hasNext()){
//                samePredCounter+=1;
//                results.next();
//            }
//            assertTrue(samePredCounter>0);  //assert that the triples having the same predicate and other subjects haven't been removed
//            repoConn.close();
//        }catch(QueryExecutionException | RepositoryException ex){
//            fail("At this point we shouldn't encounter this\n"+ex.toString());
//        }
//        try{    //giving an invalid subject
//            this.manager.deleteTriples("invalid resource uri","http://this_is_valid_uri", null, TestResources.testDataGraphspace);
//            fail("The given resource URI is invalid. An exception should be thrown here");
//        }catch(QueryExecutionException ex){
//            assertTrue("Successfully caught excpetion",true);
//        }
//        try{    //giving an invalid property
//            this.manager.deleteTriples("http://this_is_valid_uri", "invalid resource uri", null, TestResources.testDataGraphspace);
//            fail("The given resource URI is invalid. An exception should be thrown here");
//        }catch(QueryExecutionException ex){
//            assertTrue("Successfully caught excpetion",true);
//        }
//    }
//    
//    @Test
//    public void testDeleteResourceWithSubjAndPredAndObj(){
//        try{    //remove nothing - by giving uri as object
//            URI uri=this.repo.getValueFactory().createURI("http://localhost_a_uri_that_does_not_exist");
//            RepositoryConnection repoConn=this.repo.getConnection();
//            RepositoryResult<Statement> results=repoConn.getStatements(uri, uri, uri, false, this.repo.getValueFactory().createURI(TestResources.testDataGraphspace));
//            assertTrue(!results.hasNext()); //make sure that the triple does not exist
//            results=repoConn.getStatements(null, null, null, false, this.repo.getValueFactory().createURI(TestResources.testDataGraphspace));
//            int beforeCounter=0;
//            while(results.hasNext()){
//                beforeCounter+=1;
//                results.next();
//            }
//            assertTrue(beforeCounter>0);    //assert that there are triples in the graphspace
//            this.manager.deleteTriples(uri.stringValue(), uri.stringValue(), uri.stringValue(), TestResources.testDataGraphspace);
//            results=repoConn.getStatements(null, null, null, false, this.repo.getValueFactory().createURI(TestResources.testDataGraphspace));
//            int afterCounter=0;
//            while(results.hasNext()){
//                afterCounter+=1;
//                results.next();
//            }
//            assertEquals(beforeCounter,afterCounter);    //assert that no triples have been removed
//            repoConn.close();
//        }catch(RepositoryException | QueryExecutionException ex){
//            fail("At this point we shouldn't encounter this\n"+ex.toString());
//        }
//        try{    //remove nothing - by giving literal as object
//            URI uri=this.repo.getValueFactory().createURI("http://localhost_a_uri_that_does_not_exist");
//            Literal lit=this.repo.getValueFactory().createLiteral("a literal that does not exist");
//            RepositoryConnection repoConn=this.repo.getConnection();
//            RepositoryResult<Statement> results=repoConn.getStatements(uri, uri, lit, false, this.repo.getValueFactory().createURI(TestResources.testDataGraphspace));
//            assertTrue(!results.hasNext()); //make sure that the triple does not exist
//            results=repoConn.getStatements(null, null, null, false, this.repo.getValueFactory().createURI(TestResources.testDataGraphspace));
//            int beforeCounter=0;
//            while(results.hasNext()){
//                beforeCounter+=1;
//                results.next();
//            }
//            assertTrue(beforeCounter>0);    //assert that there are triples in the graphspace
//            this.manager.deleteTriples(uri.stringValue(), uri.stringValue(), lit.stringValue(), TestResources.testDataGraphspace);
//            results=repoConn.getStatements(null, null, null, false, this.repo.getValueFactory().createURI(TestResources.testDataGraphspace));
//            int afterCounter=0;
//            while(results.hasNext()){
//                afterCounter+=1;
//                results.next();
//            }
//            assertEquals(beforeCounter,afterCounter);    //assert that no triples have been removed
//            repoConn.close();
//        }catch(RepositoryException | QueryExecutionException ex){
//            fail("At this point we shouldn't encounter this\n"+ex.toString());
//        }
//        try{    //remove something
//            Collections.shuffle(this.structs);
//            URI subjUri=this.repo.getValueFactory().createURI(this.structs.get(0).getDatasetURI());
//            URI predUri=this.repo.getValueFactory().createURI(Resources.isIdentifiedBy);
//            Literal objLit=this.repo.getValueFactory().createLiteral(this.structs.get(0).getDatasetName());
//            RepositoryConnection repoConn=this.repo.getConnection();
//            RepositoryResult<Statement> results=repoConn.getStatements(subjUri, predUri, objLit, false, this.repo.getValueFactory().createURI(TestResources.testDataGraphspace));
//            assertTrue(results.hasNext());  //assert that the triple exists
//            results=repoConn.getStatements(null, null, null, false, this.repo.getValueFactory().createURI(TestResources.testDataGraphspace));
//            int beforeCounter=0;
//            while(results.hasNext()){
//                beforeCounter+=1;
//                results.next();
//            }
//            assertTrue(beforeCounter>0);    //assert that there are triples in the graphspace
//            this.manager.deleteTriples(subjUri.stringValue(), predUri.stringValue(), objLit.stringValue(), TestResources.testDataGraphspace);
//            results=repoConn.getStatements(subjUri, predUri, objLit, false, this.repo.getValueFactory().createURI(TestResources.testDataGraphspace));
//            assertTrue(!results.hasNext());  //assert that the triple has been deleted
//            results=repoConn.getStatements(null, null, null, false, this.repo.getValueFactory().createURI(TestResources.testDataGraphspace));
//            int afterCounter=0;
//            while(results.hasNext()){
//                afterCounter+=1;
//                results.next();
//            }
//            assertEquals(beforeCounter-1,afterCounter);    //assert that only one triple has been removed
//            repoConn.close();
//        }catch(RepositoryException | QueryExecutionException ex){
//            fail("At this point we shouldn't encounter this\n"+ex.toString());
//        }
//        try{    //giving an invalid subject
//            this.manager.deleteTriples("invalid resource uri","http://this_is_valid_uri","http://this_is_valid_uri", TestResources.testDataGraphspace);
//            fail("The given resource URI is invalid. An exception should be thrown here");
//        }catch(QueryExecutionException ex){
//            assertTrue("Successfully caught excpetion",true);
//        }
//        try{    //giving an invalid property
//            this.manager.deleteTriples("http://this_is_valid_uri", "invalid resource uri","http://this_is_valid_uri", TestResources.testDataGraphspace);
//            fail("The given resource URI is invalid. An exception should be thrown here");
//        }catch(QueryExecutionException ex){
//            assertTrue("Successfully caught excpetion",true);
//        }
//    }
//    
////    @Test
////    public void testUpdateResourceDepr(){
////        try{ // giving an invalid subject should throw an excpetion
////            this.manager.updateResource("this is invalid uri", "http://a_valid_uri", "http://a_valid_uri", "http://a_valid_uri", TestResources.testDataGraphspace);
////            fail("The given URI is invalid. We should get an excpetion here");
////        }catch(QueryExecutionException ex){
////            assertTrue("Successfully caught exception",true);
////        }
////        try{ // giving an invalid predicate should throw an excpetion
////            this.manager.updateResource("http://a_valid_uri", "this is invalid uri", "http://a_valid_uri", "http://a_valid_uri", TestResources.testDataGraphspace);
////            fail("The given URI is invalid. We should get an excpetion here");
////        }catch(QueryExecutionException ex){
////            assertTrue("Successfully caught exception",true);
////        }
////        try{    //renaming a dataset name
////            Collections.shuffle(this.structs);
////            DirectoryStruct struct=this.structs.get(0);
////            String subjUri=struct.getDatasetURI();
////            String propUri=Resources.isIdentifiedBy;
////            String objLit=struct.getDatasetName();
////            String newObjLit="This is the new name for the dataset";
////            List<Struct> retList=this.manager.searchDataset(objLit, struct.getOwnerName(), TestResources.testDataGraphspace);
////            assertTrue(!retList.isEmpty()); //assert that there are datasets with this name
////            this.manager.updateResource(subjUri, propUri, objLit, newObjLit, TestResources.testDataGraphspace);
////            retList=this.manager.searchDataset(objLit, struct.getOwnerName(), TestResources.testDataGraphspace);
////            assertTrue(retList.isEmpty());  //make sure that there the dataset (with the old name) is not there
////            retList=this.manager.searchDataset(newObjLit, struct.getOwnerName(), TestResources.testDataGraphspace);
////            assertTrue(!retList.isEmpty());
////            DirectoryStruct updStruct=retList.get(0);
////            assertNotNull(updStruct);
////            assertEquals(struct.getDatasetURI(), updStruct.getDatasetURI());
////            assertEquals(newObjLit, updStruct.getDatasetName());
////            assertEquals(struct.getKeeperURI(), updStruct.getKeeperURI());
////            assertEquals(struct.getKeeperName(), updStruct.getKeeperName());
////            assertEquals(struct.getOwnerURI(), updStruct.getOwnerURI());
////            assertEquals(struct.getOwnerName(), updStruct.getOwnerName());
////            assertEquals(struct.getCuratorURI(), updStruct.getCuratorURI());
////            assertEquals(struct.getCuratorName(), updStruct.getCuratorName());
////        }catch(QueryExecutionException ex){
////            fail("At this point we shouldn't encounter this\n"+ex.toString());
////        }
////    }
//    
//    @Test
//    public void testUpdateResourceGeneric(){
//        try{    //giving an invalid URI as subject
//            this.manager.updateResource("this is invalid uri", "http://a_valid_uri", ResourceType.SUBJECT, TestResources.testDataGraphspace);
//            fail("The given uri is invalid. We should get an exception here.");
//        }catch(QueryExecutionException ex){
//            assertTrue("Successfully caught exception",true);
//        }
//        try{    //giving an invalid URI as subject
//            this.manager.updateResource("http://a_valid_uri","this is invalid uri", ResourceType.SUBJECT, TestResources.testDataGraphspace);
//            fail("The given uri is invalid. We should get an exception here.");
//        }catch(QueryExecutionException ex){
//            assertTrue("Successfully caught exception",true);
//        }
//        try{    //giving an invalid URI as predicate
//            this.manager.updateResource("this is invalid uri", "http://a_valid_uri", ResourceType.PREDICATE, TestResources.testDataGraphspace);
//            fail("The given uri is invalid. We should get an exception here.");
//        }catch(QueryExecutionException ex){
//            assertTrue("Successfully caught exception",true);
//        }
//        try{    //giving an invalid URI as predicate
//            this.manager.updateResource("http://a_valid_uri","this is invalid uri", ResourceType.PREDICATE, TestResources.testDataGraphspace);
//            fail("The given uri is invalid. We should get an exception here.");
//        }catch(QueryExecutionException ex){
//            assertTrue("Successfully caught exception",true);
//        }
//        try{    //giving an invalid URI as object
//            this.manager.updateResource("this is invalid uri", "http://a_valid_uri", ResourceType.OBJECT, TestResources.testDataGraphspace);
//            fail("The given uri is invalid. We should get an exception here.");
//        }catch(QueryExecutionException ex){
//            assertTrue("Successfully caught exception",true);
//        }
//        try{    //giving an invalid URI as object
//            this.manager.updateResource("http://a_valid_uri","this is invalid uri", ResourceType.OBJECT, TestResources.testDataGraphspace);
//            fail("The given uri is invalid. We should get an exception here.");
//        }catch(QueryExecutionException ex){
//            assertTrue("Successfully caught exception",true);
//        }
//        try{    //updating a subject
//            URI oldUri=this.repo.getValueFactory().createURI(this.structs.get(0).getDatasetURI());
//            URI newUri=this.repo.getValueFactory().createURI("http://locahost/dataset/new_uri_for_dataset");
//            RepositoryConnection repoConn=this.repo.getConnection();
//            RepositoryResult<Statement> results=repoConn.getStatements(null, null, null, false, this.repo.getValueFactory().createURI(TestResources.testDataGraphspace));
//            int allCount=0;
//            while(results.hasNext()){
//                allCount+=1;
//                results.next();
//            }
//            assertTrue(allCount>0);   //assert that there are triples in the graph
//            results=repoConn.getStatements(oldUri, null, null, false, this.repo.getValueFactory().createURI(TestResources.testDataGraphspace));
//            int oldUrisCount=0;
//            while(results.hasNext()){
//                oldUrisCount+=1;
//                results.next();
//            }
//            assertTrue(oldUrisCount>0); //assert that there are old uris in the graph
//            results=repoConn.getStatements(newUri, null, null, false, this.repo.getValueFactory().createURI(TestResources.testDataGraphspace));
//            int newUrisCount=0;
//            while(results.hasNext()){
//                newUrisCount+=1;
//                results.next();
//            }
//            assertTrue(newUrisCount==0); //assert that there aren't new uris in the graph
//            this.manager.updateResource(oldUri.stringValue(), newUri.stringValue(), ResourceType.SUBJECT, TestResources.testDataGraphspace);
//            results=repoConn.getStatements(null, null, null, false, this.repo.getValueFactory().createURI(TestResources.testDataGraphspace));
//            int allCountAfter=0;
//            while(results.hasNext()){
//                allCountAfter+=1;
//                results.next();
//            }
//            assertEquals(allCount,allCountAfter);   //assert that the number of triples is the same
//            results=repoConn.getStatements(oldUri, null, null, false, this.repo.getValueFactory().createURI(TestResources.testDataGraphspace));
//            int oldUrisCountAfter=0;
//            while(results.hasNext()){
//                oldUrisCountAfter+=1;
//                results.next();
//            }
//            assertEquals(0,oldUrisCountAfter); //assert that there aren't old uris in the graph
//            results=repoConn.getStatements(newUri, null, null, false, this.repo.getValueFactory().createURI(TestResources.testDataGraphspace));
//            int newUrisCountAfter=0;
//            while(results.hasNext()){
//                newUrisCountAfter+=1;
//                results.next();
//            }
//            assertEquals(oldUrisCount,newUrisCountAfter);   //assert that the new triples are equals with the old ones
//            repoConn.close();            
//        }catch(QueryExecutionException | RepositoryException ex){
//            fail("At this point we shouldn't encounter this\n"+ex.toString());
//        }
//        try{    //updating an object
//            URI oldUri=this.repo.getValueFactory().createURI(this.structs.get(1).getKeeperURI());
//            URI newUri=this.repo.getValueFactory().createURI("http://locahost/dataset/new_keeper_uri_for_dataset");
//            RepositoryConnection repoConn=this.repo.getConnection();
//            RepositoryResult<Statement> results=repoConn.getStatements(null, null, null, false, this.repo.getValueFactory().createURI(TestResources.testDataGraphspace));
//            int allCount=0;
//            while(results.hasNext()){
//                allCount+=1;
//                results.next();
//            }
//            assertTrue(allCount>0);   //assert that there are triples in the graph
//            results=repoConn.getStatements(null, null, oldUri, false, this.repo.getValueFactory().createURI(TestResources.testDataGraphspace));
//            int oldUrisCount=0;
//            while(results.hasNext()){
//                oldUrisCount+=1;
//                results.next();
//            }
//            assertTrue(oldUrisCount>0); //assert that there are old uris in the graph
//            results=repoConn.getStatements(null, null, newUri, false, this.repo.getValueFactory().createURI(TestResources.testDataGraphspace));
//            int newUrisCount=0;
//            while(results.hasNext()){
//                newUrisCount+=1;
//                results.next();
//            }
//            assertTrue(newUrisCount==0); //assert that there aren't new uris in the graph
//            this.manager.updateResource(oldUri.stringValue(), newUri.stringValue(), ResourceType.OBJECT, TestResources.testDataGraphspace);
//            results=repoConn.getStatements(null, null, null, false, this.repo.getValueFactory().createURI(TestResources.testDataGraphspace));
//            int allCountAfter=0;
//            while(results.hasNext()){
//                allCountAfter+=1;
//                results.next();
//            }
//            assertEquals(allCount,allCountAfter);   //assert that the number of triples is the same
//            results=repoConn.getStatements(null, null, oldUri, false, this.repo.getValueFactory().createURI(TestResources.testDataGraphspace));
//            int oldUrisCountAfter=0;
//            while(results.hasNext()){
//                oldUrisCountAfter+=1;
//                results.next();
//            }
//            assertEquals(0,oldUrisCountAfter); //assert that there aren't old uris in the graph
//            results=repoConn.getStatements(null, null, newUri, false, this.repo.getValueFactory().createURI(TestResources.testDataGraphspace));
//            int newUrisCountAfter=0;
//            while(results.hasNext()){
//                newUrisCountAfter+=1;
//                results.next();
//            }
//            assertEquals(oldUrisCount,newUrisCountAfter);   //assert that the new triples are equals with the old ones
//            repoConn.close();            
//        }catch(QueryExecutionException | RepositoryException ex){
//            fail("At this point we shouldn't encounter this\n"+ex.toString());
//        }
//        try{    //updating a literal
//            Literal oldLit=this.repo.getValueFactory().createLiteral(this.structs.get(1).getOwnerName());
//            Literal newLit=this.repo.getValueFactory().createLiteral("this is the new name for the owner of the dataset");
//            RepositoryConnection repoConn=this.repo.getConnection();
//            RepositoryResult<Statement> results=repoConn.getStatements(null, null, null, false, this.repo.getValueFactory().createURI(TestResources.testDataGraphspace));
//            int allCount=0;
//            while(results.hasNext()){
//                allCount+=1;
//                results.next();
//            }
//            assertTrue(allCount>0);   //assert that there are triples in the graph
//            results=repoConn.getStatements(null, null, oldLit, false, this.repo.getValueFactory().createURI(TestResources.testDataGraphspace));
//            int oldUrisCount=0;
//            while(results.hasNext()){
//                oldUrisCount+=1;
//                results.next();
//            }
//            assertTrue(oldUrisCount>0); //assert that there are old uris in the graph
//            results=repoConn.getStatements(null, null, newLit, false, this.repo.getValueFactory().createURI(TestResources.testDataGraphspace));
//            int newUrisCount=0;
//            while(results.hasNext()){
//                newUrisCount+=1;
//                results.next();
//            }
//            assertTrue(newUrisCount==0); //assert that there aren't new uris in the graph
//            this.manager.updateResource(oldLit.stringValue(), newLit.stringValue(), ResourceType.LITERAL, TestResources.testDataGraphspace);
//            results=repoConn.getStatements(null, null, null, false, this.repo.getValueFactory().createURI(TestResources.testDataGraphspace));
//            int allCountAfter=0;
//            while(results.hasNext()){
//                allCountAfter+=1;
//                results.next();
//            }
//            assertEquals(allCount,allCountAfter);   //assert that the number of triples is the same
//            results=repoConn.getStatements(null, null, oldLit, false, this.repo.getValueFactory().createURI(TestResources.testDataGraphspace));
//            int oldUrisCountAfter=0;
//            while(results.hasNext()){
//                oldUrisCountAfter+=1;
//                results.next();
//            }
//            assertEquals(0,oldUrisCountAfter); //assert that there aren't old uris in the graph
//            results=repoConn.getStatements(null, null, newLit, false, this.repo.getValueFactory().createURI(TestResources.testDataGraphspace));
//            int newUrisCountAfter=0;
//            while(results.hasNext()){
//                newUrisCountAfter+=1;
//                results.next();
//            }
//            assertEquals(oldUrisCount,newUrisCountAfter);   //assert that the new triples are equals with the old ones
//            repoConn.close();            
//        }catch(QueryExecutionException | RepositoryException ex){
//            fail("At this point we shouldn't encounter this\n"+ex.toString());
//        }
//        try{    //updating a predicate
//            URI oldUri=this.repo.getValueFactory().createURI(Resources.isIdentifiedBy);
//            URI newUri=this.repo.getValueFactory().createURI("http://locahost/dataset/new_property_label_for_has_appellation");
//            RepositoryConnection repoConn=this.repo.getConnection();
//            RepositoryResult<Statement> results=repoConn.getStatements(null, null, null, false, this.repo.getValueFactory().createURI(TestResources.testDataGraphspace));
//            int allCount=0;
//            while(results.hasNext()){
//                allCount+=1;
//                results.next();
//            }
//            assertTrue(allCount>0);   //assert that there are triples in the graph
//            results=repoConn.getStatements(null, oldUri, null, false, this.repo.getValueFactory().createURI(TestResources.testDataGraphspace));
//            int oldUrisCount=0;
//            while(results.hasNext()){
//                oldUrisCount+=1;
//                results.next();
//            }
//            assertTrue(oldUrisCount>0); //assert that there are old uris in the graph
//            results=repoConn.getStatements(null, newUri, null, false, this.repo.getValueFactory().createURI(TestResources.testDataGraphspace));
//            int newUrisCount=0;
//            while(results.hasNext()){
//                newUrisCount+=1;
//                results.next();
//            }
//            assertTrue(newUrisCount==0); //assert that there aren't new uris in the graph
//            this.manager.updateResource(oldUri.stringValue(), newUri.stringValue(), ResourceType.PREDICATE, TestResources.testDataGraphspace);
//            results=repoConn.getStatements(null, null, null, false, this.repo.getValueFactory().createURI(TestResources.testDataGraphspace));
//            int allCountAfter=0;
//            while(results.hasNext()){
//                allCountAfter+=1;
//                results.next();
//            }
//            assertEquals(allCount,allCountAfter);   //assert that the number of triples is the same
//            results=repoConn.getStatements(null, oldUri, null, false, this.repo.getValueFactory().createURI(TestResources.testDataGraphspace));
//            int oldUrisCountAfter=0;
//            while(results.hasNext()){
//                oldUrisCountAfter+=1;
//                results.next();
//            }
//            assertEquals(0,oldUrisCountAfter); //assert that there aren't old uris in the graph
//            results=repoConn.getStatements(null, newUri, null, false, this.repo.getValueFactory().createURI(TestResources.testDataGraphspace));
//            int newUrisCountAfter=0;
//            while(results.hasNext()){
//                newUrisCountAfter+=1;
//                results.next();
//            }
//            assertEquals(oldUrisCount,newUrisCountAfter);   //assert that the new triples are equals with the old ones
//            repoConn.close();            
//        }catch(QueryExecutionException | RepositoryException ex){
//            fail("At this point we shouldn't encounter this\n"+ex.toString());
//        }
//    }
//    
//    @Test
//    public void testUpdateResource(){
//        try{    //Error case 1: try updating using an invalid uri (original resource)
//            this.manager.updateResource("this is an invalid uri", "http://localhost/validUri", TestResources.testDataGraphspace);
//            fail("The given resource contains an invalid uri. We should encounter an exception here.");
//        }catch(QueryExecutionException ex){
//            assertTrue("Successfully caught exception",true);
//        }
//        try{    //Error case 2: try updating using an invalid uri (new resource)
//            this.manager.updateResource("http://localhost/validUri", "this is an invalid uri", TestResources.testDataGraphspace);
//            fail("The given resource contains an invalid uri. We should encounter an exception here.");
//        }catch(QueryExecutionException ex){
//            assertTrue("Successfully caught exception",true);
//        }
//        try{    // try updating using a uri that does not exist
//            RepositoryConnection repoConn=this.repo.getConnection();
//            URI oldres=this.repo.getValueFactory().createURI("http://localhost/uri_that_does_not_exist");
//            URI newres=this.repo.getValueFactory().createURI("http://localhost/new_uri_that_does_not_exist");
//            RepositoryResult<Statement> results=repoConn.getStatements(oldres, null, null, false, this.repo.getValueFactory().createURI(TestResources.testDataGraphspace));
//            int oldresCnt=0;
//            while(results.hasNext()){
//                oldresCnt+=1;
//                results.next();
//            }
//            results=repoConn.getStatements(null, null, oldres, false, this.repo.getValueFactory().createURI(TestResources.testDataGraphspace));
//            while(results.hasNext()){
//                oldresCnt+=1;
//                results.next();
//            }
//            assertEquals(0, oldresCnt); //assert that the original resource does not exist
//            results=repoConn.getStatements(newres, null, null, false, this.repo.getValueFactory().createURI(TestResources.testDataGraphspace));
//            int newresCnt=0;
//            while(results.hasNext()){
//                newresCnt+=1;
//                results.next();
//            }
//            results=repoConn.getStatements(null, null, newres, false, this.repo.getValueFactory().createURI(TestResources.testDataGraphspace));
//            while(results.hasNext()){
//                newresCnt+=1;
//                results.next();
//            }
//            assertEquals(0, newresCnt); //assert that the new resource does not exist
//            this.manager.updateResource(oldres.stringValue(), newres.stringValue(), TestResources.testDataGraphspace);
//            results=repoConn.getStatements(newres, null, null, false, this.repo.getValueFactory().createURI(TestResources.testDataGraphspace));
//            newresCnt=0;
//            while(results.hasNext()){
//                newresCnt+=1;
//                results.next();
//            }
//            results=repoConn.getStatements(null, null, newres, false, this.repo.getValueFactory().createURI(TestResources.testDataGraphspace));
//            while(results.hasNext()){
//                newresCnt+=1;
//                results.next();
//            }
//            assertEquals(0, newresCnt); //assert that nothing was added (since the old resource did not exist)
//            repoConn.close();
//        }catch(QueryExecutionException | RepositoryException ex){
//            fail("We shouldn't encounter an excpetion here");
//        }
//        try{    // try updating using a uri 
//            RepositoryConnection repoConn=this.repo.getConnection();
//            Collections.shuffle(this.structs);
//            URI oldres=this.repo.getValueFactory().createURI(this.structs.get(0).getDatasetURI());
//            URI newres=this.repo.getValueFactory().createURI("http://www.ics.forth.gr/isl/new_dataset_update_test");
//            RepositoryResult<Statement> results=repoConn.getStatements(oldres, null, null, false, this.repo.getValueFactory().createURI(TestResources.testDataGraphspace));
//            int oldresCnt=0;
//            while(results.hasNext()){
//                oldresCnt+=1;
//                results.next();
//            }
//            results=repoConn.getStatements(null, null, oldres, false, this.repo.getValueFactory().createURI(TestResources.testDataGraphspace));
//            while(results.hasNext()){
//                oldresCnt+=1;
//                results.next();
//            }
//            assertTrue(oldresCnt>0); //assert that the original resource exists
//            results=repoConn.getStatements(newres, null, null, false, this.repo.getValueFactory().createURI(TestResources.testDataGraphspace));
//            int newresCnt=0;
//            while(results.hasNext()){
//                newresCnt+=1;
//                results.next();
//            }
//            results=repoConn.getStatements(null, null, newres, false, this.repo.getValueFactory().createURI(TestResources.testDataGraphspace));
//            while(results.hasNext()){
//                newresCnt+=1;
//                results.next();
//            }
//            assertEquals(0, newresCnt); //assert that the new resource does not exist
//            this.manager.updateResource(oldres.stringValue(), newres.stringValue(), TestResources.testDataGraphspace);
//            int updatedOldresCnt=0;
//            results=repoConn.getStatements(oldres, null, null, false, this.repo.getValueFactory().createURI(TestResources.testDataGraphspace));
//            while(results.hasNext()){
//                updatedOldresCnt+=1;
//                results.next();
//            }
//            results=repoConn.getStatements(null, null, oldres, false, this.repo.getValueFactory().createURI(TestResources.testDataGraphspace));
//            while(results.hasNext()){
//                updatedOldresCnt+=1;
//                results.next();
//            }
//            assertEquals(0,updatedOldresCnt); //assert that the original resource does not exist any more
//            results=repoConn.getStatements(newres, null, null, false, this.repo.getValueFactory().createURI(TestResources.testDataGraphspace));
//            int updatedNewresCnt=0;
//            while(results.hasNext()){
//                updatedNewresCnt+=1;
//                results.next();
//            }
//            results=repoConn.getStatements(null, null, newres, false, this.repo.getValueFactory().createURI(TestResources.testDataGraphspace));
//            while(results.hasNext()){
//                updatedNewresCnt+=1;
//                results.next();
//            }
//            assertTrue(updatedNewresCnt>0);     //assert the the new uri now exists
////            assertEquals(oldresCnt, updatedNewresCnt); //(duplicates are fetched sometimes so this could fail)
//            repoConn.close();
//        }catch(QueryExecutionException | RepositoryException ex){
//            fail("We shouldn't encounter an excpetion here");
//        }
//    }
//    
//    @Test
//    public void testUpdateProperty(){
//        try{    //Error case 1: try updating using an invalid uri (original resource)
//            this.manager.updateProperty("this is an invalid uri", "http://localhost/validUri", TestResources.testDataGraphspace);
//            fail("The given resource contains an invalid uri. We should encounter an exception here.");
//        }catch(QueryExecutionException ex){
//            assertTrue("Successfully caught exception",true);
//        }
//        try{    //Error case 2: try updating using an invalid uri (new resource)
//            this.manager.updateProperty("http://localhost/validUri", "this is an invalid uri", TestResources.testDataGraphspace);
//            fail("The given resource contains an invalid uri. We should encounter an exception here.");
//        }catch(QueryExecutionException ex){
//            assertTrue("Successfully caught exception",true);
//        }
//        try{    // try updating using a uri that does not exist
//            RepositoryConnection repoConn=this.repo.getConnection();
//            URI oldres=this.repo.getValueFactory().createURI("http://localhost/uri_that_does_not_exist");
//            URI newres=this.repo.getValueFactory().createURI("http://localhost/new_uri_that_does_not_exist");
//            RepositoryResult<Statement> results=repoConn.getStatements(null, oldres, null, false, this.repo.getValueFactory().createURI(TestResources.testDataGraphspace));
//            int oldresCnt=0;
//            while(results.hasNext()){
//                oldresCnt+=1;
//                results.next();
//            }
//            assertEquals(0, oldresCnt); //assert that the original resource does not exist
//            results=repoConn.getStatements(null, newres, null, false, this.repo.getValueFactory().createURI(TestResources.testDataGraphspace));
//            int newresCnt=0;
//            while(results.hasNext()){
//                newresCnt+=1;
//                results.next();
//            }
//            assertEquals(0, newresCnt); //assert that the new resource does not exist
//            this.manager.updateProperty(oldres.stringValue(), newres.stringValue(), TestResources.testDataGraphspace);
//            results=repoConn.getStatements(null, newres, null, false, this.repo.getValueFactory().createURI(TestResources.testDataGraphspace));
//            newresCnt=0;
//            while(results.hasNext()){
//                newresCnt+=1;
//                results.next();
//            }
//            assertEquals(0, newresCnt); //assert that nothing was added (since the old resource did not exist)
//            repoConn.close();
//        }catch(QueryExecutionException | RepositoryException ex){
//            fail("We shouldn't encounter an excpetion here");
//        }
//        try{    // try updating using a uri 
//            RepositoryConnection repoConn=this.repo.getConnection();
//            Collections.shuffle(this.structs);
//            URI oldres=this.repo.getValueFactory().createURI(Resources.isIdentifiedBy);
//            URI newres=this.repo.getValueFactory().createURI("http://www.ics.forth.gr/isl/new_property_update_test");
//            RepositoryResult<Statement> results=repoConn.getStatements(null, oldres, null, false, this.repo.getValueFactory().createURI(TestResources.testDataGraphspace));
//            int oldresCnt=0;
//            while(results.hasNext()){
//                oldresCnt+=1;
//                results.next();
//            }
//            assertTrue(oldresCnt>0); //assert that the original resource exists
//            results=repoConn.getStatements(null, newres, null, false, this.repo.getValueFactory().createURI(TestResources.testDataGraphspace));
//            int newresCnt=0;
//            while(results.hasNext()){
//                newresCnt+=1;
//                results.next();
//            }
//            assertEquals(0, newresCnt); //assert that the new resource does not exist
//            this.manager.updateProperty(oldres.stringValue(), newres.stringValue(), TestResources.testDataGraphspace);
//            results=repoConn.getStatements(null, oldres, null, false, this.repo.getValueFactory().createURI(TestResources.testDataGraphspace));
//            int updatedOldresCnt=0;
//            while(results.hasNext()){
//                updatedOldresCnt+=1;
//                results.next();
//            }
//            assertEquals(0,updatedOldresCnt); //assert that the original resource does not exist any more
//            results=repoConn.getStatements(null, newres, null, false, this.repo.getValueFactory().createURI(TestResources.testDataGraphspace));
//            int updatedNewresCnt=0;
//            while(results.hasNext()){
//                updatedNewresCnt+=1;
//                results.next();
//            }
//            assertTrue(updatedNewresCnt>0);     //assert the the new uri now exists
////            assertEquals(oldresCnt, updatedNewresCnt); //(duplicates are fetched sometimes so this could fail)
//            repoConn.close();
//        }catch(QueryExecutionException | RepositoryException ex){
//            fail("We shouldn't encounter an excpetion here");
//        }
//    }
//    
//    @Test
//    public void testUpdateLiteral(){
//        try{    // try updating using a literal value that does not exist
//            RepositoryConnection repoConn=this.repo.getConnection();
//            Literal oldlit=this.repo.getValueFactory().createLiteral("this is a literal value that does not exist");
//            Literal newlit=this.repo.getValueFactory().createLiteral("this is a literal value that will not be inserted");
//            RepositoryResult<Statement> results=repoConn.getStatements(null, null, oldlit, false, this.repo.getValueFactory().createURI(TestResources.testDataGraphspace));
//            int oldresCnt=0;
//            while(results.hasNext()){
//                oldresCnt+=1;
//                results.next();
//            }
//            assertEquals(0, oldresCnt); //assert that the original literal value does not exist
//            results=repoConn.getStatements(null, null, newlit, false, this.repo.getValueFactory().createURI(TestResources.testDataGraphspace));
//            int newresCnt=0;
//            while(results.hasNext()){
//                newresCnt+=1;
//                results.next();
//            }
//            assertEquals(0, newresCnt); //assert that the new literal value does not exist
//            this.manager.updateLiteral(oldlit.stringValue(), newlit.stringValue(), TestResources.testDataGraphspace);
//            results=repoConn.getStatements(null, null, newlit, false, this.repo.getValueFactory().createURI(TestResources.testDataGraphspace));
//            newresCnt=0;
//            while(results.hasNext()){
//                newresCnt+=1;
//                results.next();
//            }
//            assertEquals(0, newresCnt); //assert that nothing was added (since the old literal value did not exist)
//            repoConn.close();
//        }catch(QueryExecutionException | RepositoryException ex){
//            fail("We shouldn't encounter an excpetion here");
//        }
//        try{    // try updating using a uri 
//            RepositoryConnection repoConn=this.repo.getConnection();
//            Collections.shuffle(this.structs);
//            Literal oldlit=this.repo.getValueFactory().createLiteral(this.structs.get(0).getDatasetName());
//            Literal newlit=this.repo.getValueFactory().createLiteral("this is a new name for the dataset - test update literal");
//            RepositoryResult<Statement> results=repoConn.getStatements(null, null, oldlit, true, this.repo.getValueFactory().createURI(TestResources.testDataGraphspace));
//            int oldresCnt=0;
//            while(results.hasNext()){
//                oldresCnt+=1;
//                results.next();
//            }
//            assertTrue(oldresCnt>0); //assert that the original literal value exists
//            results=repoConn.getStatements(null, null, newlit, false, this.repo.getValueFactory().createURI(TestResources.testDataGraphspace));
//            int newresCnt=0;
//            while(results.hasNext()){
//                newresCnt+=1;
//                results.next();
//            }
//            assertEquals(0, newresCnt); //assert that the new literal value does not exist
//            this.manager.updateLiteral(oldlit.stringValue(), newlit.stringValue(), TestResources.testDataGraphspace);
//            results=repoConn.getStatements(null, null, oldlit, false, this.repo.getValueFactory().createURI(TestResources.testDataGraphspace));
//            int updatedOldresCnt=0;
//            while(results.hasNext()){
//                updatedOldresCnt+=1;
//                results.next();
//            }
//            assertEquals(0,updatedOldresCnt); //assert that the original literal value does not exist any more
//            results=repoConn.getStatements(null, null, newlit, false, this.repo.getValueFactory().createURI(TestResources.testDataGraphspace));
//            int updatedNewresCnt=0;
//            while(results.hasNext()){
//                updatedNewresCnt+=1;
//                results.next();
//            }
//            assertTrue(updatedNewresCnt>0);     //assert the the new literal value now exists
////            assertEquals(oldresCnt, updatedNewresCnt); //(duplicates are fetched sometimes so this could fail)
//            repoConn.close();
//        }catch(QueryExecutionException | RepositoryException ex){
//            fail("We shouldn't encounter an excpetion here");
//        }
//    }
//    
//    private void initializeRepo(String url, String port, String username, String password) throws RepositoryException, RDFParseException{
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
//                                       .withContributor("http://localhost/contributor1", "contributor1")
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
//                                       .withContributor("http://localhost/contributor1", "contributor1")
//                                       .withContributor("http://locahost/contributor2", "contributor2")
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
//                                        .withContributor("http://localhost/contributor1", "contributor1")
//                                        .withContributor("http://locahost/contributor2", "contributor2")
//                                        .withContributor("http://localhost/contributor3", "contributor3")
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
//            repoConn.clear(this.repo.getValueFactory().createURI(TestResources.testDataGraphspace));
//            
//            for(DirectoryStruct struct : this.structs){
//                repoConn.add(new StringReader(struct.toNtriples()), TestResources.testDataGraphspace,RDFFormat.NTRIPLES,
//                            this.repo.getValueFactory().createURI(TestResources.testDataGraphspace));
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
//    
//    private void clearGraphs() throws RepositoryException{
//        RepositoryConnection repoConn=this.repo.getConnection();
//        repoConn.clear(this.repo.getValueFactory().createURI(TestResources.testDataGraphSpaceForUpdates));
//    }
}