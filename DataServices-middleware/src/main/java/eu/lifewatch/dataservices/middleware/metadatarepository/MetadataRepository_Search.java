package eu.lifewatch.dataservices.middleware.metadatarepository;

import eu.lifewatch.core.impl.VirtuosoRepositoryManager;
import eu.lifewatch.core.model.CommonNameStruct;
import eu.lifewatch.core.model.EnvironmentalStruct;
import eu.lifewatch.core.model.GensDatasetStruct;
import eu.lifewatch.core.model.GensSampleStruct;
import eu.lifewatch.core.model.IdentificationStruct;
import eu.lifewatch.core.model.MeasurementStruct;
import eu.lifewatch.core.model.MicroCTPostProcessingStruct;
import eu.lifewatch.core.model.MicroCTReconstructionStruct;
import eu.lifewatch.core.model.MicroCTScanningStruct;
import eu.lifewatch.core.model.MicroCTSpecimenStruct;
import eu.lifewatch.core.model.MorphometricsStruct;
import eu.lifewatch.core.model.OccurrenceStatsTempStruct;
import eu.lifewatch.core.model.OccurrenceStruct;
import eu.lifewatch.core.model.ScientificNamingStruct;
import eu.lifewatch.core.model.SpecimenCollectionStruct;
import eu.lifewatch.core.model.SpecimenStruct;
import eu.lifewatch.core.model.StatsStruct;
import eu.lifewatch.core.model.SynonymStruct;
import eu.lifewatch.core.model.TaxonomyStruct;
import eu.lifewatch.core.model.Triple;
import eu.lifewatch.exception.QueryExecutionException;
import eu.lifewatch.service.impl.MetadataRepositoryService;
import java.util.ArrayList;
import java.util.List;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * MetadataRepository-Search provides the functionality to search for information from the metadata catalogue. 
 * For more information about the usage of the SOAP services and the contents and 
 * manipulation of the returned structs please refer to the javadoc documentation of the 
 * DataServices-api.
 *
 * @author Yannis Marketakis (marketak 'at' ics 'dot' forth 'dot' gr)
 * @author Nikos Minadakis (minadakn 'at' ics 'dot' forth 'dot' gr)
 */
@WebService(serviceName = "MetadataRepository_Search")
public class MetadataRepository_Search {
    private static final Logger logger=Logger.getLogger(MetadataRepository_Search.class);

    @WebMethod(operationName = "searchCommonName")
    public List<CommonNameStruct> searchCommonName(@WebParam(name = "speciesName") String speciesName,
                                                   @WebParam(name = "commonName") String commonName, 
                                                   @WebParam(name = "place") String place, 
                                                   @WebParam(name = "language") String language, 
                                                   @WebParam(name = "datasetURI") String datasetURI,
                                                   @WebParam(name = "repositoryGraph") String repositoryGraph) {
        logger.info("Request for searchCommonName("+speciesName+","+commonName+","+place+","+language+","+datasetURI+","+repositoryGraph+")");
        List<CommonNameStruct> retList=new ArrayList<>();
        try{
            ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
            VirtuosoRepositoryManager repoManager=context.getBean(VirtuosoRepositoryManager.class);
            MetadataRepositoryService api=new MetadataRepositoryService(repoManager);
            retList=api.searchCommonName(speciesName, commonName, place, language, datasetURI, repositoryGraph);
            logger.info("Number of results that will be returned: "+retList.size());
        }catch(QueryExecutionException ex){
            logger.error("An error occured while searching for common names. Returning an empty list.\n", ex);
        }
        return retList;
    }
    
    @WebMethod(operationName = "searchCommonNameWithinRange")
    public List<CommonNameStruct> searchCommonNameWithinRange(@WebParam(name = "speciesName") String speciesName,
                                                              @WebParam(name = "commonName") String commonName, 
                                                              @WebParam(name = "place") String place, 
                                                              @WebParam(name = "language") String language, 
                                                              @WebParam(name = "datasetURI") String datasetURI,
                                                              @WebParam(name = "offset") int offset,
                                                              @WebParam(name = "limit") int limit,
                                                              @WebParam(name = "repositoryGraph") String repositoryGraph) {
        logger.info("Request for searchCommonNameWithinRange("+speciesName+","+commonName+","+place+","+language+","+datasetURI+","+offset+","+limit+","+repositoryGraph+")");
        List<CommonNameStruct> retList=new ArrayList<>();
        try{
            ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
            VirtuosoRepositoryManager repoManager=context.getBean(VirtuosoRepositoryManager.class);
            MetadataRepositoryService api=new MetadataRepositoryService(repoManager);
            retList=api.searchCommonName(speciesName, commonName, place, language, datasetURI, offset, limit, repositoryGraph);
            logger.info("Number of results that will be returned: "+retList.size());
        }catch(QueryExecutionException ex){
            logger.error("An error occured while searching for common names. Returning an empty list.\n", ex);
        }
        return retList;
    }
    
    @WebMethod(operationName = "searchEnvironmental")
    public List<EnvironmentalStruct> searchEnvironmental(@WebParam(name = "dimension") String dimension,
                                                         @WebParam(name = "place") String place, 
                                                         @WebParam(name = "datasetURI") String datasetURI,
                                                         @WebParam(name = "repositoryGraph") String repositoryGraph) {
        logger.info("Request for searchEnvironmental("+dimension+","+place+","+datasetURI+","+repositoryGraph+")");
        List<EnvironmentalStruct> retList=new ArrayList<>();
        try{
            ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
            VirtuosoRepositoryManager repoManager=context.getBean(VirtuosoRepositoryManager.class);
            MetadataRepositoryService api=new MetadataRepositoryService(repoManager);
            retList=api.searchEnvironmental(dimension, place, datasetURI, repositoryGraph);
            logger.info("Number of results that will be returned: "+retList.size());
        }catch(QueryExecutionException ex){
            logger.error("An error occured while searching for environmental metadata. Returning an empty list.\n", ex);
        }
        return retList;
    }
    
    @WebMethod(operationName = "searchEnvironmentalWithinRange")
    public List<EnvironmentalStruct> searchEnvironmentalWithinRange(@WebParam(name = "dimension") String dimension,
                                                         @WebParam(name = "place") String place, 
                                                         @WebParam(name = "datasetURI") String datasetURI,
                                                         @WebParam(name = "offset") int offset,
                                                         @WebParam(name = "limit") int limit,
                                                         @WebParam(name = "repositoryGraph") String repositoryGraph) {
        logger.info("Request for searchEnvironmentalWithinRange("+dimension+","+place+","+datasetURI+","+offset+","+limit+","+repositoryGraph+")");
        List<EnvironmentalStruct> retList=new ArrayList<>();
        try{
            ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
            VirtuosoRepositoryManager repoManager=context.getBean(VirtuosoRepositoryManager.class);
            MetadataRepositoryService api=new MetadataRepositoryService(repoManager);
            retList=api.searchEnvironmental(dimension, place, datasetURI, offset, limit, repositoryGraph);
            logger.info("Number of results that will be returned: "+retList.size());
        }catch(QueryExecutionException ex){
            logger.error("An error occured while searching for environmental metadata. Returning an empty list.\n", ex);
        }
        return retList;
    }
    
