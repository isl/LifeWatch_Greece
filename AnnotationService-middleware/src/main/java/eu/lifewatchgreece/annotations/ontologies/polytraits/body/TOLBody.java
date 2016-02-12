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
 * Tolerance (AMBI index): The sensitivity of an organism to organic enrichment,
 * classified through the AMBI index [41].
 */
@Iri(POLYTRAITS.TOL)
public abstract class TOLBody implements PolytraitsBody {

    /**
     * Check that the annotation has a valid value
     *
     * @return
     */
    @Override
    public boolean isValid() {
        switch (getValue()) {
            /* group I : Species very sensitive to organic enrichment and present
            under unpolluted conditions (initial state).*/
            case POLYTRAITS.TOL_I:
            /* group II : Species indifferent to enrichment, always present in
                low densities with non-significant variations with time  (from initial state to slightly unbalanced condition).*/
            case POLYTRAITS.TOL_II:
            /* group III : Species tolerant to excess organic matter enrichment.
                These species may occur under normal conditions, but their populations
                are stimulated by organic enrichment (slightly unbalanced condition).*/
            case POLYTRAITS.TOL_III:
            /* group IV : Second-order opportunistic species (slightly to pronouncedly unbalanced condition).*/
            case POLYTRAITS.TOL_IV:
            /* group V : First-order opportunistic species (pronouncedly unbalanced condition).*/
            case POLYTRAITS.TOL_V:
                return true;
            default:
                return false;
        }
    }

    @Override
    public String toString() {
        return "TOLBody{"
                + "value='" + getValue() + '\''
                + "}";
    }
}
