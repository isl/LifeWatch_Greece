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
public class MorphometricsStruct {
    private String attributeAssignmentEventURI;
    private String dimensionUnit;
    private String dimensionValue;
    private String dimensionURI;
    private List<Pair> actors;
    private String timeSpan;
    private String speciesName;
    private String speciesURI;
    private String dimensionTypeURI;
    private String dimensionName;
    private String publicationURI;
    private String description;
    private String publicationName;
    private String datasetName;
    private String datasetURI;
     
    private static final Logger logger=Logger.getLogger(MorphometricsStruct.class);
        
    /**Creates a new instance by initializing 
     * all the fields to be empty (Null values will cause NPE issues)*/
    public MorphometricsStruct(){
        attributeAssignmentEventURI="";
        dimensionUnit="";
        dimensionValue="";
        dimensionURI="";
        dimensionName="";
        datasetURI="";
        datasetName="";
        dimensionTypeURI="";
        actors=new ArrayList<>();
        timeSpan="";
        speciesName="";
        speciesURI="";
        publicationName="";
        publicationURI="";
        description="";
    }
    
    public String getAttributeAssignmentEventURI() {
        return attributeAssignmentEventURI;
    }
    
    public String getDimensionURI() {
        return dimensionURI;
    }
     
    public String getDimensionName() {
        return dimensionName;
    }
      
    public String getDatasetURI() {
        return datasetURI;
    }
     
    public String getDatasetName() {
        return datasetName;
    }
    
    public String getDimensionUnit() {
        return dimensionUnit;
     }
      
    public String getDimensionValue() {
        return dimensionValue;
    }
      
    public String getDimensionTypeURI() {
        return dimensionTypeURI;
    }
     
    public Collection<String> getActorURIs() {
        Collection<String> actorURIs=new HashSet<>();
        for(Pair pair : this.actors){
            actorURIs.add(pair.getKey());
        }
        return actorURIs;
    }
    
    public Collection<String> getActorNames() {
        Collection<String> actorNames=new HashSet<>();
        for(Pair pair : this.actors){
            actorNames.add(pair.getValue());
        }
        return actorNames;
    }
    
    public List<Pair> getActors(){
        return this.actors;
    }
    
    public String getTimeSpan() {
        return timeSpan;
    }
    
    public String getSpeciesName() {
        return speciesName;
    }
    
    public String getSpeciesURI() {
        return speciesURI;
    }
    
    public String getDescription() {
        return description;
    }
    
    public String getPublicationName() {
        return publicationName;
    }
    
    public String getPublicationURI() {
        return publicationURI;
    }
    
    public void setAttributeAssignmentEventURI(String attributeAssignmentEventURI) {
        this.attributeAssignmentEventURI = attributeAssignmentEventURI;
    }

    public void setDimensionUnit(String dimensionUnit) {
        this.dimensionUnit = dimensionUnit;
    }

    public void setDimensionValue(String dimensionValue) {
        this.dimensionValue = dimensionValue;
    }

    public void setDimensionURI(String dimensionURI) {
        this.dimensionURI = dimensionURI;
    }

    public void setActors(List<Pair> actors) {
        this.actors = actors;
    }

    public void setTimeSpan(String timeSpan) {
        this.timeSpan = timeSpan;
    }

    public void setSpeciesName(String speciesName) {
        this.speciesName = speciesName;
    }

    public void setSpeciesURI(String speciesURI) {
        this.speciesURI = speciesURI;
    }

    public void setDimensionTypeURI(String dimensionTypeURI) {
        this.dimensionTypeURI = dimensionTypeURI;
    }

    public void setDimensionName(String dimensionName) {
        this.dimensionName = dimensionName;
    }

