package persistence;

import java.lang.reflect.InvocationTargetException;
import Application;

public final class PersistenceContext {
    private static RepositoryFactory theFactory;

    private PersistenceContext() {
        // ensure utility
    }

    /**
     * Returns the abstract repository factory configured in the application settings
     *
     * @return the repository factory
     */
    public static RepositoryFactory repositories() {
        if (theFactory == null) {
            final String factoryClassName = Application.settings().getRepositoryFactory();
            try {
                theFactory = (RepositoryFactory) Class.forName(factoryClassName).getDeclaredConstructor().newInstance();
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | IllegalArgumentException
                    | InvocationTargetException | NoSuchMethodException | SecurityException ex) {
                throw new IllegalStateException(
                        "Unable to dynamically load the Repository Factory: " + factoryClassName, ex);
            }
        }
        return theFactory;
    }
}
