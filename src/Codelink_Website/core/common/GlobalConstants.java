package common;

import java.io.File;

public class GlobalConstants {
    public static final long SEND_EMAIL_TIMEOUT = 5;
    public static final long LOADING_TIME = 3;
    public static final String UPLOAD_FILE = GlobalConstants.PROJECT_PATH + File.separator;
    public static String CODELINK_WEBSITE_STG_URL= "http://trantor.codelink.io/";
    public static String CODELINK_WEBSITE_PROD_URL= "https://www.codelink.io/";
    public static long LONG_TIMEOUT=60;
    public static long NORMAL_TIMEOUT=30;
    public static long SHORT_TIMEOUT=15;
    public static long VERY_SHORT_TIMEOUT=5;
    public static final String PROJECT_PATH = System.getProperty("user.dir");
    public static final String JAVA_VERSION = System.getProperty("java.version");
    public static final String OS_NAME = System.getProperty("os.name");
    public static final String EMAIL_ACCOUNT = "cl-site-stg-automation@codelink.io";
    public static final String SCREENSHOTS_FILE_PATH = PROJECT_PATH +File.separator +"reportNGScreenshots" + File.separator;

}
