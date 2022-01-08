package saxion.smartsolutions.persistence.impl.jpa;

import saxion.smartsolutions.core.model.domain.Model;
import saxion.smartsolutions.core.model.domain.ModelNumber;
import saxion.smartsolutions.core.model.repo.ModelRepository;

import java.util.Optional;

/**
 * Implementation of ModelRepository in the JPA Framework
 */
public class JpaModelRepository extends JpaRepository<Model, Long, ModelNumber> implements ModelRepository {

    public JpaModelRepository() {
        super("modelNumber");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Model> findByModelNumber(ModelNumber modelNumber) {
        return ofIdentity(modelNumber);
    }
}
