package pageObjects;

import common.BasePage;
import interfaces.ContactUsUI;
import interfaces.OurSolutionUI;
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

    public void inputUserInfoByName(String textboxName, String inputValue){
        waitForElementVisible(driver, ContactUsUI.CUSTOMER_INFO_TEXTBOX_BY_NAME, textboxName);
        scrollToElement(driver,ContactUsUI.CUSTOMER_INFO_TEXTBOX_BY_NAME, textboxName);
        sendKeyToElement(driver,ContactUsUI.CUSTOMER_INFO_TEXTBOX_BY_NAME, inputValue,textboxName);
    }

    public void clickSendMessage() {
        waitForElementClickable(driver, OurSolutionUI.DYNAMIC_BUTTON_BY_NAME, "Send message");
        clickToElement(driver, OurSolutionUI.DYNAMIC_BUTTON_BY_NAME, "Send message");
    }
}
