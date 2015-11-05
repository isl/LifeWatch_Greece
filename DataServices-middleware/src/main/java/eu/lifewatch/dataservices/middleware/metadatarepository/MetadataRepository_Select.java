package eu.lifewatch.dataservices.middleware.metadatarepository;

import eu.lifewatch.core.impl.VirtuosoRepositoryManager;
import eu.lifewatch.core.model.IncomingNodeStruct;
import eu.lifewatch.core.model.OutgoingNodeStruct;
import eu.lifewatch.exception.QueryExecutionException;
import eu.lifewatch.service.impl.MetadataRepositoryService;
import java.util.ArrayList;
import java.util.List;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * MetadataRepository-Select provides the functionality to browse over the linked data of the metadata catalogue. 
 * For more information about the usage of the SOAP services and the contents and 
 * manipulation of the returned structs please refer to the javadoc documentation of the 
 * DataServices-api.
 *
 * @author Yannis Marketakis (marketak 'at' ics 'dot' forth 'dot' gr)
 * @author Nikos Minadakis (minadakn 'at' ics 'dot' forth 'dot' gr)
 */
@WebService(serviceName = "MetadataRepository_Select")
public class MetadataRepository_Select {
    private static final Logger logger=Logger.getLogger(MetadataRepository_Select.class);

    @WebMethod(operationName = "selectIncoming")
    public List<IncomingNodeStruct> selectIncoming(@WebParam(name = "resourceURI") String resourceURI,
                                                   @WebParam(name = "repositoryGraph") String repositoryGraph) {
        logger.info("Request for selectIncoming("+resourceURI+","+repositoryGraph+")");
        List<IncomingNodeStruct> retList=new ArrayList<>();
        try{
            ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
            VirtuosoRepositoryManager repoManager=context.getBean(VirtuosoRepositoryManager.class);
            MetadataRepositoryService api=new MetadataRepositoryService(repoManager);
            retList=api.selectIncoming(resourceURI, repositoryGraph);
            logger.info("Number of results that will be returned: "+retList.size());
        }catch(QueryExecutionException ex){
            logger.error("An error occured while getting the incoming nodes of a resource URI. Returning an empty list.\n", ex);
        }
        return retList;
    }

    @WebMethod(operationName = "selectOutgoing")
    public List<OutgoingNodeStruct> selectOutgoing(@WebParam(name = "resourceURI") String resourceURI,
                                                   @WebParam(name = "repositoryGraph") String repositoryGraph) {
        logger.info("Request for selectOutgoing("+resourceURI+","+repositoryGraph+")");
        List<OutgoingNodeStruct> retList=new ArrayList<>();
        try{
            ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
            VirtuosoRepositoryManager repoManager=context.getBean(VirtuosoRepositoryManager.class);
            MetadataRepositoryService api=new MetadataRepositoryService(repoManager);
            retList=api.selectOutgoing(resourceURI, repositoryGraph);
            logger.info("Number of results that will be returned: "+retList.size());
        }catch(QueryExecutionException ex){
            logger.error("An error occured while getting the outgoing nodes of a resource URI. Returning an empty list.\n", ex);
        }
        return retList;
    }
}
