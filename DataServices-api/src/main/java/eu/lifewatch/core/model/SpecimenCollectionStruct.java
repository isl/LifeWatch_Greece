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
public class SpecimenCollectionStruct {
    private String collectionName;
    private String collectionURI;
    private String datasetName;
    private String datasetURI;
    private String keeperName;
    private String keeperURI;
//  private String accessRights;
//  private String accessRightsURI;
//  private String rightsHolder;
//  private String rightsHolderURI;
    private String curatorURI;
    private String curatorName;
    private String ownerName;
    private String ownerURI;
    private String note;
    private String timespan;
    private String creationEventURI;
    private String creationEvent;
    private String creatorName;
    private String creatorURI;
    private String contactPoint;
  
    private static final Logger logger=Logger.getLogger(SpecimenCollectionStruct.class);
    
    /**Creates a new instance by initializing 
     * all the fields to be empty (Null values will cause NPE issues)*/
    
    /**Creates a new instance by initializing 
     * all the fields to be empty (Null values will cause NPE issues)*/
    public SpecimenCollectionStruct(){
        datasetName="";
        datasetURI="";
        keeperName="";
        keeperURI="";
//        accessRights="";
//        rightsHolderURI="";
//        rightsHolder="";
        curatorURI="";
        curatorName="";
        creatorURI="";
        creationEventURI="";
        creationEvent="";
        creatorName="";
        timespan="";
        ownerName="";
        ownerURI="";
        note="";
        contactPoint="";
//        accessRightsURI="";
    }
    
    public String getDatasetName() {
        return datasetName;
    }
    
    public String getDatasetURI() {
        return datasetURI;
    }
    
    public String getCreationEventURI() {
        return creationEventURI;
    }
   
    public String getCreationEvent() {
        return creationEvent;
    }
     
    public String getKeeperName() {
        return keeperName;
    }
    
    public String getKeeperURI() {
        return keeperURI;
    }

    public String getCollectionName() {
        return collectionName;
    }
    
    public String getCollectionURI() {
        return collectionURI;
    }
    
//    public String getAccessRights() {
//        return accessRights;
//    }
//    
//    public String getAccessRightsURI() {
//        return accessRightsURI;
//    }
    
    public String getCreatorName() {
        return creatorName;
    }
    
    public String getCreatorURI() {
        return creatorURI;
    }

    public String getTimespan() {
        return timespan;
    }
    
//    public String getRightsHolderURI() {
//        return rightsHolderURI;
//    }
//    
//    public String getRightsHolder() {
//        return rightsHolder;
//    }
//    
    public String getCuratorURI() {
        return curatorURI;
    }
    
    public String getCuratorName() {
        return curatorName;
    }
    
