package saxion.smartsolutions.persistence.impl.jpa;

import saxion.smartsolutions.core.property.domain.Property;
import saxion.smartsolutions.core.property.repo.PropertyRepository;
import saxion.smartsolutions.core.value.Designation;

public class JpaPropertyRepository extends JpaRepository<Property, Long, Designation> implements PropertyRepository {

    public JpaPropertyRepository() {
        super("name");
    }

}
