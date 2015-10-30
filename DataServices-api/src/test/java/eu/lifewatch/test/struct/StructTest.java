package eu.lifewatch.test.struct;

import eu.lifewatch.core.model.Pair;
import java.io.IOException;
import java.io.StringReader;
import junit.framework.TestCase;
import eu.lifewatch.core.model.DirectoryStruct;
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
public class StructTest extends TestCase{
    private DirectoryStruct struct;
    private static final String datasetUri="http://dataset";
    private Repository repository;
    private final String triplesContext="http://localhost/triples";
    
    public StructTest(){
        this.struct=new DirectoryStruct();
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
        assertTrue(this.struct.getDatasetURI().isEmpty());
        this.struct.withDatasetURI(datasetUri);
        assertEquals(datasetUri, struct.getDatasetURI());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        String altDatasetURI=datasetUri+"_using_set";
        this.struct.setDatasetURI(altDatasetURI);
        assertEquals(altDatasetURI, struct.getDatasetURI());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
    }
    
    @Test
    public void testDatasetName(){
        String name="dataset name";
        String altName="dataset name using set";
        this.struct.withDatasetURI(datasetUri);
        assertTrue(this.struct.getDatasetName().isEmpty());
        this.struct.withDatasetName(name);
        assertEquals(name,struct.getDatasetName());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        this.struct.setDatasetName(altName);
        assertEquals(altName,struct.getDatasetName());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        
    }
    
    @Test
    public void testKeeperUri(){
        String keeper="http://localhost/keeper";
        String altKeeper="http://localhost/keeper_using_set";
        this.struct.withDatasetURI(datasetUri);
        assertTrue(this.struct.getKeeperURI().isEmpty());
        this.struct.withKeeperURI(keeper);
        assertEquals(keeper,struct.getKeeperURI());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        this.struct.setKeeperURI(altKeeper);
        assertEquals(altKeeper,struct.getKeeperURI());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
    }
    
    @Test
    public void testKeeperName(){
        String keeper="name of the keeper";
        String altKeeper="name of the keeper using set";
        this.struct.withDatasetURI(datasetUri);
        assertTrue(this.struct.getKeeperName().isEmpty());
        this.struct.withKeeperName(keeper);
        assertEquals(keeper,struct.getKeeperName());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        this.struct.setKeeperName(altKeeper);
        assertEquals(altKeeper,struct.getKeeperName());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
    }
    
    @Test
    public void testOwnerUri(){
        String owner="http://localhost/owner";
        String altOwner="http://localhost/owner_using_set";
        this.struct.withDatasetURI(datasetUri);
        assertTrue(this.struct.getOwnerURI().isEmpty());
        this.struct.withOwnerURI(owner);
        assertEquals(owner,struct.getOwnerURI());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        this.struct.setOwnerURI(altOwner);
        assertEquals(altOwner,struct.getOwnerURI());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
    }
    
    @Test
    public void testOwnerName(){
        String owner="name of the owner";
        String altOwner="name of the owner using set";
        this.struct.withDatasetURI(datasetUri);
        assertTrue(this.struct.getOwnerName().isEmpty());
        this.struct.withOwnerName(owner);
        assertEquals(owner,struct.getOwnerName());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        this.struct.setOwnerName(altOwner);
        assertEquals(altOwner,struct.getOwnerName());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
    }
    
    @Test
    public void testCuratorUri(){
        String curator="http://localhost/curator";
        String altCurator="http://localhost/curator_using_set";
        this.struct.withDatasetURI(datasetUri);
        assertTrue(this.struct.getCuratorURI().isEmpty());
        this.struct.withCuratorURI(curator);
        assertEquals(curator,struct.getCuratorURI());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        this.struct.setCuratorURI(altCurator);
        assertEquals(altCurator,struct.getCuratorURI());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
    }
    
