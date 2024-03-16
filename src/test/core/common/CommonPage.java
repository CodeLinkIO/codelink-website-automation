package common;

import pageObjects.*;

public class CommonPage {
    private HomePage homePage;
    private ContactUsPage contactUsPage;
    private MainMenuBar mainMenuBar;
    private OurSolutionsPage ourSolutionsPage;

    public HomePage getHomePage() {
        if (homePage == null) {
            homePage = new HomePage();
        }
        return homePage;
    }

    public ContactUsPage getContactUsPage() {
        if (contactUsPage == null) {
            contactUsPage = new ContactUsPage();
        }
        return contactUsPage;
    }

    public OurSolutionsPage getOurSolutionsPage() {
        if (ourSolutionsPage == null) {
            ourSolutionsPage = new OurSolutionsPage();
        }
        return ourSolutionsPage;
    }

    public MainMenuBar getMainMenuBar() {
        if (mainMenuBar == null) {
            mainMenuBar = new MainMenuBar();
        }
        return mainMenuBar;
    }
}
