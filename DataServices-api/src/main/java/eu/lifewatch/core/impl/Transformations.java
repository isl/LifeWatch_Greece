package eu.lifewatch.core.impl;

import eu.lifewatch.common.Resources;
import eu.lifewatch.core.model.Pair;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import org.gbif.dwc.terms.DwcTerm;
import org.gbif.dwc.text.Archive;
import org.gbif.dwc.text.ArchiveFactory;
import org.gbif.dwc.text.StarRecord;

/**
 * STATUS = UNCOMPLETED
 *
 * @author Nikos Minadakis (minadakn 'at' ics 'dot' forth 'dot' gr)
 * @author Yannis Marketakis (marketak 'at' ics 'dot' forth 'dot' gr)
 */
public class Transformations {

    public ArrayList<String> transform_data_collection_csv(String fileToParse, String repositoryGraph)
            throws FileNotFoundException, IOException {

        BufferedReader fileReader = null;

        final String DELIMITER = ";";

        String line = "";
        String query = "";
        ArrayList<String> Queries = new ArrayList<String>();

        fileReader = new BufferedReader(new FileReader(fileToParse));
        String URIprefix = "http://www.lifewatchgreece.eu/entity";
        int count = 0;

        while ((line = fileReader.readLine()) != null) {
            if (count == 0) {
                count++;
                continue;
            }

            String[] tokens = line.split(DELIMITER);

            String datasetID = tokens[0];
            String parentDataset = tokens[1];
            String datasetTitle = tokens[2];
            String creatorName = tokens[3];
            String creationDate = tokens[4];
            String contributorName = tokens[5];
            String ownerName = tokens[6];
            String keeperName = tokens[7];
            String curatorName = tokens[8];
            String contactPoint = tokens[9];
            String accessRights = tokens[10];
            String rightsHolder = tokens[11];
            String description = tokens[12];
            String accessMethod = tokens[13];
            String publisherName = tokens[14];
            String publicationDate = tokens[15];
            String imageURL = tokens[16];
            String datasetType = tokens[17];
            String locationURL = tokens[18];
            String embargoState = tokens[19];
            String embargoPeriod = tokens[20];

            String datasetURI = createURI(URIprefix, datasetTitle, "dataset");

            String creationEventLabel = "Creation of "+datasetTitle+" dataset";
            String publicationEventLabel = "Publication of "+datasetTitle+" dataset";
            String attributeAssignmentEventLabel = "Embargo assignment of "+datasetTitle+" dataset";
            
            String parentDatasetURI = "";
            if (parentDataset.startsWith("http:")) {
                parentDatasetURI = parentDataset;
            } else {
                parentDatasetURI = createURI(URIprefix, parentDataset, "dataset");
            }

            String creatorURI = "";
            if (!creatorName.isEmpty()) {
                creatorURI = createURI(URIprefix, creatorName, "actor");
            }

            String contributorURI = "";
            if (!contributorName.isEmpty()) {
                contributorURI = createURI(URIprefix, contributorName, "actor");
            }

            String ownerURI = createURI(URIprefix, ownerName, "actor");

            String keeperURI = "";
            if (!keeperName.isEmpty()) {
                keeperURI = createURI(URIprefix, keeperName, "actor");
            }

            String curatorURI = createURI(URIprefix, curatorName, "actor");

            String rightsHolderURI = "";
            if (!rightsHolder.isEmpty()) {
                rightsHolderURI = createURI(URIprefix, rightsHolder, "actor");
            }

            String accessRightsURI = "";

            if (!accessRights.isEmpty()) {
                if (!accessRights.startsWith("http")) {
                    accessRightsURI = createURI(URIprefix, accessRights, "rights");
                } else {
                    accessRightsURI = accessRights;
                }
            }

            String publisherURI = "";
            if (!publisherName.isEmpty()) {
                publisherURI = createURI(URIprefix, publisherName, "actor");
            }

            String creationEventURI = "";
            if (!creationDate.isEmpty() || !creatorName.isEmpty()) {
                creationEventURI = createURI(URIprefix, datasetTitle + "Creation", "creationEvent");
            }

            String publicationEventURI = "";
            if (!publicationDate.isEmpty() || !publisherName.isEmpty()) {
                publicationEventURI = createURI(URIprefix, datasetTitle + "Publication", "publicationEvent");
            }

            String attributeAssignmentEventURI = "";
            if (!embargoState.isEmpty() || !embargoPeriod.isEmpty()) {
                attributeAssignmentEventURI = createURI(URIprefix, datasetTitle + "AttributAssignment", "attributeAssignmentEvent");
            }

            String accessMethodURI = "";
            if (!accessMethod.isEmpty()) {
                accessMethodURI = createURI(URIprefix, datasetTitle + "AccessMethod", "procedure");
            }

            String retTriples = "INSERT INTO <" + repositoryGraph + "> { ";

            if (!datasetURI.isEmpty()) {
                retTriples += "<" + datasetURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.datasetLabel + "> .\n";
                if (!datasetTitle.isEmpty()) {
                    retTriples += "<" + datasetURI + "> <" + Resources.hasPreferredIdentifier + "> \"" + datasetTitle + "\" .\n";
                }
                if (!datasetID.isEmpty()) {
                    retTriples += "<" + datasetURI + "> <" + Resources.rdfsLabel + "> \"" + datasetID + "\" .\n";
                }
                if (!imageURL.isEmpty()) {
                    retTriples += "<" + datasetURI + "> <" + Resources.isDepictedBy + "> <" + imageURL + "> .\n";
                }
                if (!ownerURI.isEmpty()) {
                    retTriples += "<" + datasetURI + "> <" + Resources.hasCurrentOwner + "> <" + ownerURI + "> .\n";
                }

                if (!keeperURI.isEmpty()) {
                    retTriples += "<" + datasetURI + "> <" + Resources.hasCurrentKeeper + "> <" + keeperURI + "> .\n";
                }
                if (!creationEventURI.isEmpty()) {
                    retTriples += "<" + datasetURI + "> <" + Resources.wasCreatedBy + "> <" + creationEventURI + "> .\n";
                }
                if (!curatorURI.isEmpty()) {
                    retTriples += "<" + datasetURI + "> <" + Resources.hasCurator + "> <" + curatorURI + "> .\n";
                }
                if (!publicationEventURI.isEmpty()) {
                    retTriples += "<" + datasetURI + "> <" + Resources.wasCreatedBy + "> <" + publicationEventURI + "> .\n";
                }
                if (!attributeAssignmentEventURI.isEmpty()) {
                    retTriples += "<" + datasetURI + "> <" + Resources.wasAttributedBy + "> <" + attributeAssignmentEventURI + "> .\n";
                }
                if (!accessMethodURI.isEmpty()) {
                    retTriples += "<" + datasetURI + "> <" + Resources.isReferredToBy + "> <" + accessMethodURI + "> .\n";
                }
                if (!datasetType.isEmpty()) {
                    retTriples += "<" + datasetURI + "> <" + Resources.hasType + "> \"" + datasetType + "\" .\n";

                }
                if (!accessRightsURI.isEmpty()) {
                    retTriples += "<" + datasetURI + "> <" + Resources.isSubjectTo + "> <" + accessRightsURI + "> .\n";
                }
                if (!description.isEmpty()) {
                    retTriples += "<" + datasetURI + "> <" + Resources.hasNote + "> \"" + description + "\" .\n";
                }
                if (!locationURL.isEmpty()) {
                    retTriples += "<" + datasetURI + "> <" + Resources.isLocatedAt + "> <" + locationURL + "> .\n"
                            + "<" + locationURL + "> <" + Resources.hasType + "> \"URL\" .\n";
                }
                if (!parentDatasetURI.isEmpty()) {
                    retTriples += "<" + datasetURI + "> <" + Resources.formsPartOf + "> <" + parentDatasetURI + "> .\n";

                }

                if (!rightsHolderURI.isEmpty()) {
                    retTriples += "<" + datasetURI + "> <" + Resources.isPossessedBy + "> <" + rightsHolderURI + "> . \n";
                }

                if (!contributorURI.isEmpty()) {

                    retTriples += "<" + datasetURI + "> <" + Resources.hasContributor + "> <" + contributorURI + "> .\n"
                            + "<" + contributorURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.actorLabel + "> .\n"
                            + "<" + contributorURI + "> <" + Resources.rdfsLabel + "> \"" + contributorName + "\".\n";
                }
            }

            if (!ownerURI.isEmpty()) {
                retTriples += "<" + ownerURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.actorLabel + "> .\n";
                if (!ownerName.isEmpty()) {
                    retTriples += "<" + ownerURI + "> <" + Resources.rdfsLabel + "> \"" + ownerName + "\" .\n";
                }
            }

            if (!accessMethodURI.isEmpty()) {
                retTriples += "<" + accessMethodURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.procedureLabel + "> .\n";
                retTriples += "<" + accessMethodURI + "> <" + Resources.hasType + "> \"Access Method\" .\n";
                if (!accessMethod.isEmpty()) {
                    retTriples += "<" + accessMethodURI + "> <" + Resources.hasNote + "> \"" + accessMethod + "\" .\n";
                }
            }

            if (!imageURL.isEmpty()) {
                retTriples += "<" + imageURL + "> <" + Resources.rdfTypeLabel + "> <" + Resources.imageLabel + "> .\n";
//            if(!imageTitle.isEmpty()){
//                retTriples+= "<"+imageURI+"> <"+Resources.isIdentifiedBy+"> \""+imageTitle+"\" .\n";
//            }
            }

            if (!accessRightsURI.isEmpty()) {
                retTriples += "<" + accessRightsURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.accessRightsLabel + "> .\n";
                if (!accessRights.isEmpty()) {
                    retTriples += "<" + accessRightsURI + "> <" + Resources.rdfsLabel + "> \"" + accessRights + "\" .\n";
                }
//            if(!rightsHolderURI.isEmpty()){
//                retTriples+= "<"+accessRightsURI+"> <"+Resources.isPossessedBy+"> <"+rightsHolderURI+"> . \n";
//            }
            }
            if (!rightsHolderURI.isEmpty()) {
                retTriples += "<" + rightsHolderURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.actorLabel + "> .\n";
                if (!rightsHolder.isEmpty()) {
                    retTriples += "<" + rightsHolderURI + "> <" + Resources.rdfsLabel + "> \"" + rightsHolder + "\" .\n";
                }
            }

            if (!creationEventURI.isEmpty()) {
                retTriples += "<" + creationEventURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.creationEventLabel + "> .\n";
                if (!creatorURI.isEmpty()) {
                    retTriples += "<" + creationEventURI + "> <" + Resources.carriedOutBy + "> <" + creatorURI + "> .\n";
                }
                if (!creationDate.isEmpty()) {
                    retTriples += "<" + creationEventURI + "> <" + Resources.hasTimespan + "> \"" + creationDate + "\" .\n";
                }
                if (!creationEventLabel.isEmpty()) {
                    retTriples += "<" + creationEventURI + "> <" + Resources.rdfsLabel + "> \"" + creationEventLabel + "\" .\n";
                }
            }
            if (!creatorURI.isEmpty()) {
                retTriples += "<" + creatorURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.actorLabel + "> .\n";
                if (!creatorName.isEmpty()) {
                    retTriples += "<" + creatorURI + "> <" + Resources.rdfsLabel+ "> \"" + creatorName + "\" .\n";
                }
            }

            if (!publicationEventURI.isEmpty()) {
                retTriples += "<" + publicationEventURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.publicationEventLabel + "> .\n";
                if (!publisherURI.isEmpty()) {
                    retTriples += "<" + publicationEventURI + "> <" + Resources.carriedOutBy + "> <" + publisherURI + "> .\n";
                }
                if (!publicationDate.isEmpty()) {
                    retTriples += "<" + publicationEventURI + "> <" + Resources.hasTimespan + "> \"" + publicationDate + "\" .\n";
                }
                
                 if (!publicationEventLabel.isEmpty()) {
                    retTriples += "<" + publicationEventURI + "> <" + Resources.rdfsLabel + "> \"" + publicationEventLabel + "\" .\n";
                }
            }

            if (!publisherURI.isEmpty()) {
                retTriples += "<" + publisherURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.actorLabel + "> .\n";
                if (!publisherName.isEmpty()) {
                    retTriples += "<" + publisherURI + "> <" + Resources.rdfsLabel + "> \"" + publisherName + "\" .\n";
                }
            }

            if (!attributeAssignmentEventURI.isEmpty()) {
                retTriples += "<" + attributeAssignmentEventURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.attributeAssignmentEventLabel + "> .\n";
                if (!embargoState.isEmpty()) {
                    retTriples += "<" + attributeAssignmentEventURI + "> <" + Resources.assigned + "> \"" + embargoState + "\" .\n";
                }
                if (!embargoPeriod.isEmpty()) {
                    retTriples += "<" + attributeAssignmentEventURI + "> <" + Resources.hasTimespan + "> \"" + embargoPeriod + "\" .\n";
                }
                if (!attributeAssignmentEventLabel.isEmpty()) {
                    retTriples += "<" + attributeAssignmentEventURI + "> <" + Resources.rdfsLabel + "> \"" + attributeAssignmentEventLabel + "\" .\n";
                }
            }

            if (!parentDatasetURI.isEmpty()) {
                retTriples += "<" + parentDatasetURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.datasetLabel + "> .\n";
                if (!parentDataset.isEmpty()) {
                    retTriples += "<" + parentDatasetURI + "> <" + Resources.rdfsLabel+ "> \"" + parentDataset + "\" .\n";
                }
            }
            if (!curatorURI.isEmpty()) {
                retTriples += "<" + curatorURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.actorLabel + "> .\n";
                if (!curatorName.isEmpty()) {
                    retTriples += "<" + curatorURI + "> <" + Resources.rdfsLabel + "> \"" + curatorName + "\" .\n";
                }
                if (!contactPoint.isEmpty()) {
                    retTriples += "<" + curatorURI + "> <" + Resources.hasContactPoint + "> \"" + contactPoint + "\" .\n";
                }
            }
            if (!keeperURI.isEmpty()) {
                retTriples += "<" + keeperURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.actorLabel + "> .\n";
                if (!keeperName.isEmpty()) {
                    retTriples += "<" + keeperURI + "> <" + Resources.rdfsLabel + "> \"" + keeperName + "\".\n";
                }

            }

            retTriples += " } ";

            Queries.add(retTriples);

            //System.out.println(retTriples);
        }

        return Queries;

    }

    public ArrayList<String> transform_data_collection_csv(String fileToParse, String repositoryGraph,
            String datasetName)
            throws FileNotFoundException, IOException {

        BufferedReader fileReader = null;

        final String DELIMITER = ";";

        String line = "";
        String query = "";
        ArrayList<String> Queries = new ArrayList<String>();

        fileReader = new BufferedReader(new FileReader(fileToParse));
        String URIprefix = "http://www.lifewatchgreece.eu/entity";
        int count = 0;

        while ((line = fileReader.readLine()) != null) {
            if (count == 0) {
                count++;
                continue;
            }

            String[] tokens = line.split(DELIMITER);

            String datasetID = tokens[0];
            String parentDataset = tokens[1];
            //String datasetTitle = tokens[2];
            String datasetTitle = datasetName;
            String creatorName = tokens[3];
            String creationDate = tokens[4];
            String contributorName = tokens[5];
            String ownerName = tokens[6];
            String keeperName = tokens[7];
            String curatorName = tokens[8];
            String contactPoint = tokens[9];
            String accessRights = tokens[10];
            String rightsHolder = tokens[11];
            String description = tokens[12];
            String accessMethod = tokens[13];
            String publisherName = tokens[14];
            String publicationDate = tokens[15];
            String imageURL = tokens[16];
            String datasetType = tokens[17];
            String locationURL = tokens[18];
            String embargoState = tokens[19];
            String embargoPeriod = tokens[20];

            String datasetURI = createURI(URIprefix, datasetTitle, "dataset");

            String creationEventLabel = "Creation of "+datasetTitle+" dataset";
            String publicationEventLabel = "Publication of "+datasetTitle+" dataset";
            String attributeAssignmentEventLabel = "Embargo assignment of "+datasetTitle+" dataset";
            
            String parentDatasetURI = "";
            if (parentDataset.startsWith("http:")) {
                parentDatasetURI = parentDataset;
            } else {
                parentDatasetURI = createURI(URIprefix, parentDataset, "dataset");
            }

            String creatorURI = "";
            if (!creatorName.isEmpty()) {
                creatorURI = createURI(URIprefix, creatorName, "actor");
            }

            String contributorURI = "";
            if (!contributorName.isEmpty()) {
                contributorURI = createURI(URIprefix, contributorName, "actor");
            }

            String ownerURI = createURI(URIprefix, ownerName, "actor");

            String keeperURI = "";
            if (!keeperName.isEmpty()) {
                keeperURI = createURI(URIprefix, keeperName, "actor");
            }

            String curatorURI = createURI(URIprefix, curatorName, "actor");

            String rightsHolderURI = "";
            if (!rightsHolder.isEmpty()) {
                rightsHolderURI = createURI(URIprefix, rightsHolder, "actor");
            }

            String accessRightsURI = "";

            if (!accessRights.isEmpty()) {
                if (!accessRights.startsWith("http")) {
                    accessRightsURI = createURI(URIprefix, accessRights, "rights");
                } else {
                    accessRightsURI = accessRights;
                }
            }

            String publisherURI = "";
            if (!publisherName.isEmpty()) {
                publisherURI = createURI(URIprefix, publisherName, "actor");
            }

            String creationEventURI = "";
            if (!creationDate.isEmpty() || !creatorName.isEmpty()) {
                creationEventURI = createURI(URIprefix, datasetTitle + "Creation", "creationEvent");
            }

            String publicationEventURI = "";
            if (!publicationDate.isEmpty() || !publisherName.isEmpty()) {
                publicationEventURI = createURI(URIprefix, datasetTitle + "Publication", "publicationEvent");
            }

            String attributeAssignmentEventURI = "";
            if (!embargoState.isEmpty() || !embargoPeriod.isEmpty()) {
                attributeAssignmentEventURI = createURI(URIprefix, datasetTitle + "AttributAssignment", "attributeAssignmentEvent");
            }

            String accessMethodURI = "";
            if (!accessMethod.isEmpty()) {
                accessMethodURI = createURI(URIprefix, datasetTitle + "AccessMethod", "procedure");
            }

            String retTriples = "INSERT INTO <" + repositoryGraph + "> { ";

            if (!datasetURI.isEmpty()) {
                retTriples += "<" + datasetURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.datasetLabel + "> .\n";
                if (!datasetTitle.isEmpty()) {
                    retTriples += "<" + datasetURI + "> <" + Resources.hasPreferredIdentifier + "> \"" + datasetTitle + "\" .\n";
                }
                if (!datasetID.isEmpty()) {
                    retTriples += "<" + datasetURI + "> <" + Resources.rdfsLabel + "> \"" + datasetID + "\" .\n";
                }
                if (!imageURL.isEmpty()) {
                    retTriples += "<" + datasetURI + "> <" + Resources.isDepictedBy + "> <" + imageURL + "> .\n";
                }
                if (!ownerURI.isEmpty()) {
                    retTriples += "<" + datasetURI + "> <" + Resources.hasCurrentOwner + "> <" + ownerURI + "> .\n";
                }

                if (!keeperURI.isEmpty()) {
                    retTriples += "<" + datasetURI + "> <" + Resources.hasCurrentKeeper + "> <" + keeperURI + "> .\n";
                }
                if (!creationEventURI.isEmpty()) {
                    retTriples += "<" + datasetURI + "> <" + Resources.wasCreatedBy + "> <" + creationEventURI + "> .\n";
                }
                if (!curatorURI.isEmpty()) {
                    retTriples += "<" + datasetURI + "> <" + Resources.hasCurator + "> <" + curatorURI + "> .\n";
                }
                if (!publicationEventURI.isEmpty()) {
                    retTriples += "<" + datasetURI + "> <" + Resources.wasCreatedBy + "> <" + publicationEventURI + "> .\n";
                }
                if (!attributeAssignmentEventURI.isEmpty()) {
                    retTriples += "<" + datasetURI + "> <" + Resources.wasAttributedBy + "> <" + attributeAssignmentEventURI + "> .\n";
                }
                if (!accessMethodURI.isEmpty()) {
                    retTriples += "<" + datasetURI + "> <" + Resources.isReferredToBy + "> <" + accessMethodURI + "> .\n";
                }
                if (!datasetType.isEmpty()) {
                    retTriples += "<" + datasetURI + "> <" + Resources.hasType + "> \"" + datasetType + "\" .\n";

                }
                if (!accessRightsURI.isEmpty()) {
                    retTriples += "<" + datasetURI + "> <" + Resources.isSubjectTo + "> <" + accessRightsURI + "> .\n";
                }
                if (!description.isEmpty()) {
                    retTriples += "<" + datasetURI + "> <" + Resources.hasNote + "> \"" + description + "\" .\n";
                }
                if (!locationURL.isEmpty()) {
                    retTriples += "<" + datasetURI + "> <" + Resources.isLocatedAt + "> <" + locationURL + "> .\n"
                            + "<" + locationURL + "> <" + Resources.hasType + "> \"URL\" .\n";
                }
                if (!parentDatasetURI.isEmpty()) {
                    retTriples += "<" + datasetURI + "> <" + Resources.formsPartOf + "> <" + parentDatasetURI + "> .\n";

                }

                if (!rightsHolderURI.isEmpty()) {
                    retTriples += "<" + datasetURI + "> <" + Resources.isPossessedBy + "> <" + rightsHolderURI + "> . \n";
                }

                if (!contributorURI.isEmpty()) {

                    retTriples += "<" + datasetURI + "> <" + Resources.hasContributor + "> <" + contributorURI + "> .\n"
                            + "<" + contributorURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.actorLabel + "> .\n"
                            + "<" + contributorURI + "> <" + Resources.rdfsLabel + "> \"" + contributorName + "\".\n";
                }
            }

            if (!ownerURI.isEmpty()) {
                retTriples += "<" + ownerURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.actorLabel + "> .\n";
                if (!ownerName.isEmpty()) {
                    retTriples += "<" + ownerURI + "> <" + Resources.rdfsLabel + "> \"" + ownerName + "\" .\n";
                }
            }

            if (!accessMethodURI.isEmpty()) {
                retTriples += "<" + accessMethodURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.procedureLabel + "> .\n";
                retTriples += "<" + accessMethodURI + "> <" + Resources.hasType + "> \"Access Method\" .\n";
                if (!accessMethod.isEmpty()) {
                    retTriples += "<" + accessMethodURI + "> <" + Resources.hasNote + "> \"" + accessMethod + "\" .\n";
                }
            }

            if (!imageURL.isEmpty()) {
                retTriples += "<" + imageURL + "> <" + Resources.rdfTypeLabel + "> <" + Resources.imageLabel + "> .\n";
//            if(!imageTitle.isEmpty()){
//                retTriples+= "<"+imageURI+"> <"+Resources.isIdentifiedBy+"> \""+imageTitle+"\" .\n";
//            }
            }

            if (!accessRightsURI.isEmpty()) {
                retTriples += "<" + accessRightsURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.accessRightsLabel + "> .\n";
                if (!accessRights.isEmpty()) {
                    retTriples += "<" + accessRightsURI + "> <" + Resources.rdfsLabel + "> \"" + accessRights + "\" .\n";
                }
//            if(!rightsHolderURI.isEmpty()){
//                retTriples+= "<"+accessRightsURI+"> <"+Resources.isPossessedBy+"> <"+rightsHolderURI+"> . \n";
//            }
            }
            if (!rightsHolderURI.isEmpty()) {
                retTriples += "<" + rightsHolderURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.actorLabel + "> .\n";
                if (!rightsHolder.isEmpty()) {
                    retTriples += "<" + rightsHolderURI + "> <" + Resources.rdfsLabel + "> \"" + rightsHolder + "\" .\n";
                }
            }

            if (!creationEventURI.isEmpty()) {
                retTriples += "<" + creationEventURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.creationEventLabel + "> .\n";
                if (!creatorURI.isEmpty()) {
                    retTriples += "<" + creationEventURI + "> <" + Resources.carriedOutBy + "> <" + creatorURI + "> .\n";
                }
                if (!creationDate.isEmpty()) {
                    retTriples += "<" + creationEventURI + "> <" + Resources.hasTimespan + "> \"" + creationDate + "\" .\n";
                }
                 if (!creationEventLabel.isEmpty()) {
                    retTriples += "<" + creationEventURI + "> <" + Resources.rdfsLabel + "> \"" + creationEventLabel + "\" .\n";
                }
            }
            if (!creatorURI.isEmpty()) {
                retTriples += "<" + creatorURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.actorLabel + "> .\n";
                if (!creatorName.isEmpty()) {
                    retTriples += "<" + creatorURI + "> <" + Resources.rdfsLabel + "> \"" + creatorName + "\" .\n";
                }
            }

            if (!publicationEventURI.isEmpty()) {
                retTriples += "<" + publicationEventURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.publicationEventLabel + "> .\n";
                if (!publisherURI.isEmpty()) {
                    retTriples += "<" + publicationEventURI + "> <" + Resources.carriedOutBy + "> <" + publisherURI + "> .\n";
                }
                if (!publicationDate.isEmpty()) {
                    retTriples += "<" + publicationEventURI + "> <" + Resources.hasTimespan + "> \"" + publicationDate + "\" .\n";
                }
                 if (!publicationEventLabel.isEmpty()) {
                    retTriples += "<" + publicationEventURI + "> <" + Resources.rdfsLabel + "> \"" + publicationEventLabel + "\" .\n";
                }
            }

            if (!publisherURI.isEmpty()) {
                retTriples += "<" + publisherURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.actorLabel + "> .\n";
                if (!publisherName.isEmpty()) {
                    retTriples += "<" + publisherURI + "> <" + Resources.rdfsLabel + "> \"" + publisherName + "\" .\n";
                }
            }

            if (!attributeAssignmentEventURI.isEmpty()) {
                retTriples += "<" + attributeAssignmentEventURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.attributeAssignmentEventLabel + "> .\n";
                if (!embargoState.isEmpty()) {
                    retTriples += "<" + attributeAssignmentEventURI + "> <" + Resources.assigned + "> \"" + embargoState + "\" .\n";
                }
                if (!embargoPeriod.isEmpty()) {
                    retTriples += "<" + attributeAssignmentEventURI + "> <" + Resources.hasTimespan + "> \"" + embargoPeriod + "\" .\n";
                }
                 if (!attributeAssignmentEventLabel.isEmpty()) {
                    retTriples += "<" + attributeAssignmentEventURI + "> <" + Resources.rdfsLabel + "> \"" + attributeAssignmentEventLabel + "\" .\n";
                }
            }

            if (!parentDatasetURI.isEmpty()) {
                retTriples += "<" + parentDatasetURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.datasetLabel + "> .\n";
                if (!parentDataset.isEmpty()) {
                    retTriples += "<" + parentDatasetURI + "> <" + Resources.rdfsLabel + "> \"" + parentDataset + "\" .\n";
                }
            }
            if (!curatorURI.isEmpty()) {
                retTriples += "<" + curatorURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.actorLabel + "> .\n";
                if (!curatorName.isEmpty()) {
                    retTriples += "<" + curatorURI + "> <" + Resources.rdfsLabel + "> \"" + curatorName + "\" .\n";
                }
                if (!contactPoint.isEmpty()) {
                    retTriples += "<" + curatorURI + "> <" + Resources.hasContactPoint + "> \"" + contactPoint + "\" .\n";
                }
            }
            if (!keeperURI.isEmpty()) {
                retTriples += "<" + keeperURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.actorLabel + "> .\n";
                if (!keeperName.isEmpty()) {
                    retTriples += "<" + keeperURI + "> <" + Resources.rdfsLabel + "> \"" + keeperName + "\".\n";
                }

            }

            retTriples += " } ";

            Queries.add(retTriples);

            //System.out.println(retTriples);
        }

        return Queries;

    }

    public ArrayList<String> transform_occurence_csv(String fileToParse, String repositoryGraph) throws FileNotFoundException, IOException {

        //String fileToParse = "C:/uploads/TLOflat/identification.csv";
        BufferedReader fileReader = null;

        System.out.println(fileToParse);
        //Delimiter used in CSV file
        final String DELIMITER = ";";

        String line = "";
        String query = "";
        ArrayList<String> Queries = new ArrayList<String>();

        fileReader = new BufferedReader(new FileReader(fileToParse));
        String URIprefix = "http://www.lifewatchgreece.eu/entity";
        int count = 0;

        while ((line = fileReader.readLine()) != null) {

            if (count == 0) {
                count++;
                continue;
            }

            String[] tokens = line.split(DELIMITER);

            String occurrenceEventID = tokens[0];
            String datasetID = tokens[1];
            String individualID = tokens[2];
            String speciesName = tokens[3];
            String actorName = tokens[4];
            String timeSpan = tokens[5];
            String locality = tokens[6];
            String country = tokens[7];
            String waterArea = tokens[8];
            String habitat = tokens[9];
            String ecosystem = tokens[10];
            String equipmentType = tokens[11];
            String latitude = tokens[12];
            String longitude = tokens[13];
            String maximumDepth = tokens[14];
            String minimumDepth = tokens[15];
            String samplingProtocol = tokens[16];
            //  String geodeticDatum=tokens[17];
            String bibliographicCitation = tokens[17];
            String description = tokens[18];

            System.out.println("latitude" + latitude);
            System.out.println("longitude" + longitude);

            String occurrenceEventURI = "";

            if (individualID.isEmpty())
            individualID="Individual of the species"+speciesName;
             
            String occurrenceEventLabel = "Occurrence of "+speciesName+" in "+locality;
            
            if (occurrenceEventID.isEmpty()) {
                occurrenceEventURI = createURI(URIprefix, datasetID + individualID + speciesName + timeSpan, "encounterEvent");
            } else {
                if (occurrenceEventID.startsWith("http:")) {
                    occurrenceEventURI = occurrenceEventID;
                } else {
                    occurrenceEventURI = createURI(URIprefix, occurrenceEventID, "encounterEvent");
                }
            }

            String datasetURI = "";
            if (datasetID.startsWith("http:")) {
                datasetURI = datasetID;
            } else {
                datasetURI = createURI(URIprefix, datasetID, "dataset");
            }

            String individualURI = "";
            if (individualID.startsWith("http:")) {
                individualURI = individualID;
            } else {
                individualURI = createURI(URIprefix, individualID, "bioticElement");
            }

            String actorURI = "";
            if (actorName.startsWith("http:")) {
                actorURI = actorName;
            } else {
                actorURI = createURI(URIprefix, actorName, "actor");
            }

            String localityURI = "";
            if (locality.startsWith("http:")) {
                localityURI = locality;
            } else {
                localityURI = createURI(URIprefix, locality, "place");
            }

            String countryURI = "";
            if (!country.isEmpty()) {
                if (country.startsWith("http:")) {
                    countryURI = country;
                } else {
                    countryURI = createURI(URIprefix, country, "country");
                }
            }

            String waterAreaURI = "";
            if (!waterArea.isEmpty()) {
                if (waterArea.startsWith("http:")) {
                    waterAreaURI = waterArea;
                } else {
                    waterAreaURI = createURI(URIprefix, waterArea, "waterArea");
                }
            }

            String habitatURI = "";
            if (!habitat.isEmpty()) {
                if (habitat.startsWith("http:")) {
                    habitatURI = habitat;
                } else {
                    habitatURI = createURI(URIprefix, habitat, "ecosystemType");
                }
            }

            String ecosystemURI = "";
            if (!ecosystem.isEmpty()) {
                if (ecosystem.startsWith("http:")) {
                    ecosystemURI = ecosystem;
                } else {
                    ecosystemURI = createURI(URIprefix, ecosystem, "ecosystem");
                }
            }

            String equipmentURI = "";
            if (!equipmentType.isEmpty()) {
                if (equipmentType.startsWith("http:")) {
                    equipmentURI = equipmentType;
                } else {
                    equipmentURI = createURI(URIprefix, equipmentType, "equipment");
                }
            }

            String speciesURI = "";
            if (speciesName.startsWith("http:")) {
                speciesURI = speciesName;
            } else {
                speciesURI = createURI(URIprefix, speciesName, "species");
            }

            String samplingProtocolURI = "";
            if (!samplingProtocol.isEmpty()) {
                if (samplingProtocol.startsWith("http:")) {
                    samplingProtocolURI = samplingProtocol;
                } else {
                    samplingProtocolURI = createURI(URIprefix, samplingProtocol, "designOrProcedure");
                }
            }

            String bibliographicCitationURI = "";
            if (!bibliographicCitation.isEmpty()) {
                if (bibliographicCitation.startsWith("http:")) {
                    bibliographicCitationURI = bibliographicCitation;
                } else {
                    bibliographicCitationURI = createURI(URIprefix, bibliographicCitation, "conceptualObject");
                }
            }

            String stationURI = "";

            if (occurrenceEventID.isEmpty()) {
                stationURI = createURI(URIprefix, "station" + occurrenceEventURI.replace(URIprefix, ""), "place");
            } else {
                stationURI = createURI(URIprefix, "station" + occurrenceEventID, "place");
            }

            String coordinates = "";
            if (!latitude.isEmpty() || !longitude.isEmpty()) {
                coordinates = latitude + "," + longitude;
            }

            String stationNotes = "";
            if (!maximumDepth.isEmpty() || !minimumDepth.isEmpty()) {
                stationNotes = "maximumDepth:" + maximumDepth + ",minimumDepth:" + minimumDepth;
            }

            // String station = URIprefix+occurrenceID+"station"+">";
            String retTriples = "INSERT INTO <" + repositoryGraph + "> { ";

            if (!occurrenceEventURI.isEmpty()) {
                retTriples += "<" + occurrenceEventURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.encounterEventLabel + "> .\n";
                if (!timeSpan.isEmpty()) {
                    retTriples += "<" + occurrenceEventURI + "> <" + Resources.hasTimespan + "> \"" + timeSpan + "\" .\n";
                }
//                if (!localityURI.isEmpty()) {
//                    retTriples += "<" + occurrenceEventURI + "> <" + Resources.hasFoundAt + "> <" + localityURI + "> .\n";
//                }

                if (!stationURI.isEmpty()) {
                    retTriples += "<" + occurrenceEventURI + "> <" + Resources.hasFoundAt + "> <" + stationURI + "> .\n";
                }

                if (!equipmentURI.isEmpty()) {
                    retTriples += "<" + occurrenceEventURI + "> <" + Resources.usedObjectOfType + "> <" + equipmentURI + "> .\n";
                }

                if (!samplingProtocolURI.isEmpty()) {
                    retTriples += "<" + occurrenceEventURI + "> <" + Resources.usedSpecificTechnique + "> <" + samplingProtocolURI + "> .\n";
                }

                if (!bibliographicCitationURI.isEmpty()) {
                    retTriples += "<" + occurrenceEventURI + "> <" + Resources.isReferredToBy + "> <" + bibliographicCitationURI + "> .\n";
                }

                if (!actorURI.isEmpty()) {
                    retTriples += "<" + occurrenceEventURI + "> <" + Resources.carriedOutBy + "> <" + actorURI + "> .\n";
                }
                if (!individualURI.isEmpty()) {
                    retTriples += "<" + occurrenceEventURI + "> <" + Resources.hasFoundObject + "> <" + individualURI + "> .\n";
                }

                if (!datasetURI.isEmpty()) {
                    retTriples += "<" + datasetURI + "> <" + Resources.refersTo + "> <" + occurrenceEventURI + "> . \n";
                }

                if (!description.isEmpty()) {
                    retTriples += "<" + occurrenceEventURI + "> <" + Resources.hasNote + "> \"" + description + "\" .\n";
                }
                
                if (!occurrenceEventLabel.isEmpty()) {
                    retTriples += "<" + occurrenceEventURI + "> <" + Resources.rdfsLabel+ "> \"" + occurrenceEventLabel + "\" .\n";
                }

            }
            if (!equipmentURI.isEmpty()) {
                retTriples += "<" + equipmentURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.equipmentTypeLabel + "> .\n";
                if (!equipmentType.isEmpty()) {
                    retTriples += "<" + equipmentURI + "> <" + Resources.rdfsLabel + "> \"" + equipmentType + "\" .\n";
                }
            }

            if (!samplingProtocolURI.isEmpty()) {
                retTriples += "<" + samplingProtocolURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.procedureLabel + "> .\n";
                if (!samplingProtocol.isEmpty()) {
                    retTriples += "<" + samplingProtocolURI + "> <" + Resources.rdfsLabel+ "> \"" + samplingProtocol + "\" .\n";
                }
            }

            if (!bibliographicCitationURI.isEmpty()) {
                retTriples += "<" + bibliographicCitationURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.conceptualObjectLabel + "> .\n";
                if (!bibliographicCitation.isEmpty()) {
                    retTriples += "<" + bibliographicCitationURI + "> <" + Resources.rdfsLabel + "> \"" + bibliographicCitation + "\" .\n";
                }
            }

            if (!stationURI.isEmpty()) {
                retTriples += "<" + stationURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.placeLabel + "> .\n";

//            if (!latitude.isEmpty()&&!longitude.isEmpty()) {
//   
//                retTriples += "<" + stationURI + "> <" + Resources.isIdentifiedBy + "> \"" + latitude + "," + longitude + "\" .\n";
//            }
                if (!latitude.isEmpty() && !longitude.isEmpty()) {

                    retTriples += "<" + stationURI + "> <" + Resources.isIdentifiedBy + "> \"" + coordinates + "\" .\n";
                }

                if (!stationNotes.isEmpty()) {
                    retTriples += "<" + stationURI + "> <" + Resources.hasNote + "> \"" + stationNotes + "\" .\n";
                }

                if (!localityURI.isEmpty()) {
                    retTriples += "<" + stationURI + "> <" + Resources.fallsWithin + "> <" + localityURI + "> .\n";
                }

//            if (!countryURI.isEmpty()) {
//                retTriples += "<" + localityURI + "> <" + Resources.fallsWithin + "> <" + countryURI + "> .\n";
//            }
//            if (!waterAreaURI.isEmpty()) {
//                retTriples += "<" + localityURI + "> <" + Resources.fallsWithin + "> <" + waterAreaURI + "> .\n";
//            }
//            if (!habitatURI.isEmpty()) {
//                retTriples += "<" + localityURI + "> <" + Resources.hasType + "> <" + habitatURI + "> .\n";
//            }
            }

            if (!localityURI.isEmpty()) {
                retTriples += "<" + localityURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.ecosystemEnvironmentLabel + "> .\n";
                if (!locality.isEmpty()) {
                    retTriples += "<" + localityURI + "> <" + Resources.rdfsLabel + "> \"" + locality + "\" .\n";
                }

                if (!ecosystemURI.isEmpty()) {
                    retTriples += "<" + localityURI + "> <" + Resources.fallsWithin + "> <" + ecosystemURI + "> .\n";
                }

                if (!countryURI.isEmpty()) {
                    retTriples += "<" + localityURI + "> <" + Resources.fallsWithin + "> <" + countryURI + "> .\n";
                }
                if (!waterAreaURI.isEmpty()) {
                    retTriples += "<" + localityURI + "> <" + Resources.fallsWithin + "> <" + waterAreaURI + "> .\n";
                }
                if (!habitatURI.isEmpty()) {
                    retTriples += "<" + localityURI + "> <" + Resources.hasType + "> <" + habitatURI + "> .\n";
                }
            }

            if (!ecosystemURI.isEmpty()) {
                retTriples += "<" + ecosystemURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.ecosystemLabel + "> .\n";

                if (!ecosystem.isEmpty()) {
                    retTriples += "<" + ecosystemURI + "> <" + Resources.rdfsLabel + "> \"" + ecosystem + "\" .\n";
                }
            }

            if (!habitatURI.isEmpty()) {
                retTriples += "<" + habitatURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.ecosystemTypeLabel + "> .\n";
                if (!habitat.isEmpty()) {
                    retTriples += "<" + habitatURI + "> <" + Resources.rdfsLabel + "> \"" + habitat + "\" .\n";
                }
            }

            if (!actorURI.isEmpty()) {
                retTriples += "<" + actorURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.personLabel + "> .\n";
                if (!actorName.isEmpty()) {
                    retTriples += "<" + actorURI + "> <" + Resources.rdfsLabel + "> \"" + actorName + "\" .\n";
                }
            }

            if (!individualURI.isEmpty()) {
                retTriples += "<" + individualURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.bioticElementLabel + "> .\n";
                if (!speciesURI.isEmpty()) {
                    retTriples += "<" + individualURI + "> <" + Resources.belongsTo + "> <" + speciesURI + "> .\n";
                }
                if (!individualID.isEmpty()) {
                    retTriples += "<" + individualURI + "> <" + Resources.rdfsLabel + "> \"" + individualID + "\" .\n";
                }
            }

            if (!speciesURI.isEmpty()) {
                retTriples += "<" + speciesURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.speciesLabel + "> .\n";
                if (!speciesName.isEmpty()) {
                    retTriples += "<" + speciesURI + "> <" + Resources.rdfsLabel + "> \"" + speciesName + "\" .\n";
                }
            }

            if (!countryURI.isEmpty()) {
                retTriples += "<" + countryURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.countryLabel + "> .\n";
                if (!country.isEmpty()) {
                    retTriples += "<" + countryURI + "> <" + Resources.rdfsLabel + "> \"" + country + "\" .\n";
                }
            }

            if (!waterAreaURI.isEmpty()) {
                retTriples += "<" + waterAreaURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.waterAreaLabel + "> .\n";
                if (!waterArea.isEmpty()) {
                    retTriples += "<" + waterAreaURI + "> <" + Resources.rdfsLabel + "> \"" + waterArea + "\" .\n";
                }
            }

            if (!datasetURI.isEmpty()) {
                retTriples += "<" + datasetURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.datasetLabel + "> .\n";
                if (!datasetID.isEmpty()) {
                    retTriples += "<" + datasetURI + "> <" + Resources.rdfsLabel + "> \"" + datasetID + "\" .\n";
                }
            }

            retTriples += " } ";

            Queries.add(retTriples);

            //System.out.println(retTriples);
        }

        return Queries;

    }

