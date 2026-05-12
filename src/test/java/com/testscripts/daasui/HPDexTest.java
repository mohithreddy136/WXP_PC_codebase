package com.testscripts.daasui;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.basesource.action.PreDefinedActions;
import com.daasui.constants.CompaniesVariables;
import com.daasui.constants.HPDexVariables;
import com.daasui.pages.DashboardPage;
import com.daasui.pages.HPDexPage;

public class HPDexTest extends CommonTest {
	
	private static Logger LOGGER = LogManager.getLogger(HPDexTest.class);
	
	/*
	 * Verify HP-Dex tab not visible for other plans. 
	 * Feature 688641: [Core][HPDX] Add a new subscription for HPDX
	 */
	//Disabling the test case since this functionality not present now
	@Test(priority = 1, groups = { "REGRESSION" }, description = "TestCase[783799]", enabled = false)
	public final void verifyHPDexRedirectToDigitalExperience() throws Exception  {
	  
	  login("HPDEX_IT_ADMIN", "HPDEX_IT_ADMIN_PASSWORD");
	 HPDexPage hpdexPage = new HPDexPage(PreDefinedActions.getDriver()).getInstance();
	 gotoHPDexTab();
	 sleeper(3000);
	 hpdexPage.switchToDifferentTabOfHPDexPage();
	 waitForPageLoaded();
	 sleeper(3000);
	 hpdexPage.enterTextForHPDexPage("digitalExpUsername", CompaniesVariables.HP_DEX_USERNAME);
	 hpdexPage.enterTextForHPDexPage("digitalExpPassword", CompaniesVariables.HP_DEX_PASSWORD);
	 hpdexPage.verifyElementsOfHPDexPage("digitalLoginBtn");
	 hpdexPage. clickOnElementsOfHPDexPage("digitalLoginBtn");
	 waitForPageLoaded();
	 Assert.assertTrue(hpdexPage.verifyElementsOfHPDexPage("hpDexLogo"), "HP Dex logo is not visible");
	 LOGGER.info("HP-Dex tab redirect to digital experience management successfully.");
  }
	
	@DataProvider
	public Object[][] loginDataForIntegrationPopup() {
		Object[][] data = new Object[2][2];
		data[0][0] = "HPDEX_IT_ADMIN_POPUP";  //IT-Admin
		data[0][1] = "HPDEX_IT_ADMIN_POPUP_PASSWORD";
		data[1][0] = "HPDEX_REPORT_ADMIN_POPUP"; //Report Admin
		data[1][1] = "HPDEX_REPORT_ADMIN_POPUP_PASSWORD";
		return data;
	}

	/*
	 * Verify HP-Dex integration pop-up for report admin user.. 
	 * Feature 688641: [Core][HPDX] Add a new subscription for HPDX
	 */
	@Test(priority = 2, groups = { "REGRESSION" }, description = "TestCase[783799]",dataProvider = "loginDataForIntegrationPopup",enabled = false)
	public final void verifyHPIntegrationDexPopUpMessageForReportAndITAdmin(String username, String password) throws Exception  {

		login(username, password);
		SoftAssert softAssert = new SoftAssert();
		HPDexPage hpdexPage = new HPDexPage(PreDefinedActions.getDriver()).getInstance();
		gotoHPDexTab();
		waitForPageLoaded();
		hpdexPage.clickOnElementsOfHPDexPage("viewCampaignListButton");
		sleeper(2000);
		softAssert.assertTrue(hpdexPage.verifyElementsOfHPDexPage("hp-dex-pop-up-title"),"HP Dex title is not visible");
		softAssert.assertTrue(hpdexPage.getTextOfHPDexPage("hp-dex-pop-up-title").equals(getTextLanguage(LanguageCode, "daas_ui", "sidemenu.hpdx")), "HP Dex title is not visible");
		softAssert.assertTrue(hpdexPage.getTextOfHPDexPage("hp-dex-pop-up-desc1").equals(getTextLanguage(LanguageCode, "daas_ui", "hpdx.no_config.title").replace("{app}",getTextLanguage(LanguageCode, "daas_ui", "sidemenu.hpdx"))),"HP Dex description is not visible");

		if (username == "HPDEX_REPORT_ADMIN_POPUP") {
			softAssert.assertTrue(hpdexPage.getTextOfHPDexPage("hp-dex-pop-up-desc2").equals(getTextLanguage(LanguageCode, "daas_ui", "hpdx.no_config.desc_ra")),"HP Dex description2 is not visible");
		} else {
			softAssert.assertTrue(hpdexPage.getTextOfHPDexPage("hp-dex-pop-up-desc2").equals(getTextLanguage(LanguageCode, "daas_ui", "hpdx.no_config.desc")),"HP Dex description2 is not visible");
		}

		softAssert.assertAll();
		LOGGER.info("HP DEX Integration pop-up verified successfully");
	}

