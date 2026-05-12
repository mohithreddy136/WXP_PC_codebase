package com.testscripts.daasui;

import com.daasui.constants.WEPPulsesCreationPageVariables;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.basesource.action.PreDefinedActions;
import com.daasui.constants.CommonVariables;
import com.daasui.pages.WEXCompanyOverviewPage;
import com.daasui.pages.WEXDashboardPage;
import com.daasui.pages.WEXSettingPage;

public class WEXSettingTest extends CommonTest {

	private static Logger LOGGER = LogManager.getLogger(WEXSettingTest.class);
	private static final String Archived_Devices_days = "60 Days";
	private static final String CUSTOMER_NAME_STAGING = "WEX Customer";

	@Test(priority = 1, groups = { "REGRESSION_PLATFORMCX"}, description = "TestCaseID:C42273810")
	public final void verifySettingInformationOnSettngPage() throws Exception {

		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		WEXSettingPage WEXSettingPage = new WEXSettingPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		leftSideMenuVerification();
		WEXSettingPage.companyView(CommonVariables.SETTINGS);
		waitForPageLoaded();
		LOGGER.info("Redirected to setting tab");

		softAssert.assertTrue(
				WEXSettingPage.matchTextOfWEXSettingPage("companyname",
						getTextLanguage(LanguageCode, "daas_ui", "Setting.Companyinformation.Companyname")),
				"Authentication pop up header is incorrect");
		WEXSettingPage.verifyElementsOfWEXSettingPage("SettingLogo");
		softAssert
				.assertTrue(
						WEXSettingPage.matchTextOfWEXSettingPage("PreferredTimeZone",
								getTextLanguage(LanguageCode, "daas_ui",
										"Setting..Companyinformation.perferedtimezone")),
						"perferedtimezone is incorrect");
		softAssert
				.assertTrue(
						WEXSettingPage.matchTextOfWEXSettingPage("SettingCompanyStatus",
								getTextLanguage(LanguageCode, "daas_ui",
										"Setting..Companyinformation.SettingCompanyStatus")),
						"SettingCompanyStatus is incorrect");
		softAssert
				.assertTrue(
						WEXSettingPage.matchTextOfWEXSettingPage("PrimaryAdministrator",
								getTextLanguage(LanguageCode, "daas_ui",
										"Setting..Companyinformation.PrimaryAdministrator")),
						"PrimaryAdministrator is incorrect");
		softAssert.assertTrue(
				WEXSettingPage.matchTextOfWEXSettingPage("PrimaryAdministratorEmail",
						getTextLanguage(LanguageCode, "daas_ui",
								"Setting.Companyinformation.PrimaryAdministratorEmail")),
				"PrimaryAdministratorEmail is incorrect");
		softAssert
				.assertTrue(
						WEXSettingPage.matchTextOfWEXSettingPage("PrimaryContactNumber",
								getTextLanguage(LanguageCode, "daas_ui",
										"Setting..Companyinformation.PrimaryContactNumber")),
						"PrimaryContactNumber is incorrect");
		LOGGER.info("Vaidation is done for company information on setting page");
	}

	@Test(priority = 2, groups = { "REGRESSION_PLATFORMCX" }, description = "TestCaseID:C44670370")
	public final void verifyShippingLocationTabOnSettngPage() throws Exception {

		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		WEXSettingPage WEXSettingPage = new WEXSettingPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		leftSideMenuVerification();
		WEXSettingPage.companyView(CommonVariables.SETTINGS);
		waitForPageLoaded();
		LOGGER.info("Redirected to setting tab");

		WEXSettingPage.verifyElementsOfWEXSettingPage("Shippinglocationtab");
		WEXSettingPage.verifyElementsOfWEXSettingPage("Shippinglocationtab");
		LOGGER.info("Clicked on the shipping location tab");
		softAssert
				.assertTrue(
						WEXSettingPage.matchTextOfWEXSettingPage("Shippinglocationheader",
								getTextLanguage(LanguageCode, "daas_ui",
										"Setting..Companyinformation.perferedtimezone")),
						"perferedtimezone is incorrect");
		WEXSettingPage.verifyElementsOfWEXSettingPage("shippinglocationaddbutton");

	}

