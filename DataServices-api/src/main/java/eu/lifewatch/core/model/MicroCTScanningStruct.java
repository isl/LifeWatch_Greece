package eu.lifewatch.core.model;

import eu.lifewatch.common.Resources;
import eu.lifewatch.exception.URIValidationException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
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
public class MicroCTScanningStruct {

    private String specimenURI;
    private String specimenName;
    private String equipmentURI;
    private String contrastMethod;
    private String methodName;
    private String methodURI;
    private String scanningURI;
    private String timespan;
    private String actorURI;
    private String actorName;
    private String deviceURI;
    private String deviceName;
    private String deviceType;    
    private String description;
    private String datasetURI;
    private String datasetName;
    private List<Pair> products;
    
    private static final Logger logger=Logger.getLogger(MicroCTScanningStruct.class);
    
    /**Creates a new instance by initializing 
     * all the fields to be empty (Null values will cause NPE issues)*/
    public MicroCTScanningStruct(){
        specimenURI="";
        specimenName="";
        description="";
        equipmentURI="";
        contrastMethod = "";
        methodName="";
        methodURI="";
        scanningURI="";
        timespan="";
        deviceURI="";
        deviceName="";
        datasetURI="";
        datasetName="";
        deviceType="";
        actorName="";
        actorURI="";
        products=new ArrayList<>();
    }
    
    public String getSpecimenURI() {
        return specimenURI;
    }
    
    public String getSpecimenName() {
        return specimenName;
    }
     
     
    public String getDatasetURI() {
        return datasetURI;
    }
    
    public String getDatasetName() {
        return datasetName;
    }
     
