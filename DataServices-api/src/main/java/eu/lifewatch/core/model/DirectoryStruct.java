package eu.lifewatch.core.model;

import eu.lifewatch.common.Resources;
import eu.lifewatch.exception.URIValidationException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import org.apache.log4j.Logger;

/**
 * DirectoryStruct object are actually entries in the repository. They are in
 * the form of a C-Like DirectoryStruct. These object can also describe
 * themselves in an NTriple form.
 *
 * @author Nikos Minadakis (minadakn 'at' ics 'dot' forth 'dot' gr)
 * @author Yannis Marketakis (marketak 'at' ics 'dot' forth 'dot' gr)
 */
public class DirectoryStruct {
    
    private String datasetName;
    private String datasetURI;
    private String datasetID;
    private String locationURL;
    private String imageURI;
    private String imageTitle;
    private String datasetType;
    private String accessMethod;
    private String accessMethodURI;
    private String parentDatasetURI;
    private String parentDatasetName;
    private List<Pair> contributors;
    private String curatorURI;
    private String curatorName;
    private String ownerName;
    private String ownerURI;
    private String publicationEventURI;
    private String publicationDate;
    private String publisherName;
    private String publisherURI;
    private String creationEventURI;
    private String creationDate;
    private String creatorName;
    private String creatorURI;
    private String attributeAssignmentEventURI;
    private String embargoPeriod;
    private String embargoState;
    private String keeperName;
    private String keeperURI;
    private String accessRights;
    private String accessRightsURI;
    private String rightsHolderName;
    private String rightsHolderURI;
    private String contactPoint;
    private String description;

    private static final Logger logger = Logger.getLogger(DirectoryStruct.class);

    /**
     * Creates a new instance by initializing all the fields to be empty (Null
     * values will cause NPE issues)
     */
    public DirectoryStruct() {
        datasetName = "";
        datasetURI = "";
        this.contributors = new ArrayList<>();
        keeperName = "";
        keeperURI = "";
        accessRights = "";
        rightsHolderURI = "";
        rightsHolderName = "";
        accessMethod = "";
        curatorURI = "";
        curatorName = "";
        creatorURI = "";
        publisherName = "";
        publisherURI = "";
        creationEventURI = "";
        creatorName = "";
        creationDate = "";
        ownerName = "";
        ownerURI = "";
        description = "";
        locationURL = "";
        contactPoint = "";
        parentDatasetName = "";
        parentDatasetURI = "";
        accessRightsURI = "";
        datasetID = "";
        datasetType = "";
        accessMethodURI = "";
        publicationEventURI = "";
        publicationDate = "";
        attributeAssignmentEventURI = "";
        embargoPeriod = "";
        embargoState = "";
        imageURI = "";
        imageTitle = "";
    }
    
    /** Returns the name of the dataset 
     * 
     * @return a <b>literal</b> for the dataset name */
    public String getDatasetName() {
        return datasetName;
    }

    /** Returns the URI of the dataset 
     * 
     * @return a <b>URI</b> for the dataset */
    public String getDatasetURI() {
        return datasetURI;
    }

    /** Returns the URI of the image of the dataset
     * 
     * @return a <b>URI</b> for the dataset image */
    public String getImageURI() {
        return imageURI;
    }

    /** Returns the name of the dataset image
     * 
     * @return a <b>literal</b> for the dataset image*/
    public String getImageTitle() {
        return imageTitle;
    }

    /** Returns the URI of the creation event of the dataset 
     * 
     * @return a <b>URI</b> for the dataset creation event*/
    public String getCreationEventURI() {
        return creationEventURI;
    }

    /** Returns the names of the contributors of the dataset 
     * 
     * @return a collection of <b>literals</b> for the dataset contributors */
    public Collection<String> getContributorNames() {
        Set<String> contributorNames = new HashSet<>();
        for (Pair contributor : this.contributors) {
            contributorNames.add(contributor.getValue());
        }
        return contributorNames;
    }

    /** Returns the URIs of the contributors of the dataset
     * 
     * @return a collection of <b>URIs</b> for the dataset contributors */
    public Collection<String> getContributorURIs() {
        Set<String> contributorURIs = new HashSet<>();
        for (Pair contributor : this.contributors) {
            contributorURIs.add(contributor.getKey());
        }
        return contributorURIs;
    }

    /** Returns the contributors the dataset 
     * 
     * @return a list of <b>pairs</b> (URI-name) for the dataset contributors */
    public List<Pair> getContributors() {
        return this.contributors;
    }
    
    /** Returns the name of the keeper of the dataset 
     * 
     * @return a <b>literal</b> for the dataset keeper name */
    public String getKeeperName() {
        return keeperName;
    }

    /** Returns the URI of the dataset keeper
     * 
     * @return a <b>URI</b> for the dataset keeper*/
    public String getKeeperURI() {
        return keeperURI;
    }

    /** Returns the name of the access rights
     * 
     * @return a <b>literal</b> for the dataset access rights*/
    public String getAccessRights() {
        return accessRights;
    }

    /** Returns the URI of the dataset access rights
     * 
     * @return a <b>URI</b> for the dataset access rights*/
    public String getAccessRightsURI() {
        return accessRightsURI;
    }

    /** Returns the name of the dataset creator
     * 
     * @return a <b>literal</b> for the dataset creator name */
    public String getCreatorName() {
        return creatorName;
    }

