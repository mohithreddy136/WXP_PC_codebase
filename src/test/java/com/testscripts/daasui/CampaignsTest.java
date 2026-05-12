package com.testscripts.daasui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.basesource.action.PreDefinedActions;
import com.daasui.constants.CommonVariables;
import com.daasui.constants.ConstantURL;
import com.daasui.constants.UserVariables;
import com.daasui.pages.CampaignDetailsPage;
import com.daasui.pages.CampaignsListPage;
import com.daasui.pages.DashboardPage;
import com.daasui.pages.DeviceDetailsPage;
import com.daasui.pages.DeviceListPage;
import com.daasui.pages.HPDexPage;
import com.daasui.pages.SettingsPage;


public class CampaignsTest extends CommonTest {

	private static Logger LOGGER = LogManager.getLogger(CampaignsTest.class);
	
	
	
	@DataProvider
	public Object[][] getLoginDataCustomCampaign() {
		Object[][] data = new Object[2][2];
		data[0][0] = "MSP_EMAIL_CUSTOM_CAMPAIGN_US";
		data[0][1] = "MSP_PASSWORD_CUSTOM_CAMPAIGN_US";
		data[1][0] = "COMPANY_EMAIL_CUSTOM_CAMPAIGN_US";
		data[1][1] = "COMPANY_PASSWORD_CUSTOM_CAMPAIGN_US";
		return data;
	}

	@DataProvider
	public Object[][] loginDatarolesForgroupsincampaigns(){
		Object[][] data = new Object[2][2];	
		data[0][0] = "CAMPAIGNS_USER_TEST";
		data[0][1] = "CAMPAIGNS_USER_TEST_PASSWORD";
		data[1][0] = "MSP_ADMIN_US_EMAIl";
		data[1][1] = "MSP_ADMIN_US_PASSWORD";
		return data;
	}
	/**
	 * This method will verify the table configuration test cases of Campaigns tab
	 *
	 * @throws Exception
	 */
	@Test(priority = 1, groups = {"REGRESSIONCAMPAIGN1", "REGRESSION_CI"}, description = "Feature ID : 472907")
	public final void verifyTableConfigurationForCampaignListTab() throws Exception {
		login("CAMPAIGNS_USER_TEST", "CAMPAIGNS_USER_TEST_PASSWORD");
		CampaignsListPage campaignsListPage = new CampaignsListPage(PreDefinedActions.getDriver()).getInstance();
		String tenantID = getValueFromToken("tenant");
		gotoCampaignsTab();
		waitForPageLoaded();
		campaignsListPage.clickOnElementsOfCampaignsListPage("campaignListTab");
		waitForPageLoaded();
		// verifying search service API
		Assert.assertTrue(getStatusCode(getSearchServiceApi(environment) + CommonVariables.DETAILSSEARCHSERVICEAPI + tenantID + CommonVariables.DETAILSSEARCHSERVICEAPI2, UserVariables.SEARCHSERVICEBODY, "POST", getEnvironment(environment)) == CommonVariables.CODEOK, "Search service failed for campaigns list page");
		ArrayList<String> columnName = new ArrayList<String>(Arrays.asList(getTextLanguage(LanguageCode, "daas_ui", "campaign.details.name"), getTextLanguage(LanguageCode, "daas_ui", "campaigns.table.header.start_date"),getTextLanguage(LanguageCode, "daas_ui", "campaigns.table.header.end_date"),getTextLanguage(LanguageCode, "daas_ui", "campaigns.table.header.created_date"),getTextLanguage(LanguageCode, "daas_ui", "campaigns.table.header.actual_end_date"),getTextLanguage(LanguageCode, "daas_ui", "campaigns.table.header.created_by"),getTextLanguage(LanguageCode, "daas_ui", "campaigns.table.header.potential_audience"),getTextLanguage(LanguageCode, "daas_ui", "campaigns.table.header.responses"),getTextLanguage(LanguageCode, "daas_ui", "campaigns.table.header.state"),getTextLanguage(LanguageCode, "daas_ui", "campaigns.table.header.company"),getTextLanguage(LanguageCode, "daas_ui", "campaigns.table.header.category"),getTextLanguage(LanguageCode, "daas_ui", "campaigns.table.header.subcategory"),getTextLanguage(LanguageCode, "daas_ui", "assets.location.name")));
		ArrayList<String> checkboxValue = new ArrayList<String>(Arrays.asList("true", "true", "true", "true", "false", "true", "true", "true", "true", "false", "true", "true", "true"));
		ArrayList<String> name = new ArrayList<String>(Arrays.asList(getTextLanguage(LanguageCode, "daas_ui", "campaign.details.name")));
		verifyTableConfigurationTests(columnName, checkboxValue, name);
	}

