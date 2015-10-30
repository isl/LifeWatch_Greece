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
public class SynonymStruct {

    private String speciesName;
    private String appellation;
    private List<Pair> synonyms;
    private String datasetURI;
    private String datasetName;
    private String speciesURI;
    private String appellationURI;
    
    private static final Logger logger=Logger.getLogger(SynonymStruct.class);
    
    /**Creates a new instance by initializing 
     * all the fields to be empty (Null values will cause NPE issues)*/
    public SynonymStruct(){
        datasetName="";
        datasetURI="";
        speciesName="";
        speciesURI="";
        synonyms=new ArrayList<>();
        appellation="";
        appellationURI="";
    }
    
    public String getAppellationURI() {
        return appellationURI;
    }
     
    public Collection<String> getSynonymURIs() {
        Collection<String> synonymURIs=new HashSet<>();
        for(Pair pair : this.synonyms){
            synonymURIs.add(pair.getKey());
        }
        return synonymURIs;
    }
    
    public Collection<String> getSynonymNames() {
        Collection<String> synonymNames=new HashSet<>();
        for(Pair pair : this.synonyms){
            synonymNames.add(pair.getValue());
        }
        return synonymNames;
    }
    
    public List<Pair> getSynonyms(){
        return this.synonyms;
    }
    
    public String getAppellation() {
        return appellation;
    }
    
    public String getSpeciesName() {
        return speciesName;
    }
    
    public String getSpeciesURI() {
        return speciesURI;
    }
   
    public String getDatasetName() {
        return datasetName;
    }
    
    public String getDatasetURI() {
        return datasetURI;
    }
    
    public void setSpeciesName(String speciesName) {
        this.speciesName = speciesName;
    }

    public void setAppellation(String appellation) {
        this.appellation = appellation;
    }

    public void setSynonyms(List<Pair> synonyms) {
        this.synonyms = synonyms;
    }

    public void setDatasetURI(String datasetURI) {
        this.datasetURI = datasetURI;
    }

    public void setDatasetName(String datasetName) {
        this.datasetName = datasetName;
    }

    public void setSpeciesURI(String speciesURI) {
        this.speciesURI = speciesURI;
    }

    public void setAppellationURI(String appellationURI) {
        this.appellationURI = appellationURI;
    }

    public SynonymStruct withAppellation(String appellation) {
        this.appellation = appellation;
        return this;
    }
    
    public SynonymStruct withAppellationURI(String appellationURI) {
        this.appellationURI = appellationURI;
        return this;
    }

    public SynonymStruct withSpeciesURI(String speciesURI) {
        this.speciesURI = speciesURI;
        return this;
    }

    public SynonymStruct withSpeciesName(String speciesName) {
        this.speciesName = speciesName;
        return this;
    }
    
    public SynonymStruct withDatasetURI(String datasetURI) {
        this.datasetURI = datasetURI;
        return this;
    }

    public SynonymStruct withDatasetName(String datasetName) {
        this.datasetName = datasetName;
        return this;
    }
     
     
    public SynonymStruct withSynonym(String synonymURI, String synonymName) {
        if(!this.getSynonymURIs().contains(synonymURI)){
            this.synonyms.add(new Pair(synonymURI, synonymName));
        }
        return this;
    }

    /**Produces an NTRIPLES output so that it can be used to SPARQL queries
     * 
     * @return an NTRIPLES representation of the struct fields */
    public String toNtriples(){
        String retTriples="";
        if(!appellationURI.isEmpty()){
            retTriples+= "<"+appellationURI+"> <"+Resources.rdfTypeLabel+"> <"+Resources.appellationLabel+"> .\n";
            if(!appellation.isEmpty()){
                retTriples+= "<"+appellationURI+"> <"+Resources.rdfsLabel+"> \""+appellation+"\" .\n";
            }
            for(Pair pair : this.synonyms){
                retTriples+= "<"+appellationURI+"> <"+Resources.hasAlternativeForm+"> <"+pair.getKey()+"> .\n";
                if(!speciesURI.isEmpty()){
                    retTriples+= "<"+speciesURI+"> <"+Resources.isAlsoIdentifiedBy+"> <"+pair.getKey()+"> .\n";
                }
                if(!datasetURI.isEmpty()){
                    retTriples+= "<"+datasetURI+"> <"+Resources.refersTo+"> <"+pair.getKey()+"> . \n";
                }
            }
        }
        for(Pair pair : this.synonyms){
            retTriples+= "<"+pair.getKey()+"> <"+Resources.rdfTypeLabel+"> <"+Resources.appellationLabel+"> .\n";
            retTriples+= "<"+pair.getKey()+"> <"+Resources.rdfsLabel+"> \""+pair.getValue()+"\" .\n";
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
        for(Pair pair : this.synonyms){
            this.validateURI(pair.getKey());
        }
        if(!this.appellationURI.isEmpty()){
            this.validateURI(this.appellationURI);
        }
        if(!this.speciesURI.isEmpty()){
            this.validateURI(this.speciesURI);
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
        return "AppellationURI: "+this.appellationURI+"\t"
              +"AppellationName: "+this.appellation+"\t"
              +"SpeciesURI: "+this.speciesURI+"\t"
              +"SpeciesName: "+this.speciesName+"\t"
              +"DatasetURI: "+this.datasetURI+"\t"
              +"DatasetName: "+this.datasetName+"\t"
              +"Synonyms: "+this.synonyms;
    }
    
    @Override
    public boolean equals(Object anotherObject){
        if(anotherObject instanceof SynonymStruct){
            SynonymStruct anotherStruct=(SynonymStruct)anotherObject;
            return this.speciesName.equals(anotherStruct.getSpeciesName()) &&
                   this.speciesURI.equals(anotherStruct.getSpeciesURI()) &&
                   this.synonyms.containsAll(anotherStruct.getSynonyms()) &&
                   anotherStruct.getSynonyms().containsAll(this.synonyms) &&
                   this.datasetName.equals(anotherStruct.getDatasetName()) &&
                   this.datasetURI.equals(anotherStruct.getDatasetURI()) &&
                   this.appellation.equals(anotherStruct.getAppellation()) &&
                   this.appellationURI.equals(anotherStruct.getAppellationURI());          
        }else{
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.appellationURI);
        return hash;
    }
}