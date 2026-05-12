package com.testscripts.daasui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.basesource.action.PreDefinedActions;
import com.daasui.constants.CommonVariables;
import com.daasui.constants.ScriptVariables;
import com.daasui.pages.AnalyticsPage;
import com.daasui.pages.SettingsPage;
import com.daasui.pages.WEPDeviceListPage;
import com.daasui.pages.WEPGroupsPage;
import com.daasui.pages.WEPScriptsPage;
import com.daasui.pages.WEPWPTPage;
import com.daasui.pages.WEXDashboardPage;
import com.daasui.pages.WEXHelpAndSupportPage;

public class PerfEnvTesting  extends CommonTest {

	private static Logger LOGGER = LogManager.getLogger(WEXDashboardTest.class);
	public static String UserFirstname = getEnvironmentSpecificData(System.getProperty("environment"), "USER_FN");
	public static String UserLastname = getEnvironmentSpecificData(System.getProperty("environment"), "USER_LN");
	
	@DataProvider
	public Object[][] getLoginData() {
		Object[][] data = new Object[2][2];
		data[1][0] = "ITADMIN_EMAIL_SITEUP";
		data[1][1] = "ITADMIN_PASSWORD_SITEUP";
		return data;
	}
	
	@Test(priority = 1, groups = { "PERF_SANITY"})
	public final void verifySiteUpTestcaseWithItAdmin() throws Exception {
		login("ITADMIN_EMAIL_SITEUP", "ITADMIN_PASSWORD_SITEUP");
		leftSideMenuVerification();
		waitForPageLoaded();
		LOGGER.info("SiteUP is working fine");
	}
	
	
	@Test(priority = 1, groups = { "PERF_SANITY"})
	public final void verifySiteUpTestcaseWithPatnerUser() throws Exception {
		login("ROOT_ADMIN_NEW_USER_COMPANIES", "ROOT_ADMIN_NEW_PASSWORD_COMPANIES");
		leftSideMenuVerification();
		waitForPageLoaded();
		LOGGER.info("SiteUP is working fine");
	}

