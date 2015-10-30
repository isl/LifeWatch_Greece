package eu.lifewatch.service.impl;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import org.apache.log4j.Logger;
import org.irods.jargon.core.connection.IRODSAccount;
import org.irods.jargon.core.connection.JargonProperties;
import org.irods.jargon.core.exception.JargonException;
import org.irods.jargon.core.packinstr.TransferOptions;
import org.irods.jargon.core.packinstr.TransferOptions.ForceOption;
import org.irods.jargon.core.pub.DataObjectAO;
import org.irods.jargon.core.pub.DataTransferOperations;
import org.irods.jargon.core.pub.IRODSFileSystem;
import org.irods.jargon.core.pub.domain.AvuData;
import org.irods.jargon.core.pub.domain.DataObject;
import org.irods.jargon.core.pub.io.IRODSFile;
import org.irods.jargon.core.pub.io.IRODSFileFactory;
import org.irods.jargon.core.query.AVUQueryElement;
import org.irods.jargon.core.query.AVUQueryOperatorEnum;
import org.irods.jargon.core.query.JargonQueryException;
import org.irods.jargon.core.query.MetaDataAndDomainData;
import org.irods.jargon.core.transfer.DefaultTransferControlBlock;
import org.irods.jargon.core.transfer.TransferControlBlock;
import org.irods.jargon.core.transfer.TransferStatus;
import org.irods.jargon.core.transfer.TransferStatusCallbackListener;
import org.irods.jargon.core.transfer.TransferStatusCallbackListener.CallbackResponse;

/**
 * @author Nikos Minadakis (minadakn 'at' ics 'dot' forth 'dot' gr)
 */
public class ContentStorageService {

    private static final String USERNAME_LABEL = "username";
    private static final String DATASET_URI_LABEL = "datasetURI";
    private static final String CREATION_DATE_LABEL = "creationDate";
    private static final String DATASET_TITLE_LABEL = "datasetTitle";
    private static final String DATASET_TYPE_LABEL = "datasetType";
    private static final Logger logger = Logger.getLogger(ContentStorageService.class);

    public IRODSAccount connect_to_content_storage(String host, int port, String username, String password, String path, String zone, String demoResc)
            throws JargonException {
        logger.debug("Connecting to IRODS-Server[\n\tHost: " + host
                + "\tPort: " + port
                + "\tUsername: " + username
                + "\tPassword: " + password
                + "\tPath: " + path
                + "\tZone: " + zone
                + "\tResource: " + demoResc);
        IRODSAccount account = new IRODSAccount(host, 1247, username, password, path, zone, demoResc);
        return account;
    }

    public void create_user_folder(IRODSAccount account, String sourceFilePath, String irodsPath,
            String targetFileName, String userName, String datasetURI, String creationDate, String datasetName,
            String datasetType)
            throws JargonException {
        logger.debug("Importing to IRODS-Server[\n\tiRODS account: " + account.toString()
                + "\tSourceFilePath: " + sourceFilePath
                + "\tiRODSPath: " + irodsPath
                + "\tTargetFileName: " + targetFileName
                + "\tUsername: " + userName
                + "\tDatasetURI: " + datasetURI
                + "\tDatasetName: " + datasetName
                + "\tCreationDate: " + creationDate);

        IRODSFileSystem irodsFileSystem = IRODSFileSystem.instance();

        DataTransferOperations dataTransferOperations = irodsFileSystem.getIRODSAccessObjectFactory().getDataTransferOperations(account);

        File sourceFile = new File(sourceFilePath);

        IRODSFileFactory irodsFileFactory = irodsFileSystem.getIRODSFileFactory(account);

        //newFileName = "/tempZone/home/rods/"+newFileName;
        String userFolderPath = irodsPath + "/" + userName + "/";

        IRODSFile targetFileFolder = irodsFileFactory.instanceIRODSFile(userFolderPath);

        targetFileFolder.mkdir();

        String targetFilePath = userFolderPath + "/" + datasetName + "/";

        IRODSFile targetFile = irodsFileFactory.instanceIRODSFile(targetFilePath);

        targetFile.mkdir();

    }

