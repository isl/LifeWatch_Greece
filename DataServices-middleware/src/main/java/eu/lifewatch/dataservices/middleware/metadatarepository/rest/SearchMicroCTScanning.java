package eu.lifewatch.dataservices.middleware.metadatarepository.rest;

import com.google.gson.Gson;
import eu.lifewatch.core.impl.VirtuosoRepositoryManager;
import eu.lifewatch.core.model.MicroCTScanningStruct;
import eu.lifewatch.core.model.Pair;
import eu.lifewatch.exception.QueryExecutionException;
import eu.lifewatch.service.impl.MetadataRepositoryService;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * SearchMicroCTScanning provides the functionality of searching for information about 
 * MicrCT Scanning activities from the metadata catalogue. 
 * For more information about the usage of the SOAP services and the contents and 
 * manipulation of the returned structs please refer to the javadoc documentation of the 
 * DataServices-api.
 *
 * @author Yannis Marketakis (marketak 'at' ics 'dot' forth 'dot' gr)
 * @author Nikos Minadakis (minadakn 'at' ics 'dot' forth 'dot' gr)
 */
public class SearchMicroCTScanning extends HttpServlet {
    private static final Logger LOGGER=Logger.getLogger(SearchMicroCTScanning.class);
    private static final String SPECIES_LABEL="species";
    private static final String SPECIMEN_LABEL="specimen";
    private static final String SCANNING_ID_LABEL="scanning";
    private static final String RETURN_TYPE_LABEL="returnType";
    private static final String XML_CONTENT_TYPE="text/xml";
    private static final String CSV_CONTENT_TYPE="text/csv";
    private static final String JSON_CONTENT_TYPE="application/json";
    private static final String NTRIPLES_CONTENT_TYPE="text/turtle";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        LOGGER.info("Request for searchinh MicroCTScanning info");
        String speciesNameReceived=request.getParameter(SPECIES_LABEL);
        String specimenNameReceived=request.getParameter(SPECIMEN_LABEL);
        String scanningIdReceived=request.getParameter(SCANNING_ID_LABEL);
        String returnType=request.getParameter(RETURN_TYPE_LABEL);
        Enumeration<String> params=request.getParameterNames();
        if(!params.hasMoreElements()){
            LOGGER.info("The user didn't provide any attributes. Showing the HTML page with instructions.");
            InstructionsPage.showHtmlPageWithInstructions(response);
        }else if(speciesNameReceived==null && specimenNameReceived==null && scanningIdReceived==null){
            LOGGER.info("The user didn't provide any values for either \"species\" or \"specimen\" or \"scanning\". Showing the HTML page with instructions.");
            InstructionsPage.showHtmlPageWithInstructions(response);
        }else{   
            if(returnType==null){
                String responseType=response.getContentType();
                if(responseType==null){
                    responseType="else";    //This will force it to get the default value (and report the proper log message)
                }
                switch(responseType){
                    case CSV_CONTENT_TYPE:
                        returnType="csv";
                        break;
                    case XML_CONTENT_TYPE:
                        returnType="xml";
                        break;
                    case JSON_CONTENT_TYPE:
                        returnType="json";
                        break;
                    case NTRIPLES_CONTENT_TYPE:
                        returnType="ntriples";
                        break;
                    default:
                        LOGGER.info("the return type was not specified by the user. The default return type is JSON");
                        returnType="json";
                }
            }
            if(speciesNameReceived==null){
                speciesNameReceived="";
            }
            if(specimenNameReceived==null){
                specimenNameReceived="";
            }
            if(scanningIdReceived==null){
                scanningIdReceived="";
            }
            List<MicroCTScanningStruct> results=this.getMicroCTScanningResults(speciesNameReceived,specimenNameReceived,scanningIdReceived);
            this.processAndReturnResults(results,returnType,response);
        }
    }
    
    private List<MicroCTScanningStruct> getMicroCTScanningResults(String species,String specimen,String scanningID){
        List<MicroCTScanningStruct> retList=new ArrayList<>();
        try{
            ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
            VirtuosoRepositoryManager repoManager=context.getBean(VirtuosoRepositoryManager.class);
            RepositoryData repoData=context.getBean(RepositoryData.class);
            MetadataRepositoryService api=new MetadataRepositoryService(repoManager);
            LOGGER.info("Searching for MicroCTScanning data with the following details: Species: "+species+",Specimen: "+specimen+",ScanningID: "+scanningID+", repositoryGraph: "+repoData.getRepositoryGraph());
            retList=api.searchMicroCTScanning("", specimen, species, "", scanningID, "", 0, 0, repoData.getRepositoryGraph());
            LOGGER.info("Number of results that will be returned: "+retList.size());
        }catch(QueryExecutionException ex){
            LOGGER.error("An error occured while searching for microCT scanning metadata. Returning an empty list.\n", ex);
        }
      return retList; 
    }
    
    private void processAndReturnResults(List<MicroCTScanningStruct> results, String returnType, HttpServletResponse response){
        switch(returnType.toLowerCase()){
            case "ntriples":
                this.processAndReturnResultsAsNtriples(results, response);
                break;
            case "json":
                this.processAndReturnResultsAsJson(results, response);
                break;
            case "xml":
                this.processAndReturnResultsAsXML(results, response);
                break;
            case "csv":
                this.processAndReturnResultsAsCSV(results, response);
                break;
            default:
                this.processAndReturnResultsAsJson(results, response);
                break;
        }
    }
    
    private void processAndReturnResultsAsNtriples(List<MicroCTScanningStruct> results, HttpServletResponse response){
        LOGGER.info("returning the results in NTRIPLES format ("+NTRIPLES_CONTENT_TYPE+")");
        response.setContentType(NTRIPLES_CONTENT_TYPE);
        try(PrintWriter out = response.getWriter()){
            StringBuilder sb=new StringBuilder();
            for(MicroCTScanningStruct result : results){
                sb.append(result.toNtriples());
            }
            out.println(sb.toString());
            
            out.close();
        }catch(IOException ex){
            LOGGER.error("An error occured while producing results output",ex);
        }
    }
    
    private void processAndReturnResultsAsJson(List<MicroCTScanningStruct> results, HttpServletResponse response){
        LOGGER.info("returning the results in JSON format ("+JSON_CONTENT_TYPE+")");
        response.setContentType(JSON_CONTENT_TYPE);
        Gson gson=new Gson();
        String gsonResults=gson.toJson(results);
        try(PrintWriter out = response.getWriter()){
            out.println(gsonResults);
            out.close();
        }catch(IOException ex){
            LOGGER.error("An error occured while producing results output",ex);
        }
    }
    
    private void processAndReturnResultsAsCSV(List<MicroCTScanningStruct> results, HttpServletResponse response){
        LOGGER.info("returning the results in CSV format ("+CSV_CONTENT_TYPE+")");
        final String csvDelimiter=";";
        response.setContentType(CSV_CONTENT_TYPE);
        StringBuilder sb=new StringBuilder();
        sb.append("Dataset URI").append(csvDelimiter)
          .append("Dataset Name").append(csvDelimiter)
          .append("Description").append(csvDelimiter)
          .append("Specimen URI").append(csvDelimiter)
          .append("Specimen Name").append(csvDelimiter)
          .append("Equipment URI").append(csvDelimiter)
          .append("Equipment Name").append(csvDelimiter)
          .append("Contrast Method").append(csvDelimiter)
          .append("Method URI").append(csvDelimiter)
          .append("Method Name").append(csvDelimiter)
          .append("Scanning URI").append(csvDelimiter)
          .append("Scanning Name").append(csvDelimiter)
          .append("Scanning ID").append(csvDelimiter)
          .append("Timespan").append(csvDelimiter)
          .append("Actor URI").append(csvDelimiter)
          .append("Actor Name").append(csvDelimiter)
          .append("Device URI").append(csvDelimiter)
          .append("Device Name").append(csvDelimiter)
          .append("Device Type").append(csvDelimiter)
          .append("Voltage").append(csvDelimiter)
          .append("Filter").append(csvDelimiter)
          .append("Zoom").append(csvDelimiter)
          .append("Exposure Time").append(csvDelimiter)
          .append("Product URI").append(csvDelimiter)
          .append("Product Name");
        sb.append("\n");
        for(MicroCTScanningStruct result : results){
            StringBuilder sbRow=new StringBuilder();
            sbRow.append(result.getDatasetURI()).append(csvDelimiter)
                 .append(result.getDatasetName()).append(csvDelimiter)
                 .append(result.getDescription()).append(csvDelimiter)
                 .append(result.getSpecimenURI()).append(csvDelimiter)
                 .append(result.getSpecimenName()).append(csvDelimiter)
                 .append(result.getEquipmentURI()).append(csvDelimiter)
                 .append(result.getEquipment()).append(csvDelimiter)
                 .append(result.getContrastMethod()).append(csvDelimiter)
                 .append(result.getMethodURI()).append(csvDelimiter)
                 .append(result.getMethodName()).append(csvDelimiter)
                 .append(result.getScanningURI()).append(csvDelimiter)
                 .append(result.getScanningLabel()).append(csvDelimiter)
                 .append(result.getScanning()).append(csvDelimiter)
                 .append(result.getTimespan()).append(csvDelimiter)
                 .append(result.getActorURI()).append(csvDelimiter)
                 .append(result.getActorName()).append(csvDelimiter)
                 .append(result.getDeviceURI()).append(csvDelimiter)
                 .append(result.getDeviceName()).append(csvDelimiter)
                 .append(result.getDeviceType()).append(csvDelimiter)
                 .append(result.getVoltage()).append(csvDelimiter)
                 .append(result.getFilter()).append(csvDelimiter)
                 .append(result.getZoom()).append(csvDelimiter)
                 .append(result.getExposureTime()).append(csvDelimiter);
            for(Pair product : result.getProducts()){
                sb.append(sbRow)
                  .append(product.getKey())
                  .append(csvDelimiter)
                  .append(product.getValue())
                  .append("\n");
            }
        }
        response.setHeader("Content-Disposition", "inline; filename=results.csv");
        try(PrintWriter out = response.getWriter()){
            out.println(sb.toString());
            out.close();
        }catch(IOException ex){
            LOGGER.error("An error occured while producing results output",ex);
        }
    }
    
    private void processAndReturnResultsAsXML(List<MicroCTScanningStruct> results, HttpServletResponse response){
        LOGGER.info("returning the results in XML format ("+XML_CONTENT_TYPE+")");
        response.setContentType(XML_CONTENT_TYPE);
        DocumentBuilderFactory xmlFactory=DocumentBuilderFactory.newInstance();
        try{
            DocumentBuilder xmlBuilder=xmlFactory.newDocumentBuilder();
            Document doc=xmlBuilder.newDocument();
            Element rootElement=doc.createElement("Results");
            doc.appendChild(rootElement);
            for(MicroCTScanningStruct result : results){
                rootElement.appendChild(this.createXmlElement(doc,result));
            }
            Transformer transformer=TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source=new DOMSource(doc);
            StringWriter writer=new StringWriter();
            StreamResult streamResult=new StreamResult(writer);
            transformer.transform(source, streamResult);
            
            PrintWriter out=response.getWriter();
            out.append(writer.toString());
            out.close();
        }catch(ParserConfigurationException | TransformerException | IOException ex){
            LOGGER.error("An error occured while producing XML data",ex);
        }
    }
    
    private Element createXmlElement(Document doc, MicroCTScanningStruct struct){
        Element scanningElem=doc.createElement("microCT_scanning");
        scanningElem.appendChild(createNodeWithText(doc,"dataset_uri",struct.getDatasetURI()));
        scanningElem.appendChild(createNodeWithText(doc,"dataset_name",struct.getDatasetName()));
        scanningElem.appendChild(createNodeWithText(doc,"description",struct.getDescription()));
        scanningElem.appendChild(createNodeWithText(doc,"specimen_uri",struct.getSpecimenURI()));
        scanningElem.appendChild(createNodeWithText(doc,"specimen_name",struct.getSpecimenName()));
        scanningElem.appendChild(createNodeWithText(doc,"equipment_uri",struct.getEquipmentURI()));
        scanningElem.appendChild(createNodeWithText(doc,"equipment_name",struct.getEquipment()));
        scanningElem.appendChild(createNodeWithText(doc,"contrast_method",struct.getContrastMethod()));
        scanningElem.appendChild(createNodeWithText(doc,"method_uri",struct.getMethodURI()));
        scanningElem.appendChild(createNodeWithText(doc,"method_name",struct.getMethodName()));
        scanningElem.appendChild(createNodeWithText(doc,"scanning_uri",struct.getScanningURI()));
        scanningElem.appendChild(createNodeWithText(doc,"scanning_name",struct.getScanning()));
        scanningElem.appendChild(createNodeWithText(doc,"timespan",struct.getTimespan()));
        scanningElem.appendChild(createNodeWithText(doc,"actor_uri",struct.getActorURI()));
        scanningElem.appendChild(createNodeWithText(doc,"actor_name",struct.getActorName()));
        scanningElem.appendChild(createNodeWithText(doc,"device_uri",struct.getDeviceURI()));
        scanningElem.appendChild(createNodeWithText(doc,"device_name",struct.getDeviceName()));
        scanningElem.appendChild(createNodeWithText(doc,"device_type",struct.getDeviceType()));
        scanningElem.appendChild(createNodeWithText(doc,"voltage",struct.getVoltage()));
        scanningElem.appendChild(createNodeWithText(doc,"filter",struct.getFilter()));
        scanningElem.appendChild(createNodeWithText(doc,"zoom",struct.getZoom()));
        scanningElem.appendChild(createNodeWithText(doc,"exposure_time",struct.getExposureTime()));
        for(Pair product : struct.getProducts()){
            Element productElem=doc.createElement("product");
            productElem.appendChild(createNodeWithText(doc, "product_uri", product.getKey()));
            productElem.appendChild(createNodeWithText(doc, "product_name", product.getValue()));
            scanningElem.appendChild(productElem);
        }
        return scanningElem;
    }
    
    private Node createNodeWithText(Document doc, String tag, String text){
        Element nodeElem=doc.createElement(tag);
        nodeElem.setTextContent(text);
        return nodeElem;
    }
}