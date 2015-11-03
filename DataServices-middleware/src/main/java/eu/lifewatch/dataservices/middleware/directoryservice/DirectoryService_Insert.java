package eu.lifewatch.dataservices.middleware.directoryservice;

import eu.lifewatch.core.impl.VirtuosoRepositoryManager;
import eu.lifewatch.core.model.DirectoryStruct;
import eu.lifewatch.exception.QueryExecutionException;
import eu.lifewatch.exception.URIValidationException;
import eu.lifewatch.service.impl.DirectoryService;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * DirectoryService-Insert provides the functionality to add new datasets into the Directory 
 * For more information about the usage of the SOAP services and the contents and 
 * manipulation of the returned structs please refer to the javadoc documentation of the 
 * DataServices-api.
 *
 * @author Yannis Marketakis (marketak 'at' ics 'dot' forth 'dot' gr)
 * @author Nikos Minadakis (minadakn 'at' ics 'dot' forth 'dot' gr)
 */
@WebService(serviceName = "DirectoryService_Insert")
public class DirectoryService_Insert {
    private static final Logger logger=Logger.getLogger(DirectoryService_Insert.class);
    
    @WebMethod(operationName = "insertDataset")
    public boolean insertDataset(@WebParam(name = "struct") DirectoryStruct struct,
                                 @WebParam(name = "repositoryGraph") String repositoryGraph){
        logger.info("Request for searchDataset("+struct.getDatasetID()+","+repositoryGraph+")");
        try{
            ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
            VirtuosoRepositoryManager repoManager=context.getBean(VirtuosoRepositoryManager.class);
            DirectoryService api=new DirectoryService(repoManager);
            api.insertStruct(struct, repositoryGraph);
            logger.debug("Sucessfully added new DirectoryStruct - returning TRUE");
            return true;
        }catch(BeansException | QueryExecutionException | URIValidationException ex){
            logger.error("An error occured while adding a new DirectoryStruct",ex);
            logger.debug("The DirectoryStruct was not added - returning FALSE");
            return false;
        }
    }
}