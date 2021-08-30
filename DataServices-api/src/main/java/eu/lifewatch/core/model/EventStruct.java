package eu.lifewatch.core.model;

import eu.lifewatch.common.Resources;
import eu.lifewatch.exception.URIValidationException;
import java.net.URI;
import java.net.URISyntaxException;

/** Generic Event Struct
 *
 * @author Yannis Marketakis (marketak 'at' ics 'dot' forth 'dot' gr)
 */
public class EventStruct {

    private String eventUri;
    private String eventTitle;
    private String timespan;
    
    public EventStruct(){
        this.eventUri="";
        this.eventTitle="";
        this.timespan="";
    }
    
    public String getEventUri() {
        return eventUri;
    }

    public void setEventUri(String eventUri) {
        this.eventUri = eventUri;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public String getTimespan() {
        return timespan;
    }

    public void setTimespan(String timespan) {
        this.timespan = timespan;
    }
    
    public EventStruct withEventUri(String eventUri){
        this.eventUri=eventUri;
        return this;
    }
    
    public EventStruct withEventTitle(String eventTitle){
        this.eventTitle=eventTitle;
        return this;
    }
    
    public EventStruct withTimespan(String eventTimespan){
        this.timespan=eventTimespan;
        return this;
    }

    public String toString(){
        return "EventUri: "+this.eventUri+"\t"
              +"EventTitle: "+this.eventTitle+"\t"
              +"Timespan: "+this.timespan;
    }
    
    public String toNtriples(){
        String retTriples="";
        if(!this.eventUri.isEmpty()){
            if(!this.eventTitle.isEmpty()){
                retTriples+="<"+this.eventUri+"> <"+Resources.rdfsLabel+"> \""+this.eventTitle+"\". \n";
            }
            if(!this.timespan.isEmpty()){
                retTriples+="<"+this.eventUri+"> <"+Resources.hasTimespan+"> \""+this.timespan+"\". \n";
            }
        }
        return retTriples;
    }
    
    public void validateURIs() throws URIValidationException{
        if(!this.eventUri.isEmpty()){
            this.validateURI(this.eventUri);
        }   
    }
    
    private void validateURI(String uri) throws URIValidationException{
        try{
            new URI(uri);
        }catch(URISyntaxException ex){
            throw new URIValidationException("The given URI (\""+uri+"\" is not valid",ex);
        }
    }
}