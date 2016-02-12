/*
 *
 * Copyright 2015 FORTH-ICS-ISL (http://www.ics.forth.gr/isl/)
 * Foundation for Research and Technology - Hellas (FORTH)
 * Institute of Computer Science (ICS)
 * Information Systems Laboratory (ISL)
 *
 * Licensed under the EUPL, Version 1.1 or – as soon they
 * will be approved by the European Commission - subsequent
 * versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the
 * Licence.
 * You may obtain a copy of the Licence at:
 *
 * http://joinup.ec.europa.eu/software/page/eupl/licence-eupl
 *
 * Unless required by applicable law or agreed to in
 * writing, software distributed under the Licence is
 * distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied.
 * See the Licence for the specific language governing
 * permissions and limitations under the Licence.
 *
 */
package eu.lifewatchgreece.annotations.repository;

import com.github.anno4j.Anno4j;
import com.github.anno4j.io.StatementSailHandler;
import com.github.anno4j.model.Annotation;
import com.github.anno4j.model.Body;
import com.github.anno4j.model.Motivation;
import com.github.anno4j.model.MotivationFactory;
import com.github.anno4j.model.Target;
import com.github.anno4j.model.impl.agent.Person;
import com.github.anno4j.model.impl.agent.Software;
import com.github.anno4j.querying.QueryService;
import eu.lifewatchgreece.annotations.ontologies.polytraits.body.PolytraitsBody;
import eu.lifewatchgreece.annotations.ontologies.polytraits.body.TextAreaBody;
import eu.lifewatchgreece.annotations.ontologies.umbel.target.Animal;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openrdf.model.Resource;
import org.openrdf.model.URI;
import org.openrdf.model.impl.URIImpl;
import org.openrdf.query.GraphQuery;
import org.openrdf.query.GraphQueryResult;
import org.openrdf.query.MalformedQueryException;
import org.openrdf.query.QueryEvaluationException;
import org.openrdf.query.QueryLanguage;
import org.openrdf.query.QueryResults;
import org.openrdf.query.TupleQuery;
import org.openrdf.query.TupleQueryResult;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.config.RepositoryConfigException;
import org.openrdf.repository.object.ObjectConnection;
import org.openrdf.repository.object.ObjectRepository;
import org.openrdf.repository.sail.SailRepository;
import org.openrdf.repository.sparql.SPARQLRepository;
import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.RDFHandlerException;
import org.openrdf.rio.RDFParseException;
import org.openrdf.rio.RDFParser;
import org.openrdf.rio.RDFWriter;
import org.openrdf.rio.Rio;
import org.openrdf.sail.memory.MemoryStore;

/**
 * Basic class that wraps around the anno4j and provides all functionality for
 * storing and retrieving the annotations
 *
 * @author papadako
 */
public class Anno4jRepository {

    private Anno4j _anno4j = null;
    private Anno4j _parseAnno4j = null;
    private static final Anno4jRepository _SINGLETON = new Anno4jRepository();
    private URI _graph = null;
    // This holds the generated uris for the annotations and the bodies resources
    private static final String url = "http://lifewatchgreece.eu/entity/annotations/";

