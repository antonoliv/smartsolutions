package device.application;

import device.domain.Condition;
import device.domain.Device;
import device.domain.Image;
import device.domain.SerialNumber;
import device.repo.DeviceRepository;
import model.domain.Model;
import model.domain.ModelNumber;
import model.repo.ModelRepository;
import persistence.PersistenceContext;

import java.sql.Date;
import java.util.Set;

public class RegisterDeviceController {

    private DeviceRepository repo = PersistenceContext.repositories().deviceRepository();
    private ModelRepository modelrepo = PersistenceContext.repositories().modelRepository();

    public RegisterDeviceController() {

    }

    public Device registerDevice(String serial, String entryDate, String model, Condition.ConditionLevel level, String conditionobservation, Set<Image> images) {
        Model m = modelrepo.findByModelNumber(ModelNumber.valueOf(model)).get();
        return repo.save(new Device(SerialNumber.valueOf(serial), Date.valueOf(entryDate), m, Condition.valueOf(level, conditionobservation), images));
    }

    public Device registerDevice(String serial, String entryDate, String model, Condition.ConditionLevel level, String conditionobservation) {
        Model m = modelrepo.findByModelNumber(ModelNumber.valueOf(model)).get();
        return repo.save(new Device(SerialNumber.valueOf(serial), Date.valueOf(entryDate), m, Condition.valueOf(level, conditionobservation)));
    }
}
