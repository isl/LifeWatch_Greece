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
public class GensSampleStruct {

    private String sampleURI;
    private String sampleName;
    private String description;
    private String transformedSampleURI;
    private String transformedSampleName;
    private String dimensionUnit;
    private String dimensionValue;
    private String dimensionURI;
    private String dimensionTypeURI;
    private String dimensionName;
    private String deviceURI;
    private String deviceName;
    private String deviceType;
    private String postProcessingURI;
    private String productName;
    private String productURI;
    private String speciesURI;
    private String speciesName;
    private String postProductName;
    private String postProductURI;
    private String sequencingURI;
    private String transformationURI;
    private String placeURI;
    private String placeName;
    private String datasetURI;
    private String datasetName;
    private static final Logger logger=Logger.getLogger(GensSampleStruct.class);
    
    /**Creates a new instance by initializing 
     * all the fields to be empty (Null values will cause NPE issues)*/
    public GensSampleStruct(){
        transformationURI="";
        sequencingURI="";
        postProcessingURI="";
        description="";
        productName="";
        productURI="";
        postProductName="";
        postProductURI="";
        speciesURI="";
        speciesName="";
        transformedSampleURI="";
        transformedSampleName="";
        sampleURI="";
        sampleName="";
        placeURI="";
        placeName="";
        deviceURI="";
        deviceName="";
        datasetURI="";
        datasetName="";
        deviceType="";
        dimensionTypeURI="";
        dimensionURI="";
        dimensionName="";
        dimensionValue="";
        dimensionUnit="";
    }
    
    public String getTransformationURI() {
        return transformationURI;
    }
    
    public String getSequencingURI() {
        return sequencingURI;
    }
    
    public String getSpeciesURI() {
        return speciesURI;
    }
    
    public String getSpeciesName() {
        return speciesName;
    }
    
    public String getPlaceURI() {
        return placeURI;
    }
    
    public String getPlaceName() {
        return placeName;
    }
    
    public String getPostProcessingURI() {
        return postProcessingURI;
    }
    
    public String getProductURI() {
        return productURI;
    }
    
    public String getProductName() {
        return productName;
    }
      
    public String getPostProductURI() {
        return postProductURI;
    }
    public String getPostProductName() {
        return postProductName;
    }
     
    public String getTransformedSampleURI() {
        return transformedSampleURI;
    }
    
