package eu.lifewatch.core.model;

import eu.lifewatch.common.Resources;
import eu.lifewatch.exception.URIValidationException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;
import org.apache.log4j.Logger;

/**
 * Struct object are actually entries in the repository. They are in the form of a 
 * C-Like Struct. These object can also describe themselves in an NTriple form. 
 * 
 * @author Nikos Minadakis (minadakn 'at' ics 'dot' forth 'dot' gr)
 * @author Yannis Marketakis (marketak 'at' ics 'dot' forth 'dot' gr)
 */
public class OccurrenceStatsAbundanceStruct {
    private String temporaryAggregate;
    private String speciesName;
    private String speciesURI;
    private String dimensionUnit;
    private String dimensionValue;
    private String datasetName;
    private String datasetURI;
    private String dimensionURI;
    private String dimensionTypeURI;
    private String dimensionName;

    private static final Logger logger=Logger.getLogger(OccurrenceStatsAbundanceStruct.class);

    /**Creates a new instance by initializing 
     * all the fields to be empty (Null values will cause NPE issues)*/
    public OccurrenceStatsAbundanceStruct(){
        temporaryAggregate="";
        dimensionUnit="";
        dimensionValue="";
        dimensionURI="";
        dimensionName="";
        speciesURI="";
        speciesName="";
        datasetURI="";
        datasetName="";
        dimensionTypeURI=""; 
    }
    
