package com.testscripts.daasui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import com.basesource.action.PreDefinedActions;
import com.basetest.environments.SetTestEnvironments;
import com.daasui.constants.CommonVariables;
import com.daasui.constants.ConstantURL;
import com.daasui.constants.VyoptaVariables;
import com.daasui.pages.AnalyticsPage;
import com.daasui.pages.WEPSustainabilityPage;


public class WEPSustainabilityTest extends CommonTest {
	private static Logger LOGGER = LogManager.getLogger(WEPSustainabilityTest.class);

	@DataProvider
	public Object[][] getLoginData() {
		Object[][] data = new Object[2][2];
		data[0][0] = "PARTNER_EMAIL_WEP";
		data[0][1] = "PARTNER_PASSWORD_WEP";
		data[1][0] = "ITADMIN_EMAIL_WEP";
		data[1][1] = "ITADMIN_PASSWORD_WEP";
		return data;
	}


	/**
	 * This Test Case C43408570" will verify the Sustainability link should be visible on Fleet Management Analytics tab on WEP portal
	 * @throws Exception
	 */
	@Test(priority = 0, groups = { "REGRESSION_INTEGRATIONS","PRODUCTION_PULSES_EE_SUSTAIN" }, description="Testcase ID: C43776778")
	public final void verifyWEPSustainabilityLinkOnCustomerView() throws Exception {
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		leftSideMenuVerification();
		AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver()).getInstance();
		analyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
		WEPSustainabilityPage sustainabilityPage = new WEPSustainabilityPage(PreDefinedActions.getDriver()).getInstance();
		waitForPageLoaded();
		sustainabilityPage.enterTextForsustainabilityPage("searchReport", "Sustainability");
		sleeper(2000);
		sustainabilityPage.waitForElementsOfSustainabilityPage("modernReports");
		sustainabilityPage.clickOnElementsOfSustainabilityPage("modernReports");
		sleeper(1000);
		sustainabilityPage.waitForElementsOfSustainabilityPage("sustainabilityLink");
		Assert.assertTrue(
				sustainabilityPage.getTextOfWEPSustainabilityPage("sustainabilityLink")
				.equals(sustainabilityPage.getTextLanguage(LanguageCode, "daas_ui", "sustainability.label.title")),"sustainability link doesn't exists");
		LOGGER.info("Sustainability Link is Available");
	}


	/**
	 * WEPINT-52 >>Add Service check from backend E.g. PI, pro plan and Verify the Sustainability option should be visible on Fleet Management Analytics tab
	 * https://hp-jira.external.hp.com/browse/WEXINT-52
	 * This Test CaseID:43776778 will verify the Sustainability option should be visible on Fleet Management Analytics tab on WEP portal
	 * @throws Exception
	 */
	@Test(priority = 12, groups = { "REGRESSION_INTEGRATIONS" }, description="Testcase ID: C43408570")
	public final void verifyWEPSustainabilityPageHeader() throws Exception {	
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		leftSideMenuVerification();
		AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver()).getInstance();
		analyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
		WEPSustainabilityPage sustainabilityPage = new WEPSustainabilityPage(PreDefinedActions.getDriver()).getInstance();
		waitForPageLoaded();
		sustainabilityPage.enterTextForsustainabilityPage("searchReport", "Sustainability");
		sleeper(2000);
		sustainabilityPage.waitForElementsOfSustainabilityPage("modernReports");
		sustainabilityPage.clickOnElementsOfSustainabilityPage("modernReports");
		sleeper(1000);
		sustainabilityPage.waitForElementsOfSustainabilityPage("sustainabilityLink");
		sustainabilityPage.clickOnElementsOfSustainabilityPage("sustainabilityLink");
		waitForPageLoaded();
		Assert.assertTrue(
				sustainabilityPage.getTextOfWEPSustainabilityPage("sustainabilityHeader")
				.equals(sustainabilityPage.getTextLanguage(LanguageCode, "daas_ui", "sustainability.label.title")),"sustainability title breadcrub doesn't match");
		LOGGER.info("Sustainability Header is matched");
	}


	/**
	 * WEPINT-78 >> Add power consumption widget into the fleet management dashboard with API interation
	 * https://hp-jira.external.hp.com/browse/WEXINT-78
	 * This Test CaseID:43776778 will verify the Verify Average Power consumption Widget on Fleet Mngmnt Dashboard
	 * @throws Exception
	 */
	@Test(priority = 2, groups = { "REGRESSION_INTEGRATIONS" }, description="C51266314",enabled = false)
	public final void verifyPowerConsumptionWidgetOnFleetManagementDashboard() throws Exception {	
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		leftSideMenuVerification();
		WEPSustainabilityPage sustainabilityPage = new WEPSustainabilityPage(PreDefinedActions.getDriver()).getInstance();
		waitForPageLoaded();
		if (!toggleVerification(VyoptaVariables.WEX_NAVIGATION_IMPVROVEMENTS_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
			sustainabilityPage.clickOnElementsOfSustainabilityPage("fleetManagement");
			Assert.assertTrue(
					sustainabilityPage.getTextOfWEPSustainabilityPage("fleetManagementSiteHeader")
					.equals(sustainabilityPage.getTextLanguage(LanguageCode, "daas_ui", "applications.fleetmanagement.breadcrumbs.title")),"FleetManagement Site Header doesn't match");
			LOGGER.info("Fleet Management Site Header is matched");
			waitForPageLoaded();
		}else
		{
			AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver()).getInstance();
			analyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
			waitForPageLoaded();
		}
		sustainabilityPage.clickOnElementsOfSustainabilityPage("fleetManagementWidget");
		Assert.assertTrue(
				sustainabilityPage.getTextOfWEPSustainabilityPage("fleetManagementSiteHeader")
				.equals(sustainabilityPage.getTextLanguage(LanguageCode, "daas_ui", "applications.fleetmanagement.breadcrumbs.title")),"FleetManagement Site Header doesn't match");
		LOGGER.info("Fleet Management Site Header is matched");

		waitForPageLoaded();
		Assert.assertTrue(
				sustainabilityPage.getTextOfWEPSustainabilityPage("powerConsumptionWidgetTitle")
				.equals(sustainabilityPage.getTextLanguage(LanguageCode, "daas_ui", "sustainability.powerconsumption.header")),"powerConsumptionWidgetTitle  doesn't match");
		LOGGER.info("PowerConsumption Widget Title Header is matched");
		sustainabilityPage.waitForElementsOfSustainabilityPage("powerConsumptionWidgeViewdetailsLink");
		Assert.assertTrue(
				sustainabilityPage.getTextOfWEPSustainabilityPage("powerConsumptionWidgeViewdetailsLink")
				.equals(sustainabilityPage.getTextLanguage(LanguageCode, "daas_ui", "ee_view_details")),"powerConsumptionWidgeViewdetailsLink  doesn't match");
		LOGGER.info("View details link on PowerConsumption Widget is matched");
	}

	/**
	 * WEPINT-77 >>[Sustainability][UI] Chart – Average power consumption by Over Time with API integration
	 * https://hp-jira.external.hp.com/browse/WEXINT-77
	 * This Test CaseID:C44512950 will Verify on click on View details link on Power consumption Over Time Widget on Fleet Mngmnt Dashboard it should redirect to Sustainability page
	 * @throws Exception
	 */
	@Test(priority = 3, groups = { "REGRESSION_INTEGRATIONS","PRODUCTION_PULSES_EE_SUSTAIN","REGRESSION_INTEGRATIONQA_CUJ" }, description="C44512949")
	public final void verifyViewDetailsLinkFunctionalityOnPowerConsumptionWidget() throws Exception {	
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		leftSideMenuVerification();
		AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver()).getInstance();
		analyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
		waitForPageLoaded();
		WEPSustainabilityPage sustainabilityPage = new WEPSustainabilityPage(PreDefinedActions.getDriver()).getInstance();
		waitForPageLoaded();
		if (!toggleVerification(VyoptaVariables.WEX_NAVIGATION_IMPVROVEMENTS_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
			waitForPageLoaded();
			sustainabilityPage.clickOnElementsOfSustainabilityPage("fleetManagement");
			Assert.assertTrue(
					sustainabilityPage.getTextOfWEPSustainabilityPage("fleetManagementSiteHeader")
					.equals(sustainabilityPage.getTextLanguage(LanguageCode, "daas_ui", "applications.fleetmanagement.breadcrumbs.title")),"FleetManagement Site Header doesn't match");
			LOGGER.info("Fleet Management Site Header is matched");
			waitForPageLoaded();
			Assert.assertTrue(
					sustainabilityPage.getTextOfWEPSustainabilityPage("powerConsumptionWidgetTitle")
					.equals(sustainabilityPage.getTextLanguage(LanguageCode, "daas_ui", "sustainability.powerconsumption.header")),"powerConsumptionWidgetTitle  doesn't match");
			LOGGER.info("PowerConsumption Widget Title Header is matched");

			if(sustainabilityPage.verifyElementsOfSustainabilityPage("powerConsumptionWidgeViewReportLink")) {
				sustainabilityPage.waitForElementsOfSustainabilityPage("powerConsumptionWidgeViewReportLink");
				Assert.assertTrue(
						sustainabilityPage.getTextOfWEPSustainabilityPage("powerConsumptionWidgeViewReportLink")
						.equals(sustainabilityPage.getTextLanguage(LanguageCode, "daas_ui", "digital.exp.score.recommended.reports_view_report")),"powerConsumptionWidgeViewdReportLink  doesn't match");
				LOGGER.info("View reports link on PowerConsumption Widget is matched");

				sustainabilityPage.clickOnElementsOfSustainabilityPage("powerConsumptionWidgeViewReportLink");
				waitForPageLoaded();
				Assert.assertTrue(
						sustainabilityPage.getTextOfWEPSustainabilityPage("sustainabilityHeader")
						.equals(sustainabilityPage.getTextLanguage(LanguageCode, "daas_ui", "sustainability.label.title")),"sustainability title breadcrub doesn't match");
				LOGGER.info("User is redirected to Sustainability Page and Header is matched");
			} else {
				LOGGER.info("View details link is not there on PowerConsumption Widget");

			}

		}else
		{
			waitForPageLoaded();
			sustainabilityPage.waitForElementsOfSustainabilityPage("searchReport");
			sustainabilityPage.enterTextForsustainabilityPage("searchReport", "Fleet Management");
			waitForPageLoaded();
			sustainabilityPage.clickOnElementsOfSustainabilityPage("fleetManagementWidget");
			Assert.assertTrue(
					sustainabilityPage.getTextOfWEPSustainabilityPage("fleetManagementSiteHeader")
					.equals(sustainabilityPage.getTextLanguage(LanguageCode, "daas_ui", "applications.fleetmanagement.breadcrumbs.title")),"FleetManagement Site Header doesn't match");
			LOGGER.info("Fleet Management Site Header is matched");

			waitForPageLoaded();
			Assert.assertTrue(
					sustainabilityPage.getTextOfWEPSustainabilityPage("powerConsumptionWidgetTitleOnFM")
					.equalsIgnoreCase(sustainabilityPage.getTextLanguage(LanguageCode, "daas_ui", "sustainability.powerconsumption.header")),"powerConsumptionWidgetTitle  doesn't match");
			LOGGER.info("PowerConsumption Widget Title Header is matched");
			sustainabilityPage.waitForElementsOfSustainabilityPage("powerConsumptionWidgeViewReportLink");


			if(sustainabilityPage.verifyElementsOfSustainabilityPage("powerConsumptionWidgeViewReportLink")) {
				sustainabilityPage.waitForElementsOfSustainabilityPage("powerConsumptionWidgeViewReportLink");
				Assert.assertTrue(
						sustainabilityPage.getTextOfWEPSustainabilityPage("powerConsumptionWidgeViewReportLink")
						.equals(sustainabilityPage.getTextLanguage(LanguageCode, "daas_ui", "digital.exp.score.recommended.reports_view_report")),"powerConsumptionWidgeViewdReportLink  doesn't match");
				LOGGER.info("View reports link on PowerConsumption Widget is matched");

				sustainabilityPage.clickOnElementsOfSustainabilityPage("powerConsumptionWidgeViewReportLink");
				waitForPageLoaded();
				Assert.assertTrue(
						sustainabilityPage.getTextOfWEPSustainabilityPage("sustainabilityHeader")
						.equals(sustainabilityPage.getTextLanguage(LanguageCode, "daas_ui", "sustainability.label.title")),"sustainability title breadcrub doesn't match");
				LOGGER.info("User is redirected to Sustainability Page and Header is matched");
			} else {
				LOGGER.info("View Report link is not there on PowerConsumption Widget");

			}
		}
	}


	/**
	 * WEPINT-77 >>	[WEXINT][Sustainability] Verify Power consumption by over time graph is as per the figma
	 * https://hp-jira.external.hp.com/browse/WEXINT-77
	 * This Test CaseID:C44727897 will verify the Verify Verify Power consumption by over time graph is as per the figma
	 * @throws Exception
	 */

	@Test(priority = 4, groups = { "REGRESSION_INTEGRATIONS","PRODUCTION_PULSES_EE_SUSTAIN","REGRESSION_INTEGRATIONQA_CUJ" , "PRODUCTION_LDK","INITECH_SOLUTIONS" }, description="C51266313")
	public final void verifyPowerConsumptionByOverTimeGraph() throws Exception {	
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		String testSuiteName = SetTestEnvironments.suiteName;
		if(testSuiteName.contains("LDK") || testSuiteName.contains("Initech")){
			switchUserBasedOnSuite(testSuiteName);
		}
		leftSideMenuVerification();
		AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver()).getInstance();
		analyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
		WEPSustainabilityPage sustainabilityPage = new WEPSustainabilityPage(PreDefinedActions.getDriver()).getInstance();
		waitForPageLoaded();
		sustainabilityPage.enterTextForsustainabilityPage("searchReport", "Sustainability");
		sleeper(2000);
		sustainabilityPage.waitForElementsOfSustainabilityPage("modernReports");
		sustainabilityPage.clickOnElementsOfSustainabilityPage("modernReports");
		sleeper(1000);
		sustainabilityPage.waitForElementsOfSustainabilityPage("sustainabilityLink");
		sustainabilityPage.clickOnElementsOfSustainabilityPage("sustainabilityLink");
		waitForPageLoaded();
		Assert.assertTrue(
				sustainabilityPage.getTextOfWEPSustainabilityPage("sustainabilityHeader")
				.equals(sustainabilityPage.getTextLanguage(LanguageCode, "daas_ui", "sustainability.label.title")),"sustainability title breadcrumb doesn't match");
		LOGGER.info("Sustainability Header is matched");
		Assert.assertTrue(
				sustainabilityPage.getTextOfWEPSustainabilityPage("powerConsumptionWidgetTitle")
				.equals(sustainabilityPage.getTextLanguage(LanguageCode, "daas_ui", "sustainability.powerconsumption.header")),"powerConsumptionby over time  doesn't match");
		LOGGER.info("PowerConsumption by over time title Header is matched");

	}

	/**
	 * WEPINT-76 >>	[Sustainability][UI] Chart - Power Consumption by Model integration with API
	 * https://hp-jira.external.hp.com/browse/WEXINT-76
	 * This Test CaseID:C44209686 >> [WEXINT][Sustainability] Verify Power consumption by Model graph is as per the Figma
	 * @throws Exception
	 */

	@Test(priority = 5, groups = { "REGRESSION_INTEGRATIONS","PRODUCTION_PULSES_EE_SUSTAIN","REGRESSION_INTEGRATIONQA_CUJ","INITECH_SOLUTIONS"}, description="C44209686")
	public final void verifyPowerConsumptionByModelGraph() throws Exception {	
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		String testSuiteName = SetTestEnvironments.suiteName;
		if(testSuiteName.contains("LDK") || testSuiteName.contains("Initech")){
			switchUserBasedOnSuite(testSuiteName);
		}
		leftSideMenuVerification();
		AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver()).getInstance();
		analyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
		WEPSustainabilityPage sustainabilityPage = new WEPSustainabilityPage(PreDefinedActions.getDriver()).getInstance();
		waitForPageLoaded();
		sustainabilityPage.enterTextForsustainabilityPage("searchReport", "Sustainability");
		sleeper(2000);
		sustainabilityPage.waitForElementsOfSustainabilityPage("modernReports");
		sustainabilityPage.clickOnElementsOfSustainabilityPage("modernReports");
		sleeper(1000);
		sustainabilityPage.waitForElementsOfSustainabilityPage("sustainabilityLink");
		sustainabilityPage.clickOnElementsOfSustainabilityPage("sustainabilityLink");
		waitForPageLoaded();
		Assert.assertTrue(
				sustainabilityPage.getTextOfWEPSustainabilityPage("sustainabilityHeader")
				.equals(sustainabilityPage.getTextLanguage(LanguageCode, "daas_ui", "sustainability.label.title")),"sustainability title breadcrumb doesn't match");
		LOGGER.info("Sustainability Header is matched");
		Assert.assertTrue(
				sustainabilityPage.getTextOfWEPSustainabilityPage("powerConsumptionByModelTitle")
				.equals(sustainabilityPage.getTextLanguage(LanguageCode, "daas_ui", "sustainability.power_consumption_by_model")),"powerConsumption by Model  doesn't match");
		LOGGER.info("PowerConsumption by Model Title Header is matched");

	}

	/**
	 * WEPINT-30 >>	Global Visualization of IT Fleet Power Consumption by Location
	 * https://hp-jira.external.hp.com/browse/WEXINT-30
	 * This Test CaseID:C44210438 [WEXINT][Sustainability] Verify Average Power consumed by location graph is as per the Figma
	 * @throws Exception
	 */

	@Test(priority = 6, groups = { "REGRESSION_INTEGRATIONS","PRODUCTION_PULSES_EE_SUSTAIN","REGRESSION_INTEGRATIONQA_CUJ", "PRODUCTION_LDK","INITECH_SOLUTIONS" }, description="C51266312")
	public final void verifyPowerConsumptionByLocationGraph() throws Exception {	
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		String testSuiteName = SetTestEnvironments.suiteName;
		if(testSuiteName.contains("LDK") || testSuiteName.contains("Initech")){
			switchUserBasedOnSuite(testSuiteName);
		}
		leftSideMenuVerification();
		AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver()).getInstance();
		analyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
		WEPSustainabilityPage sustainabilityPage = new WEPSustainabilityPage(PreDefinedActions.getDriver()).getInstance();
		waitForPageLoaded();
		sustainabilityPage.enterTextForsustainabilityPage("searchReport", "Sustainability");
		sleeper(2000);
		sustainabilityPage.waitForElementsOfSustainabilityPage("modernReports");
		sustainabilityPage.clickOnElementsOfSustainabilityPage("modernReports");
		sleeper(1000);
		sustainabilityPage.waitForElementsOfSustainabilityPage("sustainabilityLink");
		sustainabilityPage.clickOnElementsOfSustainabilityPage("sustainabilityLink");
		waitForPageLoaded();
		Assert.assertTrue(
				sustainabilityPage.getTextOfWEPSustainabilityPage("sustainabilityHeader")
				.equals(sustainabilityPage.getTextLanguage(LanguageCode, "daas_ui", "sustainability.label.title")),"sustainability title breadcrumb doesn't match");
		LOGGER.info("Sustainability Header is matched");
		Assert.assertTrue(
				sustainabilityPage.getTextOfWEPSustainabilityPage("powerConsumptionByLocationTitle")
				.equals(sustainabilityPage.getTextLanguage(LanguageCode, "daas_ui", "sustainability.location.title")),"powerConsumption by Location  doesn't match");
		LOGGER.info("PowerConsumption by Location Title Header is matched");
	}

	/**
	 * WEPINT-45 >>	Export the excel sheet from Sustainability dashboard list page
	 * https://hp-jira.external.hp.com/browse/WEXINT-45
	 * This Test CaseID:C43824536 >> [WEXINT][Sustainability] Verify Download functionality on the Sustainability Device list grid
	 * @throws Exception
	 */

	@Test(priority = 7, groups = { "REGRESSION_INTEGRATIONS","PRODUCTION_PULSES_EE_SUSTAIN" , "PRODUCTION_LDK","INITECH_SOLUTIONS"}, description="C43824536")
	public final void verifyExportonSustainabilityPage() throws Exception {	
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		String testSuiteName = SetTestEnvironments.suiteName;
		if(testSuiteName.contains("LDK") || testSuiteName.contains("Initech")){
			switchUserBasedOnSuite(testSuiteName);
		}
		leftSideMenuVerification();
		AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver()).getInstance();
		analyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
		WEPSustainabilityPage sustainabilityPage = new WEPSustainabilityPage(PreDefinedActions.getDriver()).getInstance();
		waitForPageLoaded();
		sustainabilityPage.enterTextForsustainabilityPage("searchReport", "Sustainability");
		sleeper(2000);
		sustainabilityPage.waitForElementsOfSustainabilityPage("modernReports");
		sustainabilityPage.clickOnElementsOfSustainabilityPage("modernReports");
		sleeper(1000);
		sustainabilityPage.waitForElementsOfSustainabilityPage("sustainabilityLink");		
		sustainabilityPage.clickOnElementsOfSustainabilityPage("sustainabilityLink");
		waitForPageLoaded();
		Assert.assertTrue(
				sustainabilityPage.getTextOfWEPSustainabilityPage("sustainabilityHeader")
				.equals(sustainabilityPage.getTextLanguage(LanguageCode, "daas_ui", "sustainability.label.title")),"sustainability title breadcrumb doesn't match");
		LOGGER.info("Sustainability Header is matched");
		sustainabilityPage.verifyExportFunctionality(LanguageCode);
		LOGGER.info("Export functionality validation for sustainability has been completed successfully.");
	}

	/**
//	 * WEPINT-25 >>	Sustainability tab in Device List page
//	 * https://hp-jira.external.hp.com/browse/WEXINT-25
//	 * This Test CaseID:C44512452 >> [WEXINT][Sustainability]>>Verify sustainability tab is showing on device details page
//	 * @throws Exception
//	 */

	@Test(priority = 8, groups = { "REGRESSION_INTEGRATIONS" , "PRODUCTION_LDK","INITECH_SOLUTIONS"}, description="C44512452")
	public final void verifySustainabilityTabonDeviceDetails() throws Exception {	
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		String testSuiteName = SetTestEnvironments.suiteName;
		if(testSuiteName.contains("LDK") || testSuiteName.contains("Initech")){
			switchUserBasedOnSuite(testSuiteName);
		}
		leftSideMenuVerification();
		WEPSustainabilityPage sustainabilityPage = new WEPSustainabilityPage(PreDefinedActions.getDriver()).getInstance();
		waitForPageLoaded();
		AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver()).getInstance();
		analyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
		waitForPageLoaded();
		sustainabilityPage.enterTextForsustainabilityPage("searchReport", "Sustainability");
		sleeper(2000);
		sustainabilityPage.waitForElementsOfSustainabilityPage("modernReports");
		sustainabilityPage.clickOnElementsOfSustainabilityPage("modernReports");
		sleeper(1000);
		sustainabilityPage.waitForElementsOfSustainabilityPage("sustainabilityLink");
		sustainabilityPage.clickOnElementsOfSustainabilityPage("sustainabilityLink");
		waitForPageLoaded();
		Assert.assertTrue(
				sustainabilityPage.getTextOfWEPSustainabilityPage("sustainabilityHeader")
				.equals(sustainabilityPage.getTextLanguage(LanguageCode, "daas_ui", "sustainability.label.title")),"sustainability title breadcrub doesn't match");
		LOGGER.info("Sustainability Header is matched");
		if(sustainabilityPage.verifyElementsOfSustainabilityPage("clearFilterButtonOnDeviceGrid")) {
			sustainabilityPage.clickOnElementsOfSustainabilityPage("clearFilterButtonOnDeviceGrid");		
		}
		//click on any device from grid below
		if (!sustainabilityPage.verifyElementsOfSustainabilityPage("noItemsAvailable")) {
			sustainabilityPage.clickOnElementsOfSustainabilityPage("deviceSNO");
			if (!sustainabilityPage.verifyElementsOfSustainabilityPage("sustainabilityTab")) {
				sustainabilityPage.clickOnElementsOfSustainabilityPage("sustainabilityTab");
				sleeper(1000);
				LOGGER.info("Clicked on Sustainability tab");
			}else {
				LOGGER.info("Failed to verify the Sustainability tab on Device Details Page");
			}

		}else
		{
			LOGGER.info("No device data available on suatainability grid");
		}
	}

	/**
	 * WEXINT-277 >> Verify All the columns related to sustainability device list table page
	 * https://hp-jira.external.hp.com/browse/WEXINT-277, 46
	 * This Test CaseID:C44890319 - [WEXINT][Sustainability]>>Verify All the columns related to sustainability device list table page
	 * @throws Exception
	 */

	@Test(priority = 9, groups = { "REGRESSION_INTEGRATIONS" }, description="C44890319",enabled=false)
	public final void verifyDeviceListTableConfigurationFunctionalityOnSustainabilityPage() throws Exception {	
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		leftSideMenuVerification();
		WEPSustainabilityPage sustainabilityPage = new WEPSustainabilityPage(PreDefinedActions.getDriver()).getInstance();
		waitForPageLoaded();
		//		sleeper(5000);// Url takes time to load
		AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver()).getInstance();
		analyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
		waitForPageLoaded();
		sustainabilityPage.enterTextForsustainabilityPage("searchReport", "Sustainability");
		sleeper(2000);
		sustainabilityPage.waitForElementsOfSustainabilityPage("modernReports");
		sustainabilityPage.clickOnElementsOfSustainabilityPage("modernReports");
		sleeper(1000);
		sustainabilityPage.waitForElementsOfSustainabilityPage("sustainabilityLink");
		sustainabilityPage.clickOnElementsOfSustainabilityPage("sustainabilityLink");
		waitForPageLoaded();
		Assert.assertTrue(
				sustainabilityPage.getTextOfWEPSustainabilityPage("sustainabilityHeader")
				.equals(sustainabilityPage.getTextLanguage(LanguageCode, "daas_ui", "sustainability.label.title")),"sustainability title breadcrub doesn't match");
		LOGGER.info("Sustainability Header is matched");
		ArrayList<String> serialNumberId = new ArrayList<String>(Arrays.asList(getTextLanguage(LanguageCode, "daas_ui", "applicationDetails.grid.column.deviceSn")));
		ArrayList<String> columnName = new ArrayList<String>(Arrays.asList(
				getTextLanguage(LanguageCode, "daas_ui", "sustainability.label.lastSignedUser"),
				getTextLanguage(LanguageCode, "daas_ui", "applicationDetails.grid.column.deviceSn"),
				getTextLanguage(LanguageCode, "daas_ui", "applicationDetails.grid.column.deviceName"),
				getTextLanguage(LanguageCode, "daas_ui", "applicationDetails.grid.column.deviceModel"),
				getTextLanguage(LanguageCode, "daas_ui", "sustainability.label.powerConsumption.kwh"),
				getTextLanguage(LanguageCode, "daas_ui", "sustainability.label.changeSinceLastWeek"),
				getTextLanguage(LanguageCode, "daas_ui", "sustainability.label.powerMeasurement"),
				getTextLanguage(LanguageCode, "daas_ui", "applicationDetails.grid.column.Model"),
				getTextLanguage(LanguageCode, "daas_ui", "applicationDetails.grid.column.mfgYear"),
				getTextLanguage(LanguageCode, "daas_ui", "asset_detail.hardware_memory"),
				getTextLanguage(LanguageCode, "daas_ui", "assets.table.heading.active_warranty"),
				getTextLanguage(LanguageCode, "daas_ui", "applicationDetails.filter.location"),
				getTextLanguage(LanguageCode, "daas_ui", "assets.details.location.last_seen")));
		ArrayList<String> checkboxValue = new ArrayList<String>(Arrays.asList("true", "true", "true", "true", "true", "true", "true", "true", "true", "true", "true", "true", "true"));
		verifyTableConfigurationTests(columnName, checkboxValue, serialNumberId);
	}

	/**
	 * C44748067:[WEXINT][Sustainability] Verify the Learn More link is opening the KB Article from help icon popup message
	 * C43298187:[WEXINT][Sustainability] Verify help icon popup message
	 * This test case validates the context sensitive help links on sustainability screen
	 *
	 * @throws Exception
	 */
	@Test(priority = 17, groups = { "REGRESSION_INTEGRATIONS" }, description="C43298187,C44748067")
	public final void verifyHelpIconTextOnSustainabilityPage() throws Exception {
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");		
		leftSideMenuVerification();
		WEPSustainabilityPage sustainabilityPage = new WEPSustainabilityPage(PreDefinedActions.getDriver()).getInstance();
		waitForPageLoaded();
		//		sleeper(10000);// Url takes time to load
		AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver()).getInstance();
		analyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
		waitForPageLoaded();
		sustainabilityPage.enterTextForsustainabilityPage("searchReport", "Sustainability");
		sleeper(2000);
		sustainabilityPage.waitForElementsOfSustainabilityPage("modernReports");
		sustainabilityPage.clickOnElementsOfSustainabilityPage("modernReports");
		sleeper(1000);
		sustainabilityPage.waitForElementsOfSustainabilityPage("sustainabilityLink");
		sustainabilityPage.clickOnElementsOfSustainabilityPage("sustainabilityLink");
		waitForPageLoaded();
		Assert.assertTrue(
				sustainabilityPage.getTextOfWEPSustainabilityPage("sustainabilityHeader")
				.equals(sustainabilityPage.getTextLanguage(LanguageCode, "daas_ui", "sustainability.label.title")),"sustainability title breadcrumb doesn't match");
		LOGGER.info("Sustainability Header is matched");


		sustainabilityPage.waitForElementsOfSustainabilityPage("helpTextLink");
		sustainabilityPage.clickOnElementsOfSustainabilityPage("helpTextLink");
		LOGGER.info("Clicked on Help link from sustainability tab");
		waitForPageLoaded();
		sustainabilityPage.waitForElementsOfSustainabilityPage("sustainabilityHelperHeader");
		Assert.assertTrue(
				sustainabilityPage.getTextOfWEPSustainabilityPage("sustainabilityHelperHeader")
				.equals(sustainabilityPage.getTextLanguage(LanguageCode, "daas_ui", "sustainability.power_consumption")),"Sustainability Help Header text did not match on sustainability page");
		LOGGER.info("Sustainability Help Header text is matched on sustainability page");

		if(sustainabilityPage.verifyElementsOfSustainabilityPage("sustainabilityLastUpdatedHelpText")) {
			Assert.assertTrue(
					sustainabilityPage.getTextOfWEPSustainabilityPage("sustainabilityLastUpdatedHelpText").contains("Last Updated"),"Last Updated date time on sustainability Help doesn't match");
			LOGGER.info("Last Updated date time on sustainability Help is matched");

		}else {
			LOGGER.info("Last Updated date time on sustainability Help is is not displayed");
		}

		String helpText= sustainabilityPage.getTextLanguage(LanguageCode, "daas_ui", "sustainability.dashboard.info_message")+""+sustainabilityPage.getTextLanguage(LanguageCode, "daas_ui", "sustainability.dashboard.info_message_data").trim();
		Assert.assertTrue(sustainabilityPage.getTextOfWEPSustainabilityPage("sustainabilityHelpText").equals(helpText),"Sustainability Help text info did not match on sustainability page");
		LOGGER.info("Sustainability Help text info msg is matched on sustainability page");
		Assert.assertTrue(
				sustainabilityPage.getTextOfWEPSustainabilityPage("KBArticleLink")
				.equals(sustainabilityPage.getTextLanguage(LanguageCode, "daas_ui", "welcome.learn_more")),"Sustainability learn_more link text info did not match on sustainability page");		
		sustainabilityPage.clickOnElementsOfSustainabilityPage("KBArticleLink");
		LOGGER.info("Clicked on KB  Article link from sustainability tab");
		waitForPageLoaded();
		sustainabilityPage.switchToDifferentTab();
		sleeper(4000);// Url takes time to load
		Assert.assertTrue(sustainabilityPage.getUrlOfCurrentPage().equals(ConstantURL.Sustainability_Help),"User not redirected to Online help section after clicking on learn more link from Sustainability");
		sustainabilityPage.switchBackToPreviousTab();

		// Verify Custom Dashboards and Widgets link for dashboard tab
		waitForPageLoaded();
		LOGGER.info("Redirected to Sustainability tab");
		sustainabilityPage.waitForElementsOfSustainabilityPage("helpTextLink");
		sustainabilityPage.verifyElementsOfSustainabilityPage("helpTextLink");

		LOGGER.info("Test case to verify context sensitive links on Sustainability passed successfully.");
		sustainabilityPage.waitForElementsOfSustainabilityPage("helpCloseButton");
		sustainabilityPage.clickOnElementsOfSustainabilityPage("helpCloseButton");
		sleeper(5000);
		LOGGER.info("Close button on Sustainbility help text window is clicked");
	}


	/**
	 * This method will verify the Device List page is in sorting or not on Sustainability Dashboard
	 * User Story https://hp-jira.external.hp.com/browse/WEXINT-48
	 * Test Case C44512453 : [WEXINT][Sustainability]>>Verify Sorting functionality on sustainability device list/grid page columns
	 * @throws Exception
	 */

	@Test(priority = 11, groups = { "REGRESSION_INTEGRATIONS"}, description = "C44512453")
	public final void verifySortingOfRoomNameColumnInSortingOrder() throws Exception {
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");		
		leftSideMenuVerification();
		WEPSustainabilityPage sustainabilityPage = new WEPSustainabilityPage(PreDefinedActions.getDriver()).getInstance();
		waitForPageLoaded();
		AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver()).getInstance();
		analyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
		waitForPageLoaded();
		sustainabilityPage.enterTextForsustainabilityPage("searchReport", "Sustainability");
		sleeper(2000);
		sustainabilityPage.waitForElementsOfSustainabilityPage("modernReports");
		sustainabilityPage.clickOnElementsOfSustainabilityPage("modernReports");
		sleeper(1000);
		sustainabilityPage.waitForElementsOfSustainabilityPage("sustainabilityLink");
		sustainabilityPage.clickOnElementsOfSustainabilityPage("sustainabilityLink");
		waitForPageLoaded();
		Assert.assertTrue(
				sustainabilityPage.getTextOfWEPSustainabilityPage("sustainabilityHeader")
				.equals(sustainabilityPage.getTextLanguage(LanguageCode, "daas_ui", "sustainability.label.title")),"sustainability title breadcrub doesn't match");
		LOGGER.info("Sustainability Header is matched");

		sustainabilityPage.sortingOfRoomNameoColumnInAscendingOrder("deviceNameColumn","listkey","navigationItemNext","navigationItemFrist","idKey","selectAllCheckBox");
		LOGGER.info("Device Names are in Ascending Order");

		sustainabilityPage.sortingOfRoomNameColumnInDescendingOrder("deviceNameColumn","listkey","navigationItemNext","navigationItemFrist","idKey","selectAllCheckBox");
		LOGGER.info("Device Names are in Descending Order");
	} 



	/**
	 *This method will verify the partial search functionality on  Device List page on Sustainability Dashboard
	 * User Story :https://hp-jira.external.hp.com/browse/WEXINT-46
	 * Test Case C44728773 : [WEXINT][Sustainability]>>Verify Partial Search functionality on each column of sustainability device list table
	 * @throws Exception
	 */

	@Test(priority = 1, groups = { "REGRESSION_INTEGRATIONS"}, description = "C44728773")
	public final void verifyPartialSearchFunctionalityOnSustainabilityDeviceListPage() throws Exception {
		String deviceName = "Desktop";
		String searchNoItemsIntheList = "Book";
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");	
		leftSideMenuVerification();
		WEPSustainabilityPage sustainabilityPage = new WEPSustainabilityPage(PreDefinedActions.getDriver()).getInstance();
		waitForPageLoaded();
		AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver()).getInstance();
		analyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
		waitForPageLoaded();
		sustainabilityPage.enterTextForsustainabilityPage("searchReport", "Sustainability");
		sleeper(2000);
		sustainabilityPage.waitForElementsOfSustainabilityPage("modernReports");
		sustainabilityPage.clickOnElementsOfSustainabilityPage("modernReports");
		sleeper(1000);
		sustainabilityPage.waitForElementsOfSustainabilityPage("sustainabilityLink");
		sustainabilityPage.clickOnElementsOfSustainabilityPage("sustainabilityLink");
		waitForPageLoaded();
		Assert.assertTrue(
				sustainabilityPage.getTextOfWEPSustainabilityPage("sustainabilityHeader")
				.equals(sustainabilityPage.getTextLanguage(LanguageCode, "daas_ui", "sustainability.label.title")),"sustainability title breadcrumb doesn't match");
		LOGGER.info("Sustainability Header is matched");
		if(sustainabilityPage.verifyElementsOfSustainabilityPage("clearFilterButtonOnDeviceGrid")) {
			sustainabilityPage.clickOnElementsOfSustainabilityPage("clearFilterButtonOnDeviceGrid");
		}
		sustainabilityPage.waitForElementsOfSustainabilityPage("deviceNameSearchBox");
		Assert.assertEquals(sustainabilityPage.verifySearchValueOfColumnOnDeviceList(LanguageCode,"deviceNameSearchBox",deviceName,"noItemsAvailable","listkey"),true);
		LOGGER.info("Search Results are showing as expected");
		sleeper(2000);
		Assert.assertEquals(sustainabilityPage.verifySearchValueOfColumnOnDeviceList(LanguageCode,"deviceNameSearchBox",searchNoItemsIntheList,"noItemsAvailable","listkey"),true);
		LOGGER.info("No Results Message is showing as expected");

	} 

	/**
	 * Verify Global filter functionality on Sustainability Page for Company User.
	 * 
	 * @throws Exception
	 */
	@Test(priority = 13, groups = { "REGRESSION_INTEGRATIONS" }, description="C44604082")
	public final void VerifyGlobalFilterOnSustainabilityPage() throws Exception {
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");	
		leftSideMenuVerification();
		WEPSustainabilityPage sustainabilityPage = new WEPSustainabilityPage(PreDefinedActions.getDriver()).getInstance();
		waitForPageLoaded();
		AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver()).getInstance();
		analyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
		waitForPageLoaded();
		sleeper(4000);// Url takes time to load
		sustainabilityPage.enterTextForsustainabilityPage("searchReport", "Sustainability");
		sleeper(2000);
		sustainabilityPage.waitForElementsOfSustainabilityPage("modernReports");
		sustainabilityPage.clickOnElementsOfSustainabilityPage("modernReports");
		sleeper(1000);
		sustainabilityPage.waitForElementsOfSustainabilityPage("sustainabilityLink");
		sustainabilityPage.clickOnElementsOfSustainabilityPage("sustainabilityLink");
		waitForPageLoaded();
		LOGGER.info("Verifying global filter functionality");
		sleeper(2000);// Url takes time to load
		Assert.assertTrue(sustainabilityPage.verifyElementsOfSustainabilityPage("globalFilter"), "Global  filter is not available on dashboard page");
		sustainabilityPage.clickOnElementsOfSustainabilityPage("globalFilter");
		waitForPageLoaded();
		sustainabilityPage.clickOnElementsOfSustainabilityPage("clearAllGlobalFilter");

		waitForPageLoaded();
		sleeper(2000);
		sustainabilityPage.actionClickOfSustainabilityPage("timeFilterDropdown");
		sustainabilityPage.clickOnElementsOfSustainabilityPage("timeFilterDropdownValue");
		sleeper(2000);

		Assert.assertTrue(sustainabilityPage.verifyElementsOfSustainabilityPage("deviceModelFilterDropdown"), "Device Model Filter Dropdown  is not available on dashboard page");
		sustainabilityPage.actionClickOfSustainabilityPage("deviceModelFilterDropdown");
		if(sustainabilityPage.verifyElementIsEnableOnSustainabilityPage("deviceModelFilterDropdownValue")) {
			sustainabilityPage.clickOnElementsOfSustainabilityPage("deviceModelFilterDropdownValue");
			sustainabilityPage.doubleClickOfSustainabilityPage("deviceModelHeader");
			LOGGER.info("Device Model Filter Dropdown  is available on the page");
			sleeper(2000);

			Assert.assertTrue(sustainabilityPage.verifyElementsOfSustainabilityPage("mfgYearFilterDropdown"), "Manufacturer year Filter Dropdown  is not available on dashboard page");
			sustainabilityPage.actionClickOfSustainabilityPage("mfgYearFilterDropdown");
			sustainabilityPage.clickOnElementsOfSustainabilityPage("mfgYearFilterDropdownValue");
			sustainabilityPage.doubleClickOfSustainabilityPage("mfgYearHeader");
			LOGGER.info(" MfgYear Filter Dropdown  is available on the page");
			sleeper(2000);


			Assert.assertTrue(sustainabilityPage.verifyElementsOfSustainabilityPage("deviceManufacturerFilterDropdown"), "Manufacturer Filter Dropdown  is not available on dashboard page");
			sustainabilityPage.actionClickOfSustainabilityPage("deviceManufacturerFilterDropdown");
			sustainabilityPage.clickOnElementsOfSustainabilityPage("deviceManufacturerFilterDropdownValue");
			sustainabilityPage.doubleClickOfSustainabilityPage("deviceManufacturerHeader");
			LOGGER.info("Device Manufacturer Filter Dropdown  is available on the page");
			sleeper(2000);


			Assert.assertTrue(sustainabilityPage.verifyElementsOfSustainabilityPage("memoryFilterDropdown"), "Memory Filter Dropdown  is not available on dashboard page");
			sustainabilityPage.actionClickOfSustainabilityPage("memoryFilterDropdown");
			sustainabilityPage.clickOnElementsOfSustainabilityPage("memoryFilterDropdownValue");
			sustainabilityPage.doubleClickOfSustainabilityPage("memoryHeader");
			LOGGER.info("Device Manufacturer Filter Dropdown  is available on the page");
			sleeper(2000);


			Assert.assertTrue(sustainabilityPage.verifyElementsOfSustainabilityPage("powerMeasurementFilterDropdown"), "powerMeasurementDropdownValue  is not available on dashboard page");
			sustainabilityPage.actionClickOfSustainabilityPage("powerMeasurementFilterDropdown");
			sustainabilityPage.clickOnElementsOfSustainabilityPage("powerMeasurementDropdownValue");
			sustainabilityPage.doubleClickOfSustainabilityPage("powerMeasurementHeader");
			LOGGER.info("Power Measurement Dropdown Value  is available on the page");
			sleeper(2000);

			sustainabilityPage.clickOnElementsOfSustainabilityPage("globalFilterSave");
			LOGGER.info("Global Filter Values Saved successfully.");
			waitForPageLoaded();

			Assert.assertTrue(sustainabilityPage.getTextOfWEPSustainabilityPage("filterCount").equals("6"), "filterCount  is not matched on sustainability page");
			LOGGER.info("Filter Count is matching with appliedfilter");

			LOGGER.info("Clearing the above applied global filters");
			sleeper(2000);// Url takes time to load
			Assert.assertTrue(sustainabilityPage.verifyElementsOfSustainabilityPage("globalFilter"), "Global  filter is not available on dashboard page");
			sustainabilityPage.clickOnElementsOfSustainabilityPage("globalFilter");
			waitForPageLoaded();
			sustainabilityPage.clickOnElementsOfSustainabilityPage("clearAllGlobalFilter");
			sustainabilityPage.clickOnElementsOfSustainabilityPage("globalFilterSave");
			LOGGER.info("Global Filter Values Saved successfully.");
			waitForPageLoaded();

		}else {
			LOGGER.info("No values are showing on dropdwons because of No data  so just applying the Time filter on Global Filter Values Saving.");
			waitForPageLoaded();

			sustainabilityPage.clickOnElementsOfSustainabilityPage("globalFilterSave");
			LOGGER.info("Global Filter Values Saved successfully.");
			waitForPageLoaded();
			Assert.assertTrue(sustainabilityPage.getTextOfWEPSustainabilityPage("filterCount").equals("1"), "filterCount  is not matched on sustainability page");
			LOGGER.info("Filter Count is matching with appliedfilter");
		}


		if(sustainabilityPage.verifyElementIsEnableOnSustainabilityPage("noDataDisplay")) {
			Assert.assertTrue(sustainabilityPage.getTextOfWEPSustainabilityPage("noDataDisplay").equals(sustainabilityPage.getTextLanguage(LanguageCode, "daas_ui", "global.no_data_to_display"))," no_data_to_display title doesn't match");
			LOGGER.info("No data to display is matching");

			Assert.assertTrue(sustainabilityPage.getTextOfWEPSustainabilityPage("noResultMsg").equals(sustainabilityPage.getTextLanguage(LanguageCode, "daas_ui", "global.no_results"))," No Result Msg  doesn't match");
			LOGGER.info("No Results Msg to display is matching");
		}else {

			Assert.assertEquals(sustainabilityPage.verifySearchValueOfColumnOnDeviceList(LanguageCode,"deviceNameSearchBox","Desktop","noItemsAvailable","listkey"),true);
			LOGGER.info("Device list is showing some data after applying the graph filters");
			sustainabilityPage.verifyElementIsEnableOnSustainabilityPage("deviceListTblrow1");
			LOGGER.info("Device list is showing some data after applying the global filters");

		}

		LOGGER.info("Verified Global filter functionality successfully.");


	}

	/**
	 * WEPINT-52 >>Add Service check from backend E.g. PI, pro plan and Verify the Sustainability option should be visible on Fleet Management Analytics tab
	 * https://hp-jira.external.hp.com/browse/WEXINT-52
	 * This Test CaseID:C44748073:[WEXINT][Sustainability] Verify the Info message strip section the Sustainability page is asper the figma
	 * @throws Exception
	 */
	@Test(priority = 14, groups = { "REGRESSION_INTEGRATIONS" }, description="C44748073")
	public final void verifyWEPSustainabilityPageMessageStripHeader() throws Exception {	
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		leftSideMenuVerification();
		WEPSustainabilityPage sustainabilityPage = new WEPSustainabilityPage(PreDefinedActions.getDriver()).getInstance();
		waitForPageLoaded();
		AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver()).getInstance();
		analyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
		waitForPageLoaded();
		sustainabilityPage.enterTextForsustainabilityPage("searchReport", "Sustainability");
		sleeper(2000);
		sustainabilityPage.waitForElementsOfSustainabilityPage("modernReports");
		sustainabilityPage.clickOnElementsOfSustainabilityPage("modernReports");
		sleeper(1000);
		sustainabilityPage.waitForElementsOfSustainabilityPage("sustainabilityLink");
		sustainabilityPage.clickOnElementsOfSustainabilityPage("sustainabilityLink");
		waitForPageLoaded();
		Assert.assertTrue(
				sustainabilityPage.getTextOfWEPSustainabilityPage("sustainabilityHeader")
				.equals(sustainabilityPage.getTextLanguage(LanguageCode, "daas_ui", "sustainability.label.title")),"sustainability title breadcrub doesn't match");
		LOGGER.info("Sustainability Header is matched");
		Assert.assertTrue(
				sustainabilityPage.getTextOfWEPSustainabilityPage("sustainabilityInfoMsgHeader")
				.equals(sustainabilityPage.getTextLanguage(LanguageCode, "daas_ui", "sustainability.powerconsumption.info_header")),"sustainability info msg doesn't match");
		LOGGER.info("Sustainability Info Message Header is matched");
		Assert.assertTrue(
				sustainabilityPage.getTextOfWEPSustainabilityPage("sustainabilityInfoMsgSubHeader")
				.equals(sustainabilityPage.getTextLanguage(LanguageCode, "daas_ui", "sustainability.powerconsumption.info_subheader")),"sustainability info msg sub header doesn't match");
		LOGGER.info("Sustainability Info Message Sub Header is matched");
		Assert.assertTrue(
				sustainabilityPage.getTextOfWEPSustainabilityPage("LearnmoreLink")
				.equals(sustainabilityPage.getTextLanguage(LanguageCode, "daas_ui", "global.learn.more")),"Sustainability learn_more link text info did not match on sustainability page");		
		sustainabilityPage.clickOnElementsOfSustainabilityPage("LearnmoreLink");
		LOGGER.info("Clicked on Learn more link from sustainability tab Info Msg Strip");
		waitForPageLoaded();
		sustainabilityPage.switchToDifferentTab();
		sleeper(5000);
		Assert.assertTrue(sustainabilityPage.getUrlOfCurrentPage().equals(ConstantURL.Sustainability_Help),
				"User not redirected to Online help section after clicking on learn more link from Sustainability");
		sustainabilityPage.switchBackToPreviousTab();

		// Verify Custom Dashboards and Widgets link for dashboard tab
		waitForPageLoaded();
		LOGGER.info("Redirected to Sustainability tab");


	}

	/**
	 * WEXINT-103, WEXINT-269 >>Verify Multi Filter functionality on Sustainabilty page Graphs
	 * https://hp-jira.external.hp.com/browse/WEXINT-103, WEXINT-269
	 * This Test CaseID:C44604082[WEXINT][Sustainability] Verify Multi Filter functionality on Sustainabilty page Graphs
	 * @throws Exception
	 */
	@Test(priority = 15, groups = { "REGRESSION_INTEGRATIONS" }, description="C44748073")
	public final void verifyGrpahFilterFunctionality() throws Exception {	
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		leftSideMenuVerification();
		sleeper(4000);// Url takes time to load
		WEPSustainabilityPage sustainabilityPage = new WEPSustainabilityPage(PreDefinedActions.getDriver()).getInstance();
		waitForPageLoaded();
		AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver()).getInstance();
		analyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
		waitForPageLoaded();
		sleeper(8000);// Url takes time to load
		sustainabilityPage.enterTextForsustainabilityPage("searchReport", "Sustainability");
		sleeper(2000);
		sustainabilityPage.waitForElementsOfSustainabilityPage("modernReports");
		sustainabilityPage.clickOnElementsOfSustainabilityPage("modernReports");
		sleeper(1000);
		sustainabilityPage.waitForElementsOfSustainabilityPage("sustainabilityLink");
		sustainabilityPage.clickOnElementsOfSustainabilityPage("sustainabilityLink");
		waitForPageLoaded();
		Assert.assertTrue(
				sustainabilityPage.getTextOfWEPSustainabilityPage("sustainabilityHeader")
				.equals(sustainabilityPage.getTextLanguage(LanguageCode, "daas_ui", "sustainability.label.title")),"sustainability title breadcrub doesn't match");
		LOGGER.info("Sustainability Header is matched");

		if(sustainabilityPage.verifyElementsOfSustainabilityPage("firstDeviceModelName")) {
			sustainabilityPage.clickOnElementsOfSustainabilityPage("firstDeviceModelName");
			LOGGER.info("Clicked on First Device model name on the Power Consumption by model graph");
			waitForPageLoaded();

			sustainabilityPage.verifyElementsOfSustainabilityPage("deviceModelGraphFilter");
			LOGGER.info("DeviceModel Graph Filter is present on Sustainability tab");

			sustainabilityPage.waitForElementsOfSustainabilityPage("deviceNameSearchBox");
			String deviceName = "Desktop";


			if(sustainabilityPage.verifyElementIsEnableOnSustainabilityPage("noDataDisplay")) {
				Assert.assertTrue(sustainabilityPage.getTextOfWEPSustainabilityPage("noResultMsg").equals(sustainabilityPage.getTextLanguage(LanguageCode, "daas_ui", "global.no_results"))," No Result Msg  doesn't match");
				LOGGER.info("No Results Msg to display is matching");
			}else {
				Assert.assertEquals(sustainabilityPage.verifySearchValueOfColumnOnDeviceList(LanguageCode,"deviceNameSearchBox",deviceName,"noItemsAvailable","listkey"),true);
				LOGGER.info("Device list is showing some data after applying the graph filters");

			}
			sustainabilityPage.verifyElementsOfSustainabilityPage("deviceModelGraphFilter");
			sustainabilityPage.actionClickOfSustainabilityPage("deviceModelGraphFilter");
			LOGGER.info("Clicked on First Device model name on the Power Consumption by model graph again to clear the filter");
			waitForPageLoaded();


		}else {
			LOGGER.info("Power Consumption by model graph is empty");
		}

	}

	/**
	 * WEXINT-205 >>[Sustainability] >>Average Power Consumption by Location graph tooltip is not showing the total device count
	 * https://hp-jira.external.hp.com/browse/WEXINT-205
	 * This Test CaseID:C44603875>>[WEXINT][Sustainability] Verify Tooltips functionality on all Sustainability grpahs like Power consumption by Model,Over Time and Location
	 * @throws Exception
	 */
	@Test(priority = 16, groups = { "REGRESSION_INTEGRATIONS" }, description="C44603875")
	public final void verifyTooltipsonEachGrpah() throws Exception {
		WEPSustainabilityPage sustainabilityPage = new WEPSustainabilityPage(PreDefinedActions.getDriver()).getInstance();
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		leftSideMenuVerification();
		sleeper(4000);// Url takes time to load
		waitForPageLoaded();
		AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver()).getInstance();
		analyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
		waitForPageLoaded();
		sleeper(10000);// Url takes time to load
		sustainabilityPage.enterTextForsustainabilityPage("searchReport", "Sustainability");
		sleeper(2000);
		sustainabilityPage.waitForElementsOfSustainabilityPage("modernReports");
		sustainabilityPage.clickOnElementsOfSustainabilityPage("modernReports");
		sleeper(1000);
		sustainabilityPage.waitForElementsOfSustainabilityPage("sustainabilityLink");
		sustainabilityPage.clickOnElementsOfSustainabilityPage("sustainabilityLink");
		waitForPageLoaded();
		Assert.assertTrue(
				sustainabilityPage.getTextOfWEPSustainabilityPage("sustainabilityHeader")
				.equals(sustainabilityPage.getTextLanguage(LanguageCode, "daas_ui", "sustainability.label.title")),"sustainability title breadcrub doesn't match");
		LOGGER.info("Sustainability Header is matched");

		if(sustainabilityPage.verifyElementsOfSustainabilityPage("firstDeviceModelName")) {
			sleeper(2000);// Url takes time to load
			sustainabilityPage.mouseHoverOfSustainabilityPage("tooltipHoverDevice");
			//			Assert.assertTrue(
			//					sustainabilityPage.getTextOfWEPSustainabilityPage("tooltipTextDeviceByType")
			//					.equals(sustainabilityPage.getTextLanguage(LanguageCode, "daas_ui", "sustainability.totalDevices.graph.tooltip")),"tooltip on sustainability page doesn't match");
			//			String tooltipTextDeviceByType= sustainabilityPage.getTextLanguage(LanguageCode, "daas_ui", "sustainability.totalDevices.graph.tooltip");
			sustainabilityPage.verifyTooltipTextOnPowerConsumptionGraph("tooltipHoverDeviceByType","tooltipHoverDevice","tooltipTextDeviceByType");
			LOGGER.info("Tooltip on the Power Consumption by model graph is verified");

		}else {
			LOGGER.info("Power Consumption by model graph is empty");
		}


	}

	@Test(priority = 10, groups = { "REGRESSION_INTEGRATIONS","REGRESSION_INTEGRATIONQA_CUJ"}, description="Testcase ID: C44250968",enabled=false)
	public final void verifyheaderofPowerConsumptionWidgetOnFleetManagementDashboard() throws Exception {	
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		leftSideMenuVerification();
		WEPSustainabilityPage sustainabilityPage = new WEPSustainabilityPage(PreDefinedActions.getDriver()).getInstance();
		waitForPageLoaded();

		SoftAssert softAssert = new SoftAssert();
		leftSideMenuVerification();
		sustainabilityPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT,CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DASHBOARD);
		waitForPageLoaded();
		sleeper(3000);


		Assert.assertTrue(
				sustainabilityPage.getTextOfWEPSustainabilityPage("fleetManagementSiteHeader")
				.equals(sustainabilityPage.getTextLanguage(LanguageCode, "daas_ui", "applications.fleetmanagement.breadcrumbs.title")),"FleetManagement Site Header doesn't match");
		LOGGER.info("Fleet Management Site Header is matched");

		waitForPageLoaded();
		Assert.assertTrue(
				sustainabilityPage.getTextOfWEPSustainabilityPage("powerConsumptionWidgetTitle")
				.equals(sustainabilityPage.getTextLanguage(LanguageCode, "daas_ui", "sustainability.powerconsumption.header")),"powerConsumptionWidgetTitle  doesn't match");
		LOGGER.info("PowerConsumption Widget Title Header is matched");
	}


	/**
	 *Verify View details link functionality on Power Consumption widget on Fleet Management Dashboard
	 * @throws Exception
	 */

	@Test(priority = 18, groups = { "PRODUCTION_LDK" })
	public final void verifyViewDetailsLinkFunctionalityOnPowerConsumptionWidgetLDKProd() throws Exception {	
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		String testSuiteName = SetTestEnvironments.suiteName;
		if(testSuiteName.contains("LDK") || testSuiteName.contains("Initech")){
			switchUserBasedOnSuite(testSuiteName);
		}
		leftSideMenuVerification();
		AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver()).getInstance();
		analyticsPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
		WEPSustainabilityPage sustainabilityPage = new WEPSustainabilityPage(PreDefinedActions.getDriver()).getInstance();
		waitForPageLoaded();
		if (!toggleVerification(VyoptaVariables.WEX_NAVIGATION_IMPVROVEMENTS_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
			waitForPageLoaded();
			sustainabilityPage.clickOnElementsOfSustainabilityPage("fleetManagement");
			Assert.assertTrue(
					sustainabilityPage.getTextOfWEPSustainabilityPage("fleetManagementSiteHeader")
					.equals(sustainabilityPage.getTextLanguage(LanguageCode, "daas_ui", "applications.fleetmanagement.breadcrumbs.title")),"FleetManagement Site Header doesn't match");
			LOGGER.info("Fleet Management Site Header is matched");
			waitForPageLoaded();
			Assert.assertTrue(
					sustainabilityPage.getTextOfWEPSustainabilityPage("powerConsumptionWidgetTitle")
					.equals(sustainabilityPage.getTextLanguage(LanguageCode, "daas_ui", "sustainability.powerconsumption.header")),"powerConsumptionWidgetTitle  doesn't match");
			LOGGER.info("PowerConsumption Widget Title Header is matched");

			if(sustainabilityPage.verifyElementsOfSustainabilityPage("powerConsumptionWidgeViewReportLink1")) {
				sustainabilityPage.waitForElementsOfSustainabilityPage("powerConsumptionWidgeViewReportLink1");
				Assert.assertTrue(
						sustainabilityPage.getTextOfWEPSustainabilityPage("powerConsumptionWidgeViewReportLink1")
						.equals(sustainabilityPage.getTextLanguage(LanguageCode, "daas_ui", "digital.exp.score.recommended.reports_view_report")),"powerConsumptionWidgeViewdReportLink  doesn't match");
				LOGGER.info("View reports link on PowerConsumption Widget is matched");

				sustainabilityPage.clickOnElementsOfSustainabilityPage("powerConsumptionWidgeViewReportLink1");
				waitForPageLoaded();
				Assert.assertTrue(
						sustainabilityPage.getTextOfWEPSustainabilityPage("sustainabilityHeader")
						.equals(sustainabilityPage.getTextLanguage(LanguageCode, "daas_ui", "sustainability.label.title")),"sustainability title breadcrub doesn't match");
				LOGGER.info("User is redirected to Sustainability Page and Header is matched");
			} else {
				LOGGER.info("View details link is not there on PowerConsumption Widget");

			}

		}else
		{
			sustainabilityPage.clickOnElementsOfSustainabilityPage("fleetAnalytics");
			waitForPageLoaded();

			sustainabilityPage.clickOnElementsOfSustainabilityPage("fleetManagementWidget");
			Assert.assertTrue(
					sustainabilityPage.getTextOfWEPSustainabilityPage("fleetManagementSiteHeader")
					.equals(sustainabilityPage.getTextLanguage(LanguageCode, "daas_ui", "applications.fleetmanagement.breadcrumbs.title")),"FleetManagement Site Header doesn't match");
			LOGGER.info("Fleet Management Site Header is matched");

			waitForPageLoaded();
			Assert.assertTrue(
					sustainabilityPage.getTextOfWEPSustainabilityPage("powerConsumptionWidgetTitleOnFMDB")
					.equalsIgnoreCase(sustainabilityPage.getTextLanguage(LanguageCode, "daas_ui", "sustainability.powerconsumption.header")),"powerConsumptionWidgetTitle  doesn't match");
			LOGGER.info("PowerConsumption Widget Title Header is matched");
			sustainabilityPage.waitForElementsOfSustainabilityPage("powerConsumptionWidgeViewdetailsLink1");


			if(sustainabilityPage.verifyElementsOfSustainabilityPage("powerConsumptionWidgeViewReportLink1")) {
				sustainabilityPage.waitForElementsOfSustainabilityPage("powerConsumptionWidgeViewReportLink1");
				Assert.assertTrue(
						sustainabilityPage.getTextOfWEPSustainabilityPage("powerConsumptionWidgeViewReportLink1")
						.equals(sustainabilityPage.getTextLanguage(LanguageCode, "daas_ui", "digital.exp.score.recommended.reports_view_report")),"powerConsumptionWidgeViewdReportLink  doesn't match");
				LOGGER.info("View reports link on PowerConsumption Widget is matched");

				sustainabilityPage.clickOnElementsOfSustainabilityPage("powerConsumptionWidgeViewReportLink1");
				waitForPageLoaded();
				Assert.assertTrue(
						sustainabilityPage.getTextOfWEPSustainabilityPage("sustainabilityHeader")
						.equals(sustainabilityPage.getTextLanguage(LanguageCode, "daas_ui", "sustainability.label.title")),"sustainability title breadcrub doesn't match");
				LOGGER.info("User is redirected to Sustainability Page and Header is matched");
			} else {
				LOGGER.info("View details link is not there on PowerConsumption Widget");

			}
		}
	}

}