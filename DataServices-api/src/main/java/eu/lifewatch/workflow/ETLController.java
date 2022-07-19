package eu.lifewatch.workflow;

import eu.lifewatch.common.Resources;
import eu.lifewatch.core.impl.DwCAHarvester;
import eu.lifewatch.core.impl.DwCArchiveParser;
import eu.lifewatch.core.impl.MicroCTHarvester;
import eu.lifewatch.core.impl.VirtuosoRepositoryManager;
import eu.lifewatch.exception.DataImportException;
import eu.lifewatch.exception.QueryExecutionException;
import eu.lifewatch.exception.URIValidationException;
import eu.lifewatch.exception.WorkflowException;
import gr.forth.ics.isl.timer.Timer;
import gr.forth.ics.isl.x3ml.X3MLEngineFactory;
import lombok.Data;
import lombok.extern.log4j.Log4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.gbif.dwc.MetadataException;
import org.openrdf.rio.RDFFormat;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import org.apache.commons.lang3.tuple.Pair;

@Data
@Log4j
public class ETLController {
    private final String iptMedobisRootFolderPath;
    private final String specimenDataPath;
    private final String scanningDataPath;
    private final VirtuosoRepositoryManager repositoryManager;
    private final String directoryServiceNamedgraph;
    private final String metadataRepositoryNamedgraph;
    private static final String WORKSPACE_FOLDER="workspace";
    private static final String WORKSPACE_MICROCT_FOLDER=WORKSPACE_FOLDER+"/microct";
    private static final String WORKSPACE_IPT_MEDOBIS_FOLDER=WORKSPACE_FOLDER+"/ipt";
    private static final String X3ML_MAPPINGS_SPECIMEN_RESOURCE="x3ml/mappings-specimens.x3ml";
    private static final String X3ML_MAPPINGS_SPECIMEN_DATASET_ONLY_RESOURCE="x3ml/mappings-specimens_dataset_only.x3ml";
    private static final String X3ML_MAPPINGS_SCANNING_RESOURCE="x3ml/mappings-microct-scan.x3ml";
    private static final String X3ML_MAPPINGS_SCANNING_DATASET_ONLY_RESOURCE="x3ml/mappings-microct-scan_dataset_only.x3ml";
    private static final String X3ML_GENERATOR_POLICY_RESOURCE="x3ml/generator-policy.xml";

    public ETLController() throws WorkflowException{
        log.info("Initializing ETLController");
        ApplicationContext context=new ClassPathXmlApplicationContext(Resources.SPRING_BEANS_FILENAME);
        this.iptMedobisRootFolderPath=context.getBean(Resources.IPT_MEDOBIS_ROOT_FOLDER_BEAN,String.class);
        log.debug("Root folder for IPT Medobis: "+this.iptMedobisRootFolderPath);
        this.specimenDataPath=context.getBean(Resources.MICRO_CT_SPECIMENS_BEAN,String.class);
        log.debug("MicroCT Specimens file path: "+this.specimenDataPath);
        this.scanningDataPath=context.getBean(Resources.MICRO_CT_SCANNING_BEAN,String.class);
        log.debug("MicroCT Scanning file path: "+this.scanningDataPath);
        this.repositoryManager=context.getBean(Resources.VIRTUOSO_REPOSITORY_MANAGER_BEAN,VirtuosoRepositoryManager.class);
        log.debug("Virtuoso Repository Manager connection details: "+this.repositoryManager.connectionDetails());
        this.directoryServiceNamedgraph=context.getBean(Resources.DIRECTORY_SERVICE_NAMEGRAPH_BEAN,String.class);
        log.debug("Directory service namedgraph: "+this.directoryServiceNamedgraph);
        this.metadataRepositoryNamedgraph=context.getBean(Resources.METADATA_REPOSITORY_NAMEGRAPH_BEAN,String.class);
        log.debug("Metadata repository namedgraph: "+this.metadataRepositoryNamedgraph);
        this.validateInputResources();

        log.debug("Initialize temporary folders");
        try {
            this.initializeWorkspaceFolders();
        }catch(IOException ex){
            log.error("An error occurred while initializing the temporary workspace folders",ex);
            throw new WorkflowException("An error occurred while initializing the temporary workspace folders",ex);
        }
    }

