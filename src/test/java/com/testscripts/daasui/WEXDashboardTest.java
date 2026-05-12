package com.testscripts.daasui;

import com.daasui.pages.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import com.basesource.action.PreDefinedActions;
import com.basetest.environments.SetTestEnvironments;
import com.daasui.constants.CommonVariables;
import com.daasui.constants.ConstantURL;
import com.daasui.constants.DeviceVariables;


public class WEXDashboardTest extends CommonTest {
	private static Logger LOGGER = LogManager.getLogger(WEXDashboardTest.class);
	public static String UserFirstname = getEnvironmentSpecificData(System.getProperty("environment"), "USER_FN");
	public static String UserLastname = getEnvironmentSpecificData(System.getProperty("environment"), "USER_LN");
	
	@DataProvider
	public Object[][] getLoginData() {
		Object[][] data = new Object[2][2];
		data[0][0] = "PARTNER_EMAIL_WEP";
		data[0][1] = "PARTNER_PASSWORD_WEP";
		data[1][0] = "ITADMIN_EMAIL_WEP";
		data[1][1] = "ITADMIN_PASSWORD_WEP";
		data[2][0] = "RESELLER_ADMIN_EMAIL";
		data[2][1] = "RESELLER_ADMIN_PASSWORD";
		return data;
	}
	
	@Test(priority = 1, groups = { "REGRESSION_WEX"})
	public final void verifyWEXDashboardPartnerView() throws Exception {
		login("RESELLER_ADMIN_EMAIL", "RESELLER_ADMIN_PASSWORD");
		WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
		leftSideMenuVerification();
		dashboardPage.partnerWithCompanyView(CommonVariables.PARTNER_DEFAULT_VIEW,CommonVariables.PARTNER_REQUESTS);
		waitForPageLoaded();
	}
	
	@Test(priority = 2, groups = { "REGRESSION_PLATFORMCX" })
	public final void verifyWEXDashboardPartnerWithCustomerView() throws Exception {
		login("PARTNER_EMAIL_WEP", "PARTNER_PASSWORD_WEP");
		WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
		leftSideMenuVerification();
		dashboardPage.partnerWithCompanyView(CommonVariables.CUSTOMER_NAME,CommonVariables.CUSTOMER_FLEET_MANAGEMENT_ANALYTICS);
		waitForPageLoaded();
	}
	
	@Test(priority = 3, groups = { "REGRESSION_PLATFORMCX" })
	public final void verifyWEXDashboardCustomerView() throws Exception {
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
		leftSideMenuVerification();
		dashboardPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT,CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);
		waitForPageLoaded();
	}
	
	
	/**
	 * This Test will verify the breadcrumbs functionality on WEX Portal when Customer logged In
	 * WEXALPA-1185 >>[WEX][Automation] Breadcrumb Page Automation.
	 * [Wex]Verify site header and breadcrumbs functionality with customer login
	 * @throws Exception
	 */
	@Test(priority = 4, groups = { "REGRESSION_PLATFORMCX"}, description = "TestCaseID:C42774197")
	public final void verifySideMenuAndBreadcrumbsFunctionalityOnWEXCustomerView() throws Exception {
		
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
		leftSideMenuVerification();
		waitForPageLoaded();
		sleeper(10000);// Url takes time to load
		
		// "Home" Side Menu Verification
		dashboardPage.companyView(CommonVariables.CUSTOMER_HOME);
		
		Assert.assertTrue(
				dashboardPage.getTextOfWexDashboardPage("homeSiteHeader")
				.equals(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "sidemenu.home")),"Home Site Header doesn't match");
		LOGGER.info("Home Site Header is matched");
		
		// "HardWare Support" Side Menu Verification
		if(dashboardPage.verifyElementsOfDashboardPage("companyHardwareSupport")) {
			dashboardPage.companyView(CommonVariables.CUSTOMER_HARDWARE_SUPPORTS);
			Assert.assertTrue(
					dashboardPage.getTextOfWexDashboardPage("hardwareSupportSiteHeader")
							.equals(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "sidemenu.hardwareSupport")), "hardware Support Site Header doesn't match");
			LOGGER.info("hardware Support Site Header is matched");
		}
		
		
		// "Alerts" Side Menu Verification
		dashboardPage.companyView(CommonVariables.CUSTOMER_ALERTS,CommonVariables.CUSTOMER_ALERTS);
	   // "Active Alerts" Sub Side Menu Verification	
		dashboardPage.companyView(CommonVariables.CUSTOMER_ALERTS,CommonVariables.CUSTOMER_ACTIVE_ALERTS);
		Assert.assertTrue(
				dashboardPage.getTextOfWexDashboardPage("activeAlertSiteHeader")
				.equals(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "alerts.active.fleet.breadcrumb")),"Active Alerts Site Header doesn't match");
		LOGGER.info("Active Alerts Site Header is matched");
		dashboardPage.companyView(CommonVariables.CUSTOMER_ALERTS,CommonVariables.CUSTOMER_ALERTS_MANAGEMENT);
		Assert.assertTrue(
				dashboardPage.getTextOfWexDashboardPage("alertsManagementSiteHeader")
				.equals(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "global_alertsManagement")),"Alerts Management Site Header doesn't match");
		LOGGER.info("Alerts Management Site Header is matched");
		
		//"Analytics" Side Menu Verification
		dashboardPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
		sleeper(3000);
		Assert.assertTrue(
				dashboardPage.getTextOfWexDashboardPage("analyticsSiteHeader")
				.equals(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "sidemenu.fleetAnalytics")),"Analytics Site Header doesn't match");
		LOGGER.info("Analytics Site Header is matched");
		
		
		// "Devices" Side Menu Verification
		dashboardPage.companyView(CommonVariables.CUSTOMER_DEVICES,CommonVariables.CUSTOMER_DEVICES);
		
		//"PCs" Sub Side Menu Verification under Device tab
		dashboardPage.companyView(CommonVariables.CUSTOMER_DEVICES,CommonVariables.CUSTOMER_DEVICES_PCS);
		Assert.assertTrue(dashboardPage.getTextOfWexDashboardPage("devicesPCsSiteHeader").equals(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "dashboard.pcinventory.total_label")),"PCs Site Header doesn't match");
		LOGGER.info("PCs Site Header is matched");
		
		// "Printer" Sub Side Menu Verification under Device tab
