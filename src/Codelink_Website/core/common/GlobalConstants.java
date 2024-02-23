package common;

import java.io.File;

public class GlobalConstants {
    public static String CODELINK_WEBSITE_STG_URL = "http://trantor.codelink.io/";
    public static String CODELINK_WEBSITE_PROD_URL = "https://tanoto-fe-dev.web.app/";

    // Timeout variable
    public static long LONG_TIMEOUT = 60;
    public static long NORMAL_TIMEOUT = 30;
    public static long SHORT_TIMEOUT = 15;
    public static long VERY_SHORT_TIMEOUT = 5;
    public static String UPLOAD_FILE = "";
    public static final String PROJECT_PATH = System.getProperty("user.dir");
    public static final String JAVA_VERSION = System.getProperty("java.version");
    public static final String OS_NAME = System.getProperty("os.name");
    public static final String SCREENSHOTS_FILE_PATH = PROJECT_PATH + File.separator + "reportNGScreenshots" + File.separator;

    //Services pages
    public static String ARTIFICIAL_INTELLIGENCE_PAGE = "https://trantor.codelink.io/services/ai-ml";
    public static String SOFTWARE_DEVELOPMENT_PAGE = "https://trantor.codelink.io/services/software-development";
    public static String EMBEDDED_TEAMS_PAGE = "https://trantor.codelink.io/services/embedded-teams";
    public static String START_UP_GROWTH_PAGE = "https://trantor.codelink.io/services/start-up-growth";

    //Industries pages
    public static String WEB3_PAGE = "https://trantor.codelink.io/industries/web3";
    public static String LOGISTICS_PAGE = "https://trantor.codelink.io/industries/logistics";
    public static String HEALTH_TECH_PAGE = "https://trantor.codelink.io/industries/health-tech";
    public static String FINTECH_PAGE = "https://trantor.codelink.io/industries/fin-tech";
    public static String EDUCATION_PAGE = "https://trantor.codelink.io/industries/education";
    public static String COMMUNICATION_AND_MEDIA_PAGE = "https://trantor.codelink.io/industries/communication-and-media";

    //Company pages
    public static String OUR_COMPANY_PAGE = "https://trantor.codelink.io/our-company";
    public static String OUR_TEAM_PAGE = "https://trantor.codelink.io/our-team";
    public static String LOCATIONS_PAGE = "https://trantor.codelink.io/locations";
    public static String PLAYBOOK_PAGE = "https://trantor.codelink.io/our-playbook";
    public static String BLOG_PAGE = "https://trantor.codelink.io/blog";
    public static String CAREERS_PAGE = "https://apply.workable.com/codelink/?lng=en";

    //Location pages
    public static String HOCHIMINH_PAGE = "https://trantor.codelink.io/locations?index=0";
    public static String TORONTO_PAGE = "https://trantor.codelink.io/locations?index=1";
    public static String HANOI_PAGE = "https://trantor.codelink.io/locations?index=2";
    public static String DANANG_PAGE = "https://trantor.codelink.io/locations?index=3";

}
