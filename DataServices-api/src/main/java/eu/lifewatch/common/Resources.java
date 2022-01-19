package eu.lifewatch.common;

/**
 * @author Yannis Marketakis (marketak 'at' ics 'dot' forth 'dot' gr)
 */
public class Resources {
    /*labels for classes and properties */

    public static final String tloNamespace = "http://www.ics.forth.gr/isl/ontology/MarineTLO/";
    public static final String cidocCrmNamespace = "http://www.cidoc-crm.org/cidoc-crm/";
    public static final String crmSciNamespace = "http://www.ics.forth.gr/isl/CRMsci/";
    public static final String crmDigNamespace = "http://www.ics.forth.gr/isl/CRMdig/";
    public static final String frbrNamespace = "http://www.ics.forth.gr/isl/FRBR/";
    public static final String rdfsNamespace = "http://www.w3.org/2000/01/rdf-schema#";
    /*cidoc-crm classes and properties*/

    public static final String actorLabel = cidocCrmNamespace + "E39_Actor";
    public static final String personLabel = cidocCrmNamespace + "E21_Person";
    public static final String measurementEventLabel = cidocCrmNamespace + "E16_Measurement";
    public static final String creationEventLabel = cidocCrmNamespace + "E65_Creation";
    public static final String dimensionLabel = cidocCrmNamespace + "E54_Dimension";
    public static final String measurementUnitLabel = cidocCrmNamespace + "E58_Measurement_Unit";
    public static final String identificationEventLabel = cidocCrmNamespace + "E17_Type_Assignment";
    public static final String transformationEventLabel = cidocCrmNamespace + "E81_Transformation";
    public static final String attributeAssignmentEventLabel = cidocCrmNamespace + "E13_Attribute_Assignment";
    public static final String scientificNameAssignmentEventLabel = cidocCrmNamespace + "E15_Identifier_Assignment";
    public static final String physicalObjectLabel = cidocCrmNamespace + "E19_Physical_Object";
    public static final String institutionLabel = cidocCrmNamespace + "E40_Legal_Body";
    public static final String appellationLabel = cidocCrmNamespace + "E41_Appellation";
    public static final String typeLabel = cidocCrmNamespace + "E55_Type";
    public static final String languageLabel = cidocCrmNamespace + "E56_Language";
    public static final String collectionLabel = cidocCrmNamespace + "E78_Collection";
    public static final String placeLabel = cidocCrmNamespace + "E53_Place";
    public static final String accessRightsLabel = cidocCrmNamespace + "E30_Rights";
    public static final String imageLabel = cidocCrmNamespace + "E38_Image";
    public static final String procedureLabel = cidocCrmNamespace + "E29_Design_or_Procedure";
    public static final String identifierLabel = cidocCrmNamespace + "E42_Identifier";
    public static final String conditionStateLabel = cidocCrmNamespace + "E3_Condition_State";
    public static final String conceptualObjectLabel = cidocCrmNamespace + "E28_ConceptualObject";
    public static final String timespanLabel = cidocCrmNamespace + "E52_Time-Span";

    public static final String hasLanguage = cidocCrmNamespace + "P72_has_language";
    public static final String hasNote = cidocCrmNamespace + "P3_has_note";
    public static final String consistsOf = cidocCrmNamespace + "P9_consists_of";
    public static final String hasCurrentKeeper = cidocCrmNamespace + "P49_has_current_keeper";
    public static final String hasCurrentOwner = cidocCrmNamespace + "P52_has_current_owner";
    public static final String hasContactPoint = cidocCrmNamespace + "P76_has_contact_point";
    public static final String rightsHeldBy = cidocCrmNamespace + "P105_rights_held_by";
    public static final String isSubjectTo = cidocCrmNamespace + "P104_is_subject_to";
    public static final String isComposedOf = cidocCrmNamespace + "P46_is_composed_of";
    public static final String hasTimespan = cidocCrmNamespace + "P4_has_timespan";
    public static final String atSomeTimeWithin = cidocCrmNamespace + "P82_at_some_time_within";
    public static final String hadAtMostDuration = cidocCrmNamespace + "P84_had_at_most_duration";
    public static final String beginOfTheBegin = cidocCrmNamespace + "P82a_begin_of_the_begin";
    public static final String endOfTheEnd = cidocCrmNamespace + "P82b_end_of_the_end";
    public static final String usedObjectOfType = cidocCrmNamespace + "P125_used_object_of_type";
    public static final String isIdentifiedBy = cidocCrmNamespace + "P1_is_identified_by";
    public static final String isDepictedBy = cidocCrmNamespace + "P62_is_depicted_by";
    public static final String hasPreferredIdentifier = tloNamespace + "P48_has_preferred_identifier";
    public static final String hasType = cidocCrmNamespace + "P2_has_type";
    public static final String carriedOutBy = cidocCrmNamespace + "P14_carried_out_by";
    public static final String fallsWithin = cidocCrmNamespace + "P106_falls_within";
    public static final String refersTo = cidocCrmNamespace + "P67_refers_to";
    public static final String isReferredToBy = tloNamespace + "P67_is_referred_to_by";
    public static final String isPossessedBy = tloNamespace + "P75_is_possessed_by";

