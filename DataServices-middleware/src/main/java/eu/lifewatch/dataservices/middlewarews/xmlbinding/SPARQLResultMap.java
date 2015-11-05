package eu.lifewatch.dataservices.middlewarews.xmlbinding;

import java.util.Map;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * @author Yannis Marketakis (marketak 'at' ics 'dot' forth 'dot' gr)
 * @author Nikos Minadakis (minadakn 'at' ics 'dot' forth 'dot' gr)
 */
public class SPARQLResultMap {
    @XmlJavaTypeAdapter(value = MapAdapter.class)
    @XmlElement(name = "Sparqlresults")
    public Map<String, String> sparqlResMap;
    
}