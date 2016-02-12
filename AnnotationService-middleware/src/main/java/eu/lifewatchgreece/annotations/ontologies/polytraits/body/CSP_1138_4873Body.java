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
 * Pattern of oogenesis: Process of germ cell development in the female from the
 * primordial germ cells through oogonia to the mature haploid ova.
 */
@Iri(POLYTRAITS.CSP_1138_4873)
public abstract class CSP_1138_4873Body implements PolytraitsBody {

    /**
     * Check that the annotation has a valid value
     *
     * @return
     */
    @Override
    public boolean isValid() {
        switch (getValue()) {
            /* intraovarian: Occurs when oocytes are retained by the ovary until
            most or all of oogenesis (and vitellogenesis) is completed. Ovaries
            are usually large, structurally complex, and persistent throughout
            the sexual phase of the female [544].*/
            case POLYTRAITS.CSP_1138_4873_OOG_INTRA:
            /* extraovarian: Occurs when small, previtellogenic oocytes
            are released form the ovary where they complete vitellogenesis in the
            fluid-filled coelom. Ovaries are generally small, simple and
            sometimes have a transient nature [544].*/
            case POLYTRAITS.CSP_1138_4873_OOG_EXTRA:
                return true;
            default:
                return false;
        }
    }

    @Override
    public String toString() {
        return "CSP_1138_4873Body{"
                + "value='" + getValue() + '\''
                + "}";
    }
}
