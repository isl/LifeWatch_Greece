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
import java.util.Set;
import org.apache.log4j.Logger;

/**
 * Struct object are actually entries in the repository. They are in the form of a 
 * C-Like Struct. These object can also describe themselves in an NTriple form. 
 * 
 * @author Nikos Minadakis (minadakn 'at' ics 'dot' forth 'dot' gr)
 * @author Yannis Marketakis (marketak 'at' ics 'dot' forth 'dot' gr)
 */
public class CommonNameStruct{

    private String speciesName;
    private String commonName;
    private String language;
    private String speciesURI;
    private String commonNameURI;
    private String languageURI;
    private List<Pair> places;
    private String datasetName;
    private String datasetURI;
    
    private static Logger logger=Logger.getLogger(CommonNameStruct.class);

    /**Creates a new instance by initializing 
     * all the fields to be empty (Null values will cause NPE issues)*/
    public CommonNameStruct(){
        speciesName="";
        speciesURI="";
        language="";
        languageURI="";
        commonName="";
        commonNameURI="";
        this.places=new ArrayList<>();
        datasetName="";
        datasetURI="";
    }
    
    public String getCommonNameURI() {
        return   commonNameURI;
    }
    
    public String getCommonName() {
        return commonName;
    }
    
    public String getLanguageURI() {
        return languageURI;
    }
    
    public String getLanguage() {
        return language;    
    }
    
    public String getSpeciesName() {
        return speciesName; 
    }
    
    public String getSpeciesURI() {
        return speciesURI;
    }
    
    public String getDatasetName(){
        return datasetName;
    }
    
    public String getDatasetURI() {
        return datasetURI;
    }
    
    public Collection<String> getPlaceURIs() {
        Set<String> placeURIs=new HashSet<>();
        for(Pair pair : this.places){
            placeURIs.add(pair.getKey());
        }
        return placeURIs;
    }
    
    public Collection<String> getPlaceNames() {
        Set<String> placeNames=new HashSet<>();
        for(Pair pair : this.places){
            placeNames.add(pair.getValue());
        }
        return placeNames;
    }
    
    public List<Pair> getPlaces(){
        return this.places;
    }
  