    @WebMethod(operationName = "searchGenetics")
    public List<GensDatasetStruct> searchGenetics(@WebParam(name = "speciesName") String speciesName,
                                                  @WebParam(name = "sample") String sample, 
                                                  @WebParam(name = "place") String place,
                                                  @WebParam(name = "device") String device,
                                                  @WebParam(name = "datasetURI") String datasetURI,
                                                  @WebParam(name = "repositoryGraph") String repositoryGraph) {
        logger.info("Request for searchGenetics("+speciesName+","+sample+","+place+","+device+","+datasetURI+","+repositoryGraph+")");
        List<GensDatasetStruct> retList=new ArrayList<>();
        try{
            ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
            VirtuosoRepositoryManager repoManager=context.getBean(VirtuosoRepositoryManager.class);
            MetadataRepositoryService api=new MetadataRepositoryService(repoManager);
            retList=api.searchGenetics(speciesName, sample, place, device, datasetURI, repositoryGraph);
            logger.info("Number of results that will be returned: "+retList.size());
        }catch(QueryExecutionException ex){
            logger.error("An error occured while searching for genetic metadata. Returning an empty list.\n", ex);
        }
        return retList;
    }
    
    @WebMethod(operationName = "searchGeneticsWithinRange")
    public List<GensDatasetStruct> searchGeneticsWithinRange(@WebParam(name = "speciesName") String speciesName,
                                                             @WebParam(name = "sample") String sample, 
                                                             @WebParam(name = "place") String place,
                                                             @WebParam(name = "device") String device,
                                                             @WebParam(name = "datasetURI") String datasetURI,
                                                             @WebParam(name = "offset") int offset,
                                                             @WebParam(name = "limit") int limit,
                                                             @WebParam(name = "repositoryGraph") String repositoryGraph) {
        logger.info("Request for searchGeneticsWithinRange("+speciesName+","+sample+","+place+","+device+","+datasetURI+","+offset+","+limit+","+repositoryGraph+")");
        List<GensDatasetStruct> retList=new ArrayList<>();
        try{
            ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
            VirtuosoRepositoryManager repoManager=context.getBean(VirtuosoRepositoryManager.class);
            MetadataRepositoryService api=new MetadataRepositoryService(repoManager);
            retList=api.searchGenetics(speciesName, sample, place, device, datasetURI, offset, limit, repositoryGraph);
            logger.info("Number of results that will be returned: "+retList.size());
        }catch(QueryExecutionException ex){
            logger.error("An error occured while searching for genetic metadata. Returning an empty list.\n", ex);
        }
        return retList;
    }
    
    @WebMethod(operationName = "searchGensSample")
    public List<GensSampleStruct> searchGensSample(@WebParam(name = "speciesName") String speciesName,
                                                   @WebParam(name = "device") String device, 
                                                   @WebParam(name = "sample") String sample,
                                                   @WebParam(name = "datasetURI") String datasetURI,
                                                   @WebParam(name = "repositoryGraph") String repositoryGraph) {
        logger.info("Request for searchGensSample("+speciesName+","+device+","+sample+","+datasetURI+","+repositoryGraph+")");
        List<GensSampleStruct> retList=new ArrayList<>();
        try{
            ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
            VirtuosoRepositoryManager repoManager=context.getBean(VirtuosoRepositoryManager.class);
            MetadataRepositoryService api=new MetadataRepositoryService(repoManager);
            retList=api.searchGensSample(speciesName, device, sample, datasetURI, repositoryGraph);
            logger.info("Number of results that will be returned: "+retList.size());
        }catch(QueryExecutionException ex){
            logger.error("An error occured while searching for genetic samples metadata. Returning an empty list.\n", ex);
        }
        return retList;
    }
    
    @WebMethod(operationName = "searchGensSampleWithinRange")
    public List<GensSampleStruct> searchGensSampleWithinRange(@WebParam(name = "speciesName") String speciesName,
                                                              @WebParam(name = "device") String device, 
                                                              @WebParam(name = "sample") String sample,
                                                              @WebParam(name = "datasetURI") String datasetURI,
                                                              @WebParam(name = "offset") int offset,
                                                              @WebParam(name = "limit") int limit,
                                                              @WebParam(name = "repositoryGraph") String repositoryGraph) {
        logger.info("Request for searchGensSampleWithinRange("+speciesName+","+device+","+sample+","+datasetURI+","+offset+","+limit+","+repositoryGraph+")");
        List<GensSampleStruct> retList=new ArrayList<>();
        try{
            ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
            VirtuosoRepositoryManager repoManager=context.getBean(VirtuosoRepositoryManager.class);
            MetadataRepositoryService api=new MetadataRepositoryService(repoManager);
            retList=api.searchGensSample(speciesName, device, sample, datasetURI, offset, limit, repositoryGraph);
            logger.info("Number of results that will be returned: "+retList.size());
        }catch(QueryExecutionException ex){
            logger.error("An error occured while searching for genetic samples metadata. Returning an empty list.\n", ex);
        }
        return retList;
    }
    
    @WebMethod(operationName = "searchIdentification")
    public List<IdentificationStruct> searchIdentification(@WebParam(name = "speciesName") String speciesName,
                                                           @WebParam(name = "date") String date, 
                                                           @WebParam(name = "actor") String actor,
                                                           @WebParam(name = "place") String place,
                                                           @WebParam(name = "individual") String individual,
                                                           @WebParam(name = "datasetURI") String datasetURI,
                                                           @WebParam(name = "repositoryGraph") String repositoryGraph) {
        logger.info("Request for searchIdentification("+speciesName+","+date+","+actor+","+place+","+individual+","+datasetURI+","+repositoryGraph+")");
        List<IdentificationStruct> retList=new ArrayList<>();
        try{
            ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
            VirtuosoRepositoryManager repoManager=context.getBean(VirtuosoRepositoryManager.class);
            MetadataRepositoryService api=new MetadataRepositoryService(repoManager);
            retList=api.searchIdentification(speciesName, date, actor, place, individual, datasetURI, repositoryGraph);
            logger.info("Number of results that will be returned: "+retList.size());
        }catch(QueryExecutionException ex){
            logger.error("An error occured while searching for identification metadata. Returning an empty list.\n", ex);
        }
        return retList;
    }
    