	@Test(priority = 3, groups = { "REGRESSION_PLATFORMCX" }, description = "TestCaseID:C42455399")
	public final void verifyPreferencesTabOnSettingPage() throws Exception {

		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		WEXSettingPage WEXSettingPage = new WEXSettingPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		leftSideMenuVerification();
		WEXSettingPage.companyView(CommonVariables.SETTINGS);
		waitForPageLoaded();
		LOGGER.info("Redirected to setting tab");

		WEXSettingPage.verifyElementsOfWEXSettingPage("Preferencestab");
		WEXSettingPage.clickOnElementsOfWEXSettingPage("Preferencestab");
		softAssert.assertTrue(
				WEXSettingPage.matchTextOfWEXSettingPage("ArchivedDevicesheader",
						getTextLanguage(LanguageCode, "daas_ui", "Setting.preference.archivedevices.header")),
				"Archived deviced header is incorrect");
		softAssert
				.assertTrue(
						WEXSettingPage.matchTextOfWEXSettingPage("ArchivedDevicessubheader",
								getTextLanguage(LanguageCode, "daas_ui",
										"Setting.preference.archivedevices.subheader")),
						"Archived deviced subheader is incorrect");
		softAssert
				.assertTrue(
						WEXSettingPage.matchTextOfWEXSettingPage("archivedevicesubtitle",
								getTextLanguage(LanguageCode, "daas_ui",
										"Setting.preference.archivedevices.subtittle")),
						"Archived deviced subtittle is incorrect");
		WEXSettingPage.verifyElementsOfWEXSettingPage("archivedeviceeditbutton");
		WEXSettingPage
				.mouseHoverclickOfWEXSettingPage(WEXSettingPage.getElementOfWEXSettingPage("archivedeviceeditbutton"));
		softAssert
				.assertTrue(
						WEXSettingPage.matchTextOfWEXSettingPage("Archivedevicespopheader",
								getTextLanguage(LanguageCode, "daas_ui",
										"Setting.preference.archivedevicespopup.header")),
						"Archived deviced pop up header is incorrect");
		softAssert
				.assertTrue(
						WEXSettingPage.matchTextOfWEXSettingPage("Archivedevicespopupselectboxlabel",
								getTextLanguage(LanguageCode, "daas_ui",
										"Setting.preference.archivedevicesselectbox.label")),
						"Archived deviced selectbox label is incorrect");
		WEXSettingPage.mouseHoverclickOfWEXSettingPage(
				WEXSettingPage.getElementOfWEXSettingPage("Archivedevicespopupselectbox"));
		WEXSettingPage.selectTextValueFromDropdownOfWEXSettingPage("Archivedevicespopupselectboxvalue",
				Archived_Devices_days, "Archivedevicespopupselectbox");
		WEXSettingPage.verifyElementsOfWEXSettingPage("Archivedevicespopupsavebutton");
		WEXSettingPage.verifyElementsOfWEXSettingPage("Archivedevicespopupcancelbutton");
		WEXSettingPage.clickOnElementsOfWEXSettingPage("Archivedevicespopupsavebutton");
		LOGGER.info("Vaidation is done for archived devices section on preferences tab on setting page");

		WEXSettingPage.verifyElementsOfWEXSettingPage("Authenticationsection");
		WEXSettingPage.clickOnElementsOfWEXSettingPage("Authenticationsection");
		softAssert.assertTrue(
				WEXSettingPage.matchTextOfWEXSettingPage("Authenticationheader",
						getTextLanguage(LanguageCode, "daas_ui", "Setting.preference.Authentication.header")),
				"Authentication header is incorrect");
		softAssert
				.assertTrue(
						WEXSettingPage.matchTextOfWEXSettingPage("Authenticationsubheader",
								getTextLanguage(LanguageCode, "daas_ui",
										"Setting.preference.Authentication.subheader")),
						"Authentication subheader is incorrect");
		WEXSettingPage.verifyElementsOfWEXSettingPage("Authenticationeditbutton");
		Thread.sleep(3000);
		WEXSettingPage
				.mouseHoverclickOfWEXSettingPage(WEXSettingPage.getElementOfWEXSettingPage("Authenticationeditbutton"));
		softAssert
				.assertTrue(
						WEXSettingPage.matchTextOfWEXSettingPage("Authenticationheaderpopup",
								getTextLanguage(LanguageCode, "daas_ui",
										"Setting.preference.Authenticationpopup.header")),
						"Authentication pop up header is incorrect");
		softAssert
				.assertTrue(
						WEXSettingPage.matchTextOfWEXSettingPage("Authenticationsubheaderpopup",
								getTextLanguage(LanguageCode, "daas_ui",
										"Setting.preference.Authenticationpopup.subheader")),
						"Authentication pop up subheader is incorrect");
		softAssert.assertTrue(
				WEXSettingPage.matchTextOfWEXSettingPage("Authenticationselectboxlabel",
						getTextLanguage(LanguageCode, "daas_ui",
								"Setting.preference.Authenticationpopupselectbox.label")),
				"Authentication select box label is incorrect");
		WEXSettingPage.verifyElementsOfWEXSettingPage("Authenticationsubmitbutton");
		WEXSettingPage.verifyElementsOfWEXSettingPage("Authenticationcancelbutton");
		WEXSettingPage.clickOnElementsOfWEXSettingPage("Authenticationsubmitbutton");
		LOGGER.info("Vaidation is done for Authentication section on preferences tab on setting page");

		WEXSettingPage.verifyElementsOfWEXSettingPage("companypinsection");
		WEXSettingPage.clickOnElementsOfWEXSettingPage("companypinsection");
		softAssert.assertTrue(
				WEXSettingPage.matchTextOfWEXSettingPage("companypinheader",
						getTextLanguage(LanguageCode, "daas_ui", "Setting.preference.CompanyPin.header")),
				"CompanyPin header is incorrect");
		softAssert.assertTrue(
				WEXSettingPage.matchTextOfWEXSettingPage("companypinsubheader",
						getTextLanguage(LanguageCode, "daas_ui", "Setting.preference.CompanyPin.subheader")),
				"CompanyPin subheader is incorrect");
		softAssert.assertTrue(
				WEXSettingPage.matchTextOfWEXSettingPage("companypinrowvalidation",
						getTextLanguage(LanguageCode, "daas_ui", "Setting.preference.CompanyPin.header")),
				"CompanyPin row validation is incorrect");
		WEXSettingPage.verifyElementsOfWEXSettingPage("companydeletepinicon");
		WEXSettingPage.verifyElementsOfWEXSettingPage("companyeditpinicon");
		LOGGER.info("Vaidation is done for Company pin section on preferences tab on setting page");

		WEXSettingPage.verifyElementsOfWEXSettingPage("fleetscorepreferencessection");
		WEXSettingPage.clickOnElementsOfWEXSettingPage("fleetscorepreferencessection");
		softAssert
				.assertTrue(
						WEXSettingPage.matchTextOfWEXSettingPage("fleetscorepreferencesheader",
								getTextLanguage(LanguageCode, "daas_ui",
										"Setting.preference.fleetscorepreferences.header")),
						"fleetscorepreferences header is incorrect");
		softAssert.assertTrue(
				WEXSettingPage.matchTextOfWEXSettingPage("fleetscorepreferencesdescription",
						getTextLanguage(LanguageCode, "daas_ui", "Setting.preference.fleetscorepreferences")),
				"fleetscorepreferences subheader is incorrect");
		LOGGER.info("Vaidation is done for Fleet Score Preferences section on preferences tab on setting page");

		WEXSettingPage.verifyElementsOfWEXSettingPage("Devicegroupssection");
		WEXSettingPage.clickOnElementsOfWEXSettingPage("Devicegroupssection");
		softAssert
				.assertTrue(
						WEXSettingPage.matchTextOfWEXSettingPage("Devicegroupheader",
								getTextLanguage(LanguageCode, "daas_ui",
										"Setting.preference.fleetscorepreferences.header")),
						"fleetscorepreferences header is incorrect");
		WEXSettingPage.verifyElementsOfWEXSettingPage("switchtomultilevelbutton");
		LOGGER.info("Vaidation is done for Device groups section on preferences tab on setting page");

		WEXSettingPage.verifyElementsOfWEXSettingPage("customefieldsfordevicessection");
		WEXSettingPage.clickOnElementsOfWEXSettingPage("customefieldsfordevicessection");
		softAssert.assertTrue(
				WEXSettingPage.matchTextOfWEXSettingPage("customfieldsheader",
						getTextLanguage(LanguageCode, "daas_ui", "Setting.preference.customerfields.header")),
				"customerfields header is incorrect");
		softAssert
				.assertTrue(
						WEXSettingPage.matchTextOfWEXSettingPage("customfieldssubheader",
								getTextLanguage(LanguageCode, "daas_ui",
										"Setting.preference.customerfields.subheader")),
						"customerfields sub header is incorrect");
		softAssert
				.assertTrue(
						WEXSettingPage.matchTextOfWEXSettingPage("customefieldsvalidation",
								getTextLanguage(LanguageCode, "daas_ui",
										"Setting.preference.customerfields.Rowvalidation")),
						"customerfields row validation is incorrect");
		LOGGER.info("Vaidation is done for custom fields for devices section on preferences tab on setting page");

		WEXSettingPage.verifyElementsOfWEXSettingPage("lifecyclesection");
		WEXSettingPage.clickOnElementsOfWEXSettingPage("lifecyclesection");
		softAssert.assertTrue(
				WEXSettingPage.matchTextOfWEXSettingPage("lifecycleheader",
						getTextLanguage(LanguageCode, "daas_ui", "Setting.preference.lifecyclestatus.header")),
				"lifecyclestatus header is incorrect");
		softAssert.assertTrue(
				WEXSettingPage.matchTextOfWEXSettingPage("lifecyclerow",
						getTextLanguage(LanguageCode, "daas_ui", "Setting.preference.lifecyclestatus.header")),
				"lifecyclestatus row validation is incorrect");
		LOGGER.info("Vaidation is done for life cycle status section on preferences tab on setting page");

		WEXSettingPage.verifyElementsOfWEXSettingPage("datacollectionsection");
		WEXSettingPage.clickOnElementsOfWEXSettingPage("datacollectionsection");
		softAssert.assertTrue(
				WEXSettingPage.matchTextOfWEXSettingPage("datacollectionheader",
						getTextLanguage(LanguageCode, "daas_ui", "Setting.preference.datacollection.header")),
				"datacollection header is incorrect");
		softAssert
				.assertTrue(
						WEXSettingPage.matchTextOfWEXSettingPage("datacollectionsubheading",
								getTextLanguage(LanguageCode, "daas_ui",
										"Setting.preference.datacollection.subheader")),
						"datacollection sub header is incorrect");
		softAssert.assertTrue(
				WEXSettingPage.matchTextOfWEXSettingPage("companyfirwallrow",
						getTextLanguage(LanguageCode, "daas_ui", "Setting.preference.datacollection.header")),
				"datacollection companyfirwallrow row validation is incorrect");
		softAssert.assertTrue(
				WEXSettingPage.matchTextOfWEXSettingPage("BlueScreenCrashDumpsrowm",
						getTextLanguage(LanguageCode, "daas_ui", "Setting.preference.datacollection.header")),
				"datacollection BlueScreenCrashDumpsr row validation is incorrect");
		softAssert.assertTrue(
				WEXSettingPage.matchTextOfWEXSettingPage("devicehealthrow",
						getTextLanguage(LanguageCode, "daas_ui", "Setting.preference.datacollection.header")),
				"datacollection devicehealth row validation is incorrect");
		softAssert.assertTrue(
				WEXSettingPage.matchTextOfWEXSettingPage("devicelogsrow",
						getTextLanguage(LanguageCode, "daas_ui", "Setting.preference.datacollection.header")),
				"datacollection devicelogs row validation is incorrect");
		softAssert.assertTrue(
				WEXSettingPage.matchTextOfWEXSettingPage("companylocation",
						getTextLanguage(LanguageCode, "daas_ui", "Setting.preference.datacollection.header")),
				"datacollection networkidentifire row validation is incorrect");
		softAssert.assertTrue(
				WEXSettingPage.matchTextOfWEXSettingPage("networkidentifire",
						getTextLanguage(LanguageCode, "daas_ui", "Setting.preference.datacollection.header")),
				"datacollection networkidentifire validation is incorrect");
		softAssert.assertTrue(
				WEXSettingPage.matchTextOfWEXSettingPage("softwareinventory",
						getTextLanguage(LanguageCode, "daas_ui", "Setting.preference.datacollection.header")),
				"datacollection softwareinventory validation is incorrect");
		softAssert.assertTrue(
				WEXSettingPage.matchTextOfWEXSettingPage("softwareinventory",
						getTextLanguage(LanguageCode, "daas_ui", "Setting.preference.datacollection.header")),
				"datacollection softwareinventory validation is incorrect");
		softAssert.assertTrue(
				WEXSettingPage.matchTextOfWEXSettingPage("webapplicationusage",
						getTextLanguage(LanguageCode, "daas_ui", "Setting.preference.datacollection.header")),
				"datacollection webapplicationusage validation is incorrect");
		LOGGER.info("Vaidation is done for Data Collection section on preferences tab on setting page");

		WEXSettingPage.verifyElementsOfWEXSettingPage("domainnamesection");
		WEXSettingPage.clickOnElementsOfWEXSettingPage("domainnamesection");
		softAssert.assertTrue(
				WEXSettingPage.matchTextOfWEXSettingPage("domainnameheader",
						getTextLanguage(LanguageCode, "daas_ui", "Setting.preference.domainname.header")),
				"domian name header is incorrect");
		softAssert.assertTrue(
				WEXSettingPage.matchTextOfWEXSettingPage("domainnamesubheader",
						getTextLanguage(LanguageCode, "daas_ui", "Setting.preference.domainname.subheader")),
				"domain name sub header is incorrect");
		softAssert
				.assertTrue(
						WEXSettingPage.matchTextOfWEXSettingPage("domainnamerow",
								getTextLanguage(LanguageCode, "daas_ui",
										"Setting.preference.domainrow.domainname.row")),
						"domain name row  validation is incorrect");
		LOGGER.info("Vaidation is done for Domaine name section on preferences tab on setting page");

		WEXSettingPage.verifyElementsOfWEXSettingPage("companynetworksection");
		WEXSettingPage.clickOnElementsOfWEXSettingPage("companynetworksection");
		softAssert.assertTrue(
				WEXSettingPage.matchTextOfWEXSettingPage("companynetworkheader",
						getTextLanguage(LanguageCode, "daas_ui", "Setting.preference.companynetwork.header")),
				"company network header is incorrect");
		softAssert
				.assertTrue(
						WEXSettingPage.matchTextOfWEXSettingPage("companynetworksubheader",
								getTextLanguage(LanguageCode, "daas_ui",
										"Setting.preference.companynetwork.subheader")),
						"company network sub header is incorrect");
		softAssert
				.assertTrue(
						WEXSettingPage.matchTextOfWEXSettingPage("companynetworkrow",
								getTextLanguage(LanguageCode, "daas_ui",
										"Setting.preference.domainrow.companynetwork.row")),
						"company network row  validation is incorrect");
		LOGGER.info("Vaidation is done for company network section on preferences tab on setting page");

		WEXSettingPage.verifyElementsOfWEXSettingPage("ThirdPartysoftwareIntegrationsection");
		WEXSettingPage.clickOnElementsOfWEXSettingPage("ThirdPartysoftwareIntegrationsection");
		softAssert.assertTrue(
				WEXSettingPage.matchTextOfWEXSettingPage("thirdpartysoftwareintegrationsection",
						getTextLanguage(LanguageCode, "daas_ui",
								"Setting.preference.thirdpartysoftwareintegrationsection.header")),
				"company network header is incorrect");
		softAssert
				.assertTrue(
						WEXSettingPage.matchTextOfWEXSettingPage("chromebookrow",
								getTextLanguage(LanguageCode, "daas_ui",
										"Setting.preference.datacollection.chromebookrow")),
						"datacollection chromebookrow validation is incorrect");
		softAssert
				.assertTrue(
						WEXSettingPage.matchTextOfWEXSettingPage("companytunerow",
								getTextLanguage(LanguageCode, "daas_ui",
										"Setting.preference.datacollection.companytunerow")),
						"datacollection companytunerow validation is incorrect");
		softAssert
				.assertTrue(
						WEXSettingPage.matchTextOfWEXSettingPage("servicenowrow",
								getTextLanguage(LanguageCode, "daas_ui",
										"Setting.preference.datacollection.servicenowrow")),
						"datacollection servicenowrow validation is incorrect");
		softAssert.assertTrue(
				WEXSettingPage.matchTextOfWEXSettingPage("companyairwatchrow",
						getTextLanguage(LanguageCode, "daas_ui",
								"Setting.preference.datacollection.companyairwatchrow")),
				"datacollection companyairwatchrow validation is incorrect");
		LOGGER.info(
				"Vaidation is done for Third Party Software Integration section on preferences tab on setting page");

		LOGGER.info("Vaidation is done for preferences tab on setting page");

	}

