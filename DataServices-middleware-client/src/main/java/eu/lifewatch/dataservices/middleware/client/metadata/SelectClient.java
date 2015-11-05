package eu.lifewatch.dataservices.middleware.client.metadata;

import eu.lifewatch.dataservices.middleware.client.Commons;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * @author Yannis Marketakis (marketak 'at' ics 'dot' forth 'dot' gr)
 * @author Nikos Minadakis (minadakn 'at' ics 'dot' forth 'dot' gr)
 */
public class SelectClient {
    private static final Logger logger=Logger.getLogger(SelectClient.class);
    private static final String METADATA_CAT_GRAPHSPACE="http://www.ics.forth.gr/isl/lifewatch/metadata";
    
    private static void selectIncoming(){
        try{
            MetadataRepositorySelect_Service service = new MetadataRepositorySelect_Service();
            MetadataRepositorySelect port = service.getMetadataRepositorySelectPort();
            String resourceURI="http://localhost/microctreconstruction/reconstruction";
            List<IncomingNodeStruct> results=port.selectIncoming(resourceURI, METADATA_CAT_GRAPHSPACE);
            System.out.println("Found "+results.size()+" incoming nodes");
            results.forEach(result -> System.out.println(result.getSubject()+"\t"+
                                                         result.getSubjectName()+"\t"+
                                                         result.getSubjectType()+"\t"+
                                                         result.getPredicate()));
        }catch(Exception ex) {
            logger.error("An error occurred while getting the incoming nodes of a resource URI",ex);
        }
    }
    
    private static void selectOutgoing(){
        try{
            MetadataRepositorySelect_Service service = new MetadataRepositorySelect_Service();
            MetadataRepositorySelect port = service.getMetadataRepositorySelectPort();
            String resourceURI="http://localhost/microct/dataset";
            List<OutgoingNodeStruct> results=port.selectOutgoing(resourceURI, METADATA_CAT_GRAPHSPACE);
            System.out.println("Found "+results.size()+" outgoing nodes");
            results.forEach(result -> System.out.println(result.getObject()+"\t"+
                                                         result.getObjectName()+"\t"+
                                                         result.getObjectType()+"\t"+
                                                         result.getPredicate()));
        }catch(Exception ex) {
            logger.error("An error occurred while getting the outgoing nodes of a resource URI",ex);
        }
    }
    
    public static void main(String[] args){
        selectIncoming();
        Commons.printSeparator();
        selectOutgoing();
    }
}