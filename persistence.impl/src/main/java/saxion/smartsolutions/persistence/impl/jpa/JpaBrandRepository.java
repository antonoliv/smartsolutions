package saxion.smartsolutions.persistence.impl.jpa;

import saxion.smartsolutions.core.brand.domain.Brand;
import saxion.smartsolutions.core.brand.repo.BrandRepository;
import saxion.smartsolutions.core.value.Designation;

import java.util.Optional;

public class JpaBrandRepository extends JpaRepository<Brand, Long, Designation> implements BrandRepository {

    public JpaBrandRepository() {
        super("generic_name");
    }

    @Override
    public Optional<Brand> findByName(Designation name) {
        return ofIdentity(name);
    }
}
