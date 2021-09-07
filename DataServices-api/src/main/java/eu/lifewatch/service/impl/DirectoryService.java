package eu.lifewatch.service.impl;

import eu.lifewatch.common.ResourceType;
import static eu.lifewatch.common.ResourceType.LITERAL;
import eu.lifewatch.core.api.RepositoryManager;
import eu.lifewatch.core.model.DirectoryStruct;
import java.io.File;
import java.util.ArrayList;
import org.openrdf.query.BindingSet;
import org.openrdf.rio.RDFFormat;
import java.net.URISyntaxException;
import java.util.List;
import java.net.URI;
import eu.lifewatch.common.Resources;
import eu.lifewatch.core.model.Triple;
import eu.lifewatch.exception.DataImportException;
import eu.lifewatch.exception.QueryExecutionException;
import eu.lifewatch.exception.URIValidationException;
import eu.lifewatch.service.api.Service;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;

/**
 * DirectoryService provides the methods to search, add, delete and update
 * information in a particular repository.
 *
 * @author Nikos Minadakis (minadakn 'at' ics 'dot' forth 'dot' gr)
 * @author Yannis Marketakis (marketak 'at' ics 'dot' forth 'dot' gr)
 */
public class DirectoryService implements Service {

    private final RepositoryManager repoManager;
    private static final Logger logger = Logger.getLogger(DirectoryService.class);

    public DirectoryService(RepositoryManager repositoryManager) {
        this.repoManager = repositoryManager;
    }

    /**
     * Imports the the file found in the given file path in the repository under
     * the given graphspace.
     *
     * @param fileFullPath the path of the file that will be imported
     * @param directoryGraph the graphspace that will be used
     * @throws DataImportException is thrown if any error is occurred
     */
    @Override
    public void importFile(String fileFullPath, String directoryGraph) throws DataImportException {
        File file = new File(fileFullPath);
        if (!file.exists()) {
            throw new DataImportException("The given file (\"" + fileFullPath + "\") does not exist");
        }
        RDFFormat chosenFormat = RDFFormat.NTRIPLES;
        if (file.getAbsolutePath().toLowerCase().endsWith(Resources.rdfDefaultExtension)) {
            chosenFormat = RDFFormat.RDFXML;
            logger.debug("Recognized the format of the file (" + file.getName() + "): " + chosenFormat);
        } else if (file.getAbsolutePath().toLowerCase().endsWith(Resources.ntriplesDefaultExtension1)
                || file.getAbsolutePath().toLowerCase().endsWith(Resources.ntriplesDefaultExtension2)) {
            chosenFormat = RDFFormat.NTRIPLES;
            logger.debug("Recognized the format of the file (" + file.getName() + "): " + chosenFormat);
        } else {
            logger.debug("Couldn't recognize the format of the file (" + file.getName() + "): Trying with " + chosenFormat);
        }
        this.repoManager.importData(file, chosenFormat, directoryGraph);
    }

    /**
     * Imports the files found in the given directory in the repository under
     * the given graphspace. It automatically recognizes the format of the files
     * (by inspecting its file extension) and imports them in the repository.
     * Currently only formats expressed in RDF and NTriples format are
     * supported. The method searches for files under the given folder (it does
     * not recursively search its subdirectories).
     *
     * @param directoryGraph the graphspace that will be used for storing the
     * contents of the files
     * @param rootDirPath the path of the directory containing the files that
     * will be imported
     * @throws DataImportException is thrown if any error occurs during
     * importing
     */
    @Override
    public void importFiles(String rootDirPath, String directoryGraph) throws DataImportException {
        File directory = new File(rootDirPath);
        if (!directory.exists()) {
            throw new DataImportException("The given folder (\"" + rootDirPath + "\") does not exist");
        } else if (!directory.isDirectory()) {
            throw new DataImportException("The given path (\"" + rootDirPath + "\") does not refer to a directory");
        }
        File[] directoryListing = directory.listFiles();
        List<File> filesInRDF = new ArrayList<>();
        List<File> filesInNtriples = new ArrayList<>();
        for (File file : directoryListing) {
            if (file.isFile() && (file.getAbsolutePath().toLowerCase().endsWith(Resources.ntriplesDefaultExtension1)
                    || file.getAbsolutePath().toLowerCase().endsWith(Resources.ntriplesDefaultExtension2))) {
                filesInNtriples.add(file);
            } else if (file.isFile() && file.getAbsolutePath().toLowerCase().endsWith(Resources.rdfDefaultExtension)) {
                filesInRDF.add(file);
            }
        }
        if (!filesInRDF.isEmpty()) {
            logger.debug("Importing " + filesInRDF + " files in " + RDFFormat.RDFXML + " format");
            this.repoManager.importData(filesInRDF, RDFFormat.RDFXML, directoryGraph);
        }
        if (!filesInNtriples.isEmpty()) {
            logger.debug("Importing " + filesInNtriples + " files in " + RDFFormat.NTRIPLES + " format");
            this.repoManager.importData(filesInNtriples, RDFFormat.NTRIPLES, directoryGraph);
        }
    }

