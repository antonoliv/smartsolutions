package saxion.smartsolutions.core.model.application;

import saxion.smartsolutions.core.PersistenceContext;
import saxion.smartsolutions.core.model.domain.Model;
import saxion.smartsolutions.core.model.domain.ModelNumber;
import saxion.smartsolutions.core.model.repo.ModelRepository;

import java.util.Optional;

/**
 * Controller for searching models in the application
 */
public class SearchModelController {

    private ModelRepository repo = PersistenceContext.repositories().modelRepository();

    /**
     * Finds a model with the given model number in the application
     * @param modelNumber model number to search
     * @return model with the given model number
     */
    public Optional<Model> findByModelNumber(ModelNumber modelNumber) {
        return repo.findByModelNumber(modelNumber);
    }

    /**
     * Returns all registered models in the application
     * @return list of registered models
     */
    public Iterable<Model> listModels() {
        return repo.findAll();
    }
}
