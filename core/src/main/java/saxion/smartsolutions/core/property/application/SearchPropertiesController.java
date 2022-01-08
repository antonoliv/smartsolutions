package saxion.smartsolutions.core.property.application;

import saxion.smartsolutions.core.PersistenceContext;
import saxion.smartsolutions.core.property.domain.Property;
import saxion.smartsolutions.core.property.repo.PropertyRepository;

public class SearchPropertiesController {

    private PropertyRepository repo = PersistenceContext.repositories().propertyRepository();

    public Iterable<Property> listProperties() {
        return repo.findAll();
    }
}
