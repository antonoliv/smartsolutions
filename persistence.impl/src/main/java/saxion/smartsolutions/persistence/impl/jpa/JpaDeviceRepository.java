package saxion.smartsolutions.persistence.impl.jpa;

import saxion.smartsolutions.core.device.domain.Device;
import saxion.smartsolutions.core.device.repo.DeviceRepository;
import saxion.smartsolutions.core.value.Designation;

import java.util.Optional;

public class JpaDeviceRepository extends JpaRepository<Device, Long, Designation> implements DeviceRepository {

    public JpaDeviceRepository() {
        super("name");
    }

    @Override
    public Optional<Device> findByName(Designation name) {
        return ofIdentity(name);
    }
}
