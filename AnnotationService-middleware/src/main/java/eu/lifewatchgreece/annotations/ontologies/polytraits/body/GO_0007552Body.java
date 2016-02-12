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
 * Metamorphosis type: Generally, any anatomical remodelling between opposing
 * life-history periods, i.e. between the larval and the adult stage, can be
 * considered as a form of metamorphosis [291] [1318]. These changes can be
 * rapid and cataclysmic, or can proceed gradually, depending on the particular
 * developmental basis for the juvenile body plan within the body of the larva
 * [1302].
 */
@Iri(POLYTRAITS.GO_0007552)
public abstract class GO_0007552Body implements PolytraitsBody {

    /**
     * Check that the annotation has a valid value
     *
     * @return
     */
    @Override
    public boolean isValid() {
        switch (getValue()) {
            /* catastrophic: The metamorphosis is accompanied by massive internal change coupled with catastrophic
            destruction of the larval tissues. Huge chunks of the larval body, its tissues and organs, are digested
            away and reabsorbed, or simply discarded [1322].*/
            case POLYTRAITS.GO_0007552_MV_CAT:
            /*  non-catastrophic: The adult develops from the juvenile through a process of extension and differential
                growth, including different larval stages but without a drastic change of the body plan. */
            case POLYTRAITS.GO_0007552_MV_NCAT:
                return true;
            default:
                return false;
        }
    }

    @Override
    public String toString() {
        return "GO_0007552Body{"
                + "value='" + getValue() + '\''
                + "}";
    }
}
