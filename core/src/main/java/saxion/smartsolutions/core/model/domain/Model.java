package saxion.smartsolutions.core.model.domain;

import saxion.smartsolutions.core.brand.domain.Brand;
import saxion.smartsolutions.core.concepts.DomainEntity;
import saxion.smartsolutions.core.device.domain.Device;
import saxion.smartsolutions.core.value.Designation;

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

    @Column(nullable = false, unique = true)
    private Designation name;

    @Column(nullable = false, unique = true)
    private ModelNumber model;

    @ManyToOne
    private Device type;

    @ManyToOne
    private Brand brand;

    @ElementCollection
    private Set<File> manuals;

    public Model(Designation name, ModelNumber model, Device type, Brand brand, Set<File> manuals) {
        if (name == null || brand == null || manuals == null || type == null || model == null) {
            throw new IllegalArgumentException("Arguments cannot be null.");
        }
        this.name = name;
        this.brand = brand;
        this.model = model;
        this.type = type;
        this.manuals = new HashSet<>(manuals);
    }

    public Model(Designation name, ModelNumber model, Device type, Brand brand) {
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

    public boolean addManual(File manual) {
        manuals.add(manual);
        return true;
    }

    public boolean removeManual(File manual) {
        manuals.remove(manual);
        return true;
    }

    public boolean replaceManuals(Set<File> manuals) {
        this.manuals = new HashSet<>(manuals);
        return true;
    }

}