//		dashboardPage.companyView(CommonVariables.CUSTOMER_DEVICES,CommonVariables.CUSTOMER_DEVICES_PRINTERS);
//
//		Assert.assertTrue(
//				dashboardPage.getTextOfWexDashboardPage("devicesPrintersSiteHeader")
//				.equals(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "sidemenu.printers")),"Printers Site Header doesn't match");
//		LOGGER.info("Alerts Management Site Header is matched");
		
		// "Virtual Machine" Sub Side Menu Verification under Device tab
		dashboardPage.companyView(CommonVariables.CUSTOMER_DEVICES,CommonVariables.CUSTOMER_DEVICES_VIRTUALMACHINES);
		Assert.assertTrue(
				dashboardPage.getTextOfWexDashboardPage("devicesVirtualMachinesSiteHeader")
				.equals(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "ecp.virtual_machines")),"Virtual Machine Site Header doesn't match");
		LOGGER.info("Virtual Machine Site Header is matched");
		
		// "Groups" Side Menu Verification
		dashboardPage.companyView(CommonVariables.GROUPS);	
		Assert.assertTrue(
				dashboardPage.getTextOfWexDashboardPage("groupsSiteHeader")
				.equals(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "device.groups.sidebar.title")),"Groups Site Header doesn't match");
		LOGGER.info("Groups Site Header is matched");
		
		// "Remediations" Side Menu Verification
		dashboardPage.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS);
				
		//"Policies" Sub Side Menu Verification under Remediations tab
		dashboardPage.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
		Assert.assertTrue(dashboardPage.getTextOfWexDashboardPage("remediationsPoliciesSiteHeader").equals(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "breadcrumbs.policies")),"Policies Site Header doesn't match");
		LOGGER.info("Policies Site Header is matched");
				
		// "Scripts" Sub Side Menu Verification under Remediations tab
		dashboardPage.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_SCRIPTS);
				
		Assert.assertTrue(
			dashboardPage.getTextOfWexDashboardPage("remediationsScriptsSiteHeader")
				.equals(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "wex.script.library.header")),"Scripts Site Header doesn't match");
		LOGGER.info("Scripts Site Header is matched");
				
		// "Secrets" Sub Side Menu Verification under Remediations tab
		dashboardPage.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_SECRETS);
		Assert.assertTrue(
						dashboardPage.getTextOfWexDashboardPage("remediationsSecretsSiteHeader")
						.equals(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "sidemenu.hpcSecrets")),"Secrets Site Header doesn't match");
		LOGGER.info("Secrets Site Header is matched");
		
		// "Activity" Sub Side Menu Verification under Remediations tab
		dashboardPage.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_ACTIVITY);
		Assert.assertTrue(
						dashboardPage.getTextOfWexDashboardPage("remediationsActivitySiteHeader")
							.equals(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "wex.scripts.details.activity")),"Activity Site Header doesn't match");
		LOGGER.info("Activity Site Header is matched");
	 
		// "Pulses" Side Menu Verification
		dashboardPage.companyView(CommonVariables.PULSES);	
		Assert.assertTrue(
				dashboardPage.getTextOfWexDashboardPage("pulsesSiteHeader")
						.equals(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "global_pulses")),"Pulses Site Header doesn't match");
				LOGGER.info("Pulses Site Header is matched");
		
		// "Labs" Side Menu Verification
		dashboardPage.companyView(CommonVariables.LABS);	
		Assert.assertTrue(
				dashboardPage.getTextOfWexDashboardPage("labsSiteHeader")
						.equals(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "sidemenu.labs")),"Labs Site Header doesn't match");
				LOGGER.info("Labs Site Header is matched");
		
		//"Integration" Side Menu Verification
		dashboardPage.companyView(CommonVariables.INTEGRATIONS);	
		Assert.assertTrue(
		    dashboardPage.getTextOfWexDashboardPage("integrationsSiteHeader")
								.equals(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "integrations.appbar.title")),"Integrations Site Header doesn't match");
						LOGGER.info("Integrations Site Header is matched");
		
		//"Account" Side Menu Verification
		dashboardPage.companyView(CommonVariables.ACCOUNT);	
				Assert.assertTrue(
						dashboardPage.getTextOfWexDashboardPage("accountSiteHeader")
						.equals(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "sidemenu.account")),"Account Site Header doesn't match");
				LOGGER.info("Account Site Header is matched");
				
		//"Settings" Side Menu Verification
		dashboardPage.companyView(CommonVariables.SETTINGS);	
		Assert.assertTrue(
			dashboardPage.getTextOfWexDashboardPage("settingsSiteHeader")
			.equals(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "sidemenu.hpcSettings")),"Settings Site Header doesn't match");
		LOGGER.info("Settings Site Header is matched");
		
		
		
		// "Log" Page Verification 
		dashboardPage.clickOnElementsOfDashboardPage("logs");	
		Assert.assertTrue(
				dashboardPage.getTextOfWexDashboardPage("logsSiteHeader")
				.equals(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "sidemenu.logs")),"Logs Header doesn't match");
		LOGGER.info("Logs Site Header is matched");
		
		// "Help & Support" Page Verification 
		dashboardPage.clickOnElementsOfDashboardPage("helpDashboard");	
		Assert.assertTrue(
				dashboardPage.getTextOfWexDashboardPage("helpSiteHeader")
				.equals(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "sidemenu.support")),"Help and SUpport Site Header doesn't match");
		LOGGER.info("Help&Support Site Header is matched");
		 
	}
	
	/**
	 * This Test will verify the breadcrumbs functionality on WEX Portal when Partner logged In
	 * WEXALPA-1185 >>[WEX][Automation] Breadcrumb Page Automation. 
	 * [Wex]Verify site header and breadcrumbs functionality for partner login across all pages with All customers option selected
	 * @throws Exception
	 */
	@Test(priority = 5, groups = { "REGRESSION_PLATFORMCX"}, description = "TestCaseID:C42774205")
	public final void verifySideMenuWithBreadcrumbsFunctionalityOnWEXPartnerView() throws Exception {
		login("PARTNER_EMAIL_WEP", "PARTNER_PASSWORD_WEP");
		WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
		leftSideMenuVerification();
		waitForPageLoaded();
				
		// "Home" Side Menu Verification
		dashboardPage.partnerWithCompanyView(CommonVariables.PARTNER_DEFAULT_VIEW,CommonVariables.CUSTOMER_HOME);
		Assert.assertTrue(
			    dashboardPage.getTextOfWexDashboardPage("homeSiteHeader")
				.equals(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "sidemenu.home")),"Home Site Header doesn't match");
				LOGGER.info("Home Site Header is matched");
		// "Customers" Side Menu Verification
		dashboardPage.partnerWithCompanyView(CommonVariables.PARTNER_DEFAULT_VIEW,CommonVariables.PARTNER_CUSTOMERS);
		Assert.assertTrue(
				dashboardPage.getTextOfWexDashboardPage("customersSiteHeader")
				.equals(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "breadcrumbs.customers")),"Companies Site Header doesn't match");
		LOGGER.info("Companies Site Header is matched");

		// "product catalogs" Side Menu Verification
