package eu.lifewatch.dataservices.middlewarews.xmlbinding;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * @author Yannis Marketakis (marketak 'at' ics 'dot' forth 'dot' gr)
 * @author Nikos Minadakis (minadakn 'at' ics 'dot' forth 'dot' gr)
 */
public class SPARQLResult {
    @XmlAttribute(name = "Key")
    public String key;
    @XmlAttribute(name = "Value")
    public String value;

}
