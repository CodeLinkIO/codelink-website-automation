package testcases;

import common.BaseTest;
import common.GlobalConstants;
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
import ultilities.GMail;

import java.lang.reflect.Method;
import java.util.List;

import static reportConfig.ExtentTestManager.logInfoToReport;
import static reportConfig.ExtentTestManager.startTest;

public class ContactUsTest extends BaseTest {
    WebDriver driver;
    HomePage homePage;
    ContactUsPage contactUsPage;
    OurSolutionsPage ourSolutionPage;
    String firstName, lastName, email, companyName, phoneNumber, fullName, message;

    @Parameters({"browser", "environmentName"})
    @BeforeClass(alwaysRun = true)
    public void beforeClass(String browser, String envName) {
        driver = getBrowserDriver(browser, envName);
        homePage = new HomePage(driver);
    }

    @Test
    public void Contact_Us_Flow_With_Embedded_Team(Method method) {
        firstName = DataFakerHelpers.getFaker().name().firstName();
        lastName = DataFakerHelpers.getFaker().name().lastName();
        email = DataFakerHelpers.getFaker().internet().emailAddress();
        companyName = DataFakerHelpers.getFaker().company().name();
        phoneNumber = DataFakerHelpers.getFaker().phoneNumber().cellPhone();

        startTest(method.getName(), "Contact Us flow with Embedded Team");
        logInfoToReport("Open Contact Us page");
        contactUsPage = homePage.navigateToContactUsPage();

        logInfoToReport("Verify Contact Us page is displayed");
        Assert.assertTrue(contactUsPage.isWelcomeTitleDisplayed());

        logInfoToReport("Open Our Solution page");
        ourSolutionPage = contactUsPage.navigateToOurSolutionsPage();

        logInfoToReport("Select desired team");
        ourSolutionPage.selectDesiredTeam("Scale your team");

        logInfoToReport("Choose team members");
        ourSolutionPage.chooseTeamMember("Product Owner");
        ourSolutionPage.chooseTeamMember("Technical Lead");
        ourSolutionPage.chooseTeamMember("Front-end Developer");
        ourSolutionPage.chooseTeamMember("Back-end Developer");
        ourSolutionPage.clickOnNextButton();

        logInfoToReport("Input project overview");
        ourSolutionPage.inputProjectOverview("AUTOMATION TEST - Contact Us with Embedded Team flow");

        logInfoToReport("Project languages");
        ourSolutionPage.hasLanguageOrFrameworkDefined("No");
        ourSolutionPage.clickOnNextButton();

        logInfoToReport("Select start date & time frame");
        ourSolutionPage.startDate("Next few months");
        ourSolutionPage.timeFrame("Over 6 months");
        ourSolutionPage.clickOnNextButton();

        logInfoToReport("Input customer first name: " + firstName);
        ourSolutionPage.inputUserInfo("First Name", firstName);

        logInfoToReport("Input customer last name: " + lastName);
        ourSolutionPage.inputUserInfo("Last Name", lastName);

        logInfoToReport("Input customer email: " + email);
        ourSolutionPage.inputUserInfo("Email", email);

        logInfoToReport("Input phone number: " + phoneNumber);
        ourSolutionPage.inputUserInfo("Phone Number", phoneNumber);

        logInfoToReport("Select timezone");
        ourSolutionPage.selectTimezone("(GMT -8:00) Pacific Time (US & Canada)");

        logInfoToReport("Input company name: " + companyName);
        ourSolutionPage.inputUserInfo("Company Name", companyName);

        logInfoToReport("Finish the form");
        ourSolutionPage.clickOnFinishButton();

        logInfoToReport("Verify the success message displayed");
        Assert.assertTrue(ourSolutionPage.isSuccessMessageDisplayed());
    }

