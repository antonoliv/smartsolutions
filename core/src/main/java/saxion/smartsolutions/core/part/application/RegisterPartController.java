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


public class RegisterPartController {

    private PartRepository repo = PersistenceContext.repositories().partRepository();
    private BrandRepository brp = PersistenceContext.repositories().brandRepository();
    private ModelRepository mrp = PersistenceContext.repositories().modelRepository();

    public RegisterPartController() {

    }

    public Part registerPart(Designation name, PartNumber partnum, Designation brand, ModelNumber model) {
        Brand b;
        try {
            b = brp.findByName(brand).get();
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Brand not registered in the system.");
        }
        Model m;
        System.out.println(model.model);
        try {
            m = mrp.findByModelNumber(model).get();
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Model not registered in the system.");
        }
        return repo.save(new Part(name, partnum, b, m));
    }
}