    @WebMethod(operationName = "searchIdentificationWithinRange")
    public List<IdentificationStruct> searchIdentificationWithinRange(@WebParam(name = "speciesName") String speciesName,
                                                                      @WebParam(name = "date") String date, 
                                                                      @WebParam(name = "actor") String actor,
                                                                      @WebParam(name = "place") String place,
                                                                      @WebParam(name = "individual") String individual,
                                                                      @WebParam(name = "datasetURI") String datasetURI,
                                                                      @WebParam(name = "offset") int offset,
                                                                      @WebParam(name = "limit") int limit,
                                                                      @WebParam(name = "repositoryGraph") String repositoryGraph) {
        logger.info("Request for searchIdentification("+speciesName+","+date+","+actor+","+place+","+individual+","+datasetURI+","+offset+","+limit+","+repositoryGraph+")");
        List<IdentificationStruct> retList=new ArrayList<>();
        try{
            ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
            VirtuosoRepositoryManager repoManager=context.getBean(VirtuosoRepositoryManager.class);
            MetadataRepositoryService api=new MetadataRepositoryService(repoManager);
            retList=api.searchIdentification(speciesName, date, actor, place, individual, datasetURI, offset, limit, repositoryGraph);
            logger.info("Number of results that will be returned: "+retList.size());
        }catch(QueryExecutionException ex){
            logger.error("An error occured while searching for identification metadata. Returning an empty list.\n", ex);
        }
        return retList;
    }
    
    @WebMethod(operationName = "searchMeasurement")
    public List<MeasurementStruct> searchMeasurement(@WebParam(name = "specimen") String specimen,
                                                     @WebParam(name = "speciesName") String speciesName, 
                                                     @WebParam(name = "dimension") String dimension,
                                                     @WebParam(name = "datasetURI") String datasetURI,
                                                     @WebParam(name = "repositoryGraph") String repositoryGraph) {
        logger.info("Request for searchIdentification("+specimen+","+speciesName+","+dimension+","+datasetURI+","+repositoryGraph+")");
        List<MeasurementStruct> retList=new ArrayList<>();
        try{
            ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
            VirtuosoRepositoryManager repoManager=context.getBean(VirtuosoRepositoryManager.class);
            MetadataRepositoryService api=new MetadataRepositoryService(repoManager);
            retList=api.searchMeasurement(specimen, speciesName, dimension, datasetURI, repositoryGraph);
            logger.info("Number of results that will be returned: "+retList.size());
        }catch(QueryExecutionException ex){
            logger.error("An error occured while searching for measurement metadata. Returning an empty list.\n", ex);
        }
        return retList;
    }
        
    @WebMethod(operationName = "searchMeasurementWithinRange")
    public List<MeasurementStruct> searchMeasurementWithinRange(@WebParam(name = "specimen") String specimen,
                                                                @WebParam(name = "speciesName") String speciesName, 
                                                                @WebParam(name = "dimension") String dimension,
                                                                @WebParam(name = "datasetURI") String datasetURI,
                                                                @WebParam(name = "offset") int offset,
                                                                @WebParam(name = "limit") int limit,
                                                                @WebParam(name = "repositoryGraph") String repositoryGraph) {
        logger.info("Request for searchIdentification("+specimen+","+speciesName+","+dimension+","+datasetURI+","+offset+","+limit+","+repositoryGraph+")");
        List<MeasurementStruct> retList=new ArrayList<>();
        try{
            ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
            VirtuosoRepositoryManager repoManager=context.getBean(VirtuosoRepositoryManager.class);
            MetadataRepositoryService api=new MetadataRepositoryService(repoManager);
            retList=api.searchMeasurement(specimen, speciesName, dimension, datasetURI, offset, limit, repositoryGraph);
            logger.info("Number of results that will be returned: "+retList.size());
        }catch(QueryExecutionException ex){
            logger.error("An error occured while searching for measurement metadata. Returning an empty list.\n", ex);
        }
        return retList;
    }
    
    @WebMethod(operationName = "searchMicroCTPostProcessing")
    public List<MicroCTPostProcessingStruct> searchMicroCTPostProcessing(@WebParam(name = "speciesName") String speciesName,
                                                                         @WebParam(name = "specimen") String specimen, 
                                                                         @WebParam(name = "input") String input,
                                                                         @WebParam(name = "datasetURI") String datasetURI,
                                                                         @WebParam(name = "repositoryGraph") String repositoryGraph) {
        logger.info("Request for searchMicroCTPostProcessing("+speciesName+","+specimen+","+input+","+datasetURI+","+repositoryGraph+")");
        List<MicroCTPostProcessingStruct> retList=new ArrayList<>();
        try{
            ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
            VirtuosoRepositoryManager repoManager=context.getBean(VirtuosoRepositoryManager.class);
            MetadataRepositoryService api=new MetadataRepositoryService(repoManager);
            retList=api.searchMicroCTPostProcessing(speciesName, specimen, input, datasetURI, repositoryGraph);
            logger.info("Number of results that will be returned: "+retList.size());
        }catch(QueryExecutionException ex){
            logger.error("An error occured while searching for microCT postprocessing metadata. Returning an empty list.\n", ex);
        }
        return retList;
    }
    
    @WebMethod(operationName = "searchMicroCTPostProcessingWithinRange")
    public List<MicroCTPostProcessingStruct> searchMicroCTPostProcessingWithinRange(@WebParam(name = "speciesName") String speciesName,
                                                                                    @WebParam(name = "specimen") String specimen, 
                                                                                    @WebParam(name = "input") String input,
                                                                                    @WebParam(name = "datasetURI") String datasetURI,
                                                                                    @WebParam(name = "offset") int offset,
                                                                                    @WebParam(name = "limit") int limit,
                                                                                    @WebParam(name = "repositoryGraph") String repositoryGraph) {
        logger.info("Request for searchMicroCTPostProcessingWithinRange("+speciesName+","+specimen+","+input+","+datasetURI+","+offset+","+limit+","+repositoryGraph+")");
        List<MicroCTPostProcessingStruct> retList=new ArrayList<>();
        try{
            ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
            VirtuosoRepositoryManager repoManager=context.getBean(VirtuosoRepositoryManager.class);
            MetadataRepositoryService api=new MetadataRepositoryService(repoManager);
            retList=api.searchMicroCTPostProcessing(speciesName, specimen, input, datasetURI, offset, limit, repositoryGraph);
            logger.info("Number of results that will be returned: "+retList.size());
        }catch(QueryExecutionException ex){
            logger.error("An error occured while searching for microCT postprocessing metadata. Returning an empty list.\n", ex);
        }
        return retList;
    }
    