    public void import_to_content_storage(IRODSAccount account, String sourceFilePath, String irodsPath,
            String targetFileName, String userName, String datasetURI, String creationDate, String datasetName,
            String datasetType)
            throws JargonException {
        logger.debug("Importing to IRODS-Server[\n\tiRODS account: " + account.toString()
                + "\tSourceFilePath: " + sourceFilePath
                + "\tiRODSPath: " + irodsPath
                + "\tTargetFileName: " + targetFileName
                + "\tUsername: " + userName
                + "\tDatasetURI: " + datasetURI
                + "\tDatasetName: " + datasetName
                + "\tCreationDate: " + creationDate);

        IRODSFileSystem irodsFileSystem = IRODSFileSystem.instance();

        DataTransferOperations dataTransferOperations = irodsFileSystem.getIRODSAccessObjectFactory().getDataTransferOperations(account);

        File sourceFile = new File(sourceFilePath);

        IRODSFileFactory irodsFileFactory = irodsFileSystem.getIRODSFileFactory(account);

        //newFileName = "/tempZone/home/rods/"+newFileName;
        String userFolderPath = irodsPath + "/" + userName + "/";

        IRODSFile targetFileFolder = irodsFileFactory.instanceIRODSFile(userFolderPath);

        targetFileFolder.mkdir();

        String targetFilePath = userFolderPath + "/" + datasetName + "/";

        IRODSFile targetFile = irodsFileFactory.instanceIRODSFile(targetFilePath);

        targetFile.mkdir();

        // targetFilePath = irodsPath + targetFileName; 
        targetFilePath = targetFilePath + targetFileName;

        targetFile = irodsFileFactory.instanceIRODSFile(targetFilePath);

        DataTransferOperations dataTransferOperationsAO = irodsFileSystem.getIRODSAccessObjectFactory().getDataTransferOperations(account);
    
        dataTransferOperationsAO.putOperation(sourceFile,targetFile,null,null);

        DataObjectAO dataObjectAO = irodsFileSystem.getIRODSAccessObjectFactory().getDataObjectAO(account);

        dataObjectAO.findByAbsolutePath(targetFile.getAbsolutePath());

        AvuData avuData = new AvuData();
        avuData.setAttribute(USERNAME_LABEL);
        avuData.setValue(userName);

        AvuData avuData2 = new AvuData();
        avuData2.setAttribute(DATASET_URI_LABEL);
        avuData2.setValue(datasetURI);

        AvuData avuData3 = new AvuData();
        avuData3.setAttribute(CREATION_DATE_LABEL);
        avuData3.setValue(creationDate);

        AvuData avuData4 = new AvuData();
        avuData4.setAttribute(DATASET_TITLE_LABEL);
        avuData4.setValue(datasetName);

        AvuData avuData5 = new AvuData();
        avuData5.setAttribute(DATASET_TYPE_LABEL);
        avuData5.setValue(datasetType);

        List<AvuData> metadataList = new ArrayList();

        metadataList.add(avuData);
        metadataList.add(avuData5);
        metadataList.add(avuData2);
        metadataList.add(avuData3);
        metadataList.add(avuData4);

        dataObjectAO.addBulkAVUMetadataToDataObject(targetFile.getAbsolutePath(), metadataList);
        //dataObjectAO.addAVUMetadata(targetFile.getAbsolutePath(), avuData);
        //dataObjectAO.addAVUMetadata(targetFile.getAbsolutePath(), avuData2);
        //dataObjectAO.addAVUMetadata(targetFile.getAbsolutePath(), avuData3);
        //dataObjectAO.addAVUMetadata(targetFile.getAbsolutePath(), avuData4);
        //dataObjectAO.addAVUMetadata(targetFile.getAbsolutePath(), avuData5);

    }

