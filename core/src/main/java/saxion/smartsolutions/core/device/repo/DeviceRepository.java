package saxion.smartsolutions.core.device.repo;

import saxion.smartsolutions.core.concepts.Repository;
import saxion.smartsolutions.core.device.domain.Device;
import saxion.smartsolutions.core.value.Designation;

import java.util.Optional;

public interface DeviceRepository extends Repository<Designation, Device> {

    Optional<Device> findByName(Designation name);
}

