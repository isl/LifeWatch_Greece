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
 * Parental care blood: Any parental trait that enhances the fitness of a
 * parent’s offspring after the offspring is released from the female body
 * [1317]. Viviparity and other forms of lecitotrophy are excluded here from
 * this definition and not considered as forms of parental care.
 */
@Iri(POLYTRAITS.GO_0060746)
public abstract class GO_0060746Body implements PolytraitsBody {

    /**
     * Check that the annotation has a valid value
     *
     * @return
     */
    @Override
    public boolean isValid() {
        switch (getValue()) {
            /* yes:“Umbrella term”. Used to capture information that a species provides parental care to its offspring.*/
            case POLYTRAITS.GO_0060746_BP_YES:
            /*  no: Used to capture information that a species does not provide parental care to its offspring beyond
                supplying them with a small package of yolk that serves as an initial source of nutrition until the
                offspring are fully capable of fending for themselves [1317]. */
            case POLYTRAITS.GO_0060746_BP_NO:
                return true;
            default:
                return false;
        }
    }

    @Override
    public String toString() {
        return "GO_0060746Body{"
                + "value='" + getValue() + '\''
                + "}";
    }
}
