package com.testscripts.daasui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.basesource.action.PreDefinedActions;
import com.daasui.constants.CommonVariables;
import com.daasui.constants.ConstantPath;
import com.daasui.constants.DashboardVariables;
import com.daasui.constants.ProductCatalogVariables;
import com.daasui.pages.ProductCatalogPage;
import com.daasui.pages.TableConfigurationPage;

/**
 * ProductCatalogTest: General Import workFlow
 * 
 * Need a mechanism for a logged on customer (or partner on behalf of a customer) to import a catalog of devices to recommend from There has to be a way for Partners to import a
 * catalog and apply it to all customers that they are managing The catalog needs to follow a certain format like as CSV [Import File format Only in CSV ]
 * 
 * Feature 208768: [MPI][AUX][Analytics] Catalog Import
 */
public class ProductCatalogTest extends CommonTest {
	private static Logger LOGGER = LogManager.getLogger(ProductCatalogTest.class);
	SoftAssert sa = new SoftAssert();

	public int activePageNumber, firstPageNumber, lastPageNumber, selectedOption, totalCount;
	int notificationCountUnread = 0;
	public boolean previousButtonStatus, nextButtonStatus;
	public static String catalogAdditionCompany = getEnvironmentSpecificData(System.getProperty("environment"), "DEVICE_DETAILS_COMPANY");

