package eu.lifewatch.dataservices.middleware.directoryservice;

import eu.lifewatch.core.model.DirectoryStruct;
import eu.lifewatch.core.model.Triple;
import java.util.List;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 * DirectoryService-Search provides the functionality to search of the the Directory 
 * using various ways (search for specific structs, or triples in general). 
 * For more information about the usage of the SOAP services and the contents and 
 * manipulation of the returned structs please refer to the javadoc documentation of the 
 * DataServices-api.
 *
 * @author Yannis Marketakis (marketak 'at' ics 'dot' forth 'dot' gr)
 * @author Nikos Minadakis (minadakn 'at' ics 'dot' forth 'dot' gr)
 */
@WebService(serviceName = "DirectoryService_Search")
public class DirectoryService_Search {


    @WebMethod(operationName = "searchDataset")
    public List<DirectoryStruct> searchDataset(@WebParam(name = "datasetName") String datasetName, @WebParam(name = "ownerName") String ownerName, @WebParam(name = "datasetURI") String datasetURI, @WebParam(name = "datasetType") String datasetType, @WebParam(name = "repositoryGraph") String repositoryGraph) {
        //TODO write your implementation code here:
        return null;
    }

    @WebMethod(operationName = "searchDataset_1")
    public List<DirectoryStruct> searchDataset(@WebParam(name = "datasetName") String datasetName, @WebParam(name = "ownerName") String ownerName, @WebParam(name = "datasetURI") String datasetURI, @WebParam(name = "datasetType") String datasetType, @WebParam(name = "limit") int limit, @WebParam(name = "offset") int offset, @WebParam(name = "repositoryGraph") String repositoryGraph) {
        //TODO write your implementation code here:
        return null;
    }

    @WebMethod(operationName = "searchResource")
    public List<Triple> searchResource(@WebParam(name = "resourceURI") String resourceURI, @WebParam(name = "repositoryGraph") String repositoryGraph) {
        //TODO write your implementation code here:
        return null;
    }

    @WebMethod(operationName = "searchLiteral")
    public List<Triple> searchLiteral(@WebParam(name = "literalValue") String literalValue, @WebParam(name = "repositoryGraph") String repositoryGraph) {
        //TODO write your implementation code here:
        return null;
    }
}
