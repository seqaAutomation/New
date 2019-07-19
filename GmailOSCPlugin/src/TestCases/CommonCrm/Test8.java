package TestCases.CommonCrm;

import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import TestEngine.Base;
import TestEngine.ReportCreator;


public class Test8 extends Base{
  @Test
  public void f() {
	  System.out.println("Started Test8");
	  System.out.println("End Test8");
	  type("test", ReportCreator.getTestName());
	  report.log("PASS", ReportCreator.getTestName());
//	  System.out.println(ReportCreator.getExtentTest());
//	  ReportCreator.getExtentTest().log(LogStatus.INFO, "Test4");
//	  type("test", "Test4");
//	  report.log("Pass", "Test4");
  }
}
