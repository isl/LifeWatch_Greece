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
package eu.lifewatchgreece.annotations.ontologies.polytraits.body;

import com.github.anno4j.model.Body;
import com.github.anno4j.model.namespaces.RDF;
import org.openrdf.annotations.Iri;

/**
 * Abstract class that provides the isValid method
 *
 * @author papadako
 */
public interface PolytraitsBody extends Body {

    public boolean isValid();

    /**
     * Sets the modality of the trait described by the body
     *
     * @param value The modality of the trait
     */
    @Iri(RDF.VALUE)
    public abstract void setValue(String value);

    /**
     * Gets the modality of the trait described by the body
     *
     * @return The modality of the trait
     */
    @Iri(RDF.VALUE)
    public abstract String getValue();

}
