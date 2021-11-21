package saxion.smartsolutions.persistence.impl.jpa.framework;

import saxion.smartsolutions.core.TransactionalContext;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;


/**
 * A utility class for providing transactional context to JPA repositories not running in
 * containers. As such, this class creates its own {@link javax.persistence.EntityManagerFactory
 * EntityManagerFactory} instead of using an injected EMF by the container.
 *
 * @author Paulo Gandra Sousa
 */
public class JpaTransactionalContext implements TransactionalContext {

    private final String persistenceUnitName;

    private static EntityManagerFactory singletonEMF;

    private EntityManager entityManager;

    @SuppressWarnings({ "rawtypes", "java:S3740" })
    private final Map properties = new HashMap();

    @SuppressWarnings({ "rawtypes", "unchecked", "java:S3740" })
    public JpaTransactionalContext(final String persistenceUnitName, final Map properties) {
        if(persistenceUnitName == null || persistenceUnitName.trim().isEmpty()) {
            throw new IllegalArgumentException("Persistence Unit name cannot be null or empty.");
        }

        this.persistenceUnitName = persistenceUnitName;
        this.properties.putAll(properties);
        entityManagerFactory();
    }

    @SuppressWarnings({ "rawtypes", "java:S3740" })
    public JpaTransactionalContext(final String persistenceUnitName) {
        this(persistenceUnitName, new HashMap());
    }

    protected final synchronized EntityManagerFactory entityManagerFactory() {
        if (singletonEMF == null) {
            System.out.println(persistenceUnitName);

            singletonEMF = Persistence.createEntityManagerFactory(persistenceUnitName, properties);
        }
        return singletonEMF;
    }

    protected EntityManager entityManager() {
        if (entityManager == null || !entityManager.isOpen()) {
            entityManager = entityManagerFactory().createEntityManager();
        }
        return entityManager;
    }

    @Override
    public void beginTransaction() {
        final EntityTransaction tx = entityManager().getTransaction();
        tx.begin();
    }

    @Override
    public void commit() {
        entityManager().getTransaction().commit();
    }

    @Override
    public void rollback() {
        entityManager().getTransaction().rollback();
    }

    @Override
    public void close() {
        if (isActive()) {
            rollback();
        }
        entityManager().close();
    }

    @Override
    public boolean isActive() {
        return entityManager().getTransaction().isActive();
    }
}
