package Helper;

import java.util.ArrayList;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

import TestEngine.DriverFactory;
import TestEngine.ReportCreator;
import Utilities.FileUtilities;
import Utilities.Report;

public class HelperFunctions {
	public static FileUtilities fileUtility = new FileUtilities();
	public static String testName;
//	public static ExtentReports reports;
	public static Report report;
	public static String PASS = "PASS";
	public static String FAIL = "FAIL";
//	public static String filterName = "";
//	public static String reportFolder ="";
	public static ArrayList<String> reportPath = new ArrayList<String>();
	public static WebDriver driver() {
		return DriverFactory.getDriver();
	}

	public static ExtentTest logger() {
		return ReportCreator.getExtentTest();
	}

	public void navigateTo(String url) {
		DriverFactory.getDriver().get(url);
		think(2000);
		ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
			}
		};
		DriverFactory.getWait().until(pageLoadCondition);
	}

	public Boolean isDisplayed(String xpath) {
		Boolean val = false;

		try {
			System.out.println("Searching for : "+xpath);
			WebDriverWait wait1 = new WebDriverWait(driver(),15);
			WebElement element = getWebElement(xpath);
			wait1.until(ExpectedConditions.visibilityOf(element));
			val = element.isDisplayed();
			
		}

		catch (Exception e) {
			val = false;
		}
		return val;

	}
	
	public Boolean isAvailable(String xpath) {
		Boolean val = false;

		try {
			System.out.println("Searching for : "+xpath);
			WebDriverWait wait1 = new WebDriverWait(driver(),5);
			WebElement element = getWebElement(xpath);
			wait1.until(ExpectedConditions.visibilityOf(element));
			val = element.isDisplayed();
			
		}

		catch (Exception e) {
			val = false;
		}
		return val;

	}

	public String loadUrlInNewTab(String url) {
		ArrayList<String> tabsBefore = new ArrayList(driver().getWindowHandles());
		((JavascriptExecutor) driver()).executeScript("window.open('your url','_blank');");
		String currentWindow = driver().getWindowHandle();
		ArrayList<String> tabs = new ArrayList(driver().getWindowHandles());

		tabs.removeAll(tabsBefore);
		driver().switchTo().window(tabs.get(0));
		DriverFactory.getDriver().get(url);
		ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
			}
		};
		DriverFactory.getWait().until(pageLoadCondition);
		return currentWindow;
	}

	public void clearText(String xpath) {

		DriverFactory.getWait().until(ExpectedConditions.visibilityOfElementLocated((getXpath(xpath))));
		DriverFactory.getWait().until(ExpectedConditions.elementToBeClickable((getXpath(xpath))));
		String path = getXpathValue(xpath);
		think(1000);
		getWebElement(xpath).clear();
		think(1000);
		report.info("Cleared text in element " + path);
	}

//	public void click(String xpath) {
//		DriverFactory.getWait().until(ExpectedConditions.visibilityOfElementLocated((getXpath(xpath))));
//		DriverFactory.getWait().until(ExpectedConditions.elementToBeClickable((getXpath(xpath))));
//		getWebElement(xpath).click();
//		ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
//			public Boolean apply(WebDriver driver) {
//				return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
//			}
//		};
//		DriverFactory.getWait().until(pageLoadCondition);
//		think(3000);
//	}

	public void click(String xpath) {
		
		think(1000);
		DriverFactory.getWait().until(ExpectedConditions.presenceOfElementLocated((getXpath(xpath))));
		DriverFactory.getWait().until(ExpectedConditions.visibilityOfElementLocated((getXpath(xpath))));
		DriverFactory.getWait().until(ExpectedConditions.elementToBeClickable((getXpath(xpath))));
		Actions act = new Actions(driver());
		think(1000);
		act.moveToElement(getWebElement(xpath)).click().build().perform();
		ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
			}
		};
		DriverFactory.getWait().until(pageLoadCondition);
		String path = getXpathValue(xpath);
		think(3000);
		report.info("Clicked : " +path );
	}
	
public void clickInMail(String xpath) {
		
		think(1000);
		DriverFactory.getWait().until(ExpectedConditions.presenceOfElementLocated((getXpath(xpath))));
		DriverFactory.getWait().until(ExpectedConditions.visibilityOfElementLocated((getXpath(xpath))));
		DriverFactory.getWait().until(ExpectedConditions.elementToBeClickable((getXpath(xpath))));
		Actions act = new Actions(driver());
		think(1000);
		act.moveToElement(getWebElement(xpath)).click().build().perform();
		String path = getXpathValue(xpath);
		think(3000);
	}
	
	public void clickElement(String xpath) {
		DriverFactory.getWait().until(ExpectedConditions.presenceOfElementLocated((getXpath(xpath))));
		DriverFactory.getWait().until(ExpectedConditions.visibilityOfElementLocated((getXpath(xpath))));
		DriverFactory.getWait().until(ExpectedConditions.elementToBeClickable((getXpath(xpath))));
		think(1000);
		getWebElement(xpath).click();
		ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
			}
		};
		DriverFactory.getWait().until(pageLoadCondition);
		String path = getXpathValue(xpath);
		think(3000);
		report.info("Clicked : " +path );
	}

	public String replaceXpath(String xpath, String text) {

		String newXpath = getXpathValue(xpath).replace("%s", text);
		return newXpath;
	}

	public void type(String xpath, String value) {
		DriverFactory.getWait().until(ExpectedConditions.visibilityOfElementLocated((getXpath(xpath))));
		DriverFactory.getWait().until(ExpectedConditions.elementToBeClickable((getXpath(xpath))));
		getWebElement(xpath).sendKeys(value);
		think(1000);
		String path = getXpathValue(xpath);
		report.info("Typed value : " +value +" in element "+ path );
	}
	
	public void typeInMail(String xpath, String value) {
		DriverFactory.getWait().until(ExpectedConditions.visibilityOfElementLocated((getXpath(xpath))));
		DriverFactory.getWait().until(ExpectedConditions.elementToBeClickable((getXpath(xpath))));
		getWebElement(xpath).sendKeys(value);
		think(1000);
		String path = getXpathValue(xpath);
	}

	public WebElement getWebElement(String xpath) {
		return DriverFactory.getDriver().findElement(getXpath(xpath));

	}

	public By getXpath(String xpath) {
		String p = this.getClass().getPackage().toString().replace("TestCases.", "").replace("package ", "")
				.replace("ProductLib.", "");
		return By.xpath(fileUtility.xpath(p, xpath));

	}

	public String getXpathValue(String xpath) {
		String p = this.getClass().getPackage().toString().replace("TestCases.", "").replace("package ", "")
				.replace("ProductLib.", "");
		return (fileUtility.xpath(p, xpath));

	}

	public static void think(int milliSec) {
		try {
			Thread.sleep(milliSec);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public Map getTestData(String parameter) {
		String callerPathName = new Exception().getStackTrace()[1].getClassName();
		String callerClassName = new Exception().getStackTrace()[1].getFileName().replace(".java", "");
		String packageName = callerPathName.replace("TestCases.", "").replace("." + callerClassName, "")
				.replace("ProductLib.", "");
		System.out.println(packageName);
		return fileUtility.getTestData(parameter, packageName);

	}
}