    public ArrayList<String> transform_occurence_csv(String fileToParse, String repositoryGraph, String datasetID) throws FileNotFoundException, IOException {

        BufferedReader fileReader = null;

        System.out.println(fileToParse);
        //Delimiter used in CSV file
        final String DELIMITER = ";";

        String line = "";
        String query = "";
        ArrayList<String> Queries = new ArrayList<String>();

        fileReader = new BufferedReader(new FileReader(fileToParse));
        String URIprefix = "http://www.lifewatchgreece.eu/entity";
        int count = 0;

        while ((line = fileReader.readLine()) != null) {

            if (count == 0) {
                count++;
                continue;
            }

            String[] tokens = line.split(DELIMITER);

            String occurrenceEventID = tokens[0];
            //String datasetID = tokens[1];
            String individualID = tokens[2];
            String speciesName = tokens[3];
            String actorName = tokens[4];
            String timeSpan = tokens[5];
            String locality = tokens[6];
            String country = tokens[7];
            String waterArea = tokens[8];
            String habitat = tokens[9];
            String ecosystem = tokens[10];
            String equipmentType = tokens[11];
            String latitude = tokens[12];
            String longitude = tokens[13];
            String maximumDepth = tokens[14];
            String minimumDepth = tokens[15];
            String samplingProtocol = tokens[16];
            //  String geodeticDatum=tokens[17];
            String bibliographicCitation = tokens[17];
            String description = tokens[18];

            System.out.println("latitude" + latitude);
            System.out.println("longitude" + longitude);

            String occurrenceEventURI = "";

            String occurrenceEventLabel = "Occurrence of "+speciesName+" in "+locality;
            
            if (individualID.isEmpty())
            individualID="Individual of the species"+speciesName;
            
            if (occurrenceEventID.isEmpty()) {
                occurrenceEventURI = createURI(URIprefix, datasetID + individualID + speciesName + timeSpan, "encounterEvent");
            } else {
                if (occurrenceEventID.startsWith("http:")) {
                    occurrenceEventURI = occurrenceEventID;
                } else {
                    occurrenceEventURI = createURI(URIprefix, occurrenceEventID, "encounterEvent");
                }
            }

            String datasetURI = "";
            if (datasetID.startsWith("http:")) {
                datasetURI = datasetID;
            } else {
                datasetURI = createURI(URIprefix, datasetID, "dataset");
            }

            String individualURI = "";
            if (individualID.startsWith("http:")) {
                individualURI = individualID;
            } else {
                individualURI = createURI(URIprefix, individualID, "bioticElement");
            }

            String actorURI = "";
            if (actorName.startsWith("http:")) {
                actorURI = actorName;
            } else {
                actorURI = createURI(URIprefix, actorName, "actor");
            }

            String localityURI = "";
            if (locality.startsWith("http:")) {
                localityURI = locality;
            } else {
                localityURI = createURI(URIprefix, locality, "place");
            }

            String countryURI = "";
            if (!country.isEmpty()) {
                if (country.startsWith("http:")) {
                    countryURI = country;
                } else {
                    countryURI = createURI(URIprefix, country, "country");
                }
            }

            String waterAreaURI = "";
            if (!waterArea.isEmpty()) {
                if (waterArea.startsWith("http:")) {
                    waterAreaURI = waterArea;
                } else {
                    waterAreaURI = createURI(URIprefix, waterArea, "waterArea");
                }
            }

            String habitatURI = "";
            if (!habitat.isEmpty()) {
                if (habitat.startsWith("http:")) {
                    habitatURI = habitat;
                } else {
                    habitatURI = createURI(URIprefix, habitat, "ecosystemType");
                }
            }

            String ecosystemURI = "";
            if (!ecosystem.isEmpty()) {
                if (ecosystem.startsWith("http:")) {
                    ecosystemURI = ecosystem;
                } else {
                    ecosystemURI = createURI(URIprefix, ecosystem, "ecosystem");
                }
            }

            String equipmentURI = "";
            if (!equipmentType.isEmpty()) {
                if (equipmentType.startsWith("http:")) {
                    equipmentURI = equipmentType;
                } else {
                    equipmentURI = createURI(URIprefix, equipmentType, "equipment");
                }
            }

            String speciesURI = "";
            if (speciesName.startsWith("http:")) {
                speciesURI = speciesName;
            } else {
                speciesURI = createURI(URIprefix, speciesName, "species");
            }

            String samplingProtocolURI = "";
            if (!samplingProtocol.isEmpty()) {
                if (samplingProtocol.startsWith("http:")) {
                    samplingProtocolURI = samplingProtocol;
                } else {
                    samplingProtocolURI = createURI(URIprefix, samplingProtocol, "designOrProcedure");
                }
            }

            String bibliographicCitationURI = "";
            if (!bibliographicCitation.isEmpty()) {
                if (bibliographicCitation.startsWith("http:")) {
                    bibliographicCitationURI = bibliographicCitation;
                } else {
                    bibliographicCitationURI = createURI(URIprefix, bibliographicCitation, "conceptualObject");
                }
            }

            String stationURI = "";

            if (occurrenceEventID.isEmpty()) {
                stationURI = createURI(URIprefix, "station" + occurrenceEventURI.replace(URIprefix, ""), "place");
            } else {
                stationURI = createURI(URIprefix, "station" + occurrenceEventID, "place");
            }

            String coordinates = "";
            if (!latitude.isEmpty() || !longitude.isEmpty()) {
                coordinates = latitude + "," + longitude;
            }

            String stationNotes = "";
            if (!maximumDepth.isEmpty() || !minimumDepth.isEmpty()) {
                stationNotes = "maximumDepth:" + maximumDepth + ",minimumDepth:" + minimumDepth;
            }

            // String station = URIprefix+occurrenceID+"station"+">";
            String retTriples = "INSERT INTO <" + repositoryGraph + "> { ";

            if (!occurrenceEventURI.isEmpty()) {
                retTriples += "<" + occurrenceEventURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.encounterEventLabel + "> .\n";
                if (!timeSpan.isEmpty()) {
                    retTriples += "<" + occurrenceEventURI + "> <" + Resources.hasTimespan + "> \"" + timeSpan + "\" .\n";
                }
//                if (!localityURI.isEmpty()) {
//                    retTriples += "<" + occurrenceEventURI + "> <" + Resources.hasFoundAt + "> <" + localityURI + "> .\n";
//                }

                if (!stationURI.isEmpty()) {
                    retTriples += "<" + occurrenceEventURI + "> <" + Resources.hasFoundAt + "> <" + stationURI + "> .\n";
                }

                if (!equipmentURI.isEmpty()) {
                    retTriples += "<" + occurrenceEventURI + "> <" + Resources.usedObjectOfType + "> <" + equipmentURI + "> .\n";
                }

                if (!samplingProtocolURI.isEmpty()) {
                    retTriples += "<" + occurrenceEventURI + "> <" + Resources.usedSpecificTechnique + "> <" + samplingProtocolURI + "> .\n";
                }

                if (!bibliographicCitationURI.isEmpty()) {
                    retTriples += "<" + occurrenceEventURI + "> <" + Resources.isReferredToBy + "> <" + bibliographicCitationURI + "> .\n";
                }

                if (!actorURI.isEmpty()) {
                    retTriples += "<" + occurrenceEventURI + "> <" + Resources.carriedOutBy + "> <" + actorURI + "> .\n";
                }
                if (!individualURI.isEmpty()) {
                    retTriples += "<" + occurrenceEventURI + "> <" + Resources.hasFoundObject + "> <" + individualURI + "> .\n";
                }

                if (!datasetURI.isEmpty()) {
                    retTriples += "<" + datasetURI + "> <" + Resources.refersTo + "> <" + occurrenceEventURI + "> . \n";
                }

                if (!description.isEmpty()) {
                    retTriples += "<" + occurrenceEventURI + "> <" + Resources.hasNote + "> \"" + description + "\" .\n";
                }
                
                if (!occurrenceEventLabel.isEmpty()) {
                    retTriples += "<" + occurrenceEventURI + "> <" + Resources.rdfsLabel+ "> \"" + occurrenceEventLabel + "\" .\n";
                }

            }
            if (!equipmentURI.isEmpty()) {
                retTriples += "<" + equipmentURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.equipmentTypeLabel + "> .\n";
                if (!equipmentType.isEmpty()) {
                    retTriples += "<" + equipmentURI + "> <" + Resources.rdfsLabel + "> \"" + equipmentType + "\" .\n";
                }
            }

            if (!samplingProtocolURI.isEmpty()) {
                retTriples += "<" + samplingProtocolURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.procedureLabel + "> .\n";
                if (!samplingProtocol.isEmpty()) {
                    retTriples += "<" + samplingProtocolURI + "> <" + Resources.rdfsLabel + "> \"" + samplingProtocol + "\" .\n";
                }
            }

            if (!bibliographicCitationURI.isEmpty()) {
                retTriples += "<" + bibliographicCitationURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.conceptualObjectLabel + "> .\n";
                if (!bibliographicCitation.isEmpty()) {
                    retTriples += "<" + bibliographicCitationURI + "> <" + Resources.rdfsLabel+ "> \"" + bibliographicCitation + "\" .\n";
                }
            }

            if (!stationURI.isEmpty()) {
                retTriples += "<" + stationURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.placeLabel + "> .\n";

//            if (!latitude.isEmpty()&&!longitude.isEmpty()) {
//   
//                retTriples += "<" + stationURI + "> <" + Resources.isIdentifiedBy + "> \"" + latitude + "," + longitude + "\" .\n";
//            }
                if (!latitude.isEmpty() && !longitude.isEmpty()) {

                    retTriples += "<" + stationURI + "> <" + Resources.isIdentifiedBy + "> \"" + coordinates + "\" .\n";
                }

                if (!stationNotes.isEmpty()) {
                    retTriples += "<" + stationURI + "> <" + Resources.hasNote + "> \"" + stationNotes + "\" .\n";
                }

                if (!localityURI.isEmpty()) {
                    retTriples += "<" + stationURI + "> <" + Resources.fallsWithin + "> <" + localityURI + "> .\n";
                }

//            if (!countryURI.isEmpty()) {
//                retTriples += "<" + localityURI + "> <" + Resources.fallsWithin + "> <" + countryURI + "> .\n";
//            }
//            if (!waterAreaURI.isEmpty()) {
//                retTriples += "<" + localityURI + "> <" + Resources.fallsWithin + "> <" + waterAreaURI + "> .\n";
//            }
//            if (!habitatURI.isEmpty()) {
//                retTriples += "<" + localityURI + "> <" + Resources.hasType + "> <" + habitatURI + "> .\n";
//            }
            }

            if (!localityURI.isEmpty()) {
                retTriples += "<" + localityURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.ecosystemEnvironmentLabel + "> .\n";
                if (!locality.isEmpty()) {
                    retTriples += "<" + localityURI + "> <" + Resources.rdfsLabel + "> \"" + locality + "\" .\n";
                }

                if (!ecosystemURI.isEmpty()) {
                    retTriples += "<" + localityURI + "> <" + Resources.fallsWithin + "> <" + ecosystemURI + "> .\n";
                }

                if (!countryURI.isEmpty()) {
                    retTriples += "<" + localityURI + "> <" + Resources.fallsWithin + "> <" + countryURI + "> .\n";
                }
                if (!waterAreaURI.isEmpty()) {
                    retTriples += "<" + localityURI + "> <" + Resources.fallsWithin + "> <" + waterAreaURI + "> .\n";
                }
                if (!habitatURI.isEmpty()) {
                    retTriples += "<" + localityURI + "> <" + Resources.hasType + "> <" + habitatURI + "> .\n";
                }
            }

            if (!ecosystemURI.isEmpty()) {
                retTriples += "<" + ecosystemURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.ecosystemLabel + "> .\n";

                if (!ecosystem.isEmpty()) {
                    retTriples += "<" + ecosystemURI + "> <" + Resources.rdfsLabel + "> \"" + ecosystem + "\" .\n";
                }
            }

            if (!habitatURI.isEmpty()) {
                retTriples += "<" + habitatURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.ecosystemTypeLabel + "> .\n";
                if (!habitat.isEmpty()) {
                    retTriples += "<" + habitatURI + "> <" + Resources.rdfsLabel + "> \"" + habitat + "\" .\n";
                }
            }

            if (!actorURI.isEmpty()) {
                retTriples += "<" + actorURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.personLabel + "> .\n";
                if (!actorName.isEmpty()) {
                    retTriples += "<" + actorURI + "> <" + Resources.rdfsLabel + "> \"" + actorName + "\" .\n";
                }
            }

            if (!individualURI.isEmpty()) {
                retTriples += "<" + individualURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.bioticElementLabel + "> .\n";
                if (!speciesURI.isEmpty()) {
                    retTriples += "<" + individualURI + "> <" + Resources.belongsTo + "> <" + speciesURI + "> .\n";
                }
                if (!individualID.isEmpty()) {
                    retTriples += "<" + individualURI + "> <" + Resources.rdfsLabel + "> \"" + individualID + "\" .\n";
                }
            }

            if (!speciesURI.isEmpty()) {
                retTriples += "<" + speciesURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.speciesLabel + "> .\n";
                if (!speciesName.isEmpty()) {
                    retTriples += "<" + speciesURI + "> <" + Resources.rdfsLabel + "> \"" + speciesName + "\" .\n";
                }
            }

            if (!countryURI.isEmpty()) {
                retTriples += "<" + countryURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.countryLabel + "> .\n";
                if (!country.isEmpty()) {
                    retTriples += "<" + countryURI + "> <" + Resources.rdfsLabel + "> \"" + country + "\" .\n";
                }
            }

            if (!waterAreaURI.isEmpty()) {
                retTriples += "<" + waterAreaURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.waterAreaLabel + "> .\n";
                if (!waterArea.isEmpty()) {
                    retTriples += "<" + waterAreaURI + "> <" + Resources.rdfsLabel + "> \"" + waterArea + "\" .\n";
                }
            }

            if (!datasetURI.isEmpty()) {
                retTriples += "<" + datasetURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.datasetLabel + "> .\n";
                if (!datasetID.isEmpty()) {
                    retTriples += "<" + datasetURI + "> <" + Resources.rdfsLabel + "> \"" + datasetID + "\" .\n";
                }
            }

            retTriples += " } ";

            Queries.add(retTriples);

            //System.out.println(retTriples);
        }

        return Queries;

    }

    public ArrayList<String> transform_environmental_csv(String fileToParse, String repositoryGraph) throws FileNotFoundException, IOException {

        //String fileToParse = "C:/uploads/TLOflat/identification.csv";
        BufferedReader fileReader = null;

        System.out.println(fileToParse);
        //Delimiter used in CSV file
        final String DELIMITER = ";";

        String line = "";
        String query = "";
        ArrayList<String> Queries = new ArrayList<String>();

        fileReader = new BufferedReader(new FileReader(fileToParse));
        String URIprefix = "http://www.lifewatchgreece.eu/entity";
        int count = 0;

        while ((line = fileReader.readLine()) != null) {

            if (count == 0) {
                count++;
                continue;
            }

            String[] tokens = line.split(DELIMITER);

            String station = tokens[0];
            String place = tokens[1];
            String measurementID = tokens[2];
            String actorName = tokens[3];
            String timeSpan = tokens[4];
            String dimensionType = tokens[5];
            String dimensionValue = tokens[6];
            String dimensionUnit = tokens[7];
            String datasetID = tokens[8];

            String measurementEventLabel = "Environmental Measurement of "+dimensionType+" in "+place;
            
            String measurementEventURI = "";
            if (measurementID.isEmpty()) {
                measurementEventURI = createURI(URIprefix, datasetID + place + dimensionType + timeSpan, "measurementEvent");
            } else {
                if (measurementID.startsWith("http:")) {
                    measurementEventURI = measurementID;
                } else {
                    measurementEventURI = createURI(URIprefix, measurementID, "measurementEvent");
                }
            }

            String stationURI = "";
            if (station.isEmpty()) {
                if (measurementID.isEmpty()) {
                    stationURI = createURI(URIprefix, "station" + measurementEventURI.replace(URIprefix, ""), "place");
                } else {
                    stationURI = createURI(URIprefix, "station" + measurementID, "place");
                }
            } else {
                stationURI = createURI(URIprefix, station, "place");
            }
            String placeURI = createURI(URIprefix, place, "place");

            String actorURI = "";
            if (!actorName.isEmpty()) {

                actorURI = createURI(URIprefix, actorName, "actor");

            }

            String dimensionURI = "";

            if (measurementID.isEmpty()) {
                dimensionURI = createURI(URIprefix, measurementEventURI.replace(URIprefix, "") + dimensionType, "dimension");
            } else {
                dimensionURI = createURI(URIprefix, measurementID + dimensionType, "dimension");
            }

            String dimensionTypeURI = createURI(URIprefix, dimensionType, "dimension");

            String datasetURI = createURI(URIprefix, datasetID, "dataset");

            String retTriples = "INSERT INTO <" + repositoryGraph + "> { ";

            if (!measurementEventURI.isEmpty()) {
                retTriples += "<" + measurementEventURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.measurementEventLabel + "> .\n";
                if (!timeSpan.isEmpty()) {
                    retTriples += "<" + measurementEventURI + "> <" + Resources.hasTimespan + "> \"" + timeSpan + "\" .\n";
                }
                if (!stationURI.isEmpty()) {
                    retTriples += "<" + measurementEventURI + "> <" + Resources.measured + "> <" + stationURI + "> .\n";
                }
                if (!dimensionURI.isEmpty()) {
                    retTriples += "<" + measurementEventURI + "> <" + Resources.observedDimension + "> <" + dimensionURI + "> .\n";
                }
                if (!actorURI.isEmpty()) {
                    retTriples += "<" + measurementEventURI + "> <" + Resources.carriedOutBy + "> <" + actorURI + "> .\n";
                }
                if (!datasetURI.isEmpty()) {
                    retTriples += "<" + datasetURI + "> <" + Resources.refersTo + "> <" + measurementEventURI + "> . \n";
                }
                if (!measurementEventLabel.isEmpty()) {
                        retTriples += "<" + measurementEventURI + "> <" + Resources.rdfsLabel + "> \"" + measurementEventLabel + "\" .\n";
                }
            }
            if (!dimensionURI.isEmpty()) {
                retTriples += "<" + dimensionURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.dimensionLabel + "> .\n";
                if (!dimensionTypeURI.isEmpty()) {
                    retTriples += "<" + dimensionURI + "> <" + Resources.hasType + "> <" + dimensionTypeURI + "> .\n";

                    if (!dimensionType.isEmpty()) {
                        retTriples += "<" + dimensionTypeURI + "> <" + Resources.rdfsLabel + "> \"" + dimensionType + "\" .\n";

                    }
                    if (!dimensionUnit.isEmpty()) {
                        retTriples += "<" + dimensionURI + "> <" + Resources.hasUnit + "> \"" + dimensionUnit + "\" .\n";

                    }
                    if (!dimensionValue.isEmpty()) {
                        retTriples += "<" + dimensionURI + "> <" + Resources.hasValue + "> \"" + dimensionValue + "\" .\n";

                    }

                }
            }

            if (!stationURI.isEmpty()) {
                retTriples += "<" + stationURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.ecosystemEnvironmentLabel + "> .\n";
                if (!placeURI.isEmpty()) {
                    retTriples += "<" + stationURI + "> <" + Resources.fallsWithin + "> <" + placeURI + "> .\n";
                }

            }

            if (!placeURI.isEmpty()) {
                retTriples += "<" + placeURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.placeLabel + "> .\n";
                if (!place.isEmpty()) {
                    retTriples += "<" + placeURI + "> <" + Resources.rdfsLabel + "> \"" + place + "\" .\n";
                }

            }

            if (!datasetURI.isEmpty()) {
                retTriples += "<" + datasetURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.datasetLabel + "> .\n";
                if (!datasetID.isEmpty()) {
                    retTriples += "<" + datasetURI + "> <" + Resources.rdfsLabel + "> \"" + datasetID + "\" .\n";
                }

            }

            if (!actorURI.isEmpty()) {
                retTriples += "<" + actorURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.personLabel + "> .\n";
                if (!actorName.isEmpty()) {
                    retTriples += "<" + actorURI + "> <" + Resources.rdfsLabel + "> \"" + actorName + "\" .\n";
                }
            }

            retTriples += " } ";

            Queries.add(retTriples);

            //System.out.println(retTriples);
        }

        return Queries;

    }

    public ArrayList<String> transform_environmental_csv(String fileToParse, String repositoryGraph,
            String datasetID) throws FileNotFoundException, IOException {

        //String fileToParse = "C:/uploads/TLOflat/identification.csv";
        BufferedReader fileReader = null;

        System.out.println(fileToParse);
        //Delimiter used in CSV file
        final String DELIMITER = ";";

        String line = "";
        String query = "";
        ArrayList<String> Queries = new ArrayList<String>();

        fileReader = new BufferedReader(new FileReader(fileToParse));
        String URIprefix = "http://www.lifewatchgreece.eu/entity";
        int count = 0;

        while ((line = fileReader.readLine()) != null) {

            if (count == 0) {
                count++;
                continue;
            }

            String[] tokens = line.split(DELIMITER);

            String station = tokens[0];
            String place = tokens[1];
            String measurementID = tokens[2];
            String actorName = tokens[3];
            String timeSpan = tokens[4];
            String dimensionType = tokens[5];
            String dimensionValue = tokens[6];
            String dimensionUnit = tokens[7];
            //String datasetID = tokens[8];
            
            String measurementEventLabel = "Environmental Measurement of "+dimensionType+" in "+place;
           
            String measurementEventURI = "";
            if (measurementID.isEmpty()) {
                measurementEventURI = createURI(URIprefix, datasetID + place + dimensionType + timeSpan, "measurementEvent");
            } else {
                if (measurementID.startsWith("http:")) {
                    measurementEventURI = measurementID;
                } else {
                    measurementEventURI = createURI(URIprefix, measurementID, "encounterEvent");
                }
            }

            String stationURI = "";
            if (station.isEmpty()) {
                if (measurementID.isEmpty()) {
                    stationURI = createURI(URIprefix, "station" + measurementEventURI.replace(URIprefix, ""), "place");
                } else {
                    stationURI = createURI(URIprefix, "station" + measurementID, "place");
                }
            } else {
                stationURI = createURI(URIprefix, station, "place");
            }
            String placeURI = createURI(URIprefix, place, "place");

            String actorURI = "";
            if (!actorName.isEmpty()) {

                actorURI = createURI(URIprefix, actorName, "actor");

            }

            String dimensionURI = "";

            if (measurementID.isEmpty()) {
                dimensionURI = createURI(URIprefix, measurementEventURI.replace(URIprefix, "") + dimensionType, "dimension");
            } else {
                dimensionURI = createURI(URIprefix, measurementID + dimensionType, "dimension");
            }

            String dimensionTypeURI = createURI(URIprefix, dimensionType, "dimension");

            String datasetURI = createURI(URIprefix, datasetID, "dataset");

            String retTriples = "INSERT INTO <" + repositoryGraph + "> { ";

            if (!measurementEventURI.isEmpty()) {
                retTriples += "<" + measurementEventURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.measurementEventLabel + "> .\n";
                if (!timeSpan.isEmpty()) {
                    retTriples += "<" + measurementEventURI + "> <" + Resources.hasTimespan + "> \"" + timeSpan + "\" .\n";
                }
                if (!stationURI.isEmpty()) {
                    retTriples += "<" + measurementEventURI + "> <" + Resources.measured + "> <" + stationURI + "> .\n";
                }
                if (!dimensionURI.isEmpty()) {
                    retTriples += "<" + measurementEventURI + "> <" + Resources.observedDimension + "> <" + dimensionURI + "> .\n";
                }
                if (!actorURI.isEmpty()) {
                    retTriples += "<" + measurementEventURI + "> <" + Resources.carriedOutBy + "> <" + actorURI + "> .\n";
                }
                if (!datasetURI.isEmpty()) {
                    retTriples += "<" + datasetURI + "> <" + Resources.refersTo + "> <" + measurementEventURI + "> . \n";
                }
                if (!measurementEventLabel.isEmpty()) {
                        retTriples += "<" + measurementEventURI + "> <" + Resources.rdfsLabel + "> \"" + measurementEventLabel + "\" .\n";
                }
            }
            if (!dimensionURI.isEmpty()) {
                retTriples += "<" + dimensionURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.dimensionLabel + "> .\n";
                if (!dimensionTypeURI.isEmpty()) {
                    retTriples += "<" + dimensionURI + "> <" + Resources.hasType + "> <" + dimensionTypeURI + "> .\n";

                    if (!dimensionType.isEmpty()) {
                        retTriples += "<" + dimensionTypeURI + "> <" + Resources.rdfsLabel + "> \"" + dimensionType + "\" .\n";
                    }
                    if (!dimensionUnit.isEmpty()) {
                        retTriples += "<" + dimensionURI + "> <" + Resources.hasUnit + "> \"" + dimensionUnit + "\" .\n";

                    }
                    if (!dimensionValue.isEmpty()) {
                        retTriples += "<" + dimensionURI + "> <" + Resources.hasValue + "> \"" + dimensionValue + "\" .\n";

                    }

                }
            }

            if (!stationURI.isEmpty()) {
                retTriples += "<" + stationURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.ecosystemEnvironmentLabel + "> .\n";
                if (!placeURI.isEmpty()) {
                    retTriples += "<" + stationURI + "> <" + Resources.fallsWithin + "> <" + placeURI + "> .\n";
                }

            }

            if (!placeURI.isEmpty()) {
                retTriples += "<" + placeURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.placeLabel + "> .\n";
                if (!place.isEmpty()) {
                    retTriples += "<" + placeURI + "> <" + Resources.rdfsLabel + "> \"" + place + "\" .\n";
                }

            }

            if (!datasetURI.isEmpty()) {
                retTriples += "<" + datasetURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.datasetLabel + "> .\n";
                if (!datasetID.isEmpty()) {
                    retTriples += "<" + datasetURI + "> <" + Resources.rdfsLabel + "> \"" + datasetID + "\" .\n";
                }

            }

            if (!actorURI.isEmpty()) {
                retTriples += "<" + actorURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.personLabel + "> .\n";
                if (!actorName.isEmpty()) {
                    retTriples += "<" + actorURI + "> <" + Resources.rdfsLabel + "> \"" + actorName + "\" .\n";
                }
            }

            retTriples += " } ";

            Queries.add(retTriples);

            //System.out.println(retTriples);
        }

        return Queries;

    }

    public ArrayList<String> transform_identification_csv(String fileToParse, String repositoryGraph) throws FileNotFoundException, IOException {

        //String fileToParse = "C:/uploads/TLOflat/identification.csv";
        BufferedReader fileReader = null;

        System.out.println(fileToParse);
        //Delimiter used in CSV file
        final String DELIMITER = ";";

        String line = "";
        String query = "";
        ArrayList<String> Queries = new ArrayList<String>();

        fileReader = new BufferedReader(new FileReader(fileToParse));
        String URIprefix = "http://www.lifewatchgreece.eu/entity";
        int count = 0;

        while ((line = fileReader.readLine()) != null) {

            if (count == 0) {
                count++;
                continue;
            }

            String[] tokens = line.split(DELIMITER);

            String identificationEventID = tokens[0];
            String individualID = tokens[1];
            String datasetID = tokens[2];
            String actorName = tokens[3];
            String timeSpan = tokens[4];
            String locality = tokens[5];
            String speciesName = tokens[6];
            String identificationReferences = tokens[7];

            String individualURI = createURI(URIprefix, individualID, "bioticElement");

            if (individualID.isEmpty())
            individualID="Individual of the species"+speciesName;
                    
            String speciesURI = createURI(URIprefix, speciesName, "species");

            String identificationEventURI = "";
            if (identificationEventID.isEmpty()) {
                identificationEventURI = createURI(URIprefix, datasetID + individualID + speciesName, "identificationEvent");
            } else {
                if (identificationEventID.startsWith("http:")) {
                    identificationEventURI = identificationEventID;
                } else {
                    identificationEventURI = createURI(URIprefix, identificationEventID, "identificationEvent");
                }
            }

            String identificationEventLabel = "Identification of "+speciesName+" individual in "+locality;
            
            String datasetURI = "";
            if (datasetID.startsWith("http:")) {
                datasetURI = datasetID;
            } else {
                datasetURI = createURI(URIprefix, datasetID, "dataset");
            }

            String actorURI = "";
            if (!actorName.isEmpty()) {
                actorURI = createURI(URIprefix, actorName, "actor");
            }

            String localityURI = "";
            if (!locality.isEmpty()) {
                localityURI = createURI(URIprefix, locality, "place");
            }

            String identificationReferencesURI = "";
            if (!(identificationReferences.isEmpty()||identificationReferences.equals(" "))) {
                identificationReferencesURI = createURI(URIprefix, identificationReferences, "publication");
            }

            String retTriples = "INSERT INTO <" + repositoryGraph + "> { ";

            if (!identificationEventURI.isEmpty()) {
                retTriples += "<" + identificationEventURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.identificationEventLabel + "> .\n";
                if (!timeSpan.isEmpty()) {
                    retTriples += "<" + identificationEventURI + "> <" + Resources.hasTimespan + "> \"" + timeSpan + "\" .\n";
                }
                if (!localityURI.isEmpty()) {
                    retTriples += "<" + identificationEventURI + "> <" + Resources.tookPlaceAt + "> <" + localityURI + "> .\n";
                }
                if (!identificationReferencesURI.isEmpty()) {
                    retTriples += "<" + identificationEventURI + "> <" + Resources.usedSpecificObject + "> <" + identificationReferencesURI + "> .\n";
                }
                if (!actorURI.isEmpty()) {
                    retTriples += "<" + identificationEventURI + "> <" + Resources.carriedOutBy + "> <" + actorURI + "> .\n";
                }
                if (!individualURI.isEmpty()) {
                    retTriples += "<" + identificationEventURI + "> <" + Resources.assigned + "> <" + speciesURI + "> .\n";
                }

                if (!individualURI.isEmpty()) {
                    retTriples += "<" + identificationEventURI + "> <" + Resources.classified + "> <" + individualURI + "> .\n";
                }
                if (!datasetURI.isEmpty()) {
                    retTriples += "<" + datasetURI + "> <" + Resources.refersTo + "> <" + identificationEventURI + "> . \n";
                }
                
                if (!identificationEventLabel.isEmpty()) {
                    retTriples += "<" + identificationEventURI  + "> <" + Resources.rdfsLabel + "> \"" + identificationEventLabel + "\" .\n";
                }
            }

            if (!localityURI.isEmpty()) {
                retTriples += "<" + localityURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.ecosystemEnvironmentLabel + "> .\n";
                if (!locality.isEmpty()) {
                    retTriples += "<" + localityURI + "> <" + Resources.rdfsLabel + "> \"" + locality + "\" .\n";
                }
            }

            if (!identificationReferencesURI.isEmpty()) {
                retTriples += "<" + identificationReferencesURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.publicationLabel + "> .\n";
                if (!identificationReferences.isEmpty()) {
                    retTriples += "<" + identificationReferencesURI + "> <" + Resources.rdfsLabel + "> \"" + identificationReferences + "\" .\n";
                }
            }

            if (!actorURI.isEmpty()) {
                retTriples += "<" + actorURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.personLabel + "> .\n";
                if (!actorName.isEmpty()) {
                    retTriples += "<" + actorURI + "> <" + Resources.rdfsLabel + "> \"" + actorName + "\" .\n";
                }
            }

            if (!individualURI.isEmpty()) {
                retTriples += "<" + individualURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.bioticElementLabel + "> .\n";
                if (!individualID.isEmpty()) {
                    retTriples += "<" + individualURI + "> <" + Resources.rdfsLabel + "> \"" + individualID + "\" .\n";
                }
            }

            if (!speciesURI.isEmpty()) {
                retTriples += "<" + speciesURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.speciesLabel + "> .\n";
                if (!speciesName.isEmpty()) {
                    retTriples += "<" + speciesURI + "> <" + Resources.rdfsLabel + "> \"" + speciesName + "\" .\n";
                }
            }

            if (!datasetURI.isEmpty()) {
                retTriples += "<" + datasetURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.datasetLabel + "> .\n";
                if (!datasetID.isEmpty()) {
                    retTriples += "<" + datasetURI + "> <" + Resources.rdfsLabel + "> \"" + datasetID + "\" .\n";
                }
            }

            retTriples += " } ";

            Queries.add(retTriples);

            //System.out.println(retTriples);
        }

        return Queries;

    }

    public ArrayList<String> transform_identification_csv(String fileToParse, String repositoryGraph, String datasetID) throws FileNotFoundException, IOException {

        //String fileToParse = "C:/uploads/TLOflat/identification.csv";
        BufferedReader fileReader = null;

        System.out.println(fileToParse);
        //Delimiter used in CSV file
        final String DELIMITER = ";";

        String line = "";
        String query = "";
        ArrayList<String> Queries = new ArrayList<String>();

        fileReader = new BufferedReader(new FileReader(fileToParse));
        String URIprefix = "http://www.lifewatchgreece.eu/entity";
        int count = 0;

        while ((line = fileReader.readLine()) != null) {

            if (count == 0) {
                count++;
                continue;
            }

            String[] tokens = line.split(DELIMITER);

            String identificationEventID = tokens[0];
            String individualID = tokens[1];
            //String datasetID=tokens[2];
            String actorName = tokens[3];
            String timeSpan = tokens[4];
            String locality = tokens[5];
            String speciesName = tokens[6];
            String identificationReferences = tokens[7];

            String individualURI = createURI(URIprefix, individualID, "bioticElement");

            if (individualID.isEmpty())
            individualID="Individual of the species"+speciesName;
                 
            String speciesURI = createURI(URIprefix, speciesName, "species");

            String identificationEventURI = "";
            if (identificationEventID.isEmpty()) {
                identificationEventURI = createURI(URIprefix, datasetID + individualID + speciesName, "identificationEvent");
            } else {
                if (identificationEventID.startsWith("http:")) {
                    identificationEventURI = identificationEventID;
                } else {
                    identificationEventURI = createURI(URIprefix, identificationEventID, "identificationEvent");
                }
            }
            
            String identificationEventLabel = "Identification of "+speciesName+" individual in "+locality;

            String datasetURI = "";
            if (datasetID.startsWith("http:")) {
                datasetURI = datasetID;
            } else {
                datasetURI = createURI(URIprefix, datasetID, "dataset");
            }

            String actorURI = "";
            if (!actorName.isEmpty()) {
                actorURI = createURI(URIprefix, actorName, "actor");
            }

            String localityURI = "";
            if (!locality.isEmpty()) {
                localityURI = createURI(URIprefix, locality, "place");
            }

            String identificationReferencesURI = "";
            if (!(identificationReferences.isEmpty()||identificationReferences.equals(" "))) {
                identificationReferencesURI = createURI(URIprefix, identificationReferences, "publication");
            }

            String retTriples = "INSERT INTO <" + repositoryGraph + "> { ";

            if (!identificationEventURI.isEmpty()) {
                retTriples += "<" + identificationEventURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.identificationEventLabel + "> .\n";
                if (!timeSpan.isEmpty()) {
                    retTriples += "<" + identificationEventURI + "> <" + Resources.hasTimespan + "> \"" + timeSpan + "\" .\n";
                }
                if (!localityURI.isEmpty()) {
                    retTriples += "<" + identificationEventURI + "> <" + Resources.tookPlaceAt + "> <" + localityURI + "> .\n";
                }
                if (!identificationReferencesURI.isEmpty()) {
                    retTriples += "<" + identificationEventURI + "> <" + Resources.usedSpecificObject + "> <" + identificationReferencesURI + "> .\n";
                }
                if (!actorURI.isEmpty()) {
                    retTriples += "<" + identificationEventURI + "> <" + Resources.carriedOutBy + "> <" + actorURI + "> .\n";
                }
                if (!individualURI.isEmpty()) {
                    retTriples += "<" + identificationEventURI + "> <" + Resources.assigned + "> <" + speciesURI + "> .\n";
                }

                if (!individualURI.isEmpty()) {
                    retTriples += "<" + identificationEventURI + "> <" + Resources.classified + "> <" + individualURI + "> .\n";
                }
                if (!datasetURI.isEmpty()) {
                    retTriples += "<" + datasetURI + "> <" + Resources.refersTo + "> <" + identificationEventURI + "> . \n";
                }
                if (!identificationEventLabel.isEmpty()) {
                    retTriples += "<" + identificationEventURI  + "> <" + Resources.rdfsLabel + "> \"" + identificationEventLabel + "\" .\n";
                }
            }

            if (!localityURI.isEmpty()) {
                retTriples += "<" + localityURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.ecosystemEnvironmentLabel + "> .\n";
                if (!locality.isEmpty()) {
                    retTriples += "<" + localityURI + "> <" + Resources.rdfsLabel + "> \"" + locality + "\" .\n";
                }
            }

            if (!identificationReferencesURI.isEmpty()) {
                retTriples += "<" + identificationReferencesURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.publicationLabel + "> .\n";
                if (!identificationReferences.isEmpty()) {
                    retTriples += "<" + identificationReferencesURI + "> <" + Resources.rdfsLabel + "> \"" + identificationReferences + "\" .\n";
                }
            }

            if (!actorURI.isEmpty()) {
                retTriples += "<" + actorURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.personLabel + "> .\n";
                if (!actorName.isEmpty()) {
                    retTriples += "<" + actorURI + "> <" + Resources.rdfsLabel + "> \"" + actorName + "\" .\n";
                }
            }

            if (!individualURI.isEmpty()) {
                retTriples += "<" + individualURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.bioticElementLabel + "> .\n";
                if (!individualID.isEmpty()) {
                    retTriples += "<" + individualURI + "> <" + Resources.rdfsLabel + "> \"" + individualID + "\" .\n";
                }
            }

            if (!speciesURI.isEmpty()) {
                retTriples += "<" + speciesURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.speciesLabel + "> .\n";
                if (!speciesName.isEmpty()) {
                    retTriples += "<" + speciesURI + "> <" + Resources.rdfsLabel + "> \"" + speciesName + "\" .\n";
                }
            }

            if (!datasetURI.isEmpty()) {
                retTriples += "<" + datasetURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.datasetLabel + "> .\n";
                if (!datasetID.isEmpty()) {
                    retTriples += "<" + datasetURI + "> <" + Resources.rdfsLabel + "> \"" + datasetID + "\" .\n";
                }
            }

            retTriples += " } ";

            Queries.add(retTriples);

            //System.out.println(retTriples);
        }

        return Queries;

    }

