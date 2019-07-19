package TestCases.Gmail;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;
import ProductLib.Common.CommonLib;
import ProductLib.Gmail.GmailLib;
import TestEngine.Base;
import TestEngine.DriverFactory;
import TestEngine.ReportCreator;

public class PreReq_02 extends Base {
	@Test
	public void testMethod() {
		report.info("Test Case :Pre_Req to Create filters and set filters in plugin");
		CommonLib commonLib = new CommonLib();
		Map<String, String> testDataCommon = getTestData("commonTestData");
		Map<String, String> testData = getTestData("PreReq_02");
		Map<String, String> testDataTC_05 = getTestData("TestCase_05_06");
		String filterName = testDataCommon.get("filterName");
		String scheduleJobName = testDataCommon.get("scheduleJobName");
		String oscUserName = testDataCommon.get("oscUsername");
		String emailTaskSub = testDataTC_05.get("emailTaskSub");
		String gmailPassword = testDataCommon.get("gmailPassword");
		String gmailUserName = testDataCommon.get("gmailUserName");
		String emailToselect = testData.get("emailToselect");
		String oscUsername = testDataCommon.get("oscUsername");
		String url = fileUtility.getDataFromConfig("URL");
		report.info("Url : " + url);
		navigateTo(url);	
		commonLib.login(oscUsername);
		commonLib.navigateToProduct("Activities");
		click("xActivitiesTab");
		commonLib.createSavedSearch("Subject", filterName);
		commonLib.navigateToProduct("Contacts");
		commonLib.createSavedSearch("Contact Name", filterName);
		
		
		
		GmailLib gmailLib = new GmailLib();
		gmailLib.loginToGmail(emailToselect, oscUserName, url, gmailUserName, gmailPassword);
		while(!isAvailable("xGmailSettingsDrpDwn"))
		{
			click("xGmailExtnSettingsnBtn");
			
			think(500);
			click("xGmailSettingBtn");
			
			Actions acti = new Actions(driver());
			think(4000);
			acti.moveToElement(getWebElement("xGmailExtnSettingsnBtn")).doubleClick();
			think(3000);
		}
		
		Select sel = new Select(getWebElement("xGmailSettingsDrpDwn"));
		think(3000);
		sel.selectByValue("CONTACT_SYNC");
		think(3000);
		Select sel1 = new Select(getWebElement("xGmailSavedSearchSelect"));
		think(3000);
		sel1.selectByVisibleText(filterName);
		think(3000);
		while(isDisplayed("xGmailLoading"))
		{
			think(5000);
		}
		
		String settingsValue = replaceXpath("xGmailPluginSettingValue",filterName );
		String isSelected = getWebElement(settingsValue).getAttribute("selected");
		
		System.out.println(isSelected);
	
		if(isSelected.equalsIgnoreCase("true"))
		{
			report.log(PASS, "Verify contact filter selected in OSC plugin");
		}
		
		else
		{
			{
				report.log(FAIL, "Verify contact filter selected in OSC plugin");
			}
		}
		
		isSelected = "false";
		sel = new Select(getWebElement("xGmailSettingsDrpDwn"));
		think(6000);
		sel.selectByValue("ACTIVITY_SYNC");
		think(5000);
		sel1 = new Select(getWebElement("xGmailSavedSearchSelect"));
		think(3000);
		sel1.selectByVisibleText(filterName);
		think(3000);
		while(isDisplayed("xGmailLoading"))
		{
			think(5000);
		}
		
		isSelected = getWebElement(settingsValue).getAttribute("selected");
		
		System.out.println(isSelected);
		if(isSelected.equalsIgnoreCase("true"))
		{
			report.log(PASS, "Verify Activity filter selected in OSC plugin");
		}
		
		else
		{
			{
				report.log(FAIL, "Verify Activity filter selected in OSC plugin");
			}
		}

	}
}
