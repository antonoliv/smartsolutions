package saxion.smartsolutions.persistence.impl.jpa;

import saxion.smartsolutions.core.Application;
import saxion.smartsolutions.core.RepositoryFactory;
import saxion.smartsolutions.core.TransactionalContext;
import saxion.smartsolutions.core.brand.repo.BrandRepository;
import saxion.smartsolutions.core.device.repo.DeviceRepository;
import saxion.smartsolutions.persistence.impl.jpa.framework.JpaTransactionalContext;
import saxion.smartsolutions.core.model.repo.ModelRepository;
import saxion.smartsolutions.core.part.repo.PartRepository;

public class JpaRepositoryFactory implements RepositoryFactory {

    private String punit = Application.settings().getPersistenceUnitName();

    @Override
    public TransactionalContext newTransactionalContext() {
        return new JpaTransactionalContext(punit);
    }

    @Override
    public BrandRepository brandRepository() {
        return new JpaBrandRepository();
    }

    @Override
    public ModelRepository modelRepository() {
        return new JpaModelRepository();
    }

    @Override
    public DeviceRepository deviceRepository() {
        return new JpaDeviceRepository();
    }

    @Override
    public PartRepository partRepository() {
        return new JpaPartRepository();
    }
}
