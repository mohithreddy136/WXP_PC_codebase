package com.testscripts.daasui;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.basesource.action.PreDefinedActions;
import com.daasui.constants.DashboardVariables;
import com.daasui.pages.PreConfiguredDashboardPage;

public class PreConfiguredDashboardTest extends CommonTest {
	private static Logger LOGGER = LogManager.getLogger(AnalyticsTest.class);
	public static String environment;

	/**
	 * Verify Pre-Configured Dashboard Title
	 *
	 * @throws Exception
	 */
	@Test(priority = 1, groups = { "PREDASH" })
	public final void verifyPreConfiguredDashboardTitle() throws Exception {
		login("MSP_ADMIN_US_EMAIl_REPORTS", "MSP_ADMIN_US_PASSWORD_REPORTS");
		PreConfiguredDashboardPage preDashPage = new PreConfiguredDashboardPage(PreDefinedActions.getDriver())
				.getInstance();
		Assert.assertTrue(preDashPage.verifyElementsOfPreConfiguredDashboardPage(""), "Title is not correct");
	}

	/**
	 * Verify Set as default functionality.
	 * 
	 * @throws Exception
	 */
		  @Test(priority = 2, groups = { "PREDASH" },description = "Test Case ID : 969911") 
		  public final void verifydefaultCustomDashboard() throws Exception {
		  login("MSP_ADMIN_US_EMAIl_DASHBOARD", "MSP_ADMIN_US_PASSWORD_DASHBOARD");
		  
		  PreConfiguredDashboardPage preDashPage = new PreConfiguredDashboardPage(PreDefinedActions.getDriver()).getInstance();
			if (toggleVerification(DashboardVariables.PRE_CONFIGURED_DASHBOARD, getCredentials(environment, "MSP_ADMIN_US_EMAIl_DASHBOARD"))) 
			{
		// Add Custom dashboard
		  preDashPage.addCustomPreConfiguredDashboard("PRE_CUSTOM_DASHBOARD_NAME");
		  //Set dsahboard as default
		  Assert.assertTrue(preDashPage.setcustomdashboardAsdefault(LanguageCode,"PRE_CUSTOM_DASHBOARD_NAME"), "Could not set the dashboard to deafult"); 
		  //Delete custom dashboard
		  preDashPage.deleteCustomDashboard("PRE_CUSTOM_DASHBOARD_NAME");
		  waitForPageLoaded();
		  Assert.assertFalse(preDashPage.verifyCustomDashboardName("PRE_CUSTOM_DASHBOARD_NAME"), "Dashboard is not Deleted");
		   } 
		  else { LOGGER.info("Pre Configured dashboard toggle is not enabled"); 
		  } 
		  }
		  
		  /**
			 * Check the sequence of dashboard chart
			 * @throws Exception
			 */
			@Test(priority = 3, groups = { "PREDASH" })
			public final void verifyChartSequenceforAssetmanagmentDashboard() throws Exception {
			login("MSP_ADMIN_US_EMAIl_DASHBOARD", "MSP_ADMIN_US_PASSWORD_DASHBOARD");
			PreConfiguredDashboardPage preDashPage = new PreConfiguredDashboardPage(PreDefinedActions.getDriver()).getInstance();
			String chartIdsArray[] = { "deviceType0", "hwinvByDeviceMfg0", "hwinvByDeviceMfgYear0", "deviceOS0","summary0", "dockingStationByModelName0",
									"topApplicationSummary0", "monitorInvByDispInvCompMfg0","monitorInvByDeviceType0", "deviceEnrollSummary0", "deviceEnrollMonthlySummary0",
									"netinvByDeviceModel0", "netinvByProductName0" };
			if (toggleVerification(DashboardVariables.PRE_CONFIGURED_DASHBOARD, getCredentials(environment, "MSP_ADMIN_US_EMAIl_DASHBOARD"))) 
			{
			Assert.assertTrue(preDashPage.setToPreConfiguredDashboard(LanguageCode,"flexible_dashboard.dashboard_name.asset_management"),"Error occured in setting dashboard to default mode.");
			LOGGER.info("Validation of Chart's sequence started");
			Assert.assertTrue(preDashPage.verifyChartOrderOfDashBoard("allChartsLocatorFlexi", chartIdsArray),"Chart Sequence is not correct");
			
			LOGGER.info("Validation Chart's sequence completed successfully");
			}
			else {
			 LOGGER.info("Toggle is off for logged in user");
			}			
		}
			
