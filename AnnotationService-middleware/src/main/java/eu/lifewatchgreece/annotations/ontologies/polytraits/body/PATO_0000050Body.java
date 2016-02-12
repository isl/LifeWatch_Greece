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
@Iri(POLYTRAITS.PATO_0000050)
public abstract class PATO_0000050Body implements PolytraitsBody {

    /**
     * Check that the annotation has a valid value
     *
     * @return
     */
    @Override
    public boolean isValid() {
        switch (getValue()) {
            /*  <1 year: Life span shorter than a year.*/
            case POLYTRAITS.PATO_0000050_LIFE_1:
            /* 1-3 years: Life span between 1 and 3 years. */
            case POLYTRAITS.PATO_0000050_LIFE_1_3:
            /* 1-3 years: Life span between 3 and 5 years. */
            case POLYTRAITS.PATO_0000050_LIFE_3_5:
            /* 1-3 years: Life span more than 5 years. */
            case POLYTRAITS.PATO_0000050_LIFE_5:
                return true;
            default:
                return false;
        }
    }

    @Override
    public String toString() {
        return "PATO_0000050Body{"
                + "value='" + getValue() + '\''
                + "}";
    }
}