    public ArrayList<String> transform_measurement_csv(String fileToParse, String repositoryGraph) throws FileNotFoundException, IOException {

        //String fileToParse = "C:/uploads/TLOflat/identification.csv";
        BufferedReader fileReader = null;

        System.out.println(fileToParse);
        //Delimiter used in CSV file
        final String DELIMITER = ";";

        String line = "";
        String query = "";
        ArrayList<String> Queries = new ArrayList<String>();

        fileReader = new BufferedReader(new FileReader(fileToParse));
        String URIprefix = "http://www.lifewatchgreece.eu/entity";
        int count = 0;

        while ((line = fileReader.readLine()) != null) {

            if (count == 0) {
                count++;
                continue;
            }

            String[] tokens = line.split(DELIMITER);

            String specimen = tokens[0];
            String measurementID = tokens[1];
            String actorName = tokens[2];
            String timeSpan = tokens[3];
            String dimensionType = tokens[4];
            String dimensionValue = tokens[5];
            String dimensionUnit = tokens[6];
            String datasetID = tokens[7];
            String speciesName = tokens[8];

            String specimenURI = createURI(URIprefix, specimen, "specimen");

            String measurementEventLabel = "Measurement of "+dimensionType+" of "+specimen;
            
           // String measurementEventURI = createURI(URIprefix, measurementID, "measurementEvent");
           // String dimensionTypeURI = createURI(URIprefix, dimensionType, "dimension");
            //  String dimensionURI = createURI(URIprefix, measurementID + dimensionType, "dimension");
            String datasetURI = createURI(URIprefix, datasetID, "dataset");

            String measurementEventURI = "";
            if (measurementID.isEmpty()) {
                measurementEventURI = createURI(URIprefix, datasetID + specimen + dimensionType, "measurementEvent");
            } else {
                if (measurementID.startsWith("http:")) {
                    measurementEventURI = measurementID;
                } else {
                    measurementEventURI = createURI(URIprefix, measurementID, "measurementEvent");
                }
            }

            String actorURI = "";
            if (!actorName.isEmpty()) {
                actorURI = createURI(URIprefix, actorName, "actor");
            }

            String dimensionURI = "";

            if (measurementID.isEmpty()) {
                dimensionURI = createURI(URIprefix, measurementEventURI.replace(URIprefix, "") + dimensionType, "dimension");
            } else {
                dimensionURI = createURI(URIprefix, measurementID + dimensionType, "dimension");
            }

            String dimensionTypeURI = createURI(URIprefix, dimensionType, "dimension");

            String speciesURI = "";
            if (!speciesName.isEmpty()) {
                speciesURI = createURI(URIprefix, speciesName, "species");
            }

            String retTriples = "INSERT INTO <" + repositoryGraph + "> { ";

            if (!measurementEventURI.isEmpty()) {
                retTriples += "<" + measurementEventURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.measurementEventLabel + "> .\n";
                if (!timeSpan.isEmpty()) {
                    retTriples += "<" + measurementEventURI + "> <" + Resources.hasTimespan + "> \"" + timeSpan + "\" .\n";
                }
                if (!specimenURI.isEmpty()) {
                    retTriples += "<" + measurementEventURI + "> <" + Resources.measured + "> <" + specimenURI + "> .\n";
                }
                if (!dimensionURI.isEmpty()) {
                    retTriples += "<" + measurementEventURI + "> <" + Resources.observedDimension + "> <" + dimensionURI + "> .\n";
                }
                if (!actorURI.isEmpty()) {
                    retTriples += "<" + measurementEventURI + "> <" + Resources.carriedOutBy + "> <" + actorURI + "> .\n";
                }
                if (!datasetURI.isEmpty()) {
                    retTriples += "<" + datasetURI + "> <" + Resources.refersTo + "> <" + measurementEventURI + "> . \n";
                }
                if (!measurementEventLabel.isEmpty()) {
                        retTriples += "<" + measurementEventURI + "> <" + Resources.rdfsLabel + "> \"" + measurementEventLabel + "\" .\n";
                }
            }
            if (!dimensionURI.isEmpty()) {
                retTriples += "<" + dimensionURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.dimensionLabel + "> .\n";
                if (!dimensionTypeURI.isEmpty()) {
                    retTriples += "<" + dimensionURI + "> <" + Resources.hasType + "> <" + dimensionTypeURI + "> .\n";
                    if (!dimensionType.isEmpty()) {
                        retTriples += "<" + dimensionTypeURI + "> <" + Resources.rdfsLabel + "> \"" + dimensionType + "\" .\n";
                    }
                    if (!dimensionUnit.isEmpty()) {
                        retTriples += "<" + dimensionURI + "> <" + Resources.hasUnit + "> \"" + dimensionUnit + "\" .\n";

                    }
                    if (!dimensionValue.isEmpty()) {
                        retTriples += "<" + dimensionURI + "> <" + Resources.hasValue + "> \"" + dimensionValue + "\" .\n";

                    }

                }
            }
            if (!specimenURI.isEmpty()) {
                retTriples += "<" + specimenURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.specimenLabel + "> .\n";
                if (!specimen.isEmpty()) {
                    retTriples += "<" + specimenURI + "> <" + Resources.rdfsLabel + "> \"" + specimen + "\" .\n";
                }
                if (!speciesURI.isEmpty()) {
                    retTriples += "<" + specimenURI + "> <" + Resources.belongsTo + "> <" + speciesURI + "> .\n";

                }
            }

            if (!speciesURI.isEmpty()) {
                retTriples += "<" + speciesURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.speciesLabel + "> .\n";
                if (!speciesName.isEmpty()) {
                    retTriples += "<" + speciesURI + "> <" + Resources.rdfsLabel + "> \"" + speciesName + "\" .\n";
                }
            }
            if (!datasetURI.isEmpty()) {
                retTriples += "<" + datasetURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.datasetLabel + "> .\n";
                if (!datasetID.isEmpty()) {
                    retTriples += "<" + datasetURI + "> <" + Resources.rdfsLabel + "> \"" + datasetID + "\" .\n";
                }

            }
            if (!actorURI.isEmpty()) {
                retTriples += "<" + actorURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.personLabel + "> .\n";
                if (!actorName.isEmpty()) {
                    retTriples += "<" + actorURI + "> <" + Resources.rdfsLabel + "> \"" + actorName + "\" .\n";
                }
            }

            retTriples += " } ";

            Queries.add(retTriples);

            //System.out.println(retTriples);
        }

        return Queries;

    }

    public ArrayList<String> transform_measurement_csv(String fileToParse, String repositoryGraph,
            String datasetID) throws FileNotFoundException, IOException {

        //String fileToParse = "C:/uploads/TLOflat/identification.csv";
        BufferedReader fileReader = null;

        System.out.println(fileToParse);
        //Delimiter used in CSV file
        final String DELIMITER = ";";

        String line = "";
        String query = "";
        ArrayList<String> Queries = new ArrayList<String>();

        fileReader = new BufferedReader(new FileReader(fileToParse));
        String URIprefix = "http://www.lifewatchgreece.eu/entity";
        int count = 0;

        while ((line = fileReader.readLine()) != null) {

            if (count == 0) {
                count++;
                continue;
            }

            String[] tokens = line.split(DELIMITER);

            String specimen = tokens[0];
            String measurementID = tokens[1];
            String actorName = tokens[2];
            String timeSpan = tokens[3];
            String dimensionType = tokens[4];
            String dimensionValue = tokens[5];
            String dimensionUnit = tokens[6];
            //  String datasetID=tokens[7];
            String speciesName = tokens[8];

            String specimenURI = createURI(URIprefix, specimen, "specimen");

            String measurementEventLabel = "Measurement of "+dimensionType+" of "+specimen;
            
           // String measurementEventURI = createURI(URIprefix, measurementID, "measurementEvent");
           // String dimensionTypeURI = createURI(URIprefix, dimensionType, "dimension");
            //  String dimensionURI = createURI(URIprefix, measurementID + dimensionType, "dimension");
            String datasetURI = createURI(URIprefix, datasetID, "dataset");

            String measurementEventURI = "";
            if (measurementID.isEmpty()) {
                measurementEventURI = createURI(URIprefix, datasetID + specimen + dimensionType, "measurementEvent");
            } else {
                if (measurementID.startsWith("http:")) {
                    measurementEventURI = measurementID;
                } else {
                    measurementEventURI = createURI(URIprefix, measurementID, "measurementEvent");
                }
            }

            String actorURI = "";
            if (!actorName.isEmpty()) {
                actorURI = createURI(URIprefix, actorName, "actor");
            }

            String dimensionURI = "";

            if (measurementID.isEmpty()) {
                dimensionURI = createURI(URIprefix, measurementEventURI.replace(URIprefix, "") + dimensionType, "dimension");
            } else {
                dimensionURI = createURI(URIprefix, measurementID + dimensionType, "dimension");
            }

            String dimensionTypeURI = createURI(URIprefix, dimensionType, "dimension");

            String speciesURI = "";
            if (!speciesName.isEmpty()) {
                speciesURI = createURI(URIprefix, speciesName, "species");
            }

            String retTriples = "INSERT INTO <" + repositoryGraph + "> { ";

            if (!measurementEventURI.isEmpty()) {
                retTriples += "<" + measurementEventURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.measurementEventLabel + "> .\n";
                if (!timeSpan.isEmpty()) {
                    retTriples += "<" + measurementEventURI + "> <" + Resources.hasTimespan + "> \"" + timeSpan + "\" .\n";
                }
                if (!specimenURI.isEmpty()) {
                    retTriples += "<" + measurementEventURI + "> <" + Resources.measured + "> <" + specimenURI + "> .\n";
                }
                if (!dimensionURI.isEmpty()) {
                    retTriples += "<" + measurementEventURI + "> <" + Resources.observedDimension + "> <" + dimensionURI + "> .\n";
                }
                if (!actorURI.isEmpty()) {
                    retTriples += "<" + measurementEventURI + "> <" + Resources.carriedOutBy + "> <" + actorURI + "> .\n";
                }
                if (!datasetURI.isEmpty()) {
                    retTriples += "<" + datasetURI + "> <" + Resources.refersTo + "> <" + measurementEventURI + "> . \n";
                }
                if (!measurementEventLabel.isEmpty()) {
                        retTriples += "<" + measurementEventURI + "> <" + Resources.rdfsLabel + "> \"" + measurementEventLabel + "\" .\n";
                }

            }
            if (!dimensionURI.isEmpty()) {
                retTriples += "<" + dimensionURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.dimensionLabel + "> .\n";
                if (!dimensionTypeURI.isEmpty()) {
                    retTriples += "<" + dimensionURI + "> <" + Resources.hasType + "> <" + dimensionTypeURI + "> .\n";
                    if (!dimensionType.isEmpty()) {
                        retTriples += "<" + dimensionTypeURI + "> <" + Resources.rdfsLabel + "> \"" + dimensionType + "\" .\n";
                    }
                    if (!dimensionUnit.isEmpty()) {
                        retTriples += "<" + dimensionURI + "> <" + Resources.hasUnit + "> \"" + dimensionUnit + "\" .\n";

                    }
                    if (!dimensionValue.isEmpty()) {
                        retTriples += "<" + dimensionURI + "> <" + Resources.hasValue + "> \"" + dimensionValue + "\" .\n";

                    }

                }
            }
            if (!specimenURI.isEmpty()) {
                retTriples += "<" + specimenURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.specimenLabel + "> .\n";
                if (!specimen.isEmpty()) {
                    retTriples += "<" + specimenURI + "> <" + Resources.rdfsLabel + "> \"" + specimen + "\" .\n";
                }
                if (!speciesURI.isEmpty()) {
                    retTriples += "<" + specimenURI + "> <" + Resources.belongsTo + "> <" + speciesURI + "> .\n";

                }
            }

            if (!speciesURI.isEmpty()) {
                retTriples += "<" + speciesURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.speciesLabel + "> .\n";
                if (!speciesName.isEmpty()) {
                    retTriples += "<" + speciesURI + "> <" + Resources.rdfsLabel + "> \"" + speciesName + "\" .\n";
                }
            }
            if (!datasetURI.isEmpty()) {
                retTriples += "<" + datasetURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.datasetLabel + "> .\n";
                if (!datasetID.isEmpty()) {
                    retTriples += "<" + datasetURI + "> <" + Resources.rdfsLabel + "> \"" + datasetID + "\" .\n";
                }

            }
            if (!actorURI.isEmpty()) {
                retTriples += "<" + actorURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.personLabel + "> .\n";
                if (!actorName.isEmpty()) {
                    retTriples += "<" + actorURI + "> <" + Resources.rdfsLabel + "> \"" + actorName + "\" .\n";
                }
            }

            retTriples += " } ";

            Queries.add(retTriples);

            //System.out.println(retTriples);
        }

        return Queries;

    }

    public ArrayList<String> transform_traits_csv(String fileToParse, String repositoryGraph) throws FileNotFoundException, IOException {

        //String fileToParse = "C:/uploads/TLOflat/identification.csv";
        BufferedReader fileReader = null;

        System.out.println(fileToParse);
        //Delimiter used in CSV file
        final String DELIMITER = ";";

        String line = "";
        String query = "";
        ArrayList<String> Queries = new ArrayList<String>();

        fileReader = new BufferedReader(new FileReader(fileToParse));
        String URIprefix = "http://www.lifewatchgreece.eu/entity";
        int count = 0;

        while ((line = fileReader.readLine()) != null) {

            if (count == 0) {
                count++;
                continue;
            }

            String[] tokens = line.split(DELIMITER);

            String speciesName = tokens[0];
            String attributeAssignmentID = tokens[1];
            String actorName = tokens[2];
            String timeSpan = tokens[3];
            String dimensionType = tokens[4];
            String dimensionValue = tokens[5];
            String dimensionUnit = tokens[6];
            String publication = tokens[7];
            String description = tokens[8];
            String datasetID = tokens[9];

            String speciesURI = createURI(URIprefix, speciesName, "species");

            String datasetURI = createURI(URIprefix, datasetID, "dataset");

            String attributeAssignmentEventLabel = "Assignment of "+dimensionType+" to "+speciesName;
            
            String attributeAssignmentEventURI = "";
            if (attributeAssignmentID.isEmpty()) {
                attributeAssignmentEventURI = createURI(URIprefix, datasetID + speciesName + dimensionType.replace("http://", "") + "AttributeAssignment", "AttributeAssignment");
            } else {

                if (attributeAssignmentID.startsWith("http:")) {
                    attributeAssignmentEventURI = attributeAssignmentID;
                } else {
                    attributeAssignmentEventURI = createURI(URIprefix, attributeAssignmentID, "AttributeAssignment");
                }
            }

            String dimensionTypeURI = "";

            if (dimensionType.startsWith("http:")) {
                dimensionTypeURI = dimensionType;
            } else {
                dimensionTypeURI = createURI(URIprefix, dimensionType, "dimension");
            }

            String dimensionURI = "";

            if (attributeAssignmentID.isEmpty()) {
                dimensionURI = createURI(URIprefix, attributeAssignmentEventURI.replace(URIprefix, "") + dimensionType, "dimension");
            } else {
                dimensionURI = createURI(URIprefix, attributeAssignmentID + dimensionType, "dimension");
            }

            String actorURI = "";

            if (!actorName.isEmpty()) {
                actorURI = createURI(URIprefix, actorName, "actor");
            }

            String publicationURI = "";
            if (!publication.isEmpty()) {
                publicationURI = createURI(URIprefix, publication, "publication");

            }

            String retTriples = "INSERT INTO <" + repositoryGraph + "> { ";

            if (!attributeAssignmentEventURI.isEmpty()) {
                retTriples += "<" + attributeAssignmentEventURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.attributeAssignmentEventLabel + "> .\n";
                if (!timeSpan.isEmpty()) {
                    retTriples += "<" + attributeAssignmentEventURI + "> <" + Resources.hasTimespan + "> \"" + timeSpan + "\" .\n";
                }
                if (!speciesURI.isEmpty()) {
                    retTriples += "<" + attributeAssignmentEventURI + "> <" + Resources.assignedAttributeTo + "> <" + speciesURI + "> .\n";
                }
                if (!dimensionURI.isEmpty()) {
                    retTriples += "<" + attributeAssignmentEventURI + "> <" + Resources.assigned + "> <" + dimensionURI + "> .\n";
                }
                if (!publicationURI.isEmpty()) {
                    retTriples += "<" + publicationURI + "> <" + Resources.refersTo + "> <" + attributeAssignmentEventURI + "> .\n";
                }
                if (!datasetURI.isEmpty()) {
                    retTriples += "<" + datasetURI + "> <" + Resources.refersTo + "> <" + attributeAssignmentEventURI + "> . \n";
                }
                if (!actorURI.isEmpty()) {
                    retTriples += "<" + attributeAssignmentEventURI + "> <" + Resources.carriedOutBy + "> <" + actorURI + "> . \n";
                }
                if (!description.isEmpty()) {
                    retTriples += "<" + attributeAssignmentEventURI + "> <" + Resources.hasNote + "> \"" + description + "\" . \n";
                }
                if (!attributeAssignmentEventLabel.isEmpty()) {
                        retTriples += "<" + attributeAssignmentEventURI + "> <" + Resources.rdfsLabel + "> \"" + attributeAssignmentEventLabel + "\" .\n";
                }
            }
            if (!dimensionURI.isEmpty()) {
                retTriples += "<" + dimensionURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.dimensionLabel + "> .\n";
                if (!dimensionTypeURI.isEmpty()) {
                    retTriples += "<" + dimensionURI + "> <" + Resources.hasType + "> <" + dimensionTypeURI + "> .\n";
                    if (!dimensionType.isEmpty()) {
                        retTriples += "<" + dimensionTypeURI + "> <" + Resources.rdfsLabel + "> \"" + dimensionType + "\" .\n";
                    }
                    if (!dimensionUnit.isEmpty()) {
                        retTriples += "<" + dimensionURI + "> <" + Resources.hasUnit + "> \"" + dimensionUnit + "\" .\n";

                    }
                    if (!dimensionValue.isEmpty()) {
                        retTriples += "<" + dimensionURI + "> <" + Resources.hasValue + "> \"" + dimensionValue + "\" .\n";

                    }

                }
            }
            if (!speciesURI.isEmpty()) {
                retTriples += "<" + speciesURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.speciesLabel + "> .\n";
                if (!speciesName.isEmpty()) {
                    retTriples += "<" + speciesURI + "> <" + Resources.rdfsLabel + "> \"" + speciesName + "\" .\n";
                }

            }

            if (!datasetURI.isEmpty()) {
                retTriples += "<" + datasetURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.datasetLabel + "> .\n";
                if (!datasetID.isEmpty()) {
                    retTriples += "<" + datasetURI + "> <" + Resources.rdfsLabel + "> \"" + datasetID + "\" .\n";
                }

            }

            if (!publicationURI.isEmpty()) {
                retTriples += "<" + publicationURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.publicationLabel + "> .\n";
                if (!publication.isEmpty()) {
                    retTriples += "<" + publicationURI + "> <" + Resources.rdfsLabel + "> \"" + publication + "\" .\n";
                }

            }

            if (!actorURI.isEmpty()) {
                retTriples += "<" + actorURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.personLabel + "> .\n";
                if (!actorName.isEmpty()) {
                    retTriples += "<" + actorURI + "> <" + Resources.rdfsLabel + "> \"" + actorName + "\" .\n";
                }
            }

            retTriples += " } ";

            Queries.add(retTriples);

            //System.out.println(retTriples);
        }

        return Queries;

    }

    public ArrayList<String> transform_traits_csv(String fileToParse, String repositoryGraph,
            String datasetID) throws FileNotFoundException, IOException {

        //String fileToParse = "C:/uploads/TLOflat/identification.csv";
        BufferedReader fileReader = null;

        System.out.println(fileToParse);
        //Delimiter used in CSV file
        final String DELIMITER = ";";

        String line = "";
        String query = "";
        ArrayList<String> Queries = new ArrayList<String>();

        fileReader = new BufferedReader(new FileReader(fileToParse));
        String URIprefix = "http://www.lifewatchgreece.eu/entity";
        int count = 0;

        while ((line = fileReader.readLine()) != null) {

            if (count == 0) {
                count++;
                continue;
            }

            String[] tokens = line.split(DELIMITER);

            String speciesName = tokens[0];
            String attributeAssignmentID = tokens[1];
            String actorName = tokens[2];
            String timeSpan = tokens[3];
            String dimensionType = tokens[4];
            String dimensionValue = tokens[5];
            String dimensionUnit = tokens[6];
            String publication = tokens[7];
            String description = tokens[8];
            //  String datasetID = tokens[9];

            String speciesURI = createURI(URIprefix, speciesName, "species");

            String datasetURI = createURI(URIprefix, datasetID, "dataset");

            String attributeAssignmentEventLabel = "Assignment of "+dimensionType+" to "+speciesName;
            
            String attributeAssignmentEventURI = "";
            if (attributeAssignmentID.isEmpty()) {
                attributeAssignmentEventURI = createURI(URIprefix, datasetID + speciesName + dimensionType.replace("http://", "") + "AttributeAssignment", "AttributeAssignment");
            } else {

                if (attributeAssignmentID.startsWith("http:")) {
                    attributeAssignmentEventURI = attributeAssignmentID;
                } else {
                    attributeAssignmentEventURI = createURI(URIprefix, attributeAssignmentID, "AttributeAssignment");
                }
            }

            String dimensionTypeURI = "";

            if (dimensionType.startsWith("http:")) {
                dimensionTypeURI = dimensionType;
            } else {
                dimensionTypeURI = createURI(URIprefix, dimensionType, "dimension");
            }

            String dimensionURI = "";

            if (attributeAssignmentID.isEmpty()) {
                dimensionURI = createURI(URIprefix, attributeAssignmentEventURI.replace(URIprefix, "") + dimensionType, "dimension");
            } else {
                dimensionURI = createURI(URIprefix, attributeAssignmentID + dimensionType, "dimension");
            }

            String actorURI = "";

            if (!actorName.isEmpty()) {
                actorURI = createURI(URIprefix, actorName, "actor");
            }

            String publicationURI = "";
            if (!publication.isEmpty()) {
                publicationURI = createURI(URIprefix, publication, "publication");

            }

            String retTriples = "INSERT INTO <" + repositoryGraph + "> { ";

            if (!attributeAssignmentEventURI.isEmpty()) {
                retTriples += "<" + attributeAssignmentEventURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.attributeAssignmentEventLabel + "> .\n";
                if (!timeSpan.isEmpty()) {
                    retTriples += "<" + attributeAssignmentEventURI + "> <" + Resources.hasTimespan + "> \"" + timeSpan + "\" .\n";
                }
                if (!speciesURI.isEmpty()) {
                    retTriples += "<" + attributeAssignmentEventURI + "> <" + Resources.assignedAttributeTo + "> <" + speciesURI + "> .\n";
                }
                if (!dimensionURI.isEmpty()) {
                    retTriples += "<" + attributeAssignmentEventURI + "> <" + Resources.assigned + "> <" + dimensionURI + "> .\n";
                }
                if (!publicationURI.isEmpty()) {
                    retTriples += "<" + publicationURI + "> <" + Resources.refersTo + "> <" + attributeAssignmentEventURI + "> .\n";
                }
                if (!datasetURI.isEmpty()) {
                    retTriples += "<" + datasetURI + "> <" + Resources.refersTo + "> <" + attributeAssignmentEventURI + "> . \n";
                }
                if (!actorURI.isEmpty()) {
                    retTriples += "<" + attributeAssignmentEventURI + "> <" + Resources.carriedOutBy + "> <" + actorURI + "> . \n";
                }
                if (!description.isEmpty()) {
                    retTriples += "<" + attributeAssignmentEventURI + "> <" + Resources.hasNote + "> \"" + description + "\" . \n";
                }
                if (!attributeAssignmentEventLabel.isEmpty()) {
                        retTriples += "<" + attributeAssignmentEventURI + "> <" + Resources.rdfsLabel + "> \"" + attributeAssignmentEventLabel + "\" .\n";
                }
            }
            if (!dimensionURI.isEmpty()) {
                retTriples += "<" + dimensionURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.dimensionLabel + "> .\n";
                if (!dimensionTypeURI.isEmpty()) {
                    retTriples += "<" + dimensionURI + "> <" + Resources.hasType + "> <" + dimensionTypeURI + "> .\n";
                    if (!dimensionType.isEmpty()) {
                        retTriples += "<" + dimensionTypeURI + "> <" + Resources.rdfsLabel + "> \"" + dimensionType + "\" .\n";
                    }
                    if (!dimensionUnit.isEmpty()) {
                        retTriples += "<" + dimensionURI + "> <" + Resources.hasUnit + "> \"" + dimensionUnit + "\" .\n";

                    }
                    if (!dimensionValue.isEmpty()) {
                        retTriples += "<" + dimensionURI + "> <" + Resources.hasValue + "> \"" + dimensionValue + "\" .\n";

                    }

                }
            }
            if (!speciesURI.isEmpty()) {
                retTriples += "<" + speciesURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.speciesLabel + "> .\n";
                if (!speciesName.isEmpty()) {
                    retTriples += "<" + speciesURI + "> <" + Resources.rdfsLabel+ "> \"" + speciesName + "\" .\n";
                }

            }

            if (!datasetURI.isEmpty()) {
                retTriples += "<" + datasetURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.datasetLabel + "> .\n";
                if (!datasetID.isEmpty()) {
                    retTriples += "<" + datasetURI + "> <" + Resources.rdfsLabel + "> \"" + datasetID + "\" .\n";
                }

            }

            if (!publicationURI.isEmpty()) {
                retTriples += "<" + publicationURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.publicationLabel + "> .\n";
                if (!publication.isEmpty()) {
                    retTriples += "<" + publicationURI + "> <" + Resources.rdfsLabel + "> \"" + publication + "\" .\n";
                }

            }

            if (!actorURI.isEmpty()) {
                retTriples += "<" + actorURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.personLabel + "> .\n";
                if (!actorName.isEmpty()) {
                    retTriples += "<" + actorURI + "> <" + Resources.rdfsLabel + "> \"" + actorName + "\" .\n";
                }
            }

            retTriples += " } ";

            Queries.add(retTriples);

            //System.out.println(retTriples);
        }

        return Queries;

    }

    public ArrayList<String> transform_taxonomy_csv(String fileToParse, String repositoryGraph) throws FileNotFoundException, IOException {

        //String fileToParse = "C:/uploads/TLOflat/identification.csv";
        BufferedReader fileReader = null;

        System.out.println(fileToParse);
        //Delimiter used in CSV file
        final String DELIMITER = ";";

        String line = "";
        String query = "";
        ArrayList<String> Queries = new ArrayList<String>();

        fileReader = new BufferedReader(new FileReader(fileToParse));
        String URIprefix = "http://www.lifewatchgreece.eu/entity";
        int count = 0;

        while ((line = fileReader.readLine()) != null) {

            if (count == 0) {
                count++;
                continue;
            }

            String[] tokens = line.split(DELIMITER);

            String speciesName = tokens[0];
            String genusName = tokens[1];
            String familyName = tokens[2];
            String orderName = tokens[3];
            String className = tokens[4];
            String phylumName = tokens[5];
            String kingdomName = tokens[6];
            String datasetID = tokens[7];

            String speciesURI = "";
             if (!(speciesName.startsWith("http")||speciesName.startsWith("urn"))) {
                speciesURI = createURI(URIprefix, speciesName, "species");
             }
             else
             {
                 speciesURI = speciesName;
             }
            
            
            String genusURI = "";
            if (!genusName.isEmpty()) {
                genusURI = createURI(URIprefix, genusName, "genus");
            }

            String familyURI = "";
            if (!familyName.isEmpty()) {
                familyURI = createURI(URIprefix, familyName, "family");
            }

            String orderURI = "";
            if (!orderName.isEmpty()) {
                orderURI = createURI(URIprefix, orderName, "order");
            }

            String classURI = "";
            if (!className.isEmpty()) {
                classURI = createURI(URIprefix, className, "class");
            }

            String phylumURI = "";
            if (!phylumName.isEmpty()) {
                phylumURI = createURI(URIprefix, phylumName, "phylum");
            }

            String kingdomURI = "";
            if (!kingdomName.isEmpty()) {
                kingdomURI = createURI(URIprefix, kingdomName, "kingdom");
            }

            String datasetURI = createURI(URIprefix, datasetID, "dataset");

            String retTriples = "INSERT INTO <" + repositoryGraph + "> { ";

            if (!speciesURI.isEmpty()) {
                retTriples += "<" + speciesURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.speciesLabel + "> .\n";
                if (!speciesName.isEmpty()) {
                    retTriples += "<" + speciesURI + "> <" + Resources.rdfsLabel + "> \"" + speciesName + "\" .\n";
                }
                if (!genusName.isEmpty()) {
                    retTriples += "<" + speciesURI + "> <" + Resources.belongsTo + "> <" + genusURI + "> .\n";
                }
                if (!datasetURI.isEmpty()) {
                    retTriples += "<" + datasetURI + "> <" + Resources.refersTo + "> <" + speciesURI + "> . \n";
                }
            }

            if (!genusURI.isEmpty()) {
                retTriples += "<" + genusURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.genusLabel + "> .\n";
                if (!genusName.isEmpty()) {
                    retTriples += "<" + genusURI + "> <" + Resources.rdfsLabel + "> \"" + genusName + "\" .\n";
                }
                if (!familyName.isEmpty()) {
                    retTriples += "<" + genusURI + "> <" + Resources.belongsTo + "> <" + familyURI + "> .\n";
                }
            }

            if (!familyURI.isEmpty()) {
                retTriples += "<" + familyURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.familyLabel + "> .\n";
                if (!familyName.isEmpty()) {
                    retTriples += "<" + familyURI + "> <" + Resources.rdfsLabel + "> \"" + familyName + "\" .\n";
                }
                if (!orderName.isEmpty()) {
                    retTriples += "<" + familyURI + "> <" + Resources.belongsTo + "> <" + orderURI + "> .\n";
                }
            }

            if (!orderURI.isEmpty()) {
                retTriples += "<" + orderURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.orderLabel + "> .\n";
                if (!orderName.isEmpty()) {
                    retTriples += "<" + orderURI + "> <" + Resources.rdfsLabel + "> \"" + orderName + "\" .\n";
                }
                if (!className.isEmpty()) {
                    retTriples += "<" + orderURI + "> <" + Resources.belongsTo + "> <" + classURI + "> .\n";
                }
            }

            if (!classURI.isEmpty()) {
                retTriples += "<" + classURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.classLabel + "> .\n";
                if (!className.isEmpty()) {
                    retTriples += "<" + classURI + "> <" + Resources.rdfsLabel + "> \"" + className + "\" .\n";
                }
                if (!phylumName.isEmpty()) {
                    retTriples += "<" + classURI + "> <" + Resources.belongsTo + "> <" + phylumURI + "> .\n";
                }
            }

            if (!phylumURI.isEmpty()) {
                retTriples += "<" + phylumURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.phylumLabel + "> .\n";
                if (!phylumName.isEmpty()) {
                    retTriples += "<" + phylumURI + "> <" + Resources.rdfsLabel + "> \"" + phylumName + "\" .\n";
                }
            }

            if (!kingdomName.isEmpty()) {
                retTriples += "<" + phylumURI + "> <" + Resources.belongsTo + "> <" + kingdomURI + "> .\n";
                retTriples += "<" + kingdomURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.kingdomLabel + "> .\n";
                retTriples += "<" + kingdomURI + "> <" + Resources.rdfsLabel + "> \"" + kingdomName + "\" .\n";
            }

            if (!datasetURI.isEmpty()) {
                retTriples += "<" + datasetURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.datasetLabel + "> .\n";
                if (!datasetID.isEmpty()) {
                    retTriples += "<" + datasetURI + "> <" + Resources.rdfsLabel + "> \"" + datasetID + "\" .\n";
                }
            }

            retTriples += " } ";

            Queries.add(retTriples);

            //System.out.println(retTriples);
        }

        return Queries;

    }

    public ArrayList<String> transform_taxonomy_csv(String fileToParse, String repositoryGraph, String datasetID) throws FileNotFoundException, IOException {

        //String fileToParse = "C:/uploads/TLOflat/identification.csv";
        BufferedReader fileReader = null;

        System.out.println(fileToParse);
        //Delimiter used in CSV file
        final String DELIMITER = ";";

        String line = "";
        String query = "";
        ArrayList<String> Queries = new ArrayList<String>();

        fileReader = new BufferedReader(new FileReader(fileToParse));
        String URIprefix = "http://www.lifewatchgreece.eu/entity";
        int count = 0;

        while ((line = fileReader.readLine()) != null) {

            if (count == 0) {
                count++;
                continue;
            }

            String[] tokens = line.split(DELIMITER);

            String speciesName = tokens[0];
            String genusName = tokens[1];
            String familyName = tokens[2];
            String orderName = tokens[3];
            String className = tokens[4];
            String phylumName = tokens[5];
            String kingdomName = tokens[6];
            //   String datasetID = tokens[7];

            String speciesURI = "";

             if (!(speciesName.startsWith("http")||speciesName.startsWith("urn"))) {
                speciesURI = createURI(URIprefix, speciesName, "species");
             }
             else
             {
                 speciesURI = speciesName;
             }
            String genusURI = "";
            if (!genusName.isEmpty()) {
                genusURI = createURI(URIprefix, genusName, "genus");
            }

            String familyURI = "";
            if (!familyName.isEmpty()) {
                familyURI = createURI(URIprefix, familyName, "family");
            }

            String orderURI = "";
            if (!orderName.isEmpty()) {
                orderURI = createURI(URIprefix, orderName, "order");
            }

            String classURI = "";
            if (!className.isEmpty()) {
                classURI = createURI(URIprefix, className, "class");
            }

            String phylumURI = "";
            if (!phylumName.isEmpty()) {
                phylumURI = createURI(URIprefix, phylumName, "phylum");
            }

            String kingdomURI = "";
            if (!kingdomName.isEmpty()) {
                kingdomURI = createURI(URIprefix, kingdomName, "kingdom");
            }

            String datasetURI = createURI(URIprefix, datasetID, "dataset");

            String retTriples = "INSERT INTO <" + repositoryGraph + "> { ";

            if (!speciesURI.isEmpty()) {
                retTriples += "<" + speciesURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.speciesLabel + "> .\n";
                if (!speciesName.isEmpty()) {
                    retTriples += "<" + speciesURI + "> <" + Resources.rdfsLabel + "> \"" + speciesName + "\" .\n";
                }
                if (!genusName.isEmpty()) {
                    retTriples += "<" + speciesURI + "> <" + Resources.belongsTo + "> <" + genusURI + "> .\n";
                }
                if (!datasetURI.isEmpty()) {
                    retTriples += "<" + datasetURI + "> <" + Resources.refersTo + "> <" + speciesURI + "> . \n";
                }
            }

            if (!genusURI.isEmpty()) {
                retTriples += "<" + genusURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.genusLabel + "> .\n";
                if (!genusName.isEmpty()) {
                    retTriples += "<" + genusURI + "> <" + Resources.rdfsLabel + "> \"" + genusName + "\" .\n";
                }
                if (!familyName.isEmpty()) {
                    retTriples += "<" + genusURI + "> <" + Resources.belongsTo + "> <" + familyURI + "> .\n";
                }
            }

            if (!familyURI.isEmpty()) {
                retTriples += "<" + familyURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.familyLabel + "> .\n";
                if (!familyName.isEmpty()) {
                    retTriples += "<" + familyURI + "> <" + Resources.rdfsLabel + "> \"" + familyName + "\" .\n";
                }
                if (!orderName.isEmpty()) {
                    retTriples += "<" + familyURI + "> <" + Resources.belongsTo + "> <" + orderURI + "> .\n";
                }
            }

            if (!orderURI.isEmpty()) {
                retTriples += "<" + orderURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.orderLabel + "> .\n";
                if (!orderName.isEmpty()) {
                    retTriples += "<" + orderURI + "> <" + Resources.rdfsLabel + "> \"" + orderName + "\" .\n";
                }
                if (!className.isEmpty()) {
                    retTriples += "<" + orderURI + "> <" + Resources.belongsTo + "> <" + classURI + "> .\n";
                }
            }

            if (!classURI.isEmpty()) {
                retTriples += "<" + classURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.classLabel + "> .\n";
                if (!className.isEmpty()) {
                    retTriples += "<" + classURI + "> <" + Resources.rdfsLabel + "> \"" + className + "\" .\n";
                }
                if (!phylumName.isEmpty()) {
                    retTriples += "<" + classURI + "> <" + Resources.belongsTo + "> <" + phylumURI + "> .\n";
                }
            }

            if (!phylumURI.isEmpty()) {
                retTriples += "<" + phylumURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.phylumLabel + "> .\n";
                if (!phylumName.isEmpty()) {
                    retTriples += "<" + phylumURI + "> <" + Resources.rdfsLabel + "> \"" + phylumName + "\" .\n";
                }
            }

            if (!kingdomName.isEmpty()) {
                retTriples += "<" + phylumURI + "> <" + Resources.belongsTo + "> <" + kingdomURI + "> .\n";
                retTriples += "<" + kingdomURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.kingdomLabel + "> .\n";
                retTriples += "<" + kingdomURI + "> <" + Resources.rdfsLabel + "> \"" + kingdomName + "\" .\n";
            }

            if (!datasetURI.isEmpty()) {
                retTriples += "<" + datasetURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.datasetLabel + "> .\n";
                if (!datasetID.isEmpty()) {
                    retTriples += "<" + datasetURI + "> <" + Resources.rdfsLabel+ "> \"" + datasetID + "\" .\n";
                }
            }

            retTriples += " } ";

            Queries.add(retTriples);

            //System.out.println(retTriples);
        }

        return Queries;
    }

