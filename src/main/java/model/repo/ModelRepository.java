package model.repo;

import concepts.Repository;
import model.domain.Model;
import model.domain.ModelNumber;

import java.util.Optional;

public interface ModelRepository extends Repository<ModelNumber, Model> {

    Optional<Model> findByModelNumber(ModelNumber modelnum);
}
