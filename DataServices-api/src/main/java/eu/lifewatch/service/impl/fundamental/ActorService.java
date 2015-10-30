package eu.lifewatch.service.impl.fundamental;

import eu.lifewatch.core.api.RepositoryManager;
import eu.lifewatch.exception.QueryExecutionException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.openrdf.query.BindingSet;

/**
 * The class ActorService contains the necessary methods for retrieving various 
 * types of actors based on other resources (e.g. place, thing, event, time, etc.)
 * 
 * @author Nikos Minadakis (minadakn 'at' ics 'dot' forth 'dot' gr)
 * @author Yannis Marketakis (marketak 'at' ics 'dot' forth 'dot' gr)
 */
public class ActorService {
    RepositoryManager repoManager;
    private static final Logger logger=Logger.getLogger(ActorService.class);
    
    /**Creates a new ActorService instance using the given RepositoryManager connector
     * 
     * @param repositoryManager the connector that will be used for accessing and 
     * retrieving information from the particular triple-store */
    public ActorService(RepositoryManager repositoryManager){
        this.repoManager=repositoryManager;
    }
    
    /**
     * This method is responsible for retrieving the Actors that are related to a specific place. 
     * The place is given as a parameter. This method is responsible for translating the 
     * SPARQL template query, and returning the desired values back to the user.
     * The values are returned in a list containing maps (key-value pairs).
     * 
     * @param place the given place (either its URI or its name - depending on the template SPARQL query)
     * @return the results as a list containing maps with key-value pairs
     * @throws QueryExecutionException for any error that might occur during the execution of the query. */
    public List<Map<String,String>> getActorHasMetPlace(String place) throws QueryExecutionException{
        List<Map<String,String>> retList=new ArrayList<>();
        String sparqlQuery=FundamentalQueriesResources.getSparqlQuery(FundamentalQueriesResources.ACTOR_HAS_MET_PLACE, place);
        List<BindingSet> results=this.repoManager.query(sparqlQuery);
        logger.info("getActorHasMetPlace returned "+results.size()+" results");
        for(BindingSet result : results){
            Map<String,String> record=new HashMap<>();
            for(String param : result.getBindingNames()){
                record.put(param, result.getBinding(param).getValue().stringValue());
            }
            retList.add(record);
        }
        return retList;
    }
    
    /**
     * This method is responsible for retrieving the Actors that are related to a specific thing. 
     * The thing is given as a parameter. This method is responsible for translating the 
     * SPARQL template query, and returning the desired values back to the user.
     * The values are returned in a list containing maps (key-value pairs).
     * 
     * @param thing the given place (either its URI or its name - depending on the template SPARQL query)
     * @return the results as a list containing maps with key-value pairs
     * @throws QueryExecutionException for any error that might occur during the execution of the query. */
    public List<Map<String,String>> getActorHasMetThing(String thing) throws QueryExecutionException{
        List<Map<String,String>> retList=new ArrayList<>();
        String sparqlQuery=FundamentalQueriesResources.getSparqlQuery(FundamentalQueriesResources.ACTOR_HAS_MET_THING, thing);
        List<BindingSet> results=this.repoManager.query(sparqlQuery);
        logger.info("getActorHasMetPlace returned "+results.size()+" results");
        for(BindingSet result : results){
            Map<String,String> record=new HashMap<>();
            for(String param : result.getBindingNames()){
                record.put(param, result.getBinding(param).getValue().stringValue());
            }
            retList.add(record);
        }
        return retList;
    }
    
    /**
     * This method is responsible for retrieving the Actors that are being referred to a specific event.
     * The event is given as a parameter. This method is responsible for translating the 
     * SPARQL template query, and returning the desired values back to the user.
     * The values are returned in a list containing maps (key-value pairs).
     * 
     * @param event the given place (either its URI or its name - depending on the template SPARQL query)
     * @return the results as a list containing maps with key-value pairs
     * @throws QueryExecutionException for any error that might occur during the execution of the query. */
    public List<Map<String,String>> getActorRefersToEvent(String event) throws QueryExecutionException{
        List<Map<String,String>> retList=new ArrayList<>();
        String sparqlQuery=FundamentalQueriesResources.getSparqlQuery(FundamentalQueriesResources.ACTOR_REFERS_TO_EVENT, event);
        List<BindingSet> results=this.repoManager.query(sparqlQuery);
        logger.info("getActorHasMetPlace returned "+results.size()+" results");
        for(BindingSet result : results){
            Map<String,String> record=new HashMap<>();
            for(String param : result.getBindingNames()){
                record.put(param, result.getBinding(param).getValue().stringValue());
            }
            retList.add(record);
        }
        return retList;
    }
    
    /**
     * This method is responsible for retrieving the Actors that are being referred to a specific thing.
     * The thing is given as a parameter. This method is responsible for translating the 
     * SPARQL template query, and returning the desired values back to the user.
     * The values are returned in a list containing maps (key-value pairs).
     * 
     * @param thing the given place (either its URI or its name - depending on the template SPARQL query)
     * @return the results as a list containing maps with key-value pairs
     * @throws QueryExecutionException for any error that might occur during the execution of the query. */
    public List<Map<String,String>> getActorRefersToThing(String thing) throws QueryExecutionException{
        List<Map<String,String>> retList=new ArrayList<>();
        String sparqlQuery=FundamentalQueriesResources.getSparqlQuery(FundamentalQueriesResources.ACTOR_REFERS_TO_THING, thing);
        List<BindingSet> results=this.repoManager.query(sparqlQuery);
        logger.info("getActorHasMetPlace returned "+results.size()+" results");
        for(BindingSet result : results){
            Map<String,String> record=new HashMap<>();
            for(String param : result.getBindingNames()){
                record.put(param, result.getBinding(param).getValue().stringValue());
            }
            retList.add(record);
        }
        return retList;
    }
    
