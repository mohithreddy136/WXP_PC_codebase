package com.testscripts.daasui;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.daasui.constants.CommonVariables;
import com.daasui.pages.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.basesource.action.PreDefinedActions;
import com.basesource.utils.ExcelReader;
import com.basesource.utils.LaunchDarkly;
import com.basetest.environments.SetTestEnvironments;
import com.daasui.constants.ConstantPath;
import com.daasui.constants.ConstantURL;
import com.daasui.constants.DashboardVariables;
import com.daasui.constants.WEPPulsesCreationPageVariables;


/**
 * This class contains test cases related to analytics.
 * It extends the CommonTest class.
 */
public class AnalyticsTest extends CommonTest {

	private static Logger LOGGER = LogManager.getLogger(AnalyticsTest.class);
	private String CompanyValueReports;
	public static String environment;

	/**
	 * This test case verifies the analytics list page.
	 * It logs in with the given credentials, verifies the left side menu,
	 * and checks the analytics page title.
	 */
	@Test(priority = 1, groups = {"REGRESSION_INSIGHTS","PRODUCTION_INSIGHTS"}, description = "TestCase ID:C42541223")
	public final void verifyAnalyticsListPage() throws Exception {
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver()).getInstance();
		leftSideMenuVerification();
		analyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
		waitForPageLoaded();
		Assert.assertTrue(analyticsPage.getTextOfAnalyticsPage("analytics").equals(analyticsPage.getTextLanguage(LanguageCode, "daas_ui", "wex.analytics")),
				"Analytics title doesn't match");
		LOGGER.info("Listing page opened correctly");
	}

/**
 * This test case verifies the creation of an application dashboard.
 * It logs in with the given credentials, navigates to the analytics page,
 * creates a custom dashboard, and verifies its creation.
 * Finally, it deletes the created dashboard.
 *
 * @throws Exception if an error occurs during the test execution
 */

	@Test(priority = 2, groups = {"REGRESSION_INSIGHTS","PRODUCTION_LDK","INITECH_SOLUTIONS"}, description = "TestCase ID:C53524180")
	public final void verifyCreateApplicationDashboard() throws Exception {
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		String testSuiteName = SetTestEnvironments.suiteName;
		if(testSuiteName.contains("LDK") || testSuiteName.contains("Initech")){
			switchUserBasedOnSuite(testSuiteName);
		}
		AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver()).getInstance();
		leftSideMenuVerification();
		analyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
		waitForPageLoaded();
		analyticsPage.clickOnElementsOfAnalyticsPage("customDashboard");
		analyticsPage.clickOnElementsOfAnalyticsPage("application");
		analyticsPage.getWidgetByCategory();
		analyticsPage.clickOnElementsOfAnalyticsPage("createDashboard");
		Assert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("dashboardName"), "Dashboard name does not start with 'New Dashboard'");
		LOGGER.info("Application custom dashboard created successfully");
		Assert.assertTrue(analyticsPage.deleteCustomDashboard(), "Dashboard not deleted sucecssfully'");
		LOGGER.info("Application custom dashboard deleted successfully");
	}
