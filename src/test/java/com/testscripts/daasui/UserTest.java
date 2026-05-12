//"<!DOCTYPE suite SYSTEM "http://testng.org/testng-1.0.dtd" >"
package com.testscripts.daasui;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

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
import com.basesource.utils.Mailinator;
import com.daasui.constants.CommonVariables;
import com.daasui.constants.ConstantURL;
import com.daasui.constants.DashboardVariables;
import com.daasui.constants.PreferenceVariables;
import com.daasui.constants.UserVariables;
import com.daasui.pages.CompaniesDetailsPage;
import com.daasui.pages.CompaniesPage;
import com.daasui.pages.DeviceListPage;
import com.daasui.pages.EMMToolPage;
import com.daasui.pages.LogPage;
import com.daasui.pages.PreferencesPage;
import com.daasui.pages.SettingsPage;
import com.daasui.pages.TableConfigurationPage;
import com.daasui.pages.UserDetailsPage;
import com.daasui.pages.UserPage;

public class UserTest extends CommonTest {
	private static Logger LOGGER = LogManager.getLogger(UserTest.class);

	public int activePageNumber, firstPageNumber, lastPageNumber, selectedOption, totalCount;
	int notificationCountUnread = 0;
	public boolean previousButtonStatus, nextButtonStatus;
	ArrayList<String> userRowListInfo = new ArrayList<String>();
	public static String importCompanyLogs = getEnvironmentSpecificData(System.getProperty("environment"), "USER_IMPORT_COMPANY_LOGS");
	public static String importCompany = getEnvironmentSpecificData(System.getProperty("environment"), "USER_IMPORT_COMPANY");
	public static String azureUsers = getEnvironmentSpecificData(System.getProperty("environment"), "AZURE_USERS");
	public static String authCompany = getEnvironmentSpecificData(System.getProperty("environment"), "AUTH_COMPANY_API_DO_NOT_DELETE");
	public static String authenticationCompany = getEnvironmentSpecificData(System.getProperty("environment"), "AUTHENTICATION_COMPANY_DO_NOT_DELETE");
	public static String authTenantIDUser = getEnvironmentSpecificData(System.getProperty("environment"), "AUTH_COMPANY_TENANT_ID");
	public static String authTenantRootIDUser = getEnvironmentSpecificData(System.getProperty("environment"), "AUTH_COMPANY_ROOT_TENANT_ID");
	public static String userFileValidData = "userValid.csv";
	public static String userFileInValidData = "userInValid.csv";
	public static String userFileInValidBlank = "userInValidBlank.csv";
	public static String importCompanyNameLog = getEnvironmentSpecificData(System.getProperty("environment"), "USER_IMPORT_COMPANY_LOGS");
	public static String importCompanyName = getEnvironmentSpecificData(System.getProperty("environment"), "USER_IMPORT_COMPANY");
	public static String cataLystURL = getEnvironmentSpecificData(System.getProperty("environment"), "DAAS_API_BASE_URL_AUTH");
	public static String testUser = getEnvironmentSpecificData(System.getProperty("environment"), "TEST_USER");
	public static String emmAdminCompanyId = getEnvironmentSpecificData(System.getProperty("environment"), "EMM_ADMIN_COMPANY_TENANT_ID");
	public static String authTenantIDLDPUser = getEnvironmentSpecificData(System.getProperty("environment"), "AUTH_COMPANY_TENANT_ID_LDP");

	@DataProvider
	public Object[][] AuthenticationLoginData() {
		Object[][] data = new Object[1][2];
		data[0][0] = "MSP_AZURE";
		data[0][1] = "MSP_AZURE_PASSWORD";
		return data;
	}

	@DataProvider
	public Object[][] AuthenticationLoginData2() {
		Object[][] data = new Object[2][2];
		data[0][0] = "MSP_AZURE";
		data[0][1] = "MSP_AZURE_PASSWORD";
		data[1][0] = "PARTNER_AZURE";
		data[1][1] = "PARTNER_AZURE_PASSWORD";
		return data;
	}

