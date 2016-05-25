# Lifewatch Greece Data Services
---
## Introduction

Data Services provide the users with the APIs and funtionalities for:
* publishing their datasets and making them available to the community by providing information that allows a user to locate and access the resource and its curator/creator,
* importing their datasets to the Lifewatch Greece Infrastructure,
* searching for datasets of interest by providing an efficient way of querying semantic networks,
* annotating species using morphological traits,
* performing biodiversity data and information quality improvement. 

The schema of the data that is provided by the users is mapped to the semantic model of the LWI and the data is transformed to LWI format before it is stored to the Infrastructure. The semantic model is based on CIDOC CRM (http://www.cidoc-crm.org/), CRM dig, CRM geo, CRM sci (www.ics.forth.gr/isl/CRMext/) and MarineTLO (http://www.ics.forth.gr/isl/MarineTLO/).

Specifically, the main functionalities that are provided by the API are:

**1. Semantic Repository Actions**

The Data Services offer the functions to:

1. connect to a semantic triple store
2. import data to the triple store
3. update the triple store contents
4. delete data from the triple store
5. query the triple store

  The triple store that the API has been extendedly used is Openlink’s Virtuoso, but it has also been tested with ontotext’s OWLIM.
  
**2. Biodiversity Data Transformations**

The Data Services API contains a number of functions that transform biodiversity data from CSV format to semantic web format (i.e. rdf, turtle, etc.). The code takes as input specific CSV templates that are filled with biodiversity data, and transforms it to rdf format based on CRM family event centric semantic models. The produced triples can be imported to a triple store. The biodiversity data that can be transformed belongs to the categories below:

* Data Collection metadata
* Occurrences Events data
* Identification Events data
* Occurrences Statistics 
* Statistical data
* Morphometrics data
* Morphological Characteristics data
* Micro CT Scanning data
* Micro CT Specimen data
* Micro CT Recostruction data
* Micro CT Post Processing data
* DNA sampling and sequencing data
* Scientific Naming data
* Common Naming data
* Synonyms data
* Taxonomy data
* Environmental data

**3. Biodiversity Data Querying**

The Data Services API, apart from the generic semantic graph querying functionalities, include a number of biodiversity domain specific querying functions that return biodiversity data that is stored in triple stores and belongs to one of the meain metadata categories. Furthermore, offset and limit can be used to limit the number of returned results enabling for example paging implementations.

**4. URIs creation**

The API offers three different functions for URI creation. These functions can create URIs that are a concatenation of the prefix, the type of the entity and the id of the entity, or a concatenation of the prefix and the id of the entity, or a hash code.

**5. Browsing Semantic Graphs**

Two functions for browsing the semantic graphs have been created. The one returns all the outgoing nodes, and their types, of an entity and the other all the incoming nodes, and their types, to an entity. Similar to the querying functions the offset and limit option is provided to limit the number of the returned results.

**6. Updating Semantic Graphs**

The updating of a semantic  graph is a complicated task because almost all of the contents are connected with each other. For this reason the Data Services API offers a function that updates the entities that are connected with a central entity (or a list of entities) until a defined depth.

**7. Materialization Rules Execution**

In many cases the contents of a semantic graph need to be materialized based on a number of rules. For this reason the Data Services API offers a materialization function that takes as input the SPARQL rules from an external folder and executes them importing the materialized triples to the triple store.

**8.	Textual Description Production**

The Data Services API contains a function that takes as input the scientific name of any species and by using standard templates, produces a textual (wikipage like) description of the corresponding species, by integrating information coming from different datasets and providers.

The API and the services have been designed, developed and used in the context of the project LifeWatch Greece (National Strategic Reference Framework, 2012-2015), https://www.lifewatchgreece.eu/

The API and the Services have been used in the Data Services of the LifeWatch Greece Infrastructure and their functionalities are exposed in http://metacatalogue.portal.lifewatchgreece.eu/.

## The Components

The data services are composed of two main components:
* the API that provides the functionalities for storing/accessing/managing the data, 
* the interface of SOAP services for exposing these functionalities in a standard way,
* the annotation services, 
* the fundamental querying interface.

## Contacts
### Main Contacts
*  Martin Doerr &lt;martin@ics.forth.gr&gt;
*  Chryssoula Bekiari &lt;bekiari@ics.forth.gr&gt;

### Main Developers
*  Nikos Minadakis &lt;minadakn@ics.forth.gr&gt;
*  Yannis Marketakis &lt;marketak@ics.forth.gr&gt;
*  Panagiotis Papadakos &lt;papadako@ics.forth.gr&gt;
