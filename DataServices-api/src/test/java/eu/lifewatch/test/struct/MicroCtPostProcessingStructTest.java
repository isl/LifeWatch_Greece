package eu.lifewatch.test.struct;

import eu.lifewatch.core.model.Pair;
import java.io.IOException;
import java.io.StringReader;
import junit.framework.TestCase;
import eu.lifewatch.core.model.MicroCTPostProcessingStruct;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.sail.SailRepository;
import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.RDFParseException;
import org.openrdf.sail.inferencer.fc.ForwardChainingRDFSInferencer;
import org.openrdf.sail.memory.MemoryStore;

/**
 * @author Nikos Minadakis (minadakn 'at' ics 'dot' forth 'dot' gr)
 * @author Yannis Marketakis (marketak 'at' ics 'dot' forth 'dot' gr)
 */
public class MicroCtPostProcessingStructTest extends TestCase{
    private MicroCTPostProcessingStruct struct;
    private Repository repository;
    private final String triplesContext="http://localhost/triples";
    
    public MicroCtPostProcessingStructTest(){
        this.struct=new MicroCTPostProcessingStruct();
    }

    @Override
    public void setUp(){
        try{
            this.repository=new SailRepository(new ForwardChainingRDFSInferencer(new MemoryStore()));
            this.repository.initialize();
        }catch(RepositoryException ex){
            fail("Failed to create the main-memory repository used for testing\n"+ex.toString());
        }
    }
    
    @Override
    public void tearDown(){
        try{
            RepositoryConnection repoConn=this.repository.getConnection();
            repoConn.clear(this.repository.getValueFactory().createURI(triplesContext));
            repoConn.close();
        }catch(RepositoryException ex){
            fail("Failed to clear the main-memory repository used for testing\n"+ex.toString());
        }
    }
    
    @Test
    public void testDatasetUri(){
        String datasetUri="http://localhost/dataset";
        String altDatasetUri="http://localhost/dataset_using_set";
        assertTrue(this.struct.getDatasetURI().isEmpty());
        this.struct.withDatasetURI(datasetUri);
        assertEquals(datasetUri, struct.getDatasetURI());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        this.struct.setDatasetURI(altDatasetUri);
        assertEquals(altDatasetUri, struct.getDatasetURI());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
    }
    
    @Test
    public void testDatasetName(){
        String datasetName="dataset name";
        String altDatasetName="dataset name using set";
        assertTrue(this.struct.getDatasetTitle().isEmpty());
        this.struct.withDatasetTitle(datasetName);
        assertEquals(datasetName,struct.getDatasetTitle());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        this.struct.setDatasetTitle(altDatasetName);
        assertEquals(altDatasetName,struct.getDatasetTitle());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
    }
    
    @Test
    public void testDescription(){
        String description="description";
        String altDescription="description using set";
        assertTrue(this.struct.getDescription().isEmpty());
        this.struct.withDescription(description);
        assertEquals(description,struct.getDescription());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        this.struct.setDescription(altDescription);
        assertEquals(altDescription,struct.getDescription());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
    }
    
    @Test
    public void testPostProcessingUri(){
        String postProcessingUri="http://localhost/postProcessing";
        String altPostProcessingUri="http://localhost/postProcessing_using_set";
        assertTrue(this.struct.getPostProcessingURI().isEmpty());
        this.struct.withPostProcessingURI(postProcessingUri);
        assertEquals(postProcessingUri, struct.getPostProcessingURI());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        this.struct.setPostProcessingURI(altPostProcessingUri);
        assertEquals(altPostProcessingUri, struct.getPostProcessingURI());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
    }
    
    @Test
    public void testActorUri(){
        String actorUri="http://localhost/actor";
        String altActorUri="http://localhost/actor_using_set";
        assertTrue(this.struct.getActorURI().isEmpty());
        this.struct.withActorURI(actorUri);
        assertEquals(actorUri, struct.getActorURI());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        this.struct.setActorURI(altActorUri);
        assertEquals(altActorUri, struct.getActorURI());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
    }
    
    @Test
    public void testActorName(){
        String actorName="actor";
        String altActorName="actor using set";
        assertTrue(this.struct.getActorName().isEmpty());
        this.struct.withActorName(actorName);
        assertEquals(actorName, struct.getActorName());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        this.struct.setActorName(altActorName);
        assertEquals(altActorName, struct.getActorName());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
    }
    
    @Test
    public void testContributors(){
        String input1Uri="http://localhost/input1";
        String input1Name="input 1";
        String input2Uri="http://localhost/input2";
        String input2Name="input 2";
        assertTrue(this.struct.getInputs().isEmpty());
        assertTrue(this.struct.getInputURIs().isEmpty());
        assertTrue(this.struct.getInputNames().isEmpty());
        this.struct.withInput(input1Uri, input1Name);
        assertEquals(1,this.struct.getInputs().size());
        assertEquals(1,this.struct.getInputURIs().size());
        assertEquals(1,this.struct.getInputNames().size());
        List<Pair> inputs=this.struct.getInputs();
        assertEquals(input1Uri, inputs.get(0).getKey());
        assertEquals(input1Name, inputs.get(0).getValue());
        this.struct.withInput(input1Uri, input1Name);
        assertEquals(1,this.struct.getInputs().size());
        assertEquals(1,this.struct.getInputURIs().size());
        assertEquals(1,this.struct.getInputNames().size());
        inputs=this.struct.getInputs();
        assertEquals(input1Uri, inputs.get(0).getKey());
        assertEquals(input1Name, inputs.get(0).getValue());
        this.struct.withInput(input2Uri, input2Name);
        assertEquals(2,this.struct.getInputs().size());
        assertEquals(2,this.struct.getInputURIs().size());
        assertEquals(2,this.struct.getInputNames().size());
        assertTrue(this.struct.getInputURIs().containsAll(Arrays.asList(input1Uri,input2Uri)));
        assertTrue(Arrays.asList(input1Uri,input2Uri).containsAll(this.struct.getInputURIs()));
        assertTrue(this.struct.getInputNames().containsAll(Arrays.asList(input1Name,input2Name)));
        assertTrue(Arrays.asList(input1Name,input2Name).containsAll(this.struct.getInputNames()));
    }
    
