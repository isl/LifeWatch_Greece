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
public class GensDatasetStruct {

    private String datasetURI;
    private String datasetTitle;
    
    private String timeSpan;    
    private String sampleURI;
    private String sampleName;
    
    private String speciesURI;
    private String speciesName;
    

    private String ecosystemName;
    private String ecosystemURI;
    private String habitatURI;
    private String habitatName;
    
    private String sampleTakingURI;
    private String sampleTaking;
    
    private String description;
    private String sequencingEventURI;
    private String sequencingEvent;
    private String device; 
    private String deviceURI; 
    private String deviceType;
    private String deviceTypeURI;
    
    private String producedFile;
    private String producedFileURI;
    
    
    private static final Logger logger=Logger.getLogger(GensDatasetStruct.class);
    
    /**Creates a new instance by initializing 
     * all the fields to be empty (Null values will cause NPE issues)*/
    public GensDatasetStruct(){
        sampleTakingURI="";
        sampleTaking="";
        
        datasetURI="";
        datasetTitle="";
     
        timeSpan="";
        sampleURI="";
        sampleName="";
        speciesName="";
        speciesURI="";
      
        habitatName="";
        habitatURI="";
        ecosystemName="";
        ecosystemURI="";
     
        description="";
        sequencingEventURI="";
        sequencingEvent="";
        device="";
        deviceType="";
        producedFile="";
        deviceURI="";
        deviceTypeURI="";
        producedFileURI="";
    }
    
    public String getSampleTakingURI() {
        return sampleTakingURI;
    }
    
    public String getSampleTaking() {
        return sampleTaking;
    }
   
    public String getDatasetURI() {
        return datasetURI;
    }
     
    public String getDatasetTitle() {
        return datasetTitle;
    }
    
    public String getTimeSpan() {
        return timeSpan;
    }

    public String getHabitatName() {
        return habitatName;
    }
    
    public String getHabitatURI() {
        return habitatURI;
    }
    
    public String getEcosystemName() {
        return ecosystemName;
    }
    
    public String getEcosystemURI() {
        return ecosystemURI;
    }
     
    public String getSampleURI() {
        return sampleURI;
    }
   
    public String getSampleName() {
        return sampleName;
    }
   
    public String getSpeciesURI() {
        return speciesURI;
    }
   
    public String getSpeciesName() {
        return speciesName;
    }
    
    public String getDescription() {
        return description;
    }
     
    public String getSequencingEventURI() {
        return sequencingEventURI;
    }
    
    public String getSequencingEvent() {
        return sequencingEvent;
    }
   
    public String getDevice() {
        return  device;
    }
   
    public String getDeviceType() {
        return deviceType;
    }
   
    public String getProducedFile() {
        return  producedFile;
    }
    
      public String getDeviceURI() {
        return  deviceURI;
    }
   
    public String getDeviceTypeURI() {
        return deviceTypeURI;
    }
   
    public String getProducedFileURI() {
        return  producedFileURI;
    }
    
    
    
    
    public void setDatasetURI(String datasetURI) {
        this.datasetURI = datasetURI;
    }

    public void setDatasetTitle(String datasetTitle) {
        this.datasetTitle = datasetTitle;
    }

    public void setTimeSpan(String timeSpan) {
        this.timeSpan = timeSpan;
    }

    public void setSampleURI(String sampleURI) {
        this.sampleURI = sampleURI;
    }

    public void setSampleName(String sampleName) {
        this.sampleName = sampleName;
    }

    public void setSpeciesURI(String speciesURI) {
        this.speciesURI = speciesURI;
    }

    public void setSpeciesName(String speciesName) {
        this.speciesName = speciesName;
    }

    public void setEcosystemName(String ecosystemName) {
        this.ecosystemName = ecosystemName;
    }

    public void setEcosystemURI(String ecosystemURI) {
        this.ecosystemURI = ecosystemURI;
    }

    public void setHabitatURI(String habitatURI) {
        this.habitatURI = habitatURI;
    }

    public void setHabitatName(String habitatName) {
        this.habitatName = habitatName;
    }

