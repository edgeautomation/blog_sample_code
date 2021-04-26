package awaitility_test;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.*;


public class AwaitilityTest {

    private WebDriver driver;

    BiPredicate<List<WebElement>, String> isPresent = (webelement, text) -> {
        return webelement.stream()
                .filter(we -> we.getText().contains(text))
                .findFirst()
                .get()
                .isDisplayed();
    };

    /**
     * Click on Download button and wait for download to complete
     */
    @Test(description = "Click on Download button and wait for download to complete")
    public void waitForElementToAppear() {
        driver.get("https://www.seleniumeasy.com/test/bootstrap-download-progress-demo.html");
        driver.findElement(By.cssSelector("button#cricle-btn")).click();

        WebElement progress_circle = driver.findElement(By.cssSelector("div.percenttext"));
        await("Wait for download did not complete 100% within 30 seconds")
                .atMost(30, SECONDS) // Maximum it will wait for 15 Seconds
                .ignoreExceptions() // Ignore exception upto 15 Seconds if element is not present
                .until(progress_circle::getText, is("100%"));
    }

    /**
     * Click Get new user button and wait for image to display
     */
    @Test(description = "Click Get new user button and wait for image to display")
    public void waitForElementToDisappear() {
        driver.get("http://www.seleniumeasy.com/test/dynamic-data-loading-demo.html");
        driver.manage().window().maximize();
        //Click Get a new user button
        driver.findElement(By.id("save")).click();
        WebElement loadingElement = driver.findElement(By.id("loading"));

        //Wait for the loading to disappear and image to display
        await("Wait for new user to load")
                .atMost(10, SECONDS)
                //.until(loadingElement::getText, not("loading..."))
                .until(loadingElement::getText, not(containsString("loading...")));
    }

    /**
     * Wait for Filter Data to present
     */
    @Test(description = "Wait for Filter Data to present")
    public void waitForFilteredData() {
        driver.get("https://www.seleniumeasy.com/test/data-list-filter-demo.html");
        driver.manage().window().maximize();
        //Search Name in Data Filter
        driver.findElement(By.id("input-search")).sendKeys("Brenda Tree");

        //Wait for Data to be present
        await("Wait for Filter data to be present")
                .atMost(10, SECONDS)
                .ignoreExceptions()
                .until(() -> driver
                        .findElement(By.xpath("//*[contains(text(),'Brenda Tree')]"))
                        .isDisplayed());
    }


    /**
     * Wait for Filter Data to present from List of Element
     */
    @Test(description = "Wait for Filter Data to present")
    public void waitForFilteredDataFromList() {
        driver.get("https://www.seleniumeasy.com/test/data-list-filter-demo.html");
        driver.manage().window().maximize();
        //Search Name in Data Filter
        driver.findElement(By.id("input-search")).sendKeys("Brenda Tree");
        //Wait for Data to be present
        await("Wait for Filter data to be present")
                .atMost(10, SECONDS)
                .until(() -> driver.findElements(By.cssSelector(".info-block *"))
                        .stream()
                        .filter(s -> s.getText().contains("Brenda Tree"))
                        .findFirst()
                        .get()
                        .isDisplayed());
    }

    @Test(description = "Wait for new browser window to open")
    public void waitForNewBrowserWindow() {
        driver.get("http://www.seleniumframework.com/Practiceform/");
        driver.manage().window().maximize();
        //Click on New Browser Window
        driver.findElement(By.xpath("//*[text()='New Browser Window']")).click();
        //Wait for new window to open
        await("Wait for new window to open")
                .atMost(10, SECONDS)
                .until(() -> driver.getWindowHandles().size(), is(2));
    }

    @Test(description = "Wait for Alert to display")
    public void waitForAlert() {
        driver.get("http://www.seleniumframework.com/Practiceform/");
        WebElement alertButton = driver.findElement(By.id("timingAlert"));

        //To check if alert is present
        Predicate<WebDriver> isAlertPresent = (d) -> {
            d.switchTo().alert();
            return true;
        };

        //click - alert appears after 3 seconds
        alertButton.click();

        //wait for 5 seconds - ignore alert not present exception
        await("Wait for alert").atMost(5, SECONDS)
                .ignoreExceptions()
                .until(() -> isAlertPresent.test(driver));

        driver.switchTo().alert().accept();
    }

    @Test(description = "Wait for element to clickable")
    public void waitForElementToClickable() {
        driver.get("https://www.seleniumeasy.com/test");
        driver.manage().window().maximize();

        //To check if element is clickable
        Predicate<By> isElementClickable = (by) -> {
            try {
                ExpectedConditions
                        .elementToBeClickable(by);
                return true;
            } catch (Exception e) {
                return false;
            }
        };

        //wait for 10 seconds to check element clickable
        await("Wait for element to clickable")
                .atMost(10, SECONDS)
                .ignoreExceptions()
                .until(() -> isElementClickable.test(By.id("btn_basic_example")));

        driver.findElement(By.id("btn_basic_example")).click();

    }


    @BeforeMethod
    public void setupEnv() {
        WebDriverManager
                .chromedriver()
                .setup();
        this.driver = new ChromeDriver();
    }

    @AfterMethod
    public void teardownDriver() {
        driver.quit();
    }


}