	@Test(priority = 4, groups = { "REGRESSION_PLATFORMCX" }, description = "TestCaseID:C42213602")
	public final void verifyRolesAndPermissionTabOnSettingPage() throws Exception {

		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		WEXSettingPage WEXSettingPage = new WEXSettingPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		leftSideMenuVerification();
		WEXSettingPage.companyView(CommonVariables.SETTINGS);
		waitForPageLoaded();
		LOGGER.info("Redirected to setting tab");
		WEXSettingPage.verifyElementsOfWEXSettingPage("rolesandpermissiontab");
		WEXSettingPage.clickOnElementsOfWEXSettingPage("rolesandpermissiontab");
		WEXSettingPage.verifyElementsOfWEXSettingPage("addrolebutton");
		softAssert.assertTrue(
				WEXSettingPage.matchTextOfWEXSettingPage("rolesheader",
						getTextLanguage(LanguageCode, "daas_ui", "Setting.RolesAndPermission.header")),
				"Roles And Permission header is incorrect");
		LOGGER.info("Vaidation is done for Roles and permission tab on setting page");

	}

	@Test(priority = 5, groups = { "REGRESSION_PLATFORMCX" }, description = "TestCaseID:C42317108")
	public final void verifyAssignedPartnerTabOnSettingPage() throws Exception {

		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		WEXSettingPage WEXSettingPage = new WEXSettingPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		leftSideMenuVerification();
		WEXSettingPage.companyView(CommonVariables.SETTINGS);
		waitForPageLoaded();
		LOGGER.info("Redirected to setting tab");

		WEXSettingPage.verifyElementsOfWEXSettingPage("assignedpartnertab");
		WEXSettingPage.clickOnElementsOfWEXSettingPage("assignedpartnertab");
		softAssert.assertTrue(
				WEXSettingPage.matchTextOfWEXSettingPage("assignmentsettingheader",
						getTextLanguage(LanguageCode, "daas_ui", "Setting.AssignedPartner.Assignment setting")),
				"Assigned Partner header is incorrect");
		WEXSettingPage.verifyElementsOfWEXSettingPage("invitationsection");
		WEXSettingPage.clickOnElementsOfWEXSettingPage("invitationsection");
		softAssert.assertTrue(
				WEXSettingPage.matchTextOfWEXSettingPage("invitatiossectionheader",
						getTextLanguage(LanguageCode, "daas_ui", "Setting.AssignedPartner.Assignment setting")),
				"Invitation partner header is incorrect");
		LOGGER.info("Vaidation is done for Assigned partner tab on setting page");

	}