	@DataProvider
	public Object[][] loginData() {
		Object[][] data = new Object[2][2];
		data[0][0] = "HPDEX_IT_ADMIN";  //IT-Admin
		data[0][1] = "HPDEX_IT_ADMIN_PASSWORD";
		data[1][0] = "HPDEX_REPORT_ADMIN"; //Report Admin
		data[1][1] = "HPDEX_REPORT_ADMIN_PASSWORD";
		return data;
	}

	/*
	 * This test case verifies chart present on Experience management tab
	 * Feature 688644: [Core][HPDX] Create new HPDX dashboard
	 */
	@Test(priority = 3, groups = { "REGRESSION" }, description = "TestCase[837783]", dataProvider = "loginData",enabled = false)
	public final void verifyChartsPresentOnExperienceManagementTab(String username, String password) throws Exception  {

		 login(username, password);

		 SoftAssert softAssert = new SoftAssert();
		 HPDexPage hpdexPage = new HPDexPage(PreDefinedActions.getDriver()).getInstance();

		 gotoHPDexTab();
		 waitForPageLoaded();

		 LOGGER.info("Redirected to experience management tab");
		 hpdexPage.waitForElementsOfHPDexPage("experianceManagementDashboard");

		 //Verify digital experience overview card
		if(!hpdexPage.verifyElementsOfHPDexPage("digitalExperienceOverviewCardNoData")) {
		 softAssert.assertTrue(hpdexPage.getTextOfHPDexPage("digitalExperienceOverviewCard").equalsIgnoreCase(hpdexPage.getTextLanguage(LanguageCode, "daas_ui", "hpdx.kpi.overview_card")), "Digital experience overview card title text did not match");
		 //softAssert.assertTrue(hpdexPage.getTextOfHPDexPage("overallScoreTitle").equalsIgnoreCase(hpdexPage.getTextLanguage(LanguageCode, "daas_ui", "hpdx.kpi.exp_level_card.overall_score")), "Overall score title text did not match");
		 //softAssert.assertTrue(hpdexPage.getTextOfHPDexPage("enrolledDevicesTitle").equalsIgnoreCase(hpdexPage.getTextLanguage(LanguageCode, "daas_ui", "hpdx.kpi.exp_level_card.enrolled_devices")), "Enrolled devices title text did not match");
		 //softAssert.assertTrue(hpdexPage.verifyElementsOfHPDexPage("dataUpdatedField"), "Data updated field not present on Digital experience field card");
		} else {
			LOGGER.info("Data not present on the digital experience overview card");
		}


		if(!hpdexPage.verifyElementsOfHPDexPage("digitalExpeScoreByCategoryCardNoData"))
			softAssert.assertTrue(hpdexPage.getTextOfHPDexPage("digitalExpeScoreByCategoryCard").equalsIgnoreCase(hpdexPage.getTextLanguage(LanguageCode, "daas_ui", "hpdx.kpi.digital_experience_category")), "Digital experience score by category card title text did not match");
		else
			LOGGER.info("Data not present on digital experience score by category card");

		if(!hpdexPage.verifyElementsOfHPDexPage("digitalExpScoreOverTimeCardNoDta"))
			softAssert.assertTrue(hpdexPage.getTextOfHPDexPage("digitalExpScoreOverTimeCard").equalsIgnoreCase(hpdexPage.getTextLanguage(LanguageCode, "daas_ui", "hpdx.kpi.digital_experience_time")), "Digital experience score over time card title text did not match");
		else
			LOGGER.info("Data not present on digital experience score by category card");

		 //Verify devices by experience card
		if(!hpdexPage.verifyElementsOfHPDexPage("devicesByExperienceCardNoData")) {
		 softAssert.assertTrue(hpdexPage.getTextOfHPDexPage("devicesByExperienceCard").equalsIgnoreCase(hpdexPage.getTextLanguage(LanguageCode, "daas_ui", "hpdx.kpi.experience_level_card")), "Devices by experience level card title text did not match");
		 //softAssert.assertTrue(hpdexPage.getTextOfHPDexPage("goodExperienceTitle").equalsIgnoreCase(hpdexPage.getTextLanguage(LanguageCode, "daas_ui", "hpdx.digital_exp_overview.good_exp")), "Good experience title text did not match");
		 //softAssert.assertTrue(hpdexPage.getTextOfHPDexPage("averageExperienceTitle").equalsIgnoreCase(hpdexPage.getTextLanguage(LanguageCode, "daas_ui", "hpdx.digital_exp_overview.avg_exp")), "Average experience title text did not match");
		//softAssert.assertTrue(hpdexPage.getTextOfHPDexPage("badExperienceTitle").equalsIgnoreCase(hpdexPage.getTextLanguage(LanguageCode, "daas_ui", "hpdx.digital_exp_overview.bad_exp")), "Bad experience title text did not match");
		} else {
			LOGGER.info("Data not present on device by experience card");
		}

		 softAssert.assertAll();
		 LOGGER.info("Test case to verify charts on experience management dashboard passed successfully.");
	}

