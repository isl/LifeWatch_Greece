package eu.lifewatch.dataservices.middleware.metadatarepository;

import eu.lifewatch.core.impl.VirtuosoRepositoryManager;
import eu.lifewatch.core.model.CommonNameStruct;
import eu.lifewatch.core.model.DirectoryStruct;
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
import eu.lifewatch.core.model.OccurrenceStatsAbundanceStruct;
import eu.lifewatch.core.model.OccurrenceStatsTempStruct;
import eu.lifewatch.core.model.OccurrenceStruct;
import eu.lifewatch.core.model.ScientificNamingStruct;
import eu.lifewatch.core.model.SpecimenCollectionStruct;
import eu.lifewatch.core.model.SpecimenStruct;
import eu.lifewatch.core.model.StatsStruct;
import eu.lifewatch.core.model.SynonymStruct;
import eu.lifewatch.core.model.TaxonomyStruct;
import eu.lifewatch.exception.QueryExecutionException;
import eu.lifewatch.exception.URIValidationException;
import eu.lifewatch.service.impl.MetadataRepositoryService;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * MetadataRepository-Insert provides the functionality to add new information to the metadata catalogue. 
 * For more information about the usage of the SOAP services and the contents and 
 * manipulation of the returned structs please refer to the javadoc documentation of the 
 * DataServices-api.
 *
 * @author Yannis Marketakis (marketak 'at' ics 'dot' forth 'dot' gr)
 * @author Nikos Minadakis (minadakn 'at' ics 'dot' forth 'dot' gr)
 */
@WebService(serviceName = "MetadataRepository_Insert")
public class MetadataRepository_Insert {
    private static final Logger logger=Logger.getLogger(MetadataRepository_Insert.class);

    @WebMethod(operationName = "insert")
    public boolean insert(@WebParam(name = "struct") DirectoryStruct struct,
                         @WebParam(name = "repositoryGraph") String repositoryGraph){
        logger.info("Request for insert("+struct.getDatasetURI()+"...,"+repositoryGraph+")");
        try{
            ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
            VirtuosoRepositoryManager repoManager=context.getBean(VirtuosoRepositoryManager.class);
            MetadataRepositoryService api=new MetadataRepositoryService(repoManager);
            api.insertStruct(struct, repositoryGraph);
            logger.debug("Sucessfully added the given struct - returning TRUE");
            return true;
        }catch(BeansException | QueryExecutionException | URIValidationException ex){
            logger.error("An error occured while adding a new struct to the metadata catalogue",ex);
            logger.debug("The triple has not been removed - returning FALSE");
            return false;
        }   
    }

    @WebMethod(operationName = "insertMeasurement")
    public boolean insertMeasurement(@WebParam(name = "struct") MeasurementStruct struct,
                         @WebParam(name = "repositoryGraph") String repositoryGraph){
        logger.info("Request for insertMeasurement("+struct.getMeasurementEventURI()+"...,"+repositoryGraph+")");
        try{
            ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
            VirtuosoRepositoryManager repoManager=context.getBean(VirtuosoRepositoryManager.class);
            MetadataRepositoryService api=new MetadataRepositoryService(repoManager);
            api.insertStruct(struct, repositoryGraph);
            logger.debug("Sucessfully added the given struct - returning TRUE");
            return true;
        }catch(BeansException | QueryExecutionException | URIValidationException ex){
            logger.error("An error occured while adding a new struct to the metadata catalogue",ex);
            logger.debug("The triple has not been removed - returning FALSE");
            return false;
        }   
    }

    @WebMethod(operationName = "insertCommonName")
    public boolean insertCommonName(@WebParam(name = "struct") CommonNameStruct struct,
                         @WebParam(name = "repositoryGraph") String repositoryGraph){
        logger.info("Request for insertCommonName("+struct.getCommonNameURI()+"...,"+repositoryGraph+")");
        try{
            ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
            VirtuosoRepositoryManager repoManager=context.getBean(VirtuosoRepositoryManager.class);
            MetadataRepositoryService api=new MetadataRepositoryService(repoManager);
            api.insertStruct(struct, repositoryGraph);
            logger.debug("Sucessfully added the given struct - returning TRUE");
            return true;
        }catch(BeansException | QueryExecutionException | URIValidationException ex){
            logger.error("An error occured while adding a new struct to the metadata catalogue",ex);
            logger.debug("The triple has not been removed - returning FALSE");
            return false;
        }   
    }

