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
 * Depth zonation (benthos): The depth at which an organism occurs in the water
 * column. Commonly defined based on ecological features of the zonation.
 */
@Iri(POLYTRAITS.DZ)
public abstract class DZBody implements PolytraitsBody {

    /**
     * Check that the annotation has a valid value
     *
     * @return
     */
    @Override
    public boolean isValid() {
        switch (getValue()) {
            /* supralitoral zone: The zone of the shore immediately above the highest water leveland
            subjected to wetting by spray or wave splash */
            case POLYTRAITS.DZ_SUP:
            /* litoral zone: The area of the foreshore and seabed that is exposed to the air
                at low tide and submerged at high tide, i.e., the area between tide marks. */
            case POLYTRAITS.DZ_ENVO_00000316:
            /* sublitoral zone: The zone of the shore immediately below the lowest water level and the
                edge of the continental shelf (ca. 200 m). */
            case POLYTRAITS.DZ_SUB:
            /* bathyal zone: The steep descent zone from 200 m to 4000 m depth. */
            case POLYTRAITS.DZ_BAT:
            /* hadal zone: The sea floor deeper than 6000 m, such as that of the oceanic trenches. */
            case POLYTRAITS.DZ_ENVO_01000028:
                return true;
            default:
                return false;
        }
    }

    @Override
    public String toString() {
        return "DZBody{"
                + "value='" + getValue() + '\''
                + "}";
    }
}
