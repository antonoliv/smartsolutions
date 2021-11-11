package device.repo;

import concepts.Repository;
import device.domain.Device;
import device.domain.SerialNumber;

public interface DeviceRepository extends Repository<SerialNumber, Device> {
}