    public String getTemporaryAggregate() {
        return temporaryAggregate;
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
      
    public String getSpeciesURI() {
        return speciesURI;
    }
     
    public String getSpeciesName() {
        return speciesName;
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
    
    public void setTemporaryAggregate(String temporaryAggregate) {
        this.temporaryAggregate = temporaryAggregate;
    }

    public void setSpeciesName(String speciesName) {
        this.speciesName = speciesName;
    }

    public void setSpeciesURI(String speciesURI) {
        this.speciesURI = speciesURI;
    }

    public void setDimensionUnit(String dimensionUnit) {
        this.dimensionUnit = dimensionUnit;
    }

    public void setDimensionValue(String dimensionValue) {
        this.dimensionValue = dimensionValue;
    }

    public void setDatasetName(String datasetName) {
        this.datasetName = datasetName;
    }

    public void setDatasetURI(String datasetURI) {
        this.datasetURI = datasetURI;
    }

    public void setDimensionURI(String dimensionURI) {
        this.dimensionURI = dimensionURI;
    }

    public void setDimensionTypeURI(String dimensionTypeURI) {
        this.dimensionTypeURI = dimensionTypeURI;
    }

    public void setDimensionName(String dimensionName) {
        this.dimensionName = dimensionName;
    }
    
    public OccurrenceStatsAbundanceStruct withTemporaryAggregateURI(String temporaryAggregate) {
        this.temporaryAggregate = temporaryAggregate;
        return this;
    }

    public OccurrenceStatsAbundanceStruct withDimensionName(String dimensionName) {
        this.dimensionName = dimensionName;
        return this;
    }
    
    public OccurrenceStatsAbundanceStruct withDimensionURI(String dimensionURI) {
        this.dimensionURI = dimensionURI;
        return this;
    }
    
    public OccurrenceStatsAbundanceStruct withDatasetName(String datasetName) {
        this.datasetName = datasetName;
        return this;
    }
    
    public OccurrenceStatsAbundanceStruct withDatasetURI(String datasetURI) {
        this.datasetURI = datasetURI;
        return this;
    }
    
    public OccurrenceStatsAbundanceStruct withSpeciesName(String speciesName) {
        this.speciesName = speciesName;
        return this;
    }
    
    public OccurrenceStatsAbundanceStruct withSpeciesURI(String speciesURI) {
        this.speciesURI = speciesURI;
        return this;
    }
    
    public OccurrenceStatsAbundanceStruct withDimensionValue(String dimensionValue) {
        this.dimensionValue = dimensionValue;
        return this;
    }
    
    public OccurrenceStatsAbundanceStruct withDimensionUnit(String dimensionUnit) {
        this.dimensionUnit = dimensionUnit;
        return this;
    }
    
    public OccurrenceStatsAbundanceStruct withDimensionType(String dimensionType) {
        this.dimensionTypeURI = dimensionType;
        return this;
    }

    /**Produces an NTRIPLES output so that it can be used to SPARQL queries
     * 
     * @return an NTRIPLES representation of the struct fields */
    public String toNtriples(){
        String retTriples="";
        if(!temporaryAggregate.isEmpty()){
            retTriples+= "<"+temporaryAggregate+"> <"+Resources.rdfTypeLabel+"> <"+Resources.temporaryAggregateLabel+"> .\n";
            if(!dimensionURI.isEmpty()){
                retTriples+= "<"+temporaryAggregate+"> <"+Resources.hasDimension+"> <"+dimensionURI+"> .\n";
            }
            if(!datasetURI.isEmpty()){
                retTriples+= "<"+datasetURI+"> <"+Resources.refersTo+"> <"+temporaryAggregate+"> . \n";
            }
            if(!speciesURI.isEmpty()){
                retTriples+= "<"+temporaryAggregate+"> <"+Resources.belongsTo+"> <"+speciesURI+"> .\n";
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
        if(!datasetURI.isEmpty()){
            retTriples+= "<"+datasetURI+"> <"+Resources.rdfTypeLabel+"> <"+Resources.datasetLabel+"> .\n";
            if(!datasetName.isEmpty()){
                retTriples+= "<"+datasetURI+"> <"+Resources.rdfsLabel+"> \""+datasetName+"\" .\n";
            }
        }
        if(!speciesURI.isEmpty()){
            retTriples+= "<"+speciesURI+"> <"+Resources.rdfTypeLabel+"> <"+Resources.speciesLabel+"> .\n";
            if(!speciesName.isEmpty()){
                retTriples+= "<"+speciesURI+"> <"+Resources.rdfsLabel+"> \""+speciesName+"\" .\n";
            }
        }
        logger.debug("Struct in NTriples format: \n"+retTriples);
        return retTriples;
    }
    
    
    /**Validates the fields that will contain URIs
     * 
     * @throws URIValidationException if any of the URIs is not in valid form */
    public void validateURIs() throws URIValidationException{
       
        if(!this.temporaryAggregate.isEmpty()){
            this.validateURI(this.temporaryAggregate);
        }
        if(!this.dimensionURI.isEmpty()){
            this.validateURI(this.dimensionURI);
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

        return "TemporaryAggregateURI: "+temporaryAggregate+"\t"
              +"DimensionURI: "+dimensionURI+"\t"
              +"DimensionTypeURI: "+dimensionTypeURI+"\t"
              +"DimensionName: "+dimensionName+"\t"
              +"DimensionValue: "+dimensionValue+"\t"
              +"DimensionUnit: "+dimensionUnit+"\t"
              +"SpeciesURI: "+speciesURI+"\t"
              +"SpeciesName: "+speciesName+"\t"
              +"DatasetURI: "+datasetURI+"\t"
              +"DatasetName: "+datasetName;
    }
    
    @Override
    public boolean equals(Object anotherObject){
        if(anotherObject instanceof OccurrenceStatsAbundanceStruct){
            OccurrenceStatsAbundanceStruct anotherStruct=(OccurrenceStatsAbundanceStruct)anotherObject;
            return this.dimensionName.equals(anotherStruct.getDimensionName()) &&
                   this.dimensionURI.equals(anotherStruct.getDimensionURI()) &&
                   this.dimensionValue.equals(anotherStruct.getDimensionValue()) &&
                    this.datasetURI.equals(anotherStruct.getDatasetURI()) &&
                   this.dimensionUnit.equals(anotherStruct.getDimensionUnit()) &&
                   this.dimensionTypeURI.equals(anotherStruct.getDimensionTypeURI()) &&
                   this.temporaryAggregate.equals(anotherStruct.getTemporaryAggregate());
                  
        }
        else{
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.temporaryAggregate);
        return hash;
    }
}