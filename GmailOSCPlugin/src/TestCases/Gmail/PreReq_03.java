package TestCases.Gmail;

import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;

import ProductLib.Common.CommonLib;
import ProductLib.Gmail.GmailLib;
import TestEngine.Base;

public class PreReq_03 extends Base{
  @Test
  public void f() {
	  
	  report.info("Test Case :Pre_Req to Delete exisisting data");
	  Map<String, String> testDataCommon = getTestData("commonTestData");
		Map<String, String> testData = getTestData("PreReq_03");
		String filterName = testDataCommon.get("filterName");
		String scheduleJobName = testDataCommon.get("scheduleJobName");
		String oscUserName = testDataCommon.get("oscUsername");
		String gmailPassword = testDataCommon.get("gmailPassword");
		String gmailUserName = testDataCommon.get("gmailUserName");
		String emailToselect = testData.get("emailToselect");
		String url = fileUtility.getDataFromConfig("URL");
	  	CommonLib commonLib = new CommonLib();
	  	driver().get(url);
	  	commonLib.login(oscUserName);
		commonLib.navigateToProduct("Contacts");
		click("xFuseSearchList");
		String listXpath = replaceXpath("xSaveSearchSelectList", filterName);
		click(listXpath);
		report.log(PASS, "Contact saved search filter selected");
		think(3000);
		String newPath = replaceXpath("xContactSelectList", filterName);
		while (isDisplayed(newPath)) {
			click(newPath);
			think(1000);
			click("xContactAction");
			think(1000);
			click("xDeleteContact");
			click("xConfirmationYesBtn");
			think(3000);

		}

		commonLib.navigateToProduct("Activities");
		click("xActivitiesTab");
		click("xFuseSearchList");
		listXpath = replaceXpath("xSaveSearchSelectList", filterName);
		click(listXpath);
		report.log(PASS, "Appointement saved search filter selected");

		think(3000);
		while (isDisplayed(newPath)) {
			click(newPath);
			think(5000);
			if (isDisplayed("xFuseSeriesRadioBtn")) {
				click("xFuseSeriesRadioBtn");
				click("xFuseOkBtn");
				think(1000);
			}
			think(3000);
			{
				int j = driver().findElements(By.xpath("//div[contains(@id,'subtitle:actions')]//descendant::span[text()='Actions']//parent::a")).size();
				report.info("Number of action elements found : "+j);
				while(j>0)
				{
					String xpath = "//div[contains(@id,'subtitle:actions')]//descendant::span[text()='Actions']//parent::a["+j+"]";
					System.out.println(xpath);
					if(isAvailable(xpath))
					try
					{
						click(xpath);
					}
					catch (Exception e) {
					}
					j--;
					if(isDisplayed("xDeleteAppointment"))
					{
						break;
					}
				}
			}
			click("xDeleteAppointment");
			click("xConfirmationYesBtn");
			think(3000);

		}

		commonLib.logOut();
		
		GmailLib gmailLib = new GmailLib();
		gmailLib.loginToGmail(emailToselect, oscUserName, url, gmailUserName, gmailPassword);
		
		String currentWindowHandle = loadUrlInNewTab("https://calendar.google.com");
		think(6000);
		try 
		{
			Actions act1 = new Actions(driver());
			act1.moveToElement(getWebElement("xCalendarLabel")).click(getWebElement("xGmailDeleteAppSettings")).click(getWebElement("xCalendarLabelOptions"))
					.build().perform();
			think(2000);
			try
			{
			click("xCalendarLabelSettings");
			}
			
			catch(Exception e)
			{
				act1.moveToElement(getWebElement("xCalendarLabel")).click().click(getWebElement("xCalendarLabelOptions"))
				.build().perform();
				click("xCalendarLabelSettings");
			}
			click("xAppointmentDelete");
			click("xAppointmentDeleteConfirmatio");
			think(3000);
			navigateTo("https://calendar.google.com");
			think(3000);
		}
		
		catch(Exception e)
		{
			driver().get("https://calendar.google.com");
		}
		while(isAvailable("xGmailAppointementList"))
		{
			click("xGmailAppointementList");
			click("xGmailDeleteApp");
			if(isAvailable("xDonsendBtn"))
			{
				click("xDonsendBtn");
			}
			if(isAvailable("xGmailDeleteRec"))
			{
				click("xGmailAllEventRadioButton");
				click("xGmailOkBtn");
			}
			
			think(2000);
		}
		
		if(!isAvailable("xGmailAppointementList"))
		report.captureScreenShot("All appointment deleted");
		else
			report.log(FAIL, "Verify previous appointements are deleted");
		navigateTo("https://contacts.google.com");
		
		try {
		while(isAvailable("xGmailContacts"))
		{
			click("xGmailContacts");
			think(3000);
			click("xGmailContactMoreActions");
			if(!isAvailable("xGmailDeleteContact"))
			{
				click("xGmailContactMoreActions");
			}
			
			click("xGmailDeleteContact");
			click("xGmailDeleteContact");
		}
		}
		catch(Exception e)
		{
			report.info("Unable to delete all contacts"+e.toString());
		}
		think(3000);
		click("xContactLabel");
		click("xContactLabelDeleteBtn");
		think(3000);

		try {
			click("xDeleteAllContactsRadioBtn");
			click("xContactDeleteBtn");
		}

		catch (Exception e) {

		}
		report.captureScreenShot("All contacts deleted");

		navigateTo(url);
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

  }
}
