package eu.lifewatch.core.impl;

import eu.lifewatch.common.Resources;
import eu.lifewatch.core.model.DirectoryStruct;
import eu.lifewatch.core.model.OccurrenceStatsTempStruct;
import eu.lifewatch.core.model.OccurrenceStruct;
import eu.lifewatch.core.model.ScientificNamingStruct;
import eu.lifewatch.core.model.TaxonomyStruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import org.apache.log4j.Logger;
import org.gbif.dwc.Archive;
import org.gbif.dwc.ArchiveFile;
import org.gbif.dwc.DwcFiles;
import org.gbif.dwc.MetadataException;
import org.gbif.dwc.record.Record;
import org.gbif.dwc.record.StarRecord;
import org.gbif.dwc.terms.DwcTerm;
import org.gbif.dwc.terms.GbifTerm;
import org.gbif.dwc.terms.Term;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author Yannis Marketakis (marketak 'at' ics 'dot' forth 'dot' gr)
 */
public class DwCArchiveParser {
    private static final Logger log=Logger.getLogger(DwCArchiveParser.class);
    private Archive dwcArchive;
    private String datasetURI;
    private String datasetTitle;
    
    public DwCArchiveParser(File archive) throws IOException{
        Path myArchiveFile = Paths.get(archive.getAbsolutePath());
        Path extractToFolder = Paths.get("arch");
        this.dwcArchive = DwcFiles.fromCompressed(myArchiveFile, extractToFolder);
        this.datasetURI=Resources.defaultNamespaceForURIs+"/dataset/"+UUID.randomUUID().toString();
    } 
    
    public void parseData() throws IOException, MetadataException{
        log.debug("Parsing dataset metadata");
        DirectoryStruct directoryStruct=this.parseDatasetMetadata(this.dwcArchive.getMetadata());
        System.out.println(directoryStruct);
        log.info("Archive rowtype: " + this.dwcArchive.getCore().getRowType() + ", "+ this.dwcArchive.getExtensions().size() + " extension(s)");

        switch(this.dwcArchive.getCore().getRowType().simpleName()){
            case "Occurrence":
                    parseOccurrenceArchive(this.dwcArchive, null);
                    break;
            default:
                log.error("No parser for "+this.dwcArchive.getCore().getRowType());     
        }
        
        for(ArchiveFile archiveFile : this.dwcArchive.getExtensions()){
            switch(archiveFile.getRowType().simpleName()){
            case "Occurrence":
                    parseOccurrenceArchive(this.dwcArchive, archiveFile.getRowType());
                    break;
            default:
                log.error("No parser for "+this.dwcArchive.getCore().getRowType());     
        }
        }
    }
    
    private DirectoryStruct parseDatasetMetadata(String metadataContents){
        DirectoryStruct directoryStruct=new DirectoryStruct();
        Document metadataDoc=Jsoup.parse(metadataContents);
        Elements idElements=metadataDoc.getElementsByTag(Resources.ALTERNATE_IDENTIFIER);
        for(Element idElement : idElements){
            if(idElement.text().startsWith("http:")){
                directoryStruct.setDatasetID(idElement.text());
            }else{
                this.datasetURI=Resources.defaultNamespaceForURIs+"/dataset/"+idElement.text();
                directoryStruct.setDatasetURI(this.datasetURI);
            }
        }
        Elements titleElements=metadataDoc.getElementsByTag(Resources.TITLE);
        if(titleElements!=null){
            this.datasetTitle=titleElements.get(0).text();
            directoryStruct.setDatasetName(this.datasetTitle);
        }
        Elements creatorElements=metadataDoc.getElementsByTag(Resources.CREATOR);
        if(creatorElements!=null){
            Elements creatorNameElements=creatorElements.get(0).getElementsByTag(Resources.INDIVIDUAL_NAME);
            if(creatorNameElements!=null){
                directoryStruct.setCreatorName(creatorNameElements.get(0).text());
                directoryStruct.setCreatorURI(Utils.hashUri(Resources.defaultNamespaceForURIs, "actor", creatorNameElements.get(0).text()));
                directoryStruct.setOwnerName(creatorNameElements.get(0).text());
                directoryStruct.setOwnerURI(Utils.hashUri(Resources.defaultNamespaceForURIs, "actor", creatorNameElements.get(0).text()));
                directoryStruct.setKeeperName(creatorNameElements.get(0).text());
                directoryStruct.setKeeperURI(Utils.hashUri(Resources.defaultNamespaceForURIs, "actor", creatorNameElements.get(0).text()));
            }
        }
        Elements contributorElements=metadataDoc.getElementsByTag(Resources.ASSOCIATED_PARTY);
        for(Element contributorElement : contributorElements){
            Elements contributorNameElements=contributorElement.getElementsByTag(Resources.INDIVIDUAL_NAME);
            if(contributorNameElements!=null){
                directoryStruct.withContributor(Utils.hashUri(Resources.defaultNamespaceForURIs, "actor", contributorNameElements.get(0).text()), contributorNameElements.get(0).text());
            }
        }
        Elements pubDateElements=metadataDoc.getElementsByTag(Resources.PUB_DATE);
        if(pubDateElements!=null){
            directoryStruct.setPublicationDate(pubDateElements.get(0).text());
            directoryStruct.setPublicationEventURI(Utils.hashUri(Resources.defaultNamespaceForURIs, "publication", pubDateElements.get(0).text()));
        }
        Elements contactElemets=metadataDoc.getElementsByTag(Resources.CONTACT);
        if(contactElemets!=null){
            directoryStruct.setContactPoint(contactElemets.get(0).text());
        }
        Elements intellectualRightsElements=metadataDoc.getElementsByTag(Resources.INTELLECTUAL_RIGHTS);
        if(intellectualRightsElements!=null){
            directoryStruct.setAccessRights(intellectualRightsElements.get(0).text());
            directoryStruct.setAccessRightsURI(Utils.hashUri(Resources.defaultNamespaceForURIs,"accessrights",intellectualRightsElements.get(0).text()));
        }
        Elements keywordSets=metadataDoc.getElementsByTag(Resources.KEYWORD_SET);
        if(keywordSets!=null){
            for(Element keywordSet : keywordSets){
                if(keywordSet.getElementsByTag(Resources.KEYWORD_THESAURUS)!=null && keywordSet.getElementsByTag(Resources.KEYWORD_THESAURUS).text().contains(Resources.GBIF_THESAURUS_KEYWORD)){
                    directoryStruct.setDatasetType(keywordSet.getElementsByTag(Resources.KEYWORD).text());
                }
            }
        }
        return directoryStruct;
    }
    
