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
 * Age at first reproduction: Beginning of the first sexual reproductive cycle.
 * Defined as the first ripening of gametes.
 */
@Iri(POLYTRAITS.MAT)
public abstract class MATBody implements PolytraitsBody {

    /**
     * Check that the annotation has a valid value
     *
     * @return
     */
    @Override
    public boolean isValid() {
        switch (getValue()) {
            /* <=2 months: Reproductive maturity reached at an age younger than 2 months.*/
            case POLYTRAITS.MAT_2M:
            /* 2 - 6 months: Reproductive maturity reached at an age between 2 to 6 months.*/
            case POLYTRAITS.MAT_6M:
            /* 6 months - 1year: Reproductive maturity reached at an age between 6 months to a year.*/
            case POLYTRAITS.MAT_1Y:
            /* 1-2 years: Reproductive maturity reached at an age between 1 to 2 years.*/
            case POLYTRAITS.MAT_2Y:
            /* 2-3 years : Reproductive maturity reached at an age between 2 to 3 years.*/
            case POLYTRAITS.MAT_3Y:
            /* 3-4 years: Reproductive maturity reached at an age between 3 to 4 years.*/
            case POLYTRAITS.MAT_4Y:
            /* >4 years: Reproductive maturity reached at an age more than 4 years.*/
            case POLYTRAITS.MAT_MANY:
                return true;
            default:
                return false;
        }
    }

    @Override
    public String toString() {
        return "MATBody{"
                + "value='" + getValue() + '\''
                + "}";
    }
}
