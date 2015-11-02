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

    public String getDatasetName() {
        return datasetName;
    }

    public String getDatasetURI() {
        return datasetURI;
    }

    public String getImageURI() {
        return imageURI;
    }

    public String getImageTitle() {
        return imageTitle;
    }

    public String getCreationEventURI() {
        return creationEventURI;
    }

    public Collection<String> getContributorNames() {
        Set<String> contributorNames = new HashSet<>();
        for (Pair contributor : this.contributors) {
            contributorNames.add(contributor.getValue());
        }
        return contributorNames;
    }

    public Collection<String> getContributorURIs() {
        Set<String> contributorURIs = new HashSet<>();
        for (Pair contributor : this.contributors) {
            contributorURIs.add(contributor.getKey());
        }
        return contributorURIs;
    }

    public List<Pair> getContributors() {
        return this.contributors;
    }

    public String getKeeperName() {
        return keeperName;
    }

    public String getKeeperURI() {
        return keeperURI;
    }

    public String getAccessRights() {
        return accessRights;
    }

    public String getAccessRightsURI() {
        return accessRightsURI;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public String getCreatorURI() {
        return creatorURI;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public String getPublisherURI() {
        return publisherURI;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public String getRightsHolderURI() {
        return rightsHolderURI;
    }

    public String getRightsHolderName() {
        return rightsHolderName;
    }

    public String getAccessMethod() {
        return accessMethod;
    }

    public String getAccessMethodURI() {
        return accessMethodURI;
    }

    public String getCuratorURI() {
        return curatorURI;
    }

    public String getCuratorName() {
        return curatorName;
    }

    public String getOwnerURI() {
        return ownerURI;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public String getDescription() {
        return description;
    }

    public String getLocationURL() {
        return locationURL;
    }

    public String getContactPoint() {
        return contactPoint;
    }

    public String getParentDatasetURI() {
        return parentDatasetURI;
    }

    public String getParentDatasetName() {
        return parentDatasetName;
    }

    public String getDatasetID() {
        return datasetID;
    }

    public String getDatasetType() {
        return datasetType;
    }

    public String getPublicationEventURI() {
        return publicationEventURI;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public String getAttributeAssignmentEventURI() {
        return attributeAssignmentEventURI;
    }

    public String getEmbargoState() {
        return embargoState;
    }

    public String getEmbargoPeriod() {
        return embargoPeriod;
    }

    public void setDatasetName(String datasetName) {
        this.datasetName = datasetName;
    }

    public void setDatasetURI(String datasetURI) {
        this.datasetURI = datasetURI;
    }

    public void setContributors(List<Pair> contributors) {
        this.contributors = contributors;
    }

    public void setKeeperName(String keeperName) {
        this.keeperName = keeperName;
    }

    public void setKeeperURI(String keeperURI) {
        this.keeperURI = keeperURI;
    }

    public void setImageTitle(String imageTitle) {
        this.imageTitle = imageTitle;
    }

    public void setImageURI(String imageURI) {
        this.imageURI = imageURI;
    }

    public void setAccessRights(String accessRights) {
        this.accessRights = accessRights;
    }

    public void setRightsHolderName(String rightsHolderName) {
        this.rightsHolderName = rightsHolderName;
    }

    public void setRightsHolderURI(String rightsHolderURI) {
        this.rightsHolderURI = rightsHolderURI;
    }

    public void setAccessMethod(String accessMethod) {
        this.accessMethod = accessMethod;
    }

    public void setCuratorURI(String curatorURI) {
        this.curatorURI = curatorURI;
    }

    public void setCuratorName(String curatorName) {
        this.curatorName = curatorName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public void setOwnerURI(String ownerURI) {
        this.ownerURI = ownerURI;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public void setPublisherURI(String publisherURI) {
        this.publisherURI = publisherURI;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public void setCreatorURI(String creatorURI) {
        this.creatorURI = creatorURI;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCreationEventURI(String creationEventURI) {
        this.creationEventURI = creationEventURI;
    }

    public void setLocationURL(String locationURL) {
        this.locationURL = locationURL;
    }

    public void setContactPoint(String contactPoint) {
        this.contactPoint = contactPoint;
    }

    public void setParentDatasetURI(String parentDatasetURI) {
        this.parentDatasetURI = parentDatasetURI;
    }

    public void setAccessRightsURI(String accessRightsURI) {
        this.accessRightsURI = accessRightsURI;
    }

    public void setParentDatasetName(String parentDatasetName) {
        this.parentDatasetName = parentDatasetName;
    }

    public void setDatasetID(String datasetID) {
        this.datasetID = datasetID;
    }

    public void setDatasetType(String datasetType) {
        this.datasetType = datasetType;
    }

    public void setAccessMethodURI(String accessMethodURI) {
        this.accessMethodURI = accessMethodURI;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }
    
    public void setPublicationEventURI(String publicationEventURI){
        this.publicationEventURI=publicationEventURI;
    }

    public void setAttributeAssignmentEventURI(String attributeAssignmentEventURI) {
        this.attributeAssignmentEventURI = attributeAssignmentEventURI;
    }

    public void setEmbargoState(String embargoState) {
        this.embargoState = embargoState;
    }

    public void setEmbargoPeriod(String embargoPeriod) {
        this.embargoPeriod = embargoPeriod;
    }

    public DirectoryStruct withDatasetURI(String datasetURI) {
        this.datasetURI = datasetURI;
        return this;
    }

    public DirectoryStruct withCreationEventURI(String creationEventURI) {
        this.creationEventURI = creationEventURI;
        return this;
    }

    public DirectoryStruct withDatasetName(String datasetName) {
        this.datasetName = datasetName;
        return this;
    }

    public DirectoryStruct withContributor(String contributorURI, String contributorName) {
        if (!this.getContributorURIs().contains(contributorURI)) {
            this.contributors.add(new Pair(contributorURI, contributorName));
        }
        return this;
    }

    public DirectoryStruct withKeeperName(String keeperName) {
        this.keeperName = keeperName;
        return this;
    }

    public DirectoryStruct withKeeperURI(String keeperURI) {
        this.keeperURI = keeperURI;
        return this;
    }

    public DirectoryStruct withImageTitle(String imageTitle) {
        this.imageTitle = imageTitle;
        return this;
    }

    public DirectoryStruct withImageURI(String imageURI) {
        this.imageURI = imageURI;
        return this;
    }

    public DirectoryStruct withCreatorURI(String creatorURI) {
        this.creatorURI = creatorURI;
        return this;
    }

    public DirectoryStruct withCreatorName(String creatorName) {
        this.creatorName = creatorName;
        return this;
    }

    public DirectoryStruct withCreationDate(String creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public DirectoryStruct withAccessRights(String accessRights) {
        this.accessRights = accessRights;
        return this;
    }

    public DirectoryStruct withAccessRightsURI(String accessRightsURI) {
        this.accessRightsURI = accessRightsURI;
        return this;
    }

    public DirectoryStruct withRightsHolderURI(String rightsHolderURI) {
        this.rightsHolderURI = rightsHolderURI;
        return this;
    }

    public DirectoryStruct withRightsHolderName(String rightsHolderName) {
        this.rightsHolderName = rightsHolderName;
        return this;
    }

    public DirectoryStruct withAccessMethod(String accessMethod) {
        this.accessMethod = accessMethod;
        return this;
    }

    public DirectoryStruct withCuratorURI(String curatorURI) {
        this.curatorURI = curatorURI;
        return this;
    }

    public DirectoryStruct withCuratorName(String curatorName) {
        this.curatorName = curatorName;
        return this;
    }

    public DirectoryStruct withOwnerName(String ownerName) {
        this.ownerName = ownerName;
        return this;
    }

    public DirectoryStruct withOwnerURI(String ownerURI) {
        this.ownerURI = ownerURI;
        return this;
    }

    public DirectoryStruct withPublisherName(String publisherName) {
        this.publisherName = publisherName;
        return this;
    }

    public DirectoryStruct withPublisherURI(String publisherURI) {
        this.publisherURI = publisherURI;
        return this;
    }

    public DirectoryStruct withDescription(String description) {
        this.description = description;
        return this;
    }

    public DirectoryStruct withLocationURL(String locationURL) {
        this.locationURL = locationURL;
        return this;
    }

    public DirectoryStruct withContactPoint(String contactPoint) {
        this.contactPoint = contactPoint;
        return this;
    }

    public DirectoryStruct withParentDatasetURI(String parentDatasetURI) {
        this.parentDatasetURI = parentDatasetURI;
        return this;
    }

    public DirectoryStruct withParentDatasetName(String parentDatasetName) {
        this.parentDatasetName = parentDatasetName;
        return this;
    }

    public DirectoryStruct withDatasetID(String datasetID) {
        this.datasetID = datasetID;
        return this;
    }

    public DirectoryStruct withDatasetType(String datasetType) {
        this.datasetType = datasetType;
        return this;
    }

    public DirectoryStruct withAccessMethodURI(String accessMethodURI) {
        this.accessMethodURI = accessMethodURI;
        return this;
    }

    public DirectoryStruct withPublicationEventURI(String publicationEventURI) {
        this.publicationEventURI = publicationEventURI;
        return this;
    }

    public DirectoryStruct withPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
        return this;
    }

    public DirectoryStruct withAttributeAssignmentEventURI(String attributeAssignmentEventURI) {
        this.attributeAssignmentEventURI = attributeAssignmentEventURI;
        return this;
    }

    public DirectoryStruct withEmbargoState(String embargoState) {
        this.embargoState = embargoState;
        return this;
    }

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
