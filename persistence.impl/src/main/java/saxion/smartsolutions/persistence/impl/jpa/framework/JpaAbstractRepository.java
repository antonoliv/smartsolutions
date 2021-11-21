package saxion.smartsolutions.persistence.impl.jpa.framework;

import saxion.smartsolutions.core.concepts.DomainEntity;
import saxion.smartsolutions.core.concepts.Repository;

import javax.persistence.*;

public abstract class JpaAbstractRepository<T extends DomainEntity<I>, K, I extends Comparable<I>> implements Repository<I, T> {

    @PersistenceUnit
    private EntityManagerFactory emFactory;
    private EntityManager entityManager;

    protected JpaAbstractRepository() {
        super();
    }

    protected EntityManagerFactory entityManagerFactory() {
        return emFactory;
    }

    protected EntityManager entityManager() {
        if (entityManager == null || !entityManager.isOpen()) {
            entityManager = entityManagerFactory().createEntityManager();
        }
        return entityManager;
    }

    protected <R> TypedQuery<R> createQuery(final String jpql, final Class<R> classz) {
        return entityManager().createQuery(jpql, classz);
    }

    protected <R> Query createNativeQuery(final String sql, final Class<R> classz) {
        return entityManager().createNativeQuery(sql, classz);
    }
}
