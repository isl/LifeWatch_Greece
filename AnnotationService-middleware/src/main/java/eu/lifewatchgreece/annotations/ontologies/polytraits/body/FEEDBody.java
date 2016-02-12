package eu.lifewatchgreece.annotations.ontologies.polytraits.body;

import eu.lifewatchgreece.annotations.ontologies.polytraits.namespace.POLYTRAITS;
import org.openrdf.annotations.Iri;

/**
 * Feeding structure: The feeding structures of the polychaetes vary, reflecting
 * the diversity of feeding types. There are two major anatomical/morphological
 * features involved in the polychaetes feeding: the pharynx and the feeding
 * structures of the prostomium (e.g. palps).
 */
@Iri(POLYTRAITS.FEED)
public abstract class FEEDBody implements PolytraitsBody {

    /**
     * Check that the annotation has a valid value
     *
     * @return
     */
    @Override
    public boolean isValid() {
        switch (getValue()) {
            /* predator: An organism that feeds by preying on other organisms, killing them for food.*/
            case POLYTRAITS.FEED_predator:
            /* suspension feeder: Any organism which feeds on particulate organic matter, including plankton,
                suspended in the water column*/
            case POLYTRAITS.FEED_suspensionFeeder:
            /* non selective deposit feeder: An organism that feeds on mud or sand and may show a little
                discrimination in the size or type of particles eaten. The sediment is ingested and any digestible
                organic material is assimilated as it passes through the alimentary canal. */
            case POLYTRAITS.FEED_NSD:
            /* selective deposit feeder: Some deposit feeders do not ingest sediment haphazardly but use their palps
                or buccal organs to sort organic material from the sediment prior to ingestion. The method of
                sorting varies according to the types of palps present.*/
            case POLYTRAITS.FEED_SD:
            /*  deposit feeder (selective or non-selective): “Umbrella term”. Any organism which feeds on fragmented
                particulate organic matter from the substratum [1292]. This modality should be filled in if nothing
                about the selectivity of the deposit feeding is known.*/
            case POLYTRAITS.FEED_D:
            /* omnivore: Organisms which feed on a mixed diet including plant and animal material.*/
            case POLYTRAITS.FEED_O:
            /* scavenger: Any organism that actively feeds on dead animals.*/
            case POLYTRAITS.FEED_S:
            /* herbivore: An animal that feeds on plants or algae, or parts of them.*/
            case POLYTRAITS.FEED_D060434:
                return true;
            default:
                return false;
        }
    }

    @Override
    public String toString() {
        return "FEEDBody{"
                + "value='" + getValue() + '\''
                + "}";
    }
}
