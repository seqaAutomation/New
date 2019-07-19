package TestCases.Gmail;

import java.util.Map;

import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

import ProductLib.Common.CommonLib;
import ProductLib.Gmail.GmailLib;
import TestEngine.Base;

public class TestCase_05_06 extends Base {
	@Test
	public void testMethod() {
		report.info("Test Cases : "+"072_070_250_010_R12Gmail_Verify that Gmail email Reciepents (To,CC,From) will sync as Activity Task Participants if the contact is a known contact\n" + 
				"072_070_275_050_R12Gmail_Verify that user is able to update AccountOpportunityLead field values using Dropdown values");
		CommonLib commonLib = new CommonLib();
		GmailLib gmailLib = new GmailLib();
		Map<String, String> testDataCommon = getTestData("commonTestData");
		Map<String, String> testData = getTestData("TestCase_05_06");
		Map<String, String> testDataPR01 = getTestData("PreReq_01");
		String oscUserName = testDataCommon.get("oscUsername");
		String gmailPassword = testDataCommon.get("gmailPassword");
		String gmailUserName = testDataCommon.get("gmailUserName");
		String scheduleJobName = testDataCommon.get("scheduleJobName");
		String emailToselect = testData.get("emailToselect");
		String emailTaskSub = testData.get("emailTaskSub");
		String accName = testDataPR01.get("accName");
		String opptyName = testDataPR01.get("opptyName");
		String leadName = testDataPR01.get("leadName");
		String url = fileUtility.getDataFromConfig("URL");
		report.info("Url : " + url);
		driver().get(url);
	  	commonLib.login(oscUserName);
	  	commonLib.navigateToProduct("Activities");
	  	click("xTaskTab");
		click("xFuseTaskDrpDwn");
		String saveSearchSelectXplath = replaceXpath("xSaveSearchSelect", "All Tasks");
		click(saveSearchSelectXplath);
		report.captureScreenShot("");
		String taskCreated = replaceXpath("xTaskCreated", emailTaskSub);
		think(3000);
		if (isDisplayed(taskCreated)) {
			click(taskCreated);
			think(3000);
			if (isDisplayed("xTaskAction")) {
				click("xTaskAction");
			}

			else {
				click("xContactAction");
			}
			click("xDeleteTask");
			click("xConfirmationYesBtn");
		}
		think(3000);
		commonLib.logOut();
		
		String status = commonLib.scheduleESSJob(scheduleJobName);
		if (status.equalsIgnoreCase("error")) {
			think(60000);
			report.info("Rerunning Schedule Job");
			status = commonLib.scheduleESSJob(scheduleJobName);

		}

		if (status.equalsIgnoreCase("Succeeded")) {
			report.log(PASS, "Verify Schedule process");
		}

		else {
			report.log(FAIL, "Verify Schedule process");
		}
		gmailLib.loginToGmail(emailToselect, oscUserName,url,gmailUserName,gmailPassword);
		click("xGmailShareEmailBtn");
		report.log(PASS, "Clicked on share email button");
//		report.captureScreenShot("");
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
		think(5000);
		String sharedContact = getWebElement("xSharedContact").getAttribute("title");
		String shareBtnColor = getWebElement("xGmailShareEmailBtn").getAttribute("class");

		if (shareBtnColor.contains("yellow")) {
			report.log(PASS, "Share Email is displayed in yellow color");
			report.captureScreenShot("Email Shared successfully");
		}

		else {
			report.log(FAIL, "Share Email is not displayed in yellow color");
		}

		driver().get(url);
		commonLib.login("EBENES");
		commonLib.navigateToProduct("Activities");
		click("xTaskTab");
		click("xFuseTaskDrpDwn");
		click(saveSearchSelectXplath);
		if (getWebElement(taskCreated).isDisplayed())
		{
			report.log(PASS, "Shared Email is synced to OSC as Task activity");
			report.captureScreenShot("Shared Email is synced to OSC");
			click(taskCreated);
			String accTask = getWebElement("xFuseTaskAccValue").getText();
			report.info("Email account added in gmail : "+ acc);
			report.info("Upsynced account displayed  in OSC : "+ accTask);
			if (acc.equalsIgnoreCase(accTask)) {
				report.log(PASS, "Share Email Account added to Email synced to OSC");

			}

			else {
				report.log(FAIL, "Share Email Account added to Email not synced to OSC");
			}
			String accContact = getWebElement("xFuseTaskContactValue").getText();
			report.info("Contact added to email  :"+sharedContact);
			report.info("Contact displayed in OSC :"+accContact);
			if (sharedContact.equalsIgnoreCase(accContact)) {
				report.log(PASS, "Contact added to Email synced to OSC");

			}

			else {
				
				report.log(FAIL, "Contact added to Email synced to OSC");
			}
		}

		else {
			report.log(FAIL, "Shared Email is not synced to OSC as Task activity");
		}
	}
}
