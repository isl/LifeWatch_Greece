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
    private static final String defaultGraphspace="http://www.ics.forth.gr/isl/directoryService/example";
    
    public UsageExample createDSManager(){
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        this.dsManager=context.getBean(DirectoryService.class);
        try{
            this.dsManager.deleteTriples(null, null, null, defaultGraphspace);  //remove everything
        }catch(QueryExecutionException ex){
            System.err.println(ex);
        }
        return this;
    }
    
    public UsageExample insertSomeData(){
        DirectoryStruct struct1=new DirectoryStruct().withDatasetURI("http://localhost/dataset1")
                    .withDatasetName("dataset 1")
                    .withContributor("http://localhost/contributor1", "contributor 1")
                    .withKeeperURI("http://localhost/keeper1")
                    .withKeeperName("keeper 1")
                    .withAccessRightsURI("http://localhost/accessRightsUri1")
                    .withAccessRights("access rights 1")
                    .withRightsHolderURI("http://localhost/rightsHolder1")
                    .withRightsHolderName("rights holders 1")
                    .withAccessMethod("an access method")
                    .withCuratorURI("http://localhost/curator1")
                    .withCuratorName("curator 1")
                    .withCreationEventURI("http://localhost/creationEvent1")
                    .withCreatorURI("http://localhost/creator1")
                    .withCreatorName("creator 1")
                 //   .withTimespan("a timespan")
                    .withOwnerURI("http://localhost/owner1")
                    .withOwnerName("owner 1")
                    .withPublisherURI("http://localhost/publisher1")
                    .withPublisherName("publisher 1")
                   // .withNote("a note")
                  //  .withLocation("a location")
                    .withContactPoint("a contact point");
        DirectoryStruct struct2=new DirectoryStruct().withDatasetURI("http://localhost/dataset2")
                    .withDatasetName("dataset 2")
                    .withContributor("http://localhost/contributor1", "contributor 1")
                    .withContributor("http://localhost/contributor2", "contributor 2")
                    .withContributor("http://localhost/contributor3", "contributor 3")
                    .withKeeperURI("http://localhost/keeper2")
                    .withKeeperName("keeper 2")
                    .withAccessRightsURI("http://localhost/accessRightsUri2")
                    .withAccessRights("access rights 2")
                    .withRightsHolderURI("http://localhost/rightsHolder2")
                    .withRightsHolderName("rights holders 2")
                    .withAccessMethod("another access method")
                    .withCuratorURI("http://localhost/curator2")
                    .withCuratorName("curator 2")
                    .withCreationEventURI("http://localhost/creationEvent2")
                    .withCreatorURI("http://localhost/creator2")
                    .withCreatorName("creator 2")
                   // .withTimespan("another timespan")
                    .withOwnerURI("http://localhost/owner2")
                    .withOwnerName("owner 2")
                    .withPublisherURI("http://localhost/publisher2")
                    .withPublisherName("publisher 2")
                   // .withNote("another note")
                   // .withLocation("another location")
                    .withContactPoint("another contact point");

        try{
            System.out.println("Inserting some data");
            this.dsManager.insertStruct(struct1,defaultGraphspace);
            this.dsManager.insertStruct(struct2,defaultGraphspace);
        }catch(QueryExecutionException | URIValidationException ex){
            System.err.println(ex);
        }
        return this;
    }
    
    public UsageExample searchDatasets(){
        this.printSeparator();
        String dName="dataset 1";
        String kName="keeper 1";
        System.out.println("Searching for datasets with name \""+dName+"\" hosted by \""+kName+"\"");
        try{
            List<DirectoryStruct> structs=this.dsManager.searchDataset("", "", "","","http://Directory_100");
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
        String resource="http://localhost/dataset1";
        System.out.println("Searching for resources with URI \""+resource+"\"");
        try{
            List<Triple> triples=this.dsManager.searchResource(resource, defaultGraphspace);
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
        String literal="license";
        System.out.println("Searching for literals with value \""+literal+"\"");
        try{
            List<Triple> triples=this.dsManager.searchLiteral(literal, defaultGraphspace);
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
        String originalResource="http://localhost/dataset2";
        String updatedResource="http://localhost/dataset_new_2";
        System.out.println("Updating the resource \""+originalResource+"\" to \""+updatedResource+"\"");
        try{
            this.dsManager.updateResource(originalResource, updatedResource, defaultGraphspace);
            System.out.println("Searching for the old and new resource");
            List<Triple> results=this.dsManager.searchResource(originalResource, defaultGraphspace);
            System.out.println("Number of results ("+originalResource+"): "+results.size());
            for(Triple triple : results)
                System.out.println("\t"+triple);
            results=this.dsManager.searchResource(updatedResource, defaultGraphspace);
            System.out.println("Number of results ("+updatedResource+"): "+results.size());
            for(Triple triple : results)
                System.out.println("\t"+triple);
        }catch(QueryExecutionException ex){
            System.err.println(ex);
        }
        originalResource="a note";
        updatedResource="an updated note";
        System.out.println("Updating the literal \""+originalResource+"\" to \""+updatedResource+"\"");
        try{
            this.dsManager.updateLiteral(originalResource, updatedResource, defaultGraphspace);
            System.out.println("Searching for the old and new literal");
            List<Triple> results=this.dsManager.searchLiteral(originalResource, defaultGraphspace);
            System.out.println("Number of resutlts ("+originalResource+"): "+results.size());
            for(Triple triple : results)
                System.out.println("\t"+triple);
            results=this.dsManager.searchLiteral(updatedResource, defaultGraphspace);
            System.out.println("Number of resutlts ("+updatedResource+"): "+results.size());
            for(Triple triple : results)
                System.out.println("\t"+triple);
        }catch(QueryExecutionException ex){
            System.err.println(ex);
        }
        originalResource=Resources.isLocatedAt;
        updatedResource="http://localhost/the_location_can_be_found_at";
        System.out.println("Updating the property \""+originalResource+"\" to \""+updatedResource+"\"");
        try{
            this.dsManager.updateProperty(originalResource, updatedResource, defaultGraphspace);
            System.out.println("Check that the property has been correctly updated");
            List<Triple> results=this.dsManager.searchLiteral("a location", defaultGraphspace);
            for(Triple triple : results)
                System.out.println("\t"+triple);
        }catch(QueryExecutionException ex){
            System.err.println(ex);
        }
        return this;
    }
    
    public UsageExample deleteTriples(){
        this.printSeparator();
        System.out.println("Delete all triples containing the predicate hasAccessMethod");
        try{
            this.dsManager.deleteTriplesHavingProperty(Resources.hasAccessMethod, defaultGraphspace);
            System.out.println("Check that the property has been removed. The following dataset doesn't contain it");
            List<DirectoryStruct> datasets=this.dsManager.searchDataset("dataset 1", "keeper 1", defaultGraphspace);
            System.out.println("\t"+datasets.get(0).toString().replaceAll("\n", "\n\t"));
        }catch(QueryExecutionException ex){
            System.err.println(ex);
        }
        System.out.println("Delete all triples containing the literal \"an updated note\"");
        try{
            this.dsManager.deleteTriplesHavingLiteral("an updated note", defaultGraphspace);
            System.out.println("Searching for triples containing the deleted literal");
            List<Triple> results=this.dsManager.searchLiteral("an updated not", defaultGraphspace);
            System.out.println("Number of triples containing the deleted literal: "+results.size());
        }catch(QueryExecutionException ex){
            System.err.println(ex);
        }
        String deletedResource="http://localhost/curator1";
        System.out.println("Delete all triples containing the resource \""+deletedResource+"\"");
        try{
            this.dsManager.deleteTriplesHavingResource(deletedResource, defaultGraphspace);
            System.out.println("Searcing for triples containing the deleted resource");
            List<Triple> results=this.dsManager.searchResource(deletedResource, defaultGraphspace);
            System.out.println("Number of triples containing the deleted resource: "+results.size());
        }catch(QueryExecutionException ex){
            System.err.println(ex);
        }
        String deletedPredicate=Resources.isIdentifiedBy;
        deletedResource="http://localhost/owner1";
        System.out.println("Deleting all triples describing the appellation of \""+deletedResource+"\"");
        try{
            this.dsManager.deleteTriples(deletedResource, deletedPredicate, null, defaultGraphspace);
            System.out.println("Check that only specfiic triples have been removed. Get all the triples involving \""+deletedResource+"\"");
            List<Triple> results=this.dsManager.searchResource(deletedResource, defaultGraphspace);
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
         //                 .insertSomeData();
                         .searchDatasets();
//                          .searchResources()
//                          .searchLiterals()
//                          .updateSomething()
//                          .deleteTriples();
    }
}