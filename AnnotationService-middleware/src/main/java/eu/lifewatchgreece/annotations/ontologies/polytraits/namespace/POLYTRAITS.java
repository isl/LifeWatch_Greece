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
package eu.lifewatchgreece.annotations.ontologies.polytraits.namespace;

/**
 * The database contains 47 traits which are subdivided into 252 sub-categories
 * (called "modalities"). They cover mainly reproductive and behavioral traits
 * of both adult and larval stages, as well as information on environmental
 * preferences and a few morphological traits.
 * http://polytraits.lifewatchgreece.eu/terms
 */
/**
 *
 * @author papadako
 */
public class POLYTRAITS {

    /**
     * =========================================================================
     * Adult traits
     * =========================================================================
     */
    /**
     * A measurement of the longest dimension of a body, typically between two
     * distinct ends of the body.
     */
    public final static String CMO_0000013 = "http://purl.obolibrary.org/obo/CMO_0000013";

    /**
     * Modalities for BS
     */
    /* Up to 2.5mm */
    public final static String CMO_0000013_BS_1 = "http://polytraits.lifewatchgreece.eu/terms/BS_1";
    /* 2.5mm - 10mm */
    public final static String CMO_0000013_BS_2 = "http://polytraits.lifewatchgreece.eu/terms/BS_2";
    /* 11mm - 20mm */
    public final static String CMO_0000013_BS_3 = "http://polytraits.lifewatchgreece.eu/terms/BS_3";
    /* 21mm - 50mm */
    public final static String CMO_0000013_BS_4 = "http://polytraits.lifewatchgreece.eu/terms/BS_4";
    /* 51mm - 80mm */
    public final static String CMO_0000013_BS_5 = "http://polytraits.lifewatchgreece.eu/terms/BS_5";
    /* 81mm - 100mm */
    public final static String CMO_0000013_BS_6 = "http://polytraits.lifewatchgreece.eu/terms/BS_6";
    /* more than 100 mm */
    public final static String CMO_0000013_BS_7 = "http://polytraits.lifewatchgreece.eu/terms/BS_7";

    /*==================================================================================*/
    /**
     * Complex species: A group of species which satisfy the biological
     * definition of species, that is, they are reproductively isolated from
     * each other, but they are not morphologically distinguishable (or at least
     * are not readily or reliably distinguishable on a morphological basis)
     */
    public final static String CPLX = "http://polytraits.lifewatchgreece.eu/terms/CPLX";

    /**
     * Modalities for CPLX
     */
    /* Yes */
    public final static String CPLX_YES = "http://polytraits.lifewatchgreece.eu/terms/CPLX_YES";
    /* No */
    public final static String CPLX_NO = "http://polytraits.lifewatchgreece.eu/terms/CPLX_NO";

    /*==================================================================================*/
    /**
     * Depth zonation (benthos): The depth at which an organism occurs in the
     * water column. Commonly defined based on ecological features of the
     * zonation.
     */
    public final static String DZ = "http://polytraits.lifewatchgreece.eu/terms/DZ";

    /**
     * Modalities for DZ
     */
    /* supralitoral zone: The zone of the shore immediately above the highest water leveland
    subjected to wetting by spray or wave splash */
    public final static String DZ_SUP = "http://polytraits.lifewatchgreece.eu/terms/DZ_SUP";
    /* litoral zone: The area of the foreshore and seabed that is exposed to the air
    at low tide and submerged at high tide, i.e., the area between tide marks. */
    public final static String DZ_ENVO_00000316 = "http://purl.obolibrary.org/obo/ENVO_00000316";
    /* sublitoral zone: The zone of the shore immediately below the lowest water level and the
    edge of the continental shelf (ca. 200 m). */
    public final static String DZ_SUB = "http://polytraits.lifewatchgreece.eu/terms/DZ_SUB";
    /* bathyal zone: The steep descent zone from 200 m to 4000 m depth. */
    public final static String DZ_BAT = "http://polytraits.lifewatchgreece.eu/terms/DZ_BAT";
    /* hadal zone: The sea floor deeper than 6000 m, such as that of the oceanic trenches. */
    public final static String DZ_ENVO_01000028 = "http://purl.obolibrary.org/obo/ENVO_01000028";

    /*==================================================================================*/
    /**
     * Depth zonation (pelagic): The depth at which an organism occurs in the
     * water column. Usually defined based on ecological features of the
     * zonation.
     */
    public final static String DZP = " 	http://polytraits.lifewatchgreece.eu/terms/DZP";
    /**
     * Modalities for DZP
     */
    /* epipelagic zone: The zone of an ocean from the surface to 200m where photosynthesis can occur,
    due to the penetration of light. */
    public final static String DZP_ENVO_00000209 = "http://purl.obolibrary.org/obo/ENVO_00000209";
    /* mesopelagic zone: Water column from the upper aphotic zone (ca. 200 m) to a depth of ca. 100 m */
    public final static String DZP_MESO = "http://polytraits.lifewatchgreece.eu/terms/DZP_MESO";
    /* bathypelagic: Water column from ca. 1000 m to a depth of ca. 2500 m */
    public final static String DZP_BATH = "http://polytraits.lifewatchgreece.eu/terms/DZP_BATH";
    /* abyssopelagic:  	The zone of the ocean below the bathypelagic zone, with its lowest boundary at about 6000 m. */
    public final static String DZP_ENVO_00000212 = "http://purl.obolibrary.org/obo/ENVO_00000212";
    /* hadalpelagic zone:  The zone of an ocean in oceanic trenches, lying between 6000 m and 10000 m. */
    public final static String DZP_ENVO_00000214 = "http://purl.obolibrary.org/obo/ENVO_00000214";

    /*==================================================================================*/
    /**
     * Ecosystem engineering: Organisms can be considered as ecosystem engineers
     * when they directly or indirectly modulate the availability of resources
     * to other species, by causing physical state changes in biotic or abiotic
     * materials. In so doing they modify, maintain and/or create habitats
     */
    public final static String RW = "http://polytraits.lifewatchgreece.eu/terms/RW";
    /**
     * Modalities for RW
     */
    /* yes: “Umbrella term”. Used to capture information that a species is an ecosystem engineer,
    without specifying the type of engineering.*/
    public final static String RW_YES = "http://polytraits.lifewatchgreece.eu/terms/RW_YES";
    /* no: “Umbrella term”. Used to capture information that a species is not an ecosystem engineer. */
    public final static String RW_NO = "http://polytraits.lifewatchgreece.eu/terms/RW_NO";
    /* biodiffusor: Biodiffusors include organisms with activities that usually result in a constant
    and random local sediment biomixing over short distances */
    public final static String RW_DIFF = "http://polytraits.lifewatchgreece.eu/terms/RW_DIFF";
    /* upward conveyor: Upward conveyors are vertically oriented species that typically feed
    head-down at depth in the sediment. Vertically oriented head-down feeders actively select
    and ingest particles at the deeper sediments and egest these non-locally as faeces in
    the sediment surface */
    public final static String RW_UC = "http://polytraits.lifewatchgreece.eu/terms/RW_UC";
    /* downward conveyor: Downward conveyors exhibit a feeding strategy opposite to that of upward conveyors.
    Vertically oriented head-up feeders actively select and ingest particles at the surface and
    egest these non-locally as faeces in deeper sediment strata */
    public final static String RW_DC = "http://polytraits.lifewatchgreece.eu/terms/RW_DC";
    /* regenerator: Regenerators are excavators that dig and continuously maintain burrows in the sediment
    and by doing so they mechanically transfer sediment from depth to the surface.*/
    public final static String RW_REG = "http://polytraits.lifewatchgreece.eu/terms/RW_REG";
    /* blind-ended ventilation: Ventilation occurs when animals flush their burrows with overlying water
    for respiratory and feeding purposes. Blind-ended ventilation occurs when I-shaped burrows are
    flushed uni- or bidirectionally depending on the permeability of the sediment */
    public final static String RW_BEV = "http://polytraits.lifewatchgreece.eu/terms/RW_BEV";
    /* open-ended ventilation: In open-ended ventilation the burrows are U-shaped and can be
    flushed easily from one end to the other */
    public final static String RW_OEV = "http://polytraits.lifewatchgreece.eu/terms/RW_OEV";
    /* habitat-building: Species which create structures which in turn form new habitats for other species.*/
    public final static String TF_HAB = "http://polytraits.lifewatchgreece.eu/terms/TF_HAB";