    public void setPublicationURI(String publicationURI) {
        this.publicationURI = publicationURI;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPublicationName(String publicationName) {
        this.publicationName = publicationName;
    }

    public void setDatasetName(String datasetName) {
        this.datasetName = datasetName;
    }

    public void setDatasetURI(String datasetURI) {
        this.datasetURI = datasetURI;
    }
    
    public MorphometricsStruct withAttributeAssignmentEventURI(String attributeAssignmentEventURI) {
        this.attributeAssignmentEventURI = attributeAssignmentEventURI;
        return this;
    }

    public MorphometricsStruct withDimensionName(String dimensionName) {
        this.dimensionName = dimensionName;
        return this;
    }
    
    public MorphometricsStruct withDimensionURI(String dimensionURI) {
        this.dimensionURI = dimensionURI;
        return this;
    }
    
    public MorphometricsStruct withDatasetName(String datasetName) {
        this.datasetName = datasetName;
        return this;
    }
    
    public MorphometricsStruct withDatasetURI(String datasetURI) {
        this.datasetURI = datasetURI;
        return this;
    }
    
    public MorphometricsStruct withDimensionValue(String dimensionValue) {
        this.dimensionValue = dimensionValue;
        return this;
    }
    
    public MorphometricsStruct withDimensionUnit(String dimensionUnit) {
        this.dimensionUnit = dimensionUnit;
        return this;
    }
    
    public MorphometricsStruct withDimensionType(String dimensionType) {
        this.dimensionTypeURI = dimensionType;
        return this;
    }
    
    public MorphometricsStruct withActor(String actorURI, String actorName) {
        if(!this.getActorURIs().contains(actorURI)){
            this.actors.add(new Pair(actorURI, actorName));
        }
        return this;
    }

    public MorphometricsStruct withTimeSpan(String timeSpan) {
        this.timeSpan = timeSpan;
        return this;
    }

    public MorphometricsStruct withSpeciesURI(String speciesURI) {
        this.speciesURI = speciesURI;
        return this;
    }

    
    public MorphometricsStruct withSpeciesName(String speciesName) {
        this.speciesName= speciesName;
        return this;
    }
   
    public MorphometricsStruct withPublicationURI(String publicationURI) {
        this.publicationURI = publicationURI;
        return this;
    }

    
    public MorphometricsStruct withPublicationName(String publicationName) {
        this.publicationName= publicationName;
        return this;
    }
      
    public MorphometricsStruct withDescription(String description) {
        this.description= description;
        return this;
    }
    /**Produces an NTRIPLES output so that it can be used to SPARQL queries
     * 
     * @return an NTRIPLES representation of the struct fields */
    public String toNtriples(){
        String retTriples="";
        if(!attributeAssignmentEventURI.isEmpty()){
            retTriples+= "<"+attributeAssignmentEventURI+"> <"+Resources.rdfTypeLabel+"> <"+Resources.attributeAssignmentEventLabel+"> .\n";
            if(!timeSpan.isEmpty()){
                retTriples+= "<"+attributeAssignmentEventURI+"> <"+Resources.hasTimespan+"> \""+timeSpan+"\" .\n";
            }
            if(!speciesURI.isEmpty()){
                retTriples+= "<"+attributeAssignmentEventURI+"> <"+Resources.assignedAttributeTo+"> <"+speciesURI+"> .\n";
            }
            if(!dimensionURI.isEmpty()){
                retTriples+= "<"+attributeAssignmentEventURI+"> <"+Resources.assigned+"> <"+dimensionURI+"> .\n";
            }
            if(!publicationURI.isEmpty()){
                retTriples+= "<"+publicationURI+"> <"+Resources.refersTo+"> <"+attributeAssignmentEventURI+"> .\n";
            }
            if(!datasetURI.isEmpty()){
                retTriples+= "<"+datasetURI+"> <"+Resources.refersTo+"> <"+attributeAssignmentEventURI+"> . \n";
            }
            for(Pair pair : this.actors){
                retTriples+= "<"+attributeAssignmentEventURI+"> <"+Resources.carriedOutBy+"> <"+pair.getKey()+"> . \n";
            }
           if(!description.isEmpty()){
                retTriples+= "<"+attributeAssignmentEventURI+"> <"+Resources.hasNote+"> \""+description+"\" . \n";
            }
        }
        if(!dimensionURI.isEmpty()){
            retTriples+= "<"+dimensionURI+"> <"+Resources.rdfTypeLabel+"> <"+Resources.dimensionLabel+"> .\n";
            if(!dimensionTypeURI.isEmpty()){
                retTriples+= "<"+dimensionURI+"> <"+Resources.hasType+"> <"+dimensionTypeURI+"> .\n";
                if(!dimensionName.isEmpty()){
                    retTriples+= "<"+dimensionTypeURI+"> <"+Resources.rdfsLabel+"> \""+dimensionName+"\" .\n";
                }
                if(!dimensionUnit.isEmpty()){
                    retTriples+= "<"+dimensionURI+"> <"+Resources.hasUnit+"> \""+dimensionUnit+"\" .\n";   
                }
                if(!dimensionValue.isEmpty()){
                    retTriples+= "<"+dimensionURI+"> <"+Resources.hasValue+"> \""+dimensionValue+"\" .\n";
                }
            }
        }
        if(!speciesURI.isEmpty()){
            retTriples+= "<"+speciesURI+"> <"+Resources.rdfTypeLabel+"> <"+Resources.speciesLabel+"> .\n";
            if(!speciesName.isEmpty()){
                retTriples+= "<"+speciesURI+"> <"+Resources.rdfsLabel+"> \""+speciesName+"\" .\n";
            }
        }
        if(!datasetURI.isEmpty()){
            retTriples+= "<"+datasetURI+"> <"+Resources.rdfTypeLabel+"> <"+Resources.datasetLabel+"> .\n";
            if(!datasetName.isEmpty()){
                retTriples+= "<"+datasetURI+"> <"+Resources.rdfsLabel+"> \""+datasetName+"\" .\n";
            }
        }
        if(!publicationURI.isEmpty()){
            retTriples+= "<"+publicationURI+"> <"+Resources.rdfTypeLabel+"> <"+Resources.publicationLabel+"> .\n";
            if(!publicationName.isEmpty()){
                retTriples+= "<"+publicationURI+"> <"+Resources.rdfsLabel+"> \""+publicationName+"\" .\n";
            }
        }
        for(Pair pair : this.actors){
            retTriples+= "<"+pair.getKey()+"> <"+Resources.rdfTypeLabel+"> <"+Resources.personLabel+"> .\n";
            retTriples+= "<"+pair.getKey()+"> <"+Resources.rdfsLabel+"> \""+pair.getValue()+"\" .\n";
        }
        logger.debug("Struct in NTriples format: \n"+retTriples);
        return retTriples;
    }
    
    
    /**Validates the fields that will contain URIs
     * 
     * @throws URIValidationException if any of the URIs is not in valid form */
    public void validateURIs() throws URIValidationException{
        if(!this.attributeAssignmentEventURI.isEmpty()){
            this.validateURI(this.attributeAssignmentEventURI);
        }
        if(!this.dimensionURI.isEmpty()){
            this.validateURI(this.dimensionURI);
        }
        for(Pair pair : this.actors){
            this.validateURI(pair.getKey());
        }
        if(!this.speciesURI.isEmpty()){
            this.validateURI(this.speciesURI);
        }
        if(!this.publicationURI.isEmpty()){
            this.validateURI(this.publicationURI);
        }
        if(!this.dimensionTypeURI.isEmpty()){
            this.validateURI(this.dimensionTypeURI);
        }
        if(!this.datasetURI.isEmpty()){
            this.validateURI(this.datasetURI);
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

        return "MorphometricsEventURI: "+this.attributeAssignmentEventURI+"\t"
              +"DimensionURI: "+this.dimensionURI+"\t"
              +"DimensionTypeURI: "+this.dimensionTypeURI+"\t"
              +"DimensionName: "+this.dimensionName+"\t"
              +"DimensionUnit: "+this.dimensionUnit+"\t"
              +"DimensionValue: "+this.dimensionValue+"\t"
              +"Actors: "+this.actors+"\t"
              +"Timespan: "+this.timeSpan+"\t"
              +"Species Name: "+this.speciesName+"\t"
              +"Species URI: "+this.speciesURI+"\t"
              +"Dataset Name: "+this.datasetName+"\t"
              +"Dataset URI: "+this.datasetURI+"\t"
              +"Publication Name: "+this.publicationName+"\t"
              +"Publication URI: "+this.publicationURI+"\t"
              +"Description: "+this.description;
    }
    
    @Override
    public boolean equals(Object anotherObject){
        if(anotherObject instanceof MorphometricsStruct){
            MorphometricsStruct anotherStruct=(MorphometricsStruct)anotherObject;
            return this.dimensionName.equals(anotherStruct.getDimensionName()) &&
                   this.dimensionURI.equals(anotherStruct.getDimensionURI()) &&
                   this.dimensionValue.equals(anotherStruct.getDimensionValue()) &&
                   this.dimensionUnit.equals(anotherStruct.getDimensionUnit()) &&
                   this.dimensionTypeURI.equals(anotherStruct.getDimensionTypeURI()) &&
                   this.attributeAssignmentEventURI.equals(anotherStruct.getAttributeAssignmentEventURI()) &&
                   this.actors.containsAll(anotherStruct.getActors()) &&
                   anotherStruct.getActors().containsAll(this.actors) &&
                   this.datasetName.equals(anotherStruct.getDatasetName()) &&
                   this.datasetURI.equals(anotherStruct.getDatasetURI()) &&
                   this.timeSpan.equals(anotherStruct.getTimeSpan()) &&
                   this.publicationName.equals(anotherStruct.getPublicationName()) &&
                   this.publicationURI.equals(anotherStruct.getPublicationURI()) &&
                   this.description.equals(anotherStruct.getDescription()) &&
                   this.speciesURI.equals(anotherStruct.getSpeciesURI()) &&
                   this.speciesName.equals(anotherStruct.getSpeciesName());
        }
        else{
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.attributeAssignmentEventURI);
        return hash;
    }
}