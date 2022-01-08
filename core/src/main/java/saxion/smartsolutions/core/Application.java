package saxion.smartsolutions.core;

public class Application {
    private static final Settings SETTINGS = new Settings();

    private Application() {
        // private visibility to ensure singleton & utility
    }

    public static Settings settings() {
        return SETTINGS;
    }
}
