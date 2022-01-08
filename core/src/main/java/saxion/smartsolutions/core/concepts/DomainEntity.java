package saxion.smartsolutions.core.concepts;

import java.io.Serializable;

/**
 * A domain entity in the application that is meant to be persisted
 * @param <I> business identity of the entity
 */
public interface DomainEntity<I extends Comparable<I>> extends Serializable {

    /**
     * Returns entity's business identity
     * @return business identity
     */
    I identity();

    /**
     * Checks if an entity has a given identity
     * @param id business identity
     * @return true if entity has given identity
     */
    default boolean hasIdentity(final I id) {
        return identity().equals(id);
    }

    /**
     * Checks if an entity is equal to another object
     * @param other other entity
     * @return true if equal
     */
    @Override
    boolean equals(Object other);

    /**
     * Returns entity's hash code
     * @return hash code
     */
    @Override
    int hashCode();

    /**
     * Checks if entity has the same identity as given entity
     * @param other other entity
     * @return true if they have the same identity
     */
    boolean sameAs(Object other);

}
