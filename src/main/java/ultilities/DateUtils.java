package ultilities;

import keywords.WebUI;
import org.jboss.aerogear.security.otp.api.Clock;
import org.openqa.selenium.By;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class DateUtils extends Clock {
    public DateUtils() {
        super();
    }

    public static String getCurrentDate() {
        Date date = new Date();
        return date.toString().replace(":", "_").replace(" ", "_");
    }

    private final int interval = 30;
    private Calendar calendar;

    public long getCurrentInterval() {
        this.calendar = GregorianCalendar.getInstance(TimeZone.getTimeZone("UTC-08:00"));
        long currentTimeSeconds = this.calendar.getTimeInMillis() / 1000L;
        return currentTimeSeconds / (long) this.interval;
    }

    /**
     * @return dd/MM/yyyy HH:mm:ss
     */
    public static String getCurrentDateTime() {
        Date now = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return formatter.format(now);
    }

    /**
     * @return future date in requested format and days in advance
     */
    public static String getFutureDate(int furtureDays, String format) {
        Date futureDate = DataFakerUtils.getDataFaker().date().future(furtureDays, TimeUnit.DAYS);
        // Generate a future date within the next year
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(futureDate);
    }

    public static String getCurrentDateTimeCustom(String customFormat) {
        // Get the current date and time in the system default time zone
        ZonedDateTime currentDateTime = ZonedDateTime.now().withZoneSameInstant(java.time.ZoneOffset.UTC);
        // Format the date and time using the custom format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(customFormat);
        return currentDateTime.format(formatter);
    }

    public static String getCurrentDateTimeCustom(String customFormat, int min, String minus) {
        // Get the current date and time
        ZonedDateTime currentDateTime;
        if (minus.equalsIgnoreCase("days")) {
            currentDateTime = ZonedDateTime.now().withZoneSameInstant(java.time.ZoneOffset.UTC).minusDays(min);
        } else {
            currentDateTime = ZonedDateTime.now().withZoneSameInstant(java.time.ZoneOffset.UTC).minusHours(min);
        }
        // Format the date and time using the custom format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(customFormat);
        return currentDateTime.format(formatter);
    }

    public static String getCurrentDateTimeCustomplus(String customFormat, int min, String plus) {
        // Get the current date and time
        ZonedDateTime currentDateTime;
        if (plus.equalsIgnoreCase("days")) {
            currentDateTime = ZonedDateTime.now().withZoneSameInstant(java.time.ZoneOffset.UTC).plusDays(min);
        } else {
            currentDateTime = ZonedDateTime.now().withZoneSameInstant(java.time.ZoneOffset.UTC).plusHours(min);
        }
        // Format the date and time using the custom format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(customFormat);
        return currentDateTime.format(formatter);
    }

    /**
     * This function can add and delete the years/month/days from current date
     */
    public static String getReferenceDateTimeCustom(String customFormat, int year, int month, int days) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime updateDate = currentDateTime.plusYears(year).plusMonths(month).plusDays(days);
        // Format the date and time using the custom format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(customFormat);
        return updateDate.format(formatter);
    }

    /**
     * Get the nearest day of week like nearest Sunday
     */
    public static int getPreviousDateDiffWithDay(LocalDate date, DayOfWeek day) {
        int dayDiff = 0;
        LocalDate updateDate = date.with(TemporalAdjusters.previous(day));
        LocalDate today = LocalDate.now();
        System.out.println("Previous sunday of date (" + date.toString() + ") is    " + updateDate.toString());
        dayDiff = (int) Duration.between(today.atStartOfDay(), updateDate.atStartOfDay()).toDays();
        return dayDiff;
    }

    /**
     * Get a Date from given reference date by addition or minus of year/month/days     * @param formattedDate     * @param format     * @param year     * @param month     * @param days     * @return
     */
    public static String getDateFromAReferenceDate(String formattedDate, String format, int year, int month, int days) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        LocalDate date = LocalDate.parse(formattedDate, formatter);
        date = date.plusYears(year).plusMonths(month).plusDays(days);
        return date.format(formatter);
    }

    /**
     * Gate Difference of days between given dates with specified format
     */
    public static int getDaysDiff(String formattedDate1, String formattedDate2, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        LocalDate date1 = LocalDate.parse(formattedDate1, formatter);
        LocalDate date2 = LocalDate.parse(formattedDate2, formatter);
        return (int) Duration.between(date1.atStartOfDay(), date2.atStartOfDay()).toDays();
    }

    /**
     * Get a formatted date with given source format to desired format
     */
    public static String getFormattedDate(String dateStr, String sourceFormat, String desiredFormat) {
        DateTimeFormatter sourceFormatter = DateTimeFormatter.ofPattern(sourceFormat);
        DateTimeFormatter desiredFormatter = DateTimeFormatter.ofPattern(desiredFormat);
        LocalDate date = LocalDate.parse(dateStr, sourceFormatter);
        String desiredDateFormat = date.format(desiredFormatter);
        return desiredDateFormat;
    }

    /**
     * Clear the date field
     */
    public static void clearDate(String locatorXpathDate) {
        By dateInputClearButton = By.xpath(locatorXpathDate + "//following-sibling::div//button[text()='Clear']");
        String dateIconDynamicXpath = locatorXpathDate + "//following-sibling::i";
        By calIcon = By.xpath(dateIconDynamicXpath);
        WebUI.scrollToElementAtBottom(calIcon);
        if (!WebUI.getAttributeElement(By.xpath(locatorXpathDate), "value").isBlank()) {
            WebUI.clickElement(calIcon);
            WebUI.clickElement(dateInputClearButton);
        }
        return;
    }

    /**
     * Select a date from date picker where input and output format are given
     */
    public static void selectDateFromPicker(String locatorXpathDate, String date, String UIDateInputFormat, String UIDateDisplayFormat) {
        String dateSelectorDynamicXpath = "//span[(@class='ng-binding' or @class='ng-binding text-info') and text()='%s']";
        String dateInputDynamicXpath = locatorXpathDate;
        String dateIconDynamicXpath = dateInputDynamicXpath + "//following-sibling::i";
        By monthYearButton = By.xpath("//button[starts-with(@id,'datepicker-')]");
        By lefNav = By.xpath("//button[@class='btn btn-default btn-sm pull-left uib-left']");
        By rightNav = By.xpath("//button[@class='btn btn-default btn-sm pull-right uib-right']");
        By calIcon = By.xpath(dateIconDynamicXpath);
        By dateInput = By.xpath(dateInputDynamicXpath);
        String targetYear = DateUtils.getFormattedDate(date, UIDateInputFormat, "yyyy");
        String targetMonth = DateUtils.getFormattedDate(date, UIDateInputFormat, "MMMM");
        String targetDay = DateUtils.getFormattedDate(date, UIDateInputFormat, "dd");
        WebUI.scrollToElementAtBottom(calIcon);
        WebUI.clickElement(calIcon);
        String currentButtonText = WebUI.getTextElement(monthYearButton);
        String currentYear = currentButtonText.substring(currentButtonText.indexOf(' ') + 1).trim();
        String currentMonth = currentButtonText.substring(0, currentButtonText.lastIndexOf(' ')).trim();
        YearMonth currentYearMonth = YearMonth.of(Integer.parseInt(currentYear), getMonth(currentMonth));
        YearMonth targetYearMonth = YearMonth.of(Integer.parseInt(targetYear), getMonth(targetMonth));
        while (targetYearMonth.compareTo(currentYearMonth) != 0) {
            if (targetYearMonth.compareTo(currentYearMonth) > 0) WebUI.clickElement(rightNav);
            else WebUI.clickElement(lefNav);
            currentButtonText = WebUI.getTextElement(monthYearButton);
            currentYear = currentButtonText.substring(currentButtonText.indexOf(' ') + 1).trim();
            currentMonth = currentButtonText.substring(0, currentButtonText.lastIndexOf(' ')).trim();
            currentYearMonth = YearMonth.of(Integer.parseInt(currentYear), getMonth(currentMonth));
        }
        By dateSelector = By.xpath(String.format(dateSelectorDynamicXpath, targetDay));
        WebUI.clickElementWithJs(dateSelector);
    }

    /**
     * Get a month Enum from given String
     */
    public static Month getMonth(String monthStr) {
        Month month = Month.JANUARY;
        if (monthStr.equalsIgnoreCase("JANUARY")) month = Month.JANUARY;
        else if (monthStr.equalsIgnoreCase("FEBRUARY")) month = Month.FEBRUARY;
        else if (monthStr.equalsIgnoreCase("MARCH")) month = Month.MARCH;
        else if (monthStr.equalsIgnoreCase("APRIL")) month = Month.APRIL;
        else if (monthStr.equalsIgnoreCase("MAY")) month = Month.MAY;
        else if (monthStr.equalsIgnoreCase("JUNE")) month = Month.JUNE;
        else if (monthStr.equalsIgnoreCase("JULY")) month = Month.JULY;
        else if (monthStr.equalsIgnoreCase("AUGUST")) month = Month.AUGUST;
        else if (monthStr.equalsIgnoreCase("SEPTEMBER")) month = Month.SEPTEMBER;
        else if (monthStr.equalsIgnoreCase("OCTOBER")) month = Month.OCTOBER;
        else if (monthStr.equalsIgnoreCase("NOVEMBER")) month = Month.NOVEMBER;
        else if (monthStr.equalsIgnoreCase("DECEMBER")) month = Month.DECEMBER;
        return month;
    }

    /**
     * check if given date string is in custom format or not
     */
    public static boolean verifyDateFormat(String customFormat, String dateStr) {
        // Format the date and time using the custom format
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(customFormat);
            LocalDate date = LocalDate.parse(dateStr, formatter);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }

    /**
     * Getting current epoch time
     */
    public static long getCurrentEpochTime() {
        Date now = new Date();
        return now.getTime();
    }

    /**
     * Converting date from one format to another
     */
    public static String getConvertedDate(String inputFormat, String outputFormat, String inputDateStr) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern(inputFormat);
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern(outputFormat);
        LocalDate date = LocalDate.parse(inputDateStr, inputFormatter);
        return date.format(outputFormatter);
    }
}
