package saxion.smartsolutions.core.part.domain;


import org.json.JSONArray;
import org.json.JSONObject;
import saxion.smartsolutions.core.brand.domain.Brand;
import saxion.smartsolutions.core.concepts.DomainEntity;
import saxion.smartsolutions.core.model.domain.Model;
import saxion.smartsolutions.core.value.Designation;

import javax.persistence.*;
import java.io.File;
import java.util.HashSet;
import java.util.Set;


@Entity
public class Part implements DomainEntity<Long> {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Version
    private long version;

    @Column(nullable = false)
    private Designation name;

    @AttributeOverride(name = "partnum", column = @Column(name = "part_number", nullable = false))
    private PartNumber partNumber;

    @ManyToOne
    private Brand brand;

    @ManyToOne
    private Model model;

    private Stock stock;

    @ManyToMany
    private Set<Model> compatibleModels;

    @ElementCollection
    private Set<File> pictures;

    public Part(Designation name, PartNumber part, Brand brand, Model model, Stock stock) {
        if (name == null || part == null || brand == null) {
            throw new IllegalArgumentException("Arguments cannot be null.");
        }
        this.name = name;
        this.partNumber = part;
        this.brand = brand;
        this.model = model;
        this.stock = stock;
        this.compatibleModels = new HashSet<>();
        this.pictures = new HashSet<>();

    }

    public Part(Designation name, PartNumber part, Brand brand, Model model, Stock stock, Set<Model> compatibleModels) {
        if (name == null || part == null || brand == null || compatibleModels == null || pictures == null) {
            throw new IllegalArgumentException("Arguments cannot be null.");
        }
        this.name = name;
        this.partNumber = part;
        this.brand = brand;
        this.model = model;
        this.stock = stock;
        this.compatibleModels = new HashSet<>(compatibleModels);
        this.pictures = new HashSet<>();
    }

    protected Part() {
    }

    @Override
    public Long identity() {
        return id;
    }

    @Override
    public boolean sameAs(Object other) {
        return partNumber.equals(other);
    }

    public boolean addCompatibleModel(Model model) {
        if (this.model.equals(model)) {
            return false;
        }
        return this.compatibleModels.add(model);
    }

    public String toString() {
        return String.valueOf(id);
    }

    public Designation getName() {
        return name;
    }

    public PartNumber getPartNumber() {
        return partNumber;
    }

    public Brand getBrand() {
        return brand;
    }

    public Model getModel() {
        return model;
    }

    public Stock getStock() {
        return stock;
    }

    public Set<Model> getCompatibleModels() {
        return new HashSet<>(compatibleModels);
    }

    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.put("id", identity());
        json.put("name", getName().toString());
        json.put("part_number", getPartNumber().toString());
        json.put("model_number", getModel().getModelNumber().toString());
        json.put("stock", getStock().getStock());
        json.put("brand", getBrand().getName().toString());
        json.put("device_type", getModel().getType().getName().toString());

        JSONArray arr = new JSONArray();
        for (Model m : compatibleModels) {
            arr.put(m.getModelNumber().toString());
        }

        json.put("compatible_models", arr);
        return json;
    }
}
