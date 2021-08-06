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
public class MicroCTSpecimenStruct {

    private String specimenName;
    private String specimenURI;
    private String description;
    private String collectionURI;
    private String collectionName;
    private String providerName;
    private String providerURI;
    private String specimenType;
    private String speciesURI;
    private String speciesName;
    private String fixationType;
    private String preservationType;
    private String dimensionUnit;
    private String dimensionValue;
    private String dimensionURI;
    private String dimensionTypeURI;
    private String dimensionName;
    private String institutionURI;
    private String institutionName;
    private String datasetURI;
    private String datasetName;
    
    private static final Logger logger=Logger.getLogger(MicroCTSpecimenStruct.class);
    /**Creates a new instance by initializing 
     * all the fields to be empty (Null values will cause NPE issues)*/
    public MicroCTSpecimenStruct(){
        collectionURI="";
        collectionName="";
        providerName="";
        providerURI="";
        specimenName="";
        specimenURI="";
        datasetName="";
        datasetURI="";
        speciesURI="";
        description="";
        speciesName="";
        dimensionTypeURI="";
        dimensionURI="";;
        dimensionName="";
        dimensionValue="";
        dimensionUnit="";
        institutionURI="";
        institutionName="";
        fixationType="";
        preservationType="";
    }

    public String getCollectionURI() {
        return collectionURI;
    }
     
    public String getCollectionName() {
        return collectionName;
    }

    public String getProviderURI() {
        return providerURI;
    }
    
    public String getProviderName() {
        return providerName;
    }
    
    public String getDescription() {
        return description;
    }
    
    public String getSpecimenName() {
        return specimenName;
    }
    
    public String getSpecimenURI() {
        return specimenURI;
    }
    
    public String getDatasetName() {
        return datasetName;
    }
    
    public String getDatasetURI() {
        return datasetURI;
    }
    
    public String getInstitutionName() {
        return institutionName;
    }
    
    public String getInstitutionURI() {
        return institutionURI;
    }
    
    public String getSpeciesName() {
        return speciesName;
    }
    
    public String getSpeciesURI() {
        return speciesURI;
    }
    
