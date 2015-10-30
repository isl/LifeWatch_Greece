package eu.lifewatch.exception;

/**
 * @author Nikos Minadakis (minadakn 'at' ics 'dot' forth 'dot' gr)
 * @author Yannis Marketakis (marketak 'at' ics 'dot' forth 'dot' gr)
 */
public class QueryExecutionException extends Exception{
    
    public QueryExecutionException(){
        super("An error occured while executing the SPARQL query");
    }
    
    public QueryExecutionException(String message){
        super(message);
    }
    
    public QueryExecutionException(String message, Throwable thr){
        super(message,thr);
    }
}