package saxion.smartsolutions.core.model.application;

import saxion.smartsolutions.core.PersistenceContext;
import saxion.smartsolutions.core.brand.domain.Brand;
import saxion.smartsolutions.core.brand.repo.BrandRepository;
import saxion.smartsolutions.core.device.domain.Device;
import saxion.smartsolutions.core.device.repo.DeviceRepository;
import saxion.smartsolutions.core.model.domain.Model;
import saxion.smartsolutions.core.model.domain.ModelNumber;
import saxion.smartsolutions.core.model.repo.ModelRepository;
import saxion.smartsolutions.core.value.Designation;


public class RegisterModelController {

    private ModelRepository repo = PersistenceContext.repositories().modelRepository();
    private BrandRepository brp = PersistenceContext.repositories().brandRepository();
    private DeviceRepository trp = PersistenceContext.repositories().deviceRepository();

    public RegisterModelController() {

    }

    public Model registerModel(Designation name, ModelNumber model, Designation type, Designation brand) {
        Brand b = brp.findByName(brand).get();
        Device t = trp.findByName(type).get();
        return repo.save(new Model(name, model, t, b));
    }
}