    public String getSpeciesType() {
        return specimenType;
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
      
    public String getDimensionValue() {
        return dimensionValue;
    }
      
    public String getDimensionTypeURI() {
        return dimensionTypeURI;
    }
     
    public String getFixationType() {
        return fixationType;
    }
    
    public String getPreservationType() {
        return preservationType;
    }
     
    public void setSpecimenName(String specimenName) {
        this.specimenName = specimenName;
    }

    public void setSpecimenURI(String specimenURI) {
        this.specimenURI = specimenURI;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCollectionURI(String collectionURI) {
        this.collectionURI = collectionURI;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public void setProviderURI(String providerURI) {
        this.providerURI = providerURI;
    }

    public String getSpecimenType() {
        return specimenType;
    }

    public void setSpecimenType(String specimenType) {
        this.specimenType = specimenType;
    }

    public void setSpeciesURI(String speciesURI) {
        this.speciesURI = speciesURI;
    }

    public void setSpeciesName(String speciesName) {
        this.speciesName = speciesName;
    }

    public void setFixationType(String fixationType) {
        this.fixationType = fixationType;
    }

    public void setPreservationType(String preservationType) {
        this.preservationType = preservationType;
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

    public void setInstitutionURI(String institutionURI) {
        this.institutionURI = institutionURI;
    }

    public void setInstitutionName(String institutionName) {
        this.institutionName = institutionName;
    }

    public void setDatasetURI(String datasetURI) {
        this.datasetURI = datasetURI;
    }

    public void setDatasetName(String datasetName) {
        this.datasetName = datasetName;
    }
    
    public MicroCTSpecimenStruct withSpeciesURI(String speciesURI) {
        this.speciesURI = speciesURI;
        return this;
    }

    public MicroCTSpecimenStruct withCollectionName(String collectionName) {
        this.collectionName = collectionName;
        return this;
    }
    
    public MicroCTSpecimenStruct withCollectionURI(String collectionURI) {
        this.collectionURI = collectionURI;
        return this;
    }
    

    public MicroCTSpecimenStruct withProviderName(String providerName) {
        this.providerName = providerName;
        return this;
    }

    public MicroCTSpecimenStruct withProviderURI(String providerURI) {
        this.providerURI = providerURI;
        return this;
    }

      public MicroCTSpecimenStruct withDatasetName(String datasetName) {
        this.datasetName = datasetName;
        return this;
    }

    public MicroCTSpecimenStruct withDatasetURI(String datasetURI) {
        this.datasetURI = datasetURI;
        return this;
    }
    
      public MicroCTSpecimenStruct withInstitutionName(String institutionName) {
        this.institutionName = institutionName;
        return this;
    }

    public MicroCTSpecimenStruct withInstitutionURI(String institutionURI) {
        this.institutionURI = institutionURI;
        return this;
    }

    public MicroCTSpecimenStruct withDescription(String description) {
        this.description = description;
        return this;
    }

    public MicroCTSpecimenStruct withSpecimenURI(String specimenURI) {
        this.specimenURI = specimenURI;
        return this;
    }
    
    public MicroCTSpecimenStruct withSpecimenName(String specimenName) {
        this.specimenName= specimenName;
        return this;
    }
     
    public MicroCTSpecimenStruct withSpecimenType(String specimenType) {
        this.specimenType= specimenType;
        return this;
    }

    public MicroCTSpecimenStruct withSpeciesName(String speciesName) {
        this.speciesName= speciesName;
        return this;
    }
     
    public MicroCTSpecimenStruct  withDimensionName(String dimensionName) {
        this.dimensionName = dimensionName;
        return this;
    }
    
    public MicroCTSpecimenStruct  withDimensionURI(String dimensionURI) {
        this.dimensionURI = dimensionURI;
        return this;
    }
    
    public MicroCTSpecimenStruct  withDimensionValue(String dimensionValue) {
        this.dimensionValue = dimensionValue;
        return this;
    }
    
    public MicroCTSpecimenStruct  withDimensionUnit(String dimensionUnit) {
        this.dimensionUnit = dimensionUnit;
        return this;
    }
    
    public MicroCTSpecimenStruct  withDimensionTypeURI(String dimensionTypeURI) {
        this.dimensionTypeURI = dimensionTypeURI;
        return this;
    }
    
    public MicroCTSpecimenStruct  withFixationType(String fixationValue) {
        this.fixationType = fixationValue  ;
        return this;
    }
    
    public MicroCTSpecimenStruct  withPreservationType(String preservationMediumValue) {
        this.preservationType = preservationMediumValue  ;
        return this;
    }
   
    /**Produces an NTRIPLES output so that it can be used to SPARQL queries
     * 
     * @return an NTRIPLES representation of the struct fields */
    public String toNtriples(){
        String retTriples="";
        if(!specimenURI.isEmpty()){
            retTriples+= "<"+specimenURI+"> <"+Resources.rdfTypeLabel+"> <"+Resources.specimenLabel+"> .\n";
            if(!collectionURI.isEmpty()){
                retTriples+= "<"+specimenURI+"> <"+Resources.formsPartOf+"> <"+collectionURI+"> .\n";
            }
            if(!providerURI.isEmpty()){
                retTriples+= "<"+specimenURI+"> <"+Resources.wasProvidedBy+"> <"+providerURI+"> .\n";
            }
            if(!description.isEmpty()){
                retTriples+= "<"+specimenURI+"> <"+Resources.hasNote+"> \""+description+"\" .\n";
            }
            if(!speciesURI.isEmpty()){
                retTriples+= "<"+specimenURI+"> <"+Resources.belongsTo+"> <"+speciesURI+"> .\n";
            }
            if(!dimensionURI.isEmpty()){
                retTriples+= "<"+specimenURI+"> <"+Resources.hasDimension+"> <"+dimensionURI+"> .\n";
            }
            if(!datasetURI.isEmpty()){
                retTriples+= "<"+datasetURI+"> <"+Resources.refersTo+"> <"+specimenURI+"> . \n";
            }
        }
        if(!specimenURI.isEmpty()){
            retTriples+= "<"+specimenURI+"> <"+Resources.rdfTypeLabel+"> <"+Resources.specimenLabel+"> .\n";
            if(!specimenName.isEmpty()){
                retTriples+= "<"+specimenURI+"> <"+Resources.rdfsLabel+"> \""+specimenName+"\" .\n";
            }
        }
        if(!speciesURI.isEmpty()){
            retTriples+= "<"+speciesURI+"> <"+Resources.rdfTypeLabel+"> <"+Resources.speciesLabel+"> .\n";
            if(!speciesName.isEmpty()){
                retTriples+= "<"+speciesURI+"> <"+Resources.rdfsLabel+"> \""+speciesName+"\" .\n";
            }
        }
        if(!datasetURI.isEmpty()){
            retTriples+= "<"+datasetURI+"> <"+Resources.rdfTypeLabel+"> <"+Resources.datasetLabel+"> .\n";
            if(!datasetName.isEmpty()){
                retTriples+= "<"+datasetURI+"> <"+Resources.rdfsLabel+"> \""+datasetName+"\" .\n";
            }
        }
        if(!collectionURI.isEmpty()){
            retTriples+= "<"+collectionURI+"> <"+Resources.rdfTypeLabel+"> <"+Resources.collectionLabel+"> .\n";
            if(!collectionName.isEmpty()){
                retTriples+= "<"+collectionURI+"> <"+Resources.rdfsLabel+"> \""+collectionName+"\" .\n";
            }
        }
        if(!providerURI.isEmpty()){
            retTriples+= "<"+providerURI+"> <"+Resources.rdfTypeLabel+"> <"+Resources.personLabel+"> .\n";
            if(!providerName.isEmpty()){
                retTriples+= "<"+providerURI+"> <"+Resources.rdfsLabel+"> \""+providerName+"\" .\n";
            }
             if(!institutionURI.isEmpty()){
                retTriples+= "<"+providerURI+"> <"+Resources.isCurrentMemberOf+"> <"+institutionURI+"> .\n";
            }
        }
        if(!institutionURI.isEmpty()){
            retTriples+= "<"+institutionURI+"> <"+Resources.rdfTypeLabel+"> <"+Resources.institutionLabel+"> .\n";
            if(!institutionName.isEmpty()){
                retTriples+= "<"+institutionURI+"> <"+Resources.rdfsLabel+"> \""+institutionName+"\" .\n";
            }
        }
        if(!dimensionURI.isEmpty()){
            retTriples+= "<"+dimensionURI+"> <"+Resources.rdfTypeLabel+"> <"+Resources.dimensionLabel+"> .\n";
            if(!dimensionTypeURI.isEmpty()){
                retTriples+= "<"+dimensionURI+"> <"+Resources.hasType+"> <"+dimensionTypeURI+"> .\n";
                if(!dimensionName.isEmpty()){
                    retTriples+= "<"+dimensionTypeURI+"> <"+Resources.rdfsLabel+"> \""+dimensionName+"\" .\n";
                    retTriples+= "<"+dimensionURI+"> <"+Resources.rdfsLabel+"> \""+dimensionName+"\" .\n";
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
       
        if(!this.providerURI.isEmpty()){
            this.validateURI(this.providerURI);
        }
        if(!this.institutionURI.isEmpty()){
            this.validateURI(this.institutionURI);
        }
      
        if(!this.specimenURI.isEmpty()){
            this.validateURI(this.specimenURI);
        }
        if(!this.dimensionURI.isEmpty()){
            this.validateURI(this.dimensionURI);
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

        return "CollectionURI: "+collectionURI+"\t"
              +"CollectionName: "+collectionName+"\t"
              +"ProviderURI: "+providerURI+"\t"
              +"ProviderName: "+providerName+"\t"
              +"SpecimenName: "+specimenName+"\t"
              +"SpecimenURI: "+specimenURI+"\t"
              +"DatasetURI: "+datasetURI+"\t"
              +"DatasetName: "+datasetName+"\t"
              +"SpeciesURI: "+speciesURI+"\t"
              +"SpeciesName: "+speciesName+"\t"
              +"Description: "+description+"\t"
              +"DimensionURI: "+dimensionURI+"\t"
              +"DimensionTypeURI: "+dimensionTypeURI+"\t"
              +"DimensionName: "+dimensionName+"\t"
              +"DimensionValue: "+dimensionValue+"\t"
              +"DimensionUnit: "+dimensionUnit+"\t"
              +"InstitutionURI: "+institutionURI+"\t"
              +"InstitutionName: "+institutionName+"\t"
              +"FixationType: "+fixationType+"\t"
              +"PreservationType: "+preservationType;
    }
    
    @Override
    public boolean equals(Object anotherObject){
        if(anotherObject instanceof MicroCTSpecimenStruct){
            MicroCTSpecimenStruct anotherStruct=(MicroCTSpecimenStruct)anotherObject;
            return this.speciesName.equals(anotherStruct.getSpeciesName()) &&
                   this.institutionURI.equals(anotherStruct.getInstitutionURI()) &&
                  
                   this.speciesURI.equals(anotherStruct.getSpeciesURI()) &&
                   this.datasetURI.equals(anotherStruct.getDatasetURI()) &&
                     this.datasetName.equals(anotherStruct.getDatasetName()) &&
                   this.institutionName.equals(anotherStruct.getInstitutionName()) &&
                   this.providerURI.equals(anotherStruct.getProviderURI()) &&
                     this.providerName.equals(anotherStruct.getProviderName()) &&
                   this.description.equals(anotherStruct.getDescription()) &&
                   this.specimenURI.equals(anotherStruct.getSpecimenURI()) &&
                   this.specimenName.equals(anotherStruct.getSpecimenName());
                   
        }
        else{
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.specimenURI);
        return hash;
    }
}