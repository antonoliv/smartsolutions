package saxion.smartsolutions.core.part.repo;

import saxion.smartsolutions.core.concepts.Repository;
import saxion.smartsolutions.core.model.domain.Model;
import saxion.smartsolutions.core.part.domain.Part;
import saxion.smartsolutions.core.part.domain.PartNumber;
import saxion.smartsolutions.core.property.domain.Property;
import saxion.smartsolutions.core.value.Designation;

import java.util.Map;
import java.util.Optional;

public interface PartRepository extends Repository<Long, Part> {

    Optional<Part> findByPartNumber(PartNumber part);

    Iterable<Part> searchPart(Designation name, Model model, PartNumber partNumber, Map<Property, Designation> props);

}
