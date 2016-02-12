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
 * Egg size: Diameter of the eggs spawned by an organism.
 */
@Iri(POLYTRAITS.EGG)
public abstract class EGGBody implements PolytraitsBody {

    /**
     * Check that the annotation has a valid value
     *
     * @return
     */
    @Override
    public boolean isValid() {
        switch (getValue()) {
            /* 0um-100um: Egg diameter up to 100um.*/
            case POLYTRAITS.EGG_S:
            /* 100um-200um: Egg diameter from 100um to 200um.*/
            case POLYTRAITS.EGG_M:
            /* > 200um: Egg diameter larger than 200um.*/
            case POLYTRAITS.EGG_L:
                return true;
            default:
                return false;
        }
    }

    @Override
    public String toString() {
        return "EGGBody{"
                + "value='" + getValue() + '\''
                + "}";
    }
}