	/**
	 * This method will verify the details page for scheduled campaign
	 *
	 * @throws Exception
	 */
	@Test(priority = 2, groups = {"REGRESSIONCAMPAIGN1","PRODUCTION_CI","PULSES_PRODUCTIONCI"}, description = "Feature ID : 472907")
	public final void verifyCampaignsDetailsPageForScheduledCampaign() throws Exception {
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		CampaignsListPage campaignsListPage = new CampaignsListPage(PreDefinedActions.getDriver()).getInstance();
		CampaignDetailsPage campaigndetailsPage = new CampaignDetailsPage(PreDefinedActions.getDriver()).getInstance();
		gotoCampaignsTab();
		waitForPageLoaded();
		campaignsListPage.clickOnElementsOfCampaignsListPage("campaignListTab");
		waitForPageLoaded();
		campaignsListPage.waitForElementsOfCampaignsListPage("campaignsTitle");
		campaignsListPage.clearFiltersOfCampaigsListPage("clearfilter");
		campaignsListPage.waitForElementsOfCampaignsListPage("tableOverlay");
		dashboardPage.clickByJavaScriptOnDashboardPage("globalLocationFilterDropDown");
		waitForPageLoaded();
		LOGGER.info("Search company to set location filter");
		dashboardPage.enterTextForDashboardPage("filterMenuCompaniesSearch", getEnvironmentSpecificData(System.getProperty("environment"),"CAMPAIGN_COMPANY"));
		sleeper(5000);
		dashboardPage.waitForElementsOfDashboardPage("companyOnListSearch");
		softAssert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("companyOnListSearch"));
	    dashboardPage.clickOnElementsOfDashboardPage("companyOnListSearch");
		dashboardPage.clickOnElementsOfDashboardPage("globalFilterSave");
		LOGGER.info("Global Location Filter Saved successfully.");
		campaignsListPage.enterTextForCampaignsListPage("stateSearchBox",campaignsListPage.getTextLanguage(LanguageCode, "daas_ui","campaign.graph.status-scheduled"));
		campaignsListPage.waitForElementsOfCampaignsListPage("tableOverlay");
		sleeper(3000);
		if(campaignsListPage.verifyElementsOfCampaignsListPage("firstCampaignOnListPage")){
			String campaignName = campaignsListPage.getTextOfCampaignsListPage("firstCampaignOnListPage");
			campaignsListPage.clickOnElementsOfCampaignsListPage("firstCampaignOnListPage");
			campaignsListPage.waitForElementsOfCampaignsListPage("tableOverlay");
			if (!toggleVerification(CampaignDetailsPage.Campaign_Details_toggle, getCredentials(environment, "MSP_ADMIN_US_EMAIl"))) {
			//Verify Overview tab on Campaign details page.
			softAssert.assertTrue(campaigndetailsPage.verifyElementsOfCampaignDetailsPage("overviewTab"), "Overview tab not present on campaign details page.");
			softAssert.assertEquals(campaignsListPage.getTextOfCampaignsListPage("campaignDetailsTitle"), campaignsListPage.getTextLanguage(LanguageCode, "daas_ui", "campaign.details.breadcumb.name"), "Campaign details header not present");
			softAssert.assertTrue(campaigndetailsPage.getTextOfCampaignDetailsPage("campaignNameOnDetailsPage").equalsIgnoreCase(campaignName), "Campaign name did not match on details page");
			softAssert.assertFalse(campaigndetailsPage.verifyElementIsClickableCampaignDetailsPage("campaignNameTextBox"), "User is able to edit the Campaign name on details page.");
			softAssert.assertFalse(campaigndetailsPage.verifyElementIsClickableCampaignDetailsPage("campaignDescriptionTextBox"), "User is able to edit Campaign description on details page.");
			softAssert.assertFalse(campaigndetailsPage.verifyElementIsClickableCampaignDetailsPage("userSatisfactionRating"), "User is able to edit user satisfaction rating on Campaign details page.");

			softAssert.assertTrue(campaigndetailsPage.verifyElementsOfCampaignDetailsPage("responseResultsTab"), "Response results tab not present on campaign details page.");
			softAssert.assertTrue(campaigndetailsPage.verifyElementsOfCampaignDetailsPage("deviceHealthTab"), "Device healt tab not present on campaign details page.");
			softAssert.assertTrue(campaigndetailsPage.verifyElementsOfCampaignDetailsPage("saveButton"), "Save Campaign button not present on campaign details page");
			softAssert.assertTrue(campaigndetailsPage.verifyElementsOfCampaignDetailsPage("discardButton"), "Discard Campaign button not present on campaign details page");
			softAssert.assertTrue(campaigndetailsPage.verifyElementsOfCampaignDetailsPage("endCampaignButton"), "End Campaign button not present on campaign details page");
		    }else{
				//Verify Overview tab on Campaign details page new UI.
		    	campaigndetailsPage.verifyElementsOfCampaignDetailsPage("overviewTab");
		    	campaigndetailsPage.getTextOfCampaignDetailsPage("campaignNameOnDetailsPagenewUI");
				softAssert.assertFalse(campaigndetailsPage.verifyElementIsClickableCampaignDetailsPage("campaignnameongoing"),"Campaign Name in details page is editable");
				softAssert.assertFalse(campaigndetailsPage.verifyElementIsClickableCampaignDetailsPage("campaigndescriptionongoing"),"Campaign Description in details page is editable");
				campaigndetailsPage.scrollOnCampaignDetailsPage("subCategoryDropDown");
				campaigndetailsPage.verifyElementIsClickableCampaignDetailsPage("categoryDropDown");
				campaigndetailsPage.verifyElementIsClickableCampaignDetailsPage("subCategoryDropDown");
				sleeper(2000);
				campaigndetailsPage.scrollOnCampaignDetailsPage("locationongoing");
				campaigndetailsPage.waitForElementsOfCampaignDetailsPage("locationongoing");
				campaigndetailsPage.getTextOfCampaignDetailsPage("locationongoing");
				campaigndetailsPage.getTextOfCampaignDetailsPage("durationongoing");
				softAssert.assertTrue(campaigndetailsPage.verifyElementIsClickableCampaignDetailsPage("endDatePicker"),"Campaign End date in details page is not editable");
				campaigndetailsPage.scrollOnCampaignDetailsPage("reqquesheaderongoing");
				campaigndetailsPage.getTextOfCampaignDetailsPage("reqquesheaderongoing");
				softAssert.assertFalse(campaigndetailsPage.verifyElementIsClickableCampaignDetailsPage("additionalquesongoing"),"Campaign additional question section in details page is not editable");
				campaigndetailsPage.verifyElementsOfCampaignDetailsPage("responseResultsTab");
				scrollToTop();
				LOGGER.info("Campaign Overview tab verified Sucessfully");
				campaigndetailsPage.waitForElementsOfCampaignDetailsPage("responseResultsTab");
				//Verification of Response results tab on Campaign details page.
				sleeper(5000);
				campaigndetailsPage.clickOnElementsOfCampaignDetailsPage("responseResultsTab");
				campaigndetailsPage.waitForElementsOfCampaignDetailsPage("campaignParticpationtitlenewUI");
				//SoftAssert.assertFalse(verifyElementIsClickableCampaignDetailsPage("exportbuttonnewUI"));
				softAssert.assertTrue(campaigndetailsPage.verifyElementIsVisibleOfCampaignDetailsPage("campaignParticpationtitlenewUI"),"Campaign participation title on campaign details is not present.");
				LOGGER.info("Campaign Response tab verified Sucessfully");
				softAssert.assertTrue(campaigndetailsPage.verifyElementsOfCampaignDetailsPage("deviceHealthTab"), "Device health tab not present on campaign details page.");
				campaigndetailsPage.clickOnElementsOfCampaignDetailsPage("deviceHealthTab");
				sleeper(5000);
				softAssert.assertTrue(campaigndetailsPage.verifyElementsOfCampaignDetailsPage("devicehealthtitlenewUI"),"Device health title not present on campaign details page10.");
				softAssert.assertTrue(campaigndetailsPage.verifyElementsOfCampaignDetailsPage("editbuttonongoing"),"Edit button not present on campaign details page");
				softAssert.assertTrue(campaigndetailsPage.verifyElementsOfCampaignDetailsPage("exportbuttonnewUI"),"Export button not present on campaign details page");
				LOGGER.info("Device Health tab verified Sucessfully");
				softAssert.assertAll();
				LOGGER.info("On going Campaign details page verified Sucessfully");
		    }}
			else {
				LOGGER.info("Campaign list is empty.");
			}
			softAssert.assertAll();
			LOGGER.info("All test cases to verify Campaign Details page passed.");
		}
	
	/**
	 * This method will verify the add campaign functionality for Hardware category
	 *
	 * @throws Exception
	 */
	@Test(priority = 3, groups = {"REGRESSIONCAMPAIGN1", "REGRESSION_CI"}, description = "Feature ID : 472907")
	public final void verifyAddCampaignFunctionalityForHardwareCategory() throws Exception {
		login("CAMPAIGNS_USER_TEST", "CAMPAIGNS_USER_TEST_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		CampaignsListPage campaignsListPage = new CampaignsListPage(PreDefinedActions.getDriver()).getInstance();
		CampaignDetailsPage campaigndetailsPage = new CampaignDetailsPage(PreDefinedActions.getDriver()).getInstance();
		gotoCampaignsTab();
		waitForPageLoaded();
		campaignsListPage.clickOnElementsOfCampaignsListPage("campaignListTab");
		waitForPageLoaded();
		campaignsListPage.clearFiltersOfCampaigsListPage("clearfilter");
		campaigndetailsPage.waitForElementsOfCampaignDetailsPage("tableOverLay");
		sleeper(2000);
		campaignsListPage.clickOnElementsOfCampaignsListPage("addCampaignButton");
		campaigndetailsPage.waitForElementsOfCampaignDetailsPage("tableOverLay");
		softAssert.assertTrue(campaignsListPage.getTextOfCampaignsListPage("newCampaignTitle").equals(campaignsListPage.getTextLanguage(LanguageCode, "daas_ui", "campaign.label.new-campaign")), "Add campaign page did not opened after clicking on add button");
		String category = campaigndetailsPage.getTextLanguage(LanguageCode, "daas_ui","assets.details.inventory.hardware");
		if(campaigndetailsPage.verifyElementsOfCampaignDetailsPage("headerdismiss")==true){
			campaigndetailsPage.clickOnElementsOfCampaignDetailsPage("headerdismiss");
		}
		campaigndetailsPage.clickOnElementsOfCampaignDetailsPage("categoryDropDown");
		campaigndetailsPage.waitForElementsOfCampaignDetailsPage("categoryDropDownOptions");
		softAssert.assertTrue(campaigndetailsPage.createCampaign(category, "categoryList", LanguageCode), "Campaign did not created successfully");
		softAssert.assertAll();
		LOGGER.info("Test case for verifyAddCampaignFunctionalityForHardwareCategory passed successfully.");
	}

	/**
	 * This method will verify the add campaign functionality for Peripherals category
	 *
	 * @throws Exception
	 */
	@Test(priority = 4, groups = {"REGRESSIONCAMPAIGN1", "REGRESSION_CI"}, description = "Feature ID : 472907")
	public final void verifyAddCampaignFunctionalityForPeripheralsCategory() throws Exception {
		login("CAMPAIGNS_USER_TEST", "CAMPAIGNS_USER_TEST_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		CampaignsListPage campaignsListPage = new CampaignsListPage(PreDefinedActions.getDriver()).getInstance();
		CampaignDetailsPage campaigndetailsPage = new CampaignDetailsPage(PreDefinedActions.getDriver()).getInstance();
		gotoCampaignsTab();
		waitForPageLoaded();
		campaignsListPage.clickOnElementsOfCampaignsListPage("campaignListTab");
		waitForPageLoaded();
		campaignsListPage.clearFiltersOfCampaigsListPage("clearfilter");
		campaigndetailsPage.waitForElementsOfCampaignDetailsPage("tableOverLay");
		sleeper(2000);
		campaignsListPage.clickOnElementsOfCampaignsListPage("addCampaignButton");
		campaigndetailsPage.waitForElementsOfCampaignDetailsPage("tableOverLay");
		softAssert.assertTrue(campaignsListPage.getTextOfCampaignsListPage("newCampaignTitle").equals(campaignsListPage.getTextLanguage(LanguageCode, "daas_ui", "campaign.label.new-campaign")), "Add campaign page did not opened after clicking on add button");
		String category = campaigndetailsPage.getTextLanguage(LanguageCode, "daas_ui","product.list.peripherals");
		if(campaigndetailsPage.verifyElementsOfCampaignDetailsPage("headerdismiss")==true){
			campaigndetailsPage.clickOnElementsOfCampaignDetailsPage("headerdismiss");
		}
		campaigndetailsPage.clickOnElementsOfCampaignDetailsPage("categoryDropDown");
		campaigndetailsPage.waitForElementsOfCampaignDetailsPage("categoryDropDownOptions");
		softAssert.assertTrue(campaigndetailsPage.createCampaign(category, "categoryList", LanguageCode), "Campaign did not created successfully");
		softAssert.assertAll();
		LOGGER.info("Test case for verifyAddCampaignFunctionalityForPeripheralsCategory passed successfully.");
	}

	/**
	 * This method will verify the add campaign functionality for Software category.
	 *
	 * @throws Exception
	 */
	@Test(priority = 5, groups = {"REGRESSIONCAMPAIGN1", "REGRESSION_CI"}, description = "Feature ID : 472907")
	public final void verifyAddCampaignFunctionalityForSoftwareCategory() throws Exception {
		login("CAMPAIGNS_USER_TEST", "CAMPAIGNS_USER_TEST_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		CampaignsListPage campaignsListPage = new CampaignsListPage(PreDefinedActions.getDriver()).getInstance();
		CampaignDetailsPage campaigndetailsPage = new CampaignDetailsPage(PreDefinedActions.getDriver()).getInstance();
		gotoCampaignsTab();
		waitForPageLoaded();
		campaignsListPage.clickOnElementsOfCampaignsListPage("campaignListTab");
		waitForPageLoaded();
		campaignsListPage.clearFiltersOfCampaigsListPage("clearfilter");
		campaigndetailsPage.waitForElementsOfCampaignDetailsPage("tableOverLay");
		sleeper(2000);
		campaignsListPage.clickOnElementsOfCampaignsListPage("addCampaignButton");
		campaigndetailsPage.waitForElementsOfCampaignDetailsPage("tableOverLay");
		softAssert.assertTrue(campaignsListPage.getTextOfCampaignsListPage("newCampaignTitle").equals(campaignsListPage.getTextLanguage(LanguageCode, "daas_ui", "campaign.label.new-campaign")), "Add campaign page did not opened after clicking on add button");
		String category = campaigndetailsPage.getTextLanguage(LanguageCode, "daas_ui","assets.details.inventory.software");
		if(campaigndetailsPage.verifyElementsOfCampaignDetailsPage("headerdismiss")==true){
			campaigndetailsPage.clickOnElementsOfCampaignDetailsPage("headerdismiss");
		}
		campaigndetailsPage.clickOnElementsOfCampaignDetailsPage("categoryDropDown");
		campaigndetailsPage.waitForElementsOfCampaignDetailsPage("categoryDropDownOptions");
		softAssert.assertTrue(campaigndetailsPage.createCampaign(category, "categoryList", LanguageCode), "Campaign did not created successfully");
		softAssert.assertAll();
		LOGGER.info("Test case for verifyAddCampaignFunctionalityForSoftwareCategory passed successfully.");
	}

	/**
	 * This method will verify the add campaign functionality for Security category
	 *
	 * @throws Exception
	 */
	@Test(priority = 6, groups = {"REGRESSIONCAMPAIGN2", "REGRESSION_CI"}, description = "Feature ID : 472907")
	public final void verifyAddCampaignFunctionalityForSecurityCategory() throws Exception {
		login("CAMPAIGNS_USER_TEST", "CAMPAIGNS_USER_TEST_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		CampaignsListPage campaignsListPage = new CampaignsListPage(PreDefinedActions.getDriver()).getInstance();
		CampaignDetailsPage campaigndetailsPage = new CampaignDetailsPage(PreDefinedActions.getDriver()).getInstance();
		gotoCampaignsTab();
		waitForPageLoaded();
		campaignsListPage.clickOnElementsOfCampaignsListPage("campaignListTab");
		waitForPageLoaded();
		campaignsListPage.clearFiltersOfCampaigsListPage("clearfilter");
		campaigndetailsPage.waitForElementsOfCampaignDetailsPage("tableOverLay");
		sleeper(2000);
		campaignsListPage.clickOnElementsOfCampaignsListPage("addCampaignButton");
		campaigndetailsPage.waitForElementsOfCampaignDetailsPage("tableOverLay");
		softAssert.assertTrue(campaignsListPage.getTextOfCampaignsListPage("newCampaignTitle").equals(campaignsListPage.getTextLanguage(LanguageCode, "daas_ui", "campaign.label.new-campaign")), "Add campaign page did not opened after clicking on add button");
		String category = campaigndetailsPage.getTextLanguage(LanguageCode, "daas_ui","incidents.com.hp.mpi.icm.type.security");
		if(campaigndetailsPage.verifyElementsOfCampaignDetailsPage("headerdismiss")==true){
			campaigndetailsPage.clickOnElementsOfCampaignDetailsPage("headerdismiss");
		}
		sleeper(2000);
		campaigndetailsPage.clickOnElementsOfCampaignDetailsPage("categoryDropDown");
		campaigndetailsPage.waitForElementsOfCampaignDetailsPage("categoryDropDownOptions");
		softAssert.assertTrue(campaigndetailsPage.createCampaign(category, "categoryList", LanguageCode), "Campaign did not created successfully");
		softAssert.assertAll();
		LOGGER.info("Test case for verifyAddCampaignFunctionalityForSecurityCategory passed successfully.");
	}


	/**
	 * This method will verify the redirection from campaign dashboard tab
	 *
	 * @throws Exception
	 */
	@Test(priority = 7, groups = {"REGRESSIONCAMPAIGN2", "REGRESSION_CI"}, description = "Feature ID : 472907")
	public final void verifyRedirectionFromCampaignDashboardTab() throws Exception {
		login("CAMPAIGNS_USER_TEST", "CAMPAIGNS_USER_TEST_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		CampaignsListPage campaignsListPage = new CampaignsListPage(PreDefinedActions.getDriver()).getInstance();
		gotoCampaignsTab();
		waitForPageLoaded();
		softAssert.assertTrue(campaignsListPage.verifyElementsOfCampaignsListPage("campaignDashboardTab"), "Dashboard tab not present on campaigns page.");
		campaignsListPage.clickOnElementsOfCampaignsListPage("addCampaignButtonOnDashboard");
		waitForPageLoaded();
		softAssert.assertTrue(campaignsListPage.getTextOfCampaignsListPage("newCampaignTitle").equals(campaignsListPage.getTextLanguage(LanguageCode, "daas_ui", "campaign.label.new-campaign")), "Add campaign page did not opened after clicking on add button from dashboard page");
		navigateToBack();
		campaignsListPage.clickOnElementsOfCampaignsListPage("viewListButtonOnDashboard");
		campaignsListPage.waitForElementsOfCampaignsListPage("campaignsTitle");
		softAssert.assertTrue(campaignsListPage.verifyElementsOfCampaignsListPage("listTable"), "Table on campaigns list page is not loaded successfully");
		softAssert.assertTrue(campaignsListPage.verifyElementsOfCampaignsListPage("nameHeader"), "Default column is not present on campaigns list page");
		softAssert.assertAll();
		LOGGER.info("Test case for verifyRedirectionFromCampaignDashboardTab passed successfully.");
	}

	/**
	 * This method will verify export functionality on campaign details page
	 *
	 * @throws Exception
	 */
	@Test(priority = 8, groups = {"REGRESSIONCAMPAIGN2", "REGRESSION_CI"}, description = "Feature ID : 472907")
	public final void verifyExportFunctionalityOnCampaignDetailsPage() throws Exception {
		login("CAMPAIGNS_USER_TEST", "CAMPAIGNS_USER_TEST_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		CampaignsListPage campaignsListPage = new CampaignsListPage(PreDefinedActions.getDriver()).getInstance();
		CampaignDetailsPage campaigndetailsPage = new CampaignDetailsPage(PreDefinedActions.getDriver()).getInstance();
		gotoCampaignsTab();
		waitForPageLoaded();
		if(campaigndetailsPage.verifyElementsOfCampaignDetailsPage("headerdismiss")==true){
			campaigndetailsPage.clickOnElementsOfCampaignDetailsPage("headerdismiss");
		}
		sleeper(2000);
		campaignsListPage.clickOnElementsOfCampaignsListPage("campaignListTab");
		waitForPageLoaded();
		campaignsListPage.waitForElementsOfCampaignsListPage("campaignsTitle");
		campaignsListPage.clearFiltersOfCampaigsListPage("clearfilter");
		campaignsListPage.waitForElementsOfCampaignsListPage("tableOverlay");
		campaignsListPage.enterTextForCampaignsListPage("stateSearchBox",campaignsListPage.getTextLanguage(LanguageCode, "daas_ui","campaign.graph.status-ended"));
		campaignsListPage.waitForElementsOfCampaignsListPage("tableOverlay");
		campaignsListPage.scrollOnCampaignListPage("categoryColumnHeader");
		campaignsListPage.enterTextForCampaignsListPage("categoryColumnSearchBox", campaignsListPage.getTextLanguage(LanguageCode, "daas_ui","campaign.health.metrics.openreports.category"));
		campaignsListPage.scrollOnCampaignListPage("responseColumnHeader");
		campaignsListPage.enterTextForCampaignsListPage("responseColumnSearchBox","1");
		sleeper(2000);
		campaignsListPage.waitForElementsOfCampaignsListPage("tableOverlay");
		if(campaignsListPage.verifyElementsOfCampaignsListPage("firstCampaignOnListPage")){
			campaignsListPage.clickOnElementsOfCampaignsListPage("firstCampaignOnListPage");
			campaigndetailsPage.waitForElementsOfCampaignDetailsPage("tableOverLay");
			softAssert.assertTrue(campaigndetailsPage.verifyElementsOfCampaignDetailsPage("overviewTab"), "Overview tab not present on campaign details page.");

			//Verify export report functionality on Response results tab.
			campaigndetailsPage.clickOnElementsOfCampaignDetailsPage("responseResultsTab");
			campaigndetailsPage.waitForElementsOfCampaignDetailsPage("tableOverLay");
			softAssert.assertTrue(campaigndetailsPage.verifyExportFunctionalityForCampaignReport(LanguageCode,"exportButton"),"Export functionality failed for response results tab.");
			sleeper(2000);
			//Verify export report functionality on Device Health tab.
			campaigndetailsPage.clickOnElementsOfCampaignDetailsPage("deviceHealthTab");
			campaigndetailsPage.waitForElementsOfCampaignDetailsPage("tableOverLay");
			softAssert.assertTrue(campaigndetailsPage.verifyExportFunctionalityForCampaignReport(LanguageCode,"exportButton"),"Export functionality failed for Device Health tab.");

			softAssert.assertAll();
		}
	}

	/**
	 * This method will verify charts present on campaign dashboard
	 *
	 * @throws Exception
	 */
	@Test(priority = 9, groups = {"REGRESSIONCAMPAIGN2", "PRODUCTION_CI","PULSES_PRODUCTIONCI"}, description = "Feature ID : 472907, Test Case : 583873 ")
	public final void verifyDashboardChartsForCampaign() throws Exception {
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		CampaignsListPage campaignsListPage = new CampaignsListPage(PreDefinedActions.getDriver()).getInstance();
		gotoCampaignsTab();
		waitForPageLoaded();
		campaignsListPage.waitForElementsOfCampaignsListPage("campaignDashboardTab");
		softAssert.assertTrue(campaignsListPage.getTextOfCampaignsListPage("campaignDashboardTab").equalsIgnoreCase(campaignsListPage.getTextLanguage(LanguageCode, "daas_ui", "breadcrumbs.dashboard")), "Dashboard title did not match");

		softAssert.assertTrue(campaignsListPage.getTextOfCampaignsListPage("totalCampaignsTitle").equalsIgnoreCase(campaignsListPage.getTextLanguage(LanguageCode, "daas_ui", "campaign.graph.total_no_of.campaigns")), "Total number of campaigns title did not match");
		softAssert.assertTrue(campaignsListPage.getTextOfCampaignsListPage("keyPerformanceTitle").equalsIgnoreCase(campaignsListPage.getTextLanguage(LanguageCode, "daas_ui", "campaign.dashboard.heading.kpi_per_campaign")), "Key performance indicators per campaign title did not match");
		softAssert.assertTrue(campaignsListPage.verifyElementsOfCampaignsListPage("addCampaignButtonOnDashboard"),"Add campaign button not present on dashboard tab.");
		softAssert.assertTrue(campaignsListPage.verifyElementsOfCampaignsListPage("viewListButtonOnDashboard"),"View list button not present on dashboard tab.");

		softAssert.assertTrue(campaignsListPage.getTextOfCampaignsListPage("averageSatisfactionTitle").equalsIgnoreCase(campaignsListPage.getTextLanguage(LanguageCode, "daas_ui", "campaigns.dashboard.bynamebymonth.grid.label.avgsatisscore")), "Average satisfaction title did not match");
		softAssert.assertTrue(campaignsListPage.getTextOfCampaignsListPage("campaignsCommencedByStateTitle").equalsIgnoreCase(campaignsListPage.getTextLanguage(LanguageCode, "daas_ui", "campaign.by.month.chart")), "Campaigns commenced by state title did not match");

		//softAssert.assertTrue(campaignsListPage.getTextOfCampaignsListPage("campaignsByMedalliaChartTitle").equalsIgnoreCase(campaignsListPage.getTextLanguage(LanguageCode, "daas_ui", "campaign.dashboard.heading.kpi_Campaigns_by_Medallia")), "Campaigns By Medallia chart title did not match");
		//softAssert.assertTrue(campaignsListPage.getTextOfCampaignsListPage("noOfCampaignsOnDashboard").equals(campaignsListPage.getTextLanguage(LanguageCode, "daas_ui", "campaign.title")), "Campaigns title not present on Campaigns By Medallia chart");
		//softAssert.assertTrue(campaignsListPage.getTextOfCampaignsListPage("targetedUsersTitle").equals(campaignsListPage.getTextLanguage(LanguageCode, "daas_ui", "campaign.dashboard.kpi.targeted_users")), "Targeted users title not present on Campaigns By Medallia chart");
		softAssert.assertAll();

		LOGGER.info("Test case for verifyDashboardCharts passed successfully.");

	}

	/**
	 * This method will verify the add campaign functionality for User Sentiment category
	 *
	 * @throws Exception
	 */
	@Test(priority = 10, groups = {"REGRESSIONCAMPAIGN2", "REGRESSION_CI"}, description = "Test Case : 583707, 583707, 603206, 603223, 603224")
	public final void verifyUserSentimentCategoryForCampaign() throws Exception {
		login("CAMPAIGNS_USER_TEST", "CAMPAIGNS_USER_TEST_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		CampaignsListPage campaignsListPage = new CampaignsListPage(PreDefinedActions.getDriver()).getInstance();
		CampaignDetailsPage campaigndetailsPage = new CampaignDetailsPage(PreDefinedActions.getDriver()).getInstance();
		gotoCampaignsTab();
		waitForPageLoaded();
		campaignsListPage.clickOnElementsOfCampaignsListPage("campaignListTab");
		waitForPageLoaded();
		campaignsListPage.clearFiltersOfCampaigsListPage("clearfilter");

		//Verify add campaign functionality for User Sentiment category
		campaignsListPage.clickOnElementsOfCampaignsListPage("addCampaignButton");
		campaigndetailsPage.waitForElementsOfCampaignDetailsPage("tableOverLay");
		softAssert.assertTrue(campaignsListPage.getTextOfCampaignsListPage("newCampaignTitle").equals(campaignsListPage.getTextLanguage(LanguageCode, "daas_ui", "campaign.label.new-campaign")), "Add campaign page did not opened after clicking on add button");

		String category = campaigndetailsPage.getTextLanguage(LanguageCode, "Campaign-backend-properties","campaign.label.category.user_sentiment");
		if(campaigndetailsPage.verifyElementsOfCampaignDetailsPage("headerdismiss")==true){
			campaigndetailsPage.clickOnElementsOfCampaignDetailsPage("headerdismiss");
		}
		sleeper(2000);
		campaigndetailsPage.clickOnElementsOfCampaignDetailsPage("categoryDropDown");
		campaigndetailsPage.waitForElementsOfCampaignDetailsPage("categoryDropDownOptions");
		Assert.assertTrue(campaigndetailsPage.createUserSentimentDeviceSatisfactionCampaign(category, "categoryList", LanguageCode), "Campaign did not created successfully cannot proceed further");
		LOGGER.info("Created campaign with User Sentiment category.");
		campaigndetailsPage.clickOnElementsOfCampaignDetailsPage("discardButton");
		//Verify Search functionality with user sentiment category
		waitForPageLoaded();
		campaignsListPage.scrollOnCampaignListPage("categoryColumnSearchBox");
		campaignsListPage.enterTextForCampaignsListPage("categoryColumnSearchBox", category);
		campaignsListPage.waitForElementsOfCampaignsListPage("tableOverlay");
		softAssert.assertFalse(campaignsListPage.verifyElementsOfCampaignsListPage("noElementsDisplayText"), "Search functionality not working with User Sentiment Category");
		campaignsListPage.scrollOnCampaignListPage("responsesColumnData");
		softAssert.assertTrue(campaignsListPage.getTextOfCampaignsListPage("responsesColumnData").equals(campaignsListPage.getTextLanguage(LanguageCode, "daas_ui", "global.list.column.na")), "N/A not present in the responses column for user sentiment campaign category");
		LOGGER.info("Verified search functionality on list page for User Sentiment Category"); 
		campaignsListPage.scrollOnCampaignListPage("nameHeader");
		//Verify Response result and Device health tab not present on details page for campaign with user sentiment category.
		campaignsListPage.clickOnElementsOfCampaignsListPage("firstCampaignOnListPage");
		campaignsListPage.waitForElementsOfCampaignsListPage("tableOverlay");
		softAssert.assertFalse(campaigndetailsPage.verifyElementsOfCampaignDetailsPage("responseResultsTab"), "Response results tab is present on campaign details page for campaign with User Sentiment category.");
		softAssert.assertFalse(campaigndetailsPage.verifyElementsOfCampaignDetailsPage("deviceHealthTab"), "Device health tab is present on campaign details pagefor campaign with User sentiment category.");
		LOGGER.info("Verified campaign details page.");
		//Verify update functionality for campaign
		campaigndetailsPage.waitForElementsOfCampaignDetailsPage("endDatePicker");
		campaigndetailsPage.scrollOnCampaignDetailsPage("endDatePicker");
		campaigndetailsPage.clickOnElementsOfCampaignDetailsPage("endDatePicker");
		campaigndetailsPage.waitForElementsOfCampaignDetailsPage("monthKeyCurrent");
		campaigndetailsPage.selectDateFromCalenderOnCampaignDetailpage(addDaysToCurrentDate(4), "monthKeyCurrent","endDateDialogRightArrow", "daysOnCurrentMonthKey");
		campaigndetailsPage.clickOnElementsOfCampaignDetailsPage("saveButton");
		campaigndetailsPage.waitForElementsOfCampaignDetailsPage("toastNotification");
		softAssert.assertTrue(campaigndetailsPage.getTextOfCampaignDetailsPage("toastNotification").equals(getTextLanguage(LanguageCode, "daas_ui", "campaign.update_campaign.toast")), "Toast notification is not generated after updating campaign.");
		LOGGER.info("Verified updated functionality for campaign.");

		//Verify duplicate functionality for campaign
		campaignsListPage.waitForElementsOfCampaignsListPage("tableOverlay");
		campaignsListPage.mousehoverOnCampaignListPage("firstIdLogo");
		campaignsListPage.clickByJavaScriptOnCampaignListPage("firstIdCheckBox");
		campaignsListPage.clickOnElementsOfCampaignsListPage("closeInformationPanel");
		campaignsListPage.waitForElementsOfCampaignsListPage("duplicateButton");
		campaignsListPage.clickOnElementsOfCampaignsListPage("duplicateButton");
		campaignsListPage.waitForElementsOfCampaignsListPage("tableOverlay");
		softAssert.assertTrue(campaignsListPage.getTextOfCampaignsListPage("duplicateCampaignTitle").equals(campaignsListPage.getTextLanguage(LanguageCode, "daas_ui", "campaign.label.new-campaign")), "Add campaign page did not opened after clicking on duplicate campaign button");
		campaigndetailsPage.scrollOnCampaignDetailsPage("calculateAudience");
		campaigndetailsPage.clickOnElementsOfCampaignDetailsPage("startDateField");
		campaigndetailsPage.waitForElementsOfCampaignDetailsPage("currentDateOnCalender");
		//campaigndetailsPage.scrollOnCampaignDetailsPage("todayButton");
		campaigndetailsPage.clickOnElementsOfCampaignDetailsPage("currentDateOnCalender");
		campaigndetailsPage.waitForElementsOfCampaignDetailsPage("endDatePicker");
		campaigndetailsPage.clickOnElementsOfCampaignDetailsPage("endDatePicker");
		campaigndetailsPage.waitForElementsOfCampaignDetailsPage("monthKeyCurrent");
		campaigndetailsPage.selectDateFromCalenderOnCampaignDetailpage(addDaysToCurrentDate(2), "monthKeyCurrent","endDateDialogRightArrow", "daysOnCurrentMonthKey");
		campaigndetailsPage.clickOnElementsOfCampaignDetailsPage("saveButton");
		campaigndetailsPage.waitForElementsOfCampaignDetailsPage("toastNotification");
		softAssert.assertTrue(campaigndetailsPage.getTextOfCampaignDetailsPage("toastNotification").equals(getTextLanguage(LanguageCode, "daas_ui", "campaign.create_success.toast")), "Toast notification is not generated after duplicating campaign.");
		LOGGER.info("Verified duplicate functionality for campaign.");

		//Verify delete functionality for campaign
		waitForPageLoaded();
		campaignsListPage.enterTextForCampaignsListPage("stateSearchBox",campaignsListPage.getTextLanguage(LanguageCode, "daas_ui","campaign.graph.status-scheduled"));
		campaignsListPage.waitForElementsOfCampaignsListPage("tableOverlay");
		if(campaignsListPage.verifyElementsOfCampaignsListPage("firstCampaignOnListPage")){
			campaignsListPage.clickOnElementsOfCampaignsListPage("firstCampaignOnListPage");
			campaigndetailsPage.waitForElementsOfCampaignDetailsPage("tableOverLay");
			campaigndetailsPage.scrollOnCampaignDetailsPage("endCampaignButton");
			campaigndetailsPage.clickOnElementsOfCampaignDetailsPage("endCampaignButton");
			campaigndetailsPage.waitForElementsOfCampaignDetailsPage("toastNotification");
			waitForPageLoaded();
			campaignsListPage.clearFiltersOfCampaigsListPage("clearfilter");
			campaignsListPage.waitForElementsOfCampaignsListPage("tableOverlay");
			campaignsListPage.enterTextForCampaignsListPage("stateSearchBox",campaignsListPage.getTextLanguage(LanguageCode, "daas_ui","campaign.graph.status-stopped"));
			campaigndetailsPage.waitForElementsOfCampaignDetailsPage("tableOverLay");
			campaignsListPage.mousehoverOnCampaignListPage("firstIdLogo");
			campaignsListPage.clickByJavaScriptOnCampaignListPage("firstIdCheckBox");
			campaignsListPage.clickOnElementsOfCampaignsListPage("closeInformationPanel");
			campaignsListPage.waitForElementsOfCampaignsListPage("deleteCampaignButton");
			campaignsListPage.clickOnElementsOfCampaignsListPage("deleteCampaignButton");
			sleeper(2000);
			campaignsListPage.clickOnElementsOfCampaignsListPage("okdeletebutton");
			campaigndetailsPage.waitForElementsOfCampaignDetailsPage("toastNotification");
			softAssert.assertTrue(campaigndetailsPage.getTextOfCampaignDetailsPage("toastNotification").equals(getTextLanguage(LanguageCode, "daas_ui", "campaign_list.actions.delete_success")), "Toast notification is not generated after deleting campaign.");
			LOGGER.info("Campaign deleted successfully.");
		}

		softAssert.assertAll();
		LOGGER.info("Test case for verifyUserSentimentCategoryForCampaign passed successfully.");
	}

	/**
	 * This method will verify the redirection to device reports page
	 * User Story 523185: [AnalyticsWorkPlc] Device Health Metrics - Details - Serial Number should hyperlink to Hardware Health Report (MVP)
	 *
	 * @throws Exception
	 */
	@Test(priority = 11, groups = {"REGRESSIONCAMPAIGN2", "REGRESSION_CI"}, description = "Test Case : 617086")
	public final void verifyRedirectionFromDeviceHealthTab() throws Exception {
		login("CAMPAIGNS_USER_TEST", "CAMPAIGNS_USER_TEST_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		CampaignsListPage campaignsListPage = new CampaignsListPage(PreDefinedActions.getDriver()).getInstance();
		CampaignDetailsPage campaigndetailsPage = new CampaignDetailsPage(PreDefinedActions.getDriver()).getInstance();
		gotoCampaignsTab();
		waitForPageLoaded();
		campaignsListPage.clickOnElementsOfCampaignsListPage("campaignListTab");
		waitForPageLoaded();
		campaignsListPage.clearFiltersOfCampaigsListPage("clearfilter");
		campaignsListPage.enterTextForCampaignsListPage("stateSearchBox",campaignsListPage.getTextLanguage(LanguageCode, "daas_ui","campaign.graph.status-ended"));
		campaignsListPage.waitForElementsOfCampaignsListPage("tableOverlay");
		campaignsListPage.enterTextForCampaignsListPage("categoryColumnSearchBox", campaignsListPage.getTextLanguage(LanguageCode, "daas_ui","assets.details.inventory.hardware"));
		waitForPageLoaded();

		if(campaignsListPage.verifyElementsOfCampaignsListPage("firstCampaignOnListPage")){
			refreshPage();
			waitForPageLoaded();
			campaignsListPage.clickOnElementsOfCampaignsListPage("firstCampaignOnListPage");
			campaigndetailsPage.waitForElementsOfCampaignDetailsPage("tableOverLay");
			campaigndetailsPage.clickOnElementsOfCampaignDetailsPage("deviceHealthTab");
			campaigndetailsPage.waitForElementsOfCampaignDetailsPage("tableOverLay");
			if(campaigndetailsPage.verifyElementsOfCampaignDetailsPage("deviceDetailsSection")) {
			campaigndetailsPage.scrollOnCampaignDetailsPage("deviceDetailsSection");
			if(campaigndetailsPage.verifyElementsOfCampaignDetailsPage("firstDeviceInList")) {
				campaigndetailsPage.clickOnElementsOfCampaignDetailsPage("firstDeviceInList");
				campaigndetailsPage.switchToDifferentTab();
				waitForPageLoaded();
				softAssert.assertTrue(campaigndetailsPage.getUrlOfCurrentPage().contains("daas_reports"), "User not redirected to reports page.");
				campaigndetailsPage.switchBackToPreviousTab();
				LOGGER.info("Validation of link redirection completed successfully.");

			} else {
				LOGGER.info("No devics present in the list.");
			}
			}else {
				LOGGER.info("Device details section is not present since currently there are no active responded devices present.");
			}
		} else {
			LOGGER.info("No campaign is present with given state");
		}
		softAssert.assertAll();
		LOGGER.info("Test case for verifyRedirectionFromDeviceHealthTab passed successfully.");
	}
	
	/**
	 * Feature 620359: [AnalyticsWorkPlc][Employee Experience V2]Customer Logo in TP (end-user) pop-up:
	 */
	@Test(priority = 12, groups = {"REGRESSIONCAMPAIGN2", "REGRESSION_CI"}, description = "Feature ID : 620359")
	public final void verifyUploadCompanyLogoOption() throws Exception {
		login("CAMPAIGNS_USER_TEST", "CAMPAIGNS_USER_TEST_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		CampaignsListPage campaignsListPage = new CampaignsListPage(PreDefinedActions.getDriver()).getInstance();
		CampaignDetailsPage campaignDetailsPage =  new CampaignDetailsPage(PreDefinedActions.getDriver()).getInstance();
		gotoCampaignsTab();
		waitForPageLoaded();
		softAssert.assertTrue(campaignsListPage.verifyElementsOfCampaignsListPage("campaignDashboardTab"), "Dashboard tab not present on campaigns page.");
		waitForPageLoaded();
		campaignsListPage.clickOnElementsOfCampaignsListPage("addCampaignButtonOnDashboard");
		waitForPageLoaded();
		if(campaignDetailsPage.verifyElementIsVisibleOfCampaignDetailsPage("logopresent")==true) {
			gotoSettingsTabOfReportAdmin();
			campaignDetailsPage.clickOnElementsOfCampaignDetailsPage("selectLogoImageIcon");
			sleeper(4000);
			campaignDetailsPage.clickOnElementsOfCampaignDetailsPage("saveButtonOnPopUp");
			gotoCampaignsTab();
			campaignsListPage.clickOnElementsOfCampaignsListPage("addCampaignButtonOnDashboard");
		}
		if(campaignDetailsPage.verifyElementsOfCampaignDetailsPage("headerdismiss")==true){
			campaignDetailsPage.clickOnElementsOfCampaignDetailsPage("headerdismiss");
		}
		sleeper(2000);
		softAssert.assertTrue(campaignDetailsPage.getTextOfCampaignDetailsPage("campaigncreationnewtext").equals(campaignDetailsPage.getTextLanguage(LanguageCode, "daas_ui", "campaign.label.new-campaign")), "Add campaign page did not opened after clicking on add button from dashboard page");
		softAssert.assertTrue(campaignDetailsPage.getTextOfCampaignDetailsPage("companyLogoTitle").equals(campaignDetailsPage.getTextLanguage(LanguageCode, "daas_ui", "campaign.company_logo.label")), "Company Logo title is not matching");
		softAssert.assertTrue(campaignDetailsPage.getTextOfCampaignDetailsPage("addCompanyLogoOnCompanySetting").equals(campaignDetailsPage.getTextLanguage(LanguageCode, "daas_ui", "campaign.company_logo.action")), "Add company logo on company setting page text is not matching");
		softAssert.assertTrue(campaignDetailsPage.getTextOfCampaignDetailsPage("showCompanyLogoOnEndUserCheckboxText").equals(campaignDetailsPage.getTextLanguage(LanguageCode, "daas_ui", "campaign.company_logo.checkbox.label")), "Show company logo to end user pop up checkbox title is not matching");
		softAssert.assertAll();
		LOGGER.info("Test case for verifyUploadCompanyLogoOption passed successfully.");
	}
	
	/**
	 * Feature 620359: [AnalyticsWorkPlc][Employee Experience V2]Customer Logo in TP (end-user) pop-up:
	 */
	@Test(priority = 13, groups = {"REGRESSIONCAMPAIGN2", "REGRESSION_CI"}, description = "Feature ID : 620359")
	public final void verifyUploadLogoFunctionality() throws Exception {
		login("CAMPAIGNS_USER_TEST", "CAMPAIGNS_USER_TEST_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		CampaignsListPage campaignsListPage = new CampaignsListPage(PreDefinedActions.getDriver()).getInstance();
		CampaignDetailsPage campaignDetailsPage =  new CampaignDetailsPage(PreDefinedActions.getDriver()).getInstance();
		gotoCampaignsTab();
		waitForPageLoaded();
		softAssert.assertTrue(campaignsListPage.verifyElementsOfCampaignsListPage("campaignDashboardTab"), "Dashboard tab not present on campaigns page.");
		waitForPageLoaded();
		campaignsListPage.clickOnElementsOfCampaignsListPage("addCampaignButtonOnDashboard");
		waitForPageLoaded();
		sleeper(3000);
		if(campaignDetailsPage.verifyElementIsVisibleOfCampaignDetailsPage("logopresent")==true) {
			gotoSettingsTabOfReportAdmin();
			campaignDetailsPage.clickOnElementsOfCampaignDetailsPage("selectLogoImageIcon");
			sleeper(4000);
			campaignDetailsPage.clickOnElementsOfCampaignDetailsPage("saveButtonOnPopUp");
			gotoCampaignsTab();
			campaignsListPage.clickOnElementsOfCampaignsListPage("addCampaignButtonOnDashboard");
		}
		if(campaignDetailsPage.verifyElementsOfCampaignDetailsPage("headerdismiss")==true){
			campaignDetailsPage.clickOnElementsOfCampaignDetailsPage("headerdismiss");
		}
		sleeper(2000);
		softAssert.assertTrue(campaignsListPage.getTextOfCampaignsListPage("newCampaignTitle").equals(campaignsListPage.getTextLanguage(LanguageCode, "daas_ui", "campaign.label.new-campaign")), "Add campaign page did not opened after clicking on add button from dashboard page");
		//Click on add company logo link which redirect user on setting page
		softAssert.assertTrue(campaignDetailsPage.getTextOfCampaignDetailsPage("addCompanyLogoOnCompanySetting").equalsIgnoreCase(campaignDetailsPage.getTextLanguage(LanguageCode, "daas_ui", "campaign.company_logo.action")),"Add company logo message did not match");
		campaignDetailsPage.verifyElementIsEnableOfCampaignDetailsPage("showcompanylogosection");
		sleeper(2000);
		campaignDetailsPage.clickOnElementsOfCampaignDetailsPage("addCompanyLogoOnCompanySetting");
		sleeper(5000);
		softAssert.assertTrue(campaignDetailsPage.getTextOfCampaignDetailsPage("youAreAboutToLeaveCampaignPagePopUpText").equals(campaignsListPage.getTextLanguage(LanguageCode, "daas_ui", "campaign.company_logo.modal.text")), "Confirmation message is not showing properly");
		campaignDetailsPage.clickOnElementsOfCampaignDetailsPage("okButtonFromPopUp");
		campaignsListPage.waitForElementsOfCampaignsListPage("tableOverlay");
		//upload a logo from setting page
		softAssert.assertTrue(campaignDetailsPage.verifyUploadCompanyLogo(LanguageCode, "cat.jpg"),"Not able to upload image successfully");
		//Go to campaign page again to check uploaded logo is display or not
		gotoCampaignsTab();
		waitForPageLoaded();
		campaignsListPage.clickOnElementsOfCampaignsListPage("addCampaignButtonOnDashboard");
		waitForPageLoaded();
		softAssert.assertTrue(campaignDetailsPage.verifyElementsOfCampaignDetailsPage("logoImage"),"Uploaded image not getting displayed on new campaign page");
		softAssert.assertTrue(campaignDetailsPage.verifyElementIsEnableOfCampaignDetailsPage("showcompanylogosection"));
		campaignDetailsPage.clickOnElementsOfCampaignDetailsPage("companylogocheckbox");
		//softAssert.assertFalse(campaignDetailsPage.verifyElementIsEnableOfCampaignDetailsPage("companylogocheckbox"));
		campaignDetailsPage.clickOnElementsOfCampaignDetailsPage("companylogocheckbox");
		softAssert.assertAll();
		LOGGER.info("Test case for verifyUploadCompanyLogoOption passed successfully.");
		}
	
	/**
	 * This method will verify the campaign list page
	 *
	 * @throws Exception
	 */
	@Test(priority = 14, groups = { "REGRESSIONCAMPAIGN2", "PRODUCTION_CI", "PULSES_PRODUCTIONCI" }, description = "Test Case ID : 280835")
	public final void verifyCampaigsListPage() throws Exception {
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		CampaignsListPage campaignsListPage = new CampaignsListPage(PreDefinedActions.getDriver()).getInstance();
		gotoCampaignsTab();
		waitForPageLoaded();
		campaignsListPage.waitForElementsOfCampaignsListPage("campaignDashboardTab");
		softAssert.assertTrue(campaignsListPage.getTextOfCampaignsListPage("campaignDashboardTab").equalsIgnoreCase(campaignsListPage.getTextLanguage(LanguageCode, "daas_ui", "breadcrumbs.dashboard")), "Dashboard title did not match");
		campaignsListPage.clickOnElementsOfCampaignsListPage("campaignListTab");
		waitForPageLoaded();
		softAssert.assertTrue(campaignsListPage.verifyElementsOfCampaignsListPage("listTable"), "Table on campaign list page is not loaded successfully");
		softAssert.assertTrue(campaignsListPage.verifyElementsOfCampaignsListPage("nameHeader"), "Default column is not present on cmpaign list page");
		softAssert.assertAll();
		LOGGER.info("Campaign list page is loaded successfully");
	}
	
	/*
	 * Add button is not visible in HP campaigns dashboard/List for HP-Dex
	 * plans.
	 * 
	 */
	@Test(priority = 15, groups = { "REGRESSIONCAMPAIGN2","REGRESSION_CI" }, description = "Testcase ID : 802180", enabled = false)
	public final void verifyHPDexPlansCampaignAddButtonNotVisible() throws Exception {
		login("CAMPAIGNS_USER_TEST", "CAMPAIGNS_USER_TEST_PASSWORD");
		SoftAssert softAssert = new SoftAssert();

		HPDexPage hpdexPage = new HPDexPage(PreDefinedActions.getDriver()).getInstance();
		expandMenuIcon();
		expandSideGroupMenu(getTextLanguage(LanguageCode, "daas_ui", "sidemenu.customers"));
		if (hpdexPage.verifyElementsOfHPDexPage("hpDexTab") == true) {
			CampaignsListPage campaignsListPage = new CampaignsListPage(PreDefinedActions.getDriver()).getInstance();
			if (campaignsListPage.verifyElementsOfCampaignsListPage("campaignsTab") == true) {
				LOGGER.info("campaign tab is visible");
				campaignsListPage.clickOnElementsOfCampaignsListPage("campaignsTab");
				waitForPageLoaded();

				softAssert.assertTrue(campaignsListPage.verifyElementsOfCampaignsListPage("campaignDashboardTab"),
						"Dashboard tab not present on campaigns page.");
				softAssert.assertFalse(
						campaignsListPage.verifyElementsOfCampaignsListPage("addCampaignButtonOnDashboard"),
						"Add campaign button is present on dashboard tab.");
				LOGGER.info("In campaign dashboard tab, add button is not visible as expected for HP-Dex plans.");

				campaignsListPage.clickOnElementsOfCampaignsListPage("campaignListTab");
				softAssert.assertFalse(campaignsListPage.verifyElementsOfCampaignsListPage("addCampaignButton"),
						"Add campaign button is present on campaigns tab.");
				LOGGER.info("In campaign list, add button is not visible as expected for HP-Dex plans.");

			} else {
				softAssert.assertFalse(campaignsListPage.verifyElementsOfCampaignsListPage("campaignsTab"),
						"Campaigns tab is visible in sidemenu");
				LOGGER.info("campaign tab is not visible for HP-Dex plans ");
			}

		} else {
			LOGGER.info("HP-Dex tab is not visible");
		}

		softAssert.assertAll();
		LOGGER.info("In campaign list/dashoard, add button verified sucessfully.");
	}
	
	//User Story 818595: [Daas][Campaign][UI] Removal of no response in Campaign
	@Test(priority = 16, groups = {"REGRESSIONCAMPAIGN2"}, description = "User story ID : 818595")
	public final void verifyNoReponseIsNotPresentInAnyCampaignGraph() throws Exception {
		login("CAMPAIGNS_USER_TEST", "CAMPAIGNS_USER_TEST_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		CampaignsListPage campaignsListPage = new CampaignsListPage(PreDefinedActions.getDriver()).getInstance();
		CampaignDetailsPage campaignDetailsPage =  new CampaignDetailsPage(PreDefinedActions.getDriver()).getInstance();
		gotoCampaignsTab();
		waitForPageLoaded();
		campaignsListPage.waitForElementsOfCampaignsListPage("campaignDashboardTab");
		softAssert.assertTrue(campaignsListPage.getTextOfCampaignsListPage("campaignDashboardTab").equalsIgnoreCase(campaignsListPage.getTextLanguage(LanguageCode, "daas_ui", "breadcrumbs.dashboard")), "Dashboard title did not match");
		campaignsListPage.clickOnElementsOfCampaignsListPage("campaignListTab");
		waitForPageLoaded();
		softAssert.assertTrue(campaignsListPage.verifyElementsOfCampaignsListPage("listTable"), "Table on campaign list page is not loaded successfully");
		campaignsListPage.clearFiltersOfCampaigsListPage("clearfilter");
		campaignsListPage.waitForElementsOfCampaignsListPage("tableOverlay");
//		campaignsListPage.scrollOnCampaignListPage("campaignNameTextBoxOnListPage");
//		campaignsListPage.enterTextForCampaignsListPage("campaignNameTextBoxOnListPage", CommonVariables.No_REPONSE_TEST_CAMPAIGN);
		campaignsListPage.scrollOnCampaignListPage("statecolumnheader");
		campaignsListPage.enterTextForCampaignsListPage("stateSearchBox",campaignsListPage.getTextLanguage(LanguageCode, "daas_ui","campaign.graph.status-ended"));
		campaignsListPage.scrollOnCampaignListPage("responseColumnHeader");
		campaignsListPage.enterTextForCampaignsListPage("responseColumnSearchBox","0");
		campaignsListPage.waitForElementsOfCampaignsListPage("tableOverlay");
		sleeper(4000);
		if(campaignsListPage.verifyElementsOfCampaignsListPage("firstCampaignAfterSearch")) {
		campaignsListPage.clickOnElementsOfCampaignsListPage("firstCampaignAfterSearch");
		waitForPageLoaded();
		campaignDetailsPage.clickOnElementsOfCampaignDetailsPage("responseResultsTab");
		waitForPageLoaded();
		//Validating No Response text is not present on satisfaction graph
		softAssert.assertTrue(campaignDetailsPage.validateNoResponseNotPresentOnAnyGraph("satisfactionQuestionGraph",LanguageCode),"No Response text is present on Response Result");
		//Validating No Response text is not present on Single select question graph
		softAssert.assertTrue(campaignDetailsPage.validateNoResponseNotPresentOnAnyGraph("singleSelectQuestionGraph",LanguageCode),"No Response text is present on Response Result");
		//Validating No Response text is not present on Multi Select question graph
		softAssert.assertTrue(campaignDetailsPage.validateNoResponseNotPresentOnAnyGraph("multiSelectQuestionGraph",LanguageCode),"No Response text is present on Response Result");
		//Validating No Response text is not present on star rating graph
		softAssert.assertTrue(campaignDetailsPage.validateNoResponseNotPresentOnAnyGraph("starRatingQuestionGraph",LanguageCode),"No Response text is present on Response Result");
		LOGGER.info("No Response text is not present on any of graph of Response Detail view");
		//Validating No Response text is not present on Satisfaction Results With Device Health graph
		campaignDetailsPage.clickOnElementsOfCampaignDetailsPage("deviceHealthTab");
		waitForPageLoaded();
		softAssert.assertTrue(campaignDetailsPage.validateNoResponseNotPresentOnAnyGraph("satisfactionRatingWithDeviceHealth",LanguageCode),"No Response text is present on Device Health");
		softAssert.assertAll();
		}else {
			LOGGER.info("No campaigns liste & suits the provided filter");
		}
		LOGGER.info("No Response text is not present on Satisfaction Results With Device Health graph");
	}
	
	// Feature 814190: [AnalyticsWorkPlc][Employee Experience V2]Change Responses bar chart to pie/donut chart
	@Test(priority = 17, groups = { "REGRESSIONCAMPAIGN2" }, description = "User story ID : 818595")
	public final void verifyResponsesPieChart() throws Exception {
		login("CAMPAIGNS_USER_TEST", "CAMPAIGNS_USER_TEST_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		CampaignsListPage campaignsListPage = new CampaignsListPage(PreDefinedActions.getDriver()).getInstance();
		CampaignDetailsPage campaignDetailsPage = new CampaignDetailsPage(PreDefinedActions.getDriver()).getInstance();
		gotoCampaignsTab();
		waitForPageLoaded();
		campaignsListPage.waitForElementsOfCampaignsListPage("campaignDashboardTab");
		softAssert.assertTrue(campaignsListPage.getTextOfCampaignsListPage("campaignDashboardTab").equalsIgnoreCase(campaignsListPage.getTextLanguage(LanguageCode, "daas_ui", "breadcrumbs.dashboard")),"Dashboard title did not match");
		campaignsListPage.clickOnElementsOfCampaignsListPage("campaignListTab");
		waitForPageLoaded();
		softAssert.assertTrue(campaignsListPage.verifyElementsOfCampaignsListPage("listTable"),"Table on campaign list page is not loaded successfully");
		campaignsListPage.clearFiltersOfCampaigsListPage("clearfilter");
		campaignsListPage.waitForElementsOfCampaignsListPage("tableOverlay");
		//campaignsListPage.scrollOnCampaignListPage("categoryColumnSearchBox");
		//campaignsListPage.enterTextForCampaignsListPage("campaignNameTextBoxOnListPage",CommonVariables.No_REPONSE_TEST_CAMPAIGN);
		campaignsListPage.scrollOnCampaignListPage("statecolumnheader");
		campaignsListPage.enterTextForCampaignsListPage("stateSearchBox",campaignsListPage.getTextLanguage(LanguageCode, "daas_ui","campaign.graph.status-ended"));
		campaignsListPage.scrollOnCampaignListPage("responseColumnHeader");
		campaignsListPage.enterTextForCampaignsListPage("responseColumnSearchBox","1");
		sleeper(3000);
		campaignsListPage.waitForElementsOfCampaignsListPage("tableOverlay");
		if(campaignsListPage.verifyElementsOfCampaignsListPage("firstCampaignAfterSearch")) {
		campaignsListPage.clickOnElementsOfCampaignsListPage("firstCampaignAfterSearch");
		waitForPageLoaded();
		campaignDetailsPage.clickOnElementsOfCampaignDetailsPage("responseResultsTab");
		waitForPageLoaded();
		softAssert.assertTrue((campaignDetailsPage.getTextOfCampaignDetailsPage("reponsesPieChart")).equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "campaign.response.result.did_not_receive_survey")),"Reponses Pie chart is not present on response result tab");
		softAssert.assertAll();
		}else {
			LOGGER.info("No campaigns listed & suits the provided filter");
		}
		LOGGER.info("Validated Responses pie chart has been displayed successfully on responses detail tab");
	}
	
	/**
	 * This test case validates the context sensitive help links on Campaigns screen
	 *
	 * @throws Exception
	 */
	@Test(priority = 18, groups = { "REGRESSIONSETTINGS2", "SMOKE", "REGRESSION_CI",
	"SMOKE_CI" }, description = "Test Case ID : 891080")
	public final void verifyOnlineHelpLinksOnCampaigns() throws Exception {
		login("CAMPAIGNS_USER_TEST", "CAMPAIGNS_USER_TEST_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();

		// Verify help and support link for Campaigns tab
		waitForPageLoaded();
		gotoCampaignsTab();
		LOGGER.info("Redirected to Campaigns tab");
		settingsPage.waitForElementsOfSettingsPage("helpAndSupportIcon");
		settingsPage.clickOnElementsOfSettingsPage("helpAndSupportIcon");
		softAssert.assertTrue(
				settingsPage.matchTextOfSettingsPage("help&SupportLink",
						getTextLanguage(LanguageCode, "daas_ui", "settings.help_and_support.title")),
				"Help & Support link text did not match on Campaigns tab");
		settingsPage.clickOnElementsOfSettingsPage("help&SupportLink");
		LOGGER.info("Clicked on Help & Support link from Campaigns tab");
		waitForPageLoaded();
		softAssert.assertTrue(settingsPage.getTextOfSettingsPage("helpAndSupportTitleTextOnBreadcrumbs").equals(settingsPage.getTextLanguage(LanguageCode, "daas_ui", "sidemenu.support")),
				"User not redirected to Help & Support tab after clicking on Help & Support link from Campaigns tab");
		gotoCampaignsTab();

		// Verify overview link for Campaigns tab
		waitForPageLoaded();
		LOGGER.info("Redirected to Campaigns tab");
		settingsPage.waitForElementsOfSettingsPage("helpAndSupportIcon");
		settingsPage.clickOnElementsOfSettingsPage("helpAndSupportIcon");
		softAssert.assertTrue(
				settingsPage.matchTextOfSettingsPage("campaignOverviewLink",
						getTextLanguage(LanguageCode, "daas_ui", "sidemenu.campaign.overview")),
				"Incidents overview link text did not match on Campaigns tab");
		settingsPage.clickOnElementsOfSettingsPage("campaignOverviewLink");
		LOGGER.info("Clicked on overview link from Campaigns tab");
		settingsPage.switchToDifferentTab();
		sleeper(4000);// Url takes time to load
		softAssert.assertTrue(settingsPage.getUrlOfCurrentPage().contains(ConstantURL.KNOWLEDGE_BASE),
				"User not redirected to Online help section after clicking on overview link from Campaigns tab");
		softAssert.assertTrue(settingsPage.getUrlOfCurrentPage().contains(ConstantURL.LANGUAGE_PARAMETER),
				"Language parameter not added in Service now URL");
		settingsPage.switchBackToPreviousTab();

		softAssert.assertAll();
		LOGGER.info("Test case to verify context sensitive links on Campaigns passed successfully.");
	}
	
	/**
	 * This method will verify the add custom campaign functionality for User Sentiment category.
	 *
	 * @throws Exception
	 */
	@Test(priority = 19, groups = { "REGRESSIONSETTINGS2", "SMOKE", "REGRESSION_CI","SMOKE_CI" }, description = "Test Case ID : 934170")
	public final void verifyCustomCampaignsSubCategory() throws Exception {
		login("CAMPAIGNS_USER_TEST", "CAMPAIGNS_USER_TEST_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		CampaignsListPage campaignsListPage = new CampaignsListPage(PreDefinedActions.getDriver()).getInstance();
		CampaignDetailsPage campaigndetailsPage = new CampaignDetailsPage(PreDefinedActions.getDriver()).getInstance();
		gotoCampaignsTab();
		waitForPageLoaded();
		campaignsListPage.clickOnElementsOfCampaignsListPage("campaignListTab");
		waitForPageLoaded();
		campaignsListPage.clearFiltersOfCampaigsListPage("clearfilter");
		
		//Verify add custom campaign functionality for User Sentiment category
				campaignsListPage.clickOnElementsOfCampaignsListPage("addCampaignButton");
				campaigndetailsPage.waitForElementsOfCampaignDetailsPage("tableOverLay");
				softAssert.assertTrue(campaignsListPage.getTextOfCampaignsListPage("newCampaignTitle").equals(campaignsListPage.getTextLanguage(LanguageCode, "daas_ui", "campaign.label.new-campaign")), "Add campaign page did not opened after clicking on add button");
				String category = campaigndetailsPage.getTextLanguage(LanguageCode, "Campaign-backend-properties","campaign.label.category.user_sentiment");
				if(campaigndetailsPage.verifyElementsOfCampaignDetailsPage("headerdismiss")==true){
					campaigndetailsPage.clickOnElementsOfCampaignDetailsPage("headerdismiss");
				}
				sleeper(2000);
				campaigndetailsPage.clickOnElementsOfCampaignDetailsPage("categoryDropDown");
				campaigndetailsPage.waitForElementsOfCampaignDetailsPage("categoryDropDownOptions");
				Assert.assertTrue(campaigndetailsPage.createCustomCampaign(category, "categoryList",CampaignDetailsPage.Medallia_Service_Domain, LanguageCode), "Campaign did not created successfully cannot proceed further");
				LOGGER.info("Created campaign with User Sentiment category.");
				campaigndetailsPage.clickOnElementsOfCampaignDetailsPage("newcampaigntab");
				
				//Verify Search functionality with user sentiment category
				waitForPageLoaded();
				campaignsListPage.scrollOnCampaignListPage("categoryColumnSearchBox");
				campaignsListPage.enterTextForCampaignsListPage("categoryColumnSearchBox", category);
				campaignsListPage.waitForElementsOfCampaignsListPage("tableOverlay");
				softAssert.assertFalse(campaignsListPage.verifyElementsOfCampaignsListPage("noElementsDisplayText"), "Search functionality not working with User Sentiment Category");
				campaignsListPage.scrollOnCampaignListPage("responsesColumnData");
				softAssert.assertTrue(campaignsListPage.getTextOfCampaignsListPage("responsesColumnData").equals(campaignsListPage.getTextLanguage(LanguageCode, "daas_ui", "global.list.column.na")), "N/A not present in the responses column for user sentiment campaign category");
				LOGGER.info("Verified search functionality on list page for User Sentiment Category"); 
				campaignsListPage.scrollOnCampaignListPage("nameHeader");
				
				//Verify Response result and Device health tab not present on details page for campaign with user sentiment category.
				campaignsListPage.clickOnElementsOfCampaignsListPage("firstCampaignOnListPage");
				campaignsListPage.waitForElementsOfCampaignsListPage("tableOverlay");
				softAssert.assertFalse(campaigndetailsPage.verifyElementsOfCampaignDetailsPage("responseResultsTab"), "Response results tab is present on campaign details page for campaign with User Sentiment category.");
				softAssert.assertFalse(campaigndetailsPage.verifyElementsOfCampaignDetailsPage("deviceHealthTab"), "Device health tab is present on campaign details pagefor campaign with User sentiment category.");
				LOGGER.info("Verified campaign details page.");
				
				//Verify update functionality for campaign
				campaigndetailsPage.waitForElementsOfCampaignDetailsPage("endDatePicker");
				campaigndetailsPage.scrollOnCampaignDetailsPage("endDatePicker");
				campaigndetailsPage.clickOnElementsOfCampaignDetailsPage("endDatePicker");
				campaigndetailsPage.waitForElementsOfCampaignDetailsPage("monthKeyCurrent");
				campaigndetailsPage.selectDateFromCalenderOnCampaignDetailpage(addDaysToCurrentDate(4), "monthKeyCurrent","endDateDialogRightArrow", "daysOnCurrentMonthKey");
				campaigndetailsPage.clickOnElementsOfCampaignDetailsPage("saveButton");
				sleeper(2000);
				campaigndetailsPage.waitForElementsOfCampaignDetailsPage("toastNotification");
				softAssert.assertTrue(campaigndetailsPage.getTextOfCampaignDetailsPage("toastNotification").equals(getTextLanguage(LanguageCode, "daas_ui", "campaign.update_campaign.toast")), "Toast notification is not generated after updating campaign.");
				LOGGER.info("Verified updated functionality for campaign.");
				
				//Verify duplicate functionality for campaign
				campaignsListPage.waitForElementsOfCampaignsListPage("tableOverlay");
				campaignsListPage.mousehoverOnCampaignListPage("firstIdLogo");
				campaignsListPage.clickByJavaScriptOnCampaignListPage("firstIdCheckBox");
				campaignsListPage.clickOnElementsOfCampaignsListPage("closeInformationPanel");
				campaignsListPage.waitForElementsOfCampaignsListPage("duplicateButton");
				campaignsListPage.clickOnElementsOfCampaignsListPage("duplicateButton");
				campaignsListPage.waitForElementsOfCampaignsListPage("tableOverlay");
				softAssert.assertTrue(campaignsListPage.getTextOfCampaignsListPage("duplicateCampaignTitle").equals(campaignsListPage.getTextLanguage(LanguageCode, "daas_ui", "campaign.label.new-campaign")), "Add campaign page did not opened after clicking on duplicate campaign button");
				campaigndetailsPage.scrollOnCampaignDetailsPage("categoryDropDown");
				sleeper(2000);
				campaigndetailsPage.waitForElementsOfCampaignDetailsPage("duppreviewButton");
				campaigndetailsPage.clickByJavaScriptOnCampaignDetailsPage("duppreviewButton");
				sleeper(4000);
				campaigndetailsPage.switchToDifferentTabOfCampaignsPage();
				LOGGER.info("Switched to new window successfully for duplicate campaign");
				sleeper(2000);
				campaigndetailsPage.switchToPreviousTabOfCampaignDetailsPage();
				LOGGER.info("Switched back to parent window successfully for duplicate campaign");
				campaigndetailsPage.scrollOnCampaignDetailsPage("Duration");
				campaigndetailsPage.clickOnElementsOfCampaignDetailsPage("startDateField");
				campaigndetailsPage.waitForElementsOfCampaignDetailsPage("currentDateOnCalender");
				
				//CampaigndetailsPage.scrollOnCampaignDetailsPage("todayButton");
				campaigndetailsPage.clickOnElementsOfCampaignDetailsPage("currentDateOnCalender");
				campaigndetailsPage.waitForElementsOfCampaignDetailsPage("endDatePicker");
				campaigndetailsPage.clickOnElementsOfCampaignDetailsPage("endDatePicker");
				campaigndetailsPage.waitForElementsOfCampaignDetailsPage("monthKeyCurrent");
				campaigndetailsPage.selectDateFromCalenderOnCampaignDetailpage(addDaysToCurrentDate(2), "monthKeyCurrent","endDateDialogRightArrow", "daysOnCurrentMonthKey");
				campaigndetailsPage.clickOnElementsOfCampaignDetailsPage("saveButton");
				campaigndetailsPage.waitForElementsOfCampaignDetailsPage("toastNotification");
				softAssert.assertTrue(campaigndetailsPage.getTextOfCampaignDetailsPage("toastNotification").equals(getTextLanguage(LanguageCode, "daas_ui", "campaign.create_success.toast")), "Toast notification is not generated after duplicating campaign.");
				LOGGER.info("Verified duplicate functionality for campaign.");

				//Verify delete functionality for campaign
				waitForPageLoaded();
				campaignsListPage.enterTextForCampaignsListPage("stateSearchBox",campaignsListPage.getTextLanguage(LanguageCode, "daas_ui","campaign.graph.status-scheduled"));
				campaignsListPage.waitForElementsOfCampaignsListPage("tableOverlay");
				if(campaignsListPage.verifyElementsOfCampaignsListPage("firstCampaignOnListPage")){
					campaignsListPage.clickOnElementsOfCampaignsListPage("firstCampaignOnListPage");
					campaigndetailsPage.waitForElementsOfCampaignDetailsPage("tableOverLay");
					campaigndetailsPage.scrollOnCampaignDetailsPage("endCampaignButton");
					campaigndetailsPage.clickOnElementsOfCampaignDetailsPage("endCampaignButton");
					campaigndetailsPage.waitForElementsOfCampaignDetailsPage("toastNotification");
					waitForPageLoaded();
					campaignsListPage.clearFiltersOfCampaigsListPage("clearfilter");
					campaignsListPage.waitForElementsOfCampaignsListPage("tableOverlay");
					campaignsListPage.enterTextForCampaignsListPage("stateSearchBox",campaignsListPage.getTextLanguage(LanguageCode, "daas_ui","campaign.graph.status-stopped"));
					campaigndetailsPage.waitForElementsOfCampaignDetailsPage("tableOverLay");
					campaignsListPage.mousehoverOnCampaignListPage("firstIdLogo");
					campaignsListPage.clickByJavaScriptOnCampaignListPage("firstIdCheckBox");
					campaignsListPage.clickOnElementsOfCampaignsListPage("closeInformationPanel");
					campaignsListPage.waitForElementsOfCampaignsListPage("deleteCampaignButton");
					campaignsListPage.clickOnElementsOfCampaignsListPage("deleteCampaignButton");
					sleeper(2000);
					campaignsListPage.clickOnElementsOfCampaignsListPage("okdeletebutton");
					campaigndetailsPage.waitForElementsOfCampaignDetailsPage("toastNotification");
					softAssert.assertTrue(campaigndetailsPage.getTextOfCampaignDetailsPage("toastNotification").equals(getTextLanguage(LanguageCode, "daas_ui", "campaign_list.actions.delete_success")), "Toast notification is not generated after deleting campaign.");
					LOGGER.info("Campaign deleted successfully.");
				}

				softAssert.assertAll();
				LOGGER.info("Test case for verifyUserSentimentCategoryForCustomCampaign passed successfully.");
			}
	
	@Test(priority = 20, groups = { "REGRESSIONSETTINGS2", "SMOKE", "REGRESSION_CI","SMOKE_CI" }, description = "UserStory ID : 1038916 ")
	public final void verifySpecialCharatersinCampaigns() throws Exception {
		login("CAMPAIGNS_USER_TEST", "CAMPAIGNS_USER_TEST_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		CampaignsListPage campaignsListPage = new CampaignsListPage(PreDefinedActions.getDriver()).getInstance();
		CampaignDetailsPage campaigndetailsPage = new CampaignDetailsPage(PreDefinedActions.getDriver()).getInstance();
		gotoCampaignsTab();
		waitForPageLoaded();
		campaignsListPage.clickOnElementsOfCampaignsListPage("campaignListTab");
		waitForPageLoaded();
		campaignsListPage.clearFiltersOfCampaigsListPage("clearfilter");
		campaigndetailsPage.waitForElementsOfCampaignDetailsPage("tableOverLay");
		sleeper(2000);
		campaignsListPage.clickOnElementsOfCampaignsListPage("addCampaignButton");
		campaigndetailsPage.waitForElementsOfCampaignDetailsPage("tableOverLay");
		softAssert.assertTrue(campaignsListPage.getTextOfCampaignsListPage("newCampaignTitle").equals(campaignsListPage.getTextLanguage(LanguageCode, "daas_ui", "campaign.label.new-campaign")), "Add campaign page did not opened after clicking on add button");
		String category = campaigndetailsPage.getTextLanguage(LanguageCode, "daas_ui","assets.details.inventory.hardware");
		if(campaigndetailsPage.verifyElementsOfCampaignDetailsPage("headerdismiss")==true){
			campaigndetailsPage.clickOnElementsOfCampaignDetailsPage("headerdismiss");
		}
		sleeper(2000);
		campaigndetailsPage.clickOnElementsOfCampaignDetailsPage("categoryDropDown");
		campaigndetailsPage.waitForElementsOfCampaignDetailsPage("categoryDropDownOptions");
		softAssert.assertTrue(campaigndetailsPage.verifySpecialCharactersinCampaignCreation(category, "categoryList", LanguageCode), "Campaign did not created successfully");
		softAssert.assertAll();
	}
	
	/**
	 * Prerequisite:Use Login credentials under toggle "campaign-page-design-update"  This method will verify the campaign details page for Normal Hardware Category type 
	 * .
	 * @throws Exception
	 */
	@Test(priority = 21, groups = {"REGRESSIONCAMPAIGN1","SMOKE", "REGRESSION_CI","SMOKE_CI"}, description = "UserStory ID : 935577", enabled = true)
	public final void verifyCampaignsDetailsPageForEndedNormalCampaignnewUI() throws Exception {
		login("CAMPAIGNS_USER_TEST", "CAMPAIGNS_USER_TEST_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		CampaignsListPage campaignsListPage = new CampaignsListPage(PreDefinedActions.getDriver()).getInstance();
		CampaignDetailsPage campaigndetailsPage = new CampaignDetailsPage(PreDefinedActions.getDriver()).getInstance();
		
		gotoCampaignsTab();
		waitForPageLoaded();
		campaignsListPage.clickOnElementsOfCampaignsListPage("campaignListTab");
		waitForPageLoaded();
		campaignsListPage.waitForElementsOfCampaignsListPage("campaignsTitle");
		campaignsListPage.clearFiltersOfCampaigsListPage("clearfilter");
		campaignsListPage.waitForElementsOfCampaignsListPage("tableOverlay");
		if(campaigndetailsPage.verifyElementsOfCampaignDetailsPage("headerdismiss")==true){
			campaigndetailsPage.clickOnElementsOfCampaignDetailsPage("headerdismiss");
		}
		sleeper(2000);
		//Verification of Campaign Details page for normal campaigns in ENDED State  
		campaignsListPage.enterTextForCampaignsListPage("stateSearchBox",campaignsListPage.getTextLanguage(LanguageCode, "daas_ui","campaign.graph.status-ended"));
		campaignsListPage.enterTextForCampaignsListPage("categoryColumnSearchBox",campaignsListPage.getTextLanguage(LanguageCode, "daas_ui","assets.details.hardware.title"));
		campaignsListPage.waitForElementsOfCampaignsListPage("tableOverlay");
		sleeper(3000);
		if(campaignsListPage.verifyElementsOfCampaignsListPage("firstCampaignOnListPage")){
			String campaignName = campaignsListPage.getTextOfCampaignsListPage("firstCampaignOnListPage");
			campaignsListPage.clickOnElementsOfCampaignsListPage("firstCampaignOnListPage");
			campaignsListPage.waitForElementsOfCampaignsListPage("tableOverlay");

			//Verification of Overview tab on Campaign details page.
			softAssert.assertTrue(campaigndetailsPage.verifyElementsOfCampaignDetailsPage("overviewTab"), "Overview tab not present on campaign details page.");
			softAssert.assertEquals(campaignsListPage.getTextOfCampaignsListPage("campaignDetailsTitle"), campaignsListPage.getTextLanguage(LanguageCode, "daas_ui", "campaign.details.breadcumb.name"), "Campaign details header not present");
			softAssert.assertTrue(campaigndetailsPage.getTextOfCampaignDetailsPage("campaignNameOnDetailsPagenewUI").equalsIgnoreCase(campaignName), "Campaign name did not match on details page");
			softAssert.assertEquals(campaigndetailsPage.getTextOfCampaignDetailsPage("detailsheader"), campaigndetailsPage.getTextLanguage(LanguageCode, "daas_ui", "campaign.health.metrics.table.title"), "Campaign details header is not equal");
			softAssert.assertTrue(campaigndetailsPage.verifyElementIsClickableCampaignDetailsPage("campaignNameTextBoxdetail"), "User is able to edit the Campaign name on details page.");
			if(campaigndetailsPage.verifyElementsOfCampaignDetailsPage("logopresent")) {
			softAssert.assertTrue(campaigndetailsPage.verifyElementIsClickableCampaignDetailsPage("categoryTextBox"), "User is able to edit the Campaign category on details page.");
			softAssert.assertTrue(campaigndetailsPage.verifyElementIsClickableCampaignDetailsPage("subcategoryTextBox"), "User is able to edit the Campaign subcategory on details page.");
			}else {
				softAssert.assertTrue(campaigndetailsPage.verifyElementIsClickableCampaignDetailsPage("categoryTextBoxnoLogo"), "User is able to edit the Campaign category on details page.");
				softAssert.assertTrue(campaigndetailsPage.verifyElementIsClickableCampaignDetailsPage("subcategoryTextBoxnoLogo"), "User is able to edit the Campaign subcategory on details page.");
			}
			softAssert.assertEquals(campaigndetailsPage.getTextOfCampaignDetailsPage("criteriaheader"), campaigndetailsPage.getTextLanguage(LanguageCode, "daas_ui", "flexible_dashboard.widget_container.add_widget.filter_criteria"), "Filter Criteria header is not equal");
			if(campaigndetailsPage.verifyElementIsVisibleOfCampaignDetailsPage("criteriainnerheader")) {
				campaigndetailsPage.scrollOnCampaignDetailsPage("criteriafieldheader");
				softAssert.assertTrue(campaigndetailsPage.verifyElementsOfCampaignDetailsPage("criteriafieldheader"), "Criteria field header is not present on campaign details page.");
				softAssert.assertTrue(campaigndetailsPage.verifyElementsOfCampaignDetailsPage("criteriaValue"), "Criteria Value header is not present on campaign details page.");
				softAssert.assertTrue(campaigndetailsPage.verifyElementsOfCampaignDetailsPage("criteriaOperator"), "Criteria Operator header is not present on campaign details page.");
			}
			sleeper(2000);
			campaigndetailsPage.waitForElementsOfCampaignDetailsPage("targetaudienceheader");
			softAssert.assertEquals(campaigndetailsPage.getTextOfCampaignDetailsPage("targetaudienceheader"), campaigndetailsPage.getTextLanguage(LanguageCode, "daas_ui", "campaign.add.label.target_audience"), "Target Audience header is not equal");
			softAssert.assertEquals(campaigndetailsPage.getTextOfCampaignDetailsPage("targetaudiencemessage"), campaigndetailsPage.getTextLanguage(LanguageCode, "daas_ui", "campaign.add.potential_audience.tooltip"), "Target Audience Tooltip message is not equal");
			softAssert.assertEquals(campaigndetailsPage.getTextOfCampaignDetailsPage("durationheadernewUI"), campaigndetailsPage.getTextLanguage(LanguageCode, "daas_ui", "new.benchmark.label.duration"), "Duration header is not equal");
			campaigndetailsPage.scrollOnCampaignDetailsPage("questionheadernewUI");
			softAssert.assertEquals(campaigndetailsPage.getTextOfCampaignDetailsPage("questionheadernewUI"), campaigndetailsPage.getTextLanguage(LanguageCode, "daas_ui", "new.campaign.label.questions"), "Question header is not equal");
			softAssert.assertTrue(campaigndetailsPage.verifyElementIsClickableCampaignDetailsPage("userSatisfactionRatingnewUI"), "User is able to edit user satisfaction rating on Campaign details page.");
			softAssert.assertTrue(campaigndetailsPage.verifyElementsOfCampaignDetailsPage("responseResultsTab"), "Response results tab not present on campaign details page.");
			campaigndetailsPage.scrollToTop();
			//Verification of Response results tab on Campaign details page.
			campaigndetailsPage.clickOnElementsOfCampaignDetailsPage("responseResultsTab");
			softAssert.assertTrue(campaigndetailsPage.verifyElementsOfCampaignDetailsPage("editbuttonongoing"));
			softAssert.assertTrue(campaigndetailsPage.verifyElementsOfCampaignDetailsPage("exportbuttonnewUI"));
			softAssert.assertEquals(campaigndetailsPage.getTextOfCampaignDetailsPage("campaignParticpationtitlenewUI"), campaigndetailsPage.getTextLanguage(LanguageCode, "daas_ui", "campaigns.table.header.campaign_participation"), "Campaign participation header is not equal");
			softAssert.assertTrue(campaigndetailsPage.verifyElementsOfCampaignDetailsPage("campaignquestionnewUI"));
			//Verification of Response results tab on Campaign details page.
			softAssert.assertTrue(campaigndetailsPage.verifyElementsOfCampaignDetailsPage("deviceHealthTab"), "Device healt tab not present on campaign details page.");
			campaigndetailsPage.clickOnElementsOfCampaignDetailsPage("deviceHealthTab");
			softAssert.assertEquals(campaigndetailsPage.getTextOfCampaignDetailsPage("devicehealthtitlenewUI"), campaigndetailsPage.getTextLanguage(LanguageCode, "daas_ui", "campaign.health.metrics.bar-chart.device_health.title"), "Device health header is not equal");
			softAssert.assertTrue(campaigndetailsPage.verifyElementsOfCampaignDetailsPage("deletebuttonnewUI"));
			softAssert.assertTrue(campaigndetailsPage.verifyElementsOfCampaignDetailsPage("exportbuttonnewUI"));
		}
		else{
			LOGGER.info("Campaign list is empty.");
		}
		softAssert.assertAll();
		LOGGER.info("All test cases to verify Campaign Details page passed.");
	}

	/**
	 * Prerequisite:Use Login credentials under toggle "campaign-page-design-update" This method will verify the campaign details page for UserSentiment Category type
	 * @throws Exception
	 */
	@Test(priority = 22, groups = {"REGRESSIONCAMPAIGN1", "SMOKE", "REGRESSION_CI","SMOKE_CI"}, description = "UserStory ID : 935577", enabled = true)
	public final void verifyCampaignsDetailsPageForEndedUserSentimentCampaignnewUI() throws Exception {
		login("CAMPAIGNS_USER_TEST", "CAMPAIGNS_USER_TEST_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		CampaignsListPage campaignsListPage = new CampaignsListPage(PreDefinedActions.getDriver()).getInstance();
		CampaignDetailsPage campaigndetailsPage = new CampaignDetailsPage(PreDefinedActions.getDriver()).getInstance();
		gotoCampaignsTab();
		waitForPageLoaded();
		campaignsListPage.clickOnElementsOfCampaignsListPage("campaignListTab");
		waitForPageLoaded();
		campaignsListPage.waitForElementsOfCampaignsListPage("campaignsTitle");
		campaignsListPage.clearFiltersOfCampaigsListPage("clearfilter");
		campaignsListPage.waitForElementsOfCampaignsListPage("tableOverlay");
		if(campaigndetailsPage.verifyElementsOfCampaignDetailsPage("headerdismiss")==true){
			campaigndetailsPage.clickOnElementsOfCampaignDetailsPage("headerdismiss");
		}
		//Verification of Campaign Details page for normal campaigns in ENDED State  
		campaignsListPage.enterTextForCampaignsListPage("stateSearchBox",campaignsListPage.getTextLanguage(LanguageCode, "daas_ui","campaign.graph.status-ended"));
		campaignsListPage.enterTextForCampaignsListPage("categoryColumnSearchBox",campaignsListPage.getTextLanguage(LanguageCode, "daas_ui","logs.user.name"));
		campaignsListPage.waitForElementsOfCampaignsListPage("tableOverlay");
		sleeper(4000);
		if(campaignsListPage.verifyElementsOfCampaignsListPage("firstCampaignOnListPage")){
			String campaignName = campaignsListPage.getTextOfCampaignsListPage("firstCampaignOnListPage");
			campaignsListPage.clickOnElementsOfCampaignsListPage("firstCampaignOnListPage");
			campaignsListPage.waitForElementsOfCampaignsListPage("tableOverlay");

			//Verification of Overview tab on Campaign details page.
			softAssert.assertTrue(campaigndetailsPage.verifyElementsOfCampaignDetailsPage("overviewTab"), "Overview tab not present on campaign details page.");
			softAssert.assertEquals(campaignsListPage.getTextOfCampaignsListPage("campaignDetailsTitle"), campaignsListPage.getTextLanguage(LanguageCode, "daas_ui", "campaign.details.breadcumb.name"), "Campaign details header not present");
			softAssert.assertTrue(campaigndetailsPage.getTextOfCampaignDetailsPage("campaignNameOnDetailsPagenewUI").equalsIgnoreCase(campaignName), "Campaign name did not match on details page");
			softAssert.assertEquals(campaigndetailsPage.getTextOfCampaignDetailsPage("detailsheader"), campaigndetailsPage.getTextLanguage(LanguageCode, "daas_ui", "campaign.health.metrics.table.title"), "Campaign details header is not equal");
			softAssert.assertTrue(campaigndetailsPage.verifyElementIsClickableCampaignDetailsPage("campaignNameTextBoxdetail"), "User is able to edit the Campaign name on details page.");
			if(campaigndetailsPage.verifyElementsOfCampaignDetailsPage("logopresent")) {
				softAssert.assertTrue(campaigndetailsPage.verifyElementIsClickableCampaignDetailsPage("categoryTextBox"), "User is able to edit the Campaign category on details page.");
				softAssert.assertTrue(campaigndetailsPage.verifyElementIsClickableCampaignDetailsPage("subcategoryTextBox"), "User is able to edit the Campaign subcategory on details page.");
				}else {
					softAssert.assertTrue(campaigndetailsPage.verifyElementIsClickableCampaignDetailsPage("categoryTextBoxnoLogo"), "User is able to edit the Campaign category on details page.");
					softAssert.assertTrue(campaigndetailsPage.verifyElementIsClickableCampaignDetailsPage("subcategoryTextBoxnoLogo"), "User is able to edit the Campaign subcategory on details page.");
				}
			softAssert.assertEquals(campaigndetailsPage.getTextOfCampaignDetailsPage("criteriaheader"), campaigndetailsPage.getTextLanguage(LanguageCode, "daas_ui", "flexible_dashboard.widget_container.add_widget.filter_criteria"), "Filter Criteria header is not equal");
			if(campaigndetailsPage.verifyElementIsVisibleOfCampaignDetailsPage("criteriainnerheader")) {
				campaigndetailsPage.scrollOnCampaignDetailsPage("criteriafieldheader");
				softAssert.assertTrue(campaigndetailsPage.verifyElementsOfCampaignDetailsPage("criteriafieldheader"), "Criteria field header is not present on campaign details page.");
				softAssert.assertTrue(campaigndetailsPage.verifyElementsOfCampaignDetailsPage("criteriaValue"), "Criteria Value header is not present on campaign details page.");
				softAssert.assertTrue(campaigndetailsPage.verifyElementsOfCampaignDetailsPage("criteriaOperator"), "Criteria Operator header is not present on campaign details page.");
			}
			sleeper(2000);
			campaigndetailsPage.waitForElementsOfCampaignDetailsPage("targetaudienceheader");
			softAssert.assertEquals(campaigndetailsPage.getTextOfCampaignDetailsPage("targetaudienceheader"), campaigndetailsPage.getTextLanguage(LanguageCode, "daas_ui", "campaign.add.label.target_audience"), "Target Audience header is not equal");
			softAssert.assertEquals(campaigndetailsPage.getTextOfCampaignDetailsPage("targetaudiencemessage"), campaigndetailsPage.getTextLanguage(LanguageCode, "daas_ui", "campaign.add.potential_audience.tooltip"), "Target Audience Tooltip message is not equal");
			softAssert.assertEquals(campaigndetailsPage.getTextOfCampaignDetailsPage("durationheadernewUI"), campaigndetailsPage.getTextLanguage(LanguageCode, "daas_ui", "new.benchmark.label.duration"), "Duration header is not equal");
			campaigndetailsPage.scrollToTop();
			softAssert.assertFalse(campaigndetailsPage.verifyElementsOfCampaignDetailsPage("responseResultsTab"), "Response results tab not present on campaign details page.");
			softAssert.assertFalse(campaigndetailsPage.verifyElementsOfCampaignDetailsPage("deviceHealthTab"), "Device healt tab not present on campaign details page.");
		}
		else{
			LOGGER.info("Campaign list is empty.");
		}
		softAssert.assertAll();
		LOGGER.info("All test cases to verify Campaign Details page passed.");
	}	
	
	/**
	 * This method will verify the the create campaign functionality for danskLanguage & deutschLanguage
	 *
	 * @throws Exception
	 */
	@Test(priority = 23, groups = {"REGRESSIONCAMPAIGN1","SMOKE","REGRESSION_CI","SMOKE_CI"}, enabled = false)
	public final void verifyAddCampaignFunctionalityFordanskanddeutschlanguage() throws Exception {
		login("CAMPAIGNS_USER_TEST", "CAMPAIGNS_USER_TEST_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		CampaignsListPage campaignsListPage = new CampaignsListPage(PreDefinedActions.getDriver()).getInstance();
		CampaignDetailsPage campaigndetailsPage = new CampaignDetailsPage(PreDefinedActions.getDriver()).getInstance();
		sleeper(5000);
		preferredlanguage("danskLanguage");
		gotoCampaignsTab();
		waitForPageLoaded();
		campaignsListPage.clickOnElementsOfCampaignsListPage("campaignListTab");
		waitForPageLoaded();
		campaignsListPage.clearFiltersOfCampaigsListPage("clearfilter");
		campaigndetailsPage.waitForElementsOfCampaignDetailsPage("tableOverLay");
		sleeper(2000);
		campaignsListPage.clickOnElementsOfCampaignsListPage("addCampaignButton");
		campaigndetailsPage.waitForElementsOfCampaignDetailsPage("tableOverLay");
		campaigndetailsPage.waitForElementsOfCampaignDetailsPage("newCampaignTitle");
		softAssert.assertTrue(campaignsListPage.getTextOfCampaignsListPage("newCampaignTitle").equals(campaignsListPage.getTextLanguage(LanguageCode, "daas_ui", "campaign.label.new-campaign")), "Add campaign page did not opened after clicking on add button");
		String category = campaigndetailsPage.getTextLanguage(LanguageCode, "daas_ui","assets.details.inventory.hardware");
		if(campaigndetailsPage.verifyElementsOfCampaignDetailsPage("headerdismiss")==true){
			campaigndetailsPage.clickOnElementsOfCampaignDetailsPage("headerdismiss");
		}
		sleeper(2000);
		campaigndetailsPage.clickOnElementsOfCampaignDetailsPage("categoryDropDown");
		campaigndetailsPage.waitForElementsOfCampaignDetailsPage("categoryDropDownOptions");
		softAssert.assertTrue(campaigndetailsPage.createCampaign(category, "categoryList", LanguageCode), "Campaign did not created successfully");
		preferredlanguage("deutschLanguage");
		gotoCampaignsTab();
		waitForPageLoaded();
		campaignsListPage.clickOnElementsOfCampaignsListPage("campaignListTab");
		waitForPageLoaded();
		campaignsListPage.clearFiltersOfCampaigsListPage("clearfilter");
		campaigndetailsPage.waitForElementsOfCampaignDetailsPage("tableOverLay");
		sleeper(2000);
		campaignsListPage.clickOnElementsOfCampaignsListPage("addCampaignButton");
		campaigndetailsPage.waitForElementsOfCampaignDetailsPage("tableOverLay");
		campaigndetailsPage.waitForElementsOfCampaignDetailsPage("newCampaignTitle");
		softAssert.assertTrue(campaignsListPage.getTextOfCampaignsListPage("newCampaignTitle").equals(campaignsListPage.getTextLanguage(LanguageCode, "daas_ui", "campaign.label.new-campaign")), "Add campaign page did not opened after clicking on add button");
		String category2 = campaigndetailsPage.getTextLanguage(LanguageCode, "daas_ui","incidents.com.hp.mpi.icm.type.security");
		if(campaigndetailsPage.verifyElementsOfCampaignDetailsPage("headerdismiss")==true){
			campaigndetailsPage.clickOnElementsOfCampaignDetailsPage("headerdismiss");
		}
		campaigndetailsPage.clickOnElementsOfCampaignDetailsPage("categoryDropDown");
		campaigndetailsPage.waitForElementsOfCampaignDetailsPage("categoryDropDownOptions");
		softAssert.assertTrue(campaigndetailsPage.createCampaign(category2, "categoryList", LanguageCode), "Campaign did not created successfully");
		preferredlanguage("englishLanguage");
		softAssert.assertAll();
		LOGGER.info("Verify Add campaign functionality for danskLanguage & deutschLanguage passed successfully.");
	}
	
	/**
	 * This method will verify the the create campaign functionality for espanolLanguage & suomalainenLanguage
	 *
	 * @throws Exception
	 */
	@Test(priority = 24, groups = {"REGRESSIONCAMPAIGN1","SMOKE","REGRESSION_CI","SMOKE_CI"}, enabled = false)
	public final void verifyAddCampaignFunctionalityForespanolandsuomalainenlanguage() throws Exception {
		login("CAMPAIGNS_USER_TEST", "CAMPAIGNS_USER_TEST_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		CampaignsListPage campaignsListPage = new CampaignsListPage(PreDefinedActions.getDriver()).getInstance();
		CampaignDetailsPage campaigndetailsPage = new CampaignDetailsPage(PreDefinedActions.getDriver()).getInstance();
		sleeper(2000);
		preferredlanguage("espanolLanguage");
		gotoCampaignsTab();
		waitForPageLoaded();
		campaignsListPage.clickOnElementsOfCampaignsListPage("campaignListTab");
		waitForPageLoaded();
		campaignsListPage.clearFiltersOfCampaigsListPage("clearfilter");
		campaigndetailsPage.waitForElementsOfCampaignDetailsPage("tableOverLay");
		sleeper(2000);
		campaignsListPage.clickOnElementsOfCampaignsListPage("addCampaignButton");
		campaigndetailsPage.waitForElementsOfCampaignDetailsPage("tableOverLay");
		campaigndetailsPage.waitForElementsOfCampaignDetailsPage("newCampaignTitle");
		softAssert.assertTrue(campaignsListPage.getTextOfCampaignsListPage("newCampaignTitle").equals(campaignsListPage.getTextLanguage(LanguageCode, "daas_ui", "campaign.label.new-campaign")), "Add campaign page did not opened after clicking on add button");
		String category = campaigndetailsPage.getTextLanguage(LanguageCode, "daas_ui","assets.details.inventory.hardware");
		if(campaigndetailsPage.verifyElementsOfCampaignDetailsPage("headerdismiss")==true){
			campaigndetailsPage.clickOnElementsOfCampaignDetailsPage("headerdismiss");
		}
		sleeper(2000);
		campaigndetailsPage.clickOnElementsOfCampaignDetailsPage("categoryDropDown");
		campaigndetailsPage.waitForElementsOfCampaignDetailsPage("categoryDropDownOptions");
		softAssert.assertTrue(campaigndetailsPage.createCampaign(category, "categoryList", LanguageCode), "Campaign did not created successfully");
		preferredlanguage("suomalainenLanguage");
		gotoCampaignsTab();
		waitForPageLoaded();
		campaignsListPage.clickOnElementsOfCampaignsListPage("campaignListTab");
		waitForPageLoaded();
		campaignsListPage.clearFiltersOfCampaigsListPage("clearfilter");
		campaigndetailsPage.waitForElementsOfCampaignDetailsPage("tableOverLay");
		sleeper(2000);
		campaignsListPage.clickOnElementsOfCampaignsListPage("addCampaignButton");
		campaigndetailsPage.waitForElementsOfCampaignDetailsPage("tableOverLay");
		campaigndetailsPage.waitForElementsOfCampaignDetailsPage("newCampaignTitle");
		softAssert.assertTrue(campaignsListPage.getTextOfCampaignsListPage("newCampaignTitle").equals(campaignsListPage.getTextLanguage(LanguageCode, "daas_ui", "campaign.label.new-campaign")), "Add campaign page did not opened after clicking on add button");
		String category2 = campaigndetailsPage.getTextLanguage(LanguageCode, "daas_ui","assets.details.inventory.software");
		if(campaigndetailsPage.verifyElementsOfCampaignDetailsPage("headerdismiss")==true){
			campaigndetailsPage.clickOnElementsOfCampaignDetailsPage("headerdismiss");
		}
		campaigndetailsPage.clickOnElementsOfCampaignDetailsPage("categoryDropDown");
		campaigndetailsPage.waitForElementsOfCampaignDetailsPage("categoryDropDownOptions");
		softAssert.assertTrue(campaigndetailsPage.createCampaign(category2, "categoryList", LanguageCode), "Campaign did not created successfully");
		preferredlanguage("englishLanguage");
		softAssert.assertAll();
		LOGGER.info("Verify Add campaign functionality for espanolLanguage & suomalainenLanguage passed successfully.");
	}
	/**
	 * This method will verify the the create campaign functionality for francaisLanguage & italianoLanguage
	 *
	 * @throws Exception
	 */
	@Test(priority = 25, groups = {"REGRESSIONCAMPAIGN1","SMOKE","REGRESSION_CI","SMOKE_CI"}, enabled = false)
	public final void verifyAddCampaignFunctionalityForfrancaisanditalianolanguage() throws Exception {
		login("CAMPAIGNS_USER_TEST", "CAMPAIGNS_USER_TEST_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		CampaignsListPage campaignsListPage = new CampaignsListPage(PreDefinedActions.getDriver()).getInstance();
		CampaignDetailsPage campaigndetailsPage = new CampaignDetailsPage(PreDefinedActions.getDriver()).getInstance();
		sleeper(2000);
		preferredlanguage("francaisLanguage");
		gotoCampaignsTab();
		waitForPageLoaded();
		campaignsListPage.clickOnElementsOfCampaignsListPage("campaignListTab");
		waitForPageLoaded();
		campaignsListPage.clearFiltersOfCampaigsListPage("clearfilter");
		campaigndetailsPage.waitForElementsOfCampaignDetailsPage("tableOverLay");
		sleeper(2000);
		campaignsListPage.clickOnElementsOfCampaignsListPage("addCampaignButton");
		campaigndetailsPage.waitForElementsOfCampaignDetailsPage("tableOverLay");
		campaigndetailsPage.waitForElementsOfCampaignDetailsPage("newCampaignTitle");
		softAssert.assertTrue(campaignsListPage.getTextOfCampaignsListPage("newCampaignTitle").equals(campaignsListPage.getTextLanguage(LanguageCode, "daas_ui", "campaign.label.new-campaign")), "Add campaign page did not opened after clicking on add button");
		String category = campaigndetailsPage.getTextLanguage(LanguageCode, "daas_ui","product.list.peripherals");
		if(campaigndetailsPage.verifyElementsOfCampaignDetailsPage("headerdismiss")==true){
			campaigndetailsPage.clickOnElementsOfCampaignDetailsPage("headerdismiss");
		}
		sleeper(2000);
		campaigndetailsPage.clickOnElementsOfCampaignDetailsPage("categoryDropDown");
		campaigndetailsPage.waitForElementsOfCampaignDetailsPage("categoryDropDownOptions");
		softAssert.assertTrue(campaigndetailsPage.createCampaign(category, "categoryList", LanguageCode), "Campaign did not created successfully");
		preferredlanguage("italianoLanguage");
		gotoCampaignsTab();
		waitForPageLoaded();
		campaignsListPage.clickOnElementsOfCampaignsListPage("campaignListTab");
		waitForPageLoaded();
		campaignsListPage.clearFiltersOfCampaigsListPage("clearfilter");
		campaigndetailsPage.waitForElementsOfCampaignDetailsPage("tableOverLay");
		sleeper(2000);
		campaignsListPage.clickOnElementsOfCampaignsListPage("addCampaignButton");
		campaigndetailsPage.waitForElementsOfCampaignDetailsPage("tableOverLay");
		campaigndetailsPage.waitForElementsOfCampaignDetailsPage("newCampaignTitle");
		softAssert.assertTrue(campaignsListPage.getTextOfCampaignsListPage("newCampaignTitle").equals(campaignsListPage.getTextLanguage(LanguageCode, "daas_ui", "campaign.label.new-campaign")), "Add campaign page did not opened after clicking on add button");
		String category2 = campaigndetailsPage.getTextLanguage(LanguageCode, "daas_ui","assets.details.inventory.hardware");
		if(campaigndetailsPage.verifyElementsOfCampaignDetailsPage("headerdismiss")==true){
			campaigndetailsPage.clickOnElementsOfCampaignDetailsPage("headerdismiss");
		}
		campaigndetailsPage.clickOnElementsOfCampaignDetailsPage("categoryDropDown");
		campaigndetailsPage.waitForElementsOfCampaignDetailsPage("categoryDropDownOptions");
		softAssert.assertTrue(campaigndetailsPage.createCampaign(category2, "categoryList", LanguageCode), "Campaign did not created successfully");
		preferredlanguage("englishLanguage");
		softAssert.assertAll();
		LOGGER.info("Verify Add campaign functionality for francaisLanguage & italianoLanguage passed successfully.");
	}
	
	/**
	 * This method will verify the the create campaign functionality for norskLanguage & portuguesLanguage
	 *
	 * @throws Exception
	 */
	@Test(priority = 26, groups = {"REGRESSIONCAMPAIGN1","SMOKE","REGRESSION_CI","SMOKE_CI"}, enabled = false)
	public final void verifyAddCampaignFunctionalityFornorskandportugueslanguage() throws Exception {
		login("CAMPAIGNS_USER_TEST", "CAMPAIGNS_USER_TEST_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		CampaignsListPage campaignsListPage = new CampaignsListPage(PreDefinedActions.getDriver()).getInstance();
		CampaignDetailsPage campaigndetailsPage = new CampaignDetailsPage(PreDefinedActions.getDriver()).getInstance();
		sleeper(2000);
		preferredlanguage("norskLanguage");
		gotoCampaignsTab();
		waitForPageLoaded();
		campaignsListPage.clickOnElementsOfCampaignsListPage("campaignListTab");
		waitForPageLoaded();
		campaignsListPage.clearFiltersOfCampaigsListPage("clearfilter");
		campaigndetailsPage.waitForElementsOfCampaignDetailsPage("tableOverLay");
		sleeper(2000);
		campaignsListPage.clickOnElementsOfCampaignsListPage("addCampaignButton");
		campaigndetailsPage.waitForElementsOfCampaignDetailsPage("tableOverLay");
		campaigndetailsPage.waitForElementsOfCampaignDetailsPage("newCampaignTitle");
		softAssert.assertTrue(campaignsListPage.getTextOfCampaignsListPage("newCampaignTitle").equals(campaignsListPage.getTextLanguage(LanguageCode, "daas_ui", "campaign.label.new-campaign")), "Add campaign page did not opened after clicking on add button");
		String category = campaigndetailsPage.getTextLanguage(LanguageCode, "daas_ui","assets.details.inventory.hardware");
		if(campaigndetailsPage.verifyElementsOfCampaignDetailsPage("headerdismiss")==true){
			campaigndetailsPage.clickOnElementsOfCampaignDetailsPage("headerdismiss");
		}
		sleeper(2000);
		campaigndetailsPage.clickOnElementsOfCampaignDetailsPage("categoryDropDown");
		campaigndetailsPage.waitForElementsOfCampaignDetailsPage("categoryDropDownOptions");
		softAssert.assertTrue(campaigndetailsPage.createCampaign(category, "categoryList", LanguageCode), "Campaign did not created successfully");
		preferredlanguage("portuguesLanguage");
		gotoCampaignsTab();
		waitForPageLoaded();
		campaignsListPage.clickOnElementsOfCampaignsListPage("campaignListTab");
		waitForPageLoaded();
		campaignsListPage.clearFiltersOfCampaigsListPage("clearfilter");
		campaigndetailsPage.waitForElementsOfCampaignDetailsPage("tableOverLay");
		sleeper(2000);
		campaignsListPage.clickOnElementsOfCampaignsListPage("addCampaignButton");
		campaigndetailsPage.waitForElementsOfCampaignDetailsPage("tableOverLay");
		campaigndetailsPage.waitForElementsOfCampaignDetailsPage("newCampaignTitle");
		softAssert.assertTrue(campaignsListPage.getTextOfCampaignsListPage("newCampaignTitle").equals(campaignsListPage.getTextLanguage(LanguageCode, "daas_ui", "campaign.label.new-campaign")), "Add campaign page did not opened after clicking on add button");
		String category2 = campaigndetailsPage.getTextLanguage(LanguageCode, "daas_ui","incidents.com.hp.mpi.icm.type.security");
		if(campaigndetailsPage.verifyElementsOfCampaignDetailsPage("headerdismiss")==true){
			campaigndetailsPage.clickOnElementsOfCampaignDetailsPage("headerdismiss");
		}
		campaigndetailsPage.clickOnElementsOfCampaignDetailsPage("categoryDropDown");
		campaigndetailsPage.waitForElementsOfCampaignDetailsPage("categoryDropDownOptions");
		softAssert.assertTrue(campaigndetailsPage.createCampaign(category2, "categoryList", LanguageCode), "Campaign did not created successfully");
		preferredlanguage("englishLanguage");
		softAssert.assertAll();
		LOGGER.info("Verify Add campaign functionality for norskLanguage & portuguesLanguage passed successfully.");
	}
	
	/**
	 * This method will verify the add custom campaign functionality for Action Campaign category with new whitelisted HPIT domains.
	 * 
	 * @throws Exception
	 */
	@Test(priority = 27, groups = { "REGRESSIONCAMPAIGN1", "SMOKE", "REGRESSION_CI","SMOKE_CI" }, description = "User Story ID : 1201795", enabled = true)
	public final void verifyCustomActionCampaignswithHPITnewwhitelistedomains() throws Exception {
		login("CAMPAIGNS_USER_TEST", "CAMPAIGNS_USER_TEST_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		CampaignsListPage campaignsListPage = new CampaignsListPage(PreDefinedActions.getDriver()).getInstance();
		CampaignDetailsPage campaigndetailsPage = new CampaignDetailsPage(PreDefinedActions.getDriver()).getInstance();
		gotoCampaignsTab();
		waitForPageLoaded();
		campaignsListPage.clickOnElementsOfCampaignsListPage("campaignListTab");
		waitForPageLoaded();
		campaignsListPage.clearFiltersOfCampaigsListPage("clearfilter");
		
		//Verify add custom campaign functionality for Action Campaign category with new white-listed HPIT domains.
				campaignsListPage.clickOnElementsOfCampaignsListPage("addCampaignButton");
				campaigndetailsPage.waitForElementsOfCampaignDetailsPage("tableOverLay");
				softAssert.assertTrue(campaignsListPage.getTextOfCampaignsListPage("newCampaignTitle").equals(campaignsListPage.getTextLanguage(LanguageCode, "daas_ui", "campaign.label.new-campaign")), "Add campaign page did not opened after clicking on add button");
				String category = campaigndetailsPage.getTextLanguage(LanguageCode, "Campaign-backend-properties", "campaign.label.category.action_campaign");
				if(campaigndetailsPage.verifyElementsOfCampaignDetailsPage("headerdismiss")==true){
					campaigndetailsPage.clickOnElementsOfCampaignDetailsPage("headerdismiss");
				}
				sleeper(2000);
				if (toggleVerification(CampaignDetailsPage.Action_Campaign_toggle, getCredentials(environment, "CAMPAIGNS_USER_TEST"))) {
				campaigndetailsPage.clickOnElementsOfCampaignDetailsPage("categoryDropDown");
				campaigndetailsPage.waitForElementsOfCampaignDetailsPage("categoryDropDownOptions");
				String subcategory = campaigndetailsPage.getTextLanguage(LanguageCode, "Campaign-backend-properties", "campaign.label.subcategory.custom_action_campaign");
				Assert.assertTrue(campaigndetailsPage.createCustomActionCampaign(category,subcategory, "categoryList",CampaignDetailsPage.Valid_First_HPIT_Domain,CampaignDetailsPage.InValid_First_HPIT_Domain, LanguageCode), "Campaign did not created successfully cannot proceed further");
				LOGGER.info("Created campaign with User Sentiment category.");
				campaigndetailsPage.clickOnElementsOfCampaignDetailsPage("newcampaigntab");
				
				//Verify Search functionality for Action Campaign category with new white-listed HPIT domains.
				campaignsListPage.clearFiltersOfCampaigsListPage("clearfilter");
				waitForPageLoaded();
				campaignsListPage.scrollOnCampaignListPage("categoryColumnSearchBox");
				campaignsListPage.enterTextForCampaignsListPage("categoryColumnSearchBox", category);
				campaignsListPage.waitForElementsOfCampaignsListPage("tableOverlay");
				softAssert.assertFalse(campaignsListPage.verifyElementsOfCampaignsListPage("noElementsDisplayText"), "Search functionality not working with User Sentiment Category");
				campaignsListPage.scrollOnCampaignListPage("responsesColumnData");
				softAssert.assertTrue(campaignsListPage.getTextOfCampaignsListPage("responsesColumnData").equals(campaignsListPage.getTextLanguage(LanguageCode, "daas_ui", "global.list.column.na")), "N/A not present in the responses column for user sentiment campaign category");
				LOGGER.info("Verified search functionality on list page for User Sentiment Category"); 
				campaignsListPage.scrollOnCampaignListPage("nameHeader");
				
				//Verify Response result and Device health tab not present on details page for Action Campaign category with new white-listed HPIT domains.
				campaignsListPage.clickOnElementsOfCampaignsListPage("firstCampaignOnListPage");
				campaignsListPage.waitForElementsOfCampaignsListPage("tableOverlay");
				softAssert.assertFalse(campaigndetailsPage.verifyElementsOfCampaignDetailsPage("responseResultsTab"), "Response results tab is present on campaign details page for campaign with User Sentiment category.");
				softAssert.assertFalse(campaigndetailsPage.verifyElementsOfCampaignDetailsPage("deviceHealthTab"), "Device healt tab is present on campaign details pagefor campaign with User sentiment category.");
				LOGGER.info("Verified campaign details page.");
				
				//Verify update functionality for Action Campaign category with new white-listed HPIT domains.
				campaigndetailsPage.waitForElementsOfCampaignDetailsPage("endDatePicker");
				campaigndetailsPage.scrollOnCampaignDetailsPage("endDatePicker");
				campaigndetailsPage.clickOnElementsOfCampaignDetailsPage("endDatePicker");
				campaigndetailsPage.waitForElementsOfCampaignDetailsPage("monthKeyCurrent");
				campaigndetailsPage.selectDateFromCalenderOnCampaignDetailpage(addDaysToCurrentDate(4), "monthKeyCurrent","endDateDialogRightArrow", "daysOnCurrentMonthKey");
				campaigndetailsPage.clickOnElementsOfCampaignDetailsPage("saveButton");
				sleeper(2000);
				campaigndetailsPage.waitForElementsOfCampaignDetailsPage("toastNotification");
				softAssert.assertTrue(campaigndetailsPage.getTextOfCampaignDetailsPage("toastNotification").equals(getTextLanguage(LanguageCode, "daas_ui", "campaign.update_campaign.toast")), "Toast notification is not generated after updating campaign.");
				LOGGER.info("Verified updated functionality for campaign.");
				
				//Verify duplicate functionality for Action Campaign category with new white-listed HPIT domains
				campaignsListPage.mousehoverOnCampaignListPage("firstIdLogo");
				campaignsListPage.clickByJavaScriptOnCampaignListPage("firstIdCheckBox");
				campaignsListPage.clickOnElementsOfCampaignsListPage("closeInformationPanel");
				campaignsListPage.waitForElementsOfCampaignsListPage("duplicateButton");
				campaignsListPage.clickOnElementsOfCampaignsListPage("duplicateButton");
				softAssert.assertTrue(campaignsListPage.getTextOfCampaignsListPage("duplicateCampaignTitle").equals(campaignsListPage.getTextLanguage(LanguageCode, "daas_ui", "campaign.label.new-campaign")), "Add campaign page did not opened after clicking on duplicate campaign button");
				campaigndetailsPage.scrollOnCampaignDetailsPage("subcategorytext");
				campaigndetailsPage.enterTextForCampaignDetailsPage("customCampaignUrl",CampaignDetailsPage.InValid_Second_HPIT_Domain);
				campaigndetailsPage.clickOnElementsOfCampaignDetailsPage("accampaignpreviewButton");
				campaigndetailsPage.getTextOfCampaignDetailsPage("campaignurlerrormsg");
				sleeper(2000);
				campaigndetailsPage.enterTextForCampaignDetailsPage("customCampaignUrl",CampaignDetailsPage.Valid_Second_HPIT_Domain);
				campaigndetailsPage.clickOnElementsOfCampaignDetailsPage("accampaignpreviewButton");
				sleeper(4000);
				campaigndetailsPage.switchToDifferentTabOfCampaignsPage();
				LOGGER.info("Switched to new window successfully for duplicate campaign");
				sleeper(2000);
				campaigndetailsPage.switchToPreviousTabOfCampaignDetailsPage();
				LOGGER.info("Switched back to parent window successfully for duplicate campaign");
				campaigndetailsPage.scrollOnCampaignDetailsPage("locationongoing");
				campaigndetailsPage.clickOnElementsOfCampaignDetailsPage("startDateField");
				campaigndetailsPage.waitForElementsOfCampaignDetailsPage("currentDateOnCalender");
				campaigndetailsPage.clickOnElementsOfCampaignDetailsPage("currentDateOnCalender");
				campaigndetailsPage.waitForElementsOfCampaignDetailsPage("endDatePicker");
				campaigndetailsPage.clickOnElementsOfCampaignDetailsPage("endDatePicker");
				campaigndetailsPage.waitForElementsOfCampaignDetailsPage("monthKeyCurrent");
				campaigndetailsPage.selectDateFromCalenderOnCampaignDetailpage(addDaysToCurrentDate(2), "monthKeyCurrent","endDateDialogRightArrow", "daysOnCurrentMonthKey");
				campaigndetailsPage.clickOnElementsOfCampaignDetailsPage("saveButton");
				campaigndetailsPage.waitForElementsOfCampaignDetailsPage("toastNotification");
				softAssert.assertTrue(campaigndetailsPage.getTextOfCampaignDetailsPage("toastNotification").equals(getTextLanguage(LanguageCode, "daas_ui", "campaign.create_success.toast")), "Toast notification is not generated after duplicating campaign.");
				LOGGER.info("Verified duplicate functionality for campaign.");

				//Verify delete functionality for for Action Campaign category with new white-listed HPIT domains
				waitForPageLoaded();
				campaignsListPage.enterTextForCampaignsListPage("stateSearchBox",campaignsListPage.getTextLanguage(LanguageCode, "daas_ui","campaign.graph.status-scheduled"));
				if(campaignsListPage.verifyElementsOfCampaignsListPage("firstCampaignOnListPage")){
					campaignsListPage.clickOnElementsOfCampaignsListPage("firstCampaignOnListPage");
					campaigndetailsPage.waitForElementsOfCampaignDetailsPage("tableOverLay");
					campaigndetailsPage.scrollOnCampaignDetailsPage("endCampaignButton");
					campaigndetailsPage.clickOnElementsOfCampaignDetailsPage("endCampaignButton");
					campaigndetailsPage.waitForElementsOfCampaignDetailsPage("toastNotification");
					waitForPageLoaded();
					campaignsListPage.clearFiltersOfCampaigsListPage("clearfilter");
					campaignsListPage.waitForElementsOfCampaignsListPage("tableOverlay");
					campaignsListPage.enterTextForCampaignsListPage("stateSearchBox",campaignsListPage.getTextLanguage(LanguageCode, "daas_ui","campaign.graph.status-stopped"));
					campaigndetailsPage.waitForElementsOfCampaignDetailsPage("tableOverLay");
					campaignsListPage.mousehoverOnCampaignListPage("firstIdLogo");
					campaignsListPage.clickByJavaScriptOnCampaignListPage("firstIdCheckBox");
					campaignsListPage.clickOnElementsOfCampaignsListPage("closeInformationPanel");
					campaignsListPage.waitForElementsOfCampaignsListPage("deleteCampaignButton");
					campaignsListPage.clickOnElementsOfCampaignsListPage("deleteCampaignButton");
					sleeper(2000);
					campaignsListPage.clickOnElementsOfCampaignsListPage("okdeletebutton");
					campaigndetailsPage.waitForElementsOfCampaignDetailsPage("toastNotification");
					softAssert.assertTrue(campaigndetailsPage.getTextOfCampaignDetailsPage("toastNotification").equals(getTextLanguage(LanguageCode, "daas_ui", "campaign_list.actions.delete_success")), "Toast notification is not generated after deleting campaign.");
					LOGGER.info("Campaign deleted successfully.");
				}
				softAssert.assertAll();
				LOGGER.info("Test case for verifyCustomActionCampaignswithHPITnewwhitelistedomains passed successfully.");
				}
				else {
					LOGGER.info("Action campaign feature toggle is set to off for the provided credentials.");
				}
			}
	
	/**
	 * This method will validate the groups field selection in filter criteria section.
	 * 
	 * 
	 * @throws Exception
	 */
	@Test(priority = 28, dataProvider = "loginDatarolesForgroupsincampaigns", groups = { "REGRESSIONCAMPAIGN1", "SMOKE", "REGRESSION_CI","SMOKE_CI" }, description = "User Story ID : 1191257", enabled = true)
	public final void verifygroupsfieldselectioninfiltercriteriasection(String username, String password) throws Exception {
		login(username, password);
		SoftAssert softAssert = new SoftAssert();
		CampaignsListPage campaignsListPage = new CampaignsListPage(PreDefinedActions.getDriver()).getInstance();
		CampaignDetailsPage campaigndetailsPage = new CampaignDetailsPage(PreDefinedActions.getDriver()).getInstance();
		gotoCampaignsTab();
		waitForPageLoaded();
		campaignsListPage.clickOnElementsOfCampaignsListPage("campaignListTab");
		waitForPageLoaded();
		campaignsListPage.clearFiltersOfCampaigsListPage("clearfilter");	
		//Validation of Filter Criteria Selection for Groups field.
				campaignsListPage.clickOnElementsOfCampaignsListPage("addCampaignButton");
				campaigndetailsPage.waitForElementsOfCampaignDetailsPage("tableOverLay");
				softAssert.assertTrue(campaignsListPage.getTextOfCampaignsListPage("newCampaignTitle").equals(campaignsListPage.getTextLanguage(LanguageCode, "daas_ui", "campaign.label.new-campaign")), "Add campaign page did not opened after clicking on add button");
				String category = campaigndetailsPage.getTextLanguage(LanguageCode, "Campaign-backend-properties", "campaign.label.category.general_feedback");
				if(campaigndetailsPage.verifyElementsOfCampaignDetailsPage("headerdismiss")==true){
					campaigndetailsPage.clickOnElementsOfCampaignDetailsPage("headerdismiss");
				}
				sleeper(2000);
				campaigndetailsPage.clickOnElementsOfCampaignDetailsPage("categoryDropDown");
				campaigndetailsPage.waitForElementsOfCampaignDetailsPage("categoryDropDownOptions");
				String filtercriteria = campaigndetailsPage.getTextLanguage(LanguageCode, "Campaign-backend-properties", "campaign.label.filters.groups");
				if (toggleVerification(CampaignDetailsPage.TechPulse_Grouping_Toggle, getCredentials(environment, username))) {
					if(toggleVerification(CampaignDetailsPage.Groups_In_Campaigns_Toggle, getCredentials(environment, username))) {
						String campaignName = "TestCampaign" + generateRandomString(5);
				softAssert.assertTrue(campaigndetailsPage.verifyfiltercriteriaselectionforgroupsfilter(campaignName,category,"categoryList","subCategoryDropDown","firstElementOnSubcategoryDropDown","campaignNameTextBox","filtercriteriatile","filteroptionslist","addcriteria",filtercriteria,"valuedd","secondcriteriaheader","filtervalue1","filtervalue2","groupsvalue",LanguageCode));
				}else {
					softAssert.assertTrue(campaigndetailsPage.verifygroupsoptioninfilterswhentoggleoff(category,"categoryList","subCategoryDropDown","firstElementOnSubcategoryDropDown","campaignNameTextBox","filtercriteriatile",filtercriteria,"filteroptionslist","addcriteria",LanguageCode));
					LOGGER.info("Groups_In_Campaigns feature toggle is set to off for the provided credentials.");
				}
				}else {
					softAssert.assertTrue(campaigndetailsPage.verifygroupsoptioninfilterswhentoggleoff(category,"categoryList","subCategoryDropDown","firstElementOnSubcategoryDropDown","campaignNameTextBox","filtercriteriatile",filtercriteria,"filteroptionslist","addcriteria",LanguageCode));
					LOGGER.info("Techpulse-Grouping feature toggle is set to off for the provided credentials.");
					}	
				softAssert.assertAll();
				LOGGER.info("Test case for verifygroupsfieldselectioninfiltercriteriasection passed successfully.");
			}
	
	/**
	 * This method will validate the groups list from devices page with camapign creation page.
	 * 
	 * 
	 * @throws Exception
	 */
	@Test(priority = 29, groups = { "REGRESSIONCAMPAIGN1", "SMOKE", "REGRESSION_CI","SMOKE_CI" }, description = "User Story ID : 1191257", enabled = true)
	public final void verifygroupsfromdevicesandcampaigncreationpage() throws Exception {
		login("CAMPAIGNS_USER_TEST", "CAMPAIGNS_USER_TEST_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		CampaignsListPage campaignsListPage = new CampaignsListPage(PreDefinedActions.getDriver()).getInstance();
		CampaignDetailsPage campaigndetailsPage = new CampaignDetailsPage(PreDefinedActions.getDriver()).getInstance();
		DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
		gotoDevicesTab();	
		List<String> staticgroupsListfromdeviceslist = deviceListPage.getAllTextOfDeviceListPage("staticgrouplist");
		deviceListPage.clickOnElementsOfDeviceListPage("dynamicpanel");
		List<String> dynamicgroupsListfromdeviceslist = deviceListPage.getAllTextOfDeviceListPage("dynamicgrouplist");
		gotoCampaignsTab();
		waitForPageLoaded();
		campaignsListPage.clickOnElementsOfCampaignsListPage("campaignListTab");
		waitForPageLoaded();
		campaignsListPage.clearFiltersOfCampaigsListPage("clearfilter");	
		//Validation of groups list from devices list & campaign creation Groups field values.
				campaignsListPage.clickOnElementsOfCampaignsListPage("addCampaignButton");
				campaigndetailsPage.waitForElementsOfCampaignDetailsPage("tableOverLay");
				softAssert.assertTrue(campaignsListPage.getTextOfCampaignsListPage("newCampaignTitle").equals(campaignsListPage.getTextLanguage(LanguageCode, "daas_ui", "campaign.label.new-campaign")), "Add campaign page did not opened after clicking on add button");
				String category = campaigndetailsPage.getTextLanguage(LanguageCode, "Campaign-backend-properties", "campaign.label.category.general_feedback");
				if(campaigndetailsPage.verifyElementsOfCampaignDetailsPage("headerdismiss")==true){
					campaigndetailsPage.clickOnElementsOfCampaignDetailsPage("headerdismiss");
				}
				sleeper(2000);
				if (toggleVerification(CampaignDetailsPage.TechPulse_Grouping_Toggle, getCredentials(environment, "CAMPAIGNS_USER_TEST"))) {
					if(toggleVerification(CampaignDetailsPage.Groups_In_Campaigns_Toggle, getCredentials(environment, "CAMPAIGNS_USER_TEST"))) {
				campaigndetailsPage.clickOnElementsOfCampaignDetailsPage("categoryDropDown");
				campaigndetailsPage.waitForElementsOfCampaignDetailsPage("categoryDropDownOptions");
				String filtercriteria = campaigndetailsPage.getTextLanguage(LanguageCode, "Campaign-backend-properties", "campaign.label.filters.groups");
				softAssert.assertTrue(campaigndetailsPage.verifygroupslistfromdevicesandcampaigncreationpage(staticgroupsListfromdeviceslist,dynamicgroupsListfromdeviceslist,category,"categoryList","subCategoryDropDown","firstElementOnSubcategoryDropDown","campaignNameTextBox","filtercriteriatile",filtercriteria,"filteroptionslist","addcriteria","valuedd","groupsvaluelist",LanguageCode));
				softAssert.assertAll();
				LOGGER.info("Test case for verifygroupsfromdevicesandcampaigncreationpage passed successfully.");
				}else {
					LOGGER.info("Groups_In_Campaigns feature toggle is set to off for the provided credentials.");
				}
				}else {
					LOGGER.info("Techpulse-Grouping feature toggle is set to off for the provided credentials.");
					}	
			}
	
	/**
	 * This method will verify the filters with camapign creation/duplication/updating/delete Campaign.
	 * 
	 * 
	 * @throws Exception
	 */
	@Test(priority = 30, groups = { "REGRESSIONCAMPAIGN1", "SMOKE", "REGRESSION_CI","SMOKE_CI" }, description = "User Story ID : 1191257", enabled = true)
	public final void verifycampaigncreationpagewithgroupsfilter() throws Exception {
		login("CAMPAIGNS_USER_TEST", "CAMPAIGNS_USER_TEST_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		CampaignsListPage campaignsListPage = new CampaignsListPage(PreDefinedActions.getDriver()).getInstance();
		CampaignDetailsPage campaigndetailsPage = new CampaignDetailsPage(PreDefinedActions.getDriver()).getInstance();
		gotoCampaignsTab();
		waitForPageLoaded();
		campaignsListPage.clickOnElementsOfCampaignsListPage("campaignListTab");
		waitForPageLoaded();
		campaignsListPage.clearFiltersOfCampaigsListPage("clearfilter");	
		//Validation of groups list from devices list & campaign creation Groups field values.
				campaignsListPage.clickOnElementsOfCampaignsListPage("addCampaignButton");
				campaigndetailsPage.waitForElementsOfCampaignDetailsPage("tableOverLay");
				softAssert.assertTrue(campaignsListPage.getTextOfCampaignsListPage("newCampaignTitle").equals(campaignsListPage.getTextLanguage(LanguageCode, "daas_ui", "campaign.label.new-campaign")), "Add campaign page did not opened after clicking on add button");
				String category = campaigndetailsPage.getTextLanguage(LanguageCode, "Campaign-backend-properties", "campaign.label.category.general_feedback");
				if(campaigndetailsPage.verifyElementsOfCampaignDetailsPage("headerdismiss")==true){
					campaigndetailsPage.clickOnElementsOfCampaignDetailsPage("headerdismiss");
				}
				sleeper(2000);
				if (toggleVerification(CampaignDetailsPage.TechPulse_Grouping_Toggle, getCredentials(environment, "CAMPAIGNS_USER_TEST"))) {
					if(toggleVerification(CampaignDetailsPage.Groups_In_Campaigns_Toggle, getCredentials(environment, "CAMPAIGNS_USER_TEST"))) {
				campaigndetailsPage.clickOnElementsOfCampaignDetailsPage("categoryDropDown");
				campaigndetailsPage.waitForElementsOfCampaignDetailsPage("categoryDropDownOptions");
				String filtercriteria = campaigndetailsPage.getTextLanguage(LanguageCode, "Campaign-backend-properties", "campaign.label.filters.groups");
				String campaignName = "TestCampaign" + generateRandomString(5);
				softAssert.assertTrue(campaigndetailsPage.verifygroupcreationwithgroupsfilter(campaignName,category,"categoryList","subCategoryDropDown","firstElementOnSubcategoryDropDown","campaignNameTextBox","filtercriteriatile","filteroptionslist","addcriteria",filtercriteria,"valuedd",LanguageCode));
				//Verify the groups filter on details page for Normal Campaign category.
				campaignsListPage.clickOnElementsOfCampaignsListPage("firstCampaignOnListPage");
				campaignsListPage.waitForElementsOfCampaignsListPage("tableOverlay");
				campaigndetailsPage.scrollOnCampaignDetailsPage("criteriaheaderongoing");
				campaigndetailsPage.getTextOfCampaignDetailsPage("filterfieldongoing").equals(filtercriteria);
				softAssert.assertTrue(campaigndetailsPage.verifyElementsOfCampaignDetailsPage("responseResultsTab"), "Response results tab is present on campaign details page for campaign with User Sentiment category.");
				softAssert.assertTrue(campaigndetailsPage.verifyElementsOfCampaignDetailsPage("deviceHealthTab"), "Device healt tab is present on campaign details pagefor campaign with User sentiment category.");
				LOGGER.info("Verified campaign details page.");
				
				//Verify update campaign with groups filter on details page for Normal Campaign category.
				
				campaigndetailsPage.waitForElementsOfCampaignDetailsPage("endDatePicker");
				campaigndetailsPage.scrollOnCampaignDetailsPage("endDatePicker");
				campaigndetailsPage.clickOnElementsOfCampaignDetailsPage("endDatePicker");
				campaigndetailsPage.waitForElementsOfCampaignDetailsPage("monthKeyCurrent");
				campaigndetailsPage.selectDateFromCalenderOnCampaignDetailpage(addDaysToCurrentDate(4), "monthKeyCurrent","endDateDialogRightArrow", "daysOnCurrentMonthKey");
				campaigndetailsPage.clickOnElementsOfCampaignDetailsPage("saveButton");
				sleeper(2000);
				campaigndetailsPage.waitForElementsOfCampaignDetailsPage("toastNotification");
				softAssert.assertTrue(campaigndetailsPage.getTextOfCampaignDetailsPage("toastNotification").equals(getTextLanguage(LanguageCode, "daas_ui", "campaign.update_campaign.toast")), "Toast notification is not generated after updating campaign.");
				LOGGER.info("Verified updated functionality for campaign.");
				sleeper(4000);
				//Verify duplicate campaign with groups filter on details page for Normal Campaign category.
				campaignsListPage.mousehoverOnCampaignListPage("firstIdLogo");
				campaignsListPage.clickByJavaScriptOnCampaignListPage("firstIdCheckBox");
				campaignsListPage.clickOnElementsOfCampaignsListPage("closeInformationPanel");
				campaignsListPage.waitForElementsOfCampaignsListPage("duplicateButton");
				campaignsListPage.clickOnElementsOfCampaignsListPage("duplicateButton");
				softAssert.assertTrue(campaignsListPage.getTextOfCampaignsListPage("duplicateCampaignTitle").equals(campaignsListPage.getTextLanguage(LanguageCode, "daas_ui", "campaign.label.new-campaign")), "Add campaign page did not opened after clicking on duplicate campaign button");
				sleeper(2000);
				campaigndetailsPage.clickOnElementsOfCampaignDetailsPage("categoryDD");
				campaigndetailsPage.clickOnElementsOfCampaignDetailsPage("categoryDropDownOptions");
				campaigndetailsPage.scrollOnCampaignDetailsPage("subcategorytext");
				campaigndetailsPage.enterTextForCampaignDetailsPage("customCampaignUrl",CampaignDetailsPage.InValid_Second_HPIT_Domain);
				campaigndetailsPage.clickOnElementsOfCampaignDetailsPage("accampaignpreviewButton");
				campaigndetailsPage.getTextOfCampaignDetailsPage("campaignurlerrormsg");
				sleeper(2000);
				campaigndetailsPage.enterTextForCampaignDetailsPage("customCampaignUrl",CampaignDetailsPage.Valid_Second_HPIT_Domain);
				campaigndetailsPage.clickOnElementsOfCampaignDetailsPage("accampaignpreviewButton");
				sleeper(4000);
				campaigndetailsPage.switchToDifferentTabOfCampaignsPage();
				LOGGER.info("Switched to new window successfully for duplicate campaign");
				sleeper(2000);
				campaigndetailsPage.switchToPreviousTabOfCampaignDetailsPage();
				LOGGER.info("Switched back to parent window successfully for duplicate campaign");
				softAssert.assertTrue(campaigndetailsPage.verifyElementsOfCampaignDetailsPage("firstciteriaheader"), "filter criteria not present on duplicate page");
				campaigndetailsPage.scrollOnCampaignDetailsPage("locationongoing");
				campaigndetailsPage.clickOnElementsOfCampaignDetailsPage("startDateField");
				campaigndetailsPage.waitForElementsOfCampaignDetailsPage("currentDateOnCalender");
				campaigndetailsPage.clickOnElementsOfCampaignDetailsPage("currentDateOnCalender");
				campaigndetailsPage.waitForElementsOfCampaignDetailsPage("endDatePicker");
				campaigndetailsPage.clickOnElementsOfCampaignDetailsPage("endDatePicker");
				campaigndetailsPage.waitForElementsOfCampaignDetailsPage("monthKeyCurrent");
				campaigndetailsPage.selectDateFromCalenderOnCampaignDetailpage(addDaysToCurrentDate(2), "monthKeyCurrent","endDateDialogRightArrow", "daysOnCurrentMonthKey");
				campaigndetailsPage.clickOnElementsOfCampaignDetailsPage("saveButton");
				campaigndetailsPage.waitForElementsOfCampaignDetailsPage("toastNotification");
				softAssert.assertTrue(campaigndetailsPage.getTextOfCampaignDetailsPage("toastNotification").equals(getTextLanguage(LanguageCode, "daas_ui", "campaign.create_success.toast")), "Toast notification is not generated after duplicating campaign.");
				LOGGER.info("Verified duplicate functionality for campaign.");

				//Verify delete campaign with groups filter on details page for Normal Campaign category.
				waitForPageLoaded();
				campaignsListPage.enterTextForCampaignsListPage("stateSearchBox",campaignsListPage.getTextLanguage(LanguageCode, "daas_ui","campaign.graph.status-scheduled"));
				if(campaignsListPage.verifyElementsOfCampaignsListPage("firstCampaignOnListPage")){
					campaignsListPage.clickOnElementsOfCampaignsListPage("firstCampaignOnListPage");
					campaigndetailsPage.waitForElementsOfCampaignDetailsPage("tableOverLay");
					campaigndetailsPage.scrollOnCampaignDetailsPage("endCampaignButton");
					campaigndetailsPage.clickOnElementsOfCampaignDetailsPage("endCampaignButton");
					campaigndetailsPage.waitForElementsOfCampaignDetailsPage("toastNotification");
					waitForPageLoaded();
					campaignsListPage.clearFiltersOfCampaigsListPage("clearfilter");
					campaignsListPage.waitForElementsOfCampaignsListPage("tableOverlay");
					campaignsListPage.enterTextForCampaignsListPage("stateSearchBox",campaignsListPage.getTextLanguage(LanguageCode, "daas_ui","campaign.graph.status-stopped"));
					campaigndetailsPage.waitForElementsOfCampaignDetailsPage("tableOverLay");
					campaignsListPage.mousehoverOnCampaignListPage("firstIdLogo");
					campaignsListPage.clickByJavaScriptOnCampaignListPage("firstIdCheckBox");
					campaignsListPage.clickOnElementsOfCampaignsListPage("closeInformationPanel");
					campaignsListPage.waitForElementsOfCampaignsListPage("deleteCampaignButton");
					campaignsListPage.clickOnElementsOfCampaignsListPage("deleteCampaignButton");
					sleeper(2000);
					campaignsListPage.clickOnElementsOfCampaignsListPage("okdeletebutton");
					campaigndetailsPage.waitForElementsOfCampaignDetailsPage("toastNotification");
					softAssert.assertTrue(campaigndetailsPage.getTextOfCampaignDetailsPage("toastNotification").equals(getTextLanguage(LanguageCode, "daas_ui", "campaign_list.actions.delete_success")), "Toast notification is not generated after deleting campaign.");
					LOGGER.info("Campaign deleted successfully.");
			}
				softAssert.assertAll();
				LOGGER.info("Test case for verifycampaigncreationpagewithgroupsfilter passed successfully.");
				}else {
					LOGGER.info("Groups_In_Campaigns feature toggle is set to off for the provided credentials.");
				}
				}else {
					LOGGER.info("Techpulse-Grouping feature toggle is set to off for the provided credentials.");
					}	
			}
		
	}