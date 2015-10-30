package eu.lifewatch.core.model;

import java.util.Objects;

/**
 * @author Nikos Minadakis (minadakn 'at' ics 'dot' forth 'dot' gr)
 * @author Yannis Marketakis (marketak 'at' ics 'dot' forth 'dot' gr)
 */
public class Triple {
    private String subject, predicate, object;
    
    public Triple(String sub, String pred, String ob){
        this.subject=sub;
        this.predicate=pred;
        this.object=ob;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getPredicate() {
        return predicate;
    }

    public void setPredicate(String predicate) {
        this.predicate = predicate;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }
    
    @Override
    public String toString(){
        return "<s-p-o>: "+this.subject+" - "+this.predicate+" - "+this.object;
    }
    
    @Override
    public boolean equals(Object anotherObject){
        if(anotherObject instanceof Triple){
            Triple anotherTriple=(Triple)anotherObject;
            return (this.subject.equals(anotherTriple.getSubject()) && 
                    this.predicate.equals(anotherTriple.getPredicate()) &&
                    this.object.equals(anotherTriple.getObject()));
        }else{
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.subject);
        hash = 59 * hash + Objects.hashCode(this.predicate);
        hash = 59 * hash + Objects.hashCode(this.object);
        return hash;
    }
}