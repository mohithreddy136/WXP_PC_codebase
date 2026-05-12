package com.testscripts.daasui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.json.simple.JSONObject;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.basesource.action.PreDefinedActions;
import com.basesource.action.SortAPIQuery;
import com.basetest.environments.SetTestEnvironments;
import com.daasui.constants.CommonVariables;
import com.daasui.constants.ConstantURL;
import com.daasui.constants.DashboardVariables;
import com.daasui.constants.LogsVariables;
import com.daasui.constants.WPTVariable;
import com.daasui.pages.CompaniesPage;
import com.daasui.pages.LogPage;
import com.daasui.pages.SettingsPage;
import com.daasui.pages.TableConfigurationPage;
import com.daasui.pages.WEPWPTPage;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class LogTest extends CommonTest {
	private static Logger LOGGER = LogManager.getLogger(LogTest.class);
	SoftAssert sa = new SoftAssert();

	@Test(priority = 1, groups = { "REGRESSION", "REGRESSION_CI", "STABILIZATION_STAGING" }, description = "Test Case 207310: [AUX][DaaS] Verify that Logs screen should have Logs heading")
	public final void verifyLogTitle() throws Exception {
		login("NEW_MSP_ADMIN_US_EMAIl", "NEW_MSP_ADMIN_US_PASSWORD");
		gotoLogTab();
		Thread.sleep(3000);
		LogPage logPage = new LogPage(PreDefinedActions.getDriver()).getInstance();

		logPage.waitForPageLoaded();
		if (toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, "NEW_MSP_ADMIN_US_EMAIl"))) {
			logPage.waitForElementsOfLogPage("logTitleOnBreadcrumbs");
			Assert.assertTrue(logPage.getTextOfLogPage("logTitleOnBreadcrumbs").equals(logPage.getTextLanguage(LanguageCode, "daas_ui", "pagetitle.logs")), "Logs title text is incorrect");
			LOGGER.info("Log title is matched with selected language");
		}else{
		logPage.waitForElementsOfLogPage("logTitle");
		Assert.assertTrue(logPage.getTextOfLogPage("logTitle").equals(logPage.getTextLanguage(LanguageCode, "daas_ui", "pagetitle.logs")), "Logs title text is incorrect");
		LOGGER.info("Log title is matched with selected language");
		}
	}

	@Test(priority = 2, groups = { "REGRESSION", "REGRESSION_CI","PENTEST" }, description = "Test Case 206921: [AUX][DaaS] Verify that IT admin Users can see Logs screen new UI\n" + "Test Case 223769: [AUX][DaaS] verify that only authenticated user role can able to see LOG page\r\n")
	public final void verifyITAdminCanSeeLogsPage() throws Exception {
		login("ITA_COMPANY_DETAILS_EMAIL", "ITA_COMPANY_DETAILS_PASSWORD");
		gotoLogTab();
		LogPage logPage = new LogPage(PreDefinedActions.getDriver()).getInstance();

		logPage.waitForPageLoaded();

		if (toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, "IT_ADMIN_COMPANY_USER_EMAIL_COMPANIES"))) {
			logPage.waitForElementsOfLogPage("logTitleOnBreadcrumbs");
			Assert.assertTrue(logPage.getTextOfLogPage("logTitleOnBreadcrumbs").equals(logPage.getTextLanguage(LanguageCode, "daas_ui", "pagetitle.logs")), "Logs title text is incorrect");
			LOGGER.info("Log title is matched with selected language");
		}else{
		logPage.waitForElementsOfLogPage("logTitle");
		Assert.assertTrue(logPage.getTextOfLogPage("logTitle").equals(logPage.getTextLanguage(LanguageCode, "daas_ui", "pagetitle.logs")), "Logs title text is incorrect");
		LOGGER.info("Log title is matched with selected language");
		}

		TableConfigurationPage tableConfigurationPage = new TableConfigurationPage(PreDefinedActions.getDriver()).getInstance();

		SoftAssert sa = new SoftAssert();
		logPage.clickOnElementsOfLogPage("tableConfigurationButton");
		int actualCount = tableConfigurationPage.getCountOfSelectedCheckBoxOnPopup("selectedCheckBoxesOnPopup");
		tableConfigurationPage.selectAllCheckboxOfPopup("columnCheckboxesOnPopup", "listOfColumnsOnPopup");
		tableConfigurationPage.clickOnElementsOfTableConfigurationPage("discardButton");
		logPage.clickOnElementsOfLogPage("tableConfigurationButton");
		int expectedCount = tableConfigurationPage.getCountOfSelectedCheckBoxOnPopup("selectedCheckBoxesOnPopup");
		sa.assertEquals(actualCount, expectedCount, "Discard functionality not working properly");
		tableConfigurationPage.clickOnElementsOfTableConfigurationPage("discardButton");
		sa.assertAll();
		LOGGER.info("IT admin can access Logs tab test-case passed");

	}

	@Test(priority = 3, groups = { "REGRESSION", "REGRESSION_CI", "STABILIZATION_STAGING" }, description = "Test Case 219282: [AUX][DaaS] Verify that Logs screen should support search on filters")
	public final void verifySearchOnLogsPage() throws Exception {
		login("NEW_MSP_ADMIN_US_EMAIl", "NEW_MSP_ADMIN_US_PASSWORD");
		gotoLogTab();
		LogPage logPage = new LogPage(PreDefinedActions.getDriver()).getInstance();

		TableConfigurationPage tableConfigurationPage = new TableConfigurationPage(PreDefinedActions.getDriver()).getInstance();

		SoftAssert sa = new SoftAssert();
		resetTableConfiguration();
		logPage.clickOnElementsOfLogPage("tableConfigurationButton");
		tableConfigurationPage.selectAllCheckboxOfPopup("columnCheckboxesOnPopup", "listOfColumnsOnPopup");
		tableConfigurationPage.clickOnElementsOfTableConfigurationPage("saveButton");
		sleeper(2000);

		// Test case 1 - Search on Description field
		logPage.waitForElementsOfLogPage("descriptionSearchBox");
		logPage.clickOnElementsOfLogPage("descriptionSearchBox");
		sa.assertTrue(logPage.verifySearchValueOnLogs(LanguageCode, "descriptionSearchBox", "test", "noLogsDisplayText", "descriptionList"), "Search not working for Description");
		// Test case 2 - Search on Type field
			logPage.clearAllFiltersOfLogsPage("clearSearchBoxFilter");
		logPage.clickOnElementsOfLogPage("typeBox");
		logPage.waitForElementsOfLogPage("dropdownCheckboxes");
		sa.assertTrue(logPage.verifyFilterSingleSelectOnLogs(LanguageCode, "dropdownCheckboxes", "dropdownElementListLabels", "typeList", "noLogsDisplayText"), "Drop down selection not working for Type on Logs list");

		// Test case 3 - Search on subtype field
		logPage.clearAllFiltersOfLogsPage("clearSelectionOfFilters");
		logPage.clickOnElementsOfLogPage("subTypeBox");
		logPage.waitForElementsOfLogPage("dropdownCheckboxes");
		sa.assertTrue(logPage.verifyFilterSingleSelectOnLogs(LanguageCode, "dropdownCheckboxes", "dropdownElementListLabels", "subTypeList", "noLogsDisplayText"), "Drop down selection not working for Source on Logs list");
		logPage.clearAllFiltersOfLogsPage("clearSelectionOfFilters");

		// Test case 4 - Search on Severity field
		logPage.waitForElementsOfLogPage("severityBox");
		logPage.clickOnElementsOfLogPage("severityBox");
		logPage.waitForElementsOfLogPage("dropdownCheckboxes");
		sa.assertTrue(logPage.verifyFilterSingleSelectOnLogs(LanguageCode, "dropdownCheckboxes", "dropdownElementListLabels", "severityList", "noLogsDisplayText"), "Drop down selection not working for Severity on Logs list");
		logPage.clearAllFiltersOfLogsPage("clearSelectionOfFilters");

		// Test case 5 - Search on Asset Name field
		logPage.clearAllFiltersOfLogsPage("clearSelectionOfFilters");
		logPage.waitForElementsOfLogPage("assetNameSearchBox");
		logPage.clickOnElementsOfLogPage("assetNameSearchBox");
		sa.assertTrue(logPage.verifySearchValueOnLogs(LanguageCode, "assetNameSearchBox", "DESK", "noLogsDisplayText", "assetNameSearchList"), "Search not working for DeviceName");

		// Test case 6 - Search on Asset Name field
		// commented due to bug in device serial number search
		// logPage.clearAllFiltersOfLogsPage("clearSelectionOfFilters");
		// logPage.waitForElementsOfLogPage("assetSerialNumberSearchBox");
		// logPage.clickOnElementsOfLogPage("assetSerialNumberSearchList");
		// sa.assertTrue(logPage.verifySearchValueOnLogs(LanguageCode, "assetSerialNumberSearchBox", "CN", "noLogsDisplayText", "assetSerialNumberSearchList"), "Search not working for
		// Device Serail Number");

		// Test case 7 - search on Asset field

		logPage.clearAllFiltersOfLogsPage("clearSelectionOfFilters");
		logPage.waitForElementsOfLogPage("assetTypeBox");
		logPage.clickOnElementsOfLogPage("assetTypeBox");
		logPage.waitForElementsOfLogPage("dropdownCheckboxes");
		sa.assertTrue(logPage.verifyFilterSingleSelectOnLogs(LanguageCode, "dropdownCheckboxes", "dropdownElementListLabels", "assetTypeList", "noLogsDisplayText"), "Drop down selection not working for Device Type on Logs list");

		logPage.clearAllFiltersOfLogsPage("clearSelectionOfFilters");

		sa.assertAll();
		LOGGER.info("Search filter functionality test-cases passed");

	}

	@Test(priority = 4, groups = { "REGRESSION", "REGRESSION_CI", "STABILIZATION_STAGING" }, description = "Test Case 207305: [AUX][DaaS] Verify that Logs screen should display full logs when expand option is clicked")
	public final void verifyExpandLogsOnLogsPage() throws Exception {
		login("NEW_MSP_ADMIN_US_EMAIl", "NEW_MSP_ADMIN_US_PASSWORD");
		gotoLogTab();
		LogPage logPage = new LogPage(PreDefinedActions.getDriver()).getInstance();

		logPage.waitForPageLoaded();
		if (toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, "MSP_ADMIN_US_EMAIl"))) {
			logPage.waitForElementsOfLogPage("logTitleOnBreadcrumbs");
			Assert.assertTrue(logPage.getTextOfLogPage("logTitleOnBreadcrumbs").equals(logPage.getTextLanguage(LanguageCode, "daas_ui", "pagetitle.logs")), "Logs title text is incorrect");
			LOGGER.info("Log title is matched with selected language");
		}else{
		logPage.waitForElementsOfLogPage("logTitle");
		Assert.assertTrue(logPage.getTextOfLogPage("logTitle").equals(logPage.getTextLanguage(LanguageCode, "daas_ui", "pagetitle.logs")), "Logs title text is incorrect");
		LOGGER.info("Log title is matched with selected language");
		}

		resetTableConfiguration();
		sleeper(2000);
			logPage.waitForElementsOfLogPage("firstCheckbox");
			logPage.mousehoverOnLogsPage("firstCheckbox");
			logPage.clickOnElementsOfLogPage("selectFirstCheckbox");
			logPage.waitForElementsOfLogPage("expandedLogDesc");
			Assert.assertTrue(logPage.verifyElementIsEnableOfLogPage("expandedLogDesc"),"Logs are not getting expanded");
		}

	@Test(priority = 5, groups = { "REGRESSION", "REGRESSION_CI", "STABILIZATION_STAGING" }, description = "Test Case 214412: [AUX][DaaS] Verify that Logs screen should store user selection of columns,Test Case 206918: [AUX][DaaS] Verify that Logs screen should have following column headers ")
	public final void verifySelectionStoredOnLogsPage() throws Exception {
		login("NEW_MSP_ADMIN_US_EMAIl", "NEW_MSP_ADMIN_US_PASSWORD");
		gotoLogTab();
		ArrayList<String> columnNameOnPopup;
		ArrayList<String> columnNamesOnPage;
		SoftAssert sa = new SoftAssert();
		LogPage logPage = new LogPage(PreDefinedActions.getDriver()).getInstance();
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		logPage.waitForPageLoaded();

		if (toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, "MSP_ADMIN_US_EMAIl"))) {
			logPage.waitForElementsOfLogPage("logTitleOnBreadcrumbs");
			Assert.assertTrue(logPage.getTextOfLogPage("logTitleOnBreadcrumbs").equals(logPage.getTextLanguage(LanguageCode, "daas_ui", "pagetitle.logs")), "Logs title text is incorrect");
			LOGGER.info("Log title is matched with selected language");
		}else{
		logPage.waitForElementsOfLogPage("logTitle");
		Assert.assertTrue(logPage.getTextOfLogPage("logTitle").equals(logPage.getTextLanguage(LanguageCode, "daas_ui", "pagetitle.logs")), "Logs title text is incorrect");
		}
		TableConfigurationPage tableConfigurationPage = new TableConfigurationPage(PreDefinedActions.getDriver()).getInstance();

		// Test Case1 - To verify functionality of Up arrow and Down arrow from table configuration
		logPage.clickOnElementsOfLogPage("tableConfigurationButton");
		tableConfigurationPage.clickOnElementsOfTableConfigurationPage("firstCheckBoxOnPopup");
		tableConfigurationPage.clickOnElementsOfTableConfigurationPage("downArrow");
		tableConfigurationPage.clickOnElementsOfTableConfigurationPage("priorityCheckBoxOfpopUp");
		tableConfigurationPage.clickOnElementsOfTableConfigurationPage("upArrow");
		columnNameOnPopup = tableConfigurationPage.getSequenceOfSelectedColumns("selectedCheckBoxesOnPopup", "listOfColumnsOnPopup");
		tableConfigurationPage.clickOnElementsOfTableConfigurationPage("saveButton");
		sleeper(3000);
		gotoCompaniesTab();

		if (toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, "NEW_MSP_ADMIN_US_EMAIl"))) {
			companiesPage.waitForElementsOfCompaniesPage("companiesTitleOnBreadcrumbs");
			Assert.assertTrue(companiesPage.getTextOfCompaniesPage("companiesTitleOnBreadcrumbs").equals(companiesPage.getTextLanguage(LanguageCode, "daas_ui", "pagetitle.companies")), "Companies page title did not match");
			LOGGER.info("Companies title matched");
		}else{
		sa.assertTrue(companiesPage.getTextOfCompaniesPage("companiesTitle").equals(companiesPage.getTextLanguage(LanguageCode, "daas_ui", "pagetitle.companies")), "Companies page title did not match");
		LOGGER.info("Companies title matched");
		}

		gotoLogTab();
		logPage.waitForPageLoaded();
		// Test Case 2 - To verify log selection stored for User
		columnNamesOnPage = logPage.getSequenceOfSelectedColumnsOnLogPage("listOfColumnsOnTable");
		sa.assertTrue(columnNameOnPopup.equals(columnNamesOnPage), "When we arrange a sequence of columns from configuration pop-up ,after coming back to Logs tab Sequence of column on logs page and table configuration doesn't match");
		logout();
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		gotoLogTab();
		sa.assertTrue(columnNameOnPopup.equals(columnNamesOnPage), "When we arrange a sequence of columns from configuration pop-up ,after coming back to Logs tab Sequence of column on logs page and table configuration doesn't match");
		sa.assertAll();
		LOGGER.info("Store column selection functionality test-case passed");
	}

	@Test(priority = 6, groups = { "REGRESSION", "REGRESSION_CI" }, invocationCount = 2, description = "Test Case 219053: [DaaS][UI]Verify that 25, 50, 100 options are available in pagination dropdown on Logs page", enabled = false)
	public final void verifyPaginationOnlogPage() throws Exception {
		login("NEW_MSP_ADMIN_US_EMAIl", "NEW_MSP_ADMIN_US_PASSWORD");
		gotoLogTab();
		LogPage logPage = new LogPage(PreDefinedActions.getDriver()).getInstance();

		SoftAssert sa = new SoftAssert();
		logPage.clearAllFiltersOfLogsPage("clearSelectionOfFilters");
		logPage.waitForElementsOfLogPage("tableOverlay");
		sleeper(2000);
		logPage.waitForElementsOfLogPage("paginationDropdownMenu");
		sa.assertTrue(logPage.verifyElementIsEnableOfLogPage("paginationDropdownMenu"), "Pagination Dropdown not availble");
		sa.assertTrue(logPage.verifyElementIsClickableOfLogPage("paginationDropdownMenu"), "Pagination Dropdown not clickable");
		sa.assertTrue(logPage.waitForElementsOfLogPage("navigationItemDiv"), "Navigation Item are not available");
		getPaginationInfo();
		LOGGER.info("get Pagination Information ");
		sa.assertTrue(logPage.verifyElementIsEnableOfLogPage("navigationItemActivePage"), "The active page navigation link is not disabled");
		sa.assertTrue(logPage.verifyTotalPageCountChangeAsSelectedOption(selectedOption, totalCount, lastPageNumber), "Total page count not change with selected option");
		if (!verifySelectedOptionForNewSelection(selectedOption, CommonVariables.SELETEDFIFTY)) {
			if (changeSelectedOption(totalCount, CommonVariables.SELETEDTWENTYFIVE)) {
				logPage.selectElementFromDropDownOfLogPage("paginationDropdownMenu", "paginationOptionList", Integer.toString(CommonVariables.SELETEDTWENTYFIVE));
				LOGGER.info("Change Selected option as " + CommonVariables.SELETEDTWENTYFIVE);
				logPage.waitForElementsOfLogPage("tableOverlay");
				getPaginationInfo();
				sa.assertTrue(logPage.verifyTotalPageCountChangeAsSelectedOption(selectedOption, totalCount, lastPageNumber), "Total page count not change with selected option");
				sa.assertTrue(logPage.verifyButtonStatusofPagination(activepageNumber, firstPageNumber, lastPageNumber, totalCount, previousButtonStatus, nextButtonStatus), "Button visibility status not as per expectation");
			} else {

				LOGGER.info("Selected user has less than " + CommonVariables.SELETEDTWENTYFIVE + " records");
			}
		} else {
			if (changeSelectedOption(totalCount, CommonVariables.SELETEDFIFTY)) {
				logPage.selectElementFromDropDownOfLogPage("paginationDropdownMenu", "paginationOptionList", Integer.toString(CommonVariables.SELETEDFIFTY));
				LOGGER.info("Change Selected option as " + CommonVariables.SELETEDFIFTY);
				logPage.waitForElementsOfLogPage("tableOverlay");
				getPaginationInfo();
				sa.assertTrue(logPage.verifyTotalPageCountChangeAsSelectedOption(selectedOption, totalCount, lastPageNumber), "Total page count not change with selected option");
				sa.assertTrue(logPage.verifyButtonStatusofPagination(activepageNumber, firstPageNumber, lastPageNumber, totalCount, previousButtonStatus, nextButtonStatus), "Button visibility status not as per expectation");
			} else {

				LOGGER.info("Selected user has less than " + CommonVariables.SELETEDFIFTY + " records");
			}
		}
		logPage.waitForElementsOfLogPage("tableOverlay");
		if (logPage.canClickNext(activepageNumber, lastPageNumber, nextButtonStatus)) {
			sa.assertTrue(logPage.getButtonEnabilityStatus("navigationItemNext"), "Naviagtion next button is not enable");
			logPage.waitForElementsOfLogPage("navigationItemNext");
			logPage.clickOnElementsOfLogPage("navigationItemNext");
			logPage.waitForElementsOfLogPage("tableOverlay");
			getPaginationInfo();
			logPage.waitForElementsOfLogPage("navigationItemPrevious");
			if (logPage.canClickPrevious(activepageNumber, firstPageNumber, previousButtonStatus)) {
				sa.assertTrue(logPage.getButtonEnabilityStatus("navigationItemPrevious"), "Naviagtion previous button is not enable");
				logPage.clickOnElementsOfLogPage("navigationItemPrevious");
			} else {
				LOGGER.info("Previous button is disabled");
			}
		} else if (logPage.canClickPrevious(activepageNumber, firstPageNumber, previousButtonStatus)) {
			sa.assertTrue(logPage.getButtonEnabilityStatus("navigationItemPrevious"), "Naviagtion previous button is not enable");
			logPage.waitForElementsOfLogPage("navigationItemPrevious");
			logPage.clickOnElementsOfLogPage("navigationItemPrevious");
			logPage.waitForElementsOfLogPage("tableOverlay");
			getPaginationInfo();
			logPage.waitForElementsOfLogPage("navigationItemNext");
			if (logPage.canClickNext(activepageNumber, lastPageNumber, nextButtonStatus)) {
				sa.assertTrue(logPage.getButtonEnabilityStatus("navigationItemNext"), "Naviagtion next button is not enable");
				logPage.clickOnElementsOfLogPage("navigationItemNext");
			} else {
				LOGGER.info("Next button is disabled");
			}
		} else {
			LOGGER.info("Previous and Next button both are disabled");
		}
		sa.assertAll();
		LOGGER.info("Verification of log page pagination done Successfully ");
	}

	// Marked as false since we are testing pagination related test case on companies page only
	@Test(priority = 7, groups = { "REGRESSION", "REGRESSION_CI" }, description = "Test Case 214922 ,Test Case 221541 ,Test Case 221534 ,Test Case 221542", enabled = false)
	public final void verifyPaginationDependOnTablesettingFilters() throws Exception {
		login("NEW_MSP_ADMIN_US_EMAIl", "NEW_MSP_ADMIN_US_PASSWORD");
		gotoLogTab();
		LogPage logPage = new LogPage(PreDefinedActions.getDriver()).getInstance();

		SoftAssert sa = new SoftAssert();
		TableConfigurationPage tableConfigurationPage = new TableConfigurationPage(PreDefinedActions.getDriver()).getInstance();

		resetTableConfiguration();
		logPage.clickOnElementsOfLogPage("tableConfigurationButton");
		tableConfigurationPage.selectAllCheckboxOfPopup("columnCheckboxesOnPopup", "listOfColumnsOnPopup");
		tableConfigurationPage.clickOnElementsOfTableConfigurationPage("saveButton");
		sleeper(2000);
		logPage.waitForElementsOfLogPage("paginationDropdownMenu");
		sa.assertTrue(logPage.verifyElementIsEnableOfLogPage("paginationDropdownMenu"), "Pagination Dropdown not availble");
		sa.assertTrue(logPage.verifyElementIsClickableOfLogPage("paginationDropdownMenu"), "Pagination Dropdown not clickable");
		sa.assertTrue(logPage.waitForElementsOfLogPage("navigationItemDiv"), "Navigation Item are not available");
		logPage.clickOnElementsOfLogPage("subTypeBox");
		logPage.waitForElementsOfLogPage("dropdownCheckboxes");
		sa.assertTrue(logPage.verifyFilterSingleSelectOnLogs(LanguageCode, "dropdownCheckboxes", "dropdownElementListLabels", "subTypeList", "noLogsDisplayText"), "Drop down selection not working for Source on Logs list");
		logPage.waitForElementsOfLogPage("paginationDropdownMenu");
		getPaginationInfo();
		sa.assertTrue(logPage.verifyElementIsEnableOfLogPage("navigationItemActivePage"), "The active page navigation link is not disabled");
		if (logPage.canClickNext(activepageNumber, lastPageNumber, nextButtonStatus)) {
			sa.assertTrue(logPage.getButtonEnabilityStatus("navigationItemNext"), "Naviagtion next button is not enable");
			logPage.waitForElementsOfLogPage("navigationItemNext");
			logPage.clickOnElementsOfLogPage("navigationItemNext");
			logPage.waitForElementsOfLogPage("tableOverlay");
			logPage.scrollToTop();
			logPage.clickOnElementsOfLogPage("tableConfigurationButton");
			tableConfigurationPage.clickOnElementsOfTableConfigurationPage("subTypeCheckBox");
			tableConfigurationPage.clickOnElementsOfTableConfigurationPage("downArrow");
			tableConfigurationPage.clickOnElementsOfTableConfigurationPage("subTypeCheckBox");
			tableConfigurationPage.clickOnElementsOfTableConfigurationPage("saveButton");
			logPage.waitForElementsOfLogPage("tableOverlay");
			sa.assertEquals(Integer.parseInt(logPage.getTextOfLogPage("navigationItemActivePage")), CommonVariables.PAGESECOND, "After changing sequneces of filter column pagination navigate to first page");
			logPage.scrollToTop();
			logPage.clickOnElementsOfLogPage("tableConfigurationButton");
			tableConfigurationPage.clickOnElementsOfTableConfigurationPage("subTypeCheckBox");
			tableConfigurationPage.clickOnElementsOfTableConfigurationPage("saveButton");
			logPage.waitForElementsOfLogPage("tableOverlay");
			sa.assertEquals(Integer.parseInt(logPage.getTextOfLogPage("navigationItemActivePage")), CommonVariables.PAGEFRIST, "After removal of filter column pagination not navigate to first page");
		} else {
			LOGGER.info("Next button is disabled");
		}
		logPage.waitForElementsOfLogPage("typeBox");
		getPaginationInfo();
		sa.assertTrue(logPage.verifyElementIsEnableOfLogPage("navigationItemActivePage"), "The active page navigation link is not disabled");
		if (logPage.canClickNext(activepageNumber, lastPageNumber, nextButtonStatus)) {
			sa.assertTrue(logPage.getButtonEnabilityStatus("navigationItemNext"), "Naviagtion next button is not enable");
			logPage.waitForElementsOfLogPage("navigationItemNext");
			logPage.clickOnElementsOfLogPage("navigationItemNext");
			logPage.waitForElementsOfLogPage("tableOverlay");
			logPage.scrollToTop();
			logPage.clickOnElementsOfLogPage("tableConfigurationButton");
			tableConfigurationPage.clickOnElementsOfTableConfigurationPage("typeCheckBox");
			tableConfigurationPage.clickOnElementsOfTableConfigurationPage("saveButton");
			logPage.waitForElementsOfLogPage("tableOverlay");
			sa.assertEquals(Integer.parseInt(logPage.getTextOfLogPage("navigationItemActivePage")), CommonVariables.PAGESECOND, "After removal of un-filter column pagination navigate to first page");
		} else {
			LOGGER.info("Next button is disabled");
		}
		sa.assertAll();
		LOGGER.info("Verification of log page pagination Depend On Table setting and Filters selection done Successfully ");
	}

	@Test(priority = 8, groups = { "REGRESSION", "REGRESSION_CI" }, description = "Task 244359: [Logs][Selenium]Added more test case coverage for Logs")
	public final void verifyResellerAdminCanSeeLogsPage() throws Exception {
		login("RESELLER_NEW_UI_Companies_EMAIL_COMPANIES", "RESELLER_NEW_UI_Companies_PASSWORD_COMPANIES");
		gotoLogTab();
		SoftAssert sa = new SoftAssert();
		LogPage logPage = new LogPage(PreDefinedActions.getDriver()).getInstance();
		logPage.waitForPageLoaded();
		if (toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, "RESELLER_PARTNERS_EMAIL"))) {
			logPage.waitForElementsOfLogPage("logTitleOnBreadcrumbs");
			Assert.assertTrue(logPage.getTextOfLogPage("logTitleOnBreadcrumbs").equals(logPage.getTextLanguage(LanguageCode, "daas_ui", "pagetitle.logs")), "Logs title text is incorrect");
			LOGGER.info("Log title is matched with selected language");
		}else{
			logPage.waitForElementsOfLogPage("logTitle");
			Assert.assertTrue(logPage.getTextOfLogPage("logTitle").equals(logPage.getTextLanguage(LanguageCode, "daas_ui", "pagetitle.logs")), "Logs title text is incorrect");
			LOGGER.info("Log title is matched with selected language");	
		}
		resetTableConfiguration();
		sa.assertAll();
		LOGGER.info("All tests for reseller passed");
	}

	@Test(priority = 9, groups = { "REGRESSION", "REGRESSION_CI", "STABILIZATION_STAGING" }, description = "Test Case 220706: [AUX][DaaS] verify that asset type Drop down should not contain \"mini_pc\" checkbox option",enabled = false)
	public final void verifyAssetTypeOptions() throws Exception {
		if (!LanguageCode.equalsIgnoreCase("en")) {
			throw new SkipException("this test case execute only on english language");
		}
		login("NEW_MSP_ADMIN_US_EMAIl", "NEW_MSP_ADMIN_US_PASSWORD");
		gotoLogTab();

		LogPage logPage = new LogPage(PreDefinedActions.getDriver()).getInstance();

		TableConfigurationPage tableConfigurationPage = new TableConfigurationPage(PreDefinedActions.getDriver()).getInstance();

		SoftAssert sa = new SoftAssert();
		resetTableConfiguration();
		logPage.clickOnElementsOfLogPage("tableConfigurationButton");
		tableConfigurationPage.selectAllCheckboxOfPopup("columnCheckboxesOnPopup", "listOfColumnsOnPopup");
		tableConfigurationPage.clickOnElementsOfTableConfigurationPage("saveButton");
		logPage.waitForElementsOfLogPage("tableOverlay");
		String mspAuthToken = getTokenFromLocalStorage("dui_token_e");
		RestAssured.baseURI = getEnvironment(System.getProperty("environment"));
		RequestSpecification httpRequest = RestAssured.given().contentType(ContentType.JSON).header("authorization", "Bearer " + mspAuthToken);
		Response response = httpRequest.get(LogsVariables.UNIQUEDEVICETYPEAPI);
		Assert.assertEquals(response.statusCode(), CommonVariables.CODEOK, "deviceType Api status code not matched");
		ArrayList<String> deviceTypeListLabelsWiaApi = new ArrayList<>(Arrays.asList(response.jsonPath().getString("").toLowerCase().replaceAll("\\]|\\[|\\s+|-|_", "").trim().split(",")));
		Collections.sort(deviceTypeListLabelsWiaApi);
		
		// to verify asset type has on expected column names
		logPage.clearAllFiltersOfLogsPage("clearSelectionOfFilters");
		logPage.waitForElementsOfLogPage("assetTypeBox");
		logPage.clickOnElementsOfLogPage("assetTypeBox");
		logPage.waitForElementsOfLogPage("dropdownCheckboxes");
		sa.assertTrue(logPage.verifyDropdownOptionOnLogPage("dropdownElementListLabels", deviceTypeListLabelsWiaApi), "device Type drop down options are not matched");
		logPage.clearAllFiltersOfLogsPage("clearSelectionOfFilters");

		sa.assertAll();
		LOGGER.info("Search filter functionality test-cases passed");

	}

	@Test(priority = 10, enabled = false, groups = { "REGRESSION", "REGRESSION_CI", "STABILIZATION_STAGING" }, description = "Test Case 220706: [AUX][DaaS] verify that asset type Drop down should not contain \"mini_pc\" checkbox option")
	public final void verifySortingOnLogPage() throws Exception {
		if (!LanguageCode.equalsIgnoreCase("en")) {
			throw new SkipException("this test case execute only on english language");
		}
		login("NEW_MSP_ADMIN_US_EMAIl", "NEW_MSP_ADMIN_US_PASSWORD");
		gotoLogTab();
		String mainStringQ;
		String[] orderSort = SortAPIQuery.ORDERSORT;
		String jsonStringQAll = SortAPIQuery.JSONQALlLOGS;
		String jsonStringQCreated = SortAPIQuery.JSONQCREATEDLOG;
		String[] columnNames = SortAPIQuery.COLUMNNAMESONLOG;
		String[] columnSortLocators = SortAPIQuery.COLUMNNSORTLOCATORSONLOG;
		String[] columnListLocators = SortAPIQuery.COLUMNNLISTLOCATORSONLOG;
		LogPage logPage = new LogPage(PreDefinedActions.getDriver()).getInstance();

		SoftAssert sa = new SoftAssert();
		TableConfigurationPage tableConfigurationPage = new TableConfigurationPage(PreDefinedActions.getDriver()).getInstance();

		resetTableConfiguration();
		logPage.clickOnElementsOfLogPage("tableConfigurationButton");
		tableConfigurationPage.selectAllCheckboxOfPopup("columnCheckboxesOnPopup", "listOfColumnsOnPopup");
		tableConfigurationPage.clickOnElementsOfTableConfigurationPage("saveButton");
		logPage.waitForElementsOfLogPage("tableOverlay");
		logPage.waitForElementsOfLogPage("paginationDropdownMenu");
		sa.assertTrue(logPage.verifyElementIsEnableOfLogPage("paginationDropdownMenu"), "Pagination Dropdown not availble");
		sa.assertTrue(logPage.verifyElementIsClickableOfLogPage("paginationDropdownMenu"), "Pagination Dropdown not clickable");
		sa.assertTrue(logPage.waitForElementsOfLogPage("navigationItemDiv"), "Navigation Item are not available");
		getPaginationInfo();
		LOGGER.info("get Pagination Information ");
		for (String orderFlag : orderSort) {
			boolean sortFlag = false;
			if (orderFlag.equalsIgnoreCase("ASC")) {
				sortFlag = true;
			} else {
				sortFlag = false;
			}
			for (int columnCounter = 0; columnCounter < columnNames.length; columnCounter++) {
				if ((columnCounter >= 6 && columnCounter <= 9)) {
					continue; // this block we will remove after bug fix
				}
				if (selectedOption == CommonVariables.SELETEDHUNDRED) {
					jsonStringQAll = replceAPIStringValue(jsonStringQAll, SortAPIQuery.SIZEKEY, Integer.toString(CommonVariables.SELETEDHUNDRED));
					jsonStringQAll = replceAPISortKey(jsonStringQAll, columnNames[columnCounter]);
					jsonStringQCreated = replceAPIStringValue(jsonStringQCreated, SortAPIQuery.SIZEKEY, Integer.toString(CommonVariables.SELETEDHUNDRED));
				} else if ((totalCount > CommonVariables.SELETEDHUNDRED) && ((selectedOption == CommonVariables.SELETEDTWENTYFIVE) || (selectedOption == CommonVariables.SELETEDFIFTY))) {
					sleeper(1000);
					logPage.selectElementFromDropDownOfLogPage("paginationDropdownMenu", "paginationOptionList", Integer.toString(CommonVariables.SELETEDHUNDRED));
					selectedOption = CommonVariables.SELETEDHUNDRED;
					logPage.waitForElementsOfLogPage("tableOverlay");
					LOGGER.info("Change Selected option as " + CommonVariables.SELETEDHUNDRED);
					jsonStringQAll = replceAPIStringValue(jsonStringQAll, SortAPIQuery.SIZEKEY, Integer.toString(CommonVariables.SELETEDHUNDRED));
					jsonStringQAll = replceAPISortKey(jsonStringQAll, columnNames[columnCounter]);
					jsonStringQCreated = replceAPIStringValue(jsonStringQCreated, SortAPIQuery.SIZEKEY, Integer.toString(CommonVariables.SELETEDHUNDRED));
				} else if ((totalCount > CommonVariables.SELETEDFIFTY) && (selectedOption == CommonVariables.SELETEDTWENTYFIVE)) {
					logPage.selectElementFromDropDownOfLogPage("paginationDropdownMenu", "paginationOptionList", "50");
					selectedOption = CommonVariables.SELETEDFIFTY;
					logPage.waitForElementsOfLogPage("tableOverlay");
					LOGGER.info("Change Selected option as " + CommonVariables.SELETEDFIFTY);
					jsonStringQAll = replceAPIStringValue(jsonStringQAll, SortAPIQuery.SIZEKEY, Integer.toString(CommonVariables.SELETEDFIFTY));
					jsonStringQAll = replceAPISortKey(jsonStringQAll, columnNames[columnCounter]);
					jsonStringQCreated = replceAPIStringValue(jsonStringQCreated, SortAPIQuery.SIZEKEY, Integer.toString(CommonVariables.SELETEDFIFTY));
				} else {
					selectedOption = CommonVariables.SELETEDTWENTYFIVE;
					jsonStringQAll = replceAPIStringValue(jsonStringQAll, SortAPIQuery.SIZEKEY, Integer.toString(CommonVariables.SELETEDTWENTYFIVE));
					jsonStringQAll = replceAPISortKey(jsonStringQAll, columnNames[columnCounter]);
					jsonStringQCreated = replceAPIStringValue(jsonStringQCreated, SortAPIQuery.SIZEKEY, Integer.toString(CommonVariables.SELETEDTWENTYFIVE));
				}
				String mspAuthToken = getTokenFromLocalStorage("dui_token_e");
				if (columnNames[columnCounter].equals("createdAt")) {
					jsonStringQCreated = replceAPIStringValue(jsonStringQCreated, SortAPIQuery.ORDERKEY, "\\\"" + orderFlag + "\\\"");
					mainStringQ = jsonStringQCreated;
				} else {
					jsonStringQAll = replceAPIStringValue(jsonStringQAll, SortAPIQuery.ORDERKEY, "\\\"" + orderFlag + "\\\"");
					mainStringQ = jsonStringQAll;
				}

				JSONObject jsonString = (JSONObject) parser.parse(mainStringQ);
				JSONObject jsonAuthToken = (JSONObject) parser.parse(authTokenParse(mspAuthToken));
				String tenantID = jsonAuthToken.get("tenant").toString();
				Assert.assertFalse(mspAuthToken.isEmpty(), "Getting Cookies as null or empty please use proper key for Cookies");
				Assert.assertFalse(mspAuthToken == null, "Getting Cookies as null or empty please use proper key for Cookies");
				sleeper(1000);
				logPage.scrollOnLogPage(columnSortLocators[columnCounter]);
				sleeper(2000);
				logPage.scrollToTop();
				logPage.waitForElementsOfLogPage(columnSortLocators[columnCounter]);
				logPage.clickSortColumnOfLogPage(columnSortLocators[columnCounter], sortFlag);
				// Storing all UI list of record into variable
				List<String> uiRecordList = logPage.getAllRecordListOfLogPage(columnListLocators[columnCounter]);

				RestAssured.baseURI = logPage.getSearchServiceApi(System.getProperty("environment"));
				RequestSpecification httpRequest = RestAssured.given().contentType(ContentType.JSON).body(jsonString.toJSONString()).header("authorization", "Bearer " + mspAuthToken);
				Response response = httpRequest.post(LogsVariables.MULTITENENNTSEARCHPRE + tenantID + LogsVariables.MULTITENENNTSEARCHPOST);
				Assert.assertEquals(response.statusCode(), CommonVariables.CODEOK, " Api status code not matched for this column : " + columnNames[columnCounter]);
				List<String> listOfRecordsFromAPI = getListFromAPIKEY(LogsVariables.LOGUSERNAMEKEY + columnNames[columnCounter], response.getBody().asString());
				sa.assertTrue(verifySorting(listOfRecordsFromAPI, sortFlag), "Sorting with API is not  working for: " + columnNames[columnCounter] + "  for order :  " + orderFlag);
				if (columnNames[columnCounter].equals("createdAt")) {
					List<String> convertedDateList = getConvertedDate(listOfRecordsFromAPI);
					sa.assertTrue(convertedDateList.equals(uiRecordList), "Sorting with UI is not working for : " + columnNames[columnCounter] + "  for order :  " + orderFlag);
				} else {
					sa.assertTrue(verifySorting(uiRecordList, sortFlag), "Sorting with UI is not working for : " + columnNames[columnCounter] + "  for order :  " + orderFlag);
				}
				LOGGER.info("We have sorted Column :  " + columnNames[columnCounter] + "  for order :  " + orderFlag);
				sleeper(1000);
			}
		}
		sa.assertAll();
		LOGGER.info("verify Sorting On LogPage functionality test-cases passed");
	}

	@Test(priority = 11, groups = { "REGRESSION", "REGRESSION_CI", "PRODUCTION_SANITY", "STABILIZATION_STAGING" }, description = "Task 244359: [Logs][Selenium]Added more test case coverage for Logs")
	public final void verifyUserLinksOnLogsPage() throws Exception {
		login("NEW_MSP_ADMIN_US_EMAIl", "NEW_MSP_ADMIN_US_PASSWORD");
		gotoLogTab();
		SoftAssert sa = new SoftAssert();
		LogPage logPage = new LogPage(PreDefinedActions.getDriver()).getInstance();

		resetTableConfiguration();
		// Storing all UI list of record into variable
		logPage.scrollOnLogPage("userColumn");
		List<WebElement> uiRecordList = logPage.getElementsOfLogPage("userList");
		logPage.clickElementsOfLogPage(uiRecordList);
		String url = logPage.getUrlOfCurrentPage();
		if (url.contains("users")) {
			sa.assertTrue(url.contains("users"), "Page not redirected to users");
		} else if (url.contains("team")) {
			{
				sa.assertTrue(url.contains("team"), "Page not redirected to users");
			}
		}

		sa.assertAll();
		LOGGER.info("All test cases of User Links on Logs page passed");
	}

	@Test(priority = 12, groups = { "REGRESSION", "REGRESSION_CI", "STABILIZATION_STAGING" }, description = "Task 244359: [Logs][Selenium]Added more test case coverage for Logs",enabled=false)
	public final void verifyDeviceLinksOnLogsPage() throws Exception {
		login("NEW_MSP_ADMIN_US_EMAIl", "NEW_MSP_ADMIN_US_PASSWORD");
		gotoLogTab();
		SoftAssert sa = new SoftAssert();
		LogPage logPage = new LogPage(PreDefinedActions.getDriver()).getInstance();

		resetTableConfiguration();
		// Storing all UI list of record into variable
		List<WebElement> uiRecordList = logPage.getElementsOfLogPage("deviceList");
		logPage.scrollOnLogPage("deviceColumnName");
		logPage.clickElementsOfLogPage(uiRecordList);
		waitForPageLoaded();
		String url = logPage.getUrlOfCurrentPage();
		sa.assertTrue(url.contains("devices"), "Page not redirected to Devices Page");
		sa.assertAll();
		LOGGER.info("All test cases of Device Links on Logs page passed");
	}

	/**
	 * This method will verify the table configuration test cases of LOGS list page
	 */
	@Test(priority = 13, description = "NFR 242585", groups = { "REGRESSION", "SMOKE", "REGRESSION_CI", "SMOKE_CI", "STABILIZATION_STAGING" })
	public final void tableConfigurationTestCases() throws Exception {
		login("NEW_MSP_ADMIN_US_EMAIl", "NEW_MSP_ADMIN_US_PASSWORD");
		String tenantID = getValueFromToken("tenant");
		gotoLogTab();
		waitForPageLoaded();
		// verifying search service api
		Assert.assertTrue(getStatusCode(getSearchServiceApi(environment) + CommonVariables.DETAILSSEARCHSERVICEAPI + tenantID + CommonVariables.DETAILSSEARCHSERVICEAPI2, LogsVariables.SEARCHSERVICEBODYMSP, "POST", getEnvironment(environment)) == CommonVariables.CODEOK, "Search service failed for logs list page");
		ArrayList<String> columnName = new ArrayList<String>(Arrays.asList(getTextLanguage(LanguageCode, "daas_ui", "logs.date_time"), getTextLanguage(LanguageCode, "daas_ui", "logs.type"), getTextLanguage(LanguageCode, "daas_ui", "logs.source"), getTextLanguage(LanguageCode, "daas_ui", "logs.description"), getTextLanguage(LanguageCode, "daas_ui", "logs.company"), getTextLanguage(LanguageCode, "daas_ui", "logs.level"), getTextLanguage(LanguageCode, "daas_ui", "incidents.deviceName"),
				getTextLanguage(LanguageCode, "daas_ui", "incident.details.device_serial"), getTextLanguage(LanguageCode, "daas_ui", "logs.device.type"), getTextLanguage(LanguageCode, "daas_ui", "logs.user.name")));
		ArrayList<String> checkboxValue = new ArrayList<String>(Arrays.asList("true", "true", "true", "true", "true", "true", "false", "false", "false", "false"));
		ArrayList<String> date = new ArrayList<String>(Arrays.asList(getTextLanguage(LanguageCode, "daas_ui", "logs.date_time")));
		verifyTableConfigurationTests(columnName, checkboxValue, date);
	}

	//Disabling the test case due to Bug 1211707: [Techpulse][Automation] [USProd]>>verifyLogListPage
	@Test(priority = 14, groups = { "REGRESSION", "SMOKE", "REGRESSION_CI", "SMOKE_CI", "PRODUCTION_CI", "PRODUCTION_SANITY" }, description = "Test Case ID : 280829",enabled=false)
	public final void verifyLogListPage() throws Exception {
		login("NEW_MSP_ADMIN_US_EMAIl", "NEW_MSP_ADMIN_US_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		LogPage logPage = new LogPage(PreDefinedActions.getDriver()).getInstance();
		gotoLogTab();
		LOGGER.info("Redirected to logs list page");
		waitForPageLoaded();
		logPage.clearFiltersOfLogsListPage("clearfilter");
		softAssert.assertTrue(logPage.verifyElementsOfLogPage("listTable"), "Table on logs list page is not loaded successfully");
		softAssert.assertTrue(logPage.verifyElementsOfLogPage("dateAndTimeHeader"), "Default column is not present on logs list page");
		softAssert.assertAll();
		LOGGER.info("Logs list page is loaded successfully");
	}

	@Test(priority = 15, groups = { "REGRESSION", "SMOKE", "REGRESSION_CI", "SMOKE_CI" }, description = "Test Case ID : 280820")
	public final void verifyLogsListPageDataMSP() throws Exception {
		login("NEW_MSP_ADMIN_US_EMAIl", "NEW_MSP_ADMIN_US_PASSWORD");
		String tenantID = getValueFromToken("tenant");
		gotoLogTab();
		LOGGER.info("Redirected to logs list page");
		waitForPageLoaded();
		Assert.assertTrue(getStatusCode(getSearchServiceApi(environment) + CommonVariables.DETAILSSEARCHSERVICEAPI + tenantID + CommonVariables.DETAILSSEARCHSERVICEAPI2, LogsVariables.SEARCHSERVICEBODYMSP, "POST", getEnvironment(environment)) == CommonVariables.CODEOK, "Search service failed for logs list page for MSP");
		LOGGER.info("logs list page is loaded successfully for MSP");
	}

	@Test(priority = 16, groups = { "REGRESSION", "SMOKE", "REGRESSION_CI", "SMOKE_CI" }, description = "Test Case ID : 280820")
	public final void verifyLogsListPageDataReseller() throws Exception {
		
		login("RESELLER_NEW_UI_Companies_EMAIL_COMPANIES", "RESELLER_NEW_UI_Companies_PASSWORD_COMPANIES");
		String tenantID = getValueFromToken("tenant");
		gotoLogTab();
		LOGGER.info("Redirected to logs list page");
		waitForPageLoaded();
		Assert.assertTrue(getStatusCode(getSearchServiceApi(environment) + CommonVariables.DETAILSSEARCHSERVICEAPI + tenantID + CommonVariables.DETAILSSEARCHSERVICEAPI2, LogsVariables.SEARCHSERVICEBODYRESELLER, "POST", getEnvironment(environment)) == CommonVariables.CODEOK, "Search service failed for logs list page for Reseller");
		LOGGER.info("logs list page is loaded successfully for Reseller");
	}
	/**
	 * This method will verify the export button and download logs of logs list page
	 * @throws Exception 
	 */
	@Test(priority = 17, groups = { "REGRESSION", "SMOKE", "REGRESSION_CI", "SMOKE_CI" }, description = "NFR STORY 343963")
	public final void verifyExportButtonInLogListPage() throws Exception
	{
		login("NEW_MSP_ADMIN_US_EMAIl", "NEW_MSP_ADMIN_US_PASSWORD");
		LogPage logPage = new LogPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		gotoLogTab();
		resetTableConfiguration();
		logPage.waitForElementsOfLogPage("selectAllCheckbox");
		logPage.clickByJavaScriptOnLogPage("selectAllCheckbox");
		logPage.verifyElementsOfLogPage("exportButton");
		softAssert.assertTrue(logPage.getTextOfLogPage("exportButton").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "assets.export.btn_export")), "Export button is not available");
		logPage.waitForElementsOfLogPage("exportButton");
		logPage.clickByJavaScriptOnLogPage("exportButton");
		LOGGER.info("User is able to click Export button");
		sleeper(2000);
		softAssert.assertTrue(logPage.getTextOfLogPage("exportNotification").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "assets.export.export_in_progress")), "Unable to export logs.");
		logPage.waitUntilElementIsInvisibleOfLogPage("exportNotification");
		logPage.waitForElementsOfLogPage("notificationButtonforlogs");
			logPage.clickOnElementsOfLogPage("notificationButtonforlogs");
			logPage.waitForElementsOfLogPage("firstNotification");
			logPage.mousehoverOnLogsPage("firstNotification");
			logPage.waitForElementsOfLogPage("hamburgerMenuOnNotification");
			logPage.clickOnElementsOfLogPage("hamburgerMenuOnNotification");
			//logPage.clickOnElementsOfLogPage("downloadLogsFile");
		softAssert.assertAll();
		LOGGER.info("User is able to download logs file successfully");
	}
	
	/**
	 * This test case validates the context sensitive help links on Logs screen
	 *
	 * @throws Exception
	 */
	@Test(priority = 18, groups = { "REGRESSIONSETTINGS2", "SMOKE", "REGRESSION_CI",
	"SMOKE_CI" }, description = "Test Case ID : 891080")
	public final void verifyOnlineHelpLinksOnLogs() throws Exception {
		login("ITA_COMPANY_DETAILS_EMAIL", "ITA_COMPANY_DETAILS_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();

		// Verify help and support link for Logs tab
		waitForPageLoaded();
		gotoLogCompanyUserTab();
		LOGGER.info("Redirected to Logs tab");
		settingsPage.waitForElementsOfSettingsPage("helpAndSupportIcon");
		settingsPage.clickOnElementsOfSettingsPage("helpAndSupportIcon");
		softAssert.assertTrue(
				settingsPage.matchTextOfSettingsPage("help&SupportLink",
						getTextLanguage(LanguageCode, "daas_ui", "settings.help_and_support.title")),
				"Help & Support link text did not match on Logs tab");
		settingsPage.clickOnElementsOfSettingsPage("help&SupportLink");
		LOGGER.info("Clicked on Help & Support link from Logs tab");
		waitForPageLoaded();
		softAssert.assertTrue(settingsPage.getTextOfSettingsPage("helpAndSupportTitleTextOnBreadcrumbs").equals(settingsPage.getTextLanguage(LanguageCode, "daas_ui", "sidemenu.support")),
				"User not redirected to Help & Support tab after clicking on Help & Support link from Logs tab");
		gotoLogCompanyUserTab();

		// Verify overview link for Logs tab
		waitForPageLoaded();
		LOGGER.info("Redirected to Logs tab");
		settingsPage.waitForElementsOfSettingsPage("helpAndSupportIcon");
		settingsPage.clickOnElementsOfSettingsPage("helpAndSupportIcon");
		softAssert.assertTrue(
				settingsPage.matchTextOfSettingsPage("logsOverviewLink",
						getTextLanguage(LanguageCode, "daas_ui", "sidemenu.logs.overview")),
				"Logs - Overview link text did not match on Logs tab");
		settingsPage.clickOnElementsOfSettingsPage("logsOverviewLink");
		LOGGER.info("Clicked on overview link from Logs tab");
		settingsPage.switchToDifferentTab();
		sleeper(4000);// Url takes time to load
		softAssert.assertTrue(settingsPage.getUrlOfCurrentPage().contains(ConstantURL.KNOWLEDGE_BASE),
				"User not redirected to Online help section after clicking on Logs - Overview link from Logs tab");
		softAssert.assertTrue(settingsPage.getUrlOfCurrentPage().contains(ConstantURL.LANGUAGE_PARAMETER),
				"Language parameter not added in Service now URL");
		settingsPage.switchBackToPreviousTab();

		softAssert.assertAll();
		LOGGER.info("Test case to verify context sensitive links on Logs passed successfully.");
	}
	
	
	
	
	@Test(priority = 19, groups = {"REGRESSION", "REGRESSION_CI" }, description = "Test Case 1039798,1040126,1040275,1039801", enabled = true)
	public final void verifyImportAndAddLogs() throws Exception {

		login("NEW_MSP_ADMIN_US_EMAIl", "NEW_MSP_ADMIN_US_PASSWORD");

		gotoLogTab();
		LogPage logPage = new LogPage(PreDefinedActions.getDriver()).getInstance();

		TableConfigurationPage tableConfigurationPage = new TableConfigurationPage(PreDefinedActions.getDriver())
				.getInstance();

		SoftAssert sa = new SoftAssert();
		resetTableConfiguration();
		logPage.clickOnElementsOfLogPage("tableConfigurationButton");
		tableConfigurationPage.selectAllCheckboxOfPopup("columnCheckboxesOnPopup", "listOfColumnsOnPopup");
		tableConfigurationPage.clickOnElementsOfTableConfigurationPage("saveButton");
		sleeper(2000);

		// Test case 1 - Search on Description field
		logPage.waitForElementsOfLogPage("descriptionSearchBox");
		logPage.clickOnElementsOfLogPage("descriptionSearchBox");
		logPage.enterTextForLogPage("descriptionSearchBox",
				getEnvironmentSpecificData(System.getProperty("environment"), "IMPORT_COUNT_DESCRIPTION_LOG_MESSAGE"));
		logPage.pressKey(Keys.ENTER);

		logPage.waitForElementsOfLogPage("logsPageDescriptionFirstRow");
		sleeper(3000);

		sa.assertTrue(
				logPage.getTextOfLogPage("logsPageDescriptionFirstRow")
						.contains(getEnvironmentSpecificData(System.getProperty("environment"),
								"IMPORT_COUNT_DESCRIPTION_LOG_MESSAGE")),
				"Description did not match :" + logPage.getTextOfLogPage("logsPageDescriptionFirstRow"));
		sa.assertTrue(
				logPage.getTextOfLogPage("logsPageTypeFirstRow")
						.contains(getEnvironmentSpecificData(System.getProperty("environment"), "TYPE_LOG_VALUE")),
				"Description did not match :" + logPage.getTextOfLogPage("logsPageTypeFirstRow"));
		sa.assertTrue(
				logPage.getTextOfLogPage("logsPageSubTypeFirstRow").contains(
						getEnvironmentSpecificData(System.getProperty("environment"), "IMPORT_SUBTYPE_LOG_VALUE")),
				"Description did not match :" + logPage.getTextOfLogPage("logsPageSubTypeFirstRow"));
		logPage.scrollOnLogPage("logsPageLevelFirstRow");
		sa.assertTrue(
				logPage.getTextOfLogPage("logsPageLevelFirstRow")
						.contains(getEnvironmentSpecificData(System.getProperty("environment"), "LEVEL_LOG_VALUE")),
				"Description did not match :" + logPage.getTextOfLogPage("logsPageLevelFirstRow"));

		logPage.waitForElementsOfLogPage("clearSelectionButton");
		logPage.clickByJavaScriptOnLogPage("clearSelectionButton");
		LOGGER.info("Import Logs Message verified");
		

		// Add Log Verification
		
		gotoHelpAndSupportTab();//add for reloading of logs table data
        gotoLogTab();
        logPage.waitForElementsOfLogPage("logsPageDescriptionFirstRow");
		logPage.clickOnElementsOfLogPage("descriptionSearchBox");
		logPage.enterTextForLogPage("descriptionSearchBox",
				getEnvironmentSpecificData(System.getProperty("environment"), "ADD_DESCRIPTION_LOG__MESSAGE"));
		logPage.pressKey(Keys.ENTER);

		logPage.waitForElementsOfLogPage("logsPageDescriptionFirstRow");
		sleeper(3000);

		sa.assertTrue(
				logPage.getTextOfLogPage("logsPageDescriptionFirstRow").contains(
						getEnvironmentSpecificData(System.getProperty("environment"), "ADD_DESCRIPTION_LOG__MESSAGE")),
				"Description did not match :" + logPage.getTextOfLogPage("logsPageDescriptionFirstRow"));
		sa.assertTrue(
				logPage.getTextOfLogPage("logsPageTypeFirstRow")
						.contains(getEnvironmentSpecificData(System.getProperty("environment"), "TYPE_LOG_VALUE")),
				"Description did not match :" + logPage.getTextOfLogPage("logsPageTypeFirstRow"));
		sa.assertTrue(
				logPage.getTextOfLogPage("logsPageSubTypeFirstRow").contains(
						getEnvironmentSpecificData(System.getProperty("environment"), "ADD_SUBTYPE_LOG_VALUE")),
				"Description did not match :" + logPage.getTextOfLogPage("logsPageSubTypeFirstRow"));
		logPage.scrollOnLogPage("logsPageLevelFirstRow");
		sa.assertTrue(
				logPage.getTextOfLogPage("logsPageLevelFirstRow")
						.contains(getEnvironmentSpecificData(System.getProperty("environment"), "LEVEL_LOG_VALUE")),
				"Description did not match :" + logPage.getTextOfLogPage("logsPageLevelFirstRow"));

		logPage.waitForElementsOfLogPage("clearSelectionButton");
		logPage.clickByJavaScriptOnLogPage("clearSelectionButton");
		LOGGER.info("Logs Message verified");
		
		
	    //Error Logs
		gotoHelpAndSupportTab();//add for reloading of logs table data
        gotoLogTab();
        logPage.waitForElementsOfLogPage("logsPageDescriptionFirstRow");
		logPage.clickOnElementsOfLogPage("descriptionSearchBox");
		logPage.enterTextForLogPage("descriptionSearchBox",
				getEnvironmentSpecificData(System.getProperty("environment"), "ERROR_DESCRIPTION_LOG_MESSAGE"));
		logPage.pressKey(Keys.ENTER);

		logPage.waitForElementsOfLogPage("logsPageDescriptionFirstRow");
		sleeper(3000);

		sa.assertTrue(
				logPage.getTextOfLogPage("logsPageDescriptionFirstRow").contains(
						getEnvironmentSpecificData(System.getProperty("environment"), "ERROR_DESCRIPTION_LOG_MESSAGE")),
				"Description did not match :" + logPage.getTextOfLogPage("logsPageDescriptionFirstRow"));
		sa.assertTrue(
				logPage.getTextOfLogPage("logsPageTypeFirstRow")
						.contains(getEnvironmentSpecificData(System.getProperty("environment"), "TYPE_LOG_VALUE")),
				"Description did not match :" + logPage.getTextOfLogPage("logsPageTypeFirstRow"));
		sa.assertTrue(
				logPage.getTextOfLogPage("logsPageSubTypeFirstRow").contains(
						getEnvironmentSpecificData(System.getProperty("environment"), "ADD_SUBTYPE_LOG_VALUE")),
				"Description did not match :" + logPage.getTextOfLogPage("logsPageSubTypeFirstRow"));
		logPage.scrollOnLogPage("logsPageLevelFirstRow");
		sa.assertTrue(
				logPage.getTextOfLogPage("logsPageLevelFirstRow")
						.contains(getEnvironmentSpecificData(System.getProperty("environment"), "ERROR_LEVEL_LOG_VALUE")),
				"Description did not match :" + logPage.getTextOfLogPage("logsPageLevelFirstRow"));

		logPage.waitForElementsOfLogPage("clearSelectionButton");
		logPage.clickByJavaScriptOnLogPage("clearSelectionButton");
		LOGGER.info("Error Logs Message verified");
		
		//Delete Logs
		gotoHelpAndSupportTab();//add for reloading of logs table data
        gotoLogTab();
        logPage.waitForElementsOfLogPage("logsPageDescriptionFirstRow");
		logPage.clickOnElementsOfLogPage("descriptionSearchBox");
		logPage.enterTextForLogPage("descriptionSearchBox",
				getEnvironmentSpecificData(System.getProperty("environment"), "DELETE_DESCRIPTION_LOG_MESSAGE"));
		logPage.pressKey(Keys.ENTER);

		logPage.waitForElementsOfLogPage("logsPageDescriptionFirstRow");
		sleeper(3000);

		sa.assertTrue(
				logPage.getTextOfLogPage("logsPageDescriptionFirstRow").contains(
						getEnvironmentSpecificData(System.getProperty("environment"), "DELETE_DESCRIPTION_LOG_MESSAGE")),
				"Description did not match :" + logPage.getTextOfLogPage("logsPageDescriptionFirstRow"));
		sa.assertTrue(
				logPage.getTextOfLogPage("logsPageTypeFirstRow")
						.contains(getEnvironmentSpecificData(System.getProperty("environment"), "TYPE_LOG_VALUE")),
				"Description did not match :" + logPage.getTextOfLogPage("logsPageTypeFirstRow"));
		sa.assertTrue(
				logPage.getTextOfLogPage("logsPageSubTypeFirstRow").contains(
						getEnvironmentSpecificData(System.getProperty("environment"), "DELETE_SUBTYPE_LOG_VALUE")),
				"Description did not match :" + logPage.getTextOfLogPage("logsPageSubTypeFirstRow"));
		logPage.scrollOnLogPage("logsPageLevelFirstRow");
		sa.assertTrue(
				logPage.getTextOfLogPage("logsPageLevelFirstRow")
						.contains(getEnvironmentSpecificData(System.getProperty("environment"), "LEVEL_LOG_VALUE")),
				"Description did not match :" + logPage.getTextOfLogPage("logsPageLevelFirstRow"));

		logPage.waitForElementsOfLogPage("clearSelectionButton");
		logPage.clickByJavaScriptOnLogPage("clearSelectionButton");
		LOGGER.info("Delete Logs Message verified");

		sa.assertAll();

	}
	
	@Test(priority = 20, groups = { "REGRESSION", "REGRESSION_CI", "STABILIZATION_STAGING" }, description = "Test Case 1017479: [UI] Map the Subtypes to the selected Type for NewLogs")
	public final void verifySubTypeAndType() throws Exception {
		login("NEW_MSP_ADMIN_US_EMAIl", "NEW_MSP_ADMIN_US_PASSWORD");
		gotoLogTab();
		LogPage logPage = new LogPage(PreDefinedActions.getDriver()).getInstance();
		TableConfigurationPage tableConfigurationPage = new TableConfigurationPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert sa = new SoftAssert();
		resetTableConfiguration();
		logPage.clickOnElementsOfLogPage("tableConfigurationButton");
		tableConfigurationPage.selectAllCheckboxOfPopup("columnCheckboxesOnPopup", "listOfColumnsOnPopup");
		tableConfigurationPage.clickOnElementsOfTableConfigurationPage("saveButton");
		sleeper(2000);		
		logPage.clearAllFiltersOfLogsPage("clearSearchBoxFilter");
		logPage.clickOnElementsOfLogPage("typeBox");
		logPage.waitForElementsOfLogPage("dropdownfortypes");
		sa.assertTrue(logPage.verifyTypeDropdownsValues(logPage.expectedSubtypeTypeValues(),"typeDropdownOptions"));		
		sa.assertTrue(logPage.verifySubTypeDropdownvaluesForEachType(logPage.expectedSubtypeTypeValues(),"typeDropdownOptions", "subtypeDropdownOptions"));
		sa.assertAll();
	}

	@Test(priority = 21, groups = { "REGRESSION", "REGRESSION_CI", "STABILIZATION_STAGING" }, description = "Test Case 1017479: [UI] Map the Subtypes to the selected Type for NewLogs")
	public final void verifySubTypeAndTypeForBillingAndPatnerAdmin() throws Exception {
		login("RESELLER_NEW_UI_Companies_EMAIL_COMPANIES", "RESELLER_NEW_UI_Companies_PASSWORD_COMPANIES");
		gotoLogTab();
		LogPage logPage = new LogPage(PreDefinedActions.getDriver()).getInstance();
		TableConfigurationPage tableConfigurationPage = new TableConfigurationPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert sa = new SoftAssert();
		resetTableConfiguration();
		logPage.clickOnElementsOfLogPage("tableConfigurationButton");
		tableConfigurationPage.selectAllCheckboxOfPopup("columnCheckboxesOnPopup", "listOfColumnsOnPopup");
		tableConfigurationPage.clickOnElementsOfTableConfigurationPage("saveButton");
		sleeper(2000);		
		logPage.clearAllFiltersOfLogsPage("clearSearchBoxFilter");
		logPage.clickOnElementsOfLogPage("typeBox");
		logPage.waitForElementsOfLogPage("dropdownCheckboxes");
		//sa.assertTrue(logPage.verifyTypeDropdownsValues(logPage.expectedSubtypeTypeValues(),"typeDropdownOptions"));		
		sa.assertTrue(logPage.verifySubTypeDropdownvaluesForEachType(logPage.expectedSubtypeTypeValuesForPartnerDetailsUser(logPage.expectedSubtypeTypeValues()),"typeDropdownOptions", "subtypeDropdownOptions"));
		sa.assertAll();
	}
	
	
	// Feature 1014254: [Insights][PI_Sheff][BIOS][Analytics_DEX]Create Log Event for (BIOS) Remediation Event Taken
	
	@Test(priority = 22, groups = {"REGRESSION", "REGRESSION_CI" }, description = "Test Case 1099331,1098944,1096677", enabled = false)
	public final void verifyEMMandDeviceBIOS() throws Exception {

		login("IT_ADMIN_EMAIL_BIOS_2", "IT_ADMIN_PASSWORD_BIOS");

		gotoLogTab();
		LogPage logPage = new LogPage(PreDefinedActions.getDriver()).getInstance();

		TableConfigurationPage tableConfigurationPage = new TableConfigurationPage(PreDefinedActions.getDriver())
				.getInstance();
		SoftAssert sa = new SoftAssert();
		resetTableConfiguration();
		logPage.clickOnElementsOfLogPage("tableConfigurationButton");
		tableConfigurationPage.selectAllCheckboxOfPopup("columnCheckboxesOnPopup", "listOfColumnsOnPopup");
		tableConfigurationPage.clickOnElementsOfTableConfigurationPage("saveButton");
		sleeper(2000);

		// Test case 1 - bios policy remove logs verification 
		logPage.waitForElementsOfLogPage("descriptionSearchBox");
		logPage.clickOnElementsOfLogPage("descriptionSearchBox");
		logPage.enterTextForLogPage("descriptionSearchBox",
				getEnvironmentSpecificData(System.getProperty("environment"), "BIOS_POLICY_REMOVE_DESCRIPTION_LOG_MESSAGE"));
		logPage.pressKey(Keys.ENTER);

		logPage.waitForElementsOfLogPage("logsPageDescriptionFirstRow");
		sleeper(3000);

		sa.assertTrue(
				logPage.getTextOfLogPage("logsPageDescriptionFirstRow")
						.contains(getEnvironmentSpecificData(System.getProperty("environment"),
								"BIOS_POLICY_REMOVE_DESCRIPTION_LOG_MESSAGE")),
				"Description did not match :" + logPage.getTextOfLogPage("logsPageDescriptionFirstRow"));
		sa.assertTrue(
				logPage.getTextOfLogPage("logsPageTypeFirstRow")
						.contains(getEnvironmentSpecificData(System.getProperty("environment"), "TYPE_LOG_VALUE")),
				"Description did not match :" + logPage.getTextOfLogPage("logsPageTypeFirstRow"));
		sa.assertTrue(
				logPage.getTextOfLogPage("logsPageSubTypeFirstRow").contains(
						getEnvironmentSpecificData(System.getProperty("environment"), "BIOS_SUBTYPE_LOG_VALUE")),
				"Description did not match :" + logPage.getTextOfLogPage("logsPageSubTypeFirstRow"));
		logPage.scrollOnLogPage("logsPageLevelFirstRow");
		sa.assertTrue(
				logPage.getTextOfLogPage("logsPageLevelFirstRow")
						.contains(getEnvironmentSpecificData(System.getProperty("environment"), "LEVEL_LOG_VALUE")),
				"Description did not match :" + logPage.getTextOfLogPage("logsPageLevelFirstRow"));

		logPage.waitForElementsOfLogPage("clearSelectionButton");
		logPage.clickByJavaScriptOnLogPage("clearSelectionButton");
		LOGGER.info("BIOS Logs Message verified");
		
		// Bios policy applied Log Verification
		gotoHelpAndSupportTab();//add for reloading of logs table data
        gotoLogTab();
        logPage.waitForElementsOfLogPage("logsPageDescriptionFirstRow");
		logPage.clickOnElementsOfLogPage("descriptionSearchBox");
		logPage.enterTextForLogPage("descriptionSearchBox",
				getEnvironmentSpecificData(System.getProperty("environment"), "BIOS_POLICY_APPLIED_DESCRIPTION_LOG_MESSAGE"));
		logPage.pressKey(Keys.ENTER);

		logPage.waitForElementsOfLogPage("logsPageDescriptionFirstRow");
		sleeper(3000);

		sa.assertTrue(
				logPage.getTextOfLogPage("logsPageDescriptionFirstRow").contains(
						getEnvironmentSpecificData(System.getProperty("environment"), "BIOS_POLICY_APPLIED_DESCRIPTION_LOG_MESSAGE")),
				"Description did not match :" + logPage.getTextOfLogPage("logsPageDescriptionFirstRow"));
		sa.assertTrue(
				logPage.getTextOfLogPage("logsPageTypeFirstRow")
						.contains(getEnvironmentSpecificData(System.getProperty("environment"), "TYPE_LOG_VALUE")),
				"Description did not match :" + logPage.getTextOfLogPage("logsPageTypeFirstRow"));
		sa.assertTrue(
				logPage.getTextOfLogPage("logsPageSubTypeFirstRow").contains(
						getEnvironmentSpecificData(System.getProperty("environment"), "BIOS_SUBTYPE_LOG_VALUE")),
				"Description did not match :" + logPage.getTextOfLogPage("logsPageSubTypeFirstRow"));
		logPage.scrollOnLogPage("logsPageLevelFirstRow");
		sa.assertTrue(
				logPage.getTextOfLogPage("logsPageLevelFirstRow")
						.contains(getEnvironmentSpecificData(System.getProperty("environment"), "LEVEL_LOG_VALUE")),
				"Description did not match :" + logPage.getTextOfLogPage("logsPageLevelFirstRow"));

		logPage.waitForElementsOfLogPage("clearSelectionButton");
		logPage.clickByJavaScriptOnLogPage("clearSelectionButton");
		LOGGER.info("Logs Message verified");
			
	    //EMMProvider Add Logs
		gotoHelpAndSupportTab();//add for reloading of logs table data
        gotoLogTab();
        logPage.waitForElementsOfLogPage("logsPageDescriptionFirstRow");
		logPage.clickOnElementsOfLogPage("descriptionSearchBox");
		logPage.enterTextForLogPage("descriptionSearchBox",
				getEnvironmentSpecificData(System.getProperty("environment"), "EMM_ADD_DESCRIPTION_LOG_MESSAGE"));
		logPage.pressKey(Keys.ENTER);

		logPage.waitForElementsOfLogPage("logsPageDescriptionFirstRow");
		sleeper(3000);

		sa.assertTrue(
				logPage.getTextOfLogPage("logsPageDescriptionFirstRow").contains(
						getEnvironmentSpecificData(System.getProperty("environment"), "EMM_ADD_DESCRIPTION_LOG_MESSAGE")),
				"Description did not match :" + logPage.getTextOfLogPage("logsPageDescriptionFirstRow"));
		sa.assertTrue(
				logPage.getTextOfLogPage("logsPageTypeFirstRow")
						.contains(getEnvironmentSpecificData(System.getProperty("environment"), "EMM_TYPE_LOG_VALUE")),
				"Description did not match :" + logPage.getTextOfLogPage("logsPageTypeFirstRow"));
		sa.assertTrue(
				logPage.getTextOfLogPage("logsPageSubTypeFirstRow").contains(
						getEnvironmentSpecificData(System.getProperty("environment"), "ADD_SUBTYPE_LOG_VALUE")),
				"Description did not match :" + logPage.getTextOfLogPage("logsPageSubTypeFirstRow"));
		logPage.scrollOnLogPage("logsPageLevelFirstRow");
		sa.assertTrue(
				logPage.getTextOfLogPage("logsPageLevelFirstRow")
						.contains(getEnvironmentSpecificData(System.getProperty("environment"), "LEVEL_LOG_VALUE")),
				"Description did not match :" + logPage.getTextOfLogPage("logsPageLevelFirstRow"));

		logPage.waitForElementsOfLogPage("clearSelectionButton");
		logPage.clickByJavaScriptOnLogPage("clearSelectionButton");
		LOGGER.info("Error Logs Message verified");
		
		// EMM Remove Logs
		gotoHelpAndSupportTab();//add for reloading of logs table data
        gotoLogTab();
        logPage.waitForElementsOfLogPage("logsPageDescriptionFirstRow");
		logPage.clickOnElementsOfLogPage("descriptionSearchBox");
		logPage.enterTextForLogPage("descriptionSearchBox",
				getEnvironmentSpecificData(System.getProperty("environment"), "EMM_REMOVE_DESCRIPTION_LOG_MESSAGE"));
		logPage.pressKey(Keys.ENTER);

		logPage.waitForElementsOfLogPage("logsPageDescriptionFirstRow");
		sleeper(3000);

		sa.assertTrue(
				logPage.getTextOfLogPage("logsPageDescriptionFirstRow").contains(
						getEnvironmentSpecificData(System.getProperty("environment"), "EMM_REMOVE_DESCRIPTION_LOG_MESSAGE")),
				"Description did not match :" + logPage.getTextOfLogPage("logsPageDescriptionFirstRow"));
		sa.assertTrue(
				logPage.getTextOfLogPage("logsPageTypeFirstRow")
						.contains(getEnvironmentSpecificData(System.getProperty("environment"), "EMM_TYPE_LOG_VALUE")),
				"Description did not match :" + logPage.getTextOfLogPage("logsPageTypeFirstRow"));
		sa.assertTrue(
				logPage.getTextOfLogPage("logsPageSubTypeFirstRow").contains(
						getEnvironmentSpecificData(System.getProperty("environment"), "REMOVE_SUBTYPE_LOG_VALUE")),
				"Description did not match :" + logPage.getTextOfLogPage("logsPageSubTypeFirstRow"));
		logPage.scrollOnLogPage("logsPageLevelFirstRow");
		sa.assertTrue(
				logPage.getTextOfLogPage("logsPageLevelFirstRow")
						.contains(getEnvironmentSpecificData(System.getProperty("environment"), "LEVEL_LOG_VALUE")),
				"Description did not match :" + logPage.getTextOfLogPage("logsPageLevelFirstRow"));

		logPage.waitForElementsOfLogPage("clearSelectionButton");
		logPage.clickByJavaScriptOnLogPage("clearSelectionButton");
		LOGGER.info("Delete Logs Message verified");

		sa.assertAll();

	}
	@Test(priority = 23, groups = { "PRODUCTION_LDK","INITECH_SOLUTIONS"})
	public final void verifyLogsLdk() throws Exception {
		login("ITADMIN_EMAIL_WEP", "ITADMIN_PASSWORD_WEP");
		SoftAssert softAssert = new SoftAssert();
		 String testSuiteName = SetTestEnvironments.suiteName;
			if(testSuiteName.contains("LDK") || testSuiteName.contains("Initech")){
				switchUserBasedOnSuite(testSuiteName);
			}
		WEPWPTPage WEPWPTPage= new WEPWPTPage(PreDefinedActions.getDriver()).getInstance();	
		LogPage logPage = new LogPage(PreDefinedActions.getDriver()).getInstance();	
		leftSideMenuVerification();	
		WEPWPTPage.clickByActionsClickWEP("settingsMenu");
		waitForPageLoaded();
		sleeper(3000);
		WEPWPTPage.clickByActionsClickWEP("logsTab");
		sleeper(3000);
		LOGGER.info("Redirected to logs list page");
		WEPWPTPage.clickByActionsClickWEP("logsTabLink");		
		softAssert.assertEquals(WEPWPTPage.getTextOfElement("logsHeader"),"Logs");		
		WEPWPTPage.verifyElementsOfPresence("logsWarning");
		softAssert.assertTrue(logPage.verifyElementsOfLogPage("listTable"), "Table on logs list page is not loaded successfully");
		softAssert.assertTrue(logPage.verifyElementsOfLogPage("dateAndTimeHeader"), "Default column is not present on logs list page");
		WEPWPTPage.clickByActionsClickWEP("clearfilter");			
		LOGGER.info("Logs list page is loaded successfully");
		softAssert.assertTrue(WEPWPTPage.verifyDateAndTimeSortingAscending("dateAndTimeSorting",WPTVariable.WPT_Select_dateAndTimeAttribute),"Date and Time sorting is not working as expected");
		softAssert.assertTrue(WEPWPTPage.verifyElementsOfWEPpreferencesPage("logTableFirstRow"),"logs Table FirstRow is not present");
		softAssert.assertAll();
	}
	
}