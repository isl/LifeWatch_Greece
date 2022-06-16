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
import java.util.Set;
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
    private String equipment;
    private String contrastMethod;
    private String protocol;
    private String voltage;
    private String current;
    private String filter;
    private String zoom;
    private String cameraResolution;
    private String averaging;
    private String randomMovement;
    private String scanDegrees;
    private String exposureTime;
    private String oversizeSettings;
    private String methodName;
    private String methodURI;
    private String scanningURI;
    private String scanning;
    private String scanningLabel;
    private String actorURI;
    private String actorName;
    private String deviceURI;
    private String deviceName;
    private String deviceType;    
    private String description;
    private String datasetURI;
    private String datasetName;
    private List<Pair> products;
    private String productStatus;
    private String fileLocation;
    private String sampleHolder;
    private String scanningMedium;
    private String scannedPart;
    private String scanDate;
    private String scanningDuration;
    private String preparationTimestampStart;
    private String preparationTimestampEnd;
    private String scopeOfScan;
    private MicroCTSpecimenStruct specimen;
    
    private static final Logger logger=Logger.getLogger(MicroCTScanningStruct.class);
    
    /**Creates a new instance by initializing 
     * all the fields to be empty (Null values will cause NPE issues)*/
    public MicroCTScanningStruct(){
        specimenURI="";
        specimenName="";
        description="";
        equipmentURI="";
        equipment="";
        contrastMethod = "";
        protocol = "";
        voltage = "";
        current = "";
        filter = "";
        zoom = "";
        cameraResolution = "";
        averaging = "";
        randomMovement = "";
        scanDegrees = "";
        exposureTime = "";
        oversizeSettings = "";
        methodName="";
        methodURI="";
        scanningURI="";
        scanning="";
        scanningLabel="";
        deviceURI="";
        deviceName="";
        datasetURI="";
        datasetName="";
        deviceType="";
        actorName="";
        actorURI="";
        products=new ArrayList<>();
        productStatus="";
        fileLocation="";
        sampleHolder="";
        scanningMedium="";
        scannedPart="";
        scanningDuration="";
        scanDate="";
        preparationTimestampStart="";
        preparationTimestampEnd="";
        scopeOfScan="";
        specimen=null;
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
    
    public String getScanning() {
        return scanning;
    }
    
    public String getScanningLabel() {
        return scanningLabel;
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
        return this.scanDate;
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
    
    public String getEquipment() {
        return equipment;
    }
    
    public String getContrastMethod() {
        return contrastMethod;
    }
     
    public String getVoltage() {
        return voltage;
    }
    
    public String getFilter() {
        return filter;
    }
    
    public String getProtocol() {
        return protocol;
    }
    
    public String getPreparationDateTime() {
        return preparationTimestampStart+" - "+preparationTimestampEnd;
    }
    
    public String getZoom() {
        return zoom;
    }
    
    public String getExposureTime() {
        return exposureTime;
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
      
    public String getScopeOfScan() {
        return this.scopeOfScan;
    }
    
    public String getSampleHolder() {
        return this.sampleHolder;
    }
    
    public String getScanningMedium() {
        return this.scanningMedium;
    }
    
    public String getScannedPart() {
        return this.scannedPart;
    }
    
    public String getScanDate() {
        return this.scanDate;
    }
    
    public String getScanningDuration() {
        return this.scanningDuration;
    }
    
    public String getCurrent(){
        return this.current;
    }
    
    public String getCameraResolution(){
        return this.cameraResolution;
    }
    
    public String getAveraging(){
        return this.averaging;
    }
    
    public String getRandomMovement(){
        return this.randomMovement;
    }
    
    public String getScanDegrees(){
        return this.scanDegrees;
    }
    
    public String getOversizeSettings(){
        return this.oversizeSettings;
    }
    
    public String getFileLocation(){
        return this.fileLocation;
    }
     
    public MicroCTSpecimenStruct getSpecimen() {
        return this.specimen;
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

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }
    
    public void setContrastMethod(String contrastMethod) {
        this.contrastMethod = contrastMethod;
    }
    
    public void setZoom(String zoom) {
        this.zoom = zoom;
    }
    
    public void setVoltage(String voltage) {
        this.voltage = voltage;
    }
    
    public void setFilter(String filter) {
        this.filter = filter;
    }
    
    public void setExposureTime(String exposureTime) {
        this.exposureTime = exposureTime;
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
    
    public void setScanning(String scanning) {
        this.scanning = scanning;
    }
    
    public void setScanningLabel(String scanningLabel) {
        this.scanningLabel = scanningLabel;
    }

    public void setTimespan(String timespan) {
        this.scanDate = timespan;
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

    public void setScopeOfScan(String scope) {
        this.scopeOfScan = scope;
    }
       
    public MicroCTScanningStruct withSpecimen(MicroCTSpecimenStruct specimenStruct) {
        this.specimen=specimenStruct;
        return this;
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
        this.scanDate= timespan;
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
    
    public MicroCTScanningStruct withEquipment(String equipment) {
        this.equipment= equipment;
        return this;
    }
    
    public MicroCTScanningStruct withScanningURI(String scanningURI) {
        this.scanningURI = scanningURI;
        return this;
    }
  
    public MicroCTScanningStruct withScanning(String scanning) {
        this.scanning = scanning;
        return this;
    }
    
    public MicroCTScanningStruct withScanningLabel(String scanningLabel) {
        this.scanningLabel = scanningLabel;
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
    
    public MicroCTScanningStruct withZoom(String zoom) {
        this.zoom= zoom;
        return this;
    }
    
    public MicroCTScanningStruct withVoltage(String voltage) {
        this.voltage= voltage;
        return this;
    }
    
    public MicroCTScanningStruct withCurrent(String current) {
        this.current= current;
        return this;
    }
    
    public MicroCTScanningStruct withExposureTime(String exposureTime) {
        this.exposureTime= exposureTime;
        return this;
    }
    
    public MicroCTScanningStruct withOversizeSettings(String oversizeSettings) {
        this.oversizeSettings= oversizeSettings;
        return this;
    }
    
    public MicroCTScanningStruct withFilter(String filter) {
        this.filter = filter;
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
    
    public MicroCTScanningStruct withSampleHolder(String sampleHolder) {
        this.sampleHolder = sampleHolder;
        return this;
    }
    
    public MicroCTScanningStruct withScanningMedium(String scanningMedium) {
        this.scanningMedium = scanningMedium;
        return this;
    }
    
    public MicroCTScanningStruct withScannedPart(String scannedPart) {
        this.scannedPart = scannedPart;
        return this;
    }
    
    public MicroCTScanningStruct withScanDate(String scanDate) {
        this.scanDate = scanDate;
        return this;
    }
    
    public MicroCTScanningStruct withPreparationTimestampStart(String prepTimestamp) {
        this.preparationTimestampStart = prepTimestamp;
        return this;
    }
    
    public MicroCTScanningStruct withPreparationTimestampEnd(String prepTimestamp) {
        this.preparationTimestampEnd = prepTimestamp;
        return this;
    }
    
    public MicroCTScanningStruct withScanningDuration(String duration) {
        this.scanningDuration = duration;
        return this;
    }
    
    public MicroCTScanningStruct withCameraResolution(String cameraResolution) {
        this.cameraResolution = cameraResolution;
        return this;
    }
    
    public MicroCTScanningStruct withAveraging(String averaging) {
        this.averaging = averaging;
        return this;
    }
    
    public MicroCTScanningStruct withRandomMovement(String randomMovement) {
        this.randomMovement = randomMovement;
        return this;
    }
    
    public MicroCTScanningStruct withScanDegrees(String scanDegrees) {
        this.scanDegrees = scanDegrees;
        return this;
    }
    
    public MicroCTScanningStruct withFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
        return this;
    }
    
    public MicroCTScanningStruct withProtocol(String protocol) {
        this.protocol = protocol;
        return this;
    }
    
    public MicroCTScanningStruct withProductStatus(String status) {
        this.productStatus = status;
        return this;
    }
    
    public MicroCTScanningStruct withScopeOfScan(String scope) {
        this.scopeOfScan = scope;
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
            if(!actorURI.isEmpty()){
                retTriples+= "<"+scanningURI+"> <"+Resources.carriedOutBy+"> <"+actorURI+"> .\n";
            }
            if (!contrastMethod.isEmpty()) {
                    retTriples += "<" + scanningURI + "> <" + Resources.hasContrastMethod + "> \"" + contrastMethod + "\" .\n";
                }
            if(!datasetURI.isEmpty()){
                retTriples+= "<"+datasetURI+"> <"+Resources.refersTo+"> <"+scanningURI+"> . \n";
             }
            if(!scanningLabel.isEmpty()){
                retTriples+= "<"+scanningURI+"> <"+Resources.rdfsLabel+"> \""+scanningLabel+"\" .\n";
            }
            if(!scanning.isEmpty()){
                retTriples+= "<"+scanningURI+"> <"+Resources.isIdentifiedBy+"> \""+scanning+"\" .\n";
            }
            if (!zoom.isEmpty()) {
                    retTriples += "<" + scanningURI + "> <" + Resources.hasZoom + "> \"" + zoom + "\" .\n";
                }
            if (!filter.isEmpty()) {
                    retTriples += "<" + scanningURI + "> <" + Resources.hasFilter + "> \"" + filter + "\" .\n";
                }
            if (!voltage.isEmpty()) {
                    retTriples += "<" + scanningURI + "> <" + Resources.hasVoltage + "> \"" + voltage + "\" .\n";
                }
            if (!exposureTime.isEmpty()) {
                    retTriples += "<" + scanningURI + "> <" + Resources.hasExposureTime + "> \"" + exposureTime + "\" .\n";
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
              +"ScanningLabel: "+this.scanningLabel+"\t"
//              +"DatasetURI: "+this.datasetURI+"\t"
//              +"DatasetTitle: "+this.datasetName+"\t"
//              +"SpecimenURI: "+this.specimenURI+"\t"
//              +"SpecimenName: "+this.specimenName+"\t"
              +"ContrastEnhancementMethod: "+this.contrastMethod+"\t"
              +"Protocol: "+this.protocol+"\t"
              +"BeginOfPreparationTimestamp: "+this.preparationTimestampStart+"\t"
              +"EndOfPreparationTimestamp: "+this.preparationTimestampEnd+"\t"
              +"Notes: "+this.description+"\t"
              +"ScopeOfScan: "+this.scopeOfScan+"\t"
              +"SampleHolder: "+this.sampleHolder+"\t"
              +"ScanningMedium: "+this.scanningMedium+"\t"
              +"ScannedPart: "+this.scannedPart+"\t"
//              +"ActorURI: "+this.actorURI+"\t"
              +"ActorName: "+this.actorName+"\t"
              +"ScanDate: "+this.scanDate+"\t"
              +"ScanDuration: "+this.scanningDuration+"\t"
//              +"DeviceURI: "+this.deviceURI+"\t"
              +"DeviceName: "+this.deviceName+"\t"
//              +"DeviceType: "+this.deviceType+"\t"
              +"Voltage_kV: "+this.voltage+"\t"
              +"Current_uA: "+this.current+"\t"
              +"Filter: "+this.filter+"\t"
              +"Zoom_um: "+this.zoom+"\t"
              +"CameraResolution: "+this.cameraResolution+"\t"
              +"Averaging: "+this.averaging+"\t"
              +"RandomMovement: "+this.randomMovement+"\t"
              +"ScanDegrees: "+this.scanDate+"\t"
              +"ExposureTime_ms: "+this.exposureTime+"\t"
              +"OversizeSettings: "+this.oversizeSettings+"\t"
              +"FileLocation: "+this.fileLocation+"\t"
              +"Specimen: "+this.specimen
//              +"Products: "+this.products+"\t"
//              +"ProductStatus: "+this.productStatus
                ;
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