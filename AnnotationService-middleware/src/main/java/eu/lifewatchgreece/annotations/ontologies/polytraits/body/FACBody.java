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
 * Factors triggering reproduction:Factors that can initiate or enhance
 * reproduction.
 */
@Iri(POLYTRAITS.FAC)
public abstract class FACBody implements PolytraitsBody {

    /**
     * Check that the annotation has a valid value
     *
     * @return
     */
    @Override
    public boolean isValid() {
        switch (getValue()) {
            /* lunar cycle: Reproduction which is timed to particular phases of the lunar
            cycle (or the semilunar cycle of spring and neap tides) [1325].*/
            case POLYTRAITS.FAC_LUN:
            /* pheromones/hormones: Spawning as a result of a pheromonal interaction between swarming males and females.
                Hormonal factors may be involved not only in the timing of reproduction but also in sexual differentiation [1325].*/
            case POLYTRAITS.FAC_HOR:
            /* photoperiod: Reproduction which is timed to a particular daylight length [1325].*/
            case POLYTRAITS.FAC_PHO:
            /* temperature: Reproduction which is controlled by a change in water temperature.
                In some species, a certain temperature is a prerequisite for reproduction to occur [1325].*/
            case POLYTRAITS.FAC_TMP:
            /* salinity:Reproduction which is stimulated by changes in salinity [1326].*/
            case POLYTRAITS.FAC_SAL:
                return true;
            default:
                return false;
        }
    }

    @Override
    public String toString() {
        return "FACBody{"
                + "value='" + getValue() + '\''
                + "}";
    }
}
