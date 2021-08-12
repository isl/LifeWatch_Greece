package eu.lifewatch.core.impl;

import eu.lifewatch.common.Resources;
import eu.lifewatch.core.model.CommonNameStruct;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.apache.log4j.Logger;
import org.openrdf.model.Statement;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.RepositoryResult;
import org.openrdf.repository.sail.SailRepository;
import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.RDFHandlerException;
import org.openrdf.rio.RDFParseException;
import org.openrdf.rio.RDFWriter;
import org.openrdf.rio.Rio;
import org.openrdf.sail.inferencer.fc.ForwardChainingRDFSInferencer;
import org.openrdf.sail.memory.MemoryStore;
import org.w3c.dom.Document;

/**
 * This class contains auxiliary methods that can be exploited for 
 * various purposes.
 * 
 * @author Nikos Minadakis (minadakn 'at' ics 'dot' forth 'dot' gr)
 * @author Yannis Marketakis (marketak 'at' ics 'dot' forth 'dot' gr)
 */
public class Utils {
    
    private static final Logger logger = Logger.getLogger(Utils.class);
    
    /**Takes as input a set of triples and some important URIs and removes from the 
     * first set those triples that have one of the given URIS as their subject. 
     * If we imagine the given set of triples as a graph, this method will practically 
     * return a subgraph containing only the direct neighbours of the given URIs. 
     * 
     * @param nTriples a set of triples in NTriples format 
     * @param urisToKeep the URIs that will be used for determining which triples to keep (those appearing in subject, or object field)
     * @return a subgraph in the form of triples in NTriples format, containing only the direct neighbours of the given URIs. */
    public static String removeIndirectTriples(String nTriples, List<String> urisToKeep){
        String triplesContext="http://triplesContext";
        String subTriplesContext="http://subgraphTriplesContext";
        Repository repository=new SailRepository(new ForwardChainingRDFSInferencer(new MemoryStore()));
        try{
            repository.initialize();
            RepositoryConnection repoConn=repository.getConnection();
            repoConn.add(new StringReader(nTriples), triplesContext, RDFFormat.NTRIPLES, repository.getValueFactory().createURI(triplesContext));
            RepositoryResult<Statement> results=repoConn.getStatements(null, null, null, false, repository.getValueFactory().createURI(triplesContext));
            while(results.hasNext()){
                Statement result=results.next();
                if(urisToKeep.contains(result.getSubject().stringValue()) || urisToKeep.contains(result.getObject().stringValue())){
                    repoConn.add(result, repository.getValueFactory().createURI(subTriplesContext));
                }
            }
            ByteArrayOutputStream out=new ByteArrayOutputStream();
            RDFWriter writer=Rio.createWriter(RDFFormat.NTRIPLES, out);
            repoConn.export(writer, repository.getValueFactory().createURI(subTriplesContext));
            repoConn.close();
            return new String(out.toByteArray(),"UTF-8");
        }catch(RepositoryException | IOException | RDFParseException | RDFHandlerException ex) {
            logger.error("Cannot parse ntriples file - Return the original NTriples file",ex);
            return nTriples;
        }
    }
    
    public static String hashUri(String prefix, String hierarchy, String contents) throws UnsupportedEncodingException{
        String encodedContents=java.net.URLEncoder.encode(contents, "UTF-8").replace("+", "%20");
        return prefix+"/"+hierarchy+"/"+UUID.nameUUIDFromBytes(encodedContents.getBytes()).toString().toUpperCase();
    }
    
    public static void exportXmlToFile(Document document, File file) throws TransformerException {
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        StreamResult result = new StreamResult(file);
        DOMSource source = new DOMSource(document);
        transformer.transform(source, result);
    }
    