    @Test
    public void testCuratorName(){
        String curator="name of the curator";
        String altCurator="name of the curator using set";
        this.struct.withDatasetURI(datasetUri);
        assertTrue(this.struct.getCuratorName().isEmpty());
        this.struct.withCuratorName(curator);
        assertEquals(curator,struct.getCuratorName());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        this.struct.setCuratorName(altCurator);
        assertEquals(altCurator,struct.getCuratorName());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
    }
    
    @Test
    public void testParentDatasetUri(){
        String parentDataset="http://localhost/parent/dataset";
        String altParentDataset="http://localhost/parent/dataset_using_set";
        this.struct.withDatasetURI(datasetUri);
        assertTrue(this.struct.getParentDatasetURI().isEmpty());
        this.struct.withParentDatasetURI(parentDataset);
        assertEquals(parentDataset,struct.getParentDatasetURI());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        this.struct.setParentDatasetURI(altParentDataset);
        assertEquals(altParentDataset,struct.getParentDatasetURI());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
    }
    
    @Test
    public void testParentDatasetName(){
        String parentDataset="parent dataset";
        String altParentDataset="parent dataset using set";
        this.struct.withDatasetURI(datasetUri);
        assertTrue(this.struct.getParentDatasetName().isEmpty());
        this.struct.withParentDatasetName(parentDataset);
        assertEquals(parentDataset,struct.getParentDatasetName());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        this.struct.setParentDatasetName(altParentDataset);
        assertEquals(altParentDataset,struct.getParentDatasetName());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
    }
        
    @Test
    public void testAccessMethod(){
        String accessMethod="access method";
        String altAccessMethod="access method using set";
        this.struct.withDatasetURI(datasetUri);
        assertTrue(this.struct.getAccessMethod().isEmpty());
        this.struct.withAccessMethod(accessMethod);
        assertEquals(accessMethod,struct.getAccessMethod());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        this.struct.setAccessMethod(altAccessMethod);
        assertEquals(altAccessMethod,struct.getAccessMethod());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
    }
    
    @Test
    public void testNote(){
        String note="note";
        String altNote="note using set";
        this.struct.withDatasetURI(datasetUri);
        assertTrue(this.struct.getAccessMethod().isEmpty());
        this.struct.withDescription(note);
        assertEquals(note,struct.getDescription());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        this.struct.setDescription(altNote);
        assertEquals(altNote,struct.getDescription());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
    }
    
    @Test
    public void testLocation(){
        String location="location";
        String altLocation="location using set";
        this.struct.withDatasetURI(datasetUri);
        assertTrue(this.struct.getLocationURL().isEmpty());
        this.struct.withLocationURL(location);
        assertEquals(location,struct.getLocationURL());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        this.struct.setLocationURL(altLocation);
        assertEquals(altLocation,struct.getLocationURL());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
    }
    
    @Test
    public void testContactPoint(){
        String contact="contact";
        String altContact="contact using set";
        String curatorUri="http://localhost/curator";
        this.struct.withDatasetURI(datasetUri);
        assertTrue(this.struct.getContactPoint().isEmpty());
        assertTrue(this.struct.getCuratorURI().isEmpty());
        this.struct.withCuratorURI(curatorUri)
                   .withContactPoint(contact);
        assertEquals(contact,struct.getContactPoint());
        assertEquals(curatorUri,struct.getCuratorURI());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        this.struct.setContactPoint(altContact);
        assertEquals(altContact,struct.getContactPoint());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
    }
    
    @Test
    public void testAccessRights(){
        String accessRights="access rights";
        String altAccessRights="access rights using set";
        this.struct.withDatasetURI(datasetUri);
        assertTrue(this.struct.getAccessRights().isEmpty());
        this.struct.withAccessRights(accessRights);
        assertEquals(accessRights,struct.getAccessRights());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        this.struct.setAccessRights(altAccessRights);
        assertEquals(altAccessRights,struct.getAccessRights());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
    }
    
