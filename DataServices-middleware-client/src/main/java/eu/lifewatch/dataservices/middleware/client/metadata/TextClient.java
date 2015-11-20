package eu.lifewatch.dataservices.middleware.client.metadata;

import org.apache.log4j.Logger;

/**
 * @author Yannis Marketakis (marketak 'at' ics 'dot' forth 'dot' gr)
 * @author Nikos Minadakis (minadakn 'at' ics 'dot' forth 'dot' gr)
 */
public class TextClient {
    private static final Logger logger=Logger.getLogger(TextClient.class);
    private static final String METADATA_CAT_GRAPHSPACE="http://www.ics.forth.gr/isl/lifewatch/metadata";
    
    private static void produceText(){
        try{
            MetadataRepositoryText_Service service = new MetadataRepositoryText_Service();
            MetadataRepositoryText port = service.getMetadataRepositoryTextPort();
            String speciesName="species";
            String browseURL = "http://localhost/";
            System.out.println("Producing text (story) with the following details: "+
                               "\tSpecies Name: "+speciesName+
                               "\tBrowse URL: "+browseURL+
                               "\tGraphspace: "+METADATA_CAT_GRAPHSPACE);
            String result = port.produceText(speciesName, browseURL, METADATA_CAT_GRAPHSPACE);
            System.out.println("Result: "+result);
        } catch (Exception ex) {
            logger.error("An error occured while producing text (story)",ex);
        }
    }
    
    public static void main(String[] args){
        produceText();
    }
}