package pageObjects;

import common.CommonPage;
import keywords.WebUI;
import org.openqa.selenium.By;

public class MainMenuBar extends CommonPage {
    private final By menuBarOptions = By.xpath("//div[contains(@class, 'HeaderV2-module--right-container-desktop')]//span");
    private final By subMenuBarOptions = By.xpath("//div[contains(@class, 'DesktopMenuExtension-module')]//h6");
    private final By lblPageTitle = By.xpath("//h1");

    public MainMenuBar hoverItemOnMainMenu(String menuItem) {
        WebUI.verifyElementVisible(menuBarOptions);
        WebUI.hoverOnDynamicElement(menuBarOptions, menuItem);
        return this;
    }

    public MainMenuBar selectItemOnSubMenu(String subItem) {
        WebUI.verifyElementVisible(subMenuBarOptions);
        WebUI.selectOptionDynamic(subMenuBarOptions, subItem);
        return this;
    }

    public MainMenuBar verifyPageTitleDisplayedAsExpected(String pageTitle) {
        WebUI.verifyElementVisible(lblPageTitle);
        WebUI.verifyElementTextEquals(lblPageTitle, pageTitle);
        return this;
    }

    public MainMenuBar verifyPageURLDisplayedAsExpected(boolean codelinkURL, String pageURL) {
        if (codelinkURL) {
            WebUI.verifyElementVisible(lblPageTitle);
        }
        WebUI.verifyPageURLEqual(pageURL);
        return this;
    }

    public MainMenuBar switchTabByTitle(String pageTitle) {
        WebUI.switchToWindowOrTabByTitle(pageTitle);
        return this;
    }
}
