package pageObjects;

import interfaces.OurCompanyUI;
import org.openqa.selenium.WebDriver;

public class OurCompanyPage extends MainMenuBar {
    WebDriver driver;

    public OurCompanyPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    public void navigateToLocationPage(String item) {
        scrollToElement(driver, OurCompanyUI.LABEL_LOCATION);
        waitForElementClickable(driver, OurCompanyUI.DYNAMIC_BUTTON_LOCATION, item);
        clickToElement(driver, OurCompanyUI.DYNAMIC_BUTTON_LOCATION, item);
    }
}