			/**
			 * Check the sequence of dashboard chart
			 * @throws Exception
			 */
			@Test(priority = 4, groups = { "PREDASH" })
			public final void verifyChartSequenceforFleetHeathDashboard() throws Exception {
			login("MSP_ADMIN_US_EMAIl_DASHBOARD", "MSP_ADMIN_US_PASSWORD_DASHBOARD");
			PreConfiguredDashboardPage preDashPage = new PreConfiguredDashboardPage(PreDefinedActions.getDriver()).getInstance();
			String chartIdsArray[] = { "allIncidentByType0", "initialIncidentResponse0", "bseTopErrorsMonthlySummary0", "bseTopErrorsByDriverErrors0","bseTopErrorsByDriverDevices0", "batteryRepAndRiskFactBiosUpToDate0",
									"batteryRepAndRiskFactDeviceByBHM0", "summary0","diskCapacityIncidentKpi0", "summary0", "installationStatus0" };
			if (toggleVerification(DashboardVariables.PRE_CONFIGURED_DASHBOARD, getCredentials(environment, "MSP_ADMIN_US_EMAIl_DASHBOARD"))) 
			{
			Assert.assertTrue(preDashPage.setToPreConfiguredDashboard(LanguageCode,"flexible_dashboard.dashboard_name.fleet_health"),"Error occured in setting dashboard to default mode.");
			LOGGER.info("Validation of Chart's sequence started");
			Assert.assertTrue(preDashPage.verifyChartOrderOfDashBoard("allChartsLocatorFlexi", chartIdsArray),"Chart Sequence is not correct");
			
			LOGGER.info("Validation Chart's sequence completed successfully");
			}
			else {
			 LOGGER.info("Toggle is off for logged in user");
			}			
		}
			/**
			 * Check the sequence of dashboard chart
			 * @throws Exception
			 */
			@Test(priority = 5, groups = { "PREDASH" })
			public final void verifyChartSequenceforFleetSecurityDashboard() throws Exception {
			login("MSP_ADMIN_US_EMAIl_DASHBOARD", "MSP_ADMIN_US_PASSWORD_DASHBOARD");
			PreConfiguredDashboardPage preDashPage = new PreConfiguredDashboardPage(PreDefinedActions.getDriver()).getInstance();
			String chartIdsArray[] = { "clientStatus0", "fwprotectionstatus0", "deviceByOS0", "driverByStatus0","driverByCriticality0", "biosInventorySummary0",
		    "biosInventoryByModel0", "biosInventoryByModel0","antivirusDashboardSummary0", "firewallDashboardSummary0", "encryptionDashboardSummary0",
		    "osSupportSummary0" };
			if (toggleVerification(DashboardVariables.PRE_CONFIGURED_DASHBOARD, getCredentials(environment, "MSP_ADMIN_US_EMAIl_DASHBOARD"))) 
			{
			Assert.assertTrue(preDashPage.setToPreConfiguredDashboard(LanguageCode,"flexible_dashboard.dashboard_name.fleet_security"),"Error occured in setting dashboard to default mode.");
			LOGGER.info("Validation of Chart's sequence started");
			Assert.assertTrue(preDashPage.verifyChartOrderOfDashBoard("allChartsLocatorFlexi", chartIdsArray),"Chart Sequence is not correct");
			
			LOGGER.info("Validation Chart's sequence completed successfully");
			}
			else {
			 LOGGER.info("Toggle is off for logged in user");
			}			
		}
			/**
			 * Check the sequence of dashboard chart
			 * @throws Exception
			 */
			@Test(priority = 6, groups = { "PREDASH" })
			public final void verifyChartSequenceforPerformanceMetricsDashboard() throws Exception {
			login("MSP_ADMIN_US_EMAIl_DASHBOARD", "MSP_ADMIN_US_PASSWORD_DASHBOARD");
			PreConfiguredDashboardPage preDashPage = new PreConfiguredDashboardPage(PreDefinedActions.getDriver()).getInstance();
			String chartIdsArray[] = { "cpuUtilSummaryByTime0", "memUtilSummaryByTime0", "cpuUtilizationByDay0", "memUtilizationByDay0","top25AppCpuOverUtilizatioWin0", "top25AppMemOverUtilizatioWin0",
		    "topErrosErrorSummaryBySwErrors0", "topErrorsMonthlySummary0","wpStartup0", "wpShutdown0", "wpWakeup0","wpReboots0","gpuHealthByWeek0" };
			if (toggleVerification(DashboardVariables.PRE_CONFIGURED_DASHBOARD, getCredentials(environment, "MSP_ADMIN_US_EMAIl_DASHBOARD"))) 
			{
			Assert.assertTrue(preDashPage.setToPreConfiguredDashboard(LanguageCode,"flexible_dashboard.dashboard_name.performance_metrics"),"Error occured in setting dashboard to default mode.");
			LOGGER.info("Validation of Chart's sequence started");
			Assert.assertTrue(preDashPage.verifyChartOrderOfDashBoard("allChartsLocatorFlexi", chartIdsArray),"Chart Sequence is not correct");
			
			LOGGER.info("Validation Chart's sequence completed successfully");
			}
			else {
			 LOGGER.info("Toggle is off for logged in user");
			}			
		}

