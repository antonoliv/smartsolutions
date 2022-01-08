package saxion.smartsolutions.core.model.domain;

import org.json.JSONArray;
import org.json.JSONObject;
import saxion.smartsolutions.core.brand.domain.Brand;
import saxion.smartsolutions.core.concepts.DomainEntity;
import saxion.smartsolutions.core.device.domain.Device;
import saxion.smartsolutions.core.property.domain.Property;
import saxion.smartsolutions.core.value.Designation;

import javax.persistence.*;
import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Represents a model line in the application
 */
@Entity
public class Model implements DomainEntity<ModelNumber> {

    /**
     * Database generated id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Version
    private long version;

    /**
     * Generic name for model
     */
    @Column(nullable = false, unique = true)
    private Designation name;

    /**
     * Model number of the model
     */
    @AttributeOverride(name = "model", column = @Column(name = "model_number", nullable = false, unique = true))
    private ModelNumber modelNumber;

    /**
     * Type of Device
     */
    @ManyToOne
    @Column(nullable = false)
    private Device type;

    /**
     * Brand of model
     */
    @ManyToOne
    @Column(nullable = false)
    private Brand brand;

    /**
     * Set of manuals for the manual
     */
    @ElementCollection
    @CollectionTable(name="Manuals")
    private Set<File> manuals;

    /**
     * Values of properties of the model
     */
    @ElementCollection
    @CollectionTable(name="Properties")
    @Column(name = "value", nullable = false)
    @MapKeyJoinColumn(name = "property")
    private Map<Property, Designation> properties;

    /**
     * Constructs a new Model with the given parameters (including properties and manuals)
     * @param name generic name of the model
     * @param modelNumber model number
     * @param type type of device
     * @param brand brand of the model
     * @param manuals manuals of the model
     * @param properties property values
     */
    public Model(Designation name, ModelNumber modelNumber, Device type, Brand brand, Set<File> manuals, Map<Property, Designation> properties) {
        if (name == null || brand == null || manuals == null || type == null || modelNumber == null) {
            throw new IllegalArgumentException("Arguments cannot be null.");
        }
        this.name = name;
        this.brand = brand;
        this.modelNumber = modelNumber;
        this.type = type;
        this.manuals = new HashSet<>(manuals);
        this.properties = new HashMap<>(properties);
    }

    /**
     * Constructs a new Model with the given parameters
     * @param name generic name of the model
     * @param modelNumber model number
     * @param type type of device
     * @param brand brand of the model
     */
    public Model(Designation name, ModelNumber modelNumber, Device type, Brand brand) {
        if (name == null || brand == null || modelNumber == null || type == null) {
            throw new IllegalArgumentException("Arguments cannot be null.");
        }
        this.name = name;
        this.brand = brand;
        this.modelNumber = modelNumber;
        this.type = type;
        this.manuals = new HashSet<>();
        this.properties = new HashMap<>();
    }

    /**
     * Constructs a new Model with the given parameters (including properties)
     * @param name generic name of the model
     * @param modelNumber model number
     * @param type type of device
     * @param brand brand of the model
     * @param properties property values
     */
    public Model(Designation name, ModelNumber modelNumber, Device type, Brand brand, Map<Property, Designation> properties) {
        if (name == null || brand == null || modelNumber == null || type == null) {
            throw new IllegalArgumentException("Arguments cannot be null.");
        }
        this.name = name;
        this.brand = brand;
        this.modelNumber = modelNumber;
        this.type = type;
        this.manuals = new HashSet<>();
        this.properties = new HashMap<>(properties);
    }

    // Empty Constructor
    public Model() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ModelNumber identity() {
        return modelNumber;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean sameAs(Object other) {
        if (other == null || other.getClass() != this.getClass()) {
            return false;
        }
        Model oth = (Model) other;
        return modelNumber.equals(oth.modelNumber);
    }

    /**
     * Adds a manual to the model
     * @param manual manual to add
     * @return true if successful operation
     */
    public boolean addManual(final File manual) {
        manuals.add(manual);
        return true;
    }

    /**
     * Removes a manual from the model
     * @param manual manual to remove
     * @return true if successful operation
     */
    public boolean removeManual(final File manual) {
        manuals.remove(manual);
        return true;
    }

    /**
     * Replace a model's manuals
     * @param manuals set of manuals
     * @return true if successful operation
     */
    public boolean replaceManuals(Set<File> manuals) {
        this.manuals = new HashSet<>(manuals);
        return true;
    }

    public Designation getName() {
        return name;
    }

    public ModelNumber getModelNumber() {
        return modelNumber;
    }

    public Device getType() {
        return type;
    }

    public Brand getBrand() {
        return brand;
    }

    public Set<File> getManuals() {
        return new HashSet<>(manuals);
    }

    /**
     * Returns a json object of the model
     * @return json object of the model
     */
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.put("name", name.toString());
        json.put("model_number", modelNumber.toString());
        json.put("brand", brand.getName().toString());
        json.put("type", type.getName().toString());

        if(!properties.isEmpty()) {
            JSONArray arr = new JSONArray();
            for (Property p : properties.keySet()) {
                JSONObject obj = new JSONObject();
                obj.put("name", p.getName().toString());
                obj.put("value", properties.get(p).toString());
                arr.put(obj);
            }
            json.put("properties", arr);
        }
        return json;
    }
}