    /**
     * This method is responsible for retrieving the Actors that are owners or creators of a specific thing.
     * The thing is given as a parameter. This method is responsible for translating the 
     * SPARQL template query, and returning the desired values back to the user.
     * The values are returned in a list containing maps (key-value pairs).
     * 
     * @param thing the given thing (either its URI or its name - depending on the template SPARQL query)
     * @return the results as a list containing maps with key-value pairs
     * @throws QueryExecutionException for any error that might occur during the execution of the query. */
    public List<Map<String,String>> getActorIsOwnerOrCreatorOfThing(String thing) throws QueryExecutionException{
        List<Map<String,String>> retList=new ArrayList<>();
        String sparqlQuery=FundamentalQueriesResources.getSparqlQuery(FundamentalQueriesResources.ACTOR_IS_OWNER_OR_CREATOR_OF_THING, thing);
        List<BindingSet> results=this.repoManager.query(sparqlQuery);
        logger.info("getActorHasMetPlace returned "+results.size()+" results");
        for(BindingSet result : results){
            Map<String,String> record=new HashMap<>();
            for(String param : result.getBindingNames()){
                record.put(param, result.getBinding(param).getValue().stringValue());
            }
            retList.add(record);
        }
        return retList;
    }
    
    /**
     * This method is responsible for retrieving the Actors that fall within a particular place.
     * The place is given as a parameter. This method is responsible for translating the 
     * SPARQL template query, and returning the desired values back to the user.
     * The values are returned in a list containing maps (key-value pairs).
     * 
     * @param place the given place (either its URI or its name - depending on the template SPARQL query)
     * @return the results as a list containing maps with key-value pairs
     * @throws QueryExecutionException for any error that might occur during the execution of the query. */
    public List<Map<String,String>> getActorFromPlace(String place) throws QueryExecutionException{
        List<Map<String,String>> retList=new ArrayList<>();
        String sparqlQuery=FundamentalQueriesResources.getSparqlQuery(FundamentalQueriesResources.ACTOR_FROM_PLACE, place);
        List<BindingSet> results=this.repoManager.query(sparqlQuery);
        logger.info("getActorFromPlace returned "+results.size()+" results");
        for(BindingSet result : results){
            Map<String,String> record=new HashMap<>();
            for(String param : result.getBindingNames()){
                record.put(param, result.getBinding(param).getValue().stringValue());
            }
            retList.add(record);
        }
        return retList;
    }
    
    /**
     * This method is responsible for retrieving the Actors that fall within a particular timespan.
     * The timespan is given as a parameter. This method is responsible for translating the 
     * SPARQL template query, and returning the desired values back to the user.
     * The values are returned in a list containing maps (key-value pairs).
     * 
     * @param timespan the given timespan (either its URI or its name - depending on the template SPARQL query)
     * @return the results as a list containing maps with key-value pairs
     * @throws QueryExecutionException for any error that might occur during the execution of the query. */
    public List<Map<String,String>> getActorFromTime(String timespan) throws QueryExecutionException{
        List<Map<String,String>> retList=new ArrayList<>();
        String sparqlQuery=FundamentalQueriesResources.getSparqlQuery(FundamentalQueriesResources.ACTOR_FROM_TIME, timespan);
        List<BindingSet> results=this.repoManager.query(sparqlQuery);
        logger.info("getActorFromTime returned "+results.size()+" results");
        for(BindingSet result : results){
            Map<String,String> record=new HashMap<>();
            for(String param : result.getBindingNames()){
                record.put(param, result.getBinding(param).getValue().stringValue());
            }
            retList.add(record);
        }
        return retList;
    }
    
    /**
     * This method is responsible for retrieving the Actors that measured specific dimensions.
     * The dimension is given as a parameter. This method is responsible for translating the 
     * SPARQL template query, and returning the desired values back to the user.
     * The values are returned in a list containing maps (key-value pairs).
     * 
     * @param dimension the given dimension (either its URI or its name - depending on the template SPARQL query)
     * @return the results as a list containing maps with key-value pairs
     * @throws QueryExecutionException for any error that might occur during the execution of the query. */
    public List<Map<String,String>> getActorMeasuredDimension(String dimension) throws QueryExecutionException{
        List<Map<String,String>> retList=new ArrayList<>();
        String sparqlQuery=FundamentalQueriesResources.getSparqlQuery(FundamentalQueriesResources.ACTOR_MEASURED_DIMENSION, dimension);
        List<BindingSet> results=this.repoManager.query(sparqlQuery);
        logger.info("getActorFromTime returned "+results.size()+" results");
        for(BindingSet result : results){
            Map<String,String> record=new HashMap<>();
            for(String param : result.getBindingNames()){
                record.put(param, result.getBinding(param).getValue().stringValue());
            }
            retList.add(record);
        }
        return retList;
    }
}