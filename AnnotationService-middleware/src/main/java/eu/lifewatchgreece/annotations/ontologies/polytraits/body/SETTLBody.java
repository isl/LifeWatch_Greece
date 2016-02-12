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
 * Substrate type of settlement: Settlement is defined as the behavioural
 * performance when pelagic larvae descend from the plankton to the benthos, and
 * move upon the substratum with or without attaching to it. Settlement is
 * reversible: a larva can swim up again from the substrate to resettle at
 * another location [1298]. The surface on which larvae choose to settle is
 * defined as the substrate of settlement.
 */
@Iri(POLYTRAITS.SETTL)
public abstract class SETTLBody implements PolytraitsBody {

    /**
     * Check that the annotation has a valid value
     *
     * @return
     */
    @Override
    public boolean isValid() {
        switch (getValue()) {
            /* hard substrate: One or more successive, free-living larval stages intervene
            between embryo and adult, with a more-or-less abrupt transition, or metamorphosis,
            between the last larval stage and the adult [1316].*/
            case POLYTRAITS.SETTL_HARD:
            /* sand:Particles defined in three size categories: very coarse sand and granules (1 - 4 mm);
                medium and coarse sand (0.25 - 1 mm); very fine and fine sand (0.063 - 0.25 mm) [1292].*/
            case POLYTRAITS.SETTL_SAND:
            /* mud: Fine particles of silt and/or clay <0.063 mm; the silt/clay fraction is > 80% [1292].*/
            case POLYTRAITS.SETTL_MUD:
            /* clay: Sediment characterised by an average particle diameter between 1 and 3.9 micrometers.*/
            case POLYTRAITS.SETTL_ENVO_01000120:
            /* silt: Sediment characterised by an average particle diameter between 3.9 and 63 micrometers.*/
            case POLYTRAITS.SETTL_ENVO_01000119:
            /* gravel: An environmental material which is composed of pieces of rock that are at least two millimeters
                (2 mm) in its largest dimension and no more than 75 millimeters.*/
            case POLYTRAITS.SETTL_ENVO_01000018:
            /* pebbles: Sediment characterised by an average particle diameter between 4 and 64 mm.*/
            case POLYTRAITS.SETTL_ENVO_01000116:
            /* cobbles: Sediment characterised by an average particle diameter between 64 and 256 mm.*/
            case POLYTRAITS.SETTL_ENVO_01000115:
            /* boulders: Sediment characterised by an average particle diameter greater than 256 mm*/
            case POLYTRAITS.SETTL_ENVO_01000114:
            /* bacterial/ organic biofilm:A complex aggregation of microorganisms marked by the excretion of a protective
                and adhesive matrix; usually adhering to a substratum.*/
            case POLYTRAITS.SETTL_ENVO_00002034:
                return true;
            default:
                return false;
        }
    }

    @Override
    public String toString() {
        return "SETTLBody{"
                + "value='" + getValue() + '\''
                + "}";
    }
}
