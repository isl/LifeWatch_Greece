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
import com.github.anno4j.model.namespaces.DC;
import com.github.anno4j.model.namespaces.DCTYPES;
import com.github.anno4j.model.namespaces.RDF;
import org.openrdf.annotations.Iri;

/**
 * Allows annotations with a simple text
 *
 * @author papadako
 */
@Iri(DCTYPES.TEXT)
public abstract class TextAreaBody implements Body {

    @Iri(RDF.VALUE)
    public abstract String getValue();

    @Iri(RDF.VALUE)
    public abstract void setValue(String value);

    @Iri(DC.FORMAT)
    public abstract String getFormat();

    @Iri(DC.FORMAT)
    public abstract void setFormat(String format);

    @Iri(DC.LANGUAGE)
    public abstract String getLanguage();

    @Iri(DC.LANGUAGE)
    public abstract void setLanguage(String language);

    @Override
    public String toString() {
        return "TextAreaBody{"
                + "value='" + getValue() + "\'\n"
                + "language='" + getLanguage() + "\'\n"
                + "format='" + getFormat() + "\'\n"
                + "}";
    }

    /**
     * Method that checks if this annotation body is valid.
     *
     * @return
     */
    public boolean isValid() {
        boolean valid = false;
        if (getValue() != null) {
            valid = true;
        }
        // If we do not call the following they are not instantiated...
        this.getFormat();
        this.getLanguage();

        return valid;
    }
}