    /** Returns the URI of the dataset creator
     * 
     * @return a <b>URI</b> for the dataset creator */
    public String getCreatorURI() {
        return creatorURI;
    }

    /** Returns the name of the dataset publisher
     * 
     * @return a <b>literal</b> for the dataset publisher */
    public String getPublisherName() {
        return publisherName;
    }

    /** Returns the URI of the dataset publisher
     * 
     * @return a <b>URI</b> for the dataset publisher */
    public String getPublisherURI() {
        return publisherURI;
    }

    /** Returns the date of the creation of the dataset 
     * 
     * @return a <b>literal</b> for the dataset creation date */
    public String getCreationDate() {
        return creationDate;
    }

    /** Returns the URI of the rights holder of the dataset
     * 
     * @return a <b>URI</b> for the rights holder of the dataset */
    public String getRightsHolderURI() {
        return rightsHolderURI;
    }

    /** Returns the name of the rights holder of the dataset 
     * 
     * @return a <b>literal</b> for the rights holder of the dataset */
    public String getRightsHolderName() {
        return rightsHolderName;
    }

    /** Returns the name of the access method of the dataset 
     * 
     * @return a <b>literal</b> for the access method of the dataset */
    public String getAccessMethod() {
        return accessMethod;
    }

    /** Returns the URI of the dataset 
     * 
     * @return a <b>URI</b> for the access method dataset */
    public String getAccessMethodURI() {
        return accessMethodURI;
    }

    /** Returns the URI of the curator of the dataset 
     * 
     * @return a <b>URI</b> for the curator of the dataset */
    public String getCuratorURI() {
        return curatorURI;
    }

    /** Returns the name of the curator of the dataset 
     * 
     * @return a <b>literal</b> for the curator of the dataset */
    public String getCuratorName() {
        return curatorName;
    }

    /** Returns the URI of the owner of the dataset 
     * 
     * @return a <b>URI</b> for the owner of the dataset */
    public String getOwnerURI() {
        return ownerURI;
    }
    
    /** Returns the name of the owner of the dataset 
     * 
     * @return a <b>literal</b> for the owner of the dataset */
    public String getOwnerName() {
        return ownerName;
    }

    /** Returns the description of the dataset 
     * 
     * @return a <b>literal</b> for the dataset description */
    public String getDescription() {
        return description;
    }

    /** Returns the location of the dataset 
     * 
     * @return a <b>URL</b> for the dataset*/
    public String getLocationURL() {
        return locationURL;
    }

    /** Returns the name of the contact point of the dataset
     * 
     * @return a <b>literal</b> for the dataset contact point*/
    public String getContactPoint() {
        return contactPoint;
    }

    /** Returns the URI of the parent dataset 
     * 
     * @return a <b>URI</b> for the parent dataset*/
    public String getParentDatasetURI() {
        return parentDatasetURI;
    }

    /** Returns the name of the parent dataset 
     * 
     * @return a <b>literal</b> for the parent dataset */
    public String getParentDatasetName() {
        return parentDatasetName;
    }

    /** Returns the ID of the dataset 
     * 
     * @return a <b>literal</b> for the parent dataset */
    public String getDatasetID() {
        return datasetID;
    }

    /** Returns the type of the dataset 
     * 
     * @return a <b>literal</b> for the dataset type*/
    public String getDatasetType() {
        return datasetType;
    }

    /** Returns the URI of the dataset publication event
     * 
     * @return a <b>URI</b> for the dataset publication event*/
    public String getPublicationEventURI() {
        return publicationEventURI;
    }

    /** Returns the date of the dataset publication
     * 
     * @return a <b>literal</b> for the parent publication date */
    public String getPublicationDate() {
        return publicationDate;
    }

    /** Returns the URI of the dataset attribute assignment event
     * 
     * @return a <b>URI</b> for the dataset attribute assignment event*/
    public String getAttributeAssignmentEventURI() {
        return attributeAssignmentEventURI;
    }

    /** Returns the value of the embargo state of the dataset 
     * 
     * @return a <b>literal</b> for the embargo state of the dataset */
    public String getEmbargoState() {
        return embargoState;
    }

    /** Returns the period of embargo of the dataset 
     * 
     * @return a <b>literal</b> for the embargo period of the dataset */
    public String getEmbargoPeriod() {
        return embargoPeriod;
    }

    /** Sets the name of the dataset 
     * 
     * @param datasetName the <b>literal</b> value for the dataset name */
    public void setDatasetName(String datasetName) {
        this.datasetName = datasetName;
    }

    /** Sets the URI of the dataset 
     * 
     * @param datasetURI the <b>URI</b> value for the dataset*/
    public void setDatasetURI(String datasetURI) {
        this.datasetURI = datasetURI;
    }

    /** Sets the contributors of the dataset 
     * 
     * @param contributors  the list of <b>pairs</b> for the dataset contributors*/
    public void setContributors(List<Pair> contributors) {
        this.contributors = contributors;
    }

    /** Sets the name of the keeper of the dataset 
     * 
     * @param keeperName the <b>literal</b> value for the dataset keeper */
    public void setKeeperName(String keeperName) {
        this.keeperName = keeperName;
    }

    /** Sets the URI of the dataset keeper
     * 
     * @param keeperURI the <b>URI</b> value for the dataset keeper */
    public void setKeeperURI(String keeperURI) {
        this.keeperURI = keeperURI;
    }

