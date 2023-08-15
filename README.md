# A data-driven extended-life platform for reusable consumer electronics

An proof of concept application designed to demonstrate an example of a dedicated platform to register electronic components. Solely done by the author and motivated by a research project for the _Smart Solutions_ course at the _Saxion University of Applied Sciences_ that the author took during his ERASMUS mobility to _Enschede, Netherlands_.

The main goal was to promote and assist in electronic component collection and reuse by creating a platform to register consumer electronics' parts and more easily find them in the case their needed by a repair job. This makes the job of storing and the repurpouse of old parts easier and, furthermore, supports the concept of a **circular economy**.

During research and development several local companies, electronics stores and repair shops were consulted, through several meetings, to gather important requirements first-hand from organizations that might benefit from the system. Most of them didn't have a dedicated electronic components stock system and the ones who did had one that didn't take full advantage of the component's information.

## Functionalites

* **Register Property** - Register properties of models or parts (Height, Width, Power, ...)
* **Register Model** - Register a device model
* **Register Part** - Register a device part
* **Search Parts** - Search a part by input text and filtering by name, brand, model number, part number or specific properties
* **Check or Update Part/Model Information** - Check or Update a part's or model's details

## Source Code Structure

* **bootstrappers** - Data bootstrapper for demonstration  
* **core** - Core Project code. Divided by entities and subdivided by Controllers (application), Model classes (model) and Repositories (repo)  
* **doc** - Some project documentation (Entity-Relationship Diagram, Domain Model)  
* **docker** - Docker Compose configuration  
* **persistence.impl** - Persistance implementations for the repositories. Only has the implementation for JPA (Java Peristence API)  
* **http** - Simple API server that serves the registered information.  
* **www** - Barebones front-end to demonstrate app functionality done with plain HTML/CSS and pure JavaScript  