	/**
	 * Check Widget Remove Not present for Pre-Configured Dashboard functionality
	 * @throws Exception
	 */
	@Test(priority = 7, groups = { "PREDASH" })
	public final void verifyDeleteWidgetNotPresentForPreConfiguredDashboardFunctionality() throws Exception {
		login("MSP_ADMIN_US_EMAIl_DASHBOARD", "MSP_ADMIN_US_PASSWORD_DASHBOARD");
		PreConfiguredDashboardPage preDashPage = new PreConfiguredDashboardPage(PreDefinedActions.getDriver()).getInstance();
		if (toggleVerification(DashboardVariables.PRE_CONFIGURED_DASHBOARD,
				getCredentials(environment, "MSP_ADMIN_US_EMAIl_DASHBOARD"))) {
			Assert.assertTrue(preDashPage.setToPreConfiguredDashboard(LanguageCode,"flexible_dashboard.dashboard_name.performance_metrics"),"Error occurred in selecting the dashboard");
            Assert.assertFalse(preDashPage.verifyDeleteDashboardNotPresent("Remove","CPUUtilMoreOptions"),"The Remove Widget option is present on dashboard widget");
			Assert.assertFalse(preDashPage.verifyDeleteDashboardNotPresent("Remove","MemoryUtilMoreOptions"),"The Remove Widget option is present on dashboard widget");
			Assert.assertFalse(preDashPage.verifyDeleteDashboardNotPresent("Remove","CPUByDayMoreOptions"),"The Remove Widget option is present on dashboard widget");
			Assert.assertFalse(preDashPage.verifyDeleteDashboardNotPresent("Remove","MemoryByDayMoreOptions"),"The Remove Widget option is present on dashboard widget");
			Assert.assertFalse(preDashPage.verifyDeleteDashboardNotPresent("Remove","CPUOverMoreOptions"),"The Remove Widget option is present on dashboard widget");
			Assert.assertFalse(preDashPage.verifyDeleteDashboardNotPresent("Remove","MemoryOverMoreOptions"),"The Remove Widget option is present on dashboard widget");
			Assert.assertFalse(preDashPage.verifyDeleteDashboardNotPresent("Remove","SWErrorNoOfErrorsMonthlyMoreOptions"),"The Remove Widget option is present on dashboard widget");
			Assert.assertFalse(preDashPage.verifyDeleteDashboardNotPresent("Remove","SWErrorMonthlySummaryMoreOptions"),"The Remove Widget option is present on dashboard widget");
			Assert.assertFalse(preDashPage.verifyDeleteDashboardNotPresent("Remove","StartupMoreOptions"),"The Remove Widget option is present on dashboard widget");
			Assert.assertFalse(preDashPage.verifyDeleteDashboardNotPresent("Remove","ShutdownMoreOptions"),"The Remove Widget option is present on dashboard widget");
			Assert.assertFalse(preDashPage.verifyDeleteDashboardNotPresent("Remove","WakeupMoreOptions"),"The Remove Widget option is present on dashboard widget");
			Assert.assertFalse(preDashPage.verifyDeleteDashboardNotPresent("Remove","RebootMoreOptions"),"The Remove Widget option is present on dashboard widget");
			Assert.assertFalse(preDashPage.verifyDeleteDashboardNotPresent("Remove","GPUMoreOptions"),"The Remove Widget option is present on dashboard widget");

			//Checking No Remove Option present on Fleet Health Dashboard Widgets
			Assert.assertTrue(preDashPage.setToPreConfiguredDashboard(LanguageCode,"flexible_dashboard.dashboard_name.fleet_health"),"Error occurred in selecting the dashboard");
			Assert.assertFalse(preDashPage.verifyDeleteDashboardNotPresent("Remove","incidentByTypeMoreOptions"),"The Remove Widget option is present on dashboard widget");
			Assert.assertFalse(preDashPage.verifyDeleteDashboardNotPresent("Remove","incidentRespAndClosureMoreOptions"),"The Remove Widget option is present on dashboard widget");
			Assert.assertFalse(preDashPage.verifyDeleteDashboardNotPresent("Remove","BSEMonthlySummaryMoreOptions"),"The Remove Widget option is present on dashboard widget");
			Assert.assertFalse(preDashPage.verifyDeleteDashboardNotPresent("Remove","BSEByDriversMoreOptions"),"The Remove Widget option is present on dashboard widget");
			Assert.assertFalse(preDashPage.verifyDeleteDashboardNotPresent("Remove","BSETopErrorsMoreOptions"),"The Remove Widget option is present on dashboard widget");
			Assert.assertFalse(preDashPage.verifyDeleteDashboardNotPresent("Remove","batteryStatusBHMMoreOptions"),"The Remove Widget option is present on dashboard widget");
			Assert.assertFalse(preDashPage.verifyDeleteDashboardNotPresent("Remove","batteryStatusBIOSMoreOptions"),"The Remove Widget option is present on dashboard widget");
			Assert.assertFalse(preDashPage.verifyDeleteDashboardNotPresent("Remove","diskCapacitySpaceMoreOptions"),"The Remove Widget option is present on dashboard widget");
			Assert.assertFalse(preDashPage.verifyDeleteDashboardNotPresent("Remove","incidentDiskAtMaxMoreOptions"),"The Remove Widget option is present on dashboard widget");
			Assert.assertFalse(preDashPage.verifyDeleteDashboardNotPresent("Remove","HWReplacementDeviceHealthMoreOptions"),"The Remove Widget option is present on dashboard widget");
			Assert.assertFalse(preDashPage.verifyDeleteDashboardNotPresent("Remove","DeploymentStatusMoreOptions"),"The Remove Widget option is present on dashboard widget");

			//Checking No Remove Option present on Asset Management Dashboard Widgets
			Assert.assertTrue(preDashPage.setToPreConfiguredDashboard(LanguageCode,"flexible_dashboard.dashboard_name.asset_management"),"Error occurred in selecting the dashboard");
			Assert.assertFalse(preDashPage.verifyDeleteDashboardNotPresent("Remove","HWInvDeviceTypeMoreOptions"),"The Remove Widget option is present on dashboard widget");
			Assert.assertFalse(preDashPage.verifyDeleteDashboardNotPresent("Remove","HWInvDeviceMFGMoreOptions"),"The Remove Widget option is present on dashboard widget");
			Assert.assertFalse(preDashPage.verifyDeleteDashboardNotPresent("Remove","HWInvMfgYearMoreOptions"),"The Remove Widget option is present on dashboard widget");
			Assert.assertFalse(preDashPage.verifyDeleteDashboardNotPresent("Remove","HWInvDeviceOSMoreOptions"),"The Remove Widget option is present on dashboard widget");
			Assert.assertFalse(preDashPage.verifyDeleteDashboardNotPresent("Remove","nonReportingDevicesMoreOptions"),"The Remove Widget option is present on dashboard widget");
			Assert.assertFalse(preDashPage.verifyDeleteDashboardNotPresent("Remove","dockingStationByModelMoreOptions"),"The Remove Widget option is present on dashboard widget");
			Assert.assertFalse(preDashPage.verifyDeleteDashboardNotPresent("Remove","SWInvSummaryMoreOptions"),"The Remove Widget option is present on dashboard widget");
			Assert.assertFalse(preDashPage.verifyDeleteDashboardNotPresent("Remove","componentInvDisplayInvMoreOptions"),"The Remove Widget option is present on dashboard widget");
			Assert.assertFalse(preDashPage.verifyDeleteDashboardNotPresent("Remove","componentInvCurrentDisplayMoreOptions"),"The Remove Widget option is present on dashboard widget");
			Assert.assertFalse(preDashPage.verifyDeleteDashboardNotPresent("Remove","HWInvEnrollmentStatusMoreOptions"),"The Remove Widget option is present on dashboard widget");
			Assert.assertFalse(preDashPage.verifyDeleteDashboardNotPresent("Remove","HWInvEnrollmentSummaryMoreOptions"),"The Remove Widget option is present on dashboard widget");
			Assert.assertFalse(preDashPage.verifyDeleteDashboardNotPresent("Remove","wirelessNetworkingCardDeviceModelMoreOptions"),"The Remove Widget option is present on dashboard widget");
			Assert.assertFalse(preDashPage.verifyDeleteDashboardNotPresent("Remove","wirelessNetworkingCardNetworkAdaptorMoreOptions"),"The Remove Widget option is present on dashboard widget");

			//Checking No Remove Option present on Fleet Security Dashboard Widgets
			Assert.assertTrue(preDashPage.setToPreConfiguredDashboard(LanguageCode,"flexible_dashboard.dashboard_name.fleet_security"),"Error occurred in selecting the dashboard");
			Assert.assertFalse(preDashPage.verifyDeleteDashboardNotPresent("Remove","protectionStatusMoreOptions"),"The Remove Widget option is present on dashboard widget");
			Assert.assertFalse(preDashPage.verifyDeleteDashboardNotPresent("Remove","firewallProtectionMoreOptions"),"The Remove Widget option is present on dashboard widget");
			Assert.assertFalse(preDashPage.verifyDeleteDashboardNotPresent("Remove","deviceByOSMoreOptions"),"The Remove Widget option is present on dashboard widget");
			Assert.assertFalse(preDashPage.verifyDeleteDashboardNotPresent("Remove","driverInventoryStatusMoreOptions"),"The Remove Widget option is present on dashboard widget");
			Assert.assertFalse(preDashPage.verifyDeleteDashboardNotPresent("Remove","driverInventoryCriticalityMoreOptions"),"The Remove Widget option is present on dashboard widget");
			Assert.assertFalse(preDashPage.verifyDeleteDashboardNotPresent("Remove","BIOSVerByDeviceModelMoreOptions"),"The Remove Widget option is present on dashboard widget");
			Assert.assertFalse(preDashPage.verifyDeleteDashboardNotPresent("Remove","BIOSUniqueByModelMoreOptions"),"The Remove Widget option is present on dashboard widget");
			Assert.assertFalse(preDashPage.verifyDeleteDashboardNotPresent("Remove","DSCAntivirusMoreOptions"),"The Remove Widget option is present on dashboard widget");
			Assert.assertFalse(preDashPage.verifyDeleteDashboardNotPresent("Remove","DSCFirewallMoreOptions"),"The Remove Widget option is present on dashboard widget");
			Assert.assertFalse(preDashPage.verifyDeleteDashboardNotPresent("Remove","DSCEncryptionMoreOptions"),"The Remove Widget option is present on dashboard widget");
			Assert.assertFalse(preDashPage.verifyDeleteDashboardNotPresent("Remove","win10PatchStatusMoreOptions"),"The Remove Widget option is present on dashboard widget");
		}
	}

