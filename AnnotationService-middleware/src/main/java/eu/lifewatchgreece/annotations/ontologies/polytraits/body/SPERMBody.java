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

import eu.lifewatchgreece.annotations.ontologies.polytraits.namespace.POLYTRAITS;
import org.openrdf.annotations.Iri;

/**
 * Sperm type: Different types of sperm that occur in organisms and fertilize
 * the eggs.
 */
@Iri(POLYTRAITS.SPERM)
public abstract class SPERMBody implements PolytraitsBody {

    /**
     * Check that the annotation has a valid value
     *
     * @return
     */
    @Override
    public boolean isValid() {
        switch (getValue()) {
            /* ect-aquasperm: Type of sperm that are released into the water and fertilize similarly released eggs [400].*/
            case POLYTRAITS.SPERM_ECT:
            /* ent-aquasperm: Type of sperm that are released freely into the ambient water but differ
                from ect-aquasperm in being gathered by, or in some other way reaching, the female [400]. */
            case POLYTRAITS.SPERM_ENT:
            /* introspect: Have no contact with water when passed from male to female [400]. */
            case POLYTRAITS.SPERM_INTRO:
                return true;
            default:
                return false;
        }
    }

    @Override
    public String toString() {
        return "SPERMBody{"
                + "value='" + getValue() + '\''
                + "}";
    }
}
