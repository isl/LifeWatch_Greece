package eu.lifewatch.core.model;

import eu.lifewatch.common.Resources;
import eu.lifewatch.exception.URIValidationException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import org.apache.log4j.Logger;

/**
 * Struct object are actually entries in the repository. They are in the form of a 
 * C-Like Struct. These object can also describe themselves in an NTriple form. 
 * 
 * @author Nikos Minadakis (minadakn 'at' ics 'dot' forth 'dot' gr)
 * @author Yannis Marketakis (marketak 'at' ics 'dot' forth 'dot' gr)
 */
public class MicroCTPostProcessingStruct {

    private String datasetURI;
    private String datasetTitle;
    private String postProcessingURI;
    private String actorURI;
    private String actorName;
    private List<Pair> products;
    private List<Pair> inputs;
    private String description;
   
    private static final Logger logger=Logger.getLogger(MicroCTPostProcessingStruct.class);
    
    /**Creates a new instance by initializing 
     * all the fields to be empty (Null values will cause NPE issues)*/
    public MicroCTPostProcessingStruct(){
        datasetURI="";
        datasetTitle="";
        products=new ArrayList<>();
        inputs=new ArrayList<>();
        description="";
        postProcessingURI="";
        actorName="";
        actorURI="";
    }
    
    public String getDatasetURI() {
        return datasetURI;
    }
    
    public String getDatasetTitle() {
        return datasetTitle;
    }
     
    public Collection<String> getProductURIs() {
        Collection<String> productURIs=new HashSet<>();
        for(Pair pair : this.products){
            productURIs.add(pair.getKey());
        }
        return productURIs;
    }
     
    public Collection<String> getProductNames() {
        Collection<String> productNames=new HashSet<>();
        for(Pair pair : this.products){
            productNames.add(pair.getValue());
        }
        return productNames;
    }
    
    public List<Pair> getProducts(){
        return this.products;
    }
      
    public Collection<String> getInputURIs(){
        Collection<String> inputURIs=new HashSet<>();
        for(Pair pair :  this.inputs){
            inputURIs.add(pair.getKey());
        }
        return inputURIs;
    }
      
    public Collection<String> getInputNames() {
        Collection<String> inputNames=new HashSet<>();
        for(Pair pair :  this.inputs){
            inputNames.add(pair.getValue());
        }
        return inputNames;
    }  
    
    public List<Pair> getInputs(){
        return this.inputs;
    }
    
    public String getPostProcessingURI() {
        return postProcessingURI;
    }
       
    public String getDescription() {
        return description;
    }
          
    public String getActorName() {
        return actorName;
    }
      
    public String getActorURI() {
        return actorURI;
    }
     
    public void setDatasetURI(String datasetURI) {
        this.datasetURI = datasetURI;
    }

    public void setDatasetTitle(String datasetTitle) {
        this.datasetTitle = datasetTitle;
    }

    public void setPostProcessingURI(String postProcessingURI) {
        this.postProcessingURI = postProcessingURI;
    }

    public void setActorURI(String actorURI) {
        this.actorURI = actorURI;
    }

    public void setActorName(String actorName) {
        this.actorName = actorName;
    }

    public void setProducts(List<Pair> products) {
        this.products = products;
    }

