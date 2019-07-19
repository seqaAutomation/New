package TestCases.CommonCrm;

import org.testng.annotations.Test;

import ProductLib.CommonCrm.CommonCrmLib;
import TestEngine.Base;
import TestEngine.DriverFactory;
import TestEngine.ReportCreator;

public class Test3 extends Base{
  @Test
  public void f() 
  {
	  CommonCrmLib crmLib = new CommonCrmLib();
	  crmLib.login("Ebenes");
//	  System.out.println("Started Test3");
//	  System.out.println("End Test3");
//	  type("test", ReportCreator.getTestName());
//	  report.log("PASS", ReportCreator.getTestName());
  }
}
