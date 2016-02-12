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
 * Intra- and interspecific competition: The simultaneous demand by two or more
 * organisms or populations or species for an essential common resource that is
 * actually or potentially in limited supply or the detrimental interaction
 * between two or more organisms or species seeking a common resource that is
 * not limited
 */
@Iri(POLYTRAITS.GO_0044402)
public abstract class GO_0044402Body implements PolytraitsBody {

    /**
     * Check that the annotation has a valid value
     *
     * @return
     */
    @Override
    public boolean isValid() {
        switch (getValue()) {
            /*  annelida (adults): Competition with crustaceans that are in adult stage.*/
            case POLYTRAITS.GO_0044402_COMP_AA:
            /* crustacea (adults): */
            case POLYTRAITS.GO_0044402_COMP_CA:
            /*  annelida (larvea): Competition with other annelids that are in larval stage.
                The interaction can be between different organisms, populations or species.*/
            case POLYTRAITS.GO_0044402_COMP_AL:
            /* crustacea (larvea): Competition with crustaceans that are in larval stage.*/
            case POLYTRAITS.GO_0044402_COMP_CL:
            /* mollusca: Competition with mollusks.*/
            case POLYTRAITS.GO_0044402_COMP_MOLL:
                return true;
            default:
                return false;
        }
    }

    @Override
    public String toString() {
        return "GO_0044402Body{"
                + "value='" + getValue() + '\''
                + "}";
    }
}
