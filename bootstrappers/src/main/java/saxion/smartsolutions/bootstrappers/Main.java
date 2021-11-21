package saxion.smartsolutions.bootstrappers;

import saxion.smartsolutions.core.PersistenceContext;
import saxion.smartsolutions.core.value.Designation;


public class Main {

    public static void main(final String[] args) {

        PersistenceContext.repositories().brandRepository().deleteOfIdentity(Designation.valueOf("LG"));
//        new RegisterBrandController().registerBrand(Designation.valueOf("LG"));

    }
}
