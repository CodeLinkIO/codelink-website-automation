package pageObjects;

import common.CommonPage;
import keywords.WebUI;
import org.openqa.selenium.By;

public class OurSolutionsPage extends CommonPage {
    private final By btnScaleYourTeam = By.xpath("//span[text()='Scale your team']//parent::a");
    private final By btnBuildYourProduct = By.xpath("//span[text()='Build your product']//parent::a");
    private final By teamMemberRoleList = By.xpath("//p[text()='Select required members']//parent::div//div//p");
    private final By platformList = By.xpath("//h4[text()='Select platform']//parent::div//div//p");
    private final By btnNext = By.xpath("//span[text()='Next']");
    private final By txtProjectOverview = By.name("projectOverview");
    private final By txtProjectGoal = By.name("projectGoal");
    private final By specificLanguage = By.xpath("//h4[text()='Languages']//parent::div//label");
    private final By newOrExistingProductCode = By.xpath("//h4[text()='New or Existing Product or Code']//parent::div//label");
    private final By startDateOptions = By.xpath("//h4[text()='Start Date']//parent::div//label");
    private final By timeframeOptions = By.xpath("//h4[text()='Time Frame']//parent::div//label");
    private final By targetDateOptions = By.xpath("//h4[text()='Target Dates']//parent::div//label");
    private final By budgetOptions = By.xpath("//h4[text()='Budget']//parent::div//label");
    private final By drpTimezone = By.xpath("//span[text()='Timezone ']//following-sibling::div//div");
    private final By txtLastName = By.name("lastName");
    private final By txtFirstName = By.name("firstName");
    private final By txtEmail = By.name("email");
    private final By txtPhoneNumber = By.name("phoneNumber");
    private final By txtProjectName = By.name("projectName");
    private final By txtCompanyName = By.name("companyName");
    private final By txtCompanyWebsite = By.name("companyWebsite");
    private final By txtMoreInfo = By.name("moreInfo");
    private final By txtTargetDate = By.name("targetDate");
    private final By btnFinish = By.xpath("//span[text()='Finish']");
    private final By lblSuccessMessage = By.xpath("//h2[text()='Thanks for the details!']");

    public OurSolutionsPage selectTeamMemberRole(String teamMemberRole) {
        WebUI.verifyElementVisible(teamMemberRoleList);
        WebUI.selectOptionDynamic(teamMemberRoleList, teamMemberRole);
        return this;
    }

    public OurSolutionsPage selectEmbeddedTeam() {
        WebUI.verifyElementVisible(btnScaleYourTeam);
        WebUI.clickElement(btnScaleYourTeam);
        return this;
    }

    public OurSolutionsPage selectAutonomousTeam() {
        WebUI.verifyElementVisible(btnBuildYourProduct);
        WebUI.clickElement(btnBuildYourProduct);
        return this;
    }

    public OurSolutionsPage clickNext() {
        WebUI.verifyElementClickable(btnNext);
        WebUI.clickElement(btnNext);
        return this;
    }

    public OurSolutionsPage inputProjectOverview(String projectOverview) {
        WebUI.verifyElementVisible(txtProjectOverview);
        WebUI.clearAndFillText(txtProjectOverview, projectOverview);
        return this;
    }

    public OurSolutionsPage hasSpecificLanguage(String yesNoSelection) {
        WebUI.selectOptionDynamic(specificLanguage, yesNoSelection);
        return this;
    }

    public OurSolutionsPage newOrExistingProductCode(String yesNoSelection) {
        WebUI.selectOptionDynamic(newOrExistingProductCode, yesNoSelection);
        return this;
    }

    public OurSolutionsPage selectStartDate(String startDate) {
        WebUI.selectOptionDynamic(startDateOptions, startDate);
        return this;
    }

    public OurSolutionsPage selectTimeframe(String timeframe) {
        WebUI.selectOptionDynamic(timeframeOptions, timeframe);
        return this;
    }

    public OurSolutionsPage selectBudget(String budget) {
        WebUI.selectOptionDynamic(budgetOptions, budget);
        return this;
    }

    public OurSolutionsPage hasTargetDate(String yesNoSelection) {
        WebUI.selectOptionDynamic(targetDateOptions, yesNoSelection);
        return this;
    }

    public OurSolutionsPage inputUserBasicInfo(String firstName, String lastName, String phoneNumber, String email, String timezone, String projectName
            , String companyName, String companyWebsite, String moreInfo) {
        WebUI.verifyElementVisible(txtFirstName);
        WebUI.clearAndFillText(txtFirstName, firstName);
        WebUI.clearAndFillText(txtLastName, lastName);
        WebUI.clearAndFillText(txtEmail, email);
        WebUI.clearAndFillText(txtPhoneNumber, phoneNumber);
        WebUI.selectOptionDynamic(drpTimezone, timezone);
        WebUI.clearAndFillText(txtProjectName, projectName);
        WebUI.clearAndFillText(txtCompanyName, companyName);
        WebUI.clearAndFillText(txtCompanyWebsite, companyWebsite);
        WebUI.clearAndFillText(txtMoreInfo, moreInfo);
        return this;
    }

    public OurSolutionsPage clickFinish() {
        WebUI.verifyElementVisible(btnFinish);
        WebUI.clickElement(btnFinish);
        WebUI.verifyElementVisible(lblSuccessMessage);
        return this;
    }

    public OurSolutionsPage inputProjectGoal(String projectGoal) {
        WebUI.verifyElementVisible(txtProjectGoal);
        WebUI.clearAndFillText(txtProjectGoal, projectGoal);
        return this;
    }

    public OurSolutionsPage selectPlatform(String platform) {
        WebUI.verifyElementVisible(platformList);
        WebUI.selectOptionDynamic(platformList, platform);
        return this;
    }

    public OurSolutionsPage inputTargetDate(String targetDate) {
        WebUI.verifyElementVisible(txtTargetDate);
        WebUI.clearAndFillText(txtTargetDate, targetDate);
        return this;
    }
}
