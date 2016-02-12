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
 * Larval development: The mode of development from the larval to the adult
 * stage.
 */
@Iri(POLYTRAITS.LDEV)
public abstract class LDEVBody implements PolytraitsBody {

    /**
     * Check that the annotation has a valid value
     *
     * @return
     */
    @Override
    public boolean isValid() {
        switch (getValue()) {
            /* direct development: There are no intermediate larval stage(s) or postembryonic metamorphoses of any kind.
            Embryonic development culminates in the hatching or birth of a fully formed, albeit miniature adult [1316].*/
            case POLYTRAITS.LDEV_directDeveloper:
            /* indirect development:One or more successive, free-living larval stages intervene between embryo and adult,
                and there is a more-or-less abrupt transition, or metamorphosis, between the last larval stage and the adult [1316].*/
            case POLYTRAITS.LDEV_I:
                return true;
            default:
                return false;
        }
    }

    @Override
    public String toString() {
        return "LDEVBody{"
                + "value='" + getValue() + '\''
                + "}";
    }
}
