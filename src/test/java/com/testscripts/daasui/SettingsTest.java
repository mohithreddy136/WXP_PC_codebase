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
import com.daasui.pages.WEPWPTPage;
import com.daasui.pages.OrdersListPage;
import com.daasui.pages.OrderDetailsPage;

public class SettingsTest extends CommonTest {
	private static Logger LOGGER = LogManager.getLogger(SettingsTest.class);
	
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

	@Test(priority = 1, groups = { "REGRESSIONSETTINGS1", "REGRESSION_CI", "PRODUCTION_SANITY","STABILIZATION_STAGING" })
	public final void verifySettingsTitle() throws Exception {
		login("MSP_SETTINGS_EMAIL_SA", "MSP_SETTINGS_PASSWORD_SA");
		gotoSettingsTab();
		Thread.sleep(3000);
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();

		if (toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, "MSP_SETTINGS_EMAIL_SA"))) {
			settingsPage.waitForElementsOfSettingsPage("settingsTitleOnBreadcrumbs");
			Assert.assertTrue(settingsPage.getTextOfSettingsPage("settingsTitleOnBreadcrumbs").equals(settingsPage.getTextLanguage(LanguageCode, "daas_ui", "settings.label")), "Settings title text is incorrect");
			LOGGER.info("Settings title is matched");
		}else{
		Assert.assertTrue(settingsPage.getTextOfSettingsPage("settingsTitle").equals(settingsPage.getTextLanguage(LanguageCode, "daas_ui", "settings.label")), "Settings title text is incorrect");
		LOGGER.info("Settings title is matched");
		}
	}

	/*
	 * This test case validates Callback functionality.
	 */
	@Test(priority = 2, groups = { "REGRESSIONSETTINGS1", "SMOKE", "REGRESSION_CI", "PRODUCTION_CI", "PRODUCTION_SANITY","STABILIZATION_STAGING" }, description = "[191409]")
	public final void verifyCallbackTile() throws Exception {
		SoftAssert sa = new SoftAssert();
		login("MSP_SETTINGS_EMAIL_SA", "MSP_SETTINGS_PASSWORD_SA");
		gotoSettingsTab();
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
		settingsPage.clickOnElementsOfSettingsPage("helpAndSupportTabSettingsPage");

		// Test Case 1
		sa.assertTrue(settingsPage.getTextOfSettingsPage("callbackTitle").equalsIgnoreCase(settingsPage.getTextLanguage(LanguageCode, "daas_ui", "settings.callback.title")), "Callback title does not match.");

		// Test Case 2
		sa.assertTrue(settingsPage.getTextOfSettingsPage("callbackText").equalsIgnoreCase(settingsPage.getTextLanguage(LanguageCode, "daas_ui", "settings.allow_callback.label")), "Text for callback does nto match.");

		// Test Case 3
		sa.assertTrue(settingsPage.verifyCallbackFunctionality(LanguageCode, "statusTextOfCallback", "toggleCallback"), "Callback functionality is not working properly.");
		sa.assertAll();
		LOGGER.info("Validation of Callback functionality completed.");
	}

	/*
	 * This test case validates Direct Support functionality.
	 */
	@Test(priority = 3, groups = { "REGRESSIONSETTINGS1", "REGRESSION_CI", "ALL_CI", "ALL", "SMOKE", "SMOKE_CI" }, description = "[191410]")
	public final void verifyDirectSupportTile() throws Exception {
		SoftAssert sa = new SoftAssert();
		login("MSP_SETTINGS_EMAIL_SA", "MSP_SETTINGS_PASSWORD_SA");
		gotoSettingsTab();
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
		settingsPage.clickOnElementsOfSettingsPage("helpAndSupportTabSettingsPage");

		HashMap<String, String> directSupportInfo = settingsPage.getDirectSupportDetails();

		// Test Case 1
		sa.assertTrue(settingsPage.getTextOfSettingsPage("directSupportTitle").equalsIgnoreCase(settingsPage.getTextLanguage(LanguageCode, "daas_ui", "settings.direct_support.title")), "Direct support title does not match.");

		// Test Case 2
		sa.assertTrue(settingsPage.getTextOfSettingsPage("directSupportText").equalsIgnoreCase(settingsPage.getTextLanguage(LanguageCode, "daas_ui", "settings.allow_direct_support.label")), "Text for direct support does not match.");

		// Test Case 3
		settingsPage.mousehoverOnSettingsPage("editIconDirectSupport");
		settingsPage.verifyElementsOfSettingsPage("editIconDirectSupport");
		settingsPage.clickOnElementsOfSettingsPage("editIconDirectSupport");
		sa.assertTrue(settingsPage.getTextOfSettingsPage("directSupportTitle").equalsIgnoreCase(settingsPage.getTextLanguage(LanguageCode, "daas_ui", "settings.direct_support.title")), "Direct support modal title does not match.");
		settingsPage.clickOnElementsOfSettingsPage("cancelDirectSupport");

		// Test Case 4
		sa.assertTrue(settingsPage.verifyToggleForDirectSupport(LanguageCode, directSupportInfo), "Direct Support Toggle functionality is not working properly.");

		// Test Case 5
		settingsPage.waitForElementsOfSettingsPage("editIconDirectSupport");
		sa.assertTrue(settingsPage.verifyToggleForDirectSupportSave(LanguageCode, directSupportInfo), "Direct Support (Save button) functionality is not working properly.");

		// Test Case 6
		settingsPage.verifyElementsOfSettingsPage("editIconDirectSupport");
		sa.assertTrue(settingsPage.verifyToggleForDirectSupportCancel(LanguageCode, directSupportInfo), "Direct Support (Cancel button) functionality is not working properly.");
		sa.assertAll();
		LOGGER.info("Validation of Direct Support functionality completed.");
	}

	/*
	 * This test case validates Email Support functionality.
	 */
	@Test(priority = 4, groups = { "REGRESSIONSETTINGS1", "SMOKE", "REGRESSION_CI" }, description = "[191411]")
	public final void verifyEmailSupportTile() throws Exception {
		SoftAssert sa = new SoftAssert();
		login("MSP_SETTINGS_EMAIL_SA", "MSP_SETTINGS_PASSWORD_SA");
		gotoSettingsTab();
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
		settingsPage.clickOnElementsOfSettingsPage("helpAndSupportTabSettingsPage");

		HashMap<String, String> emailSupportInfo = settingsPage.getEmailSupportDetails();

		// Test Case 1
		sa.assertTrue(settingsPage.getTextOfSettingsPage("emailSupportTitle").equalsIgnoreCase(settingsPage.getTextLanguage(LanguageCode, "daas_ui", "settings.email_support.title")), "Email support title does not match.");

		// Test Case 2
		sa.assertTrue(settingsPage.getTextOfSettingsPage("emailSupportText").equalsIgnoreCase(settingsPage.getTextLanguage(LanguageCode, "daas_ui", "settings.email_support.label")), "Text for Email support does not match.");

		// Test Case 3
		settingsPage.clickOnElementsOfSettingsPage("editIconEmailSupport");
		sa.assertTrue(settingsPage.getTextOfSettingsPage("emailSupportModalTitle").equalsIgnoreCase(settingsPage.getTextLanguage(LanguageCode, "daas_ui", "settings.email_support.title")), "Email support modal title does not match.");
		settingsPage.clickOnElementsOfSettingsPage("cancelEmailSupport");

		// Test Case 4
		sa.assertTrue(settingsPage.verifyToggleForEmailSupportSave(emailSupportInfo,"MSP"), "Email Support (Save button) functionality is not working properly.");

		// Test Case 5
		sleeper(3000);
		sa.assertTrue(settingsPage.verifyToggleForEmailSupportCancel(emailSupportInfo), "Email Support (Cancel button) functionality is not working properly.");
		sa.assertAll();
		LOGGER.info("Validation of Email Support functionality completed.");
	}

	/*
	 * This test case validates Country and timezone functionality.
	 */
	@Test(priority = 5, groups = { "REGRESSIONSETTINGS1", "REGRESSION_CI", "ALL_CI", "ALL", "SMOKE", "SMOKE_CI" }, description = "[191413]")
	public final void verifyCountryAndTimeZone() throws Exception {
		SoftAssert sa = new SoftAssert();
		login("MSP_SETTINGS_EMAIL_SA", "MSP_SETTINGS_PASSWORD_SA");
		gotoSettingsTab();
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
		settingsPage.clickOnElementsOfSettingsPage("helpAndSupportTabSettingsPage");
		HashMap<String, String> countryAndTimeZoneInfo = settingsPage.getCountryAndTimeZoneDetails();

		// Test Case 1
		sa.assertTrue(settingsPage.getTextOfSettingsPage("countryAndTimezoneText").equalsIgnoreCase(settingsPage.getTextLanguage(LanguageCode, "daas_ui", "settings.country.label")), "Text for country and timezone does not match.");

		// Test Case 2
		settingsPage.clickOnElementsOfSettingsPage("editIconCountry");
		sa.assertTrue(settingsPage.getTextOfSettingsPage("countryAndTimezoneModalTitle").equalsIgnoreCase(settingsPage.getTextLanguage(LanguageCode, "daas_ui", "settings.country.label")), "Country and timezone modal title does not match.");
		settingsPage.clickOnElementsOfSettingsPage("cancelCountry");

		// Test Case 3
		sa.assertTrue(settingsPage.verifyCountryAndTimeZoneSave(countryAndTimeZoneInfo), "Country and Timezone(save) is not working properly");

		// Test Case 4
		sa.assertTrue(settingsPage.verifyCountryAndTimeZoneCancel(countryAndTimeZoneInfo), "Country and Timezone(cancel) is not working properly");
		sa.assertAll();
		LOGGER.info("Validation of Country & Timezone functionality completed.");
	}

	/*
	 * This test case validates Hours of Operation functionality.
	 */
	@Test(priority = 6, groups = { "REGRESSIONSETTINGS1", "REGRESSION_CI", "ALL_CI", "ALL", "SMOKE", "SMOKE_CI" }, description = "[191413]")
	public final void verifyHoursOfOperation() throws Exception {
		SoftAssert sa = new SoftAssert();
		login("MSP_SETTINGS_EMAIL_SA", "MSP_SETTINGS_PASSWORD_SA");
		gotoSettingsTab();
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
		settingsPage.clickOnElementsOfSettingsPage("helpAndSupportTabSettingsPage");
		HashMap<String, String> hoursOfOperationInfo = settingsPage.getHoursOfOperationDetails();

		// Test Case 1
		sa.assertTrue(settingsPage.getTextOfSettingsPage("hoursOfOperationText").equalsIgnoreCase(settingsPage.getTextLanguage(LanguageCode, "daas_ui", "settings.hours_of_operation.title")), "Text for Hours of Operation does not match.");

		// Test Case 2
		settingsPage.clickOnElementsOfSettingsPage("editIconHoursOfOperation");
		sa.assertTrue(settingsPage.getTextOfSettingsPage("hoursOfOperationModalTitle").equalsIgnoreCase(settingsPage.getTextLanguage(LanguageCode, "daas_ui", "settings.hours_of_operation.title")), "Hours of Operation modal title does not match.");
		settingsPage.clickOnElementsOfSettingsPage("cancelHours");

		// Test Case 3
		sa.assertTrue(settingsPage.verifyHoursOfOperationSave(hoursOfOperationInfo, LanguageCode), "Hours of Operation(save) is not working properly");
		sleeper(3000);

		// Test Case 4
		sa.assertTrue(settingsPage.verifyHoursOfOperationCancel(hoursOfOperationInfo, LanguageCode), "Hours of Operation(cancel) is not working properly");
		sa.assertAll();
		LOGGER.info("Validation of Hours of Operation functionality completed.");
	}

	/*
	 * This test case validates Exclude Public Holiday functionality.
	 */
	@Test(priority = 7, groups = { "REGRESSIONSETTINGS1", "REGRESSION_CI", "ALL_CI", "ALL", "SMOKE", "SMOKE_CI" }, description = "[191413]")
	public final void verifyExcludePublicHoliday() throws Exception {
		SoftAssert sa = new SoftAssert();
		login("MSP_SETTINGS_EMAIL_SA", "MSP_SETTINGS_PASSWORD_SA");
		gotoSettingsTab();
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
		settingsPage.clickOnElementsOfSettingsPage("helpAndSupportTabSettingsPage");

		// Test Case 1
		sa.assertTrue(settingsPage.getTextOfSettingsPage("excludeHolidayText").equalsIgnoreCase(settingsPage.getTextLanguage(LanguageCode, "daas_ui", "settings.public_holidays.label")), "Text for Exclude Public Holiday does not match.");

		// Test Case 2
		sa.assertTrue(settingsPage.verifyExcludePublicHoliday(LanguageCode, "statusTextHoliday", "toggleHoliday"), "Exclude Public Holiday is not working properly.");
		sa.assertAll();
		LOGGER.info("Validation of Exclude public holiday functionality completed.");
	}

	/*
	 * This test case validates text under Help and Support, ServiceNow for SA.
	 */
	@Test(priority = 8, groups = { "REGRESSIONSETTINGS1", "REGRESSION_CI", "ALL_CI", "ALL", "SMOKE", "SMOKE_CI" }, description = "[191416],[191406]", enabled = false)
	public final void verifyTextSupportAdmin() throws Exception {
		SoftAssert sa = new SoftAssert();
		login("MSP_SETTINGS_EMAIL_SA", "MSP_SETTINGS_PASSWORD_SA");
		gotoSettingsTab();
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();

		// Test Case 1
		sa.assertTrue(settingsPage.getTextOfSettingsPage("textHelpAndSupport").equalsIgnoreCase(settingsPage.getTextLanguage(LanguageCode, "daas_ui", "settings.help_and_support.description")), "Text under Help & Support for SA does not match.");

		// Test Case 2
		sa.assertTrue(settingsPage.getTextOfSettingsPage("textServiceNow").equalsIgnoreCase(settingsPage.getTextLanguage(LanguageCode, "daas_ui", "settings.service_now.description")), "Text under ServiceNow for SA does not match.");
		sa.assertAll();
		LOGGER.info("Validation of text for SA completed.");
	}

	/*
	 * This test case validates text under Help and Support, ServiceNow for SS.
	 */
	@Test(priority = 9, groups = { "REGRESSIONSETTINGS1", "REGRESSION_CI", "ALL_CI", "ALL", "SMOKE", "SMOKE_CI" }, description = "[191419],[191420]", enabled = false)
	public final void verifyTextSupportSpecialist() throws Exception {
		SoftAssert sa = new SoftAssert();
		login("MSP_SETTINGS_EMAIL_SS", "MSP_SETTINGS_PASSWORD_SS");
		gotoSettingsTab();
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();

		// Test Case 1
		sa.assertTrue(settingsPage.getTextOfSettingsPage("textHelpAndSupport").equalsIgnoreCase(settingsPage.getTextLanguage(LanguageCode, "daas_ui", "settings.help_and_support.read_only.description")), "Text under Help & Support for SA does not match.");

		sa.assertAll();
		LOGGER.info("Validation of text for SS completed.");
	}

	/*
	 * This test case validates ServiceNow functionality.
	 */
	@Test(priority = 10, groups = { "REGRESSIONSETTINGS1", "REGRESSION_CI", "ALL_CI", "ALL", "SMOKE", "SMOKE_CI" }, description = "192058", enabled = false)
	public final void verifyServiceNowFunctionality() throws Exception {
		SoftAssert sa = new SoftAssert();
		login("MSP_SETTINGS_EMAIL_SA", "MSP_SETTINGS_PASSWORD_SA");
		SNOWInstanceWakeUP snowInstanceWakeUP = new SNOWInstanceWakeUP();
		snowInstanceWakeUP.wakeUpSNOWInstance(ConstantURL.SNOW_URL3, ConstantURL.SNOW_EMAIL3, ConstantURL.SNOW_USERNAME, ConstantURL.SNOW_PASSWORD1);
		gotoSettingsTab();
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();

		HashMap<String, String> hoursOfOperationInfo = settingsPage.getServiceNowDetails();
		settingsPage.clickOnElementsOfSettingsPage("serviceNowTab");
		sa.assertTrue(settingsPage.verifyServiceNowEnable(LanguageCode, hoursOfOperationInfo), "ServiceNow Enable is not working properly.");

		settingsPage.verifyElementsOfSettingsPage("editIconSnow");
		sa.assertTrue(settingsPage.verifyServiceNowDisable(LanguageCode, hoursOfOperationInfo), "ServiceNow Disable is not working properly.");

		settingsPage.verifyElementIsEnableOfSettingsPage("editIconSnow");
		sa.assertTrue(settingsPage.verifyServiceNowCancel(LanguageCode, hoursOfOperationInfo), "ServiceNow Cancel is not working properly.");
		sa.assertAll();
		LOGGER.info("Validation of ServiceNow functionality completed.");
	}

	/*
	 * This test case configures Hours of Operation functionality on MSP Settings page.
	 */
	@Test(priority = 11, groups = { "REGRESSIONSETTINGS1", "REGRESSION_CI", "ALL_CI", "ALL", "SMOKE", "SMOKE_CI" })
	public final void verifyHoursOfOperationMSP() throws Exception {
		SoftAssert sa = new SoftAssert();
		login("MSP_SETTINGS_EMAIL_SA", "MSP_SETTINGS_PASSWORD_SA");
		gotoSettingsTab();
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
		settingsPage.clickOnElementsOfSettingsPage("helpAndSupportTabSettingsPage");
		HashMap<String, String> hoursOfOperationInfo = settingsPage.getHoursOfOperationDetails();
		HashMap<String, String> hoursOfOperationInfoHnS = settingsPage.getHoursOfOperationDetailsHelpAndSupport();
		// Test Case 1
		sa.assertTrue(settingsPage.verifyHoursOfOperationSave(hoursOfOperationInfo, LanguageCode), "Hours of Operation(save) is not working properly");
		timing = settingsPage.getTextFromTimings(hoursOfOperationInfoHnS);
		sa.assertAll();
		LOGGER.info("Hours of Operation for MSP configured successfully.");
	}

	/*
	 * This test case validates Hours of Operation functionality on Help & Support page.
	 */
	@Test(priority = 12, dependsOnMethods = "verifyHoursOfOperationMSP", groups = { "REGRESSIONSETTINGS1", "REGRESSION_CI", "ALL_CI", "ALL", "SMOKE", "SMOKE_CI" }, description = "[191421],[198452]")
	public final void verifyHoursOfOperationRA() throws Exception {
		SoftAssert sa = new SoftAssert();
		login("RA_HELP_AND_SUPPORT_EMAIL", "RA_HELP_AND_SUPPORT_PASSWORD");
		gotoHelpAndSupportTab();
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
		HashMap<String, String> hoursOfOperationInfoHnS = settingsPage.getHoursOfOperationDetailsHelpAndSupport();
		
		settingsPage.clickOnElementsOfSettingsPage("hpAssistanceTab");
		settingsPage.waitForElementsOfSettingsPage("partnerAssistancetitle");
		sa.assertTrue(settingsPage.getTextOfSettingsPage("hoursOfOperationsTitle").equalsIgnoreCase(settingsPage.getTextLanguage(LanguageCode, "daas_ui", "settings.hours_of_operation.title")), "Title for Hours of Operation for RA does not match.");
		sa.assertTrue(settingsPage.verifyHoursOfOperationHelpAndSupport(hoursOfOperationInfoHnS, LanguageCode, timing), "Hours of Operation for Help & Support is not updated properly");
		sa.assertAll();
		LOGGER.info("Validation of Hours of Operation for Help & Support functionality completed.");
	}

	/*
	 * This test case configures Country and timezone.
	 */
	@Test(priority = 13, groups = { "REGRESSIONSETTINGS1", "REGRESSION_CI", "ALL_CI", "ALL", "SMOKE", "SMOKE_CI" })
	public final void verifyProvideCountryAndTimeZone() throws Exception {
		SoftAssert sa = new SoftAssert();
		login("MSP_SETTINGS_EMAIL_SA", "MSP_SETTINGS_PASSWORD_SA");
		gotoSettingsTab();
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
		settingsPage.clickOnElementsOfSettingsPage("helpAndSupportTabSettingsPage");
		HashMap<String, String> hoursOfOperationInfoHnS = settingsPage.getHoursOfOperationDetailsHelpAndSupport();
		HashMap<String, String> countryAndTimeZoneInfo = settingsPage.getCountryAndTimeZoneDetails();

		sa.assertTrue(settingsPage.verifyCountryAndTimeZoneSave(countryAndTimeZoneInfo), "Country and Timezone(save) is not working properly");
		for (int i = 0; i < 2; i++) {
			countryAndTimezone = settingsPage.getTextFromCountryTimeZone(hoursOfOperationInfoHnS);
		}
		sa.assertAll();
		LOGGER.info("Configuration of Country & Timezone completed.");
	}

	/*
	 * This test case validates Hours of Operation functionality on Help & Support page.
	 */
	@Test(priority = 14, dependsOnMethods = "verifyProvideCountryAndTimeZone", groups = { "REGRESSIONSETTINGS1", "REGRESSION_CI", "ALL_CI", "ALL", "SMOKE", "SMOKE_CI" }, description = "[198452],[191421]")
	public final void verifyCountryAndTimezoneRA() throws Exception {
		SoftAssert sa = new SoftAssert();
		login("RA_HELP_AND_SUPPORT_EMAIL", "RA_HELP_AND_SUPPORT_PASSWORD");
		gotoHelpAndSupportTab();
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();

		HashMap<String, String> hoursOfOperationInfoHnS = settingsPage.getHoursOfOperationDetailsHelpAndSupport();
		settingsPage.clickOnElementsOfSettingsPage("hpAssistanceTab");
		settingsPage.waitForElementsOfSettingsPage("partnerAssistancetitle");
		sa.assertTrue(settingsPage.verifyCountryAndTimezoneHelpAndSupport(hoursOfOperationInfoHnS, countryAndTimezone), "Hours of Operation for Help & Support is not updated properly");
		sa.assertAll();
		LOGGER.info("Validation of Country and timezone for Help & Support completed.");
	}

	/*
	 * This test case configures public Holidays.
	 */
	@Test(priority = 15, groups = { "REGRESSIONSETTINGS1", "REGRESSION_CI", "ALL_CI", "ALL", "SMOKE", "SMOKE_CI" })
	public final void verifyProvidePublicHolidays() throws Exception {
		SoftAssert sa = new SoftAssert();
		login("MSP_SETTINGS_EMAIL_SA", "MSP_SETTINGS_PASSWORD_SA");
		gotoSettingsTab();
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
		settingsPage.clickOnElementsOfSettingsPage("helpAndSupportTabSettingsPage");
		HashMap<String, String> hoursOfOperationInfoHnS = settingsPage.getHoursOfOperationDetailsHelpAndSupport();
		sa.assertTrue(settingsPage.verifyExcludePublicHoliday(LanguageCode, "statusTextHoliday", "toggleHoliday"), "Exclude Public Holiday is not working properly.");
		statusTextPublicHoliday = settingsPage.getTextFromPublicHoliday(hoursOfOperationInfoHnS);
		sa.assertAll();
		LOGGER.info("Configuration of Exclude Public Holiday completed.");
	}

	/*
	 * This test case validates Hours of Operation functionality on Help & Support page.
	 */
	@Test(priority = 16, dependsOnMethods = "verifyProvidePublicHolidays", groups = { "REGRESSIONSETTINGS1", "REGRESSION_CI", "ALL_CI", "ALL", "SMOKE", "SMOKE_CI","STABILIZATION_STAGING" }, description = "[198452],[191421]")
	public final void verifyPublicHolidayRA() throws Exception {
		SoftAssert sa = new SoftAssert();
		login("RA_HELP_AND_SUPPORT_EMAIL", "RA_HELP_AND_SUPPORT_PASSWORD");
		gotoHelpAndSupportTab();
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();

		HashMap<String, String> hoursOfOperationInfoHnS = settingsPage.getHoursOfOperationDetailsHelpAndSupport();
		settingsPage.clickOnElementsOfSettingsPage("hpAssistanceTab");
		settingsPage.waitForElementsOfSettingsPage("partnerAssistancetitle");
		sa.assertTrue(settingsPage.verifyPublicHolidayHelpAndSupport(hoursOfOperationInfoHnS, statusTextPublicHoliday, LanguageCode), "Hours of Operation for Help & Support is not updated properly");
		sa.assertAll();
		LOGGER.info("Validation of Exclude public holiday for Help & Support completed.");
	}

	/*
	 * This test case configures Direct support.
	 */
	@Test(priority = 17, groups = { "REGRESSIONSETTINGS1", "REGRESSION_CI", "ALL_CI", "ALL", "SMOKE", "SMOKE_CI" })

	public final void verifyProvideDirectSupport() throws Exception {
		SoftAssert sa = new SoftAssert();
		login("MSP_SETTINGS_EMAIL_SA", "MSP_SETTINGS_PASSWORD_SA");
		gotoSettingsTab();
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
		settingsPage.clickOnElementsOfSettingsPage("helpAndSupportTabSettingsPage");
		HashMap<String, String> directSupportInfo = settingsPage.getDirectSupportDetails();
		for (int i = 0; i < 2; i++) {
			statusTextDirectSupport = settingsPage.getTextFromDirectSupport(directSupportInfo, LanguageCode);
		}
		sa.assertAll();
		LOGGER.info("Configuration of Direct Support completed.");
	}

	/*
	 * This test case validates Direct Support functionality on Help & Support page.
	 */
	@Test(priority = 18, dependsOnMethods = "verifyProvideDirectSupport", groups = { "REGRESSIONSETTINGS1", "REGRESSION_CI", "ALL_CI", "ALL", "SMOKE", "SMOKE_CI","STABILIZATION_STAGING" }, description = "[198452],[191421]")
	public final void verifyDirectSupportRA() throws Exception {
		SoftAssert sa = new SoftAssert();
		login("RA_HELP_AND_SUPPORT_EMAIL", "RA_HELP_AND_SUPPORT_PASSWORD");
		gotoHelpAndSupportTab();
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();

		HashMap<String, String> directSupportInfoHnS = settingsPage.getDirectSupportDetailsHelpAndSupport();
		settingsPage.clickOnElementsOfSettingsPage("hpAssistanceTab");
		settingsPage.waitForElementsOfSettingsPage("partnerAssistancetitle");
		sa.assertTrue(settingsPage.verifyDirectSupportHelpAndSupport(directSupportInfoHnS, statusTextDirectSupport, LanguageCode));
		sa.assertAll();
		LOGGER.info("Validation of Direct Support for Help & Support completed.");
	}

	/*
	 * This test case extracts email id from Email Support.
	 */
	@Test(priority = 19, groups = { "REGRESSIONSETTINGS1", "REGRESSION_CI", "ALL_CI", "ALL", "SMOKE", "SMOKE_CI" })
	public final void verifyExtractEmail() throws Exception {
		SoftAssert sa = new SoftAssert();
		login("MSP_SETTINGS_EMAIL_SA", "MSP_SETTINGS_PASSWORD_SA");
		gotoSettingsTab();
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
		settingsPage.clickOnElementsOfSettingsPage("helpAndSupportTabSettingsPage");
		HashMap<String, String> emailSupportInfo = settingsPage.getEmailSupportDetails();
		emailExtract = settingsPage.extractEmailFromEmailSupport(emailSupportInfo,"MSP");
		sa.assertAll();
		LOGGER.info("Extraction of email id completed.");
	}

	/*
	 * This test case configures Callback text.
	 */
	@Test(priority = 20, groups = { "REGRESSIONSETTINGS1", "REGRESSION_CI", "ALL_CI", "ALL", "SMOKE", "SMOKE_CI" })
	public final void verifyProvideCallbackText() throws Exception {
		SoftAssert sa = new SoftAssert();
		login("MSP_SETTINGS_EMAIL_SA", "MSP_SETTINGS_PASSWORD_SA");
		gotoSettingsTab();
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
		settingsPage.clickOnElementsOfSettingsPage("helpAndSupportTabSettingsPage");
		statusTextCallback = settingsPage.getTextOfSettingsPage("callbackStatusText");
		sa.assertAll();
		LOGGER.info("Configuration of Callback Support completed.");
	}

	/*
	 * This test case validates Callback functionality on Help & Support page.
	 */
	@Test(priority = 21, dependsOnMethods = "verifyProvideCallbackText", groups = { "REGRESSIONSETTINGS1", "REGRESSION_CI", "ALL_CI", "ALL", "SMOKE", "SMOKE_CI","STABILIZATION_STAGING" }, description = "[198452],[191421]")
	public final void verifyCallbackTileRA() throws Exception {
		SoftAssert sa = new SoftAssert();
		login("RA_HELP_AND_SUPPORT_EMAIL", "RA_HELP_AND_SUPPORT_PASSWORD");
		gotoHelpAndSupportTab();
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();

		HashMap<String, String> callbackRequestInfoHnS = settingsPage.getCallbackRequestDetailsHelpAndSupport();
		settingsPage.clickOnElementsOfSettingsPage("hpAssistanceTab");
		settingsPage.waitForElementsOfSettingsPage("partnerAssistancetitle");
		sa.assertTrue(settingsPage.verifyCallbackHelpAndSupport(callbackRequestInfoHnS, statusTextCallback, LanguageCode), "Callback tile validations failed");
		sa.assertAll();
		LOGGER.info("Validation of callback tile for Help & Support completed.");
	}

	/*
	 * Test case will be enabled once NFR Story 528295: [Core][DeviceEnroll] Help and Support test emails from LDK into ServiceNow is done
	 * This test case validates Email Request functionality on Help & Support page.
	 */
	@Test(priority = 22, dependsOnMethods = "verifyExtractEmail", groups = { "REGRESSIONSETTINGS1", "REGRESSION_CI", "ALL_CI", "ALL", "SMOKE", "SMOKE_CI","STABILIZATION_STAGING" }, description = "[191423],[217842]", enabled = false)
	public final void verifyEmailRequestRA() throws Exception {
		login("RA_HELP_AND_SUPPORT_EMAIL", "RA_HELP_AND_SUPPORT_PASSWORD");
		SoftAssert sa = new SoftAssert();
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
		gotoHelpAndSupportTab();
		
		HashMap<String, String> emailRequestInfoHnS = settingsPage.getEmailRequestDetailsHelpAndSupport();
		HashMap<String, String> emailContentInfoHnS = settingsPage.getEmailContentHelpAndSupport();

		sa.assertTrue(settingsPage.verifyEmailRequestMyselfHelpAndSupport(emailRequestInfoHnS, LanguageCode,"MSP"), "Email Request for myself is not working properly.");
		Mailinator objMailinator = new Mailinator("", getCredentials(environment, "RA_EMAIL_REQUEST_MAIL_ID"));
		sleeper(5000);
		MailinatorMail objMailinatorEmail = objMailinator.getSpecificEmailUsingIncidentCode("Support Request", emailExtract, true);
		Assert.assertNotNull(objMailinatorEmail, "Test failed as email is not received for submitted email request for myself.");
		validateEmailContentForRA(emailContentInfoHnS, objMailinatorEmail, emailExtract);

		sa.assertTrue(settingsPage.verifyEmailRequestOtherHelpAndSupport(emailRequestInfoHnS, LanguageCode), "Email Request for other user is not working properly.");
		sa.assertNotNull(objMailinatorEmail, "Test failed as email is not received for submitted email request for other user.");
		validateEmailContentForRA(emailContentInfoHnS, objMailinatorEmail, emailExtract);

		sa.assertAll();
		LOGGER.info("Validation of Email Request of RA completed.");
	}

	/*
	 * This test case configures Callback text.
	 */
	@Test(priority = 23, groups = { "REGRESSIONSETTINGS1", "REGRESSION_CI", "ALL_CI", "ALL", "SMOKE", "SMOKE_CI" })
	public final void verifyEnableCallback() throws Exception {
		SoftAssert sa = new SoftAssert();
		login("MSP_SETTINGS_EMAIL_SA", "MSP_SETTINGS_PASSWORD_SA");
		gotoSettingsTab();
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
		settingsPage.clickOnElementsOfSettingsPage("helpAndSupportTabSettingsPage");
		sa.assertTrue(settingsPage.enableCallbackFunctionality(LanguageCode, "statusTextOfCallback", "toggleCallback"), "Callback is not getting enabled");
		sa.assertAll();
		LOGGER.info("Configuration of Callback Support completed.");
	}

	/*
	 * This test case validates Callback Request functionality on Help & Support page.
	 */
	@Test(priority = 24,dependsOnMethods = { "verifyEnableCallback" } , groups = { "REGRESSIONSETTINGS1", "REGRESSION_CI", "ALL_CI", "ALL", "SMOKE", "SMOKE_CI","STABILIZATION_STAGING" }, description = "[191425],[217506]")
	public final void verifyCallbackRequestRA() throws Exception {
		SoftAssert sa = new SoftAssert();
		login("RA_HELP_AND_SUPPORT_EMAIL", "RA_HELP_AND_SUPPORT_PASSWORD");
		gotoHelpAndSupportTab();
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();

		HashMap<String, String> callbackRequestInfoHnS = settingsPage.getCallbackRequestDetailsHelpAndSupport();
		settingsPage.clickOnElementsOfSettingsPage("hpAssistanceTab");
		//settingsPage.waitForElementsOfSettingsPage("partnerAssistancetitle");
		sa.assertTrue(settingsPage.verifyCallbackRequestMyselfHelpAndSupport(callbackRequestInfoHnS, LanguageCode,"MSP"), "Callback Request for myself is not working properly.");
		sa.assertTrue(settingsPage.verifyCallbackRequestOtherHelpAndSupport(callbackRequestInfoHnS, LanguageCode), "Callback Request for other user is not working properly.");
		sa.assertAll();
		LOGGER.info("Validation of Callback Request of RA completed.");
	}

	@DataProvider
	public Object[][] loginDataForTNC() {
		Object[][] data = new Object[2][2];
		data[0][0] = "RESELLER_NEW_UI_EMAIL_US";
		data[0][1] = "RESELLER_NEW_UI_PASSWORD_US";
		data[1][0] = "MSP_SETTINGS_EMAIL_SA";
		data[1][1] = "MSP_SETTINGS_PASSWORD_SA";
		return data;
	}
	
	@DataProvider
	public Object[][] loginDataForRolesCheck() {
		Object[][] data = new Object[3][2];
		data[0][0] = "RESELLER_NEW_UI_EMAIL_US";
		data[0][1] = "RESELLER_NEW_UI_PASSWORD_US";
		data[1][0] = "MSP_SETTINGS_EMAIL_SA";
		data[1][1] = "MSP_SETTINGS_PASSWORD_SA";
		data[2][0] = "IT_ADMIN_COMPANY_USER_EMAIL_COMPANIES";
		data[2][1] = "IT_ADMIN_COMPANY_USER_PASSWORD_COMPANIES";
		return data;
	}

	@Test(priority = 25, groups = { "REGRESSIONSETTINGS1", "REGRESSION_CI", "ALL_CI", "ALL", "SMOKE", "SMOKE_CI","STABILIZATION_STAGING" }, description = "This test check the terms and conditions hyperlink")
	public final void verifyTermsandConditions() throws Exception {
		SoftAssert sa = new SoftAssert();
		login("IT_ADMIN_COMPANY_USER_EMAIL_COMPANIES","IT_ADMIN_COMPANY_USER_PASSWORD_COMPANIES");
		gotoHelpAndSupportTab();
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
		waitForPageLoaded();

		settingsPage.waitForElementsOfSettingsPage("termsAndConditions");
		settingsPage.clickOnElementsOfSettingsPage("termsAndConditions");
		settingsPage.switchToDifferentTab();
		sa.assertTrue(settingsPage.getTextOfSettingsPage("termsAndConditionsTitle").contains(settingsPage.getTextLanguage(LanguageCode, "daas_ui", "help.info.legal.terms.text")), "Title matching failed");
		sa.assertTrue(settingsPage.verifyElementsOfSettingsPage("printButton"), "Print Button not present");
		sa.assertTrue(settingsPage.verifyElementsOfSettingsPage("hpIcon"), "HP icon not present");
		sa.assertTrue(settingsPage.verifyElementsOfSettingsPage("hpDaas"), "HP Daas not present");
		sa.assertTrue(settingsPage.verifyElementsOfSettingsPage("userProfileIcon"), "User Profile icon not present");
		String url = SetTestEnvironments.getEnvironment(System.getProperty("environment")) + ConstantURL.TERMS_AND_CONDITIONS + SetTestEnvironments.getLanguageCode(System.getProperty("languageCode"));
		sa.assertTrue(url.equals(settingsPage.getUrlOfCurrentPage()), "URL not matching");
		settingsPage.switchBackToPreviousTab();

		LOGGER.info("Validation of Terms and Conditions completed.");

		settingsPage.waitForElementsOfSettingsPage("privacy");
		settingsPage.clickOnElementsOfSettingsPage("privacy");
		settingsPage.switchToDifferentTab();
		sleeper(10000); // Sleeper cannot be avoided as Page is taking 7 to 8 seconds to load
		sa.assertTrue(settingsPage.getUrlOfCurrentPage().contains(settingsPage.getTextLanguage(LanguageCode, "daas_ui", "welcome.privacy").toLowerCase()), "Page not redirected to Privacy page");
		settingsPage.switchBackToPreviousTab();

		LOGGER.info("Validation of Privacy completed.");

		settingsPage.waitForElementsOfSettingsPage("service");
		settingsPage.clickOnElementsOfSettingsPage("service");
		settingsPage.switchToDifferentTab();
		sa.assertTrue(settingsPage.getTextOfSettingsPage("serviceTitle").contains(settingsPage.getTextLanguage(LanguageCode, "daas_ui", "help.info.legal.sla.text")), "Title matching failed");
		sa.assertTrue(settingsPage.verifyElementsOfSettingsPage("printButton"), "Print Button not present");
		sa.assertTrue(settingsPage.verifyElementsOfSettingsPage("hpIcon"), "HP icon not present");
		sa.assertTrue(settingsPage.verifyElementsOfSettingsPage("hpDaas"), "HP Daas not present");
		sa.assertTrue(settingsPage.verifyElementsOfSettingsPage("userProfileIcon"), "User Profile Icon not present");
		url = SetTestEnvironments.getEnvironment(System.getProperty("environment")) + ConstantURL.SLA + SetTestEnvironments.getLanguageCode(System.getProperty("languageCode"));
		sa.assertTrue(url.equals(settingsPage.getUrlOfCurrentPage()), "URL not matching");
		settingsPage.switchBackToPreviousTab();

		LOGGER.info("Validation of Service Level Agreement completed.");

		sa.assertAll();
		LOGGER.info("Validation of hyperlinks under legal section completed");
	}

	//Disabling the test case since this is being validated in other tests as well.
	@Test(priority = 26, groups = { "REGRESSIONSETTINGS1", "REGRESSION_CI", "ALL_CI", "ALL", "SMOKE", "SMOKE_CI" }, description = "This test check the terms and conditions hyperlink",enabled=false)
	public final void verifyPageLoadHelpAndSupport() throws Exception {
		SoftAssert sa = new SoftAssert();
		login("RA_HELP_AND_SUPPORT_FRESH_CONFIG_UNAME", "RA_HELP_AND_SUPPORT_FRESH_CONFIG_PASS");
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();

		gotoHelpAndSupportTab();
		if (toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, "IT_ADMIN_HELP_AND_SUPPORT_EMAIL"))) {
			settingsPage.waitForElementsOfSettingsPage("helpAndSupportTitleTextOnBreadcrumbs");
			sa.assertTrue(settingsPage.verifyElementsOfSettingsPage("helpAndSupportTitleTextOnBreadcrumbs"), "Help & Support Page did not load successfully.");
		}else{
			sa.assertTrue(settingsPage.verifyElementsOfSettingsPage("helpAndSupportTitleText"), "Help & Support Page did not load successfully.");
		}
		sa.assertAll();
		LOGGER.info("Validation of Help & Support page for fresh configuration got completed.");
	}

	/**
	 * This method is used to validate email content whether email content contain the expected information
	 * 
	 * @param emailContentInfoHnS: parameters for email content
	 * @param objMailinatorEmail: parameters to get data from mail api
	 * @param emailExtract: email extract from email support section of MSP settings
	 * @throws Exception
	 */
	public void validateEmailContentForRA(Map<String, String> emailContentInfoHnS, MailinatorMail objMailinatorEmail, String emailExtract) throws Exception {
		String to = objMailinatorEmail.getTo();
		String from = objMailinatorEmail.getFrom();
		SoftAssert objSoftAssert = new SoftAssert();
		try {
			for (Map.Entry<String, String> entry : emailContentInfoHnS.entrySet()) {
				String key = entry.getKey();
				switch (key.toLowerCase()) {
				case "to:":
					objSoftAssert.assertTrue(to.equalsIgnoreCase(emailExtract), "Mail did not recieve at recepient.");
					break;
				case "from:":
					objSoftAssert.assertTrue(from.equalsIgnoreCase("HP DaaS"), "Mail did not generate from correct sender");
					break;
				default:
					LOGGER.error("Incorrect email parameter is passed for this method, please check " + key);
					break;
				}
			}
		} catch (Exception e) {
			e.getMessage();
		}
	}

	/**
	 * This method is validate SNOW can configured at Global level using MSP login. This method is validate URL field error message occurs when wrong URL entered at Global level.
	 * Test Case 211655: [SNOW] Verify if SNOW url is configured at A-MSP level then any B-MSP will not allowed to use same url for SNOW setting.
	 */
	@Test(priority = 27, groups = { "REGRESSIONSETTINGS1", "REGRESSION_CI", "ALL_CI", "ALL", "SMOKE", "SMOKE_CI" }, description = "213923,221501,221500,185719,221503,221755,221743,221504,215449,215274,215220,215266,211655,221499,221769", enabled = false)
	public final void verifyMSPIsAbleToConfiguredSNOWAtGlobalLevel() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login("MSP_SNOW_ADMIN_EMAIL", "MSP_SNOW_ADMIN_PASSWORD");
		SNOWInstanceWakeUP snowInstanceWakeUP = new SNOWInstanceWakeUP();
		snowInstanceWakeUP.wakeUpSNOWInstance(ConstantURL.SNOW_URL2, ConstantURL.SNOW_EMAIL2, ConstantURL.SNOW_USERNAME, ConstantURL.SNOW_PASSWORD1);
		gotoSettingsTab();
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
		softAssert.assertTrue(settingsPage.configuredSNOWAtGlobalLevel(ConstantURL.SNOW_URL2, ConstantURL.SNOW_USERNAME, ConstantURL.SNOW_PASSWORD1, LanguageCode), "MSP is not able to configured SNOW at global level.");
		LOGGER.info("SNOW is configured successfully at Global level using MSP login.");
		String[] data = settingsPage.getClientIDAndClientSecretFromGlobalSNOW();
		snowInstanceWakeUP.wakeUpSNOWInstance(ConstantURL.SNOW_URL1, ConstantURL.SNOW_EMAIL1, ConstantURL.SNOW_USERNAME, ConstantURL.SNOW_PASSWORD1);
		softAssert.assertTrue(settingsPage.configuredSNOWAtGlobalLevel(ConstantURL.SNOW_URL1, ConstantURL.SNOW_USERNAME, ConstantURL.SNOW_PASSWORD1, LanguageCode), "MSP is not able to configured SNOW at global level.");
		LOGGER.info("SNOW is configured successfully at Global level using MSP login.");
		String[] data1 = settingsPage.getClientIDAndClientSecretFromGlobalSNOW();
		softAssert.assertFalse((data[0].equals(data1[0])) && (data[1].equals(data1[1])), "ClientID and ClientSecret of SNOW url" + ConstantURL.SNOW_URL2 + " is not different than SNOW url " + ConstantURL.SNOW_URL1 + " Failed test case: 221499");
		LOGGER.info("Validated ClientID and ClientSecret of SNOW url " + ConstantURL.SNOW_URL2 + " is different than SNOW url " + ConstantURL.SNOW_URL1 + " Passed test case: 221499");
		logout();
		login("MSP_SNOW_ADMIN_EMAIL1", "MSP_SNOW_ADMIN_PASSWORD1");
		gotoSettingsTab();
		softAssert.assertTrue(settingsPage.verifySNOWFormAtGlobalLevel(LanguageCode, "right URL", ConstantURL.SNOW_URL1), "'The ServiceNow URL is in use by another company.'error message is not displayed though tried to configured already configured SNOW URL for another MSP. Failed test case: 215266,211655,221499");
		LOGGER.info("'The ServiceNow URL is in use by another company.' message is successfully displayed at MSP level.Passed test case: 215266,211655,221499,221769");
		softAssert.assertTrue(settingsPage.verifySNOWFormAtGlobalLevel(LanguageCode, "wrong URL", "https://test.test.com"), "'Invalid ServiceNow URL'error message is not displayed successfully if tried to add wrong url.");
		LOGGER.info("'Invalid ServiceNow URL' is displayed successfully at MSP level.");
		logout();
		login("MSP_SNOW_ADMIN_EMAIL", "MSP_SNOW_ADMIN_PASSWORD");
		gotoSettingsTab();
		softAssert.assertTrue(settingsPage.resetConfigurationAtGlobalLevel(LanguageCode), "MSP is not able to reset existing configuration.");
		softAssert.assertAll();
		LOGGER.info("SNOW configuration Reset Successfully at Global level.");
	}

	/**
	 * This method is validate URL field error message occurs when Company is trying to use same URL which is configured by MSP.
	 */
	@Test(priority = 28, groups = { "REGRESSIONSETTINGS1", "REGRESSION_CI", "ALL_CI", "ALL", "SMOKE", "SMOKE_CI" }, description = "211653,211651,216970,216964,221577", enabled = false)
	public final void verifyIfSNOWUrlUsedByOneThanItWillNotUsedByOtherUser() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
		gotoRootCompaniesTab();
		impersonateCompanyByEmailForRootAdmin(CompaniesVariables.SNOW_ENABLE_COMPANY_RA_EMAIL1);
		goToPreferencesTab();
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		String snowStatusCurrent = companiesPage.setCompanySNOWToggleAtRoot(getTextLanguage(LanguageCode, "daas_ui", "settings.service_now.config.options.single_tenant"), LanguageCode);
		softAssert.assertTrue(snowStatusCurrent.equals(getTextLanguage(LanguageCode, "daas_ui", "settings.service_now.config.options.single_tenant")), "Root Admin is not able to selected given SNOW status for company " + CompaniesVariables.SNOW_ENABLE_COMPANY_RA_EMAIL1);
		gotoRootCompaniesTab();
		impersonateCompanyByEmailForRootAdmin(CompaniesVariables.SNOW_ENABLE_COMPANY_REPORT_ADMIN_EMAIL);
		goToPreferencesTab();
		String snowStatusCurrent1 = companiesPage.setCompanySNOWToggleAtRoot(getTextLanguage(LanguageCode, "daas_ui", "settings.service_now.config.options.single_tenant"), LanguageCode);
		softAssert.assertTrue(snowStatusCurrent1.equals(getTextLanguage(LanguageCode, "daas_ui", "settings.service_now.config.options.single_tenant")), "Root Admin is not able to selected given SNOW status for company " + CompaniesVariables.SNOW_ENABLE_COMPANY_REPORT_ADMIN_EMAIL);
		logout();
		login("MSP_SNOW_ADMIN_EMAIL", "MSP_SNOW_ADMIN_PASSWORD");
		SNOWInstanceWakeUP snowInstanceWakeUP = new SNOWInstanceWakeUP();
		snowInstanceWakeUP.wakeUpSNOWInstance(ConstantURL.SNOW_URL2, ConstantURL.SNOW_EMAIL2, ConstantURL.SNOW_USERNAME, ConstantURL.SNOW_PASSWORD1);
		gotoSettingsTab();
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
		softAssert.assertTrue(settingsPage.configuredSNOWAtGlobalLevel(ConstantURL.SNOW_URL2, ConstantURL.SNOW_USERNAME, ConstantURL.SNOW_PASSWORD1, LanguageCode), "MSP is not able to configured SNOW at global level with SNOW url " + ConstantURL.SNOW_URL2);
		gotoCompaniesTab();
		impersonateCompanyByCompanyName(CompaniesVariables.SNOW_COMPANY_NAME);
		goToPreferencesTab();
		PreferencesPage preferencesPage = new PreferencesPage(PreDefinedActions.getDriver()).getInstance();
		softAssert.assertTrue(preferencesPage.alreadyConfiguredURLtenantLevelSNOWConfiguration(LanguageCode, ConstantURL.SNOW_URL2, ConstantURL.SNOW_USERNAME, ConstantURL.SNOW_PASSWORD1), "Error Company under 2nd MSP is able to configured SNOW at company level with same URL used by MSP at global level. Failed Test Case: 211651");
		LOGGER.info("Validated When MSP configured SNOW at global level child Company is not able to configured SNOW at Company level. Covered TestCases: 211651");
		logout();
		login("MSP_SNOW_ADMIN_EMAIL1", "MSP_SNOW_ADMIN_PASSWORD1");
		gotoCompaniesTab();
		impersonateCompanyByCompanyName(CompaniesVariables.SNOW_COMPANY_NAME1);
		goToPreferencesTab();
		softAssert.assertTrue(preferencesPage.alreadyConfiguredURLtenantLevelSNOWConfiguration(LanguageCode, ConstantURL.SNOW_URL2, ConstantURL.SNOW_USERNAME, ConstantURL.SNOW_PASSWORD1), "Error Company under 2nd MSP is able to configured SNOW at Company level with same URL used by MSP 1 at global level. Failed Test Case: 211653");
		LOGGER.info("Validated When MSP configured SNOW than company under MSP 2 is not able to configured SNOW with the same SNOW URL. Covered TestCases:211653");
		logout();
		login("MSP_SNOW_ADMIN_EMAIL", "MSP_SNOW_ADMIN_PASSWORD");
		gotoSettingsTab();
		softAssert.assertTrue(settingsPage.resetConfigurationAtGlobalLevel(LanguageCode), "MSP is not able to reset existing configuration.");
		softAssert.assertAll();
		LOGGER.info("SNOW configuration Reset Successfully at Global level.");
	}

	/**
	 * This test case verifies support test mail functionality
	 * 
	 * @param 'username' - username of the partner
	 * @param 'password' - password of the partner
	 * @throws Exception
	 */
	@Test(priority = 29, groups = { "REGRESSIONSETTINGS2", "SMOKE", "REGRESSION_CI", "SMOKE_CI", "PRODUCTION_SANITY" }, description = "Test Case ID : 306637, 306627, 783813, 783607, 783608, 782584, 783605, 783610 ; Roles ~ Reseller, Reseller Specialist, Service Specialist")
	public final void verifyPartnerAndChannelSupportTile() throws Exception {
		login("RESELLER_NEW_UI_EMAIL_US", "RESELLER_NEW_UI_PASSWORD_US");
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		String subject = generateRandomString(11);
		String description = generateRandomString(11);
		//String actualMailContent = null;
		ArrayList<String> partnerDetails = new ArrayList<String>();

		//settingsPage.waitForElementsOfSettingsPage("tableOverlay");
		gotoSettingsTab();
		LOGGER.info("Redirected to settings tab");
		waitForPageLoaded();
		//settingsPage.waitForElementsOfSettingsPage("tableOverlay");
		partnerDetails.add(settingsPage.getTextOfSettingsPage("primaryAdministratorName"));
		partnerDetails.add(settingsPage.getTextOfSettingsPage("primaryAdministratorEmail"));
		partnerDetails.add(settingsPage.getTextOfSettingsPage("primaryAdministratorPhone"));
		partnerDetails.add(settingsPage.getTextOfSettingsPage("primaryAdministratorCountry"));
		partnerDetails.add(settingsPage.getTextOfSettingsPage("primaryAdministratorPartnerID"));
		
//		String expectedMailContent = ( getTextLanguage(LanguageCode, "idm", "support_mailer.support_email.subject.partner")+ "  "+getTextLanguage(LanguageCode, "lhserver", "support_mailer.callback_email.greeting").replace("%{team_name}",getTextLanguage(LanguageCode, "lhserver", "support_admin.users.headings.index"))+"  " + getTextLanguage(LanguageCode, "lhserver", "support_mailer.support_email.message").replace("%{user_name}", partnerDetails.get(0)).replace("(%{email})","("+partnerDetails.get(1).trim()+")") + "  "+ getTextLanguage(LanguageCode, "idm", "support_mailer.label.partner_name") + "  "
//				+ partnerDetails.get(0) + "  " + getTextLanguage(LanguageCode, "idm", "support_mailer.label.partner_id") + "  "+ partnerDetails.get(4) + "  "+ getTextLanguage(LanguageCode, "idm", "support_mailer.label.partner_country") + "  " + partnerDetails.get(3)  + "  "+ getTextLanguage(LanguageCode, "idm", "support_mailer.label.account_email") + "  " + partnerDetails.get(1)  + "  "+ getTextLanguage(LanguageCode, "idm", "support_mailer.label.requested_for_other") + "  " + getTextLanguage(LanguageCode, "daas_ui", "partners.list.ptype.data.reseller")  + "  "+ getTextLanguage(LanguageCode, "idm", "support_mailer.label.office_phone")
//				+ "  " + getTextLanguage(LanguageCode, "lhserver", "support_mailer.value.not_available")  + "  "+ getTextLanguage(LanguageCode, "lhserver", "support_mailer.label.mobile_phone") + "  "+ getTextLanguage(LanguageCode, "lhserver", "support_mailer.value.not_available") +"  "+ getTextLanguage(LanguageCode, "idm", "support_mailer.label.time_zone") + "  " + "Etc/UTC"  + "  "+ getTextLanguage(LanguageCode, "idm", "support_mailer.label.partner_title")+ "  " + subject  + "  "+ getTextLanguage(LanguageCode, "idm", "onboarding.apps.category") + ": " + SettingsVariables.Email_Category  + "  "+ getTextLanguage(LanguageCode, "idm", "support_mailer.label.description")+ "  " + description
//				 + "  "+"Â"+ getTextLanguage(LanguageCode, "lhserver", "global.copyright").replace("%{current_year}", getCurrentYear())).replace("<b>", " ").replace("</b>", " ").replace("<b >", " ").replace("  ", " ");
		gotoHelpAndSupportTab();
		LOGGER.info("Navigated to help and support page");
		waitForPageLoaded();
		sleeper(3000); // waiting for the elements on the page to load
		//Send Email without Attachment
		softAssert.assertTrue(settingsPage.matchTextOfSettingsPage("partnerAndChannelSupportHeader", getTextLanguage(LanguageCode, "daas_ui", "partner.help.channel_support.title")), "Header on partner and channel support tile is incorrect");
		softAssert.assertTrue(settingsPage.matchTextOfSettingsPage("partnerAndChannelSupportDescription", getTextLanguage(LanguageCode, "daas_ui", "partner.help.channel_support.description")), "Description on partner and channel support tile is incorrect");
		softAssert.assertTrue(settingsPage.matchTextOfSettingsPage("hpdaasSupportHeader", getTextLanguage(LanguageCode, "daas_ui", "wfcpartner.help.support.hp_support.title").replaceAll("[>\\{}]", "").toString().replaceAll("productName","Workforce Experience Platform")), "Header on hpdaas.com support tile is incorrect");
		softAssert.assertTrue(settingsPage.matchTextOfSettingsPage("hpdaasSupportDescription", getTextLanguage(LanguageCode, "daas_ui", "wfcpartner.help.hp_support.description").replaceAll("[>\\{}]", "").toString().replaceAll("productName","Workforce Experience Platform")), "Description on hpdaas.com support tile is incorrect");
		softAssert.assertTrue(settingsPage.verifyElementsOfSettingsPage("emailSupportButton"), "Email support button is not present on hpdaas.com support tile");
		settingsPage.waitForElementsOfSettingsPage("emailSupportButton");
		settingsPage.clickOnElementsOfSettingsPage("emailSupportButton");
		LOGGER.info("Clicked on email support button");
		softAssert.assertTrue(settingsPage.verifyElementsOfSettingsPage("emailSupportModalHeader"), "Header is not present on email support modal");
		softAssert.assertTrue(settingsPage.matchTextOfSettingsPage("subjectLabel", getTextLanguage(LanguageCode, "daas_ui", "help.email.modal.email_subject.label")), "Subject label on email support modal is incorrect");
		softAssert.assertTrue(settingsPage.matchTextOfSettingsPage("emailDescriptionLabel", getTextLanguage(LanguageCode, "daas_ui", "incident.details.description")), "Description label on email support modal is incorrect");
		settingsPage.clickOnElementsOfSettingsPage("emailSupportSubmit");
		LOGGER.info("Clicked on submit button");
		softAssert.assertTrue(settingsPage.matchTextOfSettingsPage("subjectErrorMessage", getTextLanguage(LanguageCode, "lhserver", "form.validate.required")), "Error message on subject field is not generated on submitting empty fields");
		softAssert.assertTrue(settingsPage.matchTextOfSettingsPage("descriptionErrorMessage", getTextLanguage(LanguageCode, "lhserver", "form.validate.required")), "Error message on description field is not generated on submitting empty fields");
		settingsPage.enterTextForSettingsPage("emailSubjectTextBox", subject);
		settingsPage.enterTextForSettingsPage("emailDescriptionTextBox", description);
		settingsPage.clickOnElementsOfSettingsPage("emailSupportCategory");
		settingsPage.selectTextValueFromDropdownOfSettingsPage("emailSupportCategoryOptions", SettingsVariables.Email_Category,"emailSupportCategoryDropdown");
		settingsPage.clickOnElementsOfSettingsPage("emailSupportSubmit");
		softAssert.assertTrue(settingsPage.matchTextOfSettingsPage("toastNotification", getTextLanguage(LanguageCode, "daas_ui", "help.message.email.success")), "Toast notification is not generated after sending the email");
		//actualMailContent = verifyEmailContent(environment, "HELP_&_SUPPORT_SUPPORT_EMAIL", "SUPPORT_EMAIL_SUBJECT", "", true);
		//sleeper(2000);
		//softAssert.assertTrue(actualMailContent.equalsIgnoreCase(expectedMailContent), "Mail content does not match");
		LOGGER.info("Test cases for support test mail functionality without attachment passed successfully");
		
		//Send Email with Attachment
		settingsPage.waitForElementsOfSettingsPage("emailSupportButton");
		settingsPage.clickOnElementsOfSettingsPage("emailSupportButton");
		LOGGER.info("Clicked on email support button");
		settingsPage.enterTextForSettingsPage("emailSubjectTextBox", subject);
		settingsPage.enterTextForSettingsPage("emailDescriptionTextBox", description);
		settingsPage.clickOnElementsOfSettingsPage("emailSupportCategory");
		settingsPage.selectTextValueFromDropdownOfSettingsPage("emailSupportCategoryOptions", SettingsVariables.Email_Category,"emailSupportCategoryDropdown");
		
		//Verify Error Messages for invalid file. 
		fileImportInV3(ConstantPath.IMPORT_PATH + SettingsVariables.FILE_NAME_INVALID);
		settingsPage.clickOnElementsOfSettingsPage("emailSupportSubmit");
		softAssert.assertTrue(settingsPage.matchTextOfSettingsPage("invalidFileErrorMessage", getTextLanguage(LanguageCode, "daas_ui", "help.support_box.fileTypeNotSupported").replace("{supportedFileTypes}","bat,dll,dmg,exe,jar,lib,msi etc.")), "Error message on upload file field is not generated on submitting invalid file.");
		LOGGER.info("Clicked on submit button");
		
		//settingsPage.clickOnElementsOfSettingsPage("UploadBrowseButton");
		sleeper(3000);
		settingsPage.clickOnElementsOfSettingsPage("uploadClearButton");
		fileImportInV3(ConstantPath.IMPORT_PATH + SettingsVariables.FILE_NAME_INVALID_FILE_SIZE);
		settingsPage.clickOnElementsOfSettingsPage("emailSupportSubmit");
		softAssert.assertTrue(settingsPage.matchTextOfSettingsPage("invalidFileErrorMessage", getTextLanguage(LanguageCode, "daas_ui", "help.support_box.fileSizeError").replace("{fileSize}","5")), "Error message on upload file field is not generated on submitting Maximum file size."); //Maestro Strings not translated yet. Once done will remove comments.
		LOGGER.info("Clicked on submit button");
		
		//settingsPage.clickOnElementsOfSettingsPage("UploadBrowseButton");
		sleeper(3000);
		settingsPage.clickOnElementsOfSettingsPage("uploadClearButton");
		fileImportInV3(ConstantPath.IMPORT_PATH + SettingsVariables.FILE_NAME_VALID);
		settingsPage.clickOnElementsOfSettingsPage("emailSupportSubmit");
		softAssert.assertTrue(settingsPage.matchTextOfSettingsPage("toastNotification", getTextLanguage(LanguageCode, "daas_ui", "help.message.email.success")), "Toast notification is not generated after sending the email");
		softAssert.assertAll();
		LOGGER.info("Test cases for support test mail functionality passed successfully");
	}

	@Test(priority = 30, groups = { "REGRESSIONSETTINGS1", "REGRESSION_CI", "STABILIZATION_STAGING"}, description = "This test validates custom role creation for MSP, Partner Administrator, IT Admin Test Cases: 331685,331691,331930,331931",dataProvider = "loginDataForRolesCheck")
	public final void verifyRoleCreation(String username,String password) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login(username,password);
		String tenantID = getValueFromToken("tenant");
		if(username=="IT_ADMIN_COMPANY_USER_EMAIL_COMPANIES")
		gotoNonMSPSettingsTab();
		else
		gotoSettingsTab();
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
		waitForPageLoaded();
		LOGGER.info("Redirected to Settings page");
		if(username=="IT_ADMIN_COMPANY_USER_EMAIL_COMPANIES")
		settingsPage.clickOnElementsOfSettingsPage("roleAndPermissionTabCompany");
		else
		settingsPage.clickOnElementsOfSettingsPage("roleAndPersmissionTab");
        String url = roleIdURL+ConstantURL.ROLEID_URL1+tenantID+ConstantURL.ROLEID_URL2;
		List<String> roleIDs = settingsPage.getAllRoleIds(url,"{}", 0, "id");
		settingsPage.removeAllRoles(tenantID,roleIdURL,roleIDs,environment);
	    softAssert.assertTrue(settingsPage.createPermission(LanguageCode,getTextLanguage(LanguageCode, "daas_ui", "global.yes")),"Custom role did not created successfully.");
		softAssert.assertAll();
		LOGGER.info("Validation of role creation completed successfully for MSP, Partner Administrator, IT Admin.");
	}
	
	@Test(priority = 31,enabled=false)
	public final void verifyCreateNewRoleUsingAPI() throws Exception {
		login("MSP_SETTINGS_EMAIL_SA", "MSP_SETTINGS_PASSWORD_SA");
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();		
		ArrayList<String> roleDetails = new ArrayList<String>();
		gotoSettingsTab();
		waitForPageLoaded();
		LOGGER.info("Redirected to Settings page");
		settingsPage.clickOnElementsOfSettingsPage("roleAndPersmissionTab");
		String tenantID = getValueFromToken("tenant");
		roleDetails = settingsPage.addCustomRoleApi(environment,tenantID,"");
		Assert.assertNotNull(roleDetails, "Role did not get added via API.");
	}
	
	@Test(priority = 32, groups = { "REGRESSIONSETTINGS1", "REGRESSION_CI", "STABILIZATION_STAGING"}, description = "This test validates edit, duplicate, removal of custom role creation for MSP. Test Case: 331701,331842,331862,331889,336665")
	public final void verifyEditDuplicateRemoveLogRoles() throws Exception {
		login("MSP_SETTINGS_EMAIL_SA", "MSP_SETTINGS_PASSWORD_SA");
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();		
		SoftAssert softAssert = new SoftAssert();
		gotoSettingsTab();
		waitForPageLoaded();
		LOGGER.info("Redirected to Settings page");
		settingsPage.clickOnElementsOfSettingsPage("roleAndPersmissionTab");
		String tenantID = getValueFromToken("tenant");
        String url = roleIdURL+ConstantURL.ROLEID_URL1+tenantID+ConstantURL.ROLEID_URL2;
		List<String> roleIDs = settingsPage.getAllRoleIds(url,"{}", 0, "id");
		settingsPage.removeAllRoles(tenantID,roleIdURL,roleIDs,environment);
		settingsPage.clickOnElementsOfSettingsPage("addRolesButton");
		waitForPageLoaded();
		softAssert.assertTrue(settingsPage.getTextOfSettingsPage("informationSideLabel").equalsIgnoreCase(settingsPage.getTextLanguage(LanguageCode, "daas_ui", "global.information")), "Information label text is incorrect");
		softAssert.assertTrue(settingsPage.getTextOfSettingsPage("manageabilitySideLabel").equalsIgnoreCase(settingsPage.getTextLanguage(LanguageCode, "daas_ui", "roles.add.manageability.title")), "Manageability label text is incorrect");
		softAssert.assertTrue(settingsPage.getTextOfSettingsPage("permissionsSideLabel").equalsIgnoreCase(settingsPage.getTextLanguage(LanguageCode, "daas_ui", "roles.add.permissions.title")), "Permissions label text is incorrect");	
		pressKey(Keys.ESCAPE);
		Assert.assertTrue(settingsPage.createPermission(LanguageCode,getTextLanguage(LanguageCode, "daas_ui", "global.yes")),"Custom role did not created successfully.");
		gotoLogTab();
		softAssert.assertTrue(settingsPage.verifyDescriptionOnLogsPage("ADD","CUSTOM"),"Logs did not received after adding role.");
		gotoSettingsTab();
		settingsPage.clickOnElementsOfSettingsPage("roleAndPersmissionTab");
		softAssert.assertTrue(settingsPage.verifyEditRole(LanguageCode),"Custom role edit got failed.");
		gotoLogTab();
		softAssert.assertTrue(settingsPage.verifyDescriptionOnLogsPage("Edit","CUSTOM"),"Logs did not received after edting role.");
		gotoSettingsTab();
		settingsPage.clickOnElementsOfSettingsPage("roleAndPersmissionTab");
		softAssert.assertTrue(settingsPage.verifyDuplicateRole(LanguageCode),"Duplicate custom role did not created successfully.");
		softAssert.assertTrue(settingsPage.verifyRemoveRole(LanguageCode),"Role did not remove successfully.");
		gotoLogTab();
		softAssert.assertTrue(settingsPage.verifyDescriptionOnLogsPage("REMOVE","CUSTOM"),"Logs did not received after removing role.");
		softAssert.assertAll();
		LOGGER.info("Validation of edit,duplicate,remove role,logs check functionality completed successfully.");
	}
	
	@Test(priority = 33, groups = { "REGRESSIONSETTINGS1", "REGRESSION_CI", "STABILIZATION_STAGING"}, description = "This test validates role assignment to a User.TC: 337138")
	public final void verifyRoleAssignment() throws Exception {
		login("MSP_SETTINGS_EMAIL_SA", "MSP_SETTINGS_PASSWORD_SA");
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
		SupportTeamPage supportTeamPage = new SupportTeamPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		String email = generateRandomString(11) + "@mailinator.com";
		String notificationMessage = null;
		gotoSettingsTab();
		waitForPageLoaded();
		LOGGER.info("Redirected to Settings page");
		settingsPage.clickOnElementsOfSettingsPage("roleAndPersmissionTab");
		String tenantID = getValueFromToken("tenant");
        String url = roleIdURL+ConstantURL.ROLEID_URL1+tenantID+ConstantURL.ROLEID_URL2;
		List<String> roleIDs = settingsPage.getAllRoleIds(url,"{}", 0, "id");
		settingsPage.removeAllRoles(tenantID,roleIdURL,roleIDs,environment);
		Assert.assertTrue(settingsPage.createPermission(LanguageCode,getTextLanguage(LanguageCode, "daas_ui", "global.yes")),"Custom role did not created successfully.");
		gotoSupportTeamTab();
		supportTeamPage.clickOnElementsOfSupportTeamPage("supportAddButton");
		settingsPage.clickOnElementsOfSettingsPage("roleDropdownArrow");
		Assert.assertTrue(settingsPage.verifyCustomRoleInRoleDropdown(), "Custom role did not get reflected in roles dropdown.");
		settingsPage.clickByJavaScriptOnSettingsPage("rolesDropdownAfter");
		supportTeamPage.enterTextForSupportTeamPage("firstNameAddMember", SupportVariables.FIRST_NAME);
		supportTeamPage.enterTextForSupportTeamPage("lastNameAddMember", SupportVariables.LAST_NAME);
		supportTeamPage.enterTextForSupportTeamPage("emailAddMember", email);
		supportTeamPage.enterTextForSupportTeamPage("mobileAddMember", SupportVariables.MOBILE_PHONE);
		supportTeamPage.clickOnElementsOfSupportTeamPage("addMember");
		supportTeamPage.waitForElementsOfSupportTeamPage("toastNotification");
		notificationMessage = supportTeamPage.getTextOfSupportTeamPage("toastNotification");
		softAssert.assertTrue((notificationMessage.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "user.created.success"))), "Incorrect notification message is generated after adding support team member");
		sleeper(2000);
		supportTeamPage.enterTextForSupportTeamPage("emailTextbox", email);
		sleeper(3000);
		Assert.assertFalse(supportTeamPage.verifyElementsOfSupportTeamPage("noItemsDisplayText"), "Add functionality for support team member does not work correctly");
		LOGGER.info("Newly added support team member with custom role got reflected on list page.");
		//supportTeamPage.waitForElementsOfSupportTeamPage("tableOverlay");
		softAssert.assertTrue(settingsPage.verifyCustomRoleDetailsPage(), "Custom role did not reflected on support team details page.");
		softAssert.assertAll();
		LOGGER.info("Validation of role assignment functionality completed successfully.");
	}
	
	@Test(priority = 34, groups = { "REGRESSIONSETTINGS2", "REGRESSION_CI" }, description = "333508")
	public final void verifyAddHPApplicationLevelUserManually() throws Exception {
		login("APP_ADMIN_USER", "APP_ADMIN_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
		String firstName = "SelUserFirstName" + generateRandomString(5);
		String lastName = "SelUserLastName" + generateRandomString(5);
		String emailAddress = "seluser" + generateRandomString(5).toLowerCase() + "@gmail.com";
		LOGGER.info("Redirected to Role and Permission page");
		gotoAppAdminUsersTab();
		waitForPageLoaded();
		resetTableConfiguration();
		//settingsPage.waitForElementsOfSettingsPage("tableOverlay");
		settingsPage.clickByJavaScriptOnSettingsPage("addNewUserButton");
		softAssert.assertTrue(settingsPage.getTextOfSettingsPage("addUserTitle").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "administrator.add.user.heading")),"Title on add user popup is incorrect");
		settingsPage.enterTextForSettingsPage("newUserFirstName", firstName);
		settingsPage.enterTextForSettingsPage("newUserLastName", lastName);
		settingsPage.enterTextForSettingsPage("emailAddressTextbox", emailAddress);
		settingsPage.clickOnElementsOfSettingsPage("rolesDropdown");
		settingsPage.selectValueFromSingleSelectDropDownSettingsPage(1,"rolesOptions","rolesDropdown");
		settingsPage.clickByJavaScriptOnSettingsPage("rolesDropdownAfter");
		settingsPage.clickOnElementsOfSettingsPage("cancelAddUser");
		//settingsPage.waitUntilElementIsVisibleOfSettingsPage("tableOverlay");
		settingsPage.enterTextForSettingsPage("userEmailSearchbox", emailAddress);
		//settingsPage.waitUntilElementIsVisibleOfSettingsPage("tableOverlay");
		softAssert.assertTrue(settingsPage.verifyElementsOfSettingsPage("noElementsDisplayText"), "Cancel functionality on adding user manually is not working");
		settingsPage.waitUntilElementIsVisibleOfSettingsPage("tableOverlay");
		settingsPage.clickOnElementsOfSettingsPage("addNewUserButton");
		settingsPage.enterTextForSettingsPage("newUserFirstName", firstName);
		settingsPage.enterTextForSettingsPage("newUserLastName", lastName);
		settingsPage.enterTextForSettingsPage("emailAddressTextbox", emailAddress);
		settingsPage.clickOnElementsOfSettingsPage("rolesDropdown");
		settingsPage.selectValueFromSingleSelectDropDownSettingsPage(1,"rolesOptions","rolesDropdown");
		settingsPage.clickByJavaScriptOnSettingsPage("rolesDropdownAfter");
		sleeper(2000);
		settingsPage.clickOnElementsOfSettingsPage("addUser");
		settingsPage.waitUntilElementIsVisibleOfSettingsPage("tableOverlay");
		resetTableConfiguration();
		waitForPageLoaded();
		settingsPage.enterTextForSettingsPage("userEmailSearchbox", emailAddress);
		//settingsPage.waitUntilElementIsVisibleOfSettingsPage("tableOverlay");
		softAssert.assertTrue(settingsPage.matchTextOfSettingsPage("firstUserEmail", emailAddress), "Add user manually functionality is not working");
		LOGGER.info("User added successfully");
		
		settingsPage.mousehoverOnSettingsPage("firstUserEmail");
		settingsPage.clickByJavaScriptOnSettingsPage("firstUserCheckbox");
		settingsPage.clickOnElementsOfSettingsPage("removeUserButton");
		softAssert.assertTrue(settingsPage.matchTextOfSettingsPage("removeUserTitle", getTextLanguage(LanguageCode, "daas_ui", "users.list.action_buttons.remove_user")), "Title on remove user popup is incorrect");
		softAssert.assertTrue(settingsPage.matchTextOfSettingsPage("removeUserDescription", getTextLanguage(LanguageCode, "daas_ui", "users.remove.message").replace("{name}", firstName + " " + lastName)), "Description on remove user popup is incorrect");
		settingsPage.clickOnElementsOfSettingsPage("removeButton");
		//settingsPage.waitUntilElementIsVisibleOfSettingsPage("tableOverlay");
		
		softAssert.assertAll();
		LOGGER.info("Test cases for verifyAddHPApplicationLevelUserManually passed successfully");
	}
	
	/**
	 * This method will verify the table configuration test cases of application user list page
	 * 
	 * @throws Exception
	 */
	@Test(priority = 35, groups = { "REGRESSIONSETTINGS2", "REGRESSION_CI", "STABILIZATION_STAGING" }, description = "333508")
	public final void verifyTableConfigurationTestCasesForAppUsers() throws Exception {
		login("APP_ADMIN_USER", "APP_ADMIN_PASSWORD");
		LOGGER.info("Redirected to Role and Permission page");
		String tenantID = getValueFromToken("tenant");
		gotoAppAdminUsersTab();
		waitForPageLoaded();
		// verifying search service api
		Assert.assertTrue(getStatusCode(getSearchServiceApi(environment) + CommonVariables.DETAILSSEARCHSERVICEAPI + tenantID + CommonVariables.DETAILSSEARCHSERVICEAPI2, UserVariables.SEARCHSERVICEBODY, "POST", getEnvironment(environment)) == CommonVariables.CODEOK, "Search service failed for application users list page");
		ArrayList<String> columnName = new ArrayList<String>(Arrays.asList(getTextLanguage(LanguageCode, "daas_ui", "users.list.label.name"), getTextLanguage(LanguageCode, "daas_ui", "users.list.label.email"), getTextLanguage(LanguageCode, "daas_ui", "users.list.label.status"), getTextLanguage(LanguageCode, "daas_ui", "users.list.label.roles")));
		ArrayList<String> checkboxValue = new ArrayList<String>(Arrays.asList("true", "true", "true", "true"));
		ArrayList<String> name = new ArrayList<String>(Arrays.asList(getTextLanguage(LanguageCode, "daas_ui", "users.list.label.name")));
		verifyTableConfigurationTests(columnName, checkboxValue, name);
	}
	
	/**
	 * This method will verify the table configuration test cases of roles and permissions list page
	 * 
	 * @throws Exception
	 */
	@Test(priority = 36, groups = { "REGRESSIONSETTINGS2", "REGRESSION_CI", "STABILIZATION_STAGING" }, description = "333512")
	public final void verifyTableConfigurationTestCasesForRolesAndPermissions() throws Exception {
		login("APP_ADMIN_USER", "APP_ADMIN_PASSWORD");
		LOGGER.info("Redirected to Role and Permission page");
		String tenantID = getValueFromToken("tenant");
		waitForPageLoaded();
		// verifying search service api
		Assert.assertTrue(getStatusCode(getSearchServiceApi(environment) + CommonVariables.DETAILSSEARCHSERVICEAPI + tenantID + CommonVariables.DETAILSSEARCHSERVICEAPI2, UserVariables.SEARCHSERVICEBODY, "POST", getEnvironment(environment)) == CommonVariables.CODEOK, "Search service failed for application roles and permissions list page");
		ArrayList<String> columnName = new ArrayList<String>(Arrays.asList(getTextLanguage(LanguageCode, "daas_ui", "users.list.label.name"), getTextLanguage(LanguageCode, "daas_ui", "roles.list.description"), getTextLanguage(LanguageCode, "daas_ui", "roles.list.created_by"), getTextLanguage(LanguageCode, "daas_ui", "roles.list.updated_by"), getTextLanguage(LanguageCode, "daas_ui", "roles.list.created_on"), getTextLanguage(LanguageCode, "daas_ui", "roles.list.updated_on")));
		ArrayList<String> checkboxValue = new ArrayList<String>(Arrays.asList("true", "true", "true", "true", "true", "true"));
		ArrayList<String> name = new ArrayList<String>(Arrays.asList(getTextLanguage(LanguageCode, "daas_ui", "users.list.label.name")));
		verifyTableConfigurationTests(columnName, checkboxValue, name);
	}
	
	/**
	 * This method will verify the table configuration test cases of app logs list page
	 * 
	 * @throws Exception
	 */
	@Test(priority = 37, groups = { "REGRESSIONSETTINGS2", "REGRESSION_CI", "STABILIZATION_STAGING" }, description ="333501")
	public final void verifyTableConfigurationTestCasesForLogs() throws Exception {
		login("APP_ADMIN_USER", "APP_ADMIN_PASSWORD");
		LOGGER.info("Redirected to roles and permissions list page");
		String tenantID = getValueFromToken("tenant");
		gotoLogTab();
		LOGGER.info("Redirected to logs page");
		waitForPageLoaded();
		// verifying search service api
		Assert.assertTrue(getStatusCode(getSearchServiceApi(environment) + CommonVariables.DETAILSSEARCHSERVICEAPI + tenantID + CommonVariables.DETAILSSEARCHSERVICEAPI2, UserVariables.SEARCHSERVICEBODY, "POST", getEnvironment(environment)) == CommonVariables.CODEOK, "Search service failed for logs list page");
		ArrayList<String> columnName = new ArrayList<String>(Arrays.asList(getTextLanguage(LanguageCode, "daas_ui", "logs.date_time"), getTextLanguage(LanguageCode, "daas_ui", "logs.type"), getTextLanguage(LanguageCode, "daas_ui", "logs.source"), getTextLanguage(LanguageCode, "daas_ui", "logs.description"), getTextLanguage(LanguageCode, "daas_ui", "logs.user.name"), getTextLanguage(LanguageCode, "daas_ui", "logs.level")));
		ArrayList<String> checkboxValue = new ArrayList<String>(Arrays.asList("true", "true", "true", "true", "true", "true"));
		ArrayList<String> name = new ArrayList<String>(Arrays.asList(getTextLanguage(LanguageCode, "daas_ui", "logs.date_time")));
		verifyTableConfigurationTests(columnName, checkboxValue, name);
	}
	
	/**
	 * This method will verify roles and permissions details page for application administrator
	 * 
	 * @throws Exception
	 */
	@Test(priority = 38, groups = { "REGRESSIONSETTINGS2", "REGRESSION_CI", "STABILIZATION_STAGING" }, description = "333524")
	public final void verifyRolesAndPermissionsDetailsPage() throws Exception {
		login("APP_ADMIN_USER", "APP_ADMIN_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
		ArrayList<String> roleDetails = new ArrayList<String>();
		LOGGER.info("Redirected to roles and permissions list page");
		waitForPageLoaded();
		resetTableConfiguration();
		sleeper(2000);
		//settingsPage.waitForElementsOfSettingsPage("tableOverlay");
		settingsPage.clickOnElementsOfSettingsPage("createdByDropdown");
		settingsPage.selectTextValueFromDropdownOfSettingsPage("createdByOptions", SettingsVariables.SEEDED,"createdByDropdownCurrentValue");
		pressKey(Keys.ESCAPE);
		//settingsPage.waitUntilElementIsVisibleOfSettingsPage("tableOverlay");
		roleDetails.add(settingsPage.getTextOfSettingsPage("firstRole"));
		roleDetails.add(settingsPage.getTextOfSettingsPage("firstRoleDescription").replaceAll("_", " "));
		settingsPage.clickByJavaScriptOnSettingsPage("firstRole");
		//settingsPage.waitForElementsOfSettingsPage("tableOverlay");
		sleeper(2000);
		softAssert.assertTrue(settingsPage.matchTextOfSettingsPage("identityRoleName", roleDetails.get(0)), "Role name on identity tile is incorrect");
		softAssert.assertTrue(settingsPage.verifyElementsOfSettingsPage("copyIcon"), "Copy icon on role details page is not visible");
		softAssert.assertFalse(settingsPage.verifyElementsOfSettingsPage("deleteIcon"), "Delete icon on role details page is not visible");
		softAssert.assertTrue(settingsPage.getTextOfSettingsPage("informationTitle").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "global.information")), "Title on information tile is incorrect");
		softAssert.assertTrue(settingsPage.getTextOfSettingsPage("nameLabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "global.form.name")), "Label on name field is incorrect");
		softAssert.assertTrue(settingsPage.getTextOfSettingsPage("rolesnPermissionsDescriptionLabel").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "global.form.description")), "Label on description field is incorrect");
		softAssert.assertTrue(settingsPage.getTextOfSettingsPage("levelLabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "roles.add.level")), "Label on level field is incorrect");
		softAssert.assertTrue(settingsPage.getTextOfSettingsPage("nameValue").toLowerCase().equals(roleDetails.get(0).toLowerCase()), "Name of role under information tile is incorrect");
		softAssert.assertTrue(settingsPage.getTextOfSettingsPage("descriptionValue").toLowerCase().contains(roleDetails.get(1).toLowerCase()), "Description of role under information tile is incorrect");
		
		softAssert.assertTrue(settingsPage.getTextOfSettingsPage("manageabilityTitle").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "roles.add.manageability.title")), "Title on manageability tile is incorrect");
		softAssert.assertTrue(settingsPage.getTextOfSettingsPage("managedByLabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "roles.add.managed_by")), "Label on managed by field is incorrect");
		softAssert.assertTrue(settingsPage.getTextOfSettingsPage("managingLabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "roles.add.managing")), "Label on managing field is incorrect");
		
		softAssert.assertTrue(settingsPage.getTextOfSettingsPage("permissionsTitle").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "roles.add.permissions.title")), "Title on permissions tile is incorrect");
		softAssert.assertTrue(settingsPage.getTextOfSettingsPage("permissionsLabel").toLowerCase().equals(getTextLanguage(LanguageCode, "daas_ui", "roles.add.permission").toLowerCase()), "Label on permissions by field is incorrect");
		softAssert.assertTrue(settingsPage.getTextOfSettingsPage("permissionsEnabled").toLowerCase().equals(getTextLanguage(LanguageCode, "daas_ui", "global.enabled").toLowerCase()), "Enabled tag on permissions tile by is not present");
		softAssert.assertAll();
		
		LOGGER.info("All test cases for verifyRolesAndPermissionsDetailsPage passed successfully");
	}
	
	@Test(priority = 39, groups = { "REGRESSIONSETTINGS2", "SMOKE", "REGRESSION_CI", "SMOKE_CI", "PRODUCTION_CI", "PRODUCTION_SANITY" })
	public final void verifyMSPSettingsPage() throws Exception {
		login("MSP_SETTINGS_EMAIL_SA", "MSP_SETTINGS_PASSWORD_SA");
		SoftAssert softAssert = new SoftAssert();
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
		gotoSettingsTab();
		LOGGER.info("Redirected to settings page");
		waitForPageLoaded();
		settingsPage.waitForElementsOfSettingsPage("tableOverlay");
		softAssert.assertTrue(settingsPage.verifyElementsOfSettingsPage("overviewTab"), "Overview tile on settings page is not loaded successfully");
		softAssert.assertTrue(settingsPage.verifyElementsOfSettingsPage("preferenceTabSettingsPage"), "Preferences tile on settings page is not loaded successfully");
		softAssert.assertTrue(settingsPage.verifyElementsOfSettingsPage("roleAndPersmissionTab"), "Roles and permissions tile on settings page is not loaded successfully");
		softAssert.assertTrue(settingsPage.verifyElementsOfSettingsPage("helpAndSupportTabSettingsPage"), "Help and support tile on settings page is not loaded successfully");
		softAssert.assertTrue(settingsPage.verifyElementsOfSettingsPage("profileTileHeader"), "Profile tile on settings page is not loaded successfully");
		settingsPage.clickOnElementsOfSettingsPage("preferenceTabSettingsPage");
		softAssert.assertTrue(settingsPage.verifyElementsOfSettingsPage("serviceNowTileHeader"), "Service now tile on settings page is not loaded successfully");
		softAssert.assertTrue(settingsPage.verifyElementsOfSettingsPage("authenticationTileHeader"), "Authentication tile on settings page is not loaded successfully");
		settingsPage.clickOnElementsOfSettingsPage("roleAndPersmissionTab");
		softAssert.assertTrue(settingsPage.verifyElementsOfSettingsPage("rolesTileHeader"), "Roles tile on settings page is not loaded successfully");
		settingsPage.clickOnElementsOfSettingsPage("helpAndSupportTabSettingsPage");
		softAssert.assertTrue(settingsPage.verifyElementsOfSettingsPage("callbackTitle"), "Callback tile on settings page is not loaded successfully");
		softAssert.assertTrue(settingsPage.verifyElementsOfSettingsPage("directSupportTitleHnS"), "Direct support tile on settings page is not loaded successfully");
		softAssert.assertTrue(settingsPage.verifyElementsOfSettingsPage("emailSupportTitle"), "Email tile on settings page is not loaded successfully");
		softAssert.assertTrue(settingsPage.verifyElementsOfSettingsPage("HofOTileHeader"), "Hours of Operation tile on settings page is not loaded successfully");
		softAssert.assertAll();
		LOGGER.info("Settings page is loaded successfully");
	}
	
	@Test(priority = 40, groups = { "REGRESSIONSETTINGS2", "REGRESSION_CI" }, description = "This test validates default role creation,assignment and verification of role for MSP,Partner and IT Admin.Test Case:362759", dataProvider = "loginDataForRolesCheck",enabled=false)
	public final void verifyDefaultRoleCreationAssignment(String username, String password) throws Exception {
		login(username, password);
		SupportTeamPage supportTeamPage = new SupportTeamPage(PreDefinedActions.getDriver()).getInstance();
		UserPage userPage = new UserPage(PreDefinedActions.getDriver()).getInstance();
		String tenantID;
		try {
			SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
			SoftAssert softAssert = new SoftAssert();
			String notificationMessage = null;
			if (username == "IT_ADMIN_COMPANY_USER_EMAIL_COMPANIES")
				gotoNonMSPSettingsTab();
			else
				gotoSettingsTab();
			waitForPageLoaded();
			LOGGER.info("Redirected to Settings page");
			if (username == "IT_ADMIN_COMPANY_USER_EMAIL_COMPANIES")
				settingsPage.clickOnElementsOfSettingsPage("roleAndPermissionTabCompany");
			else
				settingsPage.clickOnElementsOfSettingsPage("roleAndPersmissionTab");
			tenantID = getValueFromToken("tenant");
			String url = roleIdURL + ConstantURL.ROLEID_URL1 + tenantID + ConstantURL.ROLEID_URL2;
			List<String> roleIDs = settingsPage.getAllRoleIds(url, "{}", 0, "id");
			settingsPage.removeAllRoles(tenantID, roleIdURL, roleIDs, environment);
			Assert.assertTrue(settingsPage.createPermission(LanguageCode, getTextLanguage(LanguageCode, "daas_ui", "global.no")), "Custom role did not created successfully.");

			// Add Support Team User
			if (username == "MSP_SETTINGS_EMAIL_SA")
				gotoSupportTeamTab();
			else if (username == "RESELLER_NEW_UI_EMAIL_US")
				gotoPartnerTeamTab();
			else
				gotoUserTab();
			if (username == "MSP_SETTINGS_EMAIL_SA" || username == "RESELLER_NEW_UI_EMAIL_US") {
				supportTeamPage.clickOnElementsOfSupportTeamPage("supportAddButton");
				settingsPage.clickOnElementsOfSettingsPage("roleDropdownArrow");
				settingsPage.clickOnElementsOfSettingsPage("firstRolesDropdown");
				Assert.assertTrue(settingsPage.verifyCustomRoleInRoleDropdown(), "Custom role did not get reflected in roles dropdown.");
					settingsPage.clickOnElementsOfSettingsPage("rolesDropdownAfter");
				supportTeamPage.enterTextForSupportTeamPage("firstNameAddMember", SupportVariables.FIRST_NAME);
				supportTeamPage.enterTextForSupportTeamPage("lastNameAddMember", SupportVariables.LAST_NAME);
				supportTeamPage.enterTextForSupportTeamPage("emailAddMember", supportTeamUserEmail);
				supportTeamPage.enterTextForSupportTeamPage("mobileAddMember", SupportVariables.MOBILE_PHONE);
				supportTeamPage.clickOnElementsOfSupportTeamPage("addMember");
				supportTeamPage.waitForElementsOfSupportTeamPage("toastNotification");
				notificationMessage = supportTeamPage.getTextOfSupportTeamPage("toastNotification");
				softAssert.assertTrue((notificationMessage.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "user.created.success"))), "Incorrect notification message is generated after adding support team member");
				sleeper(4000);// Due to inconsistent UI response we have to put wait here.(Tried many ways but this is the final solution which works as expected.)
				supportTeamPage.enterTextForSupportTeamPage("emailTextbox", supportTeamUserEmail);
				supportTeamPage.waitForElementsOfSupportTeamPage("tableOverlay");
				Assert.assertFalse(supportTeamPage.verifyElementsOfSupportTeamPage("noItemsDisplayText"), "Add functionality for support team member does not work correctly");
				LOGGER.info("Newly added support team member with custom role got reflected on list page.");
				supportTeamPage.waitForElementsOfSupportTeamPage("tableOverlay");
				softAssert.assertTrue(settingsPage.verifyCustomRoleDetailsPage(), "Custom role did not reflected on support team details page.");
			} else {
				userPage.clickOnElementsOfUserPage("addButton");
				userPage.clickOnElementsOfUserPage("nextButtonManually");
				userPage.enterTextForUserPage("firstNameBox", SupportVariables.FIRST_NAME);
				userPage.enterTextForUserPage("lastNameBox", SupportVariables.LAST_NAME);
				userPage.enterTextForUserPage("emailBox", supportTeamUserEmail);
				userPage.clickOnElementsOfUserPage("roleBox");
				Assert.assertTrue(settingsPage.verifyCustomRoleInUserRoleDropdown(), "Custom role did not get reflected in roles dropdown.");
				userPage.clickByJavaScriptOnElementsOfUserPage("roleBoxAfter");
				userPage.clickOnElementsOfUserPage("submitButton");
				userPage.waitForElementsOfUserPage("toastNotification");
				notificationMessage = userPage.getTextOfUserPage("toastNotification");
				softAssert.assertTrue((notificationMessage.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "user.created.success"))), "Incorrect notification message is generated after adding support team member");
			}
			logout();
			sleeper(3000);// Due to inconsistent UI response we have to put wait here.(Tried many ways but this is the final solution which works as expected.)

			// Verify default role user assignment
			login("DEFAULT_PERMISSION_ROLE_USER", "DEFAULT_PERMISSION_ROLE_USER_PASSWORD");
			Assert.assertTrue(supportTeamPage.verifyElementsOfSupportTeamPage("dashboardTitle"), "Dashboard Tab is not present after login");
			gotoReportTab();
			Assert.assertTrue(supportTeamPage.verifyElementsOfSupportTeamPage("reportTitle"), "Reports Tab is not present after login");
			gotoHelpAndSupportTab();
			Assert.assertTrue(supportTeamPage.verifyElementsOfSupportTeamPage("helpAndSupportTabTitle"), "Help and Support Tab is not present after login");
			gotoWhatsNewTabOfMspRA();
			Assert.assertTrue(supportTeamPage.verifyElementsOfSupportTeamPage("whatsNewTabTitle"), "Whats New Tab is not present after login");
			softAssert.assertAll();
			LOGGER.info("Validation of role assignment functionality completed successfully.");
		} catch (Exception e) {
			Assert.fail("Exception occured in method verifyDeafultRoleCreationAssignment " + e.getMessage());
		} finally {

			// Remove support team member
			logout();
			login(username, password);
			if (username == "MSP_SETTINGS_EMAIL_SA")
				gotoSupportTeamTab();
			else if (username == "RESELLER_NEW_UI_EMAIL_US")
				gotoPartnerTeamTab();
			else
				gotoUserTab();
			supportTeamPage.removeSupportTeamUser(LanguageCode, supportTeamUserEmail, getEnvironment(environment));
		}
	}
	
	@Test(priority = 41, groups = { "REGRESSIONSETTINGS2", "REGRESSION_CI", "ALL_CI", "ALL", "SMOKE", "SMOKE_CI","STABILIZATION_STAGING" }, description = "This test check the Help and Support for Company.Test Case:353527")
	public final void verifyHelpAndSupportForCompany() throws Exception {
		SoftAssert sa = new SoftAssert();
		login("IT_ADMIN_HELP_AND_SUPPORT_EMAIL", "IT_ADMIN_HELP_AND_SUPPORT_PASSWORD");
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();

		gotoHelpAndSupportTab();
		if (toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, "IT_ADMIN_HELP_AND_SUPPORT_EMAIL"))) {
			settingsPage.waitForElementsOfSettingsPage("helpAndSupportTitleTextOnBreadcrumbs");
			sa.assertTrue(settingsPage.verifyElementsOfSettingsPage("helpAndSupportTitleTextOnBreadcrumbs"), "Help & Support Page did not load successfully.");
		}else{
			sa.assertTrue(settingsPage.verifyElementsOfSettingsPage("helpAndSupportTitleText"), "Help & Support Page did not load successfully.");
		}

		//gotoWhatsNewTabOfMspRA();
		waitForPageLoaded();
		settingsPage.waitForElementsOfSettingsPage("helpAndSupportIcon");
		settingsPage.clickOnElementsOfSettingsPage("helpAndSupportIcon");
		settingsPage.clickOnElementsOfSettingsPage("helpAndSupportLink");
		waitForPageLoaded();
		if (toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, "IT_ADMIN_HELP_AND_SUPPORT_EMAIL"))) {
			settingsPage.waitForElementsOfSettingsPage("helpAndSupportTitleTextOnBreadcrumbs");
			sa.assertTrue(settingsPage.verifyElementsOfSettingsPage("helpAndSupportTitleTextOnBreadcrumbs"), "Help & Support Page did not load successfully on clicking icon.");
		}else{
			settingsPage.waitForElementsOfSettingsPage("helpAndSupportTitleText");
			sa.assertTrue(settingsPage.verifyElementsOfSettingsPage("helpAndSupportTitleText"), "Help & Support Page did not load successfully on clicking icon.");
		}

		sa.assertTrue(settingsPage.verifyElementsOfSettingsPage("partnerAssistanceTab"), "Partner Assistance Tab did not load successfully on Help & Support page.");
		sa.assertTrue(settingsPage.verifyElementsOfSettingsPage("hpAssistanceTab"), "HP Assistance Tab did not load successfully on Help & Support page.");

		// Partner assistance Tab
		settingsPage.clickOnElementsOfSettingsPage("partnerAssistanceTab");
		sa.assertTrue(settingsPage.getTextOfSettingsPage("partnerAssistancetitle").equals(settingsPage.getTextLanguage(LanguageCode, "daas_ui", "help.section.heading.title")), "Partner Assistance Title does not match on Help and Support Page");
		sa.assertTrue(settingsPage.getTextOfSettingsPage("partnerAssistanceDescription").equals(settingsPage.getTextLanguage(LanguageCode, "daas_ui", "help.support.msp.section.discription")), "Partner Assistance description does not match on Help and Support Page");

		sa.assertTrue(settingsPage.getTextOfSettingsPage("lookingSomethingTitle").equals(settingsPage.getTextLanguage(LanguageCode, "daas_ui", "help.section.looking.something.panel.title")), "Looking for something else-Title for Partner assistance Tab does not match on Help and Support Page");
		sa.assertTrue(settingsPage.getTextOfSettingsPage("lookingSomethingDescription").equals(settingsPage.getTextLanguage(LanguageCode, "daas_ui", "help.section.looking.something.panel.description")), "Looking for something else- description for Partner assistance Tab does not match on Help and Support Page");

		// HP assistance Tab
		settingsPage.clickOnElementsOfSettingsPage("hpAssistanceTab");
		sa.assertTrue(settingsPage.getTextOfSettingsPage("hpAssistancetitle").equals(settingsPage.getTextLanguage(LanguageCode, "daas_ui", "help.section.heading.title")), "HP Assistance Title does not match on Help and Support Page");
		sa.assertTrue(settingsPage.getTextOfSettingsPage("hpAssistanceDescription").equals(settingsPage.getTextLanguage(LanguageCode, "daas_ui", "help.support.msp.section.discription")), "HP Assistance description does not match on Help and Support Page");
		sa.assertTrue(settingsPage.getTextOfSettingsPage("hplookingSomethingTitle").equals(settingsPage.getTextLanguage(LanguageCode, "daas_ui", "help.section.looking.something.panel.title")), "Looking for something else-Title for  HP assistance Tab does not match on Help and Support Page");
		sa.assertTrue(settingsPage.getTextOfSettingsPage("hplookingSomethingDescription").equals(settingsPage.getTextLanguage(LanguageCode, "daas_ui", "help.section.looking.something.panel.description")), "Looking for something else-description for  HP assistance Tab does not match on Help and Support Page");

		sa.assertAll();
		LOGGER.info("Validation of Help & Support page for company got completed.");
	}

	@Test(priority = 42, groups = { "REGRESSIONSETTINGS2", "REGRESSION_CI", "ALL_CI", "ALL", "SMOKE", "SMOKE_CI","STABILIZATION_STAGING" }, description = "This test check the Looking for Something new hyperlinks, TC -353527,353525,353529 ")
	public final void verifyLookingForSomethingElseSection() throws Exception {
		login("MSP_HELP_AND_SUPPORT_EMAIL", "MSP_HELP_AND_SUPPORT_EMAIL_PASSWORD");
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		gotoHelpAndSupportTab();
		waitForPageLoaded();
		LOGGER.info("Redirected to help and support page");
		// Getting Started Tile
		softAssert.assertTrue(settingsPage.matchTextOfSettingsPage("gettingStartedTitle", getTextLanguage(LanguageCode, "daas_ui", "help.support.section.getting.started.title")), "Getting Started Title is incorrect");
		softAssert.assertTrue(settingsPage.matchTextOfSettingsPage("gettingStartedDescription", getTextLanguage(LanguageCode, "daas_ui", "help.support.section.getting.started.description")), "Getting Started Description is incorrect");
		softAssert.assertTrue(settingsPage.verifyElementsOfSettingsPage("gettingStartedLink"), "Getting Started link is not present on Help and Support Page");
		settingsPage.waitForElementsOfSettingsPage("gettingStartedLink");
		settingsPage.clickOnElementsOfSettingsPage("gettingStartedLink");
		sleeper(2000);
		settingsPage.switchToDifferentTab();
		sleeper(4000);// Url takes time to load
		String url = ConstantURL.WELCOME_KIT;
		softAssert.assertTrue((settingsPage.getUrlOfCurrentPage().contains(url)), "URL for Getting Started t is not matching");
		settingsPage.switchBackToPreviousTab();
		LOGGER.info("Validation of Getting Started Tile completed.");

		// Software Download Tile
		softAssert.assertTrue(settingsPage.matchTextOfSettingsPage("softwareDownloadTitle", getTextLanguage(LanguageCode, "daas_ui", "help.support.section.software.download.title")), "Software download Title is incorrect");
		softAssert.assertTrue(settingsPage.matchTextOfSettingsPage("softwareDownloadDescription", getTextLanguage(LanguageCode, "daas_ui", "help.support.section.software.download.description")), "Software download Description is incorrect");
		softAssert.assertTrue(settingsPage.verifyElementsOfSettingsPage("softwareDownloadsLink"), "Software download link is not present on Help and Support Page");
		settingsPage.waitForElementsOfSettingsPage("softwareDownloadsLink");
		settingsPage.clickOnElementsOfSettingsPage("softwareDownloadsLink");
		sleeper(2000);
		settingsPage.switchToDifferentTab();
		sleeper(4000);// Url takes time to load
		url = getEnvironment(System.getProperty("environment")) + ConstantURL.SOFTWARE_DOWNLOAD + "/" + LanguageCode;
		softAssert.assertTrue(url.equals(settingsPage.getUrlOfCurrentPage()), "URL for software download link is not matching");
		settingsPage.switchBackToPreviousTab();
		LOGGER.info("Validation of Software Download Tile completed.");

		// Knowledge Base Tile
		softAssert.assertTrue(settingsPage.matchTextOfSettingsPage("knowledgeBaseTitle", getTextLanguage(LanguageCode, "daas_ui", "help.support.knowledge.base.title")), "Knowledge Base Title is incorrect");
		softAssert.assertTrue(settingsPage.matchTextOfSettingsPage("knowledgeBaseDescription", getTextLanguage(LanguageCode, "daas_ui", "help.support.knowledge.base.description")), "Knowledge Base Description is incorrect");
		softAssert.assertTrue(settingsPage.verifyElementsOfSettingsPage("knowledgeBaseLink"), "Knowledge Base link is not present on Help and Support Page");
		settingsPage.waitForElementsOfSettingsPage("knowledgeBaseLink");
		settingsPage.clickOnElementsOfSettingsPage("knowledgeBaseLink");
		sleeper(2000);
		settingsPage.switchToDifferentTab();
		sleeper(4000);// Url takes time to load
		url = ConstantURL.KNOWLEDGE_BASE;
		softAssert.assertTrue((settingsPage.getUrlOfCurrentPage().contains(url)), "URL for Knowledge Base link is not matching");
		softAssert.assertTrue(settingsPage.getUrlOfCurrentPage().contains(ConstantURL.LANGUAGE_PARAMETER),"Language parameter not added in Service now URL");
		settingsPage.switchBackToPreviousTab();
		LOGGER.info("Validation of Knowledge Base tile completed.");

		//System requirements tile
		softAssert.assertTrue(settingsPage.matchTextOfSettingsPage("systemRequirementsTitle", getTextLanguage(LanguageCode, "daas_ui", "requirements.title")), "System requirements Title is incorrect");
		softAssert.assertTrue(settingsPage.matchTextOfSettingsPage("systemRequirementsDescription", getTextLanguage(LanguageCode, "daas_ui", "help.support.system.requirements.description")), "System requirements Description is incorrect");
		softAssert.assertTrue(settingsPage.verifyElementsOfSettingsPage("systemRequirementsLink"), "View System requirements button not present on system requirements page");
		settingsPage.clickOnElementsOfSettingsPage("systemRequirementsLink");
		sleeper(2000);
		settingsPage.switchToDifferentTab();
		sleeper(4000);// Url takes time to load
		softAssert.assertTrue(settingsPage.getUrlOfCurrentPage().contains(ConstantURL.SYSTEM_REQUIREMENTS), "Page not redirected to system requirements page");
		softAssert.assertTrue(settingsPage.verifyElementsOfSettingsPage("userProfileIcon"), "User profile icon not present on system requirements page");
		softAssert.assertTrue(settingsPage.verifyElementsOfSettingsPage("hpIcon"), "HP icon not present on system requirements page");
		softAssert.assertTrue(settingsPage.verifyElementsOfSettingsPage("hpDaas"), "HP Daas not present on system requirements page");
		settingsPage.switchBackToPreviousTab();
		LOGGER.info("Validation of System requirements tile completed.");

		// Legal Information Tile
		// Privacy link
		softAssert.assertTrue(settingsPage.matchTextOfSettingsPage("legalInformationTitle", getTextLanguage(LanguageCode, "daas_ui", "help.support.legal.information.title")), "Legal Information Title is incorrect");
		softAssert.assertTrue(settingsPage.matchTextOfSettingsPage("legalInformationDescription", getTextLanguage(LanguageCode, "daas_ui", "welcome.copyright").replace("{year}", getCurrentYear())), "Legal Information Description is incorrect");
		softAssert.assertTrue(settingsPage.verifyElementsOfSettingsPage("legalInformationPrivacyLink"), "Privacy link is not present on Help and Support Page");
		settingsPage.waitForElementsOfSettingsPage("legalInformationPrivacyLink");
		settingsPage.clickOnElementsOfSettingsPage("legalInformationPrivacyLink");
		settingsPage.switchToDifferentTab();
		sleeper(4000);// Url takes time to load
		softAssert.assertTrue(settingsPage.getUrlOfCurrentPage().contains(settingsPage.getTextLanguage(LanguageCode, "daas_ui", "welcome.privacy").toLowerCase()), "Privacy Title matching failed");
		settingsPage.switchBackToPreviousTab();
		LOGGER.info("Validation of Privacy completed.");

		// Terms and Condition link
		softAssert.assertTrue(settingsPage.verifyElementsOfSettingsPage("termsAndConditionLink"), "Terms and condition link is not present on Help and Support Page");
		settingsPage.waitForElementsOfSettingsPage("termsAndConditionLink");
		settingsPage.clickOnElementsOfSettingsPage("termsAndConditionLink");
		settingsPage.switchToDifferentTab();
		sleeper(4000);// Url takes time to load
		softAssert.assertTrue(settingsPage.verifyElementsOfSettingsPage("printButton"), "Print Button not present");
		softAssert.assertTrue(settingsPage.verifyElementsOfSettingsPage("hpIcon"), "HP icon not present");
		softAssert.assertTrue(settingsPage.verifyElementsOfSettingsPage("hpDaas"), "HP Daas not present");
		softAssert.assertTrue(settingsPage.verifyElementsOfSettingsPage("userProfileIcon"), "User profile icon not present");
		url = SetTestEnvironments.getEnvironment(System.getProperty("environment")) + ConstantURL.TERMS_AND_CONDITIONS + SetTestEnvironments.getLanguageCode(System.getProperty("languageCode"));
		softAssert.assertTrue(url.equals(settingsPage.getUrlOfCurrentPage()), "Terms and Condition URL not matching");
		settingsPage.switchBackToPreviousTab();
		LOGGER.info("Validation of Terms and Condition completed.");

		// Service Level Agreement
		settingsPage.scrollTillViewSettingsPage("serviceLevelAgreementLink");
		softAssert.assertTrue(settingsPage.verifyElementsOfSettingsPage("serviceLevelAgreementLink"), "Terms and condition link is not present on Help and Support Page");
		settingsPage.waitForElementsOfSettingsPage("serviceLevelAgreementLink");
		settingsPage.clickOnElementsOfSettingsPage("serviceLevelAgreementLink");
		settingsPage.switchToDifferentTab();
		sleeper(4000);// Url takes time to load
		softAssert.assertTrue(settingsPage.verifyElementsOfSettingsPage("printButton"), "Print Button not present");
		softAssert.assertTrue(settingsPage.verifyElementsOfSettingsPage("hpIcon"), "HP icon not present");
		softAssert.assertTrue(settingsPage.verifyElementsOfSettingsPage("hpDaas"), "HP Daas not present");
		softAssert.assertTrue(settingsPage.verifyElementsOfSettingsPage("userProfileIcon"), "User profile icon not present");
		url = SetTestEnvironments.getEnvironment(System.getProperty("environment")) + ConstantURL.SLA + SetTestEnvironments.getLanguageCode(System.getProperty("languageCode"));
		softAssert.assertTrue(url.equals(settingsPage.getUrlOfCurrentPage()), "Service Level Agreement URL not matching");
		settingsPage.switchBackToPreviousTab();
		sleeper(4000);
		LOGGER.info("Validation of Service Level Agreement completed.");

		//Verify privacy page
		getUrl(getEnvironment(System.getProperty("environment")) + "privacy/android/");
		waitForPageLoaded();
		softAssert.assertTrue(settingsPage.verifyElementsOfSettingsPage("printButton"), "Print Button not present on android privacy policy page");
		softAssert.assertTrue(settingsPage.verifyElementsOfSettingsPage("hpIcon"), "HP icon not present on android privacy policy page");
		softAssert.assertTrue(settingsPage.verifyElementsOfSettingsPage("hpDaas"), "HP Daas not present on android privacy policy page");
		softAssert.assertTrue(settingsPage.verifyElementsOfSettingsPage("userProfileIcon"), "User profile icon not present");
		softAssert.assertFalse(settingsPage.verifyElementsOfSettingsPage("language"), "Language selection drop down is present even if user is currently logged in.");
		softAssert.assertTrue(settingsPage.verifyElementsOfSettingsPage("hpPrivacyPolicyLink"), "HP Privacy policy link is not present on web page.");
		settingsPage.clickOnElementsOfSettingsPage("hpPrivacyPolicyLink");
		sleeper(2000);
		settingsPage.switchToDifferentTab();
		sleeper(4000);// Url takes time to load
		softAssert.assertTrue(settingsPage.getUrlOfCurrentPage().contains(settingsPage.getTextLanguage(LanguageCode, "daas_ui", "welcome.privacy").toLowerCase()), "Privacy Title matching failed");
		settingsPage.switchBackToPreviousTab();
		LOGGER.info("Validation of Android privacy policy web page completed.");

		softAssert.assertAll();
		LOGGER.info("Validation of Looking for something new section on help and support page completed");
	}

	/* This test case validates Email Request functionality on Help & Support page for Company. */
	/**
	 * Will enable test case once NFR Story 528295: [Core][DeviceEnroll] Help and Support test emails from LDK into ServiceNow is done
	 * @throws Exception
	 */
	@Test(priority = 43, dependsOnMethods = "verifyExtractEmailForPartner", groups = { "REGRESSIONSETTINGS2", "REGRESSION_CI", "ALL_CI", "ALL", "SMOKE", "SMOKE_CI","STABILIZATION_STAGING" }, description = "353527", enabled = false)
	public final void verifyEmailRequestRAForCompany() throws Exception {
		SoftAssert sa = new SoftAssert();
		login("IT_ADMIN_HELP_AND_SUPPORT_EMAIL", "IT_ADMIN_HELP_AND_SUPPORT_PASSWORD");
		gotoHelpAndSupportTab();
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();

		HashMap<String, String> emailRequestInfoHnS = settingsPage.getEmailRequestDetailsHelpAndSupport();
		HashMap<String, String> emailContentInfoHnS = settingsPage.getEmailContentHelpAndSupport();

		sa.assertTrue(settingsPage.verifyEmailRequestMyselfHelpAndSupport(emailRequestInfoHnS, LanguageCode, "PARTNER"), "Email Request for myself is not working properly.");
		Mailinator objMailinator = new Mailinator("", getCredentials(environment, "RA_EMAIL_REQUEST_MAIL_ID_PARTNERL"));
		sleeper(5000);
		MailinatorMail objMailinatorEmail = objMailinator.getSpecificEmailUsingIncidentCode("Support Request", emailExtract, true);
		Assert.assertNotNull(objMailinatorEmail, "Test failed as email is not received for submitted email request for myself.");
		validateEmailContentForRA(emailContentInfoHnS, objMailinatorEmail, emailExtract);

		sa.assertTrue(settingsPage.verifyEmailRequestOtherHelpAndSupport(emailRequestInfoHnS, LanguageCode), "Email Request for other user is not working properly.");
		sa.assertNotNull(objMailinatorEmail, "Test failed as email is not received for submitted email request for other user.");
		validateEmailContentForRA(emailContentInfoHnS, objMailinatorEmail, emailExtract);

		sa.assertAll();
		LOGGER.info("Validation of Email Request of RA completed.");
	}

	/* This test case extracts email id from Email Support of Partner. */
	/**
	 * @throws Exception
	 */
	@Test(priority = 44, groups = { "REGRESSIONSETTINGS2", "REGRESSION_CI", "ALL_CI", "ALL", "SMOKE", "SMOKE_CI" })
	public final void verifyExtractEmailForPartner() throws Exception {
		SoftAssert sa = new SoftAssert();
		login("RESELLER_HELP_AND_SUPPORT_EMAIL", "RESELLER_HELP_AND_SUPPORT_EMAIL_PASSWORD");
		gotoSettingsTab();
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
		settingsPage.clickOnElementsOfSettingsPage("helpAndSupportTabSettingsPage");
		HashMap<String, String> emailSupportInfo = settingsPage.getEmailSupportDetails();
		emailExtract = settingsPage.extractEmailFromEmailSupport(emailSupportInfo, "PARTNER");
		sa.assertAll();
		LOGGER.info("Extraction of email id completed.");
	}

	/* This test case validates Callback Request functionality on Help & Support page for Company. */
	/**
	 * @throws Exception
	 */
	@Test(priority = 45,dependsOnMethods = { "verifyEnableCallbackForPartner" } ,groups = { "REGRESSIONSETTINGS2", "REGRESSION_CI", "ALL_CI", "ALL", "SMOKE", "SMOKE_CI","STABILIZATION_STAGING" }, description = "353527")
	public final void verifyCallbackRequestRAForCompany() throws Exception {
		SoftAssert sa = new SoftAssert();
		login("IT_ADMIN_HELP_AND_SUPPORT_EMAIL", "IT_ADMIN_HELP_AND_SUPPORT_PASSWORD");
		gotoHelpAndSupportTab();
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();

		HashMap<String, String> callbackRequestInfoHnS = settingsPage.getCallbackRequestDetailsHelpAndSupport();

		sa.assertTrue(settingsPage.verifyCallbackRequestMyselfHelpAndSupport(callbackRequestInfoHnS, LanguageCode,"Partner"), "Callback Request for myself is not working properly.");

		sa.assertTrue(settingsPage.verifyCallbackRequestOtherHelpAndSupport(callbackRequestInfoHnS, LanguageCode), "Callback Request for other user is not working properly.");

		sa.assertAll();
		LOGGER.info("Validation of Callback Request of RA completed.");
	}

	/* This test case configures Callback text for Partner. */
	/**
	 * @throws Exception
	 */
	@Test(priority = 46, groups = { "REGRESSIONSETTINGS2", "REGRESSION_CI", "ALL_CI", "ALL", "SMOKE", "SMOKE_CI","STABILIZATION_STAGING" })
	public final void verifyEnableCallbackForPartner() throws Exception {
		SoftAssert sa = new SoftAssert();
		login("RESELLER_HELP_AND_SUPPORT_EMAIL", "RESELLER_HELP_AND_SUPPORT_EMAIL_PASSWORD");
		gotoSettingsTab();
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
		settingsPage.clickOnElementsOfSettingsPage("helpAndSupportTabSettingsPage");
		sa.assertTrue(settingsPage.enableCallbackFunctionality(LanguageCode, "statusTextOfCallback", "toggleCallback"), "Callback is not getting enabled");
		sa.assertAll();
		LOGGER.info("Configuration of Callback Support completed.");
	}

	/* This test case configures Hours of Operation functionality on Partner Settings page for Partner. */
	/**
	 * @throws Exception
	 */
	@Test(priority = 47, groups = { "REGRESSIONSETTINGS2", "REGRESSION_CI", "ALL_CI", "ALL", "SMOKE", "SMOKE_CI","STABILIZATION_STAGING" }, description = "353529")
	public final void verifyHoursOfOperationPartner() throws Exception {
		SoftAssert sa = new SoftAssert();
		login("RESELLER_HELP_AND_SUPPORT_EMAIL", "RESELLER_HELP_AND_SUPPORT_EMAIL_PASSWORD");
		gotoSettingsTab();
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
		settingsPage.clickOnElementsOfSettingsPage("helpAndSupportTabSettingsPage");
		HashMap<String, String> hoursOfOperationInfo = settingsPage.getHoursOfOperationDetails();
		HashMap<String, String> hoursOfOperationInfoHnS = settingsPage.getHoursOfOperationDetailsHelpAndSupport();
		// Test Case 1
		sa.assertTrue(settingsPage.verifyHoursOfOperationSave(hoursOfOperationInfo, LanguageCode), "Hours of Operation(save) is not working properly");
		timing = settingsPage.getTextFromTimings(hoursOfOperationInfoHnS);
		sa.assertAll();
		LOGGER.info("Hours of Operation for MSP configured successfully.");
	}

	/* This test case validates Hours of Operation functionality on Help & Support page for Company. */
	/**
	 * @throws Exception
	 */
	@Test(priority = 48, dependsOnMethods = "verifyHoursOfOperationPartner", groups = { "REGRESSIONSETTINGS2", "REGRESSION_CI", "ALL_CI", "ALL", "SMOKE", "SMOKE_CI","STABILIZATION_STAGING" }, description = "353529")
	public final void verifyHoursOfOperationRAForCompany() throws Exception {
		SoftAssert sa = new SoftAssert();
		login("IT_ADMIN_HELP_AND_SUPPORT_EMAIL", "IT_ADMIN_HELP_AND_SUPPORT_PASSWORD");
		gotoHelpAndSupportTab();
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();

		HashMap<String, String> hoursOfOperationInfoHnS = settingsPage.getHoursOfOperationDetailsHelpAndSupport();

		sa.assertTrue(settingsPage.getTextOfSettingsPage("hoursOfOperationsTitle").equalsIgnoreCase(settingsPage.getTextLanguage(LanguageCode, "daas_ui", "settings.hours_of_operation.title")), "Title for Hours of Operation for RA does not match.");

		sa.assertTrue(settingsPage.verifyHoursOfOperationHelpAndSupport(hoursOfOperationInfoHnS, LanguageCode, timing), "Hours of Operation for Help & Support is not updated properly");
		sa.assertAll();
		LOGGER.info("Validation of Hours of Operation for Help & Support functionality completed.");
	}

	/* This test case configures Direct support for Partner. */
	/**
	 * @throws Exception
	 */
	@Test(priority = 49, groups = { "REGRESSIONSETTINGS2", "REGRESSION_CI", "ALL_CI", "ALL", "SMOKE", "SMOKE_CI","STABILIZATION_STAGING" })

	public final void verifyProvideDirectSupportForParter() throws Exception {
		SoftAssert sa = new SoftAssert();
		login("RESELLER_HELP_AND_SUPPORT_EMAIL", "RESELLER_HELP_AND_SUPPORT_EMAIL_PASSWORD");
		gotoSettingsTab();
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
		settingsPage.clickOnElementsOfSettingsPage("helpAndSupportTabSettingsPage");
		HashMap<String, String> directSupportInfo = settingsPage.getDirectSupportDetails();
		for (int i = 0; i < 2; i++) {
			statusTextDirectSupport = settingsPage.getTextFromDirectSupport(directSupportInfo, LanguageCode);
		}
		sa.assertAll();
		LOGGER.info("Configuration of Direct Support completed.");
	}

	/* This test case validates Direct Support functionality on Help & Support page for Company. */
	/**
	 * @throws Exception
	 */
	@Test(priority = 50, dependsOnMethods = "verifyProvideDirectSupportForParter", groups = { "REGRESSIONSETTINGS2", "REGRESSION_CI", "ALL_CI", "ALL", "SMOKE", "SMOKE_CI","STABILIZATION_STAGING" }, description = "353529")
	public final void verifyDirectSupportRAForCompany() throws Exception {
		SoftAssert sa = new SoftAssert();
		login("IT_ADMIN_HELP_AND_SUPPORT_EMAIL", "IT_ADMIN_HELP_AND_SUPPORT_PASSWORD");
		gotoHelpAndSupportTab();
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
		HashMap<String, String> directSupportInfoHnS = settingsPage.getDirectSupportDetailsHelpAndSupport();
		sa.assertTrue(settingsPage.verifyDirectSupportHelpAndSupport(directSupportInfoHnS, statusTextDirectSupport, LanguageCode));
		sa.assertAll();
		LOGGER.info("Validation of Direct Support for Help & Support completed for Company.");
	}

	/* This test case verifies Partner And Channel Support functionality for Partner */
	/**
	 * Test case will be enabled once NFR Story 528295: [Core][DeviceEnroll] Help and Support test emails from LDK into ServiceNow is done
	 * @throws Exception
	 */
	@Test(priority = 51, groups = { "REGRESSIONSETTINGS2", "SMOKE", "REGRESSION_CI", "SMOKE_CI", "PRODUCTION_SANITY","STABILIZATION_STAGING" }, description = "Test Case ID : 353529", enabled = true)
	public final void verifyPartnerAndChannelSupportTileForPartner() throws Exception {
		login("RESELLER_HELP_AND_SUPPORT_EMAIL", "RESELLER_HELP_AND_SUPPORT_EMAIL_PASSWORD");
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		String subject = generateRandomString(11);
		String description = generateRandomString(11);
		ArrayList<String> partnerDetails = new ArrayList<String>();

		gotoSettingsTab();
		LOGGER.info("Redirected to settings tab");
		waitForPageLoaded();
		settingsPage.waitForElementsOfSettingsPage("tableOverlay");
		partnerDetails.add(settingsPage.getTextOfSettingsPage("primaryAdministratorName"));
		partnerDetails.add(settingsPage.getTextOfSettingsPage("primaryAdministratorEmail"));
		partnerDetails.add(settingsPage.getTextOfSettingsPage("primaryAdministratorPhone"));
		partnerDetails.add(settingsPage.getTextOfSettingsPage("primaryAdministratorCountry"));
		partnerDetails.add(settingsPage.getTextOfSettingsPage("primaryAdministratorPartnerID"));

//		String expectedMailContent = ( getTextLanguage(LanguageCode, "idm", "support_mailer.support_email.subject.partner")+ "  "+getTextLanguage(LanguageCode, "lhserver", "support_mailer.callback_email.greeting").replace("%{team_name}",getTextLanguage(LanguageCode, "lhserver", "support_admin.users.headings.index"))+"  " + getTextLanguage(LanguageCode, "lhserver", "support_mailer.support_email.message").replace("%{user_name}", partnerDetails.get(0)).replace("(%{email})","("+partnerDetails.get(1).trim()+")") + "  "+ getTextLanguage(LanguageCode, "idm", "support_mailer.label.partner_name") + "  "
//				+ partnerDetails.get(0) + "  " + getTextLanguage(LanguageCode, "idm", "support_mailer.label.partner_id") + "  "+ partnerDetails.get(4) + "  "+ getTextLanguage(LanguageCode, "idm", "support_mailer.label.partner_country") + "  " + partnerDetails.get(3)  + "  "+ getTextLanguage(LanguageCode, "idm", "support_mailer.label.account_email") + "  " + partnerDetails.get(1)  + "  "+ getTextLanguage(LanguageCode, "idm", "support_mailer.label.requested_for_other") + "  " + getTextLanguage(LanguageCode, "daas_ui", "partners.list.ptype.data.reseller")  + "  "+ getTextLanguage(LanguageCode, "idm", "support_mailer.label.office_phone")
//				+ "  " + partnerDetails.get(2)  + "  "+ getTextLanguage(LanguageCode, "lhserver", "support_mailer.label.mobile_phone") + "  "+ getTextLanguage(LanguageCode, "lhserver", "support_mailer.value.not_available")  + "  " + getTextLanguage(LanguageCode, "idm", "support_mailer.label.time_zone") + "  " + "Etc/UTC"  + "  "+ getTextLanguage(LanguageCode, "idm", "support_mailer.label.partner_title")+ "  " + subject  + "  "+ getTextLanguage(LanguageCode, "idm", "onboarding.apps.category") + ": " + getTextLanguage(LanguageCode, "lhserver", "support_mailer.value.not_available")  + "  "+ getTextLanguage(LanguageCode, "idm", "support_mailer.label.description")+ "  " + description
//				 + "  "+"Â"+ getTextLanguage(LanguageCode, "lhserver", "global.copyright").replace("%{current_year}", getCurrentYear())).replace("<b>", " ").replace("</b>", " ").replace("<b >", " ").replace("  ", " ");

		gotoHelpAndSupportTab();
		LOGGER.info("Navigated to help and support page");
		waitForPageLoaded();
		sleeper(3000); // waiting for the elements on the page to load
		settingsPage.waitForElementsOfSettingsPage("partnerAndChannelSupportHeader");
		softAssert.assertTrue(settingsPage.matchTextOfSettingsPage("partnerAndChannelSupportHeader", getTextLanguage(LanguageCode, "daas_ui", "partner.help.channel_support.title")), "Header on partner and channel support tile is incorrect");
		softAssert.assertTrue(settingsPage.matchTextOfSettingsPage("partnerAndChannelSupportDescription", getTextLanguage(LanguageCode, "daas_ui", "partner.help.channel_support.description")), "Description on partner and channel support tile is incorrect");
		softAssert.assertTrue(settingsPage.matchTextOfSettingsPage("hpdaasSupportHeader", getTextLanguage(LanguageCode, "daas_ui", "partner.help.hp_support.title")), "Header on hpdaas.com support tile is incorrect");
		
		softAssert.assertTrue(settingsPage.matchTextOfSettingsPage("hpdaasSupportDescription", getTextLanguage(LanguageCode, "daas_ui", "partner.help.hp_support.description")), "Description on hpdaas.com support tile is incorrect");
		softAssert.assertTrue(settingsPage.verifyElementsOfSettingsPage("emailSupportButton"), "Email support button is not present on hpdaas.com support tile");
		settingsPage.waitForElementsOfSettingsPage("emailSupportButton");
		settingsPage.clickOnElementsOfSettingsPage("emailSupportButton");
		LOGGER.info("Clicked on email support button");
		softAssert.assertTrue(settingsPage.verifyElementsOfSettingsPage("emailSupportModalHeader"), "Header is not present on email support modal");
		softAssert.assertTrue(settingsPage.matchTextOfSettingsPage("subjectLabel", getTextLanguage(LanguageCode, "daas_ui", "help.email.modal.email_subject.label")), "Subject label on email support modal is incorrect");
		softAssert.assertTrue(settingsPage.matchTextOfSettingsPage("descriptionLabelPartnerLogin", getTextLanguage(LanguageCode, "daas_ui", "incident.details.description")), "Description label on email support modal is incorrect");
		settingsPage.clickOnElementsOfSettingsPage("emailSupportSubmit");
		LOGGER.info("Clicked on submit button");
		softAssert.assertTrue(settingsPage.matchTextOfSettingsPage("subjectErrorMessage", getTextLanguage(LanguageCode, "lhserver", "form.validate.required")), "Error message on subject field is not generated on submitting empty fields");
		softAssert.assertTrue(settingsPage.matchTextOfSettingsPage("descriptionErrorMessage", getTextLanguage(LanguageCode, "lhserver", "form.validate.required")), "Error message on description field is not generated on submitting empty fields");
		settingsPage.enterTextForSettingsPage("emailSubjectTextBox", subject);
		settingsPage.enterTextForSettingsPage("emailDescriptionTextBox", description);
		settingsPage.clickOnElementsOfSettingsPage("emailSupportSubmit");
		LOGGER.info("Clicked on submit button");
		settingsPage.waitForElementsOfSettingsPage("toastNotification");
		softAssert.assertTrue(settingsPage.matchTextOfSettingsPage("toastNotification", getTextLanguage(LanguageCode, "daas_ui", "help.message.email.success")), "Toast notification is not generated after sending the email");
		//String actualMailContent = verifyEmailContent(environment, "HELP_&_SUPPORT_SUPPORT_EMAIL", "SUPPORT_EMAIL_SUBJECT", "", true);
		//softAssert.assertTrue(actualMailContent.equalsIgnoreCase(expectedMailContent), "Mail content does not match");
		softAssert.assertAll();
		LOGGER.info("Test cases for Partner and Channel support for Partner passed successfully");
	}
	
	// This test is covered in verifyCSVUploadNewACTenant
	@Test(priority = 52, groups = {"SUBODH", "REGRESSIONSETTINGS2", "SMOKE", "REGRESSION_CI", "SMOKE_CI", "STABILIZATION_STAGING"}, description = "Test Case ID : 374613", enabled=true)
	public final void verifyAddOrdersTile() throws Exception {
		
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
		OrdersListPage ordersListPage = new OrdersListPage(PreDefinedActions.getDriver()).getInstance();
		OrderDetailsPage orderDetailsPage = new OrderDetailsPage(PreDefinedActions.getDriver()).getInstance();

		SoftAssert softAssert = new SoftAssert();
		String[] columnValueToUpdateRandomData = { "ObjectOfServiceSerialNumber" };
//			Update ObjectServiceSerialNumber each execution
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


	@Test(priority = 53, groups = { "ACTIVECARESANITY", "REGRESSIONSETTINGS2", "SMOKE", "REGRESSION_CI", "SMOKE_CI", "STABILIZATION_STAGING" },description="Test Case:355148", enabled = true)
	public final void verifyIncidentEligibilityTileAppAdmin() throws Exception {
		SoftAssert sa = new SoftAssert();
		login("APP_ADMIN_USER", "APP_ADMIN_PASSWORD");
		gotoNonMSPSettingsTabAppAdmin();
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
		sa.assertTrue(settingsPage.verifyIncidentEligibilityModalDeselect(LanguageCode),"Error occured in validation of clearing checkboxes of premier care and case creation.");
		sa.assertAll();
		LOGGER.info("Validation of Incident Eligibility tile completed for App Admin.");
	}
	

	@Test(priority = 54, groups = { "ACTIVECARESANITY", "REGRESSIONSETTINGS2", "SMOKE", "REGRESSION_CI", "SMOKE_CI", "STABILIZATION_STAGING" }, description = "Test Case ID : 355185;355257", enabled=true)
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

	@Test(priority = 55, groups = { "REGRESSIONSETTINGS2", "SMOKE", "REGRESSION_CI", "SMOKE_CI" },description="Test Case:355148", enabled = false)
	public final void verifyCaseCreateButtonIncidentWiseEnable() throws Exception {
		SoftAssert sa = new SoftAssert();
		login("APP_ADMIN_USERNAME", "APP_ADMIN_PASSWORD");
		gotoNonMSPSettingsTabAppAdmin();
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
		sa.assertTrue(settingsPage.verifyIncidentEligibilityModalDeselect(LanguageCode),"Error occured in validation of clearing checkboxes of premier care and case creation.");
		sa.assertTrue(settingsPage.verifyIncidentEligibilityModal(LanguageCode),"Error occured in validation of checkboxes of premier care and case creation.");
		logout();
		login("IT_ADMIN_PREMIER_CARE_USERNAME","IT_ADMIN_PREMIER_CARE_PASSWORD");
		gotoIncidentCompanyUserTab();
		sa.assertTrue(settingsPage.validateCreateCaseButton(true),"Create case button did not enable on Incident details even after check box is selected.");
		sa.assertAll();
		LOGGER.info("Validation of Case Creation button completed for App Admin in case of enable.");
	}
	
	@Test(priority = 56, groups = { "REGRESSIONSETTINGS2", "SMOKE", "REGRESSION_CI", "SMOKE_CI" },description="Test Case:355148", enabled = false)
	public final void verifyCaseCreateButtonIncidentWiseDisable() throws Exception {
		SoftAssert sa = new SoftAssert();
		login("APP_ADMIN_USERNAME", "APP_ADMIN_PASSWORD");
		gotoNonMSPSettingsTabAppAdmin();
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
		sa.assertTrue(settingsPage.verifyIncidentEligibilityModalDeselect(LanguageCode),"Error occured in validation of clearing checkboxes of premier care and case creation.");
		logout();
		login("IT_ADMIN_PREMIER_CARE_USERNAME","IT_ADMIN_PREMIER_CARE_PASSWORD");
		gotoIncidentCompanyUserTab();
		sa.assertTrue(settingsPage.validateCreateCaseButton(false),"Create case button did not disable on Incident details even after check box is de-selected.");
		sa.assertAll();
		LOGGER.info("Validation of Case Creation button completed for App Admin in case of disable.");
	}
	//Disabling this test case as of now, once the removal role method is in place we will enable this one.
	@Test(priority = 57, groups = { "REGRESSIONSETTINGS2", "REGRESSION_CI"}, description = "This test validates base role creation for APP Admin Test Cases:333516 ",enabled=false)
	public final void verifyBaseRoleCreation() throws Exception {
		login("APP_ADMIN_USERNAME", "APP_ADMIN_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
	    softAssert.assertTrue(settingsPage.createBasePermission(LanguageCode),"Base role did not created successfully.");
	    gotoLogTab();
		softAssert.assertTrue(settingsPage.verifyDescriptionOnLogsPage("ADD","BASE"),"Logs did not received after adding base role.");
	    softAssert.assertAll();
		LOGGER.info("Validation of base role creation completed successfully for App Admin.");
	}
	
	//Disabling this test case as of now, once the removal role method is in place we will enable this one.
	@Test(priority = 58, groups = { "REGRESSIONSETTINGS2", "REGRESSION_CI"}, description = "This test validates  duplicate, removal of base role creation for AAP Admin. Test Case:333522",enabled=false)
	public final void verifyDuplicateBaseRoles() throws Exception {
		login("APP_ADMIN_USERNAME", "APP_ADMIN_PASSWORD");
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();		
		SoftAssert softAssert = new SoftAssert();
		softAssert.assertTrue(settingsPage.createBasePermission(LanguageCode),"Base role did not created successfully.");
		softAssert.assertTrue(settingsPage.verifyDuplicateBaseRole(LanguageCode),"Duplicate base role did not created successfully.");
		gotoLogTab();
		softAssert.assertTrue(settingsPage.verifyDescriptionOnLogsPage("ADD","BASE"),"Logs did not received after adding base role.");
		softAssert.assertAll();
		LOGGER.info("Validation of duplicate base role check functionality completed successfully.");
	}

	// This test is covered in verifyReactiveCaseCreationFunctionality
	@Test(priority = 59, groups = { "REGRESSIONSETTINGS2", "REGRESSION_CI", "STABILIZATION_STAGING"}, description = "TEST CASE 368400", enabled=false)
	public final void verifyCaseCreation() throws Exception {
		login("IT_ADMIN_PC_USERNAME","IT_ADMIN_PC_PASSWORD");
		waitForPageLoaded();
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();		
		SoftAssert softAssert = new SoftAssert();
		String type = getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.type.hardwarehealth");
		String subtype = getTextLanguage(LanguageCode, "daas_ui", "incident.subtypes.hdd_predictive_failure");
		String incidentTitle = "Test Incident for enrolled device";
		String incidentDescription = "Description for Test Incident for enrolled device";
		String deviceID = getEnvironmentSpecificData(System.getProperty("environment"),"DEVICE_ID_CASE_CREATION_PC_COMPANY");
		String tenantID = getValueFromToken("tenant");
		gotoIncidentTab();
		waitForPageLoaded();
		resetTableConfiguration();
		String incidentId = settingsPage.submitCaseUsingAPICaseCreation(type, subtype, incidentTitle, incidentDescription, tenantID, deviceID);
		settingsPage.enterTextForSettingsPage("idSearchBox", incidentId);
		sleeper(3000);
		settingsPage.clickByJavaScriptOnSettingsPage("selectedIncidentTitle");
		LOGGER.info("Redirected to incident details page");
		sleeper(3000);
		settingsPage.createCase();
		settingsPage.waitForElementsOfSettingsPage("caseInformationTileTitle");
		settingsPage.scrollTillViewSettingsPage("caseInformationTileTitle");
		softAssert.assertTrue(settingsPage.getTextOfSettingsPage("caseInformationTileTitle").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "incident.details.section.case.information").toUpperCase()), "Title on case information tile title is incorrect");
		softAssert.assertTrue(settingsPage.getTextOfSettingsPage("caseIDLabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "incidents.caseID")), "Case ID label on case information tile title is incorrect");
		softAssert.assertTrue(settingsPage.getTextOfSettingsPage("caseStatusLabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "incident.list.caseStatus")), "Case status label on case information tile title is incorrect");
		softAssert.assertTrue(settingsPage.getTextOfSettingsPage("caseSubmissionDate").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "incident.details.section.case.submission")), "Case submission value on case information tile title is incorrect");
		softAssert.assertTrue(settingsPage.getTextOfSettingsPage("caseLocation").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "incident.details.section.case.location")), "Case location label on case information tile title is incorrect");
		softAssert.assertTrue(settingsPage.getTextOfSettingsPage("caseContact").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "incident.details.section.case.contact")), "Case contact label on case information tile title is incorrect");
		softAssert.assertAll();
		LOGGER.info("Case creation functionality verified successfully");
	}
	
	// need to work on this hence disabled.
	@Test(priority = 60, groups = { "REGRESSIONSETTINGS2", "REGRESSION_CI"}, description = "TEST CASE 370589", enabled=false)
	public final void verifyCaseCreationRequestingLocationFromEndUser() throws Exception {
		login("MSP_PC_USERNAME", "MSP_PC_PASSWORD");
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();		
		SoftAssert softAssert = new SoftAssert();
		String type = getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.type.hardwarehealth");
		String subtype = getTextLanguage(LanguageCode, "daas_ui", "incident.subtypes.hdd_predictive_failure");
		String incidentTitle = "Test Incident for enrolled device";
		String incidentDescription = "Description for Test Incident for enrolled device";
		String deviceID = getEnvironmentSpecificData(System.getProperty("environment"), "DEVICE_ID_CASE_CREATION_PC_COMPANY");
		String tenantID = getValueFromToken("tenant");
		String apiurl = cataLystURL+ CommonVariables.DETAILSSEARCHSERVICEAPI+tenantID+CommonVariables.DETAILSSEARCHSERVICEAPI2;
		String tenantIdCompany = getTenantDetails(apiurl,CommonVariables.SEARCH_SERVICE_IDMTENANTS,incidentPCCompanyName);
		String apiSubscribe = cataLystURL+ CommonVariables.INCIDENTSERVICEAPI1+tenantIdCompany+CommonVariables.INCIDENTSERVICEAPI2;
		String bodyAllIncidents = "{\"type\":[{\"name\":\"ACCOUNT\",\"value\":\"Account\",\"subType\":[{\"name\":\"HP_TECHPULSE_INCIDENT_INTEGRATION\",\"value\":\"HP TechPulse Incident Integration\"},{\"name\":\"LICENSING\",\"value\":\"Licensing\"},{\"name\":\"UNKNOWN\",\"value\":\"Unassigned\"}]},{\"name\":\"HW_CHANGE\",\"value\":\"Hardware Change\",\"subType\":[{\"name\":\"HDD\",\"value\":\"HDD\"},{\"name\":\"MEMORY\",\"value\":\"Memory\"},{\"name\":\"UNKNOWN\",\"value\":\"Unassigned\"}]},{\"name\":\"HW_HEALTH\",\"value\":\"Hardware Health\",\"subType\":[{\"name\":\"BATTERY_NOT_DETECTED\",\"value\":\"Battery Not Detected\"},{\"name\":\"BATTERY_PREDICTIVE_FAILURE\",\"value\":\"Battery Predictive Failure/Recall\"},{\"name\":\"COMPANY_WIDE_BIOS_OUTOFDATE\",\"value\":\"BIOS Out of Date\"},{\"name\":\"HDD_PREDICTIVE_FAILURE\",\"value\":\"HDD Predictive Failure\"},{\"name\":\"HDD_SMART_EVENT_FAIL\",\"value\":\"HDD SMART Event Failure\"},{\"name\":\"HDD_STORAGE_FULL\",\"value\":\"HDD Storage Capacity Full\"},{\"name\":\"SYSTEM_ERROR_THERMAL\",\"value\":\"System Error - Thermal\"},{\"name\":\"UNKNOWN\",\"value\":\"Unassigned\"}]},{\"name\":\"OS_HEALTH\",\"value\":\"OS Health\",\"subType\":[{\"name\":\"COMPANY_WIDE_OS_BSOD\",\"value\":\"Company-wide OS BSOD\"},{\"name\":\"PERF_CPU\",\"value\":\"CPU High Utilization\"},{\"name\":\"PERF_MEMORY\",\"value\":\"Memory High Utilization\"},{\"name\":\"OS_BSOD\",\"value\":\"OS BSOD\"},{\"name\":\"OS_CRASH\",\"value\":\"OS Unexpected Crash/Reboot\"},{\"name\":\"UNKNOWN\",\"value\":\"Unassigned\"}]},{\"name\":\"SECURITY\",\"value\":\"Security\",\"subType\":[{\"name\":\"ANTIVIRUS\",\"value\":\"Antivirus\"},{\"name\":\"FIREWALL\",\"value\":\"Firewall\"},{\"name\":\"HEARTBEAT_FAILURE\",\"value\":\"Heartbeat Failure\"},{\"name\":\"UNKNOWN\",\"value\":\"Unassigned\"}]},{\"name\":\"SW_HEALTH\",\"value\":\"Software Health\",\"subType\":[{\"name\":\"REQUIRED_APP_NOT_INSTALLED\",\"value\":\"Required Apps Not Installed\"},{\"name\":\"UNKNOWN\",\"value\":\"Unassigned\"}]},{\"name\":\"UNKNOWN\",\"value\":\"Unassigned\",\"subType\":[{\"name\":\"UNKNOWN\",\"value\":\"Unassigned\"}]}]}";
//		Assert.assertTrue(settingsPage.subscribeFunctionalityApi(getEnvironment(environment),apiSubscribe,bodyAllIncidents),"Error occured in Incident subscription for All.");
		String bodyUnsubscribeAccount = "{\"type\":[{\"name\":\"HW_CHANGE\",\"value\":\"Hardware Change\",\"subType\":[{\"name\":\"HDD\",\"value\":\"HDD\"},{\"name\":\"MEMORY\",\"value\":\"Memory\"},{\"name\":\"UNKNOWN\",\"value\":\"Unassigned\"}]},{\"name\":\"HW_HEALTH\",\"value\":\"Hardware Health\",\"subType\":[{\"name\":\"BATTERY_NOT_DETECTED\",\"value\":\"Battery Not Detected\"},{\"name\":\"BATTERY_PREDICTIVE_FAILURE\",\"value\":\"Battery Predictive Failure/Recall\"},{\"name\":\"COMPANY_WIDE_BIOS_OUTOFDATE\",\"value\":\"BIOS Out of Date\"},{\"name\":\"HDD_PREDICTIVE_FAILURE\",\"value\":\"HDD Predictive Failure\"},{\"name\":\"HDD_SMART_EVENT_FAIL\",\"value\":\"HDD SMART Event Failure\"},{\"name\":\"HDD_STORAGE_FULL\",\"value\":\"HDD Storage Capacity Full\"},{\"name\":\"SYSTEM_ERROR_THERMAL\",\"value\":\"System Error - Thermal\"},{\"name\":\"UNKNOWN\",\"value\":\"Unassigned\"}]},{\"name\":\"OS_HEALTH\",\"value\":\"OS Health\",\"subType\":[{\"name\":\"COMPANY_WIDE_OS_BSOD\",\"value\":\"Company-wide OS BSOD\"},{\"name\":\"PERF_CPU\",\"value\":\"CPU High Utilization\"},{\"name\":\"PERF_MEMORY\",\"value\":\"Memory High Utilization\"},{\"name\":\"OS_BSOD\",\"value\":\"OS BSOD\"},{\"name\":\"OS_CRASH\",\"value\":\"OS Unexpected Crash/Reboot\"},{\"name\":\"UNKNOWN\",\"value\":\"Unassigned\"}]},{\"name\":\"SECURITY\",\"value\":\"Security\",\"subType\":[{\"name\":\"ANTIVIRUS\",\"value\":\"Antivirus\"},{\"name\":\"FIREWALL\",\"value\":\"Firewall\"},{\"name\":\"HEARTBEAT_FAILURE\",\"value\":\"Heartbeat Failure\"},{\"name\":\"UNKNOWN\",\"value\":\"Unassigned\"}]},{\"name\":\"SW_HEALTH\",\"value\":\"Software Health\",\"subType\":[{\"name\":\"REQUIRED_APP_NOT_INSTALLED\",\"value\":\"Required Apps Not Installed\"},{\"name\":\"UNKNOWN\",\"value\":\"Unassigned\"}]},{\"name\":\"UNKNOWN\",\"value\":\"Unassigned\",\"subType\":[{\"name\":\"UNKNOWN\",\"value\":\"Unassigned\"}]}]}";
//		Assert.assertTrue(settingsPage.subscribeFunctionalityApi(getEnvironment(environment),apiSubscribe,bodyUnsubscribeAccount),"Error occured in Incident unsubscription for single type.");
		logout();
		login("IT_ADMIN_PC_USERNAME", "IT_ADMIN_PC_PASSWORD");
		waitForPageLoaded();
		tenantID = getValueFromToken("tenant");
		gotoIncidentTab();
		waitForPageLoaded();
		resetTableConfiguration();
		String incidentId = settingsPage.submitCaseUsingAPICaseCreation(type, subtype, incidentTitle, incidentDescription, tenantID, deviceID);
		settingsPage.enterTextForSettingsPage("idSearchBox", incidentId);
		settingsPage.waitForElementsOfSettingsPage("tableOverlay");
		settingsPage.clickByJavaScriptOnSettingsPage("selectedIncidentTitle");
		LOGGER.info("Redirected to incident details page");
		sleeper(3000);
		settingsPage.createCaseRequestingLocationFromEndUser();
		settingsPage.waitForElementsOfSettingsPage("caseInformationTileTitle");
		settingsPage.scrollTillViewSettingsPage("caseInformationTileTitle");
		softAssert.assertTrue(settingsPage.getTextOfSettingsPage("caseInformationTileTitle").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "incident.details.section.case.information")), "Title on case information tile title is incorrect");
		softAssert.assertTrue(settingsPage.getTextOfSettingsPage("caseIDLabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "incidents.caseID")), "Case ID label on case information tile title is incorrect");
		softAssert.assertTrue(settingsPage.getTextOfSettingsPage("caseStatusLabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "incident.list.caseStatus")), "Case status label on case information tile title is incorrect");
		softAssert.assertTrue(settingsPage.getTextOfSettingsPage("caseSubmissionDate").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "incident.details.section.case.submission")), "Case submission value on case information tile title is incorrect");
		softAssert.assertTrue(settingsPage.getTextOfSettingsPage("caseLocation").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "incident.details.section.case.location")), "Case location label on case information tile title is incorrect");
		softAssert.assertTrue(settingsPage.getTextOfSettingsPage("caseContact").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "incident.details.section.case.contact")), "Case contact label on case information tile title is incorrect");
		
		softAssert.assertTrue(settingsPage.getTextOfSettingsPage("statusValue").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "premier.care.case.status.pending_shipto_location")), "Case status on case information tile title is incorrect");
		softAssert.assertTrue(settingsPage.getTextOfSettingsPage("caseLocationValue").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "global.pending")), "Case location on case information tile title is incorrect");
		settingsPage.clickOnElementsOfSettingsPage("editCaseLocation");
		settingsPage.createLocation();
		softAssert.assertFalse(settingsPage.verifyElementsOfSettingsPage("editCaseLocation"), "Edit location button still visible after updating the location");
		
		softAssert.assertAll();
		LOGGER.info("Case creation by requesting location from end user functionality verified successfully");
	}

	/**
	 * This test case verifies comments tab on settings page
	 * User Story 325732: [SubsSnow][Subscription][UI] Comments tab in company detail page.
	 * @throws Exception
	 */
	@Test(priority = 61, groups = { "REGRESSIONSETTINGS2", "SMOKE", "REGRESSION_CI", "SMOKE_CI"}, description = "Test Case 456986")
	public final void verifyCommentsTabOnCompaniesDetailsPage() throws Exception {
		login("MSP_COMPANY_DETAILS_EMAIL", "MSP_COMPANY_DETAILS_PASSWORD");
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		waitForPageLoaded();
		gotoCompaniesTab();
		impersonateCompanyByCompanyName(getEnvironmentSpecificData(System.getProperty("environment"), "AUTOMATION_COMPANY"));
		LOGGER.info("Navigated to Company details page");
		settingsPage.waitForElementsOfSettingsPage("companyOverviewTab");

		if(settingsPage.verifyElementsOfSettingsPage("scrollRight"))
			settingsPage.clickOnElementsOfSettingsPage("scrollRight");

		settingsPage.clickOnElementsOfSettingsPage("commentsTab");
		LOGGER.info("Redirected to comments tab");

		softAssert.assertTrue(settingsPage.getTextOfSettingsPage("commentsHeader").equalsIgnoreCase(settingsPage.getTextLanguage(LanguageCode, "daas_ui", "incident.detail.comment")),	"Comments header did not match");
		softAssert.assertTrue(settingsPage.verifyElementsOfSettingsPage("addCommentButton"),"Add Comments button not present on company details page.");
		softAssert.assertTrue(settingsPage.getTextOfSettingsPage("addCommentButton").equalsIgnoreCase(settingsPage.getTextLanguage(LanguageCode, "daas_ui", "incidents.controls.add_comment")),"Add Comments button text did not match");
		settingsPage.clickOnElementsOfSettingsPage("addCommentButton");

		// Verify add comment functionality
		softAssert.assertTrue(settingsPage.getTextOfSettingsPage("addCommentHeader").equals(settingsPage.getTextLanguage(LanguageCode, "daas_ui","incidents.controls.add_comment")),"Add comment title did not match on pop up");
		softAssert.assertTrue(settingsPage.verifyElementsOfSettingsPage("submitCommentButton"),"Submit button not present on add comment popup");
		softAssert.assertTrue(settingsPage.verifyElementsOfSettingsPage("cancelCommentButton"),"Cancel button not present on add comment popup");
		settingsPage.clickOnElementsOfSettingsPage("submitCommentButton");
		softAssert.assertTrue(settingsPage.verifyElementsOfSettingsPage("commentsErrorMessage"),"Error message not appearing for empty comment");
		String comment = generateRandomString(20);
		settingsPage.enterTextForSettingsPage("commentTextBox", comment);
		settingsPage.clickOnElementsOfSettingsPage("submitCommentButton");
		settingsPage.waitForElementsOfSettingsPage("toastNotification");
		softAssert.assertTrue(settingsPage.verifyElementsOfSettingsPage("toastNotification"),"Toast notification not generated after adding new comment");
		settingsPage.waitForElementsOfSettingsPageForinvisibile("toastNotification");
		softAssert.assertTrue(settingsPage.matchTextOfSettingsPage("newComment", comment),"Newly added comment did not reflect on company settings page");

		// Verify edit comment functionality
		settingsPage.clickOnElementsOfSettingsPage("hamburgerIcon");
		settingsPage.clickOnElementsOfSettingsPage("editButtonOnHamburger");
		softAssert.assertTrue(settingsPage.getTextOfSettingsPage("addCommentHeader").equals(settingsPage.getTextLanguage(LanguageCode, "daas_ui","companies.details.comments.edit.label")),"Edit comment title did not match on pop up");
		String editedComment = generateRandomString(20);
		settingsPage.enterTextForSettingsPage("commentTextBox", editedComment);
		settingsPage.clickOnElementsOfSettingsPage("cancelCommentButton");
		softAssert.assertTrue(settingsPage.matchTextOfSettingsPage("newComment", comment),"Cancel functionality not working while editing comment");

		settingsPage.clickOnElementsOfSettingsPage("hamburgerIcon");
		settingsPage.clickOnElementsOfSettingsPage("editButtonOnHamburger");
		settingsPage.enterTextForSettingsPage("commentTextBox", editedComment);
		settingsPage.clickOnElementsOfSettingsPage("submitCommentButton");
		settingsPage.waitForElementsOfSettingsPage("toastNotification");
		softAssert.assertTrue(settingsPage.verifyElementsOfSettingsPage("toastNotification"),"Toast notification not generated after editing comment");
		settingsPage.waitForElementsOfSettingsPageForinvisibile("toastNotification");
		softAssert.assertTrue(settingsPage.matchTextOfSettingsPage("newComment", editedComment),"Edited comment not reflected on settings tab");

		// Verify delete comment functionality
		settingsPage.clickOnElementsOfSettingsPage("hamburgerIcon");
		settingsPage.clickOnElementsOfSettingsPage("deleteButtonOnHamburger");
		softAssert.assertTrue(settingsPage.getTextOfSettingsPage("addCommentHeader").equals(settingsPage.getTextLanguage(LanguageCode, "daas_ui","companies.details.comments.delete.label")),"Delete comment title did not match on pop up");
		softAssert.assertTrue(settingsPage.verifyElementsOfSettingsPage("submitCommentButton"),"Delete button not present on delete comment popup");
		softAssert.assertTrue(settingsPage.verifyElementsOfSettingsPage("cancelCommentButton"),"Cancel button not present on delete comment popup");
		settingsPage.clickOnElementsOfSettingsPage("submitCommentButton");
		settingsPage.waitForElementsOfSettingsPage("toastNotification");
		softAssert.assertTrue(settingsPage.verifyElementsOfSettingsPage("toastNotification"),"Toast notification not generated after deleting comment");
		settingsPage.waitForElementsOfSettingsPageForinvisibile("toastNotification");
		settingsPage.waitForElementsOfSettingsPage("tableOverlay");

		if(settingsPage.verifyElementsOfSettingsPage("newComment"))
		{
			softAssert.assertFalse(settingsPage.matchTextOfSettingsPage("newComment", editedComment),"Deleted comment is present on settings tab");
		}

		softAssert.assertAll();
		LOGGER.info("Test case to verify comments tab on company detail spage passed successfully.");

	}
	
	

	/**
	 * This test case validates the redirection of certain URLs
	 *
	 * @throws Exception
	 */
	@Test(priority = 62, groups = { "REGRESSIONSETTINGS2", "SMOKE", "REGRESSION_CI","SMOKE_CI" }, description = "Test Case 582135")
	public final void verifyURLRedirection() throws Exception {

		SoftAssert softAssert = new SoftAssert();
		getUrl(getEnvironment(System.getProperty("environment")) + "blog");
		waitForPageLoaded();
		softAssert.assertTrue(getUrlOfCurrentPage().equals(SettingsVariables.BLOG_URL), " Redirected URL is incorrect");
		getUrl(getEnvironment(System.getProperty("environment")) + "help");
		waitForPageLoaded();
		softAssert.assertTrue(getUrlOfCurrentPage().equals(SettingsVariables.HELP_URL),
				" Redirected URL is incorrect");
		getUrl(getEnvironment(System.getProperty("environment")) + "faq");
		waitForPageLoaded();
		softAssert.assertTrue(getUrlOfCurrentPage().contains(SettingsVariables.FAQ_URL),
				" Redirected URL is incorrect");
		softAssert.assertAll();
	}

	/**
	 * This test case validates the context sensitive help links on Settings screen
	 *
	 * @throws Exception
	 */
	@Test(priority = 63, groups = { "REGRESSIONSETTINGS2", "SMOKE", "REGRESSION_CI",
	"SMOKE_CI" }, description = "Test Case ID : 891080")
	public final void verifyOnlineHelpLinksOnSettings() throws Exception {
		login("IT_ADMIN_HELP_AND_SUPPORT_EMAIL", "IT_ADMIN_HELP_AND_SUPPORT_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();

		// Verify help and support link for Settings tab
		waitForPageLoaded();
		gotoNonMSPSettingsCompanyUserTab();
		LOGGER.info("Redirected to Settings tab");
		settingsPage.waitForElementsOfSettingsPage("helpAndSupportIcon");
		settingsPage.clickOnElementsOfSettingsPage("helpAndSupportIcon");
		softAssert.assertTrue(
				settingsPage.matchTextOfSettingsPage("help&SupportLink",
						getTextLanguage(LanguageCode, "daas_ui", "settings.help_and_support.title")),
				"Help & Support link text did not match on Settings tab");
		settingsPage.clickOnElementsOfSettingsPage("help&SupportLink");
		LOGGER.info("Clicked on Help & Support link from Settings tab");
		waitForPageLoaded();
		softAssert.assertTrue(
				settingsPage.getTextOfSettingsPage("helpAndSupportTitleTextOnBreadcrumbs")
				.equals(settingsPage.getTextLanguage(LanguageCode, "daas_ui", "sidemenu.support")),
				"User not redirected to Help & Support tab after clicking on Help & Support link from Settings tab");
		gotoNonMSPSettingsCompanyUserTab();

		// Verify overview link for Settings tab
		waitForPageLoaded();
		LOGGER.info("Redirected to Settings tab");
		settingsPage.waitForElementsOfSettingsPage("helpAndSupportIcon");
		settingsPage.clickOnElementsOfSettingsPage("helpAndSupportIcon");
		softAssert.assertTrue(
				settingsPage.matchTextOfSettingsPage("settingsLink",
						getTextLanguage(LanguageCode, "daas_ui", "sidemenu.mspSettings")),
				"Settings link text did not match on Settings tab");
		settingsPage.clickOnElementsOfSettingsPage("settingsLink");
		LOGGER.info("Clicked on overview link from Settings tab");
		settingsPage.switchToDifferentTab();
		sleeper(4000);// Url takes time to load
		softAssert.assertTrue(settingsPage.getUrlOfCurrentPage().contains(ConstantURL.KNOWLEDGE_BASE),
				"User not redirected to Online help section after clicking on Settings link from Settings tab");
		softAssert.assertTrue(settingsPage.getUrlOfCurrentPage().contains(ConstantURL.LANGUAGE_PARAMETER),
				"Language parameter not added in Service now URL");
		settingsPage.switchBackToPreviousTab();

		softAssert.assertAll();
		LOGGER.info("Test case to verify context sensitive links on Settings passed successfully.");
	}

	
	@Test(priority = 64, groups = { "PENTEST"}, description = "This test validates custom role creation for IT Admin on PENTEST Test Cases: 331685,331691,331930,331931 ")
	public final void verifyRoleCreationForITA() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login("MSP_ADMIN_US_EMAIl","MSP_ADMIN_US_PASSWORD");
		String tenantID = getValueFromToken("tenant");
		gotoNonMSPSettingsTab();
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
		waitForPageLoaded();
		LOGGER.info("Redirected to Settings page");
		settingsPage.clickOnElementsOfSettingsPage("roleAndPermissionTabCompany");
        String url = roleIdURL+ConstantURL.ROLEID_URL1+tenantID+ConstantURL.ROLEID_URL2;
		List<String> roleIDs = settingsPage.getAllRoleIds(url,"{}", 0, "id");
		settingsPage.removeAllRoles(tenantID,roleIdURL,roleIDs,environment);
	    softAssert.assertTrue(settingsPage.createPermission(LanguageCode,getTextLanguage(LanguageCode, "daas_ui", "global.yes")),"Custom role did not created successfully.");
		softAssert.assertAll();
		LOGGER.info("Validation of role creation completed successfully for MSP, Partner Administrator, IT Admin.");
	}
	
	//Need to check this test case.
	/**
	 * This test case verifies active care assistance tab for active care company user having country region as india
	 *
	 * @throws Exception
	 */
	@Test(priority = 65, groups = { "REGRESSIONSETTINGS2","SMOKE","REGRESSION_CI","SMOKE_CI"}, description = "Test Case ID : 566805", enabled=true)
	public final void verifyActiveCareAssistancetabForPCITA() throws Exception {
		login("ACTIVECARETENANT_EMAIL","ACTIVECARETENANT_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
		ArrayList<String> daysList = new ArrayList<>(Arrays.asList(getTextLanguage(LanguageCode, "daas_ui","settings.hours_of_operation.days.0").toLowerCase(), getTextLanguage(LanguageCode, "daas_ui","settings.hours_of_operation.days.1").toLowerCase(), getTextLanguage(LanguageCode, "daas_ui","settings.hours_of_operation.days.2").toLowerCase(), getTextLanguage(LanguageCode, "daas_ui","settings.hours_of_operation.days.3").toLowerCase(), getTextLanguage(LanguageCode, "daas_ui","settings.hours_of_operation.days.4").toLowerCase(), getTextLanguage(LanguageCode, "daas_ui","settings.hours_of_operation.days.5").toLowerCase(), getTextLanguage(LanguageCode, "daas_ui","settings.hours_of_operation.days.6").toLowerCase()));
		gotoHelpAndSupportTab();
		waitForPageLoaded();
		LOGGER.info("Redirected to Settings page");
		Assert.assertTrue(settingsPage.verifyElementsOfSettingsPage("activeCareTab"), "Active care tab is not present for an active care company");
		LOGGER.info("Active care tab present, proceed with execution");
		softAssert.assertTrue(settingsPage.getTextOfSettingsPage("activeCareTab").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui","help.support.tab.activeCare.assistance")), "Active care tab label is incorrect");
		settingsPage.clickOnElementsOfSettingsPage("activeCareTab");
		softAssert.assertTrue(settingsPage.matchTextOfSettingsPage("activeCareTabHeader", getTextLanguage(LanguageCode, "daas_ui", "help.section.title")),"Header on assistance tile is incorrect");
		softAssert.assertTrue(settingsPage.matchTextOfSettingsPage("activeCareTabDescription", getTextLanguage(LanguageCode, "daas_ui", "help.support.msp.section.discription")),"Description on assistance tile is incorrect");
		softAssert.assertTrue(settingsPage.matchTextOfSettingsPage("directSupportHeader", getTextLanguage(LanguageCode, "daas_ui", "help.support_box.direct_support.title")),"Header on direct support tile is incorrect");
		softAssert.assertTrue(settingsPage.matchTextOfSettingsPage("directSupportDescription", getTextLanguage(LanguageCode, "daas_ui", "help.support_box.direct_support.description")),"Description on direct support tile is incorrect");
		softAssert.assertTrue(settingsPage.matchTextOfSettingsPage("directSupportPhoneNumber", getEnvironmentSpecificData(System.getProperty("environment"), "ACTIVE_CARE_SUPPORT_PHONENUMBER")),"Phone number on direct support tile is incorrect");
		softAssert.assertTrue(settingsPage.matchTextOfSettingsPage("hoursOfOperationHeader", getTextLanguage(LanguageCode, "daas_ui","settings.hours_of_operation.title")), "Header on hours of operation tile is incorrect");
		softAssert.assertTrue(settingsPage.getTextOfListOfSettingsPage("daysOfOperation").equals(daysList), "Days of operation are incorrect");
		softAssert.assertTrue(settingsPage.matchTextOfSettingsPage("countryLabelforActiveCare", getTextLanguage(LanguageCode, "daas_ui", "global.country")), "Country label missing from hours of operation tile");
		softAssert.assertTrue(settingsPage.matchTextOfSettingsPage("countryValue", getEnvironmentSpecificData(System.getProperty("environment"), "ACTIVE_CARE_SUPPORT_COUNRTY")), "Country missing from hours of operation tile");
		softAssert.assertTrue(settingsPage.matchTextOfSettingsPage("timezoneLabel", getTextLanguage(LanguageCode, "daas_ui", "settings.time_zone.label")), "Time zone label missing from hours of operation tile");
		softAssert.assertTrue(settingsPage.matchTextOfSettingsPage("exceptionsLabel", getTextLanguage(LanguageCode, "daas_ui", "help.support_box.exceptions.label")), "Exceptions label missing from hours of operation tile");
		softAssert.assertTrue(settingsPage.matchTextOfSettingsPage("timezoneValue", SettingsVariables.NO_DATA), "Time zone value missing from hours of operation tile");
		softAssert.assertTrue(settingsPage.matchTextOfSettingsPage("exceptionsValue", SettingsVariables.NONE), "Exceptions value missing from hours of operation tile");
		softAssert.assertAll();
	}

	/**
	 * This test case verifies HP Assistance tab is not visible for partner supported customers
	 * User Story 611341: [Partner][PrtnrMSP][UI] Hide HP Support Tab for Partner Supported Customers
	 *
	 * @throws Exception
	 */
	@Test(priority = 66, groups = { "REGRESSIONSETTINGS2","SMOKE","REGRESSION_CI","SMOKE_CI"}, description = "Test Case ID : 614299")
	public final void verifyPartnerAssistancetabForCompany() throws Exception {

		login("HELP_AND_SUPPORT_TEST_COMPANY_EMAIL", "HELP_AND_SUPPORT_TEST_COMPANY_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
		String tenantID = null;

		waitForPageLoaded();
		gotoNonMSPSettingsTab();
		LOGGER.info("Redirected to Settings tab");
		sleeper(2000);
		waitForPageLoaded();
		companiesPage.waitForPresenceOfElementsOfCompaniesPage("assignedPartner");
		companiesPage.clickOnElementsOfCompaniesPage("assignedPartner");
		companiesPage.waitForPresenceOfElementsOfCompaniesPage("invitationLabel");
		softAssert.assertTrue(unassignPartner(environment), "Partner unassign failed");
		waitForPageLoaded();

		// Verify HP assistance is present when partner is not assigned to company
		gotoHelpAndSupportTab();
		settingsPage.waitForElementsOfSettingsPage("hpAssistanceTab");
		softAssert.assertTrue(settingsPage.getTextOfSettingsPage("hpAssistanceTab").equalsIgnoreCase(settingsPage.getTextLanguage(LanguageCode, "daas_ui", "help.support.tab.hp.assistance")),"HP Assistance tab not present on help and support tab");

		// Send partner invitation
		gotoNonMSPSettingsTab();
		LOGGER.info("Redirected to Settings tab");
		waitForPageLoaded();
		companiesPage.waitForPresenceOfElementsOfCompaniesPage("assignedPartner");
		companiesPage.clickOnElementsOfCompaniesPage("assignedPartner");
		companiesPage.waitForPresenceOfElementsOfCompaniesPage("invitationLabel");
		JSONObject jsonAuthToken = (JSONObject) parser.parse(authTokenParse(companiesPage.getTokenFromLocalStorage("dui_token_e")));
		tenantID = jsonAuthToken.get("tenant").toString();
		softAssert.assertTrue(removeAllPendingInvitations(environment, tenantID, getEnvironment(environment)),"Pending Company Invitation removal failed");

		// Remove received pending invitations from partner
		softAssert.assertTrue(removeAllReceievedPendingInvitations(environment, tenantID, getEnvironment(environment)),"Pending Partner Invitation removal failed");
		LOGGER.info("Removed all pending invitations from company view");

		settingsPage.waitForElementsOfSettingsPage("editAssignedpartnerButton");
		settingsPage.clickOnElementsOfSettingsPage("editAssignedpartnerButton");
		LOGGER.info("Clicked on edit assigned partner button");
		sleeper(4000);
		waitForPageLoaded();
		settingsPage.waitForElementsOfSettingsPage("assignPartnerDD");
		settingsPage.clickOnElementsOfSettingsPage("assignPartnerDD");
		LOGGER.info("Clicked on edit assigned partner dropdown");
		sleeper(2000);
		settingsPage.waitForElementsOfSettingsPage("assignPartnerSearchBox");
		settingsPage.clickOnElementsOfSettingsPage("assignPartnerSearchBox");
		settingsPage.enterTextForSettingsPage("assignPartnerSearchBox", getEnvironmentSpecificData(environment, "ASSIGNED_PARTNER_NAME"));
		waitForPageLoaded();
		settingsPage.selectFirstOptionFromDropdownOnSettingsPage("partnersList");
		LOGGER.info("Selected partner on dropdown");
		settingsPage.clickOnElementsOfSettingsPage("assignPartnerInvite");
		LOGGER.info("Clicked on save assigned partner button");
		settingsPage.waitForElementsOfSettingsPage("toastNotification");
		settingsPage.waitForElementsOfSettingsPage("tableOverlay");
		LOGGER.info("Invitation sent to partner");
		logout();

		login("RESELLER_PARTNERS_EMAIL", "RESELLER_PARTNERS_PASSWORD");
		waitForPageLoaded();
		gotoCompaniesTab();
		LOGGER.info("Redirected to companies tab");
		resetTableConfiguration();
		companiesPage.enterTextForCompaniesPage("companyNameSearch", getEnvironmentSpecificData(environment, "HELP_AND_SUPPORT_COMPANY_NAME"));
		companiesPage.waitForElementsOfCompaniesPage("tableOverlay");
		List<WebElement> companiesList = companiesPage.getElementsOfCompanyListPage("companyCheckbox");
			List<WebElement> companyCheckboxList = companiesPage.getElementsOfCompanyListPage("companySearchResult");
			companiesPage.clickElementsOfCompaniesPage(companiesList, companyCheckboxList);
		companiesPage.clickOnElementsOfCompaniesPage("acceptCustomerButton");
		companiesPage.clickOnElementsOfCompaniesPage("dialogResend");
		LOGGER.info("Clicked on Accept Customer button");
		companiesPage.waitForElementsOfCompaniesPage("toastNotification");
		LOGGER.info("Company invitation is accepted by partner ");
		logout();

		// Verify Only partner assistance tab is available when subscription authorized partner is associated with company
		login("HELP_AND_SUPPORT_TEST_COMPANY_EMAIL", "HELP_AND_SUPPORT_TEST_COMPANY_PASSWORD");
		gotoHelpAndSupportTab();
		settingsPage.waitForElementsOfSettingsPage("partnerAssistanceTab");
		softAssert.assertTrue(settingsPage.getTextOfSettingsPage("partnerAssistanceTab").equalsIgnoreCase(settingsPage.getTextLanguage(LanguageCode, "daas_ui", "help.support.tab.partner.assistance")),"Partner Assistance tab not present on help and support page");
		softAssert.assertFalse(settingsPage.verifyElementsOfSettingsPage("hpAssistanceTab"),"HP Assistance tab is present on help and support page even if subscription authorized partner is associated to  company");

		softAssert.assertAll();
		LOGGER.info("Test case verifyPartnerAssistancetabForCompany passed successfully.");
	}
	
	/**
	 * This test case validates the More details link navigates to KA Article link IT Admin
	 *
	 * @throws Exception
	 */
	@Test(priority = 67, groups = { "REGRESSIONSETTINGS2", "SMOKE", "REGRESSION_CI","SMOKE_CI" }, description = "Test Case ID : 880636",enabled=false)
	public final void verifyDomainNameKAlinkForITA() throws Exception {
		login("IT_ADMIN_HELP_AND_SUPPORT_EMAIL", "IT_ADMIN_HELP_AND_SUPPORT_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();

		gotoCompanySettingsTab();
		waitForPageLoaded();
		LOGGER.info("Redirected to Settings tab");
		settingsPage.waitForElementsOfSettingsPage("preferenceTab");
		settingsPage.clickOnElementsOfSettingsPage("preferenceTab");
		
		settingsPage.scrollTillViewSettingsPage("domainName");
		settingsPage.clickOnElementsOfSettingsPage("domainOwnershipBtn");
		waitForPageLoaded();
		settingsPage.clickOnElementsOfSettingsPage("moreDetailsLink");
		LOGGER.info("Clicked on more details link");
		settingsPage.switchToDifferentTab();
		sleeper(4000);// Url takes time to load
		softAssert.assertTrue(settingsPage.getUrlOfCurrentPage().contains(ConstantURL.KA_ARTICLE_LINK), "User not redirected to KA article link");
		settingsPage.switchBackToPreviousTab();
		
		softAssert.assertAll();
		LOGGER.info("Test case verifyDomainNameKBlinkForITA passed successfully.");
	}
	
	@Test(priority = 68, groups = { "REGRESSIONSETTINGS2", "REGRESSION_CI" }, description = "Test Case ID : 882076")
	public final void verifyComboCarePackFunctionality() throws Exception {
		login("APP_ADMIN_USER", "APP_ADMIN_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		LicensesListPage subscriptionsListPage = new LicensesListPage(PreDefinedActions.getDriver()).getInstance();
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
		CompaniesDetailsPage companiesDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver())
				.getInstance();
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		String subKey = generateRandomString(7);
		String tenantID = null;
		LOGGER.info("Logged in with app admin user");

		// Creating license key using API
		try {
			Assert.assertTrue(subscriptionsListPage.createLicenceKey(
					getEnvironmentSpecificData(System.getProperty("environment"), "DAAS_API_BASE_URL_AUTH")
					+ subscriptionURL,
					subKey, CommonVariables.COMBO_SKU), "Licence key creation failed");
			LOGGER.info("Created licence key " + subKey);
			logout();

			// Adding Active Care company and device by importing orders csv and adding
			// Proactive Insights plan to it by assigning the license key
			login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
			LOGGER.info("Logged in with root user");
			waitForPageLoaded();

			gotoOrderTab();
			LOGGER.info("Navigated to orders page");
			waitForPageLoaded();
			settingsPage.verifyImportOrders(LanguageCode, ConstantPath.IMPORT_PATH
					+ getEnvironmentSpecificData(System.getProperty("environment"), "ORDERS_COMBO_CARE"));
			sleeper(30000);

			impersonateCompanyByCompanyName(
					getEnvironmentSpecificData(System.getProperty("environment"), "COMPANY_NAME_COMBO_CARE"));
			LOGGER.info("Navigated to Company details page");
			settingsPage.waitForElementsOfSettingsPage("companyOverviewTab");
			companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("companySubscriptionTab");

			companiesDetailsPage.waitForElementsOfCompaniesDetailsPage("accountSummaryTileHeader");
			companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("addNewKeyButton");
			companiesDetailsPage.enterTextForCompaniesDetailsPage("SubscriptionkeyTextBox", subKey);
			companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("dialogSaveButton");
			companiesDetailsPage.waitForElementsOfCompaniesDetailsPage("saveSubsriptionKey");
			companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("saveSubsriptionKey");
			LOGGER.info("Clicked on the save button");
			logout();

			// Enrolling the previously added device and verifying if both plans are added
			// in the company and the device
			login("IT_ADMIN_COMBO_CARE", "IT_ADMIN_COMBO_CARE_PASSWORD");
			tenantID = getValueFromToken("tenant");
			waitForPageLoaded();
			if (companiesPage.verifyElementsOfCompaniesPage("accountSetupCloseBtn"))
				companiesPage.clickOnElementsOfCompaniesPage("accountSetupCloseBtn");
			EnrollFakeDevice.enrollFakeDeviceWithFixedSerialNumber(
					getEnvironmentSpecificData(System.getProperty("environment"), "COMPANY_NAME_COMBO_CARE"),
					companiesPage.fetchCompanyPinUsingAPI(tenantID),
					getEnvironmentSpecificData(System.getProperty("environment"), "COMPANY_EMAIL_COMBO_CARE"),
					getEnvironmentSpecificData(System.getProperty("environment"), "DEVICE_SERIAL_NUMBER_COMBO"));

			sleeper(2000);
			gotoCompanySettingsTab();
			LOGGER.info("Redirected to Settings tab");
			waitForPageLoaded();
			settingsPage.clickOnElementsOfSettingsPage("subscriptionTab");
			softAssert.assertTrue(
					settingsPage.getTextOfSettingsPage("planSummaryComboFirstPlan")
					.contains(getTextLanguage(LanguageCode, "daas_ui", "azure.onboard.BannerHeading")),
					"PI plan not added successfully");
			softAssert.assertAll();
			LOGGER.info("Test case verifyComboCarePackFunctionality passed successfully");
		} finally {
			// Logout from account
			logout();
			login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");

			// Remove created company
			LOGGER.info("Redirected to company list page");
			waitForPageLoaded();
			impersonateCompanyByCompanyName(
					getEnvironmentSpecificData(System.getProperty("environment"), "COMPANY_NAME_COMBO_CARE"));
			LOGGER.info("Redirected to company details page");
			tenantID = getUrlOfCurrentPage().substring(getUrlOfCurrentPage().length() - 36);

			softAssert.assertTrue(companiesPage.removeCompanyUsingAPI(tenantID), "Company removal failed");
			LOGGER.info("Company has been deleted");
		}
	}
	
	@Test(priority = 69, groups = { "REGRESSIONSETTINGS2", "REGRESSION_CI" }, description = "Test Case ID : 903099", enabled = false)
	public final void verifyDisablingOfComboCareForPIWithEnrolledDevice() throws Exception {
		login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
		LOGGER.info("Logged in with root user");
		waitForPageLoaded();

		gotoOrderTab();
		LOGGER.info("Navigated to orders page");
		waitForPageLoaded();
		settingsPage.verifyImportOrders(LanguageCode, ConstantPath.IMPORT_PATH
				+ getEnvironmentSpecificData(System.getProperty("environment"), "ORDERS_PI_ENROLLED"));
		sleeper(45000);
		Assert.assertTrue(
				settingsPage.postNotificationCheckUnsuccessfulImportInV3(
						getEnvironmentSpecificData(System.getProperty("environment"), "ORDERS_PI_ENROLLED")),
				"Message on notification window is incorrect");
		LOGGER.info("Notification message verification for import has passed");
		settingsPage.waitForElementsOfSettingsPage("notificationBellIcon");
		settingsPage.clickOnElementsOfSettingsPage("notificationBellIcon");
		waitForPageLoaded();
		LOGGER.info("Test case verifyDisablingOfComboCareForPIWithEnrolledDevice passed successfully");
	}

	@Test(priority = 70, groups = { "REGRESSIONSETTINGS2", "REGRESSION_CI" }, description = "Test Case ID : 903099", enabled = false)
	public final void verifyDisablingOfComboCareForPIWithUnenrolledDevice() throws Exception {
		login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		String tenantID = null;
		LOGGER.info("Logged in with root user");
		waitForPageLoaded();

		gotoOrderTab();
		LOGGER.info("Navigated to orders page");
		waitForPageLoaded();
		settingsPage.verifyImportOrders(LanguageCode, ConstantPath.IMPORT_PATH
				+ getEnvironmentSpecificData(System.getProperty("environment"), "ORDERS_PI_UNENROLLED"));
		sleeper(60000);
		Assert.assertTrue(
				settingsPage.postNotificationCheckImportInV3(
						getEnvironmentSpecificData(System.getProperty("environment"), "ORDERS_PI_UNENROLLED")),
				"Message on notification window is incorrect");
		LOGGER.info("Notification message verification for import has passed");
		settingsPage.waitForElementsOfSettingsPage("notificationBellIcon");
		settingsPage.clickOnElementsOfSettingsPage("notificationBellIcon");
		waitForPageLoaded();
		resetTableConfiguration();
		settingsPage.clearSelectionOfSettingPage("clearFilter");
		settingsPage.waitForElementsOfSettingsPage("nameSearchBox");
		settingsPage.enterTextForSettingsPage("nameSearchBox",
				getEnvironmentSpecificData(System.getProperty("environment"), "COMP_NAME_ORDERS_PI_UNENROLLED"));
		sleeper(2000);
		settingsPage.waitForElementsOfSettingsPage("subscriptionBoxBefore");
		settingsPage.clickOnElementsOfSettingsPage("subscriptionBoxBefore");
		settingsPage.waitForElementsOfSettingsPage("subscriptionSearchbox");
		settingsPage.enterTextForSettingsPage("subscriptionSearchbox",SettingsVariables.ACTIVE_CARE_PLAN_NAME);
		settingsPage.waitForElementsOfSettingsPage("subscriptionDropdownListFirstOption");
		settingsPage.clickOnElementsOfSettingsPage("subscriptionDropdownListFirstOption");
		pressKey(Keys.ESCAPE);
		settingsPage.waitForElementsOfSettingsPage("firstCompany");
		settingsPage.clickOnElementsOfSettingsPage("firstCompany");
		LOGGER.info("Navigated to Company details page");
		waitForPageLoaded();
		LOGGER.info("New tenant created");
		tenantID = getUrlOfCurrentPage().substring(getUrlOfCurrentPage().length() - 36);

		Assert.assertTrue(companiesPage.removeCompanyUsingAPI(tenantID), "Company removal failed");
		LOGGER.info("Company has been deleted");
		LOGGER.info("Test case verifyDisablingOfComboCareForPIWithUnenrolledDevice passed successfully");
	}

	/**
	 * This test case validates if Virtual Agent is present for Company with Flip
	 * Plan on Help and Support page
	 *
	 * 
	 */

	@Test(priority = 71, groups = { "REGRESSIONSETTINGS1", "REGRESSION_CI" }, description = "Test Case ID : 881200")

	public final void verifyVirtualAgent() throws Exception {
		login("RA_HELP_AND_SUPPORT_EMAIL", "RA_HELP_AND_SUPPORT_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
		gotoHelpAndSupportTab();
		settingsPage.waitForElementsOfSettingsPage("toastNotification");
		Assert.assertTrue(settingsPage.getTextOfSettingsPage("bottext").equals(
				settingsPage.getTextLanguage(LanguageCode, "lhserver", "help_and_support.select_country.title")),
				"Virtual Agent is not present");
		LOGGER.info("Virtual Agent is present");
		settingsPage.clickOnElementsOfSettingsPage("virtualagent");
		settingsPage.switchToDifferentTab();
		waitForPageLoaded();
		softAssert.assertTrue(
				settingsPage.getUrlOfCurrentPage().contains(getEnvironmentSpecificData(environment, "VIRTUAL_AGENT")),
				"User is not redirected to Virtual Agent page");
		settingsPage.switchBackToPreviousTab();
		softAssert.assertAll();
		LOGGER.info("Validation of Virtual Agent is completed.");
	}

	@Test(priority = 72, groups = {"REGRESSIONSETTINGS1", "REGRESSION_CI" }, description = "Test Case ID : 903099")

	public final void verifyCaseCreationForACWPTCombo() throws Exception {
		login("IT_ADMIN_COMBO_ACWPT", "IT_ADMIN_COMBO_ACWPT_PASSWORD");
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		waitForPageLoaded();
		sleeper(2000);
		gotoDashboardTab();
		LOGGER.info("Redirected to dashboard tab");
		waitForPageLoaded();
		dashboardPage.mouseHoverOfDashboardPage("createCaseButton");
		Assert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("createCaseButton"), "Create case button not present on Dashboard page");
		LOGGER.info("Test case verifyCaseCreationForACWPTCombo passed successfully");
	}

	@Test(priority = 73, groups = {"REGRESSION_CI"}, description = "TEST CASE 920939")
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
	
	@Test(priority = 74, groups = { "REGRESSION_CI" }, description = "TEST CASE 923756")
	public final void verifySetLatestEmail() throws Exception {
		login("ROOT_ADMIN_NEW_USER_US", "ROOT_ADMIN_NEW_PASSWORD_US");
		SoftAssert softAssert = new SoftAssert();
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		CompaniesDetailsPage companiesDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver())
				.getInstance();
		String tenantID = null;
		LOGGER.info("Logged in with root user");
		waitForPageLoaded();
		sleeper(2000);

		// Uploading CSV with a primary administrator email

		gotoOrderTab();
		LOGGER.info("Navigated to orders page");
		waitForPageLoaded();
		settingsPage.verifyImportOrdersForMultiPlan(ConstantPath.IMPORT_PATH
				+ getEnvironmentSpecificData(System.getProperty("environment"), "ORDERS_SET_LATEST"));
		softAssert.assertTrue(
				settingsPage.matchTextOfSettingsPage("successNotification",
						getTextLanguage(LanguageCode, "daas_ui", "assets.import.asset.import_in_progress")),
				"Toast notification did not appear");
		sleeper(60000);
		Assert.assertTrue(
				settingsPage.postNotificationCheckImportInV3(
						getEnvironmentSpecificData(System.getProperty("environment"), "ORDERS_SET_LATEST")),
				"Message on notification window is incorrect");
		LOGGER.info("Notification message verification for import has passed");
		waitForPageLoaded();
		LOGGER.info("New tenant created");

		// Uploading the same CSV with a separate primary administrator email
		sleeper(2000);
		gotoOrderTab();
		LOGGER.info("Navigated to orders page");
		waitForPageLoaded();
		settingsPage.verifyImportOrdersForMultiPlan(ConstantPath.IMPORT_PATH
				+ getEnvironmentSpecificData(System.getProperty("environment"), "ORDERS_SET_LATEST_COPY"));
		softAssert.assertTrue(
				settingsPage.matchTextOfSettingsPage("successNotification",
						getTextLanguage(LanguageCode, "daas_ui", "assets.import.asset.import_in_progress")),
				"Toast notification did not appear");
		sleeper(60000);
		Assert.assertTrue(
				settingsPage.postNotificationCheckImportInV3(
						getEnvironmentSpecificData(System.getProperty("environment"), "ORDERS_SET_LATEST_COPY")),
				"Message on notification window is incorrect");
		LOGGER.info("Notification message verification for import has passed");
		settingsPage.waitForElementsOfSettingsPage("notificationBellIcon");
		settingsPage.clickOnElementsOfSettingsPage("notificationBellIcon");
		waitForPageLoaded();
		resetTableConfiguration();
		settingsPage.clearSelectionOfSettingPage("clearFilter");
		settingsPage.waitForElementsOfSettingsPage("nameSearchBox");
		settingsPage.enterTextForSettingsPage("nameSearchBox",
				getEnvironmentSpecificData(System.getProperty("environment"), "ORDERS_SET_LATEST_COMPANY_NAME"));
		sleeper(2000);
		settingsPage.waitForElementsOfSettingsPage("firstCompany");
		settingsPage.clickOnElementsOfSettingsPage("firstCompany");
		LOGGER.info("Navigated to Company details page");
		waitForPageLoaded();
		sleeper(2000);
		softAssert
		.assertTrue(
				companiesDetailsPage.matchTextOfCompaniesDetailsPage("companyPrimaryAdministratorEmail",
						getEnvironmentSpecificData(System.getProperty("environment"),
								"ORDERS_SET_LATEST_USERNAME2")),
				"Latest email is not set to primary administrator");

		tenantID = getUrlOfCurrentPage().substring(getUrlOfCurrentPage().length() - 36);
		Assert.assertTrue(companiesPage.removeCompanyUsingAPI(tenantID), "Company removal failed");
		LOGGER.info("Company has been deleted");
		LOGGER.info("Test case verifySetLatestEmail passed successfully");

	}

	@Test(priority = 75, groups = { "REGRESSION", "REGRESSION_CI" }, description = "949028", enabled = true)
	public final void verifyInvisibilityOfWolfProSoftwareInstaller() throws Exception {
		login("MSP_SETTINGS_EMAIL_SA", "MSP_SETTINGS_PASSWORD_SA");
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		gotoHelpAndSupportTab();
		waitForPageLoaded();
		// Verify removal of Wolf Pro Security installer from client software downloads
		// page
		softAssert.assertTrue(settingsPage.verifyElementsOfSettingsPage("softwareDownloadsLink"),
				"Software download link is not present on Help and Support Page");
		settingsPage.waitForElementsOfSettingsPage("softwareDownloadsLink");
		settingsPage.clickOnElementsOfSettingsPage("softwareDownloadsLink");
		sleeper(2000);
		settingsPage.switchToDifferentTab();
		sleeper(4000);// Url takes time to load
		getUrl(SetTestEnvironments.getEnvironment(System.getProperty("environment")) + ConstantURL.SOFTWARE_DOWNLOAD);
		LOGGER.info(
				"Verify that HP Wolf Pro Security installer should not be present on client software downloads page");
		softAssert.assertFalse(settingsPage.verifyElementsOfSettingsPage("wolfProSecurityApp"),
				"Wolf Pro installer is still present");
		settingsPage.switchBackToPreviousTab();
		softAssert.assertAll();
		LOGGER.info("Verified that Wolf Pro Security installer is removed from client software downloads page");

	}
	
	// need to work on this.
	@Test(priority = 76, groups = {"REGRESSIONSETTINGS2", "SMOKE", "REGRESSION_CI", "SMOKE_CI",
			"STABILIZATION_STAGING" }, description = "Test Case ID : 1003443", enabled=false)
	public final void verifyAddOrdersTileMandatoryColumnsOnly() throws Exception {
		login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
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
				getEnvironmentSpecificData(System.getProperty("environment"), "ORDERS_MANDATORY"));
		//sleeper(2000);
		softAssert.assertTrue(
				settingsPage.matchTextOfSettingsPage("successNotification",
						getTextLanguage(LanguageCode, "daas_ui", "assets.import.asset.import_in_progress")),
				"Toast notification did not appear");
		sleeper(20000);
		Assert.assertTrue(
				settingsPage.postNotificationCheckImportInV3(
						getEnvironmentSpecificData(System.getProperty("environment"), "ORDERS_MANDATORY")),
				"Message on notification window is incorrect");
		LOGGER.info("Notification message verification for import has passed");

		softAssert.assertAll();
	}
	
	
	
	/**
	 * @throws Exception
	 */
	@Test(priority = 77, groups = {"ACTIVECARESANITY", "REGRESSIONSETTINGS2", "SMOKE", "REGRESSION_CI", "SMOKE_CI",
			"STABILIZATION_STAGING" }, description = "Test Case ID:")
	public final void verifyIncidentEligibilityTileForAppAdmin() throws Exception {
		SoftAssert sa = new SoftAssert();
		login("APP_ADMIN_USER", "APP_ADMIN_PASSWORD");
		gotoNonMSPSettingsTabAppAdmin();
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
		Assert.assertTrue(
				settingsPage.getTextOfSettingsPage("incidentEligibityHeader").equals(
						settingsPage.getTextLanguage(LanguageCode, "daas_ui", "incident.eligibility.tile.title")),
				"Incident Eligibility title text is incorrect");
		ArrayList<String> expectedPremierCareList = new ArrayList<String>(Arrays.asList(
				(getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties",
						"com.hp.mpi.icm.type.hardwarehealth")),
				(getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties",
						"com.hp.mpi.icm.subtype.systemthermal")),
				(getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties",
						"com.hp.mpi.icm.subtype.hddpredictive")),
				(getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties",
						"com.hp.mpi.icm.subtype.biosoutofdate")),
				(getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties",
						"com.hp.mpi.icm.subtype.batteryneedsattention")),
				(getTextLanguage(LanguageCode, "daas_ui", "com.hp.mpi.icm.type.activecare")),
				(getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties",
						"com.hp.mpi.icm.subtype.battery")),
				(getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties",
						"com.hp.mpi.icm.subtype.hdd")),
				(getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties",
						"com.hp.mpi.icm.subtype.motherboard")),
				(getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties",
						"com.hp.mpi.icm.subtype.iodevice")),
				(getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties",
						"com.hp.mpi.icm.subtype.power")),
				(getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties",
						"com.hp.mpi.icm.subtype.other"))));
		ArrayList<String> expectedCaseCreationeList = new ArrayList<String>(Arrays.asList(
				(getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties",
						"com.hp.mpi.icm.type.hardwarehealth")),
				(getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties",
						"com.hp.mpi.icm.subtype.hddpredictive")),
				(getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties",
						"com.hp.mpi.icm.subtype.batteryneedsattention")),
				(getTextLanguage(LanguageCode, "daas_ui", "com.hp.mpi.icm.type.activecare")),
				(getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties",
						"com.hp.mpi.icm.subtype.battery")),
				(getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties",
						"com.hp.mpi.icm.subtype.hdd")),
				(getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties",
						"com.hp.mpi.icm.subtype.motherboard")),
				(getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties",
						"com.hp.mpi.icm.subtype.iodevice")),
				(getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties",
						"com.hp.mpi.icm.subtype.power")),
				(getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties",
						"com.hp.mpi.icm.subtype.other"))));
		ArrayList<String> expectedReactiveCaseCreationList = new ArrayList<String>(
				Arrays.asList((getTextLanguage(LanguageCode, "daas_ui", "com.hp.mpi.icm.type.activecare")),
						(getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties",
								"com.hp.mpi.icm.subtype.battery")),
						(getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties",
								"com.hp.mpi.icm.subtype.hdd")),
						(getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties",
								"com.hp.mpi.icm.subtype.motherboard")),
						(getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties",
								"com.hp.mpi.icm.subtype.iodevice")),
						(getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties",
								"com.hp.mpi.icm.subtype.power")),
						(getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties",
								"com.hp.mpi.icm.subtype.other"))));
		sa.assertTrue(settingsPage.verifyIncidentEligibilityTileTypesSubTypes(expectedPremierCareList,
				"premierCareTypesSubtypes"), "Error occured in validation of premier care .");
		sa.assertTrue(settingsPage.verifyIncidentEligibilityTileTypesSubTypes(expectedCaseCreationeList,
				"caseCreationTypesSubtypes"), "Error occured in validation of case creation.");
		sa.assertTrue(settingsPage.verifyIncidentEligibilityTileTypesSubTypes(expectedReactiveCaseCreationList,
				"reactiveCaseCreationTypesSubtypes"), "Error occured in validation of reactive case creation.");
		sa.assertAll();
		LOGGER.info("Validation of Incident Eligibility tile completed for App Admin.");
	}
	
	
	
	// this Test case is not required. It is already covered in other test cases.
	@Test(priority = 77, groups = {"ACTIVECARESANITY", "REGRESSIONSETTINGS2", "SMOKE", "REGRESSION_CI", "SMOKE_CI",
	"STABILIZATION_STAGING" }, description = "Test Case ID : 801448", enabled = false)
	public final void verifyAddOrdersTileInvalid() throws Exception {
		login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
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
				getEnvironmentSpecificData(System.getProperty("environment"), "ORDERS_INVALID"));
