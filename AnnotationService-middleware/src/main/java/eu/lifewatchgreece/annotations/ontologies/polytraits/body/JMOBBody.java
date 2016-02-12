package eu.lifewatchgreece.annotations.ontologies.polytraits.body;

import eu.lifewatchgreece.annotations.ontologies.polytraits.namespace.POLYTRAITS;
import org.openrdf.annotations.Iri;

/**
 * Junevile mobility: The capability of a juvenile to move spontaneously and
 * freely.
 */
@Iri(POLYTRAITS.JMOB)
public abstract class JMOBBody implements PolytraitsBody {

    /**
     * Check that the annotation has a valid value
     *
     * @return
     */
    @Override
    public boolean isValid() {
        switch (getValue()) {
            /* crawler: An organism that moves along on the substratum via movements of its legs, appendages
            (e.g. parapodia and chaetae) or muscles [1292].*/
            case POLYTRAITS.JMOB_CRAWL:
            /* burrower:  An organism that lives or moves in a burrow in soft sediments.*/
            case POLYTRAITS.JMOB_BUR:
            /* swimmer: An organism that moves through the water column via movements of its fins, legs or
                appendages, via undulatory movements of the body or via jet propulsion; includes pelagic phases
                during reproduction (swarming at the surface) [1292].*/
            case POLYTRAITS.JMOB_SWIM:
            /* non-motile / semi-motile: Permanently attached to a substratum (non-motile) or capable of
                movement across (or through) it (semi-motile).*/
            case POLYTRAITS.JMOB_SESS:
                return true;
            default:
                return false;
        }
    }

    @Override
    public String toString() {
        return "JMOBBody{"
                + "value='" + getValue() + '\''
                + "}";
    }
}
