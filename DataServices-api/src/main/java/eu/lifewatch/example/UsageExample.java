package eu.lifewatch.example;

import eu.lifewatch.service.impl.DirectoryService;
import eu.lifewatch.common.Resources;
import eu.lifewatch.core.model.DirectoryStruct;
import eu.lifewatch.core.model.Triple;
import eu.lifewatch.exception.QueryExecutionException;
import eu.lifewatch.exception.URIValidationException;
import java.util.List;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
  * This class is responsible for demonstrating the usage of the Directory Service.
 * In particular at fisrt we create some data, ingest them to the repository and exploit the 
 * DirectoryService API to retrieve them. Information about the virtuoso repository that 
 * is being used can be found at beans.xml file.
 * 
 * @author Nikos Minadakis (minadakn 'at' ics 'dot' forth 'dot' gr)
 * @author Yannis Marketakis (marketak 'at' ics 'dot' forth 'dot' gr)
 */
public class UsageExample {
    private DirectoryService dsManager;
    private static final String DEFAULT_GRAPHSPACE="http://www.ics.forth.gr/isl/lifewatch/directory/example";
    private static final String NAMESPACE_PREFIX="http://localhost/directory/";
    
    public UsageExample createDSManager(){
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        this.dsManager=context.getBean(DirectoryService.class);
        /*BUG(YM) for some weird reason this causes virtuoso to crash*/
//        try{
//            this.dsManager.deleteTriples(null, null, null, DEFAULT_GRAPHSPACE);  //remove everything
//        }catch(QueryExecutionException ex){
//            System.err.println(ex);
//        }
        return this;
    }
    
    public UsageExample insertSomeData(){
        DirectoryStruct struct1=new DirectoryStruct().withDatasetURI(NAMESPACE_PREFIX+"dataset1")
                    .withDatasetName("dataset 1")
                    .withDatasetID("dataset ID 1")
                    .withLocationURL(NAMESPACE_PREFIX+"location_URL_1")
                    .withImageURI(NAMESPACE_PREFIX+"image1")
                    .withImageTitle("image 1")
                    .withDatasetType("dataset type 1")
                    .withAccessMethod("access method 1")
                    .withAccessMethodURI(NAMESPACE_PREFIX+"access_method1")
                    .withParentDatasetURI(NAMESPACE_PREFIX+"parent_dataset_1")
                    .withParentDatasetName("parent dataset 1")
                    .withContributor(NAMESPACE_PREFIX+"contributor1", "contributor 1")
                    .withCuratorURI(NAMESPACE_PREFIX+"curator1")
                    .withCuratorName("curator 1")
                    .withOwnerURI(NAMESPACE_PREFIX+"owner1")
                    .withOwnerName("owner 1")
                    .withPublicationEventURI(NAMESPACE_PREFIX+"publication_event1")
                    .withPublicationDate("publication date 1")
                    .withPublisherURI(NAMESPACE_PREFIX+"publisher1")
                    .withPublisherName("publisher 1")
                    .withCreationEventURI(NAMESPACE_PREFIX+"creationEvent1")
                    .withCreationDate("creation date 1")
                    .withCreatorURI(NAMESPACE_PREFIX+"creator1")
                    .withCreatorName("creator 1")
                    .withAttributeAssignmentEventURI(NAMESPACE_PREFIX+"attribute_assignment1")
                    .withEmbargoPeriod("embargo period 1")
                    .withEmbargoState("embargo state 1")
                    .withKeeperURI(NAMESPACE_PREFIX+"keeper1")
                    .withKeeperName("keeper 1") 
                    .withAccessRightsURI(NAMESPACE_PREFIX+"accessRightsUri1")
                    .withAccessRights("access rights 1")
                    .withRightsHolderURI(NAMESPACE_PREFIX+"rightsHolder1")
                    .withRightsHolderName("rights holders 1")
                    .withContactPoint("contact point 1")
                    .withDescription("description 1");
           try{
            System.out.println("Inserting some data");
            this.dsManager.insertStruct(struct1,DEFAULT_GRAPHSPACE);
        }catch(QueryExecutionException | URIValidationException ex){
            System.err.println(ex);
        }
        return this;
    }
    
    public UsageExample searchDatasets(){
        this.printSeparator();
        String dName="dataset 1";
        String oName="owner 1";
        System.out.println("Searching for datasets with name \""+dName+"\" hosted by \""+oName+"\"");
        try{
            List<DirectoryStruct> structs=this.dsManager.searchDataset(dName, oName, "", "", DEFAULT_GRAPHSPACE);
            System.out.println("Found "+structs.size()+" datasets");
            for(DirectoryStruct struct : structs){
                System.out.println("\t"+struct.toString().replaceAll("\n", "\n\t"));
            }
        }catch(QueryExecutionException ex){
            System.err.println(ex);
        }
        return this;
    }
    
    public UsageExample searchResources(){
        this.printSeparator();
        String resource=NAMESPACE_PREFIX+"dataset1";
        System.out.println("Searching for resources with URI \""+resource+"\"");
        try{
            List<Triple> triples=this.dsManager.searchResource(resource, DEFAULT_GRAPHSPACE);
            System.out.println("Found "+triples.size()+" containing the resource");
            for(Triple triple : triples){
                System.out.println("\t"+triple);
            }
        }catch(QueryExecutionException ex){
            System.err.println(ex);
        }
        return this;
    }
    
    public UsageExample searchLiterals(){
        this.printSeparator();
        String literal="contact point 1";
        System.out.println("Searching for literals with value \""+literal+"\"");
        try{
            List<Triple> triples=this.dsManager.searchLiteral(literal, DEFAULT_GRAPHSPACE);
            System.out.println("Found "+triples.size()+" containing the literal value");
            for(Triple triple : triples){
                System.out.println("\t"+triple);
            }
        }catch(QueryExecutionException ex){
            System.err.println(ex);
        }
        return this;
    }
    
