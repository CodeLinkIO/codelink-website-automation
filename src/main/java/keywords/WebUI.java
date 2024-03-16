package keywords;

import constants.FrameworkConstants;
import driver.DriverManager;
import enums.FailureHandling;
import helpers.Helpers;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.print.PrintOptions;
import org.openqa.selenium.support.ui.*;
import reportConfig.ExtentReportManager;
import reportConfig.ExtentTestManager;
import ultilities.DateUtils;
import ultilities.LogUtils;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import javax.annotation.Nullable;
import java.awt.*;
import java.awt.event.InputEvent;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;
import java.util.List;

public class WebUI {
    /**
     * The SoftAssert object is created
     */
    private static final SoftAssert softAssert = new SoftAssert();

    /**
     * Stop the Soft Assert of TestNG
     */
    public static void stopSoftAssertAll() {
        softAssert.assertAll();
    }

    /**
     * Smart Waits contains waitForPageLoaded and sleep functions
     */
    public static void smartWait() {
        if (FrameworkConstants.ACTIVE_PAGE_LOADED.trim().equalsIgnoreCase("true")) {
            waitForPageLoaded();
        }
        sleep(FrameworkConstants.WAIT_SLEEP_STEP);
    }

    /**
     * Take entire-page screenshot and add to Extent report
     *
     * @param screenName Screenshot name
     */
    public static void addScreenshotToReport(String screenName) {
        if (FrameworkConstants.SCREENSHOT_ALL_STEPS.equals(FrameworkConstants.YES)) {
            if (ExtentTestManager.getExtentTest() != null) {
                ExtentReportManager.addScreenShot(Helpers.makeSlug(screenName));
            }
        }
    }

