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
public class MeasurementStruct {
    private String measurementEventURI;
    private String measurementEvent;
    private String dimensionUnit;
    private String dimensionValue;
    private String dimensionURI;
    private List<Pair> actors;
    private String datasetName;
    private String datasetURI;
    private String timeSpan;
    private String specimenName;
    private String specimenURI;
    private String speciesName;
    private String speciesURI;
    private String dimensionTypeURI;
    private String dimensionName;
    
    private static final Logger logger=Logger.getLogger(MeasurementStruct.class);
    /**Creates a new instance by initializing 
     * all the fields to be empty (Null values will cause NPE issues)*/
    public MeasurementStruct(){
        measurementEventURI="";
        measurementEvent="";
        dimensionUnit="";
        dimensionValue="";
        dimensionURI="";
        dimensionName="";
        datasetURI="";
        datasetName="";
        dimensionTypeURI="";
        this.actors=new ArrayList<>();
        speciesName="";
        speciesURI="";
        timeSpan="";
        specimenName="";
        specimenURI="";
    }
    
    public String getMeasurementEventURI() {
        return   measurementEventURI;
    }
    
     public String getMeasurementEvent() {
        return   measurementEvent;
    }
    
    public String getDimensionURI() {
        return   dimensionURI;
    }
     
    public String getDimensionName() {
        return   dimensionName;
    }
      
    public String getDimensionUnit() {
        return   dimensionUnit;
    }
      
    public String getDimensionValue() {
        return   dimensionValue;
    }
      
    public String getDimensionTypeURI() {
        return dimensionTypeURI;
    }
     
    public Collection<String> getActorURIs(){
        Collection<String> actorURIs=new HashSet<>();
        for(Pair pair : this.actors){
            actorURIs.add(pair.getKey());
        }
        return actorURIs;
    }
    
    public Collection<String> getActorNames(){
        Collection<String> actorNames=new HashSet<>();
        for(Pair pair : this.actors){
            actorNames.add(pair.getValue());
        }
        return actorNames;
    }
    
    public List<Pair> getActors(){
        return this.actors;
    }
    
    public String getSpeciesURI() {
        return speciesURI;
    }
    
    public String getSpeciesName() {
        return speciesName;
    }
    
    public String getDatasetURI() {
        return datasetURI;
    }
    
    public String getDatasetName() {
        return datasetName;
    }
    
    public String getTimeSpan() {
        return timeSpan;
    }
    
    public String getSpecimenName() {
        return specimenName;
    }
    
    public String getSpecimenURI() {
        return specimenURI;
    }
    
    public void setMeasurementEventURI(String measurementEventURI) {
        this.measurementEventURI = measurementEventURI;
    }