    @WebMethod(operationName = "searchMicroCTReconstruction")
    public List<MicroCTReconstructionStruct> searchMicroCTReconstruction(@WebParam(name = "speciesName") String speciesName,
                                                                         @WebParam(name = "specimen") String specimen, 
                                                                         @WebParam(name = "input") String input,
                                                                         @WebParam(name = "datasetURI") String datasetURI,
                                                                         @WebParam(name = "repositoryGraph") String repositoryGraph) {
        logger.info("Request for searchMicroCTReconstruction("+speciesName+","+specimen+","+input+","+datasetURI+","+repositoryGraph+")");
        List<MicroCTReconstructionStruct> retList=new ArrayList<>();
        try{
            ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
            VirtuosoRepositoryManager repoManager=context.getBean(VirtuosoRepositoryManager.class);
            MetadataRepositoryService api=new MetadataRepositoryService(repoManager);
            retList=api.searchMicroCTReconstruction(speciesName, specimen, input, datasetURI, repositoryGraph);
            logger.info("Number of results that will be returned: "+retList.size());
        }catch(QueryExecutionException ex){
            logger.error("An error occured while searching for microCT reconstruction metadata. Returning an empty list.\n", ex);
        }
        return retList;
    }
    
    @WebMethod(operationName = "searchMicroCTReconstructionWithinRange")
    public List<MicroCTReconstructionStruct> searchMicroCTReconstructionWithinRange(@WebParam(name = "speciesName") String speciesName,
                                                                                    @WebParam(name = "specimen") String specimen, 
                                                                                    @WebParam(name = "input") String input,
                                                                                    @WebParam(name = "datasetURI") String datasetURI,
                                                                                    @WebParam(name = "offset") int offset,
                                                                                    @WebParam(name = "limit") int limit,
                                                                                    @WebParam(name = "repositoryGraph") String repositoryGraph) {
        logger.info("Request for searchMicroCTReconstructionWithinRange("+speciesName+","+specimen+","+input+","+datasetURI+","+repositoryGraph+")");
        List<MicroCTReconstructionStruct> retList=new ArrayList<>();
        try{
            ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
            VirtuosoRepositoryManager repoManager=context.getBean(VirtuosoRepositoryManager.class);
            MetadataRepositoryService api=new MetadataRepositoryService(repoManager);
            retList=api.searchMicroCTReconstruction(speciesName, specimen, input, datasetURI, offset, limit, repositoryGraph);
            logger.info("Number of results that will be returned: "+retList.size());
        }catch(QueryExecutionException ex){
            logger.error("An error occured while searching for microCT reconstruction metadata. Returning an empty list.\n", ex);
        }
        return retList;
    }
    
    @WebMethod(operationName = "searchMicroCTScanning")
    public List<MicroCTScanningStruct> searchMicroCTScanning(@WebParam(name = "deviceName") String deviceName,
                                                             @WebParam(name = "specimen") String specimen, 
                                                             @WebParam(name = "speciesName") String speciesName,
                                                             @WebParam(name = "contrastMethod") String contrastMethod,
                                                             @WebParam(name = "scanning") String scanning,
                                                             @WebParam(name = "datasetURI") String datasetURI,
                                                             @WebParam(name = "repositoryGraph") String repositoryGraph) {
        logger.info("Request for searchMicroCTScanning("+deviceName+","+specimen+","+speciesName+","+contrastMethod+","+scanning+","+datasetURI+","+repositoryGraph+")");
        List<MicroCTScanningStruct> retList=new ArrayList<>();
        try{
            ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
            VirtuosoRepositoryManager repoManager=context.getBean(VirtuosoRepositoryManager.class);
            MetadataRepositoryService api=new MetadataRepositoryService(repoManager);
            retList=api.searchMicroCTScanning(deviceName, specimen, speciesName, contrastMethod, scanning, datasetURI, 0, 0, repositoryGraph);
            logger.info("Number of results that will be returned: "+retList.size());
        }catch(QueryExecutionException ex){
            logger.error("An error occured while searching for microCT scanning metadata. Returning an empty list.\n", ex);
        }
        return retList;
    }
    
    @WebMethod(operationName = "searchMicroCTScanningWithinRange")
    public List<MicroCTScanningStruct> searchMicroCTScanningWithinRange(@WebParam(name = "deviceName") String deviceName,
                                                                        @WebParam(name = "specimen") String specimen, 
                                                                        @WebParam(name = "speciesName") String speciesName,
                                                                        @WebParam(name = "contrastMethod") String contrastMethod,
                                                                        @WebParam(name = "scanning") String scanning,
                                                                        @WebParam(name = "datasetURI") String datasetURI,
                                                                        @WebParam(name = "offset") int offset,
                                                                        @WebParam(name = "limit") int limit,
                                                                        @WebParam(name = "repositoryGraph") String repositoryGraph) {
        logger.info("Request for searchMicroCTScanningWithinRange("+deviceName+","+specimen+","+speciesName+","+contrastMethod+","+scanning+","+datasetURI+","+offset+","+limit+","+repositoryGraph+")");
        List<MicroCTScanningStruct> retList=new ArrayList<>();
        try{
            ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
            VirtuosoRepositoryManager repoManager=context.getBean(VirtuosoRepositoryManager.class);
            MetadataRepositoryService api=new MetadataRepositoryService(repoManager);
            retList=api.searchMicroCTScanning(deviceName, specimen, speciesName, contrastMethod, scanning, datasetURI, offset, limit, repositoryGraph);
            logger.info("Number of results that will be returned: "+retList.size());
        }catch(QueryExecutionException ex){
            logger.error("An error occured while searching for microCT scanning metadata. Returning an empty list.\n", ex);
        }
        return retList;
    }
    
    @WebMethod(operationName = "searchMicroCTSpecimen")
    public List<MicroCTSpecimenStruct> searchMicroCTSpecimen(@WebParam(name = "specimen") String specimen, 
                                                             @WebParam(name = "collection") String collection,
                                                             @WebParam(name = "speciesName") String speciesName,
                                                             @WebParam(name = "provider") String provider,
                                                             @WebParam(name = "datasetURI") String datasetURI,
                                                             @WebParam(name = "repositoryGraph") String repositoryGraph) {
        logger.info("Request for searchMicroCTSpecimen("+specimen+","+collection+","+speciesName+","+provider+","+datasetURI+","+repositoryGraph+")");
        List<MicroCTSpecimenStruct> retList=new ArrayList<>();
        try{
            ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
            VirtuosoRepositoryManager repoManager=context.getBean(VirtuosoRepositoryManager.class);
            MetadataRepositoryService api=new MetadataRepositoryService(repoManager);
            retList=api.searchMicroCTSpecimen(specimen, collection, speciesName, provider, datasetURI, 0, 0, repositoryGraph);
            logger.info("Number of results that will be returned: "+retList.size());
        }catch(QueryExecutionException ex){
            logger.error("An error occured while searching for microCT specimen metadata. Returning an empty list.\n", ex);
        }
        return retList;
    }
    
