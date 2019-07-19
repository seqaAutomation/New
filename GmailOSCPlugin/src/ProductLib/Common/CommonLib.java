package ProductLib.Common;

import java.util.Random;

import TestEngine.Base;

public class CommonLib extends Base {

	public void login(String oscUserName) {
		report.info("UserName :" + oscUserName);
		type("xUserID", oscUserName);
		type("xPassword", "Welcome1");
		report.log(PASS, "Entered Login Credentials");
		click("xLoginBtn");
		click("xHomeBtn");
		report.log(PASS, "Logged into HomePage successfuly");
	}

	public static String generateRandomChars(int length) {
		String candidateChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
	    StringBuilder sb = new StringBuilder();
	    Random random = new Random();
	    for (int i = 0; i < length; i++) {
	        sb.append(candidateChars.charAt(random.nextInt(candidateChars
	                .length())));
	    }

	    return sb.toString();
	}
	public void navigateToProduct(String product) {
		click("xNavigator");
		think(3000);
		report.log(PASS, "Navigated to navigator page");

		String productXpath = replaceXpath("xProductNavigation", product);

		try {
			click(productXpath);
			report.info("Selected product : " + product);
		}

		catch (Exception e) {
			click("xNavigatorMore");
			report.info(product + " not found clicked on more");
			think(3000);
			click(productXpath);
			report.info("Selected product : " + product);
		}

		report.log(PASS, product + "  page displayed");
	}

	public void createSavedSearch(String saveSearch, String value) {
		click("xShowAdvanceSearch");
		click("xSaveSearchDrpDwn");
		report.log(PASS, "Adavance search opened");
		think(2000);
		String updatedxpath = replaceXpath("xSaveSearchSelect", saveSearch);
		click(updatedxpath);
		think(3000);
		click("xSaveSearchOperator");
		click("xSaveSearchOperatorValue");
		clearText("xSaveSearchSubjectText");
		type("xSaveSearchSubjectText", value);
		click("xSaveSearchSaveBtn");
		clearText("xCreateSaveSearchNameBox");
		type("xCreateSaveSearchNameBox", value);
		click("xSaveSearchAutoCheckBox");
		click("xSaveSearchOkBtn");

		if (isDisplayed("xExistingSearch")) {
			click("xExistingYesBtn");
		}
		report.log(PASS, "created save search " + saveSearch + " Starts With " + value);
	}

	public void logOut() {
		click("xLogoutDrpDwn");
		click("xSignOut");
		click("xlogOutConfirmBtn");
		think(3000);

	}

	public String scheduleESSJob(String jobName) {
		login("sales_admin");
		String url = fileUtility.getDataFromConfig("URL");
		navigateToProduct("Scheduled Processes");
		
		if(url.contains("fuscdrmsmc102") || url.contains("fuscdrmsmc305"))
		{
		if(isDisplayed("xScheduleProcessCollapsePane"))
		{
		click("xScheduleProcessCollapsePane");
		}
		
		else
		{
			report.info("Collapse pane not displayed");
		}
		}
		click("xFuseCreateScheduleProcess");
		click("xFuseSearchProcessList");
		click("xFuseSearch");
		clearText("xFuseSearchBox");
		type("xFuseSearchBox", jobName);
		click("xFilterSearch");
		think(3000);
		click("xFuseProcessSelect");
		click("xSaveSearchOkBtn");
		click("xStdExpScripValidOKBtn");
		click("xScheduleSubmit");
		String processId = getWebElement("xScheduleProcessID").getText();
		System.out.println(processId);
		processId = processId.replace("Process", "").replace("was", "").replace("submitted.", "").trim();
		System.out.println(processId);
		click("xSaveSearchOkBtn");
		click("xScheduleSearch");
		clearText("xSchdeduleProcessIDBox");
		type("xSchdeduleProcessIDBox", processId);
		click("xFilterSearch");
		think(3000);
		String path = replaceXpath("xProcessStatus", jobName);
		String status = getWebElement(path).getText();
		while (!status.equalsIgnoreCase("Succeeded")) {
			if (status.equalsIgnoreCase("Error")) {
				report.info("Scheduled process failed with error");
				break;

			}
			click("xScheduleRefresh");
			think(1000);
			status = getWebElement(path).getText();
			think(7000);
		}
		
		logOut();
		return status;
	}
}
