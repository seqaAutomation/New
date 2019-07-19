package TestCases.CommonCrm;

import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import TestEngine.Base;
import TestEngine.ReportCreator;


public class Test9 extends Base{
  @Test
  public void f() {
	  System.out.println("Started Test9");
	  System.out.println("End Test9");
	  type("test", ReportCreator.getTestName());
	  report.log("PASS", ReportCreator.getTestName());
//	  System.out.println(ReportCreator.getExtentTest());
//	  ReportCreator.getExtentTest().log(LogStatus.INFO, "Test1");
//	  type("test", "Test1");
//	  report.log("Pass", "Test1");
  }
}
