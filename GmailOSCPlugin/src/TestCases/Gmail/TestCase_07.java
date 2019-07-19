package TestCases.Gmail;

import java.util.Map;

import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

import ProductLib.Common.CommonLib;
import ProductLib.Gmail.GmailLib;
import TestEngine.Base;

public class TestCase_07 extends Base {
	@Test
	public void testMethod() {
		report.info("Test Case : "
				+ "072_070_200_125_R12Gmail_Verify that user should be able to create contact with All fields in Sidepanel");
		CommonLib commonLib = new CommonLib();
		GmailLib gmailLib = new GmailLib();
		Map<String, String> testDataCommon = getTestData("commonTestData");
		Map<String, String> testData = getTestData("TestCase_07");
		Map<String, String> testDataPR01 = getTestData("PreReq_01");
//		String saveSearch = testDataPreReq.get("filterName");
		
		String saveSearch =  testDataCommon.get("filterName");
		String oscUserName = testDataCommon.get("oscUsername");
		String gmailPassword = testDataCommon.get("gmailPassword");
		String gmailUserName = testDataCommon.get("gmailUserName");
		String emailToselect = testData.get("emailToselect");
		String accName = testDataPR01.get("accName");
		String url = fileUtility.getDataFromConfig("URL");
		report.info("Url : " + url);
		String fn = saveSearch + "FN";
		String ln = "LN";
		gmailLib.loginToGmail(emailToselect, oscUserName, url, gmailUserName, gmailPassword);
		click("xGmailContactInCC");
		click("xGmailCreateContactFromMail");
		report.log(PASS, "Click on contact create from Gmail OSC panel");
		clearText("xGmailContactFn");
		type("xGmailContactFn", fn);
		report.log(PASS, "Contact first name entered : " + fn);
		clearText("xGmailContactLn");
		type("xGmailContactLn", "LN");
		report.log(PASS, "Contact last name entered : " + ln);
		clearText("xGmailContactJob");
		type("xGmailContactJob", "QA");
		report.log(PASS, "Contact job entered : QA");
		click("xGmailAccontTxt");
		clearText("xGmailObjectSearch");
		type("xGmailObjectSearch", accName);
		click("xFilterSearch");
		click("xGmailFirstObjectValue");
		report.log(PASS, "Contact Account entered");

		click("xGmailContactPh");

		clearText("xGmailContactphCountryCode");
		type("xGmailContactphCountryCode", "91");

		clearText("xGmailContactPhAreaCode");
		type("xGmailContactPhAreaCode", "0495");

		clearText("xGmailContactPhNum");
		type("xGmailContactPhNum", "9876543210");

		clearText("xGmailContactWorkPh");
		type("xGmailContactWorkPh", "9876");

		click("xGmailSaveBtn2");

		report.log(PASS, "Contact Phone entered");
		click("xGmailContactAdd");
		clearText("xGmailContactAdd1");
		type("xGmailContactAdd1", "Address Line 1");

		clearText("xGmailContactAdd2");
		type("xGmailContactAdd2", "Address Line 2");

		clearText("xGmailContactCity");
		type("xGmailContactCity", "NewYork");

		clearText("xGmailContactState");
		type("xGmailContactState", "NewYork");

		clearText("xGmailContactCountry");
		type("xGmailContactCountry", "US");

		clearText("xGmailContactPostalCode");
		type("xGmailContactPostalCode", "674567");
		click("xGmailSaveBtn2");
		report.log(PASS, "Contact Address entered");
		click("xGmailSaveBtn");
		think(10000);
		String gmailJob = getWebElement("xGmailContactJob").getAttribute("value");
		String gmailAcc = getWebElement("xGmailAccontTxt").getAttribute("value");
		String gmailPh = getWebElement("xGmailContactPh").getAttribute("value");
		String gmailAddress = getWebElement("xGmailContactAdd").getText();
		String gmailEmail = getWebElement("xGmailContactEmail").getAttribute("value");
		report.log(PASS, "Contact created upsynced to OSC");
//		report.captureScreenShot("");

		driver().get(url);
		commonLib.login("EBENES");
		commonLib.navigateToProduct("Contacts");
		click("xFuseSearchList");
		String listXpath = replaceXpath("xSaveSearchSelectList", saveSearch);
		click(listXpath);
		report.log(PASS, "Contact saved search filter selected");
//		report.captureScreenShot("");
		String contactXpath = replaceXpath("xTaskCreated", fn + " " + ln);
		click(contactXpath);
		think(4000);
		String fuseJob = getWebElement("xContactJob").getText();
		String fuseAcc = getWebElement("xContactAccount").getText();
		String fusePh = getWebElement("xContactPhone").getText();
		String fuseAddress = getWebElement("xContactAddress").getText();
		String fuseEmail = getWebElement("xContactEmail").getText();

		System.out.println(gmailJob + " " + gmailAcc + " " + gmailPh + " " + gmailAddress + " " + gmailEmail);
		System.out.println(fuseJob + " " + fuseAcc + " " + fusePh + " " + fuseAddress + " " + fuseEmail);

		report.info(gmailJob + " " + gmailAcc + " " + gmailPh + " " + gmailAddress + " " + gmailEmail);
		report.info(fuseJob + " " + fuseAcc + " " + fusePh + " " + fuseAddress + " " + fuseEmail);
		if (gmailJob.equalsIgnoreCase(fuseJob)) {
			report.log(PASS, "Conctact Job upsynced to OSC from gmail");
			report.captureScreenShot("");
		} else {
			report.info("Upsynced job name : " + gmailJob);
			report.info("Job name displayed in contact : " + fuseJob);
			report.log(FAIL, "Conctact Job not upsynced to OSC from gmail");
		}
		if (fuseAcc.contains(gmailAcc)) {
			report.log(PASS, "Conctact Account upsynced to OSC from gmail");
		} else {
			report.info("Upsynced Account name : " + gmailAcc);
			report.info("Account name displayed in contact : " + fuseAcc);
			report.log(FAIL, "Conctact account not upsynced to OSC from gmail");
		}
		if (gmailPh.equalsIgnoreCase(fusePh)) {
			report.log(PASS, "Conctact Phone upsynced to OSC from gmail");
		} else {
			report.info("Upsynced Phone number : " + gmailPh);
			report.info("Phone number displayed in contact : " + fusePh);
			report.log(FAIL, "Conctact phone number not upsynced to OSC from gmail");
		}
		if (gmailAddress.equalsIgnoreCase(fuseAddress)) {
			report.log(PASS, "Conctact Address upsynced to OSC from gmail");
		} else {
			report.info("Upsynced  address : " + gmailAddress);
			report.info("Address displayed in contact : " + fuseAddress);
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
