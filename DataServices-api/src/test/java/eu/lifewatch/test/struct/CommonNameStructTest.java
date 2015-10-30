package eu.lifewatch.test.struct;

import eu.lifewatch.core.model.CommonNameStruct;
import eu.lifewatch.core.model.Pair;
import java.io.IOException;
import java.io.StringReader;
import junit.framework.TestCase;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.sail.SailRepository;
import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.RDFParseException;
import org.openrdf.sail.inferencer.fc.ForwardChainingRDFSInferencer;
import org.openrdf.sail.memory.MemoryStore;

/**
 * @author Nikos Minadakis (minadakn 'at' ics 'dot' forth 'dot' gr)
 * @author Yannis Marketakis (marketak 'at' ics 'dot' forth 'dot' gr)
 */
public class CommonNameStructTest extends TestCase{
    private CommonNameStruct struct;
    private Repository repository;
    private final String triplesContext="http://localhost/triples";
    
    public CommonNameStructTest(){
        this.struct=new CommonNameStruct();
    }

    @Override
    public void setUp(){
        try{
            this.repository=new SailRepository(new ForwardChainingRDFSInferencer(new MemoryStore()));
            this.repository.initialize();
        }catch(RepositoryException ex){
            fail("Failed to create the main-memory repository used for testing\n"+ex.toString());
        }
    }
    
    @Override
    public void tearDown(){
        try{
            RepositoryConnection repoConn=this.repository.getConnection();
            repoConn.clear(this.repository.getValueFactory().createURI(triplesContext));
            repoConn.close();
        }catch(RepositoryException ex){
            fail("Failed to clear the main-memory repository used for testing\n"+ex.toString());
        }
    }
    
    @Test
    public void testDatasetUri(){
        String datasetUri="http://localhost/dataset";
        assertTrue(this.struct.getDatasetURI().isEmpty());
        this.struct.withDatasetURI(datasetUri);
        assertEquals(datasetUri, struct.getDatasetURI());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        String altDatasetURI=datasetUri+"_using_set";
        this.struct.setDatasetURI(altDatasetURI);
        assertEquals(altDatasetURI, struct.getDatasetURI());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
    }
    
    @Test
    public void testDatasetName(){
        String name="dataset name";
        String altName="dataset name using set";
        assertTrue(this.struct.getDatasetName().isEmpty());
        this.struct.withDatasetName(name);
        assertEquals(name,struct.getDatasetName());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        this.struct.setDatasetName(altName);
        assertEquals(altName,struct.getDatasetName());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        
    }
    
    @Test
    public void testCommonNameUri(){
        String commonNameUri="http://localhost/commonName";
        String altCommonNameUri="http://localhost/commonName_using_set";
        assertTrue(this.struct.getCommonNameURI().isEmpty());
        this.struct.withCommonNameURI(commonNameUri);
        assertEquals(commonNameUri,struct.getCommonNameURI());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        this.struct.setCommonNameURI(altCommonNameUri);
        assertEquals(altCommonNameUri,struct.getCommonNameURI());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
    }
    
    @Test
    public void testCommonName(){
        String commonName="common name";
        String altCommonName="common name using set";
        assertTrue(this.struct.getCommonName().isEmpty());
        this.struct.withCommonName(commonName);
        assertEquals(commonName,struct.getCommonName());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        this.struct.setCommonName(altCommonName);
        assertEquals(altCommonName,struct.getCommonName());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
    }
    
    @Test
    public void testSpeciesNameUri(){
        String speciesNameUri="http://localhost/speciesName";
        String altSpeciesNameUri="http://localhost/speciesName_using_set";
        assertTrue(this.struct.getSpeciesURI().isEmpty());
        this.struct.withSpeciesURI(speciesNameUri);
        assertEquals(speciesNameUri,struct.getSpeciesURI());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        this.struct.setSpeciesURI(altSpeciesNameUri);
        assertEquals(altSpeciesNameUri,struct.getSpeciesURI());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
    }
    
    @Test
    public void testSpeciesName(){
        String speciesName="common name";
        String altSpeciesName="common name using set";
        assertTrue(this.struct.getSpeciesName().isEmpty());
        this.struct.withSpeciesName(speciesName);
        assertEquals(speciesName,struct.getSpeciesName());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        this.struct.setSpeciesName(altSpeciesName);
        assertEquals(altSpeciesName,struct.getSpeciesName());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
    }
    
    @Test
    public void testLanguageUri(){
        String language="http://localhost/language";
        String altLanguage="http://localhost/language_using_set";
        assertTrue(this.struct.getLanguageURI().isEmpty());
        this.struct.withLanguageURI(language);
        assertEquals(language,struct.getLanguageURI());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        this.struct.setLanguageURI(altLanguage);
        assertEquals(altLanguage,struct.getLanguageURI());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
    }
    
