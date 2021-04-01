package eu.lifewatch.core.impl;

import eu.lifewatch.common.Resources;
import eu.lifewatch.core.model.OccurrenceStruct;
import eu.lifewatch.core.model.ScientificNamingStruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import org.apache.log4j.Logger;
import org.gbif.dwc.Archive;
import org.gbif.dwc.DwcFiles;
import org.gbif.dwc.MetadataException;
import org.gbif.dwc.record.Record;
import org.gbif.dwc.record.StarRecord;
import org.gbif.dwc.terms.DwcTerm;
import org.gbif.dwc.terms.GbifTerm;
import org.gbif.dwc.terms.Term;

/**
 * @author Yannis Marketakis (marketak 'at' ics 'dot' forth 'dot' gr)
 */
public class DwCArchiveParser {
    static Logger log=Logger.getLogger(DwCArchiveParser.class);
    private Archive dwcArchive;
    private String datasetUUID;
    private String datasetURI;
    
    public DwCArchiveParser(File archive) throws IOException{
        Path myArchiveFile = Paths.get(archive.getAbsolutePath());
        Path extractToFolder = Paths.get("arch");
        this.dwcArchive = DwcFiles.fromCompressed(myArchiveFile, extractToFolder);
        this.datasetUUID=UUID.randomUUID().toString();
        this.datasetURI=Resources.defaultNamespaceForURIs+"/dataset/"+this.datasetUUID;
    } 
    
    public void parseData() throws IOException, MetadataException{

        log.info("Archive rowtype: " + this.dwcArchive.getCore().getRowType() + ", "+ this.dwcArchive.getExtensions().size() + " extension(s)");
    
        switch(this.dwcArchive.getCore().getRowType().simpleName()){
            case "Occurrence":
                    parseOccurrenceArchive(this.dwcArchive);
                    break;
            default:
                log.error("No parser for "+this.dwcArchive.getCore().getRowType());     
        }

    }
    
    
    
    private void parseOccurrenceArchive(Archive dwcArchive){
        for (StarRecord rec : dwcArchive) {
            OccurrenceStruct occurenceStruct=new OccurrenceStruct().withDatasetURI(this.datasetURI)
                    .withDatasetTitle(rec.core().value(DwcTerm.collectionCode))
                    .withOccurrenceEventURI(Resources.defaultUrlPrefix+"/occurrence/"+rec.core().value(DwcTerm.occurrenceID))
                    .withOccurrenceEvent(rec.core().value(DwcTerm.occurrenceID))
                    .withTimeSpan(rec.core().value(DwcTerm.eventDate))
                    .withLongitude(rec.core().value(DwcTerm.decimalLongitude))
                    .withLatitude(rec.core().value(DwcTerm.decimalLatitude))
                    .withSpeciesURI(rec.core().value(DwcTerm.scientificNameID))
                    .withSpeciesName(rec.core().value(DwcTerm.scientificName));
            System.out.println(occurenceStruct);
            System.out.println(occurenceStruct.toNtriples());
            break;
            
            
//          System.out.println(String.format("%s: %s", rec.core().id(), rec.core().value(DwcTerm.scientificName)));
//          if (rec.hasExtension(GbifTerm.VernacularName)) {
//            for (Record extRec : rec.extension(GbifTerm.VernacularName)) {
//              System.out.println(" - " + extRec.value(DwcTerm.vernacularName));
//            }
//          }
        }
    }
    
    public static void main(String[] args) throws IOException, MetadataException{
        new DwCArchiveParser(new File("D:/Repositories/GitHub/LifeWatch_Greece/DataServices-api/dwca-1.16.zip")).parseData();
    }
    
}