    public static final String measured = cidocCrmNamespace + "P39_measured";
    public static final String observedDimension = cidocCrmNamespace + "P40_observed_dimension";
    public static final String hasValue = cidocCrmNamespace + "P90_has_value";
    public static final String hadSpecificPurpose = cidocCrmNamespace + "P20_had_specific_purpose";
    public static final String wasCreatedBy = cidocCrmNamespace + "P94_was_created_by";
    public static final String hasUnit = cidocCrmNamespace + "P91_has_unit";
    public static final String formsPartOf = cidocCrmNamespace + "P5_forms_part_of";
    public static final String isCurrentMemberOf = cidocCrmNamespace + "P107_is_current_member_of";
    public static final String hasNumberOfParts = cidocCrmNamespace + "P57_has_number_of_parts";
    public static final String hasAlternativeForm = cidocCrmNamespace + "P139_has_alternative_form";
    public static final String tookPlaceAt = cidocCrmNamespace + "P7_took_place_at";
    public static final String usedSpecificObject = cidocCrmNamespace + "P33_used_specific_object";
    public static final String usedSpecificTechnique = cidocCrmNamespace + "P33_used_specific_technique";
    public static final String assigned = cidocCrmNamespace + "P42_assigned";
    public static final String hasDimension = cidocCrmNamespace + "P43_has_dimension";
    public static final String assignedAttributeTo = cidocCrmNamespace + "P140_assigned_attribute_to";
    public static final String wasAttributedBy = cidocCrmNamespace + "P140_was_attributed_by";
    public static final String classified = cidocCrmNamespace + "P41_classified";
    public static final String resultedIn = cidocCrmNamespace + "P123_resulted_in";
    public static final String transformed = cidocCrmNamespace + "P124_transformed";

    public static final String isAlsoIdentifiedBy = cidocCrmNamespace + "B_is_also_identified_by";

    public static final String publicationEventLabel = frbrNamespace + "F30_Publication";

    /*crm-digital classes and properties*/
    public static final String digitizationProcessLabel = crmDigNamespace + "D2_Digitization_Process";
    public static final String formalDerivationEventLabel = crmDigNamespace + "D3_Formal_Derivation";
    public static final String digitalDeviceLabel = crmDigNamespace + "D8_Digital_Device";
    public static final String dataObjectLabel = crmDigNamespace + "D9_Data_Object";

    public static final String digitized = crmDigNamespace + "D2_digitized";
    //public static final String digitized=tloNamespace+"L1F_digitized";
    public static final String hasCreated = crmDigNamespace + "L20F_has_created";
    public static final String hasCurrentLocation = crmDigNamespace + "L21F_happened_on_device";
    public static final String happenedOnDevice = crmDigNamespace + "L21F_happened_on_device";
    public static final String createdDerivative = crmDigNamespace + "L22F_created_derivative";
    public static final String hadOutput = crmDigNamespace + "L11_had_output";
    public static final String isStoredOn = crmDigNamespace + "L19i_is_stored_on";
    public static final String hasComment = crmDigNamespace + "L47_has_comment";
    public static final String wasDerivationSourceFor = crmDigNamespace + "L21B_was_derivation_source_for";

