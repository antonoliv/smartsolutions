package saxion.smartsolutions.core.device.domain;

import saxion.smartsolutions.core.concepts.DomainEntity;
import saxion.smartsolutions.core.value.Designation;

import javax.persistence.*;

@Entity
public class Device implements DomainEntity<Designation> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Version
    private long version;

    @Column(nullable = false)
    private Designation name;

    public Device(Designation name) {
        if(name == null) {
            throw new IllegalArgumentException("Arguments cannot be null.");
        }
        this.name = name;
    }

    public Device() {
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
        Device type = (Device) other;
        return type.name.equals(this.name);
    }
}