    public List<DirectoryStruct> searchDataset(String datasetName, String ownerName, String datasetURI, String datasetType, int limit, int offset, String repositoryGraph) throws QueryExecutionException {
        logger.info("Request for dataset search with parameters "
                   +"datasetName: ["+datasetName+"], "
                   +"ownerName: ["+ownerName+"], "
                   +"datasetUri: ["+datasetURI+"], "
                   +"datasetType: ["+datasetType+"], "
                   +"limit: ["+(limit<0?"N/A":String.valueOf(limit))+"], "
                   +"offset: ["+(offset<0?"N/A":String.valueOf(offset))+"], "
                   +"reposytoryGraph: ["+repositoryGraph+"], ");
        String queryString=this.sparqlDataset(repositoryGraph);
        if(datasetName!=null && !datasetName.isEmpty()){
            queryString+="FILTER CONTAINS(LCASE(?datasetName),\""+datasetName.toLowerCase()+"\"). ";
        }
        if(ownerName!=null && !ownerName.isEmpty()){
            queryString+="FILTER CONTAINS(LCASE(?ownerName),\""+ownerName.toLowerCase()+"\"). ";
        }
        if(datasetType!=null && !datasetType.isEmpty()){
            queryString+="FILTER CONTAINS(LCASE(?datasetType),\""+datasetType.toLowerCase()+"\"). ";
        }
        if(datasetURI!=null && !datasetURI.isEmpty()){
            queryString+="FILTER CONTAINS(LCASE(STR(?datasetURI)),\""+datasetURI.toLowerCase()+"\"). ";
        }
        queryString+="} ";
        if(limit>0){
            queryString+=" LIMIT "+limit;
        }
        if(offset>0){
            queryString+=" OFFSET "+offset;
        }
        logger.debug("Submitting the query: \"" + queryString + "\"");
        List<BindingSet> sparqlresults = this.repoManager.query(queryString);
        logger.debug("The result returned " + sparqlresults.size() + " results");
        Map<String, DirectoryStruct> structsMap = new HashMap<>();
        for (BindingSet result : sparqlresults) {
            if (!structsMap.containsKey(result.getValue("datasetURI").stringValue())) {
                DirectoryStruct struct = new DirectoryStruct().withDatasetURI(result.getValue("datasetURI").stringValue())
                        .withDatasetName(result.getValue("datasetName").stringValue())
                        .withDatasetType(result.getValue("datasetType").stringValue())
                        .withOwnerURI(result.getValue("ownerURI").stringValue())
                        .withOwnerName(result.getValue("ownerName").stringValue())
                        .withCuratorURI(result.getValue("curatorURI").stringValue())
                        .withCuratorName(result.getValue("curatorName").stringValue())
                        .withContactPoint(result.getValue("contactPoint").stringValue());
                if (result.getValue("datasetID") != null) {
                    struct.withDatasetID(result.getValue("datasetID").stringValue());
                }
                if (result.getValue("imageURI") != null) {
                    struct.withImageURI(result.getValue("imageURI").stringValue());
                }
                if (result.getValue("keeperURI") != null) {
                    struct.withKeeperURI(result.getValue("keeperURI").stringValue());
                }
                if (result.getValue("keeperName") != null) {
                    struct.withKeeperName(result.getValue("keeperName").stringValue());
                }
                if (result.getValue("publisherURI") != null) {
                    struct.withPublisherURI(result.getValue("publisherURI").stringValue());
                }
                if (result.getValue("publisherName") != null) {
                    struct.withPublisherName(result.getValue("publisherName").stringValue());
                }
                if (result.getValue("publicationDate") != null) {
                    struct.withPublicationDate(result.getValue("publicationDate").stringValue());
                }
                if (result.getValue("publicationEventLabel") != null) {
                    struct.withPublicationEventURI(result.getValue("publicationEventURI").stringValue());
                    struct.withPublicationEvent(result.getValue("publicationEventLabel").stringValue());
                }
                if (result.getValue("attributeAssignmentEventURI") != null) {
                    struct.withAttributeAssignmentEventURI(result.getValue("attributeAssignmentEventURI").stringValue());
                    struct.withAttributeAssignmentEvent(result.getValue("attributeAssignmentEventLabel").stringValue());
                }
                if (result.getValue("embargoState") != null) {
                    struct.withEmbargoState(result.getValue("embargoState").stringValue());
                }
                if (result.getValue("embargoPeriod") != null) {
                    struct.withEmbargoPeriod(result.getValue("embargoPeriod").stringValue());
                }
                if (result.getValue("contributorURI") != null) {
                    String contributorURI = result.getValue("contributorURI").stringValue();
                    String contributorName = result.getValue("contributorName").stringValue();
                    struct.withContributor(contributorURI, contributorName);
                }
                if (result.getValue("rightsHolderURI") != null) {
                    struct.withRightsHolderURI(result.getValue("rightsHolderURI").stringValue());
                }
                if (result.getValue("rightsHolderName") != null) {
                    struct.withRightsHolderName(result.getValue("rightsHolderName").stringValue());
                }
                if (result.getValue("creatorURI") != null) {
                    struct.withCreatorURI(result.getValue("creatorURI").stringValue());
                }
                if (result.getValue("creatorName") != null) {
                    struct.withCreatorName(result.getValue("creatorName").stringValue());
                }
                if (result.getValue("accessRightsURI") != null) {
                    struct.withAccessRightsURI(result.getValue("accessRightsURI").stringValue());
                }
                if (result.getValue("accessRights") != null) {
                    struct.withAccessRights(result.getValue("accessRights").stringValue());
                }
                if (result.getValue("creationEventURI") != null) {
                    struct.withCreationEventURI(result.getValue("creationEventURI").stringValue());
                    struct.withCreationEvent(result.getValue("creationEventLabel").stringValue());
                }
                if (result.getValue("creationDate") != null) {
                    struct.withCreationDate(result.getValue("creationDate").stringValue());
                }
                if (result.getValue("accessMethod") != null) {
                    struct.withAccessMethod(result.getValue("accessMethod").stringValue());
                }
                if (result.getValue("accessMethodURI") != null) {
                    struct.withAccessMethodURI(result.getValue("accessMethodURI").stringValue());
                }
                if (result.getValue("description") != null) {
                    struct.withDescription(result.getValue("description").stringValue());
                }
                if (result.getValue("locationURL") != null) {
                    struct.withLocationURL(result.getValue("locationURL").stringValue());
                }
                if (result.getValue("parentDatasetURI") != null) {
                    struct.withParentDatasetURI(result.getValue("parentDatasetURI").stringValue());
                }
                if (result.getValue("parentDatasetName") != null) {
                    struct.withParentDatasetName(result.getValue("parentDatasetName").stringValue());
                }
                structsMap.put(struct.getDatasetURI(), struct);
            } else {
                DirectoryStruct struct = structsMap.get(result.getValue("datasetURI").stringValue());
                if (result.getValue("contributorURI") != null) {
                    String contributorURI = result.getValue("contributorURI").stringValue();
                    String contributorName = result.getValue("contributorName").stringValue();
                    struct.withContributor(contributorURI, contributorName);
                }
                structsMap.put(struct.getDatasetURI(), struct);
            }
        }
        return new ArrayList<>(structsMap.values());
    }
     