    public static void consolidateIptMedobisTransformedResources(File rootFolder, File targetFolder) throws IOException{
        logger.info("Consolidating directory dataset resources");
        List<Path> directoryResources;
        try(Stream<Path> walk = Files.walk(Paths.get(rootFolder.getAbsolutePath()))){
            directoryResources=walk.filter(Files::isRegularFile)
                                   .filter(Files::isReadable)
                                   .filter(p -> p.getFileName().toString().equals(Resources.DIRECTORY_N3_FILENAME))
                                   .collect(Collectors.toList());
        }
        File dirFolder=new File(targetFolder.getAbsolutePath()+"/"+Resources.DIRECTORY_CONSOLIDATED_N3_FOLDER_NAME);
        dirFolder.mkdir();
        StringBuilder directoryResourcesBuilder=new StringBuilder();
        for(Path dirPath : directoryResources){
            for(String line : Files.readAllLines(dirPath, Charset.forName("UTF-8"))){
                directoryResourcesBuilder.append(line)
                                         .append("\n");
            }
        }
        File outputFile=new File(dirFolder.getAbsolutePath()+"/"+Resources.DIRECTORY_CONSOLIDATED_N3_FILENAME);
        logger.info("Export consolidated file with directory medatata "+outputFile.getAbsolutePath());
        BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile),"UTF-8"));
        writer.append(directoryResourcesBuilder.toString());
        writer.flush();
        writer.close();
        
        logger.info("Consolidating dataset metadata resources");
        List<Path> metadataResources;
        for(File folder : rootFolder.listFiles(file -> file.isDirectory())){
            int fileCounter=1;
            try(Stream<Path> walk = Files.walk(Paths.get(folder.getAbsolutePath()))){
                metadataResources=walk.filter(Files::isRegularFile)
                                      .filter(Files::isReadable)
                                      .filter(p -> !p.getFileName().toString().equals(Resources.DIRECTORY_N3_FILENAME))
                                      .collect(Collectors.toList());
            }
            File medatataFolder=new File(targetFolder.getAbsolutePath()+"/"+folder.getName());
            medatataFolder.mkdir();
            StringBuilder metadataResourcesBuilder=new StringBuilder();
            for(Path metPath : metadataResources){
                if(Files.size(metPath)>6*1024*1024){
                    Files.copy(metPath, Paths.get(targetFolder.getAbsolutePath()+"/"+folder.getName()+"/"+metPath.getFileName()));
                }else{
                    for(String line : Files.readAllLines(metPath, Charset.forName("UTF-8"))){
                        metadataResourcesBuilder.append(line)
                                                .append("\n");
                    }
                    if(metadataResourcesBuilder.length()>=8*1024*1024){
                        outputFile=new File(targetFolder.getAbsolutePath()+"/"+folder.getName()+"/"+Resources.METADATA_CONSOLIDATED_N3_FILENAME+"-"+fileCounter+Resources.N3_EXTENSION);
                        logger.info("Export consolidated file with meradata "+outputFile.getAbsolutePath());
                        writer=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile),"UTF8"));
                        writer.append(metadataResourcesBuilder.toString());
                        writer.flush();
                        writer.close();
                        fileCounter+=1;
                        metadataResourcesBuilder=new StringBuilder();
                    }
                }
            }
            outputFile=new File(targetFolder.getAbsolutePath()+"/"+folder.getName()+"/"+Resources.METADATA_CONSOLIDATED_N3_FILENAME+"-"+fileCounter+Resources.N3_EXTENSION);
            logger.info("Export consolidated file with meradata "+outputFile.getAbsolutePath());
            writer=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile),"UTF8"));
            writer.append(metadataResourcesBuilder.toString());
            writer.flush();
            writer.close();
        }
    }
    
    public static void main(String[] args) throws IOException{
        consolidateIptMedobisTransformedResources(new File("D:/Repositories/GitHub/LifeWatch_Greece/DataServices-api/local"), new File("D:/Repositories/GitHub/LifeWatch_Greece/DataServices-api/consolidated"));
        
    }
}