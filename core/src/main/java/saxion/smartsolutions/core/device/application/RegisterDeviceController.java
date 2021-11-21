package saxion.smartsolutions.core.device.application;

import saxion.smartsolutions.core.PersistenceContext;
import saxion.smartsolutions.core.device.domain.Device;
import saxion.smartsolutions.core.device.repo.DeviceRepository;
import saxion.smartsolutions.core.value.Designation;


public class RegisterDeviceController {

    private DeviceRepository repo = PersistenceContext.repositories().deviceRepository();

    public RegisterDeviceController() {

    }

    public Device registerDevice(Designation name) {
        return repo.save(new Device(name));
    }
}
