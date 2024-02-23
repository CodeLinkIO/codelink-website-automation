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
import pageObjects.OurCompanyPage;
import reportConfig.ExtentTestManager;

import java.lang.reflect.Method;

import static reportConfig.ExtentTestManager.logInfoToReport;

public class LocationTest extends BaseTest {

    WebDriver driver;
    MainMenuBar mainMenuBar;
    OurCompanyPage ourCompanyPage;

    @Parameters({"browser", "environmentName"})
    @BeforeClass(alwaysRun = true)
    public void beforeClass(String browser, String envName) {
        driver = getBrowserDriver(browser, envName);
        mainMenuBar = new MainMenuBar(driver);
    }

    @Test
    public void Location_Links_And_Pages(Method method) {
        ExtentTestManager.startTest(method.getName(), "Location Pages");

        logInfoToReport("Select Industries in home bar");
        mainMenuBar.hoverItemOnMainMenu("Company");

        logInfoToReport("Open Web3 page");
        mainMenuBar.selectItemOnSubMenu("Our Company");

        logInfoToReport("Open Ho Chi Minh page");
        ourCompanyPage = new OurCompanyPage(driver);
        ourCompanyPage.navigateToLocationPage("Ho Chi Minh");

        logInfoToReport("Verify Ho Chi Minh page is displayed correctly");
        Assert.assertTrue(ourCompanyPage.isTitleDisplayed("HEADQUARTERS"));
        Assert.assertEquals(ourCompanyPage.getPageURL(driver), GlobalConstants.HOCHIMINH_PAGE);

        logInfoToReport("Open Toronto page");
        ourCompanyPage.getToPage(driver);
        ourCompanyPage.navigateToLocationPage("Toronto");

        logInfoToReport("Verify Toronto page is displayed correctly");
        Assert.assertTrue(ourCompanyPage.isTitleDisplayed("NORTH AMERICA"));
        Assert.assertEquals(ourCompanyPage.getPageURL(driver), GlobalConstants.TORONTO_PAGE);

        logInfoToReport("Open Ha Noi page");
        ourCompanyPage.getToPage(driver);
        ourCompanyPage.navigateToLocationPage("Ha Noi");

        logInfoToReport("Verify Ha Noi page is displayed correctly");
        Assert.assertTrue(ourCompanyPage.isTitleDisplayed("VIETNAM - NORTH"));
        Assert.assertEquals(ourCompanyPage.getPageURL(driver), GlobalConstants.HANOI_PAGE);

        logInfoToReport("Open Da Nang page");
        ourCompanyPage.getToPage(driver);
        ourCompanyPage.navigateToLocationPage("Da Nang");

        logInfoToReport("Verify Da Nang page is displayed correctly");
        Assert.assertTrue(ourCompanyPage.isTitleDisplayed("VIETNAM - CENTRAL"));
        Assert.assertEquals(ourCompanyPage.getPageURL(driver), GlobalConstants.DANANG_PAGE);

    }

    @AfterClass(alwaysRun = true)
    public void afterClass() {
        closeBrowserDriver();
    }
}
