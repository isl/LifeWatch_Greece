package eu.lifewatch.exception;

/**
 * @author Nikos Minadakis (minadakn 'at' ics 'dot' forth 'dot' gr)
 * @author Yannis Marketakis (marketak 'at' ics 'dot' forth 'dot' gr)
 */
public class DataExportException extends Exception{
    
    public DataExportException(){
        super("An error occured while exporting data from the repository");
    }
    
    public DataExportException(String message){
        super(message);
    }
    
    public DataExportException(String message, Throwable thr){
        super(message,thr);
    }
}