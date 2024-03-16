package pageObjects;

import common.CommonPage;
import keywords.WebUI;
import org.openqa.selenium.By;


public class HomePage extends CommonPage {
    private final By btnContactus = By.xpath("//div[@class='HeaderV2-module--right-container-desktop--2muX7']//span[text()='Contact Us']");

    public HomePage navigateToContactUsPage() {
        WebUI.clickElement(btnContactus);
        return this;
    }
}