    public ArrayList<String> transform_scientific_name_csv(String fileToParse, String repositoryGraph) throws FileNotFoundException, IOException {

        //String fileToParse = "C:/uploads/TLOflat/identification.csv";
        BufferedReader fileReader = null;

        System.out.println(fileToParse);
        //Delimiter used in CSV file
        final String DELIMITER = ";";

        String line = "";
        String query = "";
        ArrayList<String> Queries = new ArrayList<String>();

        fileReader = new BufferedReader(new FileReader(fileToParse));
        String URIprefix = "http://www.lifewatchgreece.eu/entity";
        int count = 0;

        while ((line = fileReader.readLine()) != null) {

            if (count == 0) {
                count++;
                continue;
            }

            String[] tokens = line.split(DELIMITER);

            String scientificNameAssignmentEvent = tokens[0];

            String actorName = tokens[1];

            String timeSpan = tokens[2];

            String appellation = tokens[3];
            String nomenclaturalCode = tokens[4];
            String species = tokens[5];
            String datasetID = tokens[6];

            String scientificNameAssignmentEventURI = "";

            String scientificNameAssignmentEventLabel= "Scientific name assigmnent of"+species;
            
            if (scientificNameAssignmentEvent.isEmpty()) {
                scientificNameAssignmentEventURI = createURI(URIprefix, datasetID + species, "scientificNameAssignmentEvent");
            } else {
                if (scientificNameAssignmentEvent.startsWith("http:")) {
                    scientificNameAssignmentEventURI = scientificNameAssignmentEvent;
                } else {
                    scientificNameAssignmentEventURI = createURI(URIprefix, scientificNameAssignmentEvent, "scientificNameAssignmentEvent");
                }
            }

            String actorURI = "";
            if (!actorName.isEmpty()) {
                actorURI = createURI(URIprefix, actorName, "actor");
            }

            String nomenclaturalCodeURI = "";
            if (!nomenclaturalCode.isEmpty()) {
                nomenclaturalCodeURI = createURI(URIprefix, nomenclaturalCode, "protocol");
            }

            String speciesURI = "";
            if (species.startsWith("http:")) {
                speciesURI = species;
            } else {
                speciesURI = createURI(URIprefix, species, "species");
            }

            String datasetURI = createURI(URIprefix, datasetID, "dataset");

            String appellationURI = createURI(URIprefix, appellation, "appellation");

            String retTriples = "INSERT INTO <" + repositoryGraph + "> { ";

            if (!scientificNameAssignmentEventURI.isEmpty()) {
                retTriples += "<" + scientificNameAssignmentEventURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.scientificNameAssignmentEventLabel + "> .\n";
                if (!timeSpan.isEmpty()) {
                    retTriples += "<" + scientificNameAssignmentEventURI + "> <" + Resources.hasTimespan + "> \"" + timeSpan + "\" .\n";
                }
                if (!speciesURI.isEmpty()) {
                    retTriples += "<" + scientificNameAssignmentEventURI + "> <" + Resources.assignedAttributeTo + "> <" + speciesURI + "> .\n";
                }
                if (!appellationURI.isEmpty()) {
                    retTriples += "<" + scientificNameAssignmentEventURI + "> <" + Resources.assigned + "> <" + appellationURI + "> .\n";
                }

                if (!actorURI.isEmpty()) {
                    retTriples += "<" + scientificNameAssignmentEventURI + "> <" + Resources.carriedOutBy + "> <" + actorURI + "> .\n";
                }
                if (!nomenclaturalCodeURI.isEmpty()) {
                    retTriples += "<" + scientificNameAssignmentEventURI + "> <" + Resources.usedSpecificTechnique + "> <" + nomenclaturalCodeURI + "> .\n";
                }
                if (!datasetURI.isEmpty()) {
                    retTriples += "<" + datasetURI + "> <" + Resources.refersTo + "> <" + scientificNameAssignmentEventURI + "> . \n";
                }
                if (!scientificNameAssignmentEventLabel.isEmpty()) {
                    retTriples += "<" + scientificNameAssignmentEventURI + "> <" + Resources.rdfsLabel + "> \"" + scientificNameAssignmentEventLabel + "\" .\n";
                }
            }
            if (!nomenclaturalCodeURI.isEmpty()) {
                //    retTriples+= "<"+nomenclaturalCodeURI+"> <"+Resources.rdfTypeLabel+"> <"+Resources.+"> .\n";
                if (!nomenclaturalCode.isEmpty()) {
                    retTriples += "<" + nomenclaturalCodeURI + "> <" + Resources.rdfsLabel+ "> \"" + nomenclaturalCode + "\" .\n";
                }

            }

            if (!appellationURI.isEmpty()) {
                retTriples += "<" + appellationURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.appellationLabel + "> .\n";
                if (!appellation.isEmpty()) {
                    retTriples += "<" + appellationURI + "> <" + Resources.rdfsLabel + "> \"" + appellation + "\" .\n";
                }

            }

            if (!speciesURI.isEmpty()) {
                retTriples += "<" + speciesURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.speciesLabel + "> .\n";
                if (!appellation.isEmpty()) {
                    retTriples += "<" + speciesURI + "> <" + Resources.rdfsLabel + "> \"" + appellation + "\" .\n";
                }

            }

            if (!datasetURI.isEmpty()) {
                retTriples += "<" + datasetURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.datasetLabel + "> .\n";
                if (!datasetID.isEmpty()) {
                    retTriples += "<" + datasetURI + "> <" + Resources.rdfsLabel + "> \"" + datasetID + "\" .\n";
                }
            }

            if (!actorURI.isEmpty()) {
                retTriples += "<" + actorURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.actorLabel + "> .\n";
                if (!actorName.isEmpty()) {
                    retTriples += "<" + actorURI + "> <" + Resources.rdfsLabel + "> \"" + actorName + "\" .\n";
                }
            }

            retTriples += " } ";

            Queries.add(retTriples);

            //System.out.println(retTriples);
        }

        return Queries;

    }

    public ArrayList<String> transform_scientific_name_csv(String fileToParse, String repositoryGraph,
            String datasetID) throws FileNotFoundException, IOException {

        //String fileToParse = "C:/uploads/TLOflat/identification.csv";
        BufferedReader fileReader = null;

        System.out.println(fileToParse);
        //Delimiter used in CSV file
        final String DELIMITER = ";";

        String line = "";
        String query = "";
        ArrayList<String> Queries = new ArrayList<String>();

        fileReader = new BufferedReader(new FileReader(fileToParse));
        String URIprefix = "http://www.lifewatchgreece.eu/entity";
        int count = 0;

        while ((line = fileReader.readLine()) != null) {

            if (count == 0) {
                count++;
                continue;
            }

            String[] tokens = line.split(DELIMITER);

            String scientificNameAssignmentEvent = tokens[0];

            String actorName = tokens[1];

            String timeSpan = tokens[2];

            String appellation = tokens[3];
            String nomenclaturalCode = tokens[4];
            String species = tokens[5];
           // String datasetID = tokens[6];

            String scientificNameAssignmentEventURI = "";

            String scientificNameAssignmentEventLabel= "Scientific name assigmnent of"+species;
            
            if (scientificNameAssignmentEvent.isEmpty()) {
                scientificNameAssignmentEventURI = createURI(URIprefix, datasetID + species, "scientificNameAssignmentEvent");
            } else {
                if (scientificNameAssignmentEvent.startsWith("http:")) {
                    scientificNameAssignmentEventURI = scientificNameAssignmentEvent;
                } else {
                    scientificNameAssignmentEventURI = createURI(URIprefix, scientificNameAssignmentEvent, "scientificNameAssignmentEvent");
                }
            }

            String actorURI = "";
            if (!actorName.isEmpty()) {
                actorURI = createURI(URIprefix, actorName, "actor");
            }

            String nomenclaturalCodeURI = "";
            if (!nomenclaturalCode.isEmpty()) {
                nomenclaturalCodeURI = createURI(URIprefix, nomenclaturalCode, "protocol");
            }

            String speciesURI = "";
            if (species.startsWith("http:")) {
                speciesURI = species;
            } else {
                speciesURI = createURI(URIprefix, species, "species");
            }

            String datasetURI = createURI(URIprefix, datasetID, "dataset");

            String appellationURI = createURI(URIprefix, appellation, "appellation");

            String retTriples = "INSERT INTO <" + repositoryGraph + "> { ";

            if (!scientificNameAssignmentEventURI.isEmpty()) {
                retTriples += "<" + scientificNameAssignmentEventURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.scientificNameAssignmentEventLabel + "> .\n";
                if (!timeSpan.isEmpty()) {
                    retTriples += "<" + scientificNameAssignmentEventURI + "> <" + Resources.hasTimespan + "> \"" + timeSpan + "\" .\n";
                }
                if (!speciesURI.isEmpty()) {
                    retTriples += "<" + scientificNameAssignmentEventURI + "> <" + Resources.assignedAttributeTo + "> <" + speciesURI + "> .\n";
                }
                if (!appellationURI.isEmpty()) {
                    retTriples += "<" + scientificNameAssignmentEventURI + "> <" + Resources.assigned + "> <" + appellationURI + "> .\n";
                }

                if (!actorURI.isEmpty()) {
                    retTriples += "<" + scientificNameAssignmentEventURI + "> <" + Resources.carriedOutBy + "> <" + actorURI + "> .\n";
                }
                if (!nomenclaturalCodeURI.isEmpty()) {
                    retTriples += "<" + scientificNameAssignmentEventURI + "> <" + Resources.usedSpecificTechnique + "> <" + nomenclaturalCodeURI + "> .\n";
                }
                if (!datasetURI.isEmpty()) {
                    retTriples += "<" + datasetURI + "> <" + Resources.refersTo + "> <" + scientificNameAssignmentEventURI + "> . \n";
                }
                if (!scientificNameAssignmentEventLabel.isEmpty()) {
                    retTriples += "<" + scientificNameAssignmentEventURI + "> <" + Resources.rdfsLabel + "> \"" + scientificNameAssignmentEventLabel + "\" .\n";
                }
            }
            if (!nomenclaturalCodeURI.isEmpty()) {
                //    retTriples+= "<"+nomenclaturalCodeURI+"> <"+Resources.rdfTypeLabel+"> <"+Resources.+"> .\n";
                if (!nomenclaturalCode.isEmpty()) {
                    retTriples += "<" + nomenclaturalCodeURI + "> <" + Resources.rdfsLabel+ "> \"" + nomenclaturalCode + "\" .\n";
                }

            }

            if (!appellationURI.isEmpty()) {
                retTriples += "<" + appellationURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.appellationLabel + "> .\n";
                if (!appellation.isEmpty()) {
                    retTriples += "<" + appellationURI + "> <" + Resources.rdfsLabel + "> \"" + appellation + "\" .\n";
                }

            }

            if (!speciesURI.isEmpty()) {
                retTriples += "<" + speciesURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.speciesLabel + "> .\n";
                if (!appellation.isEmpty()) {
                    retTriples += "<" + speciesURI + "> <" + Resources.rdfsLabel + "> \"" + appellation + "\" .\n";
                }

            }

            if (!datasetURI.isEmpty()) {
                retTriples += "<" + datasetURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.datasetLabel + "> .\n";
                if (!datasetID.isEmpty()) {
                    retTriples += "<" + datasetURI + "> <" + Resources.rdfsLabel + "> \"" + datasetID + "\" .\n";
                }
            }

            if (!actorURI.isEmpty()) {
                retTriples += "<" + actorURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.actorLabel + "> .\n";
                if (!actorName.isEmpty()) {
                    retTriples += "<" + actorURI + "> <" + Resources.rdfsLabel + "> \"" + actorName + "\" .\n";
                }
            }

            retTriples += " } ";

            Queries.add(retTriples);

            //System.out.println(retTriples);
        }

        return Queries;

    }

    public ArrayList<String> transform_common_name_csv(String fileToParse, String repositoryGraph) throws FileNotFoundException, IOException {

        //String fileToParse = "C:/uploads/TLOflat/identification.csv";
        BufferedReader fileReader = null;

        System.out.println(fileToParse);
        //Delimiter used in CSV file
        final String DELIMITER = ";";

        String line = "";
        String query = "";
        ArrayList<String> Queries = new ArrayList<String>();

       // fileReader = new BufferedReader(new FileReader(fileToParse));
        fileReader = new BufferedReader(new InputStreamReader(
                new FileInputStream(fileToParse), "UTF8"));

        String URIprefix = "http://www.lifewatchgreece.eu/entity";
        int count = 0;

        while ((line = fileReader.readLine()) != null) {

            if (count == 0) {
                count++;
                continue;
            }

            String[] tokens = line.split(DELIMITER);

            String species = tokens[0];

            String commonName = tokens[1];
            String place = tokens[2];
            String language = tokens[3];
            String datasetID = tokens[4];

            String commonNameURI = createURI(URIprefix, commonName, "appellation");

            String speciesURI = "";
            if (species.startsWith("http:")) {
                speciesURI = species;
            } else {
                speciesURI = createURI(URIprefix, species, "species");
            }

            String placeURI = "";
            if (!place.isEmpty()) {
                placeURI = createURI(URIprefix, place, "place");
            }

            String languageURI = "";
            if (!language.isEmpty()) {
                languageURI = createURI(URIprefix, language, "language");
            }

            String datasetURI = createURI(URIprefix, datasetID, "dataset");

            String retTriples = "INSERT INTO <" + repositoryGraph + "> { ";

            if (!commonNameURI.isEmpty()) {
                retTriples += "<" + commonNameURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.appellationLabel + "> .\n";
                if (!commonName.isEmpty()) {
                    retTriples += "<" + commonNameURI + "> <" + Resources.rdfsLabel + "> \"" + commonName + "\" .\n";
                }

//            if(!speciesURI.isEmpty()){
//                retTriples+= "<"+speciesURI+"> <"+Resources.isIdentifiedBy+"> <"+commonNameURI+"> .\n";
//            }
//            
                if (!speciesURI.isEmpty()) {
                    retTriples += "<" + speciesURI + "> <" + Resources.isAlsoIdentifiedBy + "> <" + commonNameURI + "> .\n";
                }

                if (!languageURI.isEmpty()) {
                    retTriples += "<" + commonNameURI + "> <" + Resources.hasLanguage + "> <" + languageURI + "> .\n";
                }
                if (!placeURI.isEmpty()) {
                    retTriples += "<" + commonNameURI + "> <" + Resources.isUsedIn + "> <" + placeURI + "> .\n";
                }

                if (!datasetURI.isEmpty()) {
                    retTriples += "<" + datasetURI + "> <" + Resources.refersTo + "> <" + commonNameURI + "> . \n";
                }
                if (!datasetID.isEmpty()) {
                    retTriples += "<" + datasetURI + "> <" + Resources.rdfsLabel + "> \"" + datasetID + "\" .\n";
                }
            }

            if (!languageURI.isEmpty()) {
                retTriples += "<" + languageURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.languageLabel + "> .\n";
                if (!language.isEmpty()) {
                    retTriples += "<" + languageURI + "> <" + Resources.rdfsLabel+ "> \"" + language + "\" .\n";
                }

            }

            if (!placeURI.isEmpty()) {
                retTriples += "<" + placeURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.placeLabel + "> .\n";
                if (!place.isEmpty()) {
                    retTriples += "<" + placeURI + "> <" + Resources.rdfsLabel + "> \"" + place + "\" .\n";
                }

            }

            if (!speciesURI.isEmpty()) {
                retTriples += "<" + speciesURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.speciesLabel + "> .\n";
                if (!species.isEmpty()) {
                    retTriples += "<" + speciesURI + "> <" + Resources.rdfsLabel + "> \"" + species + "\" .\n";
                }
//          if(!commonName.isEmpty()){
//                retTriples+= "<"+speciesURI+"> <"+Resources.isIdentifiedBy+"> \""+commonName+"\" .\n";
//            }
            }

            if (!datasetURI.isEmpty()) {
                retTriples += "<" + datasetURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.datasetLabel + "> .\n";
                if (!datasetID.isEmpty()) {
                    retTriples += "<" + datasetURI + "> <" + Resources.rdfsLabel + "> \"" + datasetID + "\" .\n";
                }
            }

            retTriples += " } ";

            Queries.add(retTriples);

            //System.out.println(retTriples);
        }

        return Queries;

    }

    public ArrayList<String> transform_common_name_csv(String fileToParse, String repositoryGraph,
            String datasetID) throws FileNotFoundException, IOException {

        //String fileToParse = "C:/uploads/TLOflat/identification.csv";
        BufferedReader fileReader = null;

        System.out.println(fileToParse);
        //Delimiter used in CSV file
        final String DELIMITER = ";";

        String line = "";
        String query = "";
        ArrayList<String> Queries = new ArrayList<String>();

       // fileReader = new BufferedReader(new FileReader(fileToParse));
        fileReader = new BufferedReader(new InputStreamReader(
                new FileInputStream(fileToParse), "UTF8"));

        String URIprefix = "http://www.lifewatchgreece.eu/entity";
        int count = 0;

        while ((line = fileReader.readLine()) != null) {

            if (count == 0) {
                count++;
                continue;
            }

            String[] tokens = line.split(DELIMITER);

            String species = tokens[0];

            String commonName = tokens[1];
            String place = tokens[2];
            String language = tokens[3];
          //  String datasetID = tokens[4];

            String commonNameURI = createURI(URIprefix, commonName, "appellation");

            String speciesURI = "";
            if (species.startsWith("http:")) {
                speciesURI = species;
            } else {
                speciesURI = createURI(URIprefix, species, "species");
            }

            String placeURI = "";
            if (!place.isEmpty()) {
                placeURI = createURI(URIprefix, place, "place");
            }

            String languageURI = "";
            if (!language.isEmpty()) {
                languageURI = createURI(URIprefix, language, "language");
            }

            String datasetURI = createURI(URIprefix, datasetID, "dataset");

            String retTriples = "INSERT INTO <" + repositoryGraph + "> { ";

            if (!commonNameURI.isEmpty()) {
                retTriples += "<" + commonNameURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.appellationLabel + "> .\n";
                if (!commonName.isEmpty()) {
                    retTriples += "<" + commonNameURI + "> <" + Resources.rdfsLabel + "> \"" + commonName + "\" .\n";
                }

//            if(!speciesURI.isEmpty()){
//                retTriples+= "<"+speciesURI+"> <"+Resources.isIdentifiedBy+"> <"+commonNameURI+"> .\n";
//            }
//            
                if (!speciesURI.isEmpty()) {
                    retTriples += "<" + speciesURI + "> <" + Resources.isAlsoIdentifiedBy + "> <" + commonNameURI + "> .\n";
                }

                if (!languageURI.isEmpty()) {
                    retTriples += "<" + commonNameURI + "> <" + Resources.hasLanguage + "> <" + languageURI + "> .\n";
                }
                if (!placeURI.isEmpty()) {
                    retTriples += "<" + commonNameURI + "> <" + Resources.isUsedIn + "> <" + placeURI + "> .\n";
                }

                if (!datasetURI.isEmpty()) {
                    retTriples += "<" + datasetURI + "> <" + Resources.refersTo + "> <" + commonNameURI + "> . \n";
                }
                if (!datasetID.isEmpty()) {
                    retTriples += "<" + datasetURI + "> <" + Resources.rdfsLabel + "> \"" + datasetID + "\" .\n";
                }
            }

            if (!languageURI.isEmpty()) {
                retTriples += "<" + languageURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.languageLabel + "> .\n";
                if (!language.isEmpty()) {
                    retTriples += "<" + languageURI + "> <" + Resources.rdfsLabel + "> \"" + language + "\" .\n";
                }

            }

            if (!placeURI.isEmpty()) {
                retTriples += "<" + placeURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.placeLabel + "> .\n";
                if (!place.isEmpty()) {
                    retTriples += "<" + placeURI + "> <" + Resources.rdfsLabel + "> \"" + place + "\" .\n";
                }

            }

            if (!speciesURI.isEmpty()) {
                retTriples += "<" + speciesURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.speciesLabel + "> .\n";
                if (!species.isEmpty()) {
                    retTriples += "<" + speciesURI + "> <" + Resources.rdfsLabel + "> \"" + species + "\" .\n";
                }
//          if(!commonName.isEmpty()){
//                retTriples+= "<"+speciesURI+"> <"+Resources.isIdentifiedBy+"> \""+commonName+"\" .\n";
//            }
            }

            if (!datasetURI.isEmpty()) {
                retTriples += "<" + datasetURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.datasetLabel + "> .\n";
                if (!datasetID.isEmpty()) {
                    retTriples += "<" + datasetURI + "> <" + Resources.rdfsLabel + "> \"" + datasetID + "\" .\n";
                }
            }

            retTriples += " } ";

            Queries.add(retTriples);

            //System.out.println(retTriples);
        }

        return Queries;

    }

    public ArrayList<String> transform_synonyms_csv(String fileToParse, String repositoryGraph) throws FileNotFoundException, IOException {

        //String fileToParse = "C:/uploads/TLOflat/identification.csv";
        BufferedReader fileReader = null;

        System.out.println(fileToParse);
        //Delimiter used in CSV file
        final String DELIMITER = ";";

        String line = "";
        String query = "";
        ArrayList<String> Queries = new ArrayList<String>();

        fileReader = new BufferedReader(new FileReader(fileToParse));
        String URIprefix = "http://www.lifewatchgreece.eu/entity";
        int count = 0;

        while ((line = fileReader.readLine()) != null) {

            if (count == 0) {
                count++;
                continue;
            }

            String[] tokens = line.split(DELIMITER);

            String speciesName = tokens[0];
            String appellation = tokens[1];
            String synonym = tokens[2];
            String datasetID = tokens[3];

            String speciesURI = "";
            if (speciesName.startsWith("http:")) {
                speciesURI = speciesName;
            } else {
                speciesURI = createURI(URIprefix, speciesName, "species");
            }

            String appellationURI = createURI(URIprefix, appellation, "appellation");
            String synonymURI = createURI(URIprefix, synonym, "appellation");
            String datasetURI = createURI(URIprefix, datasetID, "dataset");

            String retTriples = "INSERT INTO <" + repositoryGraph + "> { ";

            if (!appellationURI.isEmpty()) {
                retTriples += "<" + appellationURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.appellationLabel + "> .\n";
                if (!appellation.isEmpty()) {
                    retTriples += "<" + appellationURI + "> <" + Resources.rdfsLabel + "> \"" + appellation + "\" .\n";
                }
                if (!synonymURI.isEmpty()) {
                    retTriples += "<" + appellationURI + "> <" + Resources.hasAlternativeForm + "> <" + synonymURI + "> .\n";
                }
                if (!speciesURI.isEmpty()) {
                    retTriples += "<" + speciesURI + "> <" + Resources.isAlsoIdentifiedBy + "> <" + synonymURI + "> .\n";
                }

                if (!datasetURI.isEmpty()) {
                    retTriples += "<" + datasetURI + "> <" + Resources.refersTo + "> <" + synonymURI + "> . \n";
                }
            }

            if (!synonymURI.isEmpty()) {
                retTriples += "<" + synonymURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.appellationLabel + "> .\n";
                if (!synonym.isEmpty()) {
                    retTriples += "<" + synonymURI + "> <" + Resources.rdfsLabel + "> \"" + synonym + "\" .\n";
                }

            }
            if (!datasetURI.isEmpty()) {
                retTriples += "<" + datasetURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.datasetLabel + "> .\n";
                if (!datasetID.isEmpty()) {
                    retTriples += "<" + datasetURI + "> <" + Resources.rdfsLabel + "> \"" + datasetID + "\" .\n";
                }
            }
            if (!speciesURI.isEmpty()) {
                retTriples += "<" + speciesURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.speciesLabel + "> .\n";
                if (!speciesName.isEmpty()) {
                    retTriples += "<" + speciesURI + "> <" + Resources.rdfsLabel + "> \"" + speciesName + "\" .\n";
                }

            }

            retTriples += " } ";

            Queries.add(retTriples);

        }

        return Queries;

    }

    public ArrayList<String> transform_synonyms_csv(String fileToParse, String repositoryGraph,
            String datasetID) throws FileNotFoundException, IOException {

        //String fileToParse = "C:/uploads/TLOflat/identification.csv";
        BufferedReader fileReader = null;

        System.out.println(fileToParse);
        //Delimiter used in CSV file
        final String DELIMITER = ";";

        String line = "";
        String query = "";
        ArrayList<String> Queries = new ArrayList<String>();

        fileReader = new BufferedReader(new FileReader(fileToParse));
        String URIprefix = "http://www.lifewatchgreece.eu/entity";
        int count = 0;

        while ((line = fileReader.readLine()) != null) {

            if (count == 0) {
                count++;
                continue;
            }

            String[] tokens = line.split(DELIMITER);

            String speciesName = tokens[0];
            String appellation = tokens[1];
            String synonym = tokens[2];
            //String datasetID = tokens[3];

            String speciesURI = "";
            if (speciesName.startsWith("http:")) {
                speciesURI = speciesName;
            } else {
                speciesURI = createURI(URIprefix, speciesName, "species");
            }

            String appellationURI = createURI(URIprefix, appellation, "appellation");
            String synonymURI = createURI(URIprefix, synonym, "appellation");
            String datasetURI = createURI(URIprefix, datasetID, "dataset");

            String retTriples = "INSERT INTO <" + repositoryGraph + "> { ";

            if (!appellationURI.isEmpty()) {
                retTriples += "<" + appellationURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.appellationLabel + "> .\n";
                if (!appellation.isEmpty()) {
                    retTriples += "<" + appellationURI + "> <" + Resources.rdfsLabel + "> \"" + appellation + "\" .\n";
                }
                if (!synonymURI.isEmpty()) {
                    retTriples += "<" + appellationURI + "> <" + Resources.hasAlternativeForm + "> <" + synonymURI + "> .\n";
                }
                if (!speciesURI.isEmpty()) {
                    retTriples += "<" + speciesURI + "> <" + Resources.isAlsoIdentifiedBy + "> <" + synonymURI + "> .\n";
                }

                if (!datasetURI.isEmpty()) {
                    retTriples += "<" + datasetURI + "> <" + Resources.refersTo + "> <" + synonymURI + "> . \n";
                }
            }

            if (!synonymURI.isEmpty()) {
                retTriples += "<" + synonymURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.appellationLabel + "> .\n";
                if (!synonym.isEmpty()) {
                    retTriples += "<" + synonymURI + "> <" + Resources.rdfsLabel + "> \"" + synonym + "\" .\n";
                }

            }
            if (!datasetURI.isEmpty()) {
                retTriples += "<" + datasetURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.datasetLabel + "> .\n";
                if (!datasetID.isEmpty()) {
                    retTriples += "<" + datasetURI + "> <" + Resources.rdfsLabel + "> \"" + datasetID + "\" .\n";
                }
            }
            if (!speciesURI.isEmpty()) {
                retTriples += "<" + speciesURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.speciesLabel + "> .\n";
                if (!speciesName.isEmpty()) {
                    retTriples += "<" + speciesURI + "> <" + Resources.rdfsLabel + "> \"" + speciesName + "\" .\n";
                }

            }

            retTriples += " } ";

            Queries.add(retTriples);

        }

        return Queries;

    }

    public ArrayList<String> transform_specimen_csv(String fileToParse, String repositoryGraph) throws FileNotFoundException, IOException {

        //String fileToParse = "C:/uploads/TLOflat/identification.csv";
        BufferedReader fileReader = null;

        System.out.println(fileToParse);
        //Delimiter used in CSV file
        final String DELIMITER = ";";

        String line = "";
        String query = "";
        ArrayList<String> Queries = new ArrayList<String>();

        fileReader = new BufferedReader(new FileReader(fileToParse));
        String URIprefix = "http://www.lifewatchgreece.eu/entity";
        int count = 0;

        while ((line = fileReader.readLine()) != null) {

            if (count == 0) {
                count++;
                continue;
            }

            String[] tokens = line.split(DELIMITER);

            String individual = tokens[0];
            String transformationID = tokens[1];
            String specimenName = tokens[2];
            String collectionName = tokens[3];
            String method = tokens[4];
            String actorName = tokens[5];
            String timeSpan = tokens[6];
            String datasetID = tokens[7];
            String speciesName = tokens[8];

            String specimenURI = createURI(URIprefix, specimenName, "specimen");

            String datasetURI = createURI(URIprefix, datasetID, "dataset");

            String individualURI = "";
            if (!individual.isEmpty()) {
                individualURI = createURI(URIprefix, individual, "bioticElement");
            }

            String transformationEventLabel = "Transformation of "+individual+" into a specimen";
            
            String transformationEventURI = "";
            if (!individual.isEmpty()) {
                {
                    if (transformationID.isEmpty()) {
                        transformationEventURI = createURI(URIprefix, datasetID + individual + specimenName, "transformationEvent");
                    } else {
                        if (transformationID.startsWith("http:")) {
                            transformationEventURI = transformationID;
                        } else {
                            transformationEventURI = createURI(URIprefix, transformationID, "transformationEvent");
                        }
                    }
                }

                String actorURI = "";
                if (!actorName.isEmpty()) {
                    actorURI = createURI(URIprefix, actorName, "actor");
                }

                String speciesURI = "";
                if (!speciesName.isEmpty()) {
                    speciesURI = createURI(URIprefix, speciesName, "species");
                }

                String methodURI = "";
                if (!method.isEmpty()) {
                    methodURI = createURI(URIprefix, method, "protocol");
                }

                String collectionURI = "";
                if (!collectionName.isEmpty()) {
                    collectionURI = createURI(URIprefix, collectionName, "collection");
                }

                String retTriples = "INSERT INTO <" + repositoryGraph + "> { ";

                if (!transformationEventURI.isEmpty()) {
                    retTriples += "<" + transformationEventURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.transformationEventLabel + "> .\n";
                    if (!timeSpan.isEmpty()) {
                        retTriples += "<" + transformationEventURI + "> <" + Resources.hasTimespan + "> \"" + timeSpan + "\" .\n";
                    }
                    if (!individualURI.isEmpty()) {
                        retTriples += "<" + transformationEventURI + "> <" + Resources.transformed + "> <" + individualURI + "> .\n";
                    }
                    if (!specimenURI.isEmpty()) {
                        retTriples += "<" + transformationEventURI + "> <" + Resources.resultedIn + "> <" + specimenURI + "> .\n";
                    }
                    if (!actorURI.isEmpty()) {
                        retTriples += "<" + transformationEventURI + "> <" + Resources.carriedOutBy + "> <" + actorURI + "> .\n";
                    }
                    if (!methodURI.isEmpty()) {
                        retTriples += "<" + transformationEventURI + "> <" + Resources.usedSpecificTechnique + "> <" + methodURI + "> .\n";
                    }

                    if (!datasetURI.isEmpty()) {
                        retTriples += "<" + datasetURI + "> <" + Resources.refersTo + "> <" + specimenURI + "> . \n";
                    }
                    if (!transformationEventLabel.isEmpty()) {
                        retTriples += "<" + transformationEventURI + "> <" + Resources.rdfsLabel + "> \"" + transformationEventLabel + "\" .\n";
                    }

                }

                if (!specimenURI.isEmpty()) {
                    retTriples += "<" + specimenURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.specimenLabel + "> .\n";
                    if (!specimenName.isEmpty()) {
                        retTriples += "<" + specimenURI + "> <" + Resources.rdfsLabel + "> \"" + specimenName + "\" .\n";
                    }
                    if (!speciesURI.isEmpty()) {
                        retTriples += "<" + specimenURI + "> <" + Resources.belongsTo + "> <" + speciesURI + "> .\n";
                    }
                }

                if (!speciesURI.isEmpty()) {
                    retTriples += "<" + speciesURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.speciesLabel + "> .\n";
                    if (!speciesName.isEmpty()) {
                        retTriples += "<" + speciesURI + "> <" + Resources.rdfsLabel+ "> \"" + speciesName + "\" .\n";
                    }

                }

                if (!individualURI.isEmpty()) {
                    retTriples += "<" + individualURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.bioticElementLabel + "> .\n";
//        if(!individualName.isEmpty()){
//                retTriples+= "<"+individualURI+"> <"+Resources.isIdentifiedBy+"> \""+individualName+"\" .\n";
//            }
//         
                }
                if (!collectionURI.isEmpty()) {
                    retTriples += "<" + collectionURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.collectionLabel + "> .\n";
                    if (!collectionName.isEmpty()) {
                        retTriples += "<" + collectionURI + "> <" + Resources.rdfsLabel + "> \"" + collectionName + "\" .\n";
                    }
                    if (!specimenURI.isEmpty()) {
                        retTriples += "<" + specimenURI + "> <" + Resources.formsPartOf + "> <" + collectionURI + "> .\n";
                    }
                }

                if (!datasetURI.isEmpty()) {
                    retTriples += "<" + datasetURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.datasetLabel + "> .\n";
                    if (!datasetID.isEmpty()) {
                        retTriples += "<" + datasetURI + "> <" + Resources.rdfsLabel + "> \"" + datasetID + "\" .\n";
                    }
                }

                if (!methodURI.isEmpty()) {
                    // retTriples+= "<"+specimenURI+"> <"+Resources.rdfTypeLabel+"> <"+Resources.+"> .\n";
                    if (!method.isEmpty()) {
                        retTriples += "<" + methodURI + "> <" + Resources.rdfsLabel+ "> \"" + method + "\" .\n";

                    }

                }

                if (!actorURI.isEmpty()) {
                    retTriples += "<" + actorURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.personLabel + "> .\n";
                    if (!actorName.isEmpty()) {
                        retTriples += "<" + actorURI + "> <" + Resources.rdfsLabel + "> \"" + actorName + "\" .\n";
                    }
                }

                retTriples += " } ";

                Queries.add(retTriples);

            }
        }
        return Queries;
    }

    public ArrayList<String> transform_specimen_csv(String fileToParse, String repositoryGraph,
            String datasetID) throws FileNotFoundException, IOException {

        //String fileToParse = "C:/uploads/TLOflat/identification.csv";
        BufferedReader fileReader = null;

        System.out.println(fileToParse);
        //Delimiter used in CSV file
        final String DELIMITER = ";";

        String line = "";
        String query = "";
        ArrayList<String> Queries = new ArrayList<String>();

        fileReader = new BufferedReader(new FileReader(fileToParse));
        String URIprefix = "http://www.lifewatchgreece.eu/entity";
        int count = 0;

        while ((line = fileReader.readLine()) != null) {

            if (count == 0) {
                count++;
                continue;
            }

            String[] tokens = line.split(DELIMITER);

            String individual = tokens[0];
            String transformationID = tokens[1];
            String specimenName = tokens[2];
            String collectionName = tokens[3];
            String method = tokens[4];
            String actorName = tokens[5];
            String timeSpan = tokens[6];
            //  String datasetID = tokens[7];
            String speciesName = tokens[8];

            String specimenURI = createURI(URIprefix, specimenName, "specimen");

            String datasetURI = createURI(URIprefix, datasetID, "dataset");

            String individualURI = "";
            if (!individual.isEmpty()) {
                individualURI = createURI(URIprefix, individual, "bioticElement");
            }

            String transformationEventURI = "";
            
            String transformationEventLabel = "Transformation of "+individual+" into a specimen";
            
            if (!individual.isEmpty()) {
                {
                    if (transformationID.isEmpty()) {
                        transformationEventURI = createURI(URIprefix, datasetID + individual + specimenName, "transformationEvent");
                    } else {
                        if (transformationID.startsWith("http:")) {
                            transformationEventURI = transformationID;
                        } else {
                            transformationEventURI = createURI(URIprefix, transformationID, "transformationEvent");
                        }
                    }
                }

                String actorURI = "";
                if (!actorName.isEmpty()) {
                    actorURI = createURI(URIprefix, actorName, "actor");
                }

                String speciesURI = "";
                if (!speciesName.isEmpty()) {
                    speciesURI = createURI(URIprefix, speciesName, "species");
                }

                String methodURI = "";
                if (!method.isEmpty()) {
                    methodURI = createURI(URIprefix, method, "protocol");
                }

                String collectionURI = "";
                if (!collectionName.isEmpty()) {
                    collectionURI = createURI(URIprefix, collectionName, "collection");
                }

                String retTriples = "INSERT INTO <" + repositoryGraph + "> { ";

                if (!transformationEventURI.isEmpty()) {
                    retTriples += "<" + transformationEventURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.transformationEventLabel + "> .\n";
                    if (!timeSpan.isEmpty()) {
                        retTriples += "<" + transformationEventURI + "> <" + Resources.hasTimespan + "> \"" + timeSpan + "\" .\n";
                    }
                    if (!individualURI.isEmpty()) {
                        retTriples += "<" + transformationEventURI + "> <" + Resources.transformed + "> <" + individualURI + "> .\n";
                    }
                    if (!specimenURI.isEmpty()) {
                        retTriples += "<" + transformationEventURI + "> <" + Resources.resultedIn + "> <" + specimenURI + "> .\n";
                    }
                    if (!actorURI.isEmpty()) {
                        retTriples += "<" + transformationEventURI + "> <" + Resources.carriedOutBy + "> <" + actorURI + "> .\n";
                    }
                    if (!methodURI.isEmpty()) {
                        retTriples += "<" + transformationEventURI + "> <" + Resources.usedSpecificTechnique + "> <" + methodURI + "> .\n";
                    }

                    if (!datasetURI.isEmpty()) {
                        retTriples += "<" + datasetURI + "> <" + Resources.refersTo + "> <" + specimenURI + "> . \n";
                    }
                    if (!transformationEventLabel.isEmpty()) {
                        retTriples += "<" + transformationEventURI + "> <" + Resources.rdfsLabel + "> \"" + transformationEventLabel + "\" .\n";
                    }

                }

                if (!specimenURI.isEmpty()) {
                    retTriples += "<" + specimenURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.specimenLabel + "> .\n";
                    if (!specimenName.isEmpty()) {
                        retTriples += "<" + specimenURI + "> <" + Resources.rdfsLabel + "> \"" + specimenName + "\" .\n";
                    }
                    if (!speciesURI.isEmpty()) {
                        retTriples += "<" + specimenURI + "> <" + Resources.belongsTo + "> <" + speciesURI + "> .\n";
                    }
                }

                if (!speciesURI.isEmpty()) {
                    retTriples += "<" + speciesURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.speciesLabel + "> .\n";
                    if (!speciesName.isEmpty()) {
                        retTriples += "<" + speciesURI + "> <" + Resources.rdfsLabel + "> \"" + speciesName + "\" .\n";
                    }

                }

                if (!individualURI.isEmpty()) {
                    retTriples += "<" + individualURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.bioticElementLabel + "> .\n";
//        if(!individualName.isEmpty()){
//                retTriples+= "<"+individualURI+"> <"+Resources.isIdentifiedBy+"> \""+individualName+"\" .\n";
//            }
//         
                }
                if (!collectionURI.isEmpty()) {
                    retTriples += "<" + collectionURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.collectionLabel + "> .\n";
                    if (!collectionName.isEmpty()) {
                        retTriples += "<" + collectionURI + "> <" + Resources.rdfsLabel + "> \"" + collectionName + "\" .\n";
                    }
                    if (!specimenURI.isEmpty()) {
                        retTriples += "<" + specimenURI + "> <" + Resources.formsPartOf + "> <" + collectionURI + "> .\n";
                    }
                }

                if (!datasetURI.isEmpty()) {
                    retTriples += "<" + datasetURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.datasetLabel + "> .\n";
                    if (!datasetID.isEmpty()) {
                        retTriples += "<" + datasetURI + "> <" + Resources.rdfsLabel + "> \"" + datasetID + "\" .\n";
                    }
                }

                if (!methodURI.isEmpty()) {
                    // retTriples+= "<"+specimenURI+"> <"+Resources.rdfTypeLabel+"> <"+Resources.+"> .\n";
                    if (!method.isEmpty()) {
                        retTriples += "<" + methodURI + "> <" + Resources.rdfsLabel + "> \"" + method + "\" .\n";

                    }

                }

                if (!actorURI.isEmpty()) {
                    retTriples += "<" + actorURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.personLabel + "> .\n";
                    if (!actorName.isEmpty()) {
                        retTriples += "<" + actorURI + "> <" + Resources.rdfsLabel + "> \"" + actorName + "\" .\n";
                    }
                }

                retTriples += " } ";

                Queries.add(retTriples);

            }
        }
        return Queries;
    }

