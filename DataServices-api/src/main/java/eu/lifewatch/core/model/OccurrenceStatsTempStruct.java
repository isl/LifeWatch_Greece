package eu.lifewatch.core.model;

import eu.lifewatch.common.Resources;
import eu.lifewatch.exception.URIValidationException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
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
public class OccurrenceStatsTempStruct {
    private String occurrenceEventURI;
    private String occurrenceEvent;
    private String datasetURI;
    private String datasetTitle;
    private String physicalObjectURI;
    private String physicalObject;
    private List<Pair> actors;
    private String timeSpan;
    private String localityName;
    private String localityURI;
    private String countryName;
    private String countryURI;
    private String waterAreaName;
    private String waterAreaURI;
    private String habitatName;
    private String habitatURI;
    private String ecosystemName;
    private String ecosystemURI;
    private String equipmentTypeName;
    private String equipmentTypeURI;
    private String latitude;
    private String longitude;
    private String maximumDepth;
    private String minimumDepth;
    private String samplingProtocolName;
    private String samplingProtocolURI;
    private String geodeticDatum;
    private String bibliographicCitation;
    private String temporaryAggregate;
    private String temporaryAggregateURI;
    private String speciesURI;
    private String speciesName;
    private String bibliographicCitationURI;
    private String description;
    private String stationURI;
    private String stationNotes;
    private String coordinates;
    private String numberOfParts;
    
    
    private static final Logger logger=Logger.getLogger(OccurrenceStatsTempStruct.class);
    
    /**Creates a new instance by initializing 
     * all the fields to be empty (Null values will cause NPE issues)*/
    public OccurrenceStatsTempStruct(){
        occurrenceEventURI="";
        occurrenceEvent="";
        datasetURI="";
        datasetTitle="";
        physicalObjectURI="";
        physicalObject="";
        actors=new ArrayList<>();
        timeSpan="";
        localityName="";
        localityURI="";
        countryName="";
        countryURI="";
        waterAreaName="";
        waterAreaURI="";
        habitatName="";
        habitatURI="";
        ecosystemName="";
        ecosystemURI="";
        equipmentTypeName="";
        equipmentTypeURI="";
        latitude="";
        longitude="";
        maximumDepth="";
        minimumDepth="";
        samplingProtocolName="";
        samplingProtocolURI="";
        geodeticDatum="";
        bibliographicCitation="";    
        temporaryAggregateURI="";
        speciesURI="";
        speciesName="";
        bibliographicCitationURI="";
        description="";
        stationURI="";
        stationNotes="";
        coordinates="";
        numberOfParts="";
    }
    
    public String getOccurrenceEventURI() {
        return occurrenceEventURI;
    }
    
    public String getOccurrenceEvent() {
        return occurrenceEvent;
    }
    
    public String getDatasetURI() {
        return datasetURI;
    }
     
    public String getDatasetTitle() {
        return datasetTitle;
    }
      
    public String getPhysicalObjectURI() {
        return physicalObjectURI;
    }
     
    public String getPhysicalObject() {
        return physicalObject;
    }

//      public List<HashMap<String,String>> getTemporaryAggregates() {
//        return temporaryAggregates;
//    }
//         
//    
//    public Collection<String> getTemporaryAggregateURIs(){
//        Collection<String> tempAggregates=new HashSet<>();
//        for(HashMap<String,String> map : this.temporaryAggregates){
//            tempAggregates.add(map.get(TEMP_AGGR));
//        }
//        return tempAggregates;
//    } 
//            
//    public Collection<String> getNumberOfPartsFromTemporaryAggregates() {
//        Collection<String> numberOfParts=new HashSet<>();
//        for(HashMap<String,String> map : this.temporaryAggregates){
//            numberOfParts.add(map.get(NUMBER_OF_PARTS));
//        }
//        return numberOfParts;
//    }
    
    
    public String getTemporaryAggregate() {
        return temporaryAggregate;
    }
         
    
    public String getTemporaryAggregateURI(){  
        return temporaryAggregateURI;
    } 
            
    public String getNumberOfParts() {
        return numberOfParts;
    }
     
    public Collection<String> getActorURIs() {
        Collection<String> actorURIs=new HashSet<>();
        for(Pair pair : this.actors){
            actorURIs.add(pair.getKey());
        }
        return actorURIs;
    }
    
