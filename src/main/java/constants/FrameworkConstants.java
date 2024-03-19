package constants;

import helpers.Helpers;
import helpers.PropertiesHelpers;

import java.io.File;

public final class FrameworkConstants {

    private FrameworkConstants() {
    }

    static {
        PropertiesHelpers.loadAllFiles();
    }

    public static final String BROWSER = PropertiesHelpers.getValue("BROWSER");
    public static final String ENVIRONMENT_PREFIX = PropertiesHelpers.getValue("ENVIRONMENT_PREFIX");
    public static final String FILE_UPLOAD_FOLDER = PropertiesHelpers.getValue("FILE_UPLOAD_FOLDER");
    public static final String TEST_FILES_FOLDER = PropertiesHelpers.getValue("TEST_FILES_FOLDER");
    public static final String DOWNLOADS_FOLDER = PropertiesHelpers.getValue("DOWNLOADS_FOLDER");

    // Extent report
    public static final String REPORT_TITLE = PropertiesHelpers.getValue("REPORT_TITLE");
    public static final String EXTENT_REPORT_NAME = PropertiesHelpers.getValue("EXTENT_REPORT_NAME");
    public static final String EXTENT_REPORT_FOLDER = PropertiesHelpers.getValue("EXTENT_REPORT_FOLDER");
    public static final String EXPORT_VIDEO_PATH = PropertiesHelpers.getValue("EXPORT_VIDEO_PATH");
    public static final String EXPORT_CAPTURE_PATH = PropertiesHelpers.getValue("EXPORT_CAPTURE_PATH");
    public static final String OVERRIDE_REPORTS = PropertiesHelpers.getValue("OVERRIDE_REPORTS");
    public static final String AUTHOR = PropertiesHelpers.getValue("AUTHOR");
    public static final String PROJECT_NAME = PropertiesHelpers.getValue("PROJECT_NAME");
    public static final String EXTENT_REPORT_FOLDER_PATH = Helpers.getCurrentDir() + EXTENT_REPORT_FOLDER;
    public static final String EXTENT_REPORT_FILE_NAME = EXTENT_REPORT_NAME + ".html";
    public static String EXTENT_REPORT_FILE_PATH = EXTENT_REPORT_FOLDER_PATH + File.separator + EXTENT_REPORT_FILE_NAME;
    public static final String OPEN_REPORTS_AFTER_EXECUTION = PropertiesHelpers.getValue("OPEN_REPORTS_AFTER_EXECUTION");

    // Wait
    public static final int WAIT_DEFAULT = Integer.parseInt(PropertiesHelpers.getValue("WAIT_DEFAULT"));
    public static final int WAIT_IMPLICIT = Integer.parseInt(PropertiesHelpers.getValue("WAIT_IMPLICIT"));
    public static final int WAIT_EXPLICIT = Integer.parseInt(PropertiesHelpers.getValue("WAIT_EXPLICIT"));
    public static final int WAIT_PAGE_LOADED = Integer.parseInt(PropertiesHelpers.getValue("WAIT_PAGE_LOADED"));
    public static final int WAIT_SLEEP_STEP = Integer.parseInt(PropertiesHelpers.getValue("WAIT_SLEEP_STEP"));

    // Screenshot and record
    public static final String SCREENSHOT_PASSED_STEPS = PropertiesHelpers.getValue("SCREENSHOT_PASSED_STEPS");
    public static final String SCREENSHOT_FAILED_STEPS = PropertiesHelpers.getValue("SCREENSHOT_FAILED_STEPS");
    public static final String SCREENSHOT_SKIPPED_STEPS = PropertiesHelpers.getValue("SCREENSHOT_SKIPPED_STEPS");
    public static final String SCREENSHOT_ALL_STEPS = PropertiesHelpers.getValue("SCREENSHOT_ALL_STEPS");
    public static final String VIDEO_RECORD = PropertiesHelpers.getValue("VIDEO_RECORD");
    public static final String ACTIVE_PAGE_LOADED = PropertiesHelpers.getValue("ACTIVE_PAGE_LOADED");

    //Zip file for Report folder
    public static final String ZIPPED_EXTENT_REPORTS_FOLDER = EXTENT_REPORT_FOLDER + ".zip";
    public static final String ZIP_FOLDER = PropertiesHelpers.getValue("ZIP_FOLDER");
    public static final String ZIP_FOLDER_PATH = PropertiesHelpers.getValue("ZIP_FOLDER_PATH");
    public static final String ZIP_FOLDER_NAME = PropertiesHelpers.getValue("ZIP_FOLDER_NAME");
    public static final Object YES = "yes";
    public static final Object NO = "no";

    //Icons
    public static final String ICON_OS_WINDOWS = "<i class='fa fa-windows' ></i>";
    public static final String ICON_OS_MAC = "<i class='fa fa-apple' ></i>";
    public static final String ICON_OS_LINUX = "<i class='fa fa-linux' ></i>";
    public static final String ICON_BROWSER_EDGE = "<i class=\"fa fa-edge\" aria-hidden=\"true\"></i>";
    public static final String ICON_BROWSER_CHROME = "<i class=\"fa fa-chrome\" aria-hidden=\"true\"></i>";
    public static final String ICON_BROWSER_FIREFOX = "<i class=\"fa fa-firefox\" aria-hidden=\"true\"></i>";

}