//		if(dashboardPage.verifyElementsOfDashboardPage("")) {
//			dashboardPage.partnerWithCompanyView(CommonVariables.PARTNER_DEFAULT_VIEW, CommonVariables.PARTNER_REQUESTS);
//			Assert.assertTrue(
//					dashboardPage.getTextOfWexDashboardPage("ProductcatlogDashboardSiteHeader")
//							.equals(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "partner.product.catalogs.breadcrumb")), "Product backlog breadcrumb doesn't match");
//			LOGGER.info("product catalogs breadcrumbs is matched");
//		}
		
		//"Analytics" Side Menu Verification
		dashboardPage.partnerWithCompanyView(CommonVariables.PARTNER_DEFAULT_VIEW,CommonVariables.PARTNER_ANALYTICS);
	 	Assert.assertTrue(
		dashboardPage.getTextOfWexDashboardPage("PartnerViewSiteHeaderAnalytics")
			 .equals(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "sidemenu.fleetAnalytics")),"Analytics Site Header doesn't match");
				LOGGER.info("Analytics Site Header is matched");
		
		//"Integration" Side Menu Verification
		dashboardPage.partnerWithCompanyView(CommonVariables.PARTNER_DEFAULT_VIEW,CommonVariables.INTEGRATIONS);	
		Assert.assertTrue(
		 dashboardPage.getTextOfWexDashboardPage("integrationsSiteHeader")
				.equals(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "integrations.appbar.title")),"Integrations Site Header doesn't match");
		LOGGER.info("Integrations Site Header is matched");
				
		//"Account" Side Menu Verification
		dashboardPage.partnerWithCompanyView(CommonVariables.PARTNER_DEFAULT_VIEW,CommonVariables.ACCOUNT);
		Assert.assertTrue(
			dashboardPage.getTextOfWexDashboardPage("accountSiteHeader")
			.equals("Account"),"Account Site Header doesn't match");
		LOGGER.info("Account Site Header is matched");
		
		//"Settings" Side Menu Verification
		dashboardPage.partnerWithCompanyView(CommonVariables.PARTNER_DEFAULT_VIEW,CommonVariables.SETTINGS);
		Assert.assertTrue(
				dashboardPage.getTextOfWexDashboardPage("settingsDashboard")
				.equals(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "flexible_dashboard.dashboard_wrapper.actions.settings")),"Settings Site Header doesn't match");
		LOGGER.info("Settings Site Header is matched");
		
		//"Help & Support" pages Verification
		dashboardPage.clickOnElementsOfDashboardPage("helpDashboard");	
		Assert.assertTrue(
				dashboardPage.getTextOfWexDashboardPage("helpSiteHeader")
				.equals(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "sidemenu.support")),"Help and SUpport Site Header doesn't match");
		LOGGER.info("Help&Support Site Header is matched");
	
	}
	
	/**
	 * This Test will verify the breadcrumbs functionality on WEX Portal when Partner Logged In and select specific Customer
	 * WEXALPA-1185 >>[WEX][Automation] Breadcrumb Page Automation.
	 * [Wex]Verify site header and breadcrumbs functionality for partner login across all pages with specific customer selection
	 * @throws Exception
	 */
	@Test(priority = 6, groups = { "REGRESSION_PLATFORMCX"}, description = "TestCaseID:C41379539")
	public final void verifySideMenuWithBreadcrumbsFunctionalityOnWEXCustomerViewFromPartner() throws Exception {
		login("PARTNER_EMAIL_WEP", "PARTNER_PASSWORD_WEP");
		WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
		leftSideMenuVerification();
		String environment = System.getProperty("environment");
		if ("US-Stage-WEP".equalsIgnoreCase(environment) || "US-VENEER-WEP".equalsIgnoreCase(environment)) {
			selectCompany(getEnvironmentSpecificData(environment, "COMPANY_NAME"));
		} else {
			selectCompany(getEnvironmentSpecificData(environment, "CUSTOMER_NAME"));
		}
		waitForPageLoaded();
		sleeper(5000);
				
		// "Home" Side Menu Verification
		dashboardPage.companyView(CommonVariables.CUSTOMER_HOME);
		Assert.assertTrue(
				dashboardPage.getTextOfWexDashboardPage("homeSiteHeader")
				.equals(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "sidemenu.home")),"Home Site Header doesn't match");
		LOGGER.info("Home Site Header is matched");
		
		
		// "HardWare Support" Side Menu Verification
		if(dashboardPage.verifyElementsOfDashboardPage("companyHardwareSupport")) {
			dashboardPage.companyView(CommonVariables.CUSTOMER_HARDWARE_SUPPORTS);
			sleeper(1000);
			Assert.assertTrue(
					dashboardPage.getTextOfWexDashboardPage("hardwareSupportSiteHeader")
							.equals(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "sidemenu.hardwareSupport")), "hardware Support Site Header doesn't match");
			LOGGER.info("hardware Support Site Header is matched");
		}
				
		
		//"Analytics" Side Menu Verification
		dashboardPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
		sleeper(5000);
	 	Assert.assertTrue(
		dashboardPage.getTextOfWexDashboardPage("PartnerViewSiteHeaderAnalytics")
			 .equals(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "sidemenu.fleetAnalytics")),"Analytics Site Header doesn't match");
				LOGGER.info("Analytics Site Header is matched");
				
				
		// "Devices" Side Menu Verification
		dashboardPage.companyView(CommonVariables.CUSTOMER_DEVICES,CommonVariables.CUSTOMER_DEVICES);
				
		//"PCs" Sub Side Menu Verification under Device tab
		dashboardPage.companyView(CommonVariables.CUSTOMER_DEVICES,CommonVariables.CUSTOMER_DEVICES_PCS);
		Assert.assertTrue(dashboardPage.getTextOfWexDashboardPage("devicesPCsSiteHeader").equals(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "dashboard.pcinventory.total_label")),"PCs Site Header doesn't match");
		LOGGER.info("PCs Site Header is matched");
				
		// "Printer" Sub Side Menu Verification under Device tab
