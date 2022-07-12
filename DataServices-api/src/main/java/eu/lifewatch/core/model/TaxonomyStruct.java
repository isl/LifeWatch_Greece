package eu.lifewatch.core.model;

import eu.lifewatch.common.Resources;
import eu.lifewatch.exception.URIValidationException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.UUID;
import org.apache.log4j.Logger;

/**
 * Struct object are actually entries in the repository. They are in the form of a 
 * C-Like Struct. These object can also describe themselves in an NTriple form. 
 * 
 * @author Nikos Minadakis (minadakn 'at' ics 'dot' forth 'dot' gr)
 * @author Yannis Marketakis (marketak 'at' ics 'dot' forth 'dot' gr)
 */
public class TaxonomyStruct {
   
    private String speciesName;
    private String genusName;
    private String familyName;
    private String orderName;
    private String className;
    private String kingdomName;
    private String datasetName;
    private String datasetURI;
    private String speciesURI;
    private String genusURI;
    private String familyURI;
    private String orderURI;
    private String classURI;
    private String kingdomURI; 
    private String phylumName;
    private String phylumURI;
    private String scNameId;
    
    private static final Logger logger=Logger.getLogger(TaxonomyStruct.class);
    
    /**Creates a new instance by initializing 
     * all the fields to be empty (Null values will cause NPE issues)*/
    public TaxonomyStruct(){
        datasetName="";
        datasetURI="";
        speciesName="";
        speciesURI="";
        phylumName="";
        phylumURI="";
        genusName="";
        genusURI="";
        orderName="";
        orderURI="";
        familyName="";
        familyURI="";
        className="";
        classURI="";
        kingdomName="";
        kingdomURI="";
        scNameId="";
    }
    
    public String getSpeciesName() {
        return speciesName;
    }
    
    public String getSpeciesURI() {
        return speciesURI;
    }
    
    public String getPhylumName() {
        return phylumName;
    }
    
    public String getPhylumURI() {
        return phylumURI;
    }
    
    public String getDatasetName() {
        return datasetName;
    }
    
    public String getDatasetURI() {
        return datasetURI;
    }

    public String getGenusName() {
        return genusName;
    }
    
    public String getGenusURI() {
        return genusURI;
    }
    
    public String getOrderName() {
        return orderName;
    }
    
    public String getOrderURI() {
        return orderURI;
    }
    
    public String getFamilyName() {
        return familyName;
    }
    
    public String getFamilyURI() {
        return familyURI;
    }
    
    public String getClassName() {
        return className;
    }
    
    public String getClassURI() {
        return classURI;
    }
    
    public String getKingdomName() {
        return kingdomName;
    }
    
    public String getKingdomURI() {
        return kingdomURI;
    }
    
    public String getScNameId() {
        return scNameId;
    }
    
    public void setScNameId(String id) {
        this.scNameId = id;
    }
    
    public void setSpeciesName(String speciesName) {
        this.speciesName = speciesName;
    }

