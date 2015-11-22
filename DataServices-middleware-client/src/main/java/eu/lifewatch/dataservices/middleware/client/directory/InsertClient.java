package eu.lifewatch.dataservices.middleware.client.directory;

import java.util.Arrays;
import org.apache.log4j.Logger;

/**
 * @author Yannis Marketakis (marketak 'at' ics 'dot' forth 'dot' gr)
 * @author Nikos Minadakis (minadakn 'at' ics 'dot' forth 'dot' gr)
 */
public class InsertClient {
    private static final Logger logger=Logger.getLogger(InsertClient.class);
    private static final String DIRECTORY_GRAPHSPACE = "http://www.ics.forth.gr/isl/lifewatch/directory";

    private static void insertDataset(){
        try{
            DirectoryServiceInsert_Service service = new DirectoryServiceInsert_Service();
            DirectoryServiceInsert port = service.getDirectoryServiceInsertPort();
            DirectoryStruct struct1 = new DirectoryStruct();
            struct1.setDatasetURI("http://localhost/directory/dataset_1");
            struct1.setDatasetName("dataset 1");
            struct1.setDatasetID("dataset ID 1");
            struct1.setDatasetType("dataset type 1");
            struct1.setAccessMethod("access method 1");
            struct1.setAccessMethodURI("http://localhost/directory/access_method_1");
            struct1.setAccessRights("access rights 1");
            struct1.setAccessRightsURI("http://localhost/directory/access_rights_1");
            struct1.setAttributeAssignmentEventURI("http://localhost/directory/attribute_assignment_1");
            struct1.setAttributeAssignmentEvent("attribute assignment 1");
            struct1.setContactPoint("contact point 1");
            struct1.setCreationDate("creation date 1");
            struct1.setCreationEventURI("http://localhost/directory/creation_event_1");
            struct1.setCreationEvent("creation event 1");
            struct1.setCreatorName("creator name 1");
            struct1.setCreatorURI("http://localhost/directory/creator_1");
            struct1.setCuratorName("curator name 1");
            struct1.setCuratorURI("http://localhost/directory/curator_1");
            struct1.setDescription("description 1");
            struct1.setEmbargoPeriod("embargo period 1");
            struct1.setEmbargoState("embargo state 1");
            struct1.setImageTitle("image title 1");
            struct1.setImageURI("http://localhost/directory/image_1");
            struct1.setKeeperName("keeper name 1");
            struct1.setKeeperURI("http://localhost/directory/keeper_1");
            struct1.setLocationURL("http://localhost/directory/location_1");
            struct1.setOwnerName("owner name 1");
            struct1.setOwnerURI("http://localhost/directory/owner_1");
            struct1.setParentDatasetName("parent dataset 1");
            struct1.setParentDatasetURI("http://localhost/directory/parent_dataset_1");
            struct1.setPublicationDate("publication date 1");
            struct1.setPublicationEventURI("http://localhost/directory/publication_event_1");
            struct1.setPublicationEvent("publication event 1");
            struct1.setPublisherName("publisher name 1");
            struct1.setPublisherURI("http://localhost/directory/publisher_1");
            struct1.setRightsHolderName("rights holder 1");
            struct1.setRightsHolderURI("http://localhost/directory/rights_holder_1");
            Pair contributor11=new Pair();
            contributor11.setKey("http://localhost/directory/contributor_11");
            contributor11.setValue("contributor 11");
            Pair contributor12=new Pair();
            contributor12.setKey("http://localhost/directory/contributor_12");
            contributor12.setValue("contributor 12");
            struct1.contributors=Arrays.asList(contributor11,contributor12);
            DirectoryStruct struct2 = new DirectoryStruct();
            struct2.setDatasetURI("http://localhost/directory/dataset_2");
            struct2.setDatasetName("dataset 2");
            struct2.setDatasetID("dataset ID 2");
            struct2.setDatasetType("dataset type 2");
            struct2.setAccessMethod("access method 2");
            struct2.setAccessMethodURI("http://localhost/directory/access_method_2");
            struct2.setAccessRights("access rights 2");
            struct2.setAccessRightsURI("http://localhost/directory/access_rights_2");
            struct2.setAttributeAssignmentEventURI("http://localhost/directory/attribute_assignment_2");
            struct2.setAttributeAssignmentEvent("attribute assignment 2");
            struct2.setContactPoint("contact point 2");
            struct2.setCreationDate("creation date 2");
            struct2.setCreationEventURI("http://localhost/directory/creation_event_2");
            struct2.setCreationEvent("creation event 2");
            struct2.setCreatorName("creator name 2");
            struct2.setCreatorURI("http://localhost/directory/creator_2");
            struct2.setCuratorName("curator name 2");
            struct2.setCuratorURI("http://localhost/directory/curator_2");
            struct2.setDescription("description 2");
            struct2.setEmbargoPeriod("embargo period 2");
            struct2.setEmbargoState("embargo state 2");
            struct2.setImageTitle("image title 2");
            struct2.setImageURI("http://localhost/directory/image_2");
            struct2.setKeeperName("keeper name 2");
            struct2.setKeeperURI("http://localhost/directory/keeper_2");
            struct2.setLocationURL("http://localhost/directory/location_2");
            struct2.setOwnerName("owner name 2");
            struct2.setOwnerURI("http://localhost/directory/owner_2");
            struct2.setParentDatasetName("parent dataset 2");
            struct2.setParentDatasetURI("http://localhost/directory/parent_dataset_2");
            struct2.setPublicationDate("publication date 2");
            struct2.setPublicationEventURI("http://localhost/directory/publication_event_2");
            struct2.setPublicationEvent("publication event 2");
            struct2.setPublisherName("publisher name 2");
            struct2.setPublisherURI("http://localhost/directory/publisher_2");
            struct2.setRightsHolderName("rights holder 2");
            struct2.setRightsHolderURI("http://localhost/directory/rights_holder_2");
            Pair contributor21=new Pair();
            contributor21.setKey("http://localhost/directory/contributor_21");
            contributor21.setValue("contributor 21");
            Pair contributor22=new Pair();
            contributor22.setKey("http://localhost/directory/contributor_22");
            contributor22.setValue("contributor 22");
            struct1.contributors=Arrays.asList(contributor21,contributor22);
            
            System.out.println("Adding a new dataset with the following details: "+
                               "\tDataset Name: "+struct1.getDatasetName()+
                               "\tOwner Name: "+struct1.getOwnerName()+
                               "\tDataset URI: "+struct1.getDatasetURI()+
                               "\tDataset Type: "+struct1.getDatasetType()+
                               "\tGraphspace: "+DIRECTORY_GRAPHSPACE+" ...");
            boolean result1= port.insertDataset(struct1, DIRECTORY_GRAPHSPACE);
            System.out.println("Insert Dataset results= "+result1);
            System.out.println("Adding a new dataset with the following details: "+
                               "\tDataset Name: "+struct2.getDatasetName()+
                               "\tOwner Name: "+struct2.getOwnerName()+
                               "\tDataset URI: "+struct2.getDatasetURI()+
                               "\tDataset Type: "+struct2.getDatasetType()+
                               "\tGraphspace: "+DIRECTORY_GRAPHSPACE+" ...");
            boolean result2= port.insertDataset(struct2, DIRECTORY_GRAPHSPACE);
            System.out.println("Insert Dataset results= "+result2);
        } catch (Exception ex) {
            logger.error("An error occured while adding the new datasets",ex);
        }
    }
    
    public static void main(String[] args){
        insertDataset();
    }
}