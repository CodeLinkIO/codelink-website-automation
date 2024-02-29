package pageObjects;

import interfaces.BlogUI;
import org.openqa.selenium.WebDriver;

public class BlogPage extends MainMenuBar {
    WebDriver driver;

    public BlogPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    public void navigateToBlogPage(String item) {
        scrollToElement(driver, BlogUI.DYNAMIC_TITLE_BLOG, item);
        waitForElementClickable(driver, BlogUI.DYNAMIC_TITLE_BLOG, item);
        clickToElement(driver, BlogUI.DYNAMIC_TITLE_BLOG, item);
    }
}
