package common;

import constants.FrameworkConstants;
import keywords.WebUI;
import org.openqa.selenium.By;
import pageObjects.*;

public class CommonPage {
    private HomePage homePage;
    private ContactUsPage contactUsPage;
    private MainMenuBar mainMenuBar;
    private OurSolutionsPage ourSolutionsPage;
    private OurCompanyPage ourCompanyPage;
    private final By lblPageTitle = By.xpath("//h1");

    public HomePage getHomePage() {
        if (homePage == null) {
            homePage = new HomePage();
        }
        return homePage;
    }

    public ContactUsPage getContactUsPage() {
        if (contactUsPage == null) {
            contactUsPage = new ContactUsPage();
        }
        return contactUsPage;
    }

    public OurSolutionsPage getOurSolutionsPage() {
        if (ourSolutionsPage == null) {
            ourSolutionsPage = new OurSolutionsPage();
        }
        return ourSolutionsPage;
    }

    public MainMenuBar getMainMenuBar() {
        if (mainMenuBar == null) {
            mainMenuBar = new MainMenuBar();
        }
        return mainMenuBar;
    }

    public OurCompanyPage getOurCompanyPage() {
        if (ourCompanyPage == null) {
            ourCompanyPage = new OurCompanyPage();
        }
        return ourCompanyPage;
    }

    public CommonPage verifyPageTitleDisplayedAsExpected(String pageTitle) {
        WebUI.verifyElementVisible(lblPageTitle);
        WebUI.verifyElementTextEquals(lblPageTitle, pageTitle);
        return this;
    }

    public CommonPage verifyPageURLDisplayedAsExpected(boolean codelinkURL, String pageURL) {
        if (codelinkURL) {
            WebUI.verifyElementVisible(lblPageTitle);
        }
        WebUI.verifyPageURLEqual(pageURL);
        return this;
    }

    public CommonPage switchTabByTitle(String pageTitle) {
        WebUI.switchToWindowOrTabByTitle(pageTitle);
        return this;
    }

    public CommonPage navigateBackwards(String expectedURL) {
        WebUI.navigateBack(expectedURL, FrameworkConstants.WAIT_EXPLICIT);
        WebUI.waitForPageLoaded(FrameworkConstants.WAIT_EXPLICIT);
        return this;
    }
}
