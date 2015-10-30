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
public class IncomingNodeStruct {

    private String subject;
    private String subjectType;
    private String predicate;
    private String subjectName;

    
    private static final Logger logger=Logger.getLogger(IncomingNodeStruct.class);
    
    /**Creates a new instance by initializing 
     * all the fields to be empty (Null values will cause NPE issues)*/
    public IncomingNodeStruct(){
        subject="";
        subjectName="";
        subjectType="";
        predicate="";
  
    }
    
    public String getSubject() {
        return subject;
    }
     
    public String getSubjectType() {
        return subjectType;
    }
       
    public String getSubjectName() {
        return subjectName;
    }
     
    public String getPredicate() {
        return predicate;
    }
     
  
    
    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setSubjectType(String subjectType) {
        this.subjectType = subjectType;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public void setPredicate(String predicate) {
        this.predicate = predicate;
    }


    public IncomingNodeStruct withPredicate(String predicate) {
        this.predicate = predicate;
        return this;
    }
    
    public IncomingNodeStruct withSubjectType(String subjectType) {
        this.subjectType = subjectType;
        return this;
    }

    public IncomingNodeStruct withSubjectName(String subjectName) {
        this.subjectName = subjectName;
        return this;
    }

    public IncomingNodeStruct withSubject(String subject) {
        this.subject = subject;
        return this;
    }
    
    
    /**Produces an NTRIPLES output so that it can be used to SPARQL queries
     * 
     * @return an NTRIPLES representation of the struct fields */
    public String toNtriples(){
        String retTriples="";
        if(!subject.isEmpty()&&subject.startsWith("http://")){
            if(!subjectType.isEmpty()){
            retTriples+= "<"+subject+"> <"+Resources.rdfTypeLabel+"> <"+subjectType+"> .\n";
            }
            if(!subjectName.isEmpty()){
                retTriples+= "<"+subject+"> <"+Resources.rdfsLabel+"> \""+subjectName+"\" .\n";
            }
        }
            
        
        logger.debug("Struct in NTriples format: \n"+retTriples);
        return retTriples;
    }
    
    
    /**Validates the fields that will contain URIs
     * 
     * @throws URIValidationException if any of the URIs is not in valid form */
    public void validateURIs() throws URIValidationException{
   
        if(!this.subjectType.isEmpty()){
            this.validateURI(this.subjectType);
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
        return "Object: "+this.subject+"\t";
    }
    
    @Override
    public boolean equals(Object anotherObject){
        if(anotherObject instanceof IncomingNodeStruct){
            IncomingNodeStruct anotherStruct=(IncomingNodeStruct)anotherObject;
            return this.subject.equals(anotherStruct.getSubject());          
        }else{
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.subject);
        return hash;
    }
}