    public void setSampleTakingURI(String sampleTakingURI) {
        this.sampleTakingURI = sampleTakingURI;
    }
    
    public void setSampleTaking(String sampleTaking) {
        this.sampleTaking = sampleTaking;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
       
    public void setSequencingEventURI(String  sequencingEventURI) {
        this. sequencingEventURI =  sequencingEventURI;
    }
     
    public void setSequencingEvent(String  sequencingEvent) {
        this. sequencingEvent =  sequencingEvent;
    }
      
    public void setDevice(String device) {
        this.device =  device;
    }
     
    public void setDeviceType(String deviceType) {
        this.deviceType =  deviceType;
    }
    
    public void setProducedFile(String producedFile) {
        this.producedFile= producedFile;
    }
    
    public void setDeviceURI(String deviceURI) {
        this.deviceURI =  deviceURI;
    }
     
    public void setDeviceTypeURI(String deviceTypeURI) {
        this.deviceTypeURI =  deviceTypeURI;
    }
    
    public void setProducedFileURI(String producedFileURI) {
        this.producedFileURI= producedFileURI;
    }
      
      

      
    public GensDatasetStruct withSampleTakingURI(String sampleTakingURI) {
        this.sampleTakingURI = sampleTakingURI;
        return this;
    }
    
    public GensDatasetStruct withSampleTaking(String sampleTaking) {
        this.sampleTaking = sampleTaking;
        return this;
    }

    public GensDatasetStruct withDatasetTitle(String datasetTitle) {
        this.datasetTitle = datasetTitle;
        return this;
    }
    
    public GensDatasetStruct withDatasetURI(String datasetURI) {
        this.datasetURI = datasetURI;
        return this;
    }
    
    
    public GensDatasetStruct withTimeSpan(String timeSpan) {
        this.timeSpan = timeSpan;
        return this;
    }

   

    public GensDatasetStruct withHabitatURI(String habitatURI) {
        this.habitatURI = habitatURI;
        return this;
    }

    public GensDatasetStruct withHabitatName(String habitatName) {
        this.habitatName= habitatName;
        return this;
    } 
      
    public GensDatasetStruct withEcosystemURI(String ecosystemURI) {
        this.ecosystemURI = ecosystemURI;
        return this;
    }
    
    public GensDatasetStruct withEcosystemName(String ecosystemName) {
        this.ecosystemName= ecosystemName;
        return this;
    } 
     
   
    public GensDatasetStruct withSampleURI(String sampleURI) {
        this.sampleURI = sampleURI;
        return this;
    }
      
    public GensDatasetStruct withSampleName(String sampleName) {
        this.sampleName = sampleName;
        return this;
    }
      
    public GensDatasetStruct withSpeciesURI(String speciesURI) {
        this.speciesURI = speciesURI;
        return this;
    }
      
    public GensDatasetStruct withSpeciesName(String speciesName) {
        this.speciesName = speciesName;
        return this;
    }
    
  
    
    public GensDatasetStruct withDescription(String description) {
        this.description = description;
        return this;
    }
    
    public GensDatasetStruct withSequencingEventURI(String sequencingEventURI) {
        this.sequencingEventURI = sequencingEventURI;
        return this;
    }
    
    public GensDatasetStruct withSequencingEvent(String sequencingEvent) {
        this.sequencingEvent = sequencingEvent;
        return this;
    }
    
     public GensDatasetStruct withDevice(String device) {
        this.device = device;
        return this;
    }
    
      public GensDatasetStruct withDeviceType(String deviceType) {
        this.deviceType =  deviceType;
        return this;
    }
    
     public GensDatasetStruct withProducedFile(String producedFile) {
        this.producedFile =  producedFile;
        return this;
    }

         public GensDatasetStruct withDeviceURI(String deviceURI) {
        this.deviceURI = deviceURI;
        return this;
    }
    
      public GensDatasetStruct withDeviceTypeURI(String deviceTypeURI) {
        this.deviceTypeURI =  deviceTypeURI;
        return this;
    }
    
     public GensDatasetStruct withProducedFileURI(String producedFileURI) {
        this.producedFileURI =  producedFileURI;
        return this;
    }
     
     
    /**Produces an NTRIPLES output so that it can be used to SPARQL queries
     * 
     * @return an NTRIPLES representation of the struct fields */
    public String toNtriples(){
        String retTriples="";
        if(!sampleTakingURI.isEmpty()){
            retTriples+= "<"+sampleTakingURI+"> <"+Resources.rdfTypeLabel+"> <"+Resources.sampleTakingLabel+"> .\n";
            if(!timeSpan.isEmpty()){
                retTriples+= "<"+sampleTakingURI+"> <"+Resources.hasTimespan+"> \""+timeSpan+"\" .\n";
            }
            if(!ecosystemURI.isEmpty()){
                retTriples+= "<"+sampleTakingURI+"> <"+Resources.tookPlaceAt+"> <"+ecosystemURI+"> .\n";
            }
            if(!sampleURI.isEmpty()){
                retTriples+= "<"+sampleTakingURI+"> <"+Resources.removed+"> <"+sampleURI+"> .\n";
            }
            if(!datasetURI.isEmpty()){
                retTriples+= "<"+datasetURI+"> <"+Resources.refersTo+"> <"+sampleTakingURI+"> . \n";
            }
            
            if(!speciesURI.isEmpty()){
                retTriples+= "<"+sampleURI+"> <"+Resources.belongsTo+"> <"+speciesURI+"> .\n";
            }
              
            if(!description.isEmpty()){
                retTriples+= "<"+sampleTakingURI+"> <"+Resources.hasNote+"> \""+description+"\" .\n";
            }
            
            if(!sampleTaking.isEmpty()){
                retTriples+= "<"+sampleTakingURI+"> <"+Resources.rdfsLabel+"> \""+sampleTaking+"\" .\n";
            }
            
        }
        if(!datasetURI.isEmpty()){
            retTriples+= "<"+datasetURI+"> <"+Resources.rdfTypeLabel+"> <"+Resources.datasetLabel+"> .\n";
            if(!datasetTitle.isEmpty()){
                retTriples+= "<"+datasetURI+"> <"+Resources.rdfsLabel+"> \""+datasetTitle+"\" .\n";
            }
         
        }
        if(!sampleURI.isEmpty()){
            retTriples+= "<"+sampleURI+"> <"+Resources.rdfTypeLabel+"> <"+Resources.sampleLabel+"> .\n";
            if(!sampleName.isEmpty()){
                retTriples+= "<"+sampleURI+"> <"+Resources.rdfsLabel+"> \""+sampleName+"\" .\n";
            }
            if(!speciesURI.isEmpty()){
                retTriples+= "<"+sampleURI+"> <"+Resources.belongsTo+"> <"+speciesURI+"> .\n";
            }  
        }
        if(!speciesURI.isEmpty()){
            retTriples+= "<"+speciesURI+"> <"+Resources.rdfTypeLabel+"> <"+Resources.speciesLabel+"> .\n";
            if(!speciesName.isEmpty()){
                retTriples+= "<"+speciesURI+"> <"+Resources.rdfsLabel+"> \""+speciesName+"\" .\n";
            }
        }
        if(!ecosystemURI.isEmpty()){
            retTriples+= "<"+ecosystemURI+"> <"+Resources.rdfTypeLabel+"> <"+Resources.ecosystemEnvironmentLabel+"> .\n";
            if(!ecosystemName.isEmpty()){
                retTriples+= "<"+ecosystemURI+"> <"+Resources.rdfsLabel+"> \""+ecosystemName+"\" .\n";
            }
         
            if(!habitatURI.isEmpty()){
                retTriples+= "<"+ecosystemURI+"> <"+Resources.hasType+"> <"+habitatURI+"> .\n";
            }
          
        }
        if(!habitatURI.isEmpty()){
            retTriples+= "<"+habitatURI+"> <"+Resources.rdfTypeLabel+"> <"+Resources.ecosystemTypeLabel+"> .\n";
            if(!habitatName.isEmpty()){
                retTriples+= "<"+habitatURI+"> <"+Resources.rdfsLabel+"> \""+habitatName+"\" .\n";
            }
        }
    
        if(!sequencingEventURI.isEmpty()){
            retTriples+= "<"+sequencingEventURI+"> <"+Resources.rdfTypeLabel+"> <"+Resources.digitizationProcessLabel+"> .\n";
            retTriples+= "<"+sequencingEventURI+"> <"+Resources.hasType+"> \"Sequencing\" .\n";
            if(!sampleURI.isEmpty()){
                retTriples+= "<"+sequencingEventURI+"> <"+Resources.digitized+"> <"+sampleURI+"> .\n";
            }
            if(!producedFile.isEmpty()){
                retTriples+= "<"+sequencingEventURI+"> <"+Resources.hasCreated+"> <"+producedFileURI+"> .\n";
            }
            if(!deviceURI.isEmpty()){
                retTriples+= "<"+sequencingEventURI+"> <"+Resources.happenedOnDevice+"> <"+deviceURI+"> .\n";
            }
            if(!datasetURI.isEmpty()){
                retTriples+= "<"+datasetURI+"> <"+Resources.refersTo+"> <"+sequencingEventURI+"> . \n";
            }
            
            if(!sequencingEvent.isEmpty()){
                retTriples+= "<"+sequencingEventURI+"> <"+Resources.rdfsLabel+"> \""+sequencingEvent+"\" .\n";
            }
          
        }
        if(!deviceURI.isEmpty()){
            retTriples+= "<"+deviceURI+"> <"+Resources.rdfTypeLabel+"> <"+Resources.digitalDeviceLabel+"> .\n";
            if(!device.isEmpty()){
                retTriples+= "<"+deviceURI+"> <"+Resources.rdfsLabel+"> \""+device+"\" .\n";
            }
            if(!deviceType.isEmpty()){
                retTriples+= "<"+deviceURI+"> <"+Resources.hasType+"> \""+deviceType+"\" .\n";
            }
        }
       
        if(!producedFileURI.isEmpty()){
            retTriples+= "<"+producedFileURI+"> <"+Resources.rdfTypeLabel+"> <"+Resources.dataObjectLabel+"> .\n";
            if(!producedFile.isEmpty()){
                retTriples+= "<"+producedFileURI+"> <"+Resources.rdfsLabel+"> \""+producedFile+"\" .\n";
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
        if(!this.sampleTakingURI.isEmpty()){
            this.validateURI(this.sampleTakingURI);
        }
      
        if(!this.ecosystemURI.isEmpty()){
            this.validateURI(this.ecosystemURI);
        }
     
         if(!this.ecosystemURI.isEmpty()){
            this.validateURI(this.ecosystemURI);
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
        return "SampleTakingURI: "+sampleTakingURI+"\t"
              +"DatasetURI: "+datasetURI+"\t"
              +"DatasetTitle: "+datasetTitle+"\t"

              +"TimeSpan: "+timeSpan+"\t"
              +"SampleURI: "+sampleURI+"\t"
              +"SampleName: "+sampleName+"\t"
              +"SpeciesURI: "+speciesURI+"\t"
              +"SpeciesName: "+speciesName+"\t"
           
              +"HabitatURI: "+habitatURI+"\t"
              +"HabitatName: "+habitatName+"\t";
           
    
           
    }
    
    @Override
    public boolean equals(Object anotherObject){
        if(anotherObject instanceof GensDatasetStruct){
            GensDatasetStruct anotherStruct=(GensDatasetStruct)anotherObject;
            return this.datasetTitle.equals(anotherStruct.getDatasetTitle()) &&
                   this.datasetURI.equals(anotherStruct.getDatasetURI()) &&
                  
                   this.timeSpan.equals(anotherStruct.getTimeSpan()) &&
                  
                   this.habitatName.equals(anotherStruct.getHabitatName()) &&
                   this.habitatURI.equals(anotherStruct.getHabitatURI()) &&
                   this.ecosystemName.equals(anotherStruct.getEcosystemName()) &&
                   this.ecosystemURI.equals(anotherStruct.getEcosystemURI()) &&
                  
                   this.sampleURI.equals(anotherStruct.getSampleURI());
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