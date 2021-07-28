package eu.lifewatch.core.impl;

import eu.lifewatch.common.Resources;
import eu.lifewatch.exception.QueryExecutionException;
import eu.lifewatch.exception.URIValidationException;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.apache.log4j.Logger;
import org.gbif.dwc.MetadataException;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

/** This class contains the resources that are necessary for harvesting the RAW contents of MEDOBIS IPT
 * and retrieve the most recent DWCA file to be included in the Directory and Metadata catalogue. 
 *
 * @author Yannis Marketakis (marketak 'at' ics 'dot' forth 'dot' gr)
 */
public class DwCAHarvester {
    private File iptResourcesFolder;
    private static final Logger log=Logger.getLogger(DwCAHarvester.class);
    
    public DwCAHarvester(String rootPath){
        this.iptResourcesFolder=new File(rootPath);
        log.info("Using IPT MEDOBIS resources root folder: "+this.iptResourcesFolder.getAbsolutePath());
    }
    
    public Collection<File> locateDwCaArchives(){
        Set<File> retFiles=new HashSet<>();
        File[] datasetFolders=this.iptResourcesFolder.listFiles();
        for(File datasetFolder : datasetFolders){
            log.info("Scanning dataset folder "+datasetFolder.getAbsolutePath());
            log.debug("Searching for file "+datasetFolder.getAbsolutePath()+"/"+Resources.MEDOBIS_FILE_RESOURCE_XML);
            File resourceFile=new File(datasetFolder.getAbsolutePath()+"/"+Resources.MEDOBIS_FILE_RESOURCE_XML);
            if(resourceFile.exists()){
                try{
                    String potentialDwCaFilename=this.identifyDwCaFilename(resourceFile);
                    File dwcaFile=new File(datasetFolder.getAbsolutePath()+"/"+potentialDwCaFilename);
                    if(dwcaFile.exists()){
                        retFiles.add(dwcaFile);
                    }else{
                        log.error("Cannot find the DWCA file "+datasetFolder.getAbsolutePath()+"/"+potentialDwCaFilename+". Skipping this dataset");
                    }
                }catch(IOException ex){
                    log.error("An error occured while parsing the contents of file "+datasetFolder.getAbsolutePath()+"/"+Resources.MEDOBIS_FILE_RESOURCE_XML);
                }
            }else{
                log.error("Cannot find the file "+datasetFolder.getAbsolutePath()+"/"+Resources.MEDOBIS_FILE_RESOURCE_XML+". Skipping this dataset");
            }
        }
        return retFiles;
    }
    
    private String identifyDwCaFilename(File file) throws IOException{
        Elements emlElementsVersion=Jsoup.parse(file, "UTF-8").getElementsByTag(Resources.EML_VERSION);
        if(emlElementsVersion!=null && !emlElementsVersion.isEmpty()){
            return Resources.DWCA_FILENAME_PREFIX+emlElementsVersion.get(0).text()+Resources.DWCA_FILE_EXTENSION;
        }else{
            return null;
        }
    }
    
    public static void main(String[] args) throws IOException, MetadataException, URIValidationException, QueryExecutionException{
        Collection<File> archives=new DwCAHarvester("D:/temp/ipt/resources_from_hcmr").locateDwCaArchives();
        System.out.println(archives.size());
        for(File file : archives){
            System.out.println(file.getAbsolutePath());
            new DwCArchiveParser(file).parseData();
        }
        
        
    }
}