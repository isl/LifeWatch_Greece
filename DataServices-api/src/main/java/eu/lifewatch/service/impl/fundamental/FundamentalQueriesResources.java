package eu.lifewatch.service.impl.fundamental;

import eu.lifewatch.common.Resources;
import eu.lifewatch.exception.QueryExecutionException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import org.apache.log4j.Logger;

/**
 * @author Nikos Minadakis (minadakn 'at' ics 'dot' forth 'dot' gr)
 * @author Yannis Marketakis (marketak 'at' ics 'dot' forth 'dot' gr)
 */
public class FundamentalQueriesResources {
    private static final Logger logger=Logger.getLogger(FundamentalQueriesResources.class);
    
    public static final String THING_FROM_ACTOR=Resources.fundamentalQueriesFolder+"/"+"Thing_From_Actor.txt";
    public static final String THING_FROM_EVENT=Resources.fundamentalQueriesFolder+"/"+"Thing_From_Event.txt";
    public static final String THING_FROM_THING=Resources.fundamentalQueriesFolder+"/"+"Thing_From_Thing.txt";
    public static final String THING_FROM_PLACE=Resources.fundamentalQueriesFolder+"/"+"Thing_From_Place.txt";
    public static final String THING_FROM_TIME=Resources.fundamentalQueriesFolder+"/"+"Thing_From_Time.txt";
    public static final String THING_REFERS_TO_EVENT=Resources.fundamentalQueriesFolder+"/"+"Thing_Refers_To_Event.txt";
    public static final String THING_REFERS_TO_THING=Resources.fundamentalQueriesFolder+"/"+"Thing_Refers_To_Thing.txt";
    public static final String THING_HAS_DIMENSION=Resources.fundamentalQueriesFolder+"/"+"Thing_Has_Dimension.txt";
    public static final String THING_HAS_MET_ACTOR=Resources.fundamentalQueriesFolder+"/"+"Thing_Has_Met_Actor.txt";
    public static final String THING_HAS_MET_PLACE=Resources.fundamentalQueriesFolder+"/"+"Thing_Has_Met_Place.txt";
    public static final String THING_HAS_MET_THING=Resources.fundamentalQueriesFolder+"/"+"Thing_Has_Met_Thing.txt";
    public static final String THING_HAS_MET_TIME=Resources.fundamentalQueriesFolder+"/"+"Thing_Has_Met_Time.txt";
    public static final String THING_BY_ACTOR=Resources.fundamentalQueriesFolder+"/"+"Thing_By_Actor.txt";
    public static final String THING_BY_EVENT=Resources.fundamentalQueriesFolder+"/"+"Thing_By_Event.txt";
    public static final String ACTOR_HAS_MET_PLACE=Resources.fundamentalQueriesFolder+"/"+"Actor_Has_Met_Place.txt";
    public static final String ACTOR_HAS_MET_THING=Resources.fundamentalQueriesFolder+"/"+"Actor_Has_Met_Thing.txt";
    public static final String ACTOR_REFERS_TO_EVENT=Resources.fundamentalQueriesFolder+"/"+"Actor_Refers_To_Event.txt";
    public static final String ACTOR_REFERS_TO_THING=Resources.fundamentalQueriesFolder+"/"+"Actor_Refers_To_Thing.txt";
    public static final String ACTOR_IS_OWNER_OR_CREATOR_OF_THING=Resources.fundamentalQueriesFolder+"/"+"Actor_Is_Owner_Or_Creator_Of_Thing.txt";
    public static final String ACTOR_FROM_PLACE=Resources.fundamentalQueriesFolder+"/"+"Actor_From_Place.txt";
    public static final String ACTOR_FROM_TIME=Resources.fundamentalQueriesFolder+"/"+"Actor_From_Time.txt";
    public static final String ACTOR_MEASURED_DIMENSION=Resources.fundamentalQueriesFolder+"/"+"Actor_Measured_Dimension.txt";
    public static final String DIMENSION_BY_ACTOR=Resources.fundamentalQueriesFolder+"/"+"Dimension_By_Actor.txt";
    public static final String DIMENSION_BY_EVENT=Resources.fundamentalQueriesFolder+"/"+"Dimension_By_Event.txt";
    public static final String DIMENSION_OF_PLACE=Resources.fundamentalQueriesFolder+"/"+"Dimension_Of_Place.txt";
    public static final String DIMENSION_OF_THING=Resources.fundamentalQueriesFolder+"/"+"Dimension_Of_Thing.txt";
    public static final String EVENT_BY_ACTOR=Resources.fundamentalQueriesFolder+"/"+"Event_By_Actor.txt";
    public static final String EVENT_FROM_PLACE=Resources.fundamentalQueriesFolder+"/"+"Event_From_Place.txt";
    public static final String EVENT_FROM_TIME=Resources.fundamentalQueriesFolder+"/"+"Event_From_Time.txt";
    public static final String EVENT_HAS_MET_ACTOR=Resources.fundamentalQueriesFolder+"/"+"Event_Has_Met_Actor.txt";
    public static final String EVENT_HAS_MET_THING=Resources.fundamentalQueriesFolder+"/"+"Event_Has_Met_Thing.txt";
    public static final String EVENT_MEASURED_DIMENSION=Resources.fundamentalQueriesFolder+"/"+"Event_Measured_Dimension.txt";
    public static final String PLACE_HAS_DIMENSION=Resources.fundamentalQueriesFolder+"/"+"Place_Has_Dimension.txt";
    public static final String PLACE_HAS_MET_THING=Resources.fundamentalQueriesFolder+"/"+"Place_Has_Met_Thing.txt";
    public static final String PLACE_HAS_PART_PLACE=Resources.fundamentalQueriesFolder+"/"+"Place_Has_Part_Place.txt";
    public static final String PLACE_IS_PART_OF_PLACE=Resources.fundamentalQueriesFolder+"/"+"Place_Is_Part_Of_Place.txt";
    public static final String TEMPLATE_1="\\[!TEMPLATE_1!\\]";
    public static final String TEMPLATE_PREFIX="\\[!TEMPLATE_";
    public static final String TEMPLATE_SUFFIX="!\\]";