    /** This method is responsible for creating the temporary workspace folders (if they do not exist), or clean them (if they exist) */
    private void initializeWorkspaceFolders() throws IOException {
        new File(WORKSPACE_FOLDER).mkdir();
        new File(WORKSPACE_IPT_MEDOBIS_FOLDER).mkdir();
        new File(WORKSPACE_MICROCT_FOLDER).mkdir();

        FileUtils.cleanDirectory(new File(WORKSPACE_IPT_MEDOBIS_FOLDER));
        FileUtils.cleanDirectory(new File(WORKSPACE_MICROCT_FOLDER));
    }

    /** Validates that the input resources (IPT Medobis root folder and MicroCT CSV files exist and are valid).

     * @throws WorkflowException if any of the required input resources is either missing or invalid */
    private void validateInputResources() throws WorkflowException{
        log.info("Validating input resources");
        log.debug("Validating IPT Medobis root folder ("+this.iptMedobisRootFolderPath+")");
        if(Files.exists(Paths.get(this.iptMedobisRootFolderPath))){
            if(!Files.isDirectory(Paths.get(this.iptMedobisRootFolderPath))){
                log.error("The provided root folder for IPT Medobis resources ("+this.iptMedobisRootFolderPath+") is not a folder");
                throw new WorkflowException("The provided root folder for IPT Medobis resources ("+this.iptMedobisRootFolderPath+") is not a folder");
            }
        }else{
            log.error("The root folder for the IPT Medobis resources ("+this.iptMedobisRootFolderPath+") does not exist");
            throw new WorkflowException("The root folder for the IPT Medobis resources ("+this.iptMedobisRootFolderPath+") does not exist");
        }
        log.debug("Validating MicroCT Specimen CSV resources ("+this.specimenDataPath+")");
        if(!Files.isRegularFile(Paths.get(this.specimenDataPath))){
            log.error("The provided CSV file with MicroCT specimens ("+this.specimenDataPath+") does not exist");
            throw new WorkflowException("The provided CSV file with MicroCT specimens ("+this.specimenDataPath+") does not exist");
        }
        if(!Files.isRegularFile(Paths.get(this.scanningDataPath))){
            log.error("The provided CSV file with MicroCT scannings ("+this.scanningDataPath+") does not exist");
            throw new WorkflowException("The provided CSV file with MicroCT scannings ("+this.scanningDataPath+") does not exist");
        }
    }

