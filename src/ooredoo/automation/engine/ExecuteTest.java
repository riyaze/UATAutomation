package ooredoo.automation.engine;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import ooredoo.automation.config.ReportManager;

public class ExecuteTest {

	public  void executeTest(String keyword, String object, String data, AppiumDriver<WebElement> driver,
			Properties p, ReportManager reportManager, String Tc_Desc) throws IOException {

		switch (keyword.trim()) {

		case "CLICK_BY_TEXT":
			try {

				//System.out.println("called CLICK_BY_TEXT");
				driver.findElement(By.xpath(p.getProperty(object))).click();
				reportManager.pass(null, Tc_Desc);
			}

			catch (Exception e) {
				reportManager.fail(capture(driver), Tc_Desc);
				e.printStackTrace();
			}

			break;

		case "CLICK_BY_ID":
			try {

				//System.out.println("called CLICK_BY_ID");
				driver.findElement(By.id(p.getProperty(object))).click();
				reportManager.pass(null, Tc_Desc);
			}

			catch (Exception e) {
				reportManager.fail(capture(driver), Tc_Desc);
				e.printStackTrace();
			}

			break;

		case "TYPE_TEXT_XPATH":
			try {
				//System.out.println("called TYPE_TEXT");
				driver.findElement(By.xpath(p.getProperty(object))).sendKeys(data);
				reportManager.pass(null, Tc_Desc);
			} catch (Exception e) {
				reportManager.fail(capture(driver), Tc_Desc);
				e.printStackTrace();
			}
			break;
		case "TYPE_TEXT_ID":
			try {
				//System.out.println("called TYPE_TEXT");
				driver.findElement(By.id(p.getProperty(object))).sendKeys(data);
				reportManager.pass(null, Tc_Desc);
			} catch (Exception e) {
				reportManager.fail(capture(driver), Tc_Desc);
				e.printStackTrace();
			}
			break;
		case "QUIT_TEST":
			try {
				//System.out.println("called QUIT_TEST");
				reportManager.pass(null, Tc_Desc);
				driver.quit();
			} catch (Exception e) {
				reportManager.fail(capture(driver), Tc_Desc);
				e.printStackTrace();
			}
			break;
		case "PAUSE":

			try {
				Thread.sleep(Integer.valueOf(data));
				reportManager.pass(null, Tc_Desc);
			}

			catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case "VERTICAL_SCROLL":
			try {
				Dimension dimensions = driver.manage().window().getSize();
				int pressX = dimensions.width / 2;
				// 4/5 of the screen as the bottom finger-press point
				int startpoint = (int) (dimensions.getHeight() * 0.50);
				// just non zero point, as it didn't scroll to zero normally
				int endpoint = (int) (dimensions.getHeight() * 0.20);
				// scroll with TouchAction by itself
				TouchAction touchAction = new TouchAction(driver);
				touchAction.press(PointOption.point(pressX, startpoint))
						.waitAction(WaitOptions.waitOptions(Duration.ofMillis(2000)))
						.moveTo(PointOption.point(pressX, endpoint)).release().perform();
				reportManager.pass(null, Tc_Desc);
			} catch (Exception e) {
				reportManager.fail(capture(driver), Tc_Desc);
				e.printStackTrace();
			}
			break;

		case "HORIZONTAL_SCROLL":
			try {
				Dimension dimensions = driver.manage().window().getSize();
				int pressX = dimensions.height / 2;
				// 4/5 of the screen as the bottom finger-press point
				int startpoint = (int) (dimensions.getWidth() * 0.50);
				// just non zero point, as it didn't scroll to zero normally
				int endpoint = (int) (dimensions.getWidth() * 0.20);
				// scroll with TouchAction by itself
				TouchAction touchAction = new TouchAction(driver);
				touchAction.press(PointOption.point(startpoint, pressX))
						.waitAction(WaitOptions.waitOptions(Duration.ofMillis(2000)))
						.moveTo(PointOption.point(endpoint, pressX)).release().perform();
				reportManager.pass(null, Tc_Desc);
			}

			catch (Exception e) {
				reportManager.fail(capture(driver), Tc_Desc);
				e.printStackTrace();
			}
			break;
		case "TOUCH_BY_COORDINATES":
			try {
				TouchAction action = new TouchAction(driver);
				//System.out.println("Touch action cooordinates " + data);
				String[] list = data.split(",");

				action.tap(PointOption.point(819, 784)).perform();
				reportManager.pass(null, Tc_Desc);
			}

			catch (Exception e) {
				reportManager.fail(capture(driver), Tc_Desc);
				e.printStackTrace();
			}
			break;
			
			
		default:
			//System.out.println("Default called");
		}
	}

	public  String capture(WebDriver driver) throws IOException {
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		File theDir = new File("Screenshots");
		if (!theDir.exists()) {
			theDir.mkdirs();
		}
		File Dest = new File(theDir.getPath() + "//" + System.currentTimeMillis() + ".png");
		String errflpath = Dest.getAbsolutePath();
		FileUtils.copyFile(scrFile, Dest);
		return errflpath;
	}
}
