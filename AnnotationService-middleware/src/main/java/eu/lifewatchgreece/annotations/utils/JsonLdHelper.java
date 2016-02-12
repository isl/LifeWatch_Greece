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
package eu.lifewatchgreece.annotations.utils;

import com.github.jsonldjava.core.JsonLdError;
import com.github.jsonldjava.core.JsonLdOptions;
import com.github.jsonldjava.core.JsonLdProcessor;
import com.github.jsonldjava.utils.JsonUtils;
import eu.lifewatchgreece.annotations.controllers.REST.AnnotationsRESTService;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author papadako
 */
public class JsonLdHelper {

    private static Map context = initContext();
    private static String frame = "{\n"
            + "\"@context\": {\n"
            + "    \"oa\" :     \"http://www.w3.org/ns/oa#\",\n"
            + "    \"dc\" :     \"http://purl.org/dc/elements/1.1/\",\n"
            + "    \"dcterms\": \"http://purl.org/dc/terms/\",\n"
            + "    \"dctypes\": \"http://purl.org/dc/dcmitype/\",\n"
            + "    \"foaf\" :   \"http://xmlns.com/foaf/0.1/\",\n"
            + "    \"rdf\" :    \"http://www.w3.org/1999/02/22-rdf-syntax-ns#\",\n"
            + "    \"rdfs\" :   \"http://www.w3.org/2000/01/rdf-schema#\",\n"
            + "    \"skos\" :   \"http://www.w3.org/2004/02/skos/core#\",\n"
            + "    \"umbelrc\": \"http://umbel.org/umbel/rc/\",\n"
            + "\n"
            + "    \"body\" :         {\"@id\" : \"oa:hasBody\"},\n"
            + "    \"target\" :       {\"@type\":\"@id\", \"@id\" : \"oa:hasTarget\"},\n"
            + "    \"source\" :       {\"@type\":\"@id\", \"@id\" : \"oa:hasSource\"},\n"
            + "    \"selector\" :     {\"@type\":\"@id\", \"@id\" : \"oa:hasSelector\"},\n"
            + "    \"state\" :        {\"@type\":\"@id\", \"@id\" : \"oa:hasState\"},\n"
            + "    \"scope\" :        {\"@type\":\"@id\", \"@id\" : \"oa:hasScope\"},\n"
            + "    \"annotatedBy\" :  {\"@type\":\"@id\", \"@id\" : \"oa:annotatedBy\"},\n"
            + "    \"serializedBy\" : {\"@type\":\"@id\", \"@id\" : \"oa:serializedBy\"},\n"
            + "    \"motivation\" :   {\"@type\":\"@id\", \"@id\" : \"oa:motivatedBy\"},\n"
            + "    \"stylesheet\" :   {\"@type\":\"@id\", \"@id\" : \"oa:styledBy\"},\n"
            + "    \"cached\" :       {\"@type\":\"@id\", \"@id\" : \"oa:cachedSource\"},\n"
            + "    \"conformsTo\" :   {\"@type\":\"@id\", \"@id\" : \"dcterms:conformsTo\"},\n"
            + "    \"members\" :      {\"@type\":\"@id\", \"@id\" : \"oa:membershipList\", \"@container\": \"@list\"},\n"
            + "    \"item\" :         {\"@type\":\"@id\", \"@id\" : \"oa:item\"},\n"
            + "    \"related\" :      {\"@type\":\"@id\", \"@id\" : \"skos:related\"},\n"
            + "\n"
            + "    \"format\" :       \"dc:format\",\n"
            + "    \"language\":      \"dc:language\",\n"
            + "    \"annotatedAt\" :  \"oa:annotatedAt\",\n"
            + "    \"serializedAt\" : \"oa:serializedAt\",\n"
            + "    \"when\" :         \"oa:when\",\n"
            + "    \"value\" :        \"rdf:value\",\n"
            + "    \"start\" :        \"oa:start\",\n"
            + "    \"end\" :          \"oa:end\",\n"
            + "    \"exact\" :        \"oa:exact\",\n"
            + "    \"prefix\" :       \"oa:prefix\",\n"
            + "    \"suffix\" :       \"oa:suffix\",\n"
            + "    \"label\" :        \"rdfs:label\",\n"
            + "    \"name\" :         \"foaf:name\",\n"
            + "    \"mbox\" :         \"foaf:mbox\",\n"
            + "    \"nick\" :         \"foaf:nick\",\n"
            + "    \"styleClass\" :   \"oa:styleClass\"\n"
            + "  },\n"
            + "  \"@graph\": {\n"
            + "    \"http://www.w3.org/ns/oa#annotatedAt\": {},\n"
            + "    \"http://www.w3.org/ns/oa#annotatedBy\": {},\n"
            + "    \"http://www.w3.org/ns/oa#hasBody\": {},\n"
            + "    \"http://www.w3.org/ns/oa#hasTarget\": {},\n"
            + "    \"http://www.w3.org/ns/oa#motivatedBy\": {},\n"
            + "    \"http://www.w3.org/ns/oa#serializedAt\":{},\n"
            + "    \"http://www.w3.org/ns/oa#serializedBy\": {}\n"
            + "  }\n"
            + "}";
    private static Object frameObject = initFrame();

    /**
     * Method that returns a framed jsonld version
     *
     * @param jsonld
     * @return
     */
    public static String compactJSONLD(String jsonld) {
        String jsonldTrans = "";
        try {
            Object jsonObject = JsonUtils.fromString(jsonld);

            // Create an instance of JsonLdOptions with the standard JSON-LD options
            JsonLdOptions options = new JsonLdOptions();
            try {
                Object framed = JsonLdProcessor.frame(jsonObject, frameObject, options);
                //jsonldTrans = JsonUtils.toPrettyString(JsonLdProcessor.compact(framed, context, options));
                jsonldTrans = JsonUtils.toPrettyString(framed);
            } catch (JsonLdError ex) {
                Logger.getLogger(AnnotationsRESTService.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (IOException ex) {
            Logger.getLogger(AnnotationsRESTService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return jsonldTrans;
    }

    /**
     * Init context and frame for JSONLD only once
     *
     * @param context
     */
    private static Map initContext() {
        // Create a context JSON map containing prefixes and definitions
        context = new HashMap();
        context.put("oa", "http://www.w3.org/ns/oa#");
        context.put("dc", "http://purl.org/dc/elements/1.1/");
        context.put("dcterms", "http://purl.org/dc/terms/");
        context.put("dctypes", "http://purl.org/dc/dcmitype/");
        context.put("foaf", "http://xmlns.com/foaf/0.1/");
        context.put("rdf", "http://www.w3.org/1999/02/22-rdf-syntax-ns#");
        context.put("rdfs", "http://www.w3.org/2000/01/rdf-schema#");
        context.put("skos", "http://www.w3.org/2004/02/skos/core#");
        context.put("body", "{\"@id\" : \"oa:hasBody\"}");
        context.put("target", "{\"@type\":\"@id\", \"@id\" : \"oa:hasTarget\"}");
        context.put("source", "{\"@type\":\"@id\", \"@id\" : \"oa:hasSource\"}");
        context.put("selector", "{\"@type\":\"@id\", \"@id\" : \"oa:hasSelector\"}");
        context.put("state", "{\"@type\":\"@id\", \"@id\" : \"oa:hasState\"}");
        context.put("scope", "{\"@type\":\"@id\", \"@id\" : \"oa:hasScope\"}");
        context.put("annotatedBy", "{\"@type\":\"@id\", \"@id\" : \"oa:annotatedBy\"}");
        context.put("serializedBy", "{\"@type\":\"@id\", \"@id\" : \"oa:serializedBy\"}");
        context.put("motivation", "{\"@type\":\"@id\", \"@id\" : \"oa:motivatedBy\"}");
        context.put("stylesheet", "{\"@type\":\"@id\", \"@id\" : \"oa:styledBy\"}");
        context.put("cached", "{\"@type\":\"@id\", \"@id\" : \"oa:cachedSource\"}");
        context.put("conformsTo", "{\"@type\":\"@id\", \"@id\" : \"dcterms:conformsTo\"}");
        context.put("members", "{\"@type\":\"@id\", \"@id\" : \"oa:membershipList\", \"@container\": \"@list\"}");
        context.put("item", "{\"@type\":\"@id\", \"@id\" : \"oa:item\"}");
        context.put("related", "{\"@type\":\"@id\", \"@id\" : \"skos:related\"}");
        context.put("format", "dc:format");
        context.put("language", "dc:language");
        context.put("annotatedAt", "oa:annotatedAt");
        context.put("serializedAt", "oa:serializedAt");
        context.put("when", "oa:when");
        context.put("value", "rdf:value");
        context.put("start", "oa:start");
        context.put("end", "oa:end");
        context.put("exact", "oa:exact");
        context.put("prefix", "oa:prefix");
        context.put("suffix", "oa:suffix");
        context.put("label", "rdfs:label");
        context.put("name", "foaf:name");
        context.put("mbox", "foaf:mbox");
        context.put("nick", "foaf:nick");
        context.put("styleClass", "oa:styleClass");

        return context;
    }

    private static Object initFrame() {
        try {
            return JsonUtils.fromString(frame);
        } catch (IOException ex) {
            Logger.getLogger(JsonLdHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