    /*crm-sci classes and properties*/
    //public static final String encounterEventLabel=tloNamespace+"S19_Encounter_Event";
    public static final String encounterEventLabel = crmSciNamespace + "S19_Encounter_Event";
    public static final String sampleTakingLabel = crmSciNamespace + "S2_Sample_Taking";
    public static final String sampleLabel = crmSciNamespace + "S2_Sample";
    public static final String dataEvaluationLabel = crmSciNamespace + "S6_Data_Evaluation";
    public static final String hasFoundAt = crmSciNamespace + "O21_has_found_at";
    public static final String hasFoundObject = crmSciNamespace + "O32_has_found_object";
    public static final String removed = crmSciNamespace + "O5_removed";
    public static final String assignedDimension = crmSciNamespace + "O10_assigned_dimension";
    public static final String describes = crmSciNamespace + "O11_describes";
    
    /*marine-tlo classes and properties*/
    public static final String countryLabel = tloNamespace + "Country";
    //public static final String countryLabel=tloNamespace+"BC_STH_Country";
    public static final String bioticElementLabel = tloNamespace + "BC38_Biotic_Element";
    public static final String ecosystemEnvironmentLabel = tloNamespace + "BC14_Ecosystem_Environment";
    public static final String ecosystemLabel = tloNamespace + "BC12_Ecosystem";
    public static final String bioticElementTypeLabel = tloNamespace + "BT10_Biotic_Element_Type";
    public static final String equipmentTypeLabel = tloNamespace + "BT11_Equipment_Type";
    public static final String datasetLabel = tloNamespace + "BC21_Dataset";
    public static final String waterAreaLabel = tloNamespace + "BC15_Water_Area";
    public static final String specimenLabel = tloNamespace + "BC53_Specimen";
    public static final String speciesLabel = tloNamespace + "BT27_Species";
    public static final String genusLabel = tloNamespace + "BT26_Genus";
    public static final String orderLabel = tloNamespace + "BT34_Order";
    public static final String familyLabel = tloNamespace + "BT24_Family";
    public static final String kingdomLabel = tloNamespace + "BT18_Kingdom";
    public static final String phylumLabel = tloNamespace + "BT19_Phylum";
    public static final String classLabel = tloNamespace + "BT22_Class";
    public static final String ecosystemTypeLabel = tloNamespace + "BT7_Ecosystem_Type";
    public static final String publicationLabel = tloNamespace + "BC27_Publication";

    public static final String belongsTo = tloNamespace + "LC10_belongs_to";
    public static final String belongsToType = tloNamespace + "LT8_belongs_to";
    public static final String LC12_wasAttributedBy = tloNamespace + "LC12_was_attributed_by";
    public static final String hasSection = tloNamespace + "LC27_has_section";
    public static final String LC16_isComposedOf = tloNamespace + "LC16_is_composed_of";

    /*new classes and properties*/
    public static final String rdfTypeLabel = "http://www.w3.org/1999/02/22-rdf-syntax-ns#type";
    public static final String temporaryAggregateLabel = tloNamespace + "Temporary_Aggregate";
    public static final String hasCurator = tloNamespace + "P_has_curator";
    public static final String hasContributor = tloNamespace + "P_has_contributor";
    public static final String hasPublisher = tloNamespace + "P_has_publisher";
    public static final String hasAccessMethod = tloNamespace + "P_has_access_method";
    public static final String isLocatedAt = tloNamespace + "P_is_located_at";
    public static final String dimensionTypeLabel = tloNamespace + "BC_Dimension_Type";
    public static final String wasProvidedBy = tloNamespace + "P_was_provided_by";
    public static final String isUsedIn = tloNamespace + "P_is_used_in";
    public static final String hasContrastMethod = cidocCrmNamespace + "P_has_contrast_method";
    public static final String hasZoom = cidocCrmNamespace + "P_has_zoom";
    public static final String hasVoltage = cidocCrmNamespace + "P_has_voltage";
    public static final String hasFilter = cidocCrmNamespace + "P_has_filter";
    public static final String hasExposureTime = cidocCrmNamespace + "P_has_exposure_time";
    public static final String hasProtocol = cidocCrmNamespace + "P_has_protocol";
    public static final String usingSampleHolder = cidocCrmNamespace + "P_using_sample_holder";
    public static final String usingScanningMedium = cidocCrmNamespace + "P_using_scanning_medium";

    public static final String rdfsLabel = rdfsNamespace + "label";

