package saxion.smartsolutions.persistence.impl.jpa;

import saxion.smartsolutions.core.brand.domain.Brand;
import saxion.smartsolutions.core.brand.repo.BrandRepository;
import saxion.smartsolutions.core.value.Designation;

import java.util.Optional;

/**
 * Implementation of BrandRepository in the JPA Framework
 */
public class JpaBrandRepository extends JpaRepository<Brand, Long, Designation> implements BrandRepository {

    public JpaBrandRepository() {
        super("name");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Brand> findByName(final Designation name) {
        return ofIdentity(name);
    }
}
