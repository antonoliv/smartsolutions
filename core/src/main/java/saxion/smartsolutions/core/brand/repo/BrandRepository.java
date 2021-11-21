package saxion.smartsolutions.core.brand.repo;

import saxion.smartsolutions.core.brand.domain.Brand;
import saxion.smartsolutions.core.concepts.Repository;
import saxion.smartsolutions.core.value.Designation;

import java.util.Optional;

public interface BrandRepository extends Repository<Designation, Brand> {

    Optional<Brand> findByName(Designation name);
}
