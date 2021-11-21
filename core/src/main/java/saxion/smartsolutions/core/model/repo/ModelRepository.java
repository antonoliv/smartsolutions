package saxion.smartsolutions.core.model.repo;

import saxion.smartsolutions.core.concepts.Repository;
import saxion.smartsolutions.core.model.domain.Model;
import saxion.smartsolutions.core.model.domain.ModelNumber;

import java.util.Optional;

public interface ModelRepository extends Repository<ModelNumber, Model> {

    Optional<Model> findByModelNumber(ModelNumber modelnum);
}
