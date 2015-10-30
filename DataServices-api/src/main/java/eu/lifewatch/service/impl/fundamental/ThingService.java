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
 * @author Nikos Minadakis (minadakn 'at' ics 'dot' forth 'dot' gr)
 * @author Yannis Marketakis (marketak 'at' ics 'dot' forth 'dot' gr)
 */
public class ThingService {
    RepositoryManager repoManager;
    private static final Logger logger=Logger.getLogger(ThingService.class);
    
    public ThingService(RepositoryManager repositoryManager){
        this.repoManager=repositoryManager;
    }
    
    /**
     * This method is responsible for retrieving the Things from the given actor. 
     * The actor is given as a parameter. This method is responsible for translating the 
     * SPARQL template query, and returning the desired values back to the user.
     * The values are returned in a list containing maps (key-value pairs).
     * 
     * @param actor the given actor (either its URI or its name - depending on the template SPARQL query)
     * @return a list containing maps with key value pairs
     * @throws QueryExecutionException for any error that might occur during the execution of the query. */
    public List<Map<String,String>> getThingFromActor(String actor) throws QueryExecutionException{
        List<Map<String,String>> retList=new ArrayList<>();
        String sparqlQuery=FundamentalQueriesResources.getSparqlQuery(FundamentalQueriesResources.THING_FROM_ACTOR, actor);
        List<BindingSet> results=this.repoManager.query(sparqlQuery);
        logger.info("getThingFromActor returned "+results.size()+" results");
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
     * This method is responsible for retrieving the Things from the given event. 
     * The event is given as a parameter. This method is responsible for translating the 
     * SPARQL template query, and returning the desired values back to the user.
     * The values are returned in a list containing maps (key-value pairs).
     * 
     * @param event the event (either its URI or its name - depending on the template SPARQL query)
     * @return a list containing maps with key value pairs
     * @throws QueryExecutionException for any error that might occur during the execution of the query. */
    public List<Map<String,String>> getThingFromEvent(String event) throws QueryExecutionException{
        List<Map<String,String>> retList=new ArrayList<>();
        String sparqlQuery=FundamentalQueriesResources.getSparqlQuery(FundamentalQueriesResources.THING_FROM_EVENT, event);
        List<BindingSet> results=this.repoManager.query(sparqlQuery);
        logger.info("getThingFromEvent returned "+results.size()+" results");
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
     * This method is responsible for retrieving the Things from the given Thing. 
     * The thing is given as a parameter. This method is responsible for translating the 
     * SPARQL template query, and returning the desired values back to the user.
     * The values are returned in a list containing maps (key-value pairs).
     * 
     * @param thing the thing (either its URI or its name - depending on the template SPARQL query)
     * @return a list containing maps with key value pairs
     * @throws QueryExecutionException for any error that might occur during the execution of the query. */
    public List<Map<String,String>> getThingFromThing(String thing) throws QueryExecutionException{
        List<Map<String,String>> retList=new ArrayList<>();
        String sparqlQuery=FundamentalQueriesResources.getSparqlQuery(FundamentalQueriesResources.THING_FROM_THING, thing);
        List<BindingSet> results=this.repoManager.query(sparqlQuery);
        logger.info("getThingFromThing returned "+results.size()+" results");
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
     * This method is responsible for retrieving the Things from the given place. 
     * The place is given as a parameter. This method is responsible for translating the 
     * SPARQL template query, and returning the desired values back to the user.
     * The values are returned in a list containing maps (key-value pairs).
     * 
     * @param place the place (either its URI or its name - depending on the template SPARQL query)
     * @return a list containing maps with key value pairs
     * @throws QueryExecutionException for any error that might occur during the execution of the query. */
    public List<Map<String,String>> getThingFromPlace(String place) throws QueryExecutionException{
        List<Map<String,String>> retList=new ArrayList<>();
        String sparqlQuery=FundamentalQueriesResources.getSparqlQuery(FundamentalQueriesResources.THING_FROM_PLACE, place);
        List<BindingSet> results=this.repoManager.query(sparqlQuery);
        logger.info("getThingFromPlace returned "+results.size()+" results");
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
     * This method is responsible for retrieving the Things falling within a specific timespan.
     * The timespan is given as a parameter. This method is responsible for translating the 
     * SPARQL template query, and returning the desired values back to the user.
     * The values are returned in a list containing maps (key-value pairs).
     * 
     * @param timespan the particular timespan (either its URI or its name - depending on the template SPARQL query)
     * @return a list containing maps with key value pairs
     * @throws QueryExecutionException for any error that might occur during the execution of the query. */
    public List<Map<String,String>> getThingFromTime(String timespan) throws QueryExecutionException{
        List<Map<String,String>> retList=new ArrayList<>();
        String sparqlQuery=FundamentalQueriesResources.getSparqlQuery(FundamentalQueriesResources.THING_FROM_TIME, timespan);
        List<BindingSet> results=this.repoManager.query(sparqlQuery);
        logger.info("getThingFromTime returned "+results.size()+" results");
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
     * This method is responsible for retrieving the Things that refer to a specific event. 
     * The event is given as a parameter. This method is responsible for translating the 
     * SPARQL template query, and returning the desired values back to the user.
     * The values are returned in a list containing maps (key-value pairs).
     * 
     * @param event the event (either its URI or its name - depending on the template SPARQL query)
     * @return a list containing maps with key value pairs
     * @throws QueryExecutionException for any error that might occur during the execution of the query. */
    public List<Map<String,String>> getThingRefersToEvent(String event) throws QueryExecutionException{
        List<Map<String,String>> retList=new ArrayList<>();
        String sparqlQuery=FundamentalQueriesResources.getSparqlQuery(FundamentalQueriesResources.THING_REFERS_TO_EVENT, event);
        List<BindingSet> results=this.repoManager.query(sparqlQuery);
        logger.info("getThingRefersToEvent returned "+results.size()+" results");
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
     * This method is responsible for retrieving the Things that refer to another thing 
     * The thing is given as a parameter. This method is responsible for translating the 
     * SPARQL template query, and returning the desired values back to the user.
     * The values are returned in a list containing maps (key-value pairs).
     * 
     * @param thing the event (either its URI or its name - depending on the template SPARQL query)
     * @return a list containing maps with key value pairs
     * @throws QueryExecutionException for any error that might occur during the execution of the query. */
    public List<Map<String,String>> getThingRefersToThing(String thing) throws QueryExecutionException{
        List<Map<String,String>> retList=new ArrayList<>();
        String sparqlQuery=FundamentalQueriesResources.getSparqlQuery(FundamentalQueriesResources.THING_REFERS_TO_THING, thing);
        List<BindingSet> results=this.repoManager.query(sparqlQuery);
        logger.info("getThingRefersToThing returned "+results.size()+" results");
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
     * This method is responsible for retrieving the Things that has a specific dimension 
     * The dimension is given as a parameter. This method is responsible for translating the 
     * SPARQL template query, and returning the desired values back to the user.
     * The values are returned in a list containing maps (key-value pairs).
     * 
     * @param dimensionValue the dimension (either its URI or its name - depending on the template SPARQL query)
     * @return a list containing maps with key value pairs
     * @throws QueryExecutionException for any error that might occur during the execution of the query. */
    public List<Map<String,String>> getThingHasDimension(String dimensionValue) throws QueryExecutionException{
        List<Map<String,String>> retList=new ArrayList<>();
        String sparqlQuery=FundamentalQueriesResources.getSparqlQuery(FundamentalQueriesResources.THING_HAS_DIMENSION, dimensionValue);
        List<BindingSet> results=this.repoManager.query(sparqlQuery);
        logger.info("getThingHasDimension returned "+results.size()+" results");
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
     * This method is responsible for retrieving the Things that has a specific actor 
     * The actor is given as a parameter. This method is responsible for translating the 
     * SPARQL template query, and returning the desired values back to the user.
     * The values are returned in a list containing maps (key-value pairs).
     * 
     * @param actor the specific actor (either its URI or its name - depending on the template SPARQL query)
     * @return a list containing maps with key value pairs
     * @throws QueryExecutionException for any error that might occur during the execution of the query. */
    public List<Map<String,String>> getThingHasMetActor(String actor) throws QueryExecutionException{
        List<Map<String,String>> retList=new ArrayList<>();
        String sparqlQuery=FundamentalQueriesResources.getSparqlQuery(FundamentalQueriesResources.THING_HAS_MET_ACTOR, actor);
        List<BindingSet> results=this.repoManager.query(sparqlQuery);
        logger.info("getThingHasMetActor returned "+results.size()+" results");
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
     * This method is responsible for retrieving the Things that has met a specific place
     * The place is given as a parameter. This method is responsible for translating the 
     * SPARQL template query, and returning the desired values back to the user.
     * The values are returned in a list containing maps (key-value pairs).
     * 
     * @param place the specific place (either its URI or its name - depending on the template SPARQL query)
     * @return a list containing maps with key value pairs
     * @throws QueryExecutionException for any error that might occur during the execution of the query. */
    public List<Map<String,String>> getThingHasMetPlace(String place) throws QueryExecutionException{
        List<Map<String,String>> retList=new ArrayList<>();
        String sparqlQuery=FundamentalQueriesResources.getSparqlQuery(FundamentalQueriesResources.THING_HAS_MET_PLACE, place);
        List<BindingSet> results=this.repoManager.query(sparqlQuery);
        logger.info("getThingHasMetPlace returned "+results.size()+" results");
        for(BindingSet result : results){
            Map<String,String> record=new HashMap<>();
            for(String param : result.getBindingNames()){
                record.put(param, result.getBinding(param).getValue().stringValue());
            }
            retList.add(record);
        }
        System.out.println("FQ QUERY"+sparqlQuery);
        return retList;
    }
    
    /**
     * This method is responsible for retrieving the Things that has met a another thing
     * The thing is given as a parameter. This method is responsible for translating the 
     * SPARQL template query, and returning the desired values back to the user.
     * The values are returned in a list containing maps (key-value pairs).
     * 
     * @param thing the specific thing (either its URI or its name - depending on the template SPARQL query)
     * @return a list containing maps with key value pairs
     * @throws QueryExecutionException for any error that might occur during the execution of the query. */
    public List<Map<String,String>> getThingHasMetThing(String thing) throws QueryExecutionException{
        List<Map<String,String>> retList=new ArrayList<>();
        String sparqlQuery=FundamentalQueriesResources.getSparqlQuery(FundamentalQueriesResources.THING_HAS_MET_THING, thing);
        List<BindingSet> results=this.repoManager.query(sparqlQuery);
        logger.info("getThingHasMetThing returned "+results.size()+" results");
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
     * This method is responsible for retrieving the Things happened in a specific timespan
     * The timespan is given as a parameter. This method is responsible for translating the 
     * SPARQL template query, and returning the desired values back to the user.
     * The values are returned in a list containing maps (key-value pairs).
     * 
     * @param timespan the particular timespan (either its URI or its name - depending on the template SPARQL query)
     * @return a list containing maps with key value pairs
     * @throws QueryExecutionException for any error that might occur during the execution of the query. */
    public List<Map<String,String>> getThingHasMetTime(String timespan) throws QueryExecutionException{
        List<Map<String,String>> retList=new ArrayList<>();
        String sparqlQuery=FundamentalQueriesResources.getSparqlQuery(FundamentalQueriesResources.THING_HAS_MET_TIME, timespan);
        List<BindingSet> results=this.repoManager.query(sparqlQuery);
        logger.info("getThingHasMetTime returned "+results.size()+" results");
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
     * This method is responsible for retrieving the Things triggered by an actor.
     * The actor is given as a parameter. This method is responsible for translating the 
     * SPARQL template query, and returning the desired values back to the user.
     * The values are returned in a list containing maps (key-value pairs).
     * 
     * @param actor the particular actor (either its URI or its name - depending on the template SPARQL query)
     * @return a list containing maps with key value pairs
     * @throws QueryExecutionException for any error that might occur during the execution of the query. */
    public List<Map<String,String>> getThingByActor(String actor) throws QueryExecutionException{
        List<Map<String,String>> retList=new ArrayList<>();
        String sparqlQuery=FundamentalQueriesResources.getSparqlQuery(FundamentalQueriesResources.THING_BY_ACTOR, actor);
        List<BindingSet> results=this.repoManager.query(sparqlQuery);
        logger.info("getThingByActor returned "+results.size()+" results");
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
     * This method is responsible for retrieving the Things triggered by an event.
     * The event is given as a parameter. This method is responsible for translating the 
     * SPARQL template query, and returning the desired values back to the user.
     * The values are returned in a list containing maps (key-value pairs).
     * 
     * @param event the particular event (either its URI or its name - depending on the template SPARQL query)
     * @return a list containing maps with key value pairs
     * @throws QueryExecutionException for any error that might occur during the execution of the query. */
    public List<Map<String,String>> getThingByEvent(String event) throws QueryExecutionException{
        List<Map<String,String>> retList=new ArrayList<>();
        String sparqlQuery=FundamentalQueriesResources.getSparqlQuery(FundamentalQueriesResources.THING_BY_EVENT, event);
        List<BindingSet> results=this.repoManager.query(sparqlQuery);
        logger.info("getThingByEvent returned "+results.size()+" results");
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