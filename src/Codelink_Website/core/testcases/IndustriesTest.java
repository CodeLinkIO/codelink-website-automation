package testcases;

import common.BaseTest;
import common.GlobalConstants;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageObjects.MainMenuBar;
import reportConfig.ExtentTestManager;

import java.lang.reflect.Method;

import static reportConfig.ExtentTestManager.logInfo;

public class IndustriesTest extends BaseTest {

    WebDriver driver;
    MainMenuBar mainMenuBar;

    @Parameters({"browser", "environmentName"})
    @BeforeClass(alwaysRun = true)
    public void beforeClass(String browser, String envName) {
        driver = getBrowserDriver(browser, envName);
        mainMenuBar = new MainMenuBar(driver);
    }

    @Test
    public void Industries_Links_And_Pages(Method method) {
        ExtentTestManager.startTest(method.getName(), "Industries Pages");

        logInfo("Select Industries in home bar");
        mainMenuBar.hoverItemOnMainMenu("Industries");

        logInfo("Open Web3 page");
        mainMenuBar.selectItemOnSubMenu("Web3");

        logInfo("Verify Web3 page is displayed correctly");
        Assert.assertTrue(mainMenuBar.isTitleDisplayed("Web3"));
        Assert.assertEquals(mainMenuBar.getPageURL(driver), GlobalConstants.WEB3_PAGE);

        logInfo("Select Industries in home bar");
        mainMenuBar.hoverItemOnMainMenu("Industries");

        logInfo("Open Logistics page");
        mainMenuBar.selectItemOnSubMenu("Logistics");

        logInfo("Verify Logistics page is displayed correctly");
        Assert.assertTrue(mainMenuBar.isTitleDisplayed("Logistics"));
        Assert.assertEquals(mainMenuBar.getPageURL(driver), GlobalConstants.LOGISTICS_PAGE);

        logInfo("Select Industries in home bar");
        mainMenuBar.hoverItemOnMainMenu("Industries");

        logInfo("Open Health Tech page");
        mainMenuBar.selectItemOnSubMenu("Health Tech");

        logInfo("Verify Health Tech page is displayed correctly");
        Assert.assertTrue(mainMenuBar.isTitleDisplayed("Health Tech"));
        Assert.assertEquals(mainMenuBar.getPageURL(driver), GlobalConstants.HEALTH_TECH_PAGE);

        logInfo("Select Industries in home bar");
        mainMenuBar.hoverItemOnMainMenu("Industries");

        logInfo("Open FinTech page");
        mainMenuBar.selectItemOnSubMenu("FinTech");

        logInfo("Verify FinTech page is displayed correctly");
        Assert.assertTrue(mainMenuBar.isTitleDisplayed("FinTech"));
        Assert.assertEquals(mainMenuBar.getPageURL(driver), GlobalConstants.FINTECH_PAGE);

        logInfo("Select Industries in home bar");
        mainMenuBar.hoverItemOnMainMenu("Industries");

        logInfo("Open Education page");
        mainMenuBar.selectItemOnSubMenu("Education");

        logInfo("Verify Education page is displayed correctly");
        Assert.assertTrue(mainMenuBar.isTitleDisplayed("Education"));
        Assert.assertEquals(mainMenuBar.getPageURL(driver), GlobalConstants.EDUCATION_PAGE);

        logInfo("Select Industries in home bar");
        mainMenuBar.hoverItemOnMainMenu("Industries");

        logInfo("Open Communication & Media page");
        mainMenuBar.selectItemOnSubMenu("Communication & Media");

        logInfo("Verify Communication & Media page is displayed correctly");
        Assert.assertTrue(mainMenuBar.isTitleDisplayed("Communication & Media"));
        Assert.assertEquals(mainMenuBar.getPageURL(driver), GlobalConstants.COMMUNICATION_AND_MEDIA_PAGE);
    }

    @AfterClass(alwaysRun = true)
    public void afterClass() {
        closeBrowserDriver();
    }
}