    /*==================================================================================*/
    /**
     * Environmental position: Living location of the organism in relation to
     * the water column, the sea floor and other organisms.
     */
    public final static String EP = "http://polytraits.lifewatchgreece.eu/terms/EP";
    /**
     * Modalities for EP
     */
    /* Epibenthic: Living on the surface of the seabed.*/
    public final static String EP_EPIB = "http://polytraits.lifewatchgreece.eu/terms/EP_EPIB";
    /* Endobenthic: Living within the sediment of the seabed but not in interstitial space.*/
    public final static String EP_ENDOB = "http://polytraits.lifewatchgreece.eu/terms/EP_ENDOB";
    /* Interstitial: Living in the spaces between grains in a sediment.*/
    public final static String EP_IST = "http://polytraits.lifewatchgreece.eu/terms/EP_IST";
    /* Hyperbenthic: Living above but close to the substratum. */
    public final static String EP_HYP = "http://polytraits.lifewatchgreece.eu/terms/EP_HYP";
    /* Pelagic: Inhabiting the open sea, excluding the sea floor.*/
    public final static String EP_ENVO_01000023 = "http://purl.obolibrary.org/obo/ENVO_01000023";
    /* Epilithic: Attached to / growing on the surface of rock.*/
    public final static String EP_EPIL = "http://polytraits.lifewatchgreece.eu/terms/EP_EPIL";
    /* Endolithic: Organism that colonize the interior of any kind of rock but not actively boring into it.*/
    public final static String EP_EL = "http://polytraits.lifewatchgreece.eu/terms/EP_EL";
    /* Lithotomous: Actively boring into rocks and living in these burrows.*/
    public final static String EP_LITH = "http://polytraits.lifewatchgreece.eu/terms/EP_LITH";
    /* Boring in biogenic substrate : Boring in biogenic hard substrate and living in these burrows.*/
    public final static String EP_BB = "http://polytraits.lifewatchgreece.eu/terms/EP_BB";
    /* Epizoic : Growing or living on or in a living animal (but not parasitic upon it).*/
    public final static String EP_EPIZ = "http://polytraits.lifewatchgreece.eu/terms/EP_EPIZ";
    /* Epiphytic: Growing on or in a living plant (but not parasitic upon it).*/
    public final static String EP_EPIP = "http://polytraits.lifewatchgreece.eu/terms/EP_EPIP";
    /* Parasitic: Living in or on another organism at the expense of this host.*/
    public final static String EP_PAR = "http://polytraits.lifewatchgreece.eu/terms/EP_PAR";

    /*==================================================================================*/
    /**
     * Feeding structure: The feeding structures of the polychaetes vary,
     * reflecting the diversity of feeding types. There are two major
     * anatomical/morphological features involved in the polychaetes feeding:
     * the pharynx and the feeding structures of the prostomium (e.g. palps).
     */
    public final static String STRUCT = "http://polytraits.lifewatchgreece.eu/terms/STRUCT";
    /**
     * Modalities for STRUCT
     */
    /* simple axial pharynx: A sac-like pharynx relying on fluid pressure from the coelom for eversion.*/
    public final static String STRUCT_SAP = "http://polytraits.lifewatchgreece.eu/terms/STRUCT_SAP";
    /* ventral buccal organ (simple) : A variable set of folds, musculature and glands, present on the ventral side
    of many polychaetes, is usually referred to as a ventral pharynx and is the most common form in Polychaetes.*/
    public final static String STRUCT_VBO = "http://polytraits.lifewatchgreece.eu/terms/STRUCT_VBO";
    /* ventral muscular pharynx : The ventral and lateral walls of the buccal region are muscular and
    the lining is sclerotized into a varying number of eversible jaw pieces. The jaws are seperated into
    a pair of ventral mandibles and two or more pairs of lateral maxillae.*/
    public final static String STRUCT_VMP = "http://polytraits.lifewatchgreece.eu/terms/STRUCT_VMP";
    /* muscular axial pharynx: The pharynx has thickened, strongly muscular walls and can be retracted into a sheath.
    In other cases the pharynx is partially retracted and partially inverted.
    The mouth proper is located at the tip of the pharynx when fully everted. */
    public final static String STRUCT_MAP = "http://polytraits.lifewatchgreece.eu/terms/STRUCT_MAP";
    /* buccal organ absent or occluded: The buccal cavity lacks obvious differentiation of the wall and it is not eversible.
    In some species, if the buccal cavity is present at all, is only a transient larval structure and becomes completely occluded.*/
    public final static String STRUCT_ABS = "http://polytraits.lifewatchgreece.eu/terms/STRUCT_ABS";
    /* accessory feeding structures : Other structures as palps, tentacles or a radiolar crown ("grooved palps").*/
    public final static String STRUCT_ACC = "http://polytraits.lifewatchgreece.eu/terms/STRUCT_ACC";

    /*==================================================================================*/
    /**
     * Feeding type: The common diet of an organism that includes the food items
     * that it is enzymatically and behaviourally capable of using.
     */
    public final static String FEED = "http://polytraits.lifewatchgreece.eu/terms/FEED";
    /**
     * Modalities for FEED
     */
    /* predator: An organism that feeds by preying on other organisms, killing them for food.*/
    public final static String FEED_predator = "http://eol.org/schema/terms/predator";
    /* suspension feeder: Any organism which feeds on particulate organic matter, including plankton,
    suspended in the water column*/
    public final static String FEED_suspensionFeeder = "http://eol.org/schema/terms/suspensionFeeder";
    /* non selective deposit feeder: An organism that feeds on mud or sand and may show a little
    discrimination in the size or type of particles eaten. The sediment is ingested and any digestible
    organic material is assimilated as it passes through the alimentary canal. */
    public final static String FEED_NSD = "http://polytraits.lifewatchgreece.eu/terms/FEED_NSD";
    /* selective deposit feeder: Some deposit feeders do not ingest sediment haphazardly but use their palps or
    buccal organs to sort organic material from the sediment prior to ingestion. The method of sorting varies
    according to the types of palps present.*/
    public final static String FEED_SD = "http://polytraits.lifewatchgreece.eu/terms/FEED_SD";
    /*  deposit feeder (selective or non-selective): “Umbrella term”. Any organism which feeds on fragmented
    particulate organic matter from the substratum [1292]. This modality should be filled in if nothing
    about the selectivity of the deposit feeding is known.*/
    public final static String FEED_D = "http://polytraits.lifewatchgreece.eu/terms/FEED_D";
    /* omnivore: Organisms which feed on a mixed diet including plant and animal material.*/
    public final static String FEED_O = "http://polytraits.lifewatchgreece.eu/terms/FEED_O";
    /* scavenger: Any organism that actively feeds on dead animals.*/
    public final static String FEED_S = "http://polytraits.lifewatchgreece.eu/terms/FEED_S";
    /* herbivore: An animal that feeds on plants or algae, or parts of them.*/
    public final static String FEED_D060434 = "http://purl.bioontology.org/ontology/MESH/D060434";

    /*==================================================================================*/
    /**
     * Habitat type: The place in which an organism lives. It is defined for the
     * marine environment according to geographical location, physiographic
     * features and the physical and chemical environment (including salinity,
     * wave exposure, strength of tidal streams, geology, biological zone,
     * substratum, 'features' (e.g. crevices, overhangs, rockpools) and
     * 'modifiers' (e.g. sand-scour, wave-surge, substratum mobility).
     */
    public final static String habitat = "http://rs.tdwg.org/dwc/terms/habitat";
    /**
     * Modalities for habitat
     */
    /* algae: Macroalgae surfaces, such as Laminaria spp., or fucoids.*/
    public final static String habitat_HAB_ALG = "http://polytraits.lifewatchgreece.eu/terms/HAB_ALG";
    /* biogenic reef: Solid, massive structure which is created by accumulations of organisms,
    usually rising from the seabed, or at least clearly forming a substantial, discrete community or
    habitat which is very different from the surrounding seabed. The structure of the reef may be composed
    almost entirely of the reef building organism and its tubes or shells, or it may to some degree be
    composed of sediments, stones and shells bound together by the organisms*/
    public final static String habitat_HAB_REEF = "http://polytraits.lifewatchgreece.eu/terms/HAB_REEF";
    /* caves: A hollow normally eroded in a cliff, with the penetration being greater than the width at the entrance [1297]).
    Caves can also be formed by boulders [1292] */
    public final static String habitat_HAB_CAVE = "http://polytraits.lifewatchgreece.eu/terms/HAB_CAVE";
    /* crevices/ fissures: Crevices are narrow cracks in a hard substratum <10 mm wide at its entrance,
    with the penetration being greater than the width at the entrance.
    Fissures are cracks in a hard substratum >10 mm wide at its entrance, with the depth being greater
    than the width at the entrance*/
    public final static String habitat_HAB_CREV = "http://polytraits.lifewatchgreece.eu/terms/HAB_CREV";
    /* maerl/ coralligenous habitats: A coralligenous habitat is defined by the presence of a bioherm of coralline algae grown at
    low irradiance levels and in relatively calm waters [1119]. Maerl denotes loose-lying, normally non-geniculate (i.e. not jointed),
    coralline red algae. Depending on the terminology used, maerl refers either to a class of rhodoliths, or may be considered distinct
    from rhodoliths in lacking a non-algal core. Maerl beds are composed of living or dead unattached corallines forming accumulations
    with or without terrigenous material*/
    public final static String habitat_HAB_MAERL = "http://polytraits.lifewatchgreece.eu/terms/HAB_MAERL";
    /* other species: Epibiont of other species.*/
    public final static String habitat_ENVO_00002032 = "http://purl.obolibrary.org/obo/ENVO_00002032";
    /* overhangs: An overhanging part of a rock formation.*/
    public final static String habitat_HAB_OH = "http://polytraits.lifewatchgreece.eu/terms/HAB_OH";
    /* rockpools: A depression in the littoral zone of a rocky seashore, where, during low tide, seawater is left behind.*/
    public final static String habitat_ENVO_00000317 = "http://purl.obolibrary.org/obo/ENVO_00000317";
    /* salt marsh: A marsh whose water contains a considerable quantity of dissolved salts.*/
    public final static String habitat_ENVO_00000054 = "http://purl.obolibrary.org/obo/ENVO_00000054";
    /* seagrass: Habitat associated with seagrass meadows communities. Seagrasses are flowering plants
    that are adapted to living fully submerged and rooted in estuarine and marine environments.*/
    public final static String habitat_ENVO_01000059 = "http://purl.obolibrary.org/obo/ENVO_01000059";
    /* strandline: A line on the shore composing debris deposited by a receding tide; commonly used
    to denote the line of debris at the level of extreme high water*/
    public final static String habitat_HAB_STRAND = "http://polytraits.lifewatchgreece.eu/terms/HAB_STRAND";
    /*  under boulders: Under unattached rocks that can be very large (>1024 mm), large (512 - 1024 mm)
    or small (256 - 512 mm) [1292].*/
    public final static String habitat_HAB_UB = "http://polytraits.lifewatchgreece.eu/terms/HAB_UB";
    /*  water column: Pelagic habitat.*/
    public final static String habitat_ENVO_01000023_STRUCT = "http://purl.obolibrary.org/obo/ENVO_01000023";
    /*  soft sediments:Deposits with a high water content (near or above the liquid limit) where the
    percolating skeleton is made of fine-grained soils (clay fraction above ~ 20%), with a high degree
    of saturation, and subjected to low effective confinement [1336]*/
    public final static String habitat_ENVO_00002007 = "http://purl.obolibrary.org/obo/ENVO_00002007";

