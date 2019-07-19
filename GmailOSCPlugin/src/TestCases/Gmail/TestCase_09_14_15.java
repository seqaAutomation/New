package TestCases.Gmail;

import java.util.Map;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import ProductLib.Common.CommonLib;
import ProductLib.Gmail.GmailLib;
import TestEngine.Base;
import TestEngine.ReportCreator;

public class TestCase_09_14_15 extends Base {
	@Test
	public void testMethod() {
		report.info("Test Cases : \n"
				+ "072_070_400_060_R12Gmail_Verify that user is able to create a Weekly recurring event in Gmail and sync to Sales cloud.\n"
				+ "072_070_400_160_R12Gmail_Verify that user is able to add account_opty_lead to the appointment in Gmail and upsync to OSC.\n"
				+ "072_070_400_240_R12Gmail_Verify that User is able to sync  Recurring appointments  from Gmail to Sales Cloud when Upsynced recurring appointment is updated in Gmail");
		GmailLib gmailLib = new GmailLib();
		CommonLib commonLib = new CommonLib();
		Map<String, String> testDataCommon = getTestData("commonTestData");
		Map<String, String> testData = getTestData("TestCase_09_14_15");
		Map<String, String> testDataPR01 = getTestData("PreReq_01");
		String oscUserName = testDataCommon.get("oscUsername");
//		String saveSearch = testDataPreReq.get("filterName");
		String saveSearch =  testDataCommon.get("filterName");
		String gmailPassword = testDataCommon.get("gmailPassword");
		String gmailUserName = testDataCommon.get("gmailUserName");
		String emailToselect = testData.get("emailToselect");
		String accName = testDataPR01.get("accName");
		String opptyName = testDataPR01.get("opptyName");
		String leadName = testDataPR01.get("leadName");
		String scheduleJobName = testDataCommon.get("scheduleJobName");
		String url = fileUtility.getDataFromConfig("URL");
		report.info("Url : " + url);
		report.info("Step_1 : Login to Gmail OSC extension");
		report.info("Step_2 : Open to Google Calendar in new tab");
		report.info("Step_3 : Create a reccurring appointmnent");
		report.info(
				"Step_4 : Edit the appointment and verify share appointment is displayed in grey color in OSC panel");
		report.info("Step_5 : Add account, oppty and lead to the appointment");
		report.info(
				"Step_6 : Share the appointment and verify share appointment is displayed in yellow color in OSC panel after sharing");
		report.info("Step_7 : Verify the shared appointment details synced to sales cloud successfully");
		report.info("Step_8 : Open to Google Calendar");
		report.info("Step_9 : Edit the previously created appointment");
		report.info("Step_10 : Run ESS job by logging in as \"Sales_Admin\"");
		report.info("Step_11 : Login to sales cloud");
		report.info("Step_12 : Verify changes in appointment are successfully synced to Sales cloud");
		report.info("Step_1");
		String appName = saveSearch+testData.get("appointmentName");
		report.info("");
		gmailLib.loginToGmail(emailToselect, oscUserName, url, gmailUserName, gmailPassword);
		report.info("Step_2");
		think(5000);

		commonLib.loadUrlInNewTab("https://calendar.google.com");
		think(3000);
//		report.captureScreenShot("Calendar opened in Googl accounts");
		report.info("Step_3");
		click("xGmailAppointementCreateBtn");
		click("xGmailAppMoreOptionBtn");
		clearText("xGmailAppTitle");
		type("xGmailAppTitle", appName);
		report.log(PASS, "Appointment tile entered : " + appName);
		click("xGmailOccurrenceSelect");
		click("xGmailWeekSelection");
		click("xGmailAfterRadioBtn");
		clearText("xGmailOccurenceCount");
		type("xGmailOccurenceCount", "3");
//		report.captureScreenShot("Reccurring appointmnet details entered");
		click("xGmailDoneBtn");
		report.log(PASS, "Appointment has been set to reccurring");
		clearText("xGmailLocation");
		type("xGmailLocation", appName);
		report.log(PASS, "Appointment location entered : " + appName);
//		report.captureScreenShot("Reccurring appointment Details");
		click("xGmailAppSaveBtn");
		think(3000);
		report.log(PASS, "Appointment " + appName + " created successfully");
		report.info("Step_4");
		String appValueXpath = replaceXpath("xGmailAppSelect", appName);
		click(appValueXpath);
		click("xGmailEditApp");
		think(6000);
		driver().switchTo().frame(getWebElement("xGmailExtnFrame"));
		String shareBtnColor = getWebElement("xGmailShareBtn").getAttribute("class");

		if (shareBtnColor.contains("grey")) {
			report.log(PASS, "Share appointment is displayed in grey color");
			report.captureScreenShot("Appointment created");
		}

		else {
			report.log(FAIL, "Share appointment is not displayed in grey color");
		}
		report.info("Step_5");
		click("xGmailAccontTxt");
		clearText("xGmailObjectSearch");
		type("xGmailObjectSearch", accName);
		click("xFilterSearch");
		click("xGmailFirstObjectValue");
		String acc = getWebElement("xGmailAccontTxt").getAttribute("value");
		report.log(PASS, "Account : " + acc + "  added to appointmnet");
		click("xGmailOpptyTxt");
		clearText("xGmailObjectSearch");
		type("xGmailObjectSearch", opptyName);
		click("xFilterSearch");
		click("xGmailFirstObjectValue");
		String oppty = getWebElement("xGmailOpptyTxt").getAttribute("value");
		report.log(PASS, "Opportunity : " + oppty + "  added to appointmnet");
		click("xGmailLeadTxt");
		clearText("xGmailObjectSearch");
		type("xGmailObjectSearch", leadName);
		click("xFilterSearch");
		click("xGmailFirstObjectValue");
		String lead = getWebElement("xGmailLeadTxt").getAttribute("value");
		report.log(PASS, "Lead : " + lead + "  added to appointmnet");
		click("xGmailSaveBtn");
		think(7000);
		report.info("Step_6");
		shareBtnColor = getWebElement("xGmailShareBtn").getAttribute("class");

		if (shareBtnColor.contains("yellow")) {
			report.log(PASS, "Share appointment is displayed in yellow color");
			report.captureScreenShot("Appointment created with account, oppty and lead");
		}

		else {
			report.log(FAIL, "Share appointment is not displayed in yellow color");
		}

//Newly added				
		driver().switchTo().parentFrame();
		click("xGmailAppSaveBtn");
		think(3000);
//till here		
		report.info("Step_7");
		driver().get(url);
		report.log(PASS, "Succefully opened : " + url);
		commonLib.login("EBENES");
		commonLib.navigateToProduct("Activities");
		click("xActivitiesTab");
		click("xFuseSearchList");

		String listXpath = replaceXpath("xSaveSearchSelectList", saveSearch);
		click(listXpath);

		String fuseAppName = replaceXpath("xFuseAppSelectValue", appName);
		String fuseAppNameList = replaceXpath("xFuseAppSelectValueList", appName);

		int recNum = driver().findElements(By.xpath(fuseAppNameList)).size();

		if (recNum == 3)

		{
			report.log(PASS, "Appointement is created in reccurring pattern");
			report.captureScreenShot("");

		}

		else {
			report.log(FAIL, "Appointement not created in reccurring pattern");
		}
		click(fuseAppName);
		click("xFuseSeriesRadioBtn");
		click("xFuseOkBtn");

		String fuseAcc = getWebElement("xFuseAccValue").getText();
		String fuseOppty = getWebElement("xFuseOpptyValue").getText();
		String fuseLead = getWebElement("xFuseLeadValue").getText();
		report.captureScreenShot("Activity created in OSC");

		if (fuseAcc.equalsIgnoreCase(acc)) {
			report.log(PASS, "Account populated based on value selected in Gmail OSC panel");
			report.captureScreenShot("");

		}

		else {
			report.log(FAIL, "Account not populated based on value selected in Gmail OSC panel");
		}

		if (fuseOppty.equalsIgnoreCase(oppty)) {
			report.log(PASS, "Oppoortunity populated based on value selected in Gmail OSC panel");

		}

		else {
			report.log(FAIL, "Oppoortunity not populated based on value selected in Gmail OSC panel");
		}

		if (fuseLead.equalsIgnoreCase(lead)) {
			report.log(PASS, "Lead populated based on value selected in Gmail OSC panel");

		}

		else {
			report.log(FAIL, "Lead not populated based on value selected in Gmail OSC panel");
		}

		click("xFuseCancelBtn");
		commonLib.logOut();

		report.info("Step_8");
		driver().get("https://calendar.google.com");
		think(3000);
		report.info("Step_9");
		click(appValueXpath);
		click("xGmailEditApp");
		think(6000);
		clearText("xGmailAppTitle");
		type("xGmailAppTitle", appName + "Updated");
		report.log(PASS, "Updated appointment title");
		clearText("xGmailLocation");
		clearText("xGmailLocation");
		think(1000);
		type("xGmailLocation", appName + "Updated");
		report.log(PASS, "Updated appointment title");
		click("xGmailAppSaveBtn");
		click("xGmailAllEventRadioBtn");
		click("xGmailOkBtn");
		report.captureScreenShot("Updated appiontment to  " + appName + "Updated");

		report.info("Step_10");
		driver().get(url);
		String status = commonLib.scheduleESSJob(scheduleJobName);
		if (status.equalsIgnoreCase("error")) {
			think(60000);
			status = commonLib.scheduleESSJob(scheduleJobName);
		}

		if (status.equalsIgnoreCase("Succeeded")) {
			report.log(PASS, "Verify Schedule process");
		}

		else {
			report.log(FAIL, "Verify Schedule process");
		}
		report.info("Step_11");
		commonLib.login("EBENES");
		report.info("Step_12");
		commonLib.navigateToProduct("Activities");
		click("xActivitiesTab");
		click("xFuseSearchList");
		click(listXpath);
		fuseAppName = replaceXpath("xFuseAppSelectValue", appName + "Updated");
		fuseAppNameList = replaceXpath("xFuseAppSelectValueList", appName + "Updated");
		recNum = driver().findElements(By.xpath(fuseAppNameList)).size();

		if (recNum == 3)

		{
			report.log(PASS, "All reccurring Appointement subject updated");
			report.captureScreenShot("");

		}

		else {
			report.log(FAIL, "Reccurring Appointement subject not updated");
		}

		if (getWebElement(fuseAppName).isDisplayed()) {
			report.log(PASS, "The changes for the appointment name are displayed properly in sales cloud");
			click(fuseAppName);
			click("xFuseSeriesRadioBtn");
			click("xFuseOkBtn");
			String loc = getWebElement("xFuseLocation").getAttribute("value");

			if (loc.contains("Updated")) {
				report.log(PASS, "The changes for the appointment Location displayed properly in sales cloud");
				report.captureScreenShot("");
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
