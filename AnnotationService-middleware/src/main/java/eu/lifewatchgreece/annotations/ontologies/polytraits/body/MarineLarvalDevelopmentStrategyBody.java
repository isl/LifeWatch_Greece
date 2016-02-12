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
 * Larval feeding type: The existence of two distinct larval types, feeding and
 * non-feeding, has established the current paradigm for larval ecology. Feeding
 * larvae are larvae that can capture and use exogenous food, whereas
 * non-feeding larvae are larvae that cannot capture or use exogenous food.
 */
@Iri(POLYTRAITS.MarineLarvalDevelopmentStrategy)
public abstract class MarineLarvalDevelopmentStrategyBody implements PolytraitsBody {

    /**
     * Check that the annotation has a valid value
     *
     * @return
     */
    @Override
    public boolean isValid() {
        switch (getValue()) {
            /* planktotropic: A larval development strategy in which small eggs are converted into larger juveniles
            by means of larval feeding and growth [1299].*/
            case POLYTRAITS.MarineLarvalDevelopmentStrategy_planktotrophic:
            /* maternally derived nutrition: “Umbrella term” describing the maternal sources of nutrition
                and including the terms lecithotrophy, adelphophagy, and translocation of nutrients.*/
            case POLYTRAITS.MarineLarvalDevelopmentStrategy_LFT_M:
                return true;
            default:
                return false;
        }
    }

    @Override
    public String toString() {
        return "MarineLarvalDevelopmentStrategyBody{"
                + "value='" + getValue() + '\''
                + "}";
    }
}
