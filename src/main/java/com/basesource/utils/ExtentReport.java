package com.basesource.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import org.testng.xml.XmlSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.basesource.action.PreDefinedActions;
import com.daasui.constants.ConstantPath;

public class ExtentReport extends TestListenerAdapter implements IReporter {

	private ExtentReports extent = ExtentManager.getInstance();
	private static ThreadLocal<ExtentTest> parentTest = new ThreadLocal<ExtentTest>();
	private static ThreadLocal<ExtentTest> test = new ThreadLocal<ExtentTest>();
	private ExtentTest parent, child;
	public static List<String> testClassName = new ArrayList<String>();
	public static List<Integer> passCount = new ArrayList<Integer>();
	public static List<Integer> failCount = new ArrayList<Integer>();
	public static List<Integer> skipCount = new ArrayList<Integer>();
	public static int totalTest;
	private final static Logger LOGGER = LogManager.getLogger(PreDefinedActions.class);
	//public static String buildNumber = System.getProperty("buildNumber");
	public static String environment = System.getProperty("environment");
	public static String browser = System.getProperty("browserName");
	private static Properties emailDataProperties = new Properties();
	static File emailDataFile = new File(ConstantPath.EMAIL_FILE_PATH + "EmailRawData.properties");

	@Override
	public synchronized void onStart(ITestContext context) {
		parent = extent.createTest(context.getName());
		parentTest.set(parent);
	}

	@Override
	public synchronized void onFinish(ITestContext context) {
		extent.flush();
	}

	@Override
	public synchronized void onTestStart(ITestResult result) {
		child = parentTest.get().createNode(result.getMethod().getMethodName());
		test.set(child);
	}

	@Override
	public synchronized void onTestSuccess(ITestResult result) {
		test.get().pass("Test passed");
	}

