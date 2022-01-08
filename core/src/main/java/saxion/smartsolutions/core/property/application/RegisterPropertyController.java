package saxion.smartsolutions.core.property.application;

import org.hibernate.exception.ConstraintViolationException;
import saxion.smartsolutions.core.PersistenceContext;
import saxion.smartsolutions.core.property.domain.Property;
import saxion.smartsolutions.core.property.repo.PropertyRepository;
import saxion.smartsolutions.core.value.Designation;

public class RegisterPropertyController {

    private final PropertyRepository repo = PersistenceContext.repositories().propertyRepository();

    public Property registerProperty(Designation prop) {
        try {
            return repo.save(new Property(prop));
        } catch (ConstraintViolationException e) {
            throw new IllegalArgumentException("Property already registered.");
        }
    }

}
