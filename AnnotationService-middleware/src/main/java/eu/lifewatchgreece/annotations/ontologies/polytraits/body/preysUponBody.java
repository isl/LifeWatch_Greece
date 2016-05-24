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
 * Typically feeds on: The type of food an organism prefers.
 */
@Iri(POLYTRAITS.preysUpon)
public abstract class preysUponBody implements PolytraitsBody {

    /**
     * Check that the annotation has a valid value
     *
     * @return
     */
    @Override
    public boolean isValid() {
        switch (getValue()) {
            /* algae: Algae as food source.*/
            case POLYTRAITS.preysUpon_TF_ALG:
            /* bacteria: Bacteria as food source.*/
            case POLYTRAITS.preysUpon_TF_BACT:
            /* anellids: Annelida as food source*/
            case POLYTRAITS.preysUpon_TF_ANN:
            /* ciliates: Ciliates as food source.*/
            case POLYTRAITS.preysUpon_TF_CIL:
            /* crustaceans : Crustaceans as food source.*/
            case POLYTRAITS.preysUpon_TF_CRUS:
            /* diatoms: Diatoms as food source.*/
            case POLYTRAITS.preysUpon_TF_DIAT:
            /* flagellates: Flagellates as food source.*/
            case POLYTRAITS.preysUpon_TF_FLAG:
            /* foraminiferans: Foraminiferans as food source. */
            case POLYTRAITS.preysUpon_TF_FORAM:
            /* mollusks: Mollusks as food source. */
            case POLYTRAITS.preysUpon_TF_MOLL:
            /* detritus: Particles of organic material from dead and decomposing organisms as food source.*/
            case POLYTRAITS.preysUpon_TF_OM:
            /* sediment: Unselective ingestion of sediment.*/
            case POLYTRAITS.preysUpon_TF_SED:
            /* fish: Fish, incl. their larvae, as food source.*/
            case POLYTRAITS.preysUpon_TF_FISH:
            /* ascidians:Ascidians, incl. their larvae, as food source.*/
            case POLYTRAITS.preysUpon_TF_ASC:
            /* echinoderms:Echinoderms, incl. their larvae, as food source.*/
            case POLYTRAITS.preysUpon_TF_ECHI:
            /* cnidarians: Cnidarians as food source.*/
            case POLYTRAITS.preysUpon_TF_CNID:
                return true;
            default:
                return false;
        }
    }

    @Override
    public String toString() {
        return "preysUponBody{"
                + "value='" + getValue() + '\''
                + "}";
    }
}
