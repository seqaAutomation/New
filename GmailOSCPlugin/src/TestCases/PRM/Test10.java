package TestCases.PRM;

import java.io.File;

import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import TestEngine.Base;
import TestEngine.ReportCreator;


public class Test10 extends Base{
  @Test
  public void f() {
	  System.out.println("Started Test10");
	  System.out.println("End Test10");
//	  type("test", ReportCreator.getTestName());
	  report.log("PASS", ReportCreator.getTestName());
	  report.log("PASS", System.getProperty("url"));
//	  
//	  
//	  File directory = new File("./src/test/com/TestCases");
//
//	    // get all the files from a directory
//	    File[] fList = directory.listFiles();
//
//	    for (File file : fList) {
//	      if (file.isDirectory()) {
//	            System.out.println(file.getAbsolutePath());
//	            
//	            String a = file.getName();
//	            System.out.println(a);
//	        }
//	    }
//	  System.out.println(ReportCreator.getExtentTest());
//	  ReportCreator.getExtentTest().log(LogStatus.INFO, "Test1");
//	  type("test", "Test1");
//	  report.log("Pass", "Test1");
  }
}