    /*==================================================================================*/
    /**
     * Intra- and interspecific competition: The simultaneous demand by two or
     * more organisms or populations or species for an essential common resource
     * that is actually or potentially in limited supply or the detrimental
     * interaction between two or more organisms or species seeking a common
     * resource that is not limited
     */
    public final static String GO_0044402 = "http://purl.obolibrary.org/obo/GO_0044402";
    /**
     * Modalities for GO_0044402
     */
    /*  annelida (adults): Competition with crustaceans that are in adult stage.*/
    public final static String GO_0044402_COMP_AA = "http://polytraits.lifewatchgreece.eu/terms/COMP_AA";
    /* crustacea (adults): */
    public final static String GO_0044402_COMP_CA = "http://polytraits.lifewatchgreece.eu/terms/COMP_CA";
    /*  annelida (larvea): Competition with other annelids that are in larval stage.
    The interaction can be between different organisms, populations or species.*/
    public final static String GO_0044402_COMP_AL = "http://polytraits.lifewatchgreece.eu/terms/COMP_AL";
    /* crustacea (larvea): Competition with crustaceans that are in larval stage.*/
    public final static String GO_0044402_COMP_CL = "http://polytraits.lifewatchgreece.eu/terms/COMP_CL";
    /* mollusca: Competition with mollusks.*/
    public final static String GO_0044402_COMP_MOLL = "http://polytraits.lifewatchgreece.eu/terms/COMP_MOLL";