    @Test
    public void testProduct(){
        String product1Uri="http://localhost/Product1";
        String product1Name="Product 1";
        String product2Uri="http://localhost/Product2";
        String product2Name="Product 2";
        assertTrue(this.struct.getProducts().isEmpty());
        assertTrue(this.struct.getProductURIs().isEmpty());
        assertTrue(this.struct.getProductNames().isEmpty());
        this.struct.withProduct(product1Uri, product1Name);
        assertEquals(1,this.struct.getProducts().size());
        assertEquals(1,this.struct.getProductURIs().size());
        assertEquals(1,this.struct.getProductNames().size());
        List<Pair> Products=this.struct.getProducts();
        assertEquals(product1Uri, Products.get(0).getKey());
        assertEquals(product1Name, Products.get(0).getValue());
        this.struct.withProduct(product1Uri, product1Name);
        assertEquals(1,this.struct.getProducts().size());
        assertEquals(1,this.struct.getProductURIs().size());
        assertEquals(1,this.struct.getProductNames().size());
        Products=this.struct.getProducts();
        assertEquals(product1Uri, Products.get(0).getKey());
        assertEquals(product1Name, Products.get(0).getValue());
        this.struct.withProduct(product2Uri, product2Name);
        assertEquals(2,this.struct.getProducts().size());
        assertEquals(2,this.struct.getProductURIs().size());
        assertEquals(2,this.struct.getProductNames().size());
        assertTrue(this.struct.getProductURIs().containsAll(Arrays.asList(product1Uri,product2Uri)));
        assertTrue(Arrays.asList(product1Uri,product2Uri).containsAll(this.struct.getProductURIs()));
        assertTrue(this.struct.getProductNames().containsAll(Arrays.asList(product1Name,product2Name)));
        assertTrue(Arrays.asList(product1Name,product2Name).containsAll(this.struct.getProductNames()));
    }
    
    public void testAllFields(){
        String datasetUri="http://localhost/dataset";
        String datasetName="name of the dataset";
        String postProcessingUri="http://localhost/postProcessing";
        String actorUri="http://localhost/actor";
        String actorName="actor";
        String product1Uri="http://localhost/product1";
        String product1Name="product 1";
        String product2Uri="http://localhost/product2";
        String product2Name="product 2";
        String input1Uri="http://localhost/input1";
        String input1Name="input 1";
        String input2Uri="http://localhost/input2";
        String input2Name="input 2";
        String description="http://localhost/description";
        
        this.struct.withDatasetURI(datasetUri)
                   .withDatasetTitle(datasetName)
                   .withPostProcessingURI(postProcessingUri)
                   .withActorName(actorName)
                   .withActorURI(actorUri)
                   .withInput(input1Uri, input1Name)
                   .withInput(input2Uri, input2Name)
                   .withProduct(product1Uri, product1Name)
                   .withProduct(product2Uri, product2Name)
                   .withDescription(description);
            
        assertEquals(datasetUri,this.struct.getDatasetURI());
        assertEquals(datasetName,this.struct.getDatasetTitle());
        assertEquals(postProcessingUri,this.struct.getPostProcessingURI());
        assertEquals(actorUri,this.struct.getActorURI());
        assertEquals(actorName,this.struct.getActorName());
        assertEquals(description,this.struct.getDescription());
        assertTrue(this.struct.getInputURIs().containsAll(Arrays.asList(input1Uri,input2Uri)));
        assertTrue(Arrays.asList(input1Uri,input2Uri).containsAll(this.struct.getInputURIs()));
        assertTrue(this.struct.getInputNames().containsAll(Arrays.asList(input1Name,input2Name)));
        assertTrue(Arrays.asList(input1Name,input2Name).containsAll(this.struct.getInputNames()));
        assertTrue(this.struct.getProductURIs().containsAll(Arrays.asList(product1Uri,product2Uri)));
        assertTrue(Arrays.asList(product1Uri,product2Uri).containsAll(this.struct.getProductURIs()));
        assertTrue(this.struct.getProductNames().containsAll(Arrays.asList(product1Name,product2Name)));
        assertTrue(Arrays.asList(product1Name,product2Name).containsAll(this.struct.getProductNames()));
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
    }
    
    private boolean importAndValidateTriples(String triples){
        try{
            RepositoryConnection repoConn=this.repository.getConnection();
            repoConn.add(new StringReader(triples), triplesContext, RDFFormat.NTRIPLES, this.repository.getValueFactory().createURI(triplesContext));
            repoConn.close();
            return true;
        }catch(RepositoryException | IOException | RDFParseException ex){
            fail("An error occured while importing triples for testing\n"+ex.toString());
            return false;
        }
    }
}