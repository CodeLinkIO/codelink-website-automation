package reportConfig;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import constants.FrameworkConstants;
import driver.DriverManager;
import enums.CategoryType;
import helpers.CaptureHelpers;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import ultilities.BrowserInfoUtils;
import ultilities.IconUtils;
import ultilities.ReportUtils;

import java.util.Objects;

public class ExtentReportManager {
    private static ExtentReports extentReports;
    private static String link = "";

    public static void initReports() {
        if (Objects.isNull(extentReports)) {
            extentReports = new ExtentReports();
            link = FrameworkConstants.EXTENT_REPORT_FILE_PATH;
            System.out.println("Link Extent Report: " + link);
        }
        ExtentSparkReporter reporter = new ExtentSparkReporter(link);
        extentReports.attachReporter(reporter);
        reporter.config().setTheme(Theme.STANDARD);
        reporter.config().setDocumentTitle(FrameworkConstants.REPORT_TITLE);
        reporter.config().setReportName(FrameworkConstants.REPORT_TITLE);
        extentReports.setSystemInfo("Project Name", FrameworkConstants.PROJECT_NAME);
        System.out.println("Extent Reports is installed.");
    }

    public static void flushReports() {
        if (Objects.nonNull(extentReports)) {
            extentReports.flush();
        }
        ExtentTestManager.unload();
        ReportUtils.openReports(link);
    }

    public static void createTest(String testCaseName) {
        ExtentTestManager.setExtentTest(extentReports.createTest(IconUtils.getBrowserIcon() + " " + testCaseName));
    }

    public static void createTest(String testCaseName, String description) {
        ExtentTestManager.setExtentTest(extentReports.createTest(testCaseName, description));
    }

    public static void removeTest(String testCaseName) {
        extentReports.removeTest(testCaseName);
    }

    /**
     * Adds the screenshot.
     * @param message the message
     */
    public static void addScreenShot(String message) {
        String base64Image = "data:image/png;base64," + ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.BASE64);
        // File Path from Screenshot of Java
        ExtentTestManager.getExtentTest().log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(String.valueOf(CaptureHelpers.getScreenshotFile(message))).build());
    }

    /**
     * Adds the screenshot.
     *
     * @param status  the status
     * @param message the message
     */
    public static void addScreenShot(Status status, String message) {
        String base64Image = "data:image/png;base64," + ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.BASE64);
//        Base64 from Screenshot of Selenium
        ExtentTestManager.getExtentTest().log(status, MediaEntityBuilder.createScreenCaptureFromBase64String(base64Image).build());
//         File Path from Screenshot of Java
        ExtentTestManager.getExtentTest().log(status, MediaEntityBuilder.createScreenCaptureFromPath(CaptureHelpers.getScreenshotAbsolutePath(message)).build());
    }

    synchronized public static void addCategories(CategoryType[] categories) {
        if (categories == null) {
            ExtentTestManager.getExtentTest().assignCategory("REGRESSION");
        } else {
            for (CategoryType category : categories) {
                ExtentTestManager.getExtentTest().assignCategory(category.toString());
            }
        }
    }

    synchronized public static void addDevices() {
        ExtentTestManager.getExtentTest().assignDevice(BrowserInfoUtils.getBrowserInfo());
    }

    public static void logMessage(String message) {
        ExtentTestManager.getExtentTest().log(Status.INFO, message);
    }

    public static void logMessage(Status status, String message) {
        ExtentTestManager.getExtentTest().log(status, message);
    }

    public static void logMessage(Status status, Object message) {
        ExtentTestManager.getExtentTest().log(status, (Throwable) message);
    }

    public static void pass(String message) {
        ExtentTestManager.getExtentTest().pass(message);
    }

    public static void pass(Markup message) {
        ExtentTestManager.getExtentTest().pass(message);
    }

    public static void fail(String message) {
        ExtentTestManager.getExtentTest().fail(message);
    }

    public static void fail(Object message) {
        ExtentTestManager.getExtentTest().fail((String) message);
    }

    public static void fail(Markup message) {
        ExtentTestManager.getExtentTest().fail(message);
    }

    public static void skip(String message) {
        ExtentTestManager.getExtentTest().skip(message);
    }

    public static void skip(Markup message) {
        ExtentTestManager.getExtentTest().skip(message);
    }

    public static void info(Markup message) {
        ExtentTestManager.getExtentTest().info(message);
    }

    public static void info(String message) {
        ExtentTestManager.getExtentTest().info(message);
    }

    public static void warning(String message) {
        ExtentTestManager.getExtentTest().log(Status.WARNING, message);
    }
}