    /** Sets the image title of the dataset 
     * 
     * @param imageTitle  the <b>literal</b> value for the dataset image */
    public void setImageTitle(String imageTitle) {
        this.imageTitle = imageTitle;
    }

    /** Sets the name of the dataset image
     * 
     * @param imageURI the <b>URI</b> value for the dataset image */
    public void setImageURI(String imageURI) {
        this.imageURI = imageURI;
    }

    /** Sets the access rights of the dataset 
     * 
     * @param accessRights the <b>literal</b> value for the dataset access rights*/
    public void setAccessRights(String accessRights) {
        this.accessRights = accessRights;
    }

    /** Sets the name of the rights holder of the dataset 
     * 
     * @param rightsHolderName  the <b>literal</b> value for the dataset rights holder */
    public void setRightsHolderName(String rightsHolderName) {
        this.rightsHolderName = rightsHolderName;
    }

    /** Sets the URI of the dataset rights holder
     * 
     * @param rightsHolderURI the <b>URI</b> value for the dataset rights holder */
    public void setRightsHolderURI(String rightsHolderURI) {
        this.rightsHolderURI = rightsHolderURI;
    }

    /** Sets the access method of the dataset 
     * 
     * @param accessMethod  the <b>literal</b> value for the dataset access method */
    public void setAccessMethod(String accessMethod) {
        this.accessMethod = accessMethod;
    }

    /** Sets the URI of the curator of the dataset 
     * 
     * @param curatorURI the <b>URI</b> value for the dataset curator*/
    public void setCuratorURI(String curatorURI) {
        this.curatorURI = curatorURI;
    }

    /** Sets the name of the curator of the dataset 
     * 
     * @param curatorName the <b>literal</b> value for the dataset curator */
    public void setCuratorName(String curatorName) {
        this.curatorName = curatorName;
    }
    
    /** Sets the name of the owner of the dataset 
     * 
     * @param ownerName the <b>literal</b> value for the dataset owner */
    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    /** Sets the URI of the owner of the dataset 
     * 
     * @param ownerURI the <b>URI</b> value for the dataset owner*/
    public void setOwnerURI(String ownerURI) {
        this.ownerURI = ownerURI;
    }

    /** Sets the name of the publisher of the dataset 
     * 
     * @param publisherName  the <b>literal</b> value for the dataset publisher*/
    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    /** Sets the URI of the publisher of the dataset 
     * 
     * @param publisherURI the <b>URI</b> value for the dataset publisher */
    public void setPublisherURI(String publisherURI) {
        this.publisherURI = publisherURI;
    }

    /** Sets the name of the creator of the dataset 
     * 
     * @param creatorName the <b>literal</b> value for the dataset creator */
    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    /** Sets the URI of the dataset creator
     * 
     * @param creatorURI  the <b>URI</b> value for the dataset creator */
    public void setCreatorURI(String creatorURI) {
        this.creatorURI = creatorURI;
    }

    /** Sets the date of the creation of the dataset 
     * 
     * @param creationDate  the <b>literal</b> value for the dataset creation date */
    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    /** Sets the description of the dataset 
     * 
     * @param description  the <b>literal</b> value for the dataset description */
    public void setDescription(String description) {
        this.description = description;
    }

    /** Sets the URI of the dataset creation event
     * 
     * @param creationEventURI the <b>URI</b> value for the dataset creation event */
    public void setCreationEventURI(String creationEventURI) {
        this.creationEventURI = creationEventURI;
    }

    /** Sets the location of the dataset 
     * 
     * @param locationURL the <b>URL</b> value for the dataset location */
    public void setLocationURL(String locationURL) {
        this.locationURL = locationURL;
    }

    /** Sets the contact point of the dataset 
     * 
     * @param contactPoint the <b>literal</b> value for the dataset contact point*/
    public void setContactPoint(String contactPoint) {
        this.contactPoint = contactPoint;
    }

    /** Sets the URI of the parent dataset 
     * 
     * @param parentDatasetURI the <b>URI</b> value for the parent dataset */
    public void setParentDatasetURI(String parentDatasetURI) {
        this.parentDatasetURI = parentDatasetURI;
    }

    /** Sets the URI of the dataset access rights
     * 
     * @param accessRightsURI the <b>URI</b> value for the dataset access rights */
    public void setAccessRightsURI(String accessRightsURI) {
        this.accessRightsURI = accessRightsURI;
    }

    /** Sets the name of the parent dataset 
     * 
     * @param parentDatasetName the <b>literal</b> value for the parent dataset */
    public void setParentDatasetName(String parentDatasetName) {
        this.parentDatasetName = parentDatasetName;
    }

    /** Sets the ID of the dataset 
     * 
     * @param datasetID the <b>literal</b> value for the dataset ID */
    public void setDatasetID(String datasetID) {
        this.datasetID = datasetID;
    }

    /** Sets the type of the dataset 
     * 
     * @param datasetType the <b>literal</b> value for the dataset type */
    public void setDatasetType(String datasetType) {
        this.datasetType = datasetType;
    }

    /** Sets the URI of the dataset access method
     * 
     * @param accessMethodURI the <b>URI</b> value for the dataset access method */
    public void setAccessMethodURI(String accessMethodURI) {
        this.accessMethodURI = accessMethodURI;
    }

