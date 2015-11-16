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
public class IdentificationStruct {
    private String identificationEventURI;
    private String identificationEvent;
    private String datasetURI;
    private String datasetTitle;
    private String individualURI;
    private String individualLabel;
    private List<Pair> actors;
    private String timeSpan;
    private String localityName;
    private String localityURI;
    private String speciesURI;
    private String speciesName;
    private String identificationReferencesURI;
    private String identificationReferencesName;
  
    private static final Logger logger=Logger.getLogger(IdentificationStruct.class);
    
    /**Creates a new instance by initializing 
     * all the fields to be empty (Null values will cause NPE issues)*/
    public IdentificationStruct(){
        identificationEventURI="";
        identificationEvent="";
        datasetURI="";
        datasetTitle="";
        individualURI="";
        individualLabel="";
        this.actors=new ArrayList<>();
        timeSpan="";
        localityName="";
        localityURI="";
        speciesURI="";
        speciesName="";
        identificationReferencesName="";
        identificationReferencesURI="";
    }
    
    public String getIdentificationEventURI() {
        return   identificationEventURI;
    }
    
    public String getIdentificationEvent() {
        return   identificationEvent;
    }
    
    public String getDatasetURI() {
        return   datasetURI;
    }
     
    public String getDatasetTitle() {
        return   datasetTitle;
    }
      
    public String getIndividualURI() {
        return individualURI;
    }
    
    public String getIndividualLabel() {
        return individualLabel;
    }
     
    public String getSpeciesURI() {
        return speciesURI;
    }
        