    @Test
    public void testLanguageName(){
        String language="language";
        String altLanguage="language using set";
        assertTrue(this.struct.getLanguage().isEmpty());
        this.struct.withLanguage(language);
        assertEquals(language,struct.getLanguage());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        this.struct.setLanguage(altLanguage);
        assertEquals(altLanguage,struct.getLanguage());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
    }
    
    @Test
    public void testContributors(){
        String place1Uri="http://localhost/place1";
        String place1Name="place 1";
        String place2Uri="http://localhost/place2";
        String place2Name="place 2";
        assertTrue(this.struct.getPlaces().isEmpty());
        assertTrue(this.struct.getPlaceURIs().isEmpty());
        assertTrue(this.struct.getPlaceNames().isEmpty());
        this.struct.withPlace(place1Uri, place1Name);
        assertEquals(1,this.struct.getPlaces().size());
        assertEquals(1,this.struct.getPlaceURIs().size());
        assertEquals(1,this.struct.getPlaceNames().size());
        List<Pair> places=this.struct.getPlaces();
        assertEquals(place1Uri, places.get(0).getKey());
        assertEquals(place1Name, places.get(0).getValue());
        this.struct.withPlace(place1Uri, place1Name);
        assertEquals(1,this.struct.getPlaces().size());
        assertEquals(1,this.struct.getPlaceURIs().size());
        assertEquals(1,this.struct.getPlaceNames().size());
        places=this.struct.getPlaces();
        assertEquals(place1Uri, places.get(0).getKey());
        assertEquals(place1Name, places.get(0).getValue());
        this.struct.withPlace(place2Uri, place2Name);
        assertEquals(2,this.struct.getPlaces().size());
        assertEquals(2,this.struct.getPlaceURIs().size());
        assertEquals(2,this.struct.getPlaceNames().size());
        assertTrue(this.struct.getPlaceURIs().containsAll(Arrays.asList(place1Uri,place2Uri)));
        assertTrue(Arrays.asList(place1Uri,place2Uri).containsAll(this.struct.getPlaceURIs()));
        assertTrue(this.struct.getPlaceNames().containsAll(Arrays.asList(place1Name,place2Name)));
        assertTrue(Arrays.asList(place1Name,place2Name).containsAll(this.struct.getPlaceNames()));
    }
    
    public void testAllFields(){
        String datasetUri="http://localhost/dataset";
        String datasetName="name of the dataset";
        String commonNameUri="http://localhost/commonName";
        String commonName="common name";
        String speciesUri="http://localhost/species";
        String speciesName="species name";
        String languageUri="http://localhost/language";
        String languageName="language name";
        String place1Uri="http://localhost/place1";
        String place1Name="place 1";
        String place2Uri="http://localhost/place2";
        String place2Name="place 2";
        
        this.struct.withDatasetURI(datasetUri)
                   .withDatasetName(datasetName)
                   .withCommonNameURI(commonNameUri)
                   .withCommonName(commonName)
                   .withSpeciesName(speciesName)
                   .withSpeciesURI(speciesUri)
                   .withLanguage(languageName)
                   .withLanguageURI(languageUri)
                   .withPlace(place1Uri, place1Name)
                   .withPlace(place2Uri, place2Name);
        
        assertEquals(datasetUri,this.struct.getDatasetURI());
        assertEquals(datasetName,this.struct.getDatasetName());
        assertTrue(this.struct.getPlaceURIs().containsAll(Arrays.asList(place1Uri,place2Uri)));
        assertTrue(Arrays.asList(place1Uri,place2Uri).containsAll(this.struct.getPlaceURIs()));
        assertTrue(this.struct.getPlaceNames().containsAll(Arrays.asList(place1Name,place2Name)));
        assertTrue(Arrays.asList(place1Name,place2Name).containsAll(this.struct.getPlaceNames()));
        assertEquals(speciesName,this.struct.getSpeciesName());
        assertEquals(speciesUri,this.struct.getSpeciesURI());
        assertEquals(commonName,this.struct.getCommonName());
        assertEquals(commonNameUri,this.struct.getCommonNameURI());
        assertEquals(languageName,this.struct.getLanguage());
        assertEquals(languageUri,this.struct.getLanguageURI());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
    }
    
    private boolean importAndValidateTriples(String triples){
        try{
            RepositoryConnection repoConn=this.repository.getConnection();
            repoConn.add(new StringReader(triples), triplesContext, RDFFormat.NTRIPLES, this.repository.getValueFactory().createURI(triplesContext));
            repoConn.close();
            return true;
        }catch(RepositoryException | IOException | RDFParseException ex){
            fail("An error occured while importing triples for testing\n"+ex.toString());
            return false;
        }
    }
}