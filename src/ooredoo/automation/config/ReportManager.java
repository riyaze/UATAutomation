package ooredoo.automation.config;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class ReportManager {
	ExtentReports report;
	ExtentTest test;

	public void CreateReportFile(String ReportfileName) {

		report = new ExtentReports(ReportfileName + ".html");

	}

	public void startTest(String testName) {

		test = report.startTest(testName);

	}

	public void pass(String imgPath, String Desc) {
		test.log(LogStatus.PASS, Desc);
	}

	public void fail(String imgPath, String Desc) {
		test.log(LogStatus.FAIL, test.addScreenCapture(imgPath) + Desc);
	}
	
	public void EndTest() {
		report.endTest(test);
		report.flush();
	}
}
