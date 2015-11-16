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
public class SpecimenStruct {
    private String transformationEventURI;
    private String transformationEvent;
    private String specimenName;
    private String specimenURI;
    private String speciesName;
    private String speciesURI;
    private String individualURI;
    private String collectionName;
    private String collectionURI;
    private List<Pair> actors;
    private String timeSpan;
    private String methodURI;
    private String methodName;
    private String datasetURI;
    private String datasetName;
    
    private static final Logger logger=Logger.getLogger(SpecimenStruct.class);

    /**Creates a new instance by initializing 
     * all the fields to be empty (Null values will cause NPE issues)*/
    public SpecimenStruct(){
        transformationEventURI="";
        transformationEvent="";
        collectionName="";
        collectionURI="";
        actors=new ArrayList<>();
        speciesName="";
        speciesURI="";
        timeSpan="";
        datasetName="";
        datasetURI="";
        specimenName="";
        specimenURI="";
        individualURI="";
        methodURI="";
        methodName="";
    }
    
    public String getTransformationEventURI() {
        return transformationEventURI;
    }
    
    public String getTransformationEvent() {
        return transformationEvent;
    }
    
    public String getCollectionURI() {
        return collectionURI;
    }
     
    public String getCollectionName() {
        return collectionName;
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
    
    public String getMethodName() {
        return methodName;
    }
    
    public String getMethodURI() {
        return methodURI;
    }
    
    public String getIndividualURI() {
        return individualURI;
    }
    
    public void setTransformationEventURI(String transformationEventURI) {
        this.transformationEventURI = transformationEventURI;
    }

    public void setTransformationEvent(String transformationEvent) {
        this.transformationEvent = transformationEvent;
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

    public void setIndividualURI(String individualURI) {
        this.individualURI = individualURI;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public void setCollectionURI(String collectionURI) {
        this.collectionURI = collectionURI;
    }

    public void setActors(List<Pair> actors) {
        this.actors = actors;
    }

    public void setTimeSpan(String timeSpan) {
        this.timeSpan = timeSpan;
    }

    public void setMethodURI(String methodURI) {
        this.methodURI = methodURI;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public void setDatasetURI(String datasetURI) {
        this.datasetURI = datasetURI;
    }

    public void setDatasetName(String datasetName) {
        this.datasetName = datasetName;
    }
    
    public SpecimenStruct withTransformationEventURI(String transformationEventURI) {
        this.transformationEventURI = transformationEventURI;
        return this;
    }

    public SpecimenStruct withTransformationEvent(String transformationEvent) {
        this.transformationEvent = transformationEvent;
        return this;
    }
    
    public SpecimenStruct withCollectionName(String collectionName) {
        this.collectionName = collectionName;
        return this;
    }
    
    public SpecimenStruct withCollectionURI(String collectionURI) {
        this.collectionURI = collectionURI;
        return this;
    }
    
     public SpecimenStruct withDatasetName(String datasetName) {
        this.datasetName = datasetName;
        return this;
    }
    
    public SpecimenStruct withDatasetURI(String datasetURI) {
        this.datasetURI = datasetURI;
        return this;
    }
    
    public SpecimenStruct withActor(String actorURI, String actorName) {
        if(!this.getActorURIs().contains(actorURI)){
            this.actors.add(new Pair(actorURI, actorName));
        }
        return this;
    }

    public SpecimenStruct withSpeciesName(String speciesName) {
        this.speciesName = speciesName;
        return this;
    }

    public SpecimenStruct withSpeciesURI(String speciesURI) {
        this.speciesURI = speciesURI;
        return this;
    }
    
    public SpecimenStruct withTimeSpan(String timeSpan) {
        this.timeSpan = timeSpan;
        return this;
    }

    public SpecimenStruct withSpecimenURI(String specimenURI) {
        this.specimenURI = specimenURI;
        return this;
    }
    
     public SpecimenStruct withSpecimenName(String specimenName) {
        this.specimenName= specimenName;
        return this;
    }
     
    public SpecimenStruct withMethodURI(String methodURI) {
        this.methodURI = methodURI;
        return this;
    }

    public SpecimenStruct withIndividualURI(String individualURI) {
        this.individualURI = individualURI;
        return this;
    }
     public SpecimenStruct withMethodName(String methodName) {
        this.methodName= methodName;
        return this;
    }
      
    /**Produces an NTRIPLES output so that it can be used to SPARQL queries
     * 
     * @return an NTRIPLES representation of the struct fields */
    public String toNtriples(){
        String retTriples="";
        if(!transformationEventURI.isEmpty()){
            retTriples+= "<"+transformationEventURI+"> <"+Resources.rdfTypeLabel+"> <"+Resources.transformationEventLabel+"> .\n";
            if(!timeSpan.isEmpty()){
                retTriples+= "<"+transformationEventURI+"> <"+Resources.hasTimespan+"> \""+timeSpan+"\" .\n";
            }
            if(!individualURI.isEmpty()){
                retTriples+= "<"+transformationEventURI+"> <"+Resources.transformed+"> <"+individualURI+"> .\n";
            }
            if(!specimenURI.isEmpty()){
                retTriples+= "<"+transformationEventURI+"> <"+Resources.resultedIn+"> <"+specimenURI+"> .\n";
            }
            for(Pair pair : this.actors){
                retTriples+= "<"+transformationEventURI+"> <"+Resources.carriedOutBy+"> <"+pair.getKey()+"> .\n";
            }
            if(!methodURI.isEmpty()){
                retTriples+= "<"+transformationEventURI+"> <"+Resources.usedSpecificTechnique+"> <"+methodURI+"> .\n";
            }
            if(!datasetURI.isEmpty()){
                retTriples+= "<"+datasetURI+"> <"+Resources.refersTo+"> <"+specimenURI+"> . \n";
            }
            if(!transformationEvent.isEmpty()){
                retTriples+= "<"+transformationEventURI+"> <"+Resources.rdfsLabel+"> \""+transformationEvent+"\" .\n";
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
        if(!individualURI.isEmpty()){
            retTriples+= "<"+individualURI+"> <"+Resources.rdfTypeLabel+"> <"+Resources.bioticElementLabel+"> .\n";
//        if(!individualName.isEmpty()){
//                retTriples+= "<"+individualURI+"> <"+Resources.isIdentifiedBy+"> \""+individualName+"\" .\n";
//            }
//         
        }
        if(!collectionURI.isEmpty()){
            retTriples+= "<"+collectionURI+"> <"+Resources.rdfTypeLabel+"> <"+Resources.collectionLabel+"> .\n";
            if(!collectionName.isEmpty()){
                retTriples+= "<"+collectionURI+"> <"+Resources.rdfsLabel+"> \""+collectionName+"\" .\n";
            }
           if(!specimenURI.isEmpty()){
                retTriples+= "<"+specimenURI+"> <"+Resources.formsPartOf+"> <"+collectionURI+"> .\n";
            }
        }
        if(!datasetURI.isEmpty()){
            retTriples+= "<"+datasetURI+"> <"+Resources.rdfTypeLabel+"> <"+Resources.datasetLabel+"> .\n";
            if(!datasetName.isEmpty()){
                retTriples+= "<"+datasetURI+"> <"+Resources.rdfsLabel+"> \""+datasetName+"\" .\n";
            }
        }
        if(!methodURI.isEmpty()){
           // retTriples+= "<"+specimenURI+"> <"+Resources.rdfTypeLabel+"> <"+Resources.+"> .\n";
            if(!methodName.isEmpty()){
                retTriples+= "<"+methodURI+"> <"+Resources.rdfsLabel+"> \""+methodName+"\" .\n";
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
        if(!this.transformationEventURI.isEmpty()){
            this.validateURI(this.transformationEventURI);
        }
        if(!this.individualURI.isEmpty()){
            this.validateURI(this.individualURI);
        }
        for(Pair pair : this.actors){
            this.validateURI(pair.getKey());
        }
        if(!this.specimenURI.isEmpty()){
            this.validateURI(this.specimenURI);
        }
        if(!this.speciesURI.isEmpty()){
            this.validateURI(this.speciesURI);
        }
        if(!this.methodURI.isEmpty()){
            this.validateURI(this.methodURI);
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

        return "SpecimenURI: "+this.specimenURI+"\t"
              +"SpecimenName: "+this.speciesName+"\t"
              +"TransformationEventURI: "+this.transformationEventURI+"\t"
              +"CollectionURI: "+this.collectionURI+"\t"
              +"CollectionName: "+this.collectionName+"\t"
              +"Actors: "+this.actors+"\t"
              +"Timespan: "+this.timeSpan+"\t"
              +"SpeciesURI: "+this.speciesURI+"\t"
              +"SpeciesName: "+this.speciesName+"\t"
              +"DatasetURI: "+this.datasetURI+"\t"
              +"DatasetName: "+this.datasetName+"\t"
              +"MethodURI: "+this.methodURI+"\t"
              +"MethodName: "+this.methodName+"\t"
              +"IndividualURI: "+this.individualURI;
    }
    
    @Override
    public boolean equals(Object anotherObject){
        if(anotherObject instanceof SpecimenStruct){
            SpecimenStruct anotherStruct=(SpecimenStruct)anotherObject;
            return this.methodName.equals(anotherStruct.getMethodName()) &&
                   this.methodURI.equals(anotherStruct.getMethodURI()) &&
                   this.individualURI.equals(anotherStruct.getIndividualURI()) &&
                   this.datasetName.equals(anotherStruct.getDatasetName()) &&
                   this.datasetURI.equals(anotherStruct.getDatasetURI()) &&
                   this.speciesName.equals(anotherStruct.getSpeciesName()) &&
                   this.speciesURI.equals(anotherStruct.getSpeciesURI()) &&
                   this.actors.containsAll(anotherStruct.getActors()) &&
                   anotherStruct.getActors().containsAll(this.actors) &&
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
        hash = 89 * hash + Objects.hashCode(this.transformationEventURI);
        return hash;
    }
}