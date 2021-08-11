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
    
    /** Uses the CSV input file containing MicroCT Specimen resources to construct its equivalent XML output
     * with the proper labels. 
     * 
     * @param input the CSV input
     * @param output the XML output
     * @throws IOException is thrown for any error that might occur while reading writing files
     * @throws ParserConfigurationException is thrown for any error that might occur while exporting data in XML
     * @throws TransformerException is thrown for any error that might occur while exporting data in XML
     * @throws UnsupportedEncodingException is thrown if the input file cannot be read using the defined encoding (i.e. UTF-8) */
    public static void xmlifyMicroCtSpecimens(File input, File output) throws UnsupportedEncodingException, IOException, ParserConfigurationException, TransformerException{
        CSVParser csvParser=CSVFormat.DEFAULT.parse(new InputStreamReader(new FileInputStream(input), "UTF-8"));
        Document document=DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        Element rootElement=document.createElement("root");
        document.appendChild(rootElement);
        for(CSVRecord record : csvParser){
            Element rowElement=document.createElement("row");
            for(int i=0;i<specimenColumnsMappings.keySet().size();i++){
                rowElement.appendChild(createElementWithText(document, specimenColumnsMappings.get(i), record.get(i)));
            }
            rootElement.appendChild(rowElement);
        }
        Utils.exportXmlToFile(document, output);
    }
    
    /** Uses the CSV input file containing MicroCT Scanning resources to construct its equivalent XML output
     * with the proper labels. 
     * 
     * @param input the CSV input
     * @param output the XML output  */
    public static void xmlifyMicroCtScannings(File input, File output){
        
    }
    
    private static Element createElementWithText(Document document, String elementName, String text){
        Element element=document.createElement(elementName);
        if(!text.isEmpty()){
            element.setTextContent(text);
        }
        return element;
    }
    
    public static void main(String[] args) throws UnsupportedEncodingException, IOException, ParserConfigurationException, TransformerException{
        xmlifyMicroCtSpecimens(new File("Specimens.csv"), new File("specimens.xml"));
    }
}