    /**
     * Returns the appropriate SPARQL query, after replacing the template with the value given in the parameter list.
     * 
     * @param fileId identifier for the file containing the SPARQL template
     * @param value the value that will be used for replacing the template values in the template SPARQL query
     * @return the SPARQL query to be submitted
     * @throws QueryExecutionException for any error that might occur */
    public static String getSparqlQuery(String fileId, String value) throws QueryExecutionException{
        try{
            StringBuilder sb=new StringBuilder();
//            BufferedReader br=new BufferedReader(new FileReader(new File(fileId)));
//            BufferedReader br=new BufferedReader(new InputStreamReader(ClassLoader.getSystemResourceAsStream(fileId)));
            BufferedReader br=new BufferedReader(new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream(fileId)));
            String line;
            while((line=br.readLine())!=null){
                if(!line.startsWith("#")){
                    sb.append(line).append("\n");
                }
            }
            String retValue=sb.toString().replaceAll(TEMPLATE_1, value);
            logger.debug(retValue);
            return retValue;
        }catch(IOException ex){
            logger.error("An error occured while retrieving the SPARQL query",ex);
            throw new QueryExecutionException("An error occured while retrieving the SPARQL excpetion for fundamental queries\n"+ex.toString(),ex);
        }
    }
    
    /**
     * Returns the appropriate SPARQL query, after replacing the template with the value given in the parameter list.
     * 
     * @param fileId identifier for the file containing the SPARQL template
     * @param values the values that will be used for replacing the template values in the template SPARQL query
     * @return the SPARQL query to be submitted
     * @throws QueryExecutionException for any error that might occur */
    public static String getSparqlQuery(String fileId, String ... values) throws QueryExecutionException{
        try{
            StringBuilder sb=new StringBuilder();
            BufferedReader br=new BufferedReader(new FileReader(new File(fileId)));
            String line;
            while((line=br.readLine())!=null){
                if(!line.startsWith("#")){
                    sb.append(line).append("\n");
                }
            }
            int cnt=1;
            for(String value : values){
                sb=new StringBuilder(sb.toString().replaceAll(TEMPLATE_PREFIX+(cnt++)+TEMPLATE_SUFFIX, value));
            }
            logger.debug(sb.toString());
            return sb.toString();
        }catch(IOException ex){
            logger.error("An error occured while retrieving the SPARQL query",ex);
            throw new QueryExecutionException("An error occured while retrieving the SPARQL excpetion for fundamental queries\n"+ex.toString(),ex);
        }
    }
    
    /**
     * Returns the appropriate SPARQL query, after replacing the templates with the given values in the parameter list.
     * 
     * @param fileId identifier for the file containing the SPARQL template
     * @param pairs a map containing key-value pairs of the form {[TEMPLATE_1],&lt;actual_uri&gt;}, {[TEMPLATE_2],value},etc.
     * @return the SPARQL query to be submitted
     * @throws QueryExecutionException for any error that might occur */
    public static String getSparqlQuery(String fileId,Map<String,String> pairs) throws QueryExecutionException{
        try{
            StringBuilder sb=new StringBuilder();
            BufferedReader br=new BufferedReader(new FileReader(new File(fileId)));
            String line;
            while((line=br.readLine())!=null){
                if(!line.startsWith("#")){
                    sb.append(line).append("\n");
                }
            }
            for(String key : pairs.keySet()){
                sb=new StringBuilder(sb.toString().replaceAll(key, pairs.get(key)));
            }
            logger.debug(sb.toString());
            return sb.toString();
        }catch(IOException ex){
            logger.error("An error occured while retrieving the SPARQL query",ex);
            throw new QueryExecutionException("An error occured while retrieving the SPARQL excpetion for fundamental queries\n"+ex.toString(),ex);
        }
    }
}
