package testcases;

import common.BaseTest;
import keywords.WebUI;
import org.testng.Assert;
import org.testng.annotations.Test;
import ultilities.GMailUtils;
import ultilities.DateUtils;
import ultilities.JsonUtils;

import java.util.List;

import static ultilities.DataFakerUtils.getDataFaker;

public class ContactUsTest extends BaseTest {
    String firstName, lastName, email, companyName, phoneNumber, fullName, message;
    String timezone = "(GMT -8:00) Pacific Time (US & Canada)";


    @Test
    public void TC_ContactUs_ContactUsFlowWithSendMessage() {
        fullName = getDataFaker().name().fullName();
        email = getDataFaker().internet().emailAddress();
        phoneNumber = getDataFaker().phoneNumber().cellPhone();
        message = "AUTOMATION TEST " + WebUI.generateRandomNumber();

        GMailUtils.deleteEmail("Website Inbound");
        getHomePage().navigateToContactUsPage();
        getContactUsPage().fillUserBasicInfoAndSendMessage(fullName, phoneNumber, email, message);
        List<String> expectedEmailContents = List.of(new String[]{fullName, phoneNumber, email, message});
        Assert.assertTrue(GMailUtils.isEmailContentMeetExpectation(JsonUtils.getTestConfig().get("Email").asText(), "Website Inbound", expectedEmailContents));
    }

    @Test
    public void TC_ContactUs_ContactUsFlowWithEmbeddedTeam() {
        firstName = getDataFaker().name().firstName();
        lastName = getDataFaker().name().lastName();
        email = getDataFaker().internet().emailAddress();
        companyName = getDataFaker().company().name();
        phoneNumber = getDataFaker().phoneNumber().cellPhone();

        getHomePage().navigateToContactUsPage();
        getContactUsPage().navigateToOurSolutionsPage();
        getOurSolutionsPage().
                selectEmbeddedTeam().selectTeamMemberRole("Product Owner").selectTeamMemberRole("Technical Lead")
                .selectTeamMemberRole("Front-end Developer").selectTeamMemberRole("Back-end Developer").selectTeamMemberRole("Quality Assurance Engineer").clickNext()
                .inputProjectOverview("AUTOMATION TEST - Contact Us with Embedded Team flow").hasSpecificLanguage("No").clickNext()
                .selectStartDate("Next few months").selectTimeframe("Over 6 months").clickNext()
                .inputUserBasicInfo(firstName, lastName, phoneNumber, email, timezone,
                        "Codelink Website Automation", companyName, JsonUtils.getTestConfig().get("URLs").get("Production").asText(), "Automation Team Test").clickFinish();
    }


    @Test
    public void TC_ContactUs_ContactUsFlowWithAutonomousTeam() {
        firstName = getDataFaker().name().firstName();
        lastName = getDataFaker().name().lastName();
        email = getDataFaker().internet().emailAddress();
        companyName = getDataFaker().company().name();
        phoneNumber = getDataFaker().phoneNumber().cellPhone();

        getHomePage().navigateToContactUsPage();
        getContactUsPage().navigateToOurSolutionsPage();
        getOurSolutionsPage().selectAutonomousTeam();
        getOurSolutionsPage().inputProjectGoal("AUTOMATION TEST - Contact Us with Autonomous Team flow")
                .selectPlatform("Mobile Web App").clickNext()
                .hasSpecificLanguage("No").newOrExistingProductCode("New").clickNext()
                .selectStartDate("ASAP!").hasTargetDate("Yes").inputTargetDate(DateUtils.getFutureDate(30, "YYY/MM/DD")).selectBudget("Expected 50k+ ($US)").clickNext()
                .inputUserBasicInfo(firstName, lastName, phoneNumber, email, timezone, "Codelink Website Automation", companyName, JsonUtils.getTestConfig().get("URLs").get("Production").asText(), "Automation Team Test").clickFinish();

    }

}