    public String getOwnerURI() {
        return ownerURI;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public String getNote() {
        return note;
    }

    public String getContactPoint() {
        return contactPoint;
    }
    
    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public void setCollectionURI(String collectionURI) {
        this.collectionURI = collectionURI;
    }

    public void setDatasetName(String datasetName) {
        this.datasetName = datasetName;
    }

    public void setDatasetURI(String datasetURI) {
        this.datasetURI = datasetURI;
    }

    public void setKeeperName(String keeperName) {
        this.keeperName = keeperName;
    }

    public void setKeeperURI(String keeperURI) {
        this.keeperURI = keeperURI;
    }

//    public void setAccessRights(String accessRights) {
//        this.accessRights = accessRights;
//    }
//
//    public void setAccessRightsURI(String accessRightsURI) {
//        this.accessRightsURI = accessRightsURI;
//    }
//
//    public void setRightsHolder(String rightsHolder) {
//        this.rightsHolder = rightsHolder;
//    }
//
//    public void setRightsHolderURI(String rightsHolderURI) {
//        this.rightsHolderURI = rightsHolderURI;
//    }

    public void setCuratorURI(String curatorURI) {
        this.curatorURI = curatorURI;
    }

    public void setCuratorName(String curatorName) {
        this.curatorName = curatorName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public void setOwnerURI(String ownerURI) {
        this.ownerURI = ownerURI;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setTimespan(String timespan) {
        this.timespan = timespan;
    }

    public void setCreationEventURI(String creationEventURI) {
        this.creationEventURI = creationEventURI;
    }

    public void setCreationEvent(String creationEvent) {
        this.creationEvent = creationEvent;
    }
    
    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public void setCreatorURI(String creatorURI) {
        this.creatorURI = creatorURI;
    }

    public void setContactPoint(String contactPoint) {
        this.contactPoint = contactPoint;
    }

    public SpecimenCollectionStruct withDatasetURI(String datasetURI) {
        this.datasetURI = datasetURI;
        return this;
    }

    public SpecimenCollectionStruct withCreationEventURI(String creationEventURI) {
        this.creationEventURI = creationEventURI;
        return this;
    }
    
    public SpecimenCollectionStruct withCreationEvent(String creationEvent) {
        this.creationEvent = creationEvent;
        return this;
    }
    
    public SpecimenCollectionStruct withDatasetName(String datasetName) {
        this.datasetName = datasetName;
        return this;
    }

    public SpecimenCollectionStruct withKeeperName(String keeperName) {
        this.keeperName = keeperName;
        return this;
    }

    public SpecimenCollectionStruct withKeeperURI(String keeperURI) {
        this.keeperURI = keeperURI;
        return this;
    }

    public SpecimenCollectionStruct withCreatorURI(String creatorURI) {
        this.creatorURI = creatorURI;
        return this;
    }

    public SpecimenCollectionStruct withCreatorName(String creatorName) {
        this.creatorName = creatorName;
        return this;
    }

    public SpecimenCollectionStruct withTimespan(String timespan) {
        this.timespan = timespan;
        return this;
    }
//    public SpecimenCollectionStruct withAccessRights(String accessRights) {
//        this.accessRights = accessRights;
//        return this;
//    }
//    
//    public SpecimenCollectionStruct withAccessRightsURI(String accessRightsURI) {
//        this.accessRightsURI = accessRightsURI;
//        return this;
//    }
//
//    public SpecimenCollectionStruct withRightsHolderURI(String rightsHolderURI) {
//        this.rightsHolderURI = rightsHolderURI;
//        return this;
//    }
//
//    public SpecimenCollectionStruct withRightsHolder(String rightsHolder) {
//        this.rightsHolder = rightsHolder;
//        return this;
//    }
    
    public SpecimenCollectionStruct withCuratorURI(String curatorURI) {
        this.curatorURI = curatorURI;
        return this;
    }

    public SpecimenCollectionStruct withCuratorName(String curatorName) {
        this.curatorName = curatorName;
        return this;
    }

    public SpecimenCollectionStruct withCollectionURI(String collectionURI) {
        this.collectionURI = collectionURI;
        return this;
    }

    public SpecimenCollectionStruct withCollectionName(String collectionName) {
        this.collectionName = collectionName;
        return this;
    }
    
    public SpecimenCollectionStruct withOwnerName(String ownerName) {
        this.ownerName = ownerName;
        return this;
    }
    
    public SpecimenCollectionStruct withOwnerURI(String ownerURI) {
        this.ownerURI = ownerURI;
        return this;
    }

    public SpecimenCollectionStruct withNote(String note) {
        this.note = note;
        return this;
    }

    public SpecimenCollectionStruct withContactPoint(String contactPoint) {
        this.contactPoint = contactPoint;
        return this;
    }

    /**Produces an NTRIPLES output so that it can be used to SPARQL queries
     * 
     * @return an NTRIPLES representation of the struct fields */
    public String toNtriples(){
        String retTriples="";
        if(!collectionURI.isEmpty()){
            retTriples+= "<"+collectionURI+"> <"+Resources.rdfTypeLabel+"> <"+Resources.collectionLabel+"> .\n";
            if(!collectionName.isEmpty()){
                retTriples+= "<"+collectionURI+"> <"+Resources.rdfsLabel+"> \""+collectionName+"\" .\n";
            }
            if(!ownerURI.isEmpty()){
                retTriples+= "<"+collectionURI+"> <"+Resources.hasCurrentOwner+"> <"+ownerURI+"> .\n";
            }
            if(!creationEventURI.isEmpty()){
                retTriples+= "<"+collectionURI+"> <"+Resources.wasCreatedBy+"> <"+creationEventURI+"> .\n";
            }
            if(!curatorURI.isEmpty()){
                retTriples+= "<"+collectionURI+"> <"+Resources.hasCurator+"> <"+curatorURI+"> .\n";
            }
            if(!keeperURI.isEmpty()){
                retTriples+= "<"+collectionURI+"> <"+Resources.hasCurrentKeeper+"> <"+keeperURI+"> .\n";
            }
            if(!datasetURI.isEmpty()){
                retTriples+= "<"+datasetURI+"> <"+Resources.refersTo+"> <"+collectionURI+"> .\n";
            }
//            if(!accessRightsURI.isEmpty()){
//                retTriples+= "<"+collectionURI+"> <"+Resources.isSubjectTo+"> <"+accessRightsURI+"> .\n";
//            }
            if(!note.isEmpty()){
                retTriples+= "<"+collectionURI+"> <"+Resources.hasNote+"> \""+note+"\" .\n";
            }
        }
        if(!ownerURI.isEmpty()){
            retTriples+= "<"+ownerURI+"> <"+Resources.rdfTypeLabel+"> <"+Resources.actorLabel+"> .\n";
            if(!ownerName.isEmpty()){
                retTriples+= "<"+ownerURI+"> <"+Resources.rdfsLabel+"> \""+ownerName+"\" .\n";
            }
        }
//        if(!accessRightsURI.isEmpty()){
//            retTriples+= "<"+accessRightsURI+"> <"+Resources.rdfTypeLabel+"> <"+Resources.accessRightsLabel+"> .\n";
//            if(!accessRights.isEmpty()){
//                retTriples+= "<"+accessRightsURI+"> <"+Resources.isIdentifiedBy+"> \""+accessRights+"\" .\n";
//            }
//            if(!rightsHolderURI.isEmpty()){
//                retTriples+= "<"+accessRightsURI+"> <"+Resources.rightsHeldBy+"> <"+rightsHolderURI+"> . \n";
//            }
//        }
//        if(!rightsHolderURI.isEmpty()){
//            retTriples+= "<"+rightsHolderURI+"> <"+Resources.rdfTypeLabel+"> <"+Resources.actorLabel+"> .\n";
//            if(!rightsHolder.isEmpty()){
//                retTriples+= "<"+rightsHolderURI+"> <"+Resources.isIdentifiedBy+"> \""+rightsHolder+"\" .\n";
//            }
//        }
        if(!creationEventURI.isEmpty()){
            retTriples+= "<"+creationEventURI+"> <"+Resources.rdfTypeLabel+"> <"+Resources.creationEventLabel+"> .\n";
            if(!creatorURI.isEmpty()){
                retTriples+= "<"+creationEventURI+"> <"+Resources.carriedOutBy+"> <"+creatorURI+"> .\n";
            }
            if(!timespan.isEmpty()){
                retTriples+= "<"+creationEventURI+"> <"+Resources.hasTimespan+"> \""+timespan+"\" .\n";
            }
            if(!creationEvent.isEmpty()){
                retTriples+= "<"+creationEventURI+"> <"+Resources.rdfsLabel+"> \""+creationEvent+"\" .\n";
            }
        }
        if(!creatorURI.isEmpty()){
            retTriples+= "<"+creatorURI+"> <"+Resources.rdfTypeLabel+"> <"+Resources.actorLabel+"> .\n";
            if(!creatorName.isEmpty()){
                retTriples+= "<"+creatorURI+"> <"+Resources.rdfsLabel+"> \""+creatorName+"\" .\n";
            }
        }
        if(!datasetURI.isEmpty()){
            retTriples+= "<"+datasetURI+"> <"+Resources.rdfTypeLabel+"> <"+Resources.datasetLabel+"> .\n";
            if(!datasetName.isEmpty()){
                retTriples+= "<"+datasetURI+"> <"+Resources.rdfsLabel+"> \""+datasetName+"\" .\n";
            }
        }
        if(!curatorURI.isEmpty()){
            retTriples+= "<"+curatorURI+"> <"+Resources.rdfTypeLabel+"> <"+Resources.actorLabel+"> .\n";
            if(!curatorName.isEmpty()){
                retTriples+= "<"+curatorURI+"> <"+Resources.rdfsLabel+"> \""+curatorName+"\" .\n";
            }
            if(!contactPoint.isEmpty()){
                retTriples+= "<"+curatorURI+"> <"+Resources.hasContactPoint+"> \""+contactPoint+"\" .\n";
            }
        }
        if(!keeperURI.isEmpty()){
            retTriples+= "<"+keeperURI+"> <"+Resources.rdfTypeLabel+"> <"+Resources.actorLabel+"> .\n";
            if(!keeperName.isEmpty()){
                retTriples+= "<"+keeperURI+"> <"+Resources.rdfsLabel+"> \""+keeperName+"\".";
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

        if(!this.keeperURI.isEmpty()){
            this.validateURI(this.keeperURI);
        }
        if(!this.curatorURI.isEmpty()){
            this.validateURI(this.curatorURI);
        }
        if(!this.ownerURI.isEmpty()){
            this.validateURI(this.ownerURI);
        }
        if(!this.creatorURI.isEmpty()){
            this.validateURI(this.creatorURI);
        }
//        if(!this.rightsHolderURI.isEmpty()){
//            this.validateURI(this.rightsHolderURI);
//        }
  
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
              +"KeeperURI: "+keeperURI+"\t"
              +"KeeperName: "+keeperName+"\t"
//              +"AccessRightsURI: "+accessRightsURI+"\t"
//              +"AccessRights: "+accessRights+"\t"
//              +"RightsHolderURI: "+rightsHolderURI+"\t"
//              +"RightsHolder: "+rightsHolder+"\t"
              +"CuratorURI: "+curatorURI+"\t"
              +"CuratorName: "+curatorName+"\t"
              +"CreatorURI: "+creatorURI+"\t"
              +"CreatorName: "+creatorName+"\t"
              +"CreationEventURI: "+creationEventURI+"\t"
              +"Timespan: "+timespan+"\t"
              +"OwnerURI: "+ownerURI+"\t"
              +"OwnerName: "+ownerName+"\t"
              +"Note: "+note+"\t"
              +"ContactPoint: "+contactPoint;
    }
    
    @Override
    public boolean equals(Object anotherObject){
        if(anotherObject instanceof SpecimenCollectionStruct){
            SpecimenCollectionStruct anotherStruct=(SpecimenCollectionStruct)anotherObject;
            return this.datasetName.equals(anotherStruct.getDatasetName()) &&
                   this.datasetURI.equals(anotherStruct.getDatasetURI()) &&
                   this.keeperURI.equals(anotherStruct.getKeeperURI()) &&
                   this.keeperName.equals(anotherStruct.getKeeperName()) &&
                   this.curatorURI.equals(anotherStruct.getCuratorURI()) &&
                   this.curatorName.equals(anotherStruct.getCuratorName()) &&
                   this.ownerURI.equals(anotherStruct.getOwnerURI()) &&
                   this.ownerName.equals(anotherStruct.getOwnerName()) &&
               
                   this.creatorName.equals(anotherStruct.getCreatorName()) &&
                   this.creatorURI.equals(anotherStruct.getCreatorURI()) &&
//                   this.accessRights.equals(anotherStruct.getAccessRights()) &&
//                   this.accessRightsURI.equals(anotherStruct.getAccessRightsURI()) &&
//                   this.rightsHolder.equals(anotherStruct.getRightsHolder())&&
//                   this.rightsHolderURI.equals(anotherStruct.getRightsHolderURI()) &&
                    
                   this.note.equals(anotherStruct.getNote()) &&
                
                   this.contactPoint.equals(anotherStruct.getContactPoint());
                 
        }else{
            return false;
        }
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.collectionURI);
        return hash;
    }
}