    public void import_to_directory_recovery(IRODSAccount account, String sourceFilePath, String irodsPath,
            String targetFileName, String userName, String datasetURI, String creationDate, String datasetName,
            String datasetType, String recoveryFolderName)
            throws JargonException {
        logger.debug("Importing to IRODS-Server[\n\tiRODS account: " + account.toString()
                + "\tSourceFilePath: " + sourceFilePath
                + "\tiRODSPath: " + irodsPath
                + "\tTargetFileName: " + targetFileName
                + "\tUsername: " + userName
                + "\tDatasetURI: " + datasetURI
                + "\tDatasetName: " + datasetName
                + "\tCreationDate: " + creationDate);

        IRODSFileSystem irodsFileSystem = IRODSFileSystem.instance();

        DataTransferOperations dataTransferOperations = irodsFileSystem.getIRODSAccessObjectFactory().getDataTransferOperations(account);

        File sourceFile = new File(sourceFilePath);

        IRODSFileFactory irodsFileFactory = irodsFileSystem.getIRODSFileFactory(account);

        //newFileName = "/tempZone/home/rods/"+newFileName;
        String recoveryFolderPath = irodsPath + "/" + recoveryFolderName + "/";

        IRODSFile targetFileFolder = irodsFileFactory.instanceIRODSFile(recoveryFolderPath);

        targetFileFolder.mkdir();

        String targetFilePath = recoveryFolderPath;// + "/" + datasetName + "/";

        IRODSFile targetFile = irodsFileFactory.instanceIRODSFile(targetFilePath);

        targetFile.mkdir();

        // targetFilePath = irodsPath + targetFileName; 
        targetFilePath = targetFilePath + targetFileName;

        targetFile = irodsFileFactory.instanceIRODSFile(targetFilePath);

        DataTransferOperations dataTransferOperationsAO = irodsFileSystem.getIRODSAccessObjectFactory().getDataTransferOperations(account);

        dataTransferOperationsAO.putOperation(sourceFile, targetFile, null, null);

        DataObjectAO dataObjectAO = irodsFileSystem.getIRODSAccessObjectFactory().getDataObjectAO(account);

        dataObjectAO.findByAbsolutePath(targetFile.getAbsolutePath());

        AvuData avuData = new AvuData();
        avuData.setAttribute(USERNAME_LABEL);
        avuData.setValue(userName);

        AvuData avuData2 = new AvuData();
        avuData2.setAttribute(DATASET_URI_LABEL);
        avuData2.setValue(datasetURI);

        AvuData avuData3 = new AvuData();
        avuData3.setAttribute(CREATION_DATE_LABEL);
        avuData3.setValue(creationDate);

        AvuData avuData4 = new AvuData();
        avuData4.setAttribute(DATASET_TITLE_LABEL);
        avuData4.setValue(datasetName);

        AvuData avuData5 = new AvuData();
        avuData5.setAttribute(DATASET_TYPE_LABEL);
        avuData5.setValue(datasetType);

        dataObjectAO.addAVUMetadata(targetFile.getAbsolutePath(), avuData);
        dataObjectAO.addAVUMetadata(targetFile.getAbsolutePath(), avuData2);
        dataObjectAO.addAVUMetadata(targetFile.getAbsolutePath(), avuData3);
        dataObjectAO.addAVUMetadata(targetFile.getAbsolutePath(), avuData4);
        dataObjectAO.addAVUMetadata(targetFile.getAbsolutePath(), avuData5);
    }

//    public String get_dataset(IRODSAccount account, String targetFilePath, String datasetURI) throws JargonException, JargonQueryException {
//        logger.debug("Retrieving dataset from IRODS-Server[\n\tiRODS account: " + account.toString()
//                + "\tTargetFilePath: " + targetFilePath
//                + "\tDatasetURI: " + datasetURI);
//
//        IRODSFileSystem irodsFileSystem = IRODSFileSystem.instance();
//
//        DataTransferOperations dataTransferOperations = irodsFileSystem.getIRODSAccessObjectFactory().getDataTransferOperations(account);
//
//        File targetFile = new File(targetFilePath);
//
//        IRODSFileFactory irodsFileFactory = irodsFileSystem.getIRODSFileFactory(account);
//
//        // newFileName = "/tempZone/home/rods/"+newFileName;
//        // String sourceFilePath = irodsPath + sourceFileName; 
//        // IRODSFile sourceFile = null;
//        // sourceFile = irodsFileFactory.instanceIRODSFile(sourceFilePath);     
//        DataTransferOperations dataTransferOperationsAO = irodsFileSystem
//                .getIRODSAccessObjectFactory().getDataTransferOperations(account);
//
//        DataObjectAO dataObjectAO = irodsFileSystem
//                .getIRODSAccessObjectFactory().getDataObjectAO(account);
//
//        List<AVUQueryElement> avuQueryElements = new ArrayList<>();
//
//        avuQueryElements.add(AVUQueryElement.instanceForValueQuery(
//                AVUQueryElement.AVUQueryPart.VALUE, AVUQueryOperatorEnum.EQUAL, datasetURI));
//
//        List<MetaDataAndDomainData> metadata = dataObjectAO
//                .findMetadataValuesByMetadataQuery(avuQueryElements);
//
//        List<DataObject> dobject = dataObjectAO.findDomainByMetadataQuery(avuQueryElements);
//
//        String fileToGetPath = dobject.get(0).getAbsolutePath();
//
//        //for (MetaDataAndDomainData actual : metadata) {
//        logger.info("File to download" + fileToGetPath);
//        //}
//
//        dataTransferOperations.getOperation(fileToGetPath, targetFilePath, dobject.get(0).getResourceName(), null, null);
//
//        return dobject.get(0).getDataName();
//
//    }
    
    
     public String get_dataset(IRODSAccount account, String targetFilePath, String datasetURI, String datasetType) throws JargonException, JargonQueryException, ParseException {
        logger.debug("Retrieving dataset from IRODS-Server[\n\tiRODS account: " + account.toString()
                + "\tTargetFilePath: " + targetFilePath
                + "\tDatasetURI: " + datasetURI);

        IRODSFileSystem irodsFileSystem = IRODSFileSystem.instance();

        DataTransferOperations dataTransferOperations = irodsFileSystem.getIRODSAccessObjectFactory().getDataTransferOperations(account);

        IRODSFileFactory irodsFileFactory = irodsFileSystem.getIRODSFileFactory(account);

        // newFileName = "/tempZone/home/rods/"+newFileName;
        // String sourceFilePath = irodsPath + sourceFileName; 
        // IRODSFile sourceFile = null;
        // sourceFile = irodsFileFactory.instanceIRODSFile(sourceFilePath);     
//       DataTransferOperations dataTransferOperationsAO = irodsFileSystem
//				.getIRODSAccessObjectFactory().getDataTransferOperations(account); 
        DataObjectAO dataObjectAO = irodsFileSystem
                .getIRODSAccessObjectFactory().getDataObjectAO(account);

        List<AVUQueryElement> avuQueryElements = new ArrayList<>();

        avuQueryElements.add(AVUQueryElement.instanceForValueQuery(
                AVUQueryElement.AVUQueryPart.ATTRIBUTE, AVUQueryOperatorEnum.EQUAL, "datasetURI"));

        avuQueryElements.add(AVUQueryElement.instanceForValueQuery(
                AVUQueryElement.AVUQueryPart.VALUE, AVUQueryOperatorEnum.EQUAL, datasetURI));

        avuQueryElements.add(AVUQueryElement.instanceForValueQuery(
                AVUQueryElement.AVUQueryPart.ATTRIBUTE, AVUQueryOperatorEnum.EQUAL, "datasetType"));

        avuQueryElements.add(AVUQueryElement.instanceForValueQuery(
                AVUQueryElement.AVUQueryPart.VALUE, AVUQueryOperatorEnum.EQUAL, datasetType));

        List<DataObject> dobject = dataObjectAO.findDomainByMetadataQuery(avuQueryElements);


        String fileToGetPath = dobject.get(0).getAbsolutePath();

        //for (MetaDataAndDomainData actual : metadata) {
        logger.info("File to download" + fileToGetPath);
        //}
        File targetFile = new File(targetFilePath + dobject.get(0).getResourceName());

        dataTransferOperations.getOperation(fileToGetPath, targetFilePath, dobject.get(0).getResourceName(), null, null);

        return dobject.get(0).getDataName();

    }
    
    