    @WebMethod(operationName = "insertEnvironmental")
    public boolean insertEnvironmental(@WebParam(name = "struct") EnvironmentalStruct struct,
                         @WebParam(name = "repositoryGraph") String repositoryGraph){
        logger.info("Request for insertEnvironmental("+struct.getMeasurementEventURI()+"...,"+repositoryGraph+")");
        try{
            ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
            VirtuosoRepositoryManager repoManager=context.getBean(VirtuosoRepositoryManager.class);
            MetadataRepositoryService api=new MetadataRepositoryService(repoManager);
            api.insertStruct(struct, repositoryGraph);
            logger.debug("Sucessfully added the given struct - returning TRUE");
            return true;
        }catch(BeansException | QueryExecutionException | URIValidationException ex){
            logger.error("An error occured while adding a new struct to the metadata catalogue",ex);
            logger.debug("The triple has not been removed - returning FALSE");
            return false;
        }   
    }

    @WebMethod(operationName = "insertIdentification")
    public boolean insertIdentification(@WebParam(name = "struct") IdentificationStruct struct,
                         @WebParam(name = "repositoryGraph") String repositoryGraph){
        logger.info("Request for insertIdentification("+struct.getIdentificationEventURI()+"...,"+repositoryGraph+")");
        try{
            ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
            VirtuosoRepositoryManager repoManager=context.getBean(VirtuosoRepositoryManager.class);
            MetadataRepositoryService api=new MetadataRepositoryService(repoManager);
            api.insertStruct(struct, repositoryGraph);
            logger.debug("Sucessfully added the given struct - returning TRUE");
            return true;
        }catch(BeansException | QueryExecutionException | URIValidationException ex){
            logger.error("An error occured while adding a new struct to the metadata catalogue",ex);
            logger.debug("The triple has not been removed - returning FALSE");
            return false;
        }   
    }

    @WebMethod(operationName = "insertScientificNaming")
    public boolean insertScientificNaming(@WebParam(name = "struct") ScientificNamingStruct struct,
                         @WebParam(name = "repositoryGraph") String repositoryGraph){
        logger.info("Request for insertScientificNaming("+struct.getScientificNameAssignmentEventURI()+"...,"+repositoryGraph+")");
        try{
            ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
            VirtuosoRepositoryManager repoManager=context.getBean(VirtuosoRepositoryManager.class);
            MetadataRepositoryService api=new MetadataRepositoryService(repoManager);
            api.insertStruct(struct, repositoryGraph);
            logger.debug("Sucessfully added the given struct - returning TRUE");
            return true;
        }catch(BeansException | QueryExecutionException | URIValidationException ex){
            logger.error("An error occured while adding a new struct to the metadata catalogue",ex);
            logger.debug("The triple has not been removed - returning FALSE");
            return false;
        }   
    }

    @WebMethod(operationName = "insertTaxonomy")
    public boolean insertTaxonomy(@WebParam(name = "struct") TaxonomyStruct struct,
                         @WebParam(name = "repositoryGraph") String repositoryGraph){
        logger.info("Request for insertTaxonomy("+struct.getSpeciesURI()+"...,"+repositoryGraph+")");
        try{
            ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
            VirtuosoRepositoryManager repoManager=context.getBean(VirtuosoRepositoryManager.class);
            MetadataRepositoryService api=new MetadataRepositoryService(repoManager);
            api.insertStruct(struct, repositoryGraph);
            logger.debug("Sucessfully added the given struct - returning TRUE");
            return true;
        }catch(BeansException | QueryExecutionException | URIValidationException ex){
            logger.error("An error occured while adding a new struct to the metadata catalogue",ex);
            logger.debug("The triple has not been removed - returning FALSE");
            return false;
        }   
    }

