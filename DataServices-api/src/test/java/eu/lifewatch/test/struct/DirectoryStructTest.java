package eu.lifewatch.test.struct;

import eu.lifewatch.core.model.Pair;
import junit.framework.TestCase;
import eu.lifewatch.core.model.DirectoryStruct;
import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;
import java.util.List;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.openrdf.model.impl.URIImpl;
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
public class DirectoryStructTest extends TestCase{
    private DirectoryStruct struct;
    private static final String datasetUri="http://dataset";
    private Repository repository;
    private final String triplesContext="http://localhost/triples";
    private static final Logger logger=Logger.getLogger(DirectoryStructTest.class);
    
    public DirectoryStructTest(){
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
    public void testDatasetID(){
        String id="dataset id";
        String altID="dataset id using set";
        this.struct.withDatasetURI(datasetUri);
        assertTrue(this.struct.getDatasetID().isEmpty());
        this.struct.withDatasetID(id);
        assertEquals(id,struct.getDatasetID());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        this.struct.setDatasetID(altID);
        assertEquals(altID,struct.getDatasetID());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
    }
    
    @Test
    public void testDatasetType(){
        String type="dataset type";
        String altType="dataset type using set";
        this.struct.withDatasetURI(datasetUri);
        assertTrue(this.struct.getDatasetType().isEmpty());
        this.struct.withDatasetType(type);
        assertEquals(type,struct.getDatasetType());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        this.struct.setDatasetType(altType);
        assertEquals(altType,struct.getDatasetType());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
    }
    
    @Test
    public void testLocationURL(){
        String location="http://localhost/parent/location";
        String altLocation="http://localhost/parent/location_using_set";
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
    public void testImageURI(){
        String image="http://localhost/image";
        String altImage="http://localhost/image_using_set";
        this.struct.withDatasetURI(datasetUri);
        assertTrue(this.struct.getImageURI().isEmpty());
        this.struct.withImageURI(image);
        assertEquals(image,struct.getImageURI());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        this.struct.setImageURI(altImage);
        assertEquals(altImage,struct.getImageURI());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
    }
    
    @Test
    public void testImageTitle(){
        String image="image";
        String altImage="image using set";
        this.struct.withDatasetURI(datasetUri);
        assertTrue(this.struct.getImageTitle().isEmpty());
        this.struct.withImageTitle(image);
        assertEquals(image,struct.getImageTitle());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        this.struct.setImageTitle(altImage);
        assertEquals(altImage,struct.getImageTitle());
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
    public void testAccessMethodURI(){
        String accessMethod="http://localhost/accessMethod";
        String altAccessMethod="http://localhost/accessMethod_using_set";
        this.struct.withDatasetURI(datasetUri);
        assertTrue(this.struct.getAccessMethodURI().isEmpty());
        this.struct.withAccessMethodURI(accessMethod);
        assertEquals(accessMethod,struct.getAccessMethodURI());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        this.struct.setAccessMethodURI(altAccessMethod);
        assertEquals(altAccessMethod,struct.getAccessMethodURI());
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
    public void testPublicationEventURI(){
        String publicationEvent="http://localhost/publication_event";
        String altPublicationEvent="http://localhost/publication_event_using_set";
        this.struct.withDatasetURI(datasetUri);
        assertTrue(this.struct.getPublicationEventURI().isEmpty());
        this.struct.withPublicationEventURI(publicationEvent);
        assertEquals(publicationEvent,struct.getPublicationEventURI());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        this.struct.setPublicationEventURI(altPublicationEvent);
        assertEquals(altPublicationEvent,struct.getPublicationEventURI());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
    }
    
    @Test
    public void testPublicationDate(){
        String publicationDate="publication date";
        String altPublicationDate="publication date using set";
        this.struct.withDatasetURI(datasetUri);
        assertTrue(this.struct.getPublicationDate().isEmpty());
        this.struct.withPublicationDate(publicationDate);
        assertEquals(publicationDate,struct.getPublicationDate());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        this.struct.setPublicationDate(altPublicationDate);
        assertEquals(altPublicationDate,struct.getPublicationDate());
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
        String altCreationEvent="http://localhost/creationEvent_using_set";
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
    public void testCreationDate(){
        String creationDate="creation date";
        String altCreationDate="creation date using set";
        this.struct.withDatasetURI(datasetUri);
        assertTrue(this.struct.getCreationDate().isEmpty());
        this.struct.withCreationDate(creationDate);
        assertEquals(creationDate,struct.getCreationDate());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        this.struct.setCreationDate(altCreationDate);
        assertEquals(altCreationDate,struct.getCreationDate());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
    }
    
    @Test
    public void testAttributeAssignmentEventURI(){
        String attributeAssignmentEvent="http://localhost/attribute_assignment_uri";
        String altAttributeAssignmentEvent="http://localhost/attribute_assignment_uri_using_set";
        this.struct.withDatasetURI(datasetUri);
        assertTrue(this.struct.getAttributeAssignmentEventURI().isEmpty());
        this.struct.withAttributeAssignmentEventURI(attributeAssignmentEvent);
        assertEquals(attributeAssignmentEvent,struct.getAttributeAssignmentEventURI());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        this.struct.setAttributeAssignmentEventURI(altAttributeAssignmentEvent);
        assertEquals(altAttributeAssignmentEvent,struct.getAttributeAssignmentEventURI());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
    }
    
    @Test
    public void testEmbargoPeriod(){
        String embargoPeriod="embargo period";
        String altEmbargoPeriod="embargo period using set";
        this.struct.withDatasetURI(datasetUri);
        assertTrue(this.struct.getEmbargoPeriod().isEmpty());
        this.struct.withEmbargoPeriod(embargoPeriod);
        assertEquals(embargoPeriod,struct.getEmbargoPeriod());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        this.struct.setEmbargoPeriod(altEmbargoPeriod);
        assertEquals(altEmbargoPeriod,struct.getEmbargoPeriod());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
    }
    
    @Test
    public void testEmbargoState(){
        String embargoState="embargo state";
        String altEmbargoState="embargo state using set";
        this.struct.withDatasetURI(datasetUri);
        assertTrue(this.struct.getEmbargoState().isEmpty());
        this.struct.withEmbargoState(embargoState);
        assertEquals(embargoState,struct.getEmbargoState());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        this.struct.setEmbargoState(altEmbargoState);
        assertEquals(altEmbargoState,struct.getEmbargoState());
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
    public void testDescription(){
        String description="description";
        String altDescription="description using set";
        this.struct.withDatasetURI(datasetUri);
        assertTrue(this.struct.getDescription().isEmpty());
        this.struct.withDescription(description);
        assertEquals(description,struct.getDescription());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        this.struct.setDescription(altDescription);
        assertEquals(altDescription,struct.getDescription());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
    }

    @Test
    public void testAllFields(){
        String datasetName="dataset";
        String datasetID="dataset id";
        String locationURL="http://localhost/location";
        String imageURI="http://localhost/image";
        String imageTitle="image";
        String datasetType="dataset type";
        String accessMethod="access method";
        String accessMethodURI="http://localhost/accessMethod";
        String parentDatasetURI="http://localhost/parent/dataset";
        String parentDatasetName="parent dataset";
        String contributor1URI="http://localhost/contributor1";
        String contributor1Name="contributor 1";
        String contributor2URI="http://localhost/contributor2";
        String contributor2Name="contributor 2";
        String curatorURI="http://localhost/curator";
        String curatorName="curator";
        String ownerName="owner";
        String ownerURI="http://localhost/owner";
        String publicationEventURI="http://localhost/publicationEvent";
        String publicationDate="publication date";
        String publisherName="publisher";
        String publisherURI="http://localhost/publisher";
        String creationEventURI="http://localhost/creationEvent";
        String creationDate="creation date";
        String creatorName="creator";
        String creatorURI="http://localhost/creator";
        String attributeAssignmentEventURI="http://localhost/attributeAssignmentEvent";
        String embargoPeriod="embargo period";
        String embargoState="embargo state";
        String keeperName="keeper";
        String keeperURI="http://localhost/keeper";
        String accessRights="access rights";
        String accessRightsURI="http://localhost/accessRights";
        String rightsHolderName="rights holder";
        String rightsHolderURI="http://localhost/rightsHolder";
        String contactPoint="contact point";
        String description="description";
        this.struct.withDatasetURI(datasetUri);
        this.struct.withDatasetName(datasetName)
                   .withDatasetID(datasetID)
                   .withLocationURL(locationURL)   
                   .withImageTitle(imageTitle)
                   .withImageURI(imageURI)
                   .withDatasetType(datasetType)
                   .withAccessMethod(accessMethod)
                   .withAccessMethodURI(accessMethodURI)
                   .withParentDatasetName(parentDatasetName)
                   .withParentDatasetURI(parentDatasetURI)
                   .withContributor(contributor1URI, contributor1Name)
                   .withContributor(contributor2URI, contributor2Name)
                   .withCuratorURI(curatorURI)
                   .withCuratorName(curatorName)
                   .withOwnerName(ownerName)
                   .withOwnerURI(ownerURI)
                   .withPublicationEventURI(publicationEventURI)
                   .withPublicationDate(publicationDate)
                   .withPublisherURI(publisherURI)
                   .withPublisherName(publisherName)
                   .withCreationEventURI(creationEventURI)
                   .withCreationDate(creationDate)
                   .withCreatorURI(creatorURI)
                   .withCreatorName(creatorName)
                   .withAttributeAssignmentEventURI(attributeAssignmentEventURI)
                   .withEmbargoPeriod(embargoPeriod)
                   .withEmbargoState(embargoState)
                   .withKeeperName(keeperName)
                   .withKeeperURI(keeperURI)
                   .withAccessRights(accessRights)
                   .withAccessRightsURI(accessRightsURI)
                   .withRightsHolderName(rightsHolderName)
                   .withRightsHolderURI(rightsHolderURI)
                   .withContactPoint(contactPoint)
                   .withDescription(description);
        assertEquals(datasetUri,this.struct.getDatasetURI());
        assertEquals(datasetName,this.struct.getDatasetName());
        assertEquals(datasetID,this.struct.getDatasetID());
        assertEquals(locationURL,this.struct.getLocationURL());
        assertEquals(imageTitle,this.struct.getImageTitle());
        assertEquals(imageURI,this.struct.getImageURI());
        assertEquals(datasetType,this.struct.getDatasetType());
        assertEquals(accessMethod,this.struct.getAccessMethod());
        assertEquals(accessMethodURI,this.struct.getAccessMethodURI());
        assertEquals(parentDatasetName,this.struct.getParentDatasetName());
        assertEquals(parentDatasetURI,this.struct.getParentDatasetURI());
        assertTrue(this.struct.getContributorURIs().containsAll(Arrays.asList(contributor1URI,contributor2URI)));
        assertTrue(Arrays.asList(contributor1URI,contributor2URI).containsAll(this.struct.getContributorURIs()));
        assertTrue(this.struct.getContributorNames().containsAll(Arrays.asList(contributor1Name,contributor2Name)));
        assertTrue(Arrays.asList(contributor1Name,contributor2Name).containsAll(this.struct.getContributorNames()));
        assertEquals(curatorName,this.struct.getCuratorName());
        assertEquals(curatorURI,this.struct.getCuratorURI());
        assertEquals(ownerName,this.struct.getOwnerName());
        assertEquals(ownerURI,this.struct.getOwnerURI());
        assertEquals(publicationEventURI,this.struct.getPublicationEventURI());
        assertEquals(publicationDate,this.struct.getPublicationDate());
        assertEquals(publisherName,this.struct.getPublisherName());
        assertEquals(publisherURI,this.struct.getPublisherURI());
        assertEquals(creationEventURI,this.struct.getCreationEventURI());
        assertEquals(creationDate,this.struct.getCreationDate());
        assertEquals(creatorName,this.struct.getCreatorName());
        assertEquals(creatorURI,this.struct.getCreatorURI());
        assertEquals(attributeAssignmentEventURI,this.struct.getAttributeAssignmentEventURI());
        assertEquals(embargoPeriod,this.struct.getEmbargoPeriod());
        assertEquals(embargoState,this.struct.getEmbargoState());
        assertEquals(keeperURI,this.struct.getKeeperURI());
        assertEquals(keeperName,this.struct.getKeeperName());
        assertEquals(accessRights,this.struct.getAccessRights());
        assertEquals(accessRightsURI,this.struct.getAccessRightsURI());
        assertEquals(rightsHolderURI,this.struct.getRightsHolderURI());
        assertEquals(rightsHolderName,this.struct.getRightsHolderName());
        assertEquals(contactPoint,this.struct.getContactPoint());
        assertEquals(description,this.struct.getDescription());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
    }
    
    @Test
    public void testAllFieldsUsingSetter(){
        String datasetName="dataset";
        String datasetID="dataset id";
        String locationURL="http://localhost/location";
        String imageURI="http://localhost/image";
        String imageTitle="image";
        String datasetType="dataset type";
        String accessMethod="access method";
        String accessMethodURI="http://localhost/accessMethod";
        String parentDatasetURI="http://localhost/parent/dataset";
        String parentDatasetName="parent dataset";
        String contributor1URI="http://localhost/contributor1";
        String contributor1Name="contributor 1";
        String contributor2URI="http://localhost/contributor2";
        String contributor2Name="contributor 2";
        String curatorURI="http://localhost/curator";
        String curatorName="curator";
        String ownerName="owner";
        String ownerURI="http://localhost/owner";
        String publicationEventURI="http://localhost/publicationEvent";
        String publicationDate="publication date";
        String publisherName="publisher";
        String publisherURI="http://localhost/publisher";
        String creationEventURI="http://localhost/creationEvent";
        String creationDate="creation date";
        String creatorName="creator";
        String creatorURI="http://localhost/creator";
        String attributeAssignmentEventURI="http://localhost/attributeAssignmentEvent";
        String embargoPeriod="embargo period";
        String embargoState="embargo state";
        String keeperName="keeper";
        String keeperURI="http://localhost/keeper";
        String accessRights="access rights";
        String accessRightsURI="http://localhost/accessRights";
        String rightsHolderName="rights holder";
        String rightsHolderURI="http://localhost/rightsHolder";
        String contactPoint="contact point";
        String description="description";
        this.struct.setDatasetURI(datasetUri);
        this.struct.setDatasetName(datasetName);
        this.struct.setDatasetID(datasetID);
        this.struct.setLocationURL(locationURL);   
        this.struct.setImageTitle(imageTitle);
        this.struct.setImageURI(imageURI);
        this.struct.setDatasetType(datasetType);
        this.struct.setAccessMethod(accessMethod);
        this.struct.setAccessMethodURI(accessMethodURI);
        this.struct.setParentDatasetName(parentDatasetName);
        this.struct.setParentDatasetURI(parentDatasetURI);
        this.struct.setContributors(Arrays.asList(Pair.of(contributor1URI, contributor1Name),Pair.of(contributor2URI, contributor2Name)));
        this.struct.setCuratorURI(curatorURI);
        this.struct.setCuratorName(curatorName);
        this.struct.setOwnerName(ownerName);
        this.struct.setOwnerURI(ownerURI);
        this.struct.setPublicationEventURI(publicationEventURI);
        this.struct.setPublicationDate(publicationDate);
        this.struct.setPublisherURI(publisherURI);
        this.struct.setPublisherName(publisherName);
        this.struct.setCreationEventURI(creationEventURI);
        this.struct.setCreationDate(creationDate);
        this.struct.setCreatorURI(creatorURI);
        this.struct.setCreatorName(creatorName);
        this.struct.setAttributeAssignmentEventURI(attributeAssignmentEventURI);
        this.struct.setEmbargoPeriod(embargoPeriod);
        this.struct.setEmbargoState(embargoState);
        this.struct.setKeeperName(keeperName);
        this.struct.setKeeperURI(keeperURI);
        this.struct.setAccessRights(accessRights);
        this.struct.setAccessRightsURI(accessRightsURI);
        this.struct.setRightsHolderName(rightsHolderName);
        this.struct.setRightsHolderURI(rightsHolderURI);
        this.struct.setContactPoint(contactPoint);
        this.struct.setDescription(description);
        assertEquals(datasetUri,this.struct.getDatasetURI());
        assertEquals(datasetName,this.struct.getDatasetName());
        assertEquals(datasetID,this.struct.getDatasetID());
        assertEquals(locationURL,this.struct.getLocationURL());
        assertEquals(imageTitle,this.struct.getImageTitle());
        assertEquals(imageURI,this.struct.getImageURI());
        assertEquals(datasetType,this.struct.getDatasetType());
        assertEquals(accessMethod,this.struct.getAccessMethod());
        assertEquals(accessMethodURI,this.struct.getAccessMethodURI());
        assertEquals(parentDatasetName,this.struct.getParentDatasetName());
        assertEquals(parentDatasetURI,this.struct.getParentDatasetURI());
        assertTrue(this.struct.getContributorURIs().containsAll(Arrays.asList(contributor1URI,contributor2URI)));
        assertTrue(Arrays.asList(contributor1URI,contributor2URI).containsAll(this.struct.getContributorURIs()));
        assertTrue(this.struct.getContributorNames().containsAll(Arrays.asList(contributor1Name,contributor2Name)));
        assertTrue(Arrays.asList(contributor1Name,contributor2Name).containsAll(this.struct.getContributorNames()));
        assertEquals(curatorName,this.struct.getCuratorName());
        assertEquals(curatorURI,this.struct.getCuratorURI());
        assertEquals(ownerName,this.struct.getOwnerName());
        assertEquals(ownerURI,this.struct.getOwnerURI());
        assertEquals(publicationEventURI,this.struct.getPublicationEventURI());
        assertEquals(publicationDate,this.struct.getPublicationDate());
        assertEquals(publisherName,this.struct.getPublisherName());
        assertEquals(publisherURI,this.struct.getPublisherURI());
        assertEquals(creationEventURI,this.struct.getCreationEventURI());
        assertEquals(creationDate,this.struct.getCreationDate());
        assertEquals(creatorName,this.struct.getCreatorName());
        assertEquals(creatorURI,this.struct.getCreatorURI());
        assertEquals(attributeAssignmentEventURI,this.struct.getAttributeAssignmentEventURI());
        assertEquals(embargoPeriod,this.struct.getEmbargoPeriod());
        assertEquals(embargoState,this.struct.getEmbargoState());
        assertEquals(keeperURI,this.struct.getKeeperURI());
        assertEquals(keeperName,this.struct.getKeeperName());
        assertEquals(accessRights,this.struct.getAccessRights());
        assertEquals(accessRightsURI,this.struct.getAccessRightsURI());
        assertEquals(rightsHolderURI,this.struct.getRightsHolderURI());
        assertEquals(rightsHolderName,this.struct.getRightsHolderName());
        assertEquals(contactPoint,this.struct.getContactPoint());
        assertEquals(description,this.struct.getDescription());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
    }
    
    private boolean importAndValidateTriples(String triples){
        try{
            RepositoryConnection repoConn=this.repository.getConnection();
            repoConn.add(new StringReader(triples), triplesContext, RDFFormat.NTRIPLES, new URIImpl(triplesContext));
            repoConn.close();
            return true;
        }catch(IOException | RDFParseException | RepositoryException ex){
            logger.error("The DirectoryStruct contents are not valid",ex);
            return false;
        }
    }
}