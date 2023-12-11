package reportConfig;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import common.GlobalConstants;

public class ExtentManager {
    static final ExtentReports extentReports = new ExtentReports();
    public synchronized static ExtentReports createExtentReports() {
        ExtentSparkReporter reporter = new ExtentSparkReporter("reports/extentreport/extentreport.html");
        reporter.config().setReportName("Codelink_Website Report");
        reporter.config().setDocumentTitle("Codelink_Website Automation Report");
        reporter.config().setTimeStampFormat("EEEE, dd MMMM, yyyy, hh:mm a '('zzz')'");
        reporter.config().setTimelineEnabled(true);
        reporter.config().setEncoding("utf-8");
        reporter.config().setTheme(Theme.STANDARD);

        extentReports.attachReporter(reporter);
        extentReports.setSystemInfo("Project", "Codelink_Website");
        extentReports.setSystemInfo("Author", "Phuong Mai");
        extentReports.setSystemInfo("JDK Version", GlobalConstants.JAVA_VERSION);
        return extentReports;
    }
}
