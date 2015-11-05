package eu.lifewatch.dataservices.middleware.fundamentalcategories;

import eu.lifewatch.core.impl.VirtuosoRepositoryManager;
import eu.lifewatch.dataservices.middlewarews.xmlbinding.SPARQLResultMap;
import eu.lifewatch.exception.QueryExecutionException;
import eu.lifewatch.service.impl.fundamental.PlaceService;
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
@WebService(serviceName = "FundamentalCategories_SearchPlace")
public class FundamentalCategories_SearchPlace {
    private static final Logger logger=Logger.getLogger(FundamentalCategories_SearchPlace.class);
    
    @WebMethod(operationName = "searchPlaceHasDimension")
    public List<SPARQLResultMap> searchPlaceHasDimension(@WebParam(name = "dimension") String dimension) {
        logger.info("Request for SearchPlaceHasDimension("+dimension+")");
        List<SPARQLResultMap> retList=new ArrayList<>();
        try{
            ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
            VirtuosoRepositoryManager repoManager=context.getBean(VirtuosoRepositoryManager.class);
            PlaceService api=new PlaceService(repoManager);
            List<Map<String,String>> results=api.getPlaceHasDimension(dimension);
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

    @WebMethod(operationName = "searchPlaceHasMetThing")
    public List<SPARQLResultMap> searchPlaceHasMetThing(@WebParam(name = "thing") String thing) {
        logger.info("Request for SearchPlaceHasMetThing("+thing+")");
        List<SPARQLResultMap> retList=new ArrayList<>();
        try{
            ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
            VirtuosoRepositoryManager repoManager=context.getBean(VirtuosoRepositoryManager.class);
            PlaceService api=new PlaceService(repoManager);
            List<Map<String,String>> results=api.getPlaceHasMetThing(thing);
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

    @WebMethod(operationName = "searchPlaceHasPartPlace")
    public List<SPARQLResultMap> searchPlaceHasPartPlace(@WebParam(name = "place") String place) {
        logger.info("Request for SearchPlaceHasPartPlace("+place+")");
        List<SPARQLResultMap> retList=new ArrayList<>();
        try{
            ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
            VirtuosoRepositoryManager repoManager=context.getBean(VirtuosoRepositoryManager.class);
            PlaceService api=new PlaceService(repoManager);
            List<Map<String,String>> results=api.getPlaceHasPartPlace(place);
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

    @WebMethod(operationName = "searchPlaceIsPartOfPlace")
    public List<SPARQLResultMap> searchPlaceIsPartOfPlace(@WebParam(name = "place") String place) {
        logger.info("Request for SearchPlaceIsPartOfPlace("+place+")");
        List<SPARQLResultMap> retList=new ArrayList<>();
        try{
            ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
            VirtuosoRepositoryManager repoManager=context.getBean(VirtuosoRepositoryManager.class);
            PlaceService api=new PlaceService(repoManager);
            List<Map<String,String>> results=api.getPlaceIsPartOfPlace(place);
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
