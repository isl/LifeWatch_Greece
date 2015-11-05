package eu.lifewatch.dataservices.middleware.fundamentalcategories;

import eu.lifewatch.core.impl.VirtuosoRepositoryManager;
import eu.lifewatch.dataservices.middlewarews.xmlbinding.SPARQLResultMap;
import eu.lifewatch.exception.QueryExecutionException;
import eu.lifewatch.service.impl.fundamental.DimensionService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * FundamentalCategories_SearchActor provides the functionality of searching for 
 * fundamental categories (i.e. Actor, Place, Event, etc). 
 * For more information about the usage of the SOAP services and the contents and 
 * manipulation of the returned structs please refer to the javadoc documentation of the 
 * DataServices-api.
 *
 * @author Yannis Marketakis (marketak 'at' ics 'dot' forth 'dot' gr)
 * @author Nikos Minadakis (minadakn 'at' ics 'dot' forth 'dot' gr)
 */
@WebService(serviceName = "FundamentalCategories_SearchDimension")
public class FundamentalCategories_SearchDimension {
    private static final Logger logger=Logger.getLogger(FundamentalCategories_SearchDimension.class);
    
    @WebMethod(operationName = "searchDimensionByActor")
    public List<SPARQLResultMap> searchDimensionByActor(@WebParam(name = "actor") String actor) {
        logger.info("Request for SearchDimensionByActor("+actor+")");
        List<SPARQLResultMap> retList=new ArrayList<>();
        try{
            ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
            VirtuosoRepositoryManager repoManager=context.getBean(VirtuosoRepositoryManager.class);
            DimensionService api=new DimensionService(repoManager);
            List<Map<String,String>> results=api.getDimensionByActor(actor);
            for(Map<String,String> result : results){
                SPARQLResultMap sparqlRes=new SPARQLResultMap();
                sparqlRes.sparqlResMap = new HashMap<>();
                for(String key : result.keySet()){
                    sparqlRes.sparqlResMap.put(key, result.get(key));
                }
                retList.add(sparqlRes);
            }
            logger.info("The result contains "+results.size()+" entries");
        }catch(QueryExecutionException ex){
            logger.error("An error occured while searching for Thing. Returning an empty List.\n", ex);
        }
        return retList;
    }

    @WebMethod(operationName = "searchDimensionByEvent")
    public List<SPARQLResultMap> searchDimensionByEvent(@WebParam(name = "event") String event) {
        logger.info("Request for SearchDimensionByEvent("+event+")");
        List<SPARQLResultMap> retList=new ArrayList<>();
        try{
            ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
            VirtuosoRepositoryManager repoManager=context.getBean(VirtuosoRepositoryManager.class);
            DimensionService api=new DimensionService(repoManager);
            List<Map<String,String>> results=api.getDimensionByEvent(event);
            for(Map<String,String> result : results){
                SPARQLResultMap sparqlRes=new SPARQLResultMap();
                sparqlRes.sparqlResMap = new HashMap<>();
                for(String key : result.keySet()){
                    sparqlRes.sparqlResMap.put(key, result.get(key));
                }
                retList.add(sparqlRes);
            }
            logger.info("The result contains "+results.size()+" entries");
        }catch(QueryExecutionException ex){
            logger.error("An error occured while searching for Thing. Returning an empty List.\n", ex);
        }
        return retList;
    }

    @WebMethod(operationName = "searchDimensionOfPlace")
    public List<SPARQLResultMap> searchDimensionOfPlace(@WebParam(name = "place") String place) {
        logger.info("Request for SearchDimensionOfPlace("+place+")");
        List<SPARQLResultMap> retList=new ArrayList<>();
        try{
            ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
            VirtuosoRepositoryManager repoManager=context.getBean(VirtuosoRepositoryManager.class);
            DimensionService api=new DimensionService(repoManager);
            List<Map<String,String>> results=api.getDimensionOfPlace(place);
            for(Map<String,String> result : results){
                SPARQLResultMap sparqlRes=new SPARQLResultMap();
                sparqlRes.sparqlResMap = new HashMap<>();
                for(String key : result.keySet()){
                    sparqlRes.sparqlResMap.put(key, result.get(key));
                }
                retList.add(sparqlRes);
            }
            logger.info("The result contains "+results.size()+" entries");
        }catch(QueryExecutionException ex){
            logger.error("An error occured while searching for Thing. Returning an empty List.\n", ex);
        }
        return retList;
    }

    @WebMethod(operationName = "searchDimensionOfThing")
    public List<SPARQLResultMap> searchDimensionOfThing(@WebParam(name = "thing") String thing) {
        logger.info("Request for SearchDimensionOfThing("+thing+")");
        List<SPARQLResultMap> retList=new ArrayList<>();
        try{
            ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
            VirtuosoRepositoryManager repoManager=context.getBean(VirtuosoRepositoryManager.class);
            DimensionService api=new DimensionService(repoManager);
            List<Map<String,String>> results=api.getDimensionOfThing(thing);
            for(Map<String,String> result : results){
                SPARQLResultMap sparqlRes=new SPARQLResultMap();
                sparqlRes.sparqlResMap = new HashMap<>();
                for(String key : result.keySet()){
                    sparqlRes.sparqlResMap.put(key, result.get(key));
                }
                retList.add(sparqlRes);
            }
            logger.info("The result contains "+results.size()+" entries");
        }catch(QueryExecutionException ex){
            logger.error("An error occured while searching for Thing. Returning an empty List.\n", ex);
        }
        return retList;
    }
}