    public String getTransformedSampleName() {
        return transformedSampleName;
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
     
    public String getDimensionURI() {
        return dimensionURI;
    }
     
    public String getDimensionName() {
        return dimensionName;
    }
      
    public String getDimensionUnit() {
        return dimensionUnit;
    }
       
    public String getDatasetName() {
        return datasetName;
    }
      
    public String getDatasetURI() {
        return datasetURI;
    }
      
    public String getDimensionValue() {
        return dimensionValue;
    }
      
    public String getDimensionTypeURI() {
        return dimensionTypeURI;
    }
     
    public String getSampleURI() {
        return sampleURI;
    }
   
    public String getSampleName() {
        return sampleName;
    }
      
    public void setSampleURI(String sampleURI) {
        this.sampleURI = sampleURI;
    }

    public void setSampleName(String sampleName) {
        this.sampleName = sampleName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTransformedSampleURI(String transformedSampleURI) {
        this.transformedSampleURI = transformedSampleURI;
    }

    public void setTransformedSampleName(String transformedSampleName) {
        this.transformedSampleName = transformedSampleName;
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

    public void setDimensionTypeURI(String dimensionTypeURI) {
        this.dimensionTypeURI = dimensionTypeURI;
    }

    public void setDimensionName(String dimensionName) {
        this.dimensionName = dimensionName;
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

    public void setPostProcessingURI(String postProcessingURI) {
        this.postProcessingURI = postProcessingURI;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setProductURI(String productURI) {
        this.productURI = productURI;
    }

    public void setSpeciesURI(String speciesURI) {
        this.speciesURI = speciesURI;
    }

    public void setSpeciesName(String speciesName) {
        this.speciesName = speciesName;
    }

    public void setPostProductName(String postProductName) {
        this.postProductName = postProductName;
    }

    public void setPostProductURI(String postProductURI) {
        this.postProductURI = postProductURI;
    }

    public void setSequencingURI(String sequencingURI) {
        this.sequencingURI = sequencingURI;
    }

    public void setTransformationURI(String transformationURI) {
        this.transformationURI = transformationURI;
    }

    public void setPlaceURI(String placeURI) {
        this.placeURI = placeURI;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public void setDatasetURI(String datasetURI) {
        this.datasetURI = datasetURI;
    }

    public void setDatasetName(String datasetName) {
        this.datasetName = datasetName;
    }

    public GensSampleStruct withTransformationURI(String transformationURI) {
        this.transformationURI = transformationURI;
        return this;
    }

    public GensSampleStruct withSequencingURI(String sequencingURI) {
        this.sequencingURI= sequencingURI;
        return this;
    }
    
    public GensSampleStruct withPlaceURI(String placeURI) {
        this.placeURI= placeURI;
        return this;
    }
    
    public GensSampleStruct withPlaceName(String placeName) {
        this.placeName= placeName;
        return this;
    }
     
    public GensSampleStruct withSpeciesURI(String speciesURI) {
        this.speciesURI= speciesURI;
        return this;
    }
    
    public GensSampleStruct withSpeciesName(String speciesName) {
        this.speciesName= speciesName;
        return this;
    }
 
    public GensSampleStruct withPostProcessingURI(String postProcessingURI) {
        this.postProcessingURI = postProcessingURI;
        return this;
    }
    
    public GensSampleStruct withTransformedSampleURI(String transformedSampleURI) {
        this.transformedSampleURI = transformedSampleURI;
        return this;
    }
    
    public GensSampleStruct withTransformedSampleName(String transformedSampleName) {
        this.transformedSampleName = transformedSampleName;
        return this;
    }
    
    public GensSampleStruct withDeviceName(String deviceName) {
        this.deviceName = deviceName;
        return this;
    }
    
    public GensSampleStruct withDatasetName(String datasetName) {
        this.datasetName = datasetName;
        return this;
    }
    
    public GensSampleStruct withDatasetURI(String datasetURI) {
        this.datasetURI = datasetURI;
        return this;
    }
    
    public GensSampleStruct withDescription(String description) {
        this.description = description;
        return this;
    }
    
    public GensSampleStruct withDeviceURI(String deviceURI) {
        this.deviceURI = deviceURI;
        return this;
    }
 
    public GensSampleStruct withDeviceType(String deviceType) {
        this.deviceType = deviceType;
        return this;
    }
    
    
    public GensSampleStruct withProductURI(String productURI) {
        this.productURI = productURI;
        return this;
    }
    
    public GensSampleStruct withProductName(String productName) {
        this.productName = productName;
        return this;
    }

    public GensSampleStruct withPostProductName(String postProductName) {
        this.postProductName= postProductName;
        return this;
    }
    
    public GensSampleStruct withPostProductURI(String postProductURI) {
        this.postProductURI= postProductURI;
        
        return this;
    }
    
    public GensSampleStruct withDimensionName(String dimensionName) {
        this.dimensionName = dimensionName;
        return this;
    }
    
    public GensSampleStruct withDimensionURI(String dimensionURI) {
        this.dimensionURI = dimensionURI;
        return this;
    }
    
    public GensSampleStruct withSampleURI(String sampleURI) {
        this.sampleURI = sampleURI;
        return this;
    }
    
    public GensSampleStruct withSampleName(String sampleName) {
        this.sampleName = sampleName;
        return this;
    }
    
    public GensSampleStruct withDimensionValue(String dimensionValue) {
        this.dimensionValue = dimensionValue;
        return this;
    }
    
    public GensSampleStruct withDimensionUnit(String dimensionUnit) {
        this.dimensionUnit = dimensionUnit;
        return this;
    }
    
    public GensSampleStruct withDimensionType(String dimensionType) {
        this.dimensionTypeURI = dimensionType;
        return this;
    }

    /**Produces an NTRIPLES output so that it can be used to SPARQL queries
     * 
     * @return an NTRIPLES representation of the struct fields */
    public String toNtriples(){
        String retTriples="";
        if(!transformationURI.isEmpty()){
            retTriples+= "<"+transformationURI+"> <"+Resources.rdfTypeLabel+"> <"+Resources.transformationEventLabel+"> .\n";
            if(!sampleURI.isEmpty()){
                retTriples+= "<"+transformationURI+"> <"+Resources.transformed+"> <"+sampleURI+"> .\n";
            }
            if(!transformedSampleURI.isEmpty()){
                retTriples+= "<"+transformationURI+"> <"+Resources.resultedIn+"> <"+transformedSampleURI+"> .\n";
            }
        }
        if(!sampleURI.isEmpty()){
            retTriples+= "<"+sampleURI+"> <"+Resources.rdfTypeLabel+"> <"+Resources.sampleLabel+"> .\n";
            if(!sampleName.isEmpty()){
                retTriples+= "<"+sampleURI+"> <"+Resources.rdfsLabel+"> \""+sampleName+"\" .\n";
            }
            if(!placeURI.isEmpty()){
                retTriples+= "<"+sampleURI+"> <"+Resources.hasCurrentLocation+"> <"+placeURI+"> .\n";
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
        if(!transformedSampleURI.isEmpty()){
            retTriples+= "<"+transformedSampleURI+"> <"+Resources.rdfTypeLabel+"> <"+Resources.sampleLabel+"> .\n";
            if(!transformedSampleName.isEmpty()){
                retTriples+= "<"+transformedSampleURI+"> <"+Resources.rdfsLabel+"> \""+transformedSampleName+"\" .\n";
            }
            if(!placeURI.isEmpty()){
                retTriples+= "<"+transformedSampleURI+"> <"+Resources.hasCurrentLocation+"> <"+placeURI+"> .\n";
            }
        }
        if(!datasetURI.isEmpty()){
            retTriples+= "<"+datasetURI+"> <"+Resources.rdfTypeLabel+"> <"+Resources.datasetLabel+"> .\n";
            if(!datasetName.isEmpty()){
                retTriples+= "<"+datasetURI+"> <"+Resources.rdfsLabel+"> \""+datasetName+"\" .\n";
            }
        }
        if(!placeURI.isEmpty()){
            retTriples+= "<"+placeURI+"> <"+Resources.rdfTypeLabel+"> <"+Resources.placeLabel+"> .\n";
            if(!placeName.isEmpty()){
                retTriples+= "<"+placeURI+"> <"+Resources.rdfsLabel+"> \""+placeName+"\" .\n";
            }
            if(!dimensionURI.isEmpty()){
                retTriples+= "<"+placeURI+"> <"+Resources.hasDimension+"> <"+dimensionURI+"> .\n";
            }
        }
        if(!sequencingURI.isEmpty()){
            retTriples+= "<"+sequencingURI+"> <"+Resources.rdfTypeLabel+"> <"+Resources.digitizationProcessLabel+"> .\n";
            retTriples+= "<"+sequencingURI+"> <"+Resources.hasType+"> \"Sequencing\" .\n";
            if(!sampleURI.isEmpty()){
                retTriples+= "<"+sequencingURI+"> <"+Resources.digitized+"> <"+sampleURI+"> .\n";
            }
            if(!productURI.isEmpty()){
                retTriples+= "<"+sequencingURI+"> <"+Resources.createdDerivative+"> <"+productURI+"> .\n";
            }
            if(!deviceURI.isEmpty()){
                retTriples+= "<"+sequencingURI+"> <"+Resources.happenedOnDevice+"> <"+deviceURI+"> .\n";
            }
            if(!datasetURI.isEmpty()){
                retTriples+= "<"+datasetURI+"> <"+Resources.refersTo+"> <"+sequencingURI+"> . \n";
            }
            if(!description.isEmpty()){
                retTriples+= "<"+sequencingURI+"> <"+Resources.hasNote+"> \""+description+"\" .\n";
            }
        }
        if(!deviceURI.isEmpty()){
            retTriples+= "<"+deviceURI+"> <"+Resources.rdfTypeLabel+"> <"+Resources.digitalDeviceLabel+"> .\n";
            if(!deviceName.isEmpty()){
                retTriples+= "<"+deviceURI+"> <"+Resources.rdfsLabel+"> \""+deviceName+"\" .\n";
            }
            if(!deviceType.isEmpty()){
                retTriples+= "<"+deviceURI+"> <"+Resources.hasType+"> \""+deviceType+"\" .\n";
            }
        }
        if(!postProcessingURI.isEmpty()){
            retTriples+= "<"+postProcessingURI+"> <"+Resources.rdfTypeLabel+"> <"+Resources.formalDerivationEventLabel+"> .\n";
            if(!productURI.isEmpty()){
                retTriples+= "<"+productURI+"> <"+Resources.wasDerivationSourceFor+"> <"+postProcessingURI+"> .\n";
            }
            if(!postProductURI.isEmpty()){
                retTriples+= "<"+postProcessingURI+"> <"+Resources.hasCreated+"> <"+postProductURI+"> .\n";
            }
        }
        if(!productURI.isEmpty()){
            retTriples+= "<"+productURI+"> <"+Resources.rdfTypeLabel+"> <"+Resources.dataObjectLabel+"> .\n";
            if(!productName.isEmpty()){
                retTriples+= "<"+productURI+"> <"+Resources.rdfsLabel+"> \""+productName+"\" .\n";
            }
        }
        if(!postProductURI.isEmpty()){
            retTriples+= "<"+postProductURI+"> <"+Resources.rdfTypeLabel+"> <"+Resources.dataObjectLabel+"> .\n";
            if(!postProductName.isEmpty()){
                retTriples+= "<"+postProductURI+"> <"+Resources.rdfsLabel+"> \""+postProductName+"\" .\n";
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
        logger.debug("Struct in NTriples format: \n"+retTriples);
        return retTriples;
    }
    
    /**Validates the fields that will contain URIs
     * 
     * @throws URIValidationException if any of the URIs is not in valid form */
    public void validateURIs() throws URIValidationException{
        if(!this.postProcessingURI.isEmpty()){
            this.validateURI(this.postProcessingURI);
        }
        if(!this.sequencingURI.isEmpty()){
            this.validateURI(this.sequencingURI);
        }
        if(!this.sampleURI.isEmpty()){
            this.validateURI(this.sampleURI);
        }

        if(!this.dimensionURI.isEmpty()){
            this.validateURI(this.dimensionURI);
        }
        if(!this.dimensionTypeURI.isEmpty()){
            this.validateURI(this.dimensionTypeURI);
        }
         if(!this.placeURI.isEmpty()){
            this.validateURI(this.placeURI);
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

        return "TransformationURI: "+transformationURI+"\t"
              +"SequencingURI: "+sequencingURI+"\t"
              +"PostProcessingURI: "+postProcessingURI+"\t"
              +"Description: "+description+"\t"
              +"ProductURI: "+productURI+"\t"
              +"ProductName: "+productName+"\t"
              +"PostProductURI: "+postProductURI+"\t"
              +"PostProductName: "+postProductName+"\t"
              +"SpeciesURI: "+speciesURI+"\t"
              +"SpeciesName: "+speciesName+"\t"
              +"TransformedSampleURI: "+transformedSampleURI+"\t"
              +"TransformedSampleName: "+transformedSampleName+"\t"
              +"SampleURI: "+sampleURI+"\t"
              +"SampleName: "+sampleName+"\t"
              +"PlaceURI: "+placeURI+"\t"
              +"PlaceName: "+placeName+"\t"
              +"DeviceURI: "+deviceURI+"\t"
              +"DeviceName: "+deviceName+"\t"
              +"DeviceType: "+deviceType+"\t"
              +"DatasetURI: "+datasetURI+"\t"
              +"DatasetName: "+datasetName+"\t"
              +"DimensionURI: "+dimensionURI+"\t"
              +"DimensionTypeURI: "+dimensionTypeURI+"\t"
              +"DimensionName: "+dimensionName+"\t"
              +"DimensionValue: "+dimensionValue+"\t"
              +"DimensionUnit: "+dimensionUnit;
    }
    
    @Override
    public boolean equals(Object anotherObject){
        if(anotherObject instanceof GensSampleStruct){
            GensSampleStruct anotherStruct=(GensSampleStruct)anotherObject;
            return this.deviceName.equals(anotherStruct.getDeviceName()) &&
                   this.deviceURI.equals(anotherStruct.getDeviceURI()) &&
                   this.datasetName.equals(anotherStruct.getDatasetName()) &&
                   this.datasetURI.equals(anotherStruct.getDatasetURI()) &&
                   this.sequencingURI.equals(anotherStruct.getSequencingURI()) &&
                   this.description.equals(anotherStruct.getDescription()) &&
                   this.postProcessingURI.equals(anotherStruct.getPostProcessingURI()) &&
                   this.dimensionURI.equals(anotherStruct.getDimensionURI()) &&
                   this.dimensionUnit.equals(anotherStruct.getDimensionUnit()) &&
                   this.dimensionValue.equals(anotherStruct.getDimensionValue()) &&
                   this.dimensionTypeURI.equals(anotherStruct.getDimensionTypeURI()) &&
                   this.deviceType.equals(anotherStruct.getDeviceType()) &&
                   this.dimensionUnit.equals(anotherStruct.getDimensionUnit()) &&
                   this.sampleURI.equals(anotherStruct.getSampleURI());
        }else{
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.sequencingURI);
        return hash;
    }
}