package saxion.smartsolutions.core.concepts;

import java.util.Optional;

public interface Repository<I extends Comparable<I>, T extends DomainEntity<I>> {

    <S extends T> S save(final S entity);

    Optional<T> ofIdentity(final I id);

    default boolean containsOfIdentity(final I id) {
        return ofIdentity(id).isPresent();
    }

    default boolean contains(final T entity) {
        return containsOfIdentity(entity.identity());
    }

    void delete(final T entity);

    void deleteOfIdentity(final I entityId);

    long count();

    default long size() {
        return count();
    }

    default void remove(final T entity) {
        delete(entity);
    }

    default void removeOfIdentity(final I entityId) {
        deleteOfIdentity(entityId);
    }
}
