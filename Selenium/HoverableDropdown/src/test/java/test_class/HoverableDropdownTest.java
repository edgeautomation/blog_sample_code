package test_class;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.function.Consumer;
import java.util.regex.Pattern;

public class HoverableDropdownTest {

    private WebDriver driver;
    private Actions action;

    Consumer<By> hover = (By by) -> {
        action.moveToElement(driver.findElement(by))
                .pause(Duration.ofSeconds(1))
                .perform();
    };

    @Test(description = "Test 1")
    public void hoverTest1() {
        driver.get("https://demoqa.com/menu/");
        driver.manage().window().maximize();

        hover.accept(By.linkText("Main Item 2"));
        hover.accept(By.linkText("SUB SUB LIST »"));
        hover.accept(By.linkText("Sub Sub Item 1"));
    }

    @Test(description = "Test 2")
    public void hoverTest2() {
        driver.get("https://demoqa.com/menu/");
        driver.manage().window().maximize();

        String by = "Main Item 2 -> SUB SUB LIST » -> Sub Sub Item 1";
        Pattern.compile("->")
                .splitAsStream(by)
                .map(String::trim)
                .map(By::linkText)
                .filter(s-> driver.findElement(s).isDisplayed())
                .forEach(hover);
    }

    @Test(description = "Test 3")
    public void hoverTest3() {
        driver.get("https://demoqa.com/menu/");
        driver.manage().window().maximize();

        String by = "Main Item 2 -> SUB SUB LIST » -> Sub Sub Item 1";
        Pattern.compile("->")
                .splitAsStream(by)
                .map(String::trim)
                .map(s-> By.xpath("//a[text()='" + s + "']"))
                .forEach(hover);
    }



    @BeforeMethod
    public void setupEnv() {
        WebDriverManager
                .chromedriver()
                .setup();
        this.driver = new ChromeDriver();
        this.action = new Actions(driver);
    }

    @AfterMethod
    public void teardownDriver() {
        driver.quit();
    }



}