	/**
	 * This method will verify title of user page
	 * 
	 * @throws Exception
	 */
	@Test(priority = 1, groups = { "REGRESSION", "REGRESSION_CI", "SMOKE", "ALL_CI", "ALL" }, description = "Test Case: 280862")
	public final void verifyUserTitle() throws Exception {
		login("COMPANY_EMAIL", "COMPANY_PASSWORD");
		gotoUserTab();
		UserPage userPage = new UserPage(PreDefinedActions.getDriver()).getInstance();

		if (toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, "MSP_ADMIN_US_EMAIl"))) {
			userPage.waitForElementsOfUserPage("userTitleOnBreadcrumbs");
			Assert.assertTrue(userPage.matchTextOfUserPage("userTitleOnBreadcrumbs", getTextLanguage(LanguageCode, "daas_ui", "pagetitle.users")), " User title doesn't match");
			LOGGER.info("User title is matched");
		}else{
		Assert.assertTrue(userPage.matchTextOfUserPage("userTitle", getTextLanguage(LanguageCode, "daas_ui", "pagetitle.users")), " User title doesn't match");
		LOGGER.info("User title is matched");
		}
	}

	/**
	 * This method basically checks all filter functionality on user list page.
	 * 
	 * @throws Exception
	 */
	@Test(priority = 2, groups = { "REGRESSION", "REGRESSION_CI", "STABILIZATION_STAGING" }, description = "TestCase:199074, 199100, 200456, 200561")
	public final void verifyFilterFunctionalityOnUserListPage() throws Exception {

		SoftAssert sa = new SoftAssert();
		login("NEW_MSP_ADMIN_US_EMAIl", "NEW_MSP_ADMIN_US_PASSWORD");
		gotoUserTab();
		UserPage userPage = new UserPage(PreDefinedActions.getDriver()).getInstance();

		waitForPageLoaded();
		resetTableConfiguration();
		userPage.waitForElementsOfUserPage("tableOverlay");

		// Test Case 1 - This test case validates if the filter functionality is working properly for the searchbox on Name column
		sa.assertTrue(userPage.verifySearchValueOnUserPage(LanguageCode, "userNameSearchBox", UserVariables.USER_LIST_PAGE_FAKE_TEXT_SEARCH, "noUserDisplayText", "userNameSearchList"), "Search not working for Name field");
		userPage.clearAllFiltersOfUsersListPage();
		LOGGER.info("Verified filter functionality for searchbox on Name column.");

		/*sa.assertTrue(userPage.verifySearchValueOnUserPage(LanguageCode, "userNameSearchBox", UserVariables.USER_LIST_PAGE_NAME_SEARCH, "noUserDisplayText", "userNameSearchList"), "Search not working for Name field");
		userPage.clearAllFiltersOfUsersListPage();
		LOGGER.info("Verified filter functionality for searhbox on Name column with User names");

		// Test Case 2 - This test case validates if the filter functionality is working properly for the searchbox on Email column
		sa.assertTrue(userPage.verifySearchValueOnUserPage(LanguageCode, "userEmailSearchBox", UserVariables.USER_LIST_PAGE_FAKE_TEXT_SEARCH, "noUserDisplayText", "userEmailSearchList"), "Search not working for Email field");
		userPage.clearAllFiltersOfUsersListPage();
		LOGGER.info("Verified filter functionality for searhbox on Email column with fake emails");

		sa.assertTrue(userPage.verifySearchValueOnUserPage(LanguageCode, "userEmailSearchBox", UserVariables.USER_LIST_PAGE_EMAIL_SEARCH, "noUserDisplayText", "userEmailSearchList"), "Search not working for Email field");
		userPage.clearAllFiltersOfUsersListPage();
		LOGGER.info("Verified filter functionality for searhbox on Email column with Email");*/

		// Test Case 3 - This test case validates if the filter functionality is working properly for the dropdown on roles column
		userPage.scrollOnUserPage("userRolesBoxTitle");
		userPage.waitForElementsOfUserPage("userRolesBox");
		userPage.waitForElementsOfUserPage("dropdownRolesCheckboxes");
		userPage.clickOnElementsOfUserPage("dropdownRolesCheckboxes");
		sa.assertTrue(userPage.verifyFilterMultiSelectOnUserPage(LanguageCode, "dropdownRolesElementListLabels", "dropdownRolesElementListLabels", "userRolesList", "noUserDisplayText"), "Listbox not working for Roles dropdown");
		userPage.waitForElementsOfUserPage("clearRolesField");
		userPage.clickByJavaScriptOnElementsOfUserPage("outsideElement");
		userPage.clickByJavaScriptOnElementsOfUserPage("clearRolesField");
		LOGGER.info("Verified filter functionality for roles column");

		// Test Case 4 - This test case validates if the filter functionality is working properly for the dropdown on company box column
//		userPage.scrollOnUserPage("companyBoxTitle");
		userPage.waitForElementsOfUserPage("userCompanyBox");
		userPage.clickOnElementsOfUserPage("companyBoxBefore");
		userPage.waitForElementsOfUserPage("companySearchBox");
		sa.assertTrue(userPage.verifySingleSelectionFilterFunctionalityFromDynamicDropdownOnUserPage(LanguageCode, "companySearchBox", UserVariables.USER_LIST_PAGE_COMPANY_SEARCH, "noElementsDisplayTextForComboBoxColumn", "companyListOnPopup", "companyList", "noUserDisplayText"), "Filter functionality on selecting single option from company column on User list page is not working");
		if (userPage.verifyElementsOfUserPage("clearCompanyField"))
			userPage.clickOnElementsOfUserPage("clearCompanyField");
		userPage.waitForElementsOfUserPageForinvisibile("tableOverlay");
		userPage.scrollOnUserPage("companyBoxTitle");
		userPage.clickOnElementsOfUserPage("companyBoxBefore");
		userPage.waitForElementsOfUserPage("companySearchBox");
		sa.assertTrue(userPage.verifyMultiSelectionFilterFunctionalityFromDynamicDropdownOnUserPage(LanguageCode, "companySearchBox", UserVariables.USER_LIST_PAGE_COMPANY_SEARCH, "noElementsDisplayTextForComboBoxColumn", "companyListOnPopup", "companyList", "noUserDisplayText"), "Filter functionality on selecting multiple options from company column on User list page is not working");
		userPage.clearAllFiltersOfUsersListPage();
		LOGGER.info("Verified filter functionality for company column");

		/*// Test Case 5 - This test case validates if the filter functionality is working properly for the dropdown on Status column
		userPage.scrollOnUserPage("StatusBoxTitle");
		userPage.waitForElementsOfUserPage("userStatusBox");
		userPage.waitForElementsOfUserPage("dropdownStatusCheckboxes");
		userPage.clickOnElementsOfUserPage("dropdownStatusCheckboxes");
		sa.assertTrue(userPage.verifyFilterMultiSelectOnUserPage(LanguageCode, "dropdownStatusElementListLabels", "dropdownStatusElementListLabels", "userStatusList", "noUserDisplayText"), "Listbox not working for Status dropdown");
		userPage.waitForElementsOfUserPage("clearStatusField");
		userPage.clickOnElementsOfUserPage("clearStatusField");
		userPage.clearAllFiltersOfUsersListPage();
		LOGGER.info("Verified filter functionality for status column");

		// Test Case 6 - This test case validates if the filter functionality is working properly for the searchbox on MobilePhone column
		sa.assertTrue(userPage.verifySearchValueOnUserPage(LanguageCode, "userMobilePhoneSearchBox", UserVariables.USER_LIST_PAGE_FAKE_NUMBER_SEARCH, "noUserDisplayText", "userMobilePhoneSearchList"), "Search not working for MobilePhone field");
		userPage.clearAllFiltersOfUsersListPage();
		LOGGER.info("Verified filter functionality for searhbox on MobilePhone column with fake MobilePhone number.");

		sa.assertTrue(userPage.verifySearchValueOnUserPage(LanguageCode, "userMobilePhoneSearchBox", UserVariables.USER_LIST_PAGE_MOBILE_PHONE_SEARCH, "noUserDisplayText", "userMobilePhoneSearchList"), "Search not working for MobilePhone field");
		userPage.clearAllFiltersOfUsersListPage();
		LOGGER.info("Verified filter functionality for searhbox on MobilePhone column with MobilePhone number.");
8
		// Test Case 7 - This test case validates if the filter functionality is working properly for the searchbox on OfficePhone column
		sa.assertTrue(userPage.verifySearchValueOnUserPage(LanguageCode, "userOfficePhoneSearchBox", UserVariables.USER_LIST_PAGE_FAKE_NUMBER_SEARCH, "noUserDisplayText", "userOfficePhoneSearchList"), "Search not working for OfficePhone field");
		userPage.clearAllFiltersOfUsersListPage();
		LOGGER.info("Verified filter functionality for searhbox on OfficePhone column with fake OfficePhone number.");

		sa.assertTrue(userPage.verifySearchValueOnUserPage(LanguageCode, "userOfficePhoneSearchBox", UserVariables.USER_LIST_PAGE_OFFICE_PHONE_SEARCH, "noUserDisplayText", "userOfficePhoneSearchList"), "Search not working for OfficePhone field");
		userPage.clearAllFiltersOfUsersListPage();
		LOGGER.info("Verified filter functionality for searhbox on OfficePhone column with OfficePhone number.");

		// Test Case 8 - This test case validates if the filter functionality is working properly for the searchbox on Title column
		sa.assertTrue(userPage.verifySearchValueOnUserPage(LanguageCode, "userTitleSearchBox", UserVariables.USER_LIST_PAGE_FAKE_TEXT_SEARCH, "noUserDisplayText", "userTitleSearchList"), "Search not working for Title field");
		userPage.clearAllFiltersOfUsersListPage();
		LOGGER.info("Verified filter functionality for searhbox on Title column with fake Title.");

		sa.assertTrue(userPage.verifySearchValueOnUserPage(LanguageCode, "userTitleSearchBox", UserVariables.USER_LIST_PAGE_TITLE_SEARCH, "noUserDisplayText", "userTitleSearchList"), "Search not working for Title field");
		userPage.clearAllFiltersOfUsersListPage();
		LOGGER.info("Verified filter functionality for searhbox on Title column with Title.");

		// Test Case 8 - This test case validates if the filter functionality is working properly for the searchbox on Address column
		sa.assertTrue(userPage.verifySearchValueOnUserPage(LanguageCode, "userAddressSearchBox", UserVariables.USER_LIST_PAGE_FAKE_TEXT_SEARCH, "noUserDisplayText", "userAddressSearchList"), "Search not working for Address field");
		userPage.clearAllFiltersOfUsersListPage();
		LOGGER.info("Verified filter functionality for searhbox on Address column with fake Address.");

		sa.assertTrue(userPage.verifySearchValueOnUserPage(LanguageCode, "userAddressSearchBox", UserVariables.USER_LIST_PAGE_ADDRESS_SEARCH, "noUserDisplayText", "userAddressSearchList"), "Search not working for Address field");
		userPage.clearAllFiltersOfUsersListPage();
		LOGGER.info("Verified filter functionality for searhbox on Address column with Address.");

		// Test Case 9 - This test case validates if the filter functionality is working properly for the searchbox on Language column
		sa.assertTrue(userPage.verifySearchValueOnUserPage(LanguageCode, "userLanguageSearchBox", UserVariables.USER_LIST_PAGE_FAKE_TEXT_SEARCH, "noUserDisplayText", "userLanguageSearchList"), "Search not working for Language field");
		userPage.clearAllFiltersOfUsersListPage();
		LOGGER.info("Verified filter functionality for searhbox on Language column with fake Language.");

		sa.assertTrue(userPage.verifySearchValueOnUserPage(LanguageCode, "userLanguageSearchBox", UserVariables.USER_LIST_PAGE_LANGUAGE_SEARCH, "noUserDisplayText", "userLanguageSearchList"), "Search not working for Language field");
		userPage.clearAllFiltersOfUsersListPage();
		LOGGER.info("Verified filter functionality for searhbox on Language column with Language.");

		// Test Case 10 - This test case validates if the filter functionality is working properly for the dropdown on Number Of Devices column
		userPage.scrollOnUserPage("numberOfDevicesBoxTitle");
		userPage.waitForElementsOfUserPage("numberOfDevicesBox");
		userPage.waitForElementsOfUserPage("dropdownNumberOfDevicesCheckboxes");
		userPage.clickOnElementsOfUserPage("dropdownNumberOfDevicesCheckboxes");
		waitForPageLoaded();
		userPage.verifyFilterSingleSelectOnUserPage(LanguageCode, "dropdownNumberOfDevicesElementListLabels", "dropdownNumberOfDevicesElementListLabels", "numberOfDevicesList", "noUserDisplayText");
		userPage.waitForElementsOfUserPage("clearNumberOfDevicesField");
		userPage.clickOnElementsOfUserPage("clearNumberOfDevicesField");
		waitForPageLoaded();
		userPage.clearAllFiltersOfUsersListPage();
		LOGGER.info("Verified filter functionality for Number Of Devices column");*/
		
		sa.assertAll();
		LOGGER.info("All filter functionality test-cases passed on Users page");

	}

	/**
	 * @throws Exception
	 */
	@Test(priority = 3, groups = { "REGRESSION", "REGRESSION_CI" }, description = "TestCase:224061,224042,224104,224729,225189,442981")
	public final void verifyEMMAdminRoleForChromebookAndEMMConfiguration() throws Exception {
		login("MSP_EMM_TEAM_USERNAME", "MSP_EMM_TEAM_PASSWORD");
		UserPage userPage = new UserPage(PreDefinedActions.getDriver()).getInstance();
		UserDetailsPage userDetailsPage = new UserDetailsPage(PreDefinedActions.getDriver()).getInstance();
		PreferencesPage preferencesPage = new PreferencesPage(PreDefinedActions.getDriver()).getInstance();
		EMMToolPage emmToolPage = new EMMToolPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();

		try {
			// Testcase 1 : verify non-emm configured tenant with emmAdmin role user
			gotoUserTab();
			resetTableConfiguration();
			if (!userPage.verifyElementsOfUserPage("clearAllFilterField")) {
				userPage.waitForElementsOfUserPage("userEmailSearchBox");
				userPage.enterTextForUserPage("userEmailSearchBox", getCredentials(environment, "EMM_NOT_CONFIGURED_USERNAME"));
				sleeper(4000);// takes time to search user email in emaillist
				if (userPage.getTextOfUserPage("userEmailSearchList").equalsIgnoreCase(getCredentials(environment, "EMM_NOT_CONFIGURED_USERNAME"))) {
					userPage.waitForElementsOfUserPage("firstRowUser");
					userPage.clickByJavaScriptOnElementsOfUserPage("firstRowUser");
				}
			}
			Assert.assertTrue(userPage.setEMMAdminUserRole(LanguageCode), "Failed to assign emm admin role");
			sleeper(2000);// Due to inconsistent UI response we have to put wait here.(Tried many ways but this is the final solution which works as expected.)
			logout();
			login("EMM_NOT_CONFIGURED_USERNAME", "EMM_NOT_CONFIGURED_PASSWORD");
			sleeper(4000);
			preferencesPage.clickOnElementsOfPreferencesPage("preferenceTab");
			if (userDetailsPage.getTextOfUserDetailsPage("chromeNotConfiguredString").equals(userDetailsPage.getTextLanguage(LanguageCode, "lhserver", "settings.preferences.emm.not_configured")) && userDetailsPage.getTextOfUserDetailsPage("emmNotConfiguredString").equals(userDetailsPage.getTextLanguage(LanguageCode, "lhserver", "settings.preferences.emm.not_configured"))) {
				userDetailsPage.verifyElementsOfUserDetailsPage("help&SupportTab");
				userDetailsPage.verifyElementsOfUserDetailsPage("settingsTab");
				LOGGER.info("Help&Support and Settings tab are displayed for emm admin role user tenant when no emm configured");
			} else {
				LOGGER.error("Unable to verify tabs for emm admin role user tenant when no emm configured");
			}
			logout();

			login("EMM_CHROMEBOOK_AW/IN_CONFIGURED_USERNAME", "EMM_CHROMEBOOK_AW/IN_CONFIGURED_PASSWORD");

			LOGGER.info("Logged in with EMM Admin user role tenant");
			// Remove Airwatch Configuration with checkobox
			preferencesPage.clickOnElementsOfPreferencesPage("preferenceTab");
			Assert.assertTrue(preferencesPage.removeEMMIntegrationUsingAPI(environment,PreferenceVariables.EMM_AIRWATCH,"airwatchIntegrationDeleteButton",emmAdminCompanyId), "Error occured while removing airwatch on Prefernce Page");

			// Remove Intune Configuration with checkobox
			preferencesPage.clickOnElementsOfPreferencesPage("preferenceTab");
			Assert.assertTrue(preferencesPage.removeEMMIntegrationUsingAPI(environment,PreferenceVariables.EMM_INTUNE, "intuneIntegrationDeleteButton",emmAdminCompanyId), "Error occured while removing microsoft intune on Prefernce Page");

		
			// Configure Airwatch
			LOGGER.info("Configuring Airwatch");
			sleeper(3000);
			preferencesPage.clickOnElementsOfPreferencesPage("preferenceTab");
			preferencesPage.clickOnElementsOfPreferencesPage("emmEditbutton");
			preferencesPage.clickOnElementsOfPreferencesPage("airwatchRadioButton");
			preferencesPage.enterValuesOfUrlKeyGroupId(PreferenceVariables.AIRWATCH_URL_EMMADMIN, PreferenceVariables.AIRWATCH_KEY_EMMADMIN, PreferenceVariables.AIRWATCH_GROUP_ID_EMMADMIN);
			Assert.assertTrue(preferencesPage.configureAirwatchUsingBasicAuthentication(PreferenceVariables.AIRWATCH_USERNAME_EMMADMIN, PreferenceVariables.AIRWATCH_PASSWORD_EMMADMIN), "Unable to configure airwatch configration using basic auth");
			Assert.assertTrue(preferencesPage.verifyAirwatchConfigurationWithSucessMessage(), "Airwatch Configuration Failed with success message");
			LOGGER.info("Airwatch Configured Successfully");
			refreshPage();

			// Configure Intune
			LOGGER.info("Configuring Intune");
			sleeper(5000);
			preferencesPage.clickOnElementsOfPreferencesPage("preferenceTab");
			preferencesPage.verifyElementsOfPreferencesPage("intuneEditButton");
			preferencesPage.clickOnElementsOfPreferencesPage("intuneEditButton");
			preferencesPage.clickOnElementsOfPreferencesPage("intuneRadioButton");
			Assert.assertTrue(preferencesPage.verifyIntuneConfiguration(PreferenceVariables.INTUNE_DOMAIN_NAME_EMMADMIN, PreferenceVariables.INTUNE_USERNAME_EMMADMIN, PreferenceVariables.INTUNE_PASSWORD_EMMADMIN), "Intune Configuration is failed");
			LOGGER.info("Intune Configured Successfully");
			refreshPage();
			sleeper(2000);

			// Verify Emm tool tab for Airwatch and Intune
			softAssert.assertTrue(emmToolPage.getTextOfEMMToolPage("emmToolTab").equals(emmToolPage.getTextLanguage(LanguageCode, "daas_ui", "sidemenu.emmTools ")), "EMM TOOL tab doesn't match");
			LOGGER.info("EMM TOOL tab matched");
			gotoEMMToolTab();
			softAssert.assertTrue(emmToolPage.verifyElementsOfEMMToolPage("airwatchIntuneTab"), "Microsoft Intune tab doesn't match");
			LOGGER.info("Microsoft Intune tab matched");
			softAssert.assertTrue(userPage.verifyEMMToolRedirectingEMMPortal(PreferenceVariables.INTUNE), "Airwatch Redirection failed");
			softAssert.assertTrue(userPage.verifyEMMToolRedirectingEMMPortal(PreferenceVariables.AIRWATCH), "Airwatch Redirection failed");
			refreshPage();
			sleeper(8000);
			LOGGER.info("Succesfully verified EMM Admin user validation for Airwatch,Intune & Chromebook Integration");

		} finally {
			logout();
			login("EMM_CHROMEBOOK_AW/IN_CONFIGURED_USERNAME", "EMM_CHROMEBOOK_AW/IN_CONFIGURED_PASSWORD");

			// Remove Airwatch Configuration with checkobox
			preferencesPage.waitForElementsOfPreferencesPage("preferenceTab");
			preferencesPage.clickOnElementsOfPreferencesPage("preferenceTab");
			Assert.assertTrue(preferencesPage.removeEMMIntegrationUsingAPI(environment,PreferenceVariables.EMM_AIRWATCH,"airwatchIntegrationDeleteButton",emmAdminCompanyId), "Error occured while removing airwatch on Prefernce Page");

			// Remove Intune Configuration with checkobox
			preferencesPage.clickOnElementsOfPreferencesPage("preferenceTab");
			Assert.assertTrue(preferencesPage.removeEMMIntegrationUsingAPI(environment,PreferenceVariables.EMM_INTUNE, "intuneIntegrationDeleteButton",emmAdminCompanyId), "Error occured while removing microsoft intune on Prefernce Page");
			logout();

		}
	}

	/**
	 * This function basically checks pagination functionality on user list page..
	 * 
	 * @throws Exception
	 */
	@Test(priority = 4, groups = { "REGRESSION", "REGRESSION_CI", "STABILIZATION_STAGING" }, description = "TestCase:199795")
	public final void verifyPaginationOnUsersListPage() {
		try {
			SoftAssert sa = new SoftAssert();
			login("NEW_MSP_ADMIN_US_EMAIl", "NEW_MSP_ADMIN_US_PASSWORD");
			gotoUserTab();
			UserPage userPage = new UserPage(PreDefinedActions.getDriver()).getInstance();

			waitForPageLoaded();
			resetTableConfiguration();
			userPage.waitForElementsOfUserPage("tableOverlay");
			userPage.waitForElementsOfUserPage("paginationDropdownMenu");
			sa.assertTrue(userPage.verifyElementIsEnableOfUserPage("paginationDropdownMenu"), "Pagination Dropdown not available");
			sa.assertTrue(userPage.verifyElementIsClickableOfUserPage("paginationDropdownMenu"), "Pagination Dropdown not clickable");
			sa.assertTrue(userPage.waitForElementsOfUserPage("navigationItemDiv"), "Navigation items are not available");
			getPaginationInfo();
			LOGGER.info("get Pagination Information ");
			sa.assertTrue(userPage.verifyElementIsEnableOfUserPage("navigationItemActivePage"), "The active page navigation link is not disabled");
			sa.assertTrue(userPage.verifyTotalPageCountChangeAsSelectedOption(selectedOption, totalCount, lastPageNumber), "Total page count does not change with selected option");
			if (!verifySelectedOptionForNewSelection(selectedOption, CommonVariables.SELETEDFIFTY)) {
				if (changeSelectedOption(totalCount, CommonVariables.SELETEDTWENTYFIVE)) {
					userPage.selectElementFromDropDownOfUserPage("paginationDropdownMenu", "paginationDropdownOptionList", Integer.toString(CommonVariables.SELETEDTWENTYFIVE));
					LOGGER.info("Change selected option as " + CommonVariables.SELETEDTWENTYFIVE);
					userPage.waitForElementsOfUserPage("tableOverlay");
					getPaginationInfo();
					sa.assertTrue(userPage.verifyTotalPageCountChangeAsSelectedOption(selectedOption, totalCount, lastPageNumber), "Total page count does not change with selected option");
					sa.assertTrue(userPage.verifyButtonStatusofPagination(activePageNumber, firstPageNumber, lastPageNumber, totalCount, previousButtonStatus, nextButtonStatus), "Button visibility status is not as per expectation");
				} else {
					LOGGER.info("Selected user has less than " + CommonVariables.SELETEDTWENTYFIVE + " records");
				}
			} else {
				if (changeSelectedOption(totalCount, CommonVariables.SELETEDFIFTY)) {
					userPage.selectElementFromDropDownOfUserPage("paginationDropdownMenu", "paginationDropdownOptionList", Integer.toString(CommonVariables.SELETEDFIFTY));
					LOGGER.info("Change Selected option as " + CommonVariables.SELETEDFIFTY);
					userPage.waitForElementsOfUserPage("tableOverlay");
					getPaginationInfo();
					sa.assertTrue(userPage.verifyTotalPageCountChangeAsSelectedOption(selectedOption, totalCount, lastPageNumber), "Total page count does not change with selected option");
					sa.assertTrue(userPage.verifyButtonStatusofPagination(activePageNumber, firstPageNumber, lastPageNumber, totalCount, previousButtonStatus, nextButtonStatus), "Button visibility status is not as per expectation");
				} else {
					LOGGER.info("Selected user has less than " + CommonVariables.SELETEDFIFTY + " records");
				}
			}
			userPage.waitForElementsOfUserPage("tableOverlay");
			if (userPage.canClickNext(activePageNumber, lastPageNumber, nextButtonStatus)) {
				sa.assertTrue(userPage.getButtonEnabilityStatus("navigationItemNext"), "Navigation next button is not enable");
				userPage.waitForElementsOfUserPage("navigationItemNext");
				userPage.clickOnElementsOfUserPage("navigationItemNext");
				userPage.waitForElementsOfUserPage("tableOverlay");
				getPaginationInfo();
				userPage.waitForElementsOfUserPage("navigationItemPrevious");
				if (userPage.canClickPrevious(activePageNumber, firstPageNumber, previousButtonStatus)) {
					sa.assertTrue(userPage.getButtonEnabilityStatus("navigationItemPrevious"), "Navigation previous button is not enabled");
					userPage.clickOnElementsOfUserPage("navigationItemPrevious");
				} else {
					LOGGER.info("Previous button is disabled");
				}
			} else if (userPage.canClickPrevious(activePageNumber, firstPageNumber, previousButtonStatus)) {
				sa.assertTrue(userPage.getButtonEnabilityStatus("navigationItemPrevious"), "Navigation previous button is not enabled");
				userPage.waitForElementsOfUserPage("navigationItemPrevious");
				userPage.clickOnElementsOfUserPage("navigationItemPrevious");
				userPage.waitForElementsOfUserPage("tableOverlay");
				getPaginationInfo();
				userPage.waitForElementsOfUserPage("navigationItemNext");
				if (userPage.canClickNext(activePageNumber, lastPageNumber, nextButtonStatus)) {
					sa.assertTrue(userPage.getButtonEnabilityStatus("navigationItemNext"), "Navigation next button is not enabled");
					userPage.clickOnElementsOfUserPage("navigationItemNext");
				} else {
					LOGGER.info("Next button is disabled");
				}
			} else {
				LOGGER.info("Previous and Next button both are disabled on Product Catalog Page ");
			}
		} catch (Exception e) {
			Assert.fail("Exception occured in method verifyPaginationOnUsersListPage " + e.getMessage());
		}
	}

	/**
	 * This test case validates details tile present on user details page
	 * 
	 * @throws Exception
	 */
	@Test(priority = 5, groups = { "REGRESSION", "REGRESSION_CI" }, description = "TestCase:207893,249095,249096")
	public final void verifyProfileTileOnUserDetailsPage() throws Exception {
		String jobTitle = null;
		String language = null;
		SoftAssert sa = new SoftAssert();
		login("NEW_MSP_ADMIN_US_EMAIl", "NEW_MSP_ADMIN_US_PASSWORD");
		UserPage userPage = new UserPage(PreDefinedActions.getDriver()).getInstance();
		UserDetailsPage userDetailsPage = new UserDetailsPage(PreDefinedActions.getDriver()).getInstance();
		//userPage.waitForElementsOfUserPage("tableOverlay");
		gotoUserTab();
		LOGGER.info("Redirected to user page");

		resetTableConfiguration();
		if (!userPage.verifyElementsOfUserPage("clearAllFilterField")) {
			userPage.waitForElementsOfUserPage("userEmailSearchBox");
			userPage.enterTextForUserPage("userEmailSearchBox", testUser);
			sleeper(4000);
		}
		userPage.scrollTillViewUserPage("titleBoxTitle");
		userPage.scrollTillViewUserPage("numberOfDevicesBoxTitle");
		sleeper(1000);

		userRowListInfo = userPage.getUserInfo();
		userPage.clickOnElementsOfUserPage("firstRowUser");
		LOGGER.info("Clicked on first user in the list");
		LOGGER.info("Redirected to user details page");
		userDetailsPage.waitUntillElementIsPresentOfUserDetailsPage("userTitleOnDetailsPage");
		waitForPageLoaded();
		// Test Case 1 - This test case validates user name on details page
		userDetailsPage.waitUntillElementIsPresentOfUserDetailsPage("detailsTitle");
		sa.assertTrue(userDetailsPage.getTextOfUserDetailsPage("detailsTitle").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "global.details.tab.tile.profile_title")), "Profile tiles for user is not present on details tile");
		sa.assertTrue(userDetailsPage.getTextOfUserDetailsPage("nameLabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "users.list.label.name")), "Name field for user is not present on details tile");
		sa.assertTrue(userDetailsPage.getTextOfUserDetailsPage("nameValue").equalsIgnoreCase(userRowListInfo.get(0)), "Name value of user on details page does not match with list page");	

		// Test Case 2 - This test case validates user email on details page
		userDetailsPage.scrollOnUserPage("emailValue");
		sa.assertTrue(userDetailsPage.getTextOfUserDetailsPage("emailLabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "users.details.email")), "Email field for user is not present on details tile");
		sa.assertTrue(userDetailsPage.getTextOfUserDetailsPage("emailValue").equalsIgnoreCase(userRowListInfo.get(1)), "Email value of user on details page does not match with list page");

		// Test Case 4 - This test case validates user company on details page
		userDetailsPage.scrollOnUserPage("companyValue");
		sa.assertTrue(userDetailsPage.getTextOfUserDetailsPage("companyLabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "users.detail.label.company")), "Company field for user is not present on details tile");
		sleeper(2000);
		sa.assertTrue(userDetailsPage.getTextOfUserDetailsPage("companyValue").equalsIgnoreCase(userRowListInfo.get(3)), "Company value of user on details page does not match with list page");	

		// Test Case 5 - This test case validates user mobile Phone on details page
		userDetailsPage.scrollOnUserPage("mobilePhoneValue");
		sa.assertTrue(userDetailsPage.getTextOfUserDetailsPage("mobilePhoneLabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "users.details.mobile_phone")), "Mobile Phone field for user is not present on details tile");
		sa.assertTrue(userDetailsPage.getTextOfUserDetailsPage("mobilePhoneValue").equalsIgnoreCase(userRowListInfo.get(4)), "Mobile Phone value of user on details page does not match with list page");
	
		// Test Case 6 - This test case validates user office Phone on details page
		userDetailsPage.scrollOnUserPage("officePhoneValue");
		sa.assertTrue(userDetailsPage.getTextOfUserDetailsPage("officePhoneLabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "users.details.office_phone")), "Office Phone field for user is not present on details tile");
		sa.assertTrue(userDetailsPage.getTextOfUserDetailsPage("officePhoneValue").equalsIgnoreCase( userRowListInfo.get(7)), "Office Phone value of user on details page does not match with list page");

		// Test Case 7 - This test case validates user Title on details page
		userDetailsPage.scrollOnUserPage("jobTitleValue");
		sa.assertTrue(userDetailsPage.getTextOfUserDetailsPage("jobTitleLabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "users.list.label.title")), "Title field for user is not present on details tile");
		sleeper(2000);
		sa.assertTrue(userDetailsPage.getTextOfUserDetailsPage("jobTitleValue").equalsIgnoreCase(userRowListInfo.get(8)), "Title value of user on details page does not match with list page");
		
		userDetailsPage.clickOnElementsOfUserDetailsPage("jobTitleEdit");
		LOGGER.info("Clicked on edit title button");

		userDetailsPage.clickOnElementsOfUserDetailsPage("cancelButtonOnPopup");
		LOGGER.info("Clicked at cancel button on job title popup");
		userDetailsPage.clickOnElementsOfUserDetailsPage("jobTitleEdit");
		sa.assertTrue(userDetailsPage.matchTextOfUserDetailsPage("titleModelHeader", getTextLanguage(LanguageCode, "daas_ui", "users.details.job_title")), "Model Title text is not matched on details tile popup");
		userDetailsPage.clearTextRefreshFromUserDetailsPageTextField("jobTitleTextKey");
		userDetailsPage.enterTextForUserDetailsPage("jobTitleTextKey", UserVariables.JOB_TITLE);
		userDetailsPage.clickOnElementsOfUserDetailsPage("SaveButtonOnPopup");
		userDetailsPage.waitUntilElementIsInvisibleOfUserDetailsPage("toastNotification");
		Thread.sleep(4000);
		sa.assertTrue(userDetailsPage.matchTextOfUserDetailsPage("jobTitleValue", UserVariables.JOB_TITLE), "Save functionality on user title popup is not working");

		// Test Case 6 - This test case validates user Address on details page:- Bug 247597: [AUX][DaaS] Address should be same on company list and company details page.
		// sa.assertTrue(userDetailsPage.matchTextOfUserDetailsPage("addressLabel", getTextLanguage(LanguageCode, "daas_ui", "users.list.label.address")), "Address field for user
		// is not
		// present on details tile");
		// sa.assertTrue(userDetailsPage.getTextOfUserDetailsPage("addressValue").contains(userRowListInfo.get(8)), "Address value of user on details page does not match with list
		// page");

		// Test Case 7 - This test case validates user Language on details page
		userDetailsPage.scrollOnUserPage("languageValue");
		sa.assertTrue(userDetailsPage.getTextOfUserDetailsPage("languageLabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "users.details.language")), "Model Language text is not matched on details tile popup");
		sa.assertTrue(userDetailsPage.getTextOfUserDetailsPage("languageValue").equalsIgnoreCase(userRowListInfo.get(10)), "Language value of user on details page does not match with list page");

		userDetailsPage.clickOnElementsOfUserDetailsPage("languageEditButton");
		LOGGER.info("Clicked on edit language button");
		userDetailsPage.clickOnElementsOfUserDetailsPage("cancelButtonOnPopup");
		LOGGER.info("Clicked at cancle button on language popup");
		userDetailsPage.clickOnElementsOfUserDetailsPage("languageEditButton");
		sa.assertTrue(userDetailsPage.getTextOfUserDetailsPage("languageModelHeader").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "users.details.language")), "Language text for user is not present on details tile popup");
		userDetailsPage.clickOnElementsOfUserDetailsPage("selectArrowKey");
		userDetailsPage.waitForElementsOfUserDetailsPage("selectLanguage");
		userDetailsPage.clickOnElementsOfUserDetailsPage("selectLanguage");
		userDetailsPage.clickOnElementsOfUserDetailsPage("SaveButtonOnPopup");
		userDetailsPage.waitUntilElementIsInvisibleOfUserDetailsPage("toastNotification");
		Thread.sleep(4000);
		language = userDetailsPage.getTextOfUserDetailsPage("languageValue");

		// Test Case 8 - This test case validates if the changed jobTitle and language is also updated on the user list page
		userDetailsPage.scrollOnUserPage("jobTitleValue");
		jobTitle = userDetailsPage.getTextOfUserDetailsPage("jobTitleValue");
		navigateToBack();
		waitForPageLoaded();
		LOGGER.info("Navigated to User list page");
		userPage.scrollOnUserPage("userTitleSearchList");
		sa.assertTrue(userPage.getTextOfUserPage("userTitleSearchList").equalsIgnoreCase(jobTitle), "JobTitle not updated successfully on User list page");
		//userPage.waitForElementsOfUserPage("tableOverlay");
		userPage.scrollOnUserPage("userLanguageSearchList");
		waitForPageLoaded();
		sleeper(3000);
		sa.assertTrue(userPage.getTextOfUserPage("userLanguageSearchList").equalsIgnoreCase(language), "language not updated successfully on User list page");
		sa.assertAll();
		LOGGER.info("Verification of user list details on details tile of users details page done successfully");
	}

	/**
	 * This test case validates identity tile present on user details page
	 * 
	 * @throws Exception
	 */
	@Test(priority = 6, groups = { "REGRESSION", "REGRESSION_CI", "STABILIZATION_STAGING" }, description = "TestCase:254240, 246104")
	public final void verifyIdentityTileOnUserDetailsPage() throws Exception {
		SoftAssert sa = new SoftAssert();
		login("NEW_MSP_ADMIN_US_EMAIl", "NEW_MSP_ADMIN_US_PASSWORD");
		UserPage userPage = new UserPage(PreDefinedActions.getDriver()).getInstance();
		UserDetailsPage userDetailsPage = new UserDetailsPage(PreDefinedActions.getDriver()).getInstance();
		//userPage.waitForElementsOfUserPage("tableOverlay");
		gotoUserTab();
		LOGGER.info("Redirected to user page");
		resetTableConfiguration();
		//userPage.waitForElementsOfUserPage("tableOverlay");

		//userPage.enterTextForUserPage("userNameSearchBox", getEnvironmentSpecificData(System.getProperty("environment"), "IDENTITY_USER"));
		//userPage.waitForElementsOfUserPage("tableOverlay");
		userPage.waitForElementsOfUserPageForinvisibile("tableOverlay");
		sleeper(1000);

		if (!toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getEnvironmentSpecificData(System.getProperty("environment"), "IDENTITY_USER"))) {
		userPage.waitForElementsOfUserPage("userRolesList");
		userRowListInfo = userPage.getUserInfo();
		userPage.clickByJavaScriptOnUserPage("firstRowUser");
		userDetailsPage.waitForElementsOfUserPageForinvisibile("tableOverlay");
		LOGGER.info("Clicked on first user in the list");
		LOGGER.info("Redirected to user details page");
		waitForPageLoaded();
		userDetailsPage.waitUntillElementIsPresentOfUserDetailsPage("userTitleOnDetailsPage");

		sa.assertTrue(userDetailsPage.matchTextOfUserDetailsPage("userTitleOnDetailsPage", getTextLanguage(LanguageCode, "daas_ui", "breadcrumbs.user_details")), "Title on user details page is incorrect");
		sa.assertTrue(userDetailsPage.matchTextOfUserDetailsPage("breadcrumbText", getTextLanguage(LanguageCode, "daas_ui", "breadcrumbs.user_details")), "Text on breadcrumb on user details page is incorrect");
		sa.assertTrue(userDetailsPage.verifyElementsOfUserDetailsPage("breadcrumbLink"), "Link on breadcrumb of user details page is missing");
		LOGGER.info("Verified breadcrumb on user details page");

		Assert.assertTrue(userDetailsPage.getTextOfUserDetailsPage("userNameIdentity").equalsIgnoreCase(userRowListInfo.get(2).toUpperCase()), "Name of user on identity tile of user details page is incorrect");
		Assert.assertTrue(userDetailsPage.getTextOfUserDetailsPage("userEmailIdentity").equals(userRowListInfo.get(1)), "User email on identity tile of user details page is incorrect");
		Assert.assertTrue(userDetailsPage.getTextOfUserDetailsPage("userPhoneIdentity").equalsIgnoreCase(userRowListInfo.get(7)), "User Phone identity tile of user details page is incorrect");
		LOGGER.info("verified user details on identity tile");
		sa.assertTrue(userDetailsPage.getTextOfUserDetailsPage("userAccountType").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "users.details.account_type.daas.hpid")), "HP Account text does not match");	
		sa.assertTrue(userDetailsPage.getTextOfUserDetailsPage("userStatusType").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "global.active")), "Status of HP Account text does not match");	
		}
		else{
			userPage.waitForElementsOfUserPage("userRolesList");
			userRowListInfo = userPage.getUserInfo();
			userPage.clickByJavaScriptOnUserPage("firstRowUser");
			userDetailsPage.waitForElementsOfUserDetailsPage("tableOverlay");
			LOGGER.info("Clicked on first user in the list");
			LOGGER.info("Redirected to user details page");
			waitForPageLoaded();
			userDetailsPage.waitUntillElementIsPresentOfUserDetailsPage("userTitleOnBreadcrumbs");

			sa.assertTrue(userDetailsPage.matchTextOfUserDetailsPage("userTitleOnBreadcrumbs", getTextLanguage(LanguageCode, "daas_ui", "breadcrumbs.user_details")), "Title on user details page is incorrect");
			sa.assertTrue(userDetailsPage.matchTextOfUserDetailsPage("breadcrumbText", getTextLanguage(LanguageCode, "daas_ui", "breadcrumbs.user_details")), "Text on breadcrumb on user details page is ");
			sa.assertTrue(userDetailsPage.verifyElementsOfUserDetailsPage("breadcrumbLink"), "Link on breadcrumb of user details page is missing");
			LOGGER.info("Verified breadcrumb on user details page");

			sa.assertTrue(userDetailsPage.getTextOfUserDetailsPage("userNameIdentity").equalsIgnoreCase(userRowListInfo.get(2)), "Name of user on identity tile of user details page is incorrect");
			Assert.assertTrue(userDetailsPage.getTextOfUserDetailsPage("userEmailIdentity").equals(userRowListInfo.get(0)), "User email on identity tile of user details page is incorrect");
			sa.assertTrue(userDetailsPage.getTextOfUserDetailsPage("userPhoneIdentity").contains(userRowListInfo.get(7)), "User Phone identity tile of user details page is incorrect");
			LOGGER.info("verified user details on identity tile");
			sa.assertTrue(userDetailsPage.getTextOfUserDetailsPage("userAccountType").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "users.details.account_type.daas.hpid")), "HP Account text does not match");	
			sa.assertTrue(userDetailsPage.getTextOfUserDetailsPage("userStatusType").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "global.active")), "Status of HP Account text does not match");	
		}
		LOGGER.info("verified user account type details on identity tile");
		sa.assertAll();
		LOGGER.info("All testcases of verifyIdentityTileOnUserDetailsPage passed successfully");
	}

	/**
	 * This test case validates role tile present on user details page
	 * @throws Exception
	 */
	@Test(priority = 7, groups = { "REGRESSION", "REGRESSION_CI", "STABILIZATION_STAGING" }, enabled = false , description = "TestCase:207630")
	public final void verifyRoleAssignmentTileOnUserDetailsPage() throws Exception {
		login("NEW_MSP_ADMIN_US_EMAIl", "NEW_MSP_ADMIN_US_PASSWORD");
		boolean roleStatusOnListPage = true;
		boolean roleStatusOnDetailsPage = true;
		SoftAssert sa = new SoftAssert();
		UserPage userPage = new UserPage(PreDefinedActions.getDriver()).getInstance();
		UserDetailsPage userDetailsPage = new UserDetailsPage(PreDefinedActions.getDriver()).getInstance();

		waitForPageLoaded();
		userPage.waitForElementsOfUserPage("tableOverlay");

		gotoUserTab();
		waitForPageLoaded();
		LOGGER.info("Redirected to user page");
		resetTableConfiguration();
		userPage.waitForElementsOfUserPage("tableOverlay");

		userPage.enterTextForUserPage("userNameSearchBox", getEnvironmentSpecificData(System.getProperty("environment"), "ROLE_USER"));
		userPage.waitForElementsOfUserPage("tableOverlay");
		userPage.waitForElementsOfUserPageForinvisibile("tableOverlay");
		userPage.waitForElementsOfUserPage("userRolesList");
		userRowListInfo = userPage.getUserInfo();
		userPage.clickByJavaScriptOnUserPage("firstRowUser");
		LOGGER.info("Clicked on first user in the list");
		LOGGER.info("Redirected to user details page");
		waitForPageLoaded();
		userDetailsPage.waitForElementsOfUserDetailsPage("tableOverlay");

		userDetailsPage.waitUntillElementIsPresentOfUserDetailsPage("breadcrumbText");
		userDetailsPage.scrollOnUserPage("userEmailIdentity");
		userDetailsPage.waitUntillElementIsPresentOfUserDetailsPage("userTitleOnDetailsPage");
		sa.assertTrue(userDetailsPage.matchTextOfUserDetailsPage("userRoleTitle", getTextLanguage(LanguageCode, "daas_ui", "users.details.role_assignment").toUpperCase()), "User role assignment title on user details page is not matched");
		sa.assertTrue(userDetailsPage.matchTextOfUserDetailsPage("userRoleAssignmentDescription", getTextLanguage(LanguageCode, "daas_ui", "users.details.section.role_assignment_description")), "User role assignment description on user details page is not matched");
		sa.assertTrue(userDetailsPage.matchTextOfUserDetailsPage("userRoleLabel", getTextLanguage(LanguageCode, "lhserver", "activerecord.attributes.user.roles")), "User role label value on user details page is not matched");
	
		// Verify Roles available on list page are available on details page
		waitForPageLoaded();
		userDetailsPage.waitForElementsOfUserDetailsPage("userRoleValue");
		String[] roleListOnUserPage = userRowListInfo.get(2).toUpperCase().replaceAll(",", " /").split(" /");
		String roleListOnUserDetailsPage = userDetailsPage.getTextOfUserDetailsPage("userRoleValue").toUpperCase();
		for(int i =0; i< roleListOnUserPage.length; i++){
			if(!roleListOnUserDetailsPage.contains(roleListOnUserPage[i])){
				LOGGER.info(roleListOnUserPage[i] + " is not available in role List On User Details Page");
				roleStatusOnListPage = false;
			}
			else
				LOGGER.info(roleListOnUserPage[i] + " is available in role List On User Details Page");
		}
		sa.assertTrue(roleStatusOnListPage, "User role value on user details page is not matched");
		waitForPageLoaded();
		userDetailsPage.clickByJavaScriptOnUserDetailsPage("userRoleEditButton");
		sa.assertTrue(userDetailsPage.matchTextOfUserDetailsPage("roleModelPopupTitle", getTextLanguage(LanguageCode, "lhserver", "users.details.roles.modal.title")), "User role Model label value on user details page is not matched");
		userDetailsPage.roleCheckboxSelectionOfUserDetailsPage();
		userDetailsPage.clickOnElementsOfUserDetailsPage("roleCancelButtonOnPopup");
		LOGGER.info("Click cancel button on user roles details popup model");

		userDetailsPage.clickByJavaScriptOnUserDetailsPage("userRoleEditButton");
		sa.assertTrue(userDetailsPage.matchTextOfUserDetailsPage("roleModelPopupTitle", getTextLanguage(LanguageCode, "lhserver", "users.details.roles.modal.title")), "User role Model label value on user details page is not matched");
		userDetailsPage.roleCheckboxSelectionOfUserDetailsPage();
		userDetailsPage.clickOnElementsOfUserDetailsPage("roleSaveButtonOnPopup");
		LOGGER.info("Click save button on user roles details popup model");

		// Validates if the roles changed on details page and updated on the user list page.
		String[] updatedRolesValueOnDetailsPage = userDetailsPage.getTextOfUserDetailsPage("userRoleValue").toUpperCase().split(" / ");
		navigateToBack();
		waitForPageLoaded();
		LOGGER.info("Navigated to User list page");
		userPage.enterTextForUserPage("userNameSearchBox", getEnvironmentSpecificData(System.getProperty("environment"), "ROLE_USER"));
		userPage.waitForElementsOfUserPage("tableOverlay");
		userPage.waitForElementsOfUserPage("userRolesList");
	
		String updatedRolesValueOnUserPage = userPage.getTextOfUserPage("userRolesList").replaceAll(",", "/").toUpperCase();
		for(int i =0; i< updatedRolesValueOnDetailsPage.length; i++){
			if(!updatedRolesValueOnUserPage.contains(updatedRolesValueOnDetailsPage[i])){
				LOGGER.info(updatedRolesValueOnDetailsPage[i] + " is not available in role List On User Details Page");
				roleStatusOnDetailsPage = false;
			}
			else
				LOGGER.info(updatedRolesValueOnDetailsPage[i] + "is available in role List On User Details Page");
		}
		sa.assertTrue(roleStatusOnDetailsPage, "Roles did not update successfully on User list page");

		LOGGER.info("verified user roles on User list page");
		sa.assertAll();
		LOGGER.info("All testcases of verifyUserDetailsRoleTile passed successfully");
	}

	/**
	 * @throws Exception
	 */
	@Test(priority = 8, groups = { "REGRESSION", "REGRESSION_CI", "STABILIZATION_STAGING" })
	public final void verifyAssignedDevicesTileOnUsersDetailsPage() throws Exception {
		String deviceName = null;
		int deviceCount = 0;
		int devicesCountOnAssignedDevicesTile = 0;
		SoftAssert sa = new SoftAssert();
		login("NEW_MSP_ADMIN_US_EMAIl", "NEW_MSP_ADMIN_US_PASSWORD");
		gotoUserTab();
		LOGGER.info("Redirected to user page");
		UserPage userPage = new UserPage(PreDefinedActions.getDriver()).getInstance();
		UserDetailsPage userDetailsPage = new UserDetailsPage(PreDefinedActions.getDriver()).getInstance();
		resetTableConfiguration();
		waitForPageLoaded();
		userPage.enterTextForUserPage("userEmailSearchBox", getCredentials(environment, "NUMBER_OF_DEVICES_USER"));
		waitForPageLoaded();
		userPage.waitForElementsOfUserPage("userRolesList");
		waitForPageLoaded();
		userRowListInfo = userPage.getUserInfo();
		waitForPageLoaded();
		userPage.scrollOnUserPage("numberOfDevicesBoxTitle");
		int numberOfDevices = Integer.parseInt(userPage.getTextOfUserPage("numberOfDevicesCount"));
		userPage.clickByJavaScriptOnUserPage("firstRowUser");
		LOGGER.info("Clicked on first user in the list");
		LOGGER.info("Redirected to user details page");
		waitForPageLoaded();
		userDetailsPage.waitUntillElementIsPresentOfUserDetailsPage("breadcrumbText");
		userDetailsPage.waitUntillElementIsPresentOfUserDetailsPage("userTitleOnDetailsPage");
		userDetailsPage.clickByJavaScriptOnUserDetailsPage("usersDeviceTab");
		// Validate the Users Device tile
		userDetailsPage.waitForElementsOfUserDetailsPage("usersDeviceTileHeaderMessage");
		sa.assertTrue(userDetailsPage.getTextOfUserDetailsPage("usersDeviceTileHeaderMessage").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "users.details.devices.title")), "User Devices title on user details page is not matched");
		sa.assertTrue(userDetailsPage.getTextOfUserDetailsPage("usersDeviceSerialNumber").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "assets.table.heading.serialNumber")), "Serial Number column does not mathch at User Devices details page");
		sa.assertTrue(userDetailsPage.getTextOfUserDetailsPage("usersDeviceName").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "users.details.devices.name")), "Name column does not mathch at User Devices details page");
		sa.assertTrue(userDetailsPage.getTextOfUserDetailsPage("usersDeviceType").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "users.details.devices.type")), "Type column does not mathch at User Devices details page");
		sa.assertTrue(userDetailsPage.getTextOfUserDetailsPage("usersDeviceApplicationEnrolled").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "assets.table.heading.enrolled")), "Enrolled column does not mathch at User Devices details page");
		sa.assertTrue(userDetailsPage.getTextOfUserDetailsPage("usersDeviceLastActivity").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "users.list.label.status")), "Last Activity column does not mathch at User Devices details page");
		if(userDetailsPage.verifyElementsOfUserDetailsPage("userDevicesStatus")){
		for(int i=0; i<userDetailsPage.getElementsOfUserDetailsPage("userDevicesStatus").size();i++){
		if(!userDetailsPage.getElementsOfUserDetailsPage("userDevicesStatus").get(i).getText().equals(getTextLanguage(LanguageCode, "daas_ui", "global.inactive"))){
			deviceCount++;}
		}
			devicesCountOnAssignedDevicesTile = Integer.parseInt(userDetailsPage.getTextOfUserDetailsPage("totalDevicesOnAssignedDevicesTile").split(" ")[1]);
		deviceName = userDetailsPage.getTextOfUserDetailsPage("usersDeviceNameLinkKey");
		userDetailsPage.clickByJavaScriptOnUserDetailsPage("usersDeviceNameLinkKey");
		userDetailsPage.waitUntilElementIsInvisibleOfUserDetailsPage("userDetailsPageSpinner");
		waitForPageLoaded();
		userDetailsPage.waitUntillElementIsPresent("deviceNameVerificationOnDetailsPageKey");
		sa.assertTrue(userDetailsPage.matchTextOfUserDetailsPage("deviceNameVerificationOnDetailsPageKey", deviceName), "Device Name does not match on device details page.");
		}
		sa.assertTrue((numberOfDevices==deviceCount || numberOfDevices == devicesCountOnAssignedDevicesTile), "Number of devices count assigned to user does not match");
		sa.assertAll();
		LOGGER.info("All testcases of verifyUsersDevicesTileOnDetailsPage passed successfully");
	}

	/**
	 * This method will verify the table configuration test cases of user list page
	 * 
	 * @throws Exception
	 */
	@Test(priority = 9, groups = { "REGRESSION", "REGRESSION_CI" }, description = "TestCase:[US 208608]")
	public final void verifyTableConfigurationTestCases() throws Exception {
		login("NEW_MSP_ADMIN_US_EMAIl", "NEW_MSP_ADMIN_US_PASSWORD");
		String tenantID = getValueFromToken("tenant");
		gotoUserTab();
		waitForPageLoaded();
		// verifying search service api
		Assert.assertTrue(getStatusCode(getSearchServiceApi(environment) + CommonVariables.DETAILSSEARCHSERVICEAPI + tenantID + CommonVariables.DETAILSSEARCHSERVICEAPI2, UserVariables.SEARCHSERVICEBODY, "POST", getEnvironment(environment)) == CommonVariables.CODEOK, "Search service failed for Support Team list page");
		ArrayList<String> columnName = new ArrayList<String>(Arrays.asList(getTextLanguage(LanguageCode, "daas_ui", "users.list.label.name"), getTextLanguage(LanguageCode, "daas_ui", "users.list.label.email"), getTextLanguage(LanguageCode, "daas_ui", "users.list.label.roles"), getTextLanguage(LanguageCode, "daas_ui", "users.list.label.company"), getTextLanguage(LanguageCode, "daas_ui", "users.list.label.status"), getTextLanguage(LanguageCode, "daas_ui", "users.list.label.mobile_phone"),
				getTextLanguage(LanguageCode, "daas_ui", "users.list.label.office_phone"), getTextLanguage(LanguageCode, "daas_ui", "users.list.label.title"), getTextLanguage(LanguageCode, "daas_ui", "users.list.label.address"), getTextLanguage(LanguageCode, "daas_ui", "users.list.label.language"), getTextLanguage(LanguageCode, "daas_ui", "companies.list.number_of_devices")));
		ArrayList<String> checkboxValue = new ArrayList<String>(Arrays.asList("true", "true", "true", "true", "true", "false", "false", "false", "false", "false", "false"));
		ArrayList<String> email = new ArrayList<String>(Arrays.asList(getTextLanguage(LanguageCode, "daas_ui", "users.list.label.email")));
		verifyTableConfigurationTests(columnName, checkboxValue, email);
	}

	/**
	 * @throws Exception
	 */
	@Test(priority = 10, groups = { "REGRESSION", "REGRESSION_CI", "STABILIZATION_STAGING","PENTEST" }, description = "TestCase:250280,199758")
	public final void verifyAddUserManually() throws Exception {
		login("NEW_MSP_ADMIN_US_EMAIl", "NEW_MSP_ADMIN_US_PASSWORD");
		SoftAssert sa = new SoftAssert();
		String expectedMailContent;
		String tenantIDPartner,apiurl;
		UserPage userPage = new UserPage(PreDefinedActions.getDriver()).getInstance();
		if(userPage.verifyElementsOfUserPage("userTab"))//For pentest we have only company users so need to check visibility of menu tabs
			gotoUserCompanyUserTab();
		else
			gotoUserTab();
		LOGGER.info("Navigated to Users Tab.");
		tenantIDPartner = getValueFromToken("tenant");
		apiurl = getEnvironmentSpecificData(System.getProperty("environment"), "DAAS_API_BASE_URL") + CommonVariables.DETAILSSEARCHSERVICEAPI + tenantIDPartner + CommonVariables.DETAILSSEARCHSERVICEAPI2;
		String tenantID = getTenantDetails(apiurl, CommonVariables.SEARCH_SERVICE_IDMTENANTS, importCompany);
		String body = UserVariables.BODY1 + tenantID + UserVariables.BODY2;
		sa.assertTrue(userPage.removeAllUsers(environment, tenantID, body, getEnvironmentSpecificData(System.getProperty("environment"), "DAAS_API_BASE_URL_AUTH")), "User removal failed");

		HashMap<String, String> companyChangeInfo = userPage.getCompanyChangeDetails();
		HashMap<String, String> getAddUserManuallyInfo = userPage.getAddUserManuallyDetails();

		Assert.assertTrue(userPage.selectCompanyOfUserPage(companyChangeInfo, importCompany, LanguageCode), "Company selection failed while adding user.");
		userPage.clickOnElementsOfUserPage("previousButton");
		// Verify Add Users title on Add user pop up
		sa.assertTrue(userPage.matchTextOfUserPage("addUserPopUpTitle", getTextLanguage(LanguageCode, "daas_ui", "users.list.company_selection.title")), "Title on Add user popup is incorrect");
		// Verify choose company message on Add user pop up
		sa.assertTrue(userPage.matchTextOfUserPage("chooseCompanyLabel", getTextLanguage(LanguageCode, "daas_ui", "users.list.company_selection.heading")), "Message on Add user popup is incorrect");
		userPage.clickOnElementsOfUserPage("nextButton");
		Assert.assertTrue(userPage.verifyAddManuallyUsers(getAddUserManuallyInfo, LanguageCode, 3), "Adding user manually has failed.");

		resetTableConfiguration();
		Assert.assertTrue(userPage.verifyUsersOnListPage(getAddUserManuallyInfo), "Users on List page did not get reflected correctly.");

		sa.assertAll();
		LOGGER.info("Validation of Add User manually completed successfully.");
	}

	/**
	 * @throws Exception
	 */
	@Test(priority = 11, groups = { "REGRESSION", "REGRESSION_CI", "STABILIZATION_STAGING" }, description = "TestCase:248678, Failing due to Bug 330068: [AUX][DaaS] Getting 404 while adding users")
	public final void verifyResendInvitation() throws Exception {
		login("NEW_MSP_ADMIN_US_EMAIl", "NEW_MSP_ADMIN_US_PASSWORD");
		UserPage userPage = new UserPage(PreDefinedActions.getDriver()).getInstance();
		gotoUserTab();
		LOGGER.info("Navigated to Users Tab.");

		HashMap<String, String> getAddUserManuallyInfo = userPage.getAddUserManuallyDetails();
		resetTableConfiguration();

		String tenantID = getValueFromToken("tenant");
		String apiurl = getEnvironmentSpecificData(System.getProperty("environment"), "DAAS_API_BASE_URL")+ CommonVariables.DETAILSSEARCHSERVICEAPI + tenantID + CommonVariables.DETAILSSEARCHSERVICEAPI2;
		String tenantIdCompany = getTenantDetails(apiurl,CommonVariables.SEARCH_SERVICE_IDMTENANTS,importCompany);
		String body = UserVariables.BODY1 + tenantIdCompany + UserVariables.BODY2;
		Assert.assertTrue(userPage.removeAllUsers(environment, tenantIdCompany, body, getEnvironmentSpecificData(System.getProperty("environment"), "DAAS_API_BASE_URL_AUTH")), "User removal failed");

		Assert.assertTrue(userPage.addUsersApi(environment, getEnvironment(environment),tenantIdCompany), "Add User API got failed.");

		Assert.assertTrue(userPage.verifySearchEmail(), "Added user email is not available on user list page.");

		Assert.assertTrue(userPage.verifyResendInvitation(getAddUserManuallyInfo, LanguageCode, "Seluser"), "Unable to Resend Invitations.");
		LOGGER.info("Validation of Resend Invitation completed successfully.");
	}

	/**
	 * @throws Exception
	 */
	@Test(priority = 12, groups = { "REGRESSION", "REGRESSION_CI", "STABILIZATION_STAGING" }, enabled =false, description = "TestCase:206843")
	public final void verifyAddUserAzureAD() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login("NEW_MSP_ADMIN_US_EMAIl", "NEW_MSP_ADMIN_US_PASSWORD");
		gotoUserTab();
		LOGGER.info("Navigated to Users Tab.");
		UserPage userPage = new UserPage(PreDefinedActions.getDriver()).getInstance();

		HashMap<String, String> companyChangeInfo = userPage.getCompanyChangeDetails();
		HashMap<String, String> getAddUserManuallyInfo = userPage.getAddUserManuallyDetails();
		HashMap<String, String> getAzureImportInfo = userPage.getAzureImportDetails();

		try {
			notificationCountUnread = userPage.preNotificationCheck();
		} catch (Exception e) {
			LOGGER.error("Exception occured in getting count of unread notification : " + e.getMessage());
		}
		resetTableConfiguration();

		String tenantID = getValueFromToken("tenant");
		String apiurl = cataLystURL+ CommonVariables.DETAILSSEARCHSERVICEAPI+tenantID+CommonVariables.DETAILSSEARCHSERVICEAPI2;
		String tenantIdCompany = getTenantDetails(apiurl,CommonVariables.SEARCH_SERVICE_IDMTENANTS,importCompanyName);
		String body = UserVariables.BODY1 + tenantIdCompany + UserVariables.BODY2;
		Assert.assertTrue(userPage.removeAllUsers(environment, tenantIdCompany, body, getEnvironment(environment)), "User removal failed");
		
		Assert.assertTrue(userPage.selectCompanyOfUserPage(companyChangeInfo, importCompany, LanguageCode), "Company selection failed while adding device.");
		userPage.clickOnElementsOfUserPage("previousButton");
		// Verify Add Users title on Add user pop up
		softAssert.assertTrue(userPage.matchTextOfUserPage("addUserPopUpTitle", getTextLanguage(LanguageCode, "daas_ui", "users.list.company_selection.title")), "Title on Add user popup is incorrect");
		// Verify choose company message on Add user pop up
		softAssert.assertTrue(userPage.matchTextOfUserPage("chooseCompanyLabel", getTextLanguage(LanguageCode, "daas_ui", "users.list.company_selection.heading")), "Message on Add user popup is incorrect");
		userPage.clickOnElementsOfUserPage("nextButton");

		Assert.assertTrue(userPage.verifyAzureImport(getAzureImportInfo, 4, notificationCountUnread), "Users from AZURE AD did not get imported successfully. ");

		resetTableConfiguration();

		Assert.assertTrue(userPage.verifyAzureUsersOnListPage(getAddUserManuallyInfo, azureUsers), "Users on List page did not get reflected correctly after Azure Import.");
		Assert.assertTrue(userPage.removeAllUsers(environment, tenantIdCompany, body, getEnvironment(environment)), "User removal failed");

		softAssert.assertAll();
		LOGGER.info("Validation of Azure Import completed successfully.");
	}

	/**
	 * @throws Exception
	 */
	@Test(priority = 13, groups = { "REGRESSION", "REGRESSION_CI", "STABILIZATION_STAGING" }, description = "TestCase:270045,201233")
	public final void verifyUserImportSuccess() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login("NEW_MSP_ADMIN_US_EMAIl", "NEW_MSP_ADMIN_US_PASSWORD");
		waitForPageLoaded();
		gotoUserTab();
		LOGGER.info("Navigated to Users Tab.");
		UserPage userPage = new UserPage(PreDefinedActions.getDriver()).getInstance();
		TableConfigurationPage tableConfigurationPage = new TableConfigurationPage(PreDefinedActions.getDriver()).getInstance();
		HashMap<String, String> companyChangeInfo = userPage.getCompanyChangeDetails();
		HashMap<String, String> getAddUserManuallyInfo = userPage.getAddUserManuallyDetails();
		HashMap<String, String> getImportInfo = userPage.getImportDetails();
		String tenantID = getValueFromToken("tenant");
		String apiurl = cataLystURL+ CommonVariables.DETAILSSEARCHSERVICEAPI+tenantID+CommonVariables.DETAILSSEARCHSERVICEAPI2;
		String tenantIdCompany = getTenantDetails(apiurl,CommonVariables.SEARCH_SERVICE_IDMTENANTS,importCompany);
		String body = UserVariables.BODY1 + tenantIdCompany + UserVariables.BODY2;
		Assert.assertTrue(userPage.removeAllUsers(environment, tenantIdCompany, body, getEnvironment(environment)), "User removal failed");

		Assert.assertTrue(userPage.selectCompanyOfUserPage(companyChangeInfo, importCompany, LanguageCode), "Company selection failed while adding device.");
		userPage.clickOnElementsOfUserPage("previousButton");
		// Verify Add Users title on Add user pop up
		userPage.waitForElementsOfUserPage("addUserPopUpTitle");
		softAssert.assertTrue(userPage.matchTextOfUserPage("addUserPopUpTitle", getTextLanguage(LanguageCode, "daas_ui", "users.list.company_selection.title")), "Title on Add user popup is incorrect");
		// Verify choose company message on Add user pop up
		softAssert.assertTrue(userPage.matchTextOfUserPage("chooseCompanyLabel", getTextLanguage(LanguageCode, "daas_ui", "users.list.company_selection.heading")), "Message on Add user popup is incorrect");
		userPage.clickOnElementsOfUserPage("nextButton");

		userPage.verifyImportUser(getImportInfo, LanguageCode, userFileValidData);
			//Assert.assertTrue(userPage.postNotificationCheckImportForSuccessfullImportInV3(userFileValidData), "Message on notification window is incorrect");
		//LOGGER.info("Notification message verification for successfull import has passed");