    public String getScanningURI() {
        return scanningURI;
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
     
    public String getTimespan() {
        return timespan;
    }
    
    public String getDeviceName() {
        return deviceName;
    }
    
    public String getDeviceType() {
        return deviceType;
    }
       
    public String getDescription() {
        return description;
    }
    
    public String getDeviceURI() {
        return deviceURI;
    }
     
    public String getEquipmentURI() {
        return equipmentURI;
    }
    
    public String getContrastMethod() {
        return contrastMethod;
    }
     
    public String getMethodName() {
        return methodName;
    }
      
    public String getMethodURI() {
        return methodURI;
    }
            
    public String getActorName() {
        return actorName;
    }
      
    public String getActorURI() {
        return actorURI;
    }
     
     public void setSpecimenURI(String specimenURI) {
        this.specimenURI = specimenURI;
    }

    public void setSpecimenName(String specimenName) {
        this.specimenName = specimenName;
    }

    public void setEquipmentURI(String equipmentURI) {
        this.equipmentURI = equipmentURI;
    }

    public void setContrastMethod(String contrastMethod) {
        this.contrastMethod = contrastMethod;
    }
    
    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public void setMethodURI(String methodURI) {
        this.methodURI = methodURI;
    }

    public void setScanningURI(String scanningURI) {
        this.scanningURI = scanningURI;
    }

    public void setTimespan(String timespan) {
        this.timespan = timespan;
    }

    public void setActorURI(String actorURI) {
        this.actorURI = actorURI;
    }

    public void setActorName(String actorName) {
        this.actorName = actorName;
    }

    public void setDeviceURI(String deviceURI) {
        this.deviceURI = deviceURI;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDatasetURI(String datasetURI) {
        this.datasetURI = datasetURI;
    }

    public void setDatasetName(String datasetName) {
        this.datasetName = datasetName;
    }

    public void setProducts(List<Pair> products) {
        this.products = products;
    }
       
    public MicroCTScanningStruct withSpecimenURI(String specimenURI) {
        this.specimenURI = specimenURI;
        return this;
    }

    public MicroCTScanningStruct withSpecimenName(String specimenName) {
        this.specimenName= specimenName;
        return this;
    }
    
    public MicroCTScanningStruct withTimespan(String timespan) {
        this.timespan= timespan;
        return this;
    }
        
    public MicroCTScanningStruct withDatasetURI(String datasetURI) {
        this.datasetURI = datasetURI;
        return this;
    }

    public MicroCTScanningStruct withDatasetName(String datasetName) {
        this.datasetName= datasetName;
        return this;
    }
    
    public MicroCTScanningStruct withEquipmentURI(String equipmentURI) {
        this.equipmentURI= equipmentURI;
        return this;
    }
    
    public MicroCTScanningStruct withScanningURI(String scanningURI) {
        this.scanningURI = scanningURI;
        return this;
    }
  
    public MicroCTScanningStruct withDeviceName(String deviceName) {
        this.deviceName = deviceName;
        return this;
    }
  
    public MicroCTScanningStruct withDeviceType(String deviceType) {
        this.deviceType = deviceType;
        return this;
    }
    
    public MicroCTScanningStruct withDescription(String description) {
        this.description = description;
        return this;
    }
    
    public MicroCTScanningStruct withDeviceURI(String deviceURI) {
        this.deviceURI = deviceURI;
        return this;
    }
    
    public MicroCTScanningStruct withProduct(String productURI, String productName) {
        if(!this.getProductURIs().contains(productURI)){
            this.products.add(new Pair(productURI, productName));
        }
        return this;
    }
    
    public MicroCTScanningStruct withContrastMethod(String contrastMethod) {
        this.contrastMethod= contrastMethod;
        return this;
    }
     
    public MicroCTScanningStruct withMethodName(String methodName) {
        this.methodName = methodName;
        return this;
    }
    
    public MicroCTScanningStruct withMethodURI(String methodURI) {
        this.methodURI = methodURI;
        return this;
    }
     
    public MicroCTScanningStruct withActorName(String actorName) {
        this.actorName = actorName;
        return this;
    }
    
    public MicroCTScanningStruct withActorURI(String actorURI) {
        this.actorURI = actorURI;
        return this;
    }
     
    /**Produces an NTRIPLES output so that it can be used to SPARQL queries
     * 
     * @return an NTRIPLES representation of the struct fields */
    public String toNtriples(){
        String retTriples="";
        if(!scanningURI.isEmpty()){
            retTriples+= "<"+scanningURI+"> <"+Resources.rdfTypeLabel+"> <"+Resources.digitizationProcessLabel+"> .\n";
            if(!specimenURI.isEmpty()){
                retTriples+= "<"+scanningURI+"> <"+Resources.digitized+"> <"+specimenURI+"> .\n";
            }
            for(Pair pair : this.products){
                retTriples+= "<"+scanningURI+"> <"+Resources.createdDerivative+"> <"+pair.getKey()+"> .\n";
            }
            if(!deviceURI.isEmpty()){
                retTriples+= "<"+scanningURI+"> <"+Resources.happenedOnDevice+"> <"+deviceURI+"> .\n";
            } 
            if(!timespan.isEmpty()){
                retTriples+= "<"+scanningURI+"> <"+Resources.hasTimespan+"> \""+timespan+"\" .\n";
            }
            if(!actorURI.isEmpty()){
                retTriples+= "<"+scanningURI+"> <"+Resources.carriedOutBy+"> <"+actorURI+"> .\n";
            }
            if (!contrastMethod.isEmpty()) {
                    retTriples += "<" + scanningURI + "> <" + Resources.hasContrastMethod + "> " + contrastMethod + " .\n";
                }
            if(!datasetURI.isEmpty()){
                retTriples+= "<"+datasetURI+"> <"+Resources.refersTo+"> <"+scanningURI+"> . \n";
             }
        }
        if(!specimenURI.isEmpty()){
            retTriples+= "<"+specimenURI+"> <"+Resources.rdfTypeLabel+"> <"+Resources.specimenLabel+"> .\n";
            if(!specimenName.isEmpty()){
                retTriples+= "<"+specimenURI+"> <"+Resources.rdfsLabel+"> \""+specimenName+"\" .\n";
            }
        }
        if(!datasetURI.isEmpty()){
            retTriples+= "<"+datasetURI+"> <"+Resources.rdfTypeLabel+"> <"+Resources.datasetLabel+"> .\n";
            if(!datasetName.isEmpty()){
                retTriples+= "<"+datasetURI+"> <"+Resources.rdfsLabel+"> \""+datasetName+"\" .\n";
            }
        }
        for(Pair pair : this.products){
            retTriples+= "<"+pair.getKey()+"> <"+Resources.rdfTypeLabel+"> <"+Resources.dataObjectLabel+"> .\n";
            retTriples+= "<"+pair.getKey()+"> <"+Resources.rdfsLabel+"> \""+pair.getValue()+"\" .\n";
        }
        if(!deviceURI.isEmpty()){
            retTriples+= "<"+deviceURI+"> <"+Resources.rdfTypeLabel+"> <"+Resources.digitalDeviceLabel+"> .\n";
            if(!deviceType.isEmpty()){
                retTriples+= "<"+deviceURI+"> <"+Resources.rdfsLabel+"> \""+deviceType+"\" .\n";
            }
        }
        if(!actorURI.isEmpty()){
            retTriples+= "<"+actorURI+"> <"+Resources.rdfTypeLabel+"> <"+Resources.personLabel+"> .\n";
            if(!actorName.isEmpty()){
                retTriples+= "<"+actorURI+"> <"+Resources.rdfsLabel+"> \""+actorName+"\" .\n";
            }
        }
        logger.debug("Struct in NTriples format: \n"+retTriples);
        return retTriples;
    }
    
    
    /**Validates the fields that will contain URIs
     * 
     * @throws URIValidationException if any of the URIs is not in valid form */
    public void validateURIs() throws URIValidationException{
        if(!this.scanningURI.isEmpty()){
            this.validateURI(this.scanningURI);
        }
        if(!this.specimenURI.isEmpty()){
            this.validateURI(this.specimenURI);
        }
        for(Pair pair : this.products){
            this.validateURI(pair.getKey());
        }
        if(!this.actorURI.isEmpty()){
            this.validateURI(this.actorURI);
        }
        if(!this.deviceURI.isEmpty()){
            this.validateURI(this.deviceURI);
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

        return "ScanningURI: "+this.scanningURI+"\t"
              +"DatasetURI: "+this.datasetURI+"\t"
              +"DatasetTitle: "+this.datasetName+"\t"
              +"DeviceURI: "+this.deviceURI+"\t"
              +"DeviceName: "+this.deviceName+"\t"
              +"DeviceType: "+this.deviceType+"\t"
              +"SpecimenURI: "+this.specimenURI+"\t"
              +"SpecimenName: "+this.specimenName+"\t"
              +"ActorURI: "+this.actorURI+"\t"
              +"ActorName: "+this.actorName+"\t"
              +"Products: "+this.products+"\t"
              +"Description: "+this.description+"\t"
              +"EquipmentURI: "+this.equipmentURI+"\t"
              +"MethodURI: "+this.methodURI+"\t"
              +"MethodName: "+this.methodName+"\t"
              +"Timespan: "+this.timespan;
    }
    
    @Override
    public boolean equals(Object anotherObject){
        if(anotherObject instanceof MicroCTScanningStruct){
            MicroCTScanningStruct anotherStruct=(MicroCTScanningStruct)anotherObject;
            return this.deviceName.equals(anotherStruct.getDeviceName()) &&
                   this.deviceURI.equals(anotherStruct.getDeviceURI()) &&
                   this.scanningURI.equals(anotherStruct.getScanningURI()) &&
                   this.description.equals(anotherStruct.getDescription()) &&
                   this.actorURI.equals(anotherStruct.getActorURI()) &&
                   this.actorName.equals(anotherStruct.getActorName())&&
                   this.methodURI.equals(anotherStruct.getMethodURI()) &&
                   this.methodName.equals(anotherStruct.getMethodName()) &&
                   this.datasetURI.equals(anotherStruct.getDatasetURI()) &&
                   this.datasetName.equals(anotherStruct.getDatasetName()) &&
                   this.products.containsAll(anotherStruct.getProducts()) && 
                   anotherStruct.getProducts().containsAll(this.products) &&
                   this.deviceType.equals(anotherStruct.getDeviceType());
        }else{
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.scanningURI);
        return hash;
    }
}