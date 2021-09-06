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
    private static final String GRAPHSPACE_DIRECTORY="http://www.ics.forth.gr/isl/lifewatch/directory_v9_issue_1";
    
    public static void main(String[] args) throws QueryExecutionException{
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        DirectoryService dsManager=context.getBean(DirectoryService.class);
        
        List<DirectoryStruct> results=dsManager.searchDataset("", "", "http://www.lifewatchgreece.eu/entity/dataset/f97f5c65-9a82-436b-a751-fcd0e9be09cc", "", GRAPHSPACE_DIRECTORY);
        System.out.println("Results: "+results.size());
        for(DirectoryStruct directoryStruct : results){
            System.out.println(directoryStruct);
            System.out.println(directoryStruct.getAccessRightsURI());
            System.out.println(directoryStruct.getAccessRights());
            System.out.println(directoryStruct.getRightsHolderURI());
            System.out.println(directoryStruct.getRightsHolderName());
        }
    }
    

}
