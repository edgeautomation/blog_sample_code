package driver_steup;

public class DriverManagerFactory {

    public static DriverManager getManager(DriverType type) {

        DriverManager driverManager = null;
        String driver_path;

        switch (type) {
            case CHROME:
                DownloadDriver.downloadChrome();
                driverManager = new ChromeDriverManager();
                break;
            case FIREFOX:
                driver_path = DownloadDriver.downloadFirefox();
                System.setProperty("webdriver.gecko.driver",driver_path);
                driverManager = new GeckoDriverManager();
                break;
            default:
                break;
        }
        return driverManager;

    }


}
