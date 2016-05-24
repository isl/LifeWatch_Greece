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
 * Sociability: “Umbrella term”. Is used to capture information that an
 * organism, population or species is living alone or interacting with others
 * forming groups/comm unities or colonies (through asexual reproduction).
 */
@Iri(POLYTRAITS.SOC)
public abstract class SOCBody implements PolytraitsBody {

    /**
     * Check that the annotation has a valid value
     *
     * @return
     */
    @Override
    public boolean isValid() {
        switch (getValue()) {
            /* algae: Species that interact/live with algae.*/
            case POLYTRAITS.SOC_ALG:
            /* seagrasses: Species that interact/live with seagrass meadows.*/
            case POLYTRAITS.SOC_PHAN:
            /* annelids: Species that interact/live with annelids.*/
            case POLYTRAITS.SOC_ANN:
            /* bacteria: Prey for bird species.*/
            case POLYTRAITS.SOC_BACT:
            /* crustaceans: Species that interact/live with crustaceans.*/
            case POLYTRAITS.SOC_CRUS:
            /* fish: Species that interact/live with fish.*/
            case POLYTRAITS.SOC_FISH:
            /* mollusks: Species that interact/live with mollusks.*/
            case POLYTRAITS.SOC_MOLL:
            /* nematodes: Species that interact/live with nematodes.*/
            case POLYTRAITS.SOC_NEM:
            /* branchiostomids: Species that interact/live with branchiostomids.*/
            case POLYTRAITS.SOC_BRAN:
            /* echinoderms: Species that interact/live with echinoderms.*/
            case POLYTRAITS.SOC_ECHI:
            /* cnidarians: Species that interact/live with cnidarians.*/
            case POLYTRAITS.SOC_ANTH:
            /* bryozans:Species that interact/live with bryozoans.*/
            case POLYTRAITS.SOC_BRY:
            /* entoproctans:Species that interact/live with entoproctans.*/
            case POLYTRAITS.SOC_ENT:
                return true;
            default:
                return false;
        }
    }

    @Override
    public String toString() {
        return "SOCBody{"
                + "value='" + getValue() + '\''
                + "}";
    }
}
