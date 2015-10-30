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
public class ScientificNamingStruct {
    private String scientificNameAssignmentEventURI;
    private List<Pair> actors;
    private String timeSpan;
    private String appellation;
    private String appellationURI;
    private String nomenclaturalCodeName;
    private String speciesName;
    private String nomenclaturalCodeURI;
    private String speciesURI;
    private String datasetURI;
    private String datasetName;
    
    private static final Logger logger=Logger.getLogger(ScientificNamingStruct.class);
    
    /**Creates a new instance by initializing 
     * all the fields to be empty (Null values will cause NPE issues)*/
    public ScientificNamingStruct(){
        scientificNameAssignmentEventURI="";
        actors=new ArrayList<>();
        timeSpan="";
        speciesName="";
        speciesURI="";
        nomenclaturalCodeName="";
        nomenclaturalCodeURI="";
        datasetName="";
        datasetURI="";
        appellation="";
        appellationURI="";
    }
    
    public String getScientificNameAssignmentEventURI() {
        return scientificNameAssignmentEventURI;
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
    
    public String getAppellation() {
        return appellation;
    }
    
    public String getAppellationURI() {
        return appellationURI;
    }
    
    public String getDatasetName() {
        return datasetName;
    }
    
    public String getDatasetURI() {
        return datasetURI;
    }

    public String getNomenclaturalCodeName() {   
        return nomenclaturalCodeName;
    }
    
    public String getNomenclaturalCodeURI() {
        return nomenclaturalCodeURI;
    }
    
    public void setScientificNameAssignmentEventURI(String scientificNameAssignmentEventURI) {
        this.scientificNameAssignmentEventURI = scientificNameAssignmentEventURI;
    }

    public void setActors(List<Pair> actors) {
        this.actors = actors;
    }

    public void setTimeSpan(String timeSpan) {
        this.timeSpan = timeSpan;
    }

    public void setAppellation(String appellation) {
        this.appellation = appellation;
    }

    public void setAppellationURI(String appellationURI) {
        this.appellationURI = appellationURI;
    }

    public void setNomenclaturalCodeName(String nomenclaturalCodeName) {
        this.nomenclaturalCodeName = nomenclaturalCodeName;
    }

    public void setSpeciesName(String speciesName) {
        this.speciesName = speciesName;
    }

    public void setNomenclaturalCodeURI(String nomenclaturalCodeURI) {
        this.nomenclaturalCodeURI = nomenclaturalCodeURI;
    }

    public void setSpeciesURI(String speciesURI) {
        this.speciesURI = speciesURI;
    }

    public void setDatasetURI(String datasetURI) {
        this.datasetURI = datasetURI;
    }

    public void setDatasetName(String datasetName) {
        this.datasetName = datasetName;
    }
    
    public ScientificNamingStruct withScientificNameAssignmentEventURI(String scientificNameAssignmentEventURI) {
        this.scientificNameAssignmentEventURI = scientificNameAssignmentEventURI;
        return this;
    }
   
    public ScientificNamingStruct withActor(String actorURI, String actorName) {
        if(!this.getActorURIs().contains(actorURI)){
            this.actors.add(new Pair(actorURI, actorName));
        }
        return this;
    }

    public ScientificNamingStruct withTimeSpan(String timeSpan) {
        this.timeSpan = timeSpan;
        return this;
    }

    public ScientificNamingStruct withSpeciesURI(String speciesURI) {
        this.speciesURI = speciesURI;
        return this;
    }

    public ScientificNamingStruct withSpeciesName(String speciesName) {
        this.speciesName= speciesName;
        return this;
    }
     
    public ScientificNamingStruct withAppellationURI(String appellationURI) {
        this.appellationURI = appellationURI;
        return this;
    }

    
    public ScientificNamingStruct withAppellation(String appellation) {
        this.appellation= appellation;
        return this;
    }
   
    public ScientificNamingStruct withNomenclaturalCodeURI(String nomenclaturalCodeURI) {
        this.nomenclaturalCodeURI = nomenclaturalCodeURI;
        return this;
    }
    
    public ScientificNamingStruct withNomenclaturalCodeName(String nomenclaturalCodeName) {
        this.nomenclaturalCodeName= nomenclaturalCodeName;
        return this;
    }
      
    public ScientificNamingStruct withDatasetURI(String datasetURI) {
        this.datasetURI = datasetURI;
        return this;
    }

    
     public ScientificNamingStruct withDatasetName(String datasetName) {
        this.datasetName= datasetName;
        return this;
    }

    /**Produces an NTRIPLES output so that it can be used to SPARQL queries
     * 
     * @return an NTRIPLES representation of the struct fields */
    public String toNtriples(){
        String retTriples="";
        if(!scientificNameAssignmentEventURI.isEmpty()){
            retTriples+= "<"+scientificNameAssignmentEventURI+"> <"+Resources.rdfTypeLabel+"> <"+Resources.scientificNameAssignmentEventLabel+"> .\n";
            if(!timeSpan.isEmpty()){
                retTriples+= "<"+scientificNameAssignmentEventURI+"> <"+Resources.hasTimespan+"> \""+timeSpan+"\" .\n";
            }
            if(!speciesURI.isEmpty()){
                retTriples+= "<"+scientificNameAssignmentEventURI+"> <"+Resources.assignedAttributeTo+"> <"+speciesURI+"> .\n";
            }
            if(!appellationURI.isEmpty()){
                retTriples+= "<"+scientificNameAssignmentEventURI+"> <"+Resources.assigned+"> <"+appellationURI+"> .\n";
            }
            for(Pair pair : this.actors){
                retTriples+= "<"+scientificNameAssignmentEventURI+"> <"+Resources.carriedOutBy+"> <"+pair.getKey()+"> .\n";
            }
            if(!nomenclaturalCodeURI.isEmpty()){
                retTriples+= "<"+scientificNameAssignmentEventURI+"> <"+Resources.usedSpecificTechnique+"> <"+nomenclaturalCodeURI+"> .\n";
            }
           if(!datasetURI.isEmpty()){
                retTriples+= "<"+datasetURI+"> <"+Resources.refersTo+"> <"+scientificNameAssignmentEventURI+"> . \n";
            }
        }
        if(!nomenclaturalCodeURI.isEmpty()){
        //    retTriples+= "<"+nomenclaturalCodeURI+"> <"+Resources.rdfTypeLabel+"> <"+Resources.+"> .\n";
            if(!nomenclaturalCodeName.isEmpty()){
                retTriples+= "<"+nomenclaturalCodeURI+"> <"+Resources.rdfsLabel+"> \""+nomenclaturalCodeName+"\" .\n";
            }          
        }
        if(!appellationURI.isEmpty()){
            retTriples+= "<"+appellationURI+"> <"+Resources.rdfTypeLabel+"> <"+Resources.appellationLabel+"> .\n";
            if(!appellation.isEmpty()){
                retTriples+= "<"+appellationURI+"> <"+Resources.rdfsLabel+"> \""+appellation+"\" .\n";
            }
        }
        if(!speciesURI.isEmpty()){
            retTriples+= "<"+speciesURI+"> <"+Resources.rdfTypeLabel+"> <"+Resources.speciesLabel+"> .\n";
            if(!appellation.isEmpty()){
                retTriples+= "<"+speciesURI+"> <"+Resources.rdfsLabel+"> \""+appellation+"\" .\n";
            }
        }
        if(!datasetURI.isEmpty()){
            retTriples+= "<"+datasetURI+"> <"+Resources.rdfTypeLabel+"> <"+Resources.datasetLabel+"> .\n";
            if(!datasetName.isEmpty()){
                retTriples+= "<"+datasetURI+"> <"+Resources.rdfsLabel+"> \""+datasetName+"\" .\n";
            }
        }
        for(Pair pair : this.actors){
            retTriples+= "<"+pair.getKey()+"> <"+Resources.rdfTypeLabel+"> <"+Resources.actorLabel+"> .\n";
            retTriples+= "<"+pair.getKey()+"> <"+Resources.rdfsLabel+"> \""+pair.getValue()+"\" .\n";
        }
        logger.debug("Struct in NTriples format: \n"+retTriples);

        return retTriples;
    }
    
    
    /**Validates the fields that will contain URIs
     * 
     * @throws URIValidationException if any of the URIs is not in valid form */
    public void validateURIs() throws URIValidationException{
        if(!this.scientificNameAssignmentEventURI.isEmpty()){
            this.validateURI(this.scientificNameAssignmentEventURI);
        }
        for(Pair pair : this.actors){
            this.validateURI(pair.getKey());
        }
        if(!this.speciesURI.isEmpty()){
            this.validateURI(this.speciesURI);
        }
        if(!this.nomenclaturalCodeURI.isEmpty()){
            this.validateURI(this.nomenclaturalCodeURI);
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
        return "ScientificNameAssignmentURI: "+this.scientificNameAssignmentEventURI+"\t"
              +"Actors: "+this.actors+"\t"
              +"Timespan: "+this.timeSpan+"\t"
              +"SpeciesURI: "+this.speciesURI+"\t"
              +"SpeciesName: "+this.speciesName+"\t"
              +"DatasetURI: "+this.datasetURI+"\t"
              +"DatasetName: "+this.datasetName+"\t"
              +"NomenclaturalCodeURI: "+this.nomenclaturalCodeURI+"\t"
              +"NomenclaturalCodeName: "+this.nomenclaturalCodeName+"\t"
              +"AppellationURI: "+this.appellationURI+"\t"
              +"AppellationName: "+this.appellation;
    }
    
    @Override
    public boolean equals(Object anotherObject){
        if(anotherObject instanceof ScientificNamingStruct){
            ScientificNamingStruct anotherStruct=(ScientificNamingStruct)anotherObject;
            return this.speciesName.equals(anotherStruct.getSpeciesName()) &&
                   this.speciesURI.equals(anotherStruct.getSpeciesURI()) &&
                   this.datasetName.equals(anotherStruct.getDatasetName()) &&
                   this.datasetURI.equals(anotherStruct.getDatasetURI()) &&
                   this.nomenclaturalCodeName.equals(anotherStruct.getNomenclaturalCodeName()) &&
                   this.nomenclaturalCodeURI.equals(anotherStruct.getNomenclaturalCodeURI()) && 
                   this.actors.containsAll(anotherStruct.getActors()) &&
                   anotherStruct.getActors().containsAll(this.actors) &&
                   this.timeSpan.equals(anotherStruct.getTimeSpan());
        }
        else{
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.scientificNameAssignmentEventURI);
        return hash;
    }
}