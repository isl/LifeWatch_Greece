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
                       +"<li><b>returnType:</b> The format of the output. The supported formats are: json, xml, csv, ntriples)</li> "
                       +"</ul> "
                       +"<h2> Examples </h2> "
                       +"<h3> Give me all the MicroCT scanning as regards the species, with scientific name that contains the word \"spec\" in...</h3> "
                       +"<h3> JSON format: </h3> "
                       +"<a href=/DataServices-middleware/searchMicroCTScanning?spec=species&returnType=json> /searchMicroCTScanning?spec=species&returnType=json</a> "
                       +"<h3> XML format: </h3> "
                       +"<a href=/DataServices-middleware/searchMicroCTScanning?spec=species&returnType=json> /searchMicroCTScanning?spec=species&returnType=xml</a> "
                       +"<h3> CSV format: </h3> "
                       +"<a href=/DataServices-middleware/searchMicroCTScanning?spec=species&returnType=json> /searchMicroCTScanning?spec=species&returnType=csv</a> "
                       +"<h3> NTRIPLES format: </h3> "
                       +"<a href=/DataServices-middleware/searchMicroCTScanning?spec=species&returnType=json> /searchMicroCTScanning?spec=species&returnType=ntriples</a> "
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