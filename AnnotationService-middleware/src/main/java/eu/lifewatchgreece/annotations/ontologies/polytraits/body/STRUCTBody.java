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
 * Feeding structure: The feeding structures of the polychaetes vary, reflecting
 * the diversity of feeding types. There are two major anatomical/morphological
 * features involved in the polychaetes feeding: the pharynx and the feeding
 * structures of the prostomium (e.g. palps).
 */
@Iri(POLYTRAITS.STRUCT)
public abstract class STRUCTBody implements PolytraitsBody {

    /**
     * Check that the annotation has a valid value
     *
     * @return
     */
    @Override
    public boolean isValid() {
        switch (getValue()) {
            /* simple axial pharynx: A sac-like pharynx relying on fluid pressure from the coelom for eversion.*/
            case POLYTRAITS.STRUCT_SAP:
            /* ventral buccal organ (simple) : A variable set of folds, musculature and glands, present
                on the ventral side of many polychaetes, is usually referred to as a ventral pharynx and
                is the most common form in Polychaetes.*/
            case POLYTRAITS.STRUCT_VBO:
            /* ventral muscular pharynx : The ventral and lateral walls of the buccal region are muscular
                and the lining is sclerotized into a varying number of eversible jaw pieces.
                The jaws are seperated into a pair of ventral mandibles and two or more pairs of lateral maxillae.*/
            case POLYTRAITS.STRUCT_VMP:
            /* muscular axial pharynx: The pharynx has thickened, strongly muscular
                walls and can be retracted into a sheath. In other cases the pharynx is
                partially retracted and partially inverted. The mouth proper is located
                at the tip of the pharynx when fully everted. */
            case POLYTRAITS.STRUCT_MAP:
            /* buccal organ absent or occluded: The buccal cavity lacks obvious
                differentiation of the wall and it is not eversible. In some species, if
                the buccal cavity is present at all, is only a transient larval structure and
                becomes completely occluded.*/
            case POLYTRAITS.STRUCT_ABS:
            /* accessory feeding structures : Other structures as palps, tentacles or a
                radiolar crown ("grooved palps").*/
            case POLYTRAITS.STRUCT_ACC:
                return true;
            default:
                return false;
        }
    }

    @Override
    public String toString() {
        return "STRUCTBody{"
                + "value='" + getValue() + '\''
                + "}";
    }
}
