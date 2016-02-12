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
 * Sexual metamorphosis: Conspicuous change in the organism's body structure
 * prior to reproduction.
 */
@Iri(POLYTRAITS.SM)
public abstract class SMBody implements PolytraitsBody {

    /**
     * Check that the annotation has a valid value
     *
     * @return
     */
    @Override
    public boolean isValid() {
        switch (getValue()) {
            /* yes: Organisms that undergo sexual metamorphosis.*/
            case POLYTRAITS.SM_YES:
            /* no: Organisms thahttp://www.sonyalpharumors.com/wp-content/uploads/2015/10/specs.pngt
                do not undergo sexual metamorphosis. */
            case POLYTRAITS.SM_NO:
                return true;
            default:
                return false;
        }
    }

    @Override
    public String toString() {
        return "SMBody{"
                + "value='" + getValue() + '\''
                + "}";
    }
}
