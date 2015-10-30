package eu.lifewatch.exception;

/**
 * @author Nikos Minadakis (minadakn 'at' ics 'dot' forth 'dot' gr)
 * @author Yannis Marketakis (marketak 'at' ics 'dot' forth 'dot' gr)
 */
public class URIValidationException extends Exception{
    
    public URIValidationException(){
        super("An error occured while validating the URI");
    }
    
    public URIValidationException(String message){
        super(message);
    }
    
    public URIValidationException(String message, Throwable thr){
        super(message,thr);
    }
}