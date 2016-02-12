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
 * Location of parental care: Defines the location of the parental care (if
 * provided), either near the body of the parent or at a distance from it.
 */
@Iri(POLYTRAITS.PC)
public abstract class PCBody implements PolytraitsBody {

    /**
     * Check that the annotation has a valid value
     *
     * @return
     */
    @Override
    public boolean isValid() {
        switch (getValue()) {
            /* outside microenvironment of the parent : Parental care is provided through e.g. protective structures,
            but not on the body of the parent or in its immediate living environment (e.g. in a burrow, tube or nest).*/
            case POLYTRAITS.PC_FAR:
            /* within microenvironment of the parent : Parental care is provided either on the body of the parent or in
                its immediate living environment (e.g. in a burrow, tube or nest). */
            case POLYTRAITS.PC_NEAR:
                return true;
            default:
                return false;
        }
    }

    @Override
    public String toString() {
        return "PCBody{"
                + "value='" + getValue() + '\''
                + "}";
    }
}