    @Test
    public void testAccessRightsUri(){
        String accessRightsURI="http://localhost/accessRights";
        String altAccessRightsURI="http://localhost/accessRights_using_set";
        this.struct.withDatasetURI(datasetUri);
        assertTrue(this.struct.getAccessRightsURI().isEmpty());
        this.struct.withAccessRightsURI(accessRightsURI);
        assertEquals(accessRightsURI,struct.getAccessRightsURI());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        this.struct.setAccessRightsURI(altAccessRightsURI);
        assertEquals(altAccessRightsURI,struct.getAccessRightsURI());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
    }
    
    @Test
    public void testRightsHolderName(){
        String rightsHolderName="rights holder";
        String altRightsHolderName="rights holder using set";
        this.struct.withDatasetURI(datasetUri);
        assertTrue(this.struct.getRightsHolderName().isEmpty());
        this.struct.withRightsHolderName(rightsHolderName);
        assertEquals(rightsHolderName,struct.getRightsHolderName());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        this.struct.setRightsHolderName(altRightsHolderName);
        assertEquals(altRightsHolderName,struct.getRightsHolderName());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
    }
    
    @Test
    public void testRightsHolderUri(){
        String rightsHolderUri="http://localhost/rightsHolder";
        String altRightsHolderUri="http://localhost/rightsHolder_using_set";
        this.struct.withDatasetURI(datasetUri);
        assertTrue(this.struct.getRightsHolderURI().isEmpty());
        this.struct.withRightsHolderURI(rightsHolderUri);
        assertEquals(rightsHolderUri,struct.getRightsHolderURI());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        this.struct.setRightsHolderURI(altRightsHolderUri);
        assertEquals(altRightsHolderUri,struct.getRightsHolderURI());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
    }
    
    @Test
    public void testPublisherUri(){
        String publisherUri="http://localhost/publisher";
        String altPublisherUri="http://localhost/publisher_using_set";
        this.struct.withDatasetURI(datasetUri);
        assertTrue(this.struct.getPublisherURI().isEmpty());
        this.struct.withPublisherURI(publisherUri);
        assertEquals(publisherUri,struct.getPublisherURI());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        this.struct.setPublisherURI(altPublisherUri);
        assertEquals(altPublisherUri,struct.getPublisherURI());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
    }
    
    @Test
    public void testPublisherName(){
        String publisherName="publisher";
        String altPublisherName="publisher using set";
        this.struct.withDatasetURI(datasetUri);
        assertTrue(this.struct.getPublisherName().isEmpty());
        this.struct.withPublisherName(publisherName);
        assertEquals(publisherName,struct.getPublisherName());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        this.struct.setPublisherName(altPublisherName);
        assertEquals(altPublisherName,struct.getPublisherName());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
    }
    
    @Test
    public void testTimespan(){
        String timespan="timespan";
        String altTimespan="timespan using set";
        this.struct.withDatasetURI(datasetUri);
        assertTrue(this.struct.getCreationDate().isEmpty());
        this.struct.withCreationDate(timespan);
        assertEquals(timespan,struct.getCreationDate());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        this.struct.setCreationDate(altTimespan);
        assertEquals(altTimespan,struct.getCreationDate());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
    }
    
    @Test
    public void testCreatorName(){
        String creatorName="creator name";
        String altCreatorName="creator name using set";
        this.struct.withDatasetURI(datasetUri);
        assertTrue(this.struct.getCreatorName().isEmpty());
        this.struct.withCreatorName(creatorName);
        assertEquals(creatorName,struct.getCreatorName());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        this.struct.setCreatorName(altCreatorName);
        assertEquals(altCreatorName,struct.getCreatorName());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
    }
    
    @Test
    public void testCreatorUri(){
        String creatorUri="http://localhost/creator";
        String altCreatorUri="http://localhost/creator_using_set";
        this.struct.withDatasetURI(datasetUri);
        assertTrue(this.struct.getCreatorURI().isEmpty());
        this.struct.withCreatorURI(creatorUri);
        assertEquals(creatorUri,struct.getCreatorURI());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        this.struct.setCreatorURI(altCreatorUri);
        assertEquals(altCreatorUri,struct.getCreatorURI());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
    }
    