    public void setGenusName(String genusName) {
        this.genusName = genusName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setKingdomName(String kingdomName) {
        this.kingdomName = kingdomName;
    }

    public void setDatasetName(String datasetName) {
        this.datasetName = datasetName;
    }

    public void setDatasetURI(String datasetURI) {
        this.datasetURI = datasetURI;
    }

    public void setSpeciesURI(String speciesURI) {
        this.speciesURI = speciesURI;
    }

    public void setGenusURI(String genusURI) {
        this.genusURI = genusURI;
    }

    public void setFamilyURI(String familyURI) {
        this.familyURI = familyURI;
    }

    public void setOrderURI(String orderURI) {
        this.orderURI = orderURI;
    }

    public void setClassURI(String classURI) {
        this.classURI = classURI;
    }

    public void setKingdomURI(String kingdomURI) {
        this.kingdomURI = kingdomURI;
    }

    public void setPhylumName(String phylumName) {
        this.phylumName = phylumName;
    }

    public void setPhylumURI(String phylumURI) {
        this.phylumURI = phylumURI;
    }
   
    public TaxonomyStruct withSpeciesURI(String speciesURI) {
        this.speciesURI = speciesURI;
        return this;
    }
    
    public TaxonomyStruct withSpeciesName(String speciesName) {
        this.speciesName= speciesName;
        return this;
    }
   
    public TaxonomyStruct withPhylumURI(String phylumURI) {
        this.phylumURI = phylumURI;
        return this;
    }

    public TaxonomyStruct withPhylumName(String phylumName) {
        this.phylumName= phylumName;
        return this;
    }
      
    public TaxonomyStruct withDatasetURI(String datasetURI) {
        this.datasetURI = datasetURI;
        return this;
    }

    public TaxonomyStruct withDatasetName(String datasetName) {
        this.datasetName= datasetName;
        return this;
    }
     
    public TaxonomyStruct withGenusURI(String genusURI) {
        this.genusURI = genusURI;
        return this;
    }
    
    public TaxonomyStruct withGenusName(String genusName) {
        this.genusName= genusName;
        return this;
    }
   
    public TaxonomyStruct withFamilyURI(String familyURI) {
        this.familyURI = familyURI;
        return this;
    }

    public TaxonomyStruct withFamilyName(String familyName) {
        this.familyName= familyName;
        return this;
    }
      
    public TaxonomyStruct withOrderURI(String orderURI) {
        this.orderURI = orderURI;
        return this;
    }
    
    public TaxonomyStruct withOrderName(String orderName) {
        this.orderName= orderName;
        return this;
    }
     
    public TaxonomyStruct withClassURI(String classURI) {
        this.classURI = classURI;
        return this;
    }

    public TaxonomyStruct withClassName(String className) {
        this.className= className;
        return this;
    }
   
    public TaxonomyStruct withKingdomURI(String kingdomURI) {
        this.kingdomURI = kingdomURI;
        return this;
    }

    public TaxonomyStruct withKingdomName(String kingdomName) {
        this.kingdomName= kingdomName;
        return this;
    }
    
    public TaxonomyStruct withScNameId(String id) {
        this.scNameId= id;
        return this;
    }
     
    /**Produces an NTRIPLES output so that it can be used to SPARQL queries
     * 
     * @return an NTRIPLES representation of the struct fields */
    public String toNtriples(){
        String retTriples="";
        if(!speciesURI.isEmpty()){
            retTriples+= "<"+speciesURI+"> <"+Resources.rdfTypeLabel+"> <"+Resources.speciesLabel+"> .\n";
            if(!speciesName.isEmpty()){
                retTriples+= "<"+speciesURI+"> <"+Resources.rdfsLabel+"> \""+speciesName+"\" .\n";
            }
            if(!genusName.isEmpty()){
                retTriples+= "<"+speciesURI+"> <"+Resources.belongsTo+"> <"+genusURI+"> .\n";
            }
            if(!datasetURI.isEmpty()){
                retTriples+= "<"+datasetURI+"> <"+Resources.refersTo+"> <"+speciesURI+"> . \n";
            }   
            if(!scNameId.isEmpty()){
                String scNameIdUri=Resources.defaultNamespaceForURIs+"/"+Resources.SPECIES_IDENTIFIER+"/"+this.scNameId;
                retTriples+="<"+speciesURI+"> <"+Resources.isIdentifiedBy+"> <"+scNameIdUri+">. \n"
                           +"<"+scNameIdUri+"> <"+Resources.rdfTypeLabel+"> <"+Resources.identifierLabel+">. \n"
                           +"<"+scNameIdUri+"> <"+Resources.rdfsLabel+"> \""+this.scNameId+"\". \n";
            }
        }
        if(!genusURI.isEmpty()){
            retTriples+= "<"+genusURI+"> <"+Resources.rdfTypeLabel+"> <"+Resources.genusLabel+"> .\n";
            if(!genusName.isEmpty()){
                retTriples+= "<"+genusURI+"> <"+Resources.rdfsLabel+"> \""+genusName+"\" .\n";
            }
            if(!familyName.isEmpty()){
                retTriples+= "<"+genusURI+"> <"+Resources.belongsTo+"> <"+familyURI+"> .\n";
            }
        }
        if(!familyURI.isEmpty()){
            retTriples+= "<"+familyURI+"> <"+Resources.rdfTypeLabel+"> <"+Resources.familyLabel+"> .\n";
            if(!familyName.isEmpty()){
                retTriples+= "<"+familyURI+"> <"+Resources.rdfsLabel+"> \""+familyName+"\" .\n";
            }
            if(!orderName.isEmpty()){
                retTriples+= "<"+familyURI+"> <"+Resources.belongsTo+"> <"+orderURI+"> .\n";
            }
        }
        if(!orderURI.isEmpty()){
            retTriples+= "<"+orderURI+"> <"+Resources.rdfTypeLabel+"> <"+Resources.orderLabel+"> .\n";
            if(!orderName.isEmpty()){
                retTriples+= "<"+orderURI+"> <"+Resources.rdfsLabel+"> \""+orderName+"\" .\n";
            }
            if(!className.isEmpty()){
                retTriples+= "<"+orderURI+"> <"+Resources.belongsTo+"> <"+classURI+"> .\n";
            }
        }
        if(!classURI.isEmpty()){
            retTriples+= "<"+classURI+"> <"+Resources.rdfTypeLabel+"> <"+Resources.classLabel+"> .\n";
            if(!className.isEmpty()){
                retTriples+= "<"+classURI+"> <"+Resources.rdfsLabel+"> \""+className+"\" .\n";
            }
            if(!phylumName.isEmpty()){
                retTriples+= "<"+classURI+"> <"+Resources.belongsTo+"> <"+phylumURI+"> .\n";
            }
        }
        if(!phylumURI.isEmpty()){
            retTriples+= "<"+phylumURI+"> <"+Resources.rdfTypeLabel+"> <"+Resources.phylumLabel+"> .\n";
            if(!phylumName.isEmpty()){
                retTriples+= "<"+phylumURI+"> <"+Resources.rdfsLabel+"> \""+phylumName+"\" .\n";
            }
        }   
        if(!kingdomName.isEmpty()){
            if(!phylumURI.isEmpty()){
                retTriples+= "<"+phylumURI+"> <"+Resources.belongsTo+"> <"+kingdomURI+"> .\n";
            }
            retTriples+= "<"+kingdomURI+"> <"+Resources.rdfTypeLabel+"> <"+Resources.kingdomLabel+"> .\n";
            retTriples+= "<"+kingdomURI+"> <"+Resources.rdfsLabel+"> \""+kingdomName+"\" .\n";
        }
        if(!datasetURI.isEmpty()){
            retTriples+= "<"+datasetURI+"> <"+Resources.rdfTypeLabel+"> <"+Resources.datasetLabel+"> .\n";
            if(!datasetName.isEmpty()){
                retTriples+= "<"+datasetURI+"> <"+Resources.rdfsLabel+"> \""+datasetName+"\" .\n";
            }
        }
      
        logger.debug("Struct in NTriples format: \n"+retTriples);
        return retTriples;
    }
    
    
    /**Validates the fields that will contain URIs
     * 
     * @throws URIValidationException if any of the URIs is not in valid form */
    public void validateURIs() throws URIValidationException{

        if(!this.phylumURI.isEmpty()){
            this.validateURI(this.phylumURI);
        }
        if(!this.speciesURI.isEmpty()){
            this.validateURI(this.speciesURI);
        }
        if(!this.genusURI.isEmpty()){
            this.validateURI(this.genusURI);
        }
        if(!this.familyURI.isEmpty()){
            this.validateURI(this.familyURI);
        }
        if(!this.orderURI.isEmpty()){
            this.validateURI(this.orderURI);
        }
        if(!this.classURI.isEmpty()){
            this.validateURI(this.classURI);
        }
        if(!this.kingdomURI.isEmpty()){
            this.validateURI(this.kingdomURI);
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
        return "DatasetURI: "+datasetURI+"\t"
              +"DatasetName: "+datasetName+"\t"
              +"SpeciesURI: "+speciesURI+"\t"
              +"SpeciesName: "+speciesName+"\t"
              +"PhylumURI: "+phylumURI+"\t"
              +"PhylumName: "+phylumName+"\t"
              +"GenusURI: "+genusURI+"\t"
              +"GenusName: "+genusName+"\t"
              +"OrderURI: "+orderURI+"\t"
              +"OrderName: "+orderName+"\t"
              +"FamilyURI: "+familyURI+"\t"
              +"FamilyName: "+familyName+"\t"
              +"ClassURI: "+classURI+"\t"
              +"ClassName: "+className+"\t"
              +"KingdomURI: "+kingdomURI+"\t"
              +"KingdomName: "+kingdomName;       
    }
    
    @Override
    public boolean equals(Object anotherObject){
        if(anotherObject instanceof TaxonomyStruct){
            TaxonomyStruct anotherStruct=(TaxonomyStruct)anotherObject;
            return this.speciesName.equals(anotherStruct.getSpeciesName()) &&
                   this.speciesURI.equals(anotherStruct.getSpeciesURI()) &&
                   this.genusName.equals(anotherStruct.getGenusName()) &&
                   this.genusURI.equals(anotherStruct.getGenusURI()) &&
                   
                  this.datasetName.equals(anotherStruct.getDatasetName()) &&
                   this.datasetURI.equals(anotherStruct.getDatasetURI()) &&
                    
                     this.orderName.equals(anotherStruct.getOrderName()) &&
                   this.orderURI.equals(anotherStruct.getOrderURI()) &&
                    
                     this.className.equals(anotherStruct.getClassName()) &&
                   this.classURI.equals(anotherStruct.getClassURI()) &&
                    
                     this.kingdomName.equals(anotherStruct.getKingdomName()) &&
                   this.kingdomURI.equals(anotherStruct.getKingdomURI()) &&
                    
                     this.familyName.equals(anotherStruct.getFamilyName()) &&
                   this.familyURI.equals(anotherStruct.getFamilyURI()) &&
                    
                    
                    this.phylumName.equals(anotherStruct.getPhylumName()) &&
                   this.phylumURI.equals(anotherStruct.getPhylumURI()) &&
                    
         
                   this.speciesURI.equals(anotherStruct.getSpeciesURI()) &&
                   this.speciesName.equals(anotherStruct.getSpeciesName());
                   
        }
        else{
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.speciesURI);
        return hash;
    }
}