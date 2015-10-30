package eu.lifewatch.core.model;

import java.util.Objects;

/**
 * Pair objects are being used to hold information about paired values. 
 * 
 * @author Nikos Minadakis (minadakn 'at' ics 'dot' forth 'dot' gr)
 * @author Yannis Marketakis (marketak 'at' ics 'dot' forth 'dot' gr)
 */
public class Pair {
    private String key;
    private String value;
       
    public Pair(){
        this.key="";
        this.value="";
    }
    
    public static Pair of(String key, String value){
        return new Pair(key,value);
    }
    
    public Pair(String key, String value){
        this.key=key;
        this.value=value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
    @Override
    public String toString(){
        return "<"+this.key+","+this.value+">";
    }
    
    @Override
    public boolean equals(Object anotherObject){
        if(anotherObject instanceof Pair){
            Pair anotherPair=(Pair)anotherObject;
            return this.key.equals(anotherPair.getKey()) && this.value.equals(anotherPair.getValue());
        }else{
            return false;
        }
        
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.key);
        hash = 59 * hash + Objects.hashCode(this.value);
        return hash;
    }
}