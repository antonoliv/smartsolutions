package saxion.smartsolutions.core.model.application;

import saxion.smartsolutions.core.PersistenceContext;
import saxion.smartsolutions.core.model.domain.Model;
import saxion.smartsolutions.core.model.domain.ModelNumber;
import saxion.smartsolutions.core.model.repo.ModelRepository;

import java.io.File;
import java.util.Set;

public class AddManualController {

    private ModelRepository repo = PersistenceContext.repositories().modelRepository();

    public AddManualController() {
    }

    public Model addManual(ModelNumber model, File manual) {
        Model m = repo.findByModelNumber(model).get();
        m.addManual(manual);
        return repo.save(m);
    }

    public Model removeManual(ModelNumber model, File manual) {
        Model m = repo.findByModelNumber(model).get();
        m.removeManual(manual);
        return repo.save(m);
    }

    public Model replaceManual(ModelNumber model, Set<File> manuals) {
        Model m = repo.findByModelNumber(model).get();
        m.replaceManuals(manuals);
        return repo.save(m);
    }
}