    private void parseOccurrenceArchive(Archive dwcArchive, Term term){
        if(term!=null){
            for(Record rec : dwcArchive.getExtension(term)){
                TaxonomyStruct taxonomyStruct=this.retrieveTaxonomy(rec);
                ScientificNamingStruct scNameStruct=this.retrieveScName(rec);
                OccurrenceStruct occurenceStruct=this.retrieveOccurence(rec);
                OccurrenceStatsTempStruct occurenceTempStruct=this.retrieveOccurenceTemp(rec);
                System.out.println(taxonomyStruct);
                System.out.println(scNameStruct);
                System.out.println(occurenceTempStruct);
                System.out.println(occurenceStruct);
            }
        }else{
            for(StarRecord rec : dwcArchive){
                TaxonomyStruct taxonomyStruct=this.retrieveTaxonomy(rec.core());
                ScientificNamingStruct scNameStruct=this.retrieveScName(rec.core());
                OccurrenceStruct occurenceStruct=this.retrieveOccurence(rec.core());
                OccurrenceStatsTempStruct occurenceTempStruct=this.retrieveOccurenceTemp(rec.core());
                System.out.println(taxonomyStruct);
                System.out.println(scNameStruct);
                System.out.println(occurenceTempStruct);
                System.out.println(occurenceStruct);
            }
        }
    }
    
    private TaxonomyStruct retrieveTaxonomy(Record rec){
        TaxonomyStruct taxonomyStruct=new TaxonomyStruct()
                    .withDatasetName(this.datasetTitle)
                    .withDatasetURI(this.datasetURI);
            if(rec.value(DwcTerm.scientificNameID)!=null){
                    taxonomyStruct.withSpeciesURI(rec.value(DwcTerm.scientificNameID));
            }else if(rec.value(DwcTerm.scientificName)!=null){
                taxonomyStruct.withSpeciesName(Utils.hashUri(Resources.defaultNamespaceForURIs, "species",rec.value(DwcTerm.scientificName)));
            }
            if(rec.value(DwcTerm.scientificName)!=null){
                taxonomyStruct.withSpeciesName(rec.value(DwcTerm.scientificName));
            }
            if(rec.value(DwcTerm.kingdom)!=null){
                taxonomyStruct.withKingdomURI(Utils.hashUri(Resources.defaultNamespaceForURIs, "species_kingdom", rec.value(DwcTerm.kingdom)))
                              .withKingdomName(rec.value(DwcTerm.kingdom));
            }
            if(rec.value(DwcTerm.phylum)!=null){
                taxonomyStruct.withPhylumURI(Utils.hashUri(Resources.defaultNamespaceForURIs, "species_phylum", rec.value(DwcTerm.phylum)))
                              .withPhylumName(rec.value(DwcTerm.phylum));
            }
            if(rec.value(DwcTerm.class_)!=null){
                taxonomyStruct.withClassURI(Utils.hashUri(Resources.defaultNamespaceForURIs, "species_class", rec.value(DwcTerm.class_)))
                              .withClassName(rec.value(DwcTerm.class_));
            }
            if(rec.value(DwcTerm.order)!=null){
                taxonomyStruct.withOrderURI(Utils.hashUri(Resources.defaultNamespaceForURIs, "species_order", rec.value(DwcTerm.order)))
                              .withOrderName(rec.value(DwcTerm.order));
            }
            if(rec.value(DwcTerm.family)!=null){
                taxonomyStruct.withFamilyURI(Utils.hashUri(Resources.defaultNamespaceForURIs, "species_family", rec.value(DwcTerm.family)))
                              .withFamilyName(rec.value(DwcTerm.family));
            }
            if(rec.value(DwcTerm.genus)!=null){
                taxonomyStruct.withGenusURI(Utils.hashUri(Resources.defaultNamespaceForURIs, "species_genus", rec.value(DwcTerm.genus)))
                              .withGenusName(rec.value(DwcTerm.genus));
            }
            return taxonomyStruct;
    }
    