//		if (tableConfigurationPage.verifyElementsOfTableConfigurationPage("clearFilter")) {
//			tableConfigurationPage.clickOnElementsOfTableConfigurationPage("clearFilter");
//			waitForPageLoaded();
//		}
		resetTableConfiguration();
		refreshPage();
		userPage.waitUntilElementIsInvisibleOfUserPage("tableOverlay");
		Assert.assertTrue(userPage.verifyImportedUsersOnListPage(getAddUserManuallyInfo, userFileValidData), "Users are not getting reflected on the list page.");
		//Assert.assertTrue(userPage.verifyDescriptionOnLogsPage(userFileValidData), "Description of imported users on logs page is incorrect");
		//userPage.clickOnElementsOfUserPage("closeInfoBar");
		//gotoUserTab();
		//resetTableConfiguration();
		//Assert.assertTrue(userPage.removeAllUsers(environment, tenantIdCompany, body, getEnvironment(environment)), "User removal failed");
        
		//Commenting this code since sometimes when notification get stuck then there is no option to clear it.
		//softAssert.assertTrue(userPage.verifyNotificationDismiss(), "Notification dismiss functionality is not working");
		//LOGGER.info("Notification message Dismiss functionality has passed successfully.");

		softAssert.assertAll();
		LOGGER.info("Validation of Import completed successfully.");
	}

	/**
	 * @throws Exception
	 */
	@Test(priority = 14, groups = { "REGRESSION", "REGRESSION_CI", "STABILIZATION_STAGING" }, description = "TestCase:270045,201233",enabled=false)
	public final void verifyUserImportFailure() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login("NEW_MSP_ADMIN_US_EMAIl", "NEW_MSP_ADMIN_US_PASSWORD");
		gotoUserTab();
		LOGGER.info("Navigated to Users Tab.");
		UserPage userPage = new UserPage(PreDefinedActions.getDriver()).getInstance();

		HashMap<String, String> companyChangeInfo = userPage.getCompanyChangeDetails();
		HashMap<String, String> getImportInfo = userPage.getImportDetails();
		String tenantID = getValueFromToken("tenant");
		String apiurl = cataLystURL+ CommonVariables.DETAILSSEARCHSERVICEAPI+tenantID+CommonVariables.DETAILSSEARCHSERVICEAPI2;
		String tenantIdCompany = getTenantDetails(apiurl,CommonVariables.SEARCH_SERVICE_IDMTENANTS,importCompanyNameLog);
		String body = UserVariables.BODY1 + tenantIdCompany + UserVariables.BODY2;
		Assert.assertTrue(userPage.removeAllUsers(environment, tenantIdCompany, body, getEnvironment(environment)), "User removal failed");

		Assert.assertTrue(userPage.selectCompanyOfUserPage(companyChangeInfo, importCompany, LanguageCode), "Company selection failed while adding device.");
		userPage.clickOnElementsOfUserPage("previousButton");
		// Verify Add Users title on Add user pop up
		softAssert.assertTrue(userPage.matchTextOfUserPage("addUserPopUpTitle", getTextLanguage(LanguageCode, "daas_ui", "users.list.company_selection.title")), "Title on Add user popup is incorrect");
		// Verify choose company message on Add user pop up
		softAssert.assertTrue(userPage.matchTextOfUserPage("chooseCompanyLabel", getTextLanguage(LanguageCode, "daas_ui", "users.list.company_selection.heading")), "Message on Add user popup is incorrect");
		userPage.clickOnElementsOfUserPage("nextButton");

		userPage.verifyImportInvalidUser(getImportInfo, LanguageCode, userFileInValidData);
			Assert.assertTrue(userPage.postNotificationCheckImportForInvalidImportInV3(userFileInValidData), "Message on notification window is incorrect");
		LOGGER.info("Notification message verification for invalid import has passed");

		Assert.assertTrue(userPage.verifyDescriptionOnLogsPage(userFileInValidData), "Description of imported users on logs page is incorrect");

		softAssert.assertAll();
		LOGGER.info("Validation of Invalid Import completed successfully.");
	}

	/**
	 * @throws Exception
	 */
	@Test(priority = 15, groups = { "REGRESSION", "REGRESSION_CI" }, description = "TestCase:270045",enabled=false)
	public final void verifyUserImportFailureWithBlankFile() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login("NEW_MSP_ADMIN_US_EMAIl", "NEW_MSP_ADMIN_US_PASSWORD");
		gotoUserTab();
		LOGGER.info("Navigated to Users Tab.");
		UserPage userPage = new UserPage(PreDefinedActions.getDriver()).getInstance();

		HashMap<String, String> companyChangeInfo = userPage.getCompanyChangeDetails();
		HashMap<String, String> getImportInfo = userPage.getImportDetails();

		String tenantID = getValueFromToken("tenant");
		String apiurl = getEnvironmentSpecificData(System.getProperty("environment"), "DAAS_API_BASE_URL")+ CommonVariables.DETAILSSEARCHSERVICEAPI+tenantID+CommonVariables.DETAILSSEARCHSERVICEAPI2;
		String tenantIdCompany = getTenantDetails(apiurl,CommonVariables.SEARCH_SERVICE_IDMTENANTS,importCompanyNameLog);
		String body = UserVariables.BODY1 + tenantIdCompany + UserVariables.BODY2;
		Assert.assertTrue(userPage.removeAllUsers(environment, tenantIdCompany, body, getEnvironmentSpecificData(System.getProperty("environment"), "DAAS_API_BASE_URL_AUTH")), "User removal failed");

		Assert.assertTrue(userPage.selectCompanyOfUserPage(companyChangeInfo, importCompanyLogs, LanguageCode), "Company selection failed while adding device.");
		userPage.clickOnElementsOfUserPage("previousButton");
		// Verify Add Users title on Add user pop up
		softAssert.assertTrue(userPage.matchTextOfUserPage("addUserPopUpTitle", getTextLanguage(LanguageCode, "daas_ui", "users.list.company_selection.title")), "Title on Add user popup is incorrect");
		// Verify choose company message on Add user pop up
		softAssert.assertTrue(userPage.matchTextOfUserPage("chooseCompanyLabel", getTextLanguage(LanguageCode, "daas_ui", "users.list.company_selection.heading")), "Message on Add user popup is incorrect");
		userPage.clickOnElementsOfUserPage("nextButton");

		userPage.verifyImportInvalidUserBlankFile(getImportInfo, LanguageCode, userFileInValidBlank);
			Assert.assertTrue(userPage.postNotificationCheckImportForInvalidImportInV3(userFileInValidBlank), "Message on notification window is incorrect");
		LOGGER.info("Notification message verification for invalid import with blank file has passed");

		Assert.assertTrue(userPage.verifyDescriptionOnLogsPage(userFileInValidBlank), "Description of imported users on logs page is incorrect");

		softAssert.assertAll();
		LOGGER.info("Validation of Invalid Import completed successfully.");
	}

	/**
	 * @throws Exception
	 */
	@Test(priority = 16, groups = { "REGRESSION", "REGRESSION_CI","STABILIZATION_STAGING" }, description = "TestCase:241731")
	public final void verifyUsersCompanyHeaderOnDetailsPage() throws Exception {
		SoftAssert sa = new SoftAssert();
		login("NEW_MSP_ADMIN_US_EMAIl", "NEW_MSP_ADMIN_US_PASSWORD");
		gotoUserTab();
		LOGGER.info("Redirected to user page");
		UserPage userPage = new UserPage(PreDefinedActions.getDriver()).getInstance();

		UserDetailsPage userDetailsPage = new UserDetailsPage(PreDefinedActions.getDriver()).getInstance();

		resetTableConfiguration();
		waitForPageLoaded();
		String tenantID = getValueFromToken("tenant");
        String apiurl = cataLystURL+ CommonVariables.DETAILSSEARCHSERVICEAPI+tenantID+CommonVariables.DETAILSSEARCHSERVICEAPI2;
        String tenantIdCompany = getTenantDetails(apiurl,CommonVariables.SEARCH_SERVICE_IDMTENANTS,importCompany);
		Assert.assertTrue(userPage.addUsersApi(environment, getEnvironment(environment),tenantIdCompany), "Add User API got failed.");
		
		refreshPage();
		waitForPageLoaded();
		Assert.assertTrue(userPage.verifySearchEmail(), "Added user email is not available on user list page.");
		
		userPage.waitForElementsOfUserPage("userRolesList");
		String companyName = userPage.getTextOfUserPage("companyList");
		// Validate the company Name Impersonation at on Details page
		userPage.clickByJavaScriptOnElementsOfUserPage("companyListLink");
		userDetailsPage.waitUntillElementIsPresentOfUserDetailsPage("userTitleOnDetailsPage");
		waitForPageLoaded();
		Thread.sleep(3000);
		userDetailsPage.waitForElementsOfUserDetailsPage("companyImpersonateMatching");
		sa.assertTrue(userDetailsPage.getTextOfUserDetailsPage("companyImpersonateMatching").equals(companyName), "Company name Impersonation did not match");
		navigateToBack();
		waitForPageLoaded();
		LOGGER.info("Navigated to User list page");
		userPage.clickByJavaScriptOnUserPage("firstRowUser");
		LOGGER.info("Clicked on first user in the list");
		LOGGER.info("Redirected to user details page");
		waitForPageLoaded();
		userDetailsPage.waitUntillElementIsPresentOfUserDetailsPage("breadcrumbText");
		userDetailsPage.waitUntillElementIsPresentOfUserDetailsPage("userManageCompanyHeader");
		// Validate the managing company Name on Details page
		sa.assertEquals(companyName, userDetailsPage.getTextOfUserDetailsPage("companyNameOnDetailsPage"), "Company header does not match on user details page");
		sa.assertAll();
		LOGGER.info("All testcases of verifyUsersCompanyHeaderOnDetailsPage passed successfully");
	}

	/**
	 * @throws Exception
	 */
	@Test(priority = 17, groups = { "REGRESSION", "REGRESSION_CI", "STABILIZATION_STAGING" }, description = "TestCase:248605, 248678")
	public final void verifyUsersHamburgerDetailsFunctionalityOnListPage() throws Exception {
		SoftAssert sa = new SoftAssert();
		login("NEW_MSP_ADMIN_US_EMAIl", "NEW_MSP_ADMIN_US_PASSWORD");
		UserPage userPage = new UserPage(PreDefinedActions.getDriver()).getInstance();
		UserDetailsPage userDetailsPage = new UserDetailsPage(PreDefinedActions.getDriver()).getInstance();
		userPage.waitForElementsOfUserPage("tableOverlay");
		gotoUserTab();
		LOGGER.info("Redirected to user page");

		resetTableConfiguration();
		waitForPageLoaded();
		//userPage.waitForElementsOfUserPage("tableOverlay");

		String tenantID = getValueFromToken("tenant");
		String apiurl = getEnvironmentSpecificData(System.getProperty("environment"), "DAAS_API_BASE_URL") + CommonVariables.DETAILSSEARCHSERVICEAPI + tenantID + CommonVariables.DETAILSSEARCHSERVICEAPI2;
		String tenantIdCompany = getTenantDetails(apiurl,CommonVariables.SEARCH_SERVICE_IDMTENANTS,importCompany);
		String body = UserVariables.BODY1 + tenantID + UserVariables.BODY2;
		Assert.assertTrue(userPage.removeAllUsers(environment, tenantIdCompany, body, getEnvironmentSpecificData(System.getProperty("environment"), "DAAS_API_BASE_URL_AUTH")), "User removal failed");

		Assert.assertTrue(userPage.addUsersApi(environment, getEnvironment(environment),tenantIdCompany), "Add User API got failed.");

		refreshPage();
		waitForPageLoaded();
		//userPage.waitForElementsOfUserPage("tableOverlay");
		//userPage.waitForElementsOfUserPageForinvisibile("tableOverlay");
		Assert.assertTrue(userPage.verifySearchEmail(), "Added user email is not available on user list page.");
		//userPage.waitForElementsOfUserPage("tableOverlay");

		// This method basically verify Details functionality through hamburger
		sa.assertTrue(userPage.verifyDetailsFunctionalityWithHamburgerSelection(LanguageCode), "Details functionality through hamburger got failed.");
		userDetailsPage.waitUntillElementIsPresentOfUserDetailsPage("userTitleOnDetailsPage");
		navigateToBack();
		waitForPageLoaded();
		LOGGER.info("Navigated to User list page");
		sa.assertAll();
		LOGGER.info("All testcases of verifyUsersHamburgerDetailsFunctionalityOnListPage passed successfully");
	}

	/**
	 * @throws Exception
	 */
	// Test case marked as enabled = False because of User removal captcha.
	@Test(priority = 18, groups = { "REGRESSION", "REGRESSION_CI" }, description = "TestCase:248605, 248611, 248613, 248657", enabled = false)
	public final void verifyUsersHamburgerRemoveFunctionalityOnListPage() throws Exception {
		SoftAssert sa = new SoftAssert();
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		gotoUserTab();
		LOGGER.info("Redirected to user page");
		UserPage userPage = new UserPage(PreDefinedActions.getDriver()).getInstance();

		resetTableConfiguration();
		waitForPageLoaded();
		String tenantID = getValueFromToken("tenant");
        String apiurl = cataLystURL+ CommonVariables.DETAILSSEARCHSERVICEAPI+tenantID+CommonVariables.DETAILSSEARCHSERVICEAPI2;
        String tenantIdCompany = getTenantDetails(apiurl,CommonVariables.SEARCH_SERVICE_IDMTENANTS,importCompanyName);
		Assert.assertTrue(userPage.addUsersApi(environment, getEnvironment(environment),tenantIdCompany), "Add User API got failed.");

		refreshPage();
		waitForPageLoaded();
		userPage.waitForElementsOfUserPage("tableOverlay");
		userPage.waitForElementsOfUserPageForinvisibile("tableOverlay");
		Assert.assertTrue(userPage.verifySearchEmail(), "Added user email is not available on user list page.");
		userPage.waitForElementsOfUserPage("tableOverlay");

		// This method basically verify remove functionality through hamburger
		sa.assertTrue(userPage.verifyRemoveFunctionalityWithHamburgerSelection(LanguageCode), "Remove functionality from Hamburger icon is not working.");
		sa.assertAll();
		LOGGER.info("All testcases of verifyUsersHamburgerRemoveFunctionalityOnListPage passed successfully");
	}

	/**
	 * @throws Exception
	 */
	@Test(priority = 19, groups = { "REGRESSION", "SMOKE", "REGRESSION_CI", "SMOKE_CI", "PRODUCTION_CI", "PRODUCTION_SANITY" , "FOUNDATIONACCOUNT_PRODUCTIONCI"}, description = "Test Case ID : 280856")
	public final void verifyUserListPage() throws Exception {
		login("NEW_MSP_ADMIN_US_EMAIl", "NEW_MSP_ADMIN_US_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		UserPage userPage = new UserPage(PreDefinedActions.getDriver()).getInstance();
		gotoUserTab();
		LOGGER.info("Redirected to user list page");
		waitForPageLoaded();
		userPage.clearFiltersOfUsersListPage("clearfilter");
		softAssert.assertTrue(userPage.verifyElementsOfUserPage("listTable"), "Table on list page is not loaded successfully");
		softAssert.assertTrue(userPage.verifyElementsOfUserPage("nameHeader"), "Default column is not present on list page");
		softAssert.assertAll();
		LOGGER.info("User list page is loaded successfully");
	}

	/**
	 * @throws Exception
	 */
	@Test(priority = 20, groups = { "REGRESSION", "SMOKE", "REGRESSION_CI", "SMOKE_CI", "PRODUCTION_CI", "PRODUCTION_SANITY", "FOUNDATIONACCOUNT_PRODUCTIONCI" }, description = "Test Case ID : 280862")
	public final void verifyUserDetailsPage() throws Exception {
		login("NEW_MSP_ADMIN_US_EMAIl", "NEW_MSP_ADMIN_US_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		UserPage userPage = new UserPage(PreDefinedActions.getDriver()).getInstance();
		UserDetailsPage userDetailsPage = new UserDetailsPage(PreDefinedActions.getDriver()).getInstance();
		gotoUserTab();
		LOGGER.info("Redirected to user list page");
		waitForPageLoaded();
		userPage.clearFiltersOfUsersListPage("clearfilter");
		userPage.waitForElementsOfUserPage("tableOverlay");
		waitForPageLoaded();
		userPage.clickByJavaScriptOnUserPage("firstRowUser");
		userDetailsPage.waitForElementsOfUserDetailsPage("tableOverlay");
		LOGGER.info("Redirected to details page");
		softAssert.assertTrue(userDetailsPage.verifyElementsOfUserDetailsPage("detailsTitle"), "Details tile on details page is not loaded successfully");
		userDetailsPage.scrollOnUserPage("userRoleTitle");
		if (toggleVerification(CommonVariables.ONE_EMAIL_MULTIPLE_ACCOUNTS_TOGGLE, getCredentials(environment, "MSP_ADMIN_US_EMAIl")))
			softAssert.assertTrue(userDetailsPage.verifyElementsOfUserDetailsPage("userRoleTitle"), "Account tile on details page is not loaded successfully");
		else {
			softAssert.assertTrue(userDetailsPage.verifyElementsOfUserDetailsPage("userRoleTitle"), "Role assignment tile on details page is not loaded successfully");
		}
		userDetailsPage.clickByJavaScriptOnUserDetailsPage("usersDeviceTab");
		softAssert.assertTrue(userDetailsPage.verifyElementsOfUserDetailsPage("usersDeviceTileHeaderMessage"), "User's devices tile on details page is not loaded successfully");
		softAssert.assertAll();
		LOGGER.info("User details page is loaded successfully");
	}

	/**
	 * This test verifies if Administrative users list page is loaded successfully
	 * 
	 * @throws Exception
	 */
	@Test(priority = 21, groups = { "REGRESSION", "SMOKE", "REGRESSION_CI", "SMOKE_CI" }, description = "")
	public final void verifyAdminstrativeUsersListPage() throws Exception {
		login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
		String tenantID = getValueFromToken("tenant");
		UserPage userPage = new UserPage(PreDefinedActions.getDriver()).getInstance();
		gotoAdministrativeUsersTab();
		userPage.waitUntilElementIsInvisibleOfUserPage("tableOverlay");
		LOGGER.info("Redirected to Administrative users list page");
		waitForPageLoaded();
		Assert.assertTrue(getStatusCode(getSearchServiceApi(environment) + CommonVariables.DETAILSSEARCHSERVICEAPI + tenantID + CommonVariables.DETAILSSEARCHSERVICEAPI2, UserVariables.SEARCHSERVICEBODY, "POST", getEnvironment(environment)) == CommonVariables.CODEOK, "Search service failed for administrative users list page");
		LOGGER.info("Administrative users list page is loaded successfully");
	}

	/**
	 * This test verifies if MSP users list page is loaded successfully, table configuration, details page.
	 * 
	 * @throws Exception
	 */
	@Test(priority = 22, groups = { "REGRESSION", "SMOKE", "REGRESSION_CI", "SMOKE_CI", "PRODUCTION_SANITY" }, description = "Test Case ID: 278567")
	public final void verifyMSPUsersListPage() throws Exception {
		login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
		String tenantID = getValueFromToken("tenant");
		UserPage userPage = new UserPage(PreDefinedActions.getDriver()).getInstance();
		TableConfigurationPage tableConfigurationPage = new TableConfigurationPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		UserDetailsPage userDetailsPage = new UserDetailsPage(PreDefinedActions.getDriver()).getInstance();
		gotoMSPUsersTab();
		userPage.waitUntilElementIsInvisibleOfUserPage("tableOverlay");
		LOGGER.info("Redirected to MSP users list page");
		waitForPageLoaded();
		Assert.assertTrue(getStatusCode(getSearchServiceApi(environment) + CommonVariables.DETAILSSEARCHSERVICEAPI + tenantID + CommonVariables.DETAILSSEARCHSERVICEAPI2, UserVariables.SEARCHSERVICEBODY, "POST", getEnvironment(environment)) == CommonVariables.CODEOK, "Search service failed for MSP users list page");
		LOGGER.info("MSP users list page is loaded successfully");

		ArrayList<String> columnName = new ArrayList<String>(Arrays.asList(getTextLanguage(LanguageCode, "daas_ui", "users.list.label.name"), getTextLanguage(LanguageCode, "daas_ui", "users.list.label.email"), getTextLanguage(LanguageCode, "daas_ui", "users.details.role"), getTextLanguage(LanguageCode, "daas_ui", "users.list.label.msp"), getTextLanguage(LanguageCode, "daas_ui", "users.list.label.status"), getTextLanguage(LanguageCode, "daas_ui", "users.list.label.mobile_phone"),
				getTextLanguage(LanguageCode, "daas_ui", "users.list.label.office_phone")));
		ArrayList<String> checkboxValue = new ArrayList<String>(Arrays.asList("true", "true", "true", "true", "true", "false", "false"));
		ArrayList<String> name = new ArrayList<String>(Arrays.asList(getTextLanguage(LanguageCode, "daas_ui", "companies.list.name")));

		if (tableConfigurationPage.verifyElementsOfTableConfigurationPage("clearFilter"))
			tableConfigurationPage.clickOnElementsOfTableConfigurationPage("clearFilter");

		userPage.waitForElementsOfUserPage("tableOverlay");
		userPage.clickByJavaScriptOnUserPage("firstRowUser");
		userDetailsPage.waitForElementsOfUserDetailsPage("detailsTitle");
		LOGGER.info("Redirected to MSP user details page");
		softAssert.assertTrue(userDetailsPage.verifyElementsOfUserDetailsPage("detailsTitle"), "Details tile on MSP User details page is not loaded successfully");
		softAssert.assertTrue(userDetailsPage.verifyElementsOfUserDetailsPage("userAccountsTitle"), "Account tile on MSP User details page is not loaded successfully");
		softAssert.assertFalse(userDetailsPage.verifyElementsOfUserDetailsPage("editNamePartnerUsers"), "Details page for MSP Users is in CRUD mode.");
		softAssert.assertAll();
		LOGGER.info("Validation of MSP User list and details page completed successfully");
	}

	/**
	 * This test verifies if Partners users list page is loaded successfully, table configuration, details page.
	 * 
	 * @throws Exception
	 */
	@Test(priority = 23, groups = { "REGRESSION", "SMOKE", "REGRESSION_CI", "SMOKE_CI", "PRODUCTION_SANITY" }, description = "Test Case ID: 278567")
	public final void verifyPartnersUsersListPage() throws Exception {
		login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
		String tenantID = getValueFromToken("tenant");
		UserPage userPage = new UserPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		UserDetailsPage userDetailsPage = new UserDetailsPage(PreDefinedActions.getDriver()).getInstance();
		TableConfigurationPage tableConfigurationPage = new TableConfigurationPage(PreDefinedActions.getDriver()).getInstance();
		gotoPartnerUsersTab();
		userPage.waitUntilElementIsInvisibleOfUserPage("tableOverlay");
		LOGGER.info("Redirected to Partners users list page");
		waitForPageLoaded();
		Assert.assertTrue(getStatusCode(getSearchServiceApi(environment) + CommonVariables.DETAILSSEARCHSERVICEAPI + tenantID + CommonVariables.DETAILSSEARCHSERVICEAPI2, UserVariables.SEARCHSERVICEBODY, "POST", getEnvironment(environment)) == CommonVariables.CODEOK, "Search service failed for Partners users list page");
		LOGGER.info("Partners users list page is loaded successfully");

		ArrayList<String> columnName = new ArrayList<String>(Arrays.asList(getTextLanguage(LanguageCode, "daas_ui", "users.list.label.name"), getTextLanguage(LanguageCode, "daas_ui", "users.list.label.email"), getTextLanguage(LanguageCode, "daas_ui", "users.details.role"), getTextLanguage(LanguageCode, "daas_ui", "users.list.label.partner"), getTextLanguage(LanguageCode, "daas_ui", "users.list.label.status"), getTextLanguage(LanguageCode, "daas_ui", "users.list.label.mobile_phone"),
				getTextLanguage(LanguageCode, "daas_ui", "users.list.label.office_phone")));
		ArrayList<String> checkboxValue = new ArrayList<String>(Arrays.asList("true", "true", "true", "true", "true", "false", "false"));
		ArrayList<String> name = new ArrayList<String>(Arrays.asList(getTextLanguage(LanguageCode, "daas_ui", "companies.list.name")));

		if (tableConfigurationPage.verifyElementsOfTableConfigurationPage("clearFilter"))
			tableConfigurationPage.clickOnElementsOfTableConfigurationPage("clearFilter");
		userPage.waitForElementsOfUserPage("tableOverlay");
		userPage.clickByJavaScriptOnUserPage("firstRowUser");
		userDetailsPage.waitForElementsOfUserDetailsPage("tableOverlay");
		LOGGER.info("Redirected to Partner User details page");
		softAssert.assertTrue(userDetailsPage.verifyElementsOfUserDetailsPage("detailsTitle"), "Details tile on details page is not loaded successfully");
		softAssert.assertTrue(userDetailsPage.verifyElementsOfUserDetailsPage("userAccountsTitle"), "Account tile on Partner User details page is not loaded successfully");
		softAssert.assertFalse(userDetailsPage.verifyElementsOfUserDetailsPage("editNamePartnerUsers"), "Details page for Partner Users is in CRUD mode.");
		softAssert.assertAll();
		LOGGER.info("Validation of Partner User list and details page completed successfully");
	}

	/**
	 * This test verifies if Partners users list page is loaded successfully, table configuration, details page.
	 * 
	 * @throws Exception
	 */
	@Test(priority = 24, groups = { "REGRESSION", "SMOKE", "REGRESSION_CI", "SMOKE_CI" }, description = "Test Case ID: 278567")
	public final void verifyCustomersUsersListPage() throws Exception {
		login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
		String tenantID = getValueFromToken("tenant");
		UserPage userPage = new UserPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		UserDetailsPage userDetailsPage = new UserDetailsPage(PreDefinedActions.getDriver()).getInstance();
		TableConfigurationPage tableConfigurationPage = new TableConfigurationPage(PreDefinedActions.getDriver()).getInstance();
		gotoCompaniesUsersTab();
		userPage.waitUntilElementIsInvisibleOfUserPage("tableOverlay");
		LOGGER.info("Redirected to Customers users list page");
		waitForPageLoaded();
		Assert.assertTrue(getStatusCode(getSearchServiceApi(environment) + CommonVariables.DETAILSSEARCHSERVICEAPI + tenantID + CommonVariables.DETAILSSEARCHSERVICEAPI2, UserVariables.SEARCHSERVICEBODY, "POST", getEnvironment(environment)) == CommonVariables.CODEOK, "Search service failed for Customers users list page");
		LOGGER.info("Customers users list page is loaded successfully");

		ArrayList<String> columnName = new ArrayList<String>(Arrays.asList(getTextLanguage(LanguageCode, "daas_ui", "users.list.label.name"), getTextLanguage(LanguageCode, "daas_ui", "users.list.label.email"), getTextLanguage(LanguageCode, "daas_ui", "users.list.label.roles"), getTextLanguage(LanguageCode, "daas_ui", "users.list.label.company"), getTextLanguage(LanguageCode, "daas_ui", "users.list.label.status"), getTextLanguage(LanguageCode, "daas_ui", "users.list.label.mobile_phone"),
				getTextLanguage(LanguageCode, "daas_ui", "users.list.label.office_phone"), getTextLanguage(LanguageCode, "daas_ui", "users.list.label.title"), getTextLanguage(LanguageCode, "daas_ui", "users.list.label.address"), getTextLanguage(LanguageCode, "daas_ui", "users.list.label.language"), getTextLanguage(LanguageCode, "daas_ui", "companies.list.number_of_devices")));
		ArrayList<String> checkboxValue = new ArrayList<String>(Arrays.asList("true", "true", "true", "true", "true", "false", "false", "false", "false", "false", "false"));
		ArrayList<String> name = new ArrayList<String>(Arrays.asList(getTextLanguage(LanguageCode, "daas_ui", "companies.list.name")));

		if (tableConfigurationPage.verifyElementsOfTableConfigurationPage("clearFilter"))
			tableConfigurationPage.clickOnElementsOfTableConfigurationPage("clearFilter");
		userPage.waitForElementsOfUserPage("tableOverlay");
		userPage.clickByJavaScriptOnUserPage("firstRowUser");
		waitForPageLoaded();
		LOGGER.info("Redirected to MSP user details page");
		softAssert.assertTrue(userDetailsPage.verifyElementsOfUserDetailsPage("detailsTitle"), "Details tile on Company User details page is not loaded successfully");
		softAssert.assertTrue(userDetailsPage.verifyElementsOfUserDetailsPage("userAccountsTitle"), "Account tile on Company User details page is not loaded successfully");
		softAssert.assertFalse(userDetailsPage.verifyElementsOfUserDetailsPage("editNameCompanyUsers"), "Details page for Company Users is in CRUD mode.");
		softAssert.assertAll();
		LOGGER.info("Validation of Company User list and details page completed successfully");
	}

	/**
	 * @throws Exception
	 */
	@Test(priority = 25, groups = { "REGRESSION", "SMOKE", "REGRESSION_CI", "SMOKE_CI", "PRODUCTION_SANITY" }, description = "Test case ID: 291085, 291778, 288112")
	public final void verifyAddAdminUser() throws Exception {
		login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
		String randomEmail = null, invalidEmail = null, expectedMailContent, actualMailContent , currentUrl;
		UserPage userPage = new UserPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		gotoAdministratorUserTab();
		LOGGER.info("Redirected to Admin user page");
		waitForPageLoaded();
		userPage.waitForElementsOfUserPage("tableOverlay");
		userPage.clickByJavaScriptOnUserPage("addNewUserButton");

		if (userPage.verifyElementsOfUserPage("addUserlabel")) {
			userPage.enterTextForUserPage("addFirstName", UserVariables.FIRST_NAME);
			userPage.enterTextForUserPage("addLastName", UserVariables.LAST_NAME);
			randomEmail = generateRandomString(11).toLowerCase() + "@hpmsqa.mailinator.com";
			userPage.enterTextForUserPage("addEmailAddress", randomEmail);
			userPage.clickByJavaScriptOnUserPage("addUserButton");
			userPage.waitForElementsOfUserPage("toastNotification");
			String successMessage = userPage.getTextOfUserPage("toastNotification");
			softAssert.assertTrue(successMessage.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "user.created.success")), "Toast notification is not generated after adding a administrator user");
			userPage.waitForElementsOfUserPage("tableOverlay");

			if (userPage.verifyElementsOfUserPage("adminUserEmailSearchBox")) {
				userPage.waitForElementsOfUserPage("adminUserEmailSearchBox");
				userPage.enterTextForUserPage("adminUserEmailSearchBox", randomEmail);
				// test case validates user on user list
				userPage.waitForElementsOfUserPage("userEmailSearchRow");
				sleeper(2000);
				softAssert.assertTrue(userPage.getTextOfUserPage("userEmailSearchValue").equals(randomEmail), "Email id is not present on user list page");
				LOGGER.info("Admin user is added successfully");		

				// Verify Email sent to user
				expectedMailContent = ((((getTextLanguage(LanguageCode, "idm", "onboarding.confirmation.subject.edited").replace("{0}", getTextLanguage(LanguageCode, "idm", "onboarding.support.url_techpulse")) + " " + getTextLanguage(LanguageCode, "idm", "onboarding.dear.salutation").replace("{0}", (UserVariables.FIRST_NAME+ " " + UserVariables.LAST_NAME))+"," + " " + (getTextLanguage(LanguageCode, "idm", "onboarding.confirmation.message1.edited").replace("{0}", randomEmail)).replace("<a {1}> {2} </a>", getEnvironment(System.getProperty("environment"))).replace("https://", "").replace("/", "")) + " "
						+ getTextLanguage(LanguageCode, "idm", "onboarding.confirmation.message2").replace("HP","") + " "
						+" 1. "+ getTextLanguage(LanguageCode, "idm", "onboarding.confirmation.step1.nospace").replace("<a {0}>{1}</a>", getEnvironment(System.getProperty("environment"))).replace("https://", "").replace("/", ""))+" "
						+" 2. "+ getTextLanguage(LanguageCode, "idm", "sign_in_change_notification.invite.body.listcontent2").replace("<b>", "").replace("</b>", "") + " "
						+" 3. "+ getTextLanguage(LanguageCode, "idm", "sign_in_change_notification.invite.body.listcontent3").replace("<b>", "").replace("</b>", "").replace("<br/>","") + " "
						+" - "+ getTextLanguage(LanguageCode, "idm", "sign_in_change_notification.invite.body.content_5").replace(" {0} ", randomEmail).replace("[", "").replace("]", "").replace("<b>", "").replace("</b>", "") + " "
						+" - "+ getTextLanguage(LanguageCode, "idm", "sign_in_change_notification.invite.body.content_6") + " "
						+" - "+ getTextLanguage(LanguageCode, "idm", "sign_in_change_notification.invite.body.content_7") + " "
						+" 4. "+ getTextLanguage(LanguageCode, "idm", "sign_in_change_notification.invite.body.content_8") + " "
						+ getTextLanguage(LanguageCode, "idm", "workspace.mail.common.thank_you").replace(",","") + " "
						+ (getTextLanguage(LanguageCode, "lhserver", "global.copyright").replace("%{current_year}", getCurrentYear()).replace("<b>", "")).replace("</b>",	"")).replace("<b >", "")).replace("  ", " ");

				Mailinator objMailinator = new Mailinator("", randomEmail);
				sleeper(5000);
				MailinatorMail objMailinatorEmail = objMailinator.getSpecificEmailUsingIncidentCode("Welcome to HP TechPulse", randomEmail, true);
				String mailContent = objMailinatorEmail.getBody();
				actualMailContent = getHtmlParser(mailContent);
				softAssert.assertTrue(actualMailContent.equalsIgnoreCase(expectedMailContent), "Mail content does not match");
				LOGGER.info("Email Verification to admin user is done succesfully");
				
				userPage.clickOnElementsOfUserPage("userFirstRow");
				waitForPageLoaded();
				sleeper(1000);

				// Test case delete the user created
				currentUrl = userPage.getUrlOfCurrentPage();
				String tenantID = currentUrl.substring(currentUrl.lastIndexOf("/") + 1);
				navigateToBack();
				softAssert.assertTrue(userPage.removeNonCompanyUser(environment, tenantID, getEnvironment(environment)), "User removal failed");
			}
		}

		// Test case to verify no data in add admin form
		userPage.waitForElementsOfUserPage("tableOverlay");
		userPage.clickByJavaScriptOnUserPage("addNewUserButton");
		userPage.verifyElementsOfUserPage("addUserlabel");
		userPage.clickByJavaScriptOnUserPage("addUserButton");
		softAssert.assertTrue(userPage.getTextOfUserPage("addFirstNameErrorMsg").equals(getTextLanguage(LanguageCode, "daas_ui", "form.validate.required")), "form validate message for First Name is not present on add admin form");
		softAssert.assertTrue(userPage.getTextOfUserPage("addLastNameErrorMsg").equals(getTextLanguage(LanguageCode, "daas_ui", "form.validate.required")), "form validate message for Last Name is not present on add admin form");
		softAssert.assertTrue(userPage.getTextOfUserPage("addEmailNameErrorMsg").equals(getTextLanguage(LanguageCode, "daas_ui", "form.validate.required")), "form validate message for Email is not present on add admin form");
		LOGGER.info("Add Admin user with no data is verified");

		// Test case to verify invalid email
		invalidEmail = generateRandomString(11).toLowerCase();
		userPage.enterTextForUserPage("addEmailAddress", invalidEmail);
		userPage.clickByJavaScriptOnUserPage("addUserButton");
		softAssert.assertTrue(userPage.getTextOfUserPage("addEmailNameErrorMsg").equals(getTextLanguage(LanguageCode, "daas_ui", "form.validate.email")), "form validate message for Email is not present on add admin form");
		userPage.clickOnElementsOfUserPage("cancelButton");
		LOGGER.info("Add Admin user with invalid email is verified");

		// Test case to verify cancel button
		userPage.waitForElementsOfUserPage("tableOverlay");
		userPage.clickByJavaScriptOnUserPage("addNewUserButton");
		userPage.verifyElementsOfUserPage("addUserlabel");
		userPage.enterTextForUserPage("addFirstName", UserVariables.FIRST_NAME);
		userPage.enterTextForUserPage("addLastName", UserVariables.LAST_NAME);
		randomEmail = generateRandomString(11).toLowerCase() + "@mailinator.com";
		userPage.enterTextForUserPage("addEmailAddress", randomEmail);
		userPage.clickOnElementsOfUserPage("cancelButton");
		// verify cancel button
		userPage.waitForElementsOfUserPage("adminUserEmailSearchBox");
		userPage.enterTextForUserPage("adminUserEmailSearchBox", randomEmail);
		// wait until search text area is displayed
		sleeper(3000);
		userPage.waitForElementsOfUserPage("userSearchText");
		if (userPage.getTextOfUserPage("userSearchText").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "list.no_items"))) {
			LOGGER.info("Admin User is not added on cancel button from admin user list page");
		} else {
			LOGGER.info("Admin User is added from admin user list page");
		}

		softAssert.assertAll();
		LOGGER.info("Admin user is added and deleted successfully");
	}

	/**
	 * @throws Exception
	 */
	@Test(priority = 26, groups = { "REGRESSION", "REGRESSION_CI", "SMOKE", "ALL_CI", "ALL", "PRODUCTION_SANITY" }, description = "Test case ID :288112",enabled=false)
	public final void checkRedirectionOfPage() throws Exception {
		login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
		UserPage userPage = new UserPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		gotoAdministratorSubscriptionTab();
		userPage.waitForElementsOfUserPage("tableOverlay");
		LOGGER.info("Clicked on Subscription Management tab ");
		switchToDifferentTab();
		userPage.waitForElementsOfUserPage("tableOverlay");
		userPage.waitForElementsOfUserPage("adminPortalLabel");
		sleeper(3000);// This sleeper is needed for "adminPortalLabel" presence.
		softAssert.assertTrue(userPage.getTextOfUserPage("adminPortalLabel").equals(CommonVariables.ADMINPORTALTITLE), "Subscription Management window title is not matching");
		softAssert.assertTrue(getUrlOfCurrentPage().equals(getEnvironmentSpecificData(System.getProperty("environment"), "LHADMIN_PORTAL_URL")), "Redirection to Subscription Management window failed on clicking Subscription Management button in Admin tab");
		switchBackToPreviousTab();
		softAssert.assertAll();
		LOGGER.info("Redirection to Subscription Management window Successful");
	}

	/**
	 * This test case verifies the user impersonation functionality for non company users
	 * 
	 * @throws Exception
	 */
	@Test(priority = 27, groups = { "REGRESSION", "SMOKE", "REGRESSION_CI", "SMOKE_CI", "STABILIZATION_STAGING" }, description = "Test Case ID : 278567")
	public final void verifyUserImpersonationFunctionalityForNonCompanyUsers() throws Exception {
		login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
		UserPage userPage = new UserPage(PreDefinedActions.getDriver()).getInstance();
		UserDetailsPage userDetailsPage = new UserDetailsPage(PreDefinedActions.getDriver()).getInstance();
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		String mspName = null;
		SoftAssert softAssert = new SoftAssert();
		gotoMSPUsersTab();
		LOGGER.info("Redirected to MSP users page");
		sleeper(2000);
		resetTableConfiguration();
		userPage.goToSpecificUserDetailsPage(getCredentials(environment, "MSP_NEW_UI_INACTIVE_USER_EMAIL"));
		waitForPageLoaded();
		softAssert.assertFalse(userDetailsPage.verifyElementsOfUserDetailsPage("impersonateIcon"), "Impersonate icon is displayed for inactive user");

		gotoMSPUsersTab();
		resetTableConfiguration();
		userPage.enterTextForUserPage("mspEmailTextbox", getCredentials(environment, "NEW_MSP_ADMIN_US_EMAIl"));
		userPage.waitUntilElementIsInvisibleOfUserPage("tableOverlay");
		userPage.clickByJavaScriptOnUserPage("firstUser");
        waitForPageLoaded();
		sleeper(6000);
		mspName = userDetailsPage.getTextOfUserDetailsPage("nameValue");
		userDetailsPage.clickOnElementsOfUserDetailsPage("impersonateIcon");
		LOGGER.info("Clicked on impersonate icon");
		softAssert.assertTrue(userDetailsPage.matchTextOfUserDetailsPage("impersonationPopupTitle", getTextLanguage(LanguageCode, "daas_ui", "users.details.impersonate.title").replace("{userName}", mspName)), "Title on user impersonation popup is incorrect");
		softAssert.assertTrue(userDetailsPage.matchTextOfUserDetailsPage("impersonationPopupConfirmationText", getTextLanguage(LanguageCode, "daas_ui", "users.details.impersonate.desc").replace("{userName}", mspName)), "Confirmation message on user impersonation popup is incorrect");
		userDetailsPage.clickByJavaScriptOnUserDetailsPage("impersonationCancel");
		softAssert.assertTrue(userDetailsPage.verifyElementsOfUserDetailsPage("impersonateIcon"), "Cancel functionality on user impersonation popup is not working");
		userDetailsPage.clickOnElementsOfUserDetailsPage("impersonateIcon");
		LOGGER.info("Clicked on impersonate icon for an MSP");
		userDetailsPage.clickByJavaScriptOnUserDetailsPage("impersonationSubmit");

		waitForPageLoaded();
		softAssert.assertTrue(userDetailsPage.getTextOfUserDetailsPage("impersonatedText").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "user.impersonation.warning").replace("{userName}", mspName)+" Go back to your own profile."),"Message on appbar after user impersonation is incorrect");
		softAssert.assertTrue(userDetailsPage.matchTextOfUserDetailsPage("impersonatedLink", getTextLanguage(LanguageCode, "daas_ui", "user.revert.impersonation.warning")), "Link on appbar after user impersonation is incorrect");
		//verifyUserImpersonation(UserVariables.MANAGED_SERVICE_PROVIDER_ADMINISTRATOR, getCredentials(environment, "NEW_MSP_ADMIN_US_EMAIl"), LanguageCode);
		userDetailsPage.waitForElementsOfUserDetailsPage("impersonatedLink");
		userDetailsPage.clickByJavaScriptOnUserDetailsPage("impersonatedLink");
		sleeper(5000);
		userDetailsPage.waitUntilElementIsInvisibleOfUserDetailsPage("loadingSpinner");
		softAssert.assertTrue(companiesPage.verifyElementsOfCompaniesPage("addButton"), "User not redirected to home page after clicking on go back to your own profile link");
		LOGGER.info("Verified user impersonation functionality for MSP");

