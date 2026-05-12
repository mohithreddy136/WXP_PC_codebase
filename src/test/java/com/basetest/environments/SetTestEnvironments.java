package com.basetest.environments;

import java.io.*;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import com.daasui.constants.CommonVariables;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.basesource.action.CommonMethod;
import com.basesource.action.PreDefinedActions;
import com.basesource.driver.DriverFactory;
import com.basesource.utils.ExcelReader;
import com.basesource.utils.ExtentManager;
import com.basesource.utils.LaunchDarkly;
import com.basesource.utils.MSTeam;
import com.basesource.utils.ObjectReader;
import com.basesource.utils.PagerDutyNotification;
import com.basesource.utils.RetryFailedTestCases;
import com.daasui.constants.ConstantPath;
import com.daasui.pages.LoginPage;
import com.daasui.pages.StatusAPIPage;
import com.daasui.pages.WelcomePage;
import com.google.common.base.Strings;
import com.testscripts.daasui.CommonTest;
import com.testscripts.daasui.SiteUpTest;


//import atu.testrecorder.ATUTestRecorder;
//import atu.testrecorder.exceptions.ATUTestRecorderException;

public class SetTestEnvironments extends CommonMethod {
	private final static Logger LOGGER = LogManager.getLogger(SetTestEnvironments.class);
	DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy__hh_mm_ssaa");
	public static String TestCaseName;
	private static Properties environmentPageProperties;
	private static ObjectReader environmentPropertiesReader = new ObjectReader();
	private static Properties languageCodePageProperties;
	private static ObjectReader languageCodePagePropertiesReader = new ObjectReader();
	//public String filePath;
	public static String LanguageCode;
	public static String environment = System.getProperty("environment");
	public static String browser = System.getProperty("browserName");
	public static String runMode = System.getProperty("runMode");
	public String startTime;
	public String endTime;
	public static boolean siteUpRunOrNot = false;
	public static boolean failureCheck = false;
	public static boolean haltExecution = false;
	public RetryFailedTestCases retryTc = new RetryFailedTestCases();
	private static Properties pagerDutyProperties = new Properties();
	static File pagerDutyFile = new File(ConstantPath.PROPERTIES_FOLDER_PATH + "PagerDutyCheck.properties");
	//ATUTestRecorder recorder;
	private static Properties emailDataProperties = new Properties();
	static File emailDataFile = new File(ConstantPath.EMAIL_FILE_PATH + "EmailRawData.properties");
	static String HAR_PATH = System.getProperty("user.dir") + File.separator + "test-report" + File.separator;
	public static String  failureReason = null;
	public HashMap <String,String> requestIdAndTransactionId=new HashMap <String,String>();
	public List<LogEntry> logEntries=null;
	public HashMap <String,String> requestIdAndStatusCode=new HashMap <String,String>();
	public Set<String> transactionIdSet=new HashSet<String>();
	private static NetworkLogsCapture networkCapture = new NetworkLogsCapture();
	public static String suiteName;