    private ScientificNamingStruct retrieveScName(Record rec){
        ScientificNamingStruct scNameStruct=new ScientificNamingStruct()
                    .withDatasetName(this.datasetTitle)
                    .withDatasetURI(this.datasetURI);
        if(rec.value(DwcTerm.scientificNameID)!=null){
            scNameStruct.withSpeciesURI(rec.value(DwcTerm.scientificNameID));
        }else if(rec.value(DwcTerm.scientificName)!=null){
            scNameStruct.withSpeciesName(Utils.hashUri(Resources.defaultNamespaceForURIs, "species",rec.value(DwcTerm.scientificName)));
        }
        if(rec.value(DwcTerm.scientificName)!=null){
            scNameStruct.withSpeciesName(rec.value(DwcTerm.scientificName));
            scNameStruct.withScientificNameAssignmentEventURI(Utils.hashUri(Resources.defaultNamespaceForURIs, "scientific_name_assignment_event", rec.value(DwcTerm.scientificName)));
            scNameStruct.withScientificNameAssignmentEvent("Assignment of Scientific name for species "+rec.value(DwcTerm.scientificName));
            scNameStruct.withAppellationURI(Utils.hashUri(Resources.defaultNamespaceForURIs, "appellation", rec.value(DwcTerm.scientificName)));
            scNameStruct.withAppellation(rec.value(DwcTerm.scientificName));
            
        }
        if(rec.value(DwcTerm.scientificNameAuthorship)!=null){
            
            String trimmedScNameAuthorship=rec.value(DwcTerm.scientificNameAuthorship);
            if(trimmedScNameAuthorship.startsWith("(") && trimmedScNameAuthorship.endsWith(")")){
                trimmedScNameAuthorship=trimmedScNameAuthorship.substring(1, trimmedScNameAuthorship.length()-1);
            }
            int commaCharIndex=trimmedScNameAuthorship.lastIndexOf(",");
            if(commaCharIndex>0){
                String authorshipName=trimmedScNameAuthorship.substring(0,commaCharIndex).trim();
                String authorshipYear=trimmedScNameAuthorship.substring(commaCharIndex+1).trim();
                scNameStruct.withActor(Utils.hashUri(Resources.defaultNamespaceForURIs, "actor", authorshipName), authorshipName);
                scNameStruct.withTimeSpan(authorshipYear);
            }
            
        }
        if(rec.value(DwcTerm.nomenclaturalCode)!=null){
            scNameStruct.withNomenclaturalCodeURI(Utils.hashUri(Resources.defaultNamespaceForURIs, "nomenclaturalCode", rec.value(DwcTerm.nomenclaturalCode)));
            scNameStruct.withNomenclaturalCodeName(rec.value(DwcTerm.nomenclaturalCode));
        }
        return scNameStruct;
    }
    
