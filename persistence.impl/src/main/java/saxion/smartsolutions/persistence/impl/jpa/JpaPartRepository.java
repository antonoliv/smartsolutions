package saxion.smartsolutions.persistence.impl.jpa;

import saxion.smartsolutions.core.model.domain.Model;
import saxion.smartsolutions.core.part.domain.Part;
import saxion.smartsolutions.core.part.domain.PartNumber;
import saxion.smartsolutions.core.part.repo.PartRepository;
import saxion.smartsolutions.core.value.Designation;

import javax.persistence.TypedQuery;
import java.util.Optional;

public class JpaPartRepository extends JpaRepository<Part, Long, Long> implements PartRepository {

    public JpaPartRepository() {
        super("id");
    }

    @Override
    public Optional<Part> findByPartNumber(PartNumber part) {
        return Optional.empty();
    }

    @Override
    public Optional<Part> findByCode(Long id) {
        return this.ofIdentity(id);
    }

    @Override
    public Iterable<Part> searchPart(Designation name, Model model, PartNumber partNumber) {
        if(name == null && model == null && partNumber == null) {
            throw new IllegalArgumentException("At least one data field must be provided.");
        }
        String q = "SELECT e FROM Part e WHERE ";
        boolean and = false;
        if(model != null) {
            q += "(e.model = :model OR :model MEMBER OF e.compatibleModels) ";
            and = true;
        }
        if(name != null) {
            if(and) {
                q += "AND ";
            }
            q += "e.name = :name ";
            and = true;
        }
        if(partNumber != null) {
            if(and) {
                q += "AND ";
            }
            q += "e.partNumber = :partnum";
        }
        final TypedQuery<Part> ret = createQuery(q, Part.class);
        if(name != null) {
            ret.setParameter("name", name);
        }
        if(model != null) {
            ret.setParameter("model", model);
        }
        if(partNumber != null) {
            ret.setParameter("partnum", partNumber);
        }
        return ret.getResultList();
    }
}