    public void setInputs(List<Pair> inputs) {
        this.inputs = inputs;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public MicroCTPostProcessingStruct withDatasetURI(String datasetURI) {
        this.datasetURI = datasetURI;
        return this;
    }

    public MicroCTPostProcessingStruct withDatasetTitle(String datasetTitle) {
        this.datasetTitle = datasetTitle;
        return this;
    }
    
    public MicroCTPostProcessingStruct withPostProcessingURI(String postProcessingURI) {
        this.postProcessingURI = postProcessingURI;
        return this;
    }
    
    public MicroCTPostProcessingStruct withProduct(String productURI, String productName) {
        if(!this.getProductURIs().contains(productURI)){
            this.products.add(new Pair(productURI, productName));
        }
        return this;
    }
    
    public MicroCTPostProcessingStruct withInput(String inputURI, String inputName) {
        if(!this.getInputURIs().contains(inputURI)){
            this.inputs.add(new Pair(inputURI,inputName));
        }
        return this;
    }

    public MicroCTPostProcessingStruct withDescription(String description) {
        this.description = description;
        return this;
    }

    public MicroCTPostProcessingStruct withActorName(String actorName) {
        this.actorName = actorName;
        return this;
    }
    
    public MicroCTPostProcessingStruct withActorURI(String actorURI) {
        this.actorURI = actorURI;
        return this;
    }
     
    /**Produces an NTRIPLES output so that it can be used to SPARQL queries
     * 
     * @return an NTRIPLES representation of the struct fields */
    public String toNtriples(){
        String retTriples="";
        if(!postProcessingURI.isEmpty()){
            retTriples="<"+postProcessingURI+"> <"+Resources.rdfTypeLabel+"> <"+Resources.formalDerivationEventLabel+"> .\n"
                      +"<"+postProcessingURI+"> <"+Resources.hasType+"> \"PostProcessing\" .\n";
            for(Pair pair : this.products){    
                retTriples+= "<"+postProcessingURI+"> <"+Resources.hasCreated+"> <"+pair.getKey()+"> .\n";
            }
            for(Pair pair : this.inputs){
                retTriples+= "<"+pair.getKey()+"> <"+Resources.wasDerivationSourceFor+"> <"+postProcessingURI+"> .\n";
            }
            if(!datasetURI.isEmpty()){
                retTriples+= "<"+datasetURI+"> <"+Resources.refersTo+"> <"+postProcessingURI+"> .\n";
            }
            if(!actorURI.isEmpty()){
               retTriples+= "<"+postProcessingURI+"> <"+Resources.carriedOutBy+"> <"+actorURI+"> .\n";
            }
            if(!description.isEmpty()){
               retTriples+= "<"+postProcessingURI+"> <"+Resources.hasNote+"> \""+description+"\" .\n";
            }
        }
        for(Pair pair : this.products){
            retTriples+= "<"+pair.getKey()+"> <"+Resources.rdfTypeLabel+"> <"+Resources.dataObjectLabel+"> .\n";
            retTriples+= "<"+pair.getKey()+"> <"+Resources.rdfsLabel+"> \""+pair.getValue()+"\" .\n";
        }
        for(Pair pair : this.inputs){
            retTriples+= "<"+pair.getKey()+"> <"+Resources.rdfTypeLabel+"> <"+Resources.dataObjectLabel+"> .\n";
            retTriples+= "<"+pair.getKey()+"> <"+Resources.rdfsLabel+"> \""+pair.getValue()+"\" .\n";
        }
        if(!actorURI.isEmpty()){
            retTriples+= "<"+actorURI+"> <"+Resources.rdfTypeLabel+"> <"+Resources.personLabel+"> .\n";
            if(!actorName.isEmpty()){
                retTriples+= "<"+actorURI+"> <"+Resources.rdfsLabel+"> \""+actorName+"\" .\n";
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
        if(!this.datasetURI.isEmpty()){
            this.validateURI(this.datasetURI);
        }
  
        if(!this.postProcessingURI.isEmpty()){
            this.validateURI(this.postProcessingURI);
        }
        if(!this.actorURI.isEmpty()){
            this.validateURI(this.actorURI);
        }
        for(Pair pair : this.products){
            this.validateURI(pair.getKey());
        }
        for(Pair pair : this.inputs){
            this.validateURI(pair.getKey());
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

        return "PostProcessingEventURI: "+this.postProcessingURI+"\t"
              +"DatasetURI: "+this.datasetURI+"\t"
              +"DatasetTitle: "+this.datasetTitle+"\t"
              +"Inputs: "+this.inputs+"\t"
              +"Products: "+this.products+"\t"
              +"ActorURI: "+this.actorURI+"\t"
              +"ActorName: "+this.actorName+"\t"
              +"Description: "+this.description;
                    
    }
    
    @Override
    public boolean equals(Object anotherObject){
        if(anotherObject instanceof MicroCTPostProcessingStruct){
            MicroCTPostProcessingStruct anotherStruct=(MicroCTPostProcessingStruct)anotherObject;
            return this.datasetTitle.equals(anotherStruct.getDatasetTitle()) &&
                   this.datasetURI.equals(anotherStruct.getDatasetURI()) &&
                   this.postProcessingURI.equals(anotherStruct.getPostProcessingURI()) &&
                   this.description.equals(anotherStruct.getDescription()) &&
                   this.actorURI.equals(anotherStruct.getActorURI()) &&
                   this.actorName.equals(anotherStruct.getActorName()) && 
                   this.products.containsAll(anotherStruct.getProducts()) && 
                   anotherStruct.getProducts().containsAll(this.products) &&
                   this.inputs.containsAll(anotherStruct.getInputs()) && 
                   anotherStruct.getInputs().containsAll(this.inputs);
        }else{
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.postProcessingURI);
        return hash;
    }
}