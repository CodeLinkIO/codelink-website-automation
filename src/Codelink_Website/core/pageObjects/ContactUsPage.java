package core.pageObjects;

import core.actions.common.BasePage;
import core.interfaces.ContactUsUI;
import org.openqa.selenium.WebDriver;

public class ContactUsPage extends BasePage {
    WebDriver driver;

    public ContactUsPage(WebDriver driver){
        this.driver= driver;
    }

    public boolean isWelcomeTitleDisplayed() {
        waitForElementVisible(driver, ContactUsUI.GREETING_LABEL);
        return isElementDisplayed(driver, ContactUsUI.GREETING_LABEL);
    }

    public OurSolutionsPage navigateToOurSolutionsPage(){
        waitForElementVisible(driver, ContactUsUI.LETS_BEGIN_LINK);
        clickToElement(driver, ContactUsUI.LETS_BEGIN_LINK);
        return new OurSolutionsPage(driver);
    }
}