    @WebMethod(operationName = "searchMicroCTSpecimenWithinRange")
    public List<MicroCTSpecimenStruct> searchMicroCTSpecimenWithinRange(@WebParam(name = "specimen") String specimen, 
                                                                        @WebParam(name = "collection") String collection,
                                                                        @WebParam(name = "speciesName") String speciesName,
                                                                        @WebParam(name = "provider") String provider,
                                                                        @WebParam(name = "datasetURI") String datasetURI,
                                                                        @WebParam(name = "offset") int offset,
                                                                        @WebParam(name = "limit") int limit,
                                                                        @WebParam(name = "repositoryGraph") String repositoryGraph) {
        logger.info("Request for searchMicroCTSpecimenWithinRange("+specimen+","+collection+","+speciesName+","+provider+","+datasetURI+","+offset+","+limit+","+repositoryGraph+")");
        List<MicroCTSpecimenStruct> retList=new ArrayList<>();
        try{
            ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
            VirtuosoRepositoryManager repoManager=context.getBean(VirtuosoRepositoryManager.class);
            MetadataRepositoryService api=new MetadataRepositoryService(repoManager);
            retList=api.searchMicroCTSpecimen(specimen, collection, speciesName, provider, datasetURI, offset, limit, repositoryGraph);
            logger.info("Number of results that will be returned: "+retList.size());
        }catch(QueryExecutionException ex){
            logger.error("An error occured while searching for microCT specimen metadata. Returning an empty list.\n", ex);
        }
        return retList;
    }
    
    @WebMethod(operationName = "searchMorphometrics")
    public List<MorphometricsStruct> searchMorphometrics(@WebParam(name = "speciesName") String speciesName, 
                                                         @WebParam(name = "dimension") String dimension,
                                                         @WebParam(name = "datasetURI") String datasetURI,
                                                         @WebParam(name = "repositoryGraph") String repositoryGraph) {
        logger.info("Request for searchMorphometrics("+speciesName+","+dimension+","+datasetURI+","+repositoryGraph+")");
        List<MorphometricsStruct> retList=new ArrayList<>();
        try{
            ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
            VirtuosoRepositoryManager repoManager=context.getBean(VirtuosoRepositoryManager.class);
            MetadataRepositoryService api=new MetadataRepositoryService(repoManager);
            retList=api.searchMorphometrics(speciesName, dimension, datasetURI, repositoryGraph);
            logger.info("Number of results that will be returned: "+retList.size());
        }catch(QueryExecutionException ex){
            logger.error("An error occured while searching for morphometrics metadata. Returning an empty list.\n", ex);
        }
        return retList;
    }
    
    @WebMethod(operationName = "searchMorphometricsWithinRange")
    public List<MorphometricsStruct> searchMorphometricsWithinRange(@WebParam(name = "speciesName") String speciesName, 
                                                                    @WebParam(name = "dimension") String dimension,
                                                                    @WebParam(name = "datasetURI") String datasetURI,
                                                                    @WebParam(name = "offset") int offset,
                                                                    @WebParam(name = "limit") int limit,
                                                                    @WebParam(name = "repositoryGraph") String repositoryGraph) {
        logger.info("Request for searchMorphometricsWithinRange("+speciesName+","+dimension+","+datasetURI+","+offset+","+limit+","+repositoryGraph+")");
        List<MorphometricsStruct> retList=new ArrayList<>();
        try{
            ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
            VirtuosoRepositoryManager repoManager=context.getBean(VirtuosoRepositoryManager.class);
            MetadataRepositoryService api=new MetadataRepositoryService(repoManager);
            retList=api.searchMorphometrics(speciesName, dimension, datasetURI, offset, limit, repositoryGraph);
            logger.info("Number of results that will be returned: "+retList.size());
        }catch(QueryExecutionException ex){
            logger.error("An error occured while searching for morphometrics metadata. Returning an empty list.\n", ex);
        }
        return retList;
    }
    
    @WebMethod(operationName = "searchOccurrenceStatsTemp")
    public List<OccurrenceStatsTempStruct> searchOccurrenceStatsTemp(@WebParam(name = "speciesName") String speciesName, 
                                                                     @WebParam(name = "place") String place,
                                                                     @WebParam(name = "date") String date,
                                                                     @WebParam(name = "numberOfParts") String numberOfParts,
                                                                     @WebParam(name = "datasetURI") String datasetURI,
                                                                     @WebParam(name = "repositoryGraph") String repositoryGraph) {
        logger.info("Request for searchOccurrenceStatsTemp("+speciesName+","+place+","+date+","+numberOfParts+","+datasetURI+","+repositoryGraph+")");
        List<OccurrenceStatsTempStruct> retList=new ArrayList<>();
        try{
            ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
            VirtuosoRepositoryManager repoManager=context.getBean(VirtuosoRepositoryManager.class);
            MetadataRepositoryService api=new MetadataRepositoryService(repoManager);
            retList=api.searchOccurenceStatsTemp(speciesName, place, date, numberOfParts, datasetURI, 0, 0, repositoryGraph);
            logger.info("Number of results that will be returned: "+retList.size());
        }catch(QueryExecutionException ex){
            logger.error("An error occured while searching for occurrence stats temp metadata. Returning an empty list.\n", ex);
        }
        return retList;
    }
    
    @WebMethod(operationName = "searchOccurrenceStatsTempWithinRange")
    public List<OccurrenceStatsTempStruct> searchOccurrenceStatsTempWithinRange(@WebParam(name = "speciesName") String speciesName, 
                                                                                @WebParam(name = "place") String place,
                                                                                @WebParam(name = "date") String date,
                                                                                @WebParam(name = "numberOfParts") String numberOfParts,
                                                                                @WebParam(name = "datasetURI") String datasetURI,
                                                                                @WebParam(name = "offset") int offset,
                                                                                @WebParam(name = "limit") int limit,
                                                                                @WebParam(name = "repositoryGraph") String repositoryGraph) {
        logger.info("Request for searchOccurrenceStatsTempWithinRange("+speciesName+","+place+","+date+","+numberOfParts+","+datasetURI+","+offset+","+limit+","+repositoryGraph+")");
        List<OccurrenceStatsTempStruct> retList=new ArrayList<>();
        try{
            ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
            VirtuosoRepositoryManager repoManager=context.getBean(VirtuosoRepositoryManager.class);
            MetadataRepositoryService api=new MetadataRepositoryService(repoManager);
            retList=api.searchOccurenceStatsTemp(speciesName, place, date, numberOfParts, datasetURI, offset, limit, repositoryGraph);
            logger.info("Number of results that will be returned: "+retList.size());
        }catch(QueryExecutionException ex){
            logger.error("An error occured while searching for occurrence stats temp metadata. Returning an empty list.\n", ex);
        }
        return retList;
    }
    
