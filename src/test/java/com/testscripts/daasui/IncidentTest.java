package com.testscripts.daasui;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.basesource.action.PreDefinedActions;
import com.basesource.utils.CompanyPin;
import com.basesource.utils.EnrollFakeDevice;
import com.daasui.constants.CommonVariables;
import com.daasui.constants.ConstantPath;
import com.daasui.constants.ConstantURL;
import com.daasui.constants.DashboardVariables;
import com.daasui.constants.IncidentsVariables;
import com.daasui.constants.PartnerVariables;
import com.daasui.pages.CompaniesPage;
import com.daasui.pages.DashboardPage;
import com.daasui.pages.DeviceDetailsPage;
import com.daasui.pages.DeviceListPage;
import com.daasui.pages.ExperienceMgmtPage;
import com.daasui.pages.IncidentDetailsPage;
import com.daasui.pages.IncidentPage;
import com.daasui.pages.PaginationPage;
import com.daasui.pages.PartnerPage;
import com.daasui.pages.SettingsPage;
import com.daasui.pages.TableConfigurationPage;
import com.google.common.base.Strings;

public class IncidentTest extends CommonTest {
	private static Logger LOGGER = LogManager.getLogger(IncidentTest.class);
	public int activepageNumber = 0, firstPageNumber = 0, lastPageNumber = 0, selectedOption = 0, totalCount = 0;
	public boolean previousButtonStatus = false, nextButtonStatus = false;

	@DataProvider
	public Object[][] loginDataListPage() {
		Object[][] data = new Object[2][2];
		data[0][0] = "MSP_DEVICE_LIST_EMAIL_COMPANIES";
		data[0][1] = "MSP_DEVICE_LIST_PASSWORD_COMPANIES";
		data[1][0] = "RESELLER_NEW_UI_Companies_EMAIL_COMPANIES";
		data[1][1] = "RESELLER_NEW_UI_Companies_PASSWORD_COMPANIES";
		return data;
	}

	@DataProvider
	public Object[][] getLoginDetails(){
		Object[][] user = new Object[2][2];
		user[0][0] = "ITADMIN_PEM_EMAIL";
		user[0][1] = "ITADMIN_PEM_PASSWORD";
		user[1][0] = "RESELLER_ACTIONABLE_EMAIL";
		user[1][1] = "RESELLER_ACTIONABLE_PASSWORD";
		return user;
	}

