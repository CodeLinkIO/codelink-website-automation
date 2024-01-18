package pageObjects;

import common.BasePage;
import interfaces.ServicesUI;
import org.openqa.selenium.WebDriver;

public class CommonFunctionsPage extends BasePage {
    WebDriver driver;

    public CommonFunctionsPage(WebDriver driver) {
        this.driver = driver;
    }

    public void selectHomeBar(String homeBar) {
        waitForElementVisible(driver, ServicesUI.DYNAMIC_BUTTON_HOME_BAR, homeBar);
        hoverMouseToElement(driver, ServicesUI.DYNAMIC_BUTTON_HOME_BAR, homeBar);
    }

    public void selectItemInHomeBar(String item) {
        waitForElementVisible(driver, ServicesUI.DYNAMIC_BUTTON_SELECT_HOME_BAR, item);
        clickToElement(driver, ServicesUI.DYNAMIC_BUTTON_SELECT_HOME_BAR, item);
    }

    public boolean isTitleDisplayed(String title) {
        waitForElementVisible(driver, ServicesUI.DYNAMIC_TITLE, title);
        return isElementDisplayed(driver, ServicesUI.DYNAMIC_TITLE, title);
    }

}