	@Test(priority = 6, groups = { "REGRESSION_PLATFORMCX" }, description = "TestCaseID:C42317092")
	public final void verifyNotificationTabOnSettingPage() throws Exception {

		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		WEXSettingPage WEXSettingPage = new WEXSettingPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		leftSideMenuVerification();
		WEXSettingPage.companyView(CommonVariables.SETTINGS);
		waitForPageLoaded();
		LOGGER.info("Redirected to setting tab");

		WEXSettingPage.verifyElementsOfWEXSettingPage("Notificationtab");
		WEXSettingPage.clickOnElementsOfWEXSettingPage("Notificationtab");
		softAssert
				.assertTrue(
						WEXSettingPage.matchTextOfWEXSettingPage("devicenotificationheader",
								getTextLanguage(LanguageCode, "daas_ui",
										"Setting.Notification. devicenotificationeader")),
						"Device notification header is incorrect");
		softAssert
				.assertTrue(
						WEXSettingPage.matchTextOfWEXSettingPage("devicenotificationsubheader",
								getTextLanguage(LanguageCode, "daas_ui",
										"Setting.Notification. devicenotificationeader")),
						"Device notification header is incorrect");
		softAssert
				.assertTrue(
						WEXSettingPage.matchTextOfWEXSettingPage("devicenotificationheader",
								getTextLanguage(LanguageCode, "daas_ui",
										"Setting.Notification. devicenotificationeader")),
						"Device notification header is incorrect");
		LOGGER.info("Vaidation is done for Notification tab on setting page");
	}

	@Test(priority = 7, groups = { "REGRESSION_PLATFORMCX" }, description = "TestCaseID:C42273810")
	public final void verifySettingInformationOnSettngPageThroughPartner() throws Exception {

		login("PARTNER_EMAIL_WEP", "PARTNER_PASSWORD_WEP");
		WEXSettingPage WEXSettingPage = new WEXSettingPage(PreDefinedActions.getDriver()).getInstance();
		WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
		dashboardPage.leftSideMenuVerification();
		SoftAssert softAssert = new SoftAssert();
		dashboardPage.partnerWithCompanyView(
				getEnvironmentSpecificData(System.getProperty("environment"), "CUSTOMER_NAME"),
				CommonVariables.SETTINGS);
		leftSideMenuVerification();
		waitForPageLoaded();
		LOGGER.info("Redirected to setting tab");

		softAssert.assertTrue(
				WEXSettingPage.matchTextOfWEXSettingPage("companyname",
						getTextLanguage(LanguageCode, "daas_ui", "Setting.Companyinformation.Companyname")),
				"Authentication pop up header is incorrect");
		WEXSettingPage.verifyElementsOfWEXSettingPage("SettingLogo");
		softAssert
				.assertTrue(
						WEXSettingPage.matchTextOfWEXSettingPage("PreferredTimeZone",
								getTextLanguage(LanguageCode, "daas_ui",
										"Setting..Companyinformation.perferedtimezone")),
						"perferedtimezone is incorrect");
		softAssert
				.assertTrue(
						WEXSettingPage.matchTextOfWEXSettingPage("SettingCompanyStatus",
								getTextLanguage(LanguageCode, "daas_ui",
										"Setting..Companyinformation.SettingCompanyStatus")),
						"SettingCompanyStatus is incorrect");
		softAssert
				.assertTrue(
						WEXSettingPage.matchTextOfWEXSettingPage("PrimaryAdministrator",
								getTextLanguage(LanguageCode, "daas_ui",
										"Setting..Companyinformation.PrimaryAdministrator")),
						"PrimaryAdministrator is incorrect");
		softAssert.assertTrue(
				WEXSettingPage.matchTextOfWEXSettingPage("PrimaryAdministratorEmail",
						getTextLanguage(LanguageCode, "daas_ui",
								"Setting.Companyinformation.PrimaryAdministratorEmail")),
				"PrimaryAdministratorEmail is incorrect");
		softAssert
				.assertTrue(
						WEXSettingPage.matchTextOfWEXSettingPage("PrimaryContactNumber",
								getTextLanguage(LanguageCode, "daas_ui",
										"Setting..Companyinformation.PrimaryContactNumber")),
						"PrimaryContactNumber is incorrect");
		LOGGER.info("Vaidation is done for company information on setting page");
	}

