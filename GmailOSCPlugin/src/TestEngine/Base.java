package TestEngine;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

import Helper.HelperFunctions;
import ProductLib.Gmail.GmailLib;
import Utilities.EmailUtility;
import Utilities.Report;

public class Base extends HelperFunctions {
  @BeforeSuite
  public void setup() 
  {
	  report = new Report();
	  
  }
  
  @BeforeClass
  public void s()
  {
	  WebDriver driver = DriverFactory.getInstance().invokeBrowser();
	  DriverFactory.setDriver(driver);
	  testName = this.getClass().getName().toString().replace("TestCases.", "");
	  ReportCreator.setTestName(testName);
	  System.out.println(ReportCreator.getTestName());
	  ExtentTest looger = ReportCreator.initialValue(ReportCreator.getTestName());
	  ReportCreator.setExtentTest(looger);
	  
  }
  
  @AfterClass
  public void end()
  {
	  ReportCreator.getInstance().removeExtentTest();
	  DriverFactory.getInstance().removeDriver(); 
  }
  
  @BeforeTest
  public void startTest(final ITestContext testContext) {
		    System.out.println(testContext.getName().substring(testContext.getName().indexOf("_")+1)); // it prints "Check name test"
		    
	  String productName = testContext.getName().substring(testContext.getName().indexOf("_")+1);
	  String release = fileUtility.getDataFromConfig("Release");
	  String reportFolder = release+"_"+productName+"_"+LocalDateTime.now().format(DateTimeFormatter.ofPattern("ddMMMYY_HHmmss"));
	  ReportCreator.setFolderName(reportFolder);
	  String reportFolderValue = ReportCreator.getFolderName();
	  System.out.println(reportFolderValue);
	  ReportCreator.setReports(new ExtentReports("./Reports/"+reportFolderValue+".html"));
//	  reports = new ExtentReports("./Reports/"+reportFolderValue+".html");
  }
  
  
  @AfterTest
  public void testA()
  {
	  System.out.println("end of test"+ReportCreator.getFolderName());
	  ReportCreator.getReports().flush();
	  reportPath.add(ReportCreator.getFolderName());
	  
  }
  
  @AfterSuite
  public void suiteA()
  {
  
	try
	{
	String sendEmail = fileUtility.getDataFromConfig("SendEmail");
	
	if(sendEmail.toLowerCase().equalsIgnoreCase("yes"))
	{
		String emailId = fileUtility.getDataFromConfig("EmailId");
	              if(reportPath.size()>0){
	            	  EmailUtility email = new EmailUtility();
	            	  String taskName = fileUtility.getDataFromConfig("Release");
	                  email.setSubject("SEQA Gmail Automation Test Result : "+taskName); // Set the email's subject field </li>
	                  email.setFrom("seqa_grp@oracle.com","SEQA Automation");
	                  email.setTo(emailId);
	                  StringBuffer mainContent = new StringBuffer();
	                  mainContent.append("Hi User, \n\n");
	                  mainContent.append("Please find the attached test results \n");
	                  mainContent.append("Regards, \n\n");
	                  mainContent.append("SEQA Automation Team \n\n");
	                  email.setMainContent(mainContent.toString());
	                  int k =1;
	                  String a ="";
	                  for(String fl : reportPath)
	                  {
	                	a ="./Reports/"+fl+".html";
	                  email.addAttachment(new File(a)); 
	                  System.out.println("Added "+k++);
	                  }// Add an attachment to the email </li>
	                  email.send(); // Send the email </li>
	                  }
	}
	}
	catch(Exception e)
	{
		System.out.println("Email not sent");
	}
  }
 
}
