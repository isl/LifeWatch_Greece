package eu.lifewatch.dataservices.middleware.directoryservice;

import eu.lifewatch.core.impl.VirtuosoRepositoryManager;
import eu.lifewatch.exception.QueryExecutionException;
import eu.lifewatch.service.impl.DirectoryService;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * DirectoryService-Update provides the functionality to removed resources from the Directory 
 * For more information about the usage of the SOAP services and the contents and 
 * manipulation of the returned structs please refer to the javadoc documentation of the 
 * DataServices-api.
 *
 * @author Yannis Marketakis (marketak 'at' ics 'dot' forth 'dot' gr)
 * @author Nikos Minadakis (minadakn 'at' ics 'dot' forth 'dot' gr)
 */
@WebService(serviceName = "DirectoryService_Delete")
public class DirectoryService_Delete {
    private static final Logger logger=Logger.getLogger(DirectoryService_Delete.class);

    @WebMethod(operationName = "deleteTriples")
    public boolean deleteTriples(@WebParam(name = "subject") String subject,
                                 @WebParam(name = "predicate") String predicate,
                                 @WebParam(name = "object") String object,
                                 @WebParam(name = "repositoryGraph") String repositoryGraph) {
        logger.info("Request for deleteTriple("+subject+","+predicate+","+object+","+repositoryGraph+")");
        try{
            ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
            VirtuosoRepositoryManager repoManager=context.getBean(VirtuosoRepositoryManager.class);
            DirectoryService api=new DirectoryService(repoManager);
            api.deleteTriples(subject,predicate, object, repositoryGraph);
            logger.debug("Sucessfully removed the given triple - returning TRUE");
            return true;
        }catch(BeansException | QueryExecutionException ex){
            logger.error("An error occured while removing a triple from the directory",ex);
            logger.debug("The triple has not been removed - returning FALSE");
            return false;
        }
    }

    @WebMethod(operationName = "deleteTriplesHavingLiteral")
    public boolean deleteTriplesHavingLiteral(@WebParam(name = "literalValue") String literalValue,
                                              @WebParam(name = "repositoryGraph") String repositoryGraph) {
        logger.info("Request for deleteTriplesHavingLiteral("+literalValue+","+repositoryGraph+")");
        try{
            ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
            VirtuosoRepositoryManager repoManager=context.getBean(VirtuosoRepositoryManager.class);
            DirectoryService api=new DirectoryService(repoManager);
            api.deleteTriplesHavingLiteral(literalValue, repositoryGraph);
            logger.debug("Sucessfully removed the triples having the given literal value - returning TRUE");
            return true;
        }catch(BeansException | QueryExecutionException ex){
            logger.error("An error occured while removing triples containing the given literal value",ex);
            logger.debug("The triples have not been removed - returning FALSE");
            return false;
        }
    }

    @WebMethod(operationName = "deleteTriplesHavingProperty")
    public boolean deleteTriplesHavingProperty(@WebParam(name = "property") String property,
                                              @WebParam(name = "repositoryGraph") String repositoryGraph) {
        logger.info("Request for deleteTriplesHavingProperty("+property+","+repositoryGraph+")");
        try{
            ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
            VirtuosoRepositoryManager repoManager=context.getBean(VirtuosoRepositoryManager.class);
            DirectoryService api=new DirectoryService(repoManager);
            api.deleteTriplesHavingProperty(property, repositoryGraph);
            logger.debug("Sucessfully removed the triples having the given property - returning TRUE");
            return true;
        }catch(BeansException | QueryExecutionException ex){
            logger.error("An error occured while removing triples containing the given property",ex);
            logger.debug("The triples have not been removed - returning FALSE");
            return false;
        }
    }

    @WebMethod(operationName = "deleteTriplesHavingResource")
    public boolean deleteTriplesHavingResource(@WebParam(name = "resourceURI") String resourceURI,
                                              @WebParam(name = "repositoryGraph") String repositoryGraph) {
        logger.info("Request for deleteTriplesHavingResource("+resourceURI+","+repositoryGraph+")");
        try{
            ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
            VirtuosoRepositoryManager repoManager=context.getBean(VirtuosoRepositoryManager.class);
            DirectoryService api=new DirectoryService(repoManager);
            api.deleteTriplesHavingResource(resourceURI, repositoryGraph);
            logger.debug("Sucessfully removed the triples having the given resource - returning TRUE");
            return true;
        }catch(BeansException | QueryExecutionException ex){
            logger.error("An error occured while removing triples containing the given resource",ex);
            logger.debug("The triples have not been removed - returning FALSE");
            return false;
        }
    }
}