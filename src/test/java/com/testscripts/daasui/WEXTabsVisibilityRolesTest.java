package com.testscripts.daasui;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.basesource.action.PreDefinedActions;
import com.daasui.pages.WEXCustomerUserDetailsPage;
import com.daasui.pages.WEXCustomerUserListPage;
import com.daasui.pages.WEXDashboardPage;

public class WEXTabsVisibilityRolesTest extends CommonTest {
	private static Logger LOGGER = LogManager.getLogger(WEXTabsVisibilityRolesTest.class);
	
	
	@Test(priority = 1, groups = {"REGRESSION_ACCOUNTS",}, description = "TestCase ID : ")
	public final void verifyTabs_Login_ConnectorAdmin() throws Exception {
		login("CONNECTOR_ADMIN_EMAIL", "CONNECTOR_ADMIN_PASSWORD");
	    WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
	    SoftAssert softAssert = new SoftAssert();

	    leftSideMenuVerification();
	    // Wait for dashboard to load
	    waitForPageLoaded();

	    // Verify Integration tab is visible
	    softAssert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("integrations"), "Integration tab should be visible for connector admin");

	    // Verify Settings tab is visible
	    softAssert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("settingsDashboard"), "Settings tab should be visible for connector admin");

	    // Verify other tabs are NOT visible for connector admin
	    softAssert.assertTrue(dashboardPage.verifyElementisinvisibleofDashboardPage("account"), "Account tab should not visible for connector admin");
	    softAssert.assertTrue(dashboardPage.verifyElementisinvisibleofDashboardPage("alertstab"), "Alerts tab should not visible for connector admin");
	    softAssert.assertTrue(dashboardPage.verifyElementisinvisibleofDashboardPage("remediationsdropDown"), "Remediations tab should not visible for connector admin");
	    softAssert.assertTrue(dashboardPage.verifyElementisinvisibleofDashboardPage("companyHardwareSupport"), "Hardware support tab should not visible for connector admin");
	    softAssert.assertTrue(dashboardPage.verifyElementisinvisibleofDashboardPage("pulses"), "Pulses tab should notvisible for connector admin");
	    softAssert.assertTrue(dashboardPage.verifyElementisinvisibleofDashboardPage("analytics"), "Analytics tab should not  visible for connector admin");
	    softAssert.assertTrue(dashboardPage.verifyElementisinvisibleofDashboardPage("devices"), "Devices tab should notvisible for connector admin");
	    softAssert.assertTrue(dashboardPage.verifyElementisinvisibleofDashboardPage("labs"), "Labs tab should not visible for connector admin");
	    softAssert.assertTrue(dashboardPage.verifyElementisinvisibleofDashboardPage("groups"), "Groups tab should not visible for connector admin");
	    
	    LOGGER.info("Tab visibility validation for connector admin completed successfully");
	    softAssert.assertAll();
	}
	 
	@Test(priority = 2, groups = {"REGRESSION_ACCOUNTS"}, description = "TestCaseID:")
	public final void verifyTabs_Login_ReportAdmin() throws Exception {
		 login("REPORT_ADMIN_EMAIL", "REPORT_ADMIN_PASSWORD");
		    WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
		    SoftAssert softAssert = new SoftAssert();

		    leftSideMenuVerification();
		    // Wait for dashboard to load
		    waitForPageLoaded();

		    // Verify all tabs are visible for Report Admin
		    softAssert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("integrations"), "Integration tab should be visible for Report admin");
		    softAssert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("settingsDashboard"), "Settings tab should be visible for Report admin");
		    softAssert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("account"), "Account tab should be visible for Report admin");
		    softAssert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("alertstab"), "Alerts tab should be visible for Report admin");
		    softAssert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("remediationstext"), "Remediations tab should be visible for Report admin");
		    softAssert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("companyHardwareSupport"), "Hardware support tab should be visible for Report admin");
		    softAssert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("pulses"), "Pulses tab should be visible for Report admin");
		    softAssert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("analytics"), "Analytics tab should be visible for Report admin");
		    softAssert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("devicestext"), "Devices tab should be visible for Report admin");
		    softAssert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("labs"), "Labs tab should be visible for Report admin");
		    softAssert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("groups"), "Groups tab should be visible for Report admin");

		    LOGGER.info("Tab visibility validation for report admin completed successfully - all tabs are accessible");
		    softAssert.assertAll();
	}
	
	@Test(priority = 3, groups = {"REGRESSION_ACCOUNTS"}, description = "TestCaseID:")
	public final void verifyTabs_Login_LostDeviceAdmin() throws Exception {
		 login("LOST_ADMIN_EMAIL", "LOST_ADMIN_PASSWORD");
		    WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
		    SoftAssert softAssert = new SoftAssert();

		    leftSideMenuVerification();
		    // Wait for dashboard to load
		    waitForPageLoaded();
		    
		    softAssert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("integrations"), "Integration tab should be visible for Lost admin");
		    softAssert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("devices"), "Devices tab should be visible for Lost admin");
		    softAssert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("devicesPCs"), "Devices  pcs tab should be visible for Lost admin");
		    softAssert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("devicesAssets"), "Devices Assets  tab should be visible for Lost admin");
		    softAssert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("devicesVirtualMachines"), "Devices  virtual machines tab should be visible for Lost admin");
		
		    // Verify other tabs are NOT visible for Lost Device admin
		    softAssert.assertTrue(dashboardPage.verifyElementisinvisibleofDashboardPage("account"), "Account tab should not visible for  Lost admin");
		    softAssert.assertTrue(dashboardPage.verifyElementisinvisibleofDashboardPage("alertstab"), "Alerts tab should not visible for Lost  admin");
		    softAssert.assertTrue(dashboardPage.verifyElementisinvisibleofDashboardPage("remediationsdropDown"), "Remediations tab should not visible for Lost admin");
		    softAssert.assertTrue(dashboardPage.verifyElementisinvisibleofDashboardPage("companyHardwareSupport"), "Hardware support tab should not visible for Lost admin");
		    softAssert.assertTrue(dashboardPage.verifyElementisinvisibleofDashboardPage("pulses"), "Pulses tab should notvisible for Lost admin");
		    softAssert.assertTrue(dashboardPage.verifyElementisinvisibleofDashboardPage("analytics"), "Analytics tab should not  visible for Lost admin");
		    softAssert.assertTrue(dashboardPage.verifyElementisinvisibleofDashboardPage("settingsDashboard"), "setting tab should notvisible for Lost admin");
		    softAssert.assertTrue(dashboardPage.verifyElementisinvisibleofDashboardPage("labs"), "Labs tab should not visible for lost admin");
		    softAssert.assertTrue(dashboardPage.verifyElementisinvisibleofDashboardPage("groups"), "Groups tab should not visible for Lost  admin");
		    
		    LOGGER.info("Tab visibility validation for Lost Device admin completed successfully");
		    softAssert.assertAll();
	}
	
	@Test(priority = 4, groups = {"REGRESSION_ACCOUNTS",}, description = "TestCase ID : ")
	public final void verifyTabs_Login_Developer() throws Exception {
		login("DEVELOPER_EMAIL", "DEVELOPER_PASSWORD");
	    WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
	    SoftAssert softAssert = new SoftAssert();

	    leftSideMenuVerification();
	    // Wait for dashboard to load
	    waitForPageLoaded();
	    // Verify Settings tab is visible
	    softAssert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("settingsDashboard"), "Settings tab should be visible for Developer");

	    // Verify other tabs are NOT visible for Developer
	    softAssert.assertTrue(dashboardPage.verifyElementisinvisibleofDashboardPage("account"), "Account tab should not visible for Developer admin");
	    softAssert.assertTrue(dashboardPage.verifyElementisinvisibleofDashboardPage("alertstab"), "Alerts tab should not visible for Developer admin");
	    softAssert.assertTrue(dashboardPage.verifyElementisinvisibleofDashboardPage("remediationsdropDown"), "Remediations tab should not visible for Developer admin");
	    softAssert.assertTrue(dashboardPage.verifyElementisinvisibleofDashboardPage("companyHardwareSupport"), "Hardware support tab should not visible for Developer admin");
	    softAssert.assertTrue(dashboardPage.verifyElementisinvisibleofDashboardPage("pulses"), "Pulses tab should notvisible for Developer admin");
	    softAssert.assertTrue(dashboardPage.verifyElementisinvisibleofDashboardPage("analytics"), "Analytics tab should not  visible for Developer admin");
	    softAssert.assertTrue(dashboardPage.verifyElementisinvisibleofDashboardPage("devices"), "Devices tab should notvisible for Developer admin");
	    softAssert.assertTrue(dashboardPage.verifyElementisinvisibleofDashboardPage("labs"), "Labs tab should not visible for Developer admin");
	    softAssert.assertTrue(dashboardPage.verifyElementisinvisibleofDashboardPage("groups"), "Groups tab should not visible for Developer admin");
	    softAssert.assertTrue(dashboardPage.verifyElementisinvisibleofDashboardPage("integrations"), "Groups tab should not visible for Developer admin");
	    
	    LOGGER.info("Tab visibility validation for Developer completed successfully");
	    softAssert.assertAll();
	}
	
	@Test(priority = 5, groups = {"REGRESSION_ACCOUNTS"}, description = "TestCaseID:")
	public final void verifyTabs_LoginITAdmin() throws Exception {
		 login("ITADMIN_EMAIL_ACCOUNT", "ITADMIN_PASSWORD_ACCOUNT");
		    WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
		    SoftAssert softAssert = new SoftAssert();

		    leftSideMenuVerification();
		    // Wait for dashboard to load
		    waitForPageLoaded();

		    // Verify all tabs are visible for IT Admin
		    softAssert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("integrations"), "Integration tab should be visible for Report admin");
		    softAssert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("settingsDashboard"), "Settings tab should be visible for Report admin");
		    softAssert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("account"), "Account tab should be visible for Report admin");
		    softAssert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("alertsMenu"), "Alerts tab should be visible for Report admin");
		    softAssert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("remediationstext"), "Remediations tab should be visible for Report admin");
		    softAssert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("companyHardwareSupport"), "Hardware support tab should be visible for Report admin");
		    softAssert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("pulses"), "Pulses tab should be visible for Report admin");
		    softAssert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("analytics"), "Analytics tab should be visible for Report admin");
		    softAssert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("devicestext"), "Devices tab should be visible for Report admin");
		    softAssert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("labs"), "Labs tab should be visible for Report admin");
		    softAssert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("groups"), "Groups tab should be visible for Report admin");

		    LOGGER.info("Tab visibility validation for IT admin completed successfully - all tabs are accessible");
		    softAssert.assertAll();
	}
	
	@Test(priority = 6, groups = {"REGRESSION_ACCOUNTS"}, description = "TestCaseID:")
	public final void verifyTabs_Login_PulseAdmin() throws Exception {
		 login("PULSE_ADMIN_EMAIL", "PULSE_ADMIN_PASSWORD");
		    WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
		    SoftAssert softAssert = new SoftAssert();

		    leftSideMenuVerification();
		    // Wait for dashboard to load
		    waitForPageLoaded();
		   
		    softAssert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("alertstab"), "Alerts tab should be visible for Pulse admin");
		    softAssert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("pulses"), "Pulses tab should be visible for Pulse admin");
		 
		    // Verify other tabs are NOT visible for Lost Device admin
		    softAssert.assertTrue(dashboardPage.verifyElementisinvisibleofDashboardPage("account"), "Account tab should not visible for  Pulse admin");
		    softAssert.assertTrue(dashboardPage.verifyElementisinvisibleofDashboardPage("remediationsdropDown"), "Remediations tab should not visible for Pulse admin");
		    softAssert.assertTrue(dashboardPage.verifyElementisinvisibleofDashboardPage("companyHardwareSupport"), "Hardware support tab should not visible for Pulse admin");
		    softAssert.assertTrue(dashboardPage.verifyElementisinvisibleofDashboardPage("integrations"), "Integration tab should not visible for Pulse admin");
		    softAssert.assertTrue(dashboardPage.verifyElementisinvisibleofDashboardPage("analytics"), "Analytics tab should not visible for Pulse admin");
		    softAssert.assertTrue(dashboardPage.verifyElementisinvisibleofDashboardPage("settingsDashboard"), "setting tab should not visible for Pulse admin");
		    softAssert.assertTrue(dashboardPage.verifyElementisinvisibleofDashboardPage("labs"), "Labs tab should not visible for Pulse admin");
		    softAssert.assertTrue(dashboardPage.verifyElementisinvisibleofDashboardPage("groups"), "Groups tab should not visible for Pulse  admin");
		    softAssert.assertTrue(dashboardPage.verifyElementisinvisibleofDashboardPage("devices"), "Devices tab should not visible for Pulse admin");
		    LOGGER.info("Tab visibility validation for Lost Pulse admin completed successfully");
		    softAssert.assertAll();
	}
}
	
			
