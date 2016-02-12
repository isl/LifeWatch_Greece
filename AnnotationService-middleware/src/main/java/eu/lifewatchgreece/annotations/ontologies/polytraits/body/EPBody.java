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
 * Environmental position: Living location of the organism in relation to the
 * water column, the sea floor and other organisms.
 */
@Iri(POLYTRAITS.EP)
public abstract class EPBody implements PolytraitsBody {

    /**
     * Check that the annotation has a valid value
     *
     * @return
     */
    @Override
    public boolean isValid() {
        switch (getValue()) {
            /* Epibenthic: Living on the surface of the seabed.*/
            case POLYTRAITS.EP_EPIB:
            /* Endobenthic: Living within the sediment of the seabed but not in interstitial space.*/
            case POLYTRAITS.EP_ENDOB:
            /* Interstitial: Living in the spaces between grains in a sediment.*/
            case POLYTRAITS.EP_IST:
            /* Hyperbenthic: Living above but close to the substratum. */
            case POLYTRAITS.EP_HYP:
            /* Pelagic: Inhabiting the open sea, excluding the sea floor.*/
            case POLYTRAITS.EP_ENVO_01000023:
            /* Epilithic: Attached to / growing on the surface of rock.*/
            case POLYTRAITS.EP_EPIL:
            /* Endolithic: Organism that colonize the interior of any kind of rock but not actively boring into it.*/
            case POLYTRAITS.EP_EL:
            /* Lithotomous: Actively boring into rocks and living in these burrows.*/
            case POLYTRAITS.EP_LITH:
            /* Boring in biogenic substrate : Boring in biogenic hard substrate and living in these burrows.*/
            case POLYTRAITS.EP_BB:
            /* Epizoic : Growing or living on or in a living animal (but not parasitic upon it).*/
            case POLYTRAITS.EP_EPIZ:
            /* Epiphytic: Growing on or in a living plant (but not parasitic upon it).*/
            case POLYTRAITS.EP_EPIP:
            /* Parasitic: Living in or on another organism at the expense of this host.*/
            case POLYTRAITS.EP_PAR:

                return true;
            default:
                return false;
        }
    }

    @Override
    public String toString() {
        return "EPBody{"
                + "value='" + getValue() + '\''
                + "}";
    }
}