    @WebMethod(operationName = "insertMicroCTPostProcessing")
    public boolean insertMicroCTPostProcessing(@WebParam(name = "struct") MicroCTPostProcessingStruct struct,
                         @WebParam(name = "repositoryGraph") String repositoryGraph){
        logger.info("Request for insertMicroCTPostProcessing("+struct.getPostProcessingURI()+"...,"+repositoryGraph+")");
        try{
            ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
            VirtuosoRepositoryManager repoManager=context.getBean(VirtuosoRepositoryManager.class);
            MetadataRepositoryService api=new MetadataRepositoryService(repoManager);
            api.insertStruct(struct, repositoryGraph);
            logger.debug("Sucessfully added the given struct - returning TRUE");
            return true;
        }catch(BeansException | QueryExecutionException | URIValidationException ex){
            logger.error("An error occured while adding a new struct to the metadata catalogue",ex);
            logger.debug("The triple has not been removed - returning FALSE");
            return false;
        }   
    }

    @WebMethod(operationName = "insertGensDataset")
    public boolean insertGensDataset(@WebParam(name = "struct") GensDatasetStruct struct,
                         @WebParam(name = "repositoryGraph") String repositoryGraph){
        logger.info("Request for insertGensDataset("+struct.getDatasetURI()+"...,"+repositoryGraph+")");
        try{
            ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
            VirtuosoRepositoryManager repoManager=context.getBean(VirtuosoRepositoryManager.class);
            MetadataRepositoryService api=new MetadataRepositoryService(repoManager);
            api.insertStruct(struct, repositoryGraph);
            logger.debug("Sucessfully added the given struct - returning TRUE");
            return true;
        }catch(BeansException | QueryExecutionException | URIValidationException ex){
            logger.error("An error occured while adding a new struct to the metadata catalogue",ex);
            logger.debug("The triple has not been removed - returning FALSE");
            return false;
        }   
    }

    @WebMethod(operationName = "insertOccurrenceStatsTemp")
    public boolean insertOccurrenceStatsTemp(@WebParam(name = "struct") OccurrenceStatsTempStruct struct,
                         @WebParam(name = "repositoryGraph") String repositoryGraph){
        logger.info("Request for insertOccurrenceStatsTemp("+struct.getOccurrenceEventURI()+"...,"+repositoryGraph+")");
        try{
            ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
            VirtuosoRepositoryManager repoManager=context.getBean(VirtuosoRepositoryManager.class);
            MetadataRepositoryService api=new MetadataRepositoryService(repoManager);
            api.insertStruct(struct, repositoryGraph);
            logger.debug("Sucessfully added the given struct - returning TRUE");
            return true;
        }catch(BeansException | QueryExecutionException | URIValidationException ex){
            logger.error("An error occured while adding a new struct to the metadata catalogue",ex);
            logger.debug("The triple has not been removed - returning FALSE");
            return false;
        }   
    }

    @WebMethod(operationName = "insertSpecimen")
    public boolean insertSpecimen(@WebParam(name = "struct") SpecimenStruct struct,
                         @WebParam(name = "repositoryGraph") String repositoryGraph){
        logger.info("Request for insertSpecimen("+struct.getSpecimenURI()+"...,"+repositoryGraph+")");
        try{
            ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
            VirtuosoRepositoryManager repoManager=context.getBean(VirtuosoRepositoryManager.class);
            MetadataRepositoryService api=new MetadataRepositoryService(repoManager);
            api.insertStruct(struct, repositoryGraph);
            logger.debug("Sucessfully added the given struct - returning TRUE");
            return true;
        }catch(BeansException | QueryExecutionException | URIValidationException ex){
            logger.error("An error occured while adding a new struct to the metadata catalogue",ex);
            logger.debug("The triple has not been removed - returning FALSE");
            return false;
        }   
    }

    @WebMethod(operationName = "insertSpecimenCollection")
    public boolean insertSpecimenCollection(@WebParam(name = "struct") SpecimenCollectionStruct struct,
                         @WebParam(name = "repositoryGraph") String repositoryGraph){
        logger.info("Request for insertSpecimenCollection("+struct.getCollectionURI()+"...,"+repositoryGraph+")");
        try{
            ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
            VirtuosoRepositoryManager repoManager=context.getBean(VirtuosoRepositoryManager.class);
            MetadataRepositoryService api=new MetadataRepositoryService(repoManager);
            api.insertStruct(struct, repositoryGraph);
            logger.debug("Sucessfully added the given struct - returning TRUE");
            return true;
        }catch(BeansException | QueryExecutionException | URIValidationException ex){
            logger.error("An error occured while adding a new struct to the metadata catalogue",ex);
            logger.debug("The triple has not been removed - returning FALSE");
            return false;
        }   
    }

