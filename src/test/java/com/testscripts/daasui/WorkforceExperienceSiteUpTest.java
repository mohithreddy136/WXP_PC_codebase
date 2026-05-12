package com.testscripts.daasui;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.basesource.utils.CSVFileReader;
import com.basesource.utils.LaunchDarkly;

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
import com.daasui.constants.ScriptVariables;
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
import com.daasui.pages.WEXCustomerHomePage;
import com.daasui.pages.WEXCustomerUserListPage;
import com.daasui.pages.WEXCompanyOverviewPage;
import com.daasui.pages.WEXDashboardPage;
import com.daasui.pages.WEPDeviceListPage;
import com.daasui.pages.WEXHelpAndSupportPage;
import com.daasui.pages.WEXLogPage;
import com.daasui.pages.WhatsNewPage;
import com.google.common.base.Strings;
import com.opencsv.CSVWriter;


public class WorkforceExperienceSiteUpTest extends CommonTest {
	private static Logger LOGGER = LogManager.getLogger(WorkforceExperienceSiteUpTest.class);
	public static boolean siteUpExecution = false;
	public static boolean siteUpRegressionPassOrNot = true;
	
	
	@Test(priority = 1, groups = { "WorkforceExperience_PAGELOAD","PRODUCTION_SITEUP" })
	public final void verifyWorkforceExperienceSiteUpTest() throws Exception {
		long wexHomeStart = 0,dashboardStart = 0,deviceStart = 0,analyticsListPageStart=0,analyticsStart = 0,scriptsStart = 0, deviceDetailsStart=0, accountManagementStart=0,
		accountManagementUserTabStart=0,accountManagementUserDetailsStart=0,helpAndSupportStart=0, integrationsPageStart=0, logsPageStart=0, engagementDashboardStart=0, engagementPulsesStart=0;
		String wexHomeTotalTime = null;
		String dashboardTotalTime = null;
		String devicesTotalTime = null;
		String deviceDetailsTotalTime = null;
		String analyticsListPageTotalTime = null;
		String analyticsTotalTime = null;
		String scriptsTotalTime = null;
		String engagementDashboardTotalTime = null;
		String engagementPulsesStartTotalTime = null;
		String accountManagementTotalTime = null;
		String accountManagementUserPageTotalTime = null;
		String accountManagementUserDetailsPageTotalTime = null;
		String helpAndSupportTotalTime = null;
		String logsTotalTime = null;
		String integrationsTotalTime = null;
		HashMap<String, String> loadTimeInCSV = new HashMap <String, String>();
		
		WEXCustomerHomePage customerHomePage = new WEXCustomerHomePage(PreDefinedActions.getDriver()).getInstance();
		WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
		WEPDeviceListPage deviceListPage = new WEPDeviceListPage(PreDefinedActions.getDriver()).getInstance();
		WEXCustomerUserListPage WEXCustomerUserListPage = new WEXCustomerUserListPage(PreDefinedActions.getDriver()).getInstance();
		WEXCompanyOverviewPage WEXCompanyOverviewPage = new WEXCompanyOverviewPage(PreDefinedActions.getDriver()).getInstance();
		WEXHelpAndSupportPage helpAndSupportPage = new WEXHelpAndSupportPage(PreDefinedActions.getDriver()).getInstance();
		WEXLogPage wexlogPage = new WEXLogPage(PreDefinedActions.getDriver()).getInstance();
		AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver()).getInstance();
		LaunchDarkly ldinstance = new LaunchDarkly();
		
		loginWithoutImplicitWait("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		
		wexHomeStart = System.nanoTime();
		Assert.assertTrue(waitForLoaderIconToDisappear(), "FAIL: WorkforceExperience Home page did not load.");
		wexHomeTotalTime = calculateLoadTimeForSiteUp(wexHomeStart);

		LOGGER.info("User is on Home page");
		LOGGER.info("Total Time taken for Home page to load - " + wexHomeTotalTime + " " + "sec");
		LOGGER.info("===========================================================================");
		Assert.assertTrue(dashboardPage.getTextOfWexDashboardPage("homeSiteHeader")
				.equals(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "sidemenu.home")),"FleetManagement Home Site Header doesn't match");
		sleeper(500);
		openLeftSidePanel();
		
