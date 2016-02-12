/*
 *
 * Copyright 2015 FORTH-ICS-ISL (http://www.ics.forth.gr/isl/)
 * Foundation for Research and Technology - Hellas (FORTH)
 * Institute of Computer Science (ICS)
 * Information Systems Laboratory (ISL)
 *
 * Licensed under the EUPL, Version 1.1 or – as soon they
 * will be approved by the European Commission - subsequent
 * versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the
 * Licence.
 * You may obtain a copy of the Licence at:
 *
 * http://joinup.ec.europa.eu/software/page/eupl/licence-eupl
 *
 * Unless required by applicable law or agreed to in
 * writing, software distributed under the Licence is
 * distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied.
 * See the Licence for the specific language governing
 * permissions and limitations under the Licence.
 *
 */
package eu.lifewatchgreece.annotations.repository;

import java.util.Set;
import java.util.UUID;
import org.openrdf.idGenerator.IDGenerator;
import org.openrdf.model.Resource;
import org.openrdf.model.URI;
import org.openrdf.sail.memory.model.MemValueFactory;


/**
/**
 * A IDGenerators using an http://lifewatchgreece.eu/entity/annotations/resources/
 *
 * @author papadako
 */
public class URIGenerator implements IDGenerator {
    
    /**
     * Generates a unique resource with an
     * http://lifewatchgreece.eu/entity/annotations/resources/ prefix.
     *
     * @return a Resource containing a unique identifier.
     */
    @Override
    public Resource generateID(Set<URI> set) {
        // In any case, i.e. whatever the set contains just return lifewatch randomUUID
        return new MemValueFactory().createURI("http://lifewatchgreece.eu/entity/annotations/resources/" + UUID.randomUUID());
    }
}
