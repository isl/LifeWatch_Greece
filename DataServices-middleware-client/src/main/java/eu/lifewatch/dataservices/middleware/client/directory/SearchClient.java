package eu.lifewatch.dataservices.middleware.client.directory;

import eu.lifewatch.dataservices.middleware.client.Commons;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * @author Yannis Marketakis (marketak 'at' ics 'dot' forth 'dot' gr)
 * @author Nikos Minadakis (minadakn 'at' ics 'dot' forth 'dot' gr)
 */
public class SearchClient {
    private static final Logger logger=Logger.getLogger(SearchClient.class);
    private static final String DIRECTORY_GRAPHSPACE = "http://www.ics.forth.gr/isl/lifewatch/directory";

    private static void searchDataset(){
        try{
            DirectoryServiceSearch_Service service = new DirectoryServiceSearch_Service();
            DirectoryServiceSearch port = service.getDirectoryServiceSearchPort();
            String datasetName = "dataset 1";
            String ownerName = "owner 1";
            String datasetURI = "http://localhost/directory/dataset1";
            String datasetType = "dataset type 1";
            System.out.println("Searching for datasets with the following details: "+
                               "\tDataset Name: "+datasetName+
                               "\tOwner Name: "+ownerName+
                               "\tDataset URI: "+datasetURI+
                               "\tDataset Type: "+datasetType+
                               "\tGraphspace: "+DIRECTORY_GRAPHSPACE);
            List<DirectoryStruct> results = port.searchDataset(datasetName, ownerName, datasetURI, datasetType, DIRECTORY_GRAPHSPACE);
            System.out.println("Found "+results.size()+" datasets");
            results.forEach(result -> System.out.println(result.getDatasetID()+
                                                         "\t"+result.getDatasetName()+
                                                         "\t"+result.getOwnerName()+
                                                         "\t"+result.getOwnerURI()+
                                                         "\t"+result.getCuratorName()+
                                                         "\t"+result.getCuratorURI()+
                                                         "\t"+result.getCreationDate()+
                                                         "\t"+result.getCreationEventURI()+"..."));
        }catch(Exception ex) {
            logger.error("An error occured while searching for datasets",ex);
        }
    }
    
    private static void searchDatasetWithinRange(){
        try{ 
            DirectoryServiceSearch_Service service = new DirectoryServiceSearch_Service();
            DirectoryServiceSearch port = service.getDirectoryServiceSearchPort();
            String datasetName = "dataset";
            String ownerName = "owner";
            String datasetURI = "http://localhost/directory/dataset1";
            String datasetType = "dataset type";
            int limit = 1;
            int offset = 0;
            System.out.println("Searching for datasets with the following details: "+
                               "\tDataset Name: "+datasetName+
                               "\tOwner Name: "+ownerName+
                               "\tDataset URI: "+datasetURI+
                               "\tDataset Type: "+datasetType+
                               "\tLimit: "+limit+
                               "\tOffset: "+offset+
                               "\tGraphspace: "+DIRECTORY_GRAPHSPACE);
            List<DirectoryStruct> results = port.searchDatasetWithinRange(datasetName, ownerName, datasetURI, datasetType, limit, offset, DIRECTORY_GRAPHSPACE);
            System.out.println("Found "+results.size()+" datasets");
            results.forEach(result -> System.out.println(result.getDatasetID()+
                                                         "\t"+result.getDatasetName()+
                                                         "\t"+result.getOwnerName()+
                                                         "\t"+result.getOwnerURI()+
                                                         "\t"+result.getCuratorName()+
                                                         "\t"+result.getCuratorURI()+
                                                         "\t"+result.getCreationDate()+
                                                         "\t"+result.getCreationEventURI()+"..."));
        }catch(Exception ex) {
            logger.error("An error occured while searching for datasets",ex);
        }
    }
    
    private static void searchResource(){
        try{
            DirectoryServiceSearch_Service service = new DirectoryServiceSearch_Service();
            DirectoryServiceSearch port = service.getDirectoryServiceSearchPort();
            java.lang.String resourceURI = "http://localhost/directory/dataset1";
            java.lang.String repositoryGraph = "http://www.ics.forth.gr/isl/lifewatch/directory";
            System.out.println("Searching for resources with the following details: "+
                               "\tResource URI: "+resourceURI+
                               "\tGraphspace: "+repositoryGraph);
            List<Triple> results = port.searchResource(resourceURI, repositoryGraph);
            System.out.println("Found "+results.size()+" triples containing the resource");
            results.forEach(result -> System.out.println("Subject: "+result.getSubject()+
                                                         "\tPredicate: "+result.getPredicate()+
                                                         "\tObject: "+result.getObject()));
        } catch (Exception ex) {
            logger.error("An error occured while searching for triples containing a resource",ex);
        }
    }
    
    private static void searchLiteral(){
        try{ 
            DirectoryServiceSearch_Service service = new DirectoryServiceSearch_Service();
            DirectoryServiceSearch port = service.getDirectoryServiceSearchPort();
            String literalValue = "owner";
            String repositoryGraph = "http://www.ics.forth.gr/isl/lifewatch/directory";
            System.out.println("Searching for literals with the following details: "+
                               "\tResource URI: "+literalValue+
                               "\tGraphspace: "+repositoryGraph);
            List<Triple> results = port.searchLiteral(literalValue, repositoryGraph);
            System.out.println("Found "+results.size()+" triples containing the literal");
            results.forEach(result -> System.out.println("Subject: "+result.getSubject()+
                                                         "\tPredicate: "+result.getPredicate()+
                                                         "\tObject: "+result.getObject()));
        } catch (Exception ex) {
            logger.error("An error occured while searching for triples containing a literal",ex);
        }
    }
    
    public static void main(String[] args){
        searchDataset();
        Commons.printSeparator();
        searchDatasetWithinRange();
        Commons.printSeparator();
        searchResource();
        Commons.printSeparator();
        searchLiteral();
    }
}