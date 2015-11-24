package eu.lifewatch.dataservices.middleware.metadatarepository.rest;

import eu.lifewatch.core.impl.VirtuosoRepositoryManager;
import eu.lifewatch.core.model.MicroCTScanningStruct;
import eu.lifewatch.exception.QueryExecutionException;
import eu.lifewatch.service.impl.MetadataRepositoryService;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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
    private static final String RETURN_TYPE_LABEL="returnType";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        LOGGER.info("Request for searchinh MicroCTScanning info");
        String speciesNameReceived=request.getParameter(SPECIES_LABEL);
        if(speciesNameReceived==null){   /* This means that the user didn't provide the proper label in the request */
            LOGGER.info("the species attribute was not given by the user. Find and return all MicroCT Scanning data");
            //TODO: Return everything or return error (??)
        }else{
            LOGGER.info("species attribute value: "+speciesNameReceived);
            List<MicroCTScanningStruct> results=this.getMicroCTScanningResults(speciesNameReceived);
            this.processAndReturnResults(results, response);
        }
    }
    
    private List<MicroCTScanningStruct> getMicroCTScanningResults(String species){
        List<MicroCTScanningStruct> retList=new ArrayList<>();
        try{
            ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
            VirtuosoRepositoryManager repoManager=context.getBean(VirtuosoRepositoryManager.class);
            RepositoryData repoData=context.getBean(RepositoryData.class);
            MetadataRepositoryService api=new MetadataRepositoryService(repoManager);
            LOGGER.info("Searching for MicroCTScanning data with the following details: Species: "+species+", repositoryGraph: "+repoData.getRepositoryGraph());
            retList=api.searchMicroCTScanning("", "", species, "", "", repoData.getRepositoryGraph());
            LOGGER.info("Number of results that will be returned: "+retList.size());
        }catch(QueryExecutionException ex){
            LOGGER.error("An error occured while searching for microCT scanning metadata. Returning an empty list.\n", ex);
        }
      return retList; 
    }
    
    private void processAndReturnResults(List<MicroCTScanningStruct> results, HttpServletResponse response){
        response.setContentType("text/turtle");
        try(PrintWriter out = response.getWriter()){
            StringBuilder sb=new StringBuilder();
            for(MicroCTScanningStruct result : results){
                sb.append(result.toNtriples());
            }
            out.println(sb.toString());
            out.close();
        }catch(IOException ex){
            LOGGER.error("An error occured while producing results output",ex);
            //TODO return also an error struct
        }
    }
}