    private String sparqlDataset(String namedgraph){
        return "SELECT DISTINCT ?datasetURI ?datasetName ?parentDatasetURI ?parentDatasetName ?datasetType ?datasetID "
                +"?ownerURI ?ownerName ?keeperURI ?keeperName "
                +"?publicationEventURI ?publicationEventLabel ?publisherURI ?publisherName ?publicationDate "
                +"?attributeAssignmentEventURI ?attributeAssignmentEventLabel ?embargoState ?embargoPeriod "
                +"?curatorURI ?curatorName ?rightsHolderURI ?rightsHolderName ?accessRightsURI ?accessRights "
                +"?contactPoint ?creationEventURI ?creationEventLabel ?creatorURI ?creatorName ?creationDate ?accessMethodURI ?accessMethod ?locationURL "
                +"?description ?contributorURI ?contributorName ?imageTitle ?imageURI "
                +"FROM <"+namedgraph+"> "
                +"WHERE{ "
                +"?datasetURI <"+Resources.rdfTypeLabel+"> <"+Resources.datasetLabel+">. "
                +"?datasetURI <"+Resources.hasPreferredIdentifier+"> ?datasetName. "
                +"?datasetURI <"+Resources.hasCurrentOwner+"> ?ownerURI. "
                +"?datasetURI <"+Resources.hasType+"> ?datasetType. "
                +"?datasetURI <"+Resources.isReferredToBy+"> ?accessMethodURI. "
                +"?accessMethodURI <"+Resources.rdfTypeLabel+"> <"+Resources.procedureLabel+">. "
                +"?accessMethodURI <"+Resources.hasNote+"> ?accessMethod. "
                +"?ownerURI <"+Resources.rdfsLabel+"> ?ownerName. "
                +"?datasetURI <"+Resources.hasCurator+"> ?curatorURI. "
                +"?curatorURI <"+Resources.rdfsLabel+"> ?curatorName. "
                +"?curatorURI <"+Resources.hasContactPoint+"> ?contactPoint. "
                +"OPTIONAL{ "
                    +"?datasetURI <"+Resources.rdfsLabel+"> ?datasetID. "
                +"} "
                +"OPTIONAL{ "
                    +"?datasetURI <"+Resources.isDepictedBy+"> ?imageURI. "
                +"} "
                +"OPTIONAL{ "
                    +"?datasetURI <"+Resources.hasCurrentKeeper+"> ?keeperURI. "
                    +"?keeperURI <"+Resources.rdfsLabel+"> ?keeperName. "
                +"} "
                +"OPTIONAL{ "
                    +"?datasetURI <"+Resources.wasCreatedBy+"> ?publicationEventURI. "
                    +"?publicationEventURI <"+Resources.rdfTypeLabel+"> <"+Resources.publicationEventLabel+">. "
                    +"?publicationEventURI <"+Resources.hasTimespan+"> ?publicationDate. "
                +"} "
                +"OPTIONAL{ "
                    +"?datasetURI <"+Resources.wasCreatedBy+"> ?publicationEventURI. "
                    +"?publicationEventURI <"+Resources.rdfTypeLabel+"> <"+Resources.publicationEventLabel+">. "
                    +"?publicationEventURI <"+Resources.carriedOutBy+"> ?publisherURI. "
                    +"?publisherURI <"+Resources.rdfsLabel+ "> ?publisherName. "
                +"} "
                +"OPTIONAL{ "
                    +"?datasetURI <"+Resources.hasContributor+"> ?contributorURI. "
                    +"?contributorURI <"+Resources.rdfsLabel+"> ?contributorName. "
                +"} "
                +"OPTIONAL{ "
                    +"?datasetURI <"+Resources.wasCreatedBy+"> ?creationEventURI. "
                    +"?creationEventURI <"+Resources.rdfTypeLabel+"> <"+Resources.creationEventLabel+">. "
                    +"?creationEventURI <"+Resources.carriedOutBy+"> ?creatorURI. "
                    +"?creationEventURI <"+Resources.rdfsLabel+"> ?creationEventLabel.  "
                    +"?creatorURI <"+Resources.rdfsLabel+"> ?creatorName. "
                +"} "
                +"OPTIONAL{ "
                    +"?datasetURI <"+Resources.wasCreatedBy+"> ?creationEventURI. "
                    +"?creationEventURI <"+Resources.rdfTypeLabel+"> <"+Resources.creationEventLabel+">. "
                    +"?creationEventURI <"+Resources.hasTimespan+"> ?creationDate. "
                +"} "
                +"OPTIONAL{ "
                    +"?datasetURI <"+Resources.wasAttributedBy+"> ?attributeAssignmentEventURI. "
                    +"?attributeAssignmentEventURI <"+Resources.rdfTypeLabel+"> <"+Resources.attributeAssignmentEventLabel+">. "
                    +"?attributeAssignmentEventURI <"+Resources.hasTimespan+"> ?embargoPeriod. "
                    +"?attributeAssignmentEventURI <"+Resources.rdfsLabel+"> ?attributeAssignmentEventLabel. "
                    +"?attributeAssignmentEventURI <"+Resources.assigned+"> ?embargoState. "
                +"} "
                +"OPTIONAL{ "
                    +"?datasetURI <"+Resources.isSubjectTo+"> ?accessRightsURI. "
                    +"?accessRightsURI <"+Resources.rdfsLabel+"> ?accessRights. "
                    +"?datasetURI <"+Resources.isPossessedBy+"> ?rightsHolderURI. "
                    +"?rightsHolderURI <"+Resources.rdfsLabel+"> ?rightsHolderName. "
                +"} "
                +"OPTIONAL{ "
                    +"?datasetURI <"+Resources.hasNote+"> ?description. "
                +"} "
                +"OPTIONAL{ "
                    +"?datasetURI <"+Resources.isLocatedAt+"> ?locationURL. "
                    +"?locationURL <"+Resources.hasType+"> \"URL\". "
                +"} "
                +"OPTIONAL{ "
                    +"?datasetURI <"+Resources.formsPartOf+"> ?parentDatasetURI. "
                    +"?parentDatasetURI <"+Resources.rdfsLabel+"> ?parentDatasetName. "
                +"} ";
    }
 