    public ArrayList<String> transform_specimen_collection_csv(String fileToParse, String repositoryGraph) throws FileNotFoundException, IOException {

        //String fileToParse = "C:/uploads/TLOflat/identification.csv";
        BufferedReader fileReader = null;

        System.out.println(fileToParse);
        //Delimiter used in CSV file
        final String DELIMITER = ";";

        String line = "";
        String query = "";
        ArrayList<String> Queries = new ArrayList<String>();

        fileReader = new BufferedReader(new FileReader(fileToParse));
        String URIprefix = "http://www.lifewatchgreece.eu/entity";
        int count = 0;

        while ((line = fileReader.readLine()) != null) {

            if (count == 0) {
                count++;
                continue;
            }

            String[] tokens = line.split(DELIMITER);

            String collectionName = tokens[0];
            String actorName = tokens[1];
            String timespan = tokens[2];
            String ownerName = tokens[3];
            String keeperName = tokens[4];
            String curatorName = tokens[5];
            String contactPoint = tokens[6];
            String description = tokens[7];
            String datasetName = tokens[8];

            String collectionURI = createURI(URIprefix, collectionName, "collection");

            String datasetURI = createURI(URIprefix, datasetName, "dataset");;

            String ownerURI = createURI(URIprefix, ownerName, "actor");

            String keeperURI = "";
            if (!keeperName.isEmpty()) {
                keeperURI = createURI(URIprefix, keeperName, "actor");
            }

            String curatorURI = createURI(URIprefix, curatorName, "actor");

            String creationEventLabel = "Creation of "+collectionName+" specimen collection";
            
            String creationEventURI = "";
            if (!timespan.isEmpty() || !actorName.isEmpty()) {
                creationEventURI = createURI(URIprefix, collectionName + "Collection" + "Creation", "creationEvent");
            }

            String actorURI = "";
            if (!actorName.isEmpty()) {
                actorURI = createURI(URIprefix, actorName, "actor");
            }

            String retTriples = "INSERT INTO <" + repositoryGraph + "> { ";

            if (!collectionURI.isEmpty()) {
                retTriples += "<" + collectionURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.collectionLabel + "> .\n";
                if (!collectionName.isEmpty()) {
                    retTriples += "<" + collectionURI + "> <" + Resources.rdfsLabel + "> \"" + collectionName + "\" .\n";
                }
                if (!ownerURI.isEmpty()) {
                    retTriples += "<" + collectionURI + "> <" + Resources.hasCurrentOwner + "> <" + ownerURI + "> .\n";
                }
                if (!creationEventURI.isEmpty()) {
                    retTriples += "<" + collectionURI + "> <" + Resources.wasCreatedBy + "> <" + creationEventURI + "> .\n";
                }
                if (!curatorURI.isEmpty()) {
                    retTriples += "<" + collectionURI + "> <" + Resources.hasCurator + "> <" + curatorURI + "> .\n";
                }
                if (!keeperURI.isEmpty()) {
                    retTriples += "<" + collectionURI + "> <" + Resources.hasCurrentKeeper + "> <" + keeperURI + "> .\n";
                }

                if (!datasetURI.isEmpty()) {
                    retTriples += "<" + datasetURI + "> <" + Resources.refersTo + "> <" + collectionURI + "> .\n";
                }

                if (!description.isEmpty()) {
                    retTriples += "<" + collectionURI + "> <" + Resources.hasNote + "> \"" + description + "\" .\n";
                }

            }
            if (!ownerURI.isEmpty()) {
                retTriples += "<" + ownerURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.actorLabel + "> .\n";
                if (!ownerName.isEmpty()) {
                    retTriples += "<" + ownerURI + "> <" + Resources.rdfsLabel + "> \"" + ownerName + "\" .\n";
                }
            }

            if (!creationEventURI.isEmpty()) {
                retTriples += "<" + creationEventURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.creationEventLabel + "> .\n";
                if (!actorURI.isEmpty()) {
                    retTriples += "<" + creationEventURI + "> <" + Resources.carriedOutBy + "> <" + actorURI + "> .\n";
                }
                if (!timespan.isEmpty()) {
                    retTriples += "<" + creationEventURI + "> <" + Resources.hasTimespan + "> \"" + timespan + "\" .\n";
                }
                if (!creationEventLabel.isEmpty()) {
                    retTriples += "<" + creationEventURI + "> <" + Resources.rdfsLabel + "> \"" + creationEventLabel + "\" .\n";
                }
            }

            if (!actorURI.isEmpty()) {
                retTriples += "<" + actorURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.actorLabel + "> .\n";
                if (!actorName.isEmpty()) {
                    retTriples += "<" + actorURI + "> <" + Resources.rdfsLabel + "> \"" + actorName + "\" .\n";
                }
            }

            if (!datasetURI.isEmpty()) {
                retTriples += "<" + datasetURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.datasetLabel + "> .\n";
                if (!datasetName.isEmpty()) {
                    retTriples += "<" + datasetURI + "> <" + Resources.rdfsLabel + "> \"" + datasetName + "\" .\n";
                }
            }

            if (!curatorURI.isEmpty()) {
                retTriples += "<" + curatorURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.actorLabel + "> .\n";
                if (!curatorName.isEmpty()) {
                    retTriples += "<" + curatorURI + "> <" + Resources.rdfsLabel + "> \"" + curatorName + "\" .\n";
                }
                if (!contactPoint.isEmpty()) {
                    retTriples += "<" + curatorURI + "> <" + Resources.hasContactPoint + "> \"" + contactPoint + "\" .\n";
                }
            }
            if (!keeperURI.isEmpty()) {
                retTriples += "<" + keeperURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.actorLabel + "> .\n";
                if (!keeperName.isEmpty()) {
                    retTriples += "<" + keeperURI + "> <" + Resources.rdfsLabel + "> \"" + keeperName + "\".";
                }
            }

            retTriples += " } ";

            Queries.add(retTriples);

        }

        return Queries;

    }

