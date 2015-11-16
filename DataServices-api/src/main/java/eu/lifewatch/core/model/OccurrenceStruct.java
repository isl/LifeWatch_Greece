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
 * Struct object are actually entries in the repository. They are in the form of
 * a C-Like Struct. These object can also describe themselves in an NTriple
 * form.
 *
 * @author Nikos Minadakis (minadakn 'at' ics 'dot' forth 'dot' gr)
 * @author Yannis Marketakis (marketak 'at' ics 'dot' forth 'dot' gr)
 */
public class OccurrenceStruct {

    private String occurrenceEventURI;
    private String occurrenceEvent;
    private String datasetURI;
    private String datasetTitle;
    private String speciesURI;
    private String speciesName;
    private String individualURI;
    private String individualLabel;
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
    private String samplingProtocol;
    private String samplingProtocolURI;
//    private String geodeticDatum;
    private String bibliographicCitation;
    private String bibliographicCitationURI;
    private String description;
    private String stationURI;
    private String stationNotes;
    private String coordinates;
    private static final Logger logger = Logger.getLogger(OccurrenceStruct.class);

    /**
     * Creates a new instance by initializing all the fields to be empty (Null
     * values will cause NPE issues)
     */
    public OccurrenceStruct() {
        occurrenceEventURI = "";
        occurrenceEvent = "";
        datasetURI = "";
        datasetTitle = "";
        individualURI = "";
        individualLabel = "";
        actors = new ArrayList<>();
        speciesName = "";
        speciesURI = "";
        timeSpan = "";
        localityName = "";
        localityURI = "";
        countryName = "";
        countryURI = "";
        waterAreaName = "";
        waterAreaURI = "";
        habitatName = "";
        habitatURI = "";
        ecosystemName = "";
        ecosystemURI = "";
        equipmentTypeName = "";
        equipmentTypeURI = "";
        latitude = "";
        longitude = "";
        maximumDepth = "";
        minimumDepth = "";
        samplingProtocol = "";
        samplingProtocolURI = "";
//        geodeticDatum = "";
        description = "";
        bibliographicCitation = "";
        bibliographicCitationURI = "";
        stationURI = "";
        stationNotes = "";
        coordinates = "";
    }

    public String getOccurrenceEventURI() {
        return occurrenceEventURI;
    }
    
    public String getOccurrenceEvent() {
        return occurrenceEventURI;
    }
    
    public String getDatasetURI() {
        return datasetURI;
    }

    public String getDatasetTitle() {
        return datasetTitle;
    }

    public String getIndividualURI() {
        return individualURI;
    }
    
    public String getIndividualLabel() {
        return individualLabel;
    }


    public String getDescription() {
        return description;
    }

    public Collection<String> getActorURIs() {
        Collection<String> actorURIs = new HashSet<>();
        for (Pair pair : this.actors) {
            actorURIs.add(pair.getKey());
        }
        return actorURIs;
    }

    public Collection<String> getActorNames() {
        Collection<String> actorNames = new HashSet<>();
        for (Pair pair : this.actors) {
            actorNames.add(pair.getValue());
        }
        return actorNames;
    }

