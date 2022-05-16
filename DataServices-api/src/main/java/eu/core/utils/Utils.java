package eu.core.utils;

import eu.lifewatch.common.Resources;

/**
 *
 * @author Yannis Marketakis (marketak 'at' ics 'dot' forth 'dot' gr)
 */
public class Utils {
    
    public static String getUserFriendlyClassName(String classUri){
        switch(classUri){
            case Resources.speciesLabel:
                return "Species";
            case Resources.genusLabel:
                return "Genus";
            case Resources.familyLabel:
                return "Family";
            case Resources.orderLabel:
                return "Order";
            case Resources.classLabel:
                return "Class";
            case Resources.phylumLabel:
                return "Phylum";
            case Resources.kingdomLabel:
                return "Kingdom";
            default:
                return "N/A";
        }
    }

}