	@Test(priority = 8, groups = { "REGRESSION_PLATFORMCX" }, description = "TestCaseID:C44670370")
	public final void verifyShippingLocationTabOnSettngPageThroughPartner() throws Exception {

		login("PARTNER_EMAIL_WEP", "PARTNER_PASSWORD_WEP");
		WEXSettingPage WEXSettingPage = new WEXSettingPage(PreDefinedActions.getDriver()).getInstance();
		WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
		dashboardPage.leftSideMenuVerification();
		SoftAssert softAssert = new SoftAssert();
		dashboardPage.partnerWithCompanyView(
				getEnvironmentSpecificData(System.getProperty("environment"), "CUSTOMER_NAME"),
				CommonVariables.SETTINGS);
		waitForPageLoaded();
		LOGGER.info("Redirected to setting tab");
		WEXSettingPage.verifyElementsOfWEXSettingPage("Shippinglocationtab");
		WEXSettingPage.verifyElementsOfWEXSettingPage("Shippinglocationtab");
		LOGGER.info("Clicked on the shipping location tab");

	}

	@Test(priority = 9, groups = { "REGRESSION_PLATFORMCX" }, description = "TestCaseID:C42455399")
	public final void verifyPreferencesTabOnSettingPageThroughPartner() throws Exception {

		login("PARTNER_EMAIL_WEP", "PARTNER_PASSWORD_WEP");
		WEXSettingPage WEXSettingPage = new WEXSettingPage(PreDefinedActions.getDriver()).getInstance();
		WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
		dashboardPage.leftSideMenuVerification();
		SoftAssert softAssert = new SoftAssert();
		String environment = System.getProperty("environment");
		String companyOrCustomerName;
		if ("US-Stage-WEP".equalsIgnoreCase(environment) || "US-VENEER-WEP".equalsIgnoreCase(environment)) {
			companyOrCustomerName = getEnvironmentSpecificData(environment, "COMPANY_NAME");
		} else {
			companyOrCustomerName = getEnvironmentSpecificData(environment, "CUSTOMER_NAME");
		}
		dashboardPage.partnerWithCompanyView(companyOrCustomerName, CommonVariables.SETTINGS);
		waitForPageLoaded();
		LOGGER.info("Redirected to setting tab");

		WEXSettingPage.verifyElementsOfWEXSettingPage("Preferencestab");
		WEXSettingPage.clickOnElementsOfWEXSettingPage("Preferencestab");
		softAssert.assertTrue(
				WEXSettingPage.matchTextOfWEXSettingPage("ArchivedDevicesheader",
						getTextLanguage(LanguageCode, "daas_ui", "Setting.preference.archivedevices.header")),
				"Archived deviced header is incorrect");
		softAssert
				.assertTrue(
						WEXSettingPage.matchTextOfWEXSettingPage("ArchivedDevicessubheader",
								getTextLanguage(LanguageCode, "daas_ui",
										"Setting.preference.archivedevices.subheader")),
						"Archived deviced subheader is incorrect");
		softAssert
				.assertTrue(
						WEXSettingPage.matchTextOfWEXSettingPage("archivedevicesubtitle",
								getTextLanguage(LanguageCode, "daas_ui",
										"Setting.preference.archivedevices.subtittle")),
						"Archived deviced subtittle is incorrect");
		WEXSettingPage.verifyElementsOfWEXSettingPage("archivedeviceeditbutton");
		WEXSettingPage
				.mouseHoverclickOfWEXSettingPage(WEXSettingPage.getElementOfWEXSettingPage("archivedeviceeditbutton"));
		softAssert
				.assertTrue(
						WEXSettingPage.matchTextOfWEXSettingPage("Archivedevicespopheader",
								getTextLanguage(LanguageCode, "daas_ui",
										"Setting.preference.archivedevicespopup.header")),
						"Archived deviced pop up header is incorrect");
		softAssert
				.assertTrue(
						WEXSettingPage.matchTextOfWEXSettingPage("Archivedevicespopupselectboxlabel",
								getTextLanguage(LanguageCode, "daas_ui",
										"Setting.preference.archivedevicesselectbox.label")),
						"Archived deviced selectbox label is incorrect");
		WEXSettingPage.mouseHoverclickOfWEXSettingPage(
				WEXSettingPage.getElementOfWEXSettingPage("Archivedevicespopupselectbox"));
		WEXSettingPage.selectTextValueFromDropdownOfWEXSettingPage("Archivedevicespopupselectboxvalue",
				Archived_Devices_days, "Archivedevicespopupselectbox");
		WEXSettingPage.verifyElementsOfWEXSettingPage("Archivedevicespopupsavebutton");
		WEXSettingPage.verifyElementsOfWEXSettingPage("Archivedevicespopupcancelbutton");
		WEXSettingPage.clickOnElementsOfWEXSettingPage("Archivedevicespopupsavebutton");
		LOGGER.info("Vaidation is done for archived devices section on preferences tab on setting page");

		WEXSettingPage.verifyElementsOfWEXSettingPage("Authenticationsection");
		WEXSettingPage.clickOnElementsOfWEXSettingPage("Authenticationsection");
		softAssert.assertTrue(
				WEXSettingPage.matchTextOfWEXSettingPage("Authenticationheader",
						getTextLanguage(LanguageCode, "daas_ui", "Setting.preference.Authentication.header")),
				"Authentication header is incorrect");
		softAssert
				.assertTrue(
						WEXSettingPage.matchTextOfWEXSettingPage("Authenticationsubheader",
								getTextLanguage(LanguageCode, "daas_ui",
										"Setting.preference.Authentication.subheader")),
						"Authentication subheader is incorrect");
		WEXSettingPage.verifyElementsOfWEXSettingPage("Authenticationeditbutton");
		Thread.sleep(3000);
		WEXSettingPage
		.mouseHoverclickOfWEXSettingPage(WEXSettingPage.getElementOfWEXSettingPage("Authenticationeditbutton"));
		softAssert
				.assertTrue(
						WEXSettingPage.matchTextOfWEXSettingPage("Authenticationheaderpopup",
								getTextLanguage(LanguageCode, "daas_ui",
										"Setting.preference.Authenticationpopup.header")),
						"Authentication pop up header is incorrect");
		softAssert
				.assertTrue(
						WEXSettingPage.matchTextOfWEXSettingPage("Authenticationsubheaderpopup",
								getTextLanguage(LanguageCode, "daas_ui",
										"Setting.preference.Authenticationpopup.subheader")),
						"Authentication pop up subheader is incorrect");
		softAssert.assertTrue(
				WEXSettingPage.matchTextOfWEXSettingPage("Authenticationselectboxlabel",
						getTextLanguage(LanguageCode, "daas_ui",
								"Setting.preference.Authenticationpopupselectbox.label")),
				"Authentication select box label is incorrect");
		WEXSettingPage.verifyElementsOfWEXSettingPage("Authenticationsubmitbutton");
		WEXSettingPage.verifyElementsOfWEXSettingPage("Authenticationcancelbutton");
		WEXSettingPage.clickOnElementsOfWEXSettingPage("Authenticationsubmitbutton");
		LOGGER.info("Vaidation is done for Authentication section on preferences tab on setting page");

		WEXSettingPage.verifyElementsOfWEXSettingPage("companypinsection");
		WEXSettingPage.clickOnElementsOfWEXSettingPage("companypinsection");
		softAssert.assertTrue(
				WEXSettingPage.matchTextOfWEXSettingPage("companypinheader",
						getTextLanguage(LanguageCode, "daas_ui", "Setting.preference.CompanyPin.header")),
				"CompanyPin header is incorrect");
		softAssert.assertTrue(
				WEXSettingPage.matchTextOfWEXSettingPage("companypinsubheader",
						getTextLanguage(LanguageCode, "daas_ui", "Setting.preference.CompanyPin.subheader")),
				"CompanyPin subheader is incorrect");
		softAssert.assertTrue(
				WEXSettingPage.matchTextOfWEXSettingPage("companypinrowvalidation",
						getTextLanguage(LanguageCode, "daas_ui", "Setting.preference.CompanyPin.header")),
				"CompanyPin row validation is incorrect");
		WEXSettingPage.verifyElementsOfWEXSettingPage("companydeletepinicon");
		WEXSettingPage.verifyElementsOfWEXSettingPage("companyeditpinicon");
		LOGGER.info("Vaidation is done for Company pin section on preferences tab on setting page");

		WEXSettingPage.verifyElementsOfWEXSettingPage("fleetscorepreferencessection");
		WEXSettingPage.clickOnElementsOfWEXSettingPage("fleetscorepreferencessection");
		softAssert
				.assertTrue(
						WEXSettingPage.matchTextOfWEXSettingPage("fleetscorepreferencesheader",
								getTextLanguage(LanguageCode, "daas_ui",
										"Setting.preference.fleetscorepreferences.header")),
						"fleetscorepreferences header is incorrect");
		softAssert.assertTrue(
				WEXSettingPage.matchTextOfWEXSettingPage("fleetscorepreferencesdetails",
						getTextLanguage(LanguageCode, "daas_ui", "Setting.preference.fleetscorepreferences")),
				"fleetscorepreferences subheader is incorrect");
		LOGGER.info("Vaidation is done for Fleet Score Preferences section on preferences tab on setting page");

		WEXSettingPage.verifyElementsOfWEXSettingPage("Devicegroupssection");
		WEXSettingPage.clickOnElementsOfWEXSettingPage("Devicegroupssection");
		softAssert
				.assertTrue(
						WEXSettingPage.matchTextOfWEXSettingPage("Devicegroupheader",
								getTextLanguage(LanguageCode, "daas_ui",
										"Setting.preference.fleetscorepreferences.header")),
						"fleetscorepreferences header is incorrect");
		WEXSettingPage.verifyElementsOfWEXSettingPage("switchtomultilevelbutton");
		LOGGER.info("Vaidation is done for Device groups section on preferences tab on setting page");

		WEXSettingPage.verifyElementsOfWEXSettingPage("customefieldsfordevicessection");
		WEXSettingPage.clickOnElementsOfWEXSettingPage("customefieldsfordevicessection");
		softAssert.assertTrue(
				WEXSettingPage.matchTextOfWEXSettingPage("customfieldsheader",
						getTextLanguage(LanguageCode, "daas_ui", "Setting.preference.customerfields.header")),
				"customerfields header is incorrect");
		softAssert
				.assertTrue(
						WEXSettingPage.matchTextOfWEXSettingPage("customfieldssubheader",
								getTextLanguage(LanguageCode, "daas_ui",
										"Setting.preference.customerfields.subheader")),
						"customerfields sub header is incorrect");
		softAssert
				.assertTrue(
						WEXSettingPage.matchTextOfWEXSettingPage("customefieldsvalidation",
								getTextLanguage(LanguageCode, "daas_ui",
										"Setting.preference.customerfields.Rowvalidation")),
						"customerfields row validation is incorrect");
		LOGGER.info("Vaidation is done for custom fields for devices section on preferences tab on setting page");

		WEXSettingPage.verifyElementsOfWEXSettingPage("lifecyclesection");
		WEXSettingPage.clickOnElementsOfWEXSettingPage("lifecyclesection");
		softAssert.assertTrue(
				WEXSettingPage.matchTextOfWEXSettingPage("lifecycleheader",
						getTextLanguage(LanguageCode, "daas_ui", "Setting.preference.lifecyclestatus.header")),
				"lifecyclestatus header is incorrect");
		softAssert.assertTrue(
				WEXSettingPage.matchTextOfWEXSettingPage("lifecyclerow",
						getTextLanguage(LanguageCode, "daas_ui", "Setting.preference.lifecyclestatus.header")),
				"lifecyclestatus row validation is incorrect");
		LOGGER.info("Vaidation is done for life cycle status section on preferences tab on setting page");

		WEXSettingPage.verifyElementsOfWEXSettingPage("datacollectionsection");
		WEXSettingPage.clickOnElementsOfWEXSettingPage("datacollectionsection");
		softAssert.assertTrue(
				WEXSettingPage.matchTextOfWEXSettingPage("datacollectionheader",
						getTextLanguage(LanguageCode, "daas_ui", "Setting.preference.datacollection.header")),
				"datacollection header is incorrect");
		softAssert
				.assertTrue(
						WEXSettingPage.matchTextOfWEXSettingPage("datacollectionsubheading",
								getTextLanguage(LanguageCode, "daas_ui",
										"Setting.preference.datacollection.subheader")),
						"datacollection sub header is incorrect");
		softAssert.assertTrue(
				WEXSettingPage.matchTextOfWEXSettingPage("companyfirwallrow",
						getTextLanguage(LanguageCode, "daas_ui", "Setting.preference.datacollection.header")),
				"datacollection companyfirwallrow row validation is incorrect");
		softAssert.assertTrue(
				WEXSettingPage.matchTextOfWEXSettingPage("BlueScreenCrashDumpsrowm",
						getTextLanguage(LanguageCode, "daas_ui", "Setting.preference.datacollection.header")),
				"datacollection BlueScreenCrashDumpsr row validation is incorrect");
		softAssert.assertTrue(
				WEXSettingPage.matchTextOfWEXSettingPage("devicehealthrow",
						getTextLanguage(LanguageCode, "daas_ui", "Setting.preference.datacollection.header")),
				"datacollection devicehealth row validation is incorrect");
		softAssert.assertTrue(
				WEXSettingPage.matchTextOfWEXSettingPage("devicelogsrow",
						getTextLanguage(LanguageCode, "daas_ui", "Setting.preference.datacollection.header")),
				"datacollection devicelogs row validation is incorrect");
		softAssert.assertTrue(
				WEXSettingPage.matchTextOfWEXSettingPage("companylocation",
						getTextLanguage(LanguageCode, "daas_ui", "Setting.preference.datacollection.header")),
				"datacollection networkidentifire row validation is incorrect");
		softAssert.assertTrue(
				WEXSettingPage.matchTextOfWEXSettingPage("networkidentifire",
						getTextLanguage(LanguageCode, "daas_ui", "Setting.preference.datacollection.header")),
				"datacollection networkidentifire validation is incorrect");
		softAssert.assertTrue(
				WEXSettingPage.matchTextOfWEXSettingPage("softwareinventory",
						getTextLanguage(LanguageCode, "daas_ui", "Setting.preference.datacollection.header")),
				"datacollection softwareinventory validation is incorrect");
		softAssert.assertTrue(
				WEXSettingPage.matchTextOfWEXSettingPage("softwareinventory",
						getTextLanguage(LanguageCode, "daas_ui", "Setting.preference.datacollection.header")),
				"datacollection softwareinventory validation is incorrect");
		softAssert.assertTrue(
				WEXSettingPage.matchTextOfWEXSettingPage("webapplicationusage",
						getTextLanguage(LanguageCode, "daas_ui", "Setting.preference.datacollection.header")),
				"datacollection webapplicationusage validation is incorrect");
		LOGGER.info("Vaidation is done for Data Collection section on preferences tab on setting page");

		WEXSettingPage.verifyElementsOfWEXSettingPage("domainnamesection");
		WEXSettingPage.clickOnElementsOfWEXSettingPage("domainnamesection");
		softAssert.assertTrue(
				WEXSettingPage.matchTextOfWEXSettingPage("domainnameheader",
						getTextLanguage(LanguageCode, "daas_ui", "Setting.preference.domainname.header")),
				"domian name header is incorrect");
		softAssert.assertTrue(
				WEXSettingPage.matchTextOfWEXSettingPage("domainnamesubheader",
						getTextLanguage(LanguageCode, "daas_ui", "Setting.preference.domainname.subheader")),
				"domain name sub header is incorrect");
		softAssert
				.assertTrue(
						WEXSettingPage.matchTextOfWEXSettingPage("domainnamerow",
								getTextLanguage(LanguageCode, "daas_ui",
										"Setting.preference.domainrow.domainname.row")),
						"domain name row  validation is incorrect");
		LOGGER.info("Vaidation is done for Domaine name section on preferences tab on setting page");

		WEXSettingPage.verifyElementsOfWEXSettingPage("companynetworksection");
		WEXSettingPage.clickOnElementsOfWEXSettingPage("companynetworksection");
		softAssert.assertTrue(
				WEXSettingPage.matchTextOfWEXSettingPage("companynetworkheader",
						getTextLanguage(LanguageCode, "daas_ui", "Setting.preference.companynetwork.header")),
				"company network header is incorrect");
		softAssert
				.assertTrue(
						WEXSettingPage.matchTextOfWEXSettingPage("companynetworksubheader",
								getTextLanguage(LanguageCode, "daas_ui",
										"Setting.preference.companynetwork.subheader")),
						"company network sub header is incorrect");
		softAssert
				.assertTrue(
						WEXSettingPage.matchTextOfWEXSettingPage("companynetworkrow",
								getTextLanguage(LanguageCode, "daas_ui",
										"Setting.preference.domainrow.companynetwork.row")),
						"company network row  validation is incorrect");
		LOGGER.info("Vaidation is done for company network section on preferences tab on setting page");

		WEXSettingPage.verifyElementsOfWEXSettingPage("ThirdPartysoftwareIntegrationsection");
		WEXSettingPage.clickOnElementsOfWEXSettingPage("ThirdPartysoftwareIntegrationsection");
		softAssert.assertTrue(
				WEXSettingPage.matchTextOfWEXSettingPage("thirdpartysoftwareintegrationsection",
						getTextLanguage(LanguageCode, "daas_ui",
								"Setting.preference.thirdpartysoftwareintegrationsection.header")),
				"company network header is incorrect");
		softAssert
				.assertTrue(
						WEXSettingPage.matchTextOfWEXSettingPage("chromebookrow",
								getTextLanguage(LanguageCode, "daas_ui",
										"Setting.preference.datacollection.chromebookrow")),
						"datacollection chromebookrow validation is incorrect");
		softAssert
				.assertTrue(
						WEXSettingPage.matchTextOfWEXSettingPage("companytunerow",
								getTextLanguage(LanguageCode, "daas_ui",
										"Setting.preference.datacollection.companytunerow")),
						"datacollection companytunerow validation is incorrect");
		softAssert
				.assertTrue(
						WEXSettingPage.matchTextOfWEXSettingPage("servicenowrow",
								getTextLanguage(LanguageCode, "daas_ui",
										"Setting.preference.datacollection.servicenowrow")),
						"datacollection servicenowrow validation is incorrect");
		softAssert.assertTrue(
				WEXSettingPage.matchTextOfWEXSettingPage("companyairwatchrow",
						getTextLanguage(LanguageCode, "daas_ui",
								"Setting.preference.datacollection.companyairwatchrow")),
				"datacollection companyairwatchrow validation is incorrect");
		LOGGER.info(
				"Vaidation is done for Third Party Software Integration section on preferences tab on setting page");

		LOGGER.info("Vaidation is done for preferences tab on setting page");

	}