    /**
     * Searches for triples containing the given resource. In particular it
     * searches the repository, under the given graphspace, for triples
     * containing the given resource as a subject or as an object.
     *
     * @param resourceURI the resource that will be searched for
     * @param repositoryGraph the graphspace under which the resource will be
     * searched for
     * @return a list containing all the triples that were found and contain the
     * resource
     * @throws QueryExecutionException is thrown for any error that might occur
     */
    @Override
    public List<Triple> searchResource(String resourceURI, String repositoryGraph) throws QueryExecutionException {
        List<Triple> results = new ArrayList<>();
        String query = "SELECT <" + resourceURI + "> as ?s ?p ?o  from <" + repositoryGraph + "> "
                + "WHERE{ <" + resourceURI + "> ?p ?o }";
        logger.debug("Submitting the query: " + query);
        List<BindingSet> sparqlResults = this.repoManager.query(query);
        for (BindingSet bset : sparqlResults) {
            Triple triple = new Triple(bset.getBinding("s").getValue().stringValue(),
                    bset.getBinding("p").getValue().stringValue(),
                    bset.getBinding("o").getValue().stringValue());
            results.add(triple);
        }
        query = "SELECT ?s ?p <" + resourceURI + "> as ?o  from <" + repositoryGraph + "> "
                + "WHERE{ ?s ?p <" + resourceURI + ">}";
        logger.debug("Submitting the query: " + query);
        sparqlResults = this.repoManager.query(query);
        for (BindingSet bset : sparqlResults) {
            Triple triple = new Triple(bset.getBinding("s").getValue().stringValue(),
                    bset.getBinding("p").getValue().stringValue(),
                    bset.getBinding("o").getValue().stringValue());
            results.add(triple);
        }
        logger.debug("The queries returned " + results.size() + " results in total");
        return results;
    }

