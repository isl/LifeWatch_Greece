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
 * Spawning frequency of the population: Period and frequency of spawning in a
 * population.
 */
@Iri(POLYTRAITS.FREQ)
public abstract class FREQBody implements PolytraitsBody {

    /**
     * Check that the annotation has a valid value
     *
     * @return
     */
    @Override
    public boolean isValid() {
        switch (getValue()) {
            /* multiple events/year: More than once per year, but in relatively defined peaks or intense periods
    that do not fall within a drawn-out period.*/
            case POLYTRAITS.FREQ_MULTI:
            /* continous or semicontinous: Reproduction occurs all year round or for the most part of the year. */
            case POLYTRAITS.FREQ_CONT:
            /* annually/seasonally: Yearly over a drawn out period of several weeks or few months, or always in a defined season,
    peaks or epidemic swarming can occur within this period. */
            case POLYTRAITS.FREQ_ANNU:
                return true;
            default:
                return false;
        }
    }

    @Override
    public String toString() {
        return "FREQBody{"
                + "value='" + getValue() + '\''
                + "}";
    }
}
