package eu.lifewatch.dataservices.middleware.client.directory;

import eu.lifewatch.dataservices.middleware.client.Commons;
import org.apache.log4j.Logger;

/**
 * @author Yannis Marketakis (marketak 'at' ics 'dot' forth 'dot' gr)
 * @author Nikos Minadakis (minadakn 'at' ics 'dot' forth 'dot' gr)
 */
public class DeleteClient {
    private static final Logger logger=Logger.getLogger(DeleteClient.class);
    
    private static void deleteTriple(){
        try{
            DirectoryServiceDelete_Service service = new DirectoryServiceDelete_Service();
            DirectoryServiceDelete port = service.getDirectoryServiceDeletePort();
            java.lang.String subject = "http://localhost/directory/keeper1";
            java.lang.String predicate = "http://www.w3.org/1999/02/22-rdf-syntax-ns#type";
            java.lang.String object = "http://www.cidoc-crm.org/cidoc-crm/E39_Actor";
            java.lang.String repositoryGraph = "http://www.ics.forth.gr/isl/lifewatch/directory";
            System.out.println("Removing triple from the directory with the following details: "+
                               "\tSubject: "+subject+
                               "\tPredicate: "+predicate+
                               "\tObject: "+object+
                               "\tGraphspace: "+repositoryGraph);
            boolean result = port.deleteTriples(subject, predicate, object, repositoryGraph);
            System.out.println("Deletion Result= "+result);
        }catch(Exception ex) {
            logger.error("An error occured while deleting a triple",ex);
        }
    }
    
    private static void deleteTriplesHavingLiteral(){
        try{
            DirectoryServiceDelete_Service service = new DirectoryServiceDelete_Service();
            DirectoryServiceDelete port = service.getDirectoryServiceDeletePort();
            String literalValue = "image";
            String repositoryGraph = "http://www.ics.forth.gr/isl/lifewatch/directory";            
            System.out.println("Removing the triples having a literal from the directory with the following details: "+
                               "\tLiteral value: "+literalValue+
                               "\tGraphspace: "+repositoryGraph);
            boolean result = port.deleteTriplesHavingLiteral(literalValue, repositoryGraph);
            System.out.println("Deletion Result= "+result);
        }catch(Exception ex) {
            logger.error("An error occured while deleting the triples having a particular literal value",ex);
        }
    }
    
    private static void deleteTriplesHavingProperty(){
        try{
            DirectoryServiceDelete_Service service = new DirectoryServiceDelete_Service();
            DirectoryServiceDelete port = service.getDirectoryServiceDeletePort();
            String property = "http://www.cidoc-crm.org/cidoc-crm/P76_has_contact_point";
            String repositoryGraph = "http://www.ics.forth.gr/isl/lifewatch/directory";
            System.out.println("Removing the triples having a property from the directory with the following details: "+
                               "\tProperty: "+property+
                               "\tGraphspace: "+repositoryGraph);
            boolean result = port.deleteTriplesHavingProperty(property, repositoryGraph);
            System.out.println("Deletion Result= "+result);
        }catch(Exception ex) {
            logger.error("An error occured while deleting the triples having a particular property",ex);
        }
    }
    
    private static void deleteTriplesHavingResource(){
        try{
            DirectoryServiceDelete_Service service = new DirectoryServiceDelete_Service();
            DirectoryServiceDelete port = service.getDirectoryServiceDeletePort();
            String resourceURI = "http://localhost/directory/access_method1";
            String repositoryGraph = "http://www.ics.forth.gr/isl/lifewatch/directory";
            System.out.println("Removing the triples having a resource from the directory with the following details: "+
                               "\tResource: "+resourceURI+
                               "\tGraphspace: "+repositoryGraph);
            boolean result = port.deleteTriplesHavingResource(resourceURI, repositoryGraph);
            System.out.println("Deletion Results= "+result);
        }catch(Exception ex) {
            logger.error("An error occured while deleting the triples having a particular resource",ex);
        }
    }
    
    public static void main(String[] args){
        deleteTriple();
        Commons.printSeparator();
        deleteTriplesHavingLiteral();
        Commons.printSeparator();
        deleteTriplesHavingProperty();
        Commons.printSeparator();
        deleteTriplesHavingResource();
    }
}