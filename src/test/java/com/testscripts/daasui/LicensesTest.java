package com.testscripts.daasui;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.basesource.action.PreDefinedActions;
import com.basesource.utils.CSVFileReader;
import com.daasui.constants.CommonVariables;
import com.daasui.constants.CompaniesVariables;
import com.daasui.constants.ConstantPath;
import com.daasui.constants.UserVariables;
import com.daasui.pages.CompaniesPage;
import com.daasui.pages.LicensesDetailsPage;
import com.daasui.pages.LicensesListPage;

public class LicensesTest extends CommonTest {
	private static Logger LOGGER = LogManager.getLogger(LicensesTest.class);
	public static String subscriptionURL = "ui/view/subscriptionsLicense/";
	public static String subscriptionRes = "services/subscription_admin_service/1.0/licenses/";
	public static String subscriptionAssignURL = "services/license_service/1.0/license/assignLicense";

	/**
	 * This test case will verify title of License page
	 * 
	 * @throws Exception
	 */
	@Test(priority = 1, groups = { "REGRESSION", "REGRESSION_CI", "PENTEST" }, description = "TestCase 355289")
	public final void verifyLicensesTitle() throws Exception {
		login("APP_ADMIN_USER", "APP_ADMIN_PASSWORD");
		gotoSubscriptionsTab();
		LicensesListPage LicensesListPage = new LicensesListPage(PreDefinedActions.getDriver()).getInstance();

		Assert.assertTrue(LicensesListPage.getTextOfSubscriptionsListPage("subscriptionsPageTitle").equals(getTextLanguage(LanguageCode, "daas_ui", "sidemenu.subscriptions")), "Licenses title text is incorrect");
		LOGGER.info("Subscriptions title is matched");
	}

	/**
	 * This method will verify the table configuration test cases of plan tab on Licenses list page
	 * 
	 * @throws Exception
	 */
	@Test(priority = 2, groups = { "REGRESSION", "REGRESSION_CI", "STABILIZATION_STAGING" }, description = "TestCase 355289")
	public final void verifyTableConfigurationOfPlanTab() throws Exception {
		login("APP_ADMIN_USER", "APP_ADMIN_PASSWORD");
		String tenantID = getValueFromToken("tenant");
		gotoSubscriptionsTab();
		waitForPageLoaded();
		// verifying search service API
		Assert.assertTrue(getStatusCode(getSearchServiceApi(environment) + CommonVariables.DETAILSSEARCHSERVICEAPI + tenantID + CommonVariables.DETAILSSEARCHSERVICEAPI2, UserVariables.SEARCHSERVICEBODY, "POST", getEnvironment(environment)) == CommonVariables.CODEOK, "Search service failed for plan subscription list page");
		ArrayList<String> columnName = new ArrayList<String>(Arrays.asList(getTextLanguage(LanguageCode, "daas_ui", "users.list.label.name"), getTextLanguage(LanguageCode, "daas_ui", "contents.tableColumns.description"), getTextLanguage(LanguageCode, "daas_ui", "subscription.license.modal.free_trail"), getTextLanguage(LanguageCode, "daas_ui", "subscription.list.expiry_days")));
		ArrayList<String> checkboxValue = new ArrayList<String>(Arrays.asList("true", "true", "true", "true"));
		ArrayList<String> name = new ArrayList<String>(Arrays.asList(getTextLanguage(LanguageCode, "daas_ui", "users.list.label.name")));
		verifyTableConfigurationTests(columnName, checkboxValue, name);
	}

	/**
	 * This method will verify the table configuration test cases of License keys tab on License list page
	 * 
	 * @throws Exception
	 */
	@Test(priority = 3, groups = { "REGRESSION", "REGRESSION_CI", "STABILIZATION_STAGING" }, description = "TestCase 355288")
	public final void verifyTableConfigurationOfLicenseKeyUnAssignedTab() throws Exception {
		login("APP_ADMIN_USER", "APP_ADMIN_PASSWORD");
		LicensesListPage LicensesListPage = new LicensesListPage(PreDefinedActions.getDriver()).getInstance();
		String tenantID = getValueFromToken("tenant");
		gotoSubscriptionsTab();
		LicensesListPage.clickByJavaScriptOnSubscriptionsListPage("subscriptionKeyTab");
		LicensesListPage.waitForElementsOfSubscriptionsListPage("spinnerSKU");
		//resetTableConfiguration();
		LOGGER.info("Clicked License keys tab");

		LicensesListPage.clickOnElementsOfSubscriptionsListPage("actionBar");
		LicensesListPage.clickOnElementsOfSubscriptionsListPage("unassignedButton");
		LOGGER.info("Switched to unassigned section");

		// verifying search service API
		Assert.assertTrue(getStatusCode(getSearchServiceApi(environment) + CommonVariables.DETAILSSEARCHSERVICEAPI + tenantID + CommonVariables.DETAILSSEARCHSERVICEAPI2, UserVariables.SEARCHSERVICEBODY, "POST", getEnvironment(environment)) == CommonVariables.CODEOK, "Search service failed for sub keys License list page");
		ArrayList<String> columnName = new ArrayList<String>(
				Arrays.asList(getTextLanguage(LanguageCode, "daas_ui", "subscription.list.order_number"), getTextLanguage(LanguageCode, "daas_ui", "subscription.list.subscription_key"), getTextLanguage(LanguageCode, "daas_ui", "subscription.license.details.users"), getTextLanguage(LanguageCode, "daas_ui", "subscription.list.purchase_date"), getTextLanguage(LanguageCode, "daas_ui", "asset_details_start_date"), getTextLanguage(LanguageCode, "daas_ui", "subscription.license.details.state")));
		ArrayList<String> checkboxValue = new ArrayList<String>(Arrays.asList("true", "true", "true", "true", "false", "true"));
		ArrayList<String> name = new ArrayList<String>(Arrays.asList(getTextLanguage(LanguageCode, "daas_ui", "subscription.list.subscription_key")));
		verifyTableConfigurationTests(columnName, checkboxValue, name);
	}

