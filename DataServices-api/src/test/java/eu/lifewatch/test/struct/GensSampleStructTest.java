package eu.lifewatch.test.struct;

import eu.lifewatch.core.model.Pair;
import java.io.IOException;
import java.io.StringReader;
import junit.framework.TestCase;
import eu.lifewatch.core.model.GensSampleStruct;
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
public class GensSampleStructTest extends TestCase{
    private GensSampleStruct struct;
    private Repository repository;
    private final String triplesContext="http://localhost/triples";
    
    public GensSampleStructTest(){
        this.struct=new GensSampleStruct();
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
        String dataset="http://localhost/dataset";
        String altDataset="http://localhost/dataset_using_set";
        assertTrue(this.struct.getDatasetURI().isEmpty());
        this.struct.withDatasetURI(dataset);
        assertEquals(dataset, struct.getDatasetURI());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        this.struct.setDatasetURI(altDataset);
        assertEquals(altDataset, struct.getDatasetURI());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
    }
    
    @Test
    public void testDatasetName(){
        String name="dataset name";
        String altName="dataset name using set";
        assertTrue(this.struct.getDatasetName().isEmpty());
        this.struct.withDatasetName(name);
        assertEquals(name,struct.getDatasetName());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        this.struct.setDatasetName(altName);
        assertEquals(altName,struct.getDatasetName());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        
    }
    
    @Test
    public void testSampleUri(){
        String sampleUri="http://localhost/sample";
        String altSampleUri="http://localhost/sample_using_set";
        assertTrue(this.struct.getSampleURI().isEmpty());
        this.struct.withSampleURI(sampleUri);
        assertEquals(sampleUri, struct.getSampleURI());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        this.struct.setSampleURI(altSampleUri);
        assertEquals(altSampleUri, struct.getSampleURI());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
    }
    
