package testcases;

import common.BaseTest;
import org.testng.annotations.Test;
import ultilities.JsonUtils;

public class LinksAndPagesTest extends BaseTest {
    @Test
    public void TC_LinksAndPages_CompanyLinksAndPages() {

        getMainMenuBar().hoverItemOnMainMenu("Company").selectItemOnSubMenu("Our Company")
                .verifyPageTitleDisplayedAsExpected("OUR COMPANY").verifyPageURLDisplayedAsExpected(true, JsonUtils.getTestConfig().get("URLs").get("OurCompanyPage").asText());
        getMainMenuBar()
                .hoverItemOnMainMenu("Company").selectItemOnSubMenu("Our Team")
                .verifyPageTitleDisplayedAsExpected("OUR TEAM").verifyPageURLDisplayedAsExpected(true, JsonUtils.getTestConfig().get("URLs").get("OurTeamPage").asText());
        getMainMenuBar()
                .hoverItemOnMainMenu("Company").selectItemOnSubMenu("Locations")
                .verifyPageTitleDisplayedAsExpected("HEADQUARTERS").verifyPageURLDisplayedAsExpected(true, JsonUtils.getTestConfig().get("URLs").get("LocationsPage").asText());
        getMainMenuBar()
                .hoverItemOnMainMenu("Company").selectItemOnSubMenu("Playbook")
                .verifyPageTitleDisplayedAsExpected("PLAYBOOK").verifyPageURLDisplayedAsExpected(true, JsonUtils.getTestConfig().get("URLs").get("PlaybookPage").asText());
        getMainMenuBar()
                .hoverItemOnMainMenu("Company").selectItemOnSubMenu("Blog")
                .verifyPageTitleDisplayedAsExpected("BLOG").verifyPageURLDisplayedAsExpected(true, JsonUtils.getTestConfig().get("URLs").get("BlogPage").asText());
        getMainMenuBar()
                .hoverItemOnMainMenu("Company").selectItemOnSubMenu("Careers")
                .switchTabByTitle("CodeLink - Current Openings").verifyPageURLDisplayedAsExpected(false, JsonUtils.getTestConfig().get("URLs").get("CareersPage").asText());

    }

    @Test
    public void TC_LinksAndPages_IndustriesLinksAndPages() {
        getMainMenuBar()
                .hoverItemOnMainMenu("Industries").selectItemOnSubMenu("Web3")
                .verifyPageTitleDisplayedAsExpected("Web3").verifyPageURLDisplayedAsExpected(true, JsonUtils.getTestConfig().get("URLs").get("Web3Page").asText());
        getMainMenuBar()
                .hoverItemOnMainMenu("Industries").selectItemOnSubMenu("Logistics")
                .verifyPageTitleDisplayedAsExpected("Logistics").verifyPageURLDisplayedAsExpected(true, JsonUtils.getTestConfig().get("URLs").get("LogisticsPage").asText());
        getMainMenuBar()
                .hoverItemOnMainMenu("Industries").selectItemOnSubMenu("Health Tech")
                .verifyPageTitleDisplayedAsExpected("Health Tech").verifyPageURLDisplayedAsExpected(true, JsonUtils.getTestConfig().get("URLs").get("HealthTechPage").asText());
        getMainMenuBar()
                .hoverItemOnMainMenu("Industries").selectItemOnSubMenu("FinTech")
                .verifyPageTitleDisplayedAsExpected("FinTech").verifyPageURLDisplayedAsExpected(true, JsonUtils.getTestConfig().get("URLs").get("FintechPage").asText());
        getMainMenuBar()
                .hoverItemOnMainMenu("Industries").selectItemOnSubMenu("Education")
                .verifyPageTitleDisplayedAsExpected("Education").verifyPageURLDisplayedAsExpected(true, JsonUtils.getTestConfig().get("URLs").get("EducationPage").asText());
        getMainMenuBar()
                .hoverItemOnMainMenu("Industries").selectItemOnSubMenu("Communication & Media")
                .verifyPageTitleDisplayedAsExpected("Communication & Media").verifyPageURLDisplayedAsExpected(true, JsonUtils.getTestConfig().get("URLs").get("CommunicationAndMediaPage").asText());

    }

    @Test
    public void TC_LinksAndPages_ServicesLinksAndPages() {
        getMainMenuBar()
                .hoverItemOnMainMenu("Services").selectItemOnSubMenu("Artificial Intelligence & Machine Learning")
                .verifyPageTitleDisplayedAsExpected("Artificial Intelligence & Machine Learning").verifyPageURLDisplayedAsExpected(true, JsonUtils.getTestConfig().get("URLs").get("ArtificialIntelligencePage").asText());
        getMainMenuBar()
                .hoverItemOnMainMenu("Services").selectItemOnSubMenu("Software Development")
                .verifyPageTitleDisplayedAsExpected("Software Development").verifyPageURLDisplayedAsExpected(true, JsonUtils.getTestConfig().get("URLs").get("SoftwareDevelopmentPage").asText());
        getMainMenuBar()
                .hoverItemOnMainMenu("Services").selectItemOnSubMenu("Embedded Teams")
                .verifyPageTitleDisplayedAsExpected("Embedded Teams").verifyPageURLDisplayedAsExpected(true, JsonUtils.getTestConfig().get("URLs").get("EmbeddedTeamPage").asText());
        getMainMenuBar()
                .hoverItemOnMainMenu("Services").selectItemOnSubMenu("Start-up Growth")
                .verifyPageTitleDisplayedAsExpected("Start-up Growth").verifyPageURLDisplayedAsExpected(true, JsonUtils.getTestConfig().get("URLs").get("StartUpGrowthPage").asText());
    }

    @Test
    public void TC_LinksAndPages_LocationLinksAndPages() {
        getMainMenuBar()
                .hoverItemOnMainMenu("Company").selectItemOnSubMenu("Our Company");
        getOurCompanyPage()
                .navigateToLocationPage("Ho Chi Minh")
                .verifyPageTitleDisplayedAsExpected("HEADQUARTERS").verifyPageURLDisplayedAsExpected(true, JsonUtils.getTestConfig().get("URLs").get("HoChiMinhOfficePage").asText())
                .navigateBackwards("our-company");
        getOurCompanyPage()
                .navigateToLocationPage("Toronto")
                .verifyPageTitleDisplayedAsExpected("NORTH AMERICA").verifyPageURLDisplayedAsExpected(true, JsonUtils.getTestConfig().get("URLs").get("TorontoOfficePage").asText())
                .navigateBackwards("our-company");
        getOurCompanyPage()
                .navigateToLocationPage("Ha Noi")
                .verifyPageTitleDisplayedAsExpected("VIETNAM - NORTH").verifyPageURLDisplayedAsExpected(true, JsonUtils.getTestConfig().get("URLs").get("HaNoiOfficePage").asText())
                .navigateBackwards("our-company");
        getOurCompanyPage()
                .navigateToLocationPage("Da Nang")
                .verifyPageTitleDisplayedAsExpected("VIETNAM - CENTRAL").verifyPageURLDisplayedAsExpected(true, JsonUtils.getTestConfig().get("URLs").get("DaNangOfficePage").asText())
                .navigateBackwards("our-company");
    }
}
