package testcases;

import common.BaseTest;
import ultilities.DataFakerHelpers;
import pageObjects.ContactUsPage;
import pageObjects.HomePage;
import pageObjects.OurSolutionsPage;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

import static reportConfig.ExtentTestManager.logInfo;
import static reportConfig.ExtentTestManager.startTest;

public class ContactUsTest extends BaseTest {
    WebDriver driver;
    HomePage homePage;
    ContactUsPage contactUsPage;
    OurSolutionsPage ourSolutionPage;

    @Parameters({"browser", "environmentName"})
    @BeforeClass(alwaysRun = true)
    public void beforeClass(String browser, String envName) {
        driver = getBrowserDriver(browser, envName);
        homePage = new HomePage(driver);
    }

    @Test
    public void Contact_Us_Flow_With_Embedded_Team(Method method){
        String firstName= DataFakerHelpers.getFaker().name().firstName();
        String lastName= DataFakerHelpers.getFaker().name().lastName();
        String email= DataFakerHelpers.getFaker().internet().emailAddress();
        String companyName=DataFakerHelpers.getFaker().company().name();
        String phoneNumber= DataFakerHelpers.getFaker().phoneNumber().cellPhone();

        startTest(method.getName(),"Contact Us flow with Embedded Team");
        logInfo("Open Contact Us page");
        contactUsPage= homePage.navigateToContactUsPage();

        logInfo("Verify Contact Us page is displayed");
        Assert.assertTrue(contactUsPage.isWelcomeTitleDisplayed());

        logInfo("Open Our Solution page");
        ourSolutionPage=contactUsPage.navigateToOurSolutionsPage();

        logInfo("Select desired team");
        ourSolutionPage.selectDesiredTeam("Scale your team");

        logInfo("Choose team members");
        ourSolutionPage.chooseTeamMember("Product Owner");
        ourSolutionPage.chooseTeamMember("Technical Lead");
        ourSolutionPage.chooseTeamMember("Front-end Developer");
        ourSolutionPage.chooseTeamMember("Back-end Developer");
        ourSolutionPage.clickOnNextButton();

        logInfo("Input Project Overview");
        ourSolutionPage.inputProjectOverview("CL QA testing");

        logInfo("Project languages");
        ourSolutionPage.hasLanguageOrFrameworkDefined("No");
        ourSolutionPage.clickOnNextButton();

        logInfo("Select Start Date & Time Frame");
        ourSolutionPage.startDate("Next few months");
        ourSolutionPage.timeFrame("Over 6 months");
        ourSolutionPage.clickOnNextButton();

        logInfo("Input customer first name: " + firstName);
        ourSolutionPage.inputUserInfo("First Name",firstName);

        logInfo("Input customer last name: " + lastName);
        ourSolutionPage.inputUserInfo("Last Name",lastName);

        logInfo("Input customer email: " + email);
        ourSolutionPage.inputUserInfo("Email",email);

        logInfo("Input phone number: "+phoneNumber);
        ourSolutionPage.inputUserInfo("Phone Number", phoneNumber);

        logInfo("Select timezone");
        ourSolutionPage.selectTimezone("(GMT -8:00) Pacific Time (US & Canada)");

        logInfo("Input company name: " + companyName);
        ourSolutionPage.inputUserInfo("Company Name",companyName);

        logInfo("Finish the form");
        ourSolutionPage.clickOnFinishButton();

        logInfo("Verify the success message displayed");
        Assert.assertTrue(ourSolutionPage.isSuccessMessageDisplayed());
    }

    @Test
    public void Contact_Us_Flow_With_Send_Message(Method method){
        String fullName= DataFakerHelpers.getFaker().name().fullName();
        String email= DataFakerHelpers.getFaker().internet().emailAddress();
        String phoneNumber= DataFakerHelpers.getFaker().phoneNumber().cellPhone();

        startTest(method.getName(),"Contact Us flow with send message");
        logInfo("Open Contact Us page");
        contactUsPage= homePage.navigateToContactUsPage();

        logInfo("Verify Contact Us page is displayed");
        Assert.assertTrue(contactUsPage.isWelcomeTitleDisplayed());

        logInfo("Input customer name: "+fullName);
        contactUsPage.inputUserInfoByName("name",fullName);

        logInfo("Input customer phone number: "+phoneNumber);
        contactUsPage.inputUserInfoByName("phone",phoneNumber);

        logInfo("Input customer email: "+email);
        contactUsPage.inputUserInfoByName("email",fullName);

        logInfo("Click on Send message");
        contactUsPage.clickSendMessage();
    }

    @Test
    public void Contact_Us_Flow_With_Autonomous_Team(Method method){
        String firstName= DataFakerHelpers.getFaker().name().firstName();
        String lastName= DataFakerHelpers.getFaker().name().lastName();
        String email= DataFakerHelpers.getFaker().internet().emailAddress();
        String companyName=DataFakerHelpers.getFaker().company().name();
        String phoneNumber= DataFakerHelpers.getFaker().phoneNumber().cellPhone();

        startTest(method.getName(),"Contact Us flow with autonomous team");
        logInfo("Open Contact Us page");
        contactUsPage= homePage.navigateToContactUsPage();

        logInfo("Open Our Solution page");
        ourSolutionPage=contactUsPage.navigateToOurSolutionsPage();

        logInfo("Choose desired team");
        ourSolutionPage.selectDesiredTeam("Build your product");

        logInfo("Input Project Goal");
        ourSolutionPage.inputProjectGoal("Test Contact Us flow with Autonomous Team");

        logInfo("Select Platform");
        ourSolutionPage.selectPlatform("Mobile Web App");
        ourSolutionPage.clickOnNextButton();

        logInfo("Project languages");
        ourSolutionPage.hasLanguageOrFrameworkDefined("No");

        logInfo("Select new or existing product or code");
        ourSolutionPage.selectNewOrExistingProduct("New");
        ourSolutionPage.clickOnNextButton();

        logInfo("Select start date");
        ourSolutionPage.startDate("ASAP!");

        logInfo("Select target dates");
        ourSolutionPage.hasTargetDatesSet("No");

        logInfo("Select budget");
        ourSolutionPage.selectBudget("Between 20k - 50k ($US)");
        ourSolutionPage.clickOnNextButton();

        logInfo("Input customer first name: " + firstName);
        ourSolutionPage.inputUserInfo("First Name",firstName);

        logInfo("Input customer last name: " + lastName);
        ourSolutionPage.inputUserInfo("Last Name",lastName);

        logInfo("Input customer email: " + email);
        ourSolutionPage.inputUserInfo("Email",email);

        logInfo("Input phone number");
        ourSolutionPage.inputUserInfo("Phone Number", phoneNumber);

        logInfo("Select timezone");
        ourSolutionPage.selectTimezone("(GMT -8:00) Pacific Time (US & Canada)");

        logInfo("Input company name" + companyName);
        ourSolutionPage.inputUserInfo("Company Name",companyName);

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
