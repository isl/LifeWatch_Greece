package eu.lifewatch.service.api;

import eu.lifewatch.common.ResourceType;
import eu.lifewatch.core.model.DirectoryStruct;
import eu.lifewatch.core.model.Triple;
import eu.lifewatch.exception.DataImportException;
import eu.lifewatch.exception.QueryExecutionException;
import eu.lifewatch.exception.URIValidationException;
import java.util.List;

/**
 * @author Nikos Minadakis (minadakn 'at' ics 'dot' forth 'dot' gr)
 * @author Yannis Marketakis (marketak 'at' ics 'dot' forth 'dot' gr)
 */
public interface Service {
   
    /**Imports the the file found in the given file path in the repository under the 
     * given graphspace. 
     * 
     * @param fileFullPath the path of the file that will be imported
     * @param directoryGraph the graphspace that will be used
     * @throws DataImportException  is thrown if any error is occurred */
    public void importFile(String fileFullPath, String directoryGraph) throws DataImportException;

    /**Imports the files found in the given directory in the repository under the 
     * given graphspace. It automatically recognizes the format of the files (by
     * inspecting its file extension) and imports them in the repository. 
     * Currently only formats expressed in RDF and NTriples format are supported.
     * The method searches for files under the given folder (it does not recursively
     * search its subdirectories).
     * 
     * @param directoryGraph the graphspace that will be used for storing the contents of the files 
     * @param rootDirPath the path of the directory containing the files that will be imported
     * @throws DataImportException is thrown if any error occurs during importing */
    public void importFiles(String rootDirPath, String directoryGraph) throws DataImportException;

    /**Searches for entries found in the repository containing information about the 
     * dataset described using the given dataset name and hosting institution name, 
     * under a specific graphspace.
     * It will return all the entries that can be found and fulfill these criteria.
     * 
     * @param datasetName the dataset name
     * @param hostingInstitutionName the hosting institution (keeper) name
     * @param repositoryGraph the graphspace that will be used
     * @return a list with the resulted entries
     * @throws QueryExecutionException is thrown for any error that might occur */
    public List<DirectoryStruct> searchDataset(String datasetName, String hostingInstitutionName, String repositoryGraph) throws QueryExecutionException;
    
    /**Searches for triples containing the given resource. In particular it searches 
     * the repository, under the given graphspace, for triples containing the 
     * given resource as a subject or as an object. 
     * 
     * @param resourceURI the resource that will be searched for 
     * @param repositoryGraph the graphspace under which the resource will be searched for
     * @return a list containing all the triples that were found and contain the resource
     * @throws QueryExecutionException  is thrown for any error that might occur */
    public List<Triple> searchResource(String resourceURI, String repositoryGraph) throws QueryExecutionException;
    
    /**Searches for triples containing the given literal value. It will search in the repository
     * under the given graphspace, for triples containing the literal value (as an object), by 
     * ignoring case (comparing literals in lowercase). 
     * 
     * @param literalValue the literal value that will be searched for
     * @param repositoryGraph the graphspace that will be used 
     * @return a list with the triples containing the givel literal value
     * @throws QueryExecutionException is thrown for any error that might occur */
    public List<Triple> searchLiteral(String literalValue, String repositoryGraph) throws QueryExecutionException;

    /**Insert the information of the given entry (which is expressed as a struct object)
     * in the repository under the given graphspace.
     * 
     * @param directoryStruct the entry that will be inserted
     * @param directoryGraph the graphspace that will be used for adding the new information
     * @throws URIValidationException is thrown if the entry contains invalid URIs 
     * @throws QueryExecutionException is thrown for any error that might occur during updating the repository */
    public void insertStruct(DirectoryStruct directoryStruct, String directoryGraph) throws URIValidationException, QueryExecutionException;
    
    /**Deletes the triples that contain the given resource, from the given graphspace.
     * More specifically it removes the triples containing the resource as an object, or as a subject.
     * 
     * @param resourceURI the resource URI that will be removed
     * @param repositoryGraph the graphspace that will be used
     * @throws QueryExecutionException is thrown for any error that might occur */
    public void deleteTriplesHavingResource(String resourceURI, String repositoryGraph) throws QueryExecutionException;

