package TestCases.Gmail;

import java.util.Map;

import org.testng.annotations.Test;

import ProductLib.Gmail.GmailLib;
import TestEngine.Base;

public class TestCase_03 extends Base {
	@Test
	public void testMethod() {
		report.info("Test Case : "+"072_070_200_050_R12Gmail_Verify the known & Unkown Contacts in Side Panel from E-mail");
		GmailLib gmailLib = new GmailLib();
		Map<String, String> testDataCommon = getTestData("commonTestData");
		Map<String, String> testData = getTestData("TestCase_03");
		String oscUserName = testDataCommon.get("oscUsername");
		String gmailPassword = testDataCommon.get("gmailPassword");
		String gmailUserName = testDataCommon.get("gmailUserName");
		String emailToselect = testData.get("emailToselect");
		String url = fileUtility.getDataFromConfig("URL");
		report.info("Url : " + url);
		report.info("Step_1 : Login to Gmail OSC extension");
		report.info("Step_2 : Verify unknown contacts are displayed in red color");
		report.info("Step_3 : Verify known contacts are displayed in blue color");
		report.info("Step_1 : Started");
		gmailLib.loginToGmail(emailToselect, oscUserName, url, gmailUserName, gmailPassword);
		report.info("Step_2 : Started");
		if (getWebElement("//button[contains(@title,'eldho.jijo@oracle.com')]").getAttribute("class").contains("red"))

		{
			report.log(PASS, "Unkonwn contact displayed in Red color");
		}

		else {
			report.log(FAIL, "Unkonwn contact not displayed in Red color");

		}
		report.info("Step_3 : Started");
		if (getWebElement("//button[@id='contact_2']").getAttribute("class").contains("blue")) {
			report.log(PASS, "konwn contact displayed in Blue color");
		}

		else {
			report.log(FAIL, "konwn contact not displayed in Blue color");
		}

	}
}
