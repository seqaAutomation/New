package TestCases.Gmail;

import java.util.Map;

import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

import ProductLib.Common.CommonLib;
import ProductLib.Gmail.GmailLib;
import TestEngine.Base;

public class TestCase_02 extends Base {
	@Test
	public void testMethod() {
		
		report.info("Test Case : "+"072_070_300_030_R12Gmail_Verify that Sales Cloud Administrator should be able to enable syncing of Contacts from Sales Cloud to Gmail");
		CommonLib commonLib = new CommonLib();
		GmailLib gmailLib = new GmailLib();
		Map<String, String> testDataCommon = getTestData("commonTestData");
		Map<String, String> testData = getTestData("TestCase_02");
//		String saveSearch = testDataPreReq.get("filterName");
		String saveSearch = testDataCommon.get("filterName");
		String scheduleJobName = testDataCommon.get("scheduleJobName");
		String oscUserName = testDataCommon.get("oscUsername");
		String gmailPassword = testDataCommon.get("gmailPassword");
		String gmailUserName = testDataCommon.get("gmailUserName");
		String emailToselect = testData.get("emailToselect");
		String contactName = saveSearch+testData.get("contactName");
		String url = fileUtility.getDataFromConfig("URL");
		report.info("Url : " + url);

		driver().get(url);
		commonLib.login("EBENES");
		commonLib.navigateToProduct("Contacts");
     	click("xFuseCreateContact");
		clearText("xFuseFNTxtBox");
		type("xFuseFNTxtBox", contactName);
		clearText("xFuseLNTxtBox");
		type("xFuseLNTxtBox", "LN");
		clearText("xFusePhoneNumTxtBox");
		type("xFusePhoneNumTxtBox", "9876543210");
		clearText("xFusePhoneCodeTxtBox");
		type("xFusePhoneCodeTxtBox", "91");
		clearText("xFuseEmailTxtBox");
		type("xFuseEmailTxtBox", contactName+"123@oracle.com");
		click("xProfileSaveBtn");
		
		think(10000);
		if(isAvailable("xContinue"))
		{
			click("xContinue");
		}
		think(10000);
		commonLib.logOut();
		
		
		gmailLib.loginToGmail(emailToselect, oscUserName,url,gmailUserName,gmailPassword);
//		think(5000);
//		click("xGmailExtnSettingsnBtn");
//		
//		Select sel = new Select(getWebElement("xGmailSettingsDrpDwn"));
//		sel.selectByValue("CONTACT_SYNC");
//		
//		
//		Select sel1 = new Select(getWebElement("xGmailSavedSearchSelect"));
//		sel1.selectByVisibleText(saveSearch);
		
		commonLib.loadUrlInNewTab(url);
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
//		driver().get("https://contacts.google.com");
		commonLib.loadUrlInNewTab("https://contacts.google.com");
		click("xContactLabel");
		String path = replaceXpath("xSpanContainsTextGeneric",contactName);
		
		if(isDisplayed(path))
		{
			report.log(PASS, "Verify syncing of Contacts from Sales Cloud to Gmail");
		}
		else
		{
			report.log(FAIL, "Verify syncing of Contacts from Sales Cloud to Gmail");
		}


	}
}
