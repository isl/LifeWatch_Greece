package eu.lifewatch.exception;

/**
 * @author Nikos Minadakis (minadakn 'at' ics 'dot' forth 'dot' gr)
 * @author Yannis Marketakis (marketak 'at' ics 'dot' forth 'dot' gr)
 */
public class DataImportException extends Exception{
    
    public DataImportException(){
        super("An error occured while importing data to the repository");
    }
    
    public DataImportException(String message){
        super(message);
    }
    
    public DataImportException(String message, Throwable thr){
        super(message,thr);
    }
}