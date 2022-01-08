package saxion.smartsolutions.core.brand.domain;

import saxion.smartsolutions.core.concepts.DomainEntity;
import saxion.smartsolutions.core.value.Designation;

import javax.persistence.*;

/**
 * Represents a brand of products in the platform
 */
@Entity
public class Brand implements DomainEntity<Designation> {

    /**
     * Database Generated id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Version
    private long version;

    /**
     * Name of the Brand
     */
    @Column(nullable = false, unique = true)
    private Designation name;

    /**
     * Creates and instance of Brand with the given name
     * @param name name of the brand
     */
    public Brand(Designation name) {
        this.name = name;
    }

    // Empty Constructor
    public Brand() {
    }

    /**
     * Returns the identity of a Brand, which is its name
     * @return identity of brand
     */
    @Override
    public Designation identity() {
        return name;
    }

    /**
     * Checks if a brand has the same identity has another
     * @param other brand
     * @return true if brands have the same identity
     */
    @Override
    public boolean sameAs(final Object other) {
        if (other == null || other.getClass() != this.getClass()) {
            return false;
        }
        final Brand oth = (Brand) other;
        return name.equals(oth.name);
    }

    public Designation getName() {
        return name;
    }
}