    public UsageExample updateSomething(){
        this.printSeparator();
        String originalResource=NAMESPACE_PREFIX+"image1";
        String updatedResource=NAMESPACE_PREFIX+"image_new_1";
        System.out.println("Updating the resource \""+originalResource+"\" to \""+updatedResource+"\"");
        try{
            this.dsManager.updateResource(originalResource, updatedResource, DEFAULT_GRAPHSPACE);
            System.out.println("Searching for the old and new resource");
            List<Triple> results=this.dsManager.searchResource(originalResource, DEFAULT_GRAPHSPACE);
            System.out.println("Number of results ("+originalResource+"): "+results.size());
            for(Triple triple : results)
                System.out.println("\t"+triple);
            results=this.dsManager.searchResource(updatedResource, DEFAULT_GRAPHSPACE);
            System.out.println("Number of results ("+updatedResource+"): "+results.size());
            for(Triple triple : results)
                System.out.println("\t"+triple);
        }catch(QueryExecutionException ex){
            System.err.println(ex);
        }
        originalResource="contact point 1";
        updatedResource="updated contact for point 1";
        System.out.println("Updating the literal \""+originalResource+"\" to \""+updatedResource+"\"");
        try{
            this.dsManager.updateLiteral(originalResource, updatedResource, DEFAULT_GRAPHSPACE);
            System.out.println("Searching for the old and new literal");
            List<Triple> results=this.dsManager.searchLiteral(originalResource, DEFAULT_GRAPHSPACE);
            System.out.println("Number of resutlts ("+originalResource+"): "+results.size());
            for(Triple triple : results)
                System.out.println("\t"+triple);
            results=this.dsManager.searchLiteral(updatedResource, DEFAULT_GRAPHSPACE);
            System.out.println("Number of resutlts ("+updatedResource+"): "+results.size());
            for(Triple triple : results)
                System.out.println("\t"+triple);
        }catch(QueryExecutionException ex){
            System.err.println(ex);
        }
        originalResource=Resources.hasNote;
        updatedResource=NAMESPACE_PREFIX+"has_the_description";
        System.out.println("Updating the property \""+originalResource+"\" to \""+updatedResource+"\"");
        try{
            this.dsManager.updateProperty(originalResource, updatedResource, DEFAULT_GRAPHSPACE);
            System.out.println("Check that the property has been correctly updated");
            List<Triple> results=this.dsManager.searchLiteral("description", DEFAULT_GRAPHSPACE);
            for(Triple triple : results)
                System.out.println("\t"+triple);
        }catch(QueryExecutionException ex){
            System.err.println(ex);
        }
        return this;
    }
    
    // Use with special care (deletions brake the links)
    public UsageExample deleteTriples(){
        this.printSeparator();
        System.out.println("Delete all triples containing the predicate "+Resources.isDepictedBy);
        try{
            this.dsManager.deleteTriplesHavingProperty(Resources.wasAttributedBy, DEFAULT_GRAPHSPACE);
            System.out.println("Check that the property has been removed. The following dataset doesn't contain it");
            List<DirectoryStruct> datasets=this.dsManager.searchDataset("dataset 1", "owner 1",NAMESPACE_PREFIX+"dataset1","dataset type 1",DEFAULT_GRAPHSPACE);
            System.out.println("\t"+datasets.get(0).toString().replaceAll("\n", "\n\t"));
        }catch(QueryExecutionException ex){
            System.err.println(ex);
        }
        System.out.println("Delete all triples containing the literal \"keeper 1\"");
        try{
            this.dsManager.deleteTriplesHavingLiteral("description", DEFAULT_GRAPHSPACE);
            System.out.println("Searching for triples containing the deleted literal");
            List<Triple> results=this.dsManager.searchLiteral("", DEFAULT_GRAPHSPACE);
            System.out.println("Number of triples containing the deleted literal: "+results.size());
        }catch(QueryExecutionException ex){
            System.err.println(ex);
        }
        String deletedResource=NAMESPACE_PREFIX+"image_new_1";
        System.out.println("Delete all triples containing the resource \""+deletedResource+"\"");
        try{
            this.dsManager.deleteTriplesHavingResource(deletedResource, DEFAULT_GRAPHSPACE);
            System.out.println("Searcing for triples containing the deleted resource");
            List<Triple> results=this.dsManager.searchResource(deletedResource, DEFAULT_GRAPHSPACE);
            System.out.println("Number of triples containing the deleted resource: "+results.size());
        }catch(QueryExecutionException ex){
            System.err.println(ex);
        }
        String deletedPredicate=Resources.isIdentifiedBy;
        deletedResource=NAMESPACE_PREFIX+"owner1";
        System.out.println("Deleting all triples describing the appellation of \""+deletedResource+"\"");
        try{
            this.dsManager.deleteTriples(deletedResource, deletedPredicate, null, DEFAULT_GRAPHSPACE);
            System.out.println("Check that only specfiic triples have been removed. Get all the triples involving \""+deletedResource+"\"");
            List<Triple> results=this.dsManager.searchResource(deletedResource, DEFAULT_GRAPHSPACE);
            for(Triple triple : results)
                System.out.println("\t"+triple);
        }catch(QueryExecutionException ex){
            System.err.println(ex);
        }
        return this;
    }
    
    private void printSeparator(){
        System.out.println("-----------------------------------------------");
    }
    
    public static void main(String[] args){
        new UsageExample().createDSManager()
                          .insertSomeData()
                          .searchDatasets()
                          .searchResources()
                          .searchLiterals()
                          .updateSomething();
    }
}