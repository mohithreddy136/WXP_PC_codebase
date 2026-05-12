package com.testscripts.daasui;

import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.basesource.action.MailinatorMail;
import com.basesource.action.PreDefinedActions;
import com.basesource.utils.CompanyPin;
import com.basesource.utils.EnrollFakeDevice;
import com.basesource.utils.Mailinator;
import com.daasui.constants.CommonVariables;
import com.daasui.constants.CompaniesVariables;
import com.daasui.constants.ConstantPath;
import com.daasui.constants.ConstantURL;
import com.daasui.constants.DashboardVariables;
import com.daasui.constants.DeviceVariables;
import com.daasui.constants.LicensesOrdersvariables;
import com.daasui.pages.CompaniesDetailsPage;
import com.daasui.pages.CompaniesPage;
import com.daasui.pages.DashboardPage;
import com.daasui.pages.DeviceDetailsPage;
import com.daasui.pages.DeviceListPage;
import com.daasui.pages.ErrorPage;
import com.daasui.pages.HPDexPage;
import com.daasui.pages.IncidentDetailsPage;
import com.daasui.pages.IncidentPage;
import com.daasui.pages.LicenseOrdersPage;
import com.daasui.pages.LicensesListPage;
import com.daasui.pages.LogPage;
import com.daasui.pages.PartnerDetailsPage;
import com.daasui.pages.PartnerPage;
import com.daasui.pages.PreferencesPage;
import com.daasui.pages.AnalyticsPage;
import com.daasui.pages.SettingsPage;
import com.daasui.pages.TableConfigurationPage;
import com.daasui.pages.UserDetailsPage;
import com.daasui.pages.UserPage;
import com.daasui.pages.WelcomePage;
import com.daasui.pages.WhatsNewPage;
import com.google.common.base.Strings;

public class CompaniesTest extends CommonTest {
	private static Logger LOGGER = LogManager.getLogger(CompaniesTest.class);
	SoftAssert sa = new SoftAssert();
	public static String importCompany = getEnvironmentSpecificData(System.getProperty("environment"), "DEVICE_IMPORT_COMPANY");
	String companyName,tenantIdCompany,apiurl,companyID,MSPID,billingModelTenantID;
	public static String tenantID;
	public static String UPIDeviceName = getEnvironmentSpecificData(System.getProperty("environment"), "UPI_DEVICE_NAME");
	public static String MSPCompany = getEnvironmentSpecificData(System.getProperty("environment"), "ROOT_ADMIN_TEST_COMPANY");
	public static String NonRootCompany = getEnvironmentSpecificData(System.getProperty("environment"), "NON_ROOT_COMPANY");
	public static String productionCompany = getEnvironmentSpecificData(System.getProperty("environment"), "PRODUCTION_TEST_COMPANY");
	public static String SubscriptionKeyResource = "settings/subscriptions.json?company_idm_guid=" + getEnvironmentSpecificData(System.getProperty("environment"), "STATIC_COMPANY_TENANT_ID");
	public static String companyId = getEnvironmentSpecificData(System.getProperty("environment"), "COMPANY_ID");
	public static String staticComapny = getEnvironmentSpecificData(System.getProperty("environment"), "STATIC_COMPANY");
	public static String inviteComapny = getEnvironmentSpecificData(System.getProperty("environment"), "COMPANY_NAME_DETAIL_PLANSLIST");
	public static String resellerCompany = getEnvironmentSpecificData(System.getProperty("environment"), "RESELLER_COMPANY_NAME");
	public static String resellerCompanyId = getEnvironmentSpecificData(System.getProperty("environment"), "RESELLER_COMPANY_ID");
	public static String authCompany = getEnvironmentSpecificData(System.getProperty("environment"), "AUTO_AUTH_COMPANY");
	public static String incidentID = getEnvironmentSpecificData(System.getProperty("environment"), "INCIDENT_ID");
	public static String autoCompany = getEnvironmentSpecificData(System.getProperty("environment"), "AUTOMATION_COMPANY");
	public static String impoCompany = getEnvironmentSpecificData(System.getProperty("environment"), "AUTOMATION_IMPO_COMPANY");
	public static String detailsCompany = getEnvironmentSpecificData(System.getProperty("environment"), "COMPANY_NAME");
	public static String currentUrl = null;
	public static String cataLystURL = getEnvironmentSpecificData(System.getProperty("environment"), "CATALYST_API_BASE_URL");
	public static String UPICompany = getEnvironmentSpecificData(System.getProperty("environment"), "AUTH_COMPANY_API_DO_NOT_DELETE");
	public static String subscriptionURL = "services/subscription_admin_service/1.0/licenses/";
	public static String summaryAPIResource = "services/resource_entitlement_service/1.0/tenant/" + getEnvironmentSpecificData(System.getProperty("environment"), "STATIC_COMPANY_TENANT_ID") + "/summarydetails";
	public static String proActiveSecurityCompany = getEnvironmentSpecificData(System.getProperty("environment"), "PROACTIVE_SECURITY_COMANY");
	public static String proActiveSecurityCompany1 = getEnvironmentSpecificData(System.getProperty("environment"), "PROACTIVE_SECURITY_COMANY1");
	public static String controllerHostname = getEnvironmentSpecificData(System.getProperty("environment"), "CONTROLLER_HOSTNAME");
	public static String groupID = getEnvironmentSpecificData(System.getProperty("environment"), "GROUP_ID");
	public static String proActiveTenantID = getEnvironmentSpecificData(System.getProperty("environment"), "PROACTIVE_COMPANY_TENANT_ID");
	public static String emmCompanyName = getEnvironmentSpecificData(System.getProperty("environment"), "EMM_COMPANY_NAME");
	public static final String companyBody = "{\"query\":\"{\\\"query\\\":{\\\"bool\\\":{\\\"must\\\":[{\\\"bool\\\":{\\\"should\\\":[{\\\"match\\\":{\\\"tenantType.keyword\\\":\\\"CUSTOMER\\\"}}]}},{\\\"bool\\\":{\\\"should\\\":[{\\\"match\\\":{\\\"subscriptionStateLocale.en.name.keyword\\\":\\\"ACTIVE\\\"}},{\\\"match\\\":{\\\"subscriptionStateLocale.en.name.keyword\\\":\\\"GRACE_PERIOD\\\"}}]}},{\\\"match\\\":{\\\"displayName\\\":{\\\"query\\\":\\\"" + CommonVariables.COMPANY_NAME+ "\\\",\\\"operator\\\":\\\"AND\\\"}}}]}},\\\"from\\\":0,\\\"size\\\":25,\\\"sort\\\":[{\\\"displayName.sort_en\\\":{\\\"order\\\":\\\"ASC\\\",\\\"unmapped_type\\\":\\\"string\\\"}}]}\",\"tenant_ids\":[],\"index_list\":[\"idmtenants\"],\"search_type\":\"tenanted\"}";
	public static String staticComapnyDomainName = getEnvironmentSpecificData(System.getProperty("environment"), "STATIC_COMPANY_DOMAIN_NAME");
	public static String envURL = getEnvironmentSpecificData(System.getProperty("environment"), "DAAS_API_BASE_URL");
	public static SimpleDateFormat formatTime;
	public static String notificCompany = getEnvironmentSpecificData(System.getProperty("environment"), "EMAIL_NOTIFIC_COMPANY");
	public static String emailnotificCompany = getEnvironmentSpecificData(System.getProperty("environment"), "NOTIFIC_COMPANY");
	public static String partnerCompanyAddition = getEnvironmentSpecificData(System.getProperty("environment"), "PARTNER_NAME_COMPANY_ADDITION");

	public static String sourceOfCustomerCmpName = getEnvironmentSpecificData(System.getProperty("environment"), "SOURCE_OF_CUSTOMER_COMPANY");	
	public static String sourceOfCstmrDSSP = getEnvironmentSpecificData(System.getProperty("environment"), "SOURCE_OF_CUSTOMER_DSSP");
	public static String subKey1 = "MixedPlanCompany";

	
	@DataProvider
	public Object[][] loginDataListPage() {
		Object[][] data = new Object[2][2];
		data[0][0] = "STAGING_ACCOUNT_MSP_COMPANY";
		data[0][1] = "STAGING_ACCOUNT_MSP_COMPANY_PASSWORD";
		data[1][0] = "RESELLER_STAGING_COMPANIES_EMAIL";
		data[1][1] = "RESELLER_STAGING_COMPANIES_PASSWORD";
		return data;
	}

	@DataProvider
	public Object[][] loginDataForCompanySettingsChange() {
		Object[][] data = new Object[3][2];
		data[0][0] = "NEW_MSP_ADMIN_US_EMAIl";
		data[0][1] = "NEW_MSP_ADMIN_US_PASSWORD";
		data[1][0] = "RESELLER_STAGING_COMPANIES_EMAIL";
		data[1][1] = "RESELLER_STAGING_COMPANIES_PASSWORD";
		data[2][0] = "IT_ADMIN_PARTNERS_EMAIL";
		data[2][1] = "IT_ADMIN_PARTNERS_PASSWORD";
		return data;
	}
	@DataProvider
	public Object[][] loginDataForCompanySourceOfCustomerField() {
		Object[][] data = new Object[3][2];
		data[0][0] = "ROOT_ADMIN_NEW_USER_US";
		data[0][1] = "ROOT_ADMIN_NEW_PASSWORD_US";
		data[1][0] = "MSP_NEW_USER_US";
		data[1][1] = "MSP_NEW_USER_PASSWORD_US";
		data[2][0] = "PARNTER_NEW_USER_US";
		data[2][1] = "PARTNER_NEW_PASSWORD_US";
		return data;
	}
	/**
	 * This method verifies the title on companies list page
	 * 
	 * @throws Exception
	 */
	@Test(priority = 1, groups = { "REGRESSIONCOMPANIES1" }, description = "TEST CASE - 280820")
	public final void verifyCompaniesTitle() throws Exception {
		login("NEW_MSP_ADMIN_US_EMAIl", "NEW_MSP_ADMIN_US_PASSWORD");
		gotoCompaniesTab();
		LOGGER.info("Redirected to company list page");
		sleeper(3000);
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();

		if (toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, "MSP_COMPANIES_LIST_EMAIL"))) {
			companiesPage.waitForElementsOfCompaniesPage("companiesTitleOnBreadcrumbs");
			Assert.assertTrue(companiesPage.getTextOfCompaniesPage("companiesTitleOnBreadcrumbs").equals(companiesPage.getTextLanguage(LanguageCode, "daas_ui", "pagetitle.companies")), "Companies page title did not match");
			LOGGER.info("Companies title matched");
		}else{
		Assert.assertTrue(companiesPage.getTextOfCompaniesPage("companiesTitle").equals(companiesPage.getTextLanguage(LanguageCode, "daas_ui", "pagetitle.companies")), "Companies page title did not match");
		LOGGER.info("Companies title matched");
		}
	}

	@DataProvider
	public Object[][] loginData() {
		//Object[][] data = new Object[0][0];
		//data[0][0] = "MSP_COMPANIES_LIST_EMAIL";
		//data[0][1] = "MSP_COMPANIES_LIST_PASSWORD";
		//data[1][0] = "RESELLER_COMPANIES_LIST_EMAIL";
		//data[1][1] = "RESELLER_COMPANIES_LIST_PASSWORD";
		//data[2][0] = "ROOT_ADMIN_NEW_USER_COMPANIES";
		//data[2][1] = "ROOT_ADMIN_NEW_PASSWORD_COMPANIES";
		return new Object[][] {
			{"NEW_MSP_ADMIN_US_EMAIl", "NEW_MSP_ADMIN_US_PASSWORD"}
		};
	}

	/**
	 * This test case verifies pagination on company list page
	 * 
	 * @param username - name of user fetched from data provider
	 * @param password - password of user fetched from data provider
	 * @throws Exception
	 */
	@Test(priority = 2, description = "TEST CASE - 240870 ; Role ~ MSP", groups = { "REGRESSIONCOMPANIES1", "PRODUCTION_SANITY", "STABILIZATION_STAGING" })
	public final void verifyPaginationOnCompanyPage() throws Exception {
		login("NEW_MSP_ADMIN_US_EMAIl","NEW_MSP_ADMIN_US_PASSWORD");
		gotoCompaniesTab();
		LOGGER.info("Redirected to company list page");

		verifyPaginationOnListPage();
	}

	/**
	 * This method will verify the table configuration test cases of company list page for MSP
	 * 
	 * @throws Exception
	 */
	@Test(priority = 3, description = "TEST CASE - 240872, 240868", groups = { "REGRESSIONCOMPANIES1", "SMOKE", "REGRESSION_CI", "SMOKE_CI", "STABILIZATION_STAGING" })
	public final void verifyTableConfigurationTestCasesForMSP() throws Exception {
		login("NEW_MSP_ADMIN_US_EMAIl", "NEW_MSP_ADMIN_US_PASSWORD");
		String tenantID = getValueFromToken("tenant");
		gotoCompaniesTab();
		LOGGER.info("Redirected to company list page");
		waitForPageLoaded();
		// verifying search service api
		Assert.assertTrue(getStatusCode(getSearchServiceApi(environment) + CommonVariables.DETAILSSEARCHSERVICEAPI + tenantID + CommonVariables.DETAILSSEARCHSERVICEAPI2, CompaniesVariables.SEARCHSERVICEBODY, "POST", getEnvironment(environment)) == CommonVariables.CODEOK, "Search service failed for company list page");
		ArrayList<String> columnName = new ArrayList<String>(Arrays.asList(getTextLanguage(LanguageCode, "daas_ui", "companies.list.name"), getTextLanguage(LanguageCode, "daas_ui", "subscription.section.plans"), getTextLanguage(LanguageCode, "daas_ui", "companies.list.primary_account_admin"), getTextLanguage(LanguageCode, "daas_ui", "company.add.lable.reseller"), getTextLanguage(LanguageCode, "daas_ui", "companies.list.status"), getTextLanguage(LanguageCode, "daas_ui", "companies.list.billingModel"), getTextLanguage(LanguageCode, "daas_ui", "companies.list.seatUtilStatus"),getTextLanguage(LanguageCode, "daas_ui", "companies.list.3rd_party_application"), getTextLanguage(LanguageCode, "daas_ui", "companies.list.address"),
				getTextLanguage(LanguageCode, "daas_ui", "companies.list.company_pin"), getTextLanguage(LanguageCode, "daas_ui", "companies.list.domain_name"), getTextLanguage(LanguageCode, "daas_ui", "companies.list.phone_number"),
				getTextLanguage(LanguageCode, "daas_ui", "companies.list.country"),getTextLanguage(LanguageCode, "daas_ui", "companies.list.preferred_time_zone"), getTextLanguage(LanguageCode, "daas_ui", "company.add.lable.number_of_employees"), getTextLanguage(LanguageCode, "daas_ui", "companies.list.service_now_url"), getTextLanguage(LanguageCode, "daas_ui", "companies.list.service_now_configuration_type"), getTextLanguage(LanguageCode, "daas_ui", "roles.list.created_on"), getTextLanguage(LanguageCode, "daas_ui", "companies.list.partnerAuth")));
		ArrayList<String> checkboxValue = new ArrayList<String>(Arrays.asList("true", "true", "true", "true", "true", "false", "false", "false", "false", "false", "false", "false", "false", "false", "false", "false", "false", "false", "true"));
		ArrayList<String> name = new ArrayList<String>(Arrays.asList(getTextLanguage(LanguageCode, "daas_ui", "companies.list.name")));

		verifyTableConfigurationTests(columnName, checkboxValue, name);
	}

	/**
	 * This method will verify the table configuration test cases of company list page for Reseller
	 * Disabling the test case since this is redundant one.
	 * @throws Exception
	 */
	@Test(priority = 4, description = "TEST CASE - 240872, 240868", groups = { "REGRESSIONCOMPANIES1", "SMOKE", "REGRESSION_CI", "SMOKE_CI", "STABILIZATION_STAGING" },enabled=false)
	public final void verifyTableConfigurationTestCasesForReseller() throws Exception {
		login("RESELLER_STAGING_COMPANIES_EMAIL", "RESELLER_STAGING_COMPANIES_EMAIL");
		String tenantID = getValueFromToken("tenant");
		gotoCompaniesTab();
		LOGGER.info("Redirected to company list page");
		waitForPageLoaded();
		// verifying search service api
		Assert.assertTrue(getStatusCode(getSearchServiceApi(environment) + CommonVariables.DETAILSSEARCHSERVICEAPI + tenantID + CommonVariables.DETAILSSEARCHSERVICEAPI2, CompaniesVariables.SEARCHSERVICEBODYRESELLER, "POST", getEnvironment(environment)) == CommonVariables.CODEOK, "Search service failed for company list page");
		ArrayList<String> columnName = new ArrayList<String>(Arrays.asList(getTextLanguage(LanguageCode, "daas_ui", "companies.list.name"),getTextLanguage(LanguageCode, "daas_ui", "companies.list.status"), getTextLanguage(LanguageCode, "daas_ui", "subscription.section.plans"),getTextLanguage(LanguageCode, "daas_ui", "companies.list.total_seats"),getTextLanguage(LanguageCode, "daas_ui", "companies.list.seatUtilStatus"),getTextLanguage(LanguageCode, "daas_ui", "companies.list.billingModel"),getTextLanguage(LanguageCode, "daas_ui", "companies.list.partnerAssignment"), getTextLanguage(LanguageCode, "daas_ui", "companies.list.primary_account_admin"), getTextLanguage(LanguageCode, "daas_ui", "companies.list.managed_service_provider"),
				getTextLanguage(LanguageCode, "daas_ui", "companies.list.address"), getTextLanguage(LanguageCode, "daas_ui", "companies.list.domain_name"), getTextLanguage(LanguageCode, "daas_ui", "companies.list.phone_number"), getTextLanguage(LanguageCode, "daas_ui", "companies.list.preferred_time_zone"), getTextLanguage(LanguageCode, "daas_ui", "company.add.lable.number_of_employees"), getTextLanguage(LanguageCode, "daas_ui", "companies.list.service_now_url"),
				getTextLanguage(LanguageCode, "daas_ui", "companies.list.country"),getTextLanguage(LanguageCode, "daas_ui", "companies.list.service_now_configuration_type"), getTextLanguage(LanguageCode, "daas_ui", "roles.list.created_on")));
		ArrayList<String> checkboxValue = new ArrayList<String>(Arrays.asList("true", "true", "true", "true","false", "false", "true", "false", "false", "false", "false", "false", "false", "false", "false", "false", "false", "false"));
		ArrayList<String> firstColumnName = new ArrayList<String>(Arrays.asList(getTextLanguage(LanguageCode, "daas_ui", "companies.list.name")));

		verifyTableConfigurationTests(columnName, checkboxValue, firstColumnName);
	}

	/**
	 * This method will verify the table configuration test cases of company list page for Root
	 * Disabling the test case since this is redundant one.
	 * @throws Exception
	 */
	@Test(priority = 5, description = "TEST CASE - 240872, 240868", groups = { "REGRESSIONCOMPANIES1", "SMOKE", "REGRESSION_CI", "SMOKE_CI", "STABILIZATION_STAGING" },enabled = false)
	public final void verifyTableConfigurationTestCasesForRoot() throws Exception {
		login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
		LOGGER.info("Redirected to company list page");
		// verifying search service api
		String tenantID = getValueFromToken("tenant");
		waitForPageLoaded();
		Assert.assertTrue(getStatusCode(getSearchServiceApi(environment) + CommonVariables.DETAILSSEARCHSERVICEAPI + tenantID + CommonVariables.DETAILSSEARCHSERVICEAPI2, CompaniesVariables.SEARCHSERVICEBODYROOT, "POST", getEnvironment(environment)) == CommonVariables.CODEOK, "Search service failed for company list page");
		ArrayList<String> columnName = new ArrayList<String>(Arrays.asList(getTextLanguage(LanguageCode, "daas_ui", "companies.list.name"), getTextLanguage(LanguageCode, "daas_ui", "subscription.section.plans"), getTextLanguage(LanguageCode, "daas_ui", "companies.list.billingModel"), getTextLanguage(LanguageCode, "daas_ui", "companies.list.seatUtilStatus"), getTextLanguage(LanguageCode, "daas_ui", "companies.list.primary_account_admin"), getTextLanguage(LanguageCode, "daas_ui", "companies.list.primary_account_email"), getTextLanguage(LanguageCode, "daas_ui", "companies.list.primary_account_phone"),
				getTextLanguage(LanguageCode, "daas_ui", "company.add.lable.reseller"), getTextLanguage(LanguageCode, "daas_ui", "company.add.lable.msp"), getTextLanguage(LanguageCode, "daas_ui", "companies.list.status"), getTextLanguage(LanguageCode, "daas_ui", "company.add.lable.number_of_employees"), getTextLanguage(LanguageCode, "daas_ui", "companies.list.address"), getTextLanguage(LanguageCode, "daas_ui", "companies.list.service_now_url"),
				getTextLanguage(LanguageCode, "daas_ui", "companies.list.country"),getTextLanguage(LanguageCode, "daas_ui", "companies.list.service_now_configuration_type"), getTextLanguage(LanguageCode, "daas_ui", "roles.list.created_on"), getTextLanguage(LanguageCode, "daas_ui", "companies.list.partnerAuth")));
		ArrayList<String> checkboxValue = new ArrayList<String>(Arrays.asList("true", "true", "false", "false", "true", "true", "true", "true", "true", "true", "false", "false", "false", "false", "false", "false", "true"));
		ArrayList<String> firstColumnName = new ArrayList<String>(Arrays.asList(getTextLanguage(LanguageCode, "daas_ui", "companies.list.name")));
		verifyTableConfigurationTests(columnName, checkboxValue, firstColumnName);

	}

	/**
	 * This test case verifies filter functionality on company list page for MSP
	 * 
	 * @throws Exception
	 */
	@Test(priority = 6, description = "TEST CASE - 240871", groups = { "REGRESSIONCOMPANIES1", "SMOKE", "REGRESSION_CI", "SMOKE_CI", "STABILIZATION_STAGING" }, enabled = false)
	public final void verifyFilterFunctionalityForMSP() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login("MSP_COMPANIES_LIST_EMAIL", "MSP_COMPANIES_LIST_PASSWORD");
		gotoCompaniesTab();
		LOGGER.info("Redirected to Companies tab");
		waitForPageLoaded();
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();

		resetTableConfiguration();
		companiesPage.waitUntilElementIsInvisibleOfCompanyPage("tableOverlay");
		Assert.assertFalse(companiesPage.verifyElementsOfCompaniesPage("noElementsDisplayText"), "No company is present on company page, unable to proceed further");
		LOGGER.info("Atleast one company is present, test case started");
		companiesPage.waitForElementsOfCompaniesPage("firstCompany");
		TableConfigurationPage tableConfigurationPage = new TableConfigurationPage(PreDefinedActions.getDriver()).getInstance();
		tableConfigurationPage = tableConfigurationPage.getInstance();
		tableConfigurationPage.waitUntillElementIsPresentOftableConfigurationPage("tableOverlay");

		// This test case validates if the filter functionality is working
		// properly for
		// the searchbox on name column
		softAssert.assertTrue(companiesPage.verifySearchValueOnCompany(LanguageCode, "nameSearchBox", CompaniesVariables.INCORRECT_SEARCH_STRING, "noElementsDisplayText", "nameList"), "Search functionality with incorrect search string for name column on company list page is not working");
		softAssert.assertTrue(companiesPage.verifySearchValueOnCompany(LanguageCode, "nameSearchBox", staticComapny, "noElementsDisplayText", "nameList"), "Search functionality for name column on company list page is not working");
		LOGGER.info("Verified filter functionality for name column");

		// This test case validates if the filter functionality is working
		// properly for
		// the dropdown on Subscription column
		companiesPage.waitUntilElementIsInvisibleOfCompanyPage("tableOverlay");
		companiesPage.clickOnElementsOfCompaniesPage("subscriptionBoxBefore");
		softAssert.assertTrue(companiesPage.verifyFilterSingleSelect(LanguageCode, "dropDownCheckBoxes", "dropDownElementListLabels", "subscriptionList", "noElementsDisplayText"), "Filter functionality on selecting single option from subscription column on company list page is not working");
		companiesPage.waitUntilElementIsInvisibleOfCompanyPage("tableOverlay");
		if (companiesPage.verifyElementsOfCompaniesPage("clearFiltersButton"))
			companiesPage.clickOnElementsOfCompaniesPage("clearFiltersButton");
		companiesPage.waitUntilElementIsInvisibleOfCompanyPage("tableOverlay");

		companiesPage.clickOnElementsOfCompaniesPage("subscriptionBoxBefore");
		softAssert.assertTrue(companiesPage.verifyFilterMultiSelect(LanguageCode, "dropDownCheckBoxes", "dropDownElementListLabels", "subscriptionList", "noElementsDisplayText"), "Filter functionality on selecting multiple options from subscription column on company list page is not working");
		companiesPage.waitUntilElementIsInvisibleOfCompanyPage("tableOverlay");
		if (companiesPage.verifyElementsOfCompaniesPage("clearFiltersButton"))
			companiesPage.clickOnElementsOfCompaniesPage("clearFiltersButton");
		companiesPage.waitUntilElementIsInvisibleOfCompanyPage("tableOverlay");
		LOGGER.info("Verified filter functionality for Subscription column");

		// This test case validates if the filter functionality is working
		// properly for
		// the dropdown on Primary Account column

		companiesPage.clickOnElementsOfCompaniesPage("primaryAccountBoxBefore");
		softAssert.assertTrue(companiesPage.verifyFilterSingleSelect(LanguageCode, "dropDownCheckBoxes", "dropDownElementListLabels", "primaryAccountList", "noElementsDisplayText"), "Filter functionality on selecting single option from Primary Account column on company list page is not working");
		companiesPage.waitUntilElementIsInvisibleOfCompanyPage("tableOverlay");
		if (companiesPage.verifyElementsOfCompaniesPage("clearFiltersButton"))
			companiesPage.clickOnElementsOfCompaniesPage("clearFiltersButton");
		companiesPage.waitUntilElementIsInvisibleOfCompanyPage("tableOverlay");

		companiesPage.clickOnElementsOfCompaniesPage("primaryAccountBoxBefore");
		softAssert.assertTrue(companiesPage.verifyFilterMultiSelect(LanguageCode, "dropDownCheckBoxes", "dropDownElementListLabels", "primaryAccountList", "noElementsDisplayText"), "Filter functionality on selecting multiple options from Primary Account column on company list page is not working");
		companiesPage.waitUntilElementIsInvisibleOfCompanyPage("tableOverlay");
		if (companiesPage.verifyElementsOfCompaniesPage("clearFiltersButton"))
			companiesPage.clickOnElementsOfCompaniesPage("clearFiltersButton");
		companiesPage.waitUntilElementIsInvisibleOfCompanyPage("tableOverlay");
		LOGGER.info("Verified filter functionality for Primary Account column");

		// This test case validates if the filter functionality is working
		// properly for // the searchbox on partner column
		softAssert.assertTrue(companiesPage.verifySearchValueOnCompany(LanguageCode, "partnerSearchBox", CompaniesVariables.INCORRECT_SEARCH_STRING, "noElementsDisplayText", "partnerList"), "Search functionality with incorrect search string for partner column on company list page is not working");
		softAssert.assertTrue(companiesPage.verifySearchValueOnCompany(LanguageCode, "partnerSearchBox", CompaniesVariables.PARTNER, "noElementsDisplayText", "partnerList"), "Search functionality for partner column on company list page is not working");
		LOGGER.info("Verified filter functionality for partner column");

		// This test case validates if the filter functionality is working
		// properly for // the dropdown on status column
		// companiesPage.clickOnElementsOfCompaniesPage("statusBoxBefore");
		softAssert.assertTrue(companiesPage.verifyFilterSingleSelect(LanguageCode, "dropDownCheckBoxes", "dropDownElementListLabels", "statusList", "noElementsDisplayText"), "Filter functionality on selecting single option from status column on company list page is not working");
		companiesPage.waitUntilElementIsInvisibleOfCompanyPage("tableOverlay");
		companiesPage.clickOnElementsOfCompaniesPage("clearFiltersButton");
		companiesPage.waitUntilElementIsInvisibleOfCompanyPage("tableOverlay");

		companiesPage.clickOnElementsOfCompaniesPage("statusBoxBefore");
		softAssert.assertTrue(companiesPage.verifyFilterMultiSelect(LanguageCode, "dropDownCheckBoxes", "dropDownElementListLabels", "statusList", "noElementsDisplayText"), "Filter functionality on selecting multiple options from status column on company list page is not working");
		companiesPage.waitUntilElementIsInvisibleOfCompanyPage("tableOverlay");
		if (companiesPage.verifyElementsOfCompaniesPage("clearFiltersButton"))
			companiesPage.clickOnElementsOfCompaniesPage("clearFiltersButton");
		companiesPage.waitUntilElementIsInvisibleOfCompanyPage("tableOverlay");
		LOGGER.info("Verified filter functionality for status column");

		// This test case validates if the filter functionality is working
		// properly for // the searchbox on Address column
		softAssert.assertTrue(companiesPage.verifySearchValueOnCompany(LanguageCode, "addressSearchBox", CompaniesVariables.INCORRECT_SEARCH_STRING, "noElementsDisplayText", "addressList"), "Search functionality with incorrect search string for Address column on company list page is not working");
		softAssert.assertTrue(companiesPage.verifySearchValueOnCompany(LanguageCode, "addressSearchBox", CompaniesVariables.ADDRESS, "noElementsDisplayText", "addressList"), "Search functionality for Address column on company list page is not working");
		LOGGER.info("Verified filter functionality for Address column");

		// This test case validates if the filter functionality is working
		// properly for // the searchbox on the company pin column
		softAssert.assertTrue(companiesPage.verifySearchValueOnCompany(LanguageCode, "companyPinSearchBox", CompaniesVariables.INCORRECT_SEARCH_STRING, "noElementsDisplayText", "companyPinList"), "Search functionality with incorrect search string for the company pin column on company list page is not working");
		softAssert.assertTrue(companiesPage.verifySearchValueOnCompany(LanguageCode, "companyPinSearchBox", CompaniesVariables.COMPANY_PIN, "noElementsDisplayText", "companyPinList"), "Search functionality for the company pin column on company list page is not working");
		LOGGER.info("Verified filter functionality for the company pin column");

		// This test case validates if the filter functionality is working
		// properly for
		// the searchbox on Domain Name column
		softAssert.assertTrue(companiesPage.verifySearchValueOnCompany(LanguageCode, "domainNameSearchBox", CompaniesVariables.INCORRECT_SEARCH_STRING, "noElementsDisplayText", "domainNameList"), "Search functionality with incorrect search string for Domain Name column on company list page is not working");
		softAssert.assertTrue(companiesPage.verifySearchValueOnCompany(LanguageCode, "domainNameSearchBox", CompaniesVariables.DOMAIN_NAME, "noElementsDisplayText", "domainNameList"), "Search functionality for Domain Name column on company list page is not working");
		LOGGER.info("Verified filter functionality for Domain Name column");
		companiesPage.waitUntilElementIsInvisibleOfCompanyPage("tableOverlay");

		// This test case validates if the filter functionality is working
		// properly for // the searchbox on Preferred Time Zone column
		softAssert.assertTrue(companiesPage.verifySearchValueOnCompany(LanguageCode, "preferredTimeZoneSearchBox", CompaniesVariables.INCORRECT_SEARCH_STRING, "noElementsDisplayText", "preferredTimeZoneList"), "Search functionality with incorrect search string for Preferred Time Zone column on company list page is not working");
		softAssert.assertTrue(companiesPage.verifySearchValueOnCompany(LanguageCode, "preferredTimeZoneSearchBox", CompaniesVariables.TIME_ZONE, "noElementsDisplayText", "preferredTimeZoneList"), "Search functionality for Preferred Time Zone column on company list page is not working");
		LOGGER.info("Verified filter functionality for Preferred Time Zone column");

		// This test case validates if the filter functionality is working
		// properly for // the searchbox on ServiceNow URL column
		softAssert.assertTrue(companiesPage.verifySearchValueOnCompany(LanguageCode, "serviceNowUrlSearchBox", CompaniesVariables.INCORRECT_SEARCH_STRING, "noElementsDisplayText", "serviceNowUrlList"), "Search functionality with incorrect search string for ServiceNow URL column on company list page is not working");
		softAssert.assertTrue(companiesPage.verifySearchValueOnCompany(LanguageCode, "serviceNowUrlSearchBox", CompaniesVariables.SERVICE_NOW_URL, "noElementsDisplayText", "serviceNowUrlList"), "Search functionality for ServiceNow URL column on company list page is not working");
		LOGGER.info("Verified filter functionality for ServiceNow URL column");

		// This test case validates if the filter functionality is working
		// properly for // the searchbox on Phone Number column
		softAssert.assertTrue(companiesPage.verifySearchValueOnCompany(LanguageCode, "phoneNumberSearchBox", CompaniesVariables.INCORRECT_SEARCH_STRING, "noElementsDisplayText", "phoneNumberList"), "Search functionality with incorrect search string for Phone Number column on company list page is not working");
		softAssert.assertTrue(companiesPage.verifySearchValueOnCompany(LanguageCode, "phoneNumberSearchBox", CompaniesVariables.NUMBER, "noElementsDisplayText", "phoneNumberList"), "Search functionality for Phone Number column on company list page is not working");
		LOGGER.info("Verified filter functionality for Phone Number column");

		// This test case validates if the filter functionality is working
		// properly for // the dropdown on ServiceNow Configuration Type column
		companiesPage.clickOnElementsOfCompaniesPage("serviceNowConfigurationTypeBoxBefore");
		softAssert.assertTrue(companiesPage.verifyFilterSingleSelect(LanguageCode, "dropDownCheckBoxes", "dropDownElementListLabels", "serviceNowConfigurationTypeList", "noElementsDisplayText"), "Filter functionality on selecting single option from ServiceNow Configuration Type column on company list page is not working");
		companiesPage.waitUntilElementIsInvisibleOfCompanyPage("tableOverlay");
		companiesPage.clickOnElementsOfCompaniesPage("clearFiltersButton");
		companiesPage.waitUntilElementIsInvisibleOfCompanyPage("tableOverlay");

		companiesPage.clickOnElementsOfCompaniesPage("serviceNowConfigurationTypeBoxBefore");
		softAssert.assertTrue(companiesPage.verifyFilterMultiSelect(LanguageCode, "dropDownCheckBoxes", "dropDownElementListLabels", "serviceNowConfigurationTypeList", "noElementsDisplayText"), "Filter functionality on selecting multiple options from ServiceNow Configuration Type column on company list page is not working");
		companiesPage.waitUntilElementIsInvisibleOfCompanyPage("tableOverlay");
		if (companiesPage.verifyElementsOfCompaniesPage("clearFiltersButton"))
			companiesPage.clickOnElementsOfCompaniesPage("clearFiltersButton");
		companiesPage.waitUntilElementIsInvisibleOfCompanyPage("tableOverlay");
		LOGGER.info("Verified filter functionality for ServiceNow Configuration Type column");

		// This test case validates if the filter functionality is working
		// properly for
		// the dropdown on company size column

		companiesPage.clickOnElementsOfCompaniesPage("numberOfEmployeesBoxBefore");
		softAssert.assertTrue(companiesPage.verifyFilterSingleSelect(LanguageCode, "dropDownCheckBoxes", "dropDownElementListLabels", "numberOfEmployeesList", "noElementsDisplayText"), "Filter functionality on selecting single option from company size column on company list page is not working");
		companiesPage.waitUntilElementIsInvisibleOfCompanyPage("tableOverlay");
		if (companiesPage.verifyElementsOfCompaniesPage("clearFiltersButton"))
			companiesPage.clickOnElementsOfCompaniesPage("clearFiltersButton");
		companiesPage.waitUntilElementIsInvisibleOfCompanyPage("tableOverlay");

		companiesPage.clickOnElementsOfCompaniesPage("numberOfEmployeesBoxBefore");
		softAssert.assertTrue(companiesPage.verifyFilterMultiSelect(LanguageCode, "dropDownCheckBoxes", "dropDownElementListLabels", "numberOfEmployeesList", "noElementsDisplayText"), "Filter functionality on selecting multiple options from company size column on company list page is not working");
		companiesPage.waitUntilElementIsInvisibleOfCompanyPage("tableOverlay");
		if (companiesPage.verifyElementsOfCompaniesPage("clearFiltersButton"))
			companiesPage.clickOnElementsOfCompaniesPage("clearFiltersButton");
		companiesPage.waitUntilElementIsInvisibleOfCompanyPage("tableOverlay");
		LOGGER.info("Verified filter functionality for company size column");

		softAssert.assertAll();
		LOGGER.info("All filter functionality test-cases passed");

	}

	/**
	 * This test case verifies filter functionality for root user
	 * 
	 * @throws Exception
	 */
	@Test(priority = 7, description = "TEST CASE - 240871", groups = { "REGRESSIONCOMPANIES1", "SMOKE", "REGRESSION_CI", "SMOKE_CI" }, enabled = false)
	public final void verifyFilterFunctionalityForRoot() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login("ROOT_ADMIN_NEW_USER_COMPANIES", "ROOT_ADMIN_NEW_PASSWORD_COMPANIES");
		gotoCompaniesTab();
		LOGGER.info("Redirected to Companies tab");
		waitForPageLoaded();
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();

		resetTableConfiguration();
		Assert.assertFalse(companiesPage.verifyElementsOfCompaniesPage("noElementsDisplayText"), "No company is present on company page, unable to proceed further");
		LOGGER.info("Atleast one company is present, test case started");
		companiesPage.waitForElementsOfCompaniesPage("firstCompany");
		TableConfigurationPage tableConfigurationPage = new TableConfigurationPage(PreDefinedActions.getDriver()).getInstance();
		tableConfigurationPage = tableConfigurationPage.getInstance();
		tableConfigurationPage.waitUntillElementIsPresentOftableConfigurationPage("tableOverlay");

		// This test case validates if the filter functionality is working
		// properly for
		// the searchbox on name column
		softAssert.assertTrue(companiesPage.verifySearchValueOnCompany(LanguageCode, "nameSearchBox", CompaniesVariables.INCORRECT_SEARCH_STRING, "noElementsDisplayText", "nameList"), "Search functionality with incorrect search string for name column on company list page is not working");
		softAssert.assertTrue(companiesPage.verifySearchValueOnCompany(LanguageCode, "nameSearchBox", staticComapny, "noElementsDisplayText", "nameList"), "Search functionality for name column on company list page is not working");
		LOGGER.info("Verified filter functionality for name column");
		companiesPage.waitUntilElementIsInvisibleOfCompanyPage("tableOverlay");

		// This test case validates if the filter functionality is working
		// properly for
		// the dropdown on Subscription column
		companiesPage.clickOnElementsOfCompaniesPage("subscriptionBoxBefore");
		softAssert.assertTrue(companiesPage.verifyFilterSingleSelect(LanguageCode, "dropDownCheckBoxes", "dropDownElementListLabels", "subscriptionList", "noElementsDisplayText"), "Filter functionality on selecting single option from subscription column on company list page is not working");
		companiesPage.waitUntilElementIsInvisibleOfCompanyPage("tableOverlay");
		if (companiesPage.verifyElementsOfCompaniesPage("clearFiltersButton"))
			companiesPage.clickOnElementsOfCompaniesPage("clearFiltersButton");
		companiesPage.waitUntilElementIsInvisibleOfCompanyPage("tableOverlay");

		companiesPage.clickOnElementsOfCompaniesPage("subscriptionBoxBefore");
		softAssert.assertTrue(companiesPage.verifyFilterMultiSelect(LanguageCode, "dropDownCheckBoxes", "dropDownElementListLabels", "subscriptionList", "noElementsDisplayText"), "Filter functionality on selecting multiple options from subscription column on company list page is not working");
		companiesPage.waitUntilElementIsInvisibleOfCompanyPage("tableOverlay");
		if (companiesPage.verifyElementsOfCompaniesPage("clearFiltersButton"))
			companiesPage.clickOnElementsOfCompaniesPage("clearFiltersButton");
		companiesPage.waitUntilElementIsInvisibleOfCompanyPage("tableOverlay");
		LOGGER.info("Verified filter functionality for Subscription column");

		// This test case validates if the filter functionality is working
		// properly for
		// the dropdown on status column
		companiesPage.clickOnElementsOfCompaniesPage("statusBoxBefore");
		softAssert.assertTrue(companiesPage.verifyFilterSingleSelect(LanguageCode, "dropDownCheckBoxes", "dropDownElementListLabels", "statusList", "noElementsDisplayText"), "Filter functionality on selecting single option from status column on company list page is not working");
		companiesPage.waitUntilElementIsInvisibleOfCompanyPage("tableOverlay");
		if (companiesPage.verifyElementsOfCompaniesPage("clearFiltersButton"))
			companiesPage.clickOnElementsOfCompaniesPage("clearFiltersButton");
		companiesPage.waitUntilElementIsInvisibleOfCompanyPage("tableOverlay");

		companiesPage.clickOnElementsOfCompaniesPage("statusBoxBefore");
		softAssert.assertTrue(companiesPage.verifyFilterMultiSelect(LanguageCode, "dropDownCheckBoxes", "dropDownElementListLabels", "statusList", "noElementsDisplayText"), "Filter functionality on selecting multiple options from status column on company list page is not working");
		companiesPage.waitUntilElementIsInvisibleOfCompanyPage("tableOverlay");
		if (companiesPage.verifyElementsOfCompaniesPage("clearFiltersButton"))
			companiesPage.clickOnElementsOfCompaniesPage("clearFiltersButton");
		companiesPage.waitUntilElementIsInvisibleOfCompanyPage("tableOverlay");
		LOGGER.info("Verified filter functionality for status column");

		// This test case validates if the filter functionality is working
		// properly for // the searchbox on Address column
		softAssert.assertTrue(companiesPage.verifySearchValueOnCompany(LanguageCode, "addressSearchBox", CompaniesVariables.INCORRECT_SEARCH_STRING, "noElementsDisplayText", "addressList"), "Search functionality with incorrect search string for Address column on company list page is not working");
		softAssert.assertTrue(companiesPage.verifySearchValueOnCompany(LanguageCode, "addressSearchBox", CompaniesVariables.ADDRESS, "noElementsDisplayText", "addressList"), "Search functionality for Address column on company list page is not working");
		LOGGER.info("Verified filter functionality for Address column");

		// This test case validates if the filter functionality is working
		// properly for
		// the searchbox on ServiceNow URL column
		softAssert.assertTrue(companiesPage.verifySearchValueOnCompany(LanguageCode, "serviceNowUrlSearchBox", CompaniesVariables.INCORRECT_SEARCH_STRING, "noElementsDisplayText", "serviceNowUrlList"), "Search functionality with incorrect search string for ServiceNow URL column on company list page is not working");
		softAssert.assertTrue(companiesPage.verifySearchValueOnCompany(LanguageCode, "serviceNowUrlSearchBox", CompaniesVariables.SERVICE_NOW_URL, "noElementsDisplayText", "serviceNowUrlList"), "Search functionality for ServiceNow URL column on company list page is not working");
		LOGGER.info("Verified filter functionality for ServiceNow URL column");
		companiesPage.waitUntilElementIsInvisibleOfCompanyPage("tableOverlay");

		// This test case validates if the filter functionality is working
		// properly for
		// the dropdown on ServiceNow Configuration Type column
		companiesPage.clickOnElementsOfCompaniesPage("serviceNowConfigurationTypeBoxBefore");
		softAssert.assertTrue(companiesPage.verifyFilterSingleSelect(LanguageCode, "dropDownCheckBoxes", "dropDownElementListLabels", "serviceNowConfigurationTypeList", "noElementsDisplayText"), "Filter functionality on selecting single option from ServiceNow Configuration Type column on company list page is not working");
		companiesPage.waitUntilElementIsInvisibleOfCompanyPage("tableOverlay");
		if (companiesPage.verifyElementsOfCompaniesPage("clearFiltersButton"))
			companiesPage.clickOnElementsOfCompaniesPage("clearFiltersButton");
		companiesPage.waitUntilElementIsInvisibleOfCompanyPage("tableOverlay");

		companiesPage.clickOnElementsOfCompaniesPage("serviceNowConfigurationTypeBoxBefore");
		softAssert.assertTrue(companiesPage.verifyFilterMultiSelect(LanguageCode, "dropDownCheckBoxes", "dropDownElementListLabels", "serviceNowConfigurationTypeList", "noElementsDisplayText"), "Filter functionality on selecting multiple options from ServiceNow Configuration Type column on company list page is not working");
		companiesPage.waitUntilElementIsInvisibleOfCompanyPage("tableOverlay");
		if (companiesPage.verifyElementsOfCompaniesPage("clearFiltersButton"))
			companiesPage.clickOnElementsOfCompaniesPage("clearFiltersButton");
		companiesPage.waitUntilElementIsInvisibleOfCompanyPage("tableOverlay");
		LOGGER.info("Verified filter functionality for ServiceNow Configuration Type column");

		// This test case validates if the filter functionality is working
		// properly for
		// the searchbox on primary account column
		softAssert.assertTrue(companiesPage.verifySearchValueOnCompany(LanguageCode, "primarySearchBox", CompaniesVariables.INCORRECT_SEARCH_STRING, "noElementsDisplayText", "primaryAccountList"), "Search functionality with incorrect search string for primary account column on company list page is not working");
		softAssert.assertTrue(companiesPage.verifySearchValueOnCompany(LanguageCode, "primarySearchBox", CompaniesVariables.PRIMARY_ACCOUNT, "noElementsDisplayText", "primaryAccountList"), "Search functionality for primary account column on company list page is not working");
		LOGGER.info("Verified filter functionality for primary account column");
		companiesPage.waitUntilElementIsInvisibleOfCompanyPage("tableOverlay");

		// This test case validates if the filter functionality is working
		// properly for
		// the searchbox on primary account email column
		softAssert.assertTrue(companiesPage.verifySearchValueOnCompany(LanguageCode, "primaryAccountEmailSearchBox", CompaniesVariables.INCORRECT_SEARCH_STRING, "noElementsDisplayText", "primaryAccountEmailList"), "Search functionality with incorrect search string for primary account email  column on company list page is not working");
		softAssert.assertTrue(companiesPage.verifySearchValueOnCompany(LanguageCode, "primaryAccountEmailSearchBox", CompaniesVariables.PRIMARY_ACCOUNT_EMAIL, "noElementsDisplayText", "primaryAccountEmailList"), "Search functionality for primary account email  column on company list page is not working");
		LOGGER.info("Verified filter functionality for primary account email  column");
		companiesPage.waitUntilElementIsInvisibleOfCompanyPage("tableOverlay");

		// This test case validates if the filter functionality is working
		// properly for // the searchbox on Phone Number column
		softAssert.assertTrue(companiesPage.verifySearchValueOnCompany(LanguageCode, "phoneNumberSearchBox", CompaniesVariables.INCORRECT_SEARCH_STRING, "noElementsDisplayText", "phoneNumberList"), "Search functionality with incorrect search string for Phone Number column on company list page is not working");
		softAssert.assertTrue(companiesPage.verifySearchValueOnCompany(LanguageCode, "phoneNumberSearchBox", CompaniesVariables.NUMBER, "noElementsDisplayText", "phoneNumberList"), "Search functionality for Phone Number column on company list page is not working");
		LOGGER.info("Verified filter functionality for Phone Number column");

		// This test case validates if the filter functionality is working
		// properly for
		// the dropdown on Partner column
		companiesPage.clickOnElementsOfCompaniesPage("partnerBoxBefore");
		softAssert.assertTrue(companiesPage.verifyFilterSingleSelect(LanguageCode, "dropDownCheckBoxes", "dropDownElementListLabels", "partnerList", "noElementsDisplayText"), "Filter functionality on selecting single option from partner column on company list page is not working");
		companiesPage.waitUntilElementIsInvisibleOfCompanyPage("tableOverlay");
		if (companiesPage.verifyElementsOfCompaniesPage("clearFiltersButton"))
			companiesPage.clickOnElementsOfCompaniesPage("clearFiltersButton");
		companiesPage.waitUntilElementIsInvisibleOfCompanyPage("tableOverlay");

		companiesPage.clickOnElementsOfCompaniesPage("partnerBoxBefore");
		softAssert.assertTrue(companiesPage.verifyFilterMultiSelect(LanguageCode, "dropDownCheckBoxes", "dropDownElementListLabels", "partnerList", "noElementsDisplayText"), "Filter functionality on selecting multiple options from partner column on company list page is not working");
		companiesPage.waitUntilElementIsInvisibleOfCompanyPage("tableOverlay");
		if (companiesPage.verifyElementsOfCompaniesPage("clearFiltersButton"))
			companiesPage.clickOnElementsOfCompaniesPage("clearFiltersButton");
		companiesPage.waitUntilElementIsInvisibleOfCompanyPage("tableOverlay");
		LOGGER.info("Verified filter functionality for partner column");

		// This test case validates if the filter functionality is working
		// properly for
		// the dropdown on MSP column
		companiesPage.clickOnElementsOfCompaniesPage("MSPBoxBefore");
		softAssert.assertTrue(companiesPage.verifyFilterSingleSelect(LanguageCode, "dropDownCheckBoxes", "dropDownElementListLabels", "MSPList", "noElementsDisplayText"), "Filter functionality on selecting single option from MSP column on company list page is not working");
		companiesPage.waitUntilElementIsInvisibleOfCompanyPage("tableOverlay");
		if (companiesPage.verifyElementsOfCompaniesPage("clearFiltersButton"))
			companiesPage.clickOnElementsOfCompaniesPage("clearFiltersButton");
		companiesPage.waitUntilElementIsInvisibleOfCompanyPage("tableOverlay");

		companiesPage.clickOnElementsOfCompaniesPage("MSPBoxBefore");
		softAssert.assertTrue(companiesPage.verifyFilterMultiSelect(LanguageCode, "dropDownCheckBoxes", "dropDownElementListLabels", "MSPList", "noElementsDisplayText"), "Filter functionality on selecting multiple options from MSP column on company list page is not working");
		companiesPage.waitUntilElementIsInvisibleOfCompanyPage("tableOverlay");
		if (companiesPage.verifyElementsOfCompaniesPage("clearFiltersButton"))
			companiesPage.clickOnElementsOfCompaniesPage("clearFiltersButton");
		companiesPage.waitUntilElementIsInvisibleOfCompanyPage("tableOverlay");
		LOGGER.info("Verified filter functionality for MSP column");

		// This test case validates if the filter functionality is working
		// properly for
		// the dropdown on company size column
		companiesPage.clickOnElementsOfCompaniesPage("numberOfEmployeesBoxBefore");
		softAssert.assertTrue(companiesPage.verifyFilterSingleSelect(LanguageCode, "dropDownCheckBoxes", "dropDownElementListLabels", "numberOfEmployeesList", "noElementsDisplayText"), "Filter functionality on selecting single option from company size column on company list page is not working");
		companiesPage.waitUntilElementIsInvisibleOfCompanyPage("tableOverlay");
		if (companiesPage.verifyElementsOfCompaniesPage("clearFiltersButton"))
			companiesPage.clickOnElementsOfCompaniesPage("clearFiltersButton");
		companiesPage.waitUntilElementIsInvisibleOfCompanyPage("tableOverlay");

		companiesPage.clickOnElementsOfCompaniesPage("numberOfEmployeesBoxBefore");
		softAssert.assertTrue(companiesPage.verifyFilterMultiSelect(LanguageCode, "dropDownCheckBoxes", "dropDownElementListLabels", "numberOfEmployeesList", "noElementsDisplayText"), "Filter functionality on selecting multiple options from company size column on company list page is not working");
		companiesPage.waitUntilElementIsInvisibleOfCompanyPage("tableOverlay");
		if (companiesPage.verifyElementsOfCompaniesPage("clearFiltersButton"))
			companiesPage.clickOnElementsOfCompaniesPage("clearFiltersButton");
		companiesPage.waitUntilElementIsInvisibleOfCompanyPage("tableOverlay");
		LOGGER.info("Verified filter functionality for company size column");

		softAssert.assertAll();
		LOGGER.info("All filter functionality test-cases passed");

	}

	/**
	 * This test case verifies filter functionality for Reseller
	 * 
	 * @throws Exception
	 */
	@Test(priority = 8, description = "TEST CASE - 240871", groups = { "REGRESSIONCOMPANIES1", "SMOKE", "REGRESSION_CI", "SMOKE_CI" }, enabled = false)
	public final void verifyFilterFunctionalityForReseller() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login("RESELLER_STAGING_COMPANIES_EMAIL", "RESELLER_STAGING_COMPANIES_EMAIL");
		gotoCompaniesTab();
		LOGGER.info("Redirected to Companies tab");
		waitForPageLoaded();
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();

		resetTableConfiguration();
		Assert.assertFalse(companiesPage.verifyElementsOfCompaniesPage("noElementsDisplayText"), "No company is present on company page, unable to proceed further");
		LOGGER.info("Atleast one company is present, test case started");
		companiesPage.waitForElementsOfCompaniesPage("firstCompany");
		TableConfigurationPage tableConfigurationPage = new TableConfigurationPage(PreDefinedActions.getDriver()).getInstance();
		tableConfigurationPage = tableConfigurationPage.getInstance();
		tableConfigurationPage.waitUntillElementIsPresentOftableConfigurationPage("tableOverlay");

		// This test case validates if the filter functionality is working
		// properly for
		// the searchbox on name column
		softAssert.assertTrue(companiesPage.verifySearchValueOnCompany(LanguageCode, "nameSearchBox", CompaniesVariables.INCORRECT_SEARCH_STRING, "noElementsDisplayText", "nameList"), "Search functionality with incorrect search string for name column on company list page is not working");
		softAssert.assertTrue(companiesPage.verifySearchValueOnCompany(LanguageCode, "nameSearchBox", staticComapny, "noElementsDisplayText", "nameList"), "Search functionality for name column on company list page is not working");
		LOGGER.info("Verified filter functionality for name column");
		companiesPage.waitUntilElementIsInvisibleOfCompanyPage("tableOverlay");

		// This test case validates if the filter functionality is working
		// properly for
		// the dropdown on Subscription column
		companiesPage.clickOnElementsOfCompaniesPage("subscriptionBoxBefore");
		softAssert.assertTrue(companiesPage.verifyFilterSingleSelect(LanguageCode, "dropDownCheckBoxes", "dropDownElementListLabels", "subscriptionList", "noElementsDisplayText"), "Filter functionality on selecting single option from subscription column on company list page is not working");
		companiesPage.waitUntilElementIsInvisibleOfCompanyPage("tableOverlay");
		if (companiesPage.verifyElementsOfCompaniesPage("clearFiltersButton"))
			companiesPage.clickOnElementsOfCompaniesPage("clearFiltersButton");
		companiesPage.waitUntilElementIsInvisibleOfCompanyPage("tableOverlay");

		companiesPage.clickOnElementsOfCompaniesPage("subscriptionBoxBefore");
		softAssert.assertTrue(companiesPage.verifyFilterMultiSelect(LanguageCode, "dropDownCheckBoxes", "dropDownElementListLabels", "subscriptionList", "noElementsDisplayText"), "Filter functionality on selecting multiple options from subscription column on company list page is not working");
		companiesPage.waitUntilElementIsInvisibleOfCompanyPage("tableOverlay");
		if (companiesPage.verifyElementsOfCompaniesPage("clearFiltersButton"))
			companiesPage.clickOnElementsOfCompaniesPage("clearFiltersButton");
		companiesPage.waitUntilElementIsInvisibleOfCompanyPage("tableOverlay");
		LOGGER.info("Verified filter functionality for Subscription column");

		// This test case validates if the filter functionality is working
		// properly for
		// the dropdown on Primary Account column
		companiesPage.clickOnElementsOfCompaniesPage("primaryAccountBoxBefore");
		softAssert.assertTrue(companiesPage.verifyFilterSingleSelect(LanguageCode, "dropDownCheckBoxes", "dropDownElementListLabels", "primaryAccountList", "noElementsDisplayText"), "Filter functionality on selecting single option from Primary Account column on company list page is not working");
		companiesPage.waitUntilElementIsInvisibleOfCompanyPage("tableOverlay");
		if (companiesPage.verifyElementsOfCompaniesPage("clearFiltersButton"))
			companiesPage.clickOnElementsOfCompaniesPage("clearFiltersButton");
		companiesPage.waitUntilElementIsInvisibleOfCompanyPage("tableOverlay");

		companiesPage.clickOnElementsOfCompaniesPage("primaryAccountBoxBefore");
		softAssert.assertTrue(companiesPage.verifyFilterMultiSelect(LanguageCode, "dropDownCheckBoxes", "dropDownElementListLabels", "primaryAccountList", "noElementsDisplayText"), "Filter functionality on selecting multiple options from Primary Account column on company list page is not working");
		companiesPage.waitUntilElementIsInvisibleOfCompanyPage("tableOverlay");
		if (companiesPage.verifyElementsOfCompaniesPage("clearFiltersButton"))
			companiesPage.clickOnElementsOfCompaniesPage("clearFiltersButton");
		companiesPage.waitUntilElementIsInvisibleOfCompanyPage("tableOverlay");
		LOGGER.info("Verified filter functionality for Primary Account column");

		// This test case validates if the filter functionality is working
		// properly for // the dropdown on status column
		// companiesPage.clickOnElementsOfCompaniesPage("statusBoxBefore");
		softAssert.assertTrue(companiesPage.verifyFilterSingleSelect(LanguageCode, "dropDownCheckBoxes", "dropDownElementListLabels", "statusList", "noElementsDisplayText"), "Filter functionality on selecting single option from status column on company list page is not working");
		companiesPage.waitUntilElementIsInvisibleOfCompanyPage("tableOverlay");
		companiesPage.clickOnElementsOfCompaniesPage("clearFiltersButton");
		companiesPage.waitUntilElementIsInvisibleOfCompanyPage("tableOverlay");

		companiesPage.clickOnElementsOfCompaniesPage("statusBoxBefore");
		softAssert.assertTrue(companiesPage.verifyFilterMultiSelect(LanguageCode, "dropDownCheckBoxes", "dropDownElementListLabels", "statusList", "noElementsDisplayText"), "Filter functionality on selecting multiple options from status column on company list page is not working");
		companiesPage.waitUntilElementIsInvisibleOfCompanyPage("tableOverlay");
		if (companiesPage.verifyElementsOfCompaniesPage("clearFiltersButton"))
			companiesPage.clickOnElementsOfCompaniesPage("clearFiltersButton");
		companiesPage.waitUntilElementIsInvisibleOfCompanyPage("tableOverlay");
		LOGGER.info("Verified filter functionality for status column");

		// This test case validates if the filter functionality is working
		// properly for // the searchbox on Address column
		softAssert.assertTrue(companiesPage.verifySearchValueOnCompany(LanguageCode, "addressSearchBox", CompaniesVariables.INCORRECT_SEARCH_STRING, "noElementsDisplayText", "addressList"), "Search functionality with incorrect search string for Address column on company list page is not working");
		softAssert.assertTrue(companiesPage.verifySearchValueOnCompany(LanguageCode, "addressSearchBox", CompaniesVariables.ADDRESS, "noElementsDisplayText", "addressList"), "Search functionality for Address column on company list page is not working");
		LOGGER.info("Verified filter functionality for Address column");

		// This test case validates if the filter functionality is working
		// properly for
		// the searchbox on the MSP column
		softAssert.assertTrue(companiesPage.verifySearchValueOnCompany(LanguageCode, "MSPSearchBox", CompaniesVariables.INCORRECT_SEARCH_STRING, "noElementsDisplayText", "MSPList"), "Search functionality with incorrect search string for MSP column on company list page is not working");
		softAssert.assertTrue(companiesPage.verifySearchValueOnCompany(LanguageCode, "MSPSearchBox", staticComapny, "noElementsDisplayText", "companyPinList"), "MSPList functionality for MSP column on company list page is not working");
		LOGGER.info("Verified filter functionality for MSP column");
		companiesPage.waitUntilElementIsInvisibleOfCompanyPage("tableOverlay");

		// This test case validates if the filter functionality is working
		// properly for
		// the searchbox on Domain Name column
		softAssert.assertTrue(companiesPage.verifySearchValueOnCompany(LanguageCode, "domainNameSearchBox", CompaniesVariables.INCORRECT_SEARCH_STRING, "noElementsDisplayText", "domainNameList"), "Search functionality with incorrect search string for Domain Name column on company list page is not working");
		softAssert.assertTrue(companiesPage.verifySearchValueOnCompany(LanguageCode, "domainNameSearchBox", CompaniesVariables.DOMAIN_NAME, "noElementsDisplayText", "domainNameList"), "Search functionality for Domain Name column on company list page is not working");
		LOGGER.info("Verified filter functionality for Domain Name column");
		companiesPage.waitUntilElementIsInvisibleOfCompanyPage("tableOverlay");

		// This test case validates if the filter functionality is working
		// properly for // the searchbox on Preferred Time Zone column
		softAssert.assertTrue(companiesPage.verifySearchValueOnCompany(LanguageCode, "preferredTimeZoneSearchBox", CompaniesVariables.INCORRECT_SEARCH_STRING, "noElementsDisplayText", "preferredTimeZoneList"), "Search functionality with incorrect search string for Preferred Time Zone column on company list page is not working");
		softAssert.assertTrue(companiesPage.verifySearchValueOnCompany(LanguageCode, "preferredTimeZoneSearchBox", CompaniesVariables.TIME_ZONE, "noElementsDisplayText", "preferredTimeZoneList"), "Search functionality for Preferred Time Zone column on company list page is not working");
		LOGGER.info("Verified filter functionality for Preferred Time Zone column");

		// This test case validates if the filter functionality is working
		// properly for // the searchbox on ServiceNow URL column
		softAssert.assertTrue(companiesPage.verifySearchValueOnCompany(LanguageCode, "serviceNowUrlSearchBox", CompaniesVariables.INCORRECT_SEARCH_STRING, "noElementsDisplayText", "serviceNowUrlList"), "Search functionality with incorrect search string for ServiceNow URL column on company list page is not working");
		softAssert.assertTrue(companiesPage.verifySearchValueOnCompany(LanguageCode, "serviceNowUrlSearchBox", CompaniesVariables.SERVICE_NOW_URL, "noElementsDisplayText", "serviceNowUrlList"), "Search functionality for ServiceNow URL column on company list page is not working");
		LOGGER.info("Verified filter functionality for ServiceNow URL column");

		// This test case validates if the filter functionality is working
		// properly for
		// the searchbox on Phone Number column
		softAssert.assertTrue(companiesPage.verifySearchValueOnCompany(LanguageCode, "phoneNumberSearchBox", CompaniesVariables.INCORRECT_SEARCH_STRING, "noElementsDisplayText", "phoneNumberList"), "Search functionality with incorrect search string for Phone Number column on company list page is not working");
		softAssert.assertTrue(companiesPage.verifySearchValueOnCompany(LanguageCode, "phoneNumberSearchBox", CompaniesVariables.NUMBER, "noElementsDisplayText", "phoneNumberList"), "Search functionality for Phone Number column on company list page is not working");
		LOGGER.info("Verified filter functionality for Phone Number column");
		companiesPage.waitUntilElementIsInvisibleOfCompanyPage("tableOverlay");

		// This test case validates if the filter functionality is working
		// properly for
		// the dropdown on ServiceNow Configuration Type column
		companiesPage.clickOnElementsOfCompaniesPage("serviceNowConfigurationTypeBoxBefore");
		softAssert.assertTrue(companiesPage.verifyFilterSingleSelect(LanguageCode, "dropDownCheckBoxes", "dropDownElementListLabels", "serviceNowConfigurationTypeList", "noElementsDisplayText"), "Filter functionality on selecting single option from ServiceNow Configuration Type column on company list page is not working");
		companiesPage.waitUntilElementIsInvisibleOfCompanyPage("tableOverlay");
		if (companiesPage.verifyElementsOfCompaniesPage("clearFiltersButton"))
			companiesPage.clickOnElementsOfCompaniesPage("clearFiltersButton");
		companiesPage.waitUntilElementIsInvisibleOfCompanyPage("tableOverlay");

		companiesPage.clickOnElementsOfCompaniesPage("serviceNowConfigurationTypeBoxBefore");
		softAssert.assertTrue(companiesPage.verifyFilterMultiSelect(LanguageCode, "dropDownCheckBoxes", "dropDownElementListLabels", "serviceNowConfigurationTypeList", "noElementsDisplayText"), "Filter functionality on selecting multiple options from ServiceNow Configuration Type column on company list page is not working");
		companiesPage.waitUntilElementIsInvisibleOfCompanyPage("tableOverlay");
		if (companiesPage.verifyElementsOfCompaniesPage("clearFiltersButton"))
			companiesPage.clickOnElementsOfCompaniesPage("clearFiltersButton");
		companiesPage.waitUntilElementIsInvisibleOfCompanyPage("tableOverlay");
		LOGGER.info("Verified filter functionality for ServiceNow Configuration Type column");

		// This test case validates if the filter functionality is working
		// properly for
		// the dropdown on company size column
		companiesPage.clickOnElementsOfCompaniesPage("numberOfEmployeesBoxBefore");
		softAssert.assertTrue(companiesPage.verifyFilterSingleSelect(LanguageCode, "dropDownCheckBoxes", "dropDownElementListLabels", "numberOfEmployeesList", "noElementsDisplayText"), "Filter functionality on selecting single option from company size column on company list page is not working");
		companiesPage.waitUntilElementIsInvisibleOfCompanyPage("tableOverlay");
		if (companiesPage.verifyElementsOfCompaniesPage("clearFiltersButton"))
			companiesPage.clickOnElementsOfCompaniesPage("clearFiltersButton");
		companiesPage.waitUntilElementIsInvisibleOfCompanyPage("tableOverlay");

		companiesPage.clickOnElementsOfCompaniesPage("numberOfEmployeesBoxBefore");
		softAssert.assertTrue(companiesPage.verifyFilterMultiSelect(LanguageCode, "dropDownCheckBoxes", "dropDownElementListLabels", "numberOfEmployeesList", "noElementsDisplayText"), "Filter functionality on selecting multiple options from company size column on company list page is not working");
		companiesPage.waitUntilElementIsInvisibleOfCompanyPage("tableOverlay");
		if (companiesPage.verifyElementsOfCompaniesPage("clearFiltersButton"))
			companiesPage.clickOnElementsOfCompaniesPage("clearFiltersButton");
		companiesPage.waitUntilElementIsInvisibleOfCompanyPage("tableOverlay");
		LOGGER.info("Verified filter functionality for company size column");

		softAssert.assertAll();
		LOGGER.info("All filter functionality test-cases passed");

	}

	/**
	 * This test case verifies redirection of links present on company list page for Root user
	 * 
	 * @throws Exception
	 */
	@Test(priority = 9, description = "TEST CASE - 240871", groups = { "REGRESSIONCOMPANIES1", "SMOKE", "REGRESSION_CI", "SMOKE_CI" })
	public final void verifyLinksOnCompanyPageUsingRootAdmin() throws Exception {
		login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
		LOGGER.info("Redirected to company list page");
		SoftAssert softAssert = new SoftAssert();
		waitForPageLoaded();
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();

		resetTableConfiguration();

		softAssert.assertFalse(companiesPage.verifyElementsOfCompaniesPage("noElementsDisplayText"), "No company is present on company page, unable to proceed further");
		LOGGER.info("Atleast one company is present, test case started");

		companiesPage.waitUntilElementIsInvisibleOfCompanyPage("tableOverlay");
		String name = companiesPage.getTextOfCompaniesPage("firstcompanyName");
		companiesPage.clickOnElementsOfCompaniesPage("firstcompanyName");
		LOGGER.info("Clicked on company name");
		waitForPageLoaded();
		softAssert.assertTrue(companiesPage.matchTextOfCompaniesPage("breadcrumb", name), "after clicking on company name it does not redirect to company detail page");
		navigateToBack();
		companiesPage.clickOnElementsOfCompaniesPage("partnerSortArrow");
		String partner = companiesPage.getTextOfCompaniesPage("partner");
		companiesPage.clickOnElementsOfCompaniesPage("partner");
		LOGGER.info("Clicked on partner");
		waitForPageLoaded();
		softAssert.assertTrue(companiesPage.matchTextOfCompaniesPage("breadcrumb", partner), "after clicking on partner it does not redirect to partner detail page");
		navigateToBack();
		companiesPage.clickOnElementsOfCompaniesPage("MSPSortArrow");
		String MSP = companiesPage.getTextOfCompaniesPage("partner");
		companiesPage.clickOnElementsOfCompaniesPage("partner");
		LOGGER.info("Clicked on MSP");
		waitForPageLoaded();
		softAssert.assertTrue(companiesPage.matchTextOfCompaniesPage("breadcrumb", MSP), "after clicking on MSP it does not redirect to MSP detail page");

		softAssert.assertAll();
		LOGGER.info("All test cases of link redirection on company list page for root user passed");
	}

	/**
	 * This test case verifies redirection of links present on company list page for MSP
	 * 
	 * @throws Exception
	 */
	@Test(priority = 10, description = "TEST CASE - 240871", groups = { "REGRESSIONCOMPANIES1", "SMOKE", "REGRESSION_CI", "SMOKE_CI" })
	public final void verifyLinksOnCompanyPageUsingMSP() throws Exception {
		login("NEW_MSP_ADMIN_US_EMAIl", "NEW_MSP_ADMIN_US_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		gotoCompaniesTab();
		LOGGER.info("Redirected to Companies tab");
		waitForPageLoaded();
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		CompaniesDetailsPage companyDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver()).getInstance();

		resetTableConfiguration();

		Assert.assertTrue(companiesPage.verifyElementsOfCompaniesPage("firstCompany"), "No company is present on company page, unable to proceed further");
		LOGGER.info("Atleast one company is present, test case started");

		sleeper(3000);
		companiesPage.clickOnElementsOfCompaniesPage("firstcompanyName");
		sleeper(3000);
		softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("companiesBreadcrumb2", companyDetailsPage.getTextLanguage(LanguageCode, "daas_ui", "breadcrumbs.company_details")), "after clicking on company name it does not redirect to company detail page");
		navigateToBack();
		sleeper(5000);

		companiesPage.clickOnElementsOfCompaniesPage("primaryAccountName");
		softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("companiesBreadcrumb2", companyDetailsPage.getTextLanguage(LanguageCode, "daas_ui", "breadcrumbs.company_details")), "after clicking on Primary account name it does not redirect to user detail page");

		softAssert.assertAll();
		LOGGER.info("All test cases of link redirection on company list page for MSP passed");
	}

	/**
	 * This test case verifies tiles on company details page for root user
	 * 
	 * @throws Exception
	 */
	@Test(priority = 11, description = "Testcase ID:240906,240903,240904,240909,240910,240908,240878", groups = { "REGRESSIONCOMPANIES1", "SMOKE", "REGRESSION_CI", "SMOKE_CI", "STABILIZATION_STAGING" })
	public final void verifyRootLoginView() throws Exception {
		login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
		SoftAssert softAssert = new SoftAssert();
		CompaniesDetailsPage companyDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver()).getInstance();

		LOGGER.info("Redirected to company list page");
		waitForPageLoaded();
		impersonateCompanyByCompanyName(autoCompany);

		// Verify Company ID
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("companyId"), "Company ID is not present under company details page");

		// Verify Company Information section
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("companyInfoTitle"), "Company Overview section is not present under company details page");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("companyInfoTitle").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "global.details.tab.overview_title").toUpperCase()), "Company Overview Label did not match");

		// Verify breadcrumbs
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("companiesBreadcrumb1").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "breadcrumbs.companies")), "BreadCrumb1 did not match");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("companiesBreadcrumb2").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "breadcrumbs.company_details")), "BreadCrumb2 did not match");
		LOGGER.info("Verified breadcrumb on company details page.");

		// Verify details tile strings and permissions
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("detailsTileHeader"), "Profile tile is not present under company details page");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("companyIDLabel").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "workflowOnboarding.company.id")), "Company ID label did not match");
		companyDetailsPage.scrollOnCompaniesDetailsPage("companyNameLabel");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("companyNameLabel").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.company_name")), "Company name label did not match");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("companyAddressLabel").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "companies.list.address")), "Address label did not match");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("companyNoOfEmployeesLabel").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "companies.list.no_of_employees")), "company size label did not match");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("companyTimeZone").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "companies.list.preferred_time_zone")), "Preferred time zone label did not match");
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("editCompanyName"), "Edit button is missing for company name tile");
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("editCompanyaddress"), "Edit button is missing for address tile");
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("editCompanyNoOfEmployees"), "Edit button is missing for Number of Employess tile");
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("editCompanyTimeZone"), "Edit button is missing for Preferred Time zone tile");
		LOGGER.info("Verified details tile strings and permissions.");

		// Verify Left panel details
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("panelCompanyName").equalsIgnoreCase( companyDetailsPage.getTextOfCompaniesDetailsPage("companyNameData").toUpperCase()), "Company Name does not match on left panel");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("panelTimeZone").equalsIgnoreCase( companyDetailsPage.getTextOfCompaniesDetailsPage("companyTimeZoneData")), "Company Time Zone does not match on left panel");

		// Verify Preferences section
		companyDetailsPage.scrollToTop();
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("preferenceTab");
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("preferencesTitle"), "Preferences section is not present under company details page");
		LOGGER.info("Clicked on preference tab");

		// Verify 3rd party Software tile strings and permissions
		companyDetailsPage.scrollOnCompaniesDetailsPage("3rdPartyAppTileHeader");		
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("3rdPartyAppTileHeader"), "3rd party Software tile is not present under company details page");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("3rdPartyAppTileHeader").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.3rd_party_applications_integration").toUpperCase()), "3rd party Software tile header did not match");
		companyDetailsPage.waitForElementsOfCompaniesDetailsPage("editCompanyServiceNowRoot");
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("editCompanyServiceNowRoot"), "Edit button is missing for service now");
		LOGGER.info("Verified 3rd party Software tile strings and permissions.");

		// Verify Subscription section
		companyDetailsPage.scrollToTop();
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("subscriptionTab");
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("subscriptionTitle"), "Subscription section is not present under company details page");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("subscriptionTitle").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "subscription.section.plans").toUpperCase()), "Subscription section title did not match");

		// Verify account summary tile strings and permissions
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("accountSummaryTileHeader"), "Account summary tile is not present under company details page");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("accountSummaryTileHeader").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.subscription")), "plan overview tile header did not match");

		//		Commented the code as this label is no longer visible on account summary tile.
		//		softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("activePlanSummary", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.active_plan")), "Active plan label did not match");
		LOGGER.info("Verified account summary tile strings and permissions.");

		// Verify subscription key tile strings and permissions
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("LicensekeyTileHeader"), "Subscription key tile is not present under company details page");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("LicensekeyTileHeader").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "sidemenu.subscriptions")), "License detail tile header did not match");
		// Plan field removed from identity tile on V3
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("addNewKeyButton"),
				"Add new key button is not present under subscription key tile");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("addNewKeyButton").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.add_subscription_key").toUpperCase()), "Add subscription key text did not match");
		LOGGER.info("Verified subscription key tile strings and permissions.");

		// Verify assignment settings tile for MSP
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("assignedMSPTab");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("assignedMSPTitle").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "companies.details.tile.assignment_settings").toUpperCase()), "Title on assigned MSP tile is incorrect");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("assignedMSPLabel").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "companies.list.msp")), "Label on assigned MSP tile is incorrect");
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("editAssignedMSPButton"), "Edit button for MSP is not present on assigned MSP tile of company details page");

		// Verify assignment settings tile for Partner
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("assignedpartnerTab");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("assignedPartnerTitle").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "companies.details.tile.assignment_settings").toUpperCase()), "Title on assigned partner tile is incorrect");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("assignedPartnerLabel").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "global.form.partner")), "Label on assigned partner tile is incorrect");
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("editAssignedpartnerButton"), "Edit button for partner is not present on assigned partner tile of company details page");

		softAssert.assertAll();
		LOGGER.info("All test cases of root login view for company details page passed");
	}

	/**
	 * This test case verifies tiles on company details page for reseller view
	 * 
	 * @throws Exception
	 */
	@Test(priority = 12, description = "TestCase ID: 240879,240888,240876,240877,240878", groups = { "REGRESSIONCOMPANIES1", "SMOKE", "REGRESSION_CI", "SMOKE_CI", "STABILIZATION_STAGING" })
	public final void verifyResellerCompanyDetailsPage() throws Exception {
		SoftAssert softAssert = new SoftAssert();

		login("RESELLER_STAGING_COMPANIES_EMAIL", "RESELLER_STAGING_COMPANIES_PASSWORD");
		CompaniesDetailsPage companyDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver()).getInstance();
		gotoCompaniesTab();
		LOGGER.info("Redirected to company list page");
		waitForPageLoaded();
		impersonateCompanyByCompanyName(getEnvironmentSpecificData(System.getProperty("environment"), "DEVICE_DETAILS_COMPANY"));

		
		// Verify Company Information section
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("companyInfoTitle"), "Company Overview section is not present under company details page");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("companyInfoTitle").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "global.details.tab.overview_title").toUpperCase()), "Company Overview Label did not match");

		// Verify breadcrumbs
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("companiesBreadcrumb1").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "breadcrumbs.companies")), "BreadCrumb1 did not match");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("companiesBreadcrumb2").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "breadcrumbs.company_details")), "BreadCrumb2 did not match");
		LOGGER.info("Verified breadcrumb on company details page.");

		// Verify details tile strings and permissions
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("detailsTileHeader"), "Profile tile is not present under company details page");
		//softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("detailsTileHeader").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "global.details.tab.tile.profile_title").toUpperCase()), "Profile tile header did not match");
		companyDetailsPage.scrollOnCompaniesDetailsPage("companyNameLabel");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("companyNameLabel").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.company_name")), "Company name label did not match");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("companyAddressLabel").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "companies.list.address")), "Address label did not match");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("companyTimeZone").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "companies.list.preferred_time_zone")), "Preferred time zone label did not match");
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("editCompanyName"), "Edit button is missing for company name tile");
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("editCompanyaddress"), "Edit button is missing for address tile");
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("editCompanyTimeZone"), "Edit button is missing for time zone tile");
		LOGGER.info("Verified details tile strings and permissions.");

		// Verify Left panel details
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("panelCompanyName").equalsIgnoreCase( companyDetailsPage.getTextOfCompaniesDetailsPage("companyNameData").toUpperCase()), "Company Name does not match on left panel");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("panelTimeZone").equalsIgnoreCase( companyDetailsPage.getTextOfCompaniesDetailsPage("companyTimeZoneData")), "Company Time Zone does not match on left panel");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("panelPrimaryAdminName").equalsIgnoreCase( companyDetailsPage.getTextOfCompaniesDetailsPage("companyMSPNameData")), "Company Primary Admin Name does not match on left panel");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("panelMSPEmail").equals(companyDetailsPage.getTextOfCompaniesDetailsPage("companyMSPEmailData")), "Company MSP Email does not match on left panel");

		// Verify Preferences section
		companyDetailsPage.scrollToTop();
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("preferenceTab");
		companyDetailsPage.waitForElementsOfCompaniesDetailsPage("preferencesTitle");
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("preferencesTitle"), "Preferences section is not present under company details page");

		// Verify domain name tile strings and permissions
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("domainNameHeader"), "Domain name tile is not present under company details page");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("domainNameHeader").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "companies.list.domain_name").toUpperCase()), "Domain name tile header did not match");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("domainNameDesc").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "companies.details.section_description.new_domain_name")), "Domain name tile desc did not match");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("domainNameLabel").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "companies.list.domain_name")), "Domain name tile label did not match");

		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("editCompanyDomainName"), "Edit button is not visible for domain name tile for reseller");
		LOGGER.info("Verified domain name tile strings and permissions.");

		// Verify incident tile strings and permissions
		if (toggleVerification(CompaniesVariables.INCIDENTRULEUI, "RESELLER_STAGING_COMPANIES_EMAIL") == false && toggleVerification(CompaniesVariables.INCIDENTSUBSCRIPTIONRULE, "RESELLER_STAGING_COMPANIES_EMAIL") == true) {
			softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("incidentTileHeader"), "Incident tile is not present under company details page");
			softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("incidentTileHeader").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.incidents").toUpperCase()), "Incident tile header did not match");
			softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("incidentLabel").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.incidents")), "Incident tile label did not match");
			softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("editCompanyIncidentSubscriptions"), "Edit button is missing for incident tile");
			LOGGER.info("Verified incident tile strings and permissions.");
		}

		// Verify Reports toggle
		//softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("toggleRpos"), "Toggle is not present for Retail Device Inventory Reports");

		// Verify 3rd party Software tile strings and permissions
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("3rdPartyAppTileHeader"), "3rd party Software tile is not present under company details page");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("3rdPartyAppTileHeader").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.3rd_party_applications_integration").toUpperCase()), "3rd party Software tile header did not match");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("inheritGlobalLabel").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "companies.details.service_now.inherit_global_snow")), "Inherit global snow label did not match");
		companyDetailsPage.waitForElementsOfCompaniesDetailsPage("editCompanyServiceNow");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("serviceNowLabel").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "settings.service_now.title")), "Service Now label did not match");
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("toggleGlobalServiceNow"), "Toggle is missing for service now");
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("editCompanyServiceNow"), "Edit button is missing for service now");
		LOGGER.info("Verified 3rd party Software tile strings and permissions.");

		// Verify Subscription section
		companyDetailsPage.scrollToTop();
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("subscriptionTab");
		companyDetailsPage.waitForElementsOfCompaniesDetailsPage("subscriptionTitle");
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("subscriptionTitle"), "Subscription section is not present under company details page");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("subscriptionTitle").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "subscription.section.plans").toUpperCase()), "Subscription section title did not match");
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("addNewKeyButton"), "Add new key button not present under company details page");
		
		// Verify account summary tile strings and permissions
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("accountSummaryTileHeader"), "Account summary tile is not present under company details page");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("accountSummaryTileHeader").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.subscription")), "Account summary tile header did not match");
		LOGGER.info("Verified account summary tile strings and permissions.");

		// Verify assignment settings tile for MSP
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("assignedMSPTab");
		companyDetailsPage.waitForElementsOfCompaniesDetailsPage("assignedMSPTitle");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("assignedMSPTitle").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "companies.details.tile.assignment_settings").toUpperCase()), "Title on assigned MSP tile is incorrect");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("assignedMSPLabel").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "companies.list.msp")), "Label on assigned MSP tile is incorrect");
		softAssert.assertFalse(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("editAssignedMSPButton"), "Edit button for MSP is present on assigned MSP tile of company details page");

		// Verify assignment settings tile for Partner
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("assignedpartnerTab");
		companyDetailsPage.waitForElementsOfCompaniesDetailsPage("assignedPartnerTitle");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("assignedPartnerTitle").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "companies.details.tile.assignment_settings").toUpperCase()), "Title on assigned partner tile is incorrect");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("assignedPartnerLabel").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "global.form.partner")), "Label on assigned partner tile is incorrect");
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("editAssignedpartnerButton"), "Edit button for partner is not present on assigned partner tile of company details page");

		softAssert.assertAll();
		LOGGER.info("Validation of Company details page for Partner Administrator completed successfully.");
	}

	/**
	 * This test case verifies tiles on company details page for reseller specialist view
	 * need to create user for this, will create user and enable it again
	 * @throws Exception
	 */
	@Test(priority = 13, description = "TestCase ID: 240879,240888,240876,240877", groups = { "REGRESSIONCOMPANIES1", "SMOKE", "REGRESSION_CI", "SMOKE_CI", "STABILIZATION_STAGING" }, enabled = false)
	public final void verifyResellerSpecialistCompanyDetailsPage() throws Exception {
		login("RESELLER_SPECIALIST_COMPANIES_DETAILS_EMAIL", "RESELLER_SPECIALIST_COMPANIES_DETAILS_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		CompaniesDetailsPage companyDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver()).getInstance();

		gotoCompaniesTab();
		LOGGER.info("Redirected to company list page");
		waitForPageLoaded();
		impersonateCompanyByCompanyName(autoCompany);

		// Verify Company Information section
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("companyInfoTitle"), "Company Overview section is not present under company details page");
		softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("companyInfoTitle", getTextLanguage(LanguageCode, "daas_ui", "global.details.tab.overview_title").toUpperCase()), "Company Overview Label did not match");

		// Verify breadcrumbs
		softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("companiesBreadcrumb1", getTextLanguage(LanguageCode, "daas_ui", "breadcrumbs.companies")), "BreadCrumb1 did not match");
		softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("companiesBreadcrumb2", getTextLanguage(LanguageCode, "daas_ui", "breadcrumbs.company_details")), "BreadCrumb2 did not match");
		LOGGER.info("Verified breadcrumb on company details page.");

		// Verify details tile strings and permissions
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("detailsTileHeader"), "Profile tile is not present under company details page");
		softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("detailsTileHeader", getTextLanguage(LanguageCode, "daas_ui", "global.details.tab.tile.profile_title").toUpperCase()), "Profile tile header did not match");
		softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("companyNameLabel", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.company_name")), "Company name label did not match");
		softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("companyAddressLabel", getTextLanguage(LanguageCode, "daas_ui", "companies.list.address")), "Address label did not match");
		softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("companyTimeZone", getTextLanguage(LanguageCode, "daas_ui", "companies.list.preferred_time_zone")), "Preferred time zone label did not match");
		softAssert.assertFalse(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("editCompanyName"), "Edit button is present for company name tile");
		softAssert.assertFalse(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("editCompanyaddress"), "Edit button is present for address tile");
		softAssert.assertFalse(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("editCompanyTimeZone"), "Edit button is present for time zone tile");
		LOGGER.info("Verified details tile strings and permissions.");

		// Verify Left panel details
		softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("panelCompanyName", companyDetailsPage.getTextOfCompaniesDetailsPage("companyNameData").toUpperCase()), "Company Name does not match on left panel");
		softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("panelPhoneNumber", companyDetailsPage.getTextOfCompaniesDetailsPage("companyPhoneData")), "Company Phone does not match on left panel");
		softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("panelTimeZone", companyDetailsPage.getTextOfCompaniesDetailsPage("companyTimeZoneData")), "Company Time Zone does not match on left panel");
		softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("panelPrimaryAdminName", companyDetailsPage.getTextOfCompaniesDetailsPage("companyMSPNameData")), "Company Primary Admin Name does not match on left panel");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("panelMSPEmail").equals(companyDetailsPage.getTextOfCompaniesDetailsPage("companyMSPEmailData")), "Company MSP Email does not match on left panel");

		// Verify Preferences section
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("preferenceTab");
		companyDetailsPage.waitForElementsOfCompaniesDetailsPage("preferencesTitle");
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("preferencesTitle"), "Preferences section is not present under company details page");

		// Verify domain name tile strings and permissions
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("domainNameHeader"), "Domain name tile is not present under company details page");
		softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("domainNameHeader", getTextLanguage(LanguageCode, "daas_ui", "companies.list.domain_name").toUpperCase()), "Domain name tile header did not match");
		softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("domainNameDesc", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section_description.domain_name")), "Domain name tile desc did not match");
		softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("domainNameLabel", getTextLanguage(LanguageCode, "daas_ui", "companies.list.domain_name")), "Domain name tile label did not match");
		softAssert.assertFalse(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("editCompanyDomainName"), "Edit button is visible for domain name tile for reseller");
		LOGGER.info("Verified domain name tile strings and permissions.");

		// Verify Reports toggle
		softAssert.assertFalse(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("toggleBromium"), "Toggle is present for HP Sure Click Advanced Reports");
		softAssert.assertFalse(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("toggleRpos"), "Toggle is present for Retail Device Inventory Reports");

		// Verify licence key tile strings and permissions
		if (toggleVerification(CompaniesVariables.SUBSCRIPTIONTOGGLE, getCredentials(System.getProperty("environment"), "RESELLER_SPECIALIST_COMPANIES_DETAILS_EMAIL"))) {
			// Verify Subscription section
			companyDetailsPage.scrollToTop();
			companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("subscriptionTab");
			companyDetailsPage.waitForElementsOfCompaniesDetailsPage("subscriptionTitle");
			softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("subscriptionTitle"), "Subscription section is not present under company details page");
			softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("subscriptionTitle", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.subscription").toUpperCase()), "Subscription section title did not match");

			// Verify account summary tile strings and permissions
			softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("accountSummaryTileHeader"), "Account summary tile is not present under company details page");
			softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("accountSummaryTileHeader", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.subscription")), "Account summary tile header did not match");
			softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("planNameOnIdentityTile", companyDetailsPage.getTextOfCompaniesDetailsPage("planAccountSummary")), "Company subscription does not match on left panel");
			LOGGER.info("Verified account summary tile strings and permissions.");

			softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("LicensekeyTileHeader"), "Subscription key tile is not present under company details page");
			softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("LicensekeyTileHeader").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "subscription.license.subscription_details")), "License detail tile header did not match");
			LOGGER.info("Verified licence key tile strings and permissions.");
		}

		// Verify assignment settings tile for MSP
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("assignedMSPTab");
		companyDetailsPage.waitForElementsOfCompaniesDetailsPage("assignedMSPTitle");
		softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("assignedMSPTitle", getTextLanguage(LanguageCode, "daas_ui", "companies.details.tile.assignment_settings").toUpperCase()), "Title on assigned MSP tile is incorrect");
		softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("assignedMSPLabel", getTextLanguage(LanguageCode, "daas_ui", "companies.list.msp")), "Label on assigned MSP tile is incorrect");
		softAssert.assertFalse(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("editAssignedMSPButton"), "Edit button for MSP is present on assigned MSP tile of company details page");
		LOGGER.info("Verified assignemnt settings tile for MSP");

		// Verify assignment settings tile for Partner
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("assignedpartnerTab");
		companyDetailsPage.waitForElementsOfCompaniesDetailsPage("assignedPartnerTitle");
		softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("assignedPartnerTitle", getTextLanguage(LanguageCode, "daas_ui", "companies.details.tile.assignment_settings").toUpperCase()), "Title on assigned partner tile is incorrect");
		softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("assignedPartnerLabel", getTextLanguage(LanguageCode, "daas_ui", "global.form.partner")), "Label on assigned partner tile is incorrect");
		softAssert.assertFalse(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("editAssignedpartnerButton"), "Edit button for partner is present on assigned partner tile of company details page");
		LOGGER.info("Verified assignment settings tile for partner");

		softAssert.assertAll();
		LOGGER.info("Validation of Company details page for Reseller Specialist completed successfully.");
	}

	/**
	 * This test case verifies tiles on company details page for msp specialist view
	 * Disabling this test case since the same page is bing validated with reseller.
	 * @throws Exception
	 */
	@Test(priority = 14, description = "Testcase ID: 239730", groups = { "REGRESSIONCOMPANIES1", "SMOKE", "REGRESSION_CI", "SMOKE_CI", "STABILIZATION_STAGING" },enabled=false)
	public final void verifyMspSpecialistCompanyDetailsPage() throws Exception {
		login("SUPPORT_SPECIALIST_STAGING_COMPANIES_EMAIL", "SUPPORT_SPECIALIST_STAGING_COMPANIES_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		CompaniesDetailsPage companyDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver()).getInstance();

		gotoCompaniesTab();
		LOGGER.info("Redirected to company list page");
		waitForPageLoaded();
		impersonateCompanyByCompanyName(proActiveSecurityCompany1);
		companyDetailsPage.waitUntilElementIsInvisibleOfCompanyDetailsPage("tableOverlay");

		// Verify Company Information section
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("companyInfoTitle"), "Company Overview section is not present under company details page");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("companyInfoTitle").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "global.details.tab.overview_title").toUpperCase()), "Company Overview Label did not match");

		// Verify breadcrumbs
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("companiesBreadcrumb1").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "breadcrumbs.companies")), "BreadCrumb1 did not match");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("companiesBreadcrumb2").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "breadcrumbs.company_details")), "BreadCrumb2 did not match");
		LOGGER.info("Verified breadcrumb on company details page.");

		// Verify details tile strings and permissions
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("detailsTileHeader"), "Profile tile is not present under company details page");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("detailsTileHeader").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "global.details.tab.tile.profile_title").toUpperCase()), "Profile tile header did not match");
		companyDetailsPage.scrollOnCompaniesDetailsPage("companyNameLabel");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("companyNameLabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.company_name")), "Company name label did not match");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("companyAddressLabel").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "companies.list.address")), "Address label did not match");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("companyTimeZone").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "companies.list.preferred_time_zone")), "Preferred time zone label did not match");
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("editCompanyName"), "Edit button is missing for company name tile");
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("editCompanyaddress"), "Edit button is missing for address tile");
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("editCompanyTimeZone"), "Edit button is missing for time zone tile");
		LOGGER.info("Verified details tile strings and permissions.");

		// Verify Left panel details
		
//		String adminemail = getDriver().findElement(By.xpath("//*[@title='"+getTextLanguage(LanguageCode, "daas_ui", "companies.list.primary_account_email")+"']")).getText();
//		String timeZone = getDriver().findElement(By.xpath("//*[@title='"+getTextLanguage(LanguageCode, "daas_ui", "companies.details.section_preferred_time_zone")+"']")).getText();
//		String primaryAdminName = getDriver().findElement(By.xpath("//*[@title='"+getTextLanguage(LanguageCode, "daas_ui", "companies.list.primary_account_admin")+"']")).getText();
//
//		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("panelCompanyName").equalsIgnoreCase( companyDetailsPage.getTextOfCompaniesDetailsPage("companyNameData").toUpperCase()), "Company Name does not match on left panel");
//		companyDetailsPage.scrollOnCompaniesDetailsPage("companyTimeZone");
//		softAssert.assertTrue(timeZone.equalsIgnoreCase( companyDetailsPage.getTextOfCompaniesDetailsPage("companyTimeZoneData")), "Company Time Zone does not match on left panel");
//		softAssert.assertTrue(primaryAdminName.equalsIgnoreCase( companyDetailsPage.getTextOfCompaniesDetailsPage("companyMSPNameData")), "Company Priamry Admin Name does not match on left panel");
//		softAssert.assertTrue(adminemail.equalsIgnoreCase( companyDetailsPage.getTextOfCompaniesDetailsPage("companyMSPEmailData")), "Company MSP Email does not match on left panel");

		// Verify Preferences section
		companyDetailsPage.scrollToTop();
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("preferenceTab");
		companyDetailsPage.waitForElementsOfCompaniesDetailsPage("preferencesTitle");
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("preferencesTitle"), "Preferences section is not present under company details page");

		// Verify company pin tile strings and permissions
		companyDetailsPage.scrollOnCompaniesDetailsPage("widePinTileHeader");
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("widePinTileHeader"), "Company pin tile is not present under company details page");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("widePinTileHeader").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.company_pin").toUpperCase()), "Company pin tile header did not match");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("widePinTileDesc").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "companies.details.section_description.wide_pin")), "Company pin tile desc did not match");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("companyPinLabel").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.company_pin")), "Company pin tile label did not match");
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("editCompanyPin"), "Edit button is missing for company pin tile");
		LOGGER.info("Verified company-wide pin tile strings and permissions.");

		// Verify Data Capture tile strings and permissions
		companyDetailsPage.scrollOnCompaniesDetailsPage("dataCaptureTileHeader");
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("dataCaptureTileHeader"), "Domain name tile is not present under company details page");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("dataCaptureTileHeader").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.data_capture").toUpperCase()), "Data capture tile header did not match");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("dataCaptureTileDesc").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "companies.details.section_description.data_capture")), "Data capture tile desc did not match");
		companyDetailsPage.scrollOnCompaniesDetailsPage("antivirusFirewallLabel");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("antivirusFirewallLabel").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.antivirus_firewall")), "Antivirus and firewall label did not match");
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("antivirusFirewallToggle"), "Toggle is missing for antivirus nad Firewall");
		companyDetailsPage.scrollOnCompaniesDetailsPage("locationLabel");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("locationLabel").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "global.location")), "Location label did not match");
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("locationToggle"), "Toggle is missing for location");
		companyDetailsPage.scrollOnCompaniesDetailsPage("softwareInventoryLabel");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("softwareInventoryLabel").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.software_inventory")), "Software Inventory label did not match");
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("softwareInventoryToggle"), "Toggle is missing for software Inventory");
		LOGGER.info("Verified Data Capture tile strings and permissions.");

		// Verify 3rd party Software tile strings and permissions
		companyDetailsPage.scrollOnCompaniesDetailsPage("3rdPartyAppTileHeader");
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("3rdPartyAppTileHeader"), "3rd party Software tile is not present under company details page");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("3rdPartyAppTileHeader").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.3rd_party_applications_integration").toUpperCase()), "3rd party Software tile header did not match");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("ChromeEnterpriseIntegrationLabel").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "devicelist.emmtools.chrome_enterprise")), "Google Chrome Enterprise tile label did not match");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("intuneLabel").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "devicelist.emmtools.microsoft_intune")), "Microsoft Intune tile label did not match");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("inheritGlobalLabel").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "companies.details.service_now.inherit_global_snow")), "Inherit global snow label did not match");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("serviceNowLabel").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "settings.service_now.title")), "Service Now label did not match");
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("editCompanyChromeEnterpriseIntegration"), "Edit button is missing for EMM Rest API");
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("toggleGlobalServiceNow"), "Toggle is missing for service now");
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("editCompanyServiceNow"), "Edit button is missing for service now");
		LOGGER.info("Verified 3rd party Software tile strings and permissions.");

		// Verify incident tile strings and permissions
		if (toggleVerification(CompaniesVariables.INCIDENTRULEUI, "SUPPORT_SPECIALIST_STAGING_COMPANIES_EMAIL") == false && toggleVerification(CompaniesVariables.INCIDENTSUBSCRIPTIONRULE, "SUPPORT_SPECIALIST_STAGING_COMPANIES_EMAIL") == true) {
			companyDetailsPage.scrollOnCompaniesDetailsPage("incidentTileHeader");
			softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("incidentTileHeader"), "Incident tile is not present under company details page");
			softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("incidentTileHeader").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.incidents").toUpperCase()), "Incident tile header did not match");
			softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("incidentLabel").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.incidents")), "Incident tile label did not match");
			softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("editCompanyIncidentSubscriptions"), "Edit button is missing for incident tile");
			LOGGER.info("Verified incident tile strings and permissions.");
		}

		// Verify Report tile strings and permissions
		companyDetailsPage.scrollOnCompaniesDetailsPage("reportTileHeader");
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("reportTileHeader"), "Report tile is not present under company details page");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("reportTileHeader").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "pagetitle.reports").toUpperCase()), "Report tile header did not match");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("reportDesc").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "companies.details.section_description.reports")), "Report tile desc did not match");
		//softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("bromiumLabel").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "companies.details.pro_sec_repos.title")), "Bromium label did not match"); // Toggle is disabled from UI
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("rposLabel").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "companies.details.rpos_config.title")), "Rpos label did not match");
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("toggleRpos"), "Toggle is missing for Retail Device Inventory Reports");
		LOGGER.info("Verified Report tile strings and permissions.");

		// Verify domain name tile strings and permissions
		companyDetailsPage.scrollOnCompaniesDetailsPage("domainNameHeader");
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("domainNameHeader"), "Domain name tile is not present under company details page");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("domainNameHeader").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "companies.list.domain_name").toUpperCase()), "Domain name tile header did not match");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("domainNameDesc").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "companies.details.section_description.domain_name")), "Domain name tile desc did not match");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("domainNameLabel").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "companies.list.domain_name")), "Domain name tile label did not match");
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("editCompanyDomainName"), "Edit button is missing for domain name pin tile");
		LOGGER.info("Verified domain name tile strings and permissions.");

		// Verify Licence key tile strings and permissions
		if (toggleVerification(CompaniesVariables.SUBSCRIPTIONTOGGLE, getCredentials(System.getProperty("environment"), "SUPPORT_SPECIALIST_STAGING_COMPANIES_EMAIL"))) {
			// Verify Subscription sectionr
			companyDetailsPage.scrollToTop();
			companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("subscriptionTab");
			companyDetailsPage.waitForElementsOfCompaniesDetailsPage("subscriptionTitle");
			softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("subscriptionTitle"), "Subscription section is not present under company details page");
			softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("subscriptionTitle").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "subscription.section.plans").toUpperCase()), "Subscription section title did not match");
			// Verify account summary tile strings and permissions
			softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("accountSummaryTileHeader"), "Account summary tile is not present under company details page");
			softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("accountSummaryTileHeader").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.subscription")), "Account summary tile header did not match");
			LOGGER.info("Verified account summary tile strings and permissions.");

			softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("LicensekeyTileHeader"), "Subscription key tile is not present under company details page");
			softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("LicensekeyTileHeader").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "sidemenu.subscriptions")), "License detail tile header did not match");
			softAssert.assertFalse(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("addNewKeyButton"), "Add new key button is present under subscription key tile");
			LOGGER.info("Verified Licence key tile strings and permissions.");
		}

		// Verify assignment settings tile for MSP
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("assignedMSPTab");
		companyDetailsPage.waitForElementsOfCompaniesDetailsPage("assignedMSPTitle");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("assignedMSPTitle").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "companies.details.tile.assignment_settings").toUpperCase()), "Title on assigned MSP tile is incorrect");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("assignedMSPLabel").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "companies.list.msp")), "Label on assigned MSP tile is incorrect");
		softAssert.assertFalse(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("editAssignedMSPButton"), "Edit button for MSP is present on assigned MSP tile of company details page");
		LOGGER.info("Verified assignemnt settings tile for MSP");

		// Verify assignment settings tile for Partner
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("assignedpartnerTab");
		companyDetailsPage.waitForElementsOfCompaniesDetailsPage("assignedPartnerTitle");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("assignedPartnerTitle").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "companies.details.tile.assignment_settings").toUpperCase()), "Title on assigned partner tile is incorrect");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("assignedPartnerLabel").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "global.form.partner")), "Label on assigned partner tile is incorrect");
		LOGGER.info("Verified assignment settings tile for partner");

		softAssert.assertAll();
		LOGGER.info("Validation of Company details page for MSP Specialist completed successfully.");
	}

	/**
	 * This test case verifies details tile present on company details page for root user
	 * 
	 * @throws Exception
	 */
	@Test(priority = 15, description = "Testcase ID:240906,240904,240909,240910,240908", groups = { "REGRESSIONCOMPANIES1", "REGRESSION_CI", "STABILIZATION_STAGING" })
	public final void verifyDetailsTileOnCompanyDetailsPage() throws Exception {
		login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
		String selectedPrimaryAccount = null;
		CompaniesDetailsPage companyDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver()).getInstance();
		try {
			SoftAssert softAssert = new SoftAssert();
			String streetAddressLine1, streetAddressLine2, locality, region, postalCode;
			waitForPageLoaded();
			LOGGER.info("Redirected to companies page");
			impersonateCompanyByCompanyName(detailsCompany);

			// Test Case 1 - This test case validates change in company size for a company
			waitForPageLoaded();
			sleeper(5000);
			String numberOfEmployees = companyDetailsPage.getTextOfCompaniesDetailsPage("companyNoOfEmployees");
			companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("editCompanyNoOfEmployees");
			LOGGER.info("Clicked on edit company size button");
			softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("noOfEmployeesHeader").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.list.no_of_employees")), "Title on company size popup is incorrect");
			companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("noOfEmployeesDropdown");
			String selectedNoOfEmployees = companyDetailsPage.selectFirstOptionFromDropdownOnCompaniesDetailsPage("noOfEmployeesList", numberOfEmployees);
			companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("cancelNumberOfEmployees");
			LOGGER.info("Clicked on cancel button for company size popup");
			softAssert.assertFalse(companyDetailsPage.getTextOfCompaniesDetailsPage("companyNoOfEmployees").equals(selectedNoOfEmployees), "Cancel functionality on company size popup is not working");
			companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("editCompanyNoOfEmployees");
			LOGGER.info("Clicked on edit company size button");
			companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("noOfEmployeesDropdown");
			selectedNoOfEmployees = companyDetailsPage.selectFirstOptionFromDropdownOnCompaniesDetailsPage("noOfEmployeesList", numberOfEmployees);
			companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("saveNoOfEmployees");
			LOGGER.info("Clicked on save button for company size popup");
			companyDetailsPage.waitForElementsOfCompaniesDetailsPage("toastNotification");
			softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("toastNotification"), "Toast notification is not generated after company size field is changed");
			softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("companyNoOfEmployees").contains(selectedNoOfEmployees), "Save functionality on company size popup is not working");
			sleeper(5000);
			scrollToTop();
			companyDetailsPage.waitForElementsOfCompaniesDetailsPage("editCompanyaddress");
			companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("editCompanyaddress");
			LOGGER.info("Clicked on edit address button");
			softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("addressTitle").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "global.address")), "Title on address popup is incorrect");
			companyDetailsPage.enterTextForCompaniesDetailsPage("streetAddressLine1Textbox", generateRandomString(257) + generateRandomString(11));
			companyDetailsPage.enterTextForCompaniesDetailsPage("postalCodeTextbox", generateRandomString(257));
			companyDetailsPage.clickByJavaScriptOnCompaniesDetailsPage("addressSaveButton");
			LOGGER.info("Clicked on save address button");
			softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("streetLine1ErrorMessage").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "form.validate.max_length").replace("{maxChar}", "255")), "Validation error message for character limit of 255 for address fields is not displayed");
			softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("zipCodeErrorMessage").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "form.validate.zip")), "Validation error message for character limit of 20 for postal code is not displayed");


			streetAddressLine1 = generateRandomString(257);
			companyDetailsPage.enterTextForCompaniesDetailsPage("streetAddressLine1Textbox", streetAddressLine1);
			streetAddressLine2 = generateRandomString(257);
			companyDetailsPage.enterTextForCompaniesDetailsPage("streetAddressLine2Textbox", streetAddressLine2);
			locality = generateRandomString(257);
			companyDetailsPage.enterTextForCompaniesDetailsPage("localityTextbox", locality);
			region = generateRandomString(257);
			companyDetailsPage.enterTextForCompaniesDetailsPage("regionTextbox", region);
			postalCode = generateRandomString(13);
			companyDetailsPage.enterTextForCompaniesDetailsPage("postalCodeTextbox", postalCode);

			companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("addressCancelButton");
			LOGGER.info("Clicked on cancel address button");

			companyDetailsPage.waitForElementsOfCompaniesDetailsPage("editCompanyaddress");
			companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("editCompanyaddress");
			LOGGER.info("Clicked on edit address button");
			streetAddressLine1 = generateRandomString(255);
			companyDetailsPage.enterTextForCompaniesDetailsPage("streetAddressLine1Textbox", streetAddressLine1);
			streetAddressLine2 = generateRandomString(255);
			companyDetailsPage.enterTextForCompaniesDetailsPage("streetAddressLine2Textbox", streetAddressLine2);
			locality = generateRandomString(49);
			companyDetailsPage.enterTextForCompaniesDetailsPage("localityTextbox", locality);
			region = generateRandomString(49);
			companyDetailsPage.enterTextForCompaniesDetailsPage("regionTextbox", region);
			postalCode = generateRandomString(11);
			companyDetailsPage.enterTextForCompaniesDetailsPage("postalCodeTextbox", postalCode);

			companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("addressSaveButton");
			LOGGER.info("Clicked on save address button");
			companyDetailsPage.waitForElementsOfCompaniesDetailsPage("toastNotification");
			softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("toastNotification"), "Toast notifcation is not generated on updating the address on company details page");
			softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("updatedAddressLines", streetAddressLine1 + "," + streetAddressLine2), "Address lines not updated on company details page");
			softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("updatedCity", locality + " - " + postalCode), "City and postal code not updated on company details page");
			softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("updatedState", region), "State not updated on company details page");

			// Test Case 2 - This test cases validates change in primary account for a company
			companyDetailsPage.waitForElementsOfCompaniesDetailsPage("companyPrimaryAccount");
			String primaryAccount = companyDetailsPage.getTextOfCompaniesDetailsPage("companyPrimaryAccount");
			companyDetailsPage.waitForElementsOfCompaniesDetailsPage("editCompanyPrimaryAccount");
			companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("editCompanyPrimaryAccount");
			LOGGER.info("Clicked on edit primary account button");
			softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("primaryAccountHeader").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.list.primary_account_admin")), "Title on primary account popup is incorrect");
			sleeper(2000);
			waitForPageLoaded();
			companyDetailsPage.waitForElementsOfCompaniesDetailsPage("primaryAccountDropdown");
			companyDetailsPage.clickByJavaScriptOnCompaniesDetailsPage("primaryAccountDropdown");
			selectedPrimaryAccount = companyDetailsPage.selectFirstOptionFromDropdownOnCompaniesDetailsPage("primaryAccountList", primaryAccount);
			companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("primaryAccountDropdown");
			selectedPrimaryAccount = companyDetailsPage.selectFirstOptionFromDropdownOnCompaniesDetailsPage("primaryAccountList", primaryAccount);
			companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("cancelPrimaryAccount");
			LOGGER.info("Clicked on cancel button for primary account popup");
			softAssert.assertFalse(companyDetailsPage.getTextOfCompaniesDetailsPage("companyPrimaryAccount").equals(selectedPrimaryAccount), "Cancel functionality on primary account popup is not working");
			companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("editCompanyPrimaryAccount");
			LOGGER.info("Clicked on edit primary account button");
			companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("primaryAccountDropdown");
			selectedPrimaryAccount = companyDetailsPage.selectFirstOptionFromDropdownOnCompaniesDetailsPage("primaryAccountList", primaryAccount);
			companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("savePrimaryAccount");
			LOGGER.info("Clicked on save button for primary account popup");
			companyDetailsPage.waitForElementsOfCompaniesDetailsPage("toastNotification");
			softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("toastNotification"), "Toast notification is not generated after primary account field is changed");
			softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("companyPrimaryAccount").contains(selectedPrimaryAccount), "Save functionality on primary account popup is not working");

			softAssert.assertAll();
			LOGGER.info("All test cases of details tile on company details page for global admin view are verified successfully");
		} catch (Exception e) {
			LOGGER.info("Exception occured in method verifyDetailsTileOnCompanyDetailsPage " + e.getMessage());
		} finally {
			logout();
			login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
			waitForPageLoaded();
			impersonateCompanyByCompanyName(detailsCompany);
			Thread.sleep(3000);
			companyDetailsPage.scrollOnCompaniesDetailsPage("companyPrimaryAccountHeader");
			companyDetailsPage.verifyElementIsClickableOfCompaniesDetailsPage("editCompanyPrimaryAccount");
			companyDetailsPage.waitForElementsOfCompaniesDetailsPage("editCompanyPrimaryAccount");
			sleeper(2000);
			companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("editCompanyPrimaryAccount");
			LOGGER.info("Clicked on edit primary account button");
			companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("primaryAccountDropdown");
			companyDetailsPage.selectFirstOptionFromDropdown("primaryAccountList");
			companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("savePrimaryAccount");
			LOGGER.info("Clicked on save button for primary account popup");
		}
	}

	/**
	 * This test case verifies add company approval functionality through root user
	 * 
	 * @throws Exception
	 */
	@Test(priority = 16, groups = { "REGRESSIONCOMPANIES1", "SMOKE", "REGRESSION_CI", "SMOKE_CI" }, description = "TESTCASE-292373", enabled = false)
	public final void verifyAddCompanyApprovalFunctionalityThroughRoot() throws Exception {
		login("ROOT_INVITE_FLOW_EMAIL", "ROOT_INVITE_FLOW_PASSWORD");
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		try {
			LOGGER.info("Redirected to company list page");
			waitForPageLoaded();
			resetTableConfiguration();
			
			Assert.assertTrue(companiesPage.createCompanyUsingAPI(getEnvironmentSpecificData(System.getProperty("environment"), "ADD_COMPANY_THROUGH_ROOT_NAME"), getEnvironmentSpecificData(System.getProperty("environment"), "ADD_COMPANY_THROUGH_ROOT_COUNTRY_CODE"), LanguageCode, getEnvironmentSpecificData(System.getProperty("environment"), "ADD_COMPANY_THROUGH_ROOT_MSP_NAME_ID"), getEnvironmentSpecificData(System.getProperty("environment"), "ADD_COMPANY_THROUGH_ROOT_PARTNER_NAME_ID"),getEnvironmentSpecificData(System.getProperty("environment"), "ADD_COMPANY_THROUGH_ROOT_EMAIL"),CompaniesVariables.COMPANY_STANDARDTRIAL), "Company Creation failed");

			impersonateCompanyByCompanyName(getEnvironmentSpecificData(System.getProperty("environment"), "ADD_COMPANY_THROUGH_ROOT_NAME"));
			LOGGER.info("Redirected to company details page");
			tenantID = getUrlOfCurrentPage().substring(getUrlOfCurrentPage().length() - 36);
			logout();
			LOGGER.info("Logged out");
			
			// Remove Pending Invitations from Company
			login("ITA_COMPANY_DETAILS_INVITE_FLOW_EMAIL", "ITA_COMPANY_DETAILS_INVITE_FLOW_PASSWORD");
			waitForPageLoaded();
			sleeper(2000);
			if (companiesPage.verifyElementsOfCompaniesPage("closeAddCompanyModalV3"))
				companiesPage.clickOnElementsOfCompaniesPage("closeAddCompanyModalV3");
			pressKey(Keys.ESCAPE);
			verifyUIVersion();
			gotoNonMSPSettingsTab();
			LOGGER.info("Redirected to Settings tab");
			waitForPageLoaded();
			companiesPage.waitForPresenceOfElementsOfCompaniesPage("assignedPartner");
			companiesPage.clickOnElementsOfCompaniesPage("assignedPartner");
			companiesPage.waitForPresenceOfElementsOfCompaniesPage("invitationLabel");
			
			// Approve Partner Invite
			waitForPageLoaded();
			// Verify Notification Icon for invitation
			companiesPage.clickOnElementsOfCompaniesPage("notificationBellIcon");
			companiesPage.waitForPresenceOfElementsOfCompaniesPage("invitationNotificationTitle");
			softAssert.assertTrue((companiesPage.getTextOfCompaniesPage("invitationNotificationTitle").split("[\\r\\n]+")[0]).equals(getTextLanguage(LanguageCode, "daas_ui", "partner.invitations").toUpperCase()), "Partner Invitation title is not matching.");
			softAssert.assertTrue(companiesPage.getTextOfCompaniesPage("invitationNotificationDescription").equals(getEnvironmentSpecificData(environment, "ADD_COMPANY_THROUGH_ROOT_PARTNER_NAME") + " (" + getTextLanguage(LanguageCode, "idm", "partner.id") + " " + getEnvironmentSpecificData(environment, "ADD_COMPANY_THROUGH_ROOT_PARTNER_ID") + ") " + CompaniesVariables.PARTNER_INVITATION_NOTIFICATION), "Partner Invitation description is not matching.");
				companiesPage.mouseHoverOnCompanyPage("invitationNotificationDescription");
				companiesPage.clickByJavaScriptOnCompaniesPage("hamburgerOnNotification");
			sleeper(3000);
			softAssert.assertTrue(companiesPage.getTextOfCompaniesPage("invitationNotificationApprove").equals(getTextLanguage(LanguageCode, "daas_ui", "global.approve")), "Approve button is not matching.");
			softAssert.assertTrue(companiesPage.getTextOfCompaniesPage("invitationNotificationDecline").equals(getTextLanguage(LanguageCode, "daas_ui", "global.decline")), "Decline button is not matching.");
			companiesPage.clickOnElementsOfCompaniesPage("notificationBellIcon");
			LOGGER.info("Notification Icon is verified successfully");

			// Approve Invitation from Partner
			waitForPageLoaded();
			companiesPage.waitForPresenceOfElementsOfCompaniesPage("invitationLabel");
			companiesPage.clickOnElementsOfCompaniesPage("acceptInvitationBtn");
			companiesPage.verifyElementsOfCompaniesPage("approveLabel");

			softAssert.assertTrue(companiesPage.getTextOfCompaniesPage("approveLabel").equals(getTextLanguage(LanguageCode, "daas_ui", "company.approve.partner")), "Approve Label is not matching.");
			softAssert.assertTrue(companiesPage.getTextOfCompaniesPage("addApproverBody").equals(getTextLanguage(LanguageCode, "daas_ui", "partner_invite.accept.message").replace("{partner_name}", getEnvironmentSpecificData(environment, "ADD_COMPANY_THROUGH_ROOT_PARTNER_NAME"))), "Approve body message is not matching.");
			softAssert.assertTrue(companiesPage.getTextOfCompaniesPage("addApproverBodyText2").equals(getTextLanguage(LanguageCode, "daas_ui", "company.approve.partner.desc.line2")), "Approve text second is not matching.");
			companiesPage.verifyElementsOfCompaniesPage("approveBtn");
			companiesPage.clickOnElementsOfCompaniesPage("approveBtn");
			companiesPage.waitForElementsOfCompaniesPage("toastNotification");
			String toastNotification = companiesPage.getTextOfCompaniesPage("toastNotification");
			softAssert.assertTrue(toastNotification.equals(getTextLanguage(LanguageCode, "daas_ui", "partner_invite.message.update")), "Toast notification is not generated after accepting invitation from Partner");
			LOGGER.info("Invitation accepted from Partner");

			softAssert.assertAll();
			LOGGER.info("All test cases for add company approval functionality through root user passed");
		} finally {
			// Logout from account
			logout();
			login("ROOT_INVITE_FLOW_EMAIL", "ROOT_INVITE_FLOW_PASSWORD");

			// Remove created company
			Assert.assertTrue(companiesPage.removeCompanyUsingAPI(tenantID), "Company removal failed");
			LOGGER.info("Company has been deleted");
		}
	}
	/**
	 * This test case verifies add company decline functionality through root user
	 * 
	 * @throws Exception
	 */
	@Test(priority = 17, description = "TEST CASE - 292373", groups = { "REGRESSIONCOMPANIES1", "SMOKE", "REGRESSION_CI", "SMOKE_CI" }, enabled = false)
	public final void verifyAddCompanyDeclineFunctionalityThroughRoot() throws Exception {

		login("ROOT_INVITE_FLOW_EMAIL", "ROOT_INVITE_FLOW_PASSWORD");
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		try {
			LOGGER.info("Redirected to company list page");
			waitForPageLoaded();
			resetTableConfiguration();
			
			Assert.assertTrue(companiesPage.createCompanyUsingAPI(getEnvironmentSpecificData(System.getProperty("environment"), "ADD_COMPANY_THROUGH_ROOT_NAME"), getEnvironmentSpecificData(System.getProperty("environment"), "ADD_COMPANY_THROUGH_ROOT_COUNTRY_CODE"), LanguageCode, getEnvironmentSpecificData(System.getProperty("environment"), "ADD_COMPANY_THROUGH_ROOT_MSP_NAME_ID"), getEnvironmentSpecificData(System.getProperty("environment"), "ADD_COMPANY_THROUGH_ROOT_PARTNER_NAME_ID"),getEnvironmentSpecificData(System.getProperty("environment"), "ADD_COMPANY_THROUGH_ROOT_EMAIL"),CompaniesVariables.COMPANY_STANDARDTRIAL), "Company Creation failed");

			impersonateCompanyByCompanyName(getEnvironmentSpecificData(System.getProperty("environment"), "ADD_COMPANY_THROUGH_ROOT_NAME"));
			LOGGER.info("Redirected to company details page");
			tenantID = getUrlOfCurrentPage().substring(getUrlOfCurrentPage().length() - 36);
			logout();
			LOGGER.info("Logged out");

			// Remove Pending Invitations from Company
			login("ITA_COMPANY_DETAILS_INVITE_FLOW_EMAIL", "ITA_COMPANY_DETAILS_INVITE_FLOW_PASSWORD");
			waitForPageLoaded();
			sleeper(2000);
			if (companiesPage.verifyElementsOfCompaniesPage("closeAddCompanyModalV3"))
				companiesPage.clickOnElementsOfCompaniesPage("closeAddCompanyModalV3");
			pressKey(Keys.ESCAPE);
			verifyUIVersion();
			gotoNonMSPSettingsTab();
			LOGGER.info("Redirected to Settings tab");
			waitForPageLoaded();
			companiesPage.waitForPresenceOfElementsOfCompaniesPage("assignedPartner");
			companiesPage.clickOnElementsOfCompaniesPage("assignedPartner");
			companiesPage.waitForPresenceOfElementsOfCompaniesPage("invitationLabel");

			// Decline Partner Invite
			waitForPageLoaded();
			// Verify Notification Icon for invitation
			companiesPage.clickOnElementsOfCompaniesPage("notificationBellIcon");
			companiesPage.waitForPresenceOfElementsOfCompaniesPage("invitationNotificationTitle");
				companiesPage.mouseHoverOnCompanyPage("invitationNotificationDescription");
				companiesPage.clickOnElementsOfCompaniesPage("hamburgerOnNotification");
			softAssert.assertTrue(companiesPage.getTextOfCompanyPage("invitationNotificationApprove").equals(getTextLanguage(LanguageCode, "daas_ui", "global.approve")), "Approve button is not matching.");
			softAssert.assertTrue(companiesPage.getTextOfCompanyPage("invitationNotificationDecline").equals(getTextLanguage(LanguageCode, "daas_ui", "global.decline")), "Decline button is not matching.");
			companiesPage.clickOnElementsOfCompaniesPage("notificationBellIcon");
			LOGGER.info("Notification Icon is verified successfully");

			// Decline Partner Invite

			companiesPage.waitForPresenceOfElementsOfCompaniesPage("assignedPartner");
			companiesPage.clickOnElementsOfCompaniesPage("assignedPartner");
			companiesPage.waitForPresenceOfElementsOfCompaniesPage("invitationLabel");
			companiesPage.clickOnElementsOfCompaniesPage("declineInvitationBtn");
			companiesPage.verifyElementsOfCompaniesPage("decloneLabel");
			softAssert.assertTrue(companiesPage.getTextOfCompanyPage("decloneLabel").equals(getTextLanguage(LanguageCode, "daas_ui", "partner_invite.model.decline.title")), "Decline Label is not matching.");

			softAssert.assertTrue(companiesPage.getTextOfCompanyPage("decloneBodyMessage").equals(getTextLanguage(LanguageCode, "daas_ui", "partner_invite.decline.message").replace("{partner_name}", getEnvironmentSpecificData(environment, "ADD_COMPANY_THROUGH_ROOT_PARTNER_NAME"))), "Decline body message is not matching.");

			companiesPage.verifyElementsOfCompaniesPage("declineBtn");
			companiesPage.clickOnElementsOfCompaniesPage("declineBtn");
			companiesPage.waitForElementsOfCompaniesPage("toastNotification");
			String toastNotification = companiesPage.getTextOfCompaniesPage("toastNotification");
			softAssert.assertTrue(toastNotification.equals(getTextLanguage(LanguageCode, "daas_ui", "partner_invite.message.update")), "Toast notification is not generated after declining invitation from Partner.");
			LOGGER.info("Invitation declined from Partner");
			softAssert.assertAll();
			LOGGER.info("All test cases for add company decline functionality through root user passed");
		} finally {
			// Logout from account
			logout();
			login("ROOT_INVITE_FLOW_EMAIL", "ROOT_INVITE_FLOW_PASSWORD");

			// Remove created company
			Assert.assertTrue(companiesPage.removeCompanyUsingAPI(tenantID), "Company removal failed");
			LOGGER.info("Company has been deleted");
		}
	}

	@DataProvider
	public Object[][] getAddCompanyLoginData() {
		//Object[][] data = new Object[2][2];
		//data[0][0] = "PARTNER_ADMIN_COMPANY_DETAILS_INVITE_FLOW_EMAIL";
		//data[0][1] = "PARTNER_ADMIN_COMPANY_DETAILS_INVITE_FLOW_PASSWORD";
		//data[1][0] = "SERVICE_SPECIALIST_ADMIN_COMPANY_DETAILS_INVITE_FLOW_EMAIL";
		//data[1][1] = "SERVICE_SPECIALIST_ADMIN_COMPANY_DETAILS_INVITE_FLOW_PASSWORD";
		return new Object[][] {
			{"RESELLER_NEW_UI_EMAIL_US","RESELLER_NEW_UI_PASSWORD_US"}
		};
	}

	/**
	 * This test case verifies add company approval functionality through partner user
	 * 
	 * @param username - name of user fetched from data provider
	 * @param password - password of user fetched from data provider
	 * @throws Exception
	 */
	@Test(priority = 18, description = "TEST CASE - 292376 ; Roles ~ Reseller, Service Specialist", groups = { "REGRESSIONCOMPANIES1", "SMOKE", "REGRESSION_CI", "SMOKE_CI" }, dataProvider = "getAddCompanyLoginData", enabled = false)
	public final void verifyAddCompanyApprovalFunctionalityThroughPartner(String username, String password) throws Exception {

		login(username, password);
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		try {
			gotoCompaniesTab();
			LOGGER.info("Redirected to Companies tab");
			waitForPageLoaded();
			sleeper(2000);
			
			// Add company through partner
			gotoCompaniesTab();
			Assert.assertTrue(
					companiesPage.createCompanyUsingAPI(getEnvironmentSpecificData(System.getProperty("environment"), "ADD_COMPANY_THROUGH_ROOT_NAME"), getEnvironmentSpecificData(System.getProperty("environment"), "COUNTRY_CODE"), LanguageCode, getEnvironmentSpecificData(System.getProperty("environment"), "MSP_ID"), getEnvironmentSpecificData(System.getProperty("environment"), "PARTNER_ID1"), getEnvironmentSpecificData(System.getProperty("environment"), "ADD_COMPANY_THROUGH_ROOT_EMAIL"), CompaniesVariables.COMPANY_STANDARDTRIAL),
					"Company not created cannot proceed further");
			LOGGER.info("New company created through reseller login");
			logout();

			// Approve Partner Invite
			login("ITA_COMPANY_DETAILS_INVITE_FLOW_EMAIL", "ITA_COMPANY_DETAILS_INVITE_FLOW_PASSWORD");
			waitForPageLoaded();
			tenantID = getValueFromToken("tenant");
			gotoNonMSPSettingsTab();
			LOGGER.info("Redirected to Settings tab");
			waitForPageLoaded();
			companiesPage.waitForPresenceOfElementsOfCompaniesPage("assignedPartner");
			companiesPage.clickOnElementsOfCompaniesPage("assignedPartner");
			companiesPage.waitForPresenceOfElementsOfCompaniesPage("invitationLabel");
			waitForPageLoaded();
			
			// Verify Notification Icon for invitation
			companiesPage.clickOnElementsOfCompaniesPage("notificationBellIcon");
			companiesPage.waitForPresenceOfElementsOfCompaniesPage("invitationNotificationTitle");
			softAssert.assertTrue(companiesPage.getTextOfCompanyPage("invitationNotificationTitle").equals(getTextLanguage(LanguageCode, "daas_ui", "partner.invitations").toUpperCase()), "Partner Invitation title is not matching.");
			softAssert.assertTrue(companiesPage.getTextOfCompanyPage("invitationNotificationDescription").equals(getEnvironmentSpecificData(environment, "AUTOMATION_PARTNER") + " (" + getTextLanguage(LanguageCode, "idm", "partner.id") + " " + getEnvironmentSpecificData(environment, "AUTOMATION_PARTNER_ID") + ") " + CompaniesVariables.PARTNER_INVITATION_NOTIFICATION), "Partner Invitation description is not matching.");
					companiesPage.mouseHoverOnCompanyPage("invitationNotificationDescription");
					companiesPage.clickOnElementsOfCompaniesPage("hamburgerOnNotification");
			softAssert.assertTrue(companiesPage.getTextOfCompanyPage("invitationNotificationApprove").equals(getTextLanguage(LanguageCode, "daas_ui", "global.approve")), "Approve button is not matching.");
			softAssert.assertTrue(companiesPage.getTextOfCompanyPage("invitationNotificationDecline").equals(getTextLanguage(LanguageCode, "daas_ui", "global.decline")), "Decline button is not matching.");
			companiesPage.clickOnElementsOfCompaniesPage("notificationBellIcon");
			LOGGER.info("Notification Icon is verified successfully");

			// Approve Invitation from Partner
			waitForPageLoaded();
			companiesPage.waitForPresenceOfElementsOfCompaniesPage("invitationLabel");
			companiesPage.clickOnElementsOfCompaniesPage("acceptInvitationBtn");
			companiesPage.verifyElementsOfCompaniesPage("approveLabel");
			softAssert.assertTrue(companiesPage.getTextOfCompanyPage("approveLabel").equals(getTextLanguage(LanguageCode, "daas_ui", "company.approve.partner")), "Approve Label is not matching.");
			softAssert.assertTrue(companiesPage.getTextOfCompanyPage("addApproverBody").equals(getTextLanguage(LanguageCode, "daas_ui", "partner_invite.accept.message").replace("{partner_name}", getEnvironmentSpecificData(environment, "AUTOMATION_PARTNER"))), "Approve body message is not matching.");
			softAssert.assertTrue(companiesPage.getTextOfCompanyPage("addApproverBodyText2").equals(getTextLanguage(LanguageCode, "daas_ui", "company.approve.partner.desc.line2")), "Approve text second is not matching.");
			companiesPage.verifyElementsOfCompaniesPage("approveBtn");
			companiesPage.clickOnElementsOfCompaniesPage("approveBtn");
			companiesPage.waitForElementsOfCompaniesPage("toastNotification");
			softAssert.assertTrue(companiesPage.matchTextOfCompanyPage("toastNotification", getTextLanguage(LanguageCode, "daas_ui", "partner_invite.message.update")), "Toast notification is not generated after accepting invitation from Partner");
			LOGGER.info("Invitation accepted from Partner");

			softAssert.assertAll();
			LOGGER.info("All test cases for add company approval functionality through partner passed");
		} finally {
			logout();
			login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
			// Remove created company
			Assert.assertTrue(companiesPage.removeCompanyUsingAPI(tenantID), "Company removal failed");
			LOGGER.info("Company has been deleted");
		}
	}

	/**
	 * This test case verifies add company decline functionality through partner user
	 * 
	 * @param username - name of user fetched from data provider
	 * @param password - password of user fetched from data provider
	 * @throws Exception Will enable once changes updated as per User Story 302920: [Partner][UI] Combine the list of Active, Grace Period, Suspended and Invited companies.
	 */
	@Test(priority = 19, description = "TEST CASE - 292376 ; Roles ~ Reseller, Service Specialist", groups = { "REGRESSIONCOMPANIES1", "SMOKE", "REGRESSION_CI", "SMOKE_CI" }, dataProvider = "getAddCompanyLoginData", enabled = false)
	public final void verifyAddCompanyDeclineFunctionalityThroughPartner(String username, String password) throws Exception {

		login(username, password);
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		try {
			gotoCompaniesTab();
			LOGGER.info("Redirected to Companies tab");

			waitForPageLoaded();
			sleeper(2000);
			
			// Add company through partner
			gotoCompaniesTab();
			Assert.assertTrue(
					companiesPage.createCompanyUsingAPI(getEnvironmentSpecificData(System.getProperty("environment"), "ADD_COMPANY_THROUGH_ROOT_NAME"), getEnvironmentSpecificData(System.getProperty("environment"), "COUNTRY_CODE"), LanguageCode, getEnvironmentSpecificData(System.getProperty("environment"), "MSP_ID"), getEnvironmentSpecificData(System.getProperty("environment"), "PARTNER_ID1"), getEnvironmentSpecificData(System.getProperty("environment"), "ADD_COMPANY_THROUGH_ROOT_EMAIL"), CompaniesVariables.COMPANY_STANDARDTRIAL),
					"Company not created cannot proceed further");
			LOGGER.info("New company created through reseller login");
			logout();
			
			// Decline Partner Invite
			login("ITA_COMPANY_DETAILS_INVITE_FLOW_EMAIL", "ITA_COMPANY_DETAILS_INVITE_FLOW_PASSWORD");
			waitForPageLoaded();
			tenantID = getValueFromToken("tenant");
			gotoNonMSPSettingsTab();
			LOGGER.info("Redirected to Settings tab");
			waitForPageLoaded();
			companiesPage.waitForPresenceOfElementsOfCompaniesPage("assignedPartner");
			companiesPage.clickOnElementsOfCompaniesPage("assignedPartner");
			companiesPage.waitForPresenceOfElementsOfCompaniesPage("invitationLabel");
			waitForPageLoaded();
			
			// Verify Notification Icon for invitation
			companiesPage.clickOnElementsOfCompaniesPage("notificationBellIcon");
			companiesPage.waitForPresenceOfElementsOfCompaniesPage("invitationNotificationTitle");
			softAssert.assertTrue(companiesPage.getTextOfCompanyPage("invitationNotificationTitle").equals(getTextLanguage(LanguageCode, "daas_ui", "partner.invitations").toUpperCase()), "Partner Invitation title is not matching.");
			softAssert.assertTrue(companiesPage.getTextOfCompanyPage("invitationNotificationDescription").equals(getEnvironmentSpecificData(environment, "AUTOMATION_PARTNER") + " (" + getTextLanguage(LanguageCode, "idm", "partner.id") + " " + getEnvironmentSpecificData(environment, "AUTOMATION_PARTNER_ID") + ") " + CompaniesVariables.PARTNER_INVITATION_NOTIFICATION), "Partner Invitation description is not matching.");
					companiesPage.mouseHoverOnCompanyPage("invitationNotificationDescription");
					companiesPage.clickOnElementsOfCompaniesPage("hamburgerOnNotification");
			
			softAssert.assertTrue(companiesPage.getTextOfCompanyPage("invitationNotificationApprove").equals(getTextLanguage(LanguageCode, "daas_ui", "global.approve")), "Approve button is not matching.");
			softAssert.assertTrue(companiesPage.getTextOfCompanyPage("invitationNotificationDecline").equals(getTextLanguage(LanguageCode, "daas_ui", "global.decline")), "Decline button is not matching.");
			companiesPage.clickOnElementsOfCompaniesPage("notificationBellIcon");
			LOGGER.info("Notification Icon is verified successfully");

			// Decline Partner Invite
			LOGGER.info("Redirected to Settings tab");
			waitForPageLoaded();
			companiesPage.waitForPresenceOfElementsOfCompaniesPage("invitationLabel");
			companiesPage.clickOnElementsOfCompaniesPage("declineInvitationBtn");
			companiesPage.verifyElementsOfCompaniesPage("decloneLabel");
			softAssert.assertTrue(companiesPage.getTextOfCompanyPage("decloneLabel").equals(getTextLanguage(LanguageCode, "daas_ui", "partner_invite.model.decline.title")), "Decline Label is not matching.");
			softAssert.assertTrue(companiesPage.getTextOfCompanyPage("decloneBodyMessage").equals(getTextLanguage(LanguageCode, "daas_ui", "partner_invite.decline.message").replace("{partner_name}", getEnvironmentSpecificData(environment, "AUTOMATION_PARTNER"))), "Decline body message is not matching.");
			companiesPage.verifyElementsOfCompaniesPage("declineBtn");
			companiesPage.clickOnElementsOfCompaniesPage("declineBtn");
			companiesPage.waitForElementsOfCompaniesPage("toastNotification");
			softAssert.assertTrue(companiesPage.matchTextOfCompanyPage("toastNotification", getTextLanguage(LanguageCode, "daas_ui", "partner_invite.message.update")), "Toast notification is not generated after declining invitation from Partner");
			LOGGER.info("Invitation declined from Partner");
	
			softAssert.assertAll();
			LOGGER.info("All test cases for add company decline functionality through partner passed");
		} finally {

			logout();
			login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
			// Remove created company
			Assert.assertTrue(companiesPage.removeCompanyUsingAPI(tenantID), "Company removal failed");
			LOGGER.info("Company has been deleted");
		}
	}

	/**
	 * This test case verifies tiles on company details page for a report admin user
	 * 
	 * @throws Exception
	 */
	@Test(priority = 20, description = "Test case ID:258959, Failing due to Bug 314363: [Core] DOMAIN NAME tile is editable for report admin", groups = { "REGRESSION", "SMOKE", "REGRESSION_CI", "SMOKE_CI", "STABILIZATION_STAGING" })
	public final void verifyReportAdminCompanyDetailsPage() throws Exception {
		login("REPORT_ADMIN_STAGING_EMAIL","REPORT_ADMIN_STAGING_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		CompaniesDetailsPage companyDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver()).getInstance();

		gotoSettingsTabWorkflow();
		LOGGER.info("Redirected to settings tab");
		waitForPageLoaded();

		// Verify Company Information section
//		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("companyInfoTitle"), "Company Overview section is not present under company details page");
//		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("companyInfoTitle").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "global.details.tab.overview_title")), "Company Overview Label did not match");
//
//		// Verify details tile strings and permissions
//		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("detailsTileHeader"), "Profile tile is not present under company details page");
//		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("detailsTileHeader").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "global.details.tab.tile.profile_title")), "Profile tile header did not match");
//
//		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("companyNameLabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.company_name")), "Company name label did not match");
//		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("companyAddressLabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.list.address")), "Address label did not match");
//		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("companyTimeZone").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.list.preferred_time_zone")), "Preferred time zone label did not match");
//		softAssert.assertFalse(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("editCompanyName"), "Edit button is present for company name tile");
//		softAssert.assertFalse(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("editCompanyaddress"), "Edit button is present for address tile");
//		softAssert.assertFalse(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("editCompanyTimeZone"), "Edit button is present for time zone tile");
//		LOGGER.info("Verified details tile strings and permissions.");
		
		// Verify Preferences section
		companyDetailsPage.scrollToTop();
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("preferenceTab");
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("preferencesTitle"), "Preferences section is not present under company details page");

		// Verify company pin tile strings and permissions
//		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("widePinTileHeader"), "Company pin tile is not present under company details page");
//		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("widePinTileHeader").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.company_pin")), "Company pin tile header did not match");
//		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("widePinTileDesc").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section_description.wide_pin")), "Company pin tile desc did not match");
//		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("companyPinLabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.company_pin")), "Company pin tile label did not match");
//		softAssert.assertFalse(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("editCompanyPin"), "Edit button is visible for company pin tile");
//		LOGGER.info("Verified company-wide pin tile strings and permissions.");

		// Verify incident tile strings and permissions
		if (toggleVerification(CompaniesVariables.INCIDENTRULEUI, "REPORT_ADMIN_NEW_UI_Companies_EMAIL_COMPANIES") == false && toggleVerification(CompaniesVariables.INCIDENTSUBSCRIPTIONRULE, "REPORT_ADMIN_NEW_UI_Companies_EMAIL_COMPANIES") == true) {
			softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("incidentTileHeader"), "Incident tile is not present under company details page");
			softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("incidentTileHeader").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.incidents").toUpperCase()), "Incident tile header did not match");
			softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("incidentLabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.incidents")), "Incident tile label did not match");
			softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("editCompanyIncidentSubscriptions"), "Edit button is missing for incident tile");
			LOGGER.info("Verified incident tile strings and permissions.");
		}

		// Verify custom field and lifecycle tile visibility
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("customFieldTile"), "Company pin tile is not present under company details page");
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("lifecycleTile"), "Company pin tile is not present under company details page");

		// Verify Report tile strings and permissions
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("reportTileHeader"), "Report tile is not present under company details page");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("reportTileHeader").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "pagetitle.reports").toUpperCase()), "Report tile header did not match");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("reportDesc").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section_description.reports")), "Report tile desc did not match");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("bromiumLabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.pro_sec_repos.title")), "Bromium label did not match");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("rposLabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.rpos_config.title")), "Rpos label did not match");
		softAssert.assertFalse(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("toggleBromium"), "Toggle is missing for HP Sure Click Advanced Reports");
		softAssert.assertFalse(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("toggleRpos"), "Toggle is missing for Retail Device Inventory Reports");
		LOGGER.info("Verified Report tile strings and permissions.");

		softAssert.assertAll();
		LOGGER.info("All test cases of company details page for report admin view passed");
	}

	/**
	 * This test case verifies detail button functionality on company list page
	 * 
	 * @throws Exception
	 */
	@Test(priority = 21, description = "NFR 242597, NFR 242598, NFR 242594", groups = { "REGRESSIONCOMPANIES1", "SMOKE", "REGRESSION_CI", "SMOKE_CI" })
	public final void verifyDetailButtonFunctionality() throws Exception {
		login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
		SoftAssert softAssert = new SoftAssert();
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		CompaniesDetailsPage companyDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver()).getInstance();

		waitForPageLoaded();
		resetTableConfiguration();
		Assert.assertTrue(companiesPage.verifyElementsOfCompaniesPage("firstCompany"), "No company is present on company page, unable to proceed further");
		LOGGER.info("Atleast one company is present, test case started");
		companiesPage.mouseHoverOnCompanyPage("firstRowName");
		companiesPage.clickOnElementsOfCompaniesPage("hamburgerIcon");
		companiesPage.clickByJavaScriptOnCompaniesPage("details");
		waitForPageLoaded();
		softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("companiesBreadcrumb1", getTextLanguage(LanguageCode, "daas_ui", "breadcrumbs.companies")), "BreadCrumb1 did not match");
		softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("companiesBreadcrumb2", getTextLanguage(LanguageCode, "daas_ui", "breadcrumbs.company_details")), "BreadCrumb2 did not match");
		LOGGER.info("Verified breadcrumb on company details page.");

		softAssert.assertAll();
		LOGGER.info("All test cases of detail button functionality passed");
	}

	/**
	 * This test case verifies localization functionality on company list page
	 * 
	 * @throws Exception
	 */
	@Test(priority = 22, description = "NFR 242597, NFR 242598, NFR 242594", groups = { "REGRESSIONCOMPANIES1", "SMOKE", "REGRESSION_CI", "SMOKE_CI", "PRODUCTION_SANITY", "STABILIZATION_STAGING" }, enabled = false)
	public final void verifyLocalizationFunctionality() throws Exception {
		login("ROOT_ADMIN_NEW_USER_COMPANIES", "ROOT_ADMIN_NEW_PASSWORD_COMPANIES");
		SoftAssert softAssert = new SoftAssert();
		LOGGER.info("Redirected to Companies tab");
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		waitForPageLoaded();

		List<WebElement> labels;
		companiesPage.waitForElementsOfCompaniesPage("addButton");
		companiesPage.clickOnElementsOfCompaniesPage("addButton");
		softAssert.assertTrue(companiesPage.getTextOfCompaniesPage("addPopUpHeader").equals(getTextLanguage(LanguageCode, "daas_ui", "company.add.popupone.title")), "Add company header on pop up is not correct");
		softAssert.assertTrue(companiesPage.getTextOfCompaniesPage("addPopupBody").equals(getTextLanguage(LanguageCode, "daas_ui", "company.add.popupone.heading")), "Add company body on pop up is not correct");
		labels = companiesPage.getElementsOfCompanyListPage("labelsList");
		softAssert.assertTrue(labels.get(0).getText().equals(getTextLanguage(LanguageCode, "daas_ui", "auto_linker.add.label.company")), "company name label on pop up is not correct");
		softAssert.assertTrue(labels.get(1).getText().equals(getTextLanguage(LanguageCode, "daas_ui", "company.add.lable.number_of_employees")), "number_of_employees label on pop up is not correct");
		softAssert.assertTrue(labels.get(2).getText().equals(getTextLanguage(LanguageCode, "daas_ui", "company.add.lable.country")), "country label on pop up is not correct");
		softAssert.assertTrue(labels.get(3).getText().equals(getTextLanguage(LanguageCode, "daas_ui", "company.add.lable.state")), "state label on pop up is not correct");
		softAssert.assertTrue(labels.get(4).getText().equals(getTextLanguage(LanguageCode, "daas_ui", "company.add.lable.city")), "city label on pop up is not correct");

		companiesPage.enterTextForCompaniesPage("companyNameOnPopup", "test");
		companiesPage.enterTextForCompaniesPage("cityOnPopup", CompaniesVariables.CITY);
		companiesPage.clickOnElementsOfCompaniesPage("countryOnPopup");
		companiesPage.clickOnElementsOfCompaniesPage("dropdownListOnPopup");

		companiesPage.clickOnElementsOfCompaniesPage("nextButtonOnPopup");
		labels = companiesPage.getElementsOfCompanyListPage("labelsList");
		softAssert.assertTrue(companiesPage.getTextOfCompaniesPage("addPopupBody").equals(getTextLanguage(LanguageCode, "daas_ui", "company.add.popuptwo.heading")), "subtitle on pop up is not correct");
		softAssert.assertTrue(companiesPage.getTextOfCompaniesPage("addPopuoBodyText2").equals(getTextLanguage(LanguageCode, "daas_ui", "company.add.popuptwo.discription")), "second subtitle on pop up is not correct");

		softAssert.assertTrue(labels.get(0).getText().equals(getTextLanguage(LanguageCode, "daas_ui", "global.form.first_name")), "first_name label on pop up is not correct");
		softAssert.assertTrue(labels.get(1).getText().equals(getTextLanguage(LanguageCode, "daas_ui", "global.form.last_name")), "last_name label on pop up is not correct");
		softAssert.assertTrue(labels.get(2).getText().equals(getTextLanguage(LanguageCode, "daas_ui", "global.form.email")), "email label on pop up is not correct");
		softAssert.assertTrue(labels.get(3).getText().equals(getTextLanguage(LanguageCode, "daas_ui", "global.form.phone")), "phone label on pop up is not correct");
		companiesPage.enterTextForCompaniesPage("firstNameOnPopup", CompaniesVariables.FIRST_NAME);
		companiesPage.enterTextForCompaniesPage("lastNameOnPopup", CompaniesVariables.COMPANIES_TITLE);
		companiesPage.enterTextForCompaniesPage("emailOnPopup", "test@gmail.com");
		companiesPage.enterTextForCompaniesPage("phoneNumberOnPopup", CompaniesVariables.NUMBER);

		companiesPage.clickOnElementsOfCompaniesPage("nextButtonOnPopup");
		sleeper(3000);
		labels = companiesPage.getElementsOfCompanyListPage("labelsList");
		softAssert.assertTrue(companiesPage.getTextOfCompaniesPage("addPopupBody").equals(getTextLanguage(LanguageCode, "daas_ui", "company.add.popupthree.heading")), "MSP body on pop up is not correct");
		softAssert.assertTrue(labels.get(0).getText().equals(getTextLanguage(LanguageCode, "daas_ui", "company.add.lable.msp")), "MSP label on pop up is not correct");
		softAssert.assertTrue(labels.get(1).getText().equals(getTextLanguage(LanguageCode, "daas_ui", "company.add.lable.reseller")), "Partner label on pop up is not correct");
		softAssert.assertAll();
		LOGGER.info("All test cases of localization functionlaity on company details page passed successfully");
	}

	/**
	 * This test case verifies strings on company details page for MSP 
	 * Disabling this test case since the similar functionality is tested in other test.
	 * 
	 * @throws Exception
	 */
	@Test(priority = 23, description = "TestCase ID: 239730,268885", groups = { "REGRESSIONCOMPANIES1", "REGRESSION_CI", "STABILIZATION_STAGING" },enabled=false)
	public final void verifyMSPcompanyDetailsPage() throws Exception {
		SoftAssert softAssert = new SoftAssert();

		login("NEW_MSP_ADMIN_US_EMAIl", "NEW_MSP_ADMIN_US_PASSWORD");
		CompaniesDetailsPage companyDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver()).getInstance();
		tenantID = getValueFromToken("tenant");
		apiurl = cataLystURL + CommonVariables.DETAILSSEARCHSERVICEAPI + tenantID + CommonVariables.DETAILSSEARCHSERVICEAPI2;
		tenantIdCompany = getTenantDetails(apiurl, CommonVariables.SEARCH_SERVICE_IDMTENANTS, autoCompany);
		companyID = getIDOverviewTab(tenantIdCompany, envURL);
		gotoCompaniesTab();
		LOGGER.info("Redirected to Companies tab");
		waitForPageLoaded();
		getUrl(getEnvironment(System.getProperty("environment")) + "ui/view/companies/" + getEnvironmentSpecificData(System.getProperty("environment"), "AUTOMATION_COMPANY_TENANT_ID"));
		
		
		// Verify breadcrumbs
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("companiesBreadcrumb1").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "breadcrumbs.companies")), "BreadCrumb1 did not match");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("companiesBreadcrumb2").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "breadcrumbs.company_details")), "BreadCrumb2 did not match");
		LOGGER.info("Verified breadcrumb on company details page.");

		// Verify company information title (Commenting these tests due to inconsistent locators.)
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("companyInfoTitle").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "global.details.tab.overview_title").toUpperCase()), "Company Overview tab title did not match");
//		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("panelCompanyName").equalsIgnoreCase(companyDetailsPage.getTextOfCompaniesDetailsPage("companyNameData").toUpperCase()), "Company Name does not match on left panel");
//		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("panelPhoneNumber").equalsIgnoreCase(companyDetailsPage.getTextOfCompaniesDetailsPage("companyPhoneData")), "Company Phone does not match on left panel");
//		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("panelTimeZone").equalsIgnoreCase(companyDetailsPage.getTextOfCompaniesDetailsPage("companyTimeZoneData")), "Company Time Zone does not match on left panel");
//		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("panelPrimaryAdminName").equalsIgnoreCase( companyDetailsPage.getTextOfCompaniesDetailsPage("companyMSPNameData")), "Company primary admin does not match on left panel");
//		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("panelMSPEmail").equalsIgnoreCase( companyDetailsPage.getTextOfCompaniesDetailsPage("companyMSPEmailData")), "Company MSP Email does not match on left panel");

		// Verify details tile strings and permissions
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("detailsTileHeader"), "Profile tile is not present under company details page");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("detailsTileHeader").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "global.details.tab.tile.profile_title").toUpperCase()), "Profile tile header did not match");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("companyNameLabel").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.company_name")), "Company name label did not match");
		companyDetailsPage.scrollOnCompaniesDetailsPage("companyAddressLabel");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("companyAddressLabel").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "companies.list.address").toUpperCase()), "Address label did not match");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("companyTimeZone").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "companies.list.preferred_time_zone")), "Preferred time zone label did not match");
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("editCompanyNameDetails"), "Edit button is missing for company name tile");
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("editCompanyaddress"), "Edit button is missing for address tile");
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("editCompanyTimeZone"), "Edit button is missing for time zone tile");
		LOGGER.info("Verified details tile strings and permissions.");

		// Verify BUSINESS IDENTIFICATION tile
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("businessIdentificationTile").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.business_id")), "BUSINESS IDENTIFICATION tile missing");
		softAssert.assertFalse(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("organizationID"), "organizationID edit button present for MSP");
		softAssert.assertFalse(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("oPSISystemsID"), "oPSISystemsID edit button present for MSP");
		softAssert.assertFalse(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("dUNSNumber"), "dUNSNumber edit button present for MSP");

		// Click on Preferences tab
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("preferenceTab");
		companyDetailsPage.waitForElementsOfCompaniesDetailsPage("preferencesTitle");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("preferencesTitle").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.preferences")), "Preferences tab title did not match");
		LOGGER.info("Redirected to Preferences tab");

		// Verify company pin tile strings and permissions
		companyDetailsPage.moveToElementOnCompaniesDetailsPage("widePinTileHeader");
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("widePinTileHeader"), "Company pin tile is not present under company details page");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("widePinTileHeader").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.company_pin")), "Company pin tile header did not match");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("widePinTileDesc").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section_description.wide_pin")), "Company pin tile desc did not match");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("companyPinLabel").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.company_pin")), "Company pin tile label did not match");
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("editCompanyPin"), "Edit button is missing for company pin tile");
		LOGGER.info("Verified company pin tile strings and permissions.");

		// Verify ARCHIVED DEVICES tile
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("archivedDeviceEditBtn"), "Archived tile edit button not present for MSP");

		// Verify AUTHENTICATION tile
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("editAuthenticationIcon"), "Authentication tile edit button not present for MSP");

		// Verify CLIENT APPLICATION tile
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("clientAppEditButton"), "Client application tile edit button not present for MSP");

		// Verify CUSTOM FIELDS FOR DEVICES tile
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("customFieldsEditButton"), "Custom Field edit button not present for MSP");

		// Verify LIFECYCLE STATUS tile
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("lifecycleStatusEditButton"), "Life cycle edit button not present for MSP");

		// Verify domain name tile strings and permissions
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("domainNameHeader"), "Domain name tile is not present under company details page");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("domainNameHeader").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "companies.list.domain_name").toUpperCase()), "Domain name tile header did not match");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("domainNameDesc").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section_description.domain_name")), "Domain name tile desc did not match");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("domainNameLabel").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "companies.list.domain_name")), "Domain name tile label did not match");
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("editCompanyDomainName"), "Edit button is missing for domain name pin tile");
		LOGGER.info("Verified domain name tile strings and permissions.");

		// Verify Preferences section
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("preferencesTitle"), "Preferences section is not present under company details page");

		// Verify Data Capture tile strings and permissions
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("dataCaptureTileHeader"), "Domain name tile is not present under company details page");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("dataCaptureTileHeader").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.data_capture").toUpperCase()), "Data capture tile header did not match");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("dataCaptureTileDesc").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "companies.details.section_description.data_capture")), "Data capture tile desc did not match");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("locationLabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "global.location")), "Location label did not match");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("antivirusFirewallLabel").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.antivirus_firewall")), "Antivirus and firewall label did not match");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("softwareInventoryLabel").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.software_inventory")), "Software Inventory label did not match");
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("locationToggle"), "Toggle is missing for location");
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("antivirusFirewallToggle"), "Toggle is missing for antivirus nad Firewall");
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("softwareInventoryToggle"), "Toggle is missing for software Inventory");
		LOGGER.info("Verified Data Capture tile strings and permissions.");

		// Verify Third party software tile strings and permissions
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("3rdPartyAppTileHeader"), "Third party software tile is not present under company details page");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("3rdPartyAppTileHeader").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.3rd_party_applications_integration")), "Third party software tile header did not match");

		// softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("ChromeEnterpriseIntegrationLabel",getTextLanguage(LanguageCode,
		// "daas_ui","companies.details.chromebook.title")), "Chrome Enterprise
		// Integration tiledesc did not match");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("ChromeEnterpriseIntegrationLabel").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "devicelist.emmtools.chrome_enterprise")), "Chrome Enterprise Integration tiledesc did not match");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("inheritGlobalLabel").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "companies.details.service_now.inherit_global_snow")), "Inherit global snow label did not match");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("serviceNowLabel").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "settings.service_now.title")), "Service Now Integration label did not match");
		// softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("editCompanyChromeEnterpriseIntegration"),"Edit
		// button is missing for Chrome EnterpriseIntegration");
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("editCompanyEmmSettings"), "Edit button is missing for EMM Rest API");
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("toggleGlobalServiceNow"), "Toggle is missing for service now");
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("editCompanyServiceNow"), "Edit button is missing for service now");
		LOGGER.info("Verified Third party software tile strings and permissions.");

		// Verify incident tile strings and permissions
		if (toggleVerification(CompaniesVariables.INCIDENTRULEUI, "MSP_DEVICE_LIST_EMAIL_COMPANIES") == false && toggleVerification(CompaniesVariables.INCIDENTSUBSCRIPTIONRULE, "MSP_DEVICE_LIST_EMAIL_COMPANIES") == true) {
			softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("incidentTileHeader"), "Incident tile is not present under company details page");
			softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("incidentTileHeader").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.incidents").toUpperCase()), "Incident tile header did not match");
			softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("incidentTileDesc").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "companies.details.section_description.incidents")), "Incident tile description did not match");
			softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("incidentLabel").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.incidents")), "Incident tile label did not match");
			softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("editCompanyIncidentSubscriptions"), "Edit button is missing for incident tile");
			LOGGER.info("Verified incident tile strings and permissions.");
		}

		// Verify Report tile strings and permissions
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("reportTileHeader"), "Report tile is not present under company details page");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("reportTileHeader").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "pagetitle.reports").toUpperCase()), "Report tile header did not match");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("reportDesc").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "companies.details.section_description.reports")), "Report tile desc did not match");
		//softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("bromiumLabel").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "companies.details.pro_sec_repos.title")), "Bromium label did not match"); // Toggle is disabled from UI
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("rposLabel").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "companies.details.rpos_config.title")), "Rpos label did not match");
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("toggleRpos"), "Toggle is missing for Retail Device Inventory Reports");
		LOGGER.info("Verified Report tile strings and permissions.");

		// Verify assignment settings tile for MSP
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("assignedMSPTab");
		companyDetailsPage.waitForElementsOfCompaniesDetailsPage("assignedMSPTitle");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("assignedMSPTitle").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.tile.assignment_settings")), "Title on assigned MSP tile is incorrect");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("assignedMSPLabel").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "companies.list.msp")), "Label on assigned MSP tile is incorrect");
		softAssert.assertFalse(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("editAssignedMSPButton"), "Edit button for MSP is not present on assigned MSP tile of company details page");

		// Verify assignment settings tile for Partner
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("assignedpartnerTab");
		companyDetailsPage.waitForElementsOfCompaniesDetailsPage("assignedPartnerTitle");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("assignedPartnerTitle").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "companies.details.tile.assignment_settings")), "Title on assigned partner tile is incorrect");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("assignedPartnerLabel").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "global.form.partner")), "Label on assigned partner tile is incorrect");
		softAssert.assertFalse(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("editAssignedpartnerButton"), "Edit button for partner is not present on assigned partner tile of company details page");

		softAssert.assertAll();
		LOGGER.info("All test cases of details tile on company details page for MSP view passed successfully");
	}

	/**
	 * This test case verifies strings on company details page for ITA
	 * Disabling this test case since this page has already been covered.
	 * @throws Exception
	 */
	@Test(priority = 24, description = "Testcase ID:258947", groups = { "REGRESSIONCOMPANIES1", "REGRESSION_CI","PENTEST" },enabled = false)
	public final void verifyITAdmincompanyDetailsPage() throws Exception {
		SoftAssert softAssert = new SoftAssert();

		login("ITA_COMPANY_DETAILS_EMAIL", "ITA_COMPANY_DETAILS_PASSWORD");
		CompaniesDetailsPage companyDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver()).getInstance();
		gotoSettingsTabWorkflow();
		waitForPageLoaded();
		LOGGER.info("Redirected to settings tab");

		// Verify company information title
		companyDetailsPage.waitForElementsOfCompaniesDetailsPage("companyInfoTitle");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("companyInfoTitle").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "global.details.tab.overview_title").toUpperCase()), "Company Overview tab title did not match");

		// Verify Left panel details (Commenting these tests due to inconsistent locators.)
//		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("panelCompanyName").equalsIgnoreCase(companyDetailsPage.getTextOfCompaniesDetailsPage("companyNameData").toUpperCase()), "Company Name does not match on left panel");
//		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("panelPhoneNumber").equalsIgnoreCase( companyDetailsPage.getTextOfCompaniesDetailsPage("companyPhoneData")), "Company Phone does not match on left panel");
//		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("panelTimeZone").equalsIgnoreCase( companyDetailsPage.getTextOfCompaniesDetailsPage("companyTimeZoneData")), "Company Time Zone does not match on left panel");
//		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("panelPrimaryAdminName").equalsIgnoreCase(companyDetailsPage.getTextOfCompaniesDetailsPage("companyMSPNameData")), "Company primary admin does not match on left panel");
//		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("panelMSPEmail").equalsIgnoreCase(companyDetailsPage.getTextOfCompaniesDetailsPage("companyMSPEmailData")), "Company MSP Email does not match on left panel");

		// Verify details tile strings and permissions
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("detailsTileHeader"), "Profile tile is not present under company details page");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("detailsTileHeader").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "global.details.tab.tile.profile_title").toUpperCase()), "Profile tile header did not match");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("companyNameLabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.company_name")), "Company name label did not match");
		companyDetailsPage.scrollOnCompaniesDetailsPage("companyAddressLabel");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("companyAddressLabel").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "companies.list.address")), "Address label did not match");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("companyTimeZone").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.list.preferred_time_zone")), "Preferred time zone label did not match");
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("editCompanyNameDetails"), "Edit button is missing for company name tile");
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("editCompanyaddress"), "Edit button is missing for address tile");
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("editCompanyTimeZone"), "Edit button is missing for time zone tile");
		LOGGER.info("Verified details tile strings and permissions.");
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("editCompanyPrimaryAccount"), "Edit button is missing for Primary Account tile");
		softAssert.assertFalse(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("editCompanyMsp"), "Edit button is present for phone number tile");
		softAssert.assertFalse(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("editCompanyPartner"), "Edit button is present for address tile");
		LOGGER.info("Verified Accounting assignment tile strings and permissions.");

		// Click on Preferences tab
		companyDetailsPage.scrollToTop();
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("preferenceTab").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.preferences").toUpperCase()), "Preferences tab title did not match");
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("preferenceTab");
		LOGGER.info("Redirected to Preferences tab");

		// Verify company pin tile strings and permissions
		companyDetailsPage.scrollOnCompaniesDetailsPage("widePinTileHeader");
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("widePinTileHeader"), "Company pin tile is not present under company details page");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("widePinTileHeader").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.company_pin").toUpperCase()), "Company pin tile header did not match");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("widePinTileDesc").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section_description.wide_pin")), "Company pin tile desc did not match");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("companyPinLabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.company_pin")), "Company pin tile label did not match");
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("editCompanyPin"), "Edit button is missing for company pin tile");
		LOGGER.info("Verified company pin tile strings and permissions.");

		// Verify domain name tile strings and permissions
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("domainNameHeader"), "Domain name tile is not present under company details page");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("domainNameHeader").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.list.domain_name").toUpperCase()), "Domain name tile header did not match");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("domainNameDesc").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section_description.domain_name")), "Domain name tile desc did not match");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("domainNameLabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.list.domain_name")), "Domain name tile label did not match");
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("editCompanyDomainName"), "Edit button is present for domain name pin tile");
		LOGGER.info("Verified domain name tile strings and permissions.");

		// Verify Data Capture tile strings and permissions
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("dataCaptureTileHeader"), "Domain name tile is not present under company details page");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("dataCaptureTileHeader").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.data_capture").toUpperCase()), "Data capture tile header did not match");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("dataCaptureTileDesc").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section_description.data_capture")), "Data capture tile desc did not match");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("locationLabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "global.location")), "Location label did not match");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("antivirusFirewallLabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.antivirus_firewall")), "Antivirus and firewall label did not match");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("softwareInventoryLabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.software_inventory")), "Software Inventory label did not match");
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("locationToggle"), "Toggle is missing for location");
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("antivirusFirewallToggle"), "Toggle is missing for antivirus nad Firewall");
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("softwareInventoryToggle"), "Toggle is missing for software Inventory");
		LOGGER.info("Verified Data Capture tile strings and permissions.");

		// Verify incident tile strings and permissions
		if (toggleVerification(CompaniesVariables.INCIDENTRULEUI, "IT_ADMIN_COMPANY_USER_EMAIL_COMPANIES") == false && toggleVerification(CompaniesVariables.INCIDENTSUBSCRIPTIONRULE, "IT_ADMIN_COMPANY_USER_EMAIL_COMPANIES") == true) {
			softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("incidentTileHeader"), "Incident tile is not present under company details page");
			softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("incidentTileHeader").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.incidents").toUpperCase()), "Incident tile header did not match");
			softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("incidentTileDesc").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section_description.incidents")), "Incident tile description did not match");
			softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("incidentLabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.incidents")), "Incident tile label did not match");
			softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("editCompanyIncidentSubscriptions"), "Edit button is missing for incident tile");
			LOGGER.info("Verified incident tile strings and permissions.");
		}

		// Verify Report tile strings and permissions
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("reportTileHeader"), "Report tile is not present under company details page");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("reportTileHeader").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "pagetitle.reports").toUpperCase()), "Report tile header did not match");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("reportDesc").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section_description.reports")), "Report tile desc did not match");
		//softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("bromiumLabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.pro_sec_repos.title")), "Bromium label did not match"); // Toggle is disabled from UI
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("rposLabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.rpos_config.title")), "Rpos label did not match");
		softAssert.assertFalse(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("toggleBromium"), "Toggle is missing for HP Sure Click Advanced Reports");
		softAssert.assertFalse(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("toggleRpos"), "Toggle is missing for Retail Device Inventory Reports");
		LOGGER.info("Verified Report tile strings and permissions.");

		// Verify subscription key tile strings and permissions
		if (toggleVerification(CompaniesVariables.SUBSCRIPTIONTOGGLE, getCredentials(System.getProperty("environment"), "ITA_COMPANY_DETAILS_EMAIL"))) {
			// Click on Subscriptions tab
			softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("subscriptionTab").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "subscription.section.plans").toUpperCase()), "Subscription tab title did not match");
			companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("subscriptionTab");
			LOGGER.info("Redirected to Subscriptions tab");
			// Verify account summary tile strings and permissions
			softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("accountSummaryTileHeader"), "Account summary tile is not present under company details page");
			softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("accountSummaryTileHeader").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.subscription")), "Account summary tile header did not match");
			LOGGER.info("Verified account summary tile strings and permissions.");

			Assert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("LicensekeyTileHeader"), "Subscription key tile is not present under company details page");
			softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("LicensekeyTileHeader").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "sidemenu.subscriptions")), "License detail tile header did not match");
			softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("addNewKeyButton"), "Add new key button is not present under subscription key tile");
			softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("addNewKeyButton").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.add_subscription_key").toUpperCase()), "Add subscription key text did not match");
			LOGGER.info("Verified licence key tile strings and permissions.");
		}

		// Verify assignment settings tile for Partner
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("assignedpartnerTab");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("assignedPartnerTitle").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "companies.details.tile.assignment_settings").toUpperCase()), "Title on assigned partner tile is incorrect");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("assignedPartnerLabel").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "global.form.partner")), "Label on assigned partner tile is incorrect");
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("editAssignedpartnerButton"), "Edit button for partner is not present on assigned partner tile of company details page");

		softAssert.assertAll();
		LOGGER.info("All test cases of details tile on company details page for ITA view passed successfully");
	}

	/**
	 * This test case verifies status of toggles on company details page for MSP
	 * 
	 * @throws Exception
	 */
	@Test(priority = 25, description = "Testcase ID:239720, User Story: 335271",dataProvider = "loginDataForCompanySettingsChange", groups = { "REGRESSIONCOMPANIES1", "SMOKE", "REGRESSION_CI", "SMOKE_CI", "STABILIZATION_STAGING" },enabled=false)
	public final void verifyTogglesForMSP(String username, String password) throws Exception {
		login(username, password);
		SoftAssert softAssert = new SoftAssert();
		CompaniesDetailsPage companyDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver()).getInstance();

		if(username != "IT_ADMIN_PARTNERS_EMAIL"){
		gotoCompaniesTab();
		LOGGER.info("Redirected to company details page");
		waitForPageLoaded();
		impersonateCompanyByCompanyName(autoCompany);
		} else{
			gotoCompanySettingsTab();
			waitForPageLoaded();
		}

		// Verify Preferences section
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("preferenceTab");
		LOGGER.info("Navigated to Preference Tab");
		companyDetailsPage.waitForElementsOfCompaniesDetailsPage("dataCaptureTileHeader");
		companyDetailsPage.scrollOnCompaniesDetailsPage("dataCaptureTileHeader");

		// Verify Antivirus and Firewall toggle
		if (companyDetailsPage.getTextOfCompaniesDetailsPage("antivirusFirewallData").equals(getTextLanguage(LanguageCode, "daas_ui", "global.enabled"))) {
			companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("antivirusFirewallToggle");
			sleeper(2000);
			softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("antivirusFirewallData", getTextLanguage(LanguageCode, "daas_ui", "global.disabled")), "Antivirus status does not match after toggle change");
			LOGGER.info("Antivirus and Firewall toggle Disabled");
			gotoLogTab();
			softAssert.assertTrue(companyDetailsPage.verifyDescriptionOnLogsPage((CompaniesVariables.DATA_COLLECTION_TOGGLE_DISABLED).replace("{toggle_name}", getTextLanguage(LanguageCode, "lhserver", "settings.preferences.device_config.toggle.antivirusFirewall"))), "Description on logs page does not match after disabling antivirus and firewall toggle");
		} else {
			companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("antivirusFirewallToggle");
			sleeper(2000);
			softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("antivirusFirewallData", getTextLanguage(LanguageCode, "daas_ui", "global.enabled")), "Antivirus status does not match after toggle change");
			LOGGER.info("Antivirus and Firewall toggle enabled");
			gotoLogTab();
			softAssert.assertTrue(companyDetailsPage.verifyDescriptionOnLogsPage((CompaniesVariables.DATA_COLLECTION_TOGGLE_ENABLED).replace("{toggle_name}", getTextLanguage(LanguageCode, "lhserver", "settings.preferences.device_config.toggle.antivirusFirewall"))), "Description on logs page does not match after enabling antivirus and firewall toggle");
		}
		LOGGER.info("Verified description on Logs for Antivirus and firewall toggle");
		navigateToBack();

		// Verify Software Inventory toggle
		companyDetailsPage.scrollOnCompaniesDetailsPage("dataCaptureTileHeader");
		if (companyDetailsPage.getTextOfCompaniesDetailsPage("softwareInventoryData").equals(getTextLanguage(LanguageCode, "daas_ui", "global.enabled"))) {
			companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("softwareInventoryToggle");
			sleeper(2000);
			softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("softwareInventoryData", getTextLanguage(LanguageCode, "daas_ui", "global.disabled")), "Software Inventory toggle status does not match after toggle change");
			LOGGER.info("Software Inventory toggle Disabled");
			gotoLogTab();
			softAssert.assertTrue(companyDetailsPage.verifyDescriptionOnLogsPage((CompaniesVariables.DATA_COLLECTION_TOGGLE_DISABLED).replace("{toggle_name}", getTextLanguage(LanguageCode, "lhserver", "settings.preferences.device_config.toggle.softwareInventory"))), "Description on logs page does not match after disabling Software inventory toggle");	
		} else {
			companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("softwareInventoryToggle");
			sleeper(2000);
			softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("softwareInventoryData", getTextLanguage(LanguageCode, "daas_ui", "global.enabled")), "Software Inventory toggle status does not match after toggle change");
			LOGGER.info("Software Inventory toggle enabled");
			gotoLogTab();
			softAssert.assertTrue(companyDetailsPage.verifyDescriptionOnLogsPage((CompaniesVariables.DATA_COLLECTION_TOGGLE_ENABLED).replace("{toggle_name}", getTextLanguage(LanguageCode, "lhserver", "settings.preferences.device_config.toggle.softwareInventory"))), "Description on logs page does not match after enabling Software inventory toggle");
		}
		LOGGER.info("Verified description on Logs for Software Inventory toggle");
		navigateToBack();

		// Verify Web Application Usage toggle
		if (companyDetailsPage.getTextOfCompaniesDetailsPage("webApplicationUsageData").equals(getTextLanguage(LanguageCode, "daas_ui", "global.enabled"))) {
			companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("webApplicationUsageToggle");
			sleeper(2000);
			softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("webApplicationUsageData", getTextLanguage(LanguageCode, "daas_ui", "global.disabled")), "Web Application Usage status does not match after toggle change");
			LOGGER.info("Web Application Usage toggle Disabled");
			gotoLogTab();
			softAssert.assertTrue(companyDetailsPage.verifyDescriptionOnLogsPage((CompaniesVariables.DATA_COLLECTION_TOGGLE_DISABLED).replace("{toggle_name}", getTextLanguage(LanguageCode, "lhserver", "settings.preferences.device_config.toggle.webAppUsage"))), "Description on logs page does not match after disabling Web application Usage toggle");	
		} else {
			companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("webApplicationUsageToggle");
			sleeper(2000);
			softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("webApplicationUsageData", getTextLanguage(LanguageCode, "daas_ui", "global.enabled")), "Web Application Usage status does not match after toggle change");
			LOGGER.info("Web Application Usage toggle enabled");
			gotoLogTab();
			softAssert.assertTrue(companyDetailsPage.verifyDescriptionOnLogsPage((CompaniesVariables.DATA_COLLECTION_TOGGLE_ENABLED).replace("{toggle_name}", getTextLanguage(LanguageCode, "lhserver", "settings.preferences.device_config.toggle.webAppUsage"))), "Description on logs page does not match after enabling Web application Usage toggle");
		}
		LOGGER.info("Verified description on Logs for Web application usage toggle");
		navigateToBack();

		// Verify Location toggle
		companyDetailsPage.scrollOnCompaniesDetailsPage("dataCaptureTileHeader");
		if (companyDetailsPage.getTextOfCompaniesDetailsPage("locationData").equals(getTextLanguage(LanguageCode, "daas_ui", "global.enabled"))) {
			companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("locationToggle");
			sleeper(2000);
			softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("locationData", getTextLanguage(LanguageCode, "daas_ui", "global.disabled")), "Location toggle status does not match after toggle change");
			LOGGER.info("Location toggle Disabled");
			gotoLogTab();
			softAssert.assertTrue(companyDetailsPage.verifyDescriptionOnLogsPage((CompaniesVariables.DATA_COLLECTION_TOGGLE_DISABLED).replace("{toggle_name}", getTextLanguage(LanguageCode, "lhserver", "settings.preferences.device_config.toggle.location"))), "Description on logs page does not match after disabling location toggle");
		} else {
			companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("locationToggle");
			sleeper(2000);
			softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("locationData", getTextLanguage(LanguageCode, "daas_ui", "global.enabled")), "Location toggle status does not match after toggle change");
			LOGGER.info("Location toggle enabled");
			gotoLogTab();
			softAssert.assertTrue(companyDetailsPage.verifyDescriptionOnLogsPage((CompaniesVariables.DATA_COLLECTION_TOGGLE_ENABLED).replace("{toggle_name}", getTextLanguage(LanguageCode, "lhserver", "settings.preferences.device_config.toggle.location"))), "Description on logs page does not match after enabling location toggle");
		}
		LOGGER.info("Verified description on Logs for location toggle");
		navigateToBack();

		// Verify Blue Screen Crash Dumps toggle
		companyDetailsPage.scrollOnCompaniesDetailsPage("dataCaptureTileHeader");
		if (companyDetailsPage.getTextOfCompaniesDetailsPage("blueScreenCrashDumpsData").equals(getTextLanguage(LanguageCode, "daas_ui", "global.enabled"))) {
			companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("blueScreenCrashDumpsToggle");
			sleeper(2000);
			softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("blueScreenCrashDumpsData", getTextLanguage(LanguageCode, "daas_ui", "global.disabled")), "Blue Screen Crash Dumps toggle status does not match after toggle change");
			LOGGER.info("Blue Screen Crash Dumps toggle Disabled");
			gotoLogTab();
			softAssert.assertTrue(companyDetailsPage.verifyDescriptionOnLogsPage((CompaniesVariables.DATA_COLLECTION_TOGGLE_DISABLED).replace("{toggle_name}", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.mini_dump"))), "Description on logs page does not match after disabling blue screen crash dumps toggle");
		} else {
			companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("blueScreenCrashDumpsToggle");
			sleeper(2000);
			softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("blueScreenCrashDumpsData", getTextLanguage(LanguageCode, "daas_ui", "global.enabled")), "Blue Screen Crash Dumps toggle status does not match after toggle change");
			LOGGER.info("Blue Screen Crash Dumps toggle enabled");
			gotoLogTab();
			softAssert.assertTrue(companyDetailsPage.verifyDescriptionOnLogsPage((CompaniesVariables.DATA_COLLECTION_TOGGLE_ENABLED).replace("{toggle_name}", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.mini_dump"))), "Description on logs page does not match after enabling blue screen crash dumps toggle");
		}
		LOGGER.info("Verified description on Logs");
		navigateToBack();

		// Verify Network Identifiers toggle
		companyDetailsPage.scrollOnCompaniesDetailsPage("dataCaptureTileHeader");
		if (companyDetailsPage.getTextOfCompaniesDetailsPage("networkIdentifiersData").equals(getTextLanguage(LanguageCode, "daas_ui", "global.enabled"))) {
			companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("networkIdentifiersToggle");
			sleeper(2000);
			softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("networkIdentifiersData", getTextLanguage(LanguageCode, "daas_ui", "global.disabled")), "Network identifier toggle status does not match after toggle change");
			LOGGER.info("Network identifier toggle Disabled");
			gotoLogTab();
			softAssert.assertTrue(companyDetailsPage.verifyDescriptionOnLogsPage((CompaniesVariables.DATA_COLLECTION_TOGGLE_DISABLED).replace("{toggle_name}", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.network_identifiers"))), "Description on logs page does not match after disabling network identifier toggle");
		} else {
			companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("networkIdentifiersToggle");
			sleeper(2000);
			softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("networkIdentifiersData", getTextLanguage(LanguageCode, "daas_ui", "global.enabled")), "Blue Screen Crash Dumps toggle status does not match after toggle change");
			LOGGER.info("Network identifier toggle enabled");
			gotoLogTab();
			softAssert.assertTrue(companyDetailsPage.verifyDescriptionOnLogsPage((CompaniesVariables.DATA_COLLECTION_TOGGLE_ENABLED).replace("{toggle_name}", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.network_identifiers"))), "Description on logs page does not match after enabling network identifiers toggle");
		}
		LOGGER.info("Verified description on Logs");
		navigateToBack();

		// Verify User personal information toggle
		if (companyDetailsPage.getTextOfCompaniesDetailsPage("userPersonalInformationData").equals(getTextLanguage(LanguageCode, "daas_ui", "global.enabled"))) {
			companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("toggleStatus");
			sleeper(2000);
			companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("disableConfirmationUPIOFFPopup");
			companyDetailsPage.waitForElementsOfCompaniesDetailsPage("tableOverlay");
			softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("userPersonalInformationData", getTextLanguage(LanguageCode, "daas_ui", "global.disabled")), "User personal information status does not match after toggle change");
			LOGGER.info("User personal information toggle Disabled");
			gotoLogTab();
			softAssert.assertTrue(companyDetailsPage.verifyDescriptionOnLogsPage((CompaniesVariables.DATA_COLLECTION_TOGGLE_DISABLED).replace("{toggle_name}", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.user_personal_info"))), "Description on logs page does not match after disabling User personal information toggle");
		} else {
			companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("toggleStatus");
			sleeper(2000);
			softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("userPersonalInformationData", getTextLanguage(LanguageCode, "daas_ui", "global.enabled")), "Antivirus status does not match after toggle change");
			LOGGER.info("User personal information toggle enabled");
			gotoLogTab();
			softAssert.assertTrue(companyDetailsPage.verifyDescriptionOnLogsPage((CompaniesVariables.DATA_COLLECTION_TOGGLE_ENABLED).replace("{toggle_name}", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.user_personal_info"))), "Description on logs page does not match after enabling User personal information toggle");
		}
		LOGGER.info("Verified description on Logs for User personal information toggle toggle");

		softAssert.assertAll();
		LOGGER.info("All test cases of toggles on company details page for MSP passed");
	}

	/**
	 * This test case verifies HP sure toggles on company details page for MSP
	 * 
	 * @throws Exception
	 * As part of Feature 987100: [WorkF] Disable and Remove Proactive Security analytics in TechPulse  // Toggle is disabled
	 */
	@Test(priority = 26, description = "Test Case ID:249295", groups = { "REGRESSIONCOMPANIES1", "SMOKE", "REGRESSION_CI", "SMOKE_CI", "STABILIZATION_STAGING" },enabled =false)
	public final void verifyHPSureForMSP() throws Exception {
		login("MSP_INCIDENTS_EMAIL", "MSP_INCIDENTS_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		CompaniesDetailsPage companyDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver()).getInstance();

		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		AnalyticsPage analyticsPage = new AnalyticsPage(PreDefinedActions.getDriver()).getInstance();

		List<String> reportHPExpectedList = new ArrayList<String>();
		gotoCompaniesTab();
		waitForPageLoaded();
		impersonateCompanyByCompanyName(getEnvironmentSpecificData(System.getProperty("environment"), "COMPANY_ARCHIVED_DEVICE"));

		// Verify Preferences section
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("preferenceTab");
		LOGGER.info("Navigated to Preference Tab");

		// Verify HP Sure Click Advanced Reports toggle
		reportHPExpectedList.add(getTextLanguage(LanguageCode, "MPI-Reporting-LHreports_service", "label.report_category_scasecurity"));
		if (companyDetailsPage.getTextOfCompaniesDetailsPage("bromiumData").equals(getTextLanguage(LanguageCode, "daas_ui", "global.enabled"))) {
			companyDetailsPage.verifyElementIsClickableOfCompaniesDetailsPage("toggleBromium");
			companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("toggleBromium");
			softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("bromiumData", getTextLanguage(LanguageCode, "daas_ui", "global.disabled")), "HP Sure status does not match after change");
			LOGGER.info("HP Sure toggle Disabled");
			companyDetailsPage.verifyElementIsClickableOfCompaniesDetailsPage("toggleBromium");
			companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("toggleBromium");
			Thread.sleep(1000);
			softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("bromiumData", getTextLanguage(LanguageCode, "daas_ui", "global.enabled")), "HP Sure status does not match after change");
			LOGGER.info("Hp Sure toggle Enabled");
		} else {
			companyDetailsPage.verifyElementIsClickableOfCompaniesDetailsPage("toggleBromium");
			companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("toggleBromium");
			softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("bromiumData", getTextLanguage(LanguageCode, "daas_ui", "global.enabled")), "HP Sure status does not match after change");
			LOGGER.info("HP Sure toggle enabled");
		}
		gotoDashboardTab();
		softAssert.assertTrue(dashboardPage.waitForElementsOfDashboardPage("hpSureClickAdvancedWidgetTitle"),"HP Sure chart not available");
		impersonateCompanyByNameReports("COMPANY_EMAIL_REPORTS_HPSURE","MSP_INCIDENTS_EMAIL");
		analyticsPage.clickOnElementsOfAnalyticsPage("createButton");
		analyticsPage.enterTextForAnalyticsPage("reportName", "Test");
		analyticsPage.selectCategoryForAnalyticsPage("Security");
		analyticsPage.clickOnElementsOfAnalyticsPage("subcategoryDropdown");
		softAssert.assertTrue(analyticsPage.verifyReportTypesDisplayedOnUI(reportHPExpectedList), "Reports does not match");
		softAssert.assertAll();
		LOGGER.info("All test cases of HP sure toggles for MSP passed");
	}

	/*
	 * This test case verify valid ServiceNow Configuration Type is displayed on company list page on Root Admin , MSP and Reseller
	 */
	@Test(priority = 28, description = "250011,235166,235171,250014", groups = { "REGRESSIONCOMPANIES1", "SMOKE", "REGRESSION_CI", "SMOKE_CI" }, enabled = false)
	public final void verifyServiceNowConfigurationTypeColumn() throws Exception {
		// Validating ServiceNow Configuration Type Column for Root Admin
		login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
		SoftAssert softAssert = new SoftAssert();
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();

		PreferencesPage preferencesPage = new PreferencesPage(PreDefinedActions.getDriver()).getInstance();

		gotoCompaniesTab();
		LOGGER.info("Redirected to Companies page of root admin");
		waitForPageLoaded();
		resetTableConfiguration();
		impersonateCompanyByEmailForRootAdmin(CompaniesVariables.COMPANIES_SNOW_VALIDATION_USER);
		goToPreferencesTab();
		String status = preferencesPage.setCompanySNOWToggleAtRoot(getTextLanguage(LanguageCode, "daas_ui", "settings.service_now.config.options.single_tenant"), LanguageCode);
		gotoCompaniesTab();
		LOGGER.info("Redirected to Companies page of Root admin again to validate changes made on prefrences page should get displayed on company list page");
		softAssert.assertTrue(status.equals(companiesPage.getTextOfCompaniesPage("serviceNowConfigurationType")), "ServiceNow Configuration Type column value present on company detail and company list page of Root admin does not matched");
		LOGGER.info("Successfully verified ServiceNow Configuration Type present on company detail page get displayed on company list page for given company for Root Admin");
		logout();
		LOGGER.info("Successfully Logged out from Root Admin");
		// Validating ServiceNow Configuration Type Column for MSP
		login("MSP_SNOW_ADMIN_EMAIL", "MSP_SNOW_ADMIN_PASSWORD");
		gotoCompaniesTab();
		LOGGER.info("Redirected to Companies page of MSP");
		waitForPageLoaded();
		resetTableConfiguration();
		if (companiesPage.waitForElementsOfCompaniesPage("removeAllSearchFilters"))
			companiesPage.clickOnElementsOfCompaniesPage("removeAllSearchFilters");
		companiesPage.verifyElementIsEnableOfCompaniesPage("nameColumn");
		companiesPage.enterTextForCompaniesPage("nameColumn", CompaniesVariables.COMPANIES_SNOW_VALIDATION_TENANT_NAME);
		waitForPageLoaded();
		softAssert.assertTrue(status.equals(companiesPage.getTextOfCompaniesPage("serviceNowConfigurationType")), "ServiceNow Configuration Type column value present on company detail of root admin and company list page of MSP does not matched");
		LOGGER.info("Successfully verified ServiceNow Configuration Type present on company detail page get displayed on company list page for given company for MSP");
		logout();
		LOGGER.info("Successfully Logged out from MSP");
		// Validating ServiceNow Configuration Type Column for Reseller
		login("RESELLER_SNOW_EMAIL", "RESELLER_SNOW_PASSWORD");
		gotoCompaniesTab();
		LOGGER.info("Redirected to Companies page of reseller");
		waitForPageLoaded();
		resetTableConfiguration();
		if (companiesPage.waitForElementsOfCompaniesPage("removeAllSearchFilters"))
			companiesPage.clickOnElementsOfCompaniesPage("removeAllSearchFilters");
		companiesPage.verifyElementIsEnableOfCompaniesPage("nameColumn");
		companiesPage.enterTextForCompaniesPage("nameColumn", CompaniesVariables.COMPANIES_SNOW_VALIDATION_TENANT_NAME);
		waitForPageLoaded();
		softAssert.assertTrue(status.equals(companiesPage.getTextOfCompaniesPage("serviceNowConfigurationType")), "ServiceNow Configuration Type column value present on company detail of root admin and company list page of Reseller does not matched");
		LOGGER.info("Successfully verified ServiceNow Configuration Type present on company detail page get displayed on company list page for given company for Reseller");
		softAssert.assertAll();
		LOGGER.info("All test cases of verify ServiceNow Configuration Type Column of company list page are passed");
	}

	/*
	 * This test case verify valid ServiceNow Configuration URL is displayed on company list page on Root Admin , MSP and Reseller
	 */
	@Test(priority = 29, description = "250011,235166,235171,250014", groups = { "REGRESSIONCOMPANIES1", "SMOKE", "REGRESSION_CI", "SMOKE_CI" }, enabled = false)
	public final void verifyServiceNowConfigurationURLColumn() throws Exception {
		// Validating ServiceNow URL Column for Root Admin
		login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
		SoftAssert softAssert = new SoftAssert();
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();

		gotoCompaniesTab();
		LOGGER.info("Redirected to Companies page of Root Admin");
		waitForPageLoaded();
		resetTableConfiguration();
		if (companiesPage.waitForElementsOfCompaniesPage("removeAllSearchFilters"))
			companiesPage.clickOnElementsOfCompaniesPage("removeAllSearchFilters");
		companiesPage.verifyElementIsEnableOfCompaniesPage("companyEmailSearchForRoot");
		companiesPage.enterTextForCompaniesPage("companyEmailSearchForRoot", CompaniesVariables.COMPANIES_SNOW_VALIDATION_USER);
		waitForPageLoaded();
		companiesPage.waitForElementsOfCompaniesPage("removeAllSearchFilters");
		sleeper(2000);// Need sleeper to get content of the page
		softAssert.assertTrue(ConstantURL.SNOW_URL_FOR_COMPANY_LIST_VALIDATION.equals(companiesPage.getTextOfCompaniesPage("serviceNowConfigurationURL")), "Not Valid ServiceNow URL column value present on company list page of Root admin");
		LOGGER.info("Successfully verified ServiceNow URL displayed on company list page for Root Admin");
		logout();
		// Validating ServiceNow URL Column for MSP
		login("MSP_SNOW_ADMIN_EMAIL", "MSP_SNOW_ADMIN_PASSWORD");
		gotoCompaniesTab();
		LOGGER.info("Redirected to Companies page of MSP");
		waitForPageLoaded();
		resetTableConfiguration();
		if (companiesPage.waitForElementsOfCompaniesPage("removeAllSearchFilters"))
			companiesPage.clickOnElementsOfCompaniesPage("removeAllSearchFilters");
		companiesPage.verifyElementIsEnableOfCompaniesPage("nameColumn");
		companiesPage.enterTextForCompaniesPage("nameColumn", CompaniesVariables.COMPANIES_SNOW_VALIDATION_TENANT_NAME);
		waitForPageLoaded();
		companiesPage.waitForElementsOfCompaniesPage("removeAllSearchFilters");
		sleeper(2000);// Need sleeper to get content of the page
		softAssert.assertTrue(ConstantURL.SNOW_URL_FOR_COMPANY_LIST_VALIDATION.equals(companiesPage.getTextOfCompaniesPage("serviceNowConfigurationURL")), "Not Valid ServiceNow URL column value present on company list page of MSP");
		LOGGER.info("Successfully verified ServiceNow URL displayed on company list page of MSP");
		logout();
		// Validating ServiceNow URL Column for Reseller
		login("RESELLER_SNOW_EMAIL", "RESELLER_SNOW_PASSWORD");
		gotoCompaniesTab();
		LOGGER.info("Redirected to Companies page of Reseller");
		waitForPageLoaded();
		resetTableConfiguration();
		if (companiesPage.waitForElementsOfCompaniesPage("removeAllSearchFilters"))
			companiesPage.clickOnElementsOfCompaniesPage("removeAllSearchFilters");
		companiesPage.verifyElementIsEnableOfCompaniesPage("nameColumn");
		companiesPage.enterTextForCompaniesPage("nameColumn", CompaniesVariables.COMPANIES_SNOW_VALIDATION_TENANT_NAME);
		waitForPageLoaded();
		companiesPage.waitForElementsOfCompaniesPage("removeAllSearchFilters");
		sleeper(2000);// Need sleeper to get content of the page
		softAssert.assertTrue(ConstantURL.SNOW_URL_FOR_COMPANY_LIST_VALIDATION.equals(companiesPage.getTextOfCompaniesPage("serviceNowConfigurationURL")), "Not Valid ServiceNow URL column value present on company list page of Reseller");
		LOGGER.info("Successfully verified ServiceNow URL displayed on company list page of Reseller");
		softAssert.assertAll();
		LOGGER.info("All test cases of verify ServiceNow ConfigurationURL Column of company list page are passed");
	}

	/**
	 * This test case verifies company pin functionality on company details page
	 * 
	 * @throws Exception
	 */
	@Test(priority = 30, description = "Test case ID:239719",dataProvider = "loginDataForCompanySettingsChange", groups = { "REGRESSIONCOMPANIES1", "SMOKE", "REGRESSION_CI", "SMOKE_CI", "STABILIZATION_STAGING" }, enabled = true)
	public final void verifyCompanyPin(String username, String password) throws Exception {
		login(username, password);
		SoftAssert softAssert = new SoftAssert();
		CompaniesDetailsPage companyDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver()).getInstance();

		Format formatter = new SimpleDateFormat("MMMMM d, YYYY");
		Format formatterVerification = new SimpleDateFormat("MM/dd/YYYY");
		Date futureDate = DateUtils.addDays(new Date(), 1);
		String selectDate = formatter.format(futureDate);
		String verificationDate = formatterVerification.format(futureDate);
		String companyPin, expiryDate;
		if(username !="IT_ADMIN_PARTNERS_EMAIL"){
		gotoCompaniesTab();
		LOGGER.info("Redirected to companies list page");
		waitForPageLoaded();
		impersonateCompanyByCompanyName(autoCompany);
		} else {
			gotoCompanySettingsTab();
		}

		// Verify Preferences section
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("preferenceTab");
		LOGGER.info("Navigated to Preference Tab");
		companyDetailsPage.scrollOnCompaniesDetailsPage("widePinTileHeader");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("widePinTileDesc").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section_description.wide_pin")), "Comapny PIN description did not match");
		sleeper(3000); // API waiting time
		if (!companyDetailsPage.getTextOfCompaniesDetailsPage("companyPin").equals(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.not_configured"))) {
			companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("removeCompanyPIN");
			companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("removeCompanyPINPopUP");
			softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("toastNotification"), "Toast notification is not generated after expiriration of Pin");
			companyDetailsPage.waitUntilElementIsInvisibleOfCompanyDetailsPage("toastNotification");
			gotoLogTab();
			if(username =="IT_ADMIN_PARTNERS_EMAIL"){
				softAssert.assertTrue(companyDetailsPage.verifyDescriptionOnLogsPage((CompaniesVariables.COMPANY_PIN_DELETE_LOG_TEXT).replace("{company_name}", getEnvironmentSpecificData(environment, "COMPANY_NAME"))), "Description on logs page does not match after changing company PIN");
			} else
			softAssert.assertTrue(companyDetailsPage.verifyDescriptionOnLogsPage((CompaniesVariables.COMPANY_PIN_DELETE_LOG_TEXT).replace("{company_name}", autoCompany)), "Description on logs page does not match after changing company PIN");
			navigateToBack();
			waitForPageLoaded();
		}
		companyDetailsPage.waitForElementsOfCompaniesDetailsPage("editCompanyPin");
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("editCompanyPin");
		sleeper(3000);
		companyDetailsPage.waitForElementsOfCompaniesDetailsPage("calendarIcon");
		/*if(isVeneer3Check())
			companyDetailsPage.clickByJavaScriptOnCompaniesDetailsPage("calendarIcon");
		else*/
			companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("calendarIcon");
		softAssert.assertTrue(companyDetailsPage.selectDateFromCalenderForCompanyPin(selectDate, "calendarIcon", "month", "rightArrow", "totalDays"), "Date not selected successfully");
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("dialogComPinSaveButton");
		companyDetailsPage.waitUntilElementIsInvisibleOfCompanyDetailsPage("toastNotification");
		gotoLogTab();
		if(username =="IT_ADMIN_PARTNERS_EMAIL"){
			softAssert.assertTrue(companyDetailsPage.verifyDescriptionOnLogsPage((CompaniesVariables.COMPANY_PIN_CREATE_LOG_TEXT).replace("{company_name}", getEnvironmentSpecificData(environment, "COMPANY_NAME"))), "Description on logs page does not match after changing company PIN");
		} else
		softAssert.assertTrue(companyDetailsPage.verifyDescriptionOnLogsPage((CompaniesVariables.COMPANY_PIN_CREATE_LOG_TEXT).replace("{company_name}", autoCompany)), "Description on logs page does not match after changing company PIN");

		navigateToBack();
		companyDetailsPage.waitForElementsOfCompaniesDetailsPage("CompanyDetailsPageSpinner");
		companyDetailsPage.waitForElementsOfCompaniesDetailsPageForinvisibile("CompanyDetailsPageSpinner");
		companyPin = companyDetailsPage.getTextOfCompaniesDetailsPage("companyPin");
		expiryDate = companyDetailsPage.getTextOfCompaniesDetailsPage("expiryDate");
		softAssert.assertTrue(companyPin.length() == 8, "Company pin not generated successfully");
		softAssert.assertTrue(expiryDate.contains(verificationDate), "Selected date not displayed");
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("editCompanyPin");
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("calendarIcon");
		futureDate = DateUtils.addDays(new Date(), 2);
		softAssert.assertTrue(companyDetailsPage.selectDateFromCalenderForCompanyPin(formatter.format(futureDate), "calendarIcon", "month", "rightArrow", "totalDays"), "Date not selected successfully");
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("dialogComPinSaveButton");
		companyDetailsPage.waitUntilElementIsInvisibleOfCompanyDetailsPage("toastNotification");
		gotoLogTab();
		if(username =="IT_ADMIN_PARTNERS_EMAIL"){
			softAssert.assertTrue(companyDetailsPage.verifyDescriptionOnLogsPage((CompaniesVariables.COMPANY_PIN_LOG_TEXT).replace("{company_name}", getEnvironmentSpecificData(environment, "COMPANY_NAME"))), "Description on logs page does not match after changing company PIN");
		} else
		softAssert.assertTrue(companyDetailsPage.verifyDescriptionOnLogsPage((CompaniesVariables.COMPANY_PIN_LOG_TEXT).replace("{company_name}", autoCompany)), "Description on logs page does not match after changing company PIN");

		softAssert.assertAll();
		LOGGER.info("All test cases of company pin functionality passed");
	}

	/**
	 * This test case verifies company pin functionality on company details page where expiry date is set as the current date
	 * 
	 * @throws Exception
	 */
	@Test(priority = 31, description = "Test case ID:239719", groups = { "REGRESSIONCOMPANIES2", "SMOKE", "REGRESSION_CI", "SMOKE_CI" })
	public final void verifyCompanyPinToday() throws Exception {
		login("NEW_MSP_ADMIN_US_EMAIl", "NEW_MSP_ADMIN_US_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		CompaniesDetailsPage companyDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver()).getInstance();
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver());
		Format formatter = new SimpleDateFormat("MM/dd/YYYY");
		Date todayDate = new Date();
		String verificationDate = formatter.format(todayDate);
		String companyPin, expiryDate;
		gotoCompaniesTab();
		waitForPageLoaded();
		impersonateCompanyByCompanyName(impoCompany);
		// Verify Preferences section
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("preferenceTab");
		LOGGER.info("Navigated to Preference Tab");
		companyDetailsPage.scrollOnCompaniesDetailsPage("widePinTileHeader");
		softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("widePinTileDesc", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section_description.wide_pin")), "Comapny PIN description did not match");
		sleeper(3000); // API waiting time
		if (!companyDetailsPage.getTextOfCompaniesDetailsPage("companyPin").equals(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.not_configured"))) {
			companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("removeCompanyPIN");
			sleeper(1000);
			companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("removeCompanyPINPopUP");
			companyDetailsPage.waitForElementsOfCompaniesDetailsPage("toastNotification");
			softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("toastNotification"), "Toast notification is not generated after expiriration of Pin");
			companyDetailsPage.waitUntilElementIsInvisibleOfCompanyDetailsPage("toastNotification");
		}
		companyDetailsPage.waitForElementsOfCompaniesDetailsPage("editCompanyPin");
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("editCompanyPin");
		sleeper(1000);
		companyDetailsPage.waitForElementsOfCompaniesDetailsPage("calendarIcon");
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("calendarIcon");
		companiesPage.clickByJavaScriptOnCompaniesPage("selectToday");
		companyDetailsPage.waitForElementsOfCompaniesDetailsPage("dialogComPinSaveButton");
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("dialogComPinSaveButton");
		Thread.sleep(2000);
		companyDetailsPage.waitForElementsOfCompaniesDetailsPage("toastNotification");
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("toastNotification"), "Toast notification is not generated for new pin generation");
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("closeToastNotification");
//		verificationDate = formatter.format(todayDate);
		Thread.sleep(3000);
		companyPin = companyDetailsPage.getTextOfCompaniesDetailsPage("companyPin");
		Thread.sleep(3000);
		companyDetailsPage.waitForElementsOfCompaniesDetailsPage("expiryDate");
		expiryDate = companyDetailsPage.getTextOfCompaniesDetailsPage("expiryDate").split(":")[1].trim();
		softAssert.assertTrue(companyPin.length() == 8, "Company pin not generated successfully");
		softAssert.assertTrue(expiryDate.contains(verificationDate), "Selected date not displayed");
		companyDetailsPage.waitForElementsOfCompaniesDetailsPage("editCompanyPin");
		softAssert.assertAll();
		LOGGER.info("All test cases of company pin functionality for current date as expiration date passed");
	}

	/**
	 * This test case verifies domain name functionality on company details page
	 * 
	 * @throws Exception
	 */
	@Test(priority = 32, description = "NFR 242597, NFR 242598, NFR 242594",dataProvider = "loginDataForCompanySettingsChange", groups = { "REGRESSIONCOMPANIES2", "SMOKE", "REGRESSION_CI", "SMOKE_CI", "PRODUCTION_SANITY", "STABILIZATION_STAGING" },enabled=false)
	public final void verifyDomainNameFunctionalityOnDetails(String username, String password) throws Exception {
		login(username, password);
		SoftAssert softAssert = new SoftAssert();
		CompaniesDetailsPage companyDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver()).getInstance();
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();

		String domainName;
		String domainNameONList;
		if(username !="IT_ADMIN_PARTNERS_EMAIL"){
	 		gotoCompaniesTab();
	 		LOGGER.info("Redirected to companies list page");
	 		waitForPageLoaded();
	 		impersonateCompanyByCompanyName(autoCompany);
		} else {
			gotoCompanySettingsTab();
			companyDetailsPage.waitForElementsOfCompaniesDetailsPage("preferenceTab");
		}

		// Verify Preferences section
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("preferenceTab");
		LOGGER.info("Navigated to Preference Tab");
		companyDetailsPage.scrollOnCompaniesDetailsPage("domainNameTileHeader");
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("editDomainName");
		companyDetailsPage.waitForElementsOfCompaniesDetailsPage("domainNamePopupTitle");
		softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("domainNamePopupTitle", getTextLanguage(LanguageCode, "daas_ui", "companies.details.ms_telemetry.domain.label")), "domain Name Popup Title is incorrect");
		softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("domainNamePopupInstructions", getTextLanguage(LanguageCode, "daas_ui", "companies.details.domain.sub.message")), "domain Name Popup Instruction is incorrect");
		if(!companyDetailsPage.getTextOfCompaniesDetailsPage("domainNameOnDetailsPage").replaceAll("\\s+", "").equals("-")){
			companyDetailsPage.clearTextRefreshFromCompaniesDetailsPageTextField("domainNameTextBox");
			companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("saveButton");
			companyDetailsPage.waitForElementsOfCompaniesDetailsPage("toastNotification");
			gotoLogTab();
			if(username =="IT_ADMIN_PARTNERS_EMAIL"){
				softAssert.assertTrue(companyDetailsPage.verifyDescriptionOnLogsPage((CompaniesVariables.DOMAIN_NAME_DELETE_LOG).replace("{company_name}", getEnvironmentSpecificData(environment, "COMPANY_NAME"))), "Description on logs page does not match");
			} else {
				softAssert.assertTrue(companyDetailsPage.verifyDescriptionOnLogsPage((CompaniesVariables.DOMAIN_NAME_DELETE_LOG).replace("{company_name}", autoCompany)), "Description on logs page does not match");
			}
			navigateToBack();
			companyDetailsPage.waitUntilElementInvisibleOfCompanyDetailsPage("tableOverlay");
			companyDetailsPage.scrollOnCompaniesDetailsPage("domainNameTileHeader");
			companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("editDomainName");
		}
		LOGGER.info("Verified all text of pop up");

		// Verify updated domain name
		companyDetailsPage.enterTextForCompaniesDetailsPage("domainNameTextBox", CompaniesVariables.VALID_DOMAIN_NAME);
		sleeper(3000);
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("saveButton");
		companyDetailsPage.waitForElementsOfCompaniesDetailsPage("toastNotification");
		softAssert.assertTrue((companyDetailsPage.getTextOfCompaniesDetailsPage("domainNameOnDetailsPage")).replaceAll("\\s+", "").equals(CompaniesVariables.VALID_DOMAIN_NAME), "Domain name on details tile did not match with the expected");
		LOGGER.info("Domain name added successfully");

		if(username !="IT_ADMIN_PARTNERS_EMAIL"){
		gotoCompaniesTab();
		sleeper(3000);
		resetTableConfiguration();
		sleeper(3000);
		companiesPage.enterTextForCompaniesPage("companyNameSearch", autoCompany);
		companiesPage.waitForElementsOfCompaniesPage("tableOverlay");
		waitForPageLoaded();
		companiesPage.scrollTillViewCompaniesPage("domainNameColumn");
		domainName = companiesPage.getTextOfCompaniesPage("domainNameOnList");
		domainNameONList = domainName.replaceAll(",", ";");
		softAssert.assertTrue(domainNameONList.equals(CompaniesVariables.VALID_DOMAIN_NAME), "domain Name on list page of MSP does not match with domain name on detail page");
		LOGGER.info("Domain name verified successfully on MSP list page");
		gotoLogTab();
		softAssert.assertTrue(companyDetailsPage.verifyDescriptionOnLogsPage((CompaniesVariables.DOMAIN_NAME_ADD_LOG).replace("{company_name}", autoCompany)), "Description on logs page does not match");
		} else {
			gotoLogTab();
			softAssert.assertTrue(companyDetailsPage.verifyDescriptionOnLogsPage((CompaniesVariables.DOMAIN_NAME_ADD_LOG).replace("{company_name}", getEnvironmentSpecificData(environment, "COMPANY_NAME"))), "Description on logs page does not match");
		}
		logout();

		softAssert.assertAll();
		LOGGER.info("All test cases of domain name functionality for company details page passed");
	}

	/**
	 * This test case verifies account summary tile on company details page
	 * 
	 * @throws Exception
	 */
	@Test(priority = 33, description = "Test Case ID: 239730,268822,269570", groups = { "REGRESSIONCOMPANIES2", "SMOKE",
			"REGRESSION_CI", "SMOKE_CI", "STABILIZATION_STAGING" })
	public final void verifyAccountSummaryTile() throws Exception {
		login("NEW_MSP_ADMIN_US_EMAIl", "NEW_MSP_ADMIN_US_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		String companyPlan = null;
		CompaniesDetailsPage companyDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver()).getInstance();
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();

		if (toggleVerification(CompaniesVariables.SUBSCRIPTIONTOGGLE,
				getCredentials(System.getProperty("environment"), "MSP_DEVICE_LIST_EMAIL_COMPANIES"))) {
			gotoCompaniesTab();
			LOGGER.info("Redirected to companies tab under MSP view");
			impersonateCompanyByCompanyName(
					getEnvironmentSpecificData(System.getProperty("environment"), "COMPANY_ARCHIVED_DEVICE"));
			LOGGER.info("Redirected to company details page");
			companyDetailsPage.waitForElementsOfCompaniesDetailsPage("subscriptionTab");
			companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("subscriptionTab");
			LOGGER.info("Redirected to subscription tab");
			Thread.sleep(2000);
			if (!companyDetailsPage.getTextOfCompaniesDetailsPage("identityTileStatus")
					.equals(getTextLanguage(LanguageCode, "daas_ui", "companies.list.sections.suspended"))) {
					companyPlan = companyDetailsPage.getTextOfCompaniesDetailsPage("planAccountSummary");
					/*softAssert.assertTrue(
							companyDetailsPage.getTextOfCompaniesDetailsPage("planAccountSummary").contains(
									companyDetailsPage.getTextOfCompaniesDetailsPage("planNameOnIdentityTile")),
							"Active plan did not match with identity tile");*/
				
				softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("progressBar"),
						"Progress bar not present on account summary tile");
				LOGGER.info("Verified account summary tile under MSP view");

				gotoCompaniesTab();
				companiesPage.enterTextForCompaniesPage("companyNameSearch",
						getEnvironmentSpecificData(System.getProperty("environment"), "COMPANY_ARCHIVED_DEVICE"));
				companiesPage.waitUntilElementIsInvisibleOfCompanyPage("tableOverlay");
				softAssert.assertTrue(companyPlan.trim().contains(companiesPage.getTextOfCompaniesPage("subscriptionList").split("\\(")[0]),
						"Active susbscription plan on list page of MSP did not match with plan on detail page");
				LOGGER.info("Verified Active plan on list and details page");

				logout();
				// Login as root
				login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
				LOGGER.info("Redirected to companies tab under Root view");
				waitForPageLoaded();
				impersonateCompanyByCompanyName(
						getEnvironmentSpecificData(System.getProperty("environment"), "COMPANY_ARCHIVED_DEVICE"));
				LOGGER.info("Redirected to company details page");
				companyDetailsPage.waitForElementsOfCompaniesDetailsPage("subscriptionTab");
				companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("subscriptionTab");
				LOGGER.info("Redirected to subscription tab");
				companyDetailsPage.waitForElementsOfCompaniesDetailsPage("planAccountSummary");
				softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("planAccountSummary"),
						"Plan name, duration missing on account summary tile");
				softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("progressBar"),
						"Progress bar not present on account summary tile");
				LOGGER.info("Verified account summary tile under root view");
			}
			softAssert.assertAll();
		}
	}

	/**
	 * This test case verifies successful loading of company list page
	 * 
	 * @throws Exception
	 */
	@Test(priority = 34, groups = { "REGRESSIONCOMPANIES2", "SMOKE", "REGRESSION_CI", "SMOKE_CI", "PRODUCTION_CI", "PRODUCTION_SANITY","FOUNDATIONACCOUNT_PRODUCTIONCI" }, description = "Test Case ID : 280820")
	public final void verifyCompaniesListPage() throws Exception {
		login("NEW_MSP_ADMIN_US_EMAIl", "NEW_MSP_ADMIN_US_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		gotoCompaniesTab();
		LOGGER.info("Redirected to companies list page");
		waitForPageLoaded();
		companiesPage.clearFiltersOfCompaniesListPage("clearfilter");
		softAssert.assertTrue(companiesPage.verifyElementsOfCompaniesPage("listTable"), "Table on companies list page is not loaded successfully");
		softAssert.assertTrue(companiesPage.verifyElementsOfCompaniesPage("nameHeader"), "Default column is not present on companies list page");
		softAssert.assertAll();
		LOGGER.info("Companies list page is loaded successfully");
	}

	/**
	 * This test case verifies successful loading of company details page
	 * 
	 * @throws Exception
	 */
	@Test(priority = 35, groups = { "REGRESSIONCOMPANIES2", "SMOKE", "REGRESSION_CI", "SMOKE_CI", "PRODUCTION_CI", "PRODUCTION_SANITY","FOUNDATIONACCOUNT_PRODUCTIONCI" },description = "Test Case ID : 280824")
	public final void verifyCompaniesDetailsPage() throws Exception {
		login("NEW_MSP_ADMIN_US_EMAIl", "NEW_MSP_ADMIN_US_PASSWORD");

		SoftAssert softAssert = new SoftAssert();
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		CompaniesDetailsPage companyDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver()).getInstance();
		if (toggleVerification(CompaniesVariables.MIXED_PLAN_TOGGLE, "NEW_MSP_ADMIN_US_EMAIl")) {
		gotoCompaniesTab();
		LOGGER.info("Redirected to companies list page");
		waitForPageLoaded();
		companiesPage.clearFiltersOfCompaniesListPage("clearfilter");
		impersonateCompanyByCompanyName(productionCompany);
		LOGGER.info("Redirected to companies details page");
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("detailsTileHeader"), "Profile tile on companies details page is not loaded successfully");
		goToPreferencesTab();
		//sleeper(3000);
		companyDetailsPage.scrollOnCompaniesDetailsPage("widePinTileHeader");
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("widePinTileHeader"), "Company wide pin tile on companies details page is not loaded successfully");
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("dataCaptureTileHeader"), "Data capture tile on companies details page is not loaded successfully");
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("domainNameTileHeader"), "Domain name tile on companies details page is not loaded successfully");
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("incidentTileHeader"), "Incident tile on companies details page is not loaded successfully");
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("reportTileHeader"), "Reports tile on companies details page is not loaded successfully");
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("3rdPartyAppTileHeader"), "Third party software tile on companies details page is not loaded successfully");
		companyDetailsPage.scrollToTop();
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("subscriptionTab");
		//softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("accountSummaryTileHeader"), "Account summary tile on companies details page is not loaded successfully");
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("subscriptionkeyTileHeader"), "Subscription key tile on companies details page is not loaded successfully");
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("assignedMSPTab");
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("assignedMSPTab"), "Assigned MSP tile on companies details page is not loaded successfully");
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("assignedpartnerTab");
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("assignedPartnerTitle"), "Assigned partner tile on companies details page is not loaded successfully");
		softAssert.assertAll();
		LOGGER.info("Companies details page is loaded successfully");
		}
		else {
			LOGGER.info("This is not under Mixed Plan Billing Toggle");
		}
	}
		
	@Test(priority = 36, description = "TestCase ID: 249514", groups = { "REGRESSIONCOMPANIES2", "REGRESSION_CI" })
	public final void verifyPrimaryAccountPhoneNumberOnDetailsPage() throws Exception {
		login("PRIMARY_ACCOUNT_MSP_COMPANY", "PRIMARY_ACCOUNT_MSP_COMPANY_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		UserDetailsPage userDetailsPage = new UserDetailsPage(PreDefinedActions.getDriver()).getInstance();
		CompaniesDetailsPage companyDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver()).getInstance();

		dashboardPage.clickOnElementsOfDashboardPage("userProfileButton");
		dashboardPage.waitForPresenceOfElementsOfDashboardPage("signoutButton");
		dashboardPage.clickOnElementsOfDashboardPage("mspSettings");
		LOGGER.info("Redirected to edit profile page");

		waitForPageLoaded();
		userDetailsPage.waitForElementsOfUserDetailsPage("mobileEditButton");
		userDetailsPage.clickOnElementsOfUserDetailsPage("mobileEditButton");
		userDetailsPage.clearTextRefreshFromUserDetailsPageTextField("mobileTextBox");
		userDetailsPage.clickOnElementsOfUserDetailsPage("SaveButtonOnPopup");
		userDetailsPage.waitForElementsOfUserPageForinvisibile("dialogueBox");
		LOGGER.info("Set mobile number to blank");

		userDetailsPage.waitForElementsOfUserDetailsPage("officeEditButton");
		userDetailsPage.clickOnElementsOfUserDetailsPage("officeEditButton");
		userDetailsPage.clearTextRefreshFromUserDetailsPageTextField("officeTextBox");
		userDetailsPage.clickOnElementsOfUserDetailsPage("SaveButtonOnPopup");
		userDetailsPage.waitForElementsOfUserPageForinvisibile("dialogueBox");
		LOGGER.info("Set office number to blank");

		logout();

		login("STAGING_ACCOUNT_MSP_COMPANY", "STAGING_ACCOUNT_MSP_COMPANY_PASSWORD");
		gotoCompaniesTab();
		LOGGER.info("Redirected to companies");
		waitForPageLoaded();
		impersonateCompanyByCompanyName(getEnvironmentSpecificData(System.getProperty("environment"), "DEVICE_DETAILS_COMPANY"));
		softAssert.assertEquals(companyDetailsPage.getTextOfCompaniesDetailsPage("companiesBreadcrumb2"), companyDetailsPage.getTextOfCompaniesDetailsPage("companiesBreadcrumb2"), "Device Details page failed to load");
		LOGGER.info("Redirected to company details page");

		Assert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("primaryAccountPhoneNumber"), "Primary account phone number is not present on details page");
		softAssert.assertAll();

	}

	@Test(priority = 37, groups = { "REGRESSIONCOMPANIES2", "SMOKE", "REGRESSION_CI", "SMOKE_CI" }, description = "Test Case ID : 280820 ; Roles ~ MSP, Reseller", dataProvider = "loginDataListPage")
	public final void verifyCompaniesListPageData(String username, String password) throws Exception {
		login(username, password);
		String tenantID = getValueFromToken("tenant");
		gotoCompaniesTab();
		LOGGER.info("Redirected to companies list page");
		waitForPageLoaded();
		Assert.assertTrue(getStatusCode(getSearchServiceApi(environment) + CommonVariables.DETAILSSEARCHSERVICEAPI + tenantID + CommonVariables.DETAILSSEARCHSERVICEAPI2, CompaniesVariables.BODY, "POST", getEnvironment(environment)) == CommonVariables.CODEOK, "Search service failed for companies list page");
		LOGGER.info("Companies list page is loaded successfully");
	}

	@Test(priority = 38, groups = { "REGRESSIONCOMPANIES2", "SMOKE", "REGRESSION_CI", "SMOKE_CI" },dataProvider = "loginDataForCompanySettingsChange", description = "TC286333",enabled=false)
	public final void verifyMSPcompanyArchivedDevicesTile(String username, String password) throws Exception {
		login(username , password );
		SoftAssert softAssert = new SoftAssert();
		CompaniesDetailsPage companyDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver()).getInstance();

		if (username != "IT_ADMIN_PARTNERS_EMAIL") {
			gotoCompaniesTab();
			LOGGER.info("Redirected to companies list page");
			waitForPageLoaded();
			impersonateCompanyByCompanyName(autoCompany);
		} else {
			gotoCompanySettingsTab();
		}

		// Click on Preferences tab
		companyDetailsPage.waitForElementsOfCompaniesDetailsPage("preferenceTab");
		softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("preferenceTab", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.preferences").toUpperCase()), "Preferences tab title did not match");
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("preferenceTab");
		LOGGER.info("Redirected to Preferences tab");

		// Verify archived devices tile section
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("preferencesTitle"), "Preferences section is not present under company details page");
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("archivedDeviceLabel"), "Archived Device is not present under company details page");
		softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("archivedDeviceTileDesc", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section_description.archived_devices")), "Archived device tile header did not match");
		softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("archivedDeviceNotSeenWithin", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.archived_devices_not_seen")), "Archived device not seen within label did not match");
		LOGGER.info("Verified archived devices tile section.");

		// Verify edit archived device section
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("archivedDeviceEditBtn");
		companyDetailsPage.verifyElementsOfCompaniesDetailsPage("archivedDeviceEditPageLabel");
		softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("archivedDeviceNotSeenWithinLabel", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.archived_devices_not_seen")), "Archived device not seen within label inside edit did not match");
		companyDetailsPage.verifyElementsOfCompaniesDetailsPage("archivedDeviceDropdownMenu");
		sleeper(2000);// Due to inconsistent UI response we have to put wait here.(Tried many ways but this is the final solution which works as expected.)
		companyDetailsPage.selectElementFromDropDown("archivedDeviceDropdownMenu", "archivedDeviceDropdownOptionList", CompaniesVariables.ARCHIVED_DEVICE_OPTION1);
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("archivedDeviceSaveBtn");
		// Wait for toast Notification
		companyDetailsPage.waitForElementsOfCompaniesDetailsPage("toastNotification");
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("toastNotification"), "Toast notification is not generated for archived device");
		companyDetailsPage.waitUntilElementIsInvisibleOfCompanyDetailsPage("toastNotification");
		softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("archivedDeviceOptionSelected", CompaniesVariables.ARCHIVED_DEVICE_OPTION1), "Acrhived Devices select option did not match");
		LOGGER.info("Verified archived devices edit functionality.");

		// Verify edit and update archived device section
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("archivedDeviceEditBtn");
		companyDetailsPage.verifyElementsOfCompaniesDetailsPage("archivedDeviceEditPageLabel");
		softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("archivedDeviceNotSeenWithinLabel", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.archived_devices_not_seen")), "Archived device not seen within label inside edit did not match");
		companyDetailsPage.verifyElementsOfCompaniesDetailsPage("archivedDeviceDropdownMenu");
		sleeper(2000);// Due to inconsistent UI response we have to put wait here.(Tried many ways but this is the final solution which works as expected.)
		softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("archivedDeviceOptionOnDropnDown", CompaniesVariables.ARCHIVED_DEVICE_OPTION1), "Acrhived Devices selected option did not match");
		companyDetailsPage.selectElementFromDropDown("archivedDeviceDropdownMenu", "archivedDeviceDropdownOptionList", CompaniesVariables.ARCHIVED_DEVICE_OPTION2);
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("archivedDeviceSaveBtn");
		// Wait for toast Notification
		companyDetailsPage.waitForElementsOfCompaniesDetailsPage("toastNotification");
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("toastNotification"), "Toast notification is not generated for archived device");
		companyDetailsPage.waitUntilElementIsInvisibleOfCompanyDetailsPage("toastNotification");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("archivedDeviceOptionSelected").equalsIgnoreCase(CompaniesVariables.ARCHIVED_DEVICE_OPTION2), "Acrhived Devices updated option select did not match");
		LOGGER.info("Verified archived devices edit and update functionality.");
		gotoLogTab();
		softAssert.assertTrue(companyDetailsPage.verifyDescriptionOnLogsPage(CompaniesVariables.ARCHIVAL_SETTINGS_CHANGE_LOG), "Description on logs page does not match after changing device archival settings");

		navigateToBack();
		waitForPageLoaded();
		// Verify Cancel button on archived device pop-up
		companyDetailsPage.waitForElementsOfCompaniesDetailsPage("preferenceTab");
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("archivedDeviceEditBtn");
		companyDetailsPage.verifyElementsOfCompaniesDetailsPage("archivedDeviceEditPageLabel");
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("archivedDeviceCancelBtn");
		softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("archivedDeviceOptionSelected", CompaniesVariables.ARCHIVED_DEVICE_OPTION2), "Acrhived Devices cancel button value did not match");
		LOGGER.info("Verified archived devices cancel functionality.");

		softAssert.assertAll();
	}

	@Test(priority = 39, groups = { "REGRESSIONCOMPANIES2", "SMOKE", "REGRESSION_CI", "SMOKE_CI" }, enabled = false)
	public final void verifyCompanyIdRoot() throws Exception {
		login("MSP_DEVICE_LIST_EMAIL_COMPANIES", "MSP_DEVICE_LIST_PASSWORD_COMPANIES");
		SoftAssert softAssert = new SoftAssert();
		CompaniesDetailsPage companyDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver()).getInstance();

		gotoCompaniesTab();
		LOGGER.info("Redirected to Companies tab");
		waitForPageLoaded();
		impersonateCompanyByCompanyName(importCompany);

		// Verify breadcrumbs
		softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("companiesBreadcrumb1", getTextLanguage(LanguageCode, "daas_ui", "breadcrumbs.companies")), "BreadCrumb1 did not match");
		softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("companiesBreadcrumb2", getTextLanguage(LanguageCode, "daas_ui", "breadcrumbs.company_details")), "BreadCrumb2 did not match");
		LOGGER.info("Verified breadcrumb on company details page.");

		// Click on Preferences tab
		softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("preferenceTab", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.preferences").toUpperCase()), "Preferences tab title did not match");
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("preferenceTab");
		LOGGER.info("Redirected to Preferences tab");

		// Verify archived devices tile section
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("preferencesTitle"), "Preferences section is not present under company details page");
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("archivedDeviceLabel"), "Archived Device is not present under company details page");
		softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("archivedDeviceTileDesc", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section_description.archived_devices")), "Archived device tile header did not match");
		softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("archivedDeviceNotSeenWithin", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.archived_devices_not_seen")), "Archived device not seen within label did not match");
		LOGGER.info("Verified archived devices tile section.");

		// Verify edit archived device section
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("archivedDeviceEditBtn");
		companyDetailsPage.verifyElementsOfCompaniesDetailsPage("archivedDeviceEditPageLabel");
		softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("archivedDeviceNotSeenWithinLabel", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.archived_devices_not_seen")), "Archived device not seen within label inside edit did not match");
		companyDetailsPage.verifyElementsOfCompaniesDetailsPage("archivedDeviceDropdownMenu");
		sleeper(2000);// Due to inconsistent UI response we have to put wait
						// here.(Tried many ways but this is the final solution
						// which works as expected.)
		companyDetailsPage.selectElementFromDropDown("archivedDeviceDropdownMenu", "archivedDeviceDropdownOptionList", CompaniesVariables.ARCHIVED_DEVICE_OPTION1);
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("archivedDeviceSaveBtn");
		// Wait for toast Notification
		companyDetailsPage.waitForElementsOfCompaniesDetailsPage("toastNotification");
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("toastNotification"), "Toast notification is not generated for archived device");
		companyDetailsPage.waitUntilElementIsInvisibleOfCompanyDetailsPage("toastNotification");
		softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("archivedDeviceOptionSelected", CompaniesVariables.ARCHIVED_DEVICE_OPTION1), "Acrhived Devices select option did not match");
		LOGGER.info("Verified archived devices edit functionality.");

		// Verify edit and update archived device section
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("archivedDeviceEditBtn");
		companyDetailsPage.verifyElementsOfCompaniesDetailsPage("archivedDeviceEditPageLabel");
		softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("archivedDeviceNotSeenWithinLabel", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.archived_devices_not_seen")), "Archived device not seen within label inside edit did not match");
		companyDetailsPage.verifyElementsOfCompaniesDetailsPage("archivedDeviceDropdownMenu");
		sleeper(2000);// Due to inconsistent UI response we have to put wait
						// here.(Tried many ways but this is the final solution
						// which works as expected.)
		softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("archivedDeviceOptionOnDropnDown", CompaniesVariables.ARCHIVED_DEVICE_OPTION1), "Acrhived Devices selected option did not match");
		companyDetailsPage.selectElementFromDropDown("archivedDeviceDropdownMenu", "archivedDeviceDropdownOptionList", CompaniesVariables.ARCHIVED_DEVICE_OPTION2);
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("archivedDeviceSaveBtn");
		// Wait for toast Notification
		companyDetailsPage.waitForElementsOfCompaniesDetailsPage("toastNotification");
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("toastNotification"), "Toast notification is not generated for archived device");
		companyDetailsPage.waitUntilElementIsInvisibleOfCompanyDetailsPage("toastNotification");
		softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("archivedDeviceOptionSelected", CompaniesVariables.ARCHIVED_DEVICE_OPTION2), "Acrhived Devices updated option select did not match");
		LOGGER.info("Verified archived devices edit and update functionality.");

		// Verify Cancel button on archived device pop-up
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("archivedDeviceEditBtn");
		companyDetailsPage.verifyElementsOfCompaniesDetailsPage("archivedDeviceEditPageLabel");
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("archivedDeviceCancelBtn");
		softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("archivedDeviceOptionSelected", CompaniesVariables.ARCHIVED_DEVICE_OPTION2), "Acrhived Devices cancel button value did not match");
		LOGGER.info("Verified archived devices cancel functionality.");

		softAssert.assertAll();
	}

	@Test(priority = 40, groups = { "REGRESSIONCOMPANIES2", "SMOKE", "REGRESSION_CI", "SMOKE_CI", "STABILIZATION_STAGING" }, description = "Test Case:366040")

	public final void verifyPartnerDataAccessFromCompanyDeclineFunctionality() throws Exception {

		//Logged in with company
		login("IT_ADMIN_COMPANY_USER_EMAIL_COMPANIES", "IT_ADMIN_COMPANY_USER_PASSWORD_COMPANIES");
		SoftAssert softAssert = new SoftAssert();
		String expectedMailContent = null, actualMailContent = null;
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
		CompaniesDetailsPage companyDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver()).getInstance();
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		ArrayList<String> infoLabels = new ArrayList<String>(Arrays.asList(getTextLanguage(LanguageCode, "daas_ui", "partner.info.id").toLowerCase().trim(), getTextLanguage(LanguageCode, "daas_ui", "partner.info.address.street").toLowerCase().trim(), getTextLanguage(LanguageCode, "daas_ui", "partner.info.address.city").toLowerCase().trim(), getTextLanguage(LanguageCode, "daas_ui", "partner.info.address.state").toLowerCase().trim(),
				getTextLanguage(LanguageCode, "daas_ui", "partner.info.address.zip").toLowerCase().trim(), getTextLanguage(LanguageCode, "daas_ui", "partner.info.address.country").toLowerCase().trim()));
		ArrayList<String> partnerDetails = new ArrayList<String>(Arrays.asList(getEnvironmentSpecificData(System.getProperty("environment"), "PARTNER_MULTI_INVITE_ID").toLowerCase(), CompaniesVariables.PARTNER_STREET_ADDRESS, CompaniesVariables.PARTNER_CITY, CompaniesVariables.PARTNER_STATE, CompaniesVariables.PARTNER_ZIP_CODE, CompaniesVariables.PARTNER_COUNTRY));

		// Remove Pending Invitations from Company
		waitForPageLoaded();
		tenantID = getValueFromToken("tenant");
		companyID =  getIDOverviewTab(tenantID, envURL);
		gotoCompanySettingsTab();
		LOGGER.info("Redirected to Settings tab");
		waitForPageLoaded();
		companiesPage.waitForPresenceOfElementsOfCompaniesPage("assignedPartner");
		companiesPage.clickOnElementsOfCompaniesPage("assignedPartner");
		companiesPage.waitForPresenceOfElementsOfCompaniesPage("invitationLabel");
		JSONObject jsonAuthToken = (JSONObject) parser.parse(authTokenParse(companiesPage.getTokenFromLocalStorage("dui_token_e")));
		String tenantID = jsonAuthToken.get("tenant").toString();
		softAssert.assertTrue(removeAllPendingInvitations(environment, tenantID, getEnvironment(environment)), "Pending Company Invitation removal failed");
		softAssert.assertTrue(removeAllReceievedPendingInvitations(environment, tenantID, getEnvironment(environment)), "Pending Partner Invitation removal failed");
		LOGGER.info("Removed all pending invitations from company view");

		LOGGER.info("Clicked on assign partner tab");
		softAssert.assertTrue(unassignPartner(environment), "Partner unassign failed");
		softAssert.assertTrue(settingsPage.getTextOfSettingsPage("assignedPartnerTitle").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.tile.assignment_settings")), "Header on assign partner tile is incorrect");
		softAssert.assertTrue(settingsPage.getTextOfSettingsPage("unassignedPartnerField").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.label.not_assigned")), "Unassigned partner field value on assign partner tile is incorrect");
		softAssert.assertTrue(settingsPage.getTextOfSettingsPage("unassignedPartnerField").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.label.not_assigned")), "Unassigned partner field value on assign partner tile is incorrect");
		softAssert.assertTrue(settingsPage.matchTextOfSettingsPage("unassignedPartnerField", getTextLanguage(LanguageCode, "daas_ui", "companies.details.label.not_assigned")), "Unassigned partner field value on assign partner tile is incorrect");
		settingsPage.waitForElementsOfSettingsPage("editAssignedpartnerButton");
		settingsPage.clickOnElementsOfSettingsPage("editAssignedpartnerButton");
		LOGGER.info("Clicked on edit assigned partner button");
		softAssert.assertTrue(settingsPage.matchTextOfSettingsPage("assignParnterPopupHeader", getTextLanguage(LanguageCode, "daas_ui", "partner.invite.title")), "Header on invite partner popup is incorrect");
		softAssert.assertTrue(settingsPage.verifyElementsOfSettingsPage("assignPartnerInvite"), "Invite button is not present on invite partner popup");
		softAssert.assertTrue(settingsPage.verifyElementsOfSettingsPage("assignPartnerCancel"), "Cancel invite button is not present on invite partner popup");
		sleeper(3000); // time required to load partner details on popup
		settingsPage.clickOnElementsOfSettingsPage("assignPartnerDD");
		LOGGER.info("Clicked on edit assigned partner dropdown");
		settingsPage.clickOnElementsOfSettingsPage("assignPartnerSearchBox");
		settingsPage.enterTextForSettingsPage("assignPartnerSearchBox", getEnvironmentSpecificData(environment, "ASSIGNED_MULTIINVITE_PARTNER_EMAIL"));
		Thread.sleep(1000);
		settingsPage.selectFirstOptionFromDropdownOnSettingsPage("partnersList");
		LOGGER.info("Selected partner on dropdown");
		softAssert.assertTrue(settingsPage.getTextOfListOfSettingsPage("infoLabelsPartner").equals(infoLabels), "Labels under information section of partner assignment popup are incorrect");
	//	softAssert.assertTrue(settingsPage.getTextOfListOfSettingsPage("infoValuesPartner").equals(partnerDetails), "Details of current partner on partner assignment popup are incorrect");
		settingsPage.clickOnElementsOfSettingsPage("assignPartnerCancel");
		LOGGER.info("Clicked on cancel assigned partner button");
		softAssert.assertTrue(settingsPage.getTextOfSettingsPage("unassignedPartnerField").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.label.not_assigned")), "Cancel functionality on partner assignment popup is not working");
		softAssert.assertTrue(settingsPage.matchTextOfSettingsPage("unassignedPartnerField", getTextLanguage(LanguageCode, "daas_ui", "companies.details.label.not_assigned")), "Cancel functionality on partner assignment popup is not working");
		settingsPage.clickOnElementsOfSettingsPage("editAssignedpartnerButton");
		LOGGER.info("Clicked on edit assigned partner button");
		sleeper(2000); // time required to load partner details on popup
		settingsPage.clickOnElementsOfSettingsPage("assignPartnerDD");
		LOGGER.info("Clicked on edit assigned partner dropdown");
		settingsPage.waitForElementsOfSettingsPage("assignPartnerSearchBox");
		settingsPage.enterTextForSettingsPage("assignPartnerSearchBox", getEnvironmentSpecificData(environment, "ASSIGNED_MULTIINVITE_PARTNER_EMAIL"));
		waitForPageLoaded();
		settingsPage.selectFirstOptionFromDropdownOnSettingsPage("partnersList");
		LOGGER.info("Selected partner on dropdown");
		settingsPage.clickOnElementsOfSettingsPage("assignPartnerInvite");
		LOGGER.info("Clicked on save assigned partner button");
		Thread.sleep(3000);
		settingsPage.waitForElementsOfSettingsPage("toastNotification");
		softAssert.assertTrue(settingsPage.verifyElementsOfSettingsPage("toastNotification"), "Toast notification is not generated after assigned MSP is changed");
		Thread.sleep(3000);//Mail takes
		expectedMailContent = getTextLanguage(LanguageCode, "idm", "association.customer.invitation.header") +" "+ getTextLanguage(LanguageCode, "idm", "association.customer.invitation.header")+" " + getTextLanguage(LanguageCode, "lhserver", "users.reset_password.hello").replace("%{user_name}", getEnvironmentSpecificData(environment, "ASSIGNED_MULTIINVITE_PARTNER_NAME")) + " " + getTextLanguage(LanguageCode, "idm", "association.customer.invitation.message") + " "
				+ getEnvironmentSpecificData(environment, "COMPANY_INVITE_NAME") + " " + getEnvironmentSpecificData(environment, "COMPANY_COUNTRY") + " " + getTextLanguage(LanguageCode, "daas_ui", "create_company.send.invitation.label") + " " + companyID + " " + getTextLanguage(LanguageCode, "idm", "association.creation.respond.message") + " " + getTextLanguage(LanguageCode, "lhserver", "welcome.signin.submit").toUpperCase() + " "
				+ getTextLanguage(LanguageCode, "lhserver", "workspace.mail.common.thank_you") + " " + (getTextLanguage(LanguageCode, "lhserver", "global.copyright").replace("%{current_year}", getCurrentYear()));
		actualMailContent = verifyEmailContent(environment, "PARTNER_WITH_MULTIINVITE", "COMPANY_INVITE_NAME", " wants to be your customer", true);
		softAssert.assertTrue(actualMailContent.equalsIgnoreCase(expectedMailContent), "Mail content does not match");
		LOGGER.info("Email Verification to partner is done succesfully");
		logout();
		LOGGER.info("Logged out successfully");

		 //Verify decline functionality through partner
		login("RESELLER_STAGING_COMPANIES_EMAIL", "RESELLER_STAGING_COMPANIES_PASSWORD");
		waitForPageLoaded();
		gotoCompaniesTab();
		resetTableConfiguration();
		waitForPageLoaded();
		LOGGER.info("Redirected to companies tab");
		companiesPage.scrollOnCompaniesPage("partnerAssignmentDropDown");
		companiesPage.clickOnElementsOfCompaniesPage("partnerAssignmentDropDown");
		companiesPage.enterTextForCompaniesPage("searchBox", getTextLanguage(LanguageCode, "daas_ui", "partner_invite.status.awaiting_partner"));
		waitForPageLoaded();
		companiesPage.clickOnElementsOfCompaniesPage("dropDownOption");
		pressKey(Keys.ESCAPE);
		waitForPageLoaded();
		companiesPage.enterTextForCompaniesPage("companyNameSearch", getEnvironmentSpecificData(environment, "COMPANY_NAME"));
		Thread.sleep(2000);
		companiesPage.mouseHoverOnCompanyPage("companySearchResult");
		companiesPage.clickByJavaScriptOnCompaniesPage("companyCheckbox");
		softAssert.assertTrue(companiesPage.verifyElementsOfCompaniesPage("acceptCustomerButton"), "Accept button is not present on invitation");
		softAssert.assertTrue(companiesPage.verifyElementsOfCompaniesPage("declineCustomerButton"), "Decline button is not present on invitation");
		companiesPage.clickOnElementsOfCompaniesPage("declineCustomerButton");
		LOGGER.info("Clicked on save decline partner button");
		companyDetailsPage.waitForElementsOfCompaniesDetailsPage("declinePopupHeader");
		softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("declinePopupHeader", getTextLanguage(LanguageCode, "daas_ui", "partner_invite.model.decline_customer.title")), "Title on decline popup is incorrect");
		softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("declineDescription", getTextLanguage(LanguageCode, "daas_ui", "partner_invite.model.decline_customer.message").replace("{name}", inviteComapny)), "Description on decline popup is incorrect");
		softAssert.assertTrue(companiesPage.verifyElementsOfCompaniesPage("dialogResend"), "Save button is not present on decline popup");
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("cancelDeclineButton"), "Cancel button is not present on decline popup");
		companiesPage.clickOnElementsOfCompaniesPage("dialogResend");
		LOGGER.info("Clicked on save decline button");
		Thread.sleep(3000);
		companiesPage.waitForElementsOfCompaniesPage("toastNotification");
		softAssert.assertTrue(companiesPage.matchTextOfCompanyPage("toastNotification", getTextLanguage(LanguageCode, "daas_ui", "partner_invite.message.update")), "Toast notification is not generated after invite resend");
		waitForPageLoaded();
		// Verify Partner Assignment status is changed to declined after
		// clicking on Decline button
		companiesPage.clickOnElementsOfCompaniesPage("clearSearchButton");
		waitForPageLoaded();
		companiesPage.enterTextForCompaniesPage("companyNameSearch", getEnvironmentSpecificData(environment, "COMPANY_NAME"));
		Thread.sleep(1000);
		companiesPage.scrollOnCompaniesPage("partnerAssignmentStatus");
		Thread.sleep(2000);
		softAssert.assertTrue(companiesPage.getTextOfCompaniesPage("partnerAssignmentStatus").equals(getTextLanguage(LanguageCode, "daas_ui", "partner_invite.status.rejected")), "Partner assignment status did not changed to Declined after declining the invitation.");
		expectedMailContent = getTextLanguage(LanguageCode, "idm", "association.partner.decline.header") + " " + getTextLanguage(LanguageCode, "idm", "association.partner.decline.header") + " " + getTextLanguage(LanguageCode, "lhserver", "users.reset_password.hello").replace("%{user_name}", getEnvironmentSpecificData(environment, "COMPANY_INVITE_NAME")) + " " + getTextLanguage(LanguageCode, "idm", "association.partner.decline.message") + " "
				+ getEnvironmentSpecificData(environment, "ASSIGNED_MULTIINVITE_PARTNER_NAME") + " " + getEnvironmentSpecificData(environment, "ASSIGNED_MULTIINVITE_PARTNER_EMAIL") + " " + getTextLanguage(LanguageCode, "daas_ui", "partner.id") + " " + getEnvironmentSpecificData(System.getProperty("environment"), "PARTNER_MULTI_INVITE_ID") + " " + getTextLanguage(LanguageCode, "idm", "association.decline.respond.message") + " " + getTextLanguage(LanguageCode, "lhserver", "welcome.signin.submit").toUpperCase() + " "
				+ getTextLanguage(LanguageCode, "lhserver", "workspace.mail.common.thank_you") + " " + (getTextLanguage(LanguageCode, "lhserver", "global.copyright").replace("%{current_year}", getCurrentYear()));
		actualMailContent = verifyEmailContent(environment, "IT_ADMIN_PARTNERS_EMAIL", "ASSIGNED_MULTIINVITE_PARTNER_NAME", " has declined your invitation", true);
		softAssert.assertTrue(actualMailContent.equalsIgnoreCase(expectedMailContent), "Mail content does not match");
		LOGGER.info("Email Verification to it admin is done succesfully");
		softAssert.assertAll();
		LOGGER.info("All test cases of verifyPartnerDataAccessFromCompanyDeclineFunctionality passed successfully");
		LOGGER.info("Logged out successfully");

	}

	@Test(priority = 41, description = "295315,460832", groups = { "REGRESSIONCOMPANIES2", "SMOKE", "REGRESSION_CI", "SMOKE_CI" })
	public final void verifyNonEnglishNameCompany() throws Exception {
		try {
			login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
			SoftAssert softAssert = new SoftAssert();
			CompaniesDetailsPage companyDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver()).getInstance();
			CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
			waitForPageLoaded();
			companiesPage.clickOnElementsOfCompaniesPage("subscriptionColumn");
			softAssert.assertTrue(companiesPage.getTextOfListOfCompaniesPage("subscriptionPlansList").contains(getTextLanguage(LanguageCode, "idm", "onboarding.pc.service_name")),"Active care plan is not present under subscription column on companies page");

			getUrl(getEnvironment(System.getProperty("environment")) + "ui/view/companies/" + getEnvironmentSpecificData(System.getProperty("environment"), "NONENGLISHCOMPANY_TENANTID"));

			// Change company name to non-english name
			sleeper(2000);
			companyDetailsPage.waitForElementsOfCompaniesDetailsPage("editCompanyName");
			companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("editCompanyName");
			companyDetailsPage.waitForElementsOfCompaniesDetailsPage("toastNotification");
			companyDetailsPage.enterTextForCompaniesDetailsPage("nameSearchBox", CompaniesVariables.NONENGLISHCOMPANYNAME);
			companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("nameSave");
			companyDetailsPage.waitForElementsOfCompaniesDetailsPage("toastNotification");
			LOGGER.info("Company name to changed to non-english name");
			navigateToBack();
			LOGGER.info("Redirected to Companies tab");
			resetTableConfiguration();
			companiesPage.waitForElementsOfCompaniesPage("selectAllCheckBox");
			companiesPage.enterTextForCompaniesPage("companyNameSearch", CompaniesVariables.NONENGLISHCOMPANYNAME);
			sleeper(2000);
			Assert.assertTrue(companiesPage.matchTextOfCompaniesPage("companySearchResult", CompaniesVariables.NONENGLISHCOMPANYNAME), "Non-English Company is not getting reflected on company list page.");
			LOGGER.info("Verified non-english company name on Companies tab");
		} catch (Exception e) {
			LOGGER.info("Exception occured in test case verifyNonEnglishNameCompany " + e.getMessage());
		} finally {
			String api = getEnvironment(System.getProperty("environment")) + CommonVariables.COMPANYNAMEUPDATEAPI + getEnvironmentSpecificData(System.getProperty("environment"), "NONENGLISHCOMPANY_TENANTID");
			Assert.assertEquals(getStatusCode(api, "{\"displayName\":" + "\"" + getEnvironmentSpecificData(System.getProperty("environment"), "NONENGLISHCOMPANY") + "\"" + "}", "PATCH", System.getProperty("environment")), 204, "Company name update API failed");
		}
	}

	@Test(priority = 42, groups = { "REGRESSIONCOMPANIES2", "SMOKE", "REGRESSION_CI", "SMOKE_CI", "PRODUCTION_SANITY" }, enabled = false)
	public final void verifyCompanyResendInviteToPartner() throws Exception {
		login("IT_ADMIN_PARTNERS_EMAIL", "IT_ADMIN_PARTNERS_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();

		// Remove Pending Invitations from Company
		waitForPageLoaded();
		gotoCompanySettingsTab();
		LOGGER.info("Redirected to Settings tab");
		waitForPageLoaded();
		companiesPage.waitForPresenceOfElementsOfCompaniesPage("assignedPartner");
		companiesPage.clickOnElementsOfCompaniesPage("assignedPartner");
		companiesPage.waitForPresenceOfElementsOfCompaniesPage("invitationLabel");
		JSONObject jsonAuthToken = (JSONObject) parser.parse(authTokenParse(companiesPage.getTokenFromLocalStorage("dui_token_e")));
		String tenantID = jsonAuthToken.get("tenant").toString();
		softAssert.assertTrue(removeAllPendingInvitations(environment, tenantID, getEnvironment(environment)), "Pending Company Invitation removal failed");
		softAssert.assertTrue(removeAllReceievedPendingInvitations(environment, tenantID, getEnvironment(environment)), "Pending Partner Invitation removal failed");
		softAssert.assertTrue(unassignPartner(environment), "Partner unassign failed");
		settingsPage.waitForElementsOfSettingsPage("editAssignedpartnerButton");
		settingsPage.clickOnElementsOfSettingsPage("editAssignedpartnerButton");
		LOGGER.info("Clicked on edit assigned partner button");
		settingsPage.clickOnElementsOfSettingsPage("assignPartnerDD");
		LOGGER.info("Clicked on edit assigned partner dropdown");
		settingsPage.clickOnElementsOfSettingsPage("assignPartnerSearchBox");
		settingsPage.enterTextForSettingsPage("assignPartnerSearchBox", getEnvironmentSpecificData(environment, "ASSIGNED_PARTNER_NAME"));
		settingsPage.selectFirstOptionFromDropdownOnSettingsPage("partnersList");
		LOGGER.info("Selected partner on dropdown");
		settingsPage.clickOnElementsOfSettingsPage("assignPartnerInvite");
		LOGGER.info("Clicked on save assigned partner button");
		settingsPage.waitForElementsOfSettingsPage("toastNotification");
		softAssert.assertTrue(settingsPage.verifyElementsOfSettingsPage("toastNotification"), "Toast notification is not generated after sending partner invitation");
		settingsPage.waitForElementsOfSettingsPage("tableOverlay");
		settingsPage.clickOnElementsOfSettingsPage("resendButton");
		settingsPage.clickByJavaScriptOnSettingsPage("dialogResend");
		settingsPage.waitForElementsOfSettingsPage("toastNotification");
		softAssert.assertTrue(settingsPage.matchTextOfSettingsPage("toastNotification", getTextLanguage(LanguageCode, "daas_ui", "partner_invite.message.sent")), "Toast notification is not generated after invite resend");
		softAssert.assertAll();
		LOGGER.info("verify Company Resend Invite To Partner completed");
	}

	// This test case verifies assigned MSP tile on company details page
	@Test(priority = 43, groups = { "REGRESSIONCOMPANIES2", "SMOKE", "REGRESSION_CI", "SMOKE_CI" })
	public final void verifyAssignedMSPTile() throws Exception {
		login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
		String currentMSP = null;
		CompaniesDetailsPage companyDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		ArrayList<String> infoLabels = new ArrayList<String>(Arrays.asList(getTextLanguage(LanguageCode, "daas_ui", "create_company.msp.msp_id").toLowerCase().trim(), getTextLanguage(LanguageCode, "daas_ui", "partner.info.address.street").toLowerCase().trim(), getTextLanguage(LanguageCode, "daas_ui", "partner.info.address.city").toLowerCase().trim(), getTextLanguage(LanguageCode, "daas_ui", "partner.info.address.state").toLowerCase().trim(),
				getTextLanguage(LanguageCode, "daas_ui", "partner.info.address.zip").toLowerCase().trim(), getTextLanguage(LanguageCode, "daas_ui", "partner.info.address.country").toLowerCase().trim()));
		Format formatter = new SimpleDateFormat("MM/dd/yyyy");
		Date date = new Date();
		String assignedOnDate = formatter.format(date);

		waitForPageLoaded();
		tenantID = getValueFromToken("tenant");
		apiurl = getEnvironmentSpecificData(System.getProperty("environment"), "DAAS_API_BASE_URL_AUTH") + CommonVariables.DETAILSSEARCHSERVICEAPI + tenantID + CommonVariables.DETAILSSEARCHSERVICEAPI2;
		tenantIdCompany = getTenantDetails(apiurl, CommonVariables.SEARCH_SERVICE_IDMTENANTSMSP, getEnvironmentSpecificData(environment, "ASSIGNED_MSP_ONE_NAME"));
		MSPID = getIDOverviewTab(tenantIdCompany, envURL);
		ArrayList<String> mspOneDetails = new ArrayList<String>(Arrays.asList(MSPID.toLowerCase(), CompaniesVariables.MSP_ONE_STREET_ADDRESS, CompaniesVariables.MSP_ONE_CITY, CompaniesVariables.MSP_ONE_STATE, CompaniesVariables.MSP_ONE_ZIP_CODE, CompaniesVariables.MSP_ONE_COUNTRY));
		impersonateCompanyByCompanyName(inviteComapny);
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("companydetailstopsection");
		sleeper(2000);
		companyDetailsPage.waitForElementsOfCompaniesDetailsPage("assignedMSPTab");
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("assignedMSPTab");
		LOGGER.info("Clicked on assigned MSP tab");
		currentMSP = companyDetailsPage.getTextOfCompaniesDetailsPage("currentMSP");
		sleeper(4000);
		companyDetailsPage.waitForElementsOfCompaniesDetailsPage("editAssignedMSPButton");
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("editAssignedMSPButton");
		LOGGER.info("Clicked on edit assigned MSP button");
		
		softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("mspPopupTitle", getTextLanguage(LanguageCode, "daas_ui", "roles.MSP")), "Title on assignment msp popup is incorrect");
		if (companyDetailsPage.verifyElementIsClickableOfCompaniesDetailsPage("assignedMSPDropdown")) {
			softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("mspOnDropdown", currentMSP), "MSP on dropdown is incorrect");
			softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("saveAssignedMSP"), "Save button is not present on msp assignment popup");
			softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("cancelAssignedMSP"), "Cancel button is not present on msp assignment popup");
			companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("assignedMSPDropdown");
			LOGGER.info("Clicked on assigned MSP dropdown");
			companyDetailsPage.waitForElementsOfCompaniesDetailsPage("mspDropdownSearch");
			companyDetailsPage.enterTextForCompaniesDetailsPage("mspDropdownSearch", getEnvironmentSpecificData(environment, "ASSIGNED_MSP_NAME"));
			waitForPageLoaded();
			companyDetailsPage.selectFirstOptionFromDropdown("assignedMSPList");
			LOGGER.info("Selected MSP on dropdown");
			companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("cancelAssignedMSP");
			LOGGER.info("Clicked on cancel button on assigned MSP popup");
			softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("currentMSP", currentMSP), "Cancel functionality on MSP assignment popup is not working");

			companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("editAssignedMSPButton");
			LOGGER.info("Clicked on edit assigned MSP button");
			companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("assignedMSPDropdown");
			companyDetailsPage.waitForElementsOfCompaniesDetailsPage("mspDropdownSearch");
			companyDetailsPage.enterTextForCompaniesDetailsPage("mspDropdownSearch", getEnvironmentSpecificData(environment, "ASSIGNED_MSP_NAME"));
			waitForPageLoaded();
			companyDetailsPage.selectFirstOptionFromDropdown("assignedMSPList");
			LOGGER.info("Selected MSP on dropdown");
			softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("informationLabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "global.information")), "Information label on assigned MSP popup is incorrect");
			companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("saveAssignedMSP");
			LOGGER.info("Clicked on save button on assigned MSP popup");
			Thread.sleep(3000);
			companyDetailsPage.waitForElementsOfCompaniesDetailsPage("toastNotification");
			softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("toastNotification"), "Toast notification is not generated after assigned MSP is changed");
			softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("assignedOnField", getTextLanguage(LanguageCode, "daas_ui", "msp.assignedOn").replace("{date}", assignedOnDate)), "Assigned On field on assignment MSP tile of company details page is incorrect");

			//Updated different value again
			companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("editAssignedMSPButton");
			LOGGER.info("Clicked on edit assigned MSP button");
			companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("assignedMSPDropdown");
			companyDetailsPage.waitForElementsOfCompaniesDetailsPage("mspDropdownSearch");
			companyDetailsPage.enterTextForCompaniesDetailsPage("mspDropdownSearch", getEnvironmentSpecificData(environment, "ASSIGNED_SECOND_MSP_NAME"));
			waitForPageLoaded();
			companyDetailsPage.selectFirstOptionFromDropdown("assignedMSPList");
			LOGGER.info("Selected MSP on dropdown");
			companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("saveAssignedMSP");
			LOGGER.info("Clicked on save button on assigned MSP popup");
			companyDetailsPage.waitForElementsOfCompaniesDetailsPage("toastNotification");

		} else {
			Assert.fail("Dropdown on partner assignment popup is not clickable");
		}
		softAssert.assertAll();
		LOGGER.info("All test cases for assigned MSP tile have passed successfully");
	}

	// This test case verifies assigned partner tile on company details page
	@Test(priority = 44, groups = { "REGRESSIONCOMPANIES2", "SMOKE", "REGRESSION_CI", "SMOKE_CI", "PRODUCTION_SANITY", "STABILIZATION_STAGING" })
	public final void verifyAssignedPartnerTileApprove() throws Exception {
		login("ROOT_ADMIN_NEW_USER_COMPANIES", "ROOT_ADMIN_NEW_PASSWORD_COMPANIES");
		SoftAssert softAssert = new SoftAssert();
		CompaniesDetailsPage companyDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver()).getInstance();
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		ArrayList<String> infoLabels = new ArrayList<String>(Arrays.asList(getTextLanguage(LanguageCode, "daas_ui", "partner.info.id").toLowerCase().trim(), getTextLanguage(LanguageCode, "daas_ui", "partner.info.address.street").toLowerCase().trim(), getTextLanguage(LanguageCode, "daas_ui", "partner.info.address.city").toLowerCase().trim(), getTextLanguage(LanguageCode, "daas_ui", "partner.info.address.state").toLowerCase().trim(),
				getTextLanguage(LanguageCode, "daas_ui", "partner.info.address.zip").toLowerCase().trim(), getTextLanguage(LanguageCode, "daas_ui", "partner.info.address.country").toLowerCase().trim()));
		ArrayList<String> partnerDetails = new ArrayList<String>(Arrays.asList(getEnvironmentSpecificData(System.getProperty("environment"), "PARTNER_ID").toLowerCase(), CompaniesVariables.PARTNER_STREET_ADDRESS, CompaniesVariables.PARTNER_CITY, CompaniesVariables.PARTNER_STATE, CompaniesVariables.PARTNER_ZIP_CODE, CompaniesVariables.PARTNER_COUNTRY));

		waitForPageLoaded();

		impersonateCompanyByCompanyName(inviteComapny);
		String tenantID = getUrlOfCurrentPage().substring(getUrlOfCurrentPage().length() - 36);
		companiesPage.waitForPresenceOfElementsOfCompaniesPage("assignedPartner");
		LOGGER.info("Clicked on assigned partner tab");
		companiesPage.clickOnElementsOfCompaniesPage("assignedPartner");
		companiesPage.waitForPresenceOfElementsOfCompaniesPage("invitationLabel");
		// Remove Pending Invitation
		softAssert.assertTrue(removeAllReceievedPendingInvitations(environment, tenantID, getEnvironment(environment)), "Pending Partner Invitation removal failed");
		softAssert.assertTrue(removeAllPendingInvitations(environment, tenantID, getEnvironment(environment)), "Pending root admin Invitation removal failed");
		softAssert.assertTrue(unassignPartner(environment), "Partner unassign failed");
		LOGGER.info("Removed all pending invitations from root admin view");

		if (companyDetailsPage.getTextOfCompaniesDetailsPage("unassignedPartnerField").equals(getTextLanguage(LanguageCode, "daas_ui", "companies.details.label.not_assigned"))) {
			companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("editAssignedpartnerButton");
			LOGGER.info("Clicked on edit assigned partner button");
			companyDetailsPage.waitForElementsOfCompaniesDetailsPage("partnerPopupTitle");
			softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("partnerPopupTitle", getTextLanguage(LanguageCode, "daas_ui", "partner.invite.title")), "Title on partner assignment popup is incorrect");
			softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("saveAssignedPartner"), "Save button is not present on partner assignment popup");
			softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("cancelAssignedPartner"), "Cancel button is not present on partner assignment popup");
			sleeper(2000); // time required to load MSP details on dropdown
			companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("assignedPartnerDropdown");
			LOGGER.info("Clicked on edit assigned partner dropdown");
			companyDetailsPage.waitForElementsOfCompaniesDetailsPage("assignedPartnerSearchBox");
			companyDetailsPage.enterTextForCompaniesDetailsPage("assignedPartnerSearchBox", getEnvironmentSpecificData(environment, "ASSIGNED_PARTNER_NAME_EU"));
			waitForPageLoaded();
			companyDetailsPage.selectFirstOptionFromDropdown("assignedPartnerList");
			LOGGER.info("Selected partner on dropdown");
			softAssert.assertTrue(companyDetailsPage.getTextOfListOfCompanyDetailsPage("infoLabelsPartner").equals(infoLabels), "Labels under information section of partner assignment popup are incorrect");
			//softAssert.assertTrue(companyDetailsPage.getTextOfListOfCompanyDetailsPage("infoValuesPartner").equals(partnerDetails), "Details of current partner on partner assignment popup are incorrect");
			companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("cancelAssignedPartner");
			LOGGER.info("Clicked on cancel assigned partner button");
			softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("unassignedPartnerField").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.label.not_assigned")), "Cancel functionality on MSP assignment popup is not working");
			softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("unassignedPartnerField", getTextLanguage(LanguageCode, "daas_ui", "companies.details.label.not_assigned")), "Cancel functionality on MSP assignment popup is not working");
			companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("editAssignedpartnerButton");
			LOGGER.info("Clicked on edit assigned partner button");
			sleeper(2000); // time required to load MSP details on dropdown
			companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("assignedPartnerDropdown");
			LOGGER.info("Clicked on edit assigned partner dropdown");
			companyDetailsPage.waitForElementsOfCompaniesDetailsPage("assignedPartnerSearchBox");
			companyDetailsPage.enterTextForCompaniesDetailsPage("assignedPartnerSearchBox", getEnvironmentSpecificData(environment, "ASSIGNED_PARTNER_NAME"));
			waitForPageLoaded();
			companyDetailsPage.selectFirstOptionFromDropdown("assignedPartnerList");
			LOGGER.info("Selected partner on dropdown");
			companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("saveAssignedPartner");
			LOGGER.info("Clicked on save assigned partner button");
			companyDetailsPage.waitForElementsOfCompaniesDetailsPage("toastNotification");
			softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("toastNotification"), "Toast notification is not generated after assigned MSP is changed");
			logout();
			LOGGER.info("Logged out successfully");

			login("IT_ADMIN_PARTNERS_EMAIL", "IT_ADMIN_PARTNERS_PASSWORD");
			LOGGER.info("Logging in to it admin successfull");
			companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("notificationBellIcon");
			companyDetailsPage.waitForElementsOfCompaniesDetailsPage("tableOverlay"); //Need to put wait here since notification is taking time to appear
			sleeper(2000); // time required to load notification
			LOGGER.info("Clicked on notification bell icon");
				companiesPage.mouseHoverOnCompanyPage("firstNotification");
				companiesPage.clickOnElementsOfCompaniesPage("hamburgerMenuOnNotification");
			companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("invitationNotificationApprove");
			LOGGER.info("Clicked on link for partner approval");
			softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("approveHeader", getTextLanguage(LanguageCode, "daas_ui", "partner_invite.accept.label")), "Header on approve partner invitation popup is incorrect");
			softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("approveDescription", getTextLanguage(LanguageCode, "daas_ui", "partner_invite.accept.message").replace("{partner_name}", getEnvironmentSpecificData(environment, "ASSIGNED_PARTNER_NAME"))), "Description on approve partner invitation popup is incorrect");
			softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("approveWarning", getTextLanguage(LanguageCode, "daas_ui", "partner_invite.accept.message2")), "Warning message on approve partner invitation popup is incorrect");
			softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("approvePartnerButton"), "Approve button is not present on approve partner popup");
			softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("cancelApprovalButton"), "Cancel button is not present on approve partner popup");
			companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("approvePartnerButton");
			companyDetailsPage.waitForElementsOfCompaniesDetailsPage("toastNotification");
			LOGGER.info("Clicked on approve assigned partner button");
			logout();
			LOGGER.info("Logged out successfully");

			login("ROOT_ADMIN_NEW_USER_COMPANIES", "ROOT_ADMIN_NEW_PASSWORD_COMPANIES");
			LOGGER.info("Logging in to root admin successfull");
			waitForPageLoaded();
			impersonateCompanyByCompanyName(inviteComapny);
			companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("assignedpartnerTab");
			LOGGER.info("Clicked on assigned partner tab");
			softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("currentPartner", getEnvironmentSpecificData(environment, "ASSIGNED_PARTNER_NAME")), "Partner is not reflected after accepting invitation from the company");
			companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("editAssignedpartnerButton");
			LOGGER.info("Clicked on edit assigned partner button");
			softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("unassignPartnerHeader", getTextLanguage(LanguageCode, "daas_ui", "company.unassign.partner")), "Header on unassign partner popup is incorrect");
			softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("unassignDescription", getTextLanguage(LanguageCode, "daas_ui", "company.unassign.partner.desc").replace("{partnerName}", getEnvironmentSpecificData(environment, "ASSIGNED_PARTNER_NAME"))), "Description on unassigned partner popup is incorrect");
			companyDetailsPage.waitForElementsOfCompaniesDetailsPage("unassignedButton");
			companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("unassignedButton");
			LOGGER.info("Clicked on unassign partner button");
			companyDetailsPage.waitForElementsOfCompaniesDetailsPage("toastNotification");
			softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("toastNotification"), "Toast notification is not generated after removing assigned partner");
			companiesPage.waitForElementsOfCompaniesPage("tableOverlay");
			softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("unassignedPartnerField").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.label.not_assigned")), "Partner is not reflected after removing it from the company");
			softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("unassignedPartnerField", getTextLanguage(LanguageCode, "daas_ui", "companies.details.label.not_assigned")), "Partner is not reflected after removing it from the company");
			LOGGER.info("Removed the assigned partner");

			softAssert.assertAll();
			LOGGER.info("All test cases for approve functionality on assigned partner tile have passed successfully");
		}
	}

	@Test(priority = 45, groups = { "REGRESSIONCOMPANIES2", "SMOKE", "REGRESSION_CI", "SMOKE_CI" }, description = "TEST CASE 296671")
	public final void verifyLimitedViewOfCompanyDetailsPage() throws Exception {
		login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		String apiurl, customerID, tenantIdCompany1 = null, tenantIdCompany2 = null,compName1,compName2 = null;
		try {
			SoftAssert softAssert = new SoftAssert();
			compName1 = "AutoTest" + generateRandomString(7);
			CompaniesDetailsPage companyDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver()).getInstance();
			DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();

			// Root
			Assert.assertTrue(companiesPage.createCompanyUsingAPI(compName1, getEnvironmentSpecificData(System.getProperty("environment"), "COUNTRY_CODE"), LanguageCode, getEnvironmentSpecificData(System.getProperty("environment"), "MSP_ID1"), "", "", CompaniesVariables.COMPANY_STANDARDTRIAL), "Company Creation failed");
			LOGGER.info(compName1 + " company created");
			tenantID = getValueFromToken("tenant");
			refreshPage();
			waitForPageLoaded();
			apiurl = cataLystURL + CommonVariables.DETAILSSEARCHSERVICEAPI + tenantID + CommonVariables.DETAILSSEARCHSERVICEAPI2;
			tenantIdCompany1 = getTenantDetails(apiurl, CommonVariables.SEARCH_SERVICE_IDMTENANTS, compName1);
			customerID = getIDOverviewTab(tenantIdCompany1, envURL);
			logout();

			login("RESELLER_NEW_UI_EMAIL_US", "RESELLER_NEW_UI_PASSWORD_US");
			gotoCompaniesTab();
			LOGGER.info("Redirected to companies tab");

			// Remove Pending invitation
			waitForPageLoaded();
			resetTableConfiguration();
			companiesPage.clickOnElementsOfCompaniesPage("partnerAssignmentDropDown");
			companiesPage.enterTextForCompaniesPage("searchBox", getTextLanguage(LanguageCode, "daas_ui", "partner_invite.status.awaiting_partner"));
			sleeper(2000);
			companiesPage.clickOnElementsOfCompaniesPage("dropDownOption");
			pressKey(Keys.ESCAPE);
			deviceListPage.waitUntilElementIsInvisibleOfDeviceListPage("SpinnerList");
			waitForPageLoaded();

			if (companiesPage.verifyElementsOfCompaniesPage("companySearchResult")) {
				waitForPageLoaded();
				List<WebElement> companiesList = companiesPage.getElementsOfCompanyListPage("companyCheckbox");
				List<WebElement> companyCheckboxList = companiesPage.getElementsOfCompanyListPage("companySearchResult");
				companiesPage.clickElementsOfCompaniesPage(companiesList, companyCheckboxList);
				companiesPage.clickOnElementsOfCompaniesPage("declineCustomerButton");
				companiesPage.clickByJavaScriptOnCompaniesPage("dialogResend");
				companiesPage.waitForElementsOfCompaniesPage("toastNotification");
				softAssert.assertTrue(companiesPage.matchTextOfCompanyPage("toastNotification", getTextLanguage(LanguageCode, "daas_ui", "partner_invite.message.update")), "Toast notification is not generated after invite resend");
				LOGGER.info("Removed all pending invitations from company");
			} else {
				LOGGER.info("There are no pending invitations present from company");
			}

			companiesPage.waitForElementsOfCompaniesPage("clearFilter");
			companiesPage.clickOnElementsOfCompaniesPage("clearFilter");
			companiesPage.clickOnElementsOfCompaniesPage("partnerAssignmentDropDown");
			companiesPage.enterTextForCompaniesPage("searchBox", getTextLanguage(LanguageCode, "daas_ui", "partner_invite.status.awaiting_customer"));
			sleeper(2000);
			companiesPage.clickOnElementsOfCompaniesPage("dropDownOption");
			pressKey(Keys.ESCAPE);
			deviceListPage.waitUntilElementIsInvisibleOfDeviceListPage("SpinnerList");
			waitForPageLoaded();
			if (companiesPage.verifyElementsOfCompaniesPage("companySearchResult")) {
				waitForPageLoaded();
				List<WebElement> companiesList = companiesPage.getElementsOfCompanyListPage("companyCheckbox");
				List<WebElement> companyCheckboxList = companiesPage.getElementsOfCompanyListPage("companySearchResult");
				companiesPage.clickElementsOfCompaniesPage(companiesList, companyCheckboxList);
				companiesPage.clickOnElementsOfCompaniesPage("recallInvitationButton");
				companiesPage.clickByJavaScriptOnCompaniesPage("dialogResend");
				companiesPage.waitForElementsOfCompaniesPage("toastNotification");
				softAssert.assertTrue(companiesPage.matchTextOfCompanyPage("toastNotification", getTextLanguage(LanguageCode, "daas_ui", "partner_invite.message.update")), "Toast notification is not generated after invite resend");
				LOGGER.info("Removed all pending invitations from partner");
			} else {
				LOGGER.info("There are no pending invitations present from partner");
			}
			companiesPage.waitForElementsOfCompaniesPage("clearFilter");
			companiesPage.clickOnElementsOfCompaniesPage("clearFilter");
			companiesPage.clickOnElementsOfCompaniesPage("addButton");
			LOGGER.info("Clicked on add button");
			companiesPage.clickOnElementsOfCompaniesPage("companyInvite");
			LOGGER.info("Clicked on invite button");
			companiesPage.clickOnElementsOfCompaniesPage("addCompanyDialogPopupNextButton");
			LOGGER.info("Clicked on next button");
			companiesPage.enterTextForCompaniesPage("customerID", customerID);
			sleeper(3000);
			LOGGER.info("Entered the customer id");
			companiesPage.clickOnElementsOfCompaniesPage("companyInviteBtn");
			LOGGER.info("Clicked on invite button");
			companiesPage.waitForElementsOfCompaniesPage("tableOverlay");
			companiesPage.clickOnElementsOfCompaniesPage("partnerAssignmentDropDown");
			companiesPage.enterTextForCompaniesPage("searchBox", getTextLanguage(LanguageCode, "daas_ui", "partner_invite.status.awaiting_customer"));
			sleeper(2000);
			companiesPage.clickOnElementsOfCompaniesPage("dropDownOption");
			pressKey(Keys.ESCAPE);
			deviceListPage.waitUntilElementIsInvisibleOfDeviceListPage("SpinnerList");
			waitForPageLoaded();
			companiesPage.enterTextForCompaniesPage("companyNameSearch",compName1 );
			sleeper(2000);
			companiesPage.clickByJavaScriptOnCompaniesPage("companySearchResult");
			companyDetailsPage.waitForElementsOfCompaniesDetailsPage("CompanyDetailsPageSpinner");
			companyDetailsPage.waitForElementsOfCompaniesDetailsPageForinvisibile("CompanyDetailsPageSpinner");
			softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("companyInfoTitle"), "Overview tile is not present on company details page after partner invite");
			softAssert.assertFalse(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("commentsTab"), "Comments tab is present on company details page even if partner invitation is not accepted by company.");

			softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("companyId").equalsIgnoreCase(customerID), "Customer id on company details page is incorrect");
			softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("companyName").equalsIgnoreCase(compName1), "Customer name on company details page is incorrect");
			softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("companyCountry", getEnvironmentSpecificData(environment, "COUNTRY_NAME_ADD")), "Customer country name on company details page is incorrect");
			softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("companyPrimaryAdministrator", CompaniesVariables.COMPANY_ADMIN_INVITE), "Customer primary administrator on company details page is incorrect");
			gotoCompaniesTab();
			sleeper(2000);
			LOGGER.info("Redirected to company list page");

			compName2 = "AutoTest" + generateRandomString(7);
			String compEmail2 = "AutoTest" + generateRandomString(7)+ "@yopmail.com";
			Assert.assertTrue(companiesPage.createCompanyUsingAPI(compName2, getEnvironmentSpecificData(System.getProperty("environment"), "COUNTRY_CODE"), LanguageCode, getEnvironmentSpecificData(System.getProperty("environment"), "MSP_ID1"), getEnvironmentSpecificData(System.getProperty("environment"), "PARTNER_ID1"), compEmail2, CompaniesVariables.COMPANY_STANDARDTRIAL), "Company Creation failed");

			impersonateCompanyByCompanyName(compName2);

			LOGGER.info("Redirected to company details page");
			softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("companyInfoTitle"), "Overview tile is not present on company details page after partner invite");
			softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("subscriptionTitle"), "Subscription tile is not present on company details page after creating new company");
			softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("companyName").equalsIgnoreCase(compName2), "Customer name on company details page is incorrect");
			softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("companyCountry", getEnvironmentSpecificData(environment, "COUNTRY_NAME_ADD")), "Customer country name on company details page is incorrect");
			softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("companyPrimaryAdministrator", CompaniesVariables.COMPANY_ADMIN_INVITE), "Customer name on company details page is incorrect");
			softAssert.assertAll();
			LOGGER.info("All test cases for limited view verification have passed");
		}

		finally {
			// Remove created company
			logout();
			login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
			companiesPage.removeCompanyUsingAPI(tenantIdCompany1);
			impersonateCompanyByCompanyName(compName2);
			tenantIdCompany2 = getUrlOfCurrentPage().substring(getUrlOfCurrentPage().length() - 36);//Teanant id of company taken from getTenantDetails() method is different when taken from Partner login and Root login for same company.(Tried many ways but this is the final solution which works as expected.)
			companiesPage.removeCompanyUsingAPI(tenantIdCompany2);
		}
	}

	@Test(priority = 46, groups = { "REGRESSIONCOMPANIES2", "SMOKE", "REGRESSION_CI", "SMOKE_CI" })
	public final void verifyResendInviteByRootUser() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		// Remove Pending Invitations from Company
		login("ITA_COMPANY_DETAILS_EMAIL", "ITA_COMPANY_DETAILS_PASSWORD");
		CompaniesDetailsPage companyDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver()).getInstance();
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();

		gotoCompanySettingsTab();
		LOGGER.info("Redirected to Settings tab");
		waitForPageLoaded();
		companiesPage.waitForPresenceOfElementsOfCompaniesPage("assignedPartner");
		companiesPage.clickOnElementsOfCompaniesPage("assignedPartner");
		companiesPage.waitForPresenceOfElementsOfCompaniesPage("invitationLabel");
		JSONObject jsonAuthToken = (JSONObject) parser.parse(authTokenParse(companiesPage.getTokenFromLocalStorage("dui_token_e")));
		String tenantID = jsonAuthToken.get("tenant").toString();
		//Remove pending invitations from company
		softAssert.assertTrue(removeAllPendingInvitations(environment, tenantID, getEnvironment(environment)), "Pending Company Invitation removal failed");

		//Remove receieved pending invitations from partner
		softAssert.assertTrue(removeAllReceievedPendingInvitations(environment, tenantID, getEnvironment(environment)), "Pending Partner Invitation removal failed");
		LOGGER.info("Removed all pending invitations from company view");
		softAssert.assertTrue(unassignPartner(environment), "Partner unassign failed");
		logout();

		// Remove Pending Invitations from Root Admin
		login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
		waitForPageLoaded();
		// Remove Pending Invitation
		impersonateCompanyByCompanyName(inviteComapny);
		sleeper(2000);
		companiesPage.waitForPresenceOfElementsOfCompaniesPage("assignedPartner");
		LOGGER.info("Clicked on assigned partner tab");
		companiesPage.clickOnElementsOfCompaniesPage("assignedPartner");
		companiesPage.waitForPresenceOfElementsOfCompaniesPage("invitationLabel");
		jsonAuthToken = (JSONObject) parser.parse(authTokenParse(companiesPage.getTokenFromLocalStorage("dui_token_e")));
		tenantID = jsonAuthToken.get("tenant").toString();

		if (companyDetailsPage.getTextOfCompaniesDetailsPage("unassignedPartnerField").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.label.not_assigned"))) {
			if (companyDetailsPage.getTextOfCompaniesDetailsPage("unassignedPartnerField").equals(getTextLanguage(LanguageCode, "daas_ui", "companies.details.label.not_assigned"))) {
				sleeper(2000);
				companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("editAssignedpartnerButton");
				sleeper(2000);
				LOGGER.info("Clicked on edit assigned partner button");
				companyDetailsPage.waitForElementsOfCompaniesDetailsPage("assignedPartnerDropdown");
				sleeper(3000);
				companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("assignedPartnerDropdown");
				companyDetailsPage.waitForElementsOfCompaniesDetailsPage("assignedPartnerSearchBox");
				LOGGER.info("Clicked on edit assigned partner dropdown");
				companyDetailsPage.enterTextForCompaniesDetailsPage("assignedPartnerSearchBox", getEnvironmentSpecificData(environment, "ASSIGNED_PARTNER_NAME"));
				waitForPageLoaded();
				companyDetailsPage.selectFirstOptionFromDropdown("assignedPartnerList");
				LOGGER.info("Selected partner on dropdown");
				companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("saveAssignedPartner");
				LOGGER.info("Clicked on save assigned partner button");
				companyDetailsPage.waitForElementsOfCompaniesDetailsPage("toastNotification");
				softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("toastNotification"), "Toast notification is not generated after assigning Partner");
				companyDetailsPage.waitForElementsOfCompaniesDetailsPage("tableOverlay");
				sleeper(4000);
				companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("resendBtn");
				companyDetailsPage.waitForElementsOfCompaniesDetailsPage("resendInviteButton");
				companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("resendInviteButton");
				LOGGER.info("Clicked on resend invite button");
				companyDetailsPage.waitForElementsOfCompaniesDetailsPage("toastNotification");
				softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("toastNotification", getTextLanguage(LanguageCode, "daas_ui", "partner_invite.message.sent")), "Toast Notification text does not match");
			}
			softAssert.assertAll();
			LOGGER.info("Root resend functionality verified");
		}

	}

	@Test(priority = 47, groups = { "REGRESSIONCOMPANIES2", "SMOKE", "REGRESSION_CI", "SMOKE_CI" }, enabled = false)
	public final void verifyFilterFunctionalityOnLicensesOrdesrPage() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login("RESELLER_NEW_UI_Companies_EMAIL_COMPANIES", "RESELLER_NEW_UI_Companies_PASSWORD_COMPANIES");
		gotosubscriptionOrdersTab();
		LOGGER.info("Redirected to subscription orders tab");
		waitForPageLoaded();
		LicenseOrdersPage subscriptionOrdersPage = new LicenseOrdersPage(PreDefinedActions.getDriver()).getInstance();

		resetTableConfiguration();
		Assert.assertTrue(subscriptionOrdersPage.verifyElementsOfSubscriptionOrdersPage("firstOrderNumber"), "No subscription key is present on incident page, unable to proceed further");
		LOGGER.info("Atleast one subscription key is present, test case started");
		subscriptionOrdersPage.waitForElementsOfSubscriptionOrdersPage("firstOrderNumber");

		// Test Case 1 - This test case validates if the filter functionality is
		// working properly for the searchbox on order number column
		softAssert.assertTrue(subscriptionOrdersPage.verifySearchValueOnSubscriptionOrdersPage(LanguageCode, "orderNumberTextBox", LicensesOrdersvariables.INCORRECT_SEARCH_STRING, "noElementsDisplayText", "orderNumberList"), "Search functionality with incorrect search string for order number column on subscription orders list page is not working");
		softAssert.assertTrue(subscriptionOrdersPage.verifySearchValueOnSubscriptionOrdersPage(LanguageCode, "orderNumberTextBox", LicensesOrdersvariables.ORDERNUMBER, "noElementsDisplayText", "orderNumberList"), "Search functionality for order number column on subscription orders list page is not working");
		LOGGER.info("Verified filter functionality for order number column");

		// Test Case 2 - This test case validates if the filter functionality is
		// working properly for the dropdown on type column
		subscriptionOrdersPage.clickOnElementsOfSubscriptionOrdersPage("subscriptionPlanDropdown");
		softAssert.assertTrue(subscriptionOrdersPage.verifyFilterSingleSelect(LanguageCode, "dropDownCheckBoxes", "dropDownElementListLabels", "planList", "noElementsDisplayText"), "Filter functionality on selecting single option from plan column on subscription orders list page is not working");
		subscriptionOrdersPage.waitUntilElementIsInvisibleOfSubscriptionOrdersPage("tableOverlay");
		subscriptionOrdersPage.clickOnElementsOfSubscriptionOrdersPage("clearFiltersButton");
		subscriptionOrdersPage.waitUntilElementIsInvisibleOfSubscriptionOrdersPage("tableOverlay");

		subscriptionOrdersPage.clickOnElementsOfSubscriptionOrdersPage("subscriptionPlanDropdown");
		softAssert.assertTrue(subscriptionOrdersPage.verifyFilterMultiSelect(LanguageCode, "dropDownCheckBoxes", "dropDownElementListLabels", "planList", "noElementsDisplayText"), "Filter functionality on selecting multiple options from plan column on subscription orders list page is not working");
		subscriptionOrdersPage.waitUntilElementIsInvisibleOfSubscriptionOrdersPage("tableOverlay");
		if (subscriptionOrdersPage.verifyElementsOfSubscriptionOrdersPage("clearFiltersButton"))
			subscriptionOrdersPage.clickOnElementsOfSubscriptionOrdersPage("clearFiltersButton");
		subscriptionOrdersPage.waitUntilElementIsInvisibleOfSubscriptionOrdersPage("tableOverlay");
		LOGGER.info("Verified filter functionality for plan column");

		// Test Case 3 - This test case validates if the filter functionality is
		// working properly for the calendar on Created On column
		subscriptionOrdersPage.scrollOnSubscriptionOrdersListPage("purchaseDateTitle");
		subscriptionOrdersPage.waitForElementsOfSubscriptionOrdersPage("purchaseDateDropdown");
		subscriptionOrdersPage.clickOnElementsOfSubscriptionOrdersPage("purchaseDateDropdown");
		sleeper(1000);
		softAssert.assertTrue(subscriptionOrdersPage.selectLastOneWeekRangeOnSubscriptionOrdersListPage(LanguageCode, "calenderIcon", "monthYearLeft", "monthYearRight", "prvArrow", "totalDaysLeftSide", "totalDaysRightSide", "noElementsDisplayText", "purchaseDateList"), "Filter functionality for purchased date column on subscription orders list page is not working");
		subscriptionOrdersPage.waitUntilElementIsInvisibleOfSubscriptionOrdersPage("tableOverlay");
		if (subscriptionOrdersPage.verifyElementsOfSubscriptionOrdersPage("clearFiltersButton"))
			subscriptionOrdersPage.clickOnElementsOfSubscriptionOrdersPage("clearFiltersButton");
		subscriptionOrdersPage.waitUntilElementIsInvisibleOfSubscriptionOrdersPage("tableOverlay");
		LOGGER.info("Verified filter functionality for purchase date column");

		// Test Case 8 - This test case validates if the filter functionality is
		// working properly for the dropdown on company box column
		// subscriptionOrdersPage.scrollOnsubscriptionOrdersPage("companyBoxTitle");
		subscriptionOrdersPage.clickOnElementsOfSubscriptionOrdersPage("companyDropdown");
		subscriptionOrdersPage.waitForElementsOfSubscriptionOrdersPage("companySearchBox");
		softAssert.assertTrue(subscriptionOrdersPage.verifyFilterFunctionalityForAssignedToSingleSelectFromDynamicDropdown(LanguageCode, "companySearchBox", LicensesOrdersvariables.COMPANY, "noElementsDisplayTextForComboBoxColumn", "companyDropdownList", "dropDownCheckBoxes", "companyList", "noElementsDisplayText"), "Filter functionality on selecting single option from company column on subscription orders list page is not working");
		if (subscriptionOrdersPage.verifyElementsOfSubscriptionOrdersPage("clearFiltersButton"))
			subscriptionOrdersPage.clickOnElementsOfSubscriptionOrdersPage("clearFiltersButton");
		subscriptionOrdersPage.waitUntilElementIsInvisibleOfSubscriptionOrdersPage("tableOverlay");

		subscriptionOrdersPage.clickOnElementsOfSubscriptionOrdersPage("companyDropdown");
		subscriptionOrdersPage.waitForElementsOfSubscriptionOrdersPage("companySearchBox");
		softAssert.assertTrue(subscriptionOrdersPage.verifyFilterFunctionalityForAssignedToMultiSelectFromDynamicDropdown(LanguageCode, "companySearchBox", LicensesOrdersvariables.COMPANY, "noElementsDisplayTextForComboBoxColumn", "companyDropdownList", "dropDownCheckBoxes", "companyList", "noElementsDisplayText"), "Filter functionality on selecting multiple options from company column on subscription orders list page is not working");
		if (subscriptionOrdersPage.verifyElementsOfSubscriptionOrdersPage("clearFiltersButton"))
			subscriptionOrdersPage.clickOnElementsOfSubscriptionOrdersPage("clearFiltersButton");
		subscriptionOrdersPage.waitUntilElementIsInvisibleOfSubscriptionOrdersPage("tableOverlay");
		LOGGER.info("Verified filter functionality for company column");

		softAssert.assertAll();
		LOGGER.info("All filter functionality test-cases passed");

	}

	/**
	 * This method will verify the table configuration test cases of subscription orders list page
	 * 
	 * @throws Exception
	 */
	@Test(priority = 48, groups = { "REGRESSIONCOMPANIES2", "SMOKE", "REGRESSION_CI", "SMOKE_CI" })
	public final void verifyTableConfigurationForLicensesOrdersTab() throws Exception {
		login("RESELLER_STAGING_COMPANIES_EMAIL", "RESELLER_PARTNERS_PASSWORD");
		gotosubscriptionOrdersTab();
		ArrayList<String> id = new ArrayList<String>(Arrays.asList(getTextLanguage(LanguageCode, "daas_ui", "subscription.list.order_number")));
		ArrayList<String> columnName = new ArrayList<String>(Arrays.asList(getTextLanguage(LanguageCode, "daas_ui", "subscription.list.order_number"), getTextLanguage(LanguageCode, "daas_ui", "subscription.list.sku"), getTextLanguage(LanguageCode, "daas_ui", "subscription.list.subscription_key"), getTextLanguage(LanguageCode, "daas_ui", "subscription.list.plan"), getTextLanguage(LanguageCode, "daas_ui", "subscription.list.total_seats"),
				getTextLanguage(LanguageCode, "daas_ui", "subscription.list.term"), getTextLanguage(LanguageCode, "daas_ui", "incident.list.company"), getTextLanguage(LanguageCode, "daas_ui", "subscription.list.purchase_date"), getTextLanguage(LanguageCode, "daas_ui", "subscription.list.start_date"), getTextLanguage(LanguageCode, "daas_ui", "subscription.list.status"), getTextLanguage(LanguageCode, "daas_ui", "subscription.list.country"),
				getTextLanguage(LanguageCode, "daas_ui", "subscription.list.state"), getTextLanguage(LanguageCode, "daas_ui", "global.city")));
		ArrayList<String> checkboxValue = new ArrayList<String>(Arrays.asList("true", "true", "true", "true", "true", "true", "true", "true", "true", "true", "false", "false", "false"));

		verifyTableConfigurationTests(columnName, checkboxValue, id);
	}

	@Test(priority = 49, groups = "REGRESSIONCOMPANIES2", enabled = false)
	public final void verifyPaginationOnLicensesOrdersPage() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login("RESELLER_STAGING_COMPANIES_EMAIL", "RESELLER_PARTNERS_PASSWORD");
		gotosubscriptionOrdersTab();
		LOGGER.info("Redirected to subscription key list page");
		waitForPageLoaded();
		resetTableConfiguration();
		LicenseOrdersPage subscriptionOrdersPage = new LicenseOrdersPage(PreDefinedActions.getDriver()).getInstance();

		Assert.assertTrue(subscriptionOrdersPage.verifyElementsOfSubscriptionOrdersPage("firstOrderNumber"), "No subscription key is present on subscription orders page, unable to proceed further");
		LOGGER.info("Atleast one subscription key is present, test case started");

		softAssert.assertTrue(subscriptionOrdersPage.verifyElementIsEnableOfSubscriptionOrdersListPage("paginationDropdownMenu"), "Pagination Dropdown not available");
		softAssert.assertTrue(subscriptionOrdersPage.verifyElementIsClickableOfSubscriptionOrdersListPage("paginationDropdownMenu"), "Pagination Dropdown not clickable");
		softAssert.assertTrue(subscriptionOrdersPage.waitForElementsOfSubscriptionOrdersPage("navigationItemDiv"), "Navigation Item are not available");
		getPaginationInfo();
		LOGGER.info("get Pagination Information ");
		softAssert.assertTrue(subscriptionOrdersPage.verifyElementIsEnableOfSubscriptionOrdersListPage("navigationItemActivePage"), "The active page navigation link is not disabled");
		softAssert.assertTrue(subscriptionOrdersPage.verifyTotalPageCountChangeAsSelectedOption(selectedOption, totalCount, lastPageNumber), "Total page count not change with selected option");
		softAssert.assertTrue(subscriptionOrdersPage.verifyButtonStatusofPagination(activepageNumber, firstPageNumber, lastPageNumber, totalCount, previousButtonStatus, nextButtonStatus), "Button visibility status not as per expectation");
		softAssert.assertTrue(verifySelectedOptionForNewSelection(selectedOption, 50), "Used already Chosen option ");
		if (changeSelectedOption(totalCount, 50)) {
			subscriptionOrdersPage.selectElementFromDropDownofSubscriptionOrdersListPage("paginationDropdownMenu", "paginationDropdownOptionList", "50");
			LOGGER.info("Change Selected option as 50");
			softAssert.assertTrue(subscriptionOrdersPage.verifyTotalPageCountChangeAsSelectedOption(selectedOption, totalCount, lastPageNumber), "Total page count not change with selected option");
			softAssert.assertTrue(subscriptionOrdersPage.verifyButtonStatusofPagination(activepageNumber, firstPageNumber, lastPageNumber, totalCount, previousButtonStatus, nextButtonStatus), "Button visibility status not as per expectation");
		} else {
			LOGGER.info("Selected user has less than 50 records");
		}
		getPaginationInfo();
		if (subscriptionOrdersPage.canClickNext(activepageNumber, lastPageNumber, nextButtonStatus)) {
			softAssert.assertTrue(subscriptionOrdersPage.getButtonEnabilityStatus("navigationItemNext"), "Naviagtion next button is not enable");
			subscriptionOrdersPage.clickOnElementsOfSubscriptionOrdersPage("navigationItemNext");
			getPaginationInfo();
			if (subscriptionOrdersPage.canClickPrevious(activepageNumber, firstPageNumber, previousButtonStatus)) {
				softAssert.assertTrue(subscriptionOrdersPage.getButtonEnabilityStatus("navigationItemPrevious"), "Naviagtion previous button is not enable");
				subscriptionOrdersPage.clickOnElementsOfSubscriptionOrdersPage("navigationItemPrevious");
			} else {
				LOGGER.info("Previous button is disabled");
			}
		} else if (subscriptionOrdersPage.canClickPrevious(activepageNumber, firstPageNumber, previousButtonStatus)) {
			softAssert.assertTrue(subscriptionOrdersPage.getButtonEnabilityStatus("navigationItemPrevious"), "Naviagtion previous button is not enable");
			subscriptionOrdersPage.clickOnElementsOfSubscriptionOrdersPage("navigationItemPrevious");
			getPaginationInfo();
			if (subscriptionOrdersPage.canClickNext(activepageNumber, lastPageNumber, nextButtonStatus)) {
				softAssert.assertTrue(subscriptionOrdersPage.getButtonEnabilityStatus("navigationItemNext"), "Naviagtion next button is not enable");
				subscriptionOrdersPage.clickOnElementsOfSubscriptionOrdersPage("navigationItemNext");
			} else {
				LOGGER.info("Next button is disabled");
			}
		} else {
			LOGGER.info("Previous and Next button both are disabled");
		}
		LOGGER.info("Verification of subscription orders page pagination done successfully");
	}

	@Test(priority = 50, groups = { "REGRESSIONCOMPANIES2", "SMOKE", "REGRESSION_CI", "SMOKE_CI" }, description = "TC300323")
	public final void verifyRolesForLicensesOrdersTab() throws Exception {
		login("RESELLER_STAGING_COMPANIES_EMAIL", "RESELLER_STAGING_COMPANIES_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		LicenseOrdersPage subscriptionOrdersPage = new LicenseOrdersPage(PreDefinedActions.getDriver()).getInstance();
		waitForPageLoaded();
		expandSideGroupMenu("My Organization");
		sleeper(2000);
		softAssert.assertTrue(subscriptionOrdersPage.verifyElementsOfSubscriptionOrdersPage("subscriptionOrdersTab"), "Subscription orders tab is not visible to sales admin");
		logout();
		LOGGER.info("Logged out successfully");
		login("RESELLER_NEW_UI_Companies_EMAIL_COMPANIES", "RESELLER_NEW_UI_Companies_PASSWORD_COMPANIES");
		waitForPageLoaded();
		expandSideGroupMenu("My Organization");
		sleeper(2000);
		softAssert.assertTrue(subscriptionOrdersPage.verifyElementsOfSubscriptionOrdersPage("subscriptionOrdersTab"), "Subscription orders tab is not visible to sales specialist");

		softAssert.assertAll();

	}

	/**
	 * This test case verifies the subscription key assignment popup on companies details page
	 *
	 * @throws Exception
	 */
	@Test(priority = 51, groups = "REGRESSIONCOMPANIES2", description = "253960")
	public final void verifyLicensesOrdersListOnCompanyDetailsPage() throws Exception {
		login("RESELLER_NEW_UI_Companies_EMAIL_COMPANIES", "RESELLER_NEW_UI_Companies_PASSWORD_COMPANIES");
		SoftAssert softAssert = new SoftAssert();
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		CompaniesDetailsPage companyDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver()).getInstance();
		gotoCompaniesTab();
		LOGGER.info("Redirected to companies page");
		waitForPageLoaded();
		impersonateCompanyByCompanyName(getEnvironmentSpecificData(System.getProperty("environment"), "AUTOMATION_IMPO_COMPANY"));
		LOGGER.info("Redirected to company details page");
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("subscriptionTab");
		LOGGER.info("Redirected to subscription tab");
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("addNewKeyButton");
		LOGGER.info("Clicked on add new key button");
		softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("addKeyPopupHeader", getTextLanguage(LanguageCode, "daas_ui", "partner.add.key.title")), "Header on add key popup is incorrect");
		softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("addKeyPopupTitle", getTextLanguage(LanguageCode, "daas_ui", "partner.addKey.first.title")), "Title on add key popup is incorrect");
		softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("addKeyPopupSubTitle", getTextLanguage(LanguageCode, "daas_ui", "partner.addKey.first.desc.line1")), "Sub title on add key popup is incorrect");
		softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("addKeyPopupDescription", getTextLanguage(LanguageCode, "daas_ui", "partner.addKey.first.desc.line2")), "Description on add key popup is incorrect");
		softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("addmanuallyHeader", getTextLanguage(LanguageCode, "daas_ui", "partner.addKey.first.add.manually")), "Title on add key manually option is incorrect");
		softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("addmanuallyDescription", getTextLanguage(LanguageCode, "daas_ui", "partner.addKey.first.add.manually.desc")), "Description on add key manually option is incorrect");
		softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("addKeyFromListHeader", getTextLanguage(LanguageCode, "daas_ui", "partner.addKey.first.add.list")), "Title on add key from a list option is incorrect");
		softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("addKeyFromListDescription", getTextLanguage(LanguageCode, "daas_ui", "partner.addKey.first.add.list.desc")), "Description on add key from a list option is incorrect");
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("nextButton");
		LOGGER.info("Clicked on next button");
		softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("addkeymanuallyTitle", getTextLanguage(LanguageCode, "daas_ui", "partner.addKey.manually.title")), "Title on add key manually popup is incorrect");
		softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("addKeyManuallyDescription", getTextLanguage(LanguageCode, "daas_ui", "partner.addKey.manually.title.desc_mixed")), "Description on add key manually popup is incorrect");
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("addKeymanuallyTextbox"), "Textbox for entering key is not present on add key manually popup");
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("previousButton");
		LOGGER.info("Clicked on previous button");
		sleeper(2000);
		companyDetailsPage.clickByJavaScriptOnCompaniesDetailsPage("addKeyFromListButton");
		LOGGER.info("Clicked on add new key from list button");
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("nextButton");
		LOGGER.info("Clicked on next button");
		softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("addkeymanuallyTitle", getTextLanguage(LanguageCode, "daas_ui", "partner.addKey.list.title")), "Title on add key from a list popup is incorrect");
		softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("addKeyManuallyDescription", getTextLanguage(LanguageCode, "daas_ui", "partner.addKey.list.desc")), "Description on add key from a list popup is incorrect");
		softAssert.assertAll();
		LOGGER.info("All test cases of verifyLicensesOrdersListOnCompanyDetailsPage passed");
	}

	/**
	 * This test case will validate company creation
	 * 
	 * @throws Exception
	 */
	@Test(priority = 52, groups = { "REGRESSIONCOMPANIES2", "SMOKE", "REGRESSION_CI", "SMOKE_CI", "STABILIZATION_STAGING" }, description = "Test case id 240912")
	public final void verifyAddCompanyFunctionalityThroughRoot() throws Exception {
		login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		String tenantID = null,strSubTenant=null;
		LOGGER.info("Redirected to Companies tab");

		waitForPageLoaded();
		resetTableConfiguration();
		String compName = generateRandomString(10);
		String compEmail = generateRandomString(5) + "@yopmail.com";

		// Company addition, billing license, free trial on
		Assert.assertTrue(companiesPage.addCompany(LanguageCode, compName, compEmail, CompaniesVariables.COUNTRY, getTextLanguage(LanguageCode, "lhserver", "global.package_name.hp_proactive_insights"), "", getEnvironmentSpecificData(System.getProperty("environment"), "AUTOMATION_PARTNER"), "FirstName", "LastName", true, getTextLanguage(LanguageCode, "daas_ui", "subscription.list.license"), "on"), "Company failed to create");
		LOGGER.info("Added company with billing model as license, free trial is on");
		
		// Remove created company 
		strSubTenant = getUrlOfCurrentPage().substring(getUrlOfCurrentPage().length() - 57); 
		tenantID = strSubTenant.substring(0, 36);
		companiesPage.removeCompanyUsingAPI(tenantID);
		navigateToBack();
		LOGGER.info("Removed added company");

		// Company addition, billing license, free trial off
		Assert.assertTrue(companiesPage.addCompany(LanguageCode, compName, compEmail, CompaniesVariables.COUNTRY, getTextLanguage(LanguageCode, "lhserver", "global.package_name.hp_proactive_insights"), "", getEnvironmentSpecificData(System.getProperty("environment"), "AUTOMATION_PARTNER"), "FirstName", "LastName", true, getTextLanguage(LanguageCode, "daas_ui", "subscription.list.license"), "off"), "Company failed to create");
		LOGGER.info("Added company with billing model as license, free trial is off");
		
		// Remove created company 
		strSubTenant = getUrlOfCurrentPage().substring(getUrlOfCurrentPage().length() - 57); 
		tenantID = strSubTenant.substring(0, 36);
		companiesPage.removeCompanyUsingAPI(tenantID);
		navigateToBack();
		LOGGER.info("Removed added company");

		// Company addition, billing subscription
		Assert.assertTrue(companiesPage.addCompany(LanguageCode, compName, compEmail, CompaniesVariables.COUNTRY, getTextLanguage(LanguageCode, "lhserver", "global.package_name.hp_proactive_insights"), "", getEnvironmentSpecificData(System.getProperty("environment"), "AUTOMATION_PARTNER"), "FirstName", "LastName", true, getTextLanguage(LanguageCode, "daas_ui", "companies.list.subscription"), "off"), "Company failed to create");
		LOGGER.info("Added company with billing model as subscription");
		
		// Remove created company
		strSubTenant = getUrlOfCurrentPage().substring(getUrlOfCurrentPage().length() - 57); 
		tenantID = strSubTenant.substring(0, 36);
		companiesPage.removeCompanyUsingAPI(tenantID);
		navigateToBack();
		LOGGER.info("Removed added company");

		softAssert.assertAll();
	}

	/**
	 * This test case validates unique subscription plans(certified partner admin)
	 * 
	 * @throws Exception
	 */
	@Test(priority = 53, description = "307368", groups = { "REGRESSIONCOMPANIES2", "SMOKE", "REGRESSION_CI", "SMOKE_CI" }, enabled = false)
	public final void verifySubscriptionListCPA() throws Exception {
		login("PARTNER_ADMIN_USER_EMAIL", "PARTNER_ADMIN_USER_PASSWORD");
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		LicenseOrdersPage subscriptionOrdersPage = new LicenseOrdersPage(PreDefinedActions.getDriver()).getInstance();
		List<String> activeTabList = null, suspendedTabList = null;

		// Get subscription plan list on subscription orders tab
		gotosubscriptionOrdersTab();
		waitForPageLoaded();
		subscriptionOrdersPage.waitForElementsOfSubscriptionOrdersPage("tableOverlay");
		resetTableConfiguration();
		List<String> subOrderList = subscriptionOrdersPage.getUniqueElementsFromSubscriptionOrdersPageList("uniquelist_subord");
		LOGGER.info("Get subscription orders tab susbcription list");

		// Get subscription plan list on active companies tab
		gotoCompaniesTab();
		waitForPageLoaded();
		resetTableConfiguration();
		if (!companiesPage.verifyElementsOfCompaniesPage("noItemsDisplayText")) {
			activeTabList = companiesPage.getUniqueElementsFromCompaniesPageList("uniquelist_active");
			subOrderList.addAll(activeTabList);
			LOGGER.info("Get active tab susbcription list");
		} else
			LOGGER.info("No companies present under active tab");

		// Get subscription plan list on suspended companies tab
		companiesPage.clickOnElementsOfCompaniesPage("suspendedtab");
		waitForPageLoaded();
		resetTableConfiguration();
		if (!companiesPage.verifyElementsOfCompaniesPage("noItemsDisplayText")) {
			suspendedTabList = companiesPage.getUniqueElementsFromCompaniesPageList("uniquelist_susped");
			subOrderList.addAll(suspendedTabList);
			LOGGER.info("Get suspended tab susbcription list");
		} else
			LOGGER.info("No companies present under suspended tab");

		// Compare list with subscription column filter
		sleeper(2000);
		companiesPage.clickOnElementsOfCompaniesPage("subscriptionColumn");
		sleeper(2000); // drop down takes time to load
		Assert.assertTrue(getUniqueList(subOrderList).containsAll(companiesPage.getUniqueElementsFromCompaniesPageList("subscriptionPlanList")), "Subscription plan list did not match with subscription order and companies tab");
		LOGGER.info("Validated subscription list with subscription column filter");
	}

	/**
	 * This test case validates unique subscription plans(MSP)
	 * 
	 * @throws Exception
	 */
	@Test(priority = 54, description = "307368", groups = { "REGRESSIONCOMPANIES2", "SMOKE", "REGRESSION_CI", "SMOKE_CI" }, enabled = false)
	public final void verifySubscriptionListMSP() throws Exception {
		login("NEW_MSP_ADMIN_US_EMAIl", "NEW_MSP_ADMIN_US_PASSWORD");
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		List<String> activeTabList = null, suspendedTabList;
		// Get subscription plan list on active companies tab
		gotoCompaniesTab();
		waitForPageLoaded();
		resetTableConfiguration();
		if (!companiesPage.verifyElementsOfCompaniesPage("noItemsDisplayText")) {
			activeTabList = companiesPage.getUniqueElementsFromCompaniesPageList("uniquelist_active");
			LOGGER.info("Get active tab susbcription list");
		} else
			LOGGER.info("No companies present under active tab");

		// Get subscription plan list on suspended companies tab
		companiesPage.clickOnElementsOfCompaniesPage("suspendedtab");
		waitForPageLoaded();
		resetTableConfiguration();
		if (!companiesPage.verifyElementsOfCompaniesPage("noItemsDisplayText")) {
			suspendedTabList = companiesPage.getUniqueElementsFromCompaniesPageList("uniquelist_susped");
			activeTabList.addAll(suspendedTabList);
			LOGGER.info("Get suspended tab susbcription list");
		} else
			LOGGER.info("No companies present under suspended tab");

		// Compare list with subscription column filter
		sleeper(2000);
		companiesPage.clickOnElementsOfCompaniesPage("subscriptionColumn");
		sleeper(2000); // drop down takes time to load
		Assert.assertTrue(getUniqueList(activeTabList).containsAll(companiesPage.getUniqueElementsFromCompaniesPageList("subscriptionPlanList")), "Subscription plan list did not match child companies plans");
		LOGGER.info("Validated subscription list with subscription column filter");
	}

	/**
	 * This test case validates Device Recommendation for Product Catalog Section in Company details page: Preference tab.
	 * 
	 * @throws Exception
	 */
	@Test(priority = 55, groups = { "REGRESSIONCOMPANIES2", "SMOKE", "REGRESSION_CI", "SMOKE_CI" }, description = "302910,308213,312904,307471,308220,300845,302907", enabled = false)
	public final void verifyDeviceRecommnedationForProductCatalog() throws Exception {
		login("RESELLER_NEW_UI_EMAIL_US", "RESELLER_NEW_UI_PASSWORD_US");
		SoftAssert softAssert = new SoftAssert();
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		try {
			gotoCompaniesTab();
			waitForPageLoaded();
			LOGGER.info("Clicked on Company tab");
			resetTableConfiguration();
			// Add company through partner
			Assert.assertTrue(companiesPage.addCompanies(LanguageCode, CommonVariables.COMPANY_NAME, CommonVariables.EMAIL, getEnvironmentSpecificData(System.getProperty("environment"), "COUNTRY"), CommonVariables.PLAN_STANDARD, CommonVariables.MSP_NAME, getEnvironmentSpecificData(environment, "ASSIGNED_PARTNER_NAME_ADD"), "FN", "LN", true, true, false), "Company not added successfully through Root admin.");
			logout();

			// Approve Partner Invite
			login("IT_ADMIN_INVITE_FLOW_FOR_APPROVAL_DECLINE_EMAIL", "IT_ADMIN_INVITE_FLOW_FOR_APPROVAL_DECLINE_PASSWORD");
			waitForPageLoaded();
			// Verify Notification Icon for invitation
			companiesPage.clickOnElementsOfCompaniesPage("notificationBellIcon");
			companiesPage.waitForPresenceOfElementsOfCompaniesPage("invitationNotificationTitle");
			softAssert.assertTrue(companiesPage.getTextOfCompanyPage("invitationNotificationTitle").equals(getTextLanguage(LanguageCode, "daas_ui", "partner.invitations").toUpperCase()), "Partner Invitation title is not matching.");
			softAssert.assertTrue(companiesPage.getTextOfCompanyPage("invitationNotificationDescription").equals(getEnvironmentSpecificData(System.getProperty("environment"), "AUTOMATION_PARTNER") + " (" + getTextLanguage(LanguageCode, "idm", "partner.id") + " " + getEnvironmentSpecificData(environment, "AUTOMATION_PARTNER_ID") + ") " + CompaniesVariables.PARTNER_INVITATION_NOTIFICATION), "Partner Invitation description is not matching.");
			softAssert.assertTrue(companiesPage.getTextOfCompanyPage("invitationNotificationApprove").equals(getTextLanguage(LanguageCode, "daas_ui", "global.approve")), "Approve button is not matching.");
			softAssert.assertTrue(companiesPage.getTextOfCompanyPage("invitationNotificationDecline").equals(getTextLanguage(LanguageCode, "daas_ui", "global.decline")), "Decline button is not matching.");
			companiesPage.clickOnElementsOfCompaniesPage("notificationBellIcon");
			LOGGER.info("Notification Icon is verified successfully");

			// Approve Invitation from Partner
			gotoCompanySettingsTab();
			waitForPageLoaded();
			companiesPage.waitForPresenceOfElementsOfCompaniesPage("assignedPartner");
			companiesPage.clickOnElementsOfCompaniesPage("assignedPartner");
			companiesPage.waitForPresenceOfElementsOfCompaniesPage("invitationLabel");
			companiesPage.clickOnElementsOfCompaniesPage("acceptInvitationBtn");
			companiesPage.verifyElementsOfCompaniesPage("approveLabel");
			softAssert.assertTrue(companiesPage.getTextOfCompanyPage("approveLabel").equals(getTextLanguage(LanguageCode, "daas_ui", "company.approve.partner")), "Approve Label is not matching.");
			softAssert.assertTrue(companiesPage.getTextOfCompanyPage("addApproverBody").equals(getTextLanguage(LanguageCode, "daas_ui", "partner_invite.accept.message").replace("{partner_name}", getEnvironmentSpecificData(System.getProperty("environment"), "AUTOMATION_PARTNER"))), "Approve body message is not matching.");
			companiesPage.verifyElementsOfCompaniesPage("approveBtn");
			companiesPage.clickOnElementsOfCompaniesPage("approveBtn");
			companiesPage.waitForElementsOfCompaniesPage("toastNotification");
			softAssert.assertTrue(companiesPage.matchTextOfCompanyPage("toastNotification", getTextLanguage(LanguageCode, "daas_ui", "partner_invite.message.update")), "Toast notification is not generated after accepting invitation from Partner");
			LOGGER.info("Invitation accepted from Partner");
			logout();

			login("RESELLER_NEW_UI_EMAIL_US", "RESELLER_NEW_UI_PASSWORD_US");
			gotoCompaniesTab();
			waitForPageLoaded();
			impersonateCompanyByCompanyName(CommonVariables.COMPANY_NAME);

			// Test Case 1: Verify default values of Device Recommendation
			goToPreferencesTab();
			waitForPageLoaded();
			softAssert.assertTrue(companiesPage.verifyDefaultValuesofDeviceRecommendation(LanguageCode), "Device Recommnedation was not updated.");

			// Test Case 2: Verify edit Functionality of Device Recommendation
			softAssert.assertTrue(companiesPage.verifyEditFunctionalityofDeviceRecommendation(LanguageCode), "Device Recommnedation was not updated.");

			// Test Case 3: Verify Cancel Functionality of Device Recommendation
			softAssert.assertTrue(companiesPage.verifyCancelFunctionalityofDeviceRecommendation(LanguageCode), "Device Recommnedation was not updated.");

			softAssert.assertAll();
			LOGGER.info("Validation of Device Recommnedation For Product Catalog completed successfully.");

		} finally {
			// Logout from account
			logout();
			// Deleting a Company
			login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
			JSONObject jsonAuthTokenforRoot = (JSONObject) parser.parse(authTokenParse(companiesPage.getTokenFromLocalStorage("dui_token_e")));
			String companyTenantID = jsonAuthTokenforRoot.get("tenant").toString();

			// Remove company through API.
			softAssert.assertTrue(companiesPage.removeAllCompany(environment, companyTenantID, CompaniesTest.companyBody, getEnvironment(environment)), "Company removal failed");
		}
	}

	@DataProvider
	public Object[][] loginDataForAuthenticationTile() {
		Object[][] data = new Object[2][2];
		data[0][0] = "MSP_AZURE";
		data[0][1] = "MSP_AZURE_PASSWORD";
		data[1][0] = "PARTNER_AZURE";
		data[1][1] = "PARTNER_AZURE_PASSWORD";
		return data;
	}

	/**
	 * verifyAuthenticationTileOnCompanyDetailsPageNonRoot
	 * 
	 * @throws Exception
	 */
	@Test(priority = 56, description = "Test case ID:310110; Roles ~ MSP, Partner Administrator", groups = { "REGRESSIONCOMPANIES2", "REGRESSION_CI" }, dataProvider = "loginDataForAuthenticationTile",enabled=false)
	public final void verifyAuthenticationTileOnCompanyDetailsPageNonRoot(String username, String password) throws Exception {
		login(username, password);
		SoftAssert softAssert = new SoftAssert();
		CompaniesDetailsPage companyDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver()).getInstance();
		if (username.equals("IT_ADMIN_AUTH_EMAIL")) {
			gotoSettingsTabWorkflow();
		} else {
			gotoCompaniesTab();
			LOGGER.info("Redirected to company list page");
			waitForPageLoaded();
			resetTableConfiguration();
			impersonateCompanyByCompanyName(authCompany);
			LOGGER.info("Redirected to company details page");
		}
		companyDetailsPage.waitForElementsOfCompaniesDetailsPage("tableOverlay");
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("preferencesTitle");
		softAssert.assertTrue(companyDetailsPage.verifyAuthenticationMethod(LanguageCode, "SAVE"), "Authentication method change operation(SAVE) got failed.");
		softAssert.assertTrue(companyDetailsPage.verifyAuthenticationMethod(LanguageCode, "CANCEL"), "Authentication method change operation(CANCEL) got failed.");
		softAssert.assertAll();
		LOGGER.info("Validation of Authentication method completed successfully FOR " + username);
	}

	/**
	 * Disabling the test case for now in lieu of Bug 812594: [DaaS][UI] Error page not appearing when URL is incorrect
	 * 
	 * This test case validates URL specific keywords for each tab present in Company details page. Validation by hitting required URL and landing on specific page. Validation by
	 * hitting invalid URL.
	 * 
	 * @throws Exception
	 */
	@Test(priority = 57, groups = { "REGRESSIONCOMPANIES2", "SMOKE", "REGRESSION_CI", "SMOKE_CI" }, description = "Test Case ID : 312492,313157", enabled = false)
	public final void verifyCompaniesDetailsUrl() throws Exception {
		login("NEW_MSP_ADMIN_US_EMAIl", "NEW_MSP_ADMIN_US_PASSWORD");

		SoftAssert softAssert = new SoftAssert();
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		CompaniesDetailsPage companyDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver()).getInstance();
		ErrorPage errorPage = new ErrorPage(PreDefinedActions.getDriver()).getInstance();
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		gotoCompaniesTab();
		LOGGER.info("Redirected to companies list page");
		waitForPageLoaded();
		companiesPage.clearFiltersOfCompaniesListPage("clearfilter");
		impersonateCompanyByCompanyName(productionCompany);
		LOGGER.info("Redirected to companies details page");
		sleeper(2000);
		goToPreferencesTab();
		currentUrl = getUrlOfCurrentPage();
		softAssert.assertTrue(currentUrl.contains("company-preferences"), "Preference tab does not have expected keyword.");
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("subscriptionTab");
		currentUrl = getUrlOfCurrentPage();
		softAssert.assertTrue(currentUrl.contains("company-subscription"), "Subscription tab does not have expected keyword.");
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("assignedMSPTab");
		currentUrl = getUrlOfCurrentPage();
		softAssert.assertTrue(currentUrl.contains("assigned-msp"), "Assigned Msp tab does not have expected keyword.");
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("assignedpartnerTab");
		currentUrl = getUrlOfCurrentPage();
		softAssert.assertTrue(currentUrl.contains("assigned-partner"), "Assigned Partner tab does not have expected keyword.");
		logout();
		getUrl(currentUrl);
		loginCustom("NEW_MSP_ADMIN_US_EMAIl", "NEW_MSP_ADMIN_US_PASSWORD");
		currentUrl = getUrlOfCurrentPage();
		softAssert.assertTrue(currentUrl.contains("assigned-partner"), "Assigned Partner tab does not have expected keyword after logout and login with custom URL.");
		getUrl(currentUrl + "randomTextinURL");
		errorPage.waitForElementsOfErrorPage("errorIcon");
		softAssert.assertTrue(errorPage.verifyElementsOfErrorPage("errorIcon"), "Hp icon is not present on the 404 error page");
		softAssert.assertTrue(errorPage.matchTextOfErrorPage("errorMessage", errorPage.getTextLanguage(LanguageCode, "daas_ui", "global.heading.404")), "Error message on 404 error page is incorrect");
		softAssert.assertTrue(errorPage.matchTextOfErrorPage("noPermissionTitle", errorPage.getTextLanguage(LanguageCode, "daas_ui", "global.error_desc.line1.404")), "No permission title 1 on 404 error page is incorrect");
		softAssert.assertTrue(errorPage.matchTextOfErrorPage("noPermissionTitle2", errorPage.getTextLanguage(LanguageCode, "daas_ui", "global.error_desc.line2.404")), "No permission title 2 on 404 error page is incorrect");
		errorPage.clickOnElementsOfErrorPage("goBackSignIn");
		waitForPageLoaded();
		dashboardPage.waitForElementsOfDashboardPage("companyDropdown");
		softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("dashboardTitleOnBreadcrumbs").equals(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "pagetitle.dashboard")), "After clicking on GO TO HOME PAGE button, user is not redirected to dashboard page");
		softAssert.assertAll();
		LOGGER.info("Validation of Company details tab URLs completed successfully");
	}

	/**
	 * This test case validates Client Application type in Company details page: Preference tab.
	 * 
	 * @throws Exception
	 */
	@Test(priority = 58, groups = { "REGRESSIONCOMPANIES2", "SMOKE", "REGRESSION_CI", "SMOKE_CI" }, description = "312140,331083,312903", enabled = false)
	public final void verifyClientApplicationConnectivityType() throws Exception {
		login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
		SoftAssert softAssert = new SoftAssert();
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		ArrayList<String> connectivityDefaultValues = new ArrayList<String>(
				Arrays.asList((getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.client_config.connectivity.wifi")), (getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.client_config.connectivity.cellular")), (getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.client_config.connectivity.metered_network")), (getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.client_config.connectivity.roaming_network"))));
		ArrayList<String> expectedConnectivityValues = new ArrayList<String>(Arrays.asList((getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.client_config.connectivity.wifi")), (getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.client_config.connectivity.cellular"))));
		try {
			LOGGER.info("Redirected to Companies tab");
			waitForPageLoaded();
			JSONObject jsonAuthTokenforRoot = (JSONObject) parser.parse(authTokenParse(companiesPage.getTokenFromLocalStorage("dui_token_e")));
			String companyTenantID = jsonAuthTokenforRoot.get("tenant").toString();

			// Remove company through API.
			softAssert.assertTrue(companiesPage.removeAllCompany(environment, companyTenantID, companyBody, getEnvironment(environment)), "Company removal failed");
			Assert.assertTrue(companiesPage.addCompanies(LanguageCode, CommonVariables.COMPANY_NAME, CommonVariables.EMAIL, getEnvironmentSpecificData(System.getProperty("environment"), "COUNTRY"), CommonVariables.PLAN_STANDARD, getEnvironmentSpecificData(System.getProperty("environment"), "AUTOMATION_MSP"), getEnvironmentSpecificData(System.getProperty("environment"), "AUTOMATION_PARTNER"), "FN", "LN", true, false, true), "Company not created through Root Admin.");
			logout();

			// Approve Partner Invite
			login("IT_ADMIN_INVITE_FLOW_FOR_APPROVAL_DECLINE_EMAIL", "IT_ADMIN_INVITE_FLOW_FOR_APPROVAL_DECLINE_PASSWORD");
			waitForPageLoaded();
			companiesPage.clickOnElementsOfCompaniesPage("notificationBellIcon");
			companiesPage.waitForPresenceOfElementsOfCompaniesPage("invitationNotificationTitle");
			softAssert.assertTrue(companiesPage.getTextOfCompanyPage("invitationNotificationTitle").equals(getTextLanguage(LanguageCode, "daas_ui", "partner.invitations").toUpperCase()), "Partner Invitation title is not matching.");
			softAssert.assertTrue(companiesPage.getTextOfCompanyPage("invitationNotificationDescription").equals(getEnvironmentSpecificData(System.getProperty("environment"), "AUTOMATION_PARTNER") + " (" + getTextLanguage(LanguageCode, "idm", "partner.id") + " " + getEnvironmentSpecificData(System.getProperty("environment"), "AUTOMATION_PARTNER_ID") + ") " + CompaniesVariables.PARTNER_INVITATION_NOTIFICATION), "Partner Invitation description is not matching.");
			softAssert.assertTrue(companiesPage.getTextOfCompanyPage("invitationNotificationApprove").equals(getTextLanguage(LanguageCode, "daas_ui", "global.approve")), "Approve button is not matching.");
			softAssert.assertTrue(companiesPage.getTextOfCompanyPage("invitationNotificationDecline").equals(getTextLanguage(LanguageCode, "daas_ui", "global.decline")), "Decline button is not matching.");
			companiesPage.clickOnElementsOfCompaniesPage("notificationBellIcon");
			LOGGER.info("Notification Icon is verified successfully");

			gotoCompanySettingsTab();
			waitForPageLoaded();
			companiesPage.waitForPresenceOfElementsOfCompaniesPage("assignedPartner");
			companiesPage.clickOnElementsOfCompaniesPage("assignedPartner");
			companiesPage.waitForPresenceOfElementsOfCompaniesPage("invitationLabel");
			companiesPage.clickOnElementsOfCompaniesPage("acceptInvitationBtn");
			companiesPage.verifyElementsOfCompaniesPage("approveLabel");
			softAssert.assertTrue(companiesPage.getTextOfCompanyPage("approveLabel").equals(getTextLanguage(LanguageCode, "daas_ui", "company.approve.partner")), "Approve Label is not matching.");
			softAssert.assertTrue(companiesPage.getTextOfCompanyPage("addApproverBody").equals(getTextLanguage(LanguageCode, "daas_ui", "partner_invite.accept.message").replace("{partner_name}", getEnvironmentSpecificData(System.getProperty("environment"), "AUTOMATION_PARTNER"))), "Approve body message is not matching.");
			companiesPage.verifyElementsOfCompaniesPage("approveBtn");
			companiesPage.clickOnElementsOfCompaniesPage("approveBtn");
			companiesPage.waitForElementsOfCompaniesPage("toastNotification");
			softAssert.assertTrue(companiesPage.matchTextOfCompanyPage("toastNotification", getTextLanguage(LanguageCode, "daas_ui", "partner_invite.message.update")), "Toast notification is not generated after accepting invitation from Partner");
			LOGGER.info("Invitation accepted from Partner");
			logout();

			// Login to MSP account
			login("MSP_ADMIN_US_EMAIl_DASHBOARD", "MSP_ADMIN_US_PASSWORD_DASHBOARD");
			gotoCompaniesTab();
			waitForPageLoaded();
			impersonateCompanyByCompanyName(CommonVariables.COMPANY_NAME);

			// Test Case 1: Verify default values of Client Application
			goToPreferencesTab();
			waitForPageLoaded();
			companiesPage.scrollOnCompaniesPage("clientApplicationHeading");
			scrollByCoordinatesofAPage(0, -80);
			LOGGER.info("Scrolled down to clientApplicationHeading ");
			companiesPage.waitForElementsOfCompaniesPage("clientApplicationHeading");
			softAssert.assertTrue(companiesPage.getTextOfCompaniesPage("clientApplicationHeading").equals(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.client_application").toUpperCase()), "Client Application heading does not match.");
			softAssert.assertTrue(companiesPage.getTextOfCompaniesPage("androidLabel").equals(getTextLanguage(LanguageCode, "daas_ui", "global.device_os.android")), "Android label heading does not match.");
			softAssert.assertTrue(companiesPage.getTextOfCompaniesPage("connectivityinfo").toLowerCase().equals(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.client_config.connectivity.title").toLowerCase()), "Connectivity info heading does not match.");
			Assert.assertTrue(companiesPage.compareTwoListOfCompanyDetailPage("connectivitySection", connectivityDefaultValues), "Default Values are not correct for Client Application.");
			LOGGER.info("Validation of Default values for Client Application completed successfully.");

			// Test Case 2: Verify default values on edit section and edit
			// Functionality of Client Application
			companiesPage.waitForElementsOfCompaniesPage("clientApplicationEditBtn");
			companiesPage.verifyElementsOfCompaniesPage("clientApplicationEditBtn");
			companiesPage.clickOnElementsOfCompaniesPage("clientApplicationEditBtn");
			companiesPage.waitForElementsOfCompaniesPage("sendDataModalheader");
			softAssert.assertTrue(companiesPage.getTextOfCompaniesPage("sendDataModalheader").equals(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.client_config.connectivity.title")), "Send data modal heading does not match.");
			softAssert.assertTrue(companiesPage.getTextOfCompaniesPage("sendDataModalLabel").equals(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.client_config.edit.description")), "Send data modal description does not match.");
			Assert.assertTrue(companiesPage.compareTwoListOfCompanyDetailPage("connectivityEditSection", connectivityDefaultValues), "Default Values are not correct for Client Application on edit section.");

			// Verify edit functionality
			companiesPage.waitForElementsOfCompaniesPage("meteredNetworkCheckbox");
			companiesPage.clickOnElementsOfCompaniesPage("meteredNetworkCheckbox");
			companiesPage.waitForElementsOfCompaniesPage("roamingNetwokCheckbox");
			companiesPage.clickOnElementsOfCompaniesPage("roamingNetwokCheckbox");
			companiesPage.waitForElementsOfCompaniesPage("connectivityTypeSaveButton");
			companiesPage.clickOnElementsOfCompaniesPage("connectivityTypeSaveButton");
			companiesPage.waitForElementsOfCompaniesPage("deviceRecommendationSuccessNotification");
			softAssert.assertTrue(companiesPage.getTextOfCompaniesPage("deviceRecommendationSuccessNotification").equals(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.client_config.connectivity.success")), "Success Notification is not displayed for Connectivity Type.");
			sleeper(2000);// Need to use hard code wait here.
			Assert.assertTrue(companiesPage.compareTwoListOfCompanyDetailPage("connectivitySection", expectedConnectivityValues), "Client Application values are not updated properly.");
			LOGGER.info("Validation of Edit functinality for Client Application completed successfully.");

			// Test Case 3: Verify Cancel Functionality of Client Application
			expectedConnectivityValues = companiesPage.getTextOfListOfCompaniesDetailPage("connectivitySection");
			companiesPage.waitForElementsOfCompaniesPage("clientApplicationEditBtn");
			companiesPage.verifyElementsOfCompaniesPage("clientApplicationEditBtn");
			companiesPage.clickOnElementsOfCompaniesPage("clientApplicationEditBtn");
			companiesPage.waitForElementsOfCompaniesPage("sendDataModalheader");
			companiesPage.waitForElementsOfCompaniesPage("connectivityTypeCancelButton");
			companiesPage.clickOnElementsOfCompaniesPage("connectivityTypeCancelButton");
			companiesPage.waitForElementsOfCompaniesPage("connectivityinfo");
			Assert.assertTrue(companiesPage.compareTwoListOfCompanyDetailPage("connectivitySection", expectedConnectivityValues), "Cancel functinality for Client Application is failed.");
			LOGGER.info("Validation of Cancel functinality for Client Application completed successfully.");

			// Test Case5: Verify Error messages on Client Application Pop-up
			companiesPage.waitForElementsOfCompaniesPage("clientApplicationEditBtn");
			companiesPage.verifyElementsOfCompaniesPage("clientApplicationEditBtn");
			companiesPage.clickOnElementsOfCompaniesPage("clientApplicationEditBtn");
			companiesPage.waitForElementsOfCompaniesPage("sendDataModalheader");
			companiesPage.deselectAllCheckboxOfPopup("columnCheckboxesOnPopup", "listOfColumnsOnPopup");
			companiesPage.waitForElementsOfCompaniesPage("connectivityTypeSaveButton");
			companiesPage.clickOnElementsOfCompaniesPage("connectivityTypeSaveButton");
			softAssert.assertTrue(companiesPage.getTextOfCompaniesPage("errorToast").equals(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.client_config.connectivity.message.required")), "Error Notification is not displayed if no option is selected for Connectivity Type.");
			companiesPage.waitForElementsOfCompaniesPage("meteredNetworkCheckbox");
			companiesPage.clickOnElementsOfCompaniesPage("meteredNetworkCheckbox");
			companiesPage.waitForElementsOfCompaniesPage("roamingNetwokCheckbox");
			companiesPage.clickOnElementsOfCompaniesPage("roamingNetwokCheckbox");
			companiesPage.waitForElementsOfCompaniesPage("connectivityTypeSaveButton");
			companiesPage.clickOnElementsOfCompaniesPage("connectivityTypeSaveButton");
			softAssert.assertTrue(companiesPage.getTextOfCompaniesPage("errorToast").equals(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.client_config.connectivity.message.required")), "Error Notification is not displayed if wifi or cellular option is not selected for Connectivity Type.");
			companiesPage.waitForElementsOfCompaniesPage("connectivityTypeCancelButton");
			companiesPage.verifyElementsOfCompaniesPage("connectivityTypeCancelButton");
			companiesPage.clickOnElementsOfCompaniesPage("connectivityTypeCancelButton");
			LOGGER.info("Validation of Error functinality for Client Application completed successfully.");
			logout();

			// Test Case4: Verify Client Application tab for Reseller.
			login("RESELLER_NEW_UI_EMAIL_US", "RESELLER_NEW_UI_PASSWORD_US");
			gotoCompaniesTab();
			waitForPageLoaded();
			impersonateCompanyByCompanyName(CommonVariables.COMPANY_NAME);
			goToPreferencesTab();
			waitForPageLoaded();
			companiesPage.scrollOnCompaniesPage("clientApplicationHeading");
			scrollByCoordinatesofAPage(0, -80);
			LOGGER.info("Scrolled down to clientApplicationHeading ");
			companiesPage.waitForElementsOfCompaniesPage("clientApplicationHeading");
			expectedConnectivityValues.clear();
			expectedConnectivityValues = companiesPage.getTextOfListOfCompaniesDetailPage("connectivitySection");
			Assert.assertTrue(companiesPage.compareTwoListOfCompanyDetailPage("connectivitySection", expectedConnectivityValues), "Client Application is not updated for Reseller.");
			LOGGER.info("Validation of Client Application for Reseller completed successfully.");

			softAssert.assertAll();
			LOGGER.info("Validation of Client Application completed successfully.");

		} finally {
			// Logout from account
			logout();
			// Deleting a Company
			login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
			JSONObject jsonAuthTokenforRoot = (JSONObject) parser.parse(authTokenParse(companiesPage.getTokenFromLocalStorage("dui_token_e")));
			String companyTenantID = jsonAuthTokenforRoot.get("tenant").toString();
			// Remove company through API.
			softAssert.assertTrue(companiesPage.removeAllCompany(environment, companyTenantID, CompaniesTest.companyBody, getEnvironment(environment)), "Company removal failed");
		}
	}

	/**
	 * This test case validates Onboard Authorized Partner Status in Company list and details page:Assigned tab.
	 * 
	 * @throws Exception
	 */
	@Test(priority = 59, groups = { "REGRESSIONCOMPANIES2", "SMOKE", "REGRESSION_CI", "SMOKE_CI" }, description = "313624, Failing due to Bug 313444: [PrtnFieldReqs][UI] Partner Icon is shown in orange color when partner is not certified on Assigned Partner tab", enabled = false)
	public final void verifyOnboardAuthorizedPartnerStatusOnCompanyList() throws Exception {
		login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
		SoftAssert softAssert = new SoftAssert();
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		PartnerPage partnerPage = new PartnerPage(PreDefinedActions.getDriver()).getInstance();
		PartnerDetailsPage partnerDetailsPage = new PartnerDetailsPage(PreDefinedActions.getDriver()).getInstance();

		// Test case1: Check Onbarding Authorized status when partner is
		// certified
		LOGGER.info("Redirected to companies list page");
		gotoPartnersTab();
		LOGGER.info("Redirected to partner page");
		waitForPageLoaded();
		partnerPage.waitUntilElementIsInvisibleOfPartnerPage("tableOverlay");
		companiesPage.clearFiltersOfCompaniesListPage("clearfilter");
		sleeper(4000); // Due to inconsistent UI response we have to put wait
						// here.(Tried many ways but this is the final solution
						// which works as expected.)
		partnerPage.waitForElementsOfPartnerPage("nameSearchBox");
		partnerPage.enterTextForPartnerPage("nameSearchBox", getEnvironmentSpecificData(environment, "AUTOMATION_PARTNER"));
		sleeper(4000);// Due to inconsistent UI response we have to put wait
						// here.(Tried many ways but this is the final solution
						// which works as expected.)
		partnerPage.waitForElementsOfPartnerPage("firstRowPartner");
		// sleeper(2000);
		partnerPage.clickOnElementsOfPartnerPage("firstRowPartner");
		partnerPage.waitForElementsOfPartnerPage("tableOverlay");
		sleeper(4000);

		if (partnerDetailsPage.getAttributesOfPartnerDetailsPage("onboardPartnerToggle", "aria-checked").equals(CommonVariables.TRUE)) {
			softAssert.assertTrue(partnerDetailsPage.matchTextOfPartnerDetailsPage("authorizationLegend", getTextLanguage(LanguageCode, "daas_ui", "global.authorized")), "Authorization legend on identity tile of partner details page is incorrect");

		} else {
			partnerDetailsPage.clickOnElementsOfPartnerDetailsPage("onboardPartnerToggle");
			sleeper(2000);// Due to inconsistent UI response we have to put wait
							// here.(Tried many ways but this is the final
							// solution which works as expected.)
			softAssert.assertTrue(partnerDetailsPage.matchTextOfPartnerDetailsPage("authorizationLegend", getTextLanguage(LanguageCode, "daas_ui", "global.authorized")), "Authorization legend on identity tile of partner details page is incorrect");
		}

		logout();

		// Login to MSP account and verify authorized partner status
		login("NEW_MSP_ADMIN_US_EMAIl", "NEW_MSP_ADMIN_US_PASSWORD");
		gotoCompaniesTab();
		waitForPageLoaded();
		companiesPage.clearFiltersOfCompaniesListPage("clearfilter");
		sleeper(4000);// Due to inconsistent UI response we have to put wait
						// here.(Tried many ways but this is the final solution
						// which works as expected.)
		companiesPage.enterTextForCompaniesPage("companyNameSearch", DeviceVariables.EMM_COMPANY1_NAME);
		Assert.assertFalse(companiesPage.matchTextOfCompaniesPage("companyEmptySearchResult", getTextLanguage(LanguageCode, "daas_ui", "incidents.list.no_items")), "Company not found");
		Assert.assertTrue(companiesPage.matchTextOfCompaniesPage("companySearchResult", DeviceVariables.EMM_COMPANY1_NAME), "company is not found on company search list.");
		String authorizedPartnerIconListColor = companiesPage.getAttributesOfCompaniesPage("authorizedPartnerIcon", "class");
		softAssert.assertTrue(authorizedPartnerIconListColor.contains("orange"), "Color of authorized Partner Icon on compnay list page after authorizing partner is not proper");
		companiesPage.clickByJavaScriptOnCompaniesPage("companySearchResult");
		companiesPage.waitForElementsOfCompaniesPage("companyNameOnDetailsPage");
		Assert.assertTrue(companiesPage.matchTextOfCompaniesPage("companyNameOnDetailsPage", DeviceVariables.EMM_COMPANY1_NAME), "Company name is not present on company details page.");
		companiesPage.clickByJavaScriptOnCompaniesPage("assignedPartner");
		companiesPage.waitForElementsOfCompaniesPage("assignedPartnerIcon");
		String authorizedPartnerIconDetailPageColor = companiesPage.getAttributesOfCompaniesPage("assignedPartnerIcon", "class");
		softAssert.assertTrue(authorizedPartnerIconDetailPageColor.contains("orange"), "Color of authorized Partner Icon on compnay detail page after authorizing partner is not proper");
		LOGGER.info("Verification of Authorized partner on Company list and detail page have passed");

		logout();

		// Test case2: Check Onbarding Authorized status when partner is not
		// certified
		login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
		LOGGER.info("Redirected to companies list page");
		gotoPartnersTab();
		LOGGER.info("Redirected to partner page");
		waitForPageLoaded();
		partnerPage.waitUntilElementIsInvisibleOfPartnerPage("tableOverlay");
		companiesPage.clearFiltersOfCompaniesListPage("clearfilter");
		sleeper(4000);
		partnerPage.waitForElementsOfPartnerPage("nameSearchBox");
		partnerPage.enterTextForPartnerPage("nameSearchBox", getEnvironmentSpecificData(environment, "AUTOMATION_PARTNER"));
		partnerPage.waitForElementsOfPartnerPage("tableOverlay");
		sleeper(4000);// Due to inconsistent UI response we have to put wait
						// here.(Tried many ways but this is the final solution
						// which works as expected.)
		partnerPage.waitForElementsOfPartnerPage("firstRowPartner");
		partnerPage.clickOnElementsOfPartnerPage("firstRowPartner");
		LOGGER.info("Clicked on first partner in the list");
		partnerPage.waitForElementsOfPartnerPage("tableOverlay");
		LOGGER.info("Redirected to partner details page");

		sleeper(4000);// Due to inconsistent UI response we have to put wait
						// here.(Tried many ways but this is the final solution
						// which works as expected.)
		if (partnerDetailsPage.getAttributesOfPartnerDetailsPage("onboardPartnerToggle", "aria-checked").equals(CommonVariables.TRUE)) {
			partnerDetailsPage.clickOnElementsOfPartnerDetailsPage("onboardPartnerToggle");
			partnerDetailsPage.waitForElementsOfPartnerDetailsPage("tableOverlay");
			softAssert.assertTrue(partnerPage.verifyUnauthorizedStatus(), "Unauthorized status is not proper.");
		}
		logout();

		// Login to MSP account and verify unauthorized partner status
		login("NEW_MSP_ADMIN_US_EMAIl", "NEW_MSP_ADMIN_US_PASSWORD");
		gotoCompaniesTab();
		waitForPageLoaded();
		companiesPage.clearFiltersOfCompaniesListPage("clearfilter");
		sleeper(4000);// Due to inconsistent UI response we have to put wait
						// here.(Tried many ways but this is the final solution
						// which works as expected.)
		companiesPage.enterTextForCompaniesPage("companyNameSearch", DeviceVariables.EMM_COMPANY1_NAME);
		Assert.assertFalse(companiesPage.matchTextOfCompaniesPage("companyEmptySearchResult", getTextLanguage(LanguageCode, "daas_ui", "incidents.list.no_items")), "Company not found");
		Assert.assertTrue(companiesPage.matchTextOfCompaniesPage("companySearchResult", DeviceVariables.EMM_COMPANY1_NAME), "company is not found on company search list.");
		String unauthorizedPartnerIconListColor = companiesPage.getAttributesOfCompaniesPage("authorizedPartnerIcon", "class");
		softAssert.assertTrue(unauthorizedPartnerIconListColor.contains("black"), "Color of unauthorized Partner Icon on company list page after unauthorizing partner is not proper");
		companiesPage.clickByJavaScriptOnCompaniesPage("companySearchResult");
		companiesPage.waitForElementsOfCompaniesPage("companyNameOnDetailsPage");
		Assert.assertTrue(companiesPage.matchTextOfCompaniesPage("companyNameOnDetailsPage", DeviceVariables.EMM_COMPANY1_NAME), "Company name is not present on company details page.");
		companiesPage.clickByJavaScriptOnCompaniesPage("assignedPartner");
		companiesPage.waitForElementsOfCompaniesPage("assignedPartnerIcon");
		String unauthorizedPartnerIconDetailPageColor = companiesPage.getAttributesOfCompaniesPage("assignedPartnerIcon", "class");
		softAssert.assertTrue(unauthorizedPartnerIconDetailPageColor.contains("black"), "Color of unauthorized Partner Icon on company detail page after unauthorizing partner is not proper");

		softAssert.assertAll();
		LOGGER.info("Verification of Unauthorized partner on Company list and detail page have passed");
	}

	/**
	 * This test case validates key information update on subscription order page
	 * 
	 * @throws Exception
	 */
	@Test(priority = 60, groups = { "REGRESSIONCOMPANIES2", "REGRESSION_CI, REGRESSION" }, description = "TC 323694")
	public final void verifyKeyInfoUpdateOnSubscriptionOrdersPage() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		String country, updatedCountry;
		String updatedCity = generateRandomString(11);
		String updatedState = generateRandomString(11);
		login("RESELLER_NEW_UI_Companies_EMAIL_COMPANIES", "RESELLER_NEW_UI_Companies_PASSWORD_COMPANIES");
		gotosubscriptionOrdersTab();
		LOGGER.info("Redirected to subscription key list page");
		waitForPageLoaded();
		resetTableConfiguration();
		LicenseOrdersPage subscriptionOrdersPage = new LicenseOrdersPage(PreDefinedActions.getDriver()).getInstance();
		CompaniesDetailsPage companiesDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver()).getInstance();
		subscriptionOrdersPage.scrollOnSubscriptionOrdersListPage("firstKeyCountry");
		if (subscriptionOrdersPage.verifyElementsOfSubscriptionOrdersPage("firstKeyCountry")) {
			country = subscriptionOrdersPage.getTextOfSubscriptionOrdersPage("firstKeyCountry");
			subscriptionOrdersPage.clickOnElementsOfSubscriptionOrdersPage("CompanyDropDownArrowOpen");
			subscriptionOrdersPage.waitForElementsOfSubscriptionOrdersPage("CompanySearchBox");
			subscriptionOrdersPage.enterTextForSubscriptionOrderPage("CompanySearchBox",CompaniesVariables.COMPANYNAME);
			Thread.sleep(2000);
			subscriptionOrdersPage.clickOnElementsOfSubscriptionOrdersPage("SelectedElement");
			pressKey(Keys.ESCAPE);
			subscriptionOrdersPage.scrollOnSubscriptionOrdersListPage("firstKeyCountry");
			subscriptionOrdersPage.clickOnElementsOfSubscriptionOrdersPage("firstKeyCompany");
			LOGGER.info("Redirected to companies details page");
			companiesDetailsPage.waitUntilElementIsInvisibleOfCompanyDetailsPage("tableOverlay");
			companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("editCompanyaddress");
			companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("countryDropdown");
			updatedCountry = companiesDetailsPage.selectFirstOptionFromDropdownOnCompaniesDetailsPage("countryDropdownOptions", country);
			companiesDetailsPage.enterTextForCompaniesDetailsPage("cityNameOnPopup", updatedCity);
			companiesDetailsPage.enterTextForCompaniesDetailsPage("stateNameOnPopup", updatedState);
			companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("addressSaveButton");
			LOGGER.info("Updated address");
			companiesDetailsPage.waitForElementsOfCompaniesDetailsPage("tableOverlay");

			gotosubscriptionOrdersTab();
			LOGGER.info("Redirected to subscription key list page");
			subscriptionOrdersPage.waitForElementsOfSubscriptionOrdersPage("tableOverlay");
			subscriptionOrdersPage.scrollOnSubscriptionOrdersListPage("firstKeyCountry");
			softAssert.assertTrue(subscriptionOrdersPage.getTextOfSubscriptionOrdersPage("firstKeyCountry").equals(updatedCountry), "Updated country is not reflected on subscription orders page");
			subscriptionOrdersPage.scrollOnSubscriptionOrdersListPage("firstKeyState");
			softAssert.assertTrue(subscriptionOrdersPage.getTextOfSubscriptionOrdersPage("firstKeyState").equals(updatedState), "Updated state is not reflected on subscription orders page");
			subscriptionOrdersPage.scrollOnSubscriptionOrdersListPage("firstKeyCity");
			softAssert.assertTrue(subscriptionOrdersPage.getTextOfSubscriptionOrdersPage("firstKeyCity").equals(updatedCity), "Updated city is not reflected on subscription orders page");
		} else
			LOGGER.info("No key present on this page, cannot proceed further");
		softAssert.assertAll();
		LOGGER.info("All test cases of verifyKeyInfoUpdateOnSubscriptionOrdersPage passed successsfully");
	}

	/**
	 * This test case validates Business Identification Tile in Company details page.
	 * 
	 * @throws Exception
	 */
	@Test(priority = 61, groups = { "REGRESSIONCOMPANIES2", "SMOKE", "REGRESSION_CI", "SMOKE_CI" }, description = "323214,323163,325714,323213",enabled = false)
	public final void verifyBusinessIdentificationTileOnCompanydetailsPage() throws Exception {
		login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
		SoftAssert softAssert = new SoftAssert();
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		String orgID, opsiID, dunsNo, orgID2, supportSpecilaistOrgID;

		// Login to Root account
		waitForPageLoaded();
		resetTableConfiguration();
		impersonateCompanyByCompanyName(emmCompanyName);

		// Test Case1: Verify Business Identification Tile
		waitForPageLoaded();
		companiesPage.scrollOnCompaniesPage("businessIdentificationHeading");
		scrollByCoordinatesofAPage(0, -80);
		LOGGER.info("Scrolled down to businessIdentificationHeading ");
		companiesPage.waitForElementsOfCompaniesPage("businessIdentificationHeading");
		softAssert.assertTrue(companiesPage.getTextOfCompaniesPage("businessIdentificationHeading").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.business_id")), "Business Identification heading does not match.");
		softAssert.assertTrue(companiesPage.getTextOfCompaniesPage("organizationIdLabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.business_id.org_id")), "Organization ID label does not match.");
		softAssert.assertTrue(companiesPage.getTextOfCompaniesPage("opsiSystemsIDLabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.business_id.opsi_id")), "OPSI System ID label does not match.");
		softAssert.assertTrue(companiesPage.getTextOfCompaniesPage("dunsNumberLabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.business_id.duns_no")), "DUNS Number label does not match.");
		LOGGER.info("Verification of Business Identification label completed successfully.");

		// Test Case2: Verify edit functionality-orgID
		companiesPage.waitForElementsOfCompaniesPage("editOrgId");
		companiesPage.clickOnElementsOfCompaniesPage("editOrgId");
		companiesPage.waitForElementsOfCompaniesPage("orgIdHeader");
		softAssert.assertTrue(companiesPage.getTextOfCompaniesPage("orgIdHeader").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.business_id.org_id")), "Organization ID heading does not match.");
		softAssert.assertTrue(companiesPage.getTextOfCompaniesPage("orgIdLabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.business_id.org_id") + " (" + getTextLanguage(LanguageCode, "daas_ui", "global.numeric_only") + ")"), "Organization ID label on pop-up does not match.");
		orgID = generateRandomNumber();
		companiesPage.enterTextForCompaniesPage("orgIDInputBox", orgID);
		companiesPage.waitForElementsOfCompaniesPage("busiIdentiifcationSaveBtn");
		companiesPage.clickOnElementsOfCompaniesPage("busiIdentiifcationSaveBtn");
		companiesPage.waitForElementsOfCompaniesPage("busiIdentificationSuccessNotiifcation");
		softAssert.assertTrue(companiesPage.getTextOfCompaniesPage("busiIdentificationSuccessNotiifcation").equals(getTextLanguage(LanguageCode, "daas_ui", "companies.details.update.success").replace("{name}", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.business_id.org_id"))), "Success Notification is not displayed for Organization ID.");
		companiesPage.clickOnElementsOfCompaniesPage("busiIdentificationCloseNotiifcation");
		LOGGER.info("Validation of Edit functinality for Organization ID completed successfully.");

		// Verify edit functionality -opsi id
		companiesPage.waitForElementsOfCompaniesPage("editOpsiId");
		companiesPage.clickOnElementsOfCompaniesPage("editOpsiId");
		companiesPage.waitForElementsOfCompaniesPage("orgIdHeader");
		softAssert.assertTrue(companiesPage.getTextOfCompaniesPage("orgIdHeader").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.business_id.opsi_id")), "OPSI System ID heading does not match.");
		softAssert.assertTrue(companiesPage.getTextOfCompaniesPage("orgIdLabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.business_id.opsi_id") + " (" + getTextLanguage(LanguageCode, "daas_ui", "global.numeric_only") + ")"), "OPSI System ID label on pop-up does not match.");
		opsiID = generateRandomNumber();
		companiesPage.enterTextForCompaniesPage("opsiIDInputBox", opsiID);
		companiesPage.waitForElementsOfCompaniesPage("busiIdentiifcationSaveBtn");
		companiesPage.clickOnElementsOfCompaniesPage("busiIdentiifcationSaveBtn");
		companiesPage.waitForElementsOfCompaniesPage("busiIdentificationSuccessNotiifcation");
		softAssert.assertTrue(companiesPage.getTextOfCompaniesPage("busiIdentificationSuccessNotiifcation").equals(getTextLanguage(LanguageCode, "daas_ui", "companies.details.update.success").replace("{name}", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.business_id.opsi_id"))), "Success Notification is not displayed for OPSI System ID.");
		companiesPage.clickOnElementsOfCompaniesPage("busiIdentificationCloseNotiifcation");
		LOGGER.info("Validation of Edit functinality for OPSI System ID completed successfully.");

		// Verify edit functionality -DUNS id
		companiesPage.waitForElementsOfCompaniesPage("editDunsNo");
		companiesPage.clickOnElementsOfCompaniesPage("editDunsNo");
		companiesPage.waitForElementsOfCompaniesPage("orgIdHeader");
		softAssert.assertTrue(companiesPage.getTextOfCompaniesPage("orgIdHeader").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.business_id.duns_no")), "DUNS Number heading does not match.");
		softAssert.assertTrue(companiesPage.getTextOfCompaniesPage("orgIdLabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.business_id.duns_no") + " (" + getTextLanguage(LanguageCode, "daas_ui", "global.numeric_only") + ")"), "DUNS Number label on pop-up does not match.");
		dunsNo = generateRandomNumber();
		companiesPage.enterTextForCompaniesPage("dunsInputBox", dunsNo);
		companiesPage.waitForElementsOfCompaniesPage("busiIdentiifcationSaveBtn");
		companiesPage.clickOnElementsOfCompaniesPage("busiIdentiifcationSaveBtn");
		companiesPage.waitForElementsOfCompaniesPage("busiIdentificationSuccessNotiifcation");
		softAssert.assertTrue(companiesPage.getTextOfCompaniesPage("busiIdentificationSuccessNotiifcation").equals(getTextLanguage(LanguageCode, "daas_ui", "companies.details.update.success").replace("{name}", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.business_id.duns_no"))), "Success Notification is not displayed for DUNS Number.");
		companiesPage.clickOnElementsOfCompaniesPage("busiIdentificationCloseNotiifcation");
		LOGGER.info("Validation of Edit functinality for DUNS Number completed successfully.");

		// Test Case 3: Verify Cancel Functionality of Business Identification
		companiesPage.waitForElementsOfCompaniesPage("editOrgId");
		companiesPage.clickOnElementsOfCompaniesPage("editOrgId");
		companiesPage.waitForElementsOfCompaniesPage("orgIdHeader");
		orgID2 = generateRandomNumber();
		companiesPage.enterTextForCompaniesPage("orgIDInputBox", orgID2);
		companiesPage.waitForElementsOfCompaniesPage("busiIdentiifcationCancelBtn");
		companiesPage.clickOnElementsOfCompaniesPage("busiIdentiifcationCancelBtn");
		softAssert.assertTrue(companiesPage.getTextOfCompaniesPage("organIdText").equals(orgID), "Organization ID value does not match after cancel.");
		LOGGER.info("Validation of Cancel functinality for Organization ID completed successfully.");
		logout();

		// Test Case 4: Verify Business Identification tile on MSP Admin
		// Login to MSP account
		login("NEW_MSP_ADMIN_US_EMAIl", "NEW_MSP_ADMIN_US_PASSWORD");
		gotoCompaniesTab();
		waitForPageLoaded();
		impersonateCompanyByCompanyName(emmCompanyName);
		waitForPageLoaded();
		companiesPage.scrollOnCompaniesPage("businessIdentificationHeading");
		scrollByCoordinatesofAPage(0, -80);
		LOGGER.info("Scrolled down to businessIdentificationHeading ");
		softAssert.assertTrue(companiesPage.getTextOfCompaniesPage("organIdText").equals(orgID), "Organization ID value does not match in MSP company detail screen.");
		softAssert.assertTrue(companiesPage.getTextOfCompaniesPage("opsiIdText").equals(opsiID), "OPSI ID value does not match in MSP company detail screen..");
		softAssert.assertTrue(companiesPage.getTextOfCompaniesPage("dunsNoText").equals(dunsNo.substring(0, dunsNo.length() - 1)), "DUNS Number value does not match in MSP company detail screen.");
		LOGGER.info("Validation of Busniess Identification values in MSP completed successfully.");
		logout();

		// Test Case 5: Verify Business Identification tile for Support
		// Specialist
		// Login to Root account
		login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
		waitForPageLoaded();
		resetTableConfiguration();
		impersonateCompanyByCompanyName(emmCompanyName);

		companiesPage.waitForElementsOfCompaniesPage("editOrgId");
		companiesPage.clickOnElementsOfCompaniesPage("editOrgId");
		companiesPage.waitForElementsOfCompaniesPage("orgIdHeader");
		companiesPage.clearTextOfCompaniesPage("orgIDInputBox");
		supportSpecilaistOrgID = generateRandomNumber();
		companiesPage.enterTextForCompaniesPage("orgIDInputBox", supportSpecilaistOrgID);
		companiesPage.waitForElementsOfCompaniesPage("busiIdentiifcationSaveBtn");
		companiesPage.clickOnElementsOfCompaniesPage("busiIdentiifcationSaveBtn");
		companiesPage.waitForElementsOfCompaniesPage("busiIdentificationSuccessNotiifcation");
		softAssert.assertTrue(companiesPage.getTextOfCompaniesPage("busiIdentificationSuccessNotiifcation").equals(getTextLanguage(LanguageCode, "daas_ui", "companies.details.update.success").replace("{name}", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.business_id.org_id"))), "Success Notification is not displayed for Organization ID.");
		companiesPage.clickOnElementsOfCompaniesPage("busiIdentificationCloseNotiifcation");
		LOGGER.info("Validation of Edit functinality for Organization ID for support specialist completed successfully.");

		// Verify edit functionality -opsi id
		companiesPage.waitForElementsOfCompaniesPage("editOpsiId");
		companiesPage.clickOnElementsOfCompaniesPage("editOpsiId");
		companiesPage.waitForElementsOfCompaniesPage("orgIdHeader");
		companiesPage.clearTextOfCompaniesPage("opsiIDInputBox");
		companiesPage.waitForElementsOfCompaniesPage("busiIdentiifcationSaveBtn");
		companiesPage.clickOnElementsOfCompaniesPage("busiIdentiifcationSaveBtn");
		companiesPage.waitForElementsOfCompaniesPage("busiIdentificationSuccessNotiifcation");
		softAssert.assertTrue(companiesPage.getTextOfCompaniesPage("busiIdentificationSuccessNotiifcation").equals(getTextLanguage(LanguageCode, "daas_ui", "companies.details.update.success").replace("{name}", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.business_id.opsi_id"))), "Success Notification is not displayed for OPSI System ID.");
		companiesPage.clickOnElementsOfCompaniesPage("busiIdentificationCloseNotiifcation");
		LOGGER.info("Validation of Edit functinality for OPSI System ID for support specilaist completed successfully.");
		logout();

		// Login to Support Specialist account
		login("SUPPORT_SPECIALIST_MSPUSER_NEW_EMAIL", "SUPPORT_SPECIALIST_MSPUSER_NEW_PASSWORD");
		gotoCompaniesTab();
		waitForPageLoaded();
		resetTableConfiguration();
		impersonateCompanyByCompanyName(emmCompanyName);

		// Test Case6: Verify Business Identification Tile
		waitForPageLoaded();
		companiesPage.scrollOnCompaniesPage("businessIdentificationHeading");
		scrollByCoordinatesofAPage(0, -80);
		LOGGER.info("Scrolled down to businessIdentificationHeading ");
		companiesPage.waitForElementsOfCompaniesPage("businessIdentificationHeading");
		softAssert.assertTrue(companiesPage.getTextOfCompaniesPage("businessIdentificationHeading").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.business_id")), "Business Identification heading does not match.");
		softAssert.assertTrue(companiesPage.getTextOfCompaniesPage("organizationIdLabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.business_id.org_id")), "Organization ID label does not match.");
		softAssert.assertTrue(companiesPage.getTextOfCompaniesPage("opsiSystemsIDLabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.business_id.opsi_id")), "OPSI System ID label does not match.");
		softAssert.assertTrue(companiesPage.getTextOfCompaniesPage("dunsNumberLabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.business_id.duns_no")), "DUNS Number label does not match.");
		LOGGER.info("Verification of Business Identification Tile for support specialist completed successfully.");

		waitForPageLoaded();
		companiesPage.scrollOnCompaniesPage("businessIdentificationHeading");
		scrollByCoordinatesofAPage(0, -80);
		LOGGER.info("Scrolled down to businessIdentificationHeading ");
		softAssert.assertTrue(companiesPage.getTextOfCompaniesPage("organIdText").equals(supportSpecilaistOrgID), "Organization ID value does not match in Support Specialist company detail screen.");
		softAssert.assertTrue(companiesPage.getTextOfCompaniesPage("opsiIdText").equals("-"), "OPSI ID value does not match in Support Specialist company detail screen..");
		softAssert.assertTrue(companiesPage.getTextOfCompaniesPage("dunsNoText").equals(dunsNo.substring(0, dunsNo.length() - 1)), "DUNS Number value does not match in Support Specialist company detail screen.");
		LOGGER.info("Validation of Business Identification values in Support Specilasit completed successfully.");

		softAssert.assertAll();
		LOGGER.info("Verification of Business Identification Tile page have passed");
	}

	/**
	 * Verifying proactive security type/ subtype when proactive seurity toggle ON/Off for premium company.
	 */
	@Test(priority = 62, groups = { "REGRESSIONCOMPANIES2", "REGRESSION", "SMOKE", "REGRESSION_CI", "SMOKE_CI" }, description = "Test Case 324022: Verify that sure sense subtypes are hidden/shown when proactive security flag value changes for premium package", enabled = false)
	public final void verifySecuritySubtypesForPremiumPackage() throws Exception {
		login("MSP_EMAIL_NOTIFICATION_EMAIL", "MSP_EMAIL_NOTIFICATION_PASSWORD");
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		CompaniesDetailsPage companyDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		gotoCompaniesTab();
		companiesPage.clearFiltersOfCompaniesListPage("clearfilter");
		sleeper(2000);
		companiesPage.enterTextForCompaniesPage("companyNameSearch", getEnvironmentSpecificData(System.getProperty("environment"), "COMPANY_NAME_BLACKBOX"));
		sleeper(3000);
		companiesPage.waitForElementsOfCompaniesPage("companySearchResult");
		companiesPage.clickByJavaScriptOnCompaniesPage("companySearchResult");
		companiesPage.waitForElementsOfCompaniesPage("companyNameOnDetailsPage");
		companiesPage.clickOnElementsOfCompaniesPage("rootPreferencesPage");
		companiesPage.verifyElementsOfCompaniesPage("verifyReportText");
		softAssert.assertTrue(companiesPage.waitForElementsOfCompaniesPage("verifyProactiveSecurityReportsToggle"), "Proactive Security Reports Toggle is not visible");
		companiesPage.clickOnElementsOfCompaniesPage("verifyProactiveSecurityReportsToggle");
		softAssert.assertTrue(companiesPage.getTextOfCompaniesPage("verifyProactiveSecurityReportsStatus").equals(getTextLanguage(LanguageCode, "daas_ui", "settings.data.enabled")), "Proactive Security status is not enabled");
		companiesPage.refreshPage();
		companyDetailsPage.verifyElementsOfCompaniesDetailsPage("incidentTileHeader");
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("editCompanyIncidentSubscriptions");
		companyDetailsPage.verifyElementIsClickableOfCompaniesDetailsPage("incidentSubscriptionPopUpHeader");

		// Verifying proactive security type and proactive security subtype-
		// Sure Sense Disabled, Sure Sense Threat Prevented, Sure click- True
		// Positive Threat Detected , SureClick- Unprotected Device are present
		// or not
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("securityTypeTextPremium").toLowerCase().equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.type.security")), "Verified Security type");

		companyDetailsPage.verifyElementIsClickableOfCompaniesDetailsPage("securityTypeEditPremium");
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("securityTypeEditPremium");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("securitySureSenseSubtypeText").equalsIgnoreCase(getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.subtype.suresenseproClientdisabled")), "security Sure Sense Subtype Text is not displayed in incident subscription when proactive security is enabled.");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("securityThreatPreventedSubtypeText").equalsIgnoreCase(getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.subtype.suresenseprothreatprevented")), "security Threat Prevented Subtype Text is not displayed in incident subscription when proactive security is enabled.");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("sureClickTruePossitiveSubtypeText").equalsIgnoreCase(getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.subtype.truepositive1")), "Sure Click - True positive threat detected Text is not displayed in incident subscription when proactive security toggle is disabled");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("sureClickUnprotectedSubtypeText").equalsIgnoreCase(getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.title.bromium2")), "Sure Click - Unprotected Device Text is not displayed in incident subscription when proactive security toggle is disabled");

		// predictive type is removed from UI, so commenting line for now. BUG
		// 375147, BUG 364089
		// Verifying predictive type and there proactive security subtype- Sure
		// Sense Disabled, Sure Sense Threat Prevented, Sure click- True
		// Positive Threat Detected , SureClick- Unprotected Device are present
		// or not
		// softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("predictiveTypeText").toLowerCase().equalsIgnoreCase(getTextLanguage(LanguageCode,
		// "MPI-Incident-management-service-backend-Properties",
		// "com.hp.mpi.icm.type.predictive")), "Verified Predictive type");
		// companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("predictiveTypeEdit");
		// softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("predictiveSureSenseSubtypeText").equalsIgnoreCase(getTextLanguage(LanguageCode,
		// "MPI-Incident-management-service-backend-Properties","com.hp.mpi.icm.subtype.suresensedisabled")),
		// "security Sure Sense Subtype Text is not displayed in incident
		// subscription when proactive security toggle is disabled.");
		// softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("predictiveThreatPreventedSubtypeText").equalsIgnoreCase(getTextLanguage(LanguageCode,
		// "MPI-Incident-management-service-backend-Properties","com.hp.mpi.icm.subtype.suresensethreatprevented")),
		// "security Threat Prevented Subtype Text is not displayed in incident
		// subscription when proactive security toggle is disabled.");
		// softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("predictiveSureClickTruePossitiveSubtypeText").equals(getTextLanguage(LanguageCode,
		// "MPI-Incident-management-service-backend-Properties","com.hp.mpi.icm.subtype.truepositive")),
		// "security Threat Prevented Subtype Text is not displayed in incident
		// subscription when proactive security toggle is disabled.");
		// softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("predictiveSureClickUnprotectedSubtypeText").equals(getTextLanguage(LanguageCode,
		// "MPI-Incident-management-service-backend-Properties","com.hp.mpi.icm.subtype.bromium")),
		// "security Threat Prevented Subtype Text is not displayed in incident
		// subscription when proactive security toggle is disabled.");
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("incidentSubscriptionSave");
		companiesPage.waitForElementsOfCompaniesPage("verifyProactiveSecurityReportsToggle");
		companiesPage.clickOnElementsOfCompaniesPage("verifyProactiveSecurityReportsToggle");
		softAssert.assertTrue(companiesPage.getTextOfCompaniesPage("verifyProactiveSecurityReportsStatus").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "settings.data.disabled")), "Proactive Security status is not disabled");
		companiesPage.refreshPage();
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("editCompanyIncidentSubscriptions");

		// Verifying security type and there proactive security subtype- Sure
		// Sense Advance Disabled, Sure Sense Advanced – Threat Prevented, Sure
		// click- True Positive, SureClick- Unprotected are present or not
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("securityTypeEditPremium");
		softAssert.assertFalse(companyDetailsPage.waitForElementsOfCompaniesDetailsPage("securitySureSenseSubtypeText"), "Sure Sense is displayed in incident subscription when proactive security toggle is enabled");
		softAssert.assertFalse(companyDetailsPage.waitForElementsOfCompaniesDetailsPage("securityThreatPreventedSubtypeText"), "Security Threat Prevented Subtype Text is displayed in incident subscription when proactive security toggle is enabled");
		softAssert.assertFalse(companyDetailsPage.waitForElementsOfCompaniesDetailsPage("sureClickTruePossitiveSubtypeText"), "Sure Click True Possitive Subtype Text is displayed in incident subscription when proactive security toggle is enabled");
		softAssert.assertFalse(companyDetailsPage.waitForElementsOfCompaniesDetailsPage("sureClickUnprotectedSubtypeText"), "sure Click Unprotected Subtype Text is displayed in incident subscription when proactive security toggle is enabled");

		// //Verifying predictive type and there proactive security subtype-
		// Sure Sense Advance Disabled, Sure Sense Advanced – Threat Prevented,
		// Sure click- True Positive Threat Detected , SureClick- Unprotected
		// Device are present or not
		// companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("predictiveTypeEdit");
		// softAssert.assertFalse(companyDetailsPage.waitForElementsOfCompaniesDetailsPage("predictiveSureSenseSubtypeText"),"Sure
		// sense Subtype Text is displayed in predective incident subscription
		// when proactive security toggle is enabled");
		// softAssert.assertFalse(companyDetailsPage.waitForElementsOfCompaniesDetailsPage("predictiveThreatPreventedSubtypeText"),"Sure
		// Sense Advanced – Threat Prevented Subtype Text is displayed in
		// predective incident subscription when proactive security toggle is
		// enabled");
		// softAssert.assertFalse(companyDetailsPage.waitForElementsOfCompaniesDetailsPage("predictiveSureClickTruePossitiveSubtypeText"),"Sure
		// Click True Possitive Subtype Text is displayed in predective incident
		// subscription when proactive security toggle is enabled");
		// softAssert.assertFalse(companyDetailsPage.waitForElementsOfCompaniesDetailsPage("predictiveSureClickUnprotectedSubtypeText"),
		// "Sure Click Unprotected Subtype Text is displayed in incident
		// subscription when proactive security toggle is enabled");
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("incidentSubscriptionSave");
		softAssert.assertAll();
		LOGGER.info("User is able to verify blakbox and bromium subtype by enabling/disabling proactive seurity report toggle for primium company");
	}

	/**
	 * Verifying proactive security type/ subtype when proactive security ON/OFF for standard company.
	 */
	@Test(priority = 63, groups = { "REGRESSIONCOMPANIES2", "REGRESSION", "SMOKE", "REGRESSION_CI", "SMOKE_CI" }, description = "Test Case 324023: Verify that sure sense subtypes are hidden/shown when proactive security flag value changes for standard package", enabled = false)
	public final void verifySecuritySubtypesForStandardPackage() throws Exception {
		login("MSP_EMAIL_NOTIFICATION_EMAIL", "MSP_EMAIL_NOTIFICATION_PASSWORD");
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		CompaniesDetailsPage companyDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		gotoCompaniesTab();
		companiesPage.clearFiltersOfCompaniesListPage("clearfilter");
		sleeper(2000);
		companiesPage.waitForElementsOfCompaniesPage("companyNameSearch");
		companiesPage.enterTextForCompaniesPage("companyNameSearch", getEnvironmentSpecificData(System.getProperty("environment"), "STANDARD_COMPANY"));
		sleeper(3000);
		companiesPage.clickByJavaScriptOnCompaniesPage("companySearchResult");
		companiesPage.waitForElementsOfCompaniesPage("companyNameOnDetailsPage");
		companiesPage.clickOnElementsOfCompaniesPage("rootPreferencesPage");
		companiesPage.verifyElementsOfCompaniesPage("verifyReportText");
		softAssert.assertTrue(companiesPage.waitForElementsOfCompaniesPage("verifyProactiveSecurityReportsToggle"), "Proactive Security Reports Toggle is not visible");
		companiesPage.clickOnElementsOfCompaniesPage("verifyProactiveSecurityReportsToggle");
		softAssert.assertTrue(companiesPage.getTextOfCompaniesPage("verifyProactiveSecurityReportsStatus").equals(getTextLanguage(LanguageCode, "daas_ui", "settings.data.enabled")), "Proactive Security status is not enabled");
		companiesPage.refreshPage();
		companyDetailsPage.verifyElementsOfCompaniesDetailsPage("incidentTileHeader");
		companyDetailsPage.scrollOnCompaniesDetailsPage("incidentTileHeader");
		companyDetailsPage.clickByJavaScriptOnCompaniesDetailsPage("editCompanyIncidentSubscriptions");
		companyDetailsPage.verifyElementIsClickableOfCompaniesDetailsPage("incidentSubscriptionPopUpHeader");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("securityTypeText").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.type.security")), "Verified Security type");

		// Verifying security type and there proactive security subtype- Sure
		// Sense Advanced Disabled, Sure Sense Advanced – Threat Prevented, Sure
		// click- True Positive Threat Detected , SureClick- Unprotected Device,
		// Antivirus , Firewall are present or not
	
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("securityTypeEdit");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("securitySureSenseSubtypeText").equalsIgnoreCase(getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.subtype.suresenseproClientdisabled")), "security Sure Sense Subtype Text is not displayed in incident subscription when proactive security is enabled.");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("securityThreatPreventedSubtypeText").equalsIgnoreCase(getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.subtype.suresenseprothreatprevented")), "security Threat Prevented Subtype Text is not displayed in incident subscription when proactive security is enabled.");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("sureClickTruePossitiveSubtypeText").equalsIgnoreCase(getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.subtype.truepositive1")), "Sure Click - True positive threat detected Text is not displayed in incident subscription when proactive security is enabled.");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("sureClickUnprotectedSubtypeText").equalsIgnoreCase(getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.title.bromium2")), "Sure Click - Unprotected Device Text is not displayed in incident subscription when proactive security is enabled.");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("antivirusSubtypeText").equalsIgnoreCase(getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.subtype.antivirus")), "Antivirus Text is not displayed in incident subscription when proactive security is enabled.");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("firewallSubtypeText").equalsIgnoreCase(getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.subtype.firewall")), "FireWall Text is not displayed in incident subscription when proactive security is enabled.");
		String firewall= companyDetailsPage.getTextOfCompaniesDetailsPage("firewallSubtypeText");
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("incidentSubscriptionSave");
		companiesPage.waitForElementsOfCompaniesPage("verifyProactiveSecurityReportsToggle");
		companiesPage.clickOnElementsOfCompaniesPage("verifyProactiveSecurityReportsToggle");
		softAssert.assertTrue(companiesPage.getTextOfCompaniesPage("verifyProactiveSecurityReportsStatus").equals(getTextLanguage(LanguageCode, "daas_ui", "settings.data.disabled")), "Proactive Security status is not disabled");
		companiesPage.refreshPage();
		companyDetailsPage.verifyElementsOfCompaniesDetailsPage("incidentTileHeader");
		companyDetailsPage.clickByJavaScriptOnCompaniesDetailsPage("editCompanyIncidentSubscriptions");
		companyDetailsPage.verifyElementIsClickableOfCompaniesDetailsPage("incidentSubscriptionPopUpHeader");

		// Verifying security type and there proactive security subtype- Sure
		// Sense Advanced Disabled, Sure Sense Advanced – Threat Prevented, Sure
		// Sure Click Pro - Threat Prevented , Sure Click Pro - Client Disabled,
		// Antivirus, Firewall are present or not
		
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("securityTypeEdit");
		softAssert.assertFalse(companyDetailsPage.waitForElementsOfCompaniesDetailsPage("securitySureSenseSubtypeText"), "Sure Sense subtype is displayed in incident subscription when proactive security is disable");
		softAssert.assertFalse(companyDetailsPage.waitForElementsOfCompaniesDetailsPage("securityThreatPreventedSubtypeText"), "security Threat Prevented Subtype Text is not displayed in incident subscription when proactive security is disable.");
		softAssert.assertFalse(companyDetailsPage.waitForElementsOfCompaniesDetailsPage("sureClickTruePossitiveSubtypeText"), "Sure Click - True positive threat detected Text is not displayed in incident subscription when proactive security is disable");
		softAssert.assertFalse(companyDetailsPage.waitForElementsOfCompaniesDetailsPage("sureClickUnprotectedSubtypeText"), "Sure Click - Unprotected Device Text is not displayed in incident subscription when proactive security is disable");
		softAssert.assertFalse(companyDetailsPage.waitForElementsOfCompaniesDetailsPage("antivirusSubtypeText"), "Antivirus subtype is displayed in incident subscription when proactive security is disable");
		softAssert.assertFalse(companyDetailsPage.waitForElementsOfCompaniesDetailsPage("firewallSubtypeText"), "Firewall subtype is displayed in incident subscription when proactive security is disable");
		softAssert.assertAll();
		LOGGER.info("User is able to Verify blakbox and bromium subtype by enabling/disabling proactive seurity report toggle for standard company");
	}

	/**
	 * Prerequisite:Login with company and proactive security toggle should be enabled Verifying, enhance company having proactive security toggle is enabled and able to create
	 * incident.
	 */
	@Test(priority = 64, groups = { "REGRESSIONCOMPANIES2", "REGRESSION", "SMOKE", "REGRESSION_CI", "SMOKE_CI" }, description = "Test Case 324021", enabled = false)
	public final void verifyBlackboxIndidentForReportAdminRole() throws Exception {
		login("REPORT_BLACKBOX_EMAIL", "REPORT_BLACKBOX_PASSWORD");
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		CompaniesDetailsPage companyDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver()).getInstance();
		DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
		DeviceDetailsPage deviceDetailsPage = new DeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
		IncidentPage incidentPage = new IncidentPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		gotoSettingsTabOfReportAdmin();
		sleeper(2000);
		String companyName = companiesPage.getTextOfCompaniesPage("companyNameShow");
		String companyEmailId = companiesPage.getTextOfCompaniesPage("companyEmailId");
		goToPreferencesTab();
		CompanyPin companypin = new CompanyPin();
		sleeper(2000);
		String companyPin = companypin.getCompanyPin();
		companyDetailsPage.verifyElementsOfCompaniesDetailsPage("incidentTileHeader");
		companyDetailsPage.clickByJavaScriptOnCompaniesDetailsPage("editCompanyIncidentSubscriptions");
		companyDetailsPage.verifyElementIsClickableOfCompaniesDetailsPage("incidentSubscriptionPopUpHeader");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("securityTypeText").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.type.security")), "Verified Security type");
		if(System.getProperty("uiVersion").equalsIgnoreCase("VENEER2")){
			companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("securityTypeEdit");
		}
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("securitySureSenseSubtypeText").equalsIgnoreCase(getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.subtype.suresenseproClientdisabled")), "security Sure Sense Subtype Text is not displayed in incident subscription when proactive security toggle is disabled.");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("securityThreatPreventedSubtypeText").equalsIgnoreCase(getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.subtype.suresenseprothreatprevented")), "security Threat Prevented Subtype Text is not displayed in incident subscription when proactive security toggle is disabled.");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("sureClickTruePossitiveSubtypeText").equalsIgnoreCase(getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.title.truepositive2")), "Sure Click Pro - Client Disabled threat detected Text is not displayed in incident subscription when proactive security toggle is disabled");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("sureClickUnprotectedSubtypeText").equalsIgnoreCase(getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.title.bromium2")), "Sure Click Pro - Threat Prevented Text is not displayed in incident subscription when proactive security toggle is disabled");
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("incidentSubscriptionSave");
		HashMap<String, String> deviceDetails = new HashMap<>();
		deviceDetails = EnrollFakeDevice.enrollFakeDeviceForIncident(companyName, companyPin, companyEmailId);
		Assert.assertTrue(!deviceDetails.isEmpty(), "Fake device couldnt be enrolled");
		String deviceSerialNumber = deviceDetails.get("deviceSerialNumber");
		LOGGER.info(deviceSerialNumber + " fake Device is Enrolled Successfully");
		gotoDevicesTab();
		sleeper(5000);// after click on device tab
		deviceListPage.waitForElementsOfDeviceListPage("deviceListTable");
		resetTableConfiguration();
		sleeper(2000);
		deviceListPage.clickOnElementsOfDeviceListPage("removeAllSearchFilters");
		sleeper(2000);
		deviceListPage.enterTextForDeviceListPage("serialNumberSearchBox", deviceSerialNumber);
		sleeper(2000);
		deviceListPage.waitForElementsOfDeviceListPage("deviceListTable");
		String deviceSerialNumberAfterSearch = deviceListPage.getTextOfDeviceListPage("foundDeviceInSearchResult");
		Assert.assertEquals(deviceSerialNumberAfterSearch.contains(deviceSerialNumber), true);
		LOGGER.info("Device is present on Devices page.");
		LOGGER.info("Click on the link to reach device details page");
		deviceListPage.clickOnElementsOfDeviceListPage("foundDeviceInSearchResult");
		sleeper(3000);// Tried removing this sleeper but is not working, so this
						// is needed after click on device list page
		String incidentTitle = "Test Incident for enrolled device " + deviceSerialNumber;
		String incidentDescription = "Description for Test Incident for enrolled device " + deviceSerialNumber;
		String type = getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.type.security");
		String subtype = "SURE_SENSE_PRO_CLIENT_DISABLED";
		String incidentId = deviceDetailsPage.submitCaseUsingAPI(type, subtype, incidentTitle, incidentDescription, deviceDetails.get("host"), deviceDetails.get("tenantId"), deviceDetails.get("userAuthToken"), deviceDetails.get("deviceId"), deviceDetails.get("UserID"));
		Assert.assertFalse(Strings.isNullOrEmpty(incidentId), "No incident id was created, hence failing the test case");
		gotoIncidentTab();
		incidentPage.verifyElementIsEnableOfIncidentPage("idSearchBox");
		LOGGER.info("Clear the text from incident ID field.");
		sleeper(2000);// Tried removing this sleeper but is not working, so this
						// is needed
		incidentPage.clickOnElementsOfIncidentPage("clearAllIncidentSearch");
		incidentPage.clearTextFromIncidentPageTextField("idSearchBox");
		incidentPage.waitForElementsOfIncidentPage("idSearchBox");
		LOGGER.info("Enter the value in incident ID field.");
		sleeper(2000);// Tried removing this sleeper but is not working, so this
						// is needed
		incidentPage.enterTextForIncidentPage("idSearchBox", incidentId);
		softAssert.assertTrue(incidentId.equals(incidentPage.getTextOfIncidentPage("incidentIdLink")));
		softAssert.assertAll();
		LOGGER.info("Verified, user is able to create incident when proactive security toggle is ON ");
	}

	/**
	 * Hide UI areas if the Companies User Personal Information toggle set OFF")
	 */
	@Test(priority = 65, groups = { "REGRESSIONCOMPANIES2", "REGRESSION", "SMOKE", "REGRESSION_CI", "SMOKE_CI" }, description = "Test Case 341376:Verify UI changes after User Personal Information toggle set OFF")
	public final void verifyUPIToggleOnCompaniesDetailsPage() throws Exception {
		login("MSP_USER_LIST_EMAIL", "MSP_USER_LIST_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		CompaniesDetailsPage companiesDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver()).getInstance();
		DeviceDetailsPage deviceDetailsPage = new DeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
		DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
		TableConfigurationPage tableConfigurationPage = new TableConfigurationPage(PreDefinedActions.getDriver());
		LogPage logPage = new LogPage(PreDefinedActions.getDriver()).getInstance();
		IncidentPage incidentPage = new IncidentPage(PreDefinedActions.getDriver()).getInstance();
		IncidentDetailsPage incidentDetailsPage = new IncidentDetailsPage(PreDefinedActions.getDriver()).getInstance();

		waitForPageLoaded();

		gotoCompaniesTab();
		LOGGER.info("Redirected to Companies list page");
		waitForPageLoaded();

		companiesPage.waitForElementsOfCompaniesPage("tableOverlay");

		Assert.assertTrue(companiesPage.getTextOfCompaniesPage("companiesTitleOnBreadcrumbs").equals(companiesPage.getTextLanguage(LanguageCode, "daas_ui", "pagetitle.companies")), "Companies page title did not match");
		LOGGER.info("Companies title matched");

		waitForPageLoaded();
		resetTableConfiguration();

		impersonateCompanyByCompanyName(UPICompany);
		companiesDetailsPage.waitForElementsOfCompaniesDetailsPageForinvisibile("tableOverlay");
		companiesPage.clickOnElementsOfCompaniesPage("rootPreferencesPage");
		waitForPageLoaded();
		LOGGER.info("Redirect to Company Preferences tab");

		companiesDetailsPage.scrollOnCompaniesDetailsPage("dataCaptureTileHeader");
		softAssert.assertTrue(companiesDetailsPage.verifyElementsOfCompaniesDetailsPage("dataCaptureTileHeader"), "Data Collection Tile header is not available on Company details page");
		softAssert.assertTrue(companiesDetailsPage.verifyElementsOfCompaniesDetailsPage("UserPersonalInformationHeaderText"), "UserPersonal Information toggle header is not available on Data Collectio tile");
		// Toggle ON
		if (companiesDetailsPage.getAttributesOfCompaniesDetailsPage("toggleStatus", "aria-checked").equals("false")) {
			LOGGER.info("USER PERSONAL INFORMATION toggle is OFF");
			companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("toggleStatus");
			LOGGER.info("USER PERSONAL INFORMATION toggle is set to ON");
		} else
			LOGGER.info("USER PERSONAL INFORMATION toggle is already switched ON");
		logout();
		waitForPageLoaded();
		LOGGER.info("Logged out successfully");
		LOGGER.info("Log in with company user to verify UPI toggle ON functionality");

		login("AUTH_COMPANY_EMAIL", "AUTH_COMPANY_PASSWORD");
		waitForPageLoaded();

		gotoDevicesTab();
		LOGGER.info("Redirected to device list page");
		waitForPageLoaded();
		deviceListPage.waitUntilElementIsInvisibleOfDeviceListPage("tableOverlay");

		Assert.assertTrue(deviceListPage.getTextOfDeviceListPage("devicesTitleOnBreadcrumbs").equals(deviceListPage.getTextLanguage(LanguageCode, "daas_ui", "pagetitle.devices")), "Devices title text is incorrect");
		LOGGER.info("Devices title matched");

		resetTableConfiguration();
		deviceListPage.waitUntilElementIsInvisibleOfDeviceListPage("tableOverlay");

		tableConfigurationPage = tableConfigurationPage.getInstance();
		tableConfigurationPage.waitForElementsOfTableConfigurationPage("tableConfigurationButton");
		tableConfigurationPage.clickOnElementsOfTableConfigurationPage("tableConfigurationButton");
		tableConfigurationPage.selectAllCheckboxOfPopup("columnCheckboxesOnPopup", "listOfColumnsOnPopup");
		tableConfigurationPage.clickOnElementsOfTableConfigurationPage("saveButton");
		sleeper(3000);

		// Verify EnrollmentUser and LastSignedInUser on Device List page
		softAssert.assertTrue(deviceListPage.verifyElementsOfDeviceListPage("EnrollmentUserColumnTitle"), "ENROLLMENT USER column is not available on device list page when UPI toggle is ON");
		softAssert.assertTrue(deviceListPage.verifyElementsOfDeviceListPage("LastSignedInUserColumnTitle"), "LAST SIGNED-IN-USER column is not available on device list page when UPI toggle is ON");

		deviceListPage.enterTextForDeviceListPage("serialNumberSearchBox", UPIDeviceName);
		deviceListPage.waitForElementsOfDeviceListPage("tableOverlay");

		softAssert.assertTrue(deviceListPage.verifyElementsOfDeviceListPage("EnrollmentUserColumnValue"), "ENROLLMENT USER data value is not available on device list page when UPI toggle is ON");
		LOGGER.info("Last SIGNED-IN USER AND ENROLLMENT USER are verified on device list page");

		deviceListPage.goToParticularDevice(UPIDeviceName);
		waitForPageLoaded();
		deviceDetailsPage.waitForElementsOfDeviceDetailsPageForinvisibile("tableOverlay");
		LOGGER.info("Redirect to device details page");
		softAssert.assertEquals(deviceDetailsPage.getTextOfDeviceDetailsPage("deviceBreadCrumbs"), deviceDetailsPage.getTextLanguage(LanguageCode, "daas_ui", "breadcrumbs.asset_details"), "Breadcrumb on device detials page does not match");

		// Verify EnrollmentUser and LastSignedInUser info in identification
		// tile on Device detail page
		softAssert.assertFalse(deviceDetailsPage.verifyElementsOfDeviceDetailsPage("EnrollmentUserOnDeviceDetailsPage"), "ENROLLMENT USER  is available on device details page when UPI toggle is ON");
		softAssert.assertFalse(deviceDetailsPage.verifyElementsOfDeviceDetailsPage("LastSignedInUserOnDeviceDetailsPage"), "LAST SIGNED-IN-USER is available on device details page when UPI toggle is ON");

		softAssert.assertFalse(deviceDetailsPage.verifyElementsOfDeviceDetailsPage("user"), "ENROLLMENT USER  data value is available on device details page when UPI toggle is ON");
		LOGGER.info("Last SIGNED-IN USER AND ENROLLMENT USER are verified on device details page");

		gotoLogTab();
		LOGGER.info("Redirected to Log page");
		waitForPageLoaded();
		Assert.assertTrue(logPage.getTextOfLogPage("logTitleOnBreadcrumbs").equals(logPage.getTextLanguage(LanguageCode, "daas_ui", "pagetitle.logs")), "Logs title text is incorrect");
		LOGGER.info("Log title on Log page is matched");

		resetTableConfiguration();
		waitForPageLoaded();

		tableConfigurationPage.waitForElementsOfTableConfigurationPage("tableConfigurationButton");
		tableConfigurationPage.clickOnElementsOfTableConfigurationPage("tableConfigurationButton");
		tableConfigurationPage.selectAllCheckboxOfPopup("columnCheckboxesOnPopup", "listOfColumnsOnPopup");
		tableConfigurationPage.clickOnElementsOfTableConfigurationPage("saveButton");
		sleeper(3000);

		// Verify User column on Logs page
		softAssert.assertTrue(logPage.verifyElementsOfLogPage("userColumn"), "Log User column is not available on Log page when UPI toggle is ON ");
		LOGGER.info("LOG USER are verified on log page");

		gotoIncidentTab();
		LOGGER.info("Redirected to incident tab");
		waitForPageLoaded();
		Assert.assertTrue(incidentPage.getTextOfIncidentPage("incidentTitleOnBreadcrumbs").equals(getTextLanguage(LanguageCode, "daas_ui", "incidents.label")), "Incident title text on incident list page is incorrect");
		LOGGER.info("Incident title on incident list page is matched");

		resetTableConfiguration();
		waitForPageLoaded();

		tableConfigurationPage.waitForElementsOfTableConfigurationPage("tableConfigurationButton");
		tableConfigurationPage.clickOnElementsOfTableConfigurationPage("tableConfigurationButton");
		tableConfigurationPage.selectAllCheckboxOfPopup("columnCheckboxesOnPopup", "listOfColumnsOnPopup");
		tableConfigurationPage.clickOnElementsOfTableConfigurationPage("saveButton");
		sleeper(3000);

		// verify specific incident
		Assert.assertTrue(incidentPage.verifyElementsOfIncidentPage("selectedIncidentTitle"), "No incident is present on incident page, unable to proceed further");
		LOGGER.info("Atleast one incident is present on Incident list page");

		// Commenting this for now as this variable is not getting used anywhere
		// in the script
		// ArrayList<String> incidentsInfo = new ArrayList<>();
		// incidentsInfo = incidentPage.getAllIncidentInfo(incidentID);

		// Verify Created by column on Incidents page
		incidentPage.scrollOnIncidentPage("createdByBoxTitle");
		softAssert.assertTrue(incidentPage.verifyElementsOfIncidentPage("createdByBoxTitle"), "Created By column is not available on Incident List page when UPI toggle is ON ");
		softAssert.assertTrue(incidentPage.verifyElementsOfIncidentPage("createdByColumnValue"), "Created By column field data is not available on Incident List page when UPI toggle is ON ");

		LOGGER.info("CREATED BY USER are verified on incident list page");

		// redirect to specific incident
		incidentPage.clickByJavaScriptOnIncidentPage("selectedIncidentTitle");
		LOGGER.info("Redirected to incident details page");
		waitForPageLoaded();

		if (!incidentDetailsPage.verifyElementOfIncidentDetailsPage("incidentDetailsPageTitle")) {
			Assert.fail("Incident details page did not load successfully");
		} else {
			incidentDetailsPage.waitUntilElementIsInvisibleOfIncidentDetailsPage("tableOverlay");
			softAssert.assertTrue(incidentDetailsPage.verifyElementOfIncidentDetailsPage("createdBy"), "Created By field is not available on Incident details page when UPI toggle is ON ");
			softAssert.assertTrue(incidentDetailsPage.verifyElementOfIncidentDetailsPage("createdByValue"), "Created By field data is not available on Incident details page when UPI toggle is ON ");

			// Created by value is 'System generated' on list page and 'System'
			// on details page, so we cannot verify it is same or not.
			// softAssert.assertTrue(incidentDetailsPage.getTextOfIncidentDetailsPage("createdByValue").equals(incidentsInfo.get(IncidentsVariables.CREATED_BY_COL_NUMBER)),
			// "Created by field data doesnt match");
			LOGGER.info("CREATED BY USER are verified on incident details page");
		}
		logout();
		waitForPageLoaded();
		LOGGER.info("Logged out successfully");
		LOGGER.info("Log in with MSP user to set USER PERSONAL INFORMATION toggle");

		login("MSP_USER_LIST_EMAIL", "MSP_USER_LIST_PASSWORD");
		waitForPageLoaded();

		gotoCompaniesTab();
		LOGGER.info("Redirected to Companies list page");
		waitForPageLoaded();
		companiesPage.waitForElementsOfCompaniesPage("tableOverlay");

		Assert.assertTrue(companiesPage.getTextOfCompaniesPage("companiesTitleOnBreadcrumbs").equals(companiesPage.getTextLanguage(LanguageCode, "daas_ui", "pagetitle.companies")), "Companies page title did not match");
		LOGGER.info("Companies title matched");

		waitForPageLoaded();
		resetTableConfiguration();
		companiesPage.waitForElementsOfCompaniesPage("tableOverlay");

		impersonateCompanyByCompanyName(UPICompany);
		companiesDetailsPage.waitForElementsOfCompaniesDetailsPageForinvisibile("tableOverlay");
		companiesPage.clickOnElementsOfCompaniesPage("rootPreferencesPage");
		waitForPageLoaded();
		LOGGER.info("Redirect to Company Preferences tab");

		companiesDetailsPage.scrollOnCompaniesDetailsPage("dataCaptureTileHeader");
		softAssert.assertTrue(companiesDetailsPage.verifyElementsOfCompaniesDetailsPage("dataCaptureTileHeader"), "Data Collection Tile is not available on Company details page");
		softAssert.assertTrue(companiesDetailsPage.verifyElementsOfCompaniesDetailsPage("UserPersonalInformationHeaderText"), "UserPersonal Information toggle is not available on Data Collectio tile");
		// Toggle OFF
		if (companiesDetailsPage.getAttributesOfCompaniesDetailsPage("toggleStatus", "aria-checked").equals("true")) {
			LOGGER.info("USER PERSONAL INFORMATION toggle is ON");
			companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("toggleStatus");
			LOGGER.info("Clicked on USER PERSONAL INFORMATION toggle to change status");

			// Verify choose company message on Add user pop up
			softAssert.assertTrue(companiesDetailsPage.matchTextOfCompaniesDetailsPage("UserPersonalInformationDisablePopupHeader", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.user_personal_info")), "USER PERSONAL INFORMATION disable popup title is incorrect");
			softAssert.assertTrue(companiesDetailsPage.matchTextOfCompaniesDetailsPage("UserPersonalInformationDisableMessage", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.user_personal_info_confirmation")), "USER PERSONAL INFORMATION disable popup confirmation text message is incorrect");

			softAssert.assertTrue(companiesDetailsPage.verifyElementsOfCompaniesDetailsPage("cancelConfirmationUPIOFFPopup"), "UserPersonal Information toggle disable confirmation button is not available");
			LOGGER.info("Click on cancel button on USER PERSONAL INFORMATION toggle disable confirmation pop up");
			companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("cancelConfirmationUPIOFFPopup");
			// When user Enable/Disable PI toggle the status of toggle should be disabled for 10 sec
			sleeper(10000);

			refreshPage();
			waitForPageLoaded();
			companiesDetailsPage.waitForElementsOfCompaniesDetailsPage("tableOverlay");
			companiesDetailsPage.scrollOnCompaniesDetailsPage("dataCaptureTileHeader");
			
			companiesDetailsPage.waitForElementsOfCompaniesDetailsPage("toggleStatus");
			companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("toggleStatus");
			LOGGER.info("Clicked on USER PERSONAL INFORMATION toggle to change status");
			softAssert.assertTrue(companiesDetailsPage.matchTextOfCompaniesDetailsPage("UserPersonalInformationDisablePopupHeader", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.user_personal_info")), "USER PERSONAL INFORMATION disable popup title is incorrect");
			softAssert.assertTrue(companiesDetailsPage.matchTextOfCompaniesDetailsPage("UserPersonalInformationDisableMessage", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.user_personal_info_confirmation")), "USER PERSONAL INFORMATION disable popup confirmation text message is incorrect");

			softAssert.assertTrue(companiesDetailsPage.verifyElementsOfCompaniesDetailsPage("disableConfirmationUPIOFFPopup"), "UserPersonal Information toggle disable confirmation button is not available");
			LOGGER.info("Click on USER PERSONAL INFORMATION toggle disable confirmation pop up");
			sleeper(3000);
			waitForPageLoaded();
			companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("disableConfirmationUPIOFFPopup");
			LOGGER.info("USER PERSONAL INFORMATION toggle is set to OFF");
		} else
			LOGGER.info("USER PERSONAL INFORMATION toggle is already switched OFF");
		logout();
		waitForPageLoaded();
		LOGGER.info("Logged out successfully");
		LOGGER.info("Log in with company user to verify UPI toggle OFF functionality");

		login("AUTH_COMPANY_EMAIL", "AUTH_COMPANY_PASSWORD");
		waitForPageLoaded();

		gotoDevicesTab();
		LOGGER.info("Redirected to device list page");
		waitForPageLoaded();
		deviceListPage.waitForElementsOfDeviceListPage("tableOverlay");

		Assert.assertTrue(deviceListPage.getTextOfDeviceListPage("devicesTitleOnBreadcrumbs").equals(deviceListPage.getTextLanguage(LanguageCode, "daas_ui", "pagetitle.devices")), "Devices title text is incorrect");
		LOGGER.info("Devices title matched");

		resetTableConfiguration();
		deviceListPage.waitUntilElementIsInvisibleOfDeviceListPage("tableOverlay");

		tableConfigurationPage = tableConfigurationPage.getInstance();
		tableConfigurationPage.waitForElementsOfTableConfigurationPage("tableConfigurationButton");
		tableConfigurationPage.clickOnElementsOfTableConfigurationPage("tableConfigurationButton");
		tableConfigurationPage.selectAllCheckboxOfPopup("columnCheckboxesOnPopup", "listOfColumnsOnPopup");
		tableConfigurationPage.clickOnElementsOfTableConfigurationPage("saveButton");
		sleeper(3000);

		// Verify EnrollmentUser and LastSignedInUser on Device List page
		softAssert.assertTrue(deviceListPage.verifyElementsOfDeviceListPage("EnrollmentUserColumnTitle"), "ENROLLMENT USER column is not available on device list page when UPI toggle is OFF");
		softAssert.assertTrue(deviceListPage.verifyElementsOfDeviceListPage("LastSignedInUserColumnTitle"), "LAST SIGNED-IN-USER column is not available on device list page when UPI toggle is OFF");

		deviceListPage.enterTextForDeviceListPage("serialNumberSearchBox", UPIDeviceName);
		deviceListPage.waitForElementsOfDeviceListPage("tableOverlay");

		softAssert.assertTrue(deviceListPage.verifyElementsOfDeviceListPage("EnrollmentUserColumnValue"), "ENROLLMENT USER data value is available on device list page when UPI toggle is OFF");
		LOGGER.info("Last SIGNED-IN USER AND ENROLLMENT USER are verified on device list page");

		deviceListPage.goToParticularDevice(UPIDeviceName);
		waitForPageLoaded();
		deviceDetailsPage.waitForElementsOfDeviceDetailsPageForinvisibile("tableOverlay");
		LOGGER.info("Redirect to device details page");
		softAssert.assertEquals(deviceDetailsPage.getTextOfDeviceDetailsPage("deviceBreadCrumbs"), deviceDetailsPage.getTextLanguage(LanguageCode, "daas_ui", "breadcrumbs.asset_details"), "Breadcrumb on device detials page does not match");

		// Verify EnrollmentUser and LastSignedInUser info in identification
		// tile on Device detail page
		softAssert.assertFalse(deviceDetailsPage.verifyElementsOfDeviceDetailsPage("EnrollmentUserOnDeviceDetailsPage"), "ENROLLMENT USER  is available on device details page when UPI toggle is OFF");
		softAssert.assertFalse(deviceDetailsPage.verifyElementsOfDeviceDetailsPage("LastSignedInUserOnDeviceDetailsPage"), "LAST SIGNED-IN-USER is available on device details page when UPI toggle is OFF");

		softAssert.assertFalse(deviceDetailsPage.verifyElementsOfDeviceDetailsPage("user"), "ENROLLMENT USER  data value is available on device details page when UPI toggle is OFF");
		LOGGER.info("Last SIGNED-IN USER AND ENROLLMENT USER are verified on device details page");

		gotoLogTab();
		LOGGER.info("Redirected to Log page");
		waitForPageLoaded();
		Assert.assertTrue(logPage.getTextOfLogPage("logTitleOnBreadcrumbs").equals(logPage.getTextLanguage(LanguageCode, "daas_ui", "pagetitle.logs")), "Logs title text is incorrect");
		LOGGER.info("Log title on Log page is matched");

		resetTableConfiguration();
		waitForPageLoaded();

		tableConfigurationPage.waitForElementsOfTableConfigurationPage("tableConfigurationButton");
		tableConfigurationPage.clickOnElementsOfTableConfigurationPage("tableConfigurationButton");
		tableConfigurationPage.selectAllCheckboxOfPopup("columnCheckboxesOnPopup", "listOfColumnsOnPopup");
		tableConfigurationPage.clickOnElementsOfTableConfigurationPage("saveButton");
		sleeper(3000);

		// Verify User column on Logs page
		softAssert.assertTrue(logPage.verifyElementsOfLogPage("userColumn"), "Log User column is not available on Log page when UPI toggle is OFF ");
		LOGGER.info("LOG USER are verified on log page");

		gotoIncidentTab();
		LOGGER.info("Redirected to incident tab");
		waitForPageLoaded();
		Assert.assertTrue(incidentPage.getTextOfIncidentPage("incidentTitle").equals(getTextLanguage(LanguageCode, "daas_ui", "incidents.label")), "Incident title text on incident list page is incorrect");
		LOGGER.info("Incident title on incident list page is matched");

		resetTableConfiguration();
		waitForPageLoaded();

		tableConfigurationPage.waitForElementsOfTableConfigurationPage("tableConfigurationButton");
		tableConfigurationPage.clickOnElementsOfTableConfigurationPage("tableConfigurationButton");
		tableConfigurationPage.selectAllCheckboxOfPopup("columnCheckboxesOnPopup", "listOfColumnsOnPopup");
		tableConfigurationPage.clickOnElementsOfTableConfigurationPage("saveButton");
		sleeper(3000);

		// verify specific incident
		Assert.assertTrue(incidentPage.verifyElementsOfIncidentPage("selectedIncidentTitle"), "No incident is present on incident page, unable to proceed further");
		LOGGER.info("Atleast one incident is present on Incident list page");
		// ArrayList<String> incidentsInfoNew = new ArrayList<>();
		// incidentsInfoNew = incidentPage.getAllIncidentInfo(incidentID);

		// Verify Created by column on Incidents page
		incidentPage.scrollOnIncidentPage("createdByBoxTitle");
		softAssert.assertTrue(incidentPage.verifyElementsOfIncidentPage("createdByBoxTitle"), "Created By column is not available on Incident List page when UPI toggle is OFF ");
		softAssert.assertTrue(incidentPage.verifyElementsOfIncidentPage("createdByColumnValue"), "Created By column field data is not available on Incident List page when UPI toggle is OFF ");

		LOGGER.info("CREATED BY USER are verified on incident list page");

		// redirect to specific incident
		incidentPage.clickByJavaScriptOnIncidentPage("selectedIncidentTitle");
		LOGGER.info("Redirected to incident details page");
		waitForPageLoaded();

		if (!incidentDetailsPage.verifyElementOfIncidentDetailsPage("incidentDetailsPageTitle")) {
			Assert.fail("Incident details page did not load successfully");
		} else {
			incidentDetailsPage.waitUntilElementIsInvisibleOfIncidentDetailsPage("tableOverlay");
			softAssert.assertTrue(incidentDetailsPage.verifyElementOfIncidentDetailsPage("createdBy"), "Created By field is not available on Incident details page when UPI toggle is OFF ");
			softAssert.assertTrue(incidentDetailsPage.verifyElementOfIncidentDetailsPage("createdByValue"), "Created By field data is not available on Incident details page when UPI toggle is OFF ");
			// Created by value is 'System generated' on list page and 'System'
			// on details page, so we cannot verify it is same or not.
			// softAssert.assertTrue(incidentDetailsPage.getTextOfIncidentDetailsPage("createdByValue").equals(incidentsInfoNew.get(IncidentsVariables.CREATED_BY_COL_NUMBER)),
			// "Created by field data doesnt match");
			LOGGER.info("CREATED BY USER are verified on incident details page");
		}
		softAssert.assertAll();
		LOGGER.info("Verified 'User Personal Information' Toggle ON/OFF functionality for Company Verified SUccessfully.");
	}

	@Test(priority = 66, groups = { "REGRESSIONCOMPANIES2", "REGRESSION", "REGRESSION_CI" }, description = "362203", enabled = false)
	public final void verifyArchivedDevicesTogglePreference() throws Exception {
		login("ROOT_ADMIN_USER_WhatsNew", "ROOT_PASSWORD_WhatsNew");
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		CompaniesDetailsPage companiesDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		String compName = generateRandomString(8);
		String paFirstName = generateRandomString(8);
		String paLastName = generateRandomString(8);
		String compEmail = "testcompany" + generateRandomNumber() + "@hpmsqa.mailinator.com";
		LOGGER.info("Redirected to companies page");
		waitForPageLoaded();
		companiesPage.waitForElementsOfCompaniesPage("tableOverlay");
		companiesPage.addCompanies(LanguageCode, compName, compEmail, CompaniesVariables.COUNTRY, CompaniesVariables.COMPANY_PLAN, getEnvironmentSpecificData(System.getProperty("environment"), "COMPANY_AUTO_MSP"), "", paFirstName, paLastName, false, false, true);
		logout();
		login("COMPANIES_MSP_EMAIL", "COMPANIES_MSP_PASSWORD");
		gotoCompaniesTab();
		LOGGER.info("Redirected to companies page");
		impersonateCompanyByCompanyName(compName);
		waitForPageLoaded();
		goToPreferencesTab();
		softAssert.assertTrue(companiesDetailsPage.matchTextOfCompaniesDetailsPage("archivedDeviceOptionSelected", "90 " + getTextLanguage(LanguageCode, "idm", "common.days") + " (" + getTextLanguage(LanguageCode, "idm", "common.recommended") + ")"), "Default preferences for archived devices toggle is incorrect");
		logout();
		login("ROOT_ADMIN_USER_WhatsNew", "ROOT_PASSWORD_WhatsNew");
		LOGGER.info("Redirected to companies page");
		waitForPageLoaded();
		companiesPage.waitForElementsOfCompaniesPage("tableOverlay");
		companiesPage.removeCompany(compName, LanguageCode);
		softAssert.assertAll();
		LOGGER.info("All test cases for verifyArchivedDevicesTogglePreference passed");
	}

	/**
	 * This test case will add license to company and validate company state accordingly
	 * 
	 * @throws Exception
	 */
	@Test(priority = 67, groups = { "REGRESSIONCOMPANIES2", "REGRESSION", "REGRESSION_CI", "STABILIZATION_STAGING" }, description = "TestCase 269570")
	public final void verifyLicenceKeyAssignmentToCompany() throws Exception {
			login("APP_ADMIN_USER", "APP_ADMIN_PASSWORD");
			SoftAssert softAssert = new SoftAssert();
			CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
			CompaniesDetailsPage companiesDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver()).getInstance();
			LicensesListPage subscriptionsListPage = new LicensesListPage(PreDefinedActions.getDriver()).getInstance();
			String subKey1 = generateRandomString(12).toUpperCase();
			String subKey2 = generateRandomString(12).toUpperCase();
			Assert.assertTrue(subscriptionsListPage.createLicenceKey(getEnvironmentSpecificData(System.getProperty("environment"), "DAAS_API_BASE_URL_AUTH") + subscriptionURL, subKey1, CommonVariables.SKU), "Licence key creation failed");
			Assert.assertTrue(subscriptionsListPage.createLicenceKey(getEnvironmentSpecificData(System.getProperty("environment"), "DAAS_API_BASE_URL_AUTH") + subscriptionURL, subKey2, CommonVariables.SKU_SEC), "Licence key creation failed");

			LOGGER.info("Created licence key " + subKey1);
			LOGGER.info("Created licence key " + subKey2);
			logout();

			login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
			LOGGER.info("Logged in with root user");

			Assert.assertTrue(companiesPage.createCompanyUsingAPI(subKey1, getEnvironmentSpecificData(System.getProperty("environment"), "COUNTRY_CODE"), LanguageCode, getEnvironmentSpecificData(System.getProperty("environment"), "MSP_ID3"), "", "", CompaniesVariables.COMPANY_STANDARDTRIAL), "Company Creation failed");
			LOGGER.info(subKey1 + " company created");
			impersonateCompanyByCompanyName(subKey1);
			LOGGER.info("Redirected to company details page");
			String tenantID = getUrlOfCurrentPage().substring(getUrlOfCurrentPage().length() - 36);

			companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("subscriptionTab");
			LOGGER.info("Click subcription tab");
			Thread.sleep(3000);
			companiesDetailsPage.clickByJavaScriptOnCompaniesDetailsPage("addNewKeyButton");
			companiesDetailsPage.enterTextForCompaniesDetailsPage("addSubscriptionKey", subKey1);
			companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("dialogSaveButton");
			softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("planOnSubscriptionModal").equals(CommonVariables.PLAN_ENHANCED), "Added key plan did not match");
			softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("licenceModalHeader").equals(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.modal.subscription_title")), "License key modal header did not match");
			Thread.sleep(3000);
			companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("btnAddKey");
			LOGGER.info("Added licence key to a company");

			String toastNotification = companiesDetailsPage.getTextOfCompaniesDetailsPage("toastNotification");
			softAssert.assertTrue(toastNotification.equals(getTextLanguage(LanguageCode, "daas_ui", "companies.details.update.success").replace("{name}", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.subscription.modal.title"))), "Key assignment to a company failed");
			companiesDetailsPage.waitUntilElementIsInvisibleOfCompanyDetailsPage("toastNotification");
			softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("planStatusOnSubscriptionTile").contains(getTextLanguage(LanguageCode, "daas_ui", "companies.list.sections.active")), "Assigned key is not in active state");
			softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("subKeyOnSubscriptionTile").contains(subKey1), "Assigned key1 did not match with licence tile key");

			companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("addNewKeyButton");
			companiesDetailsPage.enterTextForCompaniesDetailsPage("addSubscriptionKey", subKey2);
			companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("dialogSaveButton");
			softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("planOnSubscriptionModal").equals(CommonVariables.PLAN_SECURITY), "Added key plan did not match");
			softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("licenceModalHeader").equals(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.modal.subscription_title")), "License key modal header did not match");
			companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("btnAddKey");
			LOGGER.info("Added licence key to a company");

			toastNotification = companiesDetailsPage.getTextOfCompaniesDetailsPage("toastNotification");
			softAssert.assertTrue(toastNotification.equals(getTextLanguage(LanguageCode, "daas_ui", "companies.details.update.success").replace("{name}", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.subscription.modal.title"))), "Key assignment to a company failed");
			companiesDetailsPage.waitUntilElementInvisibleOfCompanyDetailsPage("toastNotification");
			companiesDetailsPage.scrollOnCompaniesDetailsPage("planStatusOnSubscriptionTile");
			softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("planStatusOnSubscriptionTile").contains(getTextLanguage(LanguageCode, "daas_ui", "companies.list.sections.active")), "Assigned key is not in active state");
			softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("subKeyOnSubscriptionTile").contains(subKey2), "Assigned key2 did not match with licence tile key");
			
			//Subscription, license and license details link verified on company details page.
			companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("subscription");
			softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("subscriptionTile").contains(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.subscription")), "Subscription title does not match on company details page");
			companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("license");
			softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("licenseTile").contains(getTextLanguage(LanguageCode, "daas_ui", "sidemenu.subscriptions")), "license title does not match on company details page");
			companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("licenseDetails");
			softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("licenseDetailsTile").contains(getTextLanguage(LanguageCode, "daas_ui", "subscription.license.subscription_details")), "license Details title does not match on company details page");
			LOGGER.info("Subscription license and license details link verified on company details page.");
			// Remove created company
			softAssert.assertTrue(companiesPage.removeCompanyUsingAPI(tenantID));
			softAssert.assertAll();
		}

	/**
	 * This test case verify user can extend trial duration for customer.
	 * 
	 * @throws Exception
	 */
	@Test(priority = 68, groups = { "REGRESSIONCOMPANIES2", "SMOKE", "REGRESSION_CI", "SMOKE_CI", "STABILIZATION_STAGING" }, description = "Test Case 365386,368922", enabled = false)
	public final void verifyExtendTrialSubscription() throws Exception {
		login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		CompaniesDetailsPage companiesDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();

		try {
			LOGGER.info("Redirected to Companies tab");
			waitForPageLoaded();
			JSONObject jsonAuthTokenforRoot = (JSONObject) parser.parse(authTokenParse(companiesPage.getTokenFromLocalStorage("dui_token_e")));
			String companyTenantID = jsonAuthTokenforRoot.get("tenant").toString();

			// Remove company through API.
			softAssert.assertTrue(companiesPage.removeAllCompany(environment, companyTenantID, companyBody, getEnvironment(environment)), "Company removal failed");
			Assert.assertTrue(companiesPage.addCompanies(LanguageCode, CommonVariables.COMPANY_NAME, CommonVariables.EMAIL, getEnvironmentSpecificData(System.getProperty("environment"), "COUNTRY"), CommonVariables.PLAN_STANDARD_TRIAL, getEnvironmentSpecificData(System.getProperty("environment"), "AUTOMATION_MSP"), getEnvironmentSpecificData(System.getProperty("environment"), "AUTOMATION_PARTNER"), "FN", "LN", true, false, true), "Company not created through Root Admin.");
			impersonateCompanyByCompanyName(CommonVariables.COMPANY_NAME);
			companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("companySubscriptionTab");
			Assert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("subscriptionTileLabel").equals(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.active_plan")), "Subscription plan label does not match on Account Summary Tile.");
			Assert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("subscriptionTileValue").equals(CommonVariables.PLAN_STANDARD_TRIAL), "Subscription plan value does not match on Account Summary Tile.");
			Assert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("companyTrialStartDateLabel").equals(getTextLanguage(LanguageCode, "daas_ui", "dashboard.widgets.business_review.start_date")), "Start Date label of trial subscription does not match on Account Summary Tile.");
			Assert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("companyTrialStartDate").equals(getCurrentMonthDateYear()), "Start Date of trial subscription does not match on Account Summary Tile.");
			Assert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("companyTrialEndDateLabel").equals(getTextLanguage(LanguageCode, "daas_ui", "dashboard.widgets.business_review.end_date")), "End Date label of trial subscription does not match on Account Summary Tile.");
			Assert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("companyTrialEndDate").equals(addDaysToCurrentDate(60)), "End Date of trial subscription does not match on Account Summary Tile.");
			Assert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("dateIdentityTile").equals(getTextLanguage(LanguageCode, "daas_ui", "companies.details.identityTile.subs.dateInfo").replace("{startDate}", getCurrentMonthDateYear()).replace("{endDate}", addDaysToCurrentDate(60))), "Start Date and End Date of trial subscription does not match on Identity Tile.");

			// Edit end date functionality
			companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("companyTrialEndDateicon");
			Assert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("endDateDialogHeader").equals(getTextLanguage(LanguageCode, "daas_ui", "dashboard.widgets.business_review.end_date")), "End Date dialog header does not match.");
			Assert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("endDateDialogLabel").equals(getTextLanguage(LanguageCode, "daas_ui", "dashboard.widgets.business_review.end_date")), "End Date dialog label does not match.");
			companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("endDateDialogDatePickerBox");
			companiesDetailsPage.waitForElementsOfCompaniesDetailsPage("endDateDialogTodayDateButton");
			companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("endDateDialogTodayDateButton");
			companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("endDateDialogSubmitButton");
			Assert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("endDateDialogTodayDateError").equals(getTextLanguage(LanguageCode, "daas_ui", "companies.details.endDate.error.futureDate")), "Error message for selecting today date in date picker does not match.");
			LOGGER.info("Error message for selecting today date in date picker is successfull.");

			// Edit end date within 180 days
			companiesDetailsPage.waitForElementsOfCompaniesDetailsPage("endDateDialogCancelButton");
			companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("endDateDialogCancelButton");
			companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("companyTrialEndDateicon");
			companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("endDateDialogDatePickerBox");
			companiesDetailsPage.waitForElementsOfCompaniesDetailsPage("endDateDialogTodayDateButton");
			companiesDetailsPage.selectDateFromCalenderOnCompanyDetailpage(addDaysToCurrentDate(70), "monthKeyCurrent", "endDateDialogRightArrow", "daysOnCurrentMonthKey");
			companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("endDateDialogSubmitButton");
			companiesDetailsPage.waitForElementsOfCompaniesDetailsPage("toastNotification");
			Assert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("toastNotification").equals(getTextLanguage(LanguageCode, "daas_ui", "companies.details.endDate.update.success")), "Error message for selecting date within 180  in date picker does not match.");

			// edit end date with 180+ days
			companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("companyTrialEndDateicon");
			companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("endDateDialogDatePickerBox");
			companiesDetailsPage.waitForElementsOfCompaniesDetailsPage("endDateDialogTodayDateButton");
			companiesDetailsPage.selectDateFromCalenderOnCompanyDetailpage(addDaysToCurrentDate(190), "monthKeyCurrent", "endDateDialogRightArrow", "daysOnCurrentMonthKey");
			companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("endDateDialogSubmitButton");
			Assert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("endDateDialogTodayDateError").equals(getTextLanguage(LanguageCode, "daas_ui", "companies.details.endDate.error.offLimit").trim()), "Error message for selecting date 180+  in date picker does not match.");
			companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("endDateDialogCancelButton");
			LOGGER.info("Error message for selecting end date > 180 days from date picker is successfull.");
			LOGGER.info("Verification of trial subscription plan verified successfully");
			logout();

			// Verify extend trial logs in MSP
			login("STAGING_ACCOUNT_MSP_COMPANY", "STAGING_ACCOUNT_MSP_COMPANY_PASSWORD");
			waitForPageLoaded();
			gotoCompaniesTab();
			waitForPageLoaded();
			impersonateCompanyByCompanyName(CommonVariables.COMPANY_NAME);
			companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("companySubscriptionTab");
			Assert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("subscriptionTileLabel").equals(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.active_plan")), "Subscription plan label does not match on Account Summary Tile of MSP.");

			// Edit end date in MSP
			companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("companyTrialEndDateicon");
			companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("endDateDialogDatePickerBox");
			companiesDetailsPage.waitForElementsOfCompaniesDetailsPage("endDateDialogTodayDateButton");
			companiesDetailsPage.selectDateFromCalenderOnCompanyDetailpage(addDaysToCurrentDate(100), "monthKeyCurrent",
					"endDateDialogRightArrow", "daysOnCurrentMonthKey");
			companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("endDateDialogSubmitButton");
			companiesDetailsPage.waitForElementsOfCompaniesDetailsPage("toastNotification");
			Assert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("toastNotification").equals(getTextLanguage(LanguageCode, "daas_ui", "companies.details.endDate.update.success")), "Error message for selecting date within 180  in date picker does not match in MSP.");
			DateFormat originalFormat = new SimpleDateFormat("MMMM dd, yyyy");
			DateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = originalFormat.parse(addDaysToCurrentDate(100));
			String formattedDate = targetFormat.format(date);
			gotoLogTab();
			sa.assertTrue(companiesDetailsPage.verifyTrialDescriptionOnLogsPage(LanguageCode, CommonVariables.COMPANY_NAME, formattedDate), "Description of trial on logs does not match");

		} finally {
			// Logout from account
			logout();
			// Deleting a Company
			login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
			JSONObject jsonAuthTokenforRoot = (JSONObject) parser.parse(authTokenParse(companiesPage.getTokenFromLocalStorage("dui_token_e")));
			String companyTenantID = jsonAuthTokenforRoot.get("tenant").toString();
			// Remove company through API.
			softAssert.assertTrue(companiesPage.removeAllCompany(environment, companyTenantID, companyBody, getEnvironment(environment)), "Company removal failed");
		}
	}

	/**
	 * Verified ProActive security tile on company preferences page
	 * 
	 * @throws Exception
	 */
	//Marked as false Proactive security toggle is hidden and will be removed in future as per User Story 987738: [WorkF][UI] Disable UI for Proactive security reports
	@Test(priority = 69, groups = { "REGRESSIONCOMPANIES2", "SMOKE", "REGRESSION_CI", "SMOKE_CI" }, description = "Test Case 374565: Verify Proactive security tile under company details page" , enabled = false)
	public final void verifyProactiveSecurityTile() throws Exception {
		login("MSP_ADMIN_US_ST_EMAIl_01", "MSP_ADMIN_US_ST_PASSWORD_01");
		SoftAssert softAssert = new SoftAssert();
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		CompaniesDetailsPage companyDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver()).getInstance();

		companiesPage.waitForElementsOfCompaniesPage("tableOverlay");
		gotoCompaniesTab();
		waitForPageLoaded();
		LOGGER.info("Naviagte to companies tab");
		companiesPage.clearFiltersOfCompaniesListPage("clearfilter");
		waitForPageLoaded();

		impersonateCompanyByCompanyName(proActiveSecurityCompany);
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("preferenceTab");
		LOGGER.info("Naviagte to companies preferences tab");
		waitForPageLoaded();

		companyDetailsPage.verifyElementsOfCompaniesDetailsPage("verifyReportText");
		companyDetailsPage.scrollOnCompaniesDetailsPage("verifyReportText");
		softAssert.assertTrue(((companyDetailsPage.waitForElementsOfCompaniesDetailsPage("verifyProactiveSecurityReportsToggle")) && (companyDetailsPage.getTextOfCompaniesDetailsPage("verifyProactiveSecurityReportsText").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.pro_sec_repos.title")))), "Proactive Security Reports Toggle is not visible");

		// ProActive Security Toggle is OFF
		LOGGER.info("Verify Proactive Security tile when Report toggle is disabled");
		if (companyDetailsPage.getTextOfCompaniesDetailsPage("verifyProactiveSecurityReportsStatus").equals(getTextLanguage(LanguageCode, "daas_ui", "settings.data.enabled"))) {
			LOGGER.info("Proactive Security Report toggle is already enabled");
			LOGGER.info("Proactive Security Report toggle set to OFF, to verify ProActive Security Tile");
			companyDetailsPage.waitForElementsOfCompaniesDetailsPage("verifyProactiveSecurityReportsToggle");
			companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("verifyProactiveSecurityReportsToggle");
			waitForPageLoaded();
			sleeper(3000);
		} else {
			LOGGER.info("Proactive Security Report toggle is already disabled");
		}
		softAssert.assertFalse(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("proactiveSecurityTileHeader"), "ProActive Security Tile is available when ProActive Security Toggle is disabled");
		companyDetailsPage.waitForElementsOfCompaniesDetailsPage("tableOverlay");
		waitForPageLoaded();

		// ProActive Security Toggle is ON
		LOGGER.info("Verify Proactive Security tile when Report toggle is enabled");
		if (companyDetailsPage.getTextOfCompaniesDetailsPage("verifyProactiveSecurityReportsStatus").equals(getTextLanguage(LanguageCode, "daas_ui", "settings.data.disabled"))) {
			LOGGER.info("Proactive Security Report toggle is already disabled");
			LOGGER.info("Proactive Security Report toggle set to ON, to verify ProActive Security Tile");
			companyDetailsPage.waitForElementsOfCompaniesDetailsPage("verifyProactiveSecurityReportsToggle");
			companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("verifyProactiveSecurityReportsToggle");
			waitForPageLoaded();
			sleeper(5000);
		} else {
			LOGGER.info("Proactive Security Report toggle is already enabled");
		}

		companyDetailsPage.waitForElementsOfCompaniesDetailsPage("tableOverlay");
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("proactiveSecurityTileHeader"), "ProActive Security Tile is available when ProActive Security Toggle is disabled");

		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("sureClickAdvancedHedaer").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.tile.proactive_security.sure_click_advanced")), "Sure Click Advanced Hedaer is not available");
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("sureClickAdvancedEditIcon");
		waitForPageLoaded();
		softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("proActiveSecurityPopUpHedaer", getTextLanguage(LanguageCode, "daas_ui", "companies.details.tile.proactive_security.sure_click_advanced")), "Sure Click Advanced Hedaer is not available on edit pop up");
		softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("proActiveSecurityPopUpInfo", getTextLanguage(LanguageCode, "daas_ui", "companies.details.tile.proactive_security.contoller_info")), "Sure Click Advanced pop up information is not correct");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("proActiveSecurityPopUpControllerHostnameHeader").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.tile.proactive_security.controller_hostname")), "Sure Click Advanced pop up Controller Hostname header is not correct");
		companyDetailsPage.enterTextForCompaniesDetailsPage("proActiveSecurityControllerHostnameInput", controllerHostname);
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("proActiveSecurityPopUpGroupIDHeader").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.group_id")), "Sure Click Advanced pop up Group ID header is not correct");
		companyDetailsPage.enterTextForCompaniesDetailsPage("proActiveSecurityGroupIDInput", groupID);
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("cancelButtonOnPopup");
		waitForPageLoaded();

		sleeper(2000);
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("sureClickAdvancedHedaer").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.tile.proactive_security.sure_click_advanced")), "Sure Click Advanced Hedaer is not available");
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("sureClickAdvancedEditIcon");
		waitForPageLoaded();
		softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("proActiveSecurityPopUpHedaer", getTextLanguage(LanguageCode, "daas_ui", "companies.details.tile.proactive_security.sure_click_advanced")), "Sure Click Advanced Hedaer is not available on edit pop up");
		softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("proActiveSecurityPopUpInfo", getTextLanguage(LanguageCode, "daas_ui", "companies.details.tile.proactive_security.contoller_info")), "Sure Click Advanced pop up information is not correct");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("proActiveSecurityPopUpControllerHostnameHeader").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.tile.proactive_security.controller_hostname")), "Sure Click Advanced pop up Controller Hostname header is not correct");
		companyDetailsPage.enterTextForCompaniesDetailsPage("proActiveSecurityControllerHostnameInput", controllerHostname);
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("proActiveSecurityPopUpGroupIDHeader").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.group_id")), "Sure Click Advanced pop up Group ID header is not correct");
		companyDetailsPage.enterTextForCompaniesDetailsPage("proActiveSecurityGroupIDInput", groupID);
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("saveButton");
		waitForPageLoaded();

		sleeper(2000);
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("sureClickAdvancedControllerHostNameHeader").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.tile.proactive_security.controller_hostname")), "Sure Click Advanced Controller Hostname header is not correct");
		softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("sureClickAdvancedControllerHostNameValue", controllerHostname), "Sure Click Advanced Controller Hostname value does not match");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("sureClickAdvancedGroupIDHeader").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.group_id")), "Sure Click Advanced pop up Group ID header is not correct");
		softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("sureClickAdvancedGroupIDValue", groupID), "Group Name value does not match");

		softAssert.assertAll();
		LOGGER.info("Verified proactive security tile on company details page successfully.");
	}

	/**
	 * Verified Company Onboarding- Approve Partner flow
	 * 
	 * @throws Exception
	 */

	@Test(priority = 70, groups = { "REGRESSIONCOMPANIES2", "SMOKE", "REGRESSION_CI", "SMOKE_CI", "STABILIZATION_STAGING" }, description = "This test validates onboarding for Company Test Cases: 379238,379252")

	public final void verifyCompanyOnboardingApprovePartner() throws Exception {
		login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
		SoftAssert softAssert = new SoftAssert();
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		UserPage userPage = new UserPage(PreDefinedActions.getDriver()).getInstance();
		WelcomePage welcomePage = new WelcomePage(PreDefinedActions.getDriver()).getInstance();
		String subKey = "OnboardTestCompany"+generateRandomString(7);
		String randomEmail = RandomStringUtils.random(5, true, false) + "@yopmail.com";
		try {
			LOGGER.info("Logged in with root user");

			Assert.assertTrue(companiesPage.createCompanyUsingAPI(subKey, getEnvironmentSpecificData(System.getProperty("environment"), "COUNTRY_CODE"), LanguageCode, getEnvironmentSpecificData(System.getProperty("environment"), "MSP_ID1"), getEnvironmentSpecificData(System.getProperty("environment"), "PARTNER_ID1"), getEnvironmentSpecificData(System.getProperty("environment"), "COMPANY_EMAIL_ONBOARDING"), CompaniesVariables.COMPANY_STANDARDTRIAL), "Company Creation failed");
			LOGGER.info(subKey + " company created");
			logout();

			// Login with Company
			getUrl(getEnvironment(System.getProperty("environment")) + "ui/view/dashboard");
			welcomePage.waitForElementsOfWelcomePage("signInButton");
			welcomePage.clickOnElementsOfWelcomePage("signInButton");
			loginCustom("ONBOARD_COMPANY_EMAIL", "ONBOARD_COMPANY_EMAIL_PASSWORD");
			waitForPageLoaded();
			tenantID = getValueFromToken("tenant");
			LOGGER.info("Login with new company");
			companiesPage.waitForElementsOfCompaniesPageDynamic("welcomeTitle", 15);

			// Verify Default Pop-up
			if (companiesPage.getTextOfCompaniesPage("welcomeTitle").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "onboarding.stepper.setup"))) {
				softAssert.assertTrue(companiesPage.getTextOfCompaniesPage("welcomeTitle").equals(getTextLanguage(LanguageCode, "daas_ui", "onboarding.stepper.setup")), "Onboarding Welcome Title does not match.");
				softAssert.assertTrue(companiesPage.getTextOfCompaniesPage("welcomeDesc").equals(getTextLanguage(LanguageCode, "daas_ui", "onboarding.confirmation.welcome.description")), "Onboarding Welcome description does not match.");
				softAssert.assertTrue(companiesPage.getTextOfCompaniesPage("welcomeText").equals(getTextLanguage(LanguageCode, "daas_ui", "onboarding.confirmation.welcome.text").trim()), "Onboarding Welcome text does not match.");
				softAssert.assertTrue(companiesPage.getTextOfCompaniesPage("startBtn").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "onboarding.action.start")), "Onboarding Start Button does not match.");
				softAssert.assertTrue(companiesPage.getTextOfCompaniesPage("addAdminTitle").equals(getTextLanguage(LanguageCode, "daas_ui", "onboarding.confirmation.it_admins.tile")), "Onboarding Add Administrators step name does not match.");
				softAssert.assertTrue(companiesPage.getTextOfCompaniesPage("approvePartnerTitle").equals(getTextLanguage(LanguageCode, "daas_ui", "onboarding.confirmation.partner.tile")), "Onboarding Approve Partner step name does not match.");
				softAssert.assertTrue(companiesPage.getTextOfCompaniesPage("finishSetupTitle").equals(getTextLanguage(LanguageCode, "daas_ui", "onboarding.confirmation.finish.tile")), "Onboarding Finish Setup step name does not match.");
				companiesPage.clickOnElementsOfCompaniesPage("startBtn");
				LOGGER.info("Clicked on start button");
				sleeper(5000);// Due to inconsistent UI response we have to put
								// wait here.(Tried many ways but this is the
								// final solution which works as expected.)
				companiesPage.waitUntilElementIsInvisibleOfCompanyPage("btnNextLoader");
				LOGGER.info("Verified welcome screen");
		
				// Verify Add Administrator Step
				Assert.assertTrue(companiesPage.getTextOfCompaniesPage("addAdminStepTitle").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "onboarding.confirmation.it_admin.title")), "Add Administrator Step did not open successfully.");
				LOGGER.info("Add Administrator Step open successfully");
				softAssert.assertTrue(companiesPage.getTextOfCompaniesPage("addAdminStepDesc").equals(getTextLanguage(LanguageCode, "daas_ui", "onboarding.confirmation.it_admin.description")), "Add Administrator Title does not match on Account Setup modal pop-up.");
				softAssert.assertTrue(companiesPage.getTextOfCompaniesPage("addAdminStepEmails").equals(getTextLanguage(LanguageCode, "daas_ui", "onboarding.confirmation.it_admin.emails")), "Add Administrator description does not match on Account Setup modal pop-up.");
				softAssert.assertTrue(companiesPage.getTextOfCompaniesPage("addAdminStepEmailDesc").equals(getTextLanguage(LanguageCode, "daas_ui", "onboarding.users.enter_emails_desc")), "Add Administrator email description does not match on Account Setup modal pop-up.");
				softAssert.assertTrue(companiesPage.getTextOfCompaniesPage("addAdminStepSkipBtn").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "onboarding.action.skip")), "Add Administrator Skip button text does not match on Account Setup modal pop-up.");
				softAssert.assertTrue(companiesPage.getTextOfCompaniesPage("addAdminStepNextBtn").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "global.next")), "Add Administrator Next button text does not match on Account Setup modal pop-up.");
				companiesPage.clickOnElementsOfCompaniesPage("addAdminStepNextBtn");
				companiesPage.waitForElementsOfCompaniesPage("addAdminError");
				softAssert.assertTrue(companiesPage.getTextOfCompaniesPage("addAdminError").equals(getTextLanguage(LanguageCode, "daas_ui", "onboarding.confirmation.partner.error_it_admin")), "Add Administrator email error does not match on Account Setup modal pop-up.");
				companiesPage.enterTextForCompaniesPage("addAdminEmailText", randomEmail);
				companiesPage.clickOnElementsOfCompaniesPage("addAdminStepNextBtn");
				sleeper(6000);// Due to inconsistent UI response we have to put
								// wait here.(Tried many ways but this is the
								// final solution which works as expected.)
				companiesPage.waitUntilElementIsInvisibleOfCompanyPage("btnNextLoader");
				
				// Verify Approve Partner Step
				Assert.assertTrue(companiesPage.getTextOfCompaniesPage("partnerConfirmTitle").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "onboarding.confirmation.partner.title")), "Approve Partner Step did not open successfully.");
				LOGGER.info("Default Pop-up verified successfully");
				softAssert.assertTrue(companiesPage.getTextOfCompaniesPage("partnerDetailsTitle").equals(getTextLanguage(LanguageCode, "daas_ui", "company.add.lable.partner")), "Partner Details Title does not match on Account Setup modal pop-up.");
				softAssert.assertTrue(companiesPage.getTextOfCompaniesPage("partnerDetailsEmail").equals(getTextLanguage(LanguageCode, "daas_ui", "company.add.lable.email")), "Partner Details Email does not match on Account Setup modal pop-up.");
				softAssert.assertTrue(companiesPage.getTextOfCompaniesPage("partnerDetailsName").equals(getTextLanguage(LanguageCode, "daas_ui", "company.add.lable.name")), "Partner Details Name does not match on Account Setup modal pop-up.");
				softAssert.assertTrue(companiesPage.getTextOfCompaniesPage("partnerDetailsAddress").equals(getTextLanguage(LanguageCode, "daas_ui", "companies.list.address")), "Partner Details Address does not match on Account Setup modal pop-up.");
				softAssert.assertTrue(companiesPage.getTextOfCompaniesPage("partnerDetailsID").equals(getTextLanguage(LanguageCode, "daas_ui", "partner.id")), "Partner Details ID does not match on Account Setup modal pop-up.");
				softAssert.assertTrue(companiesPage.getTextOfCompaniesPage("partnerAcceptBtn").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "partner_invite.button.accept")), "Partner Invite Accept button text does not match on Account Setup modal pop-up.");
				softAssert.assertTrue(companiesPage.getTextOfCompaniesPage("partnerDeclineBtn").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "global.decline")), "Partner Decline button text does not match on Account Setup modal pop-up.");
				companiesPage.clickOnElementsOfCompaniesPage("partnerAcceptBtn");
				sleeper(5000);// Due to inconsistent UI response we have to put
								// wait here.(Tried many ways but this is the
								// final solution which works as expected.)
				companiesPage.waitUntilElementIsInvisibleOfCompanyPage("btnAcceptLoader");
				
				// Verify Finish Setup Step
				companiesPage.waitForElementsOfCompaniesPage("finishTitle");
				Assert.assertTrue(companiesPage.getTextOfCompaniesPage("finishTitle").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "onboarding.confirmation.finish.title")), "Finish Setup Step did not open successfully.");
				LOGGER.info("Finish Setup Step open successfully");
				softAssert.assertTrue(companiesPage.getTextOfCompaniesPage("finishDesc").equals(getTextLanguage(LanguageCode, "daas_ui", "onboarding.confirmation.finish.description")), "Finish description does not match on Account Setup modal pop-up.");
				softAssert.assertTrue(companiesPage.getTextOfCompaniesPage("finishText").equals(getTextLanguage(LanguageCode, "daas_ui", "onboarding.confirmation.finish.text")), "Finish text does not match on Account Setup modal pop-up.");
				softAssert.assertTrue(companiesPage.getTextOfCompaniesPage("finishSetupDetails").equals(getTextLanguage(LanguageCode, "daas_ui", "onboarding.confirmation.finish.setup_details")), "Finish Setup details does not match on Account Setup modal pop-up.");
				softAssert.assertTrue(companiesPage.getTextOfCompaniesPage("partnerEnrolled").equals(getTextLanguage(LanguageCode, "daas_ui", "onboarding.confirmation.finish.partner_enrolled")), "Partner Enrolled does not match on Account Setup modal pop-up.");
				softAssert.assertTrue(companiesPage.getTextOfCompaniesPage("partnerAcceptBtn").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "global.finish")), "Finish Button text does not match on Account Setup modal pop-up.");
				companiesPage.clickOnElementsOfCompaniesPage("partnerAcceptBtn");
				sleeper(2000);
				companiesPage.waitUntilElementIsInvisibleOfCompanyPage("btnAcceptLoader");
				pressKey(Keys.ESCAPE);
				Assert.assertTrue(companiesPage.waitUntilElementInvisibleOfCompanyPage(("onboardingBanner")), "Partner is not assigned on Assigned Partner tab of settings ");
				
				softAssert.assertAll();
				LOGGER.info("Banner Widget removed from dashboard.");

			} else {
				LOGGER.error("Welcome Default pop-up did not open successfully");
			}

			// Verify Assigned Partner Tab after completing account setup
			gotoNonMSPSettingsTab();
			waitForPageLoaded();
			companiesPage.waitForElementsOfCompaniesPage("assignedPartner");
			companiesPage.clickOnElementsOfCompaniesPage("assignedPartner");
			Assert.assertTrue(companiesPage.verifyElementsOfCompaniesPage(("assignedPartnerSettings")), "Partner is not assigned on Assigned Partner tab of settings ");
			LOGGER.info("Partner is assigned after Completing Account Setup");

			// Verify Users tab after completing account setup
			gotoUserTab();
			waitForPageLoaded();
			resetTableConfiguration();
			userPage.enterTextForUserPage("userEmailSearchBox", randomEmail);
			sleeper(2000);
			Assert.assertTrue(userPage.getTextOfUserPage("userEmailSearchTextList").equalsIgnoreCase(randomEmail), "email is not found on user search list.");
			Assert.assertTrue(userPage.getTextOfUserPage("userRoleSearchList").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "roles.it_admin")), "User Role is not IT Admin on user search list.");
			LOGGER.info("User is added in list and user role added after Completing Account Setup");

			LOGGER.info("Company Onboarding with Account Setup completed successfully");
			softAssert.assertAll();

		} catch (Exception e) {
			LOGGER.error("Exception occured in method verifyCompanyOnboardingApprovePartner " + e.getMessage());

		} finally {
			// Remove created company
			logout();
			login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
			companiesPage.removeCompanyUsingAPI(tenantID);
			LOGGER.info("Company deleted");
		}
	}
	
	/**
	 * Verified Company Onboarding- Decline and Choose Partner flow
	 * 
	 * @throws Exception
	 */
	@Test(priority = 71, groups = { "REGRESSIONCOMPANIES2", "SMOKE", "REGRESSION_CI", "SMOKE_CI", "STABILIZATION_STAGING" }, description = "This test validates onboarding for Company Test Cases: 379238,379252")
	public final void verifyCompanyOnboardingDeclineChoosePartner() throws Exception {
		login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
		SoftAssert softAssert = new SoftAssert();
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		WelcomePage welcomePage = new WelcomePage(PreDefinedActions.getDriver()).getInstance();
		String subKey = "OnboardTestCompany"+generateRandomString(7);
		try {
			LOGGER.info("Logged in with root user");

			Assert.assertTrue(companiesPage.createCompanyUsingAPI(subKey, getEnvironmentSpecificData(System.getProperty("environment"), "COUNTRY_CODE"), LanguageCode, getEnvironmentSpecificData(System.getProperty("environment"), "MSP_ID1"), getEnvironmentSpecificData(System.getProperty("environment"), "PARTNER_ID1"), getEnvironmentSpecificData(System.getProperty("environment"), "COMPANY_EMAIL_ONBOARDING"), CompaniesVariables.COMPANY_STANDARDTRIAL), "Company Creation failed");
			LOGGER.info(subKey + " company created");
			logout();

			// Login with Company
			getUrl(getEnvironment(System.getProperty("environment")) + "ui/view/dashboard");
			welcomePage.waitForElementsOfWelcomePage("signInButton");
			welcomePage.clickOnElementsOfWelcomePage("signInButton");
			loginCustom("ONBOARD_COMPANY_EMAIL", "ONBOARD_COMPANY_EMAIL_PASSWORD");
			waitForPageLoaded();
			LOGGER.info("Login with new company");
			tenantID = getValueFromToken("tenant");

			companiesPage.waitForElementsOfCompaniesPageDynamic("welcomeTitle", 15);

			// Verify Default Pop-up
			if (companiesPage.getTextOfCompaniesPage("welcomeTitle").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "onboarding.stepper.setup"))) {
				softAssert.assertTrue(companiesPage.getTextOfCompaniesPage("welcomeTitle").equals(getTextLanguage(LanguageCode, "daas_ui", "onboarding.stepper.setup")), "Onboarding Welcome Title does not match.");
				companiesPage.clickOnElementsOfCompaniesPage("startBtn");
				LOGGER.info("Clicked on start button");
				sleeper(6000);// Due to inconsistent UI response we have to put wait here.(Tried many ways but this is the final solution which works as expected.)
				companiesPage.waitUntilElementIsInvisibleOfCompanyPage("btnNextLoader");
				LOGGER.info("Verified welcome screen");

				// Verify Decline and Choose Partner flow
				// Verify Add Administrator Step
				Assert.assertTrue(companiesPage.getTextOfCompaniesPage("addAdminStepTitle").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "onboarding.confirmation.it_admin.title")), "Add Administrator Step did not open successfully.");
				LOGGER.info("Add Administrator Step open successfully");
				companiesPage.clickOnElementsOfCompaniesPage("addAdminStepSkipBtn");
				sleeper(5000);// Due to inconsistent UI response we have to put wait here.(Tried many ways but this is the final solution which works as expected.)
				companiesPage.waitUntilElementIsInvisibleOfCompanyPage("btnNextLoader");
				
				Assert.assertTrue(companiesPage.getTextOfCompaniesPage("partnerConfirmTitle").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "onboarding.confirmation.partner.title")), "Approve Partner Step did not open successfully.");
				LOGGER.info("Default Pop-up verified successfully");
				companiesPage.clickOnElementsOfCompaniesPage("partnerDeclineBtn");
				companiesPage.waitForElementsOfCompaniesPage("declineErrorMsg");
				softAssert.assertTrue(companiesPage.getTextOfCompaniesPage("declineErrorMsg").equals(getTextLanguage(LanguageCode, "daas_ui", "onboarding.confirmation.partner.decline_confirmation").trim()), "Partner Decline confirmation error does not match on Account Setup modal pop-up.");
				companiesPage.clickOnElementsOfCompaniesPage("partnerAcceptBtn");
				sleeper(6000);// Due to inconsistent UI response we have to put wait here.(Tried many ways but this is the final solution which works as expected.)
				companiesPage.waitUntilElementIsInvisibleOfCompanyPage("btnAcceptLoader");
				companiesPage.waitForElementsOfCompaniesPage("partnerConfirmTitle");

				// Verify Choose Partner Screen
				Assert.assertTrue(companiesPage.getTextOfCompaniesPage("partnerConfirmTitle").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "onboarding.confirmation.choose_partner.title")), "Choose Partner Step did not open successfully.");
				LOGGER.info("Choose Partner Step open successfully");
				softAssert.assertTrue(companiesPage.getTextOfCompaniesPage("approvePartnerTitle").equals(getTextLanguage(LanguageCode, "daas_ui", "onboarding.confirmation.choose_partner.tile")), "Choose Partner step name does not match.");
				softAssert.assertTrue(companiesPage.getTextOfCompaniesPage("partnerDesc").equals(getTextLanguage(LanguageCode, "daas_ui", "onboarding.confirmation.choose_partner.description")), "Choose Partner description does not match.");
				softAssert.assertTrue(companiesPage.getTextOfCompaniesPage("partnerDropdownLabel").equals(getTextLanguage(LanguageCode, "daas_ui", "onboarding.partner.partner_name")), "Partner Name label on Choose Partner step does not match.");
				companiesPage.clickOnElementsOfCompaniesPage("partnerDropdown");
				companiesPage.selectFirstOptionFromDropdownOnCompaniesPage("partnerDropdownList");
				softAssert.assertTrue(companiesPage.getTextOfCompaniesPage("partnerDetailsTitle").equals(getTextLanguage(LanguageCode, "daas_ui", "company.add.lable.partner")), "Partner Details Title does not match on Account Setup modal pop-up.");
				softAssert.assertTrue(companiesPage.getTextOfCompaniesPage("partnerDetailsEmail").equals(getTextLanguage(LanguageCode, "daas_ui", "company.add.lable.email")), "Partner Details Email does not match on Account Setup modal pop-up.");
				softAssert.assertTrue(companiesPage.getTextOfCompaniesPage("partnerDetailsName").equals(getTextLanguage(LanguageCode, "daas_ui", "company.add.lable.name")), "Partner Details Name does not match on Account Setup modal pop-up.");
				softAssert.assertTrue(companiesPage.getTextOfCompaniesPage("partnerDetailsAddress").equals(getTextLanguage(LanguageCode, "daas_ui", "companies.list.address")), "Partner Details Address does not match on Account Setup modal pop-up.");
				softAssert.assertTrue(companiesPage.getTextOfCompaniesPage("partnerDetailsID").equals(getTextLanguage(LanguageCode, "daas_ui", "partner.id")), "Partner Details ID does not match on Account Setup modal pop-up.");
				companiesPage.clickOnElementsOfCompaniesPage("partnerAcceptBtn");
				sleeper(5000);// Due to inconsistent UI response we have to put wait here.(Tried many ways but this is the final solution which works as expected.)
				companiesPage.waitUntilElementIsInvisibleOfCompanyPage("btnAcceptLoader");
				companiesPage.waitForElementsOfCompaniesPage("partnerConfirmTitle");
				sleeper(5000);// Due to inconsistent UI response we have to put wait here.(Tried many ways but this is the final solution which works as expected.)
				companiesPage.waitUntilElementIsInvisibleOfCompanyPage("btnNextLoader");
				Assert.assertTrue(companiesPage.getTextOfCompaniesPage("finishTitle").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "onboarding.confirmation.finish.title")), "Finish Step did not open successfully.");
				softAssert.assertTrue(companiesPage.getTextOfCompaniesPage("partnerEnrolled").equals(getTextLanguage(LanguageCode, "daas_ui", "onboarding.confirmation.finish.partner_enrolled")), "Partner Enrolled does not match on Account Setup modal pop-up.");
				softAssert.assertTrue(companiesPage.getTextOfCompaniesPage("partnerAcceptBtn").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "global.finish")), "Finish Button text does not match on Account Setup modal pop-up.");
				companiesPage.clickOnElementsOfCompaniesPage("partnerAcceptBtn");
				sleeper(2000);
				pressKey(Keys.ESCAPE);
		        Assert.assertTrue(companiesPage.waitUntilElementInvisibleOfCompanyPage(("onboardingBanner")), "Partner is not assigned on Assigned Partner tab of settings ");
				LOGGER.info("Banner Widget removed from dashboard.");

			} else {
				LOGGER.error("Welcome Default pop-up did not open successfully");
			}

			// Verify Assigned Partner Tab after completing account setup
			gotoNonMSPSettingsTab();
			waitForPageLoaded();
			companiesPage.waitForElementsOfCompaniesPage("assignedPartner");
			companiesPage.clickOnElementsOfCompaniesPage("assignedPartner");
			Assert.assertTrue(companiesPage.getTextOfCompaniesPage("partnerNotAssignedSettings").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.label.not_assigned")), "Partner is assigned on Assigned Partner tab of settings.");
			companiesPage.waitForPresenceOfElementsOfCompaniesPage("invitationLabel");
			companiesPage.verifyElementsOfCompaniesPage("companyInvitationLabel");
			companiesPage.verifyElementsOfCompaniesPage("partnerInvitationName");
			companiesPage.verifyElementsOfCompaniesPage("resendPartnerInviteBtn");
			companiesPage.verifyElementsOfCompaniesPage("deletePartnerInviteBtn");
			LOGGER.info("Partner is not assigned after choosing partner on Account Setup");

			LOGGER.info("Company Onboarding with Choose Partner - Account Setup completed successfully");
			softAssert.assertAll();

		} catch (Exception e) {
			LOGGER.info("Exception occured in method verifyCompanyOnboardingDeclineChoosePartner " + e.getMessage());

		} finally {
			// Remove created company
			logout();
			login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
			Assert.assertTrue(companiesPage.removeCompanyUsingAPI(tenantID), "Company deletion failed");
			LOGGER.info("Company has been deleted");
		}
	}
	

	/**
	 *
	 * This test case will verify customer enrollment status tile on dashboard and onboarding process for newly added company
	 * 
	 * @throws Exception
	 */

	@Test(priority = 72, groups = { "REGRESSIONCOMPANIES2", "SMOKE", "REGRESSION_CI", "SMOKE_CI", "STABILIZATION_STAGING" }, description = "Test Case 374565",enabled=false)

	public final void verifyCustomerEnrollmentStatusTile() throws Exception {
		login("RESELLER_NEW_UI_Companies_EMAIL_COMPANIES", "RESELLER_NEW_UI_Companies_PASSWORD_COMPANIES");
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		WelcomePage welcomePage = new WelcomePage(PreDefinedActions.getDriver()).getInstance();
		String itAdminEmail = "testuser" + generateRandomNumber() + "@hpmsqa.mailinator.com";
		SoftAssert softAssert = new SoftAssert();
		try {

			// Create new company
			gotoCompaniesTab();
			Assert.assertTrue(companiesPage.createCompanyUsingAPI(CompaniesVariables.COMPANY_NAME_ONBOARDING, getEnvironmentSpecificData(System.getProperty("environment"), "COUNTRY_CODE"), LanguageCode, getEnvironmentSpecificData(System.getProperty("environment"), "MSP_ID"), getEnvironmentSpecificData(System.getProperty("environment"), "PARTNER_ID3"), getEnvironmentSpecificData(System.getProperty("environment"), "COMPANY_EMAIL_ONBOARDING"), CompaniesVariables.COMPANY_STANDARDTRIAL), "Company Creation failed");
			LOGGER.info("New company created through reseller login");

			// Verify newly added company and current onboarding step on
			// customer enrollment status tile.
			gotoDashboardTab();
			waitForPageLoaded();
			softAssert.assertTrue((dashboardPage.getTextOfDashboardPage("companyNameOnDashboadTile").equals(CompaniesVariables.COMPANY_NAME_ONBOARDING)), "Company name did not appear on customer enrollment status tile.");
			softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("currentStepName").equals(getTextLanguage(LanguageCode, "daas_ui", "onboarding.confirmation.it_admin.tile")), "Current step name not correct on customer enrollment status tile.");
			logout();

			getUrl(getEnvironment(System.getProperty("environment")) + "ui/view/dashboard");
			welcomePage.waitForElementsOfWelcomePage("signInButton");
			welcomePage.clickOnElementsOfWelcomePage("signInButton");
			loginCustom("ONBOARD_COMPANY_EMAIL", "ONBOARD_COMPANY_EMAIL_PASSWORD");
			tenantID = getValueFromToken("tenant");
			softAssert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("onboardingPopup"), "Onboarding popup did not appear even after first time login after sign up");
			sleeper(2000);
			dashboardPage.clickOnElementsOfDashboardPage("closeButton");
			
			if(dashboardPage.waitForElementsOfDashboardPage("whatsNewPopup"))
				dashboardPage.clickOnElementsOfDashboardPage("whatsNewPopupCloseButtonV3");
		
			// Verify onboarding widget
			softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("onboardingWidgetTitle1").equals(getTextLanguage(LanguageCode, "daas_ui", "onboarding.confirmation.banner_title")), "Onboarding widget title1 did not match");
			softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("onboardingWidgetTitle2").equals(getTextLanguage(LanguageCode, "daas_ui", "onboarding.confirmation.banner_text")), "Onboarding widget text did not match");
			dashboardPage.clickOnElementsOfDashboardPage("resumeOnboardingButton");

			// Complete onboarding process
			dashboardPage.clickOnElementsOfDashboardPage("startButton");
			sleeper(4000);
			softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("onboardingWelcomeText").equals(getTextLanguage(LanguageCode, "daas_ui", "onboarding.confirmation.it_admin.title")), "Create IT Admin title did not match");
			
			// validation for empty email
			softAssert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("skipButton"), "Skip button not present");
			softAssert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("nextButton"), "Next button not present");
			dashboardPage.clickOnElementsOfDashboardPage("nextButton");
			softAssert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("errorMessage"), "Error message not present for null values of IT admin");

			// Enter IT Admin email
			dashboardPage.clickOnElementsOfDashboardPage("itAdminEmailTextBox");
			dashboardPage.enterTextForDashboardPage("itAdminEmailTextBox", itAdminEmail);
			dashboardPage.clickOnElementsOfDashboardPage("nextButton");
			sleeper(2000);
			waitForPageLoaded();
			sleeper(5000);// After accepting partner invitation it takes time to
			// load the next step
			//dashboardPage.clickOnElementsOfDashboardPage("closeButton");
			dashboardPage.clickOnElementsOfDashboardPage("onboardBtnCancel");
			logout();

			// verify current step on customer enrollment status tile
			login("RESELLER_NEW_UI_EMAIL_US", "RESELLER_NEW_UI_PASSWORD_US");
			softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("currentStepName").equals(getTextLanguage(LanguageCode, "daas_ui", "onboarding.confirmation.partner.tile")), "Current step name not correct on customer enrollment status tile.");
			logout();

			// Verify add administrators step
			login("ONBOARD_COMPANY_EMAIL", "ONBOARD_COMPANY_EMAIL_PASSWORD");
			dashboardPage.clickOnElementsOfDashboardPage("resumeOnboardingButton");
			softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("partnerDetailsTitle").equals(getTextLanguage(LanguageCode, "daas_ui", "breadcrumbs.partner_details")), "Partner Details Title did not match");
			dashboardPage.clickOnElementsOfDashboardPage("acceptInviteButton");
			waitForPageLoaded();
			sleeper(5000);// After accepting partner invitation it takes time to
							// load the next step
			
			// Finish onboarding process
			softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("enrolledPartnerName").equals(getEnvironmentSpecificData(environment, "AUTOMATION_PARTNER")), "Partner Name did not match on onboarding popup");
			softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("enrolledPartnerNameLabel").equals(getTextLanguage(LanguageCode, "daas_ui", "onboarding.confirmation.finish.partner_enrolled")), "Enrolled partner name label did not match");
			softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("accountSetupLabel").equals(getTextLanguage(LanguageCode, "daas_ui", "onboarding.confirmation.finish.setup_details")), "Account setup details label did not match");
			dashboardPage.clickOnElementsOfDashboardPage("acceptInviteButton");
			waitForPageLoaded();

			// Verify onboarding popup is available completing enrollment
			softAssert.assertFalse(dashboardPage.verifyElementsOfDashboardPage("onboardingWidget"), "Onboarding widget appear after finishing the enrollment");
			logout();

			// verify company name is removed from customer enrollment status
			// tile after finishing enrollment
			login("RESELLER_NEW_UI_EMAIL_US", "RESELLER_NEW_UI_PASSWORD_US");
			softAssert.assertFalse((dashboardPage.getTextOfDashboardPage("companyNameOnDashboadTile").equals(CompaniesVariables.COMPANY_NAME_ONBOARDING)), "Company name did not removed from customer enrollment status tile after completing onboarding process.");
			
			softAssert.assertAll();
			LOGGER.info("Test case to verify Customer enrollment status tile and complete onboarding process passed successfully.");
		} catch (Exception e) {
			LOGGER.error("Exception occured in method verifyCustomerEnrollmentStatusTile " + e.getMessage());

		} finally {
			logout();
			login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
			Assert.assertTrue(companiesPage.removeCompanyUsingAPI(tenantID), "Company deletion failed");
			LOGGER.info("Company has been deleted");
		}
	}

	/**
	 * Verified COMPANY LOCATION HIERARCHY tile on company preferences page
	 * 
	 * @throws Exception
	 */
	@Test(priority = 73, groups = { "REGRESSIONCOMPANIES2", "SMOKE", "REGRESSION_CI", "SMOKE_CI" }, description = "Test Case 387979: Verify Company Hierarchy Location card on company details page")
	public final void verifyCompanyLocationHierarchyTile() throws Exception {

		SoftAssert softAssert = new SoftAssert();

		String mspName = null;
		String tenantID = null;
		login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		CompaniesDetailsPage companiesDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver()).getInstance();
		UserPage userPage = new UserPage(PreDefinedActions.getDriver()).getInstance();
		UserDetailsPage userDetailsPage = new UserDetailsPage(PreDefinedActions.getDriver()).getInstance();

		waitForPageLoaded();
		gotoRootCompaniesTab();
		LOGGER.info("Redirected to Companies tab");
		waitForPageLoaded();

		try {
			String subKey = generateRandomString(12);
			Assert.assertTrue(companiesPage.createCompanyUsingAPI(subKey, getEnvironmentSpecificData(System.getProperty("environment"), "COUNTRY_CODE"), LanguageCode, getEnvironmentSpecificData(System.getProperty("environment"), "MSP_ID3"), "", CompaniesVariables.HIERARCHY_LOCATION_TEST_COMPANY_EMAIL, CompaniesVariables.COMPANY_STANDARDTRIAL), "Company Creation failed");
			LOGGER.info(subKey + " company created");

			sleeper(20000);
			impersonateCompanyByCompanyName(subKey);
			LOGGER.info("Redirected to company details page");
			tenantID = getUrlOfCurrentPage().substring(getUrlOfCurrentPage().length() - 36);
			waitForPageLoaded();
			if (!toggleVerification(CompaniesVariables.DEVICE_GROUPING_TOGGLE, getCredentials(environment, "ROOT_ADMIN_USER_US"))) {
			companiesDetailsPage.waitForElementsOfCompaniesDetailsPage("preferenceTab");
			companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("preferenceTab");
			companiesDetailsPage.waitForElementsOfCompaniesDetailsPage("tableOverlay");
			LOGGER.info("Click on Preferences tab");

			// Company Location Hierarchy Tile
			waitForPageLoaded();
			companiesDetailsPage.waitForElementsOfCompaniesDetailsPage("companyLocationHierarchyTitle");
			companiesDetailsPage.scrollOnCompaniesDetailsPage("companyLocationHierarchyTitle");
			LOGGER.info("Validating Company Location Hierarchy Tile on Preferences tab");
			companiesDetailsPage.waitForElementsOfCompaniesDetailsPage("tableOverlay");
			waitForPageLoaded();
			Assert.assertTrue(companiesDetailsPage.verifyElementsOfCompaniesDetailsPage("companyLocationHierarchyTitle"), "Company Hierarchy Location Tile is not present on company preferences page");

			softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("multiLevelLocationTitle").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.locations.label")), "Multi level Location label does not match.");
			softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("multiLevelLocationToggleValue").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "global.disabled")), "Multi level Location toggle status does not match for newly created company.");
			softAssert.assertTrue(companiesDetailsPage.verifyElementsOfCompaniesDetailsPage("multiLevelLocationValueEditIcon"), "Multi level Location edit icon is not available when Multi level location level is not set");
			companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("multiLevelLocationValueEditIcon");
			LOGGER.info("Click on multi-level-location toggle edit icon on Company Location Hierarchy Tile on Preferences tab");

			softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("multiLevelLocationPopupHeader").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.locations.label")), "Multi level Location pop up header does not match");
			softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("multiLevelLocationToggleHeader").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "company_details.enable_location.label")), "Multi level Location toggle label does not match");

			companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("multiLevelLocationToggle");
			LOGGER.info("Set multi level location hierarchy on Company Location Hierarchy Tile");

			softAssert.assertTrue(companiesDetailsPage.verifyElementIsEnableOfCompaniesDetailsPage("maximumAllowedLevelHeader"), "Multi level locations - Maximum allowed levels are disabled even if Multi level locations toggle is enabled.");
			companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("maximumAllowedLevelDropdown");
			companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("maximumAllowedLevelDropdownValues");

			companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("multiLevelToggleSave");
			LOGGER.info("Save Location Hierarchy level on Company Location Hierarchy Tile");
			waitForPageLoaded();

			softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("multiLevelLocationUpdatedToggleValue").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "global.enabled")), "Multi level Location updated toggle value does not match");
			softAssert.assertFalse(companiesDetailsPage.verifyElementsOfCompaniesDetailsPage("multiLevelLocationValueEditIcon"), "Multi level Location edit icon is available when Multi level location is enabled.");
			waitForPageLoaded();
			softAssert.assertTrue(companiesDetailsPage.verifyElementsOfCompaniesDetailsPage("multiLevelLocationUpdatedHierarchyLevel"), "Multi level Location updated Hierarchy level does not match");

			softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("locationTitle").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.locations")), "Location header on Location Hieaerchy tile does not match");
			softAssert.assertTrue(companiesDetailsPage.verifyElementsOfCompaniesDetailsPage("locationTitleValue"), "Location value on Company Location Hieararchy is not present");
			LOGGER.info("Location Hierarchy tile is validated for root user");

			gotoMSPTab();
			waitForPageLoaded();
			LOGGER.info("Redirected to MSP users list page");
			userPage.waitUntilElementIsInvisibleOfUserPage("tableOverlay");

			userPage.enterTextForUserPage("nameSearchBox", getEnvironmentSpecificData(environment, "AUTOMATION_MSP_NEW"));
			userPage.waitForElementsOfUserPage("tableOverlay");
			waitForPageLoaded();
			userPage.clickByJavaScriptOnUserPage("firstUser");
			waitForPageLoaded();
			userPage.waitForElementsOfUserPage("tableOverlay");

			userPage.clickOnElementsOfUserPage("mspUserLink");
			userPage.waitForElementsOfUserPageForClick("tableOverlay");

			mspName = userDetailsPage.getTextOfUserDetailsPage("nameValue");
			userDetailsPage.waitForElementsOfUserDetailsPage("impersonateIcon");
			userDetailsPage.clickOnElementsOfUserDetailsPage("impersonateIcon");
			LOGGER.info("Clicked on impersonate icon for an MSP");
			userDetailsPage.waitForElementsOfUserDetailsPage("impersonationSubmit");
			userDetailsPage.clickByJavaScriptOnUserDetailsPage("impersonationSubmit");
			userDetailsPage.waitForElementsOfUserDetailsPage("tableOverlay");
			softAssert.assertTrue(userDetailsPage.getTextOfUserDetailsPage("impersonatedText").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "user.impersonation.warning").replace("{userName}", mspName) + " Go back to your own profile."), "Message on appbar after user impersonation is incorrect");
			softAssert.assertTrue(userDetailsPage.matchTextOfUserDetailsPage("impersonatedLink", getTextLanguage(LanguageCode, "daas_ui", "user.revert.impersonation.warning")), "Link on appbar after user impersonation is incorrect");

			gotoCompaniesTab();
			LOGGER.info("Redirected to Companies tab");
			waitForPageLoaded();

			impersonateCompanyByCompanyName(subKey);
			waitForPageLoaded();
			companiesDetailsPage.waitForElementsOfCompaniesDetailsPage("tableOverlay");

			companiesDetailsPage.waitForElementsOfCompaniesDetailsPage("preferenceTab");
			companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("preferenceTab");
			companiesDetailsPage.waitForElementsOfCompaniesDetailsPage("tableOverlay");
			LOGGER.info("Click on Preferences tab");

			// Company Location Hierarchy Tile
			waitForPageLoaded();
			companiesDetailsPage.waitForElementsOfCompaniesDetailsPage("companyLocationHierarchyTitle");
			companiesDetailsPage.scrollOnCompaniesDetailsPage("companyLocationHierarchyTitle");
			companiesDetailsPage.waitForElementsOfCompaniesDetailsPage("tableOverlay");
			waitForPageLoaded();
			LOGGER.info("Validating Company Location Hierarchy Tile on Preferences tab");
			Assert.assertTrue(companiesDetailsPage.verifyElementsOfCompaniesDetailsPage("companyLocationHierarchyTitle"), "Company Hierarchy Location Tile is not present on company preferences page");

			softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("multiLevelLocationTitle").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.locations.label")), "Multi level Location label does not match.");
			softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("multiLevelLocationUpdatedToggleValue").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "global.enabled")), "Multi level Location label does not match");
			softAssert.assertFalse(companiesDetailsPage.verifyElementsOfCompaniesDetailsPage("multiLevelLocationValueEditIcon"), "Multi level Location edit icon is available when Multi level location level is set");
			softAssert.assertTrue(companiesDetailsPage.verifyElementsOfCompaniesDetailsPage("multiLevelLocationUpdatedHierarchyLevel"), "Multi level Location Hierarchy level does not match");

			softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("locationTitle").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.locations")), "Multi level Location-Location label does not match");
			softAssert.assertTrue(companiesDetailsPage.verifyElementsOfCompaniesDetailsPage("locationTitleValue"), "Location value on Company Location Hieararchy is not present");

			userDetailsPage.waitForElementsOfUserDetailsPage("impersonatedLink");
			userDetailsPage.clickByJavaScriptOnUserDetailsPage("impersonatedLink");
			userDetailsPage.waitForElementsOfUserDetailsPage("tableOverlay");
			softAssert.assertTrue(companiesPage.verifyElementsOfCompaniesPage("addButton"), "User not redirected to home page after clicking on go back to your own profile link");
			LOGGER.info("Verified Location Hierarchy tile functionality for MSP User");

			softAssert.assertAll();
			LOGGER.info("Verified COMPANY LOCATION HIERARCHY LOCATION Tile on company preferences page successfully.");
			}else{
				companiesDetailsPage.waitForElementsOfCompaniesDetailsPage("preferenceTab");
				companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("preferenceTab");
				companiesDetailsPage.waitForElementsOfCompaniesDetailsPage("tableOverlay");
				LOGGER.info("Click on Preferences tab");

				// Company Location Hierarchy Tile
				waitForPageLoaded();
				companiesDetailsPage.waitForElementsOfCompaniesDetailsPage("companyLocationHierarchyTitle");
				companiesDetailsPage.scrollOnCompaniesDetailsPage("companyLocationHierarchyTitle");
				LOGGER.info("Validating Company Location Hierarchy Tile on Preferences tab");
				companiesDetailsPage.waitForElementsOfCompaniesDetailsPage("tableOverlay");
				waitForPageLoaded();
				Assert.assertTrue(companiesDetailsPage.verifyElementsOfCompaniesDetailsPage("companyLocationHierarchyTitle"), "Device Groups Tile is not present on company preferences page");

				softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("multiLevelButton").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.locations.multiLevel")), "Multi level Location button label does not match.");
				softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("deviceGroupLabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "company.deviceGroups")), "Device group label does not match for newly created company.");
				softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("deviceGroupLocationLevel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.locations.label.singleLevel")), "Device group location level does not match for newly created company.");
				
				companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("multiLevelButton");

				softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("switchMultilevelModalTitle").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.locations.multiLevel")), "Multi level Location modal title does not match");
				softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("switchMultilevelModalText1").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.locations.multiLevel.modalText1")), "Multi level Location modal Text1 does not match");
				softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("switchMultilevelModalText2").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.locations.multiLevel.modalText3")), "Multi level Location modal Text2 does not match");

				companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("switchMultilevelModalConfirmButton");
				waitForPageLoaded();

				softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("multiLevelButton").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.locations.singleLevel")), "Single level Location button label does not match.");
				softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("deviceGroupLabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "company.deviceGroups")), "Device group label does not match for newly created company.");
				softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("deviceGroupLocationLevel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.locations.label.multiLevel")), "Device group location level does not match for newly created company.");
				softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("windowsRegistryPath").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.locations.multiLevel.windowsRegistryPath")), "Windows Registry Path does not match for newly created company.");
				softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("windowsRegistryKey").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.locations.multiLevel.windowsRegistryKey")), "Windows Registry Key does not match for newly created company.");

				companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("multiLevelButton");
				softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("switchSinglelevelModalTitle").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.locations.singleLevel")), "Switch to Single level modal title did not match.");
				softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("switchSinglelevelModalText1").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.locations.singleLevel.modalText1")), "Switch to Single level modal Text 1 did not match.");
				softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("switchSinglelevelModalText2").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.locations.singleLevel.modalText2")), "Switch to Single level modal Text 2 did not match.");
				companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("switchSinglelevelModalCloseIcon");
				LOGGER.info("Location Hierarchy tile is validated for root user");
				waitForPageLoaded();
				gotoMSPTab();
				waitForPageLoaded();
				LOGGER.info("Redirected to MSP users list page");
				userPage.waitUntilElementIsInvisibleOfUserPage("tableOverlay");

				userPage.enterTextForUserPage("nameSearchBox", getEnvironmentSpecificData(environment, "AUTOMATION_MSP_NEW"));
				userPage.waitForElementsOfUserPage("tableOverlay");
				waitForPageLoaded();
				userPage.clickByJavaScriptOnUserPage("firstUser");
				waitForPageLoaded();
				userPage.waitForElementsOfUserPage("tableOverlay");

				userPage.clickOnElementsOfUserPage("mspUserLink");
				userPage.waitForElementsOfUserPageForClick("tableOverlay");

				mspName = userDetailsPage.getTextOfUserDetailsPage("nameValue");
				userDetailsPage.waitForElementsOfUserDetailsPage("impersonateIcon");
				userDetailsPage.clickOnElementsOfUserDetailsPage("impersonateIcon");
				LOGGER.info("Clicked on impersonate icon for an MSP");
				userDetailsPage.waitForElementsOfUserDetailsPage("impersonationSubmit");
				userDetailsPage.clickByJavaScriptOnUserDetailsPage("impersonationSubmit");
				userDetailsPage.waitForElementsOfUserDetailsPage("tableOverlay");
				softAssert.assertTrue(userDetailsPage.getTextOfUserDetailsPage("impersonatedText").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "user.impersonation.warning").replace("{userName}", mspName) + " Go back to your own profile."), "Message on appbar after user impersonation is incorrect");
				softAssert.assertTrue(userDetailsPage.matchTextOfUserDetailsPage("impersonatedLink", getTextLanguage(LanguageCode, "daas_ui", "user.revert.impersonation.warning")), "Link on appbar after user impersonation is incorrect");

				gotoCompaniesTab();
				LOGGER.info("Redirected to Companies tab");
				waitForPageLoaded();

				impersonateCompanyByCompanyName(subKey);
				waitForPageLoaded();
				companiesDetailsPage.waitForElementsOfCompaniesDetailsPage("tableOverlay");

				companiesDetailsPage.waitForElementsOfCompaniesDetailsPage("preferenceTab");
				companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("preferenceTab");
				companiesDetailsPage.waitForElementsOfCompaniesDetailsPage("tableOverlay");
				LOGGER.info("Click on Preferences tab");

				// Company Location Hierarchy Tile
				waitForPageLoaded();
				companiesDetailsPage.waitForElementsOfCompaniesDetailsPage("companyLocationHierarchyTitle");
				companiesDetailsPage.scrollOnCompaniesDetailsPage("companyLocationHierarchyTitle");
				companiesDetailsPage.waitForElementsOfCompaniesDetailsPage("tableOverlay");
				waitForPageLoaded();
				LOGGER.info("Validating Company Location Hierarchy Tile on Preferences tab");
				Assert.assertTrue(companiesDetailsPage.verifyElementsOfCompaniesDetailsPage("companyLocationHierarchyTitle"), "Device Groups Tile is not present on company preferences page");

				softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("multiLevelButton").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.locations.singleLevel")), "Single level Location button label does not match.");
				companiesDetailsPage.scrollOnCompaniesDetailsPage("deviceGroupLabel");
				softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("deviceGroupLabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "company.deviceGroups")), "Device group label does not match for newly created company.");
				softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("deviceGroupLocationLevel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.locations.label.multiLevel")), "Device group location level does not match for newly created company.");
				softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("windowsRegistryPath").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.locations.multiLevel.windowsRegistryPath")), "Windows Registry Path does not match for newly created company.");
				softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("windowsRegistryKey").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.locations.multiLevel.windowsRegistryKey")), "Windows Registry Key does not match for newly created company.");

				companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("multiLevelButton");
				softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("switchSinglelevelModalTitle").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.locations.singleLevel")), "Switch to Single level modal title did not match.");
				softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("switchSinglelevelModalText1").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.locations.singleLevel.modalText1")), "Switch to Single level modal Text 1 did not match.");
				softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("switchSinglelevelModalText2").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.locations.singleLevel.modalText2")), "Switch to Single level modal Text 2 did not match.");
				companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("switchSinglelevelModalCloseIcon");
				LOGGER.info("Verified Location Hierarchy tile functionality for MSP User");
				
				userDetailsPage.waitForElementsOfUserDetailsPage("impersonatedLink");
				userDetailsPage.clickByJavaScriptOnUserDetailsPage("impersonatedLink");
				userDetailsPage.waitForElementsOfUserDetailsPage("tableOverlay");
				softAssert.assertTrue(companiesPage.verifyElementsOfCompaniesPage("addButton"), "User not redirected to home page after clicking on go back to your own profile link");

				softAssert.assertAll();
				LOGGER.info("Verified DEVICE GROUPING Tile on company preferences page successfully.");
			}
		} finally {
			// Remove created company.
			companiesPage.removeCompanyUsingAPI(tenantID);
		}
	}

	/**
	 * This test case verifies the screens displayed for suspended company
	 * 
	 * @throws Exception
	 */

	// WRT Bug 581545: [AutoBug] REGRESSION suite-verifyDashboardViewForSuspendedCompany, companies are no longer going to be suspended, hence marking false
	
	@Test(priority = 74, groups = { "REGRESSIONCOMPANIES2", "SMOKE", "REGRESSION_CI", "SMOKE_CI", "STABILIZATION_STAGING" }, description = "Test Case 415821: [Core][UI] Verify screens for Suspended Company based on role", enabled = false)
	public final void verifySuspendedCompanyView() throws Exception {

		login("SUSPENDED_COMPANY_EMAIL", "SUSPENDED_COMPANY_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();

		if (!toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, "SUSPENDED_COMPANY_EMAIL"))) {
			gotoDashboardTab();
			LOGGER.info("Redirected to Dashboard tab");
			softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("dashboardTitle").equals(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "pagetitle.dashboard")), "Dashboard tab not loaded successfully");

			gotoDevicesTab();
			waitForPageLoaded();
			DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
			LOGGER.info("Redirected to devices tab");
			softAssert.assertTrue(deviceListPage.getTextOfDeviceListPage("devicesTitle").equals(deviceListPage.getTextLanguage(LanguageCode, "daas_ui", "pagetitle.devices")), "Devices tab not loaded successfully");

			gotoUserCompanyUserTab();
			waitForPageLoaded();
			UserPage userPage = new UserPage(PreDefinedActions.getDriver()).getInstance();
			LOGGER.info("Redirected to user page");
			softAssert.assertTrue(userPage.getTextOfUserPage("userTitle").equals(userPage.getTextLanguage(LanguageCode, "daas_ui", "pagetitle.users")), "User tab not loaded successfully");

			gotoNonMSPSettingsCompanyUserTab();
			waitForPageLoaded();
			SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
			LOGGER.info("Redirected to settings page");
			softAssert.assertTrue(settingsPage.verifyElementsOfSettingsPage("settingTitleNonCompany"), "Settings tab not loaded successfully");

			gotoHelpAndSupportTab();
			waitForPageLoaded();
			LOGGER.info("Redirected to Help and support tab");
			softAssert.assertTrue(settingsPage.getTextOfSettingsPage("helpAndSupportTitleText").equals(settingsPage.getTextLanguage(LanguageCode, "daas_ui", "sidemenu.support")), "Help and support tab not loaded successfully");

			gotoWhatsNewTabOfMspRA();
			waitForPageLoaded();
			LOGGER.info("Redirected to What's new page");
			WhatsNewPage whatsNewPage = new WhatsNewPage(PreDefinedActions.getDriver()).getInstance();
			softAssert.assertTrue(whatsNewPage.getTextOfWhatsNewPage("whatsNewTitle").equals(whatsNewPage.getTextLanguage(LanguageCode, "daas_ui", "sidemenu.whatsNew")), "What's New tab not loaded successfully");

			softAssert.assertAll();
			LOGGER.info("Verified the screens displayed for suspended IT admin");

		} else {
			gotoDashboardTab();
			softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("dashboardTitleOnBreadcrumbs").equals(dashboardPage.getTextLanguage(LanguageCode, "daas_ui", "pagetitle.dashboard")), "Dashboard tab not loaded successfully");
			LOGGER.info("Dashboard title is matched");

			gotoDevicesCompanyUserTab();
			waitForPageLoaded();
			DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
			LOGGER.info("Redirected to devices tab");
			softAssert.assertTrue(deviceListPage.getTextOfDeviceListPage("devicesTitleOnBreadcrumbs").equals(deviceListPage.getTextLanguage(LanguageCode, "daas_ui", "pagetitle.devices")), "Devices tab not loaded successfully");

			gotoUserCompanyUserTab();
			waitForPageLoaded();
			LOGGER.info("Redirected to user page");
			UserPage userPage = new UserPage(PreDefinedActions.getDriver()).getInstance();
			softAssert.assertTrue(userPage.getTextOfUserPage("userTitleOnBreadcrumbs").equals(userPage.getTextLanguage(LanguageCode, "daas_ui", "pagetitle.users")), "User tab not loaded successfully");

			gotoNonMSPSettingsCompanyUserTab();
			waitForPageLoaded();
			LOGGER.info("Redirected to settings page");
			SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
			softAssert.assertTrue(settingsPage.verifyElementsOfSettingsPage("settingTitleNonCompanyOnBreadcrumbs"), "Settings tab not loaded successfully");

			gotoHelpAndSupportTab();
			waitForPageLoaded();
			LOGGER.info("Redirected to help and support tab");
			softAssert.assertTrue(settingsPage.getTextOfSettingsPage("helpAndSupportTitleTextOnBreadcrumbs").equals(settingsPage.getTextLanguage(LanguageCode, "daas_ui", "sidemenu.support")), "Help and support tab not loaded successfully");

			gotoWhatsNewTabOfMspRA();
			waitForPageLoaded();
			WhatsNewPage whatsNewPage = new WhatsNewPage(PreDefinedActions.getDriver()).getInstance();
			LOGGER.info("Redirected to What's new tab");
			softAssert.assertTrue(whatsNewPage.getTextOfWhatsNewPage("whatsNewTitleOnBreadcrumbs").equals(whatsNewPage.getTextLanguage(LanguageCode, "daas_ui", "sidemenu.whatsNew")), "What's New tab not loaded successfully");

			softAssert.assertAll();
			LOGGER.info("Verified the screens displayed for suspended IT admin");
		}
	}
	
	// WRT Bug 581545: [AutoBug] REGRESSION suite-verifyDashboardViewForSuspendedCompany, companies are no longer going to be suspended, hence marking false
	 /***
	 * This test case will verify the dashboard view for suspended company
	 *
	 * @throws Exception
	 */

	@Test(priority = 75, groups = { "REGRESSIONCOMPANIES2", "SMOKE", "REGRESSION_CI", "SMOKE_CI" }, description = "Test Case 420442", enabled = false)

	public final void verifyDashboardViewForSuspendedCompany() throws Exception {
		login("SUSPENDED_COMPANY_EMAIL", "SUSPENDED_COMPANY_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		CompaniesDetailsPage companyDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver()).getInstance();
		String companyPlan = null;
		// Verify dashboard view for suspended company
		waitForPageLoaded();
		softAssert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("accountSummaryTile"), "Plan warning tile is not present for suspended company");
		softAssert.assertTrue(dashboardPage.matchTextOfDashboardPage("planWarningHeader", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.plan_suspended")), "Plan warning header did not match");
		softAssert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("dataUpdatedDate"), "Data updated date not present on Plan warning tile");
		softAssert.assertTrue(dashboardPage.matchTextOfDashboardPage("planWarningDescription1", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.account_warning.plan_suspended.title")), "Plan warning Description1 did not match");
		softAssert.assertTrue(dashboardPage.matchTextOfDashboardPage("planWarningDescription2", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.account_warning.licence.plan_suspended.message")), "Plan warning Description2 did not match");
		softAssert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("manageSubscriptionsButton"), "Manage subscriptions button not present on Plan warning tile");

		if(dashboardPage.verifyElementsOfDashboardPage("companyPlanNameOnDashboard"))
			companyPlan = dashboardPage.getTextOfDashboardPage("companyPlanNameOnDashboard");
		dashboardPage.clickOnElementsOfDashboardPage("manageSubscriptionsButton");
		waitForPageLoaded();
		String url = companyDetailsPage.getUrlOfCurrentPage();
		softAssert.assertTrue(url.contains("subscription"), "Page not redirected to Subscription tab on company details page after clicking on manage subscription button.");
		if(!companyPlan.equals(""))
			softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("planNameOnAccountTile").equals(companyPlan), "Plan name on company details page did not match with plan name on dashboard");

		//logout();
		LOGGER.info("Verified the plan warning tile on dashboard page for suspended company.");

		/*
		 * // Verify dashboard view for suspended company - Exceed threshold login("SUSPENDED_COMPANY_EXCEED_THRESHOLD_EMAIL", "SUSPENDED_COMPANY_EXCEED_THRESHOLD_PASSWORD");
		 * waitForPageLoaded(); softAssert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("accountSummaryTile"), "Plan warning tile is not present for suspended company");
		 * softAssert.assertTrue(dashboardPage.matchTextOfDashboardPage("planWarningHeader", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.plan_warning")),
		 * "Plan warning header did not match"); softAssert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("dataUpdatedDate"),
		 * "Data updated date not present on Plan warning tile"); softAssert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("noOfSeatsUsed"),
		 * "Number of seats used text not present on plan warning tile"); softAssert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("seatsOverLimitText"),
		 * "Number of seats Over limit text not present on plan warning tile");
		 * 
		 * companyPlan = dashboardPage.getTextOfDashboardPage("planNameOnDashboard"); softAssert.assertTrue(dashboardPage.matchTextOfDashboardPage("planWarningDescription1",
		 * getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.account_warning.seats_suspended.title")), "Plan warning Description1 did not match");
		 * softAssert.assertTrue(dashboardPage.matchTextOfDashboardPage("planWarningDescription2", getTextLanguage(LanguageCode, "daas_ui",
		 * "companies.details.section.account_warning.seats_suspended.message")), "Plan warning Description2 did not match");
		 * softAssert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("manageSubscriptionsButton"), "Manage subscriptions button not present on Plan warning tile");
		 * softAssert.assertTrue(dashboardPage.verifyElementsOfDashboardPage("manageSeatsButton"), "Manage Seats button not present on Plan warning tile");
		 * 
		 * dashboardPage.clickOnElementsOfDashboardPage("manageSubscriptionsButton"); waitForPageLoaded(); url = companyDetailsPage.getUrlOfCurrentPage();
		 * softAssert.assertTrue(url.contains("subscription"), "Page not redirected to Subscription tab on company details page after clicking on Manage subscription button.");
		 * softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("planNameOnIdentityTile").equals(companyPlan),
		 * "Plan name on company details page did not match with plan name on dashboard");
		 * softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("manageSeatsButton"), "Manage seats button not present on subscription tab");
		 * companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("manageSeatsButton"); waitForPageLoaded(); url = companyDetailsPage.getUrlOfCurrentPage();
		 * softAssert.assertTrue(url.contains("devices"), "Page not redirected to Devices tab after clicking on Manage seats button from subscription tab."); gotoDashboardTab();
		 * waitForPageLoaded(); dashboardPage.clickOnElementsOfDashboardPage("manageSeatsButton"); url = companyDetailsPage.getUrlOfCurrentPage();
		 * softAssert.assertTrue(url.contains("devices"), "Page not redirected to Devices tab after clicking on Manage seats button.");
		 */

		softAssert.assertAll();
	}
	
	/**
	 * This testcase verifies below feature 
	 * Feature 379269: [ProactiveSecurity] Automatically Toggle Proactive Security Reports to "On" when Proactive Security Product is Added.
	 * 
	 * @throws Exception
	 */
	@Test(priority = 76, groups = { "REGRESSIONCOMPANIES2", "REGRESSION", "REGRESSION_CI", "STABILIZATION_STAGING" }, description = "TestCase 269570", enabled = false)
	public final void verifyProactiveToggleEnabled() throws Exception {
		SoftAssert softAssert = new SoftAssert();

		// Create company with Plan - HP Proactive Management Premium (Onboarding)
		String subKey = generateRandomString(7);
		login("ROOT_ADMIN_USER_WhatsNew", "ROOT_PASSWORD_WhatsNew");
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		CompaniesDetailsPage companiesDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver()).getInstance();
		UserPage userPage = new UserPage(PreDefinedActions.getDriver()).getInstance();
		UserDetailsPage userDetailsPage = new UserDetailsPage(PreDefinedActions.getDriver()).getInstance();

		LOGGER.info("Logged in with root user");
		Assert.assertTrue(companiesPage.createCompanyUsingAPI(subKey, getEnvironmentSpecificData(System.getProperty("environment"), "COUNTRY_CODE"), LanguageCode, getEnvironmentSpecificData(System.getProperty("environment"), "MSP_ID"), "", "", CompaniesVariables.COMPANY_PROACTIVEPLAN), "Company Creation failed");
		LOGGER.info(subKey + " company created");
		String mspName = null;

		// Impersonate MSP

		gotoMSPTab();
		waitForPageLoaded();
		LOGGER.info("Redirected to MSP users list page");
		userPage.waitUntilElementIsInvisibleOfUserPage("tableOverlay");
		resetTableConfiguration();
		userPage.enterTextForUserPage("nameSearchBox", getEnvironmentSpecificData(environment, "COMPANY_AUTO_MSP"));
		userPage.waitForElementsOfUserPage("tableOverlay");
		waitForPageLoaded();
		userPage.clickByJavaScriptOnUserPage("firstUser");
		waitForPageLoaded();
		userPage.waitForElementsOfUserPage("tableOverlay");

		userPage.clickOnElementsOfUserPage("mspUserLink");
		userPage.waitForElementsOfUserPageForClick("tableOverlay");

		mspName = userDetailsPage.getTextOfUserDetailsPage("nameValue");
		userDetailsPage.waitForElementsOfUserDetailsPage("impersonateIcon");
		userDetailsPage.clickOnElementsOfUserDetailsPage("impersonateIcon");
		LOGGER.info("Clicked on impersonate icon for an MSP");
		userDetailsPage.waitForElementsOfUserDetailsPage("impersonationSubmit");
		userDetailsPage.clickByJavaScriptOnUserDetailsPage("impersonationSubmit");
		userDetailsPage.waitForElementsOfUserDetailsPage("tableOverlay");
		softAssert.assertTrue(userDetailsPage.getTextOfUserDetailsPage("impersonatedText").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "user.impersonation.warning").replace("{userName}", mspName) + " Go back to your own profile."), "Message on appbar after user impersonation is incorrect");
		softAssert.assertTrue(userDetailsPage.matchTextOfUserDetailsPage("impersonatedLink", getTextLanguage(LanguageCode, "daas_ui", "user.revert.impersonation.warning")), "Link on appbar after user impersonation is incorrect");

		// Imperosnate Company to verify Proactive Security Toggle status is enable for company with proactive Product

		gotoCompaniesTab();
		LOGGER.info("Redirected to Companies tab");
		waitForPageLoaded();
		impersonateCompanyByCompanyName(subKey);
		waitForPageLoaded();
		String tenantID = getUrlOfCurrentPage().substring(getUrlOfCurrentPage().length() - 36);
		companiesDetailsPage.waitForElementsOfCompaniesDetailsPage("tableOverlay");

		companiesDetailsPage.waitForElementsOfCompaniesDetailsPage("preferenceTab");
		companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("preferenceTab");
		companiesDetailsPage.waitForElementsOfCompaniesDetailsPage("tableOverlay");
		LOGGER.info("Click on Preferences tab");

		LOGGER.info("Verify Proactive Security toggle is enabled when Proactive Product is added");

		Assert.assertTrue(companiesPage.getTextOfCompaniesPage("verifyProactiveSecurityReportsStatus").equals(getTextLanguage(LanguageCode, "daas_ui", "settings.data.enabled")), "Proactive Security status is not enabled");
		LOGGER.info("Proactive Security Report toggle is enabled");

		userDetailsPage.waitForElementsOfUserDetailsPage("impersonatedLink");
		userDetailsPage.clickByJavaScriptOnUserDetailsPage("impersonatedLink");
		userDetailsPage.waitForElementsOfUserDetailsPage("tableOverlay");
		softAssert.assertTrue(companiesPage.verifyElementsOfCompaniesPage("addButton"), "User not redirected to home page after clicking on go back to your own profile link");

		// Remove created company
		Assert.assertTrue(companiesPage.removeCompanyUsingAPI(tenantID));
		LOGGER.info("Company has been deleted");
		softAssert.assertAll();
	}
	
	/**
	 * This test case verifies add company approval functionality through root user
	 * 
	 * @throws Exception
	 */
	// Disabled it as same scenarios are covered in test case: verifyMixedPlanLicenceSubscription()
	@Test(priority = 77, groups = { "REGRESSIONCOMPANIES1", "SMOKE", "REGRESSION_CI", "SMOKE_CI" }, description = "TESTCASE-253960", enabled= false)
	public final void verifyConvertBillingModelLicenseSubscription() throws Exception {
		login("ROOT_INVITE_FLOW_EMAIL", "ROOT_INVITE_FLOW_PASSWORD");
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		CompaniesDetailsPage companiesDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver()).getInstance();
		PartnerDetailsPage partnerDetailsPage = new PartnerDetailsPage(PreDefinedActions.getDriver()).getInstance();
		UserPage userPage = new UserPage(PreDefinedActions.getDriver()).getInstance();
		UserDetailsPage userDetailsPage = new UserDetailsPage(PreDefinedActions.getDriver()).getInstance();
		LogPage logPage = new LogPage(PreDefinedActions.getDriver()).getInstance();

		SoftAssert softAssert = new SoftAssert();
		try {
			LOGGER.info("Login to Root and redirected to company list page");
			waitForPageLoaded();
			resetTableConfiguration();

			Assert.assertTrue(companiesPage.createCompanyUsingAPI(getEnvironmentSpecificData(System.getProperty("environment"), "ADD_BILLING_MODEL_COMPANY_THROUGH_ROOT_NAME"), getEnvironmentSpecificData(System.getProperty("environment"), "ADD_COMPANY_THROUGH_ROOT_COUNTRY_CONTRACT_CODE"), LanguageCode, getEnvironmentSpecificData(System.getProperty("environment"), "ADD_COMPANY_THROUGH_ROOT_MSP_NAME_ID"), getEnvironmentSpecificData(System.getProperty("environment"), "ADD_COMPANY_THROUGH_ROOT_PARTNER_NAME_ID"), getEnvironmentSpecificData(System.getProperty("environment"), "ADD_BILLING_MODEL_COMPANY_THROUGH_ROOT_EMAIL"), CompaniesVariables.COMPANY_STANDARDTRIAL), "Company Creation failed");
			LOGGER.info("Company Added successfully");
			impersonateCompanyByCompanyName(getEnvironmentSpecificData(System.getProperty("environment"), "ADD_BILLING_MODEL_COMPANY_THROUGH_ROOT_NAME"));
			billingModelTenantID = getUrlOfCurrentPage().substring(getUrlOfCurrentPage().length() - 36);
			logout();

			// Remove Pending Invitations from Company
			login("ITA_COMPANY_DETAILS_BILLING_FLOW_EMAIL", "ITA_COMPANY_DETAILS_BILLING_FLOW_PASSWORD");
			LOGGER.info("Login to IT Admin of company");
			waitForPageLoaded();
			sleeper(2000);
			pressKey(Keys.ESCAPE);
			gotoNonMSPSettingsTab();
			LOGGER.info("Redirected to Settings tab");
			waitForPageLoaded();
			companiesPage.waitForPresenceOfElementsOfCompaniesPage("assignedPartner");
			companiesPage.clickOnElementsOfCompaniesPage("assignedPartner");
			companiesPage.waitForPresenceOfElementsOfCompaniesPage("invitationLabel");

			// Approve Invitation from Partner
			waitForPageLoaded();
			companiesPage.clickOnElementsOfCompaniesPage("acceptInvitationBtn");
			companiesPage.verifyElementsOfCompaniesPage("approveLabel");
			companiesPage.verifyElementsOfCompaniesPage("approveBtn");
			companiesPage.clickOnElementsOfCompaniesPage("approveBtn");
			partnerDetailsPage.waitForElementsOfPartnerDetailsPage("toastNotification");
			LOGGER.info("Invitation accepted from Partner");
			logout();

			login("ROOT_INVITE_FLOW_EMAIL", "ROOT_INVITE_FLOW_PASSWORD");
			LOGGER.info("Login to Root and redirected to company list page");
			getUrl(getEnvironment(System.getProperty("environment")) + "ui/view/partners/" + getEnvironmentSpecificData(System.getProperty("environment"), "ADD_COMPANY_THROUGH_ROOT_PARTNER_NAME_ID"));
			LOGGER.info("Redirected to partner page");

			partnerDetailsPage.verifySubscriptionContractToggle();
			LOGGER.info("Verified subscritpion authorized toggle in ON for the partner");
			String contractID = generateRandomNumber();
			partnerDetailsPage.createSubscriptionContract(LanguageCode, contractID, getEnvironmentSpecificData(System.getProperty("environment"), "ADD_COMPANY_THROUGH_ROOT_COUNTRY_CONTRACT"));
			LOGGER.info("Added contract for the partner");

			userPage.clickOnElementsOfUserPage("mspUserLink");
			userDetailsPage.waitForElementsOfUserDetailsPage("impersonateIcon");
			userDetailsPage.clickOnElementsOfUserDetailsPage("impersonateIcon");
			LOGGER.info("Clicked on impersonate icon for an Partner");
			userDetailsPage.waitForElementsOfUserDetailsPage("impersonationSubmit");
			userDetailsPage.clickByJavaScriptOnUserDetailsPage("impersonationSubmit");
			LOGGER.info("Redirected to impersonated partner");
			userDetailsPage.waitForElementsOfUserDetailsPage("tableOverlay");
			pressKey(Keys.ESCAPE);
			softAssert.assertTrue(userDetailsPage.matchTextOfUserDetailsPage("impersonatedLink", getTextLanguage(LanguageCode, "daas_ui", "user.revert.impersonation.warning")), "Link on appbar after user impersonation is incorrect");

			gotoCompaniesTab();
			LOGGER.info("Redirected to companies tab");
			impersonateCompanyByCompanyName(getEnvironmentSpecificData(System.getProperty("environment"), "ADD_BILLING_MODEL_COMPANY_THROUGH_ROOT_NAME"));
			companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("subscriptionTab");
			softAssert.assertTrue(companiesDetailsPage.verifyElementsOfCompaniesDetailsPage("convertToSubModButton"), "Convert to subscription model button not visible");
			softAssert.assertTrue(companiesDetailsPage.verifyElementsOfCompaniesDetailsPage("addNewKeyButton"), "Add new key button not visible");
			LOGGER.info("Verified billing model change on plan tab of company details page");

			Calendar cal = Calendar.getInstance();
			formatTime = new SimpleDateFormat("yyyy-MM-dd");
			cal.add(Calendar.DAY_OF_MONTH, 10);
			String endDate = formatTime.format(cal.getTime());
			Assert.assertTrue(companiesDetailsPage.convertToSubscriptionModel(billingModelTenantID, getEnvironmentSpecificData(System.getProperty("environment"), "ADD_COMPANY_THROUGH_ROOT_PARTNER_NAME_ID"), "11", endDate), "Billing model conversion has failed");
			LOGGER.info("Converted Billing model to subscription model");

			refreshPage();
			softAssert.assertTrue(companiesDetailsPage.verifyElementsOfCompaniesDetailsPage("convertToLicModel"), "Convert to license model button not visible");
			softAssert.assertTrue(companiesDetailsPage.verifyElementsOfCompaniesDetailsPage("addSubscriptionButton"), "Add subscription button not visible");
			softAssert.assertTrue(companiesDetailsPage.verifyElementsOfCompaniesDetailsPage("subscriptionAdded"), "Added subscription not visible on list");
			LOGGER.info("Verified license model view after conversion");

			gotoLogTab();
			resetTableConfiguration();
			logPage.clickOnElementsOfLogPage("firstCheckbox");
			Calendar calendar_today = Calendar.getInstance();
			formatTime = new SimpleDateFormat("MMMM dd, yyyy");
			softAssert.assertTrue(logPage.getTextOfLogPage("logsPageDescription").equalsIgnoreCase("11 seats of HP Proactive Insights plan was added to your company and starts on " + formatTime.format(calendar_today.getTime()) + "."), "Log text is incorrect");
			LOGGER.info("Verified generated log on adding subscription");

			userDetailsPage.waitForElementsOfUserDetailsPage("impersonatedLink");
			userDetailsPage.clickByJavaScriptOnUserDetailsPage("impersonatedLink");
			companiesPage.waitForElementsOfCompaniesPage("addButton");
			softAssert.assertTrue(companiesPage.verifyElementsOfCompaniesPage("addButton"), "User not redirected to home page after clicking on go back to your own profile link");
			LOGGER.info("Reverted the impersonation");

			softAssert.assertAll();
			LOGGER.info("Validation of convert billing model is done");
		} finally {
			// Logout from account
			logout();
			login("ROOT_INVITE_FLOW_EMAIL", "ROOT_INVITE_FLOW_PASSWORD");
			waitForPageLoaded();
			// Remove created company
			Assert.assertTrue(companiesPage.removeCompanyUsingAPI(billingModelTenantID), "Company removal failed");
			LOGGER.info("Company has been deleted");
		}
	}


     /**
	 * This test case verifies company creation with email domain validation
	 *
	 * @throws Exception
	 */
	@Test(priority = 78, groups = { "REGRESSIONCOMPANIES1", "SMOKE", "REGRESSION_CI", "SMOKE_CI" }, description = "BUG-570579")
	public final void verifyCompanyCreation() throws Exception {
		login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		CompaniesDetailsPage companiesDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver()).getInstance();

		SoftAssert softAssert = new SoftAssert();
		String validEmailWithMXAndA = generateRandomString(8) + "@" + CompaniesVariables.VALID_COMPANY_EMAIL_DOMAIN_WITH_MX_AND_A;
		String validEmailWithMXAndNoA = generateRandomString(8) + "@" + CompaniesVariables.VALID_COMPANY_EMAIL_DOMAIN_WITH_MX;
		String invalidEmail = generateRandomString(8) + "@" + generateRandomString(5) + "." + generateRandomString(3);
		String companyName = generateRandomString(8);
		LOGGER.info("Login to Root and redirected to company list page");
		waitForPageLoaded();

		// Create company with valid email domain with MX and A records
		companiesPage.clickOnElementsOfCompaniesPage("addButton");
		companiesPage.enterTextForCompaniesPage("companyNameOnPopup", companyName);
		companiesPage.enterTextForCompaniesPage("cityOnPopup", CompaniesVariables.CITY);
		sleeper(2000);
		companiesPage.clickOnElementsOfCompaniesPage("countryArrowOnPopup");
		companiesPage.enterTextForCompaniesPage("countrySearchOnPopup", CompaniesVariables.COUNTRY);
		companiesPage.clickOnElementsOfCompaniesPage("countrySelectOnPopup");

		companiesPage.waitForElementsOfCompaniesPage("languageArrowOnPopup");
		companiesPage.clickOnElementsOfCompaniesPage("languageArrowOnPopup");
		companiesPage.enterTextForCompaniesPage("languageSearchOnPopup", CommonVariables.LANGUAGE);
		companiesPage.clickOnElementsOfCompaniesPage("languageSelectOnPopup");

		companiesPage.enterTextForCompaniesPage("addressOnPopup", CompaniesVariables.ADDRESS);
		companiesPage.waitForElementsOfCompaniesPage("zipCodeOnCompPopup");
		companiesPage.enterTextForCompaniesPage("zipCodeOnCompPopup", CompaniesVariables.PINCODE);
		companiesPage.waitForElementsOfCompaniesPage("stateOnCompPopup");
		companiesPage.enterTextForCompaniesPage("stateOnCompPopup", CompaniesVariables.STATE);

		companiesPage.clickOnElementsOfCompaniesPage("nextButtonOnPopup");
		companiesPage.waitUntillElementIsClickableOfCompaniesPage("nextButtonOnPopup");
		companiesPage.clickOnElementsOfCompaniesPage("nextButtonOnPopup");
		companiesPage.waitForElementsOfCompaniesPage("firstNameOnPopup");
		companiesPage.enterTextForCompaniesPage("firstNameOnPopup", CompaniesVariables.FIRST_NAME);
		companiesPage.enterTextForCompaniesPage("lastNameOnPopup", CompaniesVariables.COMPANIES_TITLE);
		companiesPage.enterTextForCompaniesPage("emailOnPopup", validEmailWithMXAndA);
		companiesPage.enterTextForCompaniesPage("phoneNumberOnPopup", CompaniesVariables.NUMBER);
		
		companiesPage.clickOnElementsOfCompaniesPage("nextButtonOnPopup");
		sleeper(4000);
		companiesPage.clickOnElementsOfCompaniesPage("MSPArrowOnPopup");
		companiesPage.selectFirstOptionFromDropdownOnCompaniesPage("MSPListDrodpown");
		sleeper(2000);
		companiesPage.clickOnElementsOfCompaniesPage("nextButtonOnPopup");
		sleeper(2000);
		companiesPage.clickOnElementsOfCompaniesPage("saveButtonOnPopup");
		companiesPage.waitUntilElementIsInvisibleOfCompanyPageDynamic("btnCompanyCreate",20);
		Thread.sleep(20000); //Company API takes 1 min to load
		softAssert.assertTrue(companiesDetailsPage.matchTextOfCompaniesDetailsPage("companiesDetailsTitle", companyName),"Company with valid email domain with mx and a records created successfully");

		// Remove created company
		String[] urlArray = getUrlOfCurrentPage().split("/");
		tenantID = urlArray[6];
		Assert.assertTrue(companiesPage.removeCompanyUsingAPI(tenantID), "Company removal failed");
		LOGGER.info("Company has been deleted");

		gotoRootCompaniesTab();

		// Create company with valid email domain with MX and no A record
		companyName = generateRandomString(8);
		companiesPage.clickOnElementsOfCompaniesPage("addButton");
		companiesPage.enterTextForCompaniesPage("companyNameOnPopup", companyName);
		companiesPage.enterTextForCompaniesPage("cityOnPopup", CompaniesVariables.CITY);
		sleeper(2000);
		companiesPage.clickOnElementsOfCompaniesPage("countryArrowOnPopup");
		companiesPage.enterTextForCompaniesPage("countrySearchOnPopup", CompaniesVariables.COUNTRY);
		companiesPage.clickOnElementsOfCompaniesPage("countrySelectOnPopup");

		companiesPage.waitForElementsOfCompaniesPage("languageArrowOnPopup");
		companiesPage.clickOnElementsOfCompaniesPage("languageArrowOnPopup");
		companiesPage.enterTextForCompaniesPage("languageSearchOnPopup", CommonVariables.LANGUAGE);
		companiesPage.clickOnElementsOfCompaniesPage("languageSelectOnPopup");

		companiesPage.enterTextForCompaniesPage("addressOnPopup", CompaniesVariables.ADDRESS);
		companiesPage.waitForElementsOfCompaniesPage("zipCodeOnCompPopup");
		companiesPage.enterTextForCompaniesPage("zipCodeOnCompPopup", CompaniesVariables.PINCODE);
		companiesPage.waitForElementsOfCompaniesPage("stateOnCompPopup");
		companiesPage.enterTextForCompaniesPage("stateOnCompPopup", CompaniesVariables.STATE);

		companiesPage.clickOnElementsOfCompaniesPage("nextButtonOnPopup");
		companiesPage.waitUntillElementIsClickableOfCompaniesPage("nextButtonOnPopup");
		companiesPage.clickOnElementsOfCompaniesPage("nextButtonOnPopup");

		companiesPage.enterTextForCompaniesPage("firstNameOnPopup", CompaniesVariables.FIRST_NAME);
		companiesPage.enterTextForCompaniesPage("lastNameOnPopup", CompaniesVariables.COMPANIES_TITLE);
		companiesPage.enterTextForCompaniesPage("emailOnPopup", validEmailWithMXAndNoA);
		companiesPage.enterTextForCompaniesPage("phoneNumberOnPopup", CompaniesVariables.NUMBER);
		sleeper(2000);
		companiesPage.clickOnElementsOfCompaniesPage("nextButtonOnPopup");
		sleeper(4000);
		companiesPage.clickOnElementsOfCompaniesPage("MSPArrowOnPopup");
		companiesPage.selectFirstOptionFromDropdownOnCompaniesPage("MSPListDrodpown");
		sleeper(2000);
		companiesPage.clickOnElementsOfCompaniesPage("nextButtonOnPopup");
		sleeper(2000);
		companiesPage.clickOnElementsOfCompaniesPage("saveButtonOnPopup");
		companiesPage.waitUntilElementIsInvisibleOfCompanyPageDynamic("btnCompanyCreate",20);
		Thread.sleep(20000); //Company API takes 1 min to load
		softAssert.assertTrue(
				companiesDetailsPage.matchTextOfCompaniesDetailsPage("companiesDetailsCompanyName", companyName),
				"Company with valid email domain with mx and no a record created successfully");

		// Remove created company
		urlArray = getUrlOfCurrentPage().split("/");
		tenantID = urlArray[6];
		Assert.assertTrue(companiesPage.removeCompanyUsingAPI(tenantID), "Company removal failed");
		LOGGER.info("Company has been deleted");

		gotoRootCompaniesTab();

		// Create company with invalid email domain with no MX and no A record
		companyName = generateRandomString(8);
		companiesPage.clickOnElementsOfCompaniesPage("addButton");
		companiesPage.enterTextForCompaniesPage("companyNameOnPopup", companyName);
		companiesPage.enterTextForCompaniesPage("cityOnPopup", CompaniesVariables.CITY);
		sleeper(2000);
		companiesPage.clickOnElementsOfCompaniesPage("countryArrowOnPopup");
		companiesPage.enterTextForCompaniesPage("countrySearchOnPopup", CompaniesVariables.COUNTRY);
		companiesPage.clickOnElementsOfCompaniesPage("countrySelectOnPopup");

		companiesPage.waitForElementsOfCompaniesPage("languageArrowOnPopup");
		companiesPage.clickOnElementsOfCompaniesPage("languageArrowOnPopup");
		companiesPage.enterTextForCompaniesPage("languageSearchOnPopup", CommonVariables.LANGUAGE);
		companiesPage.clickOnElementsOfCompaniesPage("languageSelectOnPopup");

		companiesPage.enterTextForCompaniesPage("addressOnPopup", CompaniesVariables.ADDRESS);
		companiesPage.waitForElementsOfCompaniesPage("zipCodeOnCompPopup");
		companiesPage.enterTextForCompaniesPage("zipCodeOnCompPopup", CompaniesVariables.PINCODE);
		companiesPage.waitForElementsOfCompaniesPage("stateOnCompPopup");
		companiesPage.enterTextForCompaniesPage("stateOnCompPopup", CompaniesVariables.STATE);

		companiesPage.clickOnElementsOfCompaniesPage("nextButtonOnPopup");
		companiesPage.waitUntillElementIsClickableOfCompaniesPage("nextButtonOnPopup");
		companiesPage.clickOnElementsOfCompaniesPage("nextButtonOnPopup");

		companiesPage.enterTextForCompaniesPage("firstNameOnPopup", CompaniesVariables.FIRST_NAME);
		companiesPage.enterTextForCompaniesPage("lastNameOnPopup", CompaniesVariables.COMPANIES_TITLE);
		companiesPage.enterTextForCompaniesPage("emailOnPopup", invalidEmail);
		companiesPage.enterTextForCompaniesPage("phoneNumberOnPopup", CompaniesVariables.NUMBER);
		sleeper(2000);
		companiesPage.clickOnElementsOfCompaniesPage("nextButtonOnPopup");

		sleeper(2000);
		softAssert.assertTrue(companiesPage.getTextOfCompaniesPage("emailValidationErrorMessage").equalsIgnoreCase(getTextLanguage(LanguageCode, "idm", "signin.reset.invalid_email")),"Email domain validation failed");
		softAssert.assertAll();
	}

	//Disabling this test case since company creation is already tested in verifyCompanyCreation()
	@Test(priority = 79, groups = { "REGRESSIONCOMPANIES1", "SMOKE", "REGRESSION_CI", "SMOKE_CI" }, description = "TC: 570560",enabled=false)
	public final void verifyCompanyAddByRoot() throws Exception{
		login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
		SoftAssert softAssert = new SoftAssert();
		String tenantIDPartner,apiurl;
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		gotoRootCompaniesTab();

		if (toggleVerification(CompaniesVariables.PAYG_ONBOARDING_TOGGLE, getCredentials(environment, "ROOT_ADMIN_USER_US"))) {
		String companyName1 = companiesPage.createCompanyBillingModel("Subscription",partnerCompanyAddition);
		Assert.assertTrue(companiesPage.getTextOfCompaniesPage("companiesDetailsTitle").equalsIgnoreCase(companyName1), "Company creation is failed.");
		String[] urlArray1 = getUrlOfCurrentPage().split("/");
		String tenantID1 = urlArray1[6];
		Assert.assertTrue(companiesPage.removeCompanyUsingAPI(tenantID1), "Company1 removal failed");
		gotoRootCompaniesTab();
		
		String companyName2 = companiesPage.createCompanyBillingModel("License","");
		Assert.assertTrue(companiesPage.getTextOfCompaniesPage("companiesDetailsTitle").equalsIgnoreCase(companyName2), "Company creation is failed.");
		String[] urlArray2 = getUrlOfCurrentPage().split("/");
		String tenantID2 = urlArray2[6];
		Assert.assertTrue(companiesPage.removeCompanyUsingAPI(tenantID2), "Company2 removal failed");
		LOGGER.info("Company creation with Root Admin for both billing model is successfull.");
		}else{
			String companyName = companiesPage.createCompany(LanguageCode);
			softAssert.assertFalse(companyName.equals(null),
					"Error Company is not created successfully for by root credential.");
			LOGGER.info("Company is created successfully from the Root.");
			tenantIDPartner = getValueFromToken("tenant");
			apiurl = getEnvironmentSpecificData(System.getProperty("environment"), "DAAS_API_BASE_URL") + CommonVariables.DETAILSSEARCHSERVICEAPI + tenantIDPartner + CommonVariables.DETAILSSEARCHSERVICEAPI2;
			String tenantID = getTenantDetails(apiurl, CommonVariables.SEARCH_SERVICE_IDMTENANTS, companyName);
			Assert.assertTrue(companiesPage.removeCompanyUsingAPI(tenantID), "Company removal failed");
		}
	}
	/***
	 * This test case will verify the email notification triggered when subscription details of company changes.
	 *
	 * @throws Exception
	 */

	//Since mailinator has been removed due to captcha hence this test case has become obsolete.
	@Test(priority = 80, groups = {  "REGRESSIONCOMPANIES1" }, description = "TC:554398",enabled=false)
	public final void verifyEmailNotification() throws Exception {
		login("RESELLER_COMPANIES_LIST_EMAIL", "RESELLER_COMPANIES_LIST_PASSWORD");
		boolean mailReceivedFlag=false;
		Format formatter = new SimpleDateFormat("MMMMM d, YYYY");
		Date futureDate = DateUtils.addDays(new Date(), 1);
		String selectDate = formatter.format(futureDate);
		SoftAssert softAssert = new SoftAssert();
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		CompaniesDetailsPage companyDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver()).getInstance();
		gotoCompaniesTab();
		impersonateCompanyByCompanyName(getEnvironmentSpecificData(System.getProperty("environment"), "EMAIL_NOTIFIC_COMPANY"));
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("companySubscriptionTab");
		companyDetailsPage.waitForElementsOfCompaniesDetailsPage("editSubscriptionButton");
		
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("editSubscriptionButton");
		companyDetailsPage.waitForElementsOfCompaniesDetailsPage("editSubscriptionSaveKey");
		Integer seats = Integer.parseInt(companyDetailsPage.getAttributesOfCompaniesDetailsPage("subscriptionSeatField", "value"));
		seats++;
		companyDetailsPage.enterTextForCompaniesDetailsPage("subscriptionSeatField", seats.toString());
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("editSubscriptionSaveKey");
		LOGGER.info("Saved subscription key");
		Mailinator objMailinator = new Mailinator("", getEnvironmentSpecificData(System.getProperty("environment"), "EMAIL_NOTIFIC_COMPANY_EMAIL"));
		sleeper(5000);
		
		MailinatorMail objMailinatorEmail = objMailinator.getSpecificEmailUsingIncidentCode(getTextLanguage(LanguageCode, "daas_entitlement_notifier_service", "daas.notifier.subscription_changed_key_seat_increased_subject").toLowerCase().replace("{0}", getTextLanguage(LanguageCode, "lhserver", "global.package_name.hp_proactive_insights") + " subscription"), getEnvironmentSpecificData(System.getProperty("environment"), "EMAIL_NOTIFIC_COMPANY_EMAIL")+"@hpmsqa.mailinator.com", true);
		if(objMailinatorEmail!=null){
			LOGGER.info("Notification mail received after increasing seats of subscription");
			mailReceivedFlag=true;
		}
		softAssert.assertTrue(mailReceivedFlag,"No email is received after increasing seats of subscription.");
		sleeper(2000);
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("editSubscriptionButton");
		companyDetailsPage.waitForElementsOfCompaniesDetailsPage("editSubscriptionSaveKey");
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("expirationDate");
		sleeper(2000);
		softAssert.assertTrue(companyDetailsPage.selectDateFromCalenderForCompanyPin(selectDate, "calendarIcon", "month", "rightArrow", "totalDays"), "Date not selected successfully");
		sleeper(2000);
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("editSubscriptionSaveKey1");
		sleeper(5000);
		
		objMailinatorEmail = objMailinator.getSpecificEmailUsingIncidentCode(getTextLanguage(LanguageCode, "daas_entitlement_notifier_service", "daas.notifier.subscription_changed_key_expiration_subject").replace("{0}", getTextLanguage(LanguageCode, "lhserver", "global.package_name.hp_proactive_insights")), getEnvironmentSpecificData(System.getProperty("environment"), "EMAIL_NOTIFIC_COMPANY_EMAIL")+"@hpmsqa.mailinator.com", true);
		if(objMailinatorEmail!=null){
			LOGGER.info("Notification mail for expiration date change received");
			mailReceivedFlag=mailReceivedFlag&&true;
		}
		softAssert.assertTrue(mailReceivedFlag,"No email is received after expiration date change.");
		softAssert.assertAll();
		LOGGER.info("Test case verifyEmailNotification passed successfully");
	}
	

	@Test(priority = 81, groups = { "REGRESSIONCOMPANIES2" }, description = "TestCase[568164]")
	public final void verifyNotificationForCompany() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login("RESELLER_STAGING_COMPANIES_EMAIL", "RESELLER_STAGING_COMPANIES_PASSWORD");
		CompaniesDetailsPage companyDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver()).getInstance();
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		gotoCompaniesTab();
		impersonateCompanyByCompanyName(emailnotificCompany);
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("companySubscriptionTab");
		companyDetailsPage.waitForElementsOfCompaniesDetailsPage("accountSummaryTileHeader");
		companyDetailsPage.scrollOnCompaniesDetailsPage("editSubscriptionButton");
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("editSubscriptionButton");
		companyDetailsPage.waitForElementsOfCompaniesDetailsPage("editSubscriptionSaveKey");
		Integer seats = Integer.parseInt(companyDetailsPage.getAttributesOfCompaniesDetailsPage("subscriptionSeatField", "value"));
		seats++;
		companyDetailsPage.enterTextForCompaniesDetailsPage("subscriptionSeatField", seats.toString());
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("editSubscriptionSaveKey");
		LOGGER.info("Changes subscription key");
		companyDetailsPage.waitForElementsOfCompaniesDetailsPage("toastNotification");
		logout();

		login("ITA_COMPANY_DETAILS_EMAIL","ITA_COMPANY_DETAILS_PASSWORD");
		companiesPage.waitForElementsOfCompaniesPage("notificationBellIcon");
		companiesPage.clickOnElementsOfCompaniesPage("notificationBellIcon");
		companiesPage.waitForPresenceOfElementsOfCompaniesPage("invitationNotificationTitle");
		softAssert.assertTrue(companiesPage.getTextOfCompaniesPage("invitationNotificationTitle").split("[\r\n]+")[0].equals(getTextLanguage(LanguageCode, "daas_ui", "sidemenu.subscriptionPlan").toUpperCase()), "Plan title is not matching.");
		softAssert.assertTrue(companiesPage.getTextOfCompaniesPage("invitationNotificationDescription").equals(CompaniesVariables.COMPANY_PLANS), "Plan description is not matching.");
		companyDetailsPage.verifyDescriptionOnLoggingPage(CompaniesVariables.COMPANY_PLANS);
		companiesPage.clickOnElementsOfCompaniesPage("notificationBellIcon");
		softAssert.assertAll();
		LOGGER.info("Notification Icon is verified successfully");
	}
	
	@Test(priority = 82, groups = { "REGRESSIONCOMPANIES2", "SMOKE", "REGRESSION_CI", "SMOKE_CI" }, description = "TC385162", enabled = false)
	public final void verifyDeviceLockAndEraseTile() throws Exception {
		login("FLIP_MSP_ADMIN_EMAIL", "FLIP_MSP_ADMIN_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		CompaniesDetailsPage companyDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver()).getInstance();

		gotoCompaniesTab();
		LOGGER.info("Redirected to Companies tab");
		waitForPageLoaded();
		impersonateCompanyByCompanyName(getEnvironmentSpecificData(System.getProperty("environment"), "FLIP_COMPANY"));
		
		// Click on Preferences tab
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("preferenceTab");
		LOGGER.info("Redirected to Preferences tab");

		// Verify Device Lock and Erase tile section when toggle is off
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("deviceLockAndEraseSettingsTile"), "Device Lock and Erase tile missing under company details page");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("deviceLockAndEraseSettingsTileHeader").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.lockanderase").toUpperCase()), "Lock and Erase settings tile header did not match.");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("deviceLockAndEraseSettingsTileWarning").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section_description.lockanderase")), "Lock and Erase settings tile text did not match.");
		
		softAssert.assertTrue(companyDetailsPage.disableDeviceLockAndEraseToggle(LanguageCode),"Device Lock and Erase tile section when toggle is off validation failed");
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("lockAndEraseField"), "Lock And Erase field missing under company details page");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("lockAndEraseValue1").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "settings.data.disabled")), "Lock and Erase settings toggle disabled status did not match.");
		softAssert.assertFalse(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("lostDeviceAdmins"), "Lock device admin field present under company details page even if toggle is off");
		LOGGER.info(" Verified Device Lock and Erase tile section when toggle is off");
		
		
		// Verify Device Lock and Erase tile section when toggle is on
		softAssert.assertTrue(companyDetailsPage.enableDeviceLockAndEraseToggle(LanguageCode),"Device Lock and Erase tile section when toggle is on validation failed");
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("lockAndEraseField"), "Lock And Erase field missing under company details page");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("lockAndEraseValue1").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "assets.list.bromius.enabled")), "Lock and Erase settings toggle enabled status did not match.");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("lockAndEraseValue2").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.lockanderase.approvals.text").replace("{numberOfApprovals}", "4")), "Lock and Erase settings description text did not match.");
		
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("lostDeviceAdmins"), "Lock device admin field missing under company details page even if toggle is on");
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("manageApproversLink");
		
		softAssert.assertTrue(getUrlOfCurrentPage().contains("users"), "Manage approvers link did not redirected to the users page");
		LOGGER.info(" Verified Device Lock and Erase tile section when toggle is on");
		
		softAssert.assertAll();
	}

	/** This method will verify the application banner of overenrolled Plans for company
	 * 
	 * @throws Exception
	 */
	@Test(priority = 83, groups = { "REGRESSIONCOMPANIES2" }, description = "TestCase[583208]")
	public final void verifyApplicationBannerForCompany() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login("IT_ADMIN_COMPANY_USER_EMAIL_COMPANIES", "IT_ADMIN_COMPANY_USER_PASSWORD_COMPANIES");
		CompaniesDetailsPage companyDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver()).getInstance();
		//Banner verification
		companyDetailsPage.waitForElementsOfCompaniesDetailsPage("notificationBanner");
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("managePlans");
		waitForPageLoaded();
		companyDetailsPage.verifyElementsOfCompaniesDetailsPage("accountSummaryTileHeader");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("enrolledStatus").equals(getTextLanguage(LanguageCode, "daas_ui", "company.status.over_enrolled")),"Plan Status is not matching.");
		sleeper(2000);
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("remindMeLater");
//		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("remindMeLater");
		sleeper(1000);
		softAssert.assertTrue(companyDetailsPage.waitUntilElementInvisibleOfCompanyDetailsPage("notificationBanner"),"Banner is still visible");
		softAssert.assertAll();
	}
	
	/*
	 * Add - Subscription popup - plans dropdown value test
	 */
	@Test(priority = 84, groups = { "REGRESSIONCOMPANIES2" }, description = "TestCase[624757]")
	public final void verifyPlanDropdownCompanyDetailAddSubscription() throws Exception {
		login("RESELLER_STAGING_COMPANIES_EMAIL", "RESELLER_STAGING_COMPANIES_PASSWORD");
		gotoCompaniesTab();
		LOGGER.info("Redirected to Companies tab");
		waitForPageLoaded();
		impersonateCompanyByCompanyName(getEnvironmentSpecificData(System.getProperty("environment"), "COMPANY_NAME_DETAIL_PLANSLIST_RESELLER"));
		waitForPageLoaded();
		CompaniesDetailsPage companyDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver()).getInstance();
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("companySubscriptionTab");
		companyDetailsPage.scrollOnCompaniesDetailsPage("addSubscriptionButton");
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("addSubscriptionButton");
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("planDropdownButton");
		
		
		
		ArrayList<String> arrPlansValues = new ArrayList<String>(
				Arrays.asList(
						(getTextLanguage(LanguageCode, "lhserver", "global.package_name.enhanced_package.short")), 
						(getTextLanguage(LanguageCode, "lhserver", "global.package_name.premium_package.short")),
						(getTextLanguage(LanguageCode, "lhserver", "global.package_name.standard_package.short")),
						(getTextLanguage(LanguageCode, "lhserver", "global.package_name.proactive_standard_management_package.short")),
						(getTextLanguage(LanguageCode, "lhserver", "global.package_name.hp_proactive_endpoint_management")),
						(getTextLanguage(LanguageCode, "lhserver", "global.package_name.hp_proactive_insights"))
						)
				);		
		Assert.assertTrue(companyDetailsPage.comparePlansListOfCompanyDetailPage("planDropdownList", arrPlansValues), "All plans are not displayed in plans dropdown");
		
		
	}
	
	
	@Test(priority = 85, groups = { "REGRESSIONCOMPANIES2" }, description = "TestCase[770141]")
	public final void verifyDeviceLogsToggleForCompany() throws Exception 
	{
		login("NEW_MSP_ADMIN_US_EMAIl", "NEW_MSP_ADMIN_US_PASSWORD");
			SoftAssert softAssert = new SoftAssert();
			gotoCompaniesTab();
			impersonateCompanyByCompanyName(getEnvironmentSpecificData(System.getProperty("environment"), "COMPANY_NAME_DETAIL_PLANSLIST"));
			waitForPageLoaded();
			CompaniesDetailsPage companyDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver()).getInstance();
			companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("preferenceTab");
			LOGGER.info("Navigated to Preference Tab");
			companyDetailsPage.waitForElementsOfCompaniesDetailsPage("dataCaptureTileHeader");
			companyDetailsPage.scrollOnCompaniesDetailsPage("dataCaptureTileHeader");

			// Verify Device Logs toggle
			if (companyDetailsPage.getTextOfCompaniesDetailsPage("deviceLogsData").equals(getTextLanguage(LanguageCode, "daas_ui", "global.enabled"))) {
				companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("deviceLogsToggle");
				sleeper(2000);
				softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("deviceLogsData", getTextLanguage(LanguageCode, "daas_ui", "global.disabled")), "Device logs status does not match after toggle change");
				LOGGER.info("Device logs toggle Disabled");
				sleeper(6000);
				gotoLogTab();
				softAssert.assertTrue(companyDetailsPage.verifyDescriptionOnLogsPage((CompaniesVariables.DATA_COLLECTION_TOGGLE_DISABLED).replace("{toggle_name}", getTextLanguage(LanguageCode, "daas_ui", "assets.details.deviceLogs"))), "Description on logs page does not match after disabling device logs toggle");
			} else {
				companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("deviceLogsToggle");
				sleeper(2000);
				softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("deviceLogsData", getTextLanguage(LanguageCode, "daas_ui", "global.enabled")), "Device logs status does not match after toggle change");
				LOGGER.info("Device logs toggle enabled");
				sleeper(6000);
				gotoLogTab();
				softAssert.assertTrue(companyDetailsPage.verifyDescriptionOnLogsPage((CompaniesVariables.DATA_COLLECTION_TOGGLE_ENABLED).replace("{toggle_name}", getTextLanguage(LanguageCode, "daas_ui", "assets.details.deviceLogs"))), "Description on logs page does not match after enabling device logs toggle");
			}
			
			LOGGER.info("Verified description on Logs for Device Logs toggle");
		
	    softAssert.assertAll();
	}
	
	
	/*
	 * HPDex plans verify in company detail page
	 */
	@Test(priority = 86, groups = { "REGRESSIONCOMPANIES2" }, description = "TestCase[783799]", enabled = true)
	public final void verifyHPDEXPlansOnCompanyDetail() throws Exception 
	{
		login("HPDEX_IT_ADMIN", "HPDEX_IT_ADMIN_PASSWORD");
		CompaniesDetailsPage companyDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver()).getInstance();
		gotoCompanySettingsTab();
		LOGGER.info("Redirected to Settings tab");
		waitForPageLoaded();
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("subscriptionTab");
		LOGGER.info("Navigated to Preference Tab");
			
			ArrayList<String> arrPlansValues = new ArrayList<String>(
					Arrays.asList(
							(getTextLanguage(LanguageCode, "roles_meta_service", "plans.package_name.displayName.proactive_insights_hpdx")), 
							(getTextLanguage(LanguageCode, "roles_meta_service", "plans.package_name.displayName.hpdx_plan"))
							)
					);		
			Assert.assertTrue(companyDetailsPage.comparePlansOfHPDexInCompanyDetailPage("planAccountSummary", arrPlansValues), "HP Dex plans not visible in company detail page");
			LOGGER.info("HP DEX plans verified successfully");		
			
			//HP-Dex tab visible
			    HPDexPage hpdexPage = new HPDexPage(PreDefinedActions.getDriver()).getInstance();
				expandMenuIcon();
				Assert.assertTrue( hpdexPage.verifyElementsOfHPDexPage("hpDexTab"), "HP Dex tab is not visible in side-menu");	
				LOGGER.info("HP DEX tab verified successfully");	
	}
	
	
	/*
	 * Third party software integration for HPDex plans
	 */
	@DataProvider(name = "HPDexThirdPartIntegration")
	private Object[][] createIntegrationDataProvider() {
		Object[][] users = { 
				{ "HPDEX_IT_ADMIN", "HPDEX_IT_ADMIN_PASSWORD" },
				/*
				 * { "ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US" }, { "HPDEX_MSP_ADMIN",
				 * "HPDEX_MSP_ADMIN_PASSWORD" }, { "HPDEX_SUPPORT_SPECIALIST",
				 * "HPDEX_SUPPORT_SPECIALIST_PASSWORD" }
				 */
		};
		return users;
	}
	@Test(priority = 87,  dataProvider = "HPDexThirdPartIntegration", groups = { "REGRESSIONCOMPANIES2" }, description = "TestCase[783799]", enabled = true)
	public final void verifyHPDEXPIntegration(String email, String password) throws Exception 	
	{
		login(email, password);
		SoftAssert softAssert = new SoftAssert();
		CompaniesDetailsPage companyDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver()).getInstance();
		String hpdexCompany = getEnvironmentSpecificData(System.getProperty("environment"), "HPDEX_COMPANY");
		
		if (!getCredentials(environment, email).equals(getCredentials(environment, "HPDEX_IT_ADMIN"))) {
			tenantID = getValueFromToken("tenant");
			apiurl = cataLystURL + CommonVariables.DETAILSSEARCHSERVICEAPI + tenantID + CommonVariables.DETAILSSEARCHSERVICEAPI2;
			tenantIdCompany = getTenantDetails(apiurl, CommonVariables.SEARCH_SERVICE_IDMTENANTS, hpdexCompany);
			companyID = getIDOverviewTab(tenantIdCompany, envURL);
			gotoCompaniesTab();
			LOGGER.info("Redirected to company list page");
			waitForPageLoaded();
			impersonateCompanyByCompanyName(hpdexCompany);
			companyDetailsPage.waitUntilElementIsInvisibleOfCompanyDetailsPage("tableOverlay");
		} else {
			gotoCompanySettingsTab();
			LOGGER.info("Redirected to Settings tab");
		}

		waitForPageLoaded();
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("preferenceTab");
		LOGGER.info("Navigated to Preference Tab");
		waitForPageLoaded();
		companyDetailsPage.scrollToBottom();
		if (!companyDetailsPage.getTextOfCompaniesDetailsPage("HP-DEX-not-configured").equals(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.not_configured"))) {
			// Update HP DEX integration
			companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("HP-DEX-thirdpartyIntigration-edit");
			sleeper(1000);
			companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("data-colleaction-time-dropdown-button");
			sleeper(1000);
			String strTimeTextValue = companyDetailsPage.getTextOfCompaniesDetailsPage("data-colleaction-dropdown-timevalue-text");
			if (companyDetailsPage.getTextOfCompaniesDetailsPage("data-collection-time-dropdown-value1").equals(strTimeTextValue)) {
				companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("data-collection-time-dropdown-value2");
			} else {
				companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("data-collection-time-dropdown-value1");
			}
		} else {
			// Add HP DEX integration
			companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("HP-DEX-thirdpartyIntigration-delete");
			sleeper(1000);
			companyDetailsPage.enterTextForCompaniesDetailsPage("HP-DEX-inegration-accounturl-textbox",getEnvironmentSpecificData(environment, "HP_DEX_ACCOUNT_URL"));
			companyDetailsPage.enterTextForCompaniesDetailsPage("HP-DEX-inegration-engineurl-textbox",getEnvironmentSpecificData(environment, "HP_DEX_ENGINE_URL"));
			companyDetailsPage.enterTextForCompaniesDetailsPage("HP-DEX-inegration-email-textbox",getCredentials(environment, "HPDEX_IT_ADMIN"));
			companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("data-colleaction-time-dropdown-button");
			sleeper(1000);
			companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("data-collection-time-dropdown-value1");
			companyDetailsPage.enterTextForCompaniesDetailsPage("HP-DEX-inegration-username-textbox",getEnvironmentSpecificData(environment, "HP_DEX_USERNAME"));
			companyDetailsPage.enterTextForCompaniesDetailsPage("HP-DEX-inegration-password-textbox",getEnvironmentSpecificData(environment, "HP_DEX_PASSWORD"));
		}
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("HP-DEX-integration-save");
		companyDetailsPage.waitForElementsOfCompaniesDetailsPage("toastNotification");
		String toastNotification = companyDetailsPage.getTextOfCompaniesDetailsPage("toastNotification");
		softAssert.assertTrue(toastNotification.equals(getTextLanguage(LanguageCode, "daas_ui", "companies.details.update.success").replace("{name}",getTextLanguage(LanguageCode, "daas_ui", "company.details.thirdparty.hpdx.title").replace("{app}", getTextLanguage(LanguageCode, "daas_ui", "sidemenu.hpdx")))), "HP DEX Integration is failed");
		LOGGER.info("HP DEX Integration has been updated successfully");

		/*
		 * // Delete HP DEX integration sleeper(5000);
		 * companyDetailsPage.clickOnElementsOfCompaniesDetailsPage(
		 * "HP-DEX-thirdpartyIntigration-delete"); sleeper(1000);
		 * softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage(
		 * "HP-DEX-delete-title").equals(getTextLanguage(LanguageCode, "daas_ui",
		 * "devicelist.emmtools.remove.title"))
		 * ,"HP DEX Integration tiltle is not visible");
		 * softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage(
		 * "HP-DEX-delete-confirmation-content").equals(getTextLanguage(LanguageCode,
		 * "daas_ui", "devicelist.emmtools.remove.confirmation").replace("{name}",
		 * getTextLanguage(LanguageCode, "daas_ui",
		 * "sidemenu.hpdx"))),"HP DEX Integration is failed");
		 */
		softAssert.assertAll();
		LOGGER.info("HP-DEX Integration verified successfully");
	}
	
	/*
	 * Test Case is not valid now marking it as false
	 */
	@Test(priority = 88, groups = { "REGRESSIONCOMPANIES2" }, description = "TestCase[783799]", enabled = false)
	public final void verifyHPDEXPTabNotVisible() throws Exception 
	{
		login("IT_ADMIN_USER_NEW_EMAIL", "IT_ADMIN_USER_NEW_PASSWORD");
		CompaniesDetailsPage companyDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver()).getInstance();
		gotoCompanySettingsTab();
		LOGGER.info("Redirected to Settings tab");
		waitForPageLoaded();
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("subscriptionTab");
		LOGGER.info("Navigated to Preference Tab");
			
			ArrayList<String> arrPlansValues = new ArrayList<String>(
					Arrays.asList(
							(getTextLanguage(LanguageCode, "roles_meta_service", "plans.package_name.displayName.proactive_insights_hpdx")), 
							(getTextLanguage(LanguageCode, "roles_meta_service", "plans.package_name.displayName.hpdx_plan"))
							)
					);		
				
			if (companyDetailsPage.comparePlansOfHPDexInCompanyDetailPage("planAccountSummary", arrPlansValues) == false)
			{
				 HPDexPage hpdexPage = new HPDexPage(PreDefinedActions.getDriver()).getInstance();
				expandMenuIcon();
				Assert.assertFalse( hpdexPage.verifyElementsOfHPDexPage("hpDexTab"), "HP Dex tab visible in side-menu");		
			}
			
			LOGGER.info("HP-DEX tab is not visible verified successfully. ");		
	}
	
	/*
	 * No Longer valid so marking this as false
	 */
	
	@Test(priority = 89, groups = { "REGRESSIONCOMPANIES2" }, description = "TestCase[783799]", enabled = false)
	public final void verifyCreateCompanyForHPDexPlan() throws Exception{
		
		login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		String tenantID = null;
		LOGGER.info("Redirected to Companies tab");
		try {
		waitForPageLoaded();
		resetTableConfiguration();
		String compName = generateRandomString(10);
		String compEmail = generateRandomString(5) + "@yopmail.com";

		// Company addition, billing license, free trial on
		softAssert.assertTrue(companiesPage.addCompany(LanguageCode, compName, compEmail, CompaniesVariables.COUNTRY, getTextLanguage(LanguageCode, "roles_meta_service", "plans.package_name.displayName.proactive_insights_hpdx"), "","", "FirstName", "LastName", false, getTextLanguage(LanguageCode, "daas_ui", "subscription.list.license"), "off"), "Company failed to create");
		LOGGER.info("Added company with billing model as license, free trial is on");
		String strSubTenant = getUrlOfCurrentPage().substring(getUrlOfCurrentPage().length() - 57);
		tenantID = strSubTenant.substring(0, 36);
		
		CompaniesDetailsPage companyDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver()).getInstance();
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("subscriptionTab");
		LOGGER.info("Navigated to Subscription Tab");
			
			ArrayList<String> arrPlansValues = new ArrayList<String>(
					Arrays.asList(
							(getTextLanguage(LanguageCode, "roles_meta_service", "plans.package_name.displayName.proactive_insights_hpdx_trial"))
							)
					);		
			
			//find end date
			SimpleDateFormat sdf = new SimpleDateFormat("MMMM d, yyyy");
			Calendar c = Calendar.getInstance();
			c.setTime(new Date()); 
			c.add(Calendar.DATE, 90); 
			String strEndDate = sdf.format(c.getTime());
			
			//Verify trial subscription start date and end date 
			companiesPage.scrollOnCompaniesPage("licensesDetails");
			softAssert.assertTrue(companyDetailsPage.comparePlansOfHPDexInCompanyDetailPage("subscriptionTileValue", arrPlansValues), "HP Dex plans are not visible in company detail page");
			softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("companyTrialStartDateLabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "dashboard.widgets.business_review.start_date")), "Start Date label of hpdex trial subscription does not match on Account Summary Tile.");
			softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("companyTrialStartDate").equals(getCurrentMonthDateYear()), "Start Date of hpdex trial subscription does not match on Account Summary Tile.");
			softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("companyTrialEndDateLabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "dashboard.widgets.business_review.end_date")), "End Date label of hpdex trial subscription does not match on Account Summary Tile.");
			softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("companyTrialEndDate").equals(strEndDate), "End Date of hpdex trial subscription does not match on Account Summary Tile.");
			softAssert.assertAll();
		}
		 finally {
			// Remove created company 
			companiesPage.removeCompanyUsingAPI(tenantID);
			navigateToBack();
			LOGGER.info("verifyCreateCompanyForHPDexPlan verified successfully");
		 }
	}
	
	/**
	 * This test case will add hpdex license key to existing company
	 * 
	 * @throws Exception
	 */
	@Test(priority = 90, groups = { "REGRESSIONCOMPANIES2" }, description = "TestCase[783799]", enabled = true)
	public final void verifyLicenceKeyAssignmentToExistingCompanyForHPDexPlan() throws Exception {
		login("APP_ADMIN_USER", "APP_ADMIN_PASSWORD");
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		CompaniesDetailsPage companiesDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver()).getInstance();
		LicensesListPage subscriptionsListPage = new LicensesListPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();	
		String companyTenantID = null;
		try {
		String subKey1 = generateRandomString(12).toUpperCase();
		String subKey2 = generateRandomString(12).toUpperCase();
		Assert.assertTrue(subscriptionsListPage.createLicenceKey(getEnvironmentSpecificData(System.getProperty("environment"), "DAAS_API_BASE_URL_AUTH") + subscriptionURL, subKey1, CommonVariables.HPDEX_SKU), "Licence key creation failed");
		Assert.assertTrue(subscriptionsListPage.createLicenceKey(getEnvironmentSpecificData(System.getProperty("environment"), "DAAS_API_BASE_URL_AUTH") + subscriptionURL, subKey2,CommonVariables.HPDEX_ADDON_SKU), "Licence key creation failed");

		LOGGER.info("Created licence key " + subKey1);
		LOGGER.info("Created licence key " + subKey2);
		logout();

		login("ROOT_ADMIN_USER_WhatsNew", "ROOT_PASSWORD_WhatsNew");
		LOGGER.info("Logged in with root user");
		Assert.assertTrue(companiesPage.createCompanyUsingAPI(subKey1, getEnvironmentSpecificData(System.getProperty("environment"), "COUNTRY_CODE"), LanguageCode, getEnvironmentSpecificData(System.getProperty("environment"), "MSP_ID"), "", "", CompaniesVariables.COMPANY_STANDARDTRIAL), "Company Creation failed");
		LOGGER.info(subKey1 + " company created");
		impersonateCompanyByCompanyName(subKey1);
		LOGGER.info("Redirected to company details page");
		companyTenantID = getUrlOfCurrentPage().substring(getUrlOfCurrentPage().length() - 36);

		companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("subscriptionTab");
		LOGGER.info("Click subcription tab");
		sleeper(2000);
		companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("addNewKeyButton");
		companiesDetailsPage.enterTextForCompaniesDetailsPage("addSubscriptionKey", subKey1);
		companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("dialogSaveButton");
		softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("planOnSubscriptionModal").equals(CommonVariables.PLAN_HPDEX), "Added key plan did not match");
		softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("licenceModalHeader").equals(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.modal.subscription_title")), "License key modal header did not match");
		companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("saveSubsriptionKey");
		LOGGER.info("Added hpdex licence key to a company");

		String toastNotification = companiesDetailsPage.getTextOfCompaniesDetailsPage("toastNotification");
		softAssert.assertTrue(toastNotification.equals(getTextLanguage(LanguageCode, "daas_ui", "companies.details.update.success").replace("{name}", getTextLanguage(LanguageCode, "daas_ui", "subscription.license.details.licence_key"))), "Key assignment to a company failed");
		companiesDetailsPage.waitUntilElementIsInvisibleOfCompanyDetailsPage("toastNotification");
		softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("planStatusOnSubscriptionTile").contains(getTextLanguage(LanguageCode, "daas_ui", "companies.list.sections.active")), "Assigned key is not in active state");
		softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("subKeyOnSubscriptionTile").contains(subKey1), "Assigned key1 did not match with licence tile key");
		
		companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("addNewKeyButton");
		companiesDetailsPage.enterTextForCompaniesDetailsPage("addSubscriptionKey", subKey2);
		companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("dialogSaveButton");
		softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("planOnSubscriptionModal").equals(CommonVariables.PLAN_HPDEX_ADD_ON), "Added key plan did not match");
		softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("licenceModalHeader").equals(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.modal.subscription_title")), "License key modal header did not match");
		companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("saveSubsriptionKey");
		LOGGER.info("Added hpdex licence key to a company");

		toastNotification = companiesDetailsPage.getTextOfCompaniesDetailsPage("toastNotification");
		softAssert.assertTrue(toastNotification.equals(getTextLanguage(LanguageCode, "daas_ui", "companies.details.update.success").replace("{name}", getTextLanguage(LanguageCode, "daas_ui", "subscription.license.details.licence_key"))), "Key assignment to a company failed");
		companiesDetailsPage.waitUntilElementIsInvisibleOfCompanyDetailsPage("toastNotification");
		softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("planStatusOnSubscriptionTile").contains(getTextLanguage(LanguageCode, "daas_ui", "companies.list.sections.active")), "Assigned key is not in active state");
		softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("subKeyOnSubscriptionTile").contains(subKey2), "Assigned key2 did not match with licence tile key");

		
		ArrayList<String> arrPlansValues = new ArrayList<String>(
				Arrays.asList(
						(getTextLanguage(LanguageCode, "roles_meta_service", "plans.package_name.displayName.proactive_insights_hpdx")), 
						(getTextLanguage(LanguageCode, "roles_meta_service", "plans.package_name.displayName.hpdx_plan"))
						)
				);		
		softAssert.assertTrue(companiesDetailsPage.comparePlansOfHPDexInCompanyDetailPage("planAccountSummary", arrPlansValues), "HP Dex plans not visible in company detail page");
		softAssert.assertAll();
		LOGGER.info("HP DEX plans verified successfully");		
		}
		 finally {
				// Remove created company
				companiesPage.removeCompanyUsingAPI(companyTenantID);
				LOGGER.info("verifyLicenceKeyAssignmentToExistingCompanyForHPDexPlan verified successfully");
			}		
	}
	
	@DataProvider(name = "HPDexTabVisible")
	private Object[][] createDataProvider() {
		Object[][] users = {
				{ "HPDEX_IT_ADMIN", "HPDEX_IT_ADMIN_PASSWORD" },
				{ "HPDEX_REPORT_ADMIN", "HPDEX_REPORT_ADMIN_PASSWORD" }
		};
		return users;
	}
	@Test(priority = 91, dataProvider = "HPDexTabVisible", groups = { "REGRESSIONCOMPANIES2" }, description = "TestCase[783799]", enabled = true)
	public final void verifyHPDEXTabVisible(String email, String password) throws Exception 
	{
		login(email,password);
		 HPDexPage hpdexPage = new HPDexPage(PreDefinedActions.getDriver()).getInstance();
		expandMenuIcon();
		Assert.assertTrue( hpdexPage.verifyElementsOfHPDexPage("hpDexTab"), "HP Dex tab is not visible in side-menu");
		LOGGER.info("HPDex tab verified successfully");
		
	}
	/**
	 * This testcase Verify create company with free trial slider On with subscription model.
	 *
	 * @throws Exception
	 */

	@Test(priority = 92, groups = {"REGRESSIONCOMPANIES1", "SMOKE", "REGRESSION_CI", "SMOKE_CI"}, description = "NFR - 829744, TESTCASE-818738,818743,818715")
	public final void verifyCreateCompWithFreeSliderOnWithSubs() throws Exception {
		login("RESELLER_STAGING_COMPANIES_EMAIL", "RESELLER_STAGING_COMPANIES_PASSWORD");
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();

		SoftAssert softAssert = new SoftAssert();
		String compName = generateRandomString(10);
		String compEmail = generateRandomString(5) + "@yopmail.com";

		try {
			gotoCompaniesTab();
			LOGGER.info("Redirected to company list page");
			waitForPageLoaded();
			companiesPage.clickOnElementsOfCompaniesPage("addButton");
			companiesPage.clickOnElementsOfCompaniesPage("addCompanyDialogPopupNextButton");
			LOGGER.info("Clicked on next button");
			companiesPage.enterTextForCompaniesPage("companyNameOnPopup", compName);
			companiesPage.enterTextForCompaniesPage("cityOnPopup", CompaniesVariables.CITY);

			//Clicked on cross button
			companiesPage.clickOnElementsOfCompaniesPage("countryClearOnPopup");
			//Clicked on Open Arrow button
			companiesPage.waitForElementsOfCompaniesPage("countryArrowOnPopup");
			sleeper(2000);
			companiesPage.clickOnElementsOfCompaniesPage("countryArrowOnPopup");
			sleeper(2000);

			//Select country
			companiesPage.enterTextForCompaniesPage("countrySearchOnPopup", CompaniesVariables.COUNTRY);
			companiesPage.clickOnElementsOfCompaniesPage("countrySelectOnPopup");

			//Select Language
			companiesPage.clickOnElementsOfCompaniesPage("languageArrowOnPopup");
			companiesPage.enterTextForCompaniesPage("languageSearchOnPopup", CommonVariables.LANGUAGE);
			companiesPage.clickOnElementsOfCompaniesPage("languageSelectOnPopup");

			companiesPage.enterTextForCompaniesPage("addressOnPopup", CompaniesVariables.ADDRESS);
			companiesPage.waitForElementsOfCompaniesPage("zipCodeOnCompPopup");
			companiesPage.enterTextForCompaniesPage("zipCodeOnCompPopup", CompaniesVariables.PINCODE);
			companiesPage.waitForElementsOfCompaniesPage("stateOnCompPopup");
			companiesPage.enterTextForCompaniesPage("stateOnCompPopup", CompaniesVariables.STATE);

			//Click on Next button
			companiesPage.clickOnElementsOfCompaniesPage("addCompanyDialogPopupNextButton");
			LOGGER.info("Clicked on next button after first step");

			Thread.sleep(5000);

			//Wait for loader
			companiesPage.waitForPresenceOfElementsOfCompaniesPage("btnfreeTrial");

			//Verify Duration days and free slider for license
			softAssert.assertTrue(companiesPage.getAttributesOfCompaniesPage("btnfreeTrial", "aria-checked").equals("false"), "Toggle is enabled by default for license model");
			LOGGER.info("Verified Duration days and free slider for license");

			//Update billing dropdown value to Subscription
			companiesPage.clickOnElementsOfCompaniesPage("btnArrowBillingModel");
			companiesPage.clickOnElementsOfCompaniesPage("txtSubscriptionInDroDown");
			LOGGER.info("Updated billing dropdown to subscription");

			// Verify free trial slider is by default OFF
			softAssert.assertTrue(companiesPage.getElementSizeOnCompaniesPage("btnFreeSliderOFF").equals(CompaniesVariables.VALUEZERO), "Toggle is  enabled by default for Subcription model");
			LOGGER.info("Verified free trial slider is by default OFF");

			//Verify Duration and days text should not appear when free trial is OFF
			softAssert.assertFalse(companiesPage.verifyElementsOfCompaniesPage("txtTrialDuration"), "Trial Duration is appearing for Subscription model when slider is OFF");
			softAssert.assertFalse(companiesPage.verifyElementsOfCompaniesPage("txtDays"), "Duration days text is appearing for Subscription model when slider is OFF");
			LOGGER.info("Verified Duration and days text should not appear when free trial is OFF");

			//Verify start date, end date fields for Subscription when free trial is OFF
			softAssert.assertTrue(companiesPage.verifyElementsOfCompaniesPage("txtEndDate"), " End date is not appearing for Subscription  model when slider is OFF");
			softAssert.assertTrue(companiesPage.verifyElementsOfCompaniesPage("txtSeat"), "Seat field is not appearing for Subscription  model when slider is OFF");
			LOGGER.info("Verified start date, end date fields for Subscription when free trial is OFF");

			//Make free slider ON
			companiesPage.clickOnElementsOfCompaniesPage("btnfreeTrial");
			LOGGER.info("Verified free slider is ON");

			//Verify Duration and days field still appear
			softAssert.assertTrue(companiesPage.verifyElementsOfCompaniesPage("txtDays"), "Duration days text is not appearing for Subscription model when slider is ON");
			softAssert.assertTrue(companiesPage.verifyElementsOfCompaniesPage("txtTrialDuration"), "Trial Duration is not appearing for Subscription model when slider is ON");
			LOGGER.info("Verified Duration and days field still appear");

			//verify seats,start date, end date fields for Subscription should not appear
			softAssert.assertFalse(companiesPage.verifyElementsOfCompaniesPage("txtStartDate"), " Start Date Field is appearing for Subscription model when slider is ON");
			softAssert.assertFalse(companiesPage.verifyElementsOfCompaniesPage("txtEndDate"), " End date field is appearing for Subscription model when slider is ON");
			softAssert.assertFalse(companiesPage.verifyElementsOfCompaniesPage("txtSeat"), "Seat field is appearing for Subscription model when slider is ON");
			LOGGER.info("Verified seats,start date, end date fields for Subscription should not appear");

			//Click On Next button
			companiesPage.clickOnElementsOfCompaniesPage("addCompanyDialogPopupNextButton");
			companiesPage.enterTextForCompaniesPage("firstNameOnPopup", CompaniesVariables.FIRST_NAME);
			companiesPage.enterTextForCompaniesPage("lastNameOnPopup", CompaniesVariables.COMPANIES_TITLE);
			companiesPage.enterTextForCompaniesPage("emailOnPopup", compEmail);
			companiesPage.enterTextForCompaniesPage("phoneNumberOnPopup", CompaniesVariables.NUMBER);
			sleeper(2000);
			companiesPage.waitUntilElementIsInvisibleOfCompanyPageDynamic("btnCompanyCreate",20);
			companiesPage.clickByJavaScriptOnCompaniesPage("addCompanyDailogPopupCreateButton");
			LOGGER.info("Clicked on Create button");
			//Create company API takes 60 seconds
			sleeper(60000);
			waitForPageLoaded();

			//Verify trial text should appear with Plan name on company detail page
			softAssert.assertTrue(companiesPage.getTextOfCompanyPage("txtPlanName").contains(getTextLanguage(LanguageCode, "daas_ui", "subscription.list.is_trial").replace("Is ", "").toUpperCase()));
			LOGGER.info("verified trial text should  not appear with plan name");

			softAssert.assertAll();

		} finally {
			//Logout from account
			logout();
			//login from root
			login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
			LOGGER.info("Redirected to company list page");
			waitForPageLoaded();
			impersonateCompanyByCompanyName(compName);
			LOGGER.info("Redirected to company details page");
			tenantID = getUrlOfCurrentPage().substring(getUrlOfCurrentPage().length() - 36);

			// Remove created company
			softAssert.assertTrue(companiesPage.removeCompanyUsingAPI(tenantID), "Company removal failed");
			LOGGER.info("Company has been deleted");
		}
	}

	/**
	 * This testcase Verify create company with free trial slider OFF with subscription model
	 *
	 * @throws Exception
	 */
	@Test(priority = 93, groups = {"REGRESSIONCOMPANIES1", "SMOKE", "REGRESSION_CI", "SMOKE_CI"}, description = "TESTCASE-818739")
	public final void verifyCreateCompWithFreeSliderOFFWithSubs() throws Exception {
		login("RESELLER_STAGING_COMPANIES_EMAIL", "RESELLER_STAGING_COMPANIES_PASSWORD");
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		CompaniesDetailsPage companiesDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver()).getInstance();

		SoftAssert softAssert = new SoftAssert();
		String compName = generateRandomString(10);
		String compEmail = generateRandomString(5) + "@yopmail.com";

		try {
			gotoCompaniesTab();
			LOGGER.info("Redirected to company list page");
			waitForPageLoaded();
			companiesPage.clickOnElementsOfCompaniesPage("addButton");
			companiesPage.clickOnElementsOfCompaniesPage("addCompanyDialogPopupNextButton");
			LOGGER.info("Clicked on next button");
			companiesPage.enterTextForCompaniesPage("companyNameOnPopup", compName);
			companiesPage.enterTextForCompaniesPage("cityOnPopup", CompaniesVariables.CITY);

			//Select country
			companiesPage.clickOnElementsOfCompaniesPage("countryClearOnPopup");
			companiesPage.waitForElementsOfCompaniesPage("countryArrowOnPopup");
			sleeper(2000);
			companiesPage.clickOnElementsOfCompaniesPage("countryArrowOnPopup");
			sleeper(2000);

			companiesPage.enterTextForCompaniesPage("countrySearchOnPopup", CompaniesVariables.COUNTRY);
			companiesPage.clickOnElementsOfCompaniesPage("countrySelectOnPopup");

			//Select language
			companiesPage.clickOnElementsOfCompaniesPage("languageArrowOnPopup");
			companiesPage.enterTextForCompaniesPage("languageSearchOnPopup", CommonVariables.LANGUAGE);
			companiesPage.clickOnElementsOfCompaniesPage("languageSelectOnPopup");

			companiesPage.enterTextForCompaniesPage("addressOnPopup", CompaniesVariables.ADDRESS);
			companiesPage.waitForElementsOfCompaniesPage("zipCodeOnCompPopup");
			companiesPage.enterTextForCompaniesPage("zipCodeOnCompPopup", CompaniesVariables.PINCODE);
			companiesPage.waitForElementsOfCompaniesPage("stateOnCompPopup");
			companiesPage.enterTextForCompaniesPage("stateOnCompPopup", CompaniesVariables.STATE);

			companiesPage.clickOnElementsOfCompaniesPage("addCompanyDialogPopupNextButton");
			LOGGER.info("Clicked on next button after first step");

			//After clicking on Next button API takes time
			Thread.sleep(5000);

			//Wait for loader
			companiesPage.waitForPresenceOfElementsOfCompaniesPage("txtTrialDuration");

			//Update billing dropdown value to Subscription
			companiesPage.clickOnElementsOfCompaniesPage("btnArrowBillingModel");
			companiesPage.clickOnElementsOfCompaniesPage("txtSubscriptionInDroDown");
			LOGGER.info("Updated billing dropdown to subscription");

			//Click On Next button
			companiesPage.clickOnElementsOfCompaniesPage("addCompanyDialogPopupNextButton");

			//Verify Error message for mandatory start date field.
			softAssert.assertTrue(companiesPage.getTextOfCompanyPage("txtStartDateErrMsg").equals(getTextLanguage(LanguageCode, "daas_ui", "form.validate.required")));

			//Select start date ,end date and seats
			companiesPage.clickOnElementsOfCompaniesPage("openCalenderStartDate");
			companiesPage.clickOnElementsOfCompaniesPage("selectToday");
			companiesPage.waitForElementsOfCompaniesPage("openCalenderEndDate");
			companiesPage.clickOnElementsOfCompaniesPage("openCalenderEndDate");
			sleeper(5000);
			companiesPage.waitForElementsOfCompaniesPage("monthKeyCurrent");
			companiesPage.selectDateFromCalenderOnCompanyPage(addDaysToCurrentDate(2), "monthKeyCurrent",
					"endDateDialogRightArrow", "daysOnCurrentMonthKey");
			sleeper(2000);

			companiesPage.enterTextForCompaniesPage("txtSeat", CompaniesVariables.SEATCOUNT_THREE);
			LOGGER.info("Start date ,end date and seats are selected");

			//Click On Next button
			companiesPage.clickOnElementsOfCompaniesPage("addCompanyDialogPopupNextButton");

			companiesPage.enterTextForCompaniesPage("firstNameOnPopup", CompaniesVariables.FIRST_NAME);
			companiesPage.enterTextForCompaniesPage("lastNameOnPopup", CompaniesVariables.COMPANIES_TITLE);
			companiesPage.enterTextForCompaniesPage("emailOnPopup", compEmail);
			companiesPage.enterTextForCompaniesPage("phoneNumberOnPopup", CompaniesVariables.NUMBER);
			sleeper(2000);

			//Clicked on create button
			companiesPage.clickByJavaScriptOnCompaniesPage("addCompanyDailogPopupCreateButton");
			companiesPage.waitUntilElementIsInvisibleOfCompanyPageDynamic("btnCompanyCreate",20);
			LOGGER.info("Clicked on Create button");
			//Create company API takes 60 seconds
			sleeper(60000);
			waitForPageLoaded();

			//Verify "trial" text should not appear with Plan name
			softAssert.assertFalse(companiesPage.getTextOfCompanyPage("txtPlanName").contains(getTextLanguage(LanguageCode, "daas_ui", "subscription.list.is_trial").replace("Is ", "")));
			LOGGER.info("verified trial text with plan name");

			//Verify "seat" count on company detail page
			softAssert.assertTrue(companiesPage.getTextOfCompanyPage("txtSeatCount").contains(CompaniesVariables.SEATCOUNT_THREE));
			LOGGER.info("verified seat count on company detail page");

			//Click On edit icon
			companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("btnEditIcon");
			companiesDetailsPage.waitForElementsOfCompaniesDetailsPage("txtEditSeat");
			companiesDetailsPage.clearTextForCompaniesDetailsPage("txtEditSeat");
			companiesDetailsPage.enterTextForCompaniesDetailsPage("txtEditSeat",CompaniesVariables.SEATCOUNT_TWO);
			companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("btnSaveOnEditPopUp");
			companiesDetailsPage.waitForElementsOfCompaniesDetailsPage("txtToastNotification");

			//Verify Toast Notification
			softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("txtToastNotification").equals(getTextLanguage(LanguageCode, "daas_ui", "global.messages.field_update_successful").replace("{field}",getTextLanguage(LanguageCode, "daas_ui", "product.list.daasPlan"))));

			//Verify "seat" count on company detail page
			softAssert.assertTrue(companiesPage.getTextOfCompanyPage("txtSeatCount").contains(CompaniesVariables.SEATCOUNT_TWO));
			LOGGER.info("verified seat count on company detail page");

			softAssert.assertAll();

		} finally {

			//Logout from account
			logout();
			//login from root
			login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
			LOGGER.info("Redirected to company list page");
			waitForPageLoaded();
			impersonateCompanyByCompanyName(compName);
			LOGGER.info("Redirected to company details page");
			tenantID = getUrlOfCurrentPage().substring(getUrlOfCurrentPage().length() - 36);

			softAssert.assertTrue(companiesPage.removeCompanyUsingAPI(tenantID), "Company removal failed");
			LOGGER.info("Company has been deleted");
		}
	}

	/**
	 * This testcase verify Free trial slider should not appear with subscription model via root login
	 *
	 * @throws Exception
	 */

	@Test(priority = 94, groups = {"REGRESSIONCOMPANIES1", "SMOKE", "REGRESSION_CI", "SMOKE_CI"}, description = "TESTCASE-818739")
	public final void verifyFreeSliderForSubsWithRootUser() throws Exception {
		login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		String compName = generateRandomString(10);

		 //Click on Add company button
			companiesPage.clickOnElementsOfCompaniesPage("addButton");
			companiesPage.clickOnElementsOfCompaniesPage("addCompanyDialogPopupNextButton");
			LOGGER.info("Clicked on next button");
			companiesPage.enterTextForCompaniesPage("companyNameOnPopup", compName);
			companiesPage.enterTextForCompaniesPage("cityOnPopup", CompaniesVariables.CITY);

			//Select country
			companiesPage.waitForElementsOfCompaniesPage("countryArrowOnPopup");
			sleeper(2000);
			companiesPage.clickOnElementsOfCompaniesPage("countryArrowOnPopup");
			sleeper(2000);

			companiesPage.enterTextForCompaniesPage("countrySearchOnPopup", CompaniesVariables.COUNTRY);
			companiesPage.clickOnElementsOfCompaniesPage("countrySelectOnPopup");

			//Select langauge
			companiesPage.clickOnElementsOfCompaniesPage("languageArrowOnPopup");
			companiesPage.enterTextForCompaniesPage("languageSearchOnPopup", CommonVariables.LANGUAGE);
			companiesPage.clickOnElementsOfCompaniesPage("languageSelectOnPopup");

		companiesPage.enterTextForCompaniesPage("addressOnPopup", CompaniesVariables.ADDRESS);
		companiesPage.waitForElementsOfCompaniesPage("zipCodeOnCompPopup");
		companiesPage.enterTextForCompaniesPage("zipCodeOnCompPopup", CompaniesVariables.PINCODE);
		companiesPage.waitForElementsOfCompaniesPage("stateOnCompPopup");
		companiesPage.enterTextForCompaniesPage("stateOnCompPopup", CompaniesVariables.STATE);

			companiesPage.clickOnElementsOfCompaniesPage("addCompanyDialogPopupNextButton");
			LOGGER.info("Clicked on next button after first step");

		    //After clicking on Next button API takes time
		    Thread.sleep(8000);

			//Wait for loader
			companiesPage.waitForPresenceOfElementsOfCompaniesPage("txtTrialDuration");

			//Update billing dropdown value to Subscription
			companiesPage.clickOnElementsOfCompaniesPage("btnArrowBillingModel");
			companiesPage.clickOnElementsOfCompaniesPage("txtSubscriptionInDroDown");
			LOGGER.info("Updated billing dropdown to subscription");

			//Verify slider,Duration and days text should not appear
			softAssert.assertFalse(companiesPage.verifyElementsOfCompaniesPage("txtTrialDuration"), "Trial Duration is appearing for Subscription model");
			softAssert.assertFalse(companiesPage.verifyElementsOfCompaniesPage("txtDays"), "Duration days text is appearing for Subscription model");
			LOGGER.info("Verified Duration and days text should not appear");
			softAssert.assertFalse(companiesPage.verifyElementsOfCompaniesPage("btnfreeTrial"), "free trial slider is appearing for subscription");
			LOGGER.info("Verified free slider toggle is not appearing");

			softAssert.assertAll();

	}

	/**
	 * This test case Verifies the company details page for LOE2 partners
	 * User Story 835191: [Core] LOE2 should have the same views as LOE3 / LO4
	 *
	 * @throws Exception
	 */
	@Test(priority = 95, groups = {"REGRESSIONCOMPANIES2"}, description = "Test case = 843348")
	public final void verifyCompanyDetailsPageForLOE2Partners() throws Exception {
		login("RESELLER_NEW_UI_Companies_EMAIL_COMPANIES", "RESELLER_NEW_UI_Companies_PASSWORD_COMPANIES");
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		CompaniesDetailsPage companiesDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();

		waitForPageLoaded();
		gotoCompaniesTab();
		waitForPageLoaded();
		LOGGER.info("Redirected to companies tab");
		companiesPage.clearFiltersOfCompaniesListPage("clearfilter");
		impersonateCompanyByCompanyName(getEnvironmentSpecificData(System.getProperty("environment"), "COMPANY_NAME_DETAIL_PLANSLIST_RESELLER"));
		LOGGER.info("Redirected to companies details page");
		companiesDetailsPage.waitForElementsOfCompaniesDetailsPage("detailsTileHeader");
		companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("companySubscriptionTab");
		waitForPageLoaded();
		companiesDetailsPage.waitForElementsOfCompaniesDetailsPage("accountSummaryTileHeader");
		softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("accountSummaryTileHeader").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.subscription")), "Plan overview tile did not load on company details page");

		softAssert.assertAll();
		LOGGER.info("Verified companies details page for LOE2 partners");
	}
	
	@Test(priority = 96, groups = {"REGRESSIONCOMPANIES2"}, description = "Test case : 853769, 852883, 852882, 853780, 852881,922360,922341,902533, 902527,253960 ")
	public final void verifyMixedPlanLicenceSubscription() throws Exception {
		login("APP_ADMIN_USER", "APP_ADMIN_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		CompaniesDetailsPage companiesDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver()).getInstance();
		LicensesListPage subscriptionsListPage = new LicensesListPage(PreDefinedActions.getDriver()).getInstance();
		String subKey2 = "MIXEDPLANCOMPANY"+generateRandomString(12).toUpperCase();
		Assert.assertTrue(subscriptionsListPage.createLicenceKey(getEnvironmentSpecificData(System.getProperty("environment"), "DAAS_API_BASE_URL_AUTH") + subscriptionURL, subKey2, CompaniesVariables.SKU_LICTOSUB), "Licence key creation failed");
		LOGGER.info("Created licence key " + subKey2);
		logout();

		try {
			//Login with Partner
			login("PARTNER_LOGIN_ID", "PARTNER_LOGIN_PASSWORD");
			LOGGER.info("Logged in with partner user");
	
			Assert.assertTrue(companiesPage.createCompanyUsingAPI(subKey1, getEnvironmentSpecificData(System.getProperty("environment"), "COUNTRY_CODE"), LanguageCode, getEnvironmentSpecificData(System.getProperty("environment"), "MSP_ID"), getEnvironmentSpecificData(System.getProperty("environment"), "PARTNER_ID2"), getEnvironmentSpecificData(System.getProperty("environment"), "MIXPLAN_COMPANY_THROUGH_ROOT_PARTNER_EMAIL"), CompaniesVariables.COMPANY_STANDARDTRIAL), "Company Creation failed");
			LOGGER.info(subKey1 + " company created");
			gotoCompaniesTab();
			impersonateCompanyByCompanyName(subKey1);
			LOGGER.info("Redirected to company details page");
			
			//Verify Add license to company
			companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("subscriptionTab");
			LOGGER.info("Click subcription tab");
			Thread.sleep(2000);
			softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("subscriptionTitlePlanTab").equals(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.subscription")), "Subscription Title is not presnet in PLans tab of company");
			softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("licenseTitlePlanTab").equals(getTextLanguage(LanguageCode, "daas_ui", "sidemenu.subscriptions")), "Licenses Title is not presnet in PLans tab of company");
			softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("noSubscriptions").equals(getTextLanguage(LanguageCode, "daas_ui", "global.no_field").replace("{field}",getTextLanguage(LanguageCode, "daas_ui", "companies.list.subscription"))), "No subcription Title is not presnet in PLans tab of company");
			softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("noSubscriptionsDesc").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "subscription.add_new").replace("{plan_type}", getTextLanguage(LanguageCode, "daas_ui", "companies.list.subscription"))), "No subcription description is not presnet in PLans tab of company");
			companiesDetailsPage.clickByJavaScriptOnCompaniesDetailsPage("addNewKeyButton");
			companiesDetailsPage.clickByJavaScriptOnCompaniesDetailsPage("nextButton");
			softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("addNewKeyDesc").equals(getTextLanguage(LanguageCode, "daas_ui", "partner.addKey.manually.title.desc_mixed")), "Add New key description is not matching in Plans tab of company");
			companiesDetailsPage.enterTextForCompaniesDetailsPage("addSubKey", subKey2);
			softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("planOnSubModal").equals(CommonVariables.PLAN_PROACTIVE), "Added key plan did not match");
			softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("licModalHeader").equals(getTextLanguage(LanguageCode, "daas_ui", "partner.add.key.title")), "License key modal header did not match");
			Thread.sleep(2000);
			companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("addbuttonModelpopup");
			LOGGER.info("Added licence key to a company");
	
			String toastNotification = companiesDetailsPage.getTextOfCompaniesDetailsPage("toastNotification");
			softAssert.assertTrue(toastNotification.equals(getTextLanguage(LanguageCode, "daas_ui", "companies.details.update.success").replace("{name}", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.subscription.modal.title"))), "Key assignment to a company failed");
			companiesDetailsPage.waitUntilElementIsInvisibleOfCompanyDetailsPage("toastNotification");
			softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("licenseDetailsHeader").equals(getTextLanguage(LanguageCode, "daas_ui", "subscription.license.subscription_details")), "License Details header is not matching in Plans tab of company");
			softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("planStatusOnSubscriptionTile").contains(getTextLanguage(LanguageCode, "daas_ui", "companies.list.sections.active")), "Assigned key is not in active state");
			softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("subKeyOnSubscriptionTile").contains(subKey1), "Assigned key1 did not match with licence tile key");
			companiesDetailsPage.clickByJavaScriptOnCompaniesDetailsPage("convertToSubModButton");
				
			//Verify Convert To Subscription Model details
			softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("headingonSubscriptionpopup").contains(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.convert_to_subscription")), "Convert To Subscription model heading is not matching");
			softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("messageOnSubscriptionpopup").contains(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.convert_to_subscription_title1")), "Text on Convert To Subscription model is not matching");
			softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("InnerMessageOnSubscriptionpopup").contains(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.convert_to_subscription_message1")), "Inner Text on Convert To Subscription model is not matching");
			softAssert.assertTrue(companiesDetailsPage.verifyElementsOfCompaniesDetailsPage("convertBtnOnSub"),"Convert button is not available");
			softAssert.assertTrue(companiesDetailsPage.verifyElementsOfCompaniesDetailsPage("cancelBtnOnSub"),"Cancel button is not available");
			companiesDetailsPage.clickByJavaScriptOnCompaniesDetailsPage("convertBtnOnSub");
			companiesDetailsPage.waitForElementsOfCompaniesDetailsPage("convertToastNotification");
			toastNotification = companiesDetailsPage.getTextOfCompaniesDetailsPage("convertToastNotification");
			softAssert.assertTrue(toastNotification.equals(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.convert_to_subscription.start_success")), "Licenses converted start message is not shown");
			LOGGER.info("License to Subscription converison is started");
			Thread.sleep(5000);
			
			softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("planOnPlanDetailsCards").equals(CompaniesVariables.PLAN_ON_DETAILS), "plan did not match");
			softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("licenseKey").contains(subKey1), "plan did not match");
			softAssert.assertFalse(companiesDetailsPage.verifyElementsOfCompaniesDetailsPage("deleteIcon"), "Delete Icon is available");
			softAssert.assertFalse(companiesDetailsPage.verifyElementsOfCompaniesDetailsPage("editIcon"), "Edit Icon is available");
			softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("statusOnPlanDetailsCards").contains(getTextLanguage(LanguageCode, "daas_ui", "companies.list.sections.active")), "Assigned Subscription is not in active state");
			softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("seatsOnAddSubscription").contains((getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.subscription.total_seats")) + " : " + CompaniesVariables.SEATCOUNT_100), "Seats didn't match");
			Date dNow = new Date();
			SimpleDateFormat formatTime = new SimpleDateFormat("MM/dd/yyyy");
			String todayDate = formatTime.format(dNow);
			//softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("convertedMsg").equals((getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.subscription.convertedOn"))+ " : " +todayDate), "Converted message after converting from license to Subscription is incorrect.");  // will uncomment once maestro string is available.
			LOGGER.info("Verified License details after adding new License");
			
			//Verify Add Subscription in Company and Verify details on Add Subscription popup
			companiesDetailsPage.clickByJavaScriptOnCompaniesDetailsPage("addSubscriptionButton");
			companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("planDropdownButton");
			companiesDetailsPage.selectDropdownSingleValueTextFromCompaniesDetailsPage("planDropdownList",CommonVariables.PLAN_PROACTIVE,"valueinDropdown");
			companiesDetailsPage.enterTextForCompaniesDetailsPage("seats", CompaniesVariables.SEATCOUNT_TWO);
			companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("startdate");
			companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("todayDate");
			String date = companiesDetailsPage.getAttributesOfCompaniesDetailsPage("startdate","value");
			softAssert.assertTrue(companiesDetailsPage.verifyElementsOfCompaniesDetailsPage("addButton"), "Add Button is not available");
			softAssert.assertTrue(companiesDetailsPage.verifyElementsOfCompaniesDetailsPage("cancelButton"), "Cancel Button is not available");
			companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("addButton");
			
			toastNotification = companiesDetailsPage.getTextOfCompaniesDetailsPage("toastNotification");
			softAssert.assertTrue(toastNotification.equals(getTextLanguage(LanguageCode, "daas_ui", "global.messages.field_add_success").replace("{field}",
					getTextLanguage(LanguageCode, "daas_ui", "product.list.daasPlan"))), "Subscriptions converted message is not shown");
			LOGGER.info("Added Subscription to the company");
			softAssert.assertFalse(companiesDetailsPage.verifyElementsOfCompaniesDetailsPage("convertToLicModel"), "Convert to license model button is visible");
			
			//Verify plan details after Subscription added
			softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("subscriptionDetailsHeader").equals(getTextLanguage(LanguageCode, "daas_ui", "subscription.details")), "Subscription Details header is not matching in Plans tab of company");
			softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("planOnPlanDetailsCards").equals(CompaniesVariables.PLAN_ON_DETAILS), "plan did not match");
			softAssert.assertTrue(companiesDetailsPage.verifyElementsOfCompaniesDetailsPage("deleteIcon"), "Delete Icon is not available");
			softAssert.assertTrue(companiesDetailsPage.verifyElementsOfCompaniesDetailsPage("editIcon"), "Edit Icon is not available");
			softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("statusOnPlanDetailsCards").contains(getTextLanguage(LanguageCode, "daas_ui", "companies.list.sections.active")), "Assigned Subscription is not in active state");
			softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("startDateOnPlanDetailsCards").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "campaign.details.startDate") + " : " + date) , "Start Date did't match");
			softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("seatsOnPlanDetailsCards").contains((getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.subscription.total_seats")) + " : " + CompaniesVariables.SEATCOUNT_TWO), "Seats didn't match");
			softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("renewalTextOnPlanDetailsCards").contains((getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.subscription.renewal")) + " : " + (getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.subscription.auto"))) , "Renewal didn't match");
			LOGGER.info("Verified Subscription details after adding new subscription.");
			
			//Verify side menu links
			softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("subscriptionLink").equals(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.subscription")), "Subscription Sidemenu link is not presnet in PLans tab of company");
			softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("licenseLink").equals(getTextLanguage(LanguageCode, "daas_ui", "sidemenu.subscriptions")), "License Sidemenu link is not presnet in PLans tab of company");
			softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("subscriptionDetailsLink").equals(getTextLanguage(LanguageCode, "daas_ui", "subscription.details")), "Subscription Details Sidemenu link is not presnet in PLans tab of company");
			
			//Verify No License Section.
			softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("noLicenses").equals(getTextLanguage(LanguageCode, "daas_ui", "global.no_field").replace("{field}",getTextLanguage(LanguageCode, "daas_ui", "subscription.list.license"))), "No License Title is not presnet in PLans tab of company");
			softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("noLicensesDesc").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "subscription.add_new").replace("{plan_type}", getTextLanguage(LanguageCode, "daas_ui", "subscription.list.license"))), "No License description is not presnet in PLans tab of company");
			LOGGER.info("Verified No License section.");
			
			//Verify Billing Model Coumn
			gotoCompaniesTab();
			sleeper(3000);//result takes time to come
			refreshPage();
			waitForPageLoaded();
			companiesPage.scrollOnCompaniesPage("companyBillingModelCol");
			Assert.assertTrue(companiesPage.getTextOfCompaniesPage("companyBillingModelCol").contains(getTextLanguage(LanguageCode, "daas_ui", "companies.list.subscription")), "Billing Model value is not proper");
			LOGGER.info("Billing Model Column value is verified successfully.");
			
			LOGGER.info("Verified Mixed License and Subcription successfully.");
			softAssert.assertAll();
			}
		
		catch (Exception e) {
			LOGGER.error("Exception occured method verifyMixedPlanLicenceSubscription" + e.getMessage());
		}
	}
	
	@Test(priority = 96, groups = {"REGRESSIONCOMPANIES2"}, description = "Test case : 922369")
	public final void verifyMixedPlanLicenceSubscriptionCompanyLogin() throws Exception {
		login("RA_HELP_AND_SUPPORT_EMAIL", "RA_HELP_AND_SUPPORT_PASSWORD");
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		CompaniesDetailsPage companiesDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver()).getInstance();

			waitForPageLoaded();
			Thread.sleep(3000);
			pressKey(Keys.ESCAPE);
			gotoNonMSPSettingsTab();
			LOGGER.info("Redirected to Settings tab");
	
			companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("subscriptionTab");
			LOGGER.info("Click subcription tab");
			Thread.sleep(2000);
			companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("namesectiontile");
			sleeper(2000);
			softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("subscriptionTitlePlanTab").equals(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.subscription")), "Subscription Title is not presnet in PLans tab of company");
			softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("licenseTitlePlanTab").equals(getTextLanguage(LanguageCode, "daas_ui", "sidemenu.subscriptions")), "Licenses Title is not presnet in PLans tab of company");
			//softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("subscriptionDetailsHeader").equals(getTextLanguage(LanguageCode, "daas_ui", "subscription.details")), "Subscription Details header is not matching in Plans tab of company");
			
			//Verify No License Section.
			softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("noLicenses").equals(getTextLanguage(LanguageCode, "daas_ui", "global.no_field").replace("{field}",getTextLanguage(LanguageCode, "daas_ui", "subscription.list.license"))), "No License Title is not presnet in PLans tab of company");
			softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("noLicensesDesc").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "subscription.add_new").replace("{plan_type}", getTextLanguage(LanguageCode, "daas_ui", "subscription.list.license"))), "No License description is not presnet in PLans tab of company");
			LOGGER.info("Verified No License section.");
			
			//Verify side menu links
			softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("subscriptionLink").equals(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.subscription")), "Subscription Sidemenu link is not presnet in PLans tab of company");
			softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("licenseLink").equals(getTextLanguage(LanguageCode, "daas_ui", "sidemenu.subscriptions")), "License Sidemenu link is not presnet in PLans tab of company");
			softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("subscriptionDetailsLink").equals(getTextLanguage(LanguageCode, "daas_ui", "subscription.details")), "Subscription Details Sidemenu link is not presnet in PLans tab of company");
			
			LOGGER.info("Verified Mixed License and Subcription successfully with Company Login.");
			softAssert.assertAll();

	}
	
	/**
	 * This test verifies 'Convert to Subscription Model' button is not seen under plans tab of 
	 * companies detail page if the company has two different plans (PI and PEM)
	 */
	
	@Test(priority = 97, groups = {"REGRESSIONCOMPANIES2"}, description = "Test case : 853761")
	public final void verifyDualPlanLicenceToSubscriptionConversion() throws Exception {
		login("APP_ADMIN_USER", "APP_ADMIN_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		CompaniesDetailsPage companiesDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver()).getInstance();
		LicensesListPage subscriptionsListPage = new LicensesListPage(PreDefinedActions.getDriver()).getInstance();
		String subKey1 = generateRandomString(12).toUpperCase();
		String subKey2 = generateRandomString(12).toUpperCase();
		Assert.assertTrue(subscriptionsListPage.createLicenceKey(getEnvironmentSpecificData(System.getProperty("environment"), "DAAS_API_BASE_URL_AUTH") + subscriptionURL, subKey1, CompaniesVariables.SKU_LICTOSUB), "Licence key creation failed");
		Assert.assertTrue(subscriptionsListPage.createLicenceKey(getEnvironmentSpecificData(System.getProperty("environment"), "DAAS_API_BASE_URL_AUTH") + subscriptionURL, subKey2, CompaniesVariables.SKU_PEM_LICTOSUB), "Licence key creation failed");

		LOGGER.info("Created licence key " + subKey1);
		LOGGER.info("Created licence key " + subKey2);
		logout();

		try {
			//Login with Partner
			login("RESELLER_PARTNERS_EMAIL", "RESELLER_PARTNERS_PASSWORD");
			LOGGER.info("Logged in with partner user");
	
			Assert.assertTrue(companiesPage.createCompanyUsingAPI(subKey1, getEnvironmentSpecificData(System.getProperty("environment"), "COUNTRY_CODE"), LanguageCode, "", getEnvironmentSpecificData(System.getProperty("environment"), "PARTNER_ID3"), CompaniesVariables.COMPANY_NAME_SURECLICKUS, CompaniesVariables.COMPANY_STANDARDTRIAL), "Company Creation failed");
			LOGGER.info(subKey1 + " company created");
			gotoCompaniesTab();
			impersonateCompanyByCompanyName(subKey1);
			LOGGER.info("Redirected to company details page");
	
			companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("subscriptionTab");
			LOGGER.info("Click subcription tab");
			Thread.sleep(2000);
			companiesDetailsPage.clickByJavaScriptOnCompaniesDetailsPage("addNewKeyButton");
			companiesDetailsPage.clickByJavaScriptOnCompaniesDetailsPage("nextButton");
			companiesDetailsPage.enterTextForCompaniesDetailsPage("addSubKey", subKey1);
			softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("planOnSubModal").equals(CommonVariables.PLAN_PROACTIVE), "Added key plan did not match");
			softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("licModalHeader").equals(getTextLanguage(LanguageCode, "daas_ui", "partner.add.key.title")), "License key modal header did not match");
			Thread.sleep(2000);
			companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("addbuttonModelpopup");
			LOGGER.info("Added licence key 1 to a company");
	
			String toastNotification = companiesDetailsPage.getTextOfCompaniesDetailsPage("toastNotification");
			softAssert.assertTrue(toastNotification.equals(getTextLanguage(LanguageCode, "daas_ui", "companies.details.update.success").replace("{name}", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.subscription.modal.title"))), "Key assignment to a company failed");
			companiesDetailsPage.waitUntilElementIsInvisibleOfCompanyDetailsPage("toastNotification");
			softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("planStatusOnSubscriptionTile").contains(getTextLanguage(LanguageCode, "daas_ui", "companies.list.sections.active")), "Assigned key is not in active state");
			softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("subKeyOnSubscriptionTile").contains(subKey1), "Assigned key1 did not match with licence tile key");
			softAssert.assertTrue(companiesDetailsPage.verifyElementsOfCompaniesDetailsPage("convertToSubModButton"),"Convert To Subscription Model button is not available");
				
			companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("addNewKeyButton");
			companiesDetailsPage.clickByJavaScriptOnCompaniesDetailsPage("nextButton");
			companiesDetailsPage.enterTextForCompaniesDetailsPage("addSubKey", subKey2);
			softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("planOnSubModal").equals("HP Proactive Endpoint Management"), "Added key plan did not match");
			softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("licModalHeader").equals(getTextLanguage(LanguageCode, "daas_ui", "partner.add.key.title")), "License key modal header did not match");
			Thread.sleep(2000);
			companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("addbuttonModelpopup");
			LOGGER.info("Added licence key 2 to a company");

			toastNotification = companiesDetailsPage.getTextOfCompaniesDetailsPage("toastNotification");
			softAssert.assertTrue(toastNotification.equals(getTextLanguage(LanguageCode, "daas_ui", "companies.details.update.success").replace("{name}", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.subscription.modal.title"))), "Key assignment to a company failed");
			softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("planStatusOnSubscriptionTile").contains(getTextLanguage(LanguageCode, "daas_ui", "companies.list.sections.active")), "Assigned key is not in active state");
			companiesDetailsPage.scrollOnCompaniesDetailsPage("subKeyOnSubscriptionTile");
			softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("subKeyOnSubscriptionTile").contains(subKey2), "Assigned key2 did not match with licence tile key");
			softAssert.assertFalse(companiesDetailsPage.verifyElementsOfCompaniesDetailsPage("convertToSubModButton"),"Convert To Subscription Model button is available");
			
			softAssert.assertAll();
	
			}
		finally {
			// Logout from account
			logout();
			login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
			// Remove created company
			impersonateCompanyByCompanyName(subKey1);
			String tenantID = getUrlOfCurrentPage().substring(getUrlOfCurrentPage().length() - 36);
			companiesPage.removeCompanyUsingAPI(tenantID);
		}
	}

	/**
	 * This test verifies with company login 'Convert to Subscription Model' button is not seen under setting tab and  verifies edit and delete icon after converting it from license to subscription
	 */

	@Test(priority = 98, groups = {"REGRESSIONCOMPANIES2"}, description = "Test case : 852885",enabled=false)
	public final void verifyButtonActionsOnCompanyLogin() throws Exception {
		login("LICTOSUBSCONVERTED_COMPANY", "LICTOSUBSCONVERTED_COMPANY_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		CompaniesDetailsPage companiesDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver()).getInstance();
		gotoCompanySettingsTab();
		// Verify Preferences section
		companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("subscriptionTab");
		LOGGER.info("Navigated to Plan Tab");
		companiesDetailsPage.waitForElementsOfCompaniesDetailsPage("subscriptionTitle");
		softAssert.assertFalse(companiesDetailsPage.verifyElementsOfCompaniesDetailsPage("deleteIcon"), "Delete Icon is available");
		softAssert.assertFalse(companiesDetailsPage.verifyElementsOfCompaniesDetailsPage("editIcon"), "Edit Icon is available");
		softAssert.assertFalse(companiesDetailsPage.verifyElementsOfCompaniesDetailsPage("convertToSubModButton"),"Convert To Subscription Model button is available");
	}

	/**
	 * This test verifies with convert to subscription navigates to Add Subscription with revoked license key
	 */

	@Test(priority = 99, groups = {"REGRESSIONCOMPANIES2"}, description = "Test case : 858050",enabled=false)
	public final void verifyButtonActionWithRevokeKey() throws Exception {
		//Login with Partner
		login("PARTNER_LOGIN_ID", "PARTNER_LOGIN_PASSWORD");
		LOGGER.info("Logged in with partner user");

		SoftAssert softAssert = new SoftAssert();
		CompaniesDetailsPage companiesDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver()).getInstance();
		gotoCompaniesTab();
		impersonateCompanyByCompanyName(CompaniesVariables.COMPANYNAME_WITH_REVOKEKEY);
		LOGGER.info("Redirected to company details page");

		companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("subscriptionTab");
		LOGGER.info("Click subscription tab");
		Thread.sleep(2000);
		softAssert.assertTrue(companiesDetailsPage.verifyElementsOfCompaniesDetailsPage("convertToSubModButton"),"Convert To Subscription Model button is not available");
		companiesDetailsPage.clickByJavaScriptOnCompaniesDetailsPage("convertToSubModButton");
		softAssert.assertTrue(companiesDetailsPage.verifyElementsOfCompaniesDetailsPage("addButton"), "Add Button is not available");
		softAssert.assertTrue(companiesDetailsPage.verifyElementsOfCompaniesDetailsPage("cancelButton"), "Cancel Button is not available");
		softAssert.assertTrue(companiesDetailsPage.verifyElementsOfCompaniesDetailsPage("txtAddSubscriptionModel"), "Add Subscription text  is not present on model");
	}

	/**
	 * This testcase Verify user should get onboarding free trial plan when they select start date as future date.
	 *
	 * @throws Exception
	 */
	@Test(priority = 100, groups = {"REGRESSIONCOMPANIES1", "SMOKE", "REGRESSION_CI", "SMOKE_CI"}, description = "TESTCASE-849422")
	public final void verifyFreeTrialPlanWithFutureDate() throws Exception {
		login("RESELLER_STAGING_COMPANIES_EMAIL", "RESELLER_STAGING_COMPANIES_PASSWORD");
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		CompaniesDetailsPage companiesDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver()).getInstance();

		SoftAssert softAssert = new SoftAssert();
		String compName = generateRandomString(10);
		String compEmail = generateRandomString(5) + "@yopmail.com";

		try {
			gotoCompaniesTab();
			LOGGER.info("Redirected to company list page");
			waitForPageLoaded();
			companiesPage.clickOnElementsOfCompaniesPage("addButton");
			companiesPage.clickOnElementsOfCompaniesPage("addCompanyDialogPopupNextButton");
			LOGGER.info("Clicked on next button");
			companiesPage.enterTextForCompaniesPage("companyNameOnPopup", compName);
			companiesPage.enterTextForCompaniesPage("cityOnPopup", CompaniesVariables.CITY);

			//Select country
			companiesPage.clickOnElementsOfCompaniesPage("countryClearOnPopup");
			companiesPage.waitForElementsOfCompaniesPage("countryArrowOnPopup");
			sleeper(2000);
			companiesPage.clickOnElementsOfCompaniesPage("countryArrowOnPopup");
			sleeper(2000);

			companiesPage.enterTextForCompaniesPage("countrySearchOnPopup", CompaniesVariables.COUNTRY);
			companiesPage.clickOnElementsOfCompaniesPage("countrySelectOnPopup");

			//Select language
			companiesPage.clickOnElementsOfCompaniesPage("languageArrowOnPopup");
			companiesPage.enterTextForCompaniesPage("languageSearchOnPopup", CommonVariables.LANGUAGE);
			companiesPage.clickOnElementsOfCompaniesPage("languageSelectOnPopup");

			companiesPage.enterTextForCompaniesPage("addressOnPopup", CompaniesVariables.ADDRESS);
			companiesPage.waitForElementsOfCompaniesPage("zipCodeOnCompPopup");
			companiesPage.enterTextForCompaniesPage("zipCodeOnCompPopup", CompaniesVariables.PINCODE);
			companiesPage.waitForElementsOfCompaniesPage("stateOnCompPopup");
			companiesPage.enterTextForCompaniesPage("stateOnCompPopup", CompaniesVariables.STATE);

			companiesPage.clickOnElementsOfCompaniesPage("addCompanyDialogPopupNextButton");
			LOGGER.info("Clicked on next button after first step");

			//After clicking on Next button API takes time
			Thread.sleep(8000);

			//Wait for loader
			companiesPage.waitForPresenceOfElementsOfCompaniesPage("txtTrialDuration");

			//Update billing dropdown value to Subscription
			companiesPage.clickOnElementsOfCompaniesPage("btnArrowBillingModel");
			companiesPage.clickOnElementsOfCompaniesPage("txtSubscriptionInDroDown");
			LOGGER.info("Updated billing dropdown to subscription");

			//Click On Next button
			companiesPage.clickOnElementsOfCompaniesPage("addCompanyDialogPopupNextButton");

			//Verify Error message for mandatory start date field.
			softAssert.assertTrue(companiesPage.getTextOfCompanyPage("txtStartDateErrMsg").equals(getTextLanguage(LanguageCode, "daas_ui", "form.validate.required")));

			//Select start date ,end date and seats
			companiesPage.scrollOnCompaniesPage("openCalenderStartDate");
			companiesPage.clickOnElementsOfCompaniesPage("openCalenderStartDate");
			companiesPage.selectDateFromCalenderOnCompanyPage(addDaysToCurrentDate(2), "monthKeyCurrent",
					"endDateDialogRightArrow", "daysOnCurrentMonthKey");
			companiesPage.waitForElementsOfCompaniesPage("openCalenderEndDate");
			companiesPage.clickOnElementsOfCompaniesPage("openCalenderEndDate");
			sleeper(5000);
			companiesPage.waitForElementsOfCompaniesPage("monthKeyCurrent");
			companiesPage.selectDateFromCalenderOnCompanyPage(addDaysToCurrentDate(4), "monthKeyCurrent",
					"endDateDialogRightArrow", "daysOnCurrentMonthKey");
			sleeper(2000);

			companiesPage.enterTextForCompaniesPage("txtSeat", CompaniesVariables.SEATCOUNT_THREE);
			LOGGER.info("Start date ,end date and seats are selected");

			//Click On Next button
			companiesPage.clickOnElementsOfCompaniesPage("addCompanyDialogPopupNextButton");

			companiesPage.enterTextForCompaniesPage("firstNameOnPopup", CompaniesVariables.FIRST_NAME);
			companiesPage.enterTextForCompaniesPage("lastNameOnPopup", CompaniesVariables.COMPANIES_TITLE);
			companiesPage.enterTextForCompaniesPage("emailOnPopup", compEmail);
			companiesPage.enterTextForCompaniesPage("phoneNumberOnPopup", CompaniesVariables.NUMBER);
			sleeper(2000);

			//Clicked on create button
			companiesPage.clickByJavaScriptOnCompaniesPage("addCompanyDailogPopupCreateButton");
			LOGGER.info("Clicked on Create button");
			companiesPage.waitUntilElementIsInvisibleOfCompanyPageDynamic("btnCompanyCreate",30);
			Thread.sleep(40000);

			//Verify "trial" onboarding plan  should  appear with Plan name
//			companiesDetailsPage.waitForElementsOfCompaniesDetailsPage("txtOnBoardingPlanName");
//			softAssert.assertTrue(companiesDetailsPage.verifyElementsOfCompaniesDetailsPage("txtOnBoardingPlanName"),"Trial onboarding plan is not appearing");
//			LOGGER.info("verified trial text with plan name");

			//Verify free trial  "seat"  on company detail page
//			softAssert.assertTrue(companiesDetailsPage.verifyElementsOfCompaniesDetailsPage("txtFreeTrialSeat"),"Free trial seat is not appearing");
//			LOGGER.info("verified seat count on company detail page");

			//Click On edit icon
			companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("btnEditIcon");
			companiesDetailsPage.waitForElementsOfCompaniesDetailsPage("btnCrossStartDate");

			//Select start date as current date
			companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("btnCrossStartDate");
			companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("openCalenderStartDateEditModel");
			Thread.sleep(2000);
			companiesPage.clickByJavaScriptOnCompaniesPage("selectToday");
			companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("btnSaveOnEditPopUp");
			companiesDetailsPage.waitForElementsOfCompaniesDetailsPage("txtToastNotification");

			//Verify Toast Notification
			softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("txtToastNotification").equals(getTextLanguage(LanguageCode, "daas_ui", "global.messages.field_update_successful").replace("{field}",getTextLanguage(LanguageCode, "daas_ui", "product.list.daasPlan"))));

			//Verify "trial" onboarding plan  should  not appear with Plan name
			softAssert.assertFalse(companiesDetailsPage.verifyElementsOfCompaniesDetailsPage("txtOnBoardingPlanName"),"trial Onboarding plan is appearing with Plan name ");
			LOGGER.info("verified trial text with plan name");

			//Verify free trial  "seat"  on company detail page should not appear
			softAssert.assertFalse(companiesDetailsPage.verifyElementsOfCompaniesDetailsPage("txtFreeTrialSeat"),"Free trial seat is appearing");
			LOGGER.info("verified seat count on company detail page");

			softAssert.assertAll();

		} finally {

			//Logout from account
			logout();
			//login from root
			login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
			LOGGER.info("Redirected to company list page");
			waitForPageLoaded();
			impersonateCompanyByCompanyName(compName);
			LOGGER.info("Redirected to company details page");
			tenantID = getUrlOfCurrentPage().substring(getUrlOfCurrentPage().length() - 36);

			softAssert.assertTrue(companiesPage.removeCompanyUsingAPI(tenantID), "Company removal failed");
			LOGGER.info("Company has been deleted");
		}
	}
 
	/*
	 * This test case validates editing of Company Name on Company details page
	 *
	 * @throws Exception
	 */
	@Test(priority = 101, groups = { "REGRESSIONCOMPANIES2", "SMOKE", "REGRESSION_CI", "SMOKE_CI"}, description = "Test Case 239299")
	public final void verifyEditCompanyNameAndTimeZoneTileOnCompanyDetailsPage() throws Exception {
		login("NEW_MSP_ADMIN_US_EMAIl", "NEW_MSP_ADMIN_US_PASSWORD");
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		CompaniesDetailsPage companiesDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver())
				.getInstance();
		SoftAssert softAssert = new SoftAssert();
		String updatedCompanyName = "EditCompanyUpdated"+generateRandomString(8);
		String updatedTimeZone = "(GMT-11:00) American Samoa";

		try {
			waitForPageLoaded();
			gotoCompaniesTab();
			LOGGER.info("Redirected to companies tab");
			impersonateCompanyByCompanyName(
					getEnvironmentSpecificData(System.getProperty("environment"), "EDIT_COMPANY_NAME"));
			LOGGER.info("Redirected to companies details page");

			companiesDetailsPage.waitForElementsOfCompaniesDetailsPage("editCompanyName");
			companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("editCompanyName");
			softAssert.assertTrue(
					companiesDetailsPage.matchTextOfCompaniesDetailsPage("companyNameLabelOnPopUp",
							getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.company_name")),
					"Company Name text is matching on details tile popup");
			companiesDetailsPage.enterTextForCompaniesDetailsPage("companyNamePlaceHoldVal", updatedCompanyName);
			companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("btnSaveOnEditCompanyName");
			companiesDetailsPage.waitForElementsOfCompaniesDetailsPage("toastNotification");
			softAssert.assertTrue(
					companiesDetailsPage.getTextOfCompaniesDetailsPage("companyName").equals(updatedCompanyName),
					"Updated Company name is not matching");

			LOGGER.info("Company Name is getting updated correctly on details page");

			companiesDetailsPage.waitForElementsOfCompaniesDetailsPage("closeToastNotification");
			sleeper(7000);
			companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("editbtnCompanyTimeZone");
			softAssert
			.assertTrue(
							companiesDetailsPage.matchTextOfCompaniesDetailsPage("timezoneLabelOnPopup",
									getTextLanguage(LanguageCode, "daas_ui",
											"companies.details.section_preferred_time_zone")),
							"Company Time Zone text is matching on details tile popup");
			companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("timezoneDropdown");
			companiesDetailsPage.selectFirstOptionFromDropdown("timezonelistdropdwon");
			companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("timezoneSave");
			sleeper(2000);
			companiesDetailsPage.waitForElementsOfCompaniesDetailsPage("companyTimeZoneData");
			softAssert.assertTrue(
					companiesDetailsPage.getTextOfCompaniesDetailsPage("companyTimeZoneData").equals(updatedTimeZone),
					"Updated Time Zone is not matching");

			LOGGER.info("Time Zone is getting updated correctly on details page");

			gotoCompaniesTab();
			waitForPageLoaded();
			LOGGER.info("Redirected to companies tab");
			companiesPage.enterTextForCompaniesPage("companyNameSearch", updatedCompanyName);
			waitForPageLoaded();
			softAssert.assertTrue(companiesPage.getTextOfCompaniesPage("firstcompanyName").equals(updatedCompanyName),
					"Updated Company name is not matching on Company List page");			
			companiesPage.scrollOnCompaniesPage("preferredTimeZoneFirstRow");
			softAssert.assertTrue(
					companiesPage.getTextOfCompaniesPage("preferredTimeZoneFirstRow").equals(updatedTimeZone),
					"Updated Time Zone is not matching on Company List page");

			softAssert.assertAll();
			LOGGER.info("Updated Company Name and Time Zone correctly displaying on Company List Page");
		}

		finally {
			refreshPage();
			impersonateCompanyByCompanyName(updatedCompanyName);
			LOGGER.info("Redirected to companies details page");
			companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("editCompanyName");
			LOGGER.info("Clicked on edit company name button");
			LOGGER.info("Enter the previous company name");
			companiesDetailsPage.enterTextForCompaniesDetailsPage("companyNamePlaceHoldVal",
					getEnvironmentSpecificData(environment, "EDIT_COMPANY_NAME"));
			companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("btnSaveOnEditCompanyName");
			LOGGER.info("Clicked on save button on the popup");
			LOGGER.info("Company Name is changed back to its original name");
		}
	}

	/**
	 * This testcase verify plan details of company created with subscription free slider OFF
	 * Disabling this test case since, validation is already present in verifyCreateCompWithFreeSliderOnWithSubs
	 * @throws Exception
	 */
	@Test(priority = 102, groups = {"REGRESSIONCOMPANIES1", "SMOKE", "REGRESSION_CI", "SMOKE_CI"}, description = "TESTCASE-836591",enabled=false)
	public final void verifyCompPlanDetailsHavingFreeSliderOFF() throws Exception {
		login("COMPANY_FREESLIDER_OFF", "COMPANY_FREESLIDER_OFF_PASSWORD");
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();

		SoftAssert softAssert = new SoftAssert();

		gotoCompanySettingsTab();
		LOGGER.info("Redirected to Setting page");
		waitForPageLoaded();
		companiesPage.clickOnElementsOfCompaniesPage("PlanTabLink");

		//Verify "trial" text should not appear with Plan name
		settingsPage.waitUntilElementIsVisibleOfSettingsPage("txtPlanName");
		softAssert.assertFalse(settingsPage.getTextOfSettingsPage("txtPlanName").contains(getTextLanguage(LanguageCode, "daas_ui", "subscription.list.is_trial").replace("Is ", "")));
		LOGGER.info("verified trial text with plan name should not appear");

		//Verify edit icon should not appear
		softAssert.assertFalse(settingsPage.verifyElementsOfSettingsPage("btnEditIcon"));
		LOGGER.info("verified trial text with plan name should  appear");

		softAssert.assertAll();

	}

	/**
	 * This testcase verify plan details of company created with subscription free slider ON
	 *Disabling this test case since, validation is already present in verifyCreateCompWithFreeSliderOFFWithSubs
	 * @throws Exception
	 */
	@Test(priority = 102, groups = {"REGRESSIONCOMPANIES1", "SMOKE", "REGRESSION_CI", "SMOKE_CI"}, description = "TESTCASE-836590",enabled=false)
	public final void verifyCompPlanDetailsHavingFreeSliderON() throws Exception {
		login("COMPANY_FREESLIDER_ON", "COMPANY_FREESLIDER_ON_PASSWORD");
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();

		SoftAssert softAssert = new SoftAssert();

		gotoCompanySettingsTab();
		LOGGER.info("Redirected to Setting page");
		waitForPageLoaded();
		companiesPage.clickOnElementsOfCompaniesPage("PlanTabLink");

		//Verify "trial" text should  appear with Plan name
		softAssert.assertTrue(settingsPage.getTextOfSettingsPage("txtFreeTrialPlan").contains(getTextLanguage(LanguageCode, "daas_ui", "subscription.list.is_trial").replace("Is ", "")));
		LOGGER.info("verified trial text with plan name should  appear");

		//Verify edit icon should not appear
		softAssert.assertFalse(settingsPage.verifyElementsOfSettingsPage("btnEditIcon"));
		LOGGER.info("verified trial text with plan name should  appear");
		softAssert.assertAll();
	}

	/**
	 * This test case verifies Source of Customer field on company list and details page overview 
	 * for root, msp and partner user
	 * 
	 * @throws Exception
	 */
	@Test(priority = 102, description = "TEST CASE - 868538, 868560", groups = {"REGRESSIONCOMPANIES2"}, dataProvider = "loginDataForCompanySourceOfCustomerField")
	public final void verifySourceOfCustomerOnCompany(String username, String password) throws Exception {
	 if (environment.equals("US-STAGING")) {
		login(username, password);
		SoftAssert softAssert = new SoftAssert();
		gotoCompaniesTab();
		LOGGER.info("Redirected to Companies tab");
		waitForPageLoaded();
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		CompaniesDetailsPage companiesDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver()).getInstance();

		resetTableConfiguration();
		companiesPage.waitUntilElementIsInvisibleOfCompanyPage("tableOverlay");
		companiesPage.scrollOnCompaniesPage("sourceOfCustomer");
		companiesPage.clickOnElementsOfCompaniesPage("sourceOfCustomerpicklist");
		
		//Verify Source Of Customer drop down list
		softAssert.assertTrue(companiesPage.verifySourceOfCustomerdropdown("sourceOfCustomerpicklistvalues"), "Source Of Customer drop down list values are not correct");
		pressKey(Keys.ESCAPE);
		sleeper(2000);
		companiesPage.enterTextForCompaniesPage("companyNameSearch",sourceOfCustomerCmpName);
		companiesPage.waitForElementsOfCompaniesPage("companyEmptySearchResult");
		sleeper(2000);
		
		Assert.assertFalse(companiesPage.verifyElementsOfCompaniesPage("companyEmptySearchResult"), "Company not found");
		Assert.assertTrue(companiesPage.getTextOfCompaniesPage("companySearchResult").equalsIgnoreCase(sourceOfCustomerCmpName), "company is not found on company search list.");
		companiesPage.scrollOnCompaniesPage("sourceOfCustomer");
		String sourceOfCustomertext = null ;
		if(username =="ROOT_ADMIN_NEW_USER_US"){
			sourceOfCustomertext = companiesPage.getTextOfCompaniesPage("sourceofCustomerOnListPageRoot");
		}else if(username =="MSP_NEW_USER_US") {
			sourceOfCustomertext = companiesPage.getTextOfCompaniesPage("sourceofCustomerOnListPageMSP");
		}else if(username =="PARNTER_NEW_USER_US") {
			sourceOfCustomertext = companiesPage.getTextOfCompaniesPage("sourceofCustomerOnListPagePartner");
		}
		companiesPage.clickByJavaScriptOnCompaniesPage("companySearchResult");
		LOGGER.info("Redirected to company details page");
		sleeper(2000);
		
		//Will uncomment once maestro keys are translated
		//Verify details on Overview tab
		//softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("techPulseCustomerID").equals(getTextLanguage(LanguageCode, "daas_ui", "techpulse.company.id")));
		softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("sorceOfCutrOverview").toLowerCase().contains((getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.source_of_customer")).toLowerCase()), "Source of Customer text didn't match");
		softAssert.assertTrue(companiesDetailsPage.verifyElementsOfCompaniesDetailsPage("copyIcon"), "Copy Icon is not available");
		companiesDetailsPage.scrollOnCompaniesDetailsPage("sourceOfCustomerLabel");
		softAssert.assertEquals(companiesDetailsPage.getTextOfCompaniesDetailsPage("sourceOfCustomervalues"), sourceOfCustomertext , "Source of Customer on Customer details page did not match with alias on details tile");
		
		//Verify plan details
		companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("subscriptionTab");
		LOGGER.info("Click subcription tab");
		Thread.sleep(2000);
		
		//Validate Subscription tile
		softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("planName").equals(CompaniesVariables.PLAN_ON_DETAILS), "plan did not match");
		softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("statusOfPlanActive").contains(getTextLanguage(LanguageCode, "daas_ui", "companies.list.sections.active")), "Assigned Subscription is not in active state");
		softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("startDatetext").toLowerCase().contains((getTextLanguage(LanguageCode, "daas_ui", "campaign.details.startDate")).toLowerCase()) , "Start Date did't match");
		softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("totalSeatscount").contains((getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.subscription.total_seats")) + " : " + CompaniesVariables.SEATCOUNT_260), "Seats didn't match");
		softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("sourceOfCustomertext").contains((getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.source_of_customer")) + " : " + "DSP") , "Source of Customer didn't match");
		LOGGER.info("Verified Subscription plan details");
		
		softAssert.assertAll();		
		LOGGER.info("verified Source of Customer on company detail page");
	 } else {
			LOGGER.info("environment we are using is other than us-staging.This test case is for US-Staging only");
	 }
	}

	/**
	 * This test case verifies Source of Customer field on settings page for company user
	 * 
	 * @throws Exception
	 */
	@Test(priority = 103, description = "TEST CASE - 868538, 868560", groups = {"REGRESSIONCOMPANIES2"})
	public final void verifySourceOfCustomerWitCompanylogin() throws Exception {
	  if (environment.equals("US-STAGING")) {
		SoftAssert softAssert = new SoftAssert();
		login("CUSTOMER_NEW_USER", "CUSTOMER_NEW_USER_PASSWORD");
		CompaniesDetailsPage companiesDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver()).getInstance();
		gotoSettingsTabWorkflow();
		waitForPageLoaded();
		LOGGER.info("Redirected to settings tab");
		sleeper(5000);
		
		//Will uncomment once maestro keys are translated
		//Verify details on Overview tab
		//softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("techPulseCustomerID").equals(getTextLanguage(LanguageCode, "daas_ui", "techpulse.company.id")));
		softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("sorceOfCutrOverview").toLowerCase().contains((getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.source_of_customer")).toLowerCase()), "Source of Customer text didn't match");
		softAssert.assertTrue(companiesDetailsPage.verifyElementsOfCompaniesDetailsPage("copyIcon"), "Copy Icon is not available");
		companiesDetailsPage.scrollOnCompaniesDetailsPage("sourceOfCustomerLabel");
		softAssert.assertEquals(companiesDetailsPage.getTextOfCompaniesDetailsPage("sourceOfCustomervalues"), "DSP" , "Source of Customer on Customer details page did not match with alias on details tile");
				
		//Verify plan details
		companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("subscriptionTab");
		LOGGER.info("Click subcription tab");
		Thread.sleep(2000);
				
		//Validate Subscription tile
		softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("planName").equals(CompaniesVariables.PLAN_ON_DETAILS), "plan did not match");
		softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("statusOfPlanActive").contains(getTextLanguage(LanguageCode, "daas_ui", "companies.list.sections.active")), "Assigned Subscription is not in active state");
		softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("startDatetext").toLowerCase().contains((getTextLanguage(LanguageCode, "daas_ui", "campaign.details.startDate")).toLowerCase()) , "Start Date did't match");
		softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("totalSeatscount").contains((getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.subscription.total_seats")) + " : " + CompaniesVariables.SEATCOUNT_260), "Seats didn't match");
		softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("sourceOfCustomertext").contains((getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.source_of_customer")) + " : " + "DSP") , "Source of Customer didn't match");
		LOGGER.info("Verified Subscription plan details");
				
		softAssert.assertAll();		
		LOGGER.info("verified Source of Customer on company detail page");
	  } else {
			LOGGER.info("environment we are using is other than us-staging.This test case is for US-Staging only");
	 }
	}
	
	/**
	 * This test case verifies Subscription data like Source of Customer, End date for expired plan on plans tab
	 * 
	 * @throws Exception
	 */
	@Test(priority = 104, description = "TEST CASE - 876498", groups = {"REGRESSIONCOMPANIES2"})
	public final void verifySubscripptionsForDSPSourceOfCustomer() throws Exception {
	  if (environment.equals("US-STAGING")) {
		login("ROOT_ADMIN_NEW_USER_US", "ROOT_ADMIN_NEW_PASSWORD_US");
		SoftAssert softAssert = new SoftAssert();
		gotoCompaniesTab();
		LOGGER.info("Redirected to Companies tab");
		waitForPageLoaded();
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		CompaniesDetailsPage companiesDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver()).getInstance();

		resetTableConfiguration();
		companiesPage.enterTextForCompaniesPage("companyNameSearch",sourceOfCstmrDSSP);
		companiesPage.waitForElementsOfCompaniesPage("companyEmptySearchResult");
		sleeper(2000);
		
		Assert.assertFalse(companiesPage.verifyElementsOfCompaniesPage("companyEmptySearchResult"), "Company not found");
		Assert.assertTrue(companiesPage.getTextOfCompaniesPage("companySearchResult").equalsIgnoreCase(sourceOfCstmrDSSP), "company is not found on company search list.");
		companiesPage.clickByJavaScriptOnCompaniesPage("companySearchResult");
		LOGGER.info("Redirected to company details page");
		
		companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("subscriptionTab");
		LOGGER.info("Click subcription tab");
		
		//Validate Subscriptions header
		softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("subscriptionsHeader").equalsIgnoreCase("Subscription"), "company is not found on company search list.");
		
		//Validate Expired Subscription - End date should be shown for expired plan
		softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("planName").equals(CompaniesVariables.PLAN_ON_DETAILS), "plan did not match");
		softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("statusOfPlanExpired").contains(getTextLanguage(LanguageCode, "daas_ui", "subscription.list.plan.expired")), "Assigned Subscription is active state");
		softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("startDatetext").toLowerCase().contains((getTextLanguage(LanguageCode, "daas_ui", "campaign.details.startDate")).toLowerCase()) , "Start Date did't match");
		softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("endDateExpired").toLowerCase().contains((getTextLanguage(LanguageCode, "daas_ui", "campaign.details.endDate")).toLowerCase()) , "End Date did't match");
		softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("totalSeatscountExpired").contains((getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.subscription.total_seats")) + " : " + CompaniesVariables.SEATCOUNT_260), "Seats didn't match");
		softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("sourceOfCustomertextExpired").contains((getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.source_of_customer")) + " : " + "DSP") , "Source of Customer didn't match");
		LOGGER.info("Verified Subscription expired plan details");
		
		softAssert.assertAll();
		LOGGER.info("verified Subscriptions tile on company details page");	
	  } else {
			LOGGER.info("environment we are using is other than us-staging.This test case is for US-Staging only");
	 }
	}
	
	@Test(priority = 105, groups = { "REGRESSIONCOMPANIES2", "SMOKE", "REGRESSION_CI", "SMOKE_CI", "PRODUCTION_SANITY",
			"STABILIZATION_STAGING" })
	public final void verifyAssignedPartnerTileDecline() throws Exception {
		login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
		SoftAssert softAssert = new SoftAssert();
		CompaniesDetailsPage companyDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver()).getInstance();
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		ArrayList<String> partnerDetails = new ArrayList<String>(
				Arrays.asList(getEnvironmentSpecificData(System.getProperty("environment"), "PARTNER_ID").toLowerCase(),
						CompaniesVariables.PARTNER_STREET_ADDRESS, CompaniesVariables.PARTNER_CITY,
						CompaniesVariables.PARTNER_STATE, CompaniesVariables.PARTNER_ZIP_CODE,
						CompaniesVariables.PARTNER_COUNTRY));

		waitForPageLoaded();

		impersonateCompanyByCompanyName(inviteComapny);
		String tenantID = getUrlOfCurrentPage().substring(getUrlOfCurrentPage().length() - 36);
		companiesPage.waitForPresenceOfElementsOfCompaniesPage("assignedPartner");
		LOGGER.info("Clicked on assigned partner tab");
		companiesPage.clickOnElementsOfCompaniesPage("assignedPartner");
		companiesPage.waitForPresenceOfElementsOfCompaniesPage("invitationLabel");
		// Remove Pending Invitation
		softAssert.assertTrue(removeAllReceievedPendingInvitations(environment, tenantID, getEnvironment(environment)),
				"Pending Partner Invitation removal failed");
		softAssert.assertTrue(removeAllPendingInvitations(environment, tenantID, getEnvironment(environment)),
				"Pending root admin Invitation removal failed");
		softAssert.assertTrue(unassignPartner(environment), "Partner unassign failed");
		LOGGER.info("Removed all pending invitations from root admin view");

		if (companyDetailsPage.getTextOfCompaniesDetailsPage("unassignedPartnerField")
				.equals(getTextLanguage(LanguageCode, "daas_ui", "companies.details.label.not_assigned"))) {
			companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("editAssignedpartnerButton");
			LOGGER.info("Clicked on edit assigned partner button");
			companyDetailsPage.waitForElementsOfCompaniesDetailsPage("partnerPopupTitle");
			sleeper(2000); // time required to load MSP details on dropdown
			companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("assignedPartnerDropdown");
			LOGGER.info("Clicked on edit assigned partner dropdown");
			companyDetailsPage.waitForElementsOfCompaniesDetailsPage("assignedPartnerSearchBox");
			companyDetailsPage.enterTextForCompaniesDetailsPage("assignedPartnerSearchBox",
					getEnvironmentSpecificData(environment, "ASSIGNED_PARTNER_NAME"));
			waitForPageLoaded();
			companyDetailsPage.selectFirstOptionFromDropdown("assignedPartnerList");
			LOGGER.info("Selected partner on dropdown");
			softAssert.assertTrue(
					companyDetailsPage.getTextOfListOfCompanyDetailsPage("infoValuesPartner").equals(partnerDetails),
					"Details of current partner on partner assignment popup are incorrect");
			companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("cancelAssignedPartner");
			LOGGER.info("Clicked on cancel assigned partner button");
			softAssert.assertTrue(
					companyDetailsPage.getTextOfCompaniesDetailsPage("unassignedPartnerField").equalsIgnoreCase(
							getTextLanguage(LanguageCode, "daas_ui", "companies.details.label.not_assigned")),
					"Cancel functionality on MSP assignment popup is not working");
			softAssert.assertTrue(
					companyDetailsPage.matchTextOfCompaniesDetailsPage("unassignedPartnerField",
							getTextLanguage(LanguageCode, "daas_ui", "companies.details.label.not_assigned")),
					"Cancel functionality on MSP assignment popup is not working");
			companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("editAssignedpartnerButton");
			LOGGER.info("Clicked on edit assigned partner button");
			sleeper(2000); // time required to load MSP details on dropdown
			companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("assignedPartnerDropdown");
			LOGGER.info("Clicked on edit assigned partner dropdown");
			companyDetailsPage.waitForElementsOfCompaniesDetailsPage("assignedPartnerSearchBox");
			companyDetailsPage.enterTextForCompaniesDetailsPage("assignedPartnerSearchBox",
					getEnvironmentSpecificData(environment, "ASSIGNED_PARTNER_NAME"));
			waitForPageLoaded();
			companyDetailsPage.selectFirstOptionFromDropdown("assignedPartnerList");
			LOGGER.info("Selected partner on dropdown");
			companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("saveAssignedPartner");
			LOGGER.info("Clicked on save assigned partner button");
			companyDetailsPage.waitForElementsOfCompaniesDetailsPage("toastNotification");
			softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("toastNotification"),
					"Toast notification is not generated after assigned MSP is changed");
			logout();
			LOGGER.info("Logged out successfully");

			login("IT_ADMIN_PARTNERS_EMAIL", "IT_ADMIN_PARTNERS_PASSWORD");
			LOGGER.info("Logging in to it admin successfull");
			companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("notificationBellIcon");
			companyDetailsPage.waitForElementsOfCompaniesDetailsPage("tableOverlay"); // Need to put wait here since
																						// notification is taking time
																						// to appear
			LOGGER.info("Clicked on notification bell icon");
			waitForPageLoaded();// Need to put wait here as notification is taking time to appear
			sleeper(2000); // time required to load notification
			softAssert.assertTrue(
					companyDetailsPage.getTextOfCompaniesDetailsPage("invitationNotificationDescription")
							.equals(getEnvironmentSpecificData(environment, "ASSIGNED_PARTNER_NAME") + " ("
									+ CompaniesVariables.PARTNER_ID_STRING + " "
									+ getEnvironmentSpecificData(environment, "PARTNER_ID") + ") "
									+ CompaniesVariables.PARTNER_INVITATION_NOTIFICATION),
					"Description on partner invite notification is incorrect");
				companiesPage.mouseHoverOnCompanyPage("firstNotification");
				companiesPage.clickOnElementsOfCompaniesPage("hamburgerMenuOnNotification");
			companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("invitationNotificationDecline");
			LOGGER.info("Clicked on link for partner declination");
			softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("declineDescription",
					getTextLanguage(LanguageCode, "daas_ui", "partner_invite.decline.message").replace("{partner_name}",
							getEnvironmentSpecificData(environment, "ASSIGNED_PARTNER_NAME"))),
					"Description on decline partner invitation popup is incorrect");
			companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("declineSaveButton");
			companyDetailsPage.waitForElementsOfCompaniesDetailsPage("toastNotification");
			LOGGER.info("Clicked on decline assigned partner button");
			logout();
			LOGGER.info("Logged out successfully");

			softAssert.assertAll();
			LOGGER.info("All test cases for decline functionality on assigned partner tile have passed successfully");
		}
	}

	/**
	 * This testcase verify company creation with 'Wolf Protect and Trace' plan having license model and active care plan
	 * Disabling this test case since fake device enrollment has changed.
	 * @throws Exception
	 */

	@Test(priority = 106, groups = {"REGRESSIONCOMPANIES1", "SMOKE", "REGRESSION_CI", "SMOKE_CI"}, description = "TESTCASE-882097,882094,881967,882492",enabled=false)
	public final void verifyCompanyCreationWithLicModelWithMultiPlan() throws Exception {
		int notificationCountUnread = 0;

		login("SUBSCRIPTION_AUTH_PARTNER_MULTIPLAN", "SUBSCRIPTION_AUTH_PARTNER_MULTIPLAN_PASSWORD");
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
		CompaniesDetailsPage companiesDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver()).getInstance();
		UserPage userPage = new UserPage(PreDefinedActions.getDriver()).getInstance();

		SoftAssert softAssert = new SoftAssert();

		try {
			gotoCompaniesTab();
			LOGGER.info("Redirected to company list page");
			waitForPageLoaded();

			Assert.assertTrue(companiesPage.createCompanyWithBillingModelUsingAPI(CompaniesVariables.Company_MultiPlanName, getEnvironmentSpecificData(System.getProperty("environment"), "ADD_COMPANY_THROUGH_ROOT_COUNTRY_CODE"), LanguageCode, getEnvironmentSpecificData(System.getProperty("environment"), "ADD_COMPANY_THROUGH_ROOT_MULTIPLANMSP_NAME_ID"), getEnvironmentSpecificData(System.getProperty("environment"), "ADD_COMPANY_THROUGH_ROOT_MULTIPLANPARTNER_NAME_ID"),getEnvironmentSpecificData(System.getProperty("environment"), "MultiPlanCOMPANY"),CompaniesVariables.COMPANY_WOLFANDTRACETRIAL,CompaniesVariables.LicenseBillingModel ), "Company Creation failed");
			LOGGER.info("Company created successfully");

			logout();

			//login with created company
			login("MultiPlanCOMPANY", "MultiPlanCOMPANY_PASSWORD");
             if(companiesDetailsPage.verifyElementsOfCompaniesDetailsPage("btnCancelOnPopUp")){
				 companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("btnCancelOnPopUp");
			 }
			gotoCompanySettingsTab();
			LOGGER.info("Navigate to setting page successfully");
			// Navigate to Prefrence Tab
			companiesDetailsPage.waitForElementsOfCompaniesDetailsPage("preferenceTab");
			companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("preferenceTab");
			LOGGER.info("Navigated to preferenceTab ");
			Thread.sleep(3000);
			companiesDetailsPage.waitForElementsOfCompaniesDetailsPage("companyPin");
			String companyPin = companiesDetailsPage.getTextOfCompaniesDetailsPage("companyPin");

			//Enroll device
			EnrollFakeDevice.enrollFakeDeviceWithFixedSerialNumber(CompaniesVariables.Company_MultiPlanName, companyPin, CompaniesVariables.Company_MultiPlanEmail, CompaniesVariables.DEVICE_SERIALNUMBER);
			logout();

			//Login with root user
			login("ROOT_ADMIN_DEFAULT_MULTIPLAN", "ROOT_ADMIN_DEFAULT_MULTIPLAN_PASSWORD");
			LOGGER.info("logged in via root account");

			waitForPageLoaded();
			gotoOrderTab();
			LOGGER.info("Navigated to orders page");
			waitForPageLoaded();

			settingsPage.verifyImportOrdersForMultiPlan(ConstantPath.IMPORT_PATH + getEnvironmentSpecificData(System.getProperty("environment"),"MULTIPLAN_ORDERS"));
			softAssert.assertTrue(settingsPage.matchTextOfSettingsPage("successNotification", getTextLanguage(LanguageCode, "daas_ui", "assets.import.asset.import_in_progress")), "Toast notification did not appear");
			sleeper(250000);//CSV file upload takes time
			Assert.assertTrue(settingsPage.postNotificationCheckImportInV3(getEnvironmentSpecificData(System.getProperty("environment"), "MULTIPLAN_ORDERS")), "Message on notification window is incorrect");
			LOGGER.info("Notification message verification for import has passed");

			gotoRootCompaniesTab();
			//Go to User detail page
			userPage.waitUntilElementIsInvisibleOfUserPage("tableOverlay");
			impersonateCompanyByCompanyName(CompaniesVariables.Company_MultiPlanName);
			waitForPageLoaded();
			sleeper(3000);
			// Navigate to Plan  Tab
			companiesDetailsPage.waitForElementsOfCompaniesDetailsPage("subscriptionTab");
			companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("subscriptionTab");

			//Verify Active Care Plan
			waitForPageLoaded();
			companiesDetailsPage.scrollOnCompaniesDetailsPage("txtActivePlanName");
			softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("txtActivePlanName").contains(getTextLanguage(LanguageCode, "idm", "onboarding.pc.service_name")), "Active care plan text verification failed");

			//Verify device enrolled count
			softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("txtDeviceEnrolledText").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "progress_bar.devices_enrolled_s").replace("{count}", CompaniesVariables.VALUEONE)), "Device enrolled text verification failed");

			//Verify wolf protect and trace Plan
			softAssert.assertTrue(getTextLanguage(LanguageCode, "roles_meta_service", "plans.package_name.displayName.hp_protect_and_trace_trial_package").contains(companiesDetailsPage.getTextOfCompaniesDetailsPage("txtLicPlanName")), "Wolf protect and trace  plan text verification failed");

			//Verify Add New Key button
			softAssert.assertTrue(companiesDetailsPage.verifyElementsOfCompaniesDetailsPage("addNewKeyButton"), "Add new key button is not present");
			LOGGER.info("Verified plans and buttons successfully with root login");

			logout();

			//Login with partner and verify details
			login("SUBSCRIPTION_AUTH_PARTNER_MULTIPLAN", "SUBSCRIPTION_AUTH_PARTNER_MULTIPLAN_PASSWORD");
			LOGGER.info("Logged in with partner successfully");

			gotoCompaniesTab();
			waitForPageLoaded();
			impersonateCompanyByCompanyName(CompaniesVariables.Company_MultiPlanName);
			waitForPageLoaded();
			sleeper(3000);
			// Navigate to Plan  Tab
			companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("subscriptionTab");

			//Verify Active Care Plan
			companiesDetailsPage.scrollOnCompaniesDetailsPage("txtActivePlanName");
			softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("txtActivePlanName").contains(getTextLanguage(LanguageCode, "idm", "onboarding.pc.service_name")), "Active care plan text verification failed");

			//Verify device enrolled count
			softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("txtDeviceEnrolledText").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "progress_bar.devices_enrolled_s").replace("{count}", CompaniesVariables.VALUEONE)), "Device enrolled text verification failed");

			//Verify wolf protect and trace Plan
			companiesDetailsPage.waitForElementsOfCompaniesDetailsPage("txtLicPlnNmWithPtnr");
			softAssert.assertTrue(getTextLanguage(LanguageCode, "roles_meta_service", "plans.package_name.displayName.hp_protect_and_trace_trial_package").contains(companiesDetailsPage.getTextOfCompaniesDetailsPage("txtLicPlnNmWithPtnr")), "Wolf protect and trace plan text verification failed");

			//Verify Add New Key button
			softAssert.assertTrue(companiesDetailsPage.verifyElementsOfCompaniesDetailsPage("addNewKeyButton"), "Add new key button is not present");

			LOGGER.info("Verified plans and buttons successfully with partner login");

		} finally {

			//Logout from account
			logout();
			//login from root
			login("ROOT_ADMIN_DEFAULT_MULTIPLAN", "ROOT_ADMIN_DEFAULT_MULTIPLAN_PASSWORD");
			LOGGER.info("Redirected to company list page");
			waitForPageLoaded();
			impersonateCompanyByCompanyName(CompaniesVariables.Company_MultiPlanName);
			LOGGER.info("Redirected to company details page");
			tenantID = getUrlOfCurrentPage().substring(getUrlOfCurrentPage().length() - 36);
			softAssert.assertTrue(companiesPage.removeCompanyUsingAPI(tenantID), "Company removal failed");
			LOGGER.info("Company has been deleted");
		}

	}

	/**
	 * This testcase verify company creation with PI plan having  subscription  model and add active care plan
	 *
	 * @throws Exception
	 */

	@Test(priority = 107, groups = {"REGRESSIONCOMPANIES1", "SMOKE", "REGRESSION_CI", "SMOKE_CI"}, description = "TESTCASE-882098,882096,882455,881977",enabled=false)//Due to User Story 899938: [ActiveCare] Disable adding of active care device entitlement to existing PI/PEM entitled devices.Company creation is not possible with PEM and active care plan
		public final void verifyCompanyCreationWithSubModelWithMultiPlan() throws Exception {
		int notificationCountUnread = 0;

		login("SUBSCRIPTION_AUTH_PARTNER_MULTIPLAN", "SUBSCRIPTION_AUTH_PARTNER_MULTIPLAN_PASSWORD");
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
		CompaniesDetailsPage companiesDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver()).getInstance();
		UserPage userPage = new UserPage(PreDefinedActions.getDriver()).getInstance();

		SoftAssert softAssert = new SoftAssert();

		try {
			gotoCompaniesTab();
			LOGGER.info("Redirected to company list page");
			waitForPageLoaded();

			//Click on Add company button
			companiesPage.clickOnElementsOfCompaniesPage("addButton");
			companiesPage.clickOnElementsOfCompaniesPage("addCompanyDialogPopupNextButton");
			LOGGER.info("Clicked on next button");
			companiesPage.enterTextForCompaniesPage("companyNameOnPopup", CompaniesVariables.Company_MultiPlanName);
			companiesPage.enterTextForCompaniesPage("cityOnPopup", CompaniesVariables.CITY);

			//Select country
			companiesPage.clickOnElementsOfCompaniesPage("countryClearOnPopup");
			companiesPage.waitForElementsOfCompaniesPage("countryArrowOnPopup");
			sleeper(2000);
			companiesPage.clickOnElementsOfCompaniesPage("countryArrowOnPopup");
			sleeper(2000);

			companiesPage.enterTextForCompaniesPage("countrySearchOnPopup", CompaniesVariables.COUNTRY);
			companiesPage.clickOnElementsOfCompaniesPage("countrySelectOnPopup");

			//Select langauge
			companiesPage.clickOnElementsOfCompaniesPage("languageArrowOnPopup");
			companiesPage.enterTextForCompaniesPage("languageSearchOnPopup", CommonVariables.LANGUAGE);
			companiesPage.clickOnElementsOfCompaniesPage("languageSelectOnPopup");

			companiesPage.clickOnElementsOfCompaniesPage("addCompanyDialogPopupNextButton");
			LOGGER.info("Clicked on next button after first step");

			//After clicking on Next button API takes time
			Thread.sleep(8000);

			//Wait for loader
			companiesPage.waitForPresenceOfElementsOfCompaniesPage("txtTrialDuration");

			//Update billing dropdown value to Subscription
			companiesPage.clickOnElementsOfCompaniesPage("btnArrowBillingModel");
			companiesPage.clickOnElementsOfCompaniesPage("txtSubscriptionInDroDown");
			LOGGER.info("Updated billing dropdown to subscription");

			//Update Plan type to Wolf Protect and Trace
			companiesPage.clickOnElementsOfCompaniesPage("btnArrowPlanType");
			companiesPage.clickOnElementsOfCompaniesPage("txtPEMPlanTypeInDroDown");
			LOGGER.info("Updated Plan Type to PEM");


			//Select start date ,end date and seats
			companiesPage.clickOnElementsOfCompaniesPage("openCalenderStartDate");
			companiesPage.clickOnElementsOfCompaniesPage("selectToday");
			companiesPage.enterTextForCompaniesPage("txtSeat", CompaniesVariables.SEATCOUNT_THREE);
			LOGGER.info("Start date ,end date and seats are selected");

			//Click On Next button
			companiesPage.clickOnElementsOfCompaniesPage("addCompanyDialogPopupNextButton");
			companiesPage.enterTextForCompaniesPage("firstNameOnPopup", CompaniesVariables.FIRST_NAME);
			companiesPage.enterTextForCompaniesPage("lastNameOnPopup", CompaniesVariables.COMPANIES_TITLE);
			companiesPage.enterTextForCompaniesPage("emailOnPopup", getEnvironmentSpecificData(System.getProperty("environment"),"MultiPlanCOMPANY"));

			companiesPage.enterTextForCompaniesPage("phoneNumberOnPopup", CompaniesVariables.NUMBER);
			sleeper(2000);
			companiesPage.clickByJavaScriptOnCompaniesPage("addCompanyDailogPopupCreateButton");
			LOGGER.info("Clicked on Create button");

			//Create company API takes 40 seconds
			sleeper(40000);
			waitForPageLoaded();
			logout();

			//login with created company
			login("MultiPlanCOMPANY", "MultiPlanCOMPANY_PASSWORD");

			if(companiesDetailsPage.verifyElementsOfCompaniesDetailsPage("btnCancelOnPopUp")){
				companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("btnCancelOnPopUp");
			}
			gotoCompanySettingsTab();
			LOGGER.info("Navigate to setting page successfully");

			// Navigate to Prefernec Tab
			companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("preferenceTab");
			LOGGER.info("Navigated to preferenceTab ");
			Thread.sleep(3000);
			companiesDetailsPage.waitForElementsOfCompaniesDetailsPage("companyPin");
			String companyPin = companiesDetailsPage.getTextOfCompaniesDetailsPage("companyPin");

			//Enroll device
			EnrollFakeDevice.enrollFakeDeviceWithFixedSerialNumber(CompaniesVariables.Company_MultiPlanName, companyPin, CompaniesVariables.Company_MultiPlanEmail, CompaniesVariables.DEVICE_SERIALNUMBER);
			logout();

			//Login with root user
			login("ROOT_ADMIN_DEFAULT_MULTIPLAN", "ROOT_ADMIN_DEFAULT_MULTIPLAN_PASSWORD");
			waitForPageLoaded();
			gotoOrderTab();
			LOGGER.info("Navigated to orders page");
			waitForPageLoaded();
			settingsPage.verifyImportOrdersForMultiPlan(ConstantPath.IMPORT_PATH + getEnvironmentSpecificData(System.getProperty("environment"),"MULTIPLAN_ORDERS"));
			softAssert.assertTrue(settingsPage.matchTextOfSettingsPage("successNotification", getTextLanguage(LanguageCode, "daas_ui", "assets.import.asset.import_in_progress")), "Toast notification did not appear");
			sleeper(70000);//CSV file upload takes time
			Assert.assertTrue(settingsPage.postNotificationCheckImportInV3(getEnvironmentSpecificData(System.getProperty("environment"), "MULTIPLAN_ORDERS")), "Message on notification window is incorrect");
			LOGGER.info("Notification message verification for import has passed");

			gotoRootCompaniesTab();
			//Go to User detail page
			userPage.waitUntilElementIsInvisibleOfUserPage("tableOverlay");
			impersonateCompanyByCompanyName(CompaniesVariables.Company_MultiPlanName);
			waitForPageLoaded();
			sleeper(3000);
			// Navigate to Plan  Tab
			companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("subscriptionTab");

			//Verify Active Care Plan
			softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("txtActivePlanName").contains(getTextLanguage(LanguageCode, "idm", "onboarding.pc.service_name")), "Active care plan text verification failed");

			//Verify device enrolled count
			softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("txtDeviceEnrolledText").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "progress_bar.devices_enrolled_s").replace("{count}", CompaniesVariables.VALUEONE)), "Device enrolled text verification failed");

			//Verify PEM  Plan
			companiesDetailsPage.waitForElementsOfCompaniesDetailsPage("txtSubPlanName");
			softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("txtSubPlanName").contains(getTextLanguage(LanguageCode, "lhserver", "global.package_name.hp_proactive_endpoint_management")), "PEM plan text verification failed");

			//Verify Convert to license button
			softAssert.assertTrue(companiesDetailsPage.verifyElementsOfCompaniesDetailsPage("convertToLicModel"), "Convert to license button is not present");
			LOGGER.info("Verified plans and buttons successfully with root login");

		} finally {

			//Logout from account
			logout();
			//login from root
			login("ROOT_ADMIN_DEFAULT_MULTIPLAN", "ROOT_ADMIN_DEFAULT_MULTIPLAN_PASSWORD");
			LOGGER.info("Redirected to company list page");
			waitForPageLoaded();
			impersonateCompanyByCompanyName(CompaniesVariables.Company_MultiPlanName);
			LOGGER.info("Redirected to company details page");
			tenantID = getUrlOfCurrentPage().substring(getUrlOfCurrentPage().length() - 36);
			softAssert.assertTrue(companiesPage.removeCompanyUsingAPI(tenantID), "Company removal failed");
			LOGGER.info("Company has been deleted");
		}
	}
	
	@Test(priority = 96, groups = {"REGRESSIONCOMPANIES2"}, description = "Test case :903972,903953,903945,903954,907386,903953")
	public final void verifyLegacyPlanLicenceToSubscriptionConversion() throws Exception {
		login("APP_ADMIN_USER", "APP_ADMIN_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		CompaniesDetailsPage companiesDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver()).getInstance();
		LicensesListPage subscriptionsListPage = new LicensesListPage(PreDefinedActions.getDriver()).getInstance();
		String subKey1 = "CONVERTLICSUB"+generateRandomString(12).toUpperCase();
		Assert.assertTrue(subscriptionsListPage.createLicenceKey(getEnvironmentSpecificData(System.getProperty("environment"), "DAAS_API_BASE_URL_AUTH") + subscriptionURL, subKey1, CommonVariables.SKU_STANDARD), "Licence key creation failed");
		LOGGER.info("Created licence key " + subKey1);
		logout();
		try {
			//Login with Partner
			login("RESELLER_PARTNERS_EMAIL", "RESELLER_PARTNERS_PASSWORD");
			LOGGER.info("Logged in with partner user");
	
			Assert.assertTrue(companiesPage.createCompanyUsingAPI(subKey1, getEnvironmentSpecificData(System.getProperty("environment"), "COUNTRY_CODE"), LanguageCode, "", getEnvironmentSpecificData(System.getProperty("environment"), "PARTNER_ID3"), CompaniesVariables.COMPANY_NAME_SURECLICKUS, CompaniesVariables.COMPANY_STANDARDTRIAL), "Company Creation failed");
			LOGGER.info(subKey1 + " company created");
			gotoCompaniesTab();
			impersonateCompanyByCompanyName(subKey1);
			LOGGER.info("Redirected to company details page");
	
			companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("subscriptionTab");
			LOGGER.info("Click subcription tab");
			Thread.sleep(2000);
			companiesDetailsPage.clickByJavaScriptOnCompaniesDetailsPage("addNewKeyButton");
			companiesDetailsPage.clickByJavaScriptOnCompaniesDetailsPage("nextButton");
			companiesDetailsPage.enterTextForCompaniesDetailsPage("addSubKey", subKey1);
			softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("planOnSubModal").equals(CommonVariables.PLAN_STANDARD_PI), "Added key plan did not match");
			softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("licModalHeader").equals(getTextLanguage(LanguageCode, "daas_ui", "partner.add.key.title")), "License key modal header did not match");
			Thread.sleep(2000);
			companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("addbuttonModelpopup");
			companiesDetailsPage.waitForElementsOfCompaniesDetailsPage("toastNotification");
			LOGGER.info("Added licence key to a company");
	
			String toastNotification = companiesDetailsPage.getTextOfCompaniesDetailsPage("toastNotification");
			softAssert.assertTrue(toastNotification.equals(getTextLanguage(LanguageCode, "daas_ui", "companies.details.update.success").replace("{name}", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.subscription.modal.title"))), "Key assignment to a company failed");
			companiesDetailsPage.waitUntilElementIsInvisibleOfCompanyDetailsPage("toastNotification");
			softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("planStatusOnSubscriptionTile").contains(getTextLanguage(LanguageCode, "daas_ui", "companies.list.sections.active")), "Assigned key is not in active state");
			softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("subKeyOnSubscriptionTile").contains(subKey1), "Assigned key1 did not match with licence tile key");
			companiesDetailsPage.clickByJavaScriptOnCompaniesDetailsPage("convertToSubModButton");
				
			//Verify details on Convert To Subscription Model popup
			softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("headingonSubscriptionpopup").contains(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.convert_to_subscription")), "Convert To Subscription model heading is not matching");
			softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("messageOnSubscriptionpopup").contains(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.convert_to_subscription_title1")), "Text on Convert To Subscription model is not matching");
			softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("InnerMessageOnSubscriptionpopup").contains(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.convert_to_subscription_message1")), "Inner Text on Convert To Subscription model is not matching");
			softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("InnerMessageConfirmOnSubscriptionpopup").contains(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.convert_to_subscription_message2")), "Inner confirm message on Convert To Subscription model is not matching");
			softAssert.assertTrue(companiesDetailsPage.verifyElementsOfCompaniesDetailsPage("convertBtnOnSub"),"Convert button is not available");
			softAssert.assertTrue(companiesDetailsPage.verifyElementsOfCompaniesDetailsPage("cancelBtnOnSub"),"Cancel button is not available");
				
			companiesDetailsPage.clickByJavaScriptOnCompaniesDetailsPage("convertBtnOnSub");
			companiesDetailsPage.waitForElementsOfCompaniesDetailsPage("convertToastNotification");
			toastNotification = companiesDetailsPage.getTextOfCompaniesDetailsPage("convertToastNotification");
			softAssert.assertTrue(toastNotification.equals(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.convert_to_subscription.start_success")), "Licenses converted start message is not shown");
			LOGGER.info("License to Subscription converison is started");
			
			companiesDetailsPage.waitForElementsOfCompaniesDetailsPage("inProgressNotification");
			softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("inProgressNotification").equals(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.convert_to_subscription.in_progress")), "Conversion in progress notification text is improper.");
			LOGGER.info("License to Subscription converison in progress notification displayed.");
			
			//Verify bell icon notification
			companiesPage.clickOnElementsOfCompaniesPage("notificationBellIcon");
			companiesDetailsPage.waitForElementsOfCompaniesDetailsPage("tableOverlay"); // Need to put wait here since notification is taking
																						 																		// to appear
			LOGGER.info("Clicked on notification bell icon");
			waitForPageLoaded();// Need to put wait here as notification is taking time to appear
			sleeper(6000); // time required to load notification
			softAssert.assertTrue(companiesPage.getTextOfCompanyPage("invitationNotificationDescription").equals(getTextLanguage(LanguageCode, "daas_entitlement_notifier_service", "daas.notifier.convert_to_subscription.success.notification.title").replace("{0}", subKey1)), "Convert Licese to subscription description is matching.");
			LOGGER.info("License to Subscription converison is completed");
			sleeper(3000);
		
			softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("planOnPlanDetailsCards").equals(CompaniesVariables.PLAN_ON_DETAILS), "plan did not match");
			softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("licenseKey").contains(subKey1), "plan did not match");
			softAssert.assertFalse(companiesDetailsPage.verifyElementsOfCompaniesDetailsPage("deleteIcon"), "Delete Icon is available");
			softAssert.assertFalse(companiesDetailsPage.verifyElementsOfCompaniesDetailsPage("editIcon"), "Edit Icon is available");
			softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("statusOnPlanDetailsCards").contains(getTextLanguage(LanguageCode, "daas_ui", "companies.list.sections.active")), "Assigned Subscription is not in active state");
			softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("seatsOnAddSubscription").contains((getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.subscription.total_seats")) + " : " + CompaniesVariables.SEATCOUNT_100), "Seats didn't match");
		
			//Verify details on Add Subscription popup
			companiesDetailsPage.clickByJavaScriptOnCompaniesDetailsPage("addSubscriptionButton");
			companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("planDropdownButton");
			companiesDetailsPage.selectDropdownSingleValueTextFromCompaniesDetailsPage("planDropdownList",CommonVariables.PLAN_PROACTIVE,"valueinDropdown");
			companiesDetailsPage.enterTextForCompaniesDetailsPage("seats", CompaniesVariables.SEATCOUNT_TWO);
			companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("startdate");
			companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("todayDate");
			String date = companiesDetailsPage.getAttributesOfCompaniesDetailsPage("startdate","value");
			softAssert.assertTrue(companiesDetailsPage.verifyElementsOfCompaniesDetailsPage("addButton"), "Add Button is not available");
			softAssert.assertTrue(companiesDetailsPage.verifyElementsOfCompaniesDetailsPage("cancelButton"), "Cancel Button is not available");
			companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("addButton");
			
			toastNotification = companiesDetailsPage.getTextOfCompaniesDetailsPage("toastNotification");
			softAssert.assertTrue(toastNotification.equals(getTextLanguage(LanguageCode, "daas_ui", "global.messages.field_add_success").replace("{field}",
					getTextLanguage(LanguageCode, "daas_ui", "product.list.daasPlan"))), "Subscriptions converted message is not shown");
			LOGGER.info("Added Subscription to the company");
			
			//Verify plan details after Subscription added
			softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("planOnPlanDetailsCards").equals(CompaniesVariables.PLAN_ON_DETAILS), "plan did not match");
			softAssert.assertTrue(companiesDetailsPage.verifyElementsOfCompaniesDetailsPage("deleteIcon"), "Delete Icon is not available");
			softAssert.assertTrue(companiesDetailsPage.verifyElementsOfCompaniesDetailsPage("editIcon"), "Edit Icon is not available");
			softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("statusOnPlanDetailsCards").contains(getTextLanguage(LanguageCode, "daas_ui", "companies.list.sections.active")), "Assigned Subscription is not in active state");
			softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("startDateOnPlanDetailsCards").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "campaign.details.startDate") + " : " + date) , "Start Date did't match");
			softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("seatsOnPlanDetailsCards").contains((getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.subscription.total_seats")) + " : " + CompaniesVariables.SEATCOUNT_TWO), "Seats didn't match");
			softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("renewalTextOnPlanDetailsCards").contains((getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.subscription.renewal")) + " : " + (getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.subscription.auto"))) , "Renewal didn't match");
			
			//Verify 'Open Company' Link redirection to Company detail page
			gotoDashboardTab();
			waitForPageLoaded();
			companiesPage.clickOnElementsOfCompaniesPage("notificationBellIcon");
			companiesDetailsPage.waitForElementsOfCompaniesDetailsPage("tableOverlay"); // Need to put wait here since notification is taking																			 																		// to appear
			LOGGER.info("Clicked on notification bell icon");
			waitForPageLoaded();
			companiesPage.mouseHoverOnCompanyPage("firstNotification");
			companiesPage.clickOnElementsOfCompaniesPage("hamburgerMenuOnNotification");
			softAssert.assertTrue(companiesPage.getTextOfCompanyPage("openCompanyNotification").equals(getTextLanguage(LanguageCode, "daas_entitlement_notifier_service", "daas.notifier.convert_to_subscription.notification.action")), "Open Company link is not present for company.");
			companiesPage.clickOnElementsOfCompaniesPage("openCompanyNotification");
			waitForPageLoaded();
			sleeper(3000);
			softAssert.assertTrue(companiesPage.getTextOfCompaniesPage("companyNameOnDetailsPage").equalsIgnoreCase(subKey1), "Company name is not present on company details page.");
			LOGGER.info("Valid company name is present on company detail page after redirection.");
			softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("companySubscriptionTab").equals(getTextLanguage(LanguageCode, "daas_ui", "subscription.section.plans")), "Plans tab is not redirected after clicking on 'Open Company' link.");
			LOGGER.info("Plans tab is redirected after clicking on 'Open Compnay' link ");
			
			softAssert.assertAll();
	
			}
		finally {
			// Logout from account
			logout();
			login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
			// Remove created company
			impersonateCompanyByCompanyName(subKey1);
			String tenantID = getUrlOfCurrentPage().substring(getUrlOfCurrentPage().length() - 36);
			companiesPage.removeCompanyUsingAPI(tenantID);
		}
		
	}
	/* User Story 909424: [Core] [TechPulse][UI] UI update in authentication tile on company detail page.
	  */
		
		@Test(priority = 108, groups = {"REGRESSIONCOMPANIES1", "SMOKE", "REGRESSION_CI", "SMOKE_CI"}, description = "TESTCASE-911455")
		public final void verifyAuthenticationTileOnCompanyDetailsPageOnRoot() throws Exception {
			login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
			SoftAssert softAssert = new SoftAssert();
			CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
			CompaniesDetailsPage companiesDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver()).getInstance();
			gotoCompaniesTab();
			LOGGER.info("Redirected to companies list page");
			waitForPageLoaded();
			resetTableConfiguration();
			companiesPage.clickOnElementsOfCompaniesPage("firstName");
			waitForPageLoaded();
			LOGGER.info("Redirected to company details page");
			companiesDetailsPage.waitForElementsOfCompaniesDetailsPage("preferenceTab");
			companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("preferenceTab");
			companiesDetailsPage.waitForElementsOfCompaniesDetailsPage("editAuthenticationIcon");
			companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("editAuthenticationIcon");
			
			softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("authenticationmessage").equals(companiesDetailsPage.getTextLanguage(LanguageCode, "daas_ui", "companies.details.sign_in.description")), "Select one or more authentication methods available for the users of this company.");
			softAssert.assertAll();
			LOGGER.info("Validation of Authentication UI text method completed successfully");
		
		}
	
		@Test(priority = 109, groups = {"REGRESSIONCOMPANIES2"}, description = "Test case :913706,913701")
		public final void verifyFreeTrialAccountForPI() throws Exception {
			
			login("ROOT_ADMIN_USER_WhatsNew", "ROOT_PASSWORD_WhatsNew");
			CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
			SoftAssert softAssert = new SoftAssert();
			String subKey1 = "TrialPlanCompany"+generateRandomString(7);
			try {
				LOGGER.info("Redirected to company list page");
				waitForPageLoaded();
				resetTableConfiguration();
				
				Assert.assertTrue(companiesPage.createCompanyUsingAPI(subKey1, getEnvironmentSpecificData(System.getProperty("environment"), "COUNTRY_CODE"), LanguageCode, getEnvironmentSpecificData(System.getProperty("environment"), "MSP_ID"), getEnvironmentSpecificData(System.getProperty("environment"), "PARTNER_ID2"), getEnvironmentSpecificData(System.getProperty("environment"), "ADD_COMPANY_THROUGH_ROOT_PARTNER_EMAIL"), CompaniesVariables.COMPANY_INSIGHTTRIAL), "Company Creation failed");
				
				impersonateCompanyByCompanyName(subKey1);
				LOGGER.info("Redirected to company details page");
				tenantID = getUrlOfCurrentPage().substring(getUrlOfCurrentPage().length() - 36);
				logout();
				LOGGER.info("Logged out");
			
				// Approve Partner Invite
				login("TRIAL_COMPANY_PI", "TRIAL_COMPANY_PI_PASSWORD");
				waitForPageLoaded();
				Thread.sleep(3000);
				pressKey(Keys.ESCAPE);
				gotoNonMSPSettingsTab();
				LOGGER.info("Redirected to Settings tab");
				waitForPageLoaded();
				companiesPage.waitForPresenceOfElementsOfCompaniesPage("assignedPartner");
				companiesPage.clickOnElementsOfCompaniesPage("assignedPartner");
				companiesPage.waitForPresenceOfElementsOfCompaniesPage("invitationLabel");
				
				companiesPage.clickOnElementsOfCompaniesPage("acceptInvitationBtn");
				companiesPage.verifyElementsOfCompaniesPage("approveLabel");
				companiesPage.verifyElementsOfCompaniesPage("approveBtn");
				companiesPage.clickOnElementsOfCompaniesPage("approveBtn");
				companiesPage.waitForElementsOfCompaniesPage("toastNotification");
				String toastNotification = companiesPage.getTextOfCompaniesPage("toastNotification");
				softAssert.assertTrue(toastNotification.equals(getTextLanguage(LanguageCode, "daas_ui", "partner_invite.message.update")), "Toast notification is not generated after accepting invitation from Partner");
				LOGGER.info("Invitation accepted from Partner");
				logout();
				
				login("PARTNER_LOGIN_ID", "PARTNER_LOGIN_PASSWORD");
				CompaniesDetailsPage companiesDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver()).getInstance();
				LOGGER.info("Logged in with partner user");
				gotoCompaniesTab();
				impersonateCompanyByCompanyName(subKey1);
				LOGGER.info("Redirected to company details page");
		
				/*//Verify Edit icon not displayed for trial plan // Will uncomment the code once release is for all users. Currently its only for Alpha users and its tenant specific.
				companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("subscriptionTab");
				LOGGER.info("Click Plans tab");
				Thread.sleep(2000);
				softAssert.assertFalse(companiesDetailsPage.verifyElementsOfCompaniesDetailsPage("companyTrialEndDateicon"), "Edit Icon is Visible to trial plan");
				LOGGER.info("Edit icon not displayed for trial plan");*/
				
				//Verify Total Seat count is 250
				gotoCompaniesTab();
				sleeper(2000);//result takes time to come
				Assert.assertTrue(companiesPage.getTextOfCompaniesPage("companyTotalSeats").equalsIgnoreCase(CompaniesVariables.SEATCOUNT_250), "Toal Seats count value is not proper");
				LOGGER.info("Default Seat count for PI trial account verified successfully.");
				softAssert.assertAll();
		
				}
			finally {
				// Logout from account
				logout();
				login("ROOT_ADMIN_USER_WhatsNew", "ROOT_PASSWORD_WhatsNew");

				// Remove created company
				Assert.assertTrue(companiesPage.removeCompanyUsingAPI(tenantID), "Company removal failed");
				LOGGER.info("Company has been deleted");
			}
		}

	@Test(priority = 110, groups = { "REGRESSIONCOMPANIES2", "SMOKE", "REGRESSION_CI", "SMOKE_CI", "STABILIZATION_STAGING" }, description = "Test Case:366040")

	public final void verifyPartnerDataAccessFromCompanyAcceptFunctionality() throws Exception {

		//Logged in with company
		login("ITA_COMPANY_DETAILS_EMAIL", "ITA_COMPANY_DETAILS_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		String expectedMailContent = null, actualMailContent = null;
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
		CompaniesDetailsPage companyDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver()).getInstance();
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		ArrayList<String> infoLabels = new ArrayList<String>(Arrays.asList(getTextLanguage(LanguageCode, "daas_ui", "partner.info.id").toLowerCase().trim(), getTextLanguage(LanguageCode, "daas_ui", "partner.info.address.street").toLowerCase().trim(), getTextLanguage(LanguageCode, "daas_ui", "partner.info.address.city").toLowerCase().trim(), getTextLanguage(LanguageCode, "daas_ui", "partner.info.address.state").toLowerCase().trim(),
				getTextLanguage(LanguageCode, "daas_ui", "partner.info.address.zip").toLowerCase().trim(), getTextLanguage(LanguageCode, "daas_ui", "partner.info.address.country").toLowerCase().trim()));
		ArrayList<String> partnerDetails = new ArrayList<String>(Arrays.asList(getEnvironmentSpecificData(System.getProperty("environment"), "PARTNER_MULTI_INVITE_ID").toLowerCase(), CompaniesVariables.PARTNER_STREET_ADDRESS, CompaniesVariables.PARTNER_CITY, CompaniesVariables.PARTNER_STATE, CompaniesVariables.PARTNER_ZIP_CODE, CompaniesVariables.PARTNER_COUNTRY));

		// Remove Pending Invitations from Company
		waitForPageLoaded();
		tenantID = getValueFromToken("tenant");
		companyID =  getIDOverviewTab(tenantID, envURL);
		gotoCompanySettingsTab();
		LOGGER.info("Redirected to Settings tab");
		waitForPageLoaded();
		companiesPage.waitForPresenceOfElementsOfCompaniesPage("assignedPartner");
		companiesPage.clickOnElementsOfCompaniesPage("assignedPartner");
		companiesPage.waitForPresenceOfElementsOfCompaniesPage("invitationLabel");
		JSONObject jsonAuthToken = (JSONObject) parser.parse(authTokenParse(companiesPage.getTokenFromLocalStorage("dui_token_e")));
		String tenantID = jsonAuthToken.get("tenant").toString();
		softAssert.assertTrue(removeAllPendingInvitations(environment, tenantID, getEnvironment(environment)), "Pending Company Invitation removal failed");
		softAssert.assertTrue(removeAllReceievedPendingInvitations(environment, tenantID, getEnvironment(environment)), "Pending Partner Invitation removal failed");
		LOGGER.info("Removed all pending invitations from company view");

		LOGGER.info("Clicked on assign partner tab");
		softAssert.assertTrue(unassignPartner(environment), "Partner unassign failed");
		softAssert.assertTrue(settingsPage.getTextOfSettingsPage("assignedPartnerTitle").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.tile.assignment_settings")), "Header on assign partner tile is incorrect");
		softAssert.assertTrue(settingsPage.getTextOfSettingsPage("unassignedPartnerField").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.label.not_assigned")), "Unassigned partner field value on assign partner tile is incorrect");
		softAssert.assertTrue(settingsPage.getTextOfSettingsPage("unassignedPartnerField").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.label.not_assigned")), "Unassigned partner field value on assign partner tile is incorrect");
		softAssert.assertTrue(settingsPage.matchTextOfSettingsPage("unassignedPartnerField", getTextLanguage(LanguageCode, "daas_ui", "companies.details.label.not_assigned")), "Unassigned partner field value on assign partner tile is incorrect");
		settingsPage.waitForElementsOfSettingsPage("editAssignedpartnerButton");
		settingsPage.clickOnElementsOfSettingsPage("editAssignedpartnerButton");
		LOGGER.info("Clicked on edit assigned partner button");
		softAssert.assertTrue(settingsPage.matchTextOfSettingsPage("assignParnterPopupHeader", getTextLanguage(LanguageCode, "daas_ui", "partner.invite.title")), "Header on invite partner popup is incorrect");
		softAssert.assertTrue(settingsPage.verifyElementsOfSettingsPage("assignPartnerInvite"), "Invite button is not present on invite partner popup");
		softAssert.assertTrue(settingsPage.verifyElementsOfSettingsPage("assignPartnerCancel"), "Cancel invite button is not present on invite partner popup");
		sleeper(3000); // time required to load partner details on popup
		settingsPage.clickOnElementsOfSettingsPage("assignPartnerDD");
		LOGGER.info("Clicked on edit assigned partner dropdown");
		settingsPage.clickOnElementsOfSettingsPage("assignPartnerSearchBox");
		settingsPage.enterTextForSettingsPage("assignPartnerSearchBox", getEnvironmentSpecificData(environment, "ASSIGNED_MULTIINVITE_PARTNER_EMAIL"));
		Thread.sleep(1000);
		settingsPage.selectFirstOptionFromDropdownOnSettingsPage("partnersList");
		LOGGER.info("Selected partner on dropdown");
		softAssert.assertTrue(settingsPage.getTextOfListOfSettingsPage("infoLabelsPartner").equals(infoLabels), "Labels under information section of partner assignment popup are incorrect");
	//	softAssert.assertTrue(settingsPage.getTextOfListOfSettingsPage("infoValuesPartner").equals(partnerDetails), "Details of current partner on partner assignment popup are incorrect");
		settingsPage.clickOnElementsOfSettingsPage("assignPartnerCancel");
		LOGGER.info("Clicked on cancel assigned partner button");
		softAssert.assertTrue(settingsPage.getTextOfSettingsPage("unassignedPartnerField").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.label.not_assigned")), "Cancel functionality on partner assignment popup is not working");
		softAssert.assertTrue(settingsPage.matchTextOfSettingsPage("unassignedPartnerField", getTextLanguage(LanguageCode, "daas_ui", "companies.details.label.not_assigned")), "Cancel functionality on partner assignment popup is not working");
		settingsPage.clickOnElementsOfSettingsPage("editAssignedpartnerButton");
		LOGGER.info("Clicked on edit assigned partner button");
		sleeper(2000); // time required to load partner details on popup
		settingsPage.clickOnElementsOfSettingsPage("assignPartnerDD");
		LOGGER.info("Clicked on edit assigned partner dropdown");
		settingsPage.waitForElementsOfSettingsPage("assignPartnerSearchBox");
		settingsPage.enterTextForSettingsPage("assignPartnerSearchBox", getEnvironmentSpecificData(environment, "ASSIGNED_MULTIINVITE_PARTNER_EMAIL"));
		waitForPageLoaded();
		settingsPage.selectFirstOptionFromDropdownOnSettingsPage("partnersList");
		LOGGER.info("Selected partner on dropdown");
		settingsPage.clickOnElementsOfSettingsPage("assignPartnerInvite");
		LOGGER.info("Clicked on save assigned partner button");
		Thread.sleep(3000);
		settingsPage.waitForElementsOfSettingsPage("toastNotification");
		softAssert.assertTrue(settingsPage.verifyElementsOfSettingsPage("toastNotification"), "Toast notification is not generated after assigned MSP is changed");
		logout();
		LOGGER.info("Logged out successfully");

		// Verify Accept invitation functionality from partner
		login("RESELLER_NEW_UI_Companies_EMAIL_COMPANIES", "RESELLER_NEW_UI_Companies_PASSWORD_COMPANIES");
		waitForPageLoaded();
		gotoCompaniesTab();
		resetTableConfiguration();
		LOGGER.info("Redirected to companies tab");
		waitForPageLoaded();
		companiesPage.scrollOnCompaniesPage("partnerAssignmentDropDown");
		companiesPage.clickOnElementsOfCompaniesPage("partnerAssignmentDropDown");
		companiesPage.enterTextForCompaniesPage("searchBox", getTextLanguage(LanguageCode, "daas_ui", "partner_invite.status.awaiting_partner"));
		waitForPageLoaded();
		companiesPage.clickOnElementsOfCompaniesPage("dropDownOption");
		pressKey(Keys.ESCAPE);
		Thread.sleep(2000);
		waitForPageLoaded();
		companiesPage.enterTextForCompaniesPage("companyNameSearch", getEnvironmentSpecificData(environment, "INVITE_PARTNER_COMPANY"));
		companiesPage.mouseHoverOnCompanyPage("companySearchResult");
		companiesPage.clickByJavaScriptOnCompaniesPage("companyCheckbox");
		softAssert.assertTrue(companiesPage.verifyElementsOfCompaniesPage("acceptCustomerButton"), "Accept button is not present on invitation");
		softAssert.assertTrue(companiesPage.verifyElementsOfCompaniesPage("declineCustomerButton"), "Decline button is not present on invitation");
		companiesPage.clickOnElementsOfCompaniesPage("acceptCustomerButton");
		LOGGER.info("Clicked on save decline partner button");
		softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("acceptPopupHeader", getTextLanguage(LanguageCode, "daas_ui", "companies.list.accept_customer")), "Title on approve popup is incorrect");
		softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("acceptDescriptionMessage1", getTextLanguage(LanguageCode, "daas_ui", "companies.list.accept_customer.message").replace("{name}", inviteComapny)), "Description on approve popup is incorrect");
		softAssert.assertTrue(companiesPage.verifyElementsOfCompaniesPage("dialogResend"), "Save button is not present on approve popup");
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("cancelApproveButton"), "Cancel button is not present on approve popup");
		companiesPage.clickOnElementsOfCompaniesPage("dialogResend");
		LOGGER.info("Clicked on approve partner button");
		companiesPage.waitForElementsOfCompaniesPage("toastNotification");
		Thread.sleep(3000);//Mail takes time to arrive
		expectedMailContent = getTextLanguage(LanguageCode, "idm", "association.partner.accept.header") + " " + getTextLanguage(LanguageCode, "idm", "association.partner.accept.header") + " " + getTextLanguage(LanguageCode, "lhserver", "users.reset_password.hello").replace("%{user_name}", getEnvironmentSpecificData(environment, "COMPANY_NAME_EMAIL_VERIFY")) + " " + getTextLanguage(LanguageCode, "idm", "association.partner.accept.message") + " "
				+ getEnvironmentSpecificData(environment, "ASSIGNED_MULTIINVITE_PARTNER_NAME") + " " + getEnvironmentSpecificData(environment, "ASSIGNED_MULTIINVITE_PARTNER_EMAIL") + " " + getTextLanguage(LanguageCode, "daas_ui", "partner.id") + " " + getEnvironmentSpecificData(System.getProperty("environment"), "PARTNER_MULTI_INVITE_ID") + " " + getTextLanguage(LanguageCode, "idm", "association.accept.respond.message") + " " + getTextLanguage(LanguageCode, "lhserver", "welcome.signin.submit").toUpperCase() + " "
				+getTextLanguage(LanguageCode, "idm", "workspace.mail.common.thank_you") + " " + (getTextLanguage(LanguageCode, "lhserver", "global.copyright").replace("%{current_year}", getCurrentYear()));
		actualMailContent = verifyEmailContent(environment, "IT_ADMIN_PARTNERS_EMAIL", "ASSIGNED_MULTIINVITE_PARTNER_NAME", " has accepted your invitation", true);
		softAssert.assertTrue(actualMailContent.equalsIgnoreCase(expectedMailContent), "Mail content does not match");
		LOGGER.info("Email Verification to it admin is done succesfully");
		logout();
		LOGGER.info("Logged out successfully");


		//Again logged in with company and unassign partner
		login("ITA_COMPANY_DETAILS_EMAIL", "ITA_COMPANY_DETAILS_PASSWORD");
		gotoCompanySettingsTab();
		LOGGER.info("Redirected to settings tab");
		waitForPageLoaded();
		settingsPage.clickOnElementsOfSettingsPage("assignedPartnerTab");
		LOGGER.info("Clicked on assign partner tab");
		softAssert.assertTrue(settingsPage.matchTextOfSettingsPage("currentPartner", getEnvironmentSpecificData(environment, "ASSIGNED_MULTIINVITE_PARTNER_EMAIL")), "Partner not reflceted after assigning to company");
		softAssert.assertTrue(settingsPage.matchTextOfSettingsPage("currentPartnerID", getTextLanguage(LanguageCode, "daas_ui", "pending.invitations.partnerId").trim() + " " + getEnvironmentSpecificData(System.getProperty("environment"), "PARTNER_MULTI_INVITE_ID")), "Incorrect partner id displayed on company details page");
		settingsPage.clickOnElementsOfSettingsPage("editPartnerButton");
		LOGGER.info("Clicked on edit partner button");
		softAssert.assertTrue(settingsPage.matchTextOfSettingsPage("unassignPopupHeader", getTextLanguage(LanguageCode, "daas_ui", "company.unassign.partner")), "Title on unassign partner popup is incorrect");
		softAssert.assertTrue(settingsPage.matchTextOfSettingsPage("unassignDescription", getTextLanguage(LanguageCode, "daas_ui", "company.unassign.partner.desc").replace("{partnerName}", getEnvironmentSpecificData(environment, "ASSIGNED_MULTIINVITE_PARTNER_EMAIL"))), "Description on unassign partner popup is incorrect");
		softAssert.assertTrue(settingsPage.verifyElementsOfSettingsPage("unassignButton"), "Save button is not present on unassign partner popup");
		softAssert.assertTrue(settingsPage.verifyElementsOfSettingsPage("cancelUnassignButton"), "Cancel button is not present on unassign partner popup");
		settingsPage.clickOnElementsOfSettingsPage("unassignButton");
		LOGGER.info("Clicked on unassign partner button");
		Thread.sleep(3000);
		settingsPage.waitForElementsOfSettingsPage("toastNotification");
		settingsPage.waitForElementsOfSettingsPageForinvisibile("toastNotification");
		Thread.sleep(2000);
		softAssert.assertTrue(settingsPage.getTextOfSettingsPage("unassignedPartnerField").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "companies.details.label.not_assigned")), "Partner unassignment not reflected on company details page");
		LOGGER.info("Partner unassigned successfully");

		softAssert.assertAll();
		LOGGER.info("All test cases of verifyPartnerDataAccessFromCompanyAcceptFunctionality passed successfully");

		logout();
		LOGGER.info("Logged out successfully");

	}

	// We will enable testcase when code gets deployed to staging
	//Verify ITSM id field while creating company by root
	@Test(priority = 111, groups = {"REGRESSIONCOMPANIES1", "REGRESSION_CI"}, description = "TC: 968368",enabled = false)
	public final void verifyITSMFieldAddByRoot() throws Exception {

		login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();

		SoftAssert softAssert = new SoftAssert();
		waitForPageLoaded();

		companiesPage.clickOnElementsOfCompaniesPage("addButton");
		companiesPage.waitForElementsOfCompaniesPage("addCompanyDialogPopupNextButton");
		softAssert.assertTrue(companiesPage.getTextOfCompaniesPage("itsmCheckboxText").equals(companiesPage.getTextLanguage(LanguageCode, "daas_ui", "company.add.popup.lnkToItsm")), "ITSM checkbox text not verified ");
		companiesPage.clickByJavaScriptOnCompaniesPage("itsmCheckbox");

		Thread.sleep(1000);
       //Enter company name
		companiesPage.enterTextForCompaniesPage("itsmCompanyNameBox", getEnvironmentSpecificData(environment, "ITSMCompanyName"));
		companiesPage.waitUntilElementIsInvisibleOfCompanyPage("itsmLoader");
		companiesPage.clickOnElementsOfCompaniesPage("CompanyNameOptionList");

		softAssert.assertTrue(companiesPage.verifyElementsOfCompaniesPage("itsmIdBox"), "itsmId box not appear");
		softAssert.assertTrue(companiesPage.verifyElementsOfCompaniesPage("itsmNotificationMessage"), "itsm notification message not appearing");

		LOGGER.info("Itsm id and notification message verified successfully");
		companiesPage.enterTextForCompaniesPage("cityOnPopup", CompaniesVariables.CITY);

		//Select country
		companiesPage.waitForElementsOfCompaniesPage("countryArrowOnPopup");
		sleeper(2000);
		companiesPage.clickOnElementsOfCompaniesPage("countryArrowOnPopup");
		sleeper(2000);
		companiesPage.enterTextForCompaniesPage("countrySearchOnPopup", CompaniesVariables.COUNTRY);
		companiesPage.clickOnElementsOfCompaniesPage("countrySelectOnPopup");
		//Select language
		companiesPage.clickOnElementsOfCompaniesPage("languageArrowOnPopup");
		companiesPage.enterTextForCompaniesPage("languageSearchOnPopup", CommonVariables.LANGUAGE);

		companiesPage.clickOnElementsOfCompaniesPage("languageSelectOnPopup");
		companiesPage.clickOnElementsOfCompaniesPage("addCompanyDialogPopupNextButton");

		LOGGER.info("Clicked on next button after first step");

		softAssert.assertAll();

		logout();

	}

	// We will enable testcase when code gets deployed to staging
	//Verify updation of itsm id to existing company.
	@Test(priority = 112, groups = {"REGRESSIONCOMPANIES1", "REGRESSION_CI"}, description = "TC: 968368",enabled = false)
	public final void verifyUpdateITSMFieldByRoot() throws Exception {

		login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");

		CompaniesDetailsPage companiesDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver()).getInstance();
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();

		SoftAssert softAssert = new SoftAssert();
		waitForPageLoaded();
		impersonateCompanyByCompanyName(getEnvironmentSpecificData(environment, "CompanyNameForEditITSM"));
		LOGGER.info("Redirected to company details page");
		waitForPageLoaded();
		companiesDetailsPage.waitForElementsOfCompaniesDetailsPage("itsmField");
		softAssert.assertTrue(companiesDetailsPage.verifyElementsOfCompaniesDetailsPage("itsmField"),"itsm field is not present");

		//Click on edit icon of itsm
		companiesDetailsPage.scrollOnCompaniesDetailsPage("itsmEditIcon");
		Thread.sleep(1000);
		companiesDetailsPage.moveToElementOnCompaniesDetailsPage("itsmEditIcon");
		companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("itsmEditIcon");
		Thread.sleep(1000);
		companiesPage.waitUntilElementIsInvisibleOfCompanyPage("itsmLoader");
		softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("itsmEditModelHeader").equals(companiesPage.getTextLanguage(LanguageCode, "daas_ui", "company.linkWithItsm.title")),"itsm edit modal header text not verified successfully");
		softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("itsmEditModelNotification").equals(companiesPage.getTextLanguage(LanguageCode, "daas_ui", "company.add.lnkToItsm.noMatch")),"itsm edit notification text not verified successfully");

		companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("companyNameClearButton");
		//Enter company name
		companiesPage.enterTextForCompaniesPage("itsmCompanyNameBox", getEnvironmentSpecificData(environment, "ITSMCompanyName"));
		companiesPage.waitUntilElementIsInvisibleOfCompanyPage("itsmLoader");
		companiesPage.clickOnElementsOfCompaniesPage("CompanyNameOptionList");
		Thread.sleep(1000);
		softAssert.assertTrue(companiesDetailsPage.verifyElementsOfCompaniesDetailsPage("itsmIdBox"), "itsmId box not appear");
		softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("itsmEditModelNotificationText").equals(companiesPage.getTextLanguage(LanguageCode, "daas_ui", "company.add.lnkToItsm.warning")), "itsm notification message not appearing");
		softAssert.assertTrue(companiesDetailsPage.getTextOfCompaniesDetailsPage("itsmWarningMessage").replace("* ","").equals(companiesPage.getTextLanguage(LanguageCode, "daas_ui", "company.add.lnkToItsm.warning1")), "itsm notification warning text not appearing");

		LOGGER.info("Itsm id and notification message verified successfully");

		softAssert.assertTrue(companiesDetailsPage.verifyElementsOfCompaniesDetailsPage("itsmBtnSave"), "Save button for itsmId not appear");
		softAssert.assertTrue(companiesDetailsPage.verifyElementsOfCompaniesDetailsPage("itsmBtnCancel"), "Cancel button for itsmId box not appear");

		//Click on cancel button
		companiesDetailsPage.clickOnElementsOfCompaniesDetailsPage("itsmBtnCancel");
		waitForPageLoaded();
		softAssert.assertAll();

		logout();
	}
	
	@DataProvider
	public Object[][] loginDataForSupportContactChange() {
		Object[][] data = new Object[3][2];
		data[0][0] = "MSP_COMPANY_SUPPORTCONTACT_EMAIL";
		data[0][1] = "MSP_COMPANY_SUPPORTCONTACT_PASSWORD";
		data[1][0] = "MSP_SUPPORT_SUPPORTCONTACT_EMAIL";
		data[1][1] = "MSP_SUPPORT_SUPPORTCONTACT_PASSWORD";
		data[2][0] = "IT_ADMIN_SUPPORT_CONTACT_EMAIL";
		data[2][1] = "IT_ADMIN_SUPPORT_CONTACT_PASSWORD";

		return data;
	}
	
	/**
	 * Feature_1048586: Manage support contacts, This method Will run the tests Add,View,Update and delete support contacts test cases for
	 * IT ADMIN, MSP ADMIN, MSP SUPPORT SPECILIST
	 * 
	 * @param username
	 * @param password
	 * @throws Exception
	 */

		@Test(priority = 113, description = "Test case ID:1166430,1148495,1166433,1159570", groups = {
				"REGRESSIONCOMPANIES1", "SMOKE", "REGRESSION_CI", "STABILIZATION_STAGING" }, enabled = true)
		public final void verifySupportContactsForTenants() throws Exception {
			login("ITA_COMPANY_DETAILS_EMAIL", "ITA_COMPANY_DETAILS_PASSWORD");
			SoftAssert softAssert = new SoftAssert();
			CompaniesDetailsPage companyDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver())
					.getInstance();
//				if (username != "IT_ADMIN_SUPPORT_CONTACT_EMAIL") {
//					gotoCompaniesTab();
//					LOGGER.info("Redirected to companies list page");
//					waitForPageLoaded();
//					impersonateCompanyByCompanyName(CompaniesVariables.SUPPORTCONATCTS_COMPANY_NAME);
//				} else {
					gotoCompanySettingsTab();
				//}
				// opening Support Contacts tab
				companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("Supportcontactstab");
				LOGGER.info("Navigated to support Conatcts Tab");

				softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("createbutton"),
						"add button not available");

				// Adding newsupport contact
				companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("createbutton");
				softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("createnewcontactpage"),
						"createnewcontacts page not opened ");
				LOGGER.info("Create support contact modal is opend");
				softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("addexitingcontacts"),
						"addexitingcontacts option not available");
				softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("addnewconatcts"),
						"addnewconatcts option not available");

				if (!companyDetailsPage.verifyDropdownIsOpenOfCompaniesDetailsPage("dropdownselectedfornewusers")) {
					companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("addnewconatcts");
				} else
					companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("addnewconatcts");
				LOGGER.info("clicked on new contacts");
				// adding new users
				companyDetailsPage.enterTextForCompaniesDetailsPage("firstnamebox",
						CompaniesVariables.FIRST_NAME_CONTACT);
				companyDetailsPage.enterTextForCompaniesDetailsPage("lastnamebox",
						CompaniesVariables.LAST_NAME_CONTACT);
				companyDetailsPage.enterTextForCompaniesDetailsPage("emailbox", CompaniesVariables.EMAIL_CONTACT);
				companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("createbuttoninaddmodal");
				companyDetailsPage.waitForElementsOfCompaniesDetailsPage("toastNotification");
				String successMessage = companyDetailsPage.getTextOfCompaniesDetailsPage("toastNotification");
				softAssert.assertTrue(
						successMessage.equalsIgnoreCase(
								getTextLanguage(LanguageCode, "daas_ui", "manage.support.contacts.contact.added")),
						"Toast notification is not generated after adding a administrator user");
				LOGGER.info(successMessage);
				// updating support contact
				companyDetailsPage.resetSupportContactsTableConfiguration();
				companyDetailsPage.enterTextForCompaniesDetailsPage("emailsearchbox", CompaniesVariables.EMAIL_CONTACT);
				companyDetailsPage.waitForElementsOfCompaniesDetailsPage("namesearchbox");
				companyDetailsPage.enterTextForCompaniesDetailsPage("namesearchbox", CompaniesVariables.FIRST_NAME_CONTACT);
				companyDetailsPage.clickByJavaScriptOnCompaniesDetailsPage("selectallcheckbox");
				companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("editbutton");
				companyDetailsPage.waitForElementsOfCompaniesDetailsPage("Editcontactmodal");
				companyDetailsPage.enterTextwithoutclearOnCompanyPage("firstnameeditbox",
						CompaniesVariables.FIRST_NAME_CONTACT_ADD);
				companyDetailsPage.enterTextwithoutclearOnCompanyPage("lastnameditbox",
						CompaniesVariables.LAST_NAME_CONTACT_ADD);
				companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("saveeditbutton");
				companyDetailsPage.waitForElementsOfCompaniesDetailsPage("toastNotification");
				String updatemessage = companyDetailsPage.getTextOfCompaniesDetailsPage("toastNotification");
				softAssert.assertTrue(
						updatemessage.equalsIgnoreCase(
								getTextLanguage(LanguageCode, "daas_ui", "manage.support.contacts.contact.updated")),
						"Toast notification is not generated after adding a administrator user");
				LOGGER.info(updatemessage);
				companyDetailsPage.clearTextRefreshFromCompaniesDetailsPageTextField("emailsearchbox");
				companyDetailsPage.enterTextForCompaniesDetailsPage("emailsearchbox", CompaniesVariables.EMAIL_CONTACT);

				// removing support contact
				companyDetailsPage.clickByJavaScriptOnCompaniesDetailsPage("selectallcheckbox");
				companyDetailsPage.waitForElementsOfCompaniesDetailsPage("removebutton");
				companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("removebutton");
				companyDetailsPage.waitForElementsOfCompaniesDetailsPage("removemodal");
				companyDetailsPage.verifyElementsOfCompaniesDetailsPage("removemodal");
				companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("removeconfirmbutton");
				companyDetailsPage.waitForElementsOfCompaniesDetailsPage("toastNotification");
				String removemessage = companyDetailsPage.getTextOfCompaniesDetailsPage("toastNotification");
				softAssert.assertTrue(
						removemessage.equalsIgnoreCase(
								getTextLanguage(LanguageCode, "daas_ui", "manage.support.contacts.contact.removed")),
						"Toast notification is not generated after adding a administrator user");
				LOGGER.info(removemessage);

				logout();

			}
		@Test(priority = 114, description = "TestCase ID: 346319", groups = { "REGRESSIONCOMPANIES2", "REGRESSION_CI" })
		public final void verifyLifeCycleStatussectiononcompanydetailspage() throws Exception {
			CompaniesDetailsPage companyDetailsPage = new CompaniesDetailsPage(PreDefinedActions.getDriver()).getInstance();
			login("NEW_MSP_ADMIN_US_EMAIl", "NEW_MSP_ADMIN_US_PASSWORD");
			SoftAssert softAssert = new SoftAssert();
			ArrayList<String> lifecycleFields = new ArrayList<String>();
			String LIFECYCLE_STATUS_FIELD_ONE = generateRandomString(11);
			String LIFECYCLE_STATUS_FIELD_TWO = generateRandomString(11);
			String LIFECYCLE_STATUS_FIELD_THREE = generateRandomString(11);
			String LIFECYCLE_STATUS_FIELD_FOUR = generateRandomString(11);
			String LIFECYCLE_STATUS_FIELD_FIVE = generateRandomString(11);
			String LIFECYCLE_STATUS_FIELD_SIX = generateRandomString(11);
			String LIFECYCLE_STATUS_FIELD_SEVEN = generateRandomString(11);
			String LIFECYCLE_STATUS_FIELD_EIGHT = generateRandomString(11);
			String LIFECYCLE_STATUS_FIELD_NINE = generateRandomString(11);
			String LIFECYCLE_STATUS_FIELD_TEN = generateRandomString(11);
			String LIFECYCLE_STATUS_FIELD_ELEVEN = generateRandomString(11);
			String LIFECYCLE_STATUS_FIELD_TWELVE = generateRandomString(11);
			String LIFECYCLE_STATUS_FIELD_THIRTEEN = generateRandomString(11);
			String LIFECYCLE_STATUS_FIELD_FOURTEEN = generateRandomString(11);
			String LIFECYCLE_STATUS_FIELD_FIFTEEN = generateRandomString(11);
			lifecycleFields.add(LIFECYCLE_STATUS_FIELD_ONE.toLowerCase());
			lifecycleFields.add(LIFECYCLE_STATUS_FIELD_TWO.toLowerCase());
			lifecycleFields.add(LIFECYCLE_STATUS_FIELD_THREE.toLowerCase());
			lifecycleFields.add(LIFECYCLE_STATUS_FIELD_FOUR.toLowerCase());
			lifecycleFields.add(LIFECYCLE_STATUS_FIELD_FIVE.toLowerCase());
			lifecycleFields.add(LIFECYCLE_STATUS_FIELD_SIX.toLowerCase());
			lifecycleFields.add(LIFECYCLE_STATUS_FIELD_SEVEN.toLowerCase());
			lifecycleFields.add(LIFECYCLE_STATUS_FIELD_EIGHT.toLowerCase());
			lifecycleFields.add(LIFECYCLE_STATUS_FIELD_NINE.toLowerCase());
			lifecycleFields.add(LIFECYCLE_STATUS_FIELD_TEN.toLowerCase());
			lifecycleFields.add(LIFECYCLE_STATUS_FIELD_ELEVEN.toLowerCase());
			lifecycleFields.add(LIFECYCLE_STATUS_FIELD_TWELVE.toLowerCase());
			lifecycleFields.add(LIFECYCLE_STATUS_FIELD_THIRTEEN.toLowerCase());
			lifecycleFields.add(LIFECYCLE_STATUS_FIELD_FOURTEEN.toLowerCase());
			lifecycleFields.add(LIFECYCLE_STATUS_FIELD_FIFTEEN.toLowerCase());
		gotoCompaniesTab();
		waitForPageLoaded();
		impersonateCompanyByCompanyName(getEnvironmentSpecificData(System.getProperty("environment"), "EDIT_COMPANY_NAME"));
		LOGGER.info("Redirected to companies details page");
		goToPreferencesTab();
		LOGGER.info("Navigated to preferences tab");
		sleeper(2000);
		companyDetailsPage.scrollOnCompaniesDetailsPage("customFieldsTitle");
		softAssert.assertTrue(companyDetailsPage.getTextOfCompaniesDetailsPage("lifecycleStatusLabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "deviceDetail.lifeCycleStatus.label")), "Title on lifecycle status fields tile is incorrect");
		//companyDetailsPage.scrollOnCompaniesDetailsPage("lifecycleStatusEditButton");
		sleeper(2000);
		if (!companyDetailsPage.getTextOfCompaniesDetailsPage("lifecycleStatusValues").equals(CompaniesVariables.CUSTOM_FIELDS_NOT_CONFIGURED)) {
			companyDetailsPage.scrollOnCompaniesDetailsPage("customFieldsTitle");
			companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("lifecycleStatusEditButton");
			List<WebElement> uiList = companyDetailsPage.getElementsOfCompanyDetailsPage("lifecycleDeleteButton");
			companyDetailsPage.clickElementsOfCompanyDetailsPage(uiList);
			waitForPageLoaded();
			companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("saveLifecycleField");
			//companyDetailsPage.waitForElementsOfCompaniesDetailsPage("toastNotification");
			Assert.assertTrue(companyDetailsPage.waitForElementsOfCompaniesDetailsPage("toastNotification"), "Already existing lifecycle status fields were not removed successfully, cannot proceed further : TEST CASE 280877");
			LOGGER.info("Already existing lifecycle fields removed successfully");
		} else {
			LOGGER.info("No existing lifecycle fields fields present");
		}
		
		companyDetailsPage.waitForElementsOfCompaniesDetailsPage("tableOverlay");
		companyDetailsPage.scrollOnCompaniesDetailsPage("customFieldsTitle");
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("lifecycleStatusEditButton");
		softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("lifecycleTitleOnPopup", getTextLanguage(LanguageCode, "daas_ui", "deviceDetail.lifeCycleStatus.label")), "Title on lifecycle status fields popup is incorrect");
		softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("lifecycleDescriptionOnPopup", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.lifecycle_status.popup.subtitle")), "Subtitle on lifecycle status fields popup is incorrect");
		companyDetailsPage.enterTextForCompaniesDetailsPage("lifecycleStatusFirstTextbox", LIFECYCLE_STATUS_FIELD_ONE);
		companyDetailsPage.clickByJavaScriptOnCompaniesDetailsPage("addMoreFieldsLinkLifecycle");
		companyDetailsPage.enterTextForCompaniesDetailsPage("lifecycleStatusSecondTextbox", LIFECYCLE_STATUS_FIELD_TWO);
		companyDetailsPage.waitForElementsOfCompaniesDetailsPage("cancelLifecycleField");
		companyDetailsPage.clickOnElementsOfCompaniesDetailsPage("cancelLifecycleField");
		waitForPageLoaded();
		LOGGER.info("Clicked on cancel button of lifecycle status fields popup");
		softAssert.assertTrue(companyDetailsPage.matchTextOfCompaniesDetailsPage("lifecycleStatusValues", CompaniesVariables.CUSTOM_FIELDS_NOT_CONFIGURED), "Cancel functionality on lifecycle status fields popup is not working correctly");

		companyDetailsPage.addAllLifecycleStatusFields("lifecycleStatusEditButton", "addMoreFieldsLinkLifecycle", "saveLifecycleField", "lifecycleStatusFirstTextbox", "lifecycleStatusSecondTextbox", "lifecycleStatusThirdTextbox", "lifecycleStatusFourthTextbox", "lifecycleStatusFifthTextbox", "lifecycleStatusSixthTextbox", "lifecycleStatusSeventhTextbox", "lifecycleStatusEighthTextbox", "lifecycleStatusNinthTextbox", "lifecycleStatusTenthTextbox", "lifecycleStatusEleventhTextbox",
				"lifecycleStatusTwelvethTextbox", "lifecycleStatusThirteenthTextbox", "lifecycleStatusFourteenthTextbox", "lifecycleStatusFifteenthTextbox", LIFECYCLE_STATUS_FIELD_ONE, LIFECYCLE_STATUS_FIELD_TWO, LIFECYCLE_STATUS_FIELD_THREE, LIFECYCLE_STATUS_FIELD_FOUR, LIFECYCLE_STATUS_FIELD_FIVE, LIFECYCLE_STATUS_FIELD_SIX, LIFECYCLE_STATUS_FIELD_SEVEN, LIFECYCLE_STATUS_FIELD_EIGHT, LIFECYCLE_STATUS_FIELD_NINE, LIFECYCLE_STATUS_FIELD_TEN, LIFECYCLE_STATUS_FIELD_ELEVEN,
				LIFECYCLE_STATUS_FIELD_TWELVE, LIFECYCLE_STATUS_FIELD_THIRTEEN, LIFECYCLE_STATUS_FIELD_FOURTEEN, LIFECYCLE_STATUS_FIELD_FIFTEEN);
		companyDetailsPage.waitForElementsOfCompaniesDetailsPage("toastNotification");
		softAssert.assertTrue(companyDetailsPage.verifyElementsOfCompaniesDetailsPage("toastNotification"), "Save functionality on custom fields popup is not working correctly : TEST CASE 280877");
		softAssert.assertAll();
		LOGGER.info("Every steps under verifyLifeCycleStatussectiononcompanydetailspage passed & verified successfully");
		}

		}

