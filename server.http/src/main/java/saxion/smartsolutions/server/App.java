package saxion.smartsolutions.server;

import org.hibernate.service.spi.ServiceException;
import saxion.smartsolutions.core.PersistenceContext;
import saxion.smartsolutions.core.brand.application.RegisterBrandController;
import saxion.smartsolutions.core.device.application.RegisterDeviceController;
import saxion.smartsolutions.core.model.application.RegisterModelController;
import saxion.smartsolutions.core.model.domain.ModelNumber;
import saxion.smartsolutions.core.part.application.RegisterPartController;
import saxion.smartsolutions.core.part.domain.Part;
import saxion.smartsolutions.core.part.domain.PartNumber;
import saxion.smartsolutions.core.part.domain.Stock;
import saxion.smartsolutions.core.property.application.RegisterPropertyController;
import saxion.smartsolutions.core.value.Designation;
import saxion.smartsolutions.server.http.HTTPServer;

import java.io.IOException;

public class App {

    public static void main(String[] args) {
        try {
            HTTPServer server = new HTTPServer(new AppParser());
            new RegisterBrandController().registerBrand(Designation.valueOf("Samsung"));
            new RegisterDeviceController().registerDevice(Designation.valueOf("TV"));
            new RegisterModelController().registerModel(Designation.valueOf("test"), ModelNumber.valueOf("test"), Designation.valueOf("TV"), Designation.valueOf("Samsung"));
            new RegisterModelController().registerModel(Designation.valueOf("test"), ModelNumber.valueOf("test2"), Designation.valueOf("TV"), Designation.valueOf("Samsung"));
            RegisterPartController ctrl = new RegisterPartController();
            RegisterPropertyController pctrl = new RegisterPropertyController();
            pctrl.registerProperty(Designation.valueOf("Width"));
            pctrl.registerProperty(Designation.valueOf("Height"));
            pctrl.registerProperty(Designation.valueOf("Length"));
            ctrl.registerPart(Designation.valueOf("joao"), PartNumber.valueOf("joao1"), Designation.valueOf("Samsung"), ModelNumber.valueOf("test"), Stock.valueOf(3));
            ctrl.registerPart(Designation.valueOf("carlos"), PartNumber.valueOf("joao1"), Designation.valueOf("Samsung"), ModelNumber.valueOf("test"), Stock.valueOf(4));
            ctrl.registerPart(Designation.valueOf("tres"), PartNumber.valueOf("joao1"), Designation.valueOf("Samsung"), ModelNumber.valueOf("test"), Stock.valueOf(2));
            ctrl.registerPart(Designation.valueOf("joao"), PartNumber.valueOf("joao5"), Designation.valueOf("Samsung"), ModelNumber.valueOf("test"), Stock.valueOf(1));
            ctrl.registerPart(Designation.valueOf("joao"), PartNumber.valueOf("joao5"), Designation.valueOf("Samsung"), ModelNumber.valueOf("test"), Stock.valueOf(4));
            Part test = ctrl.registerPart(Designation.valueOf("joao"), PartNumber.valueOf("joao5"), Designation.valueOf("Samsung"), ModelNumber.valueOf("test2"), Stock.valueOf(0));
            test.addCompatibleModel(PersistenceContext.repositories().modelRepository().ofIdentity(ModelNumber.valueOf("test")).get());
            PersistenceContext.repositories().partRepository().save(test);
            server.start();
        } catch (ServiceException e) {
            System.out.println("Database Error: " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
