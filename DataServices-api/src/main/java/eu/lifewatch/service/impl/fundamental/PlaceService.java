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
public class PlaceService {
    RepositoryManager repoManager;
    private static final Logger logger=Logger.getLogger(PlaceService.class);
    
    public PlaceService(RepositoryManager repositoryManager){
        this.repoManager=repositoryManager;
    }
    
    /**
     * This method is responsible for retrieving the Place that has the specific dimension. 
     * The dimension is given as a parameter. This method is responsible for translating the 
     * SPARQL template query, and returning the desired values back to the user.
     * The values are returned in a list containing maps (key-value pairs).
     * 
     * @param dimension the given dimension (either its URI or its name - depending on the template SPARQL query)
     * @return a list containing maps with key value pairs
     * @throws QueryExecutionException for any error that might occur during the execution of the query. */
    public List<Map<String,String>> getPlaceHasDimension(String dimension) throws QueryExecutionException{
        List<Map<String,String>> retList=new ArrayList<>();
        String sparqlQuery=FundamentalQueriesResources.getSparqlQuery(FundamentalQueriesResources.PLACE_HAS_DIMENSION, dimension);
        List<BindingSet> results=this.repoManager.query(sparqlQuery);
        logger.info("getPlaceHasDimension returned "+results.size()+" results");
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
     * This method is responsible for retrieving the Place that is related to a specific thing. 
     * The thing is given as a parameter. This method is responsible for translating the 
     * SPARQL template query, and returning the desired values back to the user.
     * The values are returned in a list containing maps (key-value pairs).
     * 
     * @param thing the given thing (either its URI or its name - depending on the template SPARQL query)
     * @return a list containing maps with key value pairs
     * @throws QueryExecutionException for any error that might occur during the execution of the query. */
    public List<Map<String,String>> getPlaceHasMetThing(String thing) throws QueryExecutionException{
        List<Map<String,String>> retList=new ArrayList<>();
        String sparqlQuery=FundamentalQueriesResources.getSparqlQuery(FundamentalQueriesResources.PLACE_HAS_MET_THING, thing);
        List<BindingSet> results=this.repoManager.query(sparqlQuery);
        logger.info("getPlaceHasMetThing returned "+results.size()+" results");
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
     * This method is responsible for retrieving the Place that has as part another place. 
     * The given place  provided as a parameter. This method is responsible for translating the 
     * SPARQL template query, and returning the desired values back to the user.
     * The values are returned in a list containing maps (key-value pairs).
     * 
     * @param place the given place (either its URI or its name - depending on the template SPARQL query)
     * @return a list containing maps with key value pairs
     * @throws QueryExecutionException for any error that might occur during the execution of the query. */
    public List<Map<String,String>> getPlaceHasPartPlace(String place) throws QueryExecutionException{
        List<Map<String,String>> retList=new ArrayList<>();
        String sparqlQuery=FundamentalQueriesResources.getSparqlQuery(FundamentalQueriesResources.PLACE_HAS_PART_PLACE, place);
        List<BindingSet> results=this.repoManager.query(sparqlQuery);
        logger.info("getPlaceHasPartPlace returned "+results.size()+" results");
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
     * This method is responsible for retrieving the Place that consists of another place. 
     * The given place  provided as a parameter. This method is responsible for translating the 
     * SPARQL template query, and returning the desired values back to the user.
     * The values are returned in a list containing maps (key-value pairs).
     * 
     * @param place the given place (either its URI or its name - depending on the template SPARQL query)
     * @return a list containing maps with key value pairs
     * @throws QueryExecutionException for any error that might occur during the execution of the query. */
    public List<Map<String,String>> getPlaceIsPartOfPlace(String place) throws QueryExecutionException{
        List<Map<String,String>> retList=new ArrayList<>();
        String sparqlQuery=FundamentalQueriesResources.getSparqlQuery(FundamentalQueriesResources.PLACE_IS_PART_OF_PLACE, place);
        List<BindingSet> results=this.repoManager.query(sparqlQuery);
        logger.info("getPlaceIsPartOfPlace returned "+results.size()+" results");
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