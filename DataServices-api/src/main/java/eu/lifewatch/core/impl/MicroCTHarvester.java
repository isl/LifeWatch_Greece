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
        specimenColumnsMappings.put(0, "RowID");
        specimenColumnsMappings.put(1, "SpecimenID");
        specimenColumnsMappings.put(2, "SpecimenLabel");
        specimenColumnsMappings.put(3, "CollectionCode");
        specimenColumnsMappings.put(4, "SpecimenProvider");
        specimenColumnsMappings.put(5, "ProviderInstitute");
        specimenColumnsMappings.put(6, "SpecimenDescription");
        specimenColumnsMappings.put(7, "Material");
        specimenColumnsMappings.put(8, "ScientificName");
        specimenColumnsMappings.put(9, "Size_mm");
        specimenColumnsMappings.put(10, "FixationType");
        specimenColumnsMappings.put(11, "FixationNotes");
        specimenColumnsMappings.put(12, "PreservationMedium");
        specimenColumnsMappings.put(13, "SpecimenNotes");
        specimenColumnsMappings.put(14, "StoragePlace");
        specimenColumnsMappings.put(15, "TaxonomicGroup");
    }
    
    private static Map<Integer,String> scanningColumnsMappings;
    static{
        scanningColumnsMappings=new HashMap<>();
        scanningColumnsMappings.put(0, "RowID");
        scanningColumnsMappings.put(1, "SpecimenID");
        scanningColumnsMappings.put(2, "ScanID");
        scanningColumnsMappings.put(3, "ContrastEnhancementMethod");
        scanningColumnsMappings.put(4, "Protocol");
        scanningColumnsMappings.put(5, "BeginOfPreparationDate");
        scanningColumnsMappings.put(6, "BeginOfPreparationTime");
        scanningColumnsMappings.put(7, "EndOfPreparationDate");
        scanningColumnsMappings.put(8, "EndOfPreparationTime");
        scanningColumnsMappings.put(9, "PreparationNotes");
        scanningColumnsMappings.put(10, "ScopeOfScan");
        scanningColumnsMappings.put(11, "SampleHolder");
        scanningColumnsMappings.put(12, "ScanningMedium");
        scanningColumnsMappings.put(13, "ScannedPart");
        scanningColumnsMappings.put(14, "ScannedBy");
        scanningColumnsMappings.put(15, "ScanDate");
        scanningColumnsMappings.put(16, "ScanningDuration");
        scanningColumnsMappings.put(17, "Instrument");
        scanningColumnsMappings.put(18, "Voltage_kV");
        scanningColumnsMappings.put(19, "Current_uA");
        scanningColumnsMappings.put(20, "Filter");
        scanningColumnsMappings.put(21, "Zoom_um");
        scanningColumnsMappings.put(22, "CameraResolution");
        scanningColumnsMappings.put(23, "Averaging");
        scanningColumnsMappings.put(24, "RandomMovement");
        scanningColumnsMappings.put(25, "Scan360or180");
        scanningColumnsMappings.put(26, "ExposureTime_ms");
        scanningColumnsMappings.put(27, "OversizeSettings");
        scanningColumnsMappings.put(28, "ScanNotes");
        scanningColumnsMappings.put(29, "FileLocation");
        scanningColumnsMappings.put(30, "ScanFileStatus");
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
            for(int i=0;i<mappingColumnsMap.keySet().size();i++){
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
        xmlifyMicroCtResources(new File("Y:/Projects/Lifewatch-eric/ENVRI-FAIR/Data/MicroCT/1. Originally Received/Specimens (13).csv"), new File("specimens.xml"),MicroCTResourceType.MicroCT_Specimens);
        xmlifyMicroCtResources(new File("Y:/Projects/Lifewatch-eric/ENVRI-FAIR/Data/MicroCT/1. Originally Received/Prep_Scan (12).csv"), new File("scanning.xml"),MicroCTResourceType.MicroCT_Scanning);
    }
}