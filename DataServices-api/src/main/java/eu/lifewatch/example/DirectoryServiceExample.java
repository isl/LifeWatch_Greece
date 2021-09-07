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
    private static final String GRAPHSPACE_DIRECTORY="http://www.ics.forth.gr/isl/lifewatch/directory_v11";
    
    public static void main(String[] args) throws QueryExecutionException{
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        DirectoryService dsManager=context.getBean(DirectoryService.class);
        
        List<DirectoryStruct> results=dsManager.searchDataset("", "", "http://www.lifewatchgreece.eu/entity/dataset/5f04cc93-43ce-4ee2-8fe7-93a0bf5b8702", "", -1, -1, GRAPHSPACE_DIRECTORY);
        System.out.println("Results: "+results.size());
        for(DirectoryStruct directoryStruct : results){
            System.out.println(directoryStruct);
            System.out.println(directoryStruct.getContributorNames());
        }
    }
    

}
