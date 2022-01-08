package saxion.smartsolutions.core.concepts;

import java.util.Optional;

/**
 * Represents a repository in the application meant for managing an entity's persistence in the persistence context
 * @param <I> class of the business identity
 * @param <T> class of entity to be managed
 */
public interface Repository<I extends Comparable<I>, T extends DomainEntity<I>> {

    /**
     * Save an entity in the persistence context
     * @param entity entity to be saved
     * @param <S>
     * @return
     */
    <S extends T> S save(final S entity);

    /**
     * Find the entity with the given business identity
     * @param id business identity
     * @return entity if it exists
     */
    Optional<T> ofIdentity(final I id);

    /**
     * Check if database has an entity with the given business identity
     * @param id business identity
     * @return true if it exists
     */
    default boolean containsOfIdentity(final I id) {
        return ofIdentity(id).isPresent();
    }

    /**
     * Checks if database has given entity
     * @param entity entity
     * @return true if it exists
     */
    default boolean contains(final T entity) {
        return containsOfIdentity(entity.identity());
    }

    /**
     * Deletes the given entity from the persistence context
     * @param entity entity
     */
    void delete(final T entity);

    /**
     * Deletes the entity with the given identity from the persistence context
     * @param entityId entity's business identity
     */
    void deleteOfIdentity(final I entityId);

    /**
     * Returns the number of managed entities by the persistence context
     * @return number of managed entities
     */
    long count();

    /**
     * Returns a list of all managed entities
     * @return list of all managed entities
     */
    Iterable<T> findAll();

}
