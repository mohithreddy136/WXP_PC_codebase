package com.basesource.utils;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.daasui.constants.CommonVariables;

public class MSTeam {
	private final static Logger LOGGER = LogManager.getLogger(MSTeam.class);
	HttpURLConnection con;
	URL url;
	String S3Link;
	public static String environmentName = System.getProperty("environment");
	public static long totalTimeInMinute;
	private String osName = System.getProperty("osName");
	public final String getS3Link() {
		if (environmentName.equals("EU-PRODUCTION")) {
			S3Link = "https://s3.console.aws.amazon.com/s3/buckets/selenium-daas-automation/Selenium/EU-Production/" + System.getProperty("buildNumber");
		}else {
			S3Link = "https://s3.console.aws.amazon.com/s3/buckets/selenium-daas-automation/Selenium/US-Production/" + System.getProperty("buildNumber");
		}
		return S3Link;
	}

	public final void openMSteam() {
		String apiUrl=null;
		getS3Link();
		try {
			if(osName.toUpperCase().contains("LINUX"))
				apiUrl = "https://outlook.office.com/webhook/f670b8ef-a18a-46c3-b444-9cc4c64b380b@ca7981a2-785a-463d-b82a-3db87dfc3ce6/IncomingWebhook/b85c6bae6303473cb3f055573c4fcadc/94458e4c-7737-4ccc-8abd-ce7d6721092c";
			else
				apiUrl = "https://hp.webhook.office.com/webhookb2/f670b8ef-a18a-46c3-b444-9cc4c64b380b@ca7981a2-785a-463d-b82a-3db87dfc3ce6/IncomingWebhook/26a2c68396eb485db1b91b7b1682a3d3/e5b51542-ce22-42d5-a20c-73a89c7f01ad/V21aE5NmVuB8hu5fFwLpvFq_bLBap9ub0WfwADcVsrcGI1";
			url = new URL(apiUrl);
			con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/json");
			con.setRequestProperty("accept-charset", "utf-8");
			con.setDoOutput(true);

		} catch (Exception e) {
			LOGGER.error("Exception occured in Test openMSteam : " + e.getMessage());
		}

	}

	public final void getResponse(String body) {
		try {
			byte[] outputInBytes = body.getBytes("UTF-8");
			OutputStream os = con.getOutputStream();
			os.write(outputInBytes);
			os.close();
			int responseCode = con.getResponseCode();
			if (responseCode == 200) {
				LOGGER.info("Code Pass in getResponse method." + "Response for sending the message to MS team  : Success");
			} else {
				LOGGER.error("Code fail in getResponse method." + "Response for sending the message to MS team  : Error");
			}

		} catch (Exception e) {
			LOGGER.error("Exception occured in Test getResponse : " + e.getMessage());
		}

	}

	public void sendDataToMsTeamWhenSiteUpFailed(String color, String environment, String browser, String date) {
		try {
			this.openMSteam();
			String body = "{\r\n" + "\"themeColor\" : \"" + color + "\",\r\n" + "\"summary\" : \"Automation Testing Status\",\r\n" + "\"sections\" : [{\"activityTitle\" : \"Selenium Automation Update For Site Up Test\",\r\n" + "\"activitySubtitle\" : \"For " + CommonVariables.PRODUCT_NAME + " Project\",\r\n" + "\"facts\": [{\"name\" : \"SELENIUM TESTS EXECUTION STATUS\",\r\n" + "\"value\" : \"Selenium Test cases skipped due to failure of site-up\"},\r\n" + "{\"name\" : \"SITE-UP STATUS\",\r\n" + "\"value\" : \"Failed\"},\r\n"
					+ "{\"name\" : \"ENVIRONMENT\",\r\n" + "\"value\" : \"" + environment + "\"},\r\n" + "{\"name\" : \"END TIME\",\r\n" + "\"value\" : \"" + date + "\"},\r\n" + "{\"name\" : \"BROWSER\",\r\n" + "\"value\" : \"" + browser + "\"}]\r\n" + "}],\r\n" + "\"potentialAction\" : [{" + "\"@type\" : \"OpenUri\",\r\n" + "\"name\" : \"VIEW BUILD\",\r\n" + "\"targets\" : [{" + "\"os\" : \"default\",\r\n"
					+ "\"uri\" : \"https://jenkins.hppipeline.com/job/Automation/job/Selenium_DaaS_Automation/" + System.getProperty("buildNumber") + "/console\"\r\n" + "}]" + "}," + "{" + "\"@type\" : \"OpenUri\",\r\n" + "\"name\" : \"S3 LINK\",\r\n" + "\"targets\" : [{" + "\"os\" : \"default\",\r\n" + "\"uri\" : \"" + S3Link + "/?region=us-west-2&tab=overview\"\r\n" + "}]" + "}]" + "}";
			this.getResponse(body);

		} catch (Exception e) {
			LOGGER.error("Exception occured in Test sendDataToMsTeamWhenSiteUpFailed : " + e.getMessage());
		}
	}

