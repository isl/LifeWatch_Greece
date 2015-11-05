package eu.lifewatch.dataservices.middlewarews.xmlbinding;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;

/**
 * @author Yannis Marketakis (marketak 'at' ics 'dot' forth 'dot' gr)
 * @author Nikos Minadakis (minadakn 'at' ics 'dot' forth 'dot' gr)
 */
public class SPARQLResults {
    @XmlElement(name = "Sparqlresult")
    public List<SPARQLResult> sparqlresults;
}