    public String get_dataset_by_type(IRODSAccount account, String targetFilePath, String datasetURI, String datasetType) throws JargonException, JargonQueryException, ParseException {
        logger.debug("Retrieving dataset from IRODS-Server[\n\tiRODS account: " + account.toString()
                + "\tTargetFilePath: " + targetFilePath
                + "\tDatasetURI: " + datasetURI);

        IRODSFileSystem irodsFileSystem = IRODSFileSystem.instance();

        DataTransferOperations dataTransferOperations = irodsFileSystem.getIRODSAccessObjectFactory().getDataTransferOperations(account);

        IRODSFileFactory irodsFileFactory = irodsFileSystem.getIRODSFileFactory(account);

        // newFileName = "/tempZone/home/rods/"+newFileName;
        // String sourceFilePath = irodsPath + sourceFileName; 
        // IRODSFile sourceFile = null;
        // sourceFile = irodsFileFactory.instanceIRODSFile(sourceFilePath);     
//       DataTransferOperations dataTransferOperationsAO = irodsFileSystem
//				.getIRODSAccessObjectFactory().getDataTransferOperations(account); 
        DataObjectAO dataObjectAO = irodsFileSystem
                .getIRODSAccessObjectFactory().getDataObjectAO(account);

        List<AVUQueryElement> avuQueryElements = new ArrayList<>();

        avuQueryElements.add(AVUQueryElement.instanceForValueQuery(
                AVUQueryElement.AVUQueryPart.ATTRIBUTE, AVUQueryOperatorEnum.EQUAL, "datasetURI"));

        avuQueryElements.add(AVUQueryElement.instanceForValueQuery(
                AVUQueryElement.AVUQueryPart.VALUE, AVUQueryOperatorEnum.EQUAL, datasetURI));

        avuQueryElements.add(AVUQueryElement.instanceForValueQuery(
                AVUQueryElement.AVUQueryPart.ATTRIBUTE, AVUQueryOperatorEnum.EQUAL, "datasetType"));

        avuQueryElements.add(AVUQueryElement.instanceForValueQuery(
                AVUQueryElement.AVUQueryPart.VALUE, AVUQueryOperatorEnum.EQUAL, datasetType));

//       avuQueryElements.add(AVUQueryElement.instanceForValueQuery(
//				AVUQueryElement.AVUQueryPart.ATTRIBUTE, AVUQueryOperatorEnum.EQUAL, "creationDate"));
//        
//       avuQueryElements.add(AVUQueryElement.instanceForValueQuery(
//				AVUQueryElement.AVUQueryPart.VALUE, AVUQueryOperatorEnum.EQUAL, "Occurrence Metadata"));
        List<DataObject> dobject = dataObjectAO.findDomainByMetadataQuery(avuQueryElements);

        ArrayList<String> results = new ArrayList();

        Date date = new Date(1200);

        DateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzzz yyyy", Locale.getDefault());

        for (DataObject actual : dobject) {

            for (int i = 0; i < 5; i++) {
                if (dataObjectAO.findMetadataValuesForDataObject(actual.getAbsolutePath()).get(i).getAvuAttribute().toString().equals("creationDate")) {

                    results.add(dataObjectAO.findMetadataValuesForDataObject(actual.getAbsolutePath()).get(i).getAvuValue());

                    String creationDate = dataObjectAO.findMetadataValuesForDataObject(actual.getAbsolutePath()).get(i).getAvuValue().toString();

                    Date newDate = new Date();

                    newDate = format.parse(creationDate);

                    if (newDate.after(date)) {
                        date = newDate;
                    }
                }
            }
        }

        avuQueryElements.add(AVUQueryElement.instanceForValueQuery(
                AVUQueryElement.AVUQueryPart.ATTRIBUTE, AVUQueryOperatorEnum.EQUAL, "creationDate"));

        avuQueryElements.add(AVUQueryElement.instanceForValueQuery(
                AVUQueryElement.AVUQueryPart.VALUE, AVUQueryOperatorEnum.EQUAL, date.toString()));

        List<DataObject> dobject2 = dataObjectAO.findDomainByMetadataQuery(avuQueryElements);

        String fileToGetPath = dobject2.get(0).getAbsolutePath();

        //for (MetaDataAndDomainData actual : metadata) {
        logger.info("File to download" + fileToGetPath);
        //}
        File targetFile = new File(targetFilePath + dobject.get(0).getResourceName());

        dataTransferOperations.getOperation(fileToGetPath, targetFilePath, dobject.get(0).getResourceName(), null, null);

        return dobject2.get(0).getDataName();

    }