	/*
	 * This test case verifies Digital experience overview chart present on Dashboard
	 * Feature 688644: [Core][HPDX] Create new HPDX dashboard
	 */
	@Test(priority = 4, groups = { "REGRESSION","STABILIZATION_STAGING","PRODUCTION_CI" }, description = "TestCase[837783]", dataProvider = "loginData",enabled = false)
	public final void verifyDigitalExperienceOverviewCardOnDashboard(String username, String password)throws Exception {

		login(username, password);
		SoftAssert softAssert = new SoftAssert();
		HPDexPage hpdexPage = new HPDexPage(PreDefinedActions.getDriver()).getInstance();
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();

		// Verify Digital experience overview card on dashboard
		if(!hpdexPage.verifyElementsOfHPDexPage("digitalExperienceOverviewCardNoData")) {
		softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("digitalExperienceOverviewCardDashboard").trim().equalsIgnoreCase(dashboardPage.getTextLanguage(LanguageCode, "daas_ui","hpdx.kpi.overview_card")),"Digital experience overview card title text did not match on dashboard");
		softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("overallScoreTitle").equalsIgnoreCase(dashboardPage.getTextLanguage(LanguageCode, "daas_ui","hpdx.kpi.exp_level_card.overall_score")),"Overall score title text did not match on dashboard");

