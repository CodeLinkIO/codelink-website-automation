package pageObjects;

import common.BasePage;
import interfaces.OurSolutionUI;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class OurSolutionsPage extends BasePage {
    WebDriver driver;
    public OurSolutionsPage(WebDriver driver){
        this.driver=driver;
    }

    public void chooseTeamMember(String teamMemberRole){
        waitForElementVisible(driver, OurSolutionUI.TEAM_MEMBER_SELECTION_BY_ROLE, teamMemberRole);
        scrollToElement(driver, OurSolutionUI.TEAM_MEMBER_SELECTION_BY_ROLE, teamMemberRole);
        clickToElement(driver, OurSolutionUI.TEAM_MEMBER_SELECTION_BY_ROLE, teamMemberRole);
    }

    public void selectDesiredTeam(String teamType) {
        waitForElementVisible(driver, OurSolutionUI.DYNAMIC_BUTTON_BY_NAME,teamType);
        clickToElement(driver, OurSolutionUI.DYNAMIC_BUTTON_BY_NAME,teamType);
    }

    public void clickOnNextButton(){
        waitForElementVisible(driver, OurSolutionUI.DYNAMIC_BUTTON_BY_NAME,"Next");
        clickToElement(driver, OurSolutionUI.DYNAMIC_BUTTON_BY_NAME,"Next");
    }

    public void inputProjectOverview(String describeProjectOverview){
        waitForElementVisible(driver,OurSolutionUI.PROJECT_OVERVIEW, describeProjectOverview);
        sendKeyToElement(driver,OurSolutionUI.PROJECT_OVERVIEW, describeProjectOverview);
    }

    public void languageOrFrameworkDefined(String yesNo){
        waitForElementVisible(driver, OurSolutionUI.DYNAMIC_RADIO_BTN, yesNo);
        clickToElement(driver, OurSolutionUI.DYNAMIC_RADIO_BTN, yesNo);
    }

    public void startDate(String startDate){
        waitForElementVisible(driver, OurSolutionUI.DYNAMIC_RADIO_BTN, startDate);
        clickToElement(driver, OurSolutionUI.DYNAMIC_RADIO_BTN, startDate);
    }

    public void timeFrame(String timeFrame){
        waitForElementVisible(driver, OurSolutionUI.DYNAMIC_RADIO_BTN, timeFrame);
        scrollToElement(driver, OurSolutionUI.DYNAMIC_RADIO_BTN, timeFrame);
        clickToElement(driver, OurSolutionUI.DYNAMIC_RADIO_BTN, timeFrame);
    }

    public void inputUserInfoByName(String textboxName, String inputValue){
        waitForElementVisible(driver,OurSolutionUI.DYNAMIC_TEXTBOX_BY_NAME, textboxName);
        scrollToElement(driver,OurSolutionUI.DYNAMIC_TEXTBOX_BY_NAME, textboxName);
        sendKeyToElement(driver,OurSolutionUI.DYNAMIC_TEXTBOX_BY_NAME, inputValue,textboxName);
    }

    public void selectTimezone(String expectedTimezone){
        waitForElementVisible(driver,OurSolutionUI.TIMEZONE_DROPDOWN);
        clickToElement(driver,OurSolutionUI.TIMEZONE_DROPDOWN);
        List<WebElement> listAllTimeZone= getListWebElement(driver,OurSolutionUI.ALL_TIME_ZONE);
        for(WebElement timezone: listAllTimeZone){
            String actualTimezone= timezone.getText();
            if(actualTimezone.equals(expectedTimezone)){
                JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
                jsExecutor.executeScript("arguments[0].scrollIntoView(true);", timezone);
                sleepInSecond(1);
                timezone.click();
                sleepInSecond(1);
                break;
            }
        }
    }

    public void clickOnFinishButton(){
        waitForElementVisible(driver, OurSolutionUI.FINISH_BTN);
        clickToElement(driver, OurSolutionUI.FINISH_BTN);
    }

    public boolean isSuccessMessageDisplayed(){
        waitForElementVisible(driver,OurSolutionUI.SUCCESS_MESSAGE);
        return isElementDisplayed(driver,OurSolutionUI.SUCCESS_MESSAGE);
    }


}
