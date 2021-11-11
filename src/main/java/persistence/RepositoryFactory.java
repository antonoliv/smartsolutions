package persistence;

import brand.repo.BrandRepository;
import device.repo.DeviceRepository;
import model.repo.ModelRepository;

public interface RepositoryFactory {

    TransactionalContext newTransactionalContext();

    BrandRepository brandRepository();

    DeviceRepository deviceRepository();

    ModelRepository modelRepository();


}