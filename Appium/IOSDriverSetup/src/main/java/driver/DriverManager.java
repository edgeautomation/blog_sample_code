package driver;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import org.openqa.selenium.remote.DesiredCapabilities;

public abstract class DriverManager {

    protected IOSDriver driver;
    protected DesiredCapabilities capabilities = new DesiredCapabilities();
    protected static AppiumDriverLocalService service;

    protected abstract void startService();
    protected abstract void stopService();
    protected abstract void createDriver();

    public void quiteDriver() {
        if(null != driver) {
            driver.quit();
            driver = null;
        }
    }

    public IOSDriver getDriver() {
        if(null != driver) {
            startService();
            createDriver();
        }
        return driver;
    }

}