    /**
     * Searches for triples containing the given literal value. It will search
     * in the repository under the given graphspace, for triples containing the
     * literal value (as an object), by ignoring case (comparing literals in
     * lowercase).
     *
     * @param literalValue the literal value that will be searched for
     * @param repositoryGraph the graphspace that will be used
     * @return a list with the triples containing the givel literal value
     * @throws QueryExecutionException is thrown for any error that might occur
     */
    @Override
    public List<Triple> searchLiteral(String literalValue, String repositoryGraph) throws QueryExecutionException {
        List<Triple> results = new ArrayList<>();
        String query = "SELECT ?s ?p ?o  from <" + repositoryGraph + "> "
                + "WHERE{ ?s ?p ?o . "
                + "FILTER REGEX(?o, \"" + literalValue + "\", \"i\")}";
        logger.debug("Submitting the query: " + query);
        List<BindingSet> sparqlResults = this.repoManager.query(query);
        for (BindingSet bset : sparqlResults) {
            Triple triple = new Triple(bset.getBinding("s").getValue().stringValue(),
                    bset.getBinding("p").getValue().stringValue(),
                    bset.getBinding("o").getValue().stringValue());
            results.add(triple);
        }
        logger.debug("The queries returned " + results.size() + " results in total");
        return results;
    }

    /**
     * Insert the information of the given entry (which is expressed as a struct
     * object) in the repository under the given graphspace.
     *
     * @param directoryStruct the entry that will be inserted
     * @param directoryGraph the graphspace that will be used for adding the new
     * information
     * @throws URIValidationException is thrown if the entry contains invalid
     * URIs
     * @throws QueryExecutionException is thrown for any error that might occur
     * during updating the repository
     */
    @Override
    public void insertStruct(DirectoryStruct directoryStruct, String directoryGraph) throws URIValidationException, QueryExecutionException {
        // directoryStruct.validateURIs();
        String insertQuery = "INSERT INTO <" + directoryGraph + "> { " + directoryStruct.toNtriples() + " }";
        logger.debug("Submitting the insert query: " + insertQuery);
        this.repoManager.update(insertQuery);
    }

    /**
     * Deletes the triples that contain the given resource, from the given
     * graphspace. More specifically it removes the triples containing the
     * resource as an object, or as a subject.
     *
     * @param resourceURI the resource URI that will be removed
     * @param repositoryGraph the graphspace that will be used
     * @throws QueryExecutionException is thrown for any error that might occur
     */
    @Override
    public void deleteTriplesHavingResource(String resourceURI, String repositoryGraph) throws QueryExecutionException {
        String query = "DELETE FROM <" + repositoryGraph + "> {?s ?p ?o} "
                + "WHERE{"
                + "{?s ?p ?o. FILTER(?s=<" + resourceURI + ">)} "
                + "UNION "
                + "{?s ?p ?o. FILTER(?o=<" + resourceURI + ">)} } ";
        logger.debug("Submitting the delete query: " + query);
        this.repoManager.update(query);
    }

    /**
     * Deletes the triples that contain the given literal value (as an object)
     * in the given graphspace.
     *
     * @param literalValue the literal value that will be removed
     * @param repositoryGraph the graphspace that will be used
     * @throws QueryExecutionException is thrown for any error that might occur
     */
    @Override
    public void deleteTriplesHavingLiteral(String literalValue, String repositoryGraph) throws QueryExecutionException {
        String query = "DELETE FROM <" + repositoryGraph + "> {?s ?p ?o} "
                + "WHERE{ ?s ?p ?o "
                + "FILTER REGEX(?o,\"" + literalValue + "\",\"i\")}";
        logger.debug("Submitting the delete query: " + query);
        this.repoManager.update(query);
    }