    /*==================================================================================*/
    /**
     * Lifespan: Maximum length of time that any particular organism can be
     * expected to live.
     */
    public final static String PATO_0000050 = "http://purl.obolibrary.org/obo/PATO_0000050";
    /**
     * Modalities for PATO_0000050
     */
    /*  <1 year: Life span shorter than a year.*/
    public final static String PATO_0000050_LIFE_1 = "http://polytraits.lifewatchgreece.eu/terms/LIFE_1";
    /* 1-3 years: Life span between 1 and 3 years. */
    public final static String PATO_0000050_LIFE_1_3 = "http://polytraits.lifewatchgreece.eu/terms/LIFE_1-3";
    /* 1-3 years: Life span between 3 and 5 years. */
    public final static String PATO_0000050_LIFE_3_5 = "http://polytraits.lifewatchgreece.eu/terms/LIFE_3-5";
    /* 1-3 years: Life span more than 5 years. */
    public final static String PATO_0000050_LIFE_5 = "http://polytraits.lifewatchgreece.eu/terms/LIFE_5+";
    /*==================================================================================*/
    /**
     * Migration of adult: Movement of an organism or group from one habitat or
     * location to another [1292].
     */
    public final static String IDOMAL_0002084 = "http://purl.obolibrary.org/obo/IDOMAL_0002084";
    /**
     * Modalities for IDOMAL_0002084
     */
    /* yes: “Umbrella term”. Used to capture information that a species in its adult stage is migratory.*/
    public final static String IDOMAL_0002084_MIGR_YES = "http://polytraits.lifewatchgreece.eu/terms/MIGR_YES";
    /* no: “Umbrella term”. Used to capture information that a species in its adult stage is non-migratory
    and remains within the same area.*/
    public final static String IDOMAL_0002084_MIGR_NO = "http://polytraits.lifewatchgreece.eu/terms/MIGR_NO";
    /*==================================================================================*/
    /**
     * Mobility of adult: The capability of an organism to move spontaneously
     * and freely
     */
    public final static String GO_0040011 = "http://purl.obolibrary.org/obo/GO_0040011";
    /**
     * Modalities for GO_0040011
     */
    /* crawler: An organism that moves along on the substratum via movements of its legs, appendages
    (e.g. parapodia and chaetae) or muscles [1292].*/
    public final static String GO_0040011_MOB_CRAWL = "http://polytraits.lifewatchgreece.eu/terms/MOB_CRAWL";
    /* burrower: An organism that lives or moves in a burrow in soft sediments.*/
    public final static String GO_0040011_MOB_BUR = "http://polytraits.lifewatchgreece.eu/terms/MOB_BUR";
    /* swimmer: An organism that moves through the water column via movements of its fins, legs or
    appendages, via undulatory movements of the body or via jet propulsion; includes pelagic phases
    during reproduction (swarming at the surface) [1292].*/
    public final static String GO_0040011_MOB_SWIM = "http://polytraits.lifewatchgreece.eu/terms/MOB_SWIM";
    /* non-motile / semi-motile: Permanently attached to a substratum (non-motile) or capable of
    movement across (or through) it (semi-motile).*/
    public final static String GO_0040011_MOB_SESS = "http://polytraits.lifewatchgreece.eu/terms/MOB_SESS";
    /*==================================================================================*/
    /**
     * Physiographic feature: The general physical characteristics of the marine
     * environment in which an organism lives
     */
    public final static String PHF = "http://polytraits.lifewatchgreece.eu/terms/PHF";
    /**
     * Modalities for PHF
     */
    /* open coast: Any part of the coast not within a marine inlet, strait or lagoon, including offshore rocks and small islands.
    This includes MNCR types; Linear coast, Islands / Rocks and Semi-enclosed coast.*/
    public final static String PHF_COAST = "http://polytraits.lifewatchgreece.eu/terms/PHF_COAST";
    /* offshore seabed: Seabed beyond three miles (5km) from the shore.*/
    public final static String PHF_OFF = "http://polytraits.lifewatchgreece.eu/terms/PHF_OFF";
    /* strait: Strait is a narrow channel of water that connects two larger bodies of water,
    and thus lies between two land masses.*/
    public final static String PHF_ENVO_00000394 = "http://purl.obolibrary.org/obo/ENVO_00000394";
    /* fjord: Fjord is a long and narrow sea inlet with high steeply sloped walled sides.
    A fjord is a landform created during a period of glaciation.*/
    public final static String PHF_ENVO_00000039 = "http://purl.obolibrary.org/obo/ENVO_00000039";
    /* ria: Ria is a submergent coastal landform that forms where sea levels rise relative to the
    land either as a result of eustatic sea level change; where the global sea levels rise or isostatic
    sea level change; where the land sinks. When this happens valleys which were previously at sea level
    become submerged.*/
    public final static String PHF_ENVO_00000418 = "http://purl.obolibrary.org/obo/ENVO_00000418";
    /* estuary: A semi-enclosed coastal body of water with one or more rivers or streams flowing into it,
    and with a free connection to the open sea.*/
    public final static String PHF_ENVO_00000045 = "http://purl.obolibrary.org/obo/ENVO_00000045";
    /*  enclosed coast / embayment: An area of water bordered by land on three sides.*/
    public final static String PHF_ENVO_00000032 = "http://purl.obolibrary.org/obo/ENVO_00000032";
    /* lagoon: Enclosed bodies of water separated or partially separated from the sea by shingle,
    sand or sometimes rock and with a restricted exchange of water with the sea, yielding varying salinity regimes.*/
    public final static String PHF_LAG = "http://polytraits.lifewatchgreece.eu/terms/PHF_LAG";
    /* hydrothermal vents: A marine hydrothermal vent is a marine benthic feature where heat generated
    due to tectonic activity, either at divergent plate boundaries or convergent ocean plates where back-arc
    spreading occurs, is released or 'vented' to the surface. The resultant high temperature water jets are
    laden with dissolved metals and minerals.*/
    public final static String PHF_ENVO_01000122 = "http://purl.obolibrary.org/obo/ENVO_01000122";
    /*==================================================================================*/
    /**
     * Predated by: Organism categories that feed by preying on the present
     * species
     */
    public final static String PRED = "http://polytraits.lifewatchgreece.eu/terms/PRED";
    /**
     * Modalities for PRED
     */
    /* annelids : Prey for other annelids.*/
    public final static String PRED_ANN = "http://polytraits.lifewatchgreece.eu/terms/PRED_ANN";
    /* crustaceans: Prey for crustacean species*/
    public final static String PRED_CRUS = "http://polytraits.lifewatchgreece.eu/terms/PRED_CRUS";
    /* fish: Prey for fish species.*/
    public final static String PRED_FISH = "http://polytraits.lifewatchgreece.eu/terms/PRED_FISH";
    /* birds: Prey for bird species.*/
    public final static String PRED_BIRD = "http://polytraits.lifewatchgreece.eu/terms/PRED_BIRD";
    /* mollusks: Prey for mollusks.*/
    public final static String PRED_MOLL = "http://polytraits.lifewatchgreece.eu/terms/PRED_MOLL";
    /* echinoderms: Prey for echinoderm species.*/
    public final static String PRED_ECHI = "http://polytraits.lifewatchgreece.eu/terms/PRED_ECHI";
    /*==================================================================================*/
    /**
     * Sociability: “Umbrella term”. Is used to capture information that an
     * organism, population or species is living alone or interacting with
     * others forming groups/communities or colonies (through asexual
     * reproduction).
     */
    public final static String SOC = "http://polytraits.lifewatchgreece.eu/terms/SOC";
    /**
     * Modalities for SOC
     */
    /* algae: Species that interact/live with algae.*/
    public final static String SOC_ALG = "http://polytraits.lifewatchgreece.eu/terms/SOC_ALG";
    /* seagrasses: Species that interact/live with seagrass meadows.*/
    public final static String SOC_PHAN = "http://polytraits.lifewatchgreece.eu/terms/SOC_PHAN";
    /* annelids: Species that interact/live with annelids.*/
    public final static String SOC_ANN = "http://polytraits.lifewatchgreece.eu/terms/SOC_ANN";
    /* bacteria: Prey for bird species.*/
    public final static String SOC_BACT = "http://polytraits.lifewatchgreece.eu/terms/SOC_BACT";
    /* crustaceans: Species that interact/live with crustaceans.*/
    public final static String SOC_CRUS = "http://polytraits.lifewatchgreece.eu/terms/SOC_CRUS";
    /* fish: Species that interact/live with fish.*/
    public final static String SOC_FISH = "http://polytraits.lifewatchgreece.eu/terms/SOC_FISH";
    /* mollusks: Species that interact/live with mollusks.*/
    public final static String SOC_MOLL = "http://polytraits.lifewatchgreece.eu/terms/SOC_MOLL";
    /* nematodes: Species that interact/live with nematodes.*/
    public final static String SOC_NEM = "http://polytraits.lifewatchgreece.eu/terms/SOC_NEM";
    /* branchiostomids: Species that interact/live with branchiostomids.*/
    public final static String SOC_BRAN = "http://polytraits.lifewatchgreece.eu/terms/SOC_BRAN";
    /* echinoderms: Species that interact/live with echinoderms.*/
    public final static String SOC_ECHI = "http://polytraits.lifewatchgreece.eu/terms/SOC_ECHI";
    /* cnidarians: Species that interact/live with poriferans.*/
    public final static String SOC_POR = "http://polytraits.lifewatchgreece.eu/terms/SOC_POR";
    /* bryozans:Species that interact/live with bryozoans.*/
    public final static String SOC_BRY = "http://polytraits.lifewatchgreece.eu/terms/SOC_BRY";
    /* entoproctans:Species that interact/live with entoproctans.*/
    public final static String SOC_ENT = "http://polytraits.lifewatchgreece.eu/terms/SOC_ENT";
    /*==================================================================================*/
    /**
     * Substrate type: The surface on which an organism lives. The substrate may
     * simply provide structural support, or may provide nutrients [1292]. .
     */
    public final static String SUBST = "http://polytraits.lifewatchgreece.eu/terms/SUBST";
    /**
     * Modalities for SUBST
     */
    /* bedrock: Any stable hard substratum not separated into boulders or smaller sediment units.*/
    public final static String SUBST_ROCK = "http://polytraits.lifewatchgreece.eu/terms/SUBST_ROCK";
    /* large to very large boulders: Unattached rock, of large (512 - 1024 mm) or very large (>1024 mm) size [1292].*/
    public final static String SUBST_LB = "http://polytraits.lifewatchgreece.eu/terms/SUBST_LB";
    /* small boulders: Unattached rock, of small (256 - 512 mm) size [1292].*/
    public final static String SUBST_SB = "http://polytraits.lifewatchgreece.eu/terms/SUBST_SB";
    /* cobbles: Sediment characterised by an average particle diameter between 64 and 256 mm.*/
    public final static String SUBST_ENVO_01000115 = "http://purl.obolibrary.org/obo/ENVO_01000115";
    /* pebbles: Sediment characterised by an average particle diameter between 4 and 64 mm.*/
    public final static String SUBST_ENVO_01000116 = "http://purl.obolibrary.org/obo/ENVO_01000116";
    /* gravel: An environmental material which is composed of pieces of rock that are at least two
    millimeters (2 mm) in its largest dimension and no more than 75 millimeters.*/
    public final static String SUBST_ENVO_01000018_SUBST = "http://purl.obolibrary.org/obo/ENVO_01000018";
    /* gravel: 50 - 80% gravel; 20 - 50% sand.*/
    public final static String SUBST_SG = "http://polytraits.lifewatchgreece.eu/terms/SUBST_SG";
    /* muddy gravel: 50 - 80% gravel; 20 - 50% mud.*/
    public final static String SUBST_MG = "http://polytraits.lifewatchgreece.eu/terms/SUBST_MG";
    /* muddy sandy gravel: 50 - 80% gravel; 20 - 50% mud and sand.*/
    public final static String SUBST_MSG = "http://polytraits.lifewatchgreece.eu/terms/SUBST_MSG";
    /* coarse clean sand: Sediment particles diameter between 0.5 - 4 mm; the sand fraction is >80%.*/
    public final static String SUBST_CS = "http://polytraits.lifewatchgreece.eu/terms/SUBST_CS";
    /* fine clean sand: Sediment particles diameter between 0.063 - 0.5 mm; the sand fraction is >80%.*/
    public final static String SUBST_FS = "http://polytraits.lifewatchgreece.eu/terms/SUBST_FS";
    /* gravelly sand:50 - 80% sand; 20 - 80% gravel.*/
    public final static String SUBST_GS = "http://polytraits.lifewatchgreece.eu/terms/SUBST_GS";
    /* muddy gravelly sand:50 - 80% sand; 20 -50% mud and sand.*/
    public final static String SUBST_MGS = "http://polytraits.lifewatchgreece.eu/terms/SUBST_MGS";
    /* muddy sand: 50 - 80% sand; 20 -50% mud.*/
    public final static String SUBST_MS = "http://polytraits.lifewatchgreece.eu/terms/SUBST_MS";
    /* sandy mud: 50 - 80% mud; 20 -50% sand.*/
    public final static String SUBST_SM = "http://polytraits.lifewatchgreece.eu/terms/SUBST_SM";
    /* sandy gravely mud: 50 - 80% mud; 20 - 50% sand and gravel.*/
    public final static String SUBST_SGM = "http://polytraits.lifewatchgreece.eu/terms/SUBST_SGM";
    /* gravelly mud: 50 - 80% mud; 20 -50% gravel.*/
    public final static String SUBST_GM = "http://polytraits.lifewatchgreece.eu/terms/SUBST_GM";
    /* mud: Fine particles of silt and/or clay <0.063 mm; the silt/clay fraction is > 80% [1292].*/
    public final static String SUBST_MUD_SUBST = "http://polytraits.lifewatchgreece.eu/terms/MUD";
    /* silt: Sediment characterised by an average particle diameter between 3.9 and 63 micrometers.*/
    public final static String SUBST_ENVO_01000119 = "http://purl.obolibrary.org/obo/ENVO_01000119";
    /* clay: Sediment characterised by an average particle diameter between 1 and 3.9 micrometers.*/
    public final static String SUBST_ENVO_01000120 = "http://purl.obolibrary.org/obo/ENVO_01000120";
    /* mixed:Mixtures of a variety of sediment types composed of pebble/ gravel/ sand/ mud.
    This category includes muddy gravels, muddy sandy gravels, gravelly muds, and muddy gravelly sands.*/
    public final static String SUBST_SUBST_MIX = "http://polytraits.lifewatchgreece.eu/terms/SUBST_MIX";
    /* artificial:E.g. wood, metal or concrete structures.*/
    public final static String SUBST_ART = "http://polytraits.lifewatchgreece.eu/terms/SUBST_ART";
    /*==================================================================================*/
    /**
     * Survival Salinity: The range of salinities in which an organism is
     * capable to survive and grow [1292].
     */
    public final static String D054712 = "http://purl.bioontology.org/ontology/MSH/D054712";
    /**
     * Modalities for D054712
     */
    /* full salinity : The capability of an organism to live in environments of average marine water salinity (30 – 40 ‰).*/
    public final static String D054712_SAL_FULL = "http://polytraits.lifewatchgreece.eu/terms/SAL_FULL";
    /* variable salinity: The capability of an organism to live in environments of variable salinity (18 – 40 ‰).*/
    public final static String D054712_SAL_VAR = "http://polytraits.lifewatchgreece.eu/terms/SAL_VAR";
    /* reduced salinity: The capability of an organism to live in brackish water having a wide range of salinity between 18 ‰ and 30 ‰.*/
    public final static String D054712_SAL_RCD = "http://polytraits.lifewatchgreece.eu/terms/SAL_RCD";
    /* low salinity: The capability of an organism to live in brackish water with low salinity (<18 ‰).*/
    public final static String D054712_SAL_LOW = "http://polytraits.lifewatchgreece.eu/terms/SAL_LOW";
    /*==================================================================================*/
    /**
     * Survival temperature: The range of temperatures in which an organism is
     * capable to survive and grow.
     */
    public final static String TEMP = "http://polytraits.lifewatchgreece.eu/terms/TEMP";
    /**
     * Modalities for TEMP
     */
    /* cold waters : The capability of an organism to live in cold water environments (<0 - 10 °C).*/
    public final static String TEMP_COLD = "http://polytraits.lifewatchgreece.eu/terms/TEMP_COLD";
    /* warm temperate/subtropical waters: The capability of an organism to live in environments of average temperatures (10 - 25°C).*/
    public final static String TEMP_WARM = "http://polytraits.lifewatchgreece.eu/terms/TEMP_WARM";
    /* tropical waters: The capability of an organism to live in warm water environments (>25 °C). */
    public final static String TEMP_HOT = "http://polytraits.lifewatchgreece.eu/terms/TEMP_HOT";
    /*==================================================================================*/
    /**
     * Tolerance (AMBI index): The sensitivity of an organism to organic
     * enrichment, classified through the AMBI index [41].
     */
    public final static String TOL = "http://polytraits.lifewatchgreece.eu/terms/TOL";
    /**
     * Modalities for TOL
     */
    /* group I : Species very sensitive to organic enrichment and present under unpolluted conditions (initial state).*/
    public final static String TOL_I = "http://polytraits.lifewatchgreece.eu/terms/TOL_I";
    /* group II : Species indifferent to enrichment, always present in low densities with non-significant variations with time
    (from initial state to slightly unbalanced condition).*/
    public final static String TOL_II = "http://polytraits.lifewatchgreece.eu/terms/TOL_II";
    /* group III : Species tolerant to excess organic matter enrichment. These species may occur under normal conditions,
    but their populations are stimulated by organic enrichment (slightly unbalanced condition).*/
    public final static String TOL_III = "http://polytraits.lifewatchgreece.eu/terms/TOL_III";
    /* group IV : Second-order opportunistic species (slightly to pronouncedly unbalanced condition).*/
    public final static String TOL_IV = "http://polytraits.lifewatchgreece.eu/terms/TOL_IV";
    /* group V : First-order opportunistic species (pronouncedly unbalanced condition).*/
    public final static String TOL_V = "http://polytraits.lifewatchgreece.eu/terms/TOL_V";
    /*==================================================================================*/
    /**
     * Tube /burrow material: Materials used for the construction of an
     * organism’s tube or burrow (if present).
     */
    public final static String TUBE = "http://polytraits.lifewatchgreece.eu/terms/TUBE";
    /**
     * Modalities for TUBE
     */
    /* clay: Tubes/burrows constructed of clay, a group of hydrous aluminium phyllosilicate minerals
    that are typically less than 3.9 micrometres in diameter.*/
    public final static String TUBE_ENVO_00002982 = "http://purl.obolibrary.org/obo/ENVO_00002982";
    /* gravel: Tubes/burrows constructed of gravel, an environmental material which is composed of pieces
    of rock that are at least two millimeters (2 mm) in its largest dimension and no more than 75 millimeters.*/
    public final static String TUBE_ENVO_01000018_TOL = "http://purl.obolibrary.org/obo/ENVO_01000018";
    /* sand: Tubes/burrows constructed of sand, a naturally occurring granular material composed of
    finely divided rock and mineral particles.*/
    public final static String TUBE_ENVO_01000017 = "http://purl.obolibrary.org/obo/ENVO_01000017";
    /* biogenic detritus: Tubes/burrows constructed of dead skeleton materials found in the environment (e.g. shells, algal parts).*/
    public final static String TUBE_BIO = "http://polytraits.lifewatchgreece.eu/terms/TUBE_BIO";
    /* secretions : Tubes/burrows constructed of bodily secretions, usually mucus.*/
    public final static String TUBE_ENVO_02000040 = "http://purl.obolibrary.org/obo/ENVO_02000040";
    /* calcium carbonate: Tubes constructed of calcium carbonate.*/
    public final static String TUBE_CALC = "http://polytraits.lifewatchgreece.eu/terms/TUBE_CALC";
    /* mud : Tubes/burrows constructed of mud, a liquid or semi-liquid mixture of water and fine particles of silt and/or clay <0.063 mm; the silt/clay fraction is >80% [1292].*/
    public final static String TUBE_MUD = "http://polytraits.lifewatchgreece.eu/terms/MUD";
    /*==================================================================================*/
    /**
     * Typically feeds on: The type of food an organism prefers.
     */
    public final static String preysUpon = "http://eol.org/schema/terms/preysUpon";
    /**
     * Modalities for preysUpon
     */
    /* algae: Algae as food source.*/
    public final static String preysUpon_TF_ALG = "http://polytraits.lifewatchgreece.eu/terms/TF_ALG";
    /* bacteria: Bacteria as food source.*/
    public final static String preysUpon_TF_BACT = "http://polytraits.lifewatchgreece.eu/terms/TF_BACT";
    /* anellids: Annelida as food source*/
    public final static String preysUpon_TF_ANN = "http://polytraits.lifewatchgreece.eu/terms/TF_ANN";
    /* ciliates: Ciliates as food source.*/
    public final static String preysUpon_TF_CIL = "http://polytraits.lifewatchgreece.eu/terms/TF_CIL";
    /* crustaceans : Crustaceans as food source.*/
    public final static String preysUpon_TF_CRUS = "http://polytraits.lifewatchgreece.eu/terms/TF_CRUS";
    /* diatoms: Diatoms as food source.*/
    public final static String preysUpon_TF_DIAT = "http://polytraits.lifewatchgreece.eu/terms/TF_DIAT";
    /* flagellates: Flagellates as food source.*/
    public final static String preysUpon_TF_FLAG = "http://polytraits.lifewatchgreece.eu/terms/TF_FLAG";
    /* foraminiferans: Foraminiferans as food source. */
    public final static String preysUpon_TF_FORAM = "http://polytraits.lifewatchgreece.eu/terms/TF_FORAM";
    /* detritus: Particles of organic material from dead and decomposing organisms as food source.*/
    public final static String preysUpon_TF_OM = "http://polytraits.lifewatchgreece.eu/terms/TF_OM";
    /* sediment: Unselective ingestion of sediment.*/
    public final static String preysUpon_TF_SED = "http://polytraits.lifewatchgreece.eu/terms/TF_SED";
    /* fish: Fish, incl. their larvae, as food source.*/
    public final static String preysUpon_TF_FISH = "http://polytraits.lifewatchgreece.eu/terms/TF_FISH";
    /* ascidians:Ascidians, incl. their larvae, as food source.*/
    public final static String preysUpon_TF_ASC = "http://polytraits.lifewatchgreece.eu/terms/TF_ASC";
    /* echinoderms:Echinoderms, incl. their larvae, as food source.*/
    public final static String preysUpon_TF_ECHI = "http://polytraits.lifewatchgreece.eu/terms/TF_ECHI";
    /* cnidarians: Cnidarians as food source.*/
    public final static String preysUpon_TF_CNID = "http://polytraits.lifewatchgreece.eu/terms/TF_CNID";

