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
public class EnvironmentalStruct {
    private String measurementEventURI;
    private String dimensionUnit;
    private String dimensionValue;
    private String dimensionURI;
    private String datasetName;
    private String datasetURI;
    private List<Pair> actors;
    private String timeSpan;
    private String stationURI;
    private String stationName;
    private String placeURI;
    private String placeName;
    private String dimensionTypeURI;
    private String dimensionName;
    private static final Logger logger=Logger.getLogger(EnvironmentalStruct.class);
    
    /**Creates a new instance by initializing 
     * all the fields to be empty (Null values will cause NPE issues)*/
    public EnvironmentalStruct(){
        measurementEventURI="";
        dimensionUnit="";
        dimensionValue="";
        dimensionURI="";
        dimensionName="";
        dimensionTypeURI="";
        datasetName="";
        datasetURI="";
        this.actors=new ArrayList<>();
        timeSpan="";
        stationURI="";
        stationName="";
        placeURI="";
        placeName="";
    }
    
    public String getMeasurementEventURI() {
        return   measurementEventURI;
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
    
    public String getStationName() {
        return stationName;
    }
    
    public String getStationURI() {
        return stationURI;
    }
    
    public String getPlaceName() {
        return placeName;
    }
    
    public String getPlaceURI() {
        return placeURI;
    }
    
    public String getDatasetName() {
        return datasetName;
    }
    
    public String getDatasetURI() {
        return datasetURI;
    }
    
    public void setMeasurementEventURI(String measurementEventURI) {
        this.measurementEventURI = measurementEventURI;
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

    public void setDatasetName(String datasetName) {
        this.datasetName = datasetName;
    }

    public void setDatasetURI(String datasetURI) {
        this.datasetURI = datasetURI;
    }

    public void setActors(List<Pair> actors) {
        this.actors= actors;
    }

    public void setTimeSpan(String timeSpan) {
        this.timeSpan = timeSpan;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public void setPlaceURI(String placeURI) {
        this.placeURI = placeURI;
    }
    
    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public void setStationURI(String stationURI) {
        this.stationURI = stationURI;
    }

    public void setDimensionTypeURI(String dimensionTypeURI) {
        this.dimensionTypeURI = dimensionTypeURI;
    }

    public void setDimensionName(String dimensionName) {
        this.dimensionName = dimensionName;
    }
    
    public EnvironmentalStruct withMeasurementEventURI(String measurementEventURI) {
        this.measurementEventURI=measurementEventURI;
        return this;
    }

    public EnvironmentalStruct withDimensionName(String dimensionName) {
        this.dimensionName=dimensionName;
        return this;
    }
    
    public EnvironmentalStruct withDimensionURI(String dimensionURI) {
        this.dimensionURI=dimensionURI;
        return this;
    }
    
    public EnvironmentalStruct withDatasetName(String datasetName) {
        this.datasetName=datasetName;
        return this;
    }
    
    public EnvironmentalStruct withDatasetURI(String datasetURI) {
        this.datasetURI=datasetURI;
        return this;
    }
    
    public EnvironmentalStruct withDimensionValue(String dimensionValue) {
        this.dimensionValue=dimensionValue;
        return this;
    }
    
    public EnvironmentalStruct withDimensionUnit(String dimensionUnit) {
        this.dimensionUnit=dimensionUnit;
        return this;
    }
    
    public EnvironmentalStruct withDimensionTypeURI(String dimensionType){
        this.dimensionTypeURI=dimensionType;
        return this;
    }

    public EnvironmentalStruct withActor(String actorURI, String actorName) {
        if(!this.getActorURIs().contains(actorURI)){
            this.actors.add(new Pair(actorURI, actorName));
        }
        return this;
    }

    public EnvironmentalStruct withTimeSpan(String timeSpan) {
        this.timeSpan=timeSpan;
        return this;
    }

    public EnvironmentalStruct withPlaceURI(String placeURI) {
        this.placeURI=placeURI;
        return this;
    }

    public EnvironmentalStruct withPlaceName(String placeName) {
        this.placeName=placeName;
        return this;
    }

    public EnvironmentalStruct withStationURI(String stationURI) {
        this.stationURI=stationURI;
        return this;
    }

    public EnvironmentalStruct withStationName(String stationName) {
        this.stationName=stationName;
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
            if(!stationURI.isEmpty()){
                retTriples+= "<"+measurementEventURI+"> <"+Resources.measured+"> <"+stationURI+"> .\n";
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
        if(!stationURI.isEmpty()){
            retTriples+= "<"+stationURI+"> <"+Resources.rdfTypeLabel+"> <"+Resources.ecosystemEnvironmentLabel+"> .\n";
            if(!placeURI.isEmpty()){
                retTriples+= "<"+stationURI+"> <"+Resources.fallsWithin+"> <"+placeURI+"> .\n";
            }
        }
        if(!placeURI.isEmpty()){
            retTriples+= "<"+placeURI+"> <"+Resources.rdfTypeLabel+"> <"+Resources.placeLabel+"> .\n";
        if(!placeName.isEmpty()){
                retTriples+= "<"+placeURI+"> <"+Resources.rdfsLabel+"> \""+placeName+"\" .\n";
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
        if(!this.stationURI.isEmpty()){
            this.validateURI(this.stationURI);
        }
        if(!this.placeURI.isEmpty()){
            this.validateURI(this.stationURI);
        }
        if(!this.datasetURI.isEmpty()){
            this.validateURI(this.datasetURI);
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
              +"DatasetName: "+this.datasetName+"\t"
              +"DatasetURI: "+this.datasetURI+"\t"
              +"Actors: "+this.actors+"\t"
              +"Timespan: "+this.timeSpan+"\t"
              +"Station Name: "+this.stationName+"\t"
              +"Station URI: "+this.stationURI+"\t"
              +"Place Name: "+this.placeName+"\t"
              +"Place URI: "+this.placeURI+"\t"
              +"DimensionValue: "+this.dimensionValue+"\t"
              +"DimensionUnit: "+this.dimensionUnit;       
    }
    
    @Override
    public boolean equals(Object anotherObject){
        if(anotherObject instanceof EnvironmentalStruct){
            EnvironmentalStruct anotherStruct=(EnvironmentalStruct)anotherObject;
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
                   this.stationURI.equals(anotherStruct.getStationURI()) &&
                   this.stationName.equals(anotherStruct.getStationName()) &&
                   this.placeURI.equals(anotherStruct.getPlaceURI()) &&
                   this.placeName.equals(anotherStruct.getPlaceName());
        }
        else{
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