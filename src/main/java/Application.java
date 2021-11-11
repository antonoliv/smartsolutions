public class Application {
    private static final Settings SETTINGS = new Settings();

    public static Settings settings() {
        return SETTINGS;
    }

    private Application() {
        // private visibility to ensure singleton & utility
    }
}
