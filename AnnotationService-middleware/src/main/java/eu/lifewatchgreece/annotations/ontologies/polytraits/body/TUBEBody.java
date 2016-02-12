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
 * Tube /burrow material: Materials used for the construction of an organism’s
 * tube or burrow (if present).
 */
@Iri(POLYTRAITS.TUBE)
public abstract class TUBEBody implements PolytraitsBody {

    /**
     * Check that the annotation has a valid value
     *
     * @return
     */
    @Override
    public boolean isValid() {
        switch (getValue()) {
            /* clay: Tubes/burrows constructed of clay, a group of hydrous aluminium phyllosilicate minerals
    that are typically less than 3.9 micrometres in diameter.*/
            case POLYTRAITS.TUBE_ENVO_00002982:
            /* gravel: Tubes/burrows constructed of gravel, an environmental material which is composed of pieces
               of rock that are at least two millimeters (2 mm) in its largest dimension and no more than 75 millimeters.*/
            case POLYTRAITS.TUBE_ENVO_01000018_TOL:
            /* sand: Tubes/burrows constructed of sand, a naturally occurring granular material composed of
    finely divided rock and mineral particles.*/
            case POLYTRAITS.TUBE_ENVO_01000017:
            /* biogenic detritus: Tubes/burrows constructed of dead skeleton materials
                found in the environment (e.g. shells, algal parts).*/
            case POLYTRAITS.TUBE_BIO:
            /* secretions : Tubes/burrows constructed of bodily secretions, usually mucus.*/
            case POLYTRAITS.TUBE_ENVO_02000040:
            /* calcium carbonate: Tubes constructed of calcium carbonate.*/
            case POLYTRAITS.TUBE_CALC:
            /* mud : Tubes/burrows constructed of mud, a liquid or semi-liquid
                mixture of water and fine particles of silt and/or clay <0.063 mm; the silt/clay fraction is >80% [1292].*/
            case POLYTRAITS.TUBE_MUD:
                return true;
            default:
                return false;
        }
    }

    @Override
    public String toString() {
        return "TUBEBody{"
                + "value='" + getValue() + '\''
                + "}";
    }
}