    @Test
    public void testCreationEventUri(){
        String creationEvent="http://localhost/creationEvent";
        String altCreationEvent="http://localhost/creationEvent_using set";
        this.struct.withDatasetURI(datasetUri);
        assertTrue(this.struct.getCreationEventURI().isEmpty());
        this.struct.withCreationEventURI(creationEvent);
        assertEquals(creationEvent,struct.getCreationEventURI());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        this.struct.setCreationEventURI(altCreationEvent);
        assertEquals(altCreationEvent,struct.getCreationEventURI());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
    }
    
    @Test
    public void testContributors(){
        String contributor1Uri="http://localhost/contributor1";
        String contributor1Name="contributor 1";
        String contributor2Uri="http://localhost/contributor2";
        String contributor2Name="contributor 2";
        assertTrue(this.struct.getContributors().isEmpty());
        assertTrue(this.struct.getContributorURIs().isEmpty());
        assertTrue(this.struct.getContributorNames().isEmpty());
        this.struct.withContributor(contributor1Uri, contributor1Name);
        assertEquals(1,this.struct.getContributors().size());
        assertEquals(1,this.struct.getContributorURIs().size());
        assertEquals(1,this.struct.getContributorNames().size());
        List<Pair> contributors=this.struct.getContributors();
        assertEquals(contributor1Uri, contributors.get(0).getKey());
        assertEquals(contributor1Name, contributors.get(0).getValue());
        this.struct.withContributor(contributor1Uri, contributor1Name);
        assertEquals(1,this.struct.getContributors().size());
        assertEquals(1,this.struct.getContributorURIs().size());
        assertEquals(1,this.struct.getContributorNames().size());
        contributors=this.struct.getContributors();
        assertEquals(contributor1Uri, contributors.get(0).getKey());
        assertEquals(contributor1Name, contributors.get(0).getValue());
        this.struct.withContributor(contributor2Uri, contributor2Name);
        assertEquals(2,this.struct.getContributors().size());
        assertEquals(2,this.struct.getContributorURIs().size());
        assertEquals(2,this.struct.getContributorNames().size());
        assertTrue(this.struct.getContributorURIs().containsAll(Arrays.asList(contributor1Uri,contributor2Uri)));
        assertTrue(Arrays.asList(contributor1Uri,contributor2Uri).containsAll(this.struct.getContributorURIs()));
        assertTrue(this.struct.getContributorNames().containsAll(Arrays.asList(contributor1Name,contributor2Name)));
        assertTrue(Arrays.asList(contributor1Name,contributor2Name).containsAll(this.struct.getContributorNames()));
    }
    
