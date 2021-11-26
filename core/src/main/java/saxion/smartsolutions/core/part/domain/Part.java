package saxion.smartsolutions.core.part.domain;

import saxion.smartsolutions.core.brand.domain.Brand;
import saxion.smartsolutions.core.concepts.DomainEntity;
import saxion.smartsolutions.core.model.domain.Model;
import saxion.smartsolutions.core.value.Designation;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
public class Part implements DomainEntity<PartNumber>{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Version
    private long version;

    @Column(nullable = false)
    private Designation name;

    @Column(nullable = false, unique = true)
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
    public PartNumber identity() {
        return partNumber;
    }

    @Override
    public boolean sameAs(Object other) {
        return partNumber.equals(other);
    }

    public boolean addCompatibleModel(Model model) {
        return this.compatibleModels.add(model);
    }

}
