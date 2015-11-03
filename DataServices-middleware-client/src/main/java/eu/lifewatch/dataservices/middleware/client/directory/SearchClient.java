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

    private static void searchDataset(){
        try{
            DirectoryServiceSearch_Service service = new DirectoryServiceSearch_Service();
            DirectoryServiceSearch port = service.getDirectoryServiceSearchPort();
            String datasetName = "dataset 1";
            String ownerName = "owner 1";
            String datasetURI = "http://localhost/directory/dataset1";
            String datasetType = "dataset type 1";
            String repositoryGraph = "http://www.ics.forth.gr/isl/lifewatch/directory";
            System.out.println("Searching for datasets with the following details: "+
                               "\tDataset Name: "+datasetName+
                               "\tOwner Name: "+ownerName+
                               "\tDataset URI: "+datasetURI+
                               "\tDataset Type: "+datasetType+
                               "\tGraphspace: "+repositoryGraph);
            List<DirectoryStruct> results = port.searchDataset(datasetName, ownerName, datasetURI, datasetType, repositoryGraph);
            System.out.println("Found "+results.size()+" datasets");
            for(DirectoryStruct struct : results){
                System.out.println(struct.getDatasetID()+
                                   "\t"+struct.getDatasetName()+
                                   "\t"+struct.getOwnerName()+
                                   "\t"+struct.getOwnerURI()+
                                   "\t"+struct.getCuratorName()+
                                   "\t"+struct.getCuratorURI()+
                                   "\t"+struct.getCreationDate()+
                                   "\t"+struct.getCreationEventURI()+"...");
            }
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
            String repositoryGraph = "http://www.ics.forth.gr/isl/lifewatch/directory";
            System.out.println("Searching for datasets with the following details: "+
                               "\tDataset Name: "+datasetName+
                               "\tOwner Name: "+ownerName+
                               "\tDataset URI: "+datasetURI+
                               "\tDataset Type: "+datasetType+
                               "\tLimit: "+limit+
                               "\tOffset: "+offset+
                               "\tGraphspace: "+repositoryGraph);
            List<DirectoryStruct> results = port.searchDatasetWithinRange(datasetName, ownerName, datasetURI, datasetType, limit, offset, repositoryGraph);
            System.out.println("Found "+results.size()+" datasets");
            for(DirectoryStruct struct : results){
                System.out.println(struct.getDatasetID()+
                                   "\t"+struct.getDatasetName()+
                                   "\t"+struct.getOwnerName()+
                                   "\t"+struct.getOwnerURI()+
                                   "\t"+struct.getCuratorName()+
                                   "\t"+struct.getCuratorURI()+
                                   "\t"+struct.getCreationDate()+
                                   "\t"+struct.getCreationEventURI()+"...");
            }
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
            for(Triple triple : results){
                System.out.println("Subject: "+triple.getSubject()+
                                   "\tPredicate: "+triple.getPredicate()+
                                   "\tObject: "+triple.getObject());
            }
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
            for(Triple triple : results){
                System.out.println("Subject: "+triple.getSubject()+
                                   "\tPredicate: "+triple.getPredicate()+
                                   "\tObject: "+triple.getObject());
            }
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