package saxion.smartsolutions.persistence.impl.jpa.framework;

import saxion.smartsolutions.core.TransactionalContext;
import saxion.smartsolutions.core.concepts.DomainEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class JpaWithTransactionalContextRepository<T extends DomainEntity<I>, K, I extends Comparable<I>> extends JpaBaseRepository<T, K, I> {

    private JpaTransactionalContext txContext;

    public JpaWithTransactionalContextRepository(final TransactionalContext txCtx, final String identityFieldName) {
        super(identityFieldName);
        setTxCtx(txCtx);
    }

    /* package */ JpaWithTransactionalContextRepository(final TransactionalContext txCtx, final Class<T> classz,
                                                        final String identityFieldName) {
        super(classz, identityFieldName);
        setTxCtx(txCtx);
    }

    private void setTxCtx(final TransactionalContext txCtx) {
        if (!(txCtx instanceof JpaTransactionalContext)) {
            throw new IllegalArgumentException();
        }
        this.txContext = (JpaTransactionalContext) txCtx;
    }

    @Override
    protected EntityManagerFactory entityManagerFactory() {
        return txContext.entityManagerFactory();
    }

    @Override
    protected EntityManager entityManager() {
        return txContext.entityManager();
    }

    protected TransactionalContext context() {
        return txContext;
    }
}
