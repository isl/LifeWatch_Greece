package eu.lifewatch.test.struct;

import eu.lifewatch.core.model.Pair;
import java.io.IOException;
import java.io.StringReader;
import junit.framework.TestCase;
import eu.lifewatch.core.model.GensDatasetStruct;
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
public class GensDatasetStructTest extends TestCase{
    private GensDatasetStruct struct;
    private Repository repository;
    private final String triplesContext="http://localhost/triples";
    
    public GensDatasetStructTest(){
        this.struct=new GensDatasetStruct();
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
    public void testDatasetTitle(){
        String name="dataset title";
        String altName="dataset title using set";
        assertTrue(this.struct.getDatasetTitle().isEmpty());
        this.struct.withDatasetTitle(name);
        assertEquals(name,struct.getDatasetTitle());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        this.struct.setDatasetTitle(altName);
        assertEquals(altName,struct.getDatasetTitle());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
    }
//    
//    @Test
//    public void testIndividualUri(){
//        String individualUri="http://localhost/individual";
//        String altIndividualUri="http://localhost/individual_using_set";
////        assertTrue(this.struct.getIndividualURI().isEmpty());
////        this.struct.withIndividualURI(individualUri);
//      //  assertEquals(individualUri, struct.getIndividualURI());
//        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
//        this.struct.setIndividualURI(altIndividualUri);
//        assertEquals(altIndividualUri, struct.getIndividualURI());
//        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
//    }
    
//    @Test
//    public void testOwnerUri(){
//        String ownerUri="http://localhost/owner";
//        String altOwnerUri="http://localhost/owner_using_set";
//        assertTrue(this.struct.getOwnerURI().isEmpty());
//        this.struct.withOwnerURI(ownerUri);
//        assertEquals(ownerUri, struct.getOwnerURI());
//        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
//        this.struct.setOwnerURI(altOwnerUri);
//        assertEquals(altOwnerUri, struct.getOwnerURI());
//        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
//    }
//    
//    @Test
//    public void testOwnerName(){
//        String ownerName="owner";
//        String altOwnerName="owner using set";
//        assertTrue(this.struct.getOwnerName().isEmpty());
//        this.struct.withOwnerName(ownerName);
//        assertEquals(ownerName, struct.getOwnerName());
//        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
//        this.struct.setOwnerName(altOwnerName);
//        assertEquals(altOwnerName, struct.getOwnerName());
//        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
//    }
//    
//    @Test
//    public void testContactPoint(){
//        String contactPoint="contact point";
//        String altContactPoint="contact point using set";
//        assertTrue(this.struct.getContactPoint().isEmpty());
//        this.struct.withContactPoint(contactPoint);
//        assertEquals(contactPoint, struct.getContactPoint());
//        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
//        this.struct.setContactPoint(altContactPoint);
//        assertEquals(altContactPoint, struct.getContactPoint());
//        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
//    }
//    
    @Test
    public void testTimespan(){
        String timespan="timespan";
        String altTimespan="timespan using set";
        assertTrue(this.struct.getTimeSpan().isEmpty());
        this.struct.withTimeSpan(timespan);
        assertEquals(timespan, struct.getTimeSpan());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        this.struct.setTimeSpan(altTimespan);
        assertEquals(altTimespan, struct.getTimeSpan());
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
        String sampleName="sample";
        String altSampleName="sample using set";
        assertTrue(this.struct.getSampleName().isEmpty());
        this.struct.withSampleName(sampleName);
        assertEquals(sampleName, struct.getSampleName());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        this.struct.setSampleName(altSampleName);
        assertEquals(altSampleName, struct.getSampleName());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
    }
    
    @Test
    public void testSpeciesUri(){
        String speciesUri="http://localhost/species";
        String altSpeciesUri="http://localhost/species_using_set";
        assertTrue(this.struct.getSpeciesURI().isEmpty());
        this.struct.withSpeciesURI(speciesUri);
        assertEquals(speciesUri, struct.getSpeciesURI());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        this.struct.setSpeciesURI(altSpeciesUri);
        assertEquals(altSpeciesUri, struct.getSpeciesURI());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
    }
    
//      @Test
//    public void testDimensionTypeUri(){
//        String dimensionUri="http://localhost/dimensionType";
//        String altDimensionUri="http://localhost/dimensionType_using_set";
//        assertTrue(this.struct.getDimensionTypeURI().isEmpty());
//        this.struct.withDimensionType(dimensionUri);
//        assertEquals(dimensionUri, struct.getDimensionTypeURI());
//        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
//        this.struct.withDimensionType(altDimensionUri);
//        assertEquals(altDimensionUri, struct.getDimensionTypeURI());
//        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
//    }
//    
//    @Test
//    public void testDimensionUri(){
//        String dimensionUri="http://localhost/dimension";
//        String altDimensionUri="http://localhost/dimension_using_set";
//        assertTrue(this.struct.getDimensionURI().isEmpty());
//        this.struct.withDimensionURI(dimensionUri);
//        assertEquals(dimensionUri, struct.getDimensionURI());
//        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
//        this.struct.withDimensionURI(altDimensionUri);
//        assertEquals(altDimensionUri, struct.getDimensionURI());
//        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
//    }
//    
//    @Test
//    public void testDimensionName(){
//        String dimensionName="dimension name";
//        String altDimensionName="dimension name using set";
//        assertTrue(this.struct.getDimensionName().isEmpty());
//        this.struct.withDimensionName(dimensionName);
//        assertEquals(dimensionName,struct.getDimensionName());
//        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
//        this.struct.setDimensionName(altDimensionName);
//        assertEquals(altDimensionName,struct.getDimensionName());
//        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
//    }
//    
//    @Test
//    public void testDimensionValue(){
//        String dimensionValue="dimension value";
//        String altDimensionValue="dimension value using set";
//        assertTrue(this.struct.getDimensionValue().isEmpty());
//        this.struct.withDimensionValue(dimensionValue);
//        assertEquals(dimensionValue,struct.getDimensionValue());
//        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
//        this.struct.setDimensionValue(altDimensionValue);
//        assertEquals(altDimensionValue,struct.getDimensionValue());
//        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
//    }
//    
//    @Test
//    public void testDimensionunit(){
//        String dimensionUnit="dimension unit";
//        String altDimensionUnit="dimension unit using set";
//        assertTrue(this.struct.getDimensionUnit().isEmpty());
//        this.struct.withDimensionUnit(dimensionUnit);
//        assertEquals(dimensionUnit,struct.getDimensionUnit());
//        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
//        this.struct.withDimensionUnit(altDimensionUnit);
//        assertEquals(altDimensionUnit,struct.getDimensionUnit());
//        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
//    }
//    
//    @Test
//    public void testCountryName(){
//        String countryName="country";
//        String altCountryName="country using set";
//        assertTrue(this.struct.getCountryName().isEmpty());
//        this.struct.withCountryName(countryName);
//        assertEquals(countryName, struct.getCountryName());
//        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
//        this.struct.setCountryName(altCountryName);
//        assertEquals(altCountryName, struct.getCountryName());
//        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
//    }
//    
//    @Test
//    public void testCountryUri(){
//        String countryUri="http://localhost/country";
//        String altCountryUri="http://localhost/country_using_set";
//        assertTrue(this.struct.getCountryURI().isEmpty());
//        this.struct.withCountryURI(countryUri);
//        assertEquals(countryUri, struct.getCountryURI());
//        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
//        this.struct.setCountryURI(altCountryUri);
//        assertEquals(altCountryUri, struct.getCountryURI());
//        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
//    }
    
    @Test
    public void testEcosystemName(){
        String ecosystemName="ecosystem";
        String altEcosystemName="ecosystem using set";
        assertTrue(this.struct.getEcosystemName().isEmpty());
        this.struct.withEcosystemName(ecosystemName);
        assertEquals(ecosystemName, struct.getEcosystemName());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        this.struct.setEcosystemName(altEcosystemName);
        assertEquals(altEcosystemName, struct.getEcosystemName());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
    }
    
    @Test
    public void testEcosystemUri(){
        String ecosystemUri="http://localhost/ecosystem";
        String altEcosystemUri="http://localhost/ecosystem_using_set";
        assertTrue(this.struct.getEcosystemURI().isEmpty());
        this.struct.withEcosystemURI(ecosystemUri);
        assertEquals(ecosystemUri, struct.getEcosystemURI());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        this.struct.setEcosystemURI(altEcosystemUri);
        assertEquals(altEcosystemUri, struct.getEcosystemURI());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
    }
    
    @Test
    public void testHabitatName(){
        String habitatName="habitat";
        String altHabitatName="habitat using set";
        assertTrue(this.struct.getHabitatName().isEmpty());
        this.struct.withHabitatName(habitatName);
        assertEquals(habitatName, struct.getHabitatName());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        this.struct.setHabitatName(altHabitatName);
        assertEquals(altHabitatName, struct.getHabitatName());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
    }
    
    @Test
    public void testHabitatUri(){
        String habitatUri="http://localhost/habitat";
        String altHabitatUri="http://localhost/habitat_using_set";
        assertTrue(this.struct.getHabitatURI().isEmpty());
        this.struct.withHabitatURI(habitatUri);
        assertEquals(habitatUri, struct.getHabitatURI());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        this.struct.setHabitatURI(altHabitatUri);
        assertEquals(altHabitatUri, struct.getHabitatURI());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
    }
    
    @Test
    public void testSampleTakingUri(){
        String sampleTakingUri="http://localhost/sampleTaking";
        String altSampleTakingUri="http://localhost/sampleTaking_using_set";
        assertTrue(this.struct.getSampleTakingURI().isEmpty());
        this.struct.withSampleTakingURI(sampleTakingUri);
        assertEquals(sampleTakingUri, struct.getSampleTakingURI());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        this.struct.setSampleTakingURI(altSampleTakingUri);
        assertEquals(altSampleTakingUri, struct.getSampleTakingURI());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
    }
    
    public void testAllFields(){
        String datasetUri="http://localhost/dataset";
        String datasetName="name of the dataset";
        String individualUri="http://localhost/individual";
        String ownerName="owner name";
        String ownerUri="http://localhost/owner";
        String contactPoint="contact point";
        String timespan="timespan";
        String sampleUri="http://localhost/sample";
        String sampleName="sample";
        String speciesUri="http://localhost/species";
        String speciesName="species";
        String dimensionUri="http://localhost/dimension";
        String dimensionTypeUri="http://localhost/dimensionType";
        String dimensionName="dimension name";
        String dimensionValue="dimension value";
        String dimensionUnit="dimension unit";
        String countyName="country name";
        String countryUri="http://localhost/country";
        String ecosystemUri="http://localhost/ecosystem";
        String ecosystemName="ecosystem";
        String habitatName="habitat";
        String habitatUri="http://localhost/habitat";
        String sampleTakingUri="http://localhost/sampleTaking";

       
        this.struct.withDatasetURI(datasetUri)
                   .withDatasetTitle(datasetName)
             //     .withIndividualURI(individualUri)
               //    .withOwnerName(ownerName)
//                   .withOwnerURI(ownerUri)
//                   .withContactPoint(contactPoint)
                   .withTimeSpan(timespan)
                   .withSampleURI(sampleUri)
                   .withSampleName(sampleName)
                   .withSpeciesName(speciesName)
                   .withSpeciesURI(speciesUri)
//                   .withDimensionName(dimensionName)
//                   .withDimensionType(dimensionTypeUri)
//                   .withDimensionURI(dimensionUri)
//                   .withDimensionUnit(dimensionUnit)
//                   .withDimensionValue(dimensionValue)
//                   .withCountryName(countyName)
//                   .withCountryURI(countryUri)
                   .withEcosystemName(ecosystemName)
                   .withEcosystemURI(ecosystemUri)
                   .withHabitatName(habitatName)
                   .withHabitatURI(habitatUri)
                   .withSampleTakingURI(sampleTakingUri);

        assertEquals(datasetUri,this.struct.getDatasetURI());
        assertEquals(datasetName,this.struct.getDatasetTitle());
//        assertEquals(individualUri,this.struct.getIndividualURI());
//        assertEquals(ownerName,this.struct.getOwnerName());
//        assertEquals(ownerUri,this.struct.getOwnerURI());
//        assertEquals(contactPoint,this.struct.getContactPoint());
        assertEquals(timespan,this.struct.getTimeSpan());
        assertEquals(sampleUri,this.struct.getSampleURI());
        assertEquals(sampleName,this.struct.getSampleName());
        assertEquals(speciesName,this.struct.getSpeciesName());
        assertEquals(speciesUri,this.struct.getSpeciesURI());
//        assertEquals(dimensionName,this.struct.getDimensionName());
//        assertEquals(dimensionTypeUri,this.struct.getDimensionTypeURI());
//        assertEquals(dimensionUnit,this.struct.getDimensionUnit());
//        assertEquals(dimensionUri,this.struct.getDimensionURI());
//        assertEquals(dimensionValue,this.struct.getDimensionValue());
//        assertEquals(countyName,this.struct.getCountryName());
//        assertEquals(countryUri,this.struct.getCountryURI());
        assertEquals(ecosystemName,this.struct.getEcosystemName());
        assertEquals(ecosystemUri,this.struct.getEcosystemURI());
        assertEquals(habitatName,this.struct.getHabitatName());
        assertEquals(habitatUri,this.struct.getHabitatURI());
        assertEquals(sampleTakingUri,this.struct.getSampleTakingURI());
       
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