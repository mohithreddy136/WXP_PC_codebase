   package com.testscripts.daasui;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.io.FileReader;
import java.io.InputStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.html.HtmlParser;
import org.apache.tika.sax.BodyContentHandler;
import org.json.simple.JSONObject;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.basesource.action.MailinatorMail;
import com.basesource.action.PreDefinedActions;
import com.basesource.utils.EnrollFakeDevice;
import com.basesource.utils.Mailinator;
import com.basesource.utils.SNOWInstanceWakeUP;
import com.basetest.environments.SetTestEnvironments;
import com.daasui.constants.CommonVariables;
import com.daasui.constants.CompaniesVariables;
import com.daasui.constants.ConstantPath;
import com.daasui.constants.ConstantURL;
import com.daasui.constants.DashboardVariables;
import com.daasui.constants.SettingsVariables;
import com.daasui.constants.SupportVariables;
import com.daasui.constants.UserVariables;
import com.daasui.pages.CompaniesDetailsPage;
import com.daasui.pages.CompaniesPage;
import com.daasui.pages.DashboardPage;
import com.daasui.pages.DeviceDetailsPage;
import com.daasui.pages.DeviceListPage;
import com.daasui.pages.IncidentDetailsPage;
import com.daasui.pages.IncidentPage;
import com.daasui.pages.LicensesListPage;
import com.daasui.pages.PreferencesPage;
import com.daasui.pages.SettingsPage;
import com.daasui.pages.SupportTeamPage;
import com.daasui.pages.UserPage;
import com.daasui.pages.OrdersListPage;
import com.daasui.pages.OrderDetailsPage;

public class ActiveCareTest extends CommonTest {
	
	private static Logger LOGGER = LogManager.getLogger(ActiveCareTest.class);
	
	public static String timing = null;
	public static String emailExtract = null;
	public static String[] countryAndTimezone = new String[2];
	public static String statusTextPublicHoliday = null;
	public static String[] statusTextDirectSupport = new String[2];
	public static String[] emailBefore = new String[2];
	public static String statusTextCallback = null;
	int notificationCountUnread = 0;
	Properties settingsPageProperties;
	Properties EnvironmentPageProperties;
	public static String roleIdURL = getEnvironmentSpecificData(System.getProperty("environment"), "ROLE_ID_URL");
	public static String cataLystURL = getEnvironmentSpecificData(System.getProperty("environment"), "CATALYST_API_BASE_URL");
	public static String importCompany = getEnvironmentSpecificData(System.getProperty("environment"), "DEVICE_IMPORT_COMPANY");
	public static String customfieldsCompany = getEnvironmentSpecificData(System.getProperty("environment"), "CUSTOM_FIELD_COMPANY");
	public static String supportTeamUserEmail = getEnvironmentSpecificData(System.getProperty("environment"), "SUUPORT_TEAM_USER_EMAIL");
	public static String partnerName = getEnvironmentSpecificData(System.getProperty("environment"), "HELP_AND_SUPPORT_PARTNER_NAME");
	public static String mspName = getEnvironmentSpecificData(System.getProperty("environment"), "HELP_AND_SUPPORT_MSP_NAME");
	public static String incidentPCCompanyName = getEnvironmentSpecificData(System.getProperty("environment"), "INCIDENT_PCCOMPANY");
	public static String incidentPMCompanyName = getEnvironmentSpecificData(System.getProperty("environment"), "INCIDENT_PMCOMPANY");
	public static String subscriptionURL = "services/subscription_admin_service/1.0/licenses/";
	public ArrayList<String> customFields = new ArrayList<String>();
	
	
	@DataProvider
	public Object[][] loginData() {
		Object[][] data = new Object[3][2];
		data[0][0] = "RESELLER_NEW_UI_EMAIL_US";
		data[0][1] = "RESELLER_NEW_UI_PASSWORD_US";
		data[1][0] = "RESELLER_NEW_UI_EMAIL_US_SS"; // service specialist
		data[1][1] = "RESELLER_NEW_UI_PASSWORD_US_SS";
		data[2][0] = "RESELLER_SPECIALIST_NEW_UI_Companies_EMAIL_COMPANIES"; // sales specialist
		data[2][1] = "RESELLER_SPECIALIST_NEW_UI_Companies_PASSWORD_COMPANIES";
		return data;
	}
	
	@DataProvider
	public Object[][] loginDataForHelpAndSupport() {
		Object[][] data = new Object[3][2];
		data[0][0] = "MSP_HELP_AND_SUPPORT_EMAIL";
		data[0][1] = "MSP_HELP_AND_SUPPORT_EMAIL_PASSWORD";
		data[1][0] = "RESELLER_HELP_AND_SUPPORT_EMAIL";
		data[1][1] = "RESELLER_HELP_AND_SUPPORT_EMAIL_PASSWORD";
		data[2][0] = "IT_ADMIN_HELP_AND_SUPPORT_EMAIL"; 
		data[2][1] = "IT_ADMIN_HELP_AND_SUPPORT_PASSWORD";
		return data;
	}
	
	
	
	@Test(priority = 1, groups = { "ACTIVECARESANITY","REGRESSIONSETTINGS2", "REGRESSION_CI","STABILIZATION_STAGING" }, description = "TEST CASE - 1316789")
	public final void verifyUrlRedirectionDisableActiveCare() throws Exception
	{
		SoftAssert softAssert = new SoftAssert();
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
		getUrl(getEnvironment(System.getProperty("environment"))+"disable/activecare");
		String currentURL = settingsPage.getUrlOfCurrentPage();
		Assert.assertTrue(currentURL.equals(SettingsVariables.SMARTSHEET_URL_DISABLEACTIVECARE),"FAIL: User is not redirected to smartsheet.com for Disable PremiumCare");
		LOGGER.info("URL Redirection to Disable PremiumCare validated successfully");
		softAssert.assertAll();
	}

	
	//This method verifies redirection of URL for customers requesting disabling ActiveCare
	@Test(priority = 2, groups = { "ACTIVECARESANITY","REGRESSIONSETTINGS2", "REGRESSION_CI","STABILIZATION_STAGING" }, description = "TEST CASE - 1316788")
	public final void verifyUrlRedirectionTenantRequestActiveCare() throws Exception
	{
		SoftAssert softAssert = new SoftAssert();
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
		getUrl(getEnvironment(System.getProperty("environment"))+"registration/premiumplus");
		String currentURL = settingsPage.getUrlOfCurrentPage();
		Assert.assertTrue(currentURL.equals(SettingsVariables.SMARTSHEET_URL_TENANTREQUESTACTIVECARE),"FAIL: User is not redirected to smartsheet.com for Tenant Request");
		LOGGER.info("URL Redirection to Tenant Request validated successfully");
		softAssert.assertAll();
	}
	
	
	// This Data provider includes credential for AC only and AC+PI and AC+PI+WPT users.		
	@DataProvider
	public Object[][] helpAndSupportMenus() {
		return new Object[][] {
			{"ACTIVECARETENANT_EMAIL","ACTIVECARETENANT_PASSWORD",new String[] {"Active Care Assistance"}},
			{"ACPITENANT_EMAIL","ACPITENANT_PASSWORD",new String[] {"HP Assistance","Active Care Assistance"}},
			{"ACTIVECARETENANT_EMAIL1", "ACPITENANT_PASSWORD", new String[] {"HP Assistance","Active Care Assistance"}}				
			};
	}

	//This method verifies Help And Support menus for AC and AC+PI Tenant
	@Test(priority = 3, groups = { "ACTIVECARESANITY","REGRESSIONSETTINGS2", "REGRESSION_CI","STABILIZATION_STAGING" }, description = "TEST CASE - 1228306",dataProvider="helpAndSupportMenus")
	public final void verifyHelpAndSupportTabs(String username, String password, String[] helpAndSupportMenus) throws Exception {

		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		login(username,password);
		waitForPageLoaded();
		gotoHelpAndSupportTab();
		Assert.assertTrue(dashboardPage.verifyWidgetsDisplayedForActiveCare("helpandsupportabs", helpAndSupportMenus), "helpAndSupportTabs is incorrect");
		softAssert.assertAll();
		LOGGER.info("Help And Support Tabs completed successfully.");
	}
	
	
	@Test(priority = 4, groups = { "ACTIVECARESANITY","REGRESSIONSETTINGS2", "REGRESSION_CI","STABILIZATION_STAGING" },description = "TEST CASE - 1228306")
	public final void ACemail() throws Exception {
		login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		CompaniesDetailsPage companiesDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver()).getInstance();
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
		LOGGER.info("Redirected to Companies tab");

		waitForPageLoaded();
		resetTableConfiguration();
		String compName = generateRandomString(10);
		String compEmail = compName.toLowerCase() + "@hpmsqa.mailinator.com";						
		companiesPage.addCompanies(LanguageCode, compName, compEmail, getEnvironmentSpecificData(System.getProperty("environment"), "COMPANY_COUNTRY"), CommonVariables.PLAN_ACTIVE_CARE, CommonVariables.MSP_NAME, getEnvironmentSpecificData(environment, "ASSIGNED_PARTNER_NAME_ADD"), "FN", "LN", false, false, true);
		LOGGER.info("Added company with billing model as Care Pack");
		SoftAssert softAssert = new SoftAssert();
		String expectedMailContent = (getTextLanguage(LanguageCode,"idm","active.care.account.create.subject")+" "
									+ getTextLanguage(LanguageCode,"idm","active.care.account.create.dear")+"\n"
									+ getTextLanguage(LanguageCode,"idm","active.care.account.create.body_msg1").replace("<b>","").replace("</b>","")+"\n"
									+ getTextLanguage(LanguageCode,"idm","active.care.account.create.body_msg3").replace("<a href={0} style=\"color: #0096D6;\">","").replace("</a>","" )+"\n"
									+ getTextLanguage(LanguageCode,"idm","active.care.account.create.body_msg14")+"\n"
									+ getTextLanguage(LanguageCode,"idm","active.care.account.create.body_msg15").replace("<a href={0} style=\"color: #0096D6;\">{0}","").replace("</a>","").replace("{0}","usstaging")+"\n"
									+ getTextLanguage(LanguageCode,"idm","active.care.account.create.body_msg16").replace("<a href={0} style=\"color: #0096D6;\">","").replace("</a>","").replace("{0}","HP TechPulse Windows application").replace("<a href={1} style=\"color: #0096D6;\">","").replace("</a>","")+"\n"
									+ getTextLanguage(LanguageCode,"idm","active.care.account.create.body_msg13")+"\n"
									+ getTextLanguage(LanguageCode, "idm","active.care.account.create.body_msg4")+"\n"
									+ "1. "+getTextLanguage(LanguageCode,"idm","active.care.account.create.body_msg5")+"\n"
									+ "2. "+getTextLanguage(LanguageCode,"idm","active.care.account.create.body_msg6")+"\n"
									+ "3. "+getTextLanguage(LanguageCode,"idm","active.care.account.create.body_msg7")+"\n"
									+ "4. "+getTextLanguage(LanguageCode,"idm","active.care.account.create.body_msg8")+"\n"
									+ "5. "+getTextLanguage(LanguageCode,"idm","active.care.account.create.body_msg9")+"\n"
									+ "6. "+getTextLanguage(LanguageCode,"idm","active.care.account.create.body_msg10")+"\n"
									+ getTextLanguage(LanguageCode,"idm","onboarding.display.msg_7")+" hp.com/active-care"+"\n"
									+ getTextLanguage(LanguageCode,"idm","active.care.account.create.body_msg11")+"\n"
									+ getTextLanguage(LanguageCode,"idm","active.care.account.create.body_msg12")+"\n"
									+ "HPActiveCareProcessTeam@hp.com"+"\n"
									+ getTextLanguage(LanguageCode,"idm","onboarding.display.msg_4")+"\n"
									+ getTextLanguage(LanguageCode,"idm","active.care.account.create.body_msg17"));