    public ArrayList<String> search_datasets_by_username(IRODSAccount account, String username) throws JargonException, JargonQueryException {
        logger.debug("Searching datasets by username from IRODS-Server[\n\tiRODS account: " + account.toString()
                + "\tUsername: " + username);
        IRODSFileSystem irodsFileSystem = IRODSFileSystem.instance();
        DataTransferOperations dataTransferOperations = irodsFileSystem.getIRODSAccessObjectFactory().getDataTransferOperations(account);

        //File targetFile = new File(targetFilePath); 
        IRODSFileFactory irodsFileFactory = irodsFileSystem.getIRODSFileFactory(account);

        DataObjectAO dataObjectAO = irodsFileSystem.getIRODSAccessObjectFactory().getDataObjectAO(account);

        List<AVUQueryElement> avuQueryElements = new ArrayList<>();

        avuQueryElements.add(AVUQueryElement.instanceForValueQuery(AVUQueryElement.AVUQueryPart.VALUE, AVUQueryOperatorEnum.EQUAL, username));

        List<DataObject> dobject = dataObjectAO.findDomainByMetadataQuery(avuQueryElements);

        ArrayList<String> results = new ArrayList();

        for (DataObject actual : dobject) {
            results.add(actual.getAbsolutePath());
        }
        for (String m : results) {
            logger.info("Datasets Found:" + m);
        }
        return results;
    }

