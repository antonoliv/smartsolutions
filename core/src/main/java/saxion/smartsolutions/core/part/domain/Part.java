package saxion.smartsolutions.core.part.domain;

import saxion.smartsolutions.core.brand.domain.Brand;
import saxion.smartsolutions.core.concepts.DomainEntity;
import saxion.smartsolutions.core.model.domain.Model;
import saxion.smartsolutions.core.value.Designation;

import javax.persistence.*;
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

    @ManyToMany
    private Set<Model> compatibleModels;


    public Part(Designation name, PartNumber part, Brand brand, Model model) {
        if(name == null || part == null || brand == null) {
            throw new IllegalArgumentException("Arguments cannot be null.");
        }
        this.name = name;
        this.partNumber = part;
        this.brand = brand;
        this.model = model;
        this.compatibleModels = new HashSet<>();
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
        if(this.model.equals(model)) {
            return false;
        }
        return this.compatibleModels.add(model);
    }

    public String toString() {
        return String.valueOf(id);
    }

}
