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
 * Mode of reproduction: The production by an organism of new individuals that
 * contain some portion of their genetic material inherited from that organism.
 */
@Iri(POLYTRAITS.GO_0000003)
public abstract class GO_0000003Body implements PolytraitsBody {

    /**
     * Check that the annotation has a valid value
     *
     * @return
     */
    @Override
    public boolean isValid() {
        switch (getValue()) {
            /* gonochoristic: Having separate sexes throughout life [1292].*/
            case POLYTRAITS.GO_0000003_HAO_0000048:
            /* simultaneuous hermaphrodita: Condition of hermaphroditic animals
                (and plants) in which the reproductive organs of both sexes are present and functional at the same time.*/
            case POLYTRAITS.GO_0000003_HAO_0000046:
            /* sequential hermaphrodita: Sequential hermaphrodites are born as one sex,
                but can later change into the opposite sex.*/
            case POLYTRAITS.GO_0000003_HAO_0000045:
            /* asexual reproduction: Reproduction that is not sexual; that is,
                reproduction that does not include recombining the genotypes of two parents.*/
            case POLYTRAITS.GO_0000003_GO_0019954:
                return true;
            default:
                return false;
        }
    }

    @Override
    public String toString() {
        return "GO_0000003Body{"
                + "value='" + getValue() + '\''
                + "}";
    }
}
