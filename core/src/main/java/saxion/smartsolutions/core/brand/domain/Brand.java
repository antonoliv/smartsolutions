package saxion.smartsolutions.core.brand.domain;

import saxion.smartsolutions.core.concepts.DomainEntity;
import saxion.smartsolutions.core.value.Designation;

import javax.persistence.*;

@Entity
public class Brand implements DomainEntity<Designation> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Version
    private long version;

    @AttributeOverride(name = "name", column = @Column(name = "generic_name", nullable = false, unique = true))
    private Designation generic_name;

    public Brand(Designation name) {
        this.generic_name = name;
    }

    public Brand() {
        this.generic_name = null;
    }

    @Override
    public Designation identity() {
        return generic_name;
    }

    @Override
    public boolean sameAs(Object other) {
        if(other == null || other.getClass() != this.getClass()) {
            return false;
        }
        Brand oth = (Brand) other;
        return generic_name.equals(oth.generic_name);
    }
}
