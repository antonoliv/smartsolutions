package saxion.smartsolutions.persistence.impl.jpa;

import saxion.smartsolutions.core.Application;
import saxion.smartsolutions.core.concepts.DomainEntity;
import saxion.smartsolutions.persistence.impl.jpa.framework.JpaTransactionalRepository;

public class JpaRepository<T extends DomainEntity<I>, K, I extends Comparable<I>> extends JpaTransactionalRepository<T, K, I> {

    public JpaRepository(String identityFieldName) {
        super(Application.settings().getPersistenceUnitName(),
                Application.settings().getExtendedPersistenceProperties(), identityFieldName);
    }
}
