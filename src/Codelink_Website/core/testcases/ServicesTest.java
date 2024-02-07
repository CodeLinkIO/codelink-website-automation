package testcases;

import common.BaseTest;
import common.GlobalConstants;
import org.testng.Assert;
import pageObjects.MainMenuBar;
import reportConfig.ExtentTestManager;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

import static reportConfig.ExtentTestManager.logInfo;

public class ServicesTest extends BaseTest {
    WebDriver driver;
    MainMenuBar mainMenuBar;

    @Parameters({"browser", "environmentName"})
    @BeforeClass(alwaysRun = true)
    public void beforeClass(String browser, String envName) {
        driver = getBrowserDriver(browser, envName);
        mainMenuBar = new MainMenuBar(driver);
    }

    @Test
    public void Services_Links_And_Pages(Method method) {
        ExtentTestManager.startTest(method.getName(), "Services Pages");

        logInfo("Select Services in home bar");
        mainMenuBar.hoverItemOnMainMenu("Services");

        logInfo("Open Artificial Intelligence & Machine Learning page");
        mainMenuBar.selectItemOnSubMenu("Artificial Intelligence & Machine Learning");

        logInfo("Verify Artificial Intelligence & Machine Learning page is displayed correctly");
        Assert.assertTrue(mainMenuBar.isTitleDisplayed("Artificial Intelligence & Machine Learning"));
        Assert.assertEquals(mainMenuBar.getPageURL(driver), GlobalConstants.ARTIFICIAL_INTELLIGENCE_PAGE);

        logInfo("Select Services in home bar");
        mainMenuBar.hoverItemOnMainMenu("Services");

        logInfo("Open Software Development page");
        mainMenuBar.selectItemOnSubMenu("Software Development");

        logInfo("Verify Software Development page is displayed correctly");
        Assert.assertTrue(mainMenuBar.isTitleDisplayed("Software Development"));
        Assert.assertEquals(mainMenuBar.getPageURL(driver), GlobalConstants.SOFTWARE_DEVELOPMENT_PAGE);

        logInfo("Select Services in home bar");
        mainMenuBar.hoverItemOnMainMenu("Services");

        logInfo("Open Embedded Teams page");
        mainMenuBar.selectItemOnSubMenu("Embedded Teams");

        logInfo("Verify Software Development page is displayed correctly");
        Assert.assertTrue(mainMenuBar.isTitleDisplayed("Embedded Teams"));
        Assert.assertEquals(mainMenuBar.getPageURL(driver), GlobalConstants.EMBEDDED_TEAMS_PAGE);

        logInfo("Select Services in home bar");
        mainMenuBar.hoverItemOnMainMenu("Services");

        logInfo("Open Start-up Growth page");
        mainMenuBar.selectItemOnSubMenu("Start-up Growth");

        logInfo("Verify Start-up Growth page is displayed correctly");
        Assert.assertTrue(mainMenuBar.isTitleDisplayed("Start-up Growth"));
        Assert.assertEquals(mainMenuBar.getPageURL(driver), GlobalConstants.START_UP_GROWTH_PAGE);
    }

    @AfterClass(alwaysRun = true)
    public void afterClass() {
        closeBrowserDriver();
    }
}
