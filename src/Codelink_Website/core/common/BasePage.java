package common;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

public class BasePage {
    public static BasePage getBasePageObject() {
        return new BasePage();
    }

    public void openPageURL(WebDriver driver, String pageURL) {
        driver.get(pageURL);
    }

    public String getPageTitle(WebDriver driver) {
        return driver.getTitle();
    }

    public String getPageURL(WebDriver driver) {
        return driver.getCurrentUrl();
    }

    public String getPageSource(WebDriver driver) {
        return driver.getPageSource();
    }

    public void getToPage(WebDriver driver) {
        driver.navigate().back();
    }

    public void forwardToPage(WebDriver driver) {
        driver.navigate().forward();
    }

    public void refreshCurrentPage(WebDriver driver) {
        driver.navigate().refresh();
    }

    public Alert waitForAlertPresent(WebDriver driver) {
        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return (Alert) explicitWait.until(WebDriver -> ExpectedConditions.alertIsPresent());
    }

    public void acceptAlert(WebDriver driver) {
        waitForAlertPresent(driver).accept();
    }

    public void cancelAlert(WebDriver driver) {
        waitForAlertPresent(driver).dismiss();
    }

    public String getAlertText(WebDriver driver) {
        return waitForAlertPresent(driver).getText();
    }

    public void sendKeyToAlert(WebDriver driver, String textValue) {
        waitForAlertPresent(driver).sendKeys(textValue);
    }

    public void switchToWindowById(WebDriver driver, String currentWindowId) {
        // Get all Ids of windows/tabs are opening
        Set<String> allWindowIds = driver.getWindowHandles();
        // Check through all Ids and switch tab/window
        for (String id : allWindowIds) {
            if (!id.equals(currentWindowId)) {
                driver.switchTo().window(id);
            }
        }
    }

    // Use for 2 or more than 2 tab/windows
    public void switchToWindowByTitle(WebDriver driver, String expectedPageTitle) {
        // Get all id of tabs/windows
        Set<String> allWindowId = driver.getWindowHandles();
        // Use for to check through all the id
        for (String id : allWindowId) {
            driver.switchTo().window(id);
            // get title of current page
            String currentTabTitle = driver.getTitle();

            if (currentTabTitle.equals(expectedPageTitle)) {
                break;
            }
        }
    }

    public void closeCurrentTabExpectForParentTab(WebDriver driver, String currentWindowId) {
        // Get all id of tabs/windows
        Set<String> allWindowId = driver.getWindowHandles();
        // Use for to check through all the id
        for (String id : allWindowId) {
            if (!id.equals(currentWindowId)) {
                driver.switchTo().window(id);
                driver.close();
            }
        }
        driver.switchTo().window(currentWindowId);
    }


    // id=/ css=/ xpath=/name=/class=
    protected By byLocator(String locatorType) {
        By by;
        if (locatorType.startsWith("id=") || locatorType.startsWith("ID=") || locatorType.startsWith("Id=")) {
            by = By.id(locatorType.substring(3));
        } else if (locatorType.startsWith("css=") || locatorType.startsWith("CSS=") || locatorType.startsWith("Css=")) {
            by = By.cssSelector(locatorType.substring(4));
        } else if (locatorType.startsWith("class=") || locatorType.startsWith("CLASS=") || locatorType.startsWith("Class=")) {
            by = By.className(locatorType.substring(5));
        } else if (locatorType.startsWith("xpath=") || locatorType.startsWith("XPATH=") || locatorType.startsWith("Xpath=")) {
            by = By.xpath(locatorType.substring(6));
        } else if (locatorType.startsWith("name=") || locatorType.startsWith("NAME=") || locatorType.startsWith("Name=")) {
            by = By.name(locatorType.substring(5));
        } else {
            throw new RuntimeException("Locator type is not supported");
        }
        return by;
    }

