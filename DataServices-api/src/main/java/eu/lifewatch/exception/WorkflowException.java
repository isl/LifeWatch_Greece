package eu.lifewatch.exception;

/**
 * @author Nikos Minadakis (minadakn 'at' ics 'dot' forth 'dot' gr)
 */
public class WorkflowException extends Exception{

    public WorkflowException(){
        super("An error occured while executing the workflow");
    }

    public WorkflowException(String message){
        super(message);
    }

    public WorkflowException(String message, Throwable thr){
        super(message,thr);
    }
}