    @Test
    public void Contact_Us_Flow_With_Send_Message(Method method) {
        fullName = DataFakerHelpers.getFaker().name().fullName();
        email = DataFakerHelpers.getFaker().internet().emailAddress();
        phoneNumber = DataFakerHelpers.getFaker().phoneNumber().cellPhone();
        message = "AUTOMATION TEST " + generateRandomNumber();

        startTest(method.getName(), "Contact Us with Send Message flow");
        logInfoToReport("Open Contact Us page");
        contactUsPage = homePage.navigateToContactUsPage();

        logInfoToReport("Input customer name: " + fullName);
        contactUsPage.inputUserInfo("name", fullName);

        logInfoToReport("Input customer phone number: " + phoneNumber);
        contactUsPage.inputUserInfo("phone", phoneNumber);

        logInfoToReport("Input customer email: " + email);
        contactUsPage.inputUserInfo("email", email);

        logInfoToReport("Input message");
        contactUsPage.inputMessage(message);

        logInfoToReport("Click send message");
        contactUsPage.clickSendMessage();

        logInfoToReport("Verify email content has been sent correctly");
        List<String> expectedEmailContents = List.of(new String[]{fullName, phoneNumber, email});
        Assert.assertTrue(GMail.isEmailContentMeetExpectation(GlobalConstants.EMAIL_ACCOUNT, "Website Inbound", expectedEmailContents));
    }

    @Test
    public void Contact_Us_Flow_With_Autonomous_Team(Method method) {
        firstName = DataFakerHelpers.getFaker().name().firstName();
        lastName = DataFakerHelpers.getFaker().name().lastName();
        email = DataFakerHelpers.getFaker().internet().emailAddress();
        companyName = DataFakerHelpers.getFaker().company().name();
        phoneNumber = DataFakerHelpers.getFaker().phoneNumber().cellPhone();

        startTest(method.getName(), "Contact Us flow with autonomous team");
        logInfoToReport("Open Contact Us page");
        contactUsPage = homePage.navigateToContactUsPage();

        logInfoToReport("Open Our Solution page");
        ourSolutionPage = contactUsPage.navigateToOurSolutionsPage();

        logInfoToReport("Choose desired team");
        ourSolutionPage.selectDesiredTeam("Build your product");

        logInfoToReport("Input project goal");
        ourSolutionPage.inputProjectGoal("AUTOMATION TEST - Contact Us with Autonomous Team flow");

        logInfoToReport("Select platform");
        ourSolutionPage.selectPlatform("Mobile Web App");
        ourSolutionPage.clickOnNextButton();

        logInfoToReport("Project languages");
        ourSolutionPage.hasLanguageOrFrameworkDefined("No");

        logInfoToReport("Select new or existing product or code");
        ourSolutionPage.selectNewOrExistingProduct("New");
        ourSolutionPage.clickOnNextButton();

        logInfoToReport("Select start date");
        ourSolutionPage.startDate("ASAP!");

        logInfoToReport("Select target dates");
        ourSolutionPage.hasTargetDatesSet("No");

        logInfoToReport("Select budget");
        ourSolutionPage.selectBudget("Between 20k - 50k ($US)");
        ourSolutionPage.clickOnNextButton();

        logInfoToReport("Input customer first name: " + firstName);
        ourSolutionPage.inputUserInfo("First Name", firstName);

        logInfoToReport("Input customer last name: " + lastName);
        ourSolutionPage.inputUserInfo("Last Name", lastName);

        logInfoToReport("Input customer email: " + email);
        ourSolutionPage.inputUserInfo("Email", email);

        logInfoToReport("Input phone number");
        ourSolutionPage.inputUserInfo("Phone Number", phoneNumber);

        logInfoToReport("Select timezone");
        ourSolutionPage.selectTimezone("(GMT -8:00) Pacific Time (US & Canada)");

        logInfoToReport("Input company name" + companyName);
        ourSolutionPage.inputUserInfo("Company Name", companyName);

        logInfoToReport("Finish the form");
        ourSolutionPage.clickOnFinishButton();

        logInfoToReport("Verify the success message displayed");
        Assert.assertTrue(ourSolutionPage.isSuccessMessageDisplayed());
    }

    @AfterClass(alwaysRun = true)
    public void afterClass() {
        closeBrowserDriver();
    }
}
