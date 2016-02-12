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
 * Habitat type: The place in which an organism lives. It is defined for the
 * marine environment according to geographical location, physiographic features
 * and the physical and chemical environment (including salinity, wave exposure,
 * strength of tidal streams, geology, biological zone, substratum, 'features'
 * (e.g. crevices, overhangs, rockpools) and 'modifiers' (e.g. sand-scour,
 * wave-surge, substratum mobility).
 */
@Iri(POLYTRAITS.habitat)
public abstract class habitatBody implements PolytraitsBody {

    /**
     * Check that the annotation has a valid value
     *
     * @return
     */
    @Override
    public boolean isValid() {
        switch (getValue()) {
            /* algae: Macroalgae surfaces, such as Laminaria spp., or fucoids.*/
            case POLYTRAITS.habitat_HAB_ALG:
            /* biogenic reef: Solid, massive structure which is created by accumulations of organisms,
                usually rising from the seabed, or at least clearly forming a substantial, discrete community or
                habitat which is very different from the surrounding seabed. The structure of the reef may be composed
                almost entirely of the reef building organism and its tubes or shells, or it may to some degree be
                composed of sediments, stones and shells bound together by the organisms*/
            case POLYTRAITS.habitat_HAB_REEF:
            /* caves: A hollow normally eroded in a cliff, with the penetration being greater than the width at the entrance [1297]).
                Caves can also be formed by boulders [1292] */
            case POLYTRAITS.habitat_HAB_CAVE:
            /* crevices/ fissures: Crevices are narrow cracks in a hard substratum <10 mm wide at its entrance,
                with the penetration being greater than the width at the entrance.
                Fissures are cracks in a hard substratum >10 mm wide at its entrance, with the depth being greater
                than the width at the entrance*/
            case POLYTRAITS.habitat_HAB_CREV:
            /* maerl/ coralligenous habitats: A coralligenous habitat is defined by the presence of a bioherm of coralline algae grown at
                low irradiance levels and in relatively calm waters [1119]. Maerl denotes loose-lying, normally non-geniculate (i.e. not jointed),
                coralline red algae. Depending on the terminology used, maerl refers either to a class of rhodoliths, or may be considered distinct
                from rhodoliths in lacking a non-algal core. Maerl beds are composed of living or dead unattached corallines forming accumulations
                with or without terrigenous material*/
            case POLYTRAITS.habitat_HAB_MAERL:
            /* other species: Epibiont of other species.*/
            case POLYTRAITS.habitat_ENVO_00002032:
            /* overhangs: An overhanging part of a rock formation.*/
            case POLYTRAITS.habitat_HAB_OH:
            /* rockpools: A depression in the littoral zone of a rocky seashore, where, during low tide, seawater is left behind.*/
            case POLYTRAITS.habitat_ENVO_00000317:
            /* salt marsh: A marsh whose water contains a considerable quantity of dissolved salts.*/
            case POLYTRAITS.habitat_ENVO_00000054:
            /* seagrass: Habitat associated with seagrass meadows communities. Seagrasses are flowering plants
                that are adapted to living fully submerged and rooted in estuarine and marine environments.*/
            case POLYTRAITS.habitat_ENVO_01000059:
            /* strandline: A line on the shore composing debris deposited by a receding tide; commonly used
                to denote the line of debris at the level of extreme high water*/
            case POLYTRAITS.habitat_HAB_STRAND:
            /*  under boulders: Under unattached rocks that can be very large (>1024 mm), large (512 - 1024 mm)
                or small (256 - 512 mm) [1292].*/
            case POLYTRAITS.habitat_HAB_UB:
            /*  water column: Pelagic habitat.*/
            case POLYTRAITS.habitat_ENVO_01000023_STRUCT:
            /*  soft sediments:Deposits with a high water content (near or above the liquid limit) where the
                percolating skeleton is made of fine-grained soils (clay fraction above ~ 20%), with a high degree
                of saturation, and subjected to low effective confinement [1336]*/
            case POLYTRAITS.habitat_ENVO_00002007:
                return true;
            default:
                return false;
        }
    }

    @Override
    public String toString() {
        return "habitatBody{"
                + "value='" + getValue() + '\''
                + "}";
    }
}