    public void setSpeciesName(String speciesName) {
        this.speciesName = speciesName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setPlaces(List<Pair> places) {
        this.places = places;
    }

    public void setSpeciesURI(String speciesURI) {
        this.speciesURI = speciesURI;
    }

    public void setCommonNameURI(String commonNameURI) {
        this.commonNameURI = commonNameURI;
    }

    public void setLanguageURI(String languageURI) {
        this.languageURI = languageURI;
    }

    public void setDatasetName(String datasetName) {
        this.datasetName = datasetName;
    }

    public void setDatasetURI(String datasetURI) {
        this.datasetURI = datasetURI;
    }
    
    public CommonNameStruct withCommonName(String commonName) {
        this.commonName=commonName;
        return this;
    }
    
    public CommonNameStruct withCommonNameURI(String commonNameURI) {
        this.commonNameURI=commonNameURI;
        return this;
    }

    public CommonNameStruct withDatasetName(String datasetName) {
         this.datasetName=datasetName;
        return this;
    }

    public CommonNameStruct withDatasetURI(String datasetURI) {
        this.datasetURI=datasetURI;
        return this;
    }   

    public CommonNameStruct withSpeciesURI(String speciesURI) {
        this.speciesURI=speciesURI;
        return this;
    }
    
    public CommonNameStruct withSpeciesName(String speciesName) {
        this.speciesName=speciesName;
        return this;
    }
      
    public CommonNameStruct withLanguageURI(String languageURI) {
        this.languageURI=languageURI;
        return this;
    }
    
    public CommonNameStruct withLanguage(String language) {
        this.language=language;
        return this;
    }
    
    public CommonNameStruct withPlace(String placeURI, String placeName) {
        if(!this.getPlaceURIs().contains(placeURI)){
            this.places.add(new Pair(placeURI, placeName));
        }
        return this;
    }

    /**Produces an NTRIPLES output so that it can be used to SPARQL queries
     * 
     * @return an NTRIPLES representation of the struct fields */
    public String toNtriples(){
        String retTriples="";
        if(!this.commonNameURI.isEmpty()){
            retTriples+= "<"+this.commonNameURI+"> <"+Resources.rdfTypeLabel+"> <"+Resources.appellationLabel+"> .\n";
            if(!this.commonName.isEmpty()){
                retTriples+= "<"+this.commonNameURI+"> <"+Resources.rdfsLabel+"> \""+this.commonName+"\" .\n";
            }
            if(!this.speciesURI.isEmpty()){
                retTriples+= "<"+this.speciesURI+"> <"+Resources.hasPreferredIdentifier+"> <"+this.commonNameURI+"> .\n";
                retTriples+= "<"+speciesURI+"> <"+Resources.isAlsoIdentifiedBy+"> <"+this.commonNameURI+"> .\n";
            }
            if(!this.languageURI.isEmpty()){
                retTriples+= "<"+this.commonNameURI+"> <"+Resources.hasLanguage+"> <"+this.languageURI+"> .\n";
            }
            if(!this.places.isEmpty()){
                for(Pair pair : this.places){
                    retTriples+= "<"+this.commonNameURI+"> <"+Resources.isUsedIn+"> <"+pair.getKey()+"> .\n";
                }
                
            }
            if(!this.datasetURI.isEmpty()){
                    retTriples+= "<"+this.datasetURI+"> <"+Resources.refersTo+"> <"+this.commonNameURI+"> . \n";
            }
        }
        if(!this.languageURI.isEmpty()){
            retTriples+= "<"+this.languageURI+"> <"+Resources.rdfTypeLabel+"> <"+Resources.languageLabel+"> .\n";
            if(!this.language.isEmpty()){
                retTriples+= "<"+this.languageURI+"> <"+Resources.rdfsLabel+"> \""+this.language+"\" .\n";
            }
        }
        if(!this.places.isEmpty()){
            for(Pair pair : this.places){
                retTriples+= "<"+pair.getKey()+"> <"+Resources.rdfTypeLabel+"> <"+Resources.placeLabel+"> .\n";
                retTriples+= "<"+pair.getKey()+"> <"+Resources.rdfsLabel+"> \""+pair.getValue()+"\" .\n";
            }
        }
        if(!this.speciesURI.isEmpty()){
            retTriples+= "<"+this.speciesURI+"> <"+Resources.rdfTypeLabel+"> <"+Resources.speciesLabel+"> .\n";
            if(!this.speciesName.isEmpty()){
                retTriples+= "<"+this.speciesURI+"> <"+Resources.rdfsLabel+"> \""+this.speciesName+"\" .\n";
            }
        }
        if(!this.datasetURI.isEmpty()){
            retTriples+= "<"+this.datasetURI+"> <"+Resources.rdfTypeLabel+"> <"+Resources.datasetLabel+"> .\n";
            if(!this.datasetName.isEmpty()){
                retTriples+= "<"+this.datasetURI+"> <"+Resources.rdfsLabel+"> \""+this.datasetName+"\" .\n";
            }
        }          
        logger.debug("Struct in NTriples format: \n"+retTriples);
        return retTriples;
    }
    
    
    /**Validates the fields that will contain URIs
     * 
     * @throws URIValidationException if any of the URIs is not in valid form */
    public void validateURIs() throws URIValidationException{
       
        if(!this.places.isEmpty()){
            for(Pair pair : this.places){
                this.validateURI(pair.getKey());
            }
        }
        if(!this.languageURI.isEmpty()){
            this.validateURI(this.languageURI);
        }
        if(!this.speciesURI.isEmpty()){
            this.validateURI(this.speciesURI);
        }
        if(!this.datasetURI.isEmpty()){
            this.validateURI(this.datasetURI);
        }
        if(!this.commonNameURI.isEmpty()){
            this.validateURI(this.commonNameURI);
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
        return "CommonName URI: "+this.commonNameURI+"\t"
              +"Common Name: "+this.commonName+"\t"  
              +"Lanuage Uri: "+this.languageURI+"\t"
              +"Language Name: "+this.language+"\t"
              +"Species URI: "+this.speciesURI+"\t"
              +"Species Name: "+this.speciesName+"\t"  
              +"Dataset URI: "+this.datasetURI+"\t"
              +"Dataset Name: "+this.datasetName+"\t"
              +"Places: "+this.places;
    }
    
    @Override
    public boolean equals(Object anotherObject){
        if(anotherObject instanceof CommonNameStruct){
            CommonNameStruct anotherStruct=(CommonNameStruct)anotherObject;
            return this.speciesName.equals(anotherStruct.getSpeciesName()) &&
                   this.speciesURI.equals(anotherStruct.getSpeciesURI()) &&
                   this.places.containsAll(anotherStruct.getPlaces()) && 
                   anotherStruct.getPlaces().containsAll(this.places) && 
                   this.datasetName.equals(anotherStruct.getDatasetName()) &&
                   this.datasetURI.equals(anotherStruct.getDatasetURI()) &&
                   this.commonName.equals(anotherStruct.getCommonName()) &&
                   this.commonNameURI.equals(anotherStruct.getCommonNameURI()) &&
                   this.language.equals(anotherStruct.getLanguage()) &&
                   this.languageURI.equals(anotherStruct.getLanguageURI()); 
        }
        else{
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.commonNameURI);
        return hash;
    }
}