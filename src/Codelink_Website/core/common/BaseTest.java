package common;

import enums.BrowserList;
import enums.EnvironmentList;
import ultilities.log;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Objects;

public class BaseTest extends BasePage {
    protected WebDriver driver;

    public WebDriver getWebDriver(){
        return driver;
    }

    protected WebDriver getBrowserDriver(String browserName, String environmentName) {
        BrowserList browser = BrowserList.valueOf(browserName.toUpperCase());
        if (browser == BrowserList.FIREFOX) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        } else if (browser == BrowserList.CHROME) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        } else if (browser == BrowserList.EDGE) {
            WebDriverManager.edgedriver().setup();
            driver = new EdgeDriver();
        } else {
            throw new RuntimeException("Browser name is invalid");
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(GlobalConstants.NORMAL_TIMEOUT));
        driver.manage().window().maximize();
        driver.get(environmentURL(environmentName));
        return driver;
    }

    private String environmentURL(String environmentName) {
        String url = null;
        EnvironmentList environment = EnvironmentList.valueOf(environmentName.toUpperCase());
        url = switch (environment) {
            case STAGING -> GlobalConstants.CODELINK_WEBSITE_STG_URL;
            case PROD -> GlobalConstants.CODELINK_WEBSITE_PROD_URL;
        };
        return url;
    }

    public void deleteAllFileInFolder(String folderName) {
        try {
            String pathDownloadFolder = GlobalConstants.PROJECT_PATH + "/" + folderName;
            File file = new File(pathDownloadFolder);
            File[] listOfFiles = file.listFiles();
            for (int i = 0; i < Objects.requireNonNull(listOfFiles).length; i++) {
                if (listOfFiles[i].isFile()) {
                    System.out.println(listOfFiles[i].getName());
                    new File(listOfFiles[i].toString()).delete();
                }
            }
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
    }

    protected void closeBrowserDriver() {
        String cmd = null;
        try {
            String osName = GlobalConstants.OS_NAME;
            String driverInstanceName = driver.toString().toLowerCase();
            String browserDriverName = getBrowserDriverName(driverInstanceName);

            if (osName.contains("window")) {
                cmd = "taskkill /F /FI \"IMAGENAME eq " + browserDriverName + "*\"";
            } else {
                cmd = "pkill " + browserDriverName;
            }

            if (driver != null) {
                driver.manage().deleteAllCookies();
                driver.quit();
            }
        } catch (Exception e) {
            log.info(e.getMessage());
        } finally {
            try {
                Process process = Runtime.getRuntime().exec(cmd);
                process.waitFor();
            } catch (IOException | InterruptedException e) {
                e.fillInStackTrace();
            }
        }
    }

    private static String getBrowserDriverName(String driverInstanceName) {
        String browserDriverName = null;

        if (driverInstanceName.contains("chrome")) {
            browserDriverName = "chromedriver";
        } else if (driverInstanceName.contains("firefox")) {
            browserDriverName = "geckodriver";
        } else if (driverInstanceName.contains("edge")) {
            browserDriverName = "msedgedriver";
        } else if (driverInstanceName.contains("opera")) {
            browserDriverName = "operadriver";
        } else {
            browserDriverName = "safaridriver";
        }
        return browserDriverName;
    }
}