		if (!toggleVerification(ScriptVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
			dashboardPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT, CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DASHBOARD);
			dashboardStart = System.nanoTime();
			Assert.assertTrue(waitForLoaderIconToDisappear(), "FAIL: WorkforceExperience Dashboard page did not load.");
			dashboardTotalTime = calculateLoadTimeForSiteUp(dashboardStart);
			Assert.assertTrue(
					dashboardPage.getTextOfWexDashboardPage("fleetManagementSiteHeader")
					.equals(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "applications.fleetmanagement.breadcrumbs.title")),"FleetManagement Site Header doesn't match");
			LOGGER.info("User is on Fleet Management Dashboard");
			LOGGER.info("Total Time taken for dashboard page to load - " + dashboardTotalTime + " " + "sec");
			LOGGER.info("===========================================================================");
		}
		
		sleeper(2000);
		if (!toggleVerification(ScriptVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
			dashboardPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT, CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES);
		}else{
			dashboardPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT_DEVICES, CommonVariables.CUSTOMER_DEVICES_PCS);
		}
		
		deviceStart = System.nanoTime();
		Assert.assertTrue(waitForLoaderIconToDisappear(), "FAIL: WorkforceExperience Device page did not load.");
		devicesTotalTime = calculateLoadTimeForSiteUp(deviceStart);
//		Assert.assertTrue(
//				dashboardPage.getTextOfWexDashboardPage("fleetManagementDevicesSiteHeader")
//				.equals(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "applications.filter.subtitle")),"FleetManagement Devices breadcrumb doesn't match");
		LOGGER.info("User is on Fleet Management Devices");
		LOGGER.info("Total Time taken for Devices page to load - " + devicesTotalTime + " " + "sec");
		LOGGER.info("===========================================================================");

		sleeper(2000);
        resetTableConfiguration();
		Assert.assertTrue(deviceListPage.waitForElementsOfWEPDeviceListPage("firstSerialNumberFromList"), "FAIL: Devices are not getting displayed in the list");
		deviceListPage.clickOnElementsOfDevicePage("firstSerialNumberFromList");
		deviceDetailsStart = System.nanoTime();
		Assert.assertTrue(waitForLoaderIconToDisappear(), "FAIL: WorkforceExperience Device Details page did not load.");
		deviceDetailsTotalTime = calculateLoadTimeForSiteUp(deviceDetailsStart);
//		Assert.assertTrue(
//				dashboardPage.getTextOfWexDashboardPage("fleetManagementDeviceDetailsSiteHeader")
//				.equals(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "breadcrumbs.asset_details")),"FleetManagement Device Details breadcrumb doesn't match");
		LOGGER.info("User is on Fleet Management Device Details");
		LOGGER.info("Total Time taken for Device Details page to load - " + deviceDetailsTotalTime + " " + "sec");
		LOGGER.info("===========================================================================");
		
		sleeper(2000);
		dashboardPage.companyView(CommonVariables.CUSTOMER_ANALYTICS);
		analyticsListPageStart = System.nanoTime();
		Assert.assertTrue(waitForLoaderIconToDisappear(), "FAIL: WorkforceExperience Fleet-Analytics List page did not load.");
		Assert.assertTrue(analyticsPage.waitForElementsOfAnalyticsPage("blueScreenErrors"), "FAIL: Reports are displayed in the list");
		analyticsListPageTotalTime = calculateLoadTimeForSiteUp(analyticsListPageStart);
		LOGGER.info("User is on Analytics List page.");
		LOGGER.info("Total Time taken for Fleet-Analytics List page to load - " + analyticsListPageTotalTime + " " + "sec");
		LOGGER.info("===========================================================================");

		Assert.assertTrue(analyticsPage.waitForElementsOfAnalyticsPage("blueScreenErrors"), "FAIL: Blue Screen Error report is not displayed in the list");
		sleeper(2000);
		analyticsPage.clickOnElementsOfAnalyticsPage("blueScreenErrors");
		analyticsStart = System.nanoTime();
		Assert.assertTrue(waitForLoaderIconToDisappear(), "FAIL: WorkforceExperience Fleet-Analytics page-> Blue Screen Error report page did not load.");
		analyticsTotalTime = calculateLoadTimeForSiteUp(analyticsStart);