    public String getDynamicXpath(String xpathLocator, String... dynamicValues) {
        if (xpathLocator.startsWith("xpath=") || xpathLocator.startsWith("XPATH=") || xpathLocator.startsWith("Xpath=")) {
            xpathLocator = String.format(xpathLocator, (Object[]) dynamicValues);
        }
        return xpathLocator;
    }

    private WebElement getWebElement(WebDriver driver, String locatorType) {
        return driver.findElement(byLocator(locatorType));
    }

    public WebElement getWebElement(WebDriver driver, String xpathLocator, String... dynamicValues) {
        return driver.findElement(byLocator(getDynamicXpath(xpathLocator, dynamicValues)));
    }

    public List<WebElement> getListWebElement(WebDriver driver, String locatorType) {
        return driver.findElements(byLocator(locatorType));
    }

    public void clickToElement(WebDriver driver, String locatorType) {
        getWebElement(driver, locatorType).click();
    }

    public void clickToElement(WebDriver driver, String xpathLocator, String... dynamicValues) {
        getWebElement(driver, getDynamicXpath(xpathLocator, dynamicValues)).click();
    }

    public void clearTexbox(WebDriver driver, String locatorType) {
        getWebElement(driver, locatorType).clear();
    }

    public void clearTexbox(WebDriver driver, String xpathLocator, String... dynamicValue) {
        getWebElement(driver, getDynamicXpath(xpathLocator, dynamicValue)).clear();
    }

    public void sendKeyToElement(WebDriver driver, String locatorType, String textValue) {
        WebElement element = getWebElement(driver, locatorType);
        element.clear();
        element.sendKeys(textValue);
    }

    public void sendKeyToElement(WebDriver driver, String xpathLocator, String textValue, String... dynamicValues) {
        WebElement element = getWebElement(driver, getDynamicXpath(xpathLocator, dynamicValues));
        element.clear();
        element.sendKeys(textValue);
    }


    public String getElementText(WebDriver driver, String locatorType) {
        return getWebElement(driver, locatorType).getText();
    }

    public String getElementText(WebDriver driver, String xpathLocator, String... dynamicValues) {
        return getWebElement(driver, getDynamicXpath(xpathLocator, dynamicValues)).getText();
    }

    public void selectItemInDefaultDropdown(WebDriver driver, String locatorType, String textValue) {
        Select select = new Select(getWebElement(driver, locatorType));
        select.selectByValue(textValue);
    }

    public void selectItemInDefaultDropdown(WebDriver driver, String xpathLocator, String textValue, String... dynamicValues) {
        Select select = new Select(getWebElement(driver, getDynamicXpath(xpathLocator, dynamicValues)));
        select.selectByVisibleText(textValue);
    }

    public String getSelectedItemInDefaultDropdown(WebDriver driver, String locatorType) {
        Select select = new Select(getWebElement(driver, locatorType));
        return select.getFirstSelectedOption().getText();
    }

    public boolean isDropdownMultiple(WebDriver driver, String locatorType) {
        Select select = new Select(getWebElement(driver, locatorType));
        return select.isMultiple();
    }

    public void sleepInSecond(long timeInSecond) {
        try {
            Thread.sleep(timeInSecond * 1000);
        } catch (InterruptedException e) {
            e.fillInStackTrace();
        }
    }

