package TestEngine;

import java.io.File;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import Utilities.FileUtilities;

public class DriverFactory extends Base
{
	private static DriverFactory instance = new DriverFactory();
	static ThreadLocal<WebDriver> ldriver = new ThreadLocal<WebDriver>();

	private DriverFactory() 
		{
			
		}

	public static DriverFactory getInstance() 
		{
			return instance;
		}


	public static WebDriver invokeBrowser() 
		{
		String browser = FileUtilities.getDataFromConfig("Browser");
		WebDriver driver = null;
		  String url = FileUtilities.getDataFromConfig("URL");
		  if(browser.equalsIgnoreCase("Chrome"))
		  {
			  System.setProperty("webdriver.chrome.driver", "./Resources/chromedriver.exe");

			  ChromeOptions options = new ChromeOptions();
			  options.addExtensions(new File("./Resources/Gmail.crx"));
			  
			  
//			  	options.addArguments("start-maximized"); // https://stackoverflow.com/a/26283818/1689770
//		        options.addArguments("enable-automation"); // https://stackoverflow.com/a/43840128/1689770
//		        options.addArguments("--headless"); // only if you are ACTUALLY running headless
//		        options.addArguments("--no-sandbox"); //https://stackoverflow.com/a/50725918/1689770
//		        options.addArguments("--disable-infobars"); //https://stackoverflow.com/a/43840128/1689770
//		        options.addArguments("--disable-dev-shm-usage"); //https://stackoverflow.com/a/50725918/1689770
//		        options.addArguments("--disable-browser-side-navigation"); //https://stackoverflow.com/a/49123152/1689770
//		        options.addArguments("--disable-gpu");
			  
			  
			  DesiredCapabilities cap = DesiredCapabilities.chrome();
			  cap.setCapability(ChromeOptions.CAPABILITY, options);
			  driver = new ChromeDriver(cap);
			 
		  }
		  
		  else if(browser.equalsIgnoreCase("FireFox"))
		  {
			  
		  }
		  
		  else
		  {
			  
		  }
		  
		  driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		  driver.manage().timeouts().pageLoadTimeout(120, TimeUnit.SECONDS);
		  driver.manage().window().maximize();
		  setWait(driver);
//		  driver.get(url);
//		  ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
//				public Boolean apply(WebDriver driver) {
//					return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
//				}
//			};
//			DriverFactory.getWait().until(pageLoadCondition);
			return driver;
		
		}

	public static WebDriver getDriver() 
		{
			return ldriver.get();
		}

	public static void setDriver(WebDriver driver) 
		{
			ldriver.set(driver);
		}

	public void removeDriver() 
		{
			ldriver.get().quit();
			ldriver.remove();
		}
	
static ThreadLocal<WebDriverWait> waitThread = new ThreadLocal<WebDriverWait>();
	
	
	public static WebDriverWait getWait() {

		return waitThread.get();
	}

	public static void setWait(WebDriver driver) {

		waitThread.set(new WebDriverWait(driver,120));
	}

	public void removeWait() {
		waitThread.remove();
	}
	
}
