package testcases;

import common.BaseTest;
import common.GlobalConstants;
import pageObjects.HomePage;
import reportConfig.ExtentTestManager;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import ultilities.log;

import java.lang.reflect.Method;

import static reportConfig.ExtentTestManager.logInfo;

public class ServicesTest extends BaseTest {
    WebDriver driver;

    HomePage homePage;

    @Parameters({"browser", "environmentName"})
    @BeforeClass(alwaysRun = true)
    public void beforeClass(String browser, String envName) {
        driver = getBrowserDriver(browser, envName);
        homePage = new HomePage(driver);
    }

    @Test
    public void Services_Links_And_Pages(Method method){
        ExtentTestManager.startTest(method.getName(),"Home Page");

        log.info("Select Services home bar");
        homePage.selectHomeBar("Services");

        logInfo("Select Artificial Intelligence & Machine Learning");
        homePage.selectItemInHomeBar("Artificial Intelligence & Machine Learning");

        logInfo("Verify Artificial Intelligence & Machine Learning page is displayed correctly");
        Assert.assertTrue(homePage.isTitleDisplayed("Artificial Intelligence & Machine Learning"));
        Assert.assertEquals(homePage.getPageURL(driver), GlobalConstants.ARTIFICIAL_INTELLIGENCE_PAGE);

        log.info("Select Services home bar");
        homePage.selectHomeBar("Services");

        logInfo("Select Software Development");
        homePage.selectItemInHomeBar("Software Development");

        logInfo("Verify Software Development page is displayed correctly");
        Assert.assertTrue(homePage.isTitleDisplayed("Software Development"));
        Assert.assertEquals(homePage.getPageURL(driver), GlobalConstants.SOFTWARE_DEVELOPMENT_PAGE);

        log.info("Select Services home bar");
        homePage.selectHomeBar("Services");

        logInfo("Select Embedded Teams");
        homePage.selectItemInHomeBar("Embedded Teams");

        logInfo("Verify Embedded Teams page is displayed correctly");
        Assert.assertTrue(homePage.isTitleDisplayed("Embedded Teams"));
        Assert.assertEquals(homePage.getPageURL(driver), GlobalConstants.EMBEDDED_TEAMS_PAGE);

        log.info("Select Services home bar");
        homePage.selectHomeBar("Services");

        logInfo("Select Start-up Growth");
        homePage.selectItemInHomeBar("Start-up Growth");

        logInfo("Verify Start-up Growth page is displayed correctly");
        Assert.assertTrue(homePage.isTitleDisplayed("Start-up Growth"));
        Assert.assertEquals(homePage.getPageURL(driver), GlobalConstants.START_UP_GROWTH_PAGE);
    }

    @AfterClass(alwaysRun = true)
    public void afterClass(){
        closeBrowserDriver();
    }
}
