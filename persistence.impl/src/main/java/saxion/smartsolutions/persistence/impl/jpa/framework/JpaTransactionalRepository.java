package saxion.smartsolutions.persistence.impl.jpa.framework;

import saxion.smartsolutions.core.concepts.DomainEntity;

import javax.persistence.RollbackException;
import java.util.Map;

public class JpaTransactionalRepository<T extends DomainEntity<I>, K, I extends Comparable<I>> extends JpaWithTransactionalContextRepository<T, K, I> {

    public JpaTransactionalRepository(final String persistenceUnitName, final String identityFieldName) {
        super(new JpaTransactionalContext(persistenceUnitName), identityFieldName);
    }

    JpaTransactionalRepository(final String persistenceUnitName, final Class<T> classz,
                                             final String identityFieldName) {
        super(new JpaTransactionalContext(persistenceUnitName), classz, identityFieldName);
    }

    public JpaTransactionalRepository(final String persistenceUnitName,
                                      @SuppressWarnings({ "rawtypes", "java:S3740" }) final Map properties,
                                      final String identityFieldName) {
        super(new JpaTransactionalContext(persistenceUnitName, properties), identityFieldName);
    }

    JpaTransactionalRepository(final String persistenceUnitName,
                                             @SuppressWarnings({ "rawtypes", "java:S3740" }) final Map properties,
                                             final Class<T> classz,
                                             final String identityFieldName) {
        super(new JpaTransactionalContext(persistenceUnitName, properties), classz,
                identityFieldName);
    }

    @Override
    public void delete(final T entity) {
        try {
            context().beginTransaction();
            super.delete(entity);
            context().commit();
        } catch (final RollbackException e) {
            handleRollbackException(e);
        } finally {
            context().close();
        }
    }

    /**
     * Removes the entity with the specified ID from the repository.
     *
     * @param entityId
     * @throws IntegrityViolationException
     * @throws UnsupportedOperationException
     *             if the delete operation makes no sense for this repository
     */
    @Override
    public void deleteById(final K entityId) {
        try {
            context().beginTransaction();
            super.deleteById(entityId);
            context().commit();
        } catch (final RollbackException e) {
            handleRollbackException(e);
        } finally {
            context().close();
        }
    }

    /**
     * Adds <b>and commits</b> a new entity to the persistence store.
     *
     * @param entity
     * @return the newly created persistent object
     * @throws IntegrityViolationException
     * @throws RollbackException
     */
    @Override
    public T create(final T entity) {
        try {
            context().beginTransaction();
            super.create(entity);
            context().commit();
        } catch (final RollbackException e) {
            handleRollbackException(e);
        } finally {
            context().close();
        }

        return entity;
    }

    /**
     * Inserts or updates an entity.
     *
     * <p>
     * Note that you should reference the return saxion.smartsolutions.core.value to use the persisted entity, as the original
     * object passed as argument might be copied to a new object.
     *
     * <pre>
     * <code>
     * Person p = ...
     * p.method1(..);
     * ...
     * Person u = repo.save(p); // p should not be used afterwards
     * u.method2(..);
     * ...
     * </code>
     * </pre>
     *
     * <p>
     * check
     * <a href=
     * "http://blog.xebia.com/2009/03/23/jpa-implementation-patterns-saving-detached-entities/"
     * > JPA implementation patterns</a> for a discussion on saveOrUpdate() Behaviour and merge()
     *
     * @param entity
     * @return the persisted entity - might be a different object than the parameter
     * @throws ConcurrencyException
     * @throws IntegrityViolationException
     * @throws RollbackException
     */
    @Override
    @SuppressWarnings("squid:S1226")
    public <S extends T> S save(S entity) {
        if(entity == null) {
            throw new IllegalArgumentException("Entity cannot be null.");
        }

        try {
            context().beginTransaction();
            entity = super.save(entity);
            context().commit();
        } catch (final RollbackException e) {
            handleRollbackException(e);
        } finally {
            context().close();
        }

        return entity;
    }

    private RuntimeException handleRollbackException(final RollbackException e) {
        // get the cause PersistenceException
        /*final Throwable ex = e.getCause();
        if (ex.getCause() instanceof OptimisticLockException) {
            throw new ConcurrencyException(e);
        }
        if (ex.getCause() instanceof EntityExistsException
                || ex.getCause() instanceof ConstraintViolationException) {
            throw new IntegrityViolationException(e);
        } else {
            throw e;
        }*/
        throw e;
    }
}