    @WebMethod(operationName = "insertMicroCTReconstruction")
    public boolean insertMicroCTReconstruction(@WebParam(name = "struct") MicroCTReconstructionStruct struct,
                         @WebParam(name = "repositoryGraph") String repositoryGraph){
        logger.info("Request for insertMicroCTReconstruction("+struct.getReconstructionURI()+"...,"+repositoryGraph+")");
        try{
            ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
            VirtuosoRepositoryManager repoManager=context.getBean(VirtuosoRepositoryManager.class);
            MetadataRepositoryService api=new MetadataRepositoryService(repoManager);
            api.insertStruct(struct, repositoryGraph);
            logger.debug("Sucessfully added the given struct - returning TRUE");
            return true;
        }catch(BeansException | QueryExecutionException | URIValidationException ex){
            logger.error("An error occured while adding a new struct to the metadata catalogue",ex);
            logger.debug("The triple has not been removed - returning FALSE");
            return false;
        }   
    }

    @WebMethod(operationName = "insertMicroCTScanning")
    public boolean insertMicroCTScanning(@WebParam(name = "struct") MicroCTScanningStruct struct,
                         @WebParam(name = "repositoryGraph") String repositoryGraph){
        logger.info("Request for insertMicroCTScanning("+struct.getScanningURI()+"...,"+repositoryGraph+")");
        try{
            ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
            VirtuosoRepositoryManager repoManager=context.getBean(VirtuosoRepositoryManager.class);
            MetadataRepositoryService api=new MetadataRepositoryService(repoManager);
            api.insertStruct(struct, repositoryGraph);
            logger.debug("Sucessfully added the given struct - returning TRUE");
            return true;
        }catch(BeansException | QueryExecutionException | URIValidationException ex){
            logger.error("An error occured while adding a new struct to the metadata catalogue",ex);
            logger.debug("The triple has not been removed - returning FALSE");
            return false;
        }   
    }

    @WebMethod(operationName = "insertStats")
    public boolean insertStats(@WebParam(name = "struct") StatsStruct struct,
                         @WebParam(name = "repositoryGraph") String repositoryGraph){
        logger.info("Request for insertStats("+struct.getDataEvaluationURI()+"...,"+repositoryGraph+")");
        try{
            ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
            VirtuosoRepositoryManager repoManager=context.getBean(VirtuosoRepositoryManager.class);
            MetadataRepositoryService api=new MetadataRepositoryService(repoManager);
            api.insertStruct(struct, repositoryGraph);
            logger.debug("Sucessfully added the given struct - returning TRUE");
            return true;
        }catch(BeansException | QueryExecutionException | URIValidationException ex){
            logger.error("An error occured while adding a new struct to the metadata catalogue",ex);
            logger.debug("The triple has not been removed - returning FALSE");
            return false;
        }   
    }

    @WebMethod(operationName = "insertGensSample")
    public boolean insertGensSample(@WebParam(name = "struct") GensSampleStruct struct,
                         @WebParam(name = "repositoryGraph") String repositoryGraph){
        logger.info("Request for insertGensSample("+struct.getSampleURI()+"...,"+repositoryGraph+")");
        try{
            ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
            VirtuosoRepositoryManager repoManager=context.getBean(VirtuosoRepositoryManager.class);
            MetadataRepositoryService api=new MetadataRepositoryService(repoManager);
            api.insertStruct(struct, repositoryGraph);
            logger.debug("Sucessfully added the given struct - returning TRUE");
            return true;
        }catch(BeansException | QueryExecutionException | URIValidationException ex){
            logger.error("An error occured while adding a new struct to the metadata catalogue",ex);
            logger.debug("The triple has not been removed - returning FALSE");
            return false;
        }   
    }

