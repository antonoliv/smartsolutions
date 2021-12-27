package saxion.smartsolutions.core.part.application;

import saxion.smartsolutions.core.PersistenceContext;
import saxion.smartsolutions.core.model.domain.Model;
import saxion.smartsolutions.core.model.domain.ModelNumber;
import saxion.smartsolutions.core.model.repo.ModelRepository;
import saxion.smartsolutions.core.part.domain.Part;
import saxion.smartsolutions.core.part.domain.PartNumber;
import saxion.smartsolutions.core.part.repo.PartRepository;
import saxion.smartsolutions.core.value.Designation;

import java.util.NoSuchElementException;

public class SearchPartController {

    private PartRepository repo = PersistenceContext.repositories().partRepository();
    private ModelRepository modelRepo = PersistenceContext.repositories().modelRepository();

    public Iterable<Part> searchPart(Designation name, ModelNumber modelnumber, PartNumber partnumber) {
        Model m = null;
        try {
            if(modelnumber != null) {
                m = modelRepo.findByModelNumber(modelnumber).get();
            }
        } catch(NoSuchElementException e) {
            throw new IllegalArgumentException("Model does not exist.");
        }
        return repo.searchPart(name, m, partnumber);
    }
}
