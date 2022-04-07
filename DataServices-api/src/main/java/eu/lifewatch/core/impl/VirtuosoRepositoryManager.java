package eu.lifewatch.core.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import eu.lifewatch.common.Resources;
import eu.lifewatch.core.api.RepositoryManager;
import eu.lifewatch.exception.DataExportException;
import eu.lifewatch.exception.DataImportException;
import eu.lifewatch.exception.QueryExecutionException;
import eu.lifewatch.exception.RepositoryConnectionException;
import java.io.ByteArrayOutputStream;
import org.apache.log4j.Logger;
import org.openrdf.model.URI;
import org.openrdf.model.impl.URIImpl;
import org.openrdf.query.BindingSet;
import org.openrdf.query.MalformedQueryException;
import org.openrdf.query.QueryEvaluationException;
import org.openrdf.query.QueryLanguage;
import org.openrdf.query.TupleQueryResult;
import org.openrdf.query.UpdateExecutionException;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.RDFHandlerException;
import org.openrdf.rio.RDFParseException;
import org.openrdf.rio.RDFWriter;
import org.openrdf.rio.ntriples.NTriplesWriter;
import org.openrdf.rio.rdfxml.RDFXMLWriter;
import org.openrdf.rio.trig.TriGWriter;
import org.openrdf.rio.trix.TriXWriter;
import org.openrdf.rio.turtle.TurtleWriter;
import virtuoso.sesame2.driver.VirtuosoRepository;

/**
 * @author Nikos Minadakis (mindakn 'at' ics 'dot' forth 'dot' gr)
 * @author Yannis Marketakis (marketak 'at' ics 'dot' forth 'dot' gr)
 */
public class VirtuosoRepositoryManager implements RepositoryManager{
    private Repository repo;
    private static final Logger logger=Logger.getLogger(VirtuosoRepositoryManager.class);
    private String connectionDetails;
    
    public VirtuosoRepositoryManager(String repositoryUrl, String port, String username, String password) throws RepositoryConnectionException{
        logger.debug("Connecting to Virtuoso["+repositoryUrl+","+port+","+username+","+password+"]");
        this.connectionDetails="Virtuoso Connection details ["+repositoryUrl+","+port+","+username+","+password+"]";
        if(repositoryUrl.startsWith(Resources.defaultUrlPrefix)){
                repositoryUrl=repositoryUrl.replace(Resources.defaultUrlPrefix, "");
            }
        if(repositoryUrl.endsWith("/")){
            repositoryUrl=repositoryUrl.substring(0, repositoryUrl.length()-1); 
        }
        this.repo=new VirtuosoRepository(Resources.defaultVirtuosoJdbcUrlPrefix+repositoryUrl+":"+port, username, password);
        /*Connect to check that everything is allright */
        try{
            RepositoryConnection repoConn=this.repo.getConnection();
            repoConn.close();
        }catch(RepositoryException ex){
            throw new RepositoryConnectionException("An error occured while initializing the connection to the repository",ex);
        }
    }
    
    @Override
    public void importData(File file, RDFFormat format, String graphspace) throws DataImportException{
        try{
            RepositoryConnection repoConn=this.repo.getConnection();
            URI graph=this.repo.getValueFactory().createURI(graphspace);
            InputStreamReader in=new InputStreamReader(new FileInputStream(file), "UTF8");
            repoConn.add(file, graphspace, format, graph);
            repoConn.close();
        }catch(IOException | RDFParseException | RepositoryException ex){
            throw new DataImportException("An error occured while importing data",ex);
        }
    }
    
    @Override
    public void importData(Collection<File> files, RDFFormat format, String graphspace) throws DataImportException{
        try{
            RepositoryConnection repoConn=this.repo.getConnection();
            URI graph=this.repo.getValueFactory().createURI(graphspace);
            for(File file : files){
                InputStreamReader in=new InputStreamReader(new FileInputStream(file), "UTF8");
                repoConn.add(file, graphspace, format, graph);
            }
            repoConn.close();
        }catch(IOException | RDFParseException | RepositoryException ex){
            throw new DataImportException("An error occured while importing data",ex);
        }
    }
    
    @Override
    public String exportData(RDFFormat format, String graphspace) throws DataExportException{
        try{
            RepositoryConnection repoConn=this.repo.getConnection();
            ByteArrayOutputStream output=new ByteArrayOutputStream();
            RDFWriter writer;
            if(format==RDFFormat.NTRIPLES || format==RDFFormat.N3){
                writer=new NTriplesWriter(output);
            }else if(format==RDFFormat.RDFXML){
                writer=new RDFXMLWriter(output);
            }else if(format==RDFFormat.TURTLE){
                writer=new TurtleWriter(output);
            }else if(format==RDFFormat.TRIG){
                writer=new TriGWriter(output);
            }else if(format==RDFFormat.TRIX){
                writer=new TriXWriter(output);
            }else{
                writer=new NTriplesWriter(output);
            }
            repoConn.export(writer, this.repo.getValueFactory().createURI(graphspace));           
            repoConn.close();
            return output.toString();
        }catch(RDFHandlerException | RepositoryException ex){
            throw new DataExportException("An error occured while exporting data",ex);
        }
    }
    
    @Override
    public List<BindingSet> query(String sparqlQuery) throws QueryExecutionException{
        List<BindingSet> retList= new ArrayList<>();
        try{
            RepositoryConnection repoConn=this.repo.getConnection();
            TupleQueryResult results=repoConn.prepareTupleQuery(QueryLanguage.SPARQL, sparqlQuery).evaluate();
            while(results.hasNext())
                retList.add(results.next());
            results.close();
            repoConn.close();
        }catch(MalformedQueryException | QueryEvaluationException | RepositoryException ex){
            throw new QueryExecutionException("An error occured while executing SPARQL query",ex);
        }
        return retList;
    }

    @Override
    public void update(String sparqlQuery) throws QueryExecutionException {
        try{
            RepositoryConnection repoConn=this.repo.getConnection();
            repoConn.prepareUpdate(QueryLanguage.SPARQL, sparqlQuery).execute();
            repoConn.close();
        }catch(RepositoryException | MalformedQueryException | UpdateExecutionException ex){
            throw new QueryExecutionException("An error occured while executing update SPARQL query",ex);
        }
    }
    
    @Override
    public void clearGraph(String graphspace) throws QueryExecutionException{
        try{
            RepositoryConnection repoConn=this.repo.getConnection();
            repoConn.clear(new URIImpl(graphspace));
            repoConn.close();
        }catch(RepositoryException ex){
            throw new QueryExecutionException("An error occured while droping the contents of the graphspace \""+graphspace+"\"",ex);
        }
    }

    public String connectionDetails(){
        return this.connectionDetails;
    }
}
