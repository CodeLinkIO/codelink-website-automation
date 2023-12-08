package core.reportConfig;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import java.io.BufferedOutputStream;
import java.util.HashMap;
import java.util.Map;

public class ExtentTestManager {
    public static BufferedOutputStream extentReports;
    static Map<Integer, ExtentTest> extentTestMap = new HashMap<Integer, ExtentTest>();
    static ExtentReports extent = ExtentManager.createExtentReports();

    public static synchronized ExtentTest getTest() {
        return extentTestMap.get((int) Thread.currentThread().getId());
    }

    public static void logInfo(String info){
        getTest().log(Status.INFO, info);
    }

    public static synchronized void startTest(String testName, String desc) {
        ExtentTest test = extent.createTest(testName, desc);
        extentTestMap.put((int) Thread.currentThread().getId(), test);
    }

}
