package brand.application;

import brand.domain.Brand;
import brand.repo.BrandRepository;
import persistence.PersistenceContext;
import value.Designation;

public class RegisterBrandController {

    private BrandRepository repo = PersistenceContext.repositories().brandRepository();

    public RegisterBrandController() {

    }

    public Brand registerBrand(final String name) {
        return repo.save(new Brand(Designation.valueOf(name)));
    }
}