	@Test(priority = 1, groups = { "REGRESSION", "REGRESSION_CI" })
	public final void verifyProductCatalogTitle() throws Exception {
		login("RESELLER_NEW_UI_Companies_EMAIL_COMPANIES", "RESELLER_NEW_UI_Companies_PASSWORD_COMPANIES");
		gotoProductCatalogTab();
		ProductCatalogPage productCatalogPage = new ProductCatalogPage(PreDefinedActions.getDriver()).getInstance();

		waitForPageLoaded();
		if (!toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, "RESELLER_NEW_UI_Companies_EMAIL_COMPANIES"))) {
			productCatalogPage.waitUntilElementsOfProductCatalogPage("productCatalogTitle");
			Assert.assertTrue(productCatalogPage.getTextOfProductCatalogPage("productCatalogTitle").equals(productCatalogPage.getTextLanguage(LanguageCode, "daas_ui", "catalogs.label")), "Product Catalog title text is incorrect when oggle is OFF");
		} else {
			productCatalogPage.waitUntilElementsOfProductCatalogPage("productCatalogTitleOnBreadcrumbs");
			Assert.assertTrue(productCatalogPage.getTextOfProductCatalogPage("productCatalogTitleOnBreadcrumbs").equals(productCatalogPage.getTextLanguage(LanguageCode, "daas_ui", "catalogs.label")), "Product Catalog title text is incorrect when toggle is ON");
		}
		LOGGER.info("Product Catalog title is matched with selected language");
	}

	@Test(priority = 2, groups = { "REGRESSION", "SMOKE", "REGRESSION_CI", "SMOKE_CI" })
	public final void verifyImportValidCatalog() throws Exception {
		login("RESELLER_NEW_UI_Companies_EMAIL_COMPANIES", "RESELLER_NEW_UI_Companies_PASSWORD_COMPANIES");
		gotoProductCatalogTab();
		ProductCatalogPage productCatalogPage = new ProductCatalogPage(PreDefinedActions.getDriver()).getInstance();

		waitForPageLoaded();
		productCatalogPage.clickOnElementsOfProductCatalogPage("addButton");
		sa.assertTrue(productCatalogPage.getTextOfProductCatalogPage("addTitle").equals(productCatalogPage.getTextLanguage(LanguageCode, "daas_ui", "catalog.import.modal.title")), "Product catalog import title does not match");
		sa.assertTrue(productCatalogPage.getTextLanguage(LanguageCode, "daas_ui", "catalog.import.modal.description").contains(productCatalogPage.getTextOfProductCatalogPage("addCatalogLabel")), "Product catalog import Description text does not match");
		productCatalogPage.verifyElementIsClickableOfProductCatalogPage("downloadCatalogSample");
		productCatalogPage.waitForElementsOfProductCatalogPage("dropDownOpen");
		waitForPageLoaded();
		productCatalogPage.clickOnElementsOfProductCatalogPage("dropDownOpen");
		LOGGER.info("Clicked on dropdown arrow");
		productCatalogPage.waitForElementsOfProductCatalogPage("companySearch");
		productCatalogPage.enterTextForProductCatalogPage("companySearch", catalogAdditionCompany);
		sleeper(5000);//to load the company name entered
		productCatalogPage.clickOnElementsOfProductCatalogPage("selectCompany");
			sleeper(3000);
			productCatalogPage.fileImportInV3(ConstantPath.IMPORT_PATH + ProductCatalogVariables.FILE_NAME_VALID);
		productCatalogPage.waitForElementsOfProductCatalogPage("importButton");
		sleeper(2000);//File upload fails in quick import button click.
		productCatalogPage.clickOnElementsOfProductCatalogPage("importButton");
		sleeper(5000);
		productCatalogPage.waitForElementsOfProductCatalogPage("toastNotification");
		sa.assertTrue(productCatalogPage.getTextOfProductCatalogPage("toastNotification").equals(productCatalogPage.getTextLanguage(LanguageCode, "daas_ui", "assets.import.asset.import_in_progress")), "Toast Notification text does not match");
		productCatalogPage.waitForElementsOfProductCatalogPage("tableOverlay");
		Assert.assertTrue(productCatalogPage.postNotificationCheckImportInV3(ProductCatalogVariables.FILE_NAME_VALID), "Products are not getting reflected back to the list page.");
		sa.assertTrue(productCatalogPage.verifyDescriptionOnLogsPage(), "Description on logs does not match");
		sa.assertAll();
		LOGGER.info("Validation of import valid catalog completed successfully.");

	}

	@Test(priority = 3, groups = { "REGRESSION", "SMOKE", "REGRESSION_CI", "SMOKE_CI" })
	public final void verifyImportInvalidCatalog() throws Exception {
		login("RESELLER_NEW_UI_Companies_EMAIL_COMPANIES", "RESELLER_NEW_UI_Companies_PASSWORD_COMPANIES");
		gotoProductCatalogTab();
		ProductCatalogPage productCatalogPage = new ProductCatalogPage(PreDefinedActions.getDriver()).getInstance();

		waitForPageLoaded();
		productCatalogPage.clickOnElementsOfProductCatalogPage("addButton");
		productCatalogPage.waitForElementsOfProductCatalogPage("dropDownOpen");
		productCatalogPage.clickOnElementsOfProductCatalogPage("dropDownOpen");
		LOGGER.info("Clicked on dropdown arrow");
		productCatalogPage.waitForElementsOfProductCatalogPage("companySearch");
		productCatalogPage.enterTextForProductCatalogPage("companySearch", catalogAdditionCompany);
		sleeper(5000);//to load the company name entered
		productCatalogPage.clickOnElementsOfProductCatalogPage("selectCompany");
			//productCatalogPage.clickOnElementsOfProductCatalogPage("browseButton");
			productCatalogPage.fileImportInV3(ConstantPath.IMPORT_PATH + ProductCatalogVariables.FILE_NAME_INVALID);
			sleeper(2000);
		productCatalogPage.waitForElementsOfProductCatalogPage("importButton");
		productCatalogPage.clickOnElementsOfProductCatalogPage("importButton");
		sleeper(5000);
		productCatalogPage.waitForElementsOfProductCatalogPage("toastNotification");
		sa.assertTrue(productCatalogPage.getTextOfProductCatalogPage("toastNotification").equals(productCatalogPage.getTextLanguage(LanguageCode, "daas_ui", "assets.import.asset.import_in_progress")), "Toast Notification text does not match");
		productCatalogPage.waitForElementsOfProductCatalogPage("tableOverlay");
		Assert.assertTrue(productCatalogPage.postNotificationCheckImportInV3(ProductCatalogVariables.FILE_NAME_INVALID), "Products are not getting reflected back to the list page.");
		sa.assertTrue(productCatalogPage.verifyDescriptionOnLogsPage(), "Description on logs does not match");
		sa.assertAll();
		LOGGER.info("Validation of import Invalid catalog completed successfully.");

	}

	@Test(priority = 4, groups = { "REGRESSION", "SMOKE", "REGRESSION_CI", "SMOKE_CI" })
	public final void verifyRemoveFunctionalityWithHamburgerOnListPage() throws Exception {
		SoftAssert sa = new SoftAssert();
		List<String> columnList = null;
		List<String> catalogNameList = null;
		login("RESELLER_NEW_UI_Companies_EMAIL_COMPANIES", "RESELLER_NEW_UI_Companies_PASSWORD_COMPANIES");
		gotoProductCatalogTab();
		ProductCatalogPage productCatalogPage = new ProductCatalogPage(PreDefinedActions.getDriver()).getInstance();

		waitForPageLoaded();
		TableConfigurationPage tableConfigurationPage = new TableConfigurationPage(PreDefinedActions.getDriver()).getInstance();

		productCatalogPage.waitForElementsOfProductCatalogPage("tableConfigurationButton");
		resetTableConfiguration();
		productCatalogPage.clickOnElementsOfProductCatalogPage("tableConfigurationButton");
		sa.assertTrue(tableConfigurationPage.verifyElementsOfTableConfigurationPage("tableConfigurationTitle"), "Table configuration pop-up does not open after clicking on table Configuration Button");
		tableConfigurationPage.clickOnElementsOfTableConfigurationPage("saveButton");
		waitForPageLoaded();
		productCatalogPage.waitForElementsOfProductCatalogPage("addButton");
		productCatalogPage.clickOnElementsOfProductCatalogPage("addButton");
		productCatalogPage.waitForElementsOfProductCatalogPage("dropDownOpen");
		waitForPageLoaded();
		productCatalogPage.clickOnElementsOfProductCatalogPage("dropDownOpen");
		LOGGER.info("Clicked on dropdown arrow");
		columnList = productCatalogPage.getTextOfListOfProductCatalogPage("companyColumnListOnPopup");
		productCatalogPage.clickOnElementsOfProductCatalogPage("dropdownClose");
		productCatalogPage.waitForElementsOfProductCatalogPage("cancelButtonOnAddPopup");
		productCatalogPage.clickOnElementsOfProductCatalogPage("cancelButtonOnAddPopup");

		sa.assertTrue(productCatalogPage.verifyImportValidCSVRemoveFunctionalityOnProductListPage(LanguageCode, columnList), "Does not working import function properly !!!");
		catalogNameList = productCatalogPage.getTextOfListOfProductCatalogPage("catalogNameSearchList");
		sa.assertTrue(productCatalogPage.verifyRemoveFunctionalityWithHamburgerSelectionOnListPage(LanguageCode, catalogNameList), "Does not working remove function with hamburger on list page !!!");
		sa.assertAll();
		LOGGER.info("All remove functionality test-cases passed on Product Catalog list page");
	}

	@Test(priority = 5, groups = { "REGRESSION", "SMOKE", "REGRESSION_CI", "SMOKE_CI" })
	public final void verifyRemoveFunctionalityWithHamburgerOnDetailsPage() throws Exception {
		SoftAssert sa = new SoftAssert();
		List<String> columnList = null;
		List<String> catalogNameList = null;
		login("RESELLER_NEW_UI_Companies_EMAIL_COMPANIES", "RESELLER_NEW_UI_Companies_PASSWORD_COMPANIES");
		gotoProductCatalogTab();
		ProductCatalogPage productCatalogPage = new ProductCatalogPage(PreDefinedActions.getDriver()).getInstance();

		waitForPageLoaded();
		TableConfigurationPage tableConfigurationPage = new TableConfigurationPage(PreDefinedActions.getDriver()).getInstance();

		productCatalogPage.waitForElementsOfProductCatalogPage("tableConfigurationButton");
		resetTableConfiguration();
		productCatalogPage.clickOnElementsOfProductCatalogPage("tableConfigurationButton");
		sa.assertTrue(tableConfigurationPage.verifyElementsOfTableConfigurationPage("tableConfigurationTitle"), "Table configuration pop-up does not open after clicking on table Configuration Button");
		tableConfigurationPage.clickOnElementsOfTableConfigurationPage("saveButton");
		waitForPageLoaded();
		productCatalogPage.waitForElementsOfProductCatalogPage("addButton");
		productCatalogPage.clickOnElementsOfProductCatalogPage("addButton");
		productCatalogPage.waitForElementsOfProductCatalogPage("dropDownOpen");
		waitForPageLoaded();
		productCatalogPage.clickOnElementsOfProductCatalogPage("dropDownOpen");
		LOGGER.info("Clicked on dropdown arrow");
		columnList = productCatalogPage.getTextOfListOfProductCatalogPage("companyColumnListOnPopup");
		productCatalogPage.clickOnElementsOfProductCatalogPage("dropdownClose");
		productCatalogPage.waitForElementsOfProductCatalogPage("cancelButtonOnAddPopup");
		productCatalogPage.clickOnElementsOfProductCatalogPage("cancelButtonOnAddPopup");

		sa.assertTrue(productCatalogPage.verifyImportValidCSVRemoveFunctionalityOnProductListPage(LanguageCode, columnList), "Does not working import function properly !!!");
		catalogNameList = productCatalogPage.getTextOfListOfProductCatalogPage("catalogNameSearchList");
		//Vineer 3 changes for Hamburger pending due to bug 609547
		sa.assertTrue(productCatalogPage.verifyRemoveFunctionalityWithHamburgerSelectionOnDetailsPage(LanguageCode, catalogNameList), "Does not working remove function with hamburger on Details page !!!");
		sa.assertAll();
		LOGGER.info("All remove functionality test-cases passed on Product Catalog  Details page");
	}

	@Test(priority = 6, groups = { "REGRESSION", "SMOKE", "REGRESSION_CI", "SMOKE_CI" })
	public final void verifyRemoveFunctionalityWithCheckboxOnListPage() throws Exception {
		SoftAssert sa = new SoftAssert();
		List<String> columnList = null;
		List<String> catalogNameList = null;
		login("RESELLER_NEW_UI_Companies_EMAIL_COMPANIES", "RESELLER_NEW_UI_Companies_PASSWORD_COMPANIES");
		gotoProductCatalogTab();
		ProductCatalogPage productCatalogPage = new ProductCatalogPage(PreDefinedActions.getDriver()).getInstance();
		waitForPageLoaded();
		TableConfigurationPage tableConfigurationPage = new TableConfigurationPage(PreDefinedActions.getDriver()).getInstance();
		productCatalogPage.waitForElementsOfProductCatalogPage("tableConfigurationButton");
		resetTableConfiguration();
		productCatalogPage.clickOnElementsOfProductCatalogPage("tableConfigurationButton");
		sa.assertTrue(tableConfigurationPage.verifyElementsOfTableConfigurationPage("tableConfigurationTitle"), "Table configuration pop-up does not open after clicking on table Configuration Button");
		tableConfigurationPage.clickOnElementsOfTableConfigurationPage("saveButton");
		waitForPageLoaded();
		productCatalogPage.waitForElementsOfProductCatalogPage("addButton");
		productCatalogPage.clickOnElementsOfProductCatalogPage("addButton");
		productCatalogPage.waitForElementsOfProductCatalogPage("dropDownOpen");
		waitForPageLoaded();
		productCatalogPage.clickOnElementsOfProductCatalogPage("dropDownOpen");
		LOGGER.info("Clicked on dropdown arrow");
		productCatalogPage.waitForElementsOfProductCatalogPage("companyColumnListOnPopup");
		columnList = productCatalogPage.getTextOfListOfProductCatalogPage("companyColumnListOnPopup");
			productCatalogPage.clickOnElementsOfProductCatalogPage("dropdownClose");
			sleeper(2000);
		productCatalogPage.waitForElementsOfProductCatalogPage("cancelButtonOnAddPopup");
		productCatalogPage.clickOnElementsOfProductCatalogPage("cancelButtonOnAddPopup");
		catalogNameList = productCatalogPage.getTextOfListOfProductCatalogPage("catalogNameSearchList");
		sa.assertTrue(productCatalogPage.verifyImportValidCSVRemoveFunctionalityOnProductListPage(LanguageCode, columnList), "Does not working import function properly !!!");
		sa.assertTrue(productCatalogPage.verifyRemoveFunctionalityWithCheckboxSelection(LanguageCode, catalogNameList), "Does not working remove function with single or multiple checkbox selection !!!");
		sa.assertTrue(productCatalogPage.verifyImportValidCSVRemoveFunctionalityOnProductListPage(LanguageCode, columnList), "Does not working import function properly !!!");
		sa.assertAll();
		LOGGER.info("All remove functionality test-cases passed on Product Catalog list and  Details page");
	}

	@Test(priority = 7, groups = { "REGRESSION", "SMOKE", "REGRESSION_CI", "SMOKE_CI" })
	public final void verifyFilterFunctionalityOnListPage() throws Exception {
		SoftAssert sa = new SoftAssert();
		login("RESELLER_NEW_UI_Companies_EMAIL_COMPANIES", "RESELLER_NEW_UI_Companies_PASSWORD_COMPANIES");
		gotoProductCatalogTab();
		ProductCatalogPage productCatalogPage = new ProductCatalogPage(PreDefinedActions.getDriver()).getInstance();

		waitForPageLoaded();
		TableConfigurationPage tableConfigurationPage = new TableConfigurationPage(PreDefinedActions.getDriver()).getInstance();

		productCatalogPage.waitForElementsOfProductCatalogPage("tableConfigurationButton");
		resetTableConfiguration();
		productCatalogPage.clickOnElementsOfProductCatalogPage("tableConfigurationButton");
		sa.assertTrue(tableConfigurationPage.verifyElementsOfTableConfigurationPage("tableConfigurationTitle"), "Table configuration pop-up does not open after clicking on table Configuration Button");
		tableConfigurationPage.selectAllCheckboxOfPopup("columnCheckboxesOnPopup", "listOfColumnsOnPopup");
		tableConfigurationPage.clickOnElementsOfTableConfigurationPage("saveButton");

		// Test Case 1 - This test case validates if the filter functionality is working properly for the searchbox on Name column
		sa.assertTrue(productCatalogPage.verifySearchValueOnProductCatalogPage(LanguageCode, "catalogNameSearchBox", ProductCatalogVariables.PRODUCT_CATALOG_LIST_PAGE_FAKE_NAME_SEARCH, "noProductCatalogDisplayText", "catalogNameSearchList"), "Search not working for Name field");
		sa.assertTrue(productCatalogPage.verifySearchValueOnProductCatalogPage(LanguageCode, "catalogNameSearchBox", ProductCatalogVariables.PRODUCT_CATALOG_LIST_PAGE_NAME, "noProductCatalogDisplayText", "catalogNameSearchList"), "Search not working for Name field");

		// Test Case 2 - This test case validates if the filter functionality is working properly for the dropdown on type column
		productCatalogPage.waitForElementsOfProductCatalogPage("catalogTypeBox");
		sleeper(2000);
		productCatalogPage.clickOnElementsOfProductCatalogPage("catalogTypeBox");
		productCatalogPage.waitForElementsOfProductCatalogPage("dropdownTypeCheckboxes");
		sa.assertTrue(productCatalogPage.verifyFilterSingleSelectOnProductCatalogPage(LanguageCode, "dropdownTypeCheckboxes", "dropdownTypeElementListLabels", "catalogTypeList", "noProductCatalogDisplayText"), "Listbox not working for Type dropdown");
		productCatalogPage.clickOnElementsOfProductCatalogPage("clearAllFilterField");
		productCatalogPage.waitForElementsOfProductCatalogPage("catalogTypeBox");
		productCatalogPage.clickOnElementsOfProductCatalogPage("catalogTypeBox");
		productCatalogPage.waitForElementsOfProductCatalogPage("dropdownTypeCheckboxes");
		sa.assertTrue(productCatalogPage.verifyFilterMultiSelectOnProductCatalogPage(LanguageCode, "dropdownTypeCheckboxes", "dropdownTypeElementListLabels", "catalogTypeList", "noProductCatalogDisplayText"), "Listbox not working for Type dropdown");
		productCatalogPage.clickOnElementsOfProductCatalogPage("clearAllFilterField");
		// Test Case 3 - This test case validates if the filter functionality is working properly for the dropdown on company column
//		productCatalogPage.waitForElementsOfProductCatalogPage("catalogCompanyBox");
//		productCatalogPage.clickOnElementsOfProductCatalogPage("catalogCompanyBox");
//		productCatalogPage.waitForElementsOfProductCatalogPage("dropdownCompanyCheckboxes");
//		sa.assertTrue(productCatalogPage.verifySearchValueOnProductCatalogCompanyListInsidePopup(LanguageCode, "catalogCompanySearchBox", ProductCatalogVariables.PRODUCT_CATALOG_LIST_PAGE_FAKE_NAME_SEARCH, "noElementsDisplayTextForComboBoxColumn", "dropdownCompanyCheckboxes"), "Searchbox is not working for company column");
//		sa.assertTrue(productCatalogPage.verifyProductCatalogCompanyFilterFunctionalityForSingleSelect(LanguageCode, "catalogCompanySearchBox", ProductCatalogVariables.PRODUCT_CATALOG_LIST_PAGE_COMPANY, "noElementsDisplayTextForComboBoxColumn", "dropdownCompanyListOnPopup", "dropdownCompanyCheckboxes", "catalogCompanyList", "noProductCatalogDisplayText"), "Listbox not working for company");
//		productCatalogPage.clickOnElementsOfProductCatalogPage("clearCompanyField");
//		productCatalogPage.waitForElementsOfProductCatalogPage("catalogCompanyBox");
//		sleeper(2000);
//		productCatalogPage.clickOnElementsOfProductCatalogPage("catalogCompanyBox");
//		productCatalogPage.waitForElementsOfProductCatalogPage("dropdownCompanyCheckboxes");
//		sa.assertTrue(productCatalogPage.verifyProductCatalogCompanyFilterFunctionalityForMultiSelect(LanguageCode, "catalogCompanySearchBox", ProductCatalogVariables.PRODUCT_CATALOG_LIST_PAGE_COMPANY_NAME_SEARCH, "noElementsDisplayTextForComboBoxColumn", "dropdownCompanyListOnPopup", "dropdownCompanyCheckboxes", "catalogCompanyList", "noProductCatalogDisplayText"), "Listbox is not working for company column");
//		productCatalogPage.clickOnElementsOfProductCatalogPage("clearCompanyField");

		// Test Case 4 - This test case validates if the filter functionality is working properly for the searchbox on Number of Products column
//		sa.assertTrue(productCatalogPage.verifySearchValueOnProductCatalogPage(LanguageCode, "catalogNumberOfProductsSearchBox", ProductCatalogVariables.PRODUCT_CATALOG_LIST_PAGE_FAKE_NUMBER_SEARCH, "noProductCatalogDisplayText", "catalogNumberOfProductsList"), "Search not working for Number of Products field");
//		sa.assertTrue(productCatalogPage.verifySearchValueOnProductCatalogPage(LanguageCode, "catalogNumberOfProductsSearchBox", ProductCatalogVariables.PRODUCT_CATALOG_LIST_PAGE_NUMBER, "noProductCatalogDisplayText", "catalogNumberOfProductsList"), "Search not working for Number of Products field");

		// Test Case 5 - This test case validates if the filter functionality is working properly for the dropdown on UpdatedOn column
		productCatalogPage.waitForElementsOfProductCatalogPage("catalogUpdatedOnBox");
		productCatalogPage.clickOnElementsOfProductCatalogPage("catalogUpdatedOnBox");
		productCatalogPage.waitForElementsOfProductCatalogPage("dropdownUpdatedCheckboxes");
		sa.assertTrue(productCatalogPage.selectLastOneWeekRangeOnProductCatalogPage(LanguageCode, "catalogUpdatedOnBox", "monthYearLeft", "monthYearRight", "prvArrow", "totalDaysLeftSide", "totalDaysRightSide", "noProductCatalogDisplayText", "catalogUpdatedOnList"), "Filter functionality for Updated on column is not working ");
		//Pending for Vineer 3 bug 609577
		productCatalogPage.clickOnElementsOfProductCatalogPage("clearAllFilterField");

		// Test Case 6 - This test case validates if the filter functionality is working properly for the dropdown on CreatedBy column
		// productCatalogPage.scrollOnProductCatalogPage("catalogCreatedByList");
		productCatalogPage.waitForElementsOfProductCatalogPage("catalogCreatedByBox");
		productCatalogPage.clickOnElementsOfProductCatalogPage("catalogCreatedByBox");
		productCatalogPage.waitForElementsOfProductCatalogPage("dropdownCreatedByCheckboxes");
		sa.assertTrue(productCatalogPage.verifyFilterSingleSelectOnProductCatalogPage(LanguageCode, "dropdownCreatedByCheckboxes", "dropdownCreatedByElementListLabels", "catalogCreatedByList", "noProductCatalogDisplayText"), "Listbox not working for CreatedBy dropdown");
		productCatalogPage.clickOnElementsOfProductCatalogPage("clearAllFilterField");
		productCatalogPage.waitForElementsOfProductCatalogPage("catalogCreatedByBox");
		productCatalogPage.clickOnElementsOfProductCatalogPage("catalogCreatedByBox");
		productCatalogPage.waitForElementsOfProductCatalogPage("dropdownCreatedByCheckboxes");
		sa.assertTrue(productCatalogPage.verifyFilterMultiSelectOnProductCatalogPage(LanguageCode, "dropdownCreatedByCheckboxes", "dropdownCreatedByElementListLabels", "catalogCreatedByList", "noProductCatalogDisplayText"), "Listbox not working for CreatedBy dropdown");
		if(verifyElementIsPresent("clearAllFilterField")){
			productCatalogPage.clickOnElementsOfProductCatalogPage("clearAllFilterField"); 
		}
//
//		// Test Case 7 - This test case validates if the filter functionality is working properly for the dropdown on UpdatedBy column
//		productCatalogPage.scrollOnProductCatalogPage("catalogUpdatedByList");
//		productCatalogPage.waitForElementsOfProductCatalogPage("catalogUpdatedByBox");
//		productCatalogPage.clickOnElementsOfProductCatalogPage("catalogUpdatedByBox");
//		productCatalogPage.waitForElementsOfProductCatalogPage("dropdownUpdatedByCheckboxes");
//		sleeper(3000);
//		sa.assertTrue(productCatalogPage.verifyFilterSingleSelectOnProductCatalogPage(LanguageCode, "dropdownUpdatedByCheckboxes", "dropdownUpdatedByElementListLabels", "catalogUpdatedByList", "noProductCatalogDisplayText"), "Listbox not working for UpdatedBy dropdown");
//		productCatalogPage.clickOnElementsOfProductCatalogPage("clearUpdatedByField");
//		productCatalogPage.waitForElementsOfProductCatalogPage("catalogUpdatedByBox");
//		productCatalogPage.clickOnElementsOfProductCatalogPage("catalogUpdatedByBox");
//		productCatalogPage.waitForElementsOfProductCatalogPage("dropdownUpdatedByCheckboxes");
//		sa.assertTrue(productCatalogPage.verifyFilterMultiSelectOnProductCatalogPage(LanguageCode, "dropdownUpdatedByCheckboxes", "dropdownUpdatedByElementListLabels", "catalogUpdatedByList", "noProductCatalogDisplayText"), "Listbox not working for UpdatedBy dropdown");
//		productCatalogPage.clickOnElementsOfProductCatalogPage("clearUpdatedByField");
//
//		// Test Case 8 - This test case validates if the filter functionality is working properly for the dropdown on Status column
//		 productCatalogPage.scrollOnProductCatalogPage("catalogStatusCatalogActiveList");
//		productCatalogPage.waitForElementsOfProductCatalogPage("catalogStatusCatalogActiveBox");
//		productCatalogPage.clickOnElementsOfProductCatalogPage("catalogStatusCatalogActiveBox");
//		//sleeper(3000);
//		productCatalogPage.waitForElementsOfProductCatalogPage("dropdownStatusCheckboxes");
//		sleeper(3000);
//		sa.assertTrue(productCatalogPage.verifyFilterSingleSelectOnProductCatalogPage(LanguageCode, "dropdownStatusCheckboxes", "dropdownStatusElementListLabels", "catalogStatusCatalogActiveList", "noProductCatalogDisplayText"), "Listbox not working for Status dropdown");
//		productCatalogPage.clickOnElementsOfProductCatalogPage("clearStatusField");
//		productCatalogPage.waitForElementsOfProductCatalogPage("catalogStatusCatalogActiveBox");
//		productCatalogPage.clickOnElementsOfProductCatalogPage("catalogStatusCatalogActiveBox");
//		productCatalogPage.waitForElementsOfProductCatalogPage("dropdownStatusCheckboxes");
//		sa.assertTrue(productCatalogPage.verifyFilterMultiSelectOnProductCatalogPage(LanguageCode, "dropdownStatusCheckboxes", "dropdownStatusElementListLabels", "catalogStatusCatalogActiveList", "noProductCatalogDisplayText"), "Listbox not working for Status dropdown");
//		productCatalogPage.clickOnElementsOfProductCatalogPage("clearUpdatedByField");
		sa.assertAll();
		LOGGER.info("All filter functionality test-cases passed on Product Catalog page");
	}

	@Test(priority = 8, groups = { "REGRESSION", "SMOKE", "REGRESSION_CI", "SMOKE_CI" }, enabled = false)
	public final void verifyPaginationOnProductCatalogListPage() throws Exception {
		SoftAssert sa = new SoftAssert();
		login("RESELLER_NEW_UI_Companies_EMAIL_COMPANIES", "RESELLER_NEW_UI_Companies_PASSWORD_COMPANIES");
		gotoProductCatalogTab();
		ProductCatalogPage productCatalogPage = new ProductCatalogPage(PreDefinedActions.getDriver()).getInstance();

		waitForPageLoaded();
		TableConfigurationPage tableConfigurationPage = new TableConfigurationPage(PreDefinedActions.getDriver()).getInstance();

		productCatalogPage.waitForElementsOfProductCatalogPage("tableConfigurationButton");
		resetTableConfiguration();
		productCatalogPage.clickOnElementsOfProductCatalogPage("tableConfigurationButton");
		sa.assertTrue(tableConfigurationPage.verifyElementsOfTableConfigurationPage("tableConfigurationTitle"), "Table configuration pop-up does not open after clicking on table configuration button");
		tableConfigurationPage.selectAllCheckboxOfPopup("columnCheckboxesOnPopup", "listOfColumnsOnPopup");
		tableConfigurationPage.clickOnElementsOfTableConfigurationPage("saveButton");

		productCatalogPage.waitForElementsOfProductCatalogPage("tableOverlay");
		productCatalogPage.waitForElementsOfProductCatalogPage("paginationDropdownMenu");
		sa.assertTrue(productCatalogPage.verifyElementIsEnableOfProductCatalogPage("paginationDropdownMenu"), "Pagination Dropdown not available");
		sa.assertTrue(productCatalogPage.verifyElementIsClickableOfProductCatalogPage("paginationDropdownMenu"), "Pagination Dropdown not clickable");
		sa.assertTrue(productCatalogPage.waitForElementsOfProductCatalogPage("navigationItemDiv"), "Navigation items are not available");
		getPaginationInfo();
		LOGGER.info("get Pagination Information ");
		sa.assertTrue(productCatalogPage.verifyElementIsEnableOfProductCatalogPage("navigationItemActivePage"), "The active page navigation link is not disabled");
		sa.assertTrue(productCatalogPage.verifyTotalPageCountChangeAsSelectedOption(selectedOption, totalCount, lastPageNumber), "Total page count does not change with selected option");
		if (!verifySelectedOptionForNewSelection(selectedOption, CommonVariables.SELETEDFIFTY)) {
			if (changeSelectedOption(totalCount, CommonVariables.SELETEDTWENTYFIVE)) {
				productCatalogPage.selectElementFromDropDownOfProductCatalogPage("paginationDropdownMenu", "paginationOptionList", Integer.toString(CommonVariables.SELETEDTWENTYFIVE));
				LOGGER.info("Change selected option as " + CommonVariables.SELETEDTWENTYFIVE);
				productCatalogPage.waitForElementsOfProductCatalogPage("tableOverlay");
				getPaginationInfo();
				sa.assertTrue(productCatalogPage.verifyTotalPageCountChangeAsSelectedOption(selectedOption, totalCount, lastPageNumber), "Total page count does not change with selected option");
				sa.assertTrue(productCatalogPage.verifyButtonStatusofPagination(activePageNumber, firstPageNumber, lastPageNumber, totalCount, previousButtonStatus, nextButtonStatus), "Button visibility status is not as per expectation");
			} else {

				LOGGER.info("Selected user has less than " + CommonVariables.SELETEDTWENTYFIVE + " records");
			}
		} else {
			if (changeSelectedOption(totalCount, CommonVariables.SELETEDFIFTY)) {
				productCatalogPage.selectElementFromDropDownOfProductCatalogPage("paginationDropdownMenu", "paginationOptionList", Integer.toString(CommonVariables.SELETEDFIFTY));
				LOGGER.info("Change Selected option as " + CommonVariables.SELETEDFIFTY);
				productCatalogPage.waitForElementsOfProductCatalogPage("tableOverlay");
				getPaginationInfo();
				sa.assertTrue(productCatalogPage.verifyTotalPageCountChangeAsSelectedOption(selectedOption, totalCount, lastPageNumber), "Total page count does not change with selected option");
				sa.assertTrue(productCatalogPage.verifyButtonStatusofPagination(activePageNumber, firstPageNumber, lastPageNumber, totalCount, previousButtonStatus, nextButtonStatus), "Button visibility status is not as per expectation");
			} else {

				LOGGER.info("Selected user has less than " + CommonVariables.SELETEDFIFTY + " records");
			}
		}
		productCatalogPage.waitForElementsOfProductCatalogPage("tableOverlay");
		if (productCatalogPage.canClickNext(activePageNumber, lastPageNumber, nextButtonStatus)) {
			sa.assertTrue(productCatalogPage.getButtonEnabilityStatus("navigationItemNext"), "Navigation next button is not enable");
			productCatalogPage.waitForElementsOfProductCatalogPage("navigationItemNext");
			productCatalogPage.clickOnElementsOfProductCatalogPage("navigationItemNext");
			productCatalogPage.waitForElementsOfProductCatalogPage("tableOverlay");
			getPaginationInfo();
			productCatalogPage.waitForElementsOfProductCatalogPage("navigationItemPrevious");
			if (productCatalogPage.canClickPrevious(activePageNumber, firstPageNumber, previousButtonStatus)) {
				sa.assertTrue(productCatalogPage.getButtonEnabilityStatus("navigationItemPrevious"), "Navigation previous button is not enabled");
				productCatalogPage.clickOnElementsOfProductCatalogPage("navigationItemPrevious");
			} else {
				LOGGER.info("Previous button is disabled");
			}
		} else if (productCatalogPage.canClickPrevious(activePageNumber, firstPageNumber, previousButtonStatus)) {
			sa.assertTrue(productCatalogPage.getButtonEnabilityStatus("navigationItemPrevious"), "Navigation previous button is not enabled");
			productCatalogPage.waitForElementsOfProductCatalogPage("navigationItemPrevious");
			productCatalogPage.clickOnElementsOfProductCatalogPage("navigationItemPrevious");
			productCatalogPage.waitForElementsOfProductCatalogPage("tableOverlay");
			getPaginationInfo();
			productCatalogPage.waitForElementsOfProductCatalogPage("navigationItemNext");
			if (productCatalogPage.canClickNext(activePageNumber, lastPageNumber, nextButtonStatus)) {
				sa.assertTrue(productCatalogPage.getButtonEnabilityStatus("navigationItemNext"), "Navigation next button is not enabled");
				productCatalogPage.clickOnElementsOfProductCatalogPage("navigationItemNext");
			} else {
				LOGGER.info("Next button is disabled");
			}
		} else {
			LOGGER.info("Previous and Next button both are disabled on Product Catalog Page ");
		}
	}

	@Test(priority = 9, groups = { "REGRESSION", "SMOKE", "REGRESSION_CI", "SMOKE_CI" })
	public final void verifyFilterFunctionalityOnDetailsPage() throws Exception {
		SoftAssert sa = new SoftAssert();
		login("RESELLER_NEW_UI_Companies_EMAIL_COMPANIES", "RESELLER_NEW_UI_Companies_PASSWORD_COMPANIES");
		gotoProductCatalogTab();
		ProductCatalogPage productCatalogPage = new ProductCatalogPage(PreDefinedActions.getDriver()).getInstance();

		waitForPageLoaded();

		List<WebElement> catalogList = productCatalogPage.getElementsOfProductCatalogPage("catalogNameSearchList");
		if(productCatalogPage.navigateToProductCatalogDetailsPage(catalogList)) {
		productCatalogPage.waitForElementsOfProductCatalogPage("tableOverlay");

		TableConfigurationPage tableConfigurationPage = new TableConfigurationPage(PreDefinedActions.getDriver()).getInstance();

		productCatalogPage.waitForElementsOfProductCatalogPage("tableConfigurationButton");
		resetTableConfiguration();
		productCatalogPage.clickOnElementsOfProductCatalogPage("tableConfigurationButton");
		sa.assertTrue(tableConfigurationPage.verifyElementsOfTableConfigurationPage("tableConfigurationTitle"), "Table configuration pop-up does not open after clicking on table Configuration Button");
		tableConfigurationPage.selectAllCheckboxOfPopup("columnCheckboxesOnPopup", "listOfColumnsOnPopup");
		tableConfigurationPage.clickOnElementsOfTableConfigurationPage("saveButton");

		// Test Case 1 - This test case validates if the filter functionality is working properly for the searchbox on Name column
		sa.assertTrue(productCatalogPage.verifySearchValueOnProductCatalogPage(LanguageCode, "prodProdNameSearchBox", ProductCatalogVariables.PRODUCT_CATALOG_LIST_PAGE_FAKE_NAME_SEARCH, "noProductCatalogDisplayText", "prodProdNameList"), "Search not working for Name field On Details page");
		sa.assertTrue(productCatalogPage.verifySearchValueOnProductCatalogPage(LanguageCode, "prodProdNameSearchBox", ProductCatalogVariables.PRODUCT_CATALOG_DETAILS_PAGE_NAME, "noProductCatalogDisplayText", "prodProdNameList"), "Search not working for Name field On Details page");

		// Test Case 2 - This test case validates if the filter functionality is working properly for the searchbox on status column
		productCatalogPage.waitForElementsOfProductCatalogPage("prodProductStatusBox");
		productCatalogPage.clickOnElementsOfProductCatalogPage("prodProductStatusBox");
		productCatalogPage.waitForElementsOfProductCatalogPage("dropdownProdProductStatusCheckboxes");
		sa.assertTrue(productCatalogPage.verifyFilterSingleSelectOnProductCatalogPage(LanguageCode, "dropdownProdProductStatusCheckboxes", "dropdownProdProductStatusElementListLabels", "prodProductStatusList", "noProductCatalogDisplayText"), "Listbox not working for Type dropdown");
		productCatalogPage.clickOnElementsOfProductCatalogPage("clearAllFilterField");
		productCatalogPage.waitForElementsOfProductCatalogPage("prodProductStatusBox");
		productCatalogPage.clickOnElementsOfProductCatalogPage("prodProductStatusBox");
		productCatalogPage.waitForElementsOfProductCatalogPage("dropdownProdProductStatusCheckboxes");
		sa.assertTrue(productCatalogPage.verifyFilterMultiSelectOnProductCatalogPage(LanguageCode, "dropdownProdProductStatusCheckboxes", "dropdownProdProductStatusElementListLabels", "prodProductStatusList", "noProductCatalogDisplayText"), "Listbox not working for Type dropdown");
		//productCatalogPage.clickOnElementsOfProductCatalogPage("clearAllFilterField");

		/*// Test Case 3 - This test case validates if the filter functionality is working properly for the search on Category column
		productCatalogPage.waitForElementsOfProductCatalogPage("prodProductCategoryBox");
		productCatalogPage.clickOnElementsOfProductCatalogPage("prodProductCategoryBox");
		productCatalogPage.waitForElementsOfProductCatalogPage("dropdownProdProductCategoryCheckboxes");
		sa.assertTrue(productCatalogPage.verifyFilterSingleSelectOnProductCatalogPage(LanguageCode, "dropdownProdProductCategoryCheckboxes", "dropdownProdProductCategoryElementListLabels", "prodProductCategoryList", "noProductCatalogDisplayText"), "Listbox not working for Category dropdown");
		productCatalogPage.clickOnElementsOfProductCatalogPage("clearProdProductCategoryField");
		productCatalogPage.waitForElementsOfProductCatalogPage("prodProductCategoryBox");
		productCatalogPage.clickOnElementsOfProductCatalogPage("prodProductCategoryBox");
		productCatalogPage.waitForElementsOfProductCatalogPage("dropdownProdProductCategoryCheckboxes");
		sa.assertTrue(productCatalogPage.verifyFilterMultiSelectOnProductCatalogPage(LanguageCode, "dropdownProdProductCategoryCheckboxes", "dropdownProdProductCategoryElementListLabels", "prodProductCategoryList", "noProductCatalogDisplayText"), "Listbox not working for Category dropdown");
		productCatalogPage.clickOnElementsOfProductCatalogPage("clearProdProductCategoryField");

		// Test Case 4 - This test case validates if the filter functionality is working properly for the Country column
		productCatalogPage.waitForElementsOfProductCatalogPage("prodProductCountryBox");
		productCatalogPage.clickOnElementsOfProductCatalogPage("prodProductCountryBox");
		productCatalogPage.waitForElementsOfProductCatalogPage("dropdownProdProductCountryCheckboxes");
		sa.assertTrue(productCatalogPage.verifyFilterSingleSelectOnProductCatalogPage(LanguageCode, "dropdownProdProductCountryCheckboxes", "dropdownProdProductCountryElementListLabels", "prodProductCountryList", "noProductCatalogDisplayText"), "Listbox not working for Country dropdown");
		productCatalogPage.clickOnElementsOfProductCatalogPage("clearProdProductCountryField");
		productCatalogPage.waitForElementsOfProductCatalogPage("prodProductCountryBox");
		productCatalogPage.clickOnElementsOfProductCatalogPage("prodProductCountryBox");
		productCatalogPage.waitForElementsOfProductCatalogPage("dropdownProdProductCountryCheckboxes");
		sa.assertTrue(productCatalogPage.verifyFilterMultiSelectOnProductCatalogPage(LanguageCode, "dropdownProdProductCountryCheckboxes", "dropdownProdProductCountryElementListLabels", "prodProductCountryList", "noProductCatalogDisplayText"), "Listbox not working for Country dropdown");
		productCatalogPage.clickOnElementsOfProductCatalogPage("clearProdProductCountryField");

		// Test Case 5 - This test case validates if the filter functionality is working properly for the searchbox on Description column
		sa.assertTrue(productCatalogPage.verifySearchValueOnProductCatalogPage(LanguageCode, "prodProductDescSearchBox", ProductCatalogVariables.PRODUCT_CATALOG_LIST_PAGE_FAKE_NAME_SEARCH, "noProductCatalogDisplayText", "prodProductDescList"), "Search not working for Description field On Details page");
		sa.assertTrue(productCatalogPage.verifySearchValueOnProductCatalogPage(LanguageCode, "prodProductDescSearchBox", ProductCatalogVariables.PRODUCT_CATALOG_DETAILS_PAGE_DESCRIPTION, "noProductCatalogDisplayText", "prodProductDescList"), "Search not working for Description field On Details page");

		// Test Case 6 - This test case validates if the filter functionality is working properly for the Product Series column
		productCatalogPage.waitForElementsOfProductCatalogPage("prodProductSeriesBox");
		productCatalogPage.clickOnElementsOfProductCatalogPage("prodProductSeriesBox");
		productCatalogPage.waitForElementsOfProductCatalogPage("dropdownProdProductSeriesCheckboxes");
		sa.assertTrue(productCatalogPage.verifyFilterSingleSelectOnProductCatalogPage(LanguageCode, "dropdownProdProductSeriesCheckboxes", "dropdownProdProductSeriesElementListLabels", "prodProductSeriesList", "noProductCatalogDisplayText"), "Listbox not working for Product Series dropdown");
		productCatalogPage.clickOnElementsOfProductCatalogPage("clearProdProductSeriesField");
		productCatalogPage.waitForElementsOfProductCatalogPage("prodProductSeriesBox");
		productCatalogPage.clickOnElementsOfProductCatalogPage("prodProductSeriesBox");
		productCatalogPage.waitForElementsOfProductCatalogPage("dropdownProdProductSeriesCheckboxes");
		sa.assertTrue(productCatalogPage.verifyFilterMultiSelectOnProductCatalogPage(LanguageCode, "dropdownProdProductSeriesCheckboxes", "dropdownProdProductSeriesElementListLabels", "prodProductSeriesList", "noProductCatalogDisplayText"), "Listbox not working for Product Series dropdown");
		productCatalogPage.clickOnElementsOfProductCatalogPage("clearProdProductSeriesField");

		// Test Case 7 - This test case validates if the filter functionality is working properly for the searchbox on Processor column
		sa.assertTrue(productCatalogPage.verifySearchValueOnProductCatalogPage(LanguageCode, "prodProcessorSearchBox", ProductCatalogVariables.PRODUCT_CATALOG_LIST_PAGE_FAKE_NAME_SEARCH, "noProductCatalogDisplayText", "prodProcessorList"), "Search not working for Processor field On Details page");
		sa.assertTrue(productCatalogPage.verifySearchValueOnProductCatalogPage(LanguageCode, "prodProcessorSearchBox", ProductCatalogVariables.PRODUCT_CATALOG_DETAILS_PAGE_PROCESSOR, "noProductCatalogDisplayText", "prodProcessorList"), "Search not working for Processor field On Details page");

		// Test Case 8 - This test case validates if the filter functionality is working properly for the searchbox on Processor Manufacturer column
		sa.assertTrue(productCatalogPage.verifySearchValueOnProductCatalogPage(LanguageCode, "prodProcessorMfgSearchBox", ProductCatalogVariables.PRODUCT_CATALOG_LIST_PAGE_FAKE_NAME_SEARCH, "noProductCatalogDisplayText", "prodProcessorMfgList"), "Search not working for Processor Manufacturer field On Details page");
		sa.assertTrue(productCatalogPage.verifySearchValueOnProductCatalogPage(LanguageCode, "prodProcessorMfgSearchBox", ProductCatalogVariables.PRODUCT_CATALOG_DETAILS_PAGE_PROCESSOR_MANUFACTURER, "noProductCatalogDisplayText", "prodProcessorMfgList"), "Search not working for Processor Manufacturer field On Details page");

		// Test Case 9 - This test case validates if the filter functionality is working properly for the searchbox on Processor Model column
		sa.assertTrue(productCatalogPage.verifySearchValueOnProductCatalogPage(LanguageCode, "prodPrcoessorModelSearchBox", ProductCatalogVariables.PRODUCT_CATALOG_LIST_PAGE_FAKE_NAME_SEARCH, "noProductCatalogDisplayText", "prodPrcoessorModelList"), "Search not working for Processor Model field On Details page");
		sa.assertTrue(productCatalogPage.verifySearchValueOnProductCatalogPage(LanguageCode, "prodPrcoessorModelSearchBox", ProductCatalogVariables.PRODUCT_CATALOG_DETAILS_PAGE_PROCESSOR_MODEL, "noProductCatalogDisplayText", "prodPrcoessorModelList"), "Search not working for Processor Model field On Details page");

		// Test Case 10 - This test case validates if the filter functionality is working properly for the searchbox on Processor Cores column
		sa.assertTrue(productCatalogPage.verifySearchValueOnProductCatalogPage(LanguageCode, "prodProcessorCoresSearchBox", ProductCatalogVariables.PRODUCT_CATALOG_LIST_PAGE_FAKE_NUMBER_SEARCH, "noProductCatalogDisplayText", "prodProcessorCoresSearchList"), "Search not working for Processor Cores field On Details page");
		sa.assertTrue(productCatalogPage.verifySearchValueOnProductCatalogPage(LanguageCode, "prodProcessorCoresSearchBox", ProductCatalogVariables.PRODUCT_CATALOG_DETAILS_PAGE_PROCESSOR_CORES, "noProductCatalogDisplayText", "prodProcessorCoresSearchList"), "Search not working for Processor Cores field On Details page");

		// Test Case 11 - This test case validates if the filter functionality is working properly for the searchbox on Processor Speed (GHz) column
		sa.assertTrue(productCatalogPage.verifySearchValueOnProductCatalogPage(LanguageCode, "prodProcessorSpeedSearchBox", ProductCatalogVariables.PRODUCT_CATALOG_LIST_PAGE_FAKE_NUMBER_SEARCH, "noProductCatalogDisplayText", "prodProcessorSpeedList"), "Search not working for Processor Speed (GHz) field On Details page");
		sa.assertTrue(productCatalogPage.verifySearchValueOnProductCatalogPage(LanguageCode, "prodProcessorSpeedSearchBox", ProductCatalogVariables.PRODUCT_CATALOG_DETAILS_PAGE_PROCESSOR_SPEED, "noProductCatalogDisplayText", "prodProcessorSpeedList"), "Search not working for Processor Speed (GHz) field On Details page");

		// Test Case 12 - This test case validates if the filter functionality is working properly for the searchbox on Memory column
		sa.assertTrue(productCatalogPage.verifySearchValueOnProductCatalogPage(LanguageCode, "prodMemorySearchBox", ProductCatalogVariables.PRODUCT_CATALOG_LIST_PAGE_FAKE_NAME_SEARCH, "noProductCatalogDisplayText", "prodMemoryList"), "Search not working for Memory field On Details page");
		sa.assertTrue(productCatalogPage.verifySearchValueOnProductCatalogPage(LanguageCode, "prodMemorySearchBox", ProductCatalogVariables.PRODUCT_CATALOG_DETAILS_PAGE_PROCESSOR_MEMORY, "noProductCatalogDisplayText", "prodMemoryList"), "Search not working for Memory field On Details page");

		// Test Case 13 - This test case validates if the filter functionality is working properly for the searchbox on Memory Size column
		sa.assertTrue(productCatalogPage.verifySearchValueOnProductCatalogPage(LanguageCode, "prodMemorySizeSearchBox", ProductCatalogVariables.PRODUCT_CATALOG_LIST_PAGE_FAKE_NUMBER_SEARCH, "noProductCatalogDisplayText", "prodMemorySizeList"), "Search not working for Memory Size field On Details page");
		sa.assertTrue(productCatalogPage.verifySearchValueOnProductCatalogPage(LanguageCode, "prodMemorySizeSearchBox", ProductCatalogVariables.PRODUCT_CATALOG_DETAILS_PAGE_PROCESSOR_MEMORY_SIZE, "noProductCatalogDisplayText", "prodMemorySizeList"), "Search not working for Memory Size field On Details page");

		// Test Case 14 - This test case validates if the filter functionality is working properly for the searchbox on Memory Manufacturer column
		sa.assertTrue(productCatalogPage.verifySearchValueOnProductCatalogPage(LanguageCode, "prodMemoryMfgSearchBox", ProductCatalogVariables.PRODUCT_CATALOG_LIST_PAGE_FAKE_NAME_SEARCH, "noProductCatalogDisplayText", "prodMemoryMfgList"), "Search not working for Memory Manufacturer field On Details page");
		sa.assertTrue(productCatalogPage.verifySearchValueOnProductCatalogPage(LanguageCode, "prodMemoryMfgSearchBox", ProductCatalogVariables.PRODUCT_CATALOG_DETAILS_PAGE_PROCESSOR_MEMORY_MANUFACTURER, "noProductCatalogDisplayText", "prodMemoryMfgList"), "Search not working for Memory Manufacturer field On Details page");

		// Test Case 15 - This test case validates if the filter functionality is working properly for the searchbox on Memory Type column
		sa.assertTrue(productCatalogPage.verifySearchValueOnProductCatalogPage(LanguageCode, "prodMemoryTypeSearchBox", ProductCatalogVariables.PRODUCT_CATALOG_LIST_PAGE_FAKE_NAME_SEARCH, "noProductCatalogDisplayText", "prodMemoryTypeList"), "Search not working for Memory Type field On Details page");
		sa.assertTrue(productCatalogPage.verifySearchValueOnProductCatalogPage(LanguageCode, "prodMemoryTypeSearchBox", ProductCatalogVariables.PRODUCT_CATALOG_DETAILS_PAGE_PROCESSOR_MEMORY_TYPE, "noProductCatalogDisplayText", "prodMemoryTypeList"), "Search not working for Memory Type field On Details page");

		// Test Case 16 - This test case validates if the filter functionality is working properly for the searchbox on Memory Form Factor column
		sa.assertTrue(productCatalogPage.verifySearchValueOnProductCatalogPage(LanguageCode, "prodMemoryFfSearchBox", ProductCatalogVariables.PRODUCT_CATALOG_LIST_PAGE_FAKE_NAME_SEARCH, "noProductCatalogDisplayText", "prodMemoryFfList"), "Search not working for Memory Form Factor field On Details page");
		sa.assertTrue(productCatalogPage.verifySearchValueOnProductCatalogPage(LanguageCode, "prodMemoryFfSearchBox", ProductCatalogVariables.PRODUCT_CATALOG_DETAILS_PAGE_PROCESSOR_MEMORY_FACTOR, "noProductCatalogDisplayText", "prodMemoryFfList"), "Search not working for Memory Form Factor field On Details page");

		// Test Case 17 - This test case validates if the filter functionality is working properly for the searchbox on Graphics column
		sa.assertTrue(productCatalogPage.verifySearchValueOnProductCatalogPage(LanguageCode, "prodGraphicsSearchBox", ProductCatalogVariables.PRODUCT_CATALOG_LIST_PAGE_FAKE_NAME_SEARCH, "noProductCatalogDisplayText", "prodGraphicsList"), "Search not working for Graphics field On Details page");
		sa.assertTrue(productCatalogPage.verifySearchValueOnProductCatalogPage(LanguageCode, "prodGraphicsSearchBox", ProductCatalogVariables.PRODUCT_CATALOG_DETAILS_PAGE_PROCESSOR_GRAPHICS, "noProductCatalogDisplayText", "prodGraphicsList"), "Search not working for Graphics field On Details page");

		// Test Case 18 - This test case validates if the filter functionality is working properly for the searchbox on Graphics Manufacturer column
		sa.assertTrue(productCatalogPage.verifySearchValueOnProductCatalogPage(LanguageCode, "prodGraphicsMfgSearchBox", ProductCatalogVariables.PRODUCT_CATALOG_LIST_PAGE_FAKE_NAME_SEARCH, "noProductCatalogDisplayText", "prodGraphicsMfgList"), "Search not working for Graphics Manufacturer field On Details page");
		sa.assertTrue(productCatalogPage.verifySearchValueOnProductCatalogPage(LanguageCode, "prodGraphicsMfgSearchBox", ProductCatalogVariables.PRODUCT_CATALOG_DETAILS_PAGE_PROCESSOR_GRAPHICS_MANUFACTURER, "noProductCatalogDisplayText", "prodGraphicsMfgList"), "Search not working for Graphics Manufacturer field On Details page");

		// Test Case 19 - This test case validates if the filter functionality is working properly for the searchbox on Graphics Model column
		sa.assertTrue(productCatalogPage.verifySearchValueOnProductCatalogPage(LanguageCode, "prodGraphicsModelSearchBox", ProductCatalogVariables.PRODUCT_CATALOG_LIST_PAGE_FAKE_NAME_SEARCH, "noProductCatalogDisplayText", "prodGraphicsModelList"), "Search not working for Graphics Model field On Details page");
		sa.assertTrue(productCatalogPage.verifySearchValueOnProductCatalogPage(LanguageCode, "prodGraphicsModelSearchBox", ProductCatalogVariables.PRODUCT_CATALOG_DETAILS_PAGE_PROCESSOR_GRAPHICS_MODEL, "noProductCatalogDisplayText", "prodGraphicsModelList"), "Search not working for Graphics Model field On Details page");

		// Test Case 20 - This test case validates if the filter functionality is working properly for the Product Availability column
		productCatalogPage.waitForElementsOfProductCatalogPage("prodAvailabilityBox");
		productCatalogPage.clickOnElementsOfProductCatalogPage("prodAvailabilityBox");
		productCatalogPage.waitForElementsOfProductCatalogPage("dropdownProdProductAvailabilityCheckboxes");
		sa.assertTrue(productCatalogPage.verifyFilterSingleSelectOnProductCatalogPage(LanguageCode, "dropdownProdProductAvailabilityCheckboxes", "dropdownProdProductAvailabilityElementListLabels", "prodAvailabilityList", "noProductCatalogDisplayText"), "Listbox not working for Product Availability dropdown");
		productCatalogPage.clickOnElementsOfProductCatalogPage("clearProdProductAvailabilityField");
		productCatalogPage.waitForElementsOfProductCatalogPage("prodAvailabilityBox");
		productCatalogPage.clickOnElementsOfProductCatalogPage("prodAvailabilityBox");
		productCatalogPage.waitForElementsOfProductCatalogPage("dropdownProdProductAvailabilityCheckboxes");
		sa.assertTrue(productCatalogPage.verifyFilterMultiSelectOnProductCatalogPage(LanguageCode, "dropdownProdProductAvailabilityCheckboxes", "dropdownProdProductAvailabilityElementListLabels", "prodAvailabilityList", "noProductCatalogDisplayText"), "Listbox not working for Product Availability dropdown");
		productCatalogPage.clickOnElementsOfProductCatalogPage("clearProdProductAvailabilityField");

		// Test Case 21 - This test case validates if the filter functionality is working properly for the searchbox on Part Number column
		sa.assertTrue(productCatalogPage.verifySearchValueOnProductCatalogPage(LanguageCode, "prodPartNoSearchBox", ProductCatalogVariables.PRODUCT_CATALOG_LIST_PAGE_FAKE_NAME_SEARCH, "noProductCatalogDisplayText", "prodPartNoList"), "Search not working for Part Number field On Details page");
		sa.assertTrue(productCatalogPage.verifySearchValueOnProductCatalogPage(LanguageCode, "prodPartNoSearchBox", ProductCatalogVariables.PRODUCT_CATALOG_DETAILS_PAGE_PART_NUMBER, "noProductCatalogDisplayText", "prodPartNoList"), "Search not working for Part Number field On Details page");

		// Test Case 22 - This test case validates if the filter functionality is working properly for the searchbox on ConfigId column
		sa.assertTrue(productCatalogPage.verifySearchValueOnProductCatalogPage(LanguageCode, "prodConfigIdSearchBox", ProductCatalogVariables.PRODUCT_CATALOG_LIST_PAGE_FAKE_NUMBER_SEARCH, "noProductCatalogDisplayText", "prodConfigIdList"), "Search not working for ConfigId field On Details page");
		sa.assertTrue(productCatalogPage.verifySearchValueOnProductCatalogPage(LanguageCode, "prodConfigIdSearchBox", ProductCatalogVariables.PRODUCT_CATALOG_DETAILS_PAGE_CONFIG_ID, "noProductCatalogDisplayText", "prodConfigIdList"), "Search not working for ConfigId field On Details page");

		// Test Case 23 - This test case validates if the filter functionality is working properly for the Operating System column
		productCatalogPage.waitForElementsOfProductCatalogPage("prodProductOsBox");
		productCatalogPage.clickOnElementsOfProductCatalogPage("prodProductOsBox");
		productCatalogPage.waitForElementsOfProductCatalogPage("dropdownProdProductOsCheckboxes");
		sa.assertTrue(productCatalogPage.verifyFilterSingleSelectOnProductCatalogPage(LanguageCode, "dropdownProdProductOsCheckboxes", "dropdownProdProductOsElementListLabels", "prodProductOsList", "noProductCatalogDisplayText"), "Listbox not working for Operating System dropdown");
		productCatalogPage.clickOnElementsOfProductCatalogPage("clearProdProductOsField");
		productCatalogPage.waitForElementsOfProductCatalogPage("prodProductOsBox");
		productCatalogPage.clickOnElementsOfProductCatalogPage("prodProductOsBox");
		productCatalogPage.waitForElementsOfProductCatalogPage("dropdownProdProductOsCheckboxes");
		sa.assertTrue(productCatalogPage.verifyFilterMultiSelectOnProductCatalogPage(LanguageCode, "dropdownProdProductOsCheckboxes", "dropdownProdProductOsElementListLabels", "prodProductOsList", "noProductCatalogDisplayText"), "Listbox not working for Operating System dropdown");
		productCatalogPage.clickOnElementsOfProductCatalogPage("clearProdProductOsField");

		// Test Case 24 - This test case validates if the filter functionality is working properly for the searchbox on Storage column
		sa.assertTrue(productCatalogPage.verifySearchValueOnProductCatalogPage(LanguageCode, "prodHddSearchBox", ProductCatalogVariables.PRODUCT_CATALOG_LIST_PAGE_FAKE_NAME_SEARCH, "noProductCatalogDisplayText", "prodHddList"), "Search not working for Storage field On Details page");
		sa.assertTrue(productCatalogPage.verifySearchValueOnProductCatalogPage(LanguageCode, "prodHddSearchBox", ProductCatalogVariables.PRODUCT_CATALOG_DETAILS_PAGE_STORAGE, "noProductCatalogDisplayText", "prodHddList"), "Search not working for Storage field On Details page");

		// Test Case 25 - This test case validates if the filter functionality is working properly for the searchbox on Storage Size column
		sa.assertTrue(productCatalogPage.verifySearchValueOnProductCatalogPage(LanguageCode, "prodHddSizeSearchBox", ProductCatalogVariables.PRODUCT_CATALOG_LIST_PAGE_FAKE_NUMBER_SEARCH, "noProductCatalogDisplayText", "prodHddSizeList"), "Search not working for Storage Size field On Details page");
		sa.assertTrue(productCatalogPage.verifySearchValueOnProductCatalogPage(LanguageCode, "prodHddSizeSearchBox", ProductCatalogVariables.PRODUCT_CATALOG_DETAILS_PAGE_STORAGE_SIZE, "noProductCatalogDisplayText", "prodHddSizeList"), "Search not working for Storage Size field On Details page");

		// Test Case 26 - This test case validates if the filter functionality is working properly for the searchbox on Storage Type column
		sa.assertTrue(productCatalogPage.verifySearchValueOnProductCatalogPage(LanguageCode, "prodHddTypeSearchBox", ProductCatalogVariables.PRODUCT_CATALOG_LIST_PAGE_FAKE_NAME_SEARCH, "noProductCatalogDisplayText", "prodHddTypeSearchList"), "Search not working for Storage Type field On Details page");
		sa.assertTrue(productCatalogPage.verifySearchValueOnProductCatalogPage(LanguageCode, "prodHddTypeSearchBox", ProductCatalogVariables.PRODUCT_CATALOG_DETAILS_PAGE_STORAGE_TYPE, "noProductCatalogDisplayText", "prodHddTypeSearchList"), "Search not working for Storage Type field On Details page");

		// Test Case 27 - This test case validates if the filter functionality is working properly for the searchbox on Screen Information column
		sa.assertTrue(productCatalogPage.verifySearchValueOnProductCatalogPage(LanguageCode, "prodScreenInformationSearchBox", ProductCatalogVariables.PRODUCT_CATALOG_LIST_PAGE_FAKE_NAME_SEARCH, "noProductCatalogDisplayText", "prodScreenInformationSearchList"), "Search not working for Screen Information field On Details page");
		sa.assertTrue(productCatalogPage.verifySearchValueOnProductCatalogPage(LanguageCode, "prodScreenInformationSearchBox", ProductCatalogVariables.PRODUCT_CATALOG_DETAILS_PAGE_SCREEN_INFORMATION, "noProductCatalogDisplayText", "prodScreenInformationSearchList"), "Search not working for Screen Information field On Details page");

		// Test Case 28 - This test case validates if the filter functionality is working properly for the searchbox on Screen Resolution column
		sa.assertTrue(productCatalogPage.verifySearchValueOnProductCatalogPage(LanguageCode, "prodResolutionSearchBox", ProductCatalogVariables.PRODUCT_CATALOG_LIST_PAGE_FAKE_NUMBER_SEARCH, "noProductCatalogDisplayText", "prodResolutionList"), "Search not working for Screen Resolution field On Details page");
		sa.assertTrue(productCatalogPage.verifySearchValueOnProductCatalogPage(LanguageCode, "prodResolutionSearchBox", ProductCatalogVariables.PRODUCT_CATALOG_DETAILS_PAGE_SCREEN_RESOLUTION, "noProductCatalogDisplayText", "prodResolutionList"), "Search not working for Screen Resolution field On Details page");

		// Test Case 29 - This test case validates if the filter functionality is working properly for the searchbox on Peripherals column
		sa.assertTrue(productCatalogPage.verifySearchValueOnProductCatalogPage(LanguageCode, "prodPeripheralsSearchBox", ProductCatalogVariables.PRODUCT_CATALOG_LIST_PAGE_FAKE_NAME_SEARCH, "noProductCatalogDisplayText", "prodPeripheralsList"), "Search not working for Peripherals field On Details page");
		sa.assertTrue(productCatalogPage.verifySearchValueOnProductCatalogPage(LanguageCode, "prodPeripheralsSearchBox", ProductCatalogVariables.PRODUCT_CATALOG_DETAILS_PERIPHERALS, "noProductCatalogDisplayText", "prodPeripheralsList"), "Search not working for Peripherals field On Details page");

		// Test Case 30 - This test case validates if the filter functionality is working properly for the searchbox on Size column
		sa.assertTrue(productCatalogPage.verifySearchValueOnProductCatalogPage(LanguageCode, "prodSizeSearchBox", ProductCatalogVariables.PRODUCT_CATALOG_LIST_PAGE_FAKE_NUMBER_SEARCH, "noProductCatalogDisplayText", "prodSizeList"), "Search not working for Size field On Details page");
		sa.assertTrue(productCatalogPage.verifySearchValueOnProductCatalogPage(LanguageCode, "prodSizeSearchBox", ProductCatalogVariables.PRODUCT_CATALOG_DETAILS_SIZE, "noProductCatalogDisplayText", "prodSizeList"), "Search not working for Size field On Details page");

		// Test Case 31 - This test case validates if the filter functionality is working properly for the searchbox on Weight column
		sa.assertTrue(productCatalogPage.verifySearchValueOnProductCatalogPage(LanguageCode, "prodWeightSearchBox", ProductCatalogVariables.PRODUCT_CATALOG_LIST_PAGE_FAKE_NUMBER_SEARCH, "noProductCatalogDisplayText", "prodWeightList"), "Search not working for Weight field On Details page");
		sa.assertTrue(productCatalogPage.verifySearchValueOnProductCatalogPage(LanguageCode, "prodWeightSearchBox", ProductCatalogVariables.PRODUCT_CATALOG_DETAILS_WEIGHT, "noProductCatalogDisplayText", "prodWeightList"), "Search not working for Weight field On Details page");

		// Test Case 32 - This test case validates if the filter functionality is working properly for the searchbox on Color column
		sa.assertTrue(productCatalogPage.verifySearchValueOnProductCatalogPage(LanguageCode, "prodColorSearchBox", ProductCatalogVariables.PRODUCT_CATALOG_LIST_PAGE_FAKE_NAME_SEARCH, "noProductCatalogDisplayText", "prodColorList"), "Search not working for Color field On Details page");
		sa.assertTrue(productCatalogPage.verifySearchValueOnProductCatalogPage(LanguageCode, "prodColorSearchBox", ProductCatalogVariables.PRODUCT_CATALOG_DETAILS_COLOR, "noProductCatalogDisplayText", "prodColorList"), "Search not working for Color field On Details page");

		// Test Case 33 - This test case validates if the filter functionality is working properly for the searchbox on Warranty column
		sa.assertTrue(productCatalogPage.verifySearchValueOnProductCatalogPage(LanguageCode, "prodWarrantySearchBox", ProductCatalogVariables.PRODUCT_CATALOG_LIST_PAGE_FAKE_NAME_SEARCH, "noProductCatalogDisplayText", "prodWarrantyList"), "Search not working for Warranty field On Details page");
		sa.assertTrue(productCatalogPage.verifySearchValueOnProductCatalogPage(LanguageCode, "prodWarrantySearchBox", ProductCatalogVariables.PRODUCT_CATALOG_DETAILS_WARRANTY, "noProductCatalogDisplayText", "prodWarrantyList"), "Search not working for Warranty field On Details page");

		// Test Case 34 - This test case validates if the filter functionality is working properly for the searchbox on Price column
		sa.assertTrue(productCatalogPage.verifySearchValueOnProductCatalogPage(LanguageCode, "prodPriceSearchBox", ProductCatalogVariables.PRODUCT_CATALOG_LIST_PAGE_FAKE_NUMBER_SEARCH, "noProductCatalogDisplayText", "prodPriceList"), "Search not working for Price field On Details page");
		sa.assertTrue(productCatalogPage.verifySearchValueOnProductCatalogPage(LanguageCode, "prodPriceSearchBox", ProductCatalogVariables.PRODUCT_CATALOG_DETAILS_PRICE, "noProductCatalogDisplayText", "prodPriceList"), "Search not working for Price field On Details page");

		// Test Case 35 - This test case validates if the filter functionality is working properly for the searchbox on Currency column
		sa.assertTrue(productCatalogPage.verifySearchValueOnProductCatalogPage(LanguageCode, "prodPriceCurSearchBox", ProductCatalogVariables.PRODUCT_CATALOG_LIST_PAGE_FAKE_NAME_SEARCH, "noProductCatalogDisplayText", "prodPriceCurList"), "Search not working for Currency field On Details page");
		sa.assertTrue(productCatalogPage.verifySearchValueOnProductCatalogPage(LanguageCode, "prodPriceCurSearchBox", ProductCatalogVariables.PRODUCT_CATALOG_DETAILS_CURRENCY, "noProductCatalogDisplayText", "prodPriceCurList"), "Search not working for Currency field On Details page");

		// Test Case 36 - This test case validates if the filter functionality is working properly for the searchbox on Buy URL column
		sa.assertTrue(productCatalogPage.verifySearchValueOnProductCatalogPage(LanguageCode, "prodBuyUrlSearchBox", ProductCatalogVariables.PRODUCT_CATALOG_LIST_PAGE_FAKE_NAME_SEARCH, "noProductCatalogDisplayText", "prodBuyUrlList"), "Search not working for Buy URL field On Details page");
		sa.assertTrue(productCatalogPage.verifySearchValueOnProductCatalogPage(LanguageCode, "prodBuyUrlSearchBox", ProductCatalogVariables.PRODUCT_CATALOG_DETAILS_BUY_URL, "noProductCatalogDisplayText", "prodBuyUrlList"), "Search not working for Buy URL field On Details page");

		// Test Case 37 - This test case validates if the filter functionality is working properly for the Usage Category column
		productCatalogPage.waitForElementsOfProductCatalogPage("prodUsageCategoryBox");
		productCatalogPage.clickOnElementsOfProductCatalogPage("prodUsageCategoryBox");
		productCatalogPage.waitForElementsOfProductCatalogPage("dropdownProdProductUsageCategoryCheckboxes");
		sa.assertTrue(productCatalogPage.verifyFilterSingleSelectOnProductCatalogPage(LanguageCode, "dropdownProdProductUsageCategoryCheckboxes", "dropdownProdProductUsageCategoryElementListLabels", "prodUsageCategoryList", "noProductCatalogDisplayText"), "Listbox not working for Usage Category dropdown");
		productCatalogPage.clickOnElementsOfProductCatalogPage("clearProdProductUsageCategoryField");
		productCatalogPage.waitForElementsOfProductCatalogPage("prodUsageCategoryBox");
		productCatalogPage.clickOnElementsOfProductCatalogPage("prodUsageCategoryBox");
		productCatalogPage.waitForElementsOfProductCatalogPage("dropdownProdProductUsageCategoryCheckboxes");
		sa.assertTrue(productCatalogPage.verifyFilterMultiSelectOnProductCatalogPage(LanguageCode, "dropdownProdProductUsageCategoryCheckboxes", "dropdownProdProductUsageCategoryElementListLabels", "prodUsageCategoryList", "noProductCatalogDisplayText"), "Listbox not working for Usage Category dropdown");
		productCatalogPage.clickOnElementsOfProductCatalogPage("clearProdProductUsageCategoryField");

		// Test Case 38 - This test case validates if the filter functionality is working properly for the searchbox on Subcategory column
		sa.assertTrue(productCatalogPage.verifySearchValueOnProductCatalogPage(LanguageCode, "prodSubCategorySearchBox", ProductCatalogVariables.PRODUCT_CATALOG_LIST_PAGE_FAKE_NAME_SEARCH, "noProductCatalogDisplayText", "prodSubCategoryList"), "Search not working for Subcategory field On Details page");
		sa.assertTrue(productCatalogPage.verifySearchValueOnProductCatalogPage(LanguageCode, "prodSubCategorySearchBox", ProductCatalogVariables.PRODUCT_CATALOG_DETAILS_SUBCATEGORY, "noProductCatalogDisplayText", "prodSubCategoryList"), "Search not working for Subcategory field On Details page");

		// Test Case 39 - This test case validates if the filter functionality is working properly for the Type column
		productCatalogPage.waitForElementsOfProductCatalogPage("prodProductTypeBox");
		productCatalogPage.clickOnElementsOfProductCatalogPage("prodProductTypeBox");
		productCatalogPage.waitForElementsOfProductCatalogPage("dropdownProdProductTypeCheckboxes");
		sa.assertTrue(productCatalogPage.verifyFilterSingleSelectOnProductCatalogPage(LanguageCode, "dropdownProdProductTypeCheckboxes", "dropdownProdProductTypeElementListLabels", "prodProductTypeList", "noProductCatalogDisplayText"), "Listbox not working for Type dropdown");
		productCatalogPage.clickOnElementsOfProductCatalogPage("clearProdProductTypeField");
		productCatalogPage.waitForElementsOfProductCatalogPage("prodProductTypeBox");
		productCatalogPage.clickOnElementsOfProductCatalogPage("prodProductTypeBox");
		productCatalogPage.waitForElementsOfProductCatalogPage("dropdownProdProductTypeCheckboxes");
		sa.assertTrue(productCatalogPage.verifyFilterMultiSelectOnProductCatalogPage(LanguageCode, "dropdownProdProductTypeCheckboxes", "dropdownProdProductTypeElementListLabels", "prodProductTypeList", "noProductCatalogDisplayText"), "Listbox not working for Type dropdown");
		productCatalogPage.clickOnElementsOfProductCatalogPage("clearProdProductTypeField");

		// Test Case 40 - This test case validates if the filter functionality is working properly for the searchbox on Purchase Type column
		sa.assertTrue(productCatalogPage.verifySearchValueOnProductCatalogPage(LanguageCode, "prodBuyTypeSearchBox", ProductCatalogVariables.PRODUCT_CATALOG_LIST_PAGE_FAKE_NAME_SEARCH, "noProductCatalogDisplayText", "prodBuyTypeList"), "Search not working for Purchase Type field On Details page");
		sa.assertTrue(productCatalogPage.verifySearchValueOnProductCatalogPage(LanguageCode, "prodBuyTypeSearchBox", ProductCatalogVariables.PRODUCT_CATALOG_DETAILS_PURCHASE_TYPE, "noProductCatalogDisplayText", "prodBuyTypeList"), "Search not working for Purchase Type field On Details page");*/

		sa.assertAll();
	}
		LOGGER.info("All filter functionality test-cases passed on Product Catalog Details page");
	}

	@Test(priority = 10, groups = { "REGRESSION", "SMOKE", "REGRESSION_CI", "SMOKE_CI" })
	public final void verifyPaginationOnProductCatalogDetailsPage() throws Exception {
		SoftAssert sa = new SoftAssert();
		login("RESELLER_NEW_UI_Companies_EMAIL_COMPANIES", "RESELLER_NEW_UI_Companies_PASSWORD_COMPANIES");
		gotoProductCatalogTab();
		ProductCatalogPage productCatalogPage = new ProductCatalogPage(PreDefinedActions.getDriver()).getInstance();

		waitForPageLoaded();

		List<WebElement> catalogList = productCatalogPage.getElementsOfProductCatalogPage("catalogNameSearchList");
		sa.assertTrue(productCatalogPage.navigateToProductCatalogDetailsPage(catalogList), "Only Default catalog is available");
		productCatalogPage.waitForElementsOfProductCatalogPage("tableOverlay");

		TableConfigurationPage tableConfigurationPage = new TableConfigurationPage(PreDefinedActions.getDriver()).getInstance();

		productCatalogPage.waitForElementsOfProductCatalogPage("tableConfigurationButton");
		resetTableConfiguration();
		productCatalogPage.clickOnElementsOfProductCatalogPage("tableConfigurationButton");
		sa.assertTrue(tableConfigurationPage.verifyElementsOfTableConfigurationPage("tableConfigurationTitle"), "Table configuration pop-up does not open after clicking on table configuration button");
		tableConfigurationPage.selectAllCheckboxOfPopup("columnCheckboxesOnPopup", "listOfColumnsOnPopup");
		tableConfigurationPage.clickOnElementsOfTableConfigurationPage("saveButton");

		productCatalogPage.waitForElementsOfProductCatalogPage("tableOverlay");
		productCatalogPage.waitForElementsOfProductCatalogPage("paginationDropdownMenu");
		sa.assertTrue(productCatalogPage.verifyElementIsEnableOfProductCatalogPage("paginationDropdownMenu"), "Pagination Dropdown not available");
		sa.assertTrue(productCatalogPage.verifyElementIsClickableOfProductCatalogPage("paginationDropdownMenu"), "Pagination Dropdown not clickable");
		sa.assertTrue(productCatalogPage.waitForElementsOfProductCatalogPage("navigationItemDiv"), "Navigation items are not available");
		getPaginationInfo();
		LOGGER.info("get Pagination Information ");
		sa.assertTrue(productCatalogPage.verifyElementIsEnableOfProductCatalogPage("navigationItemActivePage"), "The active page navigation link is not disabled");
		sa.assertTrue(productCatalogPage.verifyTotalPageCountChangeAsSelectedOption(selectedOption, totalCount, lastPageNumber), "Total page count does not change with selected option");
		if (!verifySelectedOptionForNewSelection(selectedOption, CommonVariables.SELETEDFIFTY)) {
			if (changeSelectedOption(totalCount, CommonVariables.SELETEDTWENTYFIVE)) {
				productCatalogPage.selectElementFromDropDownOfProductCatalogPage("paginationDropdownMenu", "paginationOptionList", Integer.toString(CommonVariables.SELETEDTWENTYFIVE));
				LOGGER.info("Change selected option as " + CommonVariables.SELETEDTWENTYFIVE);
				productCatalogPage.waitForElementsOfProductCatalogPage("tableOverlay");
				getPaginationInfo();
				sa.assertTrue(productCatalogPage.verifyTotalPageCountChangeAsSelectedOption(selectedOption, totalCount, lastPageNumber), "Total page count does not change with selected option");
				sa.assertTrue(productCatalogPage.verifyButtonStatusofPagination(activePageNumber, firstPageNumber, lastPageNumber, totalCount, previousButtonStatus, nextButtonStatus), "Button visibility status is not as per expectation");
			} else {

				LOGGER.info("Selected user has less than " + CommonVariables.SELETEDTWENTYFIVE + " records");
			}
		} else {
			if (changeSelectedOption(totalCount, CommonVariables.SELETEDFIFTY)) {
				productCatalogPage.selectElementFromDropDownOfProductCatalogPage("paginationDropdownMenu", "paginationOptionList", Integer.toString(CommonVariables.SELETEDFIFTY));
				LOGGER.info("Change Selected option as " + CommonVariables.SELETEDFIFTY);
				productCatalogPage.waitForElementsOfProductCatalogPage("tableOverlay");
				getPaginationInfo();
				sa.assertTrue(productCatalogPage.verifyTotalPageCountChangeAsSelectedOption(selectedOption, totalCount, lastPageNumber), "Total page count does not change with selected option");
				sa.assertTrue(productCatalogPage.verifyButtonStatusofPagination(activePageNumber, firstPageNumber, lastPageNumber, totalCount, previousButtonStatus, nextButtonStatus), "Button visibility status is not as per expectation");
			} else {

				LOGGER.info("Selected user has less than " + CommonVariables.SELETEDFIFTY + " records");
			}
		}
		productCatalogPage.waitForElementsOfProductCatalogPage("tableOverlay");
		if (productCatalogPage.canClickNext(activePageNumber, lastPageNumber, nextButtonStatus)) {
			sa.assertTrue(productCatalogPage.getButtonEnabilityStatus("navigationItemNext"), "Navigation next button is not enable");
			productCatalogPage.waitForElementsOfProductCatalogPage("navigationItemNext");
			productCatalogPage.clickOnElementsOfProductCatalogPage("navigationItemNext");
			productCatalogPage.waitForElementsOfProductCatalogPage("tableOverlay");
			getPaginationInfo();
			productCatalogPage.waitForElementsOfProductCatalogPage("navigationItemPrevious");
			if (productCatalogPage.canClickPrevious(activePageNumber, firstPageNumber, previousButtonStatus)) {
				sa.assertTrue(productCatalogPage.getButtonEnabilityStatus("navigationItemPrevious"), "Navigation previous button is not enabled");
				productCatalogPage.clickOnElementsOfProductCatalogPage("navigationItemPrevious");
			} else {
				LOGGER.info("Previous button is disabled");
			}
		} else if (productCatalogPage.canClickPrevious(activePageNumber, firstPageNumber, previousButtonStatus)) {
			sa.assertTrue(productCatalogPage.getButtonEnabilityStatus("navigationItemPrevious"), "Navigation previous button is not enabled");
			productCatalogPage.waitForElementsOfProductCatalogPage("navigationItemPrevious");
			productCatalogPage.clickOnElementsOfProductCatalogPage("navigationItemPrevious");
			productCatalogPage.waitForElementsOfProductCatalogPage("tableOverlay");
			getPaginationInfo();
			productCatalogPage.waitForElementsOfProductCatalogPage("navigationItemNext");
			if (productCatalogPage.canClickNext(activePageNumber, lastPageNumber, nextButtonStatus)) {
				sa.assertTrue(productCatalogPage.getButtonEnabilityStatus("navigationItemNext"), "Navigation next button is not enabled");
				productCatalogPage.clickOnElementsOfProductCatalogPage("navigationItemNext");
			} else {
				LOGGER.info("Next button is disabled");
			}
		} else {
			LOGGER.info("Previous and Next button both are disabled on Product Catalog Details Page ");
		}
	}

	@Test(priority = 11, groups = { "REGRESSION", "SMOKE", "REGRESSION_CI", "SMOKE_CI" })
	public final void verifySetStatusFunctionality() throws Exception {
		login("RESELLER_NEW_UI_Companies_EMAIL_COMPANIES", "RESELLER_NEW_UI_Companies_PASSWORD_COMPANIES");
		gotoProductCatalogTab();
		ProductCatalogPage productCatalogPage = new ProductCatalogPage(PreDefinedActions.getDriver()).getInstance();
		resetTableConfiguration();
		waitForPageLoaded();
		List<WebElement> catalogList = productCatalogPage.getElementsOfProductCatalogPage("catalogNameSearchList");
		if(productCatalogPage.navigateToProductCatalogDetailsPage(catalogList)){
				productCatalogPage.waitForElementsOfProductCatalogPage("tableOverlay");
				productCatalogPage.clickByJavaScriptOnElementsOfProductCatalogPage("selectAllCheckBox");
				productCatalogPage.clickOnElementsOfProductCatalogPage("setStatusButton");
				sa.assertTrue(productCatalogPage.matchTextOfProductCatalogPage("statusTitle", productCatalogPage.getTextLanguage(LanguageCode, "daas_ui", "product.status")), "Set Status dialog title does not match");
				productCatalogPage.clickOnElementsOfProductCatalogPage("changeStatusButton");
				productCatalogPage.waitForElementsOfProductCatalogPage("retiredOption");
				productCatalogPage.clickOnElementsOfProductCatalogPage("retiredOption");
				productCatalogPage.clickOnElementsOfProductCatalogPage("saveButton");
				productCatalogPage.waitForElementsOfProductCatalogPage("tableOverlay");
				List<WebElement> catalogStatusList = productCatalogPage.getElementsOfProductCatalogPage("prodProductStatusList");
				sleeper(3000);
				sa.assertTrue(productCatalogPage.checkCatalogListStatus(catalogStatusList, LanguageCode), "Multiple set status functionality not working as expected");
				productCatalogPage.moveToElementOnProductCatalogPage("firstRecord");
				//Vineer 3 change for hamburger pending due to bug 609547
				productCatalogPage.clickOnElementsOfProductCatalogPage("hamburger");
				productCatalogPage.clickOnElementsOfProductCatalogPage("setActive");
				productCatalogPage.waitForElementsOfProductCatalogPage("toastNotification");
				sa.assertTrue(productCatalogPage.matchTextOfProductCatalogPage("toastNotification", productCatalogPage.getTextLanguage(LanguageCode, "daas_ui", "global.messages.update_successful")), "Toast Notification text doesnot match");
				sleeper(1000);
				sa.assertTrue(productCatalogPage.getTextOfProductCatalogPage("firstRecordStatus").equalsIgnoreCase(productCatalogPage.getTextLanguage(LanguageCode, "daas_ui", "product.active")), "Set Status functionality from hamburger not working");
				sa.assertAll();
				LOGGER.info("All set status functionality test-cases passed on Product Catalog Details page");
		}
	}

	/**
	 * This method will verify the table configuration test cases of device list page
	 * 
	 * @throws Exception
	 */
	@Test(priority = 12, groups = { "REGRESSION", "REGRESSION_CI" }, description = "[US 208608]")
	public final void verifyTableConfigurationTestCases() throws Exception {
		login("RESELLER_NEW_UI_Companies_EMAIL_COMPANIES", "RESELLER_NEW_UI_Companies_PASSWORD_COMPANIES");
		String tenantID = getValueFromToken("tenant");
		gotoProductCatalogTab();
		waitForPageLoaded();
		// verifying search service api
		Assert.assertTrue(getStatusCode(getSearchServiceApi(environment) + CommonVariables.DETAILSSEARCHSERVICEAPI + tenantID + CommonVariables.DETAILSSEARCHSERVICEAPI2, ProductCatalogVariables.SEARCHSERVICEBODY, "POST", getEnvironment(environment)) == CommonVariables.CODEOK, "Search service failed for product catalog list page");
		ArrayList<String> columnName = new ArrayList<String>(Arrays.asList(getTextLanguage(LanguageCode, "daas_ui", "catalog.name"), getTextLanguage(LanguageCode, "daas_ui", "catalog.type"), getTextLanguage(LanguageCode, "daas_ui", "catalog.company"), getTextLanguage(LanguageCode, "daas_ui", "catalog.products"), getTextLanguage(LanguageCode, "daas_ui", "catalog.updatedOn"), getTextLanguage(LanguageCode, "daas_ui", "catalog.createdBy"),
				getTextLanguage(LanguageCode, "daas_ui", "catalog.updatedBy"), getTextLanguage(LanguageCode, "daas_ui", "catalog.catalogActive")));
		ArrayList<String> checkboxValue = new ArrayList<String>(Arrays.asList("true", "true", "true", "true", "true", "false", "false", "false"));
		ArrayList<String> name = new ArrayList<String>(Arrays.asList(getTextLanguage(LanguageCode, "daas_ui", "catalog.name")));
		verifyTableConfigurationTests(columnName, checkboxValue, name);
	}

	/**
	 * This method will verify generate the recommendations on Product Catalog page.
	 * 
	 * @throws Exception
	 */
	@Test(priority = 13, groups = { "REGRESSION", "REGRESSION_CI","STABILIZATION_STAGING" }, description = "275695") // , enabled = false)
	public final void verifyRecommendationsTestCase() throws Exception {
		List<String> columnList = null;
		SoftAssert sa = new SoftAssert();
		login("RESELLER_NEW_UI_Companies_EMAIL_COMPANIES", "RESELLER_NEW_UI_Companies_PASSWORD_COMPANIES");
		gotoProductCatalogTab();
		ProductCatalogPage productCatalogPage = new ProductCatalogPage(PreDefinedActions.getDriver());
		productCatalogPage = productCatalogPage.getInstance();
		waitForPageLoaded();
		TableConfigurationPage tableConfigurationPage = new TableConfigurationPage(PreDefinedActions.getDriver());
		tableConfigurationPage = tableConfigurationPage.getInstance();
		productCatalogPage.waitForElementsOfProductCatalogPage("tableConfigurationButton");
		resetTableConfiguration();
		productCatalogPage.clickOnElementsOfProductCatalogPage("tableConfigurationButton");
		sa.assertTrue(tableConfigurationPage.verifyElementsOfTableConfigurationPage("tableConfigurationTitle"), "Table configuration pop-up does not open after clicking on table Configuration Button");
		tableConfigurationPage.selectAllCheckboxOfPopup("columnCheckboxesOnPopup", "listOfColumnsOnPopup");
		tableConfigurationPage.clickOnElementsOfTableConfigurationPage("saveButton");
		waitForPageLoaded();
		productCatalogPage.waitForElementsOfProductCatalogPage("addButton");
		productCatalogPage.clickOnElementsOfProductCatalogPage("addButton");
		productCatalogPage.waitForElementsOfProductCatalogPage("dropDownOpen");
		sleeper(2000);
			productCatalogPage.clickOnElementsOfProductCatalogPage("dropDownOpen");
			sleeper(2000);
		LOGGER.info("Clicked on dropdown arrow");
		columnList = productCatalogPage.getTextOfListOfProductCatalogPage("companyColumnListOnPopup");
			productCatalogPage.clickOnElementsOfProductCatalogPage("dropdownClose");
			sleeper(2000);
		productCatalogPage.waitForElementsOfProductCatalogPage("cancelButtonOnAddPopup");
		productCatalogPage.clickOnElementsOfProductCatalogPage("cancelButtonOnAddPopup");
		productCatalogPage.waitForElementsOfProductCatalogPage("catalogNameSearchBox");
		productCatalogPage.enterTextForProductCatalogPage("catalogNameSearchBox", columnList.get(0));
		productCatalogPage.clickOnElementsOfProductCatalogPage("clearAllFilterField");
		// verifySearchFunctionality(LanguageCode, "catalogNameSearchBox", columnList.get(0) , "noProductCatalogDisplayText", "catalogNameSearchList");
		// enterText("catalogNameSearchBox", columnList.get(0));
		
		if(productCatalogPage.getTextOfListOfProductCatalogPage("catalogNameSearchList").size()>1)//Check if there are catalogs present other than default
			{
				productCatalogPage.waitForElementsOfProductCatalogPage("selectAllCheckBox");
				productCatalogPage.clickByJavaScriptOnElementsOfProductCatalogPage("selectAllCheckBox");
				// Test case 1 : This test case verifies generate HTML recommendation
				productCatalogPage.clickOnElementsOfProductCatalogPage("generateRecommendation");
				productCatalogPage.clickOnElementsOfProductCatalogPage("generateHtml");
				sleeper(3000);
				sa.assertTrue(productCatalogPage.getTextOfProductCatalogPage("toastNotification").equals(productCatalogPage.getTextLanguage(LanguageCode, "daas_ui", "catalog.generate_recommendation.success")), "Toast Notification text does not match for generate recommendation");
				productCatalogPage.clickOnElementsOfProductCatalogPage("toastNotificationClose");
		
				// Test case 2 : This test case verifies generate PDF recommendation
				productCatalogPage.clickOnElementsOfProductCatalogPage("generateRecommendation");
				productCatalogPage.clickOnElementsOfProductCatalogPage("generatePDF");
				sleeper(3000);
				sa.assertTrue(productCatalogPage.getTextOfProductCatalogPage("toastNotification").equals(productCatalogPage.getTextLanguage(LanguageCode, "daas_ui", "catalog.generate_recommendation.success")), "Toast Notification text does not match for generate recommendation");
				productCatalogPage.clickOnElementsOfProductCatalogPage("toastNotificationClose");

				// Test case 3 : This test case verifies generate XLSX recommendation
				productCatalogPage.clickOnElementsOfProductCatalogPage("generateRecommendation");
				productCatalogPage.clickOnElementsOfProductCatalogPage("generateXLSX");
				sleeper(3000);
				sa.assertTrue(productCatalogPage.getTextOfProductCatalogPage("toastNotification").equals(productCatalogPage.getTextLanguage(LanguageCode, "daas_ui", "catalog.generate_recommendation.success")), "Toast Notification text does not match for generate recommendation");
				productCatalogPage.clickOnElementsOfProductCatalogPage("toastNotificationClose");
			}
		sa.assertAll();
		LOGGER.info("All recommendation test-cases passed on Product Catalog page");
	}

	@Test(priority = 14, groups = { "REGRESSION", "SMOKE", "REGRESSION_CI", "SMOKE_CI", "PRODUCTION_CI", "PRODUCTION_SANITY" }, description = "Test Case ID : 280872")
	public final void verifyProductCatalogListPage() throws Exception {
		login("RESELLER_NEW_UI_EMAIL_US", "RESELLER_NEW_UI_PASSWORD_US");
		gotoProductCatalogTab();
		SoftAssert softAssert = new SoftAssert();
		ProductCatalogPage productCatalogPage = new ProductCatalogPage(PreDefinedActions.getDriver()).getInstance();
		LOGGER.info("Redirected to product catalog list page");
		waitForPageLoaded();
		productCatalogPage.clearFiltersOfProductCatalogListPage("clearfilter");
		softAssert.assertTrue(productCatalogPage.verifyElementsOfProductCatalogPage("listTable"), "Table on list page is not loaded successfully");
		softAssert.assertTrue(productCatalogPage.verifyElementsOfProductCatalogPage("nameHeader"), "Default column is not present on list page");
		softAssert.assertAll();
		LOGGER.info("Product catalog list page is loaded successfully");
	}

	@Test(priority = 15, groups = { "REGRESSION", "SMOKE", "REGRESSION_CI", "SMOKE_CI", "PRODUCTION_CI", "PRODUCTION_SANITY" }, description = "Test Case ID : 280874")
	public final void verifyProductCatalogDetailsPage() throws Exception {
		login("RESELLER_NEW_UI_EMAIL_US", "RESELLER_NEW_UI_PASSWORD_US");
		gotoProductCatalogTab();
		SoftAssert softAssert = new SoftAssert();
		ProductCatalogPage productCatalogPage = new ProductCatalogPage(PreDefinedActions.getDriver()).getInstance();
		LOGGER.info("Redirected to product catalog list page");
		waitForPageLoaded();
		productCatalogPage.waitForElementsOfProductCatalogPage("tableOverlay");
		productCatalogPage.clickByJavaScriptOnElementsOfProductCatalogPage("firstProdName");
		waitForPageLoaded();
		productCatalogPage.clearFiltersOfProductCatalogListPage("clearfilter");
		productCatalogPage.waitForElementsOfProductCatalogPage("tableOverlay");
		LOGGER.info("Redirected to details page");
		softAssert.assertTrue(productCatalogPage.verifyElementsOfProductCatalogPage("listTable"), "Table on details page is not loaded successfully");
		softAssert.assertTrue(productCatalogPage.verifyElementsOfProductCatalogPage("prodNameHeader"), "Default column is not present on details page");
		softAssert.assertAll();
		LOGGER.info("Product catalog list page is loaded successfully");
	}

	@Test(priority = 16, groups = { "REGRESSION", "SMOKE", "REGRESSION_CI", "SMOKE_CI" }, description = "Test Case ID : 280820")
	public final void verifyProductCatalogListPageDataReseller() throws Exception {
		login("RESELLER_NEW_UI_Companies_EMAIL_COMPANIES", "RESELLER_NEW_UI_Companies_PASSWORD_COMPANIES");
		String tenantID = getValueFromToken("tenant");
		String subID = getValueFromToken("sub");
		gotoProductCatalogTab();
		LOGGER.info("Redirected to Product Catalog list page");
		waitForPageLoaded();
		Assert.assertTrue(getStatusCode(getEnvironmentSpecificData(System.getProperty("environment"), "DAAS_API_BASE_URL_AUTH") + ProductCatalogVariables.SEARCHSERVICEAPI1 + tenantID + ProductCatalogVariables.SEARCHSERVICEAPI2 + subID + ProductCatalogVariables.SEARCHSERVICEAPI3, "", "GET", getEnvironment(environment)) == CommonVariables.CODEOK, "Search service failed for Product Catalog list page");
		LOGGER.info("Product Catalog list page is loaded successfully");
	}
}