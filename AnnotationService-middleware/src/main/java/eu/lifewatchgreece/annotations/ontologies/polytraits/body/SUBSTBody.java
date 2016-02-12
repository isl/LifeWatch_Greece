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
 * Substrate type: The surface on which an organism lives. The substrate may
 * simply provide structural support, or may provide nutrients [1292]. .
 */
@Iri(POLYTRAITS.SUBST)
public abstract class SUBSTBody implements PolytraitsBody {

    /**
     * Check that the annotation has a valid value
     *
     * @return
     */
    @Override
    public boolean isValid() {
        switch (getValue()) {
            /* bedrock: Any stable hard substratum not separated into boulders or smaller sediment units.*/
            case POLYTRAITS.SUBST_ROCK:
            /* large to very large boulders: Unattached rock, of large (512 - 1024 mm) or very large (>1024 mm) size [1292].*/
            case POLYTRAITS.SUBST_LB:
            /* small boulders: Unattached rock, of small (256 - 512 mm) size [1292].*/
            case POLYTRAITS.SUBST_SB:
            /* cobbles: Sediment characterised by an average particle diameter between 64 and 256 mm.*/
            case POLYTRAITS.SUBST_ENVO_01000115:
            /* pebbles: Sediment characterised by an average particle diameter between 4 and 64 mm.*/
            case POLYTRAITS.SUBST_ENVO_01000116:
            /* gravel: An environmental material which is composed of pieces of rock that are at least two
                millimeters (2 mm) in its largest dimension and no more than 75 millimeters.*/
            case POLYTRAITS.SUBST_ENVO_01000018_SUBST:
            /* gravel: 50 - 80% gravel; 20 - 50% sand.*/
            case POLYTRAITS.SUBST_SG:
            /* muddy gravel: 50 - 80% gravel; 20 - 50% mud.*/
            case POLYTRAITS.SUBST_MG:
            /* muddy sandy gravel: 50 - 80% gravel; 20 - 50% mud and sand.*/
            case POLYTRAITS.SUBST_MSG:
            /* coarse clean sand: Sediment particles diameter between 0.5 - 4 mm; the sand fraction is >80%.*/
            case POLYTRAITS.SUBST_CS:
            /* fine clean sand: Sediment particles diameter between 0.063 - 0.5 mm; the sand fraction is >80%.*/
            case POLYTRAITS.SUBST_FS:
            /* gravelly sand:50 - 80% sand; 20 - 80% gravel.*/
            case POLYTRAITS.SUBST_GS:
            /* muddy gravelly sand:50 - 80% sand; 20 -50% mud and sand.*/
            case POLYTRAITS.SUBST_MGS:
            /* muddy sand: 50 - 80% sand; 20 -50% mud.*/
            case POLYTRAITS.SUBST_MS:
            /* sandy mud: 50 - 80% mud; 20 -50% sand.*/
            case POLYTRAITS.SUBST_SM:
            /* sandy gravely mud: 50 - 80% mud; 20 - 50% sand and gravel.*/
            case POLYTRAITS.SUBST_SGM:
            /* gravelly mud: 50 - 80% mud; 20 -50% gravel.*/
            case POLYTRAITS.SUBST_GM:
            /* mud: Fine particles of silt and/or clay <0.063 mm; the silt/clay fraction is > 80% [1292].*/
            case POLYTRAITS.SUBST_MUD_SUBST:
            /* silt: Sediment characterised by an average particle diameter between 3.9 and 63 micrometers.*/
            case POLYTRAITS.SUBST_ENVO_01000119:
            /* clay: Sediment characterised by an average particle diameter between 1 and 3.9 micrometers.*/
            case POLYTRAITS.SUBST_ENVO_01000120:
            /* mixed:Mixtures of a variety of sediment types composed of pebble/ gravel/ sand/ mud.
               This category includes muddy gravels, muddy sandy gravels, gravelly muds, and muddy gravelly sands.*/
            case POLYTRAITS.SUBST_SUBST_MIX:
            /* artificial:E.g. wood, metal or concrete structures.*/
            case POLYTRAITS.SUBST_ART:
                return true;
            default:
                return false;
        }
    }

    @Override
    public String toString() {
        return "SUBSTBody{"
                + "value='" + getValue() + '\''
                + "}";
    }
}
