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
 * Survival temperature: The range of temperatures in which an organism is
 * capable to survive and grow.
 */
@Iri(POLYTRAITS.TEMP)
public abstract class TEMPBody implements PolytraitsBody {

    /**
     * Check that the annotation has a valid value
     *
     * @return
     */
    @Override
    public boolean isValid() {
        switch (getValue()) {
            /* cold waters : The capability of an organism to live in cold water environments (<0 - 10 °C).*/
            case POLYTRAITS.TEMP_COLD:
            /* warm temperate/subtropical waters:
                The capability of an organism to live in environments of average temperatures (10 - 25°C).*/
            case POLYTRAITS.TEMP_WARM:
            /* tropical waters: The capability of an organism to live in warm water
                environments (>25 °C). */
            case POLYTRAITS.TEMP_HOT:
                return true;
            default:
                return false;
        }
    }

    @Override
    public String toString() {
        return "TEMPBody{"
                + "value='" + getValue() + '\''
                + "}";
    }
}
