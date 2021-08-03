package ooredoo.automation.uat;

import java.io.IOException;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.LogStatus;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import ooredoo.automation.config.ExecuteEntity;
import ooredoo.automation.config.ExtentManager;
import ooredoo.automation.config.ExtentTestManager;
import ooredoo.automation.config.LoadProperties;
import ooredoo.automation.config.ReportManager;
import ooredoo.automation.config.TestCaseEntity;
import ooredoo.automation.engine.ExecuteTest;
import ooredoo.automation.utils.ReadExecution;
import ooredoo.automation.utils.ReadTestCase;

public class AutomationMain {
	Properties p;
	List<ExecuteEntity> exelist;
	List<TestCaseEntity> finalTestCaselist;
	ReportManager reportManager;
	AppiumDriver<WebElement> driver;
	ReadExecution readexecution;
	ReadTestCase readtestcase;
	ExecuteTest executetest;
	LoadProperties loadprops;

	@Test
	@Parameters({ "deviceName", "platformeVersion", "platformName", "appPackage", "appActivity", "udid",
			"automationName", "noReset", "systemPort", "appiumServer", "wait", "os" })
	public void RunTestCases(String deviceName, String platformeVersion, String platformName, String appPackage,
			String appActivity, String udid, String automationName, String noReset, String systemPort,
			String appiumServer, long wait, String os) throws IOException {

		// Initialize Excel read and Report Manager
		reportManager = new ReportManager();
		readexecution = new ReadExecution();
		readtestcase = new ReadTestCase();
		executetest = new ExecuteTest();
		loadprops = new LoadProperties();
		// Create Report File and Load properties
		reportManager.CreateReportFile(System.currentTimeMillis() + "uatautomationreport");
		reportManager.startTest("Test_with_" + deviceName + "_" + os);
		//ExtentTestManager.startTest("Demo Parallel", "Test_with_" + deviceName + "_" + os);
		//ExtentTestManager.getTest().log(LogStatus.PASS, "Demo Pass "+deviceName);
		p = loadprops.getProperties("Objects.properties");

		// Read Execution Page in Excel file
		exelist = readexecution.getExecuteList(p.getProperty("Excelpath"), p.getProperty("ExecutesheetName"));
		finalTestCaselist = new ArrayList<>();

		// Loop to get Main Execute Sheet to read TestCase sheet in excel file
		for (int i = 0; i < exelist.size(); i++) {

			if (exelist.get(i).getTS_Exe_Flag().equalsIgnoreCase("Y")) {
				finalTestCaselist
						.addAll(readtestcase.getTestCases(p.getProperty("Excelpath"), exelist.get(i).getTS_Fun_Name()));
			}
		}

		// Check the Device Config and set Caps accordingly
		if (os.equalsIgnoreCase("Android")) {
			DesiredCapabilities cap = new DesiredCapabilities();
			cap.setCapability("deviceName", deviceName);
			cap.setCapability("platformVersion", platformeVersion);
			cap.setCapability("platformName", platformName);
			cap.setCapability("appPackage", appPackage);
			cap.setCapability("appActivity", appActivity);
			cap.setCapability("udid", udid);
			cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, automationName);
			cap.setCapability(MobileCapabilityType.NO_RESET, noReset);
			cap.setCapability(AndroidMobileCapabilityType.SYSTEM_PORT, systemPort);
			driver = new AndroidDriver(new URL(appiumServer), cap);
			driver.manage().timeouts().implicitlyWait(wait, TimeUnit.SECONDS);
		}

		else {

		}

       // Execute the Test after reading the Excel File
		for (int i = 0; i < finalTestCaselist.size(); i++) {
			executetest.executeTest(finalTestCaselist.get(i).getTC_KeyWord(), finalTestCaselist.get(i).getTC_Object(),
					finalTestCaselist.get(i).getTC_Data(), driver, p, reportManager,
					finalTestCaselist.get(i).getTC_Desc());
			System.out.println(finalTestCaselist.get(i).getTC_Desc());
		}
       
		
		
	  // Close Test to Generate the Report	
		reportManager.EndTest();
		//ExtentTestManager.endTest();
		//ExtentManager.getReporter().flush();
	}
}
