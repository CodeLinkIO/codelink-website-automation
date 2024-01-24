package pageObjects;

import common.BasePage;
import interfaces.MainMenuUI;
import org.openqa.selenium.WebDriver;

public class MainMenuBar extends BasePage {
    WebDriver driver;

    public MainMenuBar(WebDriver driver) {
        this.driver = driver;
    }

    public void hoverItemOnMainMenu(String item) {
        waitForElementVisible(driver, MainMenuUI.DYNAMIC_BUTTON_MAIN_MENU, item);
        hoverMouseToElement(driver, MainMenuUI.DYNAMIC_BUTTON_MAIN_MENU, item);
    }

    public void selectItemOnSubMenu(String item) {
        waitForElementVisible(driver, MainMenuUI.DYNAMIC_BUTTON_SELECT_SUB_MENU, item);
        clickToElement(driver, MainMenuUI.DYNAMIC_BUTTON_SELECT_SUB_MENU, item);
    }

    public boolean isTitleDisplayed(String title) {
        waitForElementVisible(driver, MainMenuUI.DYNAMIC_TITLE, title);
        return isElementDisplayed(driver, MainMenuUI.DYNAMIC_TITLE, title);
    }

}
