package eu.lifewatch.dataservices.middleware.fundamentalcategories;

import eu.lifewatch.core.impl.VirtuosoRepositoryManager;
import eu.lifewatch.dataservices.middlewarews.xmlbinding.SPARQLResultMap;
import eu.lifewatch.exception.QueryExecutionException;
import eu.lifewatch.service.impl.fundamental.ThingService;
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
@WebService(serviceName = "FundamentalCategories_SearchThing")
public class FundamentalCategories_SearchThing {
    private static final Logger logger=Logger.getLogger(FundamentalCategories_SearchThing.class);
    
    @WebMethod(operationName = "searchThingByActor")
    public List<SPARQLResultMap> searchThingByActor(@WebParam(name = "actor") String actor) {
        logger.info("Request for SearchThingByActor("+actor+")");
        List<SPARQLResultMap> retList=new ArrayList<>();
        try{
            ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
            VirtuosoRepositoryManager repoManager=context.getBean(VirtuosoRepositoryManager.class);
            ThingService api=new ThingService(repoManager);
            List<Map<String,String>> results=api.getThingByActor(actor);
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

    @WebMethod(operationName = "searchThingByEvent")
    public List<SPARQLResultMap> searchThingByEvent(@WebParam(name = "event") String event) {
        logger.info("Request for SearchThingByEvent("+event+")");
        List<SPARQLResultMap> retList=new ArrayList<>();
        try{
            ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
            VirtuosoRepositoryManager repoManager=context.getBean(VirtuosoRepositoryManager.class);
            ThingService api=new ThingService(repoManager);
            List<Map<String,String>> results=api.getThingByEvent(event);
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

    @WebMethod(operationName = "searchThingFromActor")
    public List<SPARQLResultMap> searchThingFromActor(@WebParam(name = "actor") String actor) {
        logger.info("Request for SearchThingFromActor("+actor+")");
        List<SPARQLResultMap> retList=new ArrayList<>();
        try{
            ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
            VirtuosoRepositoryManager repoManager=context.getBean(VirtuosoRepositoryManager.class);
            ThingService api=new ThingService(repoManager);
            List<Map<String,String>> results=api.getThingFromActor(actor);
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

    @WebMethod(operationName = "searchThingFromEvent")
    public List<SPARQLResultMap> searchThingFromEvent(@WebParam(name = "event") String event) {
        logger.info("Request for SearchThingFromEvent("+event+")");
        List<SPARQLResultMap> retList=new ArrayList<>();
        try{
            ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
            VirtuosoRepositoryManager repoManager=context.getBean(VirtuosoRepositoryManager.class);
            ThingService api=new ThingService(repoManager);
            List<Map<String,String>> results=api.getThingFromEvent(event);
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

    @WebMethod(operationName = "searchThingFromPlace")
    public List<SPARQLResultMap> searchThingFromPlace(@WebParam(name = "place") String place) {
        logger.info("Request for SearchThingFromPlace("+place+")");
        List<SPARQLResultMap> retList=new ArrayList<>();
        try{
            ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
            VirtuosoRepositoryManager repoManager=context.getBean(VirtuosoRepositoryManager.class);
            ThingService api=new ThingService(repoManager);
            List<Map<String,String>> results=api.getThingFromPlace(place);
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

    @WebMethod(operationName = "searchThingFromThing")
    public List<SPARQLResultMap> searchThingFromThing(@WebParam(name = "thing") String thing) {
        logger.info("Request for SearchThingFromThing("+thing+")");
        List<SPARQLResultMap> retList=new ArrayList<>();
        try{
            ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
            VirtuosoRepositoryManager repoManager=context.getBean(VirtuosoRepositoryManager.class);
            ThingService api=new ThingService(repoManager);
            List<Map<String,String>> results=api.getThingFromThing(thing);
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

    @WebMethod(operationName = "searchThingFromTime")
    public List<SPARQLResultMap> searchThingFromTime(@WebParam(name = "timespan") String timespan) {
        logger.info("Request for SearchThingFromTime("+timespan+")");
        List<SPARQLResultMap> retList=new ArrayList<>();
        try{
            ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
            VirtuosoRepositoryManager repoManager=context.getBean(VirtuosoRepositoryManager.class);
            ThingService api=new ThingService(repoManager);
            List<Map<String,String>> results=api.getThingFromTime(timespan);
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

    @WebMethod(operationName = "searchThingHasDimension")
    public List<SPARQLResultMap> searchThingHasDimension(@WebParam(name = "dimesionValue") String dimesionValue) {
        logger.info("Request for SearchThingHasDimension("+dimesionValue+")");
        List<SPARQLResultMap> retList=new ArrayList<>();
        try{
            ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
            VirtuosoRepositoryManager repoManager=context.getBean(VirtuosoRepositoryManager.class);
            ThingService api=new ThingService(repoManager);
            List<Map<String,String>> results=api.getThingHasDimension(dimesionValue);
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

    @WebMethod(operationName = "searchThingHasMetActor")
    public List<SPARQLResultMap> searchThingHasMetActor(@WebParam(name = "actor") String actor) {
        logger.info("Request for SearchThingHasMetActor("+actor+")");
        List<SPARQLResultMap> retList=new ArrayList<>();
        try{
            ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
            VirtuosoRepositoryManager repoManager=context.getBean(VirtuosoRepositoryManager.class);
            ThingService api=new ThingService(repoManager);
            List<Map<String,String>> results=api.getThingHasMetActor(actor);
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

    @WebMethod(operationName = "searchThingHasMetPlace")
    public List<SPARQLResultMap> searchThingHasMetPlace(@WebParam(name = "place") String place) {
        logger.info("Request for SearchThingHasMetPlace("+place+")");
        List<SPARQLResultMap> retList=new ArrayList<>();
        try{
            ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
            VirtuosoRepositoryManager repoManager=context.getBean(VirtuosoRepositoryManager.class);
            ThingService api=new ThingService(repoManager);
            List<Map<String,String>> results=api.getThingHasMetPlace(place);
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

    @WebMethod(operationName = "searchThingHasMetThing")
    public List<SPARQLResultMap> searchThingHasMetThing(@WebParam(name = "thing") String thing) {
        logger.info("Request for SearchThingHasMetThing("+thing+")");
        List<SPARQLResultMap> retList=new ArrayList<>();
        try{
            ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
            VirtuosoRepositoryManager repoManager=context.getBean(VirtuosoRepositoryManager.class);
            ThingService api=new ThingService(repoManager);
            List<Map<String,String>> results=api.getThingHasMetThing(thing);
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

    @WebMethod(operationName = "searchThingHasMetTime")
    public List<SPARQLResultMap> searchThingHasMetTime(@WebParam(name = "timespan") String timespan) {
        logger.info("Request for SearchThingHasMetTime("+timespan+")");
        List<SPARQLResultMap> retList=new ArrayList<>();
        try{
            ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
            VirtuosoRepositoryManager repoManager=context.getBean(VirtuosoRepositoryManager.class);
            ThingService api=new ThingService(repoManager);
            List<Map<String,String>> results=api.getThingHasMetTime(timespan);
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

    @WebMethod(operationName = "searchThingRefersToEvent")
    public List<SPARQLResultMap> searchThingRefersToEvent(@WebParam(name = "event") String event) {
        logger.info("Request for SearchThingRefersToEvent("+event+")");
        List<SPARQLResultMap> retList=new ArrayList<>();
        try{
            ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
            VirtuosoRepositoryManager repoManager=context.getBean(VirtuosoRepositoryManager.class);
            ThingService api=new ThingService(repoManager);
            List<Map<String,String>> results=api.getThingRefersToEvent(event);
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

    @WebMethod(operationName = "searchThingRefersToThing")
    public List<SPARQLResultMap> searchThingRefersToThing(@WebParam(name = "thing") String thing) {
        logger.info("Request for SearchThingRefersToThing("+thing+")");
        List<SPARQLResultMap> retList=new ArrayList<>();
        try{
            ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
            VirtuosoRepositoryManager repoManager=context.getBean(VirtuosoRepositoryManager.class);
            ThingService api=new ThingService(repoManager);
            List<Map<String,String>> results=api.getThingRefersToThing(thing);
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