		LOGGER.info("Invite Mail content: " +expectedMailContent);
		//String email1 = email;
		String actualMailContent = settingsPage.verifyReceivedEmailContent("INVITED_ADDUSER_EMAIL_SUBJECT", environment, compEmail,true);
	    int count = 0;
        while (count < 5 && actualMailContent == "") {
            sleeper(1000);
            count++;
            LOGGER.info(count + " : Couldn't receive Email");
            actualMailContent = verifyReceivedEmailContent("INVITED_ADDUSER_EMAIL_SUBJECT", environment, compEmail,true);
        }
        LOGGER.info("Mail content: " +actualMailContent);
	    softAssert.assertAll();
		LOGGER.info("Email Verification of invited user is done succesfully");

	}
	
	
	@Test(priority = 5, groups = {"ACTIVECARESANITY", "ACTIVECARESANITY","REGRESSIONSETTINGS2", "REGRESSION_CI","STABILIZATION_STAGING" }, description = "TEST CASE - 1295294")
	public final void verifyOrderSummaryFileDownload() throws Exception
	{
		OrdersListPage ordersListPage = new OrdersListPage(PreDefinedActions.getDriver()).getInstance();
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		
		login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
		String environment = System.getProperty("environment");
		if(environment.toLowerCase().contains("eu")) {
			LOGGER.info("As per new CarePack service EU root user will not have Orders tab. Only US root user will have orders tab and will be allowed to import devices into a Tenant.");
			softAssert.assertAll();
		}else {
		gotoOrderTab();
		sleeper(5000);
		LOGGER.info("Navigate to Orders tab.");
		int row_number = 1;
		softAssert.assertTrue(ordersListPage.downloadOrderSummaryFile(row_number),"Donwload ordersummary file.");
		softAssert.assertTrue(ordersListPage.verifyElementsOfordersListPage("downloadToastMessage"),"Toast message is displayed");	
		LOGGER.info("Order Summary File Download validated successfully.");
		softAssert.assertAll();	
		}
	}
	
	
	@Test(priority = 6, groups = { "REGRESSIONSETTINGS2", "REGRESSION_CI",	"STABILIZATION_STAGING" }, description = "TEST CASE - 1180738")
	public final void verifyNotificationsTabforAC() throws Exception {
		
		List<String> hardwareHealthList = new ArrayList<String>(Arrays.asList(
				getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties",
						"com.hp.mpi.icm.subtype.systemthermal"),
				// For EU region Battery Recall option is missing from HardwareHealthList.Need to check. 
				getTextLanguage(LanguageCode,"MPI-Incident-management-service-backend-Properties",
						"hp.mpi.icm.subtype.batteryrecall"),
				getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties",
						"com.hp.mpi.icm.subtype.hddpredictive"),
				getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties",
						"com.hp.mpi.icm.subtype.biosoutofdate"),
				getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties",
						"com.hp.mpi.icm.subtype.batteryneedsattention"),
				getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties",
						"com.hp.mpi.icm.subtype.fancritical"),
				getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties",
						"com.hp.mpi.icm.subtype.fanwarning")));
		
		List<String> activeCareList = new ArrayList<String>(Arrays.asList(
				getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties",
						"com.hp.mpi.icm.subtype.motherboard"),
				getTextLanguage(LanguageCode,"MPI-Incident-management-service-backend-Properties",
						"com.hp.mpi.icm.subtype.iodevice"),
				getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties",
						"com.hp.mpi.icm.subtype.battery"),
				getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties",
						"com.hp.mpi.icm.subtype.other"),
				getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties",
						"com.hp.mpi.icm.insights.tags.hdd.hdd"),
				getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties",
						"com.hp.mpi.icm.subtype.power")));
		
		
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		login("ACTIVECARETENANT_EMAIL", "ACTIVECARETENANT_PASSWORD");
		waitForPageLoaded();
		gotoCompanySettingsTab();						
		settingsPage.waitForElementsOfSettingsPage("notificationsTab");
		settingsPage.clickOnElementsOfSettingsPage("notificationsTab");
		softAssert.assertTrue(
				settingsPage.getTextOfSettingsPage("deviceNotificationsHeader").equalsIgnoreCase(
						getTextLanguage(LanguageCode, "daas_ui", "companies.details.tile.notifications")),
				"Header on device notifications tile is incorrect");
		softAssert.assertTrue(
				settingsPage.getTextOfSettingsPage("deviceNotificationsHeaderWarning").equalsIgnoreCase(
						getTextLanguage(LanguageCode, "daas_ui", "companies.details.tile.notifications.warning")
								.replace("{applicationName}", "Workforce Experience Platform")),
				"Warning on device notifications tile is incorrect");

		softAssert.assertTrue(
				settingsPage.getTextOfSettingsPage("allowServiceLocationLabel").equalsIgnoreCase(
						getTextLanguage(LanguageCode, "daas_ui", "companies.details.allow.service.location")),
				"Title on allow service location tile is incorrect");
		softAssert.assertTrue(
				settingsPage.getTextOfSettingsPage("selfServiceAlertsLabel").equalsIgnoreCase(
						getTextLanguage(LanguageCode, "daas_ui", "companies.details.self.service.alerts")+" (For Active Care devices only)"),
				"Title on self service alerts tile is incorrect");
		
		String enabled = getTextLanguage(LanguageCode, "daas_ui", "global.enabled");
		String disabled = getTextLanguage(LanguageCode, "daas_ui", "global.disabled");
		waitForPageLoaded();
		settingsPage.clickOnElementsOfSettingsPage("hardwareHealth");
		boolean hardwareStatus = settingsPage.checkStatusOfAlerts(enabled, disabled, hardwareHealthList, "hardware1", true);
		Assert.assertTrue(hardwareStatus, "Status mismatch for Hardware Health List");			
		settingsPage.clickOnElementsOfSettingsPage("hardwareHealth");
		settingsPage.waitForElementsOfSettingsPage("activeCare");
		settingsPage.clickByJavaScriptOnSettingsPage("activeCare");
		boolean activeCareStatus = settingsPage.checkStatusOfAlerts(enabled, disabled, activeCareList,"activeCare1",false);
		Assert.assertTrue(activeCareStatus,"Status mismatch for Active Care Alerts");		
		LOGGER.info("Notification tab default state for Active Care validated successfully.");
		softAssert.assertAll();			
	}
	
	
	// This Test will verify devices imported into named tenant.
	@Test(priority = 7, groups = {"ACTIVECARESANITY", "REGRESSIONSETTINGS2", "REGRESSION_CI",
			"STABILIZATION_STAGING", "CAREPACK"}, description = "Test Case ID : 1274300")
	public final void verifyCPImportCSVOrdersPage() throws Exception {
		login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
		String environment = System.getProperty("environment");
		DeviceListPage devicelistPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
		DeviceDetailsPage deviceDetailPage = new DeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
		OrdersListPage ordersListPage = new OrdersListPage(PreDefinedActions.getDriver()).getInstance();
		OrderDetailsPage orderDetailsPage = new OrderDetailsPage(PreDefinedActions.getDriver()).getInstance();
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		String[] columnValueToUpdateRandomData = { "ObjectOfServiceSerialNumber" };
		
		if(environment.toLowerCase().contains("us")) {
		// Update ObjectServiceSerialNumber each execution
		settingsPage.updateActiveCareCSVFieldValue("ORDERS_EXISTING_AC_COMPANY", columnValueToUpdateRandomData);
		}
		// Fetch all rows from the CSV as TestData for validation with UI
		String[][] csv_TestData = orderDetailsPage.getTestDataFromImportCSV("ORDERS_EXISTING_AC_COMPANY");
		// create new data
		String[][] csv_NewTestData = new String[csv_TestData.length -1][csv_TestData[0].length];
		HashMap<Integer, String> pos = new HashMap<Integer, String>();
		pos.put(1, "Success");
		pos.put(28,  "PASSED");
		for(int j=1; j<csv_TestData.length; j++) {
			int val = 1;
			for(int i=0; i<csv_TestData[0].length; i++) {
				if(pos.containsKey(i)) {
					csv_NewTestData[j-1][i]=pos.get(i);
				}else {
				csv_NewTestData[j-1][i]=csv_TestData[j][val-1];
				val++;
				}
								
		}
		}
		LOGGER.info(Arrays.deepToString(csv_NewTestData));
		waitForPageLoaded();
		LOGGER.info("Root user logged in");
		// this will verify condition for EU environment user. EU region root user will not have Orders tab.
		if(environment.toLowerCase().contains("eu")) {
			logout();
			orderDetailsPage.deleteAllcookies();
			LOGGER.info("As per new CarePack service EU root user will not have Orders tab. Only US root user will have orders tab and will be allowed to import devices into a Tenant.");
			sleeper(500);
			// Now login to Active Care HoldingTank Tenant and verify devices are available in list.
			login("ACTIVECARETENANT_EMAIL", "ACTIVECARETENANT_PASSWORD");
			waitForPageLoaded();
			// Navigate to Devices Tab
			gotoDevicesTab();
			waitForPageLoaded();
			// Navigate to Pending Enrollment Tab.
			devicelistPage.clickOnElementsOfDeviceListPage("pendingEnrollmentTab");
			waitForPageLoaded();
			String[] serialNumbers = settingsPage.getCSVFieldValue("ObjectOfServiceSerialNumber", "ORDERS_EXISTING_AC_COMPANY");
			for(int i=1; i<serialNumbers.length; i++) 
			{			
				devicelistPage.enterTextForDeviceListPage("serialNumberSearchBox",serialNumbers[i]);
				sleeper(1000);
				softAssert.assertTrue(devicelistPage.verifyElementsOfDeviceListPage("serialNumberList"), "FAIL: Matching serial number "+ serialNumbers[i] +" is not found in device list.");
			}
			softAssert.assertAll();
		}else {
			
		gotoOrderTab();
		String filenameToBeUploaded = getEnvironmentSpecificData(System.getProperty("environment"), "ORDERS_EXISTING_AC_COMPANY");
		sleeper(5000);
		LOGGER.info("Navigate to Orders tab.");
		softAssert.assertTrue(ordersListPage.isAtOrdersListPage(), "FAIL: User is not at Orders List Page.");
		Assert.assertTrue(ordersListPage.verifyImportCSVOrdersListPage(LanguageCode, filenameToBeUploaded), "FAIL: File import failed.");
		refreshPage();
		sleeper(5000);
		
		// fetch row number from OrdersListPage on basis of imported File name. 
		int row_number = ordersListPage.getImportRecordRowNumberInList(LanguageCode, 
				getEnvironmentSpecificData(System.getProperty("environment"), "ORDERS_EXISTING_AC_COMPANY"));
		if(row_number>0) {
			LOGGER.info(getEnvironmentSpecificData(System.getProperty("environment"), "ORDERS_EXISTING_AC_COMPANY")+" file mame is present in the list.");
			Assert.assertTrue(true, "FAIL: Imported CSV file not found in Orders List Page.");
		}else {
			LOGGER.info(getEnvironmentSpecificData(System.getProperty("environment"), "ORDERS_EXISTING_AC_COMPANY")+" file mame is not present in the list.");
			Assert.assertTrue(false, "FAIL: Imported CSV file not found in Orders List Page.");
		}
		
		// Verify UI details of file uploaded record with OrdersListPage.
		softAssert.assertTrue(ordersListPage.checkOrderListPageUIDetails(row_number, String.valueOf(csv_TestData.length-1), filenameToBeUploaded), "FAIL: Validation of OrderListPage UI details failed.");
		// Click file name to open Order Details page.
		softAssert.assertTrue(ordersListPage.clickImportRecordInList(row_number),"FAIL: Unable to click import record in the Orders list.");
		sleeper(500);
		Assert.assertTrue(orderDetailsPage.isAtOrdersDetailsPage(), "FAIL: User is not at Order Details Page.");
		
		// Fetch all rows from Order Details page for validation with test data.
		String[][] orderdetailsdata = orderDetailsPage.getImportDataFromOrdersDetailsPage();
		LOGGER.info(Arrays.deepToString(orderdetailsdata));
		
		Assert.assertTrue(Arrays.deepEquals(csv_NewTestData, orderdetailsdata),  "FAIL: Uploaded Test data does not match with data displayed in Order details page.");
		LOGGER.info("Uploaded Test data matches with data displayed in Order details page.");

		logout();
		orderDetailsPage.deleteAllcookies();
		sleeper(500);
		// Now login to Active Care HoldingTank Tenant and verify devices are available in list.
		login("ACTIVECARETENANT_EMAIL", "ACTIVECARETENANT_PASSWORD");
		waitForPageLoaded();
		// Navigate to Devices Tab
		gotoDevicesTab();
		waitForPageLoaded();
		// Navigate to Pending Enrollment Tab.
		devicelistPage.clickOnElementsOfDeviceListPage("pendingEnrollmentTab");
		waitForPageLoaded();
		// Fetch serial numbers from CSV to search in Pending enrollment tab.
		String[] serialNumbers = settingsPage.getCSVFieldValue("ObjectOfServiceSerialNumber", "ORDERS_EXISTING_AC_COMPANY");
		for(int i=0; i<serialNumbers.length-1; i++) 
		{			
			devicelistPage.enterTextForDeviceListPage("serialNumberSearchBox",serialNumbers[i]);
			sleeper(1000);
			softAssert.assertTrue(devicelistPage.verifyElementsOfDeviceListPage("serialNumberList"), "FAIL: Matching serial number "+ serialNumbers[i] +" is not found in device list.");
			
		//		Assert commented because of Elastic Search issue.
		//		String planNameDeviceList = devicelistPage.getTextOfDeviceListPage("planNameFirstDeviceListPagePE");
		//		softAssert.assertTrue(planNameDeviceList.equals(
		//				getTextLanguage(LanguageCode, "daas_ui", "services.title.hp_care_pack")));
		}
		
		// Once defect is closed will add next step to validate UI details with test data.
		softAssert.assertAll();
		}
	}
	
	
	@Test(priority = 8, groups = {"ACTIVECARESANITY", "REGRESSIONSETTINGS2", "SMOKE", "REGRESSION_CI", "SMOKE_CI",
	"STABILIZATION_STAGING"})
	public final void verifyDeviceUploadToACHT() throws Exception {
		login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
		DeviceListPage devicelistPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
		DeviceDetailsPage deviceDetailPage = new DeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
		OrdersListPage ordersListPage = new OrdersListPage(PreDefinedActions.getDriver()).getInstance();
		OrderDetailsPage orderDetailsPage = new OrderDetailsPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		String environment = System.getProperty("environment");
		String[] columnValueToUpdateRandomData = { "ObjectOfServiceSerialNumber" };
		
		if(environment.toLowerCase().contains("us")) {
		// Update ObjectServiceSerialNumber each execution
		settingsPage.updateActiveCareCSVFieldValue("ORDERS_MANDATORY_HT", columnValueToUpdateRandomData);
		}
		waitForPageLoaded();
		// Fetch all rows from the CSV as TestData for validation with UI
		String[][] csv_TestData = orderDetailsPage.getTestDataFromImportCSV("ORDERS_MANDATORY_HT");
		// create new data
		String[][] csv_NewTestData = new String[csv_TestData.length -1][csv_TestData[0].length];
		HashMap<Integer, String> pos = new HashMap<Integer, String>();
		pos.put(1, "No Tenant Found for EndCustomerName");
		pos.put(28,  "PASSED");
		
		for(int j=1; j<csv_TestData.length; j++) {
			int val = 1;
			for(int i=0; i<csv_TestData[0].length; i++) {
				if(pos.containsKey(i)) {
					csv_NewTestData[j-1][i]=(i==1)? pos.get(i)+" "+csv_TestData[j][12]:pos.get(i);
				}else {
				csv_NewTestData[j-1][i]=csv_TestData[j][val-1];
				val++;
				}
		}
		}
		LOGGER.info(Arrays.deepToString(csv_NewTestData));
		waitForPageLoaded();
		LOGGER.info("Root user logged in");
		// this will verify condition for EU environment user. EU region root user will not have Orders tab.
		// For EU region we will login to EU Holding Tank tenant and verify device is present.
		if(environment.toLowerCase().contains("eu")) {
			logout();
			orderDetailsPage.deleteAllcookies();
			LOGGER.info("As per new CarePack service EU root user will not have Orders tab. Only US root user will have orders tab and will be allowed to import devices into a Tenant.");
			sleeper(500);
			// Now login to Active Care HoldingTank Tenant and verify devices are available in list.
			login("HT_TENANT_USER_EMAIL", "HT_TENANT_USER_PASSWORD");
			waitForPageLoaded();
			// Navigate to Devices Tab
			gotoDevicesTab();
			waitForPageLoaded();
			// Navigate to Pending Enrollment Tab.
			devicelistPage.clickOnElementsOfDeviceListPage("pendingEnrollmentTab");
			waitForPageLoaded();
			String[] serialNumbers = settingsPage.getCSVFieldValue("ObjectOfServiceSerialNumber", "ORDERS_MANDATORY_HT");
			for(int i=1; i<serialNumbers.length; i++) 
			{			
				devicelistPage.enterTextForDeviceListPage("serialNumberSearchBox",serialNumbers[i]);
				sleeper(1000);
				softAssert.assertTrue(devicelistPage.verifyElementsOfDeviceListPage("serialNumberList"), "FAIL: Matching serial number "+ serialNumbers[i] +" is not found in device list.");
			}
			softAssert.assertAll();
		}else {
			
		gotoOrderTab();
		LOGGER.info("Navigate to Orders tab.");
		softAssert.assertTrue(ordersListPage.isAtOrdersListPage(), "FAIL: User is not at Orders List Page.");
		
		Assert.assertTrue(ordersListPage.verifyImportCSVOrdersListPage(LanguageCode, 
				getEnvironmentSpecificData(System.getProperty("environment"), "ORDERS_MANDATORY_HT")), "FAIL: File import failed.");
		refreshPage();
		sleeper(500);
		// fetch row number from OrdersListPage on basis of imported File name. 
		int row_number = ordersListPage.getImportRecordRowNumberInList(LanguageCode, 
				getEnvironmentSpecificData(System.getProperty("environment"), "ORDERS_MANDATORY_HT"));
		if(row_number>0) {
			LOGGER.info(getEnvironmentSpecificData(System.getProperty("environment"), "ORDERS_MANDATORY_HT")+" file mame is present in the list.");
			Assert.assertTrue(true, "FAIL: Imported CSV file not found in Orders List Page.");
		}else {
			LOGGER.info(getEnvironmentSpecificData(System.getProperty("environment"), "ORDERS_MANDATORY_HT")+" file mame is not present in the list.");
			Assert.assertTrue(false, "FAIL: Imported CSV file not found in Orders List Page.");
		
		}
		// Click file name to open Order Details page.
		softAssert.assertTrue(ordersListPage.clickImportRecordInList(row_number),"FAIL: Unable to click import record in the Orders list.");
		sleeper(500);
		Assert.assertTrue(orderDetailsPage.isAtOrdersDetailsPage(), "FAIL: User is not at Order Details Page.");
		
		// Fetch all rows from Order Details page for validation with test data.
		String[][] orderdetailsdata = orderDetailsPage.getImportDataFromOrdersDetailsPage();
		LOGGER.info(Arrays.deepToString(orderdetailsdata));
		
		Assert.assertTrue(Arrays.deepEquals(csv_NewTestData, orderdetailsdata),  "FAIL: Uploaded Test data does not match with data displayed in Order details page.");
		logout();
		
		orderDetailsPage.deleteAllcookies();
		sleeper(500);
		// Now login to Active Care HoldingTank Tenant and verify devices are available in list.
		login("HT_TENANT_USER_EMAIL", "HT_TENANT_USER_PASSWORD");
		waitForPageLoaded();
		// Navigate to Devices Tab
		gotoDevicesTab();
		waitForPageLoaded();
		// Navigate to Pending Enrollment Tab.
		devicelistPage.clickOnElementsOfDeviceListPage("pendingEnrollmentTab");
		waitForPageLoaded();
		// Fetch serial numbers from CSV to search in Pending enrollment tab.
		String[] serialNumbers = settingsPage.getCSVFieldValue("ObjectOfServiceSerialNumber", "ORDERS_MANDATORY_HT");
		for(int i=0; i<serialNumbers.length-1; i++) 
		{			
			devicelistPage.enterTextForDeviceListPage("serialNumberSearchBox",serialNumbers[i]);
			sleeper(1000);
			softAssert.assertTrue(devicelistPage.verifyElementsOfDeviceListPage("serialNumberList"), "FAIL: Matching serial number "+ serialNumbers[i] +" is not found in device list.");
			
		//		Assert commented because of Elastic Search issue.
		//		String planNameDeviceList = devicelistPage.getTextOfDeviceListPage("planNameFirstDeviceListPagePE");
		//		softAssert.assertTrue(planNameDeviceList.equals(
		//				getTextLanguage(LanguageCode, "daas_ui", "services.title.hp_care_pack")));
		}
		softAssert.assertAll();
		
		}
		}

	
	// This Test will verify ActiveCare Tenant creation is not allowed for Partner user -> Add Company flow.
	@Test(priority = 9, groups = {"ACTIVECARESANITY", "REGRESSIONSETTINGS2", "REGRESSION_CI",
			"STABILIZATION_STAGING" }, description = "Test Case ID : 1274300")
	public final void verifyACTenantCreationNotAllowedPartnerUser() throws Exception {
		login("AC_PARTNERUSER_EMAIL", "AC_PARTNERUSER_PASSWORD");
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		CompaniesDetailsPage companiesDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		waitForPageLoaded();
		LOGGER.info("Partner user logged in successfully.");
		gotoCompaniesTab();
		LOGGER.info("Navigate to Partner -> Customer -> Companies tab.");
		// Company related test data
		String company_name = CommonVariables.AC_COMPANY_NAME + generateRandomString(5);
		String company_email = company_name.toLowerCase() + "@hpmsqa.mailinator.com";
		String CompanyCountry =  getEnvironmentSpecificData(System.getProperty("environment"), "COMPANY_COUNTRY");
		String CompanyPlan = CommonVariables.PLAN_ACTIVE_CARE;
		String CompanyAddressLine1 =CommonVariables.STREET_ADDRESS;
		String CompanyCity =CommonVariables.CITY;
		String CompanyState =CommonVariables.STATE;
		String CompanyZip =CommonVariables.ZIP_CODE;
		Assert.assertFalse(companiesPage.addCompanies(LanguageCode, company_name, company_email, CompanyCountry, CompanyPlan, CommonVariables.MSP_NAME, getEnvironmentSpecificData(environment, "ASSIGNED_PARTNER_NAME_ADD"), "FN", "LN", false, false, true), "Partner user is able to create Active Care tenant.");
		LOGGER.info("PASS:Partner user is not able to create Active Care tenant.");
		
		softAssert.assertAll();
	}
	
	
	// This method verifies menu under Settings and their sub-menus for AC+PI tenant
	@Test(priority = 10, groups = {"ACTIVECARESANITY", "REGRESSIONSETTINGS2", "REGRESSION_CI",
	"STABILIZATION_STAGING" })
	public final void verifySettingsforPIAC() throws Exception {
		login("ACPITENANT_EMAIL", "ACPITENANT_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		waitForPageLoaded();
		gotoCompanySettingsTab();
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		String[] overViewLabels = {"Customer ID" , "Company Name" , "Address" , "Preferred Time Zone" , "Company Size" , "Primary Administrator" , "Source Of Customer", "Created On"};
		String[] locations = {"Shipping Locations"};
		String[] preferenceAnchorMenu = {"Archived Devices","Authentication","Company PIN","Device Groups","Custom Fields for Devices","Lifecycle Status","Data Collection","Domain Name","Company Networks","Incidents","Reports","Third Party Software Integration","Poly Lens Integration"};
		String[] rolesAndPermission = {"Roles"};
		String[] planItems = {"Subscriptions","Licenses","Care Pack","License Details"};
		String[] assignedPartner = {"Assignment Settings"};
		String[] notificationMenus = {"Device Notifications","Self-Service Alerts"};
		// Verifies labels on Overview tab
		Assert.assertTrue(dashboardPage.verifyWidgetsDisplayedForActiveCare("overviewlocator", overViewLabels), "Overview Page is missing some items");
		// added sleep waiting for the elements on the page to load as other methods did not work
		//Verify Location tab
		settingsPage.clickOnElementsOfSettingsPage("locationsTab");
		sleeper(2000);   
		Assert.assertTrue(dashboardPage.verifyWidgetsDisplayedForActiveCare("settingsMenuList", locations), "Location Page is missing some items");
		
		// Verifies labels on Preference tab
		goToPreferencesTab();
		sleeper(2000); 
		Assert.assertTrue(dashboardPage.verifyWidgetsDisplayedForActiveCare("settingsMenuList", preferenceAnchorMenu), "Preference Page is missing some items");
						
		//Verifies Roles and Permission tab
		settingsPage.clickOnElementsOfSettingsPage("roleAndPermissionTabCompany");
		sleeper(2000);
		Assert.assertTrue(dashboardPage.verifyWidgetsDisplayedForActiveCare("settingsMenuList", rolesAndPermission), "Roles Page is missing some items");
		
		//Plan
		settingsPage.clickOnElementsOfSettingsPage("subscriptionTab");
		sleeper(2000);
		Assert.assertTrue(dashboardPage.verifyWidgetsDisplayedForActiveCare("settingsMenuList", planItems), "Plan Page is missing some items");	
		
		//AssignedPartner
		settingsPage.clickOnElementsOfSettingsPage("assignedPartnerTab");
		Assert.assertTrue(dashboardPage.verifyWidgetsDisplayedForActiveCare("settingsMenuList", assignedPartner), "Assigned Partner Page is missing some items");	
		
		// Notifications
		settingsPage.clickOnElementsOfSettingsPage("notificationsTab");
		sleeper(2000);
		Assert.assertTrue(dashboardPage.verifyWidgetsDisplayedForActiveCare("settingsMenuList", notificationMenus), "Notification Page is missing some items");
		LOGGER.info("\"Validation of Settings for AC + PI plan completed successfully.\"");
		softAssert.assertAll();	

	}
	
	
	// This method verifies menu under Settings and their sub-menus for AC only tenant
	@Test(priority = 11, groups = {"ACTIVECARESANITY", "REGRESSIONSETTINGS2", "REGRESSION_CI",
	"STABILIZATION_STAGING" })
	public final void verifySettingsforAC() throws Exception {
		login("ACTIVECARETENANT_EMAIL", "ACTIVECARETENANT_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		waitForPageLoaded();
		gotoCompanySettingsTab();
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		String[] overViewLabels = {"Customer ID" , "Company Name" , "Address" , "Preferred Time Zone" , "Company Size" , "Primary Administrator" , "Source Of Customer", "Created On"};
		String[] preferenceAnchorMenu = {"Archived Devices","Authentication","Device Groups","Custom Fields for Devices","Lifecycle Status","Data Collection","Domain Name","Third Party Software Integration","Poly Lens Integration"};
		String[] rolesAndPermission = {"Roles"};
		String[] planItems = {"Subscriptions","Licenses","Care Pack"};
		String[] notificationMenus = {"Device Notifications","Self-Service Alerts"};
		// Verifies labels on Overview tab
		Assert.assertTrue(dashboardPage.verifyWidgetsDisplayedForActiveCare("overviewlocator", overViewLabels), "Overview Page is missing some items");
		
		// Verifies labels on Preference tab
		goToPreferencesTab();
		waitForPageLoaded();
		Assert.assertTrue(dashboardPage.verifyWidgetsDisplayedForActiveCare("settingsMenuList", preferenceAnchorMenu), "Preference Page is missing some items");
		// added sleep waiting for the elements on the page to load as other methods did not work
		//Verifies Roles and Permission tab
		settingsPage.clickOnElementsOfSettingsPage("roleAndPermissionTabCompany");
		sleeper(2000);
		Assert.assertTrue(dashboardPage.verifyWidgetsDisplayedForActiveCare("settingsMenuList", rolesAndPermission), "Roles Page is missing some items");
		
		//Plan
		settingsPage.clickOnElementsOfSettingsPage("subscriptionTab");
		sleeper(2000);
		Assert.assertTrue(dashboardPage.verifyWidgetsDisplayedForActiveCare("settingsMenuList", planItems), "Plan Page is missing some items");	
		
		// Notifications
		settingsPage.clickOnElementsOfSettingsPage("notificationsTab");
		sleeper(2000);
		Assert.assertTrue(dashboardPage.verifyWidgetsDisplayedForActiveCare("settingsMenuList", notificationMenus), "Plan Page is missing some items");
		LOGGER.info("Validation of Settings for AC plan completed successfully.");
		softAssert.assertAll();	

	}
	
	
	// This Test will verify ActiveCare Tenant creation using Root user -> Add Company flow.
	@Test(priority = 12, groups = {"ACTIVECARESANITY", "REGRESSIONSETTINGS2", "REGRESSION_CI",
			"STABILIZATION_STAGING" }, description = "Test Case ID : 1274300")
	public final void verifyACTenantCreationUI() throws Exception {
		login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		CompaniesDetailsPage companiesDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		waitForPageLoaded();
		LOGGER.info("Root user logged in successfully.");
		gotoRootCompaniesTab();
		LOGGER.info("Navigate to Company tab.");
		// Company related test data
		String company_name = CommonVariables.AC_COMPANY_NAME + generateRandomString(5);
		String company_email = company_name.toLowerCase() + "@hpmsqa.mailinator.com";
		String CompanyCountry =  getEnvironmentSpecificData(System.getProperty("environment"), "COMPANY_COUNTRY");
		String CompanyPlan = CommonVariables.PLAN_ACTIVE_CARE;
		String CompanyAddressLine1 =CommonVariables.STREET_ADDRESS;
		String CompanyCity =CommonVariables.CITY;
		String CompanyState =CommonVariables.STATE;
		String CompanyZip =CommonVariables.ZIP_CODE;
		Assert.assertTrue(companiesPage.addCompanies(LanguageCode, company_name, company_email, CompanyCountry, CompanyPlan, CommonVariables.MSP_NAME, getEnvironmentSpecificData(environment, "ASSIGNED_PARTNER_NAME_ADD"), "FN", "LN", false, false, true), "Company not added successfully through Root admin.");
		LOGGER.info("PASS: Root user can create Active Care Tenant successfully.");
		
		HashMap<String, String> companyDetails = new HashMap<String, String>();
		companyDetails.put("CompanyName", company_name);
		companyDetails.put("ITAdminEmail", company_email);
		companyDetails.put("CompanyCountry", CompanyCountry);
		companyDetails.put("CompanyAddressLine1", CompanyAddressLine1);
		companyDetails.put("CompanyAddressState", CompanyState);
		companyDetails.put("CompanyAddressCityZip", CompanyCity+" - "+ CompanyZip);
		companyDetails.put(CompanyPlan, "0");
		LOGGER.info("Validate Company created details with test data.");
		softAssert.assertTrue(companiesDetailsPage.verifyTenantDetailsCreated(LanguageCode, companyDetails),"FAIL: Company details verification failed.");
		softAssert.assertAll();
	}
	
	
	// This method is to verify if Active care is not displayed change plan modal for device with AC and other license based plan
	@Test(priority = 13, groups = {"ACTIVECARESANITY", "REGRESSIONSETTINGS2", "SMOKE", "REGRESSION_CI", "SMOKE_CI",
	"STABILIZATION_STAGING"}, description = "[1277752]")
	public final void verifyChangePlanForActiveCare() throws Exception {
		DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
		login("ACPITENANT_EMAIL", "ACPITENANT_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		//Assert assert = new Assert();
		waitForPageLoaded();
		gotoDevicesTab();
		deviceListPage.waitForElementsOfDeviceListPage("selectAllCheckBox");
		deviceListPage.clickByJavaScriptOnDeviceListPage("selectAllCheckBox");
		// Check Active Care plan is not displayed on change plan modal for device having AC as one of the plans
		if(deviceListPage.verifyElementsOfDeviceListPage("morebutton")) {
			deviceListPage.clickByJavaScriptOnDeviceListPage("morebutton");
			deviceListPage.waitForElementsOfDeviceListPage("changePlanButton");
			deviceListPage.clickOnElementsOfDeviceListPage("changePlanButton");
		}
		else {
				deviceListPage.verifyElementsOfDeviceListPage("changePlan");
				deviceListPage.clickOnElementsOfDeviceListPage("changePlan");		
		}
		deviceListPage.clickOnElementsOfDeviceListPage("planSelectionDropdownOnChangePlans");
		deviceListPage.waitForElementsOfDeviceListPage("planSelectionDropdownListOnChangePlans");
		Assert.assertFalse(deviceListPage.verifyIsActiveCarePlanDisplayedonChangePlanModal("planSelectionDropdownListOnChangePlans", "SettingsVariables.ACTIVE_CARE_PLAN_NAME"),"HP Active Care Plan Name is not displayed");
		LOGGER.info("Validation of HP Active Care Plan Name not getting displayed on change plan modal is completed ");	
		softAssert.assertAll();			
		
	}
	
	
	// This Test will verify if AC+PI tenant is able to assign partner to the company
	@Test(priority = 14, groups = {"ACTIVECARESANITY", "REGRESSIONSETTINGS2", "REGRESSION_CI",
	"STABILIZATION_STAGING" }, description = "Test Case ID : 1176671")
	public final void verifyACPITenantPartnerAssignment() throws Exception {
		login("ACPITENANT_EMAIL", "ACPITENANT_PASSWORD");
		waitForPageLoaded();
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		
		SoftAssert softAssert = new SoftAssert();
		gotoCompanySettingsTab();
		LOGGER.info("Redirected to Settings tab");
		waitForPageLoaded();
		companiesPage.waitForPresenceOfElementsOfCompaniesPage("assignedPartner");
		companiesPage.clickOnElementsOfCompaniesPage("assignedPartner");
		LOGGER.info("Clicked on assign partner tab");
		softAssert.assertTrue(settingsPage.getTextOfSettingsPage("assignedPartnerTitle").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.tile.assignment_settings")), "Header on assign partner tile is incorrect");
		softAssert.assertTrue(settingsPage.getTextOfSettingsPage("unassignedPartnerField").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.label.not_assigned")), "Unassigned partner field value on assign partner tile is incorrect");
		softAssert.assertTrue(settingsPage.matchTextOfSettingsPage("unassignedPartnerField", getTextLanguage(LanguageCode, "daas_ui", "companies.details.label.not_assigned")), "Unassigned partner field value on assign partner tile is incorrect");
		softAssert.assertTrue(settingsPage.verifyElementIsClickableOfSettingsPage("editAssignedpartnerButton"), "Invite Parnet button is not visible for AC+PI tenant.");
		
		softAssert.assertAll();
	}
	
	
	// This Test will verify Active care tenant should not able to assign partner to the company
	@Test(priority = 15, groups = {"ACTIVECARESANITY", "REGRESSIONSETTINGS2", "REGRESSION_CI",
	"STABILIZATION_STAGING" }, description = "Test Case ID : 1176671")
	public final void verifyACTenantRemovePartnerAssignment() throws Exception {
		login("ACTIVECARETENANT_EMAIL", "ACTIVECARETENANT_PASSWORD");
		waitForPageLoaded();
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		
		SoftAssert softAssert = new SoftAssert();
		gotoCompanySettingsTab();
		LOGGER.info("Redirected to Settings tab");
		waitForPageLoaded();
		companiesPage.waitForPresenceOfElementsOfCompaniesPage("assignedPartner");
		companiesPage.clickOnElementsOfCompaniesPage("assignedPartner");
		LOGGER.info("Clicked on assign partner tab");
		softAssert.assertTrue(settingsPage.getTextOfSettingsPage("assignedPartnerTitle").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.tile.assignment_settings")), "Header on assign partner tile is incorrect");
		softAssert.assertTrue(settingsPage.getTextOfSettingsPage("unassignedPartnerField").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.label.not_assigned")), "Unassigned partner field value on assign partner tile is incorrect");
		softAssert.assertTrue(settingsPage.matchTextOfSettingsPage("unassignedPartnerField", getTextLanguage(LanguageCode, "daas_ui", "companies.details.label.not_assigned")), "Unassigned partner field value on assign partner tile is incorrect");
		softAssert.assertTrue(settingsPage.waitForElementsOfSettingsPageForinvisibile("editAssignedpartnerButton"), "Invite Parnet button is visible for ActiveCare tenant.");
		
		softAssert.assertAll();
	}
	
	
	@Test(priority = 16, groups = {"ACTIVECARESANITY", "REGRESSIONSETTINGS2", "REGRESSION_CI",
	"STABILIZATION_STAGING" })
	public final void verifyReactiveCaseCreationFunctionality() throws Exception {
		login("ACTIVECARETENANT_EMAIL", "ACTIVECARETENANT_PASSWORD");
		waitForPageLoaded();
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
		DeviceListPage devicelistPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
		
		SoftAssert softAssert = new SoftAssert();
		gotoDevicesTab();
		waitForPageLoaded();
		Assert.assertTrue(devicelistPage.waitForElementsOfDeviceListPage("serialNumberList"), "Fail: There are no devices in this Tenant.");
		sleeper(300);
		devicelistPage.clearFiltersOfDevicesListPage("clearfilter");
		devicelistPage.clickOnElementsOfDeviceListPage("statusFilter");
		devicelistPage.selectTextValueFromDropdownOfDeviceListPage("statusdropdownValues", "Active", "statusFilter");
		sleeper(500);
		Assert.assertTrue(devicelistPage.waitForElementsOfDeviceListPage("serialNumberList"), "Fail: There are no Active devices in this Tenant.");
		
		String serialnumber = devicelistPage.getAttributesOfDeviceListPage("firstDeviceSerialNumber", "title");
		refreshPage();
		waitForPageLoaded();
		gotoDashboardTab();
		
		settingsPage.createReactiveCase(serialnumber);
		sleeper(3000);
		gotoIncidentTab();
		waitForPageLoaded();
		sleeper(3000);
		settingsPage.clearFiltersOfRolesandPermissionListPage("clearAllFilter");
		settingsPage.enterTextForSettingsPage("deviceSerialNumberSearchBox", serialnumber);
		settingsPage.clickOnElementsOfSettingsPage("typeBox"); 
		settingsPage.selectTextValueFromDropdownOfSettingsPage("typeListOptions",getTextLanguage(LanguageCode, "daas_ui", "com.hp.mpi.icm.type.activecare") , "typeBox");
		pressKey(Keys.ESCAPE);
		settingsPage.clickOnElementsOfSettingsPage("subTypeBox"); 
		settingsPage.selectTextValueFromDropdownOfSettingsPage("subTypeList",getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.subtype.battery"), "subTypeBox");
		pressKey(Keys.ESCAPE);
	
		settingsPage.clickByJavaScriptOnSettingsPage("selectedIncidentTitle");
		LOGGER.info("Redirected to incident details page");
		sleeper(3000);
		settingsPage.waitForElementsOfSettingsPage("caseInformationTileTitle");
		settingsPage.scrollTillViewSettingsPage("caseInformationTileTitle");
		softAssert.assertTrue(settingsPage.getTextOfSettingsPage("caseInformationTileTitle").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "incident.details.section.case.information").toUpperCase()), "Title on case information tile title is incorrect");
		softAssert.assertTrue(settingsPage.getTextOfSettingsPage("caseIDLabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "incidents.caseID")), "Case ID label on case information tile title is incorrect");
		softAssert.assertTrue(settingsPage.getTextOfSettingsPage("caseStatusLabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "incident.list.caseStatus")), "Case status label on case information tile title is incorrect");
		softAssert.assertTrue(settingsPage.getTextOfSettingsPage("caseSubmissionDate").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "incident.details.section.case.submission")), "Case submission value on case information tile title is incorrect");
		softAssert.assertTrue(settingsPage.getTextOfSettingsPage("caseLocation").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "incident.details.section.case.location")), "Case location label on case information tile title is incorrect");
		softAssert.assertTrue(settingsPage.getTextOfSettingsPage("caseContact").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "incident.details.section.case.contact")), "Case contact label on case information tile title is incorrect");
		softAssert.assertAll();
	}
	
	
	@Test(priority = 17, groups = {"ACTIVECARESANITY", "REGRESSIONSETTINGS2", "SMOKE", "REGRESSION_CI", "SMOKE_CI",
	"STABILIZATION_STAGING"})
	public final void verifyProactiveCaseCreation() throws Exception {
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
		DeviceListPage devicelistPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
		DeviceDetailsPage deviceDetailPage = new DeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		waitForPageLoaded();
				
		//String serialnumber = settingsPage.getCSVFieldValue("ObjectOfServiceSerialNumber", "ORDERS_EXISTING_AC_COMPANY");
		login("ACTIVECARETENANT_EMAIL", "ACTIVECARETENANT_PASSWORD");
		String tenantID = getValueFromToken("tenant");
		gotoDevicesTab();
		Assert.assertTrue(devicelistPage.waitForElementsOfDeviceListPage("serialNumberList"), "Fail: There are no devices in this Tenant.");
//		devicelistPage.selectElementFromDropDownOfDeviceListPage("statusFilter", "statusdropdownValues", "Active");
		Assert.assertTrue(devicelistPage.applyFilterDeviceListPageOnStatus("statusFilter", "statusdropdownValues", "Active"), "FAIL: Status Filter is not applied on Device list page.");
		sleeper(500);
		Assert.assertTrue(devicelistPage.waitForElementsOfDeviceListPage("serialNumberList"), "Fail: There are no Active devices in this Tenant.");
		
		String serialnumber = devicelistPage.getAttributesOfDeviceListPage("firstDeviceSerialNumber", "title");
		devicelistPage.clickByJavaScriptOnDeviceListPage("firstDeviceInList");
		deviceDetailPage.waitForElementsOfDeviceDetailsPage("deviceDetailsTitle");
		sleeper(3000);
		
		LOGGER.info("Redirected to Device details page");
		// Defect is raised for the same.
		//String planName = deviceDetailPage.getTextOfDeviceDetailsPage("deviceDetailsEnrolledPlans");
		//softAssert.assertTrue(planName.equals(
		//		getTextLanguage(LanguageCode, "daas_ui", "services.title.hp_care_pack")), "Plan is not Active Care before enrollment.");
		
		String deviceID = deviceDetailPage.getDeviceIDfromURL();
		gotoIncidentTab();
		sleeper(3000);
		
		String type = getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.type.hardwarehealth");
		String subtype = getTextLanguage(LanguageCode, "daas_ui", "incident.subtypes.hdd_predictive_failure");
		String incidentTitle = "Test Incident for enrolled device";
		String incidentDescription = "Description for Test Incident for enrolled device";
		gotoIncidentTab();
		waitForPageLoaded();
		resetTableConfiguration();
		settingsPage.clearFiltersOfRolesandPermissionListPage("clearAllFilter");
		String incidentId = settingsPage.submitCaseUsingAPICaseCreation(type, subtype, incidentTitle, incidentDescription, tenantID, deviceID);
		settingsPage.enterTextForSettingsPage("idSearchBox", incidentId);
		sleeper(3000);
		settingsPage.clickByJavaScriptOnSettingsPage("selectedIncidentTitle");
		LOGGER.info("Redirected to incident details page");
		sleeper(3000);
		softAssert.assertAll();

	}

	
		
	@Test(priority = 18, groups = {"ACTIVECARESANITY", "REGRESSIONSETTINGS2", "SMOKE", "REGRESSION_CI", "SMOKE_CI",
	"STABILIZATION_STAGING"})
	    public final void verifySidePanelForActiveCareOnlyTenant() throws Exception {
	        login("ACTIVECARETENANT_EMAIL", "ACTIVECARETENANT_PASSWORD");
	        SoftAssert softAssert = new SoftAssert();
	        SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
			ArrayList<String> expectedActiveCareMenuOptionsList = new ArrayList<String>(
			Arrays.asList((getTextLanguage(LanguageCode, "daas_ui", "sidemenu.dashboard")),
						(getTextLanguage(LanguageCode, "daas_ui","sidemenu.incidents")),
						(getTextLanguage(LanguageCode, "daas_ui","sidemenu.devices")),
						(getTextLanguage(LanguageCode, "daas_ui","sidemenu.users")),
						(getTextLanguage(LanguageCode, "daas_ui","sidemenu.logs")),
						(getTextLanguage(LanguageCode, "daas_ui","sidemenu.settings")),
						(getTextLanguage(LanguageCode, "daas_ui","sidemenu.support")),
						(getTextLanguage(LanguageCode, "daas_ui","sidemenu.whatsNew"))));
			softAssert.assertTrue(settingsPage.verifyLeftSideMenuActiveCareService(expectedActiveCareMenuOptionsList,"activeCareSideMenuOption"), "Error occured in validation of left side panel for premier care .");
	        LOGGER.info("left side panel validation for Active Care Successful");
	        softAssert.assertAll();
	}
	
	
	// As per new CarePack service this Test case is Invalid. Hence marked as disabled. Use new test case verifyCPImportCSVOrdersPage() instead.
	@Test(priority = 19, groups = {"ACTIVECARESANITY", "REGRESSIONSETTINGS2", "SMOKE", "REGRESSION_CI", "SMOKE_CI",
			"STABILIZATION_STAGING"}, enabled=true)
	public final void MakeACDeviceActiveFromPendingEnrollmentTab() throws Exception {
		
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
		DeviceListPage devicelistPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
		DeviceDetailsPage deviceDetailPage = new DeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
		EnrollFakeDevice enrollFakeDevice = new EnrollFakeDevice();
		SoftAssert softAssert = new SoftAssert();

		login("ACTIVECARETENANT_EMAIL", "ACTIVECARETENANT_PASSWORD");
		waitForPageLoaded();
		String tenantID = getValueFromToken("tenant");
		gotoPendingEnrollmentTab();
		waitForPageLoaded();
		Assert.assertTrue(devicelistPage.waitForElementsOfDeviceListPage("serialNumberList"), "FAIL: No devices are found in Pending Enrollment tab.");

		// Select 1 device from the list and click to open Device details page.
		devicelistPage.clickByJavaScriptOnDeviceListPage("firstDeviceInList");
		deviceDetailPage.waitForElementsOfDeviceDetailsPage("deviceDetailsTitle");
		sleeper(3000);

		LOGGER.info("User navigates to Device details page");
		
		// fetch deviceID from DeviceDetials page URL.
		String deviceID = deviceDetailPage.getDeviceIDfromURL();
		String serialnumber = deviceDetailPage.getTextOfDeviceDetailsPage("serialNumber");
		sleeper(3000);
		softAssert.assertTrue(enrollFakeDevice.enrollIOTFakeDevice(getEnvironmentSpecificData(System.getProperty("environment"), "ACOnlyTenantName_US"), 
				getEnvironmentSpecificData(System.getProperty("environment"), "ACOnlyTenantCPIN_US"), getCredentials(environment, "ACTIVECARETENANT_EMAIL"), serialnumber, deviceID, tenantID), 
				"Device with serial number "+ serialnumber + " is not enrolled successfully.");
		sleeper(3000);
		softAssert.assertAll();
		}
	
	
	@Test(priority = 20, groups = {"ACTIVECARESANITY", "REGRESSIONSETTINGS2", "SMOKE", "REGRESSION_CI", "SMOKE_CI",
	"STABILIZATION_STAGING" }, description = "Test Case ID : 1086749")
	public final void verifyCSVUploadCompanyNameGreater255() throws Exception {
		login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
		
		DeviceListPage devicelistPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
		
		SoftAssert softAssert = new SoftAssert();
		waitForPageLoaded();
		// update CSV file with unique Serial number
		String[] columnValueToUpdateRandomData = {"ObjectOfServiceSerialNumber"};
		settingsPage.updateActiveCareCSVFieldValue("ORDERS_GREATER_THAN_255", columnValueToUpdateRandomData);
				
		gotoOrderTab();
		LOGGER.info("Navigated to orders page");
		waitForPageLoaded();
		softAssert.assertTrue(
				settingsPage.matchTextOfSettingsPage("addOrderTitle",
						getTextLanguage(LanguageCode, "daas_ui", "orders.add.orders.title")),
				"Title on add orders popup is incorrect");
		softAssert.assertTrue(
				settingsPage.matchTextOfSettingsPage("addOrderSubtitle",
						getTextLanguage(LanguageCode, "daas_ui", "orders.add.orders.sub.title")),
				"Subtitle on add order popup is incorrect");
		softAssert.assertTrue(
				settingsPage.matchTextOfSettingsPage("addOrderDescription",
						getTextLanguage(LanguageCode, "daas_ui", "orders.add.orders.sub.discription")),
				"Description on add orders popup is incorrect");
		softAssert.assertTrue(settingsPage.verifyElementIsClickableOfSettingsPage("downloadSampleFileLink"));
		softAssert.assertTrue(settingsPage.verifyElementsOfSettingsPage("cancelOrderButton"));
		softAssert.assertTrue(settingsPage.verifyElementsOfSettingsPage("importOrderButton"));
		settingsPage.verifyImportOrders(LanguageCode,
				getEnvironmentSpecificData(System.getProperty("environment"), "ORDERS_GREATER_THAN_255"));
		// sleeper(2000);
		sleeper(20000);
		Assert.assertTrue(
				settingsPage.postNotificationCheckImportInV3(
						getEnvironmentSpecificData(System.getProperty("environment"), "ORDERS_GREATER_THAN_255")),
				"Message on notification window is incorrect");
		LOGGER.info("Notification message verification for import has passed");
		
		logout();
		// Login to ActiveCare HoldingTank Tenant.
		login("HT_TENANT_USER_EMAIL", "HT_TENANT_USER_PASSWORD");
		waitForPageLoaded();
		// Navigate to Devices tab -> Pending Enrollment Tab.
		gotoPendingEnrollmentTab();
		waitForPageLoaded();
		String[] serialNumbers = settingsPage.getCSVFieldValue("ObjectOfServiceSerialNumber", "ORDERS_GREATER_THAN_255");
		for(int i=0; i<serialNumbers.length; i++) 
		{			
			devicelistPage.enterTextForDeviceListPage("serialNumberSearchBox",serialNumbers[i]);
			sleeper(1000);
			softAssert.assertTrue(devicelistPage.waitForElementsOfDeviceListPage("serialNumberList"), "FAIL: Searched device is not found in Device list page.");
		}
		
		softAssert.assertAll();
		}
	
	
	@Test(priority = 21, groups = {"ACTIVECARESANITY",  "REGRESSIONSETTINGS2", "SMOKE", "REGRESSION_CI", "SMOKE_CI",
	"STABILIZATION_STAGING" }, description = "Test Case ID : 1086740", enabled=false)
		public final void verifyCSVUploadCompanyNameGreaterThan65LessThan255() throws Exception {
		login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		waitForPageLoaded();
		
		gotoOrderTab();
		LOGGER.info("Navigated to orders page");
		waitForPageLoaded();
		softAssert.assertTrue(
				settingsPage.matchTextOfSettingsPage("addOrderTitle",
						getTextLanguage(LanguageCode, "daas_ui", "orders.add.orders.title")),
				"Title on add orders popup is incorrect");
		softAssert.assertTrue(
				settingsPage.matchTextOfSettingsPage("addOrderSubtitle",
						getTextLanguage(LanguageCode, "daas_ui", "orders.add.orders.sub.title")),
				"Subtitle on add order popup is incorrect");
		softAssert.assertTrue(
				settingsPage.matchTextOfSettingsPage("addOrderDescription",
						getTextLanguage(LanguageCode, "daas_ui", "orders.add.orders.sub.discription")),
				"Description on add orders popup is incorrect");
		softAssert.assertTrue(settingsPage.verifyElementIsClickableOfSettingsPage("downloadSampleFileLink"));
		softAssert.assertTrue(settingsPage.verifyElementsOfSettingsPage("cancelOrderButton"));
		softAssert.assertTrue(settingsPage.verifyElementsOfSettingsPage("importOrderButton"));
		settingsPage.verifyImportOrders(LanguageCode,
				getEnvironmentSpecificData(System.getProperty("environment"), "ORDERS_LESS_THAN_255"));
		//sleeper(2000);
		sleeper(20000);
		Assert.assertTrue(
				settingsPage.postNotificationCheckImportInV3(
						getEnvironmentSpecificData(System.getProperty("environment"), "ORDERS_LESS_THAN_255")),
				"Message on notification window is incorrect");
		LOGGER.info("Notification message verification for import has passed");
		
		//companiesPage.enterTextForCompaniesPage("companyNameSearch",
		//		settingsPage.getCSVFieldValue("EndCustomerName", "ORDERS_LESS_THAN_255"));
		sleeper(3000);
		
		//verifying result of the company search
		softAssert.assertTrue(companiesPage.verifyElementsOfCompaniesPage("companiesSearchResult"));
		
		softAssert.assertAll();
		}
	
	
		@Test(priority = 22, groups = { "REGRESSIONSETTINGS2", "SMOKE", "REGRESSION_CI",
		"SMOKE_CI" }, description = "Test Case:1071431", enabled = true)
		public final void verifyCaseCreateButtonIsDisabledWhenCaseExists() throws Exception {
		
		login("ACTIVECARETENANT_EMAIL", "ACTIVECARETENANT_PASSWORD");
		waitForPageLoaded();
		IncidentPage incidentPage = new IncidentPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		gotoIncidentTab();
		waitForPageLoaded();
		resetTableConfiguration();
		incidentPage.enterTextForIncidentPage("idSearchBox",
				getEnvironmentSpecificData(System.getProperty("environment"), "INCIDENT_ID_PRE_EXISTING_CASE"));
		
		sleeper(3000);
		incidentPage.clickByJavaScriptOnIncidentPage("clickIncidentCheckbox");
		if(incidentPage.verifyElementIsVisible("sideBarCloseButton")) {
			incidentPage.clickByJavaScriptOnIncidentPage("sideBarCloseButton");
		}
	
		sleeper(1000);
		incidentPage.mousehoverOnIncientPage("createCaseDisableButton");
		softAssert.assertTrue(incidentPage.verifyElementPresentOnIncidentListPage("createCaseAlreadyExistTooltip"),
				"Message not displayed");
		LOGGER.info("Verification completed for Case creation not allowed for incidents having prexisting case");
		softAssert.assertAll();

		}
		
		
		@Test(priority = 23, groups = {"ACTIVECARESANITY", "REGRESSIONSETTINGS2", "SMOKE", "REGRESSION_CI",
		"SMOKE_CI" }, description = "Test Case:1062513 , 1062646 , 1062508", enabled = true)
		public final void verifyCaseCreateButtonIsDisabledOnIncidentDetailPage() throws Exception {

			login("ACTIVECARETENANT_EMAIL", "ACTIVECARETENANT_PASSWORD");
			waitForPageLoaded();
			IncidentPage incidentPage = new IncidentPage(PreDefinedActions.getDriver()).getInstance();
			SoftAssert softAssert = new SoftAssert();
			gotoIncidentTab();
			waitForPageLoaded();
			resetTableConfiguration();
			incidentPage.enterTextForIncidentPage("idSearchBox", getEnvironmentSpecificData(System.getProperty("environment"), "INCIDENT_ID_INVALID_TYPE"));

			sleeper(3000);
			incidentPage.clickByJavaScriptOnIncidentPage("clickIncidentCheckbox");

			incidentPage.clickByJavaScriptOnIncidentPage("sideBarCloseButton");

			sleeper(1000);
			incidentPage.clickByJavaScriptOnIncidentPage("createCaseDisableButton");
			incidentPage.mousehoverOnIncientPage("createCaseDisableButton");
			sleeper(1000);
//			softAssert.assertTrue(incidentPage.verifyElementPresentOnIncidentListPage("createCaseInvalidTypeMessageLabel"),
//					"Message not displayed");
			softAssert.assertTrue(incidentPage.matchTextOfIncidentPage("createCaseInvalidTypeMessageLabel", 
					"Cases can only be created for \"Battery Needs Attention\" or \"HDD Predictive Failure\" at this time"),
					"Message not displayed");
			LOGGER.info(
					"Verification completed for Case creation not allowed for any incidents other than HDD Predictive Failure/Battery Needs Attention");

			/*
			 * Verification of case creation tool tip when multiple incidents are
			 * selected
			 */

			incidentPage.clickByJavaScriptOnIncidentPage("clearFiltersButtonNew");
			incidentPage.waitForElementsOfIncidentPage("clickIncidentCheckbox");
			sleeper(1000);
			incidentPage.clickByJavaScriptOnIncidentPage("clickIncidentCheckbox");
			sleeper(1000);
			incidentPage.clickByJavaScriptOnIncidentPage("clickIncidentCheckboxSecondRow");
			incidentPage.clickByJavaScriptOnIncidentPage("sideBarCloseButton");
			sleeper(1000);
			incidentPage.mousehoverOnIncientPage("createCaseDisableButton");
			incidentPage.clickByJavaScriptOnIncidentPage("createCaseDisableButton");
			sleeper(1000);
//			softAssert.assertTrue(
//					incidentPage.verifyElementPresentOnIncidentListPage("createCaseInvalidMultipleDeviceLabel"),
//					"Message not displayed");
			softAssert.assertTrue(
					incidentPage.matchTextOfIncidentPage("createCaseInvalidMultipleDeviceLabel", "Only one case can be created at a time"),
					"Message not displayed");

			LOGGER.info("Verification completed for Case creation not allowed for multiple incidents at same time");

			softAssert.assertAll();

		}
		
		
		@Test(priority = 24, groups = {"ACTIVECARESANITY", "REGRESSIONSETTINGS2", "SMOKE", "REGRESSION_CI",
		"SMOKE_CI" }, description = "Test Case:1061132", enabled = true)
		public final void verifyCaseCreateButtonIsNotDisplayedOnDeviceDetailPage() throws Exception {

			login("ACTIVECARETENANT_EMAIL", "ACTIVECARETENANT_PASSWORD");
			waitForPageLoaded();
			DeviceListPage devicelistPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
			DeviceDetailsPage deviceDetailPage = new DeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();

			SoftAssert softAssert = new SoftAssert();
			gotoDevicesTab();
			waitForPageLoaded();
			resetTableConfiguration();

//			devicelistPage.enterTextForDeviceListPage("serialNumberSearchBox",
//					getEnvironmentSpecificData(System.getProperty("environment"), "DEVICE_SERIAL_NO"));
			devicelistPage.waitForElementsOfDeviceListPage("serialNumberList");

			devicelistPage.clickByJavaScriptOnDeviceListPage("firstDeviceInList");
			deviceDetailPage.waitForElementsOfDeviceDetailsPage("deviceDetailsTitle");
			sleeper(3000);

			LOGGER.info("Redirected to Device details page");

			softAssert.assertFalse(deviceDetailPage.verifyElementsOfDeviceDetailsPage("deviceDetailsCreateCaseButton"),
					"Case Create button is displayed on Device Details page");
			softAssert.assertAll();

		}
		
		
		@Test(priority = 25, groups = { "REGRESSIONSETTINGS2", "REGRESSION_CI",
		"STABILIZATION_STAGING" }, description = "TEST CASE 1062486", enabled=false)
		public final void verifyCaseCreationDisabledForInvalidIncidentTypesOnIncidentDetails() throws Exception {
			login("AUTOMATION_EMAIL_CASE", "AUTOMATION_PASSWORD_CASE");
			waitForPageLoaded();
			SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
			IncidentDetailsPage incidentDetailsPage = new IncidentDetailsPage(PreDefinedActions.getDriver()).getInstance();
			SoftAssert softAssert = new SoftAssert();
			gotoIncidentTab();
			waitForPageLoaded();
			resetTableConfiguration();
			settingsPage.enterTextForSettingsPage("idSearchBox",
					getEnvironmentSpecificData(System.getProperty("environment"), "INCIDENT_ID_ACTIVE_CARE_POWER"));
			sleeper(3000);
			settingsPage.clickByJavaScriptOnSettingsPage("selectedIncidentTitle");
			LOGGER.info("Redirected to incident details page");
			sleeper(3000);
			softAssert.assertTrue(incidentDetailsPage.verifyElementOfIncidentDetailsPage("createCaseButtonDisabled"),
					"Case Create button is not disabled");
			incidentDetailsPage.mousehoverOnIncidentDetailsPage("createCaseButtonDisabled");
			softAssert.assertEquals(
					incidentDetailsPage.getAttributesOfIncidentDetailPage("createCaseButtonDisabled", "tooltip"),
					(settingsPage.getTextLanguage(LanguageCode, "daas_ui", "create.case.invalid.type")),
					"Text on case creation button disbaled is not correct");
			softAssert.assertAll();

			LOGGER.info("Case creation Button disabled for invalid types verified successfully");
		}
		
		
		@Test(priority = 26, groups = {"REGRESSION_CI"}, description = "TEST CASE 920939")
		public final void verifyDisableTabsForAC() throws Exception {
			login("ACTIVECARETENANT_EMAIL", "ACTIVECARETENANT_PASSWORD");
			SettingsPage settingPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
			waitForPageLoaded();
			sleeper(2000);
			Assert.assertFalse(settingPage.getTextOfListOfSettingsPage("listOfTabs").contains(getTextLanguage(LanguageCode, "daas_ui", "pagetitle.benchmarks").toLowerCase()),"Benchmark tab is present for Active Care tenant");
			Assert.assertFalse(settingPage.getTextOfListOfSettingsPage("listOfTabs").contains(getTextLanguage(LanguageCode, "daas_ui", "sidemenu.reports").toLowerCase()),"Reports tab is present for Active Care tenant");
			Assert.assertFalse(settingPage.getTextOfListOfSettingsPage("listOfTabs").contains(getTextLanguage(LanguageCode, "daas_ui", "sidemenu.campaign").toLowerCase()),"Campaign tab is present for Active Care tenant");
			LOGGER.info("Test case verifyDisableTabsForAC passed successfully");
		}
		
		
		@Test(priority = 27, groups = { "ACTIVECARESANITY", "REGRESSIONSETTINGS2", "SMOKE", "REGRESSION_CI", "SMOKE_CI", "STABILIZATION_STAGING" }, description = "Test Case ID : 355185;355257", enabled=true)
		public final void verifyShippingLocationsTile() throws Exception {
			login("ACTIVECARETENANT_EMAIL", "ACTIVECARETENANT_PASSWORD");
			SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
			DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();	
			SoftAssert softAssert = new SoftAssert();
			String addressLine1 = generateRandomString(7);
			String addressLine2 = generateRandomString(7);
			String city = generateRandomString(7);
			String state = generateRandomString(7);
			String zipcode = generateRandomString(7);
			String firstName = generateRandomString(7);
			String lastName = generateRandomString(7);
			String email = generateRandomString(7) + "@mailinator.com";
			String phone = generateRandomNumber();
			String instructions = generateRandomString(7);
			ArrayList<String> locationHeaders = new ArrayList<String>(Arrays.asList(getTextLanguage(LanguageCode, "daas_ui", "shipping.locations.list.address").toLowerCase(),getTextLanguage(LanguageCode, "daas_ui", "shipping.locations.list.city").toLowerCase(),getTextLanguage(LanguageCode, "daas_ui", "shipping.locations.list.state").toLowerCase(),getTextLanguage(LanguageCode, "daas_ui", "shipping.locations.list.country").toLowerCase(),getTextLanguage(LanguageCode, "daas_ui", "shipping.locations.list.contact").toLowerCase()));

			gotoNonMSPSettingsTab();
			LOGGER.info("Redirected to settings tab");
			waitForPageLoaded();
			settingsPage.waitForElementsOfSettingsPage("locationsTab");
			settingsPage.clickOnElementsOfSettingsPage("locationsTab");
			LOGGER.info("Clicked on locations tab");
			softAssert.assertTrue(settingsPage.getTextOfSettingsPage("shippingLocationTileTitle").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.shipping.locations").toUpperCase()), "Shipping locations title is incorrect");
		    softAssert.assertTrue(settingsPage.verifyElementsOfSettingsPage("addButton"), "Add button on shipping location tiles is not present");
			softAssert.assertTrue(settingsPage.getTextOfListOfSettingsPage("locationTableHeaders").equals(locationHeaders), "The headers on location table are incorrect");
			settingsPage.clickOnElementsOfSettingsPage("addButton");
			LOGGER.info("Clicked on add button");
			sleeper(2000);
			softAssert.assertTrue(settingsPage.matchTextOfSettingsPage("addLocationPopupTitle", getTextLanguage(LanguageCode, "daas_ui", "shipping.locations.modal.add.title")), "Title on add shipping location popup is incorrect");
			softAssert.assertTrue(settingsPage.matchTextOfSettingsPage("addLocationPopupSubtitle", getTextLanguage(LanguageCode, "daas_ui", "create.case.step.location.desc.step_add_location")), "Subtitle on add shipping location popup is incorrect");

			settingsPage.clickOnElementsOfSettingsPage("nextLocationButton");
			softAssert.assertTrue(settingsPage.matchTextOfSettingsPage("countryErrorMessage", getTextLanguage(LanguageCode, "daas_ui", "form.validate.required")), "Error message for country field on add shipping location popup is incorrect");
			softAssert.assertTrue(settingsPage.matchTextOfSettingsPage("addressLine1ErrorMessage", getTextLanguage(LanguageCode, "daas_ui", "form.validate.required")), "Error message for address line 1 field on add shipping location popup is incorrect");
			softAssert.assertTrue(settingsPage.matchTextOfSettingsPage("cityErrorMessage", getTextLanguage(LanguageCode, "daas_ui", "form.validate.required")), "Error message for city field on add shipping location popup is incorrect");
			softAssert.assertTrue(settingsPage.matchTextOfSettingsPage("stateErrorMessage", getTextLanguage(LanguageCode, "daas_ui", "form.validate.required")), "Error message for state field on add shipping location popup is incorrect");
			softAssert.assertTrue(settingsPage.matchTextOfSettingsPage("zipcodeErrorMessage", getTextLanguage(LanguageCode, "daas_ui", "form.validate.required")), "Error message for zipcode field on add shipping location popup is incorrect");
			softAssert.assertTrue(settingsPage.matchTextOfSettingsPage("countryLabel", getTextLanguage(LanguageCode, "daas_ui", "global.country")), "Country label on add shipping location popup is incorrect");
			settingsPage.clickOnElementsOfSettingsPage("countryDropdown");
			settingsPage.selectFirstOptionFromDropdownOnSettingsPage("countryOptions");
			softAssert.assertTrue(settingsPage.matchTextOfSettingsPage("addressLine1Label", getTextLanguage(LanguageCode, "daas_ui", "users.details.address1")), "Address Line 1 label on add shipping location popup is incorrect");
			settingsPage.enterTextForSettingsPage("addressLine1Textbox", addressLine1);
			softAssert.assertTrue(settingsPage.matchTextOfSettingsPage("addressLine2Label", getTextLanguage(LanguageCode, "daas_ui", "users.details.address2")), "Address Line 2 label on add shipping location popup is incorrect");
			settingsPage.enterTextForSettingsPage("addressLine2Textbox", addressLine2);
			softAssert.assertTrue(settingsPage.matchTextOfSettingsPage("cityLabel", getTextLanguage(LanguageCode, "daas_ui", "users.details.city")), "City label on add shipping location popup is incorrect");
			settingsPage.enterTextForSettingsPage("cityTextbox", city);
			softAssert.assertTrue(settingsPage.matchTextOfSettingsPage("stateLabel", getTextLanguage(LanguageCode, "daas_ui", "global.region")), "State/Province label on add shipping location popup is incorrect");
			settingsPage.enterTextForSettingsPage("stateTextbox", state);
			softAssert.assertTrue(settingsPage.matchTextOfSettingsPage("zipcodeLabel", getTextLanguage(LanguageCode, "daas_ui", "global.zipcode")), "Zip code label on add shipping location popup is incorrect");
			settingsPage.enterTextForSettingsPage("zipcodeTextbox", zipcode);
			settingsPage.clickOnElementsOfSettingsPage("nextLocationButton");
			LOGGER.info("Clicked on next button");

			settingsPage.clickOnElementsOfSettingsPage("addLocationButton");
			softAssert.assertTrue(settingsPage.matchTextOfSettingsPage("firstNameErrorMessage", getTextLanguage(LanguageCode, "daas_ui", "form.validate.required")), "Error message for first name field on add shipping location popup is incorrect");
			softAssert.assertTrue(settingsPage.matchTextOfSettingsPage("lastNameErrorMessage", getTextLanguage(LanguageCode, "daas_ui", "form.validate.required")), "Error message for last name field on add shipping location popup is incorrect");
		    softAssert.assertTrue(settingsPage.matchTextOfSettingsPage("firstNameLabel", getTextLanguage(LanguageCode, "daas_ui", "global.form.first_name")), "First name label on add shipping location popup is incorrect");
			settingsPage.enterTextForSettingsPage("firstNameTextbox", firstName);
			softAssert.assertTrue(settingsPage.matchTextOfSettingsPage("lastNameLabel", getTextLanguage(LanguageCode, "daas_ui", "global.form.last_name")), "Last name label on add shipping location popup is incorrect");
			settingsPage.enterTextForSettingsPage("lastNameTextbox", lastName);
			softAssert.assertTrue(settingsPage.matchTextOfSettingsPage("emailLabel", getTextLanguage(LanguageCode, "daas_ui", "global.form.email")), "Email label on add shipping location popup is incorrect");
			settingsPage.enterTextForSettingsPage("emailTextbox", email);
			softAssert.assertTrue(settingsPage.matchTextOfSettingsPage("phoneNumberLabel", getTextLanguage(LanguageCode, "daas_ui", "global.form.phone")), "Phone label on add shipping location popup is incorrect");
			settingsPage.enterTextForSettingsPage("phoneTextbox", phone);
			softAssert.assertTrue(settingsPage.matchTextOfSettingsPage("deliveryInstructionsLabel", getTextLanguage(LanguageCode, "daas_ui", "shipping.locations.add.instructions")), "Delivery Instructions label on add shipping location popup is incorrect");
			settingsPage.enterTextForSettingsPage("instructionsTextbox", instructions);
			softAssert.assertTrue(settingsPage.matchTextOfSettingsPage("setDefaultLabel", getTextLanguage(LanguageCode, "daas_ui", "shipping.locations.add.set.default")), "Set as default location label on add shipping location popup is incorrect");
			softAssert.assertTrue(settingsPage.verifyElementsOfSettingsPage("previousLocationButton"),"Previous location button is missing on add location popup");
			softAssert.assertTrue(settingsPage.verifyElementsOfSettingsPage("addLocationButton"),"Add location button is missing on add location popup");
			softAssert.assertTrue(settingsPage.verifyElementsOfSettingsPage("cancelLocationButton"),"Cancel location button is missing on add location popup");
			settingsPage.clickOnElementsOfSettingsPage("addLocationButton");

			settingsPage.waitForElementsOfSettingsPage("successNotification");
			LOGGER.info("Shipping Location added successfully.");
			softAssert.assertTrue(settingsPage.matchTextOfSettingsPage("successNotification", getTextLanguage(LanguageCode, "daas_ui", "shipping.locations.modal.add.success")), "Toast notification is missing after adding location");

			settingsPage.enterTextForSettingsPage("addressSearchBox", addressLine1);
			String[] locationDetails = {addressLine1,city,state,"Albania",firstName+" "+lastName};
			sleeper(2000);
			softAssert.assertTrue(dashboardPage.verifyWidgetsDisplayedForActiveCare("addeddetails", locationDetails),"Location details not verified");
			sleeper(5000);//need to add sleeper since it is taking time to load the address
			softAssert.assertFalse(settingsPage.verifyElementsOfSettingsPage("noItemsAvailable"), "Location addition unsuccessfull");
			settingsPage.mousehoverOnSettingsPage("firstRowAddress");
			settingsPage.clickOnElementsOfSettingsPage("hamburger");
			settingsPage.waitForElementsOfSettingsPage("setAsDefaultActionOnHamburger");
			settingsPage.clickByJavaScriptOnSettingsPage("setAsDefaultActionOnHamburger");
			softAssert.assertTrue(settingsPage.matchTextOfSettingsPage("defaultLocationPopupTitle", getTextLanguage(LanguageCode, "daas_ui", "shipping.locations.modal.default.location")), "Title on set default location popup is incorrect");
			softAssert.assertTrue(settingsPage.matchTextOfSettingsPage("defaultLocationPopupSubtitle", getTextLanguage(LanguageCode, "daas_ui", "shipping.locations.modal.default.location.confirm").replace("{locationName}", addressLine1)), "Subtitle on set default location popup is incorrect");
			settingsPage.clickOnElementsOfSettingsPage("setToDefaultButton");
			settingsPage.waitForElementsOfSettingsPage("successNotification");
			softAssert.assertTrue(settingsPage.verifyElementsOfSettingsPage("successNotification"),"Location is not set to default successfully");
			softAssert.assertTrue(settingsPage.verifyElementsOfSettingsPage("defaultTag"), "Default tag on location is not present");

			settingsPage.mousehoverOnSettingsPage("firstRowAddress");
			settingsPage.clickByJavaScriptOnSettingsPage("firstCheckBox");
			settingsPage.clickOnElementsOfSettingsPage("removeLocationButton");
			LOGGER.info("Click Delete Location button.");
			softAssert.assertTrue(settingsPage.matchTextOfSettingsPage("removeLocationTitle", getTextLanguage(LanguageCode, "daas_ui", "shipping.locations.modal.remove.location")), "Title on remove location popup is incorrect");
			softAssert.assertTrue(settingsPage.matchTextOfSettingsPage("removeLocationSubtitle", getTextLanguage(LanguageCode, "daas_ui", "shipping.locations.modal.remove.confirm").replace("{locationName}", addressLine1)), "Subtitle on remove location popup is incorrect");
			softAssert.assertTrue(settingsPage.verifyElementsOfSettingsPage("removeLocationPopupButton"), "Remove button on popup is missing");
			softAssert.assertTrue(settingsPage.verifyElementsOfSettingsPage("removeLocationPopupCancelButton"), "Cancel remove button on popup is missing");
			settingsPage.clickOnElementsOfSettingsPage("removeLocationPopupButton");
			LOGGER.info("Click Delete button on Delete Shipping location popup modal.");
			softAssert.assertTrue(settingsPage.matchTextOfSettingsPage("successNotification", getTextLanguage(LanguageCode, "daas_ui", "shipping.locations.modal.remove.success")),"Location is not removed successfully");
			softAssert.assertAll();
		}
		
		
		@Test(priority = 28, groups = {"ACTIVECARESANITY", "SUBODH", "REGRESSIONSETTINGS2", "SMOKE", "REGRESSION_CI", "SMOKE_CI", "STABILIZATION_STAGING"}, description = "Test Case ID : 374613", enabled=true)
		public final void verifyAddOrdersTile() throws Exception {
			
			SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
			OrdersListPage ordersListPage = new OrdersListPage(PreDefinedActions.getDriver()).getInstance();
			OrderDetailsPage orderDetailsPage = new OrderDetailsPage(PreDefinedActions.getDriver()).getInstance();

			SoftAssert softAssert = new SoftAssert();
			String[] columnValueToUpdateRandomData = { "ObjectOfServiceSerialNumber" };
//				Update ObjectServiceSerialNumber each execution
			settingsPage.updateActiveCareCSVFieldValue("ORDERS_VALID", columnValueToUpdateRandomData);
			// Fetch all rows from the CSV as TestData for validation with UI
			String[][] csv_TestData = orderDetailsPage.getTestDataFromImportCSV("ORDERS_VALID");
			login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
			waitForPageLoaded();
			if(environment.toLowerCase().contains("eu")) {
				LOGGER.info("As per new CarePack service EU root user will not have Orders tab. Only US root user will have orders tab and will be allowed to import devices into a Tenant.");
				
			}else {
			gotoOrderTab();
			waitForPageLoaded();
			sleeper(5000);
			LOGGER.info("Navigate to Orders tab.");
			softAssert.assertTrue(ordersListPage.isAtOrdersListPage(), "FAIL: User is not at Orders List Page.");
			softAssert.assertTrue(ordersListPage.ClickAddButtonOrdersListPage(), "FAIL: Click Add button on Orders List Page.");
			softAssert.assertTrue(ordersListPage.verifyAddOrdersModal(LanguageCode), "FAIL: Verify Add Orders modal contents.");
			Assert.assertTrue(ordersListPage.verifyImportCSVOrdersListPage(LanguageCode, 
					getEnvironmentSpecificData(System.getProperty("environment"), "ORDERS_VALID")), "FAIL: File import failed.");
			LOGGER.info("CSV imported successfully.");
			refreshPage();
			sleeper(5000);
			int row_number = ordersListPage.getImportRecordRowNumberInList(LanguageCode, 
					getEnvironmentSpecificData(System.getProperty("environment"), "ORDERS_VALID"));
			if(row_number>0) {
				// Verify UI details of file uploaded record with OrdersListPage.
				softAssert.assertTrue(ordersListPage.checkOrderListPageUIDetails(row_number, String.valueOf(csv_TestData.length-1), getEnvironmentSpecificData(System.getProperty("environment"), "ORDERS_VALID")), "FAIL: Validation of OrderListPage UI details failed.");

				LOGGER.info(getEnvironmentSpecificData(System.getProperty("environment"), "ORDERS_VALID")+" file mame is present in the list.");
				Assert.assertTrue(true, "FAIL: Imported CSV file not found in Orders List Page.");
			}else {
				LOGGER.info(getEnvironmentSpecificData(System.getProperty("environment"), "ORDERS_VALID")+" file mame is not present in the list.");
				Assert.assertTrue(false, "FAIL: Imported CSV file not found in Orders List Page.");
			}
			}
			softAssert.assertAll();
		}
		
		
		@Test(priority = 6, groups = { "REGRESSIONSETTINGS2", "REGRESSION_CI",	"STABILIZATION_STAGING" }, description = "TEST CASE - 1180738")
		public final void verifyNotificationsTabforACFanTelemetry() throws Exception {
			
			List<String> hardwareHealthList = new ArrayList<String>(Arrays.asList(
					getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties",
							"com.hp.mpi.icm.subtype.systemthermal"),
					// For EU region Battery Recall option is missing from HardwareHealthList.Need to check. 
					getTextLanguage(LanguageCode,"MPI-Incident-management-service-backend-Properties",
							"com.hp.mpi.icm.subtype.batteryrecall"),
					getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties",
							"com.hp.mpi.icm.subtype.hddpredictive"),
					getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties",
							"com.hp.mpi.icm.subtype.biosoutofdate"),
					getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties",
							"com.hp.mpi.icm.subtype.batteryneedsattention"),
					getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties",
							"com.hp.mpi.icm.subtype.fancritical"),
					getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties",
							"com.hp.mpi.icm.subtype.fanwarning")
					 ));
			
			List<String> activeCareList = new ArrayList<String>(Arrays.asList(
					getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties",
							"com.hp.mpi.icm.subtype.motherboard"),
					getTextLanguage(LanguageCode,"MPI-Incident-management-service-backend-Properties",
							"com.hp.mpi.icm.subtype.iodevice"),
					getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties",
							"com.hp.mpi.icm.subtype.battery"),
					getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties",
							"com.hp.mpi.icm.subtype.other"),
					getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties",
							"com.hp.mpi.icm.insights.tags.hdd.hdd"),
					getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties",
							"com.hp.mpi.icm.subtype.power")));
			
			
			SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
			SoftAssert softAssert = new SoftAssert();
			login("ACTIVECARETENANT_EMAIL", "ACTIVECARETENANT_PASSWORD");
			waitForPageLoaded();
			gotoCompanySettingsTab();						
			settingsPage.waitForElementsOfSettingsPage("notificationsTab");
			settingsPage.clickOnElementsOfSettingsPage("notificationsTab");
			softAssert.assertTrue(
					settingsPage.getTextOfSettingsPage("deviceNotificationsHeader").equalsIgnoreCase(
							getTextLanguage(LanguageCode, "daas_ui", "companies.details.tile.notifications")),
					"Header on device notifications tile is incorrect");
			softAssert.assertTrue(
					settingsPage.getTextOfSettingsPage("deviceNotificationsHeaderWarning").equalsIgnoreCase(
							getTextLanguage(LanguageCode, "daas_ui", "companies.details.tile.notifications.warning")
									.replace("{applicationName}", "Workforce Experience Platform")),
					"Warning on device notifications tile is incorrect");

			softAssert.assertTrue(
					settingsPage.getTextOfSettingsPage("allowServiceLocationLabel").equalsIgnoreCase(
							getTextLanguage(LanguageCode, "daas_ui", "companies.details.allow.service.location")),
					"Title on allow service location tile is incorrect");
			softAssert.assertTrue(
					settingsPage.getTextOfSettingsPage("selfServiceAlertsLabel").equalsIgnoreCase(
							getTextLanguage(LanguageCode, "daas_ui", "companies.details.self.service.alerts")+" (For Active Care devices only)"),
					"Title on self service alerts tile is incorrect");
			
			String enabled = getTextLanguage(LanguageCode, "daas_ui", "global.enabled");
			String disabled = getTextLanguage(LanguageCode, "daas_ui", "global.disabled");
			waitForPageLoaded();
			settingsPage.clickOnElementsOfSettingsPage("hardwareHealth");
			boolean hardwareStatus = settingsPage.checkStatusOfAlerts(enabled, disabled, hardwareHealthList, "hardware1", true);
			Assert.assertTrue(hardwareStatus, "Status mismatch for Hardware Health List");
			Assert.assertTrue(settingsPage.verifyListOfSubCategoriesUnderSelfServiceAlerts(hardwareHealthList, "hardwarehealthcategoriestitle"), "Fan-Warning & Fan-Critical Categories are present in the list in Notification tab under Self Service Alert -> Hardware Health.");
			settingsPage.clickOnElementsOfSettingsPage("hardwareHealth");
			settingsPage.waitForElementsOfSettingsPage("activeCare");
			settingsPage.clickByJavaScriptOnSettingsPage("activeCare");
			boolean activeCareStatus = settingsPage.checkStatusOfAlerts(enabled, disabled, activeCareList,"activeCare1",false);
			Assert.assertTrue(activeCareStatus,"Status mismatch for Active Care Alerts");		
			Assert.assertTrue(settingsPage.verifyListOfSubCategoriesUnderSelfServiceAlerts(activeCareList, "activecarecategoriestitle"), "Verify ActiveCare sub-categories. in the list in Notification tab under Self Service Alert -> ActiveCare.");
			LOGGER.info("Notification tab default state for Active Care validated successfully.");
			softAssert.assertAll();			
		}
		
	
		@Test(priority = 6, groups = { "REGRESSIONSETTINGS2", "REGRESSION_CI",	"STABILIZATION_STAGING" }, description = "TEST CASE - 1180738")
		public final void verifyNotificationsTabforPIACFanTelemetry() throws Exception {
			
			List<String> hardwareHealthList = new ArrayList<String>(Arrays.asList(
					getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties",
							"com.hp.mpi.icm.subtype.systemthermal"),
					// For EU region Battery Recall option is missing from HardwareHealthList.Need to check. 
					getTextLanguage(LanguageCode,"MPI-Incident-management-service-backend-Properties",
							"com.hp.mpi.icm.subtype.batteryrecall"),
					getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties",
							"com.hp.mpi.icm.subtype.hddpredictive"),
					getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties",
							"com.hp.mpi.icm.subtype.biosoutofdate"),
					getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties",
							"com.hp.mpi.icm.subtype.batteryneedsattention"),
					getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties",
							"com.hp.mpi.icm.subtype.fancritical"),
					getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties",
							"com.hp.mpi.icm.subtype.fanwarning")
					 ));
			
			List<String> activeCareList = new ArrayList<String>(Arrays.asList(
					getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties",
							"com.hp.mpi.icm.subtype.motherboard"),
					getTextLanguage(LanguageCode,"MPI-Incident-management-service-backend-Properties",
							"com.hp.mpi.icm.subtype.iodevice"),
					getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties",
							"com.hp.mpi.icm.subtype.battery"),
					getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties",
							"com.hp.mpi.icm.subtype.other"),
					getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties",
							"com.hp.mpi.icm.insights.tags.hdd.hdd"),
					getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties",
							"com.hp.mpi.icm.subtype.power")));
			
			
			SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
			SoftAssert softAssert = new SoftAssert();
			login("ACPITENANT_EMAIL", "ACPITENANT_PASSWORD");
			waitForPageLoaded();
			gotoCompanySettingsTab();						
			settingsPage.waitForElementsOfSettingsPage("notificationsTab");
			settingsPage.clickOnElementsOfSettingsPage("notificationsTab");
			softAssert.assertTrue(
					settingsPage.getTextOfSettingsPage("deviceNotificationsHeader").equalsIgnoreCase(
							getTextLanguage(LanguageCode, "daas_ui", "companies.details.tile.notifications")),
					"Header on device notifications tile is incorrect");
			softAssert.assertTrue(
					settingsPage.getTextOfSettingsPage("deviceNotificationsHeaderWarning").equalsIgnoreCase(
							getTextLanguage(LanguageCode, "daas_ui", "companies.details.tile.notifications.warning")
									.replace("{applicationName}", "Workforce Experience Platform")),
					"Warning on device notifications tile is incorrect");

			softAssert.assertTrue(
					settingsPage.getTextOfSettingsPage("allowServiceLocationLabel").equalsIgnoreCase(
							getTextLanguage(LanguageCode, "daas_ui", "companies.details.allow.service.location")),
					"Title on allow service location tile is incorrect");
			softAssert.assertTrue(
					settingsPage.getTextOfSettingsPage("selfServiceAlertsLabel").equalsIgnoreCase(
							getTextLanguage(LanguageCode, "daas_ui", "companies.details.self.service.alerts")+" (For Active Care devices only)"),
					"Title on self service alerts tile is incorrect");
			
			String enabled = getTextLanguage(LanguageCode, "daas_ui", "global.enabled");
			String disabled = getTextLanguage(LanguageCode, "daas_ui", "global.disabled");
			waitForPageLoaded();
			settingsPage.clickOnElementsOfSettingsPage("hardwareHealth");
			boolean hardwareStatus = settingsPage.checkStatusOfAlerts(enabled, disabled, hardwareHealthList, "hardware1", true);
			Assert.assertTrue(hardwareStatus, "Status mismatch for Hardware Health List");
			Assert.assertTrue(settingsPage.verifyListOfSubCategoriesUnderSelfServiceAlerts(hardwareHealthList, "hardwarehealthcategoriestitle"), "Fan-Warning & Fan-Critical Categories are present in the list in Notification tab under Self Service Alert -> Hardware Health.");
			settingsPage.clickOnElementsOfSettingsPage("hardwareHealth");
			settingsPage.waitForElementsOfSettingsPage("activeCare");
			settingsPage.clickByJavaScriptOnSettingsPage("activeCare");
			boolean activeCareStatus = settingsPage.checkStatusOfAlerts(enabled, disabled, activeCareList,"activeCare1",false);
			Assert.assertTrue(activeCareStatus,"Status mismatch for Active Care Alerts");		
			Assert.assertTrue(settingsPage.verifyListOfSubCategoriesUnderSelfServiceAlerts(activeCareList, "activecarecategoriestitle"), "Verify ActiveCare sub-categories. in the list in Notification tab under Self Service Alert -> ActiveCare.");
			LOGGER.info("Notification tab default state for Active Care validated successfully.");
			softAssert.assertAll();			
		}
		
	
		@Test(priority = 6, groups = { "REGRESSIONSETTINGS2", "REGRESSION_CI",	"STABILIZATION_STAGING" }, description = "TEST CASE - 1180738")
		public final void verifyDefaultMsgtforACFanTelemetryToggles() throws Exception {
			
			List<String> hardwareHealthList = new ArrayList<String>(Arrays.asList(
					getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties",
							"com.hp.mpi.icm.subtype.systemthermal"),
					// For EU region Battery Recall option is missing from HardwareHealthList.Need to check. 
					getTextLanguage(LanguageCode,"MPI-Incident-management-service-backend-Properties",
							"com.hp.mpi.icm.subtype.batteryrecall"),
					getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties",
							"com.hp.mpi.icm.subtype.hddpredictive"),
					getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties",
							"com.hp.mpi.icm.subtype.biosoutofdate"),
					getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties",
							"com.hp.mpi.icm.subtype.batteryneedsattention"),
					getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties",
							"com.hp.mpi.icm.subtype.fancritical"),
					getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties",
							"com.hp.mpi.icm.subtype.fanwarning")
					 ));
			
			List<String> activeCareList = new ArrayList<String>(Arrays.asList(
					getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties",
							"com.hp.mpi.icm.subtype.motherboard"),
					getTextLanguage(LanguageCode,"MPI-Incident-management-service-backend-Properties",
							"com.hp.mpi.icm.subtype.iodevice"),
					getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties",
							"com.hp.mpi.icm.subtype.battery"),
					getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties",
							"com.hp.mpi.icm.subtype.other"),
					getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties",
							"com.hp.mpi.icm.insights.tags.hdd.hdd"),
					getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties",
							"com.hp.mpi.icm.subtype.power")));
			
			
			SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
			SoftAssert softAssert = new SoftAssert();
			login("ACTIVECARETENANT_EMAIL", "ACTIVECARETENANT_PASSWORD");
			waitForPageLoaded();
			gotoCompanySettingsTab();						
			settingsPage.waitForElementsOfSettingsPage("notificationsTab");
			settingsPage.clickOnElementsOfSettingsPage("notificationsTab");
			softAssert.assertTrue(
					settingsPage.getTextOfSettingsPage("deviceNotificationsHeader").equalsIgnoreCase(
							getTextLanguage(LanguageCode, "daas_ui", "companies.details.tile.notifications")),
					"Header on device notifications tile is incorrect");
			softAssert.assertTrue(
					settingsPage.getTextOfSettingsPage("deviceNotificationsHeaderWarning").equalsIgnoreCase(
							getTextLanguage(LanguageCode, "daas_ui", "companies.details.tile.notifications.warning")
									.replace("{applicationName}", "Workforce Experience Platform")),
					"Warning on device notifications tile is incorrect");

			softAssert.assertTrue(
					settingsPage.getTextOfSettingsPage("allowServiceLocationLabel").equalsIgnoreCase(
							getTextLanguage(LanguageCode, "daas_ui", "companies.details.allow.service.location")),
					"Title on allow service location tile is incorrect");
			softAssert.assertTrue(
					settingsPage.getTextOfSettingsPage("selfServiceAlertsLabel").equalsIgnoreCase(
							getTextLanguage(LanguageCode, "daas_ui", "companies.details.self.service.alerts")+" (For Active Care devices only)"),
					"Title on self service alerts tile is incorrect");
			
			String enabled = getTextLanguage(LanguageCode, "daas_ui", "global.enabled");
			String disabled = getTextLanguage(LanguageCode, "daas_ui", "global.disabled");
			waitForPageLoaded();
			settingsPage.clickOnElementsOfSettingsPage("hardwareHealth");
			boolean hardwareStatus = settingsPage.checkStatusOfAlerts(enabled, disabled, hardwareHealthList, "hardware1", true);
			Assert.assertTrue(hardwareStatus, "Status mismatch for Hardware Health List");
			Assert.assertTrue(settingsPage.editSelfServiceAlertHWToggle(getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties",
					"com.hp.mpi.icm.subtype.fancritical")), "FAIL: Unable to Edit Self Service toggle for Fan-Critical.");
			Assert.assertTrue(settingsPage.verifySelfServiceAlertHWDefaultMessageURL(SettingsVariables.FANCRITICAL_DEFAULT_MESSAGE, null), 
					"FAIL: FAN-Critical default message does not match when verified on UI.");
			Assert.assertTrue(settingsPage.editSelfServiceAlertHWToggle(getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties",
					"com.hp.mpi.icm.subtype.fanwarning")), "FAIL: Unable to Edit Self Service toggle for Fan-Warning.");
			Assert.assertTrue(settingsPage.verifySelfServiceAlertHWDefaultMessageURL(SettingsVariables.FANWARNING_DEFAULT_MESSAGE, SettingsVariables.FANWARNING_DEFAULT_URL), 
					"FAIL: FAN-Warning default message & Default URL does not match when verified on UI.");
			softAssert.assertAll();			
		}
		
		
		@Test(priority = 6, groups = { "REGRESSIONSETTINGS2", "REGRESSION_CI",	"STABILIZATION_STAGING" }, description = "TEST CASE - 1180738")
		public final void verifyDefaultMsgtforPIACFanTelemetryToggles() throws Exception {
			
			List<String> hardwareHealthList = new ArrayList<String>(Arrays.asList(
					getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties",
							"com.hp.mpi.icm.subtype.systemthermal"),
					// For EU region Battery Recall option is missing from HardwareHealthList.Need to check. 
					getTextLanguage(LanguageCode,"MPI-Incident-management-service-backend-Properties",
							"com.hp.mpi.icm.subtype.batteryrecall"),
					getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties",
							"com.hp.mpi.icm.subtype.hddpredictive"),
					getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties",
							"com.hp.mpi.icm.subtype.biosoutofdate"),
					getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties",
							"com.hp.mpi.icm.subtype.batteryneedsattention"),
					getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties",
							"com.hp.mpi.icm.subtype.fancritical"),
					getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties",
							"com.hp.mpi.icm.subtype.fanwarning")
					 ));
			
			List<String> activeCareList = new ArrayList<String>(Arrays.asList(
					getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties",
							"com.hp.mpi.icm.subtype.motherboard"),
					getTextLanguage(LanguageCode,"MPI-Incident-management-service-backend-Properties",
							"com.hp.mpi.icm.subtype.iodevice"),
					getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties",
							"com.hp.mpi.icm.subtype.battery"),
					getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties",
							"com.hp.mpi.icm.subtype.other"),
					getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties",
							"com.hp.mpi.icm.insights.tags.hdd.hdd"),
					getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties",
							"com.hp.mpi.icm.subtype.power")));
			
			
			SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
			SoftAssert softAssert = new SoftAssert();
			login("ACTIVECARETENANT_EMAIL", "ACTIVECARETENANT_PASSWORD");
			waitForPageLoaded();
			gotoCompanySettingsTab();						
			settingsPage.waitForElementsOfSettingsPage("notificationsTab");
			settingsPage.clickOnElementsOfSettingsPage("notificationsTab");
			softAssert.assertTrue(
					settingsPage.getTextOfSettingsPage("deviceNotificationsHeader").equalsIgnoreCase(
							getTextLanguage(LanguageCode, "daas_ui", "companies.details.tile.notifications")),
					"Header on device notifications tile is incorrect");
			softAssert.assertTrue(
					settingsPage.getTextOfSettingsPage("deviceNotificationsHeaderWarning").equalsIgnoreCase(
							getTextLanguage(LanguageCode, "daas_ui", "companies.details.tile.notifications.warning")
									.replace("{applicationName}", "Workforce Experience Platform")),
					"Warning on device notifications tile is incorrect");

			softAssert.assertTrue(
					settingsPage.getTextOfSettingsPage("allowServiceLocationLabel").equalsIgnoreCase(
							getTextLanguage(LanguageCode, "daas_ui", "companies.details.allow.service.location")),
					"Title on allow service location tile is incorrect");
			softAssert.assertTrue(
					settingsPage.getTextOfSettingsPage("selfServiceAlertsLabel").equalsIgnoreCase(
							getTextLanguage(LanguageCode, "daas_ui", "companies.details.self.service.alerts")+" (For Active Care devices only)"),
					"Title on self service alerts tile is incorrect");
			
			String enabled = getTextLanguage(LanguageCode, "daas_ui", "global.enabled");
			String disabled = getTextLanguage(LanguageCode, "daas_ui", "global.disabled");
			waitForPageLoaded();
			settingsPage.clickOnElementsOfSettingsPage("hardwareHealth");
			boolean hardwareStatus = settingsPage.checkStatusOfAlerts(enabled, disabled, hardwareHealthList, "hardware1", true);
			Assert.assertTrue(hardwareStatus, "FAIL: Status mismatch for Hardware Health List in PI+AC tenant");
			Assert.assertTrue(settingsPage.editSelfServiceAlertHWToggle(getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties",
					"com.hp.mpi.icm.subtype.fancritical")), "FAIL: Unable to Edit Self Service toggle for Fan-Critical in PI+AC tenant.");
			Assert.assertTrue(settingsPage.verifySelfServiceAlertHWDefaultMessageURL(SettingsVariables.FANCRITICAL_DEFAULT_MESSAGE, null), 
					"FAIL: FAN-Critical default message does not match when verified on UI in PI+AC tenant.");
			Assert.assertTrue(settingsPage.editSelfServiceAlertHWToggle(getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties",
					"com.hp.mpi.icm.subtype.fanwarning")), "FAIL: Unable to Edit Self Service toggle for Fan-Warning in PI+AC tenant.");
			Assert.assertTrue(settingsPage.verifySelfServiceAlertHWDefaultMessageURL(SettingsVariables.FANWARNING_DEFAULT_MESSAGE, SettingsVariables.FANWARNING_DEFAULT_URL), 
					"FAIL: FAN-Warning default message & Default URL does not match when verified on UI in PI+AC tenant.");
			softAssert.assertAll();			
		}
		
		
		@Test(priority = 8, groups = {"ACTIVECARESANITY", "REGRESSIONSETTINGS2", "SMOKE", "REGRESSION_CI", "SMOKE_CI",
		"STABILIZATION_STAGING"})
		public final void verifyACDeviceMaxCountUpload() throws Exception {
			login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
			OrdersListPage ordersListPage = new OrdersListPage(PreDefinedActions.getDriver()).getInstance();
			SoftAssert softAssert = new SoftAssert();
			String environment = System.getProperty("environment");
			waitForPageLoaded();
			LOGGER.info("Root user logged in");
			// this will verify condition for EU environment user. EU region root user will not have Orders tab.
			// For EU region we will login to EU Holding Tank tenant and verify device is present.
			if(environment.toLowerCase().contains("eu")) {
				LOGGER.info("PASS: For EU region Order tab is not visible for Root user.");
				Assert.assertTrue(true, "FAIL:");
			}else {
				
			gotoOrderTab();
			LOGGER.info("Navigate to Orders tab.");
			softAssert.assertTrue(ordersListPage.isAtOrdersListPage(), "FAIL: User is not at Orders List Page.");
			
			Assert.assertTrue(ordersListPage.verifyImportCSVOrdersListPage(LanguageCode, 
					getEnvironmentSpecificData(System.getProperty("environment"), "ORDERS_MAX_DEVICE_COUNT")), "FAIL: File import failed.");
			refreshPage();
			sleeper(1000);
			LOGGER.info("Search filename in OrdersListPage.");
			int row_number = ordersListPage.searchOrder(LanguageCode, getEnvironmentSpecificData(System.getProperty("environment"), "ORDERS_MAX_DEVICE_COUNT"));
			if(row_number==0) {
				Assert.assertTrue(false, "FAIL: Filename does not exist in orders list page.");
			}
			softAssert.assertTrue(ordersListPage.downloadOrderSummaryFile(row_number),"FAIL: Failed to download order summary file");
			softAssert.assertAll();
			
			}
			}
	
}
