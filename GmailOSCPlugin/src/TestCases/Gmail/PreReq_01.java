package TestCases.Gmail;

import java.util.Map;

import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;
import Helper.RestLib;
import ProductLib.Common.CommonLib;
import ProductLib.Gmail.GmailLib;
import TestEngine.Base;

public class PreReq_01 extends Base {
	@Test
	public void testMethod() {
		report.info("Test Case : Create Rest setup and OSC profile values");
		RestLib restLib = new RestLib();
		CommonLib commonLib = new CommonLib();
		Map<String, String> testData = getTestData("PreReq_01");
		Map<String, String> testDataCommon = getTestData("commonTestData");
		String testDataPath = "./src/test/com/ProductLib/Gmail/TestData/";
		String patchFilePath = testDataPath+testData.get("patchFilePath");
		String postFilePath = testDataPath+testData.get("postFilePath");
		String oscUsername = testDataCommon.get("oscUsername");
		String filterName =  testDataCommon.get("filterName");
		String accName = testData.get("accName");
		String opptyName = testData.get("opptyName");
		String leadName = testData.get("leadName");
		String url = fileUtility.getDataFromConfig("URL");
		report.info("Url : " + url);
//		String uri = testData.get("getUri");
		String uri = ":443/salesApi/resources/latest/goiApps?onlyData=true&q=IsActive=Y";
		String patchUri=":443/salesApi/resources/latest/goiApps/";
		String postUri=":443/salesApi/resources/latest/goiApps";
		uri = url + uri;
		report.info("Rest Get uri : " + uri);
		report.info("Rest Get userName : " + oscUsername);
		report.info("Rest Get password : Welcome1" );
		report.info("Filter name : "+filterName );
		Map<String, String> restBody = restLib.restResponse("GET",oscUsername, "Welcome1", uri, "");
		String applicationID = restBody.get("ApplicationId");
		String count = restBody.get("count");
		String OscServerAddress = restBody.get("OscServerAddress");
		String statusCode = restBody.get("statusCode");

		if (OscServerAddress.contains(url) && count.equalsIgnoreCase("1")) {
			report.info("OscServerAddress is : " + OscServerAddress + " and count is : " + count);
			report.log("PASS", "Completed rest setup");
		} else {
			report.info("OscServerAddress is : " + OscServerAddress + " and count is : " + count);
			uri = url + patchUri + applicationID;
			statusCode = restLib.restResponse("PATCH", oscUsername, "Welcome1", uri,patchFilePath).get("statusCode");
			if (statusCode.equalsIgnoreCase("200")) {
				report.info("Status code in patch : " + statusCode);
				report.log("PASS", "verify patch request");
			} else {
				report.info("Status code in patch : " + statusCode);
				report.log("FAIL", "verify patch request");
			}

			restLib.addParameterToJsonFile(postFilePath, "OscServerAddress", url + ":443");
			restBody = restLib.restResponse("PATCH", oscUsername, "Welcome1", uri, postFilePath);
			statusCode = restBody.get("statusCode");
			OscServerAddress = restBody.get("OscServerAddress");
			if (statusCode.equalsIgnoreCase("200") && OscServerAddress.contains(url)) {
				report.info("Post Status code : " + statusCode);
				report.info("OscServerAddress is : " + OscServerAddress);
				report.log("PASS", "Verify Rest setup");
			} else {
				report.info("Post Status code : " + statusCode);
				report.info("OscServerAddress is : " + OscServerAddress);
				report.log("FAIL", "Verify  Rest setup");
			}
		}

		driver().get(url);
		commonLib.login(oscUsername);
		try {
			commonLib.navigateToProduct("Leads");
			click("XCreateLeadBtn");
			type("xLeadNameTxt", leadName);
			click("xProfileSaveBtn");
			think(10000);
		}

		catch (Exception e) {
			report.log(FAIL, "Failed to create lead with name : "+leadName);
		}

		try {
			commonLib.navigateToProduct("Opportunities");
			click("XCreateOpptyBtn");
			type("xOpptyNameTxt", opptyName);
			click("xProfileSaveBtn");
			
			if(isDisplayed("xOpptyDuplicateCloselink"))
			{
				click("xOpptyDuplicateCloselink");
				click("xCancelBtn");
			}
		}

		catch (Exception e) {
			report.log(FAIL, "Failed to create Oppty with name : "+opptyName);
		}

		try {
			commonLib.navigateToProduct("Accounts");
			click("XCreateAccountBtn");
			type("xOpptyNameTxt", accName);
			click("xProfileSaveBtn");
			if(isDisplayed("xAccDuplicateSelectBtn"))
			{
				click("xAccDuplicateSelectBtn");
			}
		}

		catch (Exception e) {
			report.log(FAIL, "Failed to create Account with name : "+accName);
		}

		commonLib.logOut();

		commonLib.login("Sales_Admin");
		commonLib.navigateToProduct("Setup and Maintenance");

		click("xLeftPanelBtn");
		click("xSearchBtn");
		think(3000);
		report.log(PASS, "navigated to task search page");
		clearText("xActivitiesSearchBox");
		type("xActivitiesSearchBox", "Manage Administrator Profile Values");
		report.log(PASS, "Task name entered");
		click("xActivitiesSearchBtn");

		String updatedTaskClick = replaceXpath("xTaskClick", "Manage Administrator Profile Values");
		click(updatedTaskClick);
		think(3000);

		clearText("xProfileCodeTxtBox");
		type("xProfileCodeTxtBox", "ZOE_GOI");
		click("xFilterSearch");
		think(3000);

		click("xAppoinmentProfileSelect");
		think(3000);
		clearText("xProfileValue");
		type("xProfileValue", "YES");
//		report.captureScreenShot("Appointement profile has been set to Yes");
		think(3000);
		click("xContactProfileSelect");
		think(3000);
		clearText("xProfileValue");
		type("xProfileValue", "YES");
//		report.captureScreenShot("Contact profile has been set to Yes");
		click("xProfileSaveBtn");
		report.log(PASS, "Profile values has been set to Yes successfully");
		commonLib.logOut();
		think(3000);	
	}

}
