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
 * Mobility of adult: The capability of an organism to move spontaneously and
 * freely
 */
@Iri(POLYTRAITS.GO_0040011)
public abstract class GO_0040011Body implements PolytraitsBody {

    /**
     * Check that the annotation has a valid value
     *
     * @return
     */
    @Override
    public boolean isValid() {
        switch (getValue()) {
            /* crawler: An organism that moves along on the substratum via
            movements of its legs, appendages (e.g. parapodia and chaetae) or muscles [1292].*/
            case POLYTRAITS.GO_0040011_MOB_CRAWL:
            /* burrower: An organism that lives or moves in a burrow in soft sediments.*/
            case POLYTRAITS.GO_0040011_MOB_BUR:
            /* swimmer: An organism that moves through the water column via movements of its fins,
                legs or appendages, via undulatory movements of the body or via jet propulsion;
                includes pelagic phases during reproduction (swarming at the surface) [1292].*/
            case POLYTRAITS.GO_0040011_MOB_SWIM:
            /* non-motile / semi-motile: Permanently attached to a substratum (non-motile) or
                capable of movement across (or through) it (semi-motile).*/
            case POLYTRAITS.GO_0040011_MOB_SESS:
                return true;
            default:
                return false;
        }
    }

    @Override
    public String toString() {
        return "GO_0040011Body{"
                + "value='" + getValue() + '\''
                + "}";
    }
}
