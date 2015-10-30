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
public class DimensionService {
    RepositoryManager repoManager;
    private static final Logger logger=Logger.getLogger(DimensionService.class);
    
    public DimensionService(RepositoryManager repositoryManager){
        this.repoManager=repositoryManager;
    }
    
    /**
     * This method is responsible for retrieving the Dimension that are related to a specific actor. 
     * The actor is given as a parameter. This method is responsible for translating the 
     * SPARQL template query, and returning the desired values back to the user.
     * The values are returned in a list containing maps (key-value pairs).
     * 
     * @param actor the given actor (either its URI or its name - depending on the template SPARQL query)
     * @return a list containing maps with key value pairs
     * @throws QueryExecutionException for any error that might occur during the execution of the query. */
    public List<Map<String,String>> getDimensionByActor(String actor) throws QueryExecutionException{
        List<Map<String,String>> retList=new ArrayList<>();
        String sparqlQuery=FundamentalQueriesResources.getSparqlQuery(FundamentalQueriesResources.DIMENSION_BY_ACTOR, actor);
        List<BindingSet> results=this.repoManager.query(sparqlQuery);
        logger.info("getDimensionByActor returned "+results.size()+" results");
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
     * This method is responsible for retrieving the Dimension that are related to a specific event. 
     * The event is given as a parameter. This method is responsible for translating the 
     * SPARQL template query, and returning the desired values back to the user.
     * The values are returned in a list containing maps (key-value pairs).
     * 
     * @param event the given event (either its URI or its name - depending on the template SPARQL query)
     * @return a list containing maps with key value pairs
     * @throws QueryExecutionException for any error that might occur during the execution of the query. */
    public List<Map<String,String>> getDimensionByEvent(String event) throws QueryExecutionException{
        List<Map<String,String>> retList=new ArrayList<>();
        String sparqlQuery=FundamentalQueriesResources.getSparqlQuery(FundamentalQueriesResources.DIMENSION_BY_EVENT, event);
        List<BindingSet> results=this.repoManager.query(sparqlQuery);
        logger.info("getDimensionByEvent returned "+results.size()+" results");
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
     * This method is responsible for retrieving the Dimension of a place. 
     * The place is given as a parameter. This method is responsible for translating the 
     * SPARQL template query, and returning the desired values back to the user.
     * The values are returned in a list containing maps (key-value pairs).
     * 
     * @param place the given place (either its URI or its name - depending on the template SPARQL query)
     * @return a list containing maps with key value pairs
     * @throws QueryExecutionException for any error that might occur during the execution of the query. */
    public List<Map<String,String>> getDimensionOfPlace(String place) throws QueryExecutionException{
        List<Map<String,String>> retList=new ArrayList<>();
        String sparqlQuery=FundamentalQueriesResources.getSparqlQuery(FundamentalQueriesResources.DIMENSION_OF_PLACE, place);
        List<BindingSet> results=this.repoManager.query(sparqlQuery);
        logger.info("getDimensionOfPlace returned "+results.size()+" results");
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
     * This method is responsible for retrieving the Dimension of a thing. 
     * The thing is given as a parameter. This method is responsible for translating the 
     * SPARQL template query, and returning the desired values back to the user.
     * The values are returned in a list containing maps (key-value pairs).
     * 
     * @param thing the given thing (either its URI or its name - depending on the template SPARQL query)
     * @return a list containing maps with key value pairs
     * @throws QueryExecutionException for any error that might occur during the execution of the query. */
    public List<Map<String,String>> getDimensionOfThing(String thing) throws QueryExecutionException{
        List<Map<String,String>> retList=new ArrayList<>();
        String sparqlQuery=FundamentalQueriesResources.getSparqlQuery(FundamentalQueriesResources.DIMENSION_OF_THING, thing);
        List<BindingSet> results=this.repoManager.query(sparqlQuery);
        logger.info("getDimensionOfThing returned "+results.size()+" results");
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