	@Test(priority = 10, groups = { "REGRESSION_PLATFORMCX" }, description = "TestCaseID:C42213602")
	public final void verifyRolesAndPermissionTabOnSettingPageThroughPartner() throws Exception {

		login("PARTNER_EMAIL_WEP", "PARTNER_PASSWORD_WEP");
		WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
		WEXSettingPage WEXSettingPage = new WEXSettingPage(PreDefinedActions.getDriver()).getInstance();
		dashboardPage.leftSideMenuVerification();
		SoftAssert softAssert = new SoftAssert();
		dashboardPage.partnerWithCompanyView(
				getEnvironmentSpecificData(System.getProperty("environment"), "CUSTOMER_NAME"),
				CommonVariables.SETTINGS);
		waitForPageLoaded();
		LOGGER.info("Redirected to setting tab");

		WEXSettingPage.verifyElementsOfWEXSettingPage("rolesandpermissiontab");
		WEXSettingPage.clickOnElementsOfWEXSettingPage("rolesandpermissiontab");
		WEXSettingPage.verifyElementsOfWEXSettingPage("rolesheader");
		WEXSettingPage.verifyElementsOfWEXSettingPage("addrolebutton");
		softAssert.assertTrue(
				WEXSettingPage.matchTextOfWEXSettingPage("rolesheader",
						getTextLanguage(LanguageCode, "daas_ui", "Setting.RolesAndPermission.header")),
				"Roles And Permission header is incorrect");
		LOGGER.info("Vaidation is done for Roles and permission tab on setting page");

	}

