package saxion.smartsolutions.persistence.impl.jpa;

import saxion.smartsolutions.core.part.domain.Part;
import saxion.smartsolutions.core.part.domain.PartNumber;
import saxion.smartsolutions.core.part.repo.PartRepository;

import java.util.Optional;

public class JpaPartRepository extends JpaRepository<Part, Long, PartNumber> implements PartRepository {

    public JpaPartRepository() {
        super("part_number");
    }

    @Override
    public Optional<Part> findByPartNumber(PartNumber part) {
        return Optional.empty();
    }
}
