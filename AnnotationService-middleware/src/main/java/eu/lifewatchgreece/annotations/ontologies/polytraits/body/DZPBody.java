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
 * Depth zonation (pelagic): The depth at which an organism occurs in the water
 * column. Usually defined based on ecological features of the zonation.
 */
@Iri(POLYTRAITS.DZP)
public abstract class DZPBody implements PolytraitsBody {

    /**
     * Check that the annotation has a valid value
     *
     * @return
     */
    @Override
    public boolean isValid() {
        switch (getValue()) {
            /* epipelagic zone: The zone of an ocean from the surface to 200m where photosynthesis can occur,
            due to the penetration of light. */
            case POLYTRAITS.DZP_ENVO_00000209:
            /* mesopelagic zone: Water column from the upper aphotic zone (ca. 200 m) to a depth of ca. 100 m */
            case POLYTRAITS.DZP_MESO:
            /* bathypelagic: Water column from ca. 1000 m to a depth of ca. 2500 m */
            case POLYTRAITS.DZP_BATH:
            /* abyssopelagic:  	The zone of the ocean below the bathypelagic zone, with its lowest boundary at about 6000 m. */
            case POLYTRAITS.DZP_ENVO_00000212:
            /* hadalpelagic zone:  The zone of an ocean in oceanic trenches, lying between 6000 m and 10000 m. */
            case POLYTRAITS.DZP_ENVO_00000214:
                return true;
            default:
                return false;
        }
    }

    @Override
    public String toString() {
        return "DZPBody{"
                + "value='" + getValue() + '\''
                + "}";
    }
}