	@Test(priority = 11, groups = { "REGRESSION_PLATFORMCX" }, description = "TestCaseID:C42317108")
	public final void verifyAssignedPartnerTabOnSettingPageThroughPartner() throws Exception {

		login("PARTNER_EMAIL_WEP", "PARTNER_PASSWORD_WEP");
		WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
		WEXSettingPage WEXSettingPage = new WEXSettingPage(PreDefinedActions.getDriver()).getInstance();
		dashboardPage.leftSideMenuVerification();
		dashboardPage.partnerWithCompanyView(
				getEnvironmentSpecificData(System.getProperty("environment"), "CUSTOMER_NAME"),
				CommonVariables.SETTINGS);
		SoftAssert softAssert = new SoftAssert();
		waitForPageLoaded();
		LOGGER.info("Redirected to setting tab");

		WEXSettingPage.verifyElementsOfWEXSettingPage("assignedpartnertab");
		WEXSettingPage.clickOnElementsOfWEXSettingPage("assignedpartnertab");
		softAssert.assertTrue(
				WEXSettingPage.matchTextOfWEXSettingPage("assignmentsettingheader",
						getTextLanguage(LanguageCode, "daas_ui", "Setting.AssignedPartner.Assignment setting")),
				"Assigned Partner header is incorrect");
//		softAssert.assertTrue(
//				WEXSettingPage.matchTextOfWEXSettingPage("invitatiossectionheader",
//						getTextLanguage(LanguageCode, "daas_ui", "Setting.AssignedPartner.Assignment setting")),
//				"Invitation partner header is incorrect");
		LOGGER.info("Vaidation is done for Assigned partner tab on setting page");

	}

