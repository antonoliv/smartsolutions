package saxion.smartsolutions.core.model.application;

import saxion.smartsolutions.core.PersistenceContext;
import saxion.smartsolutions.core.brand.domain.Brand;
import saxion.smartsolutions.core.brand.repo.BrandRepository;
import saxion.smartsolutions.core.device.domain.Device;
import saxion.smartsolutions.core.device.repo.DeviceRepository;
import saxion.smartsolutions.core.model.domain.Model;
import saxion.smartsolutions.core.model.domain.ModelNumber;
import saxion.smartsolutions.core.model.repo.ModelRepository;
import saxion.smartsolutions.core.property.domain.Property;
import saxion.smartsolutions.core.property.repo.PropertyRepository;
import saxion.smartsolutions.core.value.Designation;

import javax.persistence.RollbackException;
import java.util.*;

/**
 * Controller for registering a new model in the application
 */
public class RegisterModelController {

    private final ModelRepository repo = PersistenceContext.repositories().modelRepository();
    private final BrandRepository brp = PersistenceContext.repositories().brandRepository();
    private final DeviceRepository trp = PersistenceContext.repositories().deviceRepository();
    private final PropertyRepository prp = PersistenceContext.repositories().propertyRepository();

    public RegisterModelController() {

    }

    /**
     * Registers a model with the given parameters in the application (including properties)
     * @param name generic name of the model
     * @param modelNumber model number
     * @param type type of device
     * @param brand brand of the model
     * @param properties property values
     */
    public Model registerModel(Designation name, ModelNumber modelNumber, Designation type, Designation brand, Map<Designation, Designation> properties) {
        Brand b;
        Device t;
        try {
            b = brp.findByName(brand).get();
        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException("Brand does not exist.");
        }
        try {
            t = trp.findByName(type).get();
        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException("Device does not exist.");
        }
        Map<Property, Designation> propertiesMap = new HashMap<>();
        for(Designation key : properties.keySet()) {
            Property prop = prp.ofIdentity(key).get();
            propertiesMap.put(prop, properties.get(key));
        }
        try {
            return repo.save(new Model(name, modelNumber, t, b, propertiesMap));
        } catch (RollbackException e) {
            throw new IllegalArgumentException("Model already registered.");
        }
    }

    /**
     * Registers a model with the given parameters in the application
     * @param name generic name of the model
     * @param modelNumber model number
     * @param type type of device
     * @param brand brand of the model
     */
    public Model registerModel(Designation name, ModelNumber modelNumber, Designation type, Designation brand) {
        Brand b;
        Device t;
        try {
            b = brp.findByName(brand).get();
        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException("Brand does not exist.");
        }
        try {
            t = trp.findByName(type).get();
        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException("Device does not exist.");
        }
        try {
            return repo.save(new Model(name, modelNumber, t, b));
        } catch (RollbackException e) {
            throw new IllegalArgumentException("Model already registered.");
        }
    }
}
