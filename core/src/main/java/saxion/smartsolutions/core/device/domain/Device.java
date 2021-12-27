package saxion.smartsolutions.core.device.domain;

import saxion.smartsolutions.core.concepts.DomainEntity;
import saxion.smartsolutions.core.value.Designation;

import javax.persistence.*;

@Entity
public class Device implements DomainEntity<Designation> {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Version
    private long version;

    @AttributeOverride(name = "name", column = @Column(name = "generic_name", nullable = false, unique = true))
    private Designation generic_name;

    public Device(Designation name) {
        if(name == null) {
            throw new IllegalArgumentException("Arguments cannot be null.");
        }
        this.generic_name = name;
    }

    public Device() {
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
        Device type = (Device) other;
        return type.generic_name.equals(this.generic_name);
    }
}