	/**
	 * This method will verify the table configuration test cases of SKU tab on License list page
	 * 
	 * @throws Exception
	 */
	@Test(priority = 4, groups = { "REGRESSION", "REGRESSION_CI","STABILIZATION_STAGING" }, description = "TestCase 355287")
	public final void verifyTableConfigurationOfSKUTab() throws Exception {
		login("APP_ADMIN_USER", "APP_ADMIN_PASSWORD");
		LicensesListPage LicensesListPage = new LicensesListPage(PreDefinedActions.getDriver()).getInstance();
		String tenantID = getValueFromToken("tenant");
		gotoSubscriptionsTab();
		LicensesListPage.clickByJavaScriptOnSubscriptionsListPage("sKUsTab");
		LOGGER.info("Clicked SKUs tab");
		waitForPageLoaded();
		// verifying search service API
		Assert.assertTrue(getStatusCode(getSearchServiceApi(environment) + CommonVariables.DETAILSSEARCHSERVICEAPI + tenantID + CommonVariables.DETAILSSEARCHSERVICEAPI2, UserVariables.SEARCHSERVICEBODY, "POST", getEnvironment(environment)) == CommonVariables.CODEOK, "Search service failed for SKus License list page");
		ArrayList<String> columnName = new ArrayList<String>(Arrays.asList(getTextLanguage(LanguageCode, "daas_ui", "subscription.list.sku"), getTextLanguage(LanguageCode, "daas_ui", "subscription.list.term"), getTextLanguage(LanguageCode, "daas_ui", "subscription.list.plan"), getTextLanguage(LanguageCode, "daas_ui", "subscription.sku.list.must_start_in_days"),getTextLanguage(LanguageCode, "daas_ui", "catalog.updatedBy"),getTextLanguage(LanguageCode, "daas_ui", "contents.tableColumns.createdBy")));
		ArrayList<String> checkboxValue = new ArrayList<String>(Arrays.asList("true", "true", "true", "true", "false", "false"));
		ArrayList<String> name = new ArrayList<String>(Arrays.asList(getTextLanguage(LanguageCode, "daas_ui", "subscription.list.sku")));
		verifyTableConfigurationTests(columnName, checkboxValue, name);
	}

	/**
	 * This method will verify the table configuration test cases of License keys tab on License list page
	 * 
	 * @throws Exception
	 */
	@Test(priority = 5, groups = { "REGRESSION", "REGRESSION_CI", "STABILIZATION_STAGING" }, description = "TestCase 355288")
	public final void verifyTableConfigurationOfLicenseKeyAssignedTab() throws Exception {
		login("APP_ADMIN_USER", "APP_ADMIN_PASSWORD");
		LicensesListPage LicensesListPage = new LicensesListPage(PreDefinedActions.getDriver()).getInstance();
		String tenantID = getValueFromToken("tenant");
		gotoSubscriptionsTab();
		LicensesListPage.clickByJavaScriptOnSubscriptionsListPage("subscriptionKeyTab");
		LicensesListPage.waitForElementsOfSubscriptionsListPage("spinnerSKU");
		resetTableConfiguration();
		LOGGER.info("Clicked License keys tab");

		LicensesListPage.clickOnElementsOfSubscriptionsListPage("actionBar");
		LicensesListPage.clickOnElementsOfSubscriptionsListPage("assignedButton");
		LOGGER.info("Switched to Assigned section");
		
		// verifying search service API
		Assert.assertTrue(getStatusCode(getSearchServiceApi(environment) + CommonVariables.DETAILSSEARCHSERVICEAPI + tenantID + CommonVariables.DETAILSSEARCHSERVICEAPI2, UserVariables.SEARCHSERVICEBODY, "POST", getEnvironment(environment)) == CommonVariables.CODEOK, "Search service failed for sub keys License list page");
		ArrayList<String> columnName = new ArrayList<String>(Arrays.asList(getTextLanguage(LanguageCode, "daas_ui", "subscription.list.order_number"), getTextLanguage(LanguageCode, "daas_ui", "subscription.list.subscription_key"), getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.company_name"), getTextLanguage(LanguageCode, "daas_ui", "subscription.license.details.primary_contact_email"), getTextLanguage(LanguageCode, "daas_ui", "subscription.license.details.users"),
				getTextLanguage(LanguageCode, "daas_ui", "subscription.list.purchase_date"), getTextLanguage(LanguageCode, "daas_ui", "asset_details_start_date"), getTextLanguage(LanguageCode, "daas_ui", "subscription.license.details.state")));
		ArrayList<String> checkboxValue = new ArrayList<String>(Arrays.asList("true", "true", "true", "true", "true", "true", "false", "true"));
		ArrayList<String> name = new ArrayList<String>(Arrays.asList(getTextLanguage(LanguageCode, "daas_ui", "subscription.list.subscription_key")));
		verifyTableConfigurationTests(columnName, checkboxValue, name);
	}

