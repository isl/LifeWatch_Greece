package eu.lifewatch.dataservices.middleware.client.fundamentalcategories;

import java.util.List;
import org.apache.log4j.Logger;

/**
 * @author Yannis Marketakis (marketak 'at' ics 'dot' forth 'dot' gr)
 * @author Nikos Minadakis (minadakn 'at' ics 'dot' forth 'dot' gr)
 */
public class SearchEvent {
    private static final Logger logger=Logger.getLogger(SearchEvent.class);
    
    private static void searchEventByActor(){
        try{
            FundamentalCategoriesSearchEvent_Service service = new FundamentalCategoriesSearchEvent_Service();
            FundamentalCategoriesSearchEvent port = service.getFundamentalCategoriesSearchEventPort();
            String actor="actor";
            System.out.println("Searching for events by the actor: "+actor);
            List<SparqlResultMap> results = port.searchEventByActor(actor);
            System.out.println("Found "+results.size()+" results");
            results.stream().forEach((result) -> {
                result.getSparqlresults().sparqlresult.forEach(sparqlResult -> System.out.print(sparqlResult.getKey()+": "+sparqlResult.getValue()+"\t"));
                System.out.print("\n");
            });
        } catch (Exception ex) {
            logger.error("An error occured while search for actors using fundamental query services",ex);
        }
    }
    
    public static void main(String[] args){
        searchEventByActor();
    }
}