    /**
     * =========================================================================
     * Reproductive traits
     * =========================================================================
     */
    /**
     * Age at first reproduction: Beginning of the first sexual reproductive
     * cycle. Defined as the first ripening of gametes.
     */
    public final static String MAT = "http://polytraits.lifewatchgreece.eu/terms/MAT";
    /**
     * Modalities for MAT
     */
    /* <=2 months: Reproductive maturity reached at an age younger than 2 months.*/
    public final static String MAT_2M = "http://polytraits.lifewatchgreece.eu/terms/MAT_2M";
    /* 2 - 6 months: Reproductive maturity reached at an age between 2 to 6 months.*/
    public final static String MAT_6M = "http://polytraits.lifewatchgreece.eu/terms/MAT_6M";
    /* 6 months - 1year: Reproductive maturity reached at an age between 6 months to a year.*/
    public final static String MAT_1Y = "http://polytraits.lifewatchgreece.eu/terms/MAT_1Y";
    /* 1-2 years: Reproductive maturity reached at an age between 1 to 2 years.*/
    public final static String MAT_2Y = "http://polytraits.lifewatchgreece.eu/terms/MAT_2Y";
    /* 2-3 years : Reproductive maturity reached at an age between 2 to 3 years.*/
    public final static String MAT_3Y = "http://polytraits.lifewatchgreece.eu/terms/MAT_3Y";
    /* 3-4 years: Reproductive maturity reached at an age between 3 to 4 years.*/
    public final static String MAT_4Y = "http://polytraits.lifewatchgreece.eu/terms/MAT_4Y";
    /* >4 years: Reproductive maturity reached at an age more than 4 years.*/
    public final static String MAT_MANY = "http://polytraits.lifewatchgreece.eu/terms/MAT_MANY";

