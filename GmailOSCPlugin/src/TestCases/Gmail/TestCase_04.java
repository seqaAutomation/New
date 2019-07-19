package TestCases.Gmail;

import java.util.Map;
import org.testng.annotations.Test;
import ProductLib.Common.CommonLib;
import ProductLib.Gmail.GmailLib;
import TestEngine.Base;

public class TestCase_04 extends Base {
	@Test
	public void testMethod() {
		report.info("Test Case : "+"072_070_400_010_R12Gmail_Verify that User is able to sync  appointments  from Gmail to Sales Cloud when upsynced appointment is updated in Gmail");
		GmailLib gmailLib = new GmailLib();
		CommonLib commonLib = new CommonLib();
		Map<String, String> testDataCommon = getTestData("commonTestData");
		Map<String, String> testData = getTestData("TestCase_04");
		Map<String, String> testDataPR01 = getTestData("PreReq_01");
		String saveSearch =  testDataCommon.get("filterName");
		String oscUserName = testDataCommon.get("oscUsername");
		String gmailPassword = testDataCommon.get("gmailPassword");
		String gmailUserName = testDataCommon.get("gmailUserName");
		String emailToselect = testData.get("emailToselect");
		String accName = testDataPR01.get("accName");
		String opptyName = testDataPR01.get("opptyName");
		String leadName = testDataPR01.get("leadName");
		String scheduleJobName = testDataCommon.get("scheduleJobName");

		String appName = saveSearch+testData.get("appointmentName");
		String url = fileUtility.getDataFromConfig("URL");
		report.info("Url : " + url);
		gmailLib.loginToGmail(emailToselect, oscUserName,url,gmailUserName,gmailPassword);
		driver().get("https://calendar.google.com");
		report.log(PASS, "Successfully opened Calendar");
//		report.captureScreenShot("");
		click("xGmailAppointementCreateBtn");
		click("xGmailAppMoreOptionBtn");
		clearText("xGmailAppTitle");
		type("xGmailAppTitle", appName);
		report.log(PASS, "Entered appointment title : " + appName);
		clearText("xGmailLocation");
		type("xGmailLocation", appName);
		report.log(PASS, "Entered appointment title : " + appName);
		click("xGmailAppSaveBtn");
		think(6000);
		String appValueXpath = replaceXpath("xGmailAppSelect", appName);
		report.log(PASS, "Appointment " + appName + "  created");
//		report.captureScreenShot("");
		click(appValueXpath);
		click("xGmailEditApp");
		think(6000);
		driver().switchTo().frame(getWebElement("xGmailExtnFrame"));
		while(isDisplayed("xGmailLoading"))
		{
			System.out.println("in loop 1");
			think(5000);
		}
		click("xGmailSaveBtn");
		while(isDisplayed("xGmailLoading"))
		{
			System.out.println("in loop 2");
			think(5000);
		}
		think(5000);
		
		if(getWebElement("xGmailShareAppBtn").getAttribute("class").contains("yellow"))
		{
			report.log(PASS, "Verify appointement shared from OSC plugin");
		}
		
		else
		{
			report.log(FAIL, "Verify appointement shared from OSC plugin");
		}
		driver().switchTo().parentFrame();
		click("xGmailAppSaveBtn");
		think(4000);
		if(!isDisplayed(appValueXpath))
		{
			driver().navigate().refresh();
		}
		click(appValueXpath);
		click("xGmailEditApp");
		think(6000);
		clearText("xGmailAppTitle");
		type("xGmailLocation","Updated");
		report.log(PASS, "Updated appointment title : " + appName + "Updated");
		type("xGmailAppTitle", appName + "Updated");
		report.log(PASS, "Updated appointment location : " + appName + "Updated");
		click("xGmailAppSaveBtn");
		report.captureScreenShot("Updated Appointement");
		driver().get(url);
		report.log(PASS, "Succefully opened : " + url);
		driver().get(url);
		String status = commonLib.scheduleESSJob(scheduleJobName);
		if(status.equalsIgnoreCase("error"))
		{
			think(60000);
			
			status = commonLib.scheduleESSJob(scheduleJobName);
			
		}
		
		if(status.equalsIgnoreCase("Succeeded"))
		{
			report.log(PASS, "Verify Schedule process");
		}
		
		else
		{
			report.log(FAIL, "Verify Schedule process");
		}
		commonLib.login("EBENES");
		commonLib.navigateToProduct("Activities");
		click("xActivitiesTab");
		click("xFuseSearchList");

		String listXpath = replaceXpath("xSaveSearchSelectList", saveSearch);
		click(listXpath);

		String fuseAppName = replaceXpath("xFuseAppSelectValue", appName + "Updated");

		if (getWebElement(fuseAppName).isDisplayed()) {
			report.log(PASS, "The changes for the appointment name are displayed properly in sales cloud");
//			report.captureScreenShot("");

			click(fuseAppName);
			String loc = getWebElement("xFuseLocation").getAttribute("value");

			if (loc.equalsIgnoreCase(appName + "Updated")) {
//				report.captureScreenShot("");
				report.log(PASS, "The changes for the appointment Location displayed properly in sales cloud");

			}

			else {
				report.log(FAIL, "The changes for the appointment Location not displayed properly in sales cloud");
			}

		}

		else {
			report.log(FAIL, "The changes for the appointment name not displayed properly in sales cloud");
		}
	}
}
