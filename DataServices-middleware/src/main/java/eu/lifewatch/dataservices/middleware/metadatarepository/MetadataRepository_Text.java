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
 * MetadataRepository-Text provides the functionality of producing text (stories) from some simple keywords (i.e. the scientific names). 
 * For more information about the usage of the SOAP services and the contents and 
 * manipulation of the returned structs please refer to the javadoc documentation of the 
 * DataServices-api.
 *
 * @author Yannis Marketakis (marketak 'at' ics 'dot' forth 'dot' gr)
 * @author Nikos Minadakis (minadakn 'at' ics 'dot' forth 'dot' gr)
 */
@WebService(serviceName = "MetadataRepository_Text")
public class MetadataRepository_Text {
    private static final Logger logger=Logger.getLogger(MetadataRepository_Text.class);

    @WebMethod(operationName = "produceText")
    public String produceText(@WebParam(name = "speciesName") String speciesName, 
                              @WebParam(name = "browseURL") String browseURL, 
                              @WebParam(name = "repositoryGraph") String repositoryGraph) {
        logger.info("Request for produceText("+speciesName+","+browseURL+","+repositoryGraph+")");
        try{
            ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
            VirtuosoRepositoryManager repoManager=context.getBean(VirtuosoRepositoryManager.class);
            MetadataRepositoryService api=new MetadataRepositoryService(repoManager);
            return api.produceText(speciesName, browseURL, repositoryGraph);
        }catch(BeansException | QueryExecutionException ex){
            logger.error("An error occured while producing text. An empty String will be returned", ex);
            return "";
        }
    }
}