package saxion.smartsolutions.persistence.impl.jpa;

import saxion.smartsolutions.core.device.domain.Device;
import saxion.smartsolutions.core.device.repo.DeviceRepository;
import saxion.smartsolutions.core.value.Designation;

import java.util.Optional;

/**
 * Implementation of DeviceRepository in the JPA Framework
 */
public class JpaDeviceRepository extends JpaRepository<Device, Long, Designation> implements DeviceRepository {

    public JpaDeviceRepository() {
        super("name");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Device> findByName(final Designation name) {
        return ofIdentity(name);
    }
}