//		userPage.impersonateUser(UserVariables.SUPPORT_SPECIALIST, getCredentials(environment, "NEW_MSP_ADMIN_US_EMAIl"), LanguageCode);
//		verifyUserImpersonation(UserVariables.SUPPORT_SPECIALIST, getCredentials(environment, "NEW_MSP_ADMIN_US_EMAIl"), LanguageCode);
//		userDetailsPage.waitForElementsOfUserDetailsPage("impersonatedLink");
//		userDetailsPage.clickByJavaScriptOnUserDetailsPage("impersonatedLink");
//		softAssert.assertTrue(companiesPage.verifyElementsOfCompaniesPage("addButton"), "User not redirected to home page after clicking on go back to your own profile link");
//		LOGGER.info("Verified user impersonation functionality for Support Specialist");

//		userPage.impersonateUser(UserVariables.PARTNER_ADMINISTRATOR, getCredentials(environment, "RESELLER_NEW_UI_Companies_EMAIL_COMPANIES"), LanguageCode);
//		verifyUserImpersonation(UserVariables.PARTNER_ADMINISTRATOR, getCredentials(environment, "RESELLER_NEW_UI_Companies_EMAIL_COMPANIES"), LanguageCode);
//		userDetailsPage.waitForElementsOfUserDetailsPage("impersonatedLink");
//		userDetailsPage.clickByJavaScriptOnUserDetailsPage("impersonatedLink");
//		softAssert.assertTrue(companiesPage.verifyElementsOfCompaniesPage("addButton"), "User not redirected to home page after clicking on go back to your own profile link");
//		LOGGER.info("Verified user impersonation functionality for Partner Administrator");

		
//		userPage.impersonateUser(UserVariables.SALES_SPECIALIST,getCredentials(environment,"RESELLER_NEW_UI_Companies_EMAIL_COMPANIES"), LanguageCode);
//		verifyUserImpersonation(UserVariables.SALES_SPECIALIST,getCredentials(environment,"RESELLER_NEW_UI_Companies_EMAIL_COMPANIESr"), LanguageCode);
//		userDetailsPage.waitForElementsOfUserDetailsPage("impersonatedLink");
//		userDetailsPage.clickByJavaScriptOnUserDetailsPage("impersonatedLink");
//		softAssert.assertTrue(companiesPage.verifyElementsOfCompaniesPage("addButton"),"User not redirected to home page after clicking on go back to your own profile link");
//		LOGGER.info("Verified user impersonation functionality for Sales Specialist");
		 
		softAssert.assertAll();
		LOGGER.info("All test cases of userImpersonation functionality for non compnay users passed successfully");
	}

	/**
	 * This test case verifies the user impersonation functionality for company users
	 * 
	 * @throws Exception
	 */
	@Test(priority = 28, groups = { "REGRESSION", "SMOKE", "REGRESSION_CI", "SMOKE_CI", "STABILIZATION_STAGING" }, description = "Test Case ID : 278567")
	public final void verifyUserImpersonationFunctionalityForCompanyUsers() throws Exception {
		login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
		UserPage userPage = new UserPage(PreDefinedActions.getDriver()).getInstance();
		String companyName = null;
		UserDetailsPage userDetailsPage = new UserDetailsPage(PreDefinedActions.getDriver()).getInstance();
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		gotoCompaniesUsersTab();
		resetTableConfiguration();
		userPage.waitUntilElementIsInvisibleOfUserPage("tableOverlay");
		userPage.goToSpecificUserDetailsPage(getCredentials(environment, "IT_ADMIN_INACTIVE_USER_EMAIL"));
		waitForPageLoaded();
		softAssert.assertFalse(userDetailsPage.verifyElementsOfUserDetailsPage("impersonateIcon"), "Impersonate icon is displayed for inactive user");
		LOGGER.info("User impersonation for inactive user was successfull");

		gotoCompaniesUsersTab();
		resetTableConfiguration();
		userPage.waitUntilElementIsInvisibleOfUserPage("tableOverlay");
		userPage.goToSpecificUserDetailsPage(getCredentials(environment, "ITA_COMPANY_DETAILS_EMAIL"));
		LOGGER.info("Clicked on first Company in the list");
		waitForPageLoaded();		
		//sleeper(5000);
		companyName = userDetailsPage.getTextOfUserDetailsPage("nameValue");
		userDetailsPage.waitForElementsOfUserDetailsPage("impersonateIcon");
		userDetailsPage.clickOnElementsOfUserDetailsPage("impersonateIcon");
		LOGGER.info("Clicked on impersonate icon");
		softAssert.assertTrue(userDetailsPage.matchTextOfUserDetailsPage("impersonationPopupTitle", getTextLanguage(LanguageCode, "daas_ui", "users.details.impersonate.title").replace("{userName}", companyName)), "Title on user impersonation popup is incorrect");
		softAssert.assertTrue(userDetailsPage.matchTextOfUserDetailsPage("impersonationPopupConfirmationText", getTextLanguage(LanguageCode, "daas_ui", "users.details.impersonate.desc").replace("{userName}", companyName)), "Confirmation message on user impersonation popup is incorrect");
		userDetailsPage.waitForElementsOfUserDetailsPage("impersonationCancel");
		userDetailsPage.clickByJavaScriptOnUserDetailsPage("impersonationCancel");
		softAssert.assertTrue(userDetailsPage.verifyElementsOfUserDetailsPage("impersonateIcon"), "Cancel functionality on user impersonation popup is not working");
		userDetailsPage.waitForElementsOfUserDetailsPage("impersonateIcon");
		userDetailsPage.clickOnElementsOfUserDetailsPage("impersonateIcon");
		LOGGER.info("Clicked on impersonate icon for company");
		userDetailsPage.waitForElementsOfUserDetailsPage("impersonationSubmit");
		userDetailsPage.clickByJavaScriptOnUserDetailsPage("impersonationSubmit");
		waitForPageLoaded();
		sleeper(5000);
		userDetailsPage.waitUntilElementIsInvisibleOfUserDetailsPage("loadingSpinner");
		softAssert.assertTrue(userDetailsPage.getTextOfUserDetailsPageDynamic("impersonatedText",30).equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "user.impersonation.warning").replace("{userName}", companyName)+" Go back to your own profile."),"Message on appbar after user impersonation is incorrect");
		softAssert.assertTrue(userDetailsPage.matchTextOfUserDetailsPage("impersonatedLink", getTextLanguage(LanguageCode, "daas_ui", "user.revert.impersonation.warning")), "Link on appbar after user impersonation is incorrect");
		//verifyUserImpersonation(UserVariables.IT_ADMINISTRATOR, getCredentials(environment, "ITA_COMPANY_DETAILS_EMAIL"), LanguageCode);
		userDetailsPage.waitForElementsOfUserDetailsPage("impersonatedLink");
		userDetailsPage.clickByJavaScriptOnUserDetailsPage("impersonatedLink");
		waitForPageLoaded();
		sleeper(5000);
		userDetailsPage.waitUntilElementIsInvisibleOfUserDetailsPage("loadingSpinner");
		softAssert.assertTrue(companiesPage.verifyElementsOfCompaniesPage("addButton"), "User not redirected to home page after clicking on go back to your own profile link");
		LOGGER.info("Verified user impersonation functionality for IT Admin");

