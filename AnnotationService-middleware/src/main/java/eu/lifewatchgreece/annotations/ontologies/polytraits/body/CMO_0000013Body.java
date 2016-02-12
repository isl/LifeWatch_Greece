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
 * A measurement of the longest dimension of a body, typically between two
 * distinct ends of the body.
 */
@Iri(POLYTRAITS.CMO_0000013)
public abstract class CMO_0000013Body implements PolytraitsBody {

    /**
     * Check that the annotation has a valid value
     *
     * @return
     */
    @Override
    public boolean isValid() {
        switch (getValue()) {
            /* Up to 2.5mm */
            case POLYTRAITS.CMO_0000013_BS_1:
            /* 2.5mm - 10mm */
            case POLYTRAITS.CMO_0000013_BS_2:
            /* 11mm - 20mm */
            case POLYTRAITS.CMO_0000013_BS_3:
            /* 21mm - 50mm */
            case POLYTRAITS.CMO_0000013_BS_4:
            /* 51mm - 80mm */
            case POLYTRAITS.CMO_0000013_BS_5:
            /* 81mm - 100mm */
            case POLYTRAITS.CMO_0000013_BS_6:
            /* more than 100 mm */
            case POLYTRAITS.CMO_0000013_BS_7:
                return true;
            default:
                return false;
        }
    }

    @Override
    public String toString() {
        return "CMO_0000013Body{"
                + "value='" + getValue() + '\''
                + "}";
    }
}
