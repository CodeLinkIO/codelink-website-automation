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

public class CompanyTest extends BaseTest {

    WebDriver driver;
    MainMenuBar mainMenuBar;

    @Parameters({"browser", "environmentName"})
    @BeforeClass(alwaysRun = true)
    public void beforeClass(String browser, String envName) {
        driver = getBrowserDriver(browser, envName);
        mainMenuBar = new MainMenuBar(driver);
    }

    @Test
    public void Company_Links_And_Pages(Method method) {
        ExtentTestManager.startTest(method.getName(), "Company Pages");

        logInfo("Select Company in home bar");
        mainMenuBar.hoverItemOnMainMenu("Company");

        logInfo("Open Our Company page");
        mainMenuBar.selectItemOnSubMenu("Our Company");

        logInfo("Verify Our Company page is displayed correctly");
        Assert.assertTrue(mainMenuBar.isTitleDisplayed("OUR COMPANY"));
        Assert.assertEquals(mainMenuBar.getPageURL(driver), GlobalConstants.OUR_COMPANY_PAGE);

        logInfo("Select Company in home bar");
        mainMenuBar.hoverItemOnMainMenu("Company");

        logInfo("Open Our Team page");
        mainMenuBar.selectItemOnSubMenu("Our Team");

        logInfo("Verify Our Team page is displayed correctly");
        Assert.assertTrue(mainMenuBar.isTitleDisplayed("OUR TEAM"));
        Assert.assertEquals(mainMenuBar.getPageURL(driver), GlobalConstants.OUR_TEAM_PAGE);

        logInfo("Select Company in home bar");
        mainMenuBar.hoverItemOnMainMenu("Company");

        logInfo("Open Locations page");
        mainMenuBar.selectItemOnSubMenu("Locations");

        logInfo("Verify Locations page is displayed correctly");
        Assert.assertTrue(mainMenuBar.isTitleDisplayed("HEADQUARTERS"));
        Assert.assertEquals(mainMenuBar.getPageURL(driver), GlobalConstants.LOCATIONS_PAGE);

        logInfo("Select Company in home bar");
        mainMenuBar.hoverItemOnMainMenu("Company");

        logInfo("Open Playbook page");
        mainMenuBar.selectItemOnSubMenu("Playbook");

        logInfo("Verify Playbook page is displayed correctly");
        Assert.assertTrue(mainMenuBar.isTitleDisplayed("PLAYBOOK"));
        Assert.assertEquals(mainMenuBar.getPageURL(driver), GlobalConstants.PLAYBOOK_PAGE);

        logInfo("Select Company in home bar");
        mainMenuBar.hoverItemOnMainMenu("Company");

        logInfo("Open Blog page");
        mainMenuBar.selectItemOnSubMenu("Blog");

        logInfo("Verify Blog page is displayed correctly");
        Assert.assertTrue(mainMenuBar.isTitleDisplayed("BLOG"));
        Assert.assertEquals(mainMenuBar.getPageURL(driver), GlobalConstants.BLOG_PAGE);

        logInfo("Select Company in home bar");
        mainMenuBar.hoverItemOnMainMenu("Company");

        logInfo("Open Careers page");
        mainMenuBar.selectItemOnSubMenu("Careers");

        logInfo("Verify Careers page is displayed correctly");
        mainMenuBar.switchToWindowByTitle(driver, "CodeLink - Current Openings");
        Assert.assertTrue(mainMenuBar.isTitleDisplayed("Job Openings"));
        Assert.assertEquals(mainMenuBar.getPageURL(driver), GlobalConstants.CAREERS_PAGE);

    }

    @AfterClass(alwaysRun = true)
    public void afterClass() {
        closeBrowserDriver();
    }
}