    /**
     * Take a screenshot of a specific web element. The captured image will be saved in '.png' format.
     *
     * @param screenName Screenshot name
     */
    public static void takeElementScreenshot(By by, String screenName) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");
        try {
            String path = Helpers.getCurrentDir() + FrameworkConstants.EXPORT_CAPTURE_PATH;
            File file = new File(path);
            if (!file.exists()) {
                LogUtils.info("No Folder: " + path);
                file.mkdir();
                LogUtils.info("Folder created: " + file);
            }
            File source = getWebElement(by).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(source, new File(path + "/" + screenName + "_" + dateFormat.format(new Date()) + ".png"));
            LogUtils.info("Screenshot taken: " + screenName);
            LogUtils.info("Screenshot taken current URL: " + getCurrentUrl());
        } catch (Exception e) {
            System.out.println("Exception while taking screenshot: " + e.getMessage());
        }
    }

    /**
     * Take entire-page screenshot, including overflow parts. The captured image will be saved in '.png' format.
     * This method simulates a scroll action to take a number of shots and merge them together to make a full-page screenshot.
     *
     * @param screenName Screenshot name
     */
    public static void takeFullPageScreenshot(String screenName) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");
        try {
            String path = Helpers.getCurrentDir() + FrameworkConstants.EXPORT_CAPTURE_PATH;
            File file = new File(path);
            if (!file.exists()) {
                LogUtils.info("No Folder: " + path);
                file.mkdir();
                LogUtils.info("Folder created: " + file);
            }
            LogUtils.info("Driver for Screenshot: " + DriverManager.getDriver());
            // Create reference of TakesScreenshot
            TakesScreenshot ts = (TakesScreenshot) DriverManager.getDriver();
            // Call the capture screenshot function - getScreenshotAs
            File source = ts.getScreenshotAs(OutputType.FILE);
            // result.getName() gets the name of the test case and assigns it to the screenshot file name
            FileUtils.copyFile(source, new File(path + "/" + screenName + "_" + dateFormat.format(new Date()) + ".png"));
            LogUtils.info("Screenshot taken: " + screenName);
            LogUtils.info("Screenshot taken current URL: " + getCurrentUrl());
        } catch (Exception e) {
            System.out.println("Exception while taking screenshot: " + e.getMessage());
        }
    }

    /**
     * Get the Download Directory path
     *
     * @return the download directory path
     */
    public static String getPathDownloadDirectory() {
        String path = "";
        String machine_name = System.getProperty("user.home");
        path = machine_name + File.separator + "Downloads";
        return path;
    }

    /**
     * Count files in Download Directory
     *
     * @return files total in download directory
     */
    public static int countFilesInDownloadDirectory() {
        String pathFolderDownload = getPathDownloadDirectory();
        File file = new File(pathFolderDownload);
        int i = 0;
        for (File listOfFiles : file.listFiles()) {
            if (listOfFiles.isFile()) {
                i++;
            }
        }
        return i;
    }

    /**
     * Verify files in the Download Directory contain the specified file (CONTAIN)
     *
     * @param fileName the specified file
     * @return true if file is contain in download directory, else is false
     */
    public static boolean verifyFileContainsInDownloadDirectory(String fileName) {
        boolean flag = false;
        try {
            String pathFolderDownload = getPathDownloadDirectory();
            File dir = new File(pathFolderDownload);
            File[] files = dir.listFiles();
            if (files == null || files.length == 0) {
                flag = false;
            }
            for (int i = 0; i < files.length; i++) {
                if (files[i].getName().contains(fileName)) {
                    flag = true;
                }
            }
            return flag;
        } catch (Exception e) {
            e.getMessage();
            return flag;
        }
    }

    /**
     * Verify files in the Download Directory contain the specified file (EQUALS)
     *
     * @param fileName the specified file
     * @return true if file is contain in download directory, else is false
     */
    public static boolean verifyFileEqualsInDownloadDirectory(String fileName) {
        boolean flag = false;
        try {
            String pathFolderDownload = getPathDownloadDirectory();
            File dir = new File(pathFolderDownload);
            File[] files = dir.listFiles();
            if (files == null || files.length == 0) {
                flag = false;
            }
            for (int i = 0; i < files.length; i++) {
                if (files[i].getName().equals(fileName)) {
                    flag = true;
                }
            }
            return flag;
        } catch (Exception e) {
            e.getMessage();
            return flag;
        }
    }

    /**
     * Verify the file is downloaded (CONTAIN)
     *
     * @param fileName       the specified file
     * @param timeoutSeconds System will wait at most timeout (seconds) to return result
     * @return true if file is downloaded, else is false
     */
    public static boolean verifyDownloadFileContainsName(String fileName, int timeoutSeconds) {
        boolean check = false;
        int i = 0;
        while (i < timeoutSeconds) {
            boolean exist = verifyFileContainsInDownloadDirectory(fileName);
            if (exist == true) {
                i = timeoutSeconds;
                return check = true;
            }
            sleep(1);
            i++;
        }
        return check;
    }

    /**
     * Verify the file is downloaded (EQUALS)
     *
     * @param fileName       the specified file
     * @param timeoutSeconds System will wait at most timeout (seconds) to return result
     * @return true if file is downloaded, else is false
     */
    public static boolean verifyDownloadFileEqualsName(String fileName, int timeoutSeconds) {
        boolean check = false;
        int i = 0;
        while (i < timeoutSeconds) {
            boolean exist = verifyFileEqualsInDownloadDirectory(fileName);
            if (exist == true) {
                i = timeoutSeconds;
                return check = true;
            }
            sleep(1);
            i++;
        }
        return check;
    }

    /**
     * Delete all files in Download Directory
     */
    public static void deleteAllFileInDownloadDirectory() {
        try {
            String pathFolderDownload = getPathDownloadDirectory();
            File file = new File(pathFolderDownload);
            File[] listOfFiles = file.listFiles();
            for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isFile()) {
                    new File(listOfFiles[i].toString()).delete();
                }
            }
        } catch (Exception e) {
            e.getMessage();
        }
    }

    /**
     * Delete all files in Download Directory
     *
     * @param pathDirectory the Download Directory path
     */
    public static void deleteAllFileInDirectory(String pathDirectory) {
        try {
            File file = new File(pathDirectory);
            File[] listOfFiles = file.listFiles();
            for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isFile()) {
                    new File(listOfFiles[i].toString()).delete();
                }
            }
        } catch (Exception e) {
            e.getMessage();
        }
    }

    /**
     * Set window sizes.
     *
     * @param widthPixel  is Width with Pixel
     * @param heightPixel is Height with Pixel
     */
    public static void setWindowSize(int widthPixel, int heightPixel) {
        DriverManager.getDriver().manage().window().setSize(new Dimension(widthPixel, heightPixel));
    }

    /**
     * Move the window to the selected position X, Y from the top left corner 0
     *
     * @param X (int) - horizontal
     * @param Y (int) - vertical
     */
    public static void setWindowPosition(int X, int Y) {
        DriverManager.getDriver().manage().window().setPosition(new Point(X, Y));
    }

    /**
     * Maximize window
     */
    public static void maximizeWindow() {
        DriverManager.getDriver().manage().window().maximize();
    }

    /**
     * Minimize window
     */
    public static void minimizeWindow() {
        DriverManager.getDriver().manage().window().minimize();
    }

    /**
     * Take a screenshot at the element location. Do not capture the entire screen.
     *
     * @param by          is an element of type By
     * @param elementName to name the .png image file
     */
    public static void screenshotElement(By by, String elementName) {
        File scrFile = getWebElement(by).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(scrFile, new File("./" + elementName + ".png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Print the current page in the browser.
     * Note: Only works in headless mode
     *
     * @param endPage is the total number of pages to print. Adjective 1.
     * @return is content of page form PDF file
     */
    public static String printPage(int endPage) {
        PrintOptions printOptions = new PrintOptions();
        //From page 1 to end page
        printOptions.setPageRanges("1-" + endPage);
        Pdf pdf = ((PrintsPage) DriverManager.getDriver()).print(printOptions);
        return pdf.getContent();
    }

    /**
     * Get the JavascriptExecutor object created
     *
     * @return JavascriptExecutor
     */
    public static JavascriptExecutor getJsExecutor() {
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
        return js;
    }

    /**
     * Convert the By object to the WebElement
     *
     * @param by is an element of type By
     * @return Returns a WebElement object
     */
    public static WebElement getWebElement(By by) {
        return DriverManager.getDriver().findElement(by);
    }

    /**
     * Find multiple elements with the locator By object
     *
     * @param by is an element of type By * @return Returns a List of WebElement objects
     */
    public static List<WebElement> getWebElements(By by) {
        return DriverManager.getDriver().findElements(by);
    }

    /**
     * Print out the message in the Console log
     *
     * @param object passes any object
     */
    public static void logConsole(@Nullable Object object) {
        System.out.println(object);
    }

    /**
     * Forced wait with unit of Seconds
     *
     * @param second is a positive integer corresponding to the number of Seconds
     */
    public static void sleep(double second) {
        try {
            if (second > 0.0) {
                LogUtils.pass("Waiting for " + second + " seconds....");
            }
            Thread.sleep((long) (second * 1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Allow browser popup notifications on the website
     *
     * @return the value set Allow - belongs to the ChromeOptions object
     */
    public static ChromeOptions notificationsAllow() {
        // Create a Map to store options
        Map<String, Object> prefs = new HashMap<String, Object>();
        // Add keys and values to Map as follows to disable browser notifications
        // Pass argument 1 to ALLOW and 2 to BLOCK
        prefs.put("profile.default_content_setting_values.notifications", 1);
        // Create a ChromeOptions session
        ChromeOptions options = new ChromeOptions();
        // Use the setExperimentalOption function to execute the value through the above prefs object
        options.setExperimentalOption("prefs", prefs);
        //Returns the set value of the ChromeOptions object
        return options;
    }

    /**
     * Block browser popup notifications on the website
     *
     * @return the value of the setup Block - belongs to the ChromeOptions object
     */
    public static ChromeOptions notificationsBlock() {
        // Create a Map to store options
        Map<String, Object> prefs = new HashMap<String, Object>();
        // Add keys and values to Map as follows to disable browser notifications
        // Pass argument 1 to ALLOW and 2 to BLOCK
        prefs.put("profile.default_content_setting_values.notifications", 2);
        // Create a ChromeOptions session
        ChromeOptions options = new ChromeOptions();
        // Use the setExperimentalOption function to execute the value through the above prefs object
        options.setExperimentalOption("prefs", prefs);
        //Returns the set value of the ChromeOptions object
        return options;
    }

    /**
     * Upload files by dragging the link directly into the input box
     *
     * @param by       passes an element of object type By
     * @param filePath absolute path to the file
     */
    public static void uploadFileWithSendKeys(By by, String filePath) {
        smartWait();
        Objects.requireNonNull(waitForElementPresent(by)).sendKeys(filePath);
        LogUtils.pass("Upload File with SendKeys");
        if (ExtentTestManager.getExtentTest() != null) {
            ExtentReportManager.info("Upload File with SendKeys");
        }
    }

    /**
     * Get current URL from current driver
     *
     * @return the current URL as String
     */
    public static String getCurrentUrl() {
        smartWait();
        LogUtils.pass("Get Current URL: " + DriverManager.getDriver().getCurrentUrl());
        if (ExtentTestManager.getExtentTest() != null) {
            ExtentReportManager.info("Get Current URL: " + DriverManager.getDriver().getCurrentUrl());
        }
        return DriverManager.getDriver().getCurrentUrl();
    }

    /**
     * Get current web page's title
     *
     * @return the current URL as String
     */
    public static String getPageTitle() {
        smartWait();
        String title = DriverManager.getDriver().getTitle();
        LogUtils.pass("Get Page Title: " + DriverManager.getDriver().getTitle());
        if (ExtentTestManager.getExtentTest() != null) {
            ExtentReportManager.info("Get Page Title: " + DriverManager.getDriver().getTitle());
        }
        return title;
    }

    /**
     * Verify the web page's title equals with the specified title
     *
     * @param pageTitle The title of the web page that needs verifying
     * @return true/false
     */
    public static boolean verifyPageTitle(String pageTitle) {
        smartWait();
        boolean result = getPageTitle().equals(pageTitle);
        if (result) {
            ExtentReportManager.info("Page title: " + getPageTitle() + " meets expected " + pageTitle);
            LogUtils.info("Page title: " + getPageTitle() + " meets expected " + pageTitle);
            return true;
        } else {
            ExtentReportManager.info("Page title: " + getPageTitle() + " doesn't meet expected " + pageTitle);
            Assert.fail("Page title: " + getPageTitle() + " doesn't meet expected " + pageTitle);
            return false;
        }
    }

    /**
     * Verify the web page's url equals with the specified url
     *
     * @param url The title of the web page that needs verifying
     * @return true/false
     */
    public static boolean verifyPageURLEqual(String url) {
        smartWait();
        boolean result = getCurrentUrl().equalsIgnoreCase(url);
        if (result) {
            ExtentReportManager.pass("Current page URL: " + getCurrentUrl() + " meets expected " + url);
            LogUtils.pass("Current page URL: " + getCurrentUrl() + " meets expected " + url);
            return true;
        } else {
            LogUtils.warn("Current page URL: " + getCurrentUrl() + " doesn't meets expected " + url);
            Assert.assertTrue(false, "Current page URL: " + getCurrentUrl() + " doesn't meets expected " + url);
            return false;
        }
    }

    /**
     * Verify if the given text presents anywhere in the page source.
     */
    public static boolean verifyPageContainsText(String text) {
        smartWait();
        return DriverManager.getDriver().getPageSource().contains(text);
    }

    /**
     * Verify if the given web element is checked.
     *
     * @param by represent a web element as the By object
     * @return true if the element is checked, otherwise false.
     */
    public static boolean verifyElementChecked(By by) {
        smartWait();
        boolean checked = getWebElement(by).isSelected();
        if (checked) {
            return true;
        } else {
            Assert.assertTrue(false, "The element NOT checked.");
            return false;
        }
    }

    /**
     * Verify if all given web elements are checked.
     *
     * @param by represent a group of web elements as the By object
     * @return true if all the element are checked, otherwise false.
     */
    public static boolean verifyElementsChecked(By by) {
        smartWait();
        boolean result = false;
        for (WebElement elem : getWebElements(by)) {
            boolean checked = getWebElement(by).isSelected();
            if (checked) {
                result = true;
            } else {
                Assert.assertTrue(false, "The element NOT checked.");
                result = false;
            }
        }
        return result;
    }

    /**
     * Verify if the given web element is checked.
     *
     * @param by      represent a web element as the By object
     * @param message the custom message if false
     * @return true if the element is checked, otherwise false.
     */
    public static boolean verifyElementChecked(By by, String message) {
        smartWait();
        waitForElementVisible(by);
        boolean checked = getWebElement(by).isSelected();
        if (checked) {
            return true;
        } else {
            Assert.assertTrue(false, message);
            return false;
        }
    }

    //Handle dropdown

    /**
     * Select value in dropdown dynamic (not pure Select Option)
     *
     * @param objectListItem is the locator of the list item as a By object
     * @param text           the value to select as Text of the item
     * @return click to select a specified item with Text value
     */
    public static boolean selectOptionDynamic(By objectListItem, String text) {
        smartWait();
        //For dynamic dropdowns (div, li, span,...not select options)
        try {
            List<WebElement> elements = getWebElements(objectListItem);
            for (WebElement element : elements) {
                scrollToElementAtTop(element);
                LogUtils.pass("Option=" + element.getText());
                if (element.getText().toLowerCase().trim().contains(text.toLowerCase().trim())) {
                    element.click();
                    sleep(1);
                    return true;
                }
            }
        } catch (Exception e) {
            LogUtils.fail(e.getMessage());
            e.getMessage();
        }
        return false;
    }

    /**
     * Select value in dropdown dynamic (not pure Select Option)
     *
     * @param objectListItem is the locator of the list item as a By object
     * @param index          the index that you want to select, starting ith 0
     * @return click to select a specified item with Text value
     */
    public static boolean selectOptionDynamic(By objectListItem, int index) {
        try {
            List<WebElement> elements = getWebElements(objectListItem);
            return selectOptionDynamic(objectListItem, elements.get(index).getText());
        } catch (Exception e) {
            LogUtils.fail(e.getMessage());
            e.getMessage();
        }
        return false;
    }

    /**
     * Verify All Options contains the specified text (select option)
     *
     * @param by   represent a web element as the By object
     * @param text the specified text
     * @return true if all option contains the specified text
     */
    public static boolean verifyOptionDynamicExist(By by, String text) {
        smartWait();
        try {
            List<WebElement> elements = getWebElements(by);
            for (WebElement element : elements) {
                LogUtils.pass(element.getText());
                if (element.getText().toLowerCase().trim().contains(text.toLowerCase().trim())) {
                    return true;
                }
            }
        } catch (Exception e) {
            LogUtils.fail(e.getMessage());
            e.getMessage();
        }
        return false;
    }

    /**
     * Get total number of options the given web element has (select option)
     *
     * @param objectListItem represent a web element as the By object
     * @return total number of options
     */
    public static int getOptionDynamicTotal(By objectListItem) {
        smartWait();
        LogUtils.info("Get total of Option Dynamic with list element. " + objectListItem);
        try {
            List<WebElement> elements = getWebElements(objectListItem);
            return elements.size();
        } catch (Exception e) {
            LogUtils.fail(e.getMessage());
            e.getMessage();
        }
        return 0;
    }

    /**
     * Select the options with the given label (displayed text).
     *
     * @param by   represent a web element as the By object
     * @param text the specified text of option
     */
    public static void selectOptionByText(By by, String text) {
        smartWait();
        Select select = new Select(getWebElement(by));
        select.selectByVisibleText(text);
        LogUtils.pass("Select Option " + by + "by text " + text);
    }

    /**
     * Select the options with the given value.
     *
     * @param by    represent a web element as the By object
     * @param value the specified value of option
     */
    public static void selectOptionByValue(By by, String value) {
        smartWait();
        Select select = new Select(getWebElement(by));
        select.selectByValue(value);
        LogUtils.pass("Select Option " + by + "by value " + value);
    }

    /**
     * Select the options with the given index.
     *
     * @param by    represent a web element as the By object
     * @param index the specified index of option
     */
    public static void selectOptionByIndex(By by, int index) {
        smartWait();
        Select select = new Select(getWebElement(by));
        select.selectByIndex(index);
        LogUtils.pass("Select Option " + by + "by index " + index);
    }

    /**
     * Verify the number of options equals the specified total
     *
     * @param by    represent a web element as the By object
     * @param total the specified options total
     */
    public static void verifyOptionTotal(By by, int total) {
        smartWait();
        Select select = new Select(getWebElement(by));
        LogUtils.pass("Verify Option Total equals: " + total);
        Assert.assertEquals(total, select.getOptions().size());
    }

    /**
     * Verify if the options at the given text are selected.
     *
     * @param by   represent a web element as the By object
     * @param text the specified options text
     * @return true if options given selected, else is false
     */
    public static boolean verifySelectedByText(By by, String text) {
        smartWait();
        Select select = new Select(getWebElement(by));
        LogUtils.pass("Verify Option Selected by text: " + select.getFirstSelectedOption().getText());
        if (select.getFirstSelectedOption().getText().equals(text)) {
            return true;
        } else {
            Assert.assertEquals(select.getFirstSelectedOption().getText(), text, "The option NOT selected. " + by);
            return false;
        }
    }

    /**
     * Verify if the options at the given value are selected.
     *
     * @param by    represent a web element as the By object
     * @param value the specified options value
     * @return true if options given selected, else is false
     */
    public static boolean verifySelectedByValue(By by, String value) {
        smartWait();
        Select select = new Select(getWebElement(by));
        LogUtils.pass("Verify Option Selected by value: " + select.getFirstSelectedOption().getAttribute("value"));
        if (select.getFirstSelectedOption().getAttribute("value").equals(value)) {
            return true;
        } else {
            Assert.assertEquals(select.getFirstSelectedOption().getAttribute("value"), value, "The option NOT selected. " + by);
            return false;
        }
    }

    /**
     * Verify if the options at the given index are selected.
     *
     * @param by    represent a web element as the By object
     * @param index the specified options index
     * @return true if options given selected, else is false
     */
    public static boolean verifySelectedByIndex(By by, int index) {
        smartWait();
        Select select = new Select(getWebElement(by));
        int indexFirstOption = select.getOptions().indexOf(select.getFirstSelectedOption());
        LogUtils.pass("The First Option selected by index: " + indexFirstOption);
        LogUtils.pass("Expected index: " + index);
        if (indexFirstOption == index) {
            return true;
        } else {
            Assert.assertTrue(false, "The option NOT selected. " + by);
            return false;
        }
    }

    /**
     * Switch to iframe by index of iframe tag
     *
     * @param index index of iframe tag
     */
    public static void switchToFrameByIndex(int index) {
        smartWait();
        WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(FrameworkConstants.WAIT_EXPLICIT), Duration.ofMillis(500));
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(index));
        LogUtils.pass("Switch to Frame by Index. " + index);
    }

    public static void waitForFileToDownload(String filePath) {
        File file = new File(filePath);
        FluentWait<WebDriver> wait = new FluentWait<WebDriver>(DriverManager.getDriver()).withTimeout(Duration.ofSeconds(FrameworkConstants.WAIT_IMPLICIT)).pollingEvery(Duration.ofMillis(500));
        wait.until(x -> file.exists());
        LogUtils.pass("File exists at " + filePath);
    }

    /**
     * Switch to iframe by ID or Name of iframe tag
     *
     * @param IdOrName ID or Name of iframe tag
     */
    public static void switchToFrameByIdOrName(String IdOrName) {
        smartWait();
        WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(FrameworkConstants.WAIT_EXPLICIT), Duration.ofMillis(500));
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(IdOrName));
        LogUtils.pass("Switch to Frame by ID or Name. " + IdOrName);
    }

    /**
     * Switch to iframe by Element is this iframe tag
     *
     * @param by iframe tag
     */
    public static void switchToFrameByElement(By by) {
        smartWait();
        WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(FrameworkConstants.WAIT_EXPLICIT), Duration.ofMillis(500));
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(by));
        LogUtils.pass("Switch to Frame by Element. " + by);
    }

    /**
     * Switch to Default Content
     */
    public static void switchToDefaultContent() {
        smartWait();
        DriverManager.getDriver().switchTo().defaultContent();
        LogUtils.pass("Switch to Default Content");
    }

    /**
     * Switch to iframe by position of iframe tag
     *
     * @param position index of iframe tag
     */
    public static void switchToWindowOrTabByPosition(int position) {
        smartWait();
        DriverManager.getDriver().switchTo().window(DriverManager.getDriver().getWindowHandles().toArray()[position].toString());
        LogUtils.pass("Switch to Window or Tab by Position: " + position);
    }

    /**
     * Switch to popup window by title
     *
     * @param title title of popup window
     */
    public static void switchToWindowOrTabByTitle(String title) {
        smartWait();
        //Store the ID of the original window
        String originalWindow = DriverManager.getDriver().getWindowHandle();
        WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(FrameworkConstants.WAIT_EXPLICIT), Duration.ofMillis(500));
        //Wait for the new window or tab
        wait.until(ExpectedConditions.numberOfWindowsToBe(2));
        //Loop through until we find a new window handle
        for (String windowHandle : DriverManager.getDriver().getWindowHandles()) {
            if (!originalWindow.contentEquals(windowHandle)) {
                DriverManager.getDriver().switchTo().window(windowHandle);
                if (DriverManager.getDriver().getTitle().equals(title)) {
                    break;
                }
            }
        }
    }

    /**
     * Switch to popup window by URL
     *
     * @param url url of popup window
     */
    public static void switchToWindowOrTabByUrl(String url) {
        smartWait();
        //Store the ID of the original window
        String originalWindow = DriverManager.getDriver().getWindowHandle();
        WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(FrameworkConstants.WAIT_EXPLICIT), Duration.ofMillis(500));
        //Wait for the new window or tab//
        wait.until(ExpectedConditions.numberOfWindowsToBe(2));
        //Loop through until we find a new window handle
        for (String windowHandle : DriverManager.getDriver().getWindowHandles()) {
            LogUtils.pass("Current Handle - ." + windowHandle);
            if (!originalWindow.contentEquals(windowHandle)) {
                DriverManager.getDriver().switchTo().window(windowHandle);
                if (DriverManager.getDriver().getCurrentUrl().contains(url)) {
                    LogUtils.pass("Switched to current Window." + DriverManager.getDriver().getCurrentUrl());
                    break;
                }
            }
        }
    }

    /**
     * Close current Window
     */
    public static void closeCurrentWindow() {
        LogUtils.pass("Close current Window." + getCurrentUrl());
        DriverManager.getDriver().close();
        LogUtils.pass("Close current Window");
    }

    /**
     * Get the total number of popup windows the given web page.
     *
     * @param number the specified number
     * @return true/false
     */
    public static boolean verifyTotalOfWindowsOrTab(int number) {
        return new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(FrameworkConstants.WAIT_EXPLICIT)).until(ExpectedConditions.numberOfWindowsToBe(number));
    }

    /**
     * Open new Tab
     */
    public static void openNewTab() {
        smartWait();
        // Opens a new tab and switches to new tab
        DriverManager.getDriver().switchTo().newWindow(WindowType.TAB);
        LogUtils.pass("Open new Tab");
    }

    /**
     * Open new Window
     */
    public static void openNewWindow() {
        smartWait();
        // Opens a new window and switches to new window
        DriverManager.getDriver().switchTo().newWindow(WindowType.WINDOW);
        LogUtils.pass("Open new Window");
    }

    /**
     * Switch to Main Window
     */
    public static void switchToMainWindow() {
        smartWait();
        DriverManager.getDriver().switchTo().window(DriverManager.getDriver().getWindowHandles().toArray()[0].toString());
        LogUtils.pass("Switch to Main Window." + DriverManager.getDriver());
    }

    /**
     * Switch to Main Window by ID
     *
     * @param originalWindow ID of main window
     */
    public static void switchToMainWindow(String originalWindow) {
        smartWait();
        DriverManager.getDriver().switchTo().window(originalWindow);
        LogUtils.pass("Switch to Main Window." + originalWindow);
    }

    /**
     * Switch to Last Window
     */
    public static void switchToLastWindow() {
        smartWait();
        Set<String> windowHandles = DriverManager.getDriver().getWindowHandles();
        WebDriver newDriver = DriverManager.getDriver().switchTo().window(DriverManager.getDriver().getWindowHandles().toArray()[windowHandles.size() - 1].toString());
        LogUtils.pass("Switch to Last Window." + newDriver.getCurrentUrl());
    }

    public static void switchToPreviousWindow() {
        smartWait();
        Set<String> windowHandles = DriverManager.getDriver().getWindowHandles();
        WebDriver newDriver = DriverManager.getDriver().switchTo().window(DriverManager.getDriver().getWindowHandles().toArray()[windowHandles.size() - 2].toString());
        LogUtils.pass("Switch to Last Window." + newDriver.getCurrentUrl());
    }

    /**
     * Get current count of windows
     */
    public static int getCurrentCountOfWindow() {
        smartWait();
        Set<String> windowHandles = DriverManager.getDriver().getWindowHandles();
        return windowHandles.size();
    }

    /**
     * Click accept on alert
     */
    public static void acceptAlert() {
        sleep(FrameworkConstants.WAIT_SLEEP_STEP);
        DriverManager.getDriver().switchTo().alert().accept();
        LogUtils.pass("Click Accept on Alert.");
    }

    /**
     * Click dismiss on alert
     */
    public static void dismissAlert() {
        sleep(FrameworkConstants.WAIT_SLEEP_STEP);
        DriverManager.getDriver().switchTo().alert().dismiss();
        LogUtils.pass("Click Dismiss on Alert.");
    }

    /**
     * Get text on alert
     */
    public static String getTextAlert() {
        sleep(FrameworkConstants.WAIT_SLEEP_STEP);
        LogUtils.pass("Get text ion alert: " + DriverManager.getDriver().switchTo().alert().getText());
        return DriverManager.getDriver().switchTo().alert().getText();
    }

    /**
     * Set text on alert
     */
    public static void setTextAlert(String text) {
        sleep(FrameworkConstants.WAIT_SLEEP_STEP);
        DriverManager.getDriver().switchTo().alert().sendKeys(text);
        LogUtils.pass("Set " + text + " on Alert.");
    }

    /**
     * Verify if alert does present
     *
     * @param timeOut Timeout waiting for alert to present.(in seconds)
     * @return true/false
     */
    public static boolean verifyAlertPresent(int timeOut) {
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(timeOut), Duration.ofMillis(500));
            wait.until(ExpectedConditions.alertIsPresent());
            return true;
        } catch (Throwable error) {
            LogUtils.fail("Alert NOT Present.");
            Assert.fail("Alert NOT Present.");
            return false;
        }
    }

    /**
     * Get list text of specified elements
     *
     * @param by represent a web element as the By object
     * @return Text list of specified elements
     */
    public static List<String> getListElementsText(By by) {
        smartWait();
        WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(FrameworkConstants.WAIT_EXPLICIT), Duration.ofMillis(500));
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        List<WebElement> listElement = getWebElements(by);
        List<String> listText = new ArrayList<>();
        for (WebElement e : listElement) {
            listText.add(e.getText());
        }
        return listText;
    }

    /**
     * Verify if a web element is present (findElements.size > 0).
     *
     * @param by represent a web element as the By object
     * @return true/false
     */
    public static boolean verifyElementExists(By by) {
        smartWait();
        boolean res;
        List<WebElement> elementList = getWebElements(by);
        if (elementList.size() > 0) {
            res = true;
            LogUtils.pass("Element existing");
        } else {
            res = false;
            LogUtils.warn("Element not exists");
        }
        return res;
    }

    /**
     * Verify if two object are equal.
     *
     * @param value1 The object one
     * @param value2 The object two
     * @return true/false
     */
    public static boolean verifyEquals(Object value1, Object value2) {
        boolean result = value1.equals(value2);
        if (result) {
            LogUtils.pass("Verify Equals: " + value1 + " = " + value2);
            if (ExtentTestManager.getExtentTest() != null) {
                ExtentReportManager.pass("Verify Equals: " + value1 + " = " + value2);
            }
        } else {
            LogUtils.pass("Verify Equals: " + value1 + " != " + value2);
            if (ExtentTestManager.getExtentTest() != null) {
                ExtentReportManager.fail("Verify Equals: " + value1 + " != " + value2);
            }
            Assert.assertEquals(value1, value2, value1 + " != " + value2);
        }
        return result;
    }

    /**
     * Verify if two object are equal.
     *
     * @param value1  The object one
     * @param value2  The object two
     * @param message The custom message if false
     * @return true/false
     */
    public static boolean verifyEquals(Object value1, Object value2, String message) {
        boolean result = value1.equals(value2);
        if (result) {
            LogUtils.pass("Verify Equals: " + value1 + " = " + value2);
            if (ExtentTestManager.getExtentTest() != null) {
                ExtentReportManager.pass("Verify Equals: " + value1 + " = " + value2);
            }
        } else {
            LogUtils.pass("Verify Equals: " + value1 + " != " + value2);
            if (ExtentTestManager.getExtentTest() != null) {
                ExtentReportManager.fail("Verify Equals: " + value1 + " != " + value2);
            }
            Assert.assertEquals(value1, value2, message);
        }
        return result;
    }

    /**
     * Verify if the first object contains the second object.
     *
     * @param value1 The first object
     * @param value2 The second object
     * @return true/false
     */
    public static boolean verifyContains(String value1, String value2) {
        boolean result = value1.contains(value2);
        if (result) {
            LogUtils.pass("Verify Equals: " + value1 + " CONTAINS " + value2);
            if (ExtentTestManager.getExtentTest() != null) {
                ExtentReportManager.pass("Verify Contains: " + value1 + " CONTAINS " + value2);
            }
        } else {
            LogUtils.pass("Verify Contains: " + value1 + " NOT CONTAINS " + value2);
            if (ExtentTestManager.getExtentTest() != null) {
                ExtentReportManager.fail("Verify Contains: " + value1 + " NOT CONTAINS " + value2);
            }
            Assert.assertEquals(value1, value2, value1 + " NOT CONTAINS " + value2);
        }
        return result;
    }

    /**
     * Verify if the first object contains the second object.
     *
     * @param value1  The first object
     * @param value2  The second object
     * @param message The custom message if false
     * @return true/false
     */
    public static boolean verifyContains(String value1, String value2, String message) {
        boolean result = value1.contains(value2);
        if (result) {
            LogUtils.pass("Verify Equals: " + value1 + " CONTAINS " + value2);
            if (ExtentTestManager.getExtentTest() != null) {
                ExtentReportManager.pass("Verify Contains: " + value1 + " CONTAINS " + value2);
            }
        } else {
            LogUtils.pass("Verify Contains: " + value1 + " NOT CONTAINS " + value2);
            if (ExtentTestManager.getExtentTest() != null) {
                ExtentReportManager.fail("Verify Contains: " + value1 + " NOT CONTAINS " + value2);
            }
            Assert.assertEquals(value1, value2, message);
        }
        return result;
    }

    /**
     * Verify the condition is true.
     *
     * @return true/false
     */
    public static boolean verifyTrue(Boolean condition) {
        if (condition) {
            LogUtils.pass("Verify TRUE: " + condition);
            if (ExtentTestManager.getExtentTest() != null) {
                ExtentReportManager.pass("Verify TRUE: " + condition);
            }
        } else {
            LogUtils.pass("Verify TRUE: " + condition);
            if (ExtentTestManager.getExtentTest() != null) {
                ExtentReportManager.fail("Verify TRUE: " + condition);
            }
            Assert.assertTrue(condition, "The condition is FALSE.");
        }
        return condition;
    }

    /**
     * Verify the condition is true.
     *
     * @param message the custom message if false
     * @return true/false
     */
    public static boolean verifyTrue(Boolean condition, String message) {
        if (condition) {
            LogUtils.pass("Verify TRUE: " + condition);
            if (ExtentTestManager.getExtentTest() != null) {
                ExtentReportManager.pass("Verify TRUE: " + condition);
            }
        } else {
            LogUtils.pass("Verify TRUE: " + condition);
            if (ExtentTestManager.getExtentTest() != null) {
                ExtentReportManager.fail("Verify TRUE: " + condition);
            }
            Assert.assertTrue(condition, message);
        }
        return condition;
    }

    /**
     * Verify text of an element. (equals)
     *
     * @param by   represent a web element as the By object
     * @param text Text of the element to verify.
     * @return true if the element has the desired text, otherwise false.
     */
    public static boolean verifyElementText(By by, String text) {
        smartWait();
        waitForElementVisible(by);
        return getTextElement(by).trim().equals(text.trim());
    }

    /**
     * Verify text of an element. (equals)
     *
     * @param by          represent a web element as the By object
     * @param text        text of the element to verify.
     * @param flowControl specify failure handling schema (STOP_ON_FAILURE, CONTINUE_ON_FAILURE, OPTIONAL) to determine whether the execution should be allowed to continue or stop
     * @return true if the element has the desired text, otherwise false.
     */
    public static boolean verifyElementTextEquals(By by, String text, FailureHandling flowControl) {
        smartWait();
        waitForElementVisible(by);
        boolean result = getTextElement(by).trim().equals(text.trim());
        if (result) {
            LogUtils.pass("Verify text of an element [Equals]: " + result);
        } else {
            LogUtils.pass("Verify text of an element [Equals]: " + result);
        }
        if (flowControl.equals(FailureHandling.STOP_ON_FAILURE)) {
            Assert.assertEquals(getTextElement(by).trim(), text.trim(), "The actual text is '" + getTextElement(by).trim() + "' not equals '" + text.trim() + "'");
        }
        if (flowControl.equals(FailureHandling.CONTINUE_ON_FAILURE)) {
            softAssert.assertEquals(getTextElement(by).trim(), text.trim(), "The actual text is '" + getTextElement(by).trim() + "' not equals '" + text.trim() + "'");
            if (!result) {
                ExtentReportManager.fail("The actual text is '" + getTextElement(by).trim() + "' not equals '" + text.trim() + "'");
            }
        }
        if (flowControl.equals(FailureHandling.OPTIONAL)) {
            if (ExtentTestManager.getExtentTest() != null) {
                ExtentReportManager.warning("Verify text of an element [Equals] - " + result);
                ExtentReportManager.warning("The actual text is '" + getTextElement(by).trim() + "' not equals expected text '" + text.trim() + "'");
            }
        }
        addScreenshotToReport(Thread.currentThread().getStackTrace()[1].getMethodName() + "_" + DateUtils.getCurrentDateTime());
        return getTextElement(by).trim().equals(text.trim());
    }

    /**
     * Verify text of an element. (equals)
     *
     * @param by   represent a web element as the By object
     * @param text Text of the element to verify.
     * @return true if the element has the desired text, otherwise false.
     */
    public static boolean verifyElementTextEquals(By by, String text) {
        smartWait();
        waitForElementVisible(by);
        boolean result = getTextElement(by).trim().equals(text.trim());
        if (result) {
            LogUtils.pass("The actual text: " + getTextElement(by) + " meets expected " + text);
            ExtentReportManager.pass("The actual text: " + getTextElement(by) + " meets expected " + text);
        } else {
            Assert.assertEquals(getTextElement(by).trim(), text.trim(), "The actual text is '" + getTextElement(by).trim() + "' not equals '" + text.trim() + "'");
            if (ExtentTestManager.getExtentTest() != null) {
                ExtentReportManager.warning("The actual text is '" + getTextElement(by).trim() + "' not equals '" + text.trim() + "'");
                addScreenshotToReport(Thread.currentThread().getStackTrace()[1].getMethodName() + "_" + DateUtils.getCurrentDateTime());
            }
        }
        return result;
    }

    /**
     * Verify text of an element. (contains)
     *
     * @param by          represent a web element as the By object
     * @param text        text of the element to verify.
     * @param flowControl specify failure handling schema (STOP_ON_FAILURE, CONTINUE_ON_FAILURE, OPTIONAL) to determine whether the execution should be allowed to continue or stop
     * @return true if the element has the desired text, otherwise false.
     */
    public static boolean verifyElementTextContains(By by, String text, FailureHandling flowControl) {
        smartWait();
        waitForElementVisible(by);
        boolean result = getTextElement(by).trim().contains(text.trim());
        if (result) {
            LogUtils.pass("Verify text of an element [Contains]: " + result);
        } else {
            LogUtils.pass("Verify text of an element [Contains]: " + result);
        }
        if (flowControl.equals(FailureHandling.STOP_ON_FAILURE)) {
            Assert.assertTrue(result, "The actual text is " + getTextElement(by).trim() + " not contains " + text.trim());
        }
        if (flowControl.equals(FailureHandling.CONTINUE_ON_FAILURE)) {
            softAssert.assertTrue(result, "The actual text is " + getTextElement(by).trim() + " not contains " + text.trim());
        }
        if (flowControl.equals(FailureHandling.OPTIONAL)) {
            if (ExtentTestManager.getExtentTest() != null) {
                ExtentReportManager.warning("Verify text of an element [Contains] - " + result);
            }
        }
        addScreenshotToReport(Thread.currentThread().getStackTrace()[1].getMethodName() + "_" + DateUtils.getCurrentDateTime());
        return getTextElement(by).trim().contains(text.trim());
    }

    /**
     * Verify text of an element. (contains)
     *
     * @param by   represent a web element as the By object
     * @param text Text of the element to verify.
     * @return true if the element has the desired text, otherwise false.
     */
    public static boolean verifyElementTextContains(By by, String text) {
        smartWait();
        waitForElementVisible(by);
        boolean result = getTextElement(by).trim().contains(text.trim());
        if (result) {
            LogUtils.pass("Verify text of an element [Contains]: " + result);
        } else {
            LogUtils.pass("Verify text of an element [Contains]: " + result);
        }
        Assert.assertTrue(result, "The actual text is " + getTextElement(by).trim() + " not contains " + text.trim());
        if (ExtentTestManager.getExtentTest() != null) {
            ExtentReportManager.info("Verify text of an element [Contains] : " + result);
        }
        addScreenshotToReport(Thread.currentThread().getStackTrace()[1].getMethodName() + "_" + DateUtils.getCurrentDateTime());
        return result;
    }

    /**
     * Verify if the given element is clickable.
     *
     * @param by represent a web element as the By object
     * @return true/false
     */
    public static boolean verifyElementClickable(By by) {
        smartWait();
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(FrameworkConstants.WAIT_EXPLICIT), Duration.ofMillis(500));
            wait.until(ExpectedConditions.elementToBeClickable(by));
            LogUtils.pass("Verify element clickable " + by);
            if (ExtentTestManager.getExtentTest() != null) {
                ExtentReportManager.info("Verify element clickable " + by);
            }
            return true;
        } catch (Exception e) {
            LogUtils.fail(e.getMessage());
            Assert.fail("FAILED. Element not clickable " + by);
            return false;
        }
    }

    /**
     * Verify if the given element is clickable. (in seconds)
     *
     * @param by      represent a web element as the By object
     * @param timeout System will wait at most timeout (seconds) to return result
     * @return true/false
     */
    public static boolean verifyElementClickable(By by, int timeout) {
        smartWait();
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(timeout), Duration.ofMillis(500));
            wait.until(ExpectedConditions.elementToBeClickable(by));
            LogUtils.pass("Verify element clickable " + by);
            if (ExtentTestManager.getExtentTest() != null) {
                ExtentReportManager.info("Verify element clickable " + by);
            }
            return true;
        } catch (Exception e) {
            LogUtils.fail("FAILED. Element not clickable " + by);
            LogUtils.fail(e.getMessage());
            Assert.fail("FAILED. Element not clickable " + by);
            return false;
        }
    }

    /**
     * Verify if the given element is clickable. (in seconds)
     *
     * @param by      represent a web element as the By object
     * @param timeout System will wait at most timeout (seconds) to return result
     * @param message the custom message if false
     * @return true/false
     */
    public static boolean verifyElementClickable(By by, int timeout, String message) {
        smartWait();
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(timeout), Duration.ofMillis(500));
            wait.until(ExpectedConditions.elementToBeClickable(by));
            LogUtils.pass("Verify element clickable " + by);
            if (ExtentTestManager.getExtentTest() != null) {
                ExtentReportManager.info("Verify element clickable " + by);
            }
            return true;
        } catch (Exception e) {
            LogUtils.fail(message);
            LogUtils.error(e.getMessage());
            Assert.fail(message + "" + e.getMessage());
            return false;
        }
    }

    /**
     * Verify if the given web element does present on DOM.
     *
     * @param by represent a web element as the By object
     * @return true/false
     */
    public static boolean verifyElementPresent(By by) {
        smartWait();
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(FrameworkConstants.WAIT_EXPLICIT), Duration.ofMillis(500));
            wait.until(ExpectedConditions.presenceOfElementLocated(by));
            LogUtils.pass("Verify element present " + by);
            if (ExtentTestManager.getExtentTest() != null) {
                ExtentReportManager.info("Verify element present " + by);
            }
            addScreenshotToReport(Thread.currentThread().getStackTrace()[1].getMethodName() + "_" + DateUtils.getCurrentDateTime());
            return true;
        } catch (Exception e) {
            LogUtils.fail("The element does NOT present. " + e.getMessage());
            Assert.fail("The element does NOT present. " + e.getMessage());
            return false;
        }
    }

    /**
     * Verify if the given web element does present on DOM. (in seconds)
     *
     * @param by      represent a web element as the By object
     * @param timeout System will wait at most timeout (seconds) to return result
     * @return true/false
     */
    public static boolean verifyElementPresent(By by, int timeout) {
        smartWait();
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(timeout));
            wait.until(ExpectedConditions.presenceOfElementLocated(by));
            LogUtils.pass("Verify element present " + by);
            if (ExtentTestManager.getExtentTest() != null) {
                ExtentReportManager.info("Verify element present " + by);
            }
            addScreenshotToReport(Thread.currentThread().getStackTrace()[1].getMethodName() + "_" + DateUtils.getCurrentDateTime());
            return true;
        } catch (Exception e) {
            LogUtils.fail("The element does NOT present. " + e.getMessage());
            Assert.fail("The element does NOT present. " + e.getMessage());
            return false;
        }
    }

    /**
     * Verify if the given web element does present on DOM.
     *
     * @param by      represent a web element as the By object
     * @param message the custom message if false
     * @return true/false
     */
    public static boolean verifyElementPresent(By by, String message) {
        smartWait();
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(FrameworkConstants.WAIT_EXPLICIT), Duration.ofMillis(500));
            wait.until(ExpectedConditions.presenceOfElementLocated(by));
            LogUtils.info("Verify element present " + by);
            if (ExtentTestManager.getExtentTest() != null) {
                ExtentReportManager.info("Verify element present " + by);
            }
            addScreenshotToReport(Thread.currentThread().getStackTrace()[1].getMethodName() + "_" + DateUtils.getCurrentDateTime());
            return true;
        } catch (Exception e) {
            if (message.isEmpty() || message == null) {
                LogUtils.error("The element does NOT present. " + e.getMessage());
                Assert.fail("The element does NOT present. " + e.getMessage());
            } else {
                LogUtils.error(message);
                Assert.fail(message);
            }
            return false;
        }
    }

    /**
     * Verify if the given web element does present on DOM. (in seconds)
     *
     * @param by      represent a web element as the By object
     * @param timeout system will wait at most timeout (seconds) to return result
     * @param message the custom message if false
     * @return true/false
     */
    public static boolean verifyElementPresent(By by, int timeout, String message) {
        smartWait();
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(timeout));
            wait.until(ExpectedConditions.presenceOfElementLocated(by));
            LogUtils.pass("Verify element present " + by);
            if (ExtentTestManager.getExtentTest() != null) {
                ExtentReportManager.info("Verify element present " + by);
            }
            addScreenshotToReport(Thread.currentThread().getStackTrace()[1].getMethodName() + "_" + DateUtils.getCurrentDateTime());
            return true;
        } catch (Exception e) {
            if (message.isEmpty() || message == null) {
                LogUtils.error("The element does NOT present. " + e.getMessage());
                Assert.fail("The element does NOT present. " + e.getMessage());
            } else {
                LogUtils.error(message);
                Assert.fail(message);
            }
            return false;
        }
    }

    /**
     * Verify if the given web element does NOT present on the DOM.
     *
     * @param by represent a web element as the By object
     * @return true/false
     */
    public static boolean verifyElementNotPresent(By by) {
        smartWait();
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(FrameworkConstants.WAIT_EXPLICIT), Duration.ofMillis(500));
            wait.until(ExpectedConditions.presenceOfElementLocated(by));
            LogUtils.fail("The element presents. " + by);
            Assert.fail("The element presents. " + by);
            return false;
        } catch (Exception e) {
            return true;
        }
    }

    /**
     * Verify if the given web element does NOT present on the DOM. (in seconds)
     *
     * @param by      represent a web element as the By object
     * @param timeout system will wait at most timeout (seconds) to return result
     * @return true/false
     */
    public static boolean verifyElementNotPresent(By by, int timeout) {
        smartWait();
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(timeout));
            wait.until(ExpectedConditions.presenceOfElementLocated(by));
            LogUtils.fail("Element is present " + by);
            Assert.fail("The element presents. " + by);
            return false;
        } catch (Exception e) {
            return true;
        }
    }

    /**
     * Verify if the given web element does NOT present on the DOM.
     *
     * @param by      represent a web element as the By object
     * @param message the custom message if false
     * @return true/false
     */
    public static boolean verifyElementNotPresent(By by, String message) {
        smartWait();
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(FrameworkConstants.WAIT_EXPLICIT), Duration.ofMillis(500));
            wait.until(ExpectedConditions.presenceOfElementLocated(by));
            if (message.isEmpty() || message == null) {
                LogUtils.fail("The element presents. " + by);
                Assert.fail("The element presents. " + by);
            } else {
                LogUtils.fail(message);
                Assert.fail(message + " " + by);
            }
            return false;
        } catch (Exception e) {
            return true;
        }
    }

    /**
     * Verify if the given web element does NOT present on the DOM. (in seconds)
     *
     * @param by      represent a web element as the By object
     * @param timeout system will wait at most timeout (seconds) to return result
     * @param message the custom message if false
     * @return true/false
     */
    public static boolean verifyElementNotPresent(By by, int timeout, String message) {
        smartWait();
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(timeout));
            wait.until(ExpectedConditions.presenceOfElementLocated(by));
            if (message.isEmpty() || message == null) {
                LogUtils.error("The element presents. " + by);
                Assert.fail("The element presents. " + by);
            } else {
                LogUtils.error(message + by);
                Assert.fail(message + by);
            }
            return false;
        } catch (Exception e) {
            return true;
        }
    }

    /**
     * Verify element is visible. (in seconds)
     *
     * @param by      represent a web element as the By object
     * @param timeout system will wait at most timeout (seconds) to return result
     * @return true/false
     */
    public static boolean isElementVisible(By by, int timeout) {
        smartWait();
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(timeout));
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
            LogUtils.info("Verify element visible " + by);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Verify if the given web element is visible.
     *
     * @param by represent a web element as the By object
     * @return true/false
     */
    public static boolean verifyElementVisible(By by) {
        smartWait();
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(FrameworkConstants.WAIT_EXPLICIT), Duration.ofMillis(500));
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
            LogUtils.info("Verify element visible " + by);
            return true;
        } catch (Exception e) {
            Assert.fail("The element is NOT visible. " + by);
            return false;
        }
    }

    /**
     * Verify if the given web element is visible. (in seconds)
     *
     * @param by      represent a web element as the By object
     * @param timeout System will wait at most timeout (seconds) to return result
     * @return true/false
     */
    public static boolean verifyElementVisible(By by, int timeout) {
        smartWait();
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(timeout));
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
            LogUtils.info("Verify element visible " + by);
            return true;
        } catch (Exception e) {
            LogUtils.error("The element is not visible. " + e.getMessage());
            Assert.fail("The element is NOT visible. " + by);
            return false;
        }
    }

    /**
     * Verify if the given web element is visible.
     *
     * @param by      represent a web element as the By object
     * @param message the custom message if false
     * @return true/false
     */
    public static boolean verifyElementVisible(By by, String message) {
        smartWait();
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(FrameworkConstants.WAIT_EXPLICIT), Duration.ofMillis(500));
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
            LogUtils.info("Verify element visible " + by);
            return true;
        } catch (Exception e) {
            if (message.isEmpty() || message == null) {
                LogUtils.error("The element is not visible. " + by);
                Assert.fail("The element is NOT visible. " + by);
            } else {
                LogUtils.error(message + by);
                Assert.fail(message + by);
            }
            return false;
        }
    }

    /**
     * Verify if the given web element is visible. (in seconds)
     *
     * @param by      represent a web element as the By object
     * @param timeout System will wait at most timeout (seconds) to return result
     * @param message the custom message if false
     * @return true/false
     */
    public static boolean verifyElementVisible(By by, int timeout, String message) {
        smartWait();
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(timeout));
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
            LogUtils.info("Verify element visible " + by);
            return true;
        } catch (Exception e) {
            if (message.isEmpty() || message == null) {
                LogUtils.error("The element is not visible. " + by);
                Assert.fail("The element is NOT visible. " + by);
            } else {
                LogUtils.error(message + by);
                Assert.fail(message + by);
            }
            return false;
        }
    }

    /**
     * Verify if the given web element is NOT visible.
     *
     * @param by represent a web element as the By object
     * @return true/false
     */
    public static boolean verifyElementNotVisible(By by) {
        smartWait();
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(FrameworkConstants.WAIT_EXPLICIT), Duration.ofMillis(500));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
            return true;
        } catch (Exception e) {
            LogUtils.error("FAILED. The element is visible " + by);
            Assert.fail("FAILED. The element is visible " + by);
            return false;
        }
    }

    /**
     * Verify if the given web element is NOT visible. (in seconds)
     *
     * @param by      represent a web element as the By object
     * @param timeout System will wait at most timeout (seconds) to return result
     * @return true/false
     */
    public static boolean verifyElementNotVisible(By by, int timeout) {
        smartWait();
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(timeout));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
            return true;
        } catch (Exception e) {
            LogUtils.error("FAILED. The element is visible " + by);
            Assert.fail("FAILED. The element is visible " + by);
            return false;
        }
    }

    /**
     * Verify if the given web element is NOT visible.
     *
     * @param by      represent a web element as the By object
     * @param message the custom message if false
     * @return true/false
     */
    public static boolean verifyElementNotVisible(By by, String message) {
        smartWait();
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(FrameworkConstants.WAIT_EXPLICIT), Duration.ofMillis(500));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
            return true;
        } catch (Exception e) {
            if (message.isEmpty() || message == null) {
                LogUtils.error("FAILED. The element is visible " + by);
                Assert.fail("FAILED. The element is visible " + by);
            } else {
                LogUtils.error(message + " " + by);
                Assert.fail(message + " " + by);
            }
            return false;
        }
    }

    /**
     * Verify if the given web element is NOT visible. (in seconds)
     *
     * @param by      represent a web element as the By object
     * @param timeout System will wait at most timeout (seconds) to return result
     * @param message the custom message if false
     * @return true/false
     */
    public static boolean verifyElementNotVisible(By by, int timeout, String message) {
        smartWait();
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(timeout));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
            return true;
        } catch (Exception e) {
            if (message.isEmpty() || message == null) {
                LogUtils.error("FAILED. The element is visible " + by);
                Assert.fail("FAILED. The element is visible " + by);
            } else {
                LogUtils.error(message + " " + by);
                Assert.fail(message + " " + by);
            }
            return false;
        }
    }

    /**
     * Scroll an element into the visible area of the browser window. (at TOP)
     *
     * @param by represent a web element as the By object
     */
    public static void scrollToElementAtTop(By by) {
        smartWait();
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
        js.executeScript("arguments[0].scrollIntoView(true);", getWebElement(by));
        LogUtils.info("Scroll to element " + by);
    }

    /**
     * Scroll an element into the visible area of the browser window. (at BOTTOM)
     *
     * @param by represent a web element as the By object
     */
    public static void scrollToElementAtBottom(By by) {
        smartWait();
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
        js.executeScript("arguments[0].scrollIntoView(false);", getWebElement(by));
        LogUtils.info("Scroll to element " + by);
    }

    /**
     * Scroll an element into the visible area of the browser window. (at TOP)
     *
     * @param webElement represent a web element as the By object
     */
    public static void scrollToElementAtTop(WebElement webElement) {
        smartWait();
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
        js.executeScript("arguments[0].scrollIntoView(true);", webElement);
        LogUtils.info("Scroll to element " + webElement);
    }

    /**
     * Scroll an element into the visible area of the browser window. (at BOTTOM)
     *
     * @param webElement represent a web element as the By object
     */
    public static void scrollToElementAtBottom(WebElement webElement) {
        smartWait();
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
        js.executeScript("arguments[0].scrollIntoView(false);", webElement);
        LogUtils.info("Scroll to element " + webElement);
    }

    /**
     * Scroll to an offset location
     *
     * @param X x offset
     * @param Y y offset
     */
    public static void scrollToPosition(int X, int Y) {
        smartWait();
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
        js.executeScript("window.scrollTo(" + X + "," + Y + ");");
        LogUtils.info("Scroll to position X = " + X + " ; Y = " + Y);
    }

    /**
     * Simulate users hovering a mouse over the given element.
     *
     * @param by represent a web element as the By object
     * @return true/false
     */
    public static boolean hoverOnElement(By by) {
        smartWait();
        try {
            Actions action = new Actions(DriverManager.getDriver());
            action.moveToElement(getWebElement(by)).perform();
            LogUtils.info("Hover on element " + by);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Simulate users hovering a mouse over the given element.
     *
     * @return true/false
     */
    public static boolean hoverOnDynamicElement(By objectListItem, String text) {
        smartWait();
        try {
            List<WebElement> elements = getWebElements(objectListItem);
            System.out.println("element list: " + elements);
            for (WebElement element : elements) {
                System.out.println("element: " + element);
                LogUtils.pass("Option=" + element.getText());
                if (element.getText().toLowerCase().trim().contains(text.toLowerCase().trim())) {
                    Actions action = new Actions(DriverManager.getDriver());
                    action.moveToElement(element).perform();
                    ExtentReportManager.info("Hover on element " + element + " with option: " + element.getText());
                    LogUtils.info("Hover on element " + element + " with option: " + element.getText());
                    return true;
                }
            }
        } catch (Exception e) {
            LogUtils.fail(e.getMessage());
            e.getMessage();
        }
        return false;
    }

    /**
     * Drag and drop an element onto another element.
     *
     * @param fromElement represent the drag-able element
     * @param toElement   represent the drop-able element
     * @return true/false
     */
    public static boolean dragAndDrop(By fromElement, By toElement) {
        smartWait();
        try {
            Actions action = new Actions(DriverManager.getDriver());
            action.dragAndDrop(getWebElement(fromElement), getWebElement(toElement)).perform();
            return true;
        } catch (Exception e) {
            LogUtils.info(e.getMessage());
            return false;
        }
    }

    /**
     * Simulate users hovering a mouse over the given element.
     *
     * @param by represent a web element as the By object
     * @return true/false
     */
    public static boolean mouseHover(By by) {
        smartWait();
        try {
            Actions action = new Actions(DriverManager.getDriver());
            action.moveToElement(getWebElement(by)).perform();
            LogUtils.info("Mouse hover on element " + by);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Drag and drop an element onto another element. (HTML5)
     *
     * @param fromElement represent the drag-able element
     * @param toElement   represent the drop-able element
     * @return true/false
     */
    public static boolean dragAndDropHTML5(By fromElement, By toElement) {
        smartWait();
        try {
            Robot robot = new Robot();
            robot.mouseMove(0, 0);
            int X1 = getWebElement(fromElement).getLocation().getX() + (getWebElement(fromElement).getSize().getWidth() / 2);
            int Y1 = getWebElement(fromElement).getLocation().getY() + (getWebElement(fromElement).getSize().getHeight() / 2);
            System.out.println(X1 + " , " + Y1);
            int X2 = getWebElement(toElement).getLocation().getX() + (getWebElement(toElement).getSize().getWidth() / 2);
            int Y2 = getWebElement(toElement).getLocation().getY() + (getWebElement(toElement).getSize().getHeight() / 2);
            System.out.println(X2 + " , " + Y2);
            //This place takes the current coordinates plus 120px which is the browser header (1920x1080 current window)
            // Header: chrome is being controlled by automated test software
            sleep(1);
            robot.mouseMove(X1, Y1 + 120);
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            sleep(1);
            robot.mouseMove(X2, Y2 + 120);
            sleep(1);
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
            return true;
        } catch (Exception e) {
            LogUtils.info(e.getMessage());
            return false;
        }
    }

    /**
     * Drag an object and drop it to an offset location.
     *
     * @param fromElement represent the drag-able element
     * @param X           x offset
     * @param Y           y offset
     * @return true/false
     */
    public static boolean dragAndDropToOffset(By fromElement, int X, int Y) {
        smartWait();
        try {
            Robot robot = new Robot();
            robot.mouseMove(0, 0);
            int X1 = getWebElement(fromElement).getLocation().getX() + (getWebElement(fromElement).getSize().getWidth() / 2);
            int Y1 = getWebElement(fromElement).getLocation().getY() + (getWebElement(fromElement).getSize().getHeight() / 2);
            System.out.println(X1 + " , " + Y1);
            sleep(1);
            //This place takes the current coordinates plus 120px which is the browser header (1920x1080 current window)
            // Header: chrome is being controlled by automated test software
            robot.mouseMove(X1, Y1 + 120);
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            sleep(1);
            robot.mouseMove(X, Y + 120);
            sleep(1);
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
            return true;
        } catch (Exception e) {
            LogUtils.info(e.getMessage());
            return false;
        }
    }

    /**
     * Move to the given element.
     *
     * @param toElement represent a web element as the By object
     * @return true/false
     */
    //@Step("Move to element {0}")
    public static boolean moveToElement(By toElement) {
        smartWait();
        try {
            Actions action = new Actions(DriverManager.getDriver());
            action.moveToElement(getWebElement(toElement)).release(getWebElement(toElement)).build().perform();
            return true;
        } catch (Exception e) {
            LogUtils.info(e.getMessage());
            return false;
        }
    }

    /**
     * Move to an offset location.
     *
     * @param X x offset
     * @param Y y offset
     * @return true/false
     */
    public static boolean moveToOffset(int X, int Y) {
        smartWait();
        try {
            Actions action = new Actions(DriverManager.getDriver());
            action.moveByOffset(X, Y).build().perform();
            return true;
        } catch (Exception e) {
            LogUtils.info(e.getMessage());
            return false;
        }
    }

    /**
     * Reload the current web page.
     */
    public static void reloadPage() {
        smartWait();
        DriverManager.getDriver().navigate().refresh();
        waitForPageLoaded();
        LogUtils.info("Reloaded page " + DriverManager.getDriver().getCurrentUrl());
    }

    /**
     * Fills the border color of the specified element.
     *
     * @param by passes the element object in the form By
     * @return Colors red borders for Elements on the website
     */
    public static WebElement highLightElement(By by) {
        smartWait();
        // draw a border around the found element
        if (DriverManager.getDriver() instanceof JavascriptExecutor) {
            ((JavascriptExecutor) DriverManager.getDriver()).executeScript("arguments[0].style.border='3px solid red'", waitForElementVisible(by));
            sleep(1);
            LogUtils.info("Highlight on element " + by);
        }
        return getWebElement(by);
    }

    /**
     * Navigate to the specified URL.
     *
     * @param URL the specified url
     */
    public static void openWebsite(String URL) {
        sleep(FrameworkConstants.WAIT_SLEEP_STEP);
        DriverManager.getDriver().get(URL);
        waitForPageLoaded();
        LogUtils.pass("Open website with URL: " + URL);
        if (ExtentTestManager.getExtentTest() != null) {
            ExtentReportManager.pass("Open website with URL: " + URL);
        }
        addScreenshotToReport(Thread.currentThread().getStackTrace()[1].getMethodName() + "_" + DateUtils.getCurrentDateTime());
    }

    /**
     * Navigate to the specified web page.
     *
     * @param URL the specified url
     */
    public static void navigateToUrl(String URL, String urlPattern, int timeout) {
        DriverManager.getDriver().navigate().to(URL);
        waitForPageLoaded();
        LogUtils.pass("Navigate to URL: " + URL);
        waitForURLSubstring(urlPattern, timeout);
        if (ExtentTestManager.getExtentTest() != null) {
            ExtentReportManager.pass("Navigate to URL: " + URL);
        }
        addScreenshotToReport(Thread.currentThread().getStackTrace()[1].getMethodName() + "_" + DateUtils.getCurrentDateTime());
    }

    /**
     * Set the value of an input field
     *
     * @param by    an element of object type By
     * @param value the value to fill in the text box
     */
    public static void setText(By by, String value) {
        waitForElementVisible(by).sendKeys(value);
        LogUtils.pass("Set text " + value + " on " + by.toString());
        if (ExtentTestManager.getExtentTest() != null) {
            ExtentReportManager.pass("Set text " + value + " on " + by.toString());
        }
        addScreenshotToReport(Thread.currentThread().getStackTrace()[1].getMethodName() + "_" + DateUtils.getCurrentDateTime());
    }

    public static void setTextByKeys(By by, String value) {
        waitForElementVisible(by).clear();
        for (char ch : value.toCharArray()) waitForElementVisible(by).sendKeys(String.valueOf(ch));
    }

    /**
     * Set the value of an input field and press the key on the keyboard
     *
     * @param by    an element of object type By
     * @param value the value to fill in the text box
     * @param keys  key on the keyboard to press
     */
    public static void setText(By by, String value, Keys keys) {
        waitForElementVisible(by).sendKeys(value, keys);
        LogUtils.pass("Set text " + value + " on " + by + " and press key " + keys.name());
        if (ExtentTestManager.getExtentTest() != null) {
            ExtentReportManager.pass("Set text " + value + " on " + by + " and press key " + keys.name());
        }
        addScreenshotToReport(Thread.currentThread().getStackTrace()[1].getMethodName() + "_" + DateUtils.getCurrentDateTime());
    }

    /**
     * Navigates back from current web page state
     *
     * @param regexPattern the value expected in URL after navigating back
     * @param timeout      time to wait for page to load completely
     */
    public static void navigateBack(String regexPattern, int timeout) {
        DriverManager.getDriver().navigate().back();
        waitForURLSubstring(regexPattern, timeout);
        LogUtils.pass("Navigated Back");
        if (ExtentTestManager.getExtentTest() != null) {
            ExtentReportManager.pass("Navigated Back");
        }
        addScreenshotToReport(Thread.currentThread().getStackTrace()[1].getMethodName() + "_" + DateUtils.getCurrentDateTime());
    }

    /**
     * Simulates keystroke events on the specified element, as though you typed the value key-by-key.
     *
     * @param by   an element of object type By
     * @param keys key on the keyboard to press
     */
    public static void sendKeys(By by, Keys keys) {
        waitForElementVisible(by).sendKeys(keys);
        LogUtils.pass("Press key " + keys.name() + " on element " + by);
        if (ExtentTestManager.getExtentTest() != null) {
            ExtentReportManager.pass("Press key " + keys.name() + " on element " + by);
        }
        addScreenshotToReport(Thread.currentThread().getStackTrace()[1].getMethodName() + "_" + DateUtils.getCurrentDateTime());
    }

    /**
     * Simulates keystroke events at the current position, as though you typed the value key-by-key.
     *
     * @param keys key on the keyboard to press
     */
    public static void sendKeys(Keys keys) {
        Actions actions = new Actions(DriverManager.getDriver());
        actions.sendKeys(keys);
        LogUtils.pass("Press key " + keys.name() + " on keyboard");
        if (ExtentTestManager.getExtentTest() != null) {
            ExtentReportManager.pass("Press key " + keys.name() + " on keyboard");
        }
        addScreenshotToReport(Thread.currentThread().getStackTrace()[1].getMethodName() + "_" + DateUtils.getCurrentDateTime());
    }

    /**
     * Clear all text of the element.
     *
     * @param by an element of object type By
     */
    public static void clearText(By by) {
        waitForElementVisible(by).clear();
        LogUtils.pass("Clear text in textbox " + by.toString());
        if (ExtentTestManager.getExtentTest() != null) {
            ExtentReportManager.pass("Clear text in textbox " + by);
        }
        addScreenshotToReport(Thread.currentThread().getStackTrace()[1].getMethodName() + "_" + DateUtils.getCurrentDateTime());
    }

    /**
     * Clear all text of the element with press Ctrl A > Delete
     *
     * @param by an element of object type By
     */
    public static void clearTextCtrlA(By by) {
        waitForElementVisible(by);
        Actions actions = new Actions(DriverManager.getDriver());
        actions.click(getWebElement(by)).build().perform();
        actions.keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL).build().perform();
        actions.sendKeys(Keys.DELETE).build().perform();
        LogUtils.pass("Clear text in textbox " + by.toString());
        if (ExtentTestManager.getExtentTest() != null) {
            ExtentReportManager.pass("Clear text in textbox " + by);
        }
        addScreenshotToReport(Thread.currentThread().getStackTrace()[1].getMethodName() + "_" + DateUtils.getCurrentDateTime());
    }

    /**
     * Clear all text of the element then set the text on that element.
     *
     * @param by    an element of object type By
     * @param value the value to fill in the text box
     */
    public static void clearAndFillText(By by, String value) {
        waitForElementVisible(by).clear();
        waitForElementVisible(by).sendKeys(value);
        LogUtils.pass("Clear and Fill " + value + " on " + by.toString());
        if (ExtentTestManager.getExtentTest() != null) {
            ExtentReportManager.pass("Clear and Fill " + value + " on " + by.toString());
        }
        addScreenshotToReport(Thread.currentThread().getStackTrace()[1].getMethodName() + "_" + DateUtils.getCurrentDateTime());
    }

    /**
     * Click on the specified element.
     *
     * @param by an element of object type By
     */
    public static void clickElement(By by) {
        waitForElementClickable(by);
        try {
            waitForElementVisible(by).click();
        } catch (StaleElementReferenceException | ElementClickInterceptedException e) {
            e.printStackTrace();
            LogUtils.info("Caught element stale/clickIntercepted exception at " + by.toString() + ", Retrying to click after 2 seconds");
            sleep(4);
            waitForElementVisible(by).click();
        }
        LogUtils.pass("Clicked on the element " + by.toString());
        if (ExtentTestManager.getExtentTest() != null) {
            ExtentReportManager.pass("Clicked on the element " + by);
        }
        addScreenshotToReport(Thread.currentThread().getStackTrace()[1].getMethodName() + "_" + DateUtils.getCurrentDateTime());
    }

    public static void waitForURLSubstring(String substring, int timeout) {
        // Create a WebDriverWait instance with a timeout (e.g., 30 seconds)
        WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(timeout));
        // Use ExpectedConditions.urlContains(substring) to wait for the URL to contain the specified substring
        wait.until(ExpectedConditions.urlContains(substring));
        if (ExtentTestManager.getExtentTest() != null) {
            ExtentReportManager.pass("Url pattern matched the expected url " + DriverManager.getDriver().getCurrentUrl());
        }
    }

    public static void clickAndWaitForURL(By by, String regexPattern, int timeout) {
        waitForElementVisible(by);
        clickElement(by);
        waitForURLSubstring(regexPattern, timeout);
        LogUtils.pass("Clicked on the element " + by.toString());
        if (ExtentTestManager.getExtentTest() != null) {
            ExtentReportManager.pass("Clicked on the element " + by);
        }
        addScreenshotToReport(Thread.currentThread().getStackTrace()[1].getMethodName() + "_" + DateUtils.getCurrentDateTime());
    }

    /**
     * Click on element with timeout
     *
     * @param by an element of object type By
     */
    public static void clickElement(By by, int timeout) {
        waitForElementVisible(by, timeout).click();
        LogUtils.pass("Clicked on the element " + by.toString());
        if (ExtentTestManager.getExtentTest() != null) {
            ExtentReportManager.pass("Clicked on the element " + by);
        }
        addScreenshotToReport(Thread.currentThread().getStackTrace()[1].getMethodName() + "_" + DateUtils.getCurrentDateTime());
    }

    /**
     * Click on Elements on the web with Javascript (click implicitly without fear of being hidden)
     *
     * @param by an element of object type By
     */
    public static void clickElementWithJs(By by) {
        waitForElementPresent(by);
        //Scroll to element với Javascript Executor
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
        js.executeScript("arguments[0].scrollIntoView(false);", getWebElement(by));
        //Click with JS
        js.executeScript("arguments[0].click();", getWebElement(by));
        LogUtils.pass("Click on element with JS: " + by);
        if (ExtentTestManager.getExtentTest() != null) {
            ExtentReportManager.pass("Click on element with JS: " + by);
        }
        addScreenshotToReport(Thread.currentThread().getStackTrace()[1].getMethodName() + "_" + DateUtils.getCurrentDateTime());
    }

    /**
     * Click on the link on website with text
     *
     * @param linkText is the visible text of a link
     */
    public static void clickLinkText(String linkText) {
        smartWait();
        WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(FrameworkConstants.WAIT_EXPLICIT), Duration.ofMillis(500));
        WebElement elementWaited = wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(linkText)));
        elementWaited.click();
        LogUtils.pass("Click on link text " + linkText);
        if (ExtentTestManager.getExtentTest() != null) {
            ExtentReportManager.pass("Click on link text " + linkText);
        }
        addScreenshotToReport(Thread.currentThread().getStackTrace()[1].getMethodName() + "_" + DateUtils.getCurrentDateTime());
    }

    /**
     * Right-click on the Element object on the web
     *
     * @param by an element of object type By
     */
    public static void rightClickElement(By by) {
        Actions action = new Actions(DriverManager.getDriver());
        action.contextClick(waitForElementVisible(by)).build().perform();
        LogUtils.pass("Right click on element " + by);
        if (ExtentTestManager.getExtentTest() != null) {
            ExtentReportManager.pass("Right click on element " + by);
        }
        addScreenshotToReport(Thread.currentThread().getStackTrace()[1].getMethodName() + "_" + DateUtils.getCurrentDateTime());
    }

    /**
     * Get text of an element
     *
     * @param by an element of object type By
     * @return text of a element
     */
    public static String getTextElement(By by) {
        smartWait();
        return waitForElementVisible(by).getText().trim();
    }

    /**
     * Get the value from the element's attribute
     *
     * @param by            an element of object type By
     * @param attributeName attribute name
     * @return element's attribute value
     */
    public static String getAttributeElement(By by, String attributeName) {
        smartWait();
        return waitForElementVisible(by).getAttribute(attributeName);
    }

    /**
     * Get CSS value of an element
     *
     * @param by      represent a web element as the By object
     * @param cssName is CSS attribute name
     * @return value of CSS attribute
     */
    public static String getCssValueElement(By by, String cssName) {
        smartWait();
        return waitForElementVisible(by).getCssValue(cssName);
    }

    /**
     * Get size of specified element
     *
     * @param by represent a web element as the By object
     * @return Dimension
     */
    public static Dimension getSizeElement(By by) {
        smartWait();
        return waitForElementVisible(by).getSize();
    }

    /**
     * Get location of specified element
     *
     * @param by represent a web element as the By object
     * @return Point
     */
    public static Point getLocationElement(By by) {
        smartWait();
        return waitForElementVisible(by).getLocation();
    }

    /**
     * Get tag name (HTML tag) of specified element
     *
     * @param by represent a web element as the By object
     * @return Tag name as String
     */
    public static String getTagNameElement(By by) {
        smartWait();
        return waitForElementVisible(by).getTagName();
    }

    /**
     * Check the value of each column of the table when searching according to EQUAL conditions (equals)
     *
     * @param column column position
     * @param value  value to compare
     */
    public static void checkEqualsValueOnTableByColumn(int column, String value) {
        smartWait();
        sleep(1);
        List<WebElement> totalRows = getWebElements(By.xpath("//tbody/tr"));
        LogUtils.info("Number of results for keywords (" + value + "): " + totalRows.size());
        if (totalRows.size() < 1) {
            LogUtils.info("Not found value: " + value);
        } else {
            for (int i = 1; i <= totalRows.size(); i++) {
                boolean res = false;
                WebElement title = waitForElementVisible(By.xpath("//tbody/tr[" + i + "]/td[" + column + "]"));
                res = title.getText().toUpperCase().equals(value.toUpperCase());
                LogUtils.info("Row " + i + ": " + res + " - " + title.getText());
                Assert.assertTrue(res, "Row " + i + " (" + title.getText() + ")" + " equals no value: " + value);
            }
        }
    }

    /**
     * Check the value of each column of the table when searching according to the CONTAINS condition (contains)
     *
     * @param column column position
     * @param value  value to compare
     */
    public static void checkContainsValueOnTableByColumn(int column, String value) {
        smartWait();
        sleep(1);
        List<WebElement> totalRows = getWebElements(By.xpath("//tbody/tr"));
        LogUtils.pass("Number of results for keywords (" + value + "): " + totalRows.size());
        if (totalRows.size() < 1) {
            LogUtils.pass("Not found value: " + value);
        } else {
            for (int i = 1; i <= totalRows.size(); i++) {
                boolean res = false;
                WebElement title = waitForElementVisible(By.xpath("//tbody/tr[" + i + "]/td[" + column + "]"));
                res = title.getText().toUpperCase().contains(value.toUpperCase());
                LogUtils.pass("Row " + i + ": " + res + " - " + title.getText());
                Assert.assertTrue(res, "Row " + i + " (" + title.getText() + ")" + " contains no value: " + value);
            }
        }
    }

    /**
     * Check the value of each column of the table when searching according to the CONTAINS condition with custom xpath
     *
     * @param column           column position
     * @param value            value to compare
     * @param xpathToTRtagname xpath value up to TR tag
     */
    public static void checkContainsValueOnTableByColumn(int column, String value, String xpathToTRtagname) {
        smartWait();
        //xpathToTRtagname is locator from table to "tr" tagname of data section: //tbody/tr, //div[@id='example_wrapper']//tbody/tr, ...
        List<WebElement> totalRows = DriverManager.getDriver().findElements(By.xpath(xpathToTRtagname));
        sleep(1);
        LogUtils.pass("Number of results for keywords (" + value + "): " + totalRows.size());
        if (totalRows.size() < 1) {
            LogUtils.pass("Not found value: " + value);
        } else {
            for (int i = 1; i <= totalRows.size(); i++) {
                boolean res = false;
                WebElement title = waitForElementVisible(By.xpath(xpathToTRtagname + "[" + i + "]/td[" + column + "]"));
                res = title.getText().toUpperCase().contains(value.toUpperCase());
                LogUtils.pass("Row " + i + ": " + res + " - " + title.getText());
                Assert.assertTrue(res, "Row " + i + " (" + title.getText() + ")" + " contains no value " + value);
            }
        }
    }

    /**
     * Get the value of a column from the table
     *
     * @param column column position
     * @return array of values of a column
     */
    public static ArrayList getValueTableByColumn(int column) {
        smartWait();
        List<WebElement> totalRows = DriverManager.getDriver().findElements(By.xpath("//tbody/tr"));
        sleep(1);
        LogUtils.info("Number of results for column (" + column + "): " + totalRows.size()); //Không thích ghi log thì xóa nhen
        ArrayList arrayList = new ArrayList<String>();
        if (totalRows.size() < 1) {
            LogUtils.pass("Not found value !!");
        } else {
            for (int i = 1; i <= totalRows.size(); i++) {
                boolean res = false;
                WebElement title = DriverManager.getDriver().findElement(By.xpath("//tbody/tr[" + i + "]/td[" + column + "]"));
                arrayList.add(title.getText());
                LogUtils.pass("Row " + i + ":" + title.getText()); //Không thích ghi log thì xóa nhen
            }
        }
        return arrayList;
    }

    //Wait Element

    /**
     * Wait until the given web element is visible within the timeout.
     *
     * @param by      an element of object type By
     * @param timeOut maximum timeout as second
     * @return a WebElement object ready to be visible
     */
    public static WebElement waitForElementVisible(By by, int timeOut) {
        smartWait();
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(timeOut), Duration.ofMillis(500));
            boolean check = verifyElementVisible(by, timeOut);
            if (check == true) {
                return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
            } else {
                scrollToElementAtTop(by);
                return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
            }
        } catch (Throwable error) {
            Assert.fail("Timeout waiting for the element Visible. " + by.toString());
            LogUtils.fail("Timeout waiting for the element Visible. " + by.toString());
        }
        return null;
    }

    /**
     * Wait until the given web element is visible.
     *
     * @param by an element of object type By
     * @return a WebElement object ready to be visible
     */
    public static WebElement waitForElementVisible(By by) {
        smartWait();
        waitForElementPresent(by);
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(FrameworkConstants.WAIT_EXPLICIT), Duration.ofMillis(500));
            boolean check = isElementVisible(by, FrameworkConstants.WAIT_IMPLICIT);
            if (check == true) {
                return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
            } else {
                scrollToElementAtBottom(by);
                return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
            }
        } catch (Throwable error) {
            LogUtils.error("Timeout waiting for the element Visible. " + by.toString());
            Assert.fail("Timeout waiting for the element Visible. " + by);
        }
        return null;
    }

    /**
     * Wait for the given element to be clickable within the given time (in seconds).
     *
     * @param by an element of object type By * @param timeOut maximum timeout as seconds
     * @return a WebElement object ready to CLICK
     */
    public static WebElement waitForElementClickable(By by, long timeOut) {
        smartWait();
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(timeOut), Duration.ofMillis(500));
            return wait.until(ExpectedConditions.elementToBeClickable(getWebElement(by)));
        } catch (Throwable error) {
            Assert.fail("Timeout waiting for the element ready to click. " + by.toString());
            LogUtils.error("Timeout waiting for the element ready to click. " + by.toString());
        }
        return null;
    }

    /**
     * Wait for the given element to be clickable.
     *
     * @param by an element of object type By
     * @return a WebElement object ready to CLICK
     */
    public static WebElement waitForElementClickable(By by) {
        smartWait();
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(FrameworkConstants.WAIT_EXPLICIT), Duration.ofMillis(500));
            return wait.until(ExpectedConditions.elementToBeClickable(getWebElement(by)));
        } catch (Throwable error) {
            Assert.fail("Timeout waiting for the element ready to click. " + by.toString());
            LogUtils.error("Timeout waiting for the element ready to click. " + by.toString());
        }
        return null;
    }

    /**
     * Wait for the given element to present within the given time (in seconds).
     *
     * @param by      an element of object type By
     * @param timeOut maximum timeout as seconds
     * @return an existing WebElement object
     */
    public static WebElement waitForElementPresent(By by, long timeOut) {
        smartWait();
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(timeOut), Duration.ofMillis(500));
            return wait.until(ExpectedConditions.presenceOfElementLocated(by));
        } catch (Throwable error) {
            LogUtils.error("Timeout waiting for the element to exist. " + by.toString());
            Assert.fail("Timeout waiting for the element to exist. " + by.toString());
        }
        return null;
    }

    /**
     * Wait for the given element to present
     *
     * @param by an element of object type By
     * @return an existing WebElement object
     */
    public static WebElement waitForElementPresent(By by) {
        smartWait();
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(FrameworkConstants.WAIT_EXPLICIT), Duration.ofMillis(500));
            return wait.until(ExpectedConditions.presenceOfElementLocated(by));
        } catch (Throwable error) {
            LogUtils.error("Element not exist. " + by.toString());
            Assert.fail("Element not exist. " + by);
        }
        return null;
    }

    /**
     * Wait for element to be absent / disappear
     */
    public static boolean waitForElementToBeAbsent(By by) {
        smartWait();
        try {
            do {
                if (verifyElementExists(by)) {
                    sleep(2);
                } else {
                    return true;
                }
            } while (true);
        } catch (Throwable error) {
            LogUtils.info("Element not exist. " + by.toString());
        }
        return false;
    }

    /**
     * Wait for an alert to present.
     */
    public static boolean waitForAlertPresent() {
        smartWait();
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(FrameworkConstants.WAIT_EXPLICIT), Duration.ofMillis(500));
            wait.until(ExpectedConditions.alertIsPresent());
            return true;
        } catch (Throwable error) {
            LogUtils.error("Alert NOT present.");
            Assert.fail("Alert NOT present.");
            return false;
        }
    }

    /**
     * Wait for an alert to present.
     *
     * @param timeOut Timeout waiting for an alert to present.
     */
    public static boolean waitForAlertPresent(int timeOut) {
        smartWait();
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(timeOut), Duration.ofMillis(500));
            wait.until(ExpectedConditions.alertIsPresent());
            return true;
        } catch (Throwable error) {
            LogUtils.error("Alert NOT present.");
            Assert.fail("Alert NOT present.");
            return false;
        }
    }

    /**
     * Wait until the given web element has an attribute with the specified name.
     *
     * @param by            an element of object type By
     * @param attributeName the name of the attribute to wait for.
     * @return true/false
     */
    public static boolean waitForElementHasAttribute(By by, String attributeName) {
        smartWait();
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(FrameworkConstants.WAIT_EXPLICIT), Duration.ofMillis(500));
            return wait.until(ExpectedConditions.attributeToBeNotEmpty(waitForElementPresent(by), attributeName));
        } catch (Throwable error) {
            LogUtils.error("Timeout for element " + by.toString() + " to exist attribute: " + attributeName);
            Assert.fail("Timeout for element " + by.toString() + " to exist attribute: " + attributeName);
        }
        return false;
    }

    /**
     * Verify if the web element has an attribute with the specified name and value.
     *
     * @param by             an element of object type By
     * @param attributeName  The name of the attribute to wait for.
     * @param attributeValue The value of attribute
     * @return true/false
     */
    public static boolean verifyElementAttributeValue(By by, String attributeName, String attributeValue) {
        smartWait();
        waitForElementVisible(by);
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(FrameworkConstants.WAIT_EXPLICIT), Duration.ofMillis(500));
            wait.until(ExpectedConditions.attributeToBe(by, attributeName, attributeValue));
            return true;
        } catch (Throwable error) {
            LogUtils.error("Object: " + by.toString() + ". Not found value: " + attributeValue + " with attribute type: " + attributeName + ". Actual get Attribute value is: " + getAttributeElement(by, attributeName));
            Assert.fail("Object: " + by.toString() + ". Not found value: " + attributeValue + " with attribute type: " + attributeName + ". Actual get Attribute value is: " + getAttributeElement(by, attributeName));
            return false;
        }
    }

    /**
     * Verify if the web element has an attribute with the specified name.
     *
     * @param by            represent a web element.
     * @param attributeName The name of the attribute to wait for.
     * @param timeOut       system will wait at most timeout (seconds) to return result
     * @return true/false
     */
    public static boolean verifyElementHasAttribute(By by, String attributeName, int timeOut) {
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(timeOut));
            wait.until(ExpectedConditions.attributeToBeNotEmpty(waitForElementPresent(by), attributeName));
            return true;
        } catch (Throwable error) {
            LogUtils.error("Not found Attribute " + attributeName + " of element " + by.toString());
            Assert.fail("Not found Attribute " + attributeName + " of element " + by.toString());
            return false;
        }
    }

    /**
     * Wait for a page to load with the default time from the config
     */
    public static void waitForPageLoaded() {
        WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(FrameworkConstants.WAIT_PAGE_LOADED), Duration.ofMillis(500));
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
        // wait for Javascript to loaded
        ExpectedCondition<Boolean> jsLoad = driver -> ((JavascriptExecutor) driver).executeScript("return document.readyState").toString().equals("complete");
        //Get JS is Ready
        boolean jsReady = js.executeScript("return document.readyState").toString().equals("complete");
        //Wait Javascript until it is Ready!
        if (!jsReady) {
            LogUtils.info("Javascript in NOT Ready!");
            //Wait for Javascript to load
            try {
                wait.until(jsLoad);
            } catch (Throwable error) {
                error.printStackTrace();
                Assert.fail("Timeout waiting for page load. (" + FrameworkConstants.WAIT_PAGE_LOADED + "s)");
            }
        }
    }

    /**
     * Wait for a page to load within the given time (in seconds)
     */
    public static void waitForPageLoaded(int timeOut) {
        WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(timeOut), Duration.ofMillis(500));
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
        // wait for Javascript to loaded
        ExpectedCondition<Boolean> jsLoad = driver -> ((JavascriptExecutor) driver).executeScript("return document.readyState").toString().equals("complete");
        //Get JS is Ready
        boolean jsReady = js.executeScript("return document.readyState").toString().equals("complete");
        //Wait Javascript until it is Ready!
        if (!jsReady) {
            LogUtils.info("Javascript in NOT Ready!");
            //Wait for Javascript to load
            try {
                wait.until(jsLoad);
            } catch (Throwable error) {
                error.printStackTrace();
                Assert.fail("Timeout waiting for page load. (" + FrameworkConstants.WAIT_PAGE_LOADED + "s)");
            }
        }
    }

    /**
     * Wait for JQuery to finish loading with default time from config
     */
    public static void waitForJQueryLoad() {
        WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(FrameworkConstants.WAIT_PAGE_LOADED), Duration.ofMillis(500));
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
        //Wait for jQuery to load
        ExpectedCondition<Boolean> jQueryLoad = driver -> {
            assert driver != null;
            return ((Long) ((JavascriptExecutor) driver).executeScript("return jQuery.active") == 0);
        };
        //Get JQuery is Ready
        boolean jqueryReady = (Boolean) js.executeScript("return jQuery.active==0");
        //Wait JQuery until it is Ready!
        if (!jqueryReady) {
            LogUtils.info("JQuery is NOT Ready!");
            try {
                //Wait for jQuery to load
                wait.until(jQueryLoad);
            } catch (Throwable error) {
                Assert.fail("Timeout waiting for JQuery load. (" + FrameworkConstants.WAIT_PAGE_LOADED + "s)");
            }
        }
    }

    /**
     * It will allow selecting option by just providing the name of div and option to select as text
     *
     * @param nameOfDrpDiv
     * @param optionToSelect
     */
    public static void selectDynamicOption(String nameOfDrpDiv, String optionToSelect) {
        selectDynamicOptionUsingXpath("//*[@name='" + nameOfDrpDiv + "']", optionToSelect);
    }

    /**
     * Select dropdown value by passing xpath of dropdown
     *
     * @param xpathOfDropdown
     * @param optionToSelect
     */
    public static void selectDynamicOptionUsingXpath(String xpathOfDropdown, String optionToSelect) {
        LogUtils.info("Option to select in dropdown " + optionToSelect);
        By dynamicOptionList = By.xpath(xpathOfDropdown + "//div[@role='option']");
        WebUI.clickElement(By.xpath(xpathOfDropdown));
        WebUI.sleep(2);
        WebUI.selectOptionDynamic(dynamicOptionList, optionToSelect);
    }

    /**
     * It will allow selecting option by just providing the name of div and option to select as index (start with 0)
     *
     * @param nameOfDrpDiv
     * @param index
     */
    public static void selectDynamicOption(String nameOfDrpDiv, int index) {
        By dynamicOptionList = By.xpath("//*[@name='" + nameOfDrpDiv + "']//div[@role='option']");
        WebUI.clickElement(By.name(nameOfDrpDiv));
        WebUI.selectOptionDynamic(dynamicOptionList, index);
    }

    /**
     * Type in textbox for dropdown and then select option to select
     *
     * @param nameOfDrpDiv
     * @param optionToSelect
     */
    public static void typeAndSelectDynamicOption(String nameOfDrpDiv, String optionToSelect) {
        typeAndSelectDynamicOptionUsingXpath("//*[@name='" + nameOfDrpDiv + "']", optionToSelect);
    }

    /**
     * type and select the value in dropdown when locator is passed as argument
     *
     * @param xpathOfDIV
     * @param optionToSelect
     */
    public static void typeAndSelectDynamicOptionUsingXpath(String xpathOfDIV, String optionToSelect) {
        By dynamicOptionList = By.xpath(xpathOfDIV + "//div[@role='option']//*[normalize-space(text())='" + optionToSelect + "']");
        By inputSearch = By.xpath(xpathOfDIV + "//input[@type='search']");
        WebUI.waitForPageLoaded();
        WebUI.sleep(2);
        WebUI.waitForElementClickable(By.xpath(xpathOfDIV));
        WebUI.smartWait();
        WebUI.clickElement(By.xpath(xpathOfDIV));
        WebUI.sleep(2);
        if (!WebUI.getWebElement(inputSearch).isEnabled()) WebUI.clickElement(By.xpath(xpathOfDIV));
        WebUI.getWebElement(inputSearch).click();
        WebUI.getWebElement(inputSearch).sendKeys(optionToSelect);
        WebUI.waitForElementVisible(dynamicOptionList);
        WebUI.clickElement(dynamicOptionList);
    }

    /**
     * Accept if any alert shown
     */
    public static void acceptIfAlertShown() {
        sleep(FrameworkConstants.WAIT_SLEEP_STEP);
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(10), Duration.ofMillis(500));
            wait.until(ExpectedConditions.alertIsPresent());
            DriverManager.getDriver().switchTo().alert().accept();
            LogUtils.pass("Click Accept on Alert.");
        } catch (Throwable error) {
            LogUtils.pass("No Alert.");
        }
    }

    /**
     * Get the option from dynamic dropdown
     *
     * @param dropdownXpath
     * @return
     */
    public static List<String> getOptionsDynamic(String dropdownXpath) {
        smartWait();
        List<String> options = new ArrayList<>();
        //For dynamic dropdowns (div, li, span,...not select options)
        if (!WebUI.isElementVisible(By.xpath(dropdownXpath + "//ul"), 2)) {
            WebUI.clickElement(By.xpath(dropdownXpath));
            WebUI.sleep(3);
        }
        try {
            List<WebElement> elements = getWebElements(By.xpath(dropdownXpath + "//div[@role='option']"));
            for (WebElement element : elements) {
                LogUtils.pass("Option=" + element.getText());
                options.add(element.getText());
            }
        } catch (Exception e) {
            LogUtils.fail(e.getMessage());
            e.getMessage();
        }
        return options;
    }

    public static int generateRandomNumber() {
        Random random = new Random();
        return random.nextInt(10000);
    }
}