//		Assert.assertTrue(
//				dashboardPage.getTextOfWexDashboardPage("fleetManagementAnalyticsBSODSiteHeader")
//				.equals(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "pagetitle.bsod")),"FleetManagement BSOD breadcrumb doesn't match");
		LOGGER.info("User is on Analytics Blue Screen Error Report page.");
		LOGGER.info("Total Time taken for Fleet-Analytics page-> Blue Screen Error report to load - " + analyticsTotalTime + " " + "sec");
		LOGGER.info("===========================================================================");

		sleeper(7000);
		
		if (!toggleVerification(ScriptVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
			dashboardPage.companyView(CommonVariables.CUSTOMER_FLEET_MANAGEMENT, CommonVariables.CUSTOMER_FLEET_MANAGEMENT_SCRIPTS);
		}else{
			dashboardPage.companyView(CommonVariables.CUSTOMER_REMEDIATIONS, CommonVariables.CUSTOMER_FLEET_MANAGEMENT_SCRIPTS);
		}
		scriptsStart = System.nanoTime();
		Assert.assertTrue(waitForLoaderIconToDisappear(), "FAIL: WorkforceExperience Fleet-Scripts page did not load.");
		scriptsTotalTime = calculateLoadTimeForSiteUp(scriptsStart);
//		Assert.assertTrue(
//				dashboardPage.getTextOfWexDashboardPage("fleetManagementScriptsSiteHeader")
//				.equals(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "wex.script.library.header")),"FleetManagement Scripts breadcrumb doesn't match");
		LOGGER.info("User is on Fleet Management Scripts page.");
		LOGGER.info("Total Time taken for WorkforceExperience Fleet-Scripts page to load - " + scriptsTotalTime + " " + "sec");
		LOGGER.info("===========================================================================");

		sleeper(5000);
		
		if (!toggleVerification(ScriptVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
			dashboardPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT, CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_DASHBOARD);
			engagementDashboardStart = System.nanoTime();
			Assert.assertTrue(waitForLoaderIconToDisappear(), "FAIL: WorkforceExperience Employee Engagement Dashboard page did not load.");
			engagementDashboardTotalTime = calculateLoadTimeForSiteUp(engagementDashboardStart);
			Assert.assertTrue(dashboardPage.getTextOfWexDashboardPage("EmployeeEngagementDashboardSiteHeader")
					.equals(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "ee_module_name")),"Employee Enagement Dashboard breadcrumb doesn't match");
			LOGGER.info("User is on Employee Engagement Dashboard page.");
			LOGGER.info("Total Time taken for WorkforceExperience Employee Engagement Dashboard page to load - " + engagementDashboardTotalTime + " " + "sec");
			LOGGER.info("===========================================================================");
		}
		
		sleeper(5000);
		if (!toggleVerification(ScriptVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
			dashboardPage.companyView(CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT, CommonVariables.CUSTOMER_EMPLOYEE_ENGAGEMENT_PULSES);
		}else{
			dashboardPage.companyView(CommonVariables.PULSES);
		}
		engagementPulsesStart = System.nanoTime();
		Assert.assertTrue(waitForLoaderIconToDisappear(), "FAIL: WorkforceExperience Employee Engagement Pulses page did not load.");
		engagementPulsesStartTotalTime = calculateLoadTimeForSiteUp(engagementPulsesStart);
//		Assert.assertTrue(dashboardPage.getTextOfWexDashboardPage("employeePulsesSiteHeader")
//				.equals(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "ee_page_text_pulses")),"Employee Enagement Pulses breadcrumb doesn't match");
		LOGGER.info("User is on Employee Engagement Pulses page.");
		LOGGER.info("Total Time taken for WorkforceExperience Employee Engagement Pulses page to load - " + engagementPulsesStartTotalTime + " " + "sec");
		LOGGER.info("===========================================================================");
		
		sleeper(2000);
		wexlogPage.companyView(CommonVariables.INTEGRATIONS);
		integrationsPageStart = System.nanoTime();
		Assert.assertTrue(waitForLoaderIconToDisappear(), "FAIL: WorkforceExperience Fleet-Integrations Page did not load.");
		integrationsTotalTime = calculateLoadTimeForSiteUp(integrationsPageStart);
		Assert.assertTrue(
				dashboardPage.getTextOfWexDashboardPage("integrationsSiteHeader")
				.equals(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "integrations.appbar.title")),"Integrations Site Header doesn't match");
		LOGGER.info("User is on Integrations page.");
		LOGGER.info("Total Time taken for WorkforceExperience Fleet-Integrations Page to load - " + integrationsTotalTime + " " + "sec");
		LOGGER.info("===========================================================================");

		
		sleeper(2000);
		
		if (!toggleVerification(ScriptVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
			WEXCustomerUserListPage.companyView(CommonVariables.ACCOUNT_MANAGEMENT);
		}else{
			WEXCustomerUserListPage.companyView(CommonVariables.ACCOUNT);
		}
		accountManagementStart = System.nanoTime();
		Assert.assertTrue(waitForLoaderIconToDisappear(), "FAIL: WorkforceExperience Fleet-Account Management -> Overview page did not load.");
		accountManagementTotalTime = calculateLoadTimeForSiteUp(accountManagementStart);
		Assert.assertTrue(WEXCompanyOverviewPage.waitForElementsOfWEXCompanyOverviewPage("overviewTabSelected"), "FAIL: Overview tab is not selected");
		LOGGER.info("User is on Account Management - Overview tab.");
		LOGGER.info("Total Time taken for WorkforceExperience Fleet-Account Management-> Overview page to load - " + accountManagementTotalTime + " " + "sec");
		LOGGER.info("===========================================================================");

		sleeper(500);
		Assert.assertTrue(WEXCustomerUserListPage.waitForElementsOfWEXCustomerUserListPage("usersTab"), "FAIL: User tab is not visible");
		sleeper(2000);
		WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("usersTab");
		accountManagementUserTabStart = System.nanoTime();
		Assert.assertTrue(waitForLoaderIconToDisappear(), "FAIL: WorkforceExperience Fleet-Account Management -> User page did not load.");
		accountManagementUserPageTotalTime = calculateLoadTimeForSiteUp(accountManagementUserTabStart);
		Assert.assertTrue(WEXCustomerUserListPage.waitForElementsOfWEXCustomerUserListPage("usersTabSelected"),"Fail: User tab is not selected");
		LOGGER.info("User is on Account Management - Users tab.");
		LOGGER.info("Total Time taken for WorkforceExperience Fleet-Account Management-> User page to load - " + accountManagementUserPageTotalTime + " " + "sec");
		LOGGER.info("===========================================================================");
        if(WEXCustomerUserListPage.waitForElementsOfWEXCustomerUserListPage("userlistEmailField"))
		{
			Assert.assertTrue(WEXCustomerUserListPage.waitForElementsOfWEXCustomerUserListPage("userlistEmailField"), "FAIL: User record is not visible in the list");
		}
		else if(WEXCustomerUserListPage.waitForElementsOfWEXCustomerUserListPage("clearFilter"))
		{
			WEXCustomerUserListPage.clickOnElementsOfWEXCustomerUserListPage("clearFilter");
			Assert.assertTrue(WEXCustomerUserListPage.waitForElementsOfWEXCustomerUserListPage("userlistEmailField"), "FAIL: User record is not visible in the list");
		}else
		{
			Assert.assertTrue(WEXCustomerUserListPage.waitForElementsOfWEXCustomerUserListPage("userlistEmailField"), "FAIL: User record is not visible in the list");
		}
		sleeper(2000);
		WEXCustomerUserListPage.clickByJavaScriptOnWEXCustomerUserListPage("userlistEmailField");
		accountManagementUserDetailsStart = System.nanoTime();
		Assert.assertTrue(waitForLoaderIconToDisappear(), "FAIL: WorkforceExperience Fleet-Account Management -> User Details page did not load.");
		accountManagementUserDetailsPageTotalTime = calculateLoadTimeForSiteUp(accountManagementUserDetailsStart);
//		Assert.assertTrue(dashboardPage.getTextOfWexDashboardPage("accountManagementUserDetailsSiteHeader")
//				.equals(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "pagetitle.user_details")),"Account Management - User Details breadcrumb doesn't match");
		LOGGER.info("User is on Account Management - User Details page.");
		LOGGER.info("Total Time taken for WorkforceExperience Fleet-Account Management-> User Details page to load - " + accountManagementUserDetailsPageTotalTime + " " + "sec");
		LOGGER.info("===========================================================================");

		sleeper(2000);
		
		if (!toggleVerification(ScriptVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
			helpAndSupportPage.companyView(CommonVariables.WEX_HELP_AND_SUPPORT);
		}else{
			helpAndSupportPage.clickOnElementsOfWEXHelpAndSupportPage("HelpAndSupportTab");
		}
		helpAndSupportStart = System.nanoTime();
		Assert.assertTrue(waitForLoaderIconToDisappear(), "FAIL: WorkforceExperience Fleet-Help & Support Page did not load.");
		helpAndSupportTotalTime = calculateLoadTimeForSiteUp(helpAndSupportStart);
//		Assert.assertTrue(dashboardPage.getTextOfWexDashboardPage("helpSiteHeader").equals(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "sidemenu.support")),"Help and SUpport Site Header doesn't match");
		LOGGER.info("User is on Help & Support page.");
		LOGGER.info("Total Time taken for WorkforceExperience Fleet-Help & Support Page to load - " + helpAndSupportTotalTime + " " + "sec");
		LOGGER.info("===========================================================================");

		sleeper(2000);
		
		if (!toggleVerification(ScriptVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
			wexlogPage.companyView(CommonVariables.LOGS);
		}else{
			dashboardPage.companyView(CommonVariables.SETTINGS);
			sleeper(2000);
			dashboardPage.clickByJavaScriptOnDashboardPage("logs");
		}
		logsPageStart = System.nanoTime();
		Assert.assertTrue(waitForLoaderIconToDisappear(), "FAIL: WorkforceExperience Fleet-LOGS Page did not load.");
		logsTotalTime = calculateLoadTimeForSiteUp(logsPageStart);
//		Assert.assertTrue(
//				dashboardPage.getTextOfWexDashboardPage("logsSiteHeader")
//				.equals(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "pagetitle.logs")),"Logs Site Header doesn't match");
		LOGGER.info("User is on Logs page.");
		LOGGER.info("Total Time taken for WorkforceExperience Fleet-LOGS Page to load - " + logsTotalTime + " " + "sec");
		LOGGER.info("===========================================================================");

		loadTimeInCSV.put("Home", wexHomeTotalTime);
		if (!toggleVerification(ScriptVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
			loadTimeInCSV.put("Dashboard", dashboardTotalTime);
			loadTimeInCSV.put("Employee Engagement Dashboard", engagementDashboardTotalTime);
		}
		loadTimeInCSV.put("Devices", devicesTotalTime);
		loadTimeInCSV.put("Device Details", deviceDetailsTotalTime);
		loadTimeInCSV.put("Analytics List Page", analyticsListPageTotalTime);
		loadTimeInCSV.put("Analytics-Blue Screen Error Report", analyticsTotalTime);
		loadTimeInCSV.put("Scripts", scriptsTotalTime);
		loadTimeInCSV.put("Employee Engagement Pulses", engagementPulsesStartTotalTime);
		loadTimeInCSV.put("Account Management", accountManagementTotalTime);
		loadTimeInCSV.put("Account Management User Page", accountManagementUserPageTotalTime);
		loadTimeInCSV.put("Account Management User Details Page", accountManagementUserDetailsPageTotalTime);
		loadTimeInCSV.put("Help & Support", helpAndSupportTotalTime);
		loadTimeInCSV.put("Logs", logsTotalTime);
		loadTimeInCSV.put("Integrations", integrationsTotalTime);

		CSVFileReader csvFileReader = new CSVFileReader();
		LocalDate currentDate = LocalDate.now();
		File csvFile = new File(ConstantPath.IMPORT_PATH + CommonVariables.LOADTIME+ CommonVariables.CSV_FILENAME);
		csvFileReader.writeToCSVLoadtime(csvFile,loadTimeInCSV,currentDate.toString());
		
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
		long pageFinishTime = System.nanoTime();
		long elapsedTime = pageFinishTime - pageStartTime;
		double totalTimePage = (double)(elapsedTime / 1_000_000_000.0);
		totalPageLoadTime = String.format("%.2f",totalTimePage);
		}catch(Exception e) {
			LOGGER.error("Exception occured in calculating load time of page : " + e.getMessage());
			return String.format("%.2f", totalPageLoadTime);
		}
		return totalPageLoadTime;
	}
}