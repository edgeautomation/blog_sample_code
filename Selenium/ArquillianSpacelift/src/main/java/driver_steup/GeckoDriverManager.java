package driver_steup;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.GeckoDriverService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GeckoDriverManager extends DriverManager {

    private GeckoDriverService geckoService;

    @Override
    public void startService() {
        if (null == geckoService) {
            try {
                geckoService = new GeckoDriverService.Builder()
                        .usingDriverExecutable(new File("driver/" + Files.list(Paths.get("driver"))
                                .filter(s -> !s.toString().contains(".zip"))
                                .findFirst()
                                .get().getFileName().toString()))
                        .usingAnyFreePort()
                        .build();
                geckoService.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void stopService() {
        if (null != geckoService && geckoService.isRunning())
            geckoService.stop();
    }

    @Override
    public void createDriver() {
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("test-type");
        driver = new FirefoxDriver();
    }

}