    /**Deletes the triples that contain the given literal value (as an object) in the given 
     * graphspace. 
     * 
     * @param literalValue the literal value that will be removed
     * @param repositoryGraph the graphspace that will be used
     * @throws QueryExecutionException is thrown for any error that might occur */
    public void deleteTriplesHavingLiteral(String literalValue, String repositoryGraph) throws QueryExecutionException;
     
    /**Deletes the triples that contain the given property in the given graphspace
     * 
     * @param property the uri of the property that will be removed
     * @param repositoryGraph the graphspace that will be used
     * @throws QueryExecutionException is thrown for any error that might occur */
    public void deleteTriplesHavingProperty(String property, String repositoryGraph) throws QueryExecutionException;
    
    /**Deletes the triples containing the given resources/values from the given graphspace.
     * More specifically the user can choose to declare the triples containing a specific subject, 
     * a specific property, a specific object or combinations of the above. If a null value is given
     * it means that we do not need to restrict the deletion for the given value. For example the following 
     * call of the method (deleteTriples(Dataset1, null, null, graphUri) will remove all triples having 
     * Dataset1 as a uri. In case where the given object value is a literal value then
     * the comparison of literals will be made by ignoring the case (upper, lower). The identification 
     * of a literal value, or URI is being made automatically. 
     * 
     * @param subject the value for the subject of the triple
     * @param property the value for the property of the triple
     * @param object the value for the object of the triple
     * @param repositoryGraph the graphspace that will be used 
     * @throws QueryExecutionException is thrown for any error that might occur */
    public void deleteTriples(String subject, String property, String object, String repositoryGraph) throws QueryExecutionException;
     
    /**Updates the object of a triple from the given graphspace.
     * It takes as input the original triple and replaces its value with the given resource. 
     * 
     * @param subject the original subject of the triple
     * @param property the original predicate of the triple
     * @param object the original object of the triple
     * @param newObject the new object of the triple
     * @param repositoryGraph the graphspace that will be used
     * @throws QueryExecutionException  is thrown for any error that might occur 
     * @deprecated this method  will break the links of linked data if not used carefully. For example is can update all 
     * the occurrences of a uri found in the subject of the triples but will not do anything for the occurrences of the 
     * same uri in the object of the triples. */
    public void updateResource(String subject, String property, String object, String newObject, String repositoryGraph) throws QueryExecutionException;

    /**Updates the value of a resource found in the given graphspace. The resource can be 
     * found either in the subject of a triple or in the object. 
     * 
     * @param originalResource the original value of the resource
     * @param newResource the new value of the resource
     * @param directoryGraph the graphspace that will be used
     * @throws QueryExecutionException is thrown for any error that might occur */
    public void updateResource(String originalResource, String newResource, String directoryGraph) throws QueryExecutionException;
    
    /**Updates the value of a property found in the given graphspace. 
     * 
     * @param originalProperty the original value of the property
     * @param newProperty the new value of the property
     * @param directoryGraph the graphspace the will be used
     * @throws QueryExecutionException is thrown for any error that might occur */
    public void updateProperty(String originalProperty, String newProperty, String directoryGraph) throws QueryExecutionException;
    
    /**Updates the value of a literal found in the given graphspace.
     * The new resource will be added as a literal.
     * 
     * @param originalLiteral the original value of the literal
     * @param newLiteral the new value of the literal
     * @param directoryGraph the graphspace that will be used
     * @throws QueryExecutionException is thrown for any error that might occur */
    public void updateLiteral(String originalLiteral, String newLiteral, String directoryGraph) throws QueryExecutionException;
      
    /**
     * @param oldResource the resource that will be replaced
     * @param newResource the value of the new resource 
     * @param resourceType the type of the resource that will be updated (literal, property, subject, object)
     * @param repositoryGraph the graph that will be used for udpating the resources
     * @throws eu.lifewatch.exception.QueryExecutionException is thrown for any error that might occur
     * @deprecated this method  will break the links of linked data if not used carefully. For example is can update all 
     * the occurrences of a uri found in the subject of the triples but will not do anything for the occurrences of the 
     * same uri in the object of the triples.  */
    public void updateResource(String oldResource, String newResource, ResourceType resourceType, String repositoryGraph) throws QueryExecutionException;
}