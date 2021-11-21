package saxion.smartsolutions.core.brand.domain;

import saxion.smartsolutions.core.concepts.DomainEntity;
import saxion.smartsolutions.core.value.Designation;

import javax.persistence.*;

@Entity
public class Brand implements DomainEntity<Designation> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Version
    private long version;

    @Column(nullable = false, unique = true)
    private Designation name;

    public Brand(Designation name) {
        this.name = name;
    }

    public Brand() {
        this.name = null;
    }

    @Override
    public Designation identity() {
        return name;
    }

    @Override
    public boolean sameAs(Object other) {
        if(other == null || other.getClass() != this.getClass()) {
            return false;
        }
        Brand oth = (Brand) other;
        return name.equals(oth.name);
    }
}
