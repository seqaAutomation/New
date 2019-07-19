package TestCases.Gmail;

import java.util.ArrayList;
import java.util.Map;

import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import ProductLib.Gmail.GmailLib;
import TestEngine.Base;

public class TestCase_01 extends Base {
	@Test
	public void testMethod() {
		report.info("Test Case : "+"072_070_100_010_R12Gmail_User is able to successfully log in to OSC for Gmail when we allow the confirmation on Request for Permission page");
		report.info("Step_1 : Login to Gmail");
		report.info("Step_2 : Login to Gmail OSC extension and verify permission");
		report.info("Step_3 : Verify user name and Host fields are disabled after logged in");
		String url = fileUtility.getDataFromConfig("URL");
		Map<String, String> testDataCommon = getTestData("commonTestData");
		String gmailPassword = testDataCommon.get("gmailPassword");
		String gmailUserName = testDataCommon.get("gmailUserName");
		String oscUserName = testDataCommon.get("oscUsername");
		report.info("Step_1");
		driver().get("https://mail.google.com");
		think(5000);
		try {
			type("xGmailUserName", gmailUserName);
			click("xGmailNxtBtn");
			report.log(PASS, "Gmail userName entered : "+gmailUserName);
		} catch (Exception e) {
			driver().get("https://mail.google.com");
			think(5000);
			type("xGmailUserName", gmailUserName);
			click("xGmailNxtBtn");
			report.log(PASS, "Gmail userName entered : "+gmailUserName);
		}
		think(5000);
		type("xGmailPwd", gmailPassword);
		click("xGmailNxtBtn");
		think(2000);
		report.log(PASS, "Gmail Password entered : "+gmailPassword);
		click("xGmailMail");
		report.log(PASS, "Logged in to Gmail successfully");
		report.info("Step_2");
		driver().switchTo().frame(getWebElement("xGmailExtnFrame"));
		think(8000);
		type("xGmailExtnHost", url);
		type("xGmailExtnUserName",oscUserName);
		type("xGmailExtnPassword", "Welcome1");
		click("xGmailExtnSignInBtn");
		String win = driver().getWindowHandle();
		ArrayList<String> tabs = new ArrayList(driver().getWindowHandles());
		System.out.println("Array List" + tabs.size());
		System.out.println("Array Values" + tabs);
		report.log(PASS, "success: Clicked on Sign in and Navigate to Select Email account");
		while(!(tabs.size()==2))
		{
			click("xGmailExtnSignInBtn");
			tabs = new ArrayList(driver().getWindowHandles());
		}
		driver().switchTo().window(tabs.get(1));
		click("xGmailselectaccountSelect");
		report.log(win, "Successfuly Choose a Gmail account");
		report.log(PASS, "Choose an account Page Loaded");

		boolean gmaileAllowMail = getWebElement("xGmaileAllowMail").isDisplayed();
		if (gmaileAllowMail = true) {
			report.log(PASS, "View your email messages and settings appeared");
		} else {
			report.log(FAIL, "View your email messages and settings appeared");
		}
		boolean gmaileAllowContact = getWebElement("xGmaileAllowContact").isDisplayed();
		if (gmaileAllowContact = true) {
			report.log(PASS, "See, edit, download, and permanently delete your contacts");
		} else {
			report.log(FAIL, "See, edit, download, and permanently delete your contacts");
		}
		boolean gmaileAllowCalendar = getWebElement("xGmaileAllowCalendar").isDisplayed();
		if (gmaileAllowCalendar = true) {
			report.log(PASS,
					"See, edit, share, and permanently delete all the calendars you can access using Google Calendar");
		} else {
			report.log(FAIL,
					"See, edit, share, and permanently delete all the calendars you can access using Google Calendar");
		}

		click("xGmailAllowBtn");

		driver().switchTo().window(tabs.get(0));
		think(10000);

		report.info("Step_3");
		driver().switchTo().frame(getWebElement("xGmailExtnFrame"));
		click("xGmailExtnSettingsnBtn");

		WebElement hostField = getWebElement("xGmailExtnHost");
		String expected = "true";
		System.out.println("hostField :" + hostField);
		String isHostNamedisabled = hostField.getAttribute("disabled");
		System.out.println("Value of the Host Name Diabled True/False:" + isHostNamedisabled);

		if (expected.equals(isHostNamedisabled)) {
			report.log(PASS, "Gmail Extn Host input filed is disabled as expected");
		} else {
			report.log(FAIL, "Gmail Extn Host input filed is Enabled. Expected disabled");
		}

		WebElement userName = getWebElement("xGmailExtnUserName");
		System.out.println("User Name Filed :" + userName);
		String isuserNamedisabled = userName.getAttribute("disabled");
		System.out.println("Value of the User Name Diabled True/False:" + isuserNamedisabled);

		if (expected.equals(isuserNamedisabled)) {
			report.log(PASS, "Gmail Extn User Name input filed is disabled as expected");
		} else {
			report.log(FAIL, "Gmail Extn User Name input filed is Enabled. Expected disabled");
		}
		WebElement passwordField = getWebElement("xGmailExtnPassword");
		System.out.println("Password Filed :" + passwordField);
		String ispasswordFieldDisabled = passwordField.getAttribute("disabled");
		System.out.println("Value of the Password Diabled True/False:" + ispasswordFieldDisabled);

		if (expected.equals(ispasswordFieldDisabled)) {
			report.log(PASS, "Gmail Extn Password input filed is disabled as expected");
		} else {
			report.log(FAIL, "Gmail Extn Password input filed is Enabled. Expected disabled");
		}

		boolean gmailExtnSignOutBtn = getWebElement("xGmailExtnSignOutBtn").isDisplayed();
		if (gmailExtnSignOutBtn = true) {
			report.log(PASS, "SignOut Button Appearing as expected");
		} else {
			report.log(FAIL, "No SignOut button appeared");
		}

	}
}
