package saxion.smartsolutions.core.part.repo;

import saxion.smartsolutions.core.concepts.Repository;
import saxion.smartsolutions.core.part.domain.Part;
import saxion.smartsolutions.core.part.domain.PartNumber;

import java.util.Optional;

public interface PartRepository extends Repository<PartNumber, Part> {

    Optional<Part> findByPartNumber(PartNumber part);
}
