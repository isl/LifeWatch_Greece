package eu.lifewatch.core.model;

import eu.lifewatch.common.Resources;
import eu.lifewatch.exception.URIValidationException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import org.apache.log4j.Logger;

/**
 * Struct object are actually entries in the repository. They are in the form of a 
 * C-Like Struct. These object can also describe themselves in an NTriple form. 
 * 
 * @author Nikos Minadakis (minadakn 'at' ics 'dot' forth 'dot' gr)
 * @author Yannis Marketakis (marketak 'at' ics 'dot' forth 'dot' gr)
 */
public class OutgoingNodeStruct {

    private String object;
    private String objectType;
    private String predicate;
    private String objectName;

    
    private static final Logger logger=Logger.getLogger(OutgoingNodeStruct.class);
    
    /**Creates a new instance by initializing 
     * all the fields to be empty (Null values will cause NPE issues)*/
    public OutgoingNodeStruct(){
        object="";
        objectName="";
        objectType="";
        predicate="";
  
    }
    
    public String getObject() {
        return object;
    }
     
    public String getObjectType() {
        return objectType;
    }
       
    public String getObjectName() {
        return objectName;
    }
     
    public String getPredicate() {
        return predicate;
    }
     
  
    
    public void setObject(String object) {
        this.object = object;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public void setPredicate(String predicate) {
        this.predicate = predicate;
    }


    public OutgoingNodeStruct withPredicate(String predicate) {
        this.predicate = predicate;
        return this;
    }
    
    public OutgoingNodeStruct withObjectType(String objectType) {
        this.objectType = objectType;
        return this;
    }

    public OutgoingNodeStruct withObjectName(String objectName) {
        this.objectName = objectName;
        return this;
    }

    public OutgoingNodeStruct withObject(String object) {
        this.object = object;
        return this;
    }
    
    
    /**Produces an NTRIPLES output so that it can be used to SPARQL queries
     * 
     * @return an NTRIPLES representation of the struct fields */
    public String toNtriples(){
        String retTriples="";
        if(!object.isEmpty()&&object.startsWith("http://")){
            if(!objectType.isEmpty()){
            retTriples+= "<"+object+"> <"+Resources.rdfTypeLabel+"> <"+objectType+"> .\n";
            }
            if(!objectName.isEmpty()){
                retTriples+= "<"+object+"> <"+Resources.rdfsLabel+"> \""+objectName+"\" .\n";
            }
        }
            
        
        logger.debug("Struct in NTriples format: \n"+retTriples);
        return retTriples;
    }
    
    
    /**Validates the fields that will contain URIs
     * 
     * @throws URIValidationException if any of the URIs is not in valid form */
    public void validateURIs() throws URIValidationException{
   
        if(!this.objectType.isEmpty()){
            this.validateURI(this.objectType);
        }
        
    }
    
    private void validateURI(String uri) throws URIValidationException{
        try{
            new URI(uri);
        }catch(URISyntaxException ex){
            throw new URIValidationException("The given URI (\""+uri+"\" is not valid",ex);
        }
    }
    
    @Override
    public String toString(){
        return "Object: "+this.object+"\t";
    }
    
    @Override
    public boolean equals(Object anotherObject){
        if(anotherObject instanceof OutgoingNodeStruct){
            OutgoingNodeStruct anotherStruct=(OutgoingNodeStruct)anotherObject;
            return this.object.equals(anotherStruct.getObject());          
        }else{
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.object);
        return hash;
    }
}