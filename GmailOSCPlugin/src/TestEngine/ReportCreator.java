package TestEngine;

import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

import Utilities.Report;


public class ReportCreator extends Base{
	private static ReportCreator instance = new ReportCreator();

	private ReportCreator() {
	}

	public static ReportCreator getInstance() {
		return instance;
	}

	static ThreadLocal<ExtentTest> llogger = new ThreadLocal<ExtentTest>();
	static ThreadLocal<String> folder = new ThreadLocal<String>();
	
	public static void setFolderName(String name) {

		folder.set(name);
	}
	
	public static String getFolderName() {

		return folder.get();
	}
	
	public static void removeFolderName() {

		folder.remove();
	}
	
static ThreadLocal<ExtentReports> reportL = new ThreadLocal<ExtentReports>();
	
	public static void setReports(ExtentReports report) {

		reportL.set(report);
	}
	
	public static ExtentReports getReports() {

		return reportL.get();
	}
	
	public static void removeReports() {

		reportL.remove();
	}
	
	public static ExtentTest initialValue(String testName) {
		return ReportCreator.getReports().startTest(testName);

	}

	public static ExtentTest getExtentTest() {

		return llogger.get();
	}

	public static void setExtentTest(ExtentTest logger) {

		llogger.set(logger);
	}

	public void removeExtentTest() {
		llogger.remove();
	}
	
	
	
	
	static ThreadLocal<String> ltestName = new ThreadLocal<String>();
	
	
	public static String getTestName() {

		return ltestName.get();
	}

	public static void setTestName(String name) {

		ltestName.set(name);
	}

	public void removeTestNameTest() {
		ltestName.remove();
	}
}
