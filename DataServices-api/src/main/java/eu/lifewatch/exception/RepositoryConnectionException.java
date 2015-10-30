package eu.lifewatch.exception;

/**
 * @author Nikos Minadakis (minadakn 'at' ics 'dot' forth 'dot' gr)
 * @author Yannis Marketakis (marketak 'at' ics 'dot' forth 'dot' gr)
 */
public class RepositoryConnectionException extends Exception{
    
    public RepositoryConnectionException(){
        super("An error occured while connecting to the repository");
    }
    
    public RepositoryConnectionException(String message){
        super(message);
    }
    
    public RepositoryConnectionException(String message, Throwable thr){
        super(message,thr);
    }
}