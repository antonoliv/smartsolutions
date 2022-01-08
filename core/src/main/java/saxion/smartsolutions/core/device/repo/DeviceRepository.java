package saxion.smartsolutions.core.device.repo;

import saxion.smartsolutions.core.concepts.Repository;
import saxion.smartsolutions.core.device.domain.Device;
import saxion.smartsolutions.core.value.Designation;

import java.util.Optional;

/**
 * Repository Interface for managing instances of types of devices in the persistence context
 */
public interface DeviceRepository extends Repository<Designation, Device> {

    /**
     * Finds a type of device in the persistence context with the given name
     * @param name designation of the type
     * @return type of device
     */
    Optional<Device> findByName(final Designation name);
}

