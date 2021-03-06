package eu.lifewatch.dataservices.middleware.metadatarepository;

import eu.lifewatch.core.impl.VirtuosoRepositoryManager;
import eu.lifewatch.exception.QueryExecutionException;
import eu.lifewatch.service.impl.MetadataRepositoryService;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * MetadataRepository-Insert provides the functionality to update information from the metadata catalogue. 
 * For more information about the usage of the SOAP services and the contents and 
 * manipulation of the returned structs please refer to the javadoc documentation of the 
 * DataServices-api.
 *
 * @author Yannis Marketakis (marketak 'at' ics 'dot' forth 'dot' gr)
 * @author Nikos Minadakis (minadakn 'at' ics 'dot' forth 'dot' gr)
 */
@WebService(serviceName = "MetadataRepository_Update")
public class MetadataRepository_Update {
    private static final Logger logger=Logger.getLogger(MetadataRepository_Update.class);
    
    @WebMethod(operationName = "updateLiteral")
    public boolean updateLiteral(@WebParam(name = "originalLiteral") String originalLiteral,
                                 @WebParam(name = "newLiteral") String newLiteral,
                                 @WebParam(name = "repositoryGraph") String repositoryGraph){
        logger.info("Request for updateLiteral("+originalLiteral+","+newLiteral+","+repositoryGraph+")");
        try{
            ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
            VirtuosoRepositoryManager repoManager=context.getBean(VirtuosoRepositoryManager.class);
            MetadataRepositoryService api=new MetadataRepositoryService(repoManager);
            api.updateLiteral(originalLiteral, newLiteral, repositoryGraph);
            logger.debug("Sucessfully updated the occurences of the given literal value- returning TRUE");
            return true;
        }catch(BeansException | QueryExecutionException ex){
            logger.error("An error occured while updating the occurences of a literal value",ex);
            logger.debug("The occurences of the literal value have not been updated - returning FALSE");
            return false;
        }
    }
    
    @WebMethod(operationName = "updateProperty")
    public boolean updateProperty(@WebParam(name = "originalProperty") String originalProperty,
                                  @WebParam(name = "newProperty") String newProperty,
                                  @WebParam(name = "repositoryGraph") String repositoryGraph){
        logger.info("Request for updateProperty("+originalProperty+","+newProperty+","+repositoryGraph+")");
        try{
            ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
            VirtuosoRepositoryManager repoManager=context.getBean(VirtuosoRepositoryManager.class);
            MetadataRepositoryService api=new MetadataRepositoryService(repoManager);
            api.updateProperty(originalProperty, newProperty, repositoryGraph);
            logger.debug("Sucessfully updated the occurences of the given property - returning TRUE");
            return true;
        }catch(BeansException | QueryExecutionException ex){
            logger.error("An error occured while updating the occurences of a property",ex);
            logger.debug("The occurences of the property have not been updated - returning FALSE");
            return false;
        }
    }
    
    @WebMethod(operationName = "updateResource")
    public boolean updateResource(@WebParam(name = "originalResource") String originalResource,
                                  @WebParam(name = "newResource") String newResource,
                                  @WebParam(name = "repositoryGraph") String repositoryGraph){
        logger.info("Request for updateResource("+originalResource+","+newResource+","+repositoryGraph+")");
        try{
            ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
            VirtuosoRepositoryManager repoManager=context.getBean(VirtuosoRepositoryManager.class);
            MetadataRepositoryService api=new MetadataRepositoryService(repoManager);
            api.updateResource(originalResource, newResource, repositoryGraph);
            logger.debug("Sucessfully updated the occurences of the given resource - returning TRUE");
            return true;
        }catch(BeansException | QueryExecutionException ex){
            logger.error("An error occured while updating the occurences of a resource",ex);
            logger.debug("The occurences of the resource have not been updated - returning FALSE");
            return false;
        }
    }
}
