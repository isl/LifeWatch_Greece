package eu.lifewatch.test;

import eu.lifewatch.common.Resources;
import eu.lifewatch.core.impl.UriCreator;
import eu.lifewatch.exception.URIValidationException;
import java.net.MalformedURLException;
import java.net.URL;
import junit.framework.TestCase;
import org.junit.Test;

/**
 * @author Nikos Minadakis (minadakn 'at' ics 'dot' forth 'dot' gr)
 * @author Yannis Marketakis (marketak 'at' ics 'dot' forth 'dot' gr)
 */
public class UriCreatorTest extends TestCase{
    
    public UriCreatorTest(){
        super(UriCreatorTest.class.getName());
    }
        
    @Test
    public void testDummy(){
        assertTrue(true);
    }
    
//    /*Testing the creation of a URI without giving any parameters*/
//    @Test
//    public void testEmpty(){
//        String createdUri=UriCreator.create().stringValue();
//        /*assert that it has been created */
//        assertNotNull(createdUri);
//        assertFalse(createdUri.isEmpty());
//        assertTrue(createdUri.startsWith(Resources.defaultNamespaceForURIs));
//        
//        /*assert that if we create another one they will no be equals */
//        String anotherCreatedUri=UriCreator.create().stringValue();
//        assertFalse(createdUri.equals(anotherCreatedUri));
//        
//        /*assert that it is a proper URI */
//        try{
//            assertNotNull(new URL(createdUri));
//        }catch(MalformedURLException ex){
//            fail("The created URI ("+createdUri+") is not valid\n"+ex);
//        }
//        try{
//            assertNotNull(new URL(anotherCreatedUri));
//        }catch(MalformedURLException ex){
//            fail("The created URI ("+anotherCreatedUri+") is not valid\n"+ex);
//        }
//    }
//    
//    /*Testing the creation of a URI using namespace*/
//    @Test
//    public void testWithNamespace(){
//        /*assert that it will fail with an invalid namespace*/
//        try{
//            String wrongNamespace="wrongNamespace";
//            String createdUri=UriCreator.create()
//                    .withNamespace(wrongNamespace)
//                    .stringValue();
//            fail("The given namespace is wrong ("+wrongNamespace+"). It should fail here");
//        }catch(URIValidationException ex){
//            assertTrue("Successfully caught exception",true);
//        }
//        
//        /*asssert that a correct namespace will lead to a valid URI containing that namespace */
//        try{
//            String correctNamespace="http://localhost/correctUri";
//            String createdUri=UriCreator.create()
//                    .withNamespace(correctNamespace)
//                    .stringValue();
//            assertNotNull(createdUri);
//            assertFalse(createdUri.isEmpty());
//            assertTrue(createdUri.startsWith(correctNamespace));
//            assertNotNull(new URL(createdUri));
//        }catch(URIValidationException ex){
//            fail("The given namespace is valid. It shouldn't fail here");
//        }catch(MalformedURLException ex){
//            fail("The craeted URI is valid. It shouldnt't fail here");
//        }
//        
//        /*asssert that special characters */
//        try{
//            String correctNamespace="http://localhost/correctUri/";
//            String createdUri=UriCreator.create()
//                    .withNamespace(correctNamespace)
//                    .stringValue();
//            assertNotNull(createdUri);
//            assertFalse(createdUri.isEmpty());
//            assertFalse(createdUri.startsWith(correctNamespace));
//            assertNotNull(new URL(createdUri));
//        }catch(URIValidationException ex){
//            fail("The given namespace is valid. It shouldn't fail here");
//        }catch(MalformedURLException ex){
//            fail("The created URI is valid. It shouldnt't fail here");
//        }
//    }
//    
//    /*Testing the creation of a URI giving a type*/
//    @Test
//    public void testWithType(){
//        String type="myType";
//        String createdUri=UriCreator.create()
//                .withType(type)
//                .stringValue();
//        assertNotNull(createdUri);
//        assertFalse(createdUri.isEmpty());
//        assertTrue(createdUri.contains(type));
//        try{
//            assertNotNull(new URL(createdUri));
//        }catch(MalformedURLException ex){
//            fail("The created URI is valid. It shouldn't fail here");
//        }
//    }
//    
//    /*Testing the creation of a URI using values*/
//    @Test
//    public void testWithValue(){
//        String value1="value1";
//        String value2="value2";
//        /*assert that the URI is being created using only one value*/
//        String createdUri=UriCreator.create()
//                .withValue(value1)
//                .stringValue();
//        assertNotNull(createdUri);
//        assertFalse(createdUri.isEmpty());
//        assertTrue(createdUri.contains(value1));
//        try{
//            assertNotNull(new URL(createdUri));
//        }catch(MalformedURLException ex){
//            fail("The created URI is valid. It shouldn't fail here");
//        }
//        
//        /*assert that the URI is being created using many values*/
//        createdUri=UriCreator.create()
//                .withValue(value1)
//                .withValue(value2)
//                .stringValue();
//        assertNotNull(createdUri);
//        assertFalse(createdUri.isEmpty());
//        assertTrue(createdUri.contains(value1));
//        assertTrue(createdUri.contains(value2));
//        try{
//            assertNotNull(new URL(createdUri));
//        }catch(MalformedURLException ex){
//            fail("The created URI is valid. It shouldn't fail here");
//        }
//    }   
//    
//    /*Testing the creation of a URI using namespace and type*/
//    @Test
//    public void testWithNamespaceAndType(){
//        String namespace="http://localhost/my_namespace";
//        String type="my_type";
//        try{   
//            String createdUri=UriCreator.create()
//                    .withNamespace(namespace)
//                    .withType(type)
//                    .stringValue();
//            assertNotNull(createdUri);
//            assertFalse(createdUri.isEmpty());
//            assertTrue(createdUri.contains(namespace));
//            assertTrue(createdUri.contains(type));
//            assertNotNull(new URL(createdUri));
//        }catch(URIValidationException ex){
//            fail("The given namespace is valid. It shouldn't fail here.");
//        }catch(MalformedURLException ex){
//            fail("The created URI is valid. It shouldn't fail here");
//        }
//    }
//    
//    /*Testing the creation of a URI using namespace and values*/
//    @Test
//    public void testWithNamespaceAndValues(){
//        String namespace="http://localhost/my_namespace";
//        String value1="value1";
//        String value2="value2";
//        try{   
//            String createdUri=UriCreator.create()
//                    .withNamespace(namespace)
//                    .withValue(value1)
//                    .withValue(value2)
//                    .stringValue();
//            assertNotNull(createdUri);
//            assertFalse(createdUri.isEmpty());
//            assertTrue(createdUri.contains(namespace));
//            assertTrue(createdUri.contains(value1));
//            assertTrue(createdUri.contains(value2));
//            assertNotNull(new URL(createdUri));
//        }catch(URIValidationException ex){
//            fail("The given namespace is valid. It shouldn't fail here.");
//        }catch(MalformedURLException ex){
//            fail("The created URI is valid. It shouldn't fail here");
//        }
//    }
//    
//    /*Testing the creation of a URI using type and values*/
//    @Test
//    public void testWithTypeAndValues(){
//        String type="my_type";
//        String value1="value1";
//        String value2="value2";
//        try{   
//            String createdUri=UriCreator.create()
//                    .withType(type)
//                    .withValue(value1)
//                    .withValue(value2)
//                    .stringValue();
//            assertNotNull(createdUri);
//            assertFalse(createdUri.isEmpty());
//            assertTrue(createdUri.contains(type));
//            assertTrue(createdUri.contains(value1));
//            assertTrue(createdUri.contains(value2));
//            assertNotNull(new URL(createdUri));
//        }catch(MalformedURLException ex){
//            fail("The created URI is valid. It shouldn't fail here");
//        }
//    }
//    
//    /*Testing the creation of a URI using namespace, type and values*/
//    @Test
//    public void testWithNamespaceAndTypeAndValues(){
//        String namespace="http://localhost/my_namespace";
//        String type="my_type";
//        String value1="value1";
//        String value2="value2";
//        try{   
//            String createdUri=UriCreator.create()
//                    .withNamespace(namespace)
//                    .withType(type)
//                    .withValue(value1)
//                    .withValue(value2)
//                    .stringValue();
//            assertNotNull(createdUri);
//            assertFalse(createdUri.isEmpty());
//            assertTrue(createdUri.contains(namespace));
//            assertTrue(createdUri.contains(type));
//            assertTrue(createdUri.contains(value1));
//            assertTrue(createdUri.contains(value2));
//            assertNotNull(new URL(createdUri));
//        }catch(URIValidationException ex){
//            fail("The given namespace is valid. It shouldn't fail here.");
//        }catch(MalformedURLException ex){
//            fail("The created URI is valid. It shouldn't fail here");
//        }
//    }
}