    public String getSpeciesName() {
        return speciesName;
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
    
    public String getLocalityName() {
        return localityName;
    }
    
    public String getLocalityURI() {
        return localityURI;
    }
    
    public String getIdentificationReferencesName() {
        return  identificationReferencesName;
    }
    
    public String getIdentificationReferencesURI() {
        return  identificationReferencesURI;
    }
    
    public void setIdentificationEventURI(String identificationEventURI) {
        this.identificationEventURI = identificationEventURI;
    }
    
    public void setIdentificationEvent(String identificationEvent) {
        this.identificationEvent = identificationEvent;
    }

    public void setDatasetURI(String datasetURI) {
        this.datasetURI = datasetURI;
    }

    public void setDatasetTitle(String datasetTitle) {
        this.datasetTitle = datasetTitle;
    }

    public void setIndividualURI(String individualURI) {
        this.individualURI = individualURI;
    }

    public void setIndividualLabel(String individualLabel) {
        this.individualLabel = individualLabel;
    }
    
    public void setActors(List<Pair> actors) {
        this.actors = actors;
    }

    public void setTimeSpan(String timeSpan) {
        this.timeSpan = timeSpan;
    }

    public void setLocalityName(String localityName) {
        this.localityName = localityName;
    }

    public void setLocalityURI(String localityURI) {
        this.localityURI = localityURI;
    }

    public void setSpeciesURI(String speciesURI) {
        this.speciesURI = speciesURI;
    }

    public void setSpeciesName(String speciesName) {
        this.speciesName = speciesName;
    }

    public void setIdentificationReferencesURI(String identificationReferencesURI) {
        this.identificationReferencesURI = identificationReferencesURI;
    }

    public void setIdentificationReferencesName(String identificationReferencesName) {
        this.identificationReferencesName = identificationReferencesName;
    }
        
    public IdentificationStruct withIdentificationEventURI(String identificationEventURI) {
        this.identificationEventURI= identificationEventURI;
        return this;
    }
    
    public IdentificationStruct withIdentificationEvent(String identificationEvent) {
        this.identificationEvent= identificationEvent;
        return this;
    }
    
    public IdentificationStruct withDatasetTitle(String datasetTitle) {
        this.datasetTitle = datasetTitle;
        return this;
    }
    
    public IdentificationStruct withDatasetURI(String datasetURI) {
        this.datasetURI = datasetURI;
        return this;
    }
    
    public IdentificationStruct withIndividualURI(String individualURI) {
        this.individualURI = individualURI;
        return this;
    }
    
    public IdentificationStruct withIndividualLabel(String individualLabel) {
        this.individualLabel = individualLabel;
        return this;
    }
    
    public IdentificationStruct withActor(String actorURI, String actorName) {
        if(!this.getActorURIs().contains(actorURI)){
            this.actors.add(new Pair(actorURI, actorName));
        }
        return this;
    }

    public IdentificationStruct withSpeciesName(String speciesName) {
        this.speciesName = speciesName;
        return this;
    }

    public IdentificationStruct withSpeciesURI(String speciesURI) {
        this.speciesURI = speciesURI;
        return this;
    }
    
    public IdentificationStruct withTimeSpan(String timeSpan) {
        this.timeSpan = timeSpan;
        return this;
    }

    public IdentificationStruct withLocalityURI(String localityURI) {
        this.localityURI = localityURI;
        return this;
    }

    public IdentificationStruct withLocalityName(String localityName) {
        this.localityName= localityName;
        return this;
    }

    public IdentificationStruct withIdentificationReferencesURI(String identificationReferencesURI) {
        this.identificationReferencesURI = identificationReferencesURI;
        return this;
    }
    public IdentificationStruct withIdentificationReferencesName(String identificationReferencesName) {
        this.identificationReferencesName = identificationReferencesName;
        return this;
    }
    
    /**Produces an NTRIPLES output so that it can be used to SPARQL queries
     * 
     * @return an NTRIPLES representation of the struct fields */
    public String toNtriples(){
        String retTriples="";
        if(!identificationEventURI.isEmpty()){
            retTriples+= "<"+identificationEventURI+"> <"+Resources.rdfTypeLabel+"> <"+Resources.identificationEventLabel+"> .\n";
            if(!timeSpan.isEmpty()){
                retTriples+= "<"+identificationEventURI+"> <"+Resources.hasTimespan+"> \""+timeSpan+"\" .\n";
            }
            if(!localityURI.isEmpty()){
                retTriples+= "<"+identificationEventURI+"> <"+Resources.tookPlaceAt+"> <"+localityURI+"> .\n";
            }
            if(!identificationReferencesURI.isEmpty()){
                retTriples+= "<"+identificationEventURI+"> <"+Resources.usedSpecificObject+"> <"+identificationReferencesURI+"> .\n";
            }
            for(Pair pair : this.actors){
                retTriples+= "<"+identificationEventURI+"> <"+Resources.carriedOutBy+"> <"+pair.getKey()+"> .\n";
            }
            if(!individualURI.isEmpty()){
                retTriples+= "<"+identificationEventURI+"> <"+Resources.assigned+"> <"+speciesURI+"> .\n";
            }
            if(!individualURI.isEmpty()){
                retTriples+= "<"+identificationEventURI+"> <"+Resources.classified+"> <"+individualURI+"> .\n";
            }
            if(!datasetURI.isEmpty()){
                retTriples+= "<"+datasetURI+"> <"+Resources.refersTo+"> <"+identificationEventURI+"> . \n";
            }
            if(!identificationEvent.isEmpty()){
                retTriples+= "<"+identificationEventURI+"> <"+Resources.rdfsLabel+"> \""+identificationEvent+"\" .\n";
            }
        }
        if(!localityURI.isEmpty()){
            retTriples+= "<"+localityURI+"> <"+Resources.rdfTypeLabel+"> <"+Resources.ecosystemEnvironmentLabel+"> .\n";
            if(!localityName.isEmpty()){
                retTriples+= "<"+localityURI+"> <"+Resources.rdfsLabel+"> \""+localityName+"\" .\n";
            }
        }
        if(!identificationReferencesURI.isEmpty()){
            retTriples+= "<"+identificationReferencesURI+"> <"+Resources.rdfTypeLabel+"> <"+Resources.publicationLabel+"> .\n";
            if(!identificationReferencesName.isEmpty()){
                retTriples+= "<"+identificationReferencesURI+"> <"+Resources.rdfsLabel+"> \""+identificationReferencesName+"\" .\n";
            }
        }
        for(Pair pair : this.actors){
            retTriples+= "<"+pair.getKey()+"> <"+Resources.rdfTypeLabel+"> <"+Resources.personLabel+"> .\n";
            retTriples+= "<"+pair.getKey()+"> <"+Resources.rdfsLabel+"> \""+pair.getValue()+"\" .\n";
        }
        if(!individualURI.isEmpty()){
            retTriples+= "<"+individualURI+"> <"+Resources.rdfTypeLabel+"> <"+Resources.bioticElementLabel+"> .\n";
            if(!individualLabel.isEmpty()){
                retTriples+= "<"+individualURI+"> <"+Resources.rdfsLabel+"> \""+individualLabel+"\" .\n";
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
            if(!datasetTitle.isEmpty()){
                retTriples+= "<"+datasetURI+"> <"+Resources.rdfsLabel+"> \""+datasetTitle+"\" .\n";
            }
        }
        logger.debug("Struct in NTriples format: \n"+retTriples);
        return retTriples;
    }
    
    /**Validates the fields that will contain URIs
     * 
     * @throws URIValidationException if any of the URIs is not in valid form */
    public void validateURIs() throws URIValidationException{
        if(!this.identificationEventURI.isEmpty()){
            this.validateURI(this.identificationEventURI);
        }
        if(!this.individualURI.isEmpty()){
            this.validateURI(this.individualURI);
        }
        for(Pair pair : this.actors){
            this.validateURI(pair.getKey());
        }
        if(!this.localityURI.isEmpty()){
            this.validateURI(this.localityURI);
        }
        if(!this.speciesURI.isEmpty()){
            this.validateURI(this.speciesURI);
        }
        if(!this.identificationReferencesURI.isEmpty()){
            this.validateURI(this.identificationReferencesURI);
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
        return "OccurrenceEventURI: "+this.identificationEventURI+"\t"
              +"DatasetURI: "+this.datasetURI+"\t"
              +"DatasetTitle: "+this.datasetTitle+"\t"
              +"SpeciesURI: "+this.speciesURI+"\t"
              +"SpeciesName: "+this.speciesName+"\t"
              +"IndividualURI: "+this.individualURI+"\t"
              +"Actors: "+this.actors+"\t"
              +"Timespan: "+this.timeSpan+"\t"
              +"Locality Name: "+this.localityName+"\t"
              +"Locality URI: "+this.localityURI+"\t"
              +"CountryName: "+this.individualURI+"\t"
              +"CountryURI: "+this.speciesURI+"\t"
              +"WaterAreaName: "+this.identificationReferencesURI+"\t"
              +"WaterAreaURI: "+this.identificationReferencesName;
    }
    
    @Override
    public boolean equals(Object anotherObject){
        if(anotherObject instanceof IdentificationStruct){
            IdentificationStruct anotherStruct=(IdentificationStruct)anotherObject;
            return this.datasetTitle.equals(anotherStruct.getDatasetTitle()) &&
                   this.datasetURI.equals(anotherStruct.getDatasetURI()) &&
                   this.individualURI.equals(anotherStruct.getIndividualURI()) &&
                   this.speciesName.equals(anotherStruct.getSpeciesName()) &&
                   this.speciesURI.equals(anotherStruct.getSpeciesURI()) &&
                   this.actors.containsAll(anotherStruct.getActors()) && 
                   anotherStruct.getActors().containsAll(this.actors) &&
                   this.timeSpan.equals(anotherStruct.getTimeSpan()) &&
                   this.localityURI.equals(anotherStruct.getLocalityURI()) &&
                   this.localityName.equals(anotherStruct.getLocalityName()) &&
                   this.identificationReferencesName.equals(anotherStruct.getIdentificationReferencesName()) &&
                   this.identificationReferencesURI.equals(anotherStruct.getIdentificationReferencesURI()) &&
                   this.speciesURI.equals(anotherStruct.getSpeciesURI());
        }else{
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.datasetURI);
        return hash;
    }
}