    public List<Pair> getActors() {
        return this.actors;
    }

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
        return countryName;
    }

    public String getCountryURI() {
        return countryURI;
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

    public String getSpeciesName() {
        return speciesName;
    }

    public String getSpeciesURI() {
        return speciesURI;
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

    public String getSamplingProtocol() {
        return samplingProtocol;
    }

    public String getSamplingProtocolURI() {
        return samplingProtocolURI;
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

//    public String getGeodeticDatum() {
//        return geodeticDatum;
//    }
    public String getBibliographicCitation() {
        return bibliographicCitation;
    }

    public String getBibliographicCitationURI() {
        return bibliographicCitationURI;
    }

    public void setOccurrenceEventURI(String occurrenceEventURI) {
        this.occurrenceEventURI = occurrenceEventURI;
    }
    
    public void setOccurrenceEvent(String occurrenceEvent) {
        this.occurrenceEvent= occurrenceEvent;
    }

    public void setDatasetURI(String datasetURI) {
        this.datasetURI = datasetURI;
    }

    public void setDatasetTitle(String datasetTitle) {
        this.datasetTitle = datasetTitle;
    }

    public void setSpeciesURI(String speciesURI) {
        this.speciesURI = speciesURI;
    }

    public void setSpeciesName(String speciesName) {
        this.speciesName = speciesName;
    }

    public void setIndividualURI(String individualURI) {
        this.individualURI = individualURI;
    }
    
    public void setIndividualLabel(String individualLabel) {
        this.individualLabel= individualLabel;
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

    public void setDescription(String description) {
        this.description = description;
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

    public void setStationURI(String stationURI) {
        this.stationURI = stationURI;
    }

    public void setStationNotes(String stationNotes) {
        this.stationNotes = stationNotes;
    }

    public void setSamplingProtocol(String samplingProtocol) {
        this.samplingProtocol = samplingProtocol;
    }

    public void setSamplingProtocolURI(String samplingProtocolURI) {
        this.samplingProtocolURI = samplingProtocolURI;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

//    public void setGeodeticDatum(String geodeticDatum) {
//        this.geodeticDatum = geodeticDatum;
//    }
    public void setBibliographicCitation(String bibliographicCitation) {
        this.bibliographicCitation = bibliographicCitation;
    }

    public void setBibliographicCitationURI(String bibliographicCitationURI) {
        this.bibliographicCitationURI = bibliographicCitationURI;
    }

    public OccurrenceStruct withOccurrenceEventURI(String occurrenceEventURI) {
        this.occurrenceEventURI = occurrenceEventURI;
        return this;
    }
    
    public OccurrenceStruct withOccurrenceEvent(String occurrenceEvent) {
        this.occurrenceEvent = occurrenceEvent;
        return this;
    }

    public OccurrenceStruct withDatasetTitle(String datasetTitle) {
        this.datasetTitle = datasetTitle;
        return this;
    }

    public OccurrenceStruct withDatasetURI(String datasetURI) {
        this.datasetURI = datasetURI;
        return this;
    }

    public OccurrenceStruct withDescription(String description) {
        this.description = description;
        return this;
    }

    public OccurrenceStruct withIndividualURI(String individualURI) {
        this.individualURI = individualURI;
        return this;
    }

    public OccurrenceStruct withIndividualLabel(String individualLabel) {
        this.individualLabel = individualLabel;
        return this;
    }
     
    public OccurrenceStruct withActor(String actorURI, String actorName) {
        if (!this.getActorURIs().contains(actorURI)) {
            this.actors.add(new Pair(actorURI, actorName));
        }
        return this;
    }

    public OccurrenceStruct withSpeciesName(String speciesName) {
        this.speciesName = speciesName;
        return this;
    }

    public OccurrenceStruct withSpeciesURI(String speciesURI) {
        this.speciesURI = speciesURI;
        return this;
    }

    public OccurrenceStruct withTimeSpan(String timeSpan) {
        this.timeSpan = timeSpan;
        return this;
    }

    public OccurrenceStruct withLocalityURI(String localityURI) {
        this.localityURI = localityURI;
        return this;
    }

    public OccurrenceStruct withLocalityName(String localityName) {
        this.localityName = localityName;
        return this;
    }

    public OccurrenceStruct withCountryURI(String countryURI) {
        this.countryURI = countryURI;
        return this;
    }

    public OccurrenceStruct withCountryName(String countryName) {
        this.countryName = countryName;
        return this;
    }

    public OccurrenceStruct withWaterAreaURI(String waterAreaURI) {
        this.waterAreaURI = waterAreaURI;
        return this;
    }

    public OccurrenceStruct withWaterAreaName(String waterAreaName) {
        this.waterAreaName = waterAreaName;
        return this;
    }

    public OccurrenceStruct withHabitatURI(String habitatURI) {
        this.habitatURI = habitatURI;
        return this;
    }

    public OccurrenceStruct withHabitatName(String habitatName) {
        this.habitatName = habitatName;
        return this;
    }

    public OccurrenceStruct withStationURI(String stationURI) {
        this.stationURI = stationURI;
        return this;
    }

    public OccurrenceStruct withStationNotes(String stationNotes) {
        this.stationNotes = stationNotes;
        return this;
    }

    public OccurrenceStruct withEcosystemURI(String ecosystemURI) {
        this.ecosystemURI = ecosystemURI;
        return this;
    }

    public OccurrenceStruct withEcosystemName(String ecosystemName) {
        this.ecosystemName = ecosystemName;
        return this;
    }

    public OccurrenceStruct withEquipmentTypeURI(String equipmentTypeURI) {
        this.equipmentTypeURI = equipmentTypeURI;
        return this;
    }

    public OccurrenceStruct withEquipmentTypeName(String equipmentTypeName) {
        this.equipmentTypeName = equipmentTypeName;
        return this;
    }

    public OccurrenceStruct withSamplingProtocolURI(String samplingProtocolURI) {
        this.samplingProtocolURI = samplingProtocolURI;
        return this;
    }

    public OccurrenceStruct withSamplingProtocol(String samplingProtocol) {
        this.samplingProtocol = samplingProtocol;
        return this;
    }

    public OccurrenceStruct withLatitude(String latitude) {
        this.latitude = latitude;
        return this;
    }

    public OccurrenceStruct withLongitude(String longitude) {
        this.longitude = longitude;
        return this;
    }

    public OccurrenceStruct withMaximumDepth(String maximumDepth) {
        this.maximumDepth = maximumDepth;
        return this;
    }

    public OccurrenceStruct withMinimumDepth(String minimumDepth) {
        this.minimumDepth = minimumDepth;
        return this;
    }

//    public OccurrenceStruct withGeodeticDatum(String geodeticDatum) {
//        this.geodeticDatum = geodeticDatum;
//        return this;
//    }
    public OccurrenceStruct withBibliographicCitation(String bibliographicCitation) {
        this.bibliographicCitation = bibliographicCitation;
        return this;
    }

    public OccurrenceStruct withBibliographicCitationURI(String bibliographicCitationURI) {
        this.bibliographicCitationURI = bibliographicCitationURI;
        return this;
    }

    public OccurrenceStruct withCoordinates(String coordinates) {
        this.coordinates = coordinates;
        return this;
    }

    /**
     * Produces an NTRIPLES output so that it can be used to SPARQL queries
     *
     * @return an NTRIPLES representation of the struct fields
     */
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
            if (!individualURI.isEmpty()) {
                retTriples += "<" + occurrenceEventURI + "> <" + Resources.hasFoundObject + "> <" + individualURI + "> .\n";
            }
            if (!datasetURI.isEmpty()) {
                retTriples += "<" + datasetURI + "> <" + Resources.refersTo + "> <" + occurrenceEventURI + "> . \n";
            }
            if (!description.isEmpty()) {
                retTriples += "<" + occurrenceEventURI + "> <" + Resources.hasNote + "> \"" + description + "\" .\n";
            }
             if (!occurrenceEvent.isEmpty()) {
                retTriples += "<" + occurrenceEventURI + "> <" + Resources.rdfsLabel + "> \"" + occurrenceEvent + "\" .\n";
            }
            
        }
        if (!equipmentTypeURI.isEmpty()) {
            retTriples += "<" + equipmentTypeURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.equipmentTypeLabel + "> .\n";
            if (!equipmentTypeName.isEmpty()) {
                retTriples += "<" + equipmentTypeURI + "> <" + Resources.rdfsLabel + "> \"" + equipmentTypeName + "\" .\n";
            }
        }

        if (!samplingProtocolURI.isEmpty()) {
            retTriples += "<" + samplingProtocolURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.procedureLabel + "> .\n";
            if (!samplingProtocol.isEmpty()) {
                retTriples += "<" + samplingProtocolURI + "> <" + Resources.rdfsLabel + "> \"" + samplingProtocol + "\" .\n";
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
        if (!individualURI.isEmpty()) {
            retTriples += "<" + individualURI + "> <" + Resources.rdfTypeLabel + "> <" + Resources.bioticElementLabel + "> .\n";
            if (!speciesURI.isEmpty()) {
                retTriples += "<" + individualURI + "> <" + Resources.belongsTo + "> <" + speciesURI + "> .\n";
            }
            if(!individualLabel.isEmpty()){
                retTriples+= "<"+individualURI+"> <"+Resources.rdfsLabel+"> \""+individualLabel+"\" .\n";
            }
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
        logger.debug("Struct in NTriples format: \n" + retTriples);
        return retTriples;
    }

    /**
     * Validates the fields that will contain URIs
     *
     * @throws URIValidationException if any of the URIs is not in valid form
     */
    public void validateURIs() throws URIValidationException {
        if (!this.datasetURI.isEmpty()) {
            this.validateURI(this.datasetURI);
        }
        if (!this.occurrenceEventURI.isEmpty()) {
            this.validateURI(this.occurrenceEventURI);
        }
        if (!this.countryURI.isEmpty()) {
            this.validateURI(this.countryURI);
        }
        for (Pair pair : this.actors) {
            this.validateURI(pair.getKey());
        }
        if (!this.localityURI.isEmpty()) {
            this.validateURI(this.localityURI);
        }
        if (!this.waterAreaURI.isEmpty()) {
            this.validateURI(this.waterAreaURI);
        }
        if (!this.equipmentTypeURI.isEmpty()) {
            this.validateURI(this.equipmentTypeURI);
        }
        if (!this.ecosystemURI.isEmpty()) {
            this.validateURI(this.ecosystemURI);
        }
        if (!this.speciesURI.isEmpty()) {
            this.validateURI(this.speciesURI);
        }

    }

    private void validateURI(String uri) throws URIValidationException {
        try {
            new URI(uri);
        } catch (URISyntaxException ex) {
            throw new URIValidationException("The given URI (\"" + uri + "\" is not valid", ex);
        }
    }

    @Override
    public String toString() {
        return "OccurrenceEventURI: " + this.occurrenceEventURI + "\t"
                + "DatasetURI: " + this.datasetURI + "\t"
                + "DatasetTitle: " + this.datasetTitle + "\t"
                + "SpeciesURI: " + this.speciesURI + "\t"
                + "SpeciesName: " + this.speciesName + "\t"
                + "IndividualURI: " + this.individualURI + "\t"
                + "Actors: " + this.actors + "\t"
                + "Timespan: " + this.timeSpan + "\t"
                + "Locality Name: " + this.localityName + "\t"
                + "Locality URI: " + this.localityURI + "\t"
                + "CountryName: " + this.countryName + "\t"
                + "CountryURI: " + this.countryURI + "\t"
                + "WaterAreaName: " + this.waterAreaName + "\t"
                + "WaterAreaURI: " + this.waterAreaURI + "\t"
                + "HabitatName: " + this.habitatName + "\t"
                + "HabitatURI: " + this.habitatURI + "\t"
                + "EcosystemName: " + this.ecosystemName + "\t"
                + "EcosystemURI: " + this.ecosystemURI + "\t"
                + "EquipmentTypeName: " + this.equipmentTypeName + "\t"
                + "EquipmentTypeURI: " + this.equipmentTypeURI + "\t"
                + "Latitude: " + this.latitude + "\t"
                + "Longitude: " + this.longitude + "\t"
                + "maximumDepth: " + this.maximumDepth + "\t"
                + "minimumDepth: " + this.minimumDepth + "\t"
                + "SamplingProtocalName: " + this.samplingProtocol + "\t"
                + "SamplingProtocolURI:" + this.samplingProtocolURI + "\t"
                // + "Geodetic Datum: " + this.geodeticDatum + "\t"
                + "Bibliographic Citation: " + this.bibliographicCitation;
    }

    @Override
    public boolean equals(Object anotherObject) {
        if (anotherObject instanceof OccurrenceStruct) {
            OccurrenceStruct anotherStruct = (OccurrenceStruct) anotherObject;
            return this.datasetTitle.equals(anotherStruct.getDatasetTitle())
                    && this.datasetURI.equals(anotherStruct.getDatasetURI())
                    && this.individualURI.equals(anotherStruct.getIndividualURI())
                    && this.actors.containsAll(anotherStruct.getActors())
                    && anotherStruct.getActors().containsAll(this.actors)
                    && this.timeSpan.equals(anotherStruct.getTimeSpan())
                    && this.localityURI.equals(anotherStruct.getLocalityURI())
                    && this.localityName.equals(anotherStruct.getLocalityName())
                    && this.countryName.equals(anotherStruct.getCountryName())
                    && this.countryURI.equals(anotherStruct.getCountryURI())
                    && this.speciesName.equals(anotherStruct.getSpeciesName())
                    && this.speciesURI.equals(anotherStruct.getSpeciesURI())
                    && this.waterAreaName.equals(anotherStruct.getWaterAreaName())
                    && this.waterAreaURI.equals(anotherStruct.getWaterAreaURI())
                    && this.habitatName.equals(anotherStruct.getHabitatName())
                    && this.habitatURI.equals(anotherStruct.getHabitatURI())
                    && this.ecosystemName.equals(anotherStruct.getEcosystemName())
                    && this.ecosystemURI.equals(anotherStruct.getEcosystemURI())
                    && this.equipmentTypeName.equals(anotherStruct.getEquipmentTypeName())
                    && this.equipmentTypeURI.equals(anotherStruct.getEquipmentTypeURI())
                    && this.latitude.equals(anotherStruct.getLatitude())
                    && this.maximumDepth.equals(anotherStruct.getMaximumDepth())
                    && this.minimumDepth.equals(anotherStruct.getMinimumDepth())
                    && this.samplingProtocol.equals(anotherStruct.getSamplingProtocol())
                    && this.samplingProtocolURI.equals(anotherStruct.getSamplingProtocolURI())
                    //&& this.geodeticDatum.equals(anotherStruct.getGeodeticDatum())
                    && this.description.equals(anotherStruct.getDescription())
                    && this.bibliographicCitation.equals(anotherStruct.getBibliographicCitation())
                    && this.longitude.equals(anotherStruct.getLongitude());
        } else {
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
