package saxion.smartsolutions.core.device.application;

import org.hibernate.exception.ConstraintViolationException;
import saxion.smartsolutions.core.PersistenceContext;
import saxion.smartsolutions.core.device.domain.Device;
import saxion.smartsolutions.core.device.repo.DeviceRepository;
import saxion.smartsolutions.core.value.Designation;


public class RegisterDeviceController {

    private DeviceRepository repo = PersistenceContext.repositories().deviceRepository();

    public RegisterDeviceController() {

    }

    public Device registerDevice(Designation name) {
        try {
            return repo.save(new Device(name));
        } catch(ConstraintViolationException e) {
            throw new IllegalArgumentException("Device already registered.");
        }
    }
}