//		dashboardPage.companyView(CommonVariables.CUSTOMER_DEVICES,CommonVariables.CUSTOMER_DEVICES_PRINTERS);
//
//		Assert.assertTrue(
//						dashboardPage.getTextOfWexDashboardPage("devicesPrintersSiteHeader")
//						.equals(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "sidemenu.printers")),"Printers Site Header doesn't match");
//		LOGGER.info("Alerts Management Site Header is matched");
				
		// "Virtual Machine" Sub Side Menu Verification under Device tab
		dashboardPage.companyView(CommonVariables.CUSTOMER_DEVICES,CommonVariables.CUSTOMER_DEVICES_VIRTUALMACHINES);
		Assert.assertTrue(
						dashboardPage.getTextOfWexDashboardPage("devicesVirtualMachinesSiteHeader")
						.equals(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "ecp.virtual_machines")),"Virtual Machine Site Header doesn't match");
		LOGGER.info("Virtual Machine Site Header is matched");
				
		// "Remediations" Side Menu Verification
		dashboardPage.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS);
						
		//"Policies" Sub Side Menu Verification under Remediations tab
		dashboardPage.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_POLICIES);
		Assert.assertTrue(dashboardPage.getTextOfWexDashboardPage("remediationsPoliciesSiteHeader").equals(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "breadcrumbs.policies")),"Policies Site Header doesn't match");
		LOGGER.info("Policies Site Header is matched");
											
		// "Secrets" Sub Side Menu Verification under Remediations tab
		dashboardPage.companyView(CommonVariables.CUSTOMER_REMEDIATIONS,CommonVariables.CUSTOMER_REMEDIATIONS_SECRETS);
		Assert.assertTrue(dashboardPage.getTextOfWexDashboardPage("remediationsSecretsSiteHeader")
								.equals(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "sidemenu.hpcSecrets")),"Secrets Site Header doesn't match");
		LOGGER.info("Secrets Site Header is matched");
				
		// "Pulses" Side Menu Verification
		dashboardPage.companyView(CommonVariables.PULSES);	
		Assert.assertTrue(dashboardPage.getTextOfWexDashboardPage("pulsesSiteHeader")
								.equals(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "global_pulses")),"Pulses Site Header doesn't match");
						LOGGER.info("Pulses Site Header is matched");
				
		// "Labs" Side Menu Verification
		dashboardPage.companyView(CommonVariables.LABS);	
		Assert.assertTrue(dashboardPage.getTextOfWexDashboardPage("labsSiteHeader")
								.equals(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "sidemenu.labs")),"Labs Site Header doesn't match");
						LOGGER.info("Labs Site Header is matched");
				
		//"Integration" Side Menu Verification
		dashboardPage.companyView(CommonVariables.INTEGRATIONS);	
		Assert.assertTrue(dashboardPage.getTextOfWexDashboardPage("integrationsSiteHeader")
										.equals(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "integrations.appbar.title")),"Integrations Site Header doesn't match");
		LOGGER.info("Integrations Site Header is matched");
				
	    //"Account" Side Menu Verification
		dashboardPage.companyView(CommonVariables.ACCOUNT);	
		Assert.assertTrue(dashboardPage.getTextOfWexDashboardPage("accountSiteHeader")
								.equals(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "sidemenu.account")),"Account Site Header doesn't match");
						LOGGER.info("Account Site Header is matched");
						
		//"Settings" Side Menu Verification
		dashboardPage.companyView(CommonVariables.SETTINGS);	
		Assert.assertTrue(dashboardPage.getTextOfWexDashboardPage("settingsSiteHeader")
					.equals(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "sidemenu.hpcSettings")),"Settings Site Header doesn't match");
				LOGGER.info("Settings Site Header is matched");
				
		
	}
	

	/**
	 * [Wex]Verify site header and breadcrumbs functionality for part
	 * WEXALPA-1186 >>[WEX][Automation] User Dropdown Page Automation.
	 * This Test Case will verify the Switch Account functionality
	 * @throws Exception
	 */
	@Test(priority = 7, groups = { "REGRESSION_ACCOUNTS"}, description = "TestCaseID:C42417467")
	public final void verifySwitchFunctionality() throws Exception {	
		login("SWITCH_EMAIL_WEP", "SWITCH_PASSWORD_WEP");
		SoftAssert softAssert = new SoftAssert();
		WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
		waitForPageLoaded();
		dashboardPage.waitForElementsOfDashboardPage("userProfileButton");
		dashboardPage.clickOnElementsOfDashboardPage("userProfileButton");
		dashboardPage.waitForElementsOfDashboardPage("switchAccount");
		dashboardPage.clickOnElementsOfDashboardPage("switchAccount");
		softAssert.assertTrue(dashboardPage.matchTextOfDashboardPage("switchAccountPopupHeader", getTextLanguage(LanguageCode, "daas_ui", "users.switchAccount")), "Switch Account title on popup is incorrect");
		dashboardPage.verifySwitchAccount(LanguageCode);
		softAssert.assertAll();
		LOGGER.info("Switch account functionality verified successfully");
	}
	
	/**
	 * WEXALPA-1195 >>[WEX][Automation] WEX bell icon Notification Automation throughout the Portal.
	 * This Test Case will verify the notification bell icon on dashboard.
	 * @throws Exception
	 */
	@Test(priority = 8, groups = {"REGRESSION_PLATFORMCX" }, description = "TestCaseID:C42197913")
	public final void verifyNotificationBellIcon() throws Exception {	
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		SoftAssert softAssert = new SoftAssert();
		WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
		waitForPageLoaded();
		dashboardPage.waitForElementsOfDashboardPage("notificationButton");
		softAssert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("notificationButton"));
		dashboardPage.clickOnElementsOfDashboardPage("notificationButton");
		dashboardPage.waitForElementsOfDashboardPage("notificationtitle");
		softAssert.assertTrue(dashboardPage.getTextOfWexDashboardPage("notificationtitle").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "notifications.i18n.title")), "Notifications are not loading.");
		softAssert.assertAll();
		LOGGER.info("Notification bell icon functionality verified successfully");
	}

	/**
	 * C42397646 >>Wex dashboard testing for root company creation.(SystemHealth Score chart verification)
	 * @throws Exception
	 */
	@Test(priority = 9, groups = { "REGRESSION_INTEGRATIONQA_CUJ", "PRODUCTION_INTEGRATIONQA_CUJ" },description = "Test Case: C42397646, C42806960")
	public final void verifyDashboardManagement() throws Exception {
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		leftSideMenuVerification();
		dashboardPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DASHBOARD);
		waitForPageLoaded();
		sleeper(3000);

		//Steps 3,4,5,6,7
		Assert.assertTrue(dashboardPage.waitForElementsOfDashboardPage("systemHealthScores")," System Health score Card did not load on FM Dashboard.");
		Assert.assertTrue(dashboardPage.waitForElementsOfDashboardPage("osPerformanceFM")," OS Performance score Card did not load on FM Dashboard.");
		Assert.assertTrue(dashboardPage.waitForElementsOfDashboardPage("securityFM")," Security score Card did not load on FM Dashboard.");
		Assert.assertTrue(dashboardPage.waitForElementsOfDashboardPage("applicationFM")," Application Card did not load on FM Dashboard.");
		Assert.assertTrue(dashboardPage.waitForElementsOfDashboardPage("recommendedActionsFM")," RA Card did not load on FM Dashboard.");
		dashboardPage.scrollToDashboardPage("pcInventoryFM");
		Assert.assertTrue(dashboardPage.waitForElementsOfDashboardPage("pcInventoryFM")," PC InventoryFM Card did not load on FM Dashboard.");
		Assert.assertTrue(dashboardPage.waitForElementsOfDashboardPage("printerManagementFM")," Printer Management/Inventory Card did not load on FM Dashboard.");
		Assert.assertTrue(dashboardPage.waitForElementsOfDashboardPage("appsWithMostCrashFM")," Apps with most crashes Card did not load on FM Dashboard.");
		Assert.assertTrue(dashboardPage.waitForElementsOfDashboardPage("powerConsumptionOverTimeFM")," Power Consumption Over Time Card did not load on FM Dashboard.");
		Assert.assertTrue(dashboardPage.waitForElementsOfDashboardPage("hpBiosPoliciesFM")," HP PC BIOS Policies Card did not load on FM Dashboard.");
		Assert.assertTrue(dashboardPage.waitForElementsOfDashboardPage("bsodOverTimeFM")," BSOD's Over Time Card did not load on FM Dashboard.");


		String totalFMDevice = dashboardPage.getTextOfWexDashboardPage("systemHealthScorePrgressBarVal");
		int totalFMDevices = Integer.parseInt(totalFMDevice);
		LOGGER.info("Count from device details page" + totalFMDevices);
		dashboardPage.clickOnElementsOfDashboardPage("systemHealthScorePrgressBar");

		String totalFMInsideDevice = dashboardPage.getTextOfWexDashboardPage("systemHealthScorPrgrsBarVal");
		int totalFMDInsidevices = Integer.parseInt(totalFMInsideDevice);
		LOGGER.info("Count from device details page" + totalFMDInsidevices);

		// Verify system health score count
		Assert.assertEquals(totalFMDInsidevices, totalFMDevices, "Mismatch found: The system health score counts are different.");

		Assert.assertTrue(dashboardPage.waitForElementsOfDashboardPage("systemHealth")," System Health Card did not load on Dashboard.");
		Assert.assertTrue(dashboardPage.waitForElementsOfDashboardPage("osPerformance")," OS Performance Card did not load on Dashboard.");
		Assert.assertTrue(dashboardPage.waitForElementsOfDashboardPage("systemHealthScore")," System health score Card did not load on Dashboard.");
		Assert.assertTrue(dashboardPage.waitForElementsOfDashboardPage("topSystemHealthIssue")," Top System health issue Card did not load on Dashboard.");
		Assert.assertTrue(dashboardPage.waitForElementsOfDashboardPage("systemHealthOvrTime")," System health over time Card did not load on Dashboard.");

		if (!dashboardPage.waitForElementsOfDashboardPage("fairProgressBar")) {
			LOGGER.info("fairProgressBar not found. Skipping the code execution.");
		} else {
			String totalFairDevice = dashboardPage.getTextOfWexDashboardPage("fairProgressBarVal");
			int totalFairDevices = Integer.parseInt(totalFairDevice);
			LOGGER.info("Count of totalFairDevices from device details page: " + totalFairDevices);
			dashboardPage.clickOnElementsOfDashboardPage("fairProgressBar");
			Thread.sleep(2000);
			dashboardPage.scrollToDashboardPage("fairProgressBarVals");
			waitForPageLoaded();
			sleeper(2000);
			String TotalFairDevice = dashboardPage.getTextOfWexDashboardPage("fairProgressBarVals").replace("of", "").trim();
			int TotalFairDevices = Integer.parseInt(TotalFairDevice);
			LOGGER.info("Count of TotalFairDevices from device details page: " + TotalFairDevices);
			Assert.assertEquals(TotalFairDevices, totalFairDevices, "Mismatch found: The progress bar device counts are different.");
			navigateToBack();
		}

		if (!dashboardPage.waitForElementsOfDashboardPage("greatProgressBar")) {
			LOGGER.info("greatProgressBar not found. Skipping the code execution.");
		} else {
			String totalFairDevice = dashboardPage.getTextOfWexDashboardPage("greatProgressBarVal");
			int totalGreatDevice = Integer.parseInt(totalFairDevice);
			LOGGER.info("Count of totalFairDevices from device details page: " + totalGreatDevice);
			dashboardPage.clickOnElementsOfDashboardPage("greatProgressBar");
			sleeper(3000);

			dashboardPage.scrollToDashboardPage("fairProgressBarVals");
			waitForPageLoaded();
			sleeper(2000);
			String TotalFairDevice = dashboardPage.getTextOfWexDashboardPage("fairProgressBarVals").replace("of", "").trim();
			int TotalFairDevices = Integer.parseInt(TotalFairDevice);
			LOGGER.info("Count of TotalFairDevices from device details page: " + TotalFairDevices);
			Assert.assertEquals(TotalFairDevices, totalGreatDevice, "Mismatch found: The progress bar device counts are different.");
			navigateToBack();
		}

		if (!dashboardPage.waitForElementsOfDashboardPage("poorProgressBar")) {
			LOGGER.info("poorProgressBar not found. Skipping the code execution.");
		} else {
			String totalPoorDevice = dashboardPage.getTextOfWexDashboardPage("poorProgressBarVal");
			int totalPoorDevices = Integer.parseInt(totalPoorDevice);
			LOGGER.info("Count of totalPoorDevices from device details page: " + totalPoorDevices);
			dashboardPage.clickOnElementsOfDashboardPage("poorProgressBar");
			sleeper(3000);

			dashboardPage.scrollToDashboardPage("fairProgressBarVals");
			waitForPageLoaded();
			sleeper(2000);
			String TotalFairDevice = dashboardPage.getTextOfWexDashboardPage("fairProgressBarVals").replace("of", "").trim();
			int TotalFairDevices = Integer.parseInt(TotalFairDevice);
			LOGGER.info("Count of TotalFairDevices from device details page: " + TotalFairDevices);
			Assert.assertEquals(TotalFairDevices, totalPoorDevices, "Mismatch found: The progress bar device counts are different.");
			navigateToBack();
		}

		Assert.assertTrue(dashboardPage.waitForElementsOfDashboardPage("systemHealthChangeLastWeek")," System health changes last week card did not load on Dashboard.");
		dashboardPage.scrollToDashboardPage("recommendedActions");
		Assert.assertTrue(dashboardPage.waitForElementsOfDashboardPage("deviceByModelHealth")," System health device by model card did not load on Dashboard.");
		Assert.assertTrue(dashboardPage.waitForElementsOfDashboardPage("recommendedActions")," System health RA card did not load on Dashboard.");
		LOGGER.info("System Health tab validation has been completed successfully.");

		softAssert.assertAll();
	}

	@Test(priority = 10, groups = { "REGRESSION_INTEGRATIONQA_CUJ" },description = "Test Case: C42806960", enabled = false)
	public final void verifyDashboardManagementselfSign() throws Exception {
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
		dashboardPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT, CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DASHBOARD);
		Assert.assertTrue(dashboardPage.waitForElementsOfDashboardPage("applicationScoreValue")," Application Score Card did not load on Dashboard.");
		Assert.assertTrue(dashboardPage.waitForElementsOfDashboardPage("securityScoreValue")," Security Score Card did not load on Dashboard.");
		Assert.assertTrue(dashboardPage.waitForElementsOfDashboardPage("pcInventory")," PC inventory Card did not load on Dashboard.");
		Assert.assertTrue(dashboardPage.waitForElementsOfDashboardPage("printersInventory")," Printer inventory Card did not load on Dashboard.");
		Assert.assertTrue(dashboardPage.waitForElementsOfDashboardPage("topCrashes")," Top crashes tab Card did not load on Dashboard.");
		Assert.assertTrue(dashboardPage.waitForElementsOfDashboardPage("bsodOverTime")," BSOD overtime tab Card did not load on Dashboard.");
		Assert.assertTrue(dashboardPage.waitForElementsOfDashboardPage("hpPCBiosPolicies")," HP Pc BIOS policies tab Card did not load on Dashboard.");
		LOGGER.info("Validation of Experience Management page from Dashboard has been completed successfully");

		dashboardPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DASHBOARD);
		waitForPageLoaded();
		sleeper(3000);
		dashboardPage.clickOnElementsOfDashboardPage("systemHealthScorePrgressBar");
		dashboardPage.scrollToDashboardPage("changesSinceLastWeekChartTitleSystemHealth");
		Assert.assertTrue(dashboardPage.waitForElementsOfDashboardPage("systemHealth")," System Health Card did not load on Dashboard.");
		Assert.assertTrue(dashboardPage.waitForElementsOfDashboardPage("osPerformance")," OS Performance Card did not load on Dashboard.");
		Assert.assertTrue(dashboardPage.waitForElementsOfDashboardPage("systemHealthScore")," System health score Card did not load on Dashboard.");
		Assert.assertTrue(dashboardPage.waitForElementsOfDashboardPage("systemHealthSummary")," System health score Card did not load on Dashboard.");
		Assert.assertTrue(dashboardPage.waitForElementsOfDashboardPage("topSystemHealthIssue")," Top System health issue Card did not load on Dashboard.");
		LOGGER.info("System Health tab validation has been completed successfully");

		dashboardPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DASHBOARD);
		waitForPageLoaded();
		sleeper(3000);
		dashboardPage.clickOnElementsOfDashboardPage("osSystemReliabilityPrgressBar");
		Assert.assertTrue(dashboardPage.waitForElementsOfDashboardPage("systemHealth")," System Health Card did not load on Dashboard.");
		Assert.assertTrue(dashboardPage.waitForElementsOfDashboardPage("osPerformance")," OS Performance Card did not load on Dashboard.");
		Assert.assertTrue(dashboardPage.waitForElementsOfDashboardPage("osPerformanceScore")," OS Performance Score Card did not load on Dashboard.");
		Assert.assertTrue(dashboardPage.waitForElementsOfDashboardPage("osPerformanceSummary")," OS Performance Summary Card did not load on Dashboard.");
		Assert.assertTrue(dashboardPage.waitForElementsOfDashboardPage("topOsPerformanceIssue")," TOP Performance Issue Card did not load on Dashboard.");
		Assert.assertTrue(dashboardPage.waitForElementsOfDashboardPage("osPerformanceOverTime")," OS Performance Over Time Card did not load on Dashboard.");
		LOGGER.info("OS Performance tab validation has been completed successfully");
		LOGGER.info("Wex dashboard testing form self sign up company creation has been completed successfully.");
	}

	@Test(priority = 11, groups = { "REGRESSION_INTEGRATIONQA_CUJ" },description = "Test Case: C42397650", enabled = false)
	public final void verifyDashboardManagementWithDiffRole() throws Exception {
		String UserEmail = UserFirstname+UserLastname+generateRandomString(3)+CommonVariables.EMAIL_DOMAIN;
		String UserEmails = UserFirstname+UserLastname+generateRandomString(5)+CommonVariables.EMAIL_DOMAIN;
		SoftAssert softAssert = new SoftAssert();
		WEXCustomerUserListPage WEXCustomerUserListPage = new WEXCustomerUserListPage(PreDefinedActions.getDriver()).getInstance();

		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		leftSideMenuVerification();
		WEXCustomerUserListPage.companyView(CommonVariables.ACCOUNT_MANAGEMENT);
		waitForPageLoaded();
		LOGGER.info("Redirected Account management-Overview tab page");

		WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("usersTab");
		WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("addUserButton");

		WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("addManually");
		WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("addCSV");

		WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("addManually");
		softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("addAnotherUserBtn"), "Add user button missing.");
		WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("addUserCancelBtn");


		WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("addAnotherUserBtn");
		WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("addUserCancelBtn");
		WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("addUserBackBtn");
		WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("addUserNextBtn");

		WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("firstNameTextBox");
		WEXCustomerUserListPage.enterTextOfWEXCustomerUserListPage("firstNameTextBox", UserFirstname);
		WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("lastNameTextBox");
		WEXCustomerUserListPage.enterTextOfWEXCustomerUserListPage("lastNameTextBox", UserLastname);
		WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("emailTextBox");
		WEXCustomerUserListPage.enterTextOfWEXCustomerUserListPage("emailTextBox", UserEmail);
		WEXCustomerUserListPage.actionClickOnWEXCustomerUserListPage("idpTypeDropDown");
		WEXCustomerUserListPage.selectFirstValueFromDropdownOnWEXCustomerUserListPage("idTypeDropDownList");

		WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("addUserNextBtn");
		waitForPageLoaded();
		WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("clearRole");
		WEXCustomerUserListPage.actionClickOnWEXCustomerUserListPage("selectRole");
		WEXCustomerUserListPage.mousehoverOnWEXCustomerUserListPage("searchRole");
		WEXCustomerUserListPage.actionClickOnWEXCustomerUserListPage("searchRole");
		WEXCustomerUserListPage.enterTextOfWEXCustomerUserListPage("searchRole", CommonVariables.CONNECTOR_ADMIN);
		WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("roleValues");
		WEXCustomerUserListPage.actionClickOnWEXCustomerUserListPage("selectRole");
		softAssert.assertTrue(WEXCustomerUserListPage.matchTextOnWEXCustomerUserListPage("roleDropResult",CommonVariables.CONNECTOR_ADMIN), "Role drop down result is incorrect");
		WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("addUserNextBtn");
		WEXCustomerUserListPage.waitForElementsOfWEXCustomerUserListPage("toastMessage");
		WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("toastMessage");

		WEXCustomerUserListPage.enterTextOfWEXCustomerUserListPage("emailInput", UserEmail);
		Thread.sleep(2000);
		WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("emailInputValue");
		softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("inviteTab"), "Invite state not available.");
		softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("roleVals"), "Role button not available.");