	@Override
	public synchronized void onTestFailure(ITestResult result) {
		test.get().fail(result.getThrowable());
		String methodName = result.getName().toString().trim();
		String path = this.takeScreenShot(methodName);

		// test with screenshot
		try {
			test.get().fail("Screenshot", MediaEntityBuilder.createScreenCaptureFromPath(path).build());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public synchronized void onTestSkipped(ITestResult result) {
		test.get().skip(result.getThrowable());
	}

	public final String takeScreenShot(final String methodName) {
		// get the driver
		WebDriver DRIVER = PreDefinedActions.getDriver();

		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy__hh_mm_ssaa");

		// Take screenshot
		File scrFile = ((TakesScreenshot) DRIVER).getScreenshotAs(OutputType.FILE);

		String screenshotPath = ConstantPath.SCREENSHOOT_FOLDER_PATH + methodName + " - " + dateFormat.format(new Date()) + ".png";

		// The below code will save the screen shot in the drive with test
		// method name and date-time
		try {
			FileUtils.copyFile(scrFile, new File(ConstantPath.SCREENSHOOT_FOLDER_PATH + methodName + " - " + dateFormat.format(new Date()) + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return screenshotPath;
	}

	public void generateReport(List<XmlSuite> xmlSuite, List<ISuite> iSuiteList, String outputDirectory) {
		ITestContext context = null;
		String suiteName = null;
		int groupNames = 0;
		String timeStamp = System.getProperty("app.timestamp");
		Collection<ITestNGMethod> testCaseFailFullList;
		Collection<ITestNGMethod> testCaseSkipFullList;
		Collection<ITestNGMethod> testCasePassFullList;
		// Second parameter of this method ISuite will contain all the suite executed.
		for (ISuite iSuite : iSuiteList) {
			// Get a map of result of a single suite at a time
			Map<String, ISuiteResult> results = iSuite.getResults();
			totalTest = iSuite.getAllMethods().size();

			if (results.size() > 0) {
				// Get the key of the result map
				Set<String> keys = results.keySet();
				// Go to each map value one by one
				for (String key : keys) {
					context = results.get(key).getTestContext();
					suiteName = context.getCurrentXmlTest().getSuite().getName();
					groupNames = context.getCurrentXmlTest().getIncludedGroups().size();
					// The Context object of current result

					testClassName.add(results.get(key).getTestContext().getName());

					testCaseFailFullList = results.get(key).getTestContext().getFailedTests().getAllMethods();
					testCaseSkipFullList = results.get(key).getTestContext().getSkippedTests().getAllMethods();
					testCasePassFullList = results.get(key).getTestContext().getPassedTests().getAllMethods();
					
					if (testCaseSkipFullList.size() > 0) {
						for (ITestNGMethod method : testCaseSkipFullList) {
							if (testCaseFailFullList.contains(method) || testCasePassFullList.contains(method)) {
								testCaseSkipFullList.remove(method);
							}
						}
					}

					failCount.add(testCaseFailFullList.size());
					passCount.add(testCasePassFullList.size());
				    skipCount.add(testCaseSkipFullList.size());
				}

			} else
				LOGGER.info("Suite result is null");

		}
		try {
			provideDataInEmailFile(suiteName, groupNames, timeStamp);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void provideDataInEmailFile(String suite, int groupSize, String timeStamp) throws FileNotFoundException, IOException {
		int totalTestCount = totalTest;
		int totalPassedCount = 0, totalFailedCount = 0, totalSkippedCount = 0;
		for (int i = 0; i < testClassName.size(); i++) {
			totalPassedCount = totalPassedCount + passCount.get(i);
			totalFailedCount = totalFailedCount + failCount.get(i);
			totalSkippedCount = totalSkippedCount + skipCount.get(i);
		}

		double passpercent = 0;
		double failpercent = 0;
		double skippercent = 0;

		DecimalFormat f = new DecimalFormat("0.00");

		if (totalTestCount > 0) {
			passpercent = (double) (totalPassedCount * 100) / (totalPassedCount + totalFailedCount + totalSkippedCount);
			failpercent = (double) (totalFailedCount * 100) / (totalPassedCount + totalFailedCount + totalSkippedCount);
			skippercent = (double) (totalSkippedCount * 100) / (totalPassedCount + totalFailedCount + totalSkippedCount);
		}

		String color = "green";
		String emailContent = "<table width=100% ><tr bgcolor=" + color + "  align='center'><FONT COLOR=white FACE=Arial SIZE=2.5><h3>Selenium Test Automation Execution Report for DaaS" + "</h3></tr></table>";
		emailContent = emailContent + "<br><h3><FONT COLOR=#153E7E FACE=Arial SIZE=2.75><b> Test Results Summary </b></h3>";
		emailContent = emailContent + "<table cellspacing=1 cellpadding=1  border=1 width=300><tr><td width=150 align=left>" + "<FONT COLOR=#153E7E FACE=Arial SIZE=2.75><b>Total Tests executed</b></td><td width=100 align='center'><FONT COLOR=#153E7E FACE=Arial SIZE=2.75><b>" + String.valueOf(totalTestCount) + "</b></td>" + "<td><span><FONT COLOR=#153E7E FACE=Arial SIZE=2.75><b></b></span></td></tr><tr><td width=150 align=left><FONT COLOR=#153E7E FACE=Arial SIZE=2.75>"
				+ "<b>Total Passed</b></td><td  width=100 align='center'><FONT COLOR=#153E7E FACE=Arial SIZE=2.75><b>" + String.valueOf(totalPassedCount) + "</b></td>" + "<td width=100 align='center' bgcolor=green><span><FONT COLOR=#153E7E FACE=Arial SIZE=2.75><b>" + f.format(passpercent) + "%</b></span></td></tr>" + "<tr><td width=100 align=left><FONT COLOR=#153E7E FACE=Arial SIZE=2.75><b>Total Skipped</b></td><td width=50 align='center'><FONT COLOR=#153E7E FACE=Arial SIZE=2.75><b>"
				+ String.valueOf(totalSkippedCount) + "</b></td>" + "<td width=50 align='center' bgcolor=orange><span><FONT COLOR=#153E7E FACE=Arial SIZE=2.75><b>" + f.format(skippercent) + "%</b></span></td></tr>" + "<tr><td width=100 align=left><FONT COLOR=#153E7E FACE=Arial SIZE=2.75><b>Total Failed</b></td><td width=50 align='center'><FONT COLOR=#153E7E FACE=Arial SIZE=2.75><b>" + String.valueOf(totalFailedCount) + "</b></td>"
				+ "<td width=50 align='center' bgcolor=red><span><FONT COLOR=#153E7E FACE=Arial SIZE=2.75><b>" + f.format(failpercent) + "%</b></span></td></tr>" + "<tr><td width=100 align=left><FONT COLOR=#153E7E FACE=Arial SIZE=2.75><b>Environment</b></td><td width=150 align='center'><FONT COLOR=#153E7E FACE=Arial SIZE=2.75><b>" + environment + "</b></td>"
				+ "<tr><td width=100 align=left><FONT COLOR=#153E7E FACE=Arial SIZE=2.75><b>Browser</b></td><td width=150 align='center'><FONT COLOR=#153E7E FACE=Arial SIZE=2.75><b>" + browser + "</b></td>" + "<tr><td width=100 align=left><FONT COLOR=#153E7E FACE=Arial SIZE=2.75><b>ToTal Execution Time (in Minutes)</b></td><td width=150 align='center'><FONT COLOR=#153E7E FACE=Arial SIZE=2.75><b>" + MSTeam.totalTimeInMinute + "</b></td></table>";
		emailContent = emailContent + "<br><h3><FONT COLOR=#153E7E FACE=Arial SIZE=2.75><b> Test Results Summary Class Wise</b></h3>";
		String header = "<tr><td><FONT COLOR=#153E7E FACE=Arial SIZE=2.75><b>Test Case Name</b></td><td><FONT COLOR=#153E7E FACE=Arial SIZE=2.75><b>Test Case Passed</b></td><td><FONT COLOR=#153E7E FACE=Arial SIZE=2.75><b>Percentage</b></td><td><FONT COLOR=#153E7E FACE=Arial SIZE=2.75><b>Test Case Failed</b></td><td><FONT COLOR=#153E7E FACE=Arial SIZE=2.75><b>Percentage</b></td><td><FONT COLOR=#153E7E FACE=Arial SIZE=2.75><b>Test Case Skip </b></td><td><FONT COLOR=#153E7E FACE=Arial SIZE=2.75><b>Percentage</b></td>";
		String CountHtml = "<tr>";

		for (int i = 0; i < testClassName.size(); i++) {
			DecimalFormat format = new DecimalFormat("0.00");
			int passCount = ExtentReport.passCount.get(i);
			int failCount = ExtentReport.failCount.get(i);
			int skipCount = ExtentReport.skipCount.get(i);
			int totalTest = passCount + failCount + skipCount;

			if (totalTest > 0) {
				passpercent = (double) (passCount * 100) / (totalTest);
				failpercent = (double) (failCount * 100) / (totalTest);
				skippercent = (double) (skipCount * 100) / (totalTest);
			}
			header = header + "<tr><td width=150 align=left> <FONT COLOR=#153E7E FACE=Arial SIZE=2.75><b>" + ExtentReport.testClassName.get(i) + "</b></td>";
			CountHtml = "<td><FONT COLOR=#153E7E FACE=Arial SIZE=2.75>" + passCount + "</td><td width=100 align='center' bgcolor=green>" + format.format(passpercent) + " %" + "<td><FONT COLOR=#153E7E FACE=Arial SIZE=2.75>" + failCount + "</td><td width=100 align='center' bgcolor=red>" + format.format(failpercent) + " %" + "<td><FONT COLOR=#153E7E FACE=Arial SIZE=2.75>" + skipCount + "</td><td width=100 align='center' bgcolor=orange>" + format.format(skippercent) + " %" + "</tr>";
			header = header + CountHtml;
		}
		String tableTestResult = "<table cellspacing=1 cellpadding=1  border=1>" + header + "</table>";
		emailContent = emailContent + tableTestResult;

		emailDataProperties.setProperty("suiteName", suite);
		emailDataProperties.setProperty("groupNames", String.valueOf(groupSize));
		emailDataProperties.setProperty("timeStamp", timeStamp);
		emailDataProperties.setProperty("environment", environment);
		emailDataProperties.setProperty("emailContent", emailContent);
		emailDataProperties.store(new FileOutputStream(emailDataFile), null);
	}

}