    /*==================================================================================*/
    /**
     * Developmental mechanism: The mechanism of the development of the
     * embryo(s), inside or outside of the parental organism.
     */
    public final static String DEV = "http://polytraits.lifewatchgreece.eu/terms/DEV";
    /**
     * Modalities for DEV
     */
    /* oviparus: Reproduction in which eggs are released by the female; development of offspring occurs outside the mother's body.*/
    public final static String DEV_D052287 = "http://purl.bioontology.org/ontology/MESH/D052287";
    /* viviparus:Reproduction in which fertilization and development take place within the female body and
    the developing embryo derives nourishment from the female.*/
    public final static String DEV_D052286 = "http://purl.bioontology.org/ontology/MESH/D052286";
    /*==================================================================================*/
    /**
     * Egg size: Diameter of the eggs spawned by an organism.
     */
    public final static String EGG = "http://polytraits.lifewatchgreece.eu/terms/EGG";
    /**
     * Modalities for EGG
     */
    /* 0um-100um: Egg diameter up to 100um.*/
    public final static String EGG_S = "http://polytraits.lifewatchgreece.eu/terms/EGG_S";
    /* 100um-200um: Egg diameter from 100um to 200um.*/
    public final static String EGG_M = "http://polytraits.lifewatchgreece.eu/terms/EGG_M";
    /* > 200um: Egg diameter larger than 200um.*/
    public final static String EGG_L = "http://polytraits.lifewatchgreece.eu/terms/EGG_L";
    /*==================================================================================*/
    /**
     * Epitoky: Form of reproduction of marine polychates in which the new
     * individual arises by modification and separation from the posterior end
     * of the worm in order to leave the bottom and reproduce [1292].
     */
    public final static String EPKY = "http://polytraits.lifewatchgreece.eu/terms/EPKY";
    /**
     * Modalities for EGG
     */
    /* yes: The organism undergoes epitokous metamorphosis.*/
    public final static String EPKY_YES = "http://polytraits.lifewatchgreece.eu/terms/EPKY_YES";
    /* no: The organism does not undergo epitokous metamorphosis.*/
    public final static String EPKY_NO = "http://polytraits.lifewatchgreece.eu/terms/EPKY_NO";
    /*==================================================================================*/
    /**
     * Factors triggering reproduction:Factors that can initiate or enhance
     * reproduction.
     */
    public final static String FAC = "http://polytraits.lifewatchgreece.eu/terms/FAC";
    /**
     * Modalities for FAC
     */
    /* lunar cycle: Reproduction which is timed to particular phases of the lunar
    cycle (or the semilunar cycle of spring and neap tides) [1325].*/
    public final static String FAC_LUN = "http://polytraits.lifewatchgreece.eu/terms/FAC_LUN";
    /* pheromones/hormones: Spawning as a result of a pheromonal interaction between swarming males and females.
    Hormonal factors may be involved not only in the timing of reproduction but also in sexual differentiation [1325].*/
    public final static String FAC_HOR = "http://polytraits.lifewatchgreece.eu/terms/FAC_HOR";
    /* photoperiod: Reproduction which is timed to a particular daylight length [1325].*/
    public final static String FAC_PHO = "http://polytraits.lifewatchgreece.eu/terms/FAC_PHO";
    /* temperature: Reproduction which is controlled by a change in water temperature.
    In some species, a certain temperature is a prerequisite for reproduction to occur [1325].*/
    public final static String FAC_TMP = "http://polytraits.lifewatchgreece.eu/terms/FAC_TMP";
    /* salinity:Reproduction which is stimulated by changes in salinity [1326].*/
    public final static String FAC_SAL = "http://polytraits.lifewatchgreece.eu/terms/FAC_SAL";
    /*==================================================================================*/
    /**
     * Fecundity:The potential reproductive capacity of an organism or
     * population, measured by the number of gametes (eggs) or asexual
     * propagules [1292].
     */
    public final static String PATO_0000273 = "http://purl.obolibrary.org/obo/PATO_0000273";
    /**
     * Modalities for PATO_0000273
     */
    /* 1-50: Up to 50 eggs per female and reproductive event.*/
    public final static String PATO_0000273_FEC_50 = "http://polytraits.lifewatchgreece.eu/terms/FEC_50";
    /* 50-500: From 50 to 500 eggs per female and reproductive event.*/
    public final static String PATO_0000273_FEC_500 = "http://polytraits.lifewatchgreece.eu/terms/FEC_500";
    /* 500-2,500: From 500 to 2,500 eggs per female and reproductive event.*/
    public final static String PATO_0000273_FEC_2500 = "http://polytraits.lifewatchgreece.eu/terms/FEC_2500";
    /* 2,500-10,000: From 2,500 to 10,000 eggs per female and reproductive event.*/
    public final static String PATO_0000273_FEC_10000 = "http://polytraits.lifewatchgreece.eu/terms/FEC_10000";
    /* 10,000-20,000: From 10,000 to 20,000 eggs per female and reproductive event.*/
    public final static String PATO_0000273_FEC_20000 = "http://polytraits.lifewatchgreece.eu/terms/FEC_20000";
    /* 20,000-100,000: From 20,000 to 100,000 per female and reproductive event.*/
    public final static String PATO_0000273_FEC_100000 = "http://polytraits.lifewatchgreece.eu/terms/FEC_100000";
    /* >100,000: More than 100,000 eggs per female and reproductive event.*/
    public final static String PATO_0000273_FEC_MORE_THAN_100000 = "http://polytraits.lifewatchgreece.eu/terms/FEC_100000+";
    /*==================================================================================*/
    /**
     * Fertilization type:Location of the union of male and female gametes.
     */
    public final static String FER = "http://polytraits.lifewatchgreece.eu/terms/FER";
    /**
     * Modalities for FER
     */
    /* internal: Fertilization takes place within the female's body.*/
    public final static String FER_INT = "http://polytraits.lifewatchgreece.eu/terms/FER_INT";
    /* external (broadcast spawner): A method of reproduction during which the gametes (egg and sperm) unite outside the body.*/
    public final static String FER_EXT = "http://polytraits.lifewatchgreece.eu/terms/FER_EXT";
    /* external (pseudocopulation): A form of external fertilization where the partners are in close contact [544].*/
    public final static String FER_PSEU = "http://polytraits.lifewatchgreece.eu/terms/FER_PSEU";
    /*==================================================================================*/
    /**
     * Mode of reproduction: The production by an organism of new individuals
     * that contain some portion of their genetic material inherited from that
     * organism.
     */
    public final static String GO_0000003 = "http://purl.obolibrary.org/obo/GO_0000003";
    /**
     * Modalities for GO_0000003
     */
    /* gonochoristic: Having separate sexes throughout life [1292].*/
    public final static String GO_0000003_HAO_0000048 = "http://purl.obolibrary.org/obo/HAO_0000048";
    /* simultaneuous hermaphrodita: Condition of hermaphroditic animals (and plants)
    in which the reproductive organs of both sexes are present and functional at the same time.*/
    public final static String GO_0000003_HAO_0000046 = "http://purl.obolibrary.org/obo/HAO_0000046";
    /* sequential hermaphrodita: Sequential hermaphrodites are born as one sex, but can later change into the opposite sex.*/
    public final static String GO_0000003_HAO_0000045 = "http://purl.obolibrary.org/obo/HAO_0000045";
    /* asexual reproduction: Reproduction that is not sexual; that is, reproduction that does not include
    recombining the genotypes of two parents.*/
    public final static String GO_0000003_GO_0019954 = "http://purl.obolibrary.org/obo/GO_0019954";
    /*==================================================================================*/
    /**
     * Pattern of oogenesis: Process of germ cell development in the female from
     * the primordial germ cells through oogonia to the mature haploid ova.
     */
    public final static String CSP_1138_4873 = "http://purl.bioontology.org/ontology/CSP/1138-4873";
    /**
     * Modalities for CSP_1138_4873`
     */
    /* intraovarian: Occurs when oocytes are retained by the ovary until most or all of oogenesis (and vitellogenesis) is completed.
    Ovaries are usually large, structurally complex, and persistent throughout the sexual phase of the female [544].*/
    public final static String CSP_1138_4873_OOG_INTRA = "http://purl.obolibrary.org/obo/OOG_INTRA";
    /* extraovarian: Occurs when small, previtellogenic oocytes are released form the ovary where they complete vitellogenesis in the fluid-filled coelom.
    Ovaries are generally small, simple and sometimes have a transient nature [544].*/
    public final static String CSP_1138_4873_OOG_EXTRA = "http://polytraits.lifewatchgreece.eu/terms/OOG_EXTRA";
    /*==================================================================================*/
    /**
     * Population sex ration: The ratio of male to female (or vice versa) in a
     * population.
     */
    public final static String EFO_0004820 = "http://www.ebi.ac.uk/efo/EFO_0004820";
    /**
     * Modalities for EFO_0004820
     */
    /* 1:1: The ratio of female to male in the population is 1 to 1.*/
    public final static String EFO_0004820_PSR_EQ = "http://polytraits.lifewatchgreece.eu/terms/PSR_EQ";
    /* female>male: The number of females is higher in a population.*/
    public final static String EFO_0004820_PSR_F = "http://polytraits.lifewatchgreece.eu/terms/PSR_F";
    /* female<male: The number of males is higher in a population.*/
    public final static String EFO_0004820_PSR_M = "http://polytraits.lifewatchgreece.eu/terms/PSR_M";
    /*==================================================================================*/
    /**
     * Reproduction strategy of the individual: Number of times an individual
     * can reproduce during its lifetime.
     */
    public final static String STRAT = "http://polytraits.lifewatchgreece.eu/terms/STRAT";
    /**
     * Modalities for STRAT
     */
    /* iteroparous: Breeding several times per lifetime.*/
    public final static String STRAT_ITER = "http://polytraits.lifewatchgreece.eu/terms/STRAT_ITER";
    /* semelparous: Organisms that have only one brood during the life time and then the parent usually dies. */
    public final static String STRAT_SEM = "http://polytraits.lifewatchgreece.eu/terms/STRAT_SEM";
    /*==================================================================================*/
    /**
     * Reproduction temperature: Water temperature that initiates or enhances
     * reproduction.
     */
    public final static String RT = "http://polytraits.lifewatchgreece.eu/terms/RT";
    /**
     * Modalities for RT
     */
    /* cold waters : Reproduction in cold water environments (<0 - 10 °C).*/
    public final static String RT_COLD = "http://polytraits.lifewatchgreece.eu/terms/RT_COLD";
    /* warm/ temperate/ subtropical waters : Reproduction in environments of average temperatures (10-25°C).*/
    public final static String RT_TMP = "http://polytraits.lifewatchgreece.eu/terms/RT_TMP";
    /* tropical waters: Reproduction in warm water environments (>25 °C). */
    public final static String RT_WARM = "http://polytraits.lifewatchgreece.eu/terms/RT_WARM";
    /*==================================================================================*/
    /**
     * Resorption of eggs: Ability to resorb all or part of the gametes instead
     * of spawning them normally.
     */
    public final static String RESORP = "http://polytraits.lifewatchgreece.eu/terms/RESORP";
    /**
     * Modalities for RT
     */
    /* yes : Organisms that have the ability of egg resorption.*/
    public final static String RESORP_YES = "http://polytraits.lifewatchgreece.eu/terms/RESORP_YES";
    /* no: Organisms that do not have the ability of egg resorption.*/
    public final static String RESORP_NO = "http://polytraits.lifewatchgreece.eu/terms/RESORP_NO";
    /*==================================================================================*/
    /**
     * Sexual metamorphosis: Conspicuous change in the organism's body structure
     * prior to reproduction.
     */
    public final static String SM = "http://polytraits.lifewatchgreece.eu/terms/SM";
    /**
     * Modalities for SM
     */
    /* yes: Organisms that undergo sexual metamorphosis.*/
    public final static String SM_YES = "http://polytraits.lifewatchgreece.eu/terms/SM_YES";
    /* no: Organisms that do not undergo sexual metamorphosis. */
    public final static String SM_NO = "http://polytraits.lifewatchgreece.eu/terms/SM_NO";
    /*==================================================================================*/
    /**
     * Spawning frequency of the population: Period and frequency of spawning in
     * a population.
     */
    public final static String FREQ = "http://polytraits.lifewatchgreece.eu/terms/FREQ";
    /**
     * Modalities for FREQ
     */
    /* multiple events/year: More than once per year, but in relatively defined peaks or intense periods
    that do not fall within a drawn-out period.*/
    public final static String FREQ_MULTI = "http://polytraits.lifewatchgreece.eu/terms/FREQ_MULTI";
    /* continous or semicontinous: Reproduction occurs all year round or for the most part of the year. */
    public final static String FREQ_CONT = "http://polytraits.lifewatchgreece.eu/terms/FREQ_CONT";
    /* annually/seasonally: Yearly over a drawn out period of several weeks or few months, or always in a defined season,
    peaks or epidemic swarming can occur within this period. */
    public final static String FREQ_ANNU = "http://polytraits.lifewatchgreece.eu/terms/FREQ_ANNU";
    /*==================================================================================*/
    /**
     * Sperm type: Different types of sperm that occur in organisms and
     * fertilize the eggs.
     */
    public final static String SPERM = "http://polytraits.lifewatchgreece.eu/terms/SPERM";
    /**
     * Modalities for SPERM
     */
    /* ect-aquasperm: Type of sperm that are released into the water and fertilize similarly released eggs [400].*/
    public final static String SPERM_ECT = "http://polytraits.lifewatchgreece.eu/terms/SPERM_ECT";
    /* ent-aquasperm: Type of sperm that are released freely into the ambient water but differ
    from ect-aquasperm in being gathered by, or in some other way reaching, the female [400]. */
    public final static String SPERM_ENT = "http://polytraits.lifewatchgreece.eu/terms/SPERM_ENT";
    /* introspect: Have no contact with water when passed from male to female [400]. */
    public final static String SPERM_INTRO = "http://polytraits.lifewatchgreece.eu/terms/SPERM_INTRO";
    /*==================================================================================*/
    /**
     * Synchronization of spawning: Level of synchronization of the reproductive
     * activity in a population.
     */
    public final static String SYNC = "http://polytraits.lifewatchgreece.eu/terms/SYNC";
    /**
     * Modalities for SYNC
     */
    /* yes: Organisms whose populations undergo through a synchronized ripening of the gametes,
    usually culminating in an epidemic spawning event.*/
    public final static String SYNC_YES = "http://polytraits.lifewatchgreece.eu/terms/SYNC_YES";
    /* no: Organisms whose populations do not undergo through a synchronized ripening of the gametes.*/
    public final static String SYNC_NO = "http://polytraits.lifewatchgreece.eu/terms/SYNC_NO";
    /**
     * =========================================================================
     * Larval traits
     * =========================================================================
     */
    /*==================================================================================*/
    /**
     * Habitat type of settlement/ early development: Habitat type of the larval
     * settlement and early development after metamorphosis.
     */
    public final static String HSET = "http://polytraits.lifewatchgreece.eu/terms/HSET";
    /**
     * Modalities for HSET
     */
    /* algae: Macroalgae surfaces, such as Laminaria spp. or fucoids..*/
    public final static String HSET_HAB_ALG = "http://polytraits.lifewatchgreece.eu/terms/HAB_ALG";
    /* biogenic reef: Solid, massive structure which is created by accumulations of organisms,
    usually rising from the seabed, or at least clearly forming a substantial, discrete community or
    habitat which is very different from the surrounding seabed. The structure of the reef may be
    composed almost entirely of the reef building organism and its tubes or shells, or it may to some
    degree be composed of sediments, stones and shells bound together by the organisms [1330].*/
    public final static String HSET_HAB_BIO = "http://polytraits.lifewatchgreece.eu/terms/HAB_BIO";
    /* caves: A hollow normally eroded in a cliff, with the penetration being greater than the width at the entrance [1297]).
    Caves can also be formed by boulders [1292] */
    public final static String HSET_HAB_CAVE = "http://polytraits.lifewatchgreece.eu/terms/HAB_CAVE";
    /* crevices/ fissures: Crevices are narrow cracks in a hard substratum <10 mm wide at its entrance,
    with the penetration being greater than the width at the entrance.
    Fissures are cracks in a hard substratum >10 mm wide at its entrance, with the depth being greater
    than the width at the entrance*/
    public final static String HSET_HAB_CREV = "http://polytraits.lifewatchgreece.eu/terms/HAB_CREV";
    /* maerl/ coralligenous habitats: A coralligenous habitat is defined by the presence of a bioherm of coralline algae grown at
    low irradiance levels and in relatively calm waters [1119]. Maerl denotes loose-lying, normally non-geniculate (i.e. not jointed),
    coralline red algae. Depending on the terminology used, maerl refers either to a class of rhodoliths, or may be considered distinct
    from rhodoliths in lacking a non-algal core. Maerl beds are composed of living or dead unattached corallines forming accumulations
    with or without terrigenous material*/
    public final static String HSET_HAB_MAERL = "http://polytraits.lifewatchgreece.eu/terms/HAB_MAERL";
    /* other species: Epibiont of other species.*/
    public final static String HSET_ENVO_00002032 = "http://purl.obolibrary.org/obo/ENVO_00002032";
    /* overhangs: An overhanging part of a rock formation.*/
    public final static String HSET_HAB_OH = "http://polytraits.lifewatchgreece.eu/terms/HAB_OH";
    /* rockpools: A depression in the littoral zone of a rocky seashore, where, during low tide, seawater is left behind.*/
    public final static String HSET_ENVO_00000317 = "http://purl.obolibrary.org/obo/ENVO_00000317";
    /* salt marsh: A marsh whose water contains a considerable quantity of dissolved salts.*/
    public final static String HSET_ENVO_00000054 = "http://purl.obolibrary.org/obo/ENVO_00000054";
    /* seagrass: Habitat associated with seagrass meadows communities. Seagrasses are flowering plants
    that are adapted to living fully submerged and rooted in estuarine and marine environments.*/
    public final static String HSET_ENVO_01000059 = "http://purl.obolibrary.org/obo/ENVO_01000059";
    /* strandline: A line on the shore composing debris deposited by a receding tide; commonly used
    to denote the line of debris at the level of extreme high water*/
    public final static String HSET_HAB_STRAND = "http://polytraits.lifewatchgreece.eu/terms/HAB_STRAND";
    /*  under boulders: Under unattached rocks that can be very large (>1024 mm), large (512 - 1024 mm)
    or small (256 - 512 mm) [1292].*/
    public final static String HSET_HAB_UB = "http://polytraits.lifewatchgreece.eu/terms/HAB_UB";
    /*  water column: Pelagic habitat.*/
    public final static String HSET_ENVO_01000023_STRUCT = "http://purl.obolibrary.org/obo/ENVO_01000023";
    /*  soft sediments:Deposits with a high water content (near or above the liquid limit) where the
    percolating skeleton is made of fine-grained soils (clay fraction above ~ 20%), with a high degree
    of saturation, and subjected to low effective confinement [1336]*/
    public final static String HSET_ENVO_00002007 = "http://purl.obolibrary.org/obo/ENVO_00002007";
    /*==================================================================================*/
    /**
     * Junevile mobility: The capability of a juvenile to move spontaneously and
     * freely.
     */
    public final static String JMOB = "http://polytraits.lifewatchgreece.eu/terms/JMOB";
    /**
     * Modalities for JMOB
     */
    /* crawler: An organism that moves along on the substratum via movements of its legs, appendages
    (e.g. parapodia and chaetae) or muscles [1292].*/
    public final static String JMOB_CRAWL = "http://polytraits.lifewatchgreece.eu/terms/MOB_CRAWL";
    /* burrower:  An organism that lives or moves in a burrow in soft sediments.*/
    public final static String JMOB_BUR = "http://polytraits.lifewatchgreece.eu/terms/MOB_BUR";
    /* swimmer: An organism that moves through the water column via movements of its fins, legs or
    appendages, via undulatory movements of the body or via jet propulsion; includes pelagic phases
    during reproduction (swarming at the surface) [1292].*/
    public final static String JMOB_SWIM = "http://polytraits.lifewatchgreece.eu/terms/MOB_SWIM";
    /* non-motile / semi-motile: Permanently attached to a substratum (non-motile) or capable of
    movement across (or through) it (semi-motile).*/
    public final static String JMOB_SESS = "http://polytraits.lifewatchgreece.eu/terms/MOB_SESS";
    /*==================================================================================*/
    /**
     * Larval development: The mode of development from the larval to the adult
     * stage.
     */
    public final static String LDEV = "http://polytraits.lifewatchgreece.eu/terms/LDEV";
    /**
     * Modalities for DEV
     */
    /* direct development: There are no intermediate larval stage(s) or postembryonic metamorphoses of any kind.
    Embryonic development culminates in the hatching or birth of a fully formed, albeit miniature adult [1316].*/
    public final static String LDEV_directDeveloper = "http://eol.org/schema/terms/directDeveloper";
    /* indirect development:One or more successive, free-living larval stages intervene between embryo and adult,
    and there is a more-or-less abrupt transition,
    or metamorphosis, between the last larval stage and the adult [1316].*/
    public final static String LDEV_I = "http://polytraits.lifewatchgreece.eu/terms/LDEV_I";
    /*==================================================================================*/
    /**
     * Larval feeding type: The existence of two distinct larval types, feeding
     * and non-feeding, has established the current paradigm for larval ecology.
     * Feeding larvae are larvae that can capture and use exogenous food,
     * whereas non-feeding larvae are larvae that cannot capture or use
     * exogenous food.
     */
    public final static String MarineLarvalDevelopmentStrategy = "http://eol.org/schema/terms/MarineLarvalDevelopmentStrategy";
    /**
     * Modalities for MarineLarvalDevelopmentStrategy
     */
    /* planktotropic: A larval development strategy in which small eggs are converted into larger juveniles
    by means of larval feeding and growth [1299].*/
    public final static String MarineLarvalDevelopmentStrategy_planktotrophic = "http://eol.org/schema/terms/planktotrophic";
    /* maternally derived nutrition: “Umbrella term” describing the maternal sources of nutrition
    and including the terms lecithotrophy, adelphophagy, and translocation of nutrients.*/
    public final static String MarineLarvalDevelopmentStrategy_LFT_M = "http://polytraits.lifewatchgreece.eu/terms/LFT_M";
    /*==================================================================================*/
    /**
     * Larval mode of development: Larvae development in the water column or
     * on/in soft- or hard-bottom substrata
     */
    public final static String LM = "http://polytraits.lifewatchgreece.eu/terms/LM";
    /**
     * Modalities for LM
     */
    /* benthic: Development on or near the bottom of a body of water.*/
    public final static String LM_B = "http://polytraits.lifewatchgreece.eu/terms/LM_B";
    /* pelagic: Development in the water column.    */
    public final static String LM_P = "http://polytraits.lifewatchgreece.eu/terms/LM_P";
    /*==================================================================================*/
    /**
     * Location of parental care: Defines the location of the parental care (if
     * provided), either near the body of the parent or at a distance from it.
     */
    public final static String PC = "http://polytraits.lifewatchgreece.eu/terms/PC";
    /**
     * Modalities for PC
     */
    /* outside microenvironment of the parent : Parental care is provided through e.g. protective structures,
    but not on the body of the parent or in its immediate living environment (e.g. in a burrow, tube or nest).*/
    public final static String PC_FAR = "http://polytraits.lifewatchgreece.eu/terms/PC_FAR";
    /* within microenvironment of the parent : Parental care is provided either on the body of the parent or in
    its immediate living environment (e.g. in a burrow, tube or nest). */
    public final static String PC_NEAR = "http://polytraits.lifewatchgreece.eu/terms/PC_NEAR";
    /*==================================================================================*/
    /**
     * Metamorphosis type: Generally, any anatomical remodelling between
     * opposing life-history periods, i.e. between the larval and the adult
     * stage, can be considered as a form of metamorphosis [291] [1318]. These
     * changes can be rapid and cataclysmic, or can proceed gradually, depending
     * on the particular developmental basis for the juvenile body plan within
     * the body of the larva [1302].
     */
    public final static String GO_0007552 = "http://purl.obolibrary.org/obo/GO_0007552";
    /**
     * Modalities for GO_0007552
     */
    /* catastrophic: The metamorphosis is accompanied by massive internal change coupled with catastrophic
    destruction of the larval tissues. Huge chunks of the larval body, its tissues and organs, are digested
    away and reabsorbed, or simply discarded [1322].*/
    public final static String GO_0007552_MV_CAT = "http://polytraits.lifewatchgreece.eu/terms/MV_CAT";
    /*  non-catastrophic: The adult develops from the juvenile through a process of extension and differential
    growth, including different larval stages but without a drastic change of the body plan. */
    public final static String GO_0007552_MV_NCAT = "http://polytraits.lifewatchgreece.eu/terms/MV_NCAT";
    /*==================================================================================*/
    /**
     * Parental care blood: Any parental trait that enhances the fitness of a
     * parent’s offspring after the offspring is released from the female body
     * [1317]. Viviparity and other forms of lecitotrophy are excluded here from
     * this definition and not considered as forms of parental care.
     */
    public final static String GO_0060746 = "http://purl.obolibrary.org/obo/GO_0060746";
    /**
     * Modalities for GO_0060746
     */
    /* yes:“Umbrella term”. Used to capture information that a species provides parental care to its offspring.*/
    public final static String GO_0060746_BP_YES = "http://polytraits.lifewatchgreece.eu/terms/BP_YES";
    /*  no: Used to capture information that a species does not provide parental care to its offspring beyond
    supplying them with a small package of yolk that serves as an initial source of nutrition until the
    offspring are fully capable of fending for themselves [1317]. */
    public final static String GO_0060746_BP_NO = "http://polytraits.lifewatchgreece.eu/terms/BP_NO";
    /*==================================================================================*/
    /**
     * Substrate type of settlement: Settlement is defined as the behavioural
     * performance when pelagic larvae descend from the plankton to the benthos,
     * and move upon the substratum with or without attaching to it. Settlement
     * is reversible: a larva can swim up again from the substrate to resettle
     * at another location [1298]. The surface on which larvae choose to settle
     * is defined as the substrate of settlement.
     */
    public final static String SETTL = "http://polytraits.lifewatchgreece.eu/terms/SETTL";
    /**
     * Modalities for SETTL
     */
    /* hard substrate: One or more successive, free-living larval stages intervene
    between embryo and adult, with a more-or-less abrupt transition, or metamorphosis,
    between the last larval stage and the adult [1316].*/
    public final static String SETTL_HARD = "http://polytraits.lifewatchgreece.eu/terms/SETTL_HARD";
    /* sand:Particles defined in three size categories: very coarse sand and granules (1 - 4 mm);
    medium and coarse sand (0.25 - 1 mm); very fine and fine sand (0.063 - 0.25 mm) [1292].*/
    public final static String SETTL_SAND = "http://polytraits.lifewatchgreece.eu/terms/SETTL_SAND";
    /* mud: Fine particles of silt and/or clay <0.063 mm; the silt/clay fraction is > 80% [1292].*/
    public final static String SETTL_MUD = "http://polytraits.lifewatchgreece.eu/terms/MUD";
    /* clay: Sediment characterised by an average particle diameter between 1 and 3.9 micrometers.*/
    public final static String SETTL_ENVO_01000120 = "http://purl.obolibrary.org/obo/ENVO_01000120";
    /* silt: Sediment characterised by an average particle diameter between 3.9 and 63 micrometers.*/
    public final static String SETTL_ENVO_01000119 = "http://purl.obolibrary.org/obo/ENVO_01000119";
    /* gravel: An environmental material which is composed of pieces of rock that are at least two millimeters
    (2 mm) in its largest dimension and no more than 75 millimeters.*/
    public final static String SETTL_ENVO_01000018 = "http://purl.obolibrary.org/obo/ENVO_01000018";
    /* pebbles: Sediment characterised by an average particle diameter between 4 and 64 mm.*/
    public final static String SETTL_ENVO_01000116 = "http://purl.obolibrary.org/obo/ENVO_01000116";
    /* cobbles: Sediment characterised by an average particle diameter between 64 and 256 mm.*/
    public final static String SETTL_ENVO_01000115 = "http://purl.obolibrary.org/obo/ENVO_01000115";
    /* boulders: Sediment characterised by an average particle diameter greater than 256 mm*/
    public final static String SETTL_ENVO_01000114 = "http://purl.obolibrary.org/obo/ENVO_01000114";
    /* bacterial/ organic biofilm:A complex aggregation of microorganisms marked by the excretion of a protective
    and adhesive matrix; usually adhering to a substratum.*/
    public final static String SETTL_ENVO_00002034 = "http://purl.obolibrary.org/obo/ENVO_00002034";
}
