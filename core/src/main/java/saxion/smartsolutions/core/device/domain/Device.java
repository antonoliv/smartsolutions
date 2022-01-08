package saxion.smartsolutions.core.device.domain;

import saxion.smartsolutions.core.concepts.DomainEntity;
import saxion.smartsolutions.core.property.domain.Property;
import saxion.smartsolutions.core.value.Designation;

import javax.persistence.*;
import java.util.Set;

/**
 * Represents a type of device in the application
 */
@Entity
public class Device implements DomainEntity<Designation> {

    /**
     * Database generated id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Version
    private long version;

    /**
     * Device type's designation
     */
    @Column(nullable = false, unique = true)
    private Designation name;

    /**
     * Constructs a type with the given designation
     * @param name designation of type
     */
    public Device(final Designation name) {
        if (name == null) {
            throw new IllegalArgumentException("Arguments cannot be null.");
        }
        this.name = name;
    }

    // Empty Constructor
    public Device() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Designation identity() {
        return name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean sameAs(Object other) {
        if (other == null || other.getClass() != this.getClass()) {
            return false;
        }
        Device type = (Device) other;
        return type.name.equals(this.name);
    }


    public Designation getName() {
        return name;
    }
}
