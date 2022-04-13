package eu.lifewatch.example;

import eu.lifewatch.core.model.DirectoryStruct;
import eu.lifewatch.exception.QueryExecutionException;
import eu.lifewatch.service.impl.DirectoryService;
import java.util.List;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author Yannis Marketakis (marketak 'at' ics 'dot' forth 'dot' gr)
 */
public class DirectoryServiceExample {
    private static final String GRAPHSPACE_DIRECTORY="http://www.ics.forth.gr/isl/lifewatch/directory";
    
    public static void main(String[] args) throws QueryExecutionException{
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        DirectoryService dsManager=context.getBean(DirectoryService.class);
        
        List<DirectoryStruct> results=dsManager.searchDataset("", "", "", "","","2010-04-04","","Mollusca", -1, -1, GRAPHSPACE_DIRECTORY);
        System.out.println("Results: "+results.size());
        for(DirectoryStruct directoryStruct : results){
//            System.out.println(directoryStruct);
//            System.out.println(directoryStruct.getContributorNames());
            System.out.println(directoryStruct.getGeographicCoverage());
            System.out.println(directoryStruct.getTemporalCoverage());
            System.out.println(directoryStruct.getTaxonomicCoverage());
        }
    }
    

}
