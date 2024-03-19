package pageObjects;

import common.CommonPage;
import keywords.WebUI;
import org.openqa.selenium.By;

public class ContactUsPage extends CommonPage {
    private final By lnkLetsBegin = By.xpath("//a[text()=concat('Let',\"'s begin\")]");
    private final By txtMessage = By.name("message");
    private final By txtUserName = By.name("name");
    private final By txtPhone = By.name("phone");
    private final By txtEmail = By.name("email");
    private final By btnSendMessage = By.xpath("//span[text()='Send message']//parent::a");


    /**
     * Open our solution page
     */
    public ContactUsPage navigateToOurSolutionsPage() {
        WebUI.verifyElementVisible(lnkLetsBegin);
        WebUI.clickElement(lnkLetsBegin);
        return this;
    }

    public ContactUsPage fillUserBasicInfoAndSendMessage(String userName, String phone, String email, String message) {
        WebUI.verifyElementVisible(txtUserName);
        WebUI.scrollToElementAtTop(txtUserName);
        WebUI.clearAndFillText(txtUserName, userName);
        WebUI.clearAndFillText(txtPhone, phone);
        WebUI.clearAndFillText(txtEmail, email);
        WebUI.clearAndFillText(txtMessage, message);
        WebUI.clickElement(btnSendMessage);
        return this;
    }
}