	/**
	 * Check Copy Dashboard for Pre-Configured Dashboard functionality
	 * @throws Exception
	 */
	@Test(priority = 8, groups = { "PREDASH" })
	public final void verifyCopyDashboardForPreConfiguredDashboardFunctionality() throws Exception {
		login("MSP_ADMIN_US_EMAIl_DASHBOARD", "MSP_ADMIN_US_PASSWORD_DASHBOARD");
		PreConfiguredDashboardPage preDashPage = new PreConfiguredDashboardPage(PreDefinedActions.getDriver()).getInstance();
		if (toggleVerification(DashboardVariables.PRE_CONFIGURED_DASHBOARD,
				getCredentials(environment, "MSP_ADMIN_US_EMAIl_DASHBOARD"))) {
			Assert.assertTrue(preDashPage.setToPreConfiguredDashboard(LanguageCode,"flexible_dashboard.dashboard_name.fleet_health"),"Error ocurred in selecting the dashboard");
			preDashPage.clickOnElementsOfPreConfiguredDashboardPage("editButton");
			preDashPage.clickOnElementsOfPreConfiguredDashboardPage("copyPreConfigDashboard");
			preDashPage.clickOnElementsOfPreConfiguredDashboardPage("copyPreConfigDashboardNameField");
			preDashPage.enterTextForPreConfiguredDashboardPage("copyPreConfiguredDashboardNameInput",getEnvironmentSpecificData(System.getProperty("environment"),
					"COPY_DASHBOARD_NAME"));
			preDashPage.clickOnElementsOfPreConfiguredDashboardPage("addDashboardButton");
			waitForPageLoaded();
			preDashPage.matchTextOfPreConfiguredDashboardPage("currentDashboardView",getEnvironmentSpecificData(System.getProperty("environment"),
					"COPY_DASHBOARD_NAME"));
			preDashPage.clickOnElementsOfPreConfiguredDashboardPage("editButton");
			preDashPage.clickOnElementsOfPreConfiguredDashboardPage("deleteCustomDashboard");
			preDashPage.clickOnElementsOfPreConfiguredDashboardPage("deleteDashboardbutton");
		}
	}
	
}
		  