    @WebMethod(operationName = "searchOccurrence")
    public List<OccurrenceStruct> searchOccurrence(@WebParam(name = "speciesName") String speciesName, 
                                                   @WebParam(name = "place") String place,
                                                   @WebParam(name = "date") String date,
                                                   @WebParam(name = "datasetURI") String datasetURI,
                                                   @WebParam(name = "repositoryGraph") String repositoryGraph) {
        logger.info("Request for searchOccurrence("+speciesName+","+place+","+date+","+datasetURI+","+repositoryGraph+")");
        List<OccurrenceStruct> retList=new ArrayList<>();
        try{
            ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
            VirtuosoRepositoryManager repoManager=context.getBean(VirtuosoRepositoryManager.class);
            MetadataRepositoryService api=new MetadataRepositoryService(repoManager);
            retList=api.searchOccurrence(speciesName, place, date, datasetURI, 0, 0, repositoryGraph);
            logger.info("Number of results that will be returned: "+retList.size());
        }catch(QueryExecutionException ex){
            logger.error("An error occured while searching for occurrence metadata. Returning an empty list.\n", ex);
        }
        return retList;
    }
    
    @WebMethod(operationName = "searchOccurrenceWithinRange")
    public List<OccurrenceStruct> searchOccurrenceWithinRange(@WebParam(name = "speciesName") String speciesName, 
                                                              @WebParam(name = "place") String place,
                                                              @WebParam(name = "date") String date,
                                                              @WebParam(name = "datasetURI") String datasetURI,
                                                              @WebParam(name = "offset") int offset,
                                                              @WebParam(name = "limit") int limit,
                                                              @WebParam(name = "repositoryGraph") String repositoryGraph) {
        logger.info("Request for searchOccurrenceWithinRange("+speciesName+","+place+","+date+","+datasetURI+","+offset+","+limit+","+repositoryGraph+")");
        List<OccurrenceStruct> retList=new ArrayList<>();
        try{
            ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
            VirtuosoRepositoryManager repoManager=context.getBean(VirtuosoRepositoryManager.class);
            MetadataRepositoryService api=new MetadataRepositoryService(repoManager);
            retList=api.searchOccurrence(speciesName, place, date, datasetURI, offset, limit, repositoryGraph);
            logger.info("Number of results that will be returned: "+retList.size());
        }catch(QueryExecutionException ex){
            logger.error("An error occured while searching for occurrence metadata. Returning an empty list.\n", ex);
        }
        return retList;
    }
    
    @WebMethod(operationName = "searchScientificNaming")
    public List<ScientificNamingStruct> searchScientificNaming(@WebParam(name = "speciesName") String speciesName, 
                                                               @WebParam(name = "date") String date,
                                                               @WebParam(name = "actor") String actor,
                                                               @WebParam(name = "datasetURI") String datasetURI,
                                                               @WebParam(name = "scientificName") String scientificName,
                                                               @WebParam(name = "repositoryGraph") String repositoryGraph) {
        logger.info("Request for searchScientificNaming("+speciesName+","+date+","+actor+","+datasetURI+","+scientificName+","+repositoryGraph+")");
        List<ScientificNamingStruct> retList=new ArrayList<>();
        try{
            ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
            VirtuosoRepositoryManager repoManager=context.getBean(VirtuosoRepositoryManager.class);
            MetadataRepositoryService api=new MetadataRepositoryService(repoManager);
            retList=api.searchScientificNaming(speciesName, date, actor, datasetURI, scientificName, 0, 0, repositoryGraph);
            logger.info("Number of results that will be returned: "+retList.size());
        }catch(QueryExecutionException ex){
            logger.error("An error occured while searching for scientific naming metadata. Returning an empty list.\n", ex);
        }
        return retList;
    }
    
    @WebMethod(operationName = "searchScientificNamingWithinRange")
    public List<ScientificNamingStruct> searchScientificNamingWithinRange(@WebParam(name = "speciesName") String speciesName, 
                                                                          @WebParam(name = "date") String date,
                                                                          @WebParam(name = "actor") String actor,
                                                                          @WebParam(name = "datasetURI") String datasetURI,
                                                                          @WebParam(name = "scientificName") String scientificName,
                                                                          @WebParam(name = "offset") int offset,
                                                                          @WebParam(name = "limit") int limit,
                                                                          @WebParam(name = "repositoryGraph") String repositoryGraph) {
        logger.info("Request for searchScientificNamingWithinRange("+speciesName+","+date+","+actor+","+datasetURI+","+scientificName+","+offset+","+limit+","+repositoryGraph+")");
        List<ScientificNamingStruct> retList=new ArrayList<>();
        try{
            ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
            VirtuosoRepositoryManager repoManager=context.getBean(VirtuosoRepositoryManager.class);
            MetadataRepositoryService api=new MetadataRepositoryService(repoManager);
            retList=api.searchScientificNaming(speciesName, date, actor, datasetURI, scientificName, offset, limit, repositoryGraph);
            logger.info("Number of results that will be returned: "+retList.size());
        }catch(QueryExecutionException ex){
            logger.error("An error occured while searching for scientific naming metadata. Returning an empty list.\n", ex);
        }
        return retList;
    }
    
    @WebMethod(operationName = "searchSpecimen")
    public List<SpecimenStruct> searchSpecimen(@WebParam(name = "specimen") String specimen, 
                                               @WebParam(name = "speciesName") String speciesName,
                                               @WebParam(name = "collection") String collection,
                                               @WebParam(name = "datasetURI") String datasetURI,
                                               @WebParam(name = "repositoryGraph") String repositoryGraph) {
        logger.info("Request for searchSpecimen("+specimen+","+speciesName+","+collection+","+datasetURI+","+repositoryGraph+")");
        List<SpecimenStruct> retList=new ArrayList<>();
        try{
            ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
            VirtuosoRepositoryManager repoManager=context.getBean(VirtuosoRepositoryManager.class);
            MetadataRepositoryService api=new MetadataRepositoryService(repoManager);
            retList=api.searchSpecimen(specimen, speciesName, collection, datasetURI, repositoryGraph);
            logger.info("Number of results that will be returned: "+retList.size());
        }catch(QueryExecutionException ex){
            logger.error("An error occured while searching for specimen metadata. Returning an empty list.\n", ex);
        }
        return retList;
    }
    
