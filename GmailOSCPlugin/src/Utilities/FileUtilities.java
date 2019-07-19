package Utilities;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class FileUtilities {
	

	public static String  getDataFromConfig(String key) 
	  {
		  File file = new File ("./Files/config.properties");
		  Properties pro = null;
		  try {
			  FileInputStream fis = new FileInputStream(file);
			  pro = new Properties();
			  pro.load(fis);
		  	  } 
		  catch (Exception e) 
		  	{
			e.printStackTrace();
		  	}
		  
		  String data = pro.getProperty(key);
		return data; 
		
	  }
	public  Map  getTestData(String parameter,String packageName) 
	  {
		  File file = new File ("./src/ProductLib/"+packageName+"/TestData.properties");
		  Map<String,String> dataMap = new HashMap<String,String>();
		  Properties pro = null;
		  try {
			  FileInputStream fis = new FileInputStream(file);
			  pro = new Properties();
			  pro.load(fis);
		  	  } 
		  catch (Exception e) 
		  	{
			e.printStackTrace();
		  	}
		  
		  String data = pro.getProperty(parameter);
		  String dataArray[]=data.split(";");
		  
		  for(String dataValue : dataArray)
		  {
		  String d[]= dataValue.split("=",2);
		  dataMap.put(d[0],d[1]);
		  }
		return dataMap;
		}
	
	public  Workbook readFromExcel(String path)
	  {
		Workbook wb = null; 
		File file = new File(path);
		System.out.println(path);
		System.out.println(file.getPath());
		try 
		{
		FileInputStream fis = new FileInputStream(file);
		wb = new XSSFWorkbook(fis);
		
		}
		catch(Exception e)
		{
			System.out.println("Unable to load file"+e.toString());
		}
		return wb;
	  }
	
	public List getClassName()
	  {
		Workbook wb = readFromExcel("./Files/TestExecutionSheet.xlsx");
		Sheet sh = wb.getSheetAt(0);
		Row row = null;
		List<String> classList =new  ArrayList<String>();
		for(int i = 1;i<=sh.getLastRowNum();i++)
		{
			row = sh.getRow(i);
			if(row.getCell(5).getStringCellValue().equalsIgnoreCase("Y"))
			{
			String testName = row.getCell(2).getStringCellValue();
			String packageName = row.getCell(3).getStringCellValue();
			String className = "testCases."+packageName+"."+testName;
			classList.add(className);
			}
		}
		return classList;
		  
	  }
	  
	  public  String xpath(String sheetName,String xpathValue)
	  {
		  Workbook wb = readFromExcel("./Files/OR.xlsx");
		  String xpath="";
		  Sheet sheet = wb.getSheet(sheetName);
		  int rowCount = sheet.getLastRowNum();
		    for (int i = 1; i < rowCount+1; i++) 
		    {
		        Row row = sheet.getRow(i);
		        String a = row.getCell(0).getStringCellValue();
		        if(row.getCell(0).getStringCellValue().equalsIgnoreCase(xpathValue))
		        {
		        	xpath = row.getCell(1).getStringCellValue();
		        	break;
		        }
		    }
		    if(xpath.isEmpty())
		    {
		    	xpath = xpathValue;
		    }
			return xpath;  
	  }
}
