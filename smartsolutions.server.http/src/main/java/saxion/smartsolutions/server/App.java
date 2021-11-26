package saxion.smartsolutions.server;

import org.hibernate.service.spi.ServiceException;
import saxion.smartsolutions.core.brand.application.RegisterBrandController;
import saxion.smartsolutions.core.device.application.RegisterDeviceController;
import saxion.smartsolutions.core.model.application.RegisterModelController;
import saxion.smartsolutions.core.model.domain.ModelNumber;
import saxion.smartsolutions.core.value.Designation;
import saxion.smartsolutions.server.http.HTTPServer;

import java.io.IOException;

public class App {

    public static void main(String[] args) {
        try {
            HTTPServer server = new HTTPServer(new AppParser());
            new RegisterBrandController().registerBrand(Designation.valueOf("Phillips"));
            new RegisterDeviceController().registerDevice(Designation.valueOf("Television"));
            new RegisterModelController().registerModel(Designation.valueOf("test"), ModelNumber.valueOf("test"), Designation.valueOf("Television"),
                    Designation.valueOf("Phillips"));
            server.start();
        } catch (ServiceException e) {
            System.out.println("Database Error: " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