    /*prefixes for repository connection details */
    public static final String defaultVirtuosoJdbcUrlPrefix = "jdbc:virtuoso://";
    public static final String defaultUrlPrefix = "http://";

    /*proper suffixes for files to be imported */
    public static final String ntriplesDefaultExtension1 = ".ntriples";
    public static final String ntriplesDefaultExtension2 = ".ttl";
    public static final String N3_EXTENSION = ".n3";
    public static final String rdfDefaultExtension = ".rdf";

    /*A default namespace to be used*/
    public static final String defaultNamespaceForURIs = "http://www.lifewatchgreece.eu/entity";

    /*the folder containing the fundamental queries*/
    public static final String fundamentalQueriesFolder = "FundamentalQueries";
    
    /*the folder containing the materialization queries*/
    public static final String materializationQueriesFolder = "MaterializationQueries";
    
    /* various instances and labels  */
    public static final String fixationLabel="fixation";
    public static final String preservationMediumLabel="preservation medium";
    public static final String CAMERA_RESOLUTION_CONSTANT_VALUE="camera resolution";
    public static final String CURRENT_CONSTANT_VALUE="current";
    public static final String EXPOSURE_TIME_CONSTANT_VALUE="exposure time";
    public static final String FILTER_CONSTANT_VALUE="filter";
    public static final String FRAME_AVERAGING_CONSTANT_VALUE="frame averaging";
    public static final String OVERSIZE_SETTINGS_CONSTANT_VALUE="οversize settings";
    public static final String PREPARATION_TIME_CONSTANT_VALUE="preparation time";
    public static final String RANDOM_MOVEMENT_CONSTANT_VALUE="random movement";
    public static final String SCAN_DEGREES_CONSTANT_VALUE="scan degrees";
    public static final String SCANNED_PART_CONSTANT_VALUE="scanned part";
    public static final String SCANNING_CONSTANT_VALUE="scanning";
    public static final String SCANNING_DURATION_CONSTANT_VALUE="scanning duration";
    public static final String VOLTAGE_CONSTANT_VALUE="voltage";
    public static final String ZOOM_CONSTANT_VALUE="zoom";
    
    /* DWCA Resources */
    public static final String ABSTRACT="abstract";
    public static final String ALTERNATE_IDENTIFIER="alternateIdentifier";
    public static final String ASSOCIATED_PARTY="associatedParty";
    public static final String CONTACT="contact";
    public static final String CREATOR="creator";
    public static final String EML_VERSION="emlVersion";
    public static final String INDIVIDUAL_NAME="individualName";
    public static final String INTELLECTUAL_RIGHTS="intellectualRights";
    public static final String KEYWORD="keyword";
    public static final String KEYWORD_SET="keywordSet";
    public static final String KEYWORD_THESAURUS="keywordThesaurus";
    public static final String METADATA_PROVIDER="metadataProvider";
    public static final String PUB_DATE="pubDate";
    public static final String RESOURCE_LOGO_URL="resourceLogoUrl";
    public static final String TITLE="title";
    public static final String GBIF_THESAURUS_KEYWORD="GBIF Dataset Type Vocabulary";
    
    public static final String MEDOBIS_FILE_RESOURCE_XML="resource.xml";
    public static final String DWCA_FILENAME_PREFIX="dwca-";
    public static final String DWCA_FILE_EXTENSION=".zip";
    
    public static final String LOCAL_DATASET_INSTANCES="local";
    public static final String DIRECTORY_N3_FILENAME="directory.n3";
    public static final String TAXONOMY_N3_FILENAME="taxomomy.n3";
    public static final String SC_NAME_N3_FILENAME="sc_name.n3";
    public static final String OCCURRENCE_TEMP_N3_FILENAME="occurence_temp.n3";
    public static final String OCCURENCE_N3_FILENAME="occurence.n3";
    public static final String OCCURRENCE_EVENT_N3_FILENAME="occurence_event.n3";
    public static final String MEASUREMENT_N3_FILENAME="measurement.n3";
    public static final String ENVIRONMENTAL_N3_FILENAME="environmental.n3";
    
    public static final String DIRECTORY_CONSOLIDATED_N3_FILENAME="directory_cons.n3";
    public static final String DIRECTORY_CONSOLIDATED_N3_FOLDER_NAME="_directory";
    public static final String METADATA_CONSOLIDATED_N3_FILENAME="metadata.n3";
}