	public final void readPagerDutyFile() throws Exception {
		InputStream fileInput = null;
		try {
			if (pagerDutyFile.exists()) {
				fileInput = new FileInputStream(pagerDutyFile);
			} else {
				fileInput = ObjectReader.class.getClassLoader().getResourceAsStream("properties/" + "PagerDutyCheck" + ".properties");
			}
			pagerDutyProperties.load(fileInput);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public final void readEmailDataFile() throws Exception {
		InputStream fileInput = null;
		try {
			if (emailDataFile.exists()) {
				fileInput = new FileInputStream(emailDataFile);
			} else {
				fileInput = ObjectReader.class.getClassLoader().getResourceAsStream("test-Email/" + "EmailRawData" + ".properties");
			}
			emailDataProperties.load(fileInput);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@BeforeSuite(alwaysRun = true)
	public final void beforeSuite(ITestContext testContext) throws Exception {
		int countFiles = 0;
		suiteName = testContext.getCurrentXmlTest().getSuite().getName();
		LOGGER.info("Executing suite : " + suiteName);
		//this.deleteAndCreateDir(ConstantPath.LOG_PATH);
		
		FileUtils.write(new File(ConstantPath.LOG_PATH + "/applog.log"), "", Charset.defaultCharset());
		init();
		//this.deleteAndCreateDir(ConstantPath.DESKTOP_VIDEO_PATH);
		this.deleteAndCreateDir(ConstantPath.SCREENSHOOT_FOLDER_PATH);
		this.deleteAndCreateDir(ConstantPath.EMAIL_FILE_PATH);
		this.deleteAndCreateDir(ConstantPath.PAGECOUNT_PATH);
		this.deleteAndCreateDir(ConstantPath.UNIQUEPAGECOUNT_PATH);
		this.deleteAndCreateDir(ConstantPath.LATENCY_PATH);
		this.deleteAndCreateDir(ConstantPath.IMPORT_PATH + CommonVariables.LOADTIME);

		createCSVFile(ConstantPath.PAGECOUNT_PATH+"pageCount.csv");
		createCSVFile(ConstantPath.UNIQUEPAGECOUNT_PATH+"uniquePageCount.csv");
		createCSVFile(ConstantPath.LATENCY_PATH+"latencyTime.csv");
		createCSVFile(ConstantPath.IMPORT_PATH + CommonVariables.LOADTIME+ CommonVariables.CSV_FILENAME);

		// Delete the files from report folder only after 10, need to see the old reports for triaging
		File f = new File(ConstantPath.REPORT_PATH);
		if (f.exists() == false) {
			File file = new File(ConstantPath.REPORT_PATH);
			FileUtils.forceMkdir(file);
		} else {
			if (f.listFiles().length > 0) {
				for (File file : f.listFiles()) {
					if (file.isFile()) {
						countFiles++;
					}
				}
				if (countFiles >= 10) {
					this.deleteAndCreateDir(ConstantPath.REPORT_PATH);
					LOGGER.info("File count is greater than 10, deleting the folder");
				} else {
					LOGGER.info("File count is less than 10, not deleting the folder");
				}
			} else {
				LOGGER.info("No file is present in the report folder, so folder deletion not needed");
			}
		}

		this.deleteAndCreateDir(ConstantPath.DOWNLOAD_PATH);

		// Excel should be ready from IDE folder or Jar.
		File excelFile = new File(ConstantPath.EXCEL_PATH);
		if (excelFile.exists()) {
			// For IDE folder path
			this.excelReader = new ExcelReader(ConstantPath.EXCEL_PATH);
		} else {
			// For JAR
			String fileName = "testdata/testdata.xlsx";
			InputStream excelStream = SetTestEnvironments.class.getClassLoader().getResourceAsStream(fileName);

			// Get the byte array of the file stream
			byte[] buffer = new byte[excelStream.available()];
			excelStream.read(buffer);

			// Create a new temp testdata file
			File targetFile = new File(fileName);
			targetFile.getParentFile().mkdir();
			targetFile.createNewFile();

			// Save the temp file
			OutputStream outStream = new FileOutputStream(targetFile);
			outStream.write(buffer);

			// Add the suite file
			this.excelReader = new ExcelReader(targetFile.getPath());
			outStream.close();
		}



		if (testContext.getCurrentXmlTest().getIncludedGroups().size() > 0) {
			if (environment.equalsIgnoreCase("US-PROD-WEP") || environment.equalsIgnoreCase("EU-PROD-WEP")) {
				readPagerDutyFile();
			}
		}
	}

	/*
	 * public void recorderStart(String extension) throws ATUTestRecorderException { recorder = new ATUTestRecorder(ConstantPath.DESKTOP_VIDEO_PATH + File.separator, extension,
	 * false); recorder.start(); }
	 */

	@BeforeMethod(alwaysRun = true)
	public void beforeMethod(Method MethodName, ITestContext context, ITestResult result)  {
		LaunchDarkly ldinstance = new LaunchDarkly().getInstance();
		if (MethodName.getName().equals("SiteUpTest")) {
			siteUpRunOrNot = true;
		}
		if (!haltExecution) {
			DRIVER = DriverFactory.setDriver(System.getProperty("browserName"), SetTestEnvironments.getEnvironment(System.getProperty("environment")));
			setWebDriver(DRIVER);
			jsDriver();
			LanguageCode = SetTestEnvironments.getLanguageCode(System.getProperty("languageCode"));
			LOGGER.info("Language code is set to : " + LanguageCode);
				TestCaseName = MethodName.getName();
				/*
				 * String extension = "DesktopRecorder-" + TestCaseName + dateFormat.format(new Date()); String dir = ConstantPath.DESKTOP_VIDEO_PATH + File.separator +
				 * "Recording"; filePath = dir + File.separator + extension + ".mov"; recorderStart(extension);
				 */
				LOGGER.info("Test Started : " + TestCaseName);
			//}
		} else {
			LOGGER.error("Previous test case has failed. Test execution ended.");
			throw new SkipException("Previous test case has failed. Test execution ended.");
		}

		// Setup network logging for capturing failed test network activity
		try {
			networkCapture.setupNetworkLogging(DRIVER);
			
			 // Only capture network logs with response body for PERF_SANITY group
	        List<String> groups = Arrays.asList(result.getMethod().getGroups());
	        if (groups.contains("PERF_SANITY")) {
	            networkCapture.CaptureNetworkLogsWithResponseBody();
	        }
			networkCapture.clearLogs(); // Clear any previous logs
			LOGGER.info("Network logging setup completed for test: " + TestCaseName);
		} catch (Exception e) {
			LOGGER.error("Failed to setup network logging: " + e.getMessage());
		}
	}

	@AfterMethod(alwaysRun = true)
	public final void aftermethod(ITestResult result, ITestContext context, Method MethodName) throws Exception {
		//logEntries = DRIVER.manage().logs().get(LogType.PERFORMANCE).getAll();
		try {
			CommonTest commonTest = new CommonTest();
			commonTest = commonTest.getInstance();
			if (!(result.getInstanceName().contains("SystemRequirementsTest") || result.getInstanceName().contains("OnboardingTest") || result.getInstanceName().contains("WelcomeTest"))) {
				commonTest.logout();
				LOGGER.info("Logout is successfull");
			} else {
				LOGGER.info("Logout button is not available on system requirements page");
			}
			LOGGER.debug("Test Finished : " + TestCaseName);
			/*
			 * if (!(recorder == null)) { recorder.stop(); }
			 */

			if (!(result.getStatus() == ITestResult.SUCCESS)) {
				LOGGER.info("Test Failed or Skipped : " + TestCaseName);
				failureCheck = true;
				failureReason = result.getThrowable().getMessage();
				if(failureReason==null){
					failureReason = result.getThrowable().getClass().toString();
				}
				if (context.getCurrentXmlTest().getIncludedGroups().size() > 0) {
					if ((environment.equalsIgnoreCase("US-PROD-WEP") || environment.equalsIgnoreCase("EU-PROD-WEP")) && (runMode.equalsIgnoreCase("Testway"))) {
						//Fetch network logs for failed TC
						networkCapture.captureNetworkLogs();
						// Export network logs for failed tests
						networkCapture.exportHar(HAR_PATH + "failed-test-" + TestCaseName + ".har");
						Thread.sleep(100);
					}
				}
				/*String[] groups = result.getMethod().getGroups();
				if(Arrays.asList(groups).contains("REGRESSION_CONFIGSCRIPTS")) {
					networkCapture.captureNetworkLogs();
					// Export network logs for failed tests
					networkCapture.exportHar(HAR_PATH + "failed-test-" + TestCaseName + ".har");
				}*/
			} else {
				LOGGER.info("Test Pass  : " + TestCaseName);
				// deletePassFile(filePath);
			}
			LOGGER.info("Test Finished  : " + TestCaseName);
			LOGGER.info("++++++++++++++++++++++++++++++++++++++++++++");
		} catch(Exception e){
			LOGGER.error(e.getMessage());
		}
		finally {
			if (!(DRIVER.toString().contains("(null)"))) {
				dr.set(null);
				DRIVER.quit();

				if(runMode.equalsIgnoreCase("Testway"))
				{
				String[] browserProcesses = {"chrome.exe", "firefox.exe", "msedge.exe"};
				for (String process : browserProcesses) {
					Runtime.getRuntime().exec("taskkill /F /IM " + process);
					LOGGER.info("Killed process: " + process);
				}
				}
				LOGGER.info("Browser is closed");			
			}	
			if (context.getCurrentXmlTest().getIncludedGroups().contains("COMPONENT_STATUS_UPDATE") && 
					(environment.equalsIgnoreCase("US-STAGE-WEP") || environment.equalsIgnoreCase("EU-STAGE-WEP") 
							|| environment.equalsIgnoreCase("US-STABLE-WEP") || environment.equalsIgnoreCase("EU-STABLE-WEP"))) {
		        Object testInstance = result.getInstance();
		        String componentId = null;
		        String status = null;
		        try {
		            componentId = (String) testInstance.getClass().getField("componentId").get(testInstance);
		            status = (String) testInstance.getClass().getField("status").get(testInstance);
		        } catch (Exception e) {
		        	LOGGER.error("Status API utility - Unable to fetch componentId or status from the test class: " + e.getMessage());
		        }
		        if (componentId != null && status != null) {
		            StatusAPIPage.verifyComponentStatusUpdate(componentId, status);
		        }
		   }
			if ((MethodName.getName().equals("siteUpTest")) && siteUpRunOrNot == true && SiteUpTest.siteUpExecution == false && RetryFailedTestCases.retryCountVal > retryTc.maxRetryCount) {
				endTime = generateDate();
				LOGGER.error("Site up test is failed. No need to execute the remaining tests");
				haltExecution = true;
				if ((environment.equalsIgnoreCase("US-PROD-WEP") || environment.equalsIgnoreCase("EU-PROD-WEP")) && (runMode.equalsIgnoreCase("Testway"))) {
					pagerDutyProperties.setProperty("pagerDutyCheck", "true");
					pagerDutyProperties.store(new FileOutputStream(pagerDutyFile), null);
				}
				if (RetryFailedTestCases.retryCountVal == retryTc.maxRetryCount - 1) {
					RetryFailedTestCases.retryCountVal = 0;
				}
				if (context.getCurrentXmlTest().getIncludedGroups().size() > 0) {
					String groupName = context.getCurrentXmlTest().getIncludedGroups().get(0).toString();
					if ((environment.equalsIgnoreCase("US-PROD-WEP") || environment.equalsIgnoreCase("EU-PROD-WEP")) && (runMode.equalsIgnoreCase("Testway"))) {
						if (pagerDutyProperties.getProperty("pagerDutyCheck").equalsIgnoreCase("true")) {
							LOGGER.info("Pager duty call in afterMethod");
							PagerDutyNotification.pagerDutyErrorStatus(TestCaseName,failureReason,transactionIdSet,groupName);
							LOGGER.error("Site up test is failed. No need to execute the remaining tests");
							throw new SkipException("Site up test is failed. No need to execute the remaining tests");
						}

					}
				} else {
					LOGGER.info("No groups selected during execution.");
				}
			} else if (!MethodName.getName().equals("siteUpTest") && failureCheck == true) {
				if (context.getCurrentXmlTest().getIncludedGroups().size() > 0) {

					if ((environment.equalsIgnoreCase("US-PROD-WEP") || environment.equalsIgnoreCase("EU-PROD-WEP"))) {
						if (RetryFailedTestCases.retryCountVal > retryTc.maxRetryCount) {
							endTime = generateDate();
							failureCheck = false;
							if ((environment.equalsIgnoreCase("US-PROD-WEP") || environment.equalsIgnoreCase("EU-PROD-WEP")) && (runMode.equalsIgnoreCase("Testway"))) {
								pagerDutyProperties.setProperty("pagerDutyCheck", "true");
								pagerDutyProperties.store(new FileOutputStream(pagerDutyFile), null);
							}
							if (RetryFailedTestCases.retryCountVal == retryTc.maxRetryCount - 1) {
								RetryFailedTestCases.retryCountVal = 0;
							}
							if(!context.getCurrentXmlTest().getSuite().getName().equals("integrationQA_CUJ PRODUCTION suite")) {
							haltExecution = true;
							LOGGER.error("Previous test case has failed. No need to execute the remaining tests");
							}
						}
					}

					if ((context.getCurrentXmlTest().getIncludedGroups().get(0).toString().contains("REGRESSION")) || (context.getCurrentXmlTest().getIncludedGroups().get(0).toString().equals("STABILIZATION_STAGING")) ||
							(context.getCurrentXmlTest().getIncludedGroups().get(0).toString().equals("WorkforceExperience_PAGELOAD"))) {
						if (RetryFailedTestCases.retryCountVal > retryTc.maxRetryCountRegression) {
							failureCheck = false;
							if (RetryFailedTestCases.retryCountVal == retryTc.maxRetryCountRegression - 1) {
								RetryFailedTestCases.retryCountVal = 0;
							}
						}

					}

					if (context.getCurrentXmlTest().getIncludedGroups().size() > 0) {
						String groupName = context.getCurrentXmlTest().getIncludedGroups().get(0).toString();
						if ((environment.equalsIgnoreCase("US-PROD-WEP") || environment.equalsIgnoreCase("EU-PROD-WEP")) && (runMode.equalsIgnoreCase("Testway"))) {
							if (pagerDutyProperties.getProperty("pagerDutyCheck").equalsIgnoreCase("true") && (RetryFailedTestCases.retryCountVal > retryTc.maxRetryCount)) {
								PagerDutyNotification.pagerDutyErrorStatus(TestCaseName,failureReason,transactionIdSet,groupName);
								LOGGER.info("after pagerDutyErrorStatus call");
								LOGGER.error("Previous test case has failed. No need to execute the remaining tests");
								throw new SkipException("Previous test case has failed. No need to execute the remaining tests");
							}
						}
					} else {
						LOGGER.info("No groups selected during execution.");
					}
				}
			}
		}

	}

	/**
	 * This method fetches transaction id of failed testcase from performance logs & passes the same to pagerDutyErrorStatus as a parameter to help debug
	 * issue faster
	 * 
	 */
	public void fetchTransactionId()
	{
		String requestId_response = null;
		String requestId_request = null;
		String statusCode = null;
		String transactionIdLog = null;
		try{
			for (LogEntry log : logEntries) {
				JSONObject string = new JSONObject(log.getMessage());
				// In log there is Network method named responseReceivedExtraInfo which contains status code
				JSONObject message = string.getJSONObject("message"); 
				if(null != message && (message.getString("method"))!= null && (message.getString("method")).equals("Network.responseReceived")){		
					JSONObject params = message.getJSONObject("params"); 
					requestId_response = params.getString("requestId");
					JSONObject headers = params.getJSONObject("response"); 
					if(headers.has("status"))
						statusCode= ""+headers.getInt("status");
					else
						statusCode=null;
					requestIdAndStatusCode.put(requestId_response,statusCode);
				}
				// In log there is Network method named requestWillBeSentExtraInfo which contains transaction id
				if(log.getMessage().contains("requestWillBeSentExtraInfo")){
//					JSONObject message = string.getJSONObject("message");
					JSONObject params = message.getJSONObject("params");
					JSONObject headers = params.getJSONObject("headers");
					requestId_request = params.getString("requestId");
					if((log.getMessage().contains("access-control-request-headers")) || (!log.getMessage().contains("x-hptm-transaction-id")))
						LOGGER.debug("Unable to fetch transaction id , as either its 3rd party tool api or not an api");
					else
						transactionIdLog = headers.getString("x-hptm-transaction-id");
					requestIdAndTransactionId.put(requestId_request,transactionIdLog);
					// Request id parameter is common for both logs , so verifying both logs are of same api if both values are equal
					// and mapping Request id i.e. an API with its transaction id & status code.
					for(Map.Entry<String,String> statusCodeMap : requestIdAndStatusCode.entrySet()) {
						for(Map.Entry<String,String> transactionIdMap : requestIdAndTransactionId.entrySet()) {
							if(requestIdAndTransactionId.containsKey(statusCodeMap.getKey())) {
								String statusCode_map = statusCodeMap.getValue();
								if(statusCode_map!=null){
									if(statusCode_map.matches("4.*") || statusCode_map.matches("5.*")) {
										String transactionIdMapped=transactionIdMap.getValue();
										if(transactionIdMapped!= null)
											transactionIdSet.add(transactionIdMapped);
									}
								}
								else
									LOGGER.debug("Unable to fetch transaction id , as status code is not acceptable");
							}
						}
					}
				}		
			}
			LOGGER.info("Transaction id for failed TC's :"+transactionIdSet);
		}
		catch(JSONException e)
		{
			LOGGER.info("Json Exception occured in parsing log.getMessage() : "+e.getMessage());
		}	
		catch(NullPointerException  e)
		{
			LOGGER.info("NullPointer Exception occured : "+e.getMessage());
		}
		catch(Exception e)
		{
			LOGGER.info("Exception occured : "+e.getMessage());
		}
	}

	// After complete execution send pdf report by email
	@AfterSuite(alwaysRun = true)
	public final void afterSuite(ITestContext context) throws Exception {
		String group = null;
		if (ExtentManager.htmlReporter != null) {
			String suiteName = context.getCurrentXmlTest().getSuite().getName();
			int totalFailedInt = ExtentManager.htmlReporter.getStatusCount().getChildCountFail();
			try {
				if ((runMode.equalsIgnoreCase("Testway"))) {
					MSTeam msteam = new MSTeam();
					msteam.sendStatusToMsTeam(environment, browser, TestCaseName, failureReason);
				}
				group = context.getCurrentXmlTest().getIncludedGroups().get(0);
				LOGGER.info("Test suite execution finished : " + suiteName);
				if (((group.contains("PRODUCTION"))) && (environment.equalsIgnoreCase("US-PROD-WEP") || environment.equalsIgnoreCase("EU-PROD-WEP"))&& (runMode.equalsIgnoreCase("Testway"))) {
					if (totalFailedInt == 0) {
						PagerDutyNotification.pagerDutyResolvedStatus(group);
						LOGGER.info("Pager duty invoked in after suite");
						pagerDutyProperties.setProperty("pagerDutyCheck", "false");
						pagerDutyProperties.store(new FileOutputStream(pagerDutyFile), null);
					}
				}
			} catch (Exception e) {
				LOGGER.error("Test suite " + suiteName + " execution failed due to exception: " + e.getMessage());
			}finally {
				if (DRIVER != null && !DRIVER.toString().contains("(null)")) {
					try {
						DRIVER.quit();
						LOGGER.info("Browser is closed");

						if(runMode.equalsIgnoreCase("Testway"))
						{
							String[] browserProcesses = {"chrome.exe", "firefox.exe", "msedge.exe"};
							for (String process : browserProcesses) {
								Runtime.getRuntime().exec("taskkill /F /IM " + process);
								LOGGER.info("Killed process: " + process);
							}
						}
					} catch (Exception e) {
						LOGGER.error("Error occurred while closing the browser: " + e.getMessage());
					} finally {
						DRIVER = null;
					}
				}
			}
		} else {
			LOGGER.error("Html report object is null, please check the log");
		}
	}

	/**
	 * This method is to read logger file.
	 */
	public final void init() {
		/*
		 * File log4jPropertiesFile = new File(ConstantPath.LOGGER_FILE_PATH); if
		 * (log4jPropertiesFile.exists()) { LoggerContext context = (LoggerContext)
		 * LogManager.getContext(false); File file = new
		 * File(ConstantPath.LOGGER_FILE_PATH);
		 * 
		 * context.setConfigLocation(file.toURI());
		 * 
		 * } else { Properties props = new Properties();
		 * 
		 * try {
		 * props.load(SetTestEnvironments.class.getClassLoader().getResourceAsStream(
		 * "properties/log4j.properties")); } catch (FileNotFoundException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); } catch (IOException e) { //
		 * TODO Auto-generated catch block e.printStackTrace(); }
		 * 
		 * LoggerContext context = (LoggerContext) LogManager.getContext(false); File
		 * file = new File(ConstantPath.LOGGER_FILE_PATH);
		 * 
		 * PropertyConfigurator.configure(props); }
		 */
		

		LoggerContext context = (org.apache.logging.log4j.core.LoggerContext) LogManager.getContext(false);
		File file = new File(ConstantPath.LOGGER_FILE_PATH);


		// this will force a reconfiguration
		context.setConfigLocation(file.toURI());
	}

	/**
	 * This method with return URL based on the given environment.
	 * 
	 * @param environment
	 * @return
	 */
	public static String getEnvironment(String environment) {
		try {
			environmentPageProperties = environmentPropertiesReader.getObjectRepository("Environment");

			switch (environment.toUpperCase()) {
			
			case "US-Perf-WEP":
				return environmentPageProperties.getProperty("Perf_USWEP");
			case "US-STABLE-WEP":
				return environmentPageProperties.getProperty("Stable_USWEP");
							
			case "EU-STABLE-WEP":
				return environmentPageProperties.getProperty("Stable_EUWEP");
			
			case "US-STAGE-WEP":
				return environmentPageProperties.getProperty("Staging_USWEP");
				
			case "EU-STAGE-WEP":
				return environmentPageProperties.getProperty("Staging_EUWEP");
				
			case "US-PROD-WEP":
				return environmentPageProperties.getProperty("Prod_USWEP");
								
			case "EU-PROD-WEP":
				return environmentPageProperties.getProperty("Prod_EUWEP");

			case "LATEST":
				return environmentPageProperties.getProperty("Latest");

			case "US-STABLE":
				return environmentPageProperties.getProperty("StableUS");

			case "US-PEM":
				return environmentPageProperties.getProperty("PEMUS");

			case "EU-STABLE":
				return environmentPageProperties.getProperty("StableEU");

			case "US-STAGING":
				return environmentPageProperties.getProperty("StagingUS");

			case "EU-STAGING":
				return environmentPageProperties.getProperty("StagingEU");

			case "US-PRODUCTION":
				return environmentPageProperties.getProperty("ProdUS");

			case "EU-PRODUCTION":
				return environmentPageProperties.getProperty("ProdEU");

			case "US-STABLE-WORKFLOW":
				return environmentPageProperties.getProperty("Workflow_StableUS");

			case "US-STAGING-WORKFLOW":
				return environmentPageProperties.getProperty("Workflow_StagingUS");

			case "US-PRODUCTION-WORKFLOW":
				return environmentPageProperties.getProperty("Workflow_ProdUS");

			case "PERFORMANCE":
				return environmentPageProperties.getProperty("Perf_Environment");
				
			case "PENTEST":
				return environmentPageProperties.getProperty("Pentest_Environment");
				
			case "US-LDKPROD-WEP":
				return environmentPageProperties.getProperty("LDKProd_WEP");

                case "US-VENEER-WEP":
                    return environmentPageProperties.getProperty("Veneer_USWEP");

                case "US-PERF-WEP":
    				return environmentPageProperties.getProperty("Perf_USWEP");
    				
			default:
				LOGGER.info("Given : " + environment + " environment is incorrect");
				throw new InputMismatchException("You can use : US-STABLE, EU-STABLE, US-PRODUCTION, EU-PRODUCTION, US-STAGING, EU-STAGING, LATEST only ");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * This method will return the service name based on the Service given in POM file.
	 * @param service
	 * @return
	 */
	public static String getservice(String service) {
		try {
			environmentPageProperties = environmentPropertiesReader.getObjectRepository("Environment");
			switch (service.toUpperCase()) {
			case "PROACTIVE-INSIGHTS":
				return environmentPageProperties.getProperty("ProactiveInsights");

			case "CONNECT":
				return environmentPageProperties.getProperty("Connect");
				
			case "WOLF-PROTECT-TRACE":
				return environmentPageProperties.getProperty("WolfProtectTrace");
				
			case "ACTIVE-CARE":
				return environmentPageProperties.getProperty("ActiveCare");
			
			default:
				LOGGER.info("Given : " + service + " service is incorrect");
				throw new InputMismatchException("You can use : PROACTIVE-INSIGHTS, CONNECT, WOLF-PROTECT-TRACE, ACTIVE-CARE only ");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	/**
	 * This method will return language code
	 * 
	 * @param Language: language parameter
	 * @return
	 */
	protected static String getLanguageCode(String Language) {
		try {
			languageCodePageProperties = languageCodePagePropertiesReader.getObjectRepository("LanguageCode");

			switch (Language.toUpperCase()) {
			case "ENGLISH":
				return languageCodePageProperties.getProperty("ENGLISH");

			case "FRENCH":
				return languageCodePageProperties.getProperty("FRENCH");

			case "JAPANESE":
				return languageCodePageProperties.getProperty("JAPANESE");

			case "GERMAN":
				return languageCodePageProperties.getProperty("GERMAN");

			case "SPANISH":
				return languageCodePageProperties.getProperty("SPANISH");

			case "PORTUGUES_BRASIL":
				return languageCodePageProperties.getProperty("PORTUGUES_BRASIL");

			case "PORTUGUES_PORTUGAL":
				return languageCodePageProperties.getProperty("PORTUGUES_PORTUGAL");

			case "CHINESE":
				return languageCodePageProperties.getProperty("CHINESE");

			case "DUTCH":
				return languageCodePageProperties.getProperty("DUTCH");

			case "SWEDISH":
				return languageCodePageProperties.getProperty("SWEDISH");

			case "ITALIAN":
				return languageCodePageProperties.getProperty("ITALIAN");

			case "DANISH":
				return languageCodePageProperties.getProperty("DANISH");

			case "FINNISH":
				return languageCodePageProperties.getProperty("FINNISH");
			default:
				LOGGER.info("Given : " + Language + " language is incorrect");
				throw new InputMismatchException("You can use ENGLISH,FRENCH, JAPANESE,GERMAN,SPANISH,PORTUGUES_BRASIL,PORTUGUES_PORTUGAL,CHINESE,DUTCH, SWEDISH, ITALIAN, DANISH,FINNISH only ");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * This method will delete directory at given path
	 * 
	 * @param path : path to delete directory
	 * @throws IOException
	 */
	public void deleteAndCreateDir(String path) throws IOException {
		if (new File(path).exists()) {
			File file = new File(path);
			FileUtils.cleanDirectory(file);
			FileUtils.forceDelete(file);
			FileUtils.forceMkdir(file);
		} else {
			File file = new File(path);
			FileUtils.forceMkdir(file);
		}
	}

	/**
	 * This method will delete pass test cases videos
	 * 
	 * @param path: video file path
	 */
	public void deletePassFile(String path) {
		try {
			Files.deleteIfExists(Paths.get(path));
		} catch (NoSuchFileException e) {
			LOGGER.debug("No such file/directory exists");
		} catch (DirectoryNotEmptyException e) {
			LOGGER.debug("Directory is not empty.");
		} catch (IOException e) {
			LOGGER.debug("Invalid permissions.");
		}
		LOGGER.info("Deletion successful: " + path);
	}

	/**
	 * This method will send report to MSTeam
	 */
	public static void determinePassOrFail() {
		//MSTeam msteam = new MSTeam();
		int totalTestCount = ExtentManager.htmlReporter.getStatusCount().getChildCount();
		int totalPassedCount = ExtentManager.htmlReporter.getStatusCount().getChildCountPass();
		int totalFailedCount = ExtentManager.htmlReporter.getStatusCount().getChildCountFail();
		if (totalTestCount == totalPassedCount) {
			//msteam.sendStatusToMsTeam("006400", environment, browser, "Passed");
		} else if (totalFailedCount > 0) {
			//msteam.sendStatusToMsTeam("FF0000", environment, browser, "Failed");
		} else {
			LOGGER.info("Test case execution skipped");
		}
	}

	public static void createCSVFile(String fileName) throws IOException{
		FileWriter writer = null;
		try {
			writer = new FileWriter(fileName);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			LOGGER.error(e.getMessage());
		}finally{
			writer.flush();
			writer.close();
		}
	}
}