    /** This method is responsible for Refreshing the repositories (directory and metadata) with fresh contents
     * from the IPT Medobis and MicroCT Scan. In a nutshell the activities that are executed here are:
     * (a) harvest data from IPT Medobis, (b) transform data from IPT Medobis, (c) transform data from MicroCT scann
     * (d) remove old namedgraphs from the triplestore (e) import transformed data (from IPT Medobis and MicroCT) to the triplestore
     * @throws WorkflowException for any error that might occur while refreshing the repositories */
    public void doRefresh() throws WorkflowException {
        try {
            log.info("Step 1/3: Clear namedgraphs with old contents");
            Timer.start(ETLController.class.toString() + ".clear_namedgraphs");
            log.debug("Clear namedgraph "+this.directoryServiceNamedgraph);
            this.repositoryManager.clearGraph(this.directoryServiceNamedgraph);
            log.debug("Clear namedgraph "+this.metadataRepositoryNamedgraph);
            this.repositoryManager.clearGraph(this.metadataRepositoryNamedgraph);
            Timer.stop(ETLController.class.toString() + ".clear_namedgraphs");

            log.info("Step 2/3: Harvest, Transform and ingest data from IPT Medobis");
            Timer.start(ETLController.class.toString() + ".harvest_ipt_medobis");
            Collection<Pair<File,String>> archiveFiles = new DwCAHarvester(this.iptMedobisRootFolderPath).locateDwCaArchives();
            log.debug("Found " + archiveFiles.size() + " DwCA archives from IPT Medobis root folder");
            for (Pair<File,String> archive : archiveFiles) {
                new DwCArchiveParser(archive.getLeft(), archive.getRight(), true, true,this.directoryServiceNamedgraph,this.metadataRepositoryNamedgraph).parseData();
            }
            Collection<Pair<File,String>> metadataOnlyFiles = new DwCAHarvester(this.iptMedobisRootFolderPath).locateDwCaMetadata();
            log.debug("Found " + metadataOnlyFiles.size() + " metadata only files from IPT Medobis root folder");
            for (Pair<File,String> metadataFile : metadataOnlyFiles) {
                new DwCArchiveParser(metadataFile.getLeft(), metadataFile.getRight(), true, true,this.directoryServiceNamedgraph).parseOnlyMetadata();
            }
            Timer.stop(ETLController.class.toString() + ".harvest_ipt_medobis");

            log.info("Step 3/3: Harvest, Transform and ingest data from MicroCT");
            Timer.start(ETLController.class.toString() + ".transform_microct");
            MicroCTHarvester.xmlifyMicroCtResources(new File(this.getSpecimenDataPath()),
                                                    new File(WORKSPACE_MICROCT_FOLDER+"/"+FilenameUtils.getBaseName(this.getSpecimenDataPath())+".xml"),
                                                    MicroCTHarvester.MicroCTResourceType.MicroCT_Specimens);
            MicroCTHarvester.xmlifyMicroCtResources(new File(this.getScanningDataPath()),
                    new File(WORKSPACE_MICROCT_FOLDER+"/"+FilenameUtils.getBaseName(this.getScanningDataPath())+".xml"),
                    MicroCTHarvester.MicroCTResourceType.MicroCT_Scanning);
            X3MLEngineFactory.create()
                    .withMappings(ETLController.class.getClassLoader().getResourceAsStream(X3ML_MAPPINGS_SPECIMEN_DATASET_ONLY_RESOURCE))
                    .withGeneratorPolicy(ETLController.class.getClassLoader().getResourceAsStream(X3ML_GENERATOR_POLICY_RESOURCE))
                    .withInputFiles(new File(WORKSPACE_MICROCT_FOLDER+"/"+FilenameUtils.getBaseName(this.getSpecimenDataPath())+".xml"))
                    .withOutput(new File(WORKSPACE_MICROCT_FOLDER+"/"+FilenameUtils.getBaseName(this.getSpecimenDataPath())+"_dataset_only.rdf"),X3MLEngineFactory.OutputFormat.RDF_XML)
                    .execute();
            X3MLEngineFactory.create()
                    .withMappings(ETLController.class.getClassLoader().getResourceAsStream(X3ML_MAPPINGS_SCANNING_DATASET_ONLY_RESOURCE))
                    .withGeneratorPolicy(ETLController.class.getClassLoader().getResourceAsStream(X3ML_GENERATOR_POLICY_RESOURCE))
                    .withInputFiles(new File(WORKSPACE_MICROCT_FOLDER+"/"+FilenameUtils.getBaseName(this.getScanningDataPath())+".xml"))
                    .withOutput(new File(WORKSPACE_MICROCT_FOLDER+"/"+FilenameUtils.getBaseName(this.getScanningDataPath())+"_dataset_only.rdf"),X3MLEngineFactory.OutputFormat.RDF_XML)
                    .execute();
            X3MLEngineFactory.create()
                    .withMappings(ETLController.class.getClassLoader().getResourceAsStream(X3ML_MAPPINGS_SPECIMEN_RESOURCE))
                    .withGeneratorPolicy(ETLController.class.getClassLoader().getResourceAsStream(X3ML_GENERATOR_POLICY_RESOURCE))
                    .withInputFiles(new File(WORKSPACE_MICROCT_FOLDER+"/"+FilenameUtils.getBaseName(this.getSpecimenDataPath())+".xml"))
                    .withOutput(new File(WORKSPACE_MICROCT_FOLDER+"/"+FilenameUtils.getBaseName(this.getSpecimenDataPath())+".rdf"),X3MLEngineFactory.OutputFormat.RDF_XML)
                    .execute();
            X3MLEngineFactory.create()
                    .withMappings(ETLController.class.getClassLoader().getResourceAsStream(X3ML_MAPPINGS_SCANNING_RESOURCE))
                    .withGeneratorPolicy(ETLController.class.getClassLoader().getResourceAsStream(X3ML_GENERATOR_POLICY_RESOURCE))
                    .withInputFiles(new File(WORKSPACE_MICROCT_FOLDER+"/"+FilenameUtils.getBaseName(this.getScanningDataPath())+".xml"))
                    .withOutput(new File(WORKSPACE_MICROCT_FOLDER+"/"+FilenameUtils.getBaseName(this.getScanningDataPath())+".rdf"),X3MLEngineFactory.OutputFormat.RDF_XML)
                    .execute();
            this.repositoryManager.importData(new File(WORKSPACE_MICROCT_FOLDER+"/"+FilenameUtils.getBaseName(this.getSpecimenDataPath())+"_dataset_only.rdf"),RDFFormat.RDFXML,this.directoryServiceNamedgraph);
            this.repositoryManager.importData(new File(WORKSPACE_MICROCT_FOLDER+"/"+FilenameUtils.getBaseName(this.getScanningDataPath())+"_dataset_only.rdf"),RDFFormat.RDFXML,this.directoryServiceNamedgraph);
            this.repositoryManager.importData(new File(WORKSPACE_MICROCT_FOLDER+"/"+FilenameUtils.getBaseName(this.getSpecimenDataPath())+".rdf"),RDFFormat.RDFXML,this.metadataRepositoryNamedgraph);
            this.repositoryManager.importData(new File(WORKSPACE_MICROCT_FOLDER+"/"+FilenameUtils.getBaseName(this.getScanningDataPath())+".rdf"),RDFFormat.RDFXML,this.metadataRepositoryNamedgraph);
            Timer.stop(ETLController.class.toString() + ".transform_microct");
        }catch(IOException | URIValidationException | QueryExecutionException | MetadataException | ParserConfigurationException | TransformerException | DataImportException ex){
            log.error("An error occurred while harvesting, transforming and ingesting resources from input resources",ex);
            throw new WorkflowException("An error occurred while harvesting, transforming and ingesting resources from input resources",ex);
        }
        log.info("Finished Refreshing LW Repositories in "+Timer.reportHumanFriendly(ETLController.class.toString()));
    }

    /** This method is responsible for generating and reporting statistics about the execution of the ETL workflow */
    public void reportStatistics(){
        log.info("ETL Report statistics");
        log.info("---------------------");
        log.info("Namedgraphs clean time: "+Timer.reportHumanFriendly(ETLController.class.toString()+".clear_namedgraphs"));
        log.info("ETL (IPT Medobis) time: "+Timer.reportHumanFriendly(ETLController.class.toString()+".harvest_ipt_medobis"));
        log.info("ETL (MicroCT) time: "+Timer.reportHumanFriendly(ETLController.class.toString()+".transform_microct"));
        log.info("ETL time: "+Timer.reportHumanFriendly(ETLController.class.toString()));
    }

    public static void main(String[] args) throws WorkflowException {
        ETLController etl=new ETLController();
        etl.doRefresh();
        etl.reportStatistics();
    }
}