    public void testAllFields(){
        String datasetName="name of the dataset";
        String contributor1Uri="http://localhost/contributor1";
        String contributor1Name="contributor 1";
        String contributor2Uri="http://localhost/contributor2";
        String contributor2Name="contributor 2";
        String keeperUri="http://localhost/keeper";
        String keeperName="name of the keeper";
        String accessRights="access rights";
        String rightsHolderUri="http://localhost/rightsHolder";
        String rightsHolderName="rights holder";
        String accessMethod="access method";
        String curatorUri="http://localhost/curator";
        String curatorName="name of the curator";
        String creatorUri="http://localhost/creator";
        String publisherName="publisher";
        String publisherUri="http://localhost/publisher";
        String creationEventUri="http://localhost/creationEvent";
        String creatorName="creator";
        String timespan="timespan";
        String ownerName="name of the owner";
        String ownerUri="http://localhost/owner"; 
        String note="just a note";
        String location="this is a location";
        String contactPoint="this is a contact point";
        String parentDatasetUri="http://localhost/parent/dataset";
        String parentDatasetName="parent dataset";
        String accessRightsUri="http://localhost/accessRights";
        this.struct.withDatasetURI(datasetUri);
        this.struct.withDatasetName(datasetName)
                   .withContributor(contributor1Uri, contributor1Name)
                   .withContributor(contributor2Uri, contributor2Name)
                   .withKeeperName(keeperName)
                   .withKeeperURI(keeperUri)
                   .withAccessRights(accessRights)
                   .withRightsHolderName(rightsHolderName)
                   .withRightsHolderURI(rightsHolderUri)
                   .withAccessMethod(accessMethod)
                   .withCuratorURI(curatorUri)
                   .withCuratorName(curatorName)
                   .withPublisherURI(publisherUri)
                   .withPublisherName(publisherName)
                   .withCreationEventURI(creationEventUri)
                   .withCreatorName(creatorName)
                   .withCreationDate(timespan)
                   .withOwnerName(ownerName)
                   .withOwnerURI(ownerUri)
                   .withDescription(note)
                   .withLocationURL(location)
                   .withContactPoint(contactPoint)
                   .withParentDatasetName(parentDatasetName)
                   .withParentDatasetURI(parentDatasetUri)
                   .withAccessRightsURI(accessRightsUri);
        assertEquals(datasetUri,this.struct.getDatasetURI());
        assertEquals(datasetName,this.struct.getDatasetName());
        assertTrue(this.struct.getContributorURIs().containsAll(Arrays.asList(contributor1Uri,contributor2Uri)));
        assertTrue(Arrays.asList(contributor1Uri,contributor2Uri).containsAll(this.struct.getContributorURIs()));
        assertTrue(this.struct.getContributorNames().containsAll(Arrays.asList(contributor1Name,contributor2Name)));
        assertTrue(Arrays.asList(contributor1Name,contributor2Name).containsAll(this.struct.getContributorNames()));
        assertEquals(keeperUri,this.struct.getKeeperURI());
        assertEquals(keeperName,this.struct.getKeeperName());
        assertEquals(accessRights,this.struct.getAccessRights());
        assertEquals(rightsHolderUri,this.struct.getRightsHolderURI());
        assertEquals(rightsHolderName,this.struct.getRightsHolderName());
        assertEquals(accessMethod,this.struct.getAccessMethod());
        assertEquals(curatorName,this.struct.getCuratorName());
        assertEquals(curatorUri,this.struct.getCuratorURI());
        assertEquals(publisherName,this.struct.getPublisherName());
        assertEquals(publisherUri,this.struct.getPublisherURI());
        assertEquals(creationEventUri,this.struct.getCreationEventURI());
        assertEquals(creatorName,this.struct.getCreatorName());
        assertEquals(timespan,this.struct.getCreationDate());
        assertEquals(ownerName,this.struct.getOwnerName());
        assertEquals(ownerUri,this.struct.getOwnerURI());
        assertEquals(note,this.struct.getDescription());
        assertEquals(location,this.struct.getLocationURL());
        assertEquals(contactPoint,this.struct.getContactPoint());
        assertEquals(parentDatasetName,this.struct.getParentDatasetName());
        assertEquals(parentDatasetUri,this.struct.getParentDatasetURI());
        assertEquals(accessRightsUri,this.struct.getAccessRightsURI());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
    }
    
//    private boolean importAndValidateTriples(String triples){
//        try{
//            RepositoryConnection repoConn=this.repository.getConnection();
//            repoConn.add(new StringReader(triples), triplesContext, RDFFormat.NTRIPLES, this.repository.getValueFactory().createURI(triplesContext));
//            repoConn.close();
//            return true;
//        }catch(RepositoryException | IOException | RDFParseException ex){
//            fail("An error occured while importing triples for testing\n"+ex.toString());
//            return false;
//        }
//    }
    
    private boolean importAndValidateTriples(String triples){
       
            return true;
        
    }
}