    @WebMethod(operationName = "searchSpecimenWithinRange")
    public List<SpecimenStruct> searchSpecimenWithinRange(@WebParam(name = "specimen") String specimen, 
                                                          @WebParam(name = "speciesName") String speciesName,
                                                          @WebParam(name = "collection") String collection,
                                                          @WebParam(name = "datasetURI") String datasetURI,
                                                          @WebParam(name = "offset") int offset,
                                                          @WebParam(name = "limit") int limit,
                                                          @WebParam(name = "repositoryGraph") String repositoryGraph) {
        logger.info("Request for searchSpecimenWithinRange("+specimen+","+speciesName+","+collection+","+datasetURI+","+offset+","+limit+","+repositoryGraph+")");
        List<SpecimenStruct> retList=new ArrayList<>();
        try{
            ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
            VirtuosoRepositoryManager repoManager=context.getBean(VirtuosoRepositoryManager.class);
            MetadataRepositoryService api=new MetadataRepositoryService(repoManager);
            retList=api.searchSpecimen(specimen, speciesName, collection, datasetURI, offset, limit, repositoryGraph);
            logger.info("Number of results that will be returned: "+retList.size());
        }catch(QueryExecutionException ex){
            logger.error("An error occured while searching for specimen metadata. Returning an empty list.\n", ex);
        }
        return retList;
    }
    
    @WebMethod(operationName = "searchSpecimenCollection")
    public List<SpecimenCollectionStruct> searchSpecimenCollection(@WebParam(name = "collectionName") String collectionName, 
                                                                   @WebParam(name = "ownerName") String ownerName,
                                                                   @WebParam(name = "datasetURI") String datasetURI,
                                                                   @WebParam(name = "repositoryGraph") String repositoryGraph) {
        logger.info("Request for searchSpecimenCollection("+collectionName+","+ownerName+","+datasetURI+","+repositoryGraph+")");
        List<SpecimenCollectionStruct> retList=new ArrayList<>();
        try{
            ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
            VirtuosoRepositoryManager repoManager=context.getBean(VirtuosoRepositoryManager.class);
            MetadataRepositoryService api=new MetadataRepositoryService(repoManager);
            retList=api.searchSpecimenCollection(collectionName, ownerName, datasetURI, repositoryGraph);
            logger.info("Number of results that will be returned: "+retList.size());
        }catch(QueryExecutionException ex){
            logger.error("An error occured while searching for specimen collection metadata. Returning an empty list.\n", ex);
        }
        return retList;
    }
    
    @WebMethod(operationName = "searchSpecimenCollectionWithinRange")
    public List<SpecimenCollectionStruct> searchSpecimenCollectionWithinRange(@WebParam(name = "collectionName") String collectionName, 
                                                                              @WebParam(name = "ownerName") String ownerName,
                                                                              @WebParam(name = "datasetURI") String datasetURI,
                                                                              @WebParam(name = "offset") int offset,
                                                                              @WebParam(name = "limit") int limit,
                                                                              @WebParam(name = "repositoryGraph") String repositoryGraph) {
        logger.info("Request for searchSpecimenCollectionWithinRange("+collectionName+","+ownerName+","+datasetURI+","+offset+","+limit+","+repositoryGraph+")");
        List<SpecimenCollectionStruct> retList=new ArrayList<>();
        try{
            ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
            VirtuosoRepositoryManager repoManager=context.getBean(VirtuosoRepositoryManager.class);
            MetadataRepositoryService api=new MetadataRepositoryService(repoManager);
            retList=api.searchSpecimenCollection(collectionName, ownerName, datasetURI, offset, limit, repositoryGraph);
            logger.info("Number of results that will be returned: "+retList.size());
        }catch(QueryExecutionException ex){
            logger.error("An error occured while searching for specimen collection metadata. Returning an empty list.\n", ex);
        }
        return retList;
    }
    
    @WebMethod(operationName = "searchStats")
    public List<StatsStruct> searchStats(@WebParam(name = "speciesName") String speciesName, 
                                         @WebParam(name = "dimension") String dimension,
                                         @WebParam(name = "datasetURI") String datasetURI,
                                         @WebParam(name = "repositoryGraph") String repositoryGraph) {
        logger.info("Request for searchStats("+speciesName+","+dimension+","+datasetURI+","+repositoryGraph+")");
        List<StatsStruct> retList=new ArrayList<>();
        try{
            ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
            VirtuosoRepositoryManager repoManager=context.getBean(VirtuosoRepositoryManager.class);
            MetadataRepositoryService api=new MetadataRepositoryService(repoManager);
            retList=api.searchStats(speciesName, dimension, datasetURI, repositoryGraph);
            logger.info("Number of results that will be returned: "+retList.size());
        }catch(QueryExecutionException ex){
            logger.error("An error occured while searching for stats metadata. Returning an empty list.\n", ex);
        }
        return retList;
    }
    
    @WebMethod(operationName = "searchStatsWithinRange")
    public List<StatsStruct> searchStatsWithinRange(@WebParam(name = "speciesName") String speciesName, 
                                                    @WebParam(name = "dimension") String dimension,
                                                    @WebParam(name = "datasetURI") String datasetURI,
                                                    @WebParam(name = "offset") int offset,
                                                    @WebParam(name = "limit") int limit,
                                                    @WebParam(name = "repositoryGraph") String repositoryGraph) {
        logger.info("Request for searchStatsWithinRange("+speciesName+","+dimension+","+datasetURI+","+offset+","+limit+","+repositoryGraph+")");
        List<StatsStruct> retList=new ArrayList<>();
        try{
            ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
            VirtuosoRepositoryManager repoManager=context.getBean(VirtuosoRepositoryManager.class);
            MetadataRepositoryService api=new MetadataRepositoryService(repoManager);
            retList=api.searchStats(speciesName, dimension, datasetURI, offset, limit, repositoryGraph);
            logger.info("Number of results that will be returned: "+retList.size());
        }catch(QueryExecutionException ex){
            logger.error("An error occured while searching for stats metadata. Returning an empty list.\n", ex);
        }
        return retList;
    }
    
    @WebMethod(operationName = "searchSynonym")
    public List<SynonymStruct> searchSynonym(@WebParam(name = "speciesName") String speciesName, 
                                             @WebParam(name = "scientificName") String scientificName,
                                             @WebParam(name = "synonym") String synonym,
                                             @WebParam(name = "datasetURI") String datasetURI,
                                             @WebParam(name = "repositoryGraph") String repositoryGraph) {
        logger.info("Request for searchSynonym("+speciesName+","+scientificName+","+synonym+","+datasetURI+","+repositoryGraph+")");
        List<SynonymStruct> retList=new ArrayList<>();
        try{
            ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
            VirtuosoRepositoryManager repoManager=context.getBean(VirtuosoRepositoryManager.class);
            MetadataRepositoryService api=new MetadataRepositoryService(repoManager);
            retList=api.searchSynonym(speciesName, scientificName, synonym, datasetURI, repositoryGraph);
            logger.info("Number of results that will be returned: "+retList.size());
        }catch(QueryExecutionException ex){
            logger.error("An error occured while searching for synonym metadata. Returning an empty list.\n", ex);
        }
        return retList;
    }
    
