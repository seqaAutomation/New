package TestCases.Gmail;

import java.util.Map;

import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

import ProductLib.Common.CommonLib;
import ProductLib.Gmail.GmailLib;
import TestEngine.Base;

public class TestCase_08 extends Base {
	@Test
	public void testMethod() {
		report.info("Test Case : "+"072_070_300_060_R12Gmail_Verify that Sales Cloud Administrator should be able to enable syncing of Activities from Sales Cloud to Gmail");
		CommonLib commonLib = new CommonLib();
		GmailLib gmailLib = new GmailLib();
		Map<String, String> testDataCommon = getTestData("commonTestData");
		Map<String, String> testData = getTestData("TestCase_08");
//		String saveSearch = testDataPreReq.get("filterName");
		String saveSearch =  testDataCommon.get("filterName");
		String scheduleJobName = testDataCommon.get("scheduleJobName");
		String oscUserName = testDataCommon.get("oscUsername");
		String gmailPassword = testDataCommon.get("gmailPassword");
		String gmailUserName = testDataCommon.get("gmailUserName");
		String emailToselect = testData.get("emailToselect");
		String appName = saveSearch+testData.get("appointmentName");
		String url = fileUtility.getDataFromConfig("URL");
		report.info("Url : " + url);

		driver().get(url);
		commonLib.login("EBENES");
		commonLib.navigateToProduct("Activities");
		click("xActivitiesTab");
		report.log(PASS, "Activities page displayed successfully");
		click("xFuseCreateApp");
		clearText("xFuseSubTxtBox");
		type("xFuseSubTxtBox", appName);
		report.log(PASS, "Entered appointment title : " + appName);
		clearText("xFuseLocTxtBox");
		type("xFuseLocTxtBox", appName);
		report.log(PASS, "Entered location  : " + appName);
		clearText("xFuseDecTxtBox");
		type("xFuseDecTxtBox", appName);
		report.log(PASS, "Entered description : " + appName);
		click("xProfileSaveBtn");
		
		think(10000);
		if(isAvailable("xContinue"))
		{
			click("xContinue");
		}
		think(8000);
		report.log(PASS, "Created appointment : " + appName);
//		report.captureScreenShot("");
		commonLib.logOut();

//		gmailLib.loginToGmail(emailToselect, oscUserName, url, gmailUserName, gmailPassword);
//		think(5000);
//		click("xGmailExtnSettingsnBtn");
//
//		Select sel = new Select(getWebElement("xGmailSettingsDrpDwn"));
//		sel.selectByValue("ACTIVITY_SYNC");
//		Select sel1 = new Select(getWebElement("xGmailSavedSearchSelect"));
//		sel1.selectByVisibleText(saveSearch);
//		report.log(PASS, "cwb1901f6g" + "selected in Activity sync");
//		report.captureScreenShot("");
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
		gmailLib.loginToGmail(emailToselect, oscUserName, url, gmailUserName, gmailPassword);
		think(5000);
		commonLib.loadUrlInNewTab("https://calendar.google.com");
		String appValueXpath = replaceXpath("xGmailAppSelect", appName);

		if (getWebElement(appValueXpath).isDisplayed()) {
			report.log(PASS, "Activities are synced from Sales Cloud to Gmail");
			report.captureScreenShot("");
		}

		else {
			report.log(FAIL, "Activities are synced from Sales Cloud to Gmail ");
		}

	}
}