	public void sendStatusToMsTeam(String environment, String browser, String failedTest, String failedTestResult) {
		String body = null;
		long totalTime = ExtentManager.htmlReporter.getRunDuration();
		totalTimeInMinute = TimeUnit.MILLISECONDS.toMinutes(totalTime);
		Date startTime = ExtentManager.htmlReporter.getStartTime();
		Date endTime = ExtentManager.htmlReporter.getEndTime();
		int totalTest = ExtentManager.htmlReporter.getStatusCount().getChildCount();
		int passTest = ExtentManager.htmlReporter.getStatusCount().getChildCountPass();
		int failTest = ExtentManager.htmlReporter.getStatusCount().getChildCountFail();
		int skipTest = ExtentManager.htmlReporter.getStatusCount().getChildCountSkip();

		/*
		 * Added if condition if all TC's are skipped - Automation status should be
		 * 'Skipped'. Earlier the status was displayed 'Failed'.
		 * Also if passTest > 0 & skipTest > 0 but failTest == 0 , earlier we were getting status 'Failed'.
		 * Added this if condition for Automation status as 'Passed'
		 */
		try {
			this.openMSteam();
			if (totalTest == passTest || ((passTest > 0) && (failTest == 0) && (skipTest > 0))) {
				body = "{\r\n" + "\"themeColor\" : \"" + "006400" + "\",\r\n" + "\"summary\" : \"Automation Testing Status\",\r\n" + "\"sections\" : [{\"activityTitle\" : \"Update From Selenium Tests Execution On " + environment + "\",\r\n" + "\"activitySubtitle\" : \"For " + CommonVariables.PRODUCT_NAME + " Project\",\r\n" + "\"facts\": [{\"name\" : \"EXECUTION STATUS\",\r\n" + "\"value\" : \"Finished\"},\r\n" + "{\"name\" : \"PLATFORM\",\r\n" + "\"value\" : \"" + osName + "\"},\r\n" + "{\"name\" : \"BROWSER\",\r\n" + "\"value\" : \"" + browser + "\"},\r\n"
						+ "{\"name\" : \"ENVIRONMENT\",\r\n" + "\"value\" : \"" + environment + "\"},\r\n" + "{\"name\" : \"TOTAL TESTS\",\r\n" + "\"value\" : \"" + totalTest + "\"},\r\n" + "{\"name\" : \"TESTS PASSED\",\r\n" + "\"value\" : \"" + passTest + "\"},\r\n" + "{\"name\" : \"TESTS FAILED\",\r\n" + "\"value\" : \"" + failTest + "\"},\r\n" + "{\"name\" : \"TESTS SKIPPED\",\r\n" + "\"value\" : \"" + skipTest + "\"},\r\n" + "{\"name\" : \"DURATION IN MINUTES\",\r\n" + "\"value\" : \""
						+ totalTimeInMinute + "\"},\r\n" + "{\"name\" : \"START TIME\",\r\n" + "\"value\" : \"" + startTime + "\"},\r\n" + "{\"name\" : \"END TIME\",\r\n" + "\"value\" : \"" + endTime + "\"},\r\n" + "{\"name\" : \"AUTOMATION STATUS\",\r\n" + "\"value\" : \"" + "Passed" + "\"}]\r\n" + "}],\r\n" + "\"potentialAction\" : [{" + "\"@type\" : \"OpenUri\",\r\n" + "\"name\" : \"VIEW BUILD\",\r\n" + "\"targets\" : [{" + "\"os\" : \"default\",\r\n"
						+ "\"uri\" : \"https://jenkins.hppipeline.com/job/Automation/job/Selenium_DaaS_Automation/" + System.getProperty("buildNumber") + "/console\"\r\n" + "}]" + "}," + "{" + "\"@type\" : \"OpenUri\",\r\n" + "\"name\" : \"S3 LINK\",\r\n" + "\"targets\" : [{" + "\"os\" : \"default\",\r\n" + "\"uri\" : \"" + S3Link + "/?region=us-west-2&tab=overview\"\r\n" + "}]" + "}]" + "}";
			} else if (failTest > 0 && environment.contains("Production")) {
				body = "{\r\n" + "\"themeColor\" : \"" + "FF0000" + "\",\r\n" + "\"summary\" : \"Automation Testing Status\",\r\n" + "\"sections\" : [{\"activityTitle\" : \"Update From Selenium Tests Execution On " + environment + "\",\r\n" + "\"activitySubtitle\" : \"For " + CommonVariables.PRODUCT_NAME + " Project\",\r\n" + "\"facts\": [{\"name\" : \"EXECUTION STATUS\",\r\n" + "\"value\" : \"Finished\"},\r\n" + "{\"name\" : \"PLATFORM\",\r\n" + "\"value\" : \"" + osName + "\"},\r\n" + "{\"name\" : \"BROWSER\",\r\n" + "\"value\" : \"" + browser + "\"},\r\n"
						+ "{\"name\" : \"ENVIRONMENT\",\r\n" + "\"value\" : \"" + environment + "\"},\r\n" + "{\"name\" : \"TOTAL TESTS\",\r\n" + "\"value\" : \"" + totalTest + "\"},\r\n" + "{\"name\" : \"TESTS PASSED\",\r\n" + "\"value\" : \"" + passTest + "\"},\r\n" + "{\"name\" : \"TESTS FAILED\",\r\n" + "\"value\" : \"" + failTest + "\"},\r\n" + "{\"name\" : \"TESTS SKIPPED\",\r\n" + "\"value\" : \"" + skipTest + "\"},\r\n" + "{\"name\" : \"DURATION IN MINUTES\",\r\n" + "\"value\" : \""
						+ totalTimeInMinute + "\"},\r\n" + "{\"name\" : \"START TIME\",\r\n" + "\"value\" : \"" + startTime + "\"},\r\n" + "{\"name\" : \"END TIME\",\r\n" + "\"value\" : \"" + endTime + "\"},\r\n" + "{\"name\" : \"AUTOMATION STATUS\",\r\n" + "\"value\" : \"" + "Failed" + "\"},\r\n" + "{\"name\" : \"FAILED TESTCASE NAME\",\r\n" + "\"value\" : \"" + failedTest + "\"},\r\n" + "{\"name\" : \"REASON\",\r\n" + "\"value\" : \"" + failedTestResult + "\"}]\r\n" + "}],\r\n" + "\"potentialAction\" : [{"
						+ "\"@type\" : \"OpenUri\",\r\n" + "\"name\" : \"VIEW BUILD\",\r\n" + "\"targets\" : [{" + "\"os\" : \"default\",\r\n" + "\"uri\": \"https://jenkins.hppipeline.com/job/Automation/job/Selenium_DaaS_Automation/" + System.getProperty("buildNumber") + "/console\"\r\n" + "}]" + "}," + "{" + "\"@type\" : \"OpenUri\",\r\n" + "\"name\" : \"S3 LINK\",\r\n" + "\"targets\" : [{" + "\"os\" : \"default\",\r\n" + "\"uri\" : \"" + S3Link + "/?region=us-west-2&tab=overview\"\r\n" + "}]"
						+ "}]" + "}";
			} else if (totalTest == skipTest) {
				body = "{\r\n" + "\"themeColor\" : \"" + "006400" + "\",\r\n" + "\"summary\" : \"Automation Testing Status\",\r\n" + "\"sections\" : [{\"activityTitle\" : \"Update From Selenium Tests Execution On " + environment + "\",\r\n" + "\"activitySubtitle\" : \"For " + CommonVariables.PRODUCT_NAME + " Project\",\r\n" + "\"facts\": [{\"name\" : \"EXECUTION STATUS\",\r\n" + "\"value\" : \"Finished\"},\r\n" + "{\"name\" : \"PLATFORM\",\r\n" + "\"value\" : \"" + osName + "\"},\r\n" + "{\"name\" : \"BROWSER\",\r\n" + "\"value\" : \"" + browser + "\"},\r\n"
						+ "{\"name\" : \"ENVIRONMENT\",\r\n" + "\"value\" : \"" + environment + "\"},\r\n" + "{\"name\" : \"TOTAL TESTS\",\r\n" + "\"value\" : \"" + totalTest + "\"},\r\n" + "{\"name\" : \"TESTS PASSED\",\r\n" + "\"value\" : \"" + passTest + "\"},\r\n" + "{\"name\" : \"TESTS FAILED\",\r\n" + "\"value\" : \"" + failTest + "\"},\r\n" + "{\"name\" : \"TESTS SKIPPED\",\r\n" + "\"value\" : \"" + skipTest + "\"},\r\n" + "{\"name\" : \"DURATION IN MINUTES\",\r\n" + "\"value\" : \""
						+ totalTimeInMinute + "\"},\r\n" + "{\"name\" : \"START TIME\",\r\n" + "\"value\" : \"" + startTime + "\"},\r\n" + "{\"name\" : \"END TIME\",\r\n" + "\"value\" : \"" + endTime + "\"},\r\n" + "{\"name\" : \"AUTOMATION STATUS\",\r\n" + "\"value\" : \"" + "Skipped" + "\"}]\r\n" + "}],\r\n" + "\"potentialAction\" : [{" + "\"@type\" : \"OpenUri\",\r\n" + "\"name\" : \"VIEW BUILD\",\r\n" + "\"targets\" : [{" + "\"os\" : \"default\",\r\n"
						+ "\"uri\" : \"https://jenkins.hppipeline.com/job/Automation/job/Selenium_DaaS_Automation/" + System.getProperty("buildNumber") + "/console\"\r\n" + "}]" + "}," + "{" + "\"@type\" : \"OpenUri\",\r\n" + "\"name\" : \"S3 LINK\",\r\n" + "\"targets\" : [{" + "\"os\" : \"default\",\r\n" + "\"uri\" : \"" + S3Link + "/?region=us-west-2&tab=overview\"\r\n" + "}]" + "}]" + "}";
			}
			else {
				body = "{\r\n" + "\"themeColor\" : \"" + "FF0000" + "\",\r\n" + "\"summary\" : \"Automation Testing Status\",\r\n" + "\"sections\" : [{\"activityTitle\" : \"Update From Selenium Tests Execution On " + environment + "\",\r\n" + "\"activitySubtitle\" : \"For " + CommonVariables.PRODUCT_NAME + " Project\",\r\n" + "\"facts\": [{\"name\" : \"EXECUTION STATUS\",\r\n" + "\"value\" : \"Finished\"},\r\n" + "{\"name\" : \"PLATFORM\",\r\n" + "\"value\" : \"" + osName + "\"},\r\n" + "{\"name\" : \"BROWSER\",\r\n" + "\"value\" : \"" + browser + "\"},\r\n"
						+ "{\"name\" : \"ENVIRONMENT\",\r\n" + "\"value\" : \"" + environment + "\"},\r\n" + "{\"name\" : \"TOTAL TESTS\",\r\n" + "\"value\" : \"" + totalTest + "\"},\r\n" + "{\"name\" : \"TESTS PASSED\",\r\n" + "\"value\" : \"" + passTest + "\"},\r\n" + "{\"name\" : \"TESTS FAILED\",\r\n" + "\"value\" : \"" + failTest + "\"},\r\n" + "{\"name\" : \"TESTS SKIPPED\",\r\n" + "\"value\" : \"" + skipTest + "\"},\r\n" + "{\"name\" : \"DURATION IN MINUTES\",\r\n" + "\"value\" : \""
						+ totalTimeInMinute + "\"},\r\n" + "{\"name\" : \"START TIME\",\r\n" + "\"value\" : \"" + startTime + "\"},\r\n" + "{\"name\" : \"END TIME\",\r\n" + "\"value\" : \"" + endTime + "\"},\r\n" + "{\"name\" : \"AUTOMATION STATUS\",\r\n" + "\"value\" : \"" + "Failed" + "\"}]\r\n" + "}],\r\n" + "\"potentialAction\" : [{" + "\"@type\" : \"OpenUri\",\r\n" + "\"name\" : \"VIEW BUILD\",\r\n" + "\"targets\" : [{" + "\"os\" : \"default\",\r\n"
						+ "\"uri\" : \"https://jenkins.hppipeline.com/job/Automation/job/Selenium_DaaS_Automation/" + System.getProperty("buildNumber") + "/console\"\r\n" + "}]" + "}," + "{" + "\"@type\" : \"OpenUri\",\r\n" + "\"name\" : \"S3 LINK\",\r\n" + "\"targets\" : [{" + "\"os\" : \"default\",\r\n" + "\"uri\" : \"" + S3Link + "/?region=us-west-2&tab=overview\"\r\n" + "}]" + "}]" + "}";
			}
			this.getResponse(body);

		} catch (Exception e) {
			LOGGER.error("Exception occured in Test sendStatusToMsTeam : " + e.getMessage());
		}
	}
	
