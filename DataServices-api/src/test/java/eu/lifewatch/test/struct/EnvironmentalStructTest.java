package eu.lifewatch.test.struct;

import eu.lifewatch.core.model.Pair;
import java.io.IOException;
import java.io.StringReader;
import junit.framework.TestCase;
import eu.lifewatch.core.model.EnvironmentalStruct;
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
public class EnvironmentalStructTest extends TestCase{
    private EnvironmentalStruct struct;
    private Repository repository;
    private final String triplesContext="http://localhost/triples";
    
    public EnvironmentalStructTest(){
        this.struct=new EnvironmentalStruct();
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
    public void testDimensionTypeUri(){
        String dimensionUri="http://localhost/dimensionType";
        String altDimensionUri="http://localhost/dimensionType_using_set";
        assertTrue(this.struct.getDimensionTypeURI().isEmpty());
        this.struct.withDimensionTypeURI(dimensionUri);
        assertEquals(dimensionUri, struct.getDimensionTypeURI());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        this.struct.withDimensionTypeURI(altDimensionUri);
        assertEquals(altDimensionUri, struct.getDimensionTypeURI());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
    }
    
    @Test
    public void testDimensionUri(){
        String dimensionUri="http://localhost/dimension";
        String altDimensionUri="http://localhost/dimension_using_set";
        assertTrue(this.struct.getDimensionURI().isEmpty());
        this.struct.withDimensionURI(dimensionUri);
        assertEquals(dimensionUri, struct.getDimensionURI());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        this.struct.withDimensionURI(altDimensionUri);
        assertEquals(altDimensionUri, struct.getDimensionURI());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
    }
    
    @Test
    public void testDimensionName(){
        String dimensionName="dimension name";
        String altDimensionName="dimension name using set";
        assertTrue(this.struct.getDimensionName().isEmpty());
        this.struct.withDimensionName(dimensionName);
        assertEquals(dimensionName,struct.getDimensionName());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        this.struct.setDimensionName(altDimensionName);
        assertEquals(altDimensionName,struct.getDimensionName());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
    }
    
    @Test
    public void testDimensionValue(){
        String dimensionValue="dimension value";
        String altDimensionValue="dimension value using set";
        assertTrue(this.struct.getDimensionValue().isEmpty());
        this.struct.withDimensionValue(dimensionValue);
        assertEquals(dimensionValue,struct.getDimensionValue());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        this.struct.setDimensionValue(altDimensionValue);
        assertEquals(altDimensionValue,struct.getDimensionValue());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
    }
    
    @Test
    public void testDimensionunit(){
        String dimensionUnit="dimension unit";
        String altDimensionUnit="dimension unit using set";
        assertTrue(this.struct.getDimensionUnit().isEmpty());
        this.struct.withDimensionUnit(dimensionUnit);
        assertEquals(dimensionUnit,struct.getDimensionUnit());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        this.struct.withDimensionUnit(altDimensionUnit);
        assertEquals(altDimensionUnit,struct.getDimensionUnit());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
    }
    
    @Test
    public void testPlaceUri(){
        String place="http://localhost/place";
        String altPlace="http://localhost/place_using_set";
        assertTrue(this.struct.getPlaceURI().isEmpty());
        this.struct.withPlaceURI(place);
        assertEquals(place,struct.getPlaceURI());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        this.struct.setPlaceURI(altPlace);
        assertEquals(altPlace,struct.getPlaceURI());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
    }
    
    @Test
    public void testPlaceName(){
        String place="name of the place";
        String altPlace="name of the place using set";
        assertTrue(this.struct.getPlaceName().isEmpty());
        this.struct.withPlaceName(place);
        assertEquals(place,struct.getPlaceName());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        this.struct.setPlaceName(altPlace);
        assertEquals(altPlace,struct.getPlaceName());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
    }
    
    @Test
    public void testStationUri(){
        String station="http://localhost/station";
        String altStation="http://localhost/station_using_set";
        assertTrue(this.struct.getStationURI().isEmpty());
        this.struct.withStationURI(station);
        assertEquals(station,struct.getStationURI());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        this.struct.setStationURI(altStation);
        assertEquals(altStation,struct.getStationURI());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
    }
    
    @Test
    public void testStationName(){
        String station="name of the station";
        String altStation="name of the station using set";
        assertTrue(this.struct.getStationName().isEmpty());
        this.struct.withStationName(station);
        assertEquals(station,struct.getStationName());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        this.struct.setStationName(altStation);
        assertEquals(altStation,struct.getStationName());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
    }
    
    @Test
    public void testMeasurementEventUri(){
        String measurementEvent="http://localhost/measurementEvent";
        String altMeasurementEvent="http://localhost/measurementEvent_using_set";
        assertTrue(this.struct.getMeasurementEventURI().isEmpty());
        this.struct.withMeasurementEventURI(measurementEvent);
        assertEquals(measurementEvent,struct.getMeasurementEventURI());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        this.struct.setMeasurementEventURI(altMeasurementEvent);
        assertEquals(altMeasurementEvent,struct.getMeasurementEventURI());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
    }
    
    @Test
    public void testTimespan(){
        String timespan="timespan";
        String altTimespan="timespan using set";
        assertTrue(this.struct.getTimeSpan().isEmpty());
        this.struct.withTimeSpan(timespan);
        assertEquals(timespan,struct.getTimeSpan());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
        this.struct.setTimeSpan(altTimespan);
        assertEquals(altTimespan,struct.getTimeSpan());
        assertTrue(this.importAndValidateTriples(struct.toNtriples()));
    }
    
    @Test
    public void testContributors(){
        String actor1Uri="http://localhost/actor1";
        String actor1Name="actor 1";
        String actor2Uri="http://localhost/actor2";
        String actor2Name="actor 2";
        assertTrue(this.struct.getActors().isEmpty());
        assertTrue(this.struct.getActorURIs().isEmpty());
        assertTrue(this.struct.getActorNames().isEmpty());
        this.struct.withActor(actor1Uri, actor1Name);
        assertEquals(1,this.struct.getActors().size());
        assertEquals(1,this.struct.getActorURIs().size());
        assertEquals(1,this.struct.getActorNames().size());
        List<Pair> actors=this.struct.getActors();
        assertEquals(actor1Uri, actors.get(0).getKey());
        assertEquals(actor1Name, actors.get(0).getValue());
        this.struct.withActor(actor1Uri, actor1Name);
        assertEquals(1,this.struct.getActors().size());
        assertEquals(1,this.struct.getActorURIs().size());
        assertEquals(1,this.struct.getActorNames().size());
        actors=this.struct.getActors();
        assertEquals(actor1Uri, actors.get(0).getKey());
        assertEquals(actor1Name, actors.get(0).getValue());
        this.struct.withActor(actor2Uri, actor2Name);
        assertEquals(2,this.struct.getActors().size());
        assertEquals(2,this.struct.getActorURIs().size());
        assertEquals(2,this.struct.getActorNames().size());
        assertTrue(this.struct.getActorURIs().containsAll(Arrays.asList(actor1Uri,actor2Uri)));
        assertTrue(Arrays.asList(actor1Uri,actor2Uri).containsAll(this.struct.getActorURIs()));
        assertTrue(this.struct.getActorNames().containsAll(Arrays.asList(actor1Name,actor2Name)));
        assertTrue(Arrays.asList(actor1Name,actor2Name).containsAll(this.struct.getActorNames()));
    }
    
    public void testAllFields(){
        String datasetUri="http://localhost/dataset";
        String datasetName="name of the dataset";
        String actor1Uri="http://localhost/actor1";
        String actor1Name="actor 1";
        String actor2Uri="http://localhost/actor2";
        String actor2Name="actor 2";
        String measurementEventUri="http://localhost/measurementEvent";
        String dimensionUnit="dimension unit";
        String dimensionValue="dimension value";
        String dimensionName="dimension name";
        String dimensionUri="http://localhost/dimension";
        String dimensionTypeUri="http://localhost/dimensionType";
        String timespan="timespan";
        String stationUri="http://localhost/station";
        String stationName="station name";
        String placeUri="http://localhost/place";
        String placeName="place";
        
        this.struct.withDatasetURI(datasetUri)
                   .withDatasetName(datasetName)
                   .withActor(actor1Uri, actor1Name)
                   .withActor(actor2Uri, actor2Name)
                   .withMeasurementEventURI(measurementEventUri)
                   .withDimensionName(dimensionName)
                   .withDimensionTypeURI(dimensionTypeUri)
                   .withDimensionURI(dimensionUri)
                   .withDimensionUnit(dimensionUnit)
                   .withDimensionValue(dimensionValue)
                   .withTimeSpan(timespan)
                   .withStationURI(stationUri)
                   .withStationName(stationName)
                   .withPlaceURI(placeUri)
                   .withPlaceName(placeName);
                   
        assertEquals(datasetUri,this.struct.getDatasetURI());
        assertEquals(datasetName,this.struct.getDatasetName());
        assertTrue(this.struct.getActorURIs().containsAll(Arrays.asList(actor1Uri,actor2Uri)));
        assertTrue(Arrays.asList(actor1Uri,actor2Uri).containsAll(this.struct.getActorURIs()));
        assertTrue(this.struct.getActorNames().containsAll(Arrays.asList(actor1Name,actor2Name)));
        assertTrue(Arrays.asList(actor1Name,actor2Name).containsAll(this.struct.getActorNames()));
        assertEquals(measurementEventUri,this.struct.getMeasurementEventURI());
        assertEquals(dimensionName,this.struct.getDimensionName());
        assertEquals(dimensionTypeUri,this.struct.getDimensionTypeURI());
        assertEquals(dimensionUnit,this.struct.getDimensionUnit());
        assertEquals(dimensionUri,this.struct.getDimensionURI());
        assertEquals(dimensionValue,this.struct.getDimensionValue());
        assertEquals(timespan,this.struct.getTimeSpan());
        assertEquals(stationName,this.struct.getStationName());
        assertEquals(stationUri,this.struct.getStationURI());
        assertEquals(placeName,this.struct.getPlaceName());
        assertEquals(placeUri,this.struct.getPlaceURI());
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