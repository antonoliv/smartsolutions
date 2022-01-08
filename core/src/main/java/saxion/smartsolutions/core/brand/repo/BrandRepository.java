package saxion.smartsolutions.core.brand.repo;

import saxion.smartsolutions.core.brand.domain.Brand;
import saxion.smartsolutions.core.concepts.Repository;
import saxion.smartsolutions.core.value.Designation;

import java.util.Optional;

/**
 * Repository Interface for managing instances of brands in the persistence context
 */
public interface BrandRepository extends Repository<Designation, Brand> {

    /**
     * Finds a brand with the given name in the persistence context
     * @param name designation of brand
     * @return brand
     */
    Optional<Brand> findByName(final Designation name);
}