	/**
	 * This method will verify SKU add and update
	 * 
	 * @throws Exception
	 */
	@Test(priority = 6, groups = { "REGRESSION", "REGRESSION_CI", "STABILIZATION_STAGING", "PENTEST" }, description = "TestCase 355287")
	public final void verifyAddUpdateSKU() throws Exception {
		login("APP_ADMIN_USER", "APP_ADMIN_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		LicensesListPage LicensesListPage = new LicensesListPage(PreDefinedActions.getDriver()).getInstance();
		gotoSubscriptionsTab();
		waitForPageLoaded();
		//resetTableConfiguration();
		LicensesListPage.clickByJavaScriptOnSubscriptionsListPage("sKUsTab");
		LOGGER.info("Click SKUS tab");

		String skuName = generateRandomString(5);

		LicensesListPage.clickOnElementsOfSubscriptionsListPage("skuAdd");
		waitForPageLoaded();
		Thread.sleep(2000);
		softAssert.assertTrue(LicensesListPage.getTextOfSubscriptionsListPage("skuModalHeader").equals(getTextLanguage(LanguageCode, "daas_ui", "subscription.form.title.sku")), "SKU header did not match");

		LicensesListPage.enterTextForSubscriptionsListPage("sku", skuName);
		LOGGER.info("Entered sku name");
		LicensesListPage.clickOnElementsOfSubscriptionsListPage("planList");
		LicensesListPage.enterTextForSubscriptionsListPage("planSearch", CommonVariables.PLAN_PREMIUM);
		sleeper(3000); // wait added to load plans from ES
		LicensesListPage.clickOnElementsOfSubscriptionsListPage("firstplanSelect");
		LOGGER.info("Select plan for SKU");
		sleeper(2000);
		LicensesListPage.clickOnElementsOfSubscriptionsListPage("term");
		LicensesListPage.clickOnElementsOfSubscriptionsListPage("firstTermList");
		LOGGER.info("Select term for SKU");

		LicensesListPage.clickOnElementsOfSubscriptionsListPage("mustStartIn");
		LicensesListPage.clickOnElementsOfSubscriptionsListPage("firstmustStartIn");
		LOGGER.info("Select mustStartIn for SKU");

		LicensesListPage.enterTextForSubscriptionsListPage("description", skuName);
		LOGGER.info("Entered description for SKU");

		LicensesListPage.clickOnElementsOfSubscriptionsListPage("skuAddModal");
		LOGGER.info("Save SKU form");
		Thread.sleep(3000);
		LicensesListPage.waitForElementsOfSubscriptionsListPage("toastNotification");
		String toastNotification = LicensesListPage.getTextOfSubscriptionsListPage("toastNotification");
		Assert.assertTrue(toastNotification.equals(getTextLanguage(LanguageCode, "daas_ui", "global.messages.field_add_success").replace("{field}", getTextLanguage(LanguageCode, "daas_ui", "subscription.list.sku"))), "SKU add failed");
		LicensesListPage.clickOnElementsOfSubscriptionsListPage("successNotificationClose");
		resetTableConfiguration();
		LicensesListPage.goToSKUDetailsPage(skuName);
		Thread.sleep(3000);
		softAssert.assertTrue(LicensesListPage.getTextOfSubscriptionsListPage("skuModalHeader").equals(getTextLanguage(LanguageCode, "daas_ui", "subscription.form.title.edit_sku")), "Edit SKU header did not match");
		softAssert.assertFalse(LicensesListPage.verifyElementIsEnabledOfSubscriptionsListPage("sku"), "SKU field is enabled in Edit SKU mode");
		softAssert.assertFalse(LicensesListPage.verifyElementIsEnabledOfSubscriptionsListPage("planID"), "plan List field is enabled in Edit SKU mode");
		softAssert.assertFalse(LicensesListPage.verifyElementIsEnabledOfSubscriptionsListPage("termEditModal"), "Term field is enabled in Edit SKU mode");
		softAssert.assertTrue(LicensesListPage.getAttributesOfSubscriptionsListPage("planID", "value").equalsIgnoreCase(CommonVariables.PLAN_PREMIUM), "plan on list and edit modal page did not match");
		LicensesListPage.enterTextForSubscriptionsListPage("description", generateRandomString(5));
		LicensesListPage.clickOnElementsOfSubscriptionsListPage("skuAddModal");
		LOGGER.info("Validated SKU in Edit mode");

		String skuToastNotification = LicensesListPage.getTextOfSubscriptionsListPage("toastNotification");
		Assert.assertTrue(skuToastNotification.equals(getTextLanguage(LanguageCode, "daas_ui", "global.messages.field_update_successful").replace("{field}", getTextLanguage(LanguageCode, "daas_ui", "subscription.list.sku"))), "SKU update failed");
		LicensesListPage.clickOnElementsOfSubscriptionsListPage("settingleftMenu");
		waitForPageLoaded();
		Thread.sleep(2000);
		softAssert.assertTrue(LicensesListPage.getTextOfSubscriptionsListPage("settingdetailstile").equals(getTextLanguage(LanguageCode, "daas_ui", "incident.eligibility.tile.title")), "Setting details header page title does not match");
		softAssert.assertAll();
		
	}

	/**
	 * This method will verify add License key
	 * 
	 * @throws Exception
	 */
	@Test(priority = 7, groups = { "REGRESSION", "REGRESSION_CI", "STABILIZATION_STAGING", "PENTEST" }, description = "TestCase 355288,1018997")
	public final void verifyAddLicenseKey() throws Exception {
		login("APP_ADMIN_USER", "APP_ADMIN_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		LicensesListPage LicensesListPage = new LicensesListPage(PreDefinedActions.getDriver()).getInstance();
		LicensesDetailsPage LicensesDetailsPage = new LicensesDetailsPage(PreDefinedActions.getDriver()).getInstance();
		gotoSubscriptionsTab();
//		resetTableConfiguration();
//		waitForPageLoaded();
		LicensesListPage.clickByJavaScriptOnSubscriptionsListPage("subscriptionKeyTab");
//		LOGGER.info("Click License tab");
//		softAssert.assertTrue(LicensesListPage.getTextOfSubscriptionsListPage("subscriptionKeyTabTitle").equals(getTextLanguage(LanguageCode, "daas_ui", "subscription.license.header.title")), "License tab title did not match");

		String subscriptionKeyName = generateRandomString(12).toUpperCase();

		LicensesListPage.clickOnElementsOfSubscriptionsListPage("addButton");
		LOGGER.info("Click Add licence button");
		softAssert.assertTrue(LicensesListPage.getTextOfSubscriptionsListPage("addModalTitle").equals(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.modal.subscription_title")), "License header did not match");
		softAssert.assertTrue(LicensesListPage.getTextOfSubscriptionsListPage("subTypeHeading").equals(getTextLanguage(LanguageCode, "daas_ui", "subscription.form.subscription_type")), "subTypeHeading did not match");

		LicensesListPage.enterTextForSubscriptionsListPage("orderId", subscriptionKeyName);
		LOGGER.info("Entered order number");
		LicensesListPage.enterTextForSubscriptionsListPage("subKey", subscriptionKeyName);
		LOGGER.info("Entered License key name");

		sleeper(3000); // wait for SKU api to load list and make sku drop down enable
		LicensesListPage.clickOnElementsOfSubscriptionsListPage("skulist");
		LicensesListPage.enterTextForSubscriptionsListPage("skuSearch", CommonVariables.SKU);
		sleeper(5000); // wait added to load plans from ES
		LicensesListPage.waitForPresenceOfElementsOfSubscriptionsListPage("firstSKU");
		LicensesListPage.clickOnElementsOfSubscriptionsListPage("firstSKU");
		LOGGER.info("Select SKU for license key");
		
		LicensesListPage.clearTextOnSubscriptionsListPage("numberOfSeats");
		LicensesListPage.enterTextForSubscriptionsListPage("numberOfSeats", "10");
		LOGGER.info("Entered number of seats for License key");

		LicensesListPage.clickOnElementsOfSubscriptionsListPage("nextButton");
		LOGGER.info("Click on next");

		LicensesListPage.enterTextForSubscriptionsListPage("partnerId", "10");
		LOGGER.info("Entered partner id for License key");

		LicensesListPage.clickOnElementsOfSubscriptionsListPage("subKeyAddButtonModal");
		LOGGER.info("Click save");
		Thread.sleep(2000);
		LicensesListPage.waitForElementsOfSubscriptionsListPage("toastNotification");
		String toastNotification = LicensesListPage.getTextOfSubscriptionsListPage("toastNotification");
		Assert.assertTrue(toastNotification.equals(getTextLanguage(LanguageCode, "daas_ui", "global.messages.field_add_success").replace("{field}", getTextLanguage(LanguageCode, "daas_ui", "subscription.section.licenseKey"))), "Licence key addition failed");
		LicensesListPage.waitForPresenceOfElementsOfSubscriptionsListPage("successNotificationClose");
		LicensesListPage.clickOnElementsOfSubscriptionsListPage("successNotificationClose");

		// Navigate to licence key detail page
		LicensesListPage.goToSubscriptionKeyDetailsPage(subscriptionKeyName);
		LicensesDetailsPage.waitForElementsOfLicensessDetailsPage("spinnerDetailsPage");
		Assert.assertTrue(LicensesDetailsPage.getTextOfLicensessDetailsPage("subscriptionNumber").equals(subscriptionKeyName), "License key details page failed to load");
		softAssert.assertTrue(LicensesDetailsPage.getTextOfLicensessDetailsPage("orderNumber").equals(subscriptionKeyName), "Order number did not match on details page");
		softAssert.assertTrue(LicensesDetailsPage.getTextOfLicensessDetailsPage("subscritionTitle").equals(getTextLanguage(LanguageCode, "daas_ui", "subscription.section.licenseKey")), "License details tab title did not match");
		softAssert.assertTrue(LicensesDetailsPage.verifyElementsOfLicensessDetailsPage("removeLicenseButton"), "Remove license button is not present");

		// Details tile
		softAssert.assertTrue(LicensesDetailsPage.verifyElementsOfLicensessDetailsPage("detailsTile"), "Details tile is not present");
		softAssert.assertTrue(LicensesDetailsPage.getTextOfLicensessDetailsPage("detailsTileTitle").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "subscription.form.subscription_type")), "details tile title did not match");
		LicensesDetailsPage.scrollOnLicensessDetailsPage("skuValue");
		softAssert.assertTrue(LicensesDetailsPage.getTextOfLicensessDetailsPage("skuValue").equalsIgnoreCase(CommonVariables.SKU), "SKU name did not match on details page");
		softAssert.assertTrue(LicensesDetailsPage.getTextOfLicensessDetailsPage("productValue").equalsIgnoreCase(CommonVariables.PLAN_ENHANCED), "Product name did not match on details page");

		// userCountTile
		softAssert.assertTrue(LicensesDetailsPage.verifyElementsOfLicensessDetailsPage("userCountTile"), "userCountTile is not present");
		softAssert.assertTrue(LicensesDetailsPage.getTextOfLicensessDetailsPage("userCountTileTitle").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "roles.list.user_count")), "Number of users tile title did not match");
		softAssert.assertTrue(LicensesDetailsPage.verifyElementsOfLicensessDetailsPage("editButtonUserCountTile"), "Edit button is missing for Number of users tile");
		softAssert.assertTrue(LicensesDetailsPage.getTextOfLicensessDetailsPage("noOfSeats").equals("10"), "No of seats did not match on details page");

		// Reduce no of seats
		LicensesDetailsPage.clickOnElementsOfLicensessDetailsPage("editButtonUserCountTile");
		LicensesListPage.clearTextOnSubscriptionsListPage("numberOfSeats");
		LicensesDetailsPage.enterTextForLicensessDetailsPage("noOfUserBox", "5");
		LicensesDetailsPage.clickOnElementsOfLicensessDetailsPage("saveButton");
		
		toastNotification = LicensesListPage.getTextOfSubscriptionsListPage("toastNotification");
		softAssert.assertTrue(toastNotification.equals(getTextLanguage(LanguageCode, "daas_ui", "global.update.success").replace("{name}", getTextLanguage(LanguageCode, "daas_ui", "subscription.sku.add.current_seats"))), "Current seat update failed");
		LicensesListPage.clickOnElementsOfSubscriptionsListPage("successNotificationClose");
		sleeper(3000); // Sleeper added wait for the API response
		softAssert.assertTrue(LicensesDetailsPage.getTextOfLicensessDetailsPage("noOfSeats").equals("5"), "Decrease in no of seats did not match on details page");

		// dateTimeTile
		LicensesDetailsPage.scrollOnLicensessDetailsPage("noOfSeats");
		softAssert.assertTrue(LicensesDetailsPage.verifyElementsOfLicensessDetailsPage("dateTimeTile"), "dateTimeTile is not present");
		softAssert.assertTrue(LicensesDetailsPage.getTextOfLicensessDetailsPage("dateTimeTileTitle").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "subscription.form.date_time_definitions")), "date and time tile title did not match");
		LicensesDetailsPage.waitForElementsOfLicensessDetailsPage("termValue");
		softAssert.assertTrue(LicensesDetailsPage.getTextOfLicensessDetailsPage("termValue").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "global.months").replace("{noOfMonths}", "6")), "term did not match on details page");
			softAssert.assertTrue(LicensesDetailsPage.getTextOfLicensessDetailsPage("expirationDateValue").equals("-"), "expiration Date did not match on details page");
		softAssert.assertTrue(LicensesDetailsPage.verifyElementsOfLicensessDetailsPage("purchaseDateValue"), "purchase DateValue is not present");
		softAssert.assertTrue(LicensesDetailsPage.verifyElementsOfLicensessDetailsPage("startDateValue"), "start Date Value is not present");
		softAssert.assertTrue(LicensesDetailsPage.verifyElementsOfLicensessDetailsPage("editButtonStartDate"), "start date Edit button is not present");

		// subscriptionDetailsTile
		softAssert.assertTrue(LicensesDetailsPage.verifyElementsOfLicensessDetailsPage("subscriptionDetailsTile"), "subscriptionDetailsTile is not present");
		softAssert.assertTrue(LicensesDetailsPage.getTextOfLicensessDetailsPage("subscriptionDetailsTileTitle").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "subscription.license.subscription_details")), "License details tile title did not match");
		softAssert.assertTrue(LicensesDetailsPage.getTextOfLicensessDetailsPage("statusValue").equals(getTextLanguage(LanguageCode, "daas_ui", "subscription.list.plan.new")), "Create License key status did not match");
			softAssert.assertTrue(LicensesDetailsPage.getTextOfLicensessDetailsPage("companyValue").equals("-"), "company value not black on details page");	
		softAssert.assertTrue(LicensesDetailsPage.getTextOfLicensessDetailsPage("primaryContactEmailValue").equals("-"), "primary Contact Email Value not blank on details page");
		// partnerDetailsTile
		softAssert.assertTrue(LicensesDetailsPage.verifyElementsOfLicensessDetailsPage("partnerDetailsTile"), "partnerDetailsTile is not present");
		softAssert.assertTrue(LicensesDetailsPage.getTextOfLicensessDetailsPage("partnerDetailsTileTitle").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "breadcrumbs.partner_details")), "Partner details tile title did not match");
		softAssert.assertTrue(LicensesDetailsPage.verifyElementsOfLicensessDetailsPage("editPartnerButton"), "partner Edit button is not present");

		// Remove License key
		LicensesDetailsPage.clickByJavaScriptOnLicensessDetailsPage("removeLicenseButton");
		LicensesDetailsPage.clickByJavaScriptOnLicensessDetailsPage("removeOkButton");
		LicensesDetailsPage.verifyElementsOfLicensessDetailsPage("toastNotification");
		softAssert.assertTrue(LicensesDetailsPage.getTextOfLicensessDetailsPage("statusValue").equals(getTextLanguage(LanguageCode, "daas_ui", "subscription.list.plan.deleted")), "License key delete failed");

		softAssert.assertAll();
	}
	
	/**
	 * This method will import CSV and check key is added or not
	 * 
	 * @throws Exception
	 */
	@Test(priority = 8, groups = { "REGRESSION", "REGRESSION_CI", "STABILIZATION_STAGING", "PENTEST" }, description = "TestCase 355288")
	public final void verifyImportCSVToAddLicenceKey() throws Exception {
		login("APP_ADMIN_USER", "APP_ADMIN_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		LicensesListPage LicensesListPage = new LicensesListPage(PreDefinedActions.getDriver()).getInstance();
		LicensesDetailsPage LicensesDetailsPage = new LicensesDetailsPage(PreDefinedActions.getDriver()).getInstance();
		gotoSubscriptionsTab();
		//resetTableConfiguration();
		waitForPageLoaded();
		LicensesListPage.clickByJavaScriptOnSubscriptionsListPage("subscriptionKeyTab");
		String subscriptionKeyName = generateRandomString(13).toUpperCase();
		LOGGER.info("Clicked License keys tab");

		LicensesListPage.clickOnElementsOfSubscriptionsListPage("importButton");
		softAssert.assertTrue(LicensesListPage.getTextOfSubscriptionsListPage("importModalHeader").equals(getTextLanguage(LanguageCode, "daas_ui", "subscription.license.import_list.title")), "Import modal header text did not match");
		softAssert.assertTrue(LicensesListPage.verifyElementsOfSubscriptionsListPage("sampleFileLink"), "Sample link is not present on import modal");
		LOGGER.info("Click import button");
		
		CSVFileReader csvFileReader = new CSVFileReader();
		File csvFile = new File(ConstantPath.IMPORT_PATH + CompaniesVariables.SUBSCRIPTION_CSV);
		String[] header = "licenseKey,skuId,numberOfSeats,partnerId,orderId,buffer,creationDate,startDate".split(",");
		String[] data = { subscriptionKeyName, CommonVariables.SKU, "100", "", "AutomationOrder", "", "", "" };
		csvFileReader.writeDataToCSVHavingMultipleColumn(csvFile, header, data);
		LicensesListPage.fileImportInV3(ConstantPath.IMPORT_PATH + CompaniesVariables.SUBSCRIPTION_CSV);
		sleeper(2000);
		LicensesListPage.waitForElementsOfSubscriptionsListPage("uploadButton");
		LicensesListPage.clickOnElementsOfSubscriptionsListPage("uploadButton");
		LOGGER.info("Upload file");
		LicensesListPage.waitUntillElementIsPresent("toastNotification");
		String toastNotification = LicensesListPage.getTextOfSubscriptionsListPage("toastNotification");
		Assert.assertTrue(toastNotification.equals(getTextLanguage(LanguageCode, "daas_ui", "assets.import.asset.upload_successful")), "File upload failed");
		sleeper(3000); // wait added till upload completes
		
		// Navigate to licence key detail page
		getUrl(getEnvironment(System.getProperty("environment")) + subscriptionURL + subscriptionKeyName);
		waitForPageLoaded();
		Assert.assertTrue(LicensesDetailsPage.getTextOfLicensessDetailsPage("subscriptionNumber").equals(subscriptionKeyName), "License key details page failed to load");
		LOGGER.info("Verified License key details page");
		
		softAssert.assertAll();
	}
	
	/**
	 * This test case will verify License admin user
	 * 
	 * @throws Exception
	 */
	@Test(priority = 9, groups = { "REGRESSION", "REGRESSION_CI", "STABILIZATION_STAGING" }, description = "TestCase 366570",enabled=false)
	public final void verifyLicenseAdminUser() throws Exception {
		login("SUBSCRIPTION_ADMIN_USER", "SUBSCRIPTION_ADMIN_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		LicensesListPage LicensesListPage = new LicensesListPage(PreDefinedActions.getDriver()).getInstance();

		softAssert.assertTrue(LicensesListPage.getTextOfSubscriptionsListPage("subscriptionsPageTitle").equals(getTextLanguage(LanguageCode, "daas_ui", "sidemenu.subscriptions")), "Subscriptions title text is incorrect");
		softAssert.assertFalse(LicensesListPage.verifyElementsOfSubscriptionsListPage("appUsersTab"), "Users tab visible for License admin");
		softAssert.assertAll();
	}
	
	/**
	 * This test case will verify license revoked
	 * 
	 * @throws Exception
	 */
	@Test(priority = 10, groups = { "REGRESSION", "REGRESSION_CI", "STABILIZATION_STAGING"}, description = "TestCase 355288")
	public final void verifyLicenseKeyRevoked() throws Exception {
			login("APP_ADMIN_USER", "APP_ADMIN_PASSWORD");
			SoftAssert softAssert = new SoftAssert();
			LicensesDetailsPage LicensesDetailsPage = new LicensesDetailsPage(PreDefinedActions.getDriver()).getInstance();
			CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
			LicensesListPage LicensesListPage = new LicensesListPage(PreDefinedActions.getDriver()).getInstance();
			String subKey = generateRandomString(13).toUpperCase();
		    String tenantID ="";
		try{
			// Create key
			Assert.assertTrue(LicensesListPage.createLicenceKey(getEnvironmentSpecificData(System.getProperty("environment"), "DAAS_API_BASE_URL_AUTH") + subscriptionRes, subKey, CommonVariables.SKU), "Licence key creation failed");
			LOGGER.info("Created licence key " + subKey);
			logout();
			waitForPageLoaded();
			// Create company and assign key to company
			login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
			LOGGER.info("Logged in with root user");
	//		Assert.assertTrue(companiesPage.createCompanyUsingAPI(subKey, getEnvironmentSpecificData(System.getProperty("environment"), "COUNTRY_CODE"), LanguageCode, getEnvironmentSpecificData(System.getProperty("environment"), "MSP_ID"), "", "", CompaniesVariables.COMPANY_STANDARDTRIAL), "Company Creation failed");
			//sleeper(3000);
			waitForPageLoaded();
			Assert.assertTrue(companiesPage.createCompanyUsingAPI(getEnvironmentSpecificData(System.getProperty("environment"), "ADD_COMPANY_THROUGH_ROOT_NAME"), getEnvironmentSpecificData(System.getProperty("environment"), "ADD_COMPANY_THROUGH_ROOT_COUNTRY_CODE"), LanguageCode, getEnvironmentSpecificData(System.getProperty("environment"), "ADD_COMPANY_THROUGH_ROOT_MSP_NAME_ID"), getEnvironmentSpecificData(System.getProperty("environment"), "ADD_COMPANY_THROUGH_ROOT_PARTNER_NAME_ID"),getEnvironmentSpecificData(System.getProperty("environment"), "ADD_COMPANY_THROUGH_ROOT_EMAIL"),CompaniesVariables.COMPANY_STANDARDTRIAL), "Company Creation failed");
			LOGGER.info(subKey + " License created");
			impersonateCompanyByCompanyName(getEnvironmentSpecificData(System.getProperty("environment"), "ADD_COMPANY_THROUGH_ROOT_NAME"));
			LOGGER.info("Redirected to company details page");
			tenantID = getUrlOfCurrentPage().substring(getUrlOfCurrentPage().length() - 36);
			Assert.assertTrue(getStatusCode(getEnvironmentSpecificData(System.getProperty("environment"), "DAAS_API_BASE_URL_AUTH") + subscriptionAssignURL + "/tenant/" + tenantID, "{\"licenseKey\":"+"\""+subKey+"\""+"}", "POST", System.getProperty("environment")) == 202, "License assignment failed");
			LOGGER.info("Assinged " + subKey + " key to company created");
			
			logout();

			 // Revoke added key
			login("APP_ADMIN_USER", "APP_ADMIN_PASSWORD");
			waitForPageLoaded();
			getUrl(getEnvironment(System.getProperty("environment")) + subscriptionURL + subKey);
			LicensesDetailsPage.waitForElementsOfLicensessDetailsPage("revoke");
			LicensesDetailsPage.clickOnElementsOfLicensessDetailsPage("revoke");
			LicensesDetailsPage.waitForElementsOfLicensessDetailsPage("removeOkButton");
			LicensesDetailsPage.clickOnElementsOfLicensessDetailsPage("removeOkButton");
			LicensesDetailsPage.verifyElementsOfLicensessDetailsPage("toastNotification");
			softAssert.assertTrue(LicensesDetailsPage.getTextOfLicensessDetailsPage("statusValue").equals(getTextLanguage(LanguageCode, "daas_ui", "subscription.list.plan.revoked")), "License key revoke failed");
			LOGGER.info("Key revoked");
			logout();
			
			login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
			LOGGER.info("Logged in with root user");
			
			waitForPageLoaded();
			softAssert.assertAll();
		} 
		finally {
			// Remove created company
			logout();
			login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
			waitForPageLoaded();
			Assert.assertTrue(companiesPage.removeCompanyUsingAPI(tenantID), "Company removal failed");
			LOGGER.info("Company removed successfully");
			logout();

		}
	}
	
	
	
	/*
	 * Verify planslist in Add-SKU dropdown
	 */
	@SuppressWarnings("unlikely-arg-type")
	@Test(priority = 11, groups = { "REGRESSION"}, description = "TestCase 624944")
	public final void verifyPlanDropdownValues() throws Exception {
		login("APP_ADMIN_USER", "APP_ADMIN_PASSWORD");
		
		SoftAssert softAssert = new SoftAssert();
		LicensesListPage LicensesListPage = new LicensesListPage(PreDefinedActions.getDriver()).getInstance();
		gotoSubscriptionsTab();
		waitForPageLoaded();
		//resetTableConfiguration();
		LicensesListPage.clickByJavaScriptOnSubscriptionsListPage("sKUsTab");
		LOGGER.info("Click SKUS tab");

		LicensesListPage.clickOnElementsOfSubscriptionsListPage("skuAdd");
		LOGGER.info("Click Add sku button");
		softAssert.assertTrue(LicensesListPage.getTextOfSubscriptionsListPage("skuModalHeader").equals(getTextLanguage(LanguageCode, "daas_ui", "subscription.form.title.sku")), "SKU header did not match");
		LicensesListPage.clickOnElementsOfSubscriptionsListPage("planList");
		
		ArrayList<String> arrPlansValues = new ArrayList<String>(
				Arrays.asList(
						(getTextLanguage(LanguageCode, "lhserver", "global.package_name.enhanced_package.short")), 
						(getTextLanguage(LanguageCode, "lhserver", "global.package_name.premium_package.short")),
						(getTextLanguage(LanguageCode, "lhserver", "global.package_name.standard_package.short"))
						)
				);
		Assert.assertTrue(LicensesListPage.comparePlansOfAddSKUPlansDropdown("planListValues",arrPlansValues), "All plans are not displayed in plans dropdown");
		sleeper(3000);
	}
	
	/**
	 * This test case will verify HPDex key revoked
	 * 
	 * @throws Exception
	 */
	@Test(priority = 12, groups = { "REGRESSIONCOMPANIES2" }, description = "TestCase[783799]", enabled = false)
	public final void verifyRevokeLicenseKeyForHPDex() throws Exception {
		login("APP_ADMIN_USER", "APP_ADMIN_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		LicensesDetailsPage LicensesDetailsPage = new LicensesDetailsPage(PreDefinedActions.getDriver()).getInstance();
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		LicensesListPage LicensesListPage = new LicensesListPage(PreDefinedActions.getDriver()).getInstance();
		String subKey = generateRandomString(12).toUpperCase();
		String tenantID = null;
		try {
		// Create key
		Assert.assertTrue(LicensesListPage.createLicenceKey(getEnvironmentSpecificData(System.getProperty("environment"), "DAAS_API_BASE_URL_AUTH") + subscriptionRes,subKey,CommonVariables.HPDEX_SKU),"Licence key creation failed");
		LOGGER.info("Created licence key " + subKey);
		logout();
		
		// Create company and assign key to company
		login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
		LOGGER.info("Logged in with root user");
		Assert.assertTrue(companiesPage.createCompanyUsingAPI(subKey,getEnvironmentSpecificData(System.getProperty("environment"), "COUNTRY_CODE"),LanguageCode,getEnvironmentSpecificData(System.getProperty("environment"), "MSP_ID"),"","", CompaniesVariables.COMPANY_STANDARDTRIAL),"Company Creation failed");
		LOGGER.info(subKey + " company created");
		impersonateCompanyByCompanyName(subKey);
		LOGGER.info("Redirected to company details page");
		tenantID = getUrlOfCurrentPage().substring(getUrlOfCurrentPage().length()-36);
		Assert.assertTrue(getStatusCode(getEnvironmentSpecificData(System.getProperty("environment"), "DAAS_API_BASE_URL_AUTH") + subscriptionAssignURL + subKey + "/tenant/" +tenantID ,"{}","POST",System.getProperty("environment")) == 202 ,"License assignment failed");
		LOGGER.info("Assinged " + subKey + " key to company created");
		logout();
		
		// Revoke added key
		login("APP_ADMIN_USER", "APP_ADMIN_PASSWORD");
		getUrl(getEnvironment(System.getProperty("environment")) + subscriptionURL + subKey);
		sleeper(7000);
		LicensesDetailsPage.clickOnElementsOfLicensessDetailsPage("revoke");
		LicensesDetailsPage.clickOnElementsOfLicensessDetailsPage("removeOkButton");
		LicensesDetailsPage.verifyElementsOfLicensessDetailsPage("toastNotification");
		softAssert.assertTrue(LicensesDetailsPage.getTextOfLicensessDetailsPage("statusValue").equals(getTextLanguage(LanguageCode, "daas_ui", "subscription.list.plan.revoked")), "License key revoke failed");
		LOGGER.info("Key revoked");
		}
		 finally {
		// Remove created company
			logout();
			login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
		companiesPage.removeCompanyUsingAPI(tenantID);
		softAssert.assertAll();
		 }
	}
	
	
	/**
	 * This method will verify invalid input value in add License key feild
	 * 
	 * @throws Exception
	 */
	 //Enabling it to false as we are now allowing license with small letters, so this testcase is not valid now
	@Test(priority = 7, groups = { "REGRESSION","STABILIZATION_STAGING",}, description = "TestCase 1018997",enabled = false)
	public final void verifyInvalidAddLicenseKeyValidations() throws Exception {
		login("APP_ADMIN_USER", "APP_ADMIN_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		LicensesListPage LicensesListPage = new LicensesListPage(PreDefinedActions.getDriver()).getInstance();
		gotoSubscriptionsTab();
		LicensesListPage.clickByJavaScriptOnSubscriptionsListPage("subscriptionKeyTab");
		LOGGER.info("Click License tab");
		String subscriptionKeyName = generateRandomString(10)+"123";

		LicensesListPage.clickOnElementsOfSubscriptionsListPage("addButton");
		LOGGER.info("Click Add licence button");
		softAssert.assertTrue(LicensesListPage.getTextOfSubscriptionsListPage("addModalTitle").equals(getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.modal.subscription_title")), "License header did not match");
		softAssert.assertTrue(LicensesListPage.getTextOfSubscriptionsListPage("subTypeHeading").equals(getTextLanguage(LanguageCode, "daas_ui", "subscription.form.subscription_type")), "subTypeHeading did not match");

		LicensesListPage.enterTextForSubscriptionsListPage("orderId", subscriptionKeyName);
		LOGGER.info("Entered order number");
		LicensesListPage.enterTextForSubscriptionsListPage("subKey", subscriptionKeyName);
		LOGGER.info("Entered License key name");

		sleeper(2000); // wait for SKU api to load list and make sku drop down enable
		LicensesListPage.clickOnElementsOfSubscriptionsListPage("skulist");
		LicensesListPage.enterTextForSubscriptionsListPage("skuSearch", CommonVariables.SKU);
		sleeper(3000); // wait added to load plans from ES
		LicensesListPage.waitForPresenceOfElementsOfSubscriptionsListPage("firstSKU");
		LicensesListPage.clickOnElementsOfSubscriptionsListPage("firstSKU");
		LOGGER.info("Select SKU for license key");
		
		LicensesListPage.clearTextOnSubscriptionsListPage("numberOfSeats");
		LicensesListPage.enterTextForSubscriptionsListPage("numberOfSeats", "10");
		LOGGER.info("Entered number of seats for License key");

		LicensesListPage.clickOnElementsOfSubscriptionsListPage("nextButton");
		LOGGER.info("Click on next");

		LicensesListPage.enterTextForSubscriptionsListPage("partnerId", "10");
		LOGGER.info("Entered partner id for License key");

		LicensesListPage.clickOnElementsOfSubscriptionsListPage("subKeyAddButtonModal");
		LOGGER.info("Click save");
		Thread.sleep(2000);
		LicensesListPage.waitForElementsOfSubscriptionsListPage("toastNotification");
		String toastNotification = LicensesListPage.getTextOfSubscriptionsListPage("toastNotification");
		Assert.assertTrue(toastNotification.equals(getTextLanguage(LanguageCode, "daas_ui", "global.messages.field_add_failed").replace("{field}", getTextLanguage(LanguageCode, "daas_ui", "onboarding.security.subscription_key"))), "Licence key added with invalid data.");
		LOGGER.info("Verification of invalid input for license key verified successfully.");
		softAssert.assertAll();
	}
}
