package model.domain;

import brand.domain.Brand;
import concepts.DomainEntity;
import device_type.domain.DeviceType;
import value.Designation;

import javax.persistence.*;
import java.io.File;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Model implements DomainEntity<ModelNumber> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Version
    private long version;

    private Designation name;

    private ModelNumber model;

    @ManyToOne
    private DeviceType type;

    @ManyToOne
    private Brand brand;

    @ElementCollection
    private Set<File> manuals;

    public Model(Designation name, ModelNumber model, DeviceType type, Brand brand, Set<File> manuals) {
        if (name == null || brand == null || manuals == null || type == null || model == null) {
            throw new IllegalArgumentException("Arguments cannot be null.");
        }
        this.name = name;
        this.brand = brand;
        this.model = model;
        this.type = type;
        this.manuals = new HashSet<>(manuals);
    }

    public Model(Designation name, Brand brand, ModelNumber model, DeviceType type) {
        if (name == null || brand == null || model == null || type == null) {
            throw new IllegalArgumentException("Arguments cannot be null.");
        }
        this.name = name;
        this.brand = brand;
        this.model = model;
        this.type = type;
        this.manuals = new HashSet<>();
    }

    public Model() {
        this.name = null;
        this.brand = null;
        this.manuals = null;
    }

    @Override
    public ModelNumber identity() {
        return model;
    }

    @Override
    public boolean sameAs(Object other) {
        if(other == null || other.getClass() != this.getClass()) {
            return false;
        }
        Model oth = (Model) other;
        return model.equals(oth.model);
    }
}
