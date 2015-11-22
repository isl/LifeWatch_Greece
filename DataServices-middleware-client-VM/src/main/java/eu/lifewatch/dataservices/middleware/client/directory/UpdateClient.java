package eu.lifewatch.dataservices.middleware.client.directory;

import eu.lifewatch.dataservices.middleware.client.Commons;
import org.apache.log4j.Logger;

/**
 * @author Yannis Marketakis (marketak 'at' ics 'dot' forth 'dot' gr)
 * @author Nikos Minadakis (minadakn 'at' ics 'dot' forth 'dot' gr)
 */
public class UpdateClient {
    private static final Logger logger=Logger.getLogger(UpdateClient.class);
    
    private static void updateLiteral(){
        try{
            DirectoryServiceUpdate_Service service = new DirectoryServiceUpdate_Service();
            DirectoryServiceUpdate port = service.getDirectoryServiceUpdatePort();
            String originalLiteral = "image title new";
            String newLiteral = "an updated and new image title";
            String repositoryGraph = "http://www.ics.forth.gr/isl/lifewatch/directory";
            System.out.println("Updating the literal values in the directory with the following details: "+
                               "\tOriginal Literal value: "+originalLiteral+
                               "\tNew Literal value: "+newLiteral+
                               "\tGraphspace: "+repositoryGraph);
            boolean result = port.updateLiteral(originalLiteral, newLiteral, repositoryGraph);
            System.out.println("Update Result= "+result);
        } catch (Exception ex) {
            logger.error("An error occured while updating literal values",ex);
        }
    }
    
    private static void updateProperty(){
        try{
            DirectoryServiceUpdate_Service service = new DirectoryServiceUpdate_Service();
            DirectoryServiceUpdate port = service.getDirectoryServiceUpdatePort();
            java.lang.String originalProperty = "http://www.cidoc-crm.org/cidoc-crm/P4_has_timespan";
            java.lang.String newProperty = "http://www.cidoc-crm.org/cidoc-crm/P4_has_updated_timespan";
            java.lang.String repositoryGraph = "http://www.ics.forth.gr/isl/lifewatch/directory";
            System.out.println("Updating the property in the directory with the following details: "+
                               "\tOriginal Property: "+originalProperty+
                               "\tNew Property: "+newProperty+
                               "\tGraphspace: "+repositoryGraph);
            boolean result = port.updateProperty(originalProperty, newProperty, repositoryGraph);
            System.out.println("Update Result= "+result);
        } catch (Exception ex) {
            logger.error("An error occured while updating literal values",ex);
        }
    }
    
    private static void updateResource(){
        try{
            DirectoryServiceUpdate_Service service = new DirectoryServiceUpdate_Service();
            DirectoryServiceUpdate port = service.getDirectoryServiceUpdatePort();
            String originalResource = "http://localhost/directory/publication_event_new";
            String newResource = "http://localhost/directory/publication_event_updated_and_new";
            String repositoryGraph = "http://www.ics.forth.gr/isl/lifewatch/directory";
            System.out.println("Updating the resource in the directory with the following details: "+
                               "\tOriginal Resource: "+originalResource+
                               "\tNew Resource: "+newResource+
                               "\tGraphspace: "+repositoryGraph);
            boolean result = port.updateResource(originalResource, newResource, repositoryGraph);
            System.out.println("Update Result= "+result);
        } catch (Exception ex) {
            logger.error("An error occured while updating literal values",ex);
        }
    }
    
    public static void main(String[] args){
        updateLiteral();
        Commons.printSeparator();
        updateProperty();
        Commons.printSeparator();
        updateResource();
    }
}