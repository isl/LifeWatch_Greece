package eu.lifewatch.dataservices.middleware.directoryservice;

import eu.lifewatch.core.impl.VirtuosoRepositoryManager;
import eu.lifewatch.core.model.DirectoryStruct;
import eu.lifewatch.core.model.Triple;
import eu.lifewatch.exception.QueryExecutionException;
import eu.lifewatch.service.impl.DirectoryService;
import java.util.ArrayList;
import java.util.List;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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
    private static final Logger logger=Logger.getLogger(DirectoryService_Search.class);

    @WebMethod(operationName = "searchDataset")
    public List<DirectoryStruct> searchDataset(@WebParam(name = "datasetName") String datasetName,
                                               @WebParam(name = "ownerName") String ownerName,
                                               @WebParam(name = "datasetURI") String datasetURI, 
                                               @WebParam(name = "datasetType") String datasetType, 
                                               @WebParam(name = "repositoryGraph") String repositoryGraph) {
        logger.info("Request for searchDataset("+datasetName+","+ownerName+","+datasetURI+","+datasetType+","+repositoryGraph+")");
        List<DirectoryStruct> retList=new ArrayList<>();
        try{
            ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
            VirtuosoRepositoryManager repoManager=context.getBean(VirtuosoRepositoryManager.class);
            DirectoryService api=new DirectoryService(repoManager);
            retList=api.searchDataset(datasetName, ownerName, datasetURI, datasetType, -1, -1, repositoryGraph);
            logger.info("Number of results that will be returned: "+retList.size());
        }catch(QueryExecutionException ex){
            logger.error("An error occured while searching for resources. Returning an empty list.\n", ex);
        }
        return retList;
    }
    
    @WebMethod(operationName = "searchDatasetWithinRange")
    public List<DirectoryStruct> searchDatasetWithinRange(@WebParam(name = "datasetName") String datasetName,
                                               @WebParam(name = "ownerName") String ownerName,
                                               @WebParam(name = "datasetURI") String datasetURI, 
                                               @WebParam(name = "datasetType") String datasetType, 
                                               @WebParam(name = "limit") int limit, 
                                               @WebParam(name = "offset") int offset,
                                               @WebParam(name = "repositoryGraph") String repositoryGraph) {
        logger.info("Request for searchDatasetWithinRange("+datasetName+","+ownerName+","+datasetURI+","+datasetType+","+limit+","+offset+","+repositoryGraph+")");
        List<DirectoryStruct> retList=new ArrayList<>();
        try{
            ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
            VirtuosoRepositoryManager repoManager=context.getBean(VirtuosoRepositoryManager.class);
            DirectoryService api=new DirectoryService(repoManager);
            retList=api.searchDataset(datasetName, ownerName, datasetURI, datasetType, limit, offset, repositoryGraph);
            logger.info("Number of results that will be returned: "+retList.size());
        }catch(QueryExecutionException ex){
            logger.error("An error occured while searching for resources. Returning an empty list.\n", ex);
        }
        return retList;
    }

    @WebMethod(operationName = "searchResource")
    public List<Triple> searchResource(@WebParam(name = "resourceURI") String resourceURI,
                                       @WebParam(name = "repositoryGraph") String repositoryGraph) {
        logger.info("Request for searchResource("+resourceURI+","+repositoryGraph+")");
        List<Triple> retList=new ArrayList<>();
        try{
            ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
            VirtuosoRepositoryManager repoManager=context.getBean(VirtuosoRepositoryManager.class);
            DirectoryService api=new DirectoryService(repoManager);
            retList=api.searchResource(resourceURI, repositoryGraph);
            logger.info("Number of results that will be returned: "+retList.size());
        }catch(QueryExecutionException ex){
            logger.error("An error occured while searching for resources. Returning an empty list.\n", ex);
        }
        return retList;
    }

    @WebMethod(operationName = "searchLiteral")
    public List<Triple> searchLiteral(@WebParam(name = "literalValue") String literalValue,
                                      @WebParam(name = "repositoryGraph") String repositoryGraph) {
        logger.info("Request for searchLiteral("+literalValue+","+repositoryGraph+")");
        List<Triple> retList=new ArrayList<>();
        try{
            ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
            VirtuosoRepositoryManager repoManager=context.getBean(VirtuosoRepositoryManager.class);
            DirectoryService api=new DirectoryService(repoManager);
            retList=api.searchLiteral(literalValue, repositoryGraph);
            logger.info("Number of results that will be returned: "+retList.size());
        }catch(QueryExecutionException ex){
            logger.error("An error occured while searching for literals. Returning an empty list.\n", ex);
        }
        return retList;
    }
}