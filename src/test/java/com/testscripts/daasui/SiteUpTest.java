package com.testscripts.daasui;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.basesource.action.PreDefinedActions;
import com.basesource.utils.MSTeam;
import com.daasui.constants.CommonVariables;
import com.daasui.constants.ConstantPath;
import com.daasui.constants.DashboardVariables;
import com.daasui.pages.CompaniesPage;
import com.daasui.pages.DashboardPage;
import com.daasui.pages.DeviceListPage;
import com.daasui.pages.IncidentPage;
import com.daasui.pages.LogPage;
import com.daasui.pages.PartnerPage;
import com.daasui.pages.ProductCatalogPage;
import com.daasui.pages.AnalyticsPage;
import com.daasui.pages.SalesTeamPage;
import com.daasui.pages.SettingsPage;
import com.daasui.pages.SupportTeamPage;
import com.daasui.pages.UserPage;
import com.daasui.pages.WhatsNewPage;
import com.google.common.base.Strings;
import com.opencsv.CSVWriter;

public class SiteUpTest extends CommonTest {
	private static Logger LOGGER = LogManager.getLogger(SiteUpTest.class);
	public static boolean siteUpExecution = false;
	public static boolean siteUpRegressionPassOrNot = true;
	public static String buildNumber = System.getProperty("buildNumber");