    /**
     * Deletes the triples that contain the given property in the given
     * graphspace
     *
     * @param property the uri of the property that will be removed
     * @param repositoryGraph the graphspace that will be used
     * @throws QueryExecutionException is thrown for any error that might occur
     */
    @Override
    public void deleteTriplesHavingProperty(String property, String repositoryGraph) throws QueryExecutionException {
        String deleteQuery = "DELETE FROM <" + repositoryGraph + "> {?s ?p ?o} "
                + "WHERE{ ?s ?p ?o . "
                + "FILTER(?p=<" + property + ">)}";
        logger.debug("Submitting the delete the query: " + deleteQuery);
        this.repoManager.update(deleteQuery);
    }

    /**
     * Deletes the triples containing the given resources/values from the given
     * graphspace. More specifically the user can choose to declare the triples
     * containing a specific subject, a specific property, a specific object or
     * combinations of the above. If a null value is given it means that we do
     * not need to restrict the deletion for the given value. For example the
     * following call of the method (deleteTriples(Dataset1, null, null,
     * graphUri) will remove all triples having Dataset1 as a uri. In case where
     * the given object value is a literal value then the comparison of literals
     * will be made by ignoring the case (upper, lower). The identification of a
     * literal value, or URI is being made automatically.
     *
     * @param subject the value for the subject of the triple
     * @param property the value for the property of the triple
     * @param object the value for the object of the triple
     * @param repositoryGraph the graphspace that will be used
     * @throws QueryExecutionException is thrown for any error that might occur
     */
    @Override
    public void deleteTriples(String subject, String property, String object, String repositoryGraph) throws QueryExecutionException {
        String deleteQuery = "DELETE FROM <" + repositoryGraph + "> {?s ?p ?o} "
                + "WHERE{ ?s ?p ?o . ";
        if (subject != null) {
            deleteQuery += "FILTER(?s=<" + subject + ">). ";
        }
        if (property != null) {
            deleteQuery += "FILTER(?p=<" + property + ">). ";
        }
        if (object != null) {
            try {
                new URI(object);
                deleteQuery += "FILTER(?o=<" + object + ">)";
            } catch (URISyntaxException ex) {
                deleteQuery += "FILTER REGEX(?o,\"" + object + "\",\"i\")";
            }
        }
        deleteQuery += "}";
        logger.debug("Submitting the delete query: " + deleteQuery);
        this.repoManager.update(deleteQuery);
    }

    /**
     * Updates the object of a triple from the given graphspace. It takes as
     * input the original triple and replaces its value with the given resource.
     *
     * @param subject the original subject of the triple
     * @param property the original predicate of the triple
     * @param object the original object of the triple
     * @param newObject the new object of the triple
     * @param repositoryGraph the graphspace that will be used
     * @throws QueryExecutionException is thrown for any error that might occur
     * @deprecated this method will break the links of linked data if not used
     * carefully. For example is can update all the occurrences of a uri found
     * in the subject of the triples but will not do anything for the
     * occurrences of the same uri in the object of the triples.
     */
    @Override
    public void updateResource(String subject, String property, String object, String newObject, String repositoryGraph) throws QueryExecutionException {
        boolean oldObjectIsUri, newObjectIsUri;
        try {
            new URI(object);
            oldObjectIsUri = true;
        } catch (URISyntaxException ex) {
            oldObjectIsUri = false;
        }
        try {
            new URI(newObject);
            newObjectIsUri = true;
        } catch (URISyntaxException ex) {
            newObjectIsUri = false;
        }
        String updateQuery;
        if (oldObjectIsUri && newObjectIsUri) {
            updateQuery = this.prepareUpdateQueryUri2Uri(subject, property, object, newObject, repositoryGraph);
        } else if (oldObjectIsUri && !newObjectIsUri) {
            updateQuery = this.prepareUpdateQueryUri2Literal(subject, property, object, newObject, repositoryGraph);
        } else if (!oldObjectIsUri && newObjectIsUri) {
            updateQuery = this.prepareUpdateQueryLiteral2Uri(subject, property, object, newObject, repositoryGraph);
        } else {
            updateQuery = this.prepareUpdateQueryLiteral2Literal(subject, property, object, newObject, repositoryGraph);
        }
        logger.debug("Executing the update query: " + updateQuery);
        this.repoManager.update(updateQuery);
    }

