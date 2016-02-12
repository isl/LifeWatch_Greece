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
 * Survival Salinity: The range of salinities in which an organism is capable to
 * survive and grow [1292].
 */
@Iri(POLYTRAITS.D054712)
public abstract class D054712Body implements PolytraitsBody {

    /**
     * Check that the annotation has a valid value
     *
     * @return
     */
    @Override
    public boolean isValid() {
        switch (getValue()) {
            /* full salinity : The capability of an organism to live in environments
            of average marine water salinity (30 – 40 ‰).*/
            case POLYTRAITS.D054712_SAL_FULL:
            /* variable salinity: The capability of an organism to live in environments
                of variable salinity (18 – 40 ‰).*/
            case POLYTRAITS.D054712_SAL_VAR:
            /* reduced salinity: The capability of an organism to live in brackish
                water having a wide range of salinity between 18 ‰ and 30 ‰.*/
            case POLYTRAITS.D054712_SAL_RCD:
            /* low salinity: The capability of an organism to live in brackish water with low salinity (<18 ‰).*/
            case POLYTRAITS.D054712_SAL_LOW:
                return true;
            default:
                return false;
        }
    }

    @Override
    public String toString() {
        return "D054712Body{"
                + "value='" + getValue() + '\''
                + "}";
    }
}
