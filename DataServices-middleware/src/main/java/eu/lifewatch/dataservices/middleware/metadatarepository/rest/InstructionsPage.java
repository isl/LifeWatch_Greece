package eu.lifewatch.dataservices.middleware.metadatarepository.rest;

import java.io.IOException;
import java.io.Writer;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 * @author Yannis Marketakis (marketak 'at' ics 'dot' forth 'dot' gr)
 * @author Nikos Minadakis (minadakn 'at' ics 'dot' forth 'dot' gr)
 */
public class InstructionsPage {
    private static final Logger LOGGER=Logger.getLogger(SearchMicroCTScanning.class);
    
    protected static void showHtmlPageWithInstructions(HttpServletResponse response){
        String htmlPage="<html> "
                       +"<title> MicroCT Scanning Service Instructions</title> "
                       +"<body> "
                       +"<h1> MicroCT Scanning Search Service </h1> "
                       +"<h2> Input </h2> "
                       +"The service requires the following input parameters to be specified: "
                       +"<ul> "
                       +"<li><b>species:</b> The scientific name (or part of it) of the species</li> "
                       +"<li><b>specimen:</b> The name (or part of it) of the specimen</li> "
                       +"<li><b>returnType:</b> The format of the output. The supported formats are: json, xml, csv, ntriples)</li> "
                       +"</ul> "
                       +"<h2> Examples: </h2> "
                       +"<ul> "
                       +"<li> <h3> Give me all the MicroCT scanning as regards the species, with scientific name that contains the word \"albacares\" in...</h3></li> "
                       +"<h4> JSON format: </h4> "
                       +"<a href=/DataServices-middleware/searchMicroCTScanning?species=albacares&returnType=json> /searchMicroCTScanning?species=albacares&returnType=json</a> "
                       +"<h4> XML format: </h4> "
                       +"<a href=/DataServices-middleware/searchMicroCTScanning?species=albacares&returnType=xml> /searchMicroCTScanning?species=albacares&returnType=xml</a> "
                       +"<h4> CSV format: </h4> "
                       +"<a href=/DataServices-middleware/searchMicroCTScanning?species=albacares&returnType=csv> /searchMicroCTScanning?species=albacares&returnType=csv</a> "
                       +"<h4> NTRIPLES format: </h4> "
                       +"<a href=/DataServices-middleware/searchMicroCTScanning?species=albacares&returnType=ntriples> /searchMicroCTScanning?species=albacares&returnType=ntriples</a> "
                       +"<li><h3> Give me all the MicroCT scanning as regards the specimen whose value contain value \"id1234\" in...</h3></li> "
                       +"<h4> JSON format: </h4> "
                       +"<a href=/DataServices-middleware/searchMicroCTScanning?specimen=id1234&returnType=json> /searchMicroCTScanning?specimen=id1234&returnType=json</a> "
                       +"<li><h3> Give me all the MicroCT scanning as regards the specimen whose value contain value \"id1234\" and the corresponding species has a scientific name that contains the value \"albacares\" in...</h3></li> "
                       +"<h4> JSON format: </h4> "
                       +"<a href=/DataServices-middleware/searchMicroCTScanning?species=albacares&specimen=id1234&returnType=json> /searchMicroCTScanning?species=albacares&specimen=id1234&returnType=json</a> "
                       +"</ul> "
                       +"</body> "
                       +"</html>";
        try{
            Writer out=response.getWriter();
            out.append(htmlPage);
            out.close();
        }catch(IOException ex){
            LOGGER.error("An error occured while exporting HTML page with instruction for using the REST service");
        }      
    }
}