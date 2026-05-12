package com.testscripts.daasui;
import java. util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.basesource.action.PreDefinedActions;
import com.basesource.utils.LaunchDarkly;
import com.daasui.constants.ConstantPath;
import com.daasui.constants.ConstantURL;
import com.daasui.constants.DashboardVariables;
import com.daasui.constants.DeviceVariables;
import com.daasui.pages.CompaniesDetailsPage;
import com.daasui.pages.CompaniesPage;
import com.daasui.pages.DashboardPage;
import com.daasui.pages.DeviceDetailsPage;
import com.daasui.pages.DeviceListPage;
import com.daasui.pages.IncidentDetailsPage;
import com.daasui.pages.IncidentPage;
import com.daasui.pages.PreConfiguredDashboardPage;
import com.daasui.pages.RoomsDetailsPage;
import com.daasui.pages.RoomsListPage;
import com.daasui.pages.SettingsPage;
import com.github.openjson.JSONArray;

public class DashboardTest extends CommonTest {
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
	public static final String biosTemplate = "Deploy bios update HP BIOS and System Firmware (S21) 02.14.00";
	public static final String biosTemplateDeviceName = "DESKTOP-F03AQFL";
	
	
	@DataProvider
	public Object[][] getLoginData() {
		Object[][] data = new Object[2][2];
		data[0][0] = "MSP_ADMIN_US_EMAIl_REPORTS";
		data[0][1] = "MSP_ADMIN_US_PASSWORD_REPORTS";
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
	public Object[][] getLoginDataForRetireBatteryDiskKpiChartFeature() {
		Object[][] data = new Object[2][2];
		data[0][0] = "MSP_ADMIN_EMAIl_CASE";
		data[0][1] = "MSP_ADMIN_PASSWORD_CASE";
		data[1][0] = "IT_ADMIN_EMAIl_CASE_AC";
		data[1][1] = "IT_ADMIN_PASSWORD_CASE_AC";
		return data;
	}
	@DataProvider
	public Object[][] getLoginDataForDigitalExpFeature() {
		Object[][] data = new Object[2][2];
		data[0][0] = "MSP_NEW_USER_EMAIL_FLEETCHART_US";
		data[0][1] = "MSP_NEW_USER_PASSWORD_FLEETCHART_US";
		data[1][0] = "COMPANY_DES_CHART_US_EMAIL";
		data[1][1] = "COMPANY_DES_CHART_US_PASSWORD";
		return data;
	}
	@DataProvider
	public Object[][] getLoginDataForAVWidget() {
		Object[][] data = new Object[2][2];
		data[0][0] = "MSP_EMAIL_AV_WIDGE";
		data[0][1] = "MSP_PASSWPORD_AV_WIDGET";
		data[1][0] = "COMPANY_EMAIL_AV_WIDGET";
		data[1][1] = "COMPANY_PASSWORD_AV_WIDGET";
		return data;
	}
	@Test(priority = 1, groups = { "REGRESSION", "REGRESSION_CI", "SMOKE", "ALL_CI", "ALL", "REGRESSIONDASHBOARD1" })
	public final void verifyDashboardTitle() throws Exception {
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		
		if (toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, "MSP_ADMIN_US_EMAIl"))) {
			dashboardPage.waitForElementsOfDashboardPage("dashboardTitleOnBreadcrumbs");
			Assert.assertTrue(dashboardPage.getTextOfDashboardPage("dashboardTitleOnBreadcrumbs").equals(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "pagetitle.dashboard")), "Dashboard title text is incorrect");
			LOGGER.info("Dashboard title is matched");
		}else{
		Assert.assertTrue(dashboardPage.getTextOfDashboardPage("dashboardTitle").equals(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "pagetitle.dashboard")), "Dashboard title text is incorrect");
		LOGGER.info("Dashboard title is matched");
		}
	}

	@Test(priority = 2)
	public final void verifyDashboardChartsLoading() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();

		softAssert.assertFalse(dashboardPage.verifyChartisLoadedDynamic(LanguageCode, "deviceByTypeChart", "fatalErrorDeviceByType", 5), "DEVICES BY TYPE Chart did not load successfully.");
		softAssert.assertFalse(dashboardPage.verifyChartisLoadedDynamic(LanguageCode, "deviceByOsChart", "fatalErrorDeviceByOs", 5), "DEVICES BY OS Chart did not load successfully.");
		softAssert.assertFalse(dashboardPage.verifyChartisLoadedDynamic(LanguageCode, "warrantyExpirationChart", "warrantyExpirationFatalError", 5), "Warranty Expiration Chart did not load successfully.");
		softAssert.assertFalse(dashboardPage.verifyChartisLoadedDynamic(LanguageCode, "batteryReplacementSummaryChart", "batteryReplacementSummaryFatalError", 5), "Battery Replacement Summary Chart did not load successfully.");
		softAssert.assertFalse(dashboardPage.verifyChartisLoadedDynamic(LanguageCode, "diskReplacementSummaryChart", "batteryReplacementSummaryFatalError", 5), "Disk Replacement Summary Chart did not load successfully.");
		softAssert.assertFalse(dashboardPage.verifyChartisLoadedDynamic(LanguageCode, "cpuUtlizationChart", "fatalErrorCPUUtlization", 5), "CPU Utlization Chart did not load successfully.");
		softAssert.assertFalse(dashboardPage.verifyChartisLoadedDynamic(LanguageCode, "memoryUtlizationChart", "fatalErrorMemoryUtlization", 5), "Memory Utlization Chart did not load successfully.");
		softAssert.assertFalse(dashboardPage.verifyChartisLoadedDynamic(LanguageCode, "hardwareInventoryChart", "fatalErrorHardwareInventory", 5), "Hardware Inventory Chart did not load successfully.");
		softAssert.assertFalse(dashboardPage.verifyChartisLoadedDynamic(LanguageCode, "softwareInventoryChart", "fatalErrorSoftwareInventory", 5), "Software Inventory Chart did not load successfully.");
		softAssert.assertFalse(dashboardPage.verifyChartisLoadedDynamic(LanguageCode, "subscriptionExpirationChart", "subscriptionExpirationFatalError", 5), "Subscription Expiration Chart did not load successfully.");
		softAssert.assertFalse(dashboardPage.verifyChartisLoadedDynamic(LanguageCode, "openIncidentsChart", "fatalErrorOpenIncidents", 5), "OPEN INCIDENTS Chart did not load successfully.");
		softAssert.assertFalse(dashboardPage.verifyChartisLoadedDynamic(LanguageCode, "allIncidentsChart", "fatalErrorAllIncidents", 5), "OPEN INCIDENTS Chart did not load successfully.");
		softAssert.assertFalse(dashboardPage.verifyChartisLoadedDynamic(LanguageCode, "topTenIncidentsChart", "fatalErrorTopTenIncidents", 5), "TOP 10 INCIDENTS Chart did not load successfully.");
		softAssert.assertFalse(dashboardPage.verifyChartisLoadedDynamic(LanguageCode, "osVersionSupportChart", "fatalOsVersionSupport", 5), "Os Version Support Chart did not load successfully.");
		softAssert.assertAll();
		LOGGER.info("Validation of Dashboard charts load completed successfully.");

	}

	@Test(priority = 3, groups = { "REGRESSION", "REGRESSION_CI", "SMOKE", "PRODUCTION_CI", "ALL_CI", "ALL", "REGRESSIONDASHBOARD1","INSIGHTS_PRODUCTIONCI"})
	public final void verifyChartSequenceforMSP() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		String chartIdsArray[] ={"fleetOverview0","batteryRepIncidentKpi0","diskRepIncidentKpi0","networkSpeed0","sevendayslowspeedtends0","outdatedNetworkCardDriver0","networkOutagesWeeklySummary0","diskCapacityIncidentKpi0","thermalGradingIncidentKpi0","scadashboard0","sureSenseProThreatsDashboard0","sureSenseProDeviceDashboard0","osSupportSummary0","deviceType0","deviceOS0","win11UpgradeStatusKpi0","hwwarByDeviceWarranty0","subsexpirybyterm0","summaryByCpuUtiDashboard0","summaryByMemUtiDashboard0","hwinvByMonth0","diskRepSummary0","allIncidentByType0","burnDownSummary0","topApplicationSummary0","todayCriticalIncident0","driverByStatus0","driverByCriticalityKpi0","windowsUpdatesCriticalSummary0","windowsUpdatesSecuritySummary0","windowsUpdatesSummary0","msOfficeUpdatesCriticalSummary0","msOfficeUpdatesSecuritySummary0","msOfficeUpdatesSummary0"};

		Assert.assertTrue(setToDefaultDashboardGlobalFilter(LanguageCode),"Error occured in setting dashboard to default mode.");
		waitForPageLoaded();
		softAssert.assertTrue(dashboardPage.verifyChartOrderOfDashBoardPageForMSPFlexi("allChartsLocatorFlexi", chartIdsArray), "Chart Sequence for MSP is not correct");
		softAssert.assertAll();
			LOGGER.info("Validation of Chart's sequence for MSP completed successfully");


	}

	@Test(priority = 4, groups = { "REGRESSION", "REGRESSION_CI", "SMOKE", "SMOKE_CI", "ALL_CI", "ALL", "REGRESSIONDASHBOARD1" })
	public final void verifyChartSequenceforReseller() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login("RESELLER_NEW_UI_EMAIL_US", "RESELLER_NEW_UI_PASSWORD_US");
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		String chartIdsArray[] ={ "diskCapacityIncidentKpi0","thermalGradingIncidentKpi0","scadashboard0","sureSenseProThreatsDashboard0","sureSenseProDeviceDashboard0","osSupportSummary0","deviceType0","deviceOS0","win11UpgradeStatusKpi0","win11Compatibility0","hwwarByDeviceWarranty0","subsexpirybyterm0","summaryByCpuUtiDashboard0","summaryByMemUtiDashboard0","hwinvByMonth0","diskRepSummary0","batteryswellprobByAge0","allIncidentByType0","burnDownSummary0","topApplicationSummary0","todayCriticalIncident0","driverByStatus0","driverByCriticalityKpi0","windowsUpdatesDashboard0","windowsDashboard0","officeUpdatesDashboard0","officeDashboard0","windowsUpdatesCriticalSummary0","windowsUpdatesSecuritySummary0","windowsUpdatesSummary0"};
		String chartIdsNetworkArray[] ={ "networkSpeed0","sevendayslowspeedtends0","outdatedNetworkCardDriver0","networkOutagesWeeklySummary0","diskCapacityIncidentKpi0","thermalGradingIncidentKpi0","scadashboard0","sureSenseProThreatsDashboard0","sureSenseProDeviceDashboard0","osSupportSummary0","deviceType0","deviceOS0","win11UpgradeStatusKpi0","win11Compatibility0", "hwwarByDeviceWarranty0","subsexpirybyterm0","summaryByCpuUtiDashboard0","summaryByMemUtiDashboard0","hwinvByMonth0","diskRepSummary0","allIncidentByType0","burnDownSummary0","topApplicationSummary0","todayCriticalIncident0","driverByStatus0","driverByCriticalityKpi0","windowsUpdatesDashboard0","windowsDashboard0","officeUpdatesDashboard0","officeDashboard0","windowsUpdatesCriticalSummary0","windowsUpdatesSecuritySummary0","windowsUpdatesSummary0"};

		if (!toggleVerification(DashboardVariables.FLEXIDASHBOARD, getCredentials(environment, "RESELLER_NEW_UI_EMAIL_US"))) {
			LOGGER.info("Validation of Chart's sequence for Reseller started");
			// Test case 1
			Assert.assertFalse(dashboardPage.verifyChartisLoadedDynamic(LanguageCode, "deviceByTypeChart", "fatalErrorDeviceByType", 5), "DEVICES BY TYPE Chart did not load successfully.");
			softAssert.assertTrue(dashboardPage.verifyChartOrderOfDashBoardPageForReseller("allChartsLocator"), "Chart Sequence for Reseller is not correct");
			softAssert.assertAll();
			LOGGER.info("Validation Chart's sequence for Reseller completed successfully");
		}else{
			if (!toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, "RESELLER_NEW_UI_EMAIL_US"))){
				Assert.assertTrue(setToDefaultDashboard(LanguageCode),"Error occured in setting dashboard to default mode.");
			}else{
				Assert.assertTrue(setToDefaultDashboardGlobalFilter(LanguageCode),"Error occured in setting dashboard to default mode.");
			}
			waitForPageLoaded();
			 if (!toggleVerification(DashboardVariables.NETWORKHEALTHANDOUTAGE, getCredentials(environment, "RESELLER_NEW_UI_EMAIL_US")))
				softAssert.assertTrue(dashboardPage.verifyChartOrderOfDashBoardPageForResellerFlexi("allChartsLocatorFlexi", chartIdsArray), "Chart Sequence for Reseller is not correct");
			else
				softAssert.assertTrue(dashboardPage.verifyChartOrderOfDashBoardPageForResellerFlexi("allChartsLocatorFlexi", chartIdsNetworkArray), "Chart Sequence for Reseller is not correct");

			softAssert.assertAll();
			LOGGER.info("Validation Chart's sequence for Reseller completed successfully");
		}
	}

	@Test(priority = 5, groups = { "REGRESSION", "REGRESSION_CI", "SMOKE", "SMOKE_CI", "ALL_CI", "ALL", "REGRESSIONDASHBOARD1" })
	public final void verifyChartSequenceforReportAdmin() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		//sleeper(200000);
		login("REPORT_ADMIN_STAGING_DASHBOARD_EMAIL", "REPORT_ADMIN_STAGING_DASHBOARD_PASSWORD");
		
		String chartIdsArray[] ={ "diskCapacityIncidentKpi0","thermalGradingIncidentKpi0","scadashboard0","sureSenseProThreatsDashboard0","sureSenseProDeviceDashboard0","osSupportSummary0","deviceType0","deviceOS0","hwwarByDeviceWarranty0","subsexpirybyterm0","summaryByCpuUtiDashboard0","summaryByMemUtiDashboard0","hwinvByMonth0","diskRepSummary0","allIncidentByType0","burnDownSummary0","topApplicationSummary0","todayCriticalIncident0","driverByStatus0","driverByCriticalityKpi0","windowsUpdatesDashboard0","windowsDashboard0","officeUpdatesDashboard0","officeDashboard0"};
		String chartIdsNetworkArray[] ={ "networkSpeed0","sevendayslowspeedtends0","outdatedNetworkCardDriver0","networkOutagesWeeklySummary0","diskCapacityIncidentKpi0","thermalGradingIncidentKpi0","osSupportSummary0","deviceType0","deviceOS0","win11UpgradeStatusKpi0","win11Compatibility0","hwwarByDeviceWarranty0","subsexpirybyterm0","summaryByCpuUtiDashboard0","summaryByMemUtiDashboard0","hwinvByMonth0","diskRepSummary0","allIncidentByType0","burnDownSummary0","topApplicationSummary0","todayCriticalIncident0","driverByStatus0","driverByCriticalityKpi0","windowsUpdatesDashboard0","windowsDashboard0","officeUpdatesDashboard0","officeDashboard0","windowsUpdatesCriticalSummary0","windowsUpdatesSecuritySummary0","windowsUpdatesSummary0"};

		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();

		if (!toggleVerification(DashboardVariables.FLEXIDASHBOARD, getCredentials(environment, "REPORT_ADMIN_STAGING_DASHBOARD_EMAIL"))) {
			LOGGER.info("Validation of Chart's sequence for ReportAdmin started");
			// Test case 1
			Assert.assertFalse(dashboardPage.verifyChartisLoadedDynamic(LanguageCode, "deviceByTypeChart", "fatalErrorDeviceByType", 5), "DEVICES BY TYPE Chart did not load successfully.");
			softAssert.assertTrue(dashboardPage.verifyChartOrderOfDashBoardPageForReportAdmin("allChartsLocator"), "Chart Sequence for ReportAdmin is not correct");
			softAssert.assertAll();
			LOGGER.info("Validation of Chart's sequence for ReportAdmin completed successfully");
		}else{
			if (!toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, "REPORT_ADMIN_STAGING_DASHBOARD_EMAIL"))){
				Assert.assertTrue(setToDefaultDashboard(LanguageCode),"Error occured in setting dashboard to default mode.");
			}else{
				Assert.assertTrue(setToDefaultDashboardGlobalFilter(LanguageCode),"Error occured in setting dashboard to default mode.");
			}
			waitForPageLoaded();
			if (!toggleVerification(DashboardVariables.NETWORKHEALTHANDOUTAGE, getCredentials(environment, "REPORT_ADMIN_STAGING_DASHBOARD_EMAIL")))
				softAssert.assertTrue(dashboardPage.verifyChartOrderOfDashBoardPageForReportAdminFlexi("allChartsLocatorFlexi", chartIdsArray), "Chart Sequence for Report Admin is not correct");
			else
				softAssert.assertTrue(dashboardPage.verifyChartOrderOfDashBoardPageForReportAdminFlexi("allChartsLocatorFlexi", chartIdsNetworkArray), "Chart Sequence for Report Admin is not correct");

			softAssert.assertAll();
			LOGGER.info("Validation of Chart's sequence for ReportAdmin completed successfully");
		}

	}

	@Test(priority = 6, groups = { "REGRESSION", "REGRESSION_CI", "ALL_CI", "ALL", "REGRESSIONDASHBOARD1", "STABILIZATION_STAGING" }, dataProvider = "getLoginData", description = "[199587],[178113],[178100],[178102],[178104],[178108],[178106],[178109],[178112],[198910],[213239][309379] ; Roles ~ MSP, Reseller")
	public final void verifyChartDevicesByTypeForPartner(String username, String password) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		ArrayList<String> DEVICES_BY_TYPE_CHART_LABELS_LIST = new ArrayList<>(Arrays.asList("global.device_type.notebook", "global.device_type.tablet", "global.device_type.smartphone", "global.device_type.desktop", "global.device_type.other", "global.device_type.all_in_one", "global.device_type.phablet"));
		final String[] DASHBOARD_CHART_DEVICE_TYPE_OS_HW_REPORT_PAGE = { "Global.gridHeaders.companyName", "gridHeaders.Hardware_Inventory.Details.allData.lastSignedinUser", "Global.gridHeaders.enrolledmonth", "Global.gridHeaders.deviceName", "Global.gridHeaders.serialNumber", "Global.gridHeaders.deviceType", "Global.gridHeaders.deviceManufacturer", "Global.gridHeaders.deviceModel", "Global.gridHeaders.operatingSystem", "gridHeaders.hardware_Inventory.Details.operating_system_release",
				"gridHeaders.hardware_Inventory.Details.operating_system_Build_number", "gridHeaders.Hardware_Inventory.Details.win10_patch_status", "Global.gridHeaders.lastSeen", "Global.gridHeaders.memory", "gridHeaders.Hardware_Inventory.Details.graphics", "Global.gridHeaders.processor", "Global.gridHeaders.manufacture_date", "gridHeaders.hardware_Inventory.Details.enrolledDate", "gridHeaders.Incident_Widget.Details.warStatus", "gridHeaders.hardware_Inventory.Details.warrantyEndDate",
				"Global.gridHeaders.country", "Global.gridHeaders.department", "Global.gridHeaders.costCenter", "Global.gridHeaders.alias", "Global.gridHeaders.asset_tag", "graphHeaders.Hardware_Inventory.Details.lifecycle_status", "graphHeaders.Hardware_Inventory.Details.device_role", "graphHeaders.Hardware_Inventory.Details.component", "graphHeaders.Hardware_Inventory.Details.component_serial_number",
				"graphHeaders.Hardware_Inventory.Details.component_repl_timeframe", "graphHeaders.Hardware_Inventory.Details.component_Model", "graphHeaders.Hardware_Inventory.Details.component_size_gb","Global.gridHeaders.location_1","Global.gridHeaders.location_2","Global.gridHeaders.location_3","Global.gridHeaders.location_4" };
		login(username, password);
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();

		if (!toggleVerification(DashboardVariables.FLEXIDASHBOARD, getCredentials(environment, username))) {

			// Verify toggle status
			Assert.assertTrue(toggleVerification(DashboardVariables.DEVICESBYTYPE, username), "LD-Toggle is disabled for DEVICES BY TYPE chart");
			LOGGER.info("Verified LD-Toggle status");

			// Reset current configuration of dashboard chart
			dashboardPage.clearDashboardCompanyFilter();
			dashboardPage.verifyResetToDefaultButton();
			dashboardPage.waitForPageLoaded();
			dashboardPage.scrollToDashboardPage("deviceByTypeTitle"); 
			LOGGER.info("Scrolled down to DEVICES BY TYPE chart");

			dashboardPage.waitForPageLoaded();
			// Test Case 1 (This Test case validates title of the chart.)
			softAssert.assertTrue(dashboardPage.verifyTitleofChartOfDashboardPage("deviceByTypeTitle", dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.devices_by_type")), "DEVICES BY TYPE Chart title did not match.");

			// Test Case 2 (This test case validate whether chart is loaded or not.)
			Assert.assertFalse(dashboardPage.verifyChartisLoadedDynamic(LanguageCode, "deviceByTypeChart", "fatalErrorDeviceByType", 5), "DEVICES BY TYPE Chart did not load successfully.");

			// Test Case 3 (This test case validate whether chart has any data or not.)
			Assert.assertFalse(dashboardPage.verifyChartsHasNoDataOnDashBoardPage(LanguageCode, "noDataDeviceByType", "dashboard.charts.title.devices_by_type"), "DEVICES BY TYPE Chart does not have any data.");
			Assert.assertFalse(dashboardPage.verifyChartsHasNoDataDevicesEnrolledOnDashBoardPage(LanguageCode, "noDataDeviceByType", "dashboard.charts.title.devices_by_type"), "DEVICES BY TYPE Chart does not have any data.");

			// Test Case 4 (This test case validates the text of tool tip present on all sub
			// parts of chart with text on Report page.)
			softAssert.assertTrue(dashboardPage.verifyTooltipTextOnReportWithFrame("tooltipHoverDeviceByType", "tooltipTextDeviceByType", "deviceByTypeTextLocator"), "Tooltip text of DEVICES BY TYPE chart did match with reports");

			// Test case 6 (This test case validates the header of reports page.)
			softAssert.assertTrue(dashboardPage.headerTextVerificationOnReportPageFrame(LanguageCode, "deviceByTypeXaxisLabels", "headerListLocatorOnDeviceByTypeReportPage", DASHBOARD_CHART_DEVICE_TYPE_OS_HW_REPORT_PAGE, "frameLocator"), "Report header for DEVICES BY TYPE chart did not match on report page.");

			// Test case 7 (This test case validates the labels of the chart. )
			softAssert.assertTrue(dashboardPage.verifyChartLabelsDashboardPage("deviceByTypeXaxisLabels", dashboardPage.getTextLanguage(LanguageCode, "daas_ui", DEVICES_BY_TYPE_CHART_LABELS_LIST)), "Labels of DEVICES BY TYPE chart did not match");

			// Test case 8 (This test case validates no data message on chart.)
			dashboardPage.scrollUpCharts();
			dashboardPage.clickOnElementsOfDashboardPage("companyDropdown");
			softAssert.assertTrue(dashboardPage.verifyCompanyChangeOfDashboardPage(LanguageCode, "companySearchBox", DashboardVariables.DASHBOARD_CHART_COMPANY_NAME_MSP_US, "companyEmptyText", "companyList", "dropdownBoxCompany"), "Company Change is not working correctly");
			softAssert.assertTrue(dashboardPage.verifyChartsHasNoDataOnDashBoardPage(LanguageCode, "noDataDeviceByType", "dashboard.charts.title.devices_by_type"), "No data message for DEVICES BY TYPE Chart is incorrect.");
			dashboardPage.clickOnElementsOfDashboardPage("clearCompany");
			dashboardPage.waitForPageLoaded();

			// Test case 9 (This test case validates redirection to device details page from
			// Report page.)
			Assert.assertFalse(dashboardPage.verifyChartisLoadedDynamic(LanguageCode, "deviceByTypeChart", "fatalErrorDeviceByType", 5), "DEVICES BY TYPE Chart did not load successfully.");
			softAssert.assertTrue(dashboardPage.verifyDeviceNameRedirectionMSPWithFrame(LanguageCode, "deviceByTypeXaxisLabels", "deviceDetailsTitle", "deviceNameColumnDeviceByType", "errorLocatorForNoDevice", "frameLocator"), "Device Name Redirection is not working properly for DEVICE BY TYPE chart");

			softAssert.assertAll();
			LOGGER.info("Validation of DEVICES BY TYPE Chart completed successfully");
		} else {
			if (!toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, username))){
				Assert.assertTrue(setToDefaultDashboard(LanguageCode),"Error occured in setting dashboard to default mode.");
			}else{
				Assert.assertTrue(setToDefaultDashboardGlobalFilter(LanguageCode),"Error occured in setting dashboard to default mode.");
			}

			// Reset current configuration of dashboard chart
			dashboardPage.waitForPageLoaded();
			sleeper(50000);
			dashboardPage.scrollToDashboardPage("deviceByTypeTitleFlexi");
			LOGGER.info("Scrolled down to DEVICES BY TYPE chart");

			dashboardPage.waitForPageLoaded();
			// Test Case 1 (This Test case validates title of the chart.)
			softAssert.assertTrue(dashboardPage.verifyTitleofChartOfDashboardPage("deviceByTypeTitleFlexi", dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.devices_by_type")), "DEVICES BY TYPE Chart title did not match.");

			// Test Case 2 (This test case validate whether chart is loaded or not.)
			Assert.assertFalse(dashboardPage.verifyChartisLoadedDynamic(LanguageCode, "deviceByTypeChartFlexi", "fatalErrorDeviceByType", 5), "DEVICES BY TYPE Chart did not load successfully.");

			// Test Case 3 (This test case validate whether chart has any data or not.)
			Assert.assertFalse(dashboardPage.verifyChartsHasNoDataOnDashBoardPageFlexi(LanguageCode, "noDataDeviceByTypeFlexi", "dashboard.charts.title.devices_by_type"), "DEVICES BY TYPE Chart does not have any data.");

			// Test Case 4 (This test case validates the text of tool tip present on all sub
			// parts of chart with text on Report page.)
			softAssert.assertTrue(dashboardPage.verifyTooltipTextOnReportWithFrame("tooltipHoverDeviceByTypeFlexi", "tooltipTextDeviceByTypeFlexi", "deviceByTypeTextLocator"), "Tooltip text of DEVICES BY TYPE chart did match with reports");

			// Test case 6 (This test case validates the header of reports page.)
			softAssert.assertTrue(dashboardPage.headerTextVerificationOnReportPageFrame(LanguageCode, "tooltipHoverDeviceByTypeFlexi", "headerListLocatorOnDeviceByTypeReportPage", DASHBOARD_CHART_DEVICE_TYPE_OS_HW_REPORT_PAGE, "frameLocator"), "Report header for DEVICES BY TYPE chart did not match on report page.");

			// Test case 7 (This test case validates the labels of the chart. )
			softAssert.assertTrue(dashboardPage.verifyChartLabelsDashboardPage("deviceByTypeXaxisLabelsFlexi", dashboardPage.getTextLanguage(LanguageCode, "daas_ui", DEVICES_BY_TYPE_CHART_LABELS_LIST)), "Labels of DEVICES BY TYPE chart did not match");
			}
			// Test case 8 (This test case validates no data message on chart.)
			//else {
			dashboardPage.scrollUpCharts();
			if (!toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, username)))
			{
				dashboardPage.clickOnElementsOfDashboardPage("companyDropdownFlexi");
				softAssert.assertTrue(dashboardPage.verifyCompanyChangeOfDashboardPage(LanguageCode, "companySearchBoxFlexi", DashboardVariables.DASHBOARD_CHART_COMPANY_NAME_MSP_US, "companyEmptyText", "companyList", "dropdownBoxCompanyFlexi"), "Company Change is not working correctly");
				softAssert.assertTrue(dashboardPage.verifyChartsHasNoDataOnDashBoardPageFlexi(LanguageCode, "noDataDeviceByTypeFlexi", "dashboard.charts.title.devices_by_type"), "No data message for DEVICES BY TYPE Chart is incorrect.");
				dashboardPage.clickOnElementsOfDashboardPage("clearCompanyFlexi");
			}
			else
			{
				dashboardPage.clickOnElementsOfDashboardPage("companyDropdownGlobalFilter");
				softAssert.assertTrue(dashboardPage.verifyCompanyChangeOfDashboardPageGlobalFilter(LanguageCode, "companySearchGlobalFilter", DashboardVariables.DASHBOARD_CHART_COMPANY_NAME_MSP_US, "companyEmptyTextGlobalFilter", "companyListGlobalFilter", "outputTextGlobalFilter","globalFilterSave"), "Company Change is not working correctly");
				softAssert.assertTrue(dashboardPage.verifyChartsHasNoDataOnDashBoardPageFlexi(LanguageCode, "noDataDeviceByTypeFlexi", "dashboard.charts.title.devices_by_type"), "No data message for DEVICES BY TYPE Chart is incorrect.");										
				dashboardPage.clickOnElementsOfDashboardPage("companyDropdownGlobalFilter");
				dashboardPage.clickOnElementsOfDashboardPage("clearGlobalFilter");
				dashboardPage.clickOnElementsOfDashboardPage("globalFilterSave");
				
			}

			dashboardPage.waitForPageLoaded();
			// Test case 9 (This test case validates redirection to device details page from
			// Report page.)
			Assert.assertFalse(dashboardPage.verifyChartisLoadedDynamic(LanguageCode, "deviceByTypeChartFlexi", "fatalErrorDeviceByType", 5), "DEVICES BY TYPE Chart did not load successfully.");
			softAssert.assertTrue(dashboardPage.verifyDeviceNameRedirectionMSPWithFrame(LanguageCode, "tooltipHoverDeviceByTypeFlexi", "deviceDetailsTitle", "deviceNameColumnDeviceByType", "errorLocatorForNoDevice", "reportLayout"), "Device Name Redirection is not working properly for DEVICE BY TYPE chart");

			softAssert.assertAll();
			LOGGER.info("Validation of DEVICES BY TYPE Chart completed successfully");

		}
	

	@Test(priority = 7, groups = { "REGRESSION", "REGRESSION_CI", "ALL_CI", "ALL", "REGRESSIONDASHBOARD1"}, description = "[199636],[177999],[178093],[178005],[178098],[178092],[191191],[177998],[178099],[177996],[213236] ; Roles ~ MSP, Reseller", enabled = false)	
		public final void verifyChartDevicesByOSForPartner() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		final String[] DASHBOARD_CHART_DEVICE_TYPE_OS_HW_REPORT_PAGE = { "Global.gridHeaders.companyName", "gridHeaders.Hardware_Inventory.Details.allData.lastSignedinUser", "Global.gridHeaders.enrolledmonth", "Global.gridHeaders.deviceName", "Global.gridHeaders.serialNumber", "Global.gridHeaders.deviceType", "Global.gridHeaders.deviceManufacturer", "Global.gridHeaders.deviceModel", "Global.gridHeaders.operatingSystem", "gridHeaders.hardware_Inventory.Details.operating_system_release",
				"gridHeaders.hardware_Inventory.Details.operating_system_Build_number", "gridHeaders.Hardware_Inventory.Details.win10_patch_status", "Global.gridHeaders.lastSeen", "Global.gridHeaders.memory", "gridHeaders.Hardware_Inventory.Details.graphics", "Global.gridHeaders.processor", "Global.gridHeaders.manufacture_date", "gridHeaders.hardware_Inventory.Details.enrolledDate", "gridHeaders.Incident_Widget.Details.warStatus", "gridHeaders.hardware_Inventory.Details.warrantyEndDate",
				"Global.gridHeaders.country", "Global.gridHeaders.department", "Global.gridHeaders.costCenter", "Global.gridHeaders.alias", "Global.gridHeaders.asset_tag", "graphHeaders.Hardware_Inventory.Details.lifecycle_status", "graphHeaders.Hardware_Inventory.Details.device_role", "graphHeaders.Hardware_Inventory.Details.component", "graphHeaders.Hardware_Inventory.Details.component_serial_number",
				"graphHeaders.Hardware_Inventory.Details.component_repl_timeframe", "graphHeaders.Hardware_Inventory.Details.component_Model", "graphHeaders.Hardware_Inventory.Details.component_size_gb","Global.gridHeaders.location_1","Global.gridHeaders.location_2","Global.gridHeaders.location_3","Global.gridHeaders.location_4" };
		login("RESELLER_NEW_UI_EMAIL_US", "RESELLER_NEW_UI_PASSWORD_US");
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();

		if (!toggleVerification(DashboardVariables.FLEXIDASHBOARD, getCredentials(environment, "RESELLER_NEW_UI_EMAIL_US"))) {
			// Verify toggle status
			Assert.assertTrue(toggleVerification(DashboardVariables.DEVICESBYOS, "RESELLER_NEW_UI_EMAIL_US"), "LD-Toggle is disabled for DEVICES BY OS chart");
			LOGGER.info("Verified LD-Toggle status");

			// Reset current configuration of dashboard chart
			dashboardPage.clearDashboardCompanyFilter();
			dashboardPage.verifyResetToDefaultButton();
			dashboardPage.waitForPageLoaded();
			dashboardPage.scrollToDashboardPage("devicesByOsTitle");
			LOGGER.info("Scrolled down to DEVICES BY OS chart");

			dashboardPage.waitForPageLoaded();
			HashMap<String, String> DeviceByOsInfo = dashboardPage.getDeviceByOsDetails();

			// Test Case 1 (This Test case validates title of the chart.)
			softAssert.assertTrue(dashboardPage.verifyTitleofChartOfDashboardPage("devicesByOsTitle", dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.devices_by_os")), "DEVICES BY OS Chart title did not match.");

			// Test Case 2 (This test case validate whether chart is loaded or not.)
			Assert.assertFalse(dashboardPage.verifyChartisLoadedDynamic(LanguageCode, "deviceByOsChart", "fatalErrorDeviceByOs", 5), "DEVICES BY OS Chart did not load successfully.");

			// Test Case 3 (This test case validate whether chart has any data or not.)
			Assert.assertFalse(dashboardPage.verifyChartsHasNoDataOnDashBoardPage(LanguageCode, "noDataDeviceByOs", "dashboard.charts.title.devices_by_os"), "DEVICES BY OS Chart does not have any data.");
			Assert.assertFalse(dashboardPage.verifyChartsHasNoDataDevicesEnrolledOnDashBoardPage(LanguageCode, "noDataDeviceByOs", "dashboard.charts.title.devices_by_os"), "DEVICES BY OS Chart does not have any data.");

			// Test case 4 (This test case validates the header column data at report page.)
			softAssert.assertTrue(dashboardPage.verifyHeaderColumnOnReportPageWithFrame(LanguageCode, "headerListLocatorOnReportPage", DASHBOARD_CHART_DEVICE_TYPE_OS_HW_REPORT_PAGE, DeviceByOsInfo), "Report text of DEVICES BY OS chart did match with reports");

			softAssert.assertAll();
			LOGGER.info("Validation of DEVICES BY OS Chart completed successfully");
		}else{
			if (!toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, "RESELLER_NEW_UI_EMAIL_US"))){
				Assert.assertTrue(setToDefaultDashboard(LanguageCode),"Error occured in setting dashboard to default mode.");
			}else{
				Assert.assertTrue(setToDefaultDashboardGlobalFilter(LanguageCode),"Error occured in setting dashboard to default mode.");
			}
			dashboardPage.scrollToDashboardPage("devicesByOsTitleFlexi");
			LOGGER.info("Scrolled down to DEVICES BY OS chart");

			dashboardPage.waitForPageLoaded();
			HashMap<String, String> DeviceByOsInfo = dashboardPage.getDeviceByOsDetails();

			// Test Case 1 (This Test case validates title of the chart.)
			softAssert.assertTrue(dashboardPage.verifyTitleofChartOfDashboardPage("devicesByOsTitleFlexi", dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.devices_by_os")), "DEVICES BY OS Chart title did not match.");

			// Test Case 2 (This test case validate whether chart is loaded or not.)
			Assert.assertFalse(dashboardPage.verifyChartisLoadedDynamic(LanguageCode, "deviceByOsChartFlexi", "fatalErrorDeviceByOs", 5), "DEVICES BY OS Chart did not load successfully.");

			// Test Case 3 (This test case validate whether chart has any data or not.)
			Assert.assertFalse(dashboardPage.verifyChartsHasNoDataOnDashBoardPageFlexi(LanguageCode, "noDataDeviceByOsFlexi", "dashboard.charts.title.devices_by_os"), "DEVICES BY OS Chart does not have any data.");

			// Test case 4 (This test case validates the header column data at report page.)
			softAssert.assertTrue(dashboardPage.verifyDeviceByOSReportHeaderWithFrameFlexi(LanguageCode, "headerListLocatorOnReportPage", DASHBOARD_CHART_DEVICE_TYPE_OS_HW_REPORT_PAGE, DeviceByOsInfo, "deviceDetailsTitle", "deviceNameColumnDeviceByType", "errorLocatorForNoDevice"), "Report text of DEVICES BY OS chart did match with reports");

			softAssert.assertAll();
			LOGGER.info("Validation of DEVICES BY OS Chart completed successfully");
		}
	}

	@Test(priority = 8, groups = { "REGRESSION", "REGRESSION_CI", "ALL_CI", "ALL", "REGRESSIONDASHBOARD1" }, dataProvider = "getLoginData", description = "[199636],[177999],[178093],[178005],[178098],[178092],[191191],[177998],[178099],[177996],[213236] ; Roles ~ MSP, Reseller", enabled = false)
	public final void verifyChartDevicesByOSTooltipTextCountForPartner(String username, String password) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login(username, password);
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();

		if (!toggleVerification(DashboardVariables.FLEXIDASHBOARD, getCredentials(environment, username))) {
			// Verify toggle status
			Assert.assertTrue(toggleVerification(DashboardVariables.DEVICESBYOS, username), "LD-Toggle is disabled for DEVICES BY OS chart");
			LOGGER.info("Verified LD-Toggle status");

			// Reset current configuration of dashboard chart
			dashboardPage.clearDashboardCompanyFilter();
			dashboardPage.verifyResetToDefaultButton();
			dashboardPage.waitForPageLoaded();
			LOGGER.info("Scrolled down to DEVICES BY OS chart");

			// Test case 3 (This test case validates no data message on chart.)
			dashboardPage.clickOnElementsOfDashboardPage("companyDropdown");
			softAssert.assertTrue(dashboardPage.verifyCompanyChangeOfDashboardPage(LanguageCode, "companySearchBox", DashboardVariables.DASHBOARD_CHART_COMPANY_NAME_MSP_US, "companyEmptyText", "companyList", "dropdownBoxCompany"), "Company Change is not working correctly");
			softAssert.assertTrue(dashboardPage.verifyChartsHasNoDataOnDashBoardPage(LanguageCode, "noDataDeviceByOs", "dashboard.charts.title.devices_by_os"), "No data message for DEVICES BY OS Chart is incorrect.");
			dashboardPage.waitForPageLoaded();
			dashboardPage.clickOnElementsOfDashboardPage("clearCompany");
			sleeper(3000);
			// Test Case 1 (This test case validate whether chart is loaded or not.)
			Assert.assertFalse(dashboardPage.verifyChartisLoadedDynamic(LanguageCode, "deviceByOsChart", "fatalErrorDeviceByOs", 5), "DEVICES BY OS Chart did not load successfully.");

			// Test case 4 (This test case validates the text,count and version of tool tip
			// present on all sub parts of chart with text,count and version on Report
			// page.)
			softAssert.assertTrue(dashboardPage.verifyTooltipTextCountAndVersionOnReportWithFrame(LanguageCode, "devicesByOsTitleFlexi", "deviceByOSLegends", "tooltipTextDeviceByOsFlexi", "tooltipTextDeviceByOsMajorVersionFlexi", "tooltipTextDeviceByOsMajorVersionNameFlexi", "tooltipTextDeviceByOsCountFlexi", "reportCountDeviceByOs", "levelsBackFunctionalityForDevicesByOsFlexi", "deviceByOsTextLocator", "deviceByOsReleaseTextLocator", "moreDetailsLink", "frameLocator", "deviceByOSLegendsHidden",
					"deviceByOSLegendsVisible", "labelsLocatorDeviceByOsFlexi"), "Tooltip text of DEVICES BY OS chart did match with reports");
			softAssert.assertAll();
			LOGGER.info("Validation of DEVICES BY OS Chart completed successfully");
		}else{
			if (!toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, username))){
				Assert.assertTrue(setToDefaultDashboard(LanguageCode),"Error occured in setting dashboard to default mode.");
			}else{
				Assert.assertTrue(setToDefaultDashboardGlobalFilter(LanguageCode),"Error occured in setting dashboard to default mode.");
			}

			Assert.assertFalse(dashboardPage.verifyChartisLoadedDynamic(LanguageCode, "deviceByOsChartFlexi", "fatalErrorDeviceByOs", 5), "DEVICES BY OS Chart did not load successfully.");

			// Test case 1 (This test case validates the text,count and version of tool tip
			// present on all sub parts of chart with text,count and version on Report
			// page.)
			dashboardPage.scrollToDashboardPage("devicesByOsTitleFlexi");
			softAssert.assertTrue(dashboardPage.verifyTooltipTextCountAndVersionOnReportWithFrame(LanguageCode, "devicesByOsTitleFlexi", "deviceByOSLegends", "tooltipTextDeviceByOsFlexi", "tooltipTextDeviceByOsMajorVersionFlexi", "tooltipTextDeviceByOsMajorVersionNameFlexi", "tooltipTextDeviceByOsCountFlexi", "reportCountDeviceByOs", "levelsBackFunctionalityForDevicesByOsFlexi", "deviceByOsTextLocator", "deviceByOsReleaseTextLocator", "moreDetailsLink", "frameLocator", "deviceByOSLegendsHidden",
					"deviceByOSLegendsVisible", "labelsLocatorDeviceByOsFlexi"), "Tooltip text of DEVICES BY OS chart did match with reports");
			softAssert.assertAll();
			LOGGER.info("Validation of DEVICES BY OS Chart completed successfully");
		}
	}

	@Test(priority = 9, groups = { "REGRESSION", "REGRESSION_CI", "ALL_CI", "ALL", "REGRESSIONDASHBOARD1" }, description = "[199636],[177999],[178093],[178005],[178098],[178092],[191191],[177998],[178099],[177996],[213236] ; Roles ~ MSP, Reseller")
	public final void verifyChartDevicesByOSRedirectionForPartner() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		final String[] DASHBOARD_CHART_DEVICE_TYPE_OS_HW_REPORT_PAGE = { "Global.gridHeaders.companyName", "Global.gridHeaders.enrolledmonth", "Global.gridHeaders.deviceName", "Global.gridHeaders.serialNumber", "Global.gridHeaders.deviceType", "Global.gridHeaders.deviceManufacturer", "Global.gridHeaders.deviceModel", "Global.gridHeaders.operatingSystem", "gridHeaders.hardware_Inventory.Details.operating_system_release", "gridHeaders.hardware_Inventory.Details.operating_system_Build_number",
				"gridHeaders.Hardware_Inventory.Details.win10_patch_status", "Global.gridHeaders.lastSeen", "Global.gridHeaders.memory", "gridHeaders.Hardware_Inventory.Details.graphics", "Global.gridHeaders.processor", "Global.gridHeaders.manufacture_date", "gridHeaders.hardware_Inventory.Details.enrolledDate", "gridHeaders.Incident_Widget.Details.warStatus", "gridHeaders.hardware_Inventory.Details.warrantyEndDate", "Global.gridHeaders.country", "Global.gridHeaders.location",
				"Global.gridHeaders.department", "Global.gridHeaders.costCenter", "Global.gridHeaders.alias", "Global.gridHeaders.asset_tag" };
		login("RESELLER_NEW_UI_EMAIL_US", "RESELLER_NEW_UI_PASSWORD_US");
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		HashMap<String, String> DeviceByOsInfo = dashboardPage.getDeviceByOsDetails();
		sleeper(3000);
		if (!toggleVerification(DashboardVariables.FLEXIDASHBOARD, getCredentials(environment, "RESELLER_NEW_UI_EMAIL_US"))) {
			// Verify toggle status
			Assert.assertTrue(toggleVerification(DashboardVariables.DEVICESBYOS, "RESELLER_NEW_UI_EMAIL_US"), "LD-Toggle is disabled for DEVICES BY OS chart");
			LOGGER.info("Verified LD-Toggle status");

			// Reset current configuration of dashboard chart
			dashboardPage.clearDashboardCompanyFilter();
			dashboardPage.verifyResetToDefaultButton();
			dashboardPage.waitForPageLoaded();
			LOGGER.info("Scrolled down to DEVICES BY OS chart");

			// Test Case 1 (This test case validate whether chart is loaded or not.)
			Assert.assertFalse(dashboardPage.verifyChartisLoadedDynamic(LanguageCode, "deviceByOsChart", "fatalErrorDeviceByOs", 5), "DEVICES BY OS Chart did not load successfully.");

			// Test case 2 (This test case validates redirection to device details page from Report page.)
			softAssert.assertTrue(dashboardPage.verifyDeviceByOsRedirectionWithFrame(LanguageCode, "headerListLocatorOnReportPage", DASHBOARD_CHART_DEVICE_TYPE_OS_HW_REPORT_PAGE, DeviceByOsInfo, "deviceDetailsTitle", "deviceNameColumnDeviceByType", "errorLocatorForNoDevice"), "Redirection of DEVICES BY OS chart navigated to Device details page");

			softAssert.assertAll();
			LOGGER.info("Validation of DEVICES BY OS Chart completed successfully");
		}else{
			if (!toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, "RESELLER_NEW_UI_EMAIL_US"))){
				Assert.assertTrue(setToDefaultDashboard(LanguageCode),"Error occured in setting dashboard to default mode.");
			}else{
				Assert.assertTrue(setToDefaultDashboardGlobalFilter(LanguageCode),"Error occured in setting dashboard to default mode.");
			}

			softAssert.assertTrue(dashboardPage.verifyDeviceByOsRedirectionWithFrameFlexi(LanguageCode, "headerListLocatorOnReportPage", DASHBOARD_CHART_DEVICE_TYPE_OS_HW_REPORT_PAGE, DeviceByOsInfo, "deviceDetailsTitle", "deviceNameColumnDeviceByType", "errorLocatorForNoDevice"), "Redirection of DEVICES BY OS chart navigated to Device details page");
			softAssert.assertAll();
			LOGGER.info("Validation of DEVICES BY OS Chart completed successfully");
		}

	}

	@Test(priority = 10, groups = { "REGRESSION", "REGRESSION_CI", "ALL_CI", "ALL", "REGRESSIONDASHBOARD1" }, dataProvider = "getLoginData", description = "[198865],[198866],[198871],[198869],[198867],[116305],[213235] ; Roles ~ MSP, Reseller")
	public final void verifyChartWarrantyExpirationForPartner(String username, String password) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		ArrayList<String> WARRANTY_EXPIRATION_LABELS_LIST = new ArrayList<>(Arrays.asList("Today", "Tomorrow", "Within 7 days", "Within 30 days", "Within 90 days", "Within 1 year", "Within 2 years", "Within 3 years", "Unknown", "Expired", "More than 3 years"));
		final String[] DASHBOARD_CHART_WARRANTY_EXPIRATION_REPORT_PAGE = { "Global.gridHeaders.companyName", "gridHeaders.Hardware_Inventory.Details.allData.lastSignedinUser", "gridHeaders.Hardware_Warranty_V2.Details.warStatusOverall", "gridHeaders.Hardware_Warranty_V2.Details.warRemainingOverall", "gridHeaders.Hardware_Warranty_V2.Details.warDaysRemainingOverall", "gridHeaders.Hardware_Warranty_V2.Details.maxOblEndDate", "Global.gridHeaders.deviceName", "Global.gridHeaders.serialNumber",
				"Global.gridHeaders.deviceManufacturer", "Global.gridHeaders.deviceModel", "gridHeaders.hardware_Inventory.Details.enrolledDate", "Global.gridHeaders.manufacture_date", "Global.gridHeaders.country", "gridHeaders.Hardware_Warranty.Details.offerProdId", "Global.gridHeaders.type", "gridHeaders.Hardware_Warranty.Details.warType", "Global.gridHeaders.title", "gridHeaders.Incident_Widget.Details.warStatus", "gridHeaders.Hardware_Warranty_V2.Details.expirationstatus",
				"gridHeaders.Hardware_Warranty.Details.daysRemaining", "gridHeaders.Subscription_Expiry.Details.start_date", "Global.gridHeaders.endDate","Global.gridHeaders.location_1","Global.gridHeaders.location_2","Global.gridHeaders.location_3","Global.gridHeaders.location_4" };
		login(username, password);
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();

		if (!toggleVerification(DashboardVariables.FLEXIDASHBOARD, getCredentials(environment, username))) {
			// Verify toggle status
			Assert.assertTrue(toggleVerification(DashboardVariables.WARRANTYEXPIRATION, username), "LD-Toggle is disabled for Warranty Expiration chart");
			LOGGER.info("Verified LD-Toggle status");

			// Reset current configuration of dashboard chart
			dashboardPage.clearDashboardCompanyFilter();
			dashboardPage.verifyResetToDefaultButton();
			dashboardPage.waitForPageLoaded();

			dashboardPage.scrollToDashboardPage("warrantyExpirationTitle");
			LOGGER.info("Scrolled down to Warranty Expiration chart");
			dashboardPage.waitForPageLoaded();
			// Test Case 1 (This Test case validates title of the chart.)
			softAssert.assertTrue(dashboardPage.verifyTitleofChartOfDashboardPage("warrantyExpirationTitle", dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.warranty_expiration")), "Warranty Expiration Chart title did not match.");

			// Test Case 2 (This test case validate whether chart is loaded or not.)
			Assert.assertFalse(dashboardPage.verifyChartisLoadedDynamic(LanguageCode, "warrantyExpirationChart", "warrantyExpirationFatalError", 5), "Warranty Expiration Chart did not load successfully.");

			// Test Case 3 (This test case validate whether chart has any data or
			// not.)
			Assert.assertFalse(dashboardPage.verifyChartsHasNoDataOnDashBoardPage(LanguageCode, "warrantyExpirationNoData", "dashboard.charts.title.warranty_expiration"), "Warranty Expiration Chart does not have any data.");
			Assert.assertFalse(dashboardPage.verifyChartsHasNoDataDevicesEnrolledOnDashBoardPage(LanguageCode, "warrantyExpirationNoData", "dashboard.charts.title.warranty_expiration"), "Warranty Expiration Chart does not have any data.");

			// Test Case 5 (This test case validates hidden/visible functionality of
			// chart.)
			softAssert.assertTrue(dashboardPage.verifyLabelHiddenWhenClickOnLegendsBar("warrantyExpirationLegendLabel", "warrantyExpirationToolTipHover", "warrantyExpirationToolTipHoverText", "warrantyExpirationNoData"), "Legends Hidden for Warranty Expiration Chart does not work as per expection");
			softAssert.assertTrue(dashboardPage.verifyLabelVisibleWhenClickOnLegendsBar("warrantyExpirationLegendLabel", "warrantyExpirationToolTipHover", "warrantyExpirationToolTipHoverText", "warrantyExpirationNoData"), "Legends Visibility for Warranty Expiration Chart does not work as per expection");

			// Test Case 6 (This test case validates the header of reports page.)
			softAssert.assertTrue(dashboardPage.headerTextVerificationOnReportPageFrame(LanguageCode, "warrantyExpirationToolTipHover", "headerListLocatorOnReportPage", DASHBOARD_CHART_WARRANTY_EXPIRATION_REPORT_PAGE, "frameLocator"), "Report header for Warranty Expiration Chart did not match on report page.");

			// Test case 7 (This test case validates the labels of the chart. )
			softAssert.assertTrue(dashboardPage.verifyChartLabelsDashboardPage("warrantyExpirationXaxisLabels", WARRANTY_EXPIRATION_LABELS_LIST), "Labels of Warranty Expiration Chart did not match");

			// Test case 8 (This test case validates no data message on chart.)
			dashboardPage.scrollUpCharts();
			dashboardPage.clickOnElementsOfDashboardPage("companyDropdown");
			softAssert.assertTrue(dashboardPage.verifyCompanyChangeOfDashboardPage(LanguageCode, "companySearchBox", DashboardVariables.DASHBOARD_CHART_COMPANY_NAME_MSP_US, "companyEmptyText", "companyList", "dropdownBoxCompany"), "Company Change is not working correctly");
			softAssert.assertTrue(dashboardPage.verifyChartsHasNoDataOnDashBoardPage(LanguageCode, "warrantyExpirationNoData", "dashboard.charts.title.warranty_expiration"), "No data message for Warranty Expiration Chart is incorrect.");
			dashboardPage.waitForPageLoaded();
			dashboardPage.clickOnElementsOfDashboardPage("clearCompany");
			dashboardPage.waitForPageLoaded();
			// dashboardPage.scrollToDashboardPage("warrantyExpirationChart");

			// Test case 9 (This test case validates redirection to device details
			// page from Report page.)
			Assert.assertFalse(dashboardPage.verifyChartisLoadedDynamic(LanguageCode, "warrantyExpirationChart", "warrantyExpirationFatalError", 5), "Warranty Expiration Chart did not load successfully.");
			softAssert.assertTrue(dashboardPage.verifyDeviceNameRedirectionMSPWithFrame(LanguageCode, "warrantyExpirationXaxisLabels", "deviceDetailsTitle", "deviceNameColumnOfHardwareWarranty", "errorLocatorForNoDevice", "frameLocator"), "Device Name Redirection is not working properly for Warranty Expiration Chart");

			softAssert.assertAll();
			LOGGER.info("Validation of Warranty Expiration completed successfully");
		} else {
			if (!toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, username))){
				Assert.assertTrue(setToDefaultDashboard(LanguageCode),"Error occured in setting dashboard to default mode.");
			}else{
				Assert.assertTrue(setToDefaultDashboardGlobalFilter(LanguageCode),"Error occured in setting dashboard to default mode.");
			}

			dashboardPage.scrollToDashboardPage("warrantyExpirationTitleFlexi");
			LOGGER.info("Scrolled down to Warranty Expiration chart");
			dashboardPage.waitForPageLoaded();
			// Test Case 1 (This Test case validates title of the chart.)
			softAssert.assertTrue(dashboardPage.verifyTitleofChartOfDashboardPage("warrantyExpirationTitleFlexi", dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.warranty_expiration")), "Warranty Expiration Chart title did not match.");

			// Test Case 2 (This test case validate whether chart is loaded or not.)
			Assert.assertFalse(dashboardPage.verifyChartisLoadedDynamic(LanguageCode, "warrantyExpirationChartFlexi", "warrantyExpirationFatalErrorFlexi", 5), "Warranty Expiration Chart did not load successfully.");

			// Test Case 3 (This test case validate whether chart has any data or
			// not.)
			  Assert.assertFalse(dashboardPage.verifyChartsHasNoDataDevicesEnrolledOnDashBoardPage(LanguageCode, "warrantyExpirationNoDataFlexi", "dashboard.charts.title.warranty_expiration"), "Warranty Expiration Chart does not have any data.");

			// Commented this test case due to scroll added in this chart, this will hinder automation of chart.
			// Test Case 5 (This test case validates hidden/visible functionality of
			// chart.)
			// softAssert.assertTrue(dashboardPage.verifyLabelHiddenWhenClickOnLegendsBar("warrantyExpirationLegendLabelFlexi", "warrantyExpirationToolTipHoverFlexi",
			// "warrantyExpirationToolTipHoverTextFlexi", "warrantyExpirationNoDataFlexi"), "Legends Hidden for Warranty Expiration Chart does not work as per expection");
			// softAssert.assertTrue(dashboardPage.verifyLabelVisibleWhenClickOnLegendsBar("warrantyExpirationLegendLabelFlexi", "warrantyExpirationToolTipHoverFlexi",
			// "warrantyExpirationToolTipHoverTextFlexi", "warrantyExpirationNoDataFlexi"), "Legends Visibility for Warranty Expiration Chart does not work as per expection");

			// Test Case 6 (This test case validates the header of reports page.)
			//softAssert.assertTrue(dashboardPage.headerTextVerificationOnReportPageFrame(LanguageCode, "warrantyExpirationToolTipChartHoverFlexi", "warrantyExpirationheaderListLocatorOnReportPageFlexi", DASHBOARD_CHART_WARRANTY_EXPIRATION_REPORT_PAGE, "frameLocator"), "Report header for Warranty Expiration Chart did not match on report page.");

			// Test case 7 (This test case validates the labels of the chart. )
			softAssert.assertTrue(dashboardPage.verifyChartLabelsDashboardPage("warrantyExpirationXaxisLabelsFlexi", WARRANTY_EXPIRATION_LABELS_LIST), "Labels of Warranty Expiration Chart did not match");

			// Test case 8 (This test case validates no data message on chart.)
			dashboardPage.scrollUpCharts();
			dashboardPage.waitForPageLoaded();
			if (!toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, username))){
				dashboardPage.clickOnElementsOfDashboardPage("companyDropdownFlexi");
				softAssert.assertTrue(dashboardPage.verifyCompanyChangeOfDashboardPage(LanguageCode, "companySearchBoxFlexi", DashboardVariables.DASHBOARD_CHART_COMPANY_NAME_MSP_US, "companyEmptyText", "companyList", "dropdownBoxCompanyFlexi"), "Company Change is not working correctly");
				softAssert.assertTrue(dashboardPage.verifyChartsHasNoDataOnDashBoardPageFlexi(LanguageCode, "warrantyExpirationNoDataFlexi", "dashboard.charts.title.devices_by_type"), "No data message for Warranty Expiration Chart is incorrect.");
				dashboardPage.clickOnElementsOfDashboardPage("clearCompanyFlexi");
			}else{
				dashboardPage.clickOnElementsOfDashboardPage("companyDropdownGlobalFilter");
				softAssert.assertTrue(dashboardPage.verifyCompanyChangeOfDashboardPageGlobalFilter(LanguageCode, "companySearchGlobalFilter", DashboardVariables.DASHBOARD_CHART_COMPANY_NAME_MSP_US, "companyEmptyTextGlobalFilter", "companyListGlobalFilter", "outputTextGlobalFilter","globalFilterSave"), "Company Change is not working correctly");
				softAssert.assertTrue(dashboardPage.verifyChartsHasNoDataOnDashBoardPageFlexi(LanguageCode, "warrantyExpirationNoDataFlexi", "dashboard.charts.title.warranty_expiration"), "No data message for Warranty Expiration chart is incorrect.");										
				dashboardPage.clickOnElementsOfDashboardPage("companyDropdownGlobalFilter");
				dashboardPage.clickOnElementsOfDashboardPage("clearGlobalFilter");
				dashboardPage.clickOnElementsOfDashboardPage("globalFilterSave");
				
			}

			// Test case 9 (This test case validates redirection to device details
			// page from Report page.)
			Assert.assertFalse(dashboardPage.verifyChartisLoadedDynamic(LanguageCode, "warrantyExpirationChartFlexi", "warrantyExpirationFatalErrorFlexi", 5), "Warranty Expiration Chart did not load successfully.");
			//softAssert.assertTrue(dashboardPage.verifyDeviceNameRedirectionMSPWithFrame(LanguageCode, "warrantyExpirationToolTipChartHoverFlexi", "deviceDetailsTitle", "deviceNameColumnOfHardwareWarranty", "errorLocatorForNoDevice", "reportLayout"), "Device Name Redirection is not working properly for Warranty Expiration Chart");
			softAssert.assertAll();
			LOGGER.info("Validation of Warranty Expiration completed successfully");
		}
	}

	@Test(priority = 11, groups = { "REGRESSION", "REGRESSION_CI", "ALL_CI", "ALL", "REGRESSIONDASHBOARD1" }, dataProvider = "getLoginData", description = "[195727],[195725],[200607],[195728],[195730],[195734],[195739],[195736],[195731] ; Roles ~ MSP, Reseller",enabled=false)
	public final void verifyBatteryReplacementSummaryForPartner(String username, String password) throws Exception {
	SoftAssert softAssert = new SoftAssert();
		ArrayList<String> BATTERY_REPLACEMENT_SUMMARY_LABELS_LIST = new ArrayList<>(Arrays.asList("dashboard.charts.timeframe.ok", "dashboard.charts.timeframe.early_detection", "dashboard.charts.timeframe.needs_action"));
		final String[] DASHBOARD_CHART_BATTERY_REPLACEMENT_SUMMARY_REPORT_PAGE = { "Global.gridHeaders.companyName", "gridHeaders.Hardware_Inventory.Details.allData.lastSignedinUser", "gridHeaders.Battery_Replacement.Details.batteryReplacementTimeframe", "gridHeaders.Battery_Replacement.Details.currentBatteryHealth", "gridHeaders.Battery_Replacement.Details.batteryReplacementReason", "Global.gridHeaders.deviceName", "Global.gridHeaders.serialNumber", "Global.gridHeaders.deviceType",
				"Global.gridHeaders.deviceManufacturer", "Global.gridHeaders.deviceModel", "Global.gridHeaders.operatingSystem", "Global.gridHeaders.manufacture_date", "Global.gridHeaders.lastSeen", "gridHeaders.Battery_Replacement.Details.batterySN", "Global.gridHeaders.country","gridHeaders.Battery_Replacement.Details.ctnumber","Global.gridHeaders.device_warranty_status", "Global.gridHeaders.device_warranty_date","Global.gridHeaders.location_1","Global.gridHeaders.location_2","Global.gridHeaders.location_3","Global.gridHeaders.location_4" };
		login(username, password);
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();

		if (!toggleVerification(DashboardVariables.FLEXIDASHBOARD, getCredentials(environment, username))) {
			// Verify toggle status
			Assert.assertTrue(toggleVerification(DashboardVariables.BATTERYREPLACEMENTSUMMARY, username), "LD-Toggle is disabled for BatteryReplacement Summary chart");
			LOGGER.info("Verified LD-Toggle status");

			// Reset current configuration of dashboard chart
			dashboardPage.clearDashboardCompanyFilter();
			dashboardPage.verifyResetToDefaultButton();

			dashboardPage.waitForPageLoaded();
			// Test Case 1 (This Test case validates title of the chart.)
			// softAssert.assertTrue(dashboardPage.verifyTitleofChartOfDashboardPage("batteryReplacementSummaryTitle", dashboardPage.getTextLanguage(LanguageCode, "daas_ui",
			// "dashboard.charts.title.battery_rep_summary")), "Battery Replacement Summary Chart title did not match.");

			// Test Case 2 (This test case validate whether chart is loaded or not.)
			Assert.assertFalse(dashboardPage.verifyChartisLoadedDynamic(LanguageCode, "batteryReplacementSummaryChart", "batteryReplacementSummaryFatalError", 5), "Battery Replacement Summary Chart did not load successfully.");

			// Test Case 3 (This test case validate whether chart has any data or
			// not.)
			// dashboardPage.scrollToDashboardPage("scrollToBatteryReplacementChart");
			Assert.assertFalse(dashboardPage.verifyChartsHasNoDataOnDashBoardPage(LanguageCode, "batteryReplacementSummaryNoData", "dashboard.charts.title.battery_rep_summary"), "Battery Replacement Summary Chart does not have any data.");
			Assert.assertFalse(dashboardPage.verifyChartsHasNoDataDevicesEnrolledOnDashBoardPage(LanguageCode, "batteryReplacementSummaryNoData", "dashboard.charts.title.battery_rep_summary"), "Battery Replacement Summary Chart does not have any data.");

			// Test Case 5 (This test case validates the count of tool tip present
			// on all sub parts of charts with total rows on Report page.)
			softAssert.assertTrue(dashboardPage.verifyTooltipCountonReportWithFrame("batteryReplacementSummaryToolTipHover", "batteryReplacementSummaryCountLocator", "columListofBatteryReplacementSummary", "paginationReportpage", "frameLocator"), "Tooltip count of Battery Replacement Summary chart did match with reports");

			// Test Case 6 (This test case validates the text of tool tip present on
			// all sub parts of chart with text on Report page.)
			softAssert.assertTrue(dashboardPage.verifyTooltipTextOnReportWithFrame("batteryReplacementSummaryToolTipHover", "batteryReplacementSummaryToolTipHoverText", "monthTextLocator"), "Tooltip text of Battery Replacement Summary chart did match with reports");

			// Test Case 7 (This test case validates the labels of the chart. )
			softAssert.assertTrue(dashboardPage.verifyChartLabelsDashboardPage("batteryReplacementSummaryLegendLabel", dashboardPage.getTextLanguage(LanguageCode, "daas_ui", BATTERY_REPLACEMENT_SUMMARY_LABELS_LIST)), "Labels of Battery Replacement Summary Chart chart did not match");

			// Test Case 8 (This test case validates the header of reports page.)
			softAssert.assertTrue(dashboardPage.headerTextVerificationOnReportPageFrame(LanguageCode, "batteryReplacementSummaryToolTipHover", "headerListLocatorOnReportPage", DASHBOARD_CHART_BATTERY_REPLACEMENT_SUMMARY_REPORT_PAGE, "frameLocator"), "Report header for Battery Replacement Summary Chart did not match on report page.");

			// Test case 9 (This test case validates no data message on chart.)
			dashboardPage.scrollUpCharts();
			dashboardPage.clickOnElementsOfDashboardPage("companyDropdown");
			softAssert.assertTrue(dashboardPage.verifyCompanyChangeOfDashboardPage(LanguageCode, "companySearchBox", DashboardVariables.DASHBOARD_CHART_COMPANY_NAME_MSP_US, "companyEmptyText", "companyList", "dropdownBoxCompany"), "Company Change is not working correctly");
			softAssert.assertTrue(dashboardPage.verifyChartsHasNoDataOnDashBoardPage(LanguageCode, "batteryReplacementSummaryNoData", "dashboard.charts.title.battery_rep_summary"), "No data message for Battery Replacement Summary Chart is incorrect.");
			dashboardPage.clickOnElementsOfDashboardPage("clearCompany");
			dashboardPage.waitForPageLoaded();
			dashboardPage.scrollDownCharts();

			// Test Case 4 (This test case validates hidden/visible functionality of chart.)
			softAssert.assertTrue(dashboardPage.verifyLabelHiddenWhenClickOnLegends("batteryReplacementSummaryLegendLabel", "batteryReplacementSummarydrilldownLabels", "batteryReplacementSummaryToolTipHoverText", "batteryReplacementSummarydrilldownLabelsAllHidden"), "Legends Hidden for Battery Replacement Summary chart does not work as per expection");
			softAssert.assertTrue(dashboardPage.verifyLabelVisibleWhenClickOnLegends("batteryReplacementSummaryLegendLabel", "batteryReplacementSummarydrilldownLabels", "batteryReplacementSummaryToolTipHoverText", "batteryReplacementSummarydrilldownLabelsAllHidden"), "Legends Visibility for Battery Replacement Summary Chart does not work as per expection");

			// Test Case 10 (This test case validates redirection to device details
			// page from Report page.)
			Assert.assertFalse(dashboardPage.verifyChartisLoadedDynamic(LanguageCode, "batteryReplacementSummaryChart", "batteryReplacementSummaryFatalError", 5), "Battery Replacement Summary Chart did not load successfully.");
			softAssert.assertTrue(dashboardPage.verifyDeviceNameRedirectionMSPWithFrame(LanguageCode, "batteryReplacementSummaryToolTipHover", "deviceDetailsTitle", "deviceNameColumnOfBatteryReplacementSummary", "errorLocatorForNoDevice", "frameLocator"), "Device Name Redirection is not working properly for Battery Replacement Summary Chart");

			softAssert.assertAll();
			LOGGER.info("Validation of Battery Replacement Summary Chart completed successfully");
		} else {
			if (!toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, username))){
				Assert.assertTrue(setToDefaultDashboard(LanguageCode),"Error occured in setting dashboard to default mode.");
			}else{
				Assert.assertTrue(setToDefaultDashboardGlobalFilter(LanguageCode),"Error occured in setting dashboard to default mode.");
			}

			// Test Case 1 (This Test case validates title of the chart.)
			softAssert.assertTrue(dashboardPage.verifyTitleofChartOfDashboardPage("batteryReplacementSummaryTitleFlexi", dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.battery_rep_summary")), "Battery Replacement Summary Chart title did not match.");

			// Test Case 2 (This test case validate whether chart is loaded or not.)
			Assert.assertFalse(dashboardPage.verifyChartisLoadedDynamic(LanguageCode, "batteryReplacementSummaryChartFlexi", "batteryReplacementSummaryFatalErrorFlexi", 5), "Battery Replacement Summary Chart did not load successfully.");

			// Test Case 3 (This test case validate whether chart has any data or not.)
			Assert.assertFalse(dashboardPage.verifyChartsHasNoDataOnDashBoardPage(LanguageCode, "batteryReplacementSummaryNoDataFlexi", "dashboard.charts.title.battery_rep_summary"), "Battery Replacement Summary Chart does not have any data.");
			Assert.assertFalse(dashboardPage.verifyChartsHasNoDataDevicesEnrolledOnDashBoardPage(LanguageCode, "batteryReplacementSummaryNoDataFlexi", "dashboard.charts.title.battery_rep_summary"), "Battery Replacement Summary Chart does not have any data.");

			// Test Case 4 (This test case validates hidden/visible functionality of chart
				softAssert.assertTrue(dashboardPage.verifyLabelHiddenWhenClickOnLegendsDonutChartFlexi("batteryReplacementSummaryLegendLabelFlexi", "batteryReplacementChartVisibilityFlexi","batteryReplacementDropdownFlexi"), "Hidden functionality is not working for Battery Replacement chart.");
				softAssert.assertTrue(dashboardPage.verifyLabelVisibleWhenClickOnLegendsDonutChartFlexi("batteryReplacementSummaryLegendLabelFlexi", "batteryReplacementChartVisibilityFlexi","batteryReplacementDropdownFlexi"), "Visible functionality is not working for Battery Replacement chart");
			
			// Test Case 5 (This test case validates the count of tool tip present on all sub parts of charts with total rows on Report page.)
			softAssert.assertTrue(dashboardPage.verifyTooltipCountonReportWithFrameDonutChartFlexiWithOffset("batteryReplacementSummaryLegendLabelFlexi", "batteryReplacementSummaryCountLocatorFlexi", "columListofBatteryReplacementSummaryFlexi", "paginationReportpage", "frameLocator", "batteryReplacementChartVisibilityFlexi", "batteryReplacementChartVisibilityFlexi",15,80,"batteryReplacementDropdownFlexi"), "Tooltip count of Battery Replacement Summary chart did match with reports");
			
			// Test Case 6 (This test case validates the text of tool tip present on all sub parts of chart with text on Report page.)
				softAssert.assertTrue(dashboardPage.verifyTooltipTextOnReportWithFrameDonutChartFlexi("batteryReplacementSummaryLegendLabelFlexi", "batteryReplacementSummaryToolTipHoverTextFlexi", "monthTextLocator", "batteryReplacementChartVisibilityFlexi", "batteryReplacementChartVisibilityFlexi","batteryReplacementDropdownFlexi"), "Tooltip text of CPU Utlization chart did match with reports");
			
			
			// Test Case 7 (This test case validates the labels of the chart. )
			softAssert.assertTrue(dashboardPage.verifyChartLabelsDashboardPage("batteryReplacementSummaryLegendLabelFlexi", dashboardPage.getTextLanguage(LanguageCode, "daas_ui", BATTERY_REPLACEMENT_SUMMARY_LABELS_LIST)), "Labels of Battery Replacement Summary Chart chart did not match");

			// Test Case 8 (This test case validates the header of reports page.)
				softAssert.assertTrue(dashboardPage.headerTextVerificationOnReportPageFrameDonutChartFlexi(LanguageCode, "batteryReplacementSummaryLegendLabelFlexi", "batteryReplacementheaderListLocatorOnReportPage", DASHBOARD_CHART_BATTERY_REPLACEMENT_SUMMARY_REPORT_PAGE, "frameLocator", "batteryReplacementChartVisibilityFlexi", "batteryReplacementChartVisibilityFlexi","batteryReplacementDropdownFlexi"), "Report header for Battery Replacement chart did not match on report page.");
			
			// Test case 9 (This test case validates no data message on chart.)
			dashboardPage.scrollUpCharts();
			if (!toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, username))){
				dashboardPage.clickOnElementsOfDashboardPage("companyDropdownFlexi");
				softAssert.assertTrue(dashboardPage.verifyCompanyChangeOfDashboardPage(LanguageCode, "companySearchBoxFlexi", DashboardVariables.DASHBOARD_CHART_COMPANY_NAME_MSP_US, "companyEmptyText", "companyList", "dropdownBoxCompanyFlexi"), "Company Change is not working correctly");
				softAssert.assertTrue(dashboardPage.verifyChartsHasNoDataOnDashBoardPageFlexi(LanguageCode, "batteryReplacementSummaryNoDataFlexi", "dashboard.charts.title.battery_rep_summary"), "No data message for Battery Replacement Summary Chart is incorrect.");
				sleeper(2000);
				dashboardPage.clickOnElementsOfDashboardPage("clearCompanyFlexi");
				dashboardPage.waitForPageLoaded();
			}else{
				dashboardPage.clickOnElementsOfDashboardPage("companyDropdownGlobalFilter");
				softAssert.assertTrue(dashboardPage.verifyCompanyChangeOfDashboardPageGlobalFilter(LanguageCode, "companySearchGlobalFilter", DashboardVariables.DASHBOARD_CHART_COMPANY_NAME_MSP_US, "companyEmptyTextGlobalFilter", "companyListGlobalFilter", "outputTextGlobalFilter","globalFilterSave"), "Company Change is not working correctly");
				softAssert.assertTrue(dashboardPage.verifyChartsHasNoDataOnDashBoardPageFlexi(LanguageCode, "batteryReplacementSummaryNoDataFlexi", "dashboard.charts.title.battery_rep_summary"), "No data message for Battery Replacement Summary Chart is incorrect.");				
				dashboardPage.clickOnElementsOfDashboardPage("companyDropdownGlobalFilter");
				dashboardPage.clickOnElementsOfDashboardPage("clearGlobalFilter");
				dashboardPage.clickOnElementsOfDashboardPage("globalFilterSave");
				}
			dashboardPage.scrollDownCharts();

			// Test Case 10 (This test case validates redirection to device details page from Report page.)
			Assert.assertFalse(dashboardPage.verifyChartisLoadedDynamic(LanguageCode, "batteryReplacementSummaryChartFlexi", "batteryReplacementSummaryFatalErrorFlexi", 5), "Battery Replacement Summary Chart did not load successfully.");
				softAssert.assertTrue(dashboardPage.verifyDeviceNameRedirectionMSPWithFrameDonutChartFlexi(LanguageCode, "batteryReplacementSummaryLegendLabelFlexi", "deviceDetailsTitle", "deviceNameColumnOfBatteryReplacementSummaryFlexi", "errorLocatorForNoDevice", "reportLayout", "batteryReplacementChartVisibilityFlexi", "batteryReplacementChartVisibilityFlexi","batteryReplacementDropdownFlexi"), "Device Name Redirection is not working properly for Battery Replacement chart");
			softAssert.assertAll();
			LOGGER.info("Validation of Battery Replacement Summary Chart completed successfully");
		}
	}

	@Test(priority = 12, groups = { "REGRESSION", "REGRESSION_CI", "ALL_CI", "ALL", "REGRESSIONDASHBOARD1","PENTEST" }, dataProvider = "getLoginData", description = "[195271],[195350],[195318],[195342],[195338],[195316],[195317],[195323],[195354],[195347],[213250] ; Roles ~ MSP, Reseller")
	public final void verifyDiskReplacementSummaryForPartner(String username, String password) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		ArrayList<String> DISK_REPLACEMENT_SUMMARY_LABELS_LIST = new ArrayList<>(Arrays.asList("dashboard.charts.timeframe.ok", "dashboard.charts.timeframe.early_detection", "dashboard.charts.timeframe.needs_action"));
		final String[] DASHBOARD_CHART_DISK_REPLACEMENT_SUMMARY_REPORT_PAGE = { "Global.gridHeaders.companyName", "gridHeaders.Hardware_Inventory.Details.allData.lastSignedinUser", "gridHeaders.Disk_Replacement.Details.diskReplacementTimeframe", "gridHeaders.Disk_Replacement.Details.currentDiskStatus", "gridHeaders.Disk_Replacement.Details.diskReplacementReason", "Global.gridHeaders.serialNumber", "Global.gridHeaders.deviceName", "Global.gridHeaders.deviceType",
				"Global.gridHeaders.deviceManufacturer", "Global.gridHeaders.deviceModel", "Global.gridHeaders.operatingSystem", "global.gridHeaders.manufactureDate", "Global.gridHeaders.lastSeen", "gridHeaders.Disk_Replacement.Details.diskSN", "gridHeaders.Disk_Replacement.Details.driveType", "gridHeaders.Disk_Capacity_Forecasting.Details.diskModel", "Global.gridHeaders.disk_size", "gridHeaders.Disk_Capacity_Forecasting.Details.diskCapacityGB",
				"gridHeaders.Disk_Replacement.Details.firmwarerevision", "Global.gridHeaders.country", "Global.gridHeaders.device_warranty_status", "Global.gridHeaders.device_warranty_date","Global.gridHeaders.location_1","Global.gridHeaders.location_2","Global.gridHeaders.location_3","Global.gridHeaders.location_4" };
		login(username, password);
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();

		if (!toggleVerification(DashboardVariables.FLEXIDASHBOARD, getCredentials(environment, username))) {
			// Verify toggle status
			Assert.assertTrue(toggleVerification(DashboardVariables.DISKREPLACEMENTSUMMARY, username), "LD-Toggle is disabled for Disk Replacement Summary Chart");
			LOGGER.info("Verified LD-Toggle status");

			// Reset current configuration of dashboard chart
			dashboardPage.clearDashboardCompanyFilter();
			dashboardPage.verifyResetToDefaultButton();
			dashboardPage.waitForPageLoaded();
			dashboardPage.scrollToDashboardPage("diskReplacementSummaryTitle");
			LOGGER.info("Scrolled down to diskReplacementSummary chart");

			dashboardPage.waitForPageLoaded();
			// Test Case 1 (This Test case validates title of the chart.)
			softAssert.assertTrue(dashboardPage.verifyTitleofChartOfDashboardPage("diskReplacementSummaryTitle", dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.disk_rep_summary")), "Disk Replacement Summary Chart title did not match.");

			// Test Case 2 (This test case validate whether chart is loaded or not.)
			Assert.assertFalse(dashboardPage.verifyChartisLoadedDynamic(LanguageCode, "diskReplacementSummaryChart", "diskReplacementSummaryFatalError", 5), "Disk Replacement Summary Chart did not load successfully.");

			// Test Case 3 (This test case validate whether chart has any data or
			// not.)
			Assert.assertFalse(dashboardPage.verifyChartsHasNoDataOnDashBoardPage(LanguageCode, "diskReplacementSummaryNoData", "dashboard.charts.title.disk_rep_summary"), "Disk Replacement Summary Chart does not have any data.");
			Assert.assertFalse(dashboardPage.verifyChartsHasNoDataDevicesEnrolledOnDashBoardPage(LanguageCode, "diskReplacementSummaryNoData", "dashboard.charts.title.disk_rep_summary"), "Disk Replacement Summary Chart does not have any data.");

			// Test Case 4 (This test case validates hidden/visible functionality of
			// chart.)
			softAssert.assertTrue(dashboardPage.verifyLabelHiddenWhenClickOnLegends("diskReplacementSummaryLegendLabel", "diskReplacementSummarydrilldownLabels", "diskReplacementSummaryToolTipHoverText", "diskReplacementSummarydrilldownLabelsAllHidden"), "Legends Hidden for Disk Replacement Summary Chart does not work as per expection");
			softAssert.assertTrue(dashboardPage.verifyLabelVisibleWhenClickOnLegends("diskReplacementSummaryLegendLabel", "diskReplacementSummarydrilldownLabels", "diskReplacementSummaryToolTipHoverText", "diskReplacementSummarydrilldownLabelsAllHidden"), "Legends Visibility for Disk Replacement Summary Chart does not work as per expection");

			// Test Case 5 (This test case validates the count of tool tip present
			// on all sub parts of charts with total rows on Report page.)
			softAssert.assertTrue(dashboardPage.verifyTooltipCountonReportWithFrame("diskReplacementSummaryToolTipHover", "diskReplacementSummaryCountLocator", "columListofDiskReplacementSummary", "paginationReportpage", "frameLocator"), "Tooltip count of Disk Replacement Summary chart did match with reports");

			// Test Case 6 (This test case validates the text of tool tip present on
			// all sub parts of chart with text on Report page.)
			softAssert.assertTrue(dashboardPage.verifyTooltipTextOnReportWithFrame("diskReplacementSummaryToolTipHover", "diskReplacementSummaryToolTipHoverText", "monthTextLocator"), "Tooltip text of Disk Replacement Summary chart did match with reports");

			// Test Case 7 (This test case validates the labels of the chart. )
			softAssert.assertTrue(dashboardPage.verifyChartLabelsDashboardPage("diskReplacementSummaryLegendLabel", dashboardPage.getTextLanguage(LanguageCode, "daas_ui", DISK_REPLACEMENT_SUMMARY_LABELS_LIST)), "Labels of Disk Replacement Summary Chart chart did not match");

			// Test Case 8 (This test case validates the header of reports page.)
			softAssert.assertTrue(dashboardPage.headerTextVerificationOnReportPageFrame(LanguageCode, "diskReplacementSummaryToolTipHover", "headerListLocatorOnReportPage", DASHBOARD_CHART_DISK_REPLACEMENT_SUMMARY_REPORT_PAGE, "frameLocator"), "Report header for Disk Replacement Summary Chart did not match on report page.");

			// Test case 9 (This test case validates no data message on chart.)
			dashboardPage.scrollUpCharts();
			dashboardPage.clickOnElementsOfDashboardPage("companyDropdown");
			softAssert.assertTrue(dashboardPage.verifyCompanyChangeOfDashboardPage(LanguageCode, "companySearchBox", DashboardVariables.DASHBOARD_CHART_COMPANY_NAME_MSP_US, "companyEmptyText", "companyList", "dropdownBoxCompany"), "Company Change is not working correctly");
			softAssert.assertTrue(dashboardPage.verifyChartsHasNoDataOnDashBoardPage(LanguageCode, "diskReplacementSummaryNoData", "dashboard.charts.title.disk_rep_summary"), "No data message for Disk Replacement Summary Chart is incorrect.");
			dashboardPage.clickOnElementsOfDashboardPage("clearCompany");
			dashboardPage.waitForPageLoaded();
			dashboardPage.scrollDownCharts();

			// Test Case 10 (This test case validates redirection to device details
			// page from Report page.)
			Assert.assertFalse(dashboardPage.verifyChartisLoadedDynamic(LanguageCode, "diskReplacementSummaryChart", "diskReplacementSummaryFatalError", 5), "Disk Replacement Summary Chart did not load successfully.");
			softAssert.assertTrue(dashboardPage.verifyDeviceNameRedirectionMSPWithFrame(LanguageCode, "diskReplacementSummaryToolTipHover", "deviceDetailsTitle", "deviceNameColumnOfDiskReplacementSummary", "errorLocatorForNoDevice", "frameLocator"), "Device Name Redirection is not working properly for Disk Replacement Summary Chart");

			softAssert.assertAll();
			LOGGER.info("Validation of Disk Replacement Summary Chart completed successfully");
		} else {
			if (!toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, username))){
				Assert.assertTrue(setToDefaultDashboard(LanguageCode),"Error occured in setting dashboard to default mode.");
			}else{
				Assert.assertTrue(setToDefaultDashboardGlobalFilter(LanguageCode),"Error occured in setting dashboard to default mode.");
			}

			dashboardPage.waitForPageLoaded(); 
			// Test Case 1 (This Test case validates title of the chart.)
			softAssert.assertTrue(dashboardPage.verifyTitleofChartOfDashboardPage("diskReplacementSummaryTitleFlexi", dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.disk_rep_summary")), "Disk Replacement Summary Chart title did not match.");
			Assert.assertFalse(dashboardPage.verifyChartisLoadedDynamic(LanguageCode, "diskReplacementSummaryChartFlexi", "diskReplacementSummaryFatalErrorFlexi", 5), "Disk Replacement Summary Chart did not load successfully.");

			if(!(dashboardPage.verifyElementsOfDashboardPage("noDatadiskReplacementSummaryChartFlexi"))) {
			// Test Case 3 (This test case validate whether chart has any data or not.)
			Assert.assertFalse(dashboardPage.verifyChartsHasNoDataOnDashBoardPage(LanguageCode, "diskReplacementSummaryNoDataFlexi", "dashboard.charts.title.disk_rep_summary"), "Disk Replacement Summary Chart does not have any data.");
			Assert.assertFalse(dashboardPage.verifyChartsHasNoDataDevicesEnrolledOnDashBoardPage(LanguageCode, "diskReplacementSummaryNoDataFlexi", "dashboard.charts.title.disk_rep_summary"), "Disk Replacement Summary Chart does not have any data.");

			// Test Case 4 (This test case validates hidden/visible functionality of chart.)
				softAssert.assertTrue(dashboardPage.verifyLabelHiddenWhenClickOnLegendsDonutChartFlexi("diskReplacementSummaryLegendLabelFlexi", "diskReplacementSummaryChartVisibilityFlexi","diskReplacementDropdownFlexi"), "Hidden functionality is not working for Disk Replacement chart.");
				softAssert.assertTrue(dashboardPage.verifyLabelVisibleWhenClickOnLegendsDonutChartFlexi("diskReplacementSummaryLegendLabelFlexi", "diskReplacementSummaryChartVisibilityFlexi","diskReplacementDropdownFlexi"), "Visible functionality is not working for Disk Replacement chart");
		
			// Test Case 5 (This test case validates the count of tool tip present on all sub parts of charts with total rows on Report page.)
				softAssert.assertTrue(dashboardPage.verifyTooltipCountonReportWithFrameDonutChartFlexiWithOffset("diskReplacementSummaryLegendLabelFlexi", "diskReplacementSummaryCountLocatorFlexi", "columListofDiskReplacementSummaryFlexi", "paginationReportpage", "frameLocator", "diskReplacementSummaryChartVisibilityFlexi", "diskReplacementSummaryChartVisibilityFlexi",00,70,"diskReplacementDropdownFlexi"), "Tooltip count of Disk Replacement Summary chart did match with reports");
		
			// Test Case 6 (This test case validates the text of tool tip present on all sub parts of chart with text on Report page.)
				softAssert.assertTrue(dashboardPage.verifyTooltipTextOnReportWithFrameDonutChartFlexi("diskReplacementSummaryLegendLabelFlexi", "diskReplacementSummaryToolTipHoverTextFlexi", "monthTextLocator", "diskReplacementSummaryChartVisibilityFlexi", "diskReplacementSummaryChartVisibilityFlexi","diskReplacementDropdownFlexi"), "Tooltip text of Disk Replacement chart did match with reports");
			
			// Test Case 7 (This test case validates the labels of the chart. )
			softAssert.assertTrue(dashboardPage.verifyChartLabelsDashboardPage("diskReplacementSummaryLegendLabelFlexi", dashboardPage.getTextLanguage(LanguageCode, "daas_ui", DISK_REPLACEMENT_SUMMARY_LABELS_LIST)), "Labels of Disk Replacement Summary Chart chart did not match");

			// Test Case 8 (This test case validates the header of reports page.)
				softAssert.assertTrue(dashboardPage.headerTextVerificationOnReportPageFrameDonutChartFlexi(LanguageCode, "diskReplacementSummaryLegendLabelFlexi", "diskReplacementheaderListLocatorOnReportPageFlexi", DASHBOARD_CHART_DISK_REPLACEMENT_SUMMARY_REPORT_PAGE, "frameLocator", "diskReplacementSummaryChartVisibilityFlexi", "diskReplacementSummaryChartVisibilityFlexi","diskReplacementDropdownFlexi"), "Report header for Disk Replacement Summary chart did not match on report page.");
			
			// Test case 9 (This test case validates no data message on chart.)
			dashboardPage.scrollUpCharts();
			dashboardPage.waitForPageLoaded();
			if (!toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, username))){
				dashboardPage.clickOnElementsOfDashboardPage("companyDropdownFlexi");
				softAssert.assertTrue(dashboardPage.verifyCompanyChangeOfDashboardPage(LanguageCode, "companySearchBoxFlexi", DashboardVariables.DASHBOARD_CHART_COMPANY_NAME_MSP_US, "companyEmptyText", "companyList", "dropdownBoxCompanyFlexi"), "Company Change is not working correctly");
				softAssert.assertTrue(dashboardPage.verifyChartsHasNoDataOnDashBoardPageFlexi(LanguageCode, "diskReplacementSummaryNoDataFlexi", "dashboard.charts.title.disk_rep_summary"), "No data message for Disk Replacement Summary Chart is incorrect.");
				dashboardPage.clickOnElementsOfDashboardPage("clearCompanyFlexi");
				dashboardPage.waitForPageLoaded();
			}else{
				dashboardPage.clickOnElementsOfDashboardPage("companyDropdownGlobalFilter");
				softAssert.assertTrue(dashboardPage.verifyCompanyChangeOfDashboardPageGlobalFilter(LanguageCode, "companySearchGlobalFilter", DashboardVariables.DASHBOARD_CHART_COMPANY_NAME_MSP_US, "companyEmptyTextGlobalFilter", "companyListGlobalFilter", "outputTextGlobalFilter","globalFilterSave"), "Company Change is not working correctly");
				softAssert.assertTrue(dashboardPage.verifyChartsHasNoDataOnDashBoardPageFlexi(LanguageCode, "diskReplacementSummaryNoDataFlexi", "dashboard.charts.title.disk_rep_summary"), "No data message for Disk Replacement Summary Chart is incorrect.");				
				dashboardPage.clickOnElementsOfDashboardPage("companyDropdownGlobalFilter");
				dashboardPage.clickOnElementsOfDashboardPage("clearGlobalFilter");
				dashboardPage.clickOnElementsOfDashboardPage("globalFilterSave");
			}
			dashboardPage.scrollDownCharts();

			// Test Case 10 (This test case validates redirection to device details page from Report page.)
			Assert.assertFalse(dashboardPage.verifyChartisLoadedDynamic(LanguageCode, "diskReplacementSummaryChartFlexi", "diskReplacementSummaryFatalErrorFlexi", 5), "Disk Replacement Summary Chart did not load successfully.");
				softAssert.assertTrue(dashboardPage.verifyDeviceNameRedirectionMSPWithFrameDonutChartFlexi(LanguageCode, "diskReplacementSummaryLegendLabelFlexi", "deviceDetailsTitle", "deviceNameColumnOfDiskReplacementSummaryFlexi", "errorLocatorForNoDevice", "reportLayout", "diskReplacementSummaryChartVisibilityFlexi", "diskReplacementSummaryChartVisibilityFlexi","diskReplacementDropdownFlexi"), "Device Name Redirection is not working properly for Disk Replacement Summary chart");
			softAssert.assertAll();
			}else {
				LOGGER.info("Disk Replacement Summary Chart has no data to show");
			}			
			LOGGER.info("Validation of Disk Replacement Summary Chart completed successfully");
		}
	}

	@Test(priority = 13, groups = { "REGRESSION", "REGRESSION_CI", "ALL_CI", "ALL", "REGRESSIONDASHBOARD1" }, dataProvider = "getLoginData", description = "[197096],[197094],[197099],[197092],[197095],[197097],[197084],[213244][297778] ; Roles ~ MSP, Reseller")
	public final void verifyChartCPUUtilizationChart(String username, String password) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		ArrayList<String> CPU_MEMORY_UTILIZATION_CHART_LABELS_LIST = new ArrayList<>(Arrays.asList("global.memory.utilization.under", "global.memory.utilization.over", "dashboard.charts.device.utilization.normal" , "dashboard.charts.device.utilization.unknown"));
		final String[] DASHBOARD_CHART_CPU_MEMORY_REPORT_PAGE = { "Global.gridHeaders.companyName", "gridHeaders.Hardware_Inventory.Details.allData.lastSignedinUser", "Global.gridHeaders.deviceName", "Global.gridHeaders.serialNumber", "Global.gridHeaders.deviceType", "Global.gridHeaders.deviceManufacturer", "Global.gridHeaders.deviceModel", "Global.gridHeaders.operatingSystem", "Global.gridHeaders.manufacturerDate", "Global.gridHeaders.lastSeen", "Global.gridHeaders.processor",
				"Global.gridHeaders.memory", "gridHeaders.Device_Utilization.Software_Performance.title.maximum_allowable_capacity", "gridHeaders.Device_Utilization.Details.cpuUtilization", "gridHeaders.Device_Utilization.Details.memoryUtilization", "gridHeaders.Device_Utilization.Details.no_of_hours", "Global.gridHeaders.month", "gridHeaders.Device_Utilization.Details.no_of_overutilized_hours_CPU", "gridHeaders.Device_Utilization.Details.no_of_overutilized_hours_memory",
				"gridHeaders.Device_Utilization.Details.cpuUsage", "gridHeaders.Device_Utilization.Details.memoryUsage", "gridHeaders.Device_Utilization.Details.no_of_cpunormalhrs", "gridHeaders.Device_Utilization.Details.no_of_cpuunderutilhrs", "gridHeaders.Device_Utilization.Details.no_of_memnormalhrs", "gridHeaders.Device_Utilization.Details.no_of_memunderutilhrs","Global.gridHeaders.location_1","Global.gridHeaders.location_2","Global.gridHeaders.location_3","Global.gridHeaders.location_4" };
		login(username, password);
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();

		if (!toggleVerification(DashboardVariables.FLEXIDASHBOARD, getCredentials(environment, username))) {
			// Verify toggle status
			Assert.assertTrue(toggleVerification(DashboardVariables.CPUUTILIZATION, username), "LD-Toggle is disabled for CPU Utilization chart");
			LOGGER.info("Verified LD-Toggle status");

			// Reset current configuration of dashboard chart
			dashboardPage.clearDashboardCompanyFilter();
			dashboardPage.verifyResetToDefaultButton();
			dashboardPage.waitForPageLoaded();
			dashboardPage.scrollToDashboardPage("cpuUtilizationTitle");
			LOGGER.info("Scrolled down to cpuUtilization chart");

			// Test Case 1 (This Test case validates title of the chart.)
			softAssert.assertTrue(dashboardPage.verifyTitleofChartOfDashboardPage("cpuUtilizationTitle", dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.cpu_utilization")), "CPU Utlization Chart title did not match.");
			LOGGER.info("Verified title of the chart");

			// Test Case 2 (This test case validate whether chart is loaded or not.)
			Assert.assertFalse(dashboardPage.verifyChartisLoadedDynamic(LanguageCode, "cpuUtlizationChart", "fatalErrorCPUUtlization", 5), "CPU Utlization Chart did not load successfully.");
			LOGGER.info("Verified whether chart is loaded or not");

			// Test Case 3 (This test case validate whether chart has any data or not.)
			Assert.assertFalse(dashboardPage.verifyChartsHasNoDataOnDashBoardPage(LanguageCode, "noDataCPUUtlizationChart", "dashboard.charts.title.cpu_utilization"), "CPU UTILIZATION Chart does not have any data.");
			Assert.assertFalse(dashboardPage.verifyChartsHasNoDataDevicesEnrolledOnDashBoardPage(LanguageCode, "noDataCPUUtlizationChart", "dashboard.charts.title.cpu_utilization"), "CPU UTILIZATION Chart does not have any data.");
			LOGGER.info("Verified whether chart has any data or not");

			// Test Case 4 (This test case validates the text of tool tip present on all sub parts of chart with text on Report page.)
			softAssert.assertTrue(dashboardPage.verifyTooltipTextOnReportWithFrame("labelsLocatorCpu", "tooltipTextCPU", "cpuUtilizationTextLocator"), "Tooltip text of CPU Utlization chart did match with reports");
			LOGGER.info("Verified text of tool tip present on all sub parts of chart");

			// Test Case 5 (This test case validates the count of tool tip present on all sub parts of charts with total rows on Report page.)
			softAssert.assertTrue(dashboardPage.verifyTooltipCountonReportWithFrame("labelsLocatorCpu", "tooltipCountCPU", "columnListCPU", "paginationReportpage", "frameLocator"), "Tooltip count of CPU Utlization chart did match with reports");
			LOGGER.info("Verified count of tool tip present on all sub parts");

			// Test Case 6 (This test case validates the header of reports page.)
			softAssert.assertTrue(dashboardPage.headerTextVerificationOnReportPageFrame(LanguageCode, "labelsLocatorCpu", "headerListLocatorOnReportPage", DASHBOARD_CHART_CPU_MEMORY_REPORT_PAGE, "frameLocator"), "Report header for CPU UTILIZATION chart did not match on report page.");
			LOGGER.info("Verified the header of reports page");

			// Test Case 7 (This test case validates the labels of the chart. )
			softAssert.assertTrue(dashboardPage.verifyChartLabelsDashboardPage("cpuUtilizationLegendLabel", dashboardPage.getTextLanguage(LanguageCode, "daas_ui", CPU_MEMORY_UTILIZATION_CHART_LABELS_LIST)), "Labels of CPU Utlization Chart did not match");
			LOGGER.info("Verified the labels of the chart");

			// Test Case 8 (This test case validates hidden/visible functionality of chart.)
			softAssert.assertTrue(dashboardPage.verifyLabelHiddenWhenClickOnLegends("cpuUtilizationLegendLabel", "cpuUtilizationdrilldownLabels", "tooltipTextCPU", "cpuUtilizationdrilldownLabelsAllHidden"), "Hidden functionality is not working for CPU Utilization chart.");
			softAssert.assertTrue(dashboardPage.verifyLabelVisibleWhenClickOnLegends("cpuUtilizationLegendLabel", "cpuUtilizationdrilldownLabels", "tooltipTextCPU", "cpuUtilizationdrilldownLabelsAllHidden"), "Visible functionality is not working for CPU Utilization chart");
			LOGGER.info("Verified legends hidden/visible functionality of chart");

			// Test Case 9 (This test case validates no data message on chart.)
			dashboardPage.scrollUpCharts();
			dashboardPage.clickOnElementsOfDashboardPage("companyDropdown");
			softAssert.assertTrue(dashboardPage.verifyCompanyChangeOfDashboardPage(LanguageCode, "companySearchBox", DashboardVariables.DASHBOARD_CHART_COMPANY_NAME_MSP_US, "companyEmptyText", "companyList", "dropdownBoxCompany"), "Company Change is not working correctly");
			softAssert.assertTrue(dashboardPage.verifyChartsHasNoDataOnDashBoardPage(LanguageCode, "noDataCPUUtlizationChart", "dashboard.charts.title.cpu_utilization"), "No data message for CPU Utlization Chart is incorrect.");
			dashboardPage.clickOnElementsOfDashboardPage("clearCompany");
			dashboardPage.waitForPageLoaded();
			dashboardPage.scrollDownCharts();
			LOGGER.info("Verified charts with no data present when company with no devices is selected");

			// Test Case 10 (This test case validates redirection to device details page from Report page.)
			Assert.assertFalse(dashboardPage.verifyChartisLoadedDynamic(LanguageCode, "cpuUtlizationChart", "fatalErrorCPUUtlization", 5), "CPU Utlization Chart did not load successfully.");
			softAssert.assertTrue(dashboardPage.verifyDeviceNameRedirectionMSPWithFrame(LanguageCode, "labelsLocatorCpu", "deviceDetailsTitle", "deviceNameColumnCPU", "errorLocatorForNoDevice", "frameLocator"), "Device Name Redirection is not working properly for CPU UTILIZATION chart");
			LOGGER.info("Verified redirection to device details page from Report page");

			softAssert.assertAll();
			LOGGER.info("Validation of CPU Utilization Chart completed successfully");
		} else {
			if (!toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, username))){
				Assert.assertTrue(setToDefaultDashboard(LanguageCode),"Error occured in setting dashboard to default mode.");
			}else{
				Assert.assertTrue(setToDefaultDashboardGlobalFilter(LanguageCode),"Error occured in setting dashboard to default mode.");
			}

			dashboardPage.scrollToDashboardPage("cpuUtilizationTitleFlexi");
			LOGGER.info("Scrolled down to CPU Utilization chart");

			dashboardPage.waitForPageLoaded();
			
			// Test Case 1 (This Test case validates title of the chart.)
			softAssert.assertTrue(dashboardPage.verifyTitleofChartOfDashboardPage("cpuUtilizationTitleFlexi", dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.cpu_utilization")), "CPU Utlization Chart title did not match.");
			LOGGER.info("Verified title of the chart");

			// Test Case 2 (This test case validate whether chart is loaded or not.)
			Assert.assertFalse(dashboardPage.verifyChartisLoadedDynamic(LanguageCode, "cpuUtlizationChartFlexi", "fatalErrorCPUUtlization", 5), "CPU Utlization Chart did not load successfully.");
			LOGGER.info("Verified whether chart is loaded or not");

			// Test Case 3 (This test case validate whether chart has any data or not.)
			Assert.assertFalse(dashboardPage.verifyChartsHasNoDataOnDashBoardPage(LanguageCode, "noDataCPUUtlizationChartFlexi", "dashboard.charts.title.cpu_utilization"), "CPU UTILIZATION Chart does not have any data.");
			Assert.assertFalse(dashboardPage.verifyChartsHasNoDataDevicesEnrolledOnDashBoardPage(LanguageCode, "noDataCPUUtlizationChartFlexi", "dashboard.charts.title.cpu_utilization"), "CPU UTILIZATION Chart does not have any data.");
			LOGGER.info("Verified whether chart has any data or not");

			// Test Case 4 (This test case validates hidden/visible functionality of chart.)
				softAssert.assertTrue(dashboardPage.verifyLabelHiddenWhenClickOnLegendsDonutChartFlexi("cpuUtilizationLegendLabelFlexi", "cpuUtilizationChartVisibilityFlexi","cpuUtilizationDropdownFlexi"), "Hidden functionality is not working for CPU Utilization chart.");
				softAssert.assertTrue(dashboardPage.verifyLabelVisibleWhenClickOnLegendsDonutChartFlexi("cpuUtilizationLegendLabelFlexi", "cpuUtilizationChartVisibilityFlexi","cpuUtilizationDropdownFlexi"), "Visible functionality is not working for CPU Utilization chart");

				LOGGER.info("Verified legends hidden/visible functionality of chart");

			// Test Case 5 (This test case validates the text of tool tip present on all sub parts of chart with text on Report page.)
				softAssert.assertTrue(dashboardPage.verifyTooltipTextOnReportWithFrameDonutChartFlexi("cpuUtilizationLegendLabelFlexi", "tooltipTextCPUFlexi", "cpuUtilizationTextLocatorFlexi", "cpuUtilizationChartVisibilityFlexi", "cpuUtilizationChartVisibilityFlexi","cpuUtilizationDropdownFlexi"), "Tooltip text of CPU Utlization chart did match with reports");

			LOGGER.info("Verified text of tool tip present on all sub parts of chart");

			// Test Case 7 (This test case validates the header of reports page.)
				softAssert.assertTrue(dashboardPage.headerTextVerificationOnReportPageFrameDonutChartFlexi(LanguageCode, "cpuUtilizationLegendLabelFlexi", "cpuUtilizationheaderListLocatorOnReportPageFlexi", DASHBOARD_CHART_CPU_MEMORY_REPORT_PAGE, "frameLocator", "cpuUtilizationChartVisibilityFlexi", "cpuUtilizationChartVisibilityFlexi","cpuUtilizationDropdownFlexi"), "Report header for CPU UTILIZATION chart did not match on report page.");
			LOGGER.info("Verified the header of reports page");

			// Test Case 8 (This test case validates the labels of the chart. )
			softAssert.assertTrue(dashboardPage.verifyChartLabelsDashboardPage("cpuUtilizationLegendLabelFlexi", dashboardPage.getTextLanguage(LanguageCode, "daas_ui", CPU_MEMORY_UTILIZATION_CHART_LABELS_LIST)), "Labels of CPU Utlization Chart did not match");
			LOGGER.info("Verified the labels of the chart");

			// Test Case 9 (This test case validates no data message on chart.)
			dashboardPage.scrollUpCharts();
			if (!toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, username))){
				dashboardPage.clickOnElementsOfDashboardPage("companyDropdownFlexi");
				softAssert.assertTrue(dashboardPage.verifyCompanyChangeOfDashboardPage(LanguageCode, "companySearchBoxFlexi", DashboardVariables.DASHBOARD_CHART_COMPANY_NAME_MSP_US, "companyEmptyText", "companyList", "dropdownBoxCompanyFlexi"), "Company Change is not working correctly");
				softAssert.assertTrue(dashboardPage.verifyChartsHasNoDataOnDashBoardPageFlexi(LanguageCode, "noDataCPUUtlizationChartFlexi", "dashboard.charts.title.cpu_utilization"), "No data message for CPU Utilization Chart is incorrect.");
				dashboardPage.clickOnElementsOfDashboardPage("clearCompanyFlexi");
				dashboardPage.waitForPageLoaded();
			}else{
				dashboardPage.clickOnElementsOfDashboardPage("companyDropdownGlobalFilter");
				softAssert.assertTrue(dashboardPage.verifyCompanyChangeOfDashboardPageGlobalFilter(LanguageCode, "companySearchGlobalFilter", DashboardVariables.DASHBOARD_CHART_COMPANY_NAME_MSP_US, "companyEmptyTextGlobalFilter", "companyListGlobalFilter", "outputTextGlobalFilter","globalFilterSave"), "Company Change is not working correctly");
				softAssert.assertTrue(dashboardPage.verifyChartsHasNoDataOnDashBoardPageFlexi(LanguageCode, "noDataCPUUtlizationChartFlexi", "dashboard.charts.title.cpu_utilization"), "No data message for CPU Utilization Chart is incorrect.");				
				dashboardPage.clickOnElementsOfDashboardPage("companyDropdownGlobalFilter");
				dashboardPage.clickOnElementsOfDashboardPage("clearGlobalFilter");
				dashboardPage.clickOnElementsOfDashboardPage("globalFilterSave");
				}
		
			dashboardPage.scrollDownCharts();
			LOGGER.info("Verified charts with no data present when company with no devices is selected");

			// Test Case 10 (This test case validates redirection to device details page from Report page.)
			Assert.assertFalse(dashboardPage.verifyChartisLoadedDynamic(LanguageCode, "cpuUtlizationChartFlexi", "fatalErrorCPUUtlizationFlexi", 5), "CPU Utlization Chart did not load successfully.");
				softAssert.assertTrue(dashboardPage.verifyDeviceNameRedirectionMSPWithFrameDonutChartFlexi(LanguageCode, "cpuUtilizationLegendLabelFlexi", "deviceDetailsTitle", "deviceNameColumnCPUFlexi", "errorLocatorForNoDevice", "reportLayout", "cpuUtilizationChartVisibilityFlexi", "cpuUtilizationChartVisibilityFlexi","cpuUtilizationDropdownFlexi"), "Device Name Redirection is not working properly for CPU UTILIZATION chart");
			LOGGER.info("Verified redirection to device details page from Report page");

			softAssert.assertAll();
			LOGGER.info("Validation of CPU Utilization Chart completed successfully");
		}
	}

	@Test(priority = 14, groups = { "REGRESSION", "REGRESSION_CI", "ALL_CI", "ALL", "REGRESSIONDASHBOARD1" }, dataProvider = "getLoginData", description = "[197124],[197127],[197111],[197107],[197123],[197103],[197126],[197106],[197104],[213254] ; Roles ~ MSP, Reseller")
	public final void verifyChartMemoryUtilizationChart(String username, String password) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		ArrayList<String> CPU_MEMORY_UTILIZATION_CHART_LABELS_LIST = new ArrayList<>(Arrays.asList("global.memory.utilization.under", "global.memory.utilization.over", "dashboard.charts.device.utilization.normal", "dashboard.charts.device.utilization.unknown"));
		final String[] DASHBOARD_CHART_CPU_MEMORY_REPORT_PAGE = { "Global.gridHeaders.companyName", "gridHeaders.Hardware_Inventory.Details.allData.lastSignedinUser", "Global.gridHeaders.deviceName", "Global.gridHeaders.serialNumber", "Global.gridHeaders.deviceType", "Global.gridHeaders.deviceManufacturer", "Global.gridHeaders.deviceModel", "Global.gridHeaders.operatingSystem", "Global.gridHeaders.manufacturerDate", "Global.gridHeaders.lastSeen", "Global.gridHeaders.processor",
				"Global.gridHeaders.memory", "gridHeaders.Device_Utilization.Software_Performance.title.maximum_allowable_capacity", "gridHeaders.Device_Utilization.Details.cpuUtilization", "gridHeaders.Device_Utilization.Details.memoryUtilization", "gridHeaders.Device_Utilization.Details.no_of_hours", "Global.gridHeaders.month", "gridHeaders.Device_Utilization.Details.no_of_overutilized_hours_CPU", "gridHeaders.Device_Utilization.Details.no_of_overutilized_hours_memory",
				"gridHeaders.Device_Utilization.Details.cpuUsage", "gridHeaders.Device_Utilization.Details.memoryUsage", "gridHeaders.Device_Utilization.Details.no_of_cpunormalhrs", "gridHeaders.Device_Utilization.Details.no_of_cpuunderutilhrs", "gridHeaders.Device_Utilization.Details.no_of_memnormalhrs", "gridHeaders.Device_Utilization.Details.no_of_memunderutilhrs" ,"Global.gridHeaders.location_1","Global.gridHeaders.location_2","Global.gridHeaders.location_3","Global.gridHeaders.location_4"};
		login(username, password);
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		sleeper(3000);
		if (!toggleVerification(DashboardVariables.FLEXIDASHBOARD, getCredentials(environment, username))) {
			// Verify toggle status
			Assert.assertTrue(toggleVerification(DashboardVariables.MEMORYUTILIZATION, username), "LD-Toggle is disabled for Memory Utilization chart");
			LOGGER.info("Verified LD-Toggle status");

			// Reset current configuration of dashboard chart
			dashboardPage.clearDashboardCompanyFilter();
			dashboardPage.verifyResetToDefaultButton();
			dashboardPage.waitForPageLoaded();
			// Test Case 1 (This Test case validates title of the chart.)
			softAssert.assertTrue(dashboardPage.verifyTitleofChartOfDashboardPage("memoryUtlizationTitle", dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.memory_utilization")), "Memory Utlization Chart title did not match.");

			// Test Case 2 (This test case validate whether chart is loaded or not.)
			Assert.assertFalse(dashboardPage.verifyChartisLoadedDynamic(LanguageCode, "memoryUtlizationChart", "fatalErrorMemoryUtlization", 5), "Memory Utlization Chart did not load successfully.");

			// Test Case 8 (This test case validates hidden/visible functionality of chart.)
			softAssert.assertTrue(dashboardPage.verifyLabelHiddenWhenClickOnLegends("memoryUtilizationLegendLabel", "memoryUtilizationdrilldownLabels", "tooltipTextMemory", "memoryUtilizationdrilldownLabelsAllHidden"), "Hidden functionality is not working for Memory Utilization chart.");
			softAssert.assertTrue(dashboardPage.verifyLabelVisibleWhenClickOnLegends("memoryUtilizationLegendLabel", "memoryUtilizationdrilldownLabels", "tooltipTextMemory", "memoryUtilizationdrilldownLabelsAllHidden"), "Visible functionality is not working for Memory Utilization chart.");

			// Test Case 3 (This test case validate whether chart has any data or
			// not.)
			Assert.assertFalse(dashboardPage.verifyChartsHasNoDataOnDashBoardPage(LanguageCode, "noDataMemoryUtlizationChart", "dashboard.charts.title.memory_utilization"), "MEMORY UTILIZATION Chart does not have any data.");
			Assert.assertFalse(dashboardPage.verifyChartsHasNoDataDevicesEnrolledOnDashBoardPage(LanguageCode, "noDataMemoryUtlizationChart", "dashboard.charts.title.memory_utilization"), "MEMORY UTILIZATION Chart does not have any data.");

			// Test Case 4 (This test case validates the text of tool tip present on
			// all sub parts of chart with text on Report page.)
			softAssert.assertTrue(dashboardPage.verifyTooltipTextOnReportWithFrame("labelsLocatorMemory", "tooltipTextMemory", "memoryUtilizationTextLocator"), "Tooltip text of Memory Utlization chart did match with reports");

			// Test Case 5 (This test case validates the count of tool tip present
			// on all sub parts of charts with total rows on Report page.)
			softAssert.assertTrue(dashboardPage.verifyTooltipCountonReportWithFrame("labelsLocatorMemory", "tooltipCountMemory", "columnListMemory", "paginationReportpage", "frameLocator"), "Tooltip count of Memory Utlization chart did match with reports");

			// Test Case 6 (This test case validates the header of reports page.)
			softAssert.assertTrue(dashboardPage.headerTextVerificationOnReportPageFrame(LanguageCode, "labelsLocatorMemory", "headerListLocatorOnReportPage", DASHBOARD_CHART_CPU_MEMORY_REPORT_PAGE, "frameLocator"), "Report header for MEMORY UTILIZATION chart did not match on report page.");
			
			// Test Case 7 (This test case validates the labels of the chart. )
			softAssert.assertTrue(dashboardPage.verifyChartLabelsDashboardPage("memoryUtilizationLegendLabel", dashboardPage.getTextLanguage(LanguageCode, "daas_ui", CPU_MEMORY_UTILIZATION_CHART_LABELS_LIST)), "Labels of Memory Utlization Chart did not match");

			// Test Case 9 (This test case validates no data message on chart.)
			dashboardPage.scrollUpCharts();
			dashboardPage.clickOnElementsOfDashboardPage("companyDropdown");
			softAssert.assertTrue(dashboardPage.verifyCompanyChangeOfDashboardPage(LanguageCode, "companySearchBox", DashboardVariables.DASHBOARD_CHART_COMPANY_NAME_MSP_US, "companyEmptyText", "companyList", "dropdownBoxCompany"), "Company Change is not working correctly");
			softAssert.assertTrue(dashboardPage.verifyChartsHasNoDataOnDashBoardPage(LanguageCode, "noDataMemoryUtlizationChart", "dashboard.charts.title.memory_utilization"), "No data message for Memory Utlization Chart is incorrect.");
			dashboardPage.clickOnElementsOfDashboardPage("clearCompany");
			dashboardPage.waitForPageLoaded();
			dashboardPage.scrollDownCharts();

			// Test Case 10 (This test case validates redirection to device details
			// page from Report page.)
			Assert.assertFalse(dashboardPage.verifyChartisLoadedDynamic(LanguageCode, "memoryUtlizationChart", "fatalErrorMemoryUtlization", 5), "Memory Utlization Chart did not load successfully.");
			softAssert.assertTrue(dashboardPage.verifyDeviceNameRedirectionMSPWithFrame(LanguageCode, "labelsLocatorMemory", "deviceDetailsTitle", "deviceNameColumnMemory", "errorLocatorForNoDevice", "frameLocator"), "Device Name Redirection is not working properly for MEMORY UTILIZATION chart");

			softAssert.assertAll();
			LOGGER.info("Validation of Memory Utilization Chart completed successfully");
		} else {
			// Reset current configuration of dashboard chart
			if (!toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, username))){
				Assert.assertTrue(setToDefaultDashboard(LanguageCode),"Error occured in setting dashboard to default mode.");
			}else{
				Assert.assertTrue(setToDefaultDashboardGlobalFilter(LanguageCode),"Error occured in setting dashboard to default mode.");
			}

			dashboardPage.scrollToDashboardPage("memoryUtlizationTitleFlexi");
			LOGGER.info("Scrolled down to Memory Utilization chart");
			
			// Test Case 1 (This Test case validates title of the chart.)
			softAssert.assertTrue(dashboardPage.verifyTitleofChartOfDashboardPage("memoryUtlizationTitleFlexi", dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.memory_utilization")), "Memory Utlization Chart title did not match.");

			// Test Case 2 (This test case validate whether chart is loaded or not.)
			Assert.assertFalse(dashboardPage.verifyChartisLoadedDynamic(LanguageCode, "memoryUtlizationChartFlexi", "fatalErrorMemoryUtlizationFlexi", 5), "Memory Utlization Chart did not load successfully.");

			// Test Case 8 (This test case validates hidden/visible functionality of chart.)
				softAssert.assertTrue(dashboardPage.verifyLabelHiddenWhenClickOnLegendsDonutChartFlexi("memoryUtilizationLegendLabelFlexi", "memoryUtilizationChartVisibilityFlexi","memoryUtilizationDropdownFlexi"), "Hidden functionality is not working for CPU Utilization chart.");
				softAssert.assertTrue(dashboardPage.verifyLabelVisibleWhenClickOnLegendsDonutChartFlexi("memoryUtilizationLegendLabelFlexi", "memoryUtilizationChartVisibilityFlexi","memoryUtilizationDropdownFlexi"), "Visible functionality is not working for CPU Utilization chart");
			LOGGER.info("Verified legends hidden/visible functionality of chart");
			
			
			// Test Case 3 (This test case validate whether chart has any data or
			// not.)
			Assert.assertFalse(dashboardPage.verifyChartsHasNoDataOnDashBoardPage(LanguageCode, "noDataMemoryUtlizationChartFlexi", "dashboard.charts.title.memory_utilization"), "MEMORY UTILIZATION Chart does not have any data.");
			Assert.assertFalse(dashboardPage.verifyChartsHasNoDataDevicesEnrolledOnDashBoardPage(LanguageCode, "noDataMemoryUtlizationChartFlexi", "dashboard.charts.title.memory_utilization"), "MEMORY UTILIZATION Chart does not have any data.");

			// Test Case 6 (This test case validates the header of reports page.)
				softAssert.assertTrue(dashboardPage.headerTextVerificationOnReportPageFrameDonutChartFlexi(LanguageCode, "memoryUtilizationLegendLabelFlexi", "memoryUtilizationheaderListLocatorOnReportPageFlexi", DASHBOARD_CHART_CPU_MEMORY_REPORT_PAGE, "frameLocator", "memoryUtilizationChartVisibilityFlexi", "memoryUtilizationChartVisibilityFlexi","memoryUtilizationDropdownFlexi"), "Report header for Memory UTILIZATION chart did not match on report page.");
			// Test Case 7 (This test case validates the labels of the chart. )
			softAssert.assertTrue(dashboardPage.verifyChartLabelsDashboardPage("memoryUtilizationLegendLabelFlexi", dashboardPage.getTextLanguage(LanguageCode, "daas_ui", CPU_MEMORY_UTILIZATION_CHART_LABELS_LIST)), "Labels of Memory Utlization Chart did not match");

			// Test Case 9 (This test case validates no data message on chart.)
			dashboardPage.scrollUpCharts();
			if (!toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, username))){
				dashboardPage.clickOnElementsOfDashboardPage("companyDropdownFlexi");
				softAssert.assertTrue(dashboardPage.verifyCompanyChangeOfDashboardPage(LanguageCode, "companySearchBoxFlexi", DashboardVariables.DASHBOARD_CHART_COMPANY_NAME_MSP_US, "companyEmptyText", "companyList", "dropdownBoxCompanyFlexi"), "Company Change is not working correctly");
				softAssert.assertTrue(dashboardPage.verifyChartsHasNoDataOnDashBoardPageFlexi(LanguageCode, "noDataMemoryUtlizationChartFlexi", "dashboard.charts.title.memory_utilization"), "No data message for Memory Utilization Chart is incorrect.");
				dashboardPage.clickOnElementsOfDashboardPage("clearCompanyFlexi");
				dashboardPage.waitForPageLoaded();
			}else{
				dashboardPage.clickOnElementsOfDashboardPage("companyDropdownGlobalFilter");
				softAssert.assertTrue(dashboardPage.verifyCompanyChangeOfDashboardPageGlobalFilter(LanguageCode, "companySearchGlobalFilter", DashboardVariables.DASHBOARD_CHART_COMPANY_NAME_MSP_US, "companyEmptyTextGlobalFilter", "companyListGlobalFilter", "outputTextGlobalFilter","globalFilterSave"), "Company Change is not working correctly");
				softAssert.assertTrue(dashboardPage.verifyChartsHasNoDataOnDashBoardPageFlexi(LanguageCode, "noDataMemoryUtlizationChartFlexi", "dashboard.charts.title.memory_utilization"), "No data message for Memory Utilization Chart is incorrect.");				
				dashboardPage.clickOnElementsOfDashboardPage("companyDropdownGlobalFilter");
				dashboardPage.clickOnElementsOfDashboardPage("clearGlobalFilter");
				dashboardPage.clickOnElementsOfDashboardPage("globalFilterSave");	
				}
			dashboardPage.scrollDownCharts();

			// Test Case 10 (This test case validates redirection to device details
			// page from Report page.)
			Assert.assertFalse(dashboardPage.verifyChartisLoadedDynamic(LanguageCode, "memoryUtlizationChartFlexi", "fatalErrorMemoryUtlizationFlexi", 5), "Memory Utlization Chart did not load successfully.");
				softAssert.assertTrue(dashboardPage.verifyDeviceNameRedirectionMSPWithFrameDonutChartFlexi(LanguageCode, "memoryUtilizationLegendLabelFlexi", "deviceDetailsTitle", "deviceNameColumnMemoryFlexi", "errorLocatorForNoDevice", "reportLayout", "memoryUtilizationChartVisibilityFlexi", "memoryUtilizationChartVisibilityFlexi","memoryUtilizationDropdownFlexi"), "Device Name Redirection is not working properly for MEMORY UTILIZATION chart");
			softAssert.assertAll();
			LOGGER.info("Validation of Memory Utilization Chart completed successfully");
		}
	}

	@Test(priority = 15, groups = { "REGRESSION", "REGRESSION_CI", "ALL_CI", "ALL", "REGRESSIONDASHBOARD1" }, dataProvider = "getLoginData", description = "[196593],[196601],[196598],[196595],[196592],[196591],[213255] ; Roles ~ MSP, Reseller")
	public final void verifyChartHardwareInventoryForPartner(String username, String password) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		final String[] DASHBOARD_CHART_DEVICE_TYPE_OS_HW_REPORT_PAGE = { "Global.gridHeaders.companyName", "gridHeaders.Hardware_Inventory.Details.allData.lastSignedinUser", "Global.gridHeaders.enrolledmonth", "Global.gridHeaders.deviceName", "Global.gridHeaders.serialNumber", "Global.gridHeaders.deviceType", "Global.gridHeaders.deviceManufacturer", "Global.gridHeaders.deviceModel", "Global.gridHeaders.operatingSystem", "gridHeaders.hardware_Inventory.Details.operating_system_release",
				"gridHeaders.hardware_Inventory.Details.operating_system_Build_number", "gridHeaders.Hardware_Inventory.Details.win10_patch_status", "Global.gridHeaders.lastSeen", "Global.gridHeaders.memory", "gridHeaders.Hardware_Inventory.Details.graphics", "Global.gridHeaders.processor", "Global.gridHeaders.manufacture_date", "gridHeaders.hardware_Inventory.Details.enrolledDate", "gridHeaders.Incident_Widget.Details.warStatus", "gridHeaders.hardware_Inventory.Details.warrantyEndDate",
				"Global.gridHeaders.country", "Global.gridHeaders.department", "Global.gridHeaders.costCenter", "Global.gridHeaders.alias", "Global.gridHeaders.asset_tag", "graphHeaders.Hardware_Inventory.Details.lifecycle_status", "graphHeaders.Hardware_Inventory.Details.device_role", "graphHeaders.Hardware_Inventory.Details.component", "graphHeaders.Hardware_Inventory.Details.component_serial_number",
				"graphHeaders.Hardware_Inventory.Details.component_repl_timeframe", "graphHeaders.Hardware_Inventory.Details.component_Model", "graphHeaders.Hardware_Inventory.Details.component_size_gb","Global.gridHeaders.location_1","Global.gridHeaders.location_2","Global.gridHeaders.location_3","Global.gridHeaders.location_4" };

		login(username, password);
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();

		if (!toggleVerification(DashboardVariables.FLEXIDASHBOARD, getCredentials(environment, username))) {
			// Verify toggle status
			Assert.assertTrue(toggleVerification(DashboardVariables.HARDWAREINVENTORY, username), "LD-Toggle is disabled for Hardware Inventory Chart");
			LOGGER.info("Verified LD-Toggle status");

			// Reset current configuration of dashboard chart
			dashboardPage.clearDashboardCompanyFilter();
			dashboardPage.verifyResetToDefaultButton();
			dashboardPage.waitForPageLoaded();
			dashboardPage.scrollToDashboardPage("hardwareInventoryTitle");
			LOGGER.info("Scrolled down to Hardware Inventory chart");

			dashboardPage.waitForPageLoaded();
			// Test Case 1 (This Test case validates title of the chart.)
			softAssert.assertTrue(dashboardPage.verifyTitleofChartOfDashboardPage("hardwareInventoryTitle", dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.hardware_invenory")), "Hardware Inventory Chart title did not match.");

			// Test Case 2 (This test case validate whether chart is loaded or not.)
			Assert.assertFalse(dashboardPage.verifyChartisLoadedDynamic(LanguageCode, "hardwareInventoryChart", "fatalErrorHardwareInventory", 5), "Hardware Inventory Chart did not load successfully.");

			// Test Case 3 (This test case validate whether chart has any data or
			// not.)
			Assert.assertFalse(dashboardPage.verifyChartsHasNoDataOnDashBoardPage(LanguageCode, "noDataHardwareInventoryChart", "dashboard.charts.title.hardware_invenory"), "HARDWARE INVENTORY Chart does not have any data.");
			Assert.assertFalse(dashboardPage.verifyChartsHasNoDataDevicesEnrolledOnDashBoardPage(LanguageCode, "noDataHardwareInventoryChart", "dashboard.charts.title.hardware_invenory"), "HARDWARE INVENTORY Chart does not have any data.");

			// Test Case 4 (This test case validates the text of tool tip present on
			// all sub parts of chart with text on Report page.)
			softAssert.assertTrue(dashboardPage.verifyTooltipTextOnReportWithFrame("labelsLocatorHardware", "tooltipTextHardware", "monthTextLocator"), "Tooltip text of Hardware Inventory chart did match with reports");

			// Test Case 6 (This test case validates the header of reports page.)
			dashboardPage.scrollToDashboardPage("hardwareInventoryTitle");
			softAssert.assertTrue(dashboardPage.headerTextVerificationOnReportPageFrame(LanguageCode, "labelsLocatorHardware", "headerListLocatorOnReportPage", DASHBOARD_CHART_DEVICE_TYPE_OS_HW_REPORT_PAGE, "frameLocator"), "Report header for HARDWARE INVENTORY chart did not match on report page.");

			// Test Case 7 (This test case validates no data message on chart.)
			dashboardPage.scrollUpCharts();
			dashboardPage.clickOnElementsOfDashboardPage("companyDropdown");
			softAssert.assertTrue(dashboardPage.verifyCompanyChangeOfDashboardPage(LanguageCode, "companySearchBox", DashboardVariables.DASHBOARD_CHART_COMPANY_NAME_MSP_US, "companyEmptyText", "companyList", "dropdownBoxCompany"), "Company Change is not working correctly");
			softAssert.assertTrue(dashboardPage.verifyChartsHasNoDataOnDashBoardPage(LanguageCode, "noDataHardwareInventoryChart", "dashboard.charts.title.hardware_invenory"), "No data message for Hardware Inventory Chart is incorrect.");
			dashboardPage.clickOnElementsOfDashboardPage("clearCompany");
			dashboardPage.waitForPageLoaded();
			dashboardPage.scrollDownCharts();

			// Test Case 8 (This test case validates redirection to device details
			// page from Report page.)
			Assert.assertFalse(dashboardPage.verifyChartisLoadedDynamic(LanguageCode, "hardwareInventoryChart", "fatalErrorHardwareInventory", 5), "Hardware Inventory Chart did not load successfully.");
			softAssert.assertTrue(dashboardPage.verifyDeviceNameRedirectionMSPWithFrame(LanguageCode, "labelsLocatorHardware", "deviceDetailsTitle", "deviceNameColumnHW", "errorLocatorForNoDevice", "frameLocator"), "Device Name Redirection is not working properly for HARDWARE INVENTORY chart");

			softAssert.assertAll();
			LOGGER.info("Validation of Hardware Inventory Chart completed successfully");
		} else {

			if (!toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, username))){
				Assert.assertTrue(setToDefaultDashboard(LanguageCode),"Error occured in setting dashboard to default mode.");
			}else{
				Assert.assertTrue(setToDefaultDashboardGlobalFilter(LanguageCode),"Error occured in setting dashboard to default mode.");
			}

			dashboardPage.scrollToDashboardPage("hardwareInventoryTitleFlexi");
			LOGGER.info("Scrolled down to Hardware Inventory chart");

			dashboardPage.waitForPageLoaded();
			if(!(dashboardPage.verifyElementsOfDashboardPage("noDataHardwareInventoryChartFlexi"))) {
			// Test Case 1 (This Test case validates title of the chart.)
			softAssert.assertTrue(dashboardPage.verifyTitleofChartOfDashboardPage("hardwareInventoryTitleFlexi", dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.hardware_invenory")), "Hardware Inventory Chart title did not match.");

			// Test Case 2 (This test case validate whether chart is loaded or not.)
			Assert.assertFalse(dashboardPage.verifyChartisLoadedDynamic(LanguageCode, "hardwareInventoryChartFlexi", "fatalErrorHardwareInventory", 5), "Hardware Inventory Chart did not load successfully.");

			// Test Case 3 (This test case validate whether chart has any data or
			// not.)
			Assert.assertFalse(dashboardPage.verifyChartsHasNoDataOnDashBoardPageFlexi(LanguageCode, "noDataHardwareInventoryChartFlexi", "dashboard.charts.title.hardware_invenory"), "HARDWARE INVENTORY Chart does not have any data.");

			// Commented this test case due to scroll added in this chart, this will hinder automation of chart.
			// Test Case 4 (This test case validates the text of tool tip present on
			// all sub parts of chart with text on Report page.)
//			softAssert.assertTrue(dashboardPage.verifyTooltipTextOnReportHWInv("labelsLocatorHardwareFlexi", "tooltipTextHardwareFlexi", "monthTextLocator"), "Tooltip text of Hardware Inventory chart did match with reports");
//
//			// Test Case 6 (This test case validates the header of reports page.)
//			softAssert.assertTrue(dashboardPage.headerTextVerificationOnReportPageFrame(LanguageCode, "labelsLocatorHardwareFlexi", "headerListLocatorOnReportPageFlexi", DASHBOARD_CHART_DEVICE_TYPE_OS_HW_REPORT_PAGE, "frameLocator"), "Report header for HARDWARE INVENTORY chart did not match on report page.");

			// Test Case 7 (This test case validates no data message on chart.)
			dashboardPage.scrollUpCharts();
			if (!toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, username))){
				dashboardPage.clickOnElementsOfDashboardPage("companyDropdownFlexi");
				softAssert.assertTrue(dashboardPage.verifyCompanyChangeOfDashboardPage(LanguageCode, "companySearchBoxFlexi", DashboardVariables.DASHBOARD_CHART_COMPANY_NAME_MSP_US, "companyEmptyText", "companyList", "dropdownBoxCompanyFlexi"), "Company Change is not working correctly");
				softAssert.assertTrue(dashboardPage.verifyChartsHasNoDataOnDashBoardPageFlexi(LanguageCode, "noDataHardwareInventoryChartFlexi", "dashboard.charts.title.devices_by_type"), "No data message for Hardware Inventory Chart is incorrect.");
				dashboardPage.clickOnElementsOfDashboardPage("clearCompanyFlexi");
				dashboardPage.waitForPageLoaded();
			}else{
				dashboardPage.clickOnElementsOfDashboardPage("companyDropdownGlobalFilter");
				softAssert.assertTrue(dashboardPage.verifyCompanyChangeOfDashboardPageGlobalFilter(LanguageCode, "companySearchGlobalFilter", DashboardVariables.DASHBOARD_CHART_COMPANY_NAME_MSP_US, "companyEmptyTextGlobalFilter", "companyListGlobalFilter", "outputTextGlobalFilter","globalFilterSave"), "Company Change is not working correctly");
				softAssert.assertTrue(dashboardPage.verifyChartsHasNoDataOnDashBoardPageFlexi(LanguageCode, "noDataHardwareInventoryChartFlexi", "dashboard.charts.title.hardware_invenory"), "No data message for Hardware Inventory Chart is incorrect.");				
				dashboardPage.clickOnElementsOfDashboardPage("companyDropdownGlobalFilter");
				dashboardPage.clickOnElementsOfDashboardPage("clearGlobalFilter");
				dashboardPage.clickOnElementsOfDashboardPage("globalFilterSave");
			}
			dashboardPage.scrollDownCharts();

			// Test Case 8 (This test case validates redirection to device details
			// page from Report page.)
			Assert.assertFalse(dashboardPage.verifyChartisLoadedDynamic(LanguageCode, "hardwareInventoryChartFlexi", "fatalErrorHardwareInventory", 5), "Hardware Inventory Chart did not load successfully.");
			//softAssert.assertTrue(dashboardPage.verifyDeviceNameRedirectionMSPWithFrame(LanguageCode, "labelsLocatorHardwareFlexi", "deviceDetailsTitle", "deviceNameColumnHW", "errorLocatorForNoDevice", "reportLayout"), "Device Name Redirection is not working properly for HARDWARE INVENTORY chart");
			softAssert.assertAll();
			}else {
				LOGGER.info("Hardware Inventory Chart has no data to show");
			}
			LOGGER.info("Validation of Hardware Inventory Chart completed successfully");
		}
	}

	@Test(priority = 16, groups = { "REGRESSION", "REGRESSION_CI", "ALL_CI", "ALL", "REGRESSIONDASHBOARD1" }, dataProvider = "getLoginData", description = "[198913],[198916],[198915],[198907],[198911],[198906],[198905],[198918],[198904],[213274] ; Roles ~ MSP, Reseller")
	public final void verifyChartSoftwareInventoryForPartner(String username, String password) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		final String[] DASHBOARD_CHART_SOFTWARE_INVENTORY_REPORT_PAGE = { "Global.gridHeaders.companyName", "Global.gridHeaders.operatingSystem", "Global.gridHeaders.appName", "gridHeaders.Software_Inventory.Summary.installedDevices", "Global.gridHeaders.total_devices", "gridHeaders.Software_Inventory.Summary.installed_Percent","Global.gridHeaders.location_1","Global.gridHeaders.location_2","Global.gridHeaders.location_3","Global.gridHeaders.location_4" };
		login(username, password);
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();

		if (!toggleVerification(DashboardVariables.FLEXIDASHBOARD, getCredentials(environment, username))) {
			// Verify toggle status
			Assert.assertTrue(toggleVerification(DashboardVariables.HARDWAREINVENTORY, username), "LD-Toggle is disabled for Hardware Inventory Chart");
			LOGGER.info("Verified LD-Toggle status");

			// Reset current configuration of dashboard chart
			dashboardPage.clearDashboardCompanyFilter();
			dashboardPage.verifyResetToDefaultButton();
			dashboardPage.waitForPageLoaded();
			dashboardPage.scrollToDashboardPage("softwareInventoryTitle");
			LOGGER.info("Scrolled down to Software Inventory chart");

			dashboardPage.waitForPageLoaded();
			// Test Case 1 (This Test case validates title of the chart.)
			softAssert.assertTrue(dashboardPage.verifyTitleofChartOfDashboardPage("softwareInventoryTitle", dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.software_invenory")), "Software Inventory Chart title did not match.");

			// Test Case 2 (This test case validate whether chart is loaded or not.)
			Assert.assertFalse(dashboardPage.verifyChartisLoadedDynamic(LanguageCode, "softwareInventoryChart", "fatalErrorSoftwareInventory", 5), "Software Inventory Chart did not load successfully.");

			// Test Case 3 (This test case validate whether chart has any data or not.)
			Assert.assertFalse(dashboardPage.verifyChartsHasNoDataOnDashBoardPage(LanguageCode, "noDataSoftwareInventoryChart", "dashboard.charts.title.software_invenory"), "SOFTWARE INVENTORY Chart does not have any data.");
			Assert.assertFalse(dashboardPage.verifyChartsHasNoDataDevicesEnrolledOnDashBoardPage(LanguageCode, "noDataSoftwareInventoryChart", "dashboard.charts.title.software_invenory"), "SOFTWARE INVENTORY Chart does not have any data.");

			// Test Case 4 (This test case validates the text of tool tip present on all sub
			// parts of chart with text on Report page.)
			softAssert.assertTrue(dashboardPage.verifyChartSoftwareInventoryTooltext("softwareInventoryLegends", "nodataSoftwareInventory", "labelsLocatorSoftwarevisiblebyos", "tooltipTextSoftware", "SoftwareInventoryTextLocator"), "Tooltip text of Memory Utlization chart did match with reports");

			// Test Case 5 (This test case validates the count of tool tip present on all
			// sub parts of charts with total rows on Report page.)
			softAssert.assertTrue(dashboardPage.verifyChartSoftwareInventoryTooltipCount("softwareInventoryLegends", "nodataSoftwareInventory", "labelsLocatorSoftwarevisiblebyos", "tooltipCountSoftware", "columnlistSoftwareForCount", "frameLocator", "tooltipTextSoftware"), "Tooltip count of SOFTWARE INVENTORY chart did match with reports");

			// Test Case 6 (This test case validates the header of reports page.)
			softAssert.assertTrue(dashboardPage.headerTextVerificationOnReportPageFrame(LanguageCode, "labelsLocatorSoftware", "headerListLocatorOnReportPageFlexi", DASHBOARD_CHART_SOFTWARE_INVENTORY_REPORT_PAGE, "frameLocator"), "Report header for SOFTWARE INVENTORY chart did not match on report page.");

			// Test Case 7 (This test case validates hidden/visible functionality of chart.)
			// softAssert.assertTrue(dashboardPage.verifyLabelHiddenWhenClickOnLegendsBar("SoftwareInventoryLegendLabel", "SoftwareInventoryToolTipHover",
			// "SoftwareInventoryToolTipHoverText", "nodataSoftwareInventory"), "Hidden functionality is not working for Software Inventory chart.");
			// softAssert.assertTrue(dashboardPage.verifyLabelVisibleWhenClickOnLegendsBar("SoftwareInventoryLegendLabel", "SoftwareInventoryToolTipHover",
			// "SoftwareInventoryToolTipHoverText", "nodataSoftwareInventory"), "Visible functionality is not working for Software Inventory chart.");

			// Test Case 8 (This test case validates no data message on chart.)
			dashboardPage.scrollUpCharts();
			dashboardPage.clickOnElementsOfDashboardPage("companyDropdown");
			softAssert.assertTrue(dashboardPage.verifyCompanyChangeOfDashboardPage(LanguageCode, "companySearchBox", DashboardVariables.DASHBOARD_CHART_COMPANY_NAME_MSP_US, "companyEmptyText", "companyList", "dropdownBoxCompany"), "Company Change is not working correctly");
			softAssert.assertTrue(dashboardPage.verifyChartsHasNoDataOnDashBoardPage(LanguageCode, "noDataSoftwareInventoryChart", "dashboard.charts.title.software_invenory"), "No data message for Software Inventory Chart is incorrect.");
			dashboardPage.clickOnElementsOfDashboardPage("clearCompany");
			dashboardPage.waitForPageLoaded();
			dashboardPage.scrollDownCharts();

			softAssert.assertAll();
			LOGGER.info("Validation of Software Inventory Chart completed successfully");
		}else{
			if (!toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, username))){
				Assert.assertTrue(setToDefaultDashboard(LanguageCode),"Error occured in setting dashboard to default mode.");
			}else{
				Assert.assertTrue(setToDefaultDashboardGlobalFilter(LanguageCode),"Error occured in setting dashboard to default mode.");
			}

			dashboardPage.scrollToDashboardPage("softwareInventoryTitleFlexi");
			LOGGER.info("Scrolled down to Software Inventory chart");

			dashboardPage.waitForPageLoaded();
			// Test Case 1 (This Test case validates title of the chart.)
			softAssert.assertTrue(dashboardPage.verifyTitleofChartOfDashboardPage("softwareInventoryTitleFlexi", dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.software_invenory")), "Software Inventory Chart title did not match.");

			// Test Case 2 (This test case validate whether chart is loaded or not.)
			Assert.assertFalse(dashboardPage.verifyChartisLoadedDynamic(LanguageCode, "softwareInventoryChartFlexi", "fatalErrorSoftwareInventory", 5), "Software Inventory Chart did not load successfully.");

			// Test Case 3 (This test case validate whether chart has any data or not.)
			Assert.assertFalse(dashboardPage.verifyChartsHasNoDataOnDashBoardPageFlexi(LanguageCode, "noDataSoftwareInventoryChartFlexi", "dashboard.charts.title.software_invenory"), "SOFTWARE INVENTORY Chart does not have any data.");

			// Test Case 4 (This test case validates the text of tool tip present on all sub
			// parts of chart with text on Report page.)
			softAssert.assertTrue(dashboardPage.verifyChartSoftwareInventoryTooltext("softwareInventoryLegendsFlexi", "noDataSoftwareInventoryChartFlexi", "labelsLocatorSoftwarevisiblebyosFlexi", "tooltipTextSoftwareFlexi", "SoftwareInventoryTextLocator"), "Tooltip text of Software Inventory chart did match with reports");

			// Test Case 5 (This test case validates the count of tool tip present on all
			// sub parts of charts with total rows on Report page.)
			softAssert.assertTrue(dashboardPage.verifyChartSoftwareInventoryTooltipCountFlexi("softwareInventoryLegendsFlexi", "noDataSoftwareInventoryChartFlexi", "labelsLocatorSoftwarevisiblebyosFlexi", "tooltipCountSoftwareFlexi", "columnlistSoftwareForCountFlexi", "frameLocator", "tooltipTextSoftwareFlexi"), "Tooltip count of SOFTWARE INVENTORY chart did match with reports");

			// Test Case 6 (This test case validates the header of reports page.)
			softAssert.assertTrue(dashboardPage.headerTextVerificationOnReportPageFrame(LanguageCode, "labelsLocatorSoftwarevisiblebyosFlexi", "headerListLocatorOnReportPageFlexi", DASHBOARD_CHART_SOFTWARE_INVENTORY_REPORT_PAGE, "frameLocator"), "Report header for SOFTWARE INVENTORY chart did not match on report page.");

			// Test Case 7 (This test case validates hidden/visible functionality of chart.)
			// softAssert.assertTrue(dashboardPage.verifyLabelHiddenWhenClickOnLegendsBar("SoftwareInventoryLegendLabelFlexi", "SoftwareInventoryToolTipHoverFlexi",
			// "SoftwareInventoryToolTipHoverTextFlexi", "noDataSoftwareInventoryChartFlexi"), "Hidden functionality is not working for Software Inventory chart.");
			// softAssert.assertTrue(dashboardPage.verifyLabelVisibleWhenClickOnLegendsBar("SoftwareInventoryLegendLabelFlexi", "SoftwareInventoryToolTipHoverFlexi",
			// "SoftwareInventoryToolTipHoverTextFlexi", "noDataSoftwareInventoryChartFlexi"), "Visible functionality is not working for Software Inventory chart.");

			// Test Case 8 (This test case validates no data message on chart.)
			dashboardPage.scrollUpCharts();
			if (!toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, username))){
				dashboardPage.clickOnElementsOfDashboardPage("companyDropdownFlexi");
				softAssert.assertTrue(dashboardPage.verifyCompanyChangeOfDashboardPage(LanguageCode, "companySearchBoxFlexi", DashboardVariables.DASHBOARD_CHART_COMPANY_NAME_MSP_US, "companyEmptyText", "companyList", "dropdownBoxCompanyFlexi"), "Company Change is not working correctly");
				softAssert.assertTrue(dashboardPage.verifyChartsHasNoDataOnDashBoardPageFlexi(LanguageCode, "noDataSoftwareInventoryChartFlexi", "dashboard.charts.title.devices_by_type"), "No data message for Software Inventory  Chart is incorrect.");
				dashboardPage.clickOnElementsOfDashboardPage("clearCompanyFlexi");
				dashboardPage.waitForPageLoaded();
			}else{
				dashboardPage.clickOnElementsOfDashboardPage("companyDropdownGlobalFilter");
				softAssert.assertTrue(dashboardPage.verifyCompanyChangeOfDashboardPageGlobalFilter(LanguageCode, "companySearchGlobalFilter", DashboardVariables.DASHBOARD_CHART_COMPANY_NAME_MSP_US, "companyEmptyTextGlobalFilter", "companyListGlobalFilter", "outputTextGlobalFilter","globalFilterSave"), "Company Change is not working correctly");
				softAssert.assertTrue(dashboardPage.verifyChartsHasNoDataOnDashBoardPageFlexi(LanguageCode, "noDataSoftwareInventoryChartFlexi", "dashboard.charts.title.software_invenory"), "No data message for Software Inventory Chart is incorrect.");				
				dashboardPage.clickOnElementsOfDashboardPage("companyDropdownGlobalFilter");
				dashboardPage.clickOnElementsOfDashboardPage("clearGlobalFilter");
				dashboardPage.clickOnElementsOfDashboardPage("globalFilterSave");
				
			}
			dashboardPage.scrollDownCharts();

			softAssert.assertAll();
			LOGGER.info("Validation of Software Inventory Chart completed successfully");
		}
	}

	@Test(priority = 17, dataProvider = "getLoginData", groups = { "REGRESSION", "ALL_CI", "ALL", "SMOKE", "SMOKE_CI", "REGRESSIONDASHBOARD1", "STABILIZATION_STAGING" }, description = "[201909],[201910][201911],[201912],[201913],[201914],[201915] ; Roles ~ MSP, Reseller")
	public final void verifySubscriptionExpirationForPartner(String username, String password) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		ArrayList<String> SUBSCRIPTION_EXPIRATION_LABELS_LIST = new ArrayList<>(Arrays.asList("dashboard.charts.timeframe.na", "dashboard.charts.timeframe.thirty_days", "dashboard.charts.timeframe.sixty_days", "dashboard.charts.timeframe.ninety_days", "dashboard.charts.timeframe.one_year", "dashboard.charts.timeframe.two_years", "dashboard.charts.timeframe.three_years", "dashboard.charts.timeframe.more_than_three_years"));
		final String[] DASHBOARD_CHART_SUBSCRIPTION_EXPIRATION_REPORT_PAGE = { "Global.gridHeaders.companyName", "gridHeaders.Patch_Compliance.Details.product", "gridHeaders.Subscription_Expiry.Details.subscription_key", "gridHeaders.Subscription_Expiry.Details.start_date", "gridHeaders.Subscription_Expiry.Details.expiration_date", "gridHeaders.Subscription_Expiry.Details.term", "gridHeaders.Subscription_Expiry.Details.seats" };

		login(username, password);
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();

		if (!toggleVerification(DashboardVariables.FLEXIDASHBOARD, getCredentials(environment, username))) {

			// Verify toggle status
			Assert.assertTrue(toggleVerification(DashboardVariables.SUBSCRIPTIONEXPIRATION, username), "LD-Toggle is disabled for Subscription Expiration chart");
			LOGGER.info("Verified LD-Toggle status");

			// Reset current configuration of dashboard chart
			dashboardPage.clearDashboardCompanyFilter();
			dashboardPage.verifyResetToDefaultButton();
			dashboardPage.waitForPageLoaded();
			dashboardPage.scrollToDashboardPage("subscriptionExpirationTitle");
			LOGGER.info("Scrolled down to Subscription Expiration chart");

			dashboardPage.waitForPageLoaded();
			// Test Case 1 (This Test case validates title of the chart.)
			softAssert.assertTrue(dashboardPage.verifyTitleofChartOfDashboardPage("subscriptionExpirationTitle", dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.subscription_expiration")), "Subscription Expiration Chart title did not match.");

			// Test Case 2 (This test case validate whether chart is loaded or not.)
			Assert.assertFalse(dashboardPage.verifyChartisLoadedDynamic(LanguageCode, "subscriptionExpirationChart", "subscriptionExpirationFatalError", 5), "Subscription Expiration Chart did not load successfully.");

			// Test Case 3 (This test case validate whether chart has any data or
			// not.)
			Assert.assertFalse(dashboardPage.verifyChartsHasNoDataOnDashBoardPage(LanguageCode, "subscriptionExpirationNoData", "dashboard.charts.title.subscription_expiration"), "Subscription Expiration Chart does not have any data.");
			Assert.assertFalse(dashboardPage.verifyChartsHasNoDataDevicesEnrolledOnDashBoardPage(LanguageCode, "subscriptionExpirationNoData", "dashboard.charts.title.subscription_expiration"), "Subscription Expiration Chart does not have any data.");

			// Test case 6 (This test case validates the header of reports page.)
			softAssert.assertTrue(dashboardPage.headerTextVerificationOnReportPageFrame(LanguageCode, "subscriptionExpirationXaxisLabels", "headerListLocatorOnReportPage", DASHBOARD_CHART_SUBSCRIPTION_EXPIRATION_REPORT_PAGE, "frameLocator"), "Report header for Subscription Expiration chart did not match on report page.");

			// Test Case 5 (This test case validates the count of tool tip present on all
			// sub parts of charts with total rows on Report page.)
			softAssert.assertTrue(dashboardPage.verifyTooltipCountOnReportwithFrameSubscriptionExpiration("tooltipHoverSubscriptionExpiration", "tooltipCountSubscriptionExpiration", "columnlistSubscriptionExpirationForCount", "frameLocator"), "Tooltip count of SOFTWARE INVENTORY chart did match with reports");

			// Test case 6 (This test case validates the header of reports page.)
			softAssert.assertTrue(dashboardPage.headerTextVerificationOnReportPageFrame(LanguageCode, "subscriptionExpirationXaxisLabels", "headerListLocatorOnReportPage", DASHBOARD_CHART_SUBSCRIPTION_EXPIRATION_REPORT_PAGE, "frameLocator"), "Report header for Subscription Expiration chart did not match on report page.");

			// Test Case 4 (This test case validates the text of tool tip present on all sub
			// parts of chart with text on Report page.)
			softAssert.assertTrue(dashboardPage.verifyTooltipTextOnReportWithFrame("tooltipHoverSubscriptionExpiration", "subscriptionExpirationToolTipHoverText", "subscriptionExpirationTextLocator"), "Tooltip text of Subscription Expiration chart did match with reports");

			// Test Case 7 (This test case validates the labels of the chart. )
			softAssert.assertTrue(dashboardPage.verifyChartLabelsDashboardPage("subscriptionExpirationXaxisLabels", dashboardPage.getTextLanguage(LanguageCode, "daas_ui", SUBSCRIPTION_EXPIRATION_LABELS_LIST)), "Labels of Subscription Expiration Chart chart did not match");

			// Test case 8 (This test case validates no data message on chart.)
			dashboardPage.scrollUpCharts();
			dashboardPage.clickOnElementsOfDashboardPage("companyDropdown");
			softAssert.assertTrue(dashboardPage.verifyCompanyChangeOfDashboardPage(LanguageCode, "companySearchBox", DashboardVariables.DASHBOARD_CHART_COMPANY_NAME_MSP_US, "companyEmptyText", "companyList", "dropdownBoxCompany"), "Company Change is not working correctly");
			softAssert.assertTrue(dashboardPage.verifyChartsHasNoDataOnDashBoardPage(LanguageCode, "subscriptionExpirationNoData", "dashboard.charts.title.subscription_expiration"), "No data message for Subscription Expiration Chart is incorrect.");
			dashboardPage.clickOnElementsOfDashboardPage("clearCompany");
			dashboardPage.waitForPageLoaded();

			softAssert.assertAll();
			LOGGER.info("Validation of Subscription Expiration Chart completed successfully");

		}else {
			if (!toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, username))){
				Assert.assertTrue(setToDefaultDashboard(LanguageCode),"Error occured in setting dashboard to default mode.");
			}else{
				Assert.assertTrue(setToDefaultDashboardGlobalFilter(LanguageCode),"Error occured in setting dashboard to default mode.");
			}

			// Verify toggle status
			Assert.assertTrue(toggleVerification(DashboardVariables.SUBSCRIPTIONEXPIRATION, username), "LD-Toggle is disabled for Subscription Expiration chart");
			LOGGER.info("Verified LD-Toggle status");

			// Reset current configuration of dashboard chart
			dashboardPage.waitForPageLoaded();
			dashboardPage.scrollToDashboardPage("subscriptionExpirationTitleFlexi");
			LOGGER.info("Scrolled down to Subscription Expiration chart");

			dashboardPage.waitForPageLoaded();
			// Test Case 1 (This Test case validates title of the chart.)
			softAssert.assertTrue(dashboardPage.verifyTitleofChartOfDashboardPage("subscriptionExpirationTitleFlexi", dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.subscription_expiration")), "Subscription Expiration Chart title did not match.");

			// Test Case 2 (This test case validate whether chart is loaded or not.)
			Assert.assertFalse(dashboardPage.verifyChartisLoadedDynamic(LanguageCode, "subscriptionExpirationChartFlexi", "subscriptionExpirationFatalError", 5), "Subscription Expiration Chart did not load successfully.");

			// Test Case 3 (This test case validate whether chart has any data or
			// not.)
			Assert.assertFalse(dashboardPage.verifyChartsHasNoDataOnDashBoardPage(LanguageCode, "subscriptionExpirationNoDataFlexi", "dashboard.charts.title.subscription_expiration"), "Subscription Expiration Chart does not have any data.");
			Assert.assertFalse(dashboardPage.verifyChartsHasNoDataDevicesEnrolledOnDashBoardPage(LanguageCode, "subscriptionExpirationNoDataFlexi", "dashboard.charts.title.subscription_expiration"), "Subscription Expiration Chart does not have any data.");

			// Test Case 4 (This test case validates the text of tool tip present on all sub
			// parts of chart with text on Report page.)
			softAssert.assertTrue(dashboardPage.verifyTooltipTextOnReportWithFrame("tooltipHoverSubscriptionExpirationFlexi", "subscriptionExpirationToolTipHoverTextFlexi", "subscriptionExpirationTextLocator"), "Tooltip text of Subscription Expiration chart did match with reports");

			// Test Case 5 (This test case validates the count of tool tip present on all
			// sub parts of charts with total rows on Report page.)
			softAssert.assertTrue(dashboardPage.verifyTooltipCountOnReportwithFrameSubscriptionExpirationFlexi("tooltipHoverSubscriptionExpirationFlexi", "tooltipCountSubscriptionExpirationFlexi", "columnlistSubscriptionExpirationForCountFlexi", "frameLocator"), "Tooltip count of SOFTWARE INVENTORY chart did match with reports");

			// Test case 6 (This test case validates the header of reports page.)
			softAssert.assertTrue(dashboardPage.headerTextVerificationOnReportPageFrame(LanguageCode, "tooltipHoverSubscriptionExpirationFlexi", "headerListLocatorOnReportPageSubscriptionFlexi", DASHBOARD_CHART_SUBSCRIPTION_EXPIRATION_REPORT_PAGE, "frameLocator"), "Report header for Subscription Expiration chart did not match on report page.");

			// Test Case 7 (This test case validates the labels of the chart. )
			softAssert.assertTrue(dashboardPage.verifyChartLabelsDashboardPage("subscriptionExpirationXaxisLabelsFlexi", dashboardPage.getTextLanguage(LanguageCode, "daas_ui", SUBSCRIPTION_EXPIRATION_LABELS_LIST)), "Labels of Subscription Expiration Chart chart did not match");

			// Test case 8 (This test case validates no data message on chart.)
			dashboardPage.scrollUpCharts();
			if (!toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, username))){
				dashboardPage.clickOnElementsOfDashboardPage("companyDropdownFlexi");
				softAssert.assertTrue(dashboardPage.verifyCompanyChangeOfDashboardPage(LanguageCode, "companySearchBoxFlexi", DashboardVariables.DASHBOARD_CHART_COMPANY_NAME_MSP_US, "companyEmptyText", "companyList", "dropdownBoxCompanyFlexi"), "Company Change is not working correctly");
				softAssert.assertTrue(dashboardPage.verifyChartsHasNoDataOnDashBoardPageFlexi(LanguageCode, "subscriptionExpirationNoDataFlexi", "dashboard.charts.title.subscription_expiration"), "No data message for Subscription Expiration Chart is incorrect.");
				dashboardPage.clickOnElementsOfDashboardPage("clearCompanyFlexi");
				dashboardPage.waitForPageLoaded();
			}else{
				dashboardPage.clickOnElementsOfDashboardPage("companyDropdownGlobalFilter");
				softAssert.assertTrue(dashboardPage.verifyCompanyChangeOfDashboardPageGlobalFilter(LanguageCode, "companySearchGlobalFilter", DashboardVariables.DASHBOARD_CHART_COMPANY_NAME_MSP_US, "companyEmptyTextGlobalFilter", "companyListGlobalFilter", "outputTextGlobalFilter","globalFilterSave"), "Company Change is not working correctly");
				softAssert.assertTrue(dashboardPage.verifyChartsHasNoDataOnDashBoardPageFlexi(LanguageCode, "subscriptionExpirationNoDataFlexi", "dashboard.charts.title.subscription_expiration"), "No data message for Subscription Expiration Chart is incorrect.");				
				dashboardPage.clickOnElementsOfDashboardPage("companyDropdownGlobalFilter");
				dashboardPage.clickOnElementsOfDashboardPage("clearGlobalFilter");
				dashboardPage.clickOnElementsOfDashboardPage("globalFilterSave");
			}
			softAssert.assertAll();
			LOGGER.info("Validation of Subscription Expiration Chart completed successfully");
		}
	}

	@Test(priority = 18, groups = { "REGRESSION", "REGRESSION_CI", "ALL_CI", "ALL", "REGRESSIONDASHBOARD1" }, description = "[211237]")
	public final void verifynoDataMessageSubscriptionExpirationForMSP() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login("MSP_NEW_UI_EMAIL_NO_DATA_US", "MSP_NEW_UI_PASSWORD_NO_DATA_US");
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		if (!toggleVerification(DashboardVariables.FLEXIDASHBOARD, getCredentials(environment, "MSP_NEW_UI_EMAIL_NO_DATA_US"))) {

			// Test Case 1 (This Test case validates title of the chart.)
			softAssert.assertTrue(dashboardPage.verifyTitleofChartOfDashboardPage("subscriptionExpirationTitle", dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.subscription_expiration")), "Subscription Expiration Chart title did not match.");

			// Test Case 2 (This test case validate whether chart is loaded or not.)
			Assert.assertFalse(dashboardPage.verifyChartisLoadedDynamic(LanguageCode, "subscriptionExpirationChart", "subscriptionExpirationFatalError", 5), "Subscription Expiration Chart did not load successfully.");

			// Test Case 3 (This test case validate no data message)
			Assert.assertTrue(dashboardPage.verifyChartsHasNoDataOnDashBoardPage(LanguageCode, "subscriptionExpirationNoData", "dashboard.charts.title.subscription_expiration"), "Subscription Expiration Chart does not have any data.");
			Assert.assertTrue(dashboardPage.verifyChartsHasNoDataDevicesEnrolledOnDashBoardPage(LanguageCode, "subscriptionExpirationNoData", "dashboard.charts.title.subscription_expiration"), "Subscription Expiration Chart does not have any data.");

			softAssert.assertAll();
			LOGGER.info("Validation of Subscription Expiration Chart completed successfully");
		}else {
			if (!toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, "MSP_NEW_UI_EMAIL_NO_DATA_US"))){
				Assert.assertTrue(setToDefaultDashboard(LanguageCode),"Error occured in setting dashboard to default mode.");
			}else{
				Assert.assertTrue(setToDefaultDashboardGlobalFilter(LanguageCode),"Error occured in setting dashboard to default mode.");
			}

			// Test Case 1 (This Test case validates title of the chart.)
			softAssert.assertTrue(dashboardPage.verifyTitleofChartOfDashboardPage("subscriptionExpirationTitleFlexi", dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.subscription_expiration")), "Subscription Expiration Chart title did not match.");

			// Test Case 2 (This test case validate whether chart is loaded or not.)
			Assert.assertFalse(dashboardPage.verifyChartisLoadedDynamic(LanguageCode, "subscriptionExpirationChartFlexi", "subscriptionExpirationFatalErrorFlexi", 5), "Subscription Expiration Chart did not load successfully.");

			// Test Case 3 (This test case validate no data message)
			Assert.assertTrue(dashboardPage.verifyChartsHasNoDataOnDashBoardPageFlexi(LanguageCode, "subscriptionExpirationNoDataFlexi", "dashboard.charts.title.subscription_expiration"), "Subscription Expiration Chart does not have any data.");

			softAssert.assertAll();
			LOGGER.info("Validation of Subscription Expiration Chart completed successfully");
		}
	}

	

	@Test(priority = 19, groups = { "REGRESSION", "REGRESSION_CI", "ALL_CI", "ALL", "REGRESSIONDASHBOARD2" })
	public final void verifynoDataMessageSubscriptionExpirationForReseller() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login("RESELLER_PARTNERS_EMAIL", "RESELLER_PARTNERS_PASSWORD");
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		if (!toggleVerification(DashboardVariables.FLEXIDASHBOARD, getCredentials(environment, "RESELLER_PARTNERS_EMAIL"))) {

			// Test Case 1 (This Test case validates title of the chart.)
			softAssert.assertTrue(dashboardPage.verifyTitleofChartOfDashboardPage("subscriptionExpirationTitle", dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.subscription_expiration")), "Subscription Expiration Chart title did not match.");

			// Test Case 2 (This test case validate whether chart is loaded or not.)
			Assert.assertFalse(dashboardPage.verifyChartisLoadedDynamic(LanguageCode, "subscriptionExpirationChart", "subscriptionExpirationFatalError", 5), "Subscription Expiration Chart did not load successfully.");

			// Test Case 3 (This test case validate no data message)
			Assert.assertTrue(dashboardPage.verifyChartsHasNoDataOnDashBoardPage(LanguageCode, "subscriptionExpirationNoData", "dashboard.charts.title.subscription_expiration"), "Subscription Expiration Chart does not have any data.");

			softAssert.assertAll();
			LOGGER.info("Validation of Subscription Expiration Chart completed successfully");
		} else {
			if (!toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, "RESELLER_PARTNERS_EMAIL"))){
				Assert.assertTrue(setToDefaultDashboard(LanguageCode),"Error occured in setting dashboard to default mode.");
			}else{
				Assert.assertTrue(setToDefaultDashboardGlobalFilter(LanguageCode),"Error occured in setting dashboard to default mode.");
			}

			dashboardPage.scrollToDashboardPage("subscriptionExpirationTitleFlexi");
			LOGGER.info("Scrolled down to Subscription Expiration chart");
			// Test Case 1 (This Test case validates title of the chart.)
			softAssert.assertTrue(dashboardPage.verifyTitleofChartOfDashboardPage("subscriptionExpirationTitleFlexi", dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.subscription_expiration")), "Subscription Expiration Chart title did not match.");

			// Test Case 2 (This test case validate whether chart is loaded or not.)
			Assert.assertFalse(dashboardPage.verifyChartisLoadedDynamic(LanguageCode, "subscriptionExpirationChartFlexi", "subscriptionExpirationFatalErrorFlexi", 5), "Subscription Expiration Chart did not load successfully.");

			// Test Case 3 (This test case validate no data message)
			Assert.assertTrue(dashboardPage.verifyChartsHasNoDataOnDashBoardPageFlexi(LanguageCode, "subscriptionExpirationNoDataFlexi", "dashboard.charts.title.subscription_expiration"), "Subscription Expiration Chart does not have any data.");

			softAssert.assertAll();
			LOGGER.info("Validation of Subscription Expiration Chart completed successfully");
		}
	}

	@Test(priority = 20, groups = { "REGRESSION", "REGRESSION_CI", "ALL_CI", "ALL", "REGRESSIONDASHBOARD1" })
	public final void verifynoDataMessageDevicesByTypeForReportAdmin() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login("REPORT_ADMIN_NEW_UI_EMAIL_NO_DATA_US", "REPORT_ADMIN_NEW_UI_PASSWORD_NO_DATA_US");
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		if (!toggleVerification(DashboardVariables.FLEXIDASHBOARD, getCredentials(environment, "REPORT_ADMIN_NEW_UI_EMAIL_NO_DATA_US"))) {

			// Test Case 1 (This test case validate no data message for DevicesByType chart)
			Assert.assertFalse(dashboardPage.verifyChartisLoadedDynamic(LanguageCode, "deviceByTypeChart", "fatalErrorDeviceByType", 5), "DEVICES BY TYPE Chart did not load successfully.");
			Assert.assertTrue(dashboardPage.verifyChartsHasNoDataOnDashBoardPage(LanguageCode, "noDataDeviceByType", "dashboard.charts.title.devices_by_type"), "DEVICES BY TYPE Chart: Message for no data is incorrect./Chart contains data now, please check.");

			softAssert.assertAll();
			LOGGER.info("Validation of Device By Type Chart  completed successfully");
		}else {
			if (!toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, "RA_HELP_AND_SUPPORT_EMAIL"))){
				Assert.assertTrue(setToDefaultDashboard(LanguageCode),"Error occured in setting dashboard to default mode.");
			}else{
				Assert.assertTrue(setToDefaultDashboardGlobalFilter(LanguageCode),"Error occured in setting dashboard to default mode.");
			}

			// Test Case 1 (This test case validate no data message for DevicesByType chart)
			Assert.assertFalse(dashboardPage.verifyChartisLoadedDynamic(LanguageCode, "deviceByTypeChartFlexi", "fatalErrorDeviceByTypeFlexi", 5), "DEVICES BY TYPE Chart did not load successfully.");
			Assert.assertTrue(dashboardPage.verifyChartsHasNoDataOnDashBoardPageFlexi(LanguageCode, "noDataDeviceByTypeFlexi", "dashboard.charts.title.devices_by_type"), "DEVICES BY TYPE Chart: Message for no data is incorrect./Chart contains data now, please check.");

			softAssert.assertAll();
			LOGGER.info("Validation of Device By Type Chart  completed successfully");
		}
	}

	@Test(priority = 21, groups = { "REGRESSION", "REGRESSION_CI", "ALL_CI", "ALL", "REGRESSIONDASHBOARD1" })
	public final void verifynoDataMessageDeviceByOSForReportAdmin() throws Exception {
		login("REPORT_ADMIN_STAGING_EMAIL", " REPORT_ADMIN_STAGING_PASSWORD");
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		if (!toggleVerification(DashboardVariables.FLEXIDASHBOARD, getCredentials(environment, "REPORT_ADMIN_STAGING_EMAIL"))) {

			// Test Case 1 (This test case validate no data message for DeviceByOS chart)
			Assert.assertFalse(dashboardPage.verifyChartisLoadedDynamic(LanguageCode, "deviceByOsChart", "fatalErrorDeviceByOs", 5), "DEVICES BY OS Chart did not load successfully.");
			Assert.assertTrue(dashboardPage.verifyChartsHasNoDataOnDashBoardPage(LanguageCode, "noDataDeviceByOs", "dashboard.charts.title.devices_by_os"), "DEVICES BY OS: Message for no data is incorrect./Chart contains data now, please check.");
			LOGGER.info("Validation of Device By Os Chart completed successfully");
		}else {
			if (!toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, "RA_HELP_AND_SUPPORT_EMAIL"))){
				Assert.assertTrue(setToDefaultDashboard(LanguageCode),"Error occured in setting dashboard to default mode.");
			}else{
				Assert.assertTrue(setToDefaultDashboardGlobalFilter(LanguageCode),"Error occured in setting dashboard to default mode.");
			}

			// Test Case 1 (This test case validate no data message for DeviceByOS chart)
			Assert.assertFalse(dashboardPage.verifyChartisLoadedDynamic(LanguageCode, "deviceByOsChartFlexi", "fatalErrorDeviceByOsFlexi", 5), "DEVICES BY OS Chart did not load successfully.");
			Assert.assertTrue(dashboardPage.verifyChartsHasNoDataOnDashBoardPageFlexi(LanguageCode, "noDataDeviceByOsFlexi", "dashboard.charts.title.devices_by_os"), "DEVICES BY OS: Message for no data is incorrect./Chart contains data now, please check.");
			LOGGER.info("Validation of Device By Os Chart completed successfully");
		}
	}

	
	@Test(priority = 22, groups = { "REGRESSION", "REGRESSION_CI", "ALL_CI", "ALL",
			"REGRESSIONDASHBOARD2" }, description = "[199638],[178113],[178100],[178102],[178104],[178108],[178106],[178109],[178112],[198910]")
	public final void verifyChartDevicesByTypeForReportAdmin() throws Exception {
		login("REPORT_ADMIN_STAGING_EMAIL", "REPORT_ADMIN_STAGING_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		String[] DASHBOARD_CHART_DEVICE_TYPE_OS_HW_REPORT_PAGE =null;
		ArrayList<String> DEVICES_BY_TYPE_CHART_LABELS_LIST = new ArrayList<>(
				Arrays.asList("global.device_type.notebook", "global.device_type.tablet",
						"global.device_type.smartphone", "global.device_type.desktop", "global.device_type.other",
						"global.device_type.all_in_one", "global.device_type.phablet"));
	
		 DASHBOARD_CHART_DEVICE_TYPE_OS_HW_REPORT_PAGE = new String[] { "gridHeaders.Hardware_Inventory.Details.allData.lastSignedinUser", "Global.gridHeaders.enrolledmonth", "Global.gridHeaders.deviceName", "Global.gridHeaders.serialNumber", "Global.gridHeaders.deviceType", "Global.gridHeaders.deviceManufacturer", "Global.gridHeaders.deviceModel", "Global.gridHeaders.operatingSystem", "gridHeaders.hardware_Inventory.Details.operating_system_release",
				"gridHeaders.hardware_Inventory.Details.operating_system_Build_number", "gridHeaders.Hardware_Inventory.Details.win10_patch_status", "Global.gridHeaders.lastSeen", "Global.gridHeaders.memory", "gridHeaders.Hardware_Inventory.Details.graphics", "Global.gridHeaders.processor", "Global.gridHeaders.manufacture_date", "gridHeaders.hardware_Inventory.Details.enrolledDate", "gridHeaders.Incident_Widget.Details.warStatus", "gridHeaders.hardware_Inventory.Details.warrantyEndDate",
				"Global.gridHeaders.country", "Global.gridHeaders.department", "Global.gridHeaders.costCenter", "Global.gridHeaders.alias", "Global.gridHeaders.asset_tag", "graphHeaders.Hardware_Inventory.Details.lifecycle_status", "graphHeaders.Hardware_Inventory.Details.device_role", "graphHeaders.Hardware_Inventory.Details.component", "graphHeaders.Hardware_Inventory.Details.component_serial_number",
				"graphHeaders.Hardware_Inventory.Details.component_repl_timeframe", "graphHeaders.Hardware_Inventory.Details.component_Model", "graphHeaders.Hardware_Inventory.Details.component_size_gb","Global.gridHeaders.location_1","Global.gridHeaders.location_2","Global.gridHeaders.location_3","Global.gridHeaders.location_4"};
		
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();

		if (!toggleVerification(DashboardVariables.FLEXIDASHBOARD,
				getCredentials(environment, "REPORT_ADMIN_STAGING_EMAI"))) {
			dashboardPage.waitForPageLoaded();
			// Test Case 1 (This Test case validates title of the chart.)
			softAssert.assertTrue(dashboardPage.verifyTitleofChartOfDashboardPage("deviceByTypeTitle",dashboardPage.getTextLanguage(LanguageCode, "daas_ui",
									"dashboard.charts.title.devices_by_type")),
					"DEVICES BY TYPE Chart title did not match.");

			// Test Case 2 (This test case validate whether chart is loaded or not.)
			Assert.assertFalse(dashboardPage.verifyChartisLoadedDynamic(LanguageCode, "deviceByTypeChart", "fatalErrorDeviceByType", 5), "DEVICES BY TYPE Chart did not load successfully.");

			// Test Case 3 (This test case validate whether chart has any data or
			// not.)
			Assert.assertFalse(dashboardPage.verifyChartsHasNoDataOnDashBoardPage(LanguageCode, "noDataDeviceByType", "dashboard.charts.title.devices_by_type"), "DEVICES BY TYPE Chart does not have any data.");

			// Test Case 4 (This test case validates the text of tool tip present on all sub
			// parts of chart with text on Report page.)
			softAssert.assertTrue(dashboardPage.verifyTooltipTextOnReportWithFrame("tooltipHoverDeviceByType", "tooltipTextDeviceByType", "deviceByTypeTextLocator"), "Tooltip text of DEVICES BY TYPE chart did match with reports");

			// Test case 6 (This test case validates the header of reports page.)
			dashboardPage.scrollToDashboardPage("deviceByTypeTitle");
			softAssert.assertTrue(dashboardPage.headerTextVerificationOnReportPageFrame(LanguageCode, "deviceByTypeXaxisLabels", "headerListLocatorOnReportPage", DASHBOARD_CHART_DEVICE_TYPE_OS_HW_REPORT_PAGE, "frameLocator"), "Report header for DEVICES BY TYPE chart did not match on report page.");

			// Test case 7 (This test caseverifyDevicesWithOutdatedDrivers validates the labels of the chart. )
			softAssert.assertTrue(dashboardPage.verifyChartLabelsDashboardPage("deviceByTypeXaxisLabels", dashboardPage.getTextLanguage(LanguageCode, "daas_ui", DEVICES_BY_TYPE_CHART_LABELS_LIST)), "Labels of DEVICES BY TYPE chart did not match");

			// Test case 8 (This test case validates redirection to device details page from Report page.)
			Assert.assertFalse(dashboardPage.verifyChartisLoadedDynamic(LanguageCode, "deviceByTypeChart", "fatalErrorDeviceByType", 5), "DEVICES BY TYPE Chart did not load successfully.");
			softAssert.assertTrue(dashboardPage.verifyDeviceNameRedirectionMSPWithFrame(LanguageCode, "deviceByTypeXaxisLabels", "deviceDetailsTitle", "deviceNameColumnDeviceByTypeReportAdmin", "errorLocatorForNoDevice", "frameLocator"), "Device Name Redirection is not working properly for DEVICE BY TYPE chart");

			softAssert.assertAll();
			LOGGER.info("Validation of DEVICES BY TYPE Chart completed successfully");
		} else {

			if (!toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, "REPORT_ADMIN_STAGING_EMAIL"))){
				Assert.assertTrue(setToDefaultDashboard(LanguageCode),"Error occured in setting dashboard to default mode.");
			}else{
				Assert.assertTrue(setToDefaultDashboardGlobalFilter(LanguageCode),"Error occured in setting dashboard to default mode.");
			}

			dashboardPage.waitForPageLoaded();
			// Test Case 1 (This Test case validates title of the chart.)
			softAssert.assertTrue(dashboardPage.verifyTitleofChartOfDashboardPage("deviceByTypeTitleFlexi", dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.devices_by_type")), "DEVICES BY TYPE Chart title did not match.");

			// Test Case 2 (This test case validate whether chart is loaded or not.)
			Assert.assertFalse(dashboardPage.verifyChartisLoadedDynamic(LanguageCode, "deviceByTypeChartFlexi", "fatalErrorDeviceByType", 5), "DEVICES BY TYPE Chart did not load successfully.");

			// Test Case 3 (This test case validate whether chart has any data or
			// not.)
			Assert.assertFalse(dashboardPage.verifyChartsHasNoDataOnDashBoardPageFlexi(LanguageCode, "noDataDeviceByTypeFlexi", "dashboard.charts.title.devices_by_type"), "DEVICES BY TYPE Chart does not have any data.");

			// Test Case 4 (This test case validates the text of tool tip present on all sub
			// parts of chart with text on Report page.)
			softAssert.assertTrue(dashboardPage.verifyTooltipTextOnReportWithFrame("tooltipHoverDeviceByTypeFlexi", "tooltipTextDeviceByTypeFlexi", "deviceByTypeTextLocator"), "Tooltip text of DEVICES BY TYPE chart did match with reports");

			// Test case 6 (This test case validates the header of reports page.)
			dashboardPage.scrollToDashboardPage("deviceByTypeTitleFlexi");
			softAssert.assertTrue(dashboardPage.headerTextVerificationOnReportPageFrame(LanguageCode, "tooltipHoverDeviceByTypeFlexi", "headerListLocatorOnReportPage", DASHBOARD_CHART_DEVICE_TYPE_OS_HW_REPORT_PAGE, "frameLocator"), "Report header for DEVICES BY TYPE chart did not match on report page.");

			// Test case 7 (This test case validates the labels of the chart. )
			softAssert.assertTrue(dashboardPage.verifyChartLabelsDashboardPage("deviceByTypeXaxisLabelsFlexi", dashboardPage.getTextLanguage(LanguageCode, "daas_ui", DEVICES_BY_TYPE_CHART_LABELS_LIST)), "Labels of DEVICES BY TYPE chart did not match");

			// Test case 8 (This test case validates redirection to device details page from Report page.)
			Assert.assertFalse(dashboardPage.verifyChartisLoadedDynamic(LanguageCode, "deviceByTypeChartFlexi", "fatalErrorDeviceByType", 5), "DEVICES BY TYPE Chart did not load successfully.");
			softAssert.assertTrue(dashboardPage.verifyDeviceNameRedirectionMSPWithFrame(LanguageCode, "tooltipHoverDeviceByTypeFlexi", "deviceDetailsTitle", "deviceNameColumnDeviceByTypeReportAdmin", "errorLocatorForNoDevice", "reportLayout"), "Device Name Redirection is not working properly for DEVICE BY TYPE chart");

			softAssert.assertAll();
			LOGGER.info("Validation of DEVICES BY TYPE Chart completed successfully");
		}
	}

	@Test(priority = 23, groups = { "REGRESSION", "REGRESSION_CI", "ALL_CI", "ALL", "REGRESSIONDASHBOARD2" }, description = "[199642],[177999],[178093],[178005],[178098],[178092],[191191],[177998],[178099],[177996]",enabled=false)
	public final void verifyChartDevicesByOSForReportAdmin() throws Exception {
		login("REPORT_ADMIN_NEW_UI_EMAIL_US", "REPORT_ADMIN_UI_PASSWORD_US");
		SoftAssert softAssert = new SoftAssert();
		final String[] DASHBOARD_CHART_DEVICE_TYPE_OS_HW_REPORT_PAGE = { "gridHeaders.Hardware_Inventory.Details.allData.lastSignedinUser", "Global.gridHeaders.enrolledmonth", "Global.gridHeaders.deviceName", "Global.gridHeaders.serialNumber", "Global.gridHeaders.deviceType", "Global.gridHeaders.deviceManufacturer", "Global.gridHeaders.deviceModel", "Global.gridHeaders.operatingSystem", "gridHeaders.hardware_Inventory.Details.operating_system_release",
				"gridHeaders.hardware_Inventory.Details.operating_system_Build_number", "gridHeaders.Hardware_Inventory.Details.win10_patch_status", "Global.gridHeaders.lastSeen", "Global.gridHeaders.memory", "gridHeaders.Hardware_Inventory.Details.graphics", "Global.gridHeaders.processor", "Global.gridHeaders.manufacture_date", "gridHeaders.hardware_Inventory.Details.enrolledDate", "gridHeaders.Incident_Widget.Details.warStatus", "gridHeaders.hardware_Inventory.Details.warrantyEndDate",
				"Global.gridHeaders.country", "Global.gridHeaders.department", "Global.gridHeaders.costCenter", "Global.gridHeaders.alias", "Global.gridHeaders.asset_tag", "graphHeaders.Hardware_Inventory.Details.lifecycle_status", "graphHeaders.Hardware_Inventory.Details.device_role", "graphHeaders.Hardware_Inventory.Details.component", "graphHeaders.Hardware_Inventory.Details.component_serial_number",
				"graphHeaders.Hardware_Inventory.Details.component_repl_timeframe", "graphHeaders.Hardware_Inventory.Details.component_Model", "graphHeaders.Hardware_Inventory.Details.component_size_gb","Global.gridHeaders.location_1","Global.gridHeaders.location_2","Global.gridHeaders.location_3","Global.gridHeaders.location_4" };

		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();

		if (!toggleVerification(DashboardVariables.FLEXIDASHBOARD, getCredentials(environment, "REPORT_ADMIN_NEW_UI_EMAIL_US"))) {
			HashMap<String, String> DeviceByOsInfo = dashboardPage.getDeviceByOsDetails();
			// Test Case 1 (This Test case validates title of the chart.)
			softAssert.assertTrue(dashboardPage.verifyTitleofChartOfDashboardPage("devicesByOsTitleFlexi", dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.devices_by_os")), "DEVICES BY OS Chart title did not match.");

			// Test Case 2 (This test case validate whether chart is loaded or not.)
			Assert.assertFalse(dashboardPage.verifyChartisLoadedDynamic(LanguageCode, "deviceByOsChart", "fatalErrorDeviceByOs", 5), "DEVICES BY OS Chart did not load successfully.");

			// Test Case 3 (This test case validate whether chart has any data or
			// not.)
			Assert.assertFalse(dashboardPage.verifyChartsHasNoDataOnDashBoardPageFlexi(LanguageCode, "noDataDeviceByOs", "dashboard.charts.title.devices_by_os"), "DEVICES BY OS Chart does not have any data.");

			// Test case 4 (This test case validates the text,count and version of tool tip
			// present on all sub parts of chart with text,count and version on Report
			// page.)
			softAssert.assertTrue(dashboardPage.verifyTooltipTextCountAndVersionOnReportWithFrame(LanguageCode, "devicesByOsTitleFlexi", "deviceByOSLegends", "tooltipTextDeviceByOsFlexi", "tooltipTextDeviceByOsMajorVersionFlexi", "tooltipTextDeviceByOsMajorVersionNameFlexi", "tooltipTextDeviceByOsCountFlexi", "reportCountDeviceByOs", "levelsBackFunctionalityForDevicesByOsFlexi", "deviceByOsTextLocator", "deviceByOsReleaseTextLocator", "moreDetailsLink", "frameLocator", "deviceByOSLegendsHidden",
					"deviceByOSLegendsVisible", "labelsLocatorDeviceByOsFlexi"), "Tooltip text of DEVICES BY OS chart did match with reports");

			// Test case 5 (This test case validates the header column data at report page.)
			dashboardPage.scrollToDashboardPage("devicesByOsTitleFlexi");
			// softAssert.assertTrue(dashboardPage.verifyHeaderColumnOnReportPageWithFrame(LanguageCode, "headerListLocatorOnReportPage",
			// DASHBOARD_CHART_DEVICE_TYPE_OS_HW_REPORT_PAGE, DeviceByOsInfo), "Report text of DEVICES BY OS chart did match with reports");

			// Test case 6 (This test case validates redirection to device details page from
			// Report page.)
			softAssert.assertTrue(dashboardPage.verifyDeviceByOsRedirectionWithFrameFlexi(LanguageCode, "headerListLocatorOnReportPage", DASHBOARD_CHART_DEVICE_TYPE_OS_HW_REPORT_PAGE, DeviceByOsInfo, "deviceDetailsTitle", "deviceNameColumnDeviceByType", "errorLocatorForNoDevice"), "Redirection of DEVICES BY OS chart navigated to Device details page");
			
			softAssert.assertAll();
			LOGGER.info("Validation of DEVICES BY OS Chart completed successfully");
		}else{
			if (!toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, "REPORT_ADMIN_NEW_UI_EMAIL_US"))){
				Assert.assertTrue(setToDefaultDashboard(LanguageCode),"Error occured in setting dashboard to default mode.");
			}else{
				Assert.assertTrue(setToDefaultDashboardGlobalFilter(LanguageCode),"Error occured in setting dashboard to default mode.");
			}

			// Assert.assertTrue(setToDefaultDashboard(LanguageCode),"Error occured in setting dashboard to default mode.");
			HashMap<String, String> DeviceByOsInfo = dashboardPage.getDeviceByOsDetails();
			// Test Case 1 (This Test case validates title of the chart.)
			// softAssert.assertTrue(dashboardPage.verifyTitleofChartOfDashboardPage("devicesByOsTitleFlexi", dashboardPage.getTextLanguage(LanguageCode, "daas_ui",
			// "dashboard.charts.title.devices_by_os")), "DEVICES BY OS Chart title did not match.");

			// Test Case 2 (This test case validate whether chart is loaded or not.)
			Assert.assertFalse(dashboardPage.verifyChartisLoadedDynamic(LanguageCode, "deviceByOsChartFlexi", "fatalErrorDeviceByOs", 5), "DEVICES BY OS Chart did not load successfully.");

			// Test Case 3 (This test case validate whether chart has any data or
			// not.)
			Assert.assertFalse(dashboardPage.verifyChartsHasNoDataOnDashBoardPage(LanguageCode, "noDataDeviceByOsFlexi", "dashboard.charts.title.devices_by_os"), "DEVICES BY OS Chart does not have any data.");

			// Test case 4 (This test case validates the text,count and version of tool tip
			// present on all sub parts of chart with text,count and version on Report
			// page.)
			softAssert.assertTrue(dashboardPage.verifyTooltipTextCountAndVersionOnReportWithFrame(LanguageCode, "devicesByOsTitleFlexi", "deviceByOSLegends", "tooltipTextDeviceByOsFlexi", "tooltipTextDeviceByOsMajorVersionFlexi", "tooltipTextDeviceByOsMajorVersionNameFlexi", "tooltipTextDeviceByOsCountFlexi", "reportCountDeviceByOs", "levelsBackFunctionalityForDevicesByOsFlexi", "deviceByOsTextLocator", "deviceByOsReleaseTextLocator", "moreDetailsLink", "frameLocator", "deviceByOSLegendsHidden",
					"deviceByOSLegendsVisible", "labelsLocatorDeviceByOsFlexi"), "Tooltip text of DEVICES BY OS chart did match with reports");

			// Test case 5 (This test case validates the header column data at report page.)
			dashboardPage.scrollToDashboardPage("devicesByOsTitleFlexi");
			softAssert.assertTrue(dashboardPage.verifyDeviceByOSReportHeaderWithFrameFlexi(LanguageCode, "headerListLocatorOnReportPage", DASHBOARD_CHART_DEVICE_TYPE_OS_HW_REPORT_PAGE, DeviceByOsInfo, "deviceDetailsTitle", "deviceNameColumnDeviceByType", "errorLocatorForNoDevice"), "Report text of DEVICES BY OS chart did match with reports");

			// Test case 6 (This test case validates redirection to device details page from
			// Report page.)
			softAssert.assertTrue(dashboardPage.verifyDeviceByOsRedirectionWithFrameFlexi(LanguageCode, "headerListLocatorOnReportPage", DASHBOARD_CHART_DEVICE_TYPE_OS_HW_REPORT_PAGE, DeviceByOsInfo, "deviceDetailsTitle", "deviceNameColumnDeviceByType", "errorLocatorForNoDevice"), "Redirection of DEVICES BY OS chart navigated to Device details page");

			softAssert.assertAll();
			LOGGER.info("Validation of DEVICES BY OS Chart completed successfully");
		}
	}

	
	@Test(priority = 24, groups = { "REGRESSION", "REGRESSION_CI", "ALL_CI", "ALL" })
	public final void verifyEstimatorTabForReseller() throws Exception {
		login("RESELLER_NEW_UI_EMAIL_US", "RESELLER_NEW_UI_PASSWORD_US");
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();

		LaunchDarkly ldinstance = new LaunchDarkly();
		if (ldinstance.enabled("estimator-tab", null, null, true)) {
			// Test case 1: Verify the Estimator tab presence on the Navigation bar
			Assert.assertTrue(dashboardPage.waitForElementsOfDashboardPage("estimatorID"), "Estimator tab is not present");
			LOGGER.info("Verified Estimator tab is present");

			// Test case 2: Verify after clicking on the Estimator tab new tab gets open
			dashboardPage.clickOnElementsOfDashboardPage("estimatorID");
			dashboardPage.switchToDifferentTabOfDashboardPage();
			LOGGER.info("Clicked on Estimator tab");

			Assert.assertTrue(dashboardPage.matchTextOfDashboardPage("estimatorTabTitle", "HP DaaS Estimator"), "Estimator tab redirection failed");
			LOGGER.info("Verified estimator tab redirection");
		} else
			LOGGER.info("Toggle for estimation tab is off.");
		LOGGER.info("VerifyEstimatorTabForReseller test case executed successfully");
	}

	

	/**
	 * This test case verify Incident dashboard present for the sales admin and Third level of the incident dashboard navigates sales admin on Incident list view
	 */
	@Test(priority = 25, groups = { "REGRESSION", "REGRESSION_CI" }, description = "223204,225516", enabled = false)
	public final void verifyIncidentDashboardForSalesAdmin() {
		try {
			login("MSP_ADMIN_US_EMAIl_REPORTS", "MSP_ADMIN_US_PASSWORD_REPORTS");
			impersonateCompanyByEmail(getCredentials(environment, "COMPANY_EMAIL_REPORTS"));
			IncidentDetailsPage incidentDetailsPage = new IncidentDetailsPage(PreDefinedActions.getDriver()).getInstance();

			HashMap<String, String> deviceDetails = incidentDetailsPage.enrollDevice(LanguageCode);

			Assert.assertTrue(deviceDetails != null, "Fake device couldn't be enrolled");
			LOGGER.info("Fake Device Has been enrolled successfully,Device serial number: " + deviceDetails.get("deviceSerialNumber"));
			String incidentId = incidentDetailsPage.submitIncident(LanguageCode, deviceDetails);
			Assert.assertTrue(incidentId != null, "Error occured while submitting an incident");
			LOGGER.info("Incident has been submitted successfully,Incident ID: " + incidentId);
			logout();

			login("RESELLER_NEW_UI_EMAIL_US", "RESELLER_NEW_UI_PASSWORD_US");
			SoftAssert softAssert = new SoftAssert();
			DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();

			// test case 1
			softAssert.assertTrue(dashboardPage.verifyIncidentDashboardForUser(LanguageCode), "Error Occure while verifing Incident dashboard charts for Sales Admin");
			LOGGER.info("Verified Incident dashboard for sales admin user");

			// test case 2
			softAssert.assertTrue(dashboardPage.isIncidentChartRedirectOnIncidentListView(LanguageCode, "openIncidentChart"), "Error occured while validating incident view redirection from Open incident chart for Sales Admin");
			LOGGER.info("Verified Sales admin able to navigate on incident page from Open Incident chart");

			gotoDashboardTab();
			softAssert.assertTrue(dashboardPage.isIncidentChartRedirectOnIncidentListView(LanguageCode, "allIncidentChart"), "Error occured while validating incident view redirection from All incident chart for Sales Admin");
			LOGGER.info("Verified Sales admin able to navigate on incident page from All Incident chart");

			gotoDashboardTab();
			softAssert.assertTrue(dashboardPage.isIncidentChartRedirectOnIncidentListView(LanguageCode, "topTenIncidentChart"), "Error occured while validating incident view redirection from Top 10 incident chart for Sales Admin");
			LOGGER.info("Verified Sales admin able to navigate on incident page from Top 10 Incident");
			softAssert.assertAll();
		} catch (Exception e) {
			LOGGER.error("Exception occured in verifyIncidentDashboardForSalesAdmin" + e.getMessage());
			Assert.fail("Exception occured in verifyIncidentDashboardForSalesAdmin");
		}
	}

	/**
	 * This test case verify Incident dashboard present for the sales Specialist and third level of the incident dashboard does not navigates sales specialist on Incident list view
	 */
	@Test(priority = 26, groups = { "REGRESSION", "REGRESSION_CI", "PRODUCTION_SANITY" }, description = "231040,225513", enabled = false)
	public final void verifyIncidentDashboardForSalesSpecialist() {
		try {
			// Submitting an incidents
			login("MSP_ADMIN_US_EMAIl_REPORTS", "MSP_ADMIN_US_PASSWORD_REPORTS");
			impersonateCompanyByEmail(getCredentials(environment, "COMPANY_EMAIL_REPORTS"));
			IncidentDetailsPage incidentDetailsPage = new IncidentDetailsPage(PreDefinedActions.getDriver()).getInstance();

			HashMap<String, String> deviceDetails = incidentDetailsPage.enrollDevice(LanguageCode);

			Assert.assertTrue(deviceDetails != null, "Fake device couldn't be enrolled");
			LOGGER.info("Fake Device Has been enrolled successfully,Device serial number: " + deviceDetails.get("deviceSerialNumber"));
			String incidentId = incidentDetailsPage.submitIncident(LanguageCode, deviceDetails);
			Assert.assertTrue(incidentId != null, "Error occured while submitting an incident");
			LOGGER.info("Incident has been submitted successfully,Incident ID: " + incidentId);
			logout();
			// Validating test case
			login("RESELLER_NEW_UI_EMAIL_US_SS", "RESELLER_NEW_UI_PASSWORD_US_SS");
			SoftAssert softAssert = new SoftAssert();
			DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();

			// test case 1
			softAssert.assertTrue(dashboardPage.verifyIncidentDashboardForUser(LanguageCode), "Error Occure while verifing Incident dashboard charts for Sales Specialist");
			LOGGER.info("Verified Incident dashboard for Sales Specialist user");

			// test case 2
			softAssert.assertTrue(!dashboardPage.isIncidentChartRedirectOnIncidentListView(LanguageCode, "openIncidentChart"), "Error occured while validating incident view redirection from Open incident chart for Sales Specialist");
			LOGGER.info("Verified Sales Specialist not able to navigate on incident page from Open Incident chart");

			gotoDashboardTab();
			softAssert.assertTrue(!dashboardPage.isIncidentChartRedirectOnIncidentListView(LanguageCode, "allIncidentChart"), "Error occured while validating incident view redirection from All incident chart for Sales Specialist");
			LOGGER.info("Verified Sales Specialist not able to navigate on incident page from All Incident chart");

			gotoDashboardTab();
			softAssert.assertTrue(!dashboardPage.isIncidentChartRedirectOnIncidentListView(LanguageCode, "topTenIncidentChart"), "Error occured while validating incident view redirection from Top 10 incident chart for Sales Specialist");
			LOGGER.info("Verified Sales Specialist not able to navigate on incident page from Top 10 Incident");
			softAssert.assertAll();
		} catch (Exception e) {
			LOGGER.error("Exception occured in verifyIncidentDashboardForSalesSpecialist" + e.getMessage());
			Assert.fail("Exception occured in verifyIncidentDashboardForSalesSpecialist");
		}
	}

	/**
	 * This test case verify Incident dashboard present for the Report Admin
	 */
	@Test(priority = 27, groups = { "REGRESSION", "REGRESSION_CI" }, description = "231040,225513", enabled = false)
	public final void verifyIncidentDashboardForReportAdmin() {
		try {
			login("MSP_ADMIN_US_EMAIl_REPORTS", "MSP_ADMIN_US_PASSWORD_REPORTS");
			impersonateCompanyByEmail(getCredentials(environment, "COMPANY_EMAIL_REPORTS"));
			IncidentDetailsPage incidentDetailsPage = new IncidentDetailsPage(PreDefinedActions.getDriver()).getInstance();

			HashMap<String, String> deviceDetails = incidentDetailsPage.enrollDevice(LanguageCode);

			Assert.assertTrue(deviceDetails != null, "Fake device couldn't be enrolled");
			LOGGER.info("Fake Device Has been enrolled successfully,Device serial number: " + deviceDetails.get("deviceSerialNumber"));
			String incidentId = incidentDetailsPage.submitIncident(LanguageCode, deviceDetails);
			Assert.assertTrue(incidentId != null, "Error occured while submitting an incident");
			LOGGER.info("Incident has been submitted successfully,Incident ID: " + incidentId);
			logout();

			login("COMPANY_EMAIL_REPORTS", "COMPANY_REPORT_ADMIN_PASSWORD");
			SoftAssert softAssert = new SoftAssert();
			DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();

			// test case 1
			softAssert.assertTrue(dashboardPage.verifyIncidentDashboardForUser(LanguageCode), "Error Occure while verifing Incident dashboard charts for Sales Specialist");
			LOGGER.info("Verified Incident dashboard for Report Admin user");
			// test case 2
			softAssert.assertTrue(dashboardPage.isIncidentChartRedirectOnIncidentListView(LanguageCode, "openIncidentChart"), "Error occured while validating incident view redirection from Open incident chart for Report Admin");
			LOGGER.info("Verified Report Admin able to navigate on incident page from Open Incident chart");
			gotoDashboardTab();
			softAssert.assertTrue(dashboardPage.isIncidentChartRedirectOnIncidentListView(LanguageCode, "allIncidentChart"), "Error occured while validating incident view redirection from All incident chart for Report Admin");
			LOGGER.info("Verified Report Admin able to navigate on incident page from All Incident chart");
			gotoDashboardTab();
			softAssert.assertTrue(dashboardPage.isIncidentChartRedirectOnIncidentListView(LanguageCode, "topTenIncidentChartRa"), "Error occured while validating incident view redirection from Top 10 incident chart for Report Admin");
			LOGGER.info("Verified Report Admin able to navigate on incident page from Top 10 Incident");
			softAssert.assertAll();
		} catch (Exception ex) {
			LOGGER.error("Exception occured in verifyIncidentDashboardForReportAdmin" + ex.getMessage());
			Assert.fail("Exception occured in verifyIncidentDashboardForReportAdmin");
		}
	}

	/**
	 * This test case verifies the HP Sure Click Dashboard Widget for Report Admin
	 */
	@Test(priority = 28, groups = { "REGRESSION", "REGRESSION_CI", "ALL_CI", "ALL", "REGRESSIONDASHBOARD2" }, description = "[258032]")
	public final void verifyChartHPSCAWidgetForReportAdmin() throws Exception {
		login("ITA_COMPANY_DETAILS_EMAIL", "ITA_COMPANY_DETAILS_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		final String[] DASHBOARD_HPSCA_WIDGET_DEVICE_DETAILS_REPORT_PAGE = { "gridHeaders.Hardware_Inventory.Details.allData.lastSignedinUser", "Global.gridHeaders.lastSeen", "Global.gridHeaders.deviceName", "Global.gridHeaders.serialNumber", "gridHeaders.Hardware_Inventory.Details.allData.device_state", "gridHeaders.scasecurity.device_protection_details.reason", "gridHeaders.Windows_Migration.details.last_disable_date", "gridHeaders.Windows_Migration.details.total_threat_occured",
		"gridHeaders.Windows_Migration.details.management_actions","Global.gridHeaders.location1","Global.gridHeaders.location2","Global.gridHeaders.location3","Global.gridHeaders.location4" };

		final String[] DASHBOARD_HPSCA_WIDGET_THREAT_DETAILS_REPORT_PAGE = { "gridHeaders.Hardware_Inventory.Details.allData.lastSignedinUser", "Global.gridHeaders.date_occured", "Global.gridHeaders.deviceName", "Global.gridHeaders.serialNumber", "graphHeaders.Hp_sure_sense.label.fileHash", "Global.gridHeaders.details", "gridHeaders.Hp_Sure_Click_Advanced_Threat_Summary.Application", "gridHeaders.Windows_Migration.details.affected_resource" };

		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		waitForPageLoaded();
		if (!toggleVerification(DashboardVariables.FLEXIDASHBOARD, getCredentials(environment, "ITA_COMPANY_DETAILS_EMAIL"))) {

			// Reset current configuration of dashboard chart
			dashboardPage.verifyResetToDefaultButton();
			dashboardPage.waitForPageLoaded();
			sleeper(3000);

			try {

				// Test Case 1 (This Test case validates title of the widget.)
				softAssert.assertTrue(dashboardPage.verifyTitleofChartOfDashboardPage("hpscaWidgetTitle", dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.bromium_sure_click")), "HP Sure Click Widget title did not match.");
				LOGGER.info("HP Sure Click widget title text matched");

				if (dashboardPage.verifyElementsOfDashboardPage("allprotectedDevices")) {

					final String allDevicesProtected = dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "dashboard.hpsureclick.nodata.title");

					// Test Case 2 (This test case validates the tile text when All devices protected)
					softAssert.assertTrue(dashboardPage.matchTextOfDashboardPage("allprotectedDevices", allDevicesProtected), "All devices Protected tile text doesn't match");
					LOGGER.info("All Devices Protected - tile text matched");

				} else {

					final String unprotectedDevice = dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "dashboard.hpsureclick.content.unprotected.title");

					// Test Case 3 (This test case validates Unprotected Devices tile)
					softAssert.assertTrue(dashboardPage.matchTextOfDashboardPage("unprotectedTile", unprotectedDevice), "Unprotected Devices tile text doesn't match");
					LOGGER.info(" Unprotected Devices tile text matched");

					// Test case 4 (This test case validates the column headers of device protection details report grid page.)
					LOGGER.info("Clicking on View Details button to validate the grid for Device Protection Details");
					softAssert.assertTrue(dashboardPage.headerTextVerificationOnReportPageFrame(LanguageCode, "viewDetailsBtnDevices", "headerListLocatorOnReportPage", DASHBOARD_HPSCA_WIDGET_DEVICE_DETAILS_REPORT_PAGE, "frameLocator"), "Report header for DEVICE PROTECTION DETAILS did not match on report page.");
				}

				if (dashboardPage.verifyElementsOfDashboardPage("allpreventedThreats")) {

					final String noThreatsOccured = dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "dashboard.hpsureclick.threats.nodata.title");

					// Test Case 5 (This test case validates the tile text when No threats occurred)
					softAssert.assertTrue(dashboardPage.matchTextOfDashboardPage("allpreventedThreats", noThreatsOccured), "No Threats Occurred tile text doesn't match");
					LOGGER.info("No Threats Occurred - tile text matched");

				} else {

					final String preventedThreats = dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "dashboard.hpsureclick.content.prevented.title");

					// Test Case 6 (This test case validates Prevented Threats tile description)
					softAssert.assertTrue(dashboardPage.matchTextOfDashboardPage("threatsPreventTile", preventedThreats), "Prevented Threats tile text doesn't match");
					LOGGER.info("Prevented Threats tile text matched");

					// Test case 7 (This test case validates the column headers of threat protection details report grid page.)
					LOGGER.info("Clicking on View Details button to validate the grid for Threat Protection Details");
					softAssert.assertTrue(dashboardPage.headerTextVerificationOnReportPageFrame(LanguageCode, "viewDetailsBtnThreats", "headerListLocatorOnReportPage", DASHBOARD_HPSCA_WIDGET_DEVICE_DETAILS_REPORT_PAGE, "frameLocator"), "Report header for THREAT PROTECTION DETAILS did not match on report page.");

				}

				softAssert.assertAll();
				LOGGER.info("Validation of HP Sure Click Pro Widget completed successfully");

			} catch (Exception ex) {
				LOGGER.error("Exception occured in verifyChartHPSCAWidgetForReportAdmin" + ex.getMessage());
			}
		} else {

			// Reset current configuration of dashboard chart
			if (!toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, "COMPANY_EMAIL_REPORTS"))){
				Assert.assertTrue(setToDefaultDashboard(LanguageCode),"Error occured in setting dashboard to default mode.");
			}else{
				Assert.assertTrue(setToDefaultDashboardGlobalFilter(LanguageCode),"Error occured in setting dashboard to default mode.");
			}
			dashboardPage.waitForPageLoaded();
			sleeper(3000);

			try {

				// Test Case 1 (This Test case validates title of the widget.)
				softAssert.assertTrue(dashboardPage.verifyTitleofChartOfDashboardPage("hpscaWidgetTitleFlexi", dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.bromium_sure_click")), "HP Sure Click Widget title did not match.");
				LOGGER.info("HP Sure Click widget title text matched");

				if (dashboardPage.verifyElementsOfDashboardPage("allprotectedDevicesFlexi")) {

					final String allDevicesProtected = dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "dashboard.hpsureclick.nodata.title");

					// Test Case 2 (This test case validates the tile text when All devices protected)
					softAssert.assertTrue(dashboardPage.matchTextOfDashboardPage("allprotectedDevicesFlexi", allDevicesProtected), "All devices Protected tile text doesn't match");
					LOGGER.info("All Devices Protected - tile text matched");

				} else {

					final String unprotectedDevice = dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "dashboard.hpsureclick.content.unprotected.title");

					// Test Case 3 (This test case validates Unprotected Devices tile)
					softAssert.assertTrue(dashboardPage.matchTextOfDashboardPage("unprotectedTileFlexi", unprotectedDevice), "Unprotected Devices tile text doesn't match");
					LOGGER.info(" Unprotected Devices tile text matched");

					// Test case 4 (This test case validates the column headers of device protection details report grid page.)
					LOGGER.info("Clicking on View Details button to validate the grid for Device Protection Details");
					softAssert.assertTrue(dashboardPage.headerTextVerificationOnReportPageFrame(LanguageCode, "viewDetailsBtnDevicesFlexi", "headerListLocatorOnReportPageHPSCAWidget", DASHBOARD_HPSCA_WIDGET_DEVICE_DETAILS_REPORT_PAGE, "frameLocator"), "Report header for DEVICE PROTECTION DETAILS did not match on report page.");
				}

				if (dashboardPage.verifyElementsOfDashboardPage("allpreventedThreatsFlexi")) {

					final String noThreatsOccured = dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "dashboard.hpsureclick.threats.nodata.title");

					// Test Case 5 (This test case validates the tile text when No threats occurred)
					softAssert.assertTrue(dashboardPage.matchTextOfDashboardPage("allpreventedThreatsFlexi", noThreatsOccured), "No Threats Occurred tile text doesn't match");
					LOGGER.info("No Threats Occurred - tile text matched");

				} else {

					final String preventedThreats = dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "dashboard.hpsureclick.threats.nodata.title");

					// Test Case 6 (This test case validates Prevented Threats tile description)
					softAssert.assertTrue(dashboardPage.matchTextOfDashboardPage("threatsPreventTileFlexi", preventedThreats), "Prevented Threats tile text doesn't match");
					LOGGER.info("Prevented Threats tile text matched");

					// Test case 7 (This test case validates the column headers of threat protection details report grid page.)
					LOGGER.info("Clicking on View Details button to validate the grid for Threat Protection Details");
					softAssert.assertTrue(dashboardPage.headerTextVerificationOnReportPageFrame(LanguageCode, "viewDetailsBtnThreatsFlexi", "headerListLocatorOnReportPageHPSCAWidget", DASHBOARD_HPSCA_WIDGET_DEVICE_DETAILS_REPORT_PAGE, "frameLocator"), "Report header for THREAT PROTECTION DETAILS did not match on report page.");

				}

				softAssert.assertAll();
				LOGGER.info("Validation of HP Sure Click Pro Widget completed successfully");

			} catch (Exception ex) {
				LOGGER.error("Exception occured in verifyChartHPSCAWidgetForReportAdmin" + ex.getMessage());
			}
		}
	}

	/*
	 * Windows 10 Patch Status chart on Dashboard page This test case validates chart title Chart loading or not Data present in the chart or not By clicking on the chart correct
	 * filter applied in redirected header section By clicking on the chart correct results displaying in the grid Correct column names in the grid Refer DashboardPage.properties
	 * for parameters - all locators listed under '#Windows 10 Patch Status' added in the files at location- properties/DashboardPage.properties Refer daas_ui for parameters - like
	 * dashboard.charts.os_version_support.title language support(keys) added in the files at location- locales/daas_ui
	 */
	@Test(priority = 29, groups = { "REGRESSION", "REGRESSION_CI", "ALL_CI", "ALL", "REGRESSIONDASHBOARD2", "STABILIZATION_STAGING","PENTEST" }, description = "[221150]")
	public final void verifyWindows_10_Patch_Status() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		final String[] DASHBOARD_CHART_HW_REPORT_DETAILS_PAGE = { "Global.gridHeaders.companyName", "gridHeaders.Hardware_Inventory.Details.allData.lastSignedinUser", "Global.gridHeaders.deviceName", "Global.gridHeaders.serialNumber", "Global.gridHeaders.deviceType", "Global.gridHeaders.deviceManufacturer", "Global.gridHeaders.deviceModel", "Global.gridHeaders.operatingSystem", "gridHeaders.hardware_Inventory.Details.operating_system_release",
				"gridHeaders.hardware_Inventory.Details.operating_system_Build_number", "gridHeaders.Hardware_Inventory.Details.win10_patch_status", "Global.gridHeaders.productSKU", "Global.gridHeaders.lastSeen", "Global.gridHeaders.memory", "gridHeaders.Hardware_Inventory.Details.graphics", "Global.gridHeaders.processor", "Global.gridHeaders.manufacture_date", "gridHeaders.hardware_Inventory.Details.enrolledDate", "gridHeaders.Incident_Widget.Details.warStatus",
				"gridHeaders.hardware_Inventory.Details.warrantyEndDate", "Global.gridHeaders.country", "Global.gridHeaders.department", "Global.gridHeaders.costCenter", "Global.gridHeaders.alias", "Global.gridHeaders.asset_tag", "graphHeaders.Hardware_Inventory.Details.lifecycle_status", "graphHeaders.Hardware_Inventory.Details.device_role", "graphHeaders.Hardware_Inventory.Details.component", "graphHeaders.Hardware_Inventory.Details.component_serial_number",
				"graphHeaders.Hardware_Inventory.Details.component_repl_timeframe", "graphHeaders.Hardware_Inventory.Details.component_Model", "graphHeaders.Hardware_Inventory.Details.component_size_gb", "Global.gridHeaders.location1","Global.gridHeaders.location2","Global.gridHeaders.location3","Global.gridHeaders.location4" };

		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		if (!toggleVerification(DashboardVariables.FLEXIDASHBOARD, getCredentials(environment, "MSP_ADMIN_US_EMAIl"))) {

			// Test Case 1 (This Test case validates title of the chart.)
			softAssert.assertTrue(dashboardPage.verifyTitleofChartOfDashboardPage("windows10PatchStatusTitle", dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.os_version_support.title")), "Windows 10 PATCH STATUS title did not match.");

			// Test Case 2 (This test case validate whether chart is loaded or not.)
			Assert.assertFalse(dashboardPage.verifyChartisLoadedDynamic(LanguageCode, "windows10PatchStatusChart", "fatalErrorDeviceByOs", 5), "Windows 10 PATCH STATUS title chart did not load successfully.");

			// Test Case 3 (This test case validate whether chart has any data or not and verifying chart messages when no data.)
			Assert.assertFalse(dashboardPage.verifyChartsHasNoDataOnDashBoardPage(LanguageCode, "noDatawindows10PatchStatus", "dashboard.charts.os_version_support.title"), "'Windows 10 PATCH STATUS' chart, devices are not enrolled.");

			// Test case 4 (This test case validates the correct filter criteria applied on redirected report details page.)
			softAssert.assertTrue(dashboardPage.verifyTooltipTextVersionStatusWPS("patchStatus", "patchStatusTooltipText", "seconddrilldown", "secondlevelTooltip", "osReleaseFilterCriteria", "backButtonWPS"), "Tooltip text of Windows10PatchStatus chart did not match with reports filter criteria");

			// Test case 5 (This test case validates the grid column header names are correct of redirected reports detail page.)
			softAssert.assertTrue(dashboardPage.verifyGridColumnsNamesWPS(LanguageCode, "labelsWPS", "columnHeaderWPS", DASHBOARD_CHART_HW_REPORT_DETAILS_PAGE, "frameLocator"), "Grid column names did not match on redirection page of 'Windows 10 Patch Status' chart");
			softAssert.assertAll();
			LOGGER.info("Validation of Windows_10_Patch_Status Chart completed successfully");

		}else {
			if (!toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, "MSP_ADMIN_US_EMAIl"))){
				Assert.assertTrue(setToDefaultDashboard(LanguageCode),"Error occured in setting dashboard to default mode.");
			}else{
				Assert.assertTrue(setToDefaultDashboardGlobalFilter(LanguageCode),"Error occured in setting dashboard to default mode.");
			}

			// Test Case 1 (This Test case validates title of the chart.)
			softAssert.assertTrue(dashboardPage.verifyTitleofChartOfDashboardPage("windows10PatchStatusTitleFlexi", dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.os_version_support.title")), "Windows 10 PATCH STATUS title did not match.");

			if(!(dashboardPage.verifyElementsOfDashboardPage("noDatawindows10PatchStatusChartFlexi"))) {
			// Test Case 2 (This test case validate whether chart is loaded or not.)
			Assert.assertFalse(dashboardPage.verifyChartisLoadedDynamic(LanguageCode, "windows10PatchStatusChartFlexi", "fatalErrorDeviceByOs", 5), "Windows 10 PATCH STATUS title chart did not load successfully.");

			// Test Case 3 (This test case validate whether chart has any data or not and verifying chart messages when no data.)
			Assert.assertFalse(dashboardPage.verifyChartsHasNoDataOnDashBoardPageFlexi(LanguageCode, "noDatawindows10PatchStatusFlexi", "dashboard.charts.os_version_support.title"), "'Windows 10 PATCH STATUS' chart, devices are not enrolled.");

			// Test case 4 (This test case validates the correct filter criteria applied on redirected report details page.)
			softAssert.assertTrue(dashboardPage.verifyTooltipTextVersionStatusWPS("patchStatusFlexi", "patchStatusTooltipTextFlexi", "seconddrilldownFlexi", "secondlevelTooltipFlexi", "osReleaseFilterCriteriaFlexi", "backButtonWPSFlexi"), "Tooltip text of Windows10PatchStatus chart did not match with reports filter criteria");

			// Test case 6 (This test case validates the grid column header names are correct of redirected reports detail page.)
			softAssert.assertTrue(dashboardPage.verifyGridColumnsNamesWPS(LanguageCode, "patchStatusFlexi", "columnHeaderWPS", DASHBOARD_CHART_HW_REPORT_DETAILS_PAGE, "frameLocator"), "Grid column names did not match on redirection page of 'Windows 10 Patch Status' chart");
			softAssert.assertAll();
			LOGGER.info("Validation of Windows_10_Patch_Status Chart completed successfully");
			} else {
				LOGGER.info("No data on Windows_10_Patch_Status Chart");
			}
		}
	}

	/**
	 * @throws Exception
	 */
	@Test(priority = 30, groups = { "REGRESSION", "REGRESSION_CI", "ALL_CI", "ALL", "REGRESSIONDASHBOARD2", "PENTEST" }, description = "277168, 277502, 277179,303801,303798")
	public final void verifyHideAndShowChartAtDashboardPage() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		waitForPageLoaded();
		// Verify Flexi Dashboard toggle
		if (!toggleVerification(DashboardVariables.FLEXIDASHBOARD, getCredentials(environment, "MSP_ADMIN_US_EMAIl"))) {
			Assert.assertTrue(toggleVerification(DashboardVariables.REDESIGN, getCredentials(environment, "MSP_ADMIN_US_EMAIl")), "[TC:277168, 277502, 277179]" + DashboardVariables.REDESIGN + "LD-Toggle is disabled for Hide and Show functionaliy");
			LOGGER.info("Verified LD-Toggle status");

			// Reset current configuration of dashboard chart
			dashboardPage.verifyResetToDefaultButton();
			dashboardPage.waitForPageLoaded();
			// String userId = dashboardPage.getTokenValue("user");
			// String mspAuthToken = getTokenFromLocalStorage("dui_token");

			// String tokenUrl = getEnvironment(System.getProperty("environment")) + LastLoggedInUserPreferences + userId + keyPreferences;
			// Boolean hiddenConfirmationModelPopupValue =dashboardPage.getHiddenConfirmationModalPopup(mspAuthToken,tokenUrl);
			dashboardPage.clickByJavaScriptOnDashboardPage("dashboardSettingConfig");
			sleeper(3000);
			/*
			 * if(!hiddenConfirmationModelPopupValue) { dashboardPage.clickByJavaScriptOnDashboardPage("hideLinkOnDashboardPage"); sleeper(2000);
			 * softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("confirmationModelHeader").equals(getTextLanguage(LanguageCode,"daas_ui",
			 * "dashboard.widgets.popup.hide.title")),"[TC:290147] Confirmation Model header text does not match on Dashboard page.");
			 * softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("confirmationModelDescriptionMessage").equals(getTextLanguage(LanguageCode,"daas_ui",
			 * "dashboard.widgets.popup.hide.desc")),"[TC:290147] Confirmation Model popup description message does not match on Dashboard page.");
			 * softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("confirmationModelCheckboxMessage").equals(getTextLanguage(LanguageCode,"daas_ui",
			 * "dashboard.widgets.popup.hide.msg")),"[TC:290147] Confirmation Model popup message not match on Dashboard page.");
			 * dashboardPage.clickByJavaScriptOnDashboardPage("confirmationModelCheckboxMessage"); dashboardPage.clickByJavaScriptOnDashboardPage("confirmationModelOkButton"); }
			 */

			// Test Case 1 (This test case validates display HIDE link with click-able in dashboard view on all chart in edit mode.)
			softAssert.assertTrue(dashboardPage.verifyHideAndShowLink(getTextLanguage(LanguageCode, "daas_ui", "global.hide").toUpperCase(), "hideLinkOnDashboardPage"), "[TC:277168] HIDE link is not present on chart at dashboard page in edit mode.");
			LOGGER.info("All the chart hidden successfully.");
			sleeper(3000);
			dashboardPage.clickByJavaScriptOnDashboardPage("saveButtonOnEditMode");
			dashboardPage.waitUntilElementsOfDashBoardPage("customizeTextOnDashboard");
			softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("customizeTextOnDashboard").equals(getTextLanguage(LanguageCode, "daas_ui", "dashboard.actions.customize.description")), "[TC:281957] Customize text does not match on Dashboard page.");
			dashboardPage.clickByJavaScriptOnDashboardPage("customizeButtonOnDashboard");
			waitForPageLoaded();
			// Test Case 2 (This test case validates display SHOW link with click-able in Hidden Widgets for all chart in edit mode.)
			softAssert.assertTrue(dashboardPage.verifyHideAndShowLink(getTextLanguage(LanguageCode, "daas_ui", "global.show").toUpperCase(), "showLinkOnDashboardPage"), "[TC:277502] SHOW link is not present on chart at dashboard page in edit mode.");
			LOGGER.info("All the chart showing in normal dashboard view by clicking on show link successfully.");
			sleeper(2000);
			LOGGER.info("HIDE and SHOW link working successfully.");
			dashboardPage.clickByJavaScriptOnDashboardPage("saveButtonOnEditMode");
			waitForPageLoaded();
			softAssert.assertAll();
			LOGGER.info("Validation of verifyHideAndShowChartAtDashboardPage Chart completed successfully");
		} else {
			LOGGER.info("This is flexible dashboard !! Hide and show chart functionality will not work ");
		}
	}

	@Test(priority = 31, groups = { "REGRESSION", "REGRESSION_CI", "ALL_CI", "ALL", "REGRESSIONDASHBOARD2", "PENTEST" }, description = " 277506, 277179")
	public final void verifyDragAndDropChartAtDashboardPage() throws Exception {

		SoftAssert softAssert = new SoftAssert();
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		waitForPageLoaded();
		if (!toggleVerification(DashboardVariables.FLEXIDASHBOARD, getCredentials(environment, "MSP_ADMIN_US_EMAIl"))) {
			String userId = dashboardPage.getTokenValue("user");
			String mspAuthToken = getTokenFromLocalStorage("dui_token_e");

			// Verify toggle status
			Assert.assertTrue(toggleVerification(DashboardVariables.REDESIGN, getCredentials(environment, "MSP_ADMIN_US_EMAIl")), "[TC:277506, 277179]" + DashboardVariables.REDESIGN + "LD-Toggle is disabled for Drag and Drop functionaliy");
			LOGGER.info("Verified LD-Toggle status");

			// Reset current configuration of dashboard chart
			dashboardPage.verifyResetToDefaultButton();
			dashboardPage.waitForPageLoaded();
			dashboardPage.clickByJavaScriptOnDashboardPage("dashboardSettingConfig");
			sleeper(3000);

			// Test Case 1 (This Test case validates the drag and drop operation in top of the page.)
			softAssert.assertTrue(dashboardPage.verifyDragAndDrop("dhDiskReplacement", "businessReviewCard"), "[TC:277506, 277179] Drag and Drop operation not happening between[dhDiskReplacement ,businessReviewCard] chart !!!");
			sleeper(2000);
			// Test Case 2 (This Test case validates the drag and drop operation in middle of the page.)
			softAssert.assertTrue(dashboardPage.verifyDragAndDrop("devicesByOs", "WarrantyExpiration"), "[TC:277506, 277179] Drag and Drop operation not happening between[devicesByOs, WarrantyExpiration] chart !!!");
			sleeper(2000);

			// Test Case 3 (This Test case validates the drag operation in middle and drop at Hidden area of the page.)
			softAssert.assertTrue(dashboardPage.verifyDragAndDrop("diskReplacementSummary", "containerHiddenArea"), "[TC:277506, 277179]diskReplacementSummary does not Drraging in Hidden widgets between area !!!");
			sleeper(2000);

			dashboardPage.clickByJavaScriptOnDashboardPage("saveButtonOnEditMode");
			String tokenUrl = getEnvironment(System.getProperty("environment")) + LastLoggedInUserPreferences + userId + keyPreferences;
			JSONArray jsonChatArray = dashboardPage.getActualAarrayAfterDragAndDropOperation(mspAuthToken, tokenUrl);

			// Test Case 4 (This Test case validates the sequence after drag and drop operation)
			softAssert.assertFalse(dashboardPage.verifyChartOrderAfterDragAndDropOfDashBoardPage(jsonChatArray), "[TC:277506, 277179] Sequence of chart does not change!!!");
			softAssert.assertAll();
			LOGGER.info("Validation of verifyDragAndDropChartAtDashboardPage Chart completed successfully");
		}else{
			if (!toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, "MSP_ADMIN_US_EMAIl"))){
				Assert.assertTrue(setToDefaultDashboard(LanguageCode),"Error occured in setting dashboard to default mode.");
			}else{
				Assert.assertTrue(setToDefaultDashboardGlobalFilter(LanguageCode),"Error occured in setting dashboard to default mode.");
			}

			waitForPageLoaded();
			// Test Case 1 (This Test case validates the drag and drop operation in top of the page.)
			softAssert.assertTrue(dashboardPage.verifyDragAndDrop("networkSpeed", "sevendayLowSpeed"), "[TC:277506, 277179] Drag and Drop operation not happening between[dhDiskReplacement ,businessReviewCard] chart !!!");
			sleeper(2000);
			// Test Case 2 (This Test case validates the drag and drop operation in middle of the page.)
			softAssert.assertTrue(dashboardPage.verifyDragAndDrop("devicesByOsFlexi", "warrantyExpirationFlexi"), "[TC:277506, 277179] Drag and Drop operation not happening between[devicesByOs, WarrantyExpiration] chart !!!");
			sleeper(2000);
			softAssert.assertAll();
			LOGGER.info("Validation of verifyDragAndDropChartAtDashboardPage Chart completed successfully");
		}
	}

	@Test(priority = 32, groups = { "REGRESSION", "REGRESSION_CI", "ALL_CI", "ALL", "REGRESSIONDASHBOARD2" }, description = "277168, 277169, 277506")
	public final void verifyResetToDefaultButtonDashboardPage() throws Exception {

		SoftAssert softAssert = new SoftAssert();
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		waitForPageLoaded();
		String mspAuthToken = getTokenFromLocalStorage("dui_token_e");
		String userId = dashboardPage.getTokenValue("user");
		// Verify Flexi dashboard toggle
		if (!toggleVerification(DashboardVariables.FLEXIDASHBOARD, getCredentials(environment, "MSP_ADMIN_US_EMAIl"))) {
			// Verify toggle status
			Assert.assertTrue(toggleVerification(DashboardVariables.REDESIGN, getCredentials(environment, "MSP_ADMIN_US_EMAIl")), "[TC:277169]" + DashboardVariables.REDESIGN + "LD-Toggle is disabled for Reset to Default button functionaliy");
			LOGGER.info("Verified LD-Toggle status");
			dashboardPage.clickByJavaScriptOnDashboardPage("dashboardSettingConfig");
			sleeper(2000);

			if (dashboardPage.verifyElementIsEnableOfDashboardPage("resetToDefaultButton")) {
				dashboardPage.clickByJavaScriptOnDashboardPage("resetToDefaultButton");
				if(uiVersion.equalsIgnoreCase("VENEER2"))
				{
					// Confirm header verification
					dashboardPage.waitUntilElementsOfDashBoardPage("confirmHeader");
					softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("confirmHeader").equals(getTextLanguage(LanguageCode, "lhserver", "global.confirm")), "[TC:277169] Confirm text does not match on reset to default popup.");
					// Reset to default message verification.
					dashboardPage.waitUntilElementsOfDashBoardPage("confirmationMeassage");
					softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("confirmationMeassage").equals(getTextLanguage(LanguageCode, "daas_ui", "dashboard.config.reset.modal.description")), "[TC:277169] Model text does not match on reset to default popup.");
				}
				else if(uiVersion.equalsIgnoreCase("VENEER3")) {
					dashboardPage.waitUntilElementsOfDashBoardPage("confirmHeader");
					softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("confirmHeader").equals(getTextLanguage(LanguageCode, "daas_ui", "flexible_dashboard.modals.title.reset_dashboard ")), "[TC:277169] Confirm text does not match on reset to default popup.");
					// Reset to default message verification.
					dashboardPage.waitUntilElementsOfDashBoardPage("confirmationMeassage");
					softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("confirmationMeassage").equals(getTextLanguage(LanguageCode, "daas_ui", "flexible_dashboard.modals.warning.reset_dashboard")), "[TC:277169] Model text does not match on reset to default popup.");
				}
				
				// Cancel button verification.
				dashboardPage.waitUntilElementsOfDashBoardPage("confirmationCancelButton");
				dashboardPage.clickByJavaScriptOnDashboardPage("confirmationCancelButton");

				dashboardPage.clickByJavaScriptOnDashboardPage("resetToDefaultButton");
				dashboardPage.waitUntilElementsOfDashBoardPage("confirmHeader");
				// Reset button verification.
				dashboardPage.waitUntilElementsOfDashBoardPage("confirmationYesButton");
				dashboardPage.clickByJavaScriptOnDashboardPage("confirmationYesButton");
				sleeper(3000);
				dashboardPage.clickByJavaScriptOnDashboardPage("saveButtonOnEditMode");
				sleeper(3000);
				dashboardPage.clickByJavaScriptOnDashboardPage("dashboardSettingConfig");
			}

			// Test Case 1 (This Test case validates the drag operation in middle and drop at Hidden area of the page.)
			softAssert.assertTrue(dashboardPage.verifyDragAndDrop("diskReplacementSummary", "containerHiddenArea"), "[TC:277168,277506] diskReplacementSummary does not Drraging in Hidden widgets between area !!!");			
			sleeper(2000);
			dashboardPage.clickByJavaScriptOnDashboardPage("saveButtonOnEditMode");
			String tokenUrl = getEnvironment(System.getProperty("environment")) + LastLoggedInUserPreferences + userId + keyPreferences;
			JSONArray jsonChatArray = dashboardPage.getActualAarrayAfterDragAndDropOperationWithHiddenArea(mspAuthToken, tokenUrl);

			// Test Case 2 (This Test case validates the hidden chart name after drag and drop operation)
			softAssert.assertFalse(dashboardPage.verifyChartInHiddenAreaAfterDragAndDropOfDashBoardPage(jsonChatArray), "[TC:277168] Hidden chart does not match in hidden area !!!");
			softAssert.assertAll();
			LOGGER.info("Validation of verifyResetToDefaultButtonDashboardPage functionality completed successfully");
		} else {
			LOGGER.info("This is flexible dashboard !! Reset to default button will not work ");
		}
	}

	/**
	 * This test case verifies the HP Sure Click Dashboard Widget functionality for MSP
	 */
	@Test(priority = 33, groups = { "REGRESSION", "REGRESSION_CI", "ALL_CI", "ALL", "REGRESSIONDASHBOARD2" }, description = "[282507]", enabled = false)
	public final void verifyChartHPSCANewWidgetForMSP() throws Exception {

		SoftAssert softAssert = new SoftAssert();
		final String[] DASHBOARD_HPSCA_WIDGET_DEVICE_DETAILS_REPORT_PAGE = { "Global.gridHeaders.companyName", "Global.gridHeaders.lastSeen", "Global.gridHeaders.deviceName", "Global.gridHeaders.serialNumber", "gridHeaders.Hardware_Inventory.Details.allData.device_state", "gridHeaders.scasecurity.device_protection_details.reason", "gridHeaders.Windows_Migration.details.last_disable_date", "gridHeaders.Windows_Migration.details.total_threat_occured",
		"gridHeaders.Windows_Migration.details.management_actions" };

		final String[] DASHBOARD_HPSCA_WIDGET_THREAT_DETAILS_REPORT_PAGE = { "Global.gridHeaders.companyName", "Global.gridHeaders.date_occured", "Global.gridHeaders.deviceName", "Global.gridHeaders.serialNumber", "gridHeaders.Windows_Migration.details.threat_type", "gridHeaders.Windows_Migration.details.threat_source", "gridHeaders.Windows_Migration.details.affected_resource" };

		login("MSP_ADMIN_US_EMAIl_REPORTS", "MSP_ADMIN_US_PASSWORD"); // MSP_ADMIN_US_EMAIl_03
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		waitForPageLoaded();
		// Verify Flexi dashboard toggle
		if (!toggleVerification(DashboardVariables.FLEXIDASHBOARD, getCredentials(environment, "MSP_ADMIN_US_EMAIl"))) {

			// Reset current configuration of dashboard chart
			dashboardPage.verifyResetToDefaultButton();
			dashboardPage.waitForPageLoaded();
			sleeper(3000);

			try {

				// Test Case 1 (This Test case validates title of the widget.)
				softAssert.assertTrue(dashboardPage.verifyTitleofChartOfDashboardPage("hpscaWidgetTitle", dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.bromium_sure_click")), "HP Sure Click Widget title did not match.");
				LOGGER.info("HP Sure Click widget title text matched");

				if (dashboardPage.verifyElementsOfDashboardPage("allprotectedDevices")) {

					final String allDevicesProtected = dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "dashboard.hpsureclick.nodata.title");

					// Test Case 2 (This test case validates the tile text when All
					// devices protected)
					softAssert.assertTrue(dashboardPage.matchTextOfDashboardPage("allprotectedDevices", allDevicesProtected), "All devices Protected tile text doesn't match");
					LOGGER.info("All Devices Protected - tile text matched");

				} else {

					final String unprotectedDevice = dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "dashboard.hpsureclick.content.unprotected.title");

					// Test Case 3 (This test case validates Unprotected Devices
					// tile)
					softAssert.assertTrue(dashboardPage.matchTextOfDashboardPage("unprotectedTile", unprotectedDevice), "Unprotected Devices tile text doesn't match");
					LOGGER.info(" Unprotected Devices tile text matched");

					// Test case 4 (This test case validates the column headers of
					// device protection details report grid page.)
					LOGGER.info("Clicking on View Details button to validate the grid for Device Protection Details");
					softAssert.assertTrue(dashboardPage.headerTextVerificationOnReportPageFrame(LanguageCode, "viewDetailsBtnDevices", "headerListLocatorOnReportPage", DASHBOARD_HPSCA_WIDGET_DEVICE_DETAILS_REPORT_PAGE, "frameLocator"), "Report header for DEVICE PROTECTION DETAILS did not match on report page.");
				}

				if (dashboardPage.verifyElementsOfDashboardPage("allpreventedThreats")) {

					final String noThreatsOccured = dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "dashboard.hpsureclick.threats.nodata.title");

					// Test Case 5 (This test case validates the tile text when No
					// threats occurred)
					softAssert.assertTrue(dashboardPage.matchTextOfDashboardPage("allpreventedThreats", noThreatsOccured), "No Threats Occurred tile text doesn't match");
					LOGGER.info("No Threats Occurred - tile text matched");

				} else {

					final String preventedThreats = dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "dashboard.hpsureclick.content.prevented.title");

					// Test Case 6 (This test case validates Prevented Threats tile
					// description)
					softAssert.assertTrue(dashboardPage.matchTextOfDashboardPage("threatsPreventTile", preventedThreats), "Prevented Threats tile text doesn't match");
					LOGGER.info("Prevented Threats tile text matched");

					// Test case 7 (This test case validates the column headers of
					// threat protection details report grid page.)
					LOGGER.info("Clicking on View Details button to validate the grid for Threat Protection Details");
					softAssert.assertTrue(dashboardPage.headerTextVerificationOnReportPageFrame(LanguageCode, "viewDetailsBtnThreats", "headerListLocatorOnReportPage", DASHBOARD_HPSCA_WIDGET_THREAT_DETAILS_REPORT_PAGE, "frameLocator"), "Report header for THREAT PROTECTION DETAILS did not match on report page.");
				}

				softAssert.assertAll();
				LOGGER.info("Validation of HP Sure Click Pro Widget completed successfully");

			} catch (Exception ex) {
				LOGGER.error("Exception occured in verifyChartHPSCANewWidgetForMSP" + ex.getMessage());
			}
		} else {
			LOGGER.info("This is not flexible dashboard !!");
		}
	}

	/**
	 * This test case verified Driver status chart
	 * 
	 * @throws Exception
	 */
	@Test(priority = 34, groups = { "REGRESSION", "REGRESSION_CI", "ALL_CI", "ALL", "REGRESSIONDASHBOARD2" }, description = "[300768]")
	public final void verifyDriverStatusChart() throws Exception {
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		ArrayList<String> DRIVER_STATUS_CHART_LABELS_LIST = new ArrayList<>(Arrays.asList("dashboard.charts.os_version_support.label.outdated", "dashboard.charts.os_version_support.label.updateAvailable.notSupported", "dashboard.charts.os_version_support.label.updated", "dashboard.charts.os_version_support.label.driverversionnotsupportedbyhp"));
		final String[] DASHBOARD_CHART_DRIVER_INVENTORY_REPORT_PAGE = { "Global.gridHeaders.companyName", "gridHeaders.Hardware_Inventory.Details.allData.lastSignedinUser", "Global.gridHeaders.serialNumber", "Global.gridHeaders.deviceName", "Global.gridHeaders.deviceType", "device_manufacturer", "Global.gridHeaders.deviceModel", "Global.gridHeaders.manufacturerDate", "gridHeaders.Hardware_BIOS.Details.device_platform_id", "Global.gridHeaders.operatingSystem",
				"graphHeaders.Hardware_Inventory.Details.operating_system_release", "gridHeaders.hardware_Inventory.Details.operating_system_Build_number", "gridHeaders.Hardware_Inventory.Details.allData.osType", "Global.gridHeaders.driverStatus", "Global.gridHeaders.driverCategory", "Global.gridHeaders.driverName", "Global.gridHeaders.driverVersion", "gridHeaders.driverInventory.latestDriverVersion", "gridHeaders.driverInventory.latestDriverReleaseDate",
				"gridHeaders.driverInventory.latestDriverCriticality", "gridHeaders.driverInventory.latestDriverSoftPackNumber", "Global.gridHeaders.pnpDeviceId", "Global.gridHeaders.location1","Global.gridHeaders.location2","Global.gridHeaders.location3","Global.gridHeaders.location4" };
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();

		if (!toggleVerification(DashboardVariables.FLEXIDASHBOARD, getCredentials(environment, "MSP_ADMIN_US_EMAIl"))) {

			// Verify toggle status
			Assert.assertTrue(toggleVerification(DashboardVariables.REDESIGN, getCredentials(environment, "MSP_ADMIN_US_EMAIl")), "[TC:277168, 277502, 277179]" + DashboardVariables.REDESIGN + "LD-Toggle is disabled for Hide and Show functionaliy");
			LOGGER.info("Verified LD-Toggle status");
			// Reset current configuration of dashboard chart
			dashboardPage.clearDashboardCompanyFilter();
			dashboardPage.verifyResetToDefaultButton();
			dashboardPage.waitForPageLoaded();
			// D Test Case 1 (This Test case validates title of the chart.)
			softAssert.assertTrue(dashboardPage.verifyTitleofChartOfDashboardPage("driverStatusTitle", dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.driver_status")), "Driver Status Chart title did not match.");

			// D Test Case 2 (This test case validate whether chart is loaded or not.)
			Assert.assertFalse(dashboardPage.verifyChartisLoadedDynamic(LanguageCode, "driverStatusChart", "fatalErrorDriverStatus", 5), "Driver Status Chart did not load successfully.");

			// D Test Case 3 (This test case validate whether chart has any data or not.)
			Assert.assertFalse(dashboardPage.verifyChartsHasNoDataOnDashBoardPage(LanguageCode, "noDataDriverStatusChart", "dashboard.charts.title.driver_status"), "Driver Status Chart does not have any data.");
			Assert.assertFalse(dashboardPage.verifyChartsHasNoDataDevicesEnrolledOnDashBoardPage(LanguageCode, "noDataDriverStatusChart", "dashboard.charts.title.driver_status"), "Driver Status Chart does not have any data.");

			// D Test Case 4 (This test case validates total-count is present on driver Status chart .)
			softAssert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("driverCount"), "Total count is not Present");

			// Test Case 4 (This test case validates the count of tool tip present on all sub parts of charts with total rows on Report page.)
			softAssert.assertTrue(dashboardPage.verifyTooltipCountonReportWithFrameDriver("labelsLocatorDriverStatus", "tooltipTextDriver", "columnListDriver", "paginationReportpageDriver", "frameLocator", "driverstatusChartVisibility", "driverstatusChartVisibility",15,80,"legendsDownArrow"), "Tooltip count of Driver Status chart did match with reports total");

			// Test Case 5 (This test case validates the header of reports page.)
			softAssert.assertTrue(dashboardPage.driverChartHeaderTextVerificationOnReportPageFrame(LanguageCode, "labelsLocatorDriverStatus", "driverCount", "headerListLocatorOnReportPage", DASHBOARD_CHART_DRIVER_INVENTORY_REPORT_PAGE, "frameLocator"), "Report header for Driver status chart did not match on report page.");

			// Test Case 6 (This test case validates the labels of the chart. )
			softAssert.assertTrue(dashboardPage.verifyChartLabelsDashboardPage("labelsLocatorDriverStatus", dashboardPage.getTextLanguage(LanguageCode, "daas_ui", DRIVER_STATUS_CHART_LABELS_LIST)), "Labels of Driver Inventory Chart did not match");

			// Test Case 7 (This test case validates no data message on chart.)
			dashboardPage.scrollUpCharts();
			dashboardPage.clickOnElementsOfDashboardPage("companyDropdown");
			softAssert.assertTrue(dashboardPage.verifyCompanyChangeOfDashboardPage(LanguageCode, "companySearchBox", DashboardVariables.DASHBOARD_CHART_COMPANY_NAME_MSP_US, "companyEmptyText", "companyList", "dropdownBoxCompany"), "Company Change is not working correctly");
			softAssert.assertTrue(dashboardPage.verifyChartsHasNoDataOnDashBoardPage(LanguageCode, "noDataDriverStatusChart", "dashboard.charts.title.driver_status"), "No data message for Driver Status Chart is incorrect.");
			dashboardPage.clickOnElementsOfDashboardPage("clearCompany");
			dashboardPage.waitForPageLoaded();
			dashboardPage.scrollDownCharts();

			// Test Case 8 (This test case validates redirection to device details page from Report page.)
			Assert.assertFalse(dashboardPage.verifyChartisLoadedDynamic(LanguageCode, "driverStatusChart", "fatalErrorDriverStatus", 5), "Driver Status Chart did not load successfully.");
			softAssert.assertTrue(dashboardPage.verifyDeviceNameRedirectionMSPWithFrame1(LanguageCode, "driverCount", "labelsLocatorDriverStatus", "deviceDetailsTitle", "devicenameColumnofDriverStatusChart", "errorLocatorForNoDevice", "frameLocator","legendsDownArrow"), "Device Name Redirection is not working properly for MEMORY UTILIZATION chart");
			softAssert.assertAll();
			LOGGER.info("Validation of Driver Status Chart completed successfully");
		} else {

			if (!toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, "MSP_ADMIN_US_EMAIl"))){
				Assert.assertTrue(setToDefaultDashboard(LanguageCode),"Error occured in setting dashboard to default mode.");
			}else{
				Assert.assertTrue(setToDefaultDashboardGlobalFilter(LanguageCode),"Error occured in setting dashboard to default mode.");
			}

			// Verify toggle status
			Assert.assertTrue(toggleVerification(DashboardVariables.REDESIGN, getCredentials(environment, "MSP_ADMIN_US_EMAIl")), "[TC:277168, 277502, 277179]" + DashboardVariables.REDESIGN + "LD-Toggle is disabled for Hide and Show functionaliy");
			LOGGER.info("Verified LD-Toggle status");
			// Reset current configuration of dashboard chart

			dashboardPage.waitForPageLoaded();
			// D Test Case 1 (This Test case validates title of the chart.)
			softAssert.assertTrue(dashboardPage.verifyTitleofChartOfDashboardPage("driverStatusTitleFlexi", dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.title.driver_status")), "Driver Status Chart title did not match.");

			// D Test Case 2 (This test case validate whether chart is loaded or not.)
			Assert.assertFalse(dashboardPage.verifyChartisLoadedDynamic(LanguageCode, "driverStatusChartFlexi", "fatalErrorDriverStatus", 5), "Driver Status Chart did not load successfully.");

			// D Test Case 3 (This test case validate whether chart has any data or not.)
			Assert.assertFalse(dashboardPage.verifyChartsHasNoDataOnDashBoardPageFlexi(LanguageCode, "noDataDriverStatusChartFlexi", "dashboard.charts.title.driver_status"), "Driver Status Chart does not have any data.");

			// D Test Case 4 (This test case validates total-count is present on driver Status chart .)
			softAssert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("driverCountFlexi"), "Total count is not Present");

			// Test Case 5 (This test case validates the count of tool tip present on all sub parts of charts with total rows on Report page.)
			dashboardPage.scrollToDashboardPage("driverStatusTitleFlexi");
			softAssert.assertTrue(dashboardPage.verifyTooltipCountonReportWithFrameDriver("labelsLocatorDriverStatusFlexi" , "tooltipTextDriverFlexi" , "columnListDriverFlexi", "paginationReportpageDriverFlexi" , "frameLocator", "driverstatusChartVisibilityFlexi" , "driverstatusChartVisibilityFlexi",15,80,"legendsDownArrow") ,"Tooltip count of Driver Status chart did match with reports total");
			
			// Test Case 6 (This test case validates the header of reports page.)
			softAssert.assertTrue(dashboardPage.driverChartHeaderTextVerificationOnReportPageFrameFlexi(LanguageCode, "labelsLocatorDriverStatusFlexi", "headerListLocatorOnReportPageDriverStatus", DASHBOARD_CHART_DRIVER_INVENTORY_REPORT_PAGE, "frameLocator", "driverstatusChartVisibilityFlexi", "driverstatusChartVisibilityFlexi","legendsDownArrow"), "Report header for Driver status chart did not match on report page.");
			
			// Test Case 7 (This test case validates the labels of the chart. )
			softAssert.assertTrue(dashboardPage.verifyChartLabelsDashboardPage("labelsLocatorDriverStatusFlexi", dashboardPage.getTextLanguage(LanguageCode, "daas_ui", DRIVER_STATUS_CHART_LABELS_LIST)), "Labels of Driver Inventory Chart did not match");

			// Test Case 8 (This test case validates no data message on chart.)
			dashboardPage.scrollUpCharts();
			dashboardPage.waitForPageLoaded();
			if (!toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, "MSP_ADMIN_US_EMAIl_DASHBOARD"))){
				dashboardPage.clickOnElementsOfDashboardPage("companyDropdownFlexi");
				softAssert.assertTrue(dashboardPage.verifyCompanyChangeOfDashboardPage(LanguageCode, "companySearchBoxFlexi", DashboardVariables.DASHBOARD_CHART_COMPANY_NAME_MSP_US, "companyEmptyText", "companyList", "dropdownBoxCompanyFlexi"), "Company Change is not working correctly");
				softAssert.assertTrue(dashboardPage.verifyChartsHasNoDataOnDashBoardPageFlexi(LanguageCode, "noDataDriverStatusChartFlexi", "dashboard.charts.title.driver_status"), "No data message for Driver Status Chart is incorrect.");
				dashboardPage.clickOnElementsOfDashboardPage("clearCompanyFlexi");
				dashboardPage.waitForPageLoaded();
			}else{
				dashboardPage.clickOnElementsOfDashboardPage("companyDropdownGlobalFilter");
				softAssert.assertTrue(dashboardPage.verifyCompanyChangeOfDashboardPageGlobalFilter(LanguageCode, "companySearchGlobalFilter", DashboardVariables.DASHBOARD_CHART_COMPANY_NAME_MSP_US, "companyEmptyTextGlobalFilter", "companyListGlobalFilter", "allCompanyTextFlexiGlobalFilter","globalFilterSave"), "Company Change is not working correctly");
				dashboardPage.waitForPageLoaded();
				softAssert.assertTrue(dashboardPage.verifyChartsHasNoDataOnDashBoardPageFlexi(LanguageCode, "noDataDriverStatusChartFlexi", "dashboard.charts.title.driver_status"), "No data message for Driver Status Chart is incorrect.");
				dashboardPage.clickOnElementsOfDashboardPage("companyDropdownGlobalFilter");
				dashboardPage.clickOnElementsOfDashboardPage("clearGlobalFilter");
				dashboardPage.clickOnElementsOfDashboardPage("globalFilterSave");
			}
			dashboardPage.scrollDownCharts();
			// Test Case 9 (This test case validates redirection to device details page from Report page.)
			Assert.assertFalse(dashboardPage.verifyChartisLoadedDynamic(LanguageCode, "driverStatusChartFlexi", "fatalErrorDriverStatus", 5), "Driver Status Chart did not load successfully.");
			softAssert.assertTrue(dashboardPage.verifyDeviceNameRedirectionMSPWithFrame1(LanguageCode, "driverstatusChartVisibilityFlexi", "labelsLocatorDriverStatusFlexi", "deviceDetailsTitle", "devicenameColumnofDriverStatusChartFlexi", "errorLocatorForNoDevice", "frameLocator", "legendsDownArrow"), "Device Name Redirection is not working properly for Driver status chart");
			softAssert.assertAll();
			LOGGER.info("Validation of Driver Status Chart completed successfully");
		}
	}

	/**
	 * This test case verified devices for out dated drivers .
	 * 
	 * @throws Exception
	 */
	@Test(priority = 35, groups = { "REGRESSION", "REGRESSION_CI", "ALL_CI", "ALL", "REGRESSIONDASHBOARD2" }, description = "[300768,912125]")
	public final void verifyDevicesWithOutdatedDrivers() throws Exception {
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		List<String> expectedList = new ArrayList<String>(Arrays.asList(getTextLanguage(LanguageCode, "daas_reports_ui", "Global.gridHeaders.companyName"), getTextLanguage(LanguageCode, "daas_reports_ui", "gridHeaders.Hardware_Inventory.Details.allData.lastSignedinUser"), getTextLanguage(LanguageCode, "daas_reports_ui", "Global.gridHeaders.serialNumber"), getTextLanguage(LanguageCode, "daas_reports_ui", "Global.gridHeaders.deviceName"),
				getTextLanguage(LanguageCode, "daas_reports_ui", "gridHeaders.details.criticalCount"), getTextLanguage(LanguageCode, "daas_reports_ui", "Global.gridHeaders.deviceType"), getTextLanguage(LanguageCode, "daas_reports_ui", "Global.gridHeaders.deviceManufacturer"), getTextLanguage(LanguageCode, "daas_reports_ui", "Global.gridHeaders.deviceModel"), getTextLanguage(LanguageCode, "daas_reports_ui", "Global.gridHeaders.manufacturerDate"), 
				getTextLanguage(LanguageCode, "daas_reports_ui", "gridHeaders.Hardware_BIOS.Details.device_platform_id"), getTextLanguage(LanguageCode, "daas_reports_ui", "Global.gridHeaders.operatingSystem"), getTextLanguage(LanguageCode, "daas_reports_ui", "gridHeaders.hardware_Inventory.Details.operating_system_release"), getTextLanguage(LanguageCode, "daas_reports_ui", "gridHeaders.hardware_Inventory.Details.operating_system_Build_number"),
				getTextLanguage(LanguageCode, "daas_reports_ui", "gridHeaders.Hardware_Inventory.Details.allData.osType"), getTextLanguage(LanguageCode, "daas_reports_ui", "Global.gridHeaders.driverStatus"), getTextLanguage(LanguageCode, "daas_reports_ui", "gridHeaders.driverInventory.latestDriverCriticality"), 
				getTextLanguage(LanguageCode, "daas_reports_ui", "Global.gridHeaders.location1"), getTextLanguage(LanguageCode, "daas_reports_ui", "Global.gridHeaders.location2"), getTextLanguage(LanguageCode, "daas_reports_ui", "Global.gridHeaders.location3"), getTextLanguage(LanguageCode, "daas_reports_ui", "Global.gridHeaders.location4")));
		
		if (!toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, "MSP_ADMIN_US_EMAIl"))){
			Assert.assertTrue(setToDefaultDashboard(LanguageCode),"Error occured in setting dashboard to default mode.");
		}else{
			Assert.assertTrue(setToDefaultDashboardGlobalFilter(LanguageCode),"Error occured in setting dashboard to default mode.");
		}

		// Verify toggle status
		Assert.assertTrue(toggleVerification(DashboardVariables.REDESIGN, getCredentials(environment, "MSP_ADMIN_US_EMAIl")), "[TC:277168, 277502, 277179]" + DashboardVariables.REDESIGN + "LD-Toggle is disabled for Hide and Show functionaliy");
		LOGGER.info("Verified LD-Toggle status");
		dashboardPage.waitForPageLoaded();
		dashboardPage.waitForElementsOfDashboardPage("devicesWithOutdatedTitleFlexi");
		dashboardPage.scrollToDashboardPage("devicesWithOutdatedTitleFlexi");
		// Test case 1 (Validate title for outdated driver devices)
		softAssert.assertTrue(dashboardPage.verifyTitleofChartOfDashboardPage("devicesWithOutdatedTitleFlexi", dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "dashboard.drivers.action.outdated")), "Devices with outdated driver title did not match.");
		LOGGER.info("Title of device with outdated driver chart is match");
		if(!(dashboardPage.verifyElementsOfDashboardPage("noDataOutdatedDriverschart"))) {
			// Test case 2 (Validate Message)
			softAssert.assertTrue(dashboardPage.verifyMesageForUpdateDriver(dashboardPage.getTextOfDashboardPage("messageForCriticalUpdateFlexi"), LanguageCode), "Message for updated driver is not match");
			// Test case 3 (Validate device count for out dated driver)
			softAssert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("deviceCountOfOutdateddriverFlexi"), "devices count for out dated driver is not match");
			LOGGER.info("Device count for out dated driver is match");
			// Test case 4 validate details grid and count matching between widget and details
			softAssert.assertTrue(verifyDetailsGridForKPIWidgetsOnDashboard(LanguageCode,"deviceCountOfOutdateddriverFlexi","outdatedDriverDetailsButton","outDriver", expectedList),"The count or columns did not matched");
			LOGGER.info("Switched to Previous tab");
		}else {
			LOGGER.info("No data on Devices With Outdated Drivers chart");
		}
		softAssert.assertAll();
	}

	/**
	 * Verify add and delete functionality of custom dashboard.
	 * 
	 * @throws Exception
	 */
	@Test(priority = 36, groups = { "PENTEST","REGRESSION", "REGRESSION_CI", "ALL_CI", "ALL", "REGRESSIONDASHBOARD1", "STABILIZATION_STAGING" }, description = "Test Case ID : 340382")
	public final void verifyAddDeleteCustomDashboard() throws Exception {
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		if (toggleVerification(DashboardVariables.FLEXIDASHBOARD, "MSP_ADMIN_US_EMAIl")) {
			// Add Custom dashboard
			dashboardPage.addCustomDashboard("CUSTOM_DASHBOARD_NAME");
			// Delete custom dashboard
			dashboardPage.deleteCustomDashboard("CUSTOM_DASHBOARD_NAME");
			waitForPageLoaded();
			softAssert.assertEquals(dashboardPage.verifyCustomDashboardName("CUSTOM_DASHBOARD_NAME"), false, "Dashboard is not Deleted");
			softAssert.assertAll();
		} else {
			LOGGER.info("This is not Flexible Dashboard");
		}
	}

	/**
	 * verify add,edit and delete widget on custom dashboard.
	 * 
	 * @throws Exception
	 */
	@Test(priority = 37, groups = { "REGRESSION", "REGRESSION_CI", "ALL_CI", "ALL", "REGRESSIONDASHBOARD1", "STABILIZATION_STAGING" }, description = "Test Case ID :340382,341067,340385 ")
	public final void VerifyAddEditDeleteWidget() throws Exception {
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		if (toggleVerification(DashboardVariables.FLEXIDASHBOARD, "MSP_ADMIN_US_EMAIl")) {
			// Add custom dashboard
			dashboardPage.addCustomDashboard("CUSTOM_DASHBOARD_NAME");
			// Add Widget into custom dashboard
			String hardware = dashboardPage.getTextLanguage(LanguageCode, "MPI-Reporting-report-html-view-UI-JSON", "hardware");
			String subCategory = dashboardPage.getTextLanguage(LanguageCode, "MPI-Reporting-LHreports_service", "label.report_category_hwhealthNew");
			dashboardPage.addWidget("LanguageCode", hardware, subCategory);
			softAssert.assertEquals(dashboardPage.getWidgetCount(), 1, "Widget is not present on grid");
			LOGGER.info("Widget has been added successfully");
			// Update widget name
			dashboardPage.updateWidget("UPDATE_NAME_OF_WIDGET");
			waitForPageLoaded();
			// Delete custom dashboard
			dashboardPage.deleteWidget();
			LOGGER.info("Widget has been deleted successfully");
			sleeper(1000);
			dashboardPage.deleteCustomDashboard("CUSTOM_DASHBOARD_NAME");
			waitForPageLoaded();
			softAssert.assertEquals(dashboardPage.verifyCustomDashboardName("CUSTOM_DASHBOARD_NAME"), false, "Dashboard is not deleted");
			softAssert.assertAll();
		} else {
			LOGGER.info("This is not Flexible Dashboard");
		}
	}

	/**
	 * verify view report of widget on custom dash-board.
	 * 
	 * @throws Exception
	 */
	@Test(priority = 38, groups = { "REGRESSION", "REGRESSION_CI", "ALL_CI", "ALL", "REGRESSIONDASHBOARD1", "STABILIZATION_STAGING" }, description = "Test Case ID :340382")
	public final void VerifyVeiwReportOfWidget() throws Exception {
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		if (toggleVerification(DashboardVariables.FLEXIDASHBOARD, "MSP_ADMIN_US_EMAIl")) {
			dashboardPage.addCustomDashboard("CUSTOM_DASHBOARD_NAME");
			waitForPageLoaded();
			String hardware = dashboardPage.getTextLanguage(LanguageCode, "MPI-Reporting-report-html-view-UI-JSON", "hardware");
			String subCategory = dashboardPage.getTextLanguage(LanguageCode, "MPI-Reporting-LHreports_service", "label.report_category_hwhealthNew");
			dashboardPage.addWidget("LanguageCode", hardware, subCategory);
			softAssert.assertEquals(dashboardPage.getWidgetCount(), 1, "Widget is not present on grid");
			LOGGER.info("Widget has been added successfully");
			if(!(dashboardPage.verifyElementsOfDashboardPage("noDataonVeiwReportOfWidget"))) {
				dashboardPage.verifyElementIsClickableOfDashboardPage("clickOnWidgetUpperLeftCornerList");
				dashboardPage.clickOnElementsOfDashboardPage("clickOnWidgetUpperLeftCornerList");
				dashboardPage.clickOnElementsOfDashboardPage("veiwFullReportOptionFirst");
			}
			// validate report graph
			softAssert.assertEquals(dashboardPage.verifyViewReportOfWidget(), true, "Data is not validate");
			waitForPageLoaded();
			dashboardPage.deleteCustomDashboard("CUSTOM_DASHBOARD_NAME");// delete customdashboard
			softAssert.assertAll();
		} else {
			LOGGER.info("This is not Flexible Dashboard");
		}
	}

	/**
	 * verify add and delete widget for incident category.
	 * 
	 * @throws Exception
	 */
	@Test(priority = 39, groups = { "REGRESSION", "REGRESSION_CI", "ALL_CI", "ALL", "REGRESSIONDASHBOARD1", "STABILIZATION_STAGING" ,"PENTEST" }, description = "Test Case ID :340382")
	public final void VerifyAddCustomDashboardAddDeleteWidgetForIncident() throws Exception {
		login("MSP_ADMIN_US_EMAIl_DASHBOARD", "MSP_ADMIN_US_PASSWORD_DASHBOARD");
		SoftAssert softAssert = new SoftAssert();
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		if (toggleVerification(DashboardVariables.FLEXIDASHBOARD, "MSP_ADMIN_US_EMAIl_DASHBOARD")) {
			String incidents = dashboardPage.getTextLanguage(LanguageCode, "MPI-Reporting-report-html-view-UI-JSON", "incident");
			dashboardPage.addCustomDashboard("CUSTOM_DASHBOARD_NAME");
			waitForPageLoaded();
			dashboardPage.addWidgetForIncident(incidents, LanguageCode);
			LOGGER.info("Widget has been added successfully");
			softAssert.assertEquals(dashboardPage.getWidgetCount(), 1, "Widget is not present on grid");
			dashboardPage.deleteCustomDashboard("CUSTOM_DASHBOARD_NAME");
			softAssert.assertAll();
		} else {
			LOGGER.info("This is not Flexible Dashboard");
		}
	}

	/**
	 * verify add and delete widget for subscription category
	 * 
	 * @throws Exception
	 */
	@Test(priority = 40, groups = { "REGRESSION", "REGRESSION_CI", "ALL_CI", "ALL", "REGRESSIONDASHBOARD1", "STABILIZATION_STAGING" }, description = "Test Case ID :340382")
	public final void VerifyAddCustomDashboardAddDeleteWidgetForSubscription() throws Exception {
		login("MSP_ADMIN_US_EMAIl_DASHBOARD", "MSP_ADMIN_US_PASSWORD_DASHBOARD");
		SoftAssert softAssert = new SoftAssert();
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		if (toggleVerification(DashboardVariables.FLEXIDASHBOARD, "MSP_ADMIN_US_EMAIl_DASHBOARD")) {
			String subscription = dashboardPage.getTextLanguage(LanguageCode, "MPI-Reporting-report-html-view-UI-JSON", "subscription");
			String subcategory = dashboardPage.getTextLanguage(LanguageCode, "MPI-Reporting-report-html-view-UI-JSON", "subscription_expiration");
			dashboardPage.addCustomDashboard("CUSTOM_DASHBOARD_NAME");
			waitForPageLoaded();
			dashboardPage.addWidgetForSubscription(subscription, subcategory, LanguageCode);
			LOGGER.info("Widget has been added successfully");
			softAssert.assertEquals(dashboardPage.getWidgetCount(), 1, "Widget is not present on grid");
			dashboardPage.deleteCustomDashboard("CUSTOM_DASHBOARD_NAME");
			waitForPageLoaded();
			softAssert.assertEquals(dashboardPage.verifyCustomDashboardName("CUSTOM_DASHBOARD_NAME"), false, "Dashboard is not created");
			softAssert.assertAll();
		} else {
			LOGGER.info("This is not flexible dashboard");
		}
	}

	/**
	 * verify add,edit and delete widget for software category.
	 * 
	 * @throws Exception
	 */
	@Test(priority = 41, groups = { "REGRESSION", "REGRESSION_CI", "ALL_CI", "ALL", "REGRESSIONDASHBOARD1", "STABILIZATION_STAGING" }, description = "Test Case ID :340382")
	public final void VerifyAddCustomDashboardAddEditDeleteWidgetForSoftware() throws Exception {
		login("MSP_ADMIN_US_EMAIl_DASHBOARD", "MSP_ADMIN_US_PASSWORD_DASHBOARD");
		SoftAssert softAssert = new SoftAssert();
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		if (toggleVerification(DashboardVariables.FLEXIDASHBOARD, "MSP_ADMIN_US_EMAIl_DASHBOARD")) {
			String software = dashboardPage.getTextLanguage(LanguageCode, "MPI-Reporting-report-html-view-UI-JSON", "software");
			// List of sub-categories of Security category.
			ArrayList<String> subCategoryList = new ArrayList<String>(Arrays.asList(getTextLanguage(LanguageCode, "MPI-Reporting-LHreports_service", "label.report_category_driver"), getTextLanguage(LanguageCode, "MPI-Reporting-LHreports_service", "label.report_category_softcat"), getTextLanguage(LanguageCode, "MPI-Reporting-LHreports_service", "label.report_category_swerrors"), getTextLanguage(LanguageCode, "MPI-Reporting-LHreports_service", "label.report_category_swinv"),
					getTextLanguage(LanguageCode, "MPI-Reporting-LHreports_service", "label.report_category_softuti"), getTextLanguage(LanguageCode, "MPI-Reporting-LHreports_service", "label.report_category_winperformance")));
			waitForPageLoaded();
			dashboardPage.addCustomDashboard("CUSTOM_DASHBOARD_NAME");
			waitForPageLoaded();
			dashboardPage.addWidgetForSoftware(software, subCategoryList, LanguageCode);
			LOGGER.info("Widget has been added successfully");
			sleeper(3000);
			dashboardPage.deleteCustomDashboard("CUSTOM_DASHBOARD_NAME");
			softAssert.assertEquals(dashboardPage.verifyCustomDashboardName("CUSTOM_DASHBOARD_NAME"), false, "Dashboard is not deleted");
			softAssert.assertAll();
		} else {
			LOGGER.info("This is not flexible dashboard");
		}
	}

	/**
	 * verify add,edit and delete widget for security category
	 * 
	 * @throws Exception
	 */
	@Test(priority = 42, groups = { "REGRESSION", "REGRESSION_CI", "ALL_CI", "ALL", "REGRESSIONDASHBOARD1", "STABILIZATION_STAGING" }, description = "Test Case ID : 340382")
	public final void VerifyAddCustomDashboardAddEditDeleteWidgetForSecurity() throws Exception {
		login("MSP_ADMIN_US_EMAIl_DASHBOARD", "MSP_ADMIN_US_PASSWORD_DASHBOARD");
		SoftAssert softAssert = new SoftAssert();
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		if (toggleVerification(DashboardVariables.FLEXIDASHBOARD, "MSP_ADMIN_US_EMAIl_DASHBOARD")) {
			String security = dashboardPage.getTextLanguage(LanguageCode, "MPI-Reporting-report-html-view-UI-JSON", "security");
			// List of sub-categories of Security category.
			ArrayList<String> subCategoryList = new ArrayList<String>(Arrays.asList(getTextLanguage(LanguageCode, "MPI-Reporting-LHreports_service", "label.report_category_companysec"), getTextLanguage(LanguageCode, "MPI-Reporting-LHreports_service", "label.report_category_devicecomp"), getTextLanguage(LanguageCode, "MPI-Reporting-LHreports_service", "label.report_category_devicesec"), getTextLanguage(LanguageCode, "MPI-Reporting-LHreports_service", "label.report_category_scasecurity"),
					getTextLanguage(LanguageCode, "MPI-Reporting-LHreports_service", "label.report_category_lostdevicepro"), getTextLanguage(LanguageCode, "MPI-Reporting-LHreports_service", "label.report_category_nonrepodevice"), getTextLanguage(LanguageCode, "MPI-Reporting-LHreports_service", "label.report_category_surestart")));
			dashboardPage.addCustomDashboard("CUSTOM_DASHBOARD_NAME");
			softAssert.assertEquals(dashboardPage.verifyCustomDashboardName("CUSTOM_DASHBOARD_NAME"), true, "Dashboard is not created");
			waitForPageLoaded();
			dashboardPage.addWidgetForSecurity(security, subCategoryList, LanguageCode);
			LOGGER.info("Widget has been added successfully");
			sleeper(3000);
			dashboardPage.deleteCustomDashboard("CUSTOM_DASHBOARD_NAME");
			softAssert.assertEquals(dashboardPage.verifyCustomDashboardName("CUSTOM_DASHBOARD_NAME"), false, "Dashboard is not deleted");
			softAssert.assertAll();
		} else {
			LOGGER.info("This is not flexible dashboard");
		}
	}

	/**
	 * verify add and delete widget for hardware category.
	 * 
	 * @throws Exception
	 */
	@Test(priority = 43, groups = { "REGRESSION", "REGRESSION_CI", "ALL_CI", "ALL", "REGRESSIONDASHBOARD1", "STABILIZATION_STAGING" }, description = "Test Case ID : 340382")
	public final void VerifyAddCustomDashboardAddDeleteWidgetForHardware() throws Exception {
		login("MSP_ADMIN_US_EMAIl_DASHBOARD", "MSP_ADMIN_US_PASSWORD_DASHBOARD");
		SoftAssert softAssert = new SoftAssert();
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		if (toggleVerification(DashboardVariables.FLEXIDASHBOARD, "MSP_ADMIN_US_EMAIl_DASHBOARD")) {
			String hardware = dashboardPage.getTextLanguage(LanguageCode, "MPI-Reporting-report-html-view-UI-JSON", "hardware");
			// List of sub-categories of hardware category
			ArrayList<String> subCategoryList = new ArrayList<String>(Arrays.asList(getTextLanguage(LanguageCode, "MPI-Reporting-LHreports_service", "label.report_category_biosinventory"), getTextLanguage(LanguageCode, "MPI-Reporting-LHreports_service", "label.report_category_batteryStatRiskFact"), getTextLanguage(LanguageCode, "MPI-Reporting-LHreports_service", "label.report_category_hwbluescreen"), getTextLanguage(LanguageCode, "MPI-Reporting-LHreports_service", "label.report_category_deviceuti"),
					getTextLanguage(LanguageCode, "MPI-Reporting-LHreports_service", "label.report_category_diskcap"), getTextLanguage(LanguageCode, "MPI-Reporting-LHreports_service", "label.report_category_diskrep"), getTextLanguage(LanguageCode, "MPI-Reporting-LHreports_service", "label.report_category_hwhealth"), getTextLanguage(LanguageCode, "MPI-Reporting-LHreports_service", "label.report_category_hwinv"),
					getTextLanguage(LanguageCode, "MPI-Reporting-LHreports_service", "label.report_category_hwgradeNew"), getTextLanguage(LanguageCode, "MPI-Reporting-LHreports_service", "label.report_category_hwwar"), getTextLanguage(LanguageCode, "MPI-Reporting-LHreports_service", "label.report_category_mstelemetry")));

			dashboardPage.addCustomDashboard("CUSTOM_DASHBOARD_NAME");
			softAssert.assertEquals(dashboardPage.verifyCustomDashboardName("CUSTOM_DASHBOARD_NAME"), true, "Dashboard is not created");
			waitForAnalyticsPageLoaded();
			dashboardPage.addWidgetForHardware(hardware, subCategoryList, LanguageCode);
			LOGGER.info("Widget has been added successfully");
			sleeper(3000);
			dashboardPage.deleteCustomDashboard("CUSTOM_DASHBOARD_NAME");
			softAssert.assertEquals(dashboardPage.verifyCustomDashboardName("CUSTOM_DASHBOARD_NAME"), false, "Dashboard is not deleted");
			softAssert.assertAll();
		} else {
			LOGGER.info("This is not flexible dashboard");
		}
	}

	@Test(priority = 42, groups = { "REGRESSION", "REGRESSION_CI", "ALL_CI", "ALL", "REGRESSIONDASHBOARD1", "STABILIZATION_STAGING" }, description = "Test Case ID :882825 ")
	public final void VerifyAddCustomDashboardAddEditDeleteWidgetForNetwork() throws Exception {
		login("MSP_ADMIN_US_EMAIl_DASHBOARD", "MSP_ADMIN_US_PASSWORD_DASHBOARD");
		SoftAssert softAssert = new SoftAssert();
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		if (toggleVerification(DashboardVariables.FLEXIDASHBOARD, "MSP_ADMIN_US_EMAIl_DASHBOARD")) {
			String network = dashboardPage.getTextLanguage(LanguageCode, "MPI-Reporting-LHreports_service","label.report_type_Network");
			// List of sub-categories of Security category.
			ArrayList<String> subCategoryList = new ArrayList<String>(Arrays.asList(getTextLanguage(LanguageCode, "MPI-Reporting-LHreports_service","label.report_category_networkassmtV2"),getTextLanguage(LanguageCode, "MPI-Reporting-LHreports_service","label.report_category_networkinv")));
			dashboardPage.addCustomDashboard("CUSTOM_DASHBOARD_NAME");
			softAssert.assertEquals(dashboardPage.verifyCustomDashboardName("CUSTOM_DASHBOARD_NAME"), true, "Dashboard is not created");
			waitForPageLoaded();
			dashboardPage.addWidgetForNetwork(network, subCategoryList, LanguageCode);
			LOGGER.info("Widget has been added successfully");
			sleeper(3000);
			dashboardPage.deleteCustomDashboard("CUSTOM_DASHBOARD_NAME");
			softAssert.assertEquals(dashboardPage.verifyCustomDashboardName("CUSTOM_DASHBOARD_NAME"), false, "Dashboard is not deleted");
			softAssert.assertAll();
		} else {
			LOGGER.info("This is not flexible dashboard");
		}
	}

	
	/**
	 * verify Share of dashboard to MSP group.
	 * 
	 * @throws Exception
	 */
	@Test(priority = 44, groups = { "REGRESSION", "REGRESSION_CI", "ALL_CI", "ALL", "REGRESSIONDASHBOARD1" }, description = "Test Case ID :383742")
	public final void VerifyShareDashboardToMSPSupportTeam() throws Exception {
		login("MSP_ADMIN_US_EMAIl_DASHBOARD", "MSP_ADMIN_US_PASSWORD_DASHBOARD");
		SoftAssert softAssert = new SoftAssert();
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		if (dashboardPage.verifyElementsOfDashboardPage("logoutButton")) {
			dashboardPage.clickOnElementsOfDashboardPage("logoutButton");
		} else if (dashboardPage.verifyElementsOfDashboardPage("logoutButtonWithoutImage")) {
			dashboardPage.clickOnElementsOfDashboardPage("logoutButtonWithoutImage");
		} else {
			LOGGER.error("Logout button not present");
		}
		String senderName = dashboardPage.getTextOfDashboardPage("userNameProfile");
		String shareDashboardUserName = getEnvironmentSpecificData(System.getProperty("environment"), "SHARE_DASHBOARD_USER_NAME");
		if (toggleVerification(DashboardVariables.FLEXIDASHBOARD, "MSP_ADMIN_US_EMAIl_DASHBOARD")) {
			softAssert.assertTrue(dashboardPage.shareDashboardpage(DashboardVariables.SHARE_DASHBOARD_NAME, shareDashboardUserName), "Share Dashboard was not successful");
			waitForPageLoaded();
			logout();
			login("MSP_ADMIN_EMAIL_SHARES_DASHBOARD", "MSP_ADMIN_PASSWORD_SHARES_DASHBOARD");
			softAssert.assertTrue(dashboardPage.verifyShareDashboardpage(DashboardVariables.SHARE_DASHBOARD_NAME, shareDashboardUserName, senderName), "Share Dashboard was not added or deleted successfully");
			softAssert.assertAll();
		} else {
			LOGGER.info("This is not flexible dashboard");
		}
	}

	/**
	 * verify Share of dashboard to Partner group.
	 * 
	 * @throws Exception
	 */
	@Test(priority = 45, groups = { "REGRESSION", "REGRESSION_CI", "ALL_CI", "ALL", "REGRESSIONDASHBOARD1" }, description = "Test Case ID :383742")
	public final void VerifyShareDashboardToPartnerTeam() throws Exception {
		login("PARTNER_EMAIL_SHARE_DASHBOARD", "PARTNER_PASSWORD_SHARE_DASHBOARD");
		SoftAssert softAssert = new SoftAssert();
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		waitForPageLoaded();
		if (dashboardPage.verifyElementsOfDashboardPage("logoutButton")) {
			dashboardPage.clickOnElementsOfDashboardPage("logoutButton");
		} else if (dashboardPage.verifyElementsOfDashboardPage("logoutButtonWithoutImage")) {
			dashboardPage.clickOnElementsOfDashboardPage("logoutButtonWithoutImage");
		} else {
			LOGGER.error("Logout button not present");
		}
		String senderName = dashboardPage.getTextOfDashboardPage("userNameProfile");
		String shareDashboardUserName = getEnvironmentSpecificData(System.getProperty("environment"), "SHARE_DASHBOARD_USER_NAME_PARTNER");
		if (toggleVerification(DashboardVariables.FLEXIDASHBOARD, "MSP_ADMIN_US_EMAIl_DASHBOARD")) {
			softAssert.assertTrue(dashboardPage.shareDashboardpage(DashboardVariables.SHARE_DASHBOARD_NAME, shareDashboardUserName), "Share Dashboard was not successful");
			waitForPageLoaded();
			logout();
			login("SS_EMAIL_SHARE_DASHBOARD", "SS_PASSWORD_SHARE_DASHBOARD");
			softAssert.assertTrue(dashboardPage.verifyShareDashboardpage(DashboardVariables.SHARE_DASHBOARD_NAME, shareDashboardUserName, senderName), "Share Dashboard was not added or deleted successfully");
			softAssert.assertAll();
		} else {
			LOGGER.info("This is not flexible dashboard");
		}
	}

	/**
	 * verify Share of dashboard to Admin group.
	 * 
	 * @throws Exception
	 */
	@Test(priority = 46, groups = { "REGRESSION", "REGRESSION_CI", "ALL_CI", "ALL", "REGRESSIONDASHBOARD1" }, description = "Test Case ID :383742")
	public final void VerifyShareDashboardToAdminTeam() throws Exception {
		login("ITADMIN_EMAIL_SHARE_DASHBOARD", "ITADMIN_PASSWORD_SHARE_DASHBOARD");
		SoftAssert softAssert = new SoftAssert();
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		if (dashboardPage.verifyElementsOfDashboardPage("logoutButton")) {
			dashboardPage.clickOnElementsOfDashboardPage("logoutButton");
		} else if (dashboardPage.verifyElementsOfDashboardPage("logoutButtonWithoutImage")) {
			dashboardPage.clickOnElementsOfDashboardPage("logoutButtonWithoutImage");
		} else {
			LOGGER.error("Logout button not present");
		}
		String senderName = dashboardPage.getTextOfDashboardPage("userNameProfile");
		String shareDashboardUserName = getEnvironmentSpecificData(System.getProperty("environment"), "SHARE_DASHBOARD_USER_NAME_RA");
		if (toggleVerification(DashboardVariables.FLEXIDASHBOARD, "MSP_ADMIN_US_EMAIl_DASHBOARD")) {
			softAssert.assertTrue(dashboardPage.shareDashboardpage(DashboardVariables.SHARE_DASHBOARD_NAME, shareDashboardUserName), "Share Dashboard was not successful");
			waitForPageLoaded();
			logout();
			login("RA_EMAIL_SHARE_DASHBOARD", "RA_PASSWORD_SHARE_DASHBOARD");
			sleeper(1000);
			softAssert.assertTrue(dashboardPage.verifyShareDashboardpage(DashboardVariables.SHARE_DASHBOARD_NAME, shareDashboardUserName, senderName), "Share Dashboard was not added or deleted successfully");
			softAssert.assertAll();
		} else {
			LOGGER.info("This is not flexible dashboard");
		}
	}

	/**
	 * verify Discard functionality of Share dashboard to Partner group.
	 * 
	 * @throws Exception
	 */
	@Test(priority = 47, groups = { "REGRESSION", "REGRESSION_CI", "ALL_CI", "ALL", "REGRESSIONDASHBOARD1" }, description = "Test Case ID :383742")
	public final void VerifyDisacrdedShareDashboardToPartnerTeam() throws Exception {
		login("PARTNER_EMAIL_SHARE_DASHBOARD", "PARTNER_PASSWORD_SHARE_DASHBOARD");
		SoftAssert softAssert = new SoftAssert();
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		waitForPageLoaded();
		if (dashboardPage.verifyElementsOfDashboardPage("logoutButton")) {
			dashboardPage.clickOnElementsOfDashboardPage("logoutButton");
		} else if (dashboardPage.verifyElementsOfDashboardPage("logoutButtonWithoutImage")) {
			dashboardPage.clickOnElementsOfDashboardPage("logoutButtonWithoutImage");
		} else {
			LOGGER.error("Logout button not present");
		}
		String senderName = dashboardPage.getTextOfDashboardPage("userNameProfile");
		String shareDashboardUserName = getEnvironmentSpecificData(System.getProperty("environment"), "SHARE_DASHBOARD_USER_NAME_PARTNER");
		if (toggleVerification(DashboardVariables.FLEXIDASHBOARD, "MSP_ADMIN_US_EMAIl_DASHBOARD")) {
			softAssert.assertTrue(dashboardPage.shareDashboardpage(DashboardVariables.SHARE_DASHBOARD_NAME, shareDashboardUserName), "Share Dashboard was not successful");
			waitForPageLoaded();
			logout();
			login("SS_EMAIL_SHARE_DASHBOARD", "SS_PASSWORD_SHARE_DASHBOARD");
			softAssert.assertTrue(dashboardPage.verifyDiscardedShareDashboardpage(DashboardVariables.SHARE_DASHBOARD_NAME, shareDashboardUserName, senderName), "share dashboard was not discarded properly");
			softAssert.assertAll();
		} else {
			LOGGER.info("This is not flexible dashboard");
		}
	}

	/**
	 * Verify Global filter functionality on Devices and hierarchical multi-tenancy for MSP User.
	 * 
	 * @throws Exception
	 */

	@Test(priority = 48, groups = { "REGRESSION", "REGRESSION_CI", "ALL_CI", "ALL", "REGRESSIONDASHBOARD1", "STABILIZATION_STAGING" }, description = "Test Case ID :406896, 407016, 407167,Test Case ID :406896, 407016, 407167, 418984")
	public final void VerifyGlobalFilterOnDevicesForMSP() throws Exception {

		login("MSP_ADMIN_GLOBAL_LOCATION_FILTER_EMAIL", "MSP_ADMIN_GLOBAL_LOCATION_FILTER_PASSWORD");

		waitForPageLoaded();
		SoftAssert softAssert = new SoftAssert();
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();

		DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
		DeviceDetailsPage deviceDetailsPage = new DeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();

		LOGGER.info("Verifying global filter functionality");
		Assert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("globalLocationFilter"), "Global location filter is not available on dashboard page");
		Assert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("globalLocationFilterDropDown"), "Global location filter dropown is not available on dashboard page");
		clearCompanyFilterDashboard(LanguageCode);
		dashboardPage.clickByJavaScriptOnDashboardPage("globalLocationFilterDropDown");
		waitForPageLoaded();

		softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("filterMenuCompaniesHeader").equalsIgnoreCase(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "dashboard.actions.filter.company.label")), "Location filter header does not match");

		dashboardPage.enterTextForDashboardPage("filterMenuCompaniesSearch", globalFilterCompany);
		LOGGER.info("Search company to set location filter");
		waitForPageLoaded();
		dashboardPage.waitForElementsOfDashboardPage("companyOnListSearch");
		softAssert.assertTrue(dashboardPage.matchTextOfDashboardPage("companyOnListSearch", globalFilterCompany), "Company name and search result company name does not match");
		if (dashboardPage.verifyElementsOfDashboardPage("companyOnListSearch"))
			dashboardPage.clickOnElementsOfDashboardPage("companyOnListSearch");
		LOGGER.info("Select company to set location filter");
		sleeper(2000);
		if (dashboardPage.verifyElementsOfDashboardPage("filterMenuLocationLevel1")) {
			dashboardPage.enterTextForDashboardPage("filterMenuLocation1Search", globalFilterLocation1);
			waitForPageLoaded();
			if (dashboardPage.verifyElementsOfDashboardPage("location1OnListSearch")) {
				dashboardPage.clickOnElementsOfDashboardPage("location1OnListSearch");
				LOGGER.info("Select location level1 on global location filter");
				sleeper(1000);
				if (dashboardPage.verifyElementsOfDashboardPage("filterMenuLocationLevel2")) {
					dashboardPage.enterTextForDashboardPage("filterMenuLocation2Search", globalFilterLocation2);
					waitForPageLoaded();
					if (dashboardPage.verifyElementsOfDashboardPage("location2OnListSearch")) {
						dashboardPage.clickOnElementsOfDashboardPage("location2OnListSearch");
						LOGGER.info("Select location level2 on global location filter");
						sleeper(1000);
						if (dashboardPage.verifyElementsOfDashboardPage("filterMenuLocationLevel3")) {
							dashboardPage.enterTextForDashboardPage("filterMenuLocation3Search", globalFilterLocation3);
							waitForPageLoaded();
							if (dashboardPage.verifyElementsOfDashboardPage("location3OnListSearch")) {
								dashboardPage.clickOnElementsOfDashboardPage("location3OnListSearch");
								LOGGER.info("Select location level3 on global location filter");
								sleeper(1000);
								if (dashboardPage.verifyElementsOfDashboardPage("filterMenuLocationLevel4")) {
									dashboardPage.enterTextForDashboardPage("filterMenuLocation4Search", globalFilterLocation4);
									waitForPageLoaded();
									if (dashboardPage.verifyElementsOfDashboardPage("location4OnListSearch")) {
										dashboardPage.clickOnElementsOfDashboardPage("location4OnListSearch");
										LOGGER.info("Select location level4 on global location filter");
										sleeper(1000);
									}
								}
							}
						}
					}
				}
			}
		}

		dashboardPage.clickOnElementsOfDashboardPage("globalFilterCancel");
		LOGGER.info("Global Location Filter Cancel functionality verified successfully.");
		waitForPageLoaded();

		dashboardPage.clickByJavaScriptOnDashboardPage("globalLocationFilterDropDown");
		waitForPageLoaded();
		softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("filterMenuCompaniesHeader").equalsIgnoreCase(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "dashboard.actions.filter.company.label")), "Location filter header does not match");

		dashboardPage.enterTextForDashboardPage("filterMenuCompaniesSearch", globalFilterCompany);
		LOGGER.info("Search company to set location filter");
		waitForPageLoaded();
		dashboardPage.waitForElementsOfDashboardPage("companyOnListSearch");
		softAssert.assertTrue(dashboardPage.matchTextOfDashboardPage("companyOnListSearch", globalFilterCompany), "Company name and search result company name does not match");
		if (dashboardPage.verifyElementsOfDashboardPage("companyOnListSearch"))
			dashboardPage.clickOnElementsOfDashboardPage("companyOnListSearch");
		LOGGER.info("Select company to set location filter");
		sleeper(2000);
		if (dashboardPage.verifyElementsOfDashboardPage("filterMenuLocationLevel1")) {
			dashboardPage.enterTextForDashboardPage("filterMenuLocation1Search", globalFilterLocation1);
			waitForPageLoaded();
			if (dashboardPage.verifyElementsOfDashboardPage("location1OnListSearch")) {
				dashboardPage.clickOnElementsOfDashboardPage("location1OnListSearch");
				LOGGER.info("Select location level1 on global location filter");
				sleeper(1000);
				if (dashboardPage.verifyElementsOfDashboardPage("filterMenuLocationLevel2")) {
					dashboardPage.enterTextForDashboardPage("filterMenuLocation2Search", globalFilterLocation2);
					waitForPageLoaded();
					if (dashboardPage.verifyElementsOfDashboardPage("location2OnListSearch")) {
						dashboardPage.clickOnElementsOfDashboardPage("location2OnListSearch");
						LOGGER.info("Select location level2 on global location filter");
						sleeper(1000);
						if (dashboardPage.verifyElementsOfDashboardPage("filterMenuLocationLevel3")) {
							dashboardPage.enterTextForDashboardPage("filterMenuLocation3Search", globalFilterLocation3);
							waitForPageLoaded();
							if (dashboardPage.verifyElementsOfDashboardPage("location3OnListSearch")) {
								dashboardPage.clickOnElementsOfDashboardPage("location3OnListSearch");
								LOGGER.info("Select location level3 on global location filter");
								sleeper(1000);
								if (dashboardPage.verifyElementsOfDashboardPage("filterMenuLocationLevel4")) {
									dashboardPage.enterTextForDashboardPage("filterMenuLocation4Search", globalFilterLocation4);
									waitForPageLoaded();
									if (dashboardPage.verifyElementsOfDashboardPage("location4OnListSearch")) {
										dashboardPage.clickOnElementsOfDashboardPage("location4OnListSearch");
										LOGGER.info("Select location level4 on global location filter");
										sleeper(1000);
									}
								}
							}
						}
					}
				}
			}
		}

		dashboardPage.clickOnElementsOfDashboardPage("globalFilterSave");
		LOGGER.info("Global Location Filter Saved successfully.");
		waitForPageLoaded();

		gotoDevicesTab();
		LOGGER.info("Redirected to device list page");
		waitForPageLoaded();
		resetTableConfiguration();

		LOGGER.info("Verifying Global location filter applied on device list page.");
		softAssert.assertTrue(deviceListPage.verifyElementsOfDeviceListPage("companyBox"), "Company drop down option not available on device list page");
		softAssert.assertTrue(deviceListPage.verifyElementsOfDeviceListPage("locationTitle"), "Asset Location Column not available on device list page");
		deviceListPage.scrollOnDeviceListPage("companyList");
		
		if (!deviceListPage.verifyElementsOfDeviceListPage("noElementsDisplayText")) {
			for (int i = 0; i < deviceListPage.getElementsOfDeviceListPage("companyList").size(); i++)
				softAssert.assertTrue(deviceListPage.getElementsOfDeviceListPage("companyList").get(i).getText().equals(globalFilterCompany), "Company filter is not applied on device list page");

			LOGGER.info("Global location filter is applied successfully on Device list page.");
			LOGGER.info("Verifying Global location filter applied on device details page.");
			deviceListPage.scrollTillViewDeviceListPage("locationListFirstElement");
			String assetLocation = deviceListPage.getTextOfDeviceListPage("locationListFirstElement");

			deviceListPage.scrollOnDeviceListPage("firstDeviceSerialNumber");
			deviceListPage.clickOnElementsOfDeviceListPage("firstDeviceSerialNumber");
			waitForPageLoaded();
			deviceDetailsPage.waitForElementsOfDeviceDetailsPage("deviceBreadCrumbs");
			LOGGER.info("Navigated to device details page");			
			
			deviceDetailsPage.scrollOndeviceDetailsPage("locationTab");
			deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("locationTab");
			waitForPageLoaded();
			LOGGER.info("Navigated to location tab");

			LOGGER.info("Verify Location tile");
			softAssert.assertTrue(deviceDetailsPage.getTextOfDeviceDetailsPage("locationHeader").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "asset_details_location")), "Location Header does not match");
			softAssert.assertTrue(deviceDetailsPage.getTextOfDeviceDetailsPage("locationLabel").equalsIgnoreCase(deviceDetailsPage.getTextLanguage(LanguageCode, "daas_ui", "asset_details.location.geolocation")), "GeoLocation title does not match");
			deviceDetailsPage.scrollOndeviceDetailsPage("descriptionLabel");
			softAssert.assertTrue(deviceDetailsPage.getTextOfDeviceDetailsPage("descriptionLabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "assets.location.name")), "Asset Location Header does not match");
			if(!(assetLocation==null)){
			if (!assetLocation.equals("N/A")) {
				softAssert.assertTrue(deviceDetailsPage.matchTextOfDeviceDetailsPage("descriptionValue", assetLocation), "Asset Location does not match on location tab of device details page.");
			} else {
				softAssert.assertTrue(((deviceDetailsPage.matchTextOfDeviceDetailsPage("descriptionValue", "-"))||(deviceDetailsPage.matchTextOfDeviceDetailsPage("descriptionValue", assetLocation))), "Asset Location does not match on location tab of device details page.");
			}}else{
				softAssert.assertTrue(deviceDetailsPage.matchTextOfDeviceDetailsPage("descriptionValue", "-"), "Asset Location does not match on location tab of device details page.");
			}
		}else {
			LOGGER.info("Devices are not present on list page for the filtered company ");
		}

		
		if (!dashboardPage.getTextOfDashboardPage("globalFilterStatus").equals(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "assets.table.all"))) {
			dashboardPage.clickByJavaScriptOnDashboardPage("globalLocationFilterDropDown");
			waitForPageLoaded();
			dashboardPage.clickOnElementsOfDashboardPage("clearFilter");
			dashboardPage.clickOnElementsOfDashboardPage("globalFilterSave");
			LOGGER.info("Global Location Filter cleared successfully.");
			waitForPageLoaded();
		}
		softAssert.assertTrue(dashboardPage.matchTextOfDashboardPage("globalFilterStatus", dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "assets.table.all")), "Company Filter did not cleared successfully.");

		softAssert.assertAll();
		LOGGER.info("Verified Global filter functionality successfully.");
	}

	/**
	 * Verify Global filter functionality on Incidents and hierarchical multi-tenancy for MSP User.
	 * 
	 * @throws Exception
	 */
	@Test(priority = 49, groups = { "REGRESSION", "REGRESSION_CI", "ALL_CI", "ALL", "REGRESSIONDASHBOARD1" }, description = "Test Case ID :406896, 407016, 407167Test Case ID :406896, 407016, 407167, 418984")
	public final void VerifyGlobalFilterOnIncidentsForMSP() throws Exception {

		login("MSP_ADMIN_GLOBAL_LOCATION_FILTER_EMAIL", "MSP_ADMIN_GLOBAL_LOCATION_FILTER_PASSWORD");

		waitForPageLoaded();
		SoftAssert softAssert = new SoftAssert();
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();

		IncidentPage incidentPage = new IncidentPage(PreDefinedActions.getDriver()).getInstance();
		IncidentDetailsPage incidentDetailsPage = new IncidentDetailsPage(PreDefinedActions.getDriver()).getInstance();

		LOGGER.info("Verifying global filter functionality");
		Assert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("globalLocationFilter"), "Global location filter is not available on dashboard page");
		Assert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("globalLocationFilterDropDown"), "Global location filter dropown is not available on dashboard page");
		clearCompanyFilterDashboard(LanguageCode);
		dashboardPage.clickByJavaScriptOnDashboardPage("globalLocationFilterDropDown");
		waitForPageLoaded();

		softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("filterMenuCompaniesHeader").equalsIgnoreCase(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "dashboard.actions.filter.company.label")), "Location filter header does not match");

		dashboardPage.enterTextForDashboardPage("filterMenuCompaniesSearch", globalFilterCompany);
		LOGGER.info("Search company to set location filter");
		waitForPageLoaded();
		dashboardPage.waitForElementsOfDashboardPage("companyOnListSearch");
		softAssert.assertTrue(dashboardPage.matchTextOfDashboardPage("companyOnListSearch", globalFilterCompany), "Company name and search result company name does not match");
		if (dashboardPage.verifyElementsOfDashboardPage("companyOnListSearch"))
			dashboardPage.clickOnElementsOfDashboardPage("companyOnListSearch");
		LOGGER.info("Select company to set location filter");
		sleeper(2000);
		if (dashboardPage.verifyElementsOfDashboardPage("filterMenuLocationLevel1")) {
			dashboardPage.enterTextForDashboardPage("filterMenuLocation1Search", globalFilterLocation1);
			waitForPageLoaded();
			if (dashboardPage.verifyElementsOfDashboardPage("location1OnListSearch")) {
				dashboardPage.clickOnElementsOfDashboardPage("location1OnListSearch");
				LOGGER.info("Select location level1 on global location filter");
				sleeper(1000);
				if (dashboardPage.verifyElementsOfDashboardPage("filterMenuLocationLevel2")) {
					dashboardPage.enterTextForDashboardPage("filterMenuLocation2Search", globalFilterLocation2);
					waitForPageLoaded();
					if (dashboardPage.verifyElementsOfDashboardPage("location2OnListSearch")) {
						dashboardPage.clickOnElementsOfDashboardPage("location2OnListSearch");
						LOGGER.info("Select location level2 on global location filter");
						sleeper(1000);
						if (dashboardPage.verifyElementsOfDashboardPage("filterMenuLocationLevel3")) {
							dashboardPage.enterTextForDashboardPage("filterMenuLocation3Search", globalFilterLocation3);
							waitForPageLoaded();
							if (dashboardPage.verifyElementsOfDashboardPage("location3OnListSearch")) {
								dashboardPage.clickOnElementsOfDashboardPage("location3OnListSearch");
								LOGGER.info("Select location level3 on global location filter");
								sleeper(1000);
								if (dashboardPage.verifyElementsOfDashboardPage("filterMenuLocationLevel4")) {
									dashboardPage.enterTextForDashboardPage("filterMenuLocation4Search", globalFilterLocation4);
									waitForPageLoaded();
									if (dashboardPage.verifyElementsOfDashboardPage("location4OnListSearch")) {
										dashboardPage.clickOnElementsOfDashboardPage("location4OnListSearch");
										LOGGER.info("Select location level4 on global location filter");
										sleeper(1000);
									}
								}
							}
						}
					}
				}
			}
		}

		dashboardPage.clickOnElementsOfDashboardPage("globalFilterCancel");
		LOGGER.info("Global Location Filter Cancel functionality verified successfully.");
		waitForPageLoaded();

		dashboardPage.clickByJavaScriptOnDashboardPage("globalLocationFilterDropDown");
		waitForPageLoaded();
		softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("filterMenuCompaniesHeader").equalsIgnoreCase(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "dashboard.actions.filter.company.label")), "Location filter header does not match");

		dashboardPage.enterTextForDashboardPage("filterMenuCompaniesSearch", globalFilterCompany);
		LOGGER.info("Search company to set location filter");
		waitForPageLoaded();
		dashboardPage.waitForElementsOfDashboardPage("companyOnListSearch");
		softAssert.assertTrue(dashboardPage.matchTextOfDashboardPage("companyOnListSearch", globalFilterCompany), "Company name and search result company name does not match");
		if (dashboardPage.verifyElementsOfDashboardPage("companyOnListSearch"))
			dashboardPage.clickOnElementsOfDashboardPage("companyOnListSearch");
		LOGGER.info("Select company to set location filter");
		sleeper(2000);
		if (dashboardPage.verifyElementsOfDashboardPage("filterMenuLocationLevel1")) {
			dashboardPage.enterTextForDashboardPage("filterMenuLocation1Search", globalFilterLocation1);
			waitForPageLoaded();
			if (dashboardPage.verifyElementsOfDashboardPage("location1OnListSearch")) {
				dashboardPage.clickOnElementsOfDashboardPage("location1OnListSearch");
				LOGGER.info("Select location level1 on global location filter");
				sleeper(1000);
				if (dashboardPage.verifyElementsOfDashboardPage("filterMenuLocationLevel2")) {
					dashboardPage.enterTextForDashboardPage("filterMenuLocation2Search", globalFilterLocation2);
					waitForPageLoaded();
					if (dashboardPage.verifyElementsOfDashboardPage("location2OnListSearch")) {
						dashboardPage.clickOnElementsOfDashboardPage("location2OnListSearch");
						LOGGER.info("Select location level2 on global location filter");
						sleeper(1000);
						if (dashboardPage.verifyElementsOfDashboardPage("filterMenuLocationLevel3")) {
							dashboardPage.enterTextForDashboardPage("filterMenuLocation3Search", globalFilterLocation3);
							waitForPageLoaded();
							if (dashboardPage.verifyElementsOfDashboardPage("location3OnListSearch")) {
								dashboardPage.clickOnElementsOfDashboardPage("location3OnListSearch");
								LOGGER.info("Select location level3 on global location filter");
								sleeper(1000);
								if (dashboardPage.verifyElementsOfDashboardPage("filterMenuLocationLevel4")) {
									dashboardPage.enterTextForDashboardPage("filterMenuLocation4Search", globalFilterLocation4);
									waitForPageLoaded();
									if (dashboardPage.verifyElementsOfDashboardPage("location4OnListSearch")) {
										dashboardPage.clickOnElementsOfDashboardPage("location4OnListSearch");
										LOGGER.info("Select location level4 on global location filter");
										sleeper(1000);
									}
								}
							}
						}
					}
				}
			}
		}

		dashboardPage.clickOnElementsOfDashboardPage("globalFilterSave");
		LOGGER.info("Global Location Filter Saved successfully.");
		waitForPageLoaded();

		gotoIncidentTab();
		LOGGER.info("Redirected to Incident list page");
		waitForPageLoaded();
		resetTableConfiguration();

		LOGGER.info("Verifying Global location filter applied on Incident list page.");
		softAssert.assertTrue(incidentPage.verifyElementsOfIncidentPage("locationTitle"), "Asset Location Column not available on Incident list page");

		if (!incidentPage.verifyElementsOfIncidentPage("noElementsDisplayText")) {
			incidentPage.enterTextForIncidentPage("idSearchBox", incidentPage.getTextOfIncidentPage("selectedIncidentitem"));
			sleeper(2000);
			incidentPage.scrollOnIncidentPage("companyBoxTitle");
			softAssert.assertTrue(incidentPage.getTextOfIncidentPage("firstIdCompany").equals(globalFilterCompany), "Company filter is not applied on Incident list page");
			
			incidentPage.scrollOnIncidentPage("locationTitle");
			String assetLocation = incidentPage.getTextOfIncidentPage("locationList");
			incidentPage.clickOnElementsOfIncidentPage("selectedIncidentTitle");
			waitForPageLoaded();
			LOGGER.info("Navigated to Incident details page");

			softAssert.assertTrue(incidentDetailsPage.verifyElementOfIncidentDetailsPage("assetLocation"), "Asset Location field is not available on Incident details page.");
			softAssert.assertTrue(incidentDetailsPage.verifyElementOfIncidentDetailsPage("assetLocationValue"), "Asset Location value is not available on Incident details page.");
			if(!(assetLocation==null)){
			if (!assetLocation.equals("N/A")) {
				softAssert.assertTrue(incidentDetailsPage.matchTextOfIncidentDetailsPage("assetLocationValue", assetLocation), "Asset Location does not match on Incident details page.");
			} else {
				softAssert.assertTrue(((incidentDetailsPage.matchTextOfIncidentDetailsPage("assetLocationValue", assetLocation)) || (incidentDetailsPage.matchTextOfIncidentDetailsPage("assetLocationValue", "--")) || (incidentDetailsPage.matchTextOfIncidentDetailsPage("assetLocationValue", "---"))), "Asset Location does not match on location tab of Incident details page.");
			}}else{
				softAssert.assertTrue(incidentDetailsPage.matchTextOfIncidentDetailsPage("assetLocationValue", "-"), "Asset Location does not match on Incident details page.");
			}
		} else {
			LOGGER.info("Incidents are not present on list page for the filtered company ");
		}
		
	

		if (!dashboardPage.getTextOfDashboardPage("globalFilterStatus").equals(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "assets.table.all"))) {

			dashboardPage.clickByJavaScriptOnDashboardPage("globalLocationFilterDropDown");
			waitForPageLoaded();
			dashboardPage.clickOnElementsOfDashboardPage("clearFilter");
			dashboardPage.clickOnElementsOfDashboardPage("globalFilterSave");
			LOGGER.info("Global Location Filter cleared successfully.");
			dashboardPage.waitForElementsOfDashboardPage("tableOverlay");
			waitForPageLoaded();
		}
		softAssert.assertTrue(dashboardPage.matchTextOfDashboardPage("globalFilterStatus", dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "assets.table.all")), "Company Filter did not cleared successfully.");

		softAssert.assertAll();
		LOGGER.info("Verified Global filter functionality successfully.");
	}

	/**
	 * Verify Global filter functionality on Devices and hierarchical multi-tenancy for Partner.
	 * 
	 * @throws Exception
	 */

	@Test(priority = 50, groups = { "REGRESSION", "REGRESSION_CI", "ALL_CI", "ALL", "REGRESSIONDASHBOARD1", "STABILIZATION_STAGING" }, description = "Test Case ID :406896, 407016, 407167Test Case ID :406896, 407016, 407167, 418984")
	public final void VerifyGlobalFilterOnDevicesForPartner() throws Exception {

		login("PARTNER_ADMIN_GLOBAL_LOCATION_FILTER_EMAIL", "PARTNER_ADMIN_GLOBAL_LOCATION_FILTER_PASSWORD");

		waitForPageLoaded();
		SoftAssert softAssert = new SoftAssert();
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();

		DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
		DeviceDetailsPage deviceDetailsPage = new DeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();

		LOGGER.info("Verifying global filter functionality");
		Assert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("globalLocationFilter"), "Global location filter is not available on dashboard page");
		Assert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("globalLocationFilterDropDown"), "Global location filter dropown is not available on dashboard page");
		clearCompanyFilterDashboard(LanguageCode);
		dashboardPage.clickByJavaScriptOnDashboardPage("globalLocationFilterDropDown");
		waitForPageLoaded();

		softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("filterMenuCompaniesHeader").equalsIgnoreCase(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "dashboard.actions.filter.company.label")), "Location filter header does not match");

		dashboardPage.enterTextForDashboardPage("filterMenuCompaniesSearch", globalFilterCompany);
		LOGGER.info("Search company to set location filter");
		waitForPageLoaded();
		dashboardPage.waitForElementsOfDashboardPage("companyOnListSearch");
		softAssert.assertTrue(dashboardPage.matchTextOfDashboardPage("companyOnListSearch", globalFilterCompany), "Company name and search result company name does not match");
		if (dashboardPage.verifyElementsOfDashboardPage("companyOnListSearch"))
			dashboardPage.clickOnElementsOfDashboardPage("companyOnListSearch");
		LOGGER.info("Select company to set location filter");
		sleeper(2000);
		if (dashboardPage.verifyElementsOfDashboardPage("filterMenuLocationLevel1")) {
			dashboardPage.enterTextForDashboardPage("filterMenuLocation1Search", globalFilterLocation1);
			waitForPageLoaded();
			if (dashboardPage.verifyElementsOfDashboardPage("location1OnListSearch")) {
				dashboardPage.clickOnElementsOfDashboardPage("location1OnListSearch");
				LOGGER.info("Select location level1 on global location filter");
				sleeper(1000);
				if (dashboardPage.verifyElementsOfDashboardPage("filterMenuLocationLevel2")) {
					dashboardPage.enterTextForDashboardPage("filterMenuLocation2Search", globalFilterLocation2);
					waitForPageLoaded();
					if (dashboardPage.verifyElementsOfDashboardPage("location2OnListSearch")) {
						dashboardPage.clickOnElementsOfDashboardPage("location2OnListSearch");
						LOGGER.info("Select location level2 on global location filter");
						sleeper(1000);
						if (dashboardPage.verifyElementsOfDashboardPage("filterMenuLocationLevel3")) {
							dashboardPage.enterTextForDashboardPage("filterMenuLocation3Search", globalFilterLocation3);
							waitForPageLoaded();
							if (dashboardPage.verifyElementsOfDashboardPage("location3OnListSearch")) {
								dashboardPage.clickOnElementsOfDashboardPage("location3OnListSearch");
								LOGGER.info("Select location level3 on global location filter");
								sleeper(1000);
								if (dashboardPage.verifyElementsOfDashboardPage("filterMenuLocationLevel4")) {
									dashboardPage.enterTextForDashboardPage("filterMenuLocation4Search", globalFilterLocation4);
									waitForPageLoaded();
									if (dashboardPage.verifyElementsOfDashboardPage("location4OnListSearch")) {
										dashboardPage.clickOnElementsOfDashboardPage("location4OnListSearch");
										LOGGER.info("Select location level4 on global location filter");
										sleeper(1000);
									}
								}
							}
						}
					}
				}
			}
		}

		dashboardPage.clickOnElementsOfDashboardPage("globalFilterCancel");
		LOGGER.info("Global Location Filter Cancel functionality verified successfully.");
		waitForPageLoaded();

		dashboardPage.clickByJavaScriptOnDashboardPage("globalLocationFilterDropDown");
		waitForPageLoaded();
		softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("filterMenuCompaniesHeader").equalsIgnoreCase(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "dashboard.actions.filter.company.label")), "Location filter header does not match");

		dashboardPage.enterTextForDashboardPage("filterMenuCompaniesSearch", globalFilterCompany);
		LOGGER.info("Search company to set location filter");
		waitForPageLoaded();
		dashboardPage.waitForElementsOfDashboardPage("companyOnListSearch");
		softAssert.assertTrue(dashboardPage.matchTextOfDashboardPage("companyOnListSearch", globalFilterCompany), "Company name and search result company name does not match");
		if (dashboardPage.verifyElementsOfDashboardPage("companyOnListSearch"))
			dashboardPage.clickOnElementsOfDashboardPage("companyOnListSearch");
		LOGGER.info("Select company to set location filter");
		sleeper(2000);
		if (dashboardPage.verifyElementsOfDashboardPage("filterMenuLocationLevel1")) {
			dashboardPage.enterTextForDashboardPage("filterMenuLocation1Search", globalFilterLocation1);
			waitForPageLoaded();
			if (dashboardPage.verifyElementsOfDashboardPage("location1OnListSearch")) {
				dashboardPage.clickOnElementsOfDashboardPage("location1OnListSearch");
				LOGGER.info("Select location level1 on global location filter");
				sleeper(1000);
				if (dashboardPage.verifyElementsOfDashboardPage("filterMenuLocationLevel2")) {
					dashboardPage.enterTextForDashboardPage("filterMenuLocation2Search", globalFilterLocation2);
					waitForPageLoaded();
					if (dashboardPage.verifyElementsOfDashboardPage("location2OnListSearch")) {
						dashboardPage.clickOnElementsOfDashboardPage("location2OnListSearch");
						LOGGER.info("Select location level2 on global location filter");
						sleeper(1000);
						if (dashboardPage.verifyElementsOfDashboardPage("filterMenuLocationLevel3")) {
							dashboardPage.enterTextForDashboardPage("filterMenuLocation3Search", globalFilterLocation3);
							waitForPageLoaded();
							if (dashboardPage.verifyElementsOfDashboardPage("location3OnListSearch")) {
								dashboardPage.clickOnElementsOfDashboardPage("location3OnListSearch");
								LOGGER.info("Select location level3 on global location filter");
								sleeper(1000);
								if (dashboardPage.verifyElementsOfDashboardPage("filterMenuLocationLevel4")) {
									dashboardPage.enterTextForDashboardPage("filterMenuLocation4Search", globalFilterLocation4);
									waitForPageLoaded();
									if (dashboardPage.verifyElementsOfDashboardPage("location4OnListSearch")) {
										dashboardPage.clickOnElementsOfDashboardPage("location4OnListSearch");
										LOGGER.info("Select location level4 on global location filter");
										sleeper(1000);
									}
								}
							}
						}
					}
				}
			}
		}

		dashboardPage.clickOnElementsOfDashboardPage("globalFilterSave");
		LOGGER.info("Global Location Filter Saved successfully.");
		waitForPageLoaded();

		gotoDevicesTab();
		LOGGER.info("Redirected to device list page");
		waitForPageLoaded();
		resetTableConfiguration();

		LOGGER.info("Verifying Global location filter applied on device list page.");
		softAssert.assertTrue(deviceListPage.verifyElementsOfDeviceListPage("companyBox"), "Company drop down option not available on device list page");
		softAssert.assertTrue(deviceListPage.verifyElementsOfDeviceListPage("locationTitle"), "Asset Location Column not available on device list page");
		deviceListPage.scrollOnDeviceListPage("companyList");
		
		if (!deviceListPage.verifyElementsOfDeviceListPage("noElementsDisplayText")) {
			for (int i = 0; i < deviceListPage.getElementsOfDeviceListPage("companyList").size(); i++)
				softAssert.assertTrue(deviceListPage.getElementsOfDeviceListPage("companyList").get(i).getText().equals(globalFilterCompany), "Company filter is not applied on device list page");

			LOGGER.info("Global location filter is applied successfully on Device list page.");
			LOGGER.info("Verifying Global location filter applied on device details page.");
			deviceListPage.scrollTillViewDeviceListPage("locationListFirstElement");
			String assetLocation = deviceListPage.getTextOfDeviceListPage("locationListFirstElement");
			
			deviceListPage.scrollTillViewDeviceListPage("firstDeviceSerialNumber");
			// Navigate to device details page
			getUrl(getEnvironment(System.getProperty("environment")) + "ui/view/devices/" + getEnvironmentSpecificData(System.getProperty("environment"), "Devices_For_Partner"));
			waitForPageLoaded();
			deviceDetailsPage.waitForElementsOfDeviceDetailsPage("deviceBreadCrumbs");
			LOGGER.info("Navigated to device details page");

			deviceDetailsPage.scrollOndeviceDetailsPage("locationTab");
			deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("locationTab");
			waitForPageLoaded();
			LOGGER.info("Navigated to location tab");

			LOGGER.info("Verify Location tile");
			softAssert.assertTrue(deviceDetailsPage.getTextOfDeviceDetailsPage("locationHeader").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "asset_details_location")), "Location Header does not match");
			softAssert.assertTrue(deviceDetailsPage.getTextOfDeviceDetailsPage("locationLabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "asset_details.location.geolocation")), "GeoLocation title does not match");
			softAssert.assertTrue(deviceDetailsPage.getTextOfDeviceDetailsPage("descriptionLabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "assets.location.name")), "Asset Location Header does not match");
			if (!assetLocation.equals("N/A")) {
				deviceDetailsPage.scrollOndeviceDetailsPage("descriptionValue");
				softAssert.assertTrue(deviceDetailsPage.getTextOfDeviceDetailsPage("descriptionValue").equalsIgnoreCase(assetLocation), "Asset Location does not match on location tab of device details page.");
			} else {
				softAssert.assertTrue(((deviceDetailsPage.getTextOfDeviceDetailsPage("descriptionValue").equalsIgnoreCase(assetLocation)) || (deviceDetailsPage.getTextOfDeviceDetailsPage("descriptionValue").equalsIgnoreCase("--")) || (deviceDetailsPage.getTextOfDeviceDetailsPage("descriptionValue").equalsIgnoreCase("-"))), "Asset Location does not match on location tab of device details page.");
			}
		} else {
			LOGGER.info("Devices are not present on list page for the filtered company ");
		}

		if (!dashboardPage.getTextOfDashboardPage("globalFilterStatus").equals(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "assets.table.all"))) {

			dashboardPage.clickByJavaScriptOnDashboardPage("globalLocationFilterDropDown");
			waitForPageLoaded();
			dashboardPage.clickOnElementsOfDashboardPage("clearFilter");
			dashboardPage.clickOnElementsOfDashboardPage("globalFilterSave");
			LOGGER.info("Global Location Filter cleared successfully.");
			waitForPageLoaded();
		}
		softAssert.assertTrue(dashboardPage.matchTextOfDashboardPage("globalFilterStatus", dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "assets.table.all")), "Company Filter did not cleared successfully.");

		softAssert.assertAll();
		LOGGER.info("Verified Global filter functionality successfully.");
	}

	/**
	 * Verify Global filter functionality on Incidents and hierarchical multi-tenancy for Partner.
	 * 
	 * @throws Exception
	 */
	@Test(priority = 51, groups = { "REGRESSION", "REGRESSION_CI", "ALL_CI", "ALL", "REGRESSIONDASHBOARD1" }, description = "Test Case ID :406896, 407016, 407167, 418984Test Case ID :406896, 407016, 407167, 418984")
	public final void VerifyGlobalFilterOnIncidentsForPartner() throws Exception {

		login("PARTNER_ADMIN_GLOBAL_LOCATION_FILTER_EMAIL", "PARTNER_ADMIN_GLOBAL_LOCATION_FILTER_PASSWORD");

		waitForPageLoaded();
		SoftAssert softAssert = new SoftAssert();
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();

		IncidentPage incidentPage = new IncidentPage(PreDefinedActions.getDriver()).getInstance();
		IncidentDetailsPage incidentDetailsPage = new IncidentDetailsPage(PreDefinedActions.getDriver()).getInstance();

		LOGGER.info("Verifying global filter functionality");
		Assert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("globalLocationFilter"), "Global location filter is not available on dashboard page");
		Assert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("globalLocationFilterDropDown"), "Global location filter dropown is not available on dashboard page");
		clearCompanyFilterDashboard(LanguageCode);
		dashboardPage.clickByJavaScriptOnDashboardPage("globalLocationFilterDropDown");
		waitForPageLoaded();

		softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("filterMenuCompaniesHeader").equalsIgnoreCase(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "dashboard.actions.filter.company.label")), "Location filter header does not match");

		dashboardPage.enterTextForDashboardPage("filterMenuCompaniesSearch", globalFilterCompany);
		LOGGER.info("Search company to set location filter");
		waitForPageLoaded();
		dashboardPage.waitForElementsOfDashboardPage("companyOnListSearch");
		softAssert.assertTrue(dashboardPage.matchTextOfDashboardPage("companyOnListSearch", globalFilterCompany), "Company name and search result company name does not match");
		if (dashboardPage.verifyElementsOfDashboardPage("companyOnListSearch"))
			dashboardPage.clickOnElementsOfDashboardPage("companyOnListSearch");
		LOGGER.info("Select company to set location filter");
		sleeper(2000);
		if (dashboardPage.verifyElementsOfDashboardPage("filterMenuLocationLevel1")) {
			dashboardPage.enterTextForDashboardPage("filterMenuLocation1Search", globalFilterLocation1);
			waitForPageLoaded();
			if (dashboardPage.verifyElementsOfDashboardPage("location1OnListSearch")) {
				dashboardPage.clickOnElementsOfDashboardPage("location1OnListSearch");
				LOGGER.info("Select location level1 on global location filter");
				sleeper(1000);
				if (dashboardPage.verifyElementsOfDashboardPage("filterMenuLocationLevel2")) {
					dashboardPage.enterTextForDashboardPage("filterMenuLocation2Search", globalFilterLocation2);
					waitForPageLoaded();
					if (dashboardPage.verifyElementsOfDashboardPage("location2OnListSearch")) {
						dashboardPage.clickOnElementsOfDashboardPage("location2OnListSearch");
						LOGGER.info("Select location level2 on global location filter");
						sleeper(1000);
						if (dashboardPage.verifyElementsOfDashboardPage("filterMenuLocationLevel3")) {
							dashboardPage.enterTextForDashboardPage("filterMenuLocation3Search", globalFilterLocation3);
							waitForPageLoaded();
							if (dashboardPage.verifyElementsOfDashboardPage("location3OnListSearch")) {
								dashboardPage.clickOnElementsOfDashboardPage("location3OnListSearch");
								LOGGER.info("Select location level3 on global location filter");
								sleeper(1000);
								if (dashboardPage.verifyElementsOfDashboardPage("filterMenuLocationLevel4")) {
									dashboardPage.enterTextForDashboardPage("filterMenuLocation4Search", globalFilterLocation4);
									waitForPageLoaded();
									if (dashboardPage.verifyElementsOfDashboardPage("location4OnListSearch")) {
										dashboardPage.clickOnElementsOfDashboardPage("location4OnListSearch");
										LOGGER.info("Select location level4 on global location filter");
										sleeper(1000);
									}
								}
							}
						}
					}
				}
			}
		}

		dashboardPage.clickOnElementsOfDashboardPage("globalFilterCancel");
		LOGGER.info("Global Location Filter Cancel functionality verified successfully.");
		waitForPageLoaded();

		dashboardPage.clickByJavaScriptOnDashboardPage("globalLocationFilterDropDown");
		waitForPageLoaded();
		softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("filterMenuCompaniesHeader").equalsIgnoreCase(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "dashboard.actions.filter.company.label")), "Location filter header does not match");

		dashboardPage.enterTextForDashboardPage("filterMenuCompaniesSearch", globalFilterCompany);
		LOGGER.info("Search company to set location filter");
		waitForPageLoaded();
		dashboardPage.waitForElementsOfDashboardPage("companyOnListSearch");
		softAssert.assertTrue(dashboardPage.matchTextOfDashboardPage("companyOnListSearch", globalFilterCompany), "Company name and search result company name does not match");
		if (dashboardPage.verifyElementsOfDashboardPage("companyOnListSearch"))
			dashboardPage.clickOnElementsOfDashboardPage("companyOnListSearch");
		LOGGER.info("Select company to set location filter");
		sleeper(2000);
		if (dashboardPage.verifyElementsOfDashboardPage("filterMenuLocationLevel1")) {
			dashboardPage.enterTextForDashboardPage("filterMenuLocation1Search", globalFilterLocation1);
			waitForPageLoaded();
			if (dashboardPage.verifyElementsOfDashboardPage("location1OnListSearch")) {
				dashboardPage.clickOnElementsOfDashboardPage("location1OnListSearch");
				LOGGER.info("Select location level1 on global location filter");
				sleeper(1000);
				if (dashboardPage.verifyElementsOfDashboardPage("filterMenuLocationLevel2")) {
					dashboardPage.enterTextForDashboardPage("filterMenuLocation2Search", globalFilterLocation2);
					waitForPageLoaded();
					if (dashboardPage.verifyElementsOfDashboardPage("location2OnListSearch")) {
						dashboardPage.clickOnElementsOfDashboardPage("location2OnListSearch");
						LOGGER.info("Select location level2 on global location filter");
						sleeper(1000);
						if (dashboardPage.verifyElementsOfDashboardPage("filterMenuLocationLevel3")) {
							dashboardPage.enterTextForDashboardPage("filterMenuLocation3Search", globalFilterLocation3);
							waitForPageLoaded();
							if (dashboardPage.verifyElementsOfDashboardPage("location3OnListSearch")) {
								dashboardPage.clickOnElementsOfDashboardPage("location3OnListSearch");
								LOGGER.info("Select location level3 on global location filter");
								sleeper(1000);
								if (dashboardPage.verifyElementsOfDashboardPage("filterMenuLocationLevel4")) {
									dashboardPage.enterTextForDashboardPage("filterMenuLocation4Search", globalFilterLocation4);
									waitForPageLoaded();
									if (dashboardPage.verifyElementsOfDashboardPage("location4OnListSearch")) {
										dashboardPage.clickOnElementsOfDashboardPage("location4OnListSearch");
										LOGGER.info("Select location level4 on global location filter");
										sleeper(1000);
									}
								}
							}
						}
					}
				}
			}
		}

		dashboardPage.clickOnElementsOfDashboardPage("globalFilterSave");
		LOGGER.info("Global Location Filter Saved successfully.");
		waitForPageLoaded();

		gotoIncidentTab();
		LOGGER.info("Redirected to Incident list page");
		waitForPageLoaded();
		resetTableConfiguration();

		LOGGER.info("Verifying Global location filter applied on Incident list page.");
		softAssert.assertFalse(incidentPage.verifyElementsOfIncidentPage("companyBox"), "Company drop down option available on Incident list page");
		
		if (!incidentPage.verifyElementsOfIncidentPage("noElementsDisplayText")) {
			incidentPage.enterTextForIncidentPage("idSearchBox", incidentPage.getTextOfIncidentPage("selectedIncidentTitle"));
			sleeper(2000);
			incidentPage.scrollOnIncidentPage("companyBoxTitle");
			softAssert.assertTrue(incidentPage.getTextOfIncidentPage("firstIdCompany").equals(globalFilterCompany), "Company filter is not applied on Incident list page");

			incidentPage.scrollOnIncidentPage("locationTitle");
			String assetLocation = incidentPage.getTextOfIncidentPage("locationList");
			incidentPage.clickOnElementsOfIncidentPage("selectedIncidentTitle");
			waitForPageLoaded();
			LOGGER.info("Navigated to Incident details page");

			softAssert.assertTrue(incidentDetailsPage.verifyElementOfIncidentDetailsPage("assetLocation"), "Asset Location field is not available on Incident details page.");
			softAssert.assertTrue(incidentDetailsPage.verifyElementOfIncidentDetailsPage("assetLocationValue"), "Asset Location value is not available on Incident details page.");
			if (!assetLocation.equals("N/A")) {
				softAssert.assertTrue(incidentDetailsPage.matchTextOfIncidentDetailsPage("assetLocationValue", assetLocation), "Asset Location does not match on Incident details page.");
			} else {
				softAssert.assertTrue(((incidentDetailsPage.matchTextOfIncidentDetailsPage("assetLocationValue", assetLocation)) || (incidentDetailsPage.matchTextOfIncidentDetailsPage("assetLocationValue", "--")) || (incidentDetailsPage.matchTextOfIncidentDetailsPage("assetLocationValue", "---"))), "Asset Location does not match on location tab of Incident details page.");
			}
		} else {
			LOGGER.info("Incidents are not present on list page for the filtered company ");
		}

		if (!dashboardPage.getTextOfDashboardPage("globalFilterStatus").equals(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "assets.table.all"))) {

			dashboardPage.clickByJavaScriptOnDashboardPage("globalLocationFilterDropDown");
			waitForPageLoaded();
			dashboardPage.clickOnElementsOfDashboardPage("clearFilter");
			dashboardPage.clickOnElementsOfDashboardPage("globalFilterSave");
			LOGGER.info("Global Location Filter cleared successfully.");
			dashboardPage.waitForElementsOfDashboardPage("tableOverlay");
			waitForPageLoaded();
		}
		
		softAssert.assertAll();
		LOGGER.info("Verified Global filter functionality successfully.");
	}

	/**
	 * Verify Global filter functionality on Devices and hierarchical multi-tenancy for Company User.
	 * 
	 * @throws Exception
	 */

	@Test(priority = 52, groups = { "REGRESSION", "REGRESSION_CI", "ALL_CI", "ALL", "REGRESSIONDASHBOARD1", "STABILIZATION_STAGING" }, description = "Test Case ID :406896, 407016, 407167, 418984")
	public final void VerifyGlobalFilterOnDevicesForCompanyUser() throws Exception {

		login("COMPANY_ADMIN_GLOBAL_LOCATION_FILTER_EMAIL", "COMPANY_ADMIN_GLOBAL_LOCATION_FILTER_PASSWORD");

		waitForPageLoaded();
		SoftAssert softAssert = new SoftAssert();
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();

		DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
		DeviceDetailsPage deviceDetailsPage = new DeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();

		LOGGER.info("Verifying global filter functionality");
		Assert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("globalLocationFilter"), "Global location filter is not available on dashboard page");
		Assert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("globalLocationFilterDropDown"), "Global location filter dropown is not available on dashboard page");
		clearCompanyFilterDashboard(LanguageCode);
		dashboardPage.clickByJavaScriptOnDashboardPage("globalLocationFilterDropDown");
		waitForPageLoaded();

		softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("filterMenuLocationLevel1").equalsIgnoreCase((dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "assets.table.heading.location") + " 1")), "Location filter header does not match");

		if (dashboardPage.verifyElementsOfDashboardPage("filterMenuLocationLevel1")) {
			dashboardPage.enterTextForDashboardPage("filterMenuLocation1Search", globalFilterLocation1);
			waitForPageLoaded();
			sleeper(3000);
			if (dashboardPage.verifyElementsOfDashboardPage("location1OnListSearch")) {
				dashboardPage.clickOnElementsOfDashboardPage("location1OnListSearch");
				LOGGER.info("Select location level1 on global location filter");
				sleeper(1000);
				if (dashboardPage.verifyElementsOfDashboardPage("filterMenuLocationLevel2")) {
					dashboardPage.enterTextForDashboardPage("filterMenuLocation2Search", globalFilterLocation2);
					waitForPageLoaded();
					if (dashboardPage.verifyElementsOfDashboardPage("location2OnListSearch")) {
						dashboardPage.clickOnElementsOfDashboardPage("location2OnListSearch");
						LOGGER.info("Select location level2 on global location filter");
						sleeper(1000);
						if (dashboardPage.verifyElementsOfDashboardPage("filterMenuLocationLevel3")) {
							dashboardPage.enterTextForDashboardPage("filterMenuLocation3Search", globalFilterLocation3);
							waitForPageLoaded();
							if (dashboardPage.verifyElementsOfDashboardPage("location3OnListSearch")) {
								dashboardPage.clickOnElementsOfDashboardPage("location3OnListSearch");
								LOGGER.info("Select location level3 on global location filter");
								sleeper(1000);
								if (dashboardPage.verifyElementsOfDashboardPage("filterMenuLocationLevel4")) {
									dashboardPage.enterTextForDashboardPage("filterMenuLocation4Search", globalFilterLocation4);
									waitForPageLoaded();
									if (dashboardPage.verifyElementsOfDashboardPage("location4OnListSearch")) {
										dashboardPage.clickOnElementsOfDashboardPage("location4OnListSearch");
										LOGGER.info("Select location level4 on global location filter");
										sleeper(1000);
									}
								}
							}
						}
					}
				}
			}
		}

		dashboardPage.clickOnElementsOfDashboardPage("globalFilterCancel");
		LOGGER.info("Global Location Filter Cancel functionality verified successfully.");
		waitForPageLoaded();

		dashboardPage.clickByJavaScriptOnDashboardPage("globalLocationFilterDropDown");
		waitForPageLoaded();
		softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("filterMenuLocationLevel1").equalsIgnoreCase((dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "assets.table.heading.location") + " 1")), "Location filter header does not match");

		if (dashboardPage.verifyElementsOfDashboardPage("filterMenuLocationLevel1")) {
			dashboardPage.enterTextForDashboardPage("filterMenuLocation1Search", globalFilterLocation1);
			waitForPageLoaded();
			sleeper(3000);
			if (dashboardPage.verifyElementsOfDashboardPage("location1OnListSearch")) {
				dashboardPage.clickOnElementsOfDashboardPage("location1OnListSearch");
				LOGGER.info("Select location level1 on global location filter");
				sleeper(1000);
				if (dashboardPage.verifyElementsOfDashboardPage("filterMenuLocationLevel2")) {
					dashboardPage.enterTextForDashboardPage("filterMenuLocation2Search", globalFilterLocation2);
					waitForPageLoaded();
					if (dashboardPage.verifyElementsOfDashboardPage("location2OnListSearch")) {
						dashboardPage.clickOnElementsOfDashboardPage("location2OnListSearch");
						LOGGER.info("Select location level2 on global location filter");
						sleeper(1000);
						if (dashboardPage.verifyElementsOfDashboardPage("filterMenuLocationLevel3")) {
							dashboardPage.enterTextForDashboardPage("filterMenuLocation3Search", globalFilterLocation3);
							waitForPageLoaded();
							if (dashboardPage.verifyElementsOfDashboardPage("location3OnListSearch")) {
								dashboardPage.clickOnElementsOfDashboardPage("location3OnListSearch");
								LOGGER.info("Select location level3 on global location filter");
								sleeper(1000);
								if (dashboardPage.verifyElementsOfDashboardPage("filterMenuLocationLevel4")) {
									dashboardPage.enterTextForDashboardPage("filterMenuLocation4Search", globalFilterLocation4);
									waitForPageLoaded();
									if (dashboardPage.verifyElementsOfDashboardPage("location4OnListSearch")) {
										dashboardPage.clickOnElementsOfDashboardPage("location4OnListSearch");
										LOGGER.info("Select location level4 on global location filter");
										sleeper(1000);
									}
								}
							}
						}
					}
				}
			}
		}

		dashboardPage.clickOnElementsOfDashboardPage("globalFilterSave");
		LOGGER.info("Global Location Filter Saved successfully.");
		waitForPageLoaded();

		gotoDevicesTab();
		LOGGER.info("Redirected to device list page");
		waitForPageLoaded();
		resetTableConfiguration();

		LOGGER.info("Verifying Global location filter applied on device list page.");
		softAssert.assertTrue(deviceListPage.verifyElementsOfDeviceListPage("locationTitle"), "Asset Location Column not available on device list page");
		LOGGER.info("Global location filter is applied successfully on Device list page.");

		if (!deviceListPage.verifyElementsOfDeviceListPage("noElementsDisplayText")) {

			LOGGER.info("Verifying Global location filter applied on device details page.");
			deviceListPage.scrollOnDeviceListPage("locationList");
			String assetLocation = deviceListPage.getTextOfDeviceListPage("locationList");

			deviceListPage.scrollOnDeviceListPage("firstDeviceSerialNumber");
			deviceListPage.clickOnElementsOfDeviceListPage("firstDeviceSerialNumber");
			waitForPageLoaded();
			deviceDetailsPage.waitForElementsOfDeviceDetailsPage("deviceDetailsPageSpinner");
			deviceDetailsPage.waitForElementsOfDeviceDetailsPageForinvisibile("deviceDetailsPageSpinner");
			deviceDetailsPage.waitForElementsOfDeviceDetailsPage("tableOverlay");
			LOGGER.info("Navigated to device details page");

			deviceDetailsPage.clickOnElementsOfDeviceDetailsPage("locationTab");
			deviceDetailsPage.waitForElementsOfDeviceDetailsPage("tableOverlay");
			waitForPageLoaded();
			LOGGER.info("Navigated to location tab");

			LOGGER.info("Verify Location tile");
			softAssert.assertTrue(deviceDetailsPage.getTextOfDeviceDetailsPage("locationHeader").equalsIgnoreCase((dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "asset_details_location"))), "Location Header does not match");
			softAssert.assertTrue(deviceDetailsPage.getTextOfDeviceDetailsPage("locationLabel").equalsIgnoreCase((dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "asset_details.location.geolocation"))), "GeoLocation title does not match");
			softAssert.assertTrue(deviceDetailsPage.getTextOfDeviceDetailsPage("descriptionLabel").equalsIgnoreCase((dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "assets.location.name"))), "Asset Location Header does not match");
			if (!assetLocation.equals("")) {
				softAssert.assertTrue(deviceDetailsPage.matchTextOfDeviceDetailsPage("descriptionValue", assetLocation), "Asset Location does not match on location tab of device details page.");
			} else {
				softAssert.assertTrue(((deviceDetailsPage.matchTextOfDeviceDetailsPage("descriptionValue", assetLocation)) || (deviceDetailsPage.matchTextOfDeviceDetailsPage("descriptionValue", "--")) || (deviceDetailsPage.matchTextOfDeviceDetailsPage("descriptionValue", "--"))), "Asset Location does not match on location tab of device details page.");
			}
		} else {
			LOGGER.info("Devices are not present on list page for the filtered company ");
		}

		if (!dashboardPage.getTextOfDashboardPage("globalFilterStatus").equals(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "assets.table.all"))) {

			dashboardPage.clickByJavaScriptOnDashboardPage("globalLocationFilterDropDown");
			waitForPageLoaded();
			dashboardPage.clickOnElementsOfDashboardPage("clearLocation1Filter");
			dashboardPage.clickOnElementsOfDashboardPage("globalFilterSave");
			LOGGER.info("Global Location Filter cleared successfully.");
			dashboardPage.waitForElementsOfDashboardPage("tableOverlay");
			waitForPageLoaded();
		}
		softAssert.assertTrue(dashboardPage.matchTextOfDashboardPage("globalFilterStatus", dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "assets.table.all")), "Company Filter did not cleared successfully.");

		softAssert.assertAll();
		LOGGER.info("Verified Global filter functionality successfully.");
	}

	/**
	 * Verify Global filter functionality on Incidents and hierarchical multi-tenancy for Company User.
	 * 
	 * @throws Exception
	 */
	@Test(priority = 53, groups = { "REGRESSION", "REGRESSION_CI", "ALL_CI", "ALL", "REGRESSIONDASHBOARD1" }, description = "Test Case ID :406896, 407016, 407167, 418984")
	public final void VerifyGlobalFilterOnIncidentsForCompanyUser() throws Exception {

		login("COMPANY_ADMIN_GLOBAL_LOCATION_FILTER_EMAIL", "COMPANY_ADMIN_GLOBAL_LOCATION_FILTER_PASSWORD");

		waitForPageLoaded();
		SoftAssert softAssert = new SoftAssert();
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();

		IncidentPage incidentPage = new IncidentPage(PreDefinedActions.getDriver()).getInstance();
		IncidentDetailsPage incidentDetailsPage = new IncidentDetailsPage(PreDefinedActions.getDriver()).getInstance();

		LOGGER.info("Verifying global filter functionality");
		Assert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("globalLocationFilter"), "Global location filter is not available on dashboard page");
		Assert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("globalLocationFilterDropDown"), "Global location filter dropown is not available on dashboard page");
		clearCompanyFilterDashboard(LanguageCode);
		dashboardPage.clickByJavaScriptOnDashboardPage("globalLocationFilterDropDown");
		waitForPageLoaded();

		softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("filterMenuLocationLevel1").equalsIgnoreCase((dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "assets.table.heading.location") + " 1")), "Location filter header does not match");

		if (dashboardPage.verifyElementsOfDashboardPage("filterMenuLocationLevel1")) {
			dashboardPage.enterTextForDashboardPage("filterMenuLocation1Search", globalFilterLocation1);
			waitForPageLoaded();
			sleeper(3000);
			if (dashboardPage.verifyElementsOfDashboardPage("location1OnListSearch")) {
				dashboardPage.clickOnElementsOfDashboardPage("location1OnListSearch");
				LOGGER.info("Select location level1 on global location filter");
				sleeper(1000);
				if (dashboardPage.verifyElementsOfDashboardPage("filterMenuLocationLevel2")) {
					dashboardPage.enterTextForDashboardPage("filterMenuLocation2Search", globalFilterLocation2);
					waitForPageLoaded();
					if (dashboardPage.verifyElementsOfDashboardPage("location2OnListSearch")) {
						dashboardPage.clickOnElementsOfDashboardPage("location2OnListSearch");
						LOGGER.info("Select location level2 on global location filter");
						sleeper(1000);
						if (dashboardPage.verifyElementsOfDashboardPage("filterMenuLocationLevel3")) {
							dashboardPage.enterTextForDashboardPage("filterMenuLocation3Search", globalFilterLocation3);
							waitForPageLoaded();
							if (dashboardPage.verifyElementsOfDashboardPage("location3OnListSearch")) {
								dashboardPage.clickOnElementsOfDashboardPage("location3OnListSearch");
								LOGGER.info("Select location level3 on global location filter");
								sleeper(1000);
								if (dashboardPage.verifyElementsOfDashboardPage("filterMenuLocationLevel4")) {
									dashboardPage.enterTextForDashboardPage("filterMenuLocation4Search", globalFilterLocation4);
									waitForPageLoaded();
									if (dashboardPage.verifyElementsOfDashboardPage("location4OnListSearch")) {
										dashboardPage.clickOnElementsOfDashboardPage("location4OnListSearch");
										LOGGER.info("Select location level4 on global location filter");
										sleeper(1000);
									}
								}
							}
						}
					}
				}
			}
		}

		dashboardPage.clickOnElementsOfDashboardPage("globalFilterCancel");
		LOGGER.info("Global Location Filter Cancel functionality verified successfully.");
		waitForPageLoaded();

		dashboardPage.clickByJavaScriptOnDashboardPage("globalLocationFilterDropDown");
		waitForPageLoaded();
		softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("filterMenuLocationLevel1").equalsIgnoreCase((dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "assets.table.heading.location") + " 1")), "Location filter header does not match");

		if (dashboardPage.verifyElementsOfDashboardPage("filterMenuLocationLevel1")) {
			dashboardPage.enterTextForDashboardPage("filterMenuLocation1Search", globalFilterLocation1);
			waitForPageLoaded();
			sleeper(3000);
			if (dashboardPage.verifyElementsOfDashboardPage("location1OnListSearch")) {
				dashboardPage.clickOnElementsOfDashboardPage("location1OnListSearch");
				LOGGER.info("Select location level1 on global location filter");
				sleeper(1000);
				if (dashboardPage.verifyElementsOfDashboardPage("filterMenuLocationLevel2")) {
					dashboardPage.enterTextForDashboardPage("filterMenuLocation2Search", globalFilterLocation2);
					waitForPageLoaded();
					if (dashboardPage.verifyElementsOfDashboardPage("location2OnListSearch")) {
						dashboardPage.clickOnElementsOfDashboardPage("location2OnListSearch");
						LOGGER.info("Select location level2 on global location filter");
						sleeper(1000);
						if (dashboardPage.verifyElementsOfDashboardPage("filterMenuLocationLevel3")) {
							dashboardPage.enterTextForDashboardPage("filterMenuLocation3Search", globalFilterLocation3);
							waitForPageLoaded();
							if (dashboardPage.verifyElementsOfDashboardPage("location3OnListSearch")) {
								dashboardPage.clickOnElementsOfDashboardPage("location3OnListSearch");
								LOGGER.info("Select location level3 on global location filter");
								sleeper(1000);
								if (dashboardPage.verifyElementsOfDashboardPage("filterMenuLocationLevel4")) {
									dashboardPage.enterTextForDashboardPage("filterMenuLocation4Search", globalFilterLocation4);
									waitForPageLoaded();
									if (dashboardPage.verifyElementsOfDashboardPage("location4OnListSearch")) {
										dashboardPage.clickOnElementsOfDashboardPage("location4OnListSearch");
										LOGGER.info("Select location level4 on global location filter");
										sleeper(1000);
									}
								}
							}
						}
					}
				}
			}
		}

		dashboardPage.clickOnElementsOfDashboardPage("globalFilterSave");
		LOGGER.info("Global Location Filter Saved successfully.");
		waitForPageLoaded();

		gotoIncidentTab();
		LOGGER.info("Redirected to Incident list page");
		waitForPageLoaded();
		resetTableConfiguration();

		LOGGER.info("Verifying Global location filter applied on Incident list page.");
		softAssert.assertTrue(incidentPage.verifyElementsOfIncidentPage("locationTitle"), "Asset Location Column not available on Incident list page");
		LOGGER.info("Global location filter is applied successfully on Incident list page.");

		if (!incidentPage.verifyElementsOfIncidentPage("noElementsDisplayText")) {
			LOGGER.info("Verifying Global location filter applied on Incident details page.");

			incidentPage.scrollOnIncidentPage("locationTitle");
			String assetLocation = incidentPage.getTextOfIncidentPage("locationListCompanyUser");
			incidentPage.clickOnElementsOfIncidentPage("selectedIncidentTitle");
			waitForPageLoaded();
			//incidentDetailsPage.waitForElementOfIncidentDetailsPage("tableOverlay");
			LOGGER.info("Navigated to Incident details page");

			softAssert.assertTrue(incidentDetailsPage.verifyElementOfIncidentDetailsPage("assetLocation"), "Asset Location field is not available on Incident details page.");
			softAssert.assertTrue(incidentDetailsPage.verifyElementOfIncidentDetailsPage("assetLocationValue"), "Asset Location value is not available on Incident details page.");
			if (!assetLocation.equals("")) {
				softAssert.assertTrue(incidentDetailsPage.matchTextOfIncidentDetailsPage("assetLocationValue", assetLocation), "Asset Location does not match on Incident details page.");
			} else {
				softAssert.assertTrue(((incidentDetailsPage.matchTextOfIncidentDetailsPage("assetLocationValue", assetLocation)) || (incidentDetailsPage.matchTextOfIncidentDetailsPage("assetLocationValue", "--")) || (incidentDetailsPage.matchTextOfIncidentDetailsPage("assetLocationValue", "---"))), "Asset Location does not match on location tab of Incident details page.");
			}
		} else {
			LOGGER.info("Incidents are not present on list page for the filtered company ");
		}

		if (!dashboardPage.getTextOfDashboardPage("globalFilterStatus").equals(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "assets.table.all"))) {

			dashboardPage.clickByJavaScriptOnDashboardPage("globalLocationFilterDropDown");
			waitForPageLoaded();
			dashboardPage.clickOnElementsOfDashboardPage("clearLocation1Filter");
			dashboardPage.clickOnElementsOfDashboardPage("globalFilterSave");
			LOGGER.info("Global Location Filter cleared successfully.");
			dashboardPage.waitForElementsOfDashboardPage("tableOverlay");
			waitForPageLoaded();
		}
		softAssert.assertTrue(dashboardPage.matchTextOfDashboardPage("globalFilterStatus", dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "assets.table.all")), "Company Filter did not cleared successfully.");

		softAssert.assertAll();
		LOGGER.info("Verified Global filter functionality successfully.");
	}


	/**
	 * Validate Incident Burndown chart for MSP and Reseller.
	 * @param username MSP/Reseller used for signIn
	 * @param password MSP/Reseller used for signIn
	 * @throws Exception
	 */
	@Test(priority = 54 , groups = {"REGRESSIONDASHBOARD1"}, dataProvider = "getLoginData", description = "Test Case ID :258590")
	public final void VerifyIncidentBurndownChart(String username, String password) throws Exception {	
		login(username, password);
		SoftAssert softAssert = new SoftAssert();
		ArrayList<String> INCIDENT_BURNDOWN_SUMMARY_LABELS_LIST = new ArrayList<>(
				Arrays.asList("dashboard.charts.title.incident_burndown.legends.created",
						"dashboard.charts.title.incident_burndown.legends.closed"));
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		IncidentPage incidentPage = new IncidentPage(PreDefinedActions.getDriver()).getInstance();
		if (!toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE,getCredentials(environment, "MSP_ADMIN_US_EMAIl_DASHBOARD"))) {
			// Verify toggle status
			Assert.assertTrue(setToDefaultDashboard(LanguageCode),"Error occured in setting dashboard to default mode.");
		} else {
			Assert.assertTrue(setToDefaultDashboardGlobalFilter(LanguageCode),"Error occured in setting dashboard to default mode.");
		}
		dashboardPage.scrollToDashboardPage("scrollToIncidentBurndownChart");
		// Test Case 1 (This Test case validates title of the chart.)

		softAssert.assertTrue(dashboardPage.verifyTitleofChartOfDashboardPage("incidentBurndownChartSummaryTitle",dashboardPage.getTextLanguage(
				LanguageCode, "daas_ui","dashboard.charts.title.incident_burndown")),"Incident Burndown Summary Chart title did not match."); 
		//Validate Lable of the chart(Create, Closed)
		softAssert.assertTrue(dashboardPage.verifyChartLabelsDashboardPage("incidentBurndownChartLegendLabel",dashboardPage.getTextLanguage(
				LanguageCode, "daas_ui", INCIDENT_BURNDOWN_SUMMARY_LABELS_LIST)),
				"Labels of Incident Burndown Chart did not match"); 
				softAssert.assertTrue(
						dashboardPage.verifyLabelHiddenWhenClickOnLegendsFlexi("incidentBurndownChartLegendLabelCreate", "incidentBurndownChartVisibility"),
						"Legends Hidden for Incident burndown chart does not work as per expection");
				softAssert.assertTrue(dashboardPage.verifyLabelHiddenWhenClickOnLegendsFlexi("incidentBurndownChartLegendLabelClose", "incidentBurndownChartVisibility"),
						"Legends Hidden for Incident burndown chart does not work as per expection");

		dashboardPage.clickOnElementsOfDashboardPage("incidentBurndownChartLegendLabelClose");
		dashboardPage.mouseHoverOfDashboardPage("incidentBurndownChartClick");
		dashboardPage.clickOnElementsOfDashboardPage("incidentBurndownChartClick");

		dashboardPage.switchToDifferentTabOfDashboardPage();
		waitForPageLoaded();
		incidentPage.scrollOnIncidentPage("dropdownStatus");
		incidentPage.waitForElementtobeClickableOfIncidentPage("dropdownStatus");
		sleeper(5000);
		incidentPage.clickOnElementsOfIncidentPage("dropdownStatus");
		
			List<String> incidentStatus = incidentPage.getTextOfListOfIncidentPage("verifyincidentStatus");

			// IncidentStatus = "Dismissed, Fixed"

			ArrayList<String> closedStatus = new ArrayList<>(Arrays.asList(
					getTextLanguage(LanguageCode, "daas_ui","incidents.com.hp.mpi.icm.status.fixed"),
					getTextLanguage(LanguageCode, "daas_ui","incidents.com.hp.mpi.icm.status.dismissed")));

			for (String status : closedStatus) {
				boolean statusMatching= false;
				for(String statusStore: incidentStatus)
				{
					if(status.equalsIgnoreCase(statusStore)) {
						statusMatching = true;
					}
				}
				softAssert.assertTrue(statusMatching,"Incident status are not present in incident list pagefor closed chart");
			}
		
			pressKey(Keys.ESCAPE);
		dashboardPage.clickOnElementsOfDashboardPage("dashboardTab");
		waitForPageLoaded();
		dashboardPage.clickOnElementsOfDashboardPage("incidentBurndownChartLegendLabelClose");
		dashboardPage.mouseHoverOfDashboardPage("incidentBurndownChartClick2");
		dashboardPage.clickOnElementsOfDashboardPage("incidentBurndownChartClick2");
		dashboardPage.switchToDifferentTabOfDashboardPage();
		waitForPageLoaded();
		incidentPage.scrollOnIncidentPage("dropdownStatus");
		incidentPage.waitForElementtobeClickableOfIncidentPage("dropdownStatus");
		incidentPage.clickOnElementsOfIncidentPage("dropdownStatus");	
		
		List<String> incidentStatusCreate=incidentPage.getTextOfListOfIncidentPage("IncidentStatusChecksCreate");
		
		ArrayList<String> createStatus= new ArrayList<>(Arrays.asList(
				getTextLanguage(LanguageCode, "daas_ui","incidents.com.hp.mpi.icm.status.new"),
				getTextLanguage(LanguageCode, "daas_ui","incidents.com.hp.mpi.icm.status.investigating"),				
				getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.status.progress"), 
				getTextLanguage(LanguageCode, "daas_ui", "partner_invite.status.awaiting_customer")));
		
		for (String statusUI : incidentStatusCreate) {
			boolean statusMatched= false;
			for(String statusStore: createStatus)
			{
				if(statusStore.equalsIgnoreCase(statusUI)) {
					statusMatched=true;				
				}
			}
			
			softAssert.assertTrue(statusMatched,"Incident status are not present in incident list page for created chart");
		}
		softAssert.assertAll();
		LOGGER.info("Validation Incident Burndown Chart completed successfully");
	}

	
	@Test(priority = 55, groups = { "REGRESSION", "REGRESSION_CI", "ALL_CI", "ALL", "SMOKE", "SMOKE_CI","REGRESSIONDASHBOARD2" }, description = "TC ID:258593")
	public final void verifyChartAllIncidentsByType() throws Exception {
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_EMAIl");
		SoftAssert softAssert = new SoftAssert();
		ArrayList<String> ALL_INCIDENTS_CHART_LEGENDS_LIST = new ArrayList<>(Arrays.asList("com.hp.mpi.icm.type.hardwarehealth","com.hp.mpi.icm.type.security","com.hp.mpi.icm.type.hwchange","com.hp.mpi.icm.type.softwarehealth","com.hp.mpi.icm.type.short.oshealth"));
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		if (!toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, "MSP_ADMIN_US_EMAIl"))){
			Assert.assertTrue(setToDefaultDashboard(LanguageCode),"Error occured in setting dashboard to default mode.");
		}else{
			Assert.assertTrue(setToDefaultDashboardGlobalFilter(LanguageCode),"Error occured in setting dashboard to default mode.");
		}
		dashboardPage.waitForPageLoaded();
		dashboardPage.scrollToDashboardPage("allIncidentsByTypeTitle");

		// Test Case 1
		softAssert.assertTrue(dashboardPage.verifyTitleofChartOfDashboardPage("allIncidentsByTypeTitle", dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.nodata.description.card_name.all_incidents_by_type")), "ALL INCIDENTS BY TYPE Chart title did not match.");

		// Test Case 2
		Assert.assertFalse(dashboardPage.verifyChartisLoadedDynamic(LanguageCode, "allIncidentsByTypeChart", "allIncidentsByTypefatalError", 5), "ALL INCIDENTS BY TYPE Chart did not load successfully.");

		// Test Case 3
		softAssert.assertTrue(dashboardPage.verifyChartLabelsDashboardPage("allIncidentsByTypeLegends", dashboardPage.getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties", ALL_INCIDENTS_CHART_LEGENDS_LIST)), "Legends of ALL INCIDENTS BY TYPE Chart did not match");

		// Test Case 4 - Verify Drilldown
		//dashboardPage.verifyTwoLevelDrillDown("allIncidentsByTypeBars","allIncidentsByTypeTooltipText","allIncidentsByTypeLegends","allIncidentsByTypeXaxisLabels");

		softAssert.assertAll();
		LOGGER.info("Validation of ALL INCIDENTS BY TYPE Chart completed successfully for MSP Admin Role");
	}
	/**
	 * User Story 515506: [DaaS][Analytics][UI]Add support of search in Dashboard name dropdown.
	 * 
	 * @throws Exception
	 */
	@Test(priority = 56, groups = { "REGRESSION", "REGRESSION_CI", "ALL_CI", "ALL", "REGRESSIONDASHBOARD1" }, description = "Test Case ID : 516406")
	public final void verifySearchDashboardNameFunctionality() throws Exception {
		login("MSP_ADMIN_US_EMAIl_DASHBOARD", "MSP_ADMIN_US_PASSWORD_DASHBOARD");
		waitForPageLoaded();
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		String dashboardName = getEnvironmentSpecificData(System.getProperty("environment"), "SEARCH_DASHBOARD_NAME");
		
		Assert.assertTrue(dashboardPage.searchDashboardinList(dashboardName));
		LOGGER.info("custom dashboard was searched successfully.");
	}
	
	@Test(priority=57,groups={"REGRESSION", "REGRESSION_CI", "ALL_CI", "ALL", "REGRESSIONDASHBOARD1"},description="TC:574043")
	public final void verifyOverEnrollmentTile() throws Exception{
		login("PARTENR_ADMIN_OVER_ENROLLMENT_EMAIL", "PARTENR_ADMIN_OVER_ENROLLMENT_PASSWROD");
		SoftAssert softassert = new SoftAssert();
		gotoCompaniesTab();
		LOGGER.info("Redirected to company list page");
		waitForPageLoaded();
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		companiesPage.waitUntilElementIsInvisibleOfCompanyPage("tableOverlay");
		companiesPage.clearFiltersOfCompaniesListPage("clearfilter");
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		gotoDashboardTab();
		if (!toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, "PARTENR_ADMIN_OVER_ENROLLMENT_EMAIL"))){
			Assert.assertTrue(setToDefaultDashboard(LanguageCode),"Error occured in setting dashboard to default mode.");
		}else{
			Assert.assertTrue(setToDefaultDashboardGlobalFilter(LanguageCode),"Error occured in setting dashboard to default mode.");
		}
		dashboardPage.waitForPageLoaded();
		
		softassert.assertTrue(dashboardPage.verifyTitleofChartOfDashboardPage("overEnrollmentTitle", dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "dashboard.widgets.over_enrollment_status")), "Over enrollment title did not match.");

		Assert.assertFalse(dashboardPage.verifyChartisLoadedDynamic(LanguageCode, "softwareInventoryChartFlexi", "fatalErrorSoftwareInventory", 5), "Over enrollment Chart did not load successfully.");

		softassert.assertTrue(dashboardPage.verifyTitleofChartOfDashboardPage("tileInnerText", dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "dashboard.widgets.over_enrollment_status.desc")), "Over enrollment tile inner text did not match.");

		Assert.assertTrue(dashboardPage.waitForElementsOfDashboardPage("viewDetailsButtonOverEnroll"), "View Details button is not present in Overenrollment tile.");
		
		Assert.assertTrue(dashboardPage.verifyPlanOnCompanyListPage(LanguageCode),"There is some error in navigation from overenrollment tile to Companies list page. ");
		softassert.assertAll();
		LOGGER.info("Validation of over enrollment tile on Partner Admin dashboard got completed successfully.");
	}
	
	
	/**
	 * This method validates over enrollment widget on dashboard.
	 * 
	 * @throws Exception
	 */
	@Test(priority = 58, groups = { "REGRESSIONDASHBOARD2" }, description = "TC:574043")
	public final void verifyOverEnrollmentWidget() throws Exception {
		login("ITA_COMPANY_DETAILS_EMAIL", "ITA_COMPANY_DETAILS_PASSWORD");
		Object[][] booleanAndStringFlag = new Object[1][2];
		DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
		DeviceDetailsPage deviceDetailsPage = new DeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		HashMap<String, String> companyDetailsforDeviceEnrollment = new HashMap<String, String>();
		CompaniesDetailsPage companiesDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver()).getInstance();
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		
		dashboardPage.waitForElementsOfDashboardPage("globalFilter");
		//Device enroll
		companyDetailsforDeviceEnrollment.put("companyName", getEnvironmentSpecificData(System.getProperty("environment"), "OVER_ENROLLMENT_WIDGET_COMP_NAME"));
		companyDetailsforDeviceEnrollment.put("companyEmailId", getEnvironmentSpecificData(System.getProperty("environment"), "ITA_COMPANY_DETAILS_EMAIL"));
		companyDetailsforDeviceEnrollment.put("companyPin", getEnvironmentSpecificData(System.getProperty("environment"), "OVER_ENROLLMENT_WIDGET_COMP_PIN"));
		settingsPage.clickOnElementsOfSettingsPage("settingsTabNonMSP");
		deviceDetailsPage.waitForElementsOfDeviceDetailsPageForinvisibile("deviceDetailsPageSpinner");
		companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("subscriptionTab");
		companiesDetailsPage.waitForElementsOfCompaniesDetailsPage("seatsInfoOnPlanOverview");
		companiesDetailsPage.getPlanNameAndUsedSeats(LanguageCode,companyDetailsforDeviceEnrollment);
		Assert.assertTrue(deviceListPage.verifyFakeDeviceListPage(companyDetailsforDeviceEnrollment, LanguageCode), "Unable to enroll Fake Device.");
		deviceListPage.waitForElementsOfDeviceListPage("firstDeviceInList");
		sleeper(5000);//element present yet click intercepts till full loading of table
		deviceListPage.clickOnElementsOfDeviceListPage("firstDeviceInList");
		deviceDetailsPage.waitForElementsOfDeviceDetailsPage("deviceDetailsPageSpinner");
		deviceDetailsPage.waitForElementsOfDeviceDetailsPageForinvisibile("deviceDetailsPageSpinner");
		deviceDetailsPage.waitForElementsOfDeviceDetailsPage("enrolledPlans");
		String enrolledPlanName = deviceDetailsPage.getTextOfDeviceDetailsPage("enrolledPlans");
		settingsPage.clickOnElementsOfSettingsPage("settingsTabNonMSP");
		deviceDetailsPage.waitForElementsOfDeviceDetailsPageForinvisibile("deviceDetailsPageSpinner");
		companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("subscriptionTab");
		companiesDetailsPage.waitForElementsOfCompaniesDetailsPage("seatsInfoOnPlanOverview");
		String seatsInfo = companiesDetailsPage.getTextOfCompaniesDetailsPage("seatsInfoOnPlanOverview");
		String extraseatsInfo = companiesDetailsPage.getTextOfCompaniesDetailsPage("overLimitText");
		booleanAndStringFlag = companiesDetailsPage.verifySeatConsumption(companyDetailsforDeviceEnrollment);
		softAssert.assertTrue((boolean) booleanAndStringFlag[0][0],"Seat consumption is not correct");
		
		//Widget verifications
		dashboardPage.clickOnElementsOfDashboardPage("dashboardTab");
		dashboardPage.waitForElementsOfDashboardPage("globalFilter");
		refreshPage();
		dashboardPage.waitForElementsOfDashboardPage("globalFilter");
		sleeper(5000);
		Assert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("overEnrollmentChart"),"Over enrollment widget not present on dashboard.");
		softAssert.assertTrue(dashboardPage.matchTextOfDashboardPage("overEnrollmentChartTitle", dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "dashboard.widgets.over_enrollment_status")),"Over enrollment widget title not as expected");
		if(dashboardPage.getElementsOfDashboardPage("manageButtonsOnOnoverEnrollmentChart").size()>1)
			{
				Assert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("manageSeatsOnOnoverEnrollmentChart"),"Manage seats button not present in Over enrollment widget.");
				Assert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("managePlansOnOnoverEnrollmentChart"),"Manage plan button not present in Over enrollment widget.");
			}
		else
				Assert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("managePlansOnOnoverEnrollmentChart"),"Manage seats button not present in Over enrollment widget.");
		softAssert.assertTrue(dashboardPage.matchTextOfDashboardPage("planNameInoverEnrollmentChart", enrolledPlanName),"Plan name not as expected");
		softAssert.assertTrue(dashboardPage.matchTextOfDashboardPage("seatsInfoOnoverEnrollmentChart", seatsInfo),"Seat info on chart not as expected");
		softAssert.assertTrue(dashboardPage.matchTextOfDashboardPage("seatsOverLimitText", extraseatsInfo),"Over limit text not as expected");
		
		softAssert.assertTrue(dashboardPage.matchTextOfDashboardPage("graceTitleOnoverEnrollmentChart", dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.account_warning_plan.seats_grace.title")),"Grace title on chart not as expected");
		softAssert.assertTrue(dashboardPage.matchTextOfDashboardPage("gracePeriodWarningOnoverEnrollmentChart", dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.account_warning_plan.seats_grace.message2")),"Grace period warning on chart not as expected");
		
		if(dashboardPage.getElementsOfDashboardPage("manageButtonsOnOnoverEnrollmentChart").size()==1)
		{
			dashboardPage.clickOnElementsOfDashboardPage("managePlansOnOnoverEnrollmentChart");
			softAssert.assertTrue(deviceListPage.verifyElementsOfDeviceListPage("firstDeviceInList"),"Redirection from manage seats to plan tab not working");
			}
		else
			{
				dashboardPage.clickOnElementsOfDashboardPage("manageSeatsOnOnoverEnrollmentChart");
				softAssert.assertTrue(deviceListPage.verifyElementsOfDeviceListPage("firstDeviceInList"),"Redirection from manage seats to plan tab not working");
				dashboardPage.clickOnElementsOfDashboardPage("dashboardTab");
				dashboardPage.waitForElementsOfDashboardPage("globalFilter");
				sleeper(5000);
				dashboardPage.clickOnElementsOfDashboardPage("managePlansOnOnoverEnrollmentChart");
				softAssert.assertTrue(companiesDetailsPage.verifyElementsOfCompaniesDetailsPage("addNewKeyButton"),"Redirection from manage plans to plan tab not working");
			}
		
		//Device unenroll
		deviceListPage.clickByJavaScriptOnDeviceListPage("devicesTab");
		deviceListPage.waitForElementsOfDeviceListPage("selectAllCheckBox");
		deviceListPage.clickOnElementsOfDeviceListPage("firstDeviceInList");
		deviceDetailsPage.waitForElementsOfDeviceDetailsPage("deviceDetailsPageSpinner");
		deviceDetailsPage.waitForElementsOfDeviceDetailsPageForinvisibile("deviceDetailsPageSpinner");
		String deviceID = getUrlOfCurrentPage().substring(getUrlOfCurrentPage().length() - 36);
		Assert.assertTrue(getStatusCode(getEnvironment(System.getProperty("environment")) + DeviceVariables.SINGLE_DEVICE_REMOVE + deviceID ,"{}","DELETE", environment) == 204,"Device removal failed");
		settingsPage.clickOnElementsOfSettingsPage("settingsTabNonMSP");
		companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("subscriptionTab");
		companiesDetailsPage.waitForElementsOfCompaniesDetailsPage("seatsInfoOnPlanOverview");
		softAssert.assertTrue(companiesDetailsPage.verifySeatConsumptionAfterUnenrollment(companyDetailsforDeviceEnrollment,enrolledPlanName),"Seat consumption incorrect after unenrollment");
		seatsInfo = companiesDetailsPage.getTextOfCompaniesDetailsPage("seatsInfoOnPlanOverview");
		String usedSeats = seatsInfo.split(" ")[0];
		if(Integer.parseInt(usedSeats)<=1) {
			dashboardPage.clickOnElementsOfDashboardPage("dashboardTab");
			dashboardPage.waitForElementsOfDashboardPage("globalFilter");
			refreshPage();
			dashboardPage.waitForElementsOfDashboardPage("globalFilter");
			sleeper(5000);
			Assert.assertFalse(dashboardPage.verifyElementsOfDashboardPage("overEnrollmentChart"),"Over enrollment widget present on dashboard.");
		}
		softAssert.assertAll();
	}
	
	/**
	 * This method validates HP Sure Sense Pro Threats widget on dashboard.
	 * 
	 * @throws Exception
	 */
	@Test(priority = 59, groups = { "REGRESSION_CI", "REGRESSIONDASHBOARD1" }, description = "TC:[553942]")
	public final void verifyChartHPSureSenseProThreats() throws Exception {
		
		login("PROACTIVE_SECURITY_CHART", "PROACTIVE_SECURITY_CHART_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		final String[] DASHBOARD_SURE_SENSE_PRO_THREATS_REPORT_PAGE = { "Global.gridHeaders.companyName","gridHeaders.Hardware_Inventory.Details.allData.lastSignedinUser","Global.gridHeaders.deviceName","Global.gridHeaders.serialNumber","Global.gridHeaders.date","gridHeaders.Hp_Sure_Click_Advanced_Threat_Protection.threat_type","gridHeaders.Hp_sure_sense_pro.threat_protection_details.file","gridHeaders.Sure_Sense_Pro.Summary.threat_protection_details.file_type","graphHeaders.Hp_sure_sense.label.fileHash","graphHeaders.Hp_sure_sense.label.ActionTaken","gridHeaders.scasecurity.threat_protection_details.view_details","graphHeaders.Hp_sure_sense.DeviceLastSeen"};
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		Assert.assertTrue(setToDefaultDashboardGlobalFilter(LanguageCode),"Error occured in setting dashboard to default mode.");

		// Reset current configuration of dashboard chart
		dashboardPage.waitForElementsOfDashboardPage("hPSureSenseProThreatsChart");
		dashboardPage.scrollToDashboardPage("hPSureSenseProThreatsChart");
		LOGGER.info("Scrolled down to HP Sure Sense Pro Threats chart");

		dashboardPage.waitForPageLoaded();
		// Test Case 1 (This Test case validates title of the chart.)
		softAssert.assertTrue(dashboardPage.verifyTitleofChartOfDashboardPage("hPSureSenseProThreatsTitle", dashboardPage.getTextLanguage(LanguageCode, "dashboard_service", "label.dashborad.global.suresensepro.sureSenseProThreatsDashboard")), "HP Sure Sense Pro Threats Chart title did not match.");

		// Test Case 2 (This test case validate whether chart is loaded or not.)
		Assert.assertFalse(dashboardPage.verifyChartisLoadedDynamic(LanguageCode, "hPSureSenseProThreatsChart", "fatalErrorhPSureSenseProThreats", 5), "HP Sure Sense Pro Threats Chart did not load successfully.");

		//Test Case 3 (This test case validate text on chart.)
		if(dashboardPage.verifyElementsOfDashboardPage("hPSureSenseProThreatsChartThreatDetectedDataTile")||dashboardPage.verifyElementsOfDashboardPage("hPSureSenseProThreatsChartThreatPreventedDataTile")) {
			if(dashboardPage.verifyElementsOfDashboardPage("hPSureSenseProThreatsChartThreatDetectedDataTile")) {
				softAssert.assertTrue(dashboardPage.matchTextOfDashboardPage("hPSureSenseProThreatsChartDataTileThreatDetectedText", dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "dashboard.hpsureclick.content.detected.title")), "HP Sure Sense Pro Threats Chart Data Tile Threat Detected text did not match.");
				// Test case 4 (This test case validates the header of reports page.)
				softAssert.assertTrue(dashboardPage.headerTextVerificationOnReportPage(LanguageCode, "hPSureSenseProThreatsChartThreatDetectedDataTile", "headerListLocatorOnReportPage", DASHBOARD_SURE_SENSE_PRO_THREATS_REPORT_PAGE), "Report header for HP Sure Sense Pro Threats Detected chart did not match on report page.");
				dashboardPage.waitForPageLoaded();
				// Test case 5 (This test case validates redirection to device details page from Report page.)
				softAssert.assertTrue(dashboardPage.verifyDeviceNameRedirectionMSPWithFrame(LanguageCode, "hPSureSenseProThreatsChartThreatDetectedDataTile", "deviceDetailsTitle", "serialNumberColumnhPSureSenseProThreats", "errorLocatorForNoDevice", "reportLayout"), "Device Name Redirection is not working properly for HP Sure Sense Pro Threats chart");
				dashboardPage.waitForPageLoaded();
				dashboardPage.switchToParentTabOfDashboardPage();
				// Test case 6 (This test case validates the presence of view details hyperlink on report page.)
				softAssert.assertTrue(dashboardPage.viewThreatDetailsRedirectionOnReportPage(LanguageCode, "hPSureSenseProThreatsChartThreatDetectedDataTile", "reportLayout", "threatPageHeader"), "Report header for HP Sure Sense Pro Threats did not match on report page.");
			}
			if(dashboardPage.verifyElementsOfDashboardPage("hPSureSenseProThreatsChartThreatPreventedDataTile")) {
				softAssert.assertTrue(dashboardPage.matchTextOfDashboardPage("hPSureSenseProThreatsChartDataTileThreatPreventedText", dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "dashboard.hpsureclick.content.prevented.title")), "HP Sure Sense Pro Threats Chart Data Tile Threat Prevented text did not match.");
				// Test case 7 (This test case validates the header of reports page.)
				softAssert.assertTrue(dashboardPage.headerTextVerificationOnReportPage(LanguageCode, "hPSureSenseProThreatsChartThreatPreventedDataTile", "headerListLocatorOnReportPage", DASHBOARD_SURE_SENSE_PRO_THREATS_REPORT_PAGE), "Report header for HP Sure Sense Pro Threats Prevented chart did not match on report page.");
				dashboardPage.waitForPageLoaded();
				// Test case 8 (This test case validates redirection to device details page from Report page.)
				softAssert.assertTrue(dashboardPage.verifyDeviceNameRedirectionMSPWithFrame(LanguageCode, "hPSureSenseProThreatsChartThreatPreventedDataTile", "deviceDetailsTitle", "serialNumberColumnhPSureSenseProThreats", "errorLocatorForNoDevice", "reportLayout"), "Device Name Redirection is not working properly for HP Sure Sense Pro Threats chart");
				dashboardPage.waitForPageLoaded();
				dashboardPage.switchToParentTabOfDashboardPage();
				// Test case 9 (This test case validates the presence of view details hyperlink on report page.)
				softAssert.assertTrue(dashboardPage.viewThreatDetailsRedirectionOnReportPage(LanguageCode, "hPSureSenseProThreatsChartThreatPreventedDataTile", "reportLayout", "threatPageHeader"), "Report header for HP Sure Sense Pro Threats did not match on report page.");
			}
			}else {
			LOGGER.info("No data present on chart.");
		}
		softAssert.assertAll();
		LOGGER.info("Validation of HP Sure Sense Pro Threats Chart completed successfully");
	}
	
	/**
	 * This method validates HP Sure Sense Pro Devices widget on dashboard.
	 * 
	 * @throws Exception
	 */
	@Test(priority = 60, groups = { "REGRESSION_CI", "REGRESSIONDASHBOARD1" }, description = "TC:[553941]")
	public final void verifyChartHPSureSenseProDevices() throws Exception {
		
		login("PROACTIVE_SECURITY_CHART", "PROACTIVE_SECURITY_CHART_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		final String[] DASHBOARD_SURE_SENSE_PRO_THREATS_REPORT_PAGE = { "Global.gridHeaders.companyName","gridHeaders.Hardware_Inventory.Details.allData.lastSignedinUser","Global.gridHeaders.deviceName","Global.gridHeaders.serialNumber","graphHeaders.Hp_sure_sense.DeviceLastSeen","graphHeaders.Hp_sure_sense.ProtectionState","gridHeaders.Hp_sure_sense_pro.device_protection_details.default_antivirus_engine","Global.gridHeaders.location1","Global.gridHeaders.location2","Global.gridHeaders.location3","Global.gridHeaders.location4"};
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		
		Assert.assertTrue(setToDefaultDashboardGlobalFilter(LanguageCode),"Error occured in setting dashboard to default mode.");

		// Reset current configuration of dashboard chart
		dashboardPage.waitForElementsOfDashboardPage("hPSureSenseProDevicesChart");
		dashboardPage.scrollToDashboardPage("hPSureSenseProDevicesChart");
		LOGGER.info("Scrolled down to HP Sure Sense Pro Devices chart");

		dashboardPage.waitForPageLoaded();
		// Test Case 1 (This Test case validates title of the chart.)
		softAssert.assertTrue(dashboardPage.verifyTitleofChartOfDashboardPage("hPSureSenseProDevicesTitle", dashboardPage.getTextLanguage(LanguageCode, "dashboard_service", "label.dashborad.global.suresensepro.sureSenseProDeviceDashboard")), "HP Sure Sense Pro Device Chart title did not match.");

		// Test Case 2 (This test case validate whether chart is loaded or not.)
		Assert.assertFalse(dashboardPage.verifyChartisLoadedDynamic(LanguageCode, "hPSureSenseProDevicesChart", "fatalErrorhPSureSenseProDevices", 5), "HP Sure Sense Pro Devices Chart did not load successfully.");

		//Test Case 3 (This test case validate text on chart.)
		if(dashboardPage.verifyElementsOfDashboardPage("hPSureSenseProThreatsChartDevicesProtectedDataTile")||dashboardPage.verifyElementsOfDashboardPage("hPSureSenseProThreatsChartDevicesNotProtectedDataTile")) {
			if(dashboardPage.verifyElementsOfDashboardPage("hPSureSenseProThreatsChartDevicesProtectedDataTile")) {
				softAssert.assertTrue(dashboardPage.matchTextOfDashboardPage("hPSureSenseProThreatsChartDataTileDevicesProtectedText", dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "dashboard.suredevice.content.protected.device")), "HP Sure Sense Pro Devices Chart Data Tile Device protected text did not match.");
				// Test case 4 (This test case validates the header of reports page.)
				softAssert.assertTrue(dashboardPage.headerTextVerificationOnReportPage(LanguageCode, "hPSureSenseProThreatsChartDevicesProtectedDataTile", "headerListLocatorOnReportPage", DASHBOARD_SURE_SENSE_PRO_THREATS_REPORT_PAGE), "Report header for HP Sure Sense Pro Device protected chart did not match on report page for Device protected.");
				dashboardPage.waitForPageLoaded();
				// Test case 5 (This test case validates redirection to device details page from Report page.)
				softAssert.assertTrue(dashboardPage.verifyDeviceNameRedirectionMSPWithFrame(LanguageCode, "hPSureSenseProThreatsChartDevicesProtectedDataTile", "deviceDetailsTitle", "serialNumberColumnhPSureSenseProDevices", "errorLocatorForNoDevice", "reportLayout"), "Device Name Redirection is not working properly for HP Sure Sense Pro Device chart for Device protected");
				dashboardPage.waitForPageLoaded();
				dashboardPage.switchToParentTabOfDashboardPage();
			}
			if(dashboardPage.verifyElementsOfDashboardPage("hPSureSenseProThreatsChartDevicesNotProtectedDataTile")) {
				softAssert.assertTrue(dashboardPage.matchTextOfDashboardPage("hPSureSenseProThreatsChartDataTileDevicesNotProtectedText", dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "dashboard.hpsureclick.content.unprotected.title")), "HP Sure Sense Pro Devices Chart Data Tile Device not protected text did not match.");
				// Test case 6 (This test case validates the header of reports page.)
				softAssert.assertTrue(dashboardPage.headerTextVerificationOnReportPage(LanguageCode, "hPSureSenseProThreatsChartDevicesNotProtectedDataTile", "headerListLocatorOnReportPage", DASHBOARD_SURE_SENSE_PRO_THREATS_REPORT_PAGE), "Report header for HP Sure Sense Pro Device not protected chart did not match on report page for Device not protected.");
				dashboardPage.waitForPageLoaded();
				// Test case 7 (This test case validates redirection to device details page from Report page.)
				softAssert.assertTrue(dashboardPage.verifyDeviceNameRedirectionMSPWithFrame(LanguageCode, "hPSureSenseProThreatsChartDevicesNotProtectedDataTile", "deviceDetailsTitle", "serialNumberColumnhPSureSenseProDevices", "errorLocatorForNoDevice", "reportLayout"), "Device Name Redirection is not working properly for HP Sure Sense Pro Device chart for Device not protected");
			}	
		}else {
			LOGGER.info("No data present on chart.");
		}
		
		softAssert.assertAll();
		LOGGER.info("Validation of HP Sure Sense Pro Device Chart completed successfully");
	}
	
	
	/**
	 * User Story 661796: [Analytics][UI] User Wants to See in drilldown only the critical updates available for the device
	 * This method validates the Critical Missing Windows Update Widget
	 * @throws Exception
	 */
	@Test(priority = 61, groups = { "REGRESSION_CI", "REGRESSIONDASHBOARD1" }, description = "TC:[258896,912125]")
	public final void verifyCriticalWindowsUpdateWidget() throws Exception {
		login("MSP_ADMIN_US_EMAIl_REPORTS", "MSP_ADMIN_US_PASSWORD_DASHBOARD");
		SoftAssert softAssert = new SoftAssert();
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		List<String> expectedList = new ArrayList<String>(Arrays.asList(getTextLanguage(LanguageCode, "daas_reports_ui", "Global.gridHeaders.companyName"), getTextLanguage(LanguageCode, "daas_reports_ui", "gridHeaders.Hardware_Inventory.Details.allData.lastSignedinUser"), getTextLanguage(LanguageCode, "daas_reports_ui", "Global.gridHeaders.deviceName"), getTextLanguage(LanguageCode, "daas_reports_ui", "Global.gridHeaders.serialNumber"),
				getTextLanguage(LanguageCode, "daas_reports_ui", "gridHeaders.details.criticalCount"), getTextLanguage(LanguageCode, "daas_reports_ui", "Global.gridHeaders.deviceType"), getTextLanguage(LanguageCode, "daas_reports_ui", "Global.gridHeaders.deviceManufacturer"), getTextLanguage(LanguageCode, "daas_reports_ui", "Global.gridHeaders.deviceModel"), getTextLanguage(LanguageCode, "daas_reports_ui", "Global.gridHeaders.manufacture_date"), 
				getTextLanguage(LanguageCode, "daas_reports_ui", "Global.gridHeaders.operatingSystem"), getTextLanguage(LanguageCode, "daas_reports_ui", "gridHeaders.hardware_Inventory.Details.operating_system_release"), getTextLanguage(LanguageCode, "daas_reports_ui", "Global.gridHeaders.lastSeen"), getTextLanguage(LanguageCode, "daas_reports_ui", "gridHeaders.Software_Update.WinUpdate.By_Update.missing_update_criticality"), 
				getTextLanguage(LanguageCode, "daas_reports_ui", "Global.gridHeaders.location1"), getTextLanguage(LanguageCode, "daas_reports_ui", "Global.gridHeaders.location2"), getTextLanguage(LanguageCode, "daas_reports_ui", "Global.gridHeaders.location3"), getTextLanguage(LanguageCode, "daas_reports_ui", "Global.gridHeaders.location4")));
		Assert.assertTrue(setToDefaultDashboardGlobalFilter(LanguageCode),"Error occured in setting dashboard to default mode.");

		Assert.assertTrue(toggleVerification(DashboardVariables.REDESIGN, getCredentials(environment, "MSP_ADMIN_US_EMAIl")), "[TC:277168, 277502, 277179]" + DashboardVariables.REDESIGN + "LD-Toggle is disabled for Hide and Show functionaliy");
		LOGGER.info("Verified LD-Toggle status");
		dashboardPage.waitForPageLoaded();
		if(!(dashboardPage.verifyElementsOfDashboardPage("noDataToDisplayWinUpdateCharts"))) {
		// Test case 1 (Validate critical windows updates missing chart is present)
		softAssert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("criticalWindowsUpdateChart"), "critical windows updates missing chart is not present");
		LOGGER.info("critical windows updates missing chart is present");
		// Test case 2 (Validate Message)
		softAssert.assertTrue(dashboardPage.matchTextOfDashboardPage("messageCriticalWindowsUpdateChart", dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "dashboard.devices.action.winmissing")), "Message for critical windows updates missing chart does not match");
		LOGGER.info("critical windows updates missing chart message matched");
		// Test case 3 (Validate device count for out dated driver)
		softAssert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("deviceCountMissingCriticalWindowsUpdate"), "devices count for out dated driver is not match");
		LOGGER.info("Device count for critical missing windows update widget is match");
		// Test case 4 validate details grid and count matching between widget and details
		softAssert.assertTrue(verifyDetailsGridForKPIWidgetsOnDashboard(LanguageCode,"deviceCountMissingCriticalWindowsUpdate","viewDetailsWindowsUpdateChart","winupdate", expectedList),"The count or columns did not matched");
		LOGGER.info("Switched to Previous tab");
		// Test case 5 (Validate filter for critical windows updates)
		dashboardPage.clickOnElementsOfDashboardPage("viewDetailsWindowsUpdateChart");
		dashboardPage.switchToDifferentTabOfDashboardPage();
		dashboardPage.waitForElementsOfDashboardPage("moreDetailsLink");
		dashboardPage.clickOnElementsOfDashboardPage("moreDetailsLink");
		softAssert.assertTrue(dashboardPage.verifyTextContainsOnElementsOfDashboardPage("filterTextCriticalMissingUpdates", dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "global.critical")), "Filter of critical updates missing is not present");
		LOGGER.info("Filter is verified");
		softAssert.assertAll();
		}else {
			LOGGER.info("critical windows updates chart has no data to show");
		}
	}
	
	/**
	 * User Story 661796: [Analytics][UI] User Wants to See in drilldown only the critical updates available for the device
	 * This method validates the Critical Missing Office Update Widget
	 * @throws Exception
	 */
	@Test(priority = 62, groups = { "REGRESSION_CI", "REGRESSIONDASHBOARD1" }, description = "TC:[258896,912125]")
	public final void verifyCriticalOfficeUpdateWidget() throws Exception {
		login("MSP_ADMIN_US_EMAIl_REPORTS", "MSP_ADMIN_US_PASSWORD_DASHBOARD");
		SoftAssert softAssert = new SoftAssert();
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		List<String> expectedList = new ArrayList<String>(Arrays.asList(getTextLanguage(LanguageCode, "daas_reports_ui", "Global.gridHeaders.companyName"), getTextLanguage(LanguageCode, "daas_reports_ui", "gridHeaders.Hardware_Inventory.Details.allData.lastSignedinUser"), getTextLanguage(LanguageCode, "daas_reports_ui", "Global.gridHeaders.deviceName"), getTextLanguage(LanguageCode, "daas_reports_ui", "Global.gridHeaders.serialNumber"),
				getTextLanguage(LanguageCode, "daas_reports_ui", "gridHeaders.details.criticalCount"), getTextLanguage(LanguageCode, "daas_reports_ui", "Global.gridHeaders.deviceType"), getTextLanguage(LanguageCode, "daas_reports_ui", "Global.gridHeaders.deviceManufacturer"), getTextLanguage(LanguageCode, "daas_reports_ui", "Global.gridHeaders.deviceModel"), getTextLanguage(LanguageCode, "daas_reports_ui", "Global.gridHeaders.manufacturerDate"), 
				getTextLanguage(LanguageCode, "daas_reports_ui", "Global.gridHeaders.operatingSystem"), getTextLanguage(LanguageCode, "daas_reports_ui", "gridHeaders.hardware_Inventory.Details.operating_system_release"), getTextLanguage(LanguageCode, "daas_reports_ui", "Global.gridHeaders.lastSeen"), getTextLanguage(LanguageCode, "daas_reports_ui", "gridHeaders.Software_Update.WinUpdate.By_Update.missing_update_criticality"), 
				getTextLanguage(LanguageCode, "daas_reports_ui", "Global.gridHeaders.location1"), getTextLanguage(LanguageCode, "daas_reports_ui", "Global.gridHeaders.location2"), getTextLanguage(LanguageCode, "daas_reports_ui", "Global.gridHeaders.location3"), getTextLanguage(LanguageCode, "daas_reports_ui", "Global.gridHeaders.location4")));
		Assert.assertTrue(setToDefaultDashboardGlobalFilter(LanguageCode),"Error occured in setting dashboard to default mode.");

		Assert.assertTrue(toggleVerification(DashboardVariables.REDESIGN, getCredentials(environment, "MSP_ADMIN_US_EMAIl")), "[TC:277168, 277502, 277179]" + DashboardVariables.REDESIGN + "LD-Toggle is disabled for Hide and Show functionaliy");
		LOGGER.info("Verified LD-Toggle status");
		dashboardPage.waitForPageLoaded();
		if(!(dashboardPage.verifyElementsOfDashboardPage("noDataToDisplayOfficeUpdateCharts"))) {
			// Test case 1 (Validate critical office updates missing chart is present)
			softAssert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("criticalOfficeUpdateChart"), "critical office updates missing chart is not present");
			LOGGER.info("critical office updates missing chart is present");
			// Test case 2 (Validate Message)
			softAssert.assertTrue(dashboardPage.matchTextOfDashboardPage("messageCriticalOfficeUpdateChart", dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "dashboard.devices.action.ofcmissing")), "Message for critical office updates missing chart does not match");
			LOGGER.info("critical office updates missing chart message matched");
			// Test case 3 (Validate device count for out dated driver)
			softAssert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("deviceCountMissingCriticalOfficeUpdate"), "devices count for out dated driver is not match");
			LOGGER.info("Device count for critical missing office update widget is match");
			// Test case 4 validate details grid and count matching between widget and details
			softAssert.assertTrue(verifyDetailsGridForKPIWidgetsOnDashboard(LanguageCode,"deviceCountMissingCriticalOfficeUpdate","viewDetailsOfficeUpdateChart","officeupdate", expectedList),"The count or columns did not matched");
			LOGGER.info("Switched to Previous tab");
			// Test case 5 (Validate filter for critical windows updates)
			dashboardPage.clickOnElementsOfDashboardPage("viewDetailsOfficeUpdateChart");
			dashboardPage.switchToDifferentTabOfDashboardPage();
			dashboardPage.waitForElementsOfDashboardPage("moreDetailsLink");
			dashboardPage.clickOnElementsOfDashboardPage("moreDetailsLink");
			softAssert.assertTrue(dashboardPage.verifyTextContainsOnElementsOfDashboardPage("filterTextCriticalMissingUpdates", dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "global.critical")), "Filter of critical updates missing is not present");
			LOGGER.info("Filter is verified");
			softAssert.assertAll();
		} else {
			LOGGER.info("critical office updates chart has no data to show");
		}
	}
	
	/**
	 * User Story 611859: [CaaS][UI] Create new Dashboard and Report for CaaS Disk replacement
	 * This method validates add and delete widget for CaaS dashboard hardware category.The subcategories to be considered are:
	 * CS Hardware Inventory,  CS Hardware health , CS Disk Replacement
	 * @throws Exception
	 */
	@Test(priority = 61, groups = {},description = "TC:[688232]")
	public final void VerifyAddCustomDashboardAddDeleteWidgetForCaasHardware() throws Exception {
		login("MSP_ADMIN_US_EMAIl_DASHBOARD_CAAS", "MSP_ADMIN_US_PASSWORD_DASHBOARD_CAAS");
		SoftAssert softAssert = new SoftAssert();
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		if (toggleVerification(DashboardVariables.FLEXIDASHBOARD, "MSP_ADMIN_US_EMAIl_DASHBOARD_CAAS")) {
			String hardware = dashboardPage.getTextLanguage(LanguageCode, "dashboard_service", "label.report_type_Conference_Service");
			// List of sub-categories of hardware category for CaaS 
			ArrayList<String> subCategoryList = new ArrayList<String>(Arrays.asList(getTextLanguage(LanguageCode, "dashboard_service", "label.report_category_mrsHwinv"), getTextLanguage(LanguageCode, "dashboard_service", "label.report_category_mrsDiskrep"), getTextLanguage(LanguageCode, "dashboard_service", "label.report_category_mrsHwhealthNew"),getTextLanguage(LanguageCode, "dashboard_service", "label.report_category_mrsDeviceutiV3")));
			dashboardPage.addCustomDashboard("CUSTOM_DASHBOARD_NAME");
			softAssert.assertEquals(dashboardPage.verifyCustomDashboardName("CUSTOM_DASHBOARD_NAME"), true, "Dashboard is not created");
			waitForAnalyticsPageLoaded();
			dashboardPage.addWidgetForCaaSHardware(hardware, subCategoryList, LanguageCode);
			LOGGER.info("Widget has been added successfully");
			sleeper(3000);
			dashboardPage.deleteCustomDashboard("CUSTOM_DASHBOARD_NAME");
			softAssert.assertEquals(dashboardPage.verifyCustomDashboardName("CUSTOM_DASHBOARD_NAME"), false, "Dashboard is not deleted");
			softAssert.assertAll();
		} else {
			LOGGER.info("This is not flexible dashboard");
		}		
	}
	
	/**
	 * User Story 794085: [Daas][Analytics][UI] Add Dashboard Changes for Battery Swell Probability report
	 * This test verifies the Battery Swell Probability for Device Age chart
	*/
	@Test(priority = 64 ,  groups = {"REGRESSIONDASHBOARD1"} , description = "Test Case = [806146]")	
	public final void verifyChartBatterySwellProbabilityChart() throws Exception {
		SoftAssert softAssert = new SoftAssert();		
		login("MSP_ADMIN_US_EMAIl_REPORTS_ALPHA", "MSP_ADMIN_US_PASSWORD_REPORTS");
		
		ArrayList<String> BATTERY_SWELL_PROBABILITY_CHART_LABELS_LIST = new ArrayList<>(Arrays.asList("graphHeaders.Battery_Swell_Probability.Details.battary_age_lessthan1year", "graphHeaders.Battery_Swell_Probability.Details.battary_age_1_2years", "graphHeaders.Battery_Swell_Probability.Details.battary_age_2_3years" , "graphHeaders.Battery_Swell_Probability.Details.battary_age_greaterthan3year", "graphHeaders.Battery_Swell_Probability.Details.undetermined"));
		
		final String[] DASHBOARD_CHART_BATTERY_SWELL_PROBABILITY_REPORT_PAGE = { "Global.gridHeaders.companyName", "gridHeaders.Hardware_Inventory.Details.allData.lastSignedinUser", "Global.gridHeaders.deviceName", "gridHeaders.Battery_Swell_Probability.Details.device_model_number", "gridHeaders.Hardware_GPU_Health.device_manufacture_year" , "Global.gridHeaders.serialNumber","Global.gridHeaders.device_warranty_status", "Global.gridHeaders.device_warranty_end_date" , 
				"gridHeaders.Battery_Replacement.Details.batterySN" , "gridHeaders.Battery_Replacement.Details.ctnumber" , "gridHeaders.Battery_Swell_Probability.Details.current_bhm_setting" , "gridHeaders.Battery_Swell_Probability.Details.date_bhm_settings_applied" , "gridHeaders.Battery_Swell_Probability.Details.recommended_bhm_action" , 
				"gridHeaders.Battery_Swell_Probability.Details.recommended_bios_action" , "gridHeaders.Battery_Swell_Probability.Details.current_bios_version_installed" , "gridHeaders.Battery_Swell_Probability.Details.latest_bios_version_available" , "gridHeaders.Hardware_BIOS.Details.softpaq_number" , "Global.gridHeaders.location_1","Global.gridHeaders.location_2","Global.gridHeaders.location_3","Global.gridHeaders.location_4" };
		
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();

		if (!toggleVerification(DashboardVariables.FLEXIDASHBOARD, getCredentials(environment, "MSP_ADMIN_US_EMAIl_REPORTS_ALPHA"))) {
			// Verify toggle status
			Assert.assertTrue(toggleVerification(DashboardVariables.BATTERYSWELLPROB, "MSP_ADMIN_US_EMAIl_REPORTS_ALPHA"), "LD-Toggle is disabled for Battery Swell Probability chart");
			LOGGER.info("Verified LD-Toggle status");

			// Reset current configuration of dashboard chart
			dashboardPage.clearDashboardCompanyFilter();
			dashboardPage.verifyResetToDefaultButton();
			dashboardPage.waitForPageLoaded();
			dashboardPage.scrollToDashboardPage("batterySwellProbabilityTitle");
			LOGGER.info("Scrolled down to batterySwellProbability chart");

			// Test Case 1 (This Test case validates title of the chart.)
			softAssert.assertTrue(dashboardPage.verifyTitleofChartOfDashboardPage("batterySwellProbabilityTitle", dashboardPage.getTextLanguage(LanguageCode, "dashboard_service", "label.dashborad.global.battery.swell.probability.by.device.age")), "Battery Swell Probability Chart title did not match.");
			LOGGER.info("Verified title of the chart");

			// Test Case 2 (This test case validate whether chart is loaded or not.)
			Assert.assertFalse(dashboardPage.verifyChartisLoadedDynamic(LanguageCode, "batterySwellProbabilityChart", "fatalErrorbatterySwellProbability", 5), "Battery Swell Probability Chart did not load successfully.");
			LOGGER.info("Verified whether chart is loaded or not");

			// Test Case 3 (This test case validate whether chart has any data or not.)
			Assert.assertFalse(dashboardPage.verifyChartsHasNoDataOnDashBoardPage(LanguageCode, "noDatabatterySwellProbabilityChart", "label.dashborad.global.battery.swell.probability.by.device.age"), "Battery Swell Probability Chart does not have any data.");
			Assert.assertFalse(dashboardPage.verifyChartsHasNoDataDevicesEnrolledOnDashBoardPage(LanguageCode, "noDatabatterySwellProbabilityChart", "label.dashborad.global.battery.swell.probability.by.device.age"), "Battery Swell Probability Chart does not have any data.");
			LOGGER.info("Verified whether chart has any data or not");

			// Test Case 4 (This test case validates the text of tool tip present on all sub parts of chart with text on Report page.)
			softAssert.assertTrue(dashboardPage.verifyTooltipTextOnReportWithFrame("labelsLocatorbatterySwellProbability", "tooltipTextbatterySwellProbability", "batterySwellProbabilityTextLocator"), "Tooltip text of Battery Swell Probability chart did match with reports");
			LOGGER.info("Verified text of tool tip present on all sub parts of chart");

			// Test Case 5 (This test case validates the header of reports page.)
			softAssert.assertTrue(dashboardPage.headerTextVerificationOnReportPageFrame(LanguageCode, "labelsLocatorbatterySwellProbability", "headerListLocatorOnReportPage", DASHBOARD_CHART_BATTERY_SWELL_PROBABILITY_REPORT_PAGE, "frameLocator"), "Report header for Battery Swell Probability Chart did not match on report page.");
			LOGGER.info("Verified the header of reports page");

			// Test Case 6 (This test case validates the labels of the chart. )
			softAssert.assertTrue(dashboardPage.verifyChartLabelsDashboardPage("batterySwellProbabilityLegendLabel", dashboardPage.getTextLanguage(LanguageCode, "daas_reports_ui", BATTERY_SWELL_PROBABILITY_CHART_LABELS_LIST)), "Labels of Battery Swell Probability Chart did not match");
			LOGGER.info("Verified the labels of the chart");

			// Test Case 7 (This test case validates no data message on chart.)
			dashboardPage.scrollUpCharts();
			dashboardPage.clickOnElementsOfDashboardPage("companyDropdown");
			softAssert.assertTrue(dashboardPage.verifyCompanyChangeOfDashboardPage(LanguageCode, "companySearchBox", DashboardVariables.DASHBOARD_CHART_COMPANY_NAME_MSP_US, "companyEmptyText", "companyList", "dropdownBoxCompany"), "Company Change is not working correctly");
			softAssert.assertTrue(dashboardPage.verifyChartsHasNoDataOnDashBoardPage(LanguageCode, "noDatabatterySwellProbabilityChart", "label.dashborad.global.battery.swell.probability.by.device.age"), "No data message for Battery Swell Probability Chart is incorrect.");
			dashboardPage.clickOnElementsOfDashboardPage("clearCompany");
			dashboardPage.waitForPageLoaded();
			dashboardPage.scrollDownCharts();
			LOGGER.info("Verified charts with no data present when company with no devices is selected");

			softAssert.assertAll();
			LOGGER.info("Validation of Battery Swell Probability Chart completed successfully");
		} else {
			if (!toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, "MSP_ADMIN_US_EMAIl_REPORTS_ALPHA"))){
				Assert.assertTrue(setToDefaultDashboard(LanguageCode),"Error occured in setting dashboard to default mode.");
			}else{
				Assert.assertTrue(setToDefaultDashboardGlobalFilter(LanguageCode),"Error occured in setting dashboard to default mode.");
			}

			dashboardPage.scrollToDashboardPage("batterySwellProbabilityTitleFlexi");
			LOGGER.info("Scrolled down to Battery Swell Probability chart");

			dashboardPage.waitForPageLoaded();
			
			if(!(dashboardPage.verifyElementsOfDashboardPage("noDatabatterySwellProbabilityChartFlexi"))) {
				
				// Test Case 1 (This Test case validates title of the chart.)
				softAssert.assertTrue(dashboardPage.verifyTitleofChartOfDashboardPage("batterySwellProbabilityTitleFlexi", dashboardPage.getTextLanguage(LanguageCode, "dashboard_service", "label.dashborad.global.battery.swell.probability.by.device.age")), "Battery Swell Probability Chart title did not match.");
				LOGGER.info("Verified title of the chart");
	
				// Test Case 2 (This test case validate whether chart is loaded or not.)
				Assert.assertFalse(dashboardPage.verifyChartisLoadedDynamic(LanguageCode, "batterySwellProbabilityChartFlexi", "fatalErrorBatterySwellProbabilityFlexi", 5), "Battery Swell Probability Chart did not load successfully.");
				LOGGER.info("Verified whether chart is loaded or not");
				
				// Test Case 3 (This test case validate whether chart has any data or not.)
				Assert.assertFalse(dashboardPage.verifyChartsHasNoDataOnDashBoardPage(LanguageCode, "noDatabatterySwellProbabilityChartFlexi", "label.dashborad.global.battery.swell.probability.by.device.age"), "Battery Swell Probability Chart does not have any data.");
				LOGGER.info("Verified whether chart has any data or not");
				
				// Test Case 4 (This test case validates the text of tool tip present on all sub parts of chart with text on Report page.)			
				softAssert.assertTrue(dashboardPage.verifyTooltipTextOnReportWithFrameDonutBatterySwellChartFlexi("batterySwellProbabilityLegendLabelFlexi", "tooltipTextbatterySwellProbabilityFlexi", "batterySwellProbabilityTextLocatorFlexi", "batterySwellProbabilityChartVisibilityFlexi", "batterySwellProbabilityChartVisibilityFlexi","batterySwellProbabilityDropdownFlexi"), "Tooltip text of Battery Swell Probability chart did match with reports");
				LOGGER.info("Verified text of tool tip present on all sub parts of chart");
				
				softAssert.assertTrue(dashboardPage.verifyTooltipCountonReportWithFrameDonutBatterySwellChartFlexiWithOffset("batterySwellProbabilityLegendLabelFlexi", "tooltipcountbatterySwellProbabilityFlexi", "columnListbatterySwellProbability", "batterySwellPaginationReportpage", "frameLocator", "batterySwellProbabilityChartVisibilityFlexi", "batterySwellProbabilityChartVisibilityFlexi",15,80,"batterySwellProbabilityDropdownFlexi"), "Tooltip count of Battery Swell Probability chart did match with reports");
				LOGGER.info("Verified count of tool tip present on all sub parts of chart");
				
				// Test Case 5 (This test case validates the header of reports page.)			
				softAssert.assertTrue(dashboardPage.headerTextVerificationOnReportPageFrameDonutSwellChartFlexi(LanguageCode, "batterySwellProbabilityLegendLabelFlexi", "batterySwellProbabilityheaderListLocatorOnReportPageFlexi", DASHBOARD_CHART_BATTERY_SWELL_PROBABILITY_REPORT_PAGE, "frameLocator", "batterySwellProbabilityChartVisibilityFlexi", "batterySwellProbabilityChartVisibilityFlexi","batterySwellProbabilityDropdownFlexi"), "Report header for Battery Swell Probability chart did not match on report page.");
				LOGGER.info("Verified the header of reports page");
	
				// Test Case 6 (This test case validates the labels of the chart. )
				softAssert.assertTrue(dashboardPage.verifyChartLabelsOnDashboardPage("batterySwellProbabilityLegendLabelFlexi", dashboardPage.getTextLanguage(LanguageCode, "daas_reports_ui", BATTERY_SWELL_PROBABILITY_CHART_LABELS_LIST)), "Labels of Battery Swell Probability Chart did not match");
				LOGGER.info("Verified the labels of the chart");

				// Test Case 7 (This test case validates no data message on chart.)
				dashboardPage.scrollUpCharts();
				if (!toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, "MSP_ADMIN_US_EMAIl_REPORTS_ALPHA"))){
					dashboardPage.clickOnElementsOfDashboardPage("companyDropdownFlexi");
					softAssert.assertTrue(dashboardPage.verifyCompanyChangeOfDashboardPage(LanguageCode, "companySearchBoxFlexi", DashboardVariables.DASHBOARD_CHART_COMPANY_NAME_MSP_US, "companyEmptyText", "companyList", "dropdownBoxCompanyFlexi"), "Company Change is not working correctly");
					softAssert.assertTrue(dashboardPage.verifyChartsHasNoDataOnDashBoardPageFlexi(LanguageCode, "noDatabatterySwellProbabilityChartFlexi", "label.dashborad.global.battery.swell.probability.by.device.age"), "No data message for Battery Swell Probability Chart is incorrect.");
					dashboardPage.clickOnElementsOfDashboardPage("clearCompanyFlexi");
					dashboardPage.waitForPageLoaded();
				}else{				
					dashboardPage.clickOnElementsOfDashboardPage("companyDropdownGlobalFilter");
					softAssert.assertTrue(dashboardPage.verifyCompanyChangeOfDashboardPageGlobalFilter(LanguageCode, "companySearchGlobalFilter", DashboardVariables.DASHBOARD_CHART_COMPANY_NAME_MSP_US, "companyEmptyTextGlobalFilter", "companyListGlobalFilter", "outputTextGlobalFilter","globalFilterSave"), "Company Change is not working correctly");
					softAssert.assertTrue(dashboardPage.verifyChartsHasNoDataOnDashBoardPageFlexi(LanguageCode, "noDatabatterySwellProbabilityChartFlexi", "label.dashborad.global.battery.swell.probability.by.device.age"), "No data message for Battery Swell Probability Chart is incorrect.");				
					dashboardPage.clickOnElementsOfDashboardPage("companyDropdownGlobalFilter");
					dashboardPage.clickOnElementsOfDashboardPage("clearGlobalFilter");
					dashboardPage.clickOnElementsOfDashboardPage("globalFilterSave");
					dashboardPage.scrollDownCharts();
					LOGGER.info("Verified charts with no data present when company with no devices is selected");
				}
				softAssert.assertAll();
				LOGGER.info("Validation of Battery Swell Probability Chart completed successfully");
			} else {
				LOGGER.info("No data on Battery Swell Probability Chart");
			}
		}
	}
	
	/**
	 * Feature 809600: [Analytics] Retire battery and HDD replacements incident widgets from dashboard
	 * @param username
	 * @param password
	 * @throws Exception
	 */
	@Test(priority = 65 ,  groups = {"REGRESSIONDASHBOARD1"}, dataProvider = "getLoginDataForRetireBatteryDiskKpiChartFeature" , description = "Test Case = [847998, 848001]")	
	public final void verifyRetireBatteryDiskKpiChartFeature(String username, String password) throws Exception {
	   login(username, password);
	   DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
	   if (dashboardPage.verifyReadInsightsIncident(environment)) {
		   
		   Assert.assertFalse(dashboardPage.verifyElementsOfDashboardPage("batteryReplcKpiChart"),"Battery Rep KPI chart is present");
		   Assert.assertFalse(dashboardPage.verifyElementsOfDashboardPage("diskReplcKpiChart"),"Disk Rep KPI chart is present");
	   }else {
		   Assert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("batteryReplcKpiChart"),"Battery Rep KPI chart is not present");
		   Assert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("diskReplcKpiChart"),"Disk Rep KPI chart is not present");
	   }
	}

	/**
	 * User Story 842139: [Analytics][UI] Show as of date tooltip icon on toggle
	 * This test verifies tool tip icon on disk capacity chart
	*/
	@Test(priority = 66 ,  groups = {"REGRESSIONDASHBOARD2"} , description = "Test Case = [862636]")
	public final void verifyInformationIconForTimestamp() throws Exception {
		SoftAssert softAssert = new SoftAssert();

		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
 
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();

		if (!toggleVerification(DashboardVariables.REMOVE_TIMESTAMP_TOGGLE, getCredentials(environment, "MSP_ADMIN_NEW_UI_EMAIL"))){

			//Reset dashboard to dafault mode
			Assert.assertTrue(setToDefaultDashboardGlobalFilter(LanguageCode),"Error occured in setting dashboard to default mode.");
			dashboardPage.waitForPageLoaded();
			dashboardPage.scrollToDashboardPage("diskCapacityChart");
			LOGGER.info("Scrolled down to Disk capacity chart");

			//Verify information icon on chart
			Assert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("informationIcon"), "Information icon not present on widget, cannot proceed further");
			dashboardPage.mouseHoverOfDashboardPage("informationIcon");
			String dataUpdatedDateOnTooltip = dashboardPage.getTextOfDashboardPage("dataUpdatedDateTooltipText");

			//Get data updated date from api
			String dateApi = getDataFromApiPost("dataAsOfDate",getEnvironmentSpecificData(System.getProperty("environment"), "DAAS_API_BASE_URL_AUTH")+ ConstantURL.DISK_CAPACITY_URL, diskCapacityBody, 0);

			softAssert.assertTrue(dataUpdatedDateOnTooltip.equals(getTextLanguage(LanguageCode, "daas_reports_ui", "Global.label.data_updated_at").replace("{dataAsOfDate}", dateApi)), "Data updated date did not match");
			softAssert.assertAll();
			LOGGER.info("Validation of tooltip to show timestamp completed successfully");
		} else {
			LOGGER.info("widget-layout-changes toggle is not enabled for logged in user");
		}
	}

	/**
	 * User Story 876033: [Analytics][UI] Win11FleetReadiness - Implement UI for Windows 11 Upgrade status KPI
	 * This test verifies the windows 11 Upgrade KPI
	*/
	@Test(priority = 63 ,  groups = {"REGRESSIONDASHBOARD2"},enabled = false  )
	public final void verifyWindows11UpgradeStatusWidget() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();

			if (!toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, "MSP_ADMIN_US_EMAIl"))){
				Assert.assertTrue(setToDefaultDashboard(LanguageCode),"Error occured in setting dashboard to default mode.");
			}else{
				Assert.assertTrue(setToDefaultDashboardGlobalFilter(LanguageCode),"Error occured in setting dashboard to default mode.");
			}

			dashboardPage.scrollToDashboardPage("windows11UpgradeStatusTitle");
			LOGGER.info("Scrolled down to Windows11 Upgrade status chart");
			dashboardPage.waitForPageLoaded();
			softAssert.assertTrue(dashboardPage.verifyTitleofChartOfDashboardPage("windows11UpgradeStatusTitle", dashboardPage.getTextLanguage(LanguageCode, "dashboard_service", "label.dashborad.global.win11FleetReadiness.win11UpgradeStatusKpi")), "Windows 11 upgrade status title dod not match.");

			//Test case 1 : Verify updated to Windows11 title
			dashboardPage.waitForElementsOfDashboardPage("upgradeToWin11Title");
			softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("upgradeToWin11Title").equals(getTextLanguage(LanguageCode, "daas_reports_ui", "hardware.win11.compatibility.updated_to_windows_11")), "Upgrade to Windows 11 title does not match.");
			dashboardPage.clickByJavaScriptOnDashboardPage("upgradeToWin11Card");
			dashboardPage.switchToDifferentTabOfDashboardPage();
			dashboardPage.waitForElementsOfDashboardPage("moreDetailsLink");
			dashboardPage.clickOnElementsOfDashboardPage("moreDetailsLink");
			softAssert.assertTrue(dashboardPage.verifyTextContainsOnElementsOfDashboardPage("filterTextWindows11UpgradeStatus", dashboardPage.getTextLanguage(LanguageCode, "daas_reports_ui", "hardware.win11.compatibility.already_on_win_11_new")), "Filter of upgrade to windows 11 is not present");

			//Test case 2 : Verify Device not updated to windows 11 title
			dashboardPage.switchBackToPreviousTab();
			dashboardPage.waitForPageLoaded();
			dashboardPage.waitForElementsOfDashboardPage("notUpdatedToWin11Title");
			softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("notUpdatedToWin11Title").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_reports_ui", "hardware.win11.compatibility.compatible_devices_not_updated_to_windows_11")), "Devices not update to Windows 11 title does not match.");
			dashboardPage.clickByJavaScriptOnDashboardPage("notUpdatedToWin11Card");
			dashboardPage.switchToDifferentTabOfDashboardPage();
			dashboardPage.waitForElementsOfDashboardPage("moreDetailsLink");
			dashboardPage.clickOnElementsOfDashboardPage("moreDetailsLink");
			softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("filterTextWindows11UpgradeStatus").toLowerCase().contains(dashboardPage.getTextLanguage(LanguageCode, "daas_reports_ui", "hardware.win11.top5device.upgrade_compatible").toLowerCase()), "Filter of device not updated is not present");
			softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("filterTextWindows11UpgradeStatus").toLowerCase().contains(dashboardPage.getTextLanguage(LanguageCode, "daas_reports_ui", "hardware.win11.compatibility.compatible_with_performance_risk").toLowerCase()), "Filter of device not updated is not present");
			//Test case 3 : Verify Not compatible with win 11 title
			dashboardPage.switchBackToPreviousTab();
			dashboardPage.waitForPageLoaded();
			dashboardPage.waitForElementsOfDashboardPage("notCompatiblWithWin11Title");
			softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("notCompatiblWithWin11Title").equals(getTextLanguage(LanguageCode, "daas_reports_ui", "hardware.win11.compatibility.not_compatible_with_windows_11")), "Not compatible to Windows 11 title does not match.");
			dashboardPage.clickByJavaScriptOnDashboardPage("notCompatiblWithWin11Card");
			dashboardPage.switchToDifferentTabOfDashboardPage();
			dashboardPage.waitForElementsOfDashboardPage("moreDetailsLink");
			dashboardPage.clickOnElementsOfDashboardPage("moreDetailsLink");
			softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("filterTextWindows11UpgradeStatus").toLowerCase().contains(dashboardPage.getTextLanguage(LanguageCode, "daas_reports_ui", "hardware.win11.compatibility.not_compatible").toLowerCase()), "Filter of not compatible on windows 11 is not present");

			//Test case 4 : Verify missing information title
			dashboardPage.switchBackToPreviousTab();
			dashboardPage.waitForPageLoaded();
			dashboardPage.waitForElementsOfDashboardPage("missingInformationTitle");
			softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("missingInformationTitle").equals(getTextLanguage(LanguageCode, "daas_reports_ui", "hardware.win11.compatibility.missing_information")), "Missing information title does not match.");
			dashboardPage.clickByJavaScriptOnDashboardPage("missingInformationCard");
			dashboardPage.switchToDifferentTabOfDashboardPage();
			dashboardPage.waitForElementsOfDashboardPage("moreDetailsLink");
			dashboardPage.clickOnElementsOfDashboardPage("moreDetailsLink");
			softAssert.assertTrue(dashboardPage.verifyTextContainsOnElementsOfDashboardPage("filterTextWindows11UpgradeStatus", dashboardPage.getTextLanguage(LanguageCode, "daas_reports_ui", "hardware.win11.compatibility.missing_information")), "Filter of missing information is not present");

			softAssert.assertAll();

			LOGGER.info("Test case verifyWindows11UpgradeStatusWidget passed successfully.");
	}

	/**
	 * User Story 876044: [Analytics][UI] Win11FleetReadiness - Implement UI for Windows 11 Compatibility
	 * This test verifies the windows 11 device compatibility chart
	*/
	@Test(priority = 64 ,  groups = {"REGRESSIONDASHBOARD2"},enabled = false  )
	public final void verifyWindows11DeviceCompatibilityChart() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login("ITA_COMPANY_DETAILS_EMAIL", "ITA_COMPANY_DETAILS_PASSWORD");

		ArrayList<String> WINDOWS_11_DEVICE_COMPATIBILITY_CHART_LABELS_LIST = new ArrayList<>(Arrays.asList("hardware.win11.top5device.upgrade_compatible", "hardware.win11.compatibility.not_compatible", "hardware.win11.compatibility.missing_information" , "hardware.win11.compatibility.compatible_with_performance_risk", "hardware.win11.compatibility.already_on_win_11"));

		final String[] DASHBOARD_CHART_WINDOWS_11_DEVICE_COMPATIBILITY_REPORT_PAGE = { "Global.gridHeaders.companyName", "gridHeaders.Hardware_Inventory.Details.allData.lastSignedinUser", "Global.gridHeaders.deviceName", "Global.gridHeaders.serialNumber","graphHeaders.Hardware_Inventory.MonthlySummary.title.device_type", "device_manufacturer","device_age", "Global.gridHeaders.deviceModel",
				"hardware.win11.deviceos","gridHeaders.Software_Update.WinUpdate.Details.OS_release","hardware.win11.compatibilityStatus","gridHeaders.Hardware_Replacement.Details.recommendation", "gridHeaders.Windows_Migration.details.cpu","gridHeaders.Windows_Migration.details.cpuspeed","hardware.win11.cpu_cores","hardware.win11.hdType","hardware.win11.hdSize","Global.gridHeaders.freespace","hardware.win11.installedMemory","hardware.win11.installedGraphics",
				"hardware.win11.currentTpm","Global.graphHeaders.secureboot","gridHeaders.Incident_Widget.Details.warStatus","gridHeaders.Windows_Migration.warrantyExpirationDate","gridHeaders.Windows_Migration.details.devicePerfomanceRating","gridHeaders.User_Mobility.Details.performanceMetrics", "hardware.win11.performanceDetails","Global.gridHeaders.lastSeen",
				"Global.gridHeaders.location_1","Global.gridHeaders.location_2","Global.gridHeaders.location_3","Global.gridHeaders.location_4" };

		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();

			if (!toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, "ITA_COMPANY_DETAILS_EMAIL"))){
				Assert.assertTrue(setToDefaultDashboard(LanguageCode),"Error occured in setting dashboard to default mode.");
			}else{
				Assert.assertTrue(setToDefaultDashboardGlobalFilter(LanguageCode),"Error occured in setting dashboard to default mode.");
			}

			dashboardPage.scrollToDashboardPage("windows11DeviceCompatibilityTitle");
			LOGGER.info("Scrolled down to Windows11 device compatibility chart");

			dashboardPage.waitForPageLoaded();

			if(!(dashboardPage.verifyElementsOfDashboardPage("noDatawindows11DeviceCompantibilityChart"))) {
				//will uncomment this validation once the string gets available in maestro
				// Test Case 1 (This Test case validates title of the chart.)
				//softAssert.assertTrue(dashboardPage.verifyTitleofChartOfDashboardPage("windows11DeviceCompatibilityTitle", dashboardPage.getTextLanguage(LanguageCode, "dashboard_service", "label.dashborad.global.battery.swell.probability.by.device.age")), "Windows 11 Device compatibility chart title did not match.");
				LOGGER.info("Verified title of the chart");

				// Test Case 2 (This test case validate whether chart is loaded or not.)
				Assert.assertFalse(dashboardPage.verifyChartisLoadedDynamic(LanguageCode, "windows11DeviceCompatibilityChart", "fatalErrorWindows11DeviceCompatibilityChart", 5), "Windows 11 device compatibility Chart did not load successfully.");
				LOGGER.info("Verified whether chart is loaded or not");

				// Test Case 3 (This test case validate whether chart has any data or not.)
				Assert.assertFalse(dashboardPage.verifyChartsHasNoDataOnDashBoardPage(LanguageCode, "noDataWindows11DeviceCompatibilityChart", "dashboard.charts.nodata.nothing_going_on_here"), "Windows 11 device compatibility Chart does not have any data.");
				LOGGER.info("Verified whether chart has any data or not");

				// Test Case 4 (This test case validates the text of tool tip present on all sub parts of chart with text on Report page.)			
				softAssert.assertTrue(dashboardPage.verifyTooltipTextOnReportWithFrameDonutBatterySwellChartFlexi("windows11DeviceCompatibilityLegendLabel", "tooltipTextWindows11DeviceCompatibility", "windows11DeviceCompatibilityTextLocator", "windows11DeviceCompatibilityChartVisibility", "windows11DeviceCompatibilityChartVisibility","windows11DeviceCompatibilityDropdown"), "Tooltip text of windows 11 device compatibility chart did match with reports");
				LOGGER.info("Verified text of tool tip present on all sub parts of chart");

				softAssert.assertTrue(dashboardPage.verifyTooltipCountonReportWithFrameDonutBatterySwellChartFlexiWithOffset("windows11DeviceCompatibilityLegendLabel", "tooltipCounttWindows11DeviceCompatibilityChart", "columnListwindows11DeviceCompatibility", "windows11DeviceCompatibilityPaginationReportpage", "frameLocator", "windows11DeviceCompatibilityChartVisibility", "windows11DeviceCompatibilityChartVisibility",15,80,"windows11DeviceCompatibilityDropdown"), "Tooltip count of windows 11 device compatibility chart did match with reports");
				LOGGER.info("Verified count of tool tip present on all sub parts of chart");

				// Test Case 5 (This test case validates the header of reports page.)
				softAssert.assertTrue(dashboardPage.headerTextVerificationOnReportPageFrameDonutSwellChartFlexi(LanguageCode, "windows11DeviceCompatibilityLegendLabel", "windows11DeviceCompatibilityheaderListLocatorOnReportPage", DASHBOARD_CHART_WINDOWS_11_DEVICE_COMPATIBILITY_REPORT_PAGE, "frameLocator", "windows11DeviceCompatibilityChartVisibility", "windows11DeviceCompatibilityChartVisibility","windows11DeviceCompatibilityDropdown"), "Report header for windows 11 device compatibility chart did not match on report page.");
				LOGGER.info("Verified the header of reports page");

				// Test Case 6 (This test case validates the labels of the chart. )
				softAssert.assertTrue(dashboardPage.verifyChartLabelsOnDashboardPage("windows11DeviceCompatibilityLegendLabel", dashboardPage.getTextLanguage(LanguageCode, "daas_reports_ui", WINDOWS_11_DEVICE_COMPATIBILITY_CHART_LABELS_LIST)), "Labels of windows 11 device compatibility Chart did not match");
				LOGGER.info("Verified the labels of the chart");

				// Test Case 7 (This test case validates no data message on chart.)
				dashboardPage.scrollUpCharts();
				if (!toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, "WINDOWS_11_DEVICE_COMPATIBILITY_VERIFICATION_USER"))){
					dashboardPage.clickOnElementsOfDashboardPage("companyDropdownFlexi");
					softAssert.assertTrue(dashboardPage.verifyCompanyChangeOfDashboardPage(LanguageCode, "companySearchBoxFlexi", getEnvironmentSpecificData(System.getProperty("environment"), "NO_DEVICE_COMPANY"), "companyEmptyText", "companyList", "dropdownBoxCompanyFlexi"), "Company Change is not working correctly");
					softAssert.assertTrue(dashboardPage.verifyChartsHasNoDataOnDashBoardPageFlexi(LanguageCode, "noDataWindows11DeviceCompatibilityChart", "flexibledashboard.charts.nodata.nothing_going_on_here"), "No data message for Windows 11 device compatibility chart is incorrect.");
					dashboardPage.clickOnElementsOfDashboardPage("clearCompanyFlexi");
					dashboardPage.waitForPageLoaded();
				} else {
					dashboardPage.clickOnElementsOfDashboardPage("companyDropdownGlobalFilter");
					softAssert.assertTrue(dashboardPage.verifyCompanyChangeOfDashboardPageGlobalFilter(LanguageCode, "companySearchGlobalFilter", getEnvironmentSpecificData(System.getProperty("environment"), "NO_DEVICE_COMPANY"), "companyEmptyTextGlobalFilter", "companyListGlobalFilter", "outputTextGlobalFilter","globalFilterSave"), "Company Change is not working correctly");
					dashboardPage.scrollToDashboardPage("windows11DeviceCompatibilityTitle");
					softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("noDataWindows11DeviceCompatibilityChart").equals(getTextLanguage(LanguageCode, "daas_reports_ui", "flexibledashboard.charts.nodata.nothing_going_on_here")), "No data message for Windows 11 device compatibility chart is incorrect");				
					dashboardPage.clickOnElementsOfDashboardPage("companyDropdownGlobalFilter");
					dashboardPage.clickOnElementsOfDashboardPage("clearGlobalFilter");
					dashboardPage.clickOnElementsOfDashboardPage("globalFilterSave");
					dashboardPage.scrollDownCharts();
					LOGGER.info("Verified charts with no data present when company with no devices is selected");
				}
				softAssert.assertAll();
				LOGGER.info("Test case verifyWindows11DeviceCompatibilityChart passed successfully");
			} else {
				LOGGER.info("No data present on windows 11 device compatibility Chart");
			}

	}
	
	/**
	 * This test case validates the context sensitive help links on Dashboard screen
	 *
	 * @throws Exception
	 */
	@Test(priority = 65, groups = { "REGRESSIONSETTINGS2", "SMOKE", "REGRESSION_CI",
	"SMOKE_CI" }, description = "Test Case ID : 891080")
	public final void verifyOnlineHelpLinksDashboard() throws Exception {
		login("IT_ADMIN_HELP_AND_SUPPORT_EMAIL", "IT_ADMIN_HELP_AND_SUPPORT_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();

		// Verify help and support link for dashboard tab
		waitForPageLoaded();
		LOGGER.info("Redirected to Dashboard tab");
		settingsPage.waitForElementsOfSettingsPage("helpAndSupportIcon");
		settingsPage.clickOnElementsOfSettingsPage("helpAndSupportIcon");
		softAssert.assertTrue(
				settingsPage.matchTextOfSettingsPage("help&SupportLink",
						getTextLanguage(LanguageCode, "daas_ui", "settings.help_and_support.title")),
				"Help & Support link text did not match on dashboard tab");
		settingsPage.clickOnElementsOfSettingsPage("help&SupportLink");
		LOGGER.info("Clicked on Help & Support link from Dashboard tab");
		waitForPageLoaded();
		softAssert.assertTrue(settingsPage.getTextOfSettingsPage("helpAndSupportTitleTextOnBreadcrumbs").equals(settingsPage.getTextLanguage(LanguageCode, "daas_ui", "sidemenu.support")),
				"User not redirected to Help & Support tab after clicking on Help & Support link from Dashboard");
		gotoDashboardTab();

		// Verify overview link for dashboard tab
		waitForPageLoaded();
		LOGGER.info("Redirected to Dashboard tab");
		settingsPage.waitForElementsOfSettingsPage("helpAndSupportIcon");
		settingsPage.clickOnElementsOfSettingsPage("helpAndSupportIcon");
		softAssert.assertTrue(
				settingsPage.matchTextOfSettingsPage("dashboardOverviewLink",
						getTextLanguage(LanguageCode, "daas_ui", "pagetitle.dashboard.overview")),
				"Dashboard overview link text did not match on dashboard tab");
		settingsPage.clickOnElementsOfSettingsPage("dashboardOverviewLink");
		LOGGER.info("Clicked on overview link from Dashboard tab");
		settingsPage.switchToDifferentTab();
		sleeper(4000);// Url takes time to load
		softAssert.assertTrue(settingsPage.getUrlOfCurrentPage().contains(ConstantURL.KNOWLEDGE_BASE),
				"User not redirected to Online help section after clicking on overview link from Dashboard");
		settingsPage.switchBackToPreviousTab();

		// Verify Custom Dashboards and Widgets link for dashboard tab
		waitForPageLoaded();
		LOGGER.info("Redirected to Dashboard tab");
		settingsPage.waitForElementsOfSettingsPage("helpAndSupportIcon");
		settingsPage.clickOnElementsOfSettingsPage("helpAndSupportIcon");
		softAssert.assertTrue(
				settingsPage.matchTextOfSettingsPage("customDashboardLink",
						getTextLanguage(LanguageCode, "daas_ui", "pagetitle.dashboard.widgets")),
				"Custom Dashboards and Widgets link text did not match on dashboard tab");
		settingsPage.clickOnElementsOfSettingsPage("customDashboardLink");
		LOGGER.info("Clicked on Custom Dashboards and Widgets link from Dashboard tab");
		settingsPage.switchToDifferentTab();
		sleeper(4000);// Url takes time to load
		softAssert.assertTrue(settingsPage.getUrlOfCurrentPage().contains(ConstantURL.KNOWLEDGE_BASE),
				"User not redirected to Online help section after clicking on Custom Dashboards and Widgets link from Dashboard");
		softAssert.assertTrue(settingsPage.getUrlOfCurrentPage().contains(ConstantURL.LANGUAGE_PARAMETER),
				"Language parameter not added in Service now URL");
		settingsPage.switchBackToPreviousTab();

		softAssert.assertAll();
		LOGGER.info("Test case to verify context sensitive links on dashboard passed successfully.");
	}
	
	/**
	 * User Story 903097: User Story 892125: [Analytics] [AnalyticsUI] Update UI to show count column in report - Windows 11
	 * This test verifies the windows 11 Upgrade KPI
	 */
	@Test(priority = 66 ,  groups = {"REGRESSIONDASHBOARD2"},enabled = false  )
	public final void verifyWindows11UpgradeStatusWidgetForMatchWidgetandDetailsCount() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");

		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		List<String> expectedList = new ArrayList<String>(Arrays.asList(getTextLanguage(LanguageCode, "daas_reports_ui", "Global.gridHeaders.companyName"), getTextLanguage(LanguageCode, "daas_reports_ui", "gridHeaders.Hardware_Inventory.Details.allData.lastSignedinUser"), getTextLanguage(LanguageCode, "daas_reports_ui", "Global.gridHeaders.deviceName"), getTextLanguage(LanguageCode, "daas_reports_ui", "Global.gridHeaders.serialNumber"),
				getTextLanguage(LanguageCode, "daas_reports_ui", "gridHeaders.details.recommendationCount"), getTextLanguage(LanguageCode, "daas_reports_ui", "Global.gridHeaders.deviceType"), getTextLanguage(LanguageCode, "daas_reports_ui", "Global.gridHeaders.deviceManufacturer"),getTextLanguage(LanguageCode, "daas_reports_ui", "device_age") ,getTextLanguage(LanguageCode, "daas_reports_ui", "Global.gridHeaders.deviceModel"), getTextLanguage(LanguageCode, "daas_reports_ui", "hardware.win11.deviceos"),
				getTextLanguage(LanguageCode, "daas_reports_ui", "gridHeaders.Software_Update.WinUpdate.Details.OS_release"), getTextLanguage(LanguageCode, "daas_reports_ui", "hardware.win11.compatibilityStatus"),getTextLanguage(LanguageCode, "daas_reports_ui","gridHeaders.Hardware_Replacement.Details.recommendation"),getTextLanguage(LanguageCode, "daas_reports_ui", "gridHeaders.Windows_Migration.details.cpu"), getTextLanguage(LanguageCode, "daas_reports_ui", "gridHeaders.Windows_Migration.details.cpuspeedmhz"),
				getTextLanguage(LanguageCode, "daas_reports_ui", "hardware.win11.cpu_cores"), getTextLanguage(LanguageCode, "daas_reports_ui", "hardware.win11.hdType"), getTextLanguage(LanguageCode, "daas_reports_ui", "hardware.win11.hdSizegb"), getTextLanguage(LanguageCode, "daas_reports_ui", "Global.gridHeaders.freespace"), getTextLanguage(LanguageCode, "daas_reports_ui", "hardware.win11.installedMemory"), 
				getTextLanguage(LanguageCode, "daas_reports_ui", "hardware.win11.installedGraphics"), getTextLanguage(LanguageCode, "daas_reports_ui", "hardware.win11.currentTpm"), getTextLanguage(LanguageCode, "daas_reports_ui", "Global.graphHeaders.secureboot"), getTextLanguage(LanguageCode, "daas_reports_ui", "gridHeaders.Incident_Widget.Details.warStatus"), 
				getTextLanguage(LanguageCode, "daas_reports_ui", "gridHeaders.Windows_Migration.warrantyExpirationDate"), getTextLanguage(LanguageCode, "daas_reports_ui", "gridHeaders.Windows_Migration.details.devicePerfomanceRating"), getTextLanguage(LanguageCode, "daas_reports_ui", "gridHeaders.Hardware_Health.MonythlySummary.performance_metrics"), getTextLanguage(LanguageCode, "daas_reports_ui", "hardware.win11.performanceDetails"), getTextLanguage(LanguageCode, "daas_reports_ui", "Global.gridHeaders.lastSeen"), 
				getTextLanguage(LanguageCode, "daas_reports_ui", "Global.gridHeaders.location1"), getTextLanguage(LanguageCode, "daas_reports_ui", "Global.gridHeaders.location2"), getTextLanguage(LanguageCode, "daas_reports_ui", "Global.gridHeaders.location3"), getTextLanguage(LanguageCode, "daas_reports_ui", "Global.gridHeaders.location4")));

		if (!toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, "MSP_ADMIN_US_EMAIl"))){
			Assert.assertTrue(setToDefaultDashboard(LanguageCode),"Error occured in setting dashboard to default mode.");
		}else{
			Assert.assertTrue(setToDefaultDashboardGlobalFilter(LanguageCode),"Error occured in setting dashboard to default mode.");
		}

		dashboardPage.scrollToDashboardPage("windows11UpgradeStatusTitle");
		LOGGER.info("Scrolled down to Windows11 Upgrade status chart");
		dashboardPage.waitForPageLoaded();
		softAssert.assertTrue(dashboardPage.verifyTitleofChartOfDashboardPage("windows11UpgradeStatusTitle", dashboardPage.getTextLanguage(LanguageCode, "dashboard_service", "label.dashborad.global.win11FleetReadiness.win11UpgradeStatusKpi")), "Windows 11 upgrade status title dod not match.");

		//Test case 1 : Verify updated to Windows11 title
		dashboardPage.waitForElementsOfDashboardPage("upgradeToWin11Title");
		softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("upgradeToWin11Title").equals(getTextLanguage(LanguageCode, "daas_reports_ui", "hardware.win11.compatibility.updated_to_windows_11")), "Upgrade to Windows 11 title does not match.");
		dashboardPage.clickByJavaScriptOnDashboardPage("upgradeToWin11Card");
		dashboardPage.switchToDifferentTabOfDashboardPage();
		dashboardPage.waitForElementsOfDashboardPage("moreDetailsLink");
		dashboardPage.clickOnElementsOfDashboardPage("moreDetailsLink");
		softAssert.assertTrue(dashboardPage.verifyTextContainsOnElementsOfDashboardPage("filterTextWindows11UpgradeStatus", dashboardPage.getTextLanguage(LanguageCode, "daas_reports_ui", "hardware.win11.compatibility.already_on_win_11_new")), "Filter of upgrade to windows 11 is not present");
		dashboardPage.switchBackToPreviousTab();
		softAssert.assertTrue(verifyDetailsGridForKPIWidgetsOnDashboard(LanguageCode,"deviceCountUpdatedToWin11","upgradeToWin11Card","win11",expectedList),"The count or columns did not matched");
		LOGGER.info("Switched to Previous tab");

		//Test case 2 : Verify Device not updated to windows 11 title
		dashboardPage.waitForPageLoaded();
		dashboardPage.waitForElementsOfDashboardPage("notUpdatedToWin11Title");
		softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("notUpdatedToWin11Title").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_reports_ui", "hardware.win11.compatibility.compatible_devices_not_updated_to_windows_11")), "Devices not update to Windows 11 title does not match.");
		dashboardPage.clickByJavaScriptOnDashboardPage("notUpdatedToWin11Card");
		dashboardPage.switchToDifferentTabOfDashboardPage();
		dashboardPage.waitForElementsOfDashboardPage("moreDetailsLink");
		dashboardPage.clickOnElementsOfDashboardPage("moreDetailsLink");
		softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("filterTextWindows11UpgradeStatus").toLowerCase().contains(dashboardPage.getTextLanguage(LanguageCode, "daas_reports_ui", "hardware.win11.compatibility.upgrade_compatible").toLowerCase()), "Filter of device not updated is not present");
		softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("filterTextWindows11UpgradeStatus").toLowerCase().contains(dashboardPage.getTextLanguage(LanguageCode, "daas_reports_ui", "hardware.win11.compatibility.compatible_with_performance_risk").toLowerCase()), "Filter of device not updated is not present");
		dashboardPage.switchBackToPreviousTab();
		softAssert.assertTrue(verifyDetailsGridForKPIWidgetsOnDashboard(LanguageCode,"deviceCountDeviceNotUpdatedToWin11","notUpdatedToWin11Card","win11",expectedList),"The count or columns did not matched");
		LOGGER.info("Switched to Previous tab");

		//Test case 3 : Verify Not compatible with win 11 title
		dashboardPage.waitForPageLoaded();
		dashboardPage.waitForElementsOfDashboardPage("notCompatiblWithWin11Title");
		softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("notCompatiblWithWin11Title").equals(getTextLanguage(LanguageCode, "daas_reports_ui", "hardware.win11.compatibility.not_compatible_with_windows_11")), "Not compatible to Windows 11 title does not match.");
		dashboardPage.clickByJavaScriptOnDashboardPage("notCompatiblWithWin11Card");
		dashboardPage.switchToDifferentTabOfDashboardPage();
		dashboardPage.waitForElementsOfDashboardPage("moreDetailsLink");
		dashboardPage.clickOnElementsOfDashboardPage("moreDetailsLink");
		softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("filterTextWindows11UpgradeStatus").toLowerCase().contains(dashboardPage.getTextLanguage(LanguageCode, "daas_reports_ui", "hardware.win11.compatibility.not_compatible").toLowerCase()), "Filter of not compatible on windows 11 is not present");
		dashboardPage.switchBackToPreviousTab();
		softAssert.assertTrue(verifyDetailsGridForKPIWidgetsOnDashboard(LanguageCode,"deviceCountDeviceNotCompatibleToWin11","notCompatiblWithWin11Card","win11",expectedList),"The count or columns did not matched");
		LOGGER.info("Switched to Previous tab");

		//Test case 4 : Verify missing information title
		dashboardPage.waitForPageLoaded();
		dashboardPage.waitForElementsOfDashboardPage("missingInformationTitle");
		softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("missingInformationTitle").equals(getTextLanguage(LanguageCode, "daas_reports_ui", "hardware.win11.compatibility.missing_information")), "Missing information title does not match.");
		dashboardPage.clickByJavaScriptOnDashboardPage("missingInformationCard");
		dashboardPage.switchToDifferentTabOfDashboardPage();
		dashboardPage.waitForElementsOfDashboardPage("moreDetailsLink");
		dashboardPage.clickOnElementsOfDashboardPage("moreDetailsLink");
		softAssert.assertTrue(dashboardPage.verifyTextContainsOnElementsOfDashboardPage("filterTextWindows11UpgradeStatus", dashboardPage.getTextLanguage(LanguageCode, "daas_reports_ui", "hardware.win11.compatibility.missing_information")), "Filter of missing information is not present");
		dashboardPage.switchBackToPreviousTab();
		softAssert.assertTrue(verifyDetailsGridForKPIWidgetsOnDashboard(LanguageCode,"deviceCountMissingInformationWin11","missingInformationCard","win11",expectedList),"The count or columns did not matched");
		LOGGER.info("Switched to Previous tab");
		softAssert.assertAll();

		LOGGER.info("Test case verifyWindows11UpgradeStatusWidgetForMatchWidgetandDetailsCount passed successfully.");
	}
	
	@Test(priority = 67, enabled = true, groups = { "REGRESSIONDASHBOARD2", "SMOKE", "REGRESSION_CI","SMOKE_CI" }, description = "Test Case ID : 838914")
	public final void verifyMSPdigitalExpDashboard() throws Exception {
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		if (!toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, "MSP_NEW_USER_EMAIL_FLEETCHART_US"))){
			Assert.assertTrue(setToDefaultDashboard(LanguageCode),"Error occured in setting dashboard to default mode.");
		}else{
			Assert.assertTrue(setToDefaultDashboardGlobalFilter(LanguageCode),"Error occured in setting dashboard to default mode.");
		}
		 
		        // Test Case 1 - Verify DES chart functionality for location filter ALL.
				softAssert.assertTrue(dashboardPage.verifyTitleofChartOfDashboardPage("digitalExperienceChartTitle", dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "des_dashboard_widget_title")), "Digital Experience chart title did not match.");
				softAssert.assertTrue(dashboardPage.verifyTextContainsOnElementsOfDashboardPage("digitalExperienceChartNoData", dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "digital.exp.score.dashboard.no.data")), "Digital Experience chart .");
				waitForPageLoaded();
				
				// Test Case 2 (This test case validate whether DES chart has any data or not.)
				Assert.assertFalse(dashboardPage.verifyChartisLoadedDynamic(LanguageCode,"noDeviceHealthData", "fatalErrorDeviceHealthData", 5),"Digital Experience chart did not load successfully.");
				LOGGER.info("Verified whether DES chart has any data or not");
				waitForPageLoaded();

		//dashboardPage.clickOnElementsOfDashboardPage("recommandedActionButton");
		waitForPageLoaded();
		//waitForPageLoaded();
	    dashboardPage.clickByJavaScriptOnDashboardPage("globalLocationFilterDropDown");
	    softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("filterMenuCompaniesHeader").equalsIgnoreCase(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "dashboard.actions.filter.company.label")), "Location filter header does not match");
		dashboardPage.enterTextForDashboardPage("filterMenuCompaniesSearch", globalFilterCompanyFleetChart);
		LOGGER.info("Search company to set location filter");
		waitForPageLoaded();
		dashboardPage.clickOnElementsOfDashboardPage("cancelbutton");
		dashboardPage.waitForElementsOfDashboardPage("companyOnListSearch");
		softAssert.assertTrue(dashboardPage.matchTextOfDashboardPage("companyOnListSearch", globalFilterCompanyFleetChart), "Company name and search result company name does not match");
		if (dashboardPage.verifyElementsOfDashboardPage("companyOnListSearch"))
			dashboardPage.clickOnElementsOfDashboardPage("companyOnListSearch");
		LOGGER.info("Select company to set location filter");
		sleeper(2000);

		dashboardPage.clickOnElementsOfDashboardPage("globalFilterSave");
		LOGGER.info("Global Location Filter Saved successfully.");
		waitForPageLoaded();
		if(dashboardPage.verifyElementsOfDashboardPage("digitalExperienceChartTitle")) {
			 sleeper(2000);
				softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("benchmarkmessage").equalsIgnoreCase(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "digital.exp.score.guage.benchmark.note")), "Benchmark message title did not match.");
				softAssert.assertTrue(dashboardPage.verifyElementIsVisible("benchmarktag"));
			softAssert.assertTrue(dashboardPage.verifyElementIsPresent("poorscorescale"));
			softAssert.assertTrue(dashboardPage.verifyElementIsPresent("fairscorescale"));
			softAssert.assertTrue(dashboardPage.verifyElementIsPresent("excellentscorescale"));
			softAssert.assertTrue(dashboardPage.verifyElementIsPresent("benchmarkscorescale"));
			//will uncomment this validation once the string gets available in maestro
			// Test Case 1 (This Test case validates title of the chart.)
		    softAssert.assertTrue(dashboardPage.verifyTitleofChartOfDashboardPage("digitalExperienceChartTitle", dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "des_dashboard_widget_title")), "Digital Experience chart title did not match.");
			LOGGER.info("Verified title of the DES chart");
			sleeper(1000);
			
			//Test case 2 This test case validates Device Health chart.
			softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("deviceHealth").equalsIgnoreCase(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "digital_exp_score_kpi_device_health")), "Device health title did not match.");
			LOGGER.info("Verified Device health chart is available on dashboard");
			sleeper(1000);
			
			//Test case 3 This test case validates Device Performance chart.
			softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("devicePerformance").equalsIgnoreCase(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "digital_exp_score_kpi_device_performance")), "Device performacve did not match");
			LOGGER.info("Verified Device performance chart is available on dashboard");
			sleeper(1000);
			
			//Test case 4 This test case validates Device Security chart.
			softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("deviceSecurity").equalsIgnoreCase(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "digital_exp_score_kpi_device_security")), "Device Security did not match.");
		     LOGGER.info("Verified  Device Security chart is available on dashboard");
			
		    //dashboardPage.mouseHover(keyPreferences);
			//Test Case 5 This test case validates save chart as image.
			dashboardPage.clickOnElementsOfDashboardPage("fleetScoreSaveChartButton");
			dashboardPage.clickOnElementsOfDashboardPage("saveChartAsImage");
			LOGGER.info("Verified Fleet score chart is able to save as image");
			
	}
}
	
	/**
	 * Feature 838914: [Core] [OKR H5] [WorkF] Digital Experience Score v1
	 * Test Case 910630: [Core][OKR H5][WorkF][UI] Verify Digital Experience Score Experience Management Custom Dashboard.
	 * 
	 * verify add and delete widget for Digital Experience category.
	 * 
	 * @throws Exception
	 */
	@Test(priority = 67,enabled = true,groups = { "REGRESSIONDASHBOARD2", "SMOKE", "REGRESSION_CI","SMOKE_CI" }, description = "Test Case ID : 910630")
	public final void VerifyAddCustomDashboardForDigitalExperience() throws Exception {
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		if (toggleVerification(DashboardVariables.FLEXIDASHBOARD, "MSP_ADMIN_US_EMAIl")) {
			dashboardPage.addCustomDashboardDES("CUSTOM_DASHBOARD_NAME");
			waitForPageLoaded();
			dashboardPage.deleteCustomDashboard("CUSTOM_DASHBOARD_NAME");
			waitForPageLoaded();
			softAssert.assertEquals(dashboardPage.verifyCustomDashboardName("CUSTOM_DASHBOARD_NAME"), false, "Dashboard is not Deleted");
			softAssert.assertAll();
		} else {
			LOGGER.info("This is not Flexible Dashboard");
		}
	}

	@Test(priority = 68,enabled = true,groups = { "REGRESSIONDASHBOARD2", "SMOKE", "REGRESSION_CI","SMOKE_CI" }, description = "Test Case ID : 910630")
	public final void VerifyAddEditDeleteWidgetForDigitalExperience() throws Exception {
		login("ITA_COMPANY_DETAILS_EMAIL", "ITA_COMPANY_DETAILS_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
	   // String subCategory = dashboardPage.getTextLanguage(LanguageCode, "MPI-Reporting-LHreports_service", "label.report_category_digitalexperience");
		if (toggleVerification(DashboardVariables.FLEXIDASHBOARD, "ITA_COMPANY_DETAILS_EMAIL")) {
			String digitalExperience = dashboardPage.getTextLanguage(LanguageCode, "dashboard_service", "label.report_type_Digital_Experience");
			// Add custom dashboard
			dashboardPage.addCustomDashboardDES("CUSTOM_DASHBOARD_NAME");
			//Add Widget into custom dashboard
			dashboardPage.addWidgetForDigitalExperience(digitalExperience, LanguageCode);
			softAssert.assertEquals(dashboardPage.getWidgetCount(), 1, "Widget is not present on grid");
			LOGGER.info("Widget has been added successfully");
			 sleeper(2000);
				softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("benchmarkmessage").equalsIgnoreCase(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "digital.exp.score.guage.benchmark.note")), "Benchmark message title did not match.");
				softAssert.assertTrue(dashboardPage.verifyElementIsVisible("benchmarktag"));
			softAssert.assertTrue(dashboardPage.verifyElementIsPresent("poorscorescale"));
			softAssert.assertTrue(dashboardPage.verifyElementIsPresent("fairscorescale"));
			softAssert.assertTrue(dashboardPage.verifyElementIsPresent("excellentscorescale"));
			softAssert.assertTrue(dashboardPage.verifyElementIsPresent("benchmarkscorescale"));
			// Update widget name
			dashboardPage.updateWidgetDES("UPDATE_NAME_OF_WIDGET");
			waitForPageLoaded();
			// Delete custom dashboard
			dashboardPage.deleteWidgetDES();
			LOGGER.info("Widget has been deleted successfully");
			sleeper(1000);
			dashboardPage.deleteCustomDashboard("CUSTOM_DASHBOARD_NAME");
			waitForPageLoaded();
			softAssert.assertEquals(dashboardPage.verifyCustomDashboardName("CUSTOM_DASHBOARD_NAME"), false, "Dashboard is not deleted");
			softAssert.assertAll();
		} else {
			LOGGER.info("This is not Flexible Dashboard");
		}
	}

	@Test(priority = 69,enabled = true,groups = { "REGRESSIONDASHBOARD2", "SMOKE", "REGRESSION_CI","SMOKE_CI" }, description = "Test Case ID : 919354")
	public final void VerifyDefaultDashboardNetworkWidget() throws Exception {
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		softAssert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("WeeklyNetworkOutageGraph"),"Default weekly outage graph on dashboard is not present");
		softAssert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("NetworkSpeedGraph"),"Network Speed graph on dashboard is not present");
		softAssert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("SevenDayLowSpeedTrend"),"SevenDayLowSpeedTrend graph on dashboard is not present");
		softAssert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("outdatedNetworkCardDriver"),"outdatedNetworkCardDriver graph on dashboard is not present");

		LOGGER.info("Default Network Speed, SevenDayLowSpeedTrend ,outdatedNetworkCardDriver and weekly network outage widget on dashboard are verified successfully");
	}

	/**
	 * User Story 928334: [Analytics][UI] Provide download functionality for Win11 widgets drilldown
	 * This test verifies the windows 11 Upgrade KPI download functionality from details
	 */
	@Test(priority = 70 ,  groups = {"REGRESSIONDASHBOARD2"},enabled = false  )
	public final void verifyWindows11WidgetDetailsDownload() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		
		if (!toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, "WINDOWS_11_DEVICE_COMPATIBILITY_VERIFICATION_USER"))){
			Assert.assertTrue(setToDefaultDashboard(LanguageCode),"Error occured in setting dashboard to default mode.");
		}else{
			Assert.assertTrue(setToDefaultDashboardGlobalFilter(LanguageCode),"Error occured in setting dashboard to default mode.");
		}
		impersonateCompanyByNameDashboard("COMPANY_NAME_REPORTS","MSP_ADMIN_US_EMAIl_REPORTS");
		
		dashboardPage.scrollToDashboardPage("windows11UpgradeStatusTitle");
		LOGGER.info("Scrolled down to Windows11 Upgrade status chart");
		dashboardPage.waitForPageLoaded();
		softAssert.assertTrue(dashboardPage.verifyTitleofChartOfDashboardPage("windows11UpgradeStatusTitle", dashboardPage.getTextLanguage(LanguageCode, "dashboard_service", "label.dashborad.global.win11FleetReadiness.win11UpgradeStatusKpi")), "Windows 11 upgrade status title dod not match.");dashboardPage.waitForElementsOfDashboardPage("upgradeToWin11Title");
		softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("upgradeToWin11Title").equals(getTextLanguage(LanguageCode, "daas_reports_ui", "hardware.win11.compatibility.updated_to_windows_11")), "Upgrade to Windows 11 title does not match.");
		dashboardPage.clickOnElementsOfDashboardPage("upgradeToWin11Card");
		dashboardPage.switchToDifferentTabOfDashboardPage();
		sleeper(3000);
		dashboardPage.clickByJavaScriptOnDashboardPage("download");
		sleeper(3000);
		boolean isFilePresent=false;
		long t = System.currentTimeMillis();
		dashboardPage.clickOnElementsOfDashboardPage("pdfClick");
		isFilePresent = dashboardPage.verifyGridDownload(isFilePresent, t);
		softAssert.assertEquals(isFilePresent, true);
		deleteAndCreateDir(ConstantPath.DOWNLOAD_PATH);
		isFilePresent = false;
		dashboardPage.clickByJavaScriptOnDashboardPage("download");
		sleeper(3000);
		t = System.currentTimeMillis();
		dashboardPage.clickOnElementsOfDashboardPage("xlsxClick");
		isFilePresent = dashboardPage.verifyGridDownload(isFilePresent, t);
		softAssert.assertEquals(isFilePresent, true);
		softAssert.assertAll();
		LOGGER.info("Test case verifyWindows11WidgetDetailsDownload passed successfully.");
	}
	/**
	 * User Story 932616: [Core] [OKR H5] [WorkF] [UI] Digital Experience Score Coachmark UI Implementation
	 * Test Case 940038: [Core] [OKR H5] [WorkF] [UI] Verification of UI coachmark DES main dashboard.
	 * This test verifies Verification of UI coachmark DES main dashboard.
	 */
	
	@Test(priority = 71, enabled = true, groups = { "REGRESSIONDASHBOARD2", "SMOKE", "REGRESSION_CI","SMOKE_CI" }, description = "Test Case ID : 838914")
	public final void verifyCoachMarkOnDigitalExpDashboard() throws Exception {
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		if (!toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, "MSP_NEW_USER_EMAIL_FLEETCHART_US"))){
			Assert.assertTrue(setToDefaultDashboard(LanguageCode),"Error occured in setting dashboard to default mode.");
		}else{
			Assert.assertTrue(setToDefaultDashboardGlobalFilter(LanguageCode),"Error occured in setting dashboard to default mode.");
		}
		if (dashboardPage.verifyElementsOfDashboardPage("recommandedActionButton")) {
			dashboardPage.clickOnElementsOfDashboardPage("recommandedActionButton");
			}
		waitForPageLoaded();
		dashboardPage.clickByJavaScriptOnDashboardPage("globalLocationFilterDropDown");
	    softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("filterMenuCompaniesHeader").equalsIgnoreCase(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "dashboard.actions.filter.company.label")), "Location filter header does not match");
		dashboardPage.enterTextForDashboardPage("filterMenuCompaniesSearch", globalFilterCompanyFleetChart);
		LOGGER.info("Search company to set location filter");
		waitForPageLoaded();
		dashboardPage.clickOnElementsOfDashboardPage("cancelbutton");
		dashboardPage.waitForElementsOfDashboardPage("companyOnListSearch");
		softAssert.assertTrue(dashboardPage.matchTextOfDashboardPage("companyOnListSearch", globalFilterCompanyFleetChart), "Company name and search result company name does not match");
		if (dashboardPage.verifyElementsOfDashboardPage("companyOnListSearch")) {
			dashboardPage.clickOnElementsOfDashboardPage("companyOnListSearch");
		LOGGER.info("Select company to set location filter");
		sleeper(2000);
        dashboardPage.clickOnElementsOfDashboardPage("globalFilterSave");
		}
		LOGGER.info("Global Location Filter Saved successfully.");
		waitForPageLoaded();
		dashboardPage.clickOnElementsOfDashboardPage("helpButton");
		if(dashboardPage.verifyElementsOfDashboardDetailsPage("relaunchDES")) {
		dashboardPage.clickOnElementsOfDashboardPage("relaunchDES");
		softAssert.assertTrue(dashboardPage.verifyTitleofChartOfDashboardPage("coachMarkTitle1", dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "digitalExpScore.dashboard.coachmark.title1")), "CoachMark title did not match.");
		softAssert.assertTrue(dashboardPage.verifyTextContainsOnElementsOfDashboardPage("coachMark1TextMessage1", dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "digitalExpScore.dashboard.coachmark.step1.msg1")), "Coachmark text 1 is not present");
		softAssert.assertTrue(dashboardPage.verifyTextContainsOnElementsOfDashboardPage("coachMark1TextMessage2", dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "digitalExpScore.dashboard.coachmark.step1.msg2")), "Coachmark text 2 is not present");
		softAssert.assertTrue(dashboardPage.verifyTextContainsOnElementsOfDashboardPage("coachMark1TextMessage3", dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "digitalExpScore.dashboard.coachmark.step1.msg3")), "Coachmark text 3 is not present");
		
		dashboardPage.clickOnElementsOfDashboardPage("coachMark1Next");
		LOGGER.info("COACHMARK 1.");
		softAssert.assertTrue(dashboardPage.verifyTitleofChartOfDashboardPage("coachMarkTitle2", dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "digitalExpScore.dashboard.coachmark.title1")), "CoachMark title did not match.");
		softAssert.assertTrue(dashboardPage.verifyTextContainsOnElementsOfDashboardPage("coachMark2TextMessage", dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "digitalExpScore.dashboard.coachmark.step2.msg1")), "Coachmark text  is not present");
		dashboardPage.scrollDownCharts();
		dashboardPage.clickOnElementsOfDashboardPage("coachMark2Next");
		LOGGER.info("COACHMARK 2.");
		softAssert.assertTrue(dashboardPage.verifyTitleofChartOfDashboardPage("coachMarkTitle3", dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "digitalExpScore.dashboard.coachmark.title1")), "CoachMark title did not match.");
		softAssert.assertTrue(dashboardPage.verifyTextContainsOnElementsOfDashboardPage("coachMark3TextMessage1", dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "digitalExpScore.dashboard.coachmark.step3.msg1")), "Coachmark text 1 is not present");
		softAssert.assertTrue(dashboardPage.verifyTextContainsOnElementsOfDashboardPage("coachMark1TextMessage2", dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "digitalExpScore.dashboard.coachmark.step3.msg2")), "Coachmark text 2 is not present");
		softAssert.assertTrue(dashboardPage.verifyTextContainsOnElementsOfDashboardPage("coachMark1TextMessage3", dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "digitalExpScore.dashboard.coachmark.step3.msg3")), "Coachmark text 3 is not present");
		dashboardPage.clickOnElementsOfDashboardPage("coachMark3Close");
		}
		LOGGER.info("All Coachmarks on dashboard are verified successfully");
}
	 
	/**
	 * User Story 932616: [Core] [OKR H5] [WorkF] [UI] Digital Experience Score Coachmark UI Implementation
	 
	 * This test verifies Verification of relaunch option when there is no DES data.
	 */
	    
	@Test(priority = 72, enabled = true, groups = { "REGRESSIONDASHBOARD2", "SMOKE", "REGRESSION_CI","SMOKE_CI" }, description = "Test Case ID : 838914")
	public final void verifyDESNoDataRelaunch() throws Exception {
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		if (!toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, "MSP_NEW_USER_EMAIL_FLEETCHART_US"))){
			Assert.assertTrue(setToDefaultDashboard(LanguageCode),"Error occured in setting dashboard to default mode.");
		}else{
			Assert.assertTrue(setToDefaultDashboardGlobalFilter(LanguageCode),"Error occured in setting dashboard to default mode.");
		}
		 
		      	softAssert.assertTrue(dashboardPage.verifyTitleofChartOfDashboardPage("digitalExperienceChartTitle", dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "des_dashboard_widget_title")), "Digital Experience chart title did not match.");
				softAssert.assertTrue(dashboardPage.verifyTextContainsOnElementsOfDashboardPage("digitalExperienceChartNoData", dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "digital.exp.score.dashboard.no.data")), "Digital Experience chart .");
				waitForPageLoaded();
				dashboardPage.clickOnElementsOfDashboardPage("recommandedActionButton");
				waitForPageLoaded();
				dashboardPage.clickOnElementsOfDashboardPage("helpButton");
				softAssert.assertTrue(dashboardPage.verifyElementsOfDashboardDetailsPage("relaunchDES"),"relaunch DES option is not present under help menu.");
		}
	
	@Test(priority = 73,enabled = true,groups = { "REGRESSIONDASHBOARD2", "SMOKE", "REGRESSION_CI","SMOKE_CI" }, description = "Test Case ID : 910630")
	public final void VerifyAddEditDeleteWidgetForDigitalExperiencelocation() throws Exception {
		login("MSP_NEW_USER_EMAIL_FLEETCHART_US", "MSP_NEW_USER_PASSWORD_FLEETCHART_US");
		SoftAssert softAssert = new SoftAssert();
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
	   	 //String subCategory = dashboardPage.getTextLanguage(LanguageCode, "MPI-Reporting-LHreports_service", "label.report_category_digitalexperience");
		if (toggleVerification(DashboardVariables.FLEXIDASHBOARD, "MSP_NEW_USER_EMAIL_FLEETCHART_US")) {
			String digitalExperience = dashboardPage.getTextLanguage(LanguageCode, "dashboard_service", "label.report_type_Digital_Experience");
			dashboardPage.clickOnElementsOfDashboardPage("globalLocationFilter");
			dashboardPage.waitForElementsOfDashboardPage("companyOnListSearch");
			dashboardPage.clickOnElementsOfDashboardPage("companyOnListSearch");
			dashboardPage.clickOnElementsOfDashboardPage("applyCompanyLocationFilter");
			// Getting all text before location change
			List<String> getallTextbeforelocation = dashboardPage.getallTextOfDashboardPage("deviceScore");
			// Add custom dashboard
			dashboardPage.addCustomDashboardDES("CUSTOM_DASHBOARD_NAME");
			//Add Widget into custom dashboard
			dashboardPage.addWidgetForDigitalExperience(digitalExperience, LanguageCode);		
			sleeper(2000);
			dashboardPage.clickOnElementsOfDashboardPage("globalLocationFilter");
			dashboardPage.clickOnElementsOfDashboardPage("locationone");
			dashboardPage.clickOnElementsOfDashboardPage("locationtwo");
			dashboardPage.clickOnElementsOfDashboardPage("locationthree");
			dashboardPage.clickOnElementsOfDashboardPage("applyCompanyLocationFilter");
			// Getting all text after location change
			List<String> getallTextafterlocation = dashboardPage.getallTextOfDashboardPage("deviceScore");
			softAssert.assertTrue(!getallTextbeforelocation.equals(getallTextafterlocation));
			waitForPageLoaded();
			LOGGER.info("Widget has been added successfully");
			// Update widget name
			dashboardPage.updateWidgetDES("UPDATE_NAME_OF_WIDGET");
			waitForPageLoaded();
			// Delete custom dashboard
			dashboardPage.deleteWidgetDES();
			LOGGER.info("Widget has been deleted successfully");
			sleeper(1000);
			dashboardPage.deleteCustomDashboard("CUSTOM_DASHBOARD_NAME");
			waitForPageLoaded();
			softAssert.assertEquals(dashboardPage.verifyCustomDashboardName("CUSTOM_DASHBOARD_NAME"), false, "Dashboard is not deleted");
			dashboardPage.clickOnElementsOfDashboardPage("globalLocationFilter");
			dashboardPage.clickOnElementsOfDashboardPage("clearalllocation");
			dashboardPage.clickOnElementsOfDashboardPage("applyCompanyLocationFilter");
			softAssert.assertAll();
		} else {
			LOGGER.info("This is not Flexible Dashboard");
		}
	}
	
	/**
	 * verify add and delete widget for hardware category for
	 * Feature 855082: [Core][OnlineHelp] Add new context sensitive links for next-level TP Screens/pages (e.g. reports) .
	 * 
	 * @throws Exception
	 */
	@Test(priority = 74, groups = { "REGRESSION", "REGRESSION_CI","REGRESSIONDASHBOARD1", "STABILIZATION_STAGING" }, description = "Test Case ID : 1033595")
	public final void VerifyHelplinkOnReportsPage() throws Exception {
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		if (toggleVerification(DashboardVariables.FLEXIDASHBOARD, "MSP_ADMIN_US_EMAIl_DASHBOARD")) {
			String hardware = dashboardPage.getTextLanguage(LanguageCode, "MPI-Reporting-report-html-view-UI-JSON", "hardware");
			// List of sub-categories of hardware category
			ArrayList<String> subCategoryList = new ArrayList<String>(Arrays.asList(getTextLanguage(LanguageCode, "MPI-Reporting-LHreports_service", "label.report_category_batteryStatRiskFact"), getTextLanguage(LanguageCode, "MPI-Reporting-LHreports_service", "label.report_category_biosinventory"),
					getTextLanguage(LanguageCode, "MPI-Reporting-LHreports_service", "label.report_category_hwbluescreen"), getTextLanguage(LanguageCode, "MPI-Reporting-LHreports_service", "label.report_category_deviceuti"),
					getTextLanguage(LanguageCode,"MPI-Reporting-LHreports_service","label.report_category_diskcap"),getTextLanguage(LanguageCode, "MPI-Reporting-LHreports_service", "label.report_category_hwhealth"),
					getTextLanguage(LanguageCode, "MPI-Reporting-LHreports_service", "label.report_category_hwinv")));

			dashboardPage.addCustomDashboard("HELPLINK_DASHBOARD_NAME");
			softAssert.assertEquals(dashboardPage.verifyCustomDashboardName("HELPLINK_DASHBOARD_NAME"), true, "Dashboard is not created");
			waitForAnalyticsPageLoaded();
			dashboardPage.addHardwareWidgetforHelpLink(hardware, subCategoryList, LanguageCode);
			sleeper(3000);
			dashboardPage.deleteCustomDashboard("HELPLINK_DASHBOARD_NAME");
			softAssert.assertEquals(dashboardPage.verifyCustomDashboardName("HELPLINK_DASHBOARD_NAME"), false, "Dashboard is not deleted");
			softAssert.assertAll();
		} else {
			LOGGER.info("This is not flexible dashboard");
		}
	}
	/**
	 * verify add and delete widget for Software category for
	 * Feature 855082: [Core][OnlineHelp] Add new context sensitive links for next-level TP Screens/pages (e.g. reports) .
	 * 
	 * @throws Exception
	 */
	@Test(priority = 75, groups = { "REGRESSION", "REGRESSION_CI", "REGRESSIONDASHBOARD1", "STABILIZATION_STAGING" }, description = "Test Case ID : 1033595")
	public final void VerifyHelplinkOnReportsPageforSoftware() throws Exception {
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		if (toggleVerification(DashboardVariables.FLEXIDASHBOARD, "MSP_ADMIN_US_EMAIl_DASHBOARD")) {
			String software = dashboardPage.getTextLanguage(LanguageCode, "MPI-Reporting-report-html-view-UI-JSON", "software");
			// List of sub-categories of hardware category
			ArrayList<String> subCategoryList = new ArrayList<String>(Arrays.asList(
					getTextLanguage(LanguageCode, "MPI-Reporting-LHreports_service", "label.report_category_swerrors"),
					getTextLanguage(LanguageCode, "MPI-Reporting-LHreports_service", "label.report_category_softuti"),
					getTextLanguage(LanguageCode, "MPI-Reporting-LHreports_service", "label.report_option_driverInv")));
			dashboardPage.addCustomDashboard("HELPLINK_DASHBOARD_NAME");
			softAssert.assertEquals(dashboardPage.verifyCustomDashboardName("HELPLINK_DASHBOARD_NAME"), true, "Dashboard is not created");
			waitForAnalyticsPageLoaded();
			dashboardPage.addsoftwareWidgetforHelpLink(software, subCategoryList, LanguageCode);
			sleeper(3000);
			dashboardPage.deleteCustomDashboard("HELPLINK_DASHBOARD_NAME");
			softAssert.assertEquals(dashboardPage.verifyCustomDashboardName("HELPLINK_DASHBOARD_NAME"), false, "Dashboard is not deleted");
			softAssert.assertAll();
		} else {
			LOGGER.info("This is not flexible dashboard");
		}
	}
	/**
	 * verify add and delete widget for Subscription category for
	 * Feature 855082: [Core][OnlineHelp] Add new context sensitive links for next-level TP Screens/pages (e.g. reports) .
	 * 
	 * @throws Exception
	 */
	@Test(priority = 76, groups = { "REGRESSION", "REGRESSION_CI", "REGRESSIONDASHBOARD1", "STABILIZATION_STAGING" }, description = "Test Case ID : 1033595")
	public final void VerifyHelplinkOnReportsPageforSubscription() throws Exception {
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		if (toggleVerification(DashboardVariables.FLEXIDASHBOARD, "MSP_ADMIN_US_EMAIl_DASHBOARD")) {
			String subscription = dashboardPage.getTextLanguage(LanguageCode, "MPI-Reporting-report-html-view-UI-JSON", "subscription");
			// List of sub-categories of hardware category
			ArrayList<String> subCategoryList = new ArrayList<String>(Arrays.asList(
					getTextLanguage(LanguageCode, "MPI-Reporting-LHreports_service", "label.report_category_seatsbydevenrol")));
			dashboardPage.addCustomDashboard("HELPLINK_DASHBOARD_NAME");
			softAssert.assertEquals(dashboardPage.verifyCustomDashboardName("HELPLINK_DASHBOARD_NAME"), true, "Dashboard is not created");
			waitForAnalyticsPageLoaded();
			dashboardPage.addsubscriptionWidgetforHelpLink(subscription, subCategoryList, LanguageCode);
			sleeper(3000);
			dashboardPage.deleteCustomDashboard("HELPLINK_DASHBOARD_NAME");
			softAssert.assertEquals(dashboardPage.verifyCustomDashboardName("HELPLINK_DASHBOARD_NAME"), false, "Dashboard is not deleted");
			softAssert.assertAll();
		} else {
			LOGGER.info("This is not flexible dashboard");
		}
	}

	/**
	 * Add Custom dashboard for PIXM
	 * Feature 1017468: [Managed][PIXM2.0] Default Dashboard for PIXM 2.0 customers .
	 * 
	 * @throws Exception
	 */
	@Test(priority = 77, groups = { "REGRESSION", "REGRESSION_CI",  "REGRESSIONDASHBOARD1" }, description = "Test Case ID : 1062537")
	public final void VerifyAddCustomDashboardForPIXM() throws Exception {
		login("EMAIl_PIXM_DASHBOARD", "PIXM_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		String digitalExperience = dashboardPage.getTextLanguage(LanguageCode, "dashboard_service", "label.report_category_digitalexperience");
		dashboardPage.addCustomDashboard("CUSTOM_DASHBOARD_NAME");
		waitForPageLoaded();
		dashboardPage.addWidgetForPixmCustomDashboard(digitalExperience, LanguageCode);
		LOGGER.info("Widget has been added successfully");
		softAssert.assertEquals(dashboardPage.getWidgetCount(), 1, "Widget is not present on grid");
		softAssert.assertAll();
		} 
	
	/**
	 * Verify Widgets sequence for default PIXM dashboard
	 * Feature 1017468: [Managed][PIXM2.0] Default Dashboard for PIXM 2.0 customers .
	 * 
	 * @throws Exception
	 */
	@Test(priority = 78, groups = { "REGRESSION", "REGRESSION_CI", "SMOKE", "SMOKE_CI", "REGRESSIONDASHBOARD1" })
	public final void verifyChartSequenceforPixmDefaultDashboard() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login("EMAIl_PIXM_DASHBOARD", "PIXM_PASSWORD");
		String chartIdsNewArray[] ={ "hpdxDEOverview","hpdxUserExperience"};
		String chartIdsOldArray[] = { "hwwarByDeviceWarranty0","hwinvByDeviceMfgYear0","monitorInvByDispInvCompMfg0","win11Compatibility0","win11UpgradeStatusKpi0","driverByCriticalityKpi0","driverByCriticalityKpi0","biosInventorySummary0","biosInventoryByWeek0","batteryRepAndRiskFactBiosUpToDate0","batteryRepAndRiskFactDeviceByBHM0"};
				
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		LOGGER.info("Validation of Chart's sequence for Pixm default dashboard started");
		// Test case 1	
		Assert.assertTrue(setToDefaultDashboardGlobalFilter(LanguageCode),"Error occured in setting dashboard to default mode.");
		waitForPageLoaded();
		softAssert.assertTrue(dashboardPage.verifyChartOrderOfDashBoardPageForReportAdminFlexi("firstTwoChartId", chartIdsNewArray), "Chart Sequence for Report Admin is not correct");
		LOGGER.info("First 2 widgets are in sequence");
		softAssert.assertTrue(dashboardPage.verifyChartOrderOfDashBoardPageForReportAdminFlexi("allChartsLocatorFlexi", chartIdsOldArray), "Chart Sequence for Report Admin is not correct");
		softAssert.assertAll();
		LOGGER.info("Validation of Chart's sequence for ReportAdmin completed successfully");
		}

	
	
	
	/**
	 * User Story 611859: [CaaS][UI] Create new Dashboard and Report for CaaS Disk replacement
	 * User Story 1119658: [CaaS][UI] On Dashboard create the Daunt Chart and widget option.
	 * Test Case 1063176: [CaaS][UI][HPPresence][SanityCheck] Add widgets on techpulse dashboard for HP Presence category
	 * This method validates add and delete widget for CaaS dashboard HP Presence category.
	 * The subcategories to be considered are:
	 * Presence Hardware Inventory,  Presence Hardware health , Presence Disk Replacement , Presence Incident Management Report
	 * Presence Room Utilization,  Presence Room health, Presence Meeting Quality, Presence Software Update
	 * @throws Exception
	 */
	@Test(priority = 77, groups = {"CAAS_REGRESSION"},description = "TC:1063176")
	public final void VerifyAddCustomDashboardAddDeleteWidgetForCaasHPPresence() throws Exception {
        login("COMPANY_CAAS_EMAIL", "COMPANY_CAAS_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		if (toggleVerification(DashboardVariables.FLEXIDASHBOARD, "COMPANY_CAAS_EMAIL")) {
			String category = dashboardPage.getTextLanguage(LanguageCode, "dashboard_service", "label.report_type_Conference_Service");
			// List of sub-categories of HP Presence category for CaaS 
			ArrayList<String> subCategoryList = new ArrayList<String>(Arrays.asList(
					getTextLanguage(LanguageCode, "dashboard_service", "label.report_category_roomUtilization"),
					getTextLanguage(LanguageCode, "dashboard_service", "label.report_category_presenceRoomHealth"),
					getTextLanguage(LanguageCode, "dashboard_service", "label.report_category_presenceMeetingQuality"),
					getTextLanguage(LanguageCode, "dashboard_service", "label.report_category_presenceDeviceUpdate"),
					getTextLanguage(LanguageCode, "dashboard_service", "label.report_category_mrsincidentmgmt"),
					getTextLanguage(LanguageCode, "dashboard_service", "label.report_category_mrsHwinv"),
					getTextLanguage(LanguageCode, "dashboard_service", "label.report_category_mrsDiskrep"), 
					getTextLanguage(LanguageCode, "dashboard_service", "label.report_category_mrsHwhealthNew"),
					getTextLanguage(LanguageCode, "dashboard_service", "label.report_category_mrsDeviceutiV3")));
			sleeper(5000);
			dashboardPage.addCustomDashboard("CUSTOM_DASHBOARD_NAME");
			softAssert.assertEquals(dashboardPage.verifyCustomDashboardName("CUSTOM_DASHBOARD_NAME"), true, "Dashboard is not created");
			refreshPage();
			dashboardPage.addWidgetForCaaSHPPresence(category, subCategoryList, LanguageCode);
			LOGGER.info("Widget has been added successfully");
			sleeper(3000);
			dashboardPage.deleteCustomDashboard("CUSTOM_DASHBOARD_NAME");
			softAssert.assertEquals(dashboardPage.verifyCustomDashboardName("CUSTOM_DASHBOARD_NAME"), false, "Dashboard is not deleted");
			//softAssert.assertAll();
		} else {
			LOGGER.info("This is not flexible dashboard");
		}
		
	}
	
	/**
	 * User Story 797796: [CaaS][UI] Software Update for Conference Room
	 * Test Case 1057680: [CaaS][UI][SanityCheck] Verify When User Open Available Software Update widget on dashboard All the Templates should shows up
	 * This method validates Updates Screen containing templates in HP Presence software Update Widget 
	 */
	@Test(priority = 80, groups = {"CAAS_REGRESSION"},description = "TC:1057680")
	public final void VerifyHPPresenceSoftwareUpdateWidgetUpdatesScreenForCaasHPPresence() throws Exception {
        login("COMPANY_CAAS_EMAIL", "COMPANY_CAAS_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		if (toggleVerification(DashboardVariables.FLEXIDASHBOARD, "COMPANY_CAAS_EMAIL")) {
			String category = dashboardPage.getTextLanguage(LanguageCode, "dashboard_service", "label.report_type_Conference_Service");
			// HPPresence SoftwareUpdate sub-category of HP Presence category for CaaS 
			ArrayList<String> subCategoryList = new ArrayList<String>(Arrays.asList(
					getTextLanguage(LanguageCode, "dashboard_service", "label.report_category_presenceSoftwareUpdate")));
			sleeper(5000);
			dashboardPage.addCustomDashboard("CUSTOM_DASHBOARD_NAME");
			softAssert.assertEquals(dashboardPage.verifyCustomDashboardName("CUSTOM_DASHBOARD_NAME"), true, "Dashboard is not created");
			refreshPage();
			dashboardPage.addWidgetForCaaSHPPresence(category, subCategoryList, LanguageCode);
			sleeper(3000);
			
			//Verifying HPPresenceSoftwareUpdate Widget
			softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("availableUpdates").equals(dashboardPage.getTextLanguage(LanguageCode,"daas_ui","rooms.deviceupdate.label.available_updates")),"Available Updates title text is incorrect");
			
			//Verifying Update Devices button is present when Templates count is more than Zero
			if(dashboardPage.verifyUpdateDevicesButtonInHpPresenceSoftwareUpdateWidget())
			{
				
			dashboardPage.clickOnElementsOfDashboardPage("updateDevices");
			LOGGER.info("Update Devices Button is present");

			//Verify Updates Screen
			softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("updates").equals(dashboardPage.getTextLanguage(LanguageCode,"daas_ui","rooms.updatedevice.label.updates")),"Software Updates - Updates Page title text is incorrect");
			
			//Verify BIOS Templates from Templates List
			List<WebElement> templatesNamesList = dashboardPage.getElementsOfDashboardPage("biosTemplateCount");
			for(WebElement templateName:templatesNamesList)
			{
				if(templateName.getText().contains(DashboardVariables.BIOSTEMPLATE1)||templateName.getText().contains(DashboardVariables.BIOSTEMPLATE2)||templateName.getText().contains(DashboardVariables.BIOSTEMPLATE3))
				{
					LOGGER.info(templateName.getText()+" this is a BIOS Template."); 
				}
				
			}
			LOGGER.info("Updates Screen has been verified ");
			
			dashboardPage.waitForElementsOfDashboardPage("updatesScreenCancelButton");
			dashboardPage.verifyElementIsClickableOfDashboardPage("updatesScreenCancelButton");
			dashboardPage.clickByJavaScriptOnDashboardPage("updatesScreenCancelButton");
			LOGGER.info("Cancel Button in Updates screen is verified");	
			}
            scrollToTop();
			dashboardPage.deleteCustomDashboard("CUSTOM_DASHBOARD_NAME");
			softAssert.assertEquals(dashboardPage.verifyCustomDashboardName("CUSTOM_DASHBOARD_NAME"), false, "Dashboard is not deleted");
			//softAssert.assertAll();
		} else {
			LOGGER.info("This is not flexible dashboard");
		}
	}
	
	/**
     * User Story 797796: [CaaS][UI] Software Update for Conference Room
     * Test Case 1055584: [CaaS][Subscription][Zoom]>> Verify Enable Teams integration message is displaying when both Third Party Integration App (Zoom or Teams) or not enabled
     * This method validates the third party software message on Room Utilization Widget
     * @throws Exception
     */
    @Test(priority = 81, groups = {"CAAS_REGRESSION"},description = "TC:1055584")
    public final void VerifyPresenceRoomUtiWidgetMsg() throws Exception {
        login("COMPANY_CAAS_EMAIL", "COMPANY_CAAS_PASSWORD");
        SoftAssert softAssert = new SoftAssert();
        DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
        String category = dashboardPage.getTextLanguage(LanguageCode, "dashboard_service", "label.report_type_Conference_Service");
        // HPPresence SoftwareUpdate sub-category of HP Presence category for CaaS
        ArrayList<String> subCategoryList = new ArrayList<String>(Arrays.asList(
                getTextLanguage(LanguageCode, "dashboard_service", "label.report_category_roomUtilization"),
                getTextLanguage(LanguageCode, "dashboard_service", "label.report_category_presenceMeetingQuality")));
        sleeper(5000);
        dashboardPage.addCustomDashboard("CUSTOM_DASHBOARD_NAME");
        softAssert.assertEquals(dashboardPage.verifyCustomDashboardName("CUSTOM_DASHBOARD_NAME"), true, "Dashboard is not created");
        refreshPage();
        dashboardPage.addWidgetForCaaSHPPresence(category, subCategoryList, LanguageCode);

       //Verifying the third party software message on Room Utilization Widget
        softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("PresenceRmUtiWidgetMsg").equals(dashboardPage.getTextLanguage(LanguageCode,"daas_ui","dashboard.widgets.presence_third_party_integration.header")),"No Message is displayed on Room Utilization Widget");
        softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("PresenceMtgQtyWidgetMsg").equals(dashboardPage.getTextLanguage(LanguageCode,"daas_ui","dashboard.widgets.presence_third_party_integration.header")),"No Message is displayed on Meeting Quality Widget");
        LOGGER.info("No Thirdparty Software like Zoom or MSteams is enabled on this Company");

        dashboardPage.deleteCustomDashboard("CUSTOM_DASHBOARD_NAME");
		softAssert.assertEquals(dashboardPage.verifyCustomDashboardName("CUSTOM_DASHBOARD_NAME"), false, "Dashboard is not deleted");
		//softAssert.assertAll();
    }
	@Test(priority = 82,groups = {"REGRESSIONDASHBOARD2"} )
	public final void verifyFleetOverviewDashboard() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login("MSP_ADMIN_US_EMAIl_REPORTS", "MSP_ADMIN_US_PASSWORD_REPORTS");
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		List<String> expectedList = new ArrayList<String>(Arrays.asList(getTextLanguage(LanguageCode, "daas_reports_ui", "Global.gridHeaders.companyName"),getTextLanguage(LanguageCode, "daas_reports_ui", "gridHeaders.Hardware_Inventory.Details.allData.lastSignedinUser"), getTextLanguage(LanguageCode, "daas_reports_ui", "Global.gridHeaders.enrolledmonth"), getTextLanguage(LanguageCode, "daas_reports_ui", "Global.gridHeaders.deviceName"), getTextLanguage(LanguageCode, "daas_reports_ui", "Global.gridHeaders.serialNumber"),
				getTextLanguage(LanguageCode, "daas_reports_ui", "Global.gridHeaders.deviceType"), getTextLanguage(LanguageCode, "daas_reports_ui", "Global.gridHeaders.deviceManufacturer"), getTextLanguage(LanguageCode, "daas_reports_ui", "Global.gridHeaders.deviceModel"), getTextLanguage(LanguageCode, "daas_reports_ui", "Global.gridHeaders.operatingSystem"),
				getTextLanguage(LanguageCode, "daas_reports_ui", "gridHeaders.hardware_Inventory.Details.operating_system_release"), getTextLanguage(LanguageCode, "daas_reports_ui", "gridHeaders.hardware_Inventory.Details.operating_system_Build_number"), getTextLanguage(LanguageCode, "daas_reports_ui", "gridHeaders.Hardware_Inventory.Details.win10_patch_status"),getTextLanguage(LanguageCode, "daas_reports_ui", "Global.gridHeaders.lastSeen"), getTextLanguage(LanguageCode, "daas_reports_ui", "Global.gridHeaders.memory"), getTextLanguage(LanguageCode, "daas_reports_ui", "gridHeaders.Hardware_Inventory.Details.graphics"), getTextLanguage(LanguageCode, "daas_reports_ui","Global.gridHeaders.processor"),getTextLanguage(LanguageCode,"daas_reports_ui","Global.gridHeaders.manufacture_date"),getTextLanguage(LanguageCode,"daas_reports_ui","gridHeaders.hardware_Inventory.Details.enrolledDate"),getTextLanguage(LanguageCode, "daas_reports_ui", "gridHeaders.Incident_Widget.Details.warStatus"),getTextLanguage(LanguageCode, "daas_reports_ui", "gridHeaders.hardware_Inventory.Details.warrantyEndDate"),getTextLanguage(LanguageCode, "daas_reports_ui", "Global.gridHeaders.country"),getTextLanguage(LanguageCode,"daas_reports_ui","Global.gridHeaders.location"),getTextLanguage(LanguageCode,"daas_reports_ui","Global.gridHeaders.department"),getTextLanguage(LanguageCode, "daas_reports_ui", "Global.gridHeaders.costCenter"),getTextLanguage(LanguageCode, "daas_reports_ui","Global.gridHeaders.alias"), getTextLanguage(LanguageCode, "daas_reports_ui", "Global.gridHeaders.asset_tag"),getTextLanguage(LanguageCode,"daas_reports_ui","graphHeaders.Hardware_Inventory.Details.lifecycle_status"), getTextLanguage(LanguageCode, "daas_reports_ui", "graphHeaders.Hardware_Inventory.Details.device_role"),
				getTextLanguage(LanguageCode, "daas_reports_ui", "graphHeaders.Hardware_Inventory.Details.component"), getTextLanguage(LanguageCode, "daas_reports_ui", "graphHeaders.Hardware_Inventory.Details.component_serial_number"), getTextLanguage(LanguageCode, "daas_reports_ui", "graphHeaders.Hardware_Inventory.Details.component_repl_timeframe"),getTextLanguage(LanguageCode, "daas_reports_ui", "graphHeaders.Hardware_Inventory.Details.component_Model"),getTextLanguage(LanguageCode,"daas_reports_ui", "graphHeaders.Hardware_Inventory.Details.component_size_gb"),getTextLanguage(LanguageCode, "daas_reports_ui", "gridHeaders.Hardware_Warranty.enrolled"),getTextLanguage(LanguageCode, "daas_reports_ui", "gridHeaders.batterySwelling.platformId"),getTextLanguage(LanguageCode, "daas_reports_ui", "Global.gridHeaders.location1"), getTextLanguage(LanguageCode, "daas_reports_ui", "Global.gridHeaders.location2"), getTextLanguage(LanguageCode, "daas_reports_ui", "Global.gridHeaders.location3"), getTextLanguage(LanguageCode, "daas_reports_ui", "Global.gridHeaders.location4")));

		List<String> expectedListMonitor = new ArrayList<String>(Arrays.asList(getTextLanguage(LanguageCode, "daas_reports_ui", "Global.gridHeaders.companyName"), getTextLanguage(LanguageCode, "daas_reports_ui", "gridHeaders.Hardware_Inventory.Details.allData.lastSignedinUser"), getTextLanguage(LanguageCode, "daas_reports_ui", "Global.gridHeaders.deviceName"), getTextLanguage(LanguageCode, "daas_reports_ui", "Global.gridHeaders.serialNumber"),getTextLanguage(LanguageCode, "daas_reports_ui", "Global.gridHeaders.deviceType"),getTextLanguage(LanguageCode, "daas_reports_ui", "Global.gridHeaders.deviceModel"),
				getTextLanguage(LanguageCode, "daas_reports_ui", "graphHeaders.Hardware_Inventory.Details.component"),getTextLanguage(LanguageCode, "daas_reports_ui", "gridHeaders.Hardware_Component_Inventory.alldata.display_manufacturer"),getTextLanguage(LanguageCode, "daas_reports_ui", "gridHeaders.Hardware_Component_Inventory.alldata.display_serial_number"),getTextLanguage(LanguageCode, "daas_reports_ui", "gridHeaders.Hardware_Component_Inventory.alldata.display_model_name"),getTextLanguage(LanguageCode, "daas_reports_ui", "gridHeaders.Hardware_Component_Inventory.month"),
				getTextLanguage(LanguageCode, "daas_reports_ui", "Global.gridHeaders.location1"), getTextLanguage(LanguageCode, "daas_reports_ui", "Global.gridHeaders.location2"), getTextLanguage(LanguageCode, "daas_reports_ui", "Global.gridHeaders.location3"), getTextLanguage(LanguageCode, "daas_reports_ui", "Global.gridHeaders.location4")));

		if (!toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, "MSP_ADMIN_US_EMAIl_REPORTS"))){
			Assert.assertTrue(setToDefaultDashboard(LanguageCode),"Error occured in setting dashboard to default mode.");
		}else{
			Assert.assertTrue(setToDefaultDashboardGlobalFilter(LanguageCode),"Error occured in setting dashboard to default mode.");
		}

		LOGGER.info("Scrolled to Fleet Dashboard status chart");
		dashboardPage.waitForPageLoaded();
		softAssert.assertTrue(dashboardPage.verifyTitleofChartOfDashboardPage("fleetDashboardTitle", dashboardPage.getTextLanguage(LanguageCode, "dashboard_service", "label.dashborad.global.fleet.overview")), "Fleet overview title did not match.");

		//Test case 1 : Verify Total Devices
		dashboardPage.waitForElementsOfDashboardPage("totalDevices");
		softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("totalDevices").equals(getTextLanguage(LanguageCode, "daas_reports_ui", "dashboard.fleet.total_devices")), "Total Devices text does not match.");
		softAssert.assertTrue(verifyDetailsGridForKPIWidgetsOnDashboard(LanguageCode,"totalDevicesCount","totalDevices","fleet",expectedList),"The count or columns did not matched");
        LOGGER.info("Switched to Previous tab");

       //Test case 2 : Verify HP Devices out of warranty
		dashboardPage.waitForPageLoaded();
		dashboardPage.waitForElementsOfDashboardPage("hpDevicesOutOfWarranty");
		softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("hpDevicesOutOfWarranty").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_reports_ui", "dashboard.fleet.out_of_warranty")), "Devices Out of warranty title does not match.");
		softAssert.assertTrue(verifyDetailsGridForKPIWidgetsOnDashboard(LanguageCode,"hpDevicesOutOfWarrantyCount","hpDevicesOutOfWarranty","fleet",expectedList),"The count or columns did not matched");
		LOGGER.info("Switched to Previous tab");

		//Test case 3 : Verify HP Devices> 3 years old
		dashboardPage.waitForPageLoaded();
		dashboardPage.waitForElementsOfDashboardPage("over3YearsOld");
		softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("over3YearsOld").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_reports_ui", "dashboard.fleet.three_year_old")), "Devices 3 years old title does not match.");
		softAssert.assertTrue(verifyDetailsGridForKPIWidgetsOnDashboard(LanguageCode,"over3YearsOldCount","over3YearsOld","fleet",expectedList),"The count or columns did not matched");
		LOGGER.info("Switched to Previous tab");

		//Test case 4 : Verify Active Monitors
		/*dashboardPage.waitForPageLoaded();
		dashboardPage.waitForElementsOfDashboardPage("activeMonitors");
		softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("activeMonitors").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_reports_ui", "dashboard.fleet.active_monitor")), "Active Monitors not title does not match.");
		//softAssert.assertTrue(verifyDetailsGridForKPIWidgetsOnDashboard(LanguageCode,"activeMonitorCount","activeMonitors","fleet",expectedListMonitor),"The count or columns did not matched");
		LOGGER.info("Switched to Previous tab");*/
		softAssert.assertAll();
	}
	
	/* Feature 609761: [Insights][PI_Sheff][Antivirus][Insights]Antivirus Insights - AV KPI Widget 
	   User Story 1058478: [Insights] [UI] [Test] : UI verification of Antivirus Insights - AV KPI Widget
	   Test Case 1068856: [Insights][UI][Test] : Antivirus Insights - AV Disabled
	   Test Case 1068895: [Insights][UI][Test] : Antivirus Insights - Outdated Signature. */
	
    @Test(priority = 82, groups = { "REGRESSION", "REGRESSION_CI", "ALL_CI", "ALL", "REGRESSIONDASHBOARD1" }, description = "TC:1068895")
	public final void verifyAVNonCompliancesWindowsDefenderWidget() throws Exception {
    	login("COMPANY_EMAIL_DEVICESECURE_WIDGET","COMPANY_PWS_DEVICESECURE_WIDGET");
    	PreConfiguredDashboardPage preDashPage = new PreConfiguredDashboardPage(PreDefinedActions.getDriver()).getInstance();
    	DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		
		final String[] DASHBOARD_CHART_DEVICE_TYPE_AV_WINDOWS_DEFENDER = {"Global.gridHeaders.deviceName","Global.gridHeaders.deviceSerialNumber",
				"gridHeaders.Hardware_Inventory.Details.allData.lastSignedinUser","Global.gridHeaders.lastSeen","Global.gridHeaders.complianceType",
				"gridHeaders.Device_Sec_Complaince.24_hrs_summary.Details.realtime_protection_status","gridHeaders.Device_Sec_Complaince.24_hrs_summary.Details.signature_status",
				"gridHeaders.Device_Sec_Complaince.24_hrs_summary.Details.totalTime_of_antivirus_status","gridHeaders.Device_Sec_Complaince.24_hrs_summary.Details.antivirus_definition_rel_date_timeframe",
				"gridHeaders.Device_Sec_Complaince.24_hrs_summary.Details.antivirus_definition_release_date","gridHeaders.Device_Sec_Complaince.24_hrs_summary.Details.antivirus_definition_name",
				"gridHeaders.Device_Sec_Complaince.hrSummary.policyValue","gridHeaders.Device_Sec_Complaince.24_hrs_summary.Details.antivirus_application_version",
				"gridHeaders.Device_Sec_Complaince.24_hrs_summary.Details.antivirus_definition_version","gridHeaders.Device_Sec_Complaince.hrSummary.no_of_not_complient_instances","Global.gridHeaders.deviceManufacturer",
				"Global.gridHeaders.deviceModel","Global.gridHeaders.operatingSystem","Global.gridHeaders.location_1","Global.gridHeaders.location_2","Global.gridHeaders.location_3","Global.gridHeaders.location_4"};
		
		if (toggleVerification(DashboardVariables.PRE_CONFIGURED_DASHBOARD, getCredentials(environment, "COMPANY_EMAIL_DEVICESECURE_WIDGET"))) 
		{
		Assert.assertTrue(preDashPage.setToPreConfiguredDashboard(LanguageCode,"flexible_dashboard.dashboard_name.fleet_security"),"Error occured in setting dashboard to default mode.");
		dashboardPage.scrollToDashboardPage("AVWindowsDefenderChartTitle");
		LOGGER.info("Scrolled down to AV windows defender chart");
		dashboardPage.waitForPageLoaded();

		//Test Case1- Verify title of chart. 
		softAssert.assertTrue(dashboardPage.verifyTitleofChartOfDashboardPage("AVWindowsDefenderChartTitle", dashboardPage.getTextLanguage(LanguageCode, "dashboard_service", "label.dashborad.global.antivirus.noncompliances.windows.defender")), "AV windows defender Widget title did not match.");
		LOGGER.info("AV windows defender widget title text matched");
		
		// Test Case 2 (This test case validate whether chart is loaded or not.)
		if(!(dashboardPage.verifyChartsHasNoDataOnDashBoardPageFlexi(LanguageCode, "noDataAVwindowsDefender", "label.dashborad.global.antivirus.noncompliances.windows.defender")))
		{
		 LOGGER.info("AV windows defender widget has data to verify.");
							
		//Test Case 3- Verify Devices with AV Disabled.
		softAssert.assertTrue(dashboardPage.verifyTextContainsOnElementsOfDashboardPage("NoOfDevicesAVDisabled", dashboardPage.getTextLanguage(LanguageCode, "daas_reports_ui", "security.device_security.windows_defender.disabled")), "Devices with Anitivirus disabled did not match.");
		LOGGER.info("Devices with Antivirus Disabled text matched");
		
		//Test Case 4 - Verify AV disabled Report page.
		softAssert.assertTrue(dashboardPage.headerTextVerificationOnReportPageFrame(LanguageCode, "AVDisabledIcon", "headerListLocatorOnReportPage", DASHBOARD_CHART_DEVICE_TYPE_AV_WINDOWS_DEFENDER, "frameLocator"), "Report header for AV Disabled chart did not match on report page.");
		LOGGER.info("AV disabled report is verified successfully");
						
		//Test Case 5 - Verify Devices with Outdated signature.
		softAssert.assertTrue(dashboardPage.verifyTextContainsOnElementsOfDashboardPage("NoOfDevicesWithOutdatedSignature", dashboardPage.getTextLanguage(LanguageCode, "daas_reports_ui", "security.device_security.windows_defender.outdates_signature")), "Devices with outdated signature did not match.");
		LOGGER.info("Devices with Outdated signature text matched");
				
		//Test Case 6 - Verify AV Outdated Signature report page.
		softAssert.assertTrue(dashboardPage.headerTextVerificationOnReportPageFrame(LanguageCode, "AVOutdatedSignatureIcon", "headerListLocatorOnReportPage", DASHBOARD_CHART_DEVICE_TYPE_AV_WINDOWS_DEFENDER, "frameLocator"), "Report header for AV Outdated Signature chart did not match on report page.");
		LOGGER.info("AV Outdated Signature report is verified successfully");
		}
		//Test Case 7- Verify Edit/Update functionality.
		dashboardPage.clickByJavaScriptOnDashboardPage("EditOptionOnWidget");
		dashboardPage.clickOnElementsOfDashboardPage("EditButton");
		dashboardPage.scrollToDashboardPage("updateWidget");
		dashboardPage.clickOnElementsOfDashboardPage("updateWidget");
		LOGGER.info("Update functionality is verified.");
		}
		softAssert.assertAll();
 }
         
    /* Feature 1055369: [Insights][PI_Sheff[Antivirus][Insights]Antivirus Insights - AV Applications Widget
    * User Story 1092962: [Insights][PI_Sheff[Antivirus][Insights]Antivirus Insights : Add widget in the Dashboard under fleet security
    * Test Case 1098066: [Insights][PI_Sheff[Antivirus][Insights][Test] UI verification of Antivirus Insights - AV Applications Widget.
    * Test Case 1098104: Verify Antivirus Insights - AV Applications Widget for other user roles.    */
     
    @Test(priority = 83, groups = { "REGRESSION", "REGRESSION_CI", "ALL_CI", "ALL", "REGRESSIONDASHBOARD1" }, description = "TC:1098066,1098104")
   	public final void verifyAVApplicationFleet() throws Exception {
       	login("COMPANY_EMAIL_DEVICESECURE_WIDGET","COMPANY_PWS_DEVICESECURE_WIDGET");
       	PreConfiguredDashboardPage preDashPage = new PreConfiguredDashboardPage(PreDefinedActions.getDriver()).getInstance();
       	DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
   		SoftAssert softAssert = new SoftAssert();
   		ArrayList<String> AVAPPLICATION_LABEL_LIST = new ArrayList<>(Arrays.asList("Windows Defender", "Bitdefender Antivirus", "AVG Antivirus" , "McAfee Endpoint Security", "Trend Micro Maximum Security","Other","None detected"));
   		final String[] DASHBOARD_CHART_AV_APPLICATION_FLEET = {"Global.gridHeaders.deviceName","Global.gridHeaders.deviceSerialNumber",
				"gridHeaders.Hardware_Inventory.Details.allData.lastSignedinUser","Global.gridHeaders.lastSeen","Global.gridHeaders.complianceType",
				"gridHeaders.Device_Sec_Complaince.24_hrs_summary.Details.realtime_protection_status","gridHeaders.Device_Sec_Complaince.24_hrs_summary.Details.signature_status",
				"gridHeaders.Device_Sec_Complaince.24_hrs_summary.Details.totalTime_of_antivirus_status","gridHeaders.Device_Sec_Complaince.24_hrs_summary.Details.antivirus_definition_rel_date_timeframe",
				"gridHeaders.Device_Sec_Complaince.24_hrs_summary.Details.antivirus_definition_release_date","gridHeaders.Device_Sec_Complaince.24_hrs_summary.Details.antivirus_definition_name",
				"gridHeaders.Device_Sec_Complaince.hrSummary.policyValue","gridHeaders.Device_Sec_Complaince.24_hrs_summary.Details.antivirus_application_version",
				"gridHeaders.Device_Sec_Complaince.24_hrs_summary.Details.antivirus_definition_version","gridHeaders.Device_Sec_Complaince.hrSummary.no_of_not_complient_instances","Global.gridHeaders.deviceManufacturer",
				"Global.gridHeaders.deviceModel","Global.gridHeaders.operatingSystem","Global.gridHeaders.location_1","Global.gridHeaders.location_2","Global.gridHeaders.location_3","Global.gridHeaders.location_4"};
		
		if (toggleVerification(DashboardVariables.PRE_CONFIGURED_DASHBOARD, getCredentials(environment, "COMPANY_EMAIL_DEVICESECURE_WIDGET"))) 
		{ 
		Assert.assertTrue(preDashPage.setToPreConfiguredDashboard(LanguageCode,"flexible_dashboard.dashboard_name.fleet_security"),"Error occured in setting dashboard to default mode.");
		dashboardPage.scrollToDashboardPage("AVApplicationInFleetChartTitle");
		LOGGER.info("Scrolled down to AV application in fleet chart");
		dashboardPage.waitForPageLoaded();
		
		if(!(dashboardPage.verifyElementsOfDashboardPage("noDataAVAppInFleet"))) {
			// Test Case 1 (This Test case validates title of the chart.)
			softAssert.assertTrue(dashboardPage.verifyTitleofChartOfDashboardPage("AVApplicationInFleetChartTitle", dashboardPage.getTextLanguage(LanguageCode, "dashboard_service", "label.dashborad.global.antivirus.applications.in.fleet")), "AV APPLICATION IN FLEET Widget title did not match.");
			LOGGER.info("AV Application In Fleet title text matched");
			// Test Case 2 (This test case validate whether chart is loaded or not.)
			Assert.assertFalse(dashboardPage.verifyChartisLoadedDynamic(LanguageCode, "AVApplicationChartHighlight", "fatalErrorAVAppInFleet", 5), "AV APPLICATION IN FLEET Widget title did not match.");
			LOGGER.info("Verified whether chart is loaded");

			// Test Case 3 (This test case validate whether chart has any data or not.)
			Assert.assertFalse(dashboardPage.verifyChartsHasNoDataOnDashBoardPageFlexi(LanguageCode, "noDataAVAppInFleet", "label.dashborad.global.antivirus.applications.in.fleet"), "AV APPLICATION IN FLEET Widget Chart does not have any data.");
			LOGGER.info("Verified  chart has  data");
			
			dashboardPage.scrollToDashboardPage("scrollToDeviceSecurityComplaince");
			dashboardPage.listMouseHoverOfDashboardPage("AVAppFleetVisibleDonut");
			LOGGER.info("MouseHoverApp donut list");		
			
			softAssert.assertTrue(dashboardPage.headerTextVerificationOnReportPageFrameFleetSecurity(LanguageCode, "AVAppFleetVisibleDonut", "headerListLocatorOnReportPage",DASHBOARD_CHART_AV_APPLICATION_FLEET, "frameLocator"), "Report header for AV Application in Fleet chart did not match on report page.");
			LOGGER.info("AV application report is verified successfully");
			}
		
		}
		//Test Case 7- Verify Edit/Update functionality.
		dashboardPage.clickOnElementsOfDashboardPage("AVEditOption");
		
		softAssert.assertTrue(dashboardPage.headerTextVerificationOnReportPageFrame(LanguageCode, "AVViewFullReport", "headerListLocatorOnReportPage", DASHBOARD_CHART_AV_APPLICATION_FLEET, "frameLocator"), "Full Report header for FW Disabled chart did not match on report page.");
		LOGGER.info("AV application FULL report is verified successfully");
		
		dashboardPage.clickOnElementsOfDashboardPage("AVEditOption");
		dashboardPage.clickOnElementsOfDashboardPage("AVSaveChartAsImage");
		LOGGER.info("Image is downloaded functionality verified.");
		sleeper(1000);
		dashboardPage.clickOnElementsOfDashboardPage("AVEditOption");
		dashboardPage.clickOnElementsOfDashboardPage("AVEditButton");
		dashboardPage.scrollToDashboardPage("updateWidget");
		dashboardPage.clickOnElementsOfDashboardPage("updateWidget");
		LOGGER.info("Update functionality is verified.");
		 softAssert.assertAll();
    }
    
    /*Feature 619548: [Insights][PI_TBD][Firewall][Insights]Firewall Insights - FW KPI Widget.
     * User Story 1100045: [Insights][PI_Sheff][Firewall][Insights]UI verification - Firewall Insights - FW KPI Widget.
     * Test Case 1100040: [Insights][PI_Sheff][Firewall][Insights][Test] UI verification -Firewall Insights - FW KPI Widget.
     */
       

    @Test(priority = 84, groups = { "REGRESSION", "REGRESSION_CI", "ALL_CI", "ALL", "REGRESSIONDASHBOARD1" }, description = "TC:1100040")
	public final void verifyFwKpiWidget() throws Exception {
    	login("COMPANY_EMAIL_DEVICESECURE_WIDGET","COMPANY_PWS_DEVICESECURE_WIDGET");
    	PreConfiguredDashboardPage preDashPage = new PreConfiguredDashboardPage(PreDefinedActions.getDriver()).getInstance();
    	DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		
		final String[] DASHBOARD_CHART_DEVICE_TYPE_FW_WINDOWS_DEFENDER = {"Global.gridHeaders.deviceName","Global.gridHeaders.deviceSerialNumber",
				"gridHeaders.Hardware_Inventory.Details.allData.lastSignedinUser","Global.gridHeaders.lastSeen","Global.gridHeaders.complianceType",
				"gridHeaders.Device_Sec_Complaince.24_hrs_summary.Details.realtime_protection_status","gridHeaders.Device_Sec_Complaince.24_hrs_summary.Details.signature_status",
				"gridHeaders.Device_Sec_Complaince.24_hrs_summary.Details.totalTime_of_antivirus_status","gridHeaders.Device_Sec_Complaince.24_hrs_summary.Details.antivirus_definition_rel_date_timeframe",
				"gridHeaders.Device_Sec_Complaince.24_hrs_summary.Details.antivirus_definition_release_date","gridHeaders.Device_Sec_Complaince.24_hrs_summary.Details.antivirus_definition_name",
				"gridHeaders.Device_Sec_Complaince.hrSummary.policyValue","gridHeaders.Device_Sec_Complaince.24_hrs_summary.Details.antivirus_application_version",
				"gridHeaders.Device_Sec_Complaince.24_hrs_summary.Details.antivirus_definition_version","gridHeaders.Device_Sec_Complaince.hrSummary.no_of_not_complient_instances","Global.gridHeaders.deviceManufacturer",
				"Global.gridHeaders.deviceModel","Global.gridHeaders.operatingSystem","Global.gridHeaders.location_1","Global.gridHeaders.location_2","Global.gridHeaders.location_3","Global.gridHeaders.location_4"};
		
		if (toggleVerification(DashboardVariables.PRE_CONFIGURED_DASHBOARD, getCredentials(environment, "COMPANY_EMAIL_DEVICESECURE_WIDGET"))) 
		{
		Assert.assertTrue(preDashPage.setToPreConfiguredDashboard(LanguageCode,"flexible_dashboard.dashboard_name.fleet_security"),"Error occured in setting dashboard to default mode.");
		dashboardPage.scrollToDashboardPage("FWKPIwidgetChartTitle");
		LOGGER.info("Scrolled down to FW windows defender chart");
		dashboardPage.waitForPageLoaded();

		//Test Case1- Verify title of chart. 
		softAssert.assertTrue(dashboardPage.verifyTitleofChartOfDashboardPage("FWKPIwidgetChartTitle", dashboardPage.getTextLanguage(LanguageCode, "dashboard_service", "label.dashborad.global.firewall.windows.defender")), "FW windows defender Widget title did not match.");
		LOGGER.info("FW windows defender widget title text matched");
		
		// Test Case 2 (This test case validate whether chart is loaded or not.)
		if((dashboardPage.verifyChartsHasNoDataOnDashBoardPageFlexi(LanguageCode, "noDataFWwindowsDefender", "label.dashborad.global.firewall.windows.defender")))
		{
		 LOGGER.info("FW windows defender widget has no data to verify.");
							
		//Test Case 3- Verify Devices with FW Disabled.
		softAssert.assertTrue(dashboardPage.verifyTextContainsOnElementsOfDashboardPage("FwDevicesText", dashboardPage.getTextLanguage(LanguageCode, "daas_reports_ui", "security.device_security.windows_defender.disabled_firewall")), "Devices with Firewall disabled did not match.");
		LOGGER.info("Devices with Firewall Disabled text matched");
		
		//Test Case 4 - Verify FW disabled Report page.
		softAssert.assertTrue(dashboardPage.headerTextVerificationOnReportPageFrame(LanguageCode, "FwDisabledIcon", "headerListLocatorOnReportPage", DASHBOARD_CHART_DEVICE_TYPE_FW_WINDOWS_DEFENDER, "frameLocator"), "Report header for FW Disabled chart did not match on report page.");
		LOGGER.info("FW disabled report is verified successfully");
						
		}
		//Test Case 7- Verify Edit/Update functionality.
		dashboardPage.clickByJavaScriptOnDashboardPage("FwEditOption");
		dashboardPage.clickOnElementsOfDashboardPage("FwEditButton");
		dashboardPage.scrollToDashboardPage("FwupdateWidget");
		dashboardPage.clickOnElementsOfDashboardPage("FwupdateWidget");
		LOGGER.info("Update functionality is verified.");
		
		softAssert.assertAll();
 }
}
    /*Feature 1055866: [Insights][PI_TBD][Firewall][Insights]Firewall Insights - FW Applications Widget
     * User Story 1101786: [Insights][PI_Sheff][Firewall][Insights][UI] UI for Firewall Application widget
     * User Story 1101768: [Insights][PI_Sheff][Firewall][Insights][Test] UI verification of Firewall Insights - FW Applications Widget.
     */
    
    @Test(priority = 85, groups = { "REGRESSION", "REGRESSION_CI", "ALL_CI", "ALL", "REGRESSIONDASHBOARD1" }, description = "TC:1098066,1098104")
   	public final void verifyFWApplicationFleet() throws Exception {
    	login("COMPANY_EMAIL_DEVICESECURE_WIDGET","COMPANY_PWS_DEVICESECURE_WIDGET");
       	PreConfiguredDashboardPage preDashPage = new PreConfiguredDashboardPage(PreDefinedActions.getDriver()).getInstance();
       	DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
   		SoftAssert softAssert = new SoftAssert();
   		ArrayList<String> FWAPPLICATION_LABEL_LIST = new ArrayList<>(Arrays.asList("Windows Defender", "Bitdefender Antivirus", "AVG Antivirus" , "McAfee Endpoint Security", "Trend Micro Maximum Security","Other","None detected"));
   		final String[] DASHBOARD_CHART_FW_APPLICATION_FLEET = {"Global.gridHeaders.deviceName","Global.gridHeaders.deviceSerialNumber",
				"gridHeaders.Hardware_Inventory.Details.allData.lastSignedinUser","Global.gridHeaders.lastSeen","Global.gridHeaders.complianceType",
				"gridHeaders.Device_Sec_Complaince.24_hrs_summary.Details.realtime_protection_status","gridHeaders.Device_Sec_Complaince.24_hrs_summary.Details.signature_status",
				"gridHeaders.Device_Sec_Complaince.24_hrs_summary.Details.totalTime_of_antivirus_status","gridHeaders.Device_Sec_Complaince.24_hrs_summary.Details.antivirus_definition_rel_date_timeframe",
				"gridHeaders.Device_Sec_Complaince.24_hrs_summary.Details.antivirus_definition_release_date","gridHeaders.Device_Sec_Complaince.24_hrs_summary.Details.antivirus_definition_name",
				"gridHeaders.Device_Sec_Complaince.hrSummary.policyValue","gridHeaders.Device_Sec_Complaince.24_hrs_summary.Details.antivirus_application_version",
				"gridHeaders.Device_Sec_Complaince.24_hrs_summary.Details.antivirus_definition_version","gridHeaders.Device_Sec_Complaince.hrSummary.no_of_not_complient_instances","Global.gridHeaders.deviceManufacturer",
				"Global.gridHeaders.deviceModel","Global.gridHeaders.operatingSystem","Global.gridHeaders.location_1","Global.gridHeaders.location_2","Global.gridHeaders.location_3","Global.gridHeaders.location_4"};
		
		if (toggleVerification(DashboardVariables.PRE_CONFIGURED_DASHBOARD, getCredentials(environment, "COMPANY_EMAIL_DEVICESECURE_WIDGET"))) 
		{
		Assert.assertTrue(preDashPage.setToPreConfiguredDashboard(LanguageCode,"flexible_dashboard.dashboard_name.fleet_security"),"Error occured in setting dashboard to default mode.");
		dashboardPage.scrollToDashboardPage("FWApplicationInFleetChartTitle");
		LOGGER.info("Scrolled down to FW application in fleet chart");
		dashboardPage.waitForPageLoaded();
		
		if(!(dashboardPage.verifyElementsOfDashboardPage("noDataFWAppInFleet"))) {
			// Test Case 1 (This Test case validates title of the chart.)
			softAssert.assertTrue(dashboardPage.verifyTitleofChartOfDashboardPage("FWApplicationInFleetChartTitle", dashboardPage.getTextLanguage(LanguageCode, "dashboard_service", "label.dashborad.global.devicesec.firewallSummaryInFleet")), "FW APPLICATION IN FLEET Widget title did not match.");
			LOGGER.info("FW Application In Fleet title text matched");
			// Test Case 2 (This test case validate whether chart is loaded or not.)
			Assert.assertFalse(dashboardPage.verifyChartisLoadedDynamic(LanguageCode, "FWApplicationChartHighlight", "fatalErrorFWAppInFleet", 5), "FW APPLICATION IN FLEET Widget title did not match.");
			LOGGER.info("Verified whether chart is loaded");

			// Test Case 3 (This test case validate whether chart has any data or not.)
			Assert.assertFalse(dashboardPage.verifyChartsHasNoDataOnDashBoardPageFlexi(LanguageCode, "noDataFWAppInFleet", "label.dashborad.global.devicesec.firewallSummaryInFleet"), "FW APPLICATION IN FLEET Widget Chart does not have any data.");
			LOGGER.info("Verified  chart has  data");
					
			
			dashboardPage.scrollToDashboardPage("scrollAVWindowsDefender");			
			dashboardPage.listMouseHoverOfDashboardPage("FWAppFleetDonutChart");
			LOGGER.info("MouseHoverApp donut list");		
			
			//dashboardPage.scrollToDashboardPage("scrollAVWindowsDefender");
			softAssert.assertTrue(dashboardPage.headerTextVerificationOnReportPageFrameFleetSecurity(LanguageCode,"FWAppFleetDonutChart", "FWAppheaderListLocatorOnReportPage", DASHBOARD_CHART_FW_APPLICATION_FLEET, "frameLocator"), "Report header for FW  Application in Fleet chart did not match on report page.");
			LOGGER.info("FW application report is verified successfully");
			}
		//softAssert.assertTrue(dashboardPage.headerTextVerificationOnReportPageFrameDonutSwellChartFlexi(LanguageCode, "FWApplicationLegendLabel", "FWAppheaderListLocatorOnReportPage", DASHBOARD_CHART_FW_APPLICATION_FLEET, "frameLocator", "windows11DeviceCompatibilityChartVisibility", "windows11DeviceCompatibilityChartVisibility","windows11DeviceCompatibilityDropdown"), "Report header for windows 11 device compatibility chart did not match on report page.");
		
		}
		//Test Case 7- Verify Edit/Update functionality.
		dashboardPage.clickOnElementsOfDashboardPage("FWEditOption");
		
		softAssert.assertTrue(dashboardPage.headerTextVerificationOnReportPageFrame(LanguageCode, "FWViewFullReport", "headerListLocatorOnReportPage", DASHBOARD_CHART_FW_APPLICATION_FLEET, "frameLocator"), "Full Report header for FW Disabled chart did not match on report page.");
		LOGGER.info("FW application FULL report is verified successfully");
		
		dashboardPage.clickOnElementsOfDashboardPage("FWEditOption");
		dashboardPage.clickOnElementsOfDashboardPage("FWSaveChartAsImage");
		sleeper(1000);
		dashboardPage.clickOnElementsOfDashboardPage("FWEditOption");
		dashboardPage.clickOnElementsOfDashboardPage("FWEditButton");
		dashboardPage.scrollToDashboardPage("updateWidget");
		dashboardPage.clickOnElementsOfDashboardPage("updateWidget");
		LOGGER.info("Update functionality is verified.");
		softAssert.assertAll();
    }
    
    
	/**
	 * DashBoard - SoftwareUpdateV2 [Feature 1052023]
	 * 
	 * @throws Exception
	 */
	@Test(priority = 86, groups = {"REGRESSION", "REGRESSION_CI", "ALL_CI", "ALL",
			"REGRESSIONDASHBOARD1" }, description = "TC:1068895")
	public final void verifySoftwareUpdatesDashboardWidget() throws Exception {
		String[] detailsColumnNames = { "Last Signed-In User", "Serial Number", "Device Name", "Update Count",
				"Update Classification", "Operating System", "Operating System Release", "Last Seen", "Device Type",
				"Device Manufacturer", "Device Model", "Manufacture Date", "Location Level 1", "Location Level 2",
				"Location Level 3", "Location Level 4" };

		String[] expectedArrayUpdateClassification = { "Application", "Connectors", "Critical Updates",
				"Definition Updates", "Developer Kits", "Feature Packs", "Guidance", "Security Updates",
				"Service Packs", "Tools", "Update Rollups", "Updates", "Upgrades", "Drivers" };

		login("MSP_NEW_USER_EMAIL_FLEETCHART_US", "MSP_NEW_USER_PASSWORD_FLEETCHART_US");
		PreConfiguredDashboardPage preDashPage = new PreConfiguredDashboardPage(PreDefinedActions.getDriver())
				.getInstance();
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();

		List<String> expectedVisualiationlist = new ArrayList<>();
		expectedVisualiationlist.add("Devices With Available Critical Updates");
		expectedVisualiationlist.add("Devices With Available Security Updates");
		expectedVisualiationlist.add("Devices With Available Updates by Classification");

		String category = dashboardPage.getTextLanguage(LanguageCode, "MPI-Reporting-report-html-view-UI-JSON",
				"software");
		String subCategory = "Software UpdatesV2";
		String option = dashboardPage.getTextLanguage(LanguageCode, "MPI-Reporting-LHreports_service",
				"label.label.report_option_windowsUpdates");

		preDashPage.waitForPageLoaded();
		preDashPage.setToPreConfiguredDashboard(LanguageCode, "catalog.type.default");

		softAssert.assertTrue(dashboardPage.verifySoftwareUpdateWidgetsVisualization(LanguageCode, category,
				subCategory, option, expectedVisualiationlist), "Visualtion List does not match");

		dashboardPage.clickByJavaScriptOnDashboardPage("criticalUpdatesRadioButton");
		dashboardPage.scrollToDashboardPage("widgetTitleTextbox");
		sleeper(1000);
		softAssert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("kpiFormatLabel"),
				"KPI Format label not present");
		softAssert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("percentageFormatLabel"),
				"Percentage Radiobutton not present");
		softAssert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("numberFormatLabel"),
				"Number Format Radiobutton not present");
		softAssert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("widgetTitleTextbox"),
				"Widget Title Textbox not present");

		dashboardPage.scrollToDashboardPage("updateByClassificationRadioButton");
		dashboardPage.clickByJavaScriptOnDashboardPage("updateByClassificationRadioButton");
		dashboardPage.scrollToDashboardPage("widgetTitleTextbox");
		sleeper(1000);
		softAssert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("addfilterLink"),
				"Add Filter Link not present");
		softAssert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("widgetTitleTextbox"),
				"Widget Title Textbox not present");
		sleeper(1000);

		dashboardPage.clickOnElementsOfDashboardPage("addfilterLink");
		String[] filtercriteriaField = { "Device Model", "Operating System Release", "Update Classification" };
		softAssert
				.assertTrue(
						dashboardPage
								.selectFilterOptionForDashBoardPage("Update Classification", LanguageCode,
										"addFilterFieldDropdown", "addFilterFieldDropdownOptionList")
								.containsAll(Arrays.asList(filtercriteriaField)),
						"Filter Criteria field options mismatched");
		sleeper(2000);

		dashboardPage.clickOnElementsOfDashboardPage("addFilterValueDropdown");
		sleeper(2000);

		scrollToBottom();
		List<String> lstactualUpdateClassification = dashboardPage
				.getTextAsListOnDashBoardPage("addFilterValueDropdownOptionList");

		softAssert.assertTrue(
				Arrays.asList(expectedArrayUpdateClassification).containsAll(lstactualUpdateClassification),
				"Update Classification options in filter did not match");

		dashboardPage.pressKey(Keys.ESCAPE);
		dashboardPage.clickOnElementsOfDashboardPage("deleteFilterIcon");

		dashboardPage.scrollToDashboardPage("securityUpdatesRadioButton");
		dashboardPage.clickByJavaScriptOnDashboardPage("securityUpdatesRadioButton");
		dashboardPage.scrollToDashboardPage("widgetTitleTextbox");
		sleeper(1000);
		softAssert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("kpiFormatLabel"),
				"KPI Format label not present");
		softAssert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("percentageFormatLabel"),
				"Percentage label not present");
		softAssert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("numberFormatLabel"),
				"Number Format label not present");
		softAssert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("widgetTitleTextbox"),
				"Widget Title Textbox not present");

		softAssert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("addButton"), "Add Button not present");
		softAssert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("discardButton"),
				"Discard button not present");

		gotoDashboardTab();

		preDashPage.setToPreConfiguredDashboard(LanguageCode, "catalog.type.default");

		LOGGER.info("Verify Device with Critical Updates Widget ");

		dashboardPage.scrollToDashboardPage("criticalUpdatesWidgetTitleLabel");
		softAssert.assertEquals(dashboardPage.getTextOfDashboardPage("criticalUpdatesWidgetTitleLabel").trim(),
				"Devices with Available Windows Critical Updates",
				"Primary text did not match for Critical Updates widget");
		softAssert.assertEquals(dashboardPage.getTextOfDashboardPage("criticalUpdatesWidgetSubTitleLabel").trim(),
				"Make these critical updates as soon as possible.",
				"Secondary text did not match for Critical Updates widget");
		softAssert.assertTrue(
				dashboardPage.getTextOfDashboardPage("criticalUpdatesWidgetCountLabel").trim().matches("[0-9]+"),
				"Critical updates device count is no number format");
		softAssert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("criticalUpdatesWidgetDetailsLink"),
				"Critical updates Details link not present");
		softAssert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("criticalUpdatesWidgetEllipsesButton"),
				"Critical updates Ellipses button not present");

		dashboardPage.clickByJavaScriptOnDashboardPage("criticalUpdatesWidgetDetailsLink");
		waitForPageLoaded();
		dashboardPage.switchToDifferentTabOfDashboardPage();
		waitForPageLoaded();

		softAssert.assertEquals(dashboardPage.getTextAsListOnDashBoardPage("windowsUpdatesDetailsGridHeader"),
				Arrays.asList(detailsColumnNames), "Column Name did not match");
		dashboardPage.switchToPreviousTabOfDashboardPage();

		LOGGER.info("Verify Device with Security Updates Widget ");
		dashboardPage.scrollToDashboardPage("securityUpdatesWidgetTitleLabel");
		softAssert.assertEquals(dashboardPage.getTextOfDashboardPage("securityUpdatesWidgetTitleLabel").trim(),
				"Devices with Available Windows Security Updates",
				"Primary text did not match for Security Updates widget");
		softAssert.assertEquals(dashboardPage.getTextOfDashboardPage("securityUpdatesWidgetSubTitleLabel").trim(),
				"Make these Security Updates as soon as possible",
				"Secondary text did not match for Security Updates widget");
		softAssert.assertTrue(
				dashboardPage.getTextOfDashboardPage("securityUpdatesWidgetCountLabel").trim().matches("[0-9]+"),
				"Security updates device count is no number format");
		softAssert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("securityUpdatesWidgetDetailsLink"),
				"Security updates Details link not present");
		softAssert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("securityUpdatesWidgetEllipsesButton"),
				"Critical updates Ellipses button not present");

		dashboardPage.clickByJavaScriptOnDashboardPage("securityUpdatesWidgetDetailsLink");
		waitForPageLoaded();
		dashboardPage.switchToDifferentTabOfDashboardPage();
		waitForPageLoaded();

		softAssert.assertEquals(dashboardPage.getTextAsListOnDashBoardPage("windowsUpdatesDetailsGridHeader"),
				Arrays.asList(detailsColumnNames), "Column Name did not match");
		dashboardPage.switchToPreviousTabOfDashboardPage();

		LOGGER.info("Verify Windows Summary Widget on Dashboard");
		dashboardPage.scrollToDashboardPage("windowsUpdatesSummaryWidget");
		softAssert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("windowsUpdatesSummaryWidget"),
				"Windows Summary widget not present");

		dashboardPage.clickOnElementsOfDashboardPage("barGraph");
		sleeper(2000);
		waitForPageLoaded();
		dashboardPage.switchToDifferentTabOfDashboardPage();
		waitForPageLoaded();
		List<String> expectedReportTabs = new ArrayList<>();
		expectedReportTabs.add(getTextLanguage(LanguageCode, "daas_reports_ui", "Global.graphHeaders.summary"));
		expectedReportTabs.add(getTextLanguage(LanguageCode, "daas_reports_ui", "by_update"));
		expectedReportTabs.add(getTextLanguage(LanguageCode, "daas_reports_ui", "details"));
		softAssert.assertTrue(
				dashboardPage.getTextAsListOnDashBoardPage("softwareNavigationTab").containsAll(expectedReportTabs),
				"Navigation tabs mismatched");
		dashboardPage.switchToPreviousTabOfDashboardPage();
		LOGGER.info("Widget is present successfully");
		softAssert.assertAll();
	}

	
	
	/*Feature 998064: [Insights][PI_Bristol][Antivirus][Action_Remediate]Remediating Disabled AV and Outdated AV Software Signature Definition.
     */
    
    @Test(priority = 87, groups = { "REGRESSION", "REGRESSION_CI", "ALL_CI", "ALL", "REGRESSIONDASHBOARD1" }, description = "TC:1098066,1098104")
   	public final void verifyAVRemediateWidgetOnFleetSecurityDashboard() throws Exception {
       	login("COMPANY_EMAIL_DEVICESECURE_WIDGET","COMPANY_PWS_DEVICESECURE_WIDGET");
       	PreConfiguredDashboardPage preDashPage = new PreConfiguredDashboardPage(PreDefinedActions.getDriver()).getInstance();
       	DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
   		SoftAssert softAssert = new SoftAssert();
   		
		if (toggleVerification(DashboardVariables.PRE_CONFIGURED_DASHBOARD, getCredentials(environment, "COMPANY_EMAIL_DEVICESECURE_WIDGET"))) 
		{
		Assert.assertTrue(preDashPage.setToPreConfiguredDashboard(LanguageCode,"flexible_dashboard.dashboard_name.fleet_security"),"Error occured in setting dashboard to default mode.");
		dashboardPage.waitForPageLoaded();
		}
		Assert.assertTrue(dashboardPage.matchTextOfDashboardPage("deviceSecurityScoreWidgetTitle", getTextLanguage(LanguageCode, "daas_ui", "device-security-score")),"Device Security widget title is incorrect");
		Assert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("deviceSecurityChart"),"Device security  chart is not present");
		Assert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("deviceSecurityTable"),"Device security table is not present");
		Assert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("securityRecommendationTitle"),"Security Recommendation title is not correct");
		Assert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("securityRecommendationChart"),"Security Recommendation chart is not correct");
		
		if(!(dashboardPage.verifyElementsOfDashboardPage("noDataSecurityWidgetOpenAction"))) {
			//Test case 1: Check data in Open action
			List<String> openSecRecommandedAction = dashboardPage.getallTextOfDashboardPage("openActionAppListName");
			for (String actionList:openSecRecommandedAction) {
				
				if (actionList.equals(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "enable-antivirus-windows-defender"))) 
				{
					
					dashboardPage.clickOnElementsOfDashboardPage("enableDetailsButton");
					sleeper(4000);
					LOGGER.info("Open action-Enable AV text is verified successfully.");
					gotoDashboardTab();
					waitForPageLoaded();
				}			
					else if (actionList.equals (dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "security.update_signature_wind_defender"))){
					dashboardPage.clickOnElementsOfDashboardPage("updateAVDetailsButton");
					sleeper(4000);
					LOGGER.info("Open action-Update Signature text is verified successfully.");
					gotoDashboardTab();
				}
				}		
		}
		//Test case 2: Check data in In-Progress action
	      if(!(dashboardPage.verifyElementsOfDashboardPage("noDataOnRecommandationSection"))) {
				List<String> RecommandedAction = dashboardPage.getallTextOfDashboardPage("inProgressAppListNameDefaultDash");
				for (String actionList:RecommandedAction) {
					
					if (actionList.equals(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "enable-antivirus-windows-defender"))) 
					{
						
						dashboardPage.clickOnElementsOfDashboardPage("inProgressEnableAVDetailsButton");
						sleeper(4000);
						LOGGER.info("InProgress action-Enable AV text is verified successfully..");
						gotoDashboardTab();
						waitForPageLoaded();
					}			
					else if (actionList.equals (dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "security.update_signature_wind_defender")))
					{
						dashboardPage.clickOnElementsOfDashboardPage("inProgressUpdateDetailButton");
						sleeper(4000);
						LOGGER.info("InProgress action-Update Signature text is verified successfully..");
						gotoDashboardTab();
					}
					}		
			}
	    //Test case 3: Check data in Completed action
	      if(!(dashboardPage.verifyElementsOfDashboardPage("noDataOnRecommandationSection"))) {
				List<String> RecommandedAction = dashboardPage.getallTextOfDashboardPage("completedAppListDefaultDash");
				for (String actionList:RecommandedAction) {
					
					if (actionList.equals(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "enable-antivirus-windows-defender"))) 
					{
						
						dashboardPage.clickOnElementsOfDashboardPage("completedEnableAVDetailsButton");
						sleeper(4000);
						LOGGER.info("Completed action-Enable AV text is verified successfully.");
						gotoDashboardTab();
						waitForPageLoaded();
						dashboardPage.clickOnElementsOfDashboardPage("completedEnableAVDismissedButton");
						sleeper(2000);
					}			
					else if (actionList.equals (dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "security.update_signature_wind_defender")))
					{
						dashboardPage.clickOnElementsOfDashboardPage("completedUpdateDetailButton");
						sleeper(4000);
						LOGGER.info("Completed action-Update Signature text is verified successfully.");
						gotoDashboardTab();
						waitForPageLoaded();
						dashboardPage.clickOnElementsOfDashboardPage("completedUpdateDismissedButton");
						sleeper(2000);
					}
					}		
			}
	    }	
		
		
    
	/**
	 Feature 1110262: Windows 11 Readiness: Standardize chart category names, legends and labels across HTML and PDF report versions
	 * This test verifies the windows 11 Upgrade KPI
	 */
	@Test(priority = 88 ,  groups = {"REGRESSIONDASHBOARD2"} )
	public final void verifyWindows11UpgradeStatusWidgetV2() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		//login("MSP_ADMIN_US_EMAIl_REPORTS_ALPHA", "MSP_ADMIN_US_PASSWORD_REPORTS");
		login("MSP_ADMIN_US_EMAIl_REPORTS", "MSP_ADMIN_US_PASSWORD_REPORTS");
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();

		if (!toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, "MSP_ADMIN_US_EMAIl_REPORTS"))){
			Assert.assertTrue(setToDefaultDashboard(LanguageCode),"Error occured in setting dashboard to default mode.");
		}else{
			Assert.assertTrue(setToDefaultDashboardGlobalFilter(LanguageCode),"Error occured in setting dashboard to default mode.");
		}

		dashboardPage.scrollToDashboardPage("windows11UpgradeStatusTitleV2");
		LOGGER.info("Scrolled down to Windows11 V2 Upgrade status chart");
		dashboardPage.waitForPageLoaded();
		softAssert.assertTrue(dashboardPage.verifyTitleofChartOfDashboardPage("windows11UpgradeStatusTitleV2", dashboardPage.getTextLanguage(LanguageCode, "dashboard_service", "label.dashborad.global.windows.11.upgrade.status")), "Windows 11 upgrade status title did not match.");

		//Test case 1 : Verify Running windows
		dashboardPage.waitForElementsOfDashboardPage("runningWindows11Title");
		softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("runningWindows11Title").equals(getTextLanguage(LanguageCode, "daas_reports_ui", "hardware.win11.running_windows_11")), "Upgrade to Windows 11 title does not match.");
		dashboardPage.clickByJavaScriptOnDashboardPage("runningWindows11Title");
		dashboardPage.switchToDifferentTabOfDashboardPage();
		dashboardPage.waitForElementsOfDashboardPage("moreDetailsLink");
		dashboardPage.clickOnElementsOfDashboardPage("moreDetailsLink");
		softAssert.assertTrue(dashboardPage.verifyTextContainsOnElementsOfDashboardPage("filterTextWindows11UpgradeStatus", dashboardPage.getTextLanguage(LanguageCode, "daas_reports_ui", "hardware.win11.running_windows_11")), "Filter of upgrade to windows 11 is not present");

		//Test case 2 : Verify Ready to update to windows 11 title
		dashboardPage.switchBackToPreviousTab();
		dashboardPage.waitForPageLoaded();
		dashboardPage.waitForElementsOfDashboardPage("readyToUpdateWin11Title");
		softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("readyToUpdateWin11Title").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_reports_ui", "hardware.win11.ready_to_update")), "Devices not update to Windows 11 title does not match.");
		dashboardPage.clickByJavaScriptOnDashboardPage("readyToUpdateWin11Title");
		dashboardPage.switchToDifferentTabOfDashboardPage();
		dashboardPage.waitForElementsOfDashboardPage("moreDetailsLink");
		dashboardPage.clickOnElementsOfDashboardPage("moreDetailsLink");
		softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("filterTextWindows11UpgradeStatus").toLowerCase().contains(dashboardPage.getTextLanguage(LanguageCode, "daas_reports_ui", "hardware.win11.ready_to_update").toLowerCase()), "Filter of device not updated is not present");
		//Test case 3 : Verify replace device win 11 title
		dashboardPage.switchBackToPreviousTab();
		dashboardPage.waitForPageLoaded();
		dashboardPage.waitForElementsOfDashboardPage("replaceDeviceWithWin11Title");
		softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("replaceDeviceWithWin11Title").equals(getTextLanguage(LanguageCode, "daas_reports_ui", "hardware.win11.replace_device")), "Not compatible to Windows 11 title does not match.");
		dashboardPage.clickByJavaScriptOnDashboardPage("replaceDeviceWithWin11Title");
		dashboardPage.switchToDifferentTabOfDashboardPage();
		dashboardPage.waitForElementsOfDashboardPage("moreDetailsLink");
		dashboardPage.clickOnElementsOfDashboardPage("moreDetailsLink");
		softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("filterTextWindows11UpgradeStatus").toLowerCase().contains(dashboardPage.getTextLanguage(LanguageCode, "daas_reports_ui", "hardware.win11.replace_device").toLowerCase()), "Filter of not compatible on windows 11 is not present");

		//Test case 3 : Verify Updatable Performance Risk win 11 title
		dashboardPage.switchBackToPreviousTab();
		dashboardPage.waitForPageLoaded();
		dashboardPage.waitForElementsOfDashboardPage("updatePerfromanceRiskWin11Title");
		softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("updatePerfromanceRiskWin11Title").equals(getTextLanguage(LanguageCode, "daas_reports_ui", "hardware.win11.updatable_performance_risk")), "Not compatible to Windows 11 title does not match.");
		dashboardPage.clickByJavaScriptOnDashboardPage("updatePerfromanceRiskWin11Title");
		dashboardPage.switchToDifferentTabOfDashboardPage();
		dashboardPage.waitForElementsOfDashboardPage("moreDetailsLink");
		dashboardPage.clickOnElementsOfDashboardPage("moreDetailsLink");
		softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("filterTextWindows11UpgradeStatus").toLowerCase().contains(dashboardPage.getTextLanguage(LanguageCode, "daas_reports_ui", "hardware.win11.updatable_performance_risk").toLowerCase()), "Filter of not compatible on windows 11 is not present");
		//Test case 4 : Verify missing information title
		dashboardPage.switchBackToPreviousTab();
		dashboardPage.waitForPageLoaded();
		dashboardPage.waitForElementsOfDashboardPage("missingInformationTitleV2");
		softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("missingInformationTitleV2").equals(getTextLanguage(LanguageCode, "daas_reports_ui", "hardware.win11.missing_information")), "Missing information title does not match.");
		dashboardPage.clickByJavaScriptOnDashboardPage("missingInformationTitleV2");
		dashboardPage.switchToDifferentTabOfDashboardPage();
		dashboardPage.waitForElementsOfDashboardPage("moreDetailsLink");
		dashboardPage.clickOnElementsOfDashboardPage("moreDetailsLink");
		softAssert.assertTrue(dashboardPage.verifyTextContainsOnElementsOfDashboardPage("filterTextWindows11UpgradeStatus", dashboardPage.getTextLanguage(LanguageCode, "daas_reports_ui", "hardware.win11.missing_information")), "Filter of missing information is not present");

		//Test case 5 : Verify View Full report
		dashboardPage.switchBackToPreviousTab();
		dashboardPage.waitForPageLoaded();
		dashboardPage.waitForElementsOfDashboardPage("viewFullReportWin11 ");
		softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("viewFullReportWin11").equals(getTextLanguage(LanguageCode, "daas_reports_ui", "flexible_dashboard.widget.action.view_full_report")), "Not compatible to Windows 11 title does not match.");
		dashboardPage.clickByJavaScriptOnDashboardPage("viewFullReportWin11Widget");
		dashboardPage.switchToDifferentTabOfDashboardPage();
		dashboardPage.waitForElementsOfDashboardPage("moreDetailsLink");
		dashboardPage.clickOnElementsOfDashboardPage("moreDetailsLink");
		softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("win11ReportTitle").toLowerCase().contains(dashboardPage.getTextLanguage(LanguageCode, "daas_reports_ui", "graphHeaders.Hardware_Windows11_Readiness_Assessment.details.windows11_upgrade_status").toLowerCase()), "Filter of not compatible on windows 11 is not present");
		softAssert.assertAll();

		LOGGER.info("Test case verifyWindows11UpgradeStatusWidget passed successfully.");
	}

	@Test(priority = 89 ,  groups = {"REGRESSIONDASHBOARD2"} )
	public final void verifyWindows11UpgradeStatusWidgetV2ForMatchWidgetandDetailsCount() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		//login("MSP_ADMIN_US_EMAIl_REPORTS", "MSP_ADMIN_US_PASSWORD_DASHBOARD");
		login("MSP_ADMIN_US_EMAIl_REPORTS", "MSP_ADMIN_US_PASSWORD_REPORTS");

		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		List<String> expectedList = new ArrayList<String>(Arrays.asList(getTextLanguage(LanguageCode, "daas_reports_ui", "Global.gridHeaders.companyName"), getTextLanguage(LanguageCode, "daas_reports_ui", "gridHeaders.Hardware_Inventory.Details.allData.lastSignedinUser"), getTextLanguage(LanguageCode, "daas_reports_ui", "Global.gridHeaders.deviceName"), getTextLanguage(LanguageCode, "daas_reports_ui", "Global.gridHeaders.serialNumber"),
				 getTextLanguage(LanguageCode, "daas_reports_ui", "Global.gridHeaders.deviceType"), getTextLanguage(LanguageCode, "daas_reports_ui", "Global.gridHeaders.deviceManufacturer"),getTextLanguage(LanguageCode, "daas_reports_ui", "device_age") ,getTextLanguage(LanguageCode, "daas_reports_ui", "Global.gridHeaders.deviceModel"), getTextLanguage(LanguageCode, "daas_reports_ui", "hardware.win11.deviceos"),
				getTextLanguage(LanguageCode, "daas_reports_ui", "gridHeaders.Software_Update.WinUpdate.Details.OS_release"), getTextLanguage(LanguageCode, "daas_reports_ui", "hardware.win11.compatibilityStatus"),getTextLanguage(LanguageCode, "daas_reports_ui","gridHeaders.Hardware_Replacement.Details.recommendation"), getTextLanguage(LanguageCode, "daas_reports_ui", "gridHeaders.Windows_Migration.details.cpu"), getTextLanguage(LanguageCode, "daas_reports_ui", "gridHeaders.Windows_Migration.details.cpuspeedmhz"),
				getTextLanguage(LanguageCode, "daas_reports_ui", "hardware.win11.cpu_cores"), getTextLanguage(LanguageCode, "daas_reports_ui", "hardware.win11.hdType"), getTextLanguage(LanguageCode, "daas_reports_ui", "hardware.win11.hdSizegb"), getTextLanguage(LanguageCode, "daas_reports_ui", "Global.gridHeaders.freespace"), getTextLanguage(LanguageCode, "daas_reports_ui", "hardware.win11.installedMemory"),
				getTextLanguage(LanguageCode, "daas_reports_ui", "hardware.win11.installedGraphics"), getTextLanguage(LanguageCode, "daas_reports_ui", "hardware.win11.currentTpm"), getTextLanguage(LanguageCode, "daas_reports_ui", "Global.graphHeaders.secureboot"), getTextLanguage(LanguageCode, "daas_reports_ui", "gridHeaders.Incident_Widget.Details.warStatus"),
				getTextLanguage(LanguageCode, "daas_reports_ui", "gridHeaders.Windows_Migration.warrantyExpirationDate"), getTextLanguage(LanguageCode, "daas_reports_ui", "gridHeaders.Windows_Migration.details.devicePerfomanceRating"), getTextLanguage(LanguageCode, "daas_reports_ui", "gridHeaders.Hardware_Health.MonythlySummary.performance_metrics"), getTextLanguage(LanguageCode, "daas_reports_ui", "hardware.win11.performanceDetails"), getTextLanguage(LanguageCode, "daas_reports_ui", "Global.gridHeaders.lastSeen"),getTextLanguage(LanguageCode, "daas_reports_ui", "gridHeaders.Hardware_Warranty.enrolled"),
				getTextLanguage(LanguageCode, "daas_reports_ui", "Global.gridHeaders.location1"), getTextLanguage(LanguageCode, "daas_reports_ui", "Global.gridHeaders.location2"), getTextLanguage(LanguageCode, "daas_reports_ui", "Global.gridHeaders.location3"), getTextLanguage(LanguageCode, "daas_reports_ui", "Global.gridHeaders.location4")));

		if (!toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, "MSP_ADMIN_US_EMAIl_REPORTS"))){
			Assert.assertTrue(setToDefaultDashboard(LanguageCode),"Error occured in setting dashboard to default mode.");
		}else{
			Assert.assertTrue(setToDefaultDashboardGlobalFilter(LanguageCode),"Error occured in setting dashboard to default mode.");
		}

		dashboardPage.scrollToDashboardPage("windows11UpgradeStatusTitleV2");
		LOGGER.info("Scrolled down to Windows11 Upgrade status chart");
		dashboardPage.waitForPageLoaded();
		softAssert.assertTrue(dashboardPage.verifyTitleofChartOfDashboardPage("windows11UpgradeStatusTitleV2", dashboardPage.getTextLanguage(LanguageCode, "dashboard_service", "label.dashborad.global.windows.11.upgrade.status")), "Windows 11 upgrade status title dod not match.");

		//Test case 1 : Verify updated to Windows11 title
		dashboardPage.waitForElementsOfDashboardPage("runningWindows11Title");
		softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("runningWindows11Title").equals(getTextLanguage(LanguageCode, "daas_reports_ui", "hardware.win11.running_windows_11")), "Upgrade to Windows 11 title does not match.");
		dashboardPage.clickByJavaScriptOnDashboardPage("runningWindows11Title");
		dashboardPage.switchToDifferentTabOfDashboardPage();
		dashboardPage.waitForElementsOfDashboardPage("moreDetailsLink");
		dashboardPage.clickOnElementsOfDashboardPage("moreDetailsLink");
		softAssert.assertTrue(dashboardPage.verifyTextContainsOnElementsOfDashboardPage("filterTextWindows11UpgradeStatus", dashboardPage.getTextLanguage(LanguageCode, "daas_reports_ui", "hardware.win11.running_windows_11")), "Filter of upgrade to windows 11 is not present");
		dashboardPage.switchBackToPreviousTab();
		softAssert.assertTrue(verifyDetailsGridForKPIWidgetsOnDashboard(LanguageCode,"deviceCountUpdatedToWin11V2","runningWindows11Title","win11",expectedList),"The count or columns did not matched");
		LOGGER.info("Switched to Previous tab");

		//Test case 2 : Verify Device not updated to windows 11 title
		dashboardPage.waitForPageLoaded();
		dashboardPage.waitForElementsOfDashboardPage("readyToUpdateWin11Title");
		softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("readyToUpdateWin11Title").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_reports_ui", "hardware.win11.ready_to_update")), "Devices not update to Windows 11 title does not match.");
		dashboardPage.clickByJavaScriptOnDashboardPage("readyToUpdateWin11Title");
		dashboardPage.switchToDifferentTabOfDashboardPage();
		dashboardPage.waitForElementsOfDashboardPage("moreDetailsLink");
		dashboardPage.clickOnElementsOfDashboardPage("moreDetailsLink");
		softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("filterTextWindows11UpgradeStatus").toLowerCase().contains(dashboardPage.getTextLanguage(LanguageCode, "daas_reports_ui", "hardware.win11.ready_to_update").toLowerCase()), "Filter of device not updated is not present");
		dashboardPage.switchBackToPreviousTab();
		softAssert.assertTrue(verifyDetailsGridForKPIWidgetsOnDashboard(LanguageCode,"deviceCountDeviceNotUpdatedToWin11V2","readyToUpdateWin11Title","win11",expectedList),"The count or columns did not matched");
		LOGGER.info("Switched to Previous tab");

		//Test case 3 : Verify Not compatible with win 11 title
		dashboardPage.waitForPageLoaded();
		dashboardPage.waitForElementsOfDashboardPage("replaceDeviceWithWin11Title");
		softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("replaceDeviceWithWin11Title").equals(getTextLanguage(LanguageCode, "daas_reports_ui", "hardware.win11.replace_device")), "Not compatible to Windows 11 title does not match.");
		dashboardPage.clickByJavaScriptOnDashboardPage("replaceDeviceWithWin11Title");
		dashboardPage.switchToDifferentTabOfDashboardPage();
		dashboardPage.waitForElementsOfDashboardPage("moreDetailsLink");
		dashboardPage.clickOnElementsOfDashboardPage("moreDetailsLink");
		softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("filterTextWindows11UpgradeStatus").toLowerCase().contains(dashboardPage.getTextLanguage(LanguageCode, "daas_reports_ui", "hardware.win11.replace_device").toLowerCase()), "Filter of not compatible on windows 11 is not present");
		dashboardPage.switchBackToPreviousTab();
		softAssert.assertTrue(verifyDetailsGridForKPIWidgetsOnDashboard(LanguageCode,"deviceCountDeviceNotCompatibleToWin11V2","replaceDeviceWithWin11Title","win11",expectedList),"The count or columns did not matched");
		LOGGER.info("Switched to Previous tab");

		//Test case 4 : Verify missing information title
		dashboardPage.waitForPageLoaded();
		dashboardPage.waitForElementsOfDashboardPage("missingInformationTitleV2");
		softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("missingInformationTitleV2").equals(getTextLanguage(LanguageCode, "daas_reports_ui", "hardware.win11.missing_information")), "Missing information title does not match.");
		dashboardPage.clickByJavaScriptOnDashboardPage("missingInformationTitleV2");
		dashboardPage.switchToDifferentTabOfDashboardPage();
		dashboardPage.waitForElementsOfDashboardPage("moreDetailsLink");
		dashboardPage.clickOnElementsOfDashboardPage("moreDetailsLink");
		softAssert.assertTrue(dashboardPage.verifyTextContainsOnElementsOfDashboardPage("filterTextWindows11UpgradeStatus", dashboardPage.getTextLanguage(LanguageCode, "daas_reports_ui", "hardware.win11.missing_information")), "Filter of missing information is not present");
		dashboardPage.switchBackToPreviousTab();
		softAssert.assertTrue(verifyDetailsGridForKPIWidgetsOnDashboard(LanguageCode,"deviceCountMissingInformationWin11V2","missingInformationTitleV2","win11",expectedList),"The count or columns did not matched");
		LOGGER.info("Switched to Previous tab");

		//Test case 5 : Verify Updatable Performance Risk win 11 title
		dashboardPage.waitForPageLoaded();
		dashboardPage.waitForElementsOfDashboardPage("updatePerfromanceRiskWin11Title");
		softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("updatePerfromanceRiskWin11Title").equals(getTextLanguage(LanguageCode, "daas_reports_ui", "hardware.win11.updatable_performance_risk")), "Not compatible to Windows 11 title does not match.");
		dashboardPage.clickByJavaScriptOnDashboardPage("updatePerfromanceRiskWin11Title");
		dashboardPage.switchToDifferentTabOfDashboardPage();
		dashboardPage.waitForElementsOfDashboardPage("moreDetailsLink");
		dashboardPage.clickOnElementsOfDashboardPage("moreDetailsLink");
		softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("filterTextWindows11UpgradeStatus").toLowerCase().contains(dashboardPage.getTextLanguage(LanguageCode, "daas_reports_ui", "hardware.win11.updatable_performance_risk").toLowerCase()), "Filter of not compatible on windows 11 is not present");
		dashboardPage.switchBackToPreviousTab();
		softAssert.assertTrue(verifyDetailsGridForKPIWidgetsOnDashboard(LanguageCode,"deviceCountDeviceupdatablePerformanceRiskToWin11V2","updatePerfromanceRiskWin11Title","win11",expectedList),"The count or columns did not matched");
		LOGGER.info("Switched to Previous tab");


		softAssert.assertAll();

		LOGGER.info("Test case verifyWindows11UpgradeStatusWidgetForMatchWidgetandDetailsCount passed successfully.");
	}

	/*Feature 998064: [Insights][PI_Bristol][Antivirus][Action_Remediate]Remediating Disabled AV and Outdated AV Software Signature Definition.
     User Story 1110991: [Insights][PI_Bristol][Antivirus][Action_Remediate] UI Automation for Default dashboard Remediation Disabled AV and Outdated Signature.*/
    
    @Test(priority = 90, groups = { "REGRESSION", "REGRESSION_CI", "ALL_CI", "ALL", "REGRESSIONDASHBOARD1" }, description = "TC:1098066,1098104")
   	public final void verifyAVRemediateWidgetOnDefaultDashboard() throws Exception {
       	login("COMPANY_EMAIL_DEVICESECURE_WIDGET","COMPANY_PWS_DEVICESECURE_WIDGET");
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();

		if (!toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, "COMPANY_EMAIL_DEVICESECURE_WIDGET"))){
			Assert.assertTrue(setToDefaultDashboard(LanguageCode),"Error occured in setting dashboard to default mode.");
		}else{
			Assert.assertTrue(setToDefaultDashboardGlobalFilter(LanguageCode),"Error occured in setting dashboard to default mode.");
		}
        dashboardPage.waitForPageLoaded();
				   
      Assert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("recommendationTitle")," Recommendation title is not correct");
      dashboardPage.scrollToDashboardPage("recommendationTitle");
      
     // Test case 1: Check data in Open action
      if(!(dashboardPage.verifyElementsOfDashboardPage("noDataOnRecommandationSection"))) {
			List<String> RecommandedAction = dashboardPage.getallTextOfDashboardPage("openActionAppListNameDefaultDash");
			for (String actionList:RecommandedAction) {
				
				if (actionList.equals(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "enable-antivirus-windows-defender"))) 
				{
					
					dashboardPage.clickOnElementsOfDashboardPage("openEnableAvDetailsButton");
					sleeper(4000);
					LOGGER.info("Open action-Enable AV text is verified successfully.");
					gotoDashboardTab();
					waitForPageLoaded();
				}			
				else if (actionList.equals (dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "security.update_signature_wind_defender")))
				{
					dashboardPage.clickOnElementsOfDashboardPage("openUpdateAVDetailsButton");
					sleeper(4000);
					LOGGER.info("Open action-Update Signature text is verified successfully.");
					gotoDashboardTab();
				}
				}		
		}
      
    //Test case 2: Check data in In-Progress action
      if(!(dashboardPage.verifyElementsOfDashboardPage("noDataOnRecommandationSection"))) {
			List<String> RecommandedAction = dashboardPage.getallTextOfDashboardPage("inProgressAppListNameDefaultDash");
			for (String actionList:RecommandedAction) {
				
				if (actionList.equals(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "enable-antivirus-windows-defender"))) 
				{
					
					dashboardPage.clickOnElementsOfDashboardPage("inProgressEnableAVDetailsButton");
					sleeper(4000);
					LOGGER.info("InProgress action-Enable AV text is verified successfully..");
					gotoDashboardTab();
					waitForPageLoaded();
				}			
				else if (actionList.equals (dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "security.update_signature_wind_defender")))
				{
					dashboardPage.clickOnElementsOfDashboardPage("inProgressUpdateDetailButton");
					sleeper(4000);
					LOGGER.info("InProgress action-Update Signature text is verified successfully..");
					gotoDashboardTab();
				}
				}		
		}
    //Test case 3: Check data in Completed action
      if(!(dashboardPage.verifyElementsOfDashboardPage("noDataOnRecommandationSection"))) {
			List<String> RecommandedAction = dashboardPage.getallTextOfDashboardPage("completedAppListDefaultDash");
			for (String actionList:RecommandedAction) {
				
				if (actionList.equals(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "enable-antivirus-windows-defender"))) 
				{
					
					dashboardPage.clickOnElementsOfDashboardPage("completedEnableAVDetailsButton");
					sleeper(4000);
					LOGGER.info("Completed action-Enable AV text is verified successfully.");
					gotoDashboardTab();
					waitForPageLoaded();
					dashboardPage.clickOnElementsOfDashboardPage("completedEnableAVDismissedButton");
					sleeper(2000);
				}			
				else if (actionList.equals (dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "security.update_signature_wind_defender")))
				{
					dashboardPage.clickOnElementsOfDashboardPage("completedUpdateDetailButton");
					sleeper(4000);
					LOGGER.info("Completed action-Update Signature text is verified successfully.");
					gotoDashboardTab();
					waitForPageLoaded();
					dashboardPage.clickOnElementsOfDashboardPage("completedUpdateDismissedButton");
					sleeper(2000);
				}
				}		
		}
    }	

    
    
    /**
	 * DashBoard - SoftwareUpdateV2 [Feature 1052024]
	 * 
	 * @throws Exception
	 */
	@Test(priority = 91, groups = {"REGRESSION", "REGRESSION_CI", "ALL_CI", "ALL",
			"REGRESSIONDASHBOARD1" }, description = "TC:1068895")
	public final void verifySoftwareUpdatesMicrosoftOfficeDashboardWidget() throws Exception {
		String[] detailsColumnNames = { "Last Signed-In User", "Serial Number", "Device Name", "Update Count",
				"Update Classification", "Operating System", "Operating System Release", "Last Seen", "Device Type",
				"Device Manufacturer", "Device Model", "Manufacture Date", "Location Level 1", "Location Level 2",
				"Location Level 3", "Location Level 4" };

		login("MSP_NEW_USER_EMAIL_FLEETCHART_US", "MSP_NEW_USER_PASSWORD_FLEETCHART_US");
		PreConfiguredDashboardPage preDashPage = new PreConfiguredDashboardPage(PreDefinedActions.getDriver())
				.getInstance();
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();

		List<String> expectedVisualiationlist = new ArrayList<>();
		expectedVisualiationlist.add("Devices With Available Critical Updates");
		expectedVisualiationlist.add("Devices With Available Security Updates");
		expectedVisualiationlist.add("Devices With Available Updates by Classification");

		String category = dashboardPage.getTextLanguage(LanguageCode, "MPI-Reporting-report-html-view-UI-JSON",
				"software");
		String subCategory = "Software UpdatesV2";
		String option = "Microsoft Office";

		preDashPage.waitForPageLoaded();
		preDashPage.setToPreConfiguredDashboard(LanguageCode, "catalog.type.default");

		softAssert.assertTrue(dashboardPage.verifySoftwareUpdateWidgetsVisualization(LanguageCode, category,
				subCategory, option, expectedVisualiationlist), "Visualtion List does not match");

		dashboardPage.clickByJavaScriptOnDashboardPage("criticalUpdatesRadioButton");
		dashboardPage.scrollToDashboardPage("widgetTitleTextbox");
		sleeper(1000);
		softAssert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("kpiFormatLabel"),
				"KPI Format label not present");
		softAssert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("percentageFormatLabel"),
				"Percentage Radiobutton not present");
		softAssert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("numberFormatLabel"),
				"Number Format Radiobutton not present");
		softAssert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("widgetTitleTextbox"),
				"Widget Title Textbox not present");

		dashboardPage.scrollToDashboardPage("updateByClassificationRadioButton");
		dashboardPage.clickByJavaScriptOnDashboardPage("updateByClassificationRadioButton");
		dashboardPage.scrollToDashboardPage("widgetTitleTextbox");
		sleeper(1000);
		softAssert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("addfilterLink"),
				"Add Filter Link not present");
		softAssert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("widgetTitleTextbox"),
				"Widget Title Textbox not present");
		sleeper(1000);

		dashboardPage.clickOnElementsOfDashboardPage("addfilterLink");
		String[] filtercriteriaField = { "Device Model", "Operating System Release", "Update Classification" };
		softAssert
				.assertTrue(
						dashboardPage
								.selectFilterOptionForDashBoardPage("Update Classification", LanguageCode,
										"addFilterFieldDropdown", "addFilterFieldDropdownOptionList")
								.containsAll(Arrays.asList(filtercriteriaField)),
						"Filter Criteria field options mismatched");
		sleeper(2000);

		dashboardPage.clickOnElementsOfDashboardPage("addFilterValueDropdown");
		sleeper(2000);

		scrollToBottom();
		List<String> lstactualUpdateClassification = dashboardPage
				.getTextAsListOnDashBoardPage("addFilterValueDropdownOptionList");

		/*
		 * softAssert.assertTrue(
		 * Arrays.asList(expectedArrayUpdateClassification).containsAll(
		 * lstactualUpdateClassification),
		 * "Update Classification options in filter did not match");
		 */

		dashboardPage.pressKey(Keys.ESCAPE);
		dashboardPage.clickOnElementsOfDashboardPage("deleteFilterIcon");

		dashboardPage.scrollToDashboardPage("securityUpdatesRadioButton");
		dashboardPage.clickByJavaScriptOnDashboardPage("securityUpdatesRadioButton");
		dashboardPage.scrollToDashboardPage("widgetTitleTextbox");
		sleeper(1000);
		softAssert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("kpiFormatLabel"),
				"KPI Format label not present");
		softAssert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("percentageFormatLabel"),
				"Percentage label not present");
		softAssert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("numberFormatLabel"),
				"Number Format label not present");
		softAssert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("widgetTitleTextbox"),
				"Widget Title Textbox not present");

		softAssert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("addButton"), "Add Button not present");
		softAssert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("discardButton"),
				"Discard button not present");

		gotoDashboardTab();

		preDashPage.setToPreConfiguredDashboard(LanguageCode, "catalog.type.default");

		LOGGER.info("Verify Device with Critical Updates Widget ");

		dashboardPage.scrollToDashboardPage("officeCriticalUpdatesWidgetTitleLabel");
		softAssert.assertEquals(dashboardPage.getTextOfDashboardPage("officeCriticalUpdatesWidgetTitleLabel").trim(),
				"Devices with Available Windows Critical Updates",
				"Primary text did not match for Critical Updates widget");
		softAssert.assertEquals(dashboardPage.getTextOfDashboardPage("officeCriticalUpdatesWidgetSubTitleLabel").trim(),
				"Make these critical updates as soon as possible.",
				"Secondary text did not match for Critical Updates widget");
		softAssert.assertTrue(
				dashboardPage.getTextOfDashboardPage("officeCriticalUpdatesWidgetCountLabel").trim().matches("[0-9]+"),
				"Critical updates device count is no number format");
		softAssert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("officeCriticalUpdatesWidgetDetailsLink"),
				"Critical updates Details link not present");
		softAssert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("officeCriticalUpdatesWidgetEllipsesButton"),
				"Critical updates Contextual Menu button not present");

		dashboardPage.clickByJavaScriptOnDashboardPage("officeCriticalUpdatesWidgetDetailsLink");
		waitForPageLoaded();
		dashboardPage.switchToDifferentTabOfDashboardPage();
		waitForPageLoaded();
		
		softAssert.assertEquals(dashboardPage.getTextAsListOnDashBoardPage("windowsUpdatesDetailsGridHeader"),
				Arrays.asList(detailsColumnNames), "Column Name did not match");
		dashboardPage.switchToPreviousTabOfDashboardPage();

		LOGGER.info("Verify Device with Security Updates Widget ");
		dashboardPage.scrollToDashboardPage("officeSecurityUpdatesWidgetTitleLabel");
		softAssert.assertEquals(dashboardPage.getTextOfDashboardPage("officeSecurityUpdatesWidgetTitleLabel").trim(),
				"Devices with Available Windows Security Updates",
				"Primary text did not match for Security Updates widget");
		softAssert.assertEquals(dashboardPage.getTextOfDashboardPage("officeSecurityUpdatesWidgetSubTitleLabel").trim(),
				"Make these Security Updates as soon as possible",
				"Secondary text did not match for Security Updates widget");
		softAssert.assertTrue(
				dashboardPage.getTextOfDashboardPage("officeSecurityUpdatesWidgetCountLabel").trim().matches("[0-9]+"),
				"Security updates device count is no number format");
		softAssert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("officeSecurityUpdatesWidgetDetailsLink"),
				"Security updates Details link not present");
		softAssert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("officeSecurityUpdatesWidgetEllipsesButton"),
				"Critical updates Ellipses button not present");

		dashboardPage.clickByJavaScriptOnDashboardPage("officeSecurityUpdatesWidgetDetailsLink");
		waitForPageLoaded();
		dashboardPage.switchToDifferentTabOfDashboardPage();
		waitForPageLoaded();
		
		softAssert.assertEquals(dashboardPage.getTextAsListOnDashBoardPage("windowsUpdatesDetailsGridHeader"),
				Arrays.asList(detailsColumnNames), "Column Name did not match");
		dashboardPage.switchToPreviousTabOfDashboardPage();

		LOGGER.info("Verify Windows Summary Widget on Dashboard");
		dashboardPage.scrollToDashboardPage("officeUpdatesSummaryWidget");
		softAssert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("officeUpdatesSummaryWidget"),
				"Windows Summary widget not present");

		dashboardPage.clickOnElementsOfDashboardPage("officeBarGraph");
		sleeper(2000);
		waitForPageLoaded();
		dashboardPage.switchToDifferentTabOfDashboardPage();
		waitForPageLoaded();
		List<String> expectedReportTabs = new ArrayList<>();
		expectedReportTabs.add(getTextLanguage(LanguageCode, "daas_reports_ui", "Global.graphHeaders.summary"));
		expectedReportTabs.add(getTextLanguage(LanguageCode, "daas_reports_ui", "by_update"));
		expectedReportTabs.add(getTextLanguage(LanguageCode, "daas_reports_ui", "details"));
		softAssert.assertTrue(
				dashboardPage.getTextAsListOnDashBoardPage("softwareNavigationTab").containsAll(expectedReportTabs),
				"Navigation tabs mismatched");
		dashboardPage.switchToPreviousTabOfDashboardPage();
		LOGGER.info("Widget is present successfully");
		softAssert.assertAll();
	}
	
	/*Feature 998064: [Insights][PI_Bristol][Antivirus][Action_Remediate]Remediating Disabled AV and Outdated AV Software Signature Definition.
    User Story 1110981: [Insights][PI_Bristol][Antivirus][Action_Remediate] UI verification of Remediating All devices for AV disabled .
	User Story 1110983: [Insights][PI_Bristol][Antivirus][Action_Remediate] UI Verification of Remediating All devices for AV outdated signature*/
   
   @Test(priority = 92, groups = { "REGRESSION", "REGRESSION_CI", "ALL_CI", "ALL", "REGRESSIONDASHBOARD1" }, description = "TC:1124549,1098104")
  	public final void verifyOpenAVRemediationPage() throws Exception {
      	login("COMPANY_EMAIL_DEVICESECURE_WIDGET","COMPANY_PWS_DEVICESECURE_WIDGET");
      	final String[] avRemediationListColumns = {"assets.table.heading.alias_name","assets.table.heading.serial_number","security.table.heading.lastSignedInUser","security.table.heading.lastSeen","security.table.heading.deviceManufacturer",
      						"security.table.heading.deviceModel","security.table.heading.operatingSystem","security.table.heading.complianceType","security.table.heading.realTimeProtectionStatus","security.table.heading.signatureStatus","security.table.heading.totalTimeofAntivirusStatus","security.table.heading.policyValue",
      						"security.table.heading.antivirusDefinitionReleaseDateTimeframe","security.table.heading.antivirusDefinitionReleaseDate","security.table.heading.antivirusDefinitionName","security.table.heading.policyStatus","security.table.heading.ErrorType","security.table.heading.antivirusApplicationVersion",
      						"security.table.heading.antivirusDefinitionVersion","security.table.heading.noofNotCompliantInstances","campaign.health.metrics.openreports.columns.location1","campaign.health.metrics.openreports.columns.location2","campaign.health.metrics.openreports.columns.location3","campaign.health.metrics.openreports.columns.location4"};
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();

		if (!toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, "COMPANY_EMAIL_DEVICESECURE_WIDGET"))){
			Assert.assertTrue(setToDefaultDashboard(LanguageCode),"Error occured in setting dashboard to default mode.");
		}else{
			Assert.assertTrue(setToDefaultDashboardGlobalFilter(LanguageCode),"Error occured in setting dashboard to default mode.");
		}
       dashboardPage.waitForPageLoaded();
				   
     Assert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("recommendationTitle")," Recommendation title is not correct");
     dashboardPage.scrollToDashboardPage("recommendationTitle");
     
    // Test case 1: Check data in Open action
     if(!(dashboardPage.verifyElementsOfDashboardPage("noDataOnRecommandationSection"))) {
			List<String> RecommandedAction = dashboardPage.getallTextOfDashboardPage("openActionAppListNameDefaultDash");
			for (String actionList:RecommandedAction) {
				
				if (actionList.equals(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "enable-antivirus-windows-defender"))) 
				{
					
					dashboardPage.clickOnElementsOfDashboardPage("openEnableAvDetailsButton");
					sleeper(4000);
					softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("enableAvHeading").equals(getTextLanguage(LanguageCode, "daas_ui", "enable-antivirus-windows-defender")), "Enable Antivirus - Windows Defender does not match on open remediation page.");
					softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("avstatusUpdateWidgetHeading").equals(getTextLanguage(LanguageCode, "daas_ui", "antivirus-update-status")), "antivirus-update-status does not match on open remediation page.");
					softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("disabledDeviceCount").equals(getTextLanguage(LanguageCode, "daas_ui", "devices-with-disabled-antivirus")), "devices-with-disabled-antivirus does not match on open remediation page.");
					softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("totalTimeforDisabledDevice").equals(getTextLanguage(LanguageCode, "daas_ui", "total.time.for.disabled.devices")), "Total Time For Disabled Devices does not match on open remediation page.");
					softAssert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("noOfdevices"),"Number of devices not present");
					softAssert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("piechartNoOfDays"),"pie chart Number of devices not present");
					softAssert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("fixButtonEnableAV"), "Fix button  does not match on open remediation page.");
					dashboardPage.clickOnElementsOfDashboardPage("fixButtonEnableAV");
					softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("fixbyEnablingAV").equals(getTextLanguage(LanguageCode, "daas_ui", "devices.security.enabling_antivirus")), "Fix by enabling AV  does not match on open remediation page.");
					softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("applyPolicyQuestion").equals(getTextLanguage(LanguageCode, "daas_ui", "devices.security.enabling_windows_defender")), "Fix button  does not match on open remediation page.");
					dashboardPage.clickOnElementsOfDashboardPage("applyPolicyButton");
					waitForPageLoaded();
					dashboardPage.clickOnElementsOfDashboardPage("exportButton");
					sleeper(2000);
					resetTableConfiguration();
					dashboardPage.scrollToDashboardPage("exportButton");
					Assert.assertTrue(dashboardPage.verifyColumnsofAVRemediationList(LanguageCode,"remediationListTableColumns",avRemediationListColumns), "remediation column list has wrong or missing column order");
					LOGGER.info("Open action-Enable AV remediation page is verified successfully.");
					sleeper(5000);
					gotoDashboardTab();
					waitForPageLoaded();
				}			
				else if (actionList.equals (dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "security.update_signature_wind_defender")))
				{
					dashboardPage.clickOnElementsOfDashboardPage("openUpdateAVDetailsButton");
					sleeper(4000);
					softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("updateSignatureHeading").equals(getTextLanguage(LanguageCode, "daas_ui", "update-signature-windows-defender")), "update-signature-windows-defender does not match on open remediation page.");
					softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("avstatusUpdateWidgetHeading").equals(getTextLanguage(LanguageCode, "daas_ui", "signature-update-status")), "signature-update-status does not match on open remediation page.");
					
					softAssert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("fixButton"), "Fix button  does not match on open remediation page.");
					dashboardPage.clickOnElementsOfDashboardPage("fixButton");
					softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("fixbyUpdatingSignature").equals(getTextLanguage(LanguageCode, "daas_ui", "devices.security.updating_signature")), "Fix by updating signature does not match on open remediation page.");
					softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("applyPolicyQuestionSignature").equals(getTextLanguage(LanguageCode, "daas_ui", "devices.security.updating_signature_windows_defender")), "Would you like to apply policy by updating signature for windows defender  does not match on open remediation page.");
					dashboardPage.clickOnElementsOfDashboardPage("applyPolicyButton");
					waitForPageLoaded();
					dashboardPage.clickOnElementsOfDashboardPage("exportButton");
					sleeper(4000);
					resetTableConfiguration();
					dashboardPage.scrollToDashboardPage("exportButton");
					Assert.assertTrue(dashboardPage.verifyColumnsofAVRemediationList(LanguageCode,"remediationListTableColumns",avRemediationListColumns), "remediation column list has wrong or missing column order");
					LOGGER.info("Open action-Signature AV remediation page is verified successfully.");
					sleeper(5000);
					gotoDashboardTab();
					waitForPageLoaded();
				}
				}		
		}
      }   
	
   /*Feature 998064: [Insights][PI_Bristol][Antivirus][Action_Remediate]Remediating Disabled AV and Outdated AV Software Signature Definition.
   User Story 1110991: [Insights][PI_Bristol][Antivirus][Action_Remediate] UI Automation for Default dashboard Remediation Disabled AV and Outdated Signature.*/
  
  @Test(priority = 93, groups = { "REGRESSION", "REGRESSION_CI", "ALL_CI", "ALL", "REGRESSIONDASHBOARD1" }, description = "TC:1098066,1098104")
 	public final void verifyInProgressAVRemediationPage() throws Exception {
     	login("COMPANY_EMAIL_DEVICESECURE_WIDGET","COMPANY_PWS_DEVICESECURE_WIDGET");
     	final String[] avRemediationListColumns = {"assets.table.heading.alias_name","assets.table.heading.serial_number","security.table.heading.lastSignedInUser","security.table.heading.lastSeen","security.table.heading.deviceManufacturer",
     						"security.table.heading.deviceModel","security.table.heading.operatingSystem","security.table.heading.complianceType","security.table.heading.realTimeProtectionStatus","security.table.heading.signatureStatus","security.table.heading.totalTimeofAntivirusStatus","security.table.heading.policyValue",
     						"security.table.heading.antivirusDefinitionReleaseDateTimeframe","security.table.heading.antivirusDefinitionReleaseDate","security.table.heading.antivirusDefinitionName","security.table.heading.policyStatus","security.table.heading.ErrorType","security.table.heading.antivirusApplicationVersion",
     						"security.table.heading.antivirusDefinitionVersion","security.table.heading.noofNotCompliantInstances","campaign.health.metrics.openreports.columns.location1","campaign.health.metrics.openreports.columns.location2","campaign.health.metrics.openreports.columns.location3","campaign.health.metrics.openreports.columns.location4"};
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();

		if (!toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, "COMPANY_EMAIL_DEVICESECURE_WIDGET"))){
			Assert.assertTrue(setToDefaultDashboard(LanguageCode),"Error occured in setting dashboard to default mode.");
		}else{
			Assert.assertTrue(setToDefaultDashboardGlobalFilter(LanguageCode),"Error occured in setting dashboard to default mode.");
		}
      dashboardPage.waitForPageLoaded();
				   
    Assert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("recommendationTitle")," Recommendation title is not correct");
    dashboardPage.scrollToDashboardPage("recommendationTitle");
    
   // Test case 1: Check data in Open action
    if(!(dashboardPage.verifyElementsOfDashboardPage("inProgressNoDataOnRecommandedSection"))) {
			List<String> RecommandedAction = dashboardPage.getallTextOfDashboardPage("inProgressAppListNameDefaultDash");
			for (String actionList:RecommandedAction) {
				
				if (actionList.equals(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "enable-antivirus-windows-defender"))) 
				{
					dashboardPage.verifyElementsOfDashboardPage("inprogressEnableProgressBar");
					dashboardPage.clickOnElementsOfDashboardPage("inProgressEnableAVDetailsButton");
					sleeper(4000);
					softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("enableAvHeading").equals(getTextLanguage(LanguageCode, "daas_ui", "enable-antivirus-windows-defender")), "Enable Antivirus - Windows Defender does not match on open remediation page.");
					softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("avstatusUpdateWidgetHeading").equals(getTextLanguage(LanguageCode, "daas_ui", "antivirus-update-status")), "antivirus-update-status does not match on open remediation page.");
					softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("disabledDeviceCount").equals(getTextLanguage(LanguageCode, "daas_ui", "devices-with-disabled-antivirus")), "devices-with-disabled-antivirus does not match on open remediation page.");
					softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("totalTimeforDisabledDevice").equals(getTextLanguage(LanguageCode, "daas_ui", "total.time.for.disabled.devices")), "Total Time For Disabled Devices does not match on open remediation page.");
					softAssert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("noOfdevices"),"Number of devices not present");
					softAssert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("piechartNoOfDays"),"pie chart Number of devices not present");
					dashboardPage.clickOnElementsOfDashboardPage("inprogressExportButton");
					sleeper(2000);
					resetTableConfiguration();
					dashboardPage.scrollToDashboardPage("inprogressExportButton");
					Assert.assertTrue(dashboardPage.verifyColumnsofAVRemediationList(LanguageCode,"remediationListTableColumns",avRemediationListColumns), "remediation column list has wrong or missing column order");
					LOGGER.info("In Progress action-Enable AV remediation page is verified successfully.");
					sleeper(5000);
					gotoDashboardTab();
					waitForPageLoaded();
				}			
				else if (actionList.equals (dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "security.update_signature_wind_defender")))
				{
					dashboardPage.verifyElementsOfDashboardPage("inprogressSignatureProgressBar");
					dashboardPage.clickOnElementsOfDashboardPage("inProgressUpdateDetailButton");
					sleeper(4000);
					softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("updateSignatureHeading").equals(getTextLanguage(LanguageCode, "daas_ui", "update-signature-windows-defender")), "update-signature-windows-defender does not match on open remediation page.");
					softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("avstatusUpdateWidgetHeading").equals(getTextLanguage(LanguageCode, "daas_ui", "signature-update-status")), "signature-update-status does not match on open remediation page.");
					dashboardPage.clickOnElementsOfDashboardPage("inprogressExportButton");
					sleeper(4000);
					resetTableConfiguration();
					dashboardPage.scrollToDashboardPage("inprogressExportButton");
					Assert.assertTrue(dashboardPage.verifyColumnsofAVRemediationList(LanguageCode,"remediationListTableColumns",avRemediationListColumns), "remediation column list has wrong or missing column order");
					LOGGER.info("In Progress  action-Signature AV remediation page is verified successfully.");
					sleeper(5000);
					gotoDashboardTab();
					waitForPageLoaded();
				}
				}		
		}
	}
  
  /*Feature 998064: [Insights][PI_Bristol][Antivirus][Action_Remediate]Remediating Disabled AV and Outdated AV Software Signature Definition.
  User Story 1110991: [Insights][PI_Bristol][Antivirus][Action_Remediate] UI Automation for Default dashboard Remediation Disabled AV and Outdated Signature.*/
 
 @Test(priority = 94, groups = { "REGRESSION", "REGRESSION_CI", "ALL_CI", "ALL", "REGRESSIONDASHBOARD1" }, description = "TC:1098066,1098104")
	public final void verifyCompletedAVRemediationPage() throws Exception {
    	login("COMPANY_EMAIL_DEVICESECURE_WIDGET","COMPANY_PWS_DEVICESECURE_WIDGET");
    	final String[] avRemediationListColumns = {"assets.table.heading.alias_name","assets.table.heading.serial_number","security.table.heading.lastSignedInUser","security.table.heading.lastSeen","security.table.heading.deviceManufacturer",
    						"security.table.heading.deviceModel","security.table.heading.operatingSystem","security.table.heading.complianceType","security.table.heading.realTimeProtectionStatus","security.table.heading.signatureStatus","security.table.heading.totalTimeofAntivirusStatus","security.table.heading.policyValue",
    						"security.table.heading.antivirusDefinitionReleaseDateTimeframe","security.table.heading.antivirusDefinitionReleaseDate","security.table.heading.antivirusDefinitionName","security.table.heading.policyStatus","security.table.heading.ErrorType","security.table.heading.antivirusApplicationVersion",
    						"security.table.heading.antivirusDefinitionVersion","security.table.heading.noofNotCompliantInstances","campaign.health.metrics.openreports.columns.location1","campaign.health.metrics.openreports.columns.location2","campaign.health.metrics.openreports.columns.location3","campaign.health.metrics.openreports.columns.location4"};
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();

		if (!toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, "COMPANY_EMAIL_DEVICESECURE_WIDGET"))){
			Assert.assertTrue(setToDefaultDashboard(LanguageCode),"Error occured in setting dashboard to default mode.");
		}else{
			Assert.assertTrue(setToDefaultDashboardGlobalFilter(LanguageCode),"Error occured in setting dashboard to default mode.");
		}
     dashboardPage.waitForPageLoaded();
				   
   Assert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("recommendationTitle")," Recommendation title is not correct");
   dashboardPage.scrollToDashboardPage("recommendationTitle");
   
  // Test case 1: Check data in Open action
   if(!(dashboardPage.verifyElementsOfDashboardPage("completedNoDataOnRecommandedSection"))) {
			List<String> RecommandedAction = dashboardPage.getallTextOfDashboardPage("completedAppListDefaultDash");
			for (String actionList:RecommandedAction) {
				
				if (actionList.equals(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "enable-antivirus-windows-defender"))) 
				{
					dashboardPage.clickOnElementsOfDashboardPage("completedEnableAVDetailsButton");
					sleeper(4000);
					softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("enableAvHeading").equals(getTextLanguage(LanguageCode, "daas_ui", "enable-antivirus-windows-defender")), "Enable Antivirus - Windows Defender does not match on open remediation page.");
					softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("avstatusUpdateWidgetHeading").equals(getTextLanguage(LanguageCode, "daas_ui", "antivirus-update-status")), "antivirus-update-status does not match on open remediation page.");
					softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("disabledDeviceCount").equals(getTextLanguage(LanguageCode, "daas_ui", "devices-with-disabled-antivirus")), "devices-with-disabled-antivirus does not match on open remediation page.");
					softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("totalTimeforDisabledDevice").equals(getTextLanguage(LanguageCode, "daas_ui", "total.time.for.disabled.devices")), "Total Time For Disabled Devices does not match on open remediation page.");
					softAssert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("noOfdevices"),"Number of devices not present");
					softAssert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("piechartNoOfDays"),"pie chart Number of devices not present");
					dashboardPage.clickOnElementsOfDashboardPage("completedExportButton");
					sleeper(2000);
					resetTableConfiguration();
					dashboardPage.scrollToDashboardPage("completedExportButton");
					Assert.assertTrue(dashboardPage.verifyColumnsofAVRemediationList(LanguageCode,"remediationListTableColumns",avRemediationListColumns), "remediation column list has wrong or missing column order");
					LOGGER.info("Completed action-Enable AV remediation page is verified successfully.");
					sleeper(5000);
					gotoDashboardTab();
					waitForPageLoaded();
					dashboardPage.clickOnElementsOfDashboardPage("completedEnableAVDismissedButton");
				}			
				else if (actionList.equals (dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "security.update_signature_wind_defender")))
				{
					dashboardPage.clickOnElementsOfDashboardPage("completedUpdateDetailButton");
					sleeper(4000);
					softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("updateSignatureHeading").equals(getTextLanguage(LanguageCode, "daas_ui", "update-signature-windows-defender")), "update-signature-windows-defender does not match on open remediation page.");
					softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("avstatusUpdateWidgetHeading").equals(getTextLanguage(LanguageCode, "daas_ui", "signature-update-status")), "signature-update-status does not match on open remediation page.");
					dashboardPage.clickOnElementsOfDashboardPage("completedExportButton");
					sleeper(4000);
					resetTableConfiguration();
					dashboardPage.scrollToDashboardPage("completedExportButton");
					Assert.assertTrue(dashboardPage.verifyColumnsofAVRemediationList(LanguageCode,"remediationListTableColumns",avRemediationListColumns), "remediation column list has wrong or missing column order");
					LOGGER.info("Completed action-Signature AV remediation page is verified successfully.");
					sleeper(5000);
					gotoDashboardTab();
					waitForPageLoaded();
					dashboardPage.clickOnElementsOfDashboardPage("completedUpdateDismissedButton");
				}
				}		
		}
	}
 /**
	 * Feature 1119544: [CaaS] Device Centric Update
	 * Test Case 1166591: [CaaS][Device Update]>> Verify the filter Criteria on Device list page
	 * This method updates templates in HP Presence device Update Widget
	 */
	@Test(priority = 95, groups = {"CAAS_REGRESSION"},description = "TC1166591")
	public final void VerifyCaasHPPresenceDeviceUpdate() throws Exception {
       login("COMPANY_CAAS_EMAIL", "COMPANY_CAAS_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		if (toggleVerification(DashboardVariables.FLEXIDASHBOARD, "COMPANY_CAAS_EMAIL")) {
			String category = dashboardPage.getTextLanguage(LanguageCode, "dashboard_service", "label.report_type_Conference_Service");
			// HPPresence SoftwareUpdate sub-category of HP Presence category for CaaS 
			ArrayList<String> subCategoryList = new ArrayList<String>(Arrays.asList(
					getTextLanguage(LanguageCode, "dashboard_service", "label.report_category_presenceDeviceUpdate")));
			sleeper(5000);
			dashboardPage.addCustomDashboard("CUSTOM_DASHBOARD_NAME");
			softAssert.assertEquals(dashboardPage.verifyCustomDashboardName("CUSTOM_DASHBOARD_NAME"), true, "Dashboard is not created");
			refreshPage();
			sleeper(3000);
			dashboardPage.addWidgetForCaaSHPPresence(category, subCategoryList, LanguageCode);
			sleeper(3000);
			waitForAnalyticsPageLoaded();
			scrollToBottom();
			
			//Verifying HPPresenceDeviceUpdate Widget
			softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("firstWidgetTitle").equals(dashboardPage.getTextLanguage(LanguageCode,"dashboard_service","label.report_category_presenceDeviceUpdate")),"Presence Device Update title text is incorrect");
			sleeper(3000);
			
			//Click on available option in the do-nut chart
			dashboardPage.clickDonutChart("availableDonutChart");
			sleeper(3000);
			 
			//Verifying Device Updates screen
			dashboardPage.waitForElementsOfDashboardPage("deviceUpdatesTitle");
			Assert.assertTrue(dashboardPage.getTextOfDashboardPage("deviceUpdatesTitle").equals(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.device_updates.list")), "Device Updates title text is incorrect");
			LOGGER.info("Device Updates title is matched");

			//Verifying the columns in Device Updates screen
			Assert.assertTrue(dashboardPage.getTextOfDashboardPage("deviceUpdatesRoomName").equals(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "rooms.list.label.name")), "Room Name text is incorrect");
			Assert.assertTrue(dashboardPage.getTextOfDashboardPage("deviceUpdatesDeviceType").equals(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "rooms.addroom.label.device_type")), "Device Type text is incorrect");
			Assert.assertTrue(dashboardPage.getTextOfDashboardPage("deviceUpdatesDeviceName").equals(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "rooms.addroom.label.device_name")), "Device Name text is incorrect");
			Assert.assertTrue(dashboardPage.getTextOfDashboardPage("deviceUpdatesUpdateStatus").equals(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "rooms.list.label.update_status")), "Update Status text is incorrect");
			Assert.assertTrue(dashboardPage.getTextOfDashboardPage("deviceUpdatesUpdateName").equals(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "windowsupdate.updatename")), "Update Name text is incorrect");
			Assert.assertTrue(dashboardPage.getTextOfDashboardPage("deviceUpdatesVersion").equals(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "asset_detail.software_list_column.version")), "Version text is incorrect");
			Assert.assertTrue(dashboardPage.getTextOfDashboardPage("deviceUpdatesReleaseDate").equals(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "rooms.details.section.release_date")), "Release Date text is incorrect");
			
			//Click on Update button
			dashboardPage.clickOnElementsOfDashboardPage("deviceUpdatesCheckbox");
			dashboardPage.waitForElementsOfDashboardPage("deviceUpdatesUpdateBtn");
			dashboardPage.clickOnElementsOfDashboardPage("deviceUpdatesUpdateBtn");
			sleeper(3000);
			
			dashboardPage.waitForElementsOfDashboardPage("deviceUpdatePopupTitle");
			Assert.assertTrue(dashboardPage.getTextOfDashboardPage("deviceUpdatePopupTitle").equals(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "rooms.updatedevice.title.device_update")), "Device Updates Heading text is incorrect");
			String deviceCount = "2";
			String roomCount = "1";
			Assert.assertTrue(dashboardPage.getTextOfDashboardPage("deviceUpdatePopupText1").equals(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "dashboard.chart.devicecentric.subheading").replace("{device_count}", deviceCount).replace("{room_count}", roomCount)), "Device Updates Sub Heading text is incorrect");
			Assert.assertTrue(dashboardPage.getTextOfDashboardPage("deviceUpdatePopupText2").equals(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "dashboard.chart.devicecentric.content")), "Device Updates Content text is incorrect");
			Assert.assertTrue(dashboardPage.getTextOfDashboardPage("deviceUpdatePopupText3").equals(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "dashboard.chart.devicecentric.subcontent")), "Device Updates Sub Content text is incorrect");
			dashboardPage.clickOnElementsOfDashboardPage("deviceUpdatePopupUpdateBtn");
			
			//Validation of Toaster message   
			Assert.assertTrue(dashboardPage.getTextOfDashboardPage("deviceUpdateToasterMessage").contains(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "dashboard.devicecentric.modal.toast_heading")), "Device Update Toaster message line 1 is not present");
			Assert.assertTrue(dashboardPage.getTextOfDashboardPage("deviceUpdateToasterMessage").contains(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "dashboard.devicecentric.modal.toast_desc").replace("{devicecount}", deviceCount).replace("{roomcount}", roomCount)), "Device Update Toaster message line 2 is not present");
			
			//Verifying Device Updates screen
			dashboardPage.waitForElementsOfDashboardPage("deviceUpdatesTitle");
			Assert.assertTrue(dashboardPage.getTextOfDashboardPage("deviceUpdatesTitle").equals(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "dashboard.charts.device_updates.list")), "Device Updates title text is incorrect");
			LOGGER.info("Device Updates title is matched");
			
			dashboardPage.clickOnElementsOfDashboardPage("deviceUpdateDashboardTitle");
			sleeper(3000);
			dashboardPage.deleteCustomDashboard("CUSTOM_DASHBOARD_NAME");
			softAssert.assertEquals(dashboardPage.verifyCustomDashboardName("CUSTOM_DASHBOARD_NAME"), false, "Dashboard is not deleted");
			//softAssert.assertAll();
		} else {
			LOGGER.info("This is not flexible dashboard");
		}
	}
	
   /**
	 * Feature 1119544: [CaaS] Device Centric Update
	 * Test Case 1166591: [CaaS][Device Update]>> Verify the filter Criteria on Device list page
	 * This method validates the task after updating templates in HP Presence device Update Widget
	 */
	@Test(priority = 96, groups = {"CAAS_REGRESSION"},description = "TC1166591")
	public final void VerifyCaasHPPresenceDeviceUpdateTask() throws Exception {
	    
		login("COMPANY_CAAS_EMAIL", "COMPANY_CAAS_PASSWORD");
		gotoTasksTab();
		LOGGER.info("Redirected to Tasks list page");
		sleeper(10000);
		RoomsListPage RoomsListPage = new RoomsListPage(PreDefinedActions.getDriver()).getInstance();
	
		if (toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, "COMPANY_CAAS_EMAIL"))) {
	
			RoomsListPage.waitForElementsOfRoomsListPage("tasksTitleOnBreadcrumbs");
			Assert.assertTrue(RoomsListPage.getTextOfRoomsListPage("tasksTitleOnBreadcrumbs").equals(RoomsListPage.getTextLanguage(LanguageCode, "daas_ui", "breadcrumbs.tasks")), "Tasks title text is incorrect");
			LOGGER.info("Tasks title matched");
			
			String str = getSystemDate();
			String[] parts = str.split(" ");
			String part1 = parts[0]; 
			String part2 = parts[1];

			Assert.assertTrue(RoomsListPage.getTextOfRoomsListPage("firstTaskInitiatedAt").contains(part1), "Initiated At Date and Time is incorrect");
			Assert.assertTrue(RoomsListPage.getTextOfRoomsListPage("firstTaskInitiatedAt").contains(part2), "Initiated At Date and Time is incorrect");
			
			RoomsListPage.clickOnElementsOfRoomsListPage("firstTask");
	
			RoomsDetailsPage roomsDetailsPage = new RoomsDetailsPage(PreDefinedActions.getDriver()).getInstance();
	
			Assert.assertTrue(roomsDetailsPage.getTextOfRoomsDetailsPage("Status").equals(RoomsListPage.getTextLanguage(LanguageCode, "daas_ui", "incidents.status")), "Status is not present");
			LOGGER.info("Status is present");
			Assert.assertTrue(roomsDetailsPage.getTextOfRoomsDetailsPage("StatusValueUnknown").equals(RoomsListPage.getTextLanguage(LanguageCode, "daas_ui", "global.unknown")), "Status value Unknown is not present");
			LOGGER.info("Status value Successful is present");
			Assert.assertTrue(roomsDetailsPage.getTextOfRoomsDetailsPage("Identifier").equals(RoomsListPage.getTextLanguage(LanguageCode, "daas_ui", "tasks.identifier")), "Identifier is not present");
			LOGGER.info("Identifier is present");
			Assert.assertTrue(roomsDetailsPage.getTextOfRoomsDetailsPage("IdentifierValueBios").equals(biosTemplate), "Identifier value bios is not present");
			LOGGER.info("Identifier value Bios is present");	
	
			roomsDetailsPage.clickOnElementsOfRoomsDetailsPage("TaskDetailsSummaryPanelHeader");
			Assert.assertTrue(roomsDetailsPage.getTextOfRoomsDetailsPage("TaskStatus").equals(RoomsListPage.getTextLanguage(LanguageCode, "daas_ui", "tasks.task_execution")), "Task Execution Status is not present");
			LOGGER.info("Task Execution Status is present");
			Assert.assertTrue(roomsDetailsPage.getTextOfRoomsDetailsPage("ConnectionStatus").equals(RoomsListPage.getTextLanguage(LanguageCode, "daas_ui", "tasks.device_connection_status")), "Device Connection Status is not present");
			LOGGER.info("Device Connection Status is present");
			Assert.assertTrue(roomsDetailsPage.getTextOfRoomsDetailsPage("DeviceName").equals(RoomsListPage.getTextLanguage(LanguageCode, "daas_ui", "rooms.details.section.device_name")), "Device Name is not present");
			LOGGER.info("Device Name is present");
			Assert.assertTrue(roomsDetailsPage.getTextOfRoomsDetailsPage("TaskStatusValue").equals(RoomsListPage.getTextLanguage(LanguageCode, "daas_ui", "global.unknown")), "Task Execution Status Value is not present");
			LOGGER.info("Task Execution Status Value is present");
			Assert.assertTrue(roomsDetailsPage.getTextOfRoomsDetailsPage("ConnectionStatusValue").equals(RoomsListPage.getTextLanguage(LanguageCode, "daas_ui", "taskdetails.label.connection_status_connected")), "Device Connection Status Value is not present");
			LOGGER.info("Device Connection Status Value is present");
			Assert.assertTrue(roomsDetailsPage.getTextOfRoomsDetailsPage("DeviceNameValue").equals(biosTemplateDeviceName), "Device Name Value is not present");
			LOGGER.info("Device Name Value is present");
		}
	}
	
	@Test(priority = 97, groups = { "REGRESSION", "REGRESSION_CI", "ALL_CI", "ALL", "REGRESSIONDASHBOARD1" })
	public final void verifyChartCPUUtilizationChartdrilldownCount() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		
		 login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		 DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();

		if (!toggleVerification(DashboardVariables.FLEXIDASHBOARD, getCredentials(environment, "MSP_ADMIN_US_EMAIl"))) {
			// Verify toggle status
			Assert.assertTrue(toggleVerification(DashboardVariables.CPUUTILIZATION, "MSP_ADMIN_US_EMAIl"), "LD-Toggle is disabled for CPU Utilization chart");
			LOGGER.info("Verified LD-Toggle status");
			// Reset current configuration of dashboard chart
			dashboardPage.clearDashboardCompanyFilter();
			dashboardPage.verifyResetToDefaultButton();
			dashboardPage.waitForPageLoaded();
			
			dashboardPage.scrollToDashboardPage("cpuUtilizationTitle");
			LOGGER.info("Scrolled down to cpuUtilization chart");
				softAssert.assertTrue(dashboardPage.verifyTooltipTextOnReportWithFrame("labelsLocatorCpu", "tooltipTextCPU", "cpuUtilizationTextLocator"), "Drilldown count of CPU Utlization chart did not match with reports");
			softAssert.assertAll();
			LOGGER.info("All drill down count is mactching !!");
		} else {
			if (!toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, "MSP_ADMIN_US_EMAIl"))){
				Assert.assertTrue(setToDefaultDashboard(LanguageCode),"Error occured in setting dashboard to default mode.");
			}else{
				Assert.assertTrue(setToDefaultDashboardGlobalFilter(LanguageCode),"Error occured in setting dashboard to default mode.");
			}
			dashboardPage.scrollToDashboardPage("cpuUtilizationTitleFlexi");
			LOGGER.info("Scrolled down to CPU Utilization chart");
			dashboardPage.waitForPageLoaded();
			//Validates the drill count from dashboard to reports details page. The Count should match!!
				softAssert.assertTrue(dashboardPage.verifyDrillDownCountgDonutChartFlexi("cpuUtilizationLegendLabelFlexi", "tooltipTextCPUFlexi", "cpuUtilizationTextLocatorFlexi", "cpuUtilizationChartVisibilityFlexi", "cpuUtilizationChartVisibilityFlexi","cpuUtilizationDropdownFlexi"), "Drilldown count of CPU Utlization chart did not match with reports");
				softAssert.assertAll();
				LOGGER.info("All drill down count is mactching !!");
		}
	}
	
	@Test(priority = 98, groups = { "REGRESSION", "REGRESSION_CI", "ALL_CI", "ALL", "REGRESSIONDASHBOARD1" })
	public final void verifyChartMemoryUtilizationChartdrilldownCount() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		
		 login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		 DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();

		if (!toggleVerification(DashboardVariables.FLEXIDASHBOARD, getCredentials(environment, "MSP_ADMIN_US_EMAIl"))) {
			// Verify toggle status
			Assert.assertTrue(toggleVerification(DashboardVariables.MEMORYUTILIZATION, "MSP_ADMIN_US_EMAIl"), "LD-Toggle is disabled for Memory Utilization chart");
			LOGGER.info("Verified LD-Toggle status");
			// Reset current configuration of dashboard chart
			dashboardPage.clearDashboardCompanyFilter();
			dashboardPage.verifyResetToDefaultButton();
			dashboardPage.waitForPageLoaded();
			dashboardPage.scrollToDashboardPage("memoryUtlizationTitle");
			LOGGER.info("Scrolled down to Memory Utilization chart");
			
			softAssert.assertTrue(dashboardPage.verifyTooltipTextOnReportWithFrame("labelsLocatorMemory", "tooltipTextMemory", "memoryUtilizationTextLocator"), "Drilldown Count of Memory Utlization chart did not match with reports");
			softAssert.assertAll();
			LOGGER.info("All drill down count is mactching !!");
		} else {
			if (!toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, "MSP_ADMIN_US_EMAIl"))){
				Assert.assertTrue(setToDefaultDashboard(LanguageCode),"Error occured in setting dashboard to default mode.");
			}else{
				Assert.assertTrue(setToDefaultDashboardGlobalFilter(LanguageCode),"Error occured in setting dashboard to default mode.");
			}
			dashboardPage.scrollToDashboardPage("memoryUtlizationTitleFlexi");
			LOGGER.info("Scrolled down to Memory Utilization chart");
			dashboardPage.waitForPageLoaded();
			
			//Validates the drill count from dashboard to reports details page. The Count should match!!
			softAssert.assertTrue(dashboardPage.verifyDrillDownCountgDonutChartFlexi("memoryUtilizationLegendLabelFlexi", "tooltipTextMemoryFlexi", "memoryUtilizationTextLocatorFlexi", "memoryUtilizationChartVisibilityFlexi", "memoryUtilizationChartVisibilityFlexi","memoryUtilizationDropdownFlexi"), "Drilldown count of memory Utlization chart did not match with reports");
			softAssert.assertAll();
			LOGGER.info("All drill down count is mactching !!");
		}
	}
	
	@Test(priority = 99, groups = { "REGRESSION", "REGRESSION_CI", "ALL_CI", "ALL"})
	public final void verifyWindows10PatchStatusDrillDownCountMatch() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		if (!toggleVerification(DashboardVariables.FLEXIDASHBOARD, getCredentials(environment, "MSP_ADMIN_US_EMAIl"))) {

			//Match the drill down count Dashboard count
			softAssert.assertTrue(dashboardPage.verifyTooltipTextVersionStatusWPS("patchStatus", "patchStatusTooltipText", "seconddrilldown", "secondlevelTooltip", "osReleaseFilterCriteria", "backButtonWPS"), "Tooltip text of Windows10PatchStatus chart did not match with reports filter criteria");
		}else {
			if (!toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, "MSP_ADMIN_US_EMAIl"))){
				Assert.assertTrue(setToDefaultDashboard(LanguageCode),"Error occured in setting dashboard to default mode.");
			}else{
				Assert.assertTrue(setToDefaultDashboardGlobalFilter(LanguageCode),"Error occured in setting dashboard to default mode.");
			}
			//Match the drill down count Dashboard count
			softAssert.assertTrue(dashboardPage.verifyTooltipTextVersionStatusdrilldown("patchStatusFlexi", "patchStatusTooltipTextFlexi", "patchStatuslabel","seconddrilldownFlexi", "secondlevelTooltipFlexi", "osReleaseFilterCriteriaFlexi", "backButtonWPSFlexi"), "Tooltip text of Windows10PatchStatus chart did not match with reports filter criteria");
			softAssert.assertAll();
			LOGGER.error("Drill Down Count did not Match");
		}
	}
	
	//This Data provider includes credential for AC only and multiplan with AC and dasboard widgets accordingly.
	@DataProvider
	public Object[][] tenantWidgetData() {
		return new Object[][] {
			{"ACTIVECARETENANT_EMAIL","ACTIVECARETENANT_PASSWORD", new String[] {"Battery Replacement", "Disk Replacement", "Thermal Grading", "All Incidents By Type", "Incident Burndown","Todays New Critical Incidents"},"AC Only"},
			{"ACTIVECARETENANT_EMAIL1", "ACPITENANT_PASSWORD", new String[] {"Fleet Overview","Fleet Experience Score", "Battery Replacement", "Disk Replacement", "Network Speed (Wireless and Wired LAN)", "Seven Day Low Speed Trend", "Outdated Network Card Driver", "Weekly Network Disconnections", "Disk Capacity", "Thermal Grading", "Windows Patch Status", "Devices By Type", "Devices by OS", "Windows 11 Upgrade Status", "Warranty Expiration", "Subscription Expiration", "CPU Utilization", "Memory Utilization", "Hardware Inventory", "Disk Replacement Summary", "All Incidents By Type", "Incident Burndown", "Software Inventory (Top 10)", "Todays New Critical Incidents", "Driver Status", "BIOS Update Insights", "Devices With Outdated Drivers", "Critical Updates (Windows)", "Security Updates (Windows)", "Devices With Available Windows Updates", "Critical Updates (Office)", "Security Updates (Office)", "Devices With Available Office Updates"},"AC PI WPT"},
			{"ACTIVECARETENANT_EMAIL2", "ACPITENANT_PASSWORD", new String[] {"Battery Replacement", "Disk Replacement", "Thermal Grading", "All Incidents By Type", "Incident Burndown","Todays New Critical Incidents"},"AC WPT"},
			{"ACTIVECARETENANT_EMAIL3", "ACPITENANT_PASSWORD", new String[] {"Fleet Overview", "Fleet Experience Score", "Battery Replacement", "Disk Replacement", "Network Speed (Wireless and Wired LAN)", "Seven Day Low Speed Trend", "Outdated Network Card Driver", "Weekly Network Disconnections", "Disk Capacity", "Thermal Grading", "Windows Patch Status", "Devices By Type", "Devices by OS", "Windows 11 Upgrade Status", "Warranty Expiration", "Subscription Expiration", "CPU Utilization", "Memory Utilization", "Hardware Inventory", "Disk Replacement Summary", "All Incidents By Type", "Incident Burndown", "Software Inventory (Top 10)", "Todays New Critical Incidents", "Driver Status", "BIOS Update Insights", "Devices With Outdated Drivers", "Critical Updates (Windows)", "Security Updates (Windows)", "Devices With Available Windows Updates", "Critical Updates (Office)", "Security Updates (Office)", "Devices With Available Office Updates"},"AC PEM"}
		};
	}

	// This method verifies Dashboard widgets for Active Care and multiplan with Active Care
	@Test(priority = 100, groups = {"ACTIVECARESANITY", "REGRESSIONSETTINGS2", "SMOKE", "REGRESSION_CI", "SMOKE_CI",
	"STABILIZATION_STAGING"}, description = "[1294351],[1294355],[1294356],[1294358],[1294359]", dataProvider = "tenantWidgetData")
	public final void verifyDashboardChartsLoadingForACAndMultiplan(String username , String password, String[] dashboardwidgets ,String Plan) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login(username, password);
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		waitForPageLoaded();	
		Assert.assertTrue(dashboardPage.verifyWidgetsDisplayedForActiveCare("widgetLocator", dashboardwidgets), "Widgets count "+Plan+" is incorrect");
		softAssert.assertAll();
		LOGGER.info("Validation of expected widgets displayed completed successfully.");

	}
			
			
	// This Data provider includes credential for AC only and AC+PI users.		
	@DataProvider
	public Object[][] getLoginData1() {
		Object[][] data = new Object[2][4];
		data[0][0] = "ACTIVECARETENANT_EMAIL";  // Only AC tenant
		data[0][1] = "ACTIVECARETENANT_PASSWORD";
		data[0][2] = new String[] {"Incidents"};
		data[0][3] = "AC Only";

		data[1][0] = "ACPITENANT_EMAIL";        // AC+PI
		data[1][1] = "ACPITENANT_PASSWORD";
		data[1][2] = new String[] {"Deployment","Fleet Overview","Hardware","Incidents","Network","Security","Software","Subscription"};
		data[1][3] = "AC PI";
		return data;
	}

	// This method verifies category dropdown item for AC only and AC+PI tenant while adding dashboard widget
	@Test(priority = 101, groups = {"ACTIVECARESANITY", "REGRESSIONSETTINGS2", "SMOKE", "REGRESSION_CI", "SMOKE_CI",
	"STABILIZATION_STAGING"}, description = "[1098735]", dataProvider = "getLoginData1")
	public final void verifyDashboardWidget(String username, String password, String[] categorydropdownValues, String tenant_type) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login(username, password);
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		waitForPageLoaded();
		if(tenant_type.equalsIgnoreCase("AC Only")) {
			LOGGER.info("For AC Only tenant ADD Widget button is disabled.");
			softAssert.assertAll();
			LOGGER.info("Validation of expected widgets is not possible for AC only tenant.");
		}else {
		dashboardPage.clickOnElementsOfDashboardPage("addWidgetButton");
		dashboardPage.clickOnElementsOfDashboardPage("categoryDropdown");
		Assert.assertTrue(dashboardPage.verifyWidgetsDisplayedForActiveCare("categoryList", categorydropdownValues), "Widgets count is incorrect");
		softAssert.assertAll();
		LOGGER.info("Validation of expected widgets displayed completed successfully.");
		}
		
	}




}


		
