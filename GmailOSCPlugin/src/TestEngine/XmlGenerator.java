package TestEngine;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.testng.TestNG;
import org.testng.annotations.Test;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlSuite.ParallelMode;
import org.testng.xml.XmlTest;

import Helper.HelperFunctions;
import Utilities.FileUtilities;

public class XmlGenerator extends HelperFunctions {
	@Test
	public void xmlCreate() {
		XmlSuite suite = new XmlSuite();
		suite.setName("OSCExecutionSuite");
		if (fileUtility.getDataFromConfig("ProductParallelRun").equalsIgnoreCase("Yes")) {
			suite.setParallel("tests");
			suite.setThreadCount(Integer.parseInt(fileUtility.getDataFromConfig("ParallelSessions")));
		}

		else {
			suite.setParallel("Suite");
			suite.setThreadCount(Integer.parseInt(fileUtility.getDataFromConfig("ParallelSessions")));
		}

		List<String> lclasses = new ArrayList<String>();
		lclasses.add("TestEngine.Listener");
		suite.setListeners(lclasses);
		Map<String, List<String>> classList = getClassNameM();
		List<XmlTest> myTests = new ArrayList<XmlTest>();
		List<XmlTest> testList = new ArrayList<XmlTest>();

		int i = 0;
		for (Entry<String, List<String>> entry : classList.entrySet()) {

			testList.add(new XmlTest(suite));
			testList.get(i).setName("TestSet_" + entry.getKey());

			if (fileUtility.getDataFromConfig("TestCaseInProductParallelRun").equalsIgnoreCase("Yes")) {
				testList.get(i).setParallel(ParallelMode.CLASSES);
				testList.get(i).setThreadCount(2);
			}
			
			
			List<ArrayList<XmlClass>> list = new ArrayList<ArrayList<XmlClass>>();
			list.add(new ArrayList<XmlClass>());
			List<String> className = entry.getValue();

			for (String classNameValue : className) {
				System.out.println(classNameValue);
				list.get(0).add(new XmlClass(classNameValue));
			}
			testList.get(i).setXmlClasses(list.get(0));
			myTests.add(testList.get(i));
			i++;
		}
		
		suite.setTests(myTests);
		List<XmlSuite> suites = new ArrayList<XmlSuite>();
		suites.add(suite);

		File file = new File("./Resources/TestNG.xml");
		System.out.println("file" + file);
		try {
			FileWriter writer = new FileWriter(file);
			writer.write(suite.toXml());
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		TestNG tng = new TestNG();
		tng.setXmlSuites(suites);
		tng.run();
	}

	public static Map getClassNameM() {

		String product = fileUtility.getDataFromConfig("Product");
		String productArray[] = product.split(";");
		File directory = new File("./src/TestCases");
		List<String> productList = new ArrayList<String>();
		Map<String, List<String>> productClass = new HashMap<String, List<String>>();
		Workbook wb = null;
		String executionSheetpath = null;

		if (productArray[0].equalsIgnoreCase("All")) {

			File[] fList = directory.listFiles();

			for (File file : fList) {
				if (file.isDirectory()) {
					String a = file.getName();
					System.out.println(a);
					productList.add(a);
				}
			}

			for (String products : productList) {
				executionSheetpath = "./src/ProductLib/" + products + "/TestExecutionSheet.xlsx";
				wb = fileUtility.readFromExcel(executionSheetpath);
				Sheet sh = wb.getSheetAt(0);
				Row row = null;
				List<String> classList = new ArrayList<String>();
				for (int i = 1; i <= sh.getLastRowNum(); i++) {
					row = sh.getRow(i);
					if (row.getCell(5).getStringCellValue().equalsIgnoreCase("Y")) {
						String testName = row.getCell(2).getStringCellValue();
						String className = "TestCases." + products + "." + testName;
						classList.add(className);
					}
				}

				productClass.put(products, classList);
			}

		}

		else {
			for (String products : productArray) {
				if (!products.equalsIgnoreCase("All")) {
					executionSheetpath = "./src/ProductLib/" + products + "/TestExecutionSheet.xlsx";
					wb = fileUtility.readFromExcel(executionSheetpath);
					Sheet sh = wb.getSheetAt(0);
					Row row = null;
					List<String> classList = new ArrayList<String>();
					for (int i = 1; i <= sh.getLastRowNum(); i++) {
						row = sh.getRow(i);
						if (row.getCell(5).getStringCellValue().equalsIgnoreCase("Y")) {
							String testName = row.getCell(2).getStringCellValue();
							String className = "TestCases." + products + "." + testName;
							classList.add(className);
						}
					}

					productClass.put(products, classList);
				}
			}
		}

		return productClass;

	}

	public static List getClassName() {
		Workbook wb = fileUtility.readFromExcel("./Files/TestExecutionSheet.xlsx");
		Sheet sh = wb.getSheetAt(0);
		Row row = null;
		List<String> classList = new ArrayList<String>();
		for (int i = 1; i <= sh.getLastRowNum(); i++) {
			row = sh.getRow(i);
			if (row.getCell(5).getStringCellValue().equalsIgnoreCase("Y")) {
				String testName = row.getCell(2).getStringCellValue();
				String packageName = row.getCell(3).getStringCellValue();
				String className = "TestCases." + packageName + "." + testName;
				classList.add(className);
			}
		}
		return classList;

	}
}
