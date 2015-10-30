package eu.lifewatch.test.struct;

import eu.lifewatch.core.model.Pair;
import java.io.IOException;
import java.io.StringReader;
import junit.framework.TestCase;
import eu.lifewatch.core.model.IdentificationStruct;
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
public class IdentificationStructTest extends TestCase{
    private IdentificationStruct struct;
    private Repository repository;
    private final String triplesContext="http://localhost/triples";
    
    public IdentificationStructTest(){
        this.struct=new IdentificationStruct();
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
        String name="dataset name";
        String altName="dataset name using set";
        assertTrue(this.struct.getDatasetTitle().isEmpty());
        this.struct.withDatasetTitle(name);
        assertEquals(name,struct.getDatasetTitle());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        this.struct.setDatasetTitle(altName);
        assertEquals(altName,struct.getDatasetTitle());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
    }
    
    @Test
    public void testIdentificationEventUri(){
        String identificationUri="http://localhost/identificationEvent";
        String altIdentificationUri="http://localhost/identificationEvent_using_set";
        assertTrue(this.struct.getIdentificationEventURI().isEmpty());
        this.struct.withIdentificationEventURI(identificationUri);
        assertEquals(identificationUri, struct.getIdentificationEventURI());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        this.struct.setIdentificationEventURI(altIdentificationUri);
        assertEquals(altIdentificationUri, struct.getIdentificationEventURI());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
    }
    
    @Test
    public void testIndividualUri(){
        String individualUri="http://localhost/individual";
        String altIndividualUri="http://localhost/individual_using_set";
        assertTrue(this.struct.getIndividualURI().isEmpty());
        this.struct.withIndividualURI(individualUri);
        assertEquals(individualUri, struct.getIndividualURI());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        this.struct.setIndividualURI(altIndividualUri);
        assertEquals(altIndividualUri, struct.getIndividualURI());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
    }
    
    @Test
    public void testLocalityUri(){
        String localityUri="http://localhost/locality";
        String altLocalityUri="http://localhost/locality_using_set";
        assertTrue(this.struct.getLocalityURI().isEmpty());
        this.struct.withLocalityURI(localityUri);
        assertEquals(localityUri, struct.getLocalityURI());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        this.struct.setLocalityURI(altLocalityUri);
        assertEquals(altLocalityUri, struct.getLocalityURI());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
    }
    
