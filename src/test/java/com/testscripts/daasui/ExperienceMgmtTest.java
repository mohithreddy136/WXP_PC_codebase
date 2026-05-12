package com.testscripts.daasui;

import java.util.List;

import com.daasui.pages.LogPage;
import com.daasui.pages.PreferencesPage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.basesource.action.PreDefinedActions;
import com.daasui.constants.DashboardVariables;
import com.daasui.pages.DashboardPage;
import com.daasui.pages.ExperienceMgmtPage;


public class ExperienceMgmtTest extends CommonTest {
	private static Logger LOGGER = LogManager.getLogger(DashboardTest.class);
	public static final String LastLoggedInUserPreferences = "api/2.0/user-preferences?userId=";
	public static final String keyPreferences = "&key=dashboard_user_preferences";
	public static final String globalFilterCompany = getEnvironmentSpecificData(System.getProperty("environment"), "GLOBAL_FILTER_COMPANY_DO_NOT_DELETE");
	public static final String globalFilterLocation1 = getEnvironmentSpecificData(System.getProperty("environment"), "GLOBAL_FILTER_LOCATION1_DO_NOT_DELETE");
	public static final String globalFilterLocation2 = getEnvironmentSpecificData(System.getProperty("environment"), "GLOBAL_FILTER_LOCATION2_DO_NOT_DELETE");
	public static final String globalFilterLocation3 = getEnvironmentSpecificData(System.getProperty("environment"), "GLOBAL_FILTER_LOCATION3_DO_NOT_DELETE");
	public static final String globalFilterLocation4 = getEnvironmentSpecificData(System.getProperty("environment"), "GLOBAL_FILTER_LOCATION4_DO_NOT_DELETE");
	public static final String diskCapacityBody = "{\"tenants\":[],\"filter\":\"\",\"tenantDetails\":[]}";
	public static final String globalFilterCompanyFleetChart = getEnvironmentSpecificData(System.getProperty("environment"), "GLOBAL_FILTER_COMPANY_FLEETCHART_DO_NOT_DELETE");
	public static final String globalFilterCompanyFleetSecurity = getEnvironmentSpecificData(System.getProperty("environment"), "GLOBAL_FILTER_COMPANY_FLEETSECURITY_DO_NOT_DELETE");

	@DataProvider
	public Object[][] getLoginData() {
		Object[][] data = new Object[2][2];
		data[0][0] = "MSP_ADMIN_US_EMAIl_DASHBOARD";
		data[0][1] = "MSP_ADMIN_US_PASSWORD_DASHBOARD";
		data[1][0] = "RESELLER_NEW_UI_EMAIL_US";
		data[1][1] = "RESELLER_NEW_UI_PASSWORD_US";
		return data;
	}
	
	/**
	 * This Data provider includes credential for PI and AC users.
	 * 
	 * @return Object
	 */
	@DataProvider
	public Object[][] getLoginDataForDigitalExpFeature() {
		Object[][] data = new Object[2][2];
		data[0][0] = "MSP_NEW_USER_EMAIL_FLEETCHART_US";
		data[0][1] = "MSP_NEW_USER_PASSWORD_FLEETCHART_US";
		data[1][0] = "COMPANY_DES_CHART_US_EMAIL";
		data[1][1] = "COMPANY_DES_CHART_US_PASSWORD";
		return data;
	}
	
	/*
	 * User Story 900138: [Core][OKR H5][WorkF][UI] Digital Experience Score Experience Management Dashboard V2
	 * This test verifies device health page on experience management tab.
	 * */
	
