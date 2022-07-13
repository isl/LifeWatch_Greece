package eu.lifewatch.example;

import eu.lifewatch.core.model.DirectoryStruct;
import eu.lifewatch.core.model.MeasurementStruct;
import eu.lifewatch.core.model.MicroCTScanningStruct;
import eu.lifewatch.core.model.MicroCTSpecimenStruct;
import eu.lifewatch.core.model.OccurrenceStatsTempStruct;
import eu.lifewatch.core.model.OccurrenceStruct;
import eu.lifewatch.core.model.ScientificNamingStruct;
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
    private static final String GRAPHSPACE_DIRECTORY="http://www.ics.forth.gr/isl/lifewatch/directory_2";
    private static final String GRAPHSPACE_METADATA="http://www.ics.forth.gr/isl/lifewatch/metadata_2";
    
    public static void main(String[] args) throws QueryExecutionException{
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        MetadataRepositoryService mrManager=context.getBean(MetadataRepositoryService.class);
        
//        String text=mrManager.produceText("Manzonia crassa", null, GRAPHSPACE_METADATA, GRAPHSPACE_DIRECTORY);
//        System.out.println(text);
        
//        List<TaxonomyStruct> results=mrManager.searchTaxonomy("Paradoneis ilvana", "", "", "", "", "", "", "", -1 , -1, GRAPHSPACE_METADATA);
//        System.out.println("Results: "+results.size());
//        for(TaxonomyStruct struct : results){
//            System.out.println(struct);
//        }

//        List<MicroCTSpecimenStruct> results=mrManager.searchMicroCTSpecimen("", "", "", "Sarah", "", GRAPHSPACE_METADATA);
//        System.out.println("Results: "+results.size());
//        for(MicroCTSpecimenStruct struct : results){
//            System.out.println(struct);
//        }       
//
//        List<MicroCTScanningStruct> results=mrManager.searchMicroCTScanning("", "", "", 0, 10, GRAPHSPACE_METADATA);
////        List<MicroCTScanningStruct> results=mrManager.searchMicroCTScanning("Agelas", "", "", -1, -1, GRAPHSPACE_METADATA);
//        System.out.println("Results: "+results.size());
//        for(MicroCTScanningStruct struct : results){
//            System.out.println(struct.getScanningLabel() +"\t"+struct.getSpecimen());
//        }        

//        List<TaxonomyStruct> results=mrManager.searchTaxonomy("", "Syllis", "", "", "", "", "", "", 0, 0, GRAPHSPACE_METADATA);
//        System.out.println("Results: "+results.size());
//        for(TaxonomyStruct struct : results){
//            System.out.println(struct);
//        }                
        
//        List<TaxonomyStruct> results=mrManager.searchTaxonomyCollated("", "Syllis", "", "", "", "", "", "", 100, 20, GRAPHSPACE_METADATA);
//        System.out.println("Results: "+results.size());
//        for(TaxonomyStruct struct : results){
//            System.out.println(struct);
//        }                

//        List<ScientificNamingStruct> results=mrManager.searchScientificNaming("", "1959", "", "", "", 0, 0, GRAPHSPACE_METADATA);
//        System.out.println("Results: "+results.size());
//        for(ScientificNamingStruct struct : results){
//            System.out.println(struct);
//        }                
//        
//        List<ScientificNamingStruct> results=mrManager.searchScientificNamingCollated("", "", "", "", 0, 20, GRAPHSPACE_METADATA);
//        System.out.println("Results: "+results.size());
//        for(ScientificNamingStruct struct : results){
//            System.out.println(struct);
//        }                
//        List<DirectoryStruct> results=mrManager.searchOccurrenceDatasets("", "", "", "", 40, 20,GRAPHSPACE_METADATA, GRAPHSPACE_DIRECTORY);
//        System.out.println("Results: "+results.size());
//        for(DirectoryStruct struct : results){
//            System.out.println(struct);
//        }                
        List<DirectoryStruct> results=mrManager.searchEnvironmentalDatasets("", "", "", 0, 0,GRAPHSPACE_METADATA, GRAPHSPACE_DIRECTORY);
        System.out.println("Results: "+results.size());
        for(DirectoryStruct struct : results){
            System.out.println(struct);
        }                
//        List<MeasurementStruct> results=mrManager.searchMeasurement("", "Chaetoceros danicus", "", "", GRAPHSPACE_METADATA);
//        System.out.println("Results: "+results.size());
//        for(MeasurementStruct struct : results){
//            System.out.println(struct);
//        }                
    }
    

}