//		softAssert.assertTrue(
//				settingsPage.matchTextOfSettingsPage("successNotification",
//						getTextLanguage(LanguageCode, "daas_ui", "assets.import.asset.import_in_progress")),
//				"Toast notification did not appear");
		sleeper(60000);
		Assert.assertTrue(
				settingsPage.postNotificationCheckUnsuccessfulImportInV3(
						getEnvironmentSpecificData(System.getProperty("environment"), "ORDERS_INVALID")),
				"Message on notification window is incorrect");
		LOGGER.info("Notification message verification for import has failed");

		softAssert.assertAll();
	}
	
	// This is covered in verifyReactiveCaseCreationFunctionality()
	@Test(priority = 78, groups = { "REGRESSIONSETTINGS2", "REGRESSION_CI",
	"STABILIZATION_STAGING" }, description = "TEST CASE 757813", enabled=false)
	public final void verifyReactiveCaseCreation() throws Exception {
		login("IT_ADMIN_PC_USERNAME", "IT_ADMIN_PC_PASSWORD");
		waitForPageLoaded();
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
		HashMap<String, String> deviceEnrollmentDetails = new HashMap<String, String>();
		SoftAssert softAssert = new SoftAssert();
		EnrollFakeDevice.enrollFakeDeviceForIncident(
				getEnvironmentSpecificData(System.getProperty("environment"), "INCIDENT_PCCOMPANY"),
				getEnvironmentSpecificData(System.getProperty("environment"), "COMPANYPIN_REACTIVECASE"),
				getEnvironmentSpecificData(System.getProperty("environment"), "IT_ADMIN_PC_USERNAME"));
		String deviceName = deviceEnrollmentDetails.get("deviceName");
		gotoDashboardTab();
		waitForPageLoaded();
		settingsPage.createReactiveCase(deviceName);
		sleeper(3000);
		gotoIncidentTab();
		waitForPageLoaded();
		settingsPage.clickByJavaScriptOnSettingsPage("selectedIncidentTitle");
		LOGGER.info("Redirected to incident details page");
		sleeper(3000);
		settingsPage.waitForElementsOfSettingsPage("caseInformationTileTitle");
		settingsPage.scrollTillViewSettingsPage("caseInformationTileTitle");
		softAssert
		.assertTrue(
				settingsPage.getTextOfSettingsPage("caseInformationTileTitle")
				.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui",
						"incident.details.section.case.information").toUpperCase()),
				"Title on case information tile title is incorrect");
		softAssert.assertTrue(
				settingsPage.getTextOfSettingsPage("caseIDLabel")
				.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "incidents.caseID")),
				"Case ID label on case information tile title is incorrect");
		softAssert.assertTrue(
				settingsPage.getTextOfSettingsPage("caseStatusLabel")
				.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "incident.list.caseStatus")),
				"Case status label on case information tile title is incorrect");
		softAssert.assertTrue(
				settingsPage.getTextOfSettingsPage("caseSubmissionDate").equalsIgnoreCase(
						getTextLanguage(LanguageCode, "daas_ui", "incident.details.section.case.submission")),
				"Case submission value on case information tile title is incorrect");
		softAssert.assertTrue(
				settingsPage.getTextOfSettingsPage("caseLocation").equalsIgnoreCase(
						getTextLanguage(LanguageCode, "daas_ui", "incident.details.section.case.location")),
				"Case location label on case information tile title is incorrect");
		softAssert.assertTrue(
				settingsPage.getTextOfSettingsPage("caseContact").equalsIgnoreCase(
						getTextLanguage(LanguageCode, "daas_ui", "incident.details.section.case.contact")),
				"Case contact label on case information tile title is incorrect");
		softAssert.assertAll();
		LOGGER.info("Reactive case creation functionality verified successfully");
	}

	
	/**
	 * Verifying Notification tab Self Service Alerts & Revert to default functionality
	 * @throws Exception
	 */
	@Test(priority = 79, groups = { "REGRESSIONSETTINGS2", "REGRESSION_CI",
			"STABILIZATION_STAGING" }, description = "TEST CASE - 420406")
	public final void verifyNotificationsTab() throws Exception {
		
		List<String> expectedListWithEnabledStatus = new ArrayList<String>(Arrays.asList(
				getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties",
						"com.hp.mpi.icm.subtype.systemthermal"),
				getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties",
						"com.hp.mpi.icm.subtype.hddpredictive"),
				getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties",
						"com.hp.mpi.icm.subtype.biosoutofdate"),
				getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties",
						"com.hp.mpi.icm.subtype.batteryneedsattention")));
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		

		login("IT_ADMIN_PC_USERNAME", "IT_ADMIN_PC_PASSWORD");
		waitForPageLoaded();
		gotoNonMSPSettingsTab();
		waitForPageLoaded();
		settingsPage.waitForElementsOfSettingsPage("notificationsTab");
		settingsPage.clickOnElementsOfSettingsPage("notificationsTab");
		waitForPageLoaded();
		settingsPage.waitForElementsOfSettingsPage("resetToDefaultAlerts");

		softAssert.assertTrue(
				settingsPage.getTextOfSettingsPage("deviceNotificationsHeader").equalsIgnoreCase(
						getTextLanguage(LanguageCode, "daas_ui", "companies.details.tile.notifications")),
				"Header on device notifications tile is incorrect");
		softAssert.assertTrue(
				settingsPage.getTextOfSettingsPage("deviceNotificationsHeaderWarning").equalsIgnoreCase(
						getTextLanguage(LanguageCode, "daas_ui", "companies.details.tile.notifications.warning")
								.replace("{applicationName}", "HP TechPulse")),
				"Warning on device notifications tile is incorrect");

		settingsPage.clickOnElementsOfSettingsPage("settingspagecollapseicon");
		softAssert.assertTrue(
				settingsPage.getTextOfSettingsPage("allowServiceLocationLabel").equalsIgnoreCase(
						getTextLanguage(LanguageCode, "daas_ui", "companies.details.allow.service.location")),
				"Title on allow service location tile is incorrect");
		softAssert.assertTrue(
				settingsPage.getTextOfSettingsPage("selfServiceAlertsLabel").equalsIgnoreCase(
						getTextLanguage(LanguageCode, "daas_ui", "companies.details.self.service.alerts")),
				"Title on self service alerts tile is incorrect");

		sleeper(3000);
		// Enable the allow service location toggle if disabled
		if (settingsPage.verifyElementsOfSettingsPage("allowServiceLocationToggle")) {
			settingsPage.clickOnElementsOfSettingsPage("allowServiceLocationToggle");
		}
		// Enable the self service alerts toggle if disabled
		if (settingsPage.verifyElementsOfSettingsPage("selfServiceAlertsToggle")) {
			settingsPage.clickOnElementsOfSettingsPage("selfServiceAlertsToggle");
		}

		settingsPage.waitForElementsOfSettingsPage("resetToDefaultAlerts");
		
		
		// Change status of a Activecare Other subtype to Enable
		settingsPage.changeStatusOfActiveCareOtherSubtype();
		
		expectedListWithEnabledStatus.add(getTextLanguage(LanguageCode,
				"MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.subtype.other"));

		boolean flag = settingsPage.compareListOfSettingsPage(
				getTextLanguage(LanguageCode, "daas_ui", "global.enabled"),
				getTextLanguage(LanguageCode, "daas_ui", "global.disabled"), expectedListWithEnabledStatus);
		softAssert.assertTrue(flag, "Change did not reflect correctly");

		settingsPage.revertSelfServiceAlertsStatusToDefault();
		
		expectedListWithEnabledStatus.remove(getTextLanguage(LanguageCode,
				"MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.subtype.other"));

		flag = settingsPage.compareListOfSettingsPage(getTextLanguage(LanguageCode, "daas_ui", "global.enabled"),
				getTextLanguage(LanguageCode, "daas_ui", "global.disabled"), expectedListWithEnabledStatus);
		softAssert.assertTrue(flag, "Values did not reset to default after clicking on Revert to default button");
		softAssert.assertAll();
		LOGGER.info("Notification tab revert to default functionality verified successfully");
	}
	
	
	// This is covered in verifyCSVUploadNewACTenant()
	@Test(priority = 80, groups = { "SUBODH2","REGRESSIONSETTINGS2", "SMOKE", "REGRESSION_CI", "SMOKE_CI",
			"STABILIZATION_STAGING" }, description = "Test Case ID : 1019549", enabled=false)
	public final void verifyAddOrdersTileWithAddToCustomerTenantColumn(	) throws Exception {
		login("ROOT_ADMIN_NEW_USER_US", "ROOT_ADMIN_NEW_PASSWORD_US");
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
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
				getEnvironmentSpecificData(System.getProperty("environment"), "ORDERS_ADDTOCUSTOMERTENANT_COLUMN"));
		
		softAssert.assertTrue(
				settingsPage.matchTextOfSettingsPage("successNotification",
						getTextLanguage(LanguageCode, "daas_ui", "assets.import.asset.import_in_progress")),
				"Toast notification did not appear");
		sleeper(20000);
		Assert.assertTrue(
				settingsPage.postNotificationCheckImportInV3(getEnvironmentSpecificData(
						System.getProperty("environment"), "ORDERS_ADDTOCUSTOMERTENANT_COLUMN")),
				"Message on notification window is incorrect");
		LOGGER.info("Notification message verification for import has passed");

		softAssert.assertAll();
	}	
	
	
	@Test(priority = 81, groups = {"ACTIVECARESANITY", "REGRESSIONSETTINGS2", "SMOKE", "REGRESSION_CI", "SMOKE_CI", "STABILIZATION_STAGING"}, description = "Test Case ID : 1019650")
	public final void verifyCSVUploadFailureRegionSpecific() throws Exception {
		login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
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
		settingsPage.verifyImportOrders(LanguageCode, getEnvironmentSpecificData(System.getProperty("environment"), "ORDERS_INVALID_REGION"));
		sleeper(20000);
		Assert.assertTrue(
				settingsPage.postNotificationCheckUnsuccessfulImportInV3(
						getEnvironmentSpecificData(System.getProperty("environment"), "ORDERS_INVALID_REGION")),
				"Message on notification window is incorrect");
		
		LOGGER.info("Notification message verification for import has passed");
		
		softAssert.assertAll();
	}
	
	


	/**
	 *
	 * Feature 862345: [Core] [WorkF] Create SSID/IP table for "Office" locations
	 * This test case verify  SSID/IP table for "Office" locations
	 * @throws Exception
	 */
	//Will enable testcase once code gets deployed on staging
	@Test(priority =80, groups = { "REGRESSION", "REGRESSION_CI", "ALL_CI", "ALL", "STABILIZATION_STAGING","REPORTS1" }, description = "Test Case ID : 882554",enabled = false)

	public final void verifySIDIPTableForOfficeLocations() throws Exception {
		login("ITADMIN_DEVICE_PROTECTTRACE_STATUS_EMAIL", "ITADMIN_DEVICE_PROTECTTRACE_STATUS_PASSWORD");
		String SSIDtext = generateRandomString(4);
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		waitForPageLoaded();
		gotoCompanySettingsTab();
		LOGGER.info("Navigated to Setting page");
		waitForPageLoaded();

		settingsPage.clickOnElementsOfSettingsPage("preferenceTab");
		waitForPageLoaded();
		settingsPage.clickByJavaScriptOnSettingsPage("CompanyNetwork");

		//Verify Panel Warning
		softAssert.assertTrue(settingsPage.getTextOfSettingsPage("CompanyNetworkPanelWarning").equals(settingsPage.getTextLanguage(LanguageCode,"daas_ui","companies.details.section.companynetworks.desc")));
		LOGGER.info("Panel warning text verified successfully");

		//Verify SSID and IP Address Label
		softAssert.assertTrue(settingsPage.verifyElementsOfSettingsPage("SSIDLabelText"),"SSID Label not matching");
		softAssert.assertTrue(settingsPage.verifyElementsOfSettingsPage("IPAddressRangeLabelText"),"IP address label not matching");

		//Click on Edit button
		settingsPage.clickOnElementsOfSettingsPage("CompanyNetworkEditButton");
		settingsPage.waitForElementsOfSettingsPage("SSIDHelperText");

		//Verify Helper text
		softAssert.assertTrue(settingsPage.getTextOfSettingsPage("SSIDHelperText").equals(settingsPage.getTextLanguage(LanguageCode,"daas_ui","companies.details.section.companynetworks.field.corporateWifiName")));
		softAssert.assertTrue(settingsPage.getTextOfSettingsPage("IPAddressHelperText").equals(settingsPage.getTextLanguage(LanguageCode,"daas_ui","companies.details.section.companynetworks.field.info").replace("{exampleText}","1.1.1.1; 1.0.0.0/9")),"IPADDress label is matchmatching");
		LOGGER.info("Helper text verified successfully");

		//Verify Notification text
		softAssert.assertTrue(settingsPage.getTextOfSettingsPage("EditBoxNotificationText").equals(settingsPage.getTextLanguage(LanguageCode,"daas_ui","companies.details.section.companynetworks.confirmed")),"Notification text is mismatching");
		LOGGER.info(" notification text verified successfully");

		//Enter SSID and IP Range
		settingsPage.clearTextOnSettingsPage("SSIDInput");
		settingsPage.enterTextForSettingsPage("SSIDInput",SSIDtext);
		settingsPage.clearTextOnSettingsPage("IpRangeInput");
		settingsPage.enterTextForSettingsPage("IpRangeInput","1.1.1.1;1.0.0.0/9");

		//Verify Save and Cancel Button
		softAssert.assertTrue(settingsPage.verifyElementsOfSettingsPage("CompanyNtwkbtnCancel"));
		settingsPage.clickOnElementsOfSettingsPage("CompanyNtwkbtnSave");

		//Verify Toast notification
		softAssert.assertTrue(settingsPage.matchTextOfSettingsPage("successNotification", settingsPage.getTextLanguage(LanguageCode,"daas_ui","companies.details.update.success").replace("{name}",settingsPage.getTextLanguage(LanguageCode,"daas_ui","companies.details.section.companynetworks.title"))),"Toast notification not updated");
		LOGGER.info("Toast notification verified successfully");

		softAssert.assertTrue(settingsPage.matchTextOfSettingsPage("SSIDValue",SSIDtext),"SSID value not updated");
		softAssert.assertTrue(settingsPage.matchTextOfSettingsPage("IPAddressValue","1.1.1.1;1.0.0.0/9"),"Ip Address value not updated");
		LOGGER.info(" SSID and Ip Range verified successfully");

		softAssert.assertAll();
	}
	
	// This test case is covered in verifyDeviceUploadToACHT
	@Test(priority = 83, groups = { "REGRESSIONSETTINGS2", "SMOKE", "REGRESSION_CI", "SMOKE_CI",
			"STABILIZATION_STAGING" }, description = "Test Case ID : 1035690", enabled=false)
	public final void verifyAddOrdersWithMandatoryColumnsOnlyHoldingTank() throws Exception {
		login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
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
				getEnvironmentSpecificData(System.getProperty("environment"), "ORDERS_MANDATORY_HT"));

		softAssert.assertTrue(
				settingsPage.matchTextOfSettingsPage("successNotification",
						getTextLanguage(LanguageCode, "daas_ui", "assets.import.asset.import_in_progress")),
				"Toast notification did not appear");
		sleeper(20000);
		Assert.assertTrue(
				settingsPage.postNotificationCheckImportInV3(
						getEnvironmentSpecificData(System.getProperty("environment"), "ORDERS_MANDATORY_HT")),
				"Message on notification window is incorrect");
		LOGGER.info("Notification message verification for import has passed");

		softAssert.assertAll();
	}
	
	
	
	
	

	@Test(priority = 84, groups = { "REGRESSIONSETTINGS2", "SMOKE", "REGRESSION_CI", "SMOKE_CI",
			"STABILIZATION_STAGING" }, description = "Test Case ID : 1019650", enabled=false)
	public final void verifyCSVUploadWithInvalidMandatoryFields() throws Exception {
		login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
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
				getEnvironmentSpecificData(System.getProperty("environment"), "ORDERS_MANDATORY_FIELDS_INVALID"));
		softAssert.assertTrue(
				settingsPage.matchTextOfSettingsPage("successNotification",
						getTextLanguage(LanguageCode, "daas_ui", "assets.import.asset.import_in_progress")),
				"Toast notification did not appear");
		sleeper(60000);

		Assert.assertTrue(
				settingsPage.postNotificationCheckUnsuccessfulImportInV3(getEnvironmentSpecificData(
						System.getProperty("environment"), "ORDERS_MANDATORY_FIELDS_INVALID")),
				"Message on notification window is incorrect");

		LOGGER.info("Notification message verification for import has passed");

		softAssert.assertAll();
	}

	/*
	 * This test case validates Service Request functionality on Help & Support
	 * page.
	 */
	@Test(priority = 85, groups = { "REGRESSIONSETTINGS2", "SMOKE", "REGRESSION_CI", "SMOKE_CI",
			"STABILIZATION_STAGING" }, description = "Test Case ID : 1035075")
	public final void verifyServiceRequestForContractualTile() throws Exception {
		SoftAssert sa = new SoftAssert();
		login("IT_ADMIN_CONTRACTUAL", "IT_ADMIN_CONTRACTUAL_PASSWORD");
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
		gotoHelpAndSupportTab();
		if (toggleVerification(SettingsVariables.CONTRACTUAL_HELPANDSUPPORT__TOGGLE,
				getCredentials(environment, "IT_ADMIN_CONTRACTUAL"))) {
			HashMap<String, String> serviceRequestInfoHnS = settingsPage.getServiceRequestHelpAndSupport();
			settingsPage.clickOnElementsOfSettingsPage("hpAssistanceTab");
			settingsPage.waitForElementsOfSettingsPage("partnerAssistancetitle");
			sa.assertTrue(settingsPage.verifyHelpAndSupportTile(serviceRequestInfoHnS, LanguageCode, "serviceRequest"),
					"Service Request tile validations failed");
			sa.assertAll();
			LOGGER.info("Validation of Service Request tile for Help & Support completed.");
		} else {
			LOGGER.info("Feature Toggle is off");
		}
	}

	/*
	 * This test case verifies Service Request tile components on Help and Support
	 * page for IT_ADMIN_CONTRACTUAL
	 */
	@Test(priority = 86, groups = { "REGRESSIONSETTINGS2", "SMOKE", "REGRESSION_CI", "SMOKE_CI",
			"STABILIZATION_STAGING" }, description = "Test Case ID : 1035075")
	public final void verifyServiceRequestForContractual() throws Exception {
		login("IT_ADMIN_CONTRACTUAL", "IT_ADMIN_CONTRACTUAL_PASSWORD");
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
		gotoHelpAndSupportTab();
		LOGGER.info("Redirected to Help and Support tab");
		waitForPageLoaded();
		if (toggleVerification(SettingsVariables.CONTRACTUAL_HELPANDSUPPORT__TOGGLE,
				getCredentials(environment, "IT_ADMIN_CONTRACTUAL"))) {
			settingsPage.clickOnElementsOfSettingsPage("hpAssistanceTab");
			settingsPage.waitForElementsOfSettingsPage("partnerAssistancetitle");
			HashMap<String, String> serviceRequestInfoHnS = settingsPage.getServiceRequestHelpAndSupport();
			settingsPage.verifyHelpAndSupportComponents(serviceRequestInfoHnS, LanguageCode, "serviceRequest",
					"service_request", "get-support");
			LOGGER.info("Validation of Service Request tile for Help & Support completed.");
		} else {
			LOGGER.info("Feature Toggle is off");
		}
	}

	/*
	 * This test case validates Service Request functionality on Help & Support
	 * page.
	 */
	@Test(priority = 87, groups = { "REGRESSIONSETTINGS2", "SMOKE", "REGRESSION_CI", "SMOKE_CI",
			"STABILIZATION_STAGING" }, description = "Test Case ID : 1035075")
	public final void verifyTrackRequestsForContractualTile() throws Exception {
		SoftAssert sa = new SoftAssert();
		login("IT_ADMIN_CONTRACTUAL", "IT_ADMIN_CONTRACTUAL_PASSWORD");
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
		gotoHelpAndSupportTab();
		if (toggleVerification(SettingsVariables.CONTRACTUAL_HELPANDSUPPORT__TOGGLE,
				getCredentials(environment, "IT_ADMIN_CONTRACTUAL"))) {
			HashMap<String, String> trackRequestsInfoHnS = settingsPage.getTrackRequestsHelpAndSupport();
			settingsPage.clickOnElementsOfSettingsPage("hpAssistanceTab");
			settingsPage.waitForElementsOfSettingsPage("partnerAssistancetitle");
			sa.assertTrue(settingsPage.verifyHelpAndSupportTile(trackRequestsInfoHnS, LanguageCode, "trackRequests"),
					"Track Request tile validations failed");
			sa.assertAll();
			LOGGER.info("Validation of Track Request tile for Help & Support completed.");
		} else {
			LOGGER.info("Feature Toggle is off");
		}
	}

	/*
	 * This test case verifies Service Request tile components on Help and Support
	 * page for IT_ADMIN_CONTRACTUAL
	 */
	@Test(priority = 88, groups = { "REGRESSIONSETTINGS2", "SMOKE", "REGRESSION_CI", "SMOKE_CI",
			"STABILIZATION_STAGING" }, description = "Test Case ID : 1035075")
	public final void verifytrackRequestsForContractual() throws Exception {
		login("IT_ADMIN_CONTRACTUAL", "IT_ADMIN_CONTRACTUAL_PASSWORD");
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
		gotoHelpAndSupportTab();
		if (toggleVerification(SettingsVariables.CONTRACTUAL_HELPANDSUPPORT__TOGGLE,
				getCredentials(environment, "IT_ADMIN_CONTRACTUAL"))) {
			LOGGER.info("Redirected to Help and Support tab");
			waitForPageLoaded();
			settingsPage.clickOnElementsOfSettingsPage("hpAssistanceTab");
			settingsPage.waitForElementsOfSettingsPage("partnerAssistancetitle");
			HashMap<String, String> trackRequestsInfoHnS = settingsPage.getTrackRequestsHelpAndSupport();
			settingsPage.verifyHelpAndSupportComponents(trackRequestsInfoHnS, LanguageCode, "trackRequests",
					"track_requests", "support-tickets");
		} else {
			LOGGER.info("Feature Toggle is off");
		}
	}
	
	
	@Test(priority = 89, groups = { "REGRESSIONSETTINGS2", "REGRESSION_CI",
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

	
	@Test(priority = 90, groups = {"ACTIVECARESANITY", "REGRESSIONSETTINGS2", "SMOKE", "REGRESSION_CI",
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

//		devicelistPage.enterTextForDeviceListPage("serialNumberSearchBox",
//				getEnvironmentSpecificData(System.getProperty("environment"), "DEVICE_SERIAL_NO"));
		devicelistPage.waitForElementsOfDeviceListPage("serialNumberList");

		devicelistPage.clickByJavaScriptOnDeviceListPage("firstDeviceInList");
		deviceDetailPage.waitForElementsOfDeviceDetailsPage("deviceDetailsTitle");
		sleeper(3000);

		LOGGER.info("Redirected to Device details page");

		softAssert.assertFalse(deviceDetailPage.verifyElementsOfDeviceDetailsPage("deviceDetailsCreateCaseButton"),
				"Case Create button is displayed on Device Details page");
		softAssert.assertAll();

	}
	
	
	
	@Test(priority = 91, groups = {"ACTIVECARESANITY", "REGRESSIONSETTINGS2", "SMOKE", "REGRESSION_CI",
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
//		softAssert.assertTrue(incidentPage.verifyElementPresentOnIncidentListPage("createCaseInvalidTypeMessageLabel"),
//				"Message not displayed");
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
//		softAssert.assertTrue(
//				incidentPage.verifyElementPresentOnIncidentListPage("createCaseInvalidMultipleDeviceLabel"),
//				"Message not displayed");
		softAssert.assertTrue(
				incidentPage.matchTextOfIncidentPage("createCaseInvalidMultipleDeviceLabel", "Only one case can be created at a time"),
				"Message not displayed");

		LOGGER.info("Verification completed for Case creation not allowed for multiple incidents at same time");

		// Test Data is not available for below verification hence commented out the code.
		/* Verification of non hp devices */

//		incidentPage.clickByJavaScriptOnIncidentPage("clearSelection");
//		incidentPage.enterTextForIncidentPage("idSearchBox",getEnvironmentSpecificData(System.getProperty("environment"),"INCIDENT_ID_NON_HP_DEVICE"));
//		incidentPage.waitForElementsOfIncidentPage("clickIncidentCheckbox");
//		sleeper(1000);
//		incidentPage.clickByJavaScriptOnIncidentPage("clickIncidentCheckbox");
//		incidentPage.clickByJavaScriptOnIncidentPage("sideBarCloseButton");
//		sleeper(1000);
//		incidentPage.clickByJavaScriptOnIncidentPage("createCaseDisableButton");
//		incidentPage.mousehoverOnIncientPage("createCaseDisableButton");
//		//sleeper(1000);
//		softAssert.assertTrue(
//				incidentPage.verifyElementPresentOnIncidentListPage("createCaseInvaliddeviceManufacturerLabel"),
//				"Message not displayed");
//		LOGGER.info("Verification completed for Case creation not allowed for non HP devices");
		softAssert.assertAll();

	}
	
	
	
	
	@Test(priority = 92, groups = {"REGRESSIONSETTINGS2", "SMOKE", "REGRESSION_CI",
	"SMOKE_CI" }, description = "Test Case:1062513 , 1062646 , 1062508", enabled = false)
	public final void verifyCaseCreationIsNotAllowedForPEMCombinationPlanTenant() throws Exception {
       
		
		/*****PEM PLAN TENANT******/
		login("AUTOMATION_PEM_EMAIL", "AUTOMATION_PEM_PASSWORD");
		waitForPageLoaded();
		IncidentPage incidentPage = new IncidentPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		
		gotoIncidentTab();
		
		softAssert.assertFalse(incidentPage.verifyElementPresentOnIncidentListPage("createCaseButton"),
				"Case Create button is displayed on Incident Lists page");
		
		waitForPageLoaded();
		softAssert.assertFalse(incidentPage.verifyCreateCaseButtonNotDisplayedOnIncidentListPage(),
				"Case Create button is not displayed on Incident Lists page");		
		incidentPage.clickByJavaScriptOnIncidentPage("firstIncidentLinkInTable");
		
		softAssert.assertFalse(incidentPage.verifyElementPresentOnIncidentListPage("createCaseButton"),
				"Case Create button is displayed on Incident page");
		logout();
		
		/*****PI + PEM PLAN TENANT******/
		login("AUTOMATION_PI_PEM_EMAIL", "AUTOMATION_PI_PEM_PASSWORD");
		waitForPageLoaded();
		
		gotoIncidentTab();
		
		softAssert.assertFalse(incidentPage.verifyElementPresentOnIncidentListPage("createCaseButton"),
				"Case Create button is displayed on Incident Lists page");
		
		waitForPageLoaded();
		softAssert.assertFalse(incidentPage.verifyCreateCaseButtonNotDisplayedOnIncidentListPage(),
				"Case Create button is not displayed on Incident Lists page");		
		incidentPage.clickByJavaScriptOnIncidentPage("firstIncidentLinkInTable");
		
		softAssert.assertFalse(incidentPage.verifyElementPresentOnIncidentListPage("createCaseButton"),
				"Case Create button is displayed on Incident page");
		logout();

		
		/*****PEM + AC PLAN DEVICE******/
		login("AUTOMATION_PEM_AC_EMAIL", "AUTOMATION_PEM_AC_PASSWORD");
		waitForPageLoaded();
		
		gotoIncidentTab();
		
		softAssert.assertFalse(incidentPage.verifyElementPresentOnIncidentListPage("createCaseButton"),
				"Case Create button is displayed on Incident Lists page");
		
		waitForPageLoaded();
		softAssert.assertFalse(incidentPage.verifyCreateCaseButtonNotDisplayedOnIncidentListPage(),
				"Case Create button is not displayed on Incident Lists page");		
		incidentPage.clickByJavaScriptOnIncidentPage("firstIncidentLinkInTable");
		
		softAssert.assertFalse(incidentPage.verifyElementPresentOnIncidentListPage("createCaseButton"),
				"Case Create button is displayed on Incident page");
		
		logout();

		/*****PEM + P&T PLAN TENANT******/
		login("AUTOMATION_PEM_WPT_EMAIL", "AUTOMATION_PEM_WPT_PASSWORD");
		waitForPageLoaded();
		
		gotoIncidentTab();
		
		softAssert.assertFalse(incidentPage.verifyElementPresentOnIncidentListPage("createCaseButton"),
				"Case Create button is displayed on Incident Lists page");
		
		waitForPageLoaded();
		softAssert.assertFalse(incidentPage.verifyCreateCaseButtonNotDisplayedOnIncidentListPage(),
				"Case Create button is not displayed on Incident Lists page");
		
		incidentPage.clickByJavaScriptOnIncidentPage("firstIncidentLinkInTable");
		
		softAssert.assertFalse(incidentPage.verifyElementPresentOnIncidentListPage("createCaseButton"),
				"Case Create button is displayed on Incident page");
		
		softAssert.assertAll();

	}
	
	
	@Test(priority = 93, groups = { "REGRESSIONSETTINGS2", "SMOKE", "REGRESSION_CI",
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

		incidentPage.clickByJavaScriptOnIncidentPage("sideBarCloseButton");

		sleeper(1000);
		incidentPage.clickByJavaScriptOnIncidentPage("createCaseDisableButton");
		incidentPage.mousehoverOnIncientPage("createCaseDisableButton");
		softAssert.assertTrue(incidentPage.verifyElementPresentOnIncidentListPage("createCaseAlreadyExistTooltip"),
				"Message not displayed");
		LOGGER.info("Verification completed for Case creation not allowed for incidents having prexisting case");
		softAssert.assertAll();

	}
	

	
	/**
	 * 
	 * @throws Exception
	 */
	@Test(priority = 94, groups = { "REGRESSIONSETTINGS2", "SMOKE", "REGRESSION_CI",
			"SMOKE_CI" }, description = "Test Case:1076154,1076381", enabled = false)
	public final void verifyDoubleNotificationOnUploadingEnrolledDevice() throws Exception {
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
		DeviceListPage devicelistPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
		DeviceDetailsPage deviceDetailPage = new DeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();

		SoftAssert softAssert = new SoftAssert();

		login("ROOT_ADMIN_USER", "ROOT_ADMIN_PASSWORD");
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
				getEnvironmentSpecificData(System.getProperty("environment"), "EXISTING_DEVICE"));

		softAssert.assertTrue(
				settingsPage.matchTextOfSettingsPage("successNotification",
						getTextLanguage(LanguageCode, "daas_ui", "assets.import.asset.import_in_progress")),
				"Toast notification did not appear");
		sleeper(60000);
		boolean isPresent = settingsPage.postNotificationCheckUnsuccessfulImportInV3Loop(
				getEnvironmentSpecificData(System.getProperty("environment"), "EXISTING_DEVICE"),
				"notificationEllipseButton", "notificationDuplicateErrorButton");
		Assert.assertTrue(isPresent, "Message on notification window is incorrect");
		LOGGER.info("Notification message verification for import has passed");

		logout();
		login("ACTIVECARETENANT_EMAIL", "ACTIVECARETENANT_PASSWORD");
		waitForPageLoaded();

		gotoDevicesTab();
		waitForPageLoaded();

//		devicelistPage.enterTextForDeviceListPage("serialNumberSearchBox",
//				settingsPage.getCSVFieldValue("ObjectOfServiceSerialNumber", "EXISTING_DEVICE"));
		devicelistPage.waitForElementsOfDeviceListPage("serialNumberList");
		sleeper(3000);

		devicelistPage.clickByJavaScriptOnDeviceListPage("firstDeviceInList");
		deviceDetailPage.waitForElementsOfDeviceDetailsPage("deviceDetailsTitle");
		sleeper(3000);

		LOGGER.info("Redirected to Device details page");
		deviceDetailPage.clickOnElementsOfDeviceDetailsPage("deviecDetailsCollapse");
		deviceDetailPage.clickOnElementsOfDeviceDetailsPage("deviceDetailsEnrollment");
		String startDate = deviceDetailPage.getTextOfDeviceDetailsPage("deviecDetailsPlanStartDate");
		String expiryDate = deviceDetailPage.getTextOfDeviceDetailsPage("deviecDetailsPlanExpiryDate");
		Map<String, String> map = settingsPage.getExpectedStartDateExpiryDate("EXISTING_DEVICE");
		softAssert.assertTrue(startDate.contains(map.get("expectedStartDate")));
		softAssert.assertTrue(expiryDate.contains(map.get("expectedExpiryDate")));

		softAssert.assertAll();

	}
	
	// Disabled this test case. Need to check
	@Test(priority = 95, groups = { "REGRESSIONSETTINGS2", "SMOKE", "REGRESSION_CI",
			"SMOKE_CI" }, description = "Test Case:1076212,1076215", enabled = false)
	public final void verifyStartDateEndADteUpdatedForPendingEnrollmentDevice() throws Exception {

		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
		DeviceListPage devicelistPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
		DeviceDetailsPage deviceDetailPage = new DeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();

		SoftAssert softAssert = new SoftAssert();

		login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
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
				getEnvironmentSpecificData(System.getProperty("environment"), "PENDING_ENROLL_EXISTING_DEVICE"));

		softAssert.assertTrue(
				settingsPage.matchTextOfSettingsPage("successNotification",
						getTextLanguage(LanguageCode, "daas_ui", "assets.import.asset.import_in_progress")),
				"Toast notification did not appear");
		sleeper(60000);
		boolean isPresent = settingsPage.postNotificationCheckUnsuccessfulImportInV3Loop(
				getEnvironmentSpecificData(System.getProperty("environment"), "PENDING_ENROLL_EXISTING_DEVICE"),
				"notificationEllipseButton", "notificationDuplicateErrorButton");
		softAssert.assertTrue(isPresent, "Message on notification window is incorrect");
		LOGGER.info("Notification message verification for import has passed");

		logout();
		login("AUTOMATION_EMAIL_CASE", "AUTOMATION_PASSWORD_CASE");
		waitForPageLoaded();

		gotoDevicesTab();
		waitForPageLoaded();
		devicelistPage.clickOnElementsOfDeviceListPage("pendingEnrollmentTab");

//		devicelistPage.enterTextForDeviceListPage("serialNumberSearchBox",
//				settingsPage.getCSVFieldValue("ObjectOfServiceSerialNumber", "PENDING_ENROLL_EXISTING_DEVICE"));
		devicelistPage.waitForElementsOfDeviceListPage("serialNumberList");
		sleeper(3000);

		devicelistPage.clickByJavaScriptOnDeviceListPage("firstDeviceInList");
		deviceDetailPage.waitForElementsOfDeviceDetailsPage("deviceDetailsTitle");
		sleeper(3000);

		LOGGER.info("Redirected to Device details page");
		deviceDetailPage.clickOnElementsOfDeviceDetailsPage("deviecDetailsCollapse");
		deviceDetailPage.clickOnElementsOfDeviceDetailsPage("deviceDetailsEnrollment");
		String startDate = deviceDetailPage.getTextOfDeviceDetailsPage("deviecDetailsPlanStartDate");
		String expiryDate = deviceDetailPage.getTextOfDeviceDetailsPage("deviecDetailsPlanExpiryDate");
		Map<String, String> map = settingsPage.getExpectedStartDateExpiryDate("PENDING_ENROLL_EXISTING_DEVICE");
		softAssert.assertTrue(startDate.contains(map.get("expectedStartDate")));
		softAssert.assertTrue(expiryDate.contains(map.get("expectedExpiryDate")));

		softAssert.assertAll();

	}
	
@Test(priority = 96, groups = { "REGRESSIONSETTINGS2", "SMOKE", "REGRESSION_CI", "SMOKE_CI"}, enabled = false)
	public final void verifyCustomFieldsOnSettingsPage() throws Exception
	{
		login("MSP_ADMIN_US_EMAIl_REPORTS", "MSP_ADMIN_US_PASSWORD_REPORTS");
		
		SoftAssert softAssert = new SoftAssert();
		String CUSTOM_FIELD_ONE = generateRandomString(11);
		String CUSTOM_FIELD_TWO = generateRandomString(11);
		String CUSTOM_FIELD_THREE = generateRandomString(11);
		String CUSTOM_FIELD_FOUR = generateRandomString(11);
		String CUSTOM_FIELD_FIVE = generateRandomString(11);
		customFields.add(CUSTOM_FIELD_TWO.toLowerCase());
		customFields.add(CUSTOM_FIELD_THREE.toLowerCase());
		customFields.add(CUSTOM_FIELD_FOUR.toLowerCase());
		customFields.add(CUSTOM_FIELD_FIVE.toLowerCase());
		customFields.add(CUSTOM_FIELD_ONE.toLowerCase());
		
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
		//CompaniesDetailsPage companyDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver()).getInstance();
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();

		gotoSettingsTab();
		LOGGER.info("Redirected to settings page");
		waitForPageLoaded();
		settingsPage.waitForElementsOfSettingsPage("tableOverlay");
		softAssert.assertTrue(settingsPage.verifyElementsOfSettingsPage("preferenceTabSettingsPage"), "Preferences tile on settings page is not loaded successfully");
			settingsPage.clickOnElementsOfSettingsPage("preferenceTabSettingsPage");
		LOGGER.info("Navigated to preferences tab");
		softAssert.assertTrue(settingsPage.getTextOfSettingsPage("customFieldsTitle").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.custom_fields.title")), "Title on custom fields tile is incorrect : TEST CASE 1106661");
		softAssert.assertTrue(settingsPage.getTextOfSettingsPage("customFieldsLabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.custom_fields.label")), "Label on custom fields tile is incorrect : TEST CASE 1106661");
			// Removing already added custom fields
				if (!settingsPage.getTextOfSettingsPage("customFieldsValue").equalsIgnoreCase(SettingsVariables.CUSTOM_FIELDS_NOT_CONFIGURED)) {
					settingsPage.clickOnElementsOfSettingsPage("customFieldsEditButton");
					List<WebElement> uiList = settingsPage.getElementsOfSettingsPage("customFieldsAllDeleteIcons");
					settingsPage.clickElementsOfSettingsPage(uiList);
					sleeper(2000);
					settingsPage.clickOnElementsOfSettingsPage("customFieldsSave");
					settingsPage.waitForElementsOfSettingsPage("toastNotification");
					Assert.assertTrue(settingsPage.verifyElementsOfSettingsPage("toastNotification"), "Already existing custom fields were not removed successfully, cannot proceed further : TEST CASE 1106661");
					LOGGER.info("Already existing custom fields removed successfully, test case started");
				} else {
					LOGGER.info("No existing custom fields present");
				}
				
				// Test Case 1106661: [Insights][UI]Verify elements of Custom fields tile on Settings page
				// Verify cancel functionality on custom field popup
				settingsPage.waitForElementsOfSettingsPage("SettingstableOverlay");
				settingsPage.clickOnElementsOfSettingsPage("customFieldsEditButton");
				softAssert.assertTrue(settingsPage.getTextOfSettingsPage("customFieldsPopupTitle").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.custom_fields.label")), "Title on custom fields popup is incorrect : TEST CASE 1106661");
				softAssert.assertTrue(settingsPage.getTextOfSettingsPage("customFieldsPopupSubTitle").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.custom_fields.popup.subtitle")), "Subtitle on custom fields popup is incorrect : TEST CASE 1106661");
				settingsPage.enterTextForSettingsPage("firstTextBox", CUSTOM_FIELD_ONE);
				settingsPage.clickOnElementsOfSettingsPage("addMoreFieldsLink");
				settingsPage.enterTextForSettingsPage("secondTextBox", CUSTOM_FIELD_TWO);
				settingsPage.clickOnElementsOfSettingsPage("customFieldsCancel");
				LOGGER.info("Clicked on cancel button of custom fields popup");
				softAssert.assertTrue(settingsPage.matchTextOfSettingsPage("customFieldsValue", "Not Configured"), "Cancel functionality on custom fields popup is not working correctly : TEST CASE 1106661");
				
				settingsPage.addAllCustomFieldsSettingsPage("customFieldsEditButton", "addMoreFieldsLink", "customFieldsSave", "firstTextBox", "secondTextBox", "thirdTextBox", "fourthTextBox", "fifthTextBox", CUSTOM_FIELD_ONE, CUSTOM_FIELD_TWO, CUSTOM_FIELD_THREE, CUSTOM_FIELD_FOUR, CUSTOM_FIELD_FIVE);
				settingsPage.waitForElementsOfSettingsPage("toastNotification");
				softAssert.assertTrue(settingsPage.verifyElementsOfSettingsPage("toastNotification"), "Save functionality on custom fields popup is not working correctly : TEST CASE 1106661");
				
				// Test Case 1106661: [Insights][UI]Verify elements of Custom fields tile on Settings page
				// verify undo functionality on custom fields popup
				settingsPage.waitForElementsOfSettingsPage("tableOverlay");
				settingsPage.clickOnElementsOfSettingsPage("customFieldsEditButton");
				LOGGER.info("Clicked on edit icon of custom fields.");
				settingsPage.clickOnElementsOfSettingsPage("firstDeleteIcon");
				LOGGER.info("Clicked on first delete icon of custom fields popup");
				String deprecatedText = settingsPage.getAttributesOfSettingsPage("firstDeprecatedCustomField", "style");
				softAssert.assertTrue((deprecatedText.substring(deprecatedText.indexOf("border:"), deprecatedText.indexOf(";"))).contains("red"), "Color on deprecated text after removing custom field is not proper : TEST CASE 1106661");
				softAssert.assertTrue(settingsPage.getAttributesOfSettingsPage("firstDeprecatedCustomField", "class").contains("deletedCustomField"), "Removed custom field is not deprecated : TEST CASE 1106661");
				softAssert.assertTrue(settingsPage.verifyElementIsClickableOfSettingsPage("undoIcon"), "Undo icon is not enabled on custom field deletion : TEST CASE 1106661");
				softAssert.assertTrue(settingsPage.getTextOfSettingsPage("warningTextCustomFieldRemoval").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.custom_fields.popup.delete.warning")), "Incorrect warning message is displayed on custom field popup while deleting custom field : TEST CASE 1106661");
				settingsPage.clickOnElementsOfSettingsPage("undoIcon");
				LOGGER.info("Clicked on undo icon of custom fields popup");
				softAssert.assertTrue(settingsPage.verifyElementIsClickableOfSettingsPage("firstDeleteIcon"), "Delete button is not displayed after clicking on undo button : TEST CASE 1106661");
				settingsPage.clickOnElementsOfSettingsPage("firstDeleteIcon");
				LOGGER.info("Clicked on first delete icon of custom fields popup");
				softAssert.assertTrue(settingsPage.verifyElementIsEnableOfSettingsPage("addMoreFieldsLink"), "Add more fields link is not enabled when 5 fields are added and one of them is deleted : TEST CASE 1106661");
				settingsPage.clickOnElementsOfSettingsPage("addMoreFieldsLink");
				softAssert.assertFalse(settingsPage.verifyElementIsClickableOfSettingsPage("undoIcon"), "Undo icon is enabled after addition of 5 fields : TEST CASE 1106661");
				settingsPage.enterTextForSettingsPage("sixthTextBox", CUSTOM_FIELD_ONE);
				settingsPage.clickOnElementsOfSettingsPage("customFieldsCancel");
				LOGGER.info("Clicked on cancel button of custom fields popup");
				
				// Test Case 1106661: [Insights][UI]Verify elements of Custom fields tile on Settings page
				// Verify delete custom field functionality
				settingsPage.waitForElementsOfSettingsPage("tableOverlay");
				settingsPage.clickOnElementsOfSettingsPage("customFieldsEditButton");
				LOGGER.info("Clicked on edit icon of custom fields.");
				settingsPage.clickOnElementsOfSettingsPage("firstDeleteIcon");
				LOGGER.info("Clicked on first delete icon of custom fields popup");
				settingsPage.clickOnElementsOfSettingsPage("customFieldsSave");
				LOGGER.info("Clicked on save button of custom fields popup");
				settingsPage.waitForElementsOfSettingsPage("toastNotification");
				softAssert.assertFalse(settingsPage.getTextOfSettingsPage("customFieldsValue").contains(CUSTOM_FIELD_ONE), "Custom field deleted is not reflected on details page : TEST CASE 1106661");

				// Test Case 1106661: [Insights][UI]Verify elements of Custom fields tile on Settings page
				// Verify error field validation messages on custom field popup
				settingsPage.waitForElementsOfSettingsPage("tableOverlay");
				settingsPage.clickOnElementsOfSettingsPage("customFieldsEditButton");
				LOGGER.info("Clicked on edit icon of custom fields.");
				softAssert.assertFalse(settingsPage.getTextOfSettingsPage("firstTextBox").equals(CUSTOM_FIELD_ONE), "Custom field not deleted from popup : TEST CASE 1106661");
				settingsPage.clickOnElementsOfSettingsPage("addMoreFieldsLink");
				settingsPage.enterTextForSettingsPage("fifthTextBox", CUSTOM_FIELD_TWO);
				softAssert.assertTrue(settingsPage.getTextOfSettingsPage("errorValidationMessage").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.custom_fields.error.1003")), "Duplicate custom field name error validation message is incorrect : TEST CASE 1106661");
				settingsPage.clearTextRefreshFromsettingPageTextField("fifthTextBox");
				settingsPage.clickOnElementsOfSettingsPage("customFieldsSave");
				softAssert.assertTrue(settingsPage.getTextOfSettingsPage("errorValidationMessage").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.custom_fields.error.1001")), "Blank custom field name error validation message is incorrect : TEST CASE 1106661");
				settingsPage.enterTextForSettingsPage("fifthTextBox", SettingsVariables.CUSTOM_FIELD_SIX);
				settingsPage.clickOnElementsOfSettingsPage("customFieldsSave");
				softAssert.assertTrue(settingsPage.getTextOfSettingsPage("errorValidationMessage").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.custom_fields.error.1004")), "Custom field name containing special characters error validation message is incorrect : TEST CASE 1106661");
				settingsPage.enterTextForSettingsPage("fifthTextBox", CUSTOM_FIELD_ONE);
				settingsPage.clickOnElementsOfSettingsPage("customFieldsSave");
				LOGGER.info("Clicked on save button of custom fields popup");
				settingsPage.waitForElementsOfSettingsPage("tableOverlay");
				
				// Verify added custom fields on company details page
				gotoCompaniesTab();
				LOGGER.info("Redirected to companies list page");
				waitForPageLoaded();
				companiesPage.clearFiltersOfCompaniesListPage("clearfilter");
				impersonateCompanyByCompanyName(customfieldsCompany);
				LOGGER.info("Redirected to companies details page");
				goToPreferencesTab();
				softAssert.assertTrue(customFields.equals(companiesPage.getTextOfColumns("customFieldsLabel")), "Incorrect custom fields are displayed on device details page : TEST CASE 1106674");				
				LOGGER.info("Verified Custom Fields successfully");
				
				softAssert.assertAll();
				LOGGER.info("Validation of custom fields on Companies page got completed successfully.");
	}

	@Test(priority = 97, groups = {"ACTIVECARESANITY",  "REGRESSIONSETTINGS2", "SMOKE", "REGRESSION_CI", "SMOKE_CI",
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
//		sleeper(2000);
		sleeper(20000);
		Assert.assertTrue(
				settingsPage.postNotificationCheckImportInV3(
						getEnvironmentSpecificData(System.getProperty("environment"), "ORDERS_LESS_THAN_255")),
				"Message on notification window is incorrect");
		LOGGER.info("Notification message verification for import has passed");

//		companiesPage.enterTextForCompaniesPage("companyNameSearch",
//				settingsPage.getCSVFieldValue("EndCustomerName", "ORDERS_LESS_THAN_255"));
		sleeper(3000);
		
		//verifying result of the company search
		softAssert.assertTrue(companiesPage.verifyElementsOfCompaniesPage("companiesSearchResult"));

		softAssert.assertAll();
	}

	@Test(priority = 98, groups = {"ACTIVECARESANITY", "REGRESSIONSETTINGS2", "SMOKE", "REGRESSION_CI", "SMOKE_CI",
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
	
	// As per new CarePack service this Test case is Invalid. Hence marked as disabled.
	@Test(priority = 99, groups = {"ACTIVECARESANITY", "REGRESSIONSETTINGS2", "SMOKE", "REGRESSION_CI", "SMOKE_CI",
			"STABILIZATION_STAGING"}, enabled = false)
	public final void verifyCSVUploadNewACTenant() throws Exception {
		login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		String expectedMailContent;
		waitForPageLoaded();

		String[] columnValueToUpdateRandomData = { "EndCustomerName", "EndCustomerPrimaryEmail",
				"ObjectOfServiceSerialNumber" };

		settingsPage.updateActiveCareCSVFieldValue("ORDERS_NEW_AC_COMPANY", columnValueToUpdateRandomData);
//		String email_id = settingsPage.getCSVFieldValue("EndCustomerPrimaryEmail", "ORDERS_NEW_AC_COMPANY"); 
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
				getEnvironmentSpecificData(System.getProperty("environment"), "ORDERS_NEW_AC_COMPANY"));
		sleeper(20000); // File upload takes time.
		Assert.assertTrue(
				settingsPage.postNotificationCheckImportInV3(
						getEnvironmentSpecificData(System.getProperty("environment"), "ORDERS_NEW_AC_COMPANY")),
				"Message on notification window is incorrect");
		LOGGER.info("Notification message verification for import has passed");

//		companiesPage.enterTextForCompaniesPage("companyNameSearch",
//				settingsPage.getCSVFieldValue("EndCustomerName", "ORDERS_NEW_AC_COMPANY"));
		sleeper(3000);

		// verifying result of the company search
		softAssert.assertTrue(companiesPage.verifyElementsOfCompaniesPage("companiesSearchResult"));
	
		// Verify ActiveCare email -> Commented this code. Need to explore.
//		expectedMailContent = ((((getTextLanguage(LanguageCode, "idm", "active.care.account.create.subject") + " " + getTextLanguage(LanguageCode, "idm", "active.care.account.create.dear")+" " + 
//		(getTextLanguage(LanguageCode, "idm", "active.care.account.create.body_msg1") + " "
//				+ getTextLanguage(LanguageCode, "idm", "active.care.account.create.body_msg2").replace("{0}",settingsPage.getCSVFieldValue("EndCustomerName", "ORDERS_NEW_AC_COMPANY")).replace("{1}", "1")+" "
//				+ getTextLanguage(LanguageCode, "idm", "active.care.account.create.body_msg3").replace("{0}", "https://vimeo.com/545065780"))+" "
//				+ getTextLanguage(LanguageCode, "idm", "active.care.account.create.body_msg14")+ " "
//				+ getTextLanguage(LanguageCode, "idm", "active.care.account.create.body_msg15").replace("{0}", System.getProperty("environment")) + " "
//				+ getTextLanguage(LanguageCode, "idm", "active.care.account.create.body_msg16").replace("{0}", "https://www.hpdaas.com/software").replace("{1}","https://h20195.www2.hp.com/v2/getpdf.aspx/4aa7-8824enw")+ " "
//				+ getTextLanguage(LanguageCode, "idm", "active.care.account.create.body_msg13") + " "
//				+ getTextLanguage(LanguageCode, "idm", "active.care.account.create.body_msg4") + " "
//				+" 1. "+ getTextLanguage(LanguageCode, "idm", "sign_in_change_notification.invite.body.content_8") + " "
//				+" 2. "+ getTextLanguage(LanguageCode, "idm", "sign_in_change_notification.invite.body.content_8") + " "
//				+" 3. "+ getTextLanguage(LanguageCode, "idm", "sign_in_change_notification.invite.body.content_8") + " "
//				+" 4. "+ getTextLanguage(LanguageCode, "idm", "sign_in_change_notification.invite.body.content_8") + " "
//				+" 5. "+ getTextLanguage(LanguageCode, "idm", "sign_in_change_notification.invite.body.content_8") + " "
//				+" 6. "+ getTextLanguage(LanguageCode, "idm", "sign_in_change_notification.invite.body.content_8") + " "
//				+ getTextLanguage(LanguageCode, "idm", "onboarding.display.msg_7")+""+" "
//				+ getTextLanguage(LanguageCode, "idm", "active.care.account.create.body_msg11")+" "
//				+ getTextLanguage(LanguageCode, "idm", "active.care.account.create.body_msg12")+" "
//				))));
//
//		Mailinator objMailinator = new Mailinator("", UserPage.mailIdForMailinator);
//		sleeper(5000);
//		MailinatorMail objMailinatorEmail = objMailinator.getSpecificEmailUsingIncidentCode(UserVariables.ACTIVECARE_EMAIL_TITLE, email_id, false);
//		if(objMailinatorEmail!=null){
//		String mailContent = objMailinatorEmail.getBody();
//		InputStream in = org.apache.commons.io.IOUtils.toInputStream(mailContent, "UTF-8");
//		BodyContentHandler handler = new BodyContentHandler();
//		Metadata metadata = new Metadata();
//		ParseContext pcontext = new ParseContext();
//		HtmlParser htmlparser = new HtmlParser();
//		htmlparser.parse(in, handler, metadata, pcontext);
//		String gotEmailBody = handler.toString().replaceAll("\\s{2,}", " ").trim();
//		softAssert.assertTrue(handler.toString().replaceAll("\\s{2,}", " ").trim().equalsIgnoreCase(expectedMailContent), "Mail content does not match");
//		}else{
//			LOGGER.error("No email is received after adding users.");
//		}
		
		softAssert.assertAll();
	}
	
	// As per new CarePack service this Test case is Invalid. Hence marked as disabled. Use new test case verifyCPImportCSVOrdersPage() instead.
	@Test(priority = 100, groups = {"ACTIVECARESANITY", "REGRESSIONSETTINGS2", "SMOKE", "REGRESSION_CI", "SMOKE_CI",
			"STABILIZATION_STAGING"}, enabled=true)
	public final void verifyCSVUploadExistingACTenant() throws Exception {
		
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
//		refreshPage();
		
//		Assert commented because of Elastic Search issue.
//		String planNameDeviceList = deviceListPage.getTextOfDeviceListPage("planNameFirstDeviceListPagePE");
//		softAssert.assertTrue(planNameDeviceList.equals(
//				getTextLanguage(LanguageCode, "daas_ui", "services.title.hp_care_pack")));
//		deviceListPage.clickByJavaScriptOnDeviceListPage("firstDeviceInList");
//		deviceDetailPage.waitForElementsOfDeviceDetailsPage("deviceDetailsTitle");

//		Assert Failing due to page refresh. Web element goes stale. Need to handle investigate. Working fine in manual execution.
//		LOGGER.info("Redirected to Device details page");
//		String afterEnrollmentplanName = deviceDetailPage.getTextOfDeviceDetailsPage("deviceDetailsEnrolledPlans");
//		softAssert.assertTrue(afterEnrollmentplanName.equals(
//				getTextLanguage(LanguageCode, "daas_ui", "services.title.hp_care_pack")), "Plan is not Active Care after enrollment.");
		softAssert.assertAll();
	}
	

	@Test(priority = 101, groups = {"ACTIVECARESANITY", "REGRESSIONSETTINGS2", "SMOKE", "REGRESSION_CI", "SMOKE_CI",
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
	
	@Test(priority = 102, groups = {"ACTIVECARESANITY", "REGRESSIONSETTINGS2", "SMOKE", "REGRESSION_CI", "SMOKE_CI",
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
		// Update ObjectServiceSerialNumber each execution
		settingsPage.updateActiveCareCSVFieldValue("ORDERS_MANDATORY_HT", columnValueToUpdateRandomData);

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

//		Assert.assertTrue(ordersListPage.verifyImportCSVOrdersListPage(LanguageCode, 
//				getEnvironmentSpecificData(System.getProperty("environment"), "ORDERS_MANDATORY_HT")), "FAIL: File import failed.");
//		refreshPage();
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
		
	
	
	
	@Test(priority = 103, groups = {"ACTIVECARESANITY", "REGRESSIONSETTINGS2", "SMOKE", "REGRESSION_CI", "SMOKE_CI",
			"STABILIZATION_STAGING"})
	public final void verifyProactiveCaseCreation() throws Exception {
		
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
		DeviceListPage devicelistPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
		DeviceDetailsPage deviceDetailPage = new DeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		waitForPageLoaded();
				
//		String serialnumber = settingsPage.getCSVFieldValue("ObjectOfServiceSerialNumber", "ORDERS_EXISTING_AC_COMPANY");
		login("ACTIVECARETENANT_EMAIL", "ACTIVECARETENANT_PASSWORD");
		String tenantID = getValueFromToken("tenant");
		gotoDevicesTab();
		Assert.assertTrue(devicelistPage.waitForElementsOfDeviceListPage("serialNumberList"), "Fail: There are no devices in this Tenant.");
		devicelistPage.selectElementFromDropDownOfDeviceListPage("statusFilter", "statusdropdownValues", "Active");
		sleeper(500);
		Assert.assertTrue(devicelistPage.waitForElementsOfDeviceListPage("serialNumberList"), "Fail: There are no Active devices in this Tenant.");
		
		String serialnumber = devicelistPage.getAttributesOfDeviceListPage("firstDeviceSerialNumber", "title");
		devicelistPage.clickByJavaScriptOnDeviceListPage("firstDeviceInList");
		deviceDetailPage.waitForElementsOfDeviceDetailsPage("deviceDetailsTitle");
		sleeper(3000);

		LOGGER.info("Redirected to Device details page");
		// Defect is raised for the same.
//		String planName = deviceDetailPage.getTextOfDeviceDetailsPage("deviceDetailsEnrolledPlans");
//		softAssert.assertTrue(planName.equals(
//				getTextLanguage(LanguageCode, "daas_ui", "services.title.hp_care_pack")), "Plan is not Active Care before enrollment.");
		
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
		// As per new implementation Case information wont be available for Proactive case
//		settingsPage.createCase();
//		softAssert.assertTrue(settingsPage.waitForElementsOfSettingsPageForinvisibile("createCaseButton"), 
//				"Create Case button is visible on Proactive Incident details page for Active Care only device.");
//		settingsPage.waitForElementsOfSettingsPage("caseInformationTileTitle");
//		settingsPage.scrollTillViewSettingsPage("caseInformationTileTitle");
//		softAssert.assertTrue(settingsPage.getTextOfSettingsPage("caseInformationTileTitle").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "incident.details.section.case.information").toUpperCase()), "Title on case information tile title is incorrect");
//		softAssert.assertTrue(settingsPage.getTextOfSettingsPage("caseIDLabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "incidents.caseID")), "Case ID label on case information tile title is incorrect");
//		softAssert.assertTrue(settingsPage.getTextOfSettingsPage("caseStatusLabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "incident.list.caseStatus")), "Case status label on case information tile title is incorrect");
//		softAssert.assertTrue(settingsPage.getTextOfSettingsPage("caseSubmissionDate").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "incident.details.section.case.submission")), "Case submission value on case information tile title is incorrect");
//		softAssert.assertTrue(settingsPage.getTextOfSettingsPage("caseLocation").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "incident.details.section.case.location")), "Case location label on case information tile title is incorrect");
//		softAssert.assertTrue(settingsPage.getTextOfSettingsPage("caseContact").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "incident.details.section.case.contact")), "Case contact label on case information tile title is incorrect");

		softAssert.assertAll();
		
		
	}
	

	@Test(priority = 104, groups = {"ACTIVECARESANITY", "REGRESSIONSETTINGS2", "REGRESSION_CI",
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
	
	// This Test will verify Active care tenant should not able to assign partner to the company
	@Test(priority = 105, groups = {"ACTIVECARESANITY", "REGRESSIONSETTINGS2", "REGRESSION_CI",
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

	// This Test will verify if AC+PI tenant is able to assign partner to the company
	@Test(priority = 106, groups = {"ACTIVECARESANITY", "REGRESSIONSETTINGS2", "REGRESSION_CI",
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
	
	
	// This method is to verify if Active care is not displayed change plan modal for device with AC and other license based plan
		@Test(priority = 107, groups = {"ACTIVECARESANITY", "REGRESSIONSETTINGS2", "SMOKE", "REGRESSION_CI", "SMOKE_CI",
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


	
	// This Test will verify ActiveCare Tenant creation using Root user -> Add Company flow.
		@Test(priority = 108, groups = {"ACTIVECARESANITY", "REGRESSIONSETTINGS2", "REGRESSION_CI",
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
	
	
	
		// This method verifies menu under Settings and their sub-menus for AC only tenant
		@Test(priority = 109, groups = {"ACTIVECARESANITY", "REGRESSIONSETTINGS2", "REGRESSION_CI",
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
		
		// This method verifies menu under Settings and their sub-menus for AC+PI tenant
		@Test(priority = 110, groups = {"ACTIVECARESANITY", "REGRESSIONSETTINGS2", "REGRESSION_CI",
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
		
		
		// This Test will verify ActiveCare Tenant creation is not allowed for Partner user -> Add Company flow.
		@Test(priority = 111, groups = {"ACTIVECARESANITY", "REGRESSIONSETTINGS2", "REGRESSION_CI",
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
			
		// This Test will verify ActiveCare Tenant creation is not allowed for Partner user -> Add Company flow.
		@Test(priority = 111, groups = {"ACTIVECARESANITY", "REGRESSIONSETTINGS2", "REGRESSION_CI",
				"STABILIZATION_STAGING", "CAREPACK"}, description = "Test Case ID : 1274300")
		public final void verifyCPImportCSVOrdersPage() throws Exception {
			login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
			String environment = System.getProperty("environment");
			OrdersListPage ordersListPage = new OrdersListPage(PreDefinedActions.getDriver()).getInstance();
			OrderDetailsPage orderDetailsPage = new OrderDetailsPage(PreDefinedActions.getDriver()).getInstance();
			SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
			SoftAssert softAssert = new SoftAssert();
			String[] columnValueToUpdateRandomData = { "ObjectOfServiceSerialNumber" };
			
			// Update ObjectServiceSerialNumber each execution
			settingsPage.updateActiveCareCSVFieldValue("ORDERS_EXISTING_AC_COMPANY", columnValueToUpdateRandomData);
			
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
				LOGGER.info("As per new CarePack service EU root user will not have Orders tab. Only US root user will have orders tab and will be allowed to import devices into a Tenant.");
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
			// Once defect is closed will add next step to validate UI details with test data.
			softAssert.assertAll();
			}
		}

		@Test(priority = 79, groups = { "REGRESSIONSETTINGS2", "REGRESSION_CI",	"STABILIZATION_STAGING" }, description = "TEST CASE - 1180738")
		public final void verifyNotificationsTabforAC() throws Exception {
			
			List<String> hardwareHealthList = new ArrayList<String>(Arrays.asList(
					getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties",
							"com.hp.mpi.icm.subtype.systemthermal"),
					// For EU region Battery Recall option is missing from HardwareHealthList.Need to check. 
					getTextLanguage(LanguageCode,"MPI-Incident-management-service-backend-Properties",
							"hp.mpi.icm.subtype.batteryrecall"),
					getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties",
							"com.hp.mpi.icm.subtype.Fan-Warning"),
					getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties",
							"com.hp.mpi.icm.subtype.Fan-Critical"),
					getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties",
							"com.hp.mpi.icm.subtype.hddpredictive"),
					getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties",
							"com.hp.mpi.icm.subtype.biosoutofdate"),
					getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties",
							"com.hp.mpi.icm.subtype.batteryneedsattention")));
			
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
									.replace("{applicationName}", "HP Workforce Experience Platform")),
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
		
		@Test(priority = 113, groups = {"ACTIVECARESANITY", "ACTIVECARESANITY","REGRESSIONSETTINGS2", "REGRESSION_CI","STABILIZATION_STAGING" }, description = "TEST CASE - 1295294")
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
			ordersListPage.clickOnElementsOfOrdersListPage("orderssummary");
			softAssert.assertTrue(ordersListPage.verifyElementsOfordersListPage("downloadToastMessage"),"Toast message is displayed");	
			LOGGER.info("Order Summary File Download validated successfully.");
			softAssert.assertAll();	
			}
	}
		//This method verifies redirection of URL for customers requesting disabling ActiveCare
		@Test(priority = 114, groups = { "ACTIVECARESANITY","REGRESSIONSETTINGS2", "REGRESSION_CI","STABILIZATION_STAGING" }, description = "TEST CASE - 1316789")
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
		@Test(priority = 115, groups = { "ACTIVECARESANITY","REGRESSIONSETTINGS2", "REGRESSION_CI","STABILIZATION_STAGING" }, description = "TEST CASE - 1316788")
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
		@Test(priority = 116, groups = { "ACTIVECARESANITY","REGRESSIONSETTINGS2", "REGRESSION_CI","STABILIZATION_STAGING" }, description = "TEST CASE - 1228306",dataProvider="helpAndSupportMenus")
		public final void verifyHelpAndSupportTabs(String username, String password, String[] helpAndSupportMenus) throws Exception {

			SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
			DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
			SoftAssert softAssert = new SoftAssert();
			login(username,password);
			gotoHelpAndSupportTab();
			Assert.assertTrue(dashboardPage.verifyWidgetsDisplayedForActiveCare("helpandsupportabs", helpAndSupportMenus), "helpAndSupportTabs is incorrect");
			softAssert.assertAll();
			LOGGER.info("Help And Support Tabs completed successfully.");
		}

		@Test(priority = 117, groups = { "ACTIVECARESANITY","REGRESSIONSETTINGS2", "REGRESSION_CI","STABILIZATION_STAGING" },description = "TEST CASE - 1228306")
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
		@Test(priority = 118, groups = { "PRODUCTION_LDK","INITECH_SOLUTIONS" })
		public final void verifySettingsLdk() throws Exception {
			login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
			SoftAssert softAssert = new SoftAssert();
			 String testSuiteName = SetTestEnvironments.suiteName;
				if(testSuiteName.contains("LDK") || testSuiteName.contains("Initech")){
					switchUserBasedOnSuite(testSuiteName);
				}
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
 	}
 

		
	

