package eu.lifewatch.dataservices.middleware.metadatarepository.rest;

/**
 * @author Yannis Marketakis (marketak 'at' ics 'dot' forth 'dot' gr)
 * @author Nikos Minadakis (minadakn 'at' ics 'dot' forth 'dot' gr)
 */
public class RepositoryData {
    String repositoryGraph;
    
    public RepositoryData(String repositoryGraph){
        this.repositoryGraph=repositoryGraph;
    }
    
    public String getRepositoryGraph(){
        return this.repositoryGraph;
    }
    
    public void setRepositoryGraph(String repositoryGraph){
        this.repositoryGraph=repositoryGraph;
    }
    
    @Override
    public String toString(){
        return this.repositoryGraph;
    }
}