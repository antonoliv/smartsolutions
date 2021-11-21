package saxion.smartsolutions.core.part.application;

import saxion.smartsolutions.core.PersistenceContext;
import saxion.smartsolutions.core.model.domain.Model;
import saxion.smartsolutions.core.model.domain.ModelNumber;
import saxion.smartsolutions.core.model.repo.ModelRepository;
import saxion.smartsolutions.core.part.domain.Part;
import saxion.smartsolutions.core.part.domain.PartNumber;
import saxion.smartsolutions.core.part.repo.PartRepository;

public class AddModelController {

    private PartRepository repo = PersistenceContext.repositories().partRepository();
    private ModelRepository modelrepo = PersistenceContext.repositories().modelRepository();

    public Part addCompatibleModel(PartNumber part, ModelNumber model) {
        Part p = repo.findByPartNumber(part).get();
        Model m = modelrepo.findByModelNumber(model).get();
        p.addCompatibleModel(m);
        return repo.save(p);
    }
}