    @WebMethod(operationName = "insertMicroCTSpecimen")
    public boolean insertMicroCTSpecimen(@WebParam(name = "struct") MicroCTSpecimenStruct struct,
                         @WebParam(name = "repositoryGraph") String repositoryGraph){
        logger.info("Request for insertMicroCTSpecimen("+struct.getSpecimenURI()+"...,"+repositoryGraph+")");
        try{
            ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
            VirtuosoRepositoryManager repoManager=context.getBean(VirtuosoRepositoryManager.class);
            MetadataRepositoryService api=new MetadataRepositoryService(repoManager);
            api.insertStruct(struct, repositoryGraph);
            logger.debug("Sucessfully added the given struct - returning TRUE");
            return true;
        }catch(BeansException | QueryExecutionException | URIValidationException ex){
            logger.error("An error occured while adding a new struct to the metadata catalogue",ex);
            logger.debug("The triple has not been removed - returning FALSE");
            return false;
        }   
    }

    @WebMethod(operationName = "insertOccurrenceStatsAbundance")
    public boolean insertOccurrenceStatsAbundance(@WebParam(name = "struct") OccurrenceStatsAbundanceStruct struct,
                         @WebParam(name = "repositoryGraph") String repositoryGraph){
        logger.info("Request for insertOccurrenceStatsAbundance("+struct.getDatasetURI()+"...,"+repositoryGraph+")");
        try{
            ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
            VirtuosoRepositoryManager repoManager=context.getBean(VirtuosoRepositoryManager.class);
            MetadataRepositoryService api=new MetadataRepositoryService(repoManager);
            api.insertStruct(struct, repositoryGraph);
            logger.debug("Sucessfully added the given struct - returning TRUE");
            return true;
        }catch(BeansException | QueryExecutionException | URIValidationException ex){
            logger.error("An error occured while adding a new struct to the metadata catalogue",ex);
            logger.debug("The triple has not been removed - returning FALSE");
            return false;
        }   
    }

    @WebMethod(operationName = "insertMorphometrics")
    public boolean insertMorphometrics(@WebParam(name = "struct") MorphometricsStruct struct,
                         @WebParam(name = "repositoryGraph") String repositoryGraph){
        logger.info("Request for insertMorphometrics("+struct.getAttributeAssignmentEventURI()+"...,"+repositoryGraph+")");
        try{
            ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
            VirtuosoRepositoryManager repoManager=context.getBean(VirtuosoRepositoryManager.class);
            MetadataRepositoryService api=new MetadataRepositoryService(repoManager);
            api.insertStruct(struct, repositoryGraph);
            logger.debug("Sucessfully added the given struct - returning TRUE");
            return true;
        }catch(BeansException | QueryExecutionException | URIValidationException ex){
            logger.error("An error occured while adding a new struct to the metadata catalogue",ex);
            logger.debug("The triple has not been removed - returning FALSE");
            return false;
        }   
    }

    @WebMethod(operationName = "insertOccurrence")
    public boolean insertOccurrence(@WebParam(name = "struct") OccurrenceStruct struct,
                         @WebParam(name = "repositoryGraph") String repositoryGraph){
        logger.info("Request for insertOccurrence("+struct.getOccurrenceEventURI()+"...,"+repositoryGraph+")");
        try{
            ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
            VirtuosoRepositoryManager repoManager=context.getBean(VirtuosoRepositoryManager.class);
            MetadataRepositoryService api=new MetadataRepositoryService(repoManager);
            api.insertStruct(struct, repositoryGraph);
            logger.debug("Sucessfully added the given struct - returning TRUE");
            return true;
        }catch(BeansException | QueryExecutionException | URIValidationException ex){
            logger.error("An error occured while adding a new struct to the metadata catalogue",ex);
            logger.debug("The triple has not been removed - returning FALSE");
            return false;
        }   
    }

    @WebMethod(operationName = "insertSynonym")
    public boolean insertSynonym(@WebParam(name = "struct") SynonymStruct struct,
                         @WebParam(name = "repositoryGraph") String repositoryGraph){
        logger.info("Request for insertSynonym("+struct.getSynonymURIs()+"...,"+repositoryGraph+")");
        try{
            ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
            VirtuosoRepositoryManager repoManager=context.getBean(VirtuosoRepositoryManager.class);
            MetadataRepositoryService api=new MetadataRepositoryService(repoManager);
            api.insertStruct(struct, repositoryGraph);
            logger.debug("Sucessfully added the given struct - returning TRUE");
            return true;
        }catch(BeansException | QueryExecutionException | URIValidationException ex){
            logger.error("An error occured while adding a new struct to the metadata catalogue",ex);
            logger.debug("The triple has not been removed - returning FALSE");
            return false;
        }   
    }
}