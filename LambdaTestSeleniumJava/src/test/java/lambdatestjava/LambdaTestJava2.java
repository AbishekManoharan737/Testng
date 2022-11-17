package lambdatestjava;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class LambdaTestJava2 {
	String username = "username";
	String accesskey = "password";
	public static RemoteWebDriver driver = null;
	public String gridURL = "@hub.lambdatest.com/wd/hub";
	public String browserName;
	public WebDriverWait explicitWait;
	boolean status = false;

	@BeforeClass
	@Parameters(value = { "browser", "version", "platform" })
	public void setUp(String browser, String version, String platform) throws Exception {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("browserName", browser);
		capabilities.setCapability("version", version);
		capabilities.setCapability("platform", platform); // If this cap isn't specified, it will just get the any
															// available one
		capabilities.setCapability("build", "Test Scenario 2");
		capabilities.setCapability("name", "Single Checkbox Demo");
		capabilities.setCapability("network", true); // To enable network logs
		capabilities.setCapability("visual", true); // To enable step by step screenshot
		capabilities.setCapability("video", true); // To enable video recording
		capabilities.setCapability("console", true); // To capture console logs

		try {
			driver = new RemoteWebDriver(new URL("https://" + username + ":" + accesskey + gridURL), capabilities);
			driver.get("https://www.lambdatest.com/selenium-playground");
			browserName = browser;
			explicitWait=new WebDriverWait(driver,Duration.ofSeconds(40));
			
			
		} catch (MalformedURLException e) {
			System.out.println("Invalid grid URL");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Test(timeOut = 20000)
	public void testScenario2() throws Exception {
		try {
			
			new WebDriverWait(driver,Duration.ofSeconds(40)).until(ExpectedConditions.elementToBeClickable(driver.findElement(By.partialLinkText("Checkbox Demo"))));
			driver.findElement(By.partialLinkText("Checkbox Demo")).click();
			JavascriptExecutor jsExecutor=(JavascriptExecutor) driver;
			jsExecutor.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.id("isAgeSelected")));		
			jsExecutor.executeScript("arguments[0].click();",driver.findElement(By.id("isAgeSelected")));
			Assert.assertTrue(driver.findElement(By.id("isAgeSelected")).isSelected());
			jsExecutor.executeScript("arguments[0].click();",driver.findElement(By.id("isAgeSelected")));
			Assert.assertFalse(driver.findElement(By.id("isAgeSelected")).isSelected());
		
			

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
	}

	@AfterClass
	public void tearDown() throws Exception {
		if (driver != null) {
			((JavascriptExecutor) driver).executeScript("lambda-status=passed");
			driver.quit();
		}
	}
}
