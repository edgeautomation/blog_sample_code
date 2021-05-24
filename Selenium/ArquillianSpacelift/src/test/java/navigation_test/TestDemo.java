package navigation_test;

import org.arquillian.spacelift.Spacelift;
import org.arquillian.spacelift.task.archive.UnzipTool;
import org.arquillian.spacelift.task.net.DownloadTool;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;

public class TestDemo {

    @Test
    public void test() {
        Spacelift.task(DownloadTool.class)
                .from("https://github.com/mozilla/geckodriver/releases/download/v0.29.0/geckodriver-v0.29.0-linux64.tar.gz")
                .to(System.getProperty("user.dir") + "/driver/gecko")
                .then(UnzipTool.class)
                .toDir(System.getProperty("user.dir") + "/driver/")
                .execute()
                .await();

    }

}
