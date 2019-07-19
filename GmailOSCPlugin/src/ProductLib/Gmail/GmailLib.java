package ProductLib.Gmail;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import org.testng.annotations.Test;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import TestEngine.Base;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;

public class GmailLib extends Base {
	@Test
	public void loginToGmail(String emailToselect,String oscUserName,String url,String gmailUserName,String gmailPassword) {
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
		think(6000);
		report.log(PASS, "Gmail Password entered : "+gmailPassword);
		report.log(PASS, "Logged in to Gmail successfully");
		String mailSubXpath = "(//span[text()='" + emailToselect + "'])[2]";
		click(mailSubXpath);
			driver().switchTo().frame(getWebElement("xGmailExtnFrame"));
			type("xGmailExtnHost", url);
			type("xGmailExtnUserName", oscUserName);
			type("xGmailExtnPassword", "Welcome1");
			click("xGmailExtnSignInBtn");
			think(10000);
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
			
			clickElement("xGmailselectaccountSelect");
			int c =0 ;
			while(!isDisplayed("xGmailAllowBtn"))
			{
				if(c==3)
				{
					break;
				}
				click("xGmailselectaccountSelect");
				think(3000);
				c++;
			}
			clickElement("xGmailAllowBtn");
			driver().switchTo().window(tabs.get(0));
			driver().switchTo().frame(getWebElement("xGmailExtnFrame"));
			while(isDisplayed("xGmailLoading"))
			{
				think(5000);
			}
			think(2000);
			report.log(PASS, "Logged in to OSC extension successfully");
	}
	
	public void sendMail(String reportName) throws AWTException 
	{
		Map<String, String> testDataCommon = getTestData("commonTestData");
		String gmailPassword = testDataCommon.get("gmailPassword");
		String gmailUserName = testDataCommon.get("gmailUserName");
		String emailId = fileUtility.getDataFromConfig("EmailId");
		think(20000);
		File file = new File("./Reports/"+reportName+".html");
		if(file.exists())
		{
			System.out.println("Report generated");
		}
		
		else
		{
			System.out.println("Report not generated");
		}
		String path = file.getAbsolutePath();
		System.out.println(path);
		driver().get("https://mail.google.com");
		think(5000);
		try {
			typeInMail("xGmailUserName", gmailUserName);
			clickInMail("xGmailNxtBtn");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			driver().get("https://mail.google.com");
			think(5000);
			typeInMail("xGmailUserName", gmailUserName);
			clickInMail("xGmailNxtBtn");
		}
		think(5000);
		typeInMail("xGmailPwd", gmailPassword);
		clickInMail("xGmailNxtBtn");
		think(6000);
		clickInMail("xGmailCompose");
//		clickInMail("xGmailToAddressClick");
		String emailIds[]=emailId.split(";");
		
		for(String e : emailIds)
		{
			typeInMail("xGmailToAddressTxtBox", e+";");
			think(2000);
		}
		typeInMail("xGmailSubjectTxtBox", "Report");
		typeInMail("xGmailBody", "FYI");
		think(1000);
		clickInMail("xGmailAttach");
		 report.captureImage(driver());
		StringSelection ss = new StringSelection(path);
	    Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
	    report.captureImage(driver());
	    Robot robot = new Robot();
	    robot.keyPress(KeyEvent.VK_ENTER);
	    robot.keyRelease(KeyEvent.VK_ENTER);
	    robot.keyPress(KeyEvent.VK_CONTROL);
	    report.captureImage(driver());
	    robot.keyPress(KeyEvent.VK_V);
	    robot.keyRelease(KeyEvent.VK_V);
	    robot.keyRelease(KeyEvent.VK_CONTROL);
	    robot.keyPress(KeyEvent.VK_ENTER);
	    report.captureImage(driver());
	    robot.keyRelease(KeyEvent.VK_ENTER);
	    think(10000);
	    int i =0;
	    report.captureImage(driver());
	    while(!isAvailable("xAttachment"))
	    {
	    	System.out.println("In loop "+i);
	    	clickInMail("xGmailAttach"); 
	    	ss = new StringSelection(path);
		    Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
		    robot.keyPress(KeyEvent.VK_ENTER);
		    robot.keyRelease(KeyEvent.VK_ENTER);
		    robot.keyPress(KeyEvent.VK_CONTROL);
		    robot.keyPress(KeyEvent.VK_V);
		    robot.keyRelease(KeyEvent.VK_V);
		    robot.keyRelease(KeyEvent.VK_CONTROL);
		    robot.keyPress(KeyEvent.VK_ENTER);
		    robot.keyRelease(KeyEvent.VK_ENTER);
		    think(20000);
		    report.captureImage(driver());
		    i++;
		    if(i>1)
		    {
		    	break;
		    }
	    }
	    clickInMail("xGmailSendBtn");
	    System.out.println("Finished");
	}
}