    public ArrayList<String> transform_specimen_collection_csv(String fileToParse, String repositoryGraph,String datasetName) throws FileNotFoundException, IOException {

        //String fileToParse = "C:/uploads/TLOflat/identification.csv";
        BufferedReader fileReader = null;

        System.out.println(fileToParse);
        //Delimiter used in CSV file
        final String DELIMITER = ";";

        String line = "";
        String query = "";
        ArrayList<String> Queries = new ArrayList<String>();

        fileReader = new BufferedReader(new FileReader(fileToParse));
        String URIprefix = "http://www.lifewatchgreece.eu/entity";
        int count = 0;

        while ((line = fileReader.readLine()) != null) {

            if (count == 0) {
                count++;
                continue;
            }

            String[] tokens = line.split(DELIMITER);

            String collectionName = tokens[0];
            String actorName = tokens[1];
            String timespan = tokens[2];
            String ownerName = tokens[3];
            String keeperName = tokens[4];
            String curatorName = tokens[5];
            String contactPoint = tokens[6];
            String description = tokens[7];
            //  String datasetName = tokens[8];

            String collectionURI = createURI(URIprefix, collectionName, "collection");

            String datasetURI = createURI(URIprefix, datasetName, "dataset");;

            String ownerURI = createURI(URIprefix, ownerName, "actor");

            String creationEventLabel = "Creation of "+collectionName+" specimen collection";
              
            String keeperURI = "";
            if (!keeperName.isEmpty()) {
                keeperURI = createURI(URIprefix, keeperName, "actor");
            }

            String curatorURI = createURI(URIprefix, curatorName, "actor");

            String creationEventURI = "";
            if (!timespan.isEmpty() || !actorName.isEmpty()) {
                creationEventURI = createURI(URIprefix, collectionName + "Collection" + "Creation", "creationEvent");
            }

            String actorURI = "";
            if (!actorName.isEmpty()) {
                actorURI = createURI(URIprefix, actorName, "actor");
            }

            String retTriples = "INSERT INTO <" + repositoryGraph + "> { ";

            if (!collectionURI.isEmpty()) {
                retTriples += "<" + collectionURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.collectionLabel + "> .\n";
                if (!collectionName.isEmpty()) {
                    retTriples += "<" + collectionURI + "> <" + Resources.rdfsLabel + "> \"" + collectionName + "\" .\n";
                }
                if (!ownerURI.isEmpty()) {
                    retTriples += "<" + collectionURI + "> <" + Resources.hasCurrentOwner + "> <" + ownerURI + "> .\n";
                }
                if (!creationEventURI.isEmpty()) {
                    retTriples += "<" + collectionURI + "> <" + Resources.wasCreatedBy + "> <" + creationEventURI + "> .\n";
                }
                if (!curatorURI.isEmpty()) {
                    retTriples += "<" + collectionURI + "> <" + Resources.hasCurator + "> <" + curatorURI + "> .\n";
                }
                if (!keeperURI.isEmpty()) {
                    retTriples += "<" + collectionURI + "> <" + Resources.hasCurrentKeeper + "> <" + keeperURI + "> .\n";
                }

                if (!datasetURI.isEmpty()) {
                    retTriples += "<" + datasetURI + "> <" + Resources.refersTo + "> <" + collectionURI + "> .\n";
                }

                if (!description.isEmpty()) {
                    retTriples += "<" + collectionURI + "> <" + Resources.hasNote + "> \"" + description + "\" .\n";
                }

            }
            if (!ownerURI.isEmpty()) {
                retTriples += "<" + ownerURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.actorLabel + "> .\n";
                if (!ownerName.isEmpty()) {
                    retTriples += "<" + ownerURI + "> <" + Resources.rdfsLabel+ "> \"" + ownerName + "\" .\n";
                }
            }

            if (!creationEventURI.isEmpty()) {
                retTriples += "<" + creationEventURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.creationEventLabel + "> .\n";
                if (!actorURI.isEmpty()) {
                    retTriples += "<" + creationEventURI + "> <" + Resources.carriedOutBy + "> <" + actorURI + "> .\n";
                }
                if (!timespan.isEmpty()) {
                    retTriples += "<" + creationEventURI + "> <" + Resources.hasTimespan + "> \"" + timespan + "\" .\n";
                }
                if (!creationEventLabel.isEmpty()) {
                    retTriples += "<" + creationEventURI + "> <" + Resources.rdfsLabel + "> \"" + creationEventLabel + "\" .\n";
                }
            }

            if (!actorURI.isEmpty()) {
                retTriples += "<" + actorURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.actorLabel + "> .\n";
                if (!actorName.isEmpty()) {
                    retTriples += "<" + actorURI + "> <" + Resources.rdfsLabel + "> \"" + actorName + "\" .\n";
                }
            }

            if (!datasetURI.isEmpty()) {
                retTriples += "<" + datasetURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.datasetLabel + "> .\n";
                if (!datasetName.isEmpty()) {
                    retTriples += "<" + datasetURI + "> <" + Resources.rdfsLabel + "> \"" + datasetName + "\" .\n";
                }
            }

            if (!curatorURI.isEmpty()) {
                retTriples += "<" + curatorURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.actorLabel + "> .\n";
                if (!curatorName.isEmpty()) {
                    retTriples += "<" + curatorURI + "> <" + Resources.rdfsLabel + "> \"" + curatorName + "\" .\n";
                }
                if (!contactPoint.isEmpty()) {
                    retTriples += "<" + curatorURI + "> <" + Resources.hasContactPoint + "> \"" + contactPoint + "\" .\n";
                }
            }
            if (!keeperURI.isEmpty()) {
                retTriples += "<" + keeperURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.actorLabel + "> .\n";
                if (!keeperName.isEmpty()) {
                    retTriples += "<" + keeperURI + "> <" + Resources.rdfsLabel + "> \"" + keeperName + "\".";
                }
            }

            retTriples += " } ";

            Queries.add(retTriples);

        }

        return Queries;

    }

    
    
    
    
    
    
    
       public ArrayList<String> transform_occurrence_stats_temp_csv(String fileToParse,
            String repositoryGraph) throws FileNotFoundException, IOException {

       
        BufferedReader fileReader = null;

        System.out.println(fileToParse);
        //Delimiter used in CSV file
        final String DELIMITER = ";";

        String line = "";
        String query = "";
        ArrayList<String> Queries = new ArrayList<String>();

        fileReader = new BufferedReader(new FileReader(fileToParse));
        String URIprefix = "http://www.lifewatchgreece.eu/entity";
        int count = 0;

        while ((line = fileReader.readLine()) != null) {

            if (count == 0) {
                count++;
                continue;
            }

            String[] tokens = line.split(DELIMITER);

            String occurrenceEventID = tokens[0];
            String datasetID = tokens[1];
            String physicalObjectID = tokens[2];
            String speciesName = tokens[3];
            String actorName = tokens[4];
            String timeSpan = tokens[5];
            String locality = tokens[6];
            String country = tokens[7];
            String waterArea = tokens[8];
            String habitat = tokens[9];
            String ecosystem = tokens[10];
            String equipmentType = tokens[11];
            String latitude = tokens[12];
            String longitude = tokens[13];
            String maximumDepth = tokens[14];
            String minimumDepth = tokens[15];
            String samplingProtocol = tokens[16];
            //  String geodeticDatum=tokens[17];
            String bibliographicCitation = tokens[17];
            String description = tokens[18];

                  
            String temporaryAggregate = tokens[19];
            String numberOfParts = tokens[20];
    

          
            String temporaryAggregateURI = "";
            
            if (temporaryAggregate.isEmpty()) {
                temporaryAggregateURI  = createURI(URIprefix, datasetID + physicalObjectID + speciesName, "temporaryAggregate");
            } else {
                if (temporaryAggregate.startsWith("http:")) {
                    temporaryAggregateURI = temporaryAggregate;
                } else {
                    temporaryAggregateURI= createURI(URIprefix, temporaryAggregate, "temporaryAggregate");
                }
            }
            
             if (physicalObjectID.isEmpty())
            physicalObjectID="Temporary Aggregate of the species"+speciesName;
            
            
            String occurrenceEventLabel = "Occurrence of "+speciesName+" in "+locality;

            String occurrenceEventURI = "";

            if (occurrenceEventID.isEmpty()) {
                occurrenceEventURI = createURI(URIprefix, datasetID + physicalObjectID + speciesName , "encounterEvent");
            } else {
                if (occurrenceEventID.startsWith("http:")) {
                    occurrenceEventURI = occurrenceEventID;
                } else {
                    occurrenceEventURI = createURI(URIprefix, occurrenceEventID, "encounterEvent");
                }
            }

            String datasetURI = "";
            if (datasetID.startsWith("http:")) {
                datasetURI = datasetID;
            } else {
                datasetURI = createURI(URIprefix, datasetID, "dataset");
            }

            String physicalObjectURI = "";
            if (physicalObjectID.startsWith("http:")) {
                physicalObjectURI = physicalObjectID;
            } else {
               physicalObjectURI = createURI(URIprefix, physicalObjectID, "physicalObject");
            }

            String actorURI = "";
            if (actorName.startsWith("http:")) {
                actorURI = actorName;
            } else {
                actorURI = createURI(URIprefix, actorName, "actor");
            }

            String localityURI = "";
            if (locality.startsWith("http:")) {
                localityURI = locality;
            } else {
                localityURI = createURI(URIprefix, locality, "place");
            }

            String countryURI = "";
            if (!country.isEmpty()) {
                if (country.startsWith("http:")) {
                    countryURI = country;
                } else {
                    countryURI = createURI(URIprefix, country, "country");
                }
            }

            String waterAreaURI = "";
            if (!waterArea.isEmpty()) {
                if (waterArea.startsWith("http:")) {
                    waterAreaURI = waterArea;
                } else {
                    waterAreaURI = createURI(URIprefix, waterArea, "waterArea");
                }
            }

            String habitatURI = "";
            if (!habitat.isEmpty()) {
                if (habitat.startsWith("http:")) {
                    habitatURI = habitat;
                } else {
                    habitatURI = createURI(URIprefix, habitat, "ecosystemType");
                }
            }

            String ecosystemURI = "";
            if (!ecosystem.isEmpty()) {
                if (ecosystem.startsWith("http:")) {
                    ecosystemURI = ecosystem;
                } else {
                    ecosystemURI = createURI(URIprefix, ecosystem, "ecosystem");
                }
            }

            String equipmentURI = "";
            if (!equipmentType.isEmpty()) {
                if (equipmentType.startsWith("http:")) {
                    equipmentURI = equipmentType;
                } else {
                    equipmentURI = createURI(URIprefix, equipmentType, "equipment");
                }
            }

            String speciesURI = "";
            if (speciesName.startsWith("http:")) {
                speciesURI = speciesName;
            } else {
                speciesURI = createURI(URIprefix, speciesName, "species");
            }

            String samplingProtocolURI = "";
            if (!samplingProtocol.isEmpty()) {
                if (samplingProtocol.startsWith("http:")) {
                    samplingProtocolURI = samplingProtocol;
                } else {
                    samplingProtocolURI = createURI(URIprefix, samplingProtocol, "designOrProcedure");
                }
            }

            String bibliographicCitationURI = "";
            if (!bibliographicCitation.isEmpty()) {
                if (bibliographicCitation.startsWith("http:")) {
                    bibliographicCitationURI = bibliographicCitation;
                } else {
                    bibliographicCitationURI = createURI(URIprefix, bibliographicCitation, "conceptualObject");
                }
            }

            String stationURI = "";

            if (occurrenceEventID.isEmpty()) {
                stationURI = createURI(URIprefix, "station" + occurrenceEventURI.replace(URIprefix, ""), "place");
            } else {
                stationURI = createURI(URIprefix, "station" + occurrenceEventID, "place");
            }

            String coordinates = "";
            if (!latitude.isEmpty() || !longitude.isEmpty()) {
                coordinates = latitude + "," + longitude;
            }

            String stationNotes = "";
            if (!maximumDepth.isEmpty() || !minimumDepth.isEmpty()) {
                stationNotes = "maximumDepth:" + maximumDepth + ",minimumDepth:" + minimumDepth;
            }

        
        
  

            String retTriples = "INSERT INTO <" + repositoryGraph + "> { ";

    
        if (!occurrenceEventURI.isEmpty()) {
            retTriples += "<" + occurrenceEventURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.encounterEventLabel + "> .\n";
            if (!timeSpan.isEmpty()) {
                retTriples += "<" + occurrenceEventURI + "> <" + Resources.hasTimespan + "> \"" + timeSpan + "\" .\n";
            }
            if (!stationURI.isEmpty()) {
                retTriples += "<" + occurrenceEventURI + "> <" + Resources.hasFoundAt + "> <" + stationURI + "> .\n";
            }
//            if (!localityURI.isEmpty()) {
//                retTriples += "<" + occurrenceEventURI + "> <" + Resources.hasFoundAt + "> <" + localityURI + "> .\n";
//            }
            if (!equipmentURI.isEmpty()) {
                retTriples += "<" + occurrenceEventURI + "> <" + Resources.usedObjectOfType + "> <" + equipmentURI+ "> .\n";
            }

            if (!samplingProtocolURI.isEmpty()) {
                retTriples += "<" + occurrenceEventURI + "> <" + Resources.usedSpecificTechnique + "> <" + samplingProtocolURI + "> .\n";
            }

            if (!bibliographicCitationURI.isEmpty()) {
                retTriples += "<" + occurrenceEventURI + "> <" + Resources.isReferredToBy + "> <" + bibliographicCitationURI + "> .\n";
            }

            if (!occurrenceEventLabel.isEmpty()) {
                    retTriples += "<" + occurrenceEventURI + "> <" + Resources.rdfsLabel+ "> \"" + occurrenceEventLabel + "\" .\n";
                }
            
            if(!actorURI.isEmpty()){
                retTriples+= "<"+occurrenceEventURI+"> <" + Resources.carriedOutBy + "> <" + actorURI + "> .\n";
            }
             if(!physicalObjectURI.isEmpty()){
                retTriples+= "<"+occurrenceEventURI+"> <"+Resources.hasFoundObject+"> <"+physicalObjectURI+"> .\n";
            }
            if (!datasetURI.isEmpty()) {
                retTriples += "<" + datasetURI + "> <" + Resources.refersTo + "> <" + occurrenceEventURI + "> . \n";
            }
            if (!description.isEmpty()) {
                retTriples += "<" + occurrenceEventURI + "> <" + Resources.hasNote + "> \"" + description + "\" .\n";
            }
        }
        if (!equipmentURI.isEmpty()) {
            retTriples += "<" + equipmentURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.equipmentTypeLabel + "> .\n";
            if (!equipmentType.isEmpty()) {
                retTriples += "<" + equipmentURI + "> <" + Resources.rdfsLabel + "> \"" + equipmentType + "\" .\n";
            }
        }
        
        if(!physicalObjectURI.isEmpty()){
            retTriples+= "<"+physicalObjectURI+"> <"+Resources.rdfTypeLabel+"> <"+Resources.physicalObjectLabel+"> .\n";
          
              if(!temporaryAggregateURI.isEmpty()){
                retTriples+= "<"+physicalObjectURI+"> <"+Resources.isComposedOf+"> <"+temporaryAggregateURI+"> .\n";
            }
              
            if (!physicalObjectID.isEmpty()) {
                    retTriples += "<" + physicalObjectURI + "> <" + Resources.rdfsLabel+ "> \"" + physicalObjectID + "\" .\n";
            }
        }      

           if(!temporaryAggregateURI.isEmpty()){
            retTriples+= "<"+temporaryAggregateURI+"> <"+Resources.rdfTypeLabel+"> <"+Resources.temporaryAggregateLabel+"> .\n";
       
            if(!temporaryAggregate.isEmpty()){
                retTriples+= "<"+temporaryAggregateURI+"> <"+Resources.rdfsLabel+"> \"" + temporaryAggregate + "\" .\n";
            }
            if(!numberOfParts.isEmpty()){
                retTriples+= "<"+temporaryAggregateURI+"> <"+Resources.hasNumberOfParts+"> \"" + numberOfParts + "\" .\n";
            }
            if(!speciesURI.isEmpty()){
                retTriples+= "<"+temporaryAggregateURI+"> <"+Resources.belongsTo+"> <"+speciesURI+"> .\n";
            }
        }
        

        if (!samplingProtocolURI.isEmpty()) {
            retTriples += "<" + samplingProtocolURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.procedureLabel + "> .\n";
            if (!samplingProtocol.isEmpty()) {
                retTriples += "<" + samplingProtocolURI + "> <" + Resources.rdfsLabel + "> \"" + samplingProtocol + "\" .\n";
            }
        }

        if (!bibliographicCitationURI.isEmpty()) {
            retTriples += "<" + bibliographicCitationURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.conceptualObjectLabel + "> .\n";
            if (!bibliographicCitation.isEmpty()) {
                retTriples += "<" + bibliographicCitationURI + "> <" + Resources.rdfsLabel + "> \"" + bibliographicCitation + "\" .\n";
            }
        }

        if (!stationURI.isEmpty()) {
            retTriples += "<" + stationURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.placeLabel + "> .\n";

//            if (!latitude.isEmpty()&&!longitude.isEmpty()) {
//                retTriples += "<" + stationURI + "> <" + Resources.isIdentifiedBy + "> \"" + latitude + "," + longitude + "\" .\n";
//            }
            if (!latitude.isEmpty() && !longitude.isEmpty()) {
                retTriples += "<" + stationURI + "> <" + Resources.isIdentifiedBy + "> \"" + coordinates + "\" .\n";
            }

            if (!stationNotes.isEmpty()) {
                retTriples += "<" + stationURI + "> <" + Resources.hasNote + "> \"" + stationNotes + "\" .\n";
            }

            if (!localityURI.isEmpty()) {
                retTriples += "<" + stationURI + "> <" + Resources.fallsWithin + "> <" + localityURI + "> .\n";
            }

//            if (!countryURI.isEmpty()) {
//                retTriples += "<" + localityURI + "> <" + Resources.fallsWithin + "> <" + countryURI + "> .\n";
//            }
//            if (!waterAreaURI.isEmpty()) {
//                retTriples += "<" + localityURI + "> <" + Resources.fallsWithin + "> <" + waterAreaURI + "> .\n";
//            }
//            if (!habitatURI.isEmpty()) {
//                retTriples += "<" + localityURI + "> <" + Resources.hasType + "> <" + habitatURI + "> .\n";
//            }
        }

        if (!localityURI.isEmpty()) {
            retTriples += "<" + localityURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.ecosystemEnvironmentLabel + "> .\n";
            if (!locality.isEmpty()) {
                retTriples += "<" + localityURI + "> <" + Resources.rdfsLabel + "> \"" + locality + "\" .\n";
            }
            if (!ecosystemURI.isEmpty()) {
                retTriples += "<" + localityURI + "> <" + Resources.fallsWithin + "> <" + ecosystemURI + "> .\n";
            }
            if (!countryURI.isEmpty()) {
                retTriples += "<" + localityURI + "> <" + Resources.fallsWithin + "> <" + countryURI + "> .\n";
            }
            if (!waterAreaURI.isEmpty()) {
                retTriples += "<" + localityURI + "> <" + Resources.fallsWithin + "> <" + waterAreaURI + "> .\n";
            }
            if (!habitatURI.isEmpty()) {
                retTriples += "<" + localityURI + "> <" + Resources.hasType + "> <" + habitatURI + "> .\n";
            }
        }
        if (!habitatURI.isEmpty()) {
            retTriples += "<" + habitatURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.ecosystemTypeLabel + "> .\n";
            if (!habitat.isEmpty()) {
                retTriples += "<" + habitatURI + "> <" + Resources.rdfsLabel + "> \"" + habitat + "\" .\n";
            }
        }
        if (!ecosystemURI.isEmpty()) {
            retTriples += "<" + ecosystemURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.ecosystemLabel + "> .\n";
            if (!ecosystem.isEmpty()) {
                retTriples += "<" + ecosystemURI + "> <" + Resources.rdfsLabel + "> \"" + ecosystem + "\" .\n";
            }
        }
       if (!ecosystemURI.isEmpty()) {
            retTriples += "<" + actorURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.personLabel + "> .\n";
            retTriples += "<" + actorURI+ "> <" + Resources.rdfsLabel + "> \"" +actorName + "\" .\n";
        }
       
        if (!speciesURI.isEmpty()) {
            retTriples += "<" + speciesURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.speciesLabel + "> .\n";
            if (!speciesName.isEmpty()) {
                retTriples += "<" + speciesURI + "> <" + Resources.rdfsLabel + "> \"" + speciesName + "\" .\n";
            }
        }
        if (!countryURI.isEmpty()) {
            retTriples += "<" + countryURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.countryLabel + "> .\n";
            if (!country.isEmpty()) {
                retTriples += "<" + countryURI + "> <" + Resources.rdfsLabel + "> \"" + country+ "\" .\n";
            }
        }
        if (!waterAreaURI.isEmpty()) {
            retTriples += "<" + waterAreaURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.waterAreaLabel + "> .\n";
            if (!waterArea.isEmpty()) {
                retTriples += "<" + waterAreaURI + "> <" + Resources.rdfsLabel + "> \"" + waterArea + "\" .\n";
            }
        }
        if (!datasetURI.isEmpty()) {
            retTriples += "<" + datasetURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.datasetLabel + "> .\n";
            if (!datasetID.isEmpty()) {
                retTriples += "<" + datasetURI + "> <" + Resources.rdfsLabel + "> \"" + datasetID + "\" .\n";
            }
        }
     
            retTriples += " } ";

            Queries.add(retTriples);

        }

        return Queries;

    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public ArrayList<String> transform_occurrence_stats_temp_csv(String fileToParse,
            String repositoryGraph, String datasetID) throws FileNotFoundException, IOException {

          BufferedReader fileReader = null;

        System.out.println(fileToParse);
        //Delimiter used in CSV file
        final String DELIMITER = ";";

        String line = "";
        String query = "";
        ArrayList<String> Queries = new ArrayList<String>();

        fileReader = new BufferedReader(new FileReader(fileToParse));
        String URIprefix = "http://www.lifewatchgreece.eu/entity";
        int count = 0;

        while ((line = fileReader.readLine()) != null) {

            if (count == 0) {
                count++;
                continue;
            }

            String[] tokens = line.split(DELIMITER);

            String occurrenceEventID = tokens[0];
          //  String datasetID = tokens[1];
            String physicalObjectID = tokens[2];
            String speciesName = tokens[3];
            String actorName = tokens[4];
            String timeSpan = tokens[5];
            String locality = tokens[6];
            String country = tokens[7];
            String waterArea = tokens[8];
            String habitat = tokens[9];
            String ecosystem = tokens[10];
            String equipmentType = tokens[11];
            String latitude = tokens[12];
            String longitude = tokens[13];
            String maximumDepth = tokens[14];
            String minimumDepth = tokens[15];
            String samplingProtocol = tokens[16];
            //  String geodeticDatum=tokens[17];
            String bibliographicCitation = tokens[17];
            String description = tokens[18];

                  
            String temporaryAggregate = tokens[19];
            String numberOfParts = tokens[20];
    
            String occurrenceEventLabel = "Occurrence of "+speciesName+" in "+locality;
          
            String temporaryAggregateURI = "";
            
            if (temporaryAggregate.isEmpty()) {
                temporaryAggregateURI  = createURI(URIprefix, datasetID + physicalObjectID + speciesName, "temporaryAggregate");
            } else {
                if (temporaryAggregate.startsWith("http:")) {
                    temporaryAggregateURI = temporaryAggregate;
                } else {
                    temporaryAggregateURI= createURI(URIprefix, temporaryAggregate, "temporaryAggregate");
                }
            }
            
            
            if (physicalObjectID.isEmpty())
            physicalObjectID="Temporary Aggregate of the species"+speciesName;
            
            String occurrenceEventURI = "";

            if (occurrenceEventID.isEmpty()) {
                occurrenceEventURI = createURI(URIprefix, datasetID + physicalObjectID + speciesName , "encounterEvent");
            } else {
                if (occurrenceEventID.startsWith("http:")) {
                    occurrenceEventURI = occurrenceEventID;
                } else {
                    occurrenceEventURI = createURI(URIprefix, occurrenceEventID, "encounterEvent");
                }
            }

            String datasetURI = "";
            if (datasetID.startsWith("http:")) {
                datasetURI = datasetID;
            } else {
                datasetURI = createURI(URIprefix, datasetID, "dataset");
            }

            String physicalObjectURI = "";
            if (physicalObjectID.startsWith("http:")) {
                physicalObjectURI = physicalObjectID;
            } else {
               physicalObjectURI = createURI(URIprefix, physicalObjectID, "physicalObject");
            }

            String actorURI = "";
            if (actorName.startsWith("http:")) {
                actorURI = actorName;
            } else {
                actorURI = createURI(URIprefix, actorName, "actor");
            }

            String localityURI = "";
            if (locality.startsWith("http:")) {
                localityURI = locality;
            } else {
                localityURI = createURI(URIprefix, locality, "place");
            }

            String countryURI = "";
            if (!country.isEmpty()) {
                if (country.startsWith("http:")) {
                    countryURI = country;
                } else {
                    countryURI = createURI(URIprefix, country, "country");
                }
            }

            String waterAreaURI = "";
            if (!waterArea.isEmpty()) {
                if (waterArea.startsWith("http:")) {
                    waterAreaURI = waterArea;
                } else {
                    waterAreaURI = createURI(URIprefix, waterArea, "waterArea");
                }
            }

            String habitatURI = "";
            if (!habitat.isEmpty()) {
                if (habitat.startsWith("http:")) {
                    habitatURI = habitat;
                } else {
                    habitatURI = createURI(URIprefix, habitat, "ecosystemType");
                }
            }

            String ecosystemURI = "";
            if (!ecosystem.isEmpty()) {
                if (ecosystem.startsWith("http:")) {
                    ecosystemURI = ecosystem;
                } else {
                    ecosystemURI = createURI(URIprefix, ecosystem, "ecosystem");
                }
            }

            String equipmentURI = "";
            if (!equipmentType.isEmpty()) {
                if (equipmentType.startsWith("http:")) {
                    equipmentURI = equipmentType;
                } else {
                    equipmentURI = createURI(URIprefix, equipmentType, "equipment");
                }
            }

            String speciesURI = "";
            if (speciesName.startsWith("http:")) {
                speciesURI = speciesName;
            } else {
                speciesURI = createURI(URIprefix, speciesName, "species");
            }

            String samplingProtocolURI = "";
            if (!samplingProtocol.isEmpty()) {
                if (samplingProtocol.startsWith("http:")) {
                    samplingProtocolURI = samplingProtocol;
                } else {
                    samplingProtocolURI = createURI(URIprefix, samplingProtocol, "designOrProcedure");
                }
            }

            String bibliographicCitationURI = "";
            if (!bibliographicCitation.isEmpty()) {
                if (bibliographicCitation.startsWith("http:")) {
                    bibliographicCitationURI = bibliographicCitation;
                } else {
                    bibliographicCitationURI = createURI(URIprefix, bibliographicCitation, "conceptualObject");
                }
            }

            String stationURI = "";

            if (occurrenceEventID.isEmpty()) {
                stationURI = createURI(URIprefix, "station" + occurrenceEventURI.replace(URIprefix, ""), "place");
            } else {
                stationURI = createURI(URIprefix, "station" + occurrenceEventID, "place");
            }

            String coordinates = "";
            if (!latitude.isEmpty() || !longitude.isEmpty()) {
                coordinates = latitude + "," + longitude;
            }

            String stationNotes = "";
            if (!maximumDepth.isEmpty() || !minimumDepth.isEmpty()) {
                stationNotes = "maximumDepth:" + maximumDepth + ",minimumDepth:" + minimumDepth;
            }

        
        
  

            String retTriples = "INSERT INTO <" + repositoryGraph + "> { ";

    
        if (!occurrenceEventURI.isEmpty()) {
            retTriples += "<" + occurrenceEventURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.encounterEventLabel + "> .\n";
            if (!timeSpan.isEmpty()) {
                retTriples += "<" + occurrenceEventURI + "> <" + Resources.hasTimespan + "> \"" + timeSpan + "\" .\n";
            }
            if (!stationURI.isEmpty()) {
                retTriples += "<" + occurrenceEventURI + "> <" + Resources.hasFoundAt + "> <" + stationURI + "> .\n";
            }
//            if (!localityURI.isEmpty()) {
//                retTriples += "<" + occurrenceEventURI + "> <" + Resources.hasFoundAt + "> <" + localityURI + "> .\n";
//            }
            if (!equipmentURI.isEmpty()) {
                retTriples += "<" + occurrenceEventURI + "> <" + Resources.usedObjectOfType + "> <" + equipmentURI+ "> .\n";
            }

            if (!samplingProtocolURI.isEmpty()) {
                retTriples += "<" + occurrenceEventURI + "> <" + Resources.usedSpecificTechnique + "> <" + samplingProtocolURI + "> .\n";
            }

            if (!bibliographicCitationURI.isEmpty()) {
                retTriples += "<" + occurrenceEventURI + "> <" + Resources.isReferredToBy + "> <" + bibliographicCitationURI + "> .\n";
            }

            if (!occurrenceEventLabel.isEmpty()) {
                    retTriples += "<" + occurrenceEventURI + "> <" + Resources.rdfsLabel+ "> \"" + occurrenceEventLabel + "\" .\n";
                }
            
            if(!actorURI.isEmpty()){
                retTriples+= "<"+occurrenceEventURI+"> <" + Resources.carriedOutBy + "> <" + actorURI + "> .\n";
            }
             if(!physicalObjectURI.isEmpty()){
                retTriples+= "<"+occurrenceEventURI+"> <"+Resources.hasFoundObject+"> <"+physicalObjectURI+"> .\n";
            }
            if (!datasetURI.isEmpty()) {
                retTriples += "<" + datasetURI + "> <" + Resources.refersTo + "> <" + occurrenceEventURI + "> . \n";
            }
            if (!description.isEmpty()) {
                retTriples += "<" + occurrenceEventURI + "> <" + Resources.hasNote + "> \"" + description + "\" .\n";
            }
        }
        if (!equipmentURI.isEmpty()) {
            retTriples += "<" + equipmentURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.equipmentTypeLabel + "> .\n";
            if (!equipmentType.isEmpty()) {
                retTriples += "<" + equipmentURI + "> <" + Resources.rdfsLabel + "> \"" + equipmentType + "\" .\n";
            }
        }
        
        if(!physicalObjectURI.isEmpty()){
            retTriples+= "<"+physicalObjectURI+"> <"+Resources.rdfTypeLabel+"> <"+Resources.physicalObjectLabel+"> .\n";
          
              if(!temporaryAggregateURI.isEmpty()){
                retTriples+= "<"+physicalObjectURI+"> <"+Resources.isComposedOf+"> <"+temporaryAggregateURI+"> .\n";
            }
            if (!physicalObjectID.isEmpty()) {
                    retTriples += "<" + physicalObjectURI + "> <" + Resources.rdfsLabel+ "> \"" + physicalObjectID + "\" .\n";
            }
        }      

           if(!temporaryAggregateURI.isEmpty()){
            retTriples+= "<"+temporaryAggregateURI+"> <"+Resources.rdfTypeLabel+"> <"+Resources.temporaryAggregateLabel+"> .\n";
       
            if(!temporaryAggregate.isEmpty()){
                retTriples+= "<"+temporaryAggregateURI+"> <"+Resources.rdfsLabel+"> \"" + temporaryAggregate + "\" .\n";
            }
            if(!numberOfParts.isEmpty()){
                retTriples+= "<"+temporaryAggregateURI+"> <"+Resources.hasNumberOfParts+"> \"" + numberOfParts + "\" .\n";
            }
            if(!speciesURI.isEmpty()){
                retTriples+= "<"+temporaryAggregateURI+"> <"+Resources.belongsTo+"> <"+speciesURI+"> .\n";
            }
        }
        

        if (!samplingProtocolURI.isEmpty()) {
            retTriples += "<" + samplingProtocolURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.procedureLabel + "> .\n";
            if (!samplingProtocol.isEmpty()) {
                retTriples += "<" + samplingProtocolURI + "> <" + Resources.rdfsLabel + "> \"" + samplingProtocol + "\" .\n";
            }
        }

        if (!bibliographicCitationURI.isEmpty()) {
            retTriples += "<" + bibliographicCitationURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.conceptualObjectLabel + "> .\n";
            if (!bibliographicCitation.isEmpty()) {
                retTriples += "<" + bibliographicCitationURI + "> <" + Resources.rdfsLabel+ "> \"" + bibliographicCitation + "\" .\n";
            }
        }

        if (!stationURI.isEmpty()) {
            retTriples += "<" + stationURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.placeLabel + "> .\n";

//            if (!latitude.isEmpty()&&!longitude.isEmpty()) {
//                retTriples += "<" + stationURI + "> <" + Resources.isIdentifiedBy + "> \"" + latitude + "," + longitude + "\" .\n";
//            }
            if (!latitude.isEmpty() && !longitude.isEmpty()) {
                retTriples += "<" + stationURI + "> <" + Resources.isIdentifiedBy + "> \"" + coordinates + "\" .\n";
            }

            if (!stationNotes.isEmpty()) {
                retTriples += "<" + stationURI + "> <" + Resources.hasNote + "> \"" + stationNotes + "\" .\n";
            }

            if (!localityURI.isEmpty()) {
                retTriples += "<" + stationURI + "> <" + Resources.fallsWithin + "> <" + localityURI + "> .\n";
            }

//            if (!countryURI.isEmpty()) {
//                retTriples += "<" + localityURI + "> <" + Resources.fallsWithin + "> <" + countryURI + "> .\n";
//            }
//            if (!waterAreaURI.isEmpty()) {
//                retTriples += "<" + localityURI + "> <" + Resources.fallsWithin + "> <" + waterAreaURI + "> .\n";
//            }
//            if (!habitatURI.isEmpty()) {
//                retTriples += "<" + localityURI + "> <" + Resources.hasType + "> <" + habitatURI + "> .\n";
//            }
        }

        if (!localityURI.isEmpty()) {
            retTriples += "<" + localityURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.ecosystemEnvironmentLabel + "> .\n";
            if (!locality.isEmpty()) {
                retTriples += "<" + localityURI + "> <" + Resources.rdfsLabel + "> \"" + locality + "\" .\n";
            }
            if (!ecosystemURI.isEmpty()) {
                retTriples += "<" + localityURI + "> <" + Resources.fallsWithin + "> <" + ecosystemURI + "> .\n";
            }
            if (!countryURI.isEmpty()) {
                retTriples += "<" + localityURI + "> <" + Resources.fallsWithin + "> <" + countryURI + "> .\n";
            }
            if (!waterAreaURI.isEmpty()) {
                retTriples += "<" + localityURI + "> <" + Resources.fallsWithin + "> <" + waterAreaURI + "> .\n";
            }
            if (!habitatURI.isEmpty()) {
                retTriples += "<" + localityURI + "> <" + Resources.hasType + "> <" + habitatURI + "> .\n";
            }
        }
        if (!habitatURI.isEmpty()) {
            retTriples += "<" + habitatURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.ecosystemTypeLabel + "> .\n";
            if (!habitat.isEmpty()) {
                retTriples += "<" + habitatURI + "> <" + Resources.rdfsLabel + "> \"" + habitat + "\" .\n";
            }
        }
        if (!ecosystemURI.isEmpty()) {
            retTriples += "<" + ecosystemURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.ecosystemLabel + "> .\n";
            if (!ecosystem.isEmpty()) {
                retTriples += "<" + ecosystemURI + "> <" + Resources.rdfsLabel + "> \"" + ecosystem + "\" .\n";
            }
        }
       if (!ecosystemURI.isEmpty()) {
            retTriples += "<" + actorURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.personLabel + "> .\n";
            retTriples += "<" + actorURI+ "> <" + Resources.rdfsLabel + "> \"" +actorName + "\" .\n";
        }
       
        if (!speciesURI.isEmpty()) {
            retTriples += "<" + speciesURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.speciesLabel + "> .\n";
            if (!speciesName.isEmpty()) {
                retTriples += "<" + speciesURI + "> <" + Resources.rdfsLabel + "> \"" + speciesName + "\" .\n";
            }
        }
        if (!countryURI.isEmpty()) {
            retTriples += "<" + countryURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.countryLabel + "> .\n";
            if (!country.isEmpty()) {
                retTriples += "<" + countryURI + "> <" + Resources.rdfsLabel + "> \"" + country+ "\" .\n";
            }
        }
        if (!waterAreaURI.isEmpty()) {
            retTriples += "<" + waterAreaURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.waterAreaLabel + "> .\n";
            if (!waterArea.isEmpty()) {
                retTriples += "<" + waterAreaURI + "> <" + Resources.rdfsLabel + "> \"" + waterArea + "\" .\n";
            }
        }
        if (!datasetURI.isEmpty()) {
            retTriples += "<" + datasetURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.datasetLabel + "> .\n";
            if (!datasetID.isEmpty()) {
                retTriples += "<" + datasetURI + "> <" + Resources.rdfsLabel + "> \"" + datasetID + "\" .\n";
            }
        }
     
            retTriples += " } ";

            Queries.add(retTriples);

        }

        return Queries;
    }

    public ArrayList<String> transform_occurrence_stats_abundance_csv(String fileToParse, String repositoryGraph) throws FileNotFoundException, IOException {

        //String fileToParse = "C:/uploads/TLOflat/identification.csv";
        BufferedReader fileReader = null;

        System.out.println(fileToParse);
        //Delimiter used in CSV file
        final String DELIMITER = ",";

        String line = "";
        String query = "";
        ArrayList<String> Queries = new ArrayList<String>();

        fileReader = new BufferedReader(new FileReader(fileToParse));
        String URIprefix = "http://lifewatch.gr/entity";
        int count = 0;

        while ((line = fileReader.readLine()) != null) {

            if (count == 0) {
                count++;
                continue;
            }

            String[] tokens = line.split(DELIMITER);

            String temporaryAggregate = tokens[0];
            String dimensionType = tokens[1];
            String dimensionValue = tokens[2];
            String dimensionUnit = tokens[3];
            String datasetID = tokens[4];
            String speciesName = tokens[5];

            String temporaryAggregateURI = createURI(URIprefix, temporaryAggregate, "temporaryAggregate");
            String dimensionTypeURI = createURI(URIprefix, dimensionType, "dimension");
            String dimensionURI = createURI(URIprefix, temporaryAggregate + dimensionType, "dimension");
            String datasetURI = createURI(URIprefix, datasetID, "dataset");
            String speciesURI = createURI(URIprefix, speciesName, "species");

            String retTriples = "INSERT INTO <" + repositoryGraph + "> { ";

            if (!temporaryAggregateURI.isEmpty()) {
                retTriples += "<" + temporaryAggregateURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.temporaryAggregateLabel + "> .\n";

                if (!dimensionURI.isEmpty()) {
                    retTriples += "<" + temporaryAggregateURI + "> <" + Resources.hasDimension + "> <" + dimensionURI + "> .\n";
                }
                if (!datasetURI.isEmpty()) {
                    retTriples += "<" + datasetURI + "> <" + Resources.refersTo + "> <" + temporaryAggregateURI + "> . \n";
                }

                if (!speciesURI.isEmpty()) {
                    retTriples += "<" + temporaryAggregateURI + "> <" + Resources.belongsTo + "> <" + speciesURI + "> .\n";

                }
            }
            if (!dimensionURI.isEmpty()) {
                retTriples += "<" + dimensionURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.dimensionLabel + "> .\n";
                if (!dimensionTypeURI.isEmpty()) {
                    retTriples += "<" + dimensionURI + "> <" + Resources.hasType + "> <" + dimensionTypeURI + "> .\n";
                    if (!dimensionType.isEmpty()) {
                        retTriples += "<" + dimensionTypeURI + "> <" + Resources.rdfsLabel + "> \"" + dimensionType + "\" .\n";
                    }
                    if (!dimensionUnit.isEmpty()) {
                        retTriples += "<" + dimensionURI + "> <" + Resources.hasUnit + "> \"" + dimensionUnit + "\" .\n";

                    }
                    if (!dimensionValue.isEmpty()) {
                        retTriples += "<" + dimensionURI + "> <" + Resources.hasValue + "> \"" + dimensionValue + "\" .\n";

                    }

                }
            }
            if (!datasetURI.isEmpty()) {
                retTriples += "<" + datasetURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.datasetLabel + "> .\n";
                if (!datasetID.isEmpty()) {
                    retTriples += "<" + datasetURI + "> <" + Resources.rdfsLabel + "> \"" + datasetID + "\" .\n";
                }
            }
            if (!speciesURI.isEmpty()) {
                retTriples += "<" + speciesURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.speciesLabel + "> .\n";
                if (!speciesName.isEmpty()) {
                    retTriples += "<" + speciesURI + "> <" + Resources.rdfsLabel + "> \"" + speciesName + "\" .\n";
                }
            }

            retTriples += " } ";

            Queries.add(retTriples);

        }

        return Queries;

    }

    public ArrayList<String> transform_occurrence_stats_abundance_csv(String fileToParse, String repositoryGraph, String datasetID) throws FileNotFoundException, IOException {

        //String fileToParse = "C:/uploads/TLOflat/identification.csv";
        BufferedReader fileReader = null;

        System.out.println(fileToParse);
        //Delimiter used in CSV file
        final String DELIMITER = ",";

        String line = "";
        String query = "";
        ArrayList<String> Queries = new ArrayList<String>();

        fileReader = new BufferedReader(new FileReader(fileToParse));
        String URIprefix = "http://lifewatch.gr/entity";
        int count = 0;

        while ((line = fileReader.readLine()) != null) {

            if (count == 0) {
                count++;
                continue;
            }

            String[] tokens = line.split(DELIMITER);

            String temporaryAggregate = tokens[0];
            String dimensionType = tokens[1];
            String dimensionValue = tokens[2];
            String dimensionUnit = tokens[3];
            //  String datasetID=tokens[4];
            String speciesName = tokens[5];

            String temporaryAggregateURI = createURI(URIprefix, temporaryAggregate, "temporaryAggregate");
            String dimensionTypeURI = createURI(URIprefix, dimensionType, "dimension");
            String dimensionURI = createURI(URIprefix, temporaryAggregate + dimensionType, "dimension");
            String datasetURI = createURI(URIprefix, datasetID, "dataset");
            String speciesURI = createURI(URIprefix, speciesName, "species");

            String retTriples = "INSERT INTO <" + repositoryGraph + "> { ";

            if (!temporaryAggregateURI.isEmpty()) {
                retTriples += "<" + temporaryAggregateURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.temporaryAggregateLabel + "> .\n";

                if (!dimensionURI.isEmpty()) {
                    retTriples += "<" + temporaryAggregateURI + "> <" + Resources.hasDimension + "> <" + dimensionURI + "> .\n";
                }
                if (!datasetURI.isEmpty()) {
                    retTriples += "<" + datasetURI + "> <" + Resources.refersTo + "> <" + temporaryAggregateURI + "> . \n";
                }

                if (!speciesURI.isEmpty()) {
                    retTriples += "<" + temporaryAggregateURI + "> <" + Resources.belongsTo + "> <" + speciesURI + "> .\n";

                }
            }
            if (!dimensionURI.isEmpty()) {
                retTriples += "<" + dimensionURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.dimensionLabel + "> .\n";
                if (!dimensionTypeURI.isEmpty()) {
                    retTriples += "<" + dimensionURI + "> <" + Resources.hasType + "> <" + dimensionTypeURI + "> .\n";
                    if (!dimensionType.isEmpty()) {
                        retTriples += "<" + dimensionTypeURI + "> <" + Resources.rdfsLabel + "> \"" + dimensionType + "\" .\n";
                    }
                    if (!dimensionUnit.isEmpty()) {
                        retTriples += "<" + dimensionURI + "> <" + Resources.hasUnit + "> \"" + dimensionUnit + "\" .\n";

                    }
                    if (!dimensionValue.isEmpty()) {
                        retTriples += "<" + dimensionURI + "> <" + Resources.hasValue + "> \"" + dimensionValue + "\" .\n";

                    }

                }
            }
            if (!datasetURI.isEmpty()) {
                retTriples += "<" + datasetURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.datasetLabel + "> .\n";
                if (!datasetID.isEmpty()) {
                    retTriples += "<" + datasetURI + "> <" + Resources.rdfsLabel + "> \"" + datasetID + "\" .\n";
                }
            }
            if (!speciesURI.isEmpty()) {
                retTriples += "<" + speciesURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.speciesLabel + "> .\n";
                if (!speciesName.isEmpty()) {
                    retTriples += "<" + speciesURI + "> <" + Resources.rdfsLabel + "> \"" + speciesName + "\" .\n";
                }
            }

            retTriples += " } ";

            Queries.add(retTriples);

        }

        return Queries;

    }

     public ArrayList<String> transform_statistics_csv(String fileToParse, String repositoryGraph) throws FileNotFoundException, IOException {

        //String fileToParse = "C:/uploads/TLOflat/identification.csv";
        BufferedReader fileReader = null;

        System.out.println(fileToParse);
        //Delimiter used in CSV file
        final String DELIMITER = ";";

        String line = "";
        String query = "";
        ArrayList<String> Queries = new ArrayList<String>();

        fileReader = new BufferedReader(new FileReader(fileToParse));
        String URIprefix = "http://www.lifewatchgreece.eu/entity";
        int count = 0;

        while ((line = fileReader.readLine()) != null) {

            if (count == 0) {
                count++;
                continue;
            }

            String[] tokens = line.split(DELIMITER);

            String speciesName = tokens[0];
            String specimenName = tokens[1];
            String dataEvaluationID = tokens[2];
            String actorName = tokens[3];
            String timeSpan = tokens[4];
            String dimensionType = tokens[5];
            String dimensionValue = tokens[6];
            String dimensionUnit = tokens[7];
            String publication = tokens[8];
            String description = tokens[9];
            String datasetID = tokens[10];

            String dataEvaluationURI = "";
            if (dataEvaluationID.isEmpty()) {
                dataEvaluationURI = createURI(URIprefix, datasetID + speciesName + specimenName + dimensionType.replace("http://", "") + "DataEvaluation", "DataEvaluation");
            } else {

                if (dataEvaluationID.startsWith("http:")) {
                    dataEvaluationURI = dataEvaluationID;
                } else {
                    dataEvaluationURI = createURI(URIprefix, dataEvaluationID, "DataEvaluation");
                }
            }

            
            String dataEvaluationLabel = "Data Evaluation of "+dimensionType+" of "+speciesName;
            
            
            String speciesURI = "";
            if (speciesName.startsWith("http:")) {
                speciesURI = speciesName;
            } else {
                speciesURI = createURI(URIprefix, speciesName, "species");
            }

            String dimensionTypeURI = "";

            if (dimensionType.startsWith("http:")) {
                dimensionTypeURI = dimensionType;
            } else {
                dimensionTypeURI = createURI(URIprefix, dimensionType, "dimension");
            }

            String dimensionURI = "";

            if (dataEvaluationID.isEmpty()) {
                dimensionURI = createURI(URIprefix, dataEvaluationURI.replace(URIprefix, "") + dimensionType, "dimension");
            } else {
                dimensionURI = createURI(URIprefix, dataEvaluationID + dimensionType, "dimension");
            }

            String actorURI = "";

            if (!actorName.isEmpty()) {
                actorURI = createURI(URIprefix, actorName, "actor");
            }

            String specimenURI = createURI(URIprefix, specimenName, "specimen");

            String publicationURI = "";

            if (!publication.isEmpty()) {
                actorURI = createURI(URIprefix, publication, "publication");
            }

            String datasetURI = createURI(URIprefix, datasetID, "dataset");

            String retTriples = "INSERT INTO <" + repositoryGraph + "> { ";

            if (!dataEvaluationURI.isEmpty()) {
                retTriples += "<" + dataEvaluationURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.dataEvaluationLabel + "> .\n";
                if (!timeSpan.isEmpty()) {
                    retTriples += "<" + dataEvaluationURI + "> <" + Resources.hasTimespan + "> \"" + timeSpan + "\" .\n";
                }
                if (!specimenURI.isEmpty()) {
                    retTriples += "<" + dataEvaluationURI + "> <" + Resources.describes + "> <" + specimenURI + "> .\n";
                }
                if (!dimensionURI.isEmpty()) {
                    retTriples += "<" + dataEvaluationURI + "> <" + Resources.assignedDimension + "> <" + dimensionURI + "> .\n";
                }
                if (!publicationURI.isEmpty()) {
                    retTriples += "<" + publicationURI + "> <" + Resources.refersTo + "> <" + dataEvaluationURI + "> .\n";
                }

                if (!actorURI.isEmpty()) {
                    retTriples += "<" + dataEvaluationURI + "> <" + Resources.carriedOutBy + "> <" + actorURI + "> .\n";
                }
                if (!description.isEmpty()) {
                    retTriples += "<" + dataEvaluationURI + "> <" + Resources.hasNote + "> \"" + description + "\" .\n";
                }
                if (!datasetURI.isEmpty()) {
                    retTriples += "<" + datasetURI + "> <" + Resources.refersTo + "> <" + dataEvaluationURI + "> . \n";
                }
                if(!dataEvaluationLabel.isEmpty()){
                retTriples+= "<"+dataEvaluationURI+"> <"+Resources.rdfsLabel+"> \""+dataEvaluationLabel+"\" .\n";
            }
            }

            if (!dimensionURI.isEmpty()) {
                retTriples += "<" + dimensionURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.dimensionLabel + "> .\n";
                if (!dimensionTypeURI.isEmpty()) {
                    retTriples += "<" + dimensionURI + "> <" + Resources.hasType + "> <" + dimensionTypeURI + "> .\n";
                    if (!dimensionType.isEmpty()) {
                        retTriples += "<" + dimensionTypeURI + "> <" + Resources.rdfsLabel + "> \"" + dimensionType + "\" .\n";
                    }
                    if (!dimensionUnit.isEmpty()) {
                        retTriples += "<" + dimensionURI + "> <" + Resources.hasUnit + "> \"" + dimensionUnit + "\" .\n";

                    }
                    if (!dimensionValue.isEmpty()) {
                        retTriples += "<" + dimensionURI + "> <" + Resources.hasValue + "> \"" + dimensionValue + "\" .\n";

                    }

                }
            }

            if (!specimenURI.isEmpty()) {
                retTriples += "<" + specimenURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.specimenLabel + "> .\n";
                if (!specimenName.isEmpty()) {
                    retTriples += "<" + specimenURI + "> <" + Resources.rdfsLabel + "> \"" + specimenName + "\" .\n";
                }
                if (!speciesURI.isEmpty()) {
                    retTriples += "<" + specimenURI + "> <" + Resources.belongsTo + "> <" + speciesURI + "> .\n";
                }
            }
            if (!speciesURI.isEmpty()) {
                retTriples += "<" + speciesURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.speciesLabel + "> .\n";
                if (!speciesName.isEmpty()) {
                    retTriples += "<" + speciesURI + "> <" + Resources.rdfsLabel + "> \"" + speciesName + "\" .\n";
                }

            }

            if (!actorURI.isEmpty()) {
                retTriples += "<" + actorURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.personLabel + "> .\n";
                if (!actorName.isEmpty()) {
                    retTriples += "<" + actorURI + "> <" + Resources.rdfsLabel + "> \"" + actorName + "\" .\n";
                }
            }

            if (!datasetURI.isEmpty()) {
                retTriples += "<" + datasetURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.datasetLabel + "> .\n";
                if (!datasetID.isEmpty()) {
                    retTriples += "<" + datasetURI + "> <" + Resources.rdfsLabel + "> \"" + datasetID + "\" .\n";
                }
            }

            if (!publicationURI.isEmpty()) {
                retTriples += "<" + publicationURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.publicationLabel + "> .\n";
                if (!publication.isEmpty()) {
                    retTriples += "<" + publicationURI + "> <" + Resources.rdfsLabel + "> \"" + publication + "\" .\n";
                }
            }

            retTriples += " } ";

            Queries.add(retTriples);

        }
        return Queries;

    }

    public ArrayList<String> transform_statistics_csv(String fileToParse,String repositoryGraph , String datasetID) throws FileNotFoundException, IOException {

        //String fileToParse = "C:/uploads/TLOflat/identification.csv";
        BufferedReader fileReader = null;

        System.out.println(fileToParse);
        //Delimiter used in CSV file
        final String DELIMITER = ";";

        String line = "";
        String query = "";
        ArrayList<String> Queries = new ArrayList<String>();

        fileReader = new BufferedReader(new FileReader(fileToParse));
        String URIprefix = "http://www.lifewatchgreece.eu/entity";
        int count = 0;

        while ((line = fileReader.readLine()) != null) {

            if (count == 0) {
                count++;
                continue;
            }

            String[] tokens = line.split(DELIMITER);

            String speciesName = tokens[0];
            String specimenName = tokens[1];
            String dataEvaluationID = tokens[2];
            String actorName = tokens[3];
            String timeSpan = tokens[4];
            String dimensionType = tokens[5];
            String dimensionValue = tokens[6];
            String dimensionUnit = tokens[7];
            String publication = tokens[8];
            String description = tokens[9];
           // String datasetID = tokens[10];

            String dataEvaluationURI = "";
            if (dataEvaluationID.isEmpty()) {
                dataEvaluationURI = createURI(URIprefix, datasetID + speciesName + specimenName + dimensionType.replace("http://", "") + "DataEvaluation", "DataEvaluation");
            } else {

                if (dataEvaluationID.startsWith("http:")) {
                    dataEvaluationURI = dataEvaluationID;
                } else {
                    dataEvaluationURI = createURI(URIprefix, dataEvaluationID, "DataEvaluation");
                }
            }

             String dataEvaluationLabel = "Data Evaluation of "+dimensionType+" of "+speciesName;
            
            String speciesURI = "";
            if (speciesName.startsWith("http:")) {
                speciesURI = speciesName;
            } else {
                speciesURI = createURI(URIprefix, speciesName, "species");
            }

            String dimensionTypeURI = "";

            if (dimensionType.startsWith("http:")) {
                dimensionTypeURI = dimensionType;
            } else {
                dimensionTypeURI = createURI(URIprefix, dimensionType, "dimension");
            }

            String dimensionURI = "";

            if (dataEvaluationID.isEmpty()) {
                dimensionURI = createURI(URIprefix, dataEvaluationURI.replace(URIprefix, "") + dimensionType, "dimension");
            } else {
                dimensionURI = createURI(URIprefix, dataEvaluationID + dimensionType, "dimension");
            }

            String actorURI = "";

            if (!actorName.isEmpty()) {
                actorURI = createURI(URIprefix, actorName, "actor");
            }

            String specimenURI = createURI(URIprefix, specimenName, "specimen");

            String publicationURI = "";

            if (!publication.isEmpty()) {
                actorURI = createURI(URIprefix, publication, "publication");
            }

            String datasetURI = createURI(URIprefix, datasetID, "dataset");

            String retTriples = "INSERT INTO <" + repositoryGraph + "> { ";

            if (!dataEvaluationURI.isEmpty()) {
                retTriples += "<" + dataEvaluationURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.dataEvaluationLabel + "> .\n";
                if (!timeSpan.isEmpty()) {
                    retTriples += "<" + dataEvaluationURI + "> <" + Resources.hasTimespan + "> \"" + timeSpan + "\" .\n";
                }
                if (!specimenURI.isEmpty()) {
                    retTriples += "<" + dataEvaluationURI + "> <" + Resources.describes + "> <" + specimenURI + "> .\n";
                }
                if (!dimensionURI.isEmpty()) {
                    retTriples += "<" + dataEvaluationURI + "> <" + Resources.assignedDimension + "> <" + dimensionURI + "> .\n";
                }
                if (!publicationURI.isEmpty()) {
                    retTriples += "<" + publicationURI + "> <" + Resources.refersTo + "> <" + dataEvaluationURI + "> .\n";
                }

                if (!actorURI.isEmpty()) {
                    retTriples += "<" + dataEvaluationURI + "> <" + Resources.carriedOutBy + "> <" + actorURI + "> .\n";
                }
                if (!description.isEmpty()) {
                    retTriples += "<" + dataEvaluationURI + "> <" + Resources.hasNote + "> \"" + description + "\" .\n";
                }
                if (!datasetURI.isEmpty()) {
                    retTriples += "<" + datasetURI + "> <" + Resources.refersTo + "> <" + dataEvaluationURI + "> . \n";
                }
                if(!dataEvaluationLabel.isEmpty()){
                retTriples+= "<"+dataEvaluationURI+"> <"+Resources.rdfsLabel+"> \""+dataEvaluationLabel+"\" .\n";
            }
            }

            if (!dimensionURI.isEmpty()) {
                retTriples += "<" + dimensionURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.dimensionLabel + "> .\n";
                if (!dimensionTypeURI.isEmpty()) {
                    retTriples += "<" + dimensionURI + "> <" + Resources.hasType + "> <" + dimensionTypeURI + "> .\n";
                    if (!dimensionType.isEmpty()) {
                        retTriples += "<" + dimensionTypeURI + "> <" + Resources.rdfsLabel + "> \"" + dimensionType + "\" .\n";
                    }
                    if (!dimensionUnit.isEmpty()) {
                        retTriples += "<" + dimensionURI + "> <" + Resources.hasUnit + "> \"" + dimensionUnit + "\" .\n";

                    }
                    if (!dimensionValue.isEmpty()) {
                        retTriples += "<" + dimensionURI + "> <" + Resources.hasValue + "> \"" + dimensionValue + "\" .\n";

                    }

                }
            }

            if (!specimenURI.isEmpty()) {
                retTriples += "<" + specimenURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.specimenLabel + "> .\n";
                if (!specimenName.isEmpty()) {
                    retTriples += "<" + specimenURI + "> <" + Resources.rdfsLabel + "> \"" + specimenName + "\" .\n";
                }
                if (!speciesURI.isEmpty()) {
                    retTriples += "<" + specimenURI + "> <" + Resources.belongsTo + "> <" + speciesURI + "> .\n";
                }
            }
            if (!speciesURI.isEmpty()) {
                retTriples += "<" + speciesURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.speciesLabel + "> .\n";
                if (!speciesName.isEmpty()) {
                    retTriples += "<" + speciesURI + "> <" + Resources.rdfsLabel + "> \"" + speciesName + "\" .\n";
                }

            }

            if (!actorURI.isEmpty()) {
                retTriples += "<" + actorURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.personLabel + "> .\n";
                if (!actorName.isEmpty()) {
                    retTriples += "<" + actorURI + "> <" + Resources.rdfsLabel + "> \"" + actorName + "\" .\n";
                }
            }

            if (!datasetURI.isEmpty()) {
                retTriples += "<" + datasetURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.datasetLabel + "> .\n";
                if (!datasetID.isEmpty()) {
                    retTriples += "<" + datasetURI + "> <" + Resources.rdfsLabel + "> \"" + datasetID + "\" .\n";
                }
            }

            if (!publicationURI.isEmpty()) {
                retTriples += "<" + publicationURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.publicationLabel + "> .\n";
                if (!publication.isEmpty()) {
                    retTriples += "<" + publicationURI + "> <" + Resources.rdfsLabel + "> \"" + publication + "\" .\n";
                }
            }

            retTriples += " } ";

            Queries.add(retTriples);

        }
        return Queries;

    }

    public ArrayList<String> transform_genetics_csv(String fileToParse, String repositoryGraph) throws FileNotFoundException, IOException {

        //String fileToParse = "C:/uploads/TLOflat/identification.csv";
        BufferedReader fileReader = null;

        System.out.println(fileToParse);
        //Delimiter used in CSV file
        final String DELIMITER = ";";

        String line = "";
        String query = "";
        ArrayList<String> Queries = new ArrayList<String>();

        fileReader = new BufferedReader(new FileReader(fileToParse));
        String URIprefix = "http://www.lifewatchgreece.eu/entity";
        int count = 0;

        while ((line = fileReader.readLine()) != null) {

            if (count == 0) {
                count++;
                continue;
            }

            String[] tokens = line.split(DELIMITER);

            String datasetID = tokens[0];
            String sampleTakingID = tokens[1];
            String timeSpan = tokens[2];
            String ecosystem = tokens[3];
            String ecosystemType = tokens[4];
            String sampleName = tokens[5];
            String speciesName = tokens[6];
            String description = tokens[7];
            String sequencingEventID = tokens[8];
            String device = tokens[9];
            String deviceType = tokens[10];
            String producedFile = tokens[11];
            
            String sequencingEventLabel = "DNA Sequencing Event of "+sampleName;
            String sampleTakingEventLabel = "DNA Sample Taking Event of "+speciesName;
            
            String sampleTakingURI = "";
           
            if (sampleTakingID.isEmpty()) {
                sampleTakingURI = createURI(URIprefix, datasetID + sampleName+"sampleTakingEvent", "sampleTakingEvent");
            } else {
                if (sampleTakingID.startsWith("http:")) {
                    sampleTakingURI = sampleTakingID;
                } else {
                    sampleTakingURI = createURI(URIprefix, sampleTakingID, "sampleTakingEvent");
                }
            }

            String sequencingEventURI = "";
            
            if (sequencingEventID.isEmpty()) {
                sequencingEventURI = createURI(URIprefix, datasetID+sampleName+"sequencingEvent", "digitizationProcess");
            } else {
                if (sequencingEventID.startsWith("http:")) {
                    sequencingEventURI = sampleTakingID;
                } else {
                    sequencingEventURI = createURI(URIprefix, sequencingEventID, "digitizationProcess");
                }
            }
       
            String sampleURI = createURI(URIprefix, sampleName, "sample");
            String speciesURI = createURI(URIprefix, speciesName, "species");
            
           
            String ecosystemURI = "";
            
            if(!ecosystem.isEmpty())
            ecosystemURI = createURI(URIprefix, ecosystem, "place");
            
            String habitatURI  = "";
           
            if(!ecosystemType.isEmpty())
            habitatURI = createURI(URIprefix, ecosystemType, "ecosystemType");
            
            
            String datasetURI = createURI(URIprefix, datasetID, "dataset");
         
            
            String deviceURI = "";
            if(!device.isEmpty())
            deviceURI = createURI(URIprefix, device, "device");
            
            
            String deviceTypeURI = "";
            if(!deviceType.isEmpty())
            deviceTypeURI = createURI(URIprefix, deviceType, "device");
            
            
            String producedFileURI = "";
              if(!producedFile.isEmpty())
            producedFileURI = createURI(URIprefix, producedFile, "dataObject");
            

            String retTriples = "INSERT INTO <" + repositoryGraph + "> { ";
            
           if(!sampleTakingURI.isEmpty()){
            retTriples+= "<"+sampleTakingURI+"> <"+Resources.rdfTypeLabel+"> <"+Resources.sampleTakingLabel+"> .\n";
            if(!timeSpan.isEmpty()){
                retTriples+= "<"+sampleTakingURI+"> <"+Resources.hasTimespan+"> \""+timeSpan+"\" .\n";
            }
            if(!ecosystemURI.isEmpty()){
                retTriples+= "<"+sampleTakingURI+"> <"+Resources.tookPlaceAt+"> <"+ecosystemURI+"> .\n";
            }
            if(!sampleURI.isEmpty()){
                retTriples+= "<"+sampleTakingURI+"> <"+Resources.removed+"> <"+sampleURI+"> .\n";
            }
            if(!datasetURI.isEmpty()){
                retTriples+= "<"+datasetURI+"> <"+Resources.refersTo+"> <"+sampleTakingURI+"> . \n";
            }
            
            if(!speciesURI.isEmpty()){
                retTriples+= "<"+sampleURI+"> <"+Resources.belongsTo+"> <"+speciesURI+"> .\n";
            }
              
            if(!description.isEmpty()){
                retTriples+= "<"+sampleTakingURI+"> <"+Resources.hasNote+"> \""+description+"\" .\n";
            }
            if(!sampleTakingEventLabel.isEmpty()){
                retTriples+= "<"+sampleTakingURI+"> <"+Resources.rdfsLabel+"> \""+sampleTakingEventLabel+"\" .\n";
            }
            
        }
        if(!datasetURI.isEmpty()){
            retTriples+= "<"+datasetURI+"> <"+Resources.rdfTypeLabel+"> <"+Resources.datasetLabel+"> .\n";
            if(!datasetID.isEmpty()){
                retTriples+= "<"+datasetURI+"> <"+Resources.rdfsLabel+"> \""+datasetID+"\" .\n";
            }
         
        }
        if(!sampleURI.isEmpty()){
            retTriples+= "<"+sampleURI+"> <"+Resources.rdfTypeLabel+"> <"+Resources.sampleLabel+"> .\n";
            if(!sampleName.isEmpty()){
                retTriples+= "<"+sampleURI+"> <"+Resources.rdfsLabel+"> \""+sampleName+"\" .\n";
            }
            if(!speciesURI.isEmpty()){
                retTriples+= "<"+sampleURI+"> <"+Resources.belongsTo+"> <"+speciesURI+"> .\n";
            }  
        }
        if(!speciesURI.isEmpty()){
            retTriples+= "<"+speciesURI+"> <"+Resources.rdfTypeLabel+"> <"+Resources.speciesLabel+"> .\n";
            if(!speciesName.isEmpty()){
                retTriples+= "<"+speciesURI+"> <"+Resources.rdfsLabel+"> \""+speciesName+"\" .\n";
            }
        }
        if(!ecosystemURI.isEmpty()){
            retTriples+= "<"+ecosystemURI+"> <"+Resources.rdfTypeLabel+"> <"+Resources.ecosystemEnvironmentLabel+"> .\n";
            if(!ecosystem.isEmpty()){
                retTriples+= "<"+ecosystemURI+"> <"+Resources.rdfsLabel+"> \""+ecosystem+"\" .\n";
            }
         
            if(!habitatURI.isEmpty()){
                retTriples+= "<"+ecosystemURI+"> <"+Resources.hasType+"> <"+habitatURI+"> .\n";
            }
          
        }
        if(!habitatURI.isEmpty()){
            retTriples+= "<"+habitatURI+"> <"+Resources.rdfTypeLabel+"> <"+Resources.ecosystemTypeLabel+"> .\n";
            if(!ecosystemType.isEmpty()){
                retTriples+= "<"+habitatURI+"> <"+Resources.rdfsLabel+"> \""+ecosystemType+"\" .\n";
            }
        }
    
        if(!sequencingEventURI.isEmpty()){
            retTriples+= "<"+sequencingEventURI+"> <"+Resources.rdfTypeLabel+"> <"+Resources.digitizationProcessLabel+"> .\n";
            retTriples+= "<"+sequencingEventURI+"> <"+Resources.hasType+"> \"Sequencing\" .\n";
            if(!sampleURI.isEmpty()){
                retTriples+= "<"+sequencingEventURI+"> <"+Resources.digitized+"> <"+sampleURI+"> .\n";
            }
            if(!producedFile.isEmpty()){
                retTriples+= "<"+sequencingEventURI+"> <"+Resources.hasCreated+"> <"+producedFileURI+"> .\n";
            }
            if(!deviceURI.isEmpty()){
                retTriples+= "<"+sequencingEventURI+"> <"+Resources.happenedOnDevice+"> <"+deviceURI+"> .\n";
            }
            if(!datasetURI.isEmpty()){
                retTriples+= "<"+datasetURI+"> <"+Resources.refersTo+"> <"+sequencingEventURI+"> . \n";
            }
            if(!sequencingEventLabel.isEmpty()){
                retTriples+= "<"+sequencingEventURI+"> <"+Resources.rdfsLabel+"> \""+sequencingEventLabel+"\" .\n";
            }
          
        }
        if(!deviceURI.isEmpty()){
            retTriples+= "<"+deviceURI+"> <"+Resources.rdfTypeLabel+"> <"+Resources.digitalDeviceLabel+"> .\n";
            if(!device.isEmpty()){
                retTriples+= "<"+deviceURI+"> <"+Resources.rdfsLabel+"> \""+device+"\" .\n";
            }
            if(!deviceType.isEmpty()){
                retTriples+= "<"+deviceURI+"> <"+Resources.hasType+"> \""+deviceType+"\" .\n";
            }
        }
       
        if(!producedFileURI.isEmpty()){
            retTriples+= "<"+producedFileURI+"> <"+Resources.rdfTypeLabel+"> <"+Resources.dataObjectLabel+"> .\n";
            if(!producedFile.isEmpty()){
                retTriples+= "<"+producedFileURI+"> <"+Resources.rdfsLabel+"> \""+producedFile+"\" .\n";
            }
        }

            retTriples += " } ";

            Queries.add(retTriples);

            //System.out.println(retTriples);
        }

        return Queries;

    }

    public ArrayList<String> transform_genetics_csv(String fileToParse, 
            String repositoryGraph, String datasetID) throws FileNotFoundException, IOException {

      
        //String fileToParse = "C:/uploads/TLOflat/identification.csv";
        BufferedReader fileReader = null;

        System.out.println(fileToParse);
        //Delimiter used in CSV file
        final String DELIMITER = ";";

        String line = "";
        String query = "";
        ArrayList<String> Queries = new ArrayList<String>();

        fileReader = new BufferedReader(new FileReader(fileToParse));
        String URIprefix = "http://www.lifewatchgreece.eu/entity";
        int count = 0;

        while ((line = fileReader.readLine()) != null) {

            if (count == 0) {
                count++;
                continue;
            }

            String[] tokens = line.split(DELIMITER);

          //  String datasetID = tokens[0];
            String sampleTakingID = tokens[1];
            String timeSpan = tokens[2];
            String ecosystem = tokens[3];
            String ecosystemType = tokens[4];
            String sampleName = tokens[5];
            String speciesName = tokens[6];
            String description = tokens[7];
            String sequencingEventID = tokens[8];
            String device = tokens[9];
            String deviceType = tokens[10];
            String producedFile = tokens[11];
            
            String sequencingEventLabel = "DNA Sequencing Event of "+sampleName;
            String sampleTakingEventLabel = "DNA Sample Taking Event of "+speciesName;
            
            String sampleTakingURI = "";
           
            if (sampleTakingID.isEmpty()) {
                sampleTakingURI = createURI(URIprefix, datasetID + sampleName+"sampleTakingEvent", "sampleTakingEvent");
            } else {
                if (sampleTakingID.startsWith("http:")) {
                    sampleTakingURI = sampleTakingID;
                } else {
                    sampleTakingURI = createURI(URIprefix, sampleTakingID, "sampleTakingEvent");
                }
            }

            String sequencingEventURI = "";
            
            if (sequencingEventID.isEmpty()) {
                sequencingEventURI = createURI(URIprefix, datasetID+sampleName+"sequencingEvent", "digitizationProcess");
            } else {
                if (sequencingEventID.startsWith("http:")) {
                    sequencingEventURI = sampleTakingID;
                } else {
                    sequencingEventURI = createURI(URIprefix, sequencingEventID, "digitizationProcess");
                }
            }
       
            String sampleURI = createURI(URIprefix, sampleName, "sample");
            String speciesURI = createURI(URIprefix, speciesName, "species");
            
           
            String ecosystemURI = "";
            
            if(!ecosystem.isEmpty())
            ecosystemURI = createURI(URIprefix, ecosystem, "place");
            
            String habitatURI  = "";
           
            if(!ecosystemType.isEmpty())
            habitatURI = createURI(URIprefix, ecosystemType, "ecosystemType");
            
            
            String datasetURI = createURI(URIprefix, datasetID, "dataset");
         
            
            String deviceURI = "";
            if(!device.isEmpty())
            deviceURI = createURI(URIprefix, device, "device");
            
            
            String deviceTypeURI = "";
            if(!deviceType.isEmpty())
            deviceTypeURI = createURI(URIprefix, deviceType, "device");
            
            
            String producedFileURI = "";
              if(!producedFile.isEmpty())
            producedFileURI = createURI(URIprefix, producedFile, "dataObject");
            

            String retTriples = "INSERT INTO <" + repositoryGraph + "> { ";
            
           if(!sampleTakingURI.isEmpty()){
            retTriples+= "<"+sampleTakingURI+"> <"+Resources.rdfTypeLabel+"> <"+Resources.sampleTakingLabel+"> .\n";
            if(!timeSpan.isEmpty()){
                retTriples+= "<"+sampleTakingURI+"> <"+Resources.hasTimespan+"> \""+timeSpan+"\" .\n";
            }
            if(!ecosystemURI.isEmpty()){
                retTriples+= "<"+sampleTakingURI+"> <"+Resources.tookPlaceAt+"> <"+ecosystemURI+"> .\n";
            }
            if(!sampleURI.isEmpty()){
                retTriples+= "<"+sampleTakingURI+"> <"+Resources.removed+"> <"+sampleURI+"> .\n";
            }
            if(!datasetURI.isEmpty()){
                retTriples+= "<"+datasetURI+"> <"+Resources.refersTo+"> <"+sampleTakingURI+"> . \n";
            }
            
            if(!speciesURI.isEmpty()){
                retTriples+= "<"+sampleURI+"> <"+Resources.belongsTo+"> <"+speciesURI+"> .\n";
            }
              
            if(!description.isEmpty()){
                retTriples+= "<"+sampleTakingURI+"> <"+Resources.hasNote+"> \""+description+"\" .\n";
            }
            if(!sampleTakingEventLabel.isEmpty()){
                retTriples+= "<"+sampleTakingURI+"> <"+Resources.rdfsLabel+"> \""+sampleTakingEventLabel+"\" .\n";
            }
            
        }
        if(!datasetURI.isEmpty()){
            retTriples+= "<"+datasetURI+"> <"+Resources.rdfTypeLabel+"> <"+Resources.datasetLabel+"> .\n";
            if(!datasetID.isEmpty()){
                retTriples+= "<"+datasetURI+"> <"+Resources.rdfsLabel+"> \""+datasetID+"\" .\n";
            }
         
        }
        if(!sampleURI.isEmpty()){
            retTriples+= "<"+sampleURI+"> <"+Resources.rdfTypeLabel+"> <"+Resources.sampleLabel+"> .\n";
            if(!sampleName.isEmpty()){
                retTriples+= "<"+sampleURI+"> <"+Resources.rdfsLabel+"> \""+sampleName+"\" .\n";
            }
            if(!speciesURI.isEmpty()){
                retTriples+= "<"+sampleURI+"> <"+Resources.belongsTo+"> <"+speciesURI+"> .\n";
            }  
        }
        if(!speciesURI.isEmpty()){
            retTriples+= "<"+speciesURI+"> <"+Resources.rdfTypeLabel+"> <"+Resources.speciesLabel+"> .\n";
            if(!speciesName.isEmpty()){
                retTriples+= "<"+speciesURI+"> <"+Resources.rdfsLabel+"> \""+speciesName+"\" .\n";
            }
        }
        if(!ecosystemURI.isEmpty()){
            retTriples+= "<"+ecosystemURI+"> <"+Resources.rdfTypeLabel+"> <"+Resources.ecosystemEnvironmentLabel+"> .\n";
            if(!ecosystem.isEmpty()){
                retTriples+= "<"+ecosystemURI+"> <"+Resources.rdfsLabel+"> \""+ecosystem+"\" .\n";
            }
         
            if(!habitatURI.isEmpty()){
                retTriples+= "<"+ecosystemURI+"> <"+Resources.hasType+"> <"+habitatURI+"> .\n";
            }
          
        }
        if(!habitatURI.isEmpty()){
            retTriples+= "<"+habitatURI+"> <"+Resources.rdfTypeLabel+"> <"+Resources.ecosystemTypeLabel+"> .\n";
            if(!ecosystemType.isEmpty()){
                retTriples+= "<"+habitatURI+"> <"+Resources.rdfsLabel+"> \""+ecosystemType+"\" .\n";
            }
        }
    
        if(!sequencingEventURI.isEmpty()){
            retTriples+= "<"+sequencingEventURI+"> <"+Resources.rdfTypeLabel+"> <"+Resources.digitizationProcessLabel+"> .\n";
            retTriples+= "<"+sequencingEventURI+"> <"+Resources.hasType+"> \"Sequencing\" .\n";
            if(!sampleURI.isEmpty()){
                retTriples+= "<"+sequencingEventURI+"> <"+Resources.digitized+"> <"+sampleURI+"> .\n";
            }
            if(!producedFile.isEmpty()){
                retTriples+= "<"+sequencingEventURI+"> <"+Resources.hasCreated+"> <"+producedFileURI+"> .\n";
            }
            if(!deviceURI.isEmpty()){
                retTriples+= "<"+sequencingEventURI+"> <"+Resources.happenedOnDevice+"> <"+deviceURI+"> .\n";
            }
            if(!datasetURI.isEmpty()){
                retTriples+= "<"+datasetURI+"> <"+Resources.refersTo+"> <"+sequencingEventURI+"> . \n";
            }
            if(!sequencingEventLabel.isEmpty()){
                retTriples+= "<"+sequencingEventURI+"> <"+Resources.rdfsLabel+"> \""+sequencingEventLabel+"\" .\n";
            }
          
        }
        if(!deviceURI.isEmpty()){
            retTriples+= "<"+deviceURI+"> <"+Resources.rdfTypeLabel+"> <"+Resources.digitalDeviceLabel+"> .\n";
            if(!device.isEmpty()){
                retTriples+= "<"+deviceURI+"> <"+Resources.rdfsLabel+"> \""+device+"\" .\n";
            }
            if(!deviceType.isEmpty()){
                retTriples+= "<"+deviceURI+"> <"+Resources.hasType+"> \""+deviceType+"\" .\n";
            }
        }
       
        if(!producedFileURI.isEmpty()){
            retTriples+= "<"+producedFileURI+"> <"+Resources.rdfTypeLabel+"> <"+Resources.dataObjectLabel+"> .\n";
            if(!producedFile.isEmpty()){
                retTriples+= "<"+producedFileURI+"> <"+Resources.rdfsLabel+"> \""+producedFile+"\" .\n";
            }
        }

            retTriples += " } ";

            Queries.add(retTriples);

            //System.out.println(retTriples);
        }

        return Queries;

    }

    public ArrayList<String> transform_genetics_sample_csv(String fileToParse, String repositoryGraph) throws FileNotFoundException, IOException {

        //String fileToParse = "C:/uploads/TLOflat/identification.csv";
        BufferedReader fileReader = null;

        System.out.println(fileToParse);
        //Delimiter used in CSV file
        final String DELIMITER = ",";

        String line = "";
        String query = "";
        ArrayList<String> Queries = new ArrayList<String>();

        fileReader = new BufferedReader(new FileReader(fileToParse));
        String URIprefix = "http://lifewatch.gr/entity";
        int count = 0;

        while ((line = fileReader.readLine()) != null) {

            if (count == 0) {
                count++;
                continue;
            }

            String[] tokens = line.split(DELIMITER);

            String sampleName = tokens[0];
            String transformationID = tokens[1];
            String description = tokens[2];
            String transformedSampleName = tokens[3];
            String placeName = tokens[4];
            String dimensionName = tokens[5];
            String dimensionValue = tokens[6];
            String dimensionUnit = tokens[7];
            String sequencingEventID = tokens[8];
            String deviceName = tokens[9];
            String deviceType = tokens[10];
            String productName = tokens[11];
            String postProcessingID = tokens[12];
            String postProductName = tokens[13];
            String datasetName = tokens[14];
            String speciesName = tokens[15];

            String transformationURI = createURI(URIprefix, transformationID, "transformationEvent");
            String transformedSampleURI = createURI(URIprefix, transformedSampleName, "sample");
            String placeURI = createURI(URIprefix, placeName, "place");
            String sequencingURI = createURI(URIprefix, sequencingEventID, "digitizationProcess");
            String deviceURI = createURI(URIprefix, deviceName, "device");
            String deviceTypeURI = createURI(URIprefix, deviceType, "device");
            String productURI = createURI(URIprefix, productName, "dataObject");
            String postProcessingURI = createURI(URIprefix, postProcessingID, "formalDerivation");
            String dimensionURI = createURI(URIprefix, sampleName + dimensionName, "dimension");
            String dimensionTypeURI = createURI(URIprefix, dimensionName, "dimension");
            String sampleURI = createURI(URIprefix, sampleName, "sample");
            String postProductURI = createURI(URIprefix, postProductName, "dataObject");
            String speciesURI = createURI(URIprefix, speciesName, "species");
            String datasetURI = createURI(URIprefix, datasetName, "dataset");
//           String EcosystemURI = URIprefix+Ecosystem.replace(" ","_")+">";
//           String EquipmentURI = URIprefix+EquipmentType.replace(" ","_")+">";  

            //    String station = URIprefix+occurrenceID+"station"+">";
            String retTriples = "INSERT INTO <" + repositoryGraph + "> { ";

            if (!transformationURI.isEmpty()) {
                retTriples += "<" + transformationURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.transformationEventLabel + "> .\n";
                if (!sampleURI.isEmpty()) {
                    retTriples += "<" + transformationURI + "> <" + Resources.transformed + "> <" + sampleURI + "> .\n";
                }
                if (!transformedSampleURI.isEmpty()) {
                    retTriples += "<" + transformationURI + "> <" + Resources.resultedIn + "> <" + transformedSampleURI + "> .\n";
                }
            }

            if (!sampleURI.isEmpty()) {
                retTriples += "<" + sampleURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.sampleLabel + "> .\n";
                if (!sampleName.isEmpty()) {
                    retTriples += "<" + sampleURI + "> <" + Resources.rdfsLabel+ "> \"" + sampleName + "\" .\n";
                }
                if (!placeURI.isEmpty()) {
                    retTriples += "<" + sampleURI + "> <" + Resources.hasCurrentLocation + "> <" + placeURI + "> .\n";
                }
                if (!speciesURI.isEmpty()) {
                    retTriples += "<" + sampleURI + "> <" + Resources.belongsTo + "> <" + speciesURI + "> .\n";
                }
            }
            if (!speciesURI.isEmpty()) {
                retTriples += "<" + speciesURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.speciesLabel + "> .\n";
                if (!speciesName.isEmpty()) {
                    retTriples += "<" + speciesURI + "> <" + Resources.rdfsLabel + "> \"" + speciesName + "\" .\n";
                }

            }
            if (!transformedSampleURI.isEmpty()) {
                retTriples += "<" + transformedSampleURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.sampleLabel + "> .\n";
                if (!transformedSampleName.isEmpty()) {
                    retTriples += "<" + transformedSampleURI + "> <" + Resources.rdfsLabel+ "> \"" + transformedSampleName + "\" .\n";
                }
                if (!placeURI.isEmpty()) {
                    retTriples += "<" + transformedSampleURI + "> <" + Resources.hasCurrentLocation + "> <" + placeURI + "> .\n";
                }
            }

            if (!datasetURI.isEmpty()) {
                retTriples += "<" + datasetURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.datasetLabel + "> .\n";
                if (!datasetName.isEmpty()) {
                    retTriples += "<" + datasetURI + "> <" + Resources.rdfsLabel + "> \"" + datasetName + "\" .\n";
                }
            }

            if (!placeURI.isEmpty()) {
                retTriples += "<" + placeURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.placeLabel + "> .\n";
                if (!placeName.isEmpty()) {
                    retTriples += "<" + placeURI + "> <" + Resources.rdfsLabel + "> \"" + placeName + "\" .\n";
                }
                if (!dimensionURI.isEmpty()) {
                    retTriples += "<" + placeURI + "> <" + Resources.hasDimension + "> <" + dimensionURI + "> .\n";
                }
            }

            if (!sequencingURI.isEmpty()) {
                retTriples += "<" + sequencingURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.digitizationProcessLabel + "> .\n";
                retTriples += "<" + sequencingURI + "> <" + Resources.hasType + "> \"Sequencing\" .\n";

                if (!sampleURI.isEmpty()) {
                    retTriples += "<" + sequencingURI + "> <" + Resources.digitized + "> <" + sampleURI + "> .\n";
                }
                if (!productURI.isEmpty()) {
                    retTriples += "<" + sequencingURI + "> <" + Resources.createdDerivative + "> <" + productURI + "> .\n";
                }
                if (!deviceURI.isEmpty()) {
                    retTriples += "<" + sequencingURI + "> <" + Resources.happenedOnDevice + "> <" + deviceURI + "> .\n";
                }
                if (!datasetURI.isEmpty()) {
                    retTriples += "<" + datasetURI + "> <" + Resources.refersTo + "> <" + sequencingURI + "> . \n";
                }
                if (!description.isEmpty()) {
                    retTriples += "<" + sequencingURI + "> <" + Resources.hasNote + "> \"" + description + "\" .\n";
                }
            }

            if (!deviceURI.isEmpty()) {
                retTriples += "<" + deviceURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.digitalDeviceLabel + "> .\n";
                if (!deviceName.isEmpty()) {
                    retTriples += "<" + deviceURI + "> <" + Resources.rdfsLabel + "> \"" + deviceName + "\" .\n";
                }
                if (!deviceType.isEmpty()) {
                    retTriples += "<" + deviceURI + "> <" + Resources.hasType + "> \"" + deviceType + "\" .\n";
                }
            }

            if (!postProcessingURI.isEmpty()) {
                retTriples += "<" + postProcessingURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.formalDerivationEventLabel + "> .\n";
                if (!productURI.isEmpty()) {
                    retTriples += "<" + productURI + "> <" + Resources.wasDerivationSourceFor + "> <" + postProcessingURI + "> .\n";
                }
                if (!postProductURI.isEmpty()) {
                    retTriples += "<" + postProcessingURI + "> <" + Resources.hasCreated + "> <" + postProductURI + "> .\n";
                }
            }
            if (!productURI.isEmpty()) {
                retTriples += "<" + productURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.dataObjectLabel + "> .\n";
                if (!productName.isEmpty()) {
                    retTriples += "<" + productURI + "> <" + Resources.rdfsLabel + "> \"" + productName + "\" .\n";
                }
            }

            if (!postProductURI.isEmpty()) {
                retTriples += "<" + postProductURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.dataObjectLabel + "> .\n";
                if (!postProductName.isEmpty()) {
                    retTriples += "<" + postProductURI + "> <" + Resources.rdfsLabel + "> \"" + postProductName + "\" .\n";
                }
            }

            if (!dimensionURI.isEmpty()) {
                retTriples += "<" + dimensionURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.dimensionLabel + "> .\n";
                if (!dimensionTypeURI.isEmpty()) {
                    retTriples += "<" + dimensionURI + "> <" + Resources.hasType + "> <" + dimensionTypeURI + "> .\n";
                    if (!dimensionName.isEmpty()) {
                        retTriples += "<" + dimensionTypeURI + "> <" + Resources.rdfsLabel+ "> \"" + dimensionName + "\" .\n";
                    }
                    if (!dimensionUnit.isEmpty()) {
                        retTriples += "<" + dimensionURI + "> <" + Resources.hasUnit + "> \"" + dimensionUnit + "\" .\n";

                    }
                    if (!dimensionValue.isEmpty()) {
                        retTriples += "<" + dimensionURI + "> <" + Resources.hasValue + "> \"" + dimensionValue + "\" .\n";

                    }

                }
            }

            retTriples += " } ";

            Queries.add(retTriples);

            //System.out.println(retTriples);
        }

        return Queries;

    }

    public ArrayList<String> transform_genetics_sample_csv(String fileToParse, String datasetName, String repositoryGraph) throws FileNotFoundException, IOException {

        //String fileToParse = "C:/uploads/TLOflat/identification.csv";
        BufferedReader fileReader = null;

        System.out.println(fileToParse);
        //Delimiter used in CSV file
        final String DELIMITER = ",";

        String line = "";
        String query = "";
        ArrayList<String> Queries = new ArrayList<String>();

        fileReader = new BufferedReader(new FileReader(fileToParse));
        String URIprefix = "http://lifewatch.gr/entity";
        int count = 0;

        while ((line = fileReader.readLine()) != null) {

            if (count == 0) {
                count++;
                continue;
            }

            String[] tokens = line.split(DELIMITER);

            String sampleName = tokens[0];
            String transformationID = tokens[1];
            String description = tokens[2];
            String transformedSampleName = tokens[3];
            String placeName = tokens[4];
            String dimensionName = tokens[5];
            String dimensionValue = tokens[6];
            String dimensionUnit = tokens[7];
            String sequencingEventID = tokens[8];
            String deviceName = tokens[9];
            String deviceType = tokens[10];
            String productName = tokens[11];
            String postProcessingID = tokens[12];
            String postProductName = tokens[13];
            //  String datasetName=tokens[14];
            String speciesName = tokens[15];

            String transformationURI = createURI(URIprefix, transformationID, "transformationEvent");
            String transformedSampleURI = createURI(URIprefix, transformedSampleName, "sample");
            String placeURI = createURI(URIprefix, placeName, "place");
            String sequencingURI = createURI(URIprefix, sequencingEventID, "digitizationProcess");
            String deviceURI = createURI(URIprefix, deviceName, "device");
            String deviceTypeURI = createURI(URIprefix, deviceType, "device");
            String productURI = createURI(URIprefix, productName, "dataObject");
            String postProcessingURI = createURI(URIprefix, postProcessingID, "formalDerivation");
            String dimensionURI = createURI(URIprefix, sampleName + dimensionName, "dimension");
            String dimensionTypeURI = createURI(URIprefix, dimensionName, "dimension");
            String sampleURI = createURI(URIprefix, sampleName, "sample");
            String postProductURI = createURI(URIprefix, postProductName, "dataObject");
            String speciesURI = createURI(URIprefix, speciesName, "species");
            String datasetURI = createURI(URIprefix, datasetName, "dataset");
//           String EcosystemURI = URIprefix+Ecosystem.replace(" ","_")+">";
//           String EquipmentURI = URIprefix+EquipmentType.replace(" ","_")+">";  

            //    String station = URIprefix+occurrenceID+"station"+">";
            String retTriples = "INSERT INTO <" + repositoryGraph + "> { ";

            if (!transformationURI.isEmpty()) {
                retTriples += "<" + transformationURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.transformationEventLabel + "> .\n";
                if (!sampleURI.isEmpty()) {
                    retTriples += "<" + transformationURI + "> <" + Resources.transformed + "> <" + sampleURI + "> .\n";
                }
                if (!transformedSampleURI.isEmpty()) {
                    retTriples += "<" + transformationURI + "> <" + Resources.resultedIn + "> <" + transformedSampleURI + "> .\n";
                }
            }

            if (!sampleURI.isEmpty()) {
                retTriples += "<" + sampleURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.sampleLabel + "> .\n";
                if (!sampleName.isEmpty()) {
                    retTriples += "<" + sampleURI + "> <" + Resources.rdfsLabel + "> \"" + sampleName + "\" .\n";
                }
                if (!placeURI.isEmpty()) {
                    retTriples += "<" + sampleURI + "> <" + Resources.hasCurrentLocation + "> <" + placeURI + "> .\n";
                }
                if (!speciesURI.isEmpty()) {
                    retTriples += "<" + sampleURI + "> <" + Resources.belongsTo + "> <" + speciesURI + "> .\n";
                }
            }
            if (!speciesURI.isEmpty()) {
                retTriples += "<" + speciesURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.speciesLabel + "> .\n";
                if (!speciesName.isEmpty()) {
                    retTriples += "<" + speciesURI + "> <" + Resources.rdfsLabel + "> \"" + speciesName + "\" .\n";
                }

            }
            if (!transformedSampleURI.isEmpty()) {
                retTriples += "<" + transformedSampleURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.sampleLabel + "> .\n";
                if (!transformedSampleName.isEmpty()) {
                    retTriples += "<" + transformedSampleURI + "> <" + Resources.rdfsLabel + "> \"" + transformedSampleName + "\" .\n";
                }
                if (!placeURI.isEmpty()) {
                    retTriples += "<" + transformedSampleURI + "> <" + Resources.hasCurrentLocation + "> <" + placeURI + "> .\n";
                }
            }

            if (!datasetURI.isEmpty()) {
                retTriples += "<" + datasetURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.datasetLabel + "> .\n";
                if (!datasetName.isEmpty()) {
                    retTriples += "<" + datasetURI + "> <" + Resources.rdfsLabel+ "> \"" + datasetName + "\" .\n";
                }
            }

            if (!placeURI.isEmpty()) {
                retTriples += "<" + placeURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.placeLabel + "> .\n";
                if (!placeName.isEmpty()) {
                    retTriples += "<" + placeURI + "> <" + Resources.rdfsLabel + "> \"" + placeName + "\" .\n";
                }
                if (!dimensionURI.isEmpty()) {
                    retTriples += "<" + placeURI + "> <" + Resources.hasDimension + "> <" + dimensionURI + "> .\n";
                }
            }

            if (!sequencingURI.isEmpty()) {
                retTriples += "<" + sequencingURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.digitizationProcessLabel + "> .\n";
                retTriples += "<" + sequencingURI + "> <" + Resources.hasType + "> \"Sequencing\" .\n";

                if (!sampleURI.isEmpty()) {
                    retTriples += "<" + sequencingURI + "> <" + Resources.digitized + "> <" + sampleURI + "> .\n";
                }
                if (!productURI.isEmpty()) {
                    retTriples += "<" + sequencingURI + "> <" + Resources.createdDerivative + "> <" + productURI + "> .\n";
                }
                if (!deviceURI.isEmpty()) {
                    retTriples += "<" + sequencingURI + "> <" + Resources.happenedOnDevice + "> <" + deviceURI + "> .\n";
                }
                if (!datasetURI.isEmpty()) {
                    retTriples += "<" + datasetURI + "> <" + Resources.refersTo + "> <" + sequencingURI + "> . \n";
                }
                if (!description.isEmpty()) {
                    retTriples += "<" + sequencingURI + "> <" + Resources.hasNote + "> \"" + description + "\" .\n";
                }
            }

            if (!deviceURI.isEmpty()) {
                retTriples += "<" + deviceURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.digitalDeviceLabel + "> .\n";
                if (!deviceName.isEmpty()) {
                    retTriples += "<" + deviceURI + "> <" + Resources.rdfsLabel+ "> \"" + deviceName + "\" .\n";
                }
                if (!deviceType.isEmpty()) {
                    retTriples += "<" + deviceURI + "> <" + Resources.hasType + "> \"" + deviceType + "\" .\n";
                }
            }

            if (!postProcessingURI.isEmpty()) {
                retTriples += "<" + postProcessingURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.formalDerivationEventLabel + "> .\n";
                if (!productURI.isEmpty()) {
                    retTriples += "<" + productURI + "> <" + Resources.wasDerivationSourceFor + "> <" + postProcessingURI + "> .\n";
                }
                if (!postProductURI.isEmpty()) {
                    retTriples += "<" + postProcessingURI + "> <" + Resources.hasCreated + "> <" + postProductURI + "> .\n";
                }
            }
            if (!productURI.isEmpty()) {
                retTriples += "<" + productURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.dataObjectLabel + "> .\n";
                if (!productName.isEmpty()) {
                    retTriples += "<" + productURI + "> <" + Resources.rdfsLabel + "> \"" + productName + "\" .\n";
                }
            }

            if (!postProductURI.isEmpty()) {
                retTriples += "<" + postProductURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.dataObjectLabel + "> .\n";
                if (!postProductName.isEmpty()) {
                    retTriples += "<" + postProductURI + "> <" + Resources.rdfsLabel + "> \"" + postProductName + "\" .\n";
                }
            }

            if (!dimensionURI.isEmpty()) {
                retTriples += "<" + dimensionURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.dimensionLabel + "> .\n";
                if (!dimensionTypeURI.isEmpty()) {
                    retTriples += "<" + dimensionURI + "> <" + Resources.hasType + "> <" + dimensionTypeURI + "> .\n";
                    if (!dimensionName.isEmpty()) {
                        retTriples += "<" + dimensionTypeURI + "> <" + Resources.rdfsLabel + "> \"" + dimensionName + "\" .\n";
                    }
                    if (!dimensionUnit.isEmpty()) {
                        retTriples += "<" + dimensionURI + "> <" + Resources.hasUnit + "> \"" + dimensionUnit + "\" .\n";

                    }
                    if (!dimensionValue.isEmpty()) {
                        retTriples += "<" + dimensionURI + "> <" + Resources.hasValue + "> \"" + dimensionValue + "\" .\n";

                    }

                }
            }

            retTriples += " } ";

            Queries.add(retTriples);

            //System.out.println(retTriples);
        }

        return Queries;

    }

    public ArrayList<String> transform_microct_specimen_csv(String fileToParse, String repositoryGraph) throws FileNotFoundException, IOException {

        //String fileToParse = "C:/uploads/TLOflat/identification.csv";
        BufferedReader fileReader = null;

        System.out.println(fileToParse);
        //Delimiter used in CSV file
        final String DELIMITER = ";";

        String line = "";
        String query = "";
        ArrayList<String> Queries = new ArrayList<String>();

        fileReader = new BufferedReader(new FileReader(fileToParse));
        String URIprefix = "http://www.lifewatchgreece.eu/entity";
        int count = 0;

        while ((line = fileReader.readLine()) != null) {

            if (count == 0) {
                count++;
                continue;
            }

            String[] tokens = line.split(DELIMITER);

            String specimen = tokens[0];
            //String specimenLabel = tokens[1];
            String datasetID = tokens[2];
            String collectionID = tokens[3];
            String providerID = tokens[4];
            String institution = tokens[5];
            String description = tokens[6];
            //String material = tokens[7];
            String species = tokens[8];
            String dimension = "size";
            String dimensionValue = tokens[9];
            String dimensionUnit = "mm";

            //String fixationType = tokens[10];
            //String fixationNotes = tokens[11];
            //String preservationMedium = tokens[12];
            //String storagePlace = tokens[14];
            String specimenURI = createURI(URIprefix, specimen, "specimen");

            String collectionURI = "";
            if (!collectionID.isEmpty()) {
                collectionURI = createURI(URIprefix, collectionID, "collection");
            }

            String providerURI = "";
            if (!providerID.isEmpty()) {
                providerURI = createURI(URIprefix, providerID, "actor");
            }

            String institutionURI = "";
            if (!institution.isEmpty()) {
                institutionURI = createURI(URIprefix, institution, "actor");
            }

            String speciesURI = createURI(URIprefix, species, "species");

            //String SpecimenTypeURI = URIprefix+SpecimenTypeID.replace(" ","_")+">";
            //String fixationTypeURI = createURI(URIprefix, fixationType, "protocol");
            //String preservationTypeURI = createURI(URIprefix, preservationMedium, "protocol");
            String dimensionTypeURI = "";
            if (!dimension.isEmpty()) {
                dimensionTypeURI = createURI(URIprefix, dimension, "dimension");
            }

            String dimensionURI = "";
            if (!dimension.isEmpty()) {
                dimensionURI = createURI(URIprefix, dimension + specimen, "dimension");
            }

            String datasetURI = createURI(URIprefix, datasetID, "dataset");

            // String EcosystemURI = URIprefix+Ecosystem.replace(" ","_")+">";
            // String EquipmentURI = URIprefix+EquipmentType.replace(" ","_")+">";  
            // String station = URIprefix+occurrenceID+"station"+">";
            String retTriples = "INSERT INTO <" + repositoryGraph + "> { ";

            if (!specimenURI.isEmpty()) {
                retTriples += "<" + specimenURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.specimenLabel + "> .\n";
                if (!collectionURI.isEmpty()) {
                    retTriples += "<" + specimenURI + "> <" + Resources.formsPartOf + "> <" + collectionURI + "> .\n";
                }
                if (!providerURI.isEmpty()) {
                    retTriples += "<" + specimenURI + "> <" + Resources.wasProvidedBy + "> <" + providerURI + "> .\n";
                }
                if (!description.isEmpty()) {
                    retTriples += "<" + specimenURI + "> <" + Resources.hasNote + "> \"" + description + "\" .\n";
                }
                if (!speciesURI.isEmpty()) {
                    retTriples += "<" + specimenURI + "> <" + Resources.belongsTo + "> <" + speciesURI + "> .\n";
                }
//          if(!specimen.isEmpty()){
//                retTriples+= "<"+specimenURI+"> <"+Resources.hasType+"> <"+specimen+"> .\n";
//            }
                if (!dimensionURI.isEmpty()) {
                    retTriples += "<" + specimenURI + "> <" + Resources.hasDimension + "> <" + dimensionURI + "> .\n";
                }
                if (!datasetURI.isEmpty()) {
                    retTriples += "<" + datasetURI + "> <" + Resources.refersTo + "> <" + specimenURI + "> . \n";
                }
            }

            if (!specimenURI.isEmpty()) {
                retTriples += "<" + specimenURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.specimenLabel + "> .\n";
                if (!specimen.isEmpty()) {
                    retTriples += "<" + specimenURI + "> <" + Resources.rdfsLabel + "> \"" + specimen + "\" .\n";
                }

            }

            if (!speciesURI.isEmpty()) {
                retTriples += "<" + speciesURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.speciesLabel + "> .\n";
                if (!species.isEmpty()) {
                    retTriples += "<" + speciesURI + "> <" + Resources.rdfsLabel + "> \"" + species + "\" .\n";
                }

            }

            if (!datasetURI.isEmpty()) {
                retTriples += "<" + datasetURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.datasetLabel + "> .\n";
                if (!datasetID.isEmpty()) {
                    retTriples += "<" + datasetURI + "> <" + Resources.rdfsLabel + "> \"" + datasetID + "\" .\n";
                }
            }
            if (!collectionURI.isEmpty()) {
                retTriples += "<" + collectionURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.collectionLabel + "> .\n";
                if (!collectionID.isEmpty()) {
                    retTriples += "<" + collectionURI + "> <" + Resources.rdfsLabel + "> \"" + collectionID + "\" .\n";
                }

            }

            if (!providerURI.isEmpty()) {
                retTriples += "<" + providerURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.personLabel + "> .\n";
                if (!providerID.isEmpty()) {
                    retTriples += "<" + providerURI + "> <" + Resources.rdfsLabel + "> \"" + providerID + "\" .\n";
                }
                if (!institutionURI.isEmpty()) {
                    retTriples += "<" + providerURI + "> <" + Resources.isCurrentMemberOf + "> <" + institutionURI + "> .\n";
                }
            }

            if (!institutionURI.isEmpty()) {
                retTriples += "<" + institutionURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.institutionLabel + "> .\n";
                if (!institution.isEmpty()) {
                    retTriples += "<" + institutionURI + "> <" + Resources.rdfsLabel + "> \"" + institution + "\" .\n";
                }

            }

            if (!dimensionURI.isEmpty()) {
                retTriples += "<" + dimensionURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.dimensionLabel + "> .\n";
                if (!dimensionTypeURI.isEmpty()) {
                    retTriples += "<" + dimensionURI + "> <" + Resources.hasType + "> <" + dimensionTypeURI + "> .\n";
                    if (!dimension.isEmpty()) {
                        retTriples += "<" + dimensionTypeURI + "> <" + Resources.rdfsLabel + "> \"" + dimension + "\" .\n";
                    }
                    if (!dimensionUnit.isEmpty()) {
                        retTriples += "<" + dimensionURI + "> <" + Resources.hasUnit + "> \"" + dimensionUnit + "\" .\n";

                    }
                    if (!dimensionValue.isEmpty()) {
                        retTriples += "<" + dimensionURI + "> <" + Resources.hasValue + "> \"" + dimensionValue + "\" .\n";

                    }
                }
            }
            retTriples += " } ";

            Queries.add(retTriples);

            //System.out.println(retTriples);
        }

        return Queries;

    }

    public ArrayList<String> transform_microct_specimen_csv(String fileToParse, String repositoryGraph, String datasetID) throws FileNotFoundException, IOException {

        BufferedReader fileReader = null;

        System.out.println(fileToParse);
        //Delimiter used in CSV file
        final String DELIMITER = ";";

        String line = "";
        String query = "";
        ArrayList<String> Queries = new ArrayList<String>();

        fileReader = new BufferedReader(new FileReader(fileToParse));
        String URIprefix = "http://www.lifewatchgreece.eu/entity";
        int count = 0;

        while ((line = fileReader.readLine()) != null) {

            if (count == 0) {
                count++;
                continue;
            }

            String[] tokens = line.split(DELIMITER);

            String specimen = tokens[0];
            //String specimenLabel = tokens[1];
            //String datasetID = tokens[2];
            String collectionID = tokens[3];
            String providerID = tokens[4];
            String institution = tokens[5];
            String description = tokens[6];
            //String material = tokens[7];
            String species = tokens[8];
            String dimension = "size";
            String dimensionValue = tokens[9];
            String dimensionUnit = "mm";

            //String fixationType = tokens[10];
            //String fixationNotes = tokens[11];
            //String preservationMedium = tokens[12];
            //String storagePlace = tokens[14];
            String specimenURI = createURI(URIprefix, specimen, "specimen");

            String collectionURI = "";
            if (!collectionID.isEmpty()) {
                collectionURI = createURI(URIprefix, collectionID, "collection");
            }

            String providerURI = "";
            if (!providerID.isEmpty()) {
                providerURI = createURI(URIprefix, providerID, "actor");
            }

            String institutionURI = "";
            if (!institution.isEmpty()) {
                institutionURI = createURI(URIprefix, institution, "actor");
            }

            String speciesURI = createURI(URIprefix, species, "species");

            //String SpecimenTypeURI = URIprefix+SpecimenTypeID.replace(" ","_")+">";
            //String fixationTypeURI = createURI(URIprefix, fixationType, "protocol");
            //String preservationTypeURI = createURI(URIprefix, preservationMedium, "protocol");
            String dimensionTypeURI = "";
            if (!dimension.isEmpty()) {
                dimensionTypeURI = createURI(URIprefix, dimension, "dimension");
            }

            String dimensionURI = "";
            if (!dimension.isEmpty()) {
                dimensionURI = createURI(URIprefix, dimension + specimen, "dimension");
            }

            String datasetURI = createURI(URIprefix, datasetID, "dataset");

            // String EcosystemURI = URIprefix+Ecosystem.replace(" ","_")+">";
            // String EquipmentURI = URIprefix+EquipmentType.replace(" ","_")+">";  
            // String station = URIprefix+occurrenceID+"station"+">";
            String retTriples = "INSERT INTO <" + repositoryGraph + "> { ";

            if (!specimenURI.isEmpty()) {
                retTriples += "<" + specimenURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.specimenLabel + "> .\n";
                if (!collectionURI.isEmpty()) {
                    retTriples += "<" + specimenURI + "> <" + Resources.formsPartOf + "> <" + collectionURI + "> .\n";
                }
                if (!providerURI.isEmpty()) {
                    retTriples += "<" + specimenURI + "> <" + Resources.wasProvidedBy + "> <" + providerURI + "> .\n";
                }
                if (!description.isEmpty()) {
                    retTriples += "<" + specimenURI + "> <" + Resources.hasNote + "> \"" + description + "\" .\n";
                }
                if (!speciesURI.isEmpty()) {
                    retTriples += "<" + specimenURI + "> <" + Resources.belongsTo + "> <" + speciesURI + "> .\n";
                }
//          if(!specimen.isEmpty()){
//                retTriples+= "<"+specimenURI+"> <"+Resources.hasType+"> <"+specimen+"> .\n";
//            }
                if (!dimensionURI.isEmpty()) {
                    retTriples += "<" + specimenURI + "> <" + Resources.hasDimension + "> <" + dimensionURI + "> .\n";
                }
                if (!datasetURI.isEmpty()) {
                    retTriples += "<" + datasetURI + "> <" + Resources.refersTo + "> <" + specimenURI + "> . \n";
                }
            }

            if (!specimenURI.isEmpty()) {
                retTriples += "<" + specimenURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.specimenLabel + "> .\n";
                if (!specimen.isEmpty()) {
                    retTriples += "<" + specimenURI + "> <" + Resources.rdfsLabel+ "> \"" + specimen + "\" .\n";
                }

            }

            if (!speciesURI.isEmpty()) {
                retTriples += "<" + speciesURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.speciesLabel + "> .\n";
                if (!species.isEmpty()) {
                    retTriples += "<" + speciesURI + "> <" + Resources.rdfsLabel + "> \"" + species + "\" .\n";
                }

            }

            if (!datasetURI.isEmpty()) {
                retTriples += "<" + datasetURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.datasetLabel + "> .\n";
                if (!datasetID.isEmpty()) {
                    retTriples += "<" + datasetURI + "> <" + Resources.rdfsLabel+ "> \"" + datasetID + "\" .\n";
                }
            }
            if (!collectionURI.isEmpty()) {
                retTriples += "<" + collectionURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.collectionLabel + "> .\n";
                if (!collectionID.isEmpty()) {
                    retTriples += "<" + collectionURI + "> <" + Resources.rdfsLabel + "> \"" + collectionID + "\" .\n";
                }

            }

            if (!providerURI.isEmpty()) {
                retTriples += "<" + providerURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.personLabel + "> .\n";
                if (!providerID.isEmpty()) {
                    retTriples += "<" + providerURI + "> <" + Resources.rdfsLabel + "> \"" + providerID + "\" .\n";
                }
                if (!institutionURI.isEmpty()) {
                    retTriples += "<" + providerURI + "> <" + Resources.isCurrentMemberOf + "> <" + institutionURI + "> .\n";
                }
            }

            if (!institutionURI.isEmpty()) {
                retTriples += "<" + institutionURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.institutionLabel + "> .\n";
                if (!institution.isEmpty()) {
                    retTriples += "<" + institutionURI + "> <" + Resources.rdfsLabel+ "> \"" + institution + "\" .\n";
                }

            }

            if (!dimensionURI.isEmpty()) {
                retTriples += "<" + dimensionURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.dimensionLabel + "> .\n";
                if (!dimensionTypeURI.isEmpty()) {
                    retTriples += "<" + dimensionURI + "> <" + Resources.hasType + "> <" + dimensionTypeURI + "> .\n";
                    if (!dimension.isEmpty()) {
                        retTriples += "<" + dimensionTypeURI + "> <" + Resources.rdfsLabel + "> \"" + dimension + "\" .\n";
                    }
                    if (!dimensionUnit.isEmpty()) {
                        retTriples += "<" + dimensionURI + "> <" + Resources.hasUnit + "> \"" + dimensionUnit + "\" .\n";

                    }
                    if (!dimensionValue.isEmpty()) {
                        retTriples += "<" + dimensionURI + "> <" + Resources.hasValue + "> \"" + dimensionValue + "\" .\n";

                    }
                }
            }
            retTriples += " } ";

            Queries.add(retTriples);

            //System.out.println(retTriples);
        }

        return Queries;
    }

    public ArrayList<String> transform_microct_scanning_csv(String fileToParse, String repositoryGraph) throws FileNotFoundException, IOException {

        //String fileToParse = "C:/uploads/TLOflat/identification.csv";
        BufferedReader fileReader = null;

        System.out.println(fileToParse);
        //Delimiter used in CSV file
        final String DELIMITER = ";";

        String line = "";
        String query = "";
        ArrayList<String> Queries = new ArrayList<String>();

        fileReader = new BufferedReader(new FileReader(fileToParse));
        String URIprefix = "http://www.lifewatchgreece.eu/entity";
        int count = 0;

        while ((line = fileReader.readLine()) != null) {

            if (count == 0) {
                count++;
                continue;
            }

            String[] tokens = line.split(DELIMITER);

            String specimenID = tokens[0];
            String scanningID = tokens[1];
            String datasetID = tokens[2];
            String contrastEnhancementMethod = tokens[3];
//            String preparationMethod = tokens[4];
//            String preparationTimespan = tokens[5] + " " + tokens[6] + "-" + tokens[7] + " " + tokens[8];
//            String preparationDescription = tokens[9];
//            String scopeOfScan = tokens[10];
            String objectOfType = tokens[11];
//            String scanningMethod = tokens[12];
//            String scannedPart = tokens[13];
            String actor = tokens[14];
            String timespan = tokens[15];
//            String scanningDuration = tokens[16];

            String deviceType = tokens[17];

//            String voltage_kV = tokens[18];
//            String current_uA = tokens[19];
//            String filter = tokens[20];
//            String zoom_um = tokens[21];
//            String cameraResolution = tokens[22];
//            String averaging = tokens[23];
//            String randomMovement = tokens[24];
//            String d360 = tokens[25];
//            String exposureTime_ms = tokens[26];
//            String oversizeSettings = tokens[27];
//            String scanNotes = tokens[28];
//            String fileLocation = tokens[29];
//            String scanFilesStatus = tokens[30];
            String productName = tokens[1] + ".zip";
            //          String preparationID = tokens[2] + "_prep";

            String specimenURI = createURI(URIprefix, specimenID, "specimen");
            String scanningURI = createURI(URIprefix, scanningID, "digitizationProcess");

            String scanningEventLabel = "Scanning of " + specimenID;
                    
//            String preparationURI = createURI(URIprefix, preparationID, "transformationEvent");
            String deviceURI = createURI(URIprefix, deviceType, "device");
//            String objectURI = createURI(URIprefix, objectOfType, "equipment");

            String actorURI = "";
            if (!actor.isEmpty()) {
                actorURI = createURI(URIprefix, actor, "actor");
            }
            
             if (contrastEnhancementMethod.isEmpty()) {
               contrastEnhancementMethod = "none";
            }

            String productURI = createURI(URIprefix, productName, "dataObject");

            String datasetURI = createURI(URIprefix, datasetID, "dataset");

            String retTriples = "INSERT INTO <" + repositoryGraph + "> { ";

            if (!scanningURI.isEmpty()) {
                retTriples += "<" + scanningURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.digitizationProcessLabel + "> .\n";
                if (!specimenURI.isEmpty()) {
                    retTriples += "<" + scanningURI + "> <" + Resources.digitized + "> <" + specimenURI + "> .\n";
                }
                if (!productURI.isEmpty()) {
                    retTriples += "<" + scanningURI + "> <" + Resources.createdDerivative + "> <" + productURI + "> .\n";
                }
                if (!deviceURI.isEmpty()) {
                    retTriples += "<" + scanningURI + "> <" + Resources.happenedOnDevice + "> <" + deviceURI + "> .\n";
                }
                
                if (!contrastEnhancementMethod.isEmpty()) {
                    retTriples += "<" + scanningURI + "> <" + Resources.hasContrastMethod + "> \"" + contrastEnhancementMethod + "\" .\n";
                }

                if (!timespan.isEmpty()) {
                    retTriples += "<" + scanningURI + "> <" + Resources.hasTimespan + "> \"" + timespan + "\" .\n";
                }
                if (!actorURI.isEmpty()) {
                    retTriples += "<" + scanningURI + "> <" + Resources.carriedOutBy + "> <" + actorURI + "> .\n";
                }
                if (!datasetURI.isEmpty()) {
                    retTriples += "<" + datasetURI + "> <" + Resources.refersTo + "> <" + scanningURI + "> . \n";
                }
                if (!scanningEventLabel.isEmpty()) {
                    retTriples += "<" + scanningURI + "> <" + Resources.rdfsLabel+ "> \"" + scanningEventLabel + "\" .\n";
                }
            }

            if (!specimenURI.isEmpty()) {
                retTriples += "<" + specimenURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.specimenLabel + "> .\n";
                if (!specimenID.isEmpty()) {
                    retTriples += "<" + specimenURI + "> <" + Resources.rdfsLabel + "> \"" + specimenID + "\" .\n";
                }

            }

            if (!datasetURI.isEmpty()) {
                retTriples += "<" + datasetURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.datasetLabel + "> .\n";
                if (!datasetID.isEmpty()) {
                    retTriples += "<" + datasetURI + "> <" + Resources.rdfsLabel + "> \"" + datasetID + "\" .\n";
                }
            }

            if (!productURI.isEmpty()) {
                retTriples += "<" + productURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.dataObjectLabel + "> .\n";
                if (!productName.isEmpty()) {
                    retTriples += "<" + productURI + "> <" + Resources.rdfsLabel + "> \"" + productName + "\" .\n";
                }
            }

            if (!deviceURI.isEmpty()) {
                retTriples += "<" + deviceURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.digitalDeviceLabel + "> .\n";
                if (!deviceType.isEmpty()) {
                    retTriples += "<" + deviceURI + "> <" + Resources.rdfsLabel+ "> \"" + deviceType + "\" .\n";
                }
//          if(!deviceType.isEmpty()){
//                retTriples+= "<"+deviceURI+"> <"+Resources.hasType+"> \""+deviceType+"\" .\n";
//            }
            }

            if (!actorURI.isEmpty()) {
                retTriples += "<" + actorURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.personLabel + "> .\n";
                if (!actor.isEmpty()) {
                    retTriples += "<" + actorURI + "> <" + Resources.rdfsLabel + "> \"" + actor + "\" .\n";
                }

            }

            retTriples += " } ";

            Queries.add(retTriples);

            //System.out.println(retTriples);
        }

        return Queries;

    }

    public ArrayList<String> transform_microct_scanning_csv(String fileToParse, String repositoryGraph,String datasetID) throws FileNotFoundException, IOException {

        //String fileToParse = "C:/uploads/TLOflat/identification.csv";
        BufferedReader fileReader = null;

        System.out.println(fileToParse);
        //Delimiter used in CSV file
        final String DELIMITER = ";";

        String line = "";
        String query = "";
        ArrayList<String> Queries = new ArrayList<String>();

        fileReader = new BufferedReader(new FileReader(fileToParse));
        String URIprefix = "http://www.lifewatchgreece.eu/entity";
        int count = 0;

        while ((line = fileReader.readLine()) != null) {

            if (count == 0) {
                count++;
                continue;
            }

            String[] tokens = line.split(DELIMITER);

            String specimenID = tokens[0];
            String scanningID = tokens[1];
            //  String datasetID = tokens[2];
            String contrastEnhancementMethod = tokens[3];
//            String preparationMethod = tokens[4];
//            String preparationTimespan = tokens[5] + " " + tokens[6] + "-" + tokens[7] + " " + tokens[8];
//            String preparationDescription = tokens[9];
//            String scopeOfScan = tokens[10];
            String objectOfType = tokens[11];
//            String scanningMethod = tokens[12];
//            String scannedPart = tokens[13];
            String actor = tokens[14];
            String timespan = tokens[15];
//            String scanningDuration = tokens[16];

            String deviceType = tokens[17];

//            String voltage_kV = tokens[18];
//            String current_uA = tokens[19];
//            String filter = tokens[20];
//            String zoom_um = tokens[21];
//            String cameraResolution = tokens[22];
//            String averaging = tokens[23];
//            String randomMovement = tokens[24];
//            String d360 = tokens[25];
//            String exposureTime_ms = tokens[26];
//            String oversizeSettings = tokens[27];
//            String scanNotes = tokens[28];
//            String fileLocation = tokens[29];
//            String scanFilesStatus = tokens[30];
            String productName = tokens[1] + ".zip";
            //          String preparationID = tokens[2] + "_prep";

            String scanningEventLabel = "Scanning of " + specimenID;
             
            String specimenURI = createURI(URIprefix, specimenID, "specimen");
            String scanningURI = createURI(URIprefix, scanningID, "digitizationProcess");

//            String preparationURI = createURI(URIprefix, preparationID, "transformationEvent");
            String deviceURI = createURI(URIprefix, deviceType, "device");
//            String objectURI = createURI(URIprefix, objectOfType, "equipment");

            String actorURI = "";
            if (!actor.isEmpty()) {
                actorURI = createURI(URIprefix, actor, "actor");
            }

            if (contrastEnhancementMethod.isEmpty()) {
               contrastEnhancementMethod = "none";
            }
            
            String productURI = createURI(URIprefix, productName, "dataObject");

            String datasetURI = createURI(URIprefix, datasetID, "dataset");

            String retTriples = "INSERT INTO <" + repositoryGraph + "> { ";

            if (!scanningURI.isEmpty()) {
                retTriples += "<" + scanningURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.digitizationProcessLabel + "> .\n";
                if (!specimenURI.isEmpty()) {
                    retTriples += "<" + scanningURI + "> <" + Resources.digitized + "> <" + specimenURI + "> .\n";
                }
                if (!productURI.isEmpty()) {
                    retTriples += "<" + scanningURI + "> <" + Resources.createdDerivative + "> <" + productURI + "> .\n";
                }
                if (!deviceURI.isEmpty()) {
                    retTriples += "<" + scanningURI + "> <" + Resources.happenedOnDevice + "> <" + deviceURI + "> .\n";
                }

                if (!timespan.isEmpty()) {
                    retTriples += "<" + scanningURI + "> <" + Resources.hasTimespan + "> \"" + timespan + "\" .\n";
                }
                if (!actorURI.isEmpty()) {
                    retTriples += "<" + scanningURI + "> <" + Resources.carriedOutBy + "> <" + actorURI + "> .\n";
                }
                
                if (!contrastEnhancementMethod.isEmpty()) {
                    retTriples += "<" + scanningURI + "> <" + Resources.hasContrastMethod + "> \"" + contrastEnhancementMethod + "\" .\n";
                }
                
                if (!datasetURI.isEmpty()) {
                    retTriples += "<" + datasetURI + "> <" + Resources.refersTo + "> <" + scanningURI + "> . \n";
                }
                if (!scanningEventLabel.isEmpty()) {
                    retTriples += "<" + scanningURI + "> <" + Resources.rdfsLabel+ "> \"" + scanningEventLabel + "\" .\n";
                }
            }

            if (!specimenURI.isEmpty()) {
                retTriples += "<" + specimenURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.specimenLabel + "> .\n";
                if (!specimenID.isEmpty()) {
                    retTriples += "<" + specimenURI + "> <" + Resources.rdfsLabel + "> \"" + specimenID + "\" .\n";
                }

            }

            if (!datasetURI.isEmpty()) {
                retTriples += "<" + datasetURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.datasetLabel + "> .\n";
                if (!datasetID.isEmpty()) {
                    retTriples += "<" + datasetURI + "> <" + Resources.rdfsLabel + "> \"" + datasetID + "\" .\n";
                }
            }

            if (!productURI.isEmpty()) {
                retTriples += "<" + productURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.dataObjectLabel + "> .\n";
                if (!productName.isEmpty()) {
                    retTriples += "<" + productURI + "> <" + Resources.rdfsLabel + "> \"" + productName + "\" .\n";
                }
            }

            if (!deviceURI.isEmpty()) {
                retTriples += "<" + deviceURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.digitalDeviceLabel + "> .\n";
                if (!deviceType.isEmpty()) {
                    retTriples += "<" + deviceURI + "> <" + Resources.rdfsLabel+ "> \"" + deviceType + "\" .\n";
                }
//          if(!deviceType.isEmpty()){
//                retTriples+= "<"+deviceURI+"> <"+Resources.hasType+"> \""+deviceType+"\" .\n";
//            }
            }

            if (!actorURI.isEmpty()) {
                retTriples += "<" + actorURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.personLabel + "> .\n";
                if (!actor.isEmpty()) {
                    retTriples += "<" + actorURI + "> <" + Resources.rdfsLabel + "> \"" + actor + "\" .\n";
                }

            }

            retTriples += " } ";

            Queries.add(retTriples);

            //System.out.println(retTriples);
        }

        return Queries;

    }

    public ArrayList<String> transform_microct_reconstruction_csv(String fileToParse, String repositoryGraph) throws FileNotFoundException, IOException {

        //String fileToParse = "C:/uploads/TLOflat/identification.csv";
        BufferedReader fileReader = null;

        System.out.println(fileToParse);
        //Delimiter used in CSV file
        final String DELIMITER = ";";

        String line = "";
        String query = "";
        ArrayList<String> Queries = new ArrayList<String>();

        fileReader = new BufferedReader(new FileReader(fileToParse));
        String URIprefix = "http://www.lifewatchgreece.eu/entity";
        int count = 0;

        while ((line = fileReader.readLine()) != null) {

            if (count == 0) {
                count++;
                continue;
            }

            String[] tokens = line.split(DELIMITER);

            String scanningID = tokens[0];
            String reconstructionID = tokens[1];
            String datasetName = tokens[2];
            //String reconScope = tokens[3];
            String actor = tokens[4];
            String timespan = tokens[5];
            //String duration = tokens[6];
            String description = tokens[7];
            //String cleanup = tokens[8];
            //String results = tokens[9];
            //String reconFilesStatus = tokens[10];
            String inputName = tokens[0] + ".zip";
            String productName = tokens[1] + ".zip";

            String scanningURI = createURI(URIprefix, scanningID, "digitizationProcess");
            String inputURI = createURI(URIprefix, inputName, "dataObject");
            String reconstructionURI = createURI(URIprefix, reconstructionID, "formalDerivation");
            String productURI = createURI(URIprefix, productName, "dataObject");

            String reconstructionEventLabel = "Reconstruction of "+inputName;
            
            String actorURI = "";
            if (!actor.isEmpty()) {
                actorURI = createURI(URIprefix, actor, "actor");
            }
            String datasetURI = createURI(URIprefix, datasetName, "dataset");

            String retTriples = "INSERT INTO <" + repositoryGraph + "> { ";
            if (!reconstructionURI.isEmpty()) {
                retTriples += "<" + reconstructionURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.formalDerivationEventLabel + "> .\n";
                retTriples += "<" + reconstructionURI + "> <" + Resources.hasType + "> \"Reconstruction\" .\n";

                if (!productURI.isEmpty()) {
                    retTriples += "<" + reconstructionURI + "> <" + Resources.createdDerivative + "> <" + productURI + "> .\n";
                }
//             if(!deviceURI.isEmpty()){
//                retTriples+= "<"+scanningURI+"> <"+Resources.happenedOnDevice+"> <"+deviceURI+"> .\n";
//            }

                if (!inputURI.isEmpty()) {
                    retTriples += "<" + inputURI + "> <" + Resources.wasDerivationSourceFor + "> <" + reconstructionURI + "> .\n";
                }

                if (!datasetURI.isEmpty()) {
                    retTriples += "<" + datasetURI + "> <" + Resources.refersTo + "> <" + reconstructionURI + "> .\n";
                }

                if (!timespan.isEmpty()) {
                    retTriples += "<" + reconstructionURI + "> <" + Resources.hasTimespan + "> \"" + timespan + "\" .\n";
                }
                if (!actorURI.isEmpty()) {
                    retTriples += "<" + reconstructionURI + "> <" + Resources.carriedOutBy + "> <" + actorURI + "> .\n";
                }
                if (!description.isEmpty()) {
                    retTriples += "<" + reconstructionURI + "> <" + Resources.hasNote + "> \"" + description + "\" .\n";
                }   
                if (!reconstructionEventLabel.isEmpty()) {
                    retTriples += "<" + reconstructionURI + "> <" + Resources.rdfsLabel+ "> \"" + reconstructionEventLabel + "\" .\n";
                }
            }

            if (!actorURI.isEmpty()) {
                retTriples += "<" + actorURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.personLabel + "> .\n";
                if (!actor.isEmpty()) {
                    retTriples += "<" + actorURI + "> <" + Resources.rdfsLabel + "> \"" + actor + "\" .\n";
                }

            }

            if (!datasetURI.isEmpty()) {
                retTriples += "<" + datasetURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.datasetLabel + "> .\n";
                if (!datasetName.isEmpty()) {
                    retTriples += "<" + datasetURI + "> <" + Resources.rdfsLabel + "> \"" + datasetName + "\" .\n";
                }

            }

            if (!productURI.isEmpty()) {
                retTriples += "<" + productURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.dataObjectLabel + "> .\n";
                if (!productName.isEmpty()) {
                    retTriples += "<" + productURI + "> <" + Resources.rdfsLabel + "> \"" + productName + "\" .\n";
                }
            }

            if (!inputURI.isEmpty()) {
                retTriples += "<" + inputURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.dataObjectLabel + "> .\n";
                if (!inputName.isEmpty()) {
                    retTriples += "<" + inputURI + "> <" + Resources.rdfsLabel + "> \"" + inputName + "\" .\n";
                }
            }

            retTriples += " } ";

            Queries.add(retTriples);

        }

        return Queries;

    }

    public ArrayList<String> transform_microct_reconstruction_csv(String fileToParse, String repositoryGraph, String datasetName) throws FileNotFoundException, IOException {

        //String fileToParse = "C:/uploads/TLOflat/identification.csv";
        BufferedReader fileReader = null;

        System.out.println(fileToParse);
        //Delimiter used in CSV file
        final String DELIMITER = ";";

        String line = "";
        String query = "";
        ArrayList<String> Queries = new ArrayList<String>();

        fileReader = new BufferedReader(new FileReader(fileToParse));
        String URIprefix = "http://www.lifewatchgreece.eu/entity";
        int count = 0;

        while ((line = fileReader.readLine()) != null) {

            if (count == 0) {
                count++;
                continue;
            }

            String[] tokens = line.split(DELIMITER);

            String scanningID = tokens[0];
            String reconstructionID = tokens[1];
            //String datasetName = tokens[2];
            //String reconScope = tokens[3];
            String actor = tokens[4];
            String timespan = tokens[5];
            //String duration = tokens[6];
            String description = tokens[7];
            //String cleanup = tokens[8];
            //String results = tokens[9];
            //String reconFilesStatus = tokens[10];
            String inputName = tokens[0] + ".zip";
            String productName = tokens[1] + ".zip";

            String scanningURI = createURI(URIprefix, scanningID, "digitizationProcess");
            String inputURI = createURI(URIprefix, inputName, "dataObject");
            String reconstructionURI = createURI(URIprefix, reconstructionID, "formalDerivation");
            String productURI = createURI(URIprefix, productName, "dataObject");

            String reconstructionEventLabel = "Reconstruction of "+inputName;
            
            String actorURI = "";
            if (!actor.isEmpty()) {
                actorURI = createURI(URIprefix, actor, "actor");
            }
            String datasetURI = createURI(URIprefix, datasetName, "dataset");

            String retTriples = "INSERT INTO <" + repositoryGraph + "> { ";
            if (!reconstructionURI.isEmpty()) {
                retTriples += "<" + reconstructionURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.formalDerivationEventLabel + "> .\n";
                retTriples += "<" + reconstructionURI + "> <" + Resources.hasType + "> \"Reconstruction\" .\n";

                if (!productURI.isEmpty()) {
                    retTriples += "<" + reconstructionURI + "> <" + Resources.createdDerivative + "> <" + productURI + "> .\n";
                }
//             if(!deviceURI.isEmpty()){
//                retTriples+= "<"+scanningURI+"> <"+Resources.happenedOnDevice+"> <"+deviceURI+"> .\n";
//            }

                if (!inputURI.isEmpty()) {
                    retTriples += "<" + inputURI + "> <" + Resources.wasDerivationSourceFor + "> <" + reconstructionURI + "> .\n";
                }

                if (!datasetURI.isEmpty()) {
                    retTriples += "<" + datasetURI + "> <" + Resources.refersTo + "> <" + reconstructionURI + "> .\n";
                }

                if (!timespan.isEmpty()) {
                    retTriples += "<" + reconstructionURI + "> <" + Resources.hasTimespan + "> \"" + timespan + "\" .\n";
                }
                if (!actorURI.isEmpty()) {
                    retTriples += "<" + reconstructionURI + "> <" + Resources.carriedOutBy + "> <" + actorURI + "> .\n";
                }
                if (!description.isEmpty()) {
                    retTriples += "<" + reconstructionURI + "> <" + Resources.hasNote + "> \"" + description + "\" .\n";
                }
                if (!reconstructionEventLabel.isEmpty()) {
                    retTriples += "<" + reconstructionURI + "> <" + Resources.rdfsLabel+ "> \"" + reconstructionEventLabel + "\" .\n";
                }
            }

            if (!actorURI.isEmpty()) {
                retTriples += "<" + actorURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.personLabel + "> .\n";
                if (!actor.isEmpty()) {
                    retTriples += "<" + actorURI + "> <" + Resources.rdfsLabel + "> \"" + actor + "\" .\n";
                }

            }

            if (!datasetURI.isEmpty()) {
                retTriples += "<" + datasetURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.datasetLabel + "> .\n";
                if (!datasetName.isEmpty()) {
                    retTriples += "<" + datasetURI + "> <" + Resources.rdfsLabel + "> \"" + datasetName + "\" .\n";
                }

            }

            if (!productURI.isEmpty()) {
                retTriples += "<" + productURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.dataObjectLabel + "> .\n";
                if (!productName.isEmpty()) {
                    retTriples += "<" + productURI + "> <" + Resources.rdfsLabel + "> \"" + productName + "\" .\n";
                }
            }

            if (!inputURI.isEmpty()) {
                retTriples += "<" + inputURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.dataObjectLabel + "> .\n";
                if (!inputName.isEmpty()) {
                    retTriples += "<" + inputURI + "> <" + Resources.rdfsLabel + "> \"" + inputName + "\" .\n";
                }
            }

            retTriples += " } ";

            Queries.add(retTriples);

        }

        return Queries;
    }

    public ArrayList<String> transform_microct_postprocessing_csv(String fileToParse, String repositoryGraph) throws FileNotFoundException, IOException {

        //String fileToParse = "C:/uploads/TLOflat/identification.csv";
        BufferedReader fileReader = null;

        System.out.println(fileToParse);
        //Delimiter used in CSV file
        final String DELIMITER = ";";

        String line = "";
        String query = "";
        ArrayList<String> Queries = new ArrayList<String>();

        fileReader = new BufferedReader(new FileReader(fileToParse));
        String URIprefix = "http://www.lifewatchgreece.eu/entity";
        int count = 0;

        while ((line = fileReader.readLine()) != null) {

            if (count == 0) {
                count++;
                continue;
            }

            String[] tokens = line.split(DELIMITER);

            String postProcessingID = tokens[1];
            String reconstructionID = tokens[0];
            String datasetName = tokens[2];
            //String subvolumeScope = tokens[3];
            String actor = tokens[4];
            String description = tokens[5];
            //String fileStatus = tokens[6];
            String productName = tokens[1] + ".zip";
            String inputName = tokens[0] + ".zip";

            String datasetURI = createURI(URIprefix, datasetName, "dataset");
            String recostructionURI = createURI(URIprefix, reconstructionID, "formalDerivation");
            String postProcessingURI = createURI(URIprefix, postProcessingID, "formalDerivation");
            String productURI = createURI(URIprefix, productName, "dataObject");
            String inputURI = createURI(URIprefix, inputName, "dataObject");

            String postProcessingEventLabel = "Post Processing of "+inputName;
                    
            String actorURI = "";
            if (!actor.isEmpty()) {
                actorURI = createURI(URIprefix, actor, "actor");
            }
            String retTriples = "INSERT INTO <" + repositoryGraph + "> { ";

            if (!postProcessingURI.isEmpty()) {
                retTriples += "<" + postProcessingURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.formalDerivationEventLabel + "> .\n";
                retTriples += "<" + postProcessingURI + "> <" + Resources.hasType + "> \"PostProcessing\" .\n";
                if (!productURI.isEmpty()) {
                    retTriples += "<" + postProcessingURI + "> <" + Resources.hasCreated + "> <" + productURI + "> .\n";
                }

                if (!inputURI.isEmpty()) {
                    retTriples += "<" + inputURI + "> <" + Resources.wasDerivationSourceFor + "> <" + postProcessingURI + "> .\n";
                }
                if (!datasetURI.isEmpty()) {
                    retTriples += "<" + datasetURI + "> <" + Resources.refersTo + "> <" + postProcessingURI + "> .\n";
                }

//            if(!timespan.isEmpty()){
//                retTriples+= "<"+reconstructionURI+"> <"+Resources.hasTimespan+"> \""+timespan+"\" .\n";
//            }
                if (!actorURI.isEmpty()) {
                    retTriples += "<" + postProcessingURI + "> <" + Resources.carriedOutBy + "> <" + actorURI + "> .\n";
                }
                if (!description.isEmpty()) {
                    retTriples += "<" + postProcessingURI + "> <" + Resources.hasNote + "> \"" + description + "\" .\n";
                }
                if (!postProcessingEventLabel.isEmpty()) {
                    retTriples += "<" + postProcessingURI + "> <" + Resources.rdfsLabel+ "> \"" + postProcessingEventLabel + "\" .\n";
                }
            }

            if (!productURI.isEmpty()) {
                retTriples += "<" + productURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.dataObjectLabel + "> .\n";
                if (!productName.isEmpty()) {
                    retTriples += "<" + productURI + "> <" + Resources.rdfsLabel+ "> \"" + productName + "\" .\n";
                }
            }

            if (!inputURI.isEmpty()) {
                retTriples += "<" + inputURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.dataObjectLabel + "> .\n";
                if (!inputName.isEmpty()) {
                    retTriples += "<" + inputURI + "> <" + Resources.rdfsLabel + "> \"" + inputName + "\" .\n";
                }
            }

            if (!actorURI.isEmpty()) {
                retTriples += "<" + actorURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.personLabel + "> .\n";
                if (!actor.isEmpty()) {
                    retTriples += "<" + actorURI + "> <" + Resources.rdfsLabel + "> \"" + actor + "\" .\n";
                }
            }

            if (!datasetURI.isEmpty()) {
                retTriples += "<" + datasetURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.datasetLabel + "> .\n";
                if (!datasetName.isEmpty()) {
                    retTriples += "<" + datasetURI + "> <" + Resources.rdfsLabel + "> \"" + datasetName + "\" .\n";
                }
            }

            retTriples += " } ";

            Queries.add(retTriples);

            //System.out.println(retTriples);
        }

        return Queries;
    }

    public ArrayList<String> transform_microct_postprocessing_csv(String fileToParse, String repositoryGraph,String datasetName) throws FileNotFoundException, IOException {

        //String fileToParse = "C:/uploads/TLOflat/identification.csv";
        BufferedReader fileReader = null;

        System.out.println(fileToParse);
        //Delimiter used in CSV file
        final String DELIMITER = ";";

        String line = "";
        String query = "";
        ArrayList<String> Queries = new ArrayList<String>();

        fileReader = new BufferedReader(new FileReader(fileToParse));
        String URIprefix = "http://www.lifewatchgreece.eu/entity";
        int count = 0;

        while ((line = fileReader.readLine()) != null) {

            if (count == 0) {
                count++;
                continue;
            }

            String[] tokens = line.split(DELIMITER);

            String postProcessingID = tokens[1];
            String reconstructionID = tokens[0];
            // String datasetName=tokens[2];
            //String subvolumeScope = tokens[3];
            String actor = tokens[4];
            String description = tokens[5];
            //String fileStatus = tokens[6];
            String productName = tokens[1] + ".zip";
            String inputName = tokens[0] + ".zip";

            String datasetURI = createURI(URIprefix, datasetName, "dataset");
            String recostructionURI = createURI(URIprefix, reconstructionID, "formalDerivation");
            String postProcessingURI = createURI(URIprefix, postProcessingID, "formalDerivation");
            String productURI = createURI(URIprefix, productName, "dataObject");
            String inputURI = createURI(URIprefix, inputName, "dataObject");

            String postProcessingEventLabel = "Post Processing of "+inputName;
            
            String actorURI = "";
            if (!actor.isEmpty()) {
                actorURI = createURI(URIprefix, actor, "actor");
            }
            String retTriples = "INSERT INTO <" + repositoryGraph + "> { ";

            if (!postProcessingURI.isEmpty()) {
                retTriples += "<" + postProcessingURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.formalDerivationEventLabel + "> .\n";
                retTriples += "<" + postProcessingURI + "> <" + Resources.hasType + "> \"PostProcessing\" .\n";
                if (!productURI.isEmpty()) {
                    retTriples += "<" + postProcessingURI + "> <" + Resources.hasCreated + "> <" + productURI + "> .\n";
                }

                if (!inputURI.isEmpty()) {
                    retTriples += "<" + inputURI + "> <" + Resources.wasDerivationSourceFor + "> <" + postProcessingURI + "> .\n";
                }
                if (!datasetURI.isEmpty()) {
                    retTriples += "<" + datasetURI + "> <" + Resources.refersTo + "> <" + postProcessingURI + "> .\n";
                }

//            if(!timespan.isEmpty()){
//                retTriples+= "<"+reconstructionURI+"> <"+Resources.hasTimespan+"> \""+timespan+"\" .\n";
//            }
                if (!actorURI.isEmpty()) {
                    retTriples += "<" + postProcessingURI + "> <" + Resources.carriedOutBy + "> <" + actorURI + "> .\n";
                }
                if (!description.isEmpty()) {
                    retTriples += "<" + postProcessingURI + "> <" + Resources.hasNote + "> \"" + description + "\" .\n";
                }
                if (!postProcessingEventLabel.isEmpty()) {
                    retTriples += "<" + postProcessingURI + "> <" + Resources.rdfsLabel+ "> \"" + postProcessingEventLabel + "\" .\n";
                }
            }

            if (!productURI.isEmpty()) {
                retTriples += "<" + productURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.dataObjectLabel + "> .\n";
                if (!productName.isEmpty()) {
                    retTriples += "<" + productURI + "> <" + Resources.rdfsLabel + "> \"" + productName + "\" .\n";
                }
            }

            if (!inputURI.isEmpty()) {
                retTriples += "<" + inputURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.dataObjectLabel + "> .\n";
                if (!inputName.isEmpty()) {
                    retTriples += "<" + inputURI + "> <" + Resources.rdfsLabel + "> \"" + inputName + "\" .\n";
                }
            }

            if (!actorURI.isEmpty()) {
                retTriples += "<" + actorURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.personLabel + "> .\n";
                if (!actor.isEmpty()) {
                    retTriples += "<" + actorURI + "> <" + Resources.rdfsLabel + "> \"" + actor + "\" .\n";
                }
            }

            if (!datasetURI.isEmpty()) {
                retTriples += "<" + datasetURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.datasetLabel + "> .\n";
                if (!datasetName.isEmpty()) {
                    retTriples += "<" + datasetURI + "> <" + Resources.rdfsLabel+ "> \"" + datasetName + "\" .\n";
                }
            }

            retTriples += " } ";

            Queries.add(retTriples);

            //System.out.println(retTriples);
        }

        return Queries;

    }

//    
//    public void transformDwCA2TLO(String dwcAFileName, String dwcAFilePath, String outputFileFullPath) throws IOException {
//              File tempFolder = new File(dwcAFilePath);
//            Archive arch = ArchiveFactory.openArchive(new File(tempFolder+dwcAFileName),tempFolder);
//            String URIprefix ="<http://lifewatch.gr/entity#";
//             
//                Iterator<StarRecord> iter = arch.iterator();
//                FileWriter writer = new FileWriter(outputFileFullPath);
//                StarRecord dwc;
//                while(iter.hasNext()){
//                    
//                    dwc = iter.next();
//                    String scientificName=dwc.core().value(DwcTerm.scientificName);
//                    String locality=dwc.core().value(DwcTerm.locality);
//                    String waterarea=dwc.core().value(DwcTerm.waterBody);
//                    String country=dwc.core().value(DwcTerm.country);
//                    String recorder=dwc.core().value(DwcTerm.recordedBy);
//                    String dataset=dwc.core().value(DwcTerm.datasetID);
//                    String date=dwc.core().value(DwcTerm.eventDate);
//                    String specimenID=dwc.core().value(DwcTerm.catalogNumber);
//                    String occurenceID=dwc.core().value(DwcTerm.recordNumber);
//                
//                
//                    String speciesURI = createURI(URIprefix,datasetID);
//                    String localityURI = createURI(URIprefix,datasetID);
//                    String waterareaURI = createURI(URIprefix,datasetID);
//                    String countryURI = createURI(URIprefix,datasetID);
//                    String recorderURI = createURI(URIprefix,datasetID);
//                    String datasetURI = createURI(URIprefix,datasetID);
//                    String specimenURI = createURI(URIprefix,datasetID);
//                    String occurenceURI = createURI(URIprefix,datasetID);
//                       
//                String occurenceQuery=
//                occurenceURI+" <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.ics.forth.gr/isl/MarineTLO/v4/marinetlo.owl#S40_encounter_event> .\n"+
//                occurenceURI+" <http://www.ics.forth.gr/isl/MarineTLO/v4/marinetlo.owl#LC5_has_time_span> \""+date+"\" . \n"+
//                occurenceURI+" <http://www.ics.forth.gr/isl/MarineTLO/v4/marinetlo.owl#LC15_took_place_at> "+localityURI+" .\n"+
//                occurenceURI+" <http://www.ics.forth.gr/isl/MarineTLO/v4/marinetlo.owl#LC13_is_carried_out_by> "+recorderURI+" .\n"+
//                occurenceURI+" <http://www.ics.forth.gr/isl/MarineTLO/v4/marinetlo.owl#has_found_object> "+specimenURI+" .\n"+
//                localityURI+" <http://www.ics.forth.gr/isl/MarineTLO/v4/marinetlo.owl#LC27_is_located_on_or_within> "+countryURI+" .\n"+
//                localityURI+" <http://www.ics.forth.gr/isl/MarineTLO/v4/marinetlo.owl#LC4_has_appellation> \""+locality+"\" .\n"+                
//                countryURI+" <http://www.ics.forth.gr/isl/MarineTLO/v4/marinetlo.owl#LC4_has_appellation> \""+country+"\" .\n"+ 
//                datasetURI+" <http://www.ics.forth.gr/isl/MarineTLO/v4/marinetlo.owl#LC2_refers_to> "+occurenceURI+" .\n ";
//                writer.write(occurenceQuery);
//               // System.out.println(occurenceQuery);
//
//                }
//                
//                
//                   writer.close();
//                   
//
//}
//     public void transformCsv2TLO(String csvFileName, String csvFilePath, String outputFileFullPath) throws FileNotFoundException, IOException {
//        // TODO code application logic here
//        
//          //Input file which needs to be parsed
//        String fileToParse = csvFilePath+csvFileName;
//        BufferedReader fileReader = null;
//         
//      
//        //Delimiter used in CSV file
//        final String DELIMITER = ",";
//    
//        String line = "";
//        String query="";
//        FileWriter writer = new FileWriter(outputFileFullPath);
//            //Create the file reader
//        fileReader = new BufferedReader(new FileReader(fileToParse));
//        String URIprefix ="<http://lifewatch.gr/entity#";
//        int count=0;
//            //Read the file line by line
//        while ((line = fileReader.readLine()) != null) 
//        {
//                //Get all tokens available in line
//             if(count==0)
//             {
//                 count++;
//                 continue;  
//             }
//             String[] tokens = line.split(DELIMITER);
////                for(String token : tokens)
////                {
//                    //Print all tokens
//             String Identification=URIprefix+tokens[0]+">";
//             String Actor=URIprefix+tokens[1].replace(" ","_")+">";
//             String Date=tokens[2];
//             String Specimen = URIprefix+tokens[3]+">";
//             String Species = URIprefix+tokens[4].replace(" ","_")+">";
//             String Proposition = URIprefix+tokens[5].replace(" ","")+">";
//             String Country = URIprefix+tokens[6].replace(" ","_")+">";       
//             
//             query=
//                    Identification+" <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.ics.forth.gr/isl/MarineTLO/v4/marinetlo.owl#E17_Type_Assignment> . \n"+
//                    Identification+" <http://www.ics.forth.gr/isl/MarineTLO/v4/marinetlo.owl#LC5_has_time_span> \""+Date+"\" . \n"+
//                    Identification+" <http://www.ics.forth.gr/isl/MarineTLO/v4/marinetlo.owl#LC13_is_carried_out_by> "+Actor+" .\n"+
//                    Actor+" <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.ics.forth.gr/isl/MarineTLO/v4/marinetlo.owl#BC8_Actor> . "+
//                    Actor+" <http://www.ics.forth.gr/isl/MarineTLO/v4/marinetlo.owl#LC4_has_appellation> \""+tokens[1]+"\" . "+
//                     
//                    Identification+" <http://www.ics.forth.gr/isl/MarineTLO/v4/marinetlo.owl#LC47_took_place_at> "+Country+" .\n"+
//                    Country+" <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.ics.forth.gr/isl/MarineTLO/v4/marinetlo.owl#Country> . "+
//                    Country+" <http://www.ics.forth.gr/isl/MarineTLO/v4/marinetlo.owl#LC4_has_appellation> \""+tokens[6]+"\" . "+ 
//                      
//                    Identification+" <http://www.ics.forth.gr/isl/MarineTLO/v4/marinetlo.owl#classified> "+Specimen+" .\n"+
//                    Specimen+" <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.ics.forth.gr/isl/MarineTLO/v4/marinetlo.owl#BC53_Specimen> . "+
//                     
//                    Identification+" <http://www.ics.forth.gr/isl/MarineTLO/v4/marinetlo.owl#assigned_type> "+Species+" .\n"+
//                    Species+" <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.ics.forth.gr/isl/MarineTLO/v4/marinetlo.owl#BT27_Species> . "+
//
//                    Proposition+" <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.ics.forth.gr/isl/MarineTLO/v4/marinetlo.owl#BC18_Proposition> . "+
//                    Identification+" <http://www.ics.forth.gr/isl/MarineTLO/v4/marinetlo.owl#used_object_of_type> "+Proposition+" .\n";
//
//                     writer.write(query);
//                    
//                  //  System.out.println(query);
//               // }
//            } 
//        writer.close();
//        }  

    /*Not sure if and where we are going to need them */
    public String createURI(String prefix, String Resource) {
        String newResource;
        newResource = "" + prefix + "/" + Resource.replace(" ", "_").toLowerCase() + "";
        return newResource;
    }

    /*Not sure if and where we are going to need them */
    public String createURI(String prefix, String Resource, String type) {
        String newResource;
        newResource = "" + prefix + "/" + type + "/" + Resource.replace(" ", "_").toLowerCase() + "";
        return newResource;
    }

    /*Not sure if and where we are going to need them */
    public String createHashURI(String prefix, String Resource) {
        String newResource;
        newResource = Resource.replace(" ", "_").toLowerCase();
        newResource = "<" + prefix + "/" + newResource.hashCode() + ">";
        return newResource;
    }
}
