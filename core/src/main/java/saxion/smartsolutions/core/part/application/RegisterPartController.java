package saxion.smartsolutions.core.part.application;

import saxion.smartsolutions.core.PersistenceContext;
import saxion.smartsolutions.core.brand.domain.Brand;
import saxion.smartsolutions.core.brand.repo.BrandRepository;
import saxion.smartsolutions.core.model.domain.Model;
import saxion.smartsolutions.core.model.domain.ModelNumber;
import saxion.smartsolutions.core.model.repo.ModelRepository;
import saxion.smartsolutions.core.part.domain.Part;
import saxion.smartsolutions.core.part.domain.PartNumber;
import saxion.smartsolutions.core.part.repo.PartRepository;
import saxion.smartsolutions.core.value.Designation;

import java.util.NoSuchElementException;


public class RegisterPartController {

    private PartRepository repo = PersistenceContext.repositories().partRepository();
    private BrandRepository brp = PersistenceContext.repositories().brandRepository();
    private ModelRepository mrp = PersistenceContext.repositories().modelRepository();

    public RegisterPartController() {

    }

    public Part registerPart(Designation name, PartNumber partnum, Designation brand, ModelNumber model) {
        Brand b;
        Model m;
        try {
            b = brp.findByName(brand).get();
        } catch(NoSuchElementException e) {
            throw new IllegalArgumentException("Brand does not exist.");
        }
        try {
            m = mrp.findByModelNumber(model).get();
        } catch(NoSuchElementException e) {
            throw new IllegalArgumentException("Model does not exist.");
        }
        return repo.save(new Part(name, partnum, b, m));
    }
}
