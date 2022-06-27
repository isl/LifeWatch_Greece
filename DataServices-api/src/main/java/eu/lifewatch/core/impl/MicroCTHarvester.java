package eu.lifewatch.core.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/** This class is responsible for harvesting CSV resources with MicroCT information (e.g. MicroCT specimens, MicroCT scannings, etc.) 
 * and generate the corresponding XML output (with the proper labels) that can be 
 * used as input for constructing the appropriate RDF resources (using the corresponding X3ML mappings).
 *
 * @author Yannis Marketakis (marketak 'at' ics 'dot' forth 'dot' gr)
 */
public class MicroCTHarvester {
    
    private static Map<Integer,String> specimenColumnsMappings;
    static{
        specimenColumnsMappings=new HashMap<>();
        specimenColumnsMappings.put(0, "SpecimenID");
        specimenColumnsMappings.put(1, "SpecimenLabel");
        specimenColumnsMappings.put(2, "CollectionCode");
        specimenColumnsMappings.put(3, "SpecimenProvider");
        specimenColumnsMappings.put(4, "ProviderInstitute");
        specimenColumnsMappings.put(5, "SpecimenDescription");
        specimenColumnsMappings.put(6, "Material");
        specimenColumnsMappings.put(7, "ScientificName");
        specimenColumnsMappings.put(8, "Size_mm");
        specimenColumnsMappings.put(9, "FixationType");
        specimenColumnsMappings.put(10, "FixationNotes");
        specimenColumnsMappings.put(11, "PreservationMedium");
        specimenColumnsMappings.put(12, "SpecimenNotes");
        specimenColumnsMappings.put(13, "StoragePlace");
        specimenColumnsMappings.put(14, "TaxonomicGroup");
    }
    
    private static Map<Integer,String> scanningColumnsMappings;
    static{
        scanningColumnsMappings=new HashMap<>();
        scanningColumnsMappings.put(0, "SpecimenID");
        scanningColumnsMappings.put(1, "ScanID");
        scanningColumnsMappings.put(2, "ContrastEnhancementMethod");
        scanningColumnsMappings.put(3, "Protocol");
        scanningColumnsMappings.put(4, "BeginOfPreparationDate");
        scanningColumnsMappings.put(5, "BeginOfPreparationTime");
        scanningColumnsMappings.put(6, "EndOfPreparationDate");
        scanningColumnsMappings.put(7, "EndOfPreparationTime");
        scanningColumnsMappings.put(8, "PreparationNotes");
        scanningColumnsMappings.put(9, "ScopeOfScan");
        scanningColumnsMappings.put(10, "SampleHolder");
        scanningColumnsMappings.put(11, "ScanningMedium");
        scanningColumnsMappings.put(12, "ScannedPart");
        scanningColumnsMappings.put(13, "ScannedBy");
        scanningColumnsMappings.put(14, "ScanDate");
        scanningColumnsMappings.put(15, "ScanningDuration");
        scanningColumnsMappings.put(16, "Instrument");
        scanningColumnsMappings.put(17, "Voltage_kV");
        scanningColumnsMappings.put(18, "Current_uA");
        scanningColumnsMappings.put(19, "Filter");
        scanningColumnsMappings.put(20, "Zoom_um");
        scanningColumnsMappings.put(21, "CameraResolution");
        scanningColumnsMappings.put(22, "Averaging");
        scanningColumnsMappings.put(23, "RandomMovement");
        scanningColumnsMappings.put(24, "Scan360or180");
        scanningColumnsMappings.put(25, "ExposureTime_ms");
        scanningColumnsMappings.put(26, "OversizeSettings");
        scanningColumnsMappings.put(27, "ScanNotes");
        scanningColumnsMappings.put(28, "FileLocation");
        scanningColumnsMappings.put(29, "ScanFileStatus");
    }
    
    public enum MicroCTResourceType {
        MicroCT_Specimens,
        MicroCT_Scanning
    }
    
    /** Uses the CSV input file containing MicroCT Specimen resources to construct its equivalent XML output
     * with the proper labels. 
     * 
     * @param input the CSV input
     * @param output the XML output
     * @param resourceType the type of the MicroCT dataset (i.e. specimens, scanning, etc.)
     * @throws IOException is thrown for any error that might occur while reading writing files
     * @throws ParserConfigurationException is thrown for any error that might occur while exporting data in XML
     * @throws TransformerException is thrown for any error that might occur while exporting data in XML
     * @throws UnsupportedEncodingException is thrown if the input file cannot be read using the defined encoding (i.e. UTF-8) */
    public static void xmlifyMicroCtResources(File input, File output, MicroCTResourceType resourceType) throws UnsupportedEncodingException, IOException, ParserConfigurationException, TransformerException{
        CSVParser csvParser=CSVFormat.DEFAULT.parse(new InputStreamReader(new FileInputStream(input), "UTF-8"));
        Document document=DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        Element rootElement=document.createElement("root");
        document.appendChild(rootElement);
        Map<Integer,String> mappingColumnsMap=new HashMap<>();
        switch(resourceType){
            case MicroCT_Specimens:
                mappingColumnsMap=specimenColumnsMappings;
                break;
            case MicroCT_Scanning:
                mappingColumnsMap=scanningColumnsMappings;
                break;
        }
                
        for(CSVRecord record : csvParser){
            Element rowElement=document.createElement("row");
            for(int i=1;i<mappingColumnsMap.keySet().size();i++){
                rowElement.appendChild(createElementWithText(document, mappingColumnsMap.get(i), record.get(i)));
            }
            rootElement.appendChild(rowElement);
        }
        Utils.exportXmlToFile(document, output);
    }
    
    private static Element createElementWithText(Document document, String elementName, String text){
        Element element=document.createElement(elementName);
        if(!text.isEmpty() && !text.equalsIgnoreCase("NULL")){
            element.setTextContent(text);
        }
        return element;
    }
    
    public static void main(String[] args) throws UnsupportedEncodingException, IOException, ParserConfigurationException, TransformerException{
        xmlifyMicroCtResources(new File("Y:/Projects/Lifewatch-eric/ENVRI-FAIR/Data/MicroCT/1. Originally Received/Specimen _metadata.csv"), new File("specimens.xml"),MicroCTResourceType.MicroCT_Specimens);
        xmlifyMicroCtResources(new File("Y:/Projects/Lifewatch-eric/ENVRI-FAIR/Data/MicroCT/1. Originally Received/Scanning_metadata.csv"), new File("scanning.xml"),MicroCTResourceType.MicroCT_Scanning);
    }
}