	@Test(priority = 1, groups = {"PERF_SANITY"}, description = "TestCase ID:C42541223")
	public final void verifyAnalyticsListPage() throws Exception {
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver()).getInstance();
		leftSideMenuVerification();
		analyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
		waitForPageLoaded();
		Assert.assertTrue(analyticsPage.getTextOfAnalyticsPage("analyticsPerf").equals(analyticsPage.getTextLanguage(LanguageCode, "daas_ui", "wex.analytics")),
				"Analytics title doesn't match");
		LOGGER.info("Listing page opened correctly");
	}
	
	@Test(priority = 118, groups = { "PRODUCTION_LDK" ,"PERF_SANITY"})
	public final void verifySettingsLdk() throws Exception {
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		SoftAssert softAssert = new SoftAssert();
		WEPWPTPage WEPWPTPage= new WEPWPTPage(PreDefinedActions.getDriver()).getInstance();
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
		leftSideMenuVerification();			
		WEPWPTPage.clickByActionsClickWEP("settingsMenu");
		waitForPageLoaded();
		softAssert.assertTrue(settingsPage.verifyElementsOfSettingsPage("primaryAdmin"), "primary Admin is not displayed");
		softAssert.assertTrue(settingsPage.verifyElementsOfSettingsPage("primaryAccountEmail"), "primary Admin is not displayed");
		softAssert.assertTrue(settingsPage.verifyElementsOfSettingsPage("primaryContact"), "primary Admin is not displayed");
		WEPWPTPage.clickByActionsClickWEP("preferencesTab");
		LOGGER.info("Redirected to preferences Page");
		softAssert.assertTrue(WEPWPTPage.verifyElementsOfWEPWPTPage("preferencesTab"), "Preferences Tab is not displayed");
		softAssert.assertTrue(settingsPage.verifyElementsOfSettingsPage("endUserNotification"), "endUserNotification Tab is not displayed");
		settingsPage.clickOnElementsOfSettingsPage("endUserNotification");
		waitForPageLoaded();
		sleeper(3000);			
		//softAssert.assertTrue(settingsPage.verifyElementsOfSettingsPage("deviceNotificationsSection"), "deviceNotificationsSection Tab is not displayed");			
		WEPWPTPage.clickByActionsClickWEP("logsTab");
		sleeper(3000);
		LOGGER.info("Redirected to logs list page");
		WEPWPTPage.clickByActionsClickWEP("logsTabLink");	
		softAssert.assertTrue(WEPWPTPage.verifyElementsOfWEPWPTPage("logsTab"), "Logs Tab is not displayed");
		softAssert.assertEquals(WEPWPTPage.getTextOfElement("logsHeader"),"Logs");						
		softAssert.assertTrue(settingsPage.verifyElementsOfSettingsPage("locations"), "location Tab is not displayed");
		settingsPage.clickOnElementsOfSettingsPage("locations");
		waitForPageLoaded();
		softAssert.assertTrue(settingsPage.verifyElementsOfSettingsPage("shippingLocations"), "shipping location is not displayed");			
		softAssert.assertAll();			
	}
	@Test(priority = 1, groups = { "REGRESSION_GROUPS","PRODUCTION_GROUPS", "PERF_SANITY"}, description="Testcase ID: C43408570")
	public final void verifyWEPGroupsListPageBreadCrumbsForCustomerView() throws Exception {	
		login("ITADMIN_EMAIL_GROUP", "ITADMIN_PASSWORD_GROUP");
		WEPGroupsPage groupsPage = new WEPGroupsPage(PreDefinedActions.getDriver()).getInstance();
		waitForPageLoaded();
			groupsPage.companyView(CommonVariables.GROUPS);
			LOGGER.info("Navigated to Groups successfully");
			sleeper(2000);
		Assert.assertTrue(groupsPage.getTextOfWEPGroupsPage("groupsMenuBreadcrumb")
				.equals(getTextLanguage(LanguageCode, "daas_ui", "device.groups.sidebar.title")),"Groups Header doesn't exists");
		LOGGER.info("Groups SideMenu is Matched");

		Assert.assertTrue(groupsPage.getTextOfWEPGroupsPage("groupsMenuBreadcrumb")
				.equals(getTextLanguage(LanguageCode, "daas_ui", "device.groups.sidebar.title")),"GroupsMenu Breadcrumb doesn't exists");
		LOGGER.info("Groups  Breadcrumb is Matched");
	}

	 @Test(priority = 3, groups = { "REGRESSION_CONFIGSCRIPTS", "REGRESSION_INTEGRATIONQA_CUJ" , "PERF_SANITY"}, description = "TestCaseID:C43550144")
	    public final void verifyWorkforceExperienceScriptsPageRedirection() throws Exception{
	        login("ITADMIN_EMAIL_SCRIPTS", "ITADMIN_PASSWORD_SCRIPTS");
	        leftSideMenuVerification();
	        WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
	        WEPScriptsPage wexScriptsPage = new WEPScriptsPage(PreDefinedActions.getDriver()).getInstance();
	        if (!toggleVerification(ScriptVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_SCRIPTS"))) {
	            dashboardPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT, CommonVariables.CUSTOMER_FLEET_MANAGEMENT_SCRIPTS);
	        }else{
	            dashboardPage.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_REMEDIATIONS_SCRIPTS);
	        }
	        Assert.assertTrue(wexScriptsPage.verifyElementsOfScriptsPage("scriptsPageTitleScripts"),"Scripts Title is not as expected");
	        LOGGER.info("Workforce Experience Scripts Page Redirection Test Passed");
	    }
	 
	 @Test(priority = 1, groups = { "REGRESSION_ACCOUNTS", "PRODUCTION_LDK" ,"PERF_SANITY"}, description = "TestCaseID:C41392702")
		public final void verifyNeedAssistanceWithWEX() throws Exception {

			login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
			WEXHelpAndSupportPage helpAndSupportPage = new WEXHelpAndSupportPage(PreDefinedActions.getDriver())
					.getInstance();
			SoftAssert softAssert = new SoftAssert();
			helpAndSupportPage.clickOnElementsOfWEXHelpAndSupportPage("HelpAndSupportTab");
			waitForPageLoaded();
			LOGGER.info("Redirected to help and support page");

			// Validating the section may assistance	
			softAssert.assertTrue(
					helpAndSupportPage.matchTextOfWEXHelpAndSupportPage("NeedassistancewithWEX",
							getTextLanguage(LanguageCode, "daas_ui", "settings.custom_support.title")),
					"Need assistance with WEX tile is incorrect");
			softAssert.assertTrue(
					helpAndSupportPage.matchTextOfWEXHelpAndSupportPage("EmailDescription",
							getTextLanguage(LanguageCode, "daas_ui", "partner.help_and_support.description")),
					" Email description is incorrect");

			LOGGER.info("Validation for the Need assistance with WEX is completed");
			softAssert.assertAll();
		}
	 
	 @Test(priority = 5, groups = {"REGRESSION_ACCOUNTS", "PRODUCTION_ACCOUNTS", "PRODUCTION_LDK" ,"PERF_SANITY"}, description = "TestCaseID:C41392702")
	 public final void verifyHelpAndSupportTab() throws Exception {
		
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		WEXHelpAndSupportPage helpAndSupportPage = new WEXHelpAndSupportPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		helpAndSupportPage.clickOnElementsOfWEXHelpAndSupportPage("HelpAndSupportTab");
		waitForPageLoaded();
		LOGGER.info("Redirected to help and support page");
		softAssert.assertTrue(helpAndSupportPage.getTextOfWEXHelpAndSupportPage("getsupporttile").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "partner.help.support.section.get_started.title")),"get support tile are not loading on Help and Support page.");
		softAssert.assertTrue(helpAndSupportPage.getTextOfWEXHelpAndSupportPage("getSupporttileBelowText").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "partner.help.support.section.get_started.section")),"get support tile below text are not loading on Help and Support page.");
		softAssert.assertTrue(helpAndSupportPage.getTextOfWEXHelpAndSupportPage("systemrequirementstile").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "partner.help.support.section.system_requirements.title")),"Syetem requirement tile are not loading on Help and Support page.");
		softAssert.assertTrue(helpAndSupportPage.getTextOfWEXHelpAndSupportPage("systemRequirementstileBelowText").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "partner.help.support.section.system_requirements.section")),"Syetem requirement tile below text are not loading on Help and Support page.");
		softAssert.assertTrue(helpAndSupportPage.getTextOfWEXHelpAndSupportPage("knowledgebasetile").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "partner.help.support.section.academy.title")),"knowlodge base tile are not loading on Help and Support page.");
		softAssert.assertTrue(helpAndSupportPage.getTextOfWEXHelpAndSupportPage("knowledgebasetileBelowText").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "partner.help.support.section.academy.section")),"knowlodge base tile are not loading on Help and Support page.");
		softAssert.assertTrue(helpAndSupportPage.getTextOfWEXHelpAndSupportPage("softwaredownload").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "partner.help.support.section.software_download.title")),"software page tile below text are not loading on Help and Support page.");
		softAssert.assertTrue(helpAndSupportPage.getTextOfWEXHelpAndSupportPage("softwareDownloadbelowText").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "partner.help.support.section.software_download.section")),"software page tile are not loading on Help and Support page.");
		softAssert.assertTrue(helpAndSupportPage.getTextOfWEXHelpAndSupportPage("whatsnewtile").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "partner.help.support.section.whats_new.title")),"what's new tile are not loading on Help and Support page.");
		softAssert.assertTrue(helpAndSupportPage.getTextOfWEXHelpAndSupportPage("wahtsNewTileBelowText").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "partner.help.support.section.whats_new.section")),"what's new tile are not loading on Help and Support page.");
		softAssert.assertTrue(helpAndSupportPage.getTextOfWEXHelpAndSupportPage("mysupporttile").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "partner.help.support.section.my_support_cases.title")),"My support description are not loading on Help and Support page.");
		softAssert.assertTrue(helpAndSupportPage.getTextOfWEXHelpAndSupportPage("MysupportTileBelowText").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "partner.help.support.section.my_support_cases.section")),"My support description text are not loading on Help and Support page.");
		softAssert.assertAll();
	}

	
}
