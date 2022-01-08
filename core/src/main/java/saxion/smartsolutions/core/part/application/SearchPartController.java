package saxion.smartsolutions.core.part.application;

import saxion.smartsolutions.core.PersistenceContext;
import saxion.smartsolutions.core.model.domain.Model;
import saxion.smartsolutions.core.model.domain.ModelNumber;
import saxion.smartsolutions.core.model.repo.ModelRepository;
import saxion.smartsolutions.core.part.domain.Part;
import saxion.smartsolutions.core.part.domain.PartNumber;
import saxion.smartsolutions.core.part.repo.PartRepository;
import saxion.smartsolutions.core.property.domain.Property;
import saxion.smartsolutions.core.property.repo.PropertyRepository;
import saxion.smartsolutions.core.value.Designation;

import java.util.*;

public class SearchPartController {

    private final PartRepository repo = PersistenceContext.repositories().partRepository();
    private final ModelRepository modelRepo = PersistenceContext.repositories().modelRepository();
    private final PropertyRepository propRepo = PersistenceContext.repositories().propertyRepository();

    public Iterable<Part> searchPart(Designation name, ModelNumber modelnumber, PartNumber partnumber, Map<Designation, Designation> props) {
        Model m = null;
        try {
            if (modelnumber != null) {
                m = modelRepo.ofIdentity(modelnumber).get();
            }
        } catch (NoSuchElementException e) {
            return new ArrayList<>();
        }
        Map<Property, Designation> map = new HashMap<>();
        for(Designation key : props.keySet()) {
            try{
                Property p = propRepo.ofIdentity(key).get();
                map.put(p, props.get(key));
            } catch (NoSuchElementException e) {
                return new ArrayList<>();
            }
        }
        return repo.searchPart(name, m, partnumber, map);
    }


    public Optional<Part> findByID(long id) {
        return repo.ofIdentity(id);
    }
}
