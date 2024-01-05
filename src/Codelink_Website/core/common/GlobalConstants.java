package common;

import java.io.File;

public class GlobalConstants {
    public static String CODELINK_WEBSITE_STG_URL= "https://www.codelink.io/";
    public static String CODELINK_WEBSITE_PROD_URL= "https://tanoto-fe-dev.web.app/";
    public static long LONG_TIMEOUT=60;
    public static long NORMAL_TIMEOUT=30;
    public static long SHORT_TIMEOUT=15;
    public static long VERY_SHORT_TIMEOUT=5;
    public static String UPLOAD_FILE="";

    // Timeout variable
    public static final long LONG_TIME = 60;
    public static final long NORMAL_TIME = 30;
    public static final long SHORT_TIME = 15;
    public static final int VERY_SHORT_TIME = 3;
    public static final long SLEEP_TIME = 3;
    public static final long LOADING_TIME = 3;
    public static final String PROJECT_PATH = System.getProperty("user.dir");
    public static final String JAVA_VERSION = System.getProperty("java.version");
    public static final String OS_NAME = System.getProperty("os.name");
    public static final String SCREENSHOTS_FILE_PATH = PROJECT_PATH +File.separator +"reportNGScreenshots" + File.separator;
    public static String ARTIFICIAL_INTELLIGENCE_PAGE= "https://www.codelink.io/services/ai-ml";
    public static String SOFTWARE_DEVELOPMENT_PAGE= "https://www.codelink.io/services/software-development";
    public static String EMBEDDED_TEAMS_PAGE= "https://www.codelink.io/services/embedded-teams";
    public static String START_UP_GROWTH_PAGE= "https://www.codelink.io/services/start-up-growth";
}
