package eu.lifewatch.dataservices.middleware.fundamentalcategories;

import eu.lifewatch.core.impl.VirtuosoRepositoryManager;
import eu.lifewatch.dataservices.middlewarews.xmlbinding.SPARQLResultMap;
import eu.lifewatch.exception.QueryExecutionException;
import eu.lifewatch.service.impl.fundamental.EventService;
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
@WebService(serviceName = "FundamentalCategories_SearchEvent")
public class FundamentalCategories_SearchEvent {
    private static final Logger logger=Logger.getLogger(FundamentalCategories_SearchEvent.class);

    @WebMethod(operationName = "searchEventByActor")
    public List<SPARQLResultMap> searchEventByActor(@WebParam(name = "actor") String actor) {
        logger.info("Request for SearchEventByActor("+actor+")");
        List<SPARQLResultMap> retList=new ArrayList<>();
        try{
            ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
            VirtuosoRepositoryManager repoManager=context.getBean(VirtuosoRepositoryManager.class);
            EventService api=new EventService(repoManager);
            List<Map<String,String>> results=api.getEventByActor(actor);
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

    @WebMethod(operationName = "searchEventFromPlace")
    public List<SPARQLResultMap> searchEventFromPlace(@WebParam(name = "place") String place) {
        logger.info("Request for SearchEventFromPlace("+place+")");
        List<SPARQLResultMap> retList=new ArrayList<>();
        try{
            ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
            VirtuosoRepositoryManager repoManager=context.getBean(VirtuosoRepositoryManager.class);
            EventService api=new EventService(repoManager);
            List<Map<String,String>> results=api.getEventFromPlace(place);
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

    @WebMethod(operationName = "searchEventFromTime")
    public List<SPARQLResultMap> searchEventFromTime(@WebParam(name = "timespan") String timespan) {
        logger.info("Request for SearchEventFromTime("+timespan+")");
        List<SPARQLResultMap> retList=new ArrayList<>();
        try{
            ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
            VirtuosoRepositoryManager repoManager=context.getBean(VirtuosoRepositoryManager.class);
            EventService api=new EventService(repoManager);
            List<Map<String,String>> results=api.getEventFromTime(timespan);
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

    @WebMethod(operationName = "searchEventHasMetActor")
    public List<SPARQLResultMap> searchEventHasMetActor(@WebParam(name = "actor") String actor) {
        logger.info("Request for SearchEventHasMetActor("+actor+")");
        List<SPARQLResultMap> retList=new ArrayList<>();
        try{
            ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
            VirtuosoRepositoryManager repoManager=context.getBean(VirtuosoRepositoryManager.class);
            EventService api=new EventService(repoManager);
            List<Map<String,String>> results=api.getEventHasMetActor(actor);
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

    @WebMethod(operationName = "searchEventHasMetThing")
    public List<SPARQLResultMap> searchEventHasMetThing(@WebParam(name = "thing") String thing) {
        logger.info("Request for SearchEventHasMetThing("+thing+")");
        List<SPARQLResultMap> retList=new ArrayList<>();
        try{
            ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
            VirtuosoRepositoryManager repoManager=context.getBean(VirtuosoRepositoryManager.class);
            EventService api=new EventService(repoManager);
            List<Map<String,String>> results=api.getEventHasMetThing(thing);
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

    @WebMethod(operationName = "searchEventMeasuredDimension")
    public List<SPARQLResultMap> searchEventMeasuredDimension(@WebParam(name = "dimension") String dimension) {
        logger.info("Request for SearchEventMeasuredDimension("+dimension+")");
        List<SPARQLResultMap> retList=new ArrayList<>();
        try{
            ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
            VirtuosoRepositoryManager repoManager=context.getBean(VirtuosoRepositoryManager.class);
            EventService api=new EventService(repoManager);
            List<Map<String,String>> results=api.getEventMeasuredDimension(dimension);
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