    public void selectItemInDropdownList(WebDriver driver, String parentXpath, String childXpath, String expectedItem) {
        // Click to open the dropdown list
        getWebElement(driver, parentXpath).click();
        sleepInSecond(1);
        // Waiting for all elements is loaded=> WebDriverWait
        // By Locator=> phải lấy locator mà đại diện cho tất cả các giá trị
        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(GlobalConstants.NORMAL_TIMEOUT));
        explicitWait.until(WebDriver -> ExpectedConditions.presenceOfAllElementsLocatedBy(byLocator(childXpath)));
        // Lấy hết list các items ra
        List<WebElement> allDropdownItems = driver.findElements(byLocator(childXpath));
        // Dùng vòng lặp để ktra
        for (WebElement item : allDropdownItems) {
            String actualTextItem = item.getText();
            // thấy item cần chọn=> so sánh với giá trị mong muốn=> nếu đúng rồi thì click vào
            if (actualTextItem.equals(expectedItem)) {
                //arguments[0].scrollIntoView(true): mép trên của element=> thường sử dụng cái này
                //arguments[0].scrollIntoView(false): mép dưới của elemet
                JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
                jsExecutor.executeScript("arguments[0].scrollIntoView(true);", item);
                sleepInSecond(1);
                item.click();
                sleepInSecond(1);
                break;
            }
        }
    }


    public String getElementAttribute(WebDriver driver, String locatorType, String attributeName) {
        return getWebElement(driver, locatorType).getAttribute(attributeName);
    }

    public String getElementAttribute(WebDriver driver, String xpathLocator, String attributeName, String... dynamicValue) {
        return getWebElement(driver, getDynamicXpath(xpathLocator, dynamicValue)).getAttribute(attributeName);
    }

    public String getElementCssValue(WebDriver driver, String locatorType, String propertyName) {
        return getWebElement(driver, locatorType).getCssValue(propertyName);
    }

    public String getElementCssValue(WebDriver driver, String xpathLocator, String propertyName, String... dynamicValue) {
        return getWebElement(driver, getDynamicXpath(xpathLocator, dynamicValue)).getCssValue(propertyName);
    }

    public String getHexaColorFromRGBA(String rgbaValue) {
        return Color.fromString(rgbaValue).asHex();
    }

    public int getElementSize(WebDriver driver, String locatorType) {
        return getListWebElement(driver, locatorType).size();
    }

    public int getElementSize(WebDriver driver, String xpathLocator, String... dynamicValues) {
        return getListWebElement(driver, getDynamicXpath(xpathLocator, dynamicValues)).size();
    }

    public void checkToCheckbox(WebDriver driver, String locatorType) {
        WebElement element = getWebElement(driver, locatorType);
        if (!element.isSelected()) {
            element.click();
        }
    }

    public void checkToCheckbox(WebDriver driver, String xpathLocator, String... dynamicValues) {
        WebElement element = getWebElement(driver, getDynamicXpath(xpathLocator, dynamicValues));
        if (!element.isSelected()) {
            element.click();
        }
    }

    public void uncheckToCheckbox(WebDriver driver, String locatorType) {
        WebElement element = getWebElement(driver, locatorType);
        if (element.isSelected()) {
            element.click();
        }
    }

    public void uncheckToCheckbox(WebDriver driver, String xpathLocator, String... dynamicValues) {
        WebElement element = getWebElement(driver, getDynamicXpath(xpathLocator, dynamicValues));
        if (element.isSelected()) {
            element.click();
        }
    }

    public boolean isElementDisplayed(WebDriver driver, String locatorType) {
        try {
            return getWebElement(driver, locatorType).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isElementDisplayed(WebDriver driver, String locatorType, String... dynamicValues) {
        try {
            return getWebElement(driver, getDynamicXpath(locatorType, dynamicValues)).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isElementEnabled(WebDriver driver, String locatorType) {
        return getWebElement(driver, locatorType).isEnabled();
    }

    public boolean isElementSelected(WebDriver driver, String locatorType) {
        return getWebElement(driver, locatorType).isSelected();
    }

    public void switchToFrameIframe(WebDriver driver, String locatorType) {
        driver.switchTo().frame(getWebElement(driver, locatorType));
    }

    public void switchToDefaultContent(WebDriver driver) {
        driver.switchTo().defaultContent();
    }

    public void hoverMouseToElement(WebDriver driver, String locatorType) {
        Actions action = new Actions(driver);
        action.moveToElement(getWebElement(driver, locatorType)).perform();
    }

    public void hoverMouseToElement(WebDriver driver, String xpathLocator, String... dynamicValue) {
        Actions action = new Actions(driver);
        action.moveToElement(getWebElement(driver, getDynamicXpath(xpathLocator, dynamicValue))).perform();
    }

    public void pressKeyToElement(WebDriver driver, String locatorType, String key) {
        Actions action = new Actions(driver);
        action.sendKeys(getWebElement(driver, locatorType), key).perform();
    }

    public void pressKeyToElement(WebDriver driver, String locatorType, Keys key, String... dynamicValues) {
        Actions action = new Actions(driver);
        action.sendKeys(getWebElement(driver, getDynamicXpath(locatorType, dynamicValues)), key).perform();
    }

    public void scrollToBottomPage(WebDriver driver) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("window.scrollBy(0,document.body.scrollHeight)");
    }

    public void highlightElement(WebDriver driver, String locatorType) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        WebElement element = getWebElement(driver, locatorType);
        String originalStyle = element.getAttribute("style");
        jsExecutor.executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, "style", "border: 2px solid red; border-style: dashed;");
        sleepInSecond(1);
        jsExecutor.executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, "style", originalStyle);
    }

    public void clickToElementByJS(WebDriver driver, String locatorType) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].click();", getWebElement(driver, locatorType));
    }

    public void scrollToElement(WebDriver driver, String locatorType) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", getWebElement(driver, locatorType));
    }

    public void scrollToElement(WebDriver driver, String xpathLocator, String... dynamicValue) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", getWebElement(driver, getDynamicXpath(xpathLocator, dynamicValue)));
        sleepInSecond(1);
    }

    public void removeAttributeInDOM(WebDriver driver, String locatorType, String attributeRemove) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].removeAttribute('" + attributeRemove + "');", getWebElement(driver, locatorType));
    }

    public void removeAttributeInDOM(WebDriver driver, String xpathLocator, String attributeRemove, String... dynamicValues) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].removeAttribute('" + attributeRemove + "');", getWebElement(driver, getDynamicXpath(xpathLocator, dynamicValues)));
    }

    public String getElementValidationMessage(WebDriver driver, String locatorType) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        return (String) jsExecutor.executeScript("return arguments[0].validationMessage;", getWebElement(driver, locatorType));
    }

    public boolean isImageLoaded(WebDriver driver, String locatorType) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        return (boolean) jsExecutor.executeScript("return arguments[0].complete && typeof arguments[0].naturalWidth != \"undefined\" && arguments[0].naturalWidth > 0", getWebElement(driver, locatorType));
    }

    public boolean isImageLoaded(WebDriver driver, String xpathLocator, String... dynamicValues) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        return (boolean) jsExecutor.executeScript("return arguments[0].complete && typeof arguments[0].naturalWidth != \"undefined\" && arguments[0].naturalWidth > 0", getWebElement(driver, getDynamicXpath(xpathLocator, dynamicValues)));
    }

    public void waitForElementVisible(WebDriver driver, String locatorType) {
        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(GlobalConstants.NORMAL_TIMEOUT));
        explicitWait.until(WebDriver -> ExpectedConditions.visibilityOfElementLocated(byLocator(locatorType)));
    }

    public void waitForElementVisible(WebDriver driver, String locatorType, String... dynamicValues) {
        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(GlobalConstants.NORMAL_TIMEOUT));
        explicitWait.until(WebDriver -> ExpectedConditions.visibilityOfElementLocated(byLocator(getDynamicXpath(locatorType, dynamicValues))));
    }


    public void waitForAllElementsVisible(WebDriver driver, String locatorType) {
        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(GlobalConstants.LONG_TIMEOUT));
        explicitWait.until(WebDriver -> ExpectedConditions.visibilityOfElementLocated(byLocator(locatorType)));
    }

    public void waitForAllElementsVisible(WebDriver driver, String locatorType, String... dynamicValues) {
        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(GlobalConstants.LONG_TIMEOUT));
        explicitWait.until(WebDriver -> ExpectedConditions.visibilityOfElementLocated(byLocator(getDynamicXpath(locatorType, dynamicValues))));
    }

    public void waitForElementInvisible(WebDriver driver, String locatorType) {
        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(GlobalConstants.VERY_SHORT_TIMEOUT));
        overrideImplicitTimeout(driver, GlobalConstants.VERY_SHORT_TIMEOUT);
        explicitWait.until(WebDriver -> ExpectedConditions.invisibilityOfElementLocated(byLocator(locatorType)));
        overrideImplicitTimeout(driver, GlobalConstants.NORMAL_TIMEOUT);
    }

    public void waitForElementInvisible(WebDriver driver, String locatorType, String... dynamicValues) {
        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(GlobalConstants.LONG_TIMEOUT));
        explicitWait.until(WebDriver -> ExpectedConditions.invisibilityOfElementLocated(byLocator(getDynamicXpath(locatorType, dynamicValues))));
    }

    /*
     * Wait for element undisplayed in Dom or not in Dom and override the implicit wait
     */
    public void waitForElementUndisplay(WebDriver driver, String locatorType) {
        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(GlobalConstants.SHORT_TIMEOUT));
        explicitWait.until(WebDriver -> ExpectedConditions.invisibilityOf(getWebElement(driver, locatorType)));
        overrideImplicitTimeout(driver, GlobalConstants.NORMAL_TIMEOUT);
    }

    public void waitForElementUndisplay(WebDriver driver, String xpath, String... dynamicValues) {
        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(GlobalConstants.SHORT_TIMEOUT));
        explicitWait.until(WebDriver -> ExpectedConditions.invisibilityOf(getWebElement(driver, getDynamicXpath(xpath, dynamicValues))));
        overrideImplicitTimeout(driver, GlobalConstants.NORMAL_TIMEOUT);
    }

    public void waitforAllElementsInvisible(WebDriver driver, String locatorType) {
        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(GlobalConstants.LONG_TIMEOUT));
        explicitWait.until(WebDriver -> ExpectedConditions.invisibilityOfAllElements(getListWebElement(driver, locatorType)));
    }

    public void waitforAllElementsInvisible(WebDriver driver, String locatorType, String... dynamicValues) {
        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(GlobalConstants.LONG_TIMEOUT));
        explicitWait.until(WebDriver -> ExpectedConditions.invisibilityOfAllElements(getListWebElement(driver, getDynamicXpath(locatorType, dynamicValues))));
    }

    public void waitForElementClickable(WebDriver driver, String locatorType, String... dynamicValues) {
        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(GlobalConstants.LONG_TIMEOUT));
        explicitWait.until(WebDriver -> ExpectedConditions.elementToBeClickable(byLocator(getDynamicXpath(locatorType, dynamicValues))));
    }

    public void waitForElementClickable(WebDriver driver, String locatorType) {
        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(GlobalConstants.LONG_TIMEOUT));
        explicitWait.until(WebDriver -> ExpectedConditions.elementToBeClickable(byLocator(getDynamicXpath(locatorType))));
    }

    public void waitForElementPresense(WebDriver driver, String locatorType) {
        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(GlobalConstants.LONG_TIMEOUT));
        explicitWait.until(WebDriver -> ExpectedConditions.presenceOfElementLocated(byLocator(locatorType)));
    }

    public void waitForElementPresense(WebDriver driver, String locatorType, String... dynamicValues) {
        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(GlobalConstants.LONG_TIMEOUT));
        explicitWait.until(WebDriver -> ExpectedConditions.presenceOfElementLocated(byLocator(getDynamicXpath(locatorType, dynamicValues))));
    }


    public void uploadMultipleFiles(WebDriver driver, String locatorType, String... fileNames) {
        //Đường dẫn tới thư mục uploadFiles: Windown/Mac/Linus
        String filePath = GlobalConstants.UPLOAD_FILE;

        //Đường dẫn của tất cả các files
        String fullFileName = "";

        for (String file : fileNames) {
            fullFileName = fullFileName + filePath + file + "\n";
        }
        fullFileName = fullFileName.trim();
        getWebElement(driver, locatorType).sendKeys(fullFileName);
    }

    public void overrideImplicitTimeout(WebDriver driver, long timeOut) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(GlobalConstants.VERY_SHORT_TIME));
    }

    public boolean isElementUndisplayed(WebDriver driver, String locatorType) {
        overrideImplicitTimeout(driver, GlobalConstants.VERY_SHORT_TIMEOUT);
        List<WebElement> elements = getListWebElement(driver, locatorType);
        overrideImplicitTimeout(driver, GlobalConstants.NORMAL_TIMEOUT);
        if (!elements.isEmpty() && !elements.get(0).isDisplayed()) {
            return true;
        } else return elements.isEmpty();
    }

    public boolean isElementUndisplayed(WebDriver driver, String xpath, String... dynamicValue) {
        overrideImplicitTimeout(driver, GlobalConstants.VERY_SHORT_TIMEOUT);
        List<WebElement> elements = getListWebElement(driver, getDynamicXpath(xpath, dynamicValue));
        overrideImplicitTimeout(driver, GlobalConstants.NORMAL_TIMEOUT);
        if (!elements.isEmpty() && !elements.get(0).isDisplayed()) {
            return true;
        } else return elements.isEmpty();
    }

    public Set<Cookie> getAllCookies(WebDriver driver) {
        return driver.manage().getCookies();
    }

    public void setCookies(WebDriver driver, Set<Cookie> cookies) {
        for (Cookie cookie : cookies) {
            driver.manage().addCookie(cookie);
        }
        refreshCurrentPage(driver);
    }

    public String getElementValueByJSXpath(WebDriver driver, String xpathLocator) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        xpathLocator = xpathLocator.replace("xpath=", "");
        return (String) jsExecutor.executeScript("return $(document.evaluate(\"" + xpathLocator + "\",document,null,XpathResult.FRIST_ORDERED_NODE_TYPE, null).singleNodeValue).val()");
    }

    public String getCurrentDate(String dateFormat) {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        return formatter.format(date);
    }

    public String getCurrentDateStringFormat() {
        String currentDate = getCurrentDate("MM/dd/yyyy");
        return currentDate.replace("/", "");
    }

    public boolean compareTwoArrayList(ArrayList<String> arrayList1, ArrayList<String> arrayList2) {
        return arrayList1.equals(arrayList2);
    }

    public String capitalizeFirstLetter(String inputValue) {
        // store each character to a char array
        String capValue;
        char[] charAray = inputValue.toCharArray();
        // for loop to capitalize first letter
        for (int i = 0; i < charAray.length; i++) {
            // capitalizing first letter of first word
            charAray[0] = Character.toUpperCase(charAray[0]);
            // loop to check if there is space between two letters
            if (charAray[i] == ' ') {
                // capitalizing first letter of rest of the word
                charAray[i + 1] = Character.toUpperCase(charAray[i + 1]);
            }
        }
        // converting the character array to the string
        return capValue = String.valueOf(charAray);
    }

    public String splitStringContainNumbers(String inputString, String companyFirstNum) {
        String getAllNumberInString = inputString.replaceAll("[^0-9]", "");
        if (String.valueOf(getAllNumberInString.charAt(0)).contentEquals(companyFirstNum)) {
            return inputString.substring(inputString.indexOf(String.valueOf(getAllNumberInString.charAt(0))));
        } else if (String.valueOf(getAllNumberInString.charAt(1)).contentEquals(companyFirstNum)) {
            return inputString.substring(inputString.indexOf(String.valueOf(getAllNumberInString.charAt(1))));
        } else {
            return inputString.substring(inputString.indexOf(String.valueOf(getAllNumberInString.charAt(2))));
        }
    }

    public String getNumberFromString(String inputString) {
        return inputString.replaceAll("[^0-9]", "");
    }

    public int generateRandomNumber(){
        Random random=new Random();
        return random.nextInt(10000);
    }


}

