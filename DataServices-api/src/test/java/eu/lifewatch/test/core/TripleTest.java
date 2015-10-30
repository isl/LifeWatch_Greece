package eu.lifewatch.test.core;

import junit.framework.TestCase;
import eu.lifewatch.core.model.Triple;
import org.junit.Test;

/**
 * @author Nikos Minadakis (minadakn 'at' ics 'dot' forth 'dot' gr)
 * @author Yannis Marketakis (marketak 'at' ics 'dot' forth 'dot' gr)
 */
public class TripleTest extends TestCase{
    public static final String defSubject="http://www.ics.forth.gr/isl/subject";
    public static final String defPredicate="http://www.ics.forth.gr/isl/predicate";
    public static final String defObject="http://www.ics.forth.gr/isl/object";
    
    public TripleTest(){
        super(TripleTest.class.getName());
    }
    
    @Test
    public void testTripleGetSet(){
        Triple triple=new Triple(defSubject,defPredicate,defObject);
        assertEquals(defSubject, triple.getSubject());
        assertEquals(defPredicate, triple.getPredicate());
        assertEquals(defObject, triple.getObject());
        triple.setSubject(defSubject+"_new");
        assertTrue(!triple.getSubject().equals(defSubject));
        assertEquals(defSubject+"_new", triple.getSubject());
        triple.setPredicate(defPredicate+"_new");
        assertTrue(!triple.getPredicate().equals(defPredicate));
        assertEquals(defPredicate+"_new", triple.getPredicate());
        triple.setObject(defObject+"_new");
        assertTrue(!triple.getObject().equals(defObject));
        assertEquals(defObject+"_new", triple.getObject());
    }
    
    @Test
    public void testEquals(){
        Triple triple=new Triple(defSubject,defPredicate,defObject);
        Triple anotherTriple=new Triple(defSubject,defPredicate,defObject);
        //comparing equal objects
        assertTrue(triple.equals(anotherTriple));
        assertEquals(triple,anotherTriple);
        assertEquals(triple.getSubject(), anotherTriple.getSubject());
        assertEquals(triple.getPredicate(), anotherTriple.getPredicate());
        assertEquals(triple.getObject(), anotherTriple.getObject());
        //Comparing non-equal objects
        anotherTriple=new Triple(defSubject+"_another",defPredicate,defObject);
        assertTrue(!triple.equals(anotherTriple));
        anotherTriple=new Triple(defSubject,defPredicate+"_another",defObject);
        assertTrue(!triple.equals(anotherTriple));
        anotherTriple=new Triple(defSubject,defPredicate,defObject+"_another");
        assertTrue(!triple.equals(anotherTriple));
        anotherTriple=new Triple(defSubject+"_another",defPredicate+"_another",defObject);
        assertTrue(!triple.equals(anotherTriple));
        anotherTriple=new Triple(defSubject+"_another",defPredicate,defObject+"_another");
        assertTrue(!triple.equals(anotherTriple));
        anotherTriple=new Triple(defSubject,defPredicate+"_another",defObject+"_another");
        assertTrue(!triple.equals(anotherTriple));
        anotherTriple=new Triple(defSubject+"_another",defPredicate+"_another",defObject+"_another");
        assertTrue(!triple.equals(anotherTriple));
    }
}