    @WebMethod(operationName = "searchSynonymWithinRange")
    public List<SynonymStruct> searchSynonymWithinRange(@WebParam(name = "speciesName") String speciesName, 
                                                        @WebParam(name = "scientificName") String scientificName,
                                                        @WebParam(name = "synonym") String synonym,
                                                        @WebParam(name = "datasetURI") String datasetURI,
                                                        @WebParam(name = "offset") int offset,
                                                        @WebParam(name = "limit") int limit,
                                                        @WebParam(name = "repositoryGraph") String repositoryGraph) {
        logger.info("Request for searchSynonymWithinRange("+speciesName+","+scientificName+","+synonym+","+datasetURI+","+offset+","+limit+","+repositoryGraph+")");
        List<SynonymStruct> retList=new ArrayList<>();
        try{
            ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
            VirtuosoRepositoryManager repoManager=context.getBean(VirtuosoRepositoryManager.class);
            MetadataRepositoryService api=new MetadataRepositoryService(repoManager);
            retList=api.searchSynonym(speciesName, scientificName, synonym, datasetURI, offset, limit, repositoryGraph);
            logger.info("Number of results that will be returned: "+retList.size());
        }catch(QueryExecutionException ex){
            logger.error("An error occured while searching for synonym metadata. Returning an empty list.\n", ex);
        }
        return retList;
    }
    
    @WebMethod(operationName = "searchTaxonomy")
    public List<TaxonomyStruct> searchTaxonomy(@WebParam(name = "speciesName") String speciesName, 
                                               @WebParam(name = "genus") String genus,
                                               @WebParam(name = "family") String family,
                                               @WebParam(name = "order") String order,
                                               @WebParam(name = "classs") String classs,
                                               @WebParam(name = "kingdom") String kingdom,
                                               @WebParam(name = "phylum") String phylum,
                                               @WebParam(name = "datasetURI") String datasetURI,
                                               @WebParam(name = "repositoryGraph") String repositoryGraph) {
        logger.info("Request for searchTaxonomy("+speciesName+","+genus+","+family+","+order+","+classs+","+kingdom+","+phylum+","+datasetURI+","+repositoryGraph+")");
        List<TaxonomyStruct> retList=new ArrayList<>();
        try{
            ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
            VirtuosoRepositoryManager repoManager=context.getBean(VirtuosoRepositoryManager.class);
            MetadataRepositoryService api=new MetadataRepositoryService(repoManager);
            retList=api.searchTaxonomy(speciesName, genus, family, order, classs, kingdom, phylum, datasetURI,0,0, repositoryGraph);
            logger.info("Number of results that will be returned: "+retList.size());
        }catch(QueryExecutionException ex){
            logger.error("An error occured while searching for taxonomy metadata. Returning an empty list.\n", ex);
        }
        return retList;
    }
    
    @WebMethod(operationName = "searchTaxonomyWithinRange")
    public List<TaxonomyStruct> searchTaxonomyWithinRange(@WebParam(name = "speciesName") String speciesName, 
                                                          @WebParam(name = "genus") String genus,
                                                          @WebParam(name = "family") String family,
                                                          @WebParam(name = "order") String order,
                                                          @WebParam(name = "classs") String classs,
                                                          @WebParam(name = "kingdom") String kingdom,
                                                          @WebParam(name = "phylum") String phylum,
                                                          @WebParam(name = "datasetURI") String datasetURI,
                                                          @WebParam(name = "offset") int offset,
                                                          @WebParam(name = "limit") int limit,
                                                          @WebParam(name = "repositoryGraph") String repositoryGraph) {
        logger.info("Request for searchTaxonomyWithinRange("+speciesName+","+genus+","+family+","+order+","+classs+","+kingdom+","+phylum+","+datasetURI+","+offset+","+limit+","+repositoryGraph+")");
        List<TaxonomyStruct> retList=new ArrayList<>();
        try{
            ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
            VirtuosoRepositoryManager repoManager=context.getBean(VirtuosoRepositoryManager.class);
            MetadataRepositoryService api=new MetadataRepositoryService(repoManager);
            retList=api.searchTaxonomy(speciesName, genus, family, order, classs, kingdom, phylum, datasetURI, 0, 0, repositoryGraph);
            logger.info("Number of results that will be returned: "+retList.size());
        }catch(QueryExecutionException ex){
            logger.error("An error occured while searching for taxonomy metadata. Returning an empty list.\n", ex);
        }
        return retList;
    }
    
    @WebMethod(operationName = "searchResource")
    public List<Triple> searchResource(@WebParam(name = "resourceURI") String resourceURI, 
                                       @WebParam(name = "repositoryGraph") String repositoryGraph) {
        logger.info("Request for searchResource("+resourceURI+","+repositoryGraph+")");
        List<Triple> retList=new ArrayList<>();
        try{
            ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
            VirtuosoRepositoryManager repoManager=context.getBean(VirtuosoRepositoryManager.class);
            MetadataRepositoryService api=new MetadataRepositoryService(repoManager);
            retList=api.searchResource(resourceURI, repositoryGraph);
            logger.info("Number of results that will be returned: "+retList.size());
        }catch(QueryExecutionException ex){
            logger.error("An error occured while searching for resources in metadata catalogue. Returning an empty list.\n", ex);
        }
        return retList;
    }
    
    @WebMethod(operationName = "searchLiteral")
    public List<Triple> searchLiteral(@WebParam(name = "literalValue") String literalValue, 
                                      @WebParam(name = "repositoryGraph") String repositoryGraph) {
        logger.info("Request for searchLiteral("+literalValue+","+repositoryGraph+")");
        List<Triple> retList=new ArrayList<>();
        try{
            ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
            VirtuosoRepositoryManager repoManager=context.getBean(VirtuosoRepositoryManager.class);
            MetadataRepositoryService api=new MetadataRepositoryService(repoManager);
            retList=api.searchLiteral(literalValue, repositoryGraph);
            logger.info("Number of results that will be returned: "+retList.size());
        }catch(QueryExecutionException ex){
            logger.error("An error occured while searching for literal values in metadata catalogue. Returning an empty list.\n", ex);
        }
        return retList;
    }
}