    /**
     * Updates the value of a resource found in the given graphspace. The
     * resource can be found either in the subject of a triple or in the object.
     *
     * @param originalResource the original value of the resource
     * @param newResource the new value of the resource
     * @param directoryGraph the graphspace that will be used
     * @throws QueryExecutionException is thrown for any error that might occur
     */
    @Override
    public void updateResource(String originalResource, String newResource, String directoryGraph) throws QueryExecutionException {
        String updateQuery = "INSERT INTO <" + directoryGraph + "> { "
                + "<" + newResource + "> ?p ?o} "
                + "WHERE {GRAPH <" + directoryGraph + "> { "
                + "?s ?p ?o. "
                + "FILTER(?s=<" + originalResource + ">)}} "
                + "INSERT INTO <" + directoryGraph + "> { "
                + "?s ?p <" + newResource + ">} "
                + "WHERE {GRAPH <" + directoryGraph + "> { "
                + "?s ?p ?o. "
                + "FILTER(?o=<" + originalResource + ">)}} "
                + "DELETE FROM <" + directoryGraph + "> { "
                + "?s ?p ?o} "
                + "WHERE {GRAPH <" + directoryGraph + "> { "
                + "?s ?p ?o . "
                + "FILTER(?s=<" + originalResource + "> || ?o=<" + originalResource + ">)}}";
        logger.debug("Executing the update query: " + updateQuery);
        this.repoManager.update(updateQuery);
    }

    /**
     * Updates the value of a property found in the given graphspace.
     *
     * @param originalProperty the original value of the property
     * @param newProperty the new value of the property
     * @param directoryGraph the graphspace the will be used
     * @throws QueryExecutionException is thrown for any error that might occur
     */
    @Override
    public void updateProperty(String originalProperty, String newProperty, String directoryGraph) throws QueryExecutionException {
        String updateQuery = "INSERT INTO <" + directoryGraph + "> { "
                + "?s <" + newProperty + "> ?o } "
                + "WHERE {GRAPH <" + directoryGraph + "> { "
                + "?s ?p ?o ."
                + "FILTER(?p=<" + originalProperty + ">)}} "
                + "DELETE FROM <" + directoryGraph + "> { "
                + "?s ?p ?o } "
                + "WHERE {GRAPH <" + directoryGraph + "> { "
                + "?s ?p ?o . "
                + "FILTER(?p=<" + originalProperty + ">)}}";
        logger.debug("Executing the update query: " + updateQuery);
        this.repoManager.update(updateQuery);
    }

    /**
     * Updates the value of a literal found in the given graphspace. The new
     * resource will be added as a literal.
     *
     * @param originalLiteral the original value of the literal
     * @param newLiteral the new value of the literal
     * @param directoryGraph the graphspace that will be used
     * @throws QueryExecutionException is thrown for any error that might occur
     */
    @Override
    public void updateLiteral(String originalLiteral, String newLiteral, String directoryGraph) throws QueryExecutionException {
        String updateQuery = "INSERT INTO <" + directoryGraph + "> { "
                + "?s ?p \"" + newLiteral + "\" } "
                + "WHERE {GRAPH <" + directoryGraph + "> { "
                + "?s ?p ?o ."
                + "FILTER REGEX(?o,\"" + originalLiteral + "\",\"i\")}} "
                + "DELETE FROM <" + directoryGraph + "> { "
                + "?s ?p ?o } "
                + "WHERE {GRAPH <" + directoryGraph + "> { "
                + "?s ?p ?o . "
                + "FILTER REGEX(?o,\"" + originalLiteral + "\",\"i\")}}";
        logger.debug("Executing the update query: " + updateQuery);
        this.repoManager.update(updateQuery);
    }

    private String prepareUpdateQueryUri2Uri(String subject, String property, String object, String newObject, String graph) {
        return "INSERT INTO <" + graph + "> {?s ?p <" + newObject + ">} "
                + "WHERE {GRAPH <" + graph + "> { ?s ?p ?o . "
                + "FILTER(?s=<" + subject + ">). "
                + "FILTER(?p=<" + property + ">). "
                + "FILTER(?o=<" + object + ">)}} "
                + "DELETE FROM <" + graph + "> {?s ?p ?o} "
                + "WHERE {GRAPH <" + graph + "> {?s ?p ?o . "
                + "FILTER(?s=<" + subject + ">). "
                + "FILTER(?p=<" + property + ">). "
                + "FILTER(?o=<" + object + ">)}}";
    }

    private String prepareUpdateQueryLiteral2Literal(String subject, String property, String object, String newObject, String graph) {
        return "INSERT INTO <" + graph + "> {?s ?p \"" + newObject + "\"} "
                + "WHERE {GRAPH <" + graph + "> { ?s ?p ?o . "
                + "FILTER(?s=<" + subject + ">). "
                + "FILTER(?p=<" + property + ">). "
                + "FILTER REGEX(?o,\"" + object + "\")}} "
                + "DELETE FROM <" + graph + "> {?s ?p ?o} "
                + "WHERE {GRAPH <" + graph + "> {?s ?p ?o . "
                + "FILTER(?s=<" + subject + ">). "
                + "FILTER(?p=<" + property + ">). "
                + "FILTER REGEX(?o,\"" + object + "\")}}";
    }