    /**
     * private constructor, since we use the Singleton pattern. It initializes
     * the repository
     */
    private Anno4jRepository() {
        if (_anno4j == null) {
            Repository repository;
            // Get the properties file
            InputStream propertiesFile = Anno4jRepository.class
                    .getResourceAsStream("/endpoint.properties");

            // Read the properties that hold the endpoint and name of graph
            //   and create a new repository
            try {
                EndPointProperties repoProperties = new EndPointProperties(propertiesFile);
                String endPointQuery = repoProperties.getEndPointQuery();
                String endPointUpdate = repoProperties.getEndPointUpdate();
                _graph = repoProperties.getGraph();

                // Initialize the repository
                repository = new SPARQLRepository(endPointQuery, endPointUpdate);
                repository.initialize();
                // Create new anno4j 
                _anno4j = new Anno4j(repository, new URIGenerator());

                // The anno4j for parsing. Have to be sure to synchronize it
                // Memory store repository
                this._parseAnno4j = new Anno4j(new SailRepository(new MemoryStore()), new URIGenerator());

            } catch (IOException | RepositoryException | RepositoryConfigException ex) {
                Logger.getLogger(Anno4jRepository.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Returns the instance of the singleton.
     *
     * @return
     */
    public static Anno4jRepository getInstance() {

        return _SINGLETON;
    }

    /**
     * Returns if the repository is initialized
     *
     * @return
     */
    public boolean isInitialized() {
        return _SINGLETON != null;
    }

    /**
     * Persists the annotation to the triplestore
     *
     * @param annotation
     */
    public void persist(Annotation annotation) {
        try {
            // Persist annotation
            _anno4j.persist(annotation, _graph);

        } catch (RepositoryException ex) {
            Logger.getLogger(Anno4jRepository.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Removes the annotation from the configured SPARQL endpoint with a
     * corresponding DELETE query. Should be done in the anno4j code. For now
     * just do it here.
     *
     * @param annotation annotation to remove from the SPARQL endpoint
     */
    public void remove(Annotation annotation) {
        try {
            ObjectRepository repo = _anno4j.getObjectRepository();
            ObjectConnection connection = repo.getConnection();

            if (_graph != null) {
                connection.setReadContexts(_graph);
                connection.setInsertContext(_graph);
                connection.setRemoveContexts(_graph);
            }

            /* The following do not work.
            Currently we leave targets and bodies to the triplestore
            // Try to remove the body
            Body body = annotation.getBody();
            if (body instanceof PolytraitsBody) {
                body = connection.getObject(PolytraitsBody.class, body.getResource());
                connection.removeDesignation(body, body.getClass());
            } else if (body instanceof TextAreaBody) {
                TextAreaBody text = connection.getObject(TextAreaBody.class, body.getResource());
                text.setFormat(null);
                text.setLanguage(null);
                text.setValue(null);
                connection.removeDesignation(text, TextAreaBody.class);
            }
            // Try to remove the body
            Set<Target> targets = annotation.getTarget();
            for (Target target : targets) {
                if (target instanceof Animal) {
                    Animal animal = connection.getObject(Animal.class, target.getResource());
                    connection.removeDesignation(animal, Animal.class);
                }
            }*/
            // Set all fields of the annotation to null
            annotation.setBody(null);
            annotation.setTarget(null);
            annotation.setAnnotatedBy(null);
            annotation.setAnnotatedAt(null);
            annotation.setMotivatedBy(null);
            annotation.setSerializedBy(null);
            annotation.setSerializedAt(null);

            // Now remove the annotation
            connection.removeDesignation(annotation, Annotation.class);

            connection.close();

        } catch (RepositoryException ex) {
            Logger.getLogger(Anno4jRepository.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Updates the old annotation with the new one for the configured SPARQL
     * endpoint with a corresponding DELETE query and INSERT query Keep the same
     * resource for the new annotation. Should be done in the anno4j code. For
     * now just do it here.
     *
     * @param toUpdate annotation to update from the SPARQL endpoint
     * @param anno the new annotation
     */
    public void update(Annotation toUpdate, Annotation anno) {
        try {
            ObjectRepository repo = _anno4j.getObjectRepository();
            ObjectConnection connection = repo.getConnection();
            if (_graph != null) {
                connection.setReadContexts(_graph);
                connection.setInsertContext(_graph);
                connection.setRemoveContexts(_graph);
            }

            // Keep the same resource for the annotation
            Resource oldResource = toUpdate.getResource();
            anno.setResource(oldResource);

            this.remove(toUpdate);
            this.persist(anno);

            connection.close();

        } catch (RepositoryException ex) {
            Logger.getLogger(Anno4jRepository.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param graph The graph under which we want to search
     * @return the query service
     */
    public QueryService createQueryService(URI graph) {
        return _anno4j.createQueryService(graph);
    }

    /**
     *
     * @param <T> The type of the object we want to create
     * @param clazz The class of the object we want to create
     * @return the object
     */
    public <T> T createObject(Class<T> clazz) {
        try {
            return _anno4j.createObject(clazz);
        } catch (RepositoryException | IllegalAccessException | InstantiationException ex) {
            Logger.getLogger(Anno4jRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Motivation createDescribingMotivation() {
        try {
            return MotivationFactory.getDescribing(_anno4j);
        } catch (RepositoryException | IllegalAccessException | InstantiationException ex) {
            Logger.getLogger(Anno4jRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Returns the graph used for persisting annotations
     *
     * @return
     */
    public URI getGraph() {
        return _graph;
    }

    /**
     *
     * @return
     */
    public String getPrefixURLOfAnnotations() {
        return url;
    }

    /**
     * Returns the annotation with id
     *
     * @param url
     * @return
     */
    public Annotation getAnnotation(String url) {
        try {
            return _anno4j.getObjectRepository().getConnection().getObject(Annotation.class, url);
        } catch (ClassCastException ex) {
            Logger.getLogger("No annotation for url " + url);
            Logger.getLogger(Anno4jRepository.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RepositoryException | QueryEvaluationException ex) {
            Logger.getLogger(Anno4jRepository.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    /**
     * Execute a tuple query
     *
     * @param query
     * @return
     */
    public TupleQueryResult sparqlTupleQuery(String query) {
        TupleQueryResult result = null;
        try {
            RepositoryConnection conn = _anno4j.getRepository().getConnection();
            // Prepare the query
            TupleQuery q = conn.prepareTupleQuery(QueryLanguage.SPARQL, query);
            try {
                result = q.evaluate();
            } catch (QueryEvaluationException ex) {
                Logger.getLogger(Anno4jRepository.class.getName()).log(Level.SEVERE, null, ex);
            }
            return result;
        } catch (RepositoryException | MalformedQueryException ex) {
            Logger.getLogger(Anno4jRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    /**
     * Executes a graph based query
     *
     * @param query
     * @return
     */
    public GraphQueryResult sparqlGraphQuery(String query) {
        GraphQueryResult result = null;
        try {
            RepositoryConnection conn = _anno4j.getRepository().getConnection();
            // Prepare the query
            GraphQuery q = conn.prepareGraphQuery(QueryLanguage.SPARQL, query);
            try {
                result = q.evaluate();
            } catch (QueryEvaluationException ex) {
                Logger.getLogger(Anno4jRepository.class.getName()).log(Level.SEVERE, null, ex);
            }
            return result;
        } catch (RepositoryException | MalformedQueryException ex) {
            Logger.getLogger(Anno4jRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    /**
     * This is a fast way to print the annotations. Much much faster using the
     * appropriate SPARQL query. If this change is accepted in anno4j then we
     * will remove this
     *
     * @param anno
     * @return
     */
    public String getAnnotationAsJsonLd(Annotation anno) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            /*
            http://stackoverflow.com/questions/33241812/sparql-query-to-get-all-triples-related-to-a-specific-subject
            CONSTRUCT {
                <http://lifewatchgreece.eu/entity/annotations/6fe690c7-371e-476b-affd-919ba062ed13> ?prop ?val .
                ?child ?childProp ?childPropVal .
                ?someSubj ?incomingChildProp ?child .
            }
            WHERE {
                <http://lifewatchgreece.eu/entity/annotations/6fe690c7-371e-476b-affd-919ba062ed13>?prop ?val ; (<>|!<>)+ ?child .
                ?child ?childProp ?childPropVal.
                ?someSubj ?incomingChildProp ?child.
            }
            
             */
            // Get the whole graph of a specific annotation 
            String query = "CONSTRUCT {\n"
                    + "   <" + anno.getResourceAsString() + "> ?prop ?val .\n"
                    + "   ?child ?childProp ?childPropVal . \n"
                    //+ "   ?someSubj ?incomingChildProp ?child .\n"
                    + "}\n"
                    + "WHERE {\n"
                    + "     <" + anno.getResourceAsString() + "> ?prop ?val ; (<>|!<>)+ ?child . \n"
                    + "     ?child ?childProp ?childPropVal.\n"
                    //+ "     ?someSubj ?incomingChildProp ?child. \n"
                    + "}";
            RDFWriter writer = Rio.createWriter(RDFFormat.JSONLD, out);
            // Execute the query
            GraphQueryResult results = sparqlGraphQuery(query);
            Rio.write(QueryResults.asModel(results), writer);
        } catch (RDFHandlerException e) {
            e.printStackTrace();
        } catch (QueryEvaluationException ex) {
            Logger.getLogger(Anno4jRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        return out.toString();
    }
    /**
     * We should not be creating a new annotation since anno4j has already read
     * it. But the annotation that we have read seems to lead to errors.
     *
     * @param jsonldAnnotation
     * @return
     */
    public List<Annotation> createAnnotationsFromJSONLD(String jsonldAnnotation) {
        List<Annotation> annos = new ArrayList<>();

        try {
            URL urlParse = new URL(getPrefixURLOfAnnotations());
            // We have to synchronize this so that no other user affects the repositories used for
            // parsing the jsonld objects sent from the client. Have to rethink though
            synchronized (this) {
                long t1 = System.currentTimeMillis();
                // But we have to update it so that we create our own identifiers for the body and target
                annos = Anno4jRepository.getInstance().parse(jsonldAnnotation, urlParse, RDFFormat.JSONLD);
                long t2 = System.currentTimeMillis();
                System.out.println("Parsing and creation annotation " + (t2 - t1));
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(Anno4jRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        return annos;
    }

    /**
     * Used to parse a given text content, supported in a given serialization
     * format.
     *
     * @param content The String representation of the textcontent.
     * @param documentURL The basic URL used for namespaces.
     * @param format The format of the given serialization. Needs to be
     * supported of an instance of RDFFormat.
     * @return A list of annotations
     */
    private List<Annotation> parse(String content, URL url, RDFFormat format) {
        List<Annotation> annos = new LinkedList<>();
        List<Annotation> orig;
        try {
            // Use this connection for parsing the jsonld and create the annotation
            RepositoryConnection con = _parseAnno4j.getRepository().getConnection();

            RDFParser parser = Rio.createParser(format);
            try {
                StatementSailHandler handler = new StatementSailHandler(con);

                parser.setRDFHandler(handler);
                byte[] bytes = content.getBytes("UTF-8");
                try (InputStream stream = new ByteArrayInputStream(bytes)) {
                    parser.parse(stream, url.toString());
                }
            } catch (RDFHandlerException | RDFParseException | IOException e) {
                e.printStackTrace();
            }

            // Get objects now
            ObjectConnection conObject = _parseAnno4j.getObjectRepository().getConnection();
            orig = conObject.getObjects(Annotation.class).asList();

            long t1 = System.currentTimeMillis();
            for (Annotation old : orig) {
                // Create a new annotation for our main repository
                // Be careful. Make sure that the returned annotation is an
                // updated object from our repository
                Annotation anno = persistAnnotation(old, conObject, url);
                annos.add(anno);
            }

            long t2 = System.currentTimeMillis();
            System.out.println("Create new annotation " + (t2 - t1));

            // Clear and close the alibaba object repository used for parsing
            con.clear();
            conObject.clear();
            con.close();
            conObject.close();

            return annos;
        } catch (RepositoryException | QueryEvaluationException ex) {
            Logger.getLogger(Anno4jRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        return annos;
    }

    /**
     * Method that creates annotation, using the correct IRIs for bodies,
     * targets, etc. Adds also the correct classes using the createObject method
     * When issues like https://github.com/anno4j/anno4j/issues/63 have been
     * fixed in anno4j, we will hopefully do not need this.
     *
     * @param jsonld
     * @param con
     * @param url
     * @return
     */
    private Annotation persistAnnotation(Annotation jsonld, ObjectConnection con, URL url) {
        Annotation anno = null;

        try {

            // Add new resources for annotation id
            // Create a URI for the annotation
            UUID idAnno = UUID.randomUUID();
            // Use the UUID
            String annoURI = url.toString() + idAnno.toString();
            // Create the resource
            Resource annoResource = new URIImpl(annoURI);

            // Create a new annotation in our main repository
            anno = createObject(Annotation.class);
            // Set resource of anno
            anno.setResource(annoResource);

	    // This is magic.... Don't know how to overcome it
   	    System.out.println(jsonld.getBody().toString());
            // use getObject to get the Object from the parse object repository
            Body body = con.getObject(Body.class, jsonld.getBody().getResource());
            if (validBody(body)) {
                setBody(anno, body, url);
            } else {
                return null;
            }

            // Use the targets read from jsonld and create target of appropriate type
            addTargets(jsonld, anno);

            // Now modify the annotation to include serialization information
            addProvenanceAndSerializationInformation(jsonld, anno);

            // For now set motivation to describing
            // anno4j seems also to not support motivations from json
            // BUG 1: https://github.com/anno4j/anno4j/issues/63
            // BUG 2: https://github.com/anno4j/anno4j/issues/64
            // Override it by setting the Resource ourselves
            Motivation motivation = createDescribingMotivation();
            //motivation.setResourceAsString("http://www.w3.org/ns/oa#describing");
            //anno.setMotivatedBy(jsonld.getMotivatedBy());
            anno.setMotivatedBy(motivation);
            // Return the new annotation

            // Persist it
            persist(anno);

            // Ask the repository and get the annotation from there so that it is up to date
            // Alibaba is lazy and creates a weird situations
            anno = getAnnotation(annoURI);
            return anno;
        } catch (RepositoryException | QueryEvaluationException ex) {
            Logger.getLogger(Anno4jRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        return anno;
    }

    /**
     *
     * @param body the body to update its resource
     * @param url
     */
    private void setBody(Annotation anno, Body body, URL url) {
        //Do the same for the body
        UUID idAnnoBody = UUID.randomUUID();
        // Use the UUID
        String annoBodyURI = url.toString() + "bodies/" + idAnnoBody.toString();
        // Create the resource
        Resource annoBodyResource = new URIImpl(annoBodyURI);
        body.setResource(annoBodyResource);
	// Another magic
	System.out.println(anno.getBody());
        anno.setBody(body);
    }

    /**
     *
     * @param given
     * @return boolean if valid or not
     */
    private boolean validBody(Body given) {
        boolean valid = false;
        if (given instanceof PolytraitsBody) {
            PolytraitsBody body = (PolytraitsBody) given;

            // If body is valid then return the correct body
            if (body.isValid()) {
                valid = true;
            } else {
                Logger.getLogger(Anno4jRepository.class.getName()).log(Level.SEVERE, null, "This is not a valid body!");
            }

        } else if (given instanceof TextAreaBody) {
            TextAreaBody body = (TextAreaBody) given;
            if (body.isValid()) {
                valid = true;
            } else {
                Logger.getLogger(Anno4jRepository.class.getName()).log(Level.SEVERE, null, "This is not a valid body!");
            }
        }

        return valid;
    }

    /**
     * Create a target using the read annotation
     *
     * @param jsonld the jsonld read annotation
     * @param jsonld the new anno
     * @return
     */
    private void addTargets(Annotation jsonld, Annotation anno) {
        // Cast targets
        Set<Target> jsonldTargets = jsonld.getTarget();
        Set<Target> newTargets = new HashSet<>();

        // BUG from anno4j
        // Targets do not seem to work, there is a wrong cast message
        // Cannot cast object.proxies._73522219e._EntityProxy206273b999 to com.github.anno4j.model.Target
        // So for now use Object and create a new anno
        for (Object old : jsonldTargets) {
            //Target target = old;    // If we gen a target with no body it might lead to exceptions
            Target target = createObject(Animal.class);
            target.setResourceAsString(old.toString());
            newTargets.add(target);
        }
        anno.setTarget(newTargets);
    }

    /**
     * Private method that inserts serialization and provenance information to
     * the annotation sent.
     *
     * @param jsonld the jsonld read annotation
     */
    private void addProvenanceAndSerializationInformation(Annotation jsonld, Annotation anno) {
        // Store when this annotation was annotated in the client
        anno.setAnnotatedAt(jsonld.getAnnotatedAt());

        // Create the person agent for the annotation
        // We need auth working so that we know the name and the login of the user
        // Use a default lifewatch account for now
        Person person = createObject(Person.class);
        person.setResourceAsString("http://lifewatchgreece.eu/entity/annotations/user/lifewatch");
        person.setName("Default lifewatch account");
        person.setNick("lifewatch");
        // If the user has an openID
        //person.setOpenID("http://example.org/agent1/openID1");
        // By whom
        anno.setAnnotatedBy(person);

        // Create the software agent for the annotation
        Software software = createObject(Software.class);
        software.setResourceAsString("http://www.lifewatchgreece.eu");
        software.setName("LifeWatch-Greece platform");
        software.setHomepage("http://www.lifewatchgreece.eu");
        // Serialized by which software
        anno.setSerializedBy(software);

        // Get the current date
        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        String strNow = format.format(now);
        // When
        anno.setSerializedAt(strNow);
    }
}