    @Test
    public void testLocalityName(){
        String localityName="locality";
        String altLocalityName="locality using set";
        assertTrue(this.struct.getLocalityName().isEmpty());
        this.struct.withLocalityName(localityName);
        assertEquals(localityName, struct.getLocalityName());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        this.struct.setLocalityName(altLocalityName);
        assertEquals(altLocalityName, struct.getLocalityName());
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
    
    @Test
    public void testSpeciesName(){
        String speciesName="species";
        String altSpeciesName="species using set";
        assertTrue(this.struct.getSpeciesName().isEmpty());
        this.struct.withSpeciesName(speciesName);
        assertEquals(speciesName, struct.getSpeciesName());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        this.struct.setSpeciesName(altSpeciesName);
        assertEquals(altSpeciesName, struct.getSpeciesName());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
    }
    
    @Test
    public void testIdentificationReferenceUri(){
        String identificationReferenceUri="http://localhost/identificationReference";
        String altIdentificationReferenceUri="http://localhost/identificationReference_using_set";
        assertTrue(this.struct.getIdentificationReferencesURI().isEmpty());
        this.struct.withIdentificationReferencesURI(identificationReferenceUri);
        assertEquals(identificationReferenceUri, struct.getIdentificationReferencesURI());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        this.struct.setIdentificationReferencesURI(altIdentificationReferenceUri);
        assertEquals(altIdentificationReferenceUri, struct.getIdentificationReferencesURI());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
    }
    
    @Test
    public void testIdentificationReferenceName(){
        String identificationReferenceName="identification reference";
        String altIdentificationReferenceName="identification reference using set";
        assertTrue(this.struct.getIdentificationReferencesName().isEmpty());
        this.struct.withIdentificationReferencesName(identificationReferenceName);
        assertEquals(identificationReferenceName, struct.getIdentificationReferencesName());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        this.struct.setIdentificationReferencesName(altIdentificationReferenceName);
        assertEquals(altIdentificationReferenceName, struct.getIdentificationReferencesName());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
    }
    
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
    public void testContributors(){
        String actor1Uri="http://localhost/actor1";
        String actor1Name="actor 1";
        String actor2Uri="http://localhost/actor2";
        String actor2Name="actor 2";
        assertTrue(this.struct.getActors().isEmpty());
        assertTrue(this.struct.getActorURIs().isEmpty());
        assertTrue(this.struct.getActorNames().isEmpty());
        this.struct.withActor(actor1Uri, actor1Name);
        assertEquals(1,this.struct.getActors().size());
        assertEquals(1,this.struct.getActorURIs().size());
        assertEquals(1,this.struct.getActorNames().size());
        List<Pair> actors=this.struct.getActors();
        assertEquals(actor1Uri, actors.get(0).getKey());
        assertEquals(actor1Name, actors.get(0).getValue());
        this.struct.withActor(actor1Uri, actor1Name);
        assertEquals(1,this.struct.getActors().size());
        assertEquals(1,this.struct.getActorURIs().size());
        assertEquals(1,this.struct.getActorNames().size());
        actors=this.struct.getActors();
        assertEquals(actor1Uri, actors.get(0).getKey());
        assertEquals(actor1Name, actors.get(0).getValue());
        this.struct.withActor(actor2Uri, actor2Name);
        assertEquals(2,this.struct.getActors().size());
        assertEquals(2,this.struct.getActorURIs().size());
        assertEquals(2,this.struct.getActorNames().size());
        assertTrue(this.struct.getActorURIs().containsAll(Arrays.asList(actor1Uri,actor2Uri)));
        assertTrue(Arrays.asList(actor1Uri,actor2Uri).containsAll(this.struct.getActorURIs()));
        assertTrue(this.struct.getActorNames().containsAll(Arrays.asList(actor1Name,actor2Name)));
        assertTrue(Arrays.asList(actor1Name,actor2Name).containsAll(this.struct.getActorNames()));
    }
    
    public void testAllFields(){
        String datasetUri="http://localhost/dataset";
        String datasetName="name of the dataset";
        String identificationEventUri="http://localhost/identificationEvent";
        String individualUri="http://localhost/individual";
        String actor1Uri="http://localhost/actor1";
        String actor1Name="actor 1";
        String actor2Uri="http://localhost/actor2";
        String actor2Name="actor 2";
        String timespan="timespan";
        String localityName="locality name";
        String localityUri="http://localhost/locality";
        String speciesUri="http://localhost/species";
        String speciesName="species";
        String identificationReferenceUri="http://localhost/identificationReference";
        String identificationReferenceName="identification reference";

        
        this.struct.withDatasetURI(datasetUri)
                   .withDatasetTitle(datasetName)
                   .withIdentificationEventURI(identificationEventUri)
                   .withIndividualURI(individualUri)
                   .withActor(actor1Uri, actor1Name)
                   .withActor(actor2Uri, actor2Name)
                   .withTimeSpan(timespan)
                   .withLocalityName(localityName)
                   .withLocalityURI(localityUri)
                   .withSpeciesName(speciesName)
                   .withSpeciesURI(speciesUri)
                   .withIdentificationReferencesURI(identificationReferenceUri)
                   .withIdentificationReferencesName(identificationReferenceName);
                   
        assertEquals(datasetUri,this.struct.getDatasetURI());
        assertEquals(datasetName,this.struct.getDatasetTitle());
        assertTrue(this.struct.getActorURIs().containsAll(Arrays.asList(actor1Uri,actor2Uri)));
        assertTrue(Arrays.asList(actor1Uri,actor2Uri).containsAll(this.struct.getActorURIs()));
        assertTrue(this.struct.getActorNames().containsAll(Arrays.asList(actor1Name,actor2Name)));
        assertTrue(Arrays.asList(actor1Name,actor2Name).containsAll(this.struct.getActorNames()));
        assertEquals(identificationEventUri,this.struct.getIdentificationEventURI());
        assertEquals(individualUri,this.struct.getIndividualURI());
        assertEquals(timespan,this.struct.getTimeSpan());
        assertEquals(localityName,this.struct.getLocalityName());
        assertEquals(localityUri,this.struct.getLocalityURI());
        assertEquals(speciesUri,this.struct.getSpeciesURI());
        assertEquals(speciesName,this.struct.getSpeciesName());
        assertEquals(identificationReferenceUri,this.struct.getIdentificationReferencesURI());
        assertEquals(identificationReferenceName,this.struct.getIdentificationReferencesName());
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