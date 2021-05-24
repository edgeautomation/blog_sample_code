package driver_steup;

import org.arquillian.spacelift.Spacelift;
import org.arquillian.spacelift.task.archive.UnzipTool;
import org.arquillian.spacelift.task.net.DownloadTool;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DownloadDriver {

    private final static String CHROMEDRIVER_URL = "https://chromedriver.storage.googleapis.com";
    private final static String GECKODRIVER_URL = "https://github.com/mozilla/geckodriver/releases/download";
    private static String OS;
    private static String OS_BIT;
    private static String downloadURL;
    private static String browser_version = "v0.29.0";
    //private static String browser_version = "90.0.4430.24";


    static {
        OS = System.getProperty("os.name");
        if (OS.contains("Windows")) {
            OS = "Windows";
        } else if (OS.contains("Linux")) {
            OS = "Linux";
        } else if (OS.contains("macOS")) {
            OS = "macOS";
        }
        OS_BIT = System.getProperty("sun.arch.data.model");

        try {
            Files.walk(Paths.get("driver"))
                    .filter(Files::isRegularFile)
                    .map(Path::toFile)
                    .forEach(File::delete);
        } catch (IOException e) {
            System.out.println("Driver Path is not valid.");
        }
    }

    public static String downloadChrome() {
        downloadURL = CHROMEDRIVER_URL + "/" + browser_version + "/" + "chromedriver_";

        if (OS.equals("Windows") && OS_BIT.equals("64")) {
            downloadURL = downloadURL + "win32.zip";
        } else if (OS.equals("Windows") && OS_BIT.equals("32")) {
            downloadURL = downloadURL + "win32.zip";
        } else if (OS.equals("Linux") && OS_BIT.equals("64")) {
            downloadURL = downloadURL + "linux64.zip";
        } else if (OS.equals("Linux") && OS_BIT.equals("32")) {
            downloadURL = downloadURL + "linux64.zip";
        } else if (OS.equals("macOS") && OS_BIT.equals("64")) {
            downloadURL = downloadURL + "mac64.zip";
        }

        Spacelift.task(DownloadTool.class)
                .from(downloadURL)
                .to(System.getProperty("user.dir") + "/driver/chrome.zip")
                .then(UnzipTool.class)
                .toDir(System.getProperty("user.dir") + "/driver/")
                .execute()
                .await();
        try {
            return System.getProperty("user.dir") + "/driver/" +
                    Files.list(Paths.get("driver"))
                    .filter(s -> !s.toString().contains(".zip"))
                    .findFirst()
                    .get().getFileName().toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String downloadFirefox() {

        downloadURL = GECKODRIVER_URL + "/" + browser_version + "/" +
                "geckodriver-" + browser_version + "-";
        OS = "Linux";
        if (OS.equals("Windows") && OS_BIT.equals("64")) {
            downloadURL = downloadURL + "win64.zip";
        } else if (OS.equals("Windows") && OS_BIT.equals("32")) {
            downloadURL = downloadURL + "win32.zip";
        } else if (OS.equals("Linux") && OS_BIT.equals("64")) {
            downloadURL = downloadURL + "linux64.tar.gz";
        } else if (OS.equals("Linux") && OS_BIT.equals("32")) {
            downloadURL = downloadURL + "linux32.tar.gz";
        } else if (OS.equals("macOS") && OS_BIT.equals("64")) {
            downloadURL = downloadURL + "macos.tar.gz";
        }
        System.out.println(downloadURL);
        Spacelift.task(DownloadTool.class)
                .from(downloadURL)
                .to(System.getProperty("user.dir") + "/driver/gecko.tar.gz")
                .then(UnzipTool.class)
                .toDir(System.getProperty("user.dir") + "/driver/")
                .execute()
                .await();
        try {
            return System.getProperty("user.dir") + "/driver/" +
                    Files.list(Paths.get("driver"))
                            .filter(s -> !s.toString().contains(".zip"))
                            .findFirst()
                            .get().getFileName().toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
