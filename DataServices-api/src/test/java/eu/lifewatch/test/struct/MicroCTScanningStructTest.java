package eu.lifewatch.test.struct;

import eu.lifewatch.core.model.Pair;
import java.io.IOException;
import java.io.StringReader;
import junit.framework.TestCase;
import eu.lifewatch.core.model.MicroCTScanningStruct;
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
public class MicroCTScanningStructTest extends TestCase{
    private MicroCTScanningStruct struct;
    private Repository repository;
    private final String triplesContext="http://localhost/triples";
    
    public MicroCTScanningStructTest(){
        this.struct=new MicroCTScanningStruct();
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
        assertTrue(this.struct.getDatasetName().isEmpty());
        this.struct.withDatasetName(datasetName);
        assertEquals(datasetName,struct.getDatasetName());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        this.struct.setDatasetName(altDatasetName);
        assertEquals(altDatasetName,struct.getDatasetName());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
    }
    
    @Test
    public void testSpecimenUri(){
        String specimenUri="http://localhost/specimen";
        String altSpecimenUri="http://localhost/specimen_using_set";
        assertTrue(this.struct.getSpecimenURI().isEmpty());
        this.struct.withSpecimenURI(specimenUri);
        assertEquals(specimenUri, struct.getSpecimenURI());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        this.struct.setSpecimenURI(altSpecimenUri);
        assertEquals(altSpecimenUri, struct.getSpecimenURI());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
    }
    
    @Test
    public void testSpecimenName(){
        String specimenName="specimen name";
        String altSpecimenName="specimen name using set";
        assertTrue(this.struct.getSpecimenName().isEmpty());
        this.struct.withSpecimenName(specimenName);
        assertEquals(specimenName,struct.getSpecimenName());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        this.struct.setSpecimenName(altSpecimenName);
        assertEquals(altSpecimenName,struct.getSpecimenName());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
    }
    
    @Test
    public void testEquipmentUri(){
        String equipmentUri="http://localhost/equipment";
        String altSpecimenUri="http://localhost/equipment_using_set";
        assertTrue(this.struct.getEquipmentURI().isEmpty());
        this.struct.withEquipmentURI(equipmentUri);
        assertEquals(equipmentUri, struct.getEquipmentURI());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        this.struct.setSpecimenURI(altSpecimenUri);
        assertEquals(altSpecimenUri, struct.getSpecimenURI());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
    }
    
    @Test
    public void testScanningUri(){
        String scanningUri="http://localhost/scanning";
        String altScanningUri="http://localhost/scanning_using_set";
        assertTrue(this.struct.getScanningURI().isEmpty());
        this.struct.withScanningURI(scanningUri);
        assertEquals(scanningUri, struct.getScanningURI());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        this.struct.setScanningURI(altScanningUri);
        assertEquals(altScanningUri, struct.getScanningURI());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
    }
    
    @Test
    public void testMethodUri(){
        String methodUri="http://localhost/method";
        String altMethodUri="http://localhost/method_using_set";
        assertTrue(this.struct.getMethodURI().isEmpty());
        this.struct.withMethodURI(methodUri);
        assertEquals(methodUri, struct.getMethodURI());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        this.struct.setMethodURI(altMethodUri);
        assertEquals(altMethodUri, struct.getMethodURI());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
    }
    
    @Test
    public void testMethodName(){
        String methodName="method name";
        String altMethodName="method name using set";
        assertTrue(this.struct.getMethodName().isEmpty());
        this.struct.withMethodName(methodName);
        assertEquals(methodName,struct.getMethodName());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        this.struct.setMethodName(altMethodName);
        assertEquals(altMethodName,struct.getMethodName());
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
        String actorName="actor name";
        String altActorName="actor name using set";
        assertTrue(this.struct.getActorName().isEmpty());
        this.struct.withActorName(actorName);
        assertEquals(actorName,struct.getActorName());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        this.struct.setActorName(altActorName);
        assertEquals(altActorName,struct.getActorName());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
    }
    