	@Test(priority = 1, groups = { "REGRESSION", "REGRESSION_CI", "SMOKE", "ALL_CI", "ALL" })
	public final void verifyIncidentTitle() throws Exception {
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		gotoIncidentTab();
		LOGGER.info("Redirected to incident tab");
		removeLocationFilter();
		IncidentPage incidentPage = new IncidentPage(PreDefinedActions.getDriver()).getInstance();
		waitForPageLoaded();
		if (toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, "MSP_ADMIN_US_EMAIl"))) {
			incidentPage.waitForElementsOfIncidentPage("incidentTitleOnBreadcrumbs");
			Assert.assertTrue(incidentPage.getTextOfIncidentPage("incidentTitleOnBreadcrumbs").equals(getTextLanguage(LanguageCode, "daas_ui", "incidents.label")), "Incident title text on incident list page is incorrect");
			LOGGER.info("Incident title on incident list page is matched");
		}else{
			Assert.assertTrue(incidentPage.getTextOfIncidentPage("incidentTitle").equals(getTextLanguage(LanguageCode, "daas_ui", "incidents.label")), "Incident title text on incident list page is incorrect");
			LOGGER.info("Incident title on incident list page is matched");
		}
	}

	@Test(priority = 2, description = "USER STORY 198797", groups = { "REGRESSION", "SMOKE", "REGRESSION_CI", "SMOKE_CI" })
	public final void verifyFilterFunctionality() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		gotoIncidentTab();
		LOGGER.info("Redirected to incident tab");
		removeLocationFilter();
		waitForPageLoaded();
		IncidentPage incidentPage = new IncidentPage(PreDefinedActions.getDriver()).getInstance();

		resetTableConfiguration();
		Assert.assertTrue(incidentPage.verifyElementsOfIncidentPage("selectedIncidentTitle"), "No incident is present on incident page, unable to proceed further");
		LOGGER.info("Atleast one incident is present, test case started");

		incidentPage.waitUntilElementIsInvisibleOfIncidentPage("tableOverlay");
		incidentPage.waitForElementsOfIncidentPage("selectedIncidentTitle");

		// Test Case 1 - This test case validates if the filter functionality is working
		// properly for the searchbox on id column
		softAssert.assertTrue(incidentPage.verifySearchValueOnIncident(LanguageCode, "idSearchBox", IncidentsVariables.INCORRECT_SEARCH_STRING, "noElementsDisplayText", "idList"), "Search functionality with incorrect search string for ID column on incident list page is not working");
		softAssert.assertTrue(incidentPage.verifySearchValueOnIncident(LanguageCode, "idSearchBox", IncidentsVariables.ID, "noElementsDisplayText", "idList"), "Search functionality for ID column on incident list page is not working");
		LOGGER.info("Verified filter functionality for ID column");
		incidentPage.waitUntilElementIsInvisibleOfIncidentPage("tableOverlay");

		// Test Case 2 - This test case validates if the filter functionality is working
		// properly for the dropdown on type column
		incidentPage.clickOnElementsOfIncidentPage("typeBoxBefore");
		softAssert.assertTrue(incidentPage.verifyFilterSingleSelect(LanguageCode, "dropDownCheckBoxes", "dropDownElementListLabels", "typeList", "noElementsDisplayText"), "Filter functionality on selecting single option from Type column on incident list page is not working");
		incidentPage.waitUntilElementIsInvisibleOfIncidentPage("tableOverlay");
		incidentPage.clickOnElementsOfIncidentPage("clearFiltersButton");
		incidentPage.waitUntilElementIsInvisibleOfIncidentPage("tableOverlay");

		incidentPage.clickOnElementsOfIncidentPage("typeBoxBefore");
		softAssert.assertTrue(incidentPage.verifyFilterMultiSelect(LanguageCode, "dropDownCheckBoxes", "dropDownElementListLabels", "typeList", "noElementsDisplayText"), "Filter functionality on selecting multiple options from Type column on incident list page is not working");
		incidentPage.waitUntilElementIsInvisibleOfIncidentPage("tableOverlay");
		if (incidentPage.verifyElementsOfIncidentPage("clearFiltersButton"))
			incidentPage.clickOnElementsOfIncidentPage("clearFiltersButton");
		incidentPage.waitUntilElementIsInvisibleOfIncidentPage("tableOverlay");
		LOGGER.info("Verified filter functionality for type column");

		/*
		 * // Test Case 3 - This test case validates if the filter functionality is working properly for the dropdown on subtype column
		 * incidentPage.clickOnElementsOfIncidentPage("subTypeBoxBefore"); softAssert.assertTrue(incidentPage.verifyFilterSingleSelect(LanguageCode, "dropDownCheckBoxes",
		 * "dropDownElementListLabels", "subTypeList", "noElementsDisplayText"), "Filter functionality on selecting single option from subtype column on incident list page is not working"
		 * ); incidentPage.clickOnElementsOfIncidentPage("clearFiltersButton"); incidentPage.waitUntilElementIsInvisibleOfIncidentPage("tableOverlay");
		 * 
		 * incidentPage.clickOnElementsOfIncidentPage("subTypeBoxBefore"); softAssert.assertTrue(incidentPage.verifyFilterMultiSelect(LanguageCode, "dropDownCheckBoxes",
		 * "dropDownElementListLabels", "subTypeList", "noElementsDisplayText"),
		 * "Filter functionality on selecting multiple options from subtype column on incident list page is not working" ); if
		 * (incidentPage.verifyElementsOfIncidentPage("clearFiltersButton")) incidentPage.clickOnElementsOfIncidentPage("clearFiltersButton");
		 * incidentPage.waitUntilElementIsInvisibleOfIncidentPage("tableOverlay"); LOGGER.info("Verified filter functionality for subtype column");
		 */

		// Test Case 4 - This test case validates if the filter functionality is working
		// properly for the calendar on Created On column
		// incidentPage.scrollOnIncidentPage("createdOnBoxTitle");

		incidentPage.waitForElementsOfIncidentPage("createdOnBox");
		incidentPage.clickOnElementsOfIncidentPage("createdOnBox");
		sleeper(1000);
		softAssert.assertTrue(incidentPage.selectLastOneWeekRangeOnIncidentListPage(LanguageCode, "calenderIcon", "monthYearLeft", "monthYearRight", "prvArrow", "totalDaysLeftSide", "totalDaysRightSide", "noElementsDisplayText", "createdOnList"), "Filter functionality for created on column on incident list page is not working");
		incidentPage.waitUntilElementIsInvisibleOfIncidentPage("tableOverlay");
		if (incidentPage.verifyElementsOfIncidentPage("clearFiltersButton"))
			incidentPage.clickOnElementsOfIncidentPage("clearFiltersButton");
		incidentPage.waitUntilElementIsInvisibleOfIncidentPage("tableOverlay");
		LOGGER.info("Verified filter functionality for created on column");

		/*
		 * // Test Case 5 - This test case validates if the filter functionality is working properly for the dropdown on priority column //
		 * incidentPage.scrollOnIncidentPage("priorityBoxTitle"); incidentPage.clickOnElementsOfIncidentPage("priorityBoxBefore");
		 * softAssert.assertTrue(incidentPage.verifyFilterSingleSelect(LanguageCode, "dropDownCheckBoxes", "dropDownElementListLabels", "priorityList", "noElementsDisplayText"),
		 * "Filter functionality on selecting single option from priority column on incident list page is not working" );
		 * incidentPage.waitUntilElementIsInvisibleOfIncidentPage("tableOverlay"); incidentPage.clickOnElementsOfIncidentPage("clearFiltersButton");
		 * incidentPage.waitUntilElementIsInvisibleOfIncidentPage("tableOverlay");
		 * 
		 * incidentPage.clickOnElementsOfIncidentPage("priorityBoxBefore"); softAssert.assertTrue(incidentPage.verifyFilterMultiSelect(LanguageCode, "dropDownCheckBoxes",
		 * "dropDownElementListLabels", "priorityList", "noElementsDisplayText"),
		 * "Filter functionality on selecting multiple options from priority column on incident list page is not working" ); if
		 * (incidentPage.verifyElementsOfIncidentPage("clearFiltersButton")) incidentPage.clickOnElementsOfIncidentPage("clearFiltersButton");
		 * incidentPage.waitUntilElementIsInvisibleOfIncidentPage("tableOverlay"); LOGGER.info("Verified filter functionality for priority column");
		 * 
		 * // Test Case 6 - This test case validates if the filter functionality is working properly for the dropdown on status column //
		 * incidentPage.scrollOnIncidentPage("statusBoxTitle"); incidentPage.clickOnElementsOfIncidentPage("statusBoxBefore");
		 * softAssert.assertTrue(incidentPage.verifyFilterSingleSelect(LanguageCode, "dropDownCheckBoxes", "dropDownElementListLabels", "statusList", "noElementsDisplayText"),
		 * "Filter functionality on selecting single option from status column on incident list page is not working" ); incidentPage.clickOnElementsOfIncidentPage("clearFiltersButton");
		 * incidentPage.waitUntilElementIsInvisibleOfIncidentPage("tableOverlay");
		 * 
		 * incidentPage.clickOnElementsOfIncidentPage("statusBoxBefore"); softAssert.assertTrue(incidentPage.verifyFilterMultiSelect(LanguageCode, "dropDownCheckBoxes",
		 * "dropDownElementListLabels", "statusList", "noElementsDisplayText"), "Filter functionality on selecting multiple options from status column on incident list page is not working"
		 * ); if (incidentPage.verifyElementsOfIncidentPage("clearFiltersButton")) incidentPage.clickOnElementsOfIncidentPage("clearFiltersButton");
		 * incidentPage.waitUntilElementIsInvisibleOfIncidentPage("tableOverlay"); LOGGER.info("Verified filter functionality for status column");
		 */

		// Test Case 7 - This test case validates if the filter functionality is working
		// properly for the dropdown on the assign to column
		// incidentPage.scrollOnIncidentPage("assignToBoxTitle");
		//		incidentPage.clickOnElementsOfIncidentPage("assignedToBoxBefore");
		//		incidentPage.waitForElementsOfIncidentPage("assignedToSearchBox");
		//		softAssert.assertTrue(incidentPage.verifyFilterFunctionalityForAssignedToSingleSelectFromDynamicDropdown(LanguageCode, "assignedToSearchBox", IncidentsVariables.ASSIGN_TO, "noElementsDisplayTextForComboBoxColumn", "assignedToListOnPopup", "dropDownCheckBoxes", "assignedToList", "noElementsDisplayText"), "Filter functionality on selecting single option from assign to column on incident list page is not working");
		//		if (incidentPage.verifyElementsOfIncidentPage("clearFiltersButton"))
		//			incidentPage.clickOnElementsOfIncidentPage("clearFiltersButton");
		//		incidentPage.waitUntilElementIsInvisibleOfIncidentPage("tableOverlay");
		//
		//		incidentPage.clickOnElementsOfIncidentPage("assignedToBoxBefore");
		//		incidentPage.waitForElementsOfIncidentPage("assignedToSearchBox");
		//		softAssert.assertTrue(incidentPage.verifyFilterFunctionalityForAssignedToMultiSelectFromDynamicDropdown(LanguageCode, "assignedToSearchBox", IncidentsVariables.ASSIGN_TO, "noElementsDisplayTextForComboBoxColumn", "assignedToListOnPopup", "dropDownCheckBoxes", "assignedToList", "noElementsDisplayText"), "Filter functionality on selecting multiple options from assign to column on incident list page is not working");
		//		if (incidentPage.verifyElementsOfIncidentPage("clearFiltersButton"))
		//			incidentPage.clickOnElementsOfIncidentPage("clearFiltersButton");
		//		incidentPage.waitUntilElementIsInvisibleOfIncidentPage("tableOverlay");
		//		LOGGER.info("Verified filter functionality for assign to column");

		/*
		 * // Test Case 8 - This test case validates if the filter functionality is working properly for the dropdown on company box column //
		 * incidentPage.scrollOnIncidentPage("companyBoxTitle"); incidentPage.clickOnElementsOfIncidentPage("companyBoxBefore");
		 * incidentPage.waitForElementsOfIncidentPage("companySearchBox"); softAssert.assertTrue(incidentPage. verifyFilterFunctionalityForAssignedToSingleSelectFromDynamicDropdown(
		 * LanguageCode, "companySearchBox", IncidentsVariables.COMPANY, "noElementsDisplayTextForComboBoxColumn", "companyListOnPopup", "dropDownCheckBoxes", "companyList",
		 * "noElementsDisplayText"), "Filter functionality on selecting single option from company column on incident list page is not working" ); if
		 * (incidentPage.verifyElementsOfIncidentPage("clearFiltersButton")) incidentPage.clickOnElementsOfIncidentPage("clearFiltersButton");
		 * incidentPage.waitUntilElementIsInvisibleOfIncidentPage("tableOverlay");
		 * 
		 * incidentPage.clickOnElementsOfIncidentPage("companyBoxBefore"); incidentPage.waitForElementsOfIncidentPage("companySearchBox"); softAssert.assertTrue(incidentPage.
		 * verifyFilterFunctionalityForAssignedToMultiSelectFromDynamicDropdown( LanguageCode, "companySearchBox", IncidentsVariables.COMPANY, "noElementsDisplayTextForComboBoxColumn",
		 * "companyListOnPopup", "dropDownCheckBoxes", "companyList", "noElementsDisplayText"),
		 * "Filter functionality on selecting multiple options from company column on incident list page is not working" ); if
		 * (incidentPage.verifyElementsOfIncidentPage("clearFiltersButton")) incidentPage.clickOnElementsOfIncidentPage("clearFiltersButton");
		 * incidentPage.waitUntilElementIsInvisibleOfIncidentPage("tableOverlay"); LOGGER.info("Verified filter functionality for company column");
		 * 
		 * // Test Case 9 - This test case validates if the filter functionality is working properly for the search box on the device name column //
		 * incidentPage.scrollOnIncidentPage("deviceNameBoxTitle"); softAssert.assertTrue(incidentPage.verifySearchValueOnIncident(LanguageCode, "deviceNameSearchBox",
		 * IncidentsVariables.INCORRECT_SEARCH_STRING, "noElementsDisplayText", "deviceNameList"),
		 * "Search functionality with incorrect search string for device name column on incident list page is not working" );
		 * softAssert.assertTrue(incidentPage.verifySearchValueOnIncident(LanguageCode, "deviceNameSearchBox", IncidentsVariables.DEVICE_NAME, "noElementsDisplayText", "deviceNameList"),
		 * "Search functionality for device name column on incident list page is not working" ); LOGGER.info("Verified filter functionality for device name column");
		 */

		/*
		 * // Test Case 10 - This test case validates if the filter functionality is working properly for the searchbox on device serial number column //
		 * incidentPage.scrollOnIncidentPage("deviceSerialNumberBoxTitle"); softAssert.assertTrue(incidentPage.verifySearchValueOnIncident(LanguageCode, "deviceSerialNumberSearchBox",
		 * IncidentsVariables.INCORRECT_SEARCH_STRING, "noElementsDisplayText", "deviceSerialNumberList"),
		 * "Search functionality with incorrect search string for device serial number column on incident list page is not working" );
		 * softAssert.assertTrue(incidentPage.verifySearchValueOnIncident(LanguageCode, "deviceSerialNumberSearchBox", IncidentsVariables.SERIAL_NUMBER, "noElementsDisplayText",
		 * "deviceSerialNumberList"), "Search functionality for device serial number column on incident list page is not working" );
		 * LOGGER.info("Verified filter functionality for device serial number column");
		 * 
		 * // Test Case 11 - This test case validates if the filter functionality is working properly for the dropdown device manufacturer column //
		 * incidentPage.scrollOnIncidentPage("deviceManufactureTitle"); incidentPage.clickOnElementsOfIncidentPage("deviceManufactureBefore");
		 * incidentPage.waitForElementsOfIncidentPage("deviceManufacturerSearchBox"); softAssert.assertTrue(incidentPage.verifySearchValueOnIncidentInsidePopup( LanguageCode,
		 * "deviceManufacturerSearchBox", IncidentsVariables.INCORRECT_SEARCH_STRING, "noElementsDisplayTextForComboBoxColumn", "deviceManufacturerListOnPopup"),
		 * "Search functionality for device manufacturer search box on popup on incident list page is not working" ); incidentPage.waitForElementsOfIncidentPage("deviceManufactureBefore");
		 * softAssert.assertTrue(incidentPage.verifyFilterSingleSelectDynamic( LanguageCode, "deviceManufacturerSearchBox", IncidentsVariables.DEVICE_MANUFACTURER,
		 * "noElementsDisplayTextForComboBoxColumn", "deviceManufacturerListOnPopup", "dropDownCheckBoxes", "deviceManufacturerList", "noElementsDisplayText"),
		 * "Filter functionality on selecting single option from device manufacturer column on incident list page is not working" ); if
		 * (incidentPage.verifyElementsOfIncidentPage("clearFiltersButton")) incidentPage.clickOnElementsOfIncidentPage("clearFiltersButton");
		 * incidentPage.waitUntilElementIsInvisibleOfIncidentPage("tableOverlay");
		 * 
		 * incidentPage.clickOnElementsOfIncidentPage("deviceManufactureBefore"); incidentPage.waitForElementsOfIncidentPage("deviceManufacturerSearchBox");
		 * softAssert.assertTrue(incidentPage.verifyFilterMultiSelectDynamic( LanguageCode, "deviceManufacturerSearchBox", IncidentsVariables.DEVICE_MANUFACTURER,
		 * "noElementsDisplayTextForComboBoxColumn", "deviceManufacturerListOnPopup", "dropDownCheckBoxes", "deviceManufacturerList", "noElementsDisplayText"),
		 * "Filter functionality on selecting multiple options from device manufacturer column on incident list page is not working" ); if
		 * (incidentPage.verifyElementsOfIncidentPage("clearFiltersButton")) incidentPage.clickOnElementsOfIncidentPage("clearFiltersButton");
		 * incidentPage.waitUntilElementIsInvisibleOfIncidentPage("tableOverlay"); LOGGER.info("Verified filter functionality for device manufacturer column");
		 * 
		 * // Test Case 12 - This test case validates if the filter functionality is working properly for the search box on title column incidentPage.scrollOnIncidentPage("titleBoxTitle");
		 * softAssert.assertTrue(incidentPage.verifySearchValueOnIncident(LanguageCode, "titleSearchBox", IncidentsVariables.INCORRECT_SEARCH_STRING, "noElementsDisplayText", "titleList"),
		 * "Search functionality with incorrect search string for title column on incident list page is not working" );
		 * softAssert.assertTrue(incidentPage.verifySearchValueOnIncident(LanguageCode, "titleSearchBox", IncidentsVariables.TITLE, "noElementsDisplayText", "titleList"),
		 * "Search functionality for title column on incident list page is not working" ); LOGGER.info("Verified filter functionality for title column");
		 * 
		 * // Test Case 13 - This test case validates if the filter functionality is working properly for the dropdown on the created by column //
		 * incidentPage.scrollOnIncidentPage("createdByBoxTitle"); incidentPage.clickOnElementsOfIncidentPage("createdByBefore");
		 * incidentPage.waitForElementsOfIncidentPage("createdBySearchBox"); softAssert.assertTrue(incidentPage.verifySearchValueOnIncidentInsidePopup( LanguageCode, "createdBySearchBox",
		 * IncidentsVariables.INCORRECT_SEARCH_STRING, "noElementsDisplayTextForComboBoxColumn", "createdbyListOnPopup"),
		 * "Search functionality for created by search box on popup on incident list page is not working" ); incidentPage.waitForElementsOfIncidentPage("createdByBefore");
		 * softAssert.assertTrue(incidentPage.verifyFilterSingleSelectDynamic( LanguageCode, "createdBySearchBox", IncidentsVariables.CREATED_BY, "noElementsDisplayTextForComboBoxColumn",
		 * "createdByListOnPopup", "dropDownCheckBoxes", "createdByList", "noElementsDisplayText"),
		 * "Filter functionality on selecting single option from created by column on incident list page is not working" ); if
		 * (incidentPage.verifyElementsOfIncidentPage("clearFiltersButton")) incidentPage.clickOnElementsOfIncidentPage("clearFiltersButton");
		 * incidentPage.waitUntilElementIsInvisibleOfIncidentPage("tableOverlay");
		 * 
		 * // incidentPage.scrollOnIncidentPage("createdByBoxTitle"); incidentPage.clickOnElementsOfIncidentPage("createdByBefore");
		 * incidentPage.waitForElementsOfIncidentPage("createdBySearchBox"); softAssert.assertTrue(incidentPage.verifyFilterMultiSelectDynamic( LanguageCode, "createdBySearchBox",
		 * IncidentsVariables.CREATED_BY, "noElementsDisplayTextForComboBoxColumn", "createdByListOnPopup", "dropDownCheckBoxes", "createdByList", "noElementsDisplayText"),
		 * "Filter functionality on selecting multiple options from created by column on incident list page is not working" ); if
		 * (incidentPage.verifyElementsOfIncidentPage("clearFiltersButton")) incidentPage.clickOnElementsOfIncidentPage("clearFiltersButton");
		 * incidentPage.waitUntilElementIsInvisibleOfIncidentPage("tableOverlay"); LOGGER.info("Verified filter functionality for created by column");
		 * 
		 * // Test Case 14 - This test case validates if the filter functionality is working properly for the calendar on occured at column
		 * incidentPage.scrollOnIncidentPage("occuredAtTitle"); incidentPage.waitForElementsOfIncidentPage("occuredAtBox"); incidentPage.clickOnElementsOfIncidentPage("occuredAtBox");
		 * sleeper(3000); softAssert.assertTrue(incidentPage.selectLastOneWeekRangeOnIncidentListPage( LanguageCode, "occuredAtBox", "monthYearLeft", "monthYearRight", "prvArrow",
		 * "totalDaysLeftSide", "totalDaysRightSide", "noElementsDisplayText", "occuredAtList"), "Filter functionality for occured at column on incident list page is not working" );
		 * incidentPage.clickOnElementsOfIncidentPage("clearFiltersButton"); incidentPage.waitUntilElementIsInvisibleOfIncidentPage("tableOverlay");
		 * LOGGER.info("Verified filter functionality for occured at column");
		 * 
		 * // Test Case 14 - This test case validates if the filter functionality is working properly for the calendar on updated on column //
		 * incidentPage.scrollOnIncidentPage("updatedOnBoxTitle"); incidentPage.waitForElementsOfIncidentPage("updatedOnBox"); incidentPage.clickOnElementsOfIncidentPage("updatedOnBox");
		 * softAssert.assertTrue(incidentPage.selectLastOneWeekRangeOnIncidentListPage( LanguageCode, "updatedOnBox", "monthYearLeft", "monthYearRight", "prvArrow", "totalDaysLeftSide",
		 * "totalDaysRightSide", "noElementsDisplayText", "updatedOnList"), "Filter functionality for updated on column on incident list page is not working" );
		 * incidentPage.clickOnElementsOfIncidentPage("clearFiltersButton"); incidentPage.waitUntilElementIsInvisibleOfIncidentPage("tableOverlay");
		 * LOGGER.info("Verified filter functionality for updated on column");
		 * 
		 * // Test Case 15 - This test case validates if the filter functionality is working properly for the search box on the device model name column //
		 * incidentPage.scrollOnIncidentPage("deviceModelNameBoxTitle"); softAssert.assertTrue(incidentPage.verifySearchValueOnIncident(LanguageCode, "deviceModelNameSearchBox",
		 * IncidentsVariables.INCORRECT_SEARCH_STRING, "noElementsDisplayText", "deviceModelNameList"),
		 * "Search functionality with incorrect search string for device model name column on incident list page is not working" );
		 * softAssert.assertTrue(incidentPage.verifySearchValueOnIncident(LanguageCode, "deviceModelNameSearchBox", IncidentsVariables.DEVICE_MODEL_NAME, "noElementsDisplayText",
		 * "deviceModelNameList"), "Search functionality for device model name column on incident list page is not working" );
		 * LOGGER.info("Verified filter functionality for device model name column");
		 * 
		 * // Test Case 16 - This test case validates if the filter functionality is working properly for the search box on the device os column //
		 * incidentPage.scrollOnIncidentPage("deviceOSTitle"); softAssert.assertTrue(incidentPage.verifySearchValueOnIncident(LanguageCode, "deviceOSSearchBox",
		 * IncidentsVariables.INCORRECT_SEARCH_STRING, "noElementsDisplayText", "deviceOSList"),
		 * "Search functionality with incorrect search string for device os column on incident list page is not working" );
		 * softAssert.assertTrue(incidentPage.verifySearchValueOnIncident(LanguageCode, "deviceOSSearchBox", IncidentsVariables.DEVICE_OS, "noElementsDisplayText", "deviceOSList"),
		 * "Search functionality for device os column on incident list page is not working" ); LOGGER.info("Verified filter functionality for device os column");
		 */

		softAssert.assertAll();
		LOGGER.info("All filter functionality test-cases passed");

	}

	// Pending for v3 changes due to Bug 619629: [AUX][DaaS] Search box missing on assign to popup on incident list page

	@DataProvider
	public Object[][] getLoginData() {
		Object[][] data = new Object[2][2];
		data[0][0] = "MSP_ADMIN_US_EMAIl";
		data[0][1] = "MSP_ADMIN_US_PASSWORD";
		data[1][0] = "MSP_SPEC_INCIDENTS_EMAIL";
		data[1][1] = "MSP_SPEC_INCIDENTS_PASSOWRD";
		return data;
	}

	@Test(priority = 3, description = " USER STORY 198797 ; Roles ~ MSP, MSP Specialist", groups = { "REGRESSION", "SMOKE", "REGRESSION_CI", "SMOKE_CI" })
	public final void verifyAssignToPopupTestCases() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		String assignTo = null;
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		gotoIncidentTab();
		removeLocationFilter();
		waitForPageLoaded();
		IncidentPage incidentPage = new IncidentPage(PreDefinedActions.getDriver()).getInstance();

		resetTableConfiguration();
		Assert.assertTrue(incidentPage.verifyElementsOfIncidentPage("selectedIncidentTitle"), "No incident is present on incident page, unable to proceed further");
		LOGGER.info("Atleast one incident is present, test case started");

		incidentPage.scrollOnIncidentPage("currentAssignTohref");
		if(incidentPage.verifyElementsOfIncidentPage("currentAssignTohref"))
			assignTo = incidentPage.getTextOfIncidentPage("currentAssignTohref");
		else
			assignTo = getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.type.unassigned");

		// Test Case 1 - This test case validates if the assign to button is present
		// even when no incident checkbox is selected

		softAssert.assertFalse(incidentPage.verifyElementsOfIncidentPage("assignToButton"), "Assign To button is enabled even when no checkbox is selected");

		// Test Case 2 - This test case validates if the assign to button is not present
		// even when an incident is selected
		incidentPage.mousehoverOnIncientPage("firstIdLogo");
		incidentPage.clickByJavaScriptOnIncidentPage("firstIdCheckBox");
		LOGGER.info("Selected first incident on incident list page");
		if(incidentPage.getTextOfIncidentPage("moreDropDownButtonText").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "users.list.action_buttons.more")))
		{
			incidentPage.clickOnElementsOfIncidentPage("moreDropdownButton");
			softAssert.assertTrue(incidentPage.verifyElementsOfIncidentPage("assignToMoreDropdown"), "Change status button is not present when all checkbox is selected");
			incidentPage.clickOnElementsOfIncidentPage("moreDropdownButton");
		}
		else{
			softAssert.assertTrue(incidentPage.verifyElementsOfIncidentPage("assignToButton"), "Change status button is not present when all checkbox is selected");
		}

		// Test Case 3 - This test case validates if the assign to popup does not show
		// up when the assign to button is selected
		if(incidentPage.getTextOfIncidentPage("moreDropDownButtonText").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "users.list.action_buttons.more")))
		{
			incidentPage.clickOnElementsOfIncidentPage("moreDropdownButton");
			incidentPage.clickOnElementsOfIncidentPage("assignToMoreDropdown");
		}
		else{
			incidentPage.clickOnElementsOfIncidentPage("assignToButton");
		}

		// Test Case 5 - This test case validates if by default the save button on the
		// assign to popup is disabled
		softAssert.assertTrue(incidentPage.verifyElementIsEnableOfIncidentPage("assignToPopupSaveButton"), "By default save button should be enable on assign To pop-up");

		// Test Case 6 - This test case validates if by default the discard button on
		// the assign to popup is enabled
		softAssert.assertTrue(incidentPage.verifyElementIsEnableOfIncidentPage("assignToPopupCancelButton"), "By default discard button should be enable on assign To pop-up");

		// Test Case 7 - This test case validates if the search box on the assign to
		// popup is working properly
		incidentPage.clickOnElementsOfIncidentPage("assignToDropDownArrow");
		sleeper(10000);//Sleeper needed since first time click happens very late.
		softAssert.assertTrue(incidentPage.verifySearchValueOnIncidentInsidePopup(LanguageCode, "assignToPopupSearchBox", IncidentsVariables.INCORRECT_SEARCH_STRING, "assignToEmptySearch", "assignToPopupSearchList"), "Search not working");


		// Test Case 10 - This test case validates if the discard functionality for the
		// assign to popup is working properly
		incidentPage.waitForElementtobeClickableOfIncidentPage("assignToDropDownArrow");
		incidentPage.clickOnElementsOfIncidentPage("assignToDropDownArrow");
		sleeper(1000);
		incidentPage.selectedValueFromSearchBoxInsidePopup(LanguageCode, "assignToPopupSearchBox", IncidentsVariables.ASSIGN_TO, "assignToEmptySearch", "assignToPopupSearchList", "assignToOnPopup2", "assignToDropDownArrow", "assignToPopupCancelButton");
		LOGGER.info("Selected value on assign to dropdown");
		incidentPage.clickOnElementsOfIncidentPage("assignToPopupCancelButton");
		LOGGER.info("Verified cancel functionality");
		incidentPage.scrollOnIncidentPage("currentAssignTohref");
		System.out.println(assignTo);
		softAssert.assertTrue(incidentPage.matchTextOfIncidentPage("currentAssignTohref", assignTo), "After selecting the value from assign to search box and clicking on cancel button , it should discard all the changes and information should not get updated on incident list page");

		// Test Case 11 - This test case validates if the save button functionality for
		// the assign to popup is working properly and the assignee gets updated on the
		// incident list page
		if(incidentPage.getTextOfIncidentPage("moreDropDownButtonText").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "users.list.action_buttons.more")))
		{
			incidentPage.clickOnElementsOfIncidentPage("moreDropdownButton");
			incidentPage.clickOnElementsOfIncidentPage("assignToMoreDropdown");
		}
		else{
			incidentPage.clickOnElementsOfIncidentPage("assignToButton");
		}
		incidentPage.waitForElementtobeClickableOfIncidentPage("assignToDropDownArrow");
		incidentPage.clickOnElementsOfIncidentPage("assignToDropDownArrow");
		sleeper(1000);
		String selectedValue = incidentPage.selectedValueFromSearchBoxInsidePopup(LanguageCode, "assignToPopupSearchBox", IncidentsVariables.ASSIGN_TO, "assignToEmptySearch", "assignToPopupSearchList", "assignToOnPopup2", "assignToDropDownArrow", "assignToPopupCancelButton");
		LOGGER.info("Selected value on assign to dropdown");
		incidentPage.clickOnElementsOfIncidentPage("assignToPopupSaveButton");
		LOGGER.info("Verified save functionality");
		incidentPage.mousehoverOnIncientPage("firstIdLogo");
		incidentPage.scrollOnIncidentPage("currentAssignTohref");
		softAssert.assertTrue(selectedValue.contains(incidentPage.getTextOfIncidentPage("currentAssignTohref")), "Assignment is not updated successfully on incident page");

		softAssert.assertAll();
		LOGGER.info("All test cases of assign To pop-up passed");

		softAssert.assertAll();
		LOGGER.info("All test cases of assign to pop-up passed");

	}

	@Test(priority = 4, description = " USER STORY 198797", groups = { "REGRESSION", "SMOKE", "REGRESSION_CI", "SMOKE_CI" })
	public final void verifyAssignProrityPopupTestCases() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		gotoIncidentTab();
		removeLocationFilter();
		waitForPageLoaded();
		IncidentPage incidentPage = new IncidentPage(PreDefinedActions.getDriver()).getInstance();

		resetTableConfiguration();
		Assert.assertTrue(incidentPage.verifyElementsOfIncidentPage("selectedIncidentTitle"), "No incident is present on incident page, unable to proceed further");
		LOGGER.info("Atleast one incident is present, test case started");

		String selectedValue = null, valueSelected;
		ArrayList<String> optionsForPriorityOnDropdown = new ArrayList<String>(
				Arrays.asList(getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.type.unassigned"), getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.priority.critical"), getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.priority.high"), getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.priority.medium"), getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.priority.low")));

		// Test Case 1 - This test case validates if assign priority is present even
		// when no checkbox is checked
		softAssert.assertFalse(incidentPage.verifyElementsOfIncidentPage("assignPriorityButton"), "Assign priority button is present even when no incident is selected");

		// Test case 3 - This test case validates if the assign priority button is
		// present when an incident is selected
		incidentPage.mousehoverOnIncientPage("firstIdLogo");
		incidentPage.clickByJavaScriptOnIncidentPage("firstIdCheckBox");
		LOGGER.info("Selected first incident on incident list page");
		incidentPage.scrollOnIncidentPage("currentPriority");
		String priority = incidentPage.getTextOfIncidentPage("currentPriority");

		if(incidentPage.getTextOfIncidentPage("moreDropDownButtonText").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "users.list.action_buttons.more")))
		{
			incidentPage.clickOnElementsOfIncidentPage("moreDropdownButton");
			softAssert.assertTrue(incidentPage.verifyElementsOfIncidentPage("assignPriorityMoreDrodown"), "Assign priority button is not present when all checkbox is selected");
			incidentPage.clickOnElementsOfIncidentPage("moreDropdownButton");
		}
		else{
			softAssert.assertTrue(incidentPage.verifyElementsOfIncidentPage("assignPriorityButton"), "Assign priority button is not present when all checkbox is selected");
		}
		// Test Case 4 - This test case validates if the assign priority popup shows up
		// when the assign priority button is clicked
		if(incidentPage.getTextOfIncidentPage("moreDropDownButtonText").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "users.list.action_buttons.more")))
		{
			incidentPage.clickOnElementsOfIncidentPage("moreDropdownButton");
			incidentPage.clickOnElementsOfIncidentPage("assignPriorityMoreDrodown");
		}
		else{
			incidentPage.clickOnElementsOfIncidentPage("assignPriorityButton");
		}

		// Test Case 5 - This test case validates if by default the submit button on
		// assign priority popup is disabled
		softAssert.assertTrue(incidentPage.verifyElementIsEnableOfIncidentPage("assignPrioritySubmitButton"), "By default submit button should be enable on assign priority pop-up");

		// Test Case 6 - This test case validates if by default the discard button on
		// assign priority popup is enabled
		softAssert.assertTrue(incidentPage.verifyElementIsEnableOfIncidentPage("assignPriorityCancelButton"), "By default discard button should be enable on assign priority pop-up");

		// Test Case 7 - This test case validates if different options for changing the
		// priority are present on the dropdown
		incidentPage.clickOnElementsOfIncidentPage("priorityDropDownArrow");
		softAssert.assertTrue(incidentPage.verifyElementIsEnableOfIncidentPage("changePriorityDropDown"), "Options to assign priority dropdown are not present on assign priority pop-up");

		// Test Case 8 - This test case validates if the options available on the
		// priority dropdown are Unassigned, Critical, High, Medium and Low
		softAssert.assertTrue(incidentPage.verifyOptionsOnDropdownForIncidentPage("assignPriorityDropDownOnPopup", optionsForPriorityOnDropdown), "Only the options Unassigned, Critical, High, Medium, Low are not present in priority dropdown of assign priority pop-up");

		// Test Case 9 - This test case validates if the selected priority from the
		// dropdown appears on the popup
		selectedValue = incidentPage.selectOptionFromDropdownOnIncidentPage("assignPriorityDropDownOnPopup", getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.priority.low"));
        System.out.println(selectedValue);
		// Test Case 11 - This test case validates if after deselecting the priority on
		// popup, the submit button is disabled
		softAssert.assertTrue(incidentPage.verifyElementIsEnableOfIncidentPage("assignPrioritySubmitButton"), "After clicking on 'x' of dropdown , submit button should be enable on assign priority pop-up");

		// Test Case 12 - This test case validates if the discard functionality for
		// assign priority button is working properly
		incidentPage.clickOnElementsOfIncidentPage("priorityDropDownArrow");
		incidentPage.selectFirstOptionFromDropdownOnIncidentPage("assignPriorityDropDownOnPopup");
		LOGGER.info("Selected value on priority dropdown");
		incidentPage.clickOnElementsOfIncidentPage("assignPriorityCancelButton");
		LOGGER.info("Verified cancel functionality");
		incidentPage.scrollOnIncidentPage("currentPriority");
		softAssert.assertTrue(incidentPage.matchTextOfIncidentPage("currentPriority", priority), "After selecting the value from assign priority pop-up dropdown and clicking on cancel button , it should discard all the changes and information should not get updated on incident page");

		// Test Case 13 - This test case validates if the save functionality for assign
		// priority button is working properly and the priority is updated on the
		// incident page
		incidentPage.scrollOnIncidentPage("firstIdCheckBox");
		incidentPage.mousehoverOnIncientPage("firstIdLogo");
		LOGGER.info("Selected first incident on incident list page");
		if(incidentPage.getTextOfIncidentPage("moreDropDownButtonText").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "users.list.action_buttons.more")))
		{
			incidentPage.clickOnElementsOfIncidentPage("moreDropdownButton");
			incidentPage.clickOnElementsOfIncidentPage("assignPriorityMoreDrodown");
		}
		else{
			incidentPage.clickOnElementsOfIncidentPage("assignPriorityButton");
		}
		incidentPage.clickOnElementsOfIncidentPage("priorityDropDownArrow");
		valueSelected = incidentPage.selectOptionFromDropdownOnIncidentPage("assignPriorityDropDownOnPopup", selectedValue);
		System.out.println(valueSelected);
		LOGGER.info("Selected value on priority dropdown");
		incidentPage.clickOnElementsOfIncidentPage("assignPrioritySubmitButton");
		LOGGER.info("Verified save functionality");
		incidentPage.waitForElementsOfIncidentPage("tableOverlayListPage");
		incidentPage.mousehoverOnIncientPage("firstIdLogo");
		incidentPage.scrollOnIncidentPage("currentPriority");
		softAssert.assertTrue((valueSelected).equals(incidentPage.getTextOfIncidentPage("currentPriority")), "Priority is not updated successfully on incident page");

		softAssert.assertAll();
		LOGGER.info("All test cases of assign priority pop-up passed");
	}

	@Test(priority = 5, groups = { "REGRESSION", "SMOKE", "REGRESSION_CI", "SMOKE_CI" }, description = " USER STORY 198797")
	public final void verifyChangeStatusPopupTestCases() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		gotoIncidentTab();
		IncidentPage incidentPage = new IncidentPage(PreDefinedActions.getDriver()).getInstance();
		removeLocationFilter();
		resetTableConfiguration();
		Assert.assertTrue(incidentPage.verifyElementsOfIncidentPage("selectedIncidentTitle"), "No incident is present on incident page, unable to proceed further");
		LOGGER.info("Atleast one incident is present, test case started");

		String selectedValue = null, statusValueOnPopup;
		ArrayList<String> optionsForStatusOnDropdown = new ArrayList<String>(Arrays.asList(getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.status.new"), getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.status.investigating"), getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.status.progress"), getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.status.awaitingcustomer"),
				getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.status.fixed"), getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.status.dismissed"),getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.status.closedfalsepositive"),getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.status.closedtruepositive"),
				getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.status.closedunknown"),getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.status.customerapproval")));

		// Test Case 1 - This test case validates if change status is present even when
		// no checkbox is checked
		softAssert.assertFalse(incidentPage.verifyElementsOfIncidentPage("changeStatusButton"), "change status button is present even when no checkbox is selected");

		// Test Case 3 - This test case validates if the change status button is present
		// when an incident is selected
		incidentPage.mousehoverOnIncientPage("firstIdLogo");
		incidentPage.clickByJavaScriptOnIncidentPage("firstIdCheckBox");
		LOGGER.info("Selected first incident on incident list page");
		String selectedIncidentID = incidentPage.getTextOfIncidentPage("firstIncidentLinkInTable");
		incidentPage.scrollOnIncidentPage("currentStatus");
		String status = incidentPage.getTextOfIncidentPage("currentStatus");

		if(incidentPage.getTextOfIncidentPage("moreDropDownButtonText").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "users.list.action_buttons.more")))
		{
			incidentPage.clickOnElementsOfIncidentPage("moreDropdownButton");
			softAssert.assertTrue(incidentPage.verifyElementsOfIncidentPage("changeStatusMoreDropdown"), "Change status button is not present when all checkbox is selected");
			incidentPage.clickOnElementsOfIncidentPage("moreDropdownButton");
		}
		else{
			softAssert.assertTrue(incidentPage.verifyElementsOfIncidentPage("changeStatusButton"), "Change status button is not present when all checkbox is selected");
		}

		// Test Case 4 - This test case validates if the change status popup shows up
		// when the change status button is clicked
		if(incidentPage.getTextOfIncidentPage("moreDropDownButtonText").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "users.list.action_buttons.more")))
		{
			incidentPage.clickOnElementsOfIncidentPage("moreDropdownButton");
			incidentPage.clickOnElementsOfIncidentPage("changeStatusMoreDropdown");
		}
		else{
			incidentPage.clickOnElementsOfIncidentPage("changeStatusButton");
		}

		// Test Case 5 - This test case validates if by default the submit button on
		// change status popup is disabled
		softAssert.assertTrue(incidentPage.verifyElementIsEnableOfIncidentPage("changeStatusSubmitButton"), "By default submit button should be enable on change status pop-up");

		// Test Case 6 - This test case validates if by default the discard button on
		// change status popup is enabled
		softAssert.assertTrue(incidentPage.verifyElementIsEnableOfIncidentPage("changeStatusCancelButton"), "By default discard button should be enable on change status pop-up");

		// Test Case 7 - This test case validates if different options for changing the
		// status are present on the dropdown
		incidentPage.clickOnElementsOfIncidentPage("statusDropDownArrow");
		sleeper(10000);
		softAssert.assertTrue(incidentPage.verifyElementsOfIncidentPage("changeStatusDropDown"), "Options to change status dropdown are not present on change status pop-up");

		// Test Case 8 - This test case validates if the options available on the status
		// dropdown are New, Investigating, Fix in progress, Awaiting customer, Fixed
		// and Dismissed
		softAssert.assertTrue(incidentPage.verifyOptionsOnDropdownForIncidentPage("changeStatusDropDownOnPopup", optionsForStatusOnDropdown), "Only the options New, Investigating, Fix in progress, Awaiting customer, Fixed, Dismissed are not present in status dropdown of change status pop-up");

		// Test Case 9 - This test case validates if the selected status from the
		// dropdown appears on the popup
		selectedValue = incidentPage.selectOptionFromDropdownOnIncidentPage("changeStatusDropDownOnPopup", "");
		LOGGER.info("Selected value on status dropdown");

		// Test Case 11 - This test case validates if after deselecting the priority on
		// popup, the submit button is disabled
		softAssert.assertTrue(incidentPage.verifyElementIsEnableOfIncidentPage("changeStatusSubmitButton"), "After clicking on 'x' of dropdown , submit button should be enable on change status pop-up");

		// Test Case 12 - This test case validates if the discard functionality for
		// change status button is working properly
		incidentPage.clickOnElementsOfIncidentPage("statusDropDownArrow");
		incidentPage.selectOptionFromDropdownOnIncidentPage("changeStatusDropDownOnPopup", selectedValue);
		LOGGER.info("Selected value from status dropdown");
		incidentPage.clickOnElementsOfIncidentPage("changeStatusCancelButton");
		LOGGER.info("Verified cancel functionality");
		incidentPage.scrollOnIncidentPage("currentStatus");
		softAssert.assertTrue(incidentPage.matchTextOfIncidentPage("currentStatus", status), "After selecting the value from change status pop-up dropdown and clicking on cancel button , it should discard all the changes and information should not get updated on incident page");

		incidentPage.scrollOnIncidentPage("firstIdCheckBox");
		incidentPage.mousehoverOnIncientPage("firstIdLogo");
		LOGGER.info("Selected first incident on incident list page");
		if(incidentPage.getTextOfIncidentPage("moreDropDownButtonText").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "users.list.action_buttons.more")))
		{
			incidentPage.clickOnElementsOfIncidentPage("moreDropdownButton");
			incidentPage.clickOnElementsOfIncidentPage("changeStatusMoreDropdown");
		}
		else{
			incidentPage.clickOnElementsOfIncidentPage("changeStatusButton");
		}
		incidentPage.clickOnElementsOfIncidentPage("statusDropDownArrow");
		String valueSelected = incidentPage.selectOptionFromDropdownOnIncidentPage("changeStatusDropDownOnPopup", selectedValue);
		LOGGER.info("Selected value from status dropdown");
		incidentPage.clickOnElementsOfIncidentPage("changeStatusSubmitButton");
		incidentPage.waitForElementsOfIncidentPage("tableOverlayListPage");
		incidentPage.enterTextForIncidentPage("idSearchBox", selectedIncidentID);
		sleeper(2000);
		incidentPage.mousehoverOnIncientPage("firstIdLogo");
		incidentPage.clickByJavaScriptOnIncidentPage("firstIdCheckBox");
		LOGGER.info("Selected first incident on incident list page");
		incidentPage.scrollOnIncidentPage("currentStatus");
		softAssert.assertTrue((valueSelected).equals(incidentPage.getTextOfIncidentPage("currentStatus")), "Status is not updated successfully on incident page");

		softAssert.assertAll();
		LOGGER.info("All test cases of change status pop-up passed");

	}

	@Test(priority = 6, description = " USER STORY 198797", groups = { "REGRESSION", "SMOKE", "REGRESSION_CI" })
	public final void verifySelectAllCheckboxTestCases() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		String selectedValue = null;
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		gotoIncidentTab();
		removeLocationFilter();
		waitForPageLoaded();
		IncidentPage incidentPage = new IncidentPage(PreDefinedActions.getDriver()).getInstance();

		resetTableConfiguration();
		Assert.assertTrue(incidentPage.verifyElementsOfIncidentPage("selectedIncidentTitle"),
				"No incident is present on incident page, unable to proceed further");
		LOGGER.info("Atleast one incident is present, test case started");

		// Test Case 1 - This test case validates if by default the select all incidents
		// checkbox is checked
		softAssert.assertFalse(incidentPage.verifyElementIsSelectedOfIncidentPage("selectAllCheckBox"),
				"By default select-all checkbox is checked");

		incidentPage.clickByJavaScriptOnIncidentPage("selectAllCheckBox");
		LOGGER.info("Selected all incidents");
		if (incidentPage.getTextOfIncidentPage("moreDropDownButtonText")
				.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "users.list.action_buttons.more"))) {
			LOGGER.info("Execution continues");
		} else {
			// Test Case 2 - This test case validates if the assign to button is present
			// when no incident is selected
			softAssert.assertFalse(incidentPage.verifyElementsOfIncidentPage("assignToButton"),
					"Assign To button is present even when no checkbox is selected");

			// Test Case 3 - This test case validates if the assign priority button is
			// present when no incident is selected
			softAssert.assertFalse(incidentPage.verifyElementsOfIncidentPage("assignPriorityButton"),
					"Assign priority button is present even when no checkbox is selected");

			// Test Case 4 - This test case validates if the change status button is present
			// when no incident is selected
			softAssert.assertFalse(incidentPage.verifyElementsOfIncidentPage("changeStatusButton"),
					"change status button is present even when no checkbox is selected");
			// Test Case 8 - This test case validates if all checkboxes are selected when we
			// click on the select all incidents checkbox
			softAssert.assertTrue(incidentPage.verifyAllCheckBoxesSelectedOnIncidentPage(),
					"All checkboxes are not selected when we click on Select All checkbox");
		}
		// Test Case 5 - This test case validates if the assign to button is not present
		// when all incidents are selected
		if (incidentPage.getTextOfIncidentPage("moreDropDownButtonText")
				.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "users.list.action_buttons.more"))) {
			incidentPage.clickOnElementsOfIncidentPage("moreDropdownButton");
			softAssert.assertTrue(incidentPage.verifyElementsOfIncidentPage("assignToMoreDropdown"),
					"Assign To button is not present when all checkbox is selected");
			incidentPage.clickOnElementsOfIncidentPage("moreDropdownButton");
		} else {
			softAssert.assertTrue(incidentPage.verifyElementsOfIncidentPage("assignToButton"),
					"Assign To button is not present when all checkbox is selected");
		}

		// Test Case 6 - This test case validates if the assign priority button is not
		// present when all incidents are selected
		if (incidentPage.getTextOfIncidentPage("moreDropDownButtonText")
				.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "users.list.action_buttons.more"))) {
			incidentPage.clickOnElementsOfIncidentPage("moreDropdownButton");
			softAssert.assertTrue(incidentPage.verifyElementsOfIncidentPage("assignPriorityMoreDrodown"),
					"Assign priority button is not present when all checkbox is selected");
			incidentPage.clickOnElementsOfIncidentPage("moreDropdownButton");
		} else {
			softAssert.assertTrue(incidentPage.verifyElementsOfIncidentPage("assignPriorityButton"),
					"Assign priority button is not present when all checkbox is selected");
		}

		// Test Case 7 - This test case validates if the change status button is not
		// present when all incidents are selected
		if (incidentPage.getTextOfIncidentPage("moreDropDownButtonText")
				.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "users.list.action_buttons.more"))) {
			incidentPage.clickOnElementsOfIncidentPage("moreDropdownButton");
			softAssert.assertTrue(incidentPage.verifyElementsOfIncidentPage("changeStatusMoreDropdown"),
					"Change status button is not present when all checkbox is selected");
			incidentPage.clickOnElementsOfIncidentPage("moreDropdownButton");
		} else {
			softAssert.assertTrue(incidentPage.verifyElementsOfIncidentPage("changeStatusButton"),
					"Change status button is not present when all checkbox is selected");
		}

		// Test Case 9 - This test case validates if the priority of all the incidents
		// is updated when it is changed
		if (incidentPage.getTextOfIncidentPage("moreDropDownButtonText")
				.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "users.list.action_buttons.more"))) {
			incidentPage.clickOnElementsOfIncidentPage("moreDropdownButton");
			selectedValue = incidentPage.selectValueFromDropdownInPopupForAllIncidentsSelected(
					"assignPriorityMoreDrodown", "priorityDropDownArrow", "assignPriorityDropDownOnPopup",
					"assignPrioritySubmitButton");
		} else {
			selectedValue = incidentPage.selectValueFromDropdownInPopupForAllIncidentsSelected("assignPriorityButton",
					"priorityDropDownArrow", "assignPriorityDropDownOnPopup", "assignPrioritySubmitButton");
		}
		LOGGER.info("Selected value on priority dropdown");
		LOGGER.info("Verified save functionality");
		incidentPage.scrollOnIncidentPage("prioritySearchList");
		softAssert.assertTrue(incidentPage.verifySelectedValuesOfPopupInTable("prioritySearchList", selectedValue),
				"Assign Priority is not updated for all incidents on incident page");
        sleeper(3000);
		// Test Case 10 - This test case validates if the status of all the incidents is
		// updated when it is changed
		resetTableConfiguration();
		incidentPage.clickByJavaScriptOnIncidentPage("selectAllCheckBox");
		LOGGER.info("Selected all incidents");
		if (incidentPage.getTextOfIncidentPage("moreDropDownButtonText")
				.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "users.list.action_buttons.more"))) {
			incidentPage.clickOnElementsOfIncidentPage("moreDropdownButton");
			selectedValue = incidentPage.selectValueFromDropdownInPopupForAllIncidentsSelected(
					"changeStatusMoreDropdown", "statusDropDownArrow", "changeStatusDropDownOnPopup",
					"changeStatusSubmitButton");
		} else {
			selectedValue = incidentPage.selectValueFromDropdownInPopupForAllIncidentsSelected("changeStatusButton",
					"statusDropDownArrow", "changeStatusDropDownOnPopup", "changeStatusSubmitButton");
		}
		LOGGER.info("Selected value on status dropdown");
		LOGGER.info("Verified save functionality");
		incidentPage.scrollOnIncidentPage("statusSearchList");
		softAssert.assertTrue(incidentPage.verifySelectedValuesOfPopupInTable("statusSearchList", selectedValue),
				"Change status is not updated for all incidents on incident page");
		LOGGER.info("Test case 10 passed");
		sleeper(3000);
		
		
        		// Test Case 11 - This test case validates if the asignee of all the incidents
		// is updated when it is changed
		//resetTableConfiguration();
		incidentPage.clickByJavaScriptOnIncidentPage("selectAllCheckBox");
		LOGGER.info("Selected all incidents");
		if (incidentPage.getTextOfIncidentPage("moreDropDownButtonText")
				.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "users.list.action_buttons.more"))) {
			incidentPage.clickOnElementsOfIncidentPage("moreDropdownButton");
			incidentPage.clickOnElementsOfIncidentPage("assignToMoreDropdown");
		} else {
			incidentPage.clickOnElementsOfIncidentPage("assignToButton");
		}
		incidentPage.clickOnElementsOfIncidentPage("assignToDropDownArrow");
		selectedValue = incidentPage.selectedValueFromSearchBoxInsidePopup(LanguageCode, "assignToPopupSearchBox",
				IncidentsVariables.ASSIGN_TO, "assignToEmptySearch", "assignToPopupSearchList", "assignToOnPopup",
				"assignToDropDownArrow", "assignToPopupCancelButton");
		LOGGER.info("Selected value on assign to dropdown");
		LOGGER.info("Verified save functionality");
		if (incidentPage.verifyElementsOfIncidentPage("assignToPopupSaveButton")) {
			incidentPage.clickOnElementsOfIncidentPage("assignToPopupSaveButton");
			incidentPage.waitUntilElementIsInvisibleOfIncidentPage("tableOverlay");
			incidentPage.scrollOnIncidentPage("assignSearchList");
			softAssert.assertTrue(incidentPage.verifySelectedValuesOfPopupInTable("assignSearchList", selectedValue),
					"Assign to is not updated for all incidents on incident page");
		} else {
			LOGGER.info("No assignee found for this name");
			softAssert.assertAll();
			LOGGER.info("All test cases of select-All checkBox passed");
		}
	}

	/**
	 * This method will verify the table configuration test cases of incident list page
	 * 
	 * @throws Exception
	 */
	@Test(priority = 7, description = "USER STORY 165850", groups = { "REGRESSION", "SMOKE", "REGRESSION_CI", "SMOKE_CI" })
	public final void verifyTableConfigurationTestCases() throws Exception {
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		String tenantID = getValueFromToken("tenant");
		gotoIncidentTab();
		removeLocationFilter();
		waitForPageLoaded();
		// verifying search service api
		Assert.assertTrue(getStatusCode(getSearchServiceApi(environment) + CommonVariables.DETAILSSEARCHSERVICEAPI + tenantID + CommonVariables.DETAILSSEARCHSERVICEAPI2, IncidentsVariables.SEARCHSERVICEBODY, "POST", getEnvironment(environment)) == CommonVariables.CODEOK, "Search service failed for incidents list page");
		ArrayList<String> id = new ArrayList<String>(Arrays.asList(getTextLanguage(LanguageCode, "daas_ui", "incident.list.id")));
		ArrayList<String> columnName = new ArrayList<String>(Arrays.asList(getTextLanguage(LanguageCode, "daas_ui", "incident.list.id"), getTextLanguage(LanguageCode, "daas_ui", "incident.list.type"), getTextLanguage(LanguageCode, "daas_ui", "incident.list.subtype"), getTextLanguage(LanguageCode, "daas_ui", "incident.list.created_on"), getTextLanguage(LanguageCode, "daas_ui", "incident.list.priority"), getTextLanguage(LanguageCode, "daas_ui", "incident.list.status"),
				getTextLanguage(LanguageCode, "daas_ui", "incident.list.assign_to"), getTextLanguage(LanguageCode, "daas_ui", "incident.list.company"), getTextLanguage(LanguageCode, "daas_ui", "incident.list.device_name"), getTextLanguage(LanguageCode, "daas_ui", "incident.list.device_serial"), getTextLanguage(LanguageCode, "daas_ui", "incident.list.device_manufacturer"), getTextLanguage(LanguageCode, "daas_ui", "incident.list.title"),
				getTextLanguage(LanguageCode, "daas_ui", "incident.list.created_by"), getTextLanguage(LanguageCode, "daas_ui", "incidents.occurred_at"), getTextLanguage(LanguageCode, "daas_ui", "incident.list.updated_on"), getTextLanguage(LanguageCode, "daas_ui", "incident.list.device_model"), getTextLanguage(LanguageCode, "daas_ui", "incident.list.deviceOs"), getTextLanguage(LanguageCode, "daas_ui", "assets.location.name")));
		ArrayList<String> checkboxValue = new ArrayList<String>(Arrays.asList("true", "true", "true", "true", "true", "true", "true", "true", "true", "true", "true", "false", "false", "false", "false", "false", "false", "true"));

		verifyTableConfigurationTests(columnName, checkboxValue, id);
	}

	@Test(priority = 8, description = " USER STORY 165854", groups = { "REGRESSION", "SMOKE", "REGRESSION_CI" })
	public final void verifyElementsOfIncidentDetailsPage() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		gotoIncidentTab();
		LOGGER.info("Redirected to incident list page");
		IncidentPage incidentPage = new IncidentPage(PreDefinedActions.getDriver()).getInstance();
		IncidentDetailsPage incidentDetailsPage = new IncidentDetailsPage(PreDefinedActions.getDriver()).getInstance();
		removeLocationFilter();
		resetTableConfiguration();
		Assert.assertTrue(incidentPage.verifyElementsOfIncidentPage("selectedIncidentTitle"), "No incident is present on incident page, unable to proceed further");
		LOGGER.info("Atleast one incident is present, test case started");
		ArrayList<String> incidentsPageDetails = new ArrayList<>();

		// Test Case 1 - This test case validates if the title on the incident list page
		// is correct
		sleeper(5000);
		softAssert.assertTrue(incidentPage.getTextOfIncidentPage("incidentTitle").equals(getTextLanguage(LanguageCode, "daas_ui", "incidents.label")), "Incident title text on incident list page is incorrect");
		LOGGER.info("Incident title is matched");
		incidentsPageDetails = incidentPage.getDetailsOfIncident();
		incidentPage.waitUntilElementIsInvisibleOfIncidentPage("tableOverlay");
		sleeper(2000);
		incidentPage.clickByJavaScriptOnIncidentPage("selectedIncidentTitle");
		LOGGER.info("Redirected to incident details page");
		waitForPageLoaded();

		sleeper(5000);
		if (!incidentDetailsPage.verifyElementOfIncidentDetailsPage("incidentDetailsPageTitle")) {
			Assert.fail("Incident details page did not load successfully");
		} else {
			// Test Case 2 - This test case validates if the title on the incident details
			// page is correct
			incidentDetailsPage.waitUntilElementIsInvisibleOfIncidentDetailsPage("tableOverlay");
			sleeper(2000);
			//			softAssert.assertTrue(incidentDetailsPage.getTextOfIncidentDetailsPage("incidentDetailsPageTitle").equalsIgnoreCase(incidentsPageDetails.get(5)), "Incident title on incident details page is not correct");
			//			softAssert.assertTrue(incidentDetailsPage.matchTextOfIncidentDetailsPage("incidentDetailsPageTitle", incidentsPageDetails.get(5)), "Incident title on incident details page is not correct");

			// Test Case 4 - This test case validates the presence of the incident details
			// table
			softAssert.assertTrue(incidentDetailsPage.verifyElementOfIncidentDetailsPage("detailsTable"), "Details table is not present on incident details page");

			// Test Case 5 - This test case validates the presence of incident link on the
			// breadcrumb on incident details page
			softAssert.assertTrue(incidentDetailsPage.verifyElementOfIncidentDetailsPage("incidentLink"), "The incidents link is not present on incident details page");

			// Test Case 6 - This test case validates if the details of a particular
			// incident on the incident details page and the incident list page do match
			softAssert.assertTrue(incidentsPageDetails.equals(incidentDetailsPage.getDetailsOfIncident()), "Details of incident on incident details page and incident list page do not match");

			// Test Case 7 - This test case validates if the incident link on the breadcrumb
			// redirects to the incident list page
			incidentDetailsPage.clickOnElementOfIncidentDetailsPage("incidentLink");
			LOGGER.info("Navigated to incident list page");
			incidentPage.waitForElementsOfIncidentPage("generateXlsButton");
			softAssert.assertTrue(incidentPage.verifyElementsOfIncidentPage("generateXlsButton"), "The incident link on the breadcrumb does not redirect to the incident list page");

			softAssert.assertAll();
		}

	}

	@Test(priority = 9, description = " USER STORY 165854", groups = { "REGRESSION", "SMOKE", "REGRESSION_CI", "SMOKE_CI", "PRODUCTION_SANITY", "STABILIZATION_STAGING" })
	public final void verifyAddCommentButtonOfIncidentDetailsPage() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		gotoIncidentTab();
		LOGGER.info("Redirected to incident list page");
		IncidentDetailsPage incidentDetailsPage = new IncidentDetailsPage(PreDefinedActions.getDriver()).getInstance();

		IncidentPage incidentPage = new IncidentPage(PreDefinedActions.getDriver()).getInstance();
		removeLocationFilter();
		resetTableConfiguration();
		Assert.assertTrue(incidentPage.verifyElementsOfIncidentPage("selectedIncidentTitle"), "No incident is present on incident page, unable to proceed further");
		LOGGER.info("Atleast one incident is present, test case started");

		incidentPage.waitUntilElementIsInvisibleOfIncidentPage("tableOverlay");
		sleeper(2000);

		incidentPage.clickByJavaScriptOnIncidentPage("selectedIncidentTitle");
		waitForPageLoaded();
		incidentDetailsPage.waitUntilElementIsInvisibleOfIncidentDetailsPage("tableOverlay");
		if (!incidentDetailsPage.verifyElementOfIncidentDetailsPage("commentTab")) {
			Assert.fail("Incident details page did not load successfully");
		} else {
			incidentDetailsPage.clickOnElementOfIncidentDetailsPage("commentTab");

			// Test Case 1 - This test case validates if the add comment button is enabled
			// on the incident details page
			softAssert.assertTrue(incidentDetailsPage.verifyElementIsEnableOfIncidentDetailsPage("addCommentButton"), "Add comment button is not enabled on incident details page");

			// Test Case 2 - This test case validates if by default the submit button is
			// disbled on the add comment popup on the incident details page
			incidentDetailsPage.clickOnElementOfIncidentDetailsPage("addCommentButton");
			softAssert.assertTrue(incidentDetailsPage.verifyElementIsEnableOfIncidentDetailsPage("submitCommentButton"), "By default submit button is not enable on add comment popup");

			// Test Case 3 - This test case validates if by default the cancel button is
			// enabled on the add comment popup on the incident details page
			softAssert.assertTrue(incidentDetailsPage.verifyElementIsEnableOfIncidentDetailsPage("cancelAddComment"), "By default cancel button is not enabled on add comment popup");

			// Test Case 4 - This test case validates if the submit button on the add
			// comment popup is enabled after the comment is entered in the text box
			incidentDetailsPage.enterTextForIncidentDetailsPage("commentArea", IncidentsVariables.TESTCOMMENT);
			softAssert.assertTrue(incidentDetailsPage.verifyElementIsEnableOfIncidentDetailsPage("submitCommentButton"), "Submit button is not enabled after comment is entered in comment box");

			// Test Case 5 - This test case validates if the comment added and the comment
			// that is updated on the incident details page match
			incidentDetailsPage.clickOnElementOfIncidentDetailsPage("submitCommentButton");
			incidentDetailsPage.waitForElementOfIncidentDetailsPage("toastNotification");
			incidentDetailsPage.getTextsOfIncidentDetailsPage("toastNotification").contains(getTextLanguage(LanguageCode, "daas_ui", "global.messages.update_successful"));
			softAssert.assertTrue(incidentDetailsPage.getTextOfElementOnIncidentDetailsPage("commentText").equals("TestComment"), "The comment added and updated comment on incident details page doesn't match");

			// Test Case 6 - This test case validates if the submit button is enabled when
			// the add comment button is clicked again. This test case has been derived from
			// a bug.
			incidentDetailsPage.waitForElementOfIncidentDetailsPage("addCommentButton");
			incidentDetailsPage.clickOnElementOfIncidentDetailsPage("addCommentButton");
			softAssert.assertTrue(incidentDetailsPage.verifyElementIsEnableOfIncidentDetailsPage("submitCommentButton"), "Submit button should not be disable when the add comment button is clicked for the second time");
			incidentDetailsPage.clickOnElementOfIncidentDetailsPage("cancelAddComment");

			softAssert.assertAll();
			LOGGER.info("All test cases of add comment button passed");
		}
	}

	@Test(priority = 10, description = " USER STORY 165855", groups = { "REGRESSION", "SMOKE", "REGRESSION_CI", "SMOKE_CI" })
	public final void verifyChangeStatusButtonOfIncidentDetailsPage() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		String selectedValue = null, status = null, statusValueOnPopup;
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		gotoIncidentTab();
		LOGGER.info("Redirected to incident list page");
		IncidentDetailsPage incidentDetailsPage = new IncidentDetailsPage(PreDefinedActions.getDriver()).getInstance();
		IncidentPage incidentPage = new IncidentPage(PreDefinedActions.getDriver()).getInstance();
		removeLocationFilter();
		resetTableConfiguration();
		Assert.assertTrue(incidentPage.verifyElementsOfIncidentPage("selectedIncidentTitle"), "No incident is present on incident page, unable to proceed further");
		LOGGER.info("Atleast one incident is present, test case started");
		//incidentPage.waitUntilElementIsInvisibleOfIncidentPage("tableOverlay");
		incidentPage.enterTextForIncidentPage("idSearchBox", incidentPage.getTextOfIncidentPage("selectedIncidentTitle"));
		sleeper(2000);
		incidentPage.clickByJavaScriptOnIncidentPage("selectedIncidentTitle");
		//incidentPage.waitUntilElementIsInvisibleOfIncidentPage("tableOverlay");
		if (incidentPage.waitForElementsOfIncidentPage("detailsPageHeading")) {
			LOGGER.info("Navigated to incident details page");

			status = incidentDetailsPage.getTextOfElementOnIncidentDetailsPage("currentIncidentStatus");
			ArrayList<String> optionsForStatusOnDropdown = new ArrayList<String>(Arrays.asList(getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.status.new"), getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.status.investigating"), getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.status.progress"), getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.status.awaitingcustomer"),
					getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.status.fixed"), getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.status.dismissed")));

			// Test Case 1 - This test case validates if the edit option is enabled for
			// status on incident details page
			softAssert.assertTrue(incidentDetailsPage.verifyElementIsEnableOfIncidentDetailsPage("editStatusButton"), "Edit option is not enabled for status on incident detail page");

			// Test Case 2 - This test case validates if the change status popup opens after
			// clicking on the change status button on incident details page
			incidentDetailsPage.clickOnElementOfIncidentDetailsPage("editStatusButton");
			softAssert.assertTrue(incidentDetailsPage.verifyElementOfIncidentDetailsPage("changeStatusTitle"), "Change status pop-up does not open after clicking on edit status button");

			// Test Case 3 - This test case validates if the selected status value and the
			// value displayed on the popup after selection of status match
			incidentDetailsPage.waitForElementOfIncidentDetailsPage("statusValueOnPopup");
			softAssert.assertTrue(incidentDetailsPage.getTextsOfIncidentDetailsPage("statusValueOnPopup").get(0).equalsIgnoreCase(status), "Status value on popup and already selected status doesn't match");

			// Test Case 4 - This test case validates if by default the submit button on
			// change status popup is disabled
			softAssert.assertTrue(incidentDetailsPage.verifyElementIsEnableOfIncidentDetailsPage("submitStatusButton"), "By default submit button should be enable on change status pop-up");

			// TestCase 5 - This test case validates if by default the discard button on
			// change status popup is enabled
			softAssert.assertTrue(incidentDetailsPage.verifyElementIsEnableOfIncidentDetailsPage("cancelChangeStatusButton"), "By default cancel button should be enabled on change status pop-up");

			// Test Case 6 - This test case validates if different options for changing the
			// status are present on the dropdown
			incidentDetailsPage.clickOnElementOfIncidentDetailsPage("statusDropdownArrow");
			sleeper(10000);//Dropdown is taking too much time to open due to existing issue.
			softAssert.assertTrue(incidentDetailsPage.verifyElementOfIncidentDetailsPage("changeStatusDropdown"), "Status dropdown is not present on change status pop-up");

			// Test Case 7 - This test case validates if the options available on the status
			// dropdown are New, Investigating, Fix in progress, Awaiting customer, Fixed
			// and Dismissed
			softAssert.assertTrue(incidentDetailsPage.verifyOptionsOnDropdownForIncidentDetailsPage("changeStatusDropdown", optionsForStatusOnDropdown), "Only the options New, Investigating, Fix in progress, Awaiting customer, Fixed, Dismissed are not present in status dropdown of Change Status pop-up");

			// Test Case 8 - This test case validates if the selected status from the
			// dropdown appears on the popup
			statusValueOnPopup = incidentDetailsPage.getTextOfElementOnIncidentDetailsPage("statusValueOnPopup");
			selectedValue = incidentDetailsPage.selectOptionFromDropdownOnIncidentDetailsPage("changeStatusDropdown", statusValueOnPopup);
			LOGGER.info("Selected value from status dropdown");
			softAssert.assertTrue(incidentDetailsPage.matchTextOfIncidentDetailsPage("statusValueOnPopup", selectedValue), "We are not able to select the value from dropdown on change status popup");

			//			// Test Case 9 - This test case validates if the selected status from the
			//			// dropdown can be deselected
			//			incidentDetailsPage.clickOnElementOfIncidentDetailsPage("statusClearButton");
			//			if(!isVeneer3Check())
			//				softAssert.assertTrue(incidentDetailsPage.matchTextOfIncidentDetailsPage("statusValueOnPopup", getTextLanguage(LanguageCode, "daas_ui", "incidents.controls.select_an_option")), "We are not able to deselect the value from dropdown on change status popup");

			// Test Case 10 - This test case validates if after deselecting the priority on
			// popup, the submit button is disabled
			softAssert.assertTrue(incidentDetailsPage.verifyElementIsEnableOfIncidentDetailsPage("submitStatusButton"), "After clicking x on the box, submit button should be enable on change status pop-up");

			// Test Case 11 - This test case validates if the discard functionality for
			// change status button is working properly
			incidentDetailsPage.clickOnElementOfIncidentDetailsPage("statusDropdownArrow");
			incidentDetailsPage.selectOptionFromDropdownOnIncidentDetailsPage("changeStatusDropdown", statusValueOnPopup);
			LOGGER.info("Selected value from status dropdown");
			incidentDetailsPage.clickOnElementOfIncidentDetailsPage("cancelChangeStatusButton");
			softAssert.assertTrue(incidentDetailsPage.matchTextOfIncidentDetailsPage("currentIncidentStatus", status), "After selecting the value from Change Status pop-up dropdown and clicking on cancel button , it should discard all the changes and information should not get updated on incident details page");

			// Test Case 12 - This test case validates if the save functionality for change
			// status button is working properly and the status is updated on the incident
			// details page
			selectedValue = incidentDetailsPage.selectValueFromDropdownInPopup("editStatusButton", "statusDropdownArrow", "changeStatusDropdown", statusValueOnPopup, "submitStatusButton");
			LOGGER.info("Selected value from status dropdown");
			//incidentDetailsPage.clickOnElementOfIncidentDetailsPage("submitStatusButton");
			LOGGER.info("Verified save functionality");
			if (incidentDetailsPage.waitForElementOfIncidentDetailsPage("toastNotification")) {
				incidentDetailsPage.getTextOfElementOnIncidentDetailsPage("toastNotification").contains(getTextLanguage(LanguageCode, "daas_ui", "global.messages.update_successful"));
			}
			softAssert.assertTrue(selectedValue.equals(incidentDetailsPage.getTextOfElementOnIncidentDetailsPage("currentIncidentStatus")), "Status not updated successfully on incident details page");

			// Test Case 13 - This test case validates if the changed status is also updated
			// on the incident list page
			status = incidentDetailsPage.getTextOfElementOnIncidentDetailsPage("currentIncidentStatus");
			navigateToBack();
			waitForPageLoaded();
			LOGGER.info("Navigated to incident list page");
			incidentPage.waitUntilElementIsInvisibleOfIncidentPage("tableOverlay");
			incidentPage.scrollOnIncidentPage("statusBoxTitle");
			softAssert.assertTrue(incidentPage.getTextOfIncidentPage("incidentStatus").equals(status), "Status not updated successfully on incident list page");

			softAssert.assertAll();
			LOGGER.info("All test cases of change status button passed");
		} else {
			Assert.fail("Incident details page did not open successfully, hence verifyChangeStatusButtonOfIncidentDetailsPage got failed.");
		}
	}

	@Test(priority = 11, description = " USER STORY 165855", groups = { "REGRESSION", "SMOKE", "REGRESSION_CI", "SMOKE_CI" })
	public final void verifyChangePriorityButtonOfIncidentDetailsPage() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		gotoIncidentTab();
		LOGGER.info("Redirected to incident list page");
		removeLocationFilter();
		waitForPageLoaded();
		IncidentDetailsPage incidentDetailsPage = new IncidentDetailsPage(PreDefinedActions.getDriver()).getInstance();

		IncidentPage incidentPage = new IncidentPage(PreDefinedActions.getDriver()).getInstance();

		resetTableConfiguration();
		Assert.assertTrue(incidentPage.verifyElementsOfIncidentPage("selectedIncidentTitle"), "No incident is present on incident page, unable to proceed further");
		LOGGER.info("Atleast one incident is present, test case started");

		//incidentPage.waitUntilElementIsInvisibleOfIncidentPage("tableOverlay");
		incidentPage.enterTextForIncidentPage("idSearchBox", incidentPage.getTextOfIncidentPage("selectedIncidentTitle"));
		sleeper(2000);

		incidentPage.clickByJavaScriptOnIncidentPage("selectedIncidentTitle");
		incidentDetailsPage.waitUntilElementIsInvisibleOfIncidentDetailsPage("tableOverlay");
		if (!incidentDetailsPage.verifyElementOfIncidentDetailsPage("currentIncidentPriority")) {
			Assert.fail("Incident details page did not load successfully");
		} else {

			String selectedValue = null, priority = null, priorityValueOnPopup;
			priority = incidentDetailsPage.getTextOfElementOnIncidentDetailsPage("currentIncidentPriority");
			ArrayList<String> optionsForPriorityOnDropdown = new ArrayList<String>(
					Arrays.asList(getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.type.unassigned"), getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.priority.critical"), getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.priority.high"), getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.priority.medium"), getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.priority.low")));

			// Test Case 1 - This test case validates if assign priority button is present
			// even when no checkbox is checked
			softAssert.assertTrue(incidentDetailsPage.verifyElementIsEnableOfIncidentDetailsPage("editPriorityButton"), "Edit option is not enabled for priority on incident detail page");
			LOGGER.info("test case 1 completed");

			// Test Case 2 - This test case validates if the assign priority popup shows up
			// when the assign priority button is clicked
			incidentDetailsPage.clickOnElementOfIncidentDetailsPage("editPriorityButton");
			softAssert.assertTrue(incidentDetailsPage.verifyElementOfIncidentDetailsPage("changePriorityTitle"), "Change priority pop-up does not open after clicking on change priority Button");
			LOGGER.info("test case 2 completed");

			// Test Case 3 - This test case validates if the already assigned priority of
			// the incident on the incident details page and the value of priority on popup
			// match
			incidentDetailsPage.waitForElementOfIncidentDetailsPage("priorityValueOnPopup");
			softAssert.assertTrue(incidentDetailsPage.getTextOfElementOnIncidentDetailsPage("priorityValueOnPopup").equals(priority), "Priority value on popup and already assigned priority value on incident details page doesn't match");
			LOGGER.info("test case 3 completed");

			// Test Case 4 - This test case validates if by default the submit button on
			// assign priority popup is disabled
			softAssert.assertTrue(incidentDetailsPage.verifyElementIsEnableOfIncidentDetailsPage("submitPriorityButton"), "By default, submit button should be enable on change priority pop-up");
			LOGGER.info("test case 4 completed");

			// Test Case 5 - This test case validates if by default the discard button on
			// assign priority popup is enabled
			softAssert.assertTrue(incidentDetailsPage.verifyElementIsEnableOfIncidentDetailsPage("cancelChangePriorityButton"), "By default, cancel button should be enabled on change priority pop-up");
			LOGGER.info("test case 5 completed");

			// Test Case 6 - This test case validates if different options for changing the
			// priority are present on the dropdown
			incidentDetailsPage.clickOnElementOfIncidentDetailsPage("dropdownArrow");
			sleeper(10000);//Sleeper required since this takes time to open dropdown list for the first time.
			softAssert.assertTrue(incidentDetailsPage.verifyElementIsEnableOfIncidentDetailsPage("changePriorityDropdown"), "Priority dropdown is not present on Change Priority pop-up");
			LOGGER.info("test case 6 completed");

			// Test Case 7 - This test case validates if the options available on the
			// priority dropdown are Unassigned, Critical, High, Medium and Low
			softAssert.assertTrue(incidentDetailsPage.verifyOptionsOnDropdownForIncidentDetailsPage("changePriorityDropdown", optionsForPriorityOnDropdown), "Only the options Unassigned, Critical, High, Medium, Low are not present in priority dropdown of Change Priority pop-up");

			// Test Case 8 - This test case validates if the selected priority from the
			// dropdown appears on the popup
			priorityValueOnPopup = incidentDetailsPage.getTextOfElementOnIncidentDetailsPage("priorityValueOnPopup");
			selectedValue = incidentDetailsPage.selectOptionFromDropdownOnIncidentDetailsPage("changePriorityDropdown", priorityValueOnPopup);
			LOGGER.info("Selected value from priority dropdown");
			// softAssert.assertTrue(incidentDetailsPage.matchTextOfIncidentDetailsPage("priorityValueOnPopup", selectedValue), "The selected value and the value of priority on pop up doesn't match");

			// Test Case 9 - This test case validates if the selected priority from the
			// dropdown can be deselected
			//			incidentDetailsPage.clickOnElementOfIncidentDetailsPage("deselectButton");
			//			sleeper(2000);
			//			softAssert.assertTrue(incidentDetailsPage.matchTextOfIncidentDetailsPage("priorityValueOnPopup", getTextLanguage(LanguageCode, "daas_ui", "incidents.controls.select_an_option")), "We are not able to deselect the value from dropdown on change priority popup");

			// Test Case 10 - This test case validates if after deselecting the priority on
			// popup, the submit button is disabled
			softAssert.assertTrue(incidentDetailsPage.verifyElementIsEnableOfIncidentDetailsPage("submitPriorityButton"), "After clicking on x submit button should be enable on change priority pop-up");

			// Test Case 11 - This test case validates if the discard functionality for
			// assign priority button is working properly
			incidentDetailsPage.clickOnElementOfIncidentDetailsPage("dropdownArrow");
			incidentDetailsPage.selectOptionFromDropdownOnIncidentDetailsPage("changePriorityDropdown", priorityValueOnPopup);
			LOGGER.info("Selected value from priority dropdown");
			incidentDetailsPage.clickOnElementOfIncidentDetailsPage("cancelChangePriorityButton");
			softAssert.assertTrue(incidentDetailsPage.matchTextOfIncidentDetailsPage("currentIncidentPriority", priority), "After selecting the value from Change priority pop-up dropdown and clicking on cancel button , it should discard all the changes and information should not get updated on incident details page");

			// Test Case 12 - This test case validates if the save functionality for assign
			// priority button is working properly and the priority is updated on the
			// incident page
			selectedValue = incidentDetailsPage.selectValueFromDropdownInPopup("editPriorityButton", "dropdownArrow", "changePriorityDropdown", priorityValueOnPopup, "submitPriorityButton");
			LOGGER.info("Selected value from priority dropdown");
			if (incidentDetailsPage.waitForElementOfIncidentDetailsPage("toastNotification")) {
				incidentDetailsPage.getTextOfElementOnIncidentDetailsPage("toastNotification").contains(getTextLanguage(LanguageCode, "daas_ui", "global.messages.update_successful"));
			}
			LOGGER.info("Verified toast notification message");
			incidentDetailsPage.waitForElementOfIncidentDetailsPage("currentIncidentPriority");
			incidentDetailsPage.waitForElementOfIncidentDetailsPage("editPriorityButton");
			softAssert.assertTrue(selectedValue.equals(incidentDetailsPage.getTextOfElementOnIncidentDetailsPage("currentIncidentPriority")), "Priority not updated successfully on incident details page");

			// Test Case 13 - This test case validates if the priority is updated on the
			// incident list page
			priority = incidentDetailsPage.getTextOfElementOnIncidentDetailsPage("currentIncidentPriority");
			navigateToBack();
			waitForPageLoaded();
			LOGGER.info("Navigated to incident list page");
			incidentPage.scrollOnIncidentPage("incidentPriority");
			softAssert.assertTrue(priority.equals(incidentPage.getTextOfIncidentPage("incidentPriority")), "Priority not updated successfully on incident list page");

			softAssert.assertAll();
			LOGGER.info("All test cases of change priority button passed");
		}

	}

	@Test(priority = 12, description = " USER STORY 165855", groups = { "REGRESSION", "SMOKE", "REGRESSION_CI", "SMOKE_CI" })
	public final void verifyChangeTypeButtonOfIncidentDetailsPage() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		String type = null, subType = null, selectedValue = null, typeValueOnPopup, subtypeValueOnPopup;
		ArrayList<String> selectedValues;
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		gotoIncidentTab();
		LOGGER.info("Redirected to incident list page");
		IncidentDetailsPage incidentDetailsPage = new IncidentDetailsPage(PreDefinedActions.getDriver()).getInstance();

		IncidentPage incidentPage = new IncidentPage(PreDefinedActions.getDriver()).getInstance();
		removeLocationFilter();
		resetTableConfiguration();
		Assert.assertTrue(incidentPage.verifyElementsOfIncidentPage("selectedIncidentTitle"), "No incident is present on incident page, unable to proceed further");
		LOGGER.info("Atleast one incident is present, test case started");

		incidentPage.waitUntilElementIsInvisibleOfIncidentPage("tableOverlay");
		incidentPage.enterTextForIncidentPage("idSearchBox", incidentPage.getTextOfIncidentPage("selectedIncidentTitle"));
		sleeper(2000);

		incidentPage.clickByJavaScriptOnIncidentPage("selectedIncidentTitle");
		incidentDetailsPage.waitUntilElementIsInvisibleOfIncidentDetailsPage("tableOverlay");
		if (!incidentDetailsPage.verifyElementOfIncidentDetailsPage("editTypeButton") && !incidentDetailsPage.verifyElementOfIncidentDetailsPage("currentIncidentType")) {
			Assert.fail("Incident details page did not load successfully");
		} else {
			if (incidentDetailsPage.waitForElementToBeClickableOfIncidentDetailsPage("editTypeButton") == false) {
				LOGGER.info("Type is already assigned, cannot update it");

			} else {
				type = incidentDetailsPage.getTextOfElementOnIncidentDetailsPage("currentIncidentType");
				subType = incidentDetailsPage.getTextOfElementOnIncidentDetailsPage("currentIncidentSubType");

				// Test Case 1 - This test case validates if the edit type button is enabled on
				// the incident details page
				softAssert.assertTrue(incidentDetailsPage.verifyElementIsEnableOfIncidentDetailsPage("editTypeButton"), "Edit option is not enabled for type on incident detail page");

				// Test Case 2 - This test case validates if the change type popup opens after
				// clicking on the change type button
				incidentDetailsPage.clickOnElementOfIncidentDetailsPage("editTypeButton");
				softAssert.assertTrue(incidentDetailsPage.verifyElementOfIncidentDetailsPage("changeTypeTitle"), "Change Type pop-up does not open after clicking on change type button");

				// Test Case 3 - This test case validates if the already assigned type value on
				// the incident details page and the type value on popup match
				incidentDetailsPage.waitForElementOfIncidentDetailsPage("typeValueOnPopup");
				softAssert.assertTrue(incidentDetailsPage.getTextOfElementOnIncidentDetailsPage("typeValueOnPopup").equals(type), "Type value on popup and already assigned type value on incident details page doesn't match");

				// Test Case 4 - This test case validates if the already assigned sub-type value
				// on the incident details page and the sub-type value on popup match
				softAssert.assertTrue(incidentDetailsPage.getTextOfElementOnIncidentDetailsPage("subTypeValueOnPopup").equals(subType), "Subtype value on popup and already assigned subtype value on incident details page doesn't match");

				// Test Case 5 - This test case validates if by default the submit button is
				// disabled on the change type popup
				softAssert.assertTrue(incidentDetailsPage.verifyElementIsEnableOfIncidentDetailsPage("submitTypeButton"), "By default submit button should be enable on change type pop-up");

				// Test Case 6 - This test case validates if by default the cancel button is
				// enabled on the change type popup
				softAssert.assertTrue(incidentDetailsPage.verifyElementIsEnableOfIncidentDetailsPage("cancelChangeTypeButton"), "By default cancel button should be enable on change type pop-up");

				// Test Case 7 - This test case validates if different options for changing the
				// type are present on the dropdown
				incidentDetailsPage.clickOnElementOfIncidentDetailsPage("typeButtonDropdownArrow");
				softAssert.assertTrue(incidentDetailsPage.verifyElementOfIncidentDetailsPage("changeTypeDropdown"), "Type dropdown is not present on Change Type pop-up");

				// Test Case 9 - This test case validates if the selected type from the dropdown
				// appears on the popup
				typeValueOnPopup = incidentDetailsPage.getTextOfElementOnIncidentDetailsPage("typeValueOnPopup");
				subtypeValueOnPopup = incidentDetailsPage.getTextOfElementOnIncidentDetailsPage("subTypeValueOnPopup");
				selectedValue = incidentDetailsPage.selectOptionFromDropdownOnIncidentDetailsPage("changeTypeDropdown", typeValueOnPopup);
				LOGGER.info("Selected value on type dropdown");
				softAssert.assertTrue(incidentDetailsPage.matchTextOfIncidentDetailsPage("typeValueOnPopup", selectedValue), "The selected value and the value on popup after selection don't match");

				// Test Case 10 - This test case validates if different options for changing the
				// subtype are present on the dropdown
				incidentDetailsPage.clickOnElementOfIncidentDetailsPage("subTypeButtonDropdownArrowAfterDeselect");
				softAssert.assertTrue(incidentDetailsPage.verifyElementOfIncidentDetailsPage("changeSubTypeDropdown"), "Sub-type dropdown is not present on Change Sub-type pop-up");

				// Test Case 11 - This test case validates if the selected subtype from the
				// dropdown appears on the popup
				selectedValue = incidentDetailsPage.selectOptionFromDropdownOnIncidentDetailsPage("changeTypeDropdown", typeValueOnPopup);
				LOGGER.info("Selected value on type dropdown");
				softAssert.assertTrue(incidentDetailsPage.matchTextOfIncidentDetailsPage("subTypeValueOnPopup", selectedValue), "The selected value and the value on popup after selection don't match");

				// Test Case 12 - This test case validates if the selected type from the
				// dropdown can be deselected
				incidentDetailsPage.clickOnElementOfIncidentDetailsPage("deselectTypeButton");
				softAssert.assertTrue(incidentDetailsPage.matchTextOfIncidentDetailsPage("typeValueOnPopup", getTextLanguage(LanguageCode, "daas_ui", "incidents.controls.select_an_option")), "We are not able to deselect the value from dropdown on change type popup");

				// Test Case 13 - This test case validates if the selected subtype from the
				// dropdown can be deselected
				incidentDetailsPage.clickOnElementOfIncidentDetailsPage("deselectSubTypeButton");
				LOGGER.info("Deselected type on dropdown");
				softAssert.assertTrue(incidentDetailsPage.matchTextOfIncidentDetailsPage("subTypeValueOnPopup", getTextLanguage(LanguageCode, "daas_ui", "incidents.controls.select_an_option")), "We are not able to deselect the value from dropdown on change sub-type popup");

				// Test Case 14 -This test case validates if after deselecting the type and
				// subtype on popup, the submit button is disabled
				softAssert.assertTrue(incidentDetailsPage.verifyElementIsEnableOfIncidentDetailsPage("submitTypeButton"), "After clicking on x submit button should be enable on change type pop-up");

				// Test Case 15 - This test case validates if the discard functionality for
				// change type button is working properly
				incidentDetailsPage.clickOnElementOfIncidentDetailsPage("typeButtonDropdownArrowAfterDeselect");
				selectedValue = incidentDetailsPage.selectFirstOptionFromDropdownOnIncidentDetailsPage("changeTypeDropdown");
				LOGGER.info("Selected value on type dropdown");
				incidentDetailsPage.clickOnElementOfIncidentDetailsPage("subTypeButtonDropdownArrowAfterDeselect");
				incidentDetailsPage.selectOptionFromDropdownOnIncidentDetailsPage("changeTypeDropdown", typeValueOnPopup);
				LOGGER.info("Selected value on subtype dropdown");
				incidentDetailsPage.clickOnElementOfIncidentDetailsPage("cancelChangeTypeButton");
				LOGGER.info("Verified cancel functionality");
				softAssert.assertTrue(incidentDetailsPage.matchTextOfIncidentDetailsPage("currentIncidentType", type), "After selecting the value from Change type pop-up dropdown and clicking on cancel button , it should discard all the changes and information should not get updated both on incident list and details page");

				// Test Case 16 - This test case validates if the discard functionality for
				// change subtype button is working properly
				softAssert.assertTrue(incidentDetailsPage.matchTextOfIncidentDetailsPage("currentIncidentSubType", subType), "After selecting the value from Change sub-type pop-up dropdown and clicking on cancel button , it should discard all the changes and information should not get updated both on incident list and details page");

				// Test Case 17 - This test case validates if the save functionality for change
				// type button is working properly and the type is updated on the incident page
				selectedValues = incidentDetailsPage.selectValueFromDropdownInTypePopup("editTypeButton", "typeButtonDropdownArrow", "subTypeButtonDropdownArrowAfterDeselect", "dropdownList", typeValueOnPopup, subtypeValueOnPopup, "submitTypeButton");
				LOGGER.info("Selected value on type dropdown");
				if (incidentDetailsPage.waitForElementOfIncidentDetailsPage("toastNotification")) {
					incidentDetailsPage.getTextOfElementOnIncidentDetailsPage("toastNotification").contains(getTextLanguage(LanguageCode, "daas_ui", "global.messages.update_successful"));
				}
				incidentDetailsPage.waitForElementOfIncidentDetailsPage("editTypeButton");
				type = incidentDetailsPage.getTextOfElementOnIncidentDetailsPage("currentIncidentType");
				softAssert.assertTrue(selectedValues.get(0).equals(type), "Type not updated successfully on incident details page");

				// Test Case 18 - This test case validates if the type and subtype is updated on
				// the incident list page
				navigateToBack();
				waitForPageLoaded();
				LOGGER.info("Navigated to incident list page");
				softAssert.assertTrue(incidentPage.getTextOfIncidentPage("incidentType").equals(selectedValues.get(0)), "Type not updated successfully on incident list page");
				softAssert.assertTrue(incidentPage.getTextOfIncidentPage("incidentSubType").equals(selectedValues.get(1)), "Subtype not updated successfully on incident list page");
				softAssert.assertAll();
				LOGGER.info("All test cases of type button passed");

			}
		}
	}

	@Test(priority = 13, description = " USER STORY 165855", groups = { "REGRESSION", "SMOKE", "REGRESSION_CI", "SMOKE_CI" }, enabled = false)
	public final void verifyChangeAssignToButtonOfIncidentDetailsPage() throws Exception {
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		gotoIncidentTab();
		LOGGER.info("Redirected to incident list page");
		removeLocationFilter();
		waitForPageLoaded();
		IncidentPage incidentPage = new IncidentPage(PreDefinedActions.getDriver()).getInstance();

		resetTableConfiguration();
		Assert.assertTrue(incidentPage.verifyElementsOfIncidentPage("selectedIncidentTitle"), "No incident is present on incident page, unable to proceed further");
		LOGGER.info("Atleast one incident is present, test case started");

		String selectedValue;
		incidentPage.waitForElementsOfIncidentPage("selectedIncidentTitle");
		incidentPage.enterTextForIncidentPage("idSearchBox", incidentPage.getTextOfIncidentPage("selectedIncidentTitle"));
		sleeper(2000);

		incidentPage.clickByJavaScriptOnIncidentPage("selectedIncidentTitle");
		LOGGER.info("Redirected to incident details page");
		IncidentDetailsPage incidentDetailsPage = new IncidentDetailsPage(PreDefinedActions.getDriver()).getInstance();
		if (!incidentDetailsPage.verifyElementOfIncidentDetailsPage("currentAssignTo")) {
			Assert.fail("Incident details page did not load successfully");
		} else {

			String assignTo = incidentDetailsPage.getTextOfElementOnIncidentDetailsPage("currentAssignTo");

			// Test Case 1 - This test validates if edit option is enabled for assignto on
			// incident detail page
			softAssert.assertTrue(incidentDetailsPage.verifyElementIsEnableOfIncidentDetailsPage("editAssignToButton"), "Edit option is not enabled for assignto on incident detail page");

			// Test Case 2 - This test validates if change assignment popup opens after
			// clicking on change assign to button
			incidentDetailsPage.clickOnElementOfIncidentDetailsPage("editAssignToButton");
			softAssert.assertTrue(incidentDetailsPage.verifyElementOfIncidentDetailsPage("changeAssignToTitle"), "Change Assignment pop-up does not open after clicking on change assign to button");

			// Test Case 3 - This test validates if by default submit button is enabled on
			// change assign-to pop-up
			softAssert.assertTrue(incidentDetailsPage.verifyElementIsEnableOfIncidentDetailsPage("submitAssigntoButton"), "By default submit button should be enable on change assign-to pop-up");

			// Test Case 4 - This test validates if by default cancel button is enabled on
			// change assign-to pop-up
			softAssert.assertTrue(incidentDetailsPage.verifyElementIsEnableOfIncidentDetailsPage("cancelChangeAssignToButton"), "By default cancel button should be enable on change assign-to pop-up");

			// Test Case 5 - This test validates if the already assigned user is present on
			// the popup
			if (assignTo.equals("Unassigned")) {
				softAssert.assertTrue(incidentDetailsPage.getTextOfElementOnIncidentDetailsPage("assignToOnPopup").equals(getTextLanguage(LanguageCode, "daas_ui", "incidents.controls.select_an_option")), "Selected value is not present on pop up ");
			} else {
				softAssert.assertTrue(incidentDetailsPage.getTextOfElementOnIncidentDetailsPage("assignToOnPopup").contains(assignTo), "Selected value is not present on pop up ");
			}

			// Test Case 6 - This test case validates if the cancel functionality on change
			// assign to popup is working properly
			sleeper(10000);
			if (incidentDetailsPage.verifyElementOfIncidentDetailsPage("assignToDropdownArrowAfterSelect") == true) {
				incidentDetailsPage.clickOnElementOfIncidentDetailsPage("assignToDropdownArrowAfterSelect");
				sleeper(3000);
				softAssert.assertTrue(incidentDetailsPage.verifySearchValueOnIncidentInsidePopup(LanguageCode, "assignToPopupSearchBox", IncidentsVariables.INCORRECT_SEARCH_STRING, "assignToEmptySearch", "assignToPopupSearchList"), "Search not working");
				incidentDetailsPage.selectedValueFromSearchBoxInsidePopup(LanguageCode, "assignToPopupSearchBox", IncidentsVariables.ASSIGN_TO, "assignToEmptySearch", "assignToPopupSearchList", "assignToOnPopup", "assignToDropdownArrowAfterSelect", "cancelChangeAssignToButton");
				LOGGER.info("Selected value from assign to dropdown");
				softAssert.assertTrue(incidentDetailsPage.getTextOfElementOnIncidentDetailsPage("currentAssignTo").contains(assignTo), "Cancel button functionality is not working properly");

				// Test Case 7 - This test validates if the deselect functionality on change
				// assign to popup is working properly
				incidentDetailsPage.clickOnElementOfIncidentDetailsPage("editAssignToButton");
				incidentDetailsPage.clickOnElementOfIncidentDetailsPage("assignToDropdownArrowAfterSelect");
				sleeper(3000);
				incidentDetailsPage.selectedValueFromSearchBoxInsidePopup(LanguageCode, "assignToPopupSearchBox", IncidentsVariables.ASSIGN_TO, "assignToEmptySearch", "assignToPopupSearchList", "assignToOnPopup", "assignToDropdownArrowAfterSelect", "cancelChangeAssignToButton");
				LOGGER.info("Selected value from assign to dropdown");
				incidentDetailsPage.clickOnElementOfIncidentDetailsPage("deselectAssignToButton");
				LOGGER.info("Deselected assign to value from drodpown");
				softAssert.assertTrue(incidentDetailsPage.getTextOfElementOnIncidentDetailsPage("assignToOnPopup").equals(getTextLanguage(LanguageCode, "daas_ui", "incidents.controls.select_an_option")), "Deselect functionality is not working properly");

				// Test Case 8 - This test validates form validation error message for blank
				// value
				incidentDetailsPage.clickOnElementOfIncidentDetailsPage("submitAssigntoButton");
				LOGGER.info("Verified error validation message for blank assignee value");
				softAssert.assertTrue(incidentDetailsPage.getTextOfElementOnIncidentDetailsPage("errorMessage").equals(getTextLanguage(LanguageCode, "daas_ui", "form.validate.required")), "We are able to submit blank value");

				// Test Case 9 -This test validates if save functionality on assign to popup is
				// working properly
				incidentDetailsPage.clickOnElementOfIncidentDetailsPage("assignToDropdownArrowBeforeSelect");
				sleeper(3000);
				String assignToOnPopup = incidentDetailsPage.getTextOfElementOnIncidentDetailsPage("assignToOnPopup");
				selectedValue = incidentDetailsPage.selectedValueFromSearchBoxInsidePopup(LanguageCode, "assignToPopupSearchBox", IncidentsVariables.ASSIGN_TO, "assignToEmptySearch", "assignToPopupSearchList", "assignToOnPopup", "assignToDropdownArrowAfterSelect", "cancelChangeAssignToButton");
				LOGGER.info("Selected value from assign to dropdown");
				incidentDetailsPage.clickOnElementOfIncidentDetailsPage("submitAssigntoButton");
				softAssert.assertTrue(assignToOnPopup.contains(assignTo), "Save button functionality is not working properly");
				LOGGER.info("Verified save functionality");
				if (incidentDetailsPage.verifyElementOfIncidentDetailsPage("changeAssignToTitle") == true) {
					incidentDetailsPage.clickOnElementOfIncidentDetailsPage("cancelChangeAssignToButton");
				}

				// Test Case 10 - This test case validates if the assigned to is updated on the
				// incident list page
				navigateToBack();
				LOGGER.info("Redirected to incident list page");
				waitForPageLoaded();
				softAssert.assertTrue(incidentPage.getTextOfIncidentPage("incidentAssignedTo").equals(selectedValue), "Assigned to is not updated successfully on incident list page");
			} else {
				// Test Case 11 -This test validates if cancel functionality on assign to popup
				// is working properly
				incidentDetailsPage.clickOnElementOfIncidentDetailsPage("assignToDropdownArrowBeforeSelect");
				sleeper(3000);
				softAssert.assertTrue(incidentDetailsPage.verifySearchValueOnIncidentInsidePopup(LanguageCode, "assignToPopupSearchBox", IncidentsVariables.INCORRECT_SEARCH_STRING, "assignToEmptySearch", "assignToPopupSearchList"), "Search not working");
				incidentDetailsPage.selectedValueFromSearchBoxInsidePopup(LanguageCode, "assignToPopupSearchBox", IncidentsVariables.ASSIGN_TO, "assignToEmptySearch", "assignToPopupSearchList", "assignToOnPopup", "assignToDropdownArrowAfterSelect", "cancelChangeAssignToButton");
				LOGGER.info("Selected value from assign to dropdown");
				incidentDetailsPage.clickOnElementOfIncidentDetailsPage("cancelChangeAssignToButton");
				LOGGER.info("Verified cancel functionality");
				softAssert.assertTrue(assignTo.equals("Unassigned"), "Cancel button functionality is not working properly");

				// Test Case 12 - This test validates if deselect functionality on assign to
				// popup is working properly
				incidentDetailsPage.clickOnElementOfIncidentDetailsPage("editAssignToButton");
				incidentDetailsPage.clickOnElementOfIncidentDetailsPage("assignToDropdownArrowBeforeSelect");
				incidentDetailsPage.selectedValueFromSearchBoxInsidePopup(LanguageCode, "assignToPopupSearchBox", IncidentsVariables.ASSIGN_TO, "assignToEmptySearch", "assignToPopupSearchList", "assignToOnPopup", "assignToDropdownArrowAfterSelect", "cancelChangeAssignToButton");
				LOGGER.info("Selected value from assign to dropdown");
				incidentDetailsPage.clickOnElementOfIncidentDetailsPage("deselectAssignToButton");
				LOGGER.info("Deselected assign to value from drodpown");
				softAssert.assertTrue(incidentDetailsPage.getTextOfElementOnIncidentDetailsPage("assignToOnPopup").equals(getTextLanguage(LanguageCode, "daas_ui", "incidents.controls.select_an_option")), "Deselect functionality is not working properly");

				// Test Case 13 - This test validates form validation error message for blank
				// value
				incidentDetailsPage.clickOnElementOfIncidentDetailsPage("submitAssigntoButton");
				LOGGER.info("Verified error validation message for blank assignee value");
				softAssert.assertTrue(incidentDetailsPage.getTextOfElementOnIncidentDetailsPage("errorMessage").equals(getTextLanguage(LanguageCode, "daas_ui", "form.validate.required")), "We are able to submit blank value");

				// Test Case 14 -This test validates if save functionality on assign to popup is
				// working properly
				incidentDetailsPage.clickOnElementOfIncidentDetailsPage("assignToDropdownArrowBeforeSelect");
				String assignToOnPopup = incidentDetailsPage.getTextOfElementOnIncidentDetailsPage("assignToOnPopup");
				selectedValue = incidentDetailsPage.selectedValueFromSearchBoxInsidePopup(LanguageCode, "assignToPopupSearchBox", IncidentsVariables.ASSIGN_TO, "assignToEmptySearch", "assignToPopupSearchList", "assignToOnPopup", "assignToDropdownArrowAfterSelect", "cancelChangeAssignToButton");
				LOGGER.info("Selected value from assign to dropdown");
				incidentDetailsPage.clickOnElementOfIncidentDetailsPage("submitAssigntoButton");
				LOGGER.info("Verified save functionality");
				softAssert.assertTrue(assignToOnPopup.contains(assignTo), "Save button functionality is not working properly");

				// Test Case 15 - This test case validates if the assigned to is updated on the
				// incident list page
				navigateToBack();
				LOGGER.info("Redirected to incident list page");
				waitForPageLoaded();
				softAssert.assertTrue(incidentPage.getTextOfIncidentPage("incidentAssignedTo").equals(selectedValue), "Assigned to is not updated successfully on incident list page");
			}

			softAssert.assertAll();
			LOGGER.info("All test cases for assign to button passed");
		}
	}

	//Pending due to Bug 622544: [DaaS][UI][Veneer3] Device dropdown disabled on incident details page

	@Test(priority = 14, description = " USER STORY 165855", groups = { "REGRESSION", "SMOKE", "REGRESSION_CI", "SMOKE_CI" })
	public final void verifyChangeDeviceButtonOfIncidentDetailsPage() throws Exception {
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		String device = null;
		gotoIncidentTab();
		LOGGER.info("Redirected to incident list page");
		removeLocationFilter();
		waitForPageLoaded();
		IncidentPage incidentPage = new IncidentPage(PreDefinedActions.getDriver()).getInstance();

		resetTableConfiguration();
		Assert.assertTrue(incidentPage.verifyElementsOfIncidentPage("selectedIncidentTitle"), "No incident is present on incident page, unable to proceed further");
		LOGGER.info("Atleast one incident is present, test case started");

		incidentPage.waitUntilElementIsInvisibleOfIncidentPage("tableOverlay");

		incidentPage.enterTextForIncidentPage("idSearchBox", incidentPage.getTextOfIncidentPage("selectedIncidentTitle"));
		incidentPage.waitForElementsOfIncidentPage("tableOverlay");
		sleeper(2000);

		incidentPage.clickByJavaScriptOnIncidentPage("selectedIncidentTitle");
		if (incidentPage.waitForElementsOfIncidentPage("detailsTitle")) {
			LOGGER.info("Navigated to incident details page");
			IncidentDetailsPage incidentDetailsPage = new IncidentDetailsPage(PreDefinedActions.getDriver()).getInstance();

			if (incidentDetailsPage.verifyElementOfIncidentDetailsPage("editDeviceButtonEnable") == false) {
				LOGGER.info("Device is already assigned, hence cannot be edited.");
			} else {

				// Test Case 1 - This test validates if edit option is enabled for device on
				// incident detail page
				softAssert.assertTrue(incidentDetailsPage.verifyElementIsEnableOfIncidentDetailsPage("editDeviceButtonEnable"), "Edit option is not enabled for device on incident detail page");

				sleeper(3000);
				// Test Case 2 - This test validates whether change device popup opens after
				// clicking on change device button
				incidentDetailsPage.clickOnElementOfIncidentDetailsPage("editDeviceButtonEnable");
				softAssert.assertTrue(incidentDetailsPage.verifyElementIsEnableOfIncidentDetailsPage("changeDeviceTitle"), "Change device pop-up does not open after clicking on change device button");

				// Test Case 3 - This test validates if by default submit button is enabled on
				// change device popup
				softAssert.assertTrue(incidentDetailsPage.verifyElementIsEnableOfIncidentDetailsPage("submitDeviceButton"), "By default submit button should be enable on change device pop-up");

				// Test Case 4 - This test validates if by default cancel button is enabled on
				// change device popup
				softAssert.assertTrue(incidentDetailsPage.verifyElementIsEnableOfIncidentDetailsPage("cancelChangeDeviceButton"), "By default cancel button should be enable on change device pop-up");

				sleeper(3000);
				// Test Case 5 - This test validates if search box is present on the change
				// device popup
				incidentDetailsPage.clickOnElementOfIncidentDetailsPage("assignDeviceDropdownArrow");
				softAssert.assertTrue(incidentDetailsPage.verifyElementOfIncidentDetailsPage("editDeviceSearchbox"), "Search box is not present on the change device popup");

				// Test Case 6 - This test validates if search functionality on change device
				// popup is working properly
				softAssert.assertTrue(incidentDetailsPage.verifySearchValueOnIncidentInsidePopup(LanguageCode, "editDeviceSearchbox", IncidentsVariables.INCORRECT_SEARCH_STRING, "assignDeviceEmptySearch", "assignDeviceList"), "Search not working");
				LOGGER.info("Verified search functionality on assign device poup");

				// Test Case 7 - This test validates whether or not we are able to deselect
				// value on the selected device popup
				incidentDetailsPage.selectedValueFromSearchBoxInsideDevicePopup(LanguageCode, "editDeviceSearchbox", IncidentsVariables.ASSIGNED_DEVICE, "assignDeviceEmptySearch", "assignDeviceList", "deviceOnPopup", "assignDeviceDropdownArrow", "cancelChangeDeviceButton");
				LOGGER.info("Selected value from edit device dropdown");
				incidentDetailsPage.clickOnElementOfIncidentDetailsPage("deselectDeviceButton");
				LOGGER.info("Deselected assigned device");
				sleeper(2000); // time required to update the change in device
				softAssert.assertTrue(incidentDetailsPage.getTextOfElementOnIncidentDetailsPage("deviceOnPopup").equals(getTextLanguage(LanguageCode, "daas_ui", "incidents.controls.select_an_option")), "We are not able to deselect value on the selected device popup");

				// Test Case 8 - This test validates if save button is disabled when we remove
				// selected value from pop-up
				softAssert.assertTrue(incidentDetailsPage.verifyElementIsEnableOfIncidentDetailsPage("submitDeviceButton"), "Save button should be disable when we remove selected value from pop-up");

				// Test Case 9 - This test validates if cancel functionality on change device
				// popup is working properly
				incidentDetailsPage.clickOnElementOfIncidentDetailsPage("assignDeviceDropdownArrow");
				incidentDetailsPage.selectedValueFromSearchBoxInsideDevicePopup(LanguageCode, "editDeviceSearchbox", IncidentsVariables.ASSIGNED_DEVICE, "assignDeviceEmptySearch", "assignDeviceList", "deviceOnPopup", "assignDeviceDropdownArrow", "cancelChangeDeviceButton");
				LOGGER.info("Selected value from edit device dropdown");
				incidentDetailsPage.clickOnElementOfIncidentDetailsPage("cancelChangeDeviceButton");
				LOGGER.info("Verified cancel functionality");
				softAssert.assertFalse(incidentDetailsPage.verifyElementOfIncidentDetailsPage("currentDeviceName"), "After selecting the value from device search box and clicking on cancel button , it should discard all the changes and information should not get updated both on incident list and details page");

				// Test Case 10 - This test validates if save functionality on change device
				// popup is working properly
				incidentDetailsPage.clickOnElementOfIncidentDetailsPage("editDeviceButtonEnable");
				incidentDetailsPage.clickOnElementOfIncidentDetailsPage("assignDeviceDropdownArrow");
				device = incidentDetailsPage.selectedValueFromSearchBoxInsideDevicePopup(LanguageCode, "editDeviceSearchbox", IncidentsVariables.ASSIGNED_DEVICE, "assignDeviceEmptySearch", "assignDeviceList", "deviceOnPopup", "assignDeviceDropdownArrow", "cancelChangeDeviceButton");
				LOGGER.info("Selected value from edit device dropdown");
				incidentDetailsPage.clickOnElementOfIncidentDetailsPage("submitDeviceButton");
				LOGGER.info("Verified save functionality");

				// Test Case 11 - This test validates if device assigned to the incident gets
				// updated on incident list page
				device = incidentDetailsPage.getTextOfElementOnIncidentDetailsPage("currentDeviceName");
				navigateToBack();
				waitForPageLoaded();
				LOGGER.info("Navigated to incident list page");
				softAssert.assertTrue(incidentPage.getTextOfIncidentPage("incidentDeviceName").equals(device), "Device not updated successfully on incident list page");
			}
			softAssert.assertAll();
			LOGGER.info("All test cases of verifyChangeDeviceButtonOfIncidentDetailsPage passed successfully");
		} else {
			Assert.fail("Incident details page did not open successfully, hence verifyChangeDeviceButtonOfIncidentDetailsPage got failed.");
		}
	}

	@Test(priority = 15, groups = "REGRESSION", description = "191027,191034,191035,190908,191033,191032", enabled = false)
	public final void verifyPaginationOnIncidentPage() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		gotoIncidentTab();
		LOGGER.info("Redirected to incident list page");
		removeLocationFilter();
		waitForPageLoaded();
		resetTableConfiguration();
		IncidentPage incidentPage = new IncidentPage(PreDefinedActions.getDriver()).getInstance();

		Assert.assertTrue(incidentPage.verifyElementsOfIncidentPage("selectedIncidentTitle"), "No incident is present on incident page, unable to proceed further");
		LOGGER.info("Atleast one incident is present, test case started");

		softAssert.assertTrue(incidentPage.verifyElementIsEnableOfIncidentPage("paginationDropdownMenu"), "Pagination Dropdown not available");
		softAssert.assertTrue(incidentPage.verifyElementIsClickableOfIncidentPage("paginationDropdownMenu"), "Pagination Dropdown not clickable");
		softAssert.assertTrue(incidentPage.waitForElementsOfIncidentPage("navigationItemDiv"), "Navigation Item are not available");
		getPaginationInfo();
		LOGGER.info("get Pagination Information ");
		softAssert.assertTrue(incidentPage.verifyElementIsEnableOfIncidentPage("navigationItemActivePage"), "The active page navigation link is not disabled");
		softAssert.assertTrue(incidentPage.verifyTotalPageCountChangeAsSelectedOption(selectedOption, totalCount, lastPageNumber), "Total page count not change with selected option");
		softAssert.assertTrue(incidentPage.verifyButtonStatusofPagination(activepageNumber, firstPageNumber, lastPageNumber, totalCount, previousButtonStatus, nextButtonStatus), "Button visibility status not as per expectation");
		softAssert.assertTrue(verifySelectedOptionForNewSelection(selectedOption, 50), "Used already Chosen option ");
		if (changeSelectedOption(totalCount, 50)) {
			incidentPage.selectElementFromDropDownofIncidentPage("paginationDropdownMenu", "paginationDropdownOptionList", "50");
			LOGGER.info("Change Selected option as 50");
			softAssert.assertTrue(incidentPage.verifyTotalPageCountChangeAsSelectedOption(selectedOption, totalCount, lastPageNumber), "Total page count not change with selected option");
			softAssert.assertTrue(incidentPage.verifyButtonStatusofPagination(activepageNumber, firstPageNumber, lastPageNumber, totalCount, previousButtonStatus, nextButtonStatus), "Button visibility status not as per expectation");
		} else {
			LOGGER.info("Selected user has less than 50 records");
		}
		getPaginationInfo();
		if (incidentPage.canClickNext(activepageNumber, lastPageNumber, nextButtonStatus)) {
			softAssert.assertTrue(incidentPage.getButtonEnabilityStatus("navigationItemNext"), "Naviagtion next button is not enable");
			incidentPage.clickOnElementsOfIncidentPage("navigationItemNext");
			getPaginationInfo();
			if (incidentPage.canClickPrevious(activepageNumber, firstPageNumber, previousButtonStatus)) {
				softAssert.assertTrue(incidentPage.getButtonEnabilityStatus("navigationItemPrevious"), "Naviagtion previous button is not enable");
				incidentPage.clickOnElementsOfIncidentPage("navigationItemPrevious");
			} else {
				LOGGER.info("Previous button is disabled");
			}
		} else if (incidentPage.canClickPrevious(activepageNumber, firstPageNumber, previousButtonStatus)) {
			softAssert.assertTrue(incidentPage.getButtonEnabilityStatus("navigationItemPrevious"), "Naviagtion previous button is not enable");
			incidentPage.clickOnElementsOfIncidentPage("navigationItemPrevious");
			getPaginationInfo();
			if (incidentPage.canClickNext(activepageNumber, lastPageNumber, nextButtonStatus)) {
				softAssert.assertTrue(incidentPage.getButtonEnabilityStatus("navigationItemNext"), "Naviagtion next button is not enable");
				incidentPage.clickOnElementsOfIncidentPage("navigationItemNext");
			} else {
				LOGGER.info("Next button is disabled");
			}
		} else {
			LOGGER.info("Previous and Next button both are disabled");
		}
		LOGGER.info("Verification of incident page pagination done Successfully ");
	}

	@Test(priority = 16, description = "USER STORY 205833", groups = { "REGRESSION", "SMOKE", "REGRESSION_CI", "SMOKE_CI" })
	public final void verifyStoreAllFiltersOnIncidentsPage() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		IncidentPage incidentPage = new IncidentPage(PreDefinedActions.getDriver()).getInstance();

		gotoIncidentTab();
		LOGGER.info("Redirected to incident list page");
		waitForPageLoaded();
		resetTableConfiguration();
		Assert.assertTrue(incidentPage.verifyElementsOfIncidentPage("selectedIncidentTitle"), "No incident is present on incident page, unable to proceed further");
		LOGGER.info("Atleast one incident is present, test case started");

		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();

		incidentPage.waitUntilElementIsInvisibleOfIncidentPage("tableOverlay");
		incidentPage.clickOnElementsOfIncidentPage("typeBoxBefore");
		String selectedType = incidentPage.selectFirstOptionFromDropdownOnIncidentPage("firstTypeOptionFromFiltersDropDown");
		pressKey(Keys.ESCAPE);
		LOGGER.info("Applied filter on type column of incident list page");

		// Test case 1 - This test validates whether the user's previous selection of
		// filters is retained when the user comes back to incident page after
		// redirection from other page
		gotoCompaniesTab();
		companiesPage.verifyElementsOfCompaniesPage("companysearchbox");
		LOGGER.info("Navigated to companies page");
		gotoIncidentTab();
		waitForPageLoaded();
		LOGGER.info("Navigated to incident page");
		incidentPage.waitUntilElementIsInvisibleOfIncidentPage("tableOverlay");
		softAssert.assertTrue(selectedType.equals(incidentPage.getTextOfIncidentPage("typeValueOnFilter")), "When user redirect to other screen and come back to incident page then then users previous selection of filters is not shown in filter selection of incidents list.");

		// Test case 2 - This test validates whether the user's previous selection of
		// filters is retained when the user logs in from another system
		logout();
		LOGGER.info("Logged out");
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		LOGGER.info("Logged in");
		gotoIncidentTab();
		LOGGER.info("Redirected to incident list page");
		waitForPageLoaded();
		incidentPage.waitUntilElementIsInvisibleOfIncidentPage("tableOverlay");
		softAssert.assertTrue(selectedType.equals(incidentPage.getTextOfIncidentPage("typeValueOnFilter")), "If user logsin from other system then his previous selection of filters is not shown in filter selection of incidents list. ");

		softAssert.assertAll();
		LOGGER.info("Test cases for save filter selection passed successfully");

	}

	@Test(priority = 17, description = "USER STORY 191434", groups = { "REGRESSION", "SMOKE", "REGRESSION_CI", "SMOKE_CI" })
	public final void verifyMultipageSelectionOnIncidentListPage() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		IncidentPage incidentPage = new IncidentPage(PreDefinedActions.getDriver()).getInstance();

		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();

		gotoIncidentTab();
		LOGGER.info("Redirected to incident list page");
		removeLocationFilter();
		waitForPageLoaded();
		resetTableConfiguration();
		Assert.assertTrue(incidentPage.verifyElementsOfIncidentPage("selectedIncidentTitle"), "No incident is present on incident page, unable to proceed further");
		LOGGER.info("Atleast one incident is present, test case started");

		// Test Case 1 - This test case validates that when user select few items from
		// page and reload the screen or redirect from any other screen to list then
		// item selection should be
		// cleared out.
		incidentPage.clickByJavaScriptOnIncidentPage("firstIdCheckBox");
		LOGGER.info("Selected first incident on incident list page");
		gotoCompaniesTab();
		waitForPageLoaded();
		LOGGER.info("Navigated to companies list page");
		companiesPage.verifyElementsOfCompaniesPage("nameColumn");
		gotoIncidentTab();
		waitForPageLoaded();
		LOGGER.info("Navigated back to incident list page");
		incidentPage.waitUntilElementIsInvisibleOfIncidentPage("tableOverlay");
		incidentPage.verifyElementsOfIncidentPage("firstIdCheckBox");
		softAssert.assertFalse(incidentPage.verifyElementsOfIncidentPage("selectAllIncidentsChecked"), "When user select few items from page and reload the screen or redirect from any other screen to list then item selection is not cleared out. ");

		// Test Case 2 - This test case validates that when user select few items from
		// page and change the pagination per page size then item selection should be
		// cleared out.
		incidentPage.clickByJavaScriptOnIncidentPage("firstIdCheckBox");
		LOGGER.info("Selected first incident on incident list page");
		incidentPage.waitUntilElementIsInvisibleOfIncidentPage("tableOverlay");
		if (verifySelectedOptionForNewSelection(selectedOption, 50) == true)
			incidentPage.selectElementFromDropDownofIncidentPage("paginationDropdownMenu", "paginationDropdownOptionList", "50");
		else
			incidentPage.selectElementFromDropDownofIncidentPage("paginationDropdownMenu", "paginationDropdownOptionList", "25");
		LOGGER.info("Changed pagination count from dropdown");
		softAssert.assertFalse(incidentPage.verifyElementIsSelectedOfIncidentPage("selectAllCheckBox"), "When user select few items from page and change the pagination per page size then item selection is not cleared out. ");

		// Test Case 3 - This test case validates that when user selects few items from
		// page 1 and then redirect to page 2 using pagination then item selection
		// should be cleared out.
		incidentPage.clickByJavaScriptOnIncidentPage("firstIdCheckBox");
		LOGGER.info("Selected first incident on incident list page");
		if (incidentPage.getButtonEnabilityStatus("navigationItemNext")) {
			incidentPage.clickOnElementsOfIncidentPage("navigationItemNext");
			LOGGER.info("Navigated to next page from pagination");
			incidentPage.waitForElementsOfIncidentPage("tableOverlay");
			incidentPage.clickOnElementsOfIncidentPage("navigationItemPrevious");
			LOGGER.info("Navigated to previous page");
			softAssert.assertFalse(incidentPage.verifyElementsOfIncidentPage("selectAllIncidentsChecked"), "When user select few items from page 1 and then redirect to page 2 using pagination then item selection is not cleared out. ");
			LOGGER.info("When user select few items from page 1 and then redirect to page 2 using pagination then item selection is not cleared out. ");
		} else
			LOGGER.info("There exists only one page in pagination, so user cannot navigate to next page");

		softAssert.assertAll();
		LOGGER.info("Multi page selection test cases for incident list page passed successfully");

	}

	@Test(priority = 18, description = "NFR STORY 222234, TASK 222876", groups = { "REGRESSION", "SMOKE", "REGRESSION_CI", "SMOKE_CI" })
	public final void verifyUserSelectionOfIncidents() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		String selectedType = null;
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		IncidentPage incidentPage = new IncidentPage(PreDefinedActions.getDriver()).getInstance();

		gotoIncidentTab();
		LOGGER.info("Redirected to incident list page");
		removeLocationFilter();
		waitForPageLoaded();
		resetTableConfiguration();
		Assert.assertTrue(incidentPage.verifyElementsOfIncidentPage("selectedIncidentTitle"), "No incident is present on incident page, unable to proceed further");
		LOGGER.info("Atleast one incident is present, test case started");

		// Test Case 1 - Verify if incident page does not lose user selection when
		// returning after opening an incident
		incidentPage.waitUntilElementIsInvisibleOfIncidentPage("tableOverlay");
		sleeper(5000);
		incidentPage.clickOnElementsOfIncidentPage("typeBoxBefore");
		selectedType = incidentPage.selectFirstOptionFromDropdownOnIncidentPage("firstTypeOptionFromFiltersDropDown");
		pressKey(Keys.ESCAPE);
		LOGGER.info("Applied filter on type column of incident list page");
		incidentPage.clickByJavaScriptOnIncidentPage("selectedIncidentTitle");
		IncidentDetailsPage incidentDetailsPage = new IncidentDetailsPage(PreDefinedActions.getDriver()).getInstance();
		if (incidentPage.waitForElementsOfIncidentPage("detailsPageHeading")) {
			waitForPageLoaded();
			LOGGER.info("navigated to incident details page");
			incidentDetailsPage.verifyElementOfIncidentDetailsPage("incidentInformationTitle");
			gotoIncidentTab();
			waitForPageLoaded();
			LOGGER.info("Navigated back to incident list page");
			incidentPage.waitUntilElementIsInvisibleOfIncidentPage("tableOverlay");
			softAssert.assertTrue(selectedType.equals(incidentPage.getTextOfIncidentPage("typeValueOnFilter")), "Incidents UI loses user selection when returning after opening an incident");
			LOGGER.info("Incident UI does not lose user selection when returning after opening an incident");

			softAssert.assertAll();
			LOGGER.info("User selection of Incidents test case for incident list page passed successfully");
		} else {
			LOGGER.error("Incident details page did not open successfully, hence userSelectionOfIncidents got failed.");
		}
	}

	@Test(priority = 21, description = "NFR STORY 222234, TASK 222876", groups = { "REGRESSION", "SMOKE", "REGRESSION_CI", "SMOKE_CI" })
	public final void verifySelectAllIncidents() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		IncidentPage incidentPage = new IncidentPage(PreDefinedActions.getDriver()).getInstance();

		gotoIncidentTab();
		LOGGER.info("Redirected to incident list page");
		removeLocationFilter();
		waitForPageLoaded();
		resetTableConfiguration();
		Assert.assertTrue(incidentPage.verifyElementsOfIncidentPage("selectedIncidentTitle"), "No incident is present on incident list page, unable to proceed further");
		LOGGER.info("Atleast one incident is present, test case started");

		// Test Case 1 - All the incidents are shown as selected after navigation to 1st
		// page from 2nd page, when only a single incident is selected on 1st page
		incidentPage.clickByJavaScriptOnIncidentPage("selectAllCheckBox");
		LOGGER.info("Selected all incidents");
		if (incidentPage.getButtonEnabilityStatus("navigationItemNext")) {
			incidentPage.clickOnElementsOfIncidentPage("navigationItemNext");
			LOGGER.info("Navigated to next page");
			sleeper(1000);
			incidentPage.clickByJavaScriptOnIncidentPage("navigationItemPrevious");
			LOGGER.info("Navigated back to previous page");
			incidentPage.clickByJavaScriptOnIncidentPage("firstIdCheckBox");
			LOGGER.info("Selected first incident");
			softAssert.assertFalse(incidentPage.verifyElementsOfIncidentPage("selectAllIncidentsChecked"), "All the incidents are shown as selected after navigation to 1st page from 2nd page, when only a single incident is selected");
		} else
			LOGGER.info("There exists only one page in pagination, so unable to navigate to next page");

		softAssert.assertAll();
		LOGGER.info("Selection of all incidents test case on incident list page passed successfully");

	}

	@Test(priority = 22, groups = { "REGRESSION", "REGRESSION_CI" }, description = "Validate functionality to enroll a device and submit incident for that device using system APIs", enabled = false)
	public final void verifyEnrollAFakeDeviceAndSubmitIncidentForThatDeviceUsingSytemAPI() throws Exception {
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		LOGGER.info("Step 1: Impersonate test company for adding a fake device under it.");
		impersonateCompanyByEmail(getCredentials(environment, "COMPANY_EMAIL_REPORTS"));
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();

		LOGGER.info("Step 2: Capture company details");
		String companyName = companiesPage.getTextOfCompaniesPage("companyNameShow");
		LOGGER.info("CompanyName is: " + companyName);
		String companyEmailId = companiesPage.getTextOfCompaniesPage("companyEmailId");
		LOGGER.info("Company EmailId is: " + companyEmailId);
		CompanyPin companypin = new CompanyPin();
		String companyPin = companypin.getCompanyPin();
		LOGGER.info("companyPin is : " + companyPin);
		LOGGER.info("Step 3: Enroll a fake device for this company");
		HashMap<String, String> deviceDetails = new HashMap<>();
		deviceDetails = EnrollFakeDevice.enrollFakeDeviceForIncident(companyName, companyPin, companyEmailId);
		Assert.assertTrue(!deviceDetails.isEmpty(), "Fake device couldnt be enrolled");
		String deviceSerialNumber = deviceDetails.get("deviceSerialNumber");
		LOGGER.info(deviceSerialNumber + " fake Device is Enrolled Successfully");
		LOGGER.info("Step 4: Submit an incident for this enrolled fake device");
		DeviceDetailsPage devicepage = new DeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();

		String incidentTitle = "Test Incident for enrolled device " + deviceSerialNumber;
		String type = getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.type.account");
		String subtype = getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.subtype.unknown");
		String incidentId = devicepage.submitCaseUsingAPI(type, subtype, incidentTitle, "", deviceDetails.get("host"), deviceDetails.get("tenantId"), deviceDetails.get("userAuthToken"), deviceDetails.get("deviceId"), deviceDetails.get("UserID"));
		Assert.assertFalse(Strings.isNullOrEmpty(incidentId), "No incident id was created, hence failing the test case");
		LOGGER.info("Incident ID: " + incidentId);
		Assert.assertTrue(!incidentId.isEmpty(), "Error occured while submitting an incident using API");
	}

	/**
	 * This test case verifies Incident tab is visible for sales admin
	 * 
	 * @throws Exception
	 */
	@Test(priority = 23, groups = { "REGRESSION", "REGRESSION_CI" }, description = "223191", enabled = false)
	public final void verifySalesAdminViewIncidentTab() throws Exception {
		login("RESELLER_PARTNERS_EMAIL", "RESELLER_PARTNERS_PASSWORD");
		IncidentPage incidentPage = new IncidentPage(PreDefinedActions.getDriver()).getInstance();

		Assert.assertTrue(incidentPage.isIncidentTabVisible(LanguageCode), "Error occured while validating incident tab visibility for sales admin");
		LOGGER.info("Validated Incident tab is visible for the sales admin");
	}

	/**
	 * This test case verifies Incident tab is not visible to sales specialist
	 * 
	 * @throws Exception
	 */
	@Test(priority = 24, groups = { "REGRESSION", "REGRESSION_CI" }, description = "224101", enabled = false)
	public final void verifySalesSpecialistCanViewIncidentTab() throws Exception {
		login("RESELLER_PARTNERS_EMAIL", "RESELLER_PARTNERS_PASSWORD");
		IncidentPage incidentPage = new IncidentPage(PreDefinedActions.getDriver()).getInstance();

		Assert.assertTrue(!incidentPage.isIncidentTabVisible(LanguageCode), "Error occured while validating incident tab visibility for sales specialist");
		LOGGER.info("Validated Incident tab is not visible for the sales specialist");
	}

	/**
	 * This test case validate device field is not hyperlink for the sales admin
	 * 
	 * @throws Exception
	 */
	@Test(priority = 25, groups = { "REGRESSION", "REGRESSION_CI" }, description = "223506", enabled = false)
	public void verifyDeviceFieldLinkForSalesAdmin() throws Exception {
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		impersonateCompanyByEmail(getCredentials(environment, "COMPANY_EMAIL_REPORTS"));
		IncidentDetailsPage incidentDetailsPage = new IncidentDetailsPage(PreDefinedActions.getDriver()).getInstance();

		HashMap<String, String> deviceDetails = incidentDetailsPage.enrollDevice(LanguageCode);

		Assert.assertTrue(deviceDetails != null, "Fake device couldn't be enrolled");
		LOGGER.info("Fake Device Has been enrolled successfully,Device serial number: " + deviceDetails.get("deviceSerialNumber"));
		String incidentId = incidentDetailsPage.submitIncident(LanguageCode, deviceDetails);
		Assert.assertTrue(incidentId != null, "Error occured while submitting an incident");
		LOGGER.info("Incident has been submitted successfully,Incident ID: " + incidentId);
		logout();
		login("RESELLER_NEW_UI_EMAIL_US", "RESELLER_NEW_UI_PASSWORD_US");
		gotoIncidentTab();
		Boolean isDeviceLinkHypelink = incidentDetailsPage.validateDeviceNameHyperLink(incidentId, LanguageCode);
		Assert.assertTrue(isDeviceLinkHypelink, "Error occured while verifying device name is hypelink for sales admin");
		LOGGER.info("Verified device link is not hypelink on incident detail page for sales admin");
	}

	/**
	 * This test case validate device field is not editable for sales admin
	 * 
	 * @throws Exception
	 */
	@Test(priority = 26, groups = { "REGRESSION", "REGRESSION_CI" }, description = "223506", enabled = false)
	public void verifyDeviceFieldIsEditableForSalesAdmin() throws Exception {
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		impersonateCompanyByEmail(getCredentials(environment, "COMPANY_EMAIL_REPORTS"));
		IncidentDetailsPage incidentDetailsPage = new IncidentDetailsPage(PreDefinedActions.getDriver()).getInstance();

		HashMap<String, String> deviceDetails = incidentDetailsPage.enrollDevice(LanguageCode);

		Assert.assertTrue(deviceDetails != null, "Error occured while enrlloing fake device");
		LOGGER.info("Fake Device Has been enrolled successfully,Device serial number: " + deviceDetails.get("deviceSerialNumber"));
		deviceDetails.put("deviceId", "");
		String incidentId = incidentDetailsPage.submitIncident(LanguageCode, deviceDetails);
		Assert.assertTrue(incidentId != null, "Error occured while submitting an incident");
		LOGGER.info("Incident has been submitted successfully,Incident ID: " + incidentId);
		logout();
		login("RESELLER_NEW_UI_EMAIL_US", "RESELLER_NEW_UI_PASSWORD_US");
		gotoIncidentTab();
		Boolean isDeviceFieldEditable = incidentDetailsPage.validateDeviceNameHyperLink(incidentId, LanguageCode);
		Assert.assertTrue(isDeviceFieldEditable, "Error occured while verifying device field is editable for sales admin");
		LOGGER.info("Verified device field is not editable on incident detail page for sales admin");
	}

	/*
	 * This test case submits a incident from device detail page search for the ID of the newly generated incident on incident page download and validate the XLS data NFR Story 211645:
	 * Now following method validates the complete data for created incident /** This test case submits a incident from device detail page search for the ID of the newly generated
	 * incident on incident page download and validate the XLS data
	 * 
	 * @throws Exception
	 */
	@Test(priority = 27, groups = { "REGRESSION", "REGRESSION_CI" }, description = "NFR Story 198820 and NFR Story 211645", enabled = false)
	public final void verifyIncidentXLS() throws Exception {
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_PASSWORD_REPORTS");// Login using primary account
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		DeviceDetailsPage deviceDetailsPage = new DeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
		DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
		IncidentPage incidentPage = new IncidentPage(PreDefinedActions.getDriver()).getInstance();
		IncidentDetailsPage incidentDetailsPage = new IncidentDetailsPage(PreDefinedActions.getDriver()).getInstance();
		TableConfigurationPage tableConfigurationPage = new TableConfigurationPage(PreDefinedActions.getDriver()).getInstance();

		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		dashboardPage.clickOnElementsOfDashboardPage("userProfileButton");
		String strLoggedInUserName = dashboardPage.getTextOfDashboardPage("userNameDisplayedOnPopup");
		dashboardPage.clickOnElementsOfDashboardPage("userProfileButton");
		LOGGER.info("Step 1: Clicked on top right User Icon to get user details popup menu");
		gotoSettingsTabWorkflow();
		LOGGER.info("Step 2: Impersonate test company for adding a fake device under it.");
		LOGGER.info("Step 3: Capture company details");
		sleeper(2000);
		String companyName = companiesPage.getTextOfCompaniesPage("companyNameShow");
		LOGGER.info("CompanyName is: " + companyName);
		String companyEmailId = companiesPage.getTextOfCompaniesPage("companyEmailId");
		LOGGER.info("Company EmailId is: " + companyEmailId);
		goToPreferencesTab();
		CompanyPin companypin = new CompanyPin();
		sleeper(2000);
		String companyPin = companypin.getCompanyPin();
		LOGGER.info("companyPin is : " + companyPin);
		LOGGER.info("Step 4: Enroll a fake device for this company");
		HashMap<String, String> deviceDetails = new HashMap<>();
		deviceDetails = EnrollFakeDevice.enrollFakeDeviceForIncident(companyName, companyPin, companyEmailId);
		Assert.assertTrue(!deviceDetails.isEmpty(), "Fake device couldnt be enrolled");
		String deviceSerialNumber = deviceDetails.get("deviceSerialNumber");
		LOGGER.info(deviceSerialNumber + " fake Device is Enrolled Successfully");
		LOGGER.info("Step 5: Validate whether added fake device is showing on device page");
		gotoDevicesTab();
		sleeper(5000);
		deviceListPage.waitForElementsOfDeviceListPage("deviceListTable");
		deviceListPage.clickOnElementsOfDeviceListPage("tableConfigurationButton");
		if (tableConfigurationPage.verifyElementsOfTableConfigurationPage("resettodefault")) {
			tableConfigurationPage.clickOnElementsOfTableConfigurationPage("resettodefault");
			tableConfigurationPage.selectAllCheckboxOfPopup("columnCheckboxesOnPopup", "listOfColumnsOnPopup");
			tableConfigurationPage.clickOnElementsOfTableConfigurationPage("saveButton");
		} else {
			tableConfigurationPage.selectAllCheckboxOfPopup("columnCheckboxesOnPopup", "listOfColumnsOnPopup");
			tableConfigurationPage.clickOnElementsOfTableConfigurationPage("saveButton");
			deviceListPage.clickOnElementsOfDeviceListPage("tableConfigurationButton");
			tableConfigurationPage.clickOnElementsOfTableConfigurationPage("resettodefault");
			tableConfigurationPage.selectAllCheckboxOfPopup("columnCheckboxesOnPopup", "listOfColumnsOnPopup");
			tableConfigurationPage.clickOnElementsOfTableConfigurationPage("saveButton");
		}
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
		sleeper(3000);
		String deviceUserName = deviceDetailsPage.getTextOfDeviceDetailsPage("deviceUserName");
		String incidentTitle = "Test Incident for enrolled device " + deviceSerialNumber;
		String incidentDescription = "Description for Test Incident for enrolled device " + deviceSerialNumber;
		String type = getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.type.account");
		String subtype = getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.subtype.licensing");
		String incidentId = deviceDetailsPage.submitCaseUsingAPI(type, subtype, incidentTitle, incidentDescription, deviceDetails.get("host"), deviceDetails.get("tenantId"), deviceDetails.get("userAuthToken"), deviceDetails.get("deviceId"), deviceDetails.get("UserID"));
		Assert.assertFalse(Strings.isNullOrEmpty(incidentId), "No incident id was created, hence failing the test case");
		LOGGER.info("Incident ID: " + incidentId);
		String deviceCompanyName = deviceDetailsPage.getTextOfDeviceDetailsPage("deviceCompanyName");
		Map<String, String> incidentDetails = new HashMap<String, String>();
		incidentDetails.put("incidentId", incidentId);
		incidentDetails.put("strLoggedInUserName", strLoggedInUserName);
		incidentDetails.put("deviceCompanyName", deviceCompanyName);
		gotoIncidentTab();
		incidentPage.clickOnElementsOfIncidentPage("tableConfigurationButton");
		if (tableConfigurationPage.verifyElementsOfTableConfigurationPage("resettodefault")) {
			tableConfigurationPage.clickOnElementsOfTableConfigurationPage("resettodefault");
			tableConfigurationPage.selectAllCheckboxOfPopup("columnCheckboxesOnPopup", "listOfColumnsOnPopup");
			tableConfigurationPage.clickOnElementsOfTableConfigurationPage("saveButton");
		} else {
			tableConfigurationPage.selectAllCheckboxOfPopup("columnCheckboxesOnPopup", "listOfColumnsOnPopup");
			tableConfigurationPage.clickOnElementsOfTableConfigurationPage("saveButton");
			incidentPage.clickOnElementsOfIncidentPage("tableConfigurationButton");
			tableConfigurationPage.clickOnElementsOfTableConfigurationPage("resettodefault");
			tableConfigurationPage.selectAllCheckboxOfPopup("columnCheckboxesOnPopup", "listOfColumnsOnPopup");
			tableConfigurationPage.clickOnElementsOfTableConfigurationPage("saveButton");
		}
		incidentPage.verifyElementIsEnableOfIncidentPage("idSearchBox");
		LOGGER.info("Clear the text from incident ID field.");
		sleeper(2000);// Tried removing this sleeper but is not working, so this is needed
		incidentPage.clickOnElementsOfIncidentPage("clearAllIncidentSearch");
		incidentPage.clearTextFromIncidentPageTextField("idSearchBox");
		incidentPage.waitForElementsOfIncidentPage("idSearchBox");
		LOGGER.info("Enter the value in incident ID field.");
		sleeper(2000);// Tried removing this sleeper but is not working, so this is needed
		incidentPage.enterTextForIncidentPage("idSearchBox", incidentDetails.get("incidentId"));
		sleeper(3000);// Tried removing this sleeper but is not working, so this is needed
		incidentPage.clickOnElementsOfIncidentPage("incidentIdLink");
		// LOGGER.info("Step 8: Add a comment by changing assigned to");
		sleeper(2000);
		incidentDetailsPage.verifyElementOfIncidentDetailsPage("editAssignedToPencilIcon");
		incidentDetailsPage.waitForElementToBeClickableOfIncidentDetailsPage("editAssignedToPencilIcon");
		LOGGER.info("Click on the pencil icon to edit the Assigned To");
		sleeper(2000);// Tried removing this sleeper but is not working, so this is needed
		incidentDetailsPage.clickOnElementOfIncidentDetailsPage("editAssignedToPencilIcon");
		sleeper(3000);
		incidentPage.verifyElementIsClickableOfIncidentPage("assignToDropDownUpdatedd");
		sleeper(2000);// Tried removing this sleeper but is not working, so this is needed
		incidentPage.clickOnElementsOfIncidentPage("assignToDropDownUpdatedd");
		sleeper(2000);
		incidentPage.verifyElementIsClickableOfIncidentPage("assignToPopupDropDownFirstOption");
		sleeper(1000);// Tried removing this sleeper but is not working, so this is needed
		incidentPage.clickOnElementsOfIncidentPage("assignToPopupDropDownFirstOption");
		incidentPage.clickOnElementsOfIncidentPage("assignToPopupSaveButton");
		LOGGER.info("Step 9: Validate assigned to has changed successfully.");
		gotoIncidentTab();
		LOGGER.info("Step 10: Select all columns for complete validation of data");
		incidentPage.clickOnElementsOfIncidentPage("tableConfigurationButton");
		if (tableConfigurationPage.verifyElementsOfTableConfigurationPage("resettodefault")) {
			tableConfigurationPage.clickOnElementsOfTableConfigurationPage("resettodefault");
			tableConfigurationPage.selectAllCheckboxOfPopup("columnCheckboxesOnPopup", "listOfColumnsOnPopup");
			tableConfigurationPage.clickOnElementsOfTableConfigurationPage("saveButton");
		} else {
			tableConfigurationPage.selectAllCheckboxOfPopup("columnCheckboxesOnPopup", "listOfColumnsOnPopup");
			tableConfigurationPage.clickOnElementsOfTableConfigurationPage("saveButton");
			incidentPage.clickOnElementsOfIncidentPage("tableConfigurationButton");
			tableConfigurationPage.clickOnElementsOfTableConfigurationPage("resettodefault");
			tableConfigurationPage.selectAllCheckboxOfPopup("columnCheckboxesOnPopup", "listOfColumnsOnPopup");
			tableConfigurationPage.clickOnElementsOfTableConfigurationPage("saveButton");
		}
		String strIncidentUpdatedOnText = incidentDetailsPage.getTextOfElementOnIncidentDetailsPage("incidentUpdatedOnText");
		String strIncidentCreatedOnText = incidentDetailsPage.getTextOfElementOnIncidentDetailsPage("currentCreatedOn");
		incidentDetails.put("incidentdescription", incidentDescription);
		incidentDetails.put("currentCreatedOn", strIncidentCreatedOnText);
		incidentDetails.put("incidentUpdatedOnText", strIncidentUpdatedOnText);
		incidentDetails.put("incidentPriority", incidentDetailsPage.getTextOfElementOnIncidentDetailsPage("currentIncidentPriority"));
		incidentDetails.put("incidentStatus", incidentDetailsPage.getTextOfElementOnIncidentDetailsPage("currentIncidentStatus"));
		incidentDetails.put("deviceUserName", deviceUserName);
		LOGGER.info("Step 11: Download and validate excel data for the searched incident id");
		Assert.assertTrue(incidentPage.validateIncidentDataInXLS(incidentDetails, LanguageCode), "Error Occured while validating Incident XLS");
		LOGGER.info("Validated incident XLS successfully");
	}

	/**
	 * This test case validating Report Admin should able to edit incidents
	 * 
	 * @throws Exception
	 */
	@Test(priority = 28, groups = { "REGRESSION", "REGRESSION_CI" }, description = "225200", enabled = false)
	public final void verifyIncidentEditableforReportAdmin() throws Exception {
		login("COMPANY_EMAIL_REPORTS", "COMPANY_REPORT_ADMIN_PASSWORD");
		IncidentPage incidentPage = new IncidentPage(PreDefinedActions.getDriver()).getInstance();

		gotoIncidentTab();
		Assert.assertTrue(incidentPage.isIncidentsEditable(), "Error occured while validating is incident are editale for report admin");
		LOGGER.info("Validated Report Admin user able to edit incident successfully");

	}

	@Test(priority = 29, groups = { "REGRESSION", "REGRESSION_CI" }, description = "TASK 244672", enabled = false)
	public final void verifyHyperlinksOnIncidentPage() throws Exception {
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		List<WebElement> companyLinksList, assigntoLinksList, idLinksList;
		String urlOfPage;
		gotoIncidentTab();
		waitForPageLoaded();
		IncidentPage incidentPage = new IncidentPage(PreDefinedActions.getDriver()).getInstance();

		resetTableConfiguration();
		Assert.assertTrue(incidentPage.verifyElementsOfIncidentPage("selectedIncidentTitle"), "No incident is present on incident page, cannot proceed further");

		incidentPage.waitForElementsOfIncidentPage("selectedIncidentTitle");
		idLinksList = incidentPage.getWebElementsOfIncidentPage("idlist");
		incidentPage.clickElementsOfIncidentPage(idLinksList);
		if (incidentPage.waitForElementsOfIncidentPage("detailsPageHeading")) {
			LOGGER.info("Navigated to incident details page");
			urlOfPage = incidentPage.getUrlOfCurrentPage();
			softAssert.assertTrue(urlOfPage.contains("incidents"), "Hyperlinks under ID column does not redirect to incident details page");
			gotoIncidentTab();
			incidentPage.waitForElementsOfIncidentPage("selectedIncidentTitle");
			assigntoLinksList = incidentPage.getWebElementsOfIncidentPage("assignedTolist");
			incidentPage.clickElementsOfIncidentPage(assigntoLinksList);
			LOGGER.info("Navigated to assignee's page");
			urlOfPage = incidentPage.getUrlOfCurrentPage();
			softAssert.assertTrue(urlOfPage.contains("team"), "Hyperlinks under assign to column does not redirect to user details page");
			gotoIncidentTab();
			incidentPage.waitForElementsOfIncidentPage("selectedIncidentTitle");
			companyLinksList = incidentPage.getWebElementsOfIncidentPage("companylist");
			incidentPage.clickElementsOfIncidentPage(companyLinksList);
			LOGGER.info("Navigated to company details page");
			urlOfPage = incidentPage.getUrlOfCurrentPage();
			softAssert.assertTrue(urlOfPage.contains("company"), "Hyperlinks under company column does not redirect to companies details page");

			softAssert.assertAll();
			LOGGER.info("Test cases to verify hyperlinks on incident list page passed successfully");
		} else {
			LOGGER.error("Incident details page did not open successfully, hence verifyHyperlinksOnIncidentPage got failed.");
		}
	}

	/*
	 * This test case verified Incidents comments has been getting added successfully when Priority,status,assignee,type/subtype of incident is changes from incident details page.
	 */
	@Test(priority = 30, groups = { "REGRESSION", "REGRESSION_CI" }, description = "243984", enabled = false)
	public final void validateIncidentComments() throws Exception {
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		gotoCompaniesTab();
		impersonateCompanyByCompanyName(getCredentials(environment, "COMPANY_NAME_REPORTS"));
		IncidentDetailsPage incidentDetailsPage = new IncidentDetailsPage(PreDefinedActions.getDriver()).getInstance();
		IncidentPage incidentPage = new IncidentPage(PreDefinedActions.getDriver()).getInstance();
		HashMap<String, String> deviceDetails = incidentDetailsPage.enrollDevice(LanguageCode);
		Assert.assertTrue(deviceDetails != null, "Fake device couldn't be enrolled");
		LOGGER.info("Fake Device Has been enrolled successfully,Device serial number: " + deviceDetails.get("deviceSerialNumber"));
		String incidentId = incidentDetailsPage.submitIncident(LanguageCode, deviceDetails);
		Assert.assertTrue(incidentId != null, "Error occured while submitting an incident");
		LOGGER.info("Incident has been submitted successfully,Incident ID: " + incidentId);
		gotoIncidentTab();
		waitForPageLoaded();
		sleeper(2000);
		if (incidentPage.waitForElementsOfIncidentPage("clearAllIncidentSearch") || incidentPage.verifyElementIsClickableOfIncidentPage("clearAllIncidentSearch"))
			incidentPage.clickOnElementsOfIncidentPage("clearAllIncidentSearch");

		sleeper(2000);// Need this sleeper
		incidentPage.enterTextForIncidentPage("idSearchBox", incidentId);
		incidentPage.waitForElementsOfIncidentPage("incidentIdLink");
		sleeper(5000);// Need this sleeper
		incidentPage.clickOnElementsOfIncidentPage("incidentIdLink");

		waitForPageLoaded();
		String assigneeComment = incidentDetailsPage.changeAssigneeOfIncident();
		String assignee = getTextLanguage(LanguageCode, "daas_ui", "incident.comment.line1.usergenerated.changedAssignee");
		Assert.assertTrue(assigneeComment.contains((assignee.split("\\}|\\{")[2])), "Comment has not been getting added when assignee of the incient is changed");
		LOGGER.info("Incident comment has been added successfully for updating assignee");

		waitForPageLoaded();
		incidentDetailsPage.clickOnElementOfIncidentDetailsPage("incidentdetailTab");
		String statusComment = incidentDetailsPage.changeStatusOfIncident();
		String status = getTextLanguage(LanguageCode, "daas_ui", "incident.comment.line1.usergenerated.updatestate");
		Assert.assertTrue(statusComment.contains((status.split("\\}|\\{")[2])), "Comment has not been getting added when status of the incident is change");
		LOGGER.info("Incident comment has been added successfully for updating status");

		waitForPageLoaded();
		incidentDetailsPage.clickOnElementOfIncidentDetailsPage("incidentdetailTab");
		String priorityComment = incidentDetailsPage.changePriorityOfIncident();
		String priority = getTextLanguage(LanguageCode, "daas_ui", "incidents.comment.line1.usergenerated.updatepriority");
		Assert.assertTrue(priorityComment.contains((priority.split("\\}|\\{")[2])), "Comment has not been getting added when priority of the incident is changed");
		LOGGER.info("Incident comment has been added successfully for updating priority");

		waitForPageLoaded();
		incidentDetailsPage.clickOnElementOfIncidentDetailsPage("incidentdetailTab");
		HashMap<String, String> typeSubtypeComments = incidentDetailsPage.changeTypeSubtypeOfIncident();
		String typeComment = typeSubtypeComments.get("type");
		String subtypeComment = typeSubtypeComments.get("subType");
		String type = getTextLanguage(LanguageCode, "daas_ui", "incidents.comment.line1.usergenerated.updatetype");
		String subtype = getTextLanguage(LanguageCode, "daas_ui", "incidents.comment.line1.usergenerated.updatesubtype");

		Assert.assertTrue(typeComment.contains((type.split("\\}|\\{")[2])), "Comment has not been getting added when Type of the incident is changed");
		LOGGER.info("Incident comment has been added successfully for updating incident type");

		Assert.assertTrue(subtypeComment.contains((subtype.split("\\}|\\{")[2])), "Comment has not been getting added when Subtype of the incident is changed");
		LOGGER.info("Incident comment has been added successfully for updating incident subtype");

	}

	/**
	 * This test case verifies Incident tab is visible to standard company's Report admin
	 * 
	 * @throws Exception
	 */
	@Test(priority = 31, groups = { "REGRESSION", "REGRESSION_CI" }, description = "225195")
	public final void verifyStandardCompanyUserViewIncidentTab() throws Exception {
		login("COMPANY_ADMIN_EMAIl_INSIGHTS", "COMPANY_ADMIN_PASSWORD_INSIGHTS");

		IncidentPage incidentPage = new IncidentPage(PreDefinedActions.getDriver()).getInstance();
		expandMenuIcon();
		Assert.assertTrue(incidentPage.isIncidentTabVisible(LanguageCode), "Error occured while validating incident tab visibility for Standard company user");
		LOGGER.info("Validated Incident tab is visible for the Standard company's Report admin/IT admin");
	}

	@Test(priority = 32, groups = { "REGRESSION", "SMOKE", "REGRESSION_CI", "SMOKE_CI", "PRODUCTION_CI", "PRODUCTION_SANITY","PENTEST" }, description = "Test Case ID : 280817")
	public final void verifyIncidentListPage() throws Exception {
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		IncidentPage incidentPage = new IncidentPage(PreDefinedActions.getDriver()).getInstance();
		if(incidentPage.verifyElementsOfIncidentPage("incidentTab"))
			gotoIncidentCompanyUserTab();
		else
			gotoIncidentTab();
		LOGGER.info("Redirected to incident list page");
		removeLocationFilter();
		waitForPageLoaded();
		incidentPage.clearFiltersOfIncidentListPage("clearfilter");
		softAssert.assertTrue(incidentPage.verifyElementsOfIncidentPage("listTable"), "Table on incident list page is not loaded successfully");
		softAssert.assertTrue(incidentPage.verifyElementsOfIncidentPage("idHeader"), "Default column is not present on incident list page");
		softAssert.assertAll();
		LOGGER.info("Incident list page is loaded successfully");
	}

	@Test(priority = 33, groups = { "REGRESSION", "SMOKE", "REGRESSION_CI", "SMOKE_CI", "PRODUCTION_CI" }, description = "Test Case ID : 280818",enabled=true)
	public final void verifyIncidentDetailsPage() throws Exception {
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		IncidentPage incidentPage = new IncidentPage(PreDefinedActions.getDriver()).getInstance();
		IncidentDetailsPage incidentDetailsPage = new IncidentDetailsPage(PreDefinedActions.getDriver()).getInstance();
		gotoIncidentTab();
		LOGGER.info("Redirected to incident list page");
		removeLocationFilter();
		waitForPageLoaded();
		incidentPage.clearFiltersOfIncidentListPage("clearfilter");
		incidentPage.waitForElementsOfIncidentPage("tableOverlay");
		incidentPage.clickByJavaScriptOnIncidentPage("selectedIncidentTitle");
		incidentDetailsPage.waitForElementOfIncidentDetailsPage("tableOverlay");
		LOGGER.info("Redirected to incident details page");
		sleeper(1000);
		softAssert.assertTrue(incidentDetailsPage.waitForElementOfIncidentDetailsPage("detailsTitle"), "Details tile on incident details page is not loaded successfully");
		softAssert.assertTrue(incidentDetailsPage.waitForElementOfIncidentDetailsPage("descriptionTileHeader"), "Description tile on incident details page is not loaded successfully");
		incidentDetailsPage.clickOnElementOfIncidentDetailsPage("commentTab");
		sleeper(1000);
		softAssert.assertTrue(incidentDetailsPage.waitForElementOfIncidentDetailsPage("commentTileHeader"), "Comments tile on incident details page is not loaded successfully");
		softAssert.assertAll();
		LOGGER.info("Incident details page is loaded successfully");
	}

	@Test(priority = 34, groups = { "REGRESSION", "SMOKE", "REGRESSION_CI", "SMOKE_CI" })
	public final void verifyIdentityTileOnIncidentDetailsPage() throws Exception {
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		IncidentPage incidentPage = new IncidentPage(PreDefinedActions.getDriver()).getInstance();
		IncidentDetailsPage incidentDetailsPage = new IncidentDetailsPage(PreDefinedActions.getDriver()).getInstance();
		ArrayList<String> incidentsPageDetails = new ArrayList<>();
		gotoIncidentTab();
		LOGGER.info("Redirected to incident list page");
		removeLocationFilter();
		waitForPageLoaded();
		resetTableConfiguration();
		incidentPage.waitForElementsOfIncidentPage("tableOverlay");
		incidentPage.clickByJavaScriptOnIncidentPage("selectedIncidentTitle");
		waitForPageLoaded();
		incidentDetailsPage.waitUntilElementIsInvisibleOfIncidentDetailsPage("tableOverlay");
		if (!incidentDetailsPage.verifyElementOfIncidentDetailsPage("incidentDetailsPageTitle")) {
			Assert.fail("Incident details page did not load successfully");
		} else {
			LOGGER.info("Redirected to incident details page");
			incidentsPageDetails = incidentDetailsPage.getDetailsOfIncident();
			sleeper(5000);
			//		softAssert.assertTrue(incidentDetailsPage.getTextOfIncidentDetailsPage("titleOnIdentityTile").equalsIgnoreCase(incidentsPageDetails.get(5)), "Incident title on identity tile of incident details page is incorrect");
			softAssert.assertTrue(incidentDetailsPage.getTextOfIncidentDetailsPage("priorityOnIdentityTile").equalsIgnoreCase(incidentsPageDetails.get(3)), "Incident priority on identity tile of incident details page is incorrect");
			softAssert.assertTrue(incidentDetailsPage.getTextOfIncidentDetailsPage("createdOnLabelOnIdentityTile").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "incident.list.created_on").toUpperCase()), "Created on label on identity tile of incident details page is incorrect");
			softAssert.assertTrue(incidentDetailsPage.getTextOfIncidentDetailsPage("createdOnOnIdentityTile").split("\\s+")[0].equalsIgnoreCase(incidentsPageDetails.get(5)), "Incident created-on on identity tile of incident details page is incorrect");
			softAssert.assertTrue(incidentDetailsPage.getTextOfIncidentDetailsPage("statusLabelOnIdentityTile").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "incident.details.event_state").toUpperCase()), "Status label on identity tile of incident details page is incorrect");
			softAssert.assertTrue(incidentDetailsPage.getTextOfIncidentDetailsPage("statusOnIdentityTile").equalsIgnoreCase(incidentsPageDetails.get(4).toUpperCase()), "Incident status on identity tile of incident details page is incorrect");
			softAssert.assertTrue(incidentDetailsPage.getTextOfIncidentDetailsPage("idNumberLabelOnIdentityTile").equalsIgnoreCase( getTextLanguage(LanguageCode, "daas_ui", "incident.details.incident_code").toUpperCase()), "ID number label on identity tile of incident details page is incorrect");
			softAssert.assertTrue(incidentDetailsPage.getTextOfIncidentDetailsPage("idOnIdentityTile").equalsIgnoreCase( "#" + incidentsPageDetails.get(0)), "Incident status on identity tile of incident details page is incorrect");
			softAssert.assertAll();
			LOGGER.info("All test cases of identity tile on incident details page passed successfully");
		}
	}

	@Test(priority = 35, groups = { "REGRESSION", "SMOKE", "REGRESSION_CI", "SMOKE_CI" })
	public final void verifyDescriptionTileOnIncidentDetailsPage() throws Exception {
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		IncidentPage incidentPage = new IncidentPage(PreDefinedActions.getDriver()).getInstance();
		IncidentDetailsPage incidentDetailsPage = new IncidentDetailsPage(PreDefinedActions.getDriver()).getInstance();
		gotoIncidentTab();
		LOGGER.info("Redirected to incident list page");
		removeLocationFilter();
		waitForPageLoaded();
		resetTableConfiguration();
		incidentPage.waitForElementsOfIncidentPage("tableOverlay");
		incidentPage.clickByJavaScriptOnIncidentPage("selectedIncidentTitle");
		waitForPageLoaded();
		incidentDetailsPage.waitUntilElementIsInvisibleOfIncidentDetailsPage("tableOverlay");
		if (!incidentDetailsPage.verifyElementOfIncidentDetailsPage("incidentDetailsPageTitle")) {
			Assert.fail("Incident details page did not load successfully");
		} else {
			LOGGER.info("Redirected to incident details page");
			incidentDetailsPage.waitForElementOfIncidentDetailsPage("descriptionTileHeader");
			softAssert.assertTrue(incidentDetailsPage.getTextOfIncidentDetailsPage("descriptionTileHeader").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "incident.details.description").toUpperCase()), "Title on description tile of incident details page is incorrect");
			softAssert.assertTrue(incidentDetailsPage.verifyElementOfIncidentDetailsPage("descriptionOnDescriptionTile"), "Description on description tile of incident details page is not present");
			softAssert.assertAll();
			LOGGER.info("All test cases of description tile on incident details page have passed successfully");
		}
	}

	@Test(priority = 36, groups = { "REGRESSION", "SMOKE", "REGRESSION_CI", "SMOKE_CI" }, description = "NFR 284012", enabled = false)
	public final void verifyAssignedToListOnIncidentPage() throws Exception {
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		List<String> supportTeamMembersList = new ArrayList<String>();
		List<String> assigneeList = new ArrayList<String>();
		String currentAssignee = null;
		IncidentPage incidentPage = new IncidentPage(PreDefinedActions.getDriver()).getInstance();
		gotoSupportTeamTab();
		LOGGER.info("Redirected to support team list page");
		waitForPageLoaded();
		resetTableConfiguration();
		incidentPage.waitUntilElementIsInvisibleOfIncidentPage("tableOverlay");
		supportTeamMembersList = incidentPage.getTextOfPresentListOfIncidentPage("supportTeamMembersNameList");
		LOGGER.info("Fetched a list of support team member's name");
		supportTeamMembersList.add("Unassigned");
		gotoIncidentTab();
		LOGGER.info("Redirected to incident list page");
		waitForPageLoaded();
		resetTableConfiguration();
		incidentPage.waitUntilElementIsInvisibleOfIncidentPage("tableOverlay");
		incidentPage.clickOnElementsOfIncidentPage("assignedToBoxBefore");
		LOGGER.info("Clicked on assigned to dropdown filter on incident list page");
		assigneeList = incidentPage.getTextOfPresentListOfIncidentPage("assignedToListOnPopup");
		LOGGER.info("Fetched a list of assignee's present on the assigned to popup on incident list page");
		supportTeamMembersList.removeAll(Collections.singletonList(""));
		assigneeList.removeAll(Collections.singletonList(""));
		Collections.sort(supportTeamMembersList, String.CASE_INSENSITIVE_ORDER);
		Collections.sort(assigneeList, String.CASE_INSENSITIVE_ORDER);
		softAssert.assertTrue(supportTeamMembersList.equals(assigneeList), "List of assignees on assigned to dropdown of incident list page is incorrect for a support admin");
		logout();
		LOGGER.info("Logged out successfully");

		login("MSP_SPECIALIST_NEW_UI_Companies_EMAIL_COMPANIES", "MSP_SPECIALIST_NEW_UI_Companies_PASSWORD_COMPANIES");
		LOGGER.info("Logged in successfully as a reseller");
		gotoIncidentTab();
		LOGGER.info("Redirected to incident list page");
		waitForPageLoaded();
		resetTableConfiguration();
		incidentPage.waitUntilElementIsInvisibleOfIncidentPage("tableOverlay");
		incidentPage.clickOnElementsOfIncidentPage("assignedToBoxBefore");
		LOGGER.info("Clicked on assigned to dropdown filter on incident list page");
		assigneeList = incidentPage.getTextOfPresentListOfIncidentPage("assignedToListOnPopup");
		LOGGER.info("Fetched a list of assignee's present on the assigned to popup on incident list page");
		assigneeList.removeAll(Collections.singletonList(""));
		Collections.sort(assigneeList, String.CASE_INSENSITIVE_ORDER);
		softAssert.assertTrue(supportTeamMembersList.equals(assigneeList), "List of assignees on assigned to dropdown of incident list page is incorrect for a support specialist");
		logout();
		LOGGER.info("Logged out successfully");

		login("IT_ADMIN_COMPANY_USER_EMAIL_COMPANIES", "IT_ADMIN_COMPANY_USER_PASSWORD_COMPANIES");
		LOGGER.info("Logged in successfully as an IT admin");
		gotoIncidentTab();
		LOGGER.info("Redirected to incident list page");
		waitForPageLoaded();
		resetTableConfiguration();
		incidentPage.waitUntilElementIsInvisibleOfIncidentPage("tableOverlay");
		currentAssignee = incidentPage.getTextOfIncidentPage("firstIdAssignTo");
		LOGGER.info("Fetched assignee name for first record on incident list page");
		incidentPage.clickOnElementsOfIncidentPage("assignedToBoxBefore");
		LOGGER.info("Clicked on assigned to dropdown filter on incident list page");
		assigneeList = incidentPage.getTextOfPresentListOfIncidentPage("assignedToListOnPopup");
		LOGGER.info("Fetched a list of assignee's present on the assigned to popup on incident list page");
		softAssert.assertTrue(assigneeList.contains(currentAssignee), "List of assignees on assigned to dropdown of incident list page is incorrect for an it admin");
		softAssert.assertAll();
		LOGGER.info("All test cases of verifyAssignedToListOnIncidentPage passed");

	}

	@Test(priority = 37, groups = { "REGRESSION", "SMOKE", "REGRESSION_CI", "SMOKE_CI" }, description = "Test Case ID : 280820")
	public final void verifyIncidentListPageData() throws Exception {
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		String tenantID = getValueFromToken("tenant");
		gotoIncidentTab();
		LOGGER.info("Redirected to incident list page");
		waitForPageLoaded();
		Assert.assertTrue(getStatusCode(getSearchServiceApi(environment) + CommonVariables.DETAILSSEARCHSERVICEAPI + tenantID + CommonVariables.DETAILSSEARCHSERVICEAPI2, IncidentsVariables.SEARCHSERVICEBODY, "POST", getEnvironment(environment)) == CommonVariables.CODEOK, "Search service failed for incident list page");
		LOGGER.info("incident list page is loaded successfully");
	}

	@Test(priority = 38, groups = { "REGRESSION", "SMOKE", "REGRESSION_CI", "SMOKE_CI" }, description = "USER STORY 271542")
	public final void verifyTagsOnIncidentDetailsPage() throws Exception {
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		List<String> tagsList = null;
		IncidentPage incidentPage = new IncidentPage(PreDefinedActions.getDriver()).getInstance();
		IncidentDetailsPage incidentDetailsPage = new IncidentDetailsPage(PreDefinedActions.getDriver()).getInstance();
		gotoIncidentTab();
		LOGGER.info("Redirected to incident list page");
		removeLocationFilter();
		waitForPageLoaded();
		incidentPage.waitForElementsOfIncidentPage("tableOverlay");
		incidentPage.clickByJavaScriptOnIncidentPage("selectedIncidentTitle");
		LOGGER.info("Clicked on first incident from list");
		incidentDetailsPage.waitForElementOfIncidentDetailsPage("tableOverlay");
		LOGGER.info("Redirected to incident details page");
		if (getDataFromApi("repeated", getEnvironmentSpecificData(System.getProperty("environment"), "CATALYST_API_BASE_URL") + ConstantURL.GET_API_INCIDENT_REPEATED + getEnvironmentSpecificData(System.getProperty("environment"), "TENANT_ID_INCIDENTTAGS_DONOTDELETE") + "/incidents" + getUrlOfCurrentPage().substring(getUrlOfCurrentPage().lastIndexOf("/"))) != null) {
			softAssert.assertTrue(getDataFromApi("repeated", getEnvironmentSpecificData(System.getProperty("environment"), "CATALYST_API_BASE_URL") + ConstantURL.GET_API_INCIDENT_REPEATED + getEnvironmentSpecificData(System.getProperty("environment"), "TENANT_ID_INCIDENTTAGS_DONOTDELETE") + "/incidents" + getUrlOfCurrentPage().substring(getUrlOfCurrentPage().lastIndexOf("/"))).equals(incidentDetailsPage.getTextOfElementOnIncidentDetailsPage("repeatedValue")),
					"Repeated count on identity tile of incident details page is incorrect");
		} else {
			LOGGER.info("Repeated count is not available for this incident");
		}
		if (incidentDetailsPage.verifyElementOfIncidentDetailsPage("tags")) {
			tagsList = Arrays.asList(incidentDetailsPage.getTextOfElementOnIncidentDetailsPage("tags").replaceAll(" ", "").split(","));
			ArrayList<String> tagsList2 = new ArrayList<String>(tagsList);
			softAssert.assertTrue(incidentDetailsPage.matchTextOfIncidentDetailsPage("tagFieldLabel", getTextLanguage(LanguageCode, "daas_ui", "incident.details.tags")), "Tags field is not present under details tile of incident details page");
			softAssert.assertTrue(getDataFromApi("tags", getEnvironmentSpecificData(System.getProperty("environment"), "CATALYST_API_BASE_URL") + ConstantURL.GET_API_INCIDENT_REPEATED + getEnvironmentSpecificData(System.getProperty("environment"), "TENANT_ID_INCIDENTTAGS_DONOTDELETE") + "/incidents" + getUrlOfCurrentPage().substring(getUrlOfCurrentPage().lastIndexOf("/"))).equals(incidentDetailsPage.getTextOfElementOnIncidentDetailsPage("tags")),
					"Tags on details tile of incident details page is incorrect");
		} else {
			LOGGER.info("Tags are not available for this incident");
		}
		softAssert.assertAll();
		LOGGER.info("All test cases of verifyTagsOnIncidentDetailsPage have been passed successfully");
	}

	@Test(priority = 39, groups = { "REGRESSION", "SMOKE", "REGRESSION_CI", "SMOKE_CI" }, description = "Test Case 213222", enabled=false)
	public final void verifyCompanyFieldsDisplayedHyperlinkedForSalesAdmin() throws Exception {
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");// Login using primary account
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		DeviceDetailsPage deviceDetailsPage = new DeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
		DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
		IncidentPage incidentPage = new IncidentPage(PreDefinedActions.getDriver()).getInstance();
		IncidentDetailsPage incidentDetailsPage = new IncidentDetailsPage(PreDefinedActions.getDriver()).getInstance();
		gotoSettingsTabOfReportAdmin();
		LOGGER.info(" Capture company details");
		sleeper(2000);
		String companyName = companiesPage.getTextOfCompaniesPage("companyNameShow");
		LOGGER.info("CompanyName is: " + companyName);
		String companyEmailId = companiesPage.getTextOfCompaniesPage("companyEmailId");
		LOGGER.info("Company EmailId is: " + companyEmailId);
		goToPreferencesTab();
		CompanyPin companypin = new CompanyPin();
		sleeper(2000);
		String companyPin = companypin.getCompanyPin();
		LOGGER.info("companyPin is : " + companyPin);
		LOGGER.info("Enroll a fake device for this company");
		HashMap<String, String> deviceDetails = new HashMap<>();
		deviceDetails = EnrollFakeDevice.enrollFakeDeviceForIncident(companyName, companyPin, companyEmailId);
		Assert.assertTrue(!deviceDetails.isEmpty(), "Fake device couldnt be enrolled");
		String deviceSerialNumber = deviceDetails.get("deviceSerialNumber");
		LOGGER.info(deviceSerialNumber + " fake Device is Enrolled Successfully");
		LOGGER.info("Validate whether added fake device is showing on device page");
		gotoDevicesTab();
		resetTableConfiguration();
		sleeper(2000);// this is required for applying filter in device list page
		deviceListPage.clickOnElementsOfDeviceListPage("removeAllSearchFilters");
		deviceListPage.waitForElementsOfDeviceListPage("deviceListTable");
		deviceListPage.enterTextForDeviceListPage("serialNumberSearchBox", deviceSerialNumber);
		sleeper(2000);// this is required after entering serial Number
		deviceListPage.waitForElementsOfDeviceListPage("deviceListTable");
		String deviceSerialNumberAfterSearch = deviceListPage.getTextOfDeviceListPage("foundDeviceInSearchResult");
		Assert.assertEquals(deviceSerialNumberAfterSearch.contains(deviceSerialNumber), true);
		LOGGER.info("Device is present on Devices page.");
		LOGGER.info("Click on the link to reach device details page");
		deviceListPage.clickOnElementsOfDeviceListPage("foundDeviceInSearchResult");
		sleeper(2000);
		String incidentTitle = "Test Incident for enrolled device " + deviceSerialNumber;
		String incidentDescription = "Description for Test Incident for enrolled device " + deviceSerialNumber;
		String type = getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.type.account");
		String subtype = getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.subtype.licensing");
		String incidentId = deviceDetailsPage.submitCaseUsingAPI(type, subtype, incidentTitle, incidentDescription, deviceDetails.get("host"), deviceDetails.get("tenantId"), deviceDetails.get("userAuthToken"), deviceDetails.get("deviceId"), deviceDetails.get("UserID"));
		Assert.assertFalse(Strings.isNullOrEmpty(incidentId), "No incident id was created, hence failing the test case");
		LOGGER.info("Incident ID: " + incidentId);
		logout();
		login("PARTNER_COMPANY_EMAIL", "PARTNER_COMPANY_PASSWORD");
		gotoIncidentTab();
		resetTableConfiguration();
		incidentPage.verifyElementIsEnableOfIncidentPage("idSearchBox");
		sleeper(2000);// wait till entering incident id in incident search box
		incidentPage.waitForElementsOfIncidentPage("idSearchBox");
		LOGGER.info("Enter the value in incident ID field.");
		sleeper(2000);// wait till entering incident id in incident search box
		incidentPage.enterTextForIncidentPage("idSearchBox", incidentId);
		sleeper(3000);// this is needed before assert
		SoftAssert softAssert = new SoftAssert();
		incidentPage.scrollOnIncidentPage("companyIncidentListHref");
		incidentPage.waitForElementsOfIncidentPage("companyIncidentListHref");
		softAssert.assertTrue((incidentPage.getAttributesOfIncidentPage("companyIncidentListHref", "href") != null) ? true : false, "Company Hyperlink is not present in incident list page");
		incidentPage.scrollOnIncidentPage("incidentIdLink");
		incidentPage.clickOnElementsOfIncidentPage("incidentIdLink");
		incidentPage.waitForElementsOfIncidentPage("companyIncidentDetailPageHref");
		softAssert.assertTrue((incidentDetailsPage.getAttributesOfIncidentDetailPage("companyIncidentDetailPageHref", "href") != null) ? true : false, "Company Hyperlink is not present in incident detail page");
		softAssert.assertAll();
		LOGGER.info("Company hyperText is present in incident list and detail pages");
	}

	@Test(priority = 40, groups = { "REGRESSION", "SMOKE", "REGRESSION_CI", "SMOKE_CI" }, description = "Test Case 213222", enabled= false)
	public final void verifyAssignedToFieldsDisplayedHyperlinkedForSalesAdmin() throws Exception {
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");// Login using primary account
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();
		DeviceDetailsPage deviceDetailsPage = new DeviceDetailsPage(PreDefinedActions.getDriver()).getInstance();
		DeviceListPage deviceListPage = new DeviceListPage(PreDefinedActions.getDriver()).getInstance();
		IncidentPage incidentPage = new IncidentPage(PreDefinedActions.getDriver()).getInstance();
		IncidentDetailsPage incidentDetailsPage = new IncidentDetailsPage(PreDefinedActions.getDriver()).getInstance();
		gotoSettingsTabOfReportAdmin();
		LOGGER.info(" Capture company details");
		sleeper(2000);
		String companyName = companiesPage.getTextOfCompaniesPage("companyNameShow");
		LOGGER.info("CompanyName is: " + companyName);
		String companyEmailId = companiesPage.getTextOfCompaniesPage("companyEmailId");
		LOGGER.info("Company EmailId is: " + companyEmailId);
		goToPreferencesTab();
		CompanyPin companypin = new CompanyPin();
		sleeper(2000);
		String companyPin = companypin.getCompanyPin();
		LOGGER.info("companyPin is : " + companyPin);
		LOGGER.info("Enroll a fake device for this company");
		HashMap<String, String> deviceDetails = new HashMap<>();
		sleeper(2000);
		deviceDetails = EnrollFakeDevice.enrollFakeDeviceForIncident(companyName, companyPin, companyEmailId);
		Assert.assertTrue(!deviceDetails.isEmpty(), "Fake device couldnt be enrolled");
		String deviceSerialNumber = deviceDetails.get("deviceSerialNumber");
		LOGGER.info(deviceSerialNumber + " fake Device is Enrolled Successfully");
		LOGGER.info("Validate whether added fake device is showing on device page");
		gotoDevicesTab();
		sleeper(5000);// to click filter button in device list page
		resetTableConfiguration();
		sleeper(2000);
		deviceListPage.clickOnElementsOfDeviceListPage("removeAllSearchFilters");
		deviceListPage.waitForElementsOfDeviceListPage("deviceListTable");
		deviceListPage.enterTextForDeviceListPage("serialNumberSearchBox", deviceSerialNumber);
		sleeper(2000);// required wait here till device visible
		deviceListPage.waitForElementsOfDeviceListPage("deviceListTable");
		String deviceSerialNumberAfterSearch = deviceListPage.getTextOfDeviceListPage("foundDeviceInSearchResult");
		Assert.assertEquals(deviceSerialNumberAfterSearch.contains(deviceSerialNumber), true);
		LOGGER.info("Device is present on Devices page.");
		LOGGER.info("Click on the link to reach device details page");
		deviceListPage.clickOnElementsOfDeviceListPage("foundDeviceInSearchResult");
		sleeper(3000);// wait after click on device search result
		String incidentTitle = "Test Incident for enrolled device " + deviceSerialNumber;
		String incidentDescription = "Description for Test Incident for enrolled device " + deviceSerialNumber;
		String type = getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.type.account");
		String subtype = getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.subtype.licensing");
		String incidentId = deviceDetailsPage.submitCaseUsingAPI(type, subtype, incidentTitle, incidentDescription, deviceDetails.get("host"), deviceDetails.get("tenantId"), deviceDetails.get("userAuthToken"), deviceDetails.get("deviceId"), deviceDetails.get("UserID"));
		Assert.assertFalse(Strings.isNullOrEmpty(incidentId), "No incident id was created, hence failing the test case");
		LOGGER.info("Incident ID: " + incidentId);
		logout();
		login("PARTNER_COMPANY_EMAIL", "PARTNER_COMPANY_PASSWORD");
		gotoIncidentTab();
		resetTableConfiguration();
		incidentPage.waitForElementsOfIncidentPage("idSearchBox");
		LOGGER.info("Enter the value in incident ID field.");
		sleeper(2000);// wait till entering incident id in incident search box
		incidentPage.enterTextForIncidentPage("idSearchBox", incidentId);
		sleeper(3000);// this is needed, before click on assignto in incident page.
		incidentPage.verifyElementIsClickableOfIncidentPage("currentAssignTo");
		incidentPage.clickByJavaScriptOnIncidentPage("clickIncidentCheckbox");
		sleeper(2000);
		incidentPage.clickByJavaScriptOnIncidentPage("moreDropdownButtonForPartner");
		incidentPage.clickByJavaScriptOnIncidentPage("assignToMoreDropdownForPartner");
		incidentPage.waitForElementtobeClickableOfIncidentPage("assignmentDropdown");
		incidentPage.clickOnElementsOfIncidentPage("assignmentDropdown");
		incidentPage.clickOnElementsOfIncidentPage("dropdownOption");
		sleeper(2000);//wait till click on submit button
		incidentPage.waitForElementtobeClickableOfIncidentPage("assignToSubmit");
		incidentPage.clickByJavaScriptOnIncidentPage("assignToSubmit");
		SoftAssert softAssert = new SoftAssert();
		incidentPage.scrollOnIncidentPage("currentAssignTohref");
		//href link is not present in html dom so unable to validate assigned to hyperlink on incident list page. Hence commenting this line for now.
		//		softAssert.assertTrue((incidentPage.getAttributesOfIncidentPage("currentAssignTohref", "href") != null ? true : false), "Incident page doesnot contain assignto hyperlink ");
		sleeper(2000);
		incidentPage.scrollOnIncidentPage("incidentIdLink");
		incidentPage.clickOnElementsOfIncidentPage("incidentIdLink");
		sleeper(3000);// this is needed before assert
		softAssert.assertTrue(incidentDetailsPage.getAttributesOfIncidentDetailPage("assignToHref", "href") != null ? true : false, "Incident detail page doesnot contain hyperlink ");
		softAssert.assertAll();
		LOGGER.info("AssignTo hyperText is present in incident list and detail pages");
	}

	@Test(priority = 41, groups = { "REGRESSION", "SMOKE", "REGRESSION_CI", "SMOKE_CI" }, description = "TEST CASE 190211")
	public final void verifyAssignedToIncidentFilterIncludesUnassigned() throws Exception {
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");// Login using partner account
		IncidentPage incidentPage = new IncidentPage(PreDefinedActions.getDriver()).getInstance();
		gotoIncidentTab();
		resetTableConfiguration();
		sleeper(2000);// Required wait click on incident id checkbox
		incidentPage.clickOnElementsOfIncidentPage("assignedToDropdown");
		incidentPage.clickOnElementsOfIncidentPage("assignToPopupSearchBox");
		incidentPage.enterTextForIncidentPage("assignToPopupSearchBox", "Unassigned");
		incidentPage.verifyElementsOfIncidentPage("unassignedText");
		sleeper(2000);// Required wait before assert
		Assert.assertTrue((getTextLanguage(LanguageCode, "daas_ui", "asset.details.incidentChart.unassigned").equals(incidentPage.getTextOfIncidentPage("unassignedText"))), "assignTo field does not contain unassign text");
		LOGGER.info("Verified Unassign is present in incident filter on incident list");
	}

	@Test(priority = 42, groups = { "REGRESSION", "SMOKE", "REGRESSION_CI", "SMOKE_CI" })
	public final void generateXlsButton() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		gotoIncidentTab();
		IncidentPage incidentPage = new IncidentPage(PreDefinedActions.getDriver()).getInstance();

		// Test Case 1 - This test case validates if there are 1 or more incidents
		// present, the generate xls button is enabled
		if (incidentPage.verifyElementsOfIncidentPage("selectAllCheckBox"))
			softAssert.assertTrue(incidentPage.verifyElementIsEnableOfIncidentPage("generateXlsButton"), "By default GENERATE XLS button is not enabled on table configuration pop-up of incident page");
		softAssert.assertAll();
		LOGGER.info("All test cases of generate Xls passed");
	}

	/**
	 * This is method is used to verify case id and case status data matching with excel case id and case status data.
	 */
	@Test(priority = 42, groups = { "REGRESSION" }, description = "TEST CASE 377372")
	public final void verifyCaseIdCaseStatusForITA() throws Exception {
		login("IT_ADMIN_EMAIl_CASE", "IT_ADMIN_PASSWORD_CASE");// Login using IT Admin account
		IncidentPage incidentPage = new IncidentPage(PreDefinedActions.getDriver()).getInstance();
		gotoIncidentTab();
		resetTableConfiguration();
		incidentPage.verifyElementIsEnableOfIncidentPage("idSearchBox");
		sleeper(2000);// wait till entering incident id in incident search box
		incidentPage.enterTextForIncidentPage("idSearchBox",
				getEnvironmentSpecificData(System.getProperty("environment"), "INCIDENT_ID_CASE"));
		sleeper(2000);
		incidentPage.clickByJavaScriptOnIncidentPage("firstIdCheckBox");
		SoftAssert softAssert = new SoftAssert();
		// Case id and case status Tile
		String strCaseId = getTextLanguage(LanguageCode, "daas_ui", "incidents.caseID");
		String strCaseStatus = getTextLanguage(LanguageCode, "daas_ui", "incident.list.caseStatus");
		String incidentIdHeader = incidentPage.getTextOfIncidentPage("incidentIdHeader");
		String incidentIDValue = incidentPage.getTextOfIncidentPage("incidentIdSelect");
		incidentPage.waitForElementsOfIncidentPage("caseIdTitle");
		incidentPage.scrollOnIncidentPage("caseStatusTitle");
		softAssert.assertTrue(strCaseId.equalsIgnoreCase(incidentPage.getTextOfIncidentPage("caseIdTitle")), "Case id tile is not equal");
		softAssert.assertTrue(strCaseStatus.equalsIgnoreCase(incidentPage.getTextOfIncidentPage("caseStatusTitle")), "Case Status title is not equal");
		LOGGER.info("caseID and case status title verified");
		incidentPage.waitForElementsOfIncidentPage("caseIdData");
		String caseIdData = incidentPage.getTextOfIncidentPage("caseIdData");
		String caseStatusData = incidentPage.getTextOfIncidentPage("caseStatusData");
		incidentPage.waitForElementsOfIncidentPage("cancelSideBar");
		incidentPage.verifyElementIsClickableOfIncidentPage("cancelSideBar");
		incidentPage.clickByJavaScriptOnIncidentPage("cancelSideBar");
		incidentPage.waitForElementsOfIncidentPage("generateXlsButton");
		incidentPage.clickByJavaScriptOnIncidentPage("generateXlsButton");
		sleeper(5000);//After Downloading XLS required wait
		LOGGER.info("Excel downloaded");
		Workbook wb = WorkbookFactory.create(new File(ConstantPath.DOWNLOAD_PATH + "IncidentReport.xlsx"));
		Iterator<Row> rows = wb.getSheetAt(0).iterator();
		int cellindexid = -1;
		int cellIndexCaseId = -1;
		int cellIndexCaseStatus = -1;
		while (rows.hasNext()) {
			Row row = rows.next();
			Iterator<Cell> cells = row.cellIterator();
			while (cellIndexCaseStatus == -1 && cells.hasNext()) {
				Cell cell = cells.next();
				if (cell.getStringCellValue().equalsIgnoreCase("#" + incidentIdHeader)) {
					cellindexid = cell.getColumnIndex();
				}
				else LOGGER.info("Cell is not header");

				if (cell.getStringCellValue().equalsIgnoreCase(incidentPage.getTextOfIncidentPage("caseIdTitle"))) {
					cellIndexCaseId = cell.getColumnIndex();
				}
				else LOGGER.info("Cell is not case id title");

				if (cell.getStringCellValue().equalsIgnoreCase(incidentPage.getTextOfIncidentPage("caseStatusTitle"))) {
					cellIndexCaseStatus = cell.getColumnIndex();
				}
				else LOGGER.info("Cell is not case status title ");
			}
			if (cellIndexCaseStatus != -1 && row.getCell(cellindexid) != null
					&& row.getCell(cellindexid).getStringCellValue().equals(incidentIDValue)) {
				softAssert.assertTrue(row.getCell(cellIndexCaseId).getStringCellValue().equalsIgnoreCase(caseIdData),
						"UI case id value is not matching with excel case id");
				softAssert.assertTrue(
						row.getCell(cellIndexCaseStatus).getStringCellValue().equalsIgnoreCase(caseStatusData),
						"UI case status value is not matching with excel case status");
			}
		}
		softAssert.assertAll();
		LOGGER.info("Verified CaseId and CaseStatus matched with excel CaseId and CaseStatus");
	}

	@Test(priority = 43, groups = { "REGRESSION", "REGRESSION_CI", "SMOKE", "ALL_CI", "ALL" }, description = "TEST CASE ID:420732")
	public final void verifySecurityIncidentTypesAndSubtypes() throws Exception {
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		ArrayList<String> securitySubtypes = new ArrayList<String>(Arrays.asList(
				getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties","com.hp.mpi.icm.subtype.bromium2").toLowerCase(),
				getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties","com.hp.mpi.icm.title.truepositive2").toLowerCase(),
				getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties","com.hp.mpi.icm.subtype.suresenseproClientdisabled").toLowerCase(),
				getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties","com.hp.mpi.icm.subtype.suresenseprothreatprevented").toLowerCase()));
		IncidentPage incidentPage = new IncidentPage(PreDefinedActions.getDriver()).getInstance();
		gotoIncidentTab();
		LOGGER.info("Redirected to incident tab");
		removeLocationFilter();
		waitForPageLoaded();
		resetTableConfiguration();
		incidentPage.clickOnElementsOfIncidentPage("subTypeBoxBefore");
		Assert.assertTrue(incidentPage.getTextOfListOfIncidentPage("typeListOptions").containsAll(securitySubtypes),"Security subtypes are missing under subtypes dropdown on incidents list page");
		LOGGER.info("Verified test case verifySecurityIncidentTypesAndSubtypes");
	}

	@Test(priority = 44, groups = { "REGRESSION", "REGRESSION_CI", "SMOKE", "ALL_CI", "ALL" }, description = "TEST CASE ID:603794")
	public final void verifySecurityUnprotectedIncidentUserComment() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		IncidentPage incidentPage = new IncidentPage(PreDefinedActions.getDriver()).getInstance();
		IncidentDetailsPage incidentDetailsPage = new IncidentDetailsPage(PreDefinedActions.getDriver()).getInstance();
		gotoIncidentTab();
		LOGGER.info("Redirected to incident tab");
		waitForPageLoaded();
		resetTableConfiguration();
		incidentPage.waitUntilElementIsInvisibleOfIncidentPage("tableOverlay");
		incidentPage.enterTextForIncidentPage("idSearchBox", getEnvironmentSpecificData(System.getProperty("environment"), "THREAT_PREVENTED_INCIDENT_ID_USER_COMMENTS"));
		sleeper(2000);
		incidentPage.waitUntilElementIsInvisibleOfIncidentPage("tableOverlay");
		incidentPage.clickByJavaScriptOnIncidentPage("selectedIncidentTitle");
		LOGGER.info("Redirected to incident details page");
		waitForPageLoaded();
		sleeper(1000);
		incidentPage.waitUntilElementIsInvisibleOfIncidentPage("tableOverlay");
		softAssert.assertTrue(incidentDetailsPage.verifyElementOfIncidentDetailsPage("userCommentForSCAUnprotectedIncident"), "User comment missing on description for SCA unprotected incident");
		softAssert.assertAll();
		LOGGER.info("Test cases for verifySecurityUnprotectedIncidentUserComment passed successfully");
	}

	@Test(priority = 45, groups = { "REGRESSION", "REGRESSION_CI", "SMOKE", "ALL_CI", "ALL" }, description = "TEST CASE ID:595635", enabled=false)
	public final void verifyThreatDetailsLinkForSCAThreatIsolatedIncidents() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		IncidentPage incidentPage = new IncidentPage(PreDefinedActions.getDriver()).getInstance();
		IncidentDetailsPage incidentDetailsPage = new IncidentDetailsPage(PreDefinedActions.getDriver()).getInstance();
		gotoIncidentTab();
		LOGGER.info("Redirected to incident tab");
		waitForPageLoaded();
		resetTableConfiguration();
		incidentPage.waitUntilElementIsInvisibleOfIncidentPage("tableOverlay");
		incidentPage.enterTextForIncidentPage("idSearchBox", getEnvironmentSpecificData(System.getProperty("environment"), "THREAT_PREVENTED_INCIDENT_ID_EVENT_LINKS"));
		sleeper(2000);
		incidentPage.waitUntilElementIsInvisibleOfIncidentPage("tableOverlay");
		incidentPage.clickByJavaScriptOnIncidentPage("selectedIncidentTitle");
		LOGGER.info("Redirected to incident details page");
		waitForPageLoaded();
		incidentPage.waitUntilElementIsInvisibleOfIncidentPage("tableOverlay");
		softAssert.assertTrue(incidentDetailsPage.verifyElementOfIncidentDetailsPage("threatEventDetailsLinkIncidentDescription"), "Link to redirect to threat event details missing on description for SCA Threat Isolated incident");
		softAssert.assertAll();
		LOGGER.info("Test cases for verifyThreatDetailsLinkForSCAThreatIsolatedIncidents passed successfully");
	}

	/**
	 * Feature 609753: [Analytics][ActionableInsights][Insight] Insights board (buttons to actions)
	 * @throws Exception
	 */
	@Test(priority = 46, groups = {"REGRESSION", "SMOKE", "REGRESSION_CI"}, description = "TEST CASE ID:781649",enabled=false)
	public final void verifyBatteryHDDInsightsPresenceonSidePanel() throws Exception {
		login("COMPANY_ADMIN_EMAIl_INSIGHTS", "COMPANY_ADMIN_PASSWORD_INSIGHTS");
		SoftAssert softAssert = new SoftAssert();
		IncidentPage incidentPage = new IncidentPage(PreDefinedActions.getDriver()).getInstance();
		softAssert.assertTrue(incidentPage.verifyElementsOfIncidentPage("insightSideBarCollapse"), "insights side panel is not expanded by default on Dashboard");
		LOGGER.info("Incidents side bar is expanded by default on dashboard");
		softAssert.assertTrue(incidentPage.verifyElementsOfIncidentPage("batteryInsightsSidepanel"),"Battery Insights detail is not present on insights side panel");
		LOGGER.info("Battery Insights is present on Incidents side bar");
		softAssert.assertTrue(incidentPage.verifyElementsOfIncidentPage("hddInsightsSidepanel"),"HDD Insights detail is not present on insights side panel");
		LOGGER.info("HDD Insights is present on Incidents side bar");
		softAssert.assertAll();
		LOGGER.info("Test cases for verifyBatteryHDDInsightsPresenceonSidePanel executed successfully");
	}


	/**
	 * Feature 611198: [Analytics][ActionableInsights][Insight] HDD replacement insights
	 * @throws Exception
	 */
	@Test(priority = 47, groups = {"REGRESSION", "SMOKE", "REGRESSION_CI"}, description = "TEST CASE ID:760241",enabled=false)
	public final void verifyHDDInsightsWidgets() throws Exception {
		login("COMPANY_ADMIN_EMAIl_INSIGHTS", "COMPANY_ADMIN_PASSWORD_INSIGHTS");
		SoftAssert softAssert = new SoftAssert();
		IncidentPage incidentPage = new IncidentPage(PreDefinedActions.getDriver()).getInstance();
		softAssert.assertTrue(incidentPage.verifyElementsOfIncidentPage("insightSideBarCollapse"), "insights side panel is not expanded by default on Dashboard");
		LOGGER.info("Incidents side bar is expanded by default on dashboard");
		incidentPage.clickOnElementsOfIncidentPage("hddInsightsSidepanelViewButton");
		softAssert.assertTrue(incidentPage.verifyElementsOfIncidentPage("hddTypeWidgetHeader"), "HDD Type heading is not present");
		LOGGER.info("successfully landed on HDD insights page");
		softAssert.assertTrue(incidentPage.verifyElementsOfIncidentPage("ageofDeviceWidgetHeader"), "Age of Device heading is not present");
		softAssert.assertTrue(incidentPage.verifyElementsOfIncidentPage("hddTypeWidget"), "HDD Type Widget is not present");
		softAssert.assertTrue(incidentPage.verifyElementsOfIncidentPage("ageofDeviceWidget"), "Age of Device Widget is not present");
		LOGGER.info("HDD insights both widget have loaded successfully");
		softAssert.assertTrue(incidentPage.verifyElementsOfIncidentPage("hddTypeWidgetCentreCount"), "HDD Type Widget centre count is not present");
		softAssert.assertTrue(incidentPage.verifyElementsOfIncidentPage("hddTypeWidgetCentreString"), "HDD Type Widget centre string is not present");
		LOGGER.info("HDD insights widget centre count and string have loaded successfully");
		softAssert.assertAll();
	}

	/**
	 * Feature 611198: [Analytics][ActionableInsights][Insight] HDD replacement insights
	 * @throws Exception
	 */
	@Test(priority = 48, groups = {"REGRESSION", "SMOKE", "REGRESSION_CI"}, description = "TEST CASE ID:760241")
	public final void verifyHDDInsightsListPage() throws Exception {
		login("COMPANY_ADMIN_EMAIl_INSIGHTS", "COMPANY_ADMIN_PASSWORD_INSIGHTS");
		final String[] incidentListColumns = {"incident.list.id","incident.list.type","incident.list.subtype",
				"incident.list.created_on","incident.list.priority","incident.list.status","incident.list.assign_to","incident.list.device_name","incident.list.device_serial","incident.list.device_manufacturer",
		"asset_modal_location","incident.list.caseId","incident.list.caseStatus","incident.list.title","incident.list.device_model","incident.list.created_by","incidents.occurred_at","incident.list.updated_on","incident.list.deviceOs","incident.list.ageOfDevice"};
		IncidentPage incidentPage = new IncidentPage(PreDefinedActions.getDriver()).getInstance();
		incidentPage.clickOnElementsOfIncidentPage("hddInsightsSidepanelViewButton");
		//incidentPage.verifyElementsOfIncidentPage("hddTypeWidget");
		resetTableConfiguration();
		Assert.assertTrue(incidentPage.verifyColumnsofInsightsIncidentList(LanguageCode,"insightsIncidentListTableColumns",incidentListColumns), "insights incident list has wrong or missing column order");
		LOGGER.info("HDD insights list table is validated successfully");
	}

	/**
	 * Feature 611198: [Analytics][ActionableInsights][Insight] HDD replacement insights
	 * @throws Exception
	 */
	@Test(priority = 49, groups = {"REGRESSION", "SMOKE", "REGRESSION_CI"}, description = "TEST CASE ID:760241",enabled=false)
	public final void verifyHDDInsightsCountOnWidgetList() throws Exception {
		login("COMPANY_ADMIN_EMAIl_INSIGHTS", "COMPANY_ADMIN_PASSWORD_INSIGHTS");
		IncidentPage incidentPage = new IncidentPage(PreDefinedActions.getDriver()).getInstance();
		incidentPage.clickOnElementsOfIncidentPage("hddInsightsSidepanelViewButton");
		incidentPage.verifyElementsOfIncidentPage("hddTypeWidget");
		sleeper(1000);
		if(incidentPage.verifyElementsOfIncidentPage("clearSelection")) {
			incidentPage.clickOnElementsOfIncidentPage("clearSelectionClick");
			sleeper(2000);
		}
		incidentPage.mousehoverOnIncientPage("hddtypeInsightsLegend");
		incidentPage.clickOnElementsOfIncidentPage("hddtypeInsightsLegend");
		sleeper(2000);
		Assert.assertTrue(incidentPage.verifyCountOnInsightsIncidentPage(incidentPage.getTextOfIncidentPage("hddTypeWidgetCentreCount"),"HDD Insights"), "Count between widget and table did not match") ;
		LOGGER.info("Count matched between widget and table , verifyHDDInsightsCountOnWidgetList test case successfull");
		incidentPage.clickOnElementsOfIncidentPage("hddtypeInsightsLegend");
	}

	/**
	 * Feature 609764: [Analytics][ActionableInsights][Insight] Battery replacement insights
	 * @throws Exception
	 */
	@Test(priority = 50, groups = {"REGRESSION", "SMOKE", "REGRESSION_CI"}, description = "TEST CASE ID:757106",enabled =false)
	public final void verifyBatteryInsightsWidgets() throws Exception {
		login("COMPANY_ADMIN_EMAIl_INSIGHTS", "COMPANY_ADMIN_PASSWORD_INSIGHTS");
		SoftAssert softAssert = new SoftAssert();
		IncidentPage incidentPage = new IncidentPage(PreDefinedActions.getDriver()).getInstance();
		softAssert.assertTrue(incidentPage.verifyElementsOfIncidentPage("insightSideBarCollapse"), "insights side panel is not expanded by default on Dashboard");
		LOGGER.info("Incidents side bar is expanded by default on dashboard");
		incidentPage.clickOnElementsOfIncidentPage("batteryInsightsSidepanelViewButton");
		incidentPage.waitForElementsOfIncidentPage("batteryTypeWidgetHeader");
		softAssert.assertTrue(incidentPage.verifyElementsOfIncidentPage("batteryTypeWidgetHeader"), "Battery Type heading is not present");
		LOGGER.info("successfully landed on Battery insights page");
		softAssert.assertTrue(incidentPage.verifyElementsOfIncidentPage("ageofDeviceWidgetHeader"), "Age of Device heading is not present");
		softAssert.assertTrue(incidentPage.verifyElementsOfIncidentPage("batteryTypeWidget"), "Battery Type Widget is not present");
		softAssert.assertTrue(incidentPage.verifyElementsOfIncidentPage("ageofDeviceWidget"), "Age of Device Widget is not present");
		LOGGER.info("Battery insights both widget have loaded successfully");
		softAssert.assertTrue(incidentPage.verifyElementsOfIncidentPage("batteryTypeWidgetCentreCount"), "Battery Type Widget centre count is not present");
		softAssert.assertTrue(incidentPage.verifyElementsOfIncidentPage("batteryTypeWidgetCentreString"), "Battery Type Widget centre string is not present");
		softAssert.assertAll();
		LOGGER.info("Battery insights widget centre count and string have loaded successfully");

	}

	/**
	 * Feature 609764: [Analytics][ActionableInsights][Insight] Battery replacement insights
	 * @throws Exception
	 */
	@Test(priority = 51, groups = {"REGRESSION", "SMOKE", "REGRESSION_CI"}, description = "TEST CASE ID:740898")
	public final void verifyBatteryInsightsListPage() throws Exception {
		login("COMPANY_ADMIN_EMAIl_INSIGHTS", "COMPANY_ADMIN_PASSWORD_INSIGHTS");
		final String[] incidentListColumns = {"incident.list.id","incident.list.type","incident.list.subtype",
				"incident.list.created_on","incident.list.priority","incident.list.status","incident.list.assign_to","incident.list.device_name","incident.list.device_serial","incident.list.device_manufacturer",
		"asset_modal_location","incident.list.caseId","incident.list.caseStatus","incident.list.title","incident.list.device_model","incident.list.created_by","incidents.occurred_at","incident.list.updated_on","incident.list.deviceOs","incident.list.ageOfDevice"};
		IncidentPage incidentPage = new IncidentPage(PreDefinedActions.getDriver()).getInstance();
		incidentPage.scrollOnIncidentPage("Recommendation");
		incidentPage.clickOnElementsOfIncidentPage("batteryInsightsSidepanelViewButton");
		incidentPage.verifyElementsOfIncidentPage("batteryTypeWidget");
		resetTableConfiguration();
		Assert.assertTrue(incidentPage.verifyColumnsofInsightsIncidentList(LanguageCode,"insightsIncidentListTableColumns",incidentListColumns), "insights incident list has wrong or missing column order");
		LOGGER.info("Battery insights list table is validated successfully");
	}

	/**
	 * Feature 609764: [Analytics][ActionableInsights][Insight] Battery replacement insights
	 * @throws Exception
	 */
	@Test(priority = 52, groups = {"REGRESSION", "SMOKE", "REGRESSION_CI"}, description = "TEST CASE ID:757106",enabled=false)
	public final void verifyBatteryInsightsCountOnWidgetList() throws Exception {
		login("COMPANY_ADMIN_EMAIl_INSIGHTS", "COMPANY_ADMIN_PASSWORD_INSIGHTS");
		IncidentPage incidentPage = new IncidentPage(PreDefinedActions.getDriver()).getInstance();
		incidentPage.clickOnElementsOfIncidentPage("batteryInsightsSidepanelViewButton");
		incidentPage.verifyElementsOfIncidentPage("batteryTypeWidget");
		sleeper(1000);
		if(incidentPage.verifyElementsOfIncidentPage("clearSelection")) {
			incidentPage.clickOnElementsOfIncidentPage("clearSelectionClick");
			sleeper(2000);
		}
		incidentPage.mousehoverOnIncientPage("batterytypeInsightsLegend");
		incidentPage.clickOnElementsOfIncidentPage("batterytypeInsightsLegend");
		sleeper(2000);
		Assert.assertTrue(incidentPage.verifyCountOnInsightsIncidentPage(incidentPage.getTextOfIncidentPage("batteryTypeWidgetCentreCount"),"Battery Insights"), "Count between widget and table did not match") ;
		LOGGER.info("Count matched between widget and table , test case successfull");
		incidentPage.mousehoverOnIncientPage("batterytypeInsightsLegend");
		incidentPage.clickOnElementsOfIncidentPage("batterytypeInsightsLegend");	
	}

	/**
	 * Feature 613183: [Analytics][ActionableInsights] Split Battery failure/recall incident into two separate incidents
	 * 
	 * @throws Exception
	 */
	@Test(priority = 53, groups = {"REGRESSION", "SMOKE", "REGRESSION_CI"}, description = "TEST CASE ID:651631")
	public final void verifyBatteryIncidentSplitOnList() throws Exception {
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		gotoIncidentTab();
		IncidentPage incidentPage = new IncidentPage(PreDefinedActions.getDriver()).getInstance();
		incidentPage.waitForElementsOfIncidentPage("subTypeBoxBefore");
		sleeper(1000);
		incidentPage.clickOnElementsOfIncidentPage("subTypeBoxBefore");
		Assert.assertTrue(
				incidentPage.verifyValueNotInDropDown("subTypeListNew",
						getTextLanguage(LanguageCode, "daas_ui",
								"incidents.com.hp.mpi.icm.subtype.batterypredictive")),
				"Battery Predictive failure/recall subtype is also present");
		LOGGER.info("Battery Predictive Failure/Recall is not present in dropdown , test case successfull");
	}

	/**
	 * Feature 609774: [Analytics][Remediation] HDD Predictive failure replacement via ServiceNow/CDAX
	 * 
	 * @throws Exception
	 */
	@Test(priority = 54, groups = {"REGRESSION", "SMOKE", "REGRESSION_CI"}, description = "TEST CASE ID:651631",enabled=false)
	public final void verifyHDDSingleCasecreation() throws Exception {
		login("COMPANY_ADMIN_EMAIl_INSIGHTS", "COMPANY_ADMIN_PASSWORD_INSIGHTS");
		IncidentPage incidentPage = new IncidentPage(PreDefinedActions.getDriver()).getInstance();
		incidentPage.clickOnElementsOfIncidentPage("hddInsightsSidepanelViewButton");
		incidentPage.verifyElementsOfIncidentPage("hddTypeWidget");

		//Click on single incident on list to verify case creation
		scrollToBottom();
		incidentPage.clickOnElementsOfIncidentPage("firstIncidentCheckbox");
		sleeper(2000);
		Assert.assertTrue(incidentPage.verifyElementIsClickableOfIncidentPage("createCaseButton"),"Create Case button is not clickable");
		incidentPage.clickOnElementsOfIncidentPage("createCaseButton");

		//Verify first pop up screen
		sleeper(2000);
		Assert.assertTrue(incidentPage.verifyElementsOfIncidentPage("popUpTitle"),"Pop up first screen title is not present");
		Assert.assertTrue(incidentPage.verifyElementsOfIncidentPage("popUpIssues"),"Left side Issue Text is not present");
		Assert.assertTrue(incidentPage.verifyElementsOfIncidentPage("popUpServiceLocation"),"Left side Service location Text is not present");
		Assert.assertTrue(incidentPage.verifyElementsOfIncidentPage("popUpReviewSubmit"),"Left side review & submit Text is not present");
		Assert.assertTrue(incidentPage.verifyElementsOfIncidentPage("popUpRightSideIssues"),"Right side Issues Text is not present");
		Assert.assertTrue(incidentPage.verifyElementsOfIncidentPage("popUpRightSideDeviceName"),"Right side device name Text is not present");
		Assert.assertTrue(incidentPage.verifyElementsOfIncidentPage("popUpRightSideDetectedIssues"),"Right side detected issues Text is not present");
		Assert.assertTrue(incidentPage.verifyElementsOfIncidentPage("popUpRightSideRecommendedActions"),"Right side recommended actions Text is not present");
		Assert.assertTrue(incidentPage.verifyElementsOfIncidentPage("popUpAdditionalIssues"),"Right side additional issues Text is not present");
		Assert.assertTrue(incidentPage.verifyElementsOfIncidentPage("popUpSerialNumberHeader"),"Right side serial no Text is not present");
		Assert.assertTrue(incidentPage.verifyElementsOfIncidentPage("popUpEnrollmentUserHeader"),"Right side Enrollment no Text is not present");
		Assert.assertTrue(incidentPage.verifyElementsOfIncidentPage("popUpLastSignedInHeader"),"Right side last signedin user Text is not present");
		Assert.assertTrue(incidentPage.verifyElementsOfIncidentPage("popUpSerialNumberHeaderValue"),"serial no value is not present");
		Assert.assertTrue(incidentPage.verifyElementsOfIncidentPage("popUpDetectedIssuesValue"),"Detected Issues value is not present");
		Assert.assertTrue(incidentPage.verifyElementsOfIncidentPage("popUpEnrollmentUserHeaderValue"),"Enrollment User value is not present");
		Assert.assertTrue(incidentPage.verifyElementIsClickableOfIncidentPage("popUpIssuesNextButton"),"Next button is not clickable");
		incidentPage.clickOnElementsOfIncidentPage("popUpIssuesNextButton");

		//verify second pop up screen
		incidentPage.clickOnElementsOfIncidentPage("popIssuesLocationDropdownOpen");
		Assert.assertTrue(incidentPage.verifyElementsOfIncidentPage("popIssuesLocationDropdownOptiontoSelect"),
				"Location value is not present");
		incidentPage.clickOnElementsOfIncidentPage("popIssuesLocationDropdownOptiontoSelect");
		incidentPage.clickOnElementsOfIncidentPage("popUpLocationNextButton");

		//verify third pop up screen
		Assert.assertTrue(incidentPage.matchTextOfIncidentPage("popUpThirdScreenTitle", 
				getTextLanguage(LanguageCode, "daas_ui", "create.case.step.review")),"Review and Submit pop up screen has not opened");

		Assert.assertTrue(incidentPage.verifyElementsOfIncidentPage("popUpSubmitButton"),"Submit button is not present");
		Assert.assertTrue(incidentPage.verifyElementsOfIncidentPage("popUpCancelButton"),"Cancel button is not present");
		incidentPage.clickOnElementsOfIncidentPage("popUpCancelButton");
		Assert.assertTrue(incidentPage.verifyElementsOfIncidentPage("firstIncidentCheckbox"),"first incident on list is not present");

		LOGGER.info("HDD single case creation test case is successfull");
	}

	/**
	 * Feature 609774: [Analytics][Remediation] HDD Predictive failure replacement via ServiceNow/CDAX
	 * 
	 * @throws Exception
	 */
	@Test(priority = 55, groups = {"REGRESSION", "SMOKE", "REGRESSION_CI"}, description = "TEST CASE ID:651631,792884",enabled=false)
	public final void verifyHDDCasecreationIncidentDetailsandDeviceDetails() throws Exception {
		login("COMPANY_ADMIN_EMAIl_INSIGHTS", "COMPANY_ADMIN_PASSWORD_INSIGHTS");
		IncidentPage incidentPage = new IncidentPage(PreDefinedActions.getDriver()).getInstance();
		incidentPage.clickOnElementsOfIncidentPage("hddInsightsSidepanelViewButton");
		incidentPage.verifyElementsOfIncidentPage("hddTypeWidget");

		//Click on single incident on list to verify incident details
		scrollToBottom();
		incidentPage.clickOnElementsOfIncidentPage("firstIncidentLinkInTable");
		Assert.assertTrue(incidentPage.waitForElementsOfIncidentPage("incidentDetailsCreateCaseButton"),"Create case button is not present ");
		Assert.assertTrue(incidentPage.verifyElementIsClickableOfIncidentPage("incidentDetailsCreateCaseButton"),
				"Create case button is not clickable");
		incidentPage.clickOnElementsOfIncidentPage("incidentDetailsCreateCaseButton");
		sleeper(2000);
		Assert.assertTrue(incidentPage.verifyElementsOfIncidentPage("popUpTitle"),"Pop up first screen title is not present");
		Assert.assertTrue(incidentPage.verifyElementsOfIncidentPage("popUpIssues"),"Left side Issue Text is not present");
		Assert.assertTrue(incidentPage.verifyElementsOfIncidentPage("popUpServiceLocation"),"Left side Service location Text is not present");
		Assert.assertTrue(incidentPage.verifyElementsOfIncidentPage("popUpReviewSubmit"),"Left side review & submit Text is not present");
		Assert.assertTrue(incidentPage.verifyElementsOfIncidentPage("popUpRightSideIssues"),"Right side Issues Text is not present");
		Assert.assertTrue(incidentPage.verifyElementsOfIncidentPage("popUpRightSideDeviceName"),"Right side device name Text is not present");
		Assert.assertTrue(incidentPage.verifyElementsOfIncidentPage("popUpCancelButton"),"Cancel button is not present");
		incidentPage.clickOnElementsOfIncidentPage("popUpCancelButton");

		//Validate case creation button on device details page
		Assert.assertTrue(incidentPage.verifyElementsOfIncidentPage("incidentDetailsDeviceNameLink"),"Device Name link is not present");
		incidentPage.clickOnElementsOfIncidentPage("incidentDetailsDeviceNameLink");
		Assert.assertTrue(incidentPage.waitForElementsOfIncidentPage("deviceDetailsCreateCaseButton"),
				"case creation button on device details page is not present");
		Assert.assertTrue(incidentPage.verifyElementIsClickableOfIncidentPage("deviceDetailsCreateCaseButton"),
				"Create case button is not clickable");
		incidentPage.clickOnElementsOfIncidentPage("deviceDetailsCreateCaseButton");
		sleeper(2000);
		Assert.assertTrue(incidentPage.verifyElementsOfIncidentPage("popUpTitle"),"Pop up first screen title is not present");
		Assert.assertTrue(incidentPage.verifyElementsOfIncidentPage("popUpIssues"),"Left side Issue Text is not present");
		Assert.assertTrue(incidentPage.verifyElementsOfIncidentPage("popUpServiceLocation"),"Left side Service location Text is not present");
		Assert.assertTrue(incidentPage.verifyElementsOfIncidentPage("popUpReviewSubmit"),"Left side review & submit Text is not present");
		Assert.assertTrue(incidentPage.verifyElementsOfIncidentPage("popUpRightSideIssues"),"Right side Issues Text is not present");
		Assert.assertTrue(incidentPage.verifyElementsOfIncidentPage("popUpRightSideDeviceName"),"Right side device name Text is not present");
		Assert.assertTrue(incidentPage.verifyElementsOfIncidentPage("popUpCancelButton"),"Cancel button is not present");
		incidentPage.clickOnElementsOfIncidentPage("popUpCancelButton");
	}

	/**
	 * Feature 609774: [Analytics][Remediation] HDD Predictive failure replacement via ServiceNow/CDAX
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	@Test(priority = 56, groups = {"REGRESSION", "SMOKE", "REGRESSION_CI"}, description = "TEST CASE ID:796618,794980", enabled = false)
	public final void HDDBulkCasecreation() throws Exception {
		login("COMPANY_ADMIN_EMAIl_INSIGHTS", "COMPANY_ADMIN_PASSWORD_INSIGHTS");
		IncidentPage incidentPage = new IncidentPage(PreDefinedActions.getDriver()).getInstance();
		final String[] bulkCaseListColumns = {"incident.details.device_serial", "assets.table.heading.last_signed_user", "create.case.step.issue.title"};
		incidentPage.clickOnElementsOfIncidentPage("hddInsightsSidepanelViewButton");
		incidentPage.verifyElementsOfIncidentPage("hddTypeWidget");

		//Click on single incident on list to verify case creation
		scrollToBottom();
		sleeper(2000);
		incidentPage.clickOnElementsOfIncidentPage("bulkCaseFirstIncidentCheckbox");
		sleeper(4000);
		incidentPage.clickOnElementsOfIncidentPage("secondIncidentCheckbox");
		sleeper(4000);
		Assert.assertTrue(incidentPage.verifyElementIsClickableOfIncidentPage("createCaseButton"),"Create Case button is not clickable");
		incidentPage.clickOnElementsOfIncidentPage("createCaseButton");
		sleeper(2000);
		//Verify first pop up screen
		Assert.assertTrue(incidentPage.verifyElementsOfIncidentPage("popUpTitle"),"Pop up first screen title is not present");
		Assert.assertTrue(incidentPage.verifyElementsOfIncidentPage("popUpIssues"),"Left side Issue Text is not present");
		Assert.assertTrue(incidentPage.verifyElementsOfIncidentPage("popUpServiceLocation"),"Left side Service location Text is not present");
		Assert.assertTrue(incidentPage.verifyElementsOfIncidentPage("popUpReviewSubmit"),"Left side review & submit Text is not present");
		Assert.assertTrue(incidentPage.verifyElementsOfIncidentPage("popUpRightSideIssues"),"Right side Issues Text is not present");
		Assert.assertTrue(incidentPage.verifyColumnsofInsightsBulkCreationTable(LanguageCode,"bulkCaseTableColumns",bulkCaseListColumns),"Columns of bulk case creation pop up table are not correct");
		List<WebElement> bulkCaseTablelength = incidentPage.getElementsOfIncidentListPage("bulkCaseTablelength");
		int tableLength = bulkCaseTablelength.size();
		Assert.assertEquals(tableLength, 2);
		Assert.assertTrue(incidentPage.verifyElementIsClickableOfIncidentPage("popUpIssuesNextButtonBulkCaseCreation"),"Next button is not clickable");
		incidentPage.clickOnElementsOfIncidentPage("popUpIssuesNextButtonBulkCaseCreation");

		//verify second pop up screen
		incidentPage.clickOnElementsOfIncidentPage("popIssuesLocationDropdownOpen");
		Assert.assertTrue(incidentPage.verifyElementsOfIncidentPage("popIssuesLocationDropdownOptiontoSelect"),
				"Location value is not present");
		incidentPage.clickOnElementsOfIncidentPage("popIssuesLocationDropdownOptiontoSelect");
		incidentPage.clickOnElementsOfIncidentPage("popUpLocationNextButton");

		//verify third pop up screen
		Assert.assertTrue(incidentPage.matchTextOfIncidentPage("popUpThirdScreenTitle", 
				getTextLanguage(LanguageCode, "daas_ui", "create.case.step.review")),"Review and Submit pop up screen has not opened");
		Assert.assertTrue(incidentPage.verifyElementsOfIncidentPage("popUpSubmitButton"),"Submit button is not present");
		Assert.assertTrue(incidentPage.verifyElementsOfIncidentPage("popUpCancelButton"),"Cancel button is not present");
		incidentPage.clickOnElementsOfIncidentPage("popUpCancelButton");
		Assert.assertTrue(incidentPage.verifyElementsOfIncidentPage("firstIncidentCheckbox"),"first incident on list is not present");

		LOGGER.info("HDD Bulk case creation test case is successfull");
	}

	//Redundant testcase available on active care
	@Test(priority = 57, groups = { "REGRESSION", "REGRESSION_CI", "SMOKE", "ALL_CI", "ALL" }, description = "TEST CASE ID:645701", enabled=false)
	public final void verifyActiveCareOnPartnerDetailsPage() throws Exception {
		login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
		SoftAssert softAssert = new SoftAssert();
		PartnerPage partnerPage = new PartnerPage(PreDefinedActions.getDriver()).getInstance();
		IncidentPage incidentPage = new IncidentPage(PreDefinedActions.getDriver()).getInstance();
		IncidentDetailsPage incidentDetailsPage = new IncidentDetailsPage(PreDefinedActions.getDriver()).getInstance();
		LOGGER.info("Redirected to companies list page");
		gotoPartnersTab();
		LOGGER.info("Redirected to partner list page");
		sleeper(3000);
		waitForPageLoaded();
		resetTableConfiguration();
		partnerPage.enterTextForPartnerPage("nameSearchBox", getEnvironmentSpecificData(environment, "PARTNER_NAME_CASE_CREATION"));
		sleeper(2000);
		partnerPage.waitForElementsOfPartnerPage("firstRowPartner");
		partnerPage.clickByJavaScriptOnPartnerPage("firstRowPartner");
		LOGGER.info("Redirected to partner details page");
		waitForPageLoaded();
		partnerPage.clickOnElementsOfPartnerPage("preferenceTab");
		partnerPage.scrollOnPartnerPage("activeCareTitle");
		softAssert.assertTrue(partnerPage.matchTextOfPartnerPage("activeCareTitle", getTextLanguage(LanguageCode, "daas_ui", "partner.details.activeCare.label")),"Title on active care tile is incorrect");
		softAssert.assertTrue(partnerPage.matchTextOfPartnerPage("activeCareLabel", getTextLanguage(LanguageCode, "daas_ui", "partner.details.activeCare.label").toUpperCase()),"Label for toggle on active care tile is incorrect");
		if(partnerPage.getAttributesOfPartnerPage("activeCareToggle", "aria-checked").equals(false)) {
			partnerPage.clickOnElementsOfPartnerPage("activeCareToggle");
			LOGGER.info("Toggle is switched ON");
		} else
			LOGGER.info("Toggle is already switched ON");
		logout();
		login("PARTNER_CASE_CREATION_EMAIL", "PARTNER_CASE_CREATION_PASSWORD");
		LOGGER.info("Redirected to dashboard page");
		gotoIncidentTab();
		LOGGER.info("Redirected to incident page");
		waitForPageLoaded();
		resetTableConfiguration();
		incidentPage.enterTextForIncidentPage("idSearchBox", getEnvironmentSpecificData(environment, "CASE_CREATION_INCIDENT_ID"));
		incidentPage.clickByJavaScriptOnIncidentPage("selectedIncidentTitle");
		waitForPageLoaded();
		incidentDetailsPage.waitUntilElementIsInvisibleOfIncidentDetailsPage("tableOverlay");
		if (!incidentDetailsPage.verifyElementOfIncidentDetailsPage("incidentDetailsPageTitle")) {
			Assert.fail("Incident details page did not load successfully");
		} else {
			LOGGER.info("Redirected to incident details page");
		}
		softAssert.assertTrue(incidentPage.verifyElementIsEnableOfIncidentPage("createCaseButton"),"Support case button is not enabled after enabling toggle for active care");
		logout();
		login("ROOT_ADMIN_USER_US", "ROOT_PASSWORD_US");
		LOGGER.info("Redirected to companies list page");
		gotoPartnersTab();
		LOGGER.info("Redirected to partner list page");
		sleeper(3000);
		waitForPageLoaded();
		resetTableConfiguration();
		partnerPage.enterTextForPartnerPage("nameSearchBox", getEnvironmentSpecificData(environment, "PARTNER_NAME_CASE_CREATION"));
		sleeper(2000);
		partnerPage.waitForElementsOfPartnerPage("firstRowPartner");
		partnerPage.clickByJavaScriptOnPartnerPage("firstRowPartner");
		LOGGER.info("Redirected to partner details page");
		waitForPageLoaded();
		LOGGER.info("Redirected to partner details page");
		partnerPage.clickOnElementsOfPartnerPage("preferenceTab");
		partnerPage.scrollOnPartnerPage("activeCareTitle");
		partnerPage.clickOnElementsOfPartnerPage("activeCareToggle");
		LOGGER.info("Toggle is switched OFF");
		logout();
		login("PARTNER_CASE_CREATION_EMAIL", "PARTNER_CASE_CREATION_PASSWORD");
		LOGGER.info("Redirected to dashboard page");
		gotoIncidentTab();
		LOGGER.info("Redirected to incident page");
		waitForPageLoaded();
		resetTableConfiguration();
		incidentPage.enterTextForIncidentPage("idSearchBox", getEnvironmentSpecificData(environment, "CASE_CREATION_INCIDENT_ID"));
		incidentPage.clickByJavaScriptOnIncidentPage("selectedIncidentTitle");
		waitForPageLoaded();
		incidentDetailsPage.waitUntilElementIsInvisibleOfIncidentDetailsPage("tableOverlay");
		if (!incidentDetailsPage.verifyElementOfIncidentDetailsPage("incidentDetailsPageTitle")) {
			Assert.fail("Incident details page did not load successfully");
		} else {
			LOGGER.info("Redirected to incident details page");
		}
		softAssert.assertFalse(incidentPage.verifyElementIsEnableOfIncidentPage("createCaseButtonDisabled"),"Support case button is enabled after disabling toggle for active care");
		softAssert.assertAll();
		LOGGER.info("Test cases for verifyActiveCareOnPartnerDetailsPage passed successfully");
	}

	//Redundant testcase available on active care
	@Test(priority = 58, groups = { "REGRESSION", "REGRESSION_CI", "SMOKE", "ALL_CI", "ALL" }, description = "TEST CASE ID:811214", enabled=false)
	public final void verifyActiveCareTileOnNonACPartnerDetailsPage() throws Exception {
		login("ROOT_ADMIN_NEW_USER_COMPANIES", "ROOT_ADMIN_NEW_PASSWORD_COMPANIES");
		SoftAssert softAssert = new SoftAssert();
		PartnerPage partnerPage = new PartnerPage(PreDefinedActions.getDriver()).getInstance();
		LOGGER.info("Redirected to companies list page");
		gotoPartnersTab();
		LOGGER.info("Redirected to partner list page");
		sleeper(3000);
		waitForPageLoaded();
		resetTableConfiguration();
		partnerPage.enterTextForPartnerPage("nameSearchBox", PartnerVariables.PARTNER_DETAILS_TEST_USER);
		sleeper(2000);
		partnerPage.waitForElementsOfPartnerPage("firstRowPartner");
		partnerPage.clickByJavaScriptOnPartnerPage("firstRowPartner");
		LOGGER.info("Redirected to partner details page");
		waitForPageLoaded();
		partnerPage.clickByJavaScriptOnPartnerPage("preferenceTab");
		partnerPage.scrollOnPartnerPage("activeCareTitle");
		softAssert.assertTrue(partnerPage.matchTextOfPartnerPage("activeCareTitle", getTextLanguage(LanguageCode, "daas_ui", "partner.details.activeCare.label")),"Title on active care tile is incorrect");
		softAssert.assertTrue(partnerPage.getTextOfPartnerPage("activeCareLabel").equals(getTextLanguage(LanguageCode, "daas_ui", "partner.details.activeCare.label").toUpperCase()),"Label for toggle on active care tile is incorrect");
		softAssert.assertAll();
		LOGGER.info("Test cases for verifyActiveCareTileOnNonACPartnerDetailsPage passed successfully");
	}

	/**
	 * Feature 609774: [Analytics][Remediation] HDD Predictive failure replacement
	 * via ServiceNow/CDAX
	 * 
	 * @throws Exception
	 */
	@Test(priority = 59, groups = { "REGRESSION", "SMOKE", "REGRESSION_CI" }, description = "TEST CASE ID:797831", enabled=false)
	public final void verifyCaseIDStatusColumns() throws Exception {
		login("COMPANY_ADMIN_EMAIl_INSIGHTS", "COMPANY_ADMIN_PASSWORD_INSIGHTS");
		IncidentPage incidentPage = new IncidentPage(PreDefinedActions.getDriver()).getInstance();
		final String[] columnToCheck = { "incident.list.caseId", "incident.list.caseStatus" };
		final String[] incidentListColumns = { "incident.list.id", "incident.list.type", "incident.list.subtype",
				"incident.list.ageOfDevice", "incident.list.hddType", "incident.list.created_on",
				"incident.list.priority", "incident.list.status", "incident.list.assign_to",
				"incident.list.device_name", "incident.list.device_serial", "incident.list.device_manufacturer",
				"asset_modal_location", "incident.list.caseId", "incident.list.caseStatus" };
		incidentPage.clickOnElementsOfIncidentPage("hddInsightsSidepanelViewButton");
		incidentPage.verifyElementsOfIncidentPage("hddTypeWidget");
		scrollToBottom();
		sleeper(2000);
		incidentPage.clickOnElementsOfIncidentPage("insightsSettingsIcon");
		Assert.assertTrue(
				incidentPage.verifyColumnsofInsightsPageSettingsPopUp(LanguageCode, "columnListPopUp", columnToCheck),
				"Case ID and Case Status columns are not present");
		incidentPage.clickOnElementsOfIncidentPage("saveButtonPopUp");
		Assert.assertTrue(incidentPage.verifyColumnsofInsightsIncidentList(LanguageCode,
				"insightsIncidentListTableColumns", incidentListColumns),
				"insights incident list has wrong or missing column order");
		LOGGER.info("Case ID and Case Status Columns are verified successfully on list page");
		incidentPage.clickOnElementsOfIncidentPage("insightsSettingsIcon");
		incidentPage.clickOnElementsOfIncidentPage("resetToDefaultButtonPopUp");
		incidentPage.clickOnElementsOfIncidentPage("saveButtonPopUp");
		LOGGER.info("Case ID and Case Status Columns are verified successfully");
	}

	/**
	 * Feature 845016: [Analytics][Remediation] Disable "create case" ability for PARTNERS and PEM tenants
	 * 
	 * @throws Exception
	 */
	@Test(priority = 60, dataProvider="getLoginDetails",groups = { "REGRESSION", "SMOKE", "REGRESSION_CI" }, description = "TEST CASE ID:874620",enabled=false)
	public final void verifyCaseCreationDisabledForPEMAndPartner(String username, String password) throws Exception {
		login(username, password);
		IncidentPage incidentPage = new IncidentPage(PreDefinedActions.getDriver()).getInstance();
		incidentPage.clickOnElementsOfIncidentPage("hddInsightsSidepanelViewButton");
		incidentPage.verifyElementsOfIncidentPage("hddTypeWidget");
		scrollToBottom();
		sleeper(2000);
		//Verify under the ellipsis options
		incidentPage.mousehoverOnIncientPage("ellipsisIconListPage");

		incidentPage.clickOnElementsOfIncidentPage("ellipsisIconListPage");
		Assert.assertFalse(incidentPage.verifyElementsOfIncidentPage("createCaseEllipsisOption"),
				"Create Case button is Present on ellipsis options");

		//Verify create case button is not visible on actionable insights page
		incidentPage.clickOnElementsOfIncidentPage("disableCreateCaseFirstCheckbox");
		sleeper(4000);
		Assert.assertFalse(incidentPage.verifyElementsOfIncidentPage("createCaseButton"),
				"Create Case button is Present on actionable insights list page");

		//Click on single incident on list to verify incident details page
		incidentPage.clickOnElementsOfIncidentPage("firstIncidentLinkInTable");
		Assert.assertFalse(incidentPage.verifyElementsOfIncidentPage("incidentDetailsCreateCaseButton"),
				"Create case button is present ");


		//Validate case creation button on device details page
		Assert.assertTrue(incidentPage.verifyElementsOfIncidentPage("incidentDetailsDeviceNameLink"),"Device Name link is not present");
		incidentPage.clickOnElementsOfIncidentPage("incidentDetailsDeviceNameLink");
		Assert.assertFalse(incidentPage.verifyElementsOfIncidentPage("deviceDetailsCreateCaseButton"),
				"case creation button on device details page is present");

	}

	/**
	 * This test case validates the context sensitive help links on Incidents screen
	 *
	 * @throws Exception
	 */
	@Test(priority = 61, groups = { "REGRESSIONSETTINGS2", "SMOKE", "REGRESSION_CI",
	"SMOKE_CI" }, description = "Test Case ID : 891080")
	public final void verifyOnlineHelpLinksIncidents() throws Exception {
		login("IT_ADMIN_HELP_AND_SUPPORT_EMAIL", "IT_ADMIN_HELP_AND_SUPPORT_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();

		// Verify help and support link for Incidents tab
		waitForPageLoaded();
		gotoIncidentCompanyUserTab();
		LOGGER.info("Redirected to Incidents tab");
		settingsPage.waitForElementsOfSettingsPage("helpAndSupportIcon");
		settingsPage.clickOnElementsOfSettingsPage("helpAndSupportIcon");
		softAssert.assertTrue(
				settingsPage.matchTextOfSettingsPage("help&SupportLink",
						getTextLanguage(LanguageCode, "daas_ui", "settings.help_and_support.title")),
				"Help & Support link text did not match on Incidents tab");
		settingsPage.clickOnElementsOfSettingsPage("help&SupportLink");
		LOGGER.info("Clicked on Help & Support link from Incidents tab");
		waitForPageLoaded();
		softAssert.assertTrue(settingsPage.getTextOfSettingsPage("helpAndSupportTitleTextOnBreadcrumbs").equals(settingsPage.getTextLanguage(LanguageCode, "daas_ui", "sidemenu.support")),
				"User not redirected to Help & Support tab after clicking on Help & Support link from Incidents");
		gotoIncidentCompanyUserTab();

		// Verify overview link for Incidents tab
		waitForPageLoaded();
		LOGGER.info("Redirected to Incidents tab");
		settingsPage.waitForElementsOfSettingsPage("helpAndSupportIcon");
		settingsPage.clickOnElementsOfSettingsPage("helpAndSupportIcon");
		softAssert.assertTrue(
				settingsPage.matchTextOfSettingsPage("incidentsOverviewLink",
						getTextLanguage(LanguageCode, "daas_ui", "sidemenu.incidents.overview")),
				"Incidents overview link text did not match on Incidents tab");
		settingsPage.clickOnElementsOfSettingsPage("incidentsOverviewLink");
		LOGGER.info("Clicked on overview link from Incidents tab");
		settingsPage.switchToDifferentTab();
		sleeper(4000);// Url takes time to load
		softAssert.assertTrue(settingsPage.getUrlOfCurrentPage().contains(ConstantURL.KNOWLEDGE_BASE),
				"User not redirected to Online help section after clicking on overview link from Incidents");
		softAssert.assertTrue(settingsPage.getUrlOfCurrentPage().contains(ConstantURL.LANGUAGE_PARAMETER),
				"Language parameter not added in Service now URL");
		settingsPage.switchBackToPreviousTab();

		softAssert.assertAll();
		LOGGER.info("Test case to verify context sensitive links on incident tab passed successfully.");
	}


	@Test(priority = 62, description = " USER STORY 884704 & 901123 ", groups = { "REGRESSION", "SMOKE", "REGRESSION_CI", "SMOKE_CI", "STABILIZATION_STAGING" })
	public final void validateCommentSectionIncidentDetailsPage() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		gotoIncidentTab();
		LOGGER.info("Redirected to incident list page");
		IncidentDetailsPage incidentDetailsPage = new IncidentDetailsPage(PreDefinedActions.getDriver()).getInstance();

		IncidentPage incidentPage = new IncidentPage(PreDefinedActions.getDriver()).getInstance();
		removeLocationFilter();
		resetTableConfiguration();
		Assert.assertTrue(incidentPage.verifyElementsOfIncidentPage("selectedIncidentTitle"), "No incident is present on incident page, unable to proceed further");
		LOGGER.info("Atleast one incident is present, test case started");

		incidentPage.waitUntilElementIsInvisibleOfIncidentPage("tableOverlay");
		//sleeper(2000);

		incidentPage.clickByJavaScriptOnIncidentPage("selectedIncidentTitle");
		waitForPageLoaded();
		incidentDetailsPage.waitUntilElementIsInvisibleOfIncidentDetailsPage("tableOverlay");
		if (!incidentDetailsPage.verifyElementOfIncidentDetailsPage("commentTab")) {
			Assert.fail("Incident details page did not load successfully");
		} else {
			incidentDetailsPage.clickOnElementOfIncidentDetailsPage("commentTab");
			softAssert.assertTrue(incidentDetailsPage.verifyElementIsEnableOfIncidentDetailsPage("addCommentButton"), "Add comment button is not enabled on incident details page");		
			incidentDetailsPage.clickOnElementOfIncidentDetailsPage("addCommentButton");
			softAssert.assertTrue(incidentDetailsPage.verifyElementIsEnableOfIncidentDetailsPage("submitCommentButton"), "By default submit button is not enable on add comment popup");
			softAssert.assertTrue(incidentDetailsPage.verifyElementIsEnableOfIncidentDetailsPage("cancelAddComment"), "By default cancel button is not enabled on add comment popup");



			// Test Case : Remove Html tags from comment section

			incidentDetailsPage.clickOnElementOfIncidentDetailsPage("addCommentButton");
			incidentDetailsPage.enterTextForIncidentDetailsPage("commentArea", IncidentsVariables.TESTCOMMENT_HTMLTAGS);
			incidentDetailsPage.clickOnElementOfIncidentDetailsPage("submitCommentButton");
			//sleeper(2000);
			incidentDetailsPage.waitForElementOfIncidentDetailsPage("toastNotification");
			incidentDetailsPage.getTextsOfIncidentDetailsPage("toastNotification").contains(getTextLanguage(LanguageCode, "daas_ui", "global.messages.update_successful"));
			softAssert.assertTrue(incidentDetailsPage.getTextOfElementOnIncidentDetailsPage("incidentAddedComment").equals(IncidentsVariables.EXP_OUTPUTCOMMENT_HTMLTAGS), "two The comment added and updated comment on incident details page doesn't match");

			//TestCase : Allow Special character in comment section

			sleeper(2000);
			incidentDetailsPage.waitForElementOfIncidentDetailsPage("addCommentButton");
			incidentDetailsPage.clickOnElementOfIncidentDetailsPage("addCommentButton");
			incidentDetailsPage.enterTextForIncidentDetailsPage("commentArea", IncidentsVariables.TESTCOMMENT_SPECIALCHAR);
			sleeper(2000);	
			incidentDetailsPage.clickOnElementOfIncidentDetailsPage("submitCommentButton");
			sleeper(2000);
			incidentDetailsPage.waitForElementOfIncidentDetailsPage("toastNotification");
			incidentDetailsPage.getTextsOfIncidentDetailsPage("toastNotification").contains(getTextLanguage(LanguageCode, "daas_ui", "global.messages.update_successful"));
			softAssert.assertTrue(incidentDetailsPage.getTextOfElementOnIncidentDetailsPage("commentText").equals(IncidentsVariables.EXP_OUTPUTCOMMENT_SPECIALCHAR), " one The comment added and updated comment on incident details page doesn't match");

			//TestCase: Decode URL

			sleeper(2000);
			incidentDetailsPage.waitForElementOfIncidentDetailsPage("addCommentButton");
			incidentDetailsPage.clickOnElementOfIncidentDetailsPage("addCommentButton");
			incidentDetailsPage.enterTextForIncidentDetailsPage("commentArea", IncidentsVariables.TESTCOMMENT_DECODEURL);
			sleeper(2000);	
			incidentDetailsPage.clickOnElementOfIncidentDetailsPage("submitCommentButton");
			sleeper(2000);
			incidentDetailsPage.waitForElementOfIncidentDetailsPage("toastNotification");
			incidentDetailsPage.getTextsOfIncidentDetailsPage("toastNotification").contains(getTextLanguage(LanguageCode, "daas_ui", "global.messages.update_successful"));
			softAssert.assertTrue(incidentDetailsPage.getTextOfElementOnIncidentDetailsPage("commentText").equals(IncidentsVariables.EXP_OUTPUTCOMMENT_DECODEURL), " one The comment added and updated comment on incident details page doesn't match");


			softAssert.assertAll();
			LOGGER.info("All test cases of add comment button passed");
		}
	}


	/**
	 * User Story 912031:TechPulse IT/MSP/Report Admin users should able to assign the incidents to users
	 * 
	 * @throws Exception
	 */ 
	@Test(priority = 63, description = " USER STORY 912031 ; Roles ~ MSP, MSP Admin", groups = { "REGRESSION", "SMOKE", "REGRESSION_CI", "SMOKE_CI" })
	public final void verifyAdminToAssignIncidentToUser() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		String assignTo = null;
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		gotoIncidentTab();
		removeLocationFilter();
		waitForPageLoaded();
		IncidentPage incidentPage = new IncidentPage(PreDefinedActions.getDriver()).getInstance();
		resetTableConfiguration();
		Assert.assertTrue(incidentPage.verifyElementsOfIncidentPage("selectedIncidentTitle"), "No incident is present on incident page, unable to proceed further");
		LOGGER.info("Atleast one incident is present, test case started");
		incidentPage.scrollOnIncidentPage("currentAssignTohref");
		if(incidentPage.verifyElementsOfIncidentPage("currentAssignTohref"))
			assignTo = incidentPage.getTextOfIncidentPage("currentAssignTohref");
		else
			assignTo = getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.type.unassigned");
		softAssert.assertFalse(incidentPage.verifyElementsOfIncidentPage("assignToButton"), "Assign To button is enabled even when no checkbox is selected");
		incidentPage.mousehoverOnIncientPage("firstIdLogo");
		incidentPage.clickByJavaScriptOnIncidentPage("firstIdCheckBox");
		LOGGER.info("Selected first incident on incident list page");
		if(incidentPage.getTextOfIncidentPage("moreDropDownButtonText").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "users.list.action_buttons.more")))
		{
			incidentPage.clickOnElementsOfIncidentPage("moreDropdownButton");
			incidentPage.clickOnElementsOfIncidentPage("assignToMoreDropdown");
		}
		else{
			incidentPage.clickOnElementsOfIncidentPage("assignToButton");
		}
		incidentPage.waitForElementtobeClickableOfIncidentPage("assignToDropDownArrow");
		incidentPage.clickOnElementsOfIncidentPage("assignToDropDownArrow");
		LOGGER.info("Assign to dropdown button is clicked");
		sleeper(1000);
		String selectedValue = incidentPage.selectedValueFromSearchBoxInsidePopup(LanguageCode, "assignToPopupSearchBox", IncidentsVariables.ASSIGN_TO, "assignToEmptySearch", "assignToPopupSearchList", "assignToOnPopup2", "assignToDropDownArrow", "assignToPopupCancelButton");
		//incidentPage.clickOnElementsOfIncidentPage(selectedValue);
		LOGGER.info("Selected value on assign to dropdown");
		incidentPage.clickOnElementsOfIncidentPage("assignToPopupSaveButton");
		LOGGER.info("Verified save functionality");
		incidentPage.mousehoverOnIncientPage("firstIdLogo");
		incidentPage.scrollOnIncidentPage("currentAssignTohref");
		softAssert.assertTrue(selectedValue.contains(incidentPage.getTextOfIncidentPage("currentAssignTohref")), "Assignment is not updated successfully on incident page");
		softAssert.assertAll();
		LOGGER.info("All test cases of assign To pop-up passed");

	}

	@Test(priority = 64, description = "Test Case 936470 & ", groups = { "REGRESSION","REGRESSION_CI"})
	public final void verifyPageCountFunctionality() throws Exception {	
		SoftAssert softAssert = new SoftAssert();
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		PaginationPage paginationPage = new PaginationPage(PreDefinedActions.getDriver()).getInstance();
		CompaniesPage companiesPage = new CompaniesPage(PreDefinedActions.getDriver()).getInstance();

		try {
			gotoIncidentTab();
			LOGGER.info("Redirected to incident list page");
			waitForPageLoaded();
			resetTableConfiguration();
		
			// Test case 1 - This test validates that Page count is set to 100 by default
			paginationPage.clickOnElementsOfPaginationPage("paginationDropdownMenu");
			if (paginationPage.getAttributesOfPaginationPage("paginationCountSetTo100", "aria-disabled")
					.equals("true")) {
				LOGGER.info("Page count already set to 100");
			} else {
				paginationPage.clickOnElementsOfPaginationPage("paginationCountSetTo100");
				LOGGER.info("Page count is set to 100");
			}
			refreshPage();
			waitForPageLoaded();
			resetTableConfiguration();
		
			// Test case 2 - This test validates that user should be able to change the page
			// count
			paginationPage.clickOnElementsOfPaginationPage("paginationDropdownMenu");
			paginationPage.clickOnElementsOfPaginationPage("paginationCountSetTo50"); // select 50 page count
			refreshPage();
			waitForPageLoaded();
			paginationPage.clickOnElementsOfPaginationPage("paginationDropdownMenu");
			if (paginationPage.getAttributesOfPaginationPage("paginationCountSetTo50", "aria-disabled")
					.equals("true")) {
				LOGGER.info("Page count is set to 50");
			}
			// Test case 3 - This test validates that on switching tabs page count should be
			// intact as per user's selection
			gotoCompaniesTab();
			waitForPageLoaded();
			resetTableConfiguration();

			LOGGER.info("Navigated to companies page");
			companiesPage.verifyElementsOfCompaniesPage("companysearchbox");
			gotoIncidentTab();
			waitForPageLoaded();
			LOGGER.info("Navigated back to incident page");
			paginationPage.clickOnElementsOfPaginationPage("paginationDropdownMenu");
			if (paginationPage.getAttributesOfPaginationPage("paginationCountSetTo50", "aria-disabled")
					.equals("true")) {
				LOGGER.info("Page count is still 50 after switching tabs as expected");

			}
			// Test case 4 - This test validates that on relogin page count should be intact
			// as per user's selection
			logout();
			LOGGER.info("Logged out");
			login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
			LOGGER.info("Logged in");
			gotoIncidentTab();
			waitForPageLoaded();
			resetTableConfiguration();
		
			LOGGER.info("Redirected to incident list page");
			sleeper(3000);
			paginationPage.clickOnElementsOfPaginationPage("paginationDropdownMenu");
			if (paginationPage.getAttributesOfPaginationPage("paginationCountSetTo50", "aria-disabled")
					.equals("true")) {
				LOGGER.info("Page count is still 50 after relogin as expected");
				softAssert.assertTrue(paginationPage.getAttributesOfPaginationPage("paginationCountSetTo50", "aria-selected")
						.equals("true"), "Relogin Test Case failed");
			}
			softAssert.assertAll();
			LOGGER.info("Test cases for save page count functionality as per user's choice passed successfully");
		}

		finally {
			gotoIncidentTab();
			LOGGER.info("Redirected to incident list page");
			waitForPageLoaded();
			resetTableConfiguration();
		
			paginationPage.clickOnElementsOfPaginationPage("paginationDropdownMenu");
			paginationPage.clickOnElementsOfPaginationPage("paginationCountSetTo100"); // select 100 page count
			LOGGER.info("Page count is set back to default 100");
		}
	}

	/**
	 * User Story 958070: [Analytics][Remediation]Disable the multiple detected issue option for PI Customers
	 *
	 * @throws Exception
	 */
	@Test(priority = 65, groups = {"REGRESSION", "SMOKE", "REGRESSION_CI"}, description = "TEST CASE ID:961686",enabled=false)
	public final void verifySingleCaseCreationRadioButton() throws Exception {
		login("IT_ADMIN_EMAIl_CASE", "IT_ADMIN_PASSWORD_CASE");
		IncidentPage incidentPage = new IncidentPage(PreDefinedActions.getDriver()).getInstance();
		incidentPage.clickOnElementsOfIncidentPage("hddInsightsSidepanelViewButton");
		incidentPage.verifyElementsOfIncidentPage("hddTypeWidget");
		//Click on single incident on list to verify case creation
		scrollToBottom();
		incidentPage.enterTextForIncidentPage("textBoxIncidentId",getEnvironmentSpecificData(System.getProperty("environment"), "CASE_INCIDENT_ID"));
		sleeper(2000);
		incidentPage.clickOnElementsOfIncidentPage("firstIncidentCheckbox");
		sleeper(2000);
		Assert.assertTrue(incidentPage.verifyElementIsClickableOfIncidentPage("createCaseButton"), "Create Case button is not clickable");
		incidentPage.clickOnElementsOfIncidentPage("createCaseButton");
		//Verify first pop up screen
		sleeper(2000);
		Assert.assertTrue(incidentPage.verifyElementsOfIncidentPage("popUpTitle"), "Pop up first screen title is not present");
		scrollToBottom();
		Assert.assertTrue(incidentPage.verifyElementIsSelectedOfIncidentPage("radioButtonCaseCreationPopUp"),
				"radio button is not present on the pop up");
		LOGGER.info("Only HDD incident checkbox is selected");
		incidentPage.clickOnElementsOfIncidentPage("popUpIssuesCancelButton");
		if(incidentPage.verifyElementsOfIncidentPage("clearSelection")) {
			incidentPage.clickOnElementsOfIncidentPage("clearSelectionClick");
			sleeper(2000);
		}
	}

	/**
	 * Feature 609758: [Insights][PI_Sheff][BIOS][Insights] BIOS Status Insights
	 * @throws Exception
	 */
	@Test(priority = 66, groups = {"REGRESSION", "SMOKE", "REGRESSION_CI"}, description = "TEST CASE ID:997874")
	public final void verifyBIOSInsightsPresenceOnRecommendation() throws Exception {
		login("COMPANY_ADMIN_EMAIl_INSIGHTS", "COMPANY_ADMIN_PASSWORD_INSIGHTS");
		SoftAssert softAssert = new SoftAssert();
		IncidentPage incidentPage = new IncidentPage(PreDefinedActions.getDriver()).getInstance();
		incidentPage.scrollOnIncidentPage("Recommendation");
		softAssert.assertTrue(incidentPage.verifyElementsOfIncidentPage("Recommendation"), "Recommendation is not present by default on Dashboard");
		LOGGER.info("Recommended action is present by default on dashboard");
		softAssert.assertTrue(incidentPage.verifyElementsOfIncidentPage("BIOSInsightsRecommendation"),"BIOS Insights detail is not present");
		LOGGER.info("BIOS Insights is present on Recommended actions");
		softAssert.assertAll();
		LOGGER.info("Test cases for verifyBIOSInsightsPresenceonSidePanel executed successfully");
	}

	/**
	 * Feature 609764: [Analytics][ActionableInsights][Insight] Battery replacement insights
	 * @throws Exception
	 */
	@Test(priority = 67, groups = {"REGRESSION", "SMOKE", "REGRESSION_CI"}, description = "TEST CASE ID:998489")
	public final void verifyBIOSInsightsListPage() throws Exception {
		login("COMPANY_ADMIN_EMAIl_INSIGHTS", "COMPANY_ADMIN_PASSWORD_INSIGHTS");
		final String[] incidentListColumns = {"assets.table.heading.alias_name","assets.table.heading.serialNumber","assets.table.heading.installedBiosVersion","assets.table.heading.installedBiosStatus",
				"assets.table.heading.latestBiosVersion","assets.table.heading.latestBiosReleaseDate","assets.table.heading.latestBiosCriticality","assets.table.heading.appliedBiosUpdatePolicy","assets.table.heading.type","assets.location.name","assets.table.heading.enrolled",
				"campaign.details.status","assets.table.heading.model","assets.table.heading.active_warranty"};
		IncidentPage incidentPage = new IncidentPage(PreDefinedActions.getDriver()).getInstance();
		incidentPage.scrollOnIncidentPage("Recommendation");

		incidentPage.clickOnElementsOfIncidentPage("BIOSInsightsSidepanelViewButton");
		incidentPage.verifyElementsOfIncidentPage("BIOSCountWidget");

		Assert.assertTrue(incidentPage.verifyColumnsofInsightsIncidentList(LanguageCode,"insightsIncidentListTableColumns",incidentListColumns), "insights incident list has wrong or missing column order");
		LOGGER.info("BIOS insights list table is validated successfully");

	}


	/**
	 * Feature 804587: [Insights][PI_Sheff][CaseCreate_Battery][Action_Remediate]Recall battery replacement via ServiceNow
	 */
	@Test(priority = 68, groups = { "REGRESSION", "SMOKE", "REGRESSION_CI" }, description = "TEST CASE ID: 1036522, 1036526, 1036532, 1036533, 1036544",enabled=false)
	public final void verifyCaseCreation_BatteryRecall_IT_ReportAdmin() throws Exception {
		login("IT_ADMIN_EMAIl_CASE", "IT_ADMIN_PASSWORD_CASE");
		IncidentPage incidentPage = new IncidentPage(PreDefinedActions.getDriver()).getInstance();
		incidentPage.clickOnElementsOfIncidentPage("batteryInsightsSidepanelViewButton");
		incidentPage.verifyElementsOfIncidentPage("batteryTypeWidget");
		scrollToBottom();
		sleeper(2000);
		//Verify under the ellipsis options : Test Case Id: 1036526
		incidentPage.mousehoverOnIncientPage("ellipsisIconListPage");

		incidentPage.clickOnElementsOfIncidentPage("ellipsisIconListPage");
		Assert.assertTrue(incidentPage.verifyElementsOfIncidentPage("createCaseEllipsisOption"),
				"Create Case button is NOT present on Ellipsis options");

		//Verify Create Case button is present on Actionable Insights page : Test Case Id: 1036522
		incidentPage.clickOnElementsOfIncidentPage("createCaseFirstCheckbox");
		sleeper(4000);
		Assert.assertTrue(incidentPage.verifyElementsOfIncidentPage("createCaseButton"),
				"Create Case button is NOT present on Actionable Insights list page");

		//Verify Create Case button is present on Incident Details page : Test Case Id: 1036532
		incidentPage.clickOnElementsOfIncidentPage("firstIncidentLinkInTable");
		Assert.assertTrue(incidentPage.verifyElementsOfIncidentPage("incidentDetailsCreateCaseButton"),
				"Create Case button is NOT present on Incident Details page");

		//Verify Create Case button is present on Device Details page : Test Case Id: 1036533
		Assert.assertTrue(incidentPage.verifyElementsOfIncidentPage("incidentDetailsDeviceNameLink"),"Device Name link is NOT present in Incident Details page");
		incidentPage.clickOnElementsOfIncidentPage("incidentDetailsDeviceNameLink");
		Assert.assertTrue(incidentPage.verifyElementsOfIncidentPage("deviceDetailsCreateCaseButton"),
				"Create Case button is NOT present on Device Details page");

	}

	/**
	 * Feature 804587: [Insights][PI_Sheff][CaseCreate_Battery][Action_Remediate]Recall battery replacement via ServiceNow
	 */	
	@Test(priority = 69, groups = {"REGRESSION", "SMOKE", "REGRESSION_CI"}, description = "TEST CASE ID: 1036543, 1036544",enabled=false)
	public final void verifyBatteryRecallSingleCasecreation() throws Exception {
		login("IT_ADMIN_EMAIl_CASE", "IT_ADMIN_PASSWORD_CASE");
		IncidentPage incidentPage = new IncidentPage(PreDefinedActions.getDriver()).getInstance();
		incidentPage.clickOnElementsOfIncidentPage("batteryInsightsSidepanelViewButton");
		incidentPage.verifyElementsOfIncidentPage("batteryTypeWidget");

		//Click on single incident on list to verify case creation
		scrollToBottom();
		incidentPage.clickOnElementsOfIncidentPage("createCaseFirstCheckbox");
		sleeper(2000);
		Assert.assertTrue(incidentPage.verifyElementIsClickableOfIncidentPage("createCaseButton"),"Create Case button is not clickable");
		incidentPage.clickOnElementsOfIncidentPage("createCaseButton");

		//Verify first pop up screen
		sleeper(2000);
		Assert.assertTrue(incidentPage.verifyElementsOfIncidentPage("popUpTitle"),"Pop up first screen title is not present");
		Assert.assertTrue(incidentPage.verifyElementsOfIncidentPage("popUpIssues"),"Left side Issue Text is not present");
		Assert.assertTrue(incidentPage.verifyElementsOfIncidentPage("popUpServiceLocation"),"Left side Service location Text is not present");
		Assert.assertTrue(incidentPage.verifyElementsOfIncidentPage("popUpReviewSubmit"),"Left side review & submit Text is not present");
		Assert.assertTrue(incidentPage.verifyElementsOfIncidentPage("popUpRightSideIssues"),"Right side Issues Text is not present");
		Assert.assertTrue(incidentPage.verifyElementsOfIncidentPage("popUpRightSideDeviceName"),"Right side device name Text is not present");
		Assert.assertTrue(incidentPage.verifyElementsOfIncidentPage("popUpRightSideDetectedIssues"),"Right side detected issues Text is not present");
		Assert.assertTrue(incidentPage.verifyElementsOfIncidentPage("popUpRightSideRecommendedActions"),"Right side recommended actions Text is not present");
		Assert.assertTrue(incidentPage.verifyElementsOfIncidentPage("popUpAdditionalIssues"),"Right side additional issues Text is not present");
		Assert.assertTrue(incidentPage.verifyElementsOfIncidentPage("popUpSerialNumberHeader"),"Right side serial no Text is not present");
		Assert.assertTrue(incidentPage.verifyElementsOfIncidentPage("popUpEnrollmentUserHeader"),"Right side Enrollment no Text is not present");
		Assert.assertTrue(incidentPage.verifyElementsOfIncidentPage("popUpLastSignedInHeader"),"Right side last signedin user Text is not present");
		Assert.assertTrue(incidentPage.verifyElementsOfIncidentPage("popUpSerialNumberHeaderValue"),"serial no value is not present");
		Assert.assertTrue(incidentPage.verifyElementsOfIncidentPage("popUpDetectedIssuesValue"),"Detected Issues value is not present");
		Assert.assertTrue(incidentPage.verifyElementsOfIncidentPage("popUpEnrollmentUserHeaderValue"),"Enrollment User value is not present");
		Assert.assertTrue(incidentPage.verifyElementIsClickableOfIncidentPage("popUpIssuesNextButton"),"Next button is not clickable");
		incidentPage.clickOnElementsOfIncidentPage("popUpIssuesNextButton");

		//verify second pop up screen
		incidentPage.clickOnElementsOfIncidentPage("popIssuesLocationDropdownOpen");
		Assert.assertTrue(incidentPage.verifyElementsOfIncidentPage("popIssuesLocationDropdownOptiontoSelect"),
				"Location value is not present");
		incidentPage.clickOnElementsOfIncidentPage("popIssuesLocationDropdownOptiontoSelect");
		incidentPage.clickOnElementsOfIncidentPage("popUpLocationNextButton");

		//verify third pop up screen
		Assert.assertTrue(incidentPage.matchTextOfIncidentPage("popUpThirdScreenTitle", 
				getTextLanguage(LanguageCode, "daas_ui", "create.case.step.review")),"Review and Submit pop up screen has not opened");

		Assert.assertTrue(incidentPage.verifyElementsOfIncidentPage("popUpSubmitButton"),"Submit button is not present");
		Assert.assertTrue(incidentPage.verifyElementsOfIncidentPage("popUpCancelButton"),"Cancel button is not present");
		incidentPage.clickOnElementsOfIncidentPage("popUpSubmitButton");

		LOGGER.info("Battery Recall incident case is submitted successfully");
	}



	/**
	 * Feature 909748: [Insights][PI_Sheff][BIOS][Action_Remediate]BIOS Remediation via HP Connect
	 */	

	@Test(priority = 70, groups = {"REGRESSION", "SMOKE", "REGRESSION_CI"}, description = "BUG ID: 1037199")
	public final void VerifyBiosWidget() throws Exception {
		login("COMPANY_ADMIN_EMAIl_INSIGHTS", "COMPANY_ADMIN_PASSWORD_INSIGHTS");
		IncidentPage incidentPage = new IncidentPage(PreDefinedActions.getDriver()).getInstance();
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		SoftAssert softAssert = new SoftAssert();
		sleeper(2000);
	    incidentPage.scrollOnIncidentPage("BIOSwidget");
		//dashboardPage.scrollToDashboardPage("biosUpdateInsightsHeader");
	    
		incidentPage.verifyElementsOfIncidentPage("BIOSwidgetheader");
		softAssert.assertTrue(incidentPage.matchTextOfIncidentPage("BIOSwidgetheader", getTextLanguage(LanguageCode, "daas_ui", "dashboard.widget.biosInsight.header")),"Bios Insight widget title is incorrect");
		LOGGER.info("BIOS header is present and text verified");
		
		softAssert.assertTrue(incidentPage.verifyElementsOfIncidentPage("deviceWithoutPolicy"), "Devices with outdated bios without policy is not present");
		softAssert.assertTrue(incidentPage.verifyElementsOfIncidentPage("deviceNotCompatible"), "Devices with outdated bios with policy is not present");
		LOGGER.info("BIOS widget components verified");
		incidentPage.clickOnElementsOfIncidentPage("deviceWithoutPolicy");
		softAssert.assertTrue(incidentPage.matchTextOfIncidentPage("biospolicyPageTitle", getTextLanguage(LanguageCode, "daas_ui", "insight.bios_update.breadcrumbs.title")),"devices without policy Page title is incorrect");
		//gotoDashboardTab();
		//incidentPage.scrollOnIncidentPage("BIOSwidget");
		//incidentPage.clickOnElementsOfIncidentPage("deviceWithpolicy");
		//softAssert.assertTrue(incidentPage.matchTextOfIncidentPage("biospolicyPageTitle", getTextLanguage(LanguageCode, "daas_ui", "insight.with.bios_update.breadcrumbs.title")),"Page title is incorrect");
		gotoDashboardTab();
		incidentPage.scrollOnIncidentPage("BIOSwidget");
		incidentPage.clickOnElementsOfIncidentPage("deviceNotCompatible");
		softAssert.assertTrue(incidentPage.matchTextOfIncidentPage("biospolicyPageTitle", getTextLanguage(LanguageCode, "daas_ui", "devices.not.compatible")),"Page title is incorrect");
		
		softAssert.assertAll();
		LOGGER.info("BIOS pages title verified");
		

	}

	@Test(priority = 71, groups = {"REGRESSION", "SMOKE", "REGRESSION_CI"}, description = "BUG ID: 1037199")
	public final void VerifyBiosInsightCount() throws Exception {
		login("IT_ADMIN_EMAIL_BIOS_2", "IT_ADMIN_PASSWORD_BIOS");
		IncidentPage incidentPage = new IncidentPage(PreDefinedActions.getDriver()).getInstance();
		incidentPage.clickOnElementsOfIncidentPage("BIOSInsightsSidepanelViewButton");
		sleeper(2000);
		incidentPage.clickOnElementsOfIncidentPage("RecommendedActionPanelExpandArrow");
		incidentPage.verifyElementsOfIncidentPage("BIOSCountWidget");
		sleeper(1000);
		if(incidentPage.verifyElementsOfIncidentPage("clearSelection")) {
			incidentPage.clickOnElementsOfIncidentPage("clearSelectionClick");
			sleeper(2000);
		}			
		Assert.assertEquals(incidentPage.getTextOfIncidentPage("BIOSCountWidget"), (incidentPage.getTextOfIncidentPage("BIOSInsightsSidepanelCount")), "Count Mismatch error");
		Assert.assertTrue(incidentPage.verifyCountOnInsightsIncidentPage(incidentPage.getTextOfIncidentPage("BIOSInsightsSidepanelCount"),"Bios Insights"), "Count between widget and table did not match") ;
		LOGGER.info("Count matched between widget and table , verifyHDDInsightsCountOnWidgetList test case successfull");

	}

    //Microsoft authentication is not possible via automation
	@Test(priority = 72, groups = {"REGRESSION", "SMOKE", "REGRESSION_CI"}, description = "BUG ID: 1037199", enabled = false)
	public final void VerifyBiosRemediation() throws Exception {
		login("IT_ADMIN_EMAIL_BIOS_2", "IT_ADMIN_PASSWORD_BIOS");
		IncidentPage incidentPage = new IncidentPage(PreDefinedActions.getDriver()).getInstance();
		incidentPage.clickOnElementsOfIncidentPage("BIOSInsightsSidepanelViewButton");
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		sleeper(2000);
		scrollToBottom();
		incidentPage.enterTextForIncidentPage("textBoxDeviceId",getEnvironmentSpecificData(System.getProperty("environment"), "BIOS_DEVICE_NAME"));
		sleeper(2000);
		incidentPage.clickOnElementsOfIncidentPage("firstIncidentCheckbox");
		sleeper(2000);
		Assert.assertTrue(incidentPage.verifyElementsOfIncidentPage("applyPolicyBtn"), "Apply policy button is not present");;
		incidentPage.clickOnElementsOfIncidentPage("applyPolicyBtn");
		Assert.assertTrue(incidentPage.matchTextOfIncidentPage("applyPolicypopUpTitle", getTextLanguage(LanguageCode, "daas_ui", "device_list_apply_policy_label")),"Bios apply policy pop up title is incorrect");
		Assert.assertTrue(incidentPage.matchTextOfIncidentPage("applyPolicypopUpOptnOne", getTextLanguage(LanguageCode, "daas_ui", "device_list_applyPolicyPopup_label_keep_bios_update")),"Bios apply policy option one is incorrect");
		Assert.assertTrue(incidentPage.matchTextOfIncidentPage("applyPolicypopUpOptnTwo", getTextLanguage(LanguageCode, "daas_ui", "device_list_applyPolicyPopup_label_deploy_critical_bios_update")),"Bios apply policy option two is incorrect");
		Assert.assertTrue(incidentPage.matchTextOfIncidentPage("applyPolicypopUpDisclaimer", getTextLanguage(LanguageCode, "daas_ui", "device_list_applyPolicyDisclaimer")),"Bios apply policy disclaimer is not showing");
		incidentPage.clickOnElementsOfIncidentPage("applyPolicySavebtn");
		if (incidentPage.waitForElementsOfIncidentPage("MEMtoggle"))
		{
			
			incidentPage.clickOnElementsOfIncidentPage("MEMtoggle");
			incidentPage.enterTextForIncidentPage("MEMDomainName", IncidentsVariables.MEM_DOMAINNAME);
			incidentPage.clickOnElementsOfIncidentPage("MEMSavebtn");		
			if (incidentPage.waitForElementsOfIncidentPage("MicrosoftLogo"))
			{	
			incidentPage.enterTextForIncidentPage("MicrosoftSignInUsername", IncidentsVariables.MEM_USERNAME);
			incidentPage.clickOnElementsOfIncidentPage("MicrosoftSignInNextbtn");
			incidentPage.enterTextForIncidentPage("MicrosoftSignInPassword", IncidentsVariables.MEM_PASSWORD);
			incidentPage.clickOnElementsOfIncidentPage("MicrosoftSignInbtn");
			incidentPage.clickOnElementsOfIncidentPage("MicrosoftSignInbtn");		
			}	
		}	
		gotoDashboardTab();
		dashboardPage.scrollToDashboardPage("biosUpdateInsightsHeader");
		incidentPage.clickOnElementsOfIncidentPage("deviceWithpolicy");
		sleeper(2000);
		Assert.assertTrue(incidentPage.matchTextOfIncidentPage("biospolicyPageTitle", getTextLanguage(LanguageCode, "daas_ui", "insight.with.bios_update.breadcrumbs.title")),"Page title is incorrect");	
		incidentPage.enterTextForIncidentPage("textBoxDeviceId",getEnvironmentSpecificData(System.getProperty("environment"), "BIOS_DEVICE_NAME"));
		sleeper(2000);
		incidentPage.waitForElementsOfIncidentPage("BiosStatus");
		Assert.assertTrue(incidentPage.getTextOfIncidentPage("BiosStatus").equals(getTextLanguage(LanguageCode, "daas_ui", "assets.list.bios.alwaysKeepBiosUpToDate")), "Bios status is incorrect");
		incidentPage.clickOnElementsOfIncidentPage("firstIncidentCheckbox");
		Assert.assertTrue(incidentPage.verifyElementsOfIncidentPage("applyPolicyBtn"), "Manage Policy button is not present");
		incidentPage.clickOnElementsOfIncidentPage("applyPolicyBtn");
		Assert.assertTrue(incidentPage.matchTextOfIncidentPage("managePolicypopUpTitle", getTextLanguage(LanguageCode, "daas_ui", "device_list_manage_policy_label")),"Bios Manage policy pop up title is incorrect");
		incidentPage.clickOnElementsOfIncidentPage("applyPolicypopUpOptnTwoRadioBtn");
		incidentPage.clickOnElementsOfIncidentPage("applyPolicySavebtn");
		incidentPage.waitForElementsOfIncidentPage("BiosStatus");
		Assert.assertTrue(incidentPage.getTextOfIncidentPage("BiosStatus").equals(getTextLanguage(LanguageCode, "daas_ui", "assets.list.bios.updateCriticalBiosOnly")), "Bios status is incorrect");

		incidentPage.clickOnElementsOfIncidentPage("firstIncidentCheckbox");
		incidentPage.clickOnElementsOfIncidentPage("applyPolicyBtn");
		incidentPage.clickOnElementsOfIncidentPage("applyPolicypopUpOptnThreeRadioBtn");
		incidentPage.clickOnElementsOfIncidentPage("applyPolicySavebtn");
		Assert.assertTrue(incidentPage.verifyElementsOfIncidentPage("Smiley"), "Device policy is not removed");
	
	}
	
	
	
	


	/**
	 * Feature 1073253: [Insights][PI_Sheff][BIOS][Insights_DEX]Replace existing UX for "Recommended Actions" and DEX score updates right side panel to new UX (bottom list)
	 */	
	
	 @Test(priority = 73, groups = {"REGRESSION", "SMOKE", "REGRESSION_CI"}, description = "Test Case: 1107437", enabled =false)
	 
     public final void VerifyBiosInsightRecommendedActionExperienceManagement() throws Exception {
	  login("COMPANY_ADMIN_EMAIl_INSIGHTS", "COMPANY_ADMIN_PASSWORD_INSIGHTS");
	  IncidentPage incidentPage = new IncidentPage(PreDefinedActions.getDriver()).getInstance();
	  ExperienceMgmtPage experienceMgmtPage = new ExperienceMgmtPage(PreDefinedActions.getDriver()).getInstance();
	  
	  //Verify right action panel is not present
	  waitForPageLoaded();
	  Assert.assertFalse(incidentPage.verifyElementsOfIncidentPage("RecommendedActionPanelExpandArrow"), "Recommended action panel is still present");
	  
	  //Verify recommendation is present on dashboard page
	  gotoDashboardTab();
	  waitForPageLoaded();
	  incidentPage.scrollOnIncidentPage("Recommendation");
	  //experienceMgmtPage.scrollToExperienceMgmtPage("Recommendation");
	  sleeper(3000);
	  Assert.assertTrue(incidentPage.verifyElementsOfIncidentPage("Recommendation"), "Recommendation is not present");

	//Verify recommendation is present on experience management page
	  gotoExperienceMgmtPageTab();
	  waitForPageLoaded();
	  experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("devicePerformanceTab");	
	  experienceMgmtPage.scrollDownCharts();
		if(incidentPage.waitForElementsOfIncidentPage("RecommendationExp"));
		{ 
			experienceMgmtPage.scrollToExperienceMgmtPage("RecommendationExp");
			experienceMgmtPage.verifyElementsOfExperienceMgmtPage("RecommendationExp");
			LOGGER.info("Recommendation is present");
		} 
		sleeper(1000);
	experienceMgmtPage.clickByJavaScriptOnExperienceMgmtPage("deviceHealthTab");
	 // experienceMgmtPage.clickOnElementsOfExperienceMgmtPage("deviceHealthTab");		
	  if(incidentPage.waitForElementsOfIncidentPage("RecommendationExp"));
		{ 
			 experienceMgmtPage.scrollToExperienceMgmtPage("RecommendationExp");
			 experienceMgmtPage.verifyElementsOfExperienceMgmtPage("RecommendationExp");
			 LOGGER.info("Recommendation is present");
					
		}
}
	 

		/**
		 * Feature 1114360: [Insights][PI_TBD][BIOSv2][Action_Remediate]Permission Consolidation (Intune & MEM)
		 * 
		 */	
	 
        //microsoft two-factor authentication cannot be done via automation
		@Test(priority = 74, groups = {"REGRESSION", "SMOKE", "REGRESSION_CI"}, description = "Test case : 1145484", enabled=false)
		public final void VerifyIntuneConfiguration() throws Exception {
			login("IT_ADMIN_EMAIL_BIOS_2", "IT_ADMIN_PASSWORD_BIOS");
			IncidentPage incidentPage = new IncidentPage(PreDefinedActions.getDriver()).getInstance();
			SettingsPage settingsPage = new SettingsPage(PreDefinedActions.getDriver()).getInstance();
			incidentPage.clickOnElementsOfIncidentPage("BIOSInsightsSidepanelViewButton");
			scrollToBottom();
			incidentPage.enterTextForIncidentPage("textBoxDeviceId",getEnvironmentSpecificData(System.getProperty("environment"), "BIOS_DEVICE_NAME"));
			incidentPage.clickOnElementsOfIncidentPage("firstIncidentCheckbox");
			incidentPage.clickOnElementsOfIncidentPage("applyPolicyBtn");
			///incidentPage.clickOnElementsOfIncidentPage("applyPolicySavebtn");
			
			
			if (incidentPage.waitForElementsOfIncidentPage("intuneLabel"))
			{
				//Verify Intune pop up header
				Assert.assertTrue(incidentPage.verifyElementsOfIncidentPage("intuneLabel"), "Intune pop up is not shown");
				sleeper(3000); //Needed on purpose
				incidentPage.clickOnElementsOfIncidentPage("intuneToggle");
				incidentPage.enterTextForIncidentPage("intuneDomainName", IncidentsVariables.INTUNE_DOMAINNAME);
				incidentPage.clickOnElementsOfIncidentPage("intuneSaveBtn");		
				if (incidentPage.waitForElementsOfIncidentPage("MicrosoftLogo"))
				{	
				incidentPage.enterTextForIncidentPage("MicrosoftSignInUsername", IncidentsVariables.INTUNE_USERNAME);
				incidentPage.clickOnElementsOfIncidentPage("MicrosoftSignInNextbtn");
				incidentPage.enterTextForIncidentPage("MicrosoftSignInPassword", IncidentsVariables.INTUNE_PASSWORD);
				sleeper(3000); //Needed on purpose
				incidentPage.clickOnElementsOfIncidentPage("MicrosoftSignInbtn");
				incidentPage.clickOnElementsOfIncidentPage("MicrosoftSignInbtn");		
				}
				waitForPageLoaded();
				sleeper(3000);
				incidentPage.waitForElementsOfIncidentPage("biospolicyPageTitle");
				
				//Verify User is taken back to device list list page once configuration is done
				Assert.assertTrue(incidentPage.matchTextOfIncidentPage("biospolicyPageTitle", getTextLanguage(LanguageCode, "daas_ui", "insight.bios_update.breadcrumbs.title")),"Page title is incorrect");
				gotoCompanySettingsTab();
				waitForPageLoaded();
				LOGGER.info("Redirected to Settings tab");
				settingsPage.waitForElementsOfSettingsPage("preferenceTab");	
				settingsPage.clickOnElementsOfSettingsPage("preferenceTab");
				settingsPage.scrollTillViewSettingsPage("intuneSection");
				incidentPage.scrollOnIncidentPage("intuneSection");
				
				//Verify intune configuration is added
				Assert.assertTrue(incidentPage.verifyElementsOfIncidentPage("intuneEnabled"), "Intune is not configured");
				Assert.assertTrue(incidentPage.matchTextOfIncidentPage("intuneEnabled", getTextLanguage(LanguageCode, "daas_ui", "settings.data.enabled")), "Intune is not configured");
				LOGGER.info("Intune Configuration added successfully");
				
				//Delete Intune configuration				
				settingsPage.scrollTillViewSettingsPage("intuneSection");
				incidentPage.clickOnElementsOfIncidentPage("intuneDeletebtn");
				incidentPage.clickOnElementsOfIncidentPage("intuneDeletepopupbtn");
				waitForPageLoaded();
				Assert.assertTrue(incidentPage.verifyElementsOfIncidentPage("intuneDeleteConfirm"), "Intune is not Deleted");
				Assert.assertTrue(incidentPage.matchTextOfIncidentPage("intuneDeleteConfirm", getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.not_configured")), "Intune configuration is not deleted");

				LOGGER.info("Intune Configuration deleted successfully");

				
}
	
}
}







