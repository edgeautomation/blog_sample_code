package navigation_test;

import driver_steup.DriverManager;
import driver_steup.DriverManagerFactory;
import driver_steup.DriverType;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;

public class NavigationTest {

    DriverManager driverManager;
    WebDriver driver;

    @Parameters({"browser"})
    @BeforeTest()
    public void beforeTest(@Optional("Chrome") String browser) {
        if (browser.equalsIgnoreCase("Chrome")) {
            driverManager = DriverManagerFactory.getManager(DriverType.CHROME);
        } else if (browser.equalsIgnoreCase("Firefox")) {
            driverManager = DriverManagerFactory.getManager(DriverType.FIREFOX);
        }
    }

    @BeforeMethod
    public void beforeMethod() {
        driver = driverManager.getDriver();
    }

    @AfterMethod
    public void afterMethod() {
        driverManager.quitDriver();
    }

    @Test
    public void launchGoogleTest() {
        driver.get("https://www.google.com");
        Assert.assertEquals("Google", driver.getTitle());
    }


}
