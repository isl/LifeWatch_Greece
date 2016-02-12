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
package eu.lifewatchgreece.annotations.controllers.REST;

import com.github.anno4j.model.Annotation;
import com.github.anno4j.querying.QueryService;
import eu.lifewatchgreece.annotations.repository.Anno4jRepository;
import eu.lifewatchgreece.annotations.utils.JsonLdHelper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import org.apache.cxf.rs.security.cors.CorsHeaderConstants;
import org.apache.cxf.rs.security.cors.LocalPreflight;
import org.apache.marmotta.ldpath.parser.ParseException;
import org.codehaus.jettison.json.JSONObject;
import org.openrdf.model.URI;
import org.openrdf.model.Value;
import org.openrdf.query.BindingSet;
import org.openrdf.query.MalformedQueryException;
import org.openrdf.query.QueryEvaluationException;
import org.openrdf.query.TupleQueryResult;
import org.openrdf.repository.RepositoryException;

/**
 *
 * @author papadako
 */
@Path("/")
public class AnnotationsRESTService {
    @Context
    private HttpHeaders headers;
    /**
     * COLLECTION: Returns json-ld annotations of all annotations stored in the
     * triplestore
     *
     * @param req
     * @return
     */
    @GET
    @Path("/")
    @Produces("application/ld+json")
    public Response getAnnotationsAll(@Context HttpServletRequest req) {
        Anno4jRepository repo = Anno4jRepository.getInstance();
        URI graph = repo.getGraph();

        // Set QueryService
        QueryService queryService = repo.createQueryService(graph);
        List<Annotation> annos = null;

        // Ask the repository if we have such an annotations
        try {
            // Get all annotations
            annos = queryService
                    .execute();
        } catch (ParseException | RepositoryException | MalformedQueryException | QueryEvaluationException ex) {
            Logger.getLogger(AnnotationsRESTService.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (annos != null && !annos.isEmpty()) {
            // This is the array of annotations
            StringBuilder jsonLDArray = new StringBuilder();
            boolean started = false;
            jsonLDArray.append("[");  // Start the array
            for (Annotation activeAnno : annos) {
                if (started) {
                    jsonLDArray.append(',');
                }
                String jsonld = repo.getAnnotationAsJsonLd(activeAnno);

                jsonLDArray.append(JsonLdHelper.compactJSONLD(jsonld));
                // Every other annotation should have a , in front
                started = true;
            }

            jsonLDArray.append("]");  // End the array

            // Return all the annotations of the user
            Response.ResponseBuilder rb = Response.status(200)
                    .entity(jsonLDArray.toString());
            // Set headers for access control, etc.
            setGeneralHeaders(rb, req);

            return rb.build();
        } else {
            // Not found
            String notFound = "No annotations were found\n";
            Response.ResponseBuilder rb = Response.status(404)
                    .entity(notFound);
            // Set headers for access control, etc.
            setGeneralHeaders(rb, req);

            return rb.build();
        }
    }

    /**
     * Annotation container: Return json-ld or turtle representation of an
     * annotation with id
     *
     * This conforms to 4.2.1 of Web Annotation Protocol
     * http://www.w3.org/TR/annotation-protocol/ draft of 2 July 2015.
     *
     * @param annoID (the id of the annotation we want to retrieve)
     * @param req The request, used for caching (ETag)
     * @param reqJAXRS
     * @return
     */
    @GET
    @Path("/{annoID}")
    @Produces("application/ld+json")
    //@Produces("text/turtle")  Do conform with W3C we should also produce turtle
    public Response getAnnotationWithID(@PathParam("annoID") String annoID,
            @Context HttpServletRequest req, @Context Request reqJAXRS) {

        Anno4jRepository repo = Anno4jRepository.getInstance();
        String uriAnno = repo.getPrefixURLOfAnnotations() + annoID;

        Annotation anno = repo.getAnnotation(uriAnno);

        if (anno != null) {

            System.out.println(anno);
            // Cache control
            //Calculate the ETag on last modified date of user resource
            EntityTag etag = new EntityTag(anno.getSerializedAt().hashCode() + "");

            Response.ResponseBuilder rb;

            //Verify if it matched with etag available in http request
            rb = reqJAXRS.evaluatePreconditions(etag);

            //Create cache control header
            CacheControl cc = new CacheControl();
            //Set max age to one day
            cc.setMaxAge(86400);

            //If ETag matches the rb will be non-null;
            //Use the rb to return the response without any further processing
            if (rb != null) {
                setGeneralHeaders(rb, req);
                return rb.cacheControl(cc).tag(etag).build();
            }
            String jsonld = repo.getAnnotationAsJsonLd(anno);

            // Currently returns only the first annotation
            rb = Response.status(200)
                    .entity(JsonLdHelper.compactJSONLD(jsonld))
                    // Holds the URI of the created annotation, needed by WAP
                    .header("Location", anno.getResourceAsString())
                    .cacheControl(cc).tag(etag);    // ETag used for cache control
            // Set headers for access control, etc.
            setGeneralHeaders(rb, req);
            return rb.build();
        } else {
            // Not found
            String notFound = anno + " not found\n";
            Response.ResponseBuilder rb = Response.status(404)
                    .entity(notFound);
            // Set headers for access control, etc.
            setGeneralHeaders(rb, req);
            return rb.build();
        }
    }

    /**
     * COLLECTION: Returns json-ld annotations of user with userID Currently no
     * cache control. How can we be sure if things have changed? Even if we now
     * the latest change, we are not sure that some annotation has not been
     * deleted.
     *
     * @param userID (the id of the user whose annotations we want to retrieve)
     * @param req req The request, used for caching (ETag)
     * @return
     */
    @GET
    @Path("/user/{userID}")
    @Produces("application/ld+json")
    public Response getAnnotationsOfUser(@PathParam("userID") String userID, @Context HttpServletRequest req) {
        Anno4jRepository repo = Anno4jRepository.getInstance();
        URI graph = repo.getGraph();

        // Set QueryService
        QueryService queryService = repo.createQueryService(graph);
        List<Annotation> annos = null;

        // Ask the repository if we have such an annotations
        try {
            // Get all annotations with this specific annoID
            annos = queryService.addCriteria("oa:annotatedBy / foaf:nick", userID).execute();
        } catch (ParseException | RepositoryException | MalformedQueryException | QueryEvaluationException ex) {
            Logger.getLogger(AnnotationsRESTService.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (annos != null && !annos.isEmpty()) {
            // This is the array of annotations
            StringBuilder jsonLDArray = new StringBuilder();
            boolean started = false;
            jsonLDArray.append("[");  // Start the array
            for (Annotation activeAnno : annos) {
                if (started) {
                    jsonLDArray.append(',');
                }
                // We will have to replace this in the future
                String jsonld = repo.getAnnotationAsJsonLd(activeAnno);
                jsonLDArray.append(JsonLdHelper.compactJSONLD(jsonld));
                // Every other annotation should have a , in front
                started = true;
            }

            jsonLDArray.append("]");  // End the array

            // Return all the annotations of the user
            Response.ResponseBuilder rb = Response.status(200)
                    .entity(jsonLDArray.toString());
            // Set headers for access control, etc.
            setGeneralHeaders(rb, req);
            return rb.build();
        } else {
            // Not found
            String notFound = "Annotations from user with nick name " + userID + " were not found\n";
            Response.ResponseBuilder rb = Response.status(404)
                    .entity(notFound);
            // Set headers for access control, etc.
            setGeneralHeaders(rb, req);
            return rb.build();
        }
    }

    /**
     * COLLECTION: Returns json-ld with the uris of the available targets that
     * belong to an annotation. Currently no cache control.
     *
     * @param uris (the uris of the target ids for which we want to check if
     * they have annotation
     * @param req req The request, used for caching (ETag)
     * @return
     */
    @POST
    @Path("/targetsOnly/")
    @Produces("application/json")
    public Response getTargets(@QueryParam("uris") String uris, @Context HttpServletRequest req) {
        Anno4jRepository repo = Anno4jRepository.getInstance();
        // This will hold the URIs given by the user
        List<String> givenURIs = new ArrayList<>();
        Map<String, Integer> annotated = new HashMap<>();
        String sparqlQ = null;

        // TODO: Parse the uris that were sent from the client
        // Now create the SPARQL query to use
        if (givenURIs.isEmpty()) {
            // Create the appropriate SPARQL query
            sparqlQ = "PREFIX oa: <http://www.w3.org/ns/oa#>\n"
                    + "select ?z\n"
                    + "where{?x oa:hasTarget ?z}";
        } else {
            // TODO Create the appropriate SPARQL query
            // Make the appropriate
            StringBuilder sb = new StringBuilder();

        }

        // Get the SPARQL result
        TupleQueryResult results = Anno4jRepository.getInstance().sparqlTupleQuery(sparqlQ);
        try {
            List<String> bindingNames;
            try {
                bindingNames = results.getBindingNames();

                while (results.hasNext()) {
                    BindingSet bindingSet = results.next();
                    Value firstValue = bindingSet.getValue(bindingNames.get(0));
                    Integer counter = annotated.get(firstValue.stringValue());
                    // If uri already exists
                    if (counter != null) {
                        counter++;
                        annotated.put(firstValue.stringValue(), counter);
                    } else {
                        // new uri, initialize counter with 1
                        annotated.put(firstValue.stringValue(), 1);
                    }
                }
            } catch (QueryEvaluationException ex) {
                Logger.getLogger(AnnotationsRESTService.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            try {
                results.close();
            } catch (QueryEvaluationException ex) {
                Logger.getLogger(AnnotationsRESTService.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        JSONObject json = new JSONObject(annotated);

        if (givenURIs.isEmpty()) {
            // This is the array of annotations
            String jsonString = json.toString();
            // Return all the annotations of the user
            Response.ResponseBuilder rb = Response.status(200)
                    .entity(jsonString);
            // Set headers for access control, etc.
            setGeneralHeaders(rb, req);
            return rb.build();
        } else {
            // Not found
            String notFound = "Annotations with target " + uris + " were not found\n";
            Response.ResponseBuilder rb = Response.status(404)
                    .entity(notFound);
            // Set headers for access control, etc.
            setGeneralHeaders(rb, req);
            return rb.build();
        }
    }

    /**
     * COLLECTION: Returns json-ld annotations of target with uri Currently no
     * cache control. How can we be sure if things have changed? Even if we now
     * the latest change, we are not sure that some annotation has not been
     * deleted. The url has to be passed as a query attribute, else it is
     * interpreted as a resource of the REST API
     *
     * @param uri (the uri of the target id whose annotations we want to
     * retrieve) This is a query parameter!
     * @param req req The request, used for caching (ETag)
     * @return
     */
    @GET
    @Path("/target/")
    @Produces("application/ld+json")
    public Response getAnnotationsTargetingURL(@QueryParam("uri") String uri, @Context HttpServletRequest req) {
        Anno4jRepository repo = Anno4jRepository.getInstance();
        URI graph = repo.getGraph();

        if (uri == null) {
            String notFound = "Asking of annotations of specific uri with no target uri given!";
            Response.ResponseBuilder rb = Response.status(404)
                    .entity(notFound);
            // Set headers for access control, etc.
            setGeneralHeaders(rb, req);
            return rb.build();
        }

        long t1 = System.currentTimeMillis();
        // Set QueryService
        QueryService queryService = repo.createQueryService(graph);
        List<Annotation> annos = null;
        long t2 = System.currentTimeMillis();
        System.out.println("Create query service: " + (t2 - t1));
        // Ask the repository if we have such an annotations
        try {
            // Get all annotations with this specific annoID
            annos = queryService.addCriteria("oa:hasTarget", uri).execute();
            long t3 = System.currentTimeMillis();
            System.out.println("Query time: " + (t3 - t1));
        } catch (ParseException | RepositoryException | MalformedQueryException | QueryEvaluationException ex) {
            Logger.getLogger(AnnotationsRESTService.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (annos != null && !annos.isEmpty()) {
            // This is the array of annotations
            StringBuilder jsonLDArray = new StringBuilder();
            boolean started = false;
            jsonLDArray.append("[");  // Start the array
            for (Annotation activeAnno : annos) {
                if (started) {
                    jsonLDArray.append(',');
                }
                String jsonld = repo.getAnnotationAsJsonLd(activeAnno);
                //String jsonld = activeAnno.getTriples(RDFFormat.JSONLD);
                jsonLDArray.append(JsonLdHelper.compactJSONLD(jsonld));
                // Every other annotation should have a , in front
                started = true;
            }

            jsonLDArray.append("]");  // End the array
            long t4 = System.currentTimeMillis();
            System.out.println("Annotations to JSONLD: " + (t4 - t1));

            // Return all the annotations of the user
            Response.ResponseBuilder rb = Response.status(200)
                    .entity(jsonLDArray.toString());

            // Set headers for access control, etc.
            setGeneralHeaders(rb, req);
            return rb.build();
        } else {
            // Not found
            String notFound = "Annotations with target " + uri + " were not found\n";
            Response.ResponseBuilder rb = Response.status(404)
                    .entity(notFound);
            // Set headers for access control, etc.
            setGeneralHeaders(rb, req);
            return rb.build();
        }
    }

    /**
     * COLLECTION: Returns json-ld annotations of user with userID and specific
     * target. Currently no cache control. How can we be sure if things have
     * changed? Even if we now the latest change, we are not sure that some
     * annotation has not been deleted. The url has to be passed as a query
     * attribute, else it is interpreted as a resource of the REST API
     *
     * @param uri (the uri of the target id whose annotations we want to
     * retrieve) This is a query parameter!
     * @param userID
     * @param req
     * @return
     */
    @GET
    @Path("/user/{userID}/target")
    @Produces("application/ld+json")
    public Response getAnnotationsOfUserTargetingURL(@PathParam("userID") String userID,
            @QueryParam("uri") String uri, @Context HttpServletRequest req) {
        Anno4jRepository repo = Anno4jRepository.getInstance();
        URI graph = repo.getGraph();

        if (uri == null) {
            String notFound = "Asking of annotations of specific uri with no target uri given!";
            Response.ResponseBuilder rb = Response.status(404)
                    .entity(notFound);
            // Set headers for access control, etc.
            setGeneralHeaders(rb, req);
            return rb.build();
        }

        // Set QueryService
        QueryService queryService = repo.createQueryService(graph);
        List<Annotation> annos = null;

        // Ask the repository if we have such an annotations
        try {
            // Get all annotations with this specific annoID
            annos = queryService.addCriteria("oa:hasTarget", uri).execute();
        } catch (ParseException | RepositoryException | MalformedQueryException | QueryEvaluationException ex) {
            Logger.getLogger(AnnotationsRESTService.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (annos != null && !annos.isEmpty()) {
            // This is the array of annotations
            StringBuilder jsonLDArray = new StringBuilder();
            boolean started = false;
            jsonLDArray.append("[");  // Start the array
            for (Annotation activeAnno : annos) {
                if (started) {
                    jsonLDArray.append(',');
                }
                String jsonld = repo.getAnnotationAsJsonLd(activeAnno);;
                jsonLDArray.append(JsonLdHelper.compactJSONLD(jsonld));
                // Every other annotation should have a , in front
                started = true;
            }

            jsonLDArray.append("]");  // End the array

            // Return all the annotations of the user
            Response.ResponseBuilder rb = Response.status(200)
                    .entity(jsonLDArray.toString());
            // Set headers for access control, etc.
            setGeneralHeaders(rb, req);
            return rb.build();
        } else {
            // Not found
            String notFound = "Annotations with target " + uri + " were not found\n";
            Response.ResponseBuilder rb = Response.status(404)
                    .entity(notFound);
            // Set headers for access control, etc.
            setGeneralHeaders(rb, req);
            return rb.build();
        }
    }

    /**
     * COLLECTION: Returns json-ld annotations with bodies of specific type with
     * typeID. Currently no cache control. How can we be sure if things have
     * changed? Even if we now the latest change, we are not sure that some
     * annotation has not been deleted. The url has to be passed as a query
     * attribute, else it is interpreted as a resource of the REST API
     *
     * @param uriID (the uri of the body id whose annotations we want to
     * retrieve) This is a query parameter!
     * @param req
     * @return
     */
    @GET
    @Path("/type")
    @Produces("application/ld+json")
    public Response getAnnotationsOfBodiesWithURI(
            @QueryParam("uri") String uriID, @Context HttpServletRequest req) {
        Anno4jRepository repo = Anno4jRepository.getInstance();
        URI graph = repo.getGraph();

        if (uriID == null) {
            String notFound = "Asking of annotations of specific type with no type uri given!";
            Response.ResponseBuilder rb = Response.status(404)
                    .entity(notFound);
            // Set headers for access control, etc.
            setGeneralHeaders(rb, req);
            return rb.build();
        }

        // Set QueryService
        QueryService queryService = repo.createQueryService(graph);
        List<Annotation> annos = null;

        // Ask the repository if we have such an annotations
        try {
            // Get all annotations with this specific annoID
            annos = queryService.addCriteria("oa:hasBody/ rdf:type", uriID)
                    .execute();
        } catch (ParseException | RepositoryException | MalformedQueryException | QueryEvaluationException ex) {
            Logger.getLogger(AnnotationsRESTService.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (annos != null && !annos.isEmpty()) {
            // This is the array of annotations
            StringBuilder jsonLDArray = new StringBuilder();
            boolean started = false;
            jsonLDArray.append("[");  // Start the array
            for (Annotation activeAnno : annos) {
                if (started) {
                    jsonLDArray.append(',');
                }
                String jsonld = repo.getAnnotationAsJsonLd(activeAnno);;
                jsonLDArray.append(JsonLdHelper.compactJSONLD(jsonld));
                // Every other annotation should have a , in front
                started = true;
            }

            jsonLDArray.append("]");  // End the array

            // Return all the annotations of the user
            Response.ResponseBuilder rb = Response.status(200)
                    .entity(jsonLDArray.toString());

            // Set headers for access control, etc.
            setGeneralHeaders(rb, req);
            return rb.build();
        } else {
            // Not found
            String notFound = "Annotations of with bodies of  " + uriID + " were not found\n";
            Response.ResponseBuilder rb = Response.status(404)
                    .entity(notFound);
            // Set headers for access control, etc.
            setGeneralHeaders(rb, req);
            return rb.build();

        }
    }

    /**
     * COLLECTION: Returns json-ld annotations of user with userID Currently no
     * cache control. How can we be sure if things have changed? Even if we now
     * the latest change, we are not sure that some annotation has not been
     * deleted. The url has to be passed as a query attribute, else it is
     * interpreted as a resource of the REST API
     *
     * Notice! This does the same as the previous
     * getAnnotationsOfUserTargetingURL Could not make multiple paths with regex
     * work.
     *
     * @param uri (the uri of the target id whose annotations we want to
     * retrieve) This is a query parameter!
     * @param userID
     * @param req
     * @return
     */
    @GET
    @Path("target/user/{userID}")
    @Produces("application/ld+json")
    public Response getAnnotationsTargetingURLOfUser(@PathParam("userID") String userID,
            @QueryParam("uri") String uri, @Context HttpServletRequest req) {
        Anno4jRepository repo = Anno4jRepository.getInstance();
        URI graph = repo.getGraph();

        if (uri == null) {
            String notFound = "Asking of annotations of specific uri with no target uri given!";
            Response.ResponseBuilder rb = Response.status(404)
                    .entity(notFound);
            // Set headers for access control, etc.
            setGeneralHeaders(rb, req);
            return rb.build();
        }

        // Set QueryService
        QueryService queryService = repo.createQueryService(graph);
        List<Annotation> annos = null;

        // Ask the repository if we have such an annotations
        try {
            // Get all annotations with this specific annoID
            annos = queryService.addCriteria("oa:hasTarget", uri)
                    .addCriteria("oa:annotatedBy / foaf:nick", userID)
                    .execute();
        } catch (ParseException | RepositoryException | MalformedQueryException | QueryEvaluationException ex) {
            Logger.getLogger(AnnotationsRESTService.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (annos != null && !annos.isEmpty()) {
            // This is the array of annotations
            StringBuilder jsonLDArray = new StringBuilder();
            boolean started = false;
            jsonLDArray.append("[");  // Start the array
            for (Annotation activeAnno : annos) {
                if (started) {
                    jsonLDArray.append(',');
                }
                String jsonld = repo.getAnnotationAsJsonLd(activeAnno);
                jsonLDArray.append(JsonLdHelper.compactJSONLD(jsonld));
                // Every other annotation should have a , in front
                started = true;
            }

            jsonLDArray.append("]");  // End the array

            // Return all the annotations of the user
            Response.ResponseBuilder rb = Response.status(200)
                    .entity(jsonLDArray.toString());
            // Set headers for access control, etc.
            setGeneralHeaders(rb, req);
            return rb.build();
        } else {
            // Not found
            String notFound = "Annotations of user " + userID + " with target " + uri + " were not found\n";
            Response.ResponseBuilder rb = Response.status(404)
                    .entity(notFound);
            // Set headers for access control, etc.
            setGeneralHeaders(rb, req);
            return rb.build();
        }
    }

    /**
     * Annotation container: create new annotation with specific id The response
     * holds also the id of the annotation
     *
     * This conforms to 4.2.1 of Web Annotation Protocol
     * http://www.w3.org/TR/annotation-protocol/ draft of 2 July 2015.
     *
     * @param annotation (the annotation sent by the client)
     * @param req The request, used for caching (ETag)
     * @param reqJAXRS
     * @return
     */
    @POST
    @Path("/")
    @Consumes("application/ld+json")
    @Produces("application/ld+json")
    public Response createAnnotation(String annotation,
            @Context HttpServletRequest req, @Context Request reqJAXRS) {
        Anno4jRepository repo = Anno4jRepository.getInstance();
        // If the post is not null.
        if (annotation != null) {
            // Parsing is slow.
            //long t1 = System.currentTimeMillis();
            List<Annotation> annos = Anno4jRepository.getInstance().createAnnotationsFromJSONLD(annotation);
            //long t2 = System.currentTimeMillis();
            //System.out.println("Time for parsing: " + (t2 - t1));

            // If annos isEmpty, something went wrong
            // Inform the client
            if (annos.isEmpty()) {
                // Not acceptable input
                Response.ResponseBuilder rb = Response.status(400);
                // Set headers for access control, etc.
                setGeneralHeaders(rb, req);
                return rb.build();
            }

            // Get only the first annotation
            Annotation activeAnno = annos.get(0);

            // If something went wrong, return invalid input
            if (activeAnno == null) {
                // Not acceptable input
                Response.ResponseBuilder rb = Response.status(400);
                // Set headers for access control, etc.
                setGeneralHeaders(rb, req);
                return rb.build();
            }

            // Cache control
            // Calculate the ETag on last modified date of user resource
            EntityTag etag = new EntityTag(activeAnno.getSerializedAt().hashCode() + "");

            Response.ResponseBuilder rb;

            //Verify if it matched with etag available in http request
            rb = reqJAXRS.evaluatePreconditions(etag);

            //Create cache control header
            CacheControl cc = new CacheControl();
            //Set max age to one day
            cc.setMaxAge(86400);

            //If ETag matches the rb will be non-null;
            //Use the rb to return the response without any further processing
            if (rb != null) {
                setGeneralHeaders(rb, req);
                return rb.cacheControl(cc).tag(etag).build();
            }

            // Have to print the annotation so that we are sure all fields of
            // the annotation are loaded.
            String jsonld = repo.getAnnotationAsJsonLd(activeAnno);
            // Return the annotation
            long t1 = System.currentTimeMillis();
            JsonLdHelper.compactJSONLD(jsonld);
            long t2 = System.currentTimeMillis();
            System.out.println("Compacting annotation " + (t2 - t1));
            rb = Response.status(201)
                    .entity(JsonLdHelper.compactJSONLD(jsonld))
                    // Holds the URI of the created annotation, needed by WAP
                    .header("Location", activeAnno.getResourceAsString())
                    .cacheControl(cc).tag(etag);    // ETag used for cache control

            // Set headers for access control, etc.
            setGeneralHeaders(rb, req);

            // Return the response
            return rb.build();
        } else {
            // Not acceptable input
            Response.ResponseBuilder rb = Response.status(400);
            // Set headers for access control, etc.
            setGeneralHeaders(rb, req);

            return rb.build();
        }
    }

    /**
     * STATUS: update annotation with specific id
     *
     * @param annotation
     * @param annoID
     * @param req
     * @return
     */
    @PUT
    @Path("/{annoID}")
    @Consumes("application/ld+json")
    @Produces("application/ld+json")
    public Response updateAnnotationWithID(String annotation, @PathParam("annoID") String annoID,
            @Context HttpServletRequest req) {
        Anno4jRepository repo = Anno4jRepository.getInstance();
        String uriAnno = repo.getPrefixURLOfAnnotations() + annoID;

        Annotation oldAnno = repo.getAnnotation(uriAnno);

        // If the post is not null.
        if (annotation != null) {

            List<Annotation> annos = Anno4jRepository.getInstance().createAnnotationsFromJSONLD(annotation);

            // If annos isEmpty, something went wrong
            // Inform the client
            if (annos.isEmpty()) {
                // Not found
                String notFound = "Annotation with id" + annoID + " was not found\n";
                Response.ResponseBuilder rb = Response.status(204)
                        .entity(notFound);
                // Set headers for access control, etc.
                setGeneralHeaders(rb, req);
                return rb.build();
            }

            // Get only the first annotation
            Annotation activeAnno = annos.get(0);

            // get the JSONLD string representation before deleting
            String jsonld = repo.getAnnotationAsJsonLd(activeAnno);

            // Persist it
            Anno4jRepository.getInstance().update(oldAnno, activeAnno);

            Response.ResponseBuilder rb;
            // Currently returns only the first annotation
            rb = Response.status(200)
                    .entity(JsonLdHelper.compactJSONLD(jsonld))
                    // Holds the URI of the created annotation, needed by WAP
                    .header("Location", activeAnno.getResourceAsString());
            // Set headers for access control, etc.
            setGeneralHeaders(rb, req);
            return rb.build();
        } else {
            // Not found
            String notFound = "Annotation with id" + annoID + " was not found\n";
            Response.ResponseBuilder rb = Response.status(204)
                    .entity(notFound);
            // Set headers for access control, etc.
            setGeneralHeaders(rb, req);
            return rb.build();
        }
    }

    /**
     * STATUS: Delete annotation with specific id
     *
     * @param annoID
     * @param req
     * @return
     */
    @DELETE
    @Path("/{annoID}")
    @Produces("application/ld+json")
    public Response deleteAnnotationWithID(@PathParam("annoID") String annoID,
            @Context HttpServletRequest req) {
        Anno4jRepository repo = Anno4jRepository.getInstance();
        String uriAnno = repo.getPrefixURLOfAnnotations() + annoID;

        Annotation anno = repo.getAnnotation(uriAnno);

        if (anno != null) {
            // get the JSONLD string representation before deleting
            String jsonld = repo.getAnnotationAsJsonLd(anno);

            // Now remove the annotation
            repo.remove(anno);

            Response.ResponseBuilder rb;
            // Currently returns only the first annotation
            rb = Response.status(200)
                    .entity("Successfully removed annotation\n" + JsonLdHelper.compactJSONLD(jsonld))
                    // Holds the URI of the created annotation, needed by WAP
                   .header("Location", anno.getResourceAsString());
            // Set headers for access control, etc.
            setGeneralHeaders(rb, req);

            return rb.build();
        } else {
            // Not found
            String notFound = "Annotation was not found\n";
            Response.ResponseBuilder rb = Response.status(404)
                    .entity(notFound);
            // Set headers for access control, etc.
            setGeneralHeaders(rb, req);

            return rb.build();
        }
    }

    /**
     * Preflight check
     *
     * @param req
     * @return
     */
    @OPTIONS
    @Path("{path : .*}")
    @LocalPreflight
    public Response options(@Context HttpServletRequest req) {
        String referer = req.getHeader("Referer");
        System.out.println("Referer: " + referer);
        System.out.println("Preflight coming from: " + req.getRemoteAddr());
        if (referer != null && referer.startsWith("http://www.lifewatchgreece.eu")) {
            System.out.println("Accepted request from lifewatch greece platform");
            String domain = "http://" + req.getRemoteAddr();
            System.out.println("Accepted origin domain: " + domain);
            return Response.ok()
                    .header(CorsHeaderConstants.HEADER_AC_ALLOW_METHODS, "POST, GET, OPTIONS, HEAD, UPDATE, DELETE")
                    .header(CorsHeaderConstants.HEADER_AC_ALLOW_CREDENTIALS, "true")
                    .header(CorsHeaderConstants.HEADER_AC_ALLOW_ORIGIN, "*")
                    //.header(CorsHeaderConstants.HEADER_AC_ALLOW_ORIGIN, "http://" + req.getRemoteAddr())
                    //.header(CorsHeaderConstants.HEADER_AC_ALLOW_ORIGIN, "http://www.lifewatchgreece.eu")
                    .header(CorsHeaderConstants.HEADER_AC_ALLOW_HEADERS, "origin, content-type, accept")
                    .build();
        } else if (req.getRemoteAddr().startsWith("139.91")) {
            String domain = "http://" + req.getRemoteAddr();
            System.out.println("Accepted origin domain: " + domain);
            return Response.ok()
                    .header(CorsHeaderConstants.HEADER_AC_ALLOW_METHODS, "POST, GET, OPTIONS, HEAD, UPDATE, DELETE")
                    .header(CorsHeaderConstants.HEADER_AC_ALLOW_CREDENTIALS, "true")
                    .header(CorsHeaderConstants.HEADER_AC_ALLOW_ORIGIN, "*")
                    //.header(CorsHeaderConstants.HEADER_AC_ALLOW_ORIGIN, "http://" + req.getRemoteAddr())
                    .header(CorsHeaderConstants.HEADER_AC_ALLOW_HEADERS, "origin, content-type, accept")
                    .build();
        } else {
            System.out.println("Accepted origin domain: ALL");
            return Response.ok()
                    .header(CorsHeaderConstants.HEADER_AC_ALLOW_METHODS, "POST, GET, OPTIONS, HEAD, UPDATE, DELETE")
                    .header(CorsHeaderConstants.HEADER_AC_ALLOW_CREDENTIALS, "true")
                    .header(CorsHeaderConstants.HEADER_AC_ALLOW_ORIGIN, "*")
                    .header(CorsHeaderConstants.HEADER_AC_ALLOW_HEADERS, "origin, content-type, accept")
                    .build();
        }/* else {
            // When we have access control, everything will be ok
            System.out.println("Not Accepted");
            return Response.ok().build();
        }*/
    }
    /**
     *
     */
    private void setGeneralHeaders(Response.ResponseBuilder rb, HttpServletRequest req) {
        // Holds the Link, RDF source according to LDP
        rb.link("http://www.w3.org/ns/ldp#Resource", "type")
                .link("http://www.w3.org/ns/oa#Annotation", "type")
                .header("Vary:", "Accept")
                // Access control for service
                // Allow only requests from http://www.lifewatchgreece.eu
                .header(CorsHeaderConstants.HEADER_AC_ALLOW_METHODS, "POST, GET, OPTIONS, HEAD, UPDATE, DELETE")
                .header(CorsHeaderConstants.HEADER_AC_ALLOW_HEADERS, "origin, content-type, accept")
                .header(CorsHeaderConstants.HEADER_AC_ALLOW_CREDENTIALS, "true");

        // Check where the request comes from and add appropriate origin
        // If request coming from lifewatchgreece
        String referer = req.getHeader("Referer");
        if (referer != null && referer.startsWith("http://www.lifewatchgreece.eu")) {
            // For now just allow anything
            rb.header(CorsHeaderConstants.HEADER_AC_ALLOW_ORIGIN, "*");
            //rb.header(CorsHeaderConstants.HEADER_AC_ALLOW_ORIGIN, "http://www.lifewatchgreece.eu");
            //rb.header(CorsHeaderConstants.HEADER_AC_ALLOW_ORIGIN, "http://" + req.getRemoteAddr());
        } else if (req.getRemoteAddr().startsWith("139.91")) {
            // For now just allow anything
            rb.header(CorsHeaderConstants.HEADER_AC_ALLOW_ORIGIN, "*");
            // Request coming from ICS-FORTH
            //rb.header(CorsHeaderConstants.HEADER_AC_ALLOW_ORIGIN, "http://" + req.getRemoteAddr());
        } else {
            // For now just allow anything
            rb.header(CorsHeaderConstants.HEADER_AC_ALLOW_ORIGIN, "*");
        }
    }
}