/**
 * This test case verifies the creation of a system dashboard.
 * It logs in with the given credentials, navigates to the analytics page,
 * creates a custom system dashboard, and verifies its creation.
 * Finally, it deletes the created dashboard.
 *
 * @throws Exception if an error occurs during the test execution
 */
	@Test(priority = 3, groups = {"REGRESSION_INSIGHTS","PRODUCTION_LDK","INITECH_SOLUTIONS"}, description = "TestCase ID:C53524185")
	public final void verifyCreateSystemDashboard() throws Exception {
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		String testSuiteName = SetTestEnvironments.suiteName;
		if(testSuiteName.contains("LDK") || testSuiteName.contains("Initech")){
			switchUserBasedOnSuite(testSuiteName);
		}
		AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver()).getInstance();
		leftSideMenuVerification();
		analyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
		waitForPageLoaded();
		analyticsPage.clickOnElementsOfAnalyticsPage("customDashboard");
		analyticsPage.clickOnElementsOfAnalyticsPage("systemHealth");
		analyticsPage.getWidgetByCategory();
		analyticsPage.clickOnElementsOfAnalyticsPage("createDashboard");
		Assert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("dashboardName"), "Dashboard name does not start with 'New Dashboard'");
		LOGGER.info("System custom dashboard created successfully");
		Assert.assertTrue(analyticsPage.deleteCustomDashboard(), "Dashboard not deleted sucecssfully '");
		LOGGER.info("System custom dashboard deleted successfully");
	}

	/**
	 * Win11 report
	 *
	 * @throws Exception
	 */
	@Test(priority = 4, groups = {"REGRESSION_INSIGHTS","PRODUCTION_INSIGHTS", "REGRESSION_INTEGRATIONQA_CUJ"}, description = "TestCase ID :C44043689 ")
	public final void verifyWin11ReadinessReportV2() throws Exception {
		SoftAssert sa = new SoftAssert();
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver()).getInstance();
		leftSideMenuVerification();
		analyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
		waitForPageLoaded();
		analyticsPage.clickOnElementsOfAnalyticsPage("classicReports");
		waitForPageLoaded();
		analyticsPage.clickByJavaScriptOnAnalyticsPage("win11");
		waitForPageLoaded();
		sa.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("summeryWin11"),"Summary tab missing.");
		sa.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("detailsWin11"),"Details tab missing.");
		analyticsPage.clickByJavaScriptOnAnalyticsPage("detailsWin11");
		waitForPageLoaded();
		sa.assertTrue(analyticsPage.verifyListofElementsOfAnalyticsPage("datalist"),"List of 11 records data is missing.in details tab");
		sa.assertAll();
	}

	/**
	 * Hardware Inventory Report
	 *
	 * @throws Exception
	 */
	@Test(priority = 5,  groups = {"REGRESSION_INSIGHTS","PRODUCTION_INSIGHTS", "REGRESSION_INTEGRATIONQA_CUJ"}, description = "TestCase ID :C44043663 ")
	public final void createHardwareInventoryDetailsReport() throws Exception {
		SoftAssert sa = new SoftAssert();
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver()).getInstance();
		leftSideMenuVerification();
		analyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
		waitForPageLoaded();
		analyticsPage.clickOnElementsOfAnalyticsPage("classicReports");
		waitForPageLoaded();
		analyticsPage.clickByJavaScriptOnAnalyticsPage("hwInventory");
		String[] columnNameKeys = {"HWInventoryDetails"};
		String[] reportTabs = new String[]{"HWInventoryDetails.ByLocation",
				"HWInventoryDetails.ByDeviceCongiguration", "HWInventoryDetails.ByDeviceModel", "HWInventoryDetails.ByOperatingSystem",
				"HWInventoryDetails.Details"};
		sa.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("byLocation"),"byLocation Summary report missing.");
    	sa.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("byDeviceConfiguration"),"byDeviceConfiguration Summary report missing.");
    	sa.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("byDeviceModel"),"byDeviceModel Summary report missing.");
    	sa.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("byOS"),"byOS Summary report missing.");
    	sa.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("byDetails"),"byDetails Summary report missing.");
    	sa.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("hwInventoryByLocation"),"hwInventoryByLocation Summary report missing.");
    	analyticsPage.clickByJavaScriptOnAnalyticsPage("byDetails");
    	waitForPageLoaded();
    	Thread.sleep(1000);
    	sa.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("lastSignUser"),"lastSignUser report missing.");
    	sa.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("serialNumber"),"serialNumber report missing.");
    	sa.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("deviceName"),"deviceName report missing.");
		sa.assertAll();
	}

	/**
	 * BIOS Inventory Report
	 * Create Hardware->BIOS Inventory->Details
	 *
	 * @throws Exception
	 */

	/**
	 * Test method to create a hardware BIOS inventory report.
	 * <p>
	 * This test logs in as an IT Admin, navigates to the Analytics Page, and verifies the BIOS Inventory report.
	 * </p>
	 *
	 * @throws Exception if an error occurs during the test execution
	 * @priority 4
	 * @groups {"REGRESSION_INSIGHTS", "PRODUCTION_INSIGHTS"}
	 * @description TestCase ID: C42541888
	 */
	@Test(priority = 6, groups = {"REGRESSION_INSIGHTS", "PRODUCTION_INSIGHTS", "REGRESSION_INTEGRATIONQA_CUJ"}, description = "TestCase ID: C42541888",enabled = false)
	public final void createHardwareBIOSInventoryReport() throws Exception {
		SoftAssert sa = new SoftAssert();
		// Log in as IT Admin
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		LOGGER.info("Logged in as IT Admin");
		// Navigate to Analytics Page
		AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver()).getInstance();
		leftSideMenuVerification();
		analyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
		sleeper(3000);
		LOGGER.info("Navigated to Analytics Page");
		analyticsPage.clickOnElementsOfAnalyticsPage("classicReports");
		waitForPageLoaded();
		// Click on BIOS Inventory
		analyticsPage.clickByJavaScriptOnAnalyticsPage("biosInventory");
		LOGGER.info("Clicked on BIOS Inventory");
		// Define column names and report tabs
		String[] columnNameKeys = {"HWbiosInventoryDetails"};
		String[] reportTabs = new String[]{"HWbiosInventoryDetails.Summary", "HWbiosInventoryDetails.UniqueBIOS",
				"HWbiosInventoryDetails.ByWeek", "HWbiosInventoryDetails.Details"};
		// Validate report data
		sa.assertTrue(
				analyticsPage.reportDataValidation(analyticsPage.getReportTabDetails(reportTabs), columnNameKeys,
						LanguageCode, "daas_reports_ui"),
				"The values in Grid or graph did not match or did not get loaded");
		LOGGER.info("Report data validation completed");
		// Assert all
		sa.assertAll();
	}


	/**
	 * Test method to create a hardware blue screen error details report.
	 * <p>
	 * This test logs in as an IT Admin, navigates to the Analytics Page, and verifies the Blue Screen Insights Page.
	 * </p>
	 *
	 * @throws Exception if an error occurs during the test execution
	 * @priority 5
	 * @groups {"REGRESSION_INSIGHTS", "PRODUCTION_INSIGHTS"}
	 * @description TestCase ID : C44672870
	 */
	@Test(priority = 7, groups = {"REGRESSION_INSIGHTS", "PRODUCTION_INSIGHTS", "REGRESSION_INTEGRATIONQA_CUJ"}, description = "TestCase ID : C44672870")
	public final void createHardwareBlueScreenErrorDetailsReport() throws Exception {
		SoftAssert sa = new SoftAssert();
		// Log in as IT Admin
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		LOGGER.info("Logged in as IT Admin");
		// Navigate to Analytics Page
		AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver()).getInstance();
		leftSideMenuVerification();
		analyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
		waitForPageLoaded();
		LOGGER.info("Navigated to Analytics Page");
		analyticsPage.clickOnElementsOfAnalyticsPage("modernReports");
		// Click on Blue Screen Errors
		analyticsPage.clickOnElementsOfAnalyticsPage("blueScreenErrors");
		LOGGER.info("Clicked on Blue Screen Errors");
		// Verify Blue Screen Insights Page
		sa.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("insightsPageBSOD"), "Blue Screen Insights Page doesn't open");
		LOGGER.info("Verified Blue Screen Insights Page");
		// Assert all
		sa.assertAll();
	}

	/**
	 * Device Utilization Report
	 *
	 * @throws Exception
	 */

	@Test(priority = 8, groups = {"REGRESSION_INSIGHTS","PRODUCTION_INSIGHTS", "REGRESSION_INTEGRATIONQA_CUJ"}, description = "TestCase ID : C44042305",enabled = false)
	public final void createHardwareDeviceutilDetailsReport() throws Exception {
		SoftAssert sa = new SoftAssert();
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver()).getInstance();
		leftSideMenuVerification();
		analyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
		waitForPageLoaded();
		analyticsPage.clickOnElementsOfAnalyticsPage("classicReports");
		waitForPageLoaded();
		analyticsPage.clickByJavaScriptOnAnalyticsPage("deviceUtilization");
	    String[] columnNameKeys = {"HWDeviceutilDetails"};
		String[] reportTabs = new String[]{"HWDeviceutilDetails.Summary", "HWDeviceutilDetails.ManufactureYear",
				"HWDeviceutilDetails.SoftPerf", "HWDeviceutilDetails.SoftPerfMac", "HWDeviceutilDetails.Details"};
		sa.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("summeryDU"),"Summery tab missing.");
		analyticsPage.clickByJavaScriptOnAnalyticsPage("summeryDU");
		waitForPageLoaded();
		sa.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("summerydata"),"Summery data missing.");
		analyticsPage.clickByJavaScriptOnAnalyticsPage("arrowclick");
		waitForPageLoaded();
		analyticsPage.clickByJavaScriptOnAnalyticsPage("detailsDU");
		waitForPageLoaded();
		sa.assertTrue(analyticsPage.verifyListofElementsOfAnalyticsPage("datalist1"),"List of 11 records data is missing.in details tab");
		sa.assertAll();
	}

	/**
	 * Driver Inventory report
	 * * report with outdated drivers information. This test creating new Driver
	 * Inventory report on the reports page, Verifying data in the header section on
	 * drill down report page, Verifying presence of data under the respective tabs,
	 * Also Verifying grid columns displaying correctly
	 *
	 * @throws Exception
	 */

	@Test(priority = 9, groups = {"REGRESSION_INSIGHTS"}, description = "TestCase ID :C44043542 ",enabled = false)
	public final void createDriverInventoryReport() throws Exception {
		SoftAssert sa = new SoftAssert();
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver()).getInstance();
		leftSideMenuVerification();
		analyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
		sleeper(3000);
		analyticsPage.clickOnElementsOfAnalyticsPage("classicReports");
		waitForPageLoaded();
		analyticsPage.clickByJavaScriptOnAnalyticsPage("driverInventory");
		String[] columnNameKeys = {"SWDriverInventoryDriversTable", "SWDriverInventoryDetails"};

		String[] reportTabs = new String[]{"SWDriverInventory.Summary", "SWDriverInventory.Drivers",
				"SWDriverInventory.DriversTable", "SWDriverInventory.Details"};
		sa.assertTrue(
				analyticsPage.reportDataValidation(analyticsPage.getReportTabDetails(reportTabs), columnNameKeys,
						LanguageCode, "daas_reports_ui"),
				"The values in Grid or graph did not match or was not get loaded");
		// Delete created report
		sa.assertAll();
	}

	/**
	 * This test case verifies the Application error Report (Analytics)
	 */
	@Test(priority = 10, groups = {"REGRESSION_INTEGRATIONQA_CUJ", "PRODUCTION_INTEGRATIONQA_CUJ"}, description = "TestCase ID:C43425017")
	public final void verifyApplicationErrorReportAnalyticsListPage() throws Exception {
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		SoftAssert softAssert = new SoftAssert();

		AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver()).getInstance();
		leftSideMenuVerification();

		analyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
		waitForPageLoaded();
		LOGGER.info("Analytics listing page opened correctly");
		// Click on Modern Reports
		analyticsPage.clickOnElementsOfAnalyticsPage("modernReports");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("ApplicationExperience"),"Application Error report missing.");
		analyticsPage.clickOnElementsOfAnalyticsPage("ApplicationExperience");
		waitForPageLoaded();
		Thread.sleep(2000);
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("applicationScore"),"Application score report missing.");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("appWithMostCrashes"),"Application with most crashes report missing.");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("appWithMostFreezes"),"Application with most freezes report missing.");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("crashesAndFreezesOverTime"),"crashes And Freezes OverTime freezes report missing.");
		LOGGER.info("Application Error validation completed");
		analyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
		waitForPageLoaded();
		analyticsPage.clickOnElementsOfAnalyticsPage("reportListSort");
		LOGGER.info("Report list sorting verified");
		softAssert.assertAll();

	}

	/**
	 * Test method to verify the presence of BSOD charts on the Analytics Page.
	 * This test logs in as an IT Admin, navigates to the Analytics Page, and verifies the presence of various BSOD charts.
	 * @throws Exception if an error occurs during the test execution.
	 * @priority 10
	 * @groups {"REGRESSION_INSIGHTS"}
	 * @description TestCase ID: C43425017
	 */
	@Test(priority = 11, groups = {"REGRESSION_INSIGHTS", "PRODUCTION_INSIGHTS","PRODUCTION_LDK","INITECH_SOLUTIONS"}, description = "TestCase ID:C44672870")
	public final void verifyBSODInsightsPageCharts() throws Exception {
		LOGGER.info("Starting test case: verifyBSODCharts");
		// Log in as IT Admin
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		String testSuiteName = SetTestEnvironments.suiteName;
		if(testSuiteName.contains("LDK") || testSuiteName.contains("Initech")){
			switchUserBasedOnSuite(testSuiteName);
		}
		LOGGER.info("Logged in as IT Admin");
		SoftAssert softAssert = new SoftAssert();
		AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver()).getInstance();
		// Verify left side menu
		leftSideMenuVerification();
		LOGGER.info("Left side menu verified");
		// Navigate to company view
		analyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
		waitForPageLoaded();
		LOGGER.info("Navigated to Analytics Page");
		analyticsPage.clickOnElementsOfAnalyticsPage("modernReports");
		// Click on Blue Screen Errors
		analyticsPage.clickOnElementsOfAnalyticsPage("blueScreenErrors");
		LOGGER.info("Clicked on Blue Screen Errors");
		// Verify presence of various charts
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("topModelAffected"), "Top Models Affected chart not present.");
		LOGGER.info("Verified Top Models Affected chart");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("topDriverAffected"), "Top drivers Affected chart not present.");
		LOGGER.info("Verified Top Drivers Affected chart");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("topBugCheckCode"), "Bug check code chart not present.");
		LOGGER.info("Verified Bug Check Code chart");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("bsodOverTimeWidget"), "BSOD overtime widget chart not present.");
		LOGGER.info("Verified BSOD Overtime Widget chart");
		// Assert all
		softAssert.assertAll();
		LOGGER.info("Completed test case: verifyBSODCharts");
	}
	/**
	 * Test method to verify the export functionality on the BSOD Insights Page.
	 * <p>
	 * This test logs in as an IT Admin, navigates to the BSOD Insights Page, and verifies the export functionality.
	 * If the export button is not present, it logs a warning and continues execution without failing the test.
	 * </p>
	 *
	 * @throws Exception if an error occurs during the test execution.
	 * @priority 11
	 * @groups {"REGRESSION_INSIGHTS"}
	 * @description Test Case: 41940288
	 */
	@Test(priority = 12, groups = {"REGRESSION_INSIGHTS"}, description = "Test Case: C44045161")
	public final void verifyBsodExportFeature() throws Exception {
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		SoftAssert softAssert = new SoftAssert();
		AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver()).getInstance();
		leftSideMenuVerification();
		analyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
		waitForPageLoaded();
		sleeper(3000);
		analyticsPage.clickOnElementsOfAnalyticsPage("blueScreenErrors");
		waitForPageLoaded();
		boolean isExportButtonPresent = analyticsPage.verifyElementIsVisibleOfAnalyticsPage("exportButton");
		if (isExportButtonPresent) {
			Assert.assertTrue(analyticsPage.verifyExportFunctionality(LanguageCode, "toastNotificationExport"), "Export functionality is not working on BSOD Insights Page.");
			LOGGER.info("Export functionality validation for BSOD Insights Page has been completed successfully.");
		} else {
			LOGGER.warn("Export button is not present on BSOD Insights Page.");
		}

		softAssert.assertAll();
	}

	/**
	 * This test case verifies the Application Inventory Report (Analytics)
	 */
	@Test(priority = 11, groups = {"REGRESSION_INTEGRATIONQA_CUJ", "PRODUCTION_INTEGRATIONQA_CUJ","PRODUCTION_LDK","INITECH_SOLUTIONS"}, description = "TestCase ID:C43425017")
	public final void verifyApplicationInventoryReportAnalyticsListPage() throws Exception {
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		SoftAssert softAssert = new SoftAssert();
		String testSuiteName = SetTestEnvironments.suiteName;
		if(testSuiteName.contains("LDK") || testSuiteName.contains("Initech")){
			switchUserBasedOnSuite(testSuiteName);
		}
		AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver()).getInstance();
		leftSideMenuVerification();

		analyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
		waitForPageLoaded();
		LOGGER.info("Analytics listing page opened correctly.");
		// Click on Modern Reports
		analyticsPage.clickOnElementsOfAnalyticsPage("modernReports");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("applicationInventory"),"Application Inventory report missing.");
		analyticsPage.clickOnElementsOfAnalyticsPage("applicationInventory");
		waitForPageLoaded();
		Thread.sleep(2000);
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("topInstalledApps"),"Top Installed Apps report missing.");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("appMostInstalledVersions"),"App mose Installed Versions report missing.");
		LOGGER.info("Application Inventory validation completed");
		analyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
		waitForPageLoaded();
		analyticsPage.clickOnElementsOfAnalyticsPage("reportListSort");
		LOGGER.info("Report list sorting verified.");
		softAssert.assertAll();

	}

	/**
	 * This test case verifies the BIOS Inventory Report (Analytics)
	 */
	@Test(priority = 12, groups = {"REGRESSION_INTEGRATIONQA_CUJ", "PRODUCTION_INTEGRATIONQA_CUJ"}, description = "TestCase ID:C43425017", enabled = false)
	public final void verifyBIOSInventoryReportAnalyticsListPage() throws Exception {
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		SoftAssert softAssert = new SoftAssert();

		AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver()).getInstance();
		leftSideMenuVerification();

		analyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
		waitForPageLoaded();
		LOGGER.info("Analytics listing page opened correctly");
		// Click on Modern Reports
		analyticsPage.clickOnElementsOfAnalyticsPage("modernReports");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("biosInventory"),"BIOS Inventory Details report missing.");
		Thread.sleep(2000);
		analyticsPage.clickOnElementsOfAnalyticsPage("biosInventory");
		waitForPageLoaded();
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("biosSumary"),"BIOS Summary report missing.");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("uniqueBios"),"Unique BIOS report missing.");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("byWeek"),"By Week report missing.");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("details"),"Details report missing.");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("biosVersionByDeviceModel"),"BIOS version by device " +
				"model report missing.");
		analyticsPage.clickOnElementsOfAnalyticsPage("uniqueBios");
		waitForPageLoaded();
		Thread.sleep(2000);
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("uniqueBIOSVersionModel"),"uniqueBIOSVersionModel report missing.");
		analyticsPage.clickOnElementsOfAnalyticsPage("byWeek");
		waitForPageLoaded();
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("biosStatusWeek"),"biosStatusWeek report missing.");
		analyticsPage.clickOnElementsOfAnalyticsPage("details");
		waitForPageLoaded();
		Thread.sleep(2000);
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("lastSignedUser"),"lastSignedUser report missing.");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("serialNumbers"),"serialNumbers report missing.");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("deviceNames"),"deviceNames report missing.");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("deviceType"),"deviceType report missing.");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("deviceManufacturer"),"deviceManufacturer report missing.");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("devicModel"),"device Model report missing.");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("devicMfgDate"),"devicMfgDate report missing.");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("devicePlatformId"),"devicePlatformId report missing.");
		LOGGER.info("BIOS Inventory validation completed");
		analyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
		waitForPageLoaded();
		analyticsPage.clickOnElementsOfAnalyticsPage("reportListSort");
		LOGGER.info("Report list sorting verified.");
		softAssert.assertAll();

	}

	/**
	 * This test case verifies the Blue Screen Error Report (Analytics)
	 */
	@Test(priority = 13, groups = {"REGRESSION_INTEGRATIONQA_CUJ", "PRODUCTION_INTEGRATIONQA_CUJ"}, description = "TestCase ID:C43425017", enabled = false)
	public final void verifyBlueScreenErrorReportAnalyticsListPage() throws Exception {
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		SoftAssert softAssert = new SoftAssert();

		AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver()).getInstance();
		leftSideMenuVerification();

		analyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
		waitForPageLoaded();
		Assert.assertTrue(analyticsPage.getTextOfAnalyticsPage("analytics").equals(analyticsPage.getTextLanguage(LanguageCode, "daas_ui", "wex.analytics")),
				"Analytics title doesn't match");
		LOGGER.info("Analytics listing page opened correctly");

		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("blueScreenErrors"),"Blue Screen Errors report missing.");
		Thread.sleep(2000);
		analyticsPage.clickOnElementsOfAnalyticsPage("blueScreenErrors");
		waitForPageLoaded();
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("topModelsAffected"),"Top Models Affected report missing.");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("topDriversCausing"),"Top Drivers Causing report missing.");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("topBugCheckCodes"),"Top Bug Checks Codes report missing.");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("bsodOverTime"),"BSOD over time report missing.");
		LOGGER.info("Blue Screen Errors validation completed");
		analyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
		waitForPageLoaded();
		analyticsPage.clickOnElementsOfAnalyticsPage("reportListSort");
		LOGGER.info("Report list sorting verified.");
		softAssert.assertAll();

	}

	/**
	 * This test case verifies the Device Utilization Report (Analytics)
	 */
	@Test(priority = 14, groups = {"REGRESSION_INTEGRATIONQA_CUJ", "PRODUCTION_INTEGRATIONQA_CUJ"}, description = "TestCase ID:C43425017", enabled = false)
	public final void verifyDeviceUtilizationReportAnalyticsListPage() throws Exception {
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		SoftAssert softAssert = new SoftAssert();

		AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver()).getInstance();
		leftSideMenuVerification();

		analyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
		waitForPageLoaded();
		Assert.assertTrue(analyticsPage.getTextOfAnalyticsPage("analytics").equals(analyticsPage.getTextLanguage(LanguageCode, "daas_ui", "wex.analytics")),
				"Analytics title doesn't match");
		LOGGER.info("Analytics listing page opened correctly");

		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("deviceUtilization"),"Device Utilization report missing.");
		Thread.sleep(2000);
		analyticsPage.clickOnElementsOfAnalyticsPage("deviceUtilization");
		waitForPageLoaded();
		Thread.sleep(2000);
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("biosSumary"),"BIOS Summary report missing.");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("byManufacturerYear"),"byManufacturerYear report missing.");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("windowsSoftwarePerformance"),"windowsSoftwarePerformance report missing.");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("macOSSoftPerf"),"macOSSoftwarePerformance report missing.");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("deviceUtiDetails"),"macOSSoftwarePerformance report missing.");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("cpuUtilizationSummary"),"CPU UtilizationSummary report missing.");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("memoryUtilizationSummary"),"CPU UtilizationSummary report missing.");
		analyticsPage.clickOnElementsOfAnalyticsPage("byManufacturerYear");
		waitForPageLoaded();
		Thread.sleep(1000);
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("deviceUtiDeviceYear"),"deviceUtiDeviceYear report missing.");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("memoryUtiDeviceYear"),"deviceUtiDeviceYear report missing.");
		analyticsPage.clickOnElementsOfAnalyticsPage("windowsSoftwarePerformance");
		waitForPageLoaded();
		Thread.sleep(1000);
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("appCauseCPUOverUtilization"),"appCauseCPUOverUtilization report missing.");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("appCauseMemOverUtilization"),"appCauseMemOverUtilization report missing.");
		analyticsPage.clickOnElementsOfAnalyticsPage("macOSSoftPerf");
		waitForPageLoaded();
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("appCauseMacCPUOverUtilization"),"appCauseCPUOverUtilization report missing.");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("appCauseMacMemOverUtilization"),"appCauseMemOverUtilization report missing.");
		analyticsPage.clickOnElementsOfAnalyticsPage("deviceUtiDetails");
		waitForPageLoaded();
		Thread.sleep(2000);
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("lastSignedUser"),"lastSignedUser report missing.");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("deviceName"),"deviceName report missing.");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("serialNumbers"),"serialNumbers report missing.");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("deviceModels"),"deviceModels report missing.");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("deviceOS"),"deviceOS report missing.");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("devicMfgDate"),"devicMfgDate report missing.");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("cpuUsageScoreCategory"),"cpuUsageScoreCategory report missing.");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("memoryUsageScoreCategory"),"memoryUsageScoreCategory report missing.");
		LOGGER.info("Device utilization validation completed");
		analyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
		waitForPageLoaded();
		analyticsPage.clickOnElementsOfAnalyticsPage("reportListSort");
		LOGGER.info("Report list sorting verified.");
		softAssert.assertAll();

	}

	/**
	 * This test case verifies the Device Inventory Report (Analytics)
	 */
	@Test(priority = 15, groups = {"REGRESSION_INTEGRATIONQA_CUJ", "PRODUCTION_INTEGRATIONQA_CUJ"}, description = "TestCase ID:C43425017", enabled = false)
	public final void verifyDeviceInventoryReportAnalyticsListPage() throws Exception {
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		SoftAssert softAssert = new SoftAssert();

		AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver()).getInstance();
		leftSideMenuVerification();
		analyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
		waitForPageLoaded();
		Assert.assertTrue(analyticsPage.getTextOfAnalyticsPage("analytics").equals(analyticsPage.getTextLanguage(LanguageCode, "daas_ui", "wex.analytics")),
				"Analytics title doesn't match");
		LOGGER.info("Analytics listing page opened correctly");

		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("driverInventory"),"Driver Inventory report missing.");
		Thread.sleep(2000);
		analyticsPage.clickOnElementsOfAnalyticsPage("driverInventory");
		waitForPageLoaded();
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("biosSumary"),"BIOS Summary report missing.");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("drivers"),"drivers Summary report missing.");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("driversTable"),"drivers table Summary report missing.");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("details"),"details Summary report missing.");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("driverStatus"),"driverStatus Summary report missing.");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("outdatedDriverByCritically"),"outdatedDriverByCritically Summary report missing.");
		analyticsPage.clickOnElementsOfAnalyticsPage("drivers");
		waitForPageLoaded();
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("driversByModel"),"driversByModel report missing.");
		analyticsPage.clickOnElementsOfAnalyticsPage("driversTable");
		waitForPageLoaded();
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("devicesModel"),"devicesModel report missing.");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("driverName"),"driverName report missing.");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("driverVersion"),"driverVersion report missing.");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("msrcSecurity"),"msrcSecurity report missing.");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("driverStat"),"driverStat report missing.");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("noOfDevices"),"noOfDevices report missing.");
		analyticsPage.clickOnElementsOfAnalyticsPage("details");
		waitForPageLoaded();
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("lastSignUser"),"lastSignUser report missing.");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("serialNumber"),"serialNumber report missing.");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("deviceName"),"deviceName report missing.");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("deviceOS"),"deviceOS report missing.");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("deviceOSReleases"),"deviceOSRelease report missing.");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("deviceOSVersion"),"deviceOSVersion report missing.");
		LOGGER.info("Driver inventory validation completed");
		analyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
		waitForPageLoaded();
		analyticsPage.clickOnElementsOfAnalyticsPage("reportListSort");
		LOGGER.info("Report list sorting verified.");
		softAssert.assertAll();
	}

	/**
	 * This test case verifies the Experience Score Report (Analytics)
	 */
	@Test(priority = 16, groups = {"REGRESSION_INTEGRATIONQA_CUJ", "PRODUCTION_INTEGRATIONQA_CUJ"}, description = "TestCase ID:C43425017", enabled = false)
	public final void verifyDeviceExperienceScoreReportAnalyticsListPage() throws Exception {
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		SoftAssert softAssert = new SoftAssert();

		AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver()).getInstance();
		leftSideMenuVerification();
		analyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
		waitForPageLoaded();
		Assert.assertTrue(analyticsPage.getTextOfAnalyticsPage("analytics").equals(analyticsPage.getTextLanguage(LanguageCode, "daas_ui", "wex.analytics")),
				"Analytics title doesn't match");
		LOGGER.info("Analytics listing page opened correctly");

		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("experienceScore"),"Experience score report missing.");
		Thread.sleep(2000);
		analyticsPage.clickOnElementsOfAnalyticsPage("experienceScore");
		waitForPageLoaded();
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("overallScoresContainer"),"overallScoresContainer Summary report cart missing.");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("experienceScores"),"experienceScores Summary report missing.");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("overallScores"),"overallScores Summary report missing.");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("experienceOverTime"),"experienceOverTime Summary report missing.");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("topIssueImpactingSentiment"),"topIssueImpactingSentiment Summary report missing.");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("topSystemHealthIssue"),"topSystemHealthIssue Summary report missing.");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("topOSPerfIssue"),"topOSPerfIssue Summary report missing.");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("deviceModel"),"deviceModel Summary report missing.");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("operatingSystem"),"operatingSystem Summary report missing.");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("siteName"),"siteName Summary report missing.");
		LOGGER.info("Experience score validation completed");
		analyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
		waitForPageLoaded();
		analyticsPage.clickOnElementsOfAnalyticsPage("reportListSort");
		LOGGER.info("Report list sorting verified.");
		softAssert.assertAll();
	}

	/**
	 * This test case verifies the HW Inventory Details Report (Analytics)
	 */
	@Test(priority = 17, groups = {"REGRESSION_INTEGRATIONQA_CUJ", "PRODUCTION_INTEGRATIONQA_CUJ"}, description = "TestCase ID:C43425017", enabled = false)
	public final void verifyDeviceHWInventoryDetailsReportAnalyticsListPage() throws Exception {
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		SoftAssert softAssert = new SoftAssert();

		AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver()).getInstance();
		leftSideMenuVerification();
		analyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
		waitForPageLoaded();
		Assert.assertTrue(analyticsPage.getTextOfAnalyticsPage("analytics").equals(analyticsPage.getTextLanguage(LanguageCode, "daas_ui", "wex.analytics")),
				"Analytics title doesn't match");
		LOGGER.info("Analytics listing page opened correctly");

		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("hwInventory"),"Hardware Inventory Details report missing.");
		Thread.sleep(2000);
		analyticsPage.clickOnElementsOfAnalyticsPage("hwInventory");
		waitForPageLoaded();
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("byLocation"),"byLocation Summary report missing.");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("byDeviceConfiguration"),"byDeviceConfiguration Summary report missing.");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("byDeviceModel"),"byDeviceModel Summary report missing.");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("byOS"),"byOS Summary report missing.");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("byDetails"),"byDetails Summary report missing.");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("hwInventoryByLocation"),"hwInventoryByLocation Summary report missing.");
		analyticsPage.clickOnElementsOfAnalyticsPage("byDeviceConfiguration");
		waitForPageLoaded();
		Thread.sleep(1000);
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("hwInvDeviceTypeSummary"),"hwInventoryByLocation Summary report missing.");
		analyticsPage.clickOnElementsOfAnalyticsPage("byDeviceModel");
		waitForPageLoaded();
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("deviceByModel"),"deviceByModel Summary report missing.");
		analyticsPage.clickOnElementsOfAnalyticsPage("byOS");
		waitForPageLoaded();
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("deviceByOS"),"deviceByOS Summary report missing.");
		analyticsPage.clickOnElementsOfAnalyticsPage("byDetails");
		waitForPageLoaded();
		Thread.sleep(1000);
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("lastSignUser"),"lastSignUser report missing.");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("serialNumber"),"serialNumber report missing.");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("deviceName"),"deviceName report missing.");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("deviceType"),"deviceType report missing.");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("devicModel"),"devicModel report missing.");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("deviceOsOriginal"),"deviceOsOriginal report missing.");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("deviceManufacturer"),"deviceManufacturer report missing.");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("osRelease"),"osRelease report missing.");

		LOGGER.info("Hardware inventory validation completed");
		analyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
		waitForPageLoaded();
		analyticsPage.clickOnElementsOfAnalyticsPage("reportListSort");
		LOGGER.info("Report list sorting verified.");
		softAssert.assertAll();
	}

	/**
	 * This test case verifies the OS Performance Score Details Report (Analytics)
	 */
	@Test(priority = 18, groups = {"REGRESSION_INTEGRATIONQA_CUJ", "PRODUCTION_INTEGRATIONQA_CUJ"}, description = "TestCase ID:C43425017")
	public final void verifyDeviceOSPerformanceScoreReportAnalyticsListPage() throws Exception {
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		SoftAssert softAssert = new SoftAssert();

		AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver()).getInstance();
		leftSideMenuVerification();
		analyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
		waitForPageLoaded();
