package testcases;

import common.BaseTest;
import common.GlobalConstants;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageObjects.BlogPage;
import pageObjects.MainMenuBar;
import reportConfig.ExtentTestManager;

import java.lang.reflect.Method;

import static reportConfig.ExtentTestManager.logInfoToReport;

public class BlogTest extends BaseTest {

    WebDriver driver;
    MainMenuBar mainMenuBar;
    BlogPage blogPage;

    @Parameters({"browser", "environmentName"})
    @BeforeClass(alwaysRun = true)
    public void beforeClass(String browser, String envName) {
        driver = getBrowserDriver(browser, envName);
        mainMenuBar = new MainMenuBar(driver);
    }

    @Test
    public void Blog_Links_And_Pages(Method method) {
        ExtentTestManager.startTest(method.getName(), "Blog Pages");

        logInfoToReport("Select Company in home bar");
        mainMenuBar.hoverItemOnMainMenu("Company");

        logInfoToReport("Open Blog page");
        mainMenuBar.selectItemOnSubMenu("Blog");

        logInfoToReport("Open Automation testing for SEO title page");
        blogPage = new BlogPage(driver);
        blogPage.navigateToBlogPage("Automation testing");

        logInfoToReport("Verify Automation testing for SEO title page is displayed correctly");
        Assert.assertTrue(blogPage.isTitleDisplayed("Automation testing for SEO title"));
        Assert.assertEquals(blogPage.getPageURL(driver), GlobalConstants.AUTOMATION_TESTING_PAGE);

        logInfoToReport("Open AImagine page");
        blogPage.getToPage(driver);
        blogPage.navigateToBlogPage("AImagine");

        logInfoToReport("Verify AImagine page is displayed correctly");
        Assert.assertTrue(blogPage.isTitleDisplayed("AImagine: Pioneering Hyperrealistic AI Image Generation"));
        Assert.assertEquals(blogPage.getPageURL(driver), GlobalConstants.AIMAGINE_PAGE);

        logInfoToReport("Open B2B Companies page");
        blogPage.getToPage(driver);
        blogPage.navigateToBlogPage("B2B Companies");

        logInfoToReport("Verify B2B Companies page is displayed correctly");
        Assert.assertTrue(blogPage.isTitleDisplayed("Recognized as one of the top B2B Companies in Vietnam for 2021.1"));
        Assert.assertEquals(blogPage.getPageURL(driver), GlobalConstants.B2B_COMPANIES_PAGE);

    }

    @AfterClass(alwaysRun = true)
    public void afterClass() {
        closeBrowserDriver();
    }
}
