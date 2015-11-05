package eu.lifewatch.dataservices.middleware.fundamentalcategories;

import eu.lifewatch.core.impl.VirtuosoRepositoryManager;
import eu.lifewatch.dataservices.middlewarews.xmlbinding.SPARQLResultMap;
import eu.lifewatch.exception.QueryExecutionException;
import eu.lifewatch.service.impl.fundamental.ActorService;
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
@WebService(serviceName = "FundamentalCatagories_SearchActor")
public class FundamentalCategories_SearchActor {
    private static final Logger logger=Logger.getLogger(FundamentalCategories_SearchActor.class);
    
    @WebMethod(operationName = "searchActorFromPlace")
    public List<SPARQLResultMap> searchActorFromPlace(@WebParam(name = "place") String place) {
        logger.info("Request for SearchActorFromPlace("+place+")");
        List<SPARQLResultMap> retList=new ArrayList<>();
        try{
            ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
            VirtuosoRepositoryManager repoManager=context.getBean(VirtuosoRepositoryManager.class);
            ActorService api=new ActorService(repoManager);
            List<Map<String,String>> results=api.getActorFromPlace(place);
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

    @WebMethod(operationName = "searchActorFromTime")
    public List<SPARQLResultMap> searchActorFromTime(@WebParam(name = "timespan") String timespan) {
        logger.info("Request for SearchActorFromTime("+timespan+")");
        List<SPARQLResultMap> retList=new ArrayList<>();
        try{
            ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
            VirtuosoRepositoryManager repoManager=context.getBean(VirtuosoRepositoryManager.class);
            ActorService api=new ActorService(repoManager);
            List<Map<String,String>> results=api.getActorFromTime(timespan);
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

    @WebMethod(operationName = "searchActorHasMetPlace")
    public List<SPARQLResultMap> searchActorHasMetPlace(@WebParam(name = "place") String place) {
        logger.info("Request for SearchActorHasMetPlace("+place+")");
        List<SPARQLResultMap> retList=new ArrayList<>();
        try{
            ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
            VirtuosoRepositoryManager repoManager=context.getBean(VirtuosoRepositoryManager.class);
            ActorService api=new ActorService(repoManager);
            List<Map<String,String>> results=api.getActorHasMetPlace(place);
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

    @WebMethod(operationName = "searchActorHasMetThing")
    public List<SPARQLResultMap> searchActorHasMetThing(@WebParam(name = "thing") String thing) {
        logger.info("Request for SearchActorHasMetThing("+thing+")");
        List<SPARQLResultMap> retList=new ArrayList<>();
        try{
            ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
            VirtuosoRepositoryManager repoManager=context.getBean(VirtuosoRepositoryManager.class);
            ActorService api=new ActorService(repoManager);
            List<Map<String,String>> results=api.getActorHasMetThing(thing);
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

    @WebMethod(operationName = "searchActorIsOwnerOrCreatorOfThing")
    public List<SPARQLResultMap> searchActorIsOwnerOrCreatorOfThing(@WebParam(name = "thing") String thing) {
        logger.info("Request for SearchActorIsOwnerOrCreatorOfThing("+thing+")");
        List<SPARQLResultMap> retList=new ArrayList<>();
        try{
            ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
            VirtuosoRepositoryManager repoManager=context.getBean(VirtuosoRepositoryManager.class);
            ActorService api=new ActorService(repoManager);
            List<Map<String,String>> results=api.getActorIsOwnerOrCreatorOfThing(thing);
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

    @WebMethod(operationName = "searchActorMeasuredDimension")
    public List<SPARQLResultMap> searchActorMeasuredDimension(@WebParam(name = "dimension") String dimension) {
        logger.info("Request for SearchActorMeasuredDimension("+dimension+")");
        List<SPARQLResultMap> retList=new ArrayList<>();
        try{
            ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
            VirtuosoRepositoryManager repoManager=context.getBean(VirtuosoRepositoryManager.class);
            ActorService api=new ActorService(repoManager);
            List<Map<String,String>> results=api.getActorMeasuredDimension(dimension);
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

    @WebMethod(operationName = "searchActorRefersToEvent")
    public List<SPARQLResultMap> searchActorRefersToEvent(@WebParam(name = "event") String event) {
        logger.info("Request for SearchActorRefersToEvent("+event+")");
        List<SPARQLResultMap> retList=new ArrayList<>();
        try{
            ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
            VirtuosoRepositoryManager repoManager=context.getBean(VirtuosoRepositoryManager.class);
            ActorService api=new ActorService(repoManager);
            List<Map<String,String>> results=api.getActorRefersToEvent(event);
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

    @WebMethod(operationName = "searchActorRefersToThing")
    public List<SPARQLResultMap> searchActorRefersToThing(@WebParam(name = "thing") String thing) {
        logger.info("Request for SearchActorRefersToThing("+thing+")");
        List<SPARQLResultMap> retList=new ArrayList<>();
        try{
            ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
            VirtuosoRepositoryManager repoManager=context.getBean(VirtuosoRepositoryManager.class);
            ActorService api=new ActorService(repoManager);
            List<Map<String,String>> results=api.getActorRefersToThing(thing);
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