    public ArrayList<String> search_directory_datasets_by_type(IRODSAccount account, String type) throws JargonException, JargonQueryException {
        logger.debug("Searching dataset URIs by username from IRODS-Server[\n\tiRODS account: " + account.toString()
                + "\tUsername: " + type);

        IRODSFileSystem irodsFileSystem = IRODSFileSystem.instance();

        DataTransferOperations dataTransferOperations = irodsFileSystem.getIRODSAccessObjectFactory().getDataTransferOperations(account);

        //File targetFile = new File(targetFilePath); 
        IRODSFileFactory irodsFileFactory = irodsFileSystem.getIRODSFileFactory(account);

        DataObjectAO dataObjectAO = irodsFileSystem
                .getIRODSAccessObjectFactory().getDataObjectAO(account);

        List<AVUQueryElement> avuQueryElements = new ArrayList<>();

        avuQueryElements.add(AVUQueryElement.instanceForValueQuery(AVUQueryElement.AVUQueryPart.VALUE, AVUQueryOperatorEnum.EQUAL, type));

        // List<MetaDataAndDomainData> metadata = dataObjectAO
        //				.findMetadataValuesByMetadataQuery(avuQueryElements);
        List<DataObject> dobject = dataObjectAO.findDomainByMetadataQuery(avuQueryElements);

        ArrayList<String> results = new ArrayList();

        for (DataObject actual : dobject) {
            for (int i = 0; i < 5; i++) {
                if (dataObjectAO.findMetadataValuesForDataObject(actual.getAbsolutePath()).get(i).getAvuAttribute().toString().equals("datasetURI")) {
                    results.add(dataObjectAO.findMetadataValuesForDataObject(actual.getAbsolutePath()).get(i).getAvuValue());
                }
            }
        }

        for (String m : results) {
            logger.info("Datasets Found:" + m);
        }

        return results;

    }

    public ArrayList<String> search_directory_datasets_by_type(IRODSAccount account, String type, String recFolderName) throws JargonException, JargonQueryException {
        logger.debug("Searching dataset URIs by username from IRODS-Server[\n\tiRODS account: " + account.toString()
                + "\tUsername: " + type);

        IRODSFileSystem irodsFileSystem = IRODSFileSystem.instance();

        DataTransferOperations dataTransferOperations = irodsFileSystem.getIRODSAccessObjectFactory().getDataTransferOperations(account);

        //File targetFile = new File(targetFilePath); 
        IRODSFileFactory irodsFileFactory = irodsFileSystem.getIRODSFileFactory(account);

        DataObjectAO dataObjectAO = irodsFileSystem
                .getIRODSAccessObjectFactory().getDataObjectAO(account);

        List<AVUQueryElement> avuQueryElements = new ArrayList<>();

        avuQueryElements.add(AVUQueryElement.instanceForValueQuery(AVUQueryElement.AVUQueryPart.VALUE, AVUQueryOperatorEnum.EQUAL, type));

        List<DataObject> dobject = dataObjectAO.findDomainByMetadataQuery(avuQueryElements);

        ArrayList<String> results = new ArrayList();

        for (DataObject actual : dobject) {
            for (int i = 0; i < 5; i++) {
                if (dataObjectAO.findMetadataValuesForDataObject(actual.getAbsolutePath()).get(i).getAvuAttribute().toString().equals("datasetURI")) {
                    if (dataObjectAO.findMetadataValuesForDataObject(actual.getAbsolutePath()).toString().contains("/tempZone/home/rods/" + recFolderName)) {
                        results.add(dataObjectAO.findMetadataValuesForDataObject(actual.getAbsolutePath()).get(i).getAvuValue());
                    }
                }
            }
        }

        for (String m : results) {
            logger.info("Datasets Found:" + m);
        }

        //   dobject.
        //   dataTransferOperations.getOperation(fileToGetPath,targetFilePath,dobject.get(0).getResourceName(), null, null);
        return results;

    }

