package TestEngine;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import ProductLib.Gmail.GmailLib;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

public class Listener extends Base implements ITestListener{
	public void onTestStart(ITestResult result) 
	{
//		logger = reports.createTest(testName); 
//		String testName = this.testName;
//		ExtentTest looger = ReportCreator.getInstance().initialValue(testName);
//		ReportCreator.setExtentTest(looger);
	}
	public void onTestSuccess(ITestResult result) {
		report.log(PASS, "Completed Test Execution");
		// TODO Auto-generated method stub
		
	}
	public void onTestFailure(ITestResult result) {
		
		report.log("FAIL", result.getThrowable().toString());
		
		// TODO Auto-generated method stub
		
	}
	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		
	}
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}
	public void onStart(ITestContext context) 
	{
		// TODO Auto-generated method stub
//		String reportFolder = LocalDateTime.now().format(DateTimeFormatter.ofPattern("ddMMMYY_HHmmss"));
//		reports = new ExtentReports("./Reports/"+reportFolder+".html");
		
	}
	public void onFinish(ITestContext context) 
	{
		
		// TODO Auto-generated method stub
//		System.out.println("out Listener");
//		reports.flush();
		
	}
}