    @Test
    public void testTimespan(){
        String timespan="timespan";
        String altTimespan="timespan using set";
        assertTrue(this.struct.getTimespan().isEmpty());
        this.struct.withTimespan(timespan);
        assertEquals(timespan,struct.getTimespan());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        this.struct.setTimespan(altTimespan);
        assertEquals(altTimespan,struct.getTimespan());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
    }
    @Test
    public void testDeviceUri(){
        String deviceUri="http://localhost/device";
        String altDeviceUri="http://localhost/device_using_set";
        assertTrue(this.struct.getDeviceURI().isEmpty());
        this.struct.withDeviceURI(deviceUri);
        assertEquals(deviceUri, struct.getDeviceURI());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        this.struct.setDeviceURI(altDeviceUri);
        assertEquals(altDeviceUri, struct.getDeviceURI());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
    }
    
    @Test
    public void testDeviceName(){
        String deviceName="device name";
        String altDeviceName="device name using set";
        assertTrue(this.struct.getDeviceName().isEmpty());
        this.struct.withDeviceName(deviceName);
        assertEquals(deviceName,struct.getDeviceName());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        this.struct.setDeviceName(altDeviceName);
        assertEquals(altDeviceName,struct.getDeviceName());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
    }
    
    @Test
    public void testDeviceType(){
        String deviceType="device type";
        String altDeviceType="device type using set";
        assertTrue(this.struct.getDeviceType().isEmpty());
        this.struct.withDeviceType(deviceType);
        assertEquals(deviceType,struct.getDeviceType());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        this.struct.setDeviceType(altDeviceType);
        assertEquals(altDeviceType,struct.getDeviceType());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
    }
    
    @Test
    public void testDescription(){
        String descriptionType="description type";
        String altDescriptionType="description type using set";
        assertTrue(this.struct.getDescription().isEmpty());
        this.struct.withDescription(descriptionType);
        assertEquals(descriptionType,struct.getDescription());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        this.struct.setDescription(altDescriptionType);
        assertEquals(altDescriptionType,struct.getDescription());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
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
        String specimenUri="http://localhost/specimen";
        String specimenName="specimen";
        String equipmentUri="http://localhost/equipment";
        String methodUri="http://localhost/method";
        String methodName="method";
        String scanningUri="http://localhost/scanning";
        String timespan="timespan";
        String actorUri="http://localhost/actor";
        String actorName="actor";
        String deviceUri="http://localhost/device";
        String deviceName="device name";
        String deviceType="device type";
        String description="description";
        String product1Uri="http://localhost/product1";
        String product1Name="product 1";
        String product2Uri="http://localhost/product2";
        String product2Name="product 2";
        
        this.struct.withDatasetURI(datasetUri)
                   .withDatasetName(datasetName)
                   .withSpecimenName(specimenName)
                   .withSpecimenURI(specimenUri)
                   .withMethodName(methodName)
                   .withMethodURI(methodUri)
                   .withScanningURI(scanningUri)
                   .withTimespan(timespan)
                   .withActorName(actorName)
                   .withActorURI(actorUri)
                   .withEquipmentURI(equipmentUri)
                   .withDeviceName(deviceName)
                   .withDeviceType(deviceType)
                   .withDeviceURI(deviceUri)
                   .withDescription(description)
                   .withProduct(product1Uri, product1Name)
                   .withProduct(product2Uri, product2Name);
     
        assertEquals(datasetUri,this.struct.getDatasetURI());
        assertEquals(datasetName,this.struct.getDatasetName());
        assertEquals(specimenName,this.struct.getSpecimenName());
        assertEquals(specimenUri,this.struct.getSpecimenURI());
        assertEquals(equipmentUri,this.struct.getEquipmentURI());
        assertEquals(methodName,this.struct.getMethodName());
        assertEquals(methodUri,this.struct.getMethodURI());
        assertEquals(scanningUri,this.struct.getScanningURI());
        assertEquals(timespan,this.struct.getTimespan());
        assertEquals(actorName,this.struct.getActorName());
        assertEquals(actorUri,this.struct.getActorURI());
        assertEquals(deviceName,this.struct.getDeviceName());
        assertEquals(deviceType,this.struct.getDeviceType());
        assertEquals(deviceUri,this.struct.getDeviceURI());
        assertEquals(description,this.struct.getDescription());
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