package eu.lifewatch.dataservices.middlewarews.xmlbinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * @author Yannis Marketakis (marketak 'at' ics 'dot' forth 'dot' gr)
 * @author Nikos Minadakis (minadakn 'at' ics 'dot' forth 'dot' gr)
 */
class MapAdapter extends XmlAdapter<SPARQLResults, Map<String, String>> {
    @Override
    public Map<String, String> unmarshal(SPARQLResults results) throws Exception {
        Map<String, String> map = new HashMap<>();
        for (SPARQLResult res : results.sparqlresults)
            map.put(res.key, res.value);
        return map;
    }

    @Override
    public SPARQLResults marshal(Map<String, String> map) throws Exception {
        SPARQLResults results = new SPARQLResults();
        // This method is only called if you marshal (Java -> XML)
        results.sparqlresults = new ArrayList<>(map.size());

        for (Entry<String, String> entry : map.entrySet()) {
            SPARQLResult e = new SPARQLResult();
            e.key = entry.getKey();
            e.value = entry.getValue();
            results.sparqlresults.add(e);
        }
        return results;
    }
}