//		userPage.impersonateUser(UserVariables.REPORT_ADMINISTRATOR, getCredentials(environment, "REPORT_ADMIN_STAGING_EMAIL"), LanguageCode);
//		verifyUserImpersonation(UserVariables.REPORT_ADMINISTRATOR, getCredentials(environment, "REPORT_ADMIN_STAGING_EMAIL"), LanguageCode);
//		userDetailsPage.waitForElementsOfUserDetailsPage("impersonatedLink");
//		userDetailsPage.clickByJavaScriptOnUserDetailsPage("impersonatedLink");
//		softAssert.assertTrue(companiesPage.verifyElementsOfCompaniesPage("addButton"), "User not redirected to home page after clicking on go back to your own profile link");
//		LOGGER.info("Verified user impersonation functionality for Report Admin");
//
//		userPage.impersonateUser(UserVariables.EMM_ADMINISTRATOR, getCredentials(environment, "EMM_ADMIN_CONFIGURED_EMAIL"), LanguageCode);
//		verifyUserImpersonation(UserVariables.EMM_ADMINISTRATOR, getCredentials(environment, "EMM_ADMIN_CONFIGURED_EMAIL"), LanguageCode);
//		userDetailsPage.waitForElementsOfUserDetailsPage("impersonatedLink");
//		userDetailsPage.clickByJavaScriptOnUserDetailsPage("impersonatedLink");
//		softAssert.assertTrue(companiesPage.verifyElementsOfCompaniesPage("addButton"), "User not redirected to home page after clicking on go back to your own profile link");
//		LOGGER.info("Verified user impersonation functionality for EMM Admin");
//
//		userPage.impersonateUser(UserVariables.MICROSOFT_TELEMETRY_ADMINISTRATOR, getCredentials(environment, "MICROSOFT_TELEMETRY_ADMIN_STAGING_USER_EMAIL"), LanguageCode);
//		verifyUserImpersonation(UserVariables.MICROSOFT_TELEMETRY_ADMINISTRATOR, getCredentials(environment, "MICROSOFT_TELEMETRY_ADMIN_STAGING_USER_PASSWORD"), LanguageCode);
//		userDetailsPage.waitForElementsOfUserDetailsPage("impersonatedLink");
//		userDetailsPage.clickByJavaScriptOnUserDetailsPage("impersonatedLink");
//		softAssert.assertTrue(companiesPage.verifyElementsOfCompaniesPage("addButton"), "User not redirected to home page after clicking on go back to your own profile link");
//		LOGGER.info("Verified user impersonation functionality for Microsoft Telemetry Admin");

		softAssert.assertAll();
		LOGGER.info("All test cases of userImpersonation functionality for  company users passed successfully");

	}
	/**
	 * @throws Exception
	 */
	@Test(priority = 29, groups = { "REGRESSION", "REGRESSION_CI" }, description = "TestCase:291778")
	public final void verifyRootAdminUserRemovalOnDetailsPage() throws Exception {
		login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
		UserPage userPage = new UserPage(PreDefinedActions.getDriver()).getInstance();
		UserDetailsPage userDetailsPage = new UserDetailsPage(PreDefinedActions.getDriver()).getInstance();
		TableConfigurationPage tableConfigurationPage = new TableConfigurationPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		String randomEmail = null;
		gotoAdministratorUserTab();
		LOGGER.info("Redirected to Admin user page");
		waitForPageLoaded();
		userPage.clickByJavaScriptOnUserPage("addNewUserButton");
		LOGGER.info("Clicked on ADD button");
		// Add root admin user
		if (userPage.verifyElementsOfUserPage("addUserlabel")) {
			userPage.enterTextForUserPage("addFirstName", UserVariables.FIRST_NAME);
			userPage.enterTextForUserPage("addLastName", UserVariables.LAST_NAME);
			randomEmail = generateRandomString(11).toLowerCase() + "@hpmsqa.mailinator.com";
			userPage.enterTextForUserPage("addEmailAddress", randomEmail);
			userPage.clickByJavaScriptOnUserPage("addUserButton");
			userPage.waitForElementsOfUserPage("toastNotification");
			softAssert.assertTrue(userPage.matchTextOfUserPage("toastNotification", getTextLanguage(LanguageCode, "daas_ui", "user.created.success")), "Toast notification is not generated after adding a administrator user");
			LOGGER.info("User added successfully");
		} else {
			LOGGER.info("ADD user button is missing on Users tab");
		}

		// verify user removal on device details page.
		userPage.waitForElementsOfUserPage("adminUserEmailSearchBox");
		if (tableConfigurationPage.verifyElementsOfTableConfigurationPage("clearFilter"))
			tableConfigurationPage.clickOnElementsOfTableConfigurationPage("clearFilter");
		userPage.enterTextForUserPage("adminUserEmailSearchBox", randomEmail);
		LOGGER.info("Redirected to user details page");
		waitForPageLoaded();
		userPage.clickByJavaScriptOnElementsOfUserPage("userFirstRow");
		waitForPageLoaded();
		userDetailsPage.waitForElementsOfUserDetailsPage("removeButton");
		userDetailsPage.clickOnElementsOfUserDetailsPage("removeButton");
		userDetailsPage.clickOnElementsOfUserDetailsPage("nextButton");
		userDetailsPage.waitForElementsOfUserDetailsPage("toastNotification");
		softAssert.assertTrue(userPage.matchTextOfUserPage("toastNotification", getTextLanguage(LanguageCode, "daas_ui", "global.messages.field_remove_success").replace("{field}", getTextLanguage(LanguageCode, "daas_ui", "asset_details_user"))), "Toast notification is not generated after removing a administrator user");
		// Verify user is removed from list page.
		userPage.waitForElementsOfUserPage("adminUserEmailSearchBox");
		userPage.enterTextForUserPage("adminUserEmailSearchBox", randomEmail);
		// wait until search text area is displayed
		sleeper(3000);
		userPage.waitForElementsOfUserPage("userSearchText");
		if (userPage.getTextOfUserPage("userSearchText").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "list.no_items"))) {
			LOGGER.info("Admin User is deleted from admin user list page");
		} else {
			LOGGER.info("Admin User removal failed");
		}

		softAssert.assertAll();
	}

	/**
	 * This test case verifies language after user impersonation
	 * 
	 * @throws Exception
	 */
	@Test(priority = 30, groups = { "REGRESSION", "SMOKE", "REGRESSION_CI", "SMOKE_CI" }, description = "Test Case ID : 291289")
	public final void verifyLanguageafterUserImpersonation() throws Exception {
		login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
		UserPage userPage = new UserPage(PreDefinedActions.getDriver()).getInstance();
		UserDetailsPage userDetailsPage = new UserDetailsPage(PreDefinedActions.getDriver()).getInstance();
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		String companiesTitle = null;
		SoftAssert softAssert = new SoftAssert();
		waitForPageLoaded();

		// Verify companies title
		companiesTitle = companiesPage.getTextOfCompaniesPage("companiesTitle");
		LOGGER.info("Get companies title text");

		// Impersonate MSP user
		gotoMSPUsersTab();
		LOGGER.info("Redirected to MSP users page");
		waitForPageLoaded();
		resetTableConfiguration();
		waitForPageLoaded();
		userPage.goToSpecificUserDetailsPage(getCredentials(environment, "NEW_MSP_ADMIN_US_EMAIl"));
		Thread.sleep(2000);
		userDetailsPage.waitForElementsOfUserDetailsPage("impersonateIcon");
		userDetailsPage.clickOnElementsOfUserDetailsPage("impersonateIcon");
		LOGGER.info("Clicked on impersonate icon for an MSP");
		waitForPageLoaded();
		userDetailsPage.waitForElementsOfUserDetailsPage("impersonationSubmit");
		userDetailsPage.clickByJavaScriptOnUserDetailsPage("impersonationSubmit");
		LOGGER.info("Redirected to impersonated view");
		
		// Verify language after impersonation
		gotoCompaniesTab();
		sleeper(3000);
		if (!toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, "NEW_MSP_ADMIN_US_EMAIl"))) {

		Assert.assertTrue(companiesPage.getTextOfCompaniesPage("companiesTitle").equals(companiesTitle), "Language is not same after impersonation");
		LOGGER.info("Compare language of before and after impersonation");
		}
		else{
			Assert.assertTrue(companiesPage.getTextOfCompaniesPage("companiesTitleOnBreadcrumbs").equals(companiesTitle), "Language is not same after impersonation");
			LOGGER.info("Compare language of before and after impersonation");

		}

		// Redirect to home page
		userDetailsPage.waitForElementsOfUserDetailsPage("impersonatedLink");
		userDetailsPage.clickByJavaScriptOnUserDetailsPage("impersonatedLink");

		Assert.assertTrue(companiesPage.getTextOfCompaniesPage("companiesTitle").equals(companiesPage.getTextLanguage(LanguageCode, "daas_ui", "pagetitle.companies")), "Root admin default page is not companies");
		LOGGER.info("Redirected back to home page");
		softAssert.assertAll();
	}

	/**
	 * This test case validates if the user is unable to Scroll when searching for Company Account
	 * 
	 * @throws Exception
	 */
	@Test(priority = 31, groups = { "REGRESSION", "REGRESSION_CI" }, description = "TestCase:306512", enabled=false)
	public final void verifyScrollFunctionalityWhileUserAddition() throws Exception {
		login("NEW_MSP_ADMIN_US_EMAIl", "NEW_MSP_ADMIN_US_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		UserPage userPage = new UserPage(PreDefinedActions.getDriver()).getInstance();
		ArrayList<String> companies = null;
		gotoCompaniesTab();
		LOGGER.info("Navigated to Companies Tab");
		resetTableConfiguration();
		companiesPage.waitUntilElementIsInvisibleOfCompanyPage("tableOverlay");
		while (companiesPage.getButtonEnabilityStatus("navigationItemNext")) {
			companiesPage.clickOnElementsOfCompaniesPage("navigationItemNext");
			companiesPage.waitUntilElementIsInvisibleOfCompanyPage("tableOverlay");
		}
		;
		LOGGER.info("Navigated to the last page");
		companiesPage.waitUntilElementIsInvisibleOfCompanyPage("tableOverlay");
		companies = companiesPage.getTextOfListOfCompaniesPage("nameList");
		gotoUserTab();
		LOGGER.info("Navigated to Users Tab");
		companiesPage.waitUntilElementIsInvisibleOfCompanyPage("tableOverlay");
		userPage.clickOnElementsOfUserPage("addButton");
		LOGGER.info("Clicked on add users button");
		sleeper(3000);
		userPage.clickOnElementsOfUserPage("dropDownBox");
		LOGGER.info("Clicked on dropdown box");
		sleeper(3000);
		while (!userPage.getTextOfUserPage("lastCompanyOnDropdown").equalsIgnoreCase(companies.get(companies.size() - 1).toLowerCase())) {
			userPage.scrollOnUserPage("lastCompanyOnDropdown");
			sleeper(3000);
		}
		softAssert.assertTrue(userPage.getTextOfUserPage("lastCompanyOnDropdown").equalsIgnoreCase(companies.get(companies.size() - 1)), "Scroll functionality on company selection dropdown while adding users is not working");
		softAssert.assertAll();
		LOGGER.info("Scroll functionality when searching for company account while adding new user is working.");
	}

	/** This test case verifies Authentication azure login functionality with email verification for MSP and Admin
	 * 
	 * @throws Exception */
	@SuppressWarnings("unchecked")
	@Test(priority = 32, groups = { "REGRESSION", "REGRESSION_CI" }, dataProvider = "AuthenticationLoginData", description = "311551,325714", enabled = false)
	public final void verifyAuthenticationFunctionalityWithEmail(String username, String password) throws Exception {
		login(username,password);
		SoftAssert softAssert = new SoftAssert();
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		UserPage userPage = new UserPage(PreDefinedActions.getDriver()).getInstance();
		ArrayList<String> userInfo = new ArrayList<String>();
		JSONObject requestParams = new JSONObject();
		String userEmail = null, userID, currentUrl;

		if (username.equalsIgnoreCase("MSP_AZURE")) {
			String tenantID = getValueFromToken("tenant");
			
			requestParams.clear();
			requestParams.put("key", "enforce_azure_login");
			requestParams.put("tenantId", tenantID);
			requestParams.put("value", "");
			gotoCompaniesTab();
			LOGGER.info("Navigated to Companies Tab");
			resetTableConfiguration();
			companiesPage.waitUntilElementIsInvisibleOfCompanyPage("tableOverlay");
			impersonateCompanyByCompanyName(authenticationCompany);
			goToPreferencesTab();
			LOGGER.info("Navigated to preferences tab");

			if (getStatusCodePost(getEnvironment(System.getProperty("environment")) + ConstantURL.POST_API_AZURE_LOGIN_FIRST_PART + authTenantIDUser + ConstantURL.POST_API_AZURE_LOGIN_SECOND_PART, requestParams.toJSONString()) == CommonVariables.CODEPASSED)
				LOGGER.info("Authentication method changed to Azure Active Directory Only");
			else
				LOGGER.error("Authentication method not changed to Azure Active Directory Only");
		}

		else {
			requestParams.clear();
			requestParams.put("key", "enforce_azure_login");
			requestParams.put("tenantId", authTenantRootIDUser);
			requestParams.put("value", "true");
			gotoAdminSettingsTab();
			LOGGER.info("Navigated to Settings Tab of Root");

			if (getStatusCodePost(getEnvironment(System.getProperty("environment")) + ConstantURL.POST_API_AZURE_LOGIN_FIRST_PART + authTenantRootIDUser + ConstantURL.POST_API_AZURE_LOGIN_SECOND_PART, requestParams.toJSONString()) == CommonVariables.CODEPASSED)
				LOGGER.info("Authentication method changed to Azure Active Directory Only");
			else
				LOGGER.error("Authentication method not changed to Azure Active Directory Only");

		}

		if (username.equalsIgnoreCase("MSP_AZURE")) {
			gotoUserTab();
			LOGGER.info("Navigated to users tab");
			resetTableConfiguration();
			Assert.assertTrue(userPage.addUsersApi(environment, getEnvironment(environment), authTenantIDUser),"Failed to add user via API.");
			userInfo = userPage.getUserDetails();
		    LOGGER.info("User added via API.");
		}

		else {
			gotoAdministratorUserTab();
			Assert.assertTrue(userPage.addUsersApi(environment, getEnvironment(environment), authTenantIDUser),"Failed to add user via API.");
		    LOGGER.info("User added via API.");
			userEmail = userInfo.get(0);
		}
//		String expectedMailContent = (((("You've got an invitation to HP TechPulse " + getTextLanguage(LanguageCode, "idm", "onboarding.dear.salutation.comma").replace("{0}", userInfo.get(1).toLowerCase()) + " " + getTextLanguage(LanguageCode, "idm", "onboarding.confirmation.message1.azure_nospace").replace("{0}", userInfo.get(0).toLowerCase()).replace("<a {1}>{2}</a>", getEnvironment(environment).replace("/", "").split(":")[1]) + " "
//				+ getTextLanguage(LanguageCode, "lhserver", "users.reset_password.greeting") + " " + getTextLanguage(LanguageCode, "idm", "onboarding.mail.greetings.end") + " " + (getTextLanguage(LanguageCode, "lhserver", "global.copyright").replace("%{current_year}", getCurrentYear()))).replace("<b>", "")).replace("</b>", "")).replace("<b >", "")).replace("  ", " ");
		Mailinator objMailinator = new Mailinator("", UserPage.mailIdForMailinator);
		sleeper(5000);
		MailinatorMail objMailinatorEmail = objMailinator.getSpecificEmailUsingIncidentCode("Welcome to HP TechPulse", userInfo.get(0).toLowerCase(), true);
		String mailContent = objMailinatorEmail.getBody();
		InputStream in = org.apache.commons.io.IOUtils.toInputStream(mailContent, "UTF-8");
		BodyContentHandler handler = new BodyContentHandler();
		Metadata metaData = new Metadata();
		ParseContext pContext = new ParseContext();
		HtmlParser htmlParser = new HtmlParser();
		htmlParser.parse(in, handler, metaData, pContext);
		softAssert.assertFalse(objMailinatorEmail==null, "No email received after adding user with authentication method as Azure Only.");
		//softAssert.assertTrue(handler.toString().replaceAll("\\s{2,}", " ").trim().equalsIgnoreCase(expectedMailContent), "Mail content does not match for azure directory only authentication method");
		LOGGER.info("Email Verification for adding users with azure directory only authentication method is done succesfully");

		if (username.equalsIgnoreCase("MSP_AZURE")) {
			gotoCompaniesTab();
			LOGGER.info("Navigated to Companies Tab");
			resetTableConfiguration();
			companiesPage.waitUntilElementIsInvisibleOfCompanyPage("tableOverlay");

			impersonateCompanyByCompanyName(authenticationCompany);
			goToPreferencesTab();
			LOGGER.info("Navigated to preferences tab");
			String tenantID = getValueFromToken("tenant");
			requestParams.clear();
			requestParams.put("key", "enforce_azure_login");
			requestParams.put("tenantId", tenantID);
			requestParams.put("value", "");

			if (getStatusCodePost(getEnvironment(System.getProperty("environment")) + ConstantURL.POST_API_AZURE_LOGIN_FIRST_PART + tenantID + ConstantURL.POST_API_AZURE_LOGIN_SECOND_PART, requestParams.toJSONString()) == CommonVariables.CODEPASSED)
				LOGGER.info("Authentication method changed to Azure Active Directory and HP account");
			else
				LOGGER.error("Authentication method not changed to Azure Active Directory and HP account");

		}

		else {

			if (userPage.waitForElementsOfUserPage("removeAllSearchFilters")) {
				userPage.clickOnElementsOfUserPage("removeAllSearchFilters");
				userPage.waitUntilElementIsInvisibleOfUserPage("tableOverlay");
				LOGGER.info("All the filters of root users list page has been removed successfully.");
			}
			userPage.enterTextForUserPage("adminUserEmailSearchBox", userEmail);
			Assert.assertFalse(userPage.verifyElementsOfUserPage("userEmptySearchResult"), "Email not found");
			Assert.assertTrue(userPage.getTextOfUserPage("userEmailSearchTextList").equalsIgnoreCase(userEmail), "email is not found on user search list.");
			userPage.clickByJavaScriptOnUserPage("userNameSearchValue");
			sleeper(3000);// Need to wait after clicking on search value
			currentUrl = userPage.getUrlOfCurrentPage();
			userID = currentUrl.substring(currentUrl.lastIndexOf("/") + 1);
			userPage.removeNonCompanyUser(environment, userID, getEnvironment(environment));

			gotoAdminSettingsTab();
			LOGGER.info("Navigated to Settings Tab of Root");
			requestParams.clear();
			requestParams.put("key", "enforce_azure_login");
			requestParams.put("tenantId", authTenantRootIDUser);
			requestParams.put("value", "");

			if (getStatusCodePost(getEnvironment(System.getProperty("environment")) + ConstantURL.POST_API_AZURE_LOGIN_FIRST_PART + authTenantRootIDUser + ConstantURL.POST_API_AZURE_LOGIN_SECOND_PART, requestParams.toJSONString()) == CommonVariables.CODEPASSED)
				LOGGER.info("Authentication method changed to Azure Active Directory and HP account");
			else
				LOGGER.error("Authentication method not changed to Azure Active Directory and HP account");

		}

		if (username.equalsIgnoreCase("MSP_AZURE")) {
			gotoUserTab();
			LOGGER.info("Navigated to users tab");
			resetTableConfiguration();
			Assert.assertTrue(userPage.addUsersApi(environment, getEnvironment(environment), authTenantIDUser),"Failed to add user via API.");
		    LOGGER.info("User added via API.");
		} else {
			gotoAdministratorUserTab();
			userInfo.clear();
			Assert.assertTrue(userPage.addUsersApi(environment, getEnvironment(environment), authTenantIDUser),"Failed to add user via API.");
		    LOGGER.info("User added via API.");
			userEmail = userInfo.get(0);
		}

//		expectedMailContent = (((("You've got an invitation to HP TechPulse " + getTextLanguage(LanguageCode, "idm", "onboarding.dear.salutation.comma").replace("{0}", userInfo.get(1).toLowerCase()) + " " + getTextLanguage(LanguageCode, "idm", "onboarding.confirmation.message1.edited").replace("{0}", userInfo.get(0)).replace("<a {1}> {2} </a>", getEnvironment(environment).replace("/", "").split(":")[1]) + " " + getTextLanguage(LanguageCode, "idm", "onboarding.confirmation.message2")
//				+ " 1. " + getTextLanguage(LanguageCode, "idm", "onboarding.confirmation.step1").replace("<a {0}> {1} </a>", getEnvironment(environment).replace("/", "").split(":")[1]) + " 2. " + getTextLanguage(LanguageCode, "idm", "sign_in_change_notification.invite.body.content_3") + " 3. " + getTextLanguage(LanguageCode, "idm", "sign_in_change_notification.invite.body.content_4") + " 4. "
//				+ getTextLanguage(LanguageCode, "idm", "sign_in_change_notification.invite.body.content_5").replace("[ ", "").replace(" ]", "").replace("{0}", userInfo.get(0).toLowerCase()) + " 5. " + getTextLanguage(LanguageCode, "idm", "sign_in_change_notification.invite.body.content_6") + " 6. " + getTextLanguage(LanguageCode, "idm", "sign_in_change_notification.invite.body.content_7") + " 7. " + getTextLanguage(LanguageCode, "daas_ui", "sign_in_change_notification.invite.body.content_8")
//				+ " " + getTextLanguage(LanguageCode, "lhserver", "users.reset_password.greeting") + " " + getTextLanguage(LanguageCode, "idm", "onboarding.mail.greetings.end") + " " + (getTextLanguage(LanguageCode, "lhserver", "global.copyright").replace("%{current_year}", getCurrentYear()))).replace("<b>", "")).replace("</b>", "")).replace("<b >", "")).replace("  ", " ");
		objMailinator = new Mailinator("", UserPage.mailIdForMailinator);
		sleeper(5000); // Time required to load the mails
		objMailinatorEmail = objMailinator.getSpecificEmailUsingIncidentCode("Welcome to HP TechPulse", userInfo.get(0).toLowerCase(), true);
		mailContent = objMailinatorEmail.getBody();
		in = org.apache.commons.io.IOUtils.toInputStream(mailContent, "UTF-8");
		handler = new BodyContentHandler();
		metaData = new Metadata();
		pContext = new ParseContext();
		htmlParser = new HtmlParser();
		htmlParser.parse(in, handler, metaData, pContext);
		softAssert.assertFalse(objMailinatorEmail==null, "No email received after adding user with authentication method as Azure Active Directory and HP account authentication method.");
		//softAssert.assertTrue(handler.toString().replaceAll("\\s{2,}", " ").trim().equalsIgnoreCase(expectedMailContent), "Mail content does not match for Azure Active Directory and HP account authentication method");

		LOGGER.info("Email Verification for adding users with Azure Active Directory and HP account authentication method is done succesfully");

		// Remove Users
		if (username.equalsIgnoreCase("MSP_AZURE")) {
			JSONObject jsonAuthToken = (JSONObject) parser.parse(authTokenParse(userPage.getTokenFromLocalStorage("dui_token_e")));
			String tenantID = jsonAuthToken.get("tenant").toString();
			String body = UserVariables.BODY1 + tenantID + UserVariables.BODY2;
			softAssert.assertTrue(userPage.removeAllUsers(environment, tenantID, body, getEnvironment(environment)), "User removal failed");
		}

		else {
			if (userPage.waitForElementsOfUserPage("removeAllSearchFilters")) {
				userPage.clickOnElementsOfUserPage("removeAllSearchFilters");
				userPage.waitUntilElementIsInvisibleOfUserPage("tableOverlay");
				LOGGER.info("All the filters of root users list page has been removed successfully.");
			}
			userPage.enterTextForUserPage("adminUserEmailSearchBox", userEmail);
			Assert.assertFalse(userPage.verifyElementsOfUserPage("userEmptySearchResult"), "Email not found");
			Assert.assertTrue(userPage.getTextOfUserPage("userEmailSearchTextList").equalsIgnoreCase(userEmail), "email is not found on user search list.");
			userPage.clickByJavaScriptOnUserPage("userNameSearchValue");
			sleeper(3000);// Need to wait after clicking on search value
			currentUrl = userPage.getUrlOfCurrentPage();
			userID = currentUrl.substring(currentUrl.lastIndexOf("/") + 1);
			userPage.removeNonCompanyUser(environment, userID, getEnvironment(environment));
		}

		softAssert.assertAll();
		LOGGER.info("Validation of Authentication functionality completed successfully.");
	}

	/** This test case verifies azure login functionality without email verification
	 * @throws Exception **/
	@SuppressWarnings("unchecked")
	@Test(priority = 33, groups = { "REGRESSION", "REGRESSION_CI" }, description = "TestCase:311551,325714",enabled=false)
	public final void verifyAuthenticationFunctionalityMSP() throws Exception {
		try{
		login("MSP_AZURE", "MSP_AZURE_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		PreferencesPage preferencesPage = new PreferencesPage(PreDefinedActions.getDriver()).getInstance();
		CompaniesDetailsPage companyDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver()).getInstance();

		gotoSettingsTab();
		waitForPageLoaded();
		LOGGER.info("Navigated to Settins Tab");
		preferencesPage.clickOnElementsOfPreferencesPage("settingsPreferenceTab");
		LOGGER.info("Navigated to preferences tab");

		softAssert.assertTrue(companyDetailsPage.verifyAuthenticationMethod(LanguageCode, "SAVE"), "Authentication method change operation(SAVE) got failed.");
		softAssert.assertTrue(companyDetailsPage.verifyAuthenticationMethod(LanguageCode, "CANCEL"), "Authentication method change operation(SAVE) got failed.");

		softAssert.assertAll();
		LOGGER.info("Validation of Authentication method completed successfully for MSP" );
		}finally{
	
			String tenantID = getValueFromToken("tenant");
			JSONObject requestParams = new JSONObject();
			requestParams.clear();
			requestParams.put("key", "enforce_azure_login");
			requestParams.put("tenantId", tenantID);
			requestParams.put("value", "");
			
			if (getStatusCodePost(getEnvironment(System.getProperty("environment")) + ConstantURL.POST_API_AZURE_LOGIN_FIRST_PART + tenantID + ConstantURL.POST_API_AZURE_LOGIN_SECOND_PART, requestParams.toJSONString()) == CommonVariables.CODEPASSED)
				LOGGER.info("Authentication method changed to Azure Active Directory and HP account");
			else
				LOGGER.error("Authentication method not changed to Azure Active Directory and HP account");

	}
}
	
	
	/** This test case verifies azure login functionality without email verification
	 * @throws Exception **/
	@SuppressWarnings("unchecked")
	@Test(priority = 34, groups = { "REGRESSION", "REGRESSION_CI" }, description = "TestCase:311551,325714",enabled=false)
	public final void verifyAuthenticationFunctionalityPartner() throws Exception {
		try{
		login("PARTNER_AZURE", "PARTNER_AZURE_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		PreferencesPage preferencesPage = new PreferencesPage(PreDefinedActions.getDriver()).getInstance();
		CompaniesDetailsPage companyDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver()).getInstance();

		gotoSettingsTab();
		waitForPageLoaded();
		LOGGER.info("Navigated to Settins Tab");
		preferencesPage.waitUntillElementIsPresent("settingsPreferenceTab");
		preferencesPage.clickOnElementsOfPreferencesPage("settingsPreferenceTab");
		LOGGER.info("Navigated to preferences tab");

		softAssert.assertTrue(companyDetailsPage.verifyAuthenticationMethod(LanguageCode, "SAVE"), "Authentication method change operation(SAVE) got failed.");
		softAssert.assertTrue(companyDetailsPage.verifyAuthenticationMethod(LanguageCode, "CANCEL"), "Authentication method change operation(SAVE) got failed.");

		softAssert.assertAll();
		LOGGER.info("Validation of Authentication method completed successfully for Partner");
		}finally{
			String tenantID = getValueFromToken("tenant");
			JSONObject requestParams = new JSONObject();
			requestParams.clear();
			requestParams.put("key", "enforce_azure_login");
			requestParams.put("tenantId", tenantID);
			requestParams.put("value", "");
			
			if (getStatusCodePost(getEnvironment(System.getProperty("environment")) + ConstantURL.POST_API_AZURE_LOGIN_FIRST_PART + tenantID + ConstantURL.POST_API_AZURE_LOGIN_SECOND_PART, requestParams.toJSONString()) == CommonVariables.CODEPASSED)
				LOGGER.info("Authentication method changed to Azure Active Directory and HP account");
			else
				LOGGER.error("Authentication method not changed to Azure Active Directory and HP account");

	}
}

	/**
	 * This method will verify pagination list table row count updates
	 *
	 * @throws Exception
	 */
	@Test(priority = 35, groups = { "REGRESSION", "REGRESSION_CI", "SMOKE", "ALL_CI", "ALL" }, description = "Test Case: 414880")
	public final void verifyUpdatedPaginationOnListPage() throws Exception {
		login("NEW_MSP_ADMIN_US_EMAIl", "NEW_MSP_ADMIN_US_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		UserPage userPage = new UserPage(PreDefinedActions.getDriver()).getInstance();
		DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
		if (!toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, "NEW_MSP_ADMIN_US_EMAIl"))) {
		gotoUserTab();
		LOGGER.info("Navigate to User list page");
		waitForPageLoaded();

		resetTableConfiguration();
		userPage.waitForElementsOfUserPage("tableOverlay");

		softAssert.assertTrue(userPage.matchTextOfUserPage("userTitle", getTextLanguage(LanguageCode, "daas_ui", "pagetitle.users")), " User title doesn't match");
		softAssert.assertTrue(userPage.verifyElementsOfUserPage("listTableRowCount"), "Total number of rows available in list table Pagination String is not available");

		if(toggleVerification(DashboardVariables.LISTPAGETABLE_UPDATED_ROW_COUNT, getCredentials(environment, "NEW_MSP_ADMIN_US_EMAIl"))){
		LOGGER.info("Verifying pagination list page rows count options when LISTPAGETABLE_UPDATED_ROW_COUNT Toggle is ON");
		userPage.clickByJavaScriptOnUserPage("listTableRowCount");
		ArrayList<String> expectedRowCount = new ArrayList<String>(Arrays.asList("25", "50", "100","500","1000"));

		softAssert.assertTrue(userPage.verifyPaginationlistTablerowsCount(expectedRowCount,"listTablerowsCountOptions"),"Rows count options on Pagination string are not as expected when 'LISTPAGETABLE_UPDATED_ROW_COUNT' toggle is ON.");
		userPage.clickByJavaScriptOnUserPage("listTablerowsCountOptions500");
		LOGGER.info("Set the pagination list page display rows count options upto 500");
		waitForPageLoaded();
		userPage.waitForElementsOfUserPage("tableOverlay");

		}
		else{
			LOGGER.info("Verifying pagination list page rows count options when LISTPAGETABLE_UPDATED_ROW_COUNT Toggle is OFF");
			userPage.clickByJavaScriptOnUserPage("listTableRowCount");
			ArrayList<String> expectedRowCount = new ArrayList<String>(Arrays.asList("25", "50", "100"));

			softAssert.assertTrue(userPage.verifyPaginationlistTablerowsCount(expectedRowCount,"listTablerowsCountOptions"),"Rows count options on Pagination string are not as expected when 'LISTPAGETABLE_UPDATED_ROW_COUNT' toggle is OFF.");
			userPage.clickByJavaScriptOnUserPage("listTablerowsCountOptions100");
			LOGGER.info("Set the pagination list page display rows count options upto 100");
			waitForPageLoaded();
			userPage.waitForElementsOfUserPage("tableOverlay");

		}
		String updatedPaginationRowCount = userPage.getTextOfUserPage("listTableRowCount");
		waitForPageLoaded();

		gotoDevicesTab();
		LOGGER.info("Navigate to Device list page");

		gotoUserTab();
		LOGGER.info("Navigate back to User list page");

		userPage.waitForElementsOfUserPage("listTableRowCount");
		softAssert.assertTrue(userPage.matchTextOfUserPage("listTableRowCount", updatedPaginationRowCount), "Pagination row count does not remembered after navigation to page and back to user page");
		LOGGER.info("Selected pagination count remembered after navigation operation.");

		logout();
		waitForPageLoaded();
		LOGGER.info("Logged out successfully");
		LOGGER.info("Log in with msp to verify Selected pagination count remembered");

		login("NEW_MSP_ADMIN_US_EMAIl", "NEW_MSP_ADMIN_US_PASSWORD");

		gotoUserTab();
		LOGGER.info("Navigate to User list page");

		resetTableConfiguration();
		userPage.waitForElementsOfUserPage("tableOverlay");

		softAssert.assertTrue(userPage.matchTextOfUserPage("userTitle", getTextLanguage(LanguageCode, "daas_ui", "pagetitle.users")), " User title doesn't match");
		softAssert.assertTrue(userPage.verifyElementsOfUserPage("listTableRowCount"), "Total number of rows available in list table Pagination String is not available");

		softAssert.assertTrue(userPage.matchTextOfUserPage("listTableRowCount", updatedPaginationRowCount), "Pagination row count does not remembered after navigation to page and back to user page");
		LOGGER.info("Selected pagination count remembered after LOG-OUT LOG-IN operation.");
		}
		else{
			gotoUserTab();
			LOGGER.info("Navigate to User list page");
			resetTableConfiguration();
			userPage.waitForElementsOfUserPage("tableOverlay");

			softAssert.assertTrue(userPage.matchTextOfUserPage("userTitleOnBreadcrumbs", getTextLanguage(LanguageCode, "daas_ui", "pagetitle.users")), " User title doesn't match");
			softAssert.assertTrue(userPage.verifyElementsOfUserPage("listTableRowCount"), "Total number of rows available in list table Pagination String is not available");

			if(toggleVerification(DashboardVariables.LISTPAGETABLE_UPDATED_ROW_COUNT, getCredentials(environment, "MSP_ADMIN_US_EMAIl"))){
			LOGGER.info("Verifying pagination list page rows count options when LISTPAGETABLE_UPDATED_ROW_COUNT Toggle is ON");
			userPage.clickByJavaScriptOnUserPage("listTableRowCount");
			ArrayList<String> expectedRowCount = new ArrayList<String>(Arrays.asList("25", "50", "100","500","1000"));

			softAssert.assertTrue(userPage.verifyPaginationlistTablerowsCount(expectedRowCount,"listTablerowsCountOptions"),"Rows count options on Pagination string are not as expected when 'LISTPAGETABLE_UPDATED_ROW_COUNT' toggle is ON.");
			userPage.clickByJavaScriptOnUserPage("listTablerowsCountOptions500");
			LOGGER.info("Set the pagination list page display rows count options upto 500");
			waitForPageLoaded();
			userPage.waitForElementsOfUserPage("tableOverlay");

			}
			else{
				LOGGER.info("Verifying pagination list page rows count options when LISTPAGETABLE_UPDATED_ROW_COUNT Toggle is OFF");
				userPage.clickByJavaScriptOnUserPage("listTableRowCount");
				ArrayList<String> expectedRowCount = new ArrayList<String>(Arrays.asList("25", "50", "100"));

				softAssert.assertTrue(userPage.verifyPaginationlistTablerowsCount(expectedRowCount,"listTablerowsCountOptions"),"Rows count options on Pagination string are not as expected when 'LISTPAGETABLE_UPDATED_ROW_COUNT' toggle is OFF.");
				userPage.clickByJavaScriptOnUserPage("listTablerowsCountOptions100");
				LOGGER.info("Set the pagination list page display rows count options upto 100");
				waitForPageLoaded();
				userPage.waitForElementsOfUserPage("tableOverlay");

			}
			String updatedPaginationRowCount = userPage.getTextOfUserPage("listTableRowCount");
			waitForPageLoaded();

			gotoDevicesTab();
			LOGGER.info("Navigate to Device list page");
			waitForPageLoaded();
			deviceListPage.waitForElementsOfDeviceListPage("tableOverlay");

			gotoUserTab();
			LOGGER.info("Navigate back to User list page");

			softAssert.assertTrue(userPage.matchTextOfUserPage("listTableRowCount", updatedPaginationRowCount), "Pagination row count does not remembered after navigation to page and back to user page");
			LOGGER.info("Selected pagination count remembered after navigation operation.");

			logout();
			waitForPageLoaded();
			LOGGER.info("Logged out successfully");
			LOGGER.info("Log in with msp to verify Selected pagination count remembered");

			login("NEW_MSP_ADMIN_US_EMAIl", "NEW_MSP_ADMIN_US_PASSWORD");

			gotoUserTab();
			LOGGER.info("Navigate to User list page");

			resetTableConfiguration();
			userPage.waitForElementsOfUserPage("tableOverlay");

			softAssert.assertTrue(userPage.matchTextOfUserPage("userTitleOnBreadcrumbs", getTextLanguage(LanguageCode, "daas_ui", "pagetitle.users")), " User title doesn't match");
			softAssert.assertTrue(userPage.verifyElementsOfUserPage("listTableRowCount"), "Total number of rows available in list table Pagination String is not available");

			softAssert.assertTrue(userPage.matchTextOfUserPage("listTableRowCount", updatedPaginationRowCount), "Pagination row count does not remembered after navigation to page and back to user page");
			LOGGER.info("Selected pagination count remembered after LOG-OUT LOG-IN operation.");
		}
		softAssert.assertAll();
		LOGGER.info("Validation of list pages pagination for 500 and 1000 rows completed successfully.");
	}

	/**
	 * This Test case verify add/remove ldp user for flip
	 * 
	 * @throws Exception
	 */
	@Test(priority = 36, groups = { "REGRESSION" }, description = "TEST CASE 582802",enabled=false)
	public final void verifyFlipAddAndRevokeLDPUser() throws Exception {
		login("ITA_COMPANY_DETAILS_EMAIL", "ITA_COMPANY_DETAILS_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		UserPage userPage = new UserPage(PreDefinedActions.getDriver()).getInstance();
		LogPage logsPage = new LogPage(PreDefinedActions.getDriver()).getInstance();
		gotoUserTab();
		resetTableConfiguration();
		softAssert.assertTrue(userPage.addLDPUsersApi(environment, getEnvironment(environment), authTenantIDLDPUser),
				"Failed to add user via API.");
		LOGGER.info("User added via API.");

		gotoLogTab();
		logsPage.clickOnElementsOfLogPage("expandButton");
		logsPage.verifyElementsOfLogPage("lockeraseenableStatus");
		Assert.assertTrue(logsPage.verifyElementsOfLogPage("lockeraseenableStatus"), "User is not present in logs tab");

		JSONObject jsonAuthToken = (JSONObject) parser
				.parse(authTokenParse(userPage.getTokenFromLocalStorage("dui_token_e")));
		String tenantID = jsonAuthToken.get("tenant").toString();
		String body = UserVariables.BODY1 + tenantID + UserVariables.BODY2;
		softAssert.assertTrue(userPage.removeAllUsers(environment, tenantID, body, getEnvironment(environment)),
				"User removal failed");

		gotoLogTab();
		logsPage.clickOnElementsOfLogPage("expandButton");
		logsPage.verifyElementsOfLogPage("lockeraseenableStatus");
		softAssert.assertTrue(logsPage.verifyElementsOfLogPage("lockeraseenableStatus"),
				"User is not present in logs tab");

		softAssert.assertAll();
		LOGGER.info("Validated add/Remove ldp user and logs");
	}
	/**
	 * This Test case verify approvel status and reminder for approver to enroll for flip
	 * 
	 * @throws Exception
	 */
	@Test(priority = 37, groups = { "REGRESSION" }, description = "TEST CASE 386077,user story 370507,", enabled=false)
	public final void verifyRemindApproverToEnrollForFlip() throws Exception {
		login("MSP_ADMIN_US_EMAIL", "MSP_ADMIN_US_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		UserPage userPage = new UserPage(PreDefinedActions.getDriver()).getInstance();
		gotoUserTab();
		resetTableConfiguration();
		List<WebElement> recordCountOnListPage = userPage.getElementsOfUserPage("recordCountOnListPage");
		String numberOfLDPAdmin = userPage.getTextOfUserPage("numberOfLdpAdmin");
		for (int singleEntry = 0; singleEntry <= recordCountOnListPage.size(); singleEntry++) {
			if (numberOfLDPAdmin.contains("Lost Device Admin")) {
				softAssert.assertEquals(userPage.getTextOfUserPage("lockAndEraseApprovalStatus"),
						userPage.getTextLanguage(LanguageCode, "daas_ui", "users.list.approversStatus.not_enrolled"));
				LOGGER.info("Verified lock and erase approval status");
				userPage.waitForElementsOfUserPage("tableRowSelector");
				userPage.mousehoverOnUserPage("tableRowSelector");
				userPage.clickOnElementsOfUserPage("userCheckbox");
				userPage.waitForElementsOfUserPage("remindApproverToEnrollButton");
				softAssert.assertEquals(userPage.getTextOfUserPage("remindApproverToEnrollButton").toLowerCase(),
						userPage.getTextLanguage(LanguageCode, "daas_ui", "flip.remindApproversToEnroll.button.text")
								.toLowerCase());
				userPage.clickOnElementsOfUserPage("remindApproverToEnrollButton");
				softAssert.assertEquals(userPage.getTextOfUserPage("remindApproverText"),
						userPage.getTextLanguage(LanguageCode, "daas_ui", "flip.remindApproversToEnroll.button.text"));
				softAssert.assertTrue(userPage.verifyElementIsClickableOfUserPage("cancelPopUpButton"), "Cancel buttion is not clickable");
				userPage.clickByJavaScriptOnUserPage("savePopupButton");
				userPage.verifyElementIsPresent("toastNotification");
				softAssert.assertEquals(userPage.getTextOfUserPage("toastNotification"),
						userPage.getTextLanguage(LanguageCode, "daas_ui", "flip.remindApprovers.message.success"));
				break;
			} else {
				LOGGER.info("Lost Device Admin is not present in user list page");
			}
		}
		softAssert.assertAll();
		LOGGER.info("Verified approvel status and reminder for approver to enroll in KMA application");
	}
	
	// This test case validates Accounts tile present on company user details page
	@Test(priority = 38, groups = { "REGRESSION", "REGRESSION_CI"}, description = "NFR 52294 , TestCase=528536",enabled=false)
	public final void verifyAccountsTile() {
		try {
			login("ITA_COMPANY_DETAILS_EMAIL", "ITA_COMPANY_DETAILS_PASSWORD");
			SoftAssert softAssert = new SoftAssert();
			gotoUserTab();
			LOGGER.info("Redirected to company user list page");
			UserPage userPage = new UserPage(PreDefinedActions.getDriver()).getInstance();
			UserDetailsPage userDetailsPage = new UserDetailsPage(PreDefinedActions.getDriver()).getInstance();
			String selectedValue = null;
			resetTableConfiguration();
			waitForPageLoaded();
			userPage.enterTextForUserPage("emailTextbox", UserVariables.COMPANY_TEST_USER_EMAIL);
			sleeper(3000);
			userPage.clickByJavaScriptOnUserPage("firstIdName");
			LOGGER.info("Redirected to user detail page");

			waitForPageLoaded();
			// Test Case 1 - This test case validates if role of sales team member on details page matches with that of the list page
			userDetailsPage.waitForElementsOfUserDetailsPage("roleHeader");
			softAssert.assertTrue(userDetailsPage.getTextOfUserDetailsPage("roleHeader").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "users.details.accounts")), "Title on Accounts tile is incorrect");
			softAssert.assertTrue(userDetailsPage.matchTextOfUserDetailsPage("roleMessage", getTextLanguage(LanguageCode, "daas_ui", "users.details.section.role_assignment_description")), "Description on Accounts tile is incorrect");
			userDetailsPage.verifyElementsOfUserDetailsPage("roleLabel");
			userDetailsPage.clickOnElementsOfUserDetailsPage("roleButton");
			userDetailsPage.waitUntillElementIsPresentOfUserDetailsPage("rolePopupHeader");
			softAssert.assertTrue(userDetailsPage.matchTextOfUserDetailsPage("rolePopupHeader", getTextLanguage(LanguageCode, "daas_ui", "users.details.roles.modal.title")), "Header of Edit Role popup is incorrect");
			userDetailsPage.clickOnElementsOfUserDetailsPage("rolesCancel");
			LOGGER.info("Verified cancel functionality on Accounts tile of company user details page");
			sleeper(3000);//Added sleeper as role list takes time in loading
			
			// Test Case 2 - This test case validates save functionality on role assignemnt popup
			userDetailsPage.clickOnElementsOfUserDetailsPage("roleButton");
			sleeper(1000);
			userDetailsPage.selectMultipleRolesOnUserDetailsPage();
			LOGGER.info("Multiple roles selected");
			userDetailsPage.clickOnElementsOfUserDetailsPage("rolesSave");
			LOGGER.info("Verified role assignment functionality on company user detail page");
			userDetailsPage.waitUntillElementIsPresentOfUserDetailsPage("toastNotificationAccountTile");
			softAssert.assertTrue(userDetailsPage.matchTextOfUserDetailsPage("toastNotificationAccountTile", getTextLanguage(LanguageCode, "daas_ui", "user.update.success").replace("{section}", "User's").replace("{field}", "roles")), "Save functionality on Edit Role popup is not working");
			sleeper(2000);
			selectedValue=userDetailsPage.getElementOfUserDetailsPage("roleValue").getText().replaceAll("/", ",");
			
			// Test Case 3 - This test case validates if the updated role is reflected back on sales team list page
			navigateToBack();
			LOGGER.info("Redirected to Company User list page");
			waitForPageLoaded();
			softAssert.assertTrue(userPage.getTextOfUserPage("firstIdRole").equalsIgnoreCase(selectedValue), "Updated role is not reflected back on Company user list page");
			softAssert.assertAll();
			LOGGER.info("Verification of Accounts tile on company user detail page done successfully");
			
		} catch (Exception e) {
			Assert.fail("Exception occured in method verifyAccountsTile " + e.getMessage());
		}
	}
	
	// This test case verifies advanced filters functionality on users list page
	@Test(priority = 39, groups = { "REGRESSION", "REGRESSION_CI" }, description = "TestCase : 637779")
	public final void verifyAdvancedFilters() throws Exception {
		login("ITA_COMPANY_DETAILS_EMAIL", "ITA_COMPANY_DETAILS_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		String filterName = generateRandomString(6);
		gotoUserTab();
		LOGGER.info("Redirected to user list page");
		UserPage userPage = new UserPage(PreDefinedActions.getDriver()).getInstance();
		resetTableConfiguration();
		waitForPageLoaded();
			userPage.clickOnElementsOfUserPage("advancedFiltersButton");
			LOGGER.info("Clicked on advanced filters button");
			softAssert.assertTrue(
					userPage.getTextOfUserPage("advancedFiltersTitle")
							.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "queryBuilder.title")),
					"Title on advanced filters popup is incorrect");
			userPage.clickOnElementsOfUserPage("newFilterButton");
			LOGGER.info("Clicked on add new filter");
			softAssert.assertTrue(
					userPage.getTextOfUserPage("newfilterTitle")
							.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "queryBuilder.newFilter")),
					"Title on new filters popup is incorrect");
			softAssert.assertTrue(
					userPage.getTextOfUserPage("filterNameLabel").equalsIgnoreCase(
							getTextLanguage(LanguageCode, "daas_ui", "queryBuilder.filter.filterName")),
					"Filter name label on new filters popup is incorrect");
			userPage.enterTextForUserPage("filterNameTextbox", filterName);
			userPage.clickByJavaScriptOnElementsOfUserPage("uncheckedPrivateFilter");
			softAssert.assertTrue(userPage.getTextOfUserPage("criteriaLabel").equalsIgnoreCase(
					getTextLanguage(LanguageCode, "daas_ui", "queryBuilder.filter.criteria").replace("{number}", "1")),
					"Criteria label on new filters popup is incorrect");
			userPage.clickOnElementsOfUserPage("fieldDropdown");
			userPage.updateValueOfDropdown("fieldListbox", UserVariables.FIELD, "fieldDropdown");
			userPage.clickOnElementsOfUserPage("operatorDropdown");
			userPage.updateValueOfDropdown("operatorListbox", UserVariables.OPERATOR_EQUAL_TO, "operatorDropdown");
			userPage.clickOnElementsOfUserPage("valueDropdown");
			userPage.updateValueOfDropdown("valueListbox", UserVariables.STATUS_VALUE, "valueDropdown");
			pressKey(Keys.ESCAPE);
			userPage.clickOnElementsOfUserPage("addCriteriaButton");
			LOGGER.info("Clicked on add new criteria button");
			softAssert.assertTrue(userPage.getTextOfUserPage("criteria2Label").equalsIgnoreCase(
					getTextLanguage(LanguageCode, "daas_ui", "queryBuilder.filter.criteria").replace("{number}", "2")),
					"Criteria did not get added on new filters popup");
			userPage.clickOnElementsOfUserPage("criteriaDeleteButton");
			userPage.clickOnElementsOfUserPage("saveQueryButton");
			userPage.clickOnElementsOfUserPage("saveAndRunFilterAction");
			LOGGER.info("Clicked on save and run filter");
			Thread.sleep(3000);
			userPage.clickOnElementsOfUserPage("querySearchBox");
			userPage.enterTextForUserPage("querySearchBox",filterName);
			waitForPageLoaded();
			softAssert.assertTrue(userPage.matchTextOfUserPage("queryName", filterName), "Query did not get added");
			sleeper(3000);
			userPage.scrollOnUserPage("StatusBoxTitle");
			softAssert.assertTrue(userPage.matchTextOfListOfUserPage("userStatusListCaseCreation", UserVariables.STATUS_VALUE),
					"Query did not run successfully");
			userPage.clickByJavaScriptOnUserPage("closeErrorMessage");

			userPage.clickOnElementsOfUserPage("firstQueryEllipsis");
			userPage.clickOnElementsOfUserPage("viewAction");
			LOGGER.info("Clicked on edit filter");
			filterName = generateRandomString(6);
			userPage.enterTextForUserPage("filterNameTextbox", filterName);
			userPage.clickOnElementsOfUserPage("saveQueryButton");
			userPage.clickOnElementsOfUserPage("saveAndRunFilterAction");
			userPage.clickOnElementsOfUserPage("querySearchBox");
			userPage.enterTextForUserPage("querySearchBox",filterName);
			waitForPageLoaded();
			userPage.waitForElementsOfUserPage("queryName");
			softAssert.assertTrue(userPage.matchTextOfUserPage("queryName", filterName), "Query did not get updated");
			userPage.clickByJavaScriptOnUserPage("closeErrorMessage");

			userPage.clickOnElementsOfUserPage("firstQueryEllipsis");
			userPage.clickOnElementsOfUserPage("favoriteAction");
			LOGGER.info("Clicked on favorite action");
			userPage.clickByJavaScriptOnUserPage("closeErrorMessage");
			softAssert.assertTrue(userPage.verifyElementsOfUserPage("firstFavoriteButton"),
					"Query not updated as favourite");
			userPage.clickOnElementsOfUserPage("firstQueryEllipsis");
			userPage.clickOnElementsOfUserPage("favoriteAction");
			userPage.clickByJavaScriptOnUserPage("closeErrorMessage");
			sleeper(1000);

			userPage.clickOnElementsOfUserPage("firstQueryEllipsis");
			userPage.clickOnElementsOfUserPage("deleteAction");
			LOGGER.info("Clicked on deleted action");
			userPage.clickOnElementsOfUserPage("deleteQueryConfirm");
			sleeper(1000);
			softAssert.assertFalse(userPage.verifyElementsOfUserPage("queryName"), "Query did not get deleted");

		softAssert.assertAll();
		LOGGER.info("Verification of advanced filters done successfully");
	}
	
	/**
	 * This test case validates the context sensitive help links on Users screen
	 *
	 * @throws Exception
	 */
	@Test(priority = 40, groups = { "REGRESSIONSETTINGS2", "SMOKE", "REGRESSION_CI",
	"SMOKE_CI" }, description = "Test Case ID : 891080")
	public final void verifyOnlineHelpLinksOnUsers() throws Exception {
		login("ITA_COMPANY_DETAILS_EMAIL", "ITA_COMPANY_DETAILS_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();

		// Verify help and support link for Users tab
		waitForPageLoaded();
		gotoUserCompanyUserTab();
		LOGGER.info("Redirected to Users tab");
		settingsPage.waitForElementsOfSettingsPage("helpAndSupportIcon");
		settingsPage.clickOnElementsOfSettingsPage("helpAndSupportIcon");
		softAssert.assertTrue(
				settingsPage.matchTextOfSettingsPage("help&SupportLink",
						getTextLanguage(LanguageCode, "daas_ui", "settings.help_and_support.title")),
				"Help & Support link text did not match on Users tab");
		settingsPage.clickOnElementsOfSettingsPage("help&SupportLink");
		LOGGER.info("Clicked on Help & Support link from Users tab");
		waitForPageLoaded();
		softAssert.assertTrue(settingsPage.getTextOfSettingsPage("helpAndSupportTitleTextOnBreadcrumbs").equals(settingsPage.getTextLanguage(LanguageCode, "daas_ui", "sidemenu.support")),
				"User not redirected to Help & Support tab after clicking on Help & Support link from Users tab");
		gotoUserCompanyUserTab();

		// Verify overview link for Users tab
		waitForPageLoaded();
		LOGGER.info("Redirected to Users tab");
		settingsPage.waitForElementsOfSettingsPage("helpAndSupportIcon");
		settingsPage.clickOnElementsOfSettingsPage("helpAndSupportIcon");
		softAssert.assertTrue(
				settingsPage.matchTextOfSettingsPage("usersOverviewLink",
						getTextLanguage(LanguageCode, "daas_ui", "sidemenu.customerUsers.overview")),
				"Incidents overview link text did not match on Users tab");
		settingsPage.clickOnElementsOfSettingsPage("usersOverviewLink");
		LOGGER.info("Clicked on overview link from Users tab");
		settingsPage.switchToDifferentTab();
		sleeper(4000);// Url takes time to load
		softAssert.assertTrue(settingsPage.getUrlOfCurrentPage().contains(ConstantURL.KNOWLEDGE_BASE),
				"User not redirected to Online help section after clicking on overview link from Users tab");
		softAssert.assertTrue(settingsPage.getUrlOfCurrentPage().contains(ConstantURL.LANGUAGE_PARAMETER),
				"Language parameter not added in Service now URL");
		settingsPage.switchBackToPreviousTab();

		softAssert.assertAll();
		LOGGER.info("Test case to verify context sensitive links on Users passed successfully.");
	}

	/**
	 * This test case validates App Switcher for "Contracts & Orders" SSO *
	 * 
	 * @throws Exception
	 */
	@Test(priority = 41, groups = { "REGRESSIONSETTINGS2", "SMOKE" }, description = "Test Case ID : 941149", enabled = false)

	public final void verifyPSPortalInAppSwitcher() throws Exception {
		login("ITA_COMPANY_DETAILS_EMAIL", "ITA_COMPANY_DETAILS_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		UserPage userPage = new UserPage(PreDefinedActions.getDriver()).getInstance();
		waitForPageLoaded();
		userPage.clickOnElementsOfUserPage("uiSwitchergrid");
		LOGGER.info("Clicked on App Switcher Grid");
		softAssert.assertTrue(
				userPage.getTextOfUserPage("contractAndOrders").equalsIgnoreCase(UserVariables.CONTRACT_AND_ORDERS),
				"Contract and Orders menu is not available");
		LOGGER.info("Menu is Present Verified successfully");
		userPage.clickOnElementsOfUserPage("contractAndOrders");
		softAssert.assertTrue(userPage.getTextOfUserPage("welcomeToPSportal")
				.equalsIgnoreCase(UserVariables.PS_SERVICE_WELCOME_PAGE));
		softAssert.assertAll();
		LOGGER.info("SSO login to PS portal is verified successfully");
	}
			
	@Test(priority = 42, groups = { "REGRESSIONSETTINGS2", "SMOKE" }, description = "Test Case ID : 778706")
	
	public final void verifyProtectAndTraceLabel() throws Exception 
	
	{
		login("FLIP_REPORT_ADMIN_EMAIL1", "FLIP_REPORT_ADMIN_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
		DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
		gotoCompanySettingsTab();;		
		waitForPageLoaded();
		LOGGER.info("Redirected to Settings tab");
		
		//Verify Name on Protect and trace should be present on company prefernce tab 
		settingsPage.waitForElementsOfSettingsPage("preferenceTab");
		settingsPage.clickOnElementsOfSettingsPage("preferenceTab");			
		softAssert.assertTrue(settingsPage.getTextOfSettingsPage("panelHeader").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.lockanderase").toUpperCase()), "Header Text - HP Wolf Protect And Trace title did not match.");
		softAssert.assertTrue(settingsPage.getTextOfSettingsPage("panelWarning").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section_description.lockanderase").toUpperCase()), "Warning Text - HP Wolf Protect And Trace title did not match.");
		softAssert.assertTrue(settingsPage.getTextOfSettingsPage("labelText").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "assets.table.heading.lockAndErase").toUpperCase()), "Label Text - HP Wolf Protect And Trace title did not match.");
		
		//Verify Name on Device list Page
		gotoDevicesTab();
		softAssert.assertTrue(deviceListPage.getTextOfDeviceListPage("ProtectTraceColumn").equalsIgnoreCase(getTextLanguage(LanguageCode, "idm", "onboarding.protectandtrace.service_name")), "Protect and Trace column text is not matching");
				
		//Verify Name on User list page
		gotoUserTab();
		UserPage userPage = new UserPage(PreDefinedActions.getDriver()).getInstance();
		softAssert.assertTrue(userPage.getTextOfUserPage("protectandtraceapprover").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "users.list.label.lockAndEraseApproversStatus")), "Protect and Trace column text is not matching");
		softAssert.assertAll();
		LOGGER.info("Protect and Trace text is verified successfully");
	}
	
}