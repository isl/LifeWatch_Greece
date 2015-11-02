package eu.lifewatch.core.api;

import eu.lifewatch.exception.DataExportException;
import java.io.File;
import java.util.Collection;
import java.util.List;
import eu.lifewatch.exception.DataImportException;
import eu.lifewatch.exception.QueryExecutionException;
import org.openrdf.query.BindingSet;
import org.openrdf.rio.RDFFormat;

/**
 * This interface exposes the functionality of a repository manager. 
 * A repository could be any triplestore (e.g. virtuoso, owlim, sesame, etc.).
 * Every class implementing this interface should implement the basic methods 
 * (import, export, query, update)
 * 
 *
 * @author Nikos Minadakis (minadakn 'at' ics 'dot' forth 'dot' gr)
 * @author Yannis Marketakis (marketak 'at' ics 'dot' forth 'dot' gr)
 */
public interface RepositoryManager{
    /**Imports the given file which is expressed in the given format in the repository in
     * a specific graphspace
     * 
     * @param file the file that will be imported 
     * @param format the format of the given file
     * @param graphspace the graphspace that will be used for importing the contents of the file
     * @throws DataImportException is thrown for any error that might occur (wrong file format, unable to find
     * the file, cannot connect/import to the repository) */
    public void importData(File file, RDFFormat format, String graphspace) throws DataImportException;
    
    /**Import the files which are expressed in the given format in the repository in 
     * a specific graphspace. 
     * 
     * @param files the collection of files that will be imported
     * @param format the format of the given files
     * @param graphspace the graphspace that will be used for storing the contents of the files
     * @throws DataImportException is thrown for any error that might occur (wrong file format, unable to find
     * the file, cannot connect/import to the repository) */
    public void importData(Collection<File> files, RDFFormat format, String graphspace) throws DataImportException;
    
    /**Export the data from the repository. More specificaly it exports the data 
     * from the given graphspace given to the given format. 
     * @param format the format of the exported contents
     * @param graphspace the graphspace containing the contents
     * @return a string representation of the exported contents
     * @throws DataExportException for any error that might occur (i.e. connectivity issues) */
    public String exportData(RDFFormat format, String graphspace) throws DataExportException;
    
    /**Submit the given SPARQL query to the repository and evaluates it. It returns a 
     * list of binding sets with the answer
     * @param sparqlQuery the SPARQL query
     * @return a list of binding sets with the answer of the query
     * @throws QueryExecutionException is thrown if any error that might occur (errors in SPARQL query, connetivity, etc.) */
    public List<BindingSet> query(String sparqlQuery) throws QueryExecutionException;
    
    /**Submit the given SPARQL query to the repository for updating (inserting/deleting) data.
     * 
     * @param sparqlQuery the update SPARQL query
     * @throws QueryExecutionException is thrown if any error occurs while updating the repository */
    public void update(String sparqlQuery) throws QueryExecutionException;
    
    /**Deletes all the contents from a given graphspace.
     * 
     * @param graphSpace the name of the graphspace that will be removed 
     * @throws QueryExecutionException is thrown if any error might occur (during the deletion of the graphspace) */
    public void clearGraph(String graphSpace) throws QueryExecutionException;
}
