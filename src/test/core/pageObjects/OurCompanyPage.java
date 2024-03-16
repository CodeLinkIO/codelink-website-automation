package pageObjects;

import common.CommonPage;
import keywords.WebUI;
import org.openqa.selenium.By;

public class OurCompanyPage extends CommonPage {
    private final By lblLocations = By.xpath("//h3[text()='Locations']");
    private final By locationOptions = By.xpath("//h3[@class='LocationCard-module--name--3aCn_']");


    public OurCompanyPage navigateToLocationPage(String location) {
        WebUI.verifyElementPresent(lblLocations);
        WebUI.scrollToElementAtBottom(lblLocations);
        WebUI.selectOptionDynamic(locationOptions, location);
        return this;
    }
}
