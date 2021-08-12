package eu.lifewatch.example;

import eu.lifewatch.core.model.MicroCTScanningStruct;
import eu.lifewatch.core.model.MicroCTSpecimenStruct;
import eu.lifewatch.core.model.TaxonomyStruct;
import eu.lifewatch.exception.QueryExecutionException;
import eu.lifewatch.service.impl.MetadataRepositoryService;
import java.util.List;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author Yannis Marketakis (marketak 'at' ics 'dot' forth 'dot' gr)
 */
public class MetadataCatalogExample {
//    private static final String GRAPHSPACE_METADATA="http://www.ics.forth.gr/isl/lifewatch/metadata";
//    private static final String GRAPHSPACE_METADATA="http://www.ics.forth.gr/isl/lifewatch/metadata_mct";
    private static final String GRAPHSPACE_METADATA="http://www.ics.forth.gr/isl/lifewatch/metadata_mctscan";
    
    public static void main(String[] args) throws QueryExecutionException{
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        MetadataRepositoryService mrManager=context.getBean(MetadataRepositoryService.class);
        
//        List<TaxonomyStruct> results=mrManager.searchTaxonomy("", "", "Balanidae", "", "", "", "", "", GRAPHSPACE_METADATA);
//        System.out.println("Results: "+results.size());
//        for(TaxonomyStruct struct : results){
//            System.out.println(struct);
//        }

//        List<MicroCTSpecimenStruct> results=mrManager.searchMicroCTSpecimen("", "", "", "Sarah", "", GRAPHSPACE_METADATA);
//        System.out.println("Results: "+results.size());
//        for(MicroCTSpecimenStruct struct : results){
//            System.out.println(struct);
//        }       

        List<MicroCTScanningStruct> results=mrManager.searchMicroCTScanning("", "25.1.3.1667", "", "", "", "", GRAPHSPACE_METADATA);
        System.out.println("Results: "+results.size());
        for(MicroCTScanningStruct struct : results){
            System.out.println(struct);
        }        
    }
    

}
