package common;

import driver.BrowserFactory;
import driver.DriverManager;
import enums.EnvironmentList;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import ultilities.JsonUtils;

public class BaseTest extends CommonPage {

    WebDriver driver;

    @Parameters({"BROWSER", "ENVIRONMENT"})
    @BeforeMethod(alwaysRun = true)
    public void createDriver(String browser, String environment) {
        System.setProperty("webdriver.http.factory", "jdk-http-client");
        driver = BrowserFactory.valueOf(browser.toUpperCase()).createDriver();
        DriverManager.setDriver(driver);
        driver.get(environmentURL(environment));
        driver.manage().window().maximize();
    }

    @AfterMethod(alwaysRun = true)
    public void closeDriver() {
        DriverManager.quit();
    }

    private String environmentURL(String environmentName) {
        String url;
        EnvironmentList environment = EnvironmentList.valueOf(environmentName.toUpperCase());
        url = switch (environment) {
            case STAGING -> JsonUtils.getTestConfig().get("URLs").get("StagingHomePage").asText();
            case PROD -> JsonUtils.getTestConfig().get("URLs").get("ProdHomePage").asText();
        };
        return url;
    }

}