    /** Sets the date of the dataset publication
     * 
     * @param publicationDate the <b>literal</b> value for the dataset publication date */
    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }
    
    /** Sets the URI of the dataset publication
     * 
     * @param publicationEventURI the <b>URI</b> value for the dataset publication event */
    public void setPublicationEventURI(String publicationEventURI){
        this.publicationEventURI=publicationEventURI;
    }

    /** Sets the URI of the dataset attribute assignment event
     * 
     * @param attributeAssignmentEventURI the <b>URI</b> value for the dataset attribute assignment event*/
    public void setAttributeAssignmentEventURI(String attributeAssignmentEventURI) {
        this.attributeAssignmentEventURI = attributeAssignmentEventURI;
    }

    /** Sets the the embargo state of the dataset 
     * 
     * @param embargoState  the <b>literal</b> value for the dataset embargo state */
    public void setEmbargoState(String embargoState) {
        this.embargoState = embargoState;
    }

    /** Sets the embargo period of the dataset 
     * 
     * @param embargoPeriod the <b>literal</b> value for the dataset embargo period */
    public void setEmbargoPeriod(String embargoPeriod) {
        this.embargoPeriod = embargoPeriod;
    }

    /** adds the URI of the dataset 
     * 
     * @param datasetURI the <b>URI</b> value for the dataset
     * @return the updated struct */
    public DirectoryStruct withDatasetURI(String datasetURI) {
        this.datasetURI = datasetURI;
        return this;
    }

    /** adds the URI of the creation event of the dataset 
     * 
     * @param creationEventURI the <b>URI</b> value for the dataset creation event 
     * @return the updated struct */
    public DirectoryStruct withCreationEventURI(String creationEventURI) {
        this.creationEventURI = creationEventURI;
        return this;
    }

    /** adds the name of the dataset 
     * 
     * @param datasetName the <b>literal</b> value for the dataset
     * @return the updated struct */
    public DirectoryStruct withDatasetName(String datasetName) {
        this.datasetName = datasetName;
        return this;
    }

    /** adds a contributor for the dataset 
     * 
     * @param contributorURI  the <b>URI</b> value for the contributor of the dataset
     * @param contributorName  the <b>literal</b> value for the contributor of the dataset
     * @return the updated struct */
    public DirectoryStruct withContributor(String contributorURI, String contributorName) {
        if (!this.getContributorURIs().contains(contributorURI)) {
            this.contributors.add(new Pair(contributorURI, contributorName));
        }
        return this;
    }

    /** adds the name of the keeper of the dataset 
     * 
     * @param keeperName the <b>literal</b> value for the dataset keeper
     * @return the updated struct */
    public DirectoryStruct withKeeperName(String keeperName) {
        this.keeperName = keeperName;
        return this;
    }

    /** adds the URI of the keeper of the dataset 
     * 
     * @param keeperURI the <b>URI</b> value for the dataset keeper
     * @return the updated struct */
    public DirectoryStruct withKeeperURI(String keeperURI) {
        this.keeperURI = keeperURI;
        return this;
    }

    /** adds the title of the image of the dataset 
     * 
     * @param imageTitle the <b>literal</b> value for the dataset image
     * @return the updated struct */
    public DirectoryStruct withImageTitle(String imageTitle) {
        this.imageTitle = imageTitle;
        return this;
    }

    /** adds the URI of the dataset image
     * 
     * @param imageURI the <b>URI</b> value for the dataset image
     * @return the updated struct */
    public DirectoryStruct withImageURI(String imageURI) {
        this.imageURI = imageURI;
        return this;
    }

    /** adds the URI of the creator of the dataset 
     * 
     * @param creatorURI the <b>URI</b> value for the dataset creator
     * @return the updated struct */
    public DirectoryStruct withCreatorURI(String creatorURI) {
        this.creatorURI = creatorURI;
        return this;
    }

    /** adds the name of the creator of the dataset 
     * 
     * @param creatorName the <b>literal</b> value for the dataset creator
     * @return the updated struct */
    public DirectoryStruct withCreatorName(String creatorName) {
        this.creatorName = creatorName;
        return this;
    }

    /** adds the date of the dataset creation
     * 
     * @param creationDate the <b>literal</b> value for the dataset creation
     * @return the updated struct */
    public DirectoryStruct withCreationDate(String creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    /** adds the value of the dataset access rights
     * 
     * @param accessRights <b>literal</b> value for the dataset access rights
     * @return the updated struct */
    public DirectoryStruct withAccessRights(String accessRights) {
        this.accessRights = accessRights;
        return this;
    }

    /** adds the URI of the dataset access rights
     * 
     * @param accessRightsURI the <b>URI</b> value for the dataset access rights
     * @return the updated struct */
    public DirectoryStruct withAccessRightsURI(String accessRightsURI) {
        this.accessRightsURI = accessRightsURI;
        return this;
    }

    /** adds the URI of the dataset rights holder
     * 
     * @param rightsHolderURI the <b>URI</b> value for the dataset rights holder
     * @return the updated struct */
    public DirectoryStruct withRightsHolderURI(String rightsHolderURI) {
        this.rightsHolderURI = rightsHolderURI;
        return this;
    }

    /** adds the name of the dataset rights holder
     * 
     * @param rightsHolderName the <b>literal</b> value for the dataset rights holder
     * @return the updated struct */
    public DirectoryStruct withRightsHolderName(String rightsHolderName) {
        this.rightsHolderName = rightsHolderName;
        return this;
    }

    /** adds the value of the dataset access method
     * 
     * @param accessMethod  the <b>URI</b> value for the dataset access method
     * @return the updated struct */
    public DirectoryStruct withAccessMethod(String accessMethod) {
        this.accessMethod = accessMethod;
        return this;
    }

    /** adds the URI of the dataset curator
     * 
     * @param curatorURI the <b>URI</b> value for the dataset curator
     * @return the updated struct */
    public DirectoryStruct withCuratorURI(String curatorURI) {
        this.curatorURI = curatorURI;
        return this;
    }

    /** adds the name of the dataset curator
     * 
     * @param curatorName the <b>literal</b> value for the dataset curator
     * @return the updated struct */
    public DirectoryStruct withCuratorName(String curatorName) {
        this.curatorName = curatorName;
        return this;
    }

    /** adds the name of the dataset owner
     * 
     * @param ownerName the <b>literal</b> value for the dataset owner
     * @return the updated struct */
    public DirectoryStruct withOwnerName(String ownerName) {
        this.ownerName = ownerName;
        return this;
    }

    /** adds the URI of the dataset owner
     * 
     * @param ownerURI the <b>URI</b> value for the dataset owner
     * @return the updated struct */
    public DirectoryStruct withOwnerURI(String ownerURI) {
        this.ownerURI = ownerURI;
        return this;
    }

    /** adds the name of the dataset publisher
     * 
     * @param publisherName the <b>literal</b> value for the dataset publisher
     * @return the updated struct */
    public DirectoryStruct withPublisherName(String publisherName) {
        this.publisherName = publisherName;
        return this;
    }

    /** adds the URI of the dataset publisher
     * 
     * @param publisherURI the <b>URI</b> value for the dataset publisher
     * @return the updated struct */
    public DirectoryStruct withPublisherURI(String publisherURI) {
        this.publisherURI = publisherURI;
        return this;
    }

    /** adds the description of the dataset 
     * 
     * @param description the <b>literal</b> value for the dataset description
     * @return the updated struct */
    public DirectoryStruct withDescription(String description) {
        this.description = description;
        return this;
    }

    /** adds the URL of the dataset location
     * 
     * @param locationURL the <b>URL</b> value for the dataset location
     * @return the updated struct */
    public DirectoryStruct withLocationURL(String locationURL) {
        this.locationURL = locationURL;
        return this;
    }

    /** adds the contact point of the dataset 
     * 
     * @param contactPoint the <b>literal</b> value for the dataset contact point
     * @return the updated struct */
    public DirectoryStruct withContactPoint(String contactPoint) {
        this.contactPoint = contactPoint;
        return this;
    }

    /** adds the URI of the parent dataset 
     * 
     * @param parentDatasetURI the <b>URI</b> value for the parent dataset
     * @return the updated struct */
    public DirectoryStruct withParentDatasetURI(String parentDatasetURI) {
        this.parentDatasetURI = parentDatasetURI;
        return this;
    }

    /** adds the name of the parent dataset 
     * 
     * @param parentDatasetName the <b>literal</b> value for the parent dataset
     * @return the updated struct */
    public DirectoryStruct withParentDatasetName(String parentDatasetName) {
        this.parentDatasetName = parentDatasetName;
        return this;
    }

    /** adds the ID of the dataset 
     * 
     * @param datasetID the <b>literal</b> value for the dataset ID
     * @return the updated struct */
    public DirectoryStruct withDatasetID(String datasetID) {
        this.datasetID = datasetID;
        return this;
    }

    /** adds the type of the dataset 
     * 
     * @param datasetType the <b>literal</b> value for the dataset type
     * @return the updated struct */
    public DirectoryStruct withDatasetType(String datasetType) {
        this.datasetType = datasetType;
        return this;
    }

    /** adds the URI of the dataset access method
     * 
     * @param accessMethodURI the <b>URI</b> value for the dataset access method
     * @return the updated struct */
    public DirectoryStruct withAccessMethodURI(String accessMethodURI) {
        this.accessMethodURI = accessMethodURI;
        return this;
    }

    /** adds the URI of the dataset publication event
     * 
     * @param publicationEventURI the <b>URI</b> value for the dataset publication event 
     * @return the updated struct */
    public DirectoryStruct withPublicationEventURI(String publicationEventURI) {
        this.publicationEventURI = publicationEventURI;
        return this;
    }

    /** adds the date of the dataset publication
     * 
     * @param publicationDate the <b>literal</b> value for the dataset publication date
     * @return the updated struct */
    public DirectoryStruct withPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
        return this;
    }

    /** adds the URI of the dataset attribute assignment event
     * 
     * @param attributeAssignmentEventURI the <b>URI</b> value for the dataset attribute assignment event
     * @return the updated struct */
    public DirectoryStruct withAttributeAssignmentEventURI(String attributeAssignmentEventURI) {
        this.attributeAssignmentEventURI = attributeAssignmentEventURI;
        return this;
    }

    /** adds the embargo state of the dataset 
     * 
     * @param embargoState the <b>literal</b> value for the dataset embargo state
     * @return the updated struct */
    public DirectoryStruct withEmbargoState(String embargoState) {
        this.embargoState = embargoState;
        return this;
    }

    /** adds the embargo period of the dataset 
     * 
     * @param embargoPeriod the <b>literal</b> value for the dataset embargo period
     * @return the updated struct */
    public DirectoryStruct withEmbargoPeriod(String embargoPeriod) {
        this.embargoPeriod = embargoPeriod;
        return this;
    }

    /**
     * Produces an NTRIPLES output so that it can be used to SPARQL queries
     *
     * @return an NTRIPLES representation of the struct fields
     */
    public String toNtriples() {
        String retTriples = "";
        if (!this.datasetURI.isEmpty()) {
            retTriples += "<" + this.datasetURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.datasetLabel + "> .\n";
            if (!this.datasetName.isEmpty()) {
                retTriples += "<" + this.datasetURI + "> <" + Resources.hasPreferredIdentifier + "> \"" + this.datasetName + "\" .\n";
            }
            if (!this.datasetID.isEmpty()) {
                retTriples += "<" + this.datasetURI + "> <" + Resources.rdfsLabel + "> \"" + this.datasetID + "\" .\n";
            }
            if (!this.imageURI.isEmpty()) {
                retTriples += "<" + this.datasetURI + "> <" + Resources.isDepictedBy + "> <" + this.imageURI + "> .\n";
            }
            if (!this.ownerURI.isEmpty()) {
                retTriples += "<" + this.datasetURI + "> <" + Resources.hasCurrentOwner + "> <" + this.ownerURI + "> .\n";
            }
            if (!this.keeperURI.isEmpty()) {
                retTriples += "<" + this.datasetURI + "> <" + Resources.hasCurrentKeeper + "> <" + this.keeperURI + "> .\n";
            }
            if (!this.creationEventURI.isEmpty()) {
                retTriples += "<" + this.datasetURI + "> <" + Resources.wasCreatedBy + "> <" + this.creationEventURI + "> .\n";
            }
            if (!this.curatorURI.isEmpty()) {
                retTriples += "<" + this.datasetURI + "> <" + Resources.hasCurator + "> <" + this.curatorURI + "> .\n";
            }
            if (!this.publicationEventURI.isEmpty()) {
                retTriples += "<" + this.datasetURI + "> <" + Resources.wasCreatedBy + "> <" + this.publicationEventURI + "> .\n";
            }
            if (!this.attributeAssignmentEventURI.isEmpty()) {
                retTriples += "<" + this.datasetURI + "> <" + Resources.wasAttributedBy + "> <" + this.attributeAssignmentEventURI + "> .\n";
            }
            if (!this.accessMethodURI.isEmpty()) {
                retTriples += "<" + this.datasetURI + "> <" + Resources.isReferredToBy + "> <" + this.accessMethodURI + "> .\n";
            }
            if (!this.datasetType.isEmpty()) {
                retTriples += "<" + this.datasetURI + "> <" + Resources.hasType + "> \"" + this.datasetType + "\" .\n";

            }
            if (!this.accessRightsURI.isEmpty()) {
                retTriples += "<" + this.datasetURI + "> <" + Resources.isSubjectTo + "> <" + this.accessRightsURI + "> .\n";
            }

            if (!this.rightsHolderURI.isEmpty()) {
                retTriples += "<" + this.datasetURI + "> <" + Resources.isPossessedBy + "> <" + this.rightsHolderURI + "> . \n";
            }

            if (!this.description.isEmpty()) {
                retTriples += "<" + this.datasetURI + "> <" + Resources.hasNote + "> \"" + this.description + "\" .\n";
            }
            if (!this.locationURL.isEmpty()) {
                retTriples += "<" + this.datasetURI + "> <" + Resources.isLocatedAt + "> <" + this.locationURL + "> .\n"
                        + "<" + this.locationURL + "> <" + Resources.hasType + "> \"URL\" .\n";
            }
            if (!this.parentDatasetURI.isEmpty()) {
                retTriples += "<" + this.datasetURI + "> <" + Resources.formsPartOf + "> <" + this.parentDatasetURI + "> .\n";

            }
            if (!this.contributors.isEmpty()) {
                for (Pair contributor : this.contributors) {
                    retTriples += "<" + this.datasetURI + "> <" + Resources.hasContributor + "> <" + contributor.getKey() + "> .\n"
                            + "<" + contributor.getKey() + "> <" + Resources.rdfTypeLabel + "> <" + Resources.actorLabel + "> .\n"
                            + "<" + contributor.getKey() + "> <" + Resources.rdfsLabel + "> \"" + contributor.getValue() + "\".\n";
                }
            }

        }
        if (!this.ownerURI.isEmpty()) {
            retTriples += "<" + this.ownerURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.actorLabel + "> .\n";
            if (!this.ownerName.isEmpty()) {
                retTriples += "<" + this.ownerURI + "> <" + Resources.rdfsLabel + "> \"" + this.ownerName + "\" .\n";
            }
        }

        if (!this.accessMethodURI.isEmpty()) {
            retTriples += "<" + this.accessMethodURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.procedureLabel + "> .\n";
            retTriples += "<" + this.accessMethodURI + "> <" + Resources.hasType + "> \"Access Method\" .\n";
            if (!this.accessMethod.isEmpty()) {
                retTriples += "<" + this.accessMethodURI + "> <" + Resources.hasNote + "> \"" + this.accessMethod + "\" .\n";
            }
        }

        if (!this.imageURI.isEmpty()) {
            retTriples += "<" + this.imageURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.imageLabel + "> .\n";
            if (!this.imageTitle.isEmpty()) {
                retTriples += "<" + this.imageURI + "> <" + Resources.rdfsLabel + "> \"" + this.imageTitle + "\" .\n";
            }
        }

        if (!this.accessRightsURI.isEmpty()) {
            retTriples += "<" + this.accessRightsURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.accessRightsLabel + "> .\n";
            if (!this.accessRights.isEmpty()) {
                retTriples += "<" + this.accessRightsURI + "> <" + Resources.rdfsLabel + "> \"" + this.accessRights + "\" .\n";
            }
        }
        if (!this.rightsHolderURI.isEmpty()) {
            retTriples += "<" + this.rightsHolderURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.actorLabel + "> .\n";
            if (!this.rightsHolderName.isEmpty()) {
                retTriples += "<" + this.rightsHolderURI + "> <" + Resources.rdfsLabel + "> \"" + this.rightsHolderName + "\" .\n";
            }
        }

        if (!this.creationEventURI.isEmpty()) {
            retTriples += "<" + this.creationEventURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.creationEventLabel + "> .\n";
            if (!this.creatorURI.isEmpty()) {
                retTriples += "<" + this.creationEventURI + "> <" + Resources.carriedOutBy + "> <" + this.creatorURI + "> .\n";
            }
            if (!this.creationDate.isEmpty()) {
                retTriples += "<" + this.creationEventURI + "> <" + Resources.hasTimespan + "> \"" + this.creationDate + "\" .\n";
            }
        }
        if (!this.creatorURI.isEmpty()) {
            retTriples += "<" + this.creatorURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.actorLabel + "> .\n";
            if (!this.creatorName.isEmpty()) {
                retTriples += "<" + this.creatorURI + "> <" + Resources.rdfsLabel+ "> \"" + this.creatorName + "\" .\n";
            }
        }

        if (!this.publicationEventURI.isEmpty()) {
            retTriples += "<" + this.publicationEventURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.publicationEventLabel + "> .\n";
            if (!this.publisherURI.isEmpty()) {
                retTriples += "<" + this.publicationEventURI + "> <" + Resources.carriedOutBy + "> <" + this.publisherURI + "> .\n";
            }
            if (!this.publicationDate.isEmpty()) {
                retTriples += "<" + this.publicationEventURI + "> <" + Resources.hasTimespan + "> \"" + this.publicationDate + "\" .\n";
            }
        }

        if (!this.publisherURI.isEmpty()) {
            retTriples += "<" + this.publisherURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.actorLabel + "> .\n";
            if (!this.publisherName.isEmpty()) {
                retTriples += "<" + this.publisherURI + "> <" + Resources.rdfsLabel+ "> \"" + this.publisherName + "\" .\n";
            }
        }

        if (!this.attributeAssignmentEventURI.isEmpty()) {
            retTriples += "<" + this.attributeAssignmentEventURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.attributeAssignmentEventLabel + "> .\n";
            if (!this.embargoState.isEmpty()) {
                retTriples += "<" + this.attributeAssignmentEventURI + "> <" + Resources.assigned + "> \"" + this.embargoState + "\" .\n";
            }
            if (!this.embargoPeriod.isEmpty()) {
                retTriples += "<" + this.attributeAssignmentEventURI + "> <" + Resources.hasTimespan + "> \"" + this.embargoPeriod + "\" .\n";
            }
        }

        if (!this.parentDatasetURI.isEmpty()) {
            retTriples += "<" + this.parentDatasetURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.datasetLabel + "> .\n";
            if (!this.parentDatasetName.isEmpty()) {
                retTriples += "<" + this.parentDatasetURI + "> <" + Resources.rdfsLabel+ "> \"" + this.parentDatasetName + "\" .\n";
            }
        }
        if (!this.curatorURI.isEmpty()) {
            retTriples += "<" + this.curatorURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.actorLabel + "> .\n";
            if (!this.curatorName.isEmpty()) {
                retTriples += "<" + this.curatorURI + "> <" + Resources.rdfsLabel+ "> \"" + this.curatorName + "\" .\n";
            }
            if (!this.contactPoint.isEmpty()) {
                retTriples += "<" + this.curatorURI + "> <" + Resources.hasContactPoint + "> \"" + this.contactPoint + "\" .\n";
            }
        }
        if (!this.keeperURI.isEmpty()) {
            retTriples += "<" + this.keeperURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.actorLabel + "> .\n";
            if (!this.keeperName.isEmpty()) {
                retTriples += "<" + this.keeperURI + "> <" + Resources.rdfsLabel + "> \"" + this.keeperName + "\".\n";
            }

        }
        logger.debug("Struct in NTriples format: \n" + retTriples);
        return retTriples;
    }

    /**
     * Validates the fields that will contain URIs
     *
     * @throws URIValidationException if any of the URIs is not in valid form
     */
    public void validateURIs() throws URIValidationException {
        if (!this.datasetURI.isEmpty()) {
            this.validateURI(this.datasetURI);
        }
        if (!this.keeperURI.isEmpty()) {
            this.validateURI(this.keeperURI);
        }
        if (!this.curatorURI.isEmpty()) {
            this.validateURI(this.curatorURI);
        }
        if (!this.ownerURI.isEmpty()) {
            this.validateURI(this.ownerURI);
        }
        if (!this.creatorURI.isEmpty()) {
            this.validateURI(this.creatorURI);
        }
        if (!this.rightsHolderURI.isEmpty()) {
            this.validateURI(this.rightsHolderURI);
        }
        if (!this.parentDatasetURI.isEmpty()) {
            this.validateURI(this.parentDatasetURI);
        }
        if (!this.publisherURI.isEmpty()) {
            this.validateURI(this.publisherURI);
        }
    }

    private void validateURI(String uri) throws URIValidationException {
        try {
            new URI(uri);
        } catch (URISyntaxException ex) {
            throw new URIValidationException("The given URI (\"" + uri + "\" is not valid", ex);
        }
    }

    @Override
    public String toString() {
        return "Dataset URI: " + this.datasetURI + "\t"
                + "Dataset name: " + this.datasetName + "\t"
                + "Dataset ID: " + this.datasetID + "\t"
                + "Dataset type: " + this.datasetType + "\t"
                + "Location URL: " + this.locationURL + "\t"
                + "Image URI: " + this.imageURI + "\t"
                + "Image title: " + this.imageTitle + "\t"
                + "Access method: " + this.accessMethod + "\t"
                + "Access method URI: " + this.accessMethodURI + "\t"
                + "Parent dataset URI: " + this.parentDatasetURI + "\t"
                + "Parent dataset name: " + this.parentDatasetName + "\t"
                + "Contributors: " + this.contributors + "\t"
                + "Curator URI: " + this.curatorURI + "\t"
                + "Curator name: " + this.curatorName + "\t"
                + "Owner URI: " + this.ownerURI + "\t"
                + "Owner name: " + this.ownerName + "\t"
                + "Publication event URI: " + this.publicationEventURI + "\t"
                + "Publication event date: " + this.publicationDate + "\t"
                + "Publisher URI: " + this.publisherURI + "\t"
                + "Publisher name: " + this.publisherName + "\t"
                + "CreationEvent URI: " + this.creationEventURI + "\t"
                + "Creation date: " + this.creationDate + "\t"
                + "Creator URI: " + this.creatorURI + "\t"
                + "Creator name: " + this.creatorName + "\t"
                + "AttributeAssignmentEvent URI: " + this.attributeAssignmentEventURI + "\t"
                + "Embargo period: " + this.embargoPeriod + "\t"
                + "Embargo State: " + this.embargoState + "\t"
                + "Keeper URI: " + this.keeperURI + "\t"
                + "Keeper name: " + this.keeperName + "\t"
                + "Access rights URI: " + this.accessRightsURI + "\t"
                + "Access rights: " + this.accessRights + "\t"
                + "Rights holder URI: " + this.rightsHolderURI + "\t"
                + "Rights holder name: " + this.rightsHolderName + "\t"
                + "Contact point: " + this.contactPoint + "\t"
                + "Description: " + this.description;
    }

    @Override
    public boolean equals(Object anotherObject) {
        if (anotherObject instanceof DirectoryStruct) {
            DirectoryStruct anotherStruct = (DirectoryStruct) anotherObject;
            return this.datasetName.equals(anotherStruct.getDatasetName())
                    && this.datasetURI.equals(anotherStruct.getDatasetURI())
                    && this.datasetID.equals(anotherStruct.getDatasetID())
                    && this.locationURL.equals(anotherStruct.getLocationURL())
                    && this.imageURI.equals(anotherStruct.getImageURI())
                    && this.imageTitle.equals(anotherStruct.getImageTitle())
                    && this.datasetType.equals(anotherStruct.getDatasetType())
                    && this.accessMethod.equals(anotherStruct.getAccessMethod())
                    && this.accessMethodURI.equals(anotherStruct.getAccessMethodURI())
                    && this.parentDatasetURI.equals(anotherStruct.getParentDatasetURI())
                    && this.parentDatasetName.equals(anotherStruct.getParentDatasetName())
                    && this.contributors.equals(anotherStruct.getContributors())
                    && this.curatorURI.equals(anotherStruct.getCuratorURI())
                    && this.curatorName.equals(anotherStruct.getCuratorName())
                    && this.ownerName.equals(anotherStruct.getOwnerName())
                    && this.ownerURI.equals(anotherStruct.getOwnerURI())
                    && this.publicationEventURI.equals(anotherStruct.getPublicationEventURI())
                    && this.publicationDate.equals(anotherStruct.getPublicationDate())
                    && this.publisherName.equals(anotherStruct.getPublisherName())
                    && this.publisherURI.equals(anotherStruct.getPublisherURI())
                    && this.creationEventURI.equals(anotherStruct.getCreationEventURI())
                    && this.creationDate.equals(anotherStruct.getCreationDate())
                    && this.creatorName.equals(anotherStruct.getCreatorName())
                    && this.creatorURI.equals(anotherStruct.getCreatorURI())
                    && this.attributeAssignmentEventURI.equals(anotherStruct.getAttributeAssignmentEventURI())
                    && this.embargoPeriod.equals(anotherStruct.getEmbargoPeriod())
                    && this.embargoState.equals(anotherStruct.getEmbargoState())
                    && this.keeperName.equals(anotherStruct.getKeeperName())
                    && this.keeperURI.equals(anotherStruct.getKeeperURI())
                    && this.accessRights.equals(anotherStruct.getAccessRights())
                    && this.accessRightsURI.equals(anotherStruct.getAccessRightsURI())
                    && this.rightsHolderName.equals(anotherStruct.getRightsHolderName())
                    && this.rightsHolderURI.equals(anotherStruct.getRightsHolderURI())
                    && this.contactPoint.equals(anotherStruct.getContactPoint())
                    && this.description.equals(anotherStruct.getDescription());
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.datasetURI);
        return hash;
    }
}
