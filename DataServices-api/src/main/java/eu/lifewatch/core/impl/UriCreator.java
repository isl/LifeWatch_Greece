package eu.lifewatch.core.impl;

import eu.lifewatch.common.Resources;
import eu.lifewatch.exception.URIValidationException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

/**
 * This class contains the basic methods for creating a URI. It supports the creation of 
 * a URI in an automatic way. The user can provide any information he wants (the namespace, the type, 
 * the values to be included) or he can simply request for a randomly generated URI. The detailed functionalities 
 * are the following. 
 * If the user does not provide any input then a random URI will be created (for this reason a default namespace will be used).
 * The user can provide also, the namespace, the type and a set of values that should be contained in the URI. 
 * Finally the method stringValue() outputs a String representation of the URI. 
 * Some examples follow:
 * 
 * <ul>
 * <li>UriCreator.create().stringValue() will produce --&gt; http://www.ics.forth.gr#nrvtitk5u561c0koae8d2060ig</li>
 * <li>UriCreator.create().withNamespace("http://localhost").stringValue() will produce --&gt;
 *     http://localhost#nrvtitk5u561c0koae8d2060ig</li>
 * <li>UriCreator.create().withValue("myresource").stringValue() will produce --&gt;
 *     http://www.ics.forth.gr#myresource </li>
 * <li>UriCreator.create().withNamespace("http://locahost").withValue("myresource").stringValue() 
 *     will produce --&gt; http://localhost#myresource</li>
 * <li>UriCreator.create().withNamespace("http://locahost").withType("generic").withValue("myresource").stringValue() 
 *     will produce --&gt; http://localhost/generic#myresource</li>
 * <li>UriCreator.create().withValue("firstresource").withValue("secondresource").stringValue() 
 *     will produce --&gt; http://localhost#firstresource_secondresource </li>
 * </ul>
 * 
 * 
 * @author Nikos Minadakis (minadakn 'at' ics 'dot' forth 'dot' gr)
 * @author Yannis Marketakis (marketak 'at' ics 'dot' forth 'dot' gr)
 */
public class UriCreator {
    private String namespace;
    private String type;
    private List<String> values;
    
    public UriCreator(){
        this.namespace=null;
        this.type=null;
        this.values=new ArrayList<>();
    }
    
    /**Creates a URI using the default settings. More specifically a default namespace will be used 
    being followed by a random alphanumeric string. 
    @return a UriCreator instance containing details about the URI. Use the method stringValue() to get 
    a String representation of the URI */
    public static UriCreator create(){
        return new UriCreator();
    }
    
    /**Creates a URI using the given namespace as its prefix.
     * 
     * @param ns The namespace that will be used.
     * @return a UriCreator instance containing the given information. Use the method stringValue() to get 
     * a String representation of the URI 
     * @throws URIValidationException if the given namespace is not valid (it will lead to an invalid URI)*/
    public UriCreator withNamespace(String ns) throws URIValidationException{
        String originalNS=ns;
        while(ns.endsWith("#") || ns.endsWith("/")){
            ns=ns.substring(0,ns.length()-1);
        }
        try{
            new URL(ns);
        }catch(MalformedURLException ex){
            throw new URIValidationException("The given namespace ("+originalNS+") is not valid",ex);
        }
        this.namespace=ns;
        return this;
    }
    
    /**Creates a URI containing the given type. The type will be hardcoded in the URI 
     * right after the namespace, and before any value(s).
     * 
     * @param tp The type that will be used.
     * @return a UriCreator instance containing the given information. Use the method stringValue() to get 
     * a String representation of the URI */
    public UriCreator withType(String tp){
        this.type=tp;
        return this;
    }
    
    /**Creates a URI containing the given value. The value will be hardcoded in the URI, 
     * right after the namespace, and the type.
     * 
     * @param value the value that will be used
     * @return a UriCreator instance containing the given information. Use the method stringValue() to get 
     * a String representation of the URI. */
    public UriCreator withValue(String value){
        if(!this.values.contains(value)){
            this.values.add(value);
        }
        return this;
    }
    
    /**Returns a string representation of the URI.     
     * @return the URI as a String */
    public String stringValue(){
        StringBuilder retValue=new StringBuilder();
        if(this.namespace==null){
            retValue.append(Resources.defaultNamespaceForURIs);
        }else{
            retValue.append(this.namespace);
        }
        if(this.type==null){
           retValue.append("#");
        }else{
            retValue.append("/")
                    .append(type).append("#");
        }
        if(this.values.isEmpty()){
            retValue.append(this.getRandomString());
        }else{
            for(String value : values){
                retValue.append(value).append("_");
            }
            retValue=retValue.deleteCharAt(retValue.length()-1);
        }
        return retValue.toString();
    }
    
    /*Creates a random alphanumeric string*/
    private String getRandomString(){
        return new BigInteger(130, new SecureRandom()).toString(32);
    }
}