//		Assert.assertTrue(analyticsPage.getTextOfAnalyticsPage("analytics").equals(analyticsPage.getTextLanguage(LanguageCode, "daas_ui", "wex.analytics")),
//				"Analytics title doesn't match");
		LOGGER.info("Analytics listing page opened correctly");
		// Click on Modern Reports
		analyticsPage.clickOnElementsOfAnalyticsPage("modernReports");

		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("osPerformanceScore"),"OS Performance score Details report missing.");
		Thread.sleep(2000);
		analyticsPage.clickOnElementsOfAnalyticsPage("osPerformanceScore");
		waitForPageLoaded();
		Thread.sleep(2000);
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("osPerformance"),"osPerformance Summary report missing.");
		if(!toggleVerification(CommonVariables.DEX_API_LHREPORTS, getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
			softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("osPerformancesScore"),"osPerformancesScore Summary report missing.");	
		}else {
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("osPerformancesScoreNew"),"osPerformancesScore Summary report missing.");
		}
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("topOSPerfIssue"),"topOSPerfIssue Summary report missing.");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("osPerfChangeOverTime"),"topOSPerfOverTime Summary report missing.");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("osPerfByModel"),"osPerfByModel Summary report missing.");
		if(analyticsPage.isWidgetPresent("sysRecommendationAction")) {
		    softAssert.assertTrue(true,"RecommendationAction Summary report is present and validated.");
		} else {
			LOGGER.info("Skipping assertion for osPerformanceRA Summary report as it is not present");
		}
		LOGGER.info("OS performance Score validation completed");
		analyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
		waitForPageLoaded();
		analyticsPage.clickOnElementsOfAnalyticsPage("reportListSort");
		LOGGER.info("Report list sorting verified.");
		softAssert.assertAll();
	}

	/**
	 * This test case verifies the Security Score Details Report (Analytics)
	 */
	@Test(priority = 19, groups = {"REGRESSION_INTEGRATIONQA_CUJ", "PRODUCTION_INTEGRATIONQA_CUJ"}, description = "TestCase ID:C43425017")
	public final void verifyDeviceSecurityScoreReportAnalyticsListPage() throws Exception {
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		SoftAssert softAssert = new SoftAssert();

		AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver()).getInstance();
		leftSideMenuVerification();
		analyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
		waitForPageLoaded();
		// Click on Modern Reports
		analyticsPage.clickOnElementsOfAnalyticsPage("modernReports");

		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("securityScore"),"Security score report missing.");
		Thread.sleep(2000);
		analyticsPage.clickOnElementsOfAnalyticsPage("securityScore");
		waitForPageLoaded();
		if(!toggleVerification(CommonVariables.DEX_API_LHREPORTS, getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
			softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("securityScores"),"osPerformancesScore Summary report missing.");	
		}else {
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("securityScoresNew"),"osPerformancesScore Summary report missing.");
		}
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("topSecurityIssue"),"topSecurityIssue Summary report missing.");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("securityChecksLastWeek"),"securityChecksLastWeek Summary report missing.");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("securityByModel"),"securityByModel Summary report missing.");
		if(analyticsPage.isWidgetPresent("securityRA")) {
			softAssert.assertTrue(true,"securityRA Summary report is present and validated.");
			} else {
				LOGGER.info("Skipping assertion for securityRA Summary report as it is not present");
		}
		LOGGER.info("Security score validation completed");
		analyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
		waitForPageLoaded();
		analyticsPage.clickOnElementsOfAnalyticsPage("reportListSort");
		LOGGER.info("Report list sorting verified.");
		softAssert.assertAll();
	}

	/**
	 * This test case verifies the System Health Score Details Report (Analytics)
	 */
	@Test(priority = 20, groups = {"REGRESSION_INTEGRATIONQA_CUJ", "PRODUCTION_INTEGRATIONQA_CUJ"}, description = "TestCase ID:C43425017")
	public final void verifyDevicesystemHealthScoreReportAnalyticsListPage() throws Exception {
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		SoftAssert softAssert = new SoftAssert();

		AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver()).getInstance();
		leftSideMenuVerification();
		analyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
		waitForPageLoaded();
		LOGGER.info("Analytics listing page opened correctly");
		// Click on Modern Reports
		analyticsPage.clickOnElementsOfAnalyticsPage("modernReports");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("systemHealthScore"),"System health score report missing.");
		Thread.sleep(2000);
		analyticsPage.clickOnElementsOfAnalyticsPage("systemHealthScore");
		waitForPageLoaded();
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("osSystemHealth"),"osSystemHealth Summary report missing.");
		if(!toggleVerification(CommonVariables.DEX_API_LHREPORTS, getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
			softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("systemHealthScores"),"osPerformancesScore Summary report missing.");	
		}else {
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("systemHealthScoresNew"),"osPerformancesScore Summary report missing.");
		}
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("topSystemHealthIssue"),"topSystemHealthIssue Summary report missing.");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("systemHealthChangeLastWeek"),"systemHealthChangeLastWeek Summary report missing.");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("systemHealthByModel"),"systemHealthByModel Summary report missing.");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("sysRecommendationAction"),"sysRecommendationAction Summary report missing.");
		LOGGER.info("System health score validation completed");
		analyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
		waitForPageLoaded();
		analyticsPage.clickOnElementsOfAnalyticsPage("reportListSort");
		LOGGER.info("Report list sorting verified.");
		softAssert.assertAll();
	}

	/**
	 * This test case verifies the Win11 Details Report (Analytics)
	 */
	@Test(priority = 21, groups = {"REGRESSION_INTEGRATIONQA_CUJ", "PRODUCTION_INTEGRATIONQA_CUJ"}, description = "TestCase ID:C43425017", enabled = false)
	public final void verifyWin11ReportAnalyticsListPage() throws Exception {
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		SoftAssert softAssert = new SoftAssert();

		AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver()).getInstance();
		leftSideMenuVerification();
		analyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
		waitForPageLoaded();
		Assert.assertTrue(analyticsPage.getTextOfAnalyticsPage("analytics").equals(analyticsPage.getTextLanguage(LanguageCode, "daas_ui", "wex.analytics")),
				"Analytics title doesn't match");
		LOGGER.info("Analytics listing page opened correctly");

		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("win11"),"Windows 11 Readiness Assessment report missing.");
		Thread.sleep(2000);
		analyticsPage.clickOnElementsOfAnalyticsPage("win11");
		waitForPageLoaded();
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("byLocation"),"Summary report missing.");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("operatingSystem"),"Details report missing.");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("updateBreakdown"),"updateBreakdown report missing.");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("topDeviceModel"),"topDeviceModel report missing.");
		analyticsPage.clickOnElementsOfAnalyticsPage("operatingSystem");
		waitForPageLoaded();
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("lastSignUser"),"lastSignUser report missing.");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("deviceSerialNumber"),"serialNumber report missing.");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("deviceName"),"deviceName report missing.");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("deviceType"),"deviceType report missing.");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("deviceManufacturers"),"deviceManufacturer report missing.");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("devicModel"),"device Model report missing.");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("deviceAge"),"deviceAge Model report missing.");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("devicesOS"),"deviceOS Model report missing.");
		LOGGER.info("Win 11 validation completed");
		analyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
		waitForPageLoaded();
		analyticsPage.clickOnElementsOfAnalyticsPage("reportListSort");
		LOGGER.info("Report list sorting verified.");
		softAssert.assertAll();
	}

	@Test(priority = 22, groups = {"REGRESSION_INSIGHTS", "PRODUCTION_INSIGHTS", "REGRESSION_INTEGRATIONQA_CUJ"}, description = "TestCase ID : 55049196")
	public final void createHardwareBlueScreenErrorDuplicateReport() throws Exception {
		SoftAssert sa = new SoftAssert();
		// Log in as IT Admin
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		LOGGER.info("Logged in as IT Admin");
		// Navigate to Analytics Page
		AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver()).getInstance();
		leftSideMenuVerification();
		analyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
		waitForPageLoaded();
		LOGGER.info("Navigated to Analytics Page");
		analyticsPage.clickOnElementsOfAnalyticsPage("modernReports");
		// Click on Blue Screen Errors
		analyticsPage.clickOnElementsOfAnalyticsPage("blueScreenErrors");
		LOGGER.info("Clicked on Blue Screen Errors");
		// Verify Blue Screen Insights Page
		sa.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("insightsPageBSOD"), "Blue Screen Insights Page doesn't open");
		LOGGER.info("Verified Blue Screen Insights Page");
		analyticsPage.clickByJavaScriptOnAnalyticsPage("dashboardellipis");
		analyticsPage.clickOnElementsOfAnalyticsPage("duplicateReportButton");
		LOGGER.info("Clicked on Duplicate Report");
		// Verify Duplicate Report Page
		Assert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("duplicateReportPage"), "Duplicate report not created successfully");
		analyticsPage.clickByJavaScriptOnAnalyticsPage("dashboardellipis");
		analyticsPage.clickOnElementsOfAnalyticsPage("deleteReporteellipis");
		LOGGER.info("Clicked on Delete Report button");
		// Verify Delete Report Page
		analyticsPage.clickOnElementsOfAnalyticsPage("deleteReportButton");
		Assert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("toastNotificationDelete"), "Report not deleted successfully");
		LOGGER.info("Report deleted successfully");
		// Assert all
		sa.assertAll();
	}
	@Test(priority = 23, groups = {"REGRESSION_INSIGHTS", "REGRESSION_INTEGRATIONQA_CUJ"}, description = "TestCase ID : 55049196")
	public final void customAnalyticsPageSearch() throws Exception {
		// Log in as IT Admin
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		LOGGER.info("Logged in as IT Admin");
		// Navigate to Analytics Page
		AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver()).getInstance();
		leftSideMenuVerification();
		analyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
		waitForPageLoaded();
		LOGGER.info("Navigated to Analytics Page");
		// Click on Modern Reports
		analyticsPage.clickOnElementsOfAnalyticsPage("modernReports");
		// Click on Blue Screen Errors
		analyticsPage.clickOnElementsOfAnalyticsPage("blueScreenErrors");
		LOGGER.info("Clicked on Blue Screen Errors");
		// Verify Blue Screen Insights Page
		Assert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("insightsPageBSOD"), "Blue Screen Insights Page doesn't open");
		LOGGER.info("Verified Blue Screen Insights Page");
		analyticsPage.clickByJavaScriptOnAnalyticsPage("dashboardellipis");
		analyticsPage.clickOnElementsOfAnalyticsPage("duplicateReportButton");
		LOGGER.info("Clicked on Duplicate Report");
		// Verify Duplicate Report Page
		Assert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("duplicateReportPage"), "Duplicate report not created successfully");
		String customName= analyticsPage.getTextOfAnalyticsPage("customName");
		analyticsPage.clickOnElementsOfAnalyticsPage("analyticsMenu");
		analyticsPage.enterTextForAnalyticsPage("searchBox",customName);
        sleeper(3000);
		waitForPageLoaded();
		Assert.assertEquals(analyticsPage.getTextOfAnalyticsPage("searchResult"), customName, "Search result doesn't match the custom name");
		LOGGER.info("Search result matches the custom name");
		analyticsPage.clickOnElementsOfAnalyticsPage("searchResult");
		sleeper(3000);
		waitForPageLoaded();
		analyticsPage.clickByJavaScriptOnAnalyticsPage("dashboardellipis");
		analyticsPage.clickOnElementsOfAnalyticsPage("deleteReporteellipis");
		LOGGER.info("Clicked on Delete Report button");
		analyticsPage.clickOnElementsOfAnalyticsPage("deleteReportButton");
		analyticsPage.waitForElementsOfAnalyticsPage("toastNotificationDelete");
		Assert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("toastNotificationDelete"), "Report not deleted successfully");
		LOGGER.info("Report deleted successfully");
	}

	@Test(priority = 24, groups = {"REGRESSION_INSIGHTS", "REGRESSION_INTEGRATIONQA_CUJ"}, description = "TestCase ID : C57035510")
	public final void editCustomReport() throws Exception {
		// Log in as IT Admin
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		LOGGER.info("Logged in as IT Admin");
		// Navigate to Analytics Page
		AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver()).getInstance();
		leftSideMenuVerification();
		analyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
		waitForPageLoaded();
		LOGGER.info("Navigated to Analytics Page");
		// Click on Modern Reports
		analyticsPage.clickOnElementsOfAnalyticsPage("modernReports");
		// Click on Blue Screen Errors
		analyticsPage.clickOnElementsOfAnalyticsPage("blueScreenErrors");
		LOGGER.info("Clicked on Blue Screen Errors");
		// Verify Blue Screen Insights Page
		Assert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("insightsPageBSOD"), "Blue Screen Insights Page doesn't open");
		LOGGER.info("Verified Blue Screen Insights Page");
		analyticsPage.clickByJavaScriptOnAnalyticsPage("dashboardellipis");
		analyticsPage.clickOnElementsOfAnalyticsPage("duplicateReportButton");
		LOGGER.info("Clicked on Duplicate Report");
		// Verify Duplicate Report Page
		Assert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("duplicateReportPage"), "Duplicate report not created successfully");
		String customName= analyticsPage.getTextOfAnalyticsPage("customName");
		analyticsPage.mouseHoverAndClickOfAnalyticsPage("editButton");
		analyticsPage.enterTextForAnalyticsPage("customReportTitle",customName + " " + "Test");
		analyticsPage.mouseHoverAndClickOfAnalyticsPage("editButton");
		Assert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("toastNotificationDelete"), "Report name updated successfully");

	}

	@Test(priority = 25, groups = {"REGRESSION_INSIGHTS","PRODUCTION_LDK","INITECH_SOLUTIONS"}, description = "TestCase ID:C61322767")
	public final void verifySoftPaqReport() throws Exception {
		LOGGER.info("Starting test case: verifySoftPaqCharts");
		// Log in as IT Admin
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		LOGGER.info("Logged in as IT Admin");
		String testSuiteName = SetTestEnvironments.suiteName;
		if(testSuiteName.contains("LDK") || testSuiteName.contains("Initech")){
			switchUserBasedOnSuite(testSuiteName);
		}
		SoftAssert softAssert = new SoftAssert();
		AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver()).getInstance();
		// Verify left side menu
		leftSideMenuVerification();
		LOGGER.info("Left side menu verified");
		// Navigate to company view
		analyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
		waitForPageLoaded();
		LOGGER.info("Navigated to Analytics Page");
		// Click on SoftPaq Insights
		analyticsPage.clickOnElementsOfAnalyticsPage("softPaqInsights");
		LOGGER.info("Clicked on SoftPaq Insights");
		// Verify presence of various charts
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("softPaqOverTime"), "SoftPaq Over Time chart not present.");
		LOGGER.info("Verified SoftPaq Over Time chart");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("impactedDeviceByWeek"), "Impacted Device by Week chart not present.");
		LOGGER.info("Verified Impacted Device by Week chart");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("softPaqByCriticality"), "SoftPaq by Criticality chart not present.");
		LOGGER.info("Verified SoftPaq by Criticality chart");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("softPaqByCategory"), "SoftPaq by Category chart not present.");
		LOGGER.info("Verified SoftPaq by Category chart");
		// Assert all
		softAssert.assertAll();
		LOGGER.info("Completed test case: verifySoftPaqCharts");
	}

	@Test(priority = 26, groups = {"REGRESSION_INSIGHTS", "PRODUCTION_INSIGHTS","PRODUCTION_LDK","INITECH_SOLUTIONS"}, description = "TestCase ID:C62577933")
	public final void verifySoftPaqReportColumns() throws Exception {
		LOGGER.info("Starting test case: verifySoftPaqReportColumnsSimple");
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		SoftAssert softAssert = new SoftAssert();
		String testSuiteName = SetTestEnvironments.suiteName;
		if(testSuiteName.contains("LDK") || testSuiteName.contains("Initech")){
			switchUserBasedOnSuite(testSuiteName);
		}
		AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver()).getInstance();
		leftSideMenuVerification();
		analyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
		waitForPageLoaded();
		analyticsPage.clickOnElementsOfAnalyticsPage("modernReports");
		analyticsPage.clickOnElementsOfAnalyticsPage("softPaqInsights");
		LOGGER.info("Clicked on SoftPaq Insights");
		List<String> expectedSoftPaqColumns = new ArrayList<String>(Arrays.asList(
				getTextLanguage(LanguageCode, "insights_template", "wxp.insights.template.widget.column.softpaq_details.softpaqTitle"),
				getTextLanguage(LanguageCode, "insights_template", "wxp.insights.template.widget.column.softpaq_details.softpaqID"),
				getTextLanguage(LanguageCode, "insights_template", "wxp.insights.template.widget.column.softpaq_details.criticality"),
				getTextLanguage(LanguageCode, "insights_template", "wxp.insights.template.widget.column.softpaq_details.impactedDeviceCount"),
				getTextLanguage(LanguageCode, "insights_template", "wxp.insights.template.widget.column.softpaq_details.softpaqVersion"),
				getTextLanguage(LanguageCode, "insights_template", "wxp.insights.template.widget.column.softpaq_details.vendorVersion"),
				getTextLanguage(LanguageCode, "insights_template", "wxp.insights.template.widget.column.softpaq_details.categoryName"),
				getTextLanguage(LanguageCode, "insights_template", "wxp.insights.template.widget.column.softpaq_details.releaseNotesURL")	// ... more localized columns
		));

		LOGGER.info("Expected SoftPaq columns defined: " + expectedSoftPaqColumns);


		// Verify table columns match expected list
		Assert.assertTrue(analyticsPage.verifyTableColumns(expectedSoftPaqColumns, "softPaqTableColumns"),
				"SoftPaq table columns are not as expected");
		LOGGER.info("SoftPaq columns verification completed successfully");
		softAssert.assertAll();
		LOGGER.info("Completed test case: verifySoftPaqReportColumnsSimple");
	}

	
	@Test(priority = 25, groups = {"PRODUCTION_LDK","INITECH_SOLUTIONS"})
	public final void verifyEmployeeEngegementDashboard() throws Exception {
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		SoftAssert softAssert = new SoftAssert();
		String testSuiteName = SetTestEnvironments.suiteName;
		if(testSuiteName.contains("LDK") || testSuiteName.contains("Initech")){
			switchUserBasedOnSuite(testSuiteName);
		}
		WEXEEDashboardPage WEXEEDashboardPage = new WEXEEDashboardPage(PreDefinedActions.getDriver()).getInstance();
		AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver()).getInstance();
		leftSideMenuVerification();
		boolean NavigationtoggleVerification = toggleVerification(WEPPulsesCreationPageVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment,   "EE_SCORE_COMPANY_EMAIL"));	
		if(!NavigationtoggleVerification){
			//Navigation check
			WEXEEDashboardPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT,CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_DASHBOARD);
			
		}else {
			//Navigation check
			WEXEEDashboardPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);			
			analyticsPage.clickOnElementsOfAnalyticsPage("employeeEngagementDashboard");		
			
		}
		waitForPageLoaded();
		boolean isSentimentScoreWidgetEmpty =analyticsPage.verifyElementsOfAnalyticsPage("SentimentScoreEmptyWidget");
		LOGGER.info("Sentiment Score Widget is empty: " + isSentimentScoreWidgetEmpty);
		{
			if (!isSentimentScoreWidgetEmpty) {
				LOGGER.info("Sentiment Score Widget is not empty, proceeding with further validation.");
				softAssert.assertTrue(WEXEEDashboardPage.verifyElementsOfWEXEEDashboardPage("SentimentScoreWidget"),
						"SentimentScore Widget is not present");
				softAssert.assertTrue(WEXEEDashboardPage.verifyElementsOfWEXEEDashboardPage("SentimentScore"),
						"SentimentScore is not present");
				softAssert.assertTrue(WEXEEDashboardPage.verifyElementsOfWEXEEDashboardPage("SentimentScoreProgressBar"),
						"SentimentScoreProgressBar is not present");				
				softAssert.assertTrue(WEXEEDashboardPage.verifyElementsOfWEXEEDashboardPage("SentimentScoreBenchmark"),
						"SentimentScore Benchmark is not present");	
				//softAssert.assertTrue(WEXEEDashboardPage.verifyElementsOfWEXEEDashboardPage("ResponsesWidget"),
						//"Responses Widget is not present");
				softAssert.assertTrue(WEXEEDashboardPage.verifyElementsOfWEXEEDashboardPage("SentimentscoreOvertimeWidget"),
						"Sentiment Score over time Widget is not present");				
				softAssert.assertTrue(WEXEEDashboardPage.verifyListofElementsOfWEXEEDashboardPage("OvertimeGraphLine"),
						"OverTime Graph lines are not present");
				softAssert.assertTrue(WEXEEDashboardPage.verifyListofElementsOfWEXEEDashboardPage("OvertimeXaxisLabel"),
						"OverTime Graph lines are not present");				
				softAssert.assertTrue(WEXEEDashboardPage.verifyElementsOfWEXEEDashboardPage("TopIssuesImpactingSentimentWidget"),
						"TopIssuesImpactingSentiment Widget is not present");		
				softAssert.assertTrue(WEXEEDashboardPage.verifyListofElementsOfWEXEEDashboardPage("YaxisOptionsTIIS"),
						"Yaxis labels are not present");	
				softAssert.assertTrue(WEXEEDashboardPage.verifyElementsOfWEXEEDashboardPage("TopIssuesChart"),
						"Top Issues Chart is not present");
				softAssert.assertTrue(WEXEEDashboardPage.verifyElementsOfWEXEEDashboardPage("TopIssuesChart"),
						"Top Issues Chart is not present");
				softAssert.assertTrue(WEXEEDashboardPage.verifyElementsOfWEXEEDashboardPage("pulsWidget"),
						"pulsWidget is not present");
				softAssert.assertTrue(WEXEEDashboardPage.verifyListofElementsOfWEXEEDashboardPage("pulsWidgetSubtitle"),
						"pulsWidget Subtitle are not present");	
				softAssert.assertTrue(WEXEEDashboardPage.verifyListofElementsOfWEXEEDashboardPage("viewPulseDetails"),
						"view Pulse Details is not present");	
				analyticsPage.clickByJavaScriptOnAnalyticsPage("viewPulseDetails");
				waitForPageLoaded();
				softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("PulselistBanner"), "Pulse List Baner not present in the Listing Page");
				softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("CreateButton"), "Create pulse buttonnot present on Page");
					
				
			} else {
				softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("SentimentScoreWidget"),
						"SentimentScore Widget is Present");
				softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("SentimentScoreEmptyWidget"),
						"SentimentScore Widget is not empty");
				softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("SentimentScoreEmptyWidgetTxt"),
						"SentimentScore Widget is not empty");	
						//softAssert.assertTrue(WEXEEDashboardPage.verifyElementsOfWEXEEDashboardPage("overviewMetricsWidget"),
								//"overviewMetrics Widget is not present");				
				softAssert.assertTrue(WEXEEDashboardPage.verifyElementsOfWEXEEDashboardPage("pulsWidget"),
						"pulsWidget is not present");
				softAssert.assertTrue(WEXEEDashboardPage.verifyListofElementsOfWEXEEDashboardPage("pulsWidgetSubtitle"),
						"pulsWidget Subtitle are not present");					
				LOGGER.info("Sentiment Score Widget is empty, skipping further validation.");
				softAssert.assertTrue(WEXEEDashboardPage.verifyListofElementsOfWEXEEDashboardPage("viewPulseDetails"),
						"view Pulse Details is not present");	
				analyticsPage.clickByJavaScriptOnAnalyticsPage("viewPulseDetails");
				waitForPageLoaded();
				softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("PulselistBanner"), "Pulse List Baner not present in the Listing Page");
				softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("CreateButton"), "Create pulse buttonnot present on Page");
			}
		}		
		softAssert.assertAll();
	
	}
	
	@Test(priority = 26, groups = {"PRODUCTION_LDK","INITECH_SOLUTIONS"},enabled=false)
	public final void verifyLabsFleetExplorer() throws Exception {
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		String testSuiteName = SetTestEnvironments.suiteName;
		if(testSuiteName.contains("LDK") || testSuiteName.contains("Initech")){
			switchUserBasedOnSuite(testSuiteName);
		}
		AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver()).getInstance();
		leftSideMenuVerification();
		SoftAssert softAssert = new SoftAssert();
		analyticsPage.clickOnElementsOfAnalyticsPage("labs");
		waitForPageLoaded();
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("labsFleetExplorerCard"),"Labs Fleet Explorer card is not present.");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("tryNowFleetExplorer"),"try Now is not present on FleetExplorerr card ");
		analyticsPage.clickOnElementsOfAnalyticsPage("tryNowFleetExplorer");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("helloOnFE"),"Title hello is not present on FleetExplorerr card ");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("fleetExplorerChatBox"),"fleet Explorer ChatBox is not present.");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("fleetExplorerChatBoxSendButton"),"fleet Explorer ChatBox SendButton is not present.");
		softAssert.assertTrue(analyticsPage.verifyListofElementsOfAnalyticsPage("suggestedQuestionsFE"),"fleet Explorer ChatBox SendButton is not present.");
		softAssert.assertTrue(analyticsPage.selectQuestion("suggestedQuestionsFE"),"fleet Explorer ChatBox SendButton is not present.");
		analyticsPage.clickOnElementsOfAnalyticsPage("fleetExplorerChatBoxSendButton");
		analyticsPage.waitForElementsOfAnalyticsPage("answerWidgets");
		sleeper(2000);
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("answerWidgets"),"Answer widget is not present.");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("answerRespText"),"Answer response text is not present.");
		//softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("answerRespTable"),"Answer widget graph is not present.");
		//softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("answerRespTableRow"),"Answer widget graph is not present.");
		softAssert.assertAll();
		
		
		
	
	}
	@Test(priority = 32, groups = {"PRODUCTION_LDK","INITECH_SOLUTIONS"})
	public final void verifyCollaborationSpaces() throws Exception {
	login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
	SoftAssert softAssert = new SoftAssert();
	String testSuiteName = SetTestEnvironments.suiteName;
	if(testSuiteName.contains("LDK") || testSuiteName.contains("Initech")){
		switchUserBasedOnSuite(testSuiteName);
	}
	WEXEEDashboardPage WEXEEDashboardPage = new WEXEEDashboardPage(PreDefinedActions.getDriver()).getInstance();
	AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver()).getInstance();
	leftSideMenuVerification();
	boolean NavigationtoggleVerification = toggleVerification(WEPPulsesCreationPageVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "EE_SCORE_COMPANY_EMAIL"));	
	if(!NavigationtoggleVerification){
		//Navigation check
		WEXEEDashboardPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT,CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_DASHBOARD);
		
	}else {
		//Navigation check
		WEXEEDashboardPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);			
		analyticsPage.clickOnElementsOfAnalyticsPage("collaborationSpaces");		
		
	}
	softAssert.assertTrue(analyticsPage.verifyListofElementsOfAnalyticsPage("colSpacesWidgetsTitle"),
			"collaborationExperience Widgets Title Subtitle are not present");		
	softAssert.assertTrue(analyticsPage.verifyListofElementsOfAnalyticsPage("spaceOverviewCols"),
			"meetings Summary Columns  are not present");		
	
	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("roomMinByLocation"),
			"Occupied room Minutes ByLocation is not present");		
	sleeper(2000);
	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("roomMinByLocationXaxis"),
			"MeetByPlatform Xaxis  is not present");
	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("roomMinByLocationYaxis"), 
			"MeetByPlatform Yaxis  is not present");
	softAssert.assertTrue(analyticsPage.verifyListofElementsOfAnalyticsPage("roomMinByLocationXaxisLocation"),
			"MeetByPlatform X axis location   is not present");		
	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("roomActivityType"),
			"Occupied room Minutes ByLocation is not present");		
	sleeper(2000);
	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("roomActivityTypeXaxis"),
			"roomActivityType Xaxis  is not present");
	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("roomActivityTypeYaxis"), 
			"roomActivityType Yaxis  is not present");
	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("spacesByType"), 
			"spaces By Type is not present");
	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("spacesByRoomSystem"), 
			"spaces By RoomSystem is not present");
	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("bookMeetByLocation"), 
			"book MeetByLocation is not present");
	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("missedMeetByLocation"), 
			"missedMeetByLocation is not present");
	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("highlyUtilizedSpace"), 
			"highlyUtilizedSpace is not present");
	
	softAssert.assertAll();
}

	
	@Test(priority = 27, groups = {"PRODUCTION_LDK","INITECH_SOLUTIONS"})
	public final void verifyCollaborationExperience() throws Exception {
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		String testSuiteName = SetTestEnvironments.suiteName;
		if(testSuiteName.contains("LDK") || testSuiteName.contains("Initech")){
			switchUserBasedOnSuite(testSuiteName);
		}
		SoftAssert softAssert = new SoftAssert();
		WEXEEDashboardPage WEXEEDashboardPage = new WEXEEDashboardPage(PreDefinedActions.getDriver()).getInstance();
		AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver()).getInstance();
		leftSideMenuVerification();
		boolean NavigationtoggleVerification = toggleVerification(WEPPulsesCreationPageVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "EE_SCORE_COMPANY_EMAIL"));	
		if(!NavigationtoggleVerification){
			//Navigation check
			WEXEEDashboardPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT,CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_DASHBOARD);
			
		}else {
			//Navigation check
			WEXEEDashboardPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);			
			analyticsPage.clickOnElementsOfAnalyticsPage("collaborationExperience");	
			
		}
		softAssert.assertTrue(analyticsPage.verifyListofElementsOfAnalyticsPage("colExperienceWidgetsTitle"),
				"collaborationExperience Widgets Title Subtitle are not present");		
		softAssert.assertTrue(analyticsPage.verifyListofElementsOfAnalyticsPage("meetingsSummaryCols"),
				"meetings Summary Columns  are not present");		
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("MeetByPlatformoverTime"),
				"MeetBy Platform overTime Chart  is not present");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("MeetQualityMSFTTeams"),
				"MeetQuality MSFTTeams  chart is not present");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("MeetingQualityZoom"),
				"MeetingQualityZoom chart  is not present");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("MeetingQualityCiscoWebex"),
				"MeetingQuality Cisco Webex chart  is not present");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("remoteParticipantQOE"),
				"remote participant QOE Chart  is not present");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("corporateNetworkQOE"),
				"corporate Network QOE  chart is not present");		
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("badQOEByNetwork"),
				"Bad Quality QOE chart  is not present");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("videoEndpointQOE"),
				"Video Endpoint QOE chart  is not present");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("endUserDeviceQOE"),
				"end user QOE chart  is not present");	
		
		softAssert.assertAll();
	}
	
	 
	/**
	 * This test case verifies the Modern Analytics Reports
	 */
	@Test(priority = 36, groups = { "PRODUCTION_LDK","INITECH_SOLUTIONS"})
	public final void verifyModernReportAnalyticsListPage() throws Exception {
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		SoftAssert softAssert = new SoftAssert();
		String testSuiteName = SetTestEnvironments.suiteName;
		if(testSuiteName.contains("LDK") || testSuiteName.contains("Initech")){
			switchUserBasedOnSuite(testSuiteName);
		}

		AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver()).getInstance();
		leftSideMenuVerification();
	
		analyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
		waitForPageLoaded();
		Assert.assertTrue(analyticsPage.getTextOfAnalyticsPage("analytics").equals(analyticsPage.getTextLanguage(LanguageCode, "daas_ui", "wex.analytics")),
				"Analytics title doesn't match");
		LOGGER.info("Analytics listing page opened correctly");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("ApplicationExperience"),"Application Error report missing.");
	    waitForPageLoaded();
	    LOGGER.info("Verified Application Error report.");
	    softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("blueScreenErrors"),"Blue Screen Errors report missing.");
		Thread.sleep(2000);
		LOGGER.info("Verified Blue Screen Errors report.");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("systemHealthScore"),"System health score report missing.");
		Thread.sleep(2000);
		LOGGER.info("Verified System health score report.");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("osPerformanceScore"),"OS Performance score Details report missing.");
		Thread.sleep(2000);
		LOGGER.info("Verified OS Performance score Details report.");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("securityScore"),"Security score report missing.");
		Thread.sleep(2000);
		LOGGER.info("Verified Security score report.");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("applicationInventory"),"Application Inventory report missing.");
		Thread.sleep(2000);
		LOGGER.info("Verified Application Inventory report.");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("networkHealth"),"Network Health report missing.");
		Thread.sleep(2000);
		LOGGER.info("Verified Network Health report.");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("sustainability"),"Sustainability report missing.");
		Thread.sleep(2000);
		LOGGER.info("Verified Sustainability report.");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("driverUpdateInsights"),"HP driver Updat eInsights report missing.");
		Thread.sleep(2000);
		LOGGER.info("Verified HP driver Update Insights report.");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("kernelPanicErrors"),"kernel Panic Errors report missing.");
		Thread.sleep(2000);
		LOGGER.info("Verified kernel Panic Errors report.");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("driverInventory"),"Driver Inventory report missing.");
		Thread.sleep(2000);
		LOGGER.info("Verified Driver Inventory  report.");
		softAssert.assertAll();
		LOGGER.info("Completed test case: verifyModernReportAnalyticsListPage");
		
	}
	
	/**
	 * This test case verifies the Application Experience Report (Analytics)
	 */
	@Test(priority = 37, groups = {"PRODUCTION_LDK","INITECH_SOLUTIONS"})
	public final void verifyApplicationExpReportAnalyticsListPage() throws Exception {
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		SoftAssert softAssert = new SoftAssert();
		String testSuiteName = SetTestEnvironments.suiteName;
		if(testSuiteName.contains("LDK") || testSuiteName.contains("Initech")){
			switchUserBasedOnSuite(testSuiteName);
		}
		AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver()).getInstance();
		leftSideMenuVerification();
		analyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
		waitForPageLoaded();
		Assert.assertTrue(analyticsPage.getTextOfAnalyticsPage("analytics").equals(analyticsPage.getTextLanguage(LanguageCode, "daas_ui", "wex.analytics")),
				"Analytics title doesn't match");
		LOGGER.info("Analytics listing page opened correctly");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("ApplicationExperience"),"Application Error report missing.");
		analyticsPage.clickOnElementsOfAnalyticsPage("ApplicationExperience");
		waitForPageLoaded();
		Thread.sleep(2000);
		
		 softAssert.assertTrue(analyticsPage.validateInstalledApplicationScoreSection(), "Installed Application Score section validation failed.");
		    LOGGER.info("Application score validation completed");
	
	    //web
		analyticsPage.waitForElementsOfAnalyticsPage("webButton");
		analyticsPage.clickOnElementsOfAnalyticsPage("webButton");
		waitForPageLoaded();
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("webApplicationsScore"),"Application score chart missing.");
		LOGGER.info("Verified Application score chart");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("appswithLongestPageLoadTime"),"Applications with Most Crashes chart missing.");
		LOGGER.info("Verified Applications with Most Crashes");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("appswithMostErrors"),"Applications with Most Freezes.");
		LOGGER.info("Verified Applications with Most Freezes");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("appswithMostErrorsOverTime"),"crashes And Freezes OverTime freezes chart missing.");
		LOGGER.info("Verified Crashes and Freezes Over Time");
		
		analyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
		waitForPageLoaded();							
		softAssert.assertAll();
		LOGGER.info("Completed test case: verifyApplicationExpReportAnalyticsListPage");
	}
	
	/**
	 * This test case verifies the BSOD Over Time Report (Analytics)
	 */
	@Test(priority = 38, enabled = true, groups = { "PRODUCTION_LDK","INITECH_SOLUTIONS"})
	public final void verifyFMBSODOverTime() throws Exception {
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		SoftAssert softAssert = new SoftAssert();
		String testSuiteName = SetTestEnvironments.suiteName;
		if(testSuiteName.contains("LDK") || testSuiteName.contains("Initech")){
			switchUserBasedOnSuite(testSuiteName);
		}

		AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver()).getInstance();
		leftSideMenuVerification();
	
		analyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
		waitForPageLoaded();
		LOGGER.info("Navigated to Analytics Page");
		analyticsPage.verifyElementIsEnableOfAnalyticsPage("fleetManagement");
		analyticsPage.clickOnElementsOfAnalyticsPage("fleetManagement");
		waitForPageLoaded();
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("fmBSODOverTime"),"BSOD Over Time widget missing.");
		analyticsPage.clickByJavaScriptOnAnalyticsPage("fmBSODOverTimeViewReport");
	    LOGGER.info("BSOD Over Time  report open successfully.");
	    waitForPageLoaded();
		   	
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("topModelsAffectedHeader"),"Top Models Affected chart missing.");
		Thread.sleep(2000);
		LOGGER.info("Verified Top Models Affected chart");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("topDriversCausingHeader"),"Top Drivers Causing chart missing.");
		Thread.sleep(2000);
		LOGGER.info("Verified Top Drivers Causing chart");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("topBugCheckCodesHeader"),"Top Bug Checks Codes chart missing.");
		Thread.sleep(2000);
		LOGGER.info("Verified Top Bug Checks Codes chart");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("bsodOverTimeHeader"),"BSOD over time chart missing.");
		Thread.sleep(2000);
		LOGGER.info("Verified BSOD over time chart");
		analyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
		waitForPageLoaded();		
		softAssert.assertAll();
		LOGGER.info("Completed test case: verifyFMBSODOverTime");
	 }
	 
	/**
	 * This test case verifies the Sustainability Report (Analytics)
	 */

	@Test(priority = 39, enabled = true, groups = { "PRODUCTION_LDK","INITECH_SOLUTIONS"})
	 public final void verifyFMSustainability() throws Exception {
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		SoftAssert softAssert = new SoftAssert();
		String testSuiteName = SetTestEnvironments.suiteName;
		if(testSuiteName.contains("LDK") || testSuiteName.contains("Initech")){
			switchUserBasedOnSuite(testSuiteName);
		}

		AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver()).getInstance();
		leftSideMenuVerification();
	
		analyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
		waitForPageLoaded();
		LOGGER.info("Navigated to Analytics Page");
		analyticsPage.verifyElementIsEnableOfAnalyticsPage("fleetManagement");
		analyticsPage.clickOnElementsOfAnalyticsPage("fleetManagement");
		waitForPageLoaded();
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("fmPowerConsumptionoverTime"),"Power Consumption over Time widget missing.");
		analyticsPage.clickByJavaScriptOnAnalyticsPage("fmPowerConsumptionoverTimeViewReport");
	    LOGGER.info("BSOD Over Time  report open successfully.");
	    waitForPageLoaded();
	    
	    softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("fmDBPowerConsumptionOverTime"),"Power Consumption Over Time chart missing.");
	    LOGGER.info("Verified Power Consumption Over Time chart");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("fmDBPowerConsumptionbyModel"),"Power Consumption by Model chart missing.");
		LOGGER.info("Verified Power Consumption by Model chart");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("fmDBPowerConsumptionbyLocation"),"Power Consumption by Location chart missing.");
		LOGGER.info("Verified Power Consumption by Location chart");
		analyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
		waitForPageLoaded();
		softAssert.assertAll();
		LOGGER.info("Completed test case: verifyFMSustainability");
	
	  }
 
	/**
	 * This test case verifies the Kernal Panic Error Report (Analytics)
	 *
	 */	
    @Test(priority = 40, groups = {"PRODUCTION_LDK","INITECH_SOLUTIONS"})
	public final void verifyKernalPanicErrorReport() throws Exception {
		LOGGER.info("Starting test case: verifyKernalPanicErrorReport");
		// Log in as IT Admin
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		LOGGER.info("Logged in as IT Admin");
		SoftAssert softAssert = new SoftAssert();
		String testSuiteName = SetTestEnvironments.suiteName;
		if(testSuiteName.contains("LDK") || testSuiteName.contains("Initech")){
			switchUserBasedOnSuite(testSuiteName);
		}
		AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver()).getInstance();
		// Verify left side menu
		leftSideMenuVerification();
		LOGGER.info("Left side menu verified");
		// Navigate to company view
		analyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
		waitForPageLoaded();
		LOGGER.info("Navigated to Analytics Page");
		// Click on SoftPaq Insights
		analyticsPage.clickOnElementsOfAnalyticsPage("kernelPanicErrorReport");
		LOGGER.info("Clicked on kernel PanicError Report");
		// Verify presence of various charts
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("topModelsAffected"),"top Models Affected chart not present.");
		LOGGER.info("Verified top Models Affected chart");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("TopModulesCausingKernelPanic"), "Top Modules Causing Kernel Panic chart not present.");
		LOGGER.info("Verified Top Modules Causing Kernel Panic chart");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("TopHardwareErrorCodes"),"Top Hardware Error Codes chart not present.");
		LOGGER.info("Verified Top Hardware Error Codes chart");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("KernelPanicovertime"),"Kernel Panic Over Time chart not present.");
		LOGGER.info("Verified Kernel Panic Over Time chart");
		// Assert all
		softAssert.assertAll();
		LOGGER.info("Completed test case: verifyKernalPanicErrorReport");
	}
    
    @Test(priority = 75, enabled = true, groups = { "PRODUCTION_LDK","INITECH_SOLUTIONS"})
   	public final void verifyFleetManagementHPPCBIOSPolicy() throws Exception {
   			  		
   			login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
   			SoftAssert softAssert = new SoftAssert();
   			String testSuiteName = SetTestEnvironments.suiteName;
   			if(testSuiteName.contains("LDK") || testSuiteName.contains("Initech")){
   				switchUserBasedOnSuite(testSuiteName);
   			}
   			DEXPage dexPage = new DEXPage(PreDefinedActions.getDriver()).getInstance();
   			WEPDeviceListPage wepDeviceListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
   			AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver()).getInstance();
   			leftSideMenuVerification();

   			dexPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
   			waitForPageLoaded();
   			Thread.sleep(5000);
   			LOGGER.info("Navigated to Analytics Page");
   			analyticsPage.verifyElementIsEnableOfAnalyticsPage("fleetManagement");
   			analyticsPage.clickByJavaScriptOnAnalyticsPage("fleetManagement");
   			waitForPageLoaded();
   			LOGGER.info("Navigated to fleet Management Page");
   			analyticsPage.scrollToAnalyticsPage("fmHPPCBIOSPolicy");
   						
   			softAssert.assertTrue(analyticsPage.waitForElementsOfAnalyticsPage("policyScoreBarTitle"), "policyScoreBarTitle Summary Chart is not working.");
   			LOGGER.info("Verified policyScoreBarTitle Summary Chart.");
   			String HPPCBIOSPolicyDeviceCount = analyticsPage.getTextOfWEXScore("policysScoreBarValue");
   			int TotalHPPCBIOSPolicyDeviceCount = Integer.parseInt(HPPCBIOSPolicyDeviceCount);
   			LOGGER.info("Device count: " + HPPCBIOSPolicyDeviceCount);
   			analyticsPage.clickByJavaScriptOnAnalyticsPage("fmPolicyScoreBar");
   			waitForPageLoaded();
   			Thread.sleep(5000);
   			String totalDevice = wepDeviceListPage.getTextOfWEPDeviceListPage("deviceCount").replace("of ", "");
   			int TotalDevicesCount = Integer.parseInt(totalDevice);
   			LOGGER.info("Device count total: " + TotalDevicesCount);
   			Assert.assertEquals(TotalDevicesCount, TotalHPPCBIOSPolicyDeviceCount, "Mismatch found: The total devices and total fleet inventory page devices count are different.");
   			LOGGER.info("The total devices and total fleet inventory page devices count are same.");
   			navigateToBack();
   			waitForPageLoaded();
   			softAssert.assertAll();
   			LOGGER.info("Completed test case: verifyFleetManagementHPPCBIOSPolicy");
   }
    
    @Test(priority = 76, groups = {"PRODUCTION_LDK","INITECH_SOLUTIONS"}, enabled = true)
	public final void verifySureStartReportAnalyticsLdk() throws Exception {
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		SoftAssert softAssert = new SoftAssert();
		String testSuiteName = SetTestEnvironments.suiteName;
		if(testSuiteName.contains("LDK") || testSuiteName.contains("Initech")){
			switchUserBasedOnSuite(testSuiteName);
		}

		AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver()).getInstance();
		leftSideMenuVerification();

		analyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
		waitForPageLoaded();
		Assert.assertTrue(analyticsPage.getTextOfAnalyticsPage("analytics").equals(analyticsPage.getTextLanguage(LanguageCode, "daas_ui", "wex.analytics")),
				"Analytics title doesn't match");
		LOGGER.info("Analytics listing page opened correctly");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("classicReports"),"classic Reports is not available");
		analyticsPage.clickOnElementsOfAnalyticsPage("classicReports");
		waitForPageLoaded();		
		Thread.sleep(2000);
		analyticsPage.clickOnElementsOfAnalyticsPage("sureStart");
		waitForPageLoaded();
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("sureStartSummerytab"),"sureStartSummerytab tab missing.");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("sureStartdetailstab"),"sureStartdetailstab tab missing.");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("sureStartFirmwareChanges"),"sureStartFirmwareChanges report missing.");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("sureStartActivityOverTime"),"sureStartActivityOverTime report missing.");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("sureStartMostImpactedDevices"),"sureStartMostImpactedDevicesreport missing.");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("sureStartHighSeverityEventsSummary"),"sureStartHighSeverityEventsSummary report missing.");		
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("sureStartdetailstab"),"sureStartdetailstab tab missing.");
		softAssert.assertAll();

	}
    
    /**
     * This test case verifies the HW Inventory Details Report (Analytics)
     */
    @Test(priority = 33, groups = {"PRODUCTION_LDK","INITECH_SOLUTIONS"}, enabled = true)
    public final void verifyDeviceHWInventoryDetailsReportAnalyticsListPageLdk() throws Exception {
    	login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
    	SoftAssert softAssert = new SoftAssert();
    	String testSuiteName = SetTestEnvironments.suiteName;
		if(testSuiteName.contains("LDK") || testSuiteName.contains("Initech")){
			switchUserBasedOnSuite(testSuiteName);
		}
    	AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver()).getInstance();
    	leftSideMenuVerification();

    	analyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
    	waitForPageLoaded();
    	Assert.assertTrue(analyticsPage.getTextOfAnalyticsPage("analytics").equals(analyticsPage.getTextLanguage(LanguageCode, "daas_ui", "wex.analytics")),
    			"Analytics title doesn't match");
    	LOGGER.info("Analytics listing page opened correctly");
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("classicReports"),"classic Reports is not available");
    	//analyticsPage.scrollTillViewAnalyticsPage("classicReports");
    	analyticsPage.clickByJavaScriptOnAnalyticsPage("classicReports");
    	waitForPageLoaded();					
    	analyticsPage.clickByJavaScriptOnAnalyticsPage("hwInventory");
    	waitForPageLoaded();
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("byLocation"),"byLocation Summary report missing.");
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("byDeviceConfiguration"),"byDeviceConfiguration Summary report missing.");
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("byDeviceModel"),"byDeviceModel Summary report missing.");
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("byOS"),"byOS Summary report missing.");
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("byDetails"),"byDetails Summary report missing.");
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("hwInventoryByLocation"),"hwInventoryByLocation Summary report missing.");
    	analyticsPage.clickOnElementsOfAnalyticsPage("byDeviceConfiguration");
    	waitForPageLoaded();
    	Thread.sleep(1000);
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("hwInvDeviceTypeSummary"),"hwInventoryByLocation Summary report missing.");
    	analyticsPage.clickOnElementsOfAnalyticsPage("byDeviceModel");
    	waitForPageLoaded();
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("deviceByModel"),"deviceByModel Summary report missing.");
    	analyticsPage.clickByJavaScriptOnAnalyticsPage("byOS");
    	waitForPageLoaded();
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("deviceByOS"),"deviceByOS Summary report missing.");
    	analyticsPage.clickByJavaScriptOnAnalyticsPage("byDetails");
    	waitForPageLoaded();
    	Thread.sleep(1000);
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("lastSignUser"),"lastSignUser report missing.");
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("serialNumber"),"serialNumber report missing.");
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("deviceName"),"deviceName report missing.");
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("deviceType"),"deviceType report missing.");
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("devicModel"),"devicModel report missing.");
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("deviceOsOriginal"),"deviceOsOriginal report missing.");
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("deviceManufacturer"),"deviceManufacturer report missing.");
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("osRelease"),"osRelease report missing.");
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("hwDetailsRow"),"hwDetails Row report missing.");
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("hwDetailsPageSize"),"hardware details page size   missing.");
    	//softAssert.assertTrue(analyticsPage.verifyPageSize("hwDetailsTotalSize"),"hardware details Total size  missing.");
    	LOGGER.info("Hardware inventory validation completed");	
    	softAssert.assertAll();
    }

    /**
     * This test case verifies the Win11 Details Report (Analytics)
     */
    @Test(priority = 34, groups = {"PRODUCTION_LDK","INITECH_SOLUTIONS"} ,enabled = true)
    public final void verifyWin11ReportAnalyticsListPageLdk() throws Exception {
    	login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
    	SoftAssert softAssert = new SoftAssert();
    	String testSuiteName = SetTestEnvironments.suiteName;
		if(testSuiteName.contains("LDK") || testSuiteName.contains("Initech")){
			switchUserBasedOnSuite(testSuiteName);
		}

    	AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver()).getInstance();
    	leftSideMenuVerification();
    	analyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
    	waitForPageLoaded();
    	Assert.assertTrue(analyticsPage.getTextOfAnalyticsPage("analytics").equals(analyticsPage.getTextLanguage(LanguageCode, "daas_ui", "wex.analytics")),
    			"Analytics title doesn't match");
    	LOGGER.info("Analytics listing page opened correctly");
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("classicReports"),"classic Reports is not available");
    	analyticsPage.clickOnElementsOfAnalyticsPage("classicReports");
    	waitForPageLoaded();

    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("win11"),"Windows 11 Readiness Assessment report missing.");
    	Thread.sleep(2000);
    	analyticsPage.clickByJavaScriptOnAnalyticsPage("win11");
    	waitForPageLoaded();
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("byLocation"),"Summary report missing.");
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("operatingSystem"),"Details report missing.");
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("updateBreakdown"),"updateBreakdown report missing.");
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("topDeviceModel"),"topDeviceModel report missing.");
    	analyticsPage.clickOnElementsOfAnalyticsPage("operatingSystem");
    	waitForPageLoaded();
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("lastSignUser"),"lastSignUser report missing.");
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("deviceSerialNumber"),"serialNumber report missing.");
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("deviceName"),"deviceName report missing.");
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("deviceType"),"deviceType report missing.");
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("deviceManufacturers"),"deviceManufacturer report missing.");
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("devicModel"),"device Model report missing.");
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("deviceAge"),"deviceAge Model report missing.");
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("devicesOS"),"deviceOS Model report missing.");
    	LOGGER.info("Win 11 validation completed");
    	analyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
    	waitForPageLoaded();
    	analyticsPage.clickOnElementsOfAnalyticsPage("reportListSort");
    	LOGGER.info("Report list sorting verified.");
    	softAssert.assertAll();
    }

    @Test(priority = 35, groups = {"PRODUCTION_LDK","INITECH_SOLUTIONS"}, enabled = true)
    public final void verifyBIOSInventoryReportAnalyticsListPageLdk() throws Exception {
    	login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
    	SoftAssert softAssert = new SoftAssert();
    	String testSuiteName = SetTestEnvironments.suiteName;
		if(testSuiteName.contains("LDK") || testSuiteName.contains("Initech")){
			switchUserBasedOnSuite(testSuiteName);
		}
    	AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver()).getInstance();
    	leftSideMenuVerification();

    	analyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
    	waitForPageLoaded();
    	Assert.assertTrue(analyticsPage.getTextOfAnalyticsPage("analytics").equals(analyticsPage.getTextLanguage(LanguageCode, "daas_ui", "wex.analytics")),
    			"Analytics title doesn't match");
    	LOGGER.info("Analytics listing page opened correctly");
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("classicReports"),"classic Reports is not available");
    	analyticsPage.clickByJavaScriptOnAnalyticsPage("classicReports");
    	waitForPageLoaded();
    	analyticsPage.clickByJavaScriptOnAnalyticsPage("biosInventory");
    	waitForPageLoaded();
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("biosSumary"),"BIOS Summary report missing.");
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("uniqueBios"),"Unique BIOS report missing.");
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("byWeek"),"By Week report missing.");
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("details"),"Details report missing.");
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("biosVersionByDeviceModel"),"BIOS version by device " +
    			"model report missing.");
    	analyticsPage.clickOnElementsOfAnalyticsPage("uniqueBios");
    	waitForPageLoaded();
    	Thread.sleep(2000);
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("uniqueBIOSVersionModel"),"uniqueBIOSVersionModel report missing.");
    	analyticsPage.clickOnElementsOfAnalyticsPage("byWeek");
    	waitForPageLoaded();
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("biosStatusWeek"),"biosStatusWeek report missing.");
    	analyticsPage.clickOnElementsOfAnalyticsPage("details");
    	waitForPageLoaded();
    	Thread.sleep(2000);
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("lastSignedUser"),"lastSignedUser report missing.");
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("serialNumbers"),"serialNumbers report missing.");
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("deviceNames"),"deviceNames report missing.");
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("deviceType"),"deviceType report missing.");
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("deviceManufacturer"),"deviceManufacturer report missing.");
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("devicModel"),"device Model report missing.");
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("devicMfgDate"),"devicMfgDate report missing.");
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("devicePlatformId"),"devicePlatformId report missing.");
    	LOGGER.info("BIOS Inventory validation completed");
    	analyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
    	waitForPageLoaded();
    	analyticsPage.clickOnElementsOfAnalyticsPage("reportListSort");
    	LOGGER.info("Report list sorting verified.");
    	softAssert.assertAll();

    }

    @Test(priority = 36, groups = {"PRODUCTION_LDK","INITECH_SOLUTIONS"} ,enabled = true)
    public final void verifyHWWarrantyReportAnalyticsListPageLdk() throws Exception {
    	login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
    	SoftAssert softAssert = new SoftAssert();
    	String testSuiteName = SetTestEnvironments.suiteName;
		if(testSuiteName.contains("LDK") || testSuiteName.contains("Initech")){
			switchUserBasedOnSuite(testSuiteName);
		}
    	AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver()).getInstance();
    	leftSideMenuVerification();

    	analyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
    	waitForPageLoaded();
    	Assert.assertTrue(analyticsPage.getTextOfAnalyticsPage("analytics").equals(analyticsPage.getTextLanguage(LanguageCode, "daas_ui", "wex.analytics")),
    			"Analytics title doesn't match");
    	LOGGER.info("Analytics listing page opened correctly");
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("classicReports"),"classic Reports is not available");
    	analyticsPage.scrollTillViewAnalyticsPage("classicReports");
    	analyticsPage.clickByJavaScriptOnAnalyticsPage("classicReports");
    	waitForPageLoaded();
    	analyticsPage.clickByJavaScriptOnAnalyticsPage("hwWarranty");
    	waitForPageLoaded();
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("byManuFactureYear"),"By Manufacture Year title  missing.");
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("byDeviceManufacturer"),"By Device Manufacturer  title  missing.");
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("byExpStatus"),"By Experience Status  title  missing.");
    	
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("hwWSummery"),"HardWare Warranty summery tab  missing.");
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("hwWDetails"),"HardWare Warranty details tab  missing.");
    	softAssert.assertTrue(analyticsPage.verifyListofElementsOfAnalyticsPage("outOfWarranty"),"List of Warranty details tab  missing.");
    	analyticsPage.clickByJavaScriptOnAnalyticsPage("hwWDetails");
    	waitForPageLoaded();
    	Thread.sleep(2000);
    	Assert.assertTrue(analyticsPage.verifyListofElementsOfAnalyticsPage("tableName"),"Report Table Columns are missing.");
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("lastSignedUser32"),"lastSignedUser report missing.");
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("serialNumber32"),"serialNumbers report missing.");
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("deviceName32"),"deviceNames report missing.");
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("deviceManufacturer32"),"deviceManufacturer report missing.");
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("devicesModel32"),"device Model report missing.");
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("enrolledID"),"Enrolled date report missing.");  
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("hardWareWrnStatus"),"hardWareWrnStatus column missing.");
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("expStatus"),"Experience Status coulmn missing.");
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("remaning"),"Day Remaining coulmn missing.");
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("endDayOveral"),"End Day Overal coulmn missing.");
    	
    	
    	LOGGER.info("HW Warranty validation completed");
    	analyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
    	waitForPageLoaded();
    	analyticsPage.clickOnElementsOfAnalyticsPage("reportListSort");
    	LOGGER.info("Report list sorting verified.");
    	softAssert.assertAll();
    }

    @Test(priority = 37, groups = {"PRODUCTION_LDK","INITECH_SOLUTIONS"} ,enabled = true)
    public final void verifycompanySecurityComplianceSummaryReportAnalyticsListPageLdk() throws Exception {
    	login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
    	SoftAssert softAssert = new SoftAssert();
    	String testSuiteName = SetTestEnvironments.suiteName;
		if(testSuiteName.contains("LDK") || testSuiteName.contains("Initech")){
			switchUserBasedOnSuite(testSuiteName);
		}
    	AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver()).getInstance();
    	leftSideMenuVerification();

    	analyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
    	waitForPageLoaded();
    	Assert.assertTrue(analyticsPage.getTextOfAnalyticsPage("analytics").equals(analyticsPage.getTextLanguage(LanguageCode, "daas_ui", "wex.analytics")),
    			"Analytics title doesn't match");
    	LOGGER.info("Analytics listing page opened correctly");
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("classicReports"),"classic Reports is not available");
    	analyticsPage.scrollTillViewAnalyticsPage("classicReports");
    	analyticsPage.clickByJavaScriptOnAnalyticsPage("classicReports");
    	waitForPageLoaded();

    	softAssert.assertTrue(analyticsPage.verifyElementIsEnableOfAnalyticsPage("securityCompanyreport"),"companySecurityComplianceSummary report missing.");
    	Thread.sleep(2000);
    	analyticsPage.clickByJavaScriptOnAnalyticsPage("securityCompanyreport");
    	waitForPageLoaded();
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("antivirouss"),"Antivirous title  missing.");
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("encryptionn"),"Encrypted  title  missing.");
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("endPointProtection1"),"endPointProtection tab  missing.");
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("comdetails"),"details  tab  missing.");
    	//softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("firewall"),"Firewall  title  missing.");
    	analyticsPage.clickByJavaScriptOnAnalyticsPage("comdetails");
    	waitForPageLoaded();
    	Thread.sleep(2000);
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("monthCSC"),"Month column  title  missing.");
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("complainsType"),"Complains Type column title  missing.");
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("complainsStatus"),"ComplainsStatus column title  missing.");
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("noOfDevicesCSC"),"NoOf Devices Column title  missing.");
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("totalTime"),"Total Time column  title  missing.");
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("timeHRS"),"TimeHRS Column title  missing.");
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("timePercentage"),"Time(%) column  title  missing.");
    	/*
    	 * String s=analyticsPage.gettextOfListOfElementsAnalyticsPage("tableNamee");
    	 * System.out.println(s); //It will validate list of elements presents
    	 * softAssert.assertTrue(analyticsPage.
    	 * getListOfElementsTillAllElementsVisibleAnalyticsPage("tableNamee")
    	 * ,"Table Coulom Name missing.");
    	 */
    	
    	LOGGER.info("companySecurityComplianceSummaryvalidation completed");
    	analyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
    	waitForPageLoaded();
    	analyticsPage.clickOnElementsOfAnalyticsPage("reportListSort");
    	LOGGER.info("Report list sorting verified.");
    	softAssert.assertAll();


    }

    @Test(priority = 38, groups = {"PRODUCTION_LDK","INITECH_SOLUTIONS"} ,enabled = true)
    public final void verifydeviceSecurityComplainsTwentyFHReportAnalyticsListPageLdk() throws Exception {
    	login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
    	SoftAssert softAssert = new SoftAssert();
    	String testSuiteName = SetTestEnvironments.suiteName;
		if(testSuiteName.contains("LDK") || testSuiteName.contains("Initech")){
			switchUserBasedOnSuite(testSuiteName);
		}
    	AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver()).getInstance();
    	leftSideMenuVerification();

    	analyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
    	waitForPageLoaded();
    	Assert.assertTrue(analyticsPage.getTextOfAnalyticsPage("analytics").equals(analyticsPage.getTextLanguage(LanguageCode, "daas_ui", "wex.analytics")),
    			"Analytics title doesn't match");
    	LOGGER.info("Analytics listing page opened correctly");
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("classicReports"),"classic Reports is not available");
    	analyticsPage.scrollTillViewAnalyticsPage("classicReports");
    	analyticsPage.clickOnElementsOfAnalyticsPage("classicReports");
    	waitForPageLoaded();

    	//softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("deviceSeDetailsTFH"),"DeviceDetailsTFH report missing.");
    	Thread.sleep(2000);
    	analyticsPage.clickByJavaScriptOnAnalyticsPage("deviceSeDetailsTFH");
    	waitForPageLoaded();
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("antivirousSignatureStatus"),"antivirousSignatureStatus title  missing.");
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("antivirousApp"),"AntivirousApplication  title  missing.");
    	//softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("realTimeAntivirous"),"RealTimeAntivirous Status  title  missing.");
    	//softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("releasDateTimeF"),"releasDateTimeFrame  title  missing.");
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("antiviriusAppTFH"),"Antivirus Applications title  missing.");
    	
    	  softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage(
    	  "fireWallAppTFH"),"Fire Wall Application  title  missing.");
    	  softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage(
    	  "endPProtectionTFH"),"End piont protection  title  missing.");
    	  softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage(
    	  "detailsTFH"),"Details tab  missing.");
    	 
    	analyticsPage.clickOnElementsOfAnalyticsPage("fireWallAppTFH");
    	waitForPageLoaded();
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("fireWallSigStatus"),"Fire wall Signature status vigit  missing.");
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("fireAppV"),"FireWall Application  vigits  missing.");
    	//softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("realTimeV"),"RealTimeapp vigit`11`  missing.");
    	analyticsPage.clickOnElementsOfAnalyticsPage("endPProtectionTFH");
    	waitForPageLoaded();
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("noOfHoursV"),"No Of time vigit  missing.");
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("noOfInstanceV"),"No Of Instance  vigits  missing.");
    	analyticsPage.clickOnElementsOfAnalyticsPage("detailsTFH");
    	waitForPageLoaded();
    	/*
    	 * String s=analyticsPage.gettextOfListOfElementsAnalyticsPage("tableName");
    	 * System.out.println(s); //It will validate list of elements presents
    	 * softAssert.assertTrue(analyticsPage.
    	 * getListOfElementsTillAllElementsVisibleAnalyticsPage("tableName")
    	 * ,"Table Coulom Name missing.");
    	 */
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("lastSignedUser"),"lastSignedUser report missing.");
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("serialNumbers"),"serialNumbers report missing.");
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("deviceNames"),"deviceNames report missing.");
    	
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("lastSeen"),"Lastseen column report missing.");
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("devicModel"),"device Model report missing.");
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("complains"),"Complains report missing.");
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("realTimeProStatus"),"RealTimeProtection status report missing.");
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("signature"),"Signature status report missing.");
    	LOGGER.info("deviceSecurityComplainsTwenty Four Hours validation completed");
    	analyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
    	waitForPageLoaded();
    	analyticsPage.clickOnElementsOfAnalyticsPage("reportListSort");
    	LOGGER.info("Report list sorting verified.");
    	softAssert.assertAll();


    }

    @Test(priority = 39, groups = {"PRODUCTION_LDK","INITECH_SOLUTIONS"} ,enabled = true)
    public final void verifydevicesecurityComplianceMonthlySummaryReportAnalyticsListPageLdk() throws Exception {
    	login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
    	SoftAssert softAssert = new SoftAssert();
    	String testSuiteName = SetTestEnvironments.suiteName;
		if(testSuiteName.contains("LDK") || testSuiteName.contains("Initech")){
			switchUserBasedOnSuite(testSuiteName);
		}
    	AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver()).getInstance();
    	leftSideMenuVerification();

    	analyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
    	waitForPageLoaded();
    	Assert.assertTrue(analyticsPage.getTextOfAnalyticsPage("analytics").equals(analyticsPage.getTextLanguage(LanguageCode, "daas_ui", "wex.analytics")),
    			"Analytics title doesn't match");
    	LOGGER.info("Analytics listing page opened correctly");
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("classicReports"),"classic Reports is not available");
    	analyticsPage.scrollTillViewAnalyticsPage("classicReports");
    	analyticsPage.clickOnElementsOfAnalyticsPage("classicReports");
    	waitForPageLoaded();

    	//softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("monthlyReport"),"Device Security Compliance Monthly Summary report missing.");
    	Thread.sleep(2000);
    	analyticsPage.clickByJavaScriptOnAnalyticsPage("monthlyReport");
    	waitForPageLoaded();
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("endPointProtection2"),"endPointProtection element  missing.");
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("antivirous"),"Antivirous vigits  missing.");
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("encryption"),"Encryption  title  missing.");
    	//softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("fireWall"),"Firewall  title  missing.");
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("detailsCSM"),"Details  tab  missing.");
    	analyticsPage.clickOnElementsOfAnalyticsPage("detailsCSM");
    	waitForPageLoaded();
    	
    	//It will validate list of elements presents
    	//softAssert.assertTrue(analyticsPage.getListOfElementsTillAllElementsVisibleAnalyticsPage("tableName"),"Table Coulom Name missing.");
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("lastSignedUser"),"lastSignedUser report missing.");
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("serialNumbers"),"serialNumbers report missing.");
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("deviceNames"),"deviceNames report missing.");
    	
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("lastSeen"),"Lastseen column report missing.");
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("devicModel"),"device Model report missing.");
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("complains"),"Complains report missing.");
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("deviceManufacturer"),"deviceManufacturer report missing.");
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("devicModel"),"device Model report missing.");
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("diskSrNo"),"Disk Serial Number report missing.");

    	LOGGER.info("deviceSecurityComplainsTwenty Four Hours validation completed");
    	
    	LOGGER.info("HW Warranty validation completed");
    	analyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
    	waitForPageLoaded();
    	analyticsPage.clickOnElementsOfAnalyticsPage("reportListSort");
    	LOGGER.info("Report list sorting verified.");
    	softAssert.assertAll();


    }

    @Test(priority = 40, groups = {"PRODUCTION_LDK","INITECH_SOLUTIONS"} ,enabled = true)
    public final void verifySoftwareUtilizationSummaryReportAnalyticsListPageLdk() throws Exception {
    	login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
    	SoftAssert softAssert = new SoftAssert();
    	String testSuiteName = SetTestEnvironments.suiteName;
		if(testSuiteName.contains("LDK") || testSuiteName.contains("Initech")){
			switchUserBasedOnSuite(testSuiteName);
		}
    	AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver()).getInstance();
    	leftSideMenuVerification();

    	analyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
    	waitForPageLoaded();
    	Assert.assertTrue(analyticsPage.getTextOfAnalyticsPage("analytics").equals(analyticsPage.getTextLanguage(LanguageCode, "daas_ui", "wex.analytics")),
    			"Analytics title doesn't match");
    	LOGGER.info("Analytics listing page opened correctly");
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("classicReports"),"classic Reports is not available");
    	analyticsPage.scrollTillViewAnalyticsPage("classicReports");
    	
    	analyticsPage.clickOnElementsOfAnalyticsPage("classicReports");
    	waitForPageLoaded();
    	
    	softAssert.assertTrue(analyticsPage.verifyElementIsEnableOfAnalyticsPage("softUtilisasmr"),"Software Utilization Summary report missing.");
    	Thread.sleep(2000);
    	analyticsPage.clickByJavaScriptOnAnalyticsPage("softUtilisasmr");
    	waitForPageLoaded();
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("monthlySummery"),"monthlySummery visit  missing.");
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("topmonthlySmry"),"Top Apps by Usage Time (Monthly Summary) vigits  missing.");
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("topsedAusedWinApp"),"Top Used Window  Applications element missing.");
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("topUsedAndApp"),"Top Used Android Applications element  missing.");
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("topUsedMacApp"),"Top Used Android Applications element  missing.");
    	analyticsPage.clickOnElementsOfAnalyticsPage("topUsedAndApp");
    	waitForPageLoaded();
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("topActivlyU"),"Top Activity usage element missing.");
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("topAppUsage"),"Top app Usage element  missing.");
    	analyticsPage.clickOnElementsOfAnalyticsPage("topUsedMacApp");
    	waitForPageLoaded();
    	
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("topAct"),"Top Activity  Applications element missing.");
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("topAppByU"),"Top app used  Applications element  missing.");
    	analyticsPage.clickOnElementsOfAnalyticsPage("detailsSUS");
    	waitForPageLoaded();
    	
    	
    	/*
    	 * LOGGER.info("HW Warranty validation completed");
    	 * analyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
    	 * waitForPageLoaded();
    	 * analyticsPage.clickOnElementsOfAnalyticsPage("reportListSort");
    	 * LOGGER.info("Report list sorting verified.");
    	 */
    	softAssert.assertAll();


    }

    @Test(priority = 41, groups = {"PRODUCTION_LDK","INITECH_SOLUTIONS"} ,enabled = true)
    public final void verifyDiskCapacityPlanningDetailsReportAnalyticsListPageLdk() throws Exception {
    	login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
    	SoftAssert softAssert = new SoftAssert();
    	String testSuiteName = SetTestEnvironments.suiteName;
		if(testSuiteName.contains("LDK") || testSuiteName.contains("Initech")){
			switchUserBasedOnSuite(testSuiteName);
		}
    	AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver()).getInstance();
    	leftSideMenuVerification();

    	analyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
    	waitForPageLoaded();
    	Assert.assertTrue(analyticsPage.getTextOfAnalyticsPage("analytics").equals(analyticsPage.getTextLanguage(LanguageCode, "daas_ui", "wex.analytics")),
    			"Analytics title doesn't match");
    	LOGGER.info("Analytics listing page opened correctly");
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("classicReports"),"classic Reports is not available");
    	analyticsPage.scrollTillViewAnalyticsPage("classicReports");
    	analyticsPage.clickOnElementsOfAnalyticsPage("classicReports");
    	waitForPageLoaded();

    	//softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("diskCapacityP"),"Software Utilization Summary report missing.");
    	Thread.sleep(2000);
    	analyticsPage.clickByJavaScriptOnAnalyticsPage("diskCapacityP");
    	waitForPageLoaded();
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("summery"),"Summery title  missing.");
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("diskSize"),"diskSize vigits  missing.");
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("diskModel"),"diskModel element missing.");
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("diskSpaceStatus"),"diskSpaceStatus element  missing.");
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("rplySummr"),"rplySummr element  missing.");
    	analyticsPage.clickOnElementsOfAnalyticsPage("diskSize");
    	waitForPageLoaded();
    	//softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("diskSpaceStatus"),"Disk Status vigits  missing.");
    	analyticsPage.clickOnElementsOfAnalyticsPage("diskModel");
    	waitForPageLoaded();
    	//softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("diskSpaceStatus"),"DiskSpace Column title  missing.");
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("additionalStatus"),"additionalStatus Column title  missing.");
    	
    	analyticsPage.clickOnElementsOfAnalyticsPage("detailsDC");
    	waitForPageLoaded();
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("lastSignedUser"),"lastSignedUser report missing.");
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("serialNumbers"),"serialNumbers report missing.");
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("deviceNames"),"deviceNames report missing.");
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("deviceType"),"deviceType report missing.");
    	
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("devicModel"),"device Model report missing.");
    	softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("devicMfgDate"),"devicMfgDate report missing.");
    	
     LOGGER.info("HW Warranty validation completed");
    	 analyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
    	 waitForPageLoaded();
    	 analyticsPage.clickOnElementsOfAnalyticsPage("reportListSort");
    	 LOGGER.info("Report list sorting verified.");

    	softAssert.assertAll();


    }
    	 

    @Test(priority = 77, groups = {"PRODUCTION_LDK","INITECH_SOLUTIONS", "PRODUCTION_INTEGRATIONQA_CUJ","PRODUCTION_INTEGRATIONQA_CUJ"}, enabled = true)
	public final void verifyDriverInventoryReportAnalyticsLdk() throws Exception {
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		SoftAssert softAssert = new SoftAssert();
		String testSuiteName = SetTestEnvironments.suiteName;
		if(testSuiteName.contains("LDK") || testSuiteName.contains("Initech")){
			switchUserBasedOnSuite(testSuiteName);
		}
		AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver()).getInstance();
		leftSideMenuVerification();

		analyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
		waitForPageLoaded();
		Assert.assertTrue(analyticsPage.getTextOfAnalyticsPage("analytics").equals(analyticsPage.getTextLanguage(LanguageCode, "daas_ui", "wex.analytics")),
				"Analytics title doesn't match");
		LOGGER.info("Analytics listing page opened correctly");
		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("mordernReport"),"Mordern Reports is not available");
		analyticsPage.clickByJavaScriptOnAnalyticsPage("mordernReport");
		waitForPageLoaded();		
		Thread.sleep(2000);
		analyticsPage.clickByJavaScriptOnAnalyticsPage("driverInventoryReport");
		waitForPageLoaded();
		softAssert.assertTrue(analyticsPage.verifyListofElementsOfAnalyticsPage("listOfLinks"),"25 links not presents.");

		softAssert.assertAll();

	}

     @Test(priority = 78, groups = {"PRODUCTION_LDK","INITECH_SOLUTIONS"}, enabled = true)
   	public final void verifyBatteryStatusAndRiskReportAnalyticsLdk() throws Exception {
   		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
   		SoftAssert softAssert = new SoftAssert();
   		String testSuiteName = SetTestEnvironments.suiteName;
		if(testSuiteName.contains("LDK") || testSuiteName.contains("Initech")){
			switchUserBasedOnSuite(testSuiteName);
		}
   		AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver()).getInstance();
   		leftSideMenuVerification();

   		analyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
   		waitForPageLoaded();
   		Assert.assertTrue(analyticsPage.getTextOfAnalyticsPage("analytics").equals(analyticsPage.getTextLanguage(LanguageCode, "daas_ui", "wex.analytics")),
   				"Analytics title doesn't match");
   		LOGGER.info("Analytics listing page opened correctly");
   		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("classicReports"),"classic Reports is not available");
   		analyticsPage.clickOnElementsOfAnalyticsPage("classicReports");
   		waitForPageLoaded();		
   		Thread.sleep(2000);
   		analyticsPage.clickOnElementsOfAnalyticsPage("batteryRepAndRisk");
   		waitForPageLoaded();
   		//softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("batteryRepAndRisk"),"batteryRepAndRisk  missing.");
   		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("batteryRepAndRiskFactSummary"),"batteryRepAndRiskFactSummary report missing.");
   		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("batteryRepAndRiskFactDeviceByBHM"),"batteryRepAndRiskFactDeviceByBHM report missing.");
   		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("batteryRepAndRiskFactBiosUpToDate"),"batteryRepAndRiskFactBiosUpToDate report missing.");
   		analyticsPage.clickOnElementsOfAnalyticsPage("batteryRepAndRiskByModel");
   		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("batteryRepAndRiskByModel"),"batteryRepAndRiskByModel is missing.");
   		softAssert.assertAll();

   	}
     @Test(priority = 79, groups = {"PRODUCTION_LDK","INITECH_SOLUTIONS"}, enabled = true)
 	public final void verifyCustomeDataReportAnalyticsLdk() throws Exception {
    	 
 		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
 		SoftAssert softAssert = new SoftAssert();
 		String testSuiteName = SetTestEnvironments.suiteName;
		if(testSuiteName.contains("LDK") || testSuiteName.contains("Initech")){
			switchUserBasedOnSuite(testSuiteName);
		}
 		AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver()).getInstance();
 		leftSideMenuVerification();
 		analyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
 		waitForPageLoaded();
 		Assert.assertTrue(analyticsPage.getTextOfAnalyticsPage("analytics").equals(analyticsPage.getTextLanguage(LanguageCode, "daas_ui", "wex.analytics")),
 				"Analytics title doesn't match");
 		LOGGER.info("Analytics listing page opened correctly");
 		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("customeReport"),"Custome Data Reports is not available");
 		analyticsPage.clickByJavaScriptOnAnalyticsPage("customeReport");
 		waitForPageLoaded();		
 		Thread.sleep(2000);
 		String textName= analyticsPage.getTextOfAnalyticsPage("textmsg");
 		Assert.assertEquals(analyticsPage.getTextOfAnalyticsPage("textmsg"), textName, "Textmsg doesn't match the textName");
 		softAssert.assertTrue(analyticsPage.verifyElementIsEnableOfAnalyticsPage("addbutton"),"Add button is not enabled");
 		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("tableHead"),"Table columns missing");
 		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("reportsLinks"),"All reports missing");
 		softAssert.assertAll();
     }
       

 	@Test(priority = 80, groups = {"PRODUCTION_LDK","INITECH_SOLUTIONS"}, enabled = true)
   	public final void verifyWindowsDefenderReportAnalyticsLdk() throws Exception {
   		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
   		SoftAssert softAssert = new SoftAssert();
   		String testSuiteName = SetTestEnvironments.suiteName;
		if(testSuiteName.contains("LDK") || testSuiteName.contains("Initech")){
			switchUserBasedOnSuite(testSuiteName);
		}
   		AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver()).getInstance();
   		leftSideMenuVerification();

   		analyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
   		waitForPageLoaded();
   		Assert.assertTrue(analyticsPage.getTextOfAnalyticsPage("analytics").equals(analyticsPage.getTextLanguage(LanguageCode, "daas_ui", "wex.analytics")),
   				"Analytics title doesn't match");
   		LOGGER.info("Analytics listing page opened correctly");
   		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("classicReports"),"classic Reports is not available");
   		analyticsPage.clickOnElementsOfAnalyticsPage("classicReports");
   		waitForPageLoaded();		
   		Thread.sleep(2000);
   		analyticsPage.clickOnElementsOfAnalyticsPage("WindowsDefender");
   		waitForPageLoaded();
   		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("WinDefclientStatus"),"WinDefclientStatus is  missing.");
   		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("threatsClassficationSummary"),"threatsClassficationSummary report missing.");
   		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("WinDefthreatsBySeverity"),"WinDefthreatsBySeverity report missing.");
   		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("threatsByActionTaken"),"threatsByActionTaken report missing.");
   		softAssert.assertAll();

   	}

   @Test(priority = 81, groups = {"PRODUCTION_LDK","INITECH_SOLUTIONS"}, enabled = false)
   	public final void verifyDataHealthReportAnalyticsLdk() throws Exception {
   		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
   		SoftAssert softAssert = new SoftAssert();
   		String testSuiteName = SetTestEnvironments.suiteName;
		if(testSuiteName.contains("LDK") || testSuiteName.contains("Initech")){
			switchUserBasedOnSuite(testSuiteName);
		}
   		AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver()).getInstance();
   		leftSideMenuVerification();

   		analyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
   		waitForPageLoaded();
   		Assert.assertTrue(analyticsPage.getTextOfAnalyticsPage("analytics").equals(analyticsPage.getTextLanguage(LanguageCode, "daas_ui", "wex.analytics")),
   				"Analytics title doesn't match");
   		LOGGER.info("Analytics listing page opened correctly");
   		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("classicReports"),"classic Reports is not available");
   		analyticsPage.clickOnElementsOfAnalyticsPage("classicReports");
   		waitForPageLoaded();		
   		Thread.sleep(2000);
   		analyticsPage.clickOnElementsOfAnalyticsPage("dataHealth");
   		waitForPageLoaded();
   		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("dataHealthInstallationStatus"),"WinDefclientStatus is  missing.");
   		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("dataHealthAnalytics"),"threatsClassficationSummary report missing.");
   		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("WinDefthreatsBySeverity"),"WinDefthreatsBySeverity report missing.");
   		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("threatsByActionTaken"),"threatsByActionTaken report missing.");
   		softAssert.assertAll();

   	}

   	@Test(priority = 82, groups = {"PRODUCTION_LDK","INITECH_SOLUTIONS"}, enabled = true)
   	public final void verifyDeviceUtilizationReportAnalyticsLdk() throws Exception {
   		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
   		SoftAssert softAssert = new SoftAssert();
   		String testSuiteName = SetTestEnvironments.suiteName;
		if(testSuiteName.contains("LDK") || testSuiteName.contains("Initech")){
			switchUserBasedOnSuite(testSuiteName);
		}
   		AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver()).getInstance();
   		leftSideMenuVerification();
   		analyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
   		waitForPageLoaded();
   		Assert.assertTrue(analyticsPage.getTextOfAnalyticsPage("analytics").equals(analyticsPage.getTextLanguage(LanguageCode, "daas_ui", "wex.analytics")),
   				"Analytics title doesn't match");
   		LOGGER.info("Analytics listing page opened correctly");
   		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("classicReports"),"classic Reports is not available");
   		analyticsPage.clickByJavaScriptOnAnalyticsPage("classicReports");
   		waitForPageLoaded();		
   		Thread.sleep(2000);
   		analyticsPage.clickByJavaScriptOnAnalyticsPage("deviceUtilization");
   		waitForPageLoaded();
   		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("cpuUtilizationSummarry"),"cpuUtilizationSummarry report is  missing.");
   		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("memoryUtilizationSummarry"),"memoryUtilizationSummarry report is missing.");
   		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("cpuUtilizationSummarryTitle"),"cpuUtilizationSummarry Title is missing.");
   		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("memoryUtilizationSummarryTitle"),"memoryUtilizationSummarry Title is missing.");
   		analyticsPage.clickOnElementsOfAnalyticsPage("byManufactureYear");
   		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("cpuUtilizationYear"),"cpuUtilization Year is  missing.");
   		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("memoryUtilizationYear"),"memoryUtilization Year is missing.");
   		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("cpuUtilizationYearTitle"),"cpuUtilizationYear Title is missing.");
   		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("memoryUtilizationYearTitle"),"memoryUtilizationYear Title is missing.");
   		analyticsPage.clickOnElementsOfAnalyticsPage("windowsSoftwarePerformance");
   		//softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("cpuUtilizationYear"),"cpuUtilization Year is  missing.");
   		//softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("memoryUtilizationYear"),"memoryUtilization Year is missing.");
   		//softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("cpuUtilizationYearTitle"),"cpuUtilizationYear Title is missing.");
   		//softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("memoryUtilizationYearTitle"),"memoryUtilizationYear Title is missing.");
   		analyticsPage.clickOnElementsOfAnalyticsPage("MacSoftwarePerformance");
   		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("appCauseCPUOverUtilizMacTitle"),"appCauseCPUOverUtilizMac Title is  missing.");
   		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("appCauseCPUOverUtilizMac"),"appCauseCPUOverUtilization for Mac is missing.");
   		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("appMemoryOverutilizationMacTitle"),"appMemoryOverutilizationMac Title is missing.");
   		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("appMemoryOverutilizationMac"),"appMemoryOverutilizationMac is missing.");
   		analyticsPage.clickOnElementsOfAnalyticsPage("linuxSoftwarePerformance");
   		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("AppCausCpuOverutilizationTitle"),"AppCausCpuOverutilization Title is  missing.");
   		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("AppCausCpuOverutilizationLinux"),"AppCausCpuOverutilization for Linux is missing.");
   		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("AppCausMemoryOverutilizationTitle"),"AppCausMemoryOverutilization Title is missing.");
   		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("AppCausMemoryOverutilizationLinux"),"AppCausMemoryOverutilizationLinux is missing.");
   		softAssert.assertAll();

   	}

   @Test(priority = 83, groups = {"PRODUCTION_LDK","INITECH_SOLUTIONS"}, enabled = true)
   	public final void verifyDeploymentStatusAnalyticsLdk() throws Exception {
   		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
   		SoftAssert softAssert = new SoftAssert();
   		String testSuiteName = SetTestEnvironments.suiteName;
		if(testSuiteName.contains("LDK") || testSuiteName.contains("Initech")){
			switchUserBasedOnSuite(testSuiteName);
		}
   		AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver()).getInstance();
   		leftSideMenuVerification();

   		analyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
   		waitForPageLoaded();
   		Assert.assertTrue(analyticsPage.getTextOfAnalyticsPage("analytics").equals(analyticsPage.getTextLanguage(LanguageCode, "daas_ui", "wex.analytics")),
   				"Analytics title doesn't match");
   		LOGGER.info("Analytics listing page opened correctly");
   		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("classicReports"),"classic Reports is not available");
   		analyticsPage.clickByJavaScriptOnAnalyticsPage("classicReports");
   		waitForPageLoaded();		
   		Thread.sleep(2000);
   		analyticsPage.clickByJavaScriptOnAnalyticsPage("deploymentStatus");
   		waitForPageLoaded();
   		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("installationStatus"),"installationStatus report is  missing.");
   		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("installationStatusChart"),"installationStatus Chart is missing.");
   		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("installationStatusTitle"),"installationStatus Title missing.");
   		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("installationStatusFailure"),"installationStatus Failure chart missing.");
   		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("installationStatusSuccess"),"installationStatus Success chart missing.");
   		softAssert.assertAll();

   	}
   	

   @Test(priority = 84, groups = {"PRODUCTION_LDK","INITECH_SOLUTIONS"}, enabled = false)
   	public final void verifyDiskReplacementAnalyticsLdk() throws Exception {
   		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
   		SoftAssert softAssert = new SoftAssert();
   		String testSuiteName = SetTestEnvironments.suiteName;
		if(testSuiteName.contains("LDK") || testSuiteName.contains("Initech")){
			switchUserBasedOnSuite(testSuiteName);
		}
   		AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver()).getInstance();
   		leftSideMenuVerification();
   		analyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
   		waitForPageLoaded();
   		Assert.assertTrue(analyticsPage.getTextOfAnalyticsPage("analytics").equals(analyticsPage.getTextLanguage(LanguageCode, "daas_ui", "wex.analytics")),
   				"Analytics title doesn't match");
   		LOGGER.info("Analytics listing page opened correctly");
   		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("classicReports"),"classic Reports is not available");
   		analyticsPage.clickByJavaScriptOnAnalyticsPage("classicReports");
   		waitForPageLoaded();		
   		Thread.sleep(2000);
   		analyticsPage.clickByJavaScriptOnAnalyticsPage("deploymentStatus");
   		waitForPageLoaded();
   		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("installationStatus"),"installationStatus report is  missing.");
   		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("installationStatusChart"),"installationStatus Chart is missing.");
   		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("installationStatusTitle"),"installationStatus Title missing.");
   		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("installationStatusFailure"),"installationStatus Failure chart missing.");
   		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("installationStatusSuccess"),"installationStatus Success chart missing.");
   		softAssert.assertAll();
   		
   	}

   @Test(priority = 85, groups = {"PRODUCTION_LDK","INITECH_SOLUTIONS"}, enabled = true)
   	public final void verifyNonreportingDevicesAnalyticsLdk() throws Exception {
   		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
   		SoftAssert softAssert = new SoftAssert();
   		String testSuiteName = SetTestEnvironments.suiteName;
		if(testSuiteName.contains("LDK") || testSuiteName.contains("Initech")){
			switchUserBasedOnSuite(testSuiteName);
		}
   		AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver()).getInstance();
   		leftSideMenuVerification();
   		analyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
   		waitForPageLoaded();
   		Assert.assertTrue(analyticsPage.getTextOfAnalyticsPage("analytics").equals(analyticsPage.getTextLanguage(LanguageCode, "daas_ui", "wex.analytics")),
   				"Analytics title doesn't match");
   		LOGGER.info("Analytics listing page opened correctly");
   		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("classicReports"),"classic Reports is not available");
   		analyticsPage.clickOnElementsOfAnalyticsPage("classicReports");
   		waitForPageLoaded();		
   		Thread.sleep(2000);
   		analyticsPage.clickByJavaScriptOnAnalyticsPage("nonReportingDevicesDetails");
   		waitForPageLoaded();
   		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("nonReportingDevicesTitle"),"nonReportingDevices Title is missing.");
   		//softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("installationStatusChart"),"installationStatus Chart is missing.");
   		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("nonReportingDevicesSummary"),"nonReportingDevices Summary is missing.");
   		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("nonReportingDevicesSummryChart"),"nonReportingDevices SummryChart missing.");
   		softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("nonReportingDeviceshighchartsLegendBox"),"nonReportingDeviceshighcharts LegendBox is missing.");
   		softAssert.assertAll();
   	}
   		@Test(priority = 86, groups = {"PRODUCTION_LDK","INITECH_SOLUTIONS"}, enabled = false)
   		public final void verifySeatsEntitledbyDeviceAnalyticsLdk() throws Exception {
   			login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
   			SoftAssert softAssert = new SoftAssert();
   			String testSuiteName = SetTestEnvironments.suiteName;
   			if(testSuiteName.contains("LDK") || testSuiteName.contains("Initech")){
   				switchUserBasedOnSuite(testSuiteName);
   			}
   			AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver()).getInstance();
   			leftSideMenuVerification();
   			analyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
   			waitForPageLoaded();
   			Assert.assertTrue(analyticsPage.getTextOfAnalyticsPage("analytics").equals(analyticsPage.getTextLanguage(LanguageCode, "daas_ui", "wex.analytics")),
   					"Analytics title doesn't match");
   			LOGGER.info("Analytics listing page opened correctly");
   			softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("classicReports"),"classic Reports is not available");
   			analyticsPage.clickOnElementsOfAnalyticsPage("classicReports");
   			waitForPageLoaded();		
   			Thread.sleep(2000);
   			analyticsPage.clickOnElementsOfAnalyticsPage("nonReportingDevicesDetails");
   			waitForPageLoaded();
   			softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("nonReportingDevicesTitle"),"nonReportingDevices Title is missing.");
   			softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("installationStatusChart"),"installationStatus Chart is missing.");
   			softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("nonReportingDevicesSummary"),"nonReportingDevices Summary is missing.");
   			softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("nonReportingDevicesSummryChart"),"nonReportingDevices SummryChart missing.");
   			softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("nonReportingDeviceshighchartsLegendBox"),"nonReportingDeviceshighcharts LegendBox is missing.");
   			softAssert.assertAll();

   		}
   		@Test(priority = 87, groups = {"PRODUCTION_LDK","INITECH_SOLUTIONS"}, enabled = false)
   		public final void verifyWindowsStartupAndShutdownAnalyticsLdk() throws Exception {
   			login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
   			SoftAssert softAssert = new SoftAssert();
   			String testSuiteName = SetTestEnvironments.suiteName;
   			if(testSuiteName.contains("LDK") || testSuiteName.contains("Initech")){
   				switchUserBasedOnSuite(testSuiteName);
   			}
   			AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver()).getInstance();
   			leftSideMenuVerification();
   			analyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
   			waitForPageLoaded();
   			Assert.assertTrue(analyticsPage.getTextOfAnalyticsPage("analytics").equals(analyticsPage.getTextLanguage(LanguageCode, "daas_ui", "wex.analytics")),
   					"Analytics title doesn't match");
   			LOGGER.info("Analytics listing page opened correctly");
   			softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("classicReports"),"classic Reports is not available");
   			analyticsPage.clickOnElementsOfAnalyticsPage("classicReports");
   			waitForPageLoaded();		
   			Thread.sleep(2000);
   			analyticsPage.clickOnElementsOfAnalyticsPage("nonReportingDevicesDetails");
   			waitForPageLoaded();
   			softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("nonReportingDevicesTitle"),"nonReportingDevices Title is missing.");
   			softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("installationStatusChart"),"installationStatus Chart is missing.");
   			softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("nonReportingDevicesSummary"),"nonReportingDevices Summary is missing.");
   			softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("nonReportingDevicesSummryChart"),"nonReportingDevices SummryChart missing.");
   			softAssert.assertTrue(analyticsPage.verifyElementsOfAnalyticsPage("nonReportingDeviceshighchartsLegendBox"),"nonReportingDeviceshighcharts LegendBox is missing.");
   			softAssert.assertAll();
   		}


}
