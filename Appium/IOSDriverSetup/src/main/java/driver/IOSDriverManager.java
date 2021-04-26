package driver;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.AndroidServerFlag;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import io.appium.java_client.service.local.flags.IOSServerFlag;
import org.openqa.selenium.remote.DesiredCapabilities;
import util.NodeConfig;

import java.io.File;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class IOSDriverManager extends DriverManager{

    NodeConfig nodeConfig = new NodeConfig("iPad_Air2");

    /**
     * Start Appium Service
     */
    @Override
    protected void startService() {
        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setCapability(MobileCapabilityType.UDID,nodeConfig.getCapability("UDID"));
        AppiumServiceBuilder builder = new AppiumServiceBuilder()
                .withArgument(GeneralServerFlag.LOG_LEVEL,"error")
                .withLogFile(new File(nodeConfig.getCapability("TEMPDIRECTORY")+ "/appiumLog.txt"))
                .withArgument(IOSServerFlag.WEBKIT_DEBUG_PROXY_PORT,nodeConfig.getCapability("WEBKIT_DEBUG_PROXY_PORT"))
                .withArgument(AndroidServerFlag.BOOTSTRAP_PORT_NUMBER,nodeConfig.getCapability("BOOTSTRAP_PORT_NUMBER"))
                .withIPAddress("0.0.0.0")
                .withArgument(GeneralServerFlag.TEMP_DIRECTORY,nodeConfig.getCapability("TEMPDIRECTORY") +
                        nodeConfig.getCapability("WEBDRIVER_PORT"))
                .withArgument(GeneralServerFlag.SESSION_OVERRIDE)
                .usingPort(Integer.parseInt(nodeConfig.getCapability("WEBDRIVER_PORT")))
                .withArgument(GeneralServerFlag.CONFIGURATION_FILE,nodeConfig.getCapability("CONFIGURATIONFILE"))
                .withCapabilities(cap);
        service = builder.build();
        service.start();
    }

    /**
     * Stop Appium Service
     */
    @Override
    protected void stopService() {
        service.stop();
        if(service.isRunning()){
            service.stop();
        }
    }

    /**
     * Create IOS Driver
     */
    @Override
    protected void createDriver() {

        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION,nodeConfig.getCapability("PLATFORM_VERSION"));
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, nodeConfig.getCapability("DEVICE_NAME"));
        capabilities.setCapability(MobileCapabilityType.UDID,nodeConfig.getCapability("UDID"));
        capabilities.setCapability(MobileCapabilityType.NO_RESET, true);
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME,nodeConfig.getCapability("PLATFORM_NAME"));
        capabilities.setCapability(MobileCapabilityType.APP, "put your app path");
        capabilities.setCapability(IOSMobileCapabilityType.LAUNCH_TIMEOUT,50000000);
        capabilities.setCapability("newCommandTimeout",60*15);
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.IOS_XCUI_TEST);
        capabilities.setCapability(IOSMobileCapabilityType.SHOW_IOS_LOG,false);
        capabilities.setCapability("realDeviceLogger","/usr/local/lib/node_modules/deviceconsole/deviceconsole");
        capabilities.setCapability("showXcodeLog",false);
        capabilities.setCapability(MobileCapabilityType.TAKES_SCREENSHOT,"true");
        capabilities.setCapability("autoGrantPermissions",true);
        capabilities.setCapability("autoAcceptAlerts",true);
        capabilities.setCapability("autoDismissAlerts",true);
        capabilities.setCapability("nativeWebTap",true);
        capabilities.setCapability(IOSMobileCapabilityType.WDA_LOCAL_PORT,nodeConfig.getCapability("WEBDRIVER_AGENT_PORT"));
        capabilities.setCapability(IOSMobileCapabilityType.BUNDLE_ID,"your app bundle id");

        try {
            this.driver = new IOSDriver(new URL(nodeConfig.getCapability("PORT")),capabilities);
        } catch (Exception e) {
            try {
                throw new Exception("IOS Driver Initialization Error", e);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }

        this.driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

    }
}