		softAssert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("viewDetailsButtonOnDashboard"),"View details button not present on dashboard");
		dashboardPage.clickOnElementsOfDashboardPage("viewDetailsButtonOnDashboard");
		waitForPageLoaded();
		String currentURL = hpdexPage.getUrlOfCurrentPage();
		softAssert.assertTrue(currentURL.contains(HPDexVariables.EXPERIENCE_MANAGEMENT), "User did not redirected to Experience management tab after clicking on view details button from dashboard");
		} else {
			LOGGER.info("Data not present on the digital experience overview card");
		}

		softAssert.assertAll();
		LOGGER.info("Test case to verify Digital experience overview card on dashboard passed successfully");
	}


	/*
	 * This test case verifies redirection to Nexthink portal after clicking on add campaign button fromExperience management tab
	 */
	@Test(priority = 5, groups = { "REGRESSION" }, description = "TestCase[837783]", dataProvider = "loginData",enabled = false)
	public final void verifyRedirectionToNexthinkPortal(String username, String password) throws Exception  {

		login(username, password);

		SoftAssert softAssert = new SoftAssert();
		HPDexPage hpdexPage = new HPDexPage(PreDefinedActions.getDriver()).getInstance();

		gotoHPDexTab();
		waitForPageLoaded();

		LOGGER.info("Redirected to experience management tab");
		hpdexPage.waitForElementsOfHPDexPage("experianceManagementDashboard");

		// Verify link redirection after clicking on view campaign button
		if (username == "HPDEX_REPORT_ADMIN")
			softAssert.assertFalse(hpdexPage.verifyElementsOfHPDexPage("addCampaignButton"),"Add campaign button present on Experience management dashboard");
		else
			softAssert.assertTrue(hpdexPage.verifyElementsOfHPDexPage("addCampaignButton"),"Add campaign button not present on Experience management dashboard");
		softAssert.assertTrue(hpdexPage.verifyElementsOfHPDexPage("viewCampaignListButton"),"View campaign button not present on Experience management dashboard");
		hpdexPage.clickOnElementsOfHPDexPage("viewCampaignListButton");
		hpdexPage.switchToDifferentTab();
		String currentURL = hpdexPage.getUrlOfCurrentPage();
		softAssert.assertTrue(currentURL.contains(getEnvironmentSpecificData(environment, "VIEW_CAMPAIGN_URL")),"User did not redirected to Nexthink portal after clicking on View campaign button");
		hpdexPage.switchBackToPreviousTab();
		waitForPageLoaded();

		softAssert.assertAll();
		LOGGER.info("Test case to verify redirection to Nexthink portal passed successfully");
	}

	/*
	 * / This test case to verify PIXM 2.0 enhacements and clean up for feature
	 * 973391 Verify subtabs avaialble in Expereience mgnt tab and text changes and
	 * SSO from summary tab for PIXM Customers
	 * 
	 *
	 */

	@Test(priority = 6, groups = { "REGRESSION","STABILIZATION_STAGING","PRODUCTION_CI" }, description = "TestCase[1037244]", dataProvider = "loginData",enabled = false)
	public final void verifyPIXMEnhacementsForCustomers(String username, String password) throws Exception {

		login(username, password);

		SoftAssert softAssert = new SoftAssert();
		HPDexPage hpdexPage = new HPDexPage(PreDefinedActions.getDriver()).getInstance();

		gotoHPDexTab();
		waitForPageLoaded();

		LOGGER.info("Redirected to experience management tab");
		hpdexPage.waitForElementsOfHPDexPage("experianceManagementDashboard");
		scrollToTop();
		// Summary tab verification
		hpdexPage.verifySubtabsinHpdexPage("summaryTab");

		// Text change for View Finder
		softAssert.assertTrue(hpdexPage.getTextOfHPDexPage("viewFinder").equalsIgnoreCase("view Finder"));

		// NextThinkPoral redirection from summary tab
		softAssert.assertTrue(hpdexPage.verifyElementsOfHPDexPage("viewFinder"),
				"View Finder button is present on Experience management dashboard");
		softAssert.assertTrue(hpdexPage.verifyElementsOfHPDexPage("viewCampaignListButton"),
				"View campaign button present on Experience management dashboard");
		hpdexPage.clickOnElementsOfHPDexPage("viewCampaignListButton");
		if(!hpdexPage.verifyElementsOfHPDexPage("pixmNoConfigtab")) {
		hpdexPage.switchToDifferentTab();
		String currentURL = hpdexPage.getUrlOfCurrentPage();
		if(!currentURL.contains(HPDexVariables.SSOSIGNUPURL)) {
		softAssert.assertTrue(currentURL.contains(getEnvironmentSpecificData(environment, "VIEW_CAMPAIGN_URL")),
				"User did redirected to Nexthink portal after clicking on View campaign button");
		hpdexPage.switchBackToPreviousTab();
		waitForPageLoaded();
		}
		else {
			LOGGER.info("SSO Authentication not configured");
			hpdexPage.switchBackToPreviousTab();
			waitForPageLoaded();
		}
		}else {
		hpdexPage.clickOnElementsOfHPDexPage("pixmNoConfigtabCancelbutton");
		LOGGER.info("PIXM is not configured");}
        softAssert.assertAll();
		LOGGER.info("SSO NextThink redirection Validated for PIXM customers");
	}

	/*
	 * / This test case to verify PIXM 2.0 enhacements and clean up for feature
	 * 973391 Verify sub tabs avaialble in Expereience mgnt tab and text changes and
	 * SSO for PIXM MSP users
	 * 
	 *
	 */
	@Test(priority = 7, groups = { "REGRESSION" }, description = "TestCase[1037245]",enabled = false)
	public final void verifyPIXMEnhacementsForForMsp() throws Exception {

		login("MSP_NEW_USER_EMAIL_FLEETCHART_US", "MSP_NEW_USER_PASSWORD_FLEETCHART_US");
		SoftAssert softAssert = new SoftAssert();
		HPDexPage hpdexPage = new HPDexPage(PreDefinedActions.getDriver()).getInstance();
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		dashboardPage.selectCompanyInGlobalFilter("Analytics Infra");
		gotoHPDexTab();
		sleeper(3000);

		//// Summary tab verification
		hpdexPage.verifySubtabsinHpdexPage("summaryTab");

		//// Text change for View Finder and Frustrating Experience
		softAssert.assertTrue(hpdexPage.getTextOfHPDexPage("viewFinder").equalsIgnoreCase("view Finder"));
		softAssert.assertTrue(
				hpdexPage.getTextOfHPDexPage("frustatingExperience").equalsIgnoreCase("Frustrating Experience"));

		//// NextThinkPoral redirection from summary tab
		softAssert.assertTrue(hpdexPage.verifyElementsOfHPDexPage("viewFinder"),
				"Add campaign button present on Experience management dashboard");
		softAssert.assertTrue(hpdexPage.verifyElementsOfHPDexPage("viewCampaignListButton"),
				"View campaign button present on Experience management dashboard");
		hpdexPage.clickOnElementsOfHPDexPage("viewCampaignListButton");
		hpdexPage.switchToDifferentTab();
		String currentURL = hpdexPage.getUrlOfCurrentPage();
		softAssert.assertTrue(currentURL.contains(getEnvironmentSpecificData(environment, "VIEW_CAMPAIGN_URL")),
				"User did redirected to Nexthink portal after clicking on View campaign button");
		hpdexPage.switchBackToPreviousTab();
		waitForPageLoaded();
		softAssert.assertAll();
		LOGGER.info("SSO NextThink redirection happended successfully for PIXM MSP user");
	}
	
}
