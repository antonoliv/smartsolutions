package saxion.smartsolutions.core.model.repo;

import saxion.smartsolutions.core.concepts.Repository;
import saxion.smartsolutions.core.model.domain.Model;
import saxion.smartsolutions.core.model.domain.ModelNumber;

import java.util.Optional;

/**
 * Repository Interface for managing instances of models in the persistence context
 */
public interface ModelRepository extends Repository<ModelNumber, Model> {

    /**
     * Finds a model with the given model number in the persistence context
     * @param modelNumber model number to search
     * @return model with the given model number
     */
    Optional<Model> findByModelNumber(final ModelNumber modelNumber);
}
