package ProductLib.CommonCrm;

import org.testng.annotations.Test;

import TestEngine.Base;

public class CommonCrmLib extends Base{

  public void login(String userName) 
  {
	String oscUserName = userName;
   	report.captureScreenShot("UserName :" +oscUserName);
   	type("xUserID", oscUserName);
   	type("xPassword", "Welcome1");
   	report.log("PASS", "Entered Login Credentials");
   	click("xLoginBtn");
   	click("xHomeBtn");
   	report.log("PASS", "Logged into HomePage successfuly");
   	report.captureScreenShot("Home Page Displayed");
  }
}
