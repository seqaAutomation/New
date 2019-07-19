package Utilities;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.LogStatus;

import TestEngine.DriverFactory;
import TestEngine.ReportCreator;

public class Report {
	
	public void log(String status, String expectedResult) {
		if (status.equalsIgnoreCase("PASS")) {
			System.out.println(expectedResult);
			ReportCreator.getExtentTest().log(LogStatus.PASS,expectedResult);
		}

		else if (status.equalsIgnoreCase("FAIL")) {
			System.out.println(expectedResult);
			ReportCreator.getExtentTest().log(LogStatus.FAIL,
					ReportCreator.getExtentTest().addBase64ScreenShot(capture(DriverFactory.getDriver()))
							+ expectedResult);
			
		}
	}
	
	public void info(String expectedResult) {
			System.out.println(expectedResult);
			ReportCreator.getExtentTest().log(LogStatus.INFO,expectedResult);
		}

	public void captureScreenShot(String info) {
		try {
		Thread.sleep(3000);
		
			ReportCreator.getExtentTest().log(LogStatus.INFO,
					ReportCreator.getExtentTest().addBase64ScreenShot(capture(DriverFactory.getDriver())) + info);
		} catch (Exception e) {
		}
	}

	/* Commented below method to change it to base64 conversion*/
	
	public static String captureImage(WebDriver driver) {
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		File Dest = new File("./Results/" + System.currentTimeMillis() + ".png");
		String errflpath = Dest.getAbsolutePath();
		try {
			FileUtils.copyFile(scrFile, Dest);
		} catch (Exception e) {
			System.out.println("Exception in capture");
		}
		return errflpath;

	}
	
	public static String capture(WebDriver driver){
	     TakesScreenshot newScreen = (TakesScreenshot) driver;
	     String scnShot = newScreen.getScreenshotAs(OutputType.BASE64);
	     return "data:image/jpg;base64, " + scnShot ;
	}
}
