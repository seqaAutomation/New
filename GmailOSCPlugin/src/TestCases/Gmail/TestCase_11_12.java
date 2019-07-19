package TestCases.Gmail;

import java.util.Map;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import ProductLib.Common.CommonLib;
import ProductLib.Gmail.GmailLib;
import TestEngine.Base;

public class TestCase_11_12 extends Base{
  @Test
  public void testMethod() {
	  GmailLib gmailLib = new GmailLib();
		CommonLib commonLib = new CommonLib();
		Map<String, String> testDataCommon = getTestData("commonTestData");
		Map<String, String> testData = getTestData("TestCase_11_12");
//		String saveSearch = testDataPreReq.get("filterName");
		String saveSearch =  testDataCommon.get("filterName");
		String oscUserName = testDataCommon.get("oscUsername");
		String gmailPassword = testDataCommon.get("gmailPassword");
		String gmailUserName = testDataCommon.get("gmailUserName");
		String emailToselect = testData.get("emailToselect");
		String scheduleJobName = testDataCommon.get("scheduleJobName");
	  String url = fileUtility.getDataFromConfig("URL");
		report.info("Url : " + url);
		gmailLib.loginToGmail(emailToselect, oscUserName,url,gmailUserName,gmailPassword);
		commonLib.loadUrlInNewTab("https://contacts.google.com");
		report.log(PASS, "Successfully opened Contacts");
		report.captureScreenShot("");
		report.info("Creating contact with email");
		String firstNameWE = saveSearch+testData.get("firstNameWE");
		String lastName = "LN";
		String emailWE = firstNameWE+"@oracle.com";
		click("xCreateContactBtn");
		type("xGmailConFN", firstNameWE);
		type("xGmailConLN", lastName);
		type("xGmailConJob", "QA");
		type("xGmailConEmail", emailWE);
		type("xGmailConEmailLabel", "Work");
		type("xGmailConPhone", "1234567890");
		type("xGmailConPhoneLabel", "Work");
		click("xGmailConSaveBtn");
		think(2000);
		driver().switchTo().frame(getWebElement("xGmailExtnFrame"));
		click("xGmailCreateContactFromMail");
		think(3000);
		click("xGmailSaveBtn");
		think(3000);
		String gmailJob = getWebElement("xGmailContactJob").getAttribute("value");
		String gmailPh = getWebElement("xGmailContactPh").getAttribute("value");
		String gmailEmail = getWebElement("xGmailContactEmail").getAttribute("value");
		
		driver().switchTo().parentFrame();
		click("xGmailContactClose");
		report.info("Creating contact without email");
		String firstNameWOE  = saveSearch+testData.get("firstNameWOE");
		click("xCreateContactBtn");
		type("xGmailConFN", firstNameWOE);
		type("xGmailConLN", lastName);
		type("xGmailConJob", "QA");
		type("xGmailConPhone", "1234567790");
		type("xGmailConPhoneLabel", "Work");
		click("xGmailConSaveBtn");
		think(2000);
		driver().switchTo().frame(getWebElement("xGmailExtnFrame"));
		click("xGmailCreateContactFromMail");
		think(3000);
		click("xGmailSaveBtn");
		think(3000);
		driver().switchTo().parentFrame();
		click("xGmailContactClose");
	
		String contactValueXpathWE = replaceXpath("xGmailContactSelect",firstNameWE+" "+lastName);
		click(contactValueXpathWE);
		click("xGmailContactEditBtn");
		
		clearText("xGmailConFN");
		type("xGmailConFN", firstNameWE+"GUpdate");
		clearText("xGmailConJob");
		type("xGmailConJob", "QAGUpdate");
		clearText("xGmailConPhone");
		type("xGmailConPhone", "0123456789");
		click("xGmailConSaveBtn");
		click("xGmailContactClose");
		
		String contactValueXpathWOE = replaceXpath("xGmailContactSelect",firstNameWOE+" "+lastName);
		click(contactValueXpathWOE);
		
		click("xGmailContactEditBtn");
		clearText("xGmailConFN");
		type("xGmailConFN", firstNameWOE+"GUpdate");
		clearText("xGmailConJob");
		type("xGmailConJob", "QAGUpdate");
		clearText("xGmailConPhone");
		type("xGmailConPhone", "0123456779");
		click("xGmailConSaveBtn");
		click("xGmailContactClose");
		
		driver().get(url);
		commonLib.login("EBENES");
		commonLib.navigateToProduct("Contacts");
		click("xFuseSearchList");
		String listXpath = replaceXpath("xSaveSearchSelectList", saveSearch);
		click(listXpath);
		report.log(PASS, "Contact saved search filter selected");
		report.captureScreenShot("");
		
		String contactXpathWE = replaceXpath("xTaskCreated", firstNameWE + " " + lastName);
		click(contactXpathWE);
		think(4000);
		click("xContactProfileTab");
		clearText("xFuseFNTxtBox");
		type("xFuseFNTxtBox", firstNameWE+"OSCUpdate");
		clearText("xFuseJobTxtBox");
		type("xFuseJobTxtBox", "QAOSCUpdate");
		clearText("xFusePhoneNumTxtBox");
		type("xFusePhoneNumTxtBox", "222222");
		click("xProfileSaveBtn");
		
		String contactXpathWOE = replaceXpath("xTaskCreated", firstNameWOE + " " + lastName);
		click(contactXpathWOE);
		think(4000);
		click("xContactProfileTab");
		clearText("xFuseFNTxtBox");
		type("xFuseFNTxtBox", firstNameWOE+"OSCUpdate");
		clearText("xFuseJobTxtBox");
		type("xFuseJobTxtBox", "QAOSCUpdate");
		clearText("xFusePhoneNumTxtBox");
		type("xFusePhoneNumTxtBox", "222222");
		click("xProfileSaveBtn");
		
		commonLib.logOut();
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
		
		driver().get("https://contacts.google.com");
		
		String contactValueXpathWOEAfterUpdate = replaceXpath("xGmailContactSelect",firstNameWOE+"OSCUpdate "+lastName);
		
		click("xContactLabel");
		
		if(isDisplayed(contactValueXpathWOEAfterUpdate))
		{
			report.log(PASS, "Verify OSC update taken precedence");
			click(contactValueXpathWOEAfterUpdate);
			driver().switchTo().frame(getWebElement("xGmailExtnFrame"));
			String gmailJobWOEmail = getWebElement("xGmailContactJob").getAttribute("value");
			String gmailPhWOEmail = getWebElement("xGmailContactPh").getAttribute("value");
			if(gmailJobWOEmail.equalsIgnoreCase("QAOSCUpdate"))
			{
				report.log(PASS, "Verify OSC job update taken precedence");
			}
			else
			{
				report.log(FAIL, "Verify OSC job update taken precedence");
			}
			
			if(gmailPhWOEmail.contains("222222"))
			{
				report.log(PASS, "Verify OSC Phone update taken precedence");
			}
			else
			{
				report.log(FAIL, "Verify OSC Phone update taken precedence");
			}
			
			driver().switchTo().parentFrame();
			click("xGmailContactClose");
			
		}
		
		else
		{
			report.log(FAIL, "Verify OSC update taken precedence");
			
		}
		
String contactValueXpathWEAfterUpdate = replaceXpath("xGmailContactSelect",firstNameWE+"OSCUpdate "+lastName);
		
		
		if(isDisplayed(contactValueXpathWEAfterUpdate))
		{
			report.log(PASS, "Verify OSC update taken precedence");
			click(contactValueXpathWEAfterUpdate);
			driver().switchTo().frame(getWebElement("xGmailExtnFrame"));
			String gmailJobWOEmail = getWebElement("xGmailContactJob").getAttribute("value");
			String gmailPhWOEmail = getWebElement("xGmailContactPh").getAttribute("value");
			if(gmailJobWOEmail.equalsIgnoreCase("QAOSCUpdate"))
			{
				report.log(PASS, "Verify OSC job update taken precedence");
			}
			else
			{
				report.log(FAIL, "Verify OSC job update taken precedence");
			}
			
			if(gmailPhWOEmail.contains("222222"))
			{
				report.log(PASS, "Verify OSC Phone update taken precedence");
			}
			else
			{
				report.log(FAIL, "Verify OSC Phone update taken precedence");
			}
			
			driver().switchTo().parentFrame();
			click("xGmailContactClose");
			
		}
		
		else
		{
			report.log(FAIL, "Verify OSC update taken precedence");
			
		}
  }
}