	@Test(groups = { "REGRESSION_CI", "SMOKE_CI", "ALL_CI", "PRODUCTION_CI", "STABLE_CI", "PRODUCTION_SANITY" })
	public final void siteUpTest() throws Exception {
		long dashboardStart,companyStart,supportTeamStart,incidentStart,
		 deviceStart,userStart,reportsStart,
		 logsStart,settingsStart;
		String dashboardTotalTime = null;
		String companyTotalTime = null;
		String supportTeamTotalTime = null;
		String incidentsTotalTime = null;
		String devicesTotalTime = null;
		String usersTotalTime = null;
		String reportsTotalTime = null;
		String logsTotalTime = null;
		String settingsTotalTime = null;
		
		try {
			siteUpRunOrNot = true;
			/**
			 * Using sleep for temporary. Once UI gets stable , we will be using Explicit wait
			 * 
			 */
			environment = System.getProperty("environment");
			startTime = generateDate();
			CommonTest commonTest = new CommonTest();
			commonTest = commonTest.getInstance();
			commonTest.login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
			changeLanguage("ENGLISH");
            
			//start timer for dashboard tab
			dashboardStart = System.currentTimeMillis();
			LOGGER.info("Time at the start of dashboard page loading - " + dashboardStart + " " + "ms");
			
			DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
			getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(CommonVariables.IMPLICITWAIT));
			//sleeper(5000);

			if(!toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, "MSP_ADMIN_US_EMAIl"))){
			dashboardPage.waitForElementsOfDashboardPage("dashboardWrapperSiteUp");
			dashboardTotalTime = calculateLoadTimeForSiteUp(dashboardStart);
			LOGGER.info("Total Time taken for Dashboard page to load - " + dashboardTotalTime + " " + "sec");	
			Assert.assertTrue(dashboardPage.getTextOfDashboardPage("dashboardTitle").equals(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "pagetitle.dashboard")), "Dashboard title text is incorrect");
			LOGGER.info("Dashboard title is matched");

			commonTest.gotoCompaniesTab();
			
			//start timer for companies tab
			companyStart = System.currentTimeMillis();
			LOGGER.info("Time at the start of company page loading - " + companyStart + " " + "ms");

			CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
			getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(CommonVariables.IMPLICITWAIT));
			companiesPage.waitForElementsOfCompaniesPage("tableOverlaySiteUp");		
			//sleeper(5000);			
			companyTotalTime = calculateLoadTimeForSiteUp(companyStart);
			LOGGER.info("Total Time taken for company page to load - " + companyTotalTime + " " + "sec");
			Assert.assertTrue(companiesPage.getTextOfCompaniesPage("companiesTitle").equals(companiesPage.getTextLanguage(LanguageCode, "daas_ui", "pagetitle.companies")), "Companies page title did not match");
			LOGGER.info("Companies title is matched");
			
			commonTest.gotoSupportTeamTab();

			//start timer for support team tab
			supportTeamStart = System.currentTimeMillis();
			LOGGER.info("Time at the start of support team page loading - " + supportTeamStart + " " + "ms");
			SupportTeamPage supportTeamPage = new SupportTeamPage(PreDefinedActions.getDriver()).getInstance();
			getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(CommonVariables.IMPLICITWAIT));
			supportTeamPage.waitForElementsOfSupportTeamPage("tableOverlaySiteUp");
			//sleeper(5000);
			supportTeamTotalTime = calculateLoadTimeForSiteUp(supportTeamStart);
			LOGGER.info("Total Time taken for support team page to load - " + supportTeamTotalTime + " " + "sec");
			Assert.assertTrue(supportTeamPage.getTextOfSupportTeamPage("supportTeamTitle").equals(supportTeamPage.getTextLanguage(LanguageCode, "daas_ui", "pagetitle.supportTeam")), "Support team title did not match");

			LOGGER.info("SupportTeam title is matched");

			commonTest.gotoIncidentTab();
			
			//start timer for incident team tab
			incidentStart = System.currentTimeMillis();
			LOGGER.info("Time at the start of incident page loading - " + incidentStart + " " + "ms");
			
			IncidentPage incidentPage = new IncidentPage(PreDefinedActions.getDriver()).getInstance();
			getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(CommonVariables.IMPLICITWAIT));
			//sleeper(5000);
			incidentPage.waitForElementsOfIncidentPage("tableOverlaySiteUp");
			incidentsTotalTime = calculateLoadTimeForSiteUp(incidentStart);
			LOGGER.info("Total Time taken for incident page to load - " + incidentsTotalTime + " " + "sec");
			Assert.assertTrue(incidentPage.getTextOfIncidentPage("incidentTitle").equals(getTextLanguage(LanguageCode, "daas_ui", "incidents.label")), "Incident title text on incident list page is incorrect");
			LOGGER.info("Incidents title is matched");

			commonTest.gotoDevicesTab();

			//start timer for devices team tab
			deviceStart = System.currentTimeMillis();
			LOGGER.info("Time at the start of device page loading - " + deviceStart + " " + "ms");
			
			DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
			getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(CommonVariables.IMPLICITWAIT));
			//sleeper(5000);
			deviceListPage.waitForElementsOfDeviceListPage("tableOverlaySiteUp");
			devicesTotalTime = calculateLoadTimeForSiteUp(deviceStart);
			LOGGER.info("Total Time taken for device page to load - " + devicesTotalTime + " " + "sec");
			Assert.assertTrue(deviceListPage.getTextOfDeviceListPage("devicesTitle").equals(deviceListPage.getTextLanguage(LanguageCode, "daas_ui", "pagetitle.devices")), "Devices title text is incorrect");
			LOGGER.info("Devices title is matched");

			commonTest.gotoUserTab();

			//start timer for users team tab
			userStart = System.currentTimeMillis();
			LOGGER.info("Time at the start of user page loading - " + userStart + " " + "ms");
			
			UserPage userPage = new UserPage(PreDefinedActions.getDriver()).getInstance();
			getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(CommonVariables.IMPLICITWAIT));
			//sleeper(5000);
			userPage.waitForElementsOfUserPage("tableOverlaySiteUp");
			usersTotalTime = calculateLoadTimeForSiteUp(userStart);
			LOGGER.info("Total Time taken for user page to load - " + usersTotalTime + " " + "sec");
			Assert.assertTrue(userPage.getTextOfUserPage("userTitle").equals(userPage.getTextLanguage(LanguageCode, "daas_ui", "pagetitle.users")), "User title doesn't match");

			LOGGER.info("Users title is matched");

			commonTest.gotoReportTab();

			//start timer for reports team tab
			reportsStart = System.currentTimeMillis();
			LOGGER.info("Time at the start of report page loading - " + reportsStart + " " + "ms");
			
			AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver()).getInstance();
			analyticsPage = analyticsPage.getInstance();
			getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(CommonVariables.IMPLICITWAIT));
			//sleeper(5000);
			analyticsPage.waitForElementsOfAnalyticsPage("customReportsCreatedList");
			reportsTotalTime = calculateLoadTimeForSiteUp(reportsStart);
			LOGGER.info("Total Time taken for reports page to load - " + reportsTotalTime + " " + "sec");
			Assert.assertTrue(analyticsPage.getTextOfAnalyticsPage("reportTitle").equals(analyticsPage.getTextLanguage(LanguageCode, "daas_ui", "pagetitle.reports")), "Report title doesn't match");

			LOGGER.info("Reports title is matched");

			commonTest.gotoLogTab();

			//start timer for log team tab
			logsStart = System.currentTimeMillis();
			LOGGER.info("Time at the start of log page loading - " + logsStart + " " + "ms");
			
			LogPage logPage = new LogPage(PreDefinedActions.getDriver()).getInstance();
			getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(CommonVariables.IMPLICITWAIT));
			//sleeper(5000);
			logPage.waitForElementsOfLogPage("tableOverlaySiteUp");
			logsTotalTime = calculateLoadTimeForSiteUp(logsStart);
			LOGGER.info("Total Time taken for logs page to load - " + logsTotalTime + " " + "sec");
			Assert.assertTrue(logPage.getTextOfLogPage("logTitle").equals(logPage.getTextLanguage(LanguageCode, "daas_ui", "pagetitle.logs")), "Logs title text is incorrect");
			LOGGER.info("Logs title is matched");

			commonTest.gotoSettingsTab();

			//start timer for settings team tab
			settingsStart = System.currentTimeMillis();
			LOGGER.info("Time at the start of settings page loading - " + settingsStart + " " + "ms");
			
			SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
			settingsPage = settingsPage.getInstance();
			getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(CommonVariables.IMPLICITWAIT));
			//sleeper(5000);
			settingsPage.waitForElementsOfSettingsPage("settingTabSiteUp");
			settingsTotalTime = calculateLoadTimeForSiteUp(settingsStart);
			LOGGER.info("Total Time taken for settings page to load - " + settingsTotalTime + " " + "sec");
			Assert.assertTrue(settingsPage.getTextOfSettingsPage("settingsTitle").equals(settingsPage.getTextLanguage(LanguageCode, "daas_ui", "settings.label")), "Settings title text is incorrect");
			LOGGER.info("Settings title is matched");
			siteUpExecution = true;
			endTime = generateDate();
			}
			else{

				dashboardPage.waitForElementsOfDashboardPage("dashboardWrapperSiteUp");
				dashboardTotalTime = calculateLoadTimeForSiteUp(dashboardStart);
				LOGGER.info("Total Time taken for Dashboard page to load - " + dashboardTotalTime + " " + "sec");
				Assert.assertTrue(dashboardPage.getTextOfDashboardPage("dashboardTitleOnBreadcrumbs").equals(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "pagetitle.dashboard")), "Dashboard title text is incorrect");
				LOGGER.info("Dashboard title is matched");

				commonTest.gotoCompaniesTab();
				
				//start timer for companies tab
				companyStart = System.currentTimeMillis();
				LOGGER.info("Time at the start of company page loading - " + companyStart + " " + "ms");

				CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
				getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(CommonVariables.IMPLICITWAIT));
				companiesPage.waitForElementsOfCompaniesPage("tableOverlaySiteUp");		
				//sleeper(5000);			
				companyTotalTime = calculateLoadTimeForSiteUp(companyStart);
				LOGGER.info("Total Time taken for company page to load - " + companyTotalTime + " " + "sec");
				Assert.assertTrue(companiesPage.getTextOfCompaniesPage("companiesTitleOnBreadcrumbs").equals(companiesPage.getTextLanguage(LanguageCode, "daas_ui", "pagetitle.companies")), "Companies page title did not match");
				LOGGER.info("Companies title is matched");
				
				commonTest.gotoSupportTeamTab();
				
				//start timer for support team tab
				supportTeamStart = System.currentTimeMillis();
				LOGGER.info("Time at the start of support team page loading - " + supportTeamStart + " " + "ms");

				SupportTeamPage supportTeamPage = new SupportTeamPage(PreDefinedActions.getDriver()).getInstance();
				getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(CommonVariables.IMPLICITWAIT));
				supportTeamPage.waitForElementsOfSupportTeamPage("tableOverlaySiteUp");
				//sleeper(5000);
				supportTeamTotalTime = calculateLoadTimeForSiteUp(supportTeamStart);
				LOGGER.info("Total Time taken for support team page to load - " + supportTeamTotalTime + " " + "sec");
				Assert.assertTrue(supportTeamPage.getTextOfSupportTeamPage("supportTeamTitleOnBreadcrumbs").equals(supportTeamPage.getTextLanguage(LanguageCode, "daas_ui", "pagetitle.supportTeam")), "Support team title did not match");
				LOGGER.info("SupportTeam title is matched");

				commonTest.gotoIncidentTab();
				
				//start timer for incident team tab
				incidentStart = System.currentTimeMillis();
				LOGGER.info("Time at the start of incident page loading - " + incidentStart + " " + "ms");

				IncidentPage incidentPage = new IncidentPage(PreDefinedActions.getDriver()).getInstance();
				getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(CommonVariables.IMPLICITWAIT));
				//sleeper(5000);
				incidentPage.waitForElementsOfIncidentPage("tableOverlaySiteUp");
				incidentsTotalTime = calculateLoadTimeForSiteUp(incidentStart);
				LOGGER.info("Total Time taken for incident page to load - " + incidentsTotalTime + " " + "sec");	
				Assert.assertTrue(incidentPage.getTextOfIncidentPage("incidentTitleOnBreadcrumbs").equals(getTextLanguage(LanguageCode, "daas_ui", "incidents.label")), "Incident title text on incident list page is incorrect");
				LOGGER.info("Incidents title is matched");

				commonTest.gotoDevicesTab();

				//start timer for devices team tab
				deviceStart = System.currentTimeMillis();
				LOGGER.info("Time at the start of device page loading - " + deviceStart + " " + "ms");
				DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
				getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(CommonVariables.IMPLICITWAIT));
				//sleeper(5000);
				deviceListPage.waitForElementsOfDeviceListPage("tableOverlaySiteUp");
				devicesTotalTime = calculateLoadTimeForSiteUp(deviceStart);
				LOGGER.info("Total Time taken for device page to load - " + devicesTotalTime + " " + "sec");
				Assert.assertTrue(deviceListPage.getTextOfDeviceListPage("devicesTitleOnBreadcrumbs").equals(deviceListPage.getTextLanguage(LanguageCode, "daas_ui", "pagetitle.devices")), "Devices title text is incorrect");
				LOGGER.info("Devices title is matched");

				commonTest.gotoUserTab();
				
				//start timer for users team tab
				userStart = System.currentTimeMillis();
				LOGGER.info("Time at the start of user page loading - " + userStart + " " + "ms");

				UserPage userPage = new UserPage(PreDefinedActions.getDriver()).getInstance();
				getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(CommonVariables.IMPLICITWAIT));
				//sleeper(5000);
				userPage.waitForElementsOfUserPage("tableOverlaySiteUp");
				usersTotalTime = calculateLoadTimeForSiteUp(userStart);
				LOGGER.info("Total Time taken for user page to load - " + usersTotalTime + " " + "sec");
				Assert.assertTrue(userPage.getTextOfUserPage("userTitleOnBreadcrumbs").equals(userPage.getTextLanguage(LanguageCode, "daas_ui", "pagetitle.users")), "User title doesn't match");
				LOGGER.info("Users title is matched");

				commonTest.gotoReportTab();

				//start timer for reports team tab
				reportsStart = System.currentTimeMillis();
				LOGGER.info("Time at the start of report page loading - " + reportsStart + " " + "ms");
				AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver()).getInstance();
				analyticsPage = analyticsPage.getInstance();
				getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(CommonVariables.IMPLICITWAIT));
				//sleeper(5000);
				analyticsPage.waitForElementsOfAnalyticsPage("customReportsCreatedList");
				reportsTotalTime = calculateLoadTimeForSiteUp(reportsStart);
				LOGGER.info("Total Time taken for reports page to load - " + reportsTotalTime + " " + "sec");
				Assert.assertTrue(analyticsPage.getTextOfAnalyticsPage("reportTitleOnBreadcrumbs").equals(analyticsPage.getTextLanguage(LanguageCode, "daas_ui", "pagetitle.reports")), "Report title doesn't match");
				LOGGER.info("Reports title is matched");

				commonTest.gotoLogTab();

				//start timer for log team tab
				logsStart = System.currentTimeMillis();
				LOGGER.info("Time at the start of log page loading - " + logsStart + " " + "ms");
				LogPage logPage = new LogPage(PreDefinedActions.getDriver()).getInstance();
				getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(CommonVariables.IMPLICITWAIT));
				//sleeper(5000);
				logPage.waitForElementsOfLogPage("tableOverlaySiteUp");
				logsTotalTime = calculateLoadTimeForSiteUp(logsStart);
				LOGGER.info("Total Time taken for logs page to load - " + logsTotalTime + " " + "sec");
				Assert.assertTrue(logPage.getTextOfLogPage("logTitleOnBreadcrumbs").equals(logPage.getTextLanguage(LanguageCode, "daas_ui", "pagetitle.logs")), "Logs title text is incorrect");
				LOGGER.info("Logs title is matched");

				commonTest.gotoSettingsTab();

				//start timer for settings team tab
				settingsStart = System.currentTimeMillis();
				LOGGER.info("Time at the start of settings page loading - " + settingsStart + " " + "ms");
				
				SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
				settingsPage = settingsPage.getInstance();
				getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(CommonVariables.IMPLICITWAIT));
				//sleeper(5000);
				settingsPage.waitForElementsOfSettingsPage("settingTabSiteUp");
				settingsTotalTime = calculateLoadTimeForSiteUp(settingsStart);
				LOGGER.info("Total Time taken for settings page to load - " + settingsTotalTime + " " + "sec");
				Assert.assertTrue(settingsPage.getTextOfSettingsPage("settingsTitleOnBreadcrumbs").equals(settingsPage.getTextLanguage(LanguageCode, "daas_ui", "settings.label")), "Settings title text is incorrect");
				LOGGER.info("Settings title is matched");
				
				siteUpExecution = true;
				endTime = generateDate();
			}
		} finally {
			if (siteUpExecution == false) {
				endTime = generateDate();
				MSTeam msteam = new MSTeam();
				msteam.sendDataToMsTeamWhenSiteUpFailed("FF0000", environment, browser, endTime);
			}
			if (((environment.equalsIgnoreCase("US-PRODUCTION") || environment.equalsIgnoreCase("EU-PRODUCTION")) && !(Strings.isNullOrEmpty(buildNumber)))) {
				String timestamp =  LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
				writeTimeforEachPagetoCSV(dashboardTotalTime, companyTotalTime, supportTeamTotalTime, incidentsTotalTime, devicesTotalTime, usersTotalTime, reportsTotalTime, logsTotalTime, settingsTotalTime, timestamp);
			}
			// TODO: handle finally clause
		}

	}

	/**
	 * This is the common site up test method for all user roles
	 * 
	 * @param LanguageCode - language code
	 */
	public final void commonSiteUp(String LanguageCode, String userName) {
		try {
			CommonTest commonTest = new CommonTest().getInstance();
			DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
			if(!toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, userName)){
			dashboardPage.waitForElementsOfDashboardPage("tableOverlay");
			Assert.assertTrue(dashboardPage.getTextOfDashboardPage("dashboardTitle").equals(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "pagetitle.dashboard")), "Dashboard title text is incorrect");
			LOGGER.info("Dashboard title is matched");

			commonTest.gotoDevicesTab();
			LOGGER.info("Redirected to devices tab");

			DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
			deviceListPage.waitForElementsOfDeviceListPage("tableOverlay");
			Assert.assertTrue(deviceListPage.getTextOfDeviceListPage("devicesTitle").equals(deviceListPage.getTextLanguage(LanguageCode, "daas_ui", "pagetitle.devices")), "Devices title text is incorrect");
			LOGGER.info("Devices title is matched");

			commonTest.gotoReportTab();
			LOGGER.info("Redirected to reports tab");

			AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver()).getInstance();
			Assert.assertTrue(analyticsPage.getTextOfAnalyticsPage("reportTitle").equals(analyticsPage.getTextLanguage(LanguageCode, "daas_ui", "pagetitle.reports")), "Report title doesn't match");

			LOGGER.info("Reports title is matched");

			commonTest.gotoHelpAndSupportTab();
			LOGGER.info("Redirected to help and support page");

			SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
			Assert.assertTrue(settingsPage.getTextOfSettingsPage("helpAndSupportTitleText").equals(settingsPage.getTextLanguage(LanguageCode, "daas_ui", "sidemenu.support")), "Help and support title text is incorrect");
			LOGGER.info("Help and support title is matched");
			}
			else{
				dashboardPage.waitForElementsOfDashboardPage("tableOverlay");
				Assert.assertTrue(dashboardPage.getTextOfDashboardPage("dashboardTitleOnBreadcrumbs").equals(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "pagetitle.dashboard")), "Dashboard title text is incorrect");
				LOGGER.info("Dashboard title is matched");

				commonTest.gotoDevicesTab();
				LOGGER.info("Redirected to devices tab");

				DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
				Assert.assertTrue(deviceListPage.getTextOfDeviceListPage("devicesTitleOnBreadcrumbs").equals(deviceListPage.getTextLanguage(LanguageCode, "daas_ui", "pagetitle.devices")), "Devices title text is incorrect");
				LOGGER.info("Devices title is matched");

				commonTest.gotoReportTab();
				LOGGER.info("Redirected to reports tab");

				AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver()).getInstance();
				Assert.assertTrue(analyticsPage.getTextOfAnalyticsPage("reportTitleOnBreadcrumbs").equals(analyticsPage.getTextLanguage(LanguageCode, "daas_ui", "pagetitle.reports")), "Report title doesn't match");

				LOGGER.info("Reports title is matched");

				commonTest.gotoHelpAndSupportTab();
				LOGGER.info("Redirected to help and support page");

				SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
				Assert.assertTrue(settingsPage.getTextOfSettingsPage("helpAndSupportTitleTextOnBreadcrumbs").equals(settingsPage.getTextLanguage(LanguageCode, "daas_ui", "sidemenu.support")), "Help and support title text is incorrect");
				LOGGER.info("Help and support title is matched");
			}

			LOGGER.info("Common site up test completed");

		} catch (Exception e) {
			LOGGER.error(("Exception occured in method commonSiteUp " + e.getMessage()));
			siteUpRegressionPassOrNot= false;
		}

	}

	/**
	 * This is the method to verify if user impersonation for service now administrator and microsost telemetry administrator is successfull
	 * 
	 * @param LanguageCode - language code
	 */
	public final void snowAdministratorAndMicrosoftTelemetryAdministratorSiteUpTest(String LanguageCode, String userName) {
		try {
			CommonTest commonTest = new CommonTest().getInstance();
			SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
			
			commonTest.gotoSettingsTabOfSnowAdmin();
			settingsPage.waitForElementsOfSettingsPage("tableOverlay");
			LOGGER.info("Redirected to settings page");
			
			if(!toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, userName)){
			Assert.assertTrue(settingsPage.verifyElementsOfSettingsPage("settingTitleNonCompany"), "Settings title text is incorrect");
			LOGGER.info("Settings title is matched");

			commonTest.gotoHelpAndSupportTab();
			LOGGER.info("Redirected to help and support page");

			Assert.assertTrue(settingsPage.verifyElementsOfSettingsPage("helpAndSupportTitleText"), "Help and support title text is incorrect");
			LOGGER.info("Help and support title is matched");
			}
			else{
				Assert.assertTrue(settingsPage.verifyElementsOfSettingsPage("settingTitleNonCompanyOnBreadcrumbs"), "Settings title text is incorrect");
				LOGGER.info("Settings title is matched");

				commonTest.gotoHelpAndSupportTab();
				LOGGER.info("Redirected to help and support page");

				Assert.assertTrue(settingsPage.verifyElementsOfSettingsPage("helpAndSupportTitleTextOnBreadcrumbs"), "Help and support title text is incorrect");
				LOGGER.info("Help and support title is matched");

			}
			LOGGER.info("Site up test for snow admin/Microsoft Telemetry completed");

		} catch (Exception e) {
			LOGGER.error(("Exception occured in method snowAdminAndMSTSiteUpTest " + e.getMessage()));
		}
	}

	/**
	 * This is the method to verify if user impersonation for it administrator is successfull
	 * 
	 * @param LanguageCode - language code
	 */
	public final void itAdministratorSiteUpTest(String LanguageCode, String userName) {
		try {
			CommonTest commonTest = new CommonTest().getInstance();
			DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();

			if(!toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, userName)){
			gotoDashboardTab();
			Assert.assertTrue(dashboardPage.getTextOfDashboardPage("dashboardTitle").equals(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "pagetitle.dashboard")), "Dashboard title text is incorrect");
			LOGGER.info("Dashboard title is matched");

			commonTest.gotoDevicesCompanyUserTab();;
			LOGGER.info("Redirected to devices tab");

			DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
			Assert.assertTrue(deviceListPage.getTextOfDeviceListPage("devicesTitle").equals(deviceListPage.getTextLanguage(LanguageCode, "daas_ui", "pagetitle.devices")), "Devices title text is incorrect");
			LOGGER.info("Devices title is matched");

			commonTest.gotoReportCompanyUserTab();;
			LOGGER.info("Redirected to reports tab");

			AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver()).getInstance();
			Assert.assertTrue(analyticsPage.getTextOfAnalyticsPage("reportTitle").equals(analyticsPage.getTextLanguage(LanguageCode, "daas_ui", "pagetitle.reports")), "Report title doesn't match");

			LOGGER.info("Reports title is matched");

			commonTest.gotoHelpAndSupportTab();
			LOGGER.info("Redirected to help and support page");

			SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
			Assert.assertTrue(settingsPage.getTextOfSettingsPage("helpAndSupportTitleText").equals(settingsPage.getTextLanguage(LanguageCode, "daas_ui", "sidemenu.support")), "Help and support title text is incorrect");
			LOGGER.info("Help and support title is matched");
		
			commonTest.gotoIncidentCompanyUserTab();
			LOGGER.info("Redirected to incidents page");

			IncidentPage incidentPage = new IncidentPage(PreDefinedActions.getDriver()).getInstance();
			Assert.assertTrue(incidentPage.getTextOfIncidentPage("incidentTitle").equals(getTextLanguage(LanguageCode, "daas_ui", "incidents.label")), "Incident title text on incident list page is incorrect");
			LOGGER.info("Incidents title is matched");

			commonTest.gotoUserCompanyUserTab();;
			LOGGER.info("Redirected to user page");

			UserPage userPage = new UserPage(PreDefinedActions.getDriver()).getInstance();
			Assert.assertTrue(userPage.getTextOfUserPage("userTitle").equals(userPage.getTextLanguage(LanguageCode, "daas_ui", "pagetitle.users")), "User title doesn't match");

			LOGGER.info("Users title is matched");
			commonTest.gotoLogCompanyUserTab();;
			LOGGER.info("Redirected to logs page");

			LogPage logPage = new LogPage(PreDefinedActions.getDriver()).getInstance();
			Assert.assertTrue(logPage.getTextOfLogPage("logTitle").equals(logPage.getTextLanguage(LanguageCode, "daas_ui", "pagetitle.logs")), "Logs title text is incorrect");
			LOGGER.info("Logs title is matched");

			commonTest.gotoNonMSPSettingsCompanyUserTab();;
			LOGGER.info("Redirected to settings page");

			Assert.assertTrue(settingsPage.verifyElementsOfSettingsPage("settingTitleNonCompany"), "Settings title text is incorrect");
			LOGGER.info("Settings title is matched");
			}
			else{
				gotoDashboardTab();
				dashboardPage.waitForElementsOfDashboardPage("tableOverlay");
				Assert.assertTrue(dashboardPage.getTextOfDashboardPage("dashboardTitleOnBreadcrumbs").equals(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "pagetitle.dashboard")), "Dashboard title text is incorrect");
				LOGGER.info("Dashboard title is matched");

				commonTest.gotoDevicesCompanyUserTab();;
				LOGGER.info("Redirected to devices tab");

				DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
				Assert.assertTrue(deviceListPage.getTextOfDeviceListPage("devicesTitleOnBreadcrumbs").equals(deviceListPage.getTextLanguage(LanguageCode, "daas_ui", "pagetitle.devices")), "Devices title text is incorrect");
				LOGGER.info("Devices title is matched");

				commonTest.gotoReportCompanyUserTab();;
				LOGGER.info("Redirected to reports tab");

				AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver()).getInstance();
				Assert.assertTrue(analyticsPage.getTextOfAnalyticsPage("reportTitleOnBreadcrumbs").equals(analyticsPage.getTextLanguage(LanguageCode, "daas_ui", "pagetitle.reports")), "Report title doesn't match");

				LOGGER.info("Reports title is matched");

				commonTest.gotoHelpAndSupportTab();
				LOGGER.info("Redirected to help and support page");

				SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
				Assert.assertTrue(settingsPage.getTextOfSettingsPage("helpAndSupportTitleTextOnBreadcrumbs").equals(settingsPage.getTextLanguage(LanguageCode, "daas_ui", "sidemenu.support")), "Help and support title text is incorrect");
				LOGGER.info("Help and support title is matched");
			
				commonTest.gotoIncidentCompanyUserTab();
				LOGGER.info("Redirected to incidents page");

				IncidentPage incidentPage = new IncidentPage(PreDefinedActions.getDriver()).getInstance();
				Assert.assertTrue(incidentPage.getTextOfIncidentPage("incidentTitleOnBreadcrumbs").equals(getTextLanguage(LanguageCode, "daas_ui", "incidents.label")), "Incident title text on incident list page is incorrect");
				LOGGER.info("Incidents title is matched");

				commonTest.gotoUserCompanyUserTab();;
				LOGGER.info("Redirected to user page");
				sleeper(3000);
				UserPage userPage = new UserPage(PreDefinedActions.getDriver()).getInstance();
				Assert.assertTrue(userPage.getTextOfUserPage("userTitleOnBreadcrumbs").equals(userPage.getTextLanguage(LanguageCode, "daas_ui", "pagetitle.users")), "User title doesn't match");

				LOGGER.info("Users title is matched");
				commonTest.gotoLogCompanyUserTab();;
				LOGGER.info("Redirected to logs page");

				LogPage logPage = new LogPage(PreDefinedActions.getDriver()).getInstance();
				Assert.assertTrue(logPage.getTextOfLogPage("logTitleOnBreadcrumbs").equals(logPage.getTextLanguage(LanguageCode, "daas_ui", "pagetitle.logs")), "Logs title text is incorrect");
				LOGGER.info("Logs title is matched");

				commonTest.gotoNonMSPSettingsCompanyUserTab();;
				LOGGER.info("Redirected to settings page");

				Assert.assertTrue(settingsPage.verifyElementsOfSettingsPage("settingTitleNonCompanyOnBreadcrumbs"), "Settings title text is incorrect");
				LOGGER.info("Settings title is matched");
			}
			LOGGER.info("Site up test for IT admin completed");

		} catch (Exception e) {
			LOGGER.error(("Exception occured in method itAdminSiteUpTest " + e.getMessage()));
			siteUpRegressionPassOrNot= false;
		}
	}

	/**
	 * This is the method to verify if user impersonation for report administrator is successfull
	 * 
	 * @param LanguageCode - language code
	 */
	public final void reportAdministratorSiteUpTest(String LanguageCode, String userName) {
		try {
			CommonTest commonTest = new CommonTest().getInstance();
			commonTest.gotoIncidentTab();
			LOGGER.info("Redirected to incidents page");

			IncidentPage incidentPage = new IncidentPage(PreDefinedActions.getDriver()).getInstance();

			if(!toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, userName)){
			Assert.assertTrue(incidentPage.getTextOfIncidentPage("incidentTitle").equals(getTextLanguage(LanguageCode, "daas_ui", "incidents.label")), "Incident title text on incident list page is incorrect");
			LOGGER.info("Incidents title is matched");

			commonTest.gotoNonMSPSettingsTab();
			LOGGER.info("Redirected to settings page");

			SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
			Assert.assertTrue(settingsPage.verifyElementsOfSettingsPage("settingTitleNonCompany"), "Settings title text is incorrect");
			LOGGER.info("Settings title is matched");
			}
			else{
				Assert.assertTrue(incidentPage.getTextOfIncidentPage("incidentTitleOnBreadcrumbs").equals(getTextLanguage(LanguageCode, "daas_ui", "incidents.label")), "Incident title text on incident list page is incorrect");
				LOGGER.info("Incidents title is matched");

				commonTest.gotoNonMSPSettingsTab();
				LOGGER.info("Redirected to settings page");

				SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
				Assert.assertTrue(settingsPage.verifyElementsOfSettingsPage("settingTitleNonCompanyOnBreadcrumbs"), "Settings title text is incorrect");
				LOGGER.info("Settings title is matched");

			}
			LOGGER.info("Site up test for report admin completed");

		} catch (Exception e) {
			LOGGER.error(("Exception occured in method reportAdminSiteUpTest " + e.getMessage()));
		}

	}

	/**
	 * This is the method to verify if user impersonation for sales specialist user is successfull
	 * 
	 * @param LanguageCode - language code
	 */
	public final void salesSpecialistSiteUpTest(String LanguageCode, String userName) {
		try {
			CommonTest commonTest = new CommonTest().getInstance();
			commonTest.gotoCompaniesTab();
			LOGGER.info("Redirected to companies page");

			CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
			if(!toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, userName)){
			companiesPage.waitForElementsOfCompaniesPage("tableOverlay");
			Assert.assertTrue(companiesPage.getTextOfCompaniesPage("companiesTitle").equals(companiesPage.getTextLanguage(LanguageCode, "daas_ui", "pagetitle.companies")), "Companies page title did not match");
			LOGGER.info("Companies title is matched");
			}
			else{
				companiesPage.waitForElementsOfCompaniesPage("tableOverlay");
				Assert.assertTrue(companiesPage.getTextOfCompaniesPage("companiesTitleOnBreadcrumbs").equals(companiesPage.getTextLanguage(LanguageCode, "daas_ui", "pagetitle.companies")), "Companies page title did not match");
				LOGGER.info("Companies title is matched");		
			}
			LOGGER.info("Site up test for sales specialist completed");

		} catch (Exception e) {
			LOGGER.error(("Exception occured in method salesSpecialistSiteUpTest " + e.getMessage()));
			siteUpRegressionPassOrNot= false;
		}

	}

	/**
	 * This is the method to verify if user impersonation for partner administrator is successfull
	 * 
	 * @param LanguageCode - language code
	 */
	public final void partnerAdministratorSiteUpTest(String LanguageCode, String userName) {
		try {
			CommonTest commonTest = new CommonTest().getInstance();
			commonTest.gotoCompaniesTab();
			LOGGER.info("Redirected to companies page");

			CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
			if(!toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, userName)){
			companiesPage.waitForElementsOfCompaniesPage("tableOverlay");
			Assert.assertTrue(companiesPage.getTextOfCompaniesPage("companiesTitle").equals(companiesPage.getTextLanguage(LanguageCode, "daas_ui", "pagetitle.companies")), "Companies page title did not match");
			LOGGER.info("Companies title is matched");

			commonTest.gotoPartnerTeamTab();
			LOGGER.info("Redirected to partner team page");

			SalesTeamPage salesTeamPage = new SalesTeamPage(PreDefinedActions.getDriver()).getInstance();
			Assert.assertTrue(salesTeamPage.matchTextOfSalesTeamPage("salesTeamTitle", getTextLanguage(LanguageCode, "daas_ui", "pagetitle.partnerTeam")), "Sales team title doesn't match");

			LOGGER.info("Sales team title is matched");

			commonTest.gotoIncidentTab();
			LOGGER.info("Redirected to incidents page");

			IncidentPage incidentPage = new IncidentPage(PreDefinedActions.getDriver()).getInstance();
			Assert.assertTrue(incidentPage.getTextOfIncidentPage("incidentTitle").equals(getTextLanguage(LanguageCode, "daas_ui", "incidents.label")), "Incident title text on incident list page is incorrect");
			LOGGER.info("Incidents title is matched");

			commonTest.gotoUserTab();
			LOGGER.info("Redirected to users page");

			UserPage userPage = new UserPage(PreDefinedActions.getDriver()).getInstance();
			Assert.assertTrue(userPage.getTextOfUserPage("userTitle").equals(userPage.getTextLanguage(LanguageCode, "daas_ui", "pagetitle.users")), "User title doesn't match");
			LOGGER.info("Users title is matched");

			commonTest.gotoLogTab();
			LOGGER.info("Redirected to logs page");

			LogPage logPage = new LogPage(PreDefinedActions.getDriver()).getInstance();
			Assert.assertTrue(logPage.getTextOfLogPage("logTitle").equals(logPage.getTextLanguage(LanguageCode, "daas_ui", "pagetitle.logs")), "Logs title text is incorrect");
			LOGGER.info("Logs title is matched");

			commonTest.gotoSettingsTab();
			LOGGER.info("Redirected to settings page");

			SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
			Assert.assertTrue(settingsPage.getTextOfSettingsPage("settingsTitle").equals(settingsPage.getTextLanguage(LanguageCode, "daas_ui", "settings.label")), "Settings title text is incorrect");
			LOGGER.info("Settings title is matched");

			commonTest.gotoProductCatalogTab();
			LOGGER.info("Redirected to product catalog page");

			ProductCatalogPage productCatalogPage = new ProductCatalogPage(PreDefinedActions.getDriver()).getInstance();
			Assert.assertTrue(productCatalogPage.getTextOfProductCatalogPage("productCatalogTitle").equals(productCatalogPage.getTextLanguage(LanguageCode, "daas_ui", "catalogs.label")), "Product Catalog title text is incorrect");
			LOGGER.info("Product Catalog title is matched");
			}
			else{
				companiesPage.waitForElementsOfCompaniesPage("tableOverlay");
				Assert.assertTrue(companiesPage.getTextOfCompaniesPage("companiesTitleOnBreadcrumbs").equals(companiesPage.getTextLanguage(LanguageCode, "daas_ui", "pagetitle.companies")), "Companies page title did not match");
				LOGGER.info("Companies title is matched");

				commonTest.gotoPartnerTeamTab();
				LOGGER.info("Redirected to partner team page");

				SalesTeamPage salesTeamPage = new SalesTeamPage(PreDefinedActions.getDriver()).getInstance();
				Assert.assertTrue(salesTeamPage.matchTextOfSalesTeamPage("salesTeamTitleOnBreadcrumbs", getTextLanguage(LanguageCode, "daas_ui", "pagetitle.partnerTeam")), "Sales team title doesn't match");

				LOGGER.info("Sales team title is matched");

				commonTest.gotoIncidentTab();
				LOGGER.info("Redirected to incidents page");

				IncidentPage incidentPage = new IncidentPage(PreDefinedActions.getDriver()).getInstance();
				Assert.assertTrue(incidentPage.getTextOfIncidentPage("incidentTitleOnBreadcrumbs").equals(getTextLanguage(LanguageCode, "daas_ui", "incidents.label")), "Incident title text on incident list page is incorrect");
				LOGGER.info("Incidents title is matched");

				commonTest.gotoUserTab();
				LOGGER.info("Redirected to users page");

				UserPage userPage = new UserPage(PreDefinedActions.getDriver()).getInstance();
				Assert.assertTrue(userPage.getTextOfUserPage("userTitleOnBreadcrumbs").equals(userPage.getTextLanguage(LanguageCode, "daas_ui", "pagetitle.users")), "User title doesn't match");
				LOGGER.info("Users title is matched");

				commonTest.gotoLogTab();
				LOGGER.info("Redirected to logs page");

				LogPage logPage = new LogPage(PreDefinedActions.getDriver()).getInstance();
				Assert.assertTrue(logPage.getTextOfLogPage("logTitleOnBreadcrumbs").equals(logPage.getTextLanguage(LanguageCode, "daas_ui", "pagetitle.logs")), "Logs title text is incorrect");
				LOGGER.info("Logs title is matched");

				commonTest.gotoSettingsTab();
				LOGGER.info("Redirected to settings page");

				SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
				Assert.assertTrue(settingsPage.getTextOfSettingsPage("settingsTitleOnBreadcrumbs").equals(settingsPage.getTextLanguage(LanguageCode, "daas_ui", "settings.label")), "Settings title text is incorrect");
				LOGGER.info("Settings title is matched");

				commonTest.gotoProductCatalogTab();
				LOGGER.info("Redirected to product catalog page");

				ProductCatalogPage productCatalogPage = new ProductCatalogPage(PreDefinedActions.getDriver()).getInstance();
				Assert.assertTrue(productCatalogPage.getTextOfProductCatalogPage("productCatalogTitleOnBreadcrumbs").equals(productCatalogPage.getTextLanguage(LanguageCode, "daas_ui", "catalogs.label")), "Product Catalog title text is incorrect");
				LOGGER.info("Product Catalog title is matched");
			}
			LOGGER.info("Site up test for partner administrator completed");

		} catch (Exception e) {
			LOGGER.error(("Exception occured in method partnerAdministratorSiteUpTest " + e.getMessage()));
			siteUpRegressionPassOrNot= false;
		}

	}

	/**
	 * This is the method to verify if user impersonation for support specialist user is successfull
	 * 
	 * @param LanguageCode - language code
	 */
	public final void supportSpecialistSiteUpTest(String LanguageCode, String userName) {
		try {
			CommonTest commonTest = new CommonTest().getInstance();
			commonTest.gotoCompaniesTab();
			LOGGER.info("Redirected to companies page");

			CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
			if(!toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, userName)){
			companiesPage.waitForElementsOfCompaniesPage("tableOverlay");
			Assert.assertTrue(companiesPage.getTextOfCompaniesPage("companiesTitle").equals(companiesPage.getTextLanguage(LanguageCode, "daas_ui", "pagetitle.companies")), "Companies page title did not match");
			LOGGER.info("Companies title is matched");

			commonTest.gotoIncidentTab();
			LOGGER.info("Redirected to incidents page");

			IncidentPage incidentPage = new IncidentPage(PreDefinedActions.getDriver()).getInstance();
			Assert.assertTrue(incidentPage.getTextOfIncidentPage("incidentTitle").equals(getTextLanguage(LanguageCode, "daas_ui", "incidents.label")), "Incident title text on incident list page is incorrect");
			LOGGER.info("Incidents title is matched");

			commonTest.gotoUserTab();
			LOGGER.info("Redirected to users page");

			UserPage userPage = new UserPage(PreDefinedActions.getDriver()).getInstance();
			Assert.assertTrue(userPage.getTextOfUserPage("userTitle").equals(userPage.getTextLanguage(LanguageCode, "daas_ui", "pagetitle.users")), "User title doesn't match");

			LOGGER.info("Users title is matched");

			commonTest.gotoLogTab();
			LOGGER.info("Redirected to logs page");

			LogPage logPage = new LogPage(PreDefinedActions.getDriver()).getInstance();
			Assert.assertTrue(logPage.getTextOfLogPage("logTitle").equals(logPage.getTextLanguage(LanguageCode, "daas_ui", "pagetitle.logs")), "Logs title text is incorrect");
			LOGGER.info("Logs title is matched");

			commonTest.gotoSettingsTab();
			LOGGER.info("Redirected to settings page");

			SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
			Assert.assertTrue(settingsPage.getTextOfSettingsPage("settingsTitle").equals(settingsPage.getTextLanguage(LanguageCode, "daas_ui", "settings.label")), "Settings title text is incorrect");
			LOGGER.info("Settings title is matched");
			}
			else{
				Assert.assertTrue(companiesPage.getTextOfCompaniesPage("companiesTitleOnBreadcrumbs").equals(companiesPage.getTextLanguage(LanguageCode, "daas_ui", "pagetitle.companies")), "Companies page title did not match");
				LOGGER.info("Companies title is matched");

				commonTest.gotoIncidentTab();
				LOGGER.info("Redirected to incidents page");

				IncidentPage incidentPage = new IncidentPage(PreDefinedActions.getDriver()).getInstance();
				Assert.assertTrue(incidentPage.getTextOfIncidentPage("incidentTitleOnBreadcrumbs").equals(getTextLanguage(LanguageCode, "daas_ui", "incidents.label")), "Incident title text on incident list page is incorrect");
				LOGGER.info("Incidents title is matched");

				commonTest.gotoUserTab();
				LOGGER.info("Redirected to users page");

				UserPage userPage = new UserPage(PreDefinedActions.getDriver()).getInstance();
				Assert.assertTrue(userPage.getTextOfUserPage("userTitleOnBreadcrumbs").equals(userPage.getTextLanguage(LanguageCode, "daas_ui", "pagetitle.users")), "User title doesn't match");

				LOGGER.info("Users title is matched");

				commonTest.gotoLogTab();
				LOGGER.info("Redirected to logs page");

				LogPage logPage = new LogPage(PreDefinedActions.getDriver()).getInstance();
				Assert.assertTrue(logPage.getTextOfLogPage("logTitleOnBreadcrumbs").equals(logPage.getTextLanguage(LanguageCode, "daas_ui", "pagetitle.logs")), "Logs title text is incorrect");
				LOGGER.info("Logs title is matched");

				commonTest.gotoSettingsTab();
				LOGGER.info("Redirected to settings page");

				SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
				Assert.assertTrue(settingsPage.getTextOfSettingsPage("settingsTitleOnBreadcrumbs").equals(settingsPage.getTextLanguage(LanguageCode, "daas_ui", "settings.label")), "Settings title text is incorrect");
				LOGGER.info("Settings title is matched");
			}
			LOGGER.info("Site up test for support specialist completed");

		} catch (Exception e) {
			LOGGER.error(("Exception occured in method supportSpecialistSiteUpTest " + e.getMessage()));
			siteUpRegressionPassOrNot= false;
		}
	}

	/**
	 * This is the method to verify if user impersonation for managed service provider is successfull
	 * 
	 * @param LanguageCode - language code
	 */
	public final void managedServiceProviderSiteUpTest(String LanguageCode, String userName) {
		try {
			CommonTest commonTest = new CommonTest().getInstance();
			DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
			getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(CommonVariables.IMPLICITWAIT));
			if(!toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, userName)){
			dashboardPage.waitForElementsOfDashboardPage("tableOverlay");
			Assert.assertTrue(dashboardPage.getTextOfDashboardPage("dashboardTitle").equals(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "pagetitle.dashboard")), "Dashboard title text is incorrect");
			LOGGER.info("Dashboard title is matched");

			commonTest.gotoCompaniesTab();
			LOGGER.info("Redirected to companies page");

			CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
			Assert.assertTrue(companiesPage.getTextOfCompaniesPage("companiesTitle").equals(companiesPage.getTextLanguage(LanguageCode, "daas_ui", "pagetitle.companies")), "Companies page title did not match");
			LOGGER.info("Companies title is matched");

			commonTest.gotoSupportTeamTab();
			LOGGER.info("Redirected to support team page");
			SupportTeamPage supportTeamPage = new SupportTeamPage(PreDefinedActions.getDriver()).getInstance();
			Assert.assertTrue(supportTeamPage.getTextOfSupportTeamPage("supportTeamTitle").equals(supportTeamPage.getTextLanguage(LanguageCode, "daas_ui", "pagetitle.supportTeam")), "Support team title did not match");

			LOGGER.info("SupportTeam title is matched");

			commonTest.gotoIncidentTab();
			LOGGER.info("Redirected to incidents page");

			IncidentPage incidentPage = new IncidentPage(PreDefinedActions.getDriver()).getInstance();
			Assert.assertTrue(incidentPage.getTextOfIncidentPage("incidentTitle").equals(getTextLanguage(LanguageCode, "daas_ui", "incidents.label")), "Incident title text on incident list page is incorrect");
			LOGGER.info("Incidents title is matched");

			commonTest.gotoDevicesTab();
			LOGGER.info("Redirected to devices page");

			DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
			Assert.assertTrue(deviceListPage.getTextOfDeviceListPage("devicesTitle").equals(deviceListPage.getTextLanguage(LanguageCode, "daas_ui", "pagetitle.devices")), "Devices title text is incorrect");

			LOGGER.info("Devices title is matched");

			commonTest.gotoUserTab();
			LOGGER.info("Redirected to users page");

			UserPage userPage = new UserPage(PreDefinedActions.getDriver()).getInstance();
			Assert.assertTrue(userPage.getTextOfUserPage("userTitle").equals(userPage.getTextLanguage(LanguageCode, "daas_ui", "pagetitle.users")), "User title doesn't match");

			LOGGER.info("Users title is matched");

			commonTest.gotoReportTab();
			LOGGER.info("Redirected to reports page");

			AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver()).getInstance();
			Assert.assertTrue(analyticsPage.getTextOfAnalyticsPage("reportTitle").equals(analyticsPage.getTextLanguage(LanguageCode, "daas_ui", "pagetitle.reports")), "Report title doesn't match");

			LOGGER.info("Reports title is matched");

			commonTest.gotoLogTab();
			LOGGER.info("Redirected to logs page");

			LogPage logPage = new LogPage(PreDefinedActions.getDriver()).getInstance();
			Assert.assertTrue(logPage.getTextOfLogPage("logTitle").equals(logPage.getTextLanguage(LanguageCode, "daas_ui", "pagetitle.logs")), "Logs title text is incorrect");

			LOGGER.info("Logs title is matched");

			commonTest.gotoSettingsTab();
			LOGGER.info("Redirected to settings page");

			SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
			Assert.assertTrue(settingsPage.getTextOfSettingsPage("settingsTitle").equals(settingsPage.getTextLanguage(LanguageCode, "daas_ui", "settings.label")), "Settings title text is incorrect");

			LOGGER.info("Settings title is matched");

			commonTest.gotoHelpAndSupportTab();
			LOGGER.info("Redirected to help and support page");

			getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(CommonVariables.IMPLICITWAIT));
			Assert.assertTrue(settingsPage.getTextOfSettingsPage("helpAndSupportTitleText").equals(settingsPage.getTextLanguage(LanguageCode, "daas_ui", "sidemenu.support")), "Help and support title text is incorrect");

			LOGGER.info("Help and Support title is matched");
			}
			else{
				dashboardPage.waitForElementsOfDashboardPage("tableOverlay");
				Assert.assertTrue(dashboardPage.getTextOfDashboardPage("dashboardTitleOnBreadcrumbs").equals(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "pagetitle.dashboard")), "Dashboard title text is incorrect");
				LOGGER.info("Dashboard title is matched");

				commonTest.gotoCompaniesTab();
				LOGGER.info("Redirected to companies page");

				CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
				Assert.assertTrue(companiesPage.getTextOfCompaniesPage("companiesTitleOnBreadcrumbs").equals(companiesPage.getTextLanguage(LanguageCode, "daas_ui", "pagetitle.companies")), "Companies page title did not match");
				LOGGER.info("Companies title is matched");

				commonTest.gotoSupportTeamTab();
				LOGGER.info("Redirected to support team page");
				sleeper(3000);
				SupportTeamPage supportTeamPage = new SupportTeamPage(PreDefinedActions.getDriver()).getInstance();
				Assert.assertTrue(supportTeamPage.getTextOfSupportTeamPage("supportTeamTitleOnBreadcrumbs").equals(supportTeamPage.getTextLanguage(LanguageCode, "daas_ui", "pagetitle.supportTeam")), "Support team title did not match");

				LOGGER.info("SupportTeam title is matched");

				commonTest.gotoIncidentTab();
				LOGGER.info("Redirected to incidents page");

				IncidentPage incidentPage = new IncidentPage(PreDefinedActions.getDriver()).getInstance();
				Assert.assertTrue(incidentPage.getTextOfIncidentPage("incidentTitleOnBreadcrumbs").equals(getTextLanguage(LanguageCode, "daas_ui", "incidents.label")), "Incident title text on incident list page is incorrect");
				LOGGER.info("Incidents title is matched");

				commonTest.gotoDevicesTab();
				LOGGER.info("Redirected to devices page");

				DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
				Assert.assertTrue(deviceListPage.getTextOfDeviceListPage("devicesTitleOnBreadcrumbs").equals(deviceListPage.getTextLanguage(LanguageCode, "daas_ui", "pagetitle.devices")), "Devices title text is incorrect");

				LOGGER.info("Devices title is matched");

				commonTest.gotoUserTab();
				LOGGER.info("Redirected to users page");
				sleeper(3000);
				UserPage userPage = new UserPage(PreDefinedActions.getDriver()).getInstance();
				Assert.assertTrue(userPage.getTextOfUserPage("userTitleOnBreadcrumbs").equals(userPage.getTextLanguage(LanguageCode, "daas_ui", "pagetitle.users")), "User title doesn't match");

				LOGGER.info("Users title is matched");

				commonTest.gotoReportTab();
				LOGGER.info("Redirected to reports page");

				AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver()).getInstance();
				Assert.assertTrue(analyticsPage.getTextOfAnalyticsPage("reportTitleOnBreadcrumbs").equals(analyticsPage.getTextLanguage(LanguageCode, "daas_ui", "pagetitle.reports")), "Report title doesn't match");

				LOGGER.info("Reports title is matched");

				commonTest.gotoLogTab();
				LOGGER.info("Redirected to logs page");

				LogPage logPage = new LogPage(PreDefinedActions.getDriver()).getInstance();
				Assert.assertTrue(logPage.getTextOfLogPage("logTitleOnBreadcrumbs").equals(logPage.getTextLanguage(LanguageCode, "daas_ui", "pagetitle.logs")), "Logs title text is incorrect");

				LOGGER.info("Logs title is matched");

				commonTest.gotoSettingsTab();
				LOGGER.info("Redirected to settings page");

				SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
				settingsPage = settingsPage.getInstance();
				Assert.assertTrue(settingsPage.getTextOfSettingsPage("settingsTitleOnBreadcrumbs").equals(settingsPage.getTextLanguage(LanguageCode, "daas_ui", "settings.label")), "Settings title text is incorrect");

				LOGGER.info("Settings title is matched");

				commonTest.gotoHelpAndSupportTab();
				LOGGER.info("Redirected to help and support page");

				getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(CommonVariables.IMPLICITWAIT));
				Assert.assertTrue(settingsPage.getTextOfSettingsPage("helpAndSupportTitleTextOnBreadcrumbs").equals(settingsPage.getTextLanguage(LanguageCode, "daas_ui", "sidemenu.support")), "Help and support title text is incorrect");

				LOGGER.info("Help and Support title is matched");

			}
			LOGGER.info("Site up test for MSP completed");

		} catch (Exception e) {
			LOGGER.error(("Exception occured in method mspSiteUpTest " + e.getMessage()));
		}
	}
	
	/**
	 * This is the method to verify if MSP administrator is successfull
	 * 
	 * @param LanguageCode - language code
	 */
	public final void mspAdministratorSiteUpTest(String LanguageCode) {
		try {	
			CommonTest commonTest = new CommonTest();
			commonTest = commonTest.getInstance();
			commonTest.login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
			
			commonTest.gotoIncidentTab();
			LOGGER.info("Redirected to incidents page");

			IncidentPage incidentPage = new IncidentPage(PreDefinedActions.getDriver()).getInstance();
			getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(CommonVariables.IMPLICITWAIT));
			sleeper(5000);
			if(!toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, "MSP_ADMIN_US_EMAIl"))){
			incidentPage.waitForElementsOfIncidentPage("tableOverlay");
			Assert.assertTrue(incidentPage.getTextOfIncidentPage("incidentTitle").equals(getTextLanguage(LanguageCode, "daas_ui", "incidents.label")), "Incident title text on incident list page is incorrect");
			LOGGER.info("Incidents title is matched");

			commonTest.gotoUserTab();
			LOGGER.info("Redirected to user page");

			UserPage userPage = new UserPage(PreDefinedActions.getDriver()).getInstance();
			getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(CommonVariables.IMPLICITWAIT));
			sleeper(5000);
			userPage.waitForElementsOfUserPage("tableOverlay");
			Assert.assertTrue(userPage.getTextOfUserPage("userTitle").equals(userPage.getTextLanguage(LanguageCode, "daas_ui", "pagetitle.users")), "User title doesn't match");
			LOGGER.info("Users title is matched");

			commonTest.gotoLogTab();
			LOGGER.info("Redirected to logs page");

			LogPage logPage = new LogPage(PreDefinedActions.getDriver()).getInstance();
			getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(CommonVariables.IMPLICITWAIT));
			sleeper(5000);
			logPage.waitForElementsOfLogPage("tableOverlay");
			Assert.assertTrue(logPage.getTextOfLogPage("logTitle").equals(logPage.getTextLanguage(LanguageCode, "daas_ui", "pagetitle.logs")), "Logs title text is incorrect");
			LOGGER.info("Logs title is matched");

			commonTest.gotoNonMSPSettingsTab();
			LOGGER.info("Redirected to settings page");

			SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
			settingsPage = settingsPage.getInstance();
			getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(CommonVariables.IMPLICITWAIT));
			sleeper(5000);
			settingsPage.waitForElementsOfSettingsPage("tableOverlay");
			settingsPage.waitForElementsOfSettingsPageForinvisibile("tableOverlay");
			Assert.assertTrue(settingsPage.verifyElementsOfSettingsPage("settingsTitle"), "Settings title text is incorrect");
			LOGGER.info("Settings title is matched");
			}
			else{
				incidentPage.waitForElementsOfIncidentPage("tableOverlay");
				Assert.assertTrue(incidentPage.getTextOfIncidentPage("incidentTitleOnBreadcrumbs").equals(getTextLanguage(LanguageCode, "daas_ui", "incidents.label")), "Incident title text on incident list page is incorrect");
				LOGGER.info("Incidents title is matched");

				commonTest.gotoUserTab();
				LOGGER.info("Redirected to user page");

				UserPage userPage = new UserPage(PreDefinedActions.getDriver()).getInstance();
				getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(CommonVariables.IMPLICITWAIT));
				sleeper(5000);
				userPage.waitForElementsOfUserPage("tableOverlay");
				Assert.assertTrue(userPage.getTextOfUserPage("userTitleOnBreadcrumbs").equals(userPage.getTextLanguage(LanguageCode, "daas_ui", "pagetitle.users")), "User title doesn't match");
				LOGGER.info("Users title is matched");

				commonTest.gotoLogTab();
				LOGGER.info("Redirected to logs page");

				LogPage logPage = new LogPage(PreDefinedActions.getDriver()).getInstance();
				getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(CommonVariables.IMPLICITWAIT));
				sleeper(5000);
				logPage.waitForElementsOfLogPage("tableOverlay");
				Assert.assertTrue(logPage.getTextOfLogPage("logTitleOnBreadcrumbs").equals(logPage.getTextLanguage(LanguageCode, "daas_ui", "pagetitle.logs")), "Logs title text is incorrect");
				LOGGER.info("Logs title is matched");

				commonTest.gotoNonMSPSettingsTab();
				LOGGER.info("Redirected to settings page");

				SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
				settingsPage = settingsPage.getInstance();
				getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(CommonVariables.IMPLICITWAIT));
				sleeper(5000);
				settingsPage.waitForElementsOfSettingsPage("tableOverlay");
				settingsPage.waitForElementsOfSettingsPageForinvisibile("tableOverlay");
				Assert.assertTrue(settingsPage.verifyElementsOfSettingsPage("settingsTitleOnBreadcrumbs"), "Settings title text is incorrect");
				LOGGER.info("Settings title is matched");

			}
			LOGGER.info("Site up test for msp admin completed");

		} catch (Exception e) {
			LOGGER.error(("Exception occured in method mspAdministratorSiteUpTest " + e.getMessage()));
		}
	}
		
	/**
	 * This is the method to verify if IT administrator is successfull
	 * 
	 * @param LanguageCode - language code
	 */
	
	@Test(priority = 0,groups = { "REGRESSION_CI", "SMOKE_CI", "ALL_CI"})
	public void verifyITADministratorSiteUpTest() throws Exception {
			SoftAssert softAssert = new SoftAssert();
			login("IT_ADMIN_USER_US", "IT_ADMIN_USER_PASSWORD");
			itAdministratorSiteUpTest(LanguageCode, getCredentials(environment, "IT_ADMIN_USER_US"));
			softAssert.assertTrue(siteUpRegressionPassOrNot == true,"Site Test for IT Admin got failed.");
			siteUpRegressionPassOrNot = true;
			softAssert.assertAll();
			LOGGER.info("Site up test for IT Admin is successful");	
	}
	
	/**
	 * This is the method to verify if Support Specialist is successfull
	 * 
	 * @param LanguageCode - language code
	 */
	
	@Test(priority = 1,groups = { "REGRESSION_CI", "SMOKE_CI", "ALL_CI"})
	public void verifySupportSpecialistSiteUpTest() throws Exception {
			SoftAssert softAssert = new SoftAssert();
			login("MSP_SPECIALIST_NEW_UI_Companies_EMAIL_COMPANIES", "MSP_SPECIALIST_NEW_UI_Companies_PASSWORD_COMPANIES");
			commonSiteUp(LanguageCode, getCredentials(environment,"MSP_SPECIALIST_NEW_UI_Companies_EMAIL_COMPANIES"));
			supportSpecialistSiteUpTest(LanguageCode, getCredentials(environment,"MSP_SPECIALIST_NEW_UI_Companies_EMAIL_COMPANIES"));
			softAssert.assertTrue(siteUpRegressionPassOrNot == true,"Site Test for Support Specialist got failed.");
			siteUpRegressionPassOrNot = true;
			softAssert.assertAll();
			LOGGER.info("Site up test for Support Specialist is successful");

	}
	
	/**
	 * This is the method to verify if Partner administrator is successfull
	 * 
	 * @param LanguageCode - language code
	 */
	
	@Test(priority = 2,groups = { "REGRESSION_CI", "SMOKE_CI", "ALL_CI"})
	public void verifyPartnerAdministratorSiteUpTest() throws Exception {
			SoftAssert softAssert = new SoftAssert();
			login("RESELLER_NEW_UI_Companies_EMAIL_COMPANIES", "RESELLER_NEW_UI_Companies_PASSWORD_COMPANIES");
			commonSiteUp(LanguageCode, getCredentials(environment,"RESELLER_NEW_UI_Companies_EMAIL_COMPANIES"));
			partnerAdministratorSiteUpTest(LanguageCode, getCredentials(environment,"RESELLER_NEW_UI_Companies_EMAIL_COMPANIES"));
			softAssert.assertTrue(siteUpRegressionPassOrNot == true,"Site Test for Partner Admin got failed.");
			siteUpRegressionPassOrNot = true;
			softAssert.assertAll();
			LOGGER.info("Site up test for Partner Admin is successful");
	
	}
	
	/**
	 * This is the method to verify if Sales Specialist is successfull
	 * 
	 * @param LanguageCode - language code
	 */
	
	@Test(priority = 3,groups = { "REGRESSION_CI", "SMOKE_CI", "ALL_CI"})
	public void verifySalesSpecialistSiteUpTest() throws Exception {
			SoftAssert softAssert = new SoftAssert();
			login("RESELLER_SPECIALIST_NEW_UI_Companies_EMAIL_COMPANIES", "RESELLER_SPECIALIST_NEW_UI_Companies_PASSWORD_COMPANIES");
			commonSiteUp(LanguageCode, getCredentials(environment,"RESELLER_SPECIALIST_NEW_UI_Companies_EMAIL_COMPANIES"));
			salesSpecialistSiteUpTest(LanguageCode, getCredentials(environment,"RESELLER_SPECIALIST_NEW_UI_Companies_EMAIL_COMPANIES"));
			softAssert.assertTrue(siteUpRegressionPassOrNot == true,"Site Test for Sales Specialist got failed.");
			siteUpRegressionPassOrNot = true;
			softAssert.assertAll();
			LOGGER.info("Site up test for Sales Specialist is successful");
	
	}
	
	/**
	 * This is the method to verify if Service Specialist is successfull
	 * 
	 * @param LanguageCode - language code
	 */
	
	@Test(priority = 4,groups = { "REGRESSION_CI", "SMOKE_CI", "ALL_CI"})
	public void verifyServiceSpecialistSiteUpTest() throws Exception {
			SoftAssert softAssert = new SoftAssert();
			login("SERVICE_SPECIALIST_USER_ENAIL1", "SERVICE_SPECIALIST_USER_PASSWORD1");
			commonSiteUp(LanguageCode, getCredentials(environment,"SERVICE_SPECIALIST_USER_ENAIL1"));
			supportSpecialistSiteUpTest(LanguageCode, getCredentials(environment,"SERVICE_SPECIALIST_USER_ENAIL1"));
			softAssert.assertTrue(siteUpRegressionPassOrNot == true,"Site Test for Sales Specialist got failed.");
			siteUpRegressionPassOrNot = true;
			softAssert.assertAll();
			LOGGER.info("Site up test for Service Specialist is successful");
		
	}
	
	/**
	 * This is the method to verify Root administrator site up test is successfull
	 * 
	 * @param LanguageCode - language code
	 */
	public final void rootAdministratorSiteUpTest(String LanguageCode, String userName) {
		try {
			CommonTest commonTest = new CommonTest().getInstance();
			getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(CommonVariables.IMPLICITWAIT));

			if(!toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, userName)){
			commonTest.gotoAdministratorUserTab();
			UserPage userPage = new UserPage(PreDefinedActions.getDriver()).getInstance();
			LOGGER.info("Redirected to Admin User page");
			Assert.assertTrue(userPage.verifyElementsOfUserPage("userTitle"), "Root Admin user title text is incorrect");
			LOGGER.info("Admin User is matched");
			
			commonTest.gotoAdministratorSubscriptionTab();
			LOGGER.info("Clicked on Subscription Management tab ");
			switchToDifferentTab();
			userPage.waitForElementsOfUserPage("adminPortalLabel");
			Assert.assertTrue(PreDefinedActions.getDriver().getTitle().equals(CommonVariables.ADMINPORTALTITLE), "Subscription Management window title is not matching");
			LOGGER.info("Subscription Management window title is matching ");
			Assert.assertTrue(getUrlOfCurrentPage().equals(getEnvironmentSpecificData(System.getProperty("environment"), "LHADMIN_PORTAL_URL")), "Redirection to Subscription Management window failed on clicking Subscription Management button in Admin tab");
			switchBackToPreviousTab();
			
			commonTest.gotoWhatsNewTabOfRootAdmin();
			WhatsNewPage whatsNewPage = new WhatsNewPage(PreDefinedActions.getDriver()).getInstance();
			LOGGER.info("Redirected to Whats New page");
			Assert.assertTrue(whatsNewPage.verifyElementsOfWhatsNewPage("whatsNewTitle"), "What's New title text is incorrect");
			LOGGER.info("What's New title is matched");
			
			commonTest.gotoMSPTab();
			PartnerPage partnerPage = new PartnerPage(PreDefinedActions.getDriver()).getInstance();
			LOGGER.info("Redirected to MSP page");
			Assert.assertTrue(partnerPage.verifyElementsOfPartnerPage("partnersTitle"), "MSP title text is incorrect");
			LOGGER.info("MSP title title is matched");
			
			gotoMSPUsersTab();
			LOGGER.info("Redirected to MSPs users page");
			Assert.assertTrue(userPage.verifyElementIsClickableOfUserPage("userTitle"), "MSPs Users title text is incorrect");
			LOGGER.info("MSPs Users title is matched");
		
			commonTest.gotoPartnersTab();
			LOGGER.info("Redirected to partners page");
			Assert.assertTrue(partnerPage.verifyElementsOfPartnerPage("partnersTitle"), "Partners title text is incorrect");
			LOGGER.info("Partners title is matched");
			gotoPartnerUsersTab();
			LOGGER.info("Redirected to partners users page");
			Assert.assertTrue(userPage.verifyElementIsClickableOfUserPage("userTitle"), "Partners Users title text is incorrect");
			LOGGER.info("Partners Users title is matched");
			
			commonTest.gotoRootCompaniesTab();;
			CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
			LOGGER.info("Redirected to Customer page");
			Assert.assertTrue(companiesPage.verifyElementsOfCompaniesPage("companiesTitle"), "Company title text is incorrect");
			LOGGER.info("Company title is matched");
			gotoCompaniesUsersTab();
			LOGGER.info("Redirected to Company users page");
			Assert.assertTrue(userPage.verifyElementIsClickableOfUserPage("userTitle"), "Company Users title text is incorrect");
			LOGGER.info("Company Users title is matched");
			}
			else{
				commonTest.gotoAdministratorUserTab();
				UserPage userPage = new UserPage(PreDefinedActions.getDriver()).getInstance();
				LOGGER.info("Redirected to Admin User page");
				Assert.assertTrue(userPage.verifyElementsOfUserPage("userTitleOnBreadcrumbs"), "Root Admin user title text is incorrect");
				LOGGER.info("Admin User is matched");
				
				commonTest.gotoAdministratorSubscriptionTab();
				LOGGER.info("Clicked on Subscription Management tab ");
				switchToDifferentTab();
				userPage.waitForElementsOfUserPage("adminPortalLabel");
				Assert.assertTrue(PreDefinedActions.getDriver().getTitle().equals(CommonVariables.ADMINPORTALTITLE), "Subscription Management window title is not matching");
				LOGGER.info("Subscription Management window title is matching ");
				Assert.assertTrue(getUrlOfCurrentPage().equals(getEnvironmentSpecificData(System.getProperty("environment"), "LHADMIN_PORTAL_URL")), "Redirection to Subscription Management window failed on clicking Subscription Management button in Admin tab");
				switchBackToPreviousTab();
				
				commonTest.gotoWhatsNewTabOfRootAdmin();
				WhatsNewPage whatsNewPage = new WhatsNewPage(PreDefinedActions.getDriver()).getInstance();
				LOGGER.info("Redirected to Whats New page");
				Assert.assertTrue(whatsNewPage.verifyElementsOfWhatsNewPage("whatsNewTitleOnBreadcrumbs"), "What's New title text is incorrect");
				LOGGER.info("What's New title is matched");
				
				commonTest.gotoMSPTab();
				PartnerPage partnerPage = new PartnerPage(PreDefinedActions.getDriver()).getInstance();
				LOGGER.info("Redirected to MSP page");
				Assert.assertTrue(partnerPage.verifyElementsOfPartnerPage("partnersTitleOnBreadcrumbs"), "MSP title text is incorrect");
				LOGGER.info("MSP title title is matched");
				
				gotoMSPUsersTab();
				LOGGER.info("Redirected to MSPs users page");
				Assert.assertTrue(userPage.verifyElementIsClickableOfUserPage("userTitleOnBreadcrumbs"), "MSPs Users title text is incorrect");
				LOGGER.info("MSPs Users title is matched");
			
				commonTest.gotoPartnersTab();
				LOGGER.info("Redirected to partners page");
				Assert.assertTrue(partnerPage.verifyElementsOfPartnerPage("partnersTitleOnBreadcrumbs"), "Partners title text is incorrect");
				LOGGER.info("Partners title is matched");
				gotoPartnerUsersTab();
				LOGGER.info("Redirected to partners users page");
				Assert.assertTrue(userPage.verifyElementIsClickableOfUserPage("userTitleOnBreadcrumbs"), "Partners Users title text is incorrect");
				LOGGER.info("Partners Users title is matched");
				
				commonTest.gotoRootCompaniesTab();;
				CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
				LOGGER.info("Redirected to Customer page");
				Assert.assertTrue(companiesPage.verifyElementsOfCompaniesPage("companiesTitleOnBreadcrumbs"), "Company title text is incorrect");
				LOGGER.info("Company title is matched");
				gotoCompaniesUsersTab();
				LOGGER.info("Redirected to Company users page");
				Assert.assertTrue(userPage.verifyElementIsClickableOfUserPage("userTitleOnBreadcrumbs"), "Company Users title text is incorrect");
				LOGGER.info("Company Users title is matched");

			}
			LOGGER.info("Site up test for Root Admin completed");

		} catch (Exception e) {
			LOGGER.error(("Exception occured in method rootAdministratorSiteUpTest " + e.getMessage()));
			siteUpRegressionPassOrNot= false;
		}
	}
	
	
	/**
	 * This is the method to verify if Root Admin site up test is successfull
	 * 
	 * @param LanguageCode - language code
	 */
	
	@Test(priority = 5,groups = { "REGRESSION_CI", "SMOKE_CI", "ALL_CI"})
	public void verifyRootAdministratorSiteUpTest() throws Exception {
			SoftAssert softAssert = new SoftAssert();
			login("ROOT_ADMIN_USER_WhatsNew", "ROOT_PASSWORD_WhatsNew");
			rootAdministratorSiteUpTest(LanguageCode, getCredentials(environment,"ROOT_ADMIN_USER_WhatsNew"));
			softAssert.assertTrue(siteUpRegressionPassOrNot == true,"Site Test for Root Admin got failed.");
			siteUpRegressionPassOrNot = true;
			softAssert.assertAll();
			LOGGER.info("Site up test for Root Admin is successful");
	}
	
	/**
	 * This Method creates the Latency Time csv.
	 * @param dashboardtime
	 * @param companyTime
	 * @param supportTeamTime
	 * @param incidentsTime
	 * @param devicesTime
	 * @param usersTime
	 * @param reportsTime
	 * @param logsTime
	 * @param settingsTime
	 * @param timeStamp
	 * @throws IOException
	 */
	public final void writeTimeforEachPagetoCSV(String dashboardtime, String companyTime, String supportTeamTime, String incidentsTime, String devicesTime, String usersTime, String reportsTime, String logsTime, String settingsTime, String timeStamp) throws IOException {
		
		File file = new File(ConstantPath.LATENCY_PATH+"latencyTime.csv"); 
		CSVWriter writer = null;  
	    try { 
	        // create FileWriter object with file as parameter 
	        FileWriter outputfile = new FileWriter(file); 
	  
	        // create CSVWriter object file writer object as parameter 
	        writer = new CSVWriter(outputfile); 
	  
	        // create a List which contains String array 
	        List<String[]> data = new ArrayList<String[]>(); 
	        data.add(new String[] {"TabName", "LatencyTime", "timeStamp" });
	        data.add(new String[] {"Dashboard", dashboardtime, timeStamp });
	        data.add(new String[] {"Company", companyTime, timeStamp });
	        data.add(new String[] {"Support Team", supportTeamTime, timeStamp });
	        data.add(new String[] {"Incidents", incidentsTime, timeStamp });
	        data.add(new String[] {"Devices", devicesTime, timeStamp });
	        data.add(new String[] {"Users", usersTime, timeStamp });
	        data.add(new String[] {"Reports", reportsTime, timeStamp });
	        data.add(new String[] {"Logs", logsTime, timeStamp });
	        data.add(new String[] {"Settings", settingsTime, timeStamp });
	        writer.writeAll(data); 
	  
	    } 
	    catch (IOException e) { 
	    	LOGGER.error(e.getMessage());
	        
	    }finally{
			writer.flush();
			writer.close();
		}	
	}
	
	/**
	 * This method is used to calculate the latency time of each page in production Site Up test.
	 * @param pageStartTime
	 * @return String
	 */
	public final String calculateLoadTimeForSiteUp(long pageStartTime) {
		String totalPageLoadTime = null;
		
		try {
		//stop timer for companies tab
		long pageFinishTime = System.currentTimeMillis();
		LOGGER.info("Time at which company page loaded completely - " + pageFinishTime + " " + "ms");
		long totalTimePage = (int) (((pageFinishTime - pageStartTime) / 1000) - 1);
		totalPageLoadTime = Long.toString(totalTimePage);
		
		}catch(Exception e) {
			LOGGER.error("Exception occured in calculating load time of page : " + e.getMessage());
			return totalPageLoadTime;
		}
		return totalPageLoadTime;
	}
}