    private String prepareUpdateQueryUri2Literal(String subject, String property, String object, String newObject, String graph) {
        return "INSERT INTO <" + graph + "> {?s ?p \"" + newObject + "\"} "
                + "WHERE {GRAPH <" + graph + "> { ?s ?p ?o . "
                + "FILTER(?s=<" + subject + ">). "
                + "FILTER(?p=<" + property + ">). "
                + "FILTER(?o=<" + object + ">)}} "
                + "DELETE FROM <" + graph + "> {?s ?p ?o} "
                + "WHERE {GRAPH <" + graph + "> {?s ?p ?o . "
                + "FILTER(?s=<" + subject + ">). "
                + "FILTER(?p=<" + property + ">). "
                + "FILTER(?o=<" + object + ">)}}";
    }

    private String prepareUpdateQueryLiteral2Uri(String subject, String property, String object, String newObject, String graph) {
        return "INSERT INTO <" + graph + "> {?s ?p <" + newObject + ">} "
                + "WHERE {GRAPH <" + graph + "> { ?s ?p ?o . "
                + "FILTER(?s=<" + subject + ">). "
                + "FILTER(?p=<" + property + ">). "
                + "FILTER REGEX(?o,\"" + object + "\")}} "
                + "DELETE FROM <" + graph + "> {?s ?p ?o} "
                + "WHERE {GRAPH <" + graph + "> {?s ?p ?o . "
                + "FILTER(?s=<" + subject + ">). "
                + "FILTER(?p=<" + property + ">). "
                + "FILTER REGEX(?o,\"" + object + "\")}}";
    }

    /**
     * @deprecated this method will break the links of linked data if not used
     * carefully. For example is can update all the occurrences of a uri found
     * in the subject of the triples but will not do anything for the
     * occurrences of the same uri in the object of the triples.
     */
    @Deprecated
    @Override
    public void updateResource(String oldResource, String newResource, ResourceType resourceType, String repositoryGraph) throws QueryExecutionException {
        String updateQuery = "";
        switch (resourceType) {
            case SUBJECT:
                updateQuery = "INSERT INTO <" + repositoryGraph + "> {<" + newResource + "> ?p ?o} "
                        + "WHERE {GRAPH <" + repositoryGraph + "> {?s ?p ?o . "
                        + "FILTER(?s=<" + oldResource + ">)}} "
                        + "DELETE FROM <" + repositoryGraph + "> {?s ?p ?o} "
                        + "WHERE {GRAPH <" + repositoryGraph + "> {?s ?p ?o . "
                        + "FILTER(?s=<" + oldResource + ">)}}";
                break;
            case PREDICATE:
                updateQuery = "INSERT INTO <" + repositoryGraph + "> {?s <" + newResource + "> ?o} "
                        + "WHERE {GRAPH <" + repositoryGraph + "> {?s ?p ?o . "
                        + "FILTER(?p=<" + oldResource + ">)}} "
                        + "DELETE FROM <" + repositoryGraph + "> {?s ?p ?o} "
                        + "WHERE {GRAPH <" + repositoryGraph + "> {?s ?p ?o . "
                        + "FILTER(?p=<" + oldResource + ">)}}";
                break;
            case OBJECT:
                updateQuery = "INSERT INTO <" + repositoryGraph + "> {?s ?p <" + newResource + ">} "
                        + "WHERE {GRAPH <" + repositoryGraph + "> {?s ?p ?o . "
                        + "FILTER(?o=<" + oldResource + ">)}} "
                        + "DELETE FROM <" + repositoryGraph + "> {?s ?p ?o} "
                        + "WHERE {GRAPH <" + repositoryGraph + "> {?s ?p ?o . "
                        + "FILTER(?o=<" + oldResource + ">)}}";
                break;
            case LITERAL:
                updateQuery = "INSERT INTO <" + repositoryGraph + "> {?s ?p \"" + newResource + "\"} "
                        + "WHERE {GRAPH <" + repositoryGraph + "> {?s ?p ?o . "
                        + "FILTER REGEX(?o, \"" + oldResource + "\")}} "
                        + "DELETE FROM <" + repositoryGraph + "> {?s ?p ?o} "
                        + "WHERE {GRAPH <" + repositoryGraph + "> {?s ?p ?o . "
                        + "FILTER REGEX(?o, \"" + oldResource + "\")}}";
                break;
        }
        logger.debug("Submitting the update query: " + updateQuery);
        this.repoManager.query(updateQuery);
    }

    @Override
    @Deprecated
    public List<DirectoryStruct> searchDataset(String datasetName, String hostingInstitutionName, String repositoryGraph) throws QueryExecutionException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
