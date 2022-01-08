package saxion.smartsolutions.core.brand.application;

import org.hibernate.exception.ConstraintViolationException;
import saxion.smartsolutions.core.PersistenceContext;
import saxion.smartsolutions.core.brand.domain.Brand;
import saxion.smartsolutions.core.brand.repo.BrandRepository;
import saxion.smartsolutions.core.value.Designation;

/**
 * Controller for registering a new brand of products in the platform
 */
public class RegisterBrandController {

    private final BrandRepository repo = PersistenceContext.repositories().brandRepository();

    public RegisterBrandController() {
    }

    /**
     * Registers a brand in the application
     * @param name name of the brand
     * @return created brand
     */
    public Brand registerBrand(final Designation name) {
        try {
            return repo.save(new Brand(name));
        } catch (ConstraintViolationException e) {
            throw new IllegalArgumentException("Brand already registered.");
        }
    }
}