    private OccurrenceStruct retrieveOccurence(Record rec){
        OccurrenceStruct occurrenceStruct=new OccurrenceStruct()
                    .withDatasetTitle(this.datasetTitle)
                    .withDatasetURI(this.datasetURI);
        if(rec.value(DwcTerm.occurrenceID)!=null){
            occurrenceStruct.withOccurrenceEventURI(Utils.hashUri(Resources.defaultNamespaceForURIs, "encounter_event", rec.value(DwcTerm.occurrenceID)));
            occurrenceStruct.withOccurrenceEvent(rec.value(DwcTerm.occurrenceID));
        }
        if(rec.value(DwcTerm.eventDate)!=null){
            occurrenceStruct.withTimeSpan(rec.value(DwcTerm.eventDate));
        }
        if(rec.value(DwcTerm.decimalLatitude)!=null){
            occurrenceStruct.withLatitude(rec.value(DwcTerm.decimalLatitude));
        }
        if(rec.value(DwcTerm.decimalLongitude)!=null){
            occurrenceStruct.withLongitude(rec.value(DwcTerm.decimalLongitude));
        }
        if(rec.value(DwcTerm.scientificNameID)!=null){
            occurrenceStruct.withSpeciesURI(rec.value(DwcTerm.scientificNameID));
        }else if(rec.value(DwcTerm.scientificName)!=null){
            occurrenceStruct.withSpeciesName(Utils.hashUri(Resources.defaultNamespaceForURIs, "species",rec.value(DwcTerm.scientificName)));
        }
        if(rec.value(DwcTerm.scientificName)!=null){
            occurrenceStruct.withSpeciesName(rec.value(DwcTerm.scientificName));
        }
        
        
        return occurrenceStruct;
    }
    
    private OccurrenceStatsTempStruct retrieveOccurenceTemp(Record rec){
        OccurrenceStatsTempStruct occurrenceTempStruct=new OccurrenceStatsTempStruct()
                    .withDatasetTitle(this.datasetTitle)
                    .withDatasetURI(this.datasetURI);
        String placeCoordinates="";
        if(rec.value(DwcTerm.occurrenceID)!=null){
            occurrenceTempStruct.withOccurrenceEventURI(Utils.hashUri(Resources.defaultNamespaceForURIs, "encounter_event", rec.value(DwcTerm.occurrenceID)));
            occurrenceTempStruct.withOccurrenceEvent(rec.value(DwcTerm.occurrenceID));
            occurrenceTempStruct.withPhysicalObjectURI(Utils.hashUri(Resources.defaultNamespaceForURIs, "biotic_element", rec.value(DwcTerm.occurrenceID)));
        }
        if(rec.value(DwcTerm.eventDate)!=null){
            occurrenceTempStruct.withTimeSpan(rec.value(DwcTerm.eventDate));
        }
        if(rec.value(DwcTerm.decimalLongitude)!=null){
            occurrenceTempStruct.withLongitude(rec.value(DwcTerm.decimalLongitude));
            placeCoordinates+="Longitude: "+rec.value(DwcTerm.decimalLongitude)+"\t";
        }
        if(rec.value(DwcTerm.decimalLatitude)!=null){
            occurrenceTempStruct.withLatitude(rec.value(DwcTerm.decimalLatitude));
            placeCoordinates+="Latitude: "+rec.value(DwcTerm.decimalLatitude);
        }
        if(rec.value(DwcTerm.scientificNameID)!=null){
            occurrenceTempStruct.withSpeciesURI(rec.value(DwcTerm.scientificNameID));
        }else if(rec.value(DwcTerm.scientificName)!=null){
            occurrenceTempStruct.withSpeciesName(Utils.hashUri(Resources.defaultNamespaceForURIs, "species",rec.value(DwcTerm.scientificName)));
        }
        if(rec.value(DwcTerm.scientificName)!=null){
            occurrenceTempStruct.withSpeciesName(rec.value(DwcTerm.scientificName));
            occurrenceTempStruct.withTemporaryAggregateURI(Utils.hashUri(Resources.defaultNamespaceForURIs, "temporary_aggregate",rec.value(DwcTerm.scientificName)));
            occurrenceTempStruct.withTemporaryAggregate("Temporary Aggregate of species "+rec.value(DwcTerm.scientificName));
        }
        if(rec.value(DwcTerm.locationID)!=null){
            occurrenceTempStruct.withStationURI(Utils.hashUri(Resources.defaultNamespaceForURIs, "place", rec.value(DwcTerm.locationID)));
            occurrenceTempStruct.withCoordinates(rec.value(DwcTerm.locationID));
            if(!placeCoordinates.isEmpty()){
                occurrenceTempStruct.withStationNotes(placeCoordinates);
            }
        }
        if(rec.value(DwcTerm.individualCount)!=null){
            occurrenceTempStruct.withNumberOfParts(rec.value(DwcTerm.individualCount));
        }
        return occurrenceTempStruct;
    }
    
    public static void main(String[] args) throws IOException, MetadataException{
//        new DwCArchiveParser(new File("D:/Repositories/GitHub/LifeWatch_Greece/DataServices-api/dwca-1.16.zip")).parseData();
        new DwCArchiveParser(new File("D:/Repositories/GitHub/LifeWatch_Greece/DataServices-api/dwca-zoobenthos_in_amvrakikos_wetlands-v1.17.zip")).parseData();
    }
    
}
