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
 * Ecosystem engineering: Organisms can be considered as ecosystem engineers
 * when they directly or indirectly modulate the availability of resources to
 * other species, by causing physical state changes in biotic or abiotic
 * materials. In so doing they modify, maintain and/or create habitats
 */
@Iri(POLYTRAITS.RW)
public abstract class RWBody implements PolytraitsBody {

    /**
     * Check that the annotation has a valid value
     *
     * @return
     */
    @Override
    public boolean isValid() {
        switch (getValue()) {
            /* yes: “Umbrella term”. Used to capture information that a species is an ecosystem engineer,
            without specifying the type of engineering.*/
            case POLYTRAITS.RW_YES:
            /* no: “Umbrella term”. Used to capture information that a species is not an ecosystem engineer. */
            case POLYTRAITS.RW_NO:
            /* biodiffusor: Biodiffusors include organisms with activities that usually result in a constant
                and random local sediment biomixing over short distances */
            case POLYTRAITS.RW_DIFF:
            /* upward conveyor: Upward conveyors are vertically oriented species that typically feed
                head-down at depth in the sediment. Vertically oriented head-down feeders actively select
                and ingest particles at the deeper sediments and egest these non-locally as faeces in
                the sediment surface */
            case POLYTRAITS.RW_UC:
            /* downward conveyor: Downward conveyors exhibit a feeding strategy opposite to that of upward conveyors.
                Vertically oriented head-up feeders actively select and ingest particles at the surface and
                egest these non-locally as faeces in deeper sediment strata */
            case POLYTRAITS.RW_DC:
            /* regenerator: Regenerators are excavators that dig and continuously maintain burrows in the sediment
                and by doing so they mechanically transfer sediment from depth to the surface.*/
            case POLYTRAITS.RW_REG:
            /* blind-ended ventilation: Ventilation occurs when animals flush their burrows with overlying water
                for respiratory and feeding purposes. Blind-ended ventilation occurs when I-shaped burrows are
                flushed uni- or bidirectionally depending on the permeability of the sediment */
            case POLYTRAITS.RW_BEV:
            /* open-ended ventilation: In open-ended ventilation the burrows are U-shaped and can be
                flushed easily from one end to the other */
            case POLYTRAITS.RW_OEV:
            /* habitat-building: Species which create structures which in turn form new habitats for other species.*/
            case POLYTRAITS.TF_HAB:
                return true;
            default:
                return false;
        }
    }

    @Override
    public String toString() {
        return "RWBody{"
                + "value='" + getValue() + '\''
                + "}";
    }
}