	@Test(priority = 01,groups = { "REGRESSIONDASHBOARD2", "SMOKE", "REGRESSION_CI","SMOKE_CI" }, description = "Test Case ID : 838914",enabled=false)
	public final void verifyExperienceMgmtDeviceHealth () throws Exception {
		login("MSP_NEW_USER_EMAIL_FLEETCHART_US", "MSP_NEW_USER_PASSWORD_FLEETCHART_US");
		SoftAssert softAssert = new SoftAssert();
		final String[] expMgmtListColumns = {"digital_exp_score_table_column_subcomponent","digital_exp_score_table_column_weighting_percentage","digital_exp_score_table_column_previous_week_score_change","digital_exp_score_table_column_previous_week_device_change","digital_exp_score_table_column_current_score"};
		
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		ExperienceMgmtPage experienceMgmtPage = new ExperienceMgmtPage(PreDefinedActions.getDriver()).getInstance();
		
		dashboardPage.clickOnElementsOfDashboardPage("recommandedActionButton");
		waitForPageLoaded();
		
		if (!toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, "COMPANY_DES_CHART_US_EMAIL"))){
			Assert.assertTrue(setToDefaultDashboard(LanguageCode),"Error occured in setting dashboard to default mode.");
		}else{
			Assert.assertTrue(setToDefaultDashboardGlobalFilter(LanguageCode),"Error occured in setting dashboard to default mode.");
		}
		
		// Test Case 1 - Verify DES chart functionality for location filter ALL.
		softAssert.assertTrue(dashboardPage.verifyTitleofChartOfDashboardPage("digitalExperienceChartTitle", dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "des_dashboard_widget_title")), "Digital Experience chart title did not match.");
		softAssert.assertTrue(dashboardPage.verifyTextContainsOnElementsOfDashboardPage("digitalExperienceChartNoData", dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "digital.exp.score.dashboard.no.data")), "Digital Experience chart .");
		waitForPageLoaded();
	    dashboardPage.clickByJavaScriptOnDashboardPage("globalLocationFilterDropDown");
		 softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("filterMenuCompaniesHeader").equalsIgnoreCase(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "dashboard.actions.filter.company.label")), "Location filter header does not match");
			dashboardPage.enterTextForDashboardPage("filterMenuCompaniesSearch", globalFilterCompanyFleetChart);
			LOGGER.info("Search company to set location filter");
			waitForPageLoaded();
			dashboardPage.waitForElementsOfDashboardPage("companyOnListSearch");
			softAssert.assertTrue(dashboardPage.matchTextOfDashboardPage("companyOnListSearch", globalFilterCompanyFleetChart), "Company name and search result company name does not match");
			if (dashboardPage.verifyElementsOfDashboardPage("companyOnListSearch"))
				dashboardPage.clickOnElementsOfDashboardPage("companyOnListSearch");
			LOGGER.info("Select company to set location filter");
			sleeper(2000);
			dashboardPage.clickOnElementsOfDashboardPage("globalFilterSave");
			LOGGER.info("Global Location Filter Saved successfully.");
			waitForPageLoaded();
		
		if (dashboardPage.verifyElementsOfDashboardPage("digitalExperienceChartTitle")) {
			// Test Case 1 (This Test case validates title of the chart.)
			softAssert.assertTrue(dashboardPage.verifyTitleofChartOfDashboardPage("digitalExperienceChartTitle", dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "des_dashboard_widget_title")), "Digital Experience chart title did not match.");
			LOGGER.info("Verified title of the DES chart");
			waitForPageLoaded();
			//Test case 2 This test case validates Device Health chart.
			softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("deviceHealth").equalsIgnoreCase(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "digital_exp_score_kpi_device_health")), "Device health title did not match.");
			LOGGER.info("Verified Device health text is available on exp mgmt page");
			waitForPageLoaded();
			dashboardPage.clickOnElementsOfDashboardPage("deviceHealthView");
			waitForPageLoaded();
			//Verify Device Health Tab
			experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("deviceHealthTab");
			sleeper(2000);
			softAssert.assertTrue(experienceMgmtPage.getTextOfExperienceMgmtPage("deviceHealthTab").equalsIgnoreCase(experienceMgmtPage.getTextLanguage(LanguageCode, "daas_ui", "digital.exp.score.tab.label.device.health")), "Device health  tab is not visible.");
			softAssert.assertTrue(experienceMgmtPage.getTextOfExperienceMgmtPage("benchmarkmessage").equalsIgnoreCase(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "digital.exp.score.guage.benchmark.note")), "Benchmark message title did not match.");
			softAssert.assertTrue(experienceMgmtPage.verifyElementIsVisible("benchmarktag"));
			
			//Verify Device health score title.
			softAssert.assertTrue(experienceMgmtPage.verifyTitleofChartOfExperienceMgmtPage("deviceHealthScoreTitle", dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "digital.exp.score.tab.device.health")), "Device Health SCore chart title did not match.");
			softAssert.assertTrue(experienceMgmtPage.getTextOfExperienceMgmtPage("deviceHealthText").equalsIgnoreCase(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "digital_exp_score_kpi_device_health")), "Device health  text is not visible.");
			softAssert.assertTrue(experienceMgmtPage.getTextOfExperienceMgmtPage("deviceHealthLastWeek").equalsIgnoreCase(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "digital.exp.score.guage.changeInScore")), "Device health  last week text is not visible.");
			softAssert.assertTrue(experienceMgmtPage.getTextOfExperienceMgmtPage("poorDeviceHealth").equalsIgnoreCase(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "digital.exp.score.guage.legend.poor")), "Device health poor text is not visible.");
			softAssert.assertTrue(experienceMgmtPage.getTextOfExperienceMgmtPage("fairDeviceHealth").equalsIgnoreCase(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "digital.exp.score.guage.legend.fair")), "Device health fairtext is not visible.");
			softAssert.assertTrue(experienceMgmtPage.getTextOfExperienceMgmtPage("excellentDeviceHealth").equalsIgnoreCase(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "digital.exp.score.guage.legend.excellent")), "Device health  excellent text is not visible.");
			//verify chart
			waitForPageLoaded();
			Assert.assertTrue(experienceMgmtPage.verifyColumnsofExpMgmtTable(LanguageCode,"expeMgmtDeviceHealthChart",expMgmtListColumns), "Device health table column header has wrong or missing column order");
			LOGGER.info("DES chart table is validated successfully");
			experienceMgmtPage.scrollDownCharts();
			//Verify Recommended Actions
			softAssert.assertTrue(experienceMgmtPage.verifyTitleofChartOfExperienceMgmtPage("recommendedActions", dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "digital.exp.score.recommended.actions.header")), "Recommended Actions chart title did not match.");
			if(experienceMgmtPage.verifyElementsOfExperienceMgmtPage("noDataRecommendedAction")) {
				
				LOGGER.info("The msg Nothing going on here validated successfully");
			}else {
		    softAssert.assertTrue(experienceMgmtPage.getTextOfExperienceMgmtPage("batteryReplacement").equalsIgnoreCase(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "insight.battery_replacement.battery.sidebar.title")), "Battery status text is not visible.");
			experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("batteryReplacementViewIncident");
			LOGGER.info("Navigation to incident page is validated successfully");
			waitForPageLoaded();
			
			gotoExperienceMgmtPageTab();
			experienceMgmtPage.scrollDownCharts();
            softAssert.assertTrue(experienceMgmtPage.getTextOfExperienceMgmtPage("diskReplacements").equalsIgnoreCase(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "insight.hdd_replacements.hdd.breadcrumbs.title")), "Battery status text is not visible.");
            experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("diskReplacementViewIncident");
			LOGGER.info("Navigation to incident page is validated successfully");
			waitForPageLoaded();
			gotoExperienceMgmtPageTab();
			}
		 experienceMgmtPage.scrollDownCharts();
			//Verify Recommended Reports
			softAssert.assertTrue(experienceMgmtPage.verifyTitleofChartOfExperienceMgmtPage("recommendedReports", dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "digital.exp.score.recommended.reports_header")), "Recommended Reports title did not match.");
			softAssert.assertTrue(experienceMgmtPage.getTextOfExperienceMgmtPage("batteryStatus").equalsIgnoreCase(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "digital_exp_score_report_battery_status")), "Battery status text is not visible.");
			experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("batteryStatusviewReportDeviceHealthLink");
			waitForPageLoaded();
			experienceMgmtPage.switchBackToPreviousTab();
			sleeper(2000);
			experienceMgmtPage.scrollDownCharts();
			softAssert.assertTrue(experienceMgmtPage.getTextOfExperienceMgmtPage("diskReplacement").equalsIgnoreCase(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "digital_exp_score_report_disk_replacement")), "Disk replacement text is not visible.");
			experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("diskReplacementviewReportDeviceHealthLink");
			waitForPageLoaded();
			experienceMgmtPage.switchBackToPreviousTab();
			sleeper(2000);
			experienceMgmtPage.scrollDownCharts();
			softAssert.assertTrue(experienceMgmtPage.getTextOfExperienceMgmtPage("driverInventory").equalsIgnoreCase(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "digital_exp_score_report_driver_inventory")), "Driver Inventory text is not visible.");
			experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("driverInventoryviewReportDeviceHealthLink");
			waitForPageLoaded();
			experienceMgmtPage.switchBackToPreviousTab();
			sleeper(2000);
			experienceMgmtPage.scrollDownCharts();
			experienceMgmtPage.scrollToExperienceMgmtPage("hardwareWarranty");
			softAssert.assertTrue(experienceMgmtPage.getTextOfExperienceMgmtPage("hardwareWarranty").equalsIgnoreCase(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "digital_exp_score_report_hardware_inventory")), "Hardware Warranty text is not visible.");
			experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("hardwareWarrantyviewReportDeviceHealthLink");
			waitForPageLoaded();
			experienceMgmtPage.switchBackToPreviousTab();
			sleeper(2000);
			experienceMgmtPage.scrollDownCharts();
			softAssert.assertTrue(experienceMgmtPage.getTextOfExperienceMgmtPage("thermal").equalsIgnoreCase(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "digital_exp_score_report_thermal")), "Thermal text is not visible.");
			experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("thermalviewReportDeviceHealthLink");
			waitForPageLoaded();
			experienceMgmtPage.switchBackToPreviousTab();
			sleeper(2000);
			experienceMgmtPage.scrollDownCharts();
			LOGGER.info("Recommanded reports is validated successfully");
			//Verify Device over time chart
			softAssert.assertTrue(experienceMgmtPage.verifyTitleofChartOfExperienceMgmtPage("deviceHealthOvertimeChart", dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "digital.exp.score.device_health_summary_chart_title")), "Disk replacement  chart title did not match.");
			experienceMgmtPage.getTextOfExperienceMgmtPage("deviceHealthScoreYaxis");
			experienceMgmtPage.getTextOfExperienceMgmtPage("deviceHealthScoreXaxis");
			LOGGER.info("Device health score chart is validated successfully");
			}
	}
	    /*
	     * User Story 900138: [Core][OKR H5][WorkF][UI] Digital Experience Score Experience Management Dashboard V2.
	     *  This test verifies device Performance page on experience management tab.
	     *  */
	
	@Test(priority = 02,enabled = false, groups = { "REGRESSIONDASHBOARD2", "SMOKE", "REGRESSION_CI","SMOKE_CI" }, description = "Test Case ID : 838914")
	public final void verifyExperienceMgmtDevicePerformance () throws Exception {
		login("MSP_NEW_USER_EMAIL_FLEETCHART_US", "MSP_NEW_USER_PASSWORD_FLEETCHART_US");
		SoftAssert softAssert = new SoftAssert();
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		ExperienceMgmtPage experienceMgmtPage = new ExperienceMgmtPage(PreDefinedActions.getDriver()).getInstance();
		
		dashboardPage.clickOnElementsOfDashboardPage("recommandedActionButton");
		waitForPageLoaded();
		
		if (!toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, "COMPANY_DES_CHART_US_EMAIL"))){
			Assert.assertTrue(setToDefaultDashboard(LanguageCode),"Error occured in setting dashboard to default mode.");
		}else{
			Assert.assertTrue(setToDefaultDashboardGlobalFilter(LanguageCode),"Error occured in setting dashboard to default mode.");
		}
		
		// Test Case 1 - Verify DES chart functionality for location filter ALL.
		softAssert.assertTrue(dashboardPage.verifyTitleofChartOfDashboardPage("digitalExperienceChartTitle", dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "des_dashboard_widget_title")), "Digital Experience chart title did not match.");
		softAssert.assertTrue(dashboardPage.verifyTextContainsOnElementsOfDashboardPage("digitalExperienceChartNoData", dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "digital.exp.score.dashboard.no.data")), "Digital Experience chart .");
		waitForPageLoaded();
			dashboardPage.clickByJavaScriptOnDashboardPage("globalLocationFilterDropDown");
		    softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("filterMenuCompaniesHeader").equalsIgnoreCase(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "dashboard.actions.filter.company.label")), "Location filter header does not match");
			dashboardPage.enterTextForDashboardPage("filterMenuCompaniesSearch", globalFilterCompanyFleetChart);
			LOGGER.info("Search company to set location filter");
			waitForPageLoaded();
			dashboardPage.waitForElementsOfDashboardPage("companyOnListSearch");
			softAssert.assertTrue(dashboardPage.matchTextOfDashboardPage("companyOnListSearch", globalFilterCompanyFleetChart), "Company name and search result company name does not match");
			if (dashboardPage.verifyElementsOfDashboardPage("companyOnListSearch"))
				dashboardPage.clickOnElementsOfDashboardPage("companyOnListSearch");
			LOGGER.info("Select company to set location filter");
			sleeper(2000);
			dashboardPage.clickOnElementsOfDashboardPage("globalFilterSave");
			LOGGER.info("Global Location Filter Saved successfully.");
			waitForPageLoaded();
		
		if (dashboardPage.verifyElementsOfDashboardPage("digitalExperienceChartTitle")) {
			//will uncomment this validation once the string gets available in maestro
			// Test Case 1 (This Test case validates title of the chart.)
			softAssert.assertTrue(dashboardPage.verifyTitleofChartOfDashboardPage("digitalExperienceChartTitle", dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "des_dashboard_widget_title")), "Digital Experience chart title did not match.");
			LOGGER.info("Verified title of the DES chart");
			waitForPageLoaded();
		
			//Test case 2 This test case validates Device Performance chart.
			softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("devicePerformance").equalsIgnoreCase(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "digital_exp_score_kpi_device_performance")), "Device performance title did not match.");
			LOGGER.info("Verified Device Performance text is available on exp mgmt page");
			waitForPageLoaded();
			dashboardPage.clickOnElementsOfDashboardPage("devicePerformanceView");
			waitForPageLoaded();
		    //Verify Device Performance Tab
			experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("devicePerformanceTab");
			//softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("devicePerformanceTab").equalsIgnoreCase(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "digital.exp.score.tab.label.device.performance")), "Device performance  tab is not visible.");
			softAssert.assertTrue(experienceMgmtPage.getTextOfExperienceMgmtPage("devicePerformanceTab").equalsIgnoreCase(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "digital.exp.score.tab.label.device.performance")), "Device performance  tab is not visible.");
			sleeper(2000);
			softAssert.assertTrue(experienceMgmtPage.getTextOfExperienceMgmtPage("benchmarkmessage").equalsIgnoreCase(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "digital.exp.score.guage.benchmark.note")), "Benchmark message title did not match.");
			softAssert.assertTrue(experienceMgmtPage.verifyElementIsVisible("benchmarktag"));
			//Verify Device Performance score title.
			softAssert.assertTrue(experienceMgmtPage.verifyTitleofChartOfExperienceMgmtPage("devicePerformanceScoreTitle", dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "digital.exp.score.tab.device.performance")), "Device Performance SCore chart title did not match.");
			softAssert.assertTrue(experienceMgmtPage.getTextOfExperienceMgmtPage("devicePerformanceText").equalsIgnoreCase(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "digital_exp_score_kpi_device_performance")), "Device Performance  text is not visible.");
			softAssert.assertTrue(experienceMgmtPage.getTextOfExperienceMgmtPage("devicePerformanceLastWeek").equalsIgnoreCase(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "digital.exp.score.guage.changeInScore")), "Device health  last week text is not visible.");
			softAssert.assertTrue(experienceMgmtPage.getTextOfExperienceMgmtPage("poorDevicePerformance").equalsIgnoreCase(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "digital.exp.score.guage.legend.poor")), "Device health poor text is not visible.");
			softAssert.assertTrue(experienceMgmtPage.getTextOfExperienceMgmtPage("fairDevicePerformance").equalsIgnoreCase(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "digital.exp.score.guage.legend.fair")), "Device health fairtext is not visible.");
			softAssert.assertTrue(experienceMgmtPage.getTextOfExperienceMgmtPage("excellentDevicePerformance").equalsIgnoreCase(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "digital.exp.score.guage.legend.excellent")), "Device health  excellent text is not visible.");
            experienceMgmtPage.scrollDownCharts();
			//Verify Recommended Actions
			softAssert.assertTrue(experienceMgmtPage.verifyTitleofChartOfExperienceMgmtPage("recommendedActions", dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "digital.exp.score.recommended.actions.header")), "Recommended Actions chart title did not match.");
			experienceMgmtPage.getTextOfExperienceMgmtPage("noDataOnRecommendedActionPerformance");}
			waitForPageLoaded();
			experienceMgmtPage.scrollDownCharts();
			//Verify Recommended Reports
			softAssert.assertTrue(experienceMgmtPage.verifyTitleofChartOfExperienceMgmtPage("recommendedReportsDevicePerformance", dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "digital.exp.score.recommended.reports_header")), "Recommended Reports title did not match.");
			softAssert.assertTrue(experienceMgmtPage.getTextOfExperienceMgmtPage("bsodDevicePerformance").equalsIgnoreCase(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "digital_exp_score_report_bsod")), "BSOD status text is not visible.");
			experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("bsodStatusviewReportDeviceHealthLink");
			waitForPageLoaded();
			experienceMgmtPage.switchToPreviousTabOfExperienceMgmtPage();
			experienceMgmtPage.scrollDownCharts();
			softAssert.assertTrue(experienceMgmtPage.getTextOfExperienceMgmtPage("softwareErrorsDevicePerformance").equalsIgnoreCase(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "digital_exp_score_report_software_errors")), "Software errors text is not visible.");
			experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("softwareErrorStatusviewReportDeviceHealthLink");
			waitForPageLoaded();
			experienceMgmtPage.switchToPreviousTabOfExperienceMgmtPage();
			experienceMgmtPage.scrollDownCharts();
		softAssert.assertTrue(experienceMgmtPage.getTextOfExperienceMgmtPage("cpuUtilizationDevicePerformance").equalsIgnoreCase(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "digital_exp_score_report_cpu_utilization")), "CPU Utilization text is not visible.");
			experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("cpuStatusviewReportDeviceHealthLink");
			waitForPageLoaded();
			experienceMgmtPage.switchToPreviousTabOfExperienceMgmtPage();
			experienceMgmtPage.scrollDownCharts();
		experienceMgmtPage.scrollToExperienceMgmtPage("missingBiosDevicePerformance");
			softAssert.assertTrue(experienceMgmtPage.getTextOfExperienceMgmtPage("missingBiosDevicePerformance").equalsIgnoreCase(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "digital_exp_score_report_missing_critical_bios_update")), "Critical BIOS text is not visible.");
			experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("missingBiosStatusviewReportDeviceHealthLink");
			waitForPageLoaded();
			experienceMgmtPage.switchToPreviousTabOfExperienceMgmtPage();
			experienceMgmtPage.scrollDownCharts();
			softAssert.assertTrue(experienceMgmtPage.getTextOfExperienceMgmtPage("startUpDevicePerformance").equalsIgnoreCase(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "digital_exp_score_report_startup_shutdown_duration")), "Start-up / Shut-down duration text is not visible.");
			experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("startUpStatusviewReportDeviceHealthLink");
			waitForPageLoaded();
			experienceMgmtPage.switchToPreviousTabOfExperienceMgmtPage();
			experienceMgmtPage.scrollDownCharts();
			
			softAssert.assertTrue(experienceMgmtPage.getTextOfExperienceMgmtPage("memoryDevicePerformance").equalsIgnoreCase(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "digital_exp_score_report_memory_over")), "Memory Over text is not visible.");
			experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("memoryDeviceviewReportDeviceHealthLink");
			waitForPageLoaded();
			experienceMgmtPage.switchToPreviousTabOfExperienceMgmtPage();
			experienceMgmtPage.scrollDownCharts();
			
			softAssert.assertTrue(experienceMgmtPage.getTextOfExperienceMgmtPage("utilizationDevicePerformance").equalsIgnoreCase(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "digital_exp_score_report_utilization_disk_space")), "Utilization Disk Space text is not visible.");
			experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("utilizationviewReportDeviceHealthLink");
			waitForPageLoaded();
			experienceMgmtPage.switchToPreviousTabOfExperienceMgmtPage();
			experienceMgmtPage.scrollDownCharts();
		LOGGER.info("Recommanded reports is validated successfully");
			//Verify Device over time chart
			softAssert.assertTrue(experienceMgmtPage.verifyTitleofChartOfExperienceMgmtPage("devicePerformanceOvertimeChart", dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "digital.exp.score.device_performance_summary_chart_title')}")), "Disk replacement  chart title did not match.");
			experienceMgmtPage.getTextOfExperienceMgmtPage("devicePerformanceScoreYaxis");
			experienceMgmtPage.getTextOfExperienceMgmtPage("devicePerformanceScoreXaxis");
			LOGGER.info("Device Performance score chart is validated successfully");
}

      /*
       * User Story 900138: [Core][OKR H5][WorkF][UI] Digital Experience Score Experience Management Dashboard V2
       * This test verifies device Security page on experience management tab.
       * */
	 
	@Test(priority = 03,enabled = false, groups = { "REGRESSIONDASHBOARD2", "SMOKE", "REGRESSION_CI","SMOKE_CI" }, description = "Test Case ID : 838914")
	public final void verifyExperienceMgmtDeviceSecurity () throws Exception {
		login("MSP_NEW_USER_EMAIL_FLEETCHART_US", "MSP_NEW_USER_PASSWORD_FLEETCHART_US");
		SoftAssert softAssert = new SoftAssert();
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		ExperienceMgmtPage experienceMgmtPage = new ExperienceMgmtPage(PreDefinedActions.getDriver()).getInstance();
		dashboardPage.clickOnElementsOfDashboardPage("recommandedActionButton");
		waitForPageLoaded();
		if (!toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, "COMPANY_DES_CHART_US_EMAIL"))){
			Assert.assertTrue(setToDefaultDashboard(LanguageCode),"Error occured in setting dashboard to default mode.");
		}else{
			Assert.assertTrue(setToDefaultDashboardGlobalFilter(LanguageCode),"Error occured in setting dashboard to default mode.");
		}
		
		// Test Case 1 - Verify DES chart functionality for location filter ALL.
		softAssert.assertTrue(dashboardPage.verifyTitleofChartOfDashboardPage("digitalExperienceChartTitle", dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "des_dashboard_widget_title")), "Digital Experience chart title did not match.");
		softAssert.assertTrue(dashboardPage.verifyTextContainsOnElementsOfDashboardPage("digitalExperienceChartNoData", dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "digital.exp.score.dashboard.no.data")), "Digital Experience chart .");
		waitForPageLoaded();
		dashboardPage.clickByJavaScriptOnDashboardPage("globalLocationFilterDropDown");
		 softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("filterMenuCompaniesHeader").equalsIgnoreCase(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "dashboard.actions.filter.company.label")), "Location filter header does not match");
			dashboardPage.enterTextForDashboardPage("filterMenuCompaniesSearch", globalFilterCompanyFleetChart);
			LOGGER.info("Search company to set location filter");
			waitForPageLoaded();
			dashboardPage.waitForElementsOfDashboardPage("companyOnListSearch");
			softAssert.assertTrue(dashboardPage.matchTextOfDashboardPage("companyOnListSearch", globalFilterCompanyFleetChart), "Company name and search result company name does not match");
			if (dashboardPage.verifyElementsOfDashboardPage("companyOnListSearch"))
				dashboardPage.clickOnElementsOfDashboardPage("companyOnListSearch");
			LOGGER.info("Select company to set location filter");
			sleeper(2000);
			dashboardPage.clickOnElementsOfDashboardPage("globalFilterSave");
			LOGGER.info("Global Location Filter Saved successfully.");
			waitForPageLoaded();
		
		if (dashboardPage.verifyElementsOfDashboardPage("digitalExperienceChartTitle")) {
			//will uncomment this validation once the string gets available in maestro
			// Test Case 1 (This Test case validates title of the chart.)
			softAssert.assertTrue(dashboardPage.verifyTitleofChartOfDashboardPage("digitalExperienceChartTitle", dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "des_dashboard_widget_title")), "Digital Experience chart title did not match.");
			LOGGER.info("Verified title of the DES chart");
			waitForPageLoaded();
			
			//Test case 2 This test case validates Device Security chart.
			softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("deviceSecurity").equalsIgnoreCase(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "digital_exp_score_kpi_device_security")), "Device Security title did not match.");
		 LOGGER.info("Verified Device Security text is available on exp mgmt page");
		   //waitForPageLoaded();
			dashboardPage.clickOnElementsOfDashboardPage("deviceSecurityView");	
			waitForPageLoaded();
		//Verify Device Security Tab
			experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("deviceSecurityTab");
		    softAssert.assertTrue(experienceMgmtPage.getTextOfExperienceMgmtPage("deviceSecurityTab").equalsIgnoreCase(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "digital.exp.score.tab.label.device.security")), "Device Security  tab is not visible.");
		    sleeper(2000);
			softAssert.assertTrue(experienceMgmtPage.getTextOfExperienceMgmtPage("benchmarkmessage").equalsIgnoreCase(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "digital.exp.score.guage.benchmark.note")), "Benchmark message title did not match.");
			softAssert.assertTrue(experienceMgmtPage.verifyElementIsVisible("benchmarktag"));
			//Verify Device Security score title.
			softAssert.assertTrue(experienceMgmtPage.verifyTitleofChartOfExperienceMgmtPage("deviceSecurityScoreTitle", dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "digital.exp.score.tab.device.security")), "Device Security Score chart title did not match.");
			softAssert.assertTrue(experienceMgmtPage.getTextOfExperienceMgmtPage("deviceSecurityText").equalsIgnoreCase(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "digital_exp_score_kpi_device_security")), "Device Security text is not visible.");
			softAssert.assertTrue(experienceMgmtPage.getTextOfExperienceMgmtPage("deviceSecurityLastWeek").equalsIgnoreCase(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "digital.exp.score.guage.changeInScore")), "Device security  last week text is not visible.");
			softAssert.assertTrue(experienceMgmtPage.getTextOfExperienceMgmtPage("poorDeviceSecurity").equalsIgnoreCase(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "digital.exp.score.guage.legend.poor")), "Device security poor text is not visible.");
			softAssert.assertTrue(experienceMgmtPage.getTextOfExperienceMgmtPage("fairDeviceSecurity").equalsIgnoreCase(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "digital.exp.score.guage.legend.fair")), "Device security fair text is not visible.");
			softAssert.assertTrue(experienceMgmtPage.getTextOfExperienceMgmtPage("excellentDeviceSecurity").equalsIgnoreCase(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "digital.exp.score.guage.legend.excellent")), "Device security  excellent text is not visible.");
		     waitForPageLoaded();
			//Assert.assertTrue(experienceMgmtPage.verifyColumnsofExpMgmtTable(LanguageCode,"expeMgmtDeviceHealthChart",expMgmtListColumns), "Device health table column header has wrong or missing column order");
			LOGGER.info("DES chart table is validated successfully");
		    experienceMgmtPage.scrollDownCharts();
			//Verify Recommended Actions
			softAssert.assertTrue(experienceMgmtPage.verifyTitleofChartOfExperienceMgmtPage("recommendedActions", dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "digital.exp.score.recommended.actions.header")), "Recommended Actions chart title did not match.");
			experienceMgmtPage.getTextOfExperienceMgmtPage("noDataOnRecommendedActionSecurity");}
			waitForPageLoaded();
			experienceMgmtPage.scrollDownCharts();
			//Verify Recommended Reports
			softAssert.assertTrue(experienceMgmtPage.verifyTitleofChartOfExperienceMgmtPage("recommendedReportsDeviceSecurity", dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "digital.exp.score.recommended.reports_header")), "Recommended Reports title did not match.");
			softAssert.assertTrue(experienceMgmtPage.getTextOfExperienceMgmtPage("tpEnrollmentPercentage").equalsIgnoreCase(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "digital_exp_score_report_tp_enrollment_percentage")), "Tp enrollment  text is not visible.");
			experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("tpEnrolviewReportDeviceHealthLink");
			waitForPageLoaded();
			experienceMgmtPage.switchBackToPreviousTab();
			experienceMgmtPage.scrollDownCharts();
			
			softAssert.assertTrue(experienceMgmtPage.getTextOfExperienceMgmtPage("nonReportingDevicesTP").equalsIgnoreCase(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "digital_exp_score_report_nonreporting_devices_tp")), "Non reporting devices text is not visible.");
			experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("nonReportviewReportDeviceHealthLink");
			waitForPageLoaded();
			experienceMgmtPage.switchToPreviousTabOfExperienceMgmtPage();
			experienceMgmtPage.scrollDownCharts();
			
			softAssert.assertTrue(experienceMgmtPage.getTextOfExperienceMgmtPage("antivirusProtection").equalsIgnoreCase(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "digital_exp_score_report_antivirus_protection")), "Antivirus protection text is not visible.");
			experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("anitivirusviewReportDeviceHealthLink");
			waitForPageLoaded();
			experienceMgmtPage.switchToPreviousTabOfExperienceMgmtPage();
			experienceMgmtPage.scrollDownCharts();
			
			experienceMgmtPage.scrollToExperienceMgmtPage("firewallProtection");
			softAssert.assertTrue(experienceMgmtPage.getTextOfExperienceMgmtPage("firewallProtection").equalsIgnoreCase(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "digital_exp_score_report_firewall_protection")), "firewall protection text is not visible.");
			experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("firewallviewReportDeviceHealthLink");
			waitForPageLoaded();
			experienceMgmtPage.switchToPreviousTabOfExperienceMgmtPage();
			experienceMgmtPage.scrollDownCharts();
			
			softAssert.assertTrue(experienceMgmtPage.getTextOfExperienceMgmtPage("diskEncryptionProtection").equalsIgnoreCase(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "digital_exp_score_report_disk_encryption_protection")), "disk Encryption Protection text is not visible.");
			experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("diskEncryptionviewReportDeviceHealthLink");
			waitForPageLoaded();
			experienceMgmtPage.switchToPreviousTabOfExperienceMgmtPage();
			experienceMgmtPage.scrollDownCharts();
			
			softAssert.assertTrue(experienceMgmtPage.getTextOfExperienceMgmtPage("secureBoot").equalsIgnoreCase(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "digital_exp_score_report_secure_boot")), "secure boot text is not visible.");
			experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("secureBootviewReportDeviceHealthLink");
			waitForPageLoaded();
			experienceMgmtPage.switchToPreviousTabOfExperienceMgmtPage();
			experienceMgmtPage.scrollDownCharts();
			
			softAssert.assertTrue(experienceMgmtPage.getTextOfExperienceMgmtPage("windowsOsReleaseStatus").equalsIgnoreCase(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "digital_exp_score_report_windows_os_release_status")), "windows Os ReleaseStatus text is not visible.");
			experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("windowsviewReportDeviceHealthLink");
			waitForPageLoaded();
			experienceMgmtPage.switchToPreviousTabOfExperienceMgmtPage();
			experienceMgmtPage.scrollDownCharts();
			
			LOGGER.info("Recommanded reports is validated successfully");
			experienceMgmtPage.scrollDownCharts();
			
			//Verify Device over time chart
			softAssert.assertTrue(experienceMgmtPage.verifyTitleofChartOfExperienceMgmtPage("deviceSecurityOvertimeChart", dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "digital.exp.score.device_security_summary_chart_title")), "device Security OvertimeChart chart title did not match.");
			experienceMgmtPage.getTextOfExperienceMgmtPage("deviceSecurityScoreYaxis");
			experienceMgmtPage.getTextOfExperienceMgmtPage("deviceSecurityScoreXaxis");
			LOGGER.info("Device Security score chart is validated successfully");
	
}
     /*User Story 900138: [Core][OKR H5][WorkF][UI] Digital Experience Score Experience Management Dashboard V2*/
	
	@Test(priority = 04,enabled = false, groups = { "REGRESSIONDASHBOARD2", "SMOKE", "REGRESSION_CI","SMOKE_CI" }, description = "Test Case ID : 838914")
	public final void verifyCoachmarkOnExpMgmtPage () throws Exception {
		login("MSP_NEW_USER_EMAIL_FLEETCHART_US", "MSP_NEW_USER_PASSWORD_FLEETCHART_US");
		SoftAssert softAssert = new SoftAssert();
	    DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		ExperienceMgmtPage experienceMgmtPage = new ExperienceMgmtPage(PreDefinedActions.getDriver()).getInstance();
		dashboardPage.clickOnElementsOfDashboardPage("recommandedActionButton");
		waitForPageLoaded();
		if (!toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, "COMPANY_DES_CHART_US_EMAIL"))){
			Assert.assertTrue(setToDefaultDashboard(LanguageCode),"Error occured in setting dashboard to default mode.");
		}else{
			Assert.assertTrue(setToDefaultDashboardGlobalFilter(LanguageCode),"Error occured in setting dashboard to default mode.");
		}
		
		    dashboardPage.clickByJavaScriptOnDashboardPage("globalLocationFilterDropDown");
		    softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("filterMenuCompaniesHeader").equalsIgnoreCase(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "dashboard.actions.filter.company.label")), "Location filter header does not match");
			dashboardPage.enterTextForDashboardPage("filterMenuCompaniesSearch", globalFilterCompanyFleetChart);
			LOGGER.info("Search company to set location filter");
			waitForPageLoaded();
			dashboardPage.waitForElementsOfDashboardPage("companyOnListSearch");
			softAssert.assertTrue(dashboardPage.matchTextOfDashboardPage("companyOnListSearch", globalFilterCompanyFleetChart), "Company name and search result company name does not match");
			if (dashboardPage.verifyElementsOfDashboardPage("companyOnListSearch"))
				dashboardPage.clickOnElementsOfDashboardPage("companyOnListSearch");
			LOGGER.info("Select company to set location filter");
			sleeper(2000);
			dashboardPage.clickOnElementsOfDashboardPage("globalFilterSave");
			LOGGER.info("Global Location Filter Saved successfully.");
			waitForPageLoaded();
			gotoExperienceMgmtPageTab();
			experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("helpButton");
			experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("relaunchOption");
			LOGGER.info("Verified Relaunch option.");
			//CoachMark 1 of 5
			softAssert.assertTrue(experienceMgmtPage.verifyTextContainsOnElementsOfExperienceMgmtPage("coachMark1Text", experienceMgmtPage.getTextLanguage(LanguageCode, "daas_ui", "digitalExpScore.experienceMgmt.coachmark.tabs")), "Coachmark text 1 (1 of 5) is not present");
			experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("coachMark1NextButton");
			LOGGER.info("CoachMark1 verified successfully .");
			//CoachMark 2 of 5
			softAssert.assertTrue(experienceMgmtPage.verifyTextContainsOnElementsOfExperienceMgmtPage("coachMark2Text1", experienceMgmtPage.getTextLanguage(LanguageCode, "daas_ui", "digitalExpScore.experienceMgmt.coachmark.grid.msg1")), "Coachmark text 1 (2 of 5)is not present");
			softAssert.assertTrue(experienceMgmtPage.verifyTextContainsOnElementsOfExperienceMgmtPage("coachMark2Text2", experienceMgmtPage.getTextLanguage(LanguageCode, "daas_ui", "digitalExpScore.experienceMgmt.coachmark.grid.msg2")), "Coachmark text 2 (2 of 5)is not present");
			experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("coachMark2NextButton");
			LOGGER.info("CoachMark2 verified successfully .");
			//CoachMark 3 of 5
			softAssert.assertTrue(experienceMgmtPage.verifyTextContainsOnElementsOfExperienceMgmtPage("coachMark3Text1", experienceMgmtPage.getTextLanguage(LanguageCode, "daas_ui", "digitalExpScore.experienceMgmt.coachmark.recommendedActions.msg1 ")), "Coachmark text 1 (3 of 5) is not present");
			softAssert.assertTrue(experienceMgmtPage.verifyTextContainsOnElementsOfExperienceMgmtPage("coachMark3Text2", experienceMgmtPage.getTextLanguage(LanguageCode, "daas_ui", "digitalExpScore.experienceMgmt.coachmark.recommendedActions.msg2 ")), "Coachmark text 1 (3 of 5) is not present");
			experienceMgmtPage.scrollDownCharts();
			experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("coachMark3NextButton");
			LOGGER.info("CoachMark3 verified successfully .");
			//CoachMark 4 of 5
			softAssert.assertTrue(experienceMgmtPage.verifyTextContainsOnElementsOfExperienceMgmtPage("coachMark4Text1", experienceMgmtPage.getTextLanguage(LanguageCode, "daas_ui", "digitalExpScore.experienceMgmt.coachmark.recommendedReports.msg1")), "Coachmark text 1 (4 of 5)is not present");
			softAssert.assertTrue(experienceMgmtPage.verifyTextContainsOnElementsOfExperienceMgmtPage("coachMark4Text2", experienceMgmtPage.getTextLanguage(LanguageCode, "daas_ui", "digitalExpScore.experienceMgmt.coachmark.recommendedReports.msg2")), "Coachmark text 2 (4 of 5)is not present");
			experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("coachMark4NextButton");
			LOGGER.info("CoachMark4 verified successfully .");
			//coachMark 5 of 5
			softAssert.assertTrue(experienceMgmtPage.verifyTextContainsOnElementsOfExperienceMgmtPage("coachMark5Text1", experienceMgmtPage.getTextLanguage(LanguageCode, "daas_ui", "digitalExpScore.experienceMgmt.coachmark.trendChart.msg1")), "Coachmark text 1 (5 of 5)is not present");
			softAssert.assertTrue(experienceMgmtPage.verifyTextContainsOnElementsOfExperienceMgmtPage("coachMark5Text2", experienceMgmtPage.getTextLanguage(LanguageCode, "daas_ui", "digitalExpScore.experienceMgmt.coachmark.trendChart.msg2")), "Coachmark text 2 (5 of 5)is not present");
			experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("coachMark5CloseButton");
			LOGGER.info("All Coachmarks on dashboard are verified successfully");
		
	}		
	
	@Test(priority = 05, enabled = false, groups = { "REGRESSIONDASHBOARD2", "SMOKE", "REGRESSION_CI","SMOKE_CI" }, description = "Test Case ID : 838914")
	public final void verifyNoDataRelaunchOnExpMgmtPage() throws Exception {
		login("MSP_NEW_USER_EMAIL_FLEETCHART_US", "MSP_NEW_USER_PASSWORD_FLEETCHART_US");
		SoftAssert softAssert = new SoftAssert();
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		ExperienceMgmtPage experienceMgmtPage = new ExperienceMgmtPage(PreDefinedActions.getDriver()).getInstance();
		if (!toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, "MSP_NEW_USER_EMAIL_FLEETCHART_US"))){
			Assert.assertTrue(setToDefaultDashboard(LanguageCode),"Error occured in setting dashboard to default mode.");
		}else{
			Assert.assertTrue(setToDefaultDashboardGlobalFilter(LanguageCode),"Error occured in setting dashboard to default mode.");
		}
		gotoExperienceMgmtPageTab();
		softAssert.assertTrue(experienceMgmtPage.verifyTextContainsOnElementsOfExperienceMgmtPage("noDataExpMgmtPage1", experienceMgmtPage.getTextLanguage(LanguageCode, "daas_ui", "des_all_company_selection_msg1")), "Data is present");
		softAssert.assertTrue(experienceMgmtPage.verifyTextContainsOnElementsOfExperienceMgmtPage("noDataExpMgmtPage2", experienceMgmtPage.getTextLanguage(LanguageCode, "daas_ui", "des_all_company_selection_msg2")), "Data is present");
		experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("helpButton");
		softAssert.assertTrue(experienceMgmtPage.verifyElementsOfExperienceMgmtPage("relaunchOption"),"relaunch option is not present under help menu.");
		
		}
	
	@Test(priority = 06, enabled = false, groups = { "REGRESSIONDASHBOARD2", "SMOKE", "REGRESSION_CI","SMOKE_CI" }, description = "Test Case ID : 838914")
	public final void verifySecurityScorelocation() throws Exception {
		login("MSP_NEW_USER_EMAIL_FLEETCHART_US1", "MSP_NEW_USER_PASSWORD_FLEETCHART_US1");
		SoftAssert softAssert = new SoftAssert();
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		ExperienceMgmtPage experienceMgmtPage = new ExperienceMgmtPage(PreDefinedActions.getDriver()).getInstance();
		if (toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE,"MSP_NEW_USER_EMAIL_FLEETCHART_US")){
			String digitalExperience = dashboardPage.getTextLanguage(LanguageCode, "dashboard_service", "label.report_type_Digital_Experience");
			gotoExperienceMgmtPageTab();
			//No location Assigned option selection in location filter
			experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("globalLocationFilter");
			experienceMgmtPage.waitForElementsOfExperienceMgmtPage("companyOnListSearch");
			experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("companyOnListSearch");
			experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("nolocation");
			experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("applyCompanyLocationFilter");
			experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("deviceSecurityTab");
			experienceMgmtPage.scrollToExperienceMgmtPage("devicechartHeader");
			sleeper(2000);
			List<String> Healthgraph = experienceMgmtPage.mouseHoverOfElementsExperienceMgmtPage("graph","graphtooltip");
			//Selecting the particular location
			experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("globalLocationFilter");
			experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("clearlocation");
			experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("locationone");
			experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("locationtwo");
			experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("locationthree");
			experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("applyCompanyLocationFilter");
			waitForPageLoaded();
			sleeper(2000);
			experienceMgmtPage.scrollToExperienceMgmtPage("devicechartHeader");
			List<String> Healthgraphafterlocation = experienceMgmtPage.mouseHoverOfElementsExperienceMgmtPage("graph","graphtooltip");
			softAssert.assertTrue(!Healthgraph.equals(Healthgraphafterlocation));
			experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("globalLocationFilter");
			experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("clearalllocation");
			experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("applyCompanyLocationFilter");
			softAssert.assertAll();	
		}
		}
		@Test(priority = 07, enabled = false, groups = { "REGRESSIONDASHBOARD2", "SMOKE", "REGRESSION_CI","SMOKE_CI" }, description = "Test Case ID : 838914")
		public final void verifyHealthScorelocation() throws Exception {
		login("MSP_NEW_USER_EMAIL_FLEETCHART_US1", "MSP_NEW_USER_PASSWORD_FLEETCHART_US1");
		SoftAssert softAssert = new SoftAssert();
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		ExperienceMgmtPage experienceMgmtPage = new ExperienceMgmtPage(PreDefinedActions.getDriver()).getInstance();
		if (toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE,"MSP_NEW_USER_EMAIL_FLEETCHART_US")){
			String digitalExperience = dashboardPage.getTextLanguage(LanguageCode, "dashboard_service", "label.report_type_Digital_Experience");
			gotoExperienceMgmtPageTab();
			//No location Assigned option selection in location filter
			experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("globalLocationFilter");
			experienceMgmtPage.waitForElementsOfExperienceMgmtPage("companyOnListSearch");
			experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("companyOnListSearch");
			experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("nolocation");
			experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("applyCompanyLocationFilter");
			experienceMgmtPage.scrollDownCharts();
			//Verify Recommended Actions Section
			VerifyRecommendedActions();
			experienceMgmtPage.scrollToExperienceMgmtPage("devicechartHeader");
			sleeper(2000);
			List<String> Healthgraph = experienceMgmtPage.mouseHoverOfElementsExperienceMgmtPage("graph","graphtooltip");
			//Selecting the particular location
			experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("globalLocationFilter");
			experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("clearlocation");
			experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("locationone");
			experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("locationtwo");
			experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("locationthree");
			experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("applyCompanyLocationFilter");
			experienceMgmtPage.scrollDownCharts();
			//Verify Recommended Actions Section After Location change
			VerifyRecommendedActions();
			waitForPageLoaded();
			sleeper(2000);
			experienceMgmtPage.scrollToExperienceMgmtPage("devicechartHeader");
			List<String> Healthgraphafterlocation = experienceMgmtPage.mouseHoverOfElementsExperienceMgmtPage("graph","graphtooltip");
			softAssert.assertTrue(!Healthgraph.equals(Healthgraphafterlocation));
			experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("globalLocationFilter");
			experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("clearalllocation");
			experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("applyCompanyLocationFilter");
			softAssert.assertAll();
 			}
	}
		
		@Test(priority = 8, enabled = false, groups = { "REGRESSIONDASHBOARD2", "SMOKE", "REGRESSION_CI","SMOKE_CI" }, description = "Test Case ID : 838914")
		public final void verifyPerformanceScorelocation() throws Exception {
		login("MSP_NEW_USER_EMAIL_FLEETCHART_US1", "MSP_NEW_USER_PASSWORD_FLEETCHART_US1");
		SoftAssert softAssert = new SoftAssert();
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		ExperienceMgmtPage experienceMgmtPage = new ExperienceMgmtPage(PreDefinedActions.getDriver()).getInstance();
		if (toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE,"MSP_NEW_USER_EMAIL_FLEETCHART_US")){
			String digitalExperience = dashboardPage.getTextLanguage(LanguageCode, "dashboard_service", "label.report_type_Digital_Experience");
			gotoExperienceMgmtPageTab();
			//No location Assigned option selection in location filter
			experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("globalLocationFilter");
			experienceMgmtPage.waitForElementsOfExperienceMgmtPage("companyOnListSearch");
			experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("companyOnListSearch");
			experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("nolocation");
			experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("applyCompanyLocationFilter");
			experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("devicePerformanceTab1");
			experienceMgmtPage.scrollDownCharts();
			//Verify Recommended Actions Section
			VerifyRecommendedActions();
			experienceMgmtPage.scrollToExperienceMgmtPage("devicechartHeader");
			sleeper(2000);
			List<String> Healthgraph = experienceMgmtPage.mouseHoverOfElementsExperienceMgmtPage("graph","graphtooltip");
			//Selecting the particular location
			experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("globalLocationFilter");
			experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("clearlocation");
			experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("locationone");
			experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("locationtwo");
			experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("locationthree");
			experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("applyCompanyLocationFilter");
			experienceMgmtPage.scrollDownCharts();
			//Verify Recommended Actions Section After Location change
			VerifyRecommendedActions();
			waitForPageLoaded();
			sleeper(2000);
			experienceMgmtPage.scrollToExperienceMgmtPage("devicechartHeader");
			List<String> Healthgraphafterlocation = experienceMgmtPage.mouseHoverOfElementsExperienceMgmtPage("graph","graphtooltip");
			softAssert.assertTrue(!Healthgraph.equals(Healthgraphafterlocation));
			experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("globalLocationFilter");
			experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("clearalllocation");
			experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("applyCompanyLocationFilter");
			softAssert.assertAll();
		}
		}
 
		
		@Test(priority = 8, groups = { "REGRESSIONEXPMGMT", "SMOKE", "REGRESSION_CI","SMOKE_CI" }, description = "")
		public final void verifyHardwarehealthSummaryChart() throws Exception {
			login("COMPANY_EMAIL_REPORTS", "COMPANY_REPORT_ADMIN_PASSWORD");
			ExperienceMgmtPage experienceMgmtPage = new ExperienceMgmtPage(PreDefinedActions.getDriver()).getInstance();
			gotoExperienceMgmtPageTab();
			experienceMgmtPage.scrollToExperienceMgmtPage("osMinorVersionFilter");
			Assert.assertTrue(experienceMgmtPage.verifyDDEXDonutChart(), "Hardware Health Summary Chart is not working.");
		}
		
		
		@Test(priority = 9, enabled = true, groups = { "REGRESSIONEXPMGMT", "SMOKE", "REGRESSION_CI","SMOKE_CI" }, description = "")
		public final void verifyHardwarehealthByModelChart() throws Exception {
			login("COMPANY_EMAIL_REPORTS", "COMPANY_REPORT_ADMIN_PASSWORD");
			ExperienceMgmtPage experienceMgmtPage = new ExperienceMgmtPage(PreDefinedActions.getDriver()).getInstance();
			gotoExperienceMgmtPageTab();
			experienceMgmtPage.scrollToExperienceMgmtPage("hwHealthSummaryChartTitle");
			Assert.assertTrue(experienceMgmtPage.verifyDDEXDeviceByModelChart("deviceByModelChartBarsLocator"), "Hardware Health Summary Chart is not working.");
		}
		
		@Test(priority = 10, enabled = true, groups = { "REGRESSIONEXPMGMT", "SMOKE", "REGRESSION_CI","SMOKE_CI" }, description = "")
		public final void verifyHardwarehealthIssueslChart() throws Exception {
			login("COMPANY_EMAIL_REPORTS", "COMPANY_REPORT_ADMIN_PASSWORD");
			ExperienceMgmtPage experienceMgmtPage = new ExperienceMgmtPage(PreDefinedActions.getDriver()).getInstance();
			gotoExperienceMgmtPageTab();
			experienceMgmtPage.scrollToExperienceMgmtPage("hwHealthSummaryChartTitle");
			Assert.assertTrue(experienceMgmtPage.verifyDDEXIssuesChart(), "Hardware Health Summary Chart is not working.");
		}
		
		@Test(priority = 11, enabled = true, groups = { "REGRESSIONEXPMGMT", "SMOKE", "REGRESSION_CI","SMOKE_CI" }, description = "")
		public final void verifyPerfhealthSummaryChart() throws Exception {
			login("COMPANY_EMAIL_REPORTS", "COMPANY_REPORT_ADMIN_PASSWORD");
			ExperienceMgmtPage experienceMgmtPage = new ExperienceMgmtPage(PreDefinedActions.getDriver()).getInstance();
			gotoExperienceMgmtPageTab();
			experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("fleetPerformanceTab");
			experienceMgmtPage.scrollToExperienceMgmtPage("osMinorVersionFilter");
			Assert.assertTrue(experienceMgmtPage.verifyDDEXDonutChart(), "Hardware Health Summary Chart is not working.");
		}
		
		
		@Test(priority =12, enabled = true, groups = { "REGRESSIONEXPMGMT", "SMOKE", "REGRESSION_CI","SMOKE_CI" }, description = "")
		public final void verifyPerfhealthByModelChart() throws Exception {
			login("COMPANY_EMAIL_REPORTS", "COMPANY_REPORT_ADMIN_PASSWORD");
			ExperienceMgmtPage experienceMgmtPage = new ExperienceMgmtPage(PreDefinedActions.getDriver()).getInstance();
			gotoExperienceMgmtPageTab();
			experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("fleetPerformanceTab");
			experienceMgmtPage.scrollToExperienceMgmtPage("hwHealthSummaryChartTitle");
			Assert.assertTrue(experienceMgmtPage.verifyDDEXDeviceByModelChart("deviceByModelChartBarsLocator"), "Hardware Health Summary Chart is not working.");
		}
		
		@Test(priority = 13, enabled = true, groups = { "REGRESSIONEXPMGMT", "SMOKE", "REGRESSION_CI","SMOKE_CI" }, description = "")
		public final void verifyPerfhealthIssueslChart() throws Exception {
			login("COMPANY_EMAIL_REPORTS", "COMPANY_REPORT_ADMIN_PASSWORD");
			ExperienceMgmtPage experienceMgmtPage = new ExperienceMgmtPage(PreDefinedActions.getDriver()).getInstance();
			gotoExperienceMgmtPageTab();
			experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("fleetPerformanceTab");
			Assert.assertTrue(experienceMgmtPage.verifyDDEXIssuesChart(), "Performance Top Issues Chart is not working.");
		}
		
		@Test(priority = 14, enabled = true, groups = { "REGRESSIONEXPMGMT", "SMOKE", "REGRESSION_CI","SMOKE_CI" }, description = "")
		public final void verifySecurityhealthSummaryChart() throws Exception {
			login("COMPANY_EMAIL_REPORTS", "COMPANY_REPORT_ADMIN_PASSWORD");
			ExperienceMgmtPage experienceMgmtPage = new ExperienceMgmtPage(PreDefinedActions.getDriver()).getInstance();
			gotoExperienceMgmtPageTab();
			experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("fleetSecurityTab");
			experienceMgmtPage.scrollToExperienceMgmtPage("osMinorVersionFilter");
			Assert.assertTrue(experienceMgmtPage.verifyDDEXDonutChart(), "Hardware Health Summary Chart is not working.");
		}
		
		
		@Test(priority =15, enabled = true, groups = { "REGRESSIONEXPMGMT", "SMOKE", "REGRESSION_CI","SMOKE_CI" }, description = "")
		public final void verifySecurityhealthByModelChart() throws Exception {
			login("COMPANY_EMAIL_REPORTS", "COMPANY_REPORT_ADMIN_PASSWORD");
			ExperienceMgmtPage experienceMgmtPage = new ExperienceMgmtPage(PreDefinedActions.getDriver()).getInstance();
			gotoExperienceMgmtPageTab();
			experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("fleetSecurityTab");
			experienceMgmtPage.scrollToExperienceMgmtPage("hwHealthSummaryChartTitle");
			Assert.assertTrue(experienceMgmtPage.verifyDDEXDeviceByModelChart("deviceByModelChartBarsLocator"), "Hardware Health Summary Chart is not working.");
		}
		
		@Test(priority = 16, enabled = true, groups = { "REGRESSIONEXPMGMT", "SMOKE", "REGRESSION_CI","SMOKE_CI" }, description = "")
		public final void verifySecurityhealthIssueslChart() throws Exception {
			login("COMPANY_EMAIL_REPORTS", "COMPANY_REPORT_ADMIN_PASSWORD");
			ExperienceMgmtPage experienceMgmtPage = new ExperienceMgmtPage(PreDefinedActions.getDriver()).getInstance();
			gotoExperienceMgmtPageTab();
			experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("fleetSecurityTab");
			//experienceMgmtPage.scrollToExperienceMgmtPage("hwHealthSummaryChartTitle");
			Assert.assertTrue(experienceMgmtPage.verifyDDEXIssuesChart(), "Hardware Health Summary Chart is not working.");
		}
		
		@Test(priority = 17, enabled = true, groups = { "REGRESSIONEXPMGMT", "SMOKE", "REGRESSION_CI","SMOKE_CI" }, description = "")
		public final void verifyFiltersOnGridPage() throws Exception {
			login("COMPANY_EMAIL_REPORTS", "COMPANY_REPORT_ADMIN_PASSWORD");
			SoftAssert softassert =  new SoftAssert();
			ExperienceMgmtPage experienceMgmtPage = new ExperienceMgmtPage(PreDefinedActions.getDriver()).getInstance();
			gotoExperienceMgmtPageTab();
			experienceMgmtPage.scrollToExperienceMgmtPage("osMinorVersionFilter");
			sleeper(3000);
			experienceMgmtPage.mouseHoverbyoffsettClick("donutchartCentreText", 00, 75);
			sleeper(3000);
			experienceMgmtPage.waitUntilElementIsInvisibleOfExperienceMgmtPage("reactSkelaton");
			experienceMgmtPage.scrollToExperienceMgmtPage("serialNumberSearchBox");
			softassert.assertTrue(experienceMgmtPage.verifySearchBoxFilterDDEX("serialNumberColumnValues","serialNumberSearchBox"), "Search box filter is not working.");
			experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("clearGridFilter");
			experienceMgmtPage.waitUntilElementIsInvisibleOfExperienceMgmtPage("reactSkelaton");
			//sleeper(3000);
			softassert.assertTrue(experienceMgmtPage.verifyMultiSelectDropdownFilterDDEX("warrantyColumnValues","warrantyDropdown","warrantyDropdownValues"), "Multidropdown box filter is not working.");
			experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("clearGridFilter");
			experienceMgmtPage.waitUntilElementIsInvisibleOfExperienceMgmtPage("reactSkelaton");
			softassert.assertTrue(experienceMgmtPage.verifyComparisonRangeFilter("Less than"), "Comparison range filter is not working.");
			experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("clearGridFilter");
			experienceMgmtPage.waitUntilElementIsInvisibleOfExperienceMgmtPage("reactSkelaton");
			softassert.assertTrue(experienceMgmtPage.verifyComparisonRangeFilter("Greater than"), "Comparison range filter is not working.");
			experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("clearGridFilter");
			experienceMgmtPage.waitUntilElementIsInvisibleOfExperienceMgmtPage("reactSkelaton");
			softassert.assertTrue(experienceMgmtPage.verifyComparisonRangeFilter("Less than or equal to"), "Comparison range filter is not working.");
			experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("clearGridFilter");
			experienceMgmtPage.waitUntilElementIsInvisibleOfExperienceMgmtPage("reactSkelaton");
			softassert.assertTrue(experienceMgmtPage.verifyComparisonRangeFilter("Greater than or equal to"), "Comparison range filter is not working.");
			experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("clearGridFilter");
			softassert.assertAll();
			LOGGER.info("Filter functionality on Experience Management Grid page has been validated successfully without any issues.");
		}
		
	
		@Test(priority = 18, enabled = true, groups = { "REGRESSIONEXPMGMT", "SMOKE", "REGRESSION_CI","SMOKE_CI" }, description = "")
		public final void verifyFiltersOnExpMgmtPage() throws Exception {
			login("COMPANY_EMAIL_REPORTS", "COMPANY_REPORT_ADMIN_PASSWORD");
			SoftAssert softAssert = new SoftAssert();
			ExperienceMgmtPage experienceMgmtPage = new ExperienceMgmtPage(PreDefinedActions.getDriver()).getInstance();
			gotoExperienceMgmtPageTab();
			
			softAssert.assertTrue(experienceMgmtPage.verifyfiltersExpMgmtPage("dexRatingFilterDropdown","dexRatingFilterDropdownValues","dexRatingFilterSelectedText"), "DEX Rating filter is not working.");
			softAssert.assertTrue(experienceMgmtPage.verifyfiltersExpMgmtPage("dexIssueFilterDropdown","dexIssueFilterDropdownValues","dexIssueFilterSelectedText"), "DEX Issues filter is not working.");
			softAssert.assertTrue(experienceMgmtPage.verifyfiltersExpMgmtPage("deviceModelFilterDropdown","deviceModelFilterDropdownValues","deviceModelFilterSelectedText"), "Device Model filter is not working.");
			softAssert.assertTrue(experienceMgmtPage.verifyfiltersExpMgmtPage("memoryFilterDropdown","memoryFilterDropdownValues","memoryFilterSelectedText"), "Memory filter is not working.");
			softAssert.assertTrue(experienceMgmtPage.verifyfiltersExpMgmtPage("diskTypeFilterDropdown","diskTypeFilterDropdownValues","diskTypeFilterSelectedText"), "Disk Type filter is not working.");
			softAssert.assertTrue(experienceMgmtPage.verifyfiltersExpMgmtPage("mfgYearFilterDropdown","mfgYearFilterDropdownValues","mfgYearFilterSelectedText"), "Manufacture Year filter is not working.");
			softAssert.assertTrue(experienceMgmtPage.verifyfiltersExpMgmtPage("bornOnYearFilterDropdown","bornOnYearFilterDropdownValues","bornOnYearFilterSelectedText"), "Born On Year filter is not working.");
			softAssert.assertTrue(experienceMgmtPage.verifyfiltersExpMgmtPage("warrantyFilterDropdown","warrantyFilterDropdownValues","warrantyFilterSelectedText"), "Warranty filter is not working.");
			softAssert.assertTrue(experienceMgmtPage.verifyfiltersExpMgmtPage("deviceOSMajorFilterDropdown","deviceOSMajorFilterDropdownValues","deviceOSMajorFilterSelectedText"), "OS major Version filter is not working.");
			softAssert.assertTrue(experienceMgmtPage.verifyfiltersExpMgmtPage("deviceOSMinorFilterDropdown","deviceOSMinorFilterDropdownValues","deviceOSMinorFilterSelectedText"), "OS Minor Version filter is not working.");

			softAssert.assertAll();
			LOGGER.info("Filter functionality validation has been completed successfully.");
		}

	@Test(priority = 19, enabled = true, groups = { "REGRESSIONEXPMGMT", "SMOKE", "REGRESSION_CI","SMOKE_CI" }, description = "")
	public final void verifyRecommendedActionsTile() throws Exception {
		login("COMPANY_EMAIL_REPORTS", "COMPANY_REPORT_ADMIN_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		ExperienceMgmtPage experienceMgmtPage = new ExperienceMgmtPage(PreDefinedActions.getDriver()).getInstance();
		gotoExperienceMgmtPageTab();
		experienceMgmtPage.scrollToExperienceMgmtPage("hwHealthSummaryChartTitle");
		softAssert.assertTrue(experienceMgmtPage.verifyRecommendedActionstile(LanguageCode),"DEX Rating filter is not working on HW Tab.");
		softAssert.assertTrue(experienceMgmtPage.verifyViewDevicesButton(LanguageCode),"View Devices is not working on HW Tab.");
		experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("experienceMgmtTab");
		experienceMgmtPage.scrollUpCharts();
		experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("fleetPerformanceTab");
		softAssert.assertTrue(experienceMgmtPage.verifyRecommendedActionstile(LanguageCode),"DEX Rating filter is not working on Perf Tab.");
		softAssert.assertTrue(experienceMgmtPage.verifyViewDevicesButton(LanguageCode),"View Devices is not working on HW Tab.");
		experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("experienceMgmtTab");
		experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("fleetSecurityTab");
		softAssert.assertTrue(experienceMgmtPage.verifyRecommendedActionstile(LanguageCode),"DEX Rating filter is not working on Security Tab.");
		softAssert.assertTrue(experienceMgmtPage.verifyViewDevicesButton(LanguageCode),"View Devices is not working on HW Tab.");
		softAssert.assertAll();
		LOGGER.info("Filter functionality validation has been completed successfully.");
	}

	@Test(priority = 20, groups = { "REGRESSIONEXPMGMT", "SMOKE", "REGRESSION_CI","SMOKE_CI" }, description = "")
	public final void verifyDeviceExpMgmtDeviceDetails() throws Exception {
		login("COMPANY_EMAIL_REPORTS", "COMPANY_REPORT_ADMIN_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		ExperienceMgmtPage experienceMgmtPage = new ExperienceMgmtPage(PreDefinedActions.getDriver()).getInstance();
		gotoExperienceMgmtPageTab();
		experienceMgmtPage.scrollToExperienceMgmtPage("hwHealthSummaryChartTitle");
		Assert.assertTrue(experienceMgmtPage.verifyDeviceDetailNavigation(LanguageCode),"Device Details page did not load from Exp Mgmt Page.");
		softAssert.assertTrue((experienceMgmtPage.getTextOfExperienceMgmtPage("hwScoreText").equalsIgnoreCase(getTextLanguage(LanguageCode,"daas_ui","exp.mgmt.details.hardwareScore"))),"HW Health score text is incorrect on Device Details Page.");
		softAssert.assertTrue((experienceMgmtPage.getTextOfExperienceMgmtPage("perfScoreText").equalsIgnoreCase(getTextLanguage(LanguageCode,"daas_ui","exp.mgmt.details.performanceScore"))),"Performance score text is incorrect on Device Details Page.");
		softAssert.assertTrue((experienceMgmtPage.getTextOfExperienceMgmtPage("securityScoreText").equalsIgnoreCase(getTextLanguage(LanguageCode,"daas_ui","exp.mgmt.details.securityScore"))),"Security score text is incorrect on Device Details Page.");
		experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("collapsedRecActionsTile");
		softAssert.assertTrue(experienceMgmtPage.verifyRecommendedActionstile(LanguageCode),"Recommendation tab did not load in Device Details page.");
		softAssert.assertTrue((experienceMgmtPage.getTextOfExperienceMgmtPage("issueIndicatorsTitle").equalsIgnoreCase(getTextLanguage(LanguageCode,"daas_ui","assets_details_issueindicators_issueindicators"))),"Issue Indicators title text is incorrect on Device Details Page.");
		softAssert.assertTrue((experienceMgmtPage.getTextOfExperienceMgmtPage("issueIndicatorsSubtitle").equalsIgnoreCase(getTextLanguage(LanguageCode,"daas_ui","assets_details_issueindicators_warning"))),"Issue Indicators subtitle text is incorrect on Device Details Page.");
		softAssert.assertTrue(experienceMgmtPage.waitForElementsOfExperienceMgmtPage("issueIndicatorColumn"),"Issue Indicator column is not present in Issue Indicators table.");
		softAssert.assertTrue(experienceMgmtPage.waitForElementsOfExperienceMgmtPage("valueColumn"),"Value column is not present in Issue Indicators table.");
		softAssert.assertTrue(experienceMgmtPage.waitForElementsOfExperienceMgmtPage("recValueColumn"),"Recommended Value column is not present in Issue Indicators table.");

		softAssert.assertTrue((experienceMgmtPage.getTextOfExperienceMgmtPage("issueResolvedLastWeekTitle").equalsIgnoreCase(getTextLanguage(LanguageCode,"daas_ui","assets_details_issueresolved_issueresolved"))),"Issue Resolved Since Last week title text is incorrect on Device Details Page.");
		softAssert.assertTrue((experienceMgmtPage.getTextOfExperienceMgmtPage("issueResolvedLastWeekSubtitle").equalsIgnoreCase(getTextLanguage(LanguageCode,"daas_ui","assets_details_issueresolved_warning"))),"Issue Resolved Since Last week subtitle text is incorrect on Device Details Page.");
		softAssert.assertTrue(experienceMgmtPage.waitForElementsOfExperienceMgmtPage("issueNameColumn"),"Issue name column is not present in Issue Indicators table.");
		softAssert.assertTrue(experienceMgmtPage.waitForElementsOfExperienceMgmtPage("valueLastWeekColumn"),"Value last week column is not present in Issue Resolved Since Last week table.");
		softAssert.assertTrue(experienceMgmtPage.waitForElementsOfExperienceMgmtPage("valueThisWeekColumn"),"Value this week column is not present in Issue Resolved Since Last week table.");


		softAssert.assertAll();
		LOGGER.info("Filter functionality validation has been completed successfully.");
	}

	@Test(priority = 21, groups = { "REGRESSIONEXPMGMT", "SMOKE", "REGRESSION_CI","SMOKE_CI" }, description = "")
	public final void verifyDeviceExpMgmtExportFeature() throws Exception {
		login("COMPANY_EMAIL_REPORTS", "COMPANY_REPORT_ADMIN_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		ExperienceMgmtPage experienceMgmtPage = new ExperienceMgmtPage(PreDefinedActions.getDriver()).getInstance();
		gotoExperienceMgmtPageTab();
		Assert.assertTrue(experienceMgmtPage.verifyExpmgmtGridNavigation(LanguageCode),"Experience Management Grid page did not load successfully.");
		Assert.assertTrue(experienceMgmtPage.verifyExportFunctionality(LanguageCode),"Export functionality is not working.");
		softAssert.assertAll();
		LOGGER.info("Export of DDEX functionality validation has been completed successfully.");
	}

	@Test(priority = 19, enabled = true, groups = { "REGRESSIONEXPMGMT", "SMOKE", "REGRESSION_CI","SMOKE_CI" }, description = "")
	public final void verifyViewDevicesRecommendedActionsTile() throws Exception {
		login("COMPANY_EMAIL_REPORTS", "COMPANY_REPORT_ADMIN_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		ExperienceMgmtPage experienceMgmtPage = new ExperienceMgmtPage(PreDefinedActions.getDriver()).getInstance();
		gotoExperienceMgmtPageTab();
		experienceMgmtPage.scrollToExperienceMgmtPage("hwHealthSummaryChartTitle");
		softAssert.assertTrue(experienceMgmtPage.verifyViewDevicesButton(LanguageCode),"View Devices is not working on HW Tab.");
		experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("experienceMgmtTab");
		experienceMgmtPage.scrollUpCharts();
		experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("fleetPerformanceTab");
		softAssert.assertTrue(experienceMgmtPage.verifyViewDevicesButton(LanguageCode),"View Devices is not working on Perf Tab.");
		experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("experienceMgmtTab");
		//experienceMgmtPage.scrollToExperienceMgmtPage("experiencemgmtHeader");
		experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("fleetSecurityTab");
		softAssert.assertTrue(experienceMgmtPage.verifyViewDevicesButton(LanguageCode),"View Devices is not working on Security Tab.");
		softAssert.assertAll();
		LOGGER.info("View Devices functionality validation has been completed successfully.");
	}

    	@Test(priority = 20, groups = { "REGRESSIONEXPMGMT", "SMOKE", "REGRESSION_CI","SMOKE_CI" }, description = "")
		//write test case to validate the troubleshoot button
		public final void verifyTroubleshootButton() throws Exception {
			login("COMPANY_EMAIL_REPORTS", "COMPANY_REPORT_ADMIN_PASSWORD");
			SoftAssert softAssert = new SoftAssert();
			ExperienceMgmtPage experienceMgmtPage = new ExperienceMgmtPage(PreDefinedActions.getDriver()).getInstance();
			gotoExperienceMgmtPageTab();
			experienceMgmtPage.scrollToExperienceMgmtPage("hwHealthSummaryChartTitle");
			softAssert.assertTrue(experienceMgmtPage.verifyTroubleshootButton(LanguageCode),"Troubleshoot button is not working.");
			experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("experienceMgmtTab");
			experienceMgmtPage.scrollUpCharts();
			experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("fleetPerformanceTab");
			//experienceMgmtPage.scrollToExperienceMgmtPage("hwHealthSummaryChartTitle");
			softAssert.assertTrue(experienceMgmtPage.verifyTroubleshootButton(LanguageCode),"Troubleshoot button is not working.");
			experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("experienceMgmtTab");
			experienceMgmtPage.scrollUpCharts();
			experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("fleetSecurityTab");
			experienceMgmtPage.scrollToExperienceMgmtPage("hwHealthSummaryChartTitle");
			softAssert.assertTrue(experienceMgmtPage.verifyTroubleshootButton(LanguageCode),"Troubleshoot button is not working.");
			softAssert.assertAll();
			LOGGER.info("Troubleshoot button functionality validation has been completed successfully.");
		}

		@Test(priority = 21, groups = { "REGRESSIONEXPMGMT", "PRODUCTION_CI","SCORE_PRODUCTIONCI" }, description = "")
		public final void verifyChartsLoadingExperienceManagement() throws Exception {
			login("MSP_ADMIN_US_EMAIl_REPORTS", "MSP_ADMIN_US_PASSWORD_REPORTS");
			impersonateCompanyByNameDashboard("COMPANY_NAME_DEX", "MSP_ADMIN_US_EMAIl_REPORTS");
			SoftAssert softAssert = new SoftAssert();
			ExperienceMgmtPage experienceMgmtPage = new ExperienceMgmtPage(PreDefinedActions.getDriver()).getInstance();
			gotoExperienceMgmtPageTab();
			experienceMgmtPage.scrollToExperienceMgmtPage("hwHealthSummaryChartTitle");
			softAssert.assertTrue(experienceMgmtPage.verifyExperienceManagementChartLoading(LanguageCode,"meterchartNoDataMessage","meterChartData","HW Meter Chart"), "Meter Chart on HW Tab are is not loading on Experience Management page.");
			softAssert.assertTrue(experienceMgmtPage.verifyExperienceManagementChartLoading(LanguageCode,"summarychartNoDataMessage","summaryChartData","HW Summary Chart"), "Summary Chart on HW Tab are is not loading on Experience Management page.");
			softAssert.assertTrue(experienceMgmtPage.verifyExperienceManagementChartLoading(LanguageCode,"topIssueschartNoDataMessage","topIssuesChartData","HW Top Issues Chart"), "Top Issues Chart on HW Tab are is not loading on Experience Management page.");
			softAssert.assertTrue(experienceMgmtPage.verifyExperienceManagementChartLoading(LanguageCode,"trendChartNoDataMessage","trendChartData","HW Weekly Trend Chart"), "Weekly Trend Chart on HW Tab are is not loading on Experience Management page.");
			softAssert.assertTrue(experienceMgmtPage.verifyExperienceManagementChartLoading(LanguageCode,"modelChartNoDataMessage","modelChartData","HW Device Model Chart"), "Device Model Chart on HW Tab are is not loading on Experience Management page.");
			softAssert.assertTrue(experienceMgmtPage.verifyExperienceManagementChartLoading(LanguageCode,"recommendedActionsNoDataMessage","recommendedActionsData","HW Recommended Actions Chart"), "Recommended Actions Chart on HW Tab are is not loading on Experience Management page.");
			experienceMgmtPage.scrollUpCharts();
			experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("fleetPerformanceTab");
			softAssert.assertTrue(experienceMgmtPage.verifyExperienceManagementChartLoading(LanguageCode,"meterchartNoDataMessage","meterChartData","Perf Meter Chart"), "Meter Chart on Performance Tab is not loading on Experience Management page.");
			softAssert.assertTrue(experienceMgmtPage.verifyExperienceManagementChartLoading(LanguageCode,"summarychartNoDataMessage","summaryChartData","Perf Summary Chart"), "Summary Chart on Performance Tab is not loading on Experience Management page.");
			softAssert.assertTrue(experienceMgmtPage.verifyExperienceManagementChartLoading(LanguageCode,"topIssueschartNoDataMessage","topIssuesChartData","Perf Top Issues Chart"), "Top Issues Chart on Performance Tab is not loading on Experience Management page.");
			softAssert.assertTrue(experienceMgmtPage.verifyExperienceManagementChartLoading(LanguageCode,"trendChartNoDataMessage","trendChartData","Perf Weekly Trend Chart"), "Weekly Trend Chart on Performance Tab is not loading on Experience Management page.");
			softAssert.assertTrue(experienceMgmtPage.verifyExperienceManagementChartLoading(LanguageCode,"modelChartNoDataMessage","modelChartData","Perf Device Model Chart"), "Device Model Chart on Performance Tab is not loading on Experience Management page.");
			softAssert.assertTrue(experienceMgmtPage.verifyExperienceManagementChartLoading(LanguageCode,"recommendedActionsNoDataMessage","recommendedActionsData","Perf Recommended Actions Chart"), "Recommended Actions Chart on Performance Tab is not loading on Experience Management page.");
			experienceMgmtPage.scrollUpCharts();
			experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("fleetSecurityTab");
			softAssert.assertTrue(experienceMgmtPage.verifyExperienceManagementChartLoading(LanguageCode,"meterchartNoDataMessage","meterChartData","Security Meter Chart"), "Meter Chart on Security Tab is not loading on Experience Management page.");
			softAssert.assertTrue(experienceMgmtPage.verifyExperienceManagementChartLoading(LanguageCode,"summarychartNoDataMessage","summaryChartData","Security Summary Chart"), "Summary Chart on Security Tab is not loading on Experience Management page.");
			softAssert.assertTrue(experienceMgmtPage.verifyExperienceManagementChartLoading(LanguageCode,"topIssueschartNoDataMessage","topIssuesChartData","Security Top Issues Chart"), "Top Issues Chart on Security Tab is not loading on Experience Management page.");
			softAssert.assertTrue(experienceMgmtPage.verifyExperienceManagementChartLoading(LanguageCode,"trendChartNoDataMessage","trendChartData","Security Weekly Trend Chart"), "Weekly Trend Chart on Security Tab is not loading on Experience Management page.");
			softAssert.assertTrue(experienceMgmtPage.verifyExperienceManagementChartLoading(LanguageCode,"modelChartNoDataMessage","modelChartData","Security Device Model Chart"), "Device Model Chart on Security Tab is not loading on Experience Management page.");
			softAssert.assertTrue(experienceMgmtPage.verifyExperienceManagementChartLoading(LanguageCode,"recommendedActionsNoDataMessage","recommendedActionsData","Security Recommended Actions Chart"), "Recommended Actions Chart on Security Tab is not loading on Experience Management page.");
			experienceMgmtPage.scrollUpCharts();
			softAssert.assertAll();
			LOGGER.info("Experience management Charts loading functionality validation has been completed successfully.");
		}

	@Test(priority = 21,enabled = false, groups = { "REGRESSIONEXPMGMT", "PRODUCTION_CI","SCORE_PRODUCTIONCI" }, description = "")
	public final void verifyGridLoadingExperienceManagement() throws Exception {
		login("MSP_ADMIN_US_EMAIl_REPORTS", "MSP_ADMIN_US_PASSWORD_REPORTS");
		impersonateCompanyByNameDashboard("COMPANY_NAME_DEX", "MSP_ADMIN_US_EMAIl_REPORTS");
		SoftAssert softAssert = new SoftAssert();
		ExperienceMgmtPage experienceMgmtPage = new ExperienceMgmtPage(PreDefinedActions.getDriver()).getInstance();
		gotoExperienceMgmtPageTab();
		Assert.assertTrue(experienceMgmtPage.verifyExpmgmtGridNavigation(LanguageCode),"Experience Management Grid page did not load successfully.");
		softAssert.assertFalse(experienceMgmtPage.waitForElementsOfExperienceMgmtPage("expmgmtGridTableNoData"),"Grid does not have any data.");
		softAssert.assertAll();
		LOGGER.info("Experience management Grid loading functionality validation has been completed successfully.");
	}
	@Test(priority = 22, groups = { "REGRESSIONEXPMGMT"}, description = "")
	public final void verifyWeeklyTrendChartHW() throws Exception {
		login("COMPANY_EMAIL_REPORTS", "COMPANY_REPORT_ADMIN_PASSWORD");
		//login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		ExperienceMgmtPage experienceMgmtPage = new ExperienceMgmtPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		gotoExperienceMgmtPageTab();
		experienceMgmtPage.scrollToExperienceMgmtPage("hwHealthSummaryChartTitle");
		softAssert.assertTrue(experienceMgmtPage.verifyWeeklyTrendChart(),"Weekly Trend Chart is not working for HW Health");
		experienceMgmtPage.scrollUpCharts();
		experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("fleetPerformanceTab");
		experienceMgmtPage.scrollToExperienceMgmtPage("hwHealthSummaryChartTitle");
		softAssert.assertTrue(experienceMgmtPage.verifyWeeklyTrendChart(),"Weekly Trend Chart is not working for Performance");
		experienceMgmtPage.scrollUpCharts();
		experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("fleetSecurityTab");
		experienceMgmtPage.scrollToExperienceMgmtPage("hwHealthSummaryChartTitle");
		softAssert.assertTrue(experienceMgmtPage.verifyWeeklyTrendChart(),"Weekly Trend Chart is not working for Security");
		softAssert.assertAll();
		LOGGER.info("Weekly Trend Chart(HW Health) functionality validation has been completed successfully.");
	}

	@Test(priority = 23, groups = { "REGRESSIONEXPMGMT"}, description = "")
	public final void verifyColumnHeadersExpHeaders() throws Exception {
		login("COMPANY_EMAIL_REPORTS", "COMPANY_REPORT_ADMIN_PASSWORD");
		final String[] HW_Health_Coulmn = {"asset_details_serialNumber","assets.table.heading.alias_name","assets.table.heading.last_signed_user","exp.mgmt.details.warrantyState","exp.mgmt.fleetOverTime.health.title.y","exp.mgmt.details.diskHealth",
		"exp.mgmt.details.batteryHealth","exp.mgmt.details.batteryFcc","exp.mgmt.details.fanSpeed","exp.mgmt.details.gpuHealth","exp.mgmt.details.gpuIssues","exp.mgmt.details.lastOnlineDate","exp.mgmt.details.mfgYr","exp.mgmt.details.diskreplacementreason","exp.mgmt.details.performanceScore",
		"exp.mgmt.details.avgCpuUtilization","exp.mgmt.details.avgMemUtilization","exp.mgmt.details.diskStorageUtilization","exp.mgmt.details.osCrashes","exp.mgmt.details.bsodIssues","exp.mgmt.details.avgStartUp","exp.mgmt.details.avgShutDown","exp.mgmt.details.softwareErrors","exp.mgmt.details.dockingStatus","exp.mgmt.details.dockingHealth","exp.mgmt.details.missingDrivers","exp.mgmt.details.biosStatus","exp.mgmt.details.criticalBiosUpdates","exp.mgmt.details.batteryHealthSetting",
		"exp.mgmt.details.lastRestartDate","exp.mgmt.details.missingUpdates","exp.mgmt.details.securityScore","exp.mgmt.details.missingSecurityUpdate","exp.mgmt.details.secureBootStatus","exp.mgmt.details.antivirusStatus","exp.mgmt.details.antivirusSignStatus","exp.mgmt.details.avPolicyValue","exp.mgmt.details.firewallStatus","exp.mgmt.details.firewallSignStatus","exp.mgmt.details.encryptionStatus","benchmark.details.table.device_model","security.table.heading.deviceManufacturer","asset_detail.hardware_memory",
		"exp.mgmt.details.cpuProcessor","exp.mgmt.details.gpuProcessor","exp.mgmt.details.deviceOs","exp.mgmt.details.osBuildNumber","exp.mgmt.details.bornOnYear"};
		final String[] perf_Coulmn = {"asset_details_serialNumber","assets.table.heading.alias_name","assets.table.heading.last_signed_user","exp.mgmt.details.warrantyState","exp.mgmt.details.performanceScore","exp.mgmt.details.avgCpuUtilization","exp.mgmt.details.avgMemUtilization","exp.mgmt.details.diskStorageUtilization","exp.mgmt.details.osCrashes","exp.mgmt.details.bsodIssues","exp.mgmt.details.avgStartUp","exp.mgmt.details.avgShutDown","exp.mgmt.details.softwareErrors","exp.mgmt.details.dockingStatus",
				"exp.mgmt.details.dockingHealth","exp.mgmt.details.missingDrivers","exp.mgmt.details.biosStatus","exp.mgmt.details.criticalBiosUpdates","exp.mgmt.details.batteryHealthSetting","exp.mgmt.details.lastRestartDate","exp.mgmt.details.missingUpdates","exp.mgmt.details.lastOnlineDate","exp.mgmt.details.mfgYr","exp.mgmt.fleetOverTime.health.title.y","exp.mgmt.details.diskHealth","exp.mgmt.details.diskreplacementreason","exp.mgmt.details.batteryHealth","exp.mgmt.details.batteryFcc","exp.mgmt.details.fanSpeed",
				"exp.mgmt.details.gpuHealth","exp.mgmt.details.gpuIssues","exp.mgmt.details.securityScore","exp.mgmt.details.missingSecurityUpdate","exp.mgmt.details.secureBootStatus","exp.mgmt.details.antivirusStatus","exp.mgmt.details.antivirusSignStatus","exp.mgmt.details.avPolicyValue","exp.mgmt.details.firewallStatus","exp.mgmt.details.firewallSignStatus","exp.mgmt.details.encryptionStatus","benchmark.details.table.device_model","security.table.heading.deviceManufacturer","asset_detail.hardware_memory",
				"exp.mgmt.details.cpuProcessor","exp.mgmt.details.gpuProcessor","exp.mgmt.details.deviceOs","exp.mgmt.details.osBuildNumber","exp.mgmt.details.bornOnYear"};
		final String[] security_Coulmn = {"asset_details_serialNumber","assets.table.heading.alias_name","assets.table.heading.last_signed_user","exp.mgmt.details.warrantyState","exp.mgmt.details.securityScore","exp.mgmt.details.missingSecurityUpdate","exp.mgmt.details.secureBootStatus","exp.mgmt.details.antivirusStatus","exp.mgmt.details.antivirusSignStatus","exp.mgmt.details.avPolicyValue","exp.mgmt.details.firewallStatus","exp.mgmt.details.firewallSignStatus","exp.mgmt.details.encryptionStatus","exp.mgmt.details.lastOnlineDate",
				"exp.mgmt.details.mfgYr","exp.mgmt.fleetOverTime.health.title.y","exp.mgmt.details.diskHealth","exp.mgmt.details.diskreplacementreason","exp.mgmt.details.batteryHealth","exp.mgmt.details.batteryFcc","exp.mgmt.details.fanSpeed","exp.mgmt.details.gpuHealth","exp.mgmt.details.gpuIssues","exp.mgmt.details.performanceScore","exp.mgmt.details.avgCpuUtilization","exp.mgmt.details.avgMemUtilization","exp.mgmt.details.diskStorageUtilization","exp.mgmt.details.osCrashes","exp.mgmt.details.bsodIssues",
				"exp.mgmt.details.avgStartUp","exp.mgmt.details.avgShutDown","exp.mgmt.details.softwareErrors","exp.mgmt.details.dockingStatus","exp.mgmt.details.dockingHealth","exp.mgmt.details.missingDrivers","exp.mgmt.details.biosStatus","exp.mgmt.details.criticalBiosUpdates","exp.mgmt.details.batteryHealthSetting","exp.mgmt.details.lastRestartDate","exp.mgmt.details.missingUpdates","benchmark.details.table.device_model","security.table.heading.deviceManufacturer","asset_detail.hardware_memory","exp.mgmt.details.cpuProcessor",
				"exp.mgmt.details.gpuProcessor","exp.mgmt.details.deviceOs","exp.mgmt.details.osBuildNumber","exp.mgmt.details.bornOnYear"};

		ExperienceMgmtPage experienceMgmtPage = new ExperienceMgmtPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		gotoExperienceMgmtPageTab();
		Assert.assertTrue(experienceMgmtPage.verifyExpmgmtGridNavigation(LanguageCode),"Experience Management Grid page did not load successfully.");
		resetTableConfiguration();
		softAssert.assertTrue(experienceMgmtPage.verifyHeaderExpMgmtGridPage(LanguageCode,"columnListGridPage",HW_Health_Coulmn),"Column headers are not correct for HW Health");
		gotoExperienceMgmtPageTab();
		experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("fleetPerformanceTab");
		Assert.assertTrue(experienceMgmtPage.verifyExpmgmtGridNavigation(LanguageCode),"Experience Management Grid page did not load successfully.");
		resetTableConfiguration();
		softAssert.assertTrue(experienceMgmtPage.verifyHeaderExpMgmtGridPage(LanguageCode,"columnListGridPage",perf_Coulmn),"Column headers are not correct for Performance");
		gotoExperienceMgmtPageTab();
		experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("fleetSecurityTab");
		Assert.assertTrue(experienceMgmtPage.verifyExpmgmtGridNavigation(LanguageCode),"Experience Management Grid page did not load successfully.");
		resetTableConfiguration();
		softAssert.assertTrue(experienceMgmtPage.verifyHeaderExpMgmtGridPage(LanguageCode,"columnListGridPage",security_Coulmn),"Column headers are not correct for Security");
		softAssert.assertAll();
		LOGGER.info("Experience management Grid Header validation has been completed successfully.");
	}

	@Test(priority = 24, groups = { "REGRESSIONEXPMGMT" }, description = "")
	public final void verifySummaryTabRemovalExperienceManagement() throws Exception {
		login("MSP_ADMIN_US_EMAIl_REPORTS", "MSP_ADMIN_US_PASSWORD_REPORTS");
		impersonateCompanyByNameDashboard("COMPANY_NAME_DEX", "MSP_ADMIN_US_EMAIl_REPORTS");
		SoftAssert softAssert = new SoftAssert();
		ExperienceMgmtPage experienceMgmtPage = new ExperienceMgmtPage(PreDefinedActions.getDriver()).getInstance();
		gotoExperienceMgmtPageTab();
		softAssert.assertFalse(experienceMgmtPage.verifyElementsOfExperienceMgmtPage("summaryTab"),"Summary tab is visible for MSP/Partner.");
		softAssert.assertTrue(experienceMgmtPage.verifyElementsOfExperienceMgmtPage("hwHealthTab"),"Hardware tab is not loading in Experience management tab.");
		softAssert.assertTrue(experienceMgmtPage.verifyElementsOfExperienceMgmtPage("perfTab"),"Performance tab is not loading in Experience management tab.");
		softAssert.assertTrue(experienceMgmtPage.verifyElementsOfExperienceMgmtPage("securityTab"),"Security tab is not loading in Experience management tab.");
		softAssert.assertAll();
		LOGGER.info("Tabs validation completed successfully in Experience Management page.");
	}

	@Test(priority = 25, groups = { "REGRESSIONEXPMGMT" }, description = "")
	public final void verifyChangesSinceLastWeek() throws Exception {
		login("COMPANY_EMAIL_REPORTS", "COMPANY_REPORT_ADMIN_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		ExperienceMgmtPage experienceMgmtPage = new ExperienceMgmtPage(PreDefinedActions.getDriver()).getInstance();
		gotoExperienceMgmtPageTab();
		softAssert.assertTrue(experienceMgmtPage.getTextOfExperienceMgmtPage("changesSinceLastWeekChartTitle").equalsIgnoreCase(experienceMgmtPage.getTextLanguage(LanguageCode, "daas_ui", "exp.mgmt.changessincelastweek.title")));
		experienceMgmtPage.scrollToExperienceMgmtPage("hwHealthSummaryChartTitle");
		softAssert.assertTrue(experienceMgmtPage.verifyChangesSinceLastWeek(),"Changes Since Last Week is not working for HW Health");
		experienceMgmtPage.scrollUpCharts();
		experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("fleetPerformanceTab");
		experienceMgmtPage.scrollToExperienceMgmtPage("hwHealthSummaryChartTitle");
		softAssert.assertTrue(experienceMgmtPage.verifyChangesSinceLastWeek(),"Changes Since Last Week is not working for Performance");
		experienceMgmtPage.scrollUpCharts();
		experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("fleetSecurityTab");
		experienceMgmtPage.scrollToExperienceMgmtPage("hwHealthSummaryChartTitle");
		softAssert.assertTrue(experienceMgmtPage.verifyChangesSinceLastWeek(),"Changes Since Last Week is not working for Security");
		softAssert.assertAll();
		LOGGER.info("Changes Since Last Week functionality validation has been completed successfully.");
	}

	@Test(priority = 26, groups = { "REGRESSIONEXPMGMT" }, description = "")
	public final void verifyDashboardExpMgmt() throws Exception {

		login("COMPANY_EMAIL_REPORTS", "COMPANY_REPORT_ADMIN_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		ExperienceMgmtPage experienceMgmtPage = new ExperienceMgmtPage(PreDefinedActions.getDriver()).getInstance();
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		Assert.assertTrue(experienceMgmtPage.waitForElementsOfExperienceMgmtPage("hwHeathChartDashboard")," Hardware Health Score Card did not load on Dashboard.");
		Assert.assertTrue(experienceMgmtPage.waitForElementsOfExperienceMgmtPage("perfChartDashboard")," Performance Health Score Card did not load on Dashboard.");
		Assert.assertTrue(experienceMgmtPage.waitForElementsOfExperienceMgmtPage("secChartDashboard")," Security Health Score Card did not load on Dashboard.");
		Assert.assertTrue(experienceMgmtPage.verifydexChartDashboardNavigation("hwHealthExpandbutton","fleetHardwareTab")," Experience Management page (HW Tab) did not load successfully when navigated from Dashboard.");
		gotoDashboardTab();
		experienceMgmtPage.waitUntilElementIsInvisibleOfExperienceMgmtPage("reactSkelaton");
		Assert.assertTrue(experienceMgmtPage.verifydexChartDashboardNavigation("perfExpandbutton","fleetPerformanceTab")," Experience Management page (Performance Tab) did not load successfully when navigated from Dashboard.");
		gotoDashboardTab();
		experienceMgmtPage.waitUntilElementIsInvisibleOfExperienceMgmtPage("reactSkelaton");
		Assert.assertTrue(experienceMgmtPage.verifydexChartDashboardNavigation("secExpandbutton","fleetSecurityTab")," Experience Management page (Security Tab) did not load successfully when navigated from Dashboard.");
		LOGGER.info("Validation of Experience Management page from Dashboard has been completed successfully.");
	}


	@Test(priority = 27, enabled = true, groups = { "REGRESSIONEXPMGMT", "SMOKE", "REGRESSION_CI","SMOKE_CI" }, description = "")
	public final void verifyViewDevicesRecommendedActionsTileDashboard() throws Exception {
		login("COMPANY_EMAIL_REPORTS", "COMPANY_REPORT_ADMIN_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		ExperienceMgmtPage experienceMgmtPage = new ExperienceMgmtPage(PreDefinedActions.getDriver()).getInstance();
		softAssert.assertTrue(experienceMgmtPage.verifyViewDevicesButton(LanguageCode),"View Devices is not working on HW Tab.");
		gotoDashboardTab();
		experienceMgmtPage.waitUntilElementIsInvisibleOfExperienceMgmtPage("reactSkelaton");
		softAssert.assertTrue(experienceMgmtPage.verifyTroubleshootButton(LanguageCode),"Troubleshoot button is not working.");
		softAssert.assertAll();
		LOGGER.info("View Devices functionality validation has been completed successfully.");
	}


	@Test(priority = 28, groups = { "REGRESSIONEXPMGMT" }, description = "Test Case 1326696")
	public final void verifyFleetScorePreferenceTileHWHealth() throws Exception {
		login("COMPANY_EMAIL_REPORTS", "COMPANY_REPORT_ADMIN_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		ExperienceMgmtPage experienceMgmtPage = new ExperienceMgmtPage(PreDefinedActions.getDriver()).getInstance();
		PreferencesPage preferencesPage = new PreferencesPage(PreDefinedActions.getDriver()).getInstance();
		LogPage logPage = new LogPage(PreDefinedActions.getDriver()).getInstance();
		experienceMgmtPage.scrollToExperienceMgmtPage("reportAdminSettingsTab");
		gotoCompanySettingsTab();
		preferencesPage.goToPreferencesTab();
		experienceMgmtPage.scrollDownCharts();
		softAssert.assertTrue(experienceMgmtPage.getTextOfExperienceMgmtPage("fleetScoreTitle").equalsIgnoreCase(experienceMgmtPage.getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.fleetscorepreferences")),"Fleet Score Preference tile title is not correct.");
		softAssert.assertTrue(experienceMgmtPage.verifyFleetScorePreferenceTile(LanguageCode,"editIconHwHealthTile"),"Fleet Score Preference tile is not working correctly for Hardware Health.");
		gotoExperienceMgmtPageTab();
		sleeper(3000);
		softAssert.assertTrue(experienceMgmtPage.getTextOfExperienceMgmtPage("bannerNotification1").contains("Hardware Health's scoring preference was updated"),"Notification message is not coming on Exp mgmt page after Fleet Score Preference update.");
		softAssert.assertTrue(experienceMgmtPage.waitForElementsOfExperienceMgmtPage("gearIcon"),"Settings gear icon is not present on Experience management page.");
		softAssert.assertTrue(experienceMgmtPage.verifyClickAndValidateElement("gearIcon","fleetScorePreferences"),"Settings page did not load successfully from Experience Management page.");
		gotoDashboardTab();
		sleeper(3000);
		softAssert.assertTrue(experienceMgmtPage.getTextOfExperienceMgmtPage("bannerNotification1").contains("Hardware Health's scoring preference was updated"),"Notification message is not coming on Dashboard page after Fleet Score Preference update.");
		experienceMgmtPage.scrollDownCharts();
		experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("expmgmtDashboardEllipse");
		softAssert.assertTrue(experienceMgmtPage.verifyClickAndValidateElement("settingsIconDashboard","fleetScorePreferences"),"Settings page did not load successfully from Experience Management page.");
		gotoLogTab();
		waitForPageLoaded();
		Assert.assertTrue(logPage.matchTextOfLogPage("logTitleOnBreadcrumbs", getTextLanguage(LanguageCode, "daas_ui", "pagetitle.logs")), "Logs page navigation failed");
		LOGGER.info("Redirected to logs page");
		resetTableConfiguration();
		// Verify device name,type and sub type filter text
		softAssert.assertTrue(logPage.matchTextOfLogPage("typeBoxText", "Experience Management"), "Selected Type column filter is not Devices");
		softAssert.assertTrue(logPage.matchTextOfLogPage("subtypeBoxText", "DEX Score Modification"), "Selected Subtype column filter is not Enrollment");
		LOGGER.info("Verified device name,type and sub type filter text on logs page");
		softAssert.assertAll();
		LOGGER.info("Fleet Score Preference tile title validation has been completed successfully.");
	}

	@Test(priority = 29, groups = { "REGRESSIONEXPMGMT" }, description = "Test Case 1326696")
	public final void verifyFleetScorePreferenceTileHPerf() throws Exception {
		login("COMPANY_EMAIL_REPORTS", "COMPANY_REPORT_ADMIN_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		ExperienceMgmtPage experienceMgmtPage = new ExperienceMgmtPage(PreDefinedActions.getDriver()).getInstance();
		PreferencesPage preferencesPage = new PreferencesPage(PreDefinedActions.getDriver()).getInstance();
		LogPage logPage = new LogPage(PreDefinedActions.getDriver()).getInstance();
		experienceMgmtPage.scrollToExperienceMgmtPage("reportAdminSettingsTab");
		gotoCompanySettingsTab();
		preferencesPage.goToPreferencesTab();
		experienceMgmtPage.scrollDownCharts();
		softAssert.assertTrue(experienceMgmtPage.getTextOfExperienceMgmtPage("fleetScoreTitle").equalsIgnoreCase(experienceMgmtPage.getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.fleetscorepreferences")),"Fleet Score Preference tile title is not correct.");
		softAssert.assertTrue(experienceMgmtPage.verifyFleetScorePreferenceTile(LanguageCode,"editIconPerfTile"),"Fleet Score Preference tile is not working correctly for Hardware Health.");
		gotoExperienceMgmtPageTab();
		sleeper(3000);
		softAssert.assertTrue(experienceMgmtPage.getTextOfExperienceMgmtPage("bannerNotification2").contains("Performance's scoring preference was updated"),"Notification message is not coming on Exp mgmt page after Fleet Score Preference update.");
		softAssert.assertTrue(experienceMgmtPage.waitForElementsOfExperienceMgmtPage("gearIcon"),"Settings gear icon is not present on Experience management page.");
		softAssert.assertTrue(experienceMgmtPage.verifyClickAndValidateElement("gearIcon","fleetScorePreferences"),"Settings page did not load successfully from Experience Management page.");
		gotoDashboardTab();
		sleeper(3000);
		softAssert.assertTrue(experienceMgmtPage.getTextOfExperienceMgmtPage("bannerNotification2").contains("Performance's scoring preference was updated"),"Notification message is not coming on Dashboard page after Fleet Score Preference update.");
		experienceMgmtPage.scrollDownCharts();
		experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("expmgmtDashboardEllipse");
		softAssert.assertTrue(experienceMgmtPage.verifyClickAndValidateElement("settingsIconDashboard","fleetScorePreferences"),"Settings page did not load successfully from Experience Management page.");
		gotoLogTab();
		waitForPageLoaded();
		Assert.assertTrue(logPage.matchTextOfLogPage("logTitleOnBreadcrumbs", getTextLanguage(LanguageCode, "daas_ui", "pagetitle.logs")), "Logs page navigation failed");
		LOGGER.info("Redirected to logs page");
		resetTableConfiguration();
		// Verify device name,type and sub type filter text
		softAssert.assertTrue(logPage.matchTextOfLogPage("typeBoxText", "Experience Management"), "Selected Type column filter is not Devices");
		softAssert.assertTrue(logPage.matchTextOfLogPage("subtypeBoxText", "DEX Score Modification"), "Selected Subtype column filter is not Enrollment");
		LOGGER.info("Verified device name,type and sub type filter text on logs page");
		softAssert.assertAll();
		LOGGER.info("Fleet Score Preference tile title validation has been completed successfully.");
	}

	@Test(priority = 30, groups = { "REGRESSIONEXPMGMT" }, description = "Test Case 1326696")
	public final void verifyFleetScorePreferenceTileHSecurity() throws Exception {
		login("COMPANY_EMAIL_REPORTS", "COMPANY_REPORT_ADMIN_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		ExperienceMgmtPage experienceMgmtPage = new ExperienceMgmtPage(PreDefinedActions.getDriver()).getInstance();
		PreferencesPage preferencesPage = new PreferencesPage(PreDefinedActions.getDriver()).getInstance();
		LogPage logPage = new LogPage(PreDefinedActions.getDriver()).getInstance();
		experienceMgmtPage.scrollToExperienceMgmtPage("reportAdminSettingsTab");
		gotoCompanySettingsTab();
		preferencesPage.goToPreferencesTab();
		experienceMgmtPage.scrollDownCharts();
		softAssert.assertTrue(experienceMgmtPage.getTextOfExperienceMgmtPage("fleetScoreTitle").equalsIgnoreCase(experienceMgmtPage.getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.fleetscorepreferences")),"Fleet Score Preference tile title is not correct.");
		softAssert.assertTrue(experienceMgmtPage.verifyFleetScorePreferenceTile(LanguageCode,"editIconSecTile"),"Fleet Score Preference tile is not working correctly for Hardware Health.");
		gotoExperienceMgmtPageTab();
		sleeper(3000);
		softAssert.assertTrue(experienceMgmtPage.getTextOfExperienceMgmtPage("bannerNotification3").contains("Security's scoring preference was updated"),"Notification message is not coming on Exp mgmt page after Fleet Score Preference update.");
		softAssert.assertTrue(experienceMgmtPage.waitForElementsOfExperienceMgmtPage("gearIcon"),"Settings gear icon is not present on Experience management page.");
		softAssert.assertTrue(experienceMgmtPage.verifyClickAndValidateElement("gearIcon","fleetScorePreferences"),"Settings page did not load successfully from Experience Management page.");
		gotoDashboardTab();
		sleeper(3000);
		softAssert.assertTrue(experienceMgmtPage.getTextOfExperienceMgmtPage("bannerNotification3").contains("Security's scoring preference was updated"),"Notification message is not coming on Dashboard page after Fleet Score Preference update.");
		experienceMgmtPage.scrollDownCharts();
		experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("expmgmtDashboardEllipse");
		softAssert.assertTrue(experienceMgmtPage.verifyClickAndValidateElement("settingsIconDashboard","fleetScorePreferences"),"Settings page did not load successfully from Experience Management page.");
		gotoLogTab();
		waitForPageLoaded();
		Assert.assertTrue(logPage.matchTextOfLogPage("logTitleOnBreadcrumbs", getTextLanguage(LanguageCode, "daas_ui", "pagetitle.logs")), "Logs page navigation failed");
		LOGGER.info("Redirected to logs page");
		resetTableConfiguration();
		// Verify device name,type and sub type filter text
		softAssert.assertTrue(logPage.matchTextOfLogPage("typeBoxText", "Experience Management"), "Selected Type column filter is not Devices");
		softAssert.assertTrue(logPage.matchTextOfLogPage("subtypeBoxText", "DEX Score Modification"), "Selected Subtype column filter is not Enrollment");
		LOGGER.info("Verified device name,type and sub type filter text on logs page");
		softAssert.assertAll();
		LOGGER.info("Fleet Score Preference tile title validation has been completed successfully.");
	}

}