    public Collection<String> getActorName() {
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
//       public Collection<String> getSpeciesURIsFromTemporaryAggregates() {
//        Collection<String> speciesURIs=new HashSet<>();
//        for(HashMap<String,String> map : this.temporaryAggregates){
//            speciesURIs.add(map.get(SPECIES_URI));
//        }
//        return speciesURIs;
//    }
//    
//    public Collection<String> getSpeciesNameFromTemporaryAggregates() {
//        Collection<String> speciesNames=new HashSet<>();
//        for(HashMap<String,String> map : this.temporaryAggregates){
//            speciesNames.add(map.get(SPECIES_NAME));
//        }
//        return speciesNames;
//    }
//    
    
    public String getTimeSpan() {
        return timeSpan;
    }
    
    public String getLocalityName() {
        return localityName;
    }
    
    public String getLocalityURI() {
        return localityURI;
    }
    
    public String getCountryName() {
        return  countryName;
    }
    
    public String getCountryURI() {
        return  countryURI;
    }
    
    public String getWaterAreaName() {
        return waterAreaName;
    }
   
    public String getWaterAreaURI() {
        return waterAreaURI;
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
     
    public String getEquipmentTypeName() {
        return equipmentTypeName;
    }
    
    public String getEquipmentTypeURI() {
        return equipmentTypeURI;
    }
     
    public String getLatitude() {
        return latitude;
    }
    
    public String getLongitude() {
        return longitude;
    }
    
    public String getMaximumDepth() {
        return maximumDepth;
    }
    
    public String getMinimumDepth() {
        return minimumDepth;
    }
    
    public String getSamplingProtocolName() {
        return samplingProtocolName;
    }
    
    public String getSamplingProtocolURI() {
        return samplingProtocolURI;
    }
    
    
    public String getBibliographicCitation() {
        return bibliographicCitation;
    }
    

    public String getBibliographicCitationURI() {
        return bibliographicCitationURI;
    }
    
    
    
      public String getDescription() {
        return description;
    }
    
    
    public String getStationURI() {
        return stationURI;
    }
    

    public String getStationNotes() {
        return stationNotes;
    }
    
    public String getCoordinates() {
        return coordinates;
    }

    public void setOccurrenceEventURI(String occurrenceEventURI) {
        this.occurrenceEventURI = occurrenceEventURI;
    }
    
    public void setOccurrenceEvent(String occurrenceEvent) {
        this.occurrenceEvent = occurrenceEvent;
    }

    public void setDatasetURI(String datasetURI) {
        this.datasetURI = datasetURI;
    }

    public void setDatasetTitle(String datasetTitle) {
        this.datasetTitle = datasetTitle;
    }

    public void setPhysicalObjectURI(String physicalObjectURI) {
        this.physicalObjectURI = physicalObjectURI;
    }

    public void setPhysicalObject(String physicalObject) {
        this.physicalObject = physicalObject;
    }
    
    public void setActors(List<Pair> actors) {
        this.actors = actors;
    }

    public void setTimeSpan(String timeSpan) {
        this.timeSpan = timeSpan;
    }

    public void setLocalityName(String localityName) {
        this.localityName = localityName;
    }

    public void setLocalityURI(String localityURI) {
        this.localityURI = localityURI;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public void setCountryURI(String countryURI) {
        this.countryURI = countryURI;
    }

    public void setWaterAreaName(String waterAreaName) {
        this.waterAreaName = waterAreaName;
    }

    public void setWaterAreaURI(String waterAreaURI) {
        this.waterAreaURI = waterAreaURI;
    }

    public void setHabitatName(String habitatName) {
        this.habitatName = habitatName;
    }

    public void setHabitatURI(String habitatURI) {
        this.habitatURI = habitatURI;
    }

    public void setEcosystemName(String ecosystemName) {
        this.ecosystemName = ecosystemName;
    }

    public void setEcosystemURI(String ecosystemURI) {
        this.ecosystemURI = ecosystemURI;
    }

    public void setEquipmentTypeName(String equipmentTypeName) {
        this.equipmentTypeName = equipmentTypeName;
    }

    public void setEquipmentTypeURI(String equipmentTypeURI) {
        this.equipmentTypeURI = equipmentTypeURI;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setMaximumDepth(String maximumDepth) {
        this.maximumDepth = maximumDepth;
    }

    public void setMinimumDepth(String minimumDepth) {
        this.minimumDepth = minimumDepth;
    }

    public void setSamplingProtocolName(String samplingProtocolName) {
        this.samplingProtocolName = samplingProtocolName;
    }

    public void setSamplingProtocolURI(String samplingProtocolURI) {
        this.samplingProtocolURI = samplingProtocolURI;
    }

    public void setGeodeticDatum(String geodeticDatum) {
        this.geodeticDatum = geodeticDatum;
    }

    public void setBibliographicCitation(String bibliographicCitation) {
        this.bibliographicCitation = bibliographicCitation;
    }
    
    public void setTemporaryAggregate(String temporaryAggregate) {
        this.temporaryAggregate = temporaryAggregate;
    }
    
    public void setTemporaryAggregateURI(String temporaryAggregateURI) {
        this.temporaryAggregateURI = temporaryAggregateURI;
    }
    
    public void setSpeciesURI(String speciesURI) {
        this.speciesURI = speciesURI;
    }
    
    public void setSpeciesName(String speciesName) {
        this.speciesName= speciesName;
    }
    
    public void bibliographicCitationURI(String bibliographicCitationURI){
        this.bibliographicCitationURI = bibliographicCitationURI;
    }
    
    public void setDescription(String desription) {
        this.description= description;
    }
     
    public void stationNotes(String stationNotes) {
        this.stationNotes = stationNotes;
    }
    
    public void setStationURI(String stationURI) {
        this.stationURI= stationURI;
    }
     
    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }
    
    public void setNumberOfParts(String numberOfParts) {
        this.numberOfParts= numberOfParts;
    }

    public OccurrenceStatsTempStruct withOccurrenceEventURI(String occurrenceEventURI) {
        this.occurrenceEventURI = occurrenceEventURI;
        return this;
    }
    
    public OccurrenceStatsTempStruct withOccurrenceEvent(String occurrenceEvent) {
        this.occurrenceEvent = occurrenceEvent;
        return this;
    }

    public OccurrenceStatsTempStruct withDatasetTitle(String datasetTitle) {
        this.datasetTitle = datasetTitle;
        return this;
    }
    
    public OccurrenceStatsTempStruct withDatasetURI(String datasetURI) {
        this.datasetURI = datasetURI;
        return this;
    }
    
    public OccurrenceStatsTempStruct withPhysicalObjectURI(String physicalObjectURI) {
        this.physicalObjectURI = physicalObjectURI;
        return this;
    }
    
     public OccurrenceStatsTempStruct withPhysicalObject(String physicalObject) {
        this.physicalObject = physicalObject;
        return this;
    }
    
//    public OccurrenceStatsTempStruct withTemporaryAggregate(String temporAggregateURI, String speciesURI, String speciesName, String numberOfParts) {
//        if(!this.getTemporaryAggregateURIs().contains(temporAggregateURI)){
//            HashMap<String,String> map=new HashMap<>();
//            map.put(TEMP_AGGR,temporAggregateURI);
//            map.put(SPECIES_URI,speciesURI);
//            map.put(SPECIES_NAME,speciesName);
//            map.put(NUMBER_OF_PARTS,numberOfParts);
//            this.temporaryAggregates.add(map);
//        }
//        return this;
//    }
//    
//    public OccurrenceStatsTempStruct withTemporaryAggregate(String temporAggregateURI, String speciesURI, String speciesName) {
//        if(!this.getTemporaryAggregateURIs().contains(temporAggregateURI)){
//            HashMap<String,String> map=new HashMap<>();
//            map.put(TEMP_AGGR,temporAggregateURI);
//            map.put(SPECIES_URI,speciesURI);
//            map.put(SPECIES_NAME,speciesName);
//            this.temporaryAggregates.add(map);
//        }
//        return this;
//    }
//    
//    public OccurrenceStatsTempStruct withNumberOfParts(String temporAggregateURI, String numberOfParts) {
//        for(HashMap<String,String> map : this.temporaryAggregates){
//            if(map.get(temporAggregateURI).equals(temporAggregateURI)){
//                map.put(NUMBER_OF_PARTS,numberOfParts);
//                this.temporaryAggregates.add(map);
//            }
//        }
//        return this;
//    }
//    
    
    
     public OccurrenceStatsTempStruct withTemporaryAggregateURI(String temporaryAggregateURI) {
        
            this.temporaryAggregateURI = temporaryAggregateURI;
 
        return this;
    }
    
        public OccurrenceStatsTempStruct withTemporaryAggregate(String temporaryAggregate) {
        
            this.temporaryAggregate = temporaryAggregate;
 
        return this;
    }
        
   
    public OccurrenceStatsTempStruct withActor(String actorURI, String actorName) {
        if(!this.getActorURIs().contains(actorURI)){
            this.actors.add(new Pair(actorURI,actorName));
        }
        return this;
    }
    
    public OccurrenceStatsTempStruct withTimeSpan(String timeSpan) {
        this.timeSpan = timeSpan;
        return this;
    }

    public OccurrenceStatsTempStruct withLocalityURI(String localityURI) {
        this.localityURI = localityURI;
        return this;
    }

    
    public OccurrenceStatsTempStruct withLocalityName(String localityName) {
        this.localityName= localityName;
        return this;
    }
   
    public OccurrenceStatsTempStruct withCountryURI(String countryURI) {
        this.countryURI = countryURI;
        return this;
    }

    public OccurrenceStatsTempStruct withCountryName(String countryName) {
        this.countryName= countryName;
        return this;
    } 
     
    public OccurrenceStatsTempStruct withWaterAreaURI(String waterAreaURI) {
        this.waterAreaURI = waterAreaURI;
        return this;
    }
    
    public OccurrenceStatsTempStruct withWaterAreaName(String waterAreaName) {
        this.waterAreaName= waterAreaName;
        return this;
    } 
     
    public OccurrenceStatsTempStruct withHabitatURI(String habitatURI) {
        this.habitatURI = habitatURI;
        return this;
    }

    public OccurrenceStatsTempStruct withHabitatName(String habitatName) {
        this.habitatName= habitatName;
        return this;
    } 
     
     
    public OccurrenceStatsTempStruct withEcosystemURI(String ecosystemURI) {
        this.ecosystemURI = ecosystemURI;
        return this;
    }

    public OccurrenceStatsTempStruct withEcosystemName(String ecosystemName) {
        this.ecosystemName= ecosystemName;
        return this;
    } 
     
    public OccurrenceStatsTempStruct withEquipmentTypeURI(String equipmentTypeURI) {
        this.equipmentTypeURI = equipmentTypeURI;
        return this;
    }

    public OccurrenceStatsTempStruct withEquipmentTypeName(String equipmentTypeName) {
        this.equipmentTypeName= equipmentTypeName;
        return this;
    } 
     
    public OccurrenceStatsTempStruct withSamplingProtocolURI(String samplingProtocolURI) {
        this.samplingProtocolURI = samplingProtocolURI;
        return this;
    }
    
    public OccurrenceStatsTempStruct withSamplingProtocolName(String samplingProtocolName) {
        this.samplingProtocolName = samplingProtocolName;
        return this;
    }
     
    public OccurrenceStatsTempStruct withLatitude(String latitude) {
        this.latitude = latitude;
        return this;
    }

    public OccurrenceStatsTempStruct withLongitude(String longitude) {
        this.longitude = longitude;
        return this;
    }

    public OccurrenceStatsTempStruct withMaximumDepth(String maximumDepth) {
        this.maximumDepth = maximumDepth;
        return this;
    }
     
    public OccurrenceStatsTempStruct withMinimumDepth(String minimumDepth) {
        this.minimumDepth = minimumDepth;
        return this;
    }
     
    public OccurrenceStatsTempStruct withGeodeticDatum(String geodeticDatum) {
        this.geodeticDatum = geodeticDatum;
        return this;
    }
                    
    public OccurrenceStatsTempStruct withBibliographicCitation(String bibliographicCitation) {
        this.bibliographicCitation= bibliographicCitation;
        return this;
    }
    
    
    
    public OccurrenceStatsTempStruct withSpeciesURI(String speciesURI) {
        this.speciesURI = speciesURI;
        return this;
    }
     
    public OccurrenceStatsTempStruct withSpeciesName(String speciesName) {
        this.speciesName = speciesName;
        return this;
    }
     
    public OccurrenceStatsTempStruct withBibliographicCitationURI(String bibliographicCitationURI) {
        this.bibliographicCitationURI = bibliographicCitationURI;
        return this;
    }
         
    public OccurrenceStatsTempStruct withDescription(String description) {
        this.description= description;
        return this;
    }
    
    
    public OccurrenceStatsTempStruct withStationURI(String stationURI) {
        this.stationURI = stationURI;
        return this;
    }
     
    public OccurrenceStatsTempStruct withStationNotes(String stationNotes) {
        this.stationNotes = stationNotes;
        return this;
    }
     
    public OccurrenceStatsTempStruct withCoordinates(String coordinates) {
        this.coordinates = coordinates;
        return this;
    }
         
    
     public OccurrenceStatsTempStruct withNumberOfParts(String numberOfParts) {
        this.numberOfParts = numberOfParts;
        return this;
    }
   
    
    public String toNtriples() {
        String retTriples = "";
        if (!occurrenceEventURI.isEmpty()) {
            retTriples += "<" + occurrenceEventURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.encounterEventLabel + "> .\n";
            if (!timeSpan.isEmpty()) {
                retTriples += "<" + occurrenceEventURI + "> <" + Resources.hasTimespan + "> \"" + timeSpan + "\" .\n";
            }
            if (!stationURI.isEmpty()) {
                retTriples += "<" + occurrenceEventURI + "> <" + Resources.hasFoundAt + "> <" + stationURI + "> .\n";
            }
//            if (!localityURI.isEmpty()) {
//                retTriples += "<" + occurrenceEventURI + "> <" + Resources.hasFoundAt + "> <" + localityURI + "> .\n";
//            }
            if (!equipmentTypeURI.isEmpty()) {
                retTriples += "<" + occurrenceEventURI + "> <" + Resources.usedObjectOfType + "> <" + equipmentTypeURI + "> .\n";
            }

            if (!samplingProtocolURI.isEmpty()) {
                retTriples += "<" + occurrenceEventURI + "> <" + Resources.usedSpecificTechnique + "> <" + samplingProtocolURI + "> .\n";
            }

            if (!bibliographicCitationURI.isEmpty()) {
                retTriples += "<" + occurrenceEventURI + "> <" + Resources.isReferredToBy + "> <" + bibliographicCitationURI + "> .\n";
            }

            for (Pair pair : this.actors) {
                retTriples += "<" + occurrenceEventURI + "> <" + Resources.carriedOutBy + "> <" + pair.getKey() + "> .\n";
            }
            if(!physicalObjectURI.isEmpty()){
                retTriples+= "<"+occurrenceEventURI+"> <"+Resources.hasFoundObject+"> <"+physicalObjectURI+"> .\n";
            }
            if (!datasetURI.isEmpty()) {
                retTriples += "<" + datasetURI + "> <" + Resources.refersTo + "> <" + occurrenceEventURI + "> . \n";
            }
            if (!description.isEmpty()) {
                retTriples += "<" + occurrenceEventURI + "> <" + Resources.hasNote + "> \"" + description + "\" .\n";
            }
            
            if(!occurrenceEvent.isEmpty()){
                retTriples+= "<"+occurrenceEventURI+"> <"+Resources.rdfsLabel+"> \"" + occurrenceEvent + "\" .\n";
            }
        }
        if (!equipmentTypeURI.isEmpty()) {
            retTriples += "<" + equipmentTypeURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.equipmentTypeLabel + "> .\n";
            if (!equipmentTypeName.isEmpty()) {
                retTriples += "<" + equipmentTypeURI + "> <" + Resources.rdfsLabel + "> \"" + equipmentTypeName + "\" .\n";
            }
        }
        
        if(!physicalObjectURI.isEmpty()){
            retTriples+= "<"+physicalObjectURI+"> <"+Resources.rdfTypeLabel+"> <"+Resources.physicalObjectLabel+"> .\n";
          
            if(!temporaryAggregateURI.isEmpty()){
                retTriples+= "<"+physicalObjectURI+"> <"+Resources.isComposedOf+"> <"+temporaryAggregateURI+"> .\n";
            }
            if (!physicalObject.isEmpty()) {
                retTriples += "<" + physicalObjectURI + "> <" + Resources.rdfsLabel + "> \"" + physicalObject + "\" .\n";
            }
        }      

           if(!temporaryAggregateURI.isEmpty()){
            retTriples+= "<"+temporaryAggregateURI+"> <"+Resources.rdfTypeLabel+"> <"+Resources.temporaryAggregateLabel+"> .\n";
       
            if(!temporaryAggregate.isEmpty()){
                retTriples+= "<"+temporaryAggregateURI+"> <"+Resources.rdfsLabel+"> \"" + temporaryAggregate + "\" .\n";
            }
            if(!numberOfParts.isEmpty()){
                retTriples+= "<"+temporaryAggregateURI+"> <"+Resources.hasNumberOfParts+"> \"" + numberOfParts + "\" .\n";
            }
            if(!speciesURI.isEmpty()){
                retTriples+= "<"+temporaryAggregateURI+"> <"+Resources.belongsTo+"> <"+speciesURI+"> .\n";
            }
        }
        

        if (!samplingProtocolURI.isEmpty()) {
            retTriples += "<" + samplingProtocolURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.procedureLabel + "> .\n";
            if (!samplingProtocolName.isEmpty()) {
                retTriples += "<" + samplingProtocolURI + "> <" + Resources.rdfsLabel + "> \"" + samplingProtocolName + "\" .\n";
            }
        }

        if (!bibliographicCitationURI.isEmpty()) {
            retTriples += "<" + bibliographicCitationURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.conceptualObjectLabel + "> .\n";
            if (!bibliographicCitation.isEmpty()) {
                retTriples += "<" + bibliographicCitationURI + "> <" + Resources.rdfsLabel + "> \"" + bibliographicCitation + "\" .\n";
            }
        }

        if (!stationURI.isEmpty()) {
            retTriples += "<" + stationURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.placeLabel + "> .\n";

//            if (!latitude.isEmpty()&&!longitude.isEmpty()) {
//                retTriples += "<" + stationURI + "> <" + Resources.isIdentifiedBy + "> \"" + latitude + "," + longitude + "\" .\n";
//            }
            if (!latitude.isEmpty() && !longitude.isEmpty()) {
                retTriples += "<" + stationURI + "> <" + Resources.isIdentifiedBy + "> \"" + coordinates + "\" .\n";
            }

            if (!stationNotes.isEmpty()) {
                retTriples += "<" + stationURI + "> <" + Resources.hasNote + "> \"" + stationNotes + "\" .\n";
            }

            if (!localityURI.isEmpty()) {
                retTriples += "<" + stationURI + "> <" + Resources.fallsWithin + "> <" + localityURI + "> .\n";
            }

            if (!ecosystemURI.isEmpty()) {
                retTriples += "<" + stationURI  + "> <" + Resources.fallsWithin + "> <" + ecosystemURI + "> .\n";
            }
            if (!countryURI.isEmpty()) {
                retTriples += "<" + stationURI  + "> <" + Resources.fallsWithin + "> <" + countryURI + "> .\n";
            }
            if (!waterAreaURI.isEmpty()) {
                retTriples += "<" + stationURI  + "> <" + Resources.fallsWithin + "> <" + waterAreaURI + "> .\n";
            }
//            if (!habitatURI.isEmpty()) {
//                retTriples += "<" + stationURI + "> <" + Resources.hasType + "> <" + habitatURI + "> .\n";
//            }
//            if (!countryURI.isEmpty()) {
//                retTriples += "<" + localityURI + "> <" + Resources.fallsWithin + "> <" + countryURI + "> .\n";
//            }
//            if (!waterAreaURI.isEmpty()) {
//                retTriples += "<" + localityURI + "> <" + Resources.fallsWithin + "> <" + waterAreaURI + "> .\n";
//            }
//            if (!habitatURI.isEmpty()) {
//                retTriples += "<" + localityURI + "> <" + Resources.hasType + "> <" + habitatURI + "> .\n";
//            }
        }

        if (!localityURI.isEmpty()) {
            retTriples += "<" + localityURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.ecosystemEnvironmentLabel + "> .\n";
            if (!localityName.isEmpty()) {
                retTriples += "<" + localityURI + "> <" + Resources.rdfsLabel + "> \"" + localityName + "\" .\n";
            }
            if (!ecosystemURI.isEmpty()) {
                retTriples += "<" + localityURI + "> <" + Resources.fallsWithin + "> <" + ecosystemURI + "> .\n";
            }
            if (!countryURI.isEmpty()) {
                retTriples += "<" + localityURI + "> <" + Resources.fallsWithin + "> <" + countryURI + "> .\n";
            }
            if (!waterAreaURI.isEmpty()) {
                retTriples += "<" + localityURI + "> <" + Resources.fallsWithin + "> <" + waterAreaURI + "> .\n";
            }
            if (!habitatURI.isEmpty()) {
                retTriples += "<" + localityURI + "> <" + Resources.hasType + "> <" + habitatURI + "> .\n";
            }
        }
        if (!habitatURI.isEmpty()) {
            retTriples += "<" + habitatURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.ecosystemTypeLabel + "> .\n";
            if (!habitatName.isEmpty()) {
                retTriples += "<" + habitatURI + "> <" + Resources.rdfsLabel + "> \"" + habitatName + "\" .\n";
            }
        }
        if (!ecosystemURI.isEmpty()) {
            retTriples += "<" + ecosystemURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.ecosystemLabel + "> .\n";
            if (!ecosystemName.isEmpty()) {
                retTriples += "<" + ecosystemURI + "> <" + Resources.rdfsLabel + "> \"" + ecosystemName + "\" .\n";
            }
        }
        for (Pair pair : this.actors) {
            retTriples += "<" + pair.getKey() + "> <" + Resources.rdfTypeLabel + "> <" + Resources.personLabel + "> .\n";
            retTriples += "<" + pair.getKey() + "> <" + Resources.rdfsLabel + "> \"" + pair.getValue() + "\" .\n";
        }
       
        if (!speciesURI.isEmpty()) {
            retTriples += "<" + speciesURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.speciesLabel + "> .\n";
            if (!speciesName.isEmpty()) {
                retTriples += "<" + speciesURI + "> <" + Resources.rdfsLabel + "> \"" + speciesName + "\" .\n";
            }
        }
        if (!countryURI.isEmpty()) {
            retTriples += "<" + countryURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.countryLabel + "> .\n";
            if (!countryName.isEmpty()) {
                retTriples += "<" + countryURI + "> <" + Resources.rdfsLabel + "> \"" + countryName + "\" .\n";
            }
        }
        if (!waterAreaURI.isEmpty()) {
            retTriples += "<" + waterAreaURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.waterAreaLabel + "> .\n";
            if (!waterAreaName.isEmpty()) {
                retTriples += "<" + waterAreaURI + "> <" + Resources.rdfsLabel + "> \"" + waterAreaName + "\" .\n";
            }
        }
        if (!datasetURI.isEmpty()) {
            retTriples += "<" + datasetURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.datasetLabel + "> .\n";
            if (!datasetTitle.isEmpty()) {
                retTriples += "<" + datasetURI + "> <" + Resources.rdfsLabel + "> \"" + datasetTitle + "\" .\n";
            }
        }
        if(!minimumDepth.isEmpty() || !maximumDepth.isEmpty()){
                String measurementEvent="urn:uuid:"+UUID.randomUUID().toString().toUpperCase();
                String unitMetersUri="http://metadata_constants/unit/meters";
                String unitMetersValue="meters";
                String maxDepthTypeUri="http://metadata_constants/dimension_type/max_depth";
                String minDepthTypeUri="http://metadata_constants/dimension_type/min_depth";
                String maxDepthTypeValue="maximum depth";
                String minDepthTypeValue="minimum depth";
                retTriples+="<"+occurrenceEventURI+"> <"+Resources.consistsOf+"> <"+measurementEvent+">. "
                           +"<"+measurementEvent+"> <"+Resources.rdfTypeLabel+"> <"+Resources.measurementEventLabel+">. ";
                if(!minimumDepth.isEmpty()){
                    String minDimensionUuid="urn:uuid:"+UUID.randomUUID().toString().toUpperCase();
                    retTriples+="<"+measurementEvent+"> <"+Resources.observedDimension+"> <"+minDimensionUuid+">. "
                               +"<"+minDimensionUuid+"> <"+Resources.rdfTypeLabel+"> <"+Resources.dimensionLabel+">. "
                               +"<"+minDimensionUuid+"> <"+Resources.rdfsLabel+"> \"Minimum depth in meters: "+minimumDepth+"\". "
                               +"<"+minDimensionUuid+"> <"+Resources.typeLabel+"> <"+minDepthTypeUri+">. "
                               +"<"+minDepthTypeUri+"> <"+Resources.rdfTypeLabel+"> <"+Resources.typeLabel+">. "
                               +"<"+minDepthTypeUri+"> <"+Resources.rdfsLabel+"> \""+minDepthTypeValue+"\". "
                               +"<"+minDimensionUuid+"> <"+Resources.hasValue+"> \""+minimumDepth+"\". "
                               +"<"+minDimensionUuid+"> <"+Resources.hasUnit+"> <"+unitMetersUri+">. "
                               +"<"+unitMetersUri+"> <"+Resources.typeLabel+"> <"+Resources.measurementUnitLabel+">. "
                               +"<"+unitMetersUri+"> <"+Resources.rdfsLabel+"> \""+unitMetersValue+"\". ";
                }
                if(!maximumDepth.isEmpty()){
                    String maxDimensionUuid="urn:uuid:"+UUID.randomUUID().toString().toUpperCase();
                    retTriples+="<"+measurementEvent+"> <"+Resources.observedDimension+"> <"+maxDimensionUuid+">. "
                               +"<"+maxDimensionUuid+"> <"+Resources.rdfTypeLabel+"> <"+Resources.dimensionLabel+">. "
                               +"<"+maxDimensionUuid+"> <"+Resources.rdfsLabel+"> \"Maximum depth in meters: "+maximumDepth+"\". "
                               +"<"+maxDimensionUuid+"> <"+Resources.typeLabel+"> <"+maxDepthTypeUri+">. "
                               +"<"+maxDepthTypeUri+"> <"+Resources.rdfTypeLabel+"> <"+Resources.typeLabel+">. "
                               +"<"+maxDepthTypeUri+"> <"+Resources.rdfsLabel+"> \""+maxDepthTypeValue+"\". "
                               +"<"+maxDimensionUuid+"> <"+Resources.hasValue+"> \""+maximumDepth+"\". "
                               +"<"+maxDimensionUuid+"> <"+Resources.hasUnit+"> <"+unitMetersUri+">. "
                               +"<"+unitMetersUri+"> <"+Resources.typeLabel+"> <"+Resources.measurementUnitLabel+">. "
                               +"<"+unitMetersUri+"> <"+Resources.rdfsLabel+"> \""+unitMetersValue+"\". ";
                }
            }
        logger.debug("Struct in NTriples format: \n" + retTriples);
        return retTriples;
    }


    /**Validates the fields that will contain URIs
     * 
     * @throws URIValidationException if any of the URIs is not in valid form */
    public void validateURIs() throws URIValidationException{
        if(!this.datasetURI.isEmpty()){
            this.validateURI(this.datasetURI);
        }
        if(!this.occurrenceEventURI.isEmpty()){
            this.validateURI(this.occurrenceEventURI);
        }
         if(!this.physicalObjectURI.isEmpty()){
            this.validateURI(this.physicalObjectURI);
        }
    
        if(!this.countryURI.isEmpty()){
            this.validateURI(this.countryURI);
        }
        for(Pair pair : this.actors){
            this.validateURI(pair.getKey());
        }
        if(!this.localityURI.isEmpty()){
            this.validateURI(this.localityURI);
        }
        if(!this.waterAreaURI.isEmpty()){
            this.validateURI(this.waterAreaURI);
        }
        if(!this.equipmentTypeURI.isEmpty()){
            this.validateURI(this.equipmentTypeURI);
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

        return "OccurrenceEventURI: "+this.occurrenceEventURI+"\t"
              +"DatasetURI: "+this.datasetURI+"\t"
              +"DatasetTitle: "+this.datasetTitle+"\t"
              +"IndividualURI: "+this.physicalObjectURI+"\t"
             
              +"Actors: "+this.actors+"\t"
              +"Timespan: "+this.timeSpan+"\t"
              +"Locality Name: "+this.localityName+"\t"
              +"Locality URI: "+this.localityURI+"\t"
              +"CountryName: "+this.countryName+"\t"
              +"CountryURI: "+this.countryURI+"\t"
              +"WaterAreaName: "+this.waterAreaName+"\t"
              +"WaterAreaURI: "+this.waterAreaURI+"\t"
              +"HabitatName: "+this.habitatName+"\t"
              +"HabitatURI: "+this.habitatURI+"\t"
              +"EcosystemName: "+this.ecosystemName+"\t"
              +"EcosystemURI: "+this.ecosystemURI+"\t"
              +"EquipmentTypeName: "+this.equipmentTypeName+"\t"
              +"EquipmentTypeURI: "+this.equipmentTypeURI+"\t"
              +"Latitude: "+this.latitude+"\t"
              +"Longitude: "+this.longitude+"\t"
              +"maximumDepth: "+this.maximumDepth+"\t"
              +"minimumDepth: "+this.minimumDepth+"\t"
              +"SamplingProtocalName: "+this.samplingProtocolName+"\t"
              +"SamplingProtocolURI:"+this.samplingProtocolURI+"\t"
              +"Geodetic Datum: "+this.geodeticDatum+"\t"
              +"Bibliographic Citation: "+this.bibliographicCitation;
    }
    
    @Override
    public boolean equals(Object anotherObject){
        if(anotherObject instanceof OccurrenceStatsTempStruct){
            OccurrenceStatsTempStruct anotherStruct=(OccurrenceStatsTempStruct)anotherObject;
            return this.datasetTitle.equals(anotherStruct.getDatasetTitle()) &&
                   this.datasetURI.equals(anotherStruct.getDatasetURI()) &&
                   this.physicalObjectURI.equals(anotherStruct.getPhysicalObjectURI()) &&
                   this.actors.containsAll(anotherStruct.getActors()) &&
                   anotherStruct.getActors().containsAll(this.actors) &&
                   this.timeSpan.equals(anotherStruct.getTimeSpan()) &&
                   this.localityURI.equals(anotherStruct.getLocalityURI()) &&
                   this.localityName.equals(anotherStruct.getLocalityName()) &&
                   this.countryName.equals(anotherStruct.getCountryName()) &&
                   this.countryURI.equals(anotherStruct.getCountryURI()) &&
                   this.waterAreaName.equals(anotherStruct.getWaterAreaName()) &&
                   this.waterAreaURI.equals(anotherStruct.getWaterAreaURI()) &&
                   this.habitatName.equals(anotherStruct.getHabitatName()) &&
                   this.habitatURI.equals(anotherStruct.getHabitatURI()) &&
                   this.ecosystemName.equals(anotherStruct.getEcosystemName()) &&
                   this.ecosystemURI.equals(anotherStruct.getEcosystemURI()) &&
                   this.equipmentTypeName.equals(anotherStruct.getEquipmentTypeName()) &&
                   this.equipmentTypeURI.equals(anotherStruct.getEquipmentTypeURI()) &&
                   this.latitude.equals(anotherStruct.getLatitude()) &&
                   this.maximumDepth.equals(anotherStruct.getMaximumDepth()) &&
                   this.minimumDepth.equals(anotherStruct.getMinimumDepth()) &&
                   this.samplingProtocolName.equals(anotherStruct.getSamplingProtocolName()) &&
                   this.samplingProtocolURI.equals(anotherStruct.getSamplingProtocolURI()) &&
                   this.bibliographicCitation.equals(anotherStruct.getBibliographicCitation()) &&
                   this.longitude.equals(anotherStruct.getLongitude());
        }else{
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.occurrenceEventURI);
        return hash;
    }
}