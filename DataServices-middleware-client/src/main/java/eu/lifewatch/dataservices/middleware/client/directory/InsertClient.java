package eu.lifewatch.dataservices.middleware.client.directory;

import java.util.Arrays;
import org.apache.log4j.Logger;

/**
 * @author Yannis Marketakis (marketak 'at' ics 'dot' forth 'dot' gr)
 * @author Nikos Minadakis (minadakn 'at' ics 'dot' forth 'dot' gr)
 */
public class InsertClient {
    private static final Logger logger=Logger.getLogger(InsertClient.class);

    private static void insertDataset(){
        try{
            DirectoryServiceInsert_Service service = new DirectoryServiceInsert_Service();
            DirectoryServiceInsert port = service.getDirectoryServiceInsertPort();
            DirectoryStruct struct = new DirectoryStruct();
            struct.setDatasetURI("http://localhost/directory/dataset_new");
            struct.setDatasetName("dataset new");
            struct.setDatasetID("dataset ID new");
            struct.setDatasetType("dataset type new");
            struct.setAccessMethod("access method new");
            struct.setAccessMethodURI("http://localhost/directory/access_method_new");
            struct.setAccessRights("access rights new");
            struct.setAccessRightsURI("http://localhost/directory/access_rights_new");
            struct.setAttributeAssignmentEventURI("http://localhost/directory/attribute_assignment_new");
            struct.setAttributeAssignmentEvent("attribute assignment");
            struct.setContactPoint("contact point new");
            struct.setCreationDate("creation date new");
            struct.setCreationEventURI("http://localhost/directory/creatio_event_new");
            struct.setCreationEvent("creation event");
            struct.setCreatorName("creator name new");
            struct.setCreatorURI("http://localhost/directory/creator_new");
            struct.setCuratorName("curator name new");
            struct.setCuratorURI("http://localhost/directory/curator_new");
            struct.setDescription("description new");
            struct.setEmbargoPeriod("embargo period new");
            struct.setEmbargoState("embargo state new");
            struct.setImageTitle("image title new");
            struct.setImageURI("http://localhost/directory/image_new");
            struct.setKeeperName("keeper name new");
            struct.setKeeperURI("http://localhost/directory/keeper_new");
            struct.setLocationURL("http://localhost/directory/location_new");
            struct.setOwnerName("owner name");
            struct.setOwnerURI("http://localhost/directory/owner_new");
            struct.setParentDatasetName("parent dataset new");
            struct.setParentDatasetURI("http://localhost/directory/parent_dataset_new");
            struct.setPublicationDate("publication date new");
            struct.setPublicationEventURI("http://localhost/directory/publication_event_new");
            struct.setPublicationEvent("publication event");
            struct.setPublisherName("publisher name new");
            struct.setPublisherURI("http://localhost/directory/publisher_new");
            struct.setRightsHolderName("rights holder new");
            struct.setRightsHolderURI("http://localhost/directory/rights_holder_new");
            Pair contributor1=new Pair();
            contributor1.setKey("http://localhost/directory/contributor1");
            contributor1.setValue("contributor 1");
            Pair contributor2=new Pair();
            contributor2.setKey("http://localhost/directory/contributor2");
            contributor2.setValue("contributor 2");
            struct.contributors=Arrays.asList(contributor1,contributor2);
            String repositoryGraph = "http://www.ics.forth.gr/isl/lifewatch/directory";
            System.out.println("Adding a new dataset with the following details: "+
                               "\tDataset Name: "+struct.getDatasetName()+
                               "\tOwner Name: "+struct.getOwnerName()+
                               "\tDataset URI: "+struct.getDatasetURI()+
                               "\tDataset Type: "+struct.getDatasetType()+
                               "\tGraphspace: "+repositoryGraph+" ...");
            boolean result = port.insertDataset(struct, repositoryGraph);
            System.out.println("Insert Dataset results= "+result);
        } catch (Exception ex) {
            logger.error("An error occured while adding a new dataset",ex);
        }
    }
    
    public static void main(String[] args){
        insertDataset();
    }
}