    public ArrayList<String> search_datasets_uris_by_username(IRODSAccount account, String username) throws JargonException, JargonQueryException {
        logger.debug("Searching dataset URIs by username from IRODS-Server[\n\tiRODS account: " + account.toString()
                + "\tUsername: " + username);

        IRODSFileSystem irodsFileSystem = IRODSFileSystem.instance();

        DataTransferOperations dataTransferOperations = irodsFileSystem.getIRODSAccessObjectFactory().getDataTransferOperations(account);

        //File targetFile = new File(targetFilePath); 
        IRODSFileFactory irodsFileFactory = irodsFileSystem.getIRODSFileFactory(account);

        DataObjectAO dataObjectAO = irodsFileSystem
                .getIRODSAccessObjectFactory().getDataObjectAO(account);

        List<AVUQueryElement> avuQueryElements = new ArrayList<>();

        avuQueryElements.add(AVUQueryElement.instanceForValueQuery(AVUQueryElement.AVUQueryPart.VALUE, AVUQueryOperatorEnum.EQUAL, username));

        // List<MetaDataAndDomainData> metadata = dataObjectAO
        //				.findMetadataValuesByMetadataQuery(avuQueryElements);
        List<DataObject> dobject = dataObjectAO.findDomainByMetadataQuery(avuQueryElements);

        ArrayList<String> results = new ArrayList();

        for (DataObject actual : dobject) {
            for (int i = 0; i < 5; i++) {
                if (dataObjectAO.findMetadataValuesForDataObject(actual.getAbsolutePath()).get(i).getAvuAttribute().toString().equals("datasetURI")) {
                    results.add(dataObjectAO.findMetadataValuesForDataObject(actual.getAbsolutePath()).get(i).getAvuValue());
                }
            }

        }

        for (String m : results) {
            logger.info("Datasets Found:" + m);
        }

        //   dobject.
        //   dataTransferOperations.getOperation(fileToGetPath,targetFilePath,dobject.get(0).getResourceName(), null, null);
        return results;

    }

    public void delete_dataset(IRODSAccount account, String datasetURI) throws JargonException, JargonQueryException {
        logger.debug("Deleting dataset from IRODS-Server[\n\tiRODS account: " + account.toString()
                + "\tDatasetURI: " + datasetURI);

        IRODSFileSystem irodsFileSystem = IRODSFileSystem.instance();
        DataTransferOperations dataTransferOperations = irodsFileSystem.getIRODSAccessObjectFactory().getDataTransferOperations(account);

        // File targetFile = new File(targetFilePath); 
        IRODSFileFactory irodsFileFactory = irodsFileSystem.getIRODSFileFactory(account);

        // newFileName = "/tempZone/home/rods/"+newFileName;
        // String sourceFilePath = irodsPath + sourceFileName; 
        // IRODSFile sourceFile = null; 
        // sourceFile = irodsFileFactory.instanceIRODSFile(sourceFilePath);           
        // DataTransferOperations dataTransferOperationsAO = irodsFileSystem
        //				.getIRODSAccessObjectFactory().getDataTransferOperations(account); 
        DataObjectAO dataObjectAO = irodsFileSystem.getIRODSAccessObjectFactory().getDataObjectAO(account);

        List<AVUQueryElement> avuQueryElements = new ArrayList<>();

        avuQueryElements.add(AVUQueryElement.instanceForValueQuery(AVUQueryElement.AVUQueryPart.VALUE, AVUQueryOperatorEnum.EQUAL, datasetURI));

        List<MetaDataAndDomainData> metadata = dataObjectAO.findMetadataValuesByMetadataQuery(avuQueryElements);

        List<DataObject> dobject = dataObjectAO.findDomainByMetadataQuery(avuQueryElements);

        String fileToDeletePath = dobject.get(0).getAbsolutePath();

        irodsFileFactory.instanceIRODSFile(fileToDeletePath).delete();

        // for (MetaDataAndDomainData actual : metadata) {
        // }
        // dobject.     
        //   dataTransferOperations.getOperation(fileToGetPath,targetFilePath,dobject.get(0).getResourceName(), null, null);
    }

}
