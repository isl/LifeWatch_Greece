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
 */package eu.lifewatchgreece.annotations.ontologies.polytraits.body;

import eu.lifewatchgreece.annotations.ontologies.polytraits.namespace.POLYTRAITS;
import org.openrdf.annotations.Iri;

/**
 * Physiographic feature: The general physical characteristics of the marine
 * environment in which an organism lives
 */
@Iri(POLYTRAITS.PHF)
public abstract class PHFBody implements PolytraitsBody {

    /**
     * Check that the annotation has a valid value
     *
     * @return
     */
    @Override
    public boolean isValid() {
        switch (getValue()) {
            /* open coast: Any part of the coast not within a marine inlet, strait
            or lagoon, including offshore rocks and small islands. This includes
            MNCR types; Linear coast, Islands / Rocks and Semi-enclosed coast.*/
            case POLYTRAITS.PHF_COAST:
            /* offshore seabed: Seabed beyond three miles (5km) from the shore.*/
            case POLYTRAITS.PHF_OFF:
            /* strait: Strait is a narrow channel of water that connects two larger
                bodies of water, and thus lies between two land masses.*/
            case POLYTRAITS.PHF_ENVO_00000394:
            /* fjord: Fjord is a long and narrow sea inlet with high steeply sloped walled sides.
                A fjord is a landform created during a period of glaciation.*/
            case POLYTRAITS.PHF_ENVO_00000039:
            /* ria: Ria is a submergent coastal landform that forms where sea levels rise relative to the
                land either as a result of eustatic sea level change; where the global sea levels rise or isostatic
                sea level change; where the land sinks. When this happens valleys which were previously at sea level
                become submerged.*/
            case POLYTRAITS.PHF_ENVO_00000418:
            /* estuary: A semi-enclosed coastal body of water with one or more rivers or streams flowing into it,
                and with a free connection to the open sea.*/
            case POLYTRAITS.PHF_ENVO_00000045:
            /*  enclosed coast / embayment: An area of water bordered by land on three sides.*/
            case POLYTRAITS.PHF_ENVO_00000032:
            /* lagoon: Enclosed bodies of water separated or partially separated from the sea by shingle,
                sand or sometimes rock and with a restricted exchange of water with the sea, yielding varying salinity regimes.*/
            case POLYTRAITS.PHF_LAG:
            /* hydrothermal vents: A marine hydrothermal vent is a marine benthic feature where heat generated
                due to tectonic activity, either at divergent plate boundaries or convergent ocean plates where back-arc
                spreading occurs, is released or 'vented' to the surface. The resultant high temperature water jets are
                laden with dissolved metals and minerals.*/
            case POLYTRAITS.PHF_ENVO_01000122:
                return true;
            default:
                return false;
        }
    }

    @Override
    public String toString() {
        return "PHFBody{"
                + "value='" + getValue() + '\''
                + "}";
    }
}
