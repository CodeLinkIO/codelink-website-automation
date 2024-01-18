package testcases;

import common.BaseTest;
import common.GlobalConstants;
import org.testng.Assert;
import pageObjects.CommonFunctionsPage;
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
    CommonFunctionsPage commonFunctionsPage;

    @Parameters({"browser", "environmentName"})
    @BeforeClass(alwaysRun = true)
    public void beforeClass(String browser, String envName) {
        driver = getBrowserDriver(browser, envName);
        commonFunctionsPage = new CommonFunctionsPage(driver);
    }

    @Test
    public void Services_Links_And_Pages(Method method) {
        ExtentTestManager.startTest(method.getName(), "Services Pages");

        logInfo("Select Services in home bar");
        commonFunctionsPage.selectHomeBar("Services");

        logInfo("Open Artificial Intelligence & Machine Learning page");
        commonFunctionsPage.selectItemInHomeBar("Artificial Intelligence & Machine Learning");

        logInfo("Verify Artificial Intelligence & Machine Learning page is displayed correctly");
        Assert.assertTrue(commonFunctionsPage.isTitleDisplayed("Artificial Intelligence & Machine Learning"));
        Assert.assertEquals(commonFunctionsPage.getPageURL(driver), GlobalConstants.ARTIFICIAL_INTELLIGENCE_PAGE);

        logInfo("Select Services in home bar");
        commonFunctionsPage.selectHomeBar("Services");

        logInfo("Open Software Development page");
        commonFunctionsPage.selectItemInHomeBar("Software Development");

        logInfo("Verify Software Development page is displayed correctly");
        Assert.assertTrue(commonFunctionsPage.isTitleDisplayed("Software Development"));
        Assert.assertEquals(commonFunctionsPage.getPageURL(driver), GlobalConstants.SOFTWARE_DEVELOPMENT_PAGE);

        logInfo("Select Services in home bar");
        commonFunctionsPage.selectHomeBar("Services");

        logInfo("Open Embedded Teams page");
        commonFunctionsPage.selectItemInHomeBar("Embedded Teams");

        logInfo("Verify Software Development page is displayed correctly");
        Assert.assertTrue(commonFunctionsPage.isTitleDisplayed("Embedded Teams"));
        Assert.assertEquals(commonFunctionsPage.getPageURL(driver), GlobalConstants.EMBEDDED_TEAMS_PAGE);

        logInfo("Select Services in home bar");
        commonFunctionsPage.selectHomeBar("Services");

        logInfo("Open Start-up Growth page");
        commonFunctionsPage.selectItemInHomeBar("Start-up Growth");

        logInfo("Verify Start-up Growth page is displayed correctly");
        Assert.assertTrue(commonFunctionsPage.isTitleDisplayed("Start-up Growth"));
        Assert.assertEquals(commonFunctionsPage.getPageURL(driver), GlobalConstants.START_UP_GROWTH_PAGE);
    }

    @AfterClass(alwaysRun = true)
    public void afterClass() {
        closeBrowserDriver();
    }
}
