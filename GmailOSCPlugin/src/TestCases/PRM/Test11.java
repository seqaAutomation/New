package TestCases.PRM;

import org.testng.annotations.Test;

import TestEngine.Base;
import TestEngine.DriverFactory;
import TestEngine.ReportCreator;


public class Test11 extends Base{
  @Test
  public void f() {
//	  DriverFactory.getDriver().get("https://www.google.com");
	  System.out.println("Started Test11");
	  System.out.println("End Test11");
	  type("test", ReportCreator.getTestName());
	  report.log("PASS", ReportCreator.getTestName());
//	  type("test", "Test2");
//	  report.log("PASS", "Test2");
//	  
//	  driver().get("https://facebook.com");
//	  report.log("PASS", "Facebook");
  }
}
