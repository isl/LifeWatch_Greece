package eu.lifewatch.test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import junit.framework.TestCase;
import eu.lifewatch.common.Resources;
import eu.lifewatch.core.api.RepositoryManager;
import eu.lifewatch.core.impl.VirtuosoRepositoryManager;
import eu.lifewatch.exception.DataExportException;
import eu.lifewatch.exception.DataImportException;
import eu.lifewatch.exception.QueryExecutionException;
import eu.lifewatch.exception.RepositoryConnectionException;
import org.junit.Test;
import org.openrdf.model.URI;
import org.openrdf.query.BindingSet;
import org.openrdf.query.MalformedQueryException;
import org.openrdf.query.QueryEvaluationException;
import org.openrdf.query.QueryLanguage;
import org.openrdf.query.TupleQueryResult;
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
public class RepositoryManagerTest extends TestCase{
    RepositoryManager core;
    Repository repo;
    private static boolean setupOccured=false;

    public RepositoryManagerTest(String name){
        super(name);
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
//            this.core=new VirtuosoRepositoryManager(url, port, username, password);
//            if(setupOccured){
//                this.connectToRepo(url, port, username, password);
//            }else{ 
//                this.initializeRepo(url, port, username, password);
//                this.clearOldGraphs();
//                setupOccured=true;
//            }
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
//    public void tearDown(){
//        
//    }
//    
//    @Test
//    public void testQuery(){
//        /*submit a simple query - this should be ok */
//        try{
//            String sparqlQuery="SELECT * FROM <"+TestResources.testDataGraphspace+"> WHERE{?x ?y ?z}";
//            List<BindingSet> results=this.core.query(sparqlQuery);
//            assertNotNull(results);
//            assertFalse(results.isEmpty());
//            for(BindingSet res : results){
//                assertTrue(!res.getBinding("x").getValue().stringValue().isEmpty());
//                assertTrue(!res.getBinding("y").getValue().stringValue().isEmpty());
//                assertTrue(!res.getBinding("z").getValue().stringValue().isEmpty());
//            }
//        }catch(QueryExecutionException ex){
//            fail("At this point we shouldn't encounter this exception - The given SPARQL is correct");
//        }
//        /*submit an erroneous SPARQL query - this should fail*/
//        try{
//            String sparqlWrong="SELECT * FROM WHERE {?x ?y ?z}";
//            this.core.query(sparqlWrong);
//            fail("The given SPARQL query has errors. The method should fail here");
//        }catch(QueryExecutionException ex){
//            assertTrue("Successfully caught exception",true);
//        }
//    }
//    
//    @Test
//    public void testUpdate(){
//        /*submit a simple update query - this should be ok */
//        try{
//            String sparqlQuery="SELECT * FROM <"+TestResources.testDataGraphspace+"> WHERE{<Yannis> rdf:type <Person>}";
//            List<BindingSet> results=this.core.query(sparqlQuery);
//            assertNotNull(results);
//            assertTrue(results.isEmpty());
//            String updateSparqlQuery="INSERT DATA INTO <"+TestResources.testDataGraphspace+"> {"
//                                    +"<Yannis> rdf:type <Person> }";
//            this.core.update(updateSparqlQuery);
//            results=this.core.query(sparqlQuery);
//            assertNotNull(results);
//            assertFalse(results.isEmpty());
//        }catch(QueryExecutionException ex){
//            fail("At this point we shouldn't encounter this exception - The given SPARQL is correct");
//        }
//        /*submit an erroneous SPARQL query - this should fail*/
//        try{
//            String sparqlWrong="INSERT DATA <"+TestResources.testDataGraphspace+"> {"
//                              +"<Yannis> rdf:type <Person>}";
//            this.core.query(sparqlWrong);
//            fail("The given SPARQL query has errors. The method should fail here");
//        }catch(QueryExecutionException ex){
//            assertTrue("Successfully caught exception",true);
//        }
//    }
//    
//    @Test
//    public void testImportData(){
//        /* Try importing a single file */
//        try{
//            RepositoryConnection repoConn=this.repo.getConnection();
//            String sparqlQuery="SELECT COUNT(?x) as ?count FROM <"+TestResources.testDataGraphSpaceForUpdates+"> WHERE{?x ?y ?z}";
//            TupleQueryResult results=repoConn.prepareTupleQuery(QueryLanguage.SPARQL, sparqlQuery).evaluate();
//            assertTrue(results.hasNext());
//            String retVal=results.next().getBinding("count").getValue().stringValue();
//            assertEquals(0,Integer.parseInt(retVal));
//            this.core.importData(new File(TestResources.testResourcesPath+TestResources.testDataImportSingle_Ntriples), RDFFormat.NTRIPLES, TestResources.testDataGraphSpaceForUpdates);
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
//        /* Try importing a multiple files */
//        try{
//            List<File> files=Arrays.asList(new File(TestResources.testResourcesPath+TestResources.testDataImportBulk1_Ntriples),
//                            new File(TestResources.testResourcesPath+TestResources.testDataImportBulk2_Ntriples));
//            
//            RepositoryConnection repoConn=this.repo.getConnection();
//            repoConn.clear(this.repo.getValueFactory().createURI(TestResources.testDataGraphSpaceForUpdates));
//            String sparqlQuery="SELECT COUNT(?x) as ?count FROM <"+TestResources.testDataGraphSpaceForUpdates+"> WHERE{?x ?y ?z}";
//            TupleQueryResult results=repoConn.prepareTupleQuery(QueryLanguage.SPARQL, sparqlQuery).evaluate();
//            assertTrue(results.hasNext());
//            String retVal=results.next().getBinding("count").getValue().stringValue();
//            assertEquals(0,Integer.parseInt(retVal));
//            this.core.importData(files, RDFFormat.NTRIPLES, TestResources.testDataGraphSpaceForUpdates);
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
//        /* Try importing data from erroneous format */
//        try{
//            this.core.importData(new File(TestResources.testResourcesPath+TestResources.testDataImportSingle_Ntriples), RDFFormat.RDFXML, TestResources.testDataGraphSpaceForUpdates);
//            fail("The format of the given file is not correct. It should fail here.");
//        }catch(DataImportException ex){
//            assertTrue("Successfully caught DataImportException",true);
//        }
//        /* Try importing data that do not exist*/
//        try{
//            this.core.importData(new File("a_file_that_does_not_exist.ttl"), RDFFormat.NTRIPLES, TestResources.testDataGraphSpaceForUpdates);
//            fail("The given file does not exist. It should fail here.");
//        }catch(DataImportException ex){
//            assertTrue("Successfully caught DataImportException",true);
//        }
//    }
//    
//    @Test
//    public void testExportdData(){
//        try{    /* export to NTriples format */
//            String nTriplesResults=core.exportData(RDFFormat.NTRIPLES, TestResources.testDataGraphspace);
//            assertFalse(nTriplesResults.isEmpty());
//        }catch(DataExportException ex){
//            fail("At this point we shouldn't encoutner an excpetion");
//        }
//        try{    /* export to RDF format */
//            String rdfResults=core.exportData(RDFFormat.RDFXML, TestResources.testDataGraphspace);
//            assertFalse(rdfResults.isEmpty());
//        }catch(DataExportException ex){
//            fail("At this point we shouldn't encoutner an excpetion");
//        }
//        try{    /* export to Turtle format */
//            String turtleResults=core.exportData(RDFFormat.TURTLE, TestResources.testDataGraphspace);
//            assertFalse(turtleResults.isEmpty());
//        }catch(DataExportException ex){
//            fail("At this point we shouldn't encoutner an excpetion");
//        }
//        try{    /* export to TRIG format */
//            String trigResults=core.exportData(RDFFormat.TRIG, TestResources.testDataGraphspace);
//            assertFalse(trigResults.isEmpty());
//        }catch(DataExportException ex){
//            fail("At this point we shouldn't encoutner an excpetion");
//        }
//        try{    /* export to TRIX format */
//            String trixResults=core.exportData(RDFFormat.TRIX, TestResources.testDataGraphspace);
//            assertFalse(trixResults.isEmpty());
//        }catch(DataExportException ex){
//            fail("At this point we shouldn't encoutner an excpetion");
//        }
//    }
//    
//    private void initializeRepo(String url, String port, String username, String password) throws RepositoryException, RDFParseException{
//        this.connectToRepo(url, port, username, password);
//        try{
//            RepositoryConnection repoConn=this.repo.getConnection();
//            URI graph=this.repo.getValueFactory().createURI(TestResources.testDataGraphspace);
//            repoConn.clear(graph);
//            repoConn.add(ClassLoader.getSystemResourceAsStream(TestResources.testDataFilename), TestResources.testDataGraphspace, RDFFormat.NTRIPLES, graph);
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
//    private void clearOldGraphs() throws RepositoryException{
//        RepositoryConnection repoConn=this.repo.getConnection();
//        URI dataImportGraphSingle=this.repo.getValueFactory().createURI(TestResources.testDataGraphSpaceForUpdates);
//        URI dataImportGraphBulk=this.repo.getValueFactory().createURI(TestResources.testDataGraphSpaceForUpdates);
//        repoConn.clear(dataImportGraphSingle,dataImportGraphBulk);
//    }
}