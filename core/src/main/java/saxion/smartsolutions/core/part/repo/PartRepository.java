package saxion.smartsolutions.core.part.repo;

import saxion.smartsolutions.core.concepts.Repository;
import saxion.smartsolutions.core.model.domain.Model;
import saxion.smartsolutions.core.part.domain.Part;
import saxion.smartsolutions.core.part.domain.PartNumber;
import saxion.smartsolutions.core.value.Designation;

import java.util.Optional;

public interface PartRepository extends Repository<Long, Part> {

    Optional<Part> findByPartNumber(PartNumber part);

    Optional<Part> findByCode(Long id);

    Iterable<Part> searchPart(Designation name, Model model, PartNumber partNumber);
}