    public void setMeasurementEvent(String measurementEvent) {
        this.measurementEvent= measurementEvent;
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

    public void setDatasetName(String datasetName) {
        this.datasetName = datasetName;
    }

    public void setDatasetURI(String datasetURI) {
        this.datasetURI = datasetURI;
    }

    public void setTimeSpan(String timeSpan) {
        this.timeSpan = timeSpan;
    }

    public void setSpecimenName(String specimenName) {
        this.specimenName = specimenName;
    }

    public void setSpecimenURI(String specimenURI) {
        this.specimenURI = specimenURI;
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
 
    public MeasurementStruct withMeasurementEventURI(String measurementEventURI) {
        this.measurementEventURI = measurementEventURI;
        return this;
    }
    
    public MeasurementStruct withMeasurementEvent(String measurementEvent) {
        this.measurementEvent = measurementEvent;
        return this;
    }

    public MeasurementStruct withDimensionName(String dimensionName) {
        this.dimensionName = dimensionName;
        return this;
    }
    
    public MeasurementStruct withDimensionURI(String dimensionURI) {
        this.dimensionURI = dimensionURI;
        return this;
    }
    
    public MeasurementStruct withDatasetName(String datasetName) {
        this.datasetName = datasetName;
        return this;
    }
    
    public MeasurementStruct withDatasetURI(String datasetURI) {
        this.datasetURI = datasetURI;
        return this;
    }
    
     public MeasurementStruct withSpeciesName(String speciesName) {
        this.speciesName = speciesName;
        return this;
    }
    
    public MeasurementStruct withSpeciesURI(String speciesURI) {
        this.speciesURI = speciesURI;
        return this;
    }
    
    public MeasurementStruct withDimensionValue(String dimensionValue) {
        this.dimensionValue = dimensionValue;
        return this;
    }
    
    public MeasurementStruct withDimensionUnit(String dimensionUnit) {
        this.dimensionUnit = dimensionUnit;
        return this;
    }
    
    public MeasurementStruct withDimensionType(String dimensionType) {
        this.dimensionTypeURI = dimensionType;
        return this;
    }
    
    public MeasurementStruct withActor(String actorURI, String actorName) {
        if(!this.getActorURIs().contains(actorURI)){
            this.actors.add(new Pair(actorURI, actorName));
        }
        return this;
    }

    public MeasurementStruct withTimeSpan(String timeSpan) {
        this.timeSpan = timeSpan;
        return this;
    }

    public MeasurementStruct withSpecimenURI(String specimenURI) {
        this.specimenURI = specimenURI;
        return this;
    }

    
     public MeasurementStruct withSpecimenName(String specimenName) {
        this.specimenName= specimenName;
        return this;
    }
      
    /**Produces an NTRIPLES output so that it can be used to SPARQL queries
     * 
     * @return an NTRIPLES representation of the struct fields */
    public String toNtriples(){
        String retTriples="";
        if(!measurementEventURI.isEmpty()){
            retTriples+= "<"+measurementEventURI+"> <"+Resources.rdfTypeLabel+"> <"+Resources.measurementEventLabel+"> .\n";
            if(!timeSpan.isEmpty()){
                retTriples+= "<"+measurementEventURI+"> <"+Resources.hasTimespan+"> \""+timeSpan+"\" .\n";
            }
            if(!specimenURI.isEmpty()){
                retTriples+= "<"+measurementEventURI+"> <"+Resources.measured+"> <"+specimenURI+"> .\n";
            }
            if(!dimensionURI.isEmpty()){
                retTriples+= "<"+measurementEventURI+"> <"+Resources.observedDimension+"> <"+dimensionURI+"> .\n";
            }
            for(Pair pair : this.actors){
                retTriples+= "<"+measurementEventURI+"> <"+Resources.carriedOutBy+"> <"+pair.getKey()+"> .\n";
            }
            if(!datasetURI.isEmpty()){
                retTriples+= "<"+datasetURI+"> <"+Resources.refersTo+"> <"+measurementEventURI+"> . \n";
            }
            
            if(!measurementEvent.isEmpty()){
                    retTriples+= "<"+measurementEventURI+"> <"+Resources.rdfsLabel+"> \""+measurementEvent+"\" .\n";
            }
        }
        if(!dimensionURI.isEmpty()){
            retTriples+= "<"+dimensionURI+"> <"+Resources.rdfTypeLabel+"> <"+Resources.dimensionLabel+"> .\n";
            if(!dimensionTypeURI.isEmpty()){
                retTriples+= "<"+dimensionURI+"> <"+Resources.hasType+"> <"+dimensionTypeURI+"> .\n";
                if(!dimensionName.isEmpty()){
                    retTriples+= "<"+dimensionTypeURI+"> <"+Resources.rdfsLabel+"> \""+dimensionName+"\" .\n";
                    retTriples+= "<"+dimensionURI+"> <"+Resources.rdfsLabel+"> \""+dimensionName+"\" .\n";
                }
                if(!dimensionUnit.isEmpty()){
                    retTriples+= "<"+dimensionURI+"> <"+Resources.hasUnit+"> \""+dimensionUnit+"\" .\n";
                }
                if(!dimensionValue.isEmpty()){
                    retTriples+= "<"+dimensionURI+"> <"+Resources.hasValue+"> \""+dimensionValue+"\" .\n";
                }
            }
        }
        if(!specimenURI.isEmpty()){
            retTriples+= "<"+specimenURI+"> <"+Resources.rdfTypeLabel+"> <"+Resources.specimenLabel+"> .\n";
            if(!specimenName.isEmpty()){
                retTriples+= "<"+specimenURI+"> <"+Resources.rdfsLabel+"> \""+specimenName+"\" .\n";
            }
           if(!speciesURI.isEmpty()){
              retTriples+= "<"+specimenURI+"> <"+Resources.belongsTo+"> <"+speciesURI+"> .\n";
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
       
        if(!this.measurementEventURI.isEmpty()){
            this.validateURI(this.measurementEventURI);
        }
        if(!this.dimensionURI.isEmpty()){
            this.validateURI(this.dimensionURI);
        }
        for(Pair pair : this.actors){
            this.validateURI(pair.getKey());
        }
         if(!this.datasetURI.isEmpty()){
            this.validateURI(this.datasetURI);
        }
        if(!this.specimenURI.isEmpty()){
            this.validateURI(this.specimenURI);
        }
        if(!this.dimensionTypeURI.isEmpty()){
            this.validateURI(this.dimensionTypeURI);
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
        return "MeasurementEventURI: "+this.measurementEventURI+"\t"
              +"DimensionURI: "+this.dimensionURI+"\t"
              +"DimensionTypeURI: "+this.dimensionTypeURI+"\t"
              +"DimensionName: "+this.dimensionName+"\t"
              +"Actors: "+this.actors+"\t"
              +"DatasetName: "+this.datasetName+"\t"
              +"DatasetURI: "+this.datasetURI+"\t"
              +"Timespan: "+this.timeSpan+"\t"
              +"Locality Name: "+this.specimenName+"\t"
              +"Locality URI: "+this.specimenURI+"\t"
              +"DimensionValue: "+this.dimensionValue+"\t"
              +"DimensionUnit: "+this.dimensionUnit;
    }
    
    @Override
    public boolean equals(Object anotherObject){
        if(anotherObject instanceof MeasurementStruct){
            MeasurementStruct anotherStruct=(MeasurementStruct)anotherObject;
            return this.dimensionName.equals(anotherStruct.getDimensionName()) &&
                   this.dimensionURI.equals(anotherStruct.getDimensionURI()) &&
                   this.dimensionValue.equals(anotherStruct.getDimensionValue()) &&
                   this.dimensionUnit.equals(anotherStruct.getDimensionUnit()) &&
                   this.dimensionTypeURI.equals(anotherStruct.getDimensionTypeURI()) &&
                   this.measurementEventURI.equals(anotherStruct.getMeasurementEventURI()) &&
                   this.actors.containsAll(anotherStruct.getActors()) && 
                   anotherStruct.getActors().containsAll(this.actors) &&
                   this.datasetName.equals(anotherStruct.getDatasetName()) &&
                   this.datasetURI.equals(anotherStruct.getDatasetURI()) &&
                   this.timeSpan.equals(anotherStruct.getTimeSpan()) &&
                   this.specimenURI.equals(anotherStruct.getSpecimenURI()) &&
                   this.specimenName.equals(anotherStruct.getSpecimenName());
        }else{
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.measurementEventURI);
        return hash;
    }
}