package saxion.smartsolutions.persistence.impl.jpa;

import saxion.smartsolutions.core.model.domain.Model;
import saxion.smartsolutions.core.part.domain.Part;
import saxion.smartsolutions.core.part.domain.PartNumber;
import saxion.smartsolutions.core.part.repo.PartRepository;
import saxion.smartsolutions.core.property.domain.Property;
import saxion.smartsolutions.core.value.Designation;

import javax.persistence.TypedQuery;
import java.util.Map;
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
    public Iterable<Part> searchPart(Designation name, Model model, PartNumber partNumber, Map<Property, Designation> props) {
        if(name == null && model == null && partNumber == null && props.isEmpty()) {
            throw new IllegalArgumentException("At least one data field must be provided.");
        }
        String q = "SELECT e FROM Part e ";
        boolean and = false;
        boolean where = true;
        if(!props.isEmpty()) {
            q += "JOIN e.model.properties p WHERE ";
            where = false;
            int i = 1;
            for(Property p : props.keySet()) {
                if(and) {
                    q += "AND ";
                }
                q += "p.id = :prop" + i + "name AND p.name = :prop" + i + "value ";
                and = true;
                i++;
            }
        }
        if(model != null) {
            if(where) {
                q += "WHERE ";
            }
            if(and) {
                q += "AND ";
            }
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
            q += "e.partNumber = :partnum ";
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
        if(!props.isEmpty()) {
            int i = 1;
            for(Property prop : props.keySet()) {
                ret.setParameter("prop" + i + "name", prop.getId());
                ret.setParameter("prop" + i + "value", props.get(prop).toString());
                i++;
            }
        }
        return ret.getResultList();
    }
}