	@Test(priority = 12, groups = { "REGRESSION_PLATFORMCX" }, description = "TestCaseID:C42317092")
	public final void verifyNotificationTabOnSettingPageThroughpartner() throws Exception {

		login("PARTNER_EMAIL_WEP", "PARTNER_PASSWORD_WEP");
		WEXDashboardPage dashboardPage = new WEXDashboardPage(PreDefinedActions.getDriver()).getInstance();
		WEXSettingPage WEXSettingPage = new WEXSettingPage(PreDefinedActions.getDriver()).getInstance();
		dashboardPage.leftSideMenuVerification();
		dashboardPage.partnerWithCompanyView(
				getEnvironmentSpecificData(System.getProperty("environment"), "CUSTOMER_NAME"),
				CommonVariables.SETTINGS);
		SoftAssert softAssert = new SoftAssert();
		waitForPageLoaded();
		LOGGER.info("Redirected to setting tab");

		WEXSettingPage.verifyElementsOfWEXSettingPage("Notificationtab");
		WEXSettingPage.clickOnElementsOfWEXSettingPage("Notificationtab");
		softAssert
				.assertTrue(
						WEXSettingPage.matchTextOfWEXSettingPage("devicenotificationheader",
								getTextLanguage(LanguageCode, "daas_ui",
										"Setting.Notification. devicenotificationeader")),
						"Device notification header is incorrect");
		softAssert
				.assertTrue(
						WEXSettingPage.matchTextOfWEXSettingPage("devicenotificationsubheader",
								getTextLanguage(LanguageCode, "daas_ui",
										"Setting.Notification. devicenotificationeader")),
						"Device notification header is incorrect");
		softAssert
				.assertTrue(
						WEXSettingPage.matchTextOfWEXSettingPage("devicenotificationheader",
								getTextLanguage(LanguageCode, "daas_ui",
										"Setting.Notification. devicenotificationeader")),
						"Device notification header is incorrect");
		LOGGER.info("Vaidation is done for Notification tab on setting page");
	}
	
	@Test(priority = 13, groups = {"PRODUCTION_PLATFORMCX" }, description = "TestCaseID:C42273810")
	public final void verifyTabsOnSettng() throws Exception {
 
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		WEXSettingPage WEXSettingPage = new WEXSettingPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		
		leftSideMenuVerification();
		WEXSettingPage.companyView(CommonVariables.SETTINGS);
		sleeper(3000);
		LOGGER.info("Redirected to setting tab");
		
		WEXSettingPage.verifyElementsOfWEXSettingPage("Shippinglocationtab");
		WEXSettingPage.verifyElementsOfWEXSettingPage("Shippinglocationtab");
		softAssert.assertTrue(WEXSettingPage.matchTextOfWEXSettingPage("Shippinglocationheader",getTextLanguage(LanguageCode, "daas_ui","Setting..Companyinformation.perferedtimezone")),"perferedtimezone is incorrect");

		if(!toggleVerification(WEPPulsesCreationPageVariables.WX_NAVIGATION_IMPROVEMENT_TOGGLE, getCredentials(environment, "ITADMIN_EMAIL_WEP"))) {
			WEXSettingPage.verifyElementsOfWEXSettingPage("perferencestaboldmenu");
			WEXSettingPage.clickOnElementsOfWEXSettingPage("perferencestaboldmenu");
		}
		else {
			WEXSettingPage.verifyElementsOfWEXSettingPage("Preferencestab");
			WEXSettingPage.clickOnElementsOfWEXSettingPage("Preferencestab");
		}

		softAssert.assertTrue(WEXSettingPage.matchTextOfWEXSettingPage("ArchivedDevicesheader",getTextLanguage(LanguageCode, "daas_ui", "Setting.preference.archivedevices.header")),"Archived deviced header is incorrect");
		
		WEXSettingPage.verifyElementsOfWEXSettingPage("rolesandpermissiontab");
		WEXSettingPage.clickOnElementsOfWEXSettingPage("rolesandpermissiontab");
		WEXSettingPage.verifyElementsOfWEXSettingPage("addrolebutton");

		WEXSettingPage.verifyElementsOfWEXSettingPage("Notificationtab");
		WEXSettingPage.clickOnElementsOfWEXSettingPage("Notificationtab");
		softAssert.assertTrue(WEXSettingPage.matchTextOfWEXSettingPage("devicenotificationheader",getTextLanguage(LanguageCode, "daas_ui","Setting.Notification. devicenotificationeader")),"Device notification header is incorrect");
		
		LOGGER.info("Vaidation is done for all the tab on setting page");
	}
	
}
