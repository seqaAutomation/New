package TestCases.Gmail;

import java.util.Map;

import org.openqa.selenium.Keys;
import org.testng.annotations.Test;

import ProductLib.Common.CommonLib;
import ProductLib.Gmail.GmailLib;
import TestEngine.Base;

public class TestCase_10 extends Base {
  @Test
  public void testMethod() {
		GmailLib gmailLib = new GmailLib();
		CommonLib commonLib = new CommonLib();
		Map<String, String> testDataCommon = getTestData("commonTestData");
		Map<String, String> testData = getTestData("TestCase_10");
//		String saveSearch = testDataPreReq.get("filterName");
		String saveSearch =  testDataCommon.get("filterName");
		String oscUserName = testDataCommon.get("oscUsername");
		String gmailPassword = testDataCommon.get("gmailPassword");
		String gmailUserName = testDataCommon.get("gmailUserName");
		String emailToselect = testData.get("emailToselect");
	  String url = fileUtility.getDataFromConfig("URL");
		report.info("Url : " + url);
		gmailLib.loginToGmail(emailToselect, oscUserName,url,gmailUserName,gmailPassword);
		commonLib.loadUrlInNewTab("https://contacts.google.com");
		report.log(PASS, "Successfully opened Contacts");
		report.captureScreenShot("");
		String contactFirstName = saveSearch+testData.get("contactFirstName");
		String lastName = "LN";
		String email = contactFirstName+"@oracle.com";
		
		
		click("xCreateContactBtn");
		type("xGmailConFN", contactFirstName);
		type("xGmailConLN", lastName);
		type("xGmailConJob", "QA");
		type("xGmailConEmail", email);
		type("xGmailConEmailLabel", "Work");
		type("xGmailConPhone", "1234567890");
		type("xGmailConPhoneLabel", "Work");
		
		click("xGmailConMore");
		click("xGmailConCountry");
		click("xGmailConUS");
		
		String streetAdress = "New York Ave";
		type("xGmailConStreetAddres", streetAdress);
		click("xGmailConState");
		String district = "Tennessee";
//				"District of Columbia";
		String xdistrict = replaceXpath("xGmailConDistrict", district);
		if(!isAvailable(xdistrict))
		{
			click("xGmailConState");
		}
		click(xdistrict);
		clearText("xGmailConZipCode");
		String zipCode = "37921";
		type("xGmailConZipCode", zipCode);
		type("xGmailConAddressLabel", "Work");
		click("xGmailConSaveBtn");
		think(2000);
		driver().switchTo().frame(getWebElement("xGmailExtnFrame"));
		click("xGmailCreateContactFromMail");
		think(3000);
		click("xGmailSaveBtn");
		think(3000);
		String gmailJob = getWebElement("xGmailContactJob").getAttribute("value");
		System.out.println(gmailJob);
		String gmailPh = getWebElement("xGmailContactPh").getAttribute("value");
		String gmailAddress = getWebElement("xGmailContactAdd").getText();
		String gmailEmail = getWebElement("xGmailContactEmail").getAttribute("value");
		driver().get(url);
		commonLib.login("EBENES");
		commonLib.navigateToProduct("Contacts");
		click("xFuseSearchList");
		String listXpath = replaceXpath("xSaveSearchSelectList", saveSearch);
		click(listXpath);
		report.log(PASS, "Contact saved search filter selected");
		report.captureScreenShot("");
		String contactXpath = replaceXpath("xTaskCreated", contactFirstName + " " + lastName);
		click(contactXpath);
		think(4000);
		String fuseJob = getWebElement("xContactJob").getText();
		String fusePh = getWebElement("xContactPhone").getText();
		String fuseAddress = getWebElement("xContactAddress").getText();
		String fuseEmail = getWebElement("xContactEmail").getText();

		System.out.println(gmailJob + " " +  " " + gmailPh + " " + gmailAddress + " " + gmailEmail);
		System.out.println(fuseJob + " "+ " " + fusePh + " " + fuseAddress + " " + fuseEmail);

		report.info(gmailJob + " "  + " " + gmailPh + " " + gmailAddress + " " + gmailEmail);
		report.info(fuseJob + " "  + " " + fusePh + " " + fuseAddress + " " + fuseEmail);
		if (gmailJob.equalsIgnoreCase(fuseJob)) {
			report.log(PASS, "Conctact Job upsynced to OSC from gmail");
			report.captureScreenShot("");
		} else {
			report.info("Upsynced job name : " + gmailJob);
			report.info("Job name displayed in contact : " + fuseJob);
			report.log(FAIL, "Conctact Job not upsynced to OSC from gmail");
		}

		if (gmailPh.replace(" ", "").equalsIgnoreCase(fusePh.replace(" ", ""))) {
			report.log(PASS, "Conctact Phone upsynced to OSC from gmail");
		} else {
			report.info("Upsynced Phone number : " + gmailPh);
			report.info("Phone number displayed in contact : " + fusePh);
			report.log(FAIL, "Conctact phone number not upsynced to OSC from gmail");
		}
		if ((fuseAddress.toLowerCase()).contains(streetAdress.toLowerCase())) {
			report.log(PASS, "Conctact Address upsynced to OSC from gmail");
		} else {
			report.info("Upsynced  address : " + gmailAddress);
			report.info("Upsynced  Street address : " + streetAdress.toLowerCase());
			report.info("Address displayed in contact : " + fuseAddress.toLowerCase());
			report.log(FAIL, "Conctact address not upsynced to OSC from gmail");
		}
		if (gmailEmail.equalsIgnoreCase(fuseEmail)) {
			report.log(PASS, "Conctact email upsynced to OSC from gmail");
		} else {
			report.info("Upsynced email : " + gmailEmail);
			report.info("Email displayed in contact : " + fuseEmail);
			report.log(FAIL, "Conctact email not upsynced to OSC from gmail");
		}		
  }
}