	public void sendDataToMsTeamWhenSiteUpFailedForWorkflow(String color, String environment, String browser, String date) {
		try {
			this.openMSteamWorkflow();
			String body = "{\r\n" + "\"themeColor\" : \"" + color + "\",\r\n" + "\"summary\" : \"Automation Testing Status\",\r\n" + "\"sections\" : [{\"activityTitle\" : \"Selenium Automation Update For Site Up Test\",\r\n" + "\"activitySubtitle\" : \"For HP Workflow Project\",\r\n" + "\"facts\": [{\"name\" : \"SELENIUM TESTS EXECUTION STATUS\",\r\n" + "\"value\" : \"Selenium Test cases skipped due to failure of site-up\"},\r\n" + "{\"name\" : \"SITE-UP STATUS\",\r\n" + "\"value\" : \"Failed\"},\r\n"
					+ "{\"name\" : \"ENVIRONMENT\",\r\n" + "\"value\" : \"" + environment + "\"},\r\n" + "{\"name\" : \"END TIME\",\r\n" + "\"value\" : \"" + date + "\"},\r\n" + "{\"name\" : \"BROWSER\",\r\n" + "\"value\" : \"" + browser + "\"}]\r\n" + "}],\r\n" + "\"potentialAction\" : [{" + "\"@type\" : \"OpenUri\",\r\n" + "\"name\" : \"VIEW BUILD\",\r\n" + "\"targets\" : [{" + "\"os\" : \"default\",\r\n"
					+ "\"uri\" : \"https://jenkins.hppipeline.com/job/Automation/job/Selenium_DaaS_Automation/" + System.getProperty("buildNumber") + "/console\"\r\n" + "}]" + "}]" + "}";
			this.getResponse(body);

		} catch (Exception e) {
			LOGGER.error("Exception occured in Test sendDataToMsTeamWhenSiteUpFailedForWorkflow : " + e.getMessage());
		}
	}
	
	public final void openMSteamWorkflow() {
		try {
			String apiUrl = "https://outlook.office.com/webhook/e6a1b7f2-de2c-408c-af70-113d01255e16@ca7981a2-785a-463d-b82a-3db87dfc3ce6/IncomingWebhook/2a851f3511fb4641a2c064d4b929191c/4c5eea46-1b37-4269-b830-5cbfce31d441";
			url = new URL(apiUrl);
			con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/json");
			con.setRequestProperty("accept-charset", "utf-8");
			con.setDoOutput(true);

		} catch (Exception e) {
			LOGGER.error("Exception occured in Test openMSteamWorkflow : " + e.getMessage());
		}

	}

}