//		Adding new customer with new role
		WEXCustomerUserListPage.companyView(CommonVariables.ACCOUNT_MANAGEMENT);
		waitForPageLoaded();
		LOGGER.info("Redirected Account management-Overview tab page");

		WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("usersTab");
		WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("addUserButton");

		WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("addManually");
		WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("addCSV");

		WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("addManually");
		softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("addAnotherUserBtn"), "Add user button missing.");
		WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("addUserCancelBtn");


		WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("addAnotherUserBtn");
		WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("addUserCancelBtn");
		WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("addUserBackBtn");
		WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("addUserNextBtn");

		WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("firstNameTextBox");
		WEXCustomerUserListPage.enterTextOfWEXCustomerUserListPage("firstNameTextBox", UserFirstname);
		WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("lastNameTextBox");
		WEXCustomerUserListPage.enterTextOfWEXCustomerUserListPage("lastNameTextBox", UserLastname);
		WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("emailTextBox");
		WEXCustomerUserListPage.enterTextOfWEXCustomerUserListPage("emailTextBox", UserEmails);
		WEXCustomerUserListPage.actionClickOnWEXCustomerUserListPage("idpTypeDropDown");
		WEXCustomerUserListPage.selectFirstValueFromDropdownOnWEXCustomerUserListPage("idTypeDropDownList");

		WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("addUserNextBtn");
		waitForPageLoaded();
		WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("clearRole");
		WEXCustomerUserListPage.actionClickOnWEXCustomerUserListPage("selectRole");
		WEXCustomerUserListPage.mousehoverOnWEXCustomerUserListPage("searchRole");
		WEXCustomerUserListPage.actionClickOnWEXCustomerUserListPage("searchRole");
		WEXCustomerUserListPage.enterTextOfWEXCustomerUserListPage("searchRole", CommonVariables.REPORT_ADMIN);
		WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("roleValues");
		WEXCustomerUserListPage.actionClickOnWEXCustomerUserListPage("selectRole");
		softAssert.assertTrue(WEXCustomerUserListPage.matchTextOnWEXCustomerUserListPage("roleDropResult",CommonVariables.REPORT_ADMIN), "Role drop down result is incorrect");
		WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("addUserNextBtn");
		WEXCustomerUserListPage.waitForElementsOfWEXCustomerUserListPage("toastMessage");
		WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("toastMessage");

		WEXCustomerUserListPage.enterTextOfWEXCustomerUserListPage("emailInput", UserEmails);
		Thread.sleep(2000);
		WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("emailInputValue");
		softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("inviteTab"), "Invite state not available.");
		softAssert.assertTrue(WEXCustomerUserListPage.verifyElementsOfWEXCustomerUserListPage("roleVals"), "Role button not available.");

		LOGGER.info("Customer User added successfully");
		softAssert.assertAll();
}

	/**
	 * C42397646 >>Wex dashboard testing for root company creation.(OS Performance chart verification)
	 * @throws Exception
	 */
	@Test(priority = 13, groups = { "REGRESSION_INTEGRATIONQA_CUJ", "PRODUCTION_INTEGRATIONQA_CUJ" },description = "Test Case: C42397646, C42806960")
	public final void verifyDashboardManagementOS() throws Exception {
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		leftSideMenuVerification();
		dashboardPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DASHBOARD);
		waitForPageLoaded();
		sleeper(3000);

		//Steps 8, 9, 10
		Assert.assertTrue(dashboardPage.waitForElementsOfDashboardPage("osPerformanceFM")," OS Performance score Card did not load on FM Dashboard.");

		String totalFMDevice = dashboardPage.getTextOfWexDashboardPage("osPerfScorePrgressBarVal");
		int totalFMDevices = Integer.parseInt(totalFMDevice);
		LOGGER.info("Count of osPerfScorePrgressBarVal from device details page" + totalFMDevices);
		dashboardPage.clickOnElementsOfDashboardPage("osSystemReliabilityPrgressBar");

		String totalFMInsideDevice = dashboardPage.getTextOfWexDashboardPage("osPerfScorPrgrsBarVal");
		int totalFMDInsidevices = Integer.parseInt(totalFMInsideDevice);
		LOGGER.info("Count os osPerfScorPrgrsBarVal from device details page" + totalFMDInsidevices);

		Assert.assertEquals(totalFMDInsidevices, totalFMDevices, "Mismatch found: The OS performance score counts are different.");

		Assert.assertTrue(dashboardPage.waitForElementsOfDashboardPage("systemHealth")," System Health Card did not load on Dashboard.");
		Assert.assertTrue(dashboardPage.waitForElementsOfDashboardPage("osPerformance")," OS Performance Card did not load on Dashboard.");
		Assert.assertTrue(dashboardPage.waitForElementsOfDashboardPage("osPerformanceScore")," OS Performance Score Card did not load on Dashboard.");
		dashboardPage.waitForElementsOfDashboardPage("fairProgressBar");
		dashboardPage.waitForElementsOfDashboardPage("poorProgressBar");
		dashboardPage.waitForElementsOfDashboardPage("greatProgressBar");

		Assert.assertTrue(dashboardPage.waitForElementsOfDashboardPage("topOSPerformanceIssue")," Tops OS Performance issues Card did not load on Dashboard.");
		Assert.assertTrue(dashboardPage.waitForElementsOfDashboardPage("OSPerformanceOverTime")," OS Performance over time Card did not load on Dashboard.");
		Assert.assertTrue(dashboardPage.waitForElementsOfDashboardPage("OSPerformanceChangesLastWeek")," OS Performance Changes Since Last Week Card did not load on Dashboard.");
		dashboardPage.scrollToDashboardPage("recommendedActions");
		Assert.assertTrue(dashboardPage.waitForElementsOfDashboardPage("OSPerformanceModel")," OS Performance by Model Card did not load on Dashboard.");
		Assert.assertTrue(dashboardPage.waitForElementsOfDashboardPage("recommendedActions")," Recommended Actions Card did not load on Dashboard.");
		LOGGER.info("OS Performance tab validation has been completed successfully.");

		softAssert.assertAll();
	}

	/**
	 * C42397646 >>Wex dashboard testing for root company creation.(Security chart verification)
	 * @throws Exception
	 */
	@Test(priority = 14, groups = { "REGRESSION_INTEGRATIONQA_CUJ", "PRODUCTION_INTEGRATIONQA_CUJ" },description = "Test Case: C42397646, C42806960")
	public final void verifyDashboardManagementSecurity() throws Exception {
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		leftSideMenuVerification();
		dashboardPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DASHBOARD);
		waitForPageLoaded();
		sleeper(3000);

		//steps 11,12,13
		Assert.assertTrue(dashboardPage.waitForElementsOfDashboardPage("securityPerformanceFM")," Security score Card did not load on FM Dashboard.");

		String totalFMDevice = dashboardPage.getTextOfWexDashboardPage("securityPrgressBarVal");
		int totalFMDevices = Integer.parseInt(totalFMDevice);
		LOGGER.info("Count of osPerfScorePrgressBarVal from device details page" + totalFMDevices);
		dashboardPage.clickOnElementsOfDashboardPage("SecurityPrgressBar");

		String totalFMInsideDevice = dashboardPage.getTextOfWexDashboardPage("securityScorPrgrsBarVal");
		int totalFMDInsidevices = Integer.parseInt(totalFMInsideDevice);
		LOGGER.info("Count os osPerfScorPrgrsBarVal from device details page" + totalFMDInsidevices);

		Assert.assertEquals(totalFMDInsidevices, totalFMDevices, "Mismatch found: The Security score counts are different.");

		Assert.assertTrue(dashboardPage.waitForElementsOfDashboardPage("securtyScore")," Security Score Card did not load on Dashboard.");
		dashboardPage.waitForElementsOfDashboardPage("fairProgressBar");
		dashboardPage.waitForElementsOfDashboardPage("poorProgressBar");
		dashboardPage.waitForElementsOfDashboardPage("greatProgressBar");
		Assert.assertTrue(dashboardPage.waitForElementsOfDashboardPage("topSecurityIssue")," Tops Security Issue Card did not load on Dashboard.");
		Assert.assertTrue(dashboardPage.waitForElementsOfDashboardPage("securityOverTime")," Security over time Card did not load on Dashboard.");
		Assert.assertTrue(dashboardPage.waitForElementsOfDashboardPage("securityChangesLastWeek")," Security Changes Since Last Week Card did not load on Dashboard.");
		dashboardPage.scrollToDashboardPage("securityChangesLastWeek");
		Assert.assertTrue(dashboardPage.waitForElementsOfDashboardPage("securityByModel")," Security by Model Card did not load on Dashboard.");
		Assert.assertTrue(dashboardPage.waitForElementsOfDashboardPage("recommendedActions")," RA Card did not load on Dashboard.");
		LOGGER.info("Security score tab validation has been completed successfully.");

		softAssert.assertAll();
	}

	/**
	 * C42397646 >>Wex dashboard testing for root company creation.(Application chart verification)
	 * @throws Exception
	 */
	@Test(priority = 13, groups = { "REGRESSION_INTEGRATIONQA_CUJ", "PRODUCTION_INTEGRATIONQA_CUJ" },description = "Test Case: C42397646, C42806960")
	public final void verifyDashboardManagementApplication() throws Exception {
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
		DEXPage dexPage = new DEXPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		leftSideMenuVerification();
		dashboardPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DASHBOARD);
		waitForPageLoaded();
		sleeper(3000);

		//step 15
		Assert.assertTrue(dashboardPage.waitForElementsOfDashboardPage("recommendedActionsFM")," RA Card did not load on FM Dashboard.");

		//step 14
		Assert.assertTrue(dashboardPage.waitForElementsOfDashboardPage("ApplicationsFM")," Applications score Card did not load on FM Dashboard.");

		String totalFMDevice = dashboardPage.getTextOfWexDashboardPage("ApplicationsPrgressBarVal");
		int totalFMDevices = Integer.parseInt(totalFMDevice);
		LOGGER.info("Count of ApplicationsPrgressBarVal from device details page" + totalFMDevices);
		dashboardPage.clickOnElementsOfDashboardPage("applicationPrgressBar");

		String totalFMInsideDevice = dashboardPage.getTextOfWexDashboardPage("AppScorPrgrsBarVal");
		int totalFMDInsidevices = Integer.parseInt(totalFMInsideDevice);
		LOGGER.info("Count os AppScorPrgrsBarVal from device details page" + totalFMDInsidevices);

		Assert.assertEquals(totalFMDInsidevices, totalFMDevices, "Mismatch found: The Applications score counts are different.");

		Assert.assertTrue(dashboardPage.waitForElementsOfDashboardPage("applicationScores")," Application Score Card did not load on Dashboard.");
		Assert.assertTrue(dashboardPage.waitForElementsOfDashboardPage("appWithMostCrashes")," Apps with Most Crashes Card did not load on Dashboard.");
		Assert.assertTrue(dashboardPage.waitForElementsOfDashboardPage("appWithMostFreeze")," Apps with Most Freezes Card did not load on Dashboard.");
		Assert.assertTrue(dashboardPage.waitForElementsOfDashboardPage("crashesAndFreeze")," Crashes and Freezes Over Time Card did not load on Dashboard.");
		//Assert.assertTrue(dashboardPage.waitForElementsOfDashboardPage("exportButton")," Export option missing.");
		//Assert.assertTrue(dashboardPage.waitForElementsOfDashboardPage("gridTable")," Grid table.");

		softAssert.assertAll();
	}
	
	
	@Test(priority = 14, groups = { "PRODUCTION_PLATFORMCX" },description = "TestCaseID:C42269272")
	public final void verifyHomeDashboard () throws Exception {
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		softAssert.assertTrue(dashboardPage.getTextOfWexDashboardPage("experienceSore").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.list.wexScore")), "Send Experience Score title are not loading.");
		softAssert.assertTrue(dashboardPage.getTextOfWexDashboardPage("experienceOverTime").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "ee_wex_over_time_heading")), "Send Experience Over time title are not loading.");
		softAssert.assertTrue(dashboardPage.getTextOfWexDashboardPage("fleetInventory").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "wex_dashboard_fleetInventory_title")), "Send Fleet Inventory title are not loading.");
	//	softAssert.assertTrue(dashboardPage.getTextOfWexDashboardPage("AppsWithMostCrashes").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "wex.customer_dashboard.tile.title.apps_with_most_crashes")), "Send Apps with most crashes title are not loading.");
		softAssert.assertAll();
	}
}