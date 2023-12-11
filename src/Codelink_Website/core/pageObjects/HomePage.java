package pageObjects;

import common.BasePage;
import interfaces.HomePageUI;
import org.openqa.selenium.WebDriver;
import pageObjects.ContactUsPage;

public class HomePage extends BasePage {
    WebDriver driver;

    public HomePage(WebDriver driver){
        this.driver= driver;
    }
    public ContactUsPage navigateToContactUsPage() {
        waitForElementClickable(driver, HomePageUI.CONTACT_US_BTN);
        clickToElement(driver, HomePageUI.CONTACT_US_BTN);
        return new ContactUsPage(driver);
    }
}
