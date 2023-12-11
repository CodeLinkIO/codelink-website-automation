package testcases;

import common.BaseTest;
import ultilities.DataFakerHelpers;
import pageObjects.ContactUsPage;
import pageObjects.HomePage;
import pageObjects.OurSolutionsPage;
import reportConfig.ExtentTestManager;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

import static reportConfig.ExtentTestManager.logInfo;

public class ContactUsTest extends BaseTest {
    WebDriver driver;
    HomePage homePage;
    ContactUsPage contactUsPage;

    OurSolutionsPage ourSolutionPage;

    String firstName= DataFakerHelpers.getFaker().name().firstName();
    String lastName= DataFakerHelpers.getFaker().name().lastName();
    String email= DataFakerHelpers.getFaker().internet().emailAddress();
    String companyName=DataFakerHelpers.getFaker().company().name();

    @Parameters({"browser", "environmentName"})
    @BeforeClass(alwaysRun = true)
    public void beforeClass(String browser, String envName) {
        driver = getBrowserDriver(browser, envName);
        homePage = new HomePage(driver);
    }

    @Test
    public void Contact_Us_Flow_With_Embedded_Team(Method method){
        ExtentTestManager.startTest(method.getName(),"Contact Us flow with Embedded Team");
        logInfo("Open Contact Us page");
        contactUsPage= homePage.navigateToContactUsPage();

        logInfo("Verify Contact Us page is displayed");
        ExtentTestManager.logInfo("Verify Contact Us page is displayed");
        Assert.assertTrue(contactUsPage.isWelcomeTitleDisplayed());

        logInfo("Open Our Solution page");
        ourSolutionPage=contactUsPage.navigateToOurSolutionsPage();

        logInfo("Choose desired team");
        ourSolutionPage.selectDesiredTeam("Scale your team");

        logInfo("Choose team members");
        ourSolutionPage.chooseTeamMember("Product Owner");
        ourSolutionPage.chooseTeamMember("Technical Lead");
        ourSolutionPage.chooseTeamMember("Front-end Developer");
        ourSolutionPage.chooseTeamMember("Back-end Developer");
        ourSolutionPage.clickOnNextButton();

        logInfo("Input Project Overview");
        ourSolutionPage.inputProjectOverview("Testing");

        logInfo("Project languages");
        ourSolutionPage.languageOrFrameworkDefined("No");
        ourSolutionPage.clickOnNextButton();

        logInfo("Select Start Date & Time Frame");
        ourSolutionPage.startDate("Next few months");
        ourSolutionPage.timeFrame("Over 6 months");
        ourSolutionPage.clickOnNextButton();

        logInfo("Input customer first name " + firstName);
        ourSolutionPage.inputUserInfoByName("First Name",firstName);

        logInfo("Input customer last name " + lastName);
        ourSolutionPage.inputUserInfoByName("Last Name",lastName);

        logInfo("Input customer email " + email);
        ourSolutionPage.inputUserInfoByName("Email",email);

        logInfo("Input phone number");
        ourSolutionPage.inputUserInfoByName("Phone Number", "1234455");

        logInfo("Select timezone");
        ourSolutionPage.selectTimezone("(GMT -8:00) Pacific Time (US & Canada)");

        logInfo("Input company name" + companyName);
        ourSolutionPage.inputUserInfoByName("Company Name",companyName);

        logInfo("Finish the form");
        ourSolutionPage.clickOnFinishButton();

        logInfo("Verify the success message displayed");
        Assert.assertTrue(ourSolutionPage.isSuccessMessageDisplayed());
    }

    @AfterClass(alwaysRun = true)
    public void afterClass(){
        closeBrowserDriver();
    }
}