    @Test
    public void testSampleName(){
        String sampleName="sample name";
        String altSampleName="sample name using set";
        assertTrue(this.struct.getSampleName().isEmpty());
        this.struct.withSampleName(sampleName);
        assertEquals(sampleName,struct.getSampleName());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        this.struct.setSampleName(altSampleName);
        assertEquals(altSampleName,struct.getSampleName());
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
    public void testTransformedSampleUri(){
        String transSampleUri="http://localhost/transformedSampleUri";
        String altTransSampleUri="http://localhost/transformedSampleUri_using_set";
        assertTrue(this.struct.getTransformedSampleURI().isEmpty());
        this.struct.withTransformedSampleURI(transSampleUri);
        assertEquals(transSampleUri,struct.getTransformedSampleURI());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        this.struct.setTransformedSampleURI(altTransSampleUri);
        assertEquals(altTransSampleUri,struct.getTransformedSampleURI());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
    }
    
    @Test
    public void testTransformedSampleName(){
        String transSample="transformed sample";
        String altTransSample="transformed sample using set";
        assertTrue(this.struct.getTransformedSampleName().isEmpty());
        this.struct.withTransformedSampleName(transSample);
        assertEquals(transSample,struct.getTransformedSampleName());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        this.struct.setTransformedSampleName(altTransSample);
        assertEquals(altTransSample,struct.getTransformedSampleName());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
    }
    
    @Test
    public void testDimensionTypeUri(){
        String dimensionUri="http://localhost/dimensionType";
        String altDimensionUri="http://localhost/dimensionType_using_set";
        assertTrue(this.struct.getDimensionTypeURI().isEmpty());
        this.struct.withDimensionType(dimensionUri);
        assertEquals(dimensionUri, struct.getDimensionTypeURI());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        this.struct.withDimensionType(altDimensionUri);
        assertEquals(altDimensionUri, struct.getDimensionTypeURI());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
    }
    
    @Test
    public void testDimensionUri(){
        String dimensionUri="http://localhost/dimension";
        String altDimensionUri="http://localhost/dimension_using_set";
        assertTrue(this.struct.getDimensionURI().isEmpty());
        this.struct.withDimensionURI(dimensionUri);
        assertEquals(dimensionUri, struct.getDimensionURI());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        this.struct.withDimensionURI(altDimensionUri);
        assertEquals(altDimensionUri, struct.getDimensionURI());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
    }
    
    @Test
    public void testDimensionName(){
        String dimensionName="dimension name";
        String altDimensionName="dimension name using set";
        assertTrue(this.struct.getDimensionName().isEmpty());
        this.struct.withDimensionName(dimensionName);
        assertEquals(dimensionName,struct.getDimensionName());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        this.struct.setDimensionName(altDimensionName);
        assertEquals(altDimensionName,struct.getDimensionName());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
    }
    
    @Test
    public void testDimensionValue(){
        String dimensionValue="dimension value";
        String altDimensionValue="dimension value using set";
        assertTrue(this.struct.getDimensionValue().isEmpty());
        this.struct.withDimensionValue(dimensionValue);
        assertEquals(dimensionValue,struct.getDimensionValue());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        this.struct.setDimensionValue(altDimensionValue);
        assertEquals(altDimensionValue,struct.getDimensionValue());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
    }
    
    @Test
    public void testDimensionUnit(){
        String dimensionUnit="dimension unit";
        String altDimensionUnit="dimension unit using set";
        assertTrue(this.struct.getDimensionUnit().isEmpty());
        this.struct.withDimensionUnit(dimensionUnit);
        assertEquals(dimensionUnit,struct.getDimensionUnit());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        this.struct.withDimensionUnit(altDimensionUnit);
        assertEquals(altDimensionUnit,struct.getDimensionUnit());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
    }
    
    @Test
    public void testDeviceUri(){
        String deviceUri="http://locahost/device";
        String altDeviceUri="http://locahost/device_using_set";
        assertTrue(this.struct.getDeviceURI().isEmpty());
        this.struct.withDeviceURI(deviceUri);
        assertEquals(deviceUri,struct.getDeviceURI());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        this.struct.setDeviceURI(altDeviceUri);
        assertEquals(altDeviceUri,struct.getDeviceURI());
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
    public void testPostProcessingUri(){
        String postProcUri="http://localhost/postProcessing";
        String altPostProcUri="http://localhost/postProcessing_using_set";
        assertTrue(this.struct.getPostProcessingURI().isEmpty());
        this.struct.withPostProcessingURI(postProcUri);
        assertEquals(postProcUri,struct.getPostProcessingURI());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        this.struct.setPostProcessingURI(altPostProcUri);
        assertEquals(altPostProcUri,struct.getPostProcessingURI());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
    }
    
    @Test
    public void testProductUri(){
        String productUri="http://localhost/product";
        String altProductUri="http://localhost/product_using_set";
        assertTrue(this.struct.getProductURI().isEmpty());
        this.struct.withProductURI(productUri);
        assertEquals(productUri,struct.getProductURI());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        this.struct.setProductURI(altProductUri);
        assertEquals(altProductUri,struct.getProductURI());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
    }
    
    @Test
    public void testProductName(){
        String productName="product";
        String altProductName="product using set";
        assertTrue(this.struct.getProductName().isEmpty());
        this.struct.withProductName(productName);
        assertEquals(productName,struct.getProductName());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        this.struct.setProductName(altProductName);
        assertEquals(altProductName,struct.getProductName());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
    }
    
    @Test
    public void testPostProductUri(){
        String productUri="http://localhost/product";
        String altProductUri="http://localhost/product_using_set";
        assertTrue(this.struct.getPostProductURI().isEmpty());
        this.struct.withPostProductURI(productUri);
        assertEquals(productUri,struct.getPostProductURI());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        this.struct.setPostProductURI(altProductUri);
        assertEquals(altProductUri,struct.getPostProductURI());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
    }
    
    @Test
    public void testPostProductName(){
        String productName="product";
        String altProductName="product using set";
        assertTrue(this.struct.getPostProductName().isEmpty());
        this.struct.withPostProductName(productName);
        assertEquals(productName,struct.getPostProductName());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        this.struct.setPostProductName(altProductName);
        assertEquals(altProductName,struct.getPostProductName());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
    }
    
    @Test
    public void testSpeciesUri(){
        String speciesUri="http://localhost/species";
        String altSpeciesUri="http://localhost/species_using_set";
        assertTrue(this.struct.getSpeciesURI().isEmpty());
        this.struct.withSpeciesURI(speciesUri);
        assertEquals(speciesUri,struct.getSpeciesURI());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        this.struct.setSpeciesURI(altSpeciesUri);
        assertEquals(altSpeciesUri,struct.getSpeciesURI());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
    }
    
    @Test
    public void testSpeciesName(){
        String speciesName="species";
        String altSpeciesName="species using set";
        assertTrue(this.struct.getSpeciesName().isEmpty());
        this.struct.withSpeciesName(speciesName);
        assertEquals(speciesName,struct.getSpeciesName());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        this.struct.setSpeciesName(altSpeciesName);
        assertEquals(altSpeciesName,struct.getSpeciesName());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
    }
    
    @Test
    public void testPlaceUri(){
        String placeUri="http://localhost/place";
        String altPlaceUri="http://localhost/place_using_set";
        assertTrue(this.struct.getPlaceURI().isEmpty());
        this.struct.withPlaceURI(placeUri);
        assertEquals(placeUri,struct.getPlaceURI());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        this.struct.setPlaceURI(altPlaceUri);
        assertEquals(altPlaceUri,struct.getPlaceURI());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
    }
    
    @Test
    public void testPlaceName(){
        String placeName="place";
        String altPlaceName="place using set";
        assertTrue(this.struct.getPlaceName().isEmpty());
        this.struct.withPlaceName(placeName);
        assertEquals(placeName,struct.getPlaceName());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        this.struct.setPlaceName(altPlaceName);
        assertEquals(altPlaceName,struct.getPlaceName());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
    }

    @Test
    public void testSequencingUri(){
        String seqUri="http://localhost/sequencing";
        String altSeqUri="http://localhost/sequencing_using_set";
        assertTrue(this.struct.getSequencingURI().isEmpty());
        this.struct.withSequencingURI(seqUri);
        assertEquals(seqUri,struct.getSequencingURI());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        this.struct.setSequencingURI(altSeqUri);
        assertEquals(altSeqUri,struct.getSequencingURI());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
    }

    @Test
    public void testTransformationUri(){
        String transUri="http://localhost/transformation";
        String altTransUri="http://localhost/transformation_using_set";
        assertTrue(this.struct.getTransformationURI().isEmpty());
        this.struct.withTransformationURI(transUri);
        assertEquals(transUri,struct.getTransformationURI());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        this.struct.setTransformationURI(altTransUri);
        assertEquals(altTransUri,struct.getTransformationURI());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
    }
    
    public void testAllFields(){
        String datasetUri="http://localhost/dataset";
        String datasetName="name of the dataset";
        String sampleUri="http://localhost/sample";
        String sampleName="sample";
        String description="description";
        String transformedSampleUri="http://localhost/transformedSample";
        String transformedSampleName="transformed sample";
        String dimensionTypeUri="http://localhost/dimensionType";
        String dimensionUri="http://localhost/dimension";
        String dimensionName="dimension name";
        String dimensionValue="dimension value";
        String dimensionUnit="dimension unit";
        String deviceUri="http://localhost/device";
        String deviceType="device type";
        String deviceName="device name";
        String postProcessingUri="http://localhost/postProcessing";
        String productUri="http://localhost/product";
        String productName="product";
        String speciesUri="http://localhost/species";
        String speciesName="species";
        String postProductUri="http://localhost/postProduct";
        String postProductName="post product";
        String sequencingUri="http://localhost/sequencing";
        String transformationUri="http://localhost/transformation";
        String placeUri="http://localhost/place";
        String placeName="place";
        
        this.struct.withDatasetURI(datasetUri)
                   .withDatasetName(datasetName)
                   .withSampleName(sampleName)
                   .withSampleURI(sampleUri)
                   .withDescription(description)
                   .withTransformedSampleName(transformedSampleName)
                   .withTransformedSampleURI(transformedSampleUri)
                   .withDimensionName(dimensionName)
                   .withDimensionType(dimensionTypeUri)
                   .withDimensionURI(dimensionUri)
                   .withDimensionUnit(dimensionUnit)
                   .withDimensionValue(dimensionValue)
                   .withDeviceURI(deviceUri)
                   .withDeviceName(deviceName)
                   .withDeviceType(deviceType)
                   .withPostProcessingURI(postProcessingUri)
                   .withProductName(productName)
                   .withProductURI(productUri)
                   .withSpeciesName(speciesName)
                   .withSpeciesURI(speciesUri)
                   .withPostProductName(postProductName)
                   .withPostProductURI(postProductUri)
                   .withSequencingURI(sequencingUri)
                   .withTransformationURI(transformationUri)
                   .withPlaceURI(placeUri)
                   .withPlaceName(placeName);
        
        assertEquals(datasetUri,this.struct.getDatasetURI());
        assertEquals(datasetName,this.struct.getDatasetName());
        assertEquals(sampleName,this.struct.getSampleName());
        assertEquals(sampleUri,this.struct.getSampleURI());
        assertEquals(description,this.struct.getDescription());
        assertEquals(transformedSampleName,this.struct.getTransformedSampleName());
        assertEquals(transformedSampleUri,this.struct.getTransformedSampleURI());
        assertEquals(dimensionTypeUri,this.struct.getDimensionTypeURI());
        assertEquals(dimensionUri,this.struct.getDimensionURI());
        assertEquals(dimensionName,this.struct.getDimensionName());
        assertEquals(dimensionValue,this.struct.getDimensionValue());
        assertEquals(dimensionUnit,this.struct.getDimensionUnit());
        assertEquals(deviceUri,this.struct.getDeviceURI());
        assertEquals(deviceName,this.struct.getDeviceName());
        assertEquals(deviceType,this.struct.getDeviceType());
        assertEquals(postProcessingUri,this.struct.getPostProcessingURI());
        assertEquals(productName,this.struct.getProductName());
        assertEquals(productUri,this.struct.getProductURI());
        assertEquals(speciesName,this.struct.getSpeciesName());
        assertEquals(speciesUri,this.struct.getSpeciesURI());
        assertEquals(postProductName,this.struct.getPostProductName());
        assertEquals(postProductUri,this.struct.getPostProductURI());
        assertEquals(sequencingUri,this.struct.getSequencingURI());
        assertEquals(transformationUri,this.struct.getTransformationURI());
        assertEquals(placeName,this.struct.getPlaceName());
        assertEquals(placeUri,this.struct.getPlaceURI());
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