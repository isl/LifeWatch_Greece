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

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.openrdf.model.URI;
import org.openrdf.model.impl.URIImpl;


public final class EndPointProperties {

    private String _endPointQuery = null;
    private String _endPointUpdate = null;
    private URI _graphName = null;

    public EndPointProperties() {
        _endPointQuery = "http://localhost:9999/bigdata/sparql";
        _endPointUpdate = "http://localhost:9999/bigdata/sparql";
        _graphName = new URIImpl("http://LifeWatchAnnotationGraph");

    }

    public EndPointProperties(InputStream aPropertiesFile) throws IOException {
        // Try to load the properties using the propertiesFile
        Properties properties = new Properties();
        properties.load(aPropertiesFile);
        setEndPointQuery(properties.getProperty("endPointQuery"));
        setEndPointUpdate(properties.getProperty("endPointUpdate"));
        setGraphName(new URIImpl(properties.getProperty("graphName")));
    }

    public String getEndPointQuery() {
        return _endPointQuery;
    }

    public String getEndPointUpdate() {
        return _endPointUpdate;
    }

    public URI getGraph() {
        return _graphName;
    }

    public void setEndPointQuery(String endPointQuery) {
        this._endPointQuery = endPointQuery;
    }

    public void setEndPointUpdate(String endPointUpdate) {
        this._endPointUpdate = endPointUpdate;
    }

    public void setGraphName(URI graphName) {
        this._graphName = graphName;
    }
}
