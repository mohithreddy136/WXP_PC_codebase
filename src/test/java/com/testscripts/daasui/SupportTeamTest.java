package com.testscripts.daasui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.basesource.action.PreDefinedActions;
import com.daasui.constants.CommonVariables;
import com.daasui.constants.ConstantPath;
import com.daasui.constants.DashboardVariables;
import com.daasui.constants.SupportVariables;
import com.daasui.pages.DashboardPage;
import com.daasui.pages.IncidentDetailsPage;
import com.daasui.pages.IncidentPage;
import com.daasui.pages.SupportTeamDetailsPage;
import com.daasui.pages.SupportTeamPage;
import com.daasui.pages.UserPage;

/**
 * This class implements test cases related to support team list and details page
 */
public class SupportTeamTest extends CommonTest {
	private static Logger LOGGER = LogManager.getLogger(SupportTeamTest.class);
	public int activePageNumber = 0, firstPageNumber = 0, lastPageNumber = 0, selectedOption = 0, totalCount = 0;
	public boolean previousButtonStatus = false, nextButtonStatus = false;
	ArrayList<String> supportTeamMemberInfo = new ArrayList<String>();
	public static final String supportTeamUser = getEnvironmentSpecificData(System.getProperty("environment"), "SUPPORT_TEAM_TEST_USER_EMAIL");

	@DataProvider
	public Object[][] getdifferentLoginData() {
		Object[][] data = new Object[4][4];
		data[0][0] = "IT_ADMIN_USER_US";
		data[0][1] = "IT_ADMIN_USER_PASSWORD";
		data[0][2] = "IT_ADMIN_USER_US1";
		data[0][3] = "IT_ADMIN_USER_PASSWORD1";
		data[1][0] = "SUPPORT_ADMIN_USER_EMAIL";
		data[1][1] = "SUPPORT_ADMIN_USER_PASSWORD";
		data[1][2] = "SUPPORT_ADMIN_USER_EMAIL1";
		data[1][3] = "SUPPORT_ADMIN_USER_PASSWORD1";
		data[2][0] = "SUPPORT_SPECIALIST_USER_EMAIL";
		data[2][1] = "SUPPORT_SPECIALIST_USER_PASSWORD";
		data[2][2] = "SUPPORT_SPECIALIST_USER_EMAIL1";
		data[2][3] = "SUPPORT_SPECIALIST_USER_PASSWORD1";
		data[3][0] = "PARTNER_SPECIALIST_USER_EMAIL1";
		data[3][1] = "PARTNER_SPECIALIST_USER_PASSWORD1";
		data[3][2] = "PARTNER_SPECIALIST_USER_EMAIL2";
		data[3][3] = "PARTNER_SPECIALIST_USER_PASSWORD2";

		return data;
	}

	@DataProvider
	public Object[][] getLoginData() {
		Object[][] data = new Object[4][2];
		data[0][0] = "MSP_ADMIN_US_ST_EMAIl_01";
		data[0][1] = "MSP_ADMIN_US_ST_PASSWORD_01";
		data[1][0] = "IT_ADMIN_USER_US";
		data[1][1] = "IT_ADMIN_USER_PASSWORD";
		data[2][0] = "REPORT_ADMIN_NEW_UI_EMAIL_US";
		data[2][1] = "REPORT_ADMIN_UI_PASSWORD_US";
		data[3][0] = "RESELLER_SNOW_EMAIL";
		data[3][1] = "RESELLER_SNOW_PASSWORD";

		return data;
	}

	// This test case validates title on support team page
	@Test(priority = 1, groups = { "REGRESSION", "REGRESSION_CI", "SMOKE", "ALL_CI", "ALL" }, description = "NFR 242585")
	public final void verifySupportTeamTitle() {
		try {
			login("MSP_SETTINGS_EMAIL_SA", "MSP_SETTINGS_PASSWORD_SA");
			gotoSupportTeamTab();
			LOGGER.info("Redirected to support team list page");
			SupportTeamPage supportTeamPage = new SupportTeamPage(PreDefinedActions.getDriver()).getInstance();

			if (toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, "MSP_SETTINGS_EMAIL_SA"))) {
				supportTeamPage.waitForElementsOfSupportTeamPage("supportTeamTitleOnBreadcrumbs");
				Assert.assertTrue(supportTeamPage.matchTextOfSupportTeamPage("supportTeamTitleOnBreadcrumbs", getTextLanguage(LanguageCode, "daas_ui", "pagetitle.supportTeam")), "Title on support team list page is incorrect");
				LOGGER.info("Title on support team list page is correct");
			}else{
			Assert.assertTrue(supportTeamPage.matchTextOfSupportTeamPage("supportTeamTitle", getTextLanguage(LanguageCode, "daas_ui", "pagetitle.supportTeam")), "Title on support team list page is incorrect");
			LOGGER.info("Title on support team list page is correct");
			}
		} catch (Exception e) {
			Assert.fail("Exception occured in method verifySupportTeamTitle " + e.getMessage());
		}
	}

	@Test(priority = 2, groups = { "REGRESSION", "REGRESSION_CI", "SMOKE", "ALL_CI", "ALL" }, enabled = false)
	public final void verifyPartnerTeamTitleandRoles() throws Exception {
		login("RESELLER_NEW_UI_EMAIL_US", "RESELLER_NEW_UI_PASSWORD_US");
		final String[] PARTNER_TEAM_ROLES = { "support_admin.users.sales_admin", "support_admin.users.sales_specialist" };
		SupportTeamPage supportTeamPage = new SupportTeamPage(PreDefinedActions.getDriver()).getInstance();

		gotoPartnerTeamTab();

		// Verify Partner title
		Assert.assertTrue(supportTeamPage.matchTextOfSupportTeamPage("supportTeamTitle", getTextLanguage(LanguageCode, "lhserver", "navigation.sales_team")), "Partner team title doesn't match");
		LOGGER.info("Partner team tab is matched");

		// Verify roles under Partner Team
		supportTeamPage.clickOnElementsOfSupportTeamPage("supportAddButton");
		supportTeamPage.clickOnElementsOfSupportTeamPage("partnerRolesDropdown");
		Assert.assertTrue(supportTeamPage.verifyRolesUnderPartnerTeam(LanguageCode, "partnerUserRolesList", "lhserver", PARTNER_TEAM_ROLES));
		LOGGER.info("Method verifyPartnerTeamTitleandRoles executed successfully");
	}

	/**
	 * This test case validates remove support team member functionality
	 * Disabling this test case since Remove functionality is already being tested while addition.
	 * @throws Exception
	 */
	@Test(priority = 3, groups = { "REGRESSION", "SMOKE", "REGRESSION_CI", "ALL_CI", "ALL", "PRODUCTION_SANITY","STABILIZATION_STAGING" }, description = "NFR 242585",enabled = false)
	public final void verifyRemoveFunctionality() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login("MSP_SUPPORTTEAM_EMAIL", "MSP_SUPPORTTEAM_PASSWORD");
		gotoSupportTeamTab();
		LOGGER.info("Redirected to support team list page");
		SupportTeamPage supportTeamPage = new SupportTeamPage(PreDefinedActions.getDriver()).getInstance();
		String email = generateRandomString(11) + "@mailinator.com";
		String notificationMessage = null;

		resetTableConfiguration();
		waitForPageLoaded();
		Assert.assertTrue(supportTeamPage.addSingleMember(LanguageCode, email), "Support team member is not added successfully, cannot proceed further.");
		LOGGER.info("Test case verifyRemoveFunctionality started");

		// Test Case 1 - This test case validates if the remove button is present when no support team member is selected
		softAssert.assertFalse(supportTeamPage.verifyElementsOfSupportTeamPage("supportRemoveButton"), "Remove button is present even when no checkbox is selected on support team list page");

		// Test Case 2 - This test case validates if the remove button is not present even when all support team members are selected
		supportTeamPage.clickByJavaScriptOnSupportTeamPage("selectAllCheckbox");
		softAssert.assertTrue(supportTeamPage.verifyElementsOfSupportTeamPage("supportRemoveButton"), "Remove button is not present when all checkboxes are selected on support team list page");
		supportTeamPage.clickByJavaScriptOnSupportTeamPage("selectAllCheckbox");
		supportTeamPage.waitForElementsOfSupportTeamPage("tableOverlay");

		// Test Case 3 - This test case validates if by default the checkbox for primary account login is disabled
		supportTeamPage.enterTextForSupportTeamPage("emailTextbox", getEnvironmentSpecificData(System.getProperty("environment"), "MSP_EMAIL"));
		supportTeamPage.waitForElementsOfSupportTeamPage("tableOverlay");
		supportTeamPage.mousehoverOnSupportTeamPage("firstIdLogo");
		supportTeamPage.waitForElementsOfSupportTeamPage("tableOverlay");
		softAssert.assertTrue(supportTeamPage.verifyElementsOfSupportTeamPage("firstIdCheckboxDisabled"), "Checkbox for primary account member is not disabled by default on support team list page");
		supportTeamPage.waitUntilElementIsInvisibleOfSupportTeamPage("tableOverlay");
		supportTeamPage.clearTextFromSupportTeamPageTextField("emailTextbox");
		supportTeamPage.waitUntilElementIsInvisibleOfSupportTeamPage("tableOverlay");

		// Test Case 4 - This test case validates the cancel functionality on remove support team member popup
		supportTeamPage.enterTextForSupportTeamPage("emailTextbox", email);
		supportTeamPage.waitForElementsOfSupportTeamPage("tableOverlay");
		sleeper(2000);
		supportTeamPage.mousehoverOnSupportTeamPage("firstIdLogo");
		supportTeamPage.clickByJavaScriptOnSupportTeamPage("firstIdCheckbox");
		if(supportTeamPage.verifyElementsOfSupportTeamPage("btnClose")==true) {
			supportTeamPage.clickOnElementsOfSupportTeamPage("btnClose");
		}
		supportTeamPage.clickOnElementsOfSupportTeamPage("supportRemoveButton");
		softAssert.assertTrue(supportTeamPage.getTextOfSupportTeamPage("removeTitle").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "users.remove.account")), "Title on remove support team member popup is incorrect");	
		softAssert.assertTrue(supportTeamPage.getTextOfSupportTeamPage("removeMessage").equals(getTextLanguage(LanguageCode, "daas_ui", "users.remove.singleAccount").replace("{name}", SupportVariables.COMPLETE_NAME)), "Remove member message on remove support team member popup is incorrect");
		supportTeamPage.clickOnElementsOfSupportTeamPage("cancelRemove");
		LOGGER.info("Verified cancel functionality on support team member remove popup");
		softAssert.assertFalse(supportTeamPage.verifyElementsOfSupportTeamPage("noItemsDisplayText"), "Cancel functionality on remove support team member popup is not working");

		// Test Case 5 - This test case validates the remove functionality of support team member
		supportTeamPage.waitUntilElementIsInvisibleOfSupportTeamPage("tableOverlay");
		supportTeamPage.clickOnElementsOfSupportTeamPage("supportRemoveButton");
		supportTeamPage.clickOnElementsOfSupportTeamPage("submitRemove");
		LOGGER.info("Removed the support team member");
		supportTeamPage.waitForElementsOfSupportTeamPage("toastNotification");
		notificationMessage = supportTeamPage.getTextOfSupportTeamPage("toastNotification");
		softAssert.assertTrue(supportTeamPage.matchTextOfSupportTeamPage("toastNotification", getTextLanguage(LanguageCode, "daas_ui", "global.messages.field_remove_success").replace("{field}", getTextLanguage(LanguageCode, "daas_ui", "logs.user.name"))), "Toast notification message on removing a support team member is incorrect");
		softAssert.assertTrue(supportTeamPage.verifyElementsOfSupportTeamPage("noItemsDisplayText"), "Submit functionality on remove support team member popup is not working");
		supportTeamPage.waitForElementsOfSupportTeamPage("tableOverlay");
		supportTeamPage.clearTextFromSupportTeamPageTextField("emailTextbox");
		supportTeamPage.waitForElementsOfSupportTeamPage("tableOverlay");

		// Test Case 6 - This test case validates if remove popup appears on clicking the remove option from hamburger menu
		Assert.assertTrue(supportTeamPage.addSingleMember(LanguageCode, email), "Support team member is not added successfully, cannot proceed further to validate hamburger menu");
		LOGGER.info("Added new support team member");
		supportTeamPage.enterTextForSupportTeamPage("emailTextbox", email);
		supportTeamPage.waitForElementsOfSupportTeamPage("tableOverlay");
		supportTeamPage.mousehoverOnSupportTeamPage("firstIdLogo");
		supportTeamPage.clickOnElementsOfSupportTeamPage("hamburgerIcon");
		supportTeamPage.clickOnElementsOfSupportTeamPage("removeAction");
		softAssert.assertTrue(supportTeamPage.verifyElementsOfSupportTeamPage("removeTitle"), "Remove support team member does not appear after clicking on remove from hamburger icon");
		supportTeamPage.clickOnElementsOfSupportTeamPage("submitRemove");
		LOGGER.info("Removed the support team member");
		supportTeamPage.waitForElementsOfSupportTeamPage("toastNotification");
		notificationMessage = supportTeamPage.getTextOfSupportTeamPage("toastNotification");
		softAssert.assertTrue(notificationMessage.equals(getTextLanguage(LanguageCode, "daas_ui", "global.messages.field_remove_success").replace("{field}", getTextLanguage(LanguageCode, "daas_ui", "logs.user.name"))), "Toast notification message on removing a support team member is incorrect");
		supportTeamPage.enterTextForSupportTeamPage("emailTextbox", email);
		softAssert.assertTrue(supportTeamPage.verifyElementsOfSupportTeamPage("noItemsDisplayText"), "Remove functionality after clicking on hamburger icon on remove support team member popup is not working");

		softAssert.assertAll();
		LOGGER.info("All test cases of remove functionality for support team page have passed");
	}

	// This test case validates add support team member functionality
	@Test(priority = 4, groups = { "REGRESSION", "REGRESSION_CI", "SMOKE", "ALL_CI", "ALL","STABILIZATION_STAGING" }, description = "NFR 242585")
	public final void verifyAddFunctionality() throws Exception {
		login("MSP_SETTINGS_EMAIL_SA", "MSP_SETTINGS_PASSWORD_SA");
		SupportTeamPage supportTeamPage = new SupportTeamPage(PreDefinedActions.getDriver()).getInstance();
		String email = generateRandomString(11) + "@mailinator.com";
		try {
			SoftAssert softAssert = new SoftAssert();
			gotoSupportTeamTab();
			LOGGER.info("Redirected to support team list page");
			String notificationMessage = null;

			waitForPageLoaded();
			resetTableConfiguration();
			supportTeamPage.waitForElementsOfSupportTeamPage("tableOverlay");

			// Test Case 1 - This test case validates cancel functionality on add support team member popup
			supportTeamPage.clickByJavaScriptOnSupportTeamPage("supportAddButton");
			//supportTeamPage.waitForElementsOfSupportTeamPage("supportAdminAddMemberLabel");
			softAssert.assertTrue(supportTeamPage.matchTextOfSupportTeamPage("addMemberTitle", getTextLanguage(LanguageCode, "daas_ui", "support_team.list.add_member")), "Title on add support team member popup is incorrect");
			softAssert.assertTrue(supportTeamPage.matchTextOfSupportTeamPage("roleTitle", getTextLanguage(LanguageCode, "daas_ui", "users.details.role")), "Role field title didn't matched on add support team member popup");
			//softAssert.assertTrue(supportTeamPage.verifyElementIsEnableOfSupportTeamPage("roleDropDown"), "Role dropdown is not enabled");

			softAssert.assertTrue(supportTeamPage.matchTextOfSupportTeamPage("firstNameAddMemberLabel", getTextLanguage(LanguageCode, "daas_ui", "support_team.details.first_name")), "First name field is not present on add support team member popup");
			supportTeamPage.enterTextForSupportTeamPage("firstNameAddMember", SupportVariables.FIRST_NAME);

			softAssert.assertTrue(supportTeamPage.matchTextOfSupportTeamPage("lastNameAddMemberLabel", getTextLanguage(LanguageCode, "daas_ui", "support_team.details.last_name")), "Last name field is not present on add support team member popup");
			supportTeamPage.enterTextForSupportTeamPage("lastNameAddMember", SupportVariables.LAST_NAME);

			softAssert.assertTrue(supportTeamPage.matchTextOfSupportTeamPage("emailAddMemberLabel", getTextLanguage(LanguageCode, "daas_ui", "support_team.details.email")), "Email field is not present on add support team member popup");
			supportTeamPage.enterTextForSupportTeamPage("emailAddMember", email);

			softAssert.assertTrue(supportTeamPage.matchTextOfSupportTeamPage("mobileAddMemberLabel", getTextLanguage(LanguageCode, "daas_ui", "support_team.details.phone_mobile")), "Mobile phone number field is not present on add support team member popup");
			supportTeamPage.enterTextForSupportTeamPage("mobileAddMember", SupportVariables.MOBILE_PHONE);
			LOGGER.info("Filled all the form fields");

			supportTeamPage.clickOnElementsOfSupportTeamPage("cancelAddMember");
			LOGGER.info("Verified cancel functionality on add support team member popup");
			//softAssert.assertFalse(supportTeamPage.verifyElementsOfSupportTeamPage("toastNotification"), "Cancel functionality on add support team member popup does not work correctly");

			// Test Case 2 - This test case validates the add functionality of support team members
			supportTeamPage.clickByJavaScriptOnSupportTeamPage("supportAddButton");
			supportTeamPage.enterTextForSupportTeamPage("firstNameAddMember", SupportVariables.FIRST_NAME);
			supportTeamPage.enterTextForSupportTeamPage("lastNameAddMember", SupportVariables.LAST_NAME);
			supportTeamPage.enterTextForSupportTeamPage("emailAddMember", email);
			supportTeamPage.enterTextForSupportTeamPage("mobileAddMember", SupportVariables.MOBILE_PHONE);
			supportTeamPage.clickByJavaScriptOnSupportTeamPage("addMember");
			supportTeamPage.waitForElementsOfSupportTeamPage("toastNotification");
			notificationMessage = supportTeamPage.getTextOfSupportTeamPage("toastNotification");
			softAssert.assertTrue(notificationMessage.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "user.created.success")), "Incorrect notification message is generated after adding support team member");
			waitForPageLoaded();
			supportTeamPage.enterTextForSupportTeamPage("emailTextbox", email);
			supportTeamPage.waitForElementsOfSupportTeamPage("tableOverlay");
			sleeper(5000);
			//softAssert.assertFalse(supportTeamPage.verifyElementsOfSupportTeamPage("noItemsDisplayText"), "Add functionality for support team member does not work correctly");
			Assert.assertTrue(supportTeamPage.getTextOfSupportTeamPage("emailList").equalsIgnoreCase(email),"Add functionality for support team member is not working correctly");
			LOGGER.info("Verified add support team member functionality");

			softAssert.assertAll();
			LOGGER.info("All test cases of add functionality for support team page have passed");
		} catch (Exception e) {
			Assert.fail("Exception occured in method verifyAddFunctionality " + e.getMessage());
		} finally {
			supportTeamPage.removeSingleMember(email, LanguageCode);
		}
	}

	// This test case validates all filters' functionality on support team list page
	@Test(priority = 5, groups = { "REGRESSION", "REGRESSION_CI", "SMOKE", "ALL_CI", "ALL" }, description = "NFR 242585", enabled = false)
	public final void verifyFilterFunctionality() {
		try {
			SoftAssert softAssert = new SoftAssert();
			login("MSP_SUPPORTTEAM_EMAIL", "MSP_SUPPORTTEAM_PASSWORD");
			gotoSupportTeamTab();
			SupportTeamPage supportTeamPage = new SupportTeamPage(PreDefinedActions.getDriver()).getInstance();

			LOGGER.info("Redirected to support team list page");
			resetTableConfiguration();
			supportTeamPage.waitForElementsOfSupportTeamPage("tableOverlay");

			// Test Case 1 - This test case validates if the filter functionality is working properly for the searchbox on name column
			softAssert.assertTrue(supportTeamPage.verifySearchValueOnSupportTeam(LanguageCode, "nameTextbox", SupportVariables.INCORRECT_SEARCH_STRING, "noItemsDisplayText", "nameList"), "Search functionality with incorrect search string is not working for name column on support team list page");
			softAssert.assertTrue(supportTeamPage.verifySearchValueOnSupportTeam(LanguageCode, "nameTextbox", SupportVariables.NAME, "noItemsDisplayText", "nameList"), "Search functionality is not working for name column on support team list page");

			/*
			 * // Test Case 2 - This test case validates if the filter functionality is working properly for the searchbox on email column
			 * softAssert.assertTrue(supportTeamPage.verifySearchValueOnSupportTeam(LanguageCode, "emailTextbox", SupportVariables.INCORRECT_SEARCH_STRING, "noItemsDisplayText", "emailList"),
			 * "Incorrect search not working for Email column on support team list page"); softAssert.assertTrue(supportTeamPage.verifySearchValueOnSupportTeam(LanguageCode, "emailTextbox",
			 * SupportVariables.EMAIL_SEARCH, "noItemsDisplayText", "emailList"), "Search not working for Email column on support team list page");
			 */

			// Test Case 3 - This test case validates if the filter functionality is working properly for the dropdown on role column
			supportTeamPage.clickOnElementsOfSupportTeamPage("rolesFilter");
			softAssert.assertTrue(supportTeamPage.verifyFilterSingleSelect(LanguageCode, "dropdowncheckboxes", "dropdownelementlistlabels", "roleList", "noItemsDisplayText"), "Listbox not working for roles on support team list page");
			supportTeamPage.clickOnElementsOfSupportTeamPage("clearFilter");
			supportTeamPage.waitForElementsOfSupportTeamPage("tableOverlay");

			/*
			 * // Test Case 4 - This test case validates if the filter functionality is working properly for the dropdown on status column
			 * supportTeamPage.clickOnElementsOfSupportTeamPage("statusFilter"); softAssert.assertTrue(supportTeamPage.verifyFilterSingleSelect(LanguageCode, "dropdowncheckboxes",
			 * "dropdownelementlistlabels", "statusList", "noItemsDisplayText"), "Listbox not working for roles on support team list page");
			 * supportTeamPage.clickOnElementsOfSupportTeamPage("clearFilter"); supportTeamPage.waitForElementsOfSupportTeamPage("tableOverlay");
			 */

			// Test Case 5 - This test case validates if the filter functionality is working properly for the searchbox on mobile phone column
			softAssert.assertTrue(supportTeamPage.verifySearchValueOnSupportTeam(LanguageCode, "mobilePhoneTextbox", SupportVariables.INCORRECT_SEARCH_STRING, "noItemsDisplayText", "mobilePhoneList"), "Search functionality with incorrect search string is not working for mobile phone column on support team list page");
			softAssert.assertTrue(supportTeamPage.verifySearchValueOnSupportTeam(LanguageCode, "mobilePhoneTextbox", SupportVariables.MOBILE_PHONE_SEARCH, "noItemsDisplayText", "mobilePhoneList"), "Search functionality is not working for mobile phone column on support team list page");

			/*
			 * // Test Case 6 - This test case validates if the filter functionality is working properly for the searchbox on office phone column
			 * softAssert.assertTrue(supportTeamPage.verifySearchValueOnSupportTeam(LanguageCode, "officePhoneTextbox", SupportVariables.INCORRECT_SEARCH_STRING, "noItemsDisplayText",
			 * "officePhoneList"), "Search functionality with incorrect search string is not working for office phone number column on support team list page");
			 * softAssert.assertTrue(supportTeamPage.verifySearchValueOnSupportTeam(LanguageCode, "officePhoneTextbox", SupportVariables.OFFICE_PHONE_SEARCH, "noItemsDisplayText",
			 * "officePhoneList"), "Search functionality is not working for office phone number column on support team list page");
			 */

			softAssert.assertAll();
			LOGGER.info("All test cases of filter functionality for support team page have passed");
		} catch (Exception e) {
			Assert.fail("Exception occured in method verifyFilterFunctionality " + e.getMessage());
		}
	}

	// This test case validates pagination functionality on support team list page
	@Test(priority = 6, groups = { "REGRESSION", "REGRESSION_CI", "SMOKE", "ALL_CI", "ALL" }, description = "NFR 242585", enabled = false)
	public final void verifyPaginationOnSupportTeamPage() {
		try {
			SoftAssert softAssert = new SoftAssert();
			login("MSP_SUPPORTTEAM_EMAIL", "MSP_SUPPORTTEAM_PASSWORD");
			gotoSupportTeamTab();
			LOGGER.info("Redirected to support team list page");
			SupportTeamPage supportTeamPage = new SupportTeamPage(PreDefinedActions.getDriver()).getInstance();

			supportTeamPage.waitUntilElementIsInvisibleOfSupportTeamPage("tableOverlay");
			softAssert.assertTrue(supportTeamPage.verifyElementIsEnableOfSupportTeamPage("paginationDropdownMenu"), "Pagination Dropdown not availble");
			softAssert.assertTrue(supportTeamPage.verifyElementIsClickableOfSupportTeamPage("paginationDropdownMenu"), "Pagination Dropdown not clickable");
			softAssert.assertTrue(supportTeamPage.waitForElementsOfSupportTeamPage("navigationItemDiv"), "Navigation Item are not available");
			getPaginationInfo();
			LOGGER.info("get Pagination Information ");
			softAssert.assertTrue(supportTeamPage.verifyElementIsEnableOfSupportTeamPage("navigationItemActivePage"), "The active page navigation link is not disabled");
			softAssert.assertTrue(supportTeamPage.verifyTotalPageCountChangeAsSelectedOption(selectedOption, totalCount, lastPageNumber), "Total page count not change with selected option");
			if (!verifySelectedOptionForNewSelection(selectedOption, CommonVariables.SELETEDFIFTY)) {
				if (changeSelectedOption(totalCount, CommonVariables.SELETEDTWENTYFIVE)) {
					supportTeamPage.selectElementFromDropDownofSupportTeamPage("paginationDropdownMenu", "paginationOptionList", Integer.toString(CommonVariables.SELETEDTWENTYFIVE));
					LOGGER.info("Change Selected option as " + CommonVariables.SELETEDTWENTYFIVE);
					supportTeamPage.waitForElementsOfSupportTeamPage("tableOverlay");
					getPaginationInfo();
					softAssert.assertTrue(supportTeamPage.verifyTotalPageCountChangeAsSelectedOption(selectedOption, totalCount, lastPageNumber), "Total page count not change with selected option");
					softAssert.assertTrue(supportTeamPage.verifyButtonStatusofPagination(activePageNumber, firstPageNumber, lastPageNumber, totalCount, previousButtonStatus, nextButtonStatus), "Button visibility status not as per expectation");
				} else {

					LOGGER.info("Selected user has less than " + CommonVariables.SELETEDTWENTYFIVE + " records");
				}
			} else {
				if (changeSelectedOption(totalCount, CommonVariables.SELETEDFIFTY)) {
					supportTeamPage.selectElementFromDropDownofSupportTeamPage("paginationDropdownMenu", "paginationOptionList", Integer.toString(CommonVariables.SELETEDFIFTY));
					LOGGER.info("Change Selected option as " + CommonVariables.SELETEDFIFTY);
					supportTeamPage.waitForElementsOfSupportTeamPage("tableOverlay");
					getPaginationInfo();
					softAssert.assertTrue(supportTeamPage.verifyTotalPageCountChangeAsSelectedOption(selectedOption, totalCount, lastPageNumber), "Total page count not change with selected option");
					softAssert.assertTrue(supportTeamPage.verifyButtonStatusofPagination(activePageNumber, firstPageNumber, lastPageNumber, totalCount, previousButtonStatus, nextButtonStatus), "Button visibility status not as per expectation");
				} else {

					LOGGER.info("Selected user has less than " + CommonVariables.SELETEDFIFTY + " records");
				}
			}
			supportTeamPage.waitForElementsOfSupportTeamPage("tableOverlay");
			if (supportTeamPage.canClickNext(activePageNumber, lastPageNumber, nextButtonStatus)) {
				softAssert.assertTrue(supportTeamPage.getButtonEnabilityStatus("navigationItemNext"), "Naviagtion next button is not enable");
				supportTeamPage.waitForElementsOfSupportTeamPage("navigationItemNext");
				supportTeamPage.clickOnElementsOfSupportTeamPage("navigationItemNext");
				supportTeamPage.waitForElementsOfSupportTeamPage("tableOverlay");
				getPaginationInfo();
				supportTeamPage.waitForElementsOfSupportTeamPage("navigationItemPrevious");
				if (supportTeamPage.canClickPrevious(activePageNumber, firstPageNumber, previousButtonStatus)) {
					softAssert.assertTrue(supportTeamPage.getButtonEnabilityStatus("navigationItemPrevious"), "Naviagtion previous button is not enable");
					supportTeamPage.clickOnElementsOfSupportTeamPage("navigationItemPrevious");
				} else {
					LOGGER.info("Previous button is disabled");
				}
			} else if (supportTeamPage.canClickPrevious(activePageNumber, firstPageNumber, previousButtonStatus)) {
				softAssert.assertTrue(supportTeamPage.getButtonEnabilityStatus("navigationItemPrevious"), "Naviagtion previous button is not enable");
				supportTeamPage.waitForElementsOfSupportTeamPage("navigationItemPrevious");
				supportTeamPage.clickOnElementsOfSupportTeamPage("navigationItemPrevious");
				supportTeamPage.waitForElementsOfSupportTeamPage("tableOverlay");
				getPaginationInfo();
				supportTeamPage.waitForElementsOfSupportTeamPage("navigationItemNext");
				if (supportTeamPage.canClickNext(activePageNumber, lastPageNumber, nextButtonStatus)) {
					softAssert.assertTrue(supportTeamPage.getButtonEnabilityStatus("navigationItemNext"), "Naviagtion next button is not enable");
					supportTeamPage.clickOnElementsOfSupportTeamPage("navigationItemNext");
				} else {
					LOGGER.info("Next button is disabled");
				}
			} else {
				LOGGER.info("Previous and Next button both are disabled");
			}
			softAssert.assertAll();
			LOGGER.info("Verification of support team page pagination on support team list page done successfully ");
		} catch (Exception e) {
			Assert.fail("Exception occured in method verifyPaginationOnSupportTeamPage " + e.getMessage());
		}
	}

	// This test case validates details tile present on support team details page
	@Test(priority = 7, groups = { "REGRESSION", "REGRESSION_CI", "SMOKE", "ALL_CI", "ALL" }, description = "NFR 242589")
	public final void verifyDetailsTile() {
		try {
			SoftAssert softAssert = new SoftAssert();
			login("MSP_SETTINGS_EMAIL_SA", "MSP_SETTINGS_PASSWORD_SA");
			gotoSupportTeamTab();
			LOGGER.info("Redirected to support team details page");
			SupportTeamPage supportTeamPage = new SupportTeamPage(PreDefinedActions.getDriver()).getInstance();
			SupportTeamDetailsPage supportTeamDetailsPage = new SupportTeamDetailsPage(PreDefinedActions.getDriver()).getInstance();
			resetTableConfiguration();
			waitForPageLoaded();
			supportTeamPage.waitForElementsOfSupportTeamPage("tableOverlay");
			supportTeamPage.enterTextForSupportTeamPage("emailTextbox", supportTeamUser);
			supportTeamPage.waitForElementsOfSupportTeamPage("tableOverlay");
			supportTeamPage.scrollOnSupportTeamPage("txtMobilePhone");
			supportTeamMemberInfo = supportTeamPage.getSupportTeamMemberInfo();
			supportTeamPage.clickByJavaScriptOnSupportTeamPage("firstIdName");

			supportTeamDetailsPage.verifyElementsOfSupportTeamDetailsPage("detailsTitle");

			// Test Case 1 - This test case validates if the name of member on list and details page is the same
			softAssert.assertTrue(supportTeamDetailsPage.getTextOfSupportTeamDetailsPage("nameLabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "support_team.details.name")), "Name field for support team member is not present on details tile");
				softAssert.assertTrue(supportTeamDetailsPage.getTextOfSupportTeamDetailsPage("nameValue").equalsIgnoreCase(supportTeamMemberInfo.get(1)), "Name of support team member on details page does not match with list page");
	
			// Test Case 2 - This test case validates if the email of member on list and details page is the same
			softAssert.assertTrue(supportTeamDetailsPage.getTextOfSupportTeamDetailsPage("emailLabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "users.details.email")), "Email field for support team member is not present on details tile");
				softAssert.assertTrue(supportTeamDetailsPage.getTextOfSupportTeamDetailsPage("emailValue").equalsIgnoreCase(supportTeamMemberInfo.get(0)), "Email of support team member on details page does not match with list page");
			
			// Test Case 3 - This test case validates if the Mobile phone of member on list and details page is the same
			softAssert.assertTrue(supportTeamDetailsPage.getTextOfSupportTeamDetailsPage("mobileLabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "users.details.mobile_phone")), "Mobile phone number field for support team member is not present on details tile");
				softAssert.assertTrue(supportTeamDetailsPage.getTextOfSupportTeamDetailsPage("mobileValue").equalsIgnoreCase(supportTeamMemberInfo.get(4)), "Mobile phone of support team member on details page does not match with list page");	
			
			// Test Case 4 - This test case validates if the Office phone of member on list and details page is the same
			softAssert.assertTrue(supportTeamDetailsPage.getTextOfSupportTeamDetailsPage("officeLabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "users.details.office_phone")), "Office phone number field for support team member is not present on details tile");
			if(!supportTeamDetailsPage.getTextOfSupportTeamDetailsPage("officeValue").equals("-")) {
					softAssert.assertTrue(supportTeamDetailsPage.getTextOfSupportTeamDetailsPage("officeValue").equalsIgnoreCase(supportTeamMemberInfo.get(5)), "Office phone of support team member on details page does not match with list page");	
					}
			softAssert.assertAll();
			LOGGER.info("Verification of support team member details on details tile of support team details page done successfully");
		} catch (Exception e) {
			Assert.fail("Exception occured in method verifyDetailsTile " + e.getMessage());
		}
	}

	// This test case validates Accounts tile present on support team details page
	@Test(priority = 8, groups = { "REGRESSION", "REGRESSION_CI" }, description = "NFR 242589 , 522924 , TestCase=528536")
	public final void verifyAccountsTile() {
		try {
			login("MSP_SETTINGS_EMAIL_SA", "MSP_SETTINGS_PASSWORD_SA");
			SoftAssert softAssert = new SoftAssert();
			gotoSupportTeamTab();
			LOGGER.info("Redirected to support team list page");
			SupportTeamPage supportTeamPage = new SupportTeamPage(PreDefinedActions.getDriver()).getInstance();
			SupportTeamDetailsPage supportTeamDetailsPage = new SupportTeamDetailsPage(PreDefinedActions.getDriver()).getInstance();
			String selectedValue = null;
			resetTableConfiguration();
			waitForPageLoaded();
			supportTeamPage.waitForElementsOfSupportTeamPage("tableOverlay");
			supportTeamPage.enterTextForSupportTeamPage("emailTextbox", supportTeamUser);
			supportTeamPage.waitForElementsOfSupportTeamPage("tableOverlay");
			sleeper(1000);
			supportTeamPage.clickByJavaScriptOnSupportTeamPage("firstIdName");
			supportTeamPage.waitForElementsOfSupportTeamPage("tableOverlay");

			// Test Case 1 - This test case validates Accounts Tile and Cancel functionality
			softAssert.assertTrue(supportTeamDetailsPage.getTextOfSupportTeamDetailsPage("roleHeader").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "users.details.accounts")), "Title on Accounts tile is incorrect");
			softAssert.assertTrue(supportTeamDetailsPage.matchTextOfSupportTeamDetailsPage(("roleMessage"), getTextLanguage(LanguageCode, "daas_ui", "users.details.section.role_assignment_description")), "Description on Account tile is incorrect");
			supportTeamDetailsPage.verifyElementsOfSupportTeamDetailsPage("roleLabel");
			supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("roleButton");
			softAssert.assertTrue(supportTeamDetailsPage.matchTextOfSupportTeamDetailsPage("rolePopupHeader", getTextLanguage(LanguageCode, "daas_ui", "users.details.roles.modal.title")), "Header of Edit Role popup is incorrect");
			supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("rolesCancel");
			LOGGER.info("Verified cancel functionality on Accounts tile of support team details page");
			sleeper(3000);//Added sleeper as role list takes time in loading
			
			// Test Case 2 - This test case validates Save functionality on role assignemnt popup
			supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("roleButton");
			sleeper(1000);
			supportTeamDetailsPage.selectMultipleRolesOnSupportTeamDetailsPage();
			LOGGER.info("Multiple roles selected");
			supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("rolesSave");
			LOGGER.info("Verified role assignment functionality on support team details page");
			softAssert.assertTrue(supportTeamDetailsPage.matchTextOfSupportTeamDetailsPage("toastNotificationAccountTile", getTextLanguage(LanguageCode, "daas_ui", "user.update.success").replace("{section}", "User's").replace("{field}", "roles")), "Save functionality on Edit Role popup is not working");
			sleeper(2000);
			selectedValue=supportTeamDetailsPage.getElementOfSupportTeamDetailsPage("roleValue").getText().replaceAll("/", ",");
			
			// Test Case 3 - This test case validates if the updated role is reflected back on support team list page
			navigateToBack();
			LOGGER.info("Navigate back to support team list page");
			waitForPageLoaded();
			supportTeamPage.waitForElementsOfSupportTeamPage("tableOverlay");
			softAssert.assertTrue(supportTeamPage.getTextOfSupportTeamPage("firstIdRole").equalsIgnoreCase(selectedValue), "Updated role is not reflected back on support team list page");

			softAssert.assertAll();
			LOGGER.info("Verification of Accounts tile on support team details page done successfully");
			
		} catch (Exception e) {
			Assert.fail("Exception occured in method verifyAccountsTile " + e.getMessage());
		}
	}

	// This test case validates identity tile present on support team details page
	@Test(priority = 9, groups = { "REGRESSION", "REGRESSION_CI", "SMOKE", "ALL_CI", "ALL" }, description = "NFR 242589",enabled=false)
	public final void verifyIdentityTile() {
		try {
			SoftAssert softAssert = new SoftAssert();
			login("MSP_SUPPORTTEAM_EMAIL", "MSP_SUPPORTTEAM_PASSWORD");
			gotoSupportTeamTab();
			LOGGER.info("Redirected to support team list page");
			SupportTeamPage supportTeamPage = new SupportTeamPage(PreDefinedActions.getDriver()).getInstance();
			SupportTeamDetailsPage supportTeamDetailsPage = new SupportTeamDetailsPage(PreDefinedActions.getDriver()).getInstance();
			resetTableConfiguration();
			waitForPageLoaded();
			supportTeamPage.waitForElementsOfSupportTeamPage("tableOverlay");
			supportTeamPage.enterTextForSupportTeamPage("emailTextbox", SupportVariables.SUPPORT_TEAM_TEST_USER_EMAIL);
			supportTeamPage.waitForElementsOfSupportTeamPage("tableOverlay");
			supportTeamPage.scrollOnSupportTeamPage("txtMobilePhone");
			supportTeamMemberInfo = supportTeamPage.getSupportTeamMemberInfo();
			supportTeamPage.clickByJavaScriptOnSupportTeamPage("firstIdName");
			LOGGER.info("Redirected to support team details page");
			supportTeamPage.waitForElementsOfSupportTeamPage("tableOverlay");

			// Test case 1 - This test case validates support team member details on identity tile
				softAssert.assertTrue(supportTeamDetailsPage.getTextOfSupportTeamDetailsPage("identityName").equalsIgnoreCase(supportTeamMemberInfo.get(0)), "Name on identity tile is incorrect");	
				
			softAssert.assertTrue(supportTeamDetailsPage.getTextOfSupportTeamDetailsPage("identityEmail").equalsIgnoreCase(supportTeamMemberInfo.get(1)), "Email on identity tile is incorrect");

			// verify the phone number only if not null
			if (!supportTeamDetailsPage.getElementOfSupportTeamDetailsPage("identityPhone").getText().equals("")) {
					softAssert.assertTrue(supportTeamDetailsPage.getTextOfSupportTeamDetailsPage("identityPhone").equalsIgnoreCase(supportTeamMemberInfo.get(5)) || supportTeamDetailsPage.getTextOfSupportTeamDetailsPage("identityPhone").equalsIgnoreCase(supportTeamMemberInfo.get(4)), "Phone on identity tile is incorrect");
					}
				softAssert.assertTrue(supportTeamDetailsPage.getTextOfSupportTeamDetailsPage("identityStatus").equalsIgnoreCase(supportTeamMemberInfo.get(3)), "Status on identity tile is incorrect");
			
			softAssert.assertAll();
			LOGGER.info("Verification of identity tile on support team details page done successfully");
		} catch (Exception e) {
			Assert.fail("Exception occured in method verifyIdentityTile " + e.getMessage());
		}
	}

	// This test case validates communication preferences tile present on edit user's profile page
	@Test(priority = 10, groups = { "REGRESSION", "REGRESSION_CI", "SMOKE", "ALL_CI", "ALL","STABILIZATION_STAGING" }, description = "NFR 242589",enabled=false)
	public final void verifyCommunicationPreferencesTile() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		login("MSP_SUPPORTTEAM_EMAIL", "MSP_SUPPORTTEAM_PASSWORD");
		gotoSupportTeamTab();
		LOGGER.info("Redirected to support team list page");
		waitForPageLoaded();
		SupportTeamPage supportTeamPage = new SupportTeamPage(PreDefinedActions.getDriver()).getInstance();

		SupportTeamDetailsPage supportTeamDetailsPage = new SupportTeamDetailsPage(PreDefinedActions.getDriver()).getInstance();

		ArrayList<String> accountSubTypesOnIncidentNotificationPreferencesPopup = new ArrayList<String>(
				Arrays.asList(getTextLanguage(LanguageCode, "daas_ui", "global.all"), getTextLanguage(LanguageCode, "daas_ui", "download.techPulse_inc"), getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.subtype.licensing"), getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.type.unassigned")));

		ArrayList<String> hardwareChangeSubTypesOnIncidentNotificationPreferencesPopup = new ArrayList<String>(
				Arrays.asList(getTextLanguage(LanguageCode, "daas_ui", "global.all"), getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.subtype.hdd"), getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.subtype.memory"), getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.type.unassigned")));

		ArrayList<String> hardwareHealthSubTypesOnIncidentNotificationPreferencesPopup = new ArrayList<String>(Arrays.asList(getTextLanguage(LanguageCode, "daas_ui", "global.all"), getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.subtype.batterydetected"),
				getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.subtype.batterypredictive1"),
				getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.subtype.biosoutofdate"),
				getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.subtype.camerastatus"),
				getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.subtype.centerroomcontrolstatus").replaceAll("of Room Control status", "Room Control Status"),
				getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.subtype.conferencespeakerstatus"),
				getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.subtype.defaultspeakerstatus"),
				getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.subtype.hddpredictive"),
				getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.subtype.hddevent"), getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.subtype.hddstore"),
				getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.subtype.systemthermal"), getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.type.unassigned")));

		ArrayList<String> osHealthSubTypesOnIncidentNotificationPreferencesPopup = new ArrayList<String>(Arrays.asList(getTextLanguage(LanguageCode, "daas_ui", "global.all"), getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.subtype.companybsod"),
				getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.subtype.cpuhigh"), getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.subtype.memoryhigh"), getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.subtype.osbsod"), getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.subtype.oscrash"), getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.type.unassigned")));

		ArrayList<String> securitySubTypesOnIncidentNotificationPreferencesPopup = new ArrayList<String>(Arrays.asList(getTextLanguage(LanguageCode, "daas_ui", "global.all"),
				getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.subtype.antivirus"),
				getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.subtype.firewall"),
				getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.subtype.heartbeat"),
				getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.subtype.bromium2"),
				getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.subtype.truepositive1"),				
				getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.subtype.bromium"),
				getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.subtype.suresenseproClientdisabled"),
				getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.subtype.suresenseprothreatprevented"),
				getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.subtype.truepositive"),
				getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.subtype.suresensedisabled"),
				getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.subtype.suresensethreatprevented"),
				getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.subtype.truepositive").replaceAll("Threat Prevented", "True positive threat detected"),
				getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.subtype.bromium1"),
				getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.subtype.suresensethreatprevented").replaceAll("-", "Advanced -"),
				getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.subtype.suresensedisabled").replaceAll("- Client Disabled", "Advanced disabled"),
				getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.type.unassigned")));

		ArrayList<String> softwareHealthSubTypesOnIncidentNotificationPreferencesPopup = new ArrayList<String>(Arrays.asList(getTextLanguage(LanguageCode, "daas_ui", "global.all"), getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.subtype.driveroutofdate"), getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.subtype.requiredapps"), getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.type.unassigned")));

		ArrayList<String> unassignedSubTypesOnIncidentNotificationPreferencesPopup = new ArrayList<String>(Arrays.asList(getTextLanguage(LanguageCode, "daas_ui", "global.all"), getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.type.unassigned")));

		String selectedAllIncidentTypes = getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.type.account") + " (" + getTextLanguage(LanguageCode, "daas_ui", "global.all") + "), " + getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.type.hwchange") + " ("
				+ getTextLanguage(LanguageCode, "daas_ui", "global.all") + "), " + getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.type.hardwarehealth") + " (" + getTextLanguage(LanguageCode, "daas_ui", "global.all") + "), " + getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.type.oshealth") + " (" + getTextLanguage(LanguageCode, "daas_ui", "global.all") + "), "
				+ getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.type.security") + " (" + getTextLanguage(LanguageCode, "daas_ui", "global.all") + "), " + getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.type.softwarehealth") + " ("
				+ getTextLanguage(LanguageCode, "daas_ui", "global.all") + "), " + getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.type.unassigned") + " (" + getTextLanguage(LanguageCode, "daas_ui", "global.all") + ")";

		String selectedSpecificIncidentTypes = getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.type.account") + " (" + getTextLanguage(LanguageCode, "lhserver", "settings.preferences.incident_config.label.custom") + "), "
				+ getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.type.hwchange") + " (" + getTextLanguage(LanguageCode, "lhserver", "settings.preferences.incident_config.label.custom") + "), " + getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.type.hardwarehealth") + " (" + getTextLanguage(LanguageCode, "lhserver", "settings.preferences.incident_config.label.custom") + "), " + getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.type.oshealth")
				+ " (" + getTextLanguage(LanguageCode, "lhserver", "settings.preferences.incident_config.label.custom") + "), " + getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.type.security") + " ("
				+ getTextLanguage(LanguageCode, "lhserver", "settings.preferences.incident_config.label.custom") + "), " + getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.type.softwarehealth") + " (" + getTextLanguage(LanguageCode, "lhserver", "settings.preferences.incident_config.label.custom") + "), " + getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.type.unassigned") + " (" + getTextLanguage(LanguageCode, "daas_ui", "global.all") + ")", timeZone = null,
				timeZoneCode = null;

		LOGGER.info("Test case verifyCommunicationPreferencesTile started");
		resetTableConfiguration();
		supportTeamPage.waitForElementsOfSupportTeamPage("tableOverlay");
		supportTeamPage.enterTextForSupportTeamPage("emailTextbox", getCredentials(environment, "MSP_SUPPORTTEAM_EMAIL"));
		supportTeamPage.waitForElementsOfSupportTeamPage("tableOverlay");
		supportTeamPage.clickOnElementsOfSupportTeamPage("firstIdName");
		supportTeamPage.waitForElementsOfSupportTeamPage("tableOverlay");
		supportTeamPage.waitUntilElementIsInvisibleOfSupportTeamPage("tableOverlay");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("communicationPreferencesTab");

		supportTeamPage.waitForElementsOfSupportTeamPage("tableOverlay");
		LOGGER.info("Redirected to logged in user's details page");
		softAssert.assertTrue(supportTeamDetailsPage.getTextOfSupportTeamDetailsPage("communicationPreferencesHeader").equals(getTextLanguage(LanguageCode, "daas_ui", "user.details.system.communications").toUpperCase()), "Communication preferences tile is not present on logged in user's details page");
		softAssert.assertTrue(supportTeamDetailsPage.matchTextOfSupportTeamDetailsPage("deviceHealthNotificationTitle", getTextLanguage(LanguageCode, "daas_ui", "users.details.device_health_notify")), "Device health notification label is incorrect on logged in user's details page");
		if (supportTeamDetailsPage.getAttributesOfSupportTeamDetailsPage("deviceHealthNotificationToggle", "aria-checked").equals("true")) {
			softAssert.assertTrue(supportTeamDetailsPage.matchTextOfSupportTeamDetailsPage("deviceHealthNotificationValue", getTextLanguage(LanguageCode, "daas_ui", "global.enabled")), "Device health notification status is disabled even when toggle is 'on' on logged in user's details page");
			supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("deviceHealthNotificationToggle");
			LOGGER.info("Switched device health notification toggle off");
			softAssert.assertTrue(supportTeamDetailsPage.matchTextOfSupportTeamDetailsPage("deviceHealthNotificationValue", getTextLanguage(LanguageCode, "daas_ui", "global.disabled")), "Device health notification toggle is not updated successfully on logged in user's details page");
		} else {
			softAssert.assertTrue(supportTeamDetailsPage.matchTextOfSupportTeamDetailsPage("deviceHealthNotificationValue", getTextLanguage(LanguageCode, "daas_ui", "global.disabled")), "Device health notification status is enabled even when toggle is 'off' on logged in user's details page");
			supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("deviceHealthNotificationToggle");
			LOGGER.info("Switched device health notification toggle on");
			softAssert.assertTrue(supportTeamDetailsPage.matchTextOfSupportTeamDetailsPage("deviceHealthNotificationValue", getTextLanguage(LanguageCode, "daas_ui", "global.enabled")), "Device health notification toggle is not updated successfully on logged in user's details page");
		}

		softAssert.assertTrue(supportTeamDetailsPage.matchTextOfSupportTeamDetailsPage("incidentsNotificationTitle", getTextLanguage(LanguageCode, "daas_ui", "users.details.communication_preferences.incident_notification")), "Incident notification label is incorrect on logged in user's details page");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("incidentsNotificationEdit");
		LOGGER.info("Clicked on edit notification preferences button");
		softAssert.assertTrue(supportTeamDetailsPage.matchTextOfSupportTeamDetailsPage("incidentNotificationPreferencesTitle", getTextLanguage(LanguageCode, "daas_ui", "users.details.communication_preferences.incident_notification_modal_title")), "Title on incident notification popup is incorrect on logged in user's details page");
		softAssert.assertTrue(supportTeamDetailsPage.matchTextOfSupportTeamDetailsPage("incidentNotificationPreferencesSubTitle", getTextLanguage(LanguageCode, "daas_ui", "users.details.communication_preferences.incident_notification_modal_subtitle")), "Subtitle on incident notification popup is incorrect on logged in user's details page");

		softAssert.assertTrue(supportTeamDetailsPage.matchTextOfSupportTeamDetailsPage("accountTitle", getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.type.account").toUpperCase()), "Incident type 'Account' is not present on incident notification preferences popup");
		waitForPageLoaded();
		if (supportTeamDetailsPage.verifyElementsOfSupportTeamDetailsPage("accountListDropdownArrowCollapsed")) {
			supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("accountListDropdownArrow");
			LOGGER.info("Clicked on account incident type dropdown");
		} else {
			LOGGER.info("Account list dropdown is already expanded");
		}
		supportTeamDetailsPage.waitForElementsOfSupportTeamDetailsPage("accountAllLocator");
		waitForPageLoaded();
		softAssert.assertTrue(accountSubTypesOnIncidentNotificationPreferencesPopup.containsAll(supportTeamDetailsPage.getTextOfListOnSupportTeamDetailsPage("accountList")), "Incident subtypes for incident type 'Account' are incorrect");
		supportTeamDetailsPage.selectAllIncidentTypeOfSupportTeamDetailsPage("accountAllLocator");
		LOGGER.info("Selected all incident subtypes on account incident type dropdown");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("accountListDropdownArrow");

		softAssert.assertTrue(supportTeamDetailsPage.matchTextOfSupportTeamDetailsPage("hardwareChangeTitle", getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.type.hwchange").toUpperCase()), "Incident type 'Hardware Change' is not present on incident notification preferences popup");
		waitForPageLoaded();
		if (supportTeamDetailsPage.verifyElementsOfSupportTeamDetailsPage("hardwareChangeListDropdownArrowCollapsed")) {
			supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("hardwareChangeListDropdownArrow");
			LOGGER.info("Clicked on hardware change incident type dropdown");
		} else {
			LOGGER.info("Hardware change list dropdown is already expanded");
		}
		supportTeamDetailsPage.waitForElementsOfSupportTeamDetailsPage("hardwareChangeAllLocator");
		waitForPageLoaded();
		softAssert.assertTrue(hardwareChangeSubTypesOnIncidentNotificationPreferencesPopup.containsAll(supportTeamDetailsPage.getTextOfListOnSupportTeamDetailsPage("hardwareChangeList")), "Incident subtypes for incident type 'Hardware Change' are incorrect");

		supportTeamDetailsPage.selectAllIncidentTypeOfSupportTeamDetailsPage("hardwareChangeAllLocator");
		LOGGER.info("Selected all incident subtypes on hardware change incident type dropdown");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("hardwareChangeListDropdownArrow");

		softAssert.assertTrue(supportTeamDetailsPage.matchTextOfSupportTeamDetailsPage("hardwareHealthTitle", getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.type.hardwarehealth").toUpperCase()), "Incident type 'Hardware Health' is not present on incident notification preferences popup");
		waitForPageLoaded();
		if (supportTeamDetailsPage.verifyElementsOfSupportTeamDetailsPage("hardwareHealthListDropdownArrowCollapsed")) {
			supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("hardwareHealthListDropdownArrow");
			LOGGER.info("Clicked on hardware health incident type dropdown");
		} else {
			LOGGER.info("Hardware health list dropdown is already expanded");
		}
		supportTeamDetailsPage.waitForElementsOfSupportTeamDetailsPage("hardwareHealthAllLocator");
		waitForPageLoaded();
		softAssert.assertTrue(hardwareHealthSubTypesOnIncidentNotificationPreferencesPopup.containsAll(supportTeamDetailsPage.getTextOfListOnSupportTeamDetailsPage("hardwareHealthList")), "Incident subtypes for incident type 'Hardware Health' are incorrect");

		supportTeamDetailsPage.selectAllIncidentTypeOfSupportTeamDetailsPage("hardwareHealthAllLocator");
		LOGGER.info("Selected all incident subtypes on hardware health incident type dropdown");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("hardwareHealthListDropdownArrow");

		softAssert.assertTrue(supportTeamDetailsPage.matchTextOfSupportTeamDetailsPage("osHealthTitle", getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.type.oshealth").toUpperCase()), "Incident type 'OS Health' is not present on incident notification preferences popup");
		waitForPageLoaded();
		if (supportTeamDetailsPage.verifyElementsOfSupportTeamDetailsPage("osHealthListDropdownArrowCollapsed")) {
			supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("osHealthListDropdownArrow");
			LOGGER.info("Clicked on os health incident type dropdown");
		} else {
			LOGGER.info("OS health list dropdown is already expanded");
		}
		sleeper(5000);
		waitForPageLoaded();
		supportTeamDetailsPage.waitForElementsOfSupportTeamDetailsPage("osHealthAllLocator");
		waitForPageLoaded();
		softAssert.assertTrue(osHealthSubTypesOnIncidentNotificationPreferencesPopup.containsAll(supportTeamDetailsPage.getTextOfListOnSupportTeamDetailsPage("osHealthList")), "Incident subtypes for incident type 'OS Health' are incorrect");

		supportTeamDetailsPage.selectAllIncidentTypeOfSupportTeamDetailsPage("osHealthAllLocator");
		LOGGER.info("Selected all incident subtypes on os health incident type dropdown");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("osHealthListDropdownArrow");

		sleeper(1000);
		softAssert.assertTrue(supportTeamDetailsPage.matchTextOfSupportTeamDetailsPage("securityTitle", getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.type.security").toUpperCase()), "Incident type 'Security' is not present on incident notification preferences popup");
		waitForPageLoaded();
		if (supportTeamDetailsPage.verifyElementsOfSupportTeamDetailsPage("securityListDropdownArrowCollapsed")) {
			supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("securityListDropdownArrow");
			LOGGER.info("Clicked on security incident type dropdown");
		} else {
			LOGGER.info("Security list dropdown is already expanded");
		}
		supportTeamDetailsPage.waitForElementsOfSupportTeamDetailsPage("securityAllLocator");
		waitForPageLoaded();
		softAssert.assertTrue(securitySubTypesOnIncidentNotificationPreferencesPopup.containsAll(supportTeamDetailsPage.getTextOfListOnSupportTeamDetailsPage("securityList")), "Incident subtypes for incident type 'Security' are incorrect");

		supportTeamDetailsPage.selectAllIncidentTypeOfSupportTeamDetailsPage("securityAllLocator");
		LOGGER.info("Selected all incident subtypes on security incident type dropdown");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("securityListDropdownArrow");

		softAssert.assertTrue(supportTeamDetailsPage.matchTextOfSupportTeamDetailsPage("softwareHealthTitle", getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.type.softwarehealth").toUpperCase()), "Incident type 'Software Health' is not present on incident notification preferences popup");
		waitForPageLoaded();
		if (supportTeamDetailsPage.verifyElementsOfSupportTeamDetailsPage("softwareHealthListDropdownArrowCollapsed")) {
			supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("softwareHealthListDropdownArrow");
			LOGGER.info("Clicked on software health incident type dropdown");
		} else {
			LOGGER.info("Software health list dropdown is already expanded");
		}
		supportTeamDetailsPage.waitForElementsOfSupportTeamDetailsPage("softwareHealthAllLocator");
		waitForPageLoaded();
		softAssert.assertTrue(softwareHealthSubTypesOnIncidentNotificationPreferencesPopup.containsAll(supportTeamDetailsPage.getTextOfListOnSupportTeamDetailsPage("softwareHealthList")), "Incident subtypes for incident type 'Software Health' are incorrect");

		supportTeamDetailsPage.selectAllIncidentTypeOfSupportTeamDetailsPage("softwareHealthAllLocator");
		LOGGER.info("Selected all incident subtypes on software health incident type dropdown");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("softwareHealthListDropdownArrow");

		softAssert.assertTrue(supportTeamDetailsPage.matchTextOfSupportTeamDetailsPage("unassignedTitle", getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.type.unassigned").toUpperCase()), "Incident type 'Unassigned' is not present on incident notification preferences popup");
		waitForPageLoaded();
		if (supportTeamDetailsPage.verifyElementsOfSupportTeamDetailsPage("unassignedListDropdownArrowCollapsed")) {
			supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("unassignedListDropdownArrow");
			LOGGER.info("Clicked on assigned incident type dropdown");
		} else {
			LOGGER.info("Unassigned list dropdown is already expanded");
		}
		supportTeamDetailsPage.waitForElementsOfSupportTeamDetailsPage("unassignedAllLocator");
		waitForPageLoaded();
		softAssert.assertTrue(unassignedSubTypesOnIncidentNotificationPreferencesPopup.containsAll(supportTeamDetailsPage.getTextOfListOnSupportTeamDetailsPage("unassignedList")), "Incident subtypes for incident type 'Unassigned' are incorrect");

		supportTeamDetailsPage.selectAllIncidentTypeOfSupportTeamDetailsPage("unassignedAllLocator");
		LOGGER.info("Selected all incident subtypes on unassigned incident type dropdown");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("unassignedListDropdownArrow");
		supportTeamDetailsPage.waitForElementsOfSupportTeamDetailsPage("tableOverlay");

		softAssert.assertTrue(supportTeamDetailsPage.matchTextOfSupportTeamDetailsPage("doNotReceiveEmailNotificationLocator", getTextLanguage(LanguageCode, "daas_ui", "users.details.communication_preferences.incident_notification.option_1")), "Do not receive email notification radio button is not present on incident preferences popup");
		softAssert.assertTrue(supportTeamDetailsPage.matchTextOfSupportTeamDetailsPage("ReceiveEmailNotificationWhenNewIncidentIsReportedLocator", getTextLanguage(LanguageCode, "daas_ui", "users.details.communication_preferences.incident_notification.option_2")), "Receive email notification when new incident is reported radio button is not present incident preferences on popup");
		softAssert.assertTrue(supportTeamDetailsPage.matchTextOfSupportTeamDetailsPage("ReceiveAnEmailNotificationWithAllIncidentsThatAreOpenedInaDayLocator", getTextLanguage(LanguageCode, "daas_ui", "users.details.communication_preferences.incident_notification.option_3")), "Receive an email notification with all incidents that are opened in a day. radio button is not present on incident preferences popup");
		softAssert.assertTrue(supportTeamDetailsPage.matchTextOfSupportTeamDetailsPage("ReceiveEmailNotificationWhenNewIncidentIsReportedAndOneEmailWithAllIncidentsThatAreOpenedInaDayLocator", getTextLanguage(LanguageCode, "daas_ui", "users.details.communication_preferences.incident_notification.option_4")), "Receive email notification when new incident is reported and one email with all incidents that are opened in a day radio button is not present on incident preferences popup");

		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("incidentPreferencesSaveButton");
		LOGGER.info("Clicked on save button of incident preferences popup");
		supportTeamDetailsPage.waitForElementsOfSupportTeamDetailsPage("tableOverlay");
		softAssert.assertTrue(supportTeamDetailsPage.getTextOfSupportTeamDetailsPage("incidentsNotificationValue").equals(selectedAllIncidentTypes), "Save functionality on incident preferences popup is not working correctly when all subtypes of all incident types are selected");

		supportTeamDetailsPage.waitForElementsOfSupportTeamDetailsPage("tableOverlay");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("incidentsNotificationEdit");
		LOGGER.info("Clicked on edit notification preferences button");
		waitForPageLoaded();
		if (supportTeamDetailsPage.verifyElementsOfSupportTeamDetailsPage("accountListDropdownArrowCollapsed")) {
			supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("accountListDropdownArrow");
			LOGGER.info("Clicked on account incident type dropdown");
		} else {
			LOGGER.info("Account list dropdown is already expanded");
		}
		supportTeamDetailsPage.selectSingleIncidentTypeOnSupportTeamDetailsPage("accountAllLocator", "accountLocator");
		waitForPageLoaded();
		LOGGER.info("Selected single incident subtype on account incident type dropdown");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("accountListDropdownArrow");
		supportTeamDetailsPage.scrollOnSupportTeamPage("hardwareChangeListDropdownArrow");

		waitForPageLoaded();
		if (supportTeamDetailsPage.verifyElementsOfSupportTeamDetailsPage("hardwareChangeListDropdownArrowCollapsed")) {
			supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("hardwareChangeListDropdownArrow");
			LOGGER.info("Clicked on hardware change incident type dropdown");
		} else {
			LOGGER.info("Hardware change list dropdown is already expanded");
		}
		supportTeamDetailsPage.selectSingleIncidentTypeOnSupportTeamDetailsPage("hardwareChangeAllLocator", "hardwareChangeLocator");
		waitForPageLoaded();
		LOGGER.info("Selected single incident subtype on hardware change incident type dropdown");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("hardwareChangeListDropdownArrow");
		supportTeamDetailsPage.scrollOnSupportTeamPage("hardwareHealthListDropdownArrow");

		waitForPageLoaded();
		if (supportTeamDetailsPage.verifyElementsOfSupportTeamDetailsPage("hardwareHealthListDropdownArrowCollapsed")) {
			supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("hardwareHealthListDropdownArrow");
			LOGGER.info("Clicked on hardware health incident type dropdown");
		} else {
			LOGGER.info("Hardware health list dropdown is already expanded");
		}
		supportTeamDetailsPage.selectSingleIncidentTypeOnSupportTeamDetailsPage("hardwareHealthAllLocator", "hardwareHealthLocator");
		waitForPageLoaded();
		LOGGER.info("Selected single incident subtype on hardware health incident type dropdown");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("hardwareHealthListDropdownArrow");
		supportTeamDetailsPage.scrollOnSupportTeamPage("osHealthListDropdownArrow");

		waitForPageLoaded();
		if (supportTeamDetailsPage.verifyElementsOfSupportTeamDetailsPage("osHealthListDropdownArrowCollapsed")) {
			supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("osHealthListDropdownArrow");
			LOGGER.info("Clicked on os health incident type dropdown");
		} else {
			LOGGER.info("OS health list dropdown is already expanded");
		}
		supportTeamDetailsPage.selectSingleIncidentTypeOnSupportTeamDetailsPage("osHealthAllLocator", "osHealthLocator");
		waitForPageLoaded();
		LOGGER.info("Selected single incident subtype on os health incident type dropdown");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("osHealthListDropdownArrow");
		supportTeamDetailsPage.scrollOnSupportTeamPage("securityListDropdownArrow");

		sleeper(1000);
		waitForPageLoaded();
		if (supportTeamDetailsPage.verifyElementsOfSupportTeamDetailsPage("securityListDropdownArrowCollapsed")) {
			supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("securityListDropdownArrow");
			LOGGER.info("Clicked on security incident type dropdown");
		} else {
			LOGGER.info("Security list dropdown is already expanded");
		}
		supportTeamDetailsPage.selectSingleIncidentTypeOnSupportTeamDetailsPage("securityAllLocator", "securityLocator");
		waitForPageLoaded();
		LOGGER.info("Selected single incident subtype on security incident type dropdown");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("securityListDropdownArrow");
		supportTeamDetailsPage.scrollOnSupportTeamPage("softwareHealthListDropdownArrow");

		waitForPageLoaded();
		if (supportTeamDetailsPage.verifyElementsOfSupportTeamDetailsPage("softwareHealthListDropdownArrowCollapsed")) {
			supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("softwareHealthListDropdownArrow");
			LOGGER.info("Clicked on software health incident type dropdown");
		} else {
			LOGGER.info("Software health list dropdown is already expanded");
		}
		supportTeamDetailsPage.waitForElementsOfSupportTeamDetailsPage("softwareHealthList");
		waitForPageLoaded();
		supportTeamDetailsPage.selectSingleIncidentTypeOnSupportTeamDetailsPage("softwareHealthAllLocator", "softwareHealthLocator");
		LOGGER.info("Selected single incident subtype on software health incident type dropdown");
		supportTeamDetailsPage.scrollOnSupportTeamPage("unassignedListDropdownArrow");

		waitForPageLoaded();
		if (supportTeamDetailsPage.verifyElementsOfSupportTeamDetailsPage("unassignedListDropdownArrowCollapsed")) {
			supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("unassignedListDropdownArrow");
			LOGGER.info("Clicked on assigned incident type dropdown");
		} else {
			LOGGER.info("Unassigned list dropdown is already expanded");
		}
		supportTeamDetailsPage.waitForElementsOfSupportTeamDetailsPage("unassignedList");
		waitForPageLoaded();
		supportTeamDetailsPage.selectSingleIncidentTypeOnSupportTeamDetailsPage("unassignedAllLocator", "unassignedLocator");
		LOGGER.info("Selected single incident subtype on unassigned incident type dropdown");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("unassignedListDropdownArrow");
		supportTeamDetailsPage.waitForElementsOfSupportTeamDetailsPage("tableOverlay");

		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("incidentPreferencesSaveButton");
		LOGGER.info("Clicked on save button of incident preferences popup");
		supportTeamDetailsPage.waitForElementsOfSupportTeamDetailsPage("tableOverlay");
		softAssert.assertTrue(supportTeamDetailsPage.getTextOfSupportTeamDetailsPage("incidentsNotificationValue").equals(selectedSpecificIncidentTypes), "Save functionality on incident preferences popup is not working correctly when single subtype of every incident types are selected");

		supportTeamDetailsPage.waitForElementsOfSupportTeamDetailsPage("tableOverlay");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("incidentsNotificationEdit");
		LOGGER.info("Clicked on edit notification preferences button");

		waitForPageLoaded();
		if (supportTeamDetailsPage.verifyElementsOfSupportTeamDetailsPage("accountListDropdownArrowCollapsed")) {
			supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("accountListDropdownArrow");
			LOGGER.info("Clicked on account incident type dropdown");
		} else {
			LOGGER.info("Account list dropdown is already expanded");
		}
		supportTeamDetailsPage.deselectAllIncidentTypeOnSupportTeamDetailsPage("accountAllLocator");
		waitForPageLoaded();
		LOGGER.info("Removed all incident subtype on account incident type dropdown");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("accountListDropdownArrow");
		supportTeamDetailsPage.scrollOnSupportTeamPage("hardwareChangeListDropdownArrow");

		waitForPageLoaded();
		if (supportTeamDetailsPage.verifyElementsOfSupportTeamDetailsPage("hardwareChangeListDropdownArrowCollapsed")) {
			supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("hardwareChangeListDropdownArrow");
			LOGGER.info("Clicked on hardware change incident type dropdown");
		} else {
			LOGGER.info("Hardware change list dropdown is already expanded");
		}
		supportTeamDetailsPage.deselectAllIncidentTypeOnSupportTeamDetailsPage("hardwareChangeAllLocator");
		waitForPageLoaded();
		LOGGER.info("Removed all incident subtype on hardware change incident type dropdown");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("hardwareChangeListDropdownArrow");
		supportTeamDetailsPage.scrollOnSupportTeamPage("hardwareHealthListDropdownArrow");

		waitForPageLoaded();
		if (supportTeamDetailsPage.verifyElementsOfSupportTeamDetailsPage("hardwareHealthListDropdownArrowCollapsed")) {
			supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("hardwareHealthListDropdownArrow");
			LOGGER.info("Clicked on hardware health incident type dropdown");
		} else {
			LOGGER.info("Hardware health list dropdown is already expanded");
		}
		supportTeamDetailsPage.deselectAllIncidentTypeOnSupportTeamDetailsPage("hardwareHealthAllLocator");
		waitForPageLoaded();
		LOGGER.info("Removed all incident subtype on hardware health incident type dropdown");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("hardwareHealthListDropdownArrow");
		supportTeamDetailsPage.scrollOnSupportTeamPage("osHealthListDropdownArrow");

		waitForPageLoaded();
		if (supportTeamDetailsPage.verifyElementsOfSupportTeamDetailsPage("osHealthListDropdownArrowCollapsed")) {
			supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("osHealthListDropdownArrow");
			LOGGER.info("Clicked on os health incident type dropdown");
		} else {
			LOGGER.info("OS health list dropdown is already expanded");
		}
		supportTeamDetailsPage.deselectAllIncidentTypeOnSupportTeamDetailsPage("osHealthAllLocator");
		waitForPageLoaded();
		LOGGER.info("Removed all incident subtype on os health incident type dropdown");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("osHealthListDropdownArrow");
		supportTeamDetailsPage.scrollOnSupportTeamPage("securityListDropdownArrow");

		sleeper(1000);
		waitForPageLoaded();
		if (supportTeamDetailsPage.verifyElementsOfSupportTeamDetailsPage("securityListDropdownArrowCollapsed")) {
			supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("securityListDropdownArrow");
			LOGGER.info("Clicked on security incident type dropdown");
		} else {
			LOGGER.info("Security list dropdown is already expanded");
		}
		supportTeamDetailsPage.deselectAllIncidentTypeOnSupportTeamDetailsPage("securityAllLocator");
		waitForPageLoaded();
		LOGGER.info("Removed all incident subtype on security incident type dropdown");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("securityListDropdownArrow");
		supportTeamDetailsPage.scrollOnSupportTeamPage("softwareHealthListDropdownArrow");

		waitForPageLoaded();
		if (supportTeamDetailsPage.verifyElementsOfSupportTeamDetailsPage("softwareHealthListDropdownArrowCollapsed")) {
			supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("softwareHealthListDropdownArrow");
			LOGGER.info("Clicked on software health incident type dropdown");
		} else {
			LOGGER.info("Software health list dropdown is already expanded");
		}
		supportTeamDetailsPage.deselectAllIncidentTypeOnSupportTeamDetailsPage("softwareHealthAllLocator");
		waitForPageLoaded();
		LOGGER.info("Removed all incident subtype on software health incident type dropdown");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("softwareHealthListDropdownArrow");
		supportTeamDetailsPage.scrollOnSupportTeamPage("unassignedListDropdownArrow");

		waitForPageLoaded();
		if (supportTeamDetailsPage.verifyElementsOfSupportTeamDetailsPage("unassignedListDropdownArrowCollapsed")) {
			supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("unassignedListDropdownArrow");
			LOGGER.info("Clicked on assigned incident type dropdown");
		} else {
			LOGGER.info("Unassigned list dropdown is already expanded");
		}
		supportTeamDetailsPage.deselectAllIncidentTypeOnSupportTeamDetailsPage("unassignedAllLocator");
		waitForPageLoaded();
		LOGGER.info("Removed all incident subtype on unassigned incident type dropdown");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("unassignedListDropdownArrow");

		waitForPageLoaded();
		if (!supportTeamDetailsPage.verifyElementIsSelectedOfSupportTeamDetailsPage("ReceiveEmailNotificationWhenNewIncidentIsReportedRadioButton"))
			supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("ReceiveEmailNotificationWhenNewIncidentIsReportedRadioButton");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("incidentPreferencesSaveButton");
		LOGGER.info("Clicked on save button of incident preferences popup");
		waitForPageLoaded();
		
		softAssert.assertTrue(supportTeamDetailsPage.matchTextOfSupportTeamDetailsPage("validationErrorMessageOnIncidentPreferences", getTextLanguage(LanguageCode, "daas_ui", "users.details.communication_preferences.subtype_selection_error")), "");
		supportTeamDetailsPage.waitForElementsOfSupportTeamDetailsPage("doNotReceiveEmailNotificationRadioButton");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("doNotReceiveEmailNotificationRadioButton");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("incidentPreferencesSaveButton");
		LOGGER.info("Clicked on save button of incident preferences popup");
		supportTeamDetailsPage.waitForElementsOfSupportTeamDetailsPage("tableOverlay");
		softAssert.assertTrue(supportTeamDetailsPage.getTextOfSupportTeamDetailsPage("incidentsNotificationValue").equals("-"), "Save functionality on incident preferences popup is not working correctly when no incident subtypes are selected");

		supportTeamDetailsPage.waitForElementsOfSupportTeamDetailsPage("tableOverlay");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("incidentsNotificationEdit");
		LOGGER.info("Clicked on edit notification preferences button");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("accountListDropdownArrow");
		supportTeamDetailsPage.selectSingleIncidentTypeOnSupportTeamDetailsPage("accountAllLocator", "accountLocator");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("accountListDropdownArrow");
		supportTeamDetailsPage.scrollOnSupportTeamPage("hardwareChangeListDropdownArrow");

		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("hardwareChangeListDropdownArrow");
		supportTeamDetailsPage.selectSingleIncidentTypeOnSupportTeamDetailsPage("hardwareChangeAllLocator", "hardwareChangeLocator");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("hardwareChangeListDropdownArrow");

		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("incidentPreferencesCancelButton");
		LOGGER.info("Clicked on cancel button of incident preferences popup");
		softAssert.assertTrue(supportTeamDetailsPage.getTextOfSupportTeamDetailsPage("incidentsNotificationValue").equals("-"), "Cancel functionality on incident preferences popup is not working correctly");

		softAssert.assertTrue(supportTeamDetailsPage.matchTextOfSupportTeamDetailsPage("dailyEmailNotificationTitle", getTextLanguage(LanguageCode, "daas_ui", "users.details.communication_preferences.receive_daily_emails_at")), "Daily emails notification label is incorrect on logged in user's details page");
		supportTeamDetailsPage.clickByJavaScriptOnSupportTeamDetailsPage("dailyEmailNotificationEditButton");
		LOGGER.info("Clicked on edit daily email notification button");
		String timeZoneBefore = supportTeamDetailsPage.getTextOfSupportTeamDetailsPage("timezoneDropdown");
		String timeZoneCodeBefore = supportTeamDetailsPage.getTextOfSupportTeamDetailsPage("timezoneCodeDropdown");
		supportTeamDetailsPage.selectRoleFromDropDownOfSupportTeamDetailsPage("timezoneDropdown", "timezoneDropdownList", timeZoneBefore);
		LOGGER.info("Updated time zone on daily email notification dropdown");
		supportTeamDetailsPage.selectRoleFromDropDownOfSupportTeamDetailsPage("timezoneCodeDropdown", "timezoneCodeDropdownList", timeZoneCodeBefore);
		LOGGER.info("Updated time zone code on daily email notification dropdown");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("dailyEmailNotificationCancelButton");
		LOGGER.info("Clicked on cancel button of daily email notification popup");
		softAssert.assertFalse(supportTeamDetailsPage.getTextOfListOnSupportTeamDetailsPage("currentTimeZone").contains(timeZone), "Cancel functionality for timezone on daily email notification popup is not working correctly");
		softAssert.assertFalse(supportTeamDetailsPage.getTextOfListOnSupportTeamDetailsPage("currentTimeZone").contains(timeZoneCode), "Cancel functionality for timezone code on daily email notification popup is not working correctly");

		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("dailyEmailNotificationEditButton");
		waitForPageLoaded();
		LOGGER.info("Clicked on edit daily email notification button");
		timeZone = supportTeamDetailsPage.selectRoleFromDropDownOfSupportTeamDetailsPage("timezoneDropdown", "timezoneDropdownList", timeZoneBefore);
		LOGGER.info("Updated time zone on daily email notification dropdown");
		supportTeamDetailsPage.selectRoleFromDropDownOfSupportTeamDetailsPage("timezoneCodeDropdown", "timezoneCodeDropdownList", timeZoneCodeBefore);
		LOGGER.info("Updated time zone code on daily email notification dropdown");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("dailyEmailNotificationSaveButton");
		LOGGER.info("Clicked on save button of daily email notification popup");
		supportTeamDetailsPage.waitForElementsOfSupportTeamDetailsPage("tableOverlay");
		softAssert.assertTrue(supportTeamDetailsPage.matchTextOfSupportTeamDetailsPage("dailyEmailNotificationValue", timeZone), "Save functionality on daily email notification popup is not working correctly");

		softAssert.assertAll();
		LOGGER.info("Verification of communication preferences tile on logged in user's details page done successfully");
	}

	@Test(priority = 11, groups = { "REGRESSION", "REGRESSION_CI", "SMOKE", "ALL_CI", "ALL" }, description = "NFR 242589", enabled = false)
	public final void verifyEditloggedInUserDetails() throws Exception {
		login("MSP_DEVICE_LIST_EMAIL_COMPANIES", "MSP_DEVICE_LIST_PASSWORD_COMPANIES");
		SupportTeamPage supportTeamPage = new SupportTeamPage(PreDefinedActions.getDriver()).getInstance();

		SoftAssert softAssert = new SoftAssert();
		String firstName = null, lastName = null, mobileNumber = null, officeNumber = null, title = null, address1 = null, address2 = null, state = null, city = null, zipcode = null;
		gotoSupportTeamTab();
		waitForPageLoaded();
		resetTableConfiguration();
		supportTeamPage.waitForElementsOfSupportTeamPage("tableOverlay");
		supportTeamPage.enterTextForSupportTeamPage("emailTextbox", getCredentials(environment, "MSP_DEVICE_LIST_EMAIL_COMPANIES"));
		LOGGER.info("Filtered support team list with logged in user");
		supportTeamPage.waitForElementsOfSupportTeamPage("tableOverlay");
		List<WebElement> supportTeamList = supportTeamPage.getElementsOfSupportTeamPage("firstRecordName");
		LOGGER.info("Fetching filtered elements in list");
		supportTeamPage.clickElementsOfSupportTeamPage(supportTeamList);
		LOGGER.info("Clicking on list element for loading to details page ");
		supportTeamPage.waitForElementsOfSupportTeamPage("tableOverlay");
		softAssert.assertTrue(supportTeamPage.verifyElementsOfSupportTeamPage("breadCrumbLink"), "BreadCrumb link is not present");
		LOGGER.info("Verified Bread crumb link");
		softAssert.assertTrue(supportTeamPage.matchTextOfSupportTeamPage("detailsNamePanel", supportTeamPage.getTextOfSupportTeamPage("breadCrumbName")), "Name does not match");
		LOGGER.info("Verified Name of logged in user");
		softAssert.assertTrue(supportTeamPage.matchTextOfSupportTeamPage("detailsEmailPanel", getCredentials(environment, "MSP_DEVICE_LIST_EMAIL_COMPANIES")), "Email does not match");
		LOGGER.info("Verified Email of logged in user");
		softAssert.assertTrue(supportTeamPage.matchTextOfSupportTeamPage("namePanel", getTextLanguage(LanguageCode, "daas_ui", "support_team.details.name")), "Name label does not match");
		LOGGER.info("Verified Name label");
		softAssert.assertTrue(supportTeamPage.matchTextOfSupportTeamPage("emailPanel", getTextLanguage(LanguageCode, "daas_ui", "support_team.details.email")), "Email label does not match");
		LOGGER.info("Verified Email label");
		softAssert.assertTrue(supportTeamPage.matchTextOfSupportTeamPage("mobilePanel", getTextLanguage(LanguageCode, "daas_ui", "support_team.details.phone_mobile")), "Mobile Phone label does not match");
		LOGGER.info("Verified Mobile label");
		softAssert.assertTrue(supportTeamPage.matchTextOfSupportTeamPage("officePanel", getTextLanguage(LanguageCode, "daas_ui", "support_team.details.phone_office")), "Office Phone label does not match");
		LOGGER.info("Verified Office label");
		softAssert.assertTrue(supportTeamPage.verifyElementsOfSupportTeamPage("jobTitlePanel"), "Title label is not present");
		LOGGER.info("Verified Title label");
		softAssert.assertTrue(supportTeamPage.matchTextOfSupportTeamPage("addressPanel", getTextLanguage(LanguageCode, "daas_ui", "global.address")), "Address label does not match");
		LOGGER.info("Verified Address label");
		softAssert.assertTrue(supportTeamPage.verifyElementsOfSupportTeamPage("languagePanel"), "Language label is not present");
		LOGGER.info("Verified Language label");
		softAssert.assertTrue(supportTeamPage.matchTextOfSupportTeamPage("passwordPanel", getTextLanguage(LanguageCode, "daas_ui", "global.password")), "Password label does not match");
		LOGGER.info("Verified Password label");

		// Test case to verify name after change
		supportTeamPage.clickOnElementsOfSupportTeamPage("nameEditButton");
		LOGGER.info("Clicked on name edit button");
		firstName = supportTeamPage.generateRandomString(11);
		supportTeamPage.enterTextForSupportTeamPage("firstNameTextBox", firstName);
		LOGGER.info("Entered First name");
		lastName = supportTeamPage.generateRandomString(11);
		supportTeamPage.enterTextForSupportTeamPage("lastNameTextBox", lastName);
		LOGGER.info("Entered Last name");
		supportTeamPage.clickOnElementsOfSupportTeamPage("saveButton");
		LOGGER.info("Clicked save button");
		supportTeamPage.waitForElementsOfSupportTeamPage("toastNotification");
		softAssert.assertTrue(supportTeamPage.matchTextOfSupportTeamPage("detailsNamePanel", firstName + " " + lastName), "Name does not match after change");
		LOGGER.info("Verified name after change");

		// Test case to verify Mobile number after change
		supportTeamPage.clickOnElementsOfSupportTeamPage("mobileEditButton");
		LOGGER.info("Clicked on Mobile edit button");
		mobileNumber = supportTeamPage.generateRandomNumber();
		supportTeamPage.enterTextForSupportTeamPage("mobileTextBox", mobileNumber);
		LOGGER.info("Entered Mobile Number");
		supportTeamPage.clickOnElementsOfSupportTeamPage("saveButton");
		LOGGER.info("Clicked save button");
		supportTeamPage.waitForElementsOfSupportTeamPage("tableOverlay");
		softAssert.assertTrue(supportTeamPage.matchTextOfSupportTeamPage("detailsMobilePanel", mobileNumber), "mobile Number does not match after change");
		LOGGER.info("Verified Mobile number after change");

		// Test case to verify Office number after change
		supportTeamPage.clickOnElementsOfSupportTeamPage("officeEditButton");
		LOGGER.info("Clicked on Office edit button");
		officeNumber = supportTeamPage.generateRandomNumber();
		supportTeamPage.enterTextForSupportTeamPage("officeTextBox", officeNumber);
		LOGGER.info("Entered Office Number");
		supportTeamPage.clickOnElementsOfSupportTeamPage("saveButton");
		LOGGER.info("Clicked save button");
		supportTeamPage.waitForElementsOfSupportTeamPage("tableOverlay");
		softAssert.assertTrue(supportTeamPage.matchTextOfSupportTeamPage("detailsOfficePanel", officeNumber), "Office Number does not match after change");
		LOGGER.info("Verified Office number after change");

		// Test case to verify title after change
		supportTeamPage.clickOnElementsOfSupportTeamPage("titleEditButton");
		LOGGER.info("Clicked on title edit button");
		title = supportTeamPage.generateRandomString(11);
		supportTeamPage.enterTextForSupportTeamPage("titleTextBox", title);
		LOGGER.info("Entered Title");
		supportTeamPage.clickOnElementsOfSupportTeamPage("saveButton");
		LOGGER.info("Clicked save button");
		supportTeamPage.waitForElementsOfSupportTeamPage("tableOverlay");
		softAssert.assertTrue(supportTeamPage.matchTextOfSupportTeamPage("detailsTitlePanel", title), "Title does not match after change");
		LOGGER.info("Verified Title number after change");

		// Test case to verify Address after change
		supportTeamPage.clickOnElementsOfSupportTeamPage("addressEditButton");
		LOGGER.info("Clicked on Address edit button");
		address1 = supportTeamPage.generateRandomString(11);
		address2 = supportTeamPage.generateRandomString(11);
		state = supportTeamPage.generateRandomString(11);
		city = supportTeamPage.generateRandomString(11);
		zipcode = supportTeamPage.generateRandomNumber();
		supportTeamPage.enterTextForSupportTeamPage("address1Textbox", address1);
		LOGGER.info("Entered Address 1");
		supportTeamPage.enterTextForSupportTeamPage("address2Textbox", address2);
		LOGGER.info("Entered Address 2");
		supportTeamPage.selectElementFromDropDownofSupportTeamPage("dropDownBox", "dropDownList", SupportVariables.EDITPROFILE_COUNTRY);
		LOGGER.info("Selected Country");
		supportTeamPage.enterTextForSupportTeamPage("stateTextbox", state);
		LOGGER.info("Entered State");
		supportTeamPage.enterTextForSupportTeamPage("cityTextbox", city);
		LOGGER.info("Entered City");
		supportTeamPage.enterTextForSupportTeamPage("zipcodeTextbox", zipcode);
		LOGGER.info("Entered Zip Code");
		supportTeamPage.clickOnElementsOfSupportTeamPage("saveButton");
		LOGGER.info("Clicked save button");
		supportTeamPage.waitForElementsOfSupportTeamPage("toastNotification");
		softAssert.assertTrue(supportTeamPage.matchTextOfSupportTeamPage("detailsCountry", SupportVariables.EDITPROFILE_COUNTRY), "Country does not match after change");
		softAssert.assertTrue(supportTeamPage.matchTextOfSupportTeamPage("detailsAddress1", address1), "Address 1 does not match after change");
		softAssert.assertTrue(supportTeamPage.matchTextOfSupportTeamPage("detailsAddress2", address2), "Address 2 does not match after change");
		softAssert.assertTrue(supportTeamPage.matchTextOfSupportTeamPage("detailsStateCityZipcode", city + ", " + state + " " + zipcode), "City or state or zipcode does not match after change");
		LOGGER.info("Verified Address after change");

		// Test case to verify Password section
		supportTeamPage.verifyElementsOfSupportTeamPage("detailsPassword");
		softAssert.assertTrue(supportTeamPage.matchTextOfSupportTeamPage("detailsPassword", getTextLanguage(LanguageCode, "daas_ui", "users.details.password.edit")), "Password label does not match");
		softAssert.assertTrue(supportTeamPage.matchTextOfSupportTeamPage("hpAccountText", getTextLanguage(LanguageCode, "daas_ui", "users.details.account_type.hpid")), "HP Account text does not match");
		softAssert.assertTrue(supportTeamPage.matchTextOfSupportTeamPage("accountStatus", getTextLanguage(LanguageCode, "daas_ui", "global.active")), "Account status does not match");
		LOGGER.info("Verified Password section");

		// Test case to verify Avtar after change
		softAssert.assertTrue(supportTeamPage.getTextOfSupportTeamPage("detailsNamePanel").toUpperCase().equals(supportTeamPage.getTextOfSupportTeamPage("avtarName")), "Name does not match with avtar name");
		LOGGER.info("Verified Name in Avtar tile");
		softAssert.assertTrue(supportTeamPage.matchTextOfSupportTeamPage("avtarEmail", getCredentials(environment, "MSP_DEVICE_LIST_EMAIL_COMPANIES")), "Email does not match");
		LOGGER.info("Verified Email in Avtar tile");
		softAssert.assertTrue(supportTeamPage.getTextOfSupportTeamPage("avtarPhone").equals(supportTeamPage.getTextOfSupportTeamPage("detailsOfficePanel")), "Mobile does not match with avtar Mobile");
		LOGGER.info("Verified Phone in Avtar tile");

		// Test case to verify Role assignment section
		softAssert.assertTrue(supportTeamPage.getTextOfSupportTeamPage("roleAssignmentHeader").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "support_team.details.role_assignment.title").toUpperCase()), "Role Assignment Header does not match");
		LOGGER.info("Verified Role Assignment Header");
		softAssert.assertTrue(supportTeamPage.matchTextOfSupportTeamPage("roleAssignmentDescription", getTextLanguage(LanguageCode, "daas_ui", "users.details.role_assignment_description")), "Role Assignment description does not match");
		LOGGER.info("Verified Role Assignment Description");
		softAssert.assertTrue(supportTeamPage.matchTextOfSupportTeamPage("rolesLabel", getTextLanguage(LanguageCode, "daas_ui", "users.details.roles")), "Roles Label does not match");
		LOGGER.info("Verified Role Assignment Label");
		softAssert.assertTrue(supportTeamPage.matchTextOfSupportTeamPage("role", getTextLanguage(LanguageCode, "daas_ui", "roles.support_admin")), "Role does not match");
		LOGGER.info("Verified Role of logged in user");

		// Test case to verify upload image
		if (supportTeamPage.verifyElementsOfSupportTeamPage("avtarImagePhoto")) {
			supportTeamPage.clickOnElementsOfSupportTeamPage("avtarImagePhoto");
			supportTeamPage.clickOnElementsOfSupportTeamPage("noPhoto");
			supportTeamPage.clickOnElementsOfSupportTeamPage("avtarSaveButton");
			supportTeamPage.waitForElementsOfSupportTeamPage("tableOverlay");
		}
		supportTeamPage.clickOnElementsOfSupportTeamPage("avtarImageNoPhoto");
		supportTeamPage.clickOnElementsOfSupportTeamPage("photoRadio");
		supportTeamPage.setAttributeOfSupportTeam("fileImport");
		supportTeamPage.enterTextForSupportTeamPage("fileImport", ConstantPath.IMPORT_PATH + SupportVariables.AVTAR_FILENAME_INVALID);
		softAssert.assertTrue(supportTeamPage.matchTextOfSupportTeamPage("fileBrowse", getTextLanguage(LanguageCode, "daas_ui", "users.details.avatar.modal.file.placeholder")), "Invalid upload file test failed");
		supportTeamPage.clickOnElementsOfSupportTeamPage("photoRadio");
		supportTeamPage.enterTextForSupportTeamPage("fileImport", ConstantPath.IMPORT_PATH + SupportVariables.AVTAR_FILENAME);
		sleeper(2000);
		supportTeamPage.clickOnElementsOfSupportTeamPage("avtarSaveButton");
		LOGGER.info("Clicked save button");
		supportTeamPage.waitForElementsOfSupportTeamPage("tableOverlay");

		// Test case to verify no photo upload image
		supportTeamPage.clickOnElementsOfSupportTeamPage("avtarImagePhoto");
		supportTeamPage.clickOnElementsOfSupportTeamPage("noPhoto");
		supportTeamPage.clickOnElementsOfSupportTeamPage("avtarSaveButton");
		supportTeamPage.waitForElementsOfSupportTeamPage("tableOverlay");

		// Test case to verify Language after change
		supportTeamPage.clickOnElementsOfSupportTeamPage("languageEditButton");
		LOGGER.info("Clicked on Language edit button");
		supportTeamPage.selectElementFromDropDownofSupportTeamPage("dropDownBox", "dropDownList", SupportVariables.EDITPROFILE_LANGUAGE_CHINESE);
		LOGGER.info("Selected Language");
		supportTeamPage.clickOnElementsOfSupportTeamPage("saveButton");
		LOGGER.info("Clicked save button");
		supportTeamPage.waitForElementsOfSupportTeamPage("toastNotification");
		softAssert.assertTrue(supportTeamPage.matchTextOfSupportTeamPage("detailsLanguagePanel", "日本語"), "Language Text does not match");
		LOGGER.info("Verified Language after change");
		supportTeamPage.clickOnElementsOfSupportTeamPage("languageEditButton");
		supportTeamPage.selectElementFromDropDownofSupportTeamPage("dropDownBox", "dropDownList", SupportVariables.EDITPROFILE_LANGUAGE_ENGLISH);
		supportTeamPage.clickOnElementsOfSupportTeamPage("saveButton");
		supportTeamPage.waitForElementsOfSupportTeamPage("toastNotification");
		LOGGER.info("Changed Language back to English");

		softAssert.assertAll();
		LOGGER.info("Method verifySupportTeamEditProfile executed successfully");
	}

	/**
	 * This method will verify the table configuration test cases of support team list page
	 */
	@Test(priority = 12, description = "NFR 242585", groups = { "REGRESSION", "SMOKE", "REGRESSION_CI", "SMOKE_CI" })
	public final void verifyTableConfigurationTestCases() throws Exception {
		login("MSP_SETTINGS_EMAIL_SA", "MSP_SETTINGS_PASSWORD_SA");
		String tenantID = getValueFromToken("tenant");
		gotoSupportTeamTab();
		waitForPageLoaded();
		// verifying search service api
		Assert.assertTrue(getStatusCode(getSearchServiceApi(environment) + CommonVariables.DETAILSSEARCHSERVICEAPI + tenantID + CommonVariables.DETAILSSEARCHSERVICEAPI2, SupportVariables.SEARCHSERVICEBODY, "POST", getEnvironment(environment)) == CommonVariables.CODEOK, "Search service failed for Support Team list page");
		ArrayList<String> id = new ArrayList<String>(Arrays.asList(getTextLanguage(LanguageCode, "daas_ui", "support_team.details.email")));
		ArrayList<String> columnName = new ArrayList<String>(
				Arrays.asList(getTextLanguage(LanguageCode, "daas_ui", "support_team.details.name"), getTextLanguage(LanguageCode, "daas_ui", "support_team.details.email"), getTextLanguage(LanguageCode, "daas_ui", "support_team.details.role"), getTextLanguage(LanguageCode, "daas_ui", "global.form.status"), getTextLanguage(LanguageCode, "daas_ui", "support_team.details.phone_mobile"), getTextLanguage(LanguageCode, "daas_ui", "support_team.details.phone_office")));
		ArrayList<String> checkboxValue = new ArrayList<String>(Arrays.asList("true", "true", "true", "true", "false", "false"));
		verifyTableConfigurationTests(columnName, checkboxValue, id);
	}

	@Test(priority = 13, groups = { "REGRESSION", "SMOKE", "REGRESSION_CI", "SMOKE_CI", "PRODUCTION_CI", "PRODUCTION_SANITY" ,"FOUNDATIONACCOUNT_PRODUCTIONCI"}, description = "Test Case ID : 280844")
	public final void verifySupportTeamListPage() throws Exception {
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		SupportTeamPage supportTeamPage = new SupportTeamPage(PreDefinedActions.getDriver()).getInstance();
		gotoSupportTeamTab();
		LOGGER.info("Redirected to support team list page");
		waitForPageLoaded();
		supportTeamPage.clearFiltersOfSupportTeamListPage("clearfilter");
		softAssert.assertTrue(supportTeamPage.verifyElementsOfSupportTeamPage("listTable"), "Table on list page is not loaded successfully");
		softAssert.assertTrue(supportTeamPage.verifyElementsOfSupportTeamPage("nameHeader"), "Default column is not present on list page");
		softAssert.assertAll();
		LOGGER.info("Support team list page is loaded successfully");
	}

	@Test(priority = 14, groups = { "REGRESSION", "SMOKE", "REGRESSION_CI", "SMOKE_CI", "PRODUCTION_CI", "PRODUCTION_SANITY", "FOUNDATIONACCOUNT_PRODUCTIONCI" }, description = "Test Case ID : 280841")
	public final void verifySupportTeamDetailsPage() throws Exception {
		login("MSP_ADMIN_US_EMAIl", "MSP_ADMIN_US_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		SupportTeamPage supportTeamPage = new SupportTeamPage(PreDefinedActions.getDriver()).getInstance();
		SupportTeamDetailsPage supportTeamDetailsPage = new SupportTeamDetailsPage(PreDefinedActions.getDriver()).getInstance();
		gotoSupportTeamTab();
		LOGGER.info("Redirected to support team list page");
		waitForPageLoaded();
		supportTeamPage.clearFiltersOfSupportTeamListPage("clearfilter");
		supportTeamPage.waitForElementsOfSupportTeamPage("tableOverlay");
		supportTeamPage.clickByJavaScriptOnSupportTeamPage("firstIdName");
		supportTeamDetailsPage.waitForElementsOfSupportTeamDetailsPage("tableOverlay");
		LOGGER.info("Redirected to details page");
		softAssert.assertTrue(supportTeamDetailsPage.verifyElementsOfSupportTeamDetailsPage("detailsTileHeader"), "Details tile on details page is not loaded successfully");
		supportTeamDetailsPage.scrollOnSupportTeamPage("roleHeader");
		if (toggleVerification(CommonVariables.ONE_EMAIL_MULTIPLE_ACCOUNTS_TOGGLE, getCredentials(environment, "MSP_ADMIN_US_EMAIl")))
			softAssert.assertTrue(supportTeamDetailsPage.verifyElementsOfSupportTeamDetailsPage("roleHeader"), "Account tile on details page is not loaded successfully");
		else {
			softAssert.assertTrue(supportTeamDetailsPage.verifyElementsOfSupportTeamDetailsPage("roleHeader"), "Role assignment tile on details page is not loaded successfully");
		}
		softAssert.assertAll();
		LOGGER.info("Support team details page is loaded successfully");
	}

	/**
	 * This test case validates Receive incident notification option
	 */
	@Test(priority = 15, groups = { "REGRESSION", "REGRESSION_CI", "ALL_CI", "ALL" }, dataProvider = "getLoginData", description = "Test Case 255658/244068/255658/244083/244070,NFR Story 247006 ; Roles ~ MSP, Reseller, IT Admin, Report Admin", enabled = false)
	public final void verifyReceiveIncidentNotificationByEmailOption(String UserName, String UserPassword) throws Exception {
		login(UserName, UserPassword);
		SoftAssert softAssert = new SoftAssert();
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		SupportTeamDetailsPage supportTeamDetailsPage = new SupportTeamDetailsPage(PreDefinedActions.getDriver()).getInstance();
		ArrayList<String> accountSubTypesOnIncidentNotificationPreferencesPopup = new ArrayList<String>(
				Arrays.asList(getTextLanguage(LanguageCode, "daas_ui", "global.all"), getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.subtype.aggregation"), getTextLanguage(LanguageCode, "daas_ui", "download.techPulse_inc"), getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.subtype.licensing"), getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.type.unassigned")));
		ArrayList<String> genericSubTypesOnIncidentNotificationPreferencesPopup = new ArrayList<String>(Arrays.asList(getTextLanguage(LanguageCode, "daas_ui", "global.all"), "Uncategorized"));
		ArrayList<String> hardwareChangeSubTypesOnIncidentNotificationPreferencesPopup = new ArrayList<String>(
				Arrays.asList(getTextLanguage(LanguageCode, "daas_ui", "global.all"), getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.subtype.aggregation"), getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.subtype.hdd"), getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.subtype.memory"), getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.type.unassigned")));
		ArrayList<String> hardwareHealthSubTypesOnIncidentNotificationPreferencesPopup = new ArrayList<String>(Arrays.asList(getTextLanguage(LanguageCode, "daas_ui", "global.all"), getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.subtype.aggregation"), getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.subtype.batterydetected"),
				getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.subtype.batterypredictive1"), getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.subtype.hddpredictive"), getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.subtype.hddevent"),
				getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.subtype.hddstore"), getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.subtype.systemthermal"), getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.type.unassigned")));
		ArrayList<String> osHealthSubTypesOnIncidentNotificationPreferencesPopup = new ArrayList<String>(Arrays.asList(getTextLanguage(LanguageCode, "daas_ui", "global.all"), getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.subtype.aggregation"), getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.subtype.companybsod"),
				getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.subtype.cpuhigh"), getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.subtype.memoryhigh"), getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.subtype.osbsod"), getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.subtype.oscrash"), getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.type.unassigned")));

		ArrayList<String> predictiveSubTypesOnIncidentNotificationPreferencesPopup = new ArrayList<String>(Arrays.asList(getTextLanguage(LanguageCode, "daas_ui", "global.all"), getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.subtype.antivirus"), getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.subtype.batterydetected"), getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.subtype.batterypredictive1"),
				getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.subtype.cpuhigh"), getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.subtype.firewall"), getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.subtype.hdd"), getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.subtype.hddpredictive"),
				getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.subtype.hddevent"), getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.subtype.hddstore"), getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.subtype.heartbeat"), getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.subtype.memory"),
				getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.subtype.memoryhigh"), getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.subtype.osbsod"), getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.subtype.oscrash"), getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.subtype.requiredapps"),
				getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.subtype.bromium"), getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.subtype.systemthermal"), getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.type.unassigned")));
		ArrayList<String> securitySubTypesOnIncidentNotificationPreferencesPopup = new ArrayList<String>(Arrays.asList(getTextLanguage(LanguageCode, "daas_ui", "global.all"), getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.subtype.aggregation"), getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.subtype.antivirus"), getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.subtype.firewall"),
				getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.subtype.heartbeat"), getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.subtype.bromium1"), getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.type.unassigned")));

		ArrayList<String> softwareHealthSubTypesOnIncidentNotificationPreferencesPopup = new ArrayList<String>(Arrays.asList(getTextLanguage(LanguageCode, "daas_ui", "global.all"), getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.subtype.aggregation"), getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.subtype.requiredapps"), getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.type.unassigned")));
		ArrayList<String> unassignedSubTypesOnIncidentNotificationPreferencesPopup = new ArrayList<String>(Arrays.asList(getTextLanguage(LanguageCode, "daas_ui", "global.all"), getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.type.unassigned")));

		String selectedAllIncidentTypes = getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.type.account") + " (" + getTextLanguage(LanguageCode, "daas_ui", "global.all") + "), " + getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.type.generic") + " (" + getTextLanguage(LanguageCode, "daas_ui", "global.all") + "), " + getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.type.hwchange") + " ("
				+ getTextLanguage(LanguageCode, "daas_ui", "global.all") + "), " + getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.type.hardwarehealth") + " (" + getTextLanguage(LanguageCode, "daas_ui", "global.all") + "), " + getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.type.oshealth") + " (" + getTextLanguage(LanguageCode, "daas_ui", "global.all") + "), "
				+ getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.type.predictive") + " (" + getTextLanguage(LanguageCode, "daas_ui", "global.all") + "), " + getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.type.security") + " (" + getTextLanguage(LanguageCode, "daas_ui", "global.all") + "), " + getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.type.softwarehealth") + " ("
				+ getTextLanguage(LanguageCode, "daas_ui", "global.all") + "), " + getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.type.unassigned") + " (" + getTextLanguage(LanguageCode, "daas_ui", "global.all") + ")";
		UserPage userPage = new UserPage(PreDefinedActions.getDriver()).getInstance();
		dashboardPage.clickOnElementsOfDashboardPage("logoutButtonWithoutImage");
		softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("mspSettings").equals(getTextLanguage(LanguageCode, "daas_ui", "userProfile.editMyProfile").toUpperCase()), "Edit My Profile text is not present on logged in user's details page refer Test Case 255658");
		dashboardPage.clickOnElementsOfDashboardPage("mspSettings");
		userPage.waitForElementsOfUserPageForinvisibile("invisibleElement");
		supportTeamDetailsPage.verifyElementsOfSupportTeamDetailsPage("communicationPreferencesTab");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("communicationPreferencesTab");
		LOGGER.info("Redirected to logged in support team details page");
		softAssert.assertTrue(supportTeamDetailsPage.matchTextOfSupportTeamDetailsPage("communicationPreferencesHeader", getTextLanguage(LanguageCode, "daas_ui", "user.details.system.communications").toUpperCase()), "Communication preferences tile is not present on logged in user's details page refer Test Case 255658");
		softAssert.assertTrue(supportTeamDetailsPage.matchTextOfSupportTeamDetailsPage("incidentsNotificationTitle", getTextLanguage(LanguageCode, "daas_ui", "users.details.communication_preferences.incident_notification")), "Incident notification label is incorrect on logged in user's details page refer Test Case 255658");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("incidentsNotificationEdit");
		LOGGER.info("Clicked on edit notification preferences button");
		softAssert.assertTrue(supportTeamDetailsPage.matchTextOfSupportTeamDetailsPage("incidentNotificationPreferencesTitle", getTextLanguage(LanguageCode, "daas_ui", "users.details.communication_preferences.incident_notification_modal_title")), "Title on incident notification popup is incorrect on logged in user's details page refer Test Case 255658");
		softAssert.assertTrue(supportTeamDetailsPage.matchTextOfSupportTeamDetailsPage("incidentNotificationPreferencesSubTitle", getTextLanguage(LanguageCode, "daas_ui", "users.details.communication_preferences.incident_notification_modal_subtitle")), "Subtitle on incident notification popup is incorrect on logged in user's details page refer Test Case 255658");

		softAssert.assertTrue(supportTeamDetailsPage.matchTextOfSupportTeamDetailsPage("accountTitle", getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.type.account").toUpperCase()), "Incident type 'Account' is not present on incident notification preferences popup refer Test Case 255658");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("accountListDropdownArrow");
		LOGGER.info("Clicked on account incident type dropdown");
		supportTeamDetailsPage.waitForElementsOfSupportTeamDetailsPage("accountLocator");
		softAssert.assertTrue(supportTeamDetailsPage.getTextOfListOnSupportTeamDetailsPage("accountList").equals(accountSubTypesOnIncidentNotificationPreferencesPopup), "Incident subtypes for incident type 'Account' are incorrect");
		supportTeamDetailsPage.selectAllIncidentTypeOfSupportTeamDetailsPage("accountAllLocator");
		LOGGER.info("Selected all incident subtypes on account incident type dropdown");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("accountListDropdownArrow");

		softAssert.assertTrue(supportTeamDetailsPage.matchTextOfSupportTeamDetailsPage("genericTitle", getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.type.generic").toUpperCase()), "Incident types Generic not present on incident notification preferences popup refer Test Case");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("genericListDropdownArrow");
		LOGGER.info("Clicked on Generic incident type dropdown");
		supportTeamDetailsPage.waitForElementsOfSupportTeamDetailsPage("genericLocator");
		softAssert.assertTrue(supportTeamDetailsPage.getTextOfListOnSupportTeamDetailsPage("genericList").equals(genericSubTypesOnIncidentNotificationPreferencesPopup), "Incident subtypes for incident type 'Generic' are incorrect");
		supportTeamDetailsPage.selectAllIncidentTypeOfSupportTeamDetailsPage("genericAllLocator");
		LOGGER.info("Selected all incident subtypes on Generic incident type dropdown");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("genericListDropdownArrow");

		softAssert.assertTrue(supportTeamDetailsPage.matchTextOfSupportTeamDetailsPage("hardwareChangeTitle", getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.type.hwchange").toUpperCase()), "Incident type 'Hardware Change' is not present on incident notification preferences popup refer Test Case 255658");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("hardwareChangeListDropdownArrow");
		LOGGER.info("Clicked on hardware change incident type dropdown");
		supportTeamDetailsPage.waitForElementsOfSupportTeamDetailsPage("hardwareChangeLocator");
		softAssert.assertTrue(supportTeamDetailsPage.getTextOfListOnSupportTeamDetailsPage("hardwareChangeList").equals(hardwareChangeSubTypesOnIncidentNotificationPreferencesPopup), "Incident subtypes for incident type 'Hardware Change' are incorrect");
		supportTeamDetailsPage.selectAllIncidentTypeOfSupportTeamDetailsPage("hardwareChangeAllLocator");
		LOGGER.info("Selected all incident subtypes on hardware change incident type dropdown");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("hardwareChangeListDropdownArrow");

		softAssert.assertTrue(supportTeamDetailsPage.matchTextOfSupportTeamDetailsPage("hardwareHealthTitle", getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.type.hardwarehealth").toUpperCase()), "Incident type 'Hardware Health' is not present on incident notification preferences popup");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("hardwareHealthListDropdownArrow");
		LOGGER.info("Clicked on hardware health incident type dropdown");
		supportTeamDetailsPage.waitForElementsOfSupportTeamDetailsPage("hardwareHealthLocator");
		softAssert.assertTrue(supportTeamDetailsPage.getTextOfListOnSupportTeamDetailsPage("hardwareHealthList").equals(hardwareHealthSubTypesOnIncidentNotificationPreferencesPopup), "Incident subtypes for incident type 'Hardware Health' are incorrect");
		supportTeamDetailsPage.selectAllIncidentTypeOfSupportTeamDetailsPage("hardwareHealthAllLocator");
		LOGGER.info("Selected all incident subtypes on hardware health incident type dropdown");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("hardwareHealthListDropdownArrow");

		softAssert.assertTrue(supportTeamDetailsPage.matchTextOfSupportTeamDetailsPage("osHealthTitle", getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.type.oshealth").toUpperCase()), "Incident type 'OS Health' is not present on incident notification preferences popup refer Test Case 255658");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("osHealthListDropdownArrow");
		LOGGER.info("Clicked on os health incident type dropdown");
		supportTeamDetailsPage.waitForElementsOfSupportTeamDetailsPage("osHealthLocator");
		softAssert.assertTrue(supportTeamDetailsPage.getTextOfListOnSupportTeamDetailsPage("osHealthList").equals(osHealthSubTypesOnIncidentNotificationPreferencesPopup), "Incident subtypes for incident type 'OS Health' are incorrect refer Test Case 255658");
		supportTeamDetailsPage.selectAllIncidentTypeOfSupportTeamDetailsPage("osHealthAllLocator");
		LOGGER.info("Selected all incident subtypes on os health incident type dropdown");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("osHealthListDropdownArrow");

		softAssert.assertTrue(supportTeamDetailsPage.matchTextOfSupportTeamDetailsPage("predictiveTitle", getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.type.predictive").toUpperCase()), "Incident type 'predictive' is not present on incident notification preferences popup refer Test Case 255658");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("predictiveListDropdownArrow");
		LOGGER.info("Clicked on predictive incident type dropdown");
		supportTeamDetailsPage.waitForElementsOfSupportTeamDetailsPage("predictiveLocator");
		softAssert.assertTrue(supportTeamDetailsPage.getTextOfListOnSupportTeamDetailsPage("predictiveList").equals(predictiveSubTypesOnIncidentNotificationPreferencesPopup), "Incident subtypes for incident type 'predictive' are incorrect refer Test Case 255658");
		supportTeamDetailsPage.selectAllIncidentTypeOfSupportTeamDetailsPage("predictiveAllLocator");
		LOGGER.info("Selected all incident subtypes on predictive incident type dropdown");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("predictiveListDropdownArrow");

		softAssert.assertTrue(supportTeamDetailsPage.matchTextOfSupportTeamDetailsPage("securityTitle", getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.type.security").toUpperCase()), "Incident type 'Security' is not present on incident notification preferences popup refer Test Case 255658");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("securityListDropdownArrow");
		LOGGER.info("Clicked on security incident type dropdown");
		supportTeamDetailsPage.waitForElementsOfSupportTeamDetailsPage("securityLocator");
		softAssert.assertTrue(supportTeamDetailsPage.getTextOfListOnSupportTeamDetailsPage("securityList").equals(securitySubTypesOnIncidentNotificationPreferencesPopup), "Incident subtypes for incident type 'Security' are incorrect refer Test Case 255658");
		supportTeamDetailsPage.selectAllIncidentTypeOfSupportTeamDetailsPage("securityAllLocator");
		LOGGER.info("Selected all incident subtypes on security incident type dropdown");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("securityListDropdownArrow");

		softAssert.assertTrue(supportTeamDetailsPage.matchTextOfSupportTeamDetailsPage("softwareHealthTitle", getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.type.softwarehealth").toUpperCase()), "Incident type 'Software Health' is not present on incident notification preferences popup refer Test Case 255658");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("softwareHealthListDropdownArrow");
		LOGGER.info("Clicked on software health incident type dropdown");
		supportTeamDetailsPage.waitForElementsOfSupportTeamDetailsPage("softwareHealthLocator");
		softAssert.assertTrue(supportTeamDetailsPage.getTextOfListOnSupportTeamDetailsPage("softwareHealthList").equals(softwareHealthSubTypesOnIncidentNotificationPreferencesPopup), "Incident subtypes for incident type 'Software Health' are incorrect refer Test Case 255658");
		supportTeamDetailsPage.selectAllIncidentTypeOfSupportTeamDetailsPage("softwareHealthAllLocator");
		LOGGER.info("Selected all incident subtypes on software health incident type dropdown");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("softwareHealthListDropdownArrow");

		softAssert.assertTrue(supportTeamDetailsPage.matchTextOfSupportTeamDetailsPage("unassignedTitle", getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.type.unassigned").toUpperCase()), "Incident type 'Unassigned' is not present on incident notification preferences popup refer Test Case 255658");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("unassignedListDropdownArrow");
		LOGGER.info("Clicked on unassigned incident type type dropdown");
		supportTeamDetailsPage.waitForElementsOfSupportTeamDetailsPage("unassignedLocator");
		softAssert.assertTrue(supportTeamDetailsPage.getTextOfListOnSupportTeamDetailsPage("unassignedList").equals(unassignedSubTypesOnIncidentNotificationPreferencesPopup), "Incident subtypes for incident type 'Unassigned' are incorrect refer Test Case 255658");
		supportTeamDetailsPage.selectAllIncidentTypeOfSupportTeamDetailsPage("unassignedAllLocator");
		LOGGER.info("Selected all incident subtypes on unassigned incident type dropdown");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("unassignedListDropdownArrow");
		supportTeamDetailsPage.waitForElementsOfSupportTeamDetailsPage("doNotReceiveEmailNotificationRadioButton");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("doNotReceiveEmailNotificationRadioButton");
		supportTeamDetailsPage.verifyElementIsEnable("incidentPreferencesSaveButton");
		LOGGER.info("Save button of incident preferences popup is enable verification of test case 244070 is done");
		supportTeamDetailsPage.verifyElementIsEnable("incidentPreferencesCancelButton");
		LOGGER.info("Cancel button of incident preferences popup is enable");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("incidentPreferencesSaveButton");
		LOGGER.info("Clicked on save button of incident preferences popup");
		softAssert.assertTrue(supportTeamDetailsPage.verifyElementsOfSupportTeamDetailsPage("toastNotification"), "Toast notification message is not generated when incident preferences are updated refer Test Case 255658");
		softAssert.assertTrue(supportTeamDetailsPage.getTextOfSupportTeamDetailsPage("incidentsNotificationValue").equals(selectedAllIncidentTypes), "Save functionality on incident preferences popup is not working correctly when all subtypes of all incident types are selected refer Test Case 255658");

		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("incidentsNotificationEdit");
		LOGGER.info("Clicked on edit notification preferences button");

		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("accountListDropdownArrow");
		supportTeamDetailsPage.deselectAllIncidentTypeOnSupportTeamDetailsPage("accountAllLocator");
		LOGGER.info("Removed all incident subtype on account incident type dropdown");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("accountListDropdownArrow");
		supportTeamDetailsPage.scrollOnSupportTeamPage("genericListDropdownArrow");

		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("genericListDropdownArrow");
		supportTeamDetailsPage.deselectAllIncidentTypeOnSupportTeamDetailsPage("genericAllLocator");
		LOGGER.info("Removed all incident subtype on generic incident type dropdown");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("genericListDropdownArrow");
		supportTeamDetailsPage.scrollOnSupportTeamPage("hardwareChangeListDropdownArrow");

		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("hardwareChangeListDropdownArrow");
		supportTeamDetailsPage.deselectAllIncidentTypeOnSupportTeamDetailsPage("hardwareChangeAllLocator");
		LOGGER.info("Removed all incident subtype on hardware change incident type dropdown");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("hardwareChangeListDropdownArrow");
		supportTeamDetailsPage.scrollOnSupportTeamPage("hardwareHealthListDropdownArrow");

		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("hardwareHealthListDropdownArrow");
		supportTeamDetailsPage.deselectAllIncidentTypeOnSupportTeamDetailsPage("hardwareHealthAllLocator");
		LOGGER.info("Removed all incident subtype on hardware health incident type dropdown");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("hardwareHealthListDropdownArrow");
		supportTeamDetailsPage.scrollOnSupportTeamPage("osHealthListDropdownArrow");

		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("osHealthListDropdownArrow");
		supportTeamDetailsPage.deselectAllIncidentTypeOnSupportTeamDetailsPage("osHealthAllLocator");
		LOGGER.info("Removed all incident subtype on os health incident type dropdown");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("osHealthListDropdownArrow");
		supportTeamDetailsPage.scrollOnSupportTeamPage("predictiveListDropdownArrow");

		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("predictiveListDropdownArrow");
		supportTeamDetailsPage.deselectAllIncidentTypeOnSupportTeamDetailsPage("predictiveAllLocator");
		LOGGER.info("Removed all incident subtype on predictive incident type dropdown");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("predictiveListDropdownArrow");
		supportTeamDetailsPage.scrollOnSupportTeamPage("securityListDropdownArrow");

		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("securityListDropdownArrow");
		supportTeamDetailsPage.deselectAllIncidentTypeOnSupportTeamDetailsPage("securityAllLocator");
		LOGGER.info("Removed all incident subtype on security incident type dropdown");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("securityListDropdownArrow");
		supportTeamDetailsPage.scrollOnSupportTeamPage("softwareHealthListDropdownArrow");

		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("softwareHealthListDropdownArrow");
		supportTeamDetailsPage.deselectAllIncidentTypeOnSupportTeamDetailsPage("softwareHealthAllLocator");
		LOGGER.info("Removed all incident subtype on software health incident type dropdown");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("softwareHealthListDropdownArrow");
		supportTeamDetailsPage.scrollOnSupportTeamPage("unassignedListDropdownArrow");

		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("unassignedListDropdownArrow");
		supportTeamDetailsPage.deselectAllIncidentTypeOnSupportTeamDetailsPage("unassignedAllLocator");
		LOGGER.info("Removed all incident subtype on unassigned incident type dropdown");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("unassignedListDropdownArrow");

		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("incidentPreferencesSaveButton");
		LOGGER.info("Clicked on save button of incident preferences popup to deselect options for next run");
		softAssert.assertTrue(supportTeamDetailsPage.verifyElementsOfSupportTeamDetailsPage("toastNotification"), "Toast notification message is not generated when incident preferences are updated");
		supportTeamDetailsPage.waitForElementsOfSupportTeamDetailsPage("incidentsNotificationValue");
		softAssert.assertTrue(supportTeamDetailsPage.getTextOfSupportTeamDetailsPage("incidentsNotificationValue").equals("-"), "When no incidents are selected it is not showing - refer test case-244068 ");
		LOGGER.info("Verification of test case 244068 is done");
		softAssert.assertAll();
		LOGGER.info("Test Case 255658/244068/244070 verifyReceiveIncidentNotificationByEmailOption executed successfully");

	}

	/**
	 * This test case validates custom incident notification text
	 */
	@Test(priority = 16, groups = { "REGRESSION", "REGRESSION_CI", "ALL_CI", "ALL" }, description = "Test Case 244088,NFR Story 247006")
	public final void verifyCustomIncidentNotificationText() throws Exception {
		login("MSP_SETTINGS_EMAIL_SA", "MSP_SETTINGS_PASSWORD_SA");
		SoftAssert softAssert = new SoftAssert();
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		SupportTeamDetailsPage supportTeamDetailsPage = new SupportTeamDetailsPage(PreDefinedActions.getDriver()).getInstance();
		UserPage userPage = new UserPage(PreDefinedActions.getDriver()).getInstance();
		dashboardPage.clickOnElementsOfDashboardPage("logoutButtonWithoutImage");
		softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("mspSettings").equals(getTextLanguage(LanguageCode, "daas_ui", "userProfile.editProfile")), "Edit My Profile text is not present on logged in user's details page refer Test Case 244088");
		dashboardPage.clickOnElementsOfDashboardPage("mspSettings");
		userPage.waitForElementsOfUserPageForinvisibile("invisibleElement");
		supportTeamDetailsPage.verifyElementsOfSupportTeamDetailsPage("communicationPreferencesTab");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("communicationPreferencesTab");
		LOGGER.info("Redirected to logged in support team details page");
		softAssert.assertTrue(supportTeamDetailsPage.getTextOfSupportTeamDetailsPage("communicationPreferencesHeader").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "user.details.system.communications").toUpperCase()), "Communication preferences tile is not present on logged in user's details page refer Test Case 244088");
		softAssert.assertTrue(supportTeamDetailsPage.getTextOfSupportTeamDetailsPage("incidentsNotificationTitle").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "users.details.communication_preferences.incident_notification")), "Incident notification label is incorrect on logged in user's details page refer test case 244088");

		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("incidentsNotificationEdit");
		LOGGER.info("Clicked on edit notification preferences button");
		softAssert.assertTrue(supportTeamDetailsPage.matchTextOfSupportTeamDetailsPage("incidentNotificationPreferencesTitle", getTextLanguage(LanguageCode, "daas_ui", "users.details.communication_preferences.incident_notification_modal_title")), "Title on incident notification popup is incorrect on logged in user's details page refer Test Case 255658");
		softAssert.assertTrue(supportTeamDetailsPage.matchTextOfSupportTeamDetailsPage("incidentNotificationPreferencesSubTitle", getTextLanguage(LanguageCode, "daas_ui", "users.details.communication_preferences.incident_notification_modal_subtitle")), "Subtitle on incident notification popup is incorrect on logged in user's details page refer Test Case 255658");

		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("accountListDropdownArrow");
		supportTeamDetailsPage.deselectAllIncidentTypeOnSupportTeamDetailsPage("accountAllLocator");
		LOGGER.info("Removed all incident subtype on account incident type dropdown");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("accountListDropdownArrow");
		supportTeamDetailsPage.scrollOnSupportTeamPage("hardwareChangeListDropdownArrow");

		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("hardwareChangeListDropdownArrow");
		supportTeamDetailsPage.deselectAllIncidentTypeOnSupportTeamDetailsPage("hardwareChangeAllLocator");
		LOGGER.info("Removed all incident subtype on hardware change incident type dropdown");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("hardwareChangeListDropdownArrow");
		supportTeamDetailsPage.scrollOnSupportTeamPage("hardwareHealthListDropdownArrow");

		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("hardwareHealthListDropdownArrow");
		supportTeamDetailsPage.deselectAllIncidentTypeOnSupportTeamDetailsPage("hardwareHealthAllLocator");
		LOGGER.info("Removed all incident subtype on hardware health incident type dropdown");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("hardwareHealthListDropdownArrow");
		supportTeamDetailsPage.scrollOnSupportTeamPage("osHealthListDropdownArrow");

		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("osHealthListDropdownArrow");
		supportTeamDetailsPage.deselectAllIncidentTypeOnSupportTeamDetailsPage("osHealthAllLocator");
		LOGGER.info("Removed all incident subtype on os health incident type dropdown");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("osHealthListDropdownArrow");
		supportTeamDetailsPage.scrollOnSupportTeamPage("securityListDropdownArrow");

		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("securityListDropdownArrow");
		supportTeamDetailsPage.deselectAllIncidentTypeOnSupportTeamDetailsPage("securityAllLocator");
		LOGGER.info("Removed all incident subtype on security incident type dropdown");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("securityListDropdownArrow");
		supportTeamDetailsPage.scrollOnSupportTeamPage("softwareHealthListDropdownArrow");

		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("softwareHealthListDropdownArrow");
		supportTeamDetailsPage.deselectAllIncidentTypeOnSupportTeamDetailsPage("softwareHealthAllLocator");
		LOGGER.info("Removed all incident subtype on software health incident type dropdown");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("softwareHealthListDropdownArrow");
		supportTeamDetailsPage.scrollOnSupportTeamPage("unassignedListDropdownArrow");

		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("unassignedListDropdownArrow");
		supportTeamDetailsPage.deselectAllIncidentTypeOnSupportTeamDetailsPage("unassignedAllLocator");
		LOGGER.info("Removed all incident subtype on unassigned incident type dropdown");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("unassignedListDropdownArrow");
		supportTeamDetailsPage.waitForElementsOfSupportTeamDetailsPage("doNotReceiveEmailNotificationRadioButton");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("doNotReceiveEmailNotificationRadioButton");

		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("incidentPreferencesSaveButton");
		LOGGER.info("Clicked on save button of incident preferences popup to deselect options");
			supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("toastNotificationClose");
		sleeper(1000);
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("incidentsNotificationEdit");
		LOGGER.info("Clicked on edit notification preferences button");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("accountListDropdownArrow");
		LOGGER.info("Clicked on account incident type dropdown");
		supportTeamDetailsPage.selectSingleIncidentTypeOnSupportTeamDetailsPage("accountAllLocator", "accountLocator");
		LOGGER.info("Selected single incident subtype on account incident type dropdown");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("accountListDropdownArrow");

		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("incidentPreferencesSaveButton");
		LOGGER.info("Clicked on save button of incident preferences popup");
		sleeper(1000);
		softAssert.assertTrue(supportTeamDetailsPage.verifyElementsOfSupportTeamDetailsPage("toastNotification"), "Toast notification message is not generated when incident preferences are updated refer test case 244088");
			supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("toastNotificationClose");
		supportTeamDetailsPage.waitForElementsOfSupportTeamDetailsPage("incidentsNotificationValue");
		softAssert.assertTrue(supportTeamDetailsPage.matchTextOfSupportTeamDetailsPage("incidentsNotificationValue", "Account (Custom)"), "Correct Custom text is not visible on incident preferences tile when subtype of Account incident types is selected refer test case 244088");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("incidentsNotificationEdit");
		LOGGER.info("Clicked on edit notification preferences button");

		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("accountListDropdownArrow");
		supportTeamDetailsPage.deselectAllIncidentTypeOnSupportTeamDetailsPage("accountAllLocator");
		LOGGER.info("Removed all incident subtype on account incident type dropdown");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("accountListDropdownArrow");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("incidentPreferencesSaveButton");
		LOGGER.info("Clicked on save button of incident preferences popup");
		sleeper(1000);
		softAssert.assertTrue(supportTeamDetailsPage.verifyElementsOfSupportTeamDetailsPage("toastNotification"), "Toast notification message is not generated when incident preferences are updated");
		supportTeamDetailsPage.waitForElementsOfSupportTeamDetailsPage("incidentsNotificationValue");
		softAssert.assertTrue(supportTeamDetailsPage.matchTextOfSupportTeamDetailsPage("incidentsNotificationValue","-"), "When no incidents are selected it is not showing - refer test case-244068 ");
		LOGGER.info("Verification of test case 244068 is done");
		softAssert.assertAll();
		LOGGER.info("Test Case 244088 verifyCustomIncidentNotificationText executed successfully");
	}

	/**
	 * This test case validates Error message when user select no incident type with option ReceiveEmailNotificationWhenNewIncidentIsRepored
	 */
	@Test(priority = 17, groups = { "REGRESSION", "REGRESSION_CI", "ALL_CI", "ALL" }, description = "Test Case 243528/244009,NFR Story 247006", enabled = true)
	public final void verifyErrorMsgOfSelectOneIncidentSubtype() throws Exception {
		login("MSP_SETTINGS_EMAIL_SA", "MSP_SETTINGS_PASSWORD_SA");
		SoftAssert softAssert = new SoftAssert();
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		SupportTeamDetailsPage supportTeamDetailsPage = new SupportTeamDetailsPage(PreDefinedActions.getDriver()).getInstance();
		UserPage userPage = new UserPage(PreDefinedActions.getDriver()).getInstance();
		dashboardPage.clickOnElementsOfDashboardPage("logoutButtonWithoutImage");
			softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("mspSettings").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "userProfile.editProfile")), "Edit Profile text is not present on logged in user's details page refer Test Case 244088");
		dashboardPage.clickOnElementsOfDashboardPage("mspSettings");
		userPage.waitForElementsOfUserPageForinvisibile("invisibleElement");
		supportTeamDetailsPage.verifyElementsOfSupportTeamDetailsPage("communicationPreferencesTab");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("communicationPreferencesTab");
		LOGGER.info("Redirected to logged in support team details page");
		softAssert.assertTrue(supportTeamDetailsPage.getTextOfSupportTeamDetailsPage("communicationPreferencesHeader").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "user.details.system.communications")), "Communication preferences tile is not present on logged in user's details page refer Test Case 243528");		
		softAssert.assertTrue(supportTeamDetailsPage.getTextOfSupportTeamDetailsPage("incidentsNotificationTitle").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "users.details.communication_preferences.incident_notification")), "Incident notification label is incorrect on logged in user's details page refer test case 243528");

		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("incidentsNotificationEdit");
		LOGGER.info("Clicked on edit notification preferences button");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("accountListDropdownArrow");
		supportTeamDetailsPage.deselectAllIncidentTypeOnSupportTeamDetailsPage("accountAllLocator");
		LOGGER.info("Removed all incident subtype on account incident type dropdown");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("accountListDropdownArrow");
		supportTeamDetailsPage.scrollOnSupportTeamPage("hardwareChangeListDropdownArrow");

		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("hardwareChangeListDropdownArrow");
		supportTeamDetailsPage.deselectAllIncidentTypeOnSupportTeamDetailsPage("hardwareChangeAllLocator");
		LOGGER.info("Removed all incident subtype on hardware change incident type dropdown");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("hardwareChangeListDropdownArrow");
		supportTeamDetailsPage.scrollOnSupportTeamPage("hardwareHealthListDropdownArrow");

		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("hardwareHealthListDropdownArrow");
		supportTeamDetailsPage.deselectAllIncidentTypeOnSupportTeamDetailsPage("hardwareHealthAllLocator");
		LOGGER.info("Removed all incident subtype on hardware health incident type dropdown");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("hardwareHealthListDropdownArrow");
		supportTeamDetailsPage.scrollOnSupportTeamPage("osHealthListDropdownArrow");

		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("osHealthListDropdownArrow");
		supportTeamDetailsPage.deselectAllIncidentTypeOnSupportTeamDetailsPage("osHealthAllLocator");
		LOGGER.info("Removed all incident subtype on os health incident type dropdown");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("osHealthListDropdownArrow");
		supportTeamDetailsPage.scrollOnSupportTeamPage("securityListDropdownArrow");

		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("securityListDropdownArrow");
		supportTeamDetailsPage.deselectAllIncidentTypeOnSupportTeamDetailsPage("securityAllLocator");
		LOGGER.info("Removed all incident subtype on security incident type dropdown");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("securityListDropdownArrow");
		supportTeamDetailsPage.scrollOnSupportTeamPage("softwareHealthListDropdownArrow");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("softwareHealthListDropdownArrow");
		supportTeamDetailsPage.deselectAllIncidentTypeOnSupportTeamDetailsPage("softwareHealthAllLocator");
		LOGGER.info("Removed all incident subtype on software health incident type dropdown");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("softwareHealthListDropdownArrow");
		supportTeamDetailsPage.scrollOnSupportTeamPage("unassignedListDropdownArrow");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("unassignedListDropdownArrow");
		supportTeamDetailsPage.deselectAllIncidentTypeOnSupportTeamDetailsPage("unassignedAllLocator");
		LOGGER.info("Removed all incident subtype on unassigned incident type dropdown");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("unassignedListDropdownArrow");
		LOGGER.info("Removed all incident subtype from all incident type dropdowns");
		supportTeamDetailsPage.waitForElementsOfSupportTeamDetailsPage("doNotReceiveEmailNotificationRadioButton");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("doNotReceiveEmailNotificationRadioButton");
		LOGGER.info("User selected one option from Email notification options of incident preferences popup- test case 244009 verified");
		supportTeamDetailsPage.waitForElementsOfSupportTeamDetailsPage("ReceiveEmailNotificationWhenNewIncidentIsReportedLocator");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("ReceiveEmailNotificationWhenNewIncidentIsReportedLocator");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("incidentPreferencesSaveButton");
		LOGGER.info("Clicked on save button of incident preferences popup now error should be seen");
		supportTeamDetailsPage.waitUntillElementIsPresent("validationErrorMessageOnIncidentPreferences");
		softAssert.assertTrue(supportTeamDetailsPage.matchTextOfSupportTeamDetailsPage("validationErrorMessageOnIncidentPreferences", getTextLanguage(LanguageCode, "daas_ui", "users.details.communication_preferences.subtype_selection_error")), "Refer test case 243528");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("incidentPreferencesCancelButton");
		LOGGER.info("Clicked on cancel button of incident preferences popup");
		softAssert.assertAll();
		LOGGER.info("Test Case 243528/244009 verifyCustomIncidentNotificationText executed successfully");
	}

	/**
	 * This test case validates communication preferences option Receive daily email's
	 */
	@Test(priority = 18, groups = { "REGRESSION", "REGRESSION_CI", "ALL_CI", "ALL" }, description = "Test Case 246055/245247,NFR Story 247006")
	public final void verifyReceiveDailyEmailTimeChecks() throws Exception {
		login("MSP_SETTINGS_EMAIL_SA", "MSP_SETTINGS_PASSWORD_SA");
		SoftAssert softAssert = new SoftAssert();

		ArrayList<String> timeValuesInDropDown = new ArrayList<String>(Arrays.asList(("12 " + getTextLanguage(LanguageCode, "daas_ui", "settings.hours_of_operation.time.am")), ("1 " + getTextLanguage(LanguageCode, "daas_ui", "settings.hours_of_operation.time.am")), ("2 " + getTextLanguage(LanguageCode, "daas_ui", "settings.hours_of_operation.time.am")), ("3 " + getTextLanguage(LanguageCode, "daas_ui", "settings.hours_of_operation.time.am")),
				("4 " + getTextLanguage(LanguageCode, "daas_ui", "settings.hours_of_operation.time.am")), ("5 " + getTextLanguage(LanguageCode, "daas_ui", "settings.hours_of_operation.time.am")), ("6 " + getTextLanguage(LanguageCode, "daas_ui", "settings.hours_of_operation.time.am")), ("7 " + getTextLanguage(LanguageCode, "daas_ui", "settings.hours_of_operation.time.am")), ("8 " + getTextLanguage(LanguageCode, "daas_ui", "settings.hours_of_operation.time.am")),
				("9 " + getTextLanguage(LanguageCode, "daas_ui", "settings.hours_of_operation.time.am")), ("10 " + getTextLanguage(LanguageCode, "daas_ui", "settings.hours_of_operation.time.am")), ("11 " + getTextLanguage(LanguageCode, "daas_ui", "settings.hours_of_operation.time.am")), ("12 " + getTextLanguage(LanguageCode, "daas_ui", "settings.hours_of_operation.time.pm")), ("1 " + getTextLanguage(LanguageCode, "daas_ui", "settings.hours_of_operation.time.pm")),
				("2 " + getTextLanguage(LanguageCode, "daas_ui", "settings.hours_of_operation.time.pm")), ("3 " + getTextLanguage(LanguageCode, "daas_ui", "settings.hours_of_operation.time.pm")), ("4 " + getTextLanguage(LanguageCode, "daas_ui", "settings.hours_of_operation.time.pm")), ("5 " + getTextLanguage(LanguageCode, "daas_ui", "settings.hours_of_operation.time.pm")), ("6 " + getTextLanguage(LanguageCode, "daas_ui", "settings.hours_of_operation.time.pm")),
				("7 " + getTextLanguage(LanguageCode, "daas_ui", "settings.hours_of_operation.time.pm")), ("8 " + getTextLanguage(LanguageCode, "daas_ui", "settings.hours_of_operation.time.pm")), ("9 " + getTextLanguage(LanguageCode, "daas_ui", "settings.hours_of_operation.time.pm")), ("10 " + getTextLanguage(LanguageCode, "daas_ui", "settings.hours_of_operation.time.pm")), ("11 " + getTextLanguage(LanguageCode, "daas_ui", "settings.hours_of_operation.time.pm"))));
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		SupportTeamDetailsPage supportTeamDetailsPage = new SupportTeamDetailsPage(PreDefinedActions.getDriver()).getInstance();
		UserPage userPage = new UserPage(PreDefinedActions.getDriver()).getInstance();
		dashboardPage.clickOnElementsOfDashboardPage("logoutButtonWithoutImage");
			softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("mspSettings").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "userProfile.editProfile")), "Edit Profile text is not present on logged in user's details page refer Test Case 244088");
		dashboardPage.clickOnElementsOfDashboardPage("mspSettings");
		userPage.waitForElementsOfUserPageForinvisibile("invisibleElement");
		supportTeamDetailsPage.verifyElementsOfSupportTeamDetailsPage("communicationPreferencesTab");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("communicationPreferencesTab");
		LOGGER.info("Redirected to logged in support team details page");
		softAssert.assertTrue(supportTeamDetailsPage.getTextOfSupportTeamDetailsPage("communicationPreferencesHeader").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "user.details.system.communications")), "Communication preferences tile is not present on logged in user's details page refer Test Case 246055");
		softAssert.assertTrue(supportTeamDetailsPage.getTextOfSupportTeamDetailsPage("dailyEmailNotificationTitle").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "users.details.communication_preferences.receive_daily_emails_at")), "Daily emails notification label is incorrect on logged in user's details page");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("dailyEmailNotificationEditButton");
		LOGGER.info("Clicked on edit daily email notification button");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("timezoneDropdown");
		softAssert.assertTrue(supportTeamDetailsPage.compareTwoListOfSupportTeamDetailsPage("timezoneDropdownList", timeValuesInDropDown), "Time Values are not correct refer test case 246055");
		softAssert.assertAll();
		LOGGER.info("Test Case 246055/245247 verifyReceiveDailyEmailTimeChecks executed successfully");
	}

	/**
	 * This test case validates communication preferences tile changes made by ITAdmin, SA,SS should not be reflected to other User
	 */
	@Test(priority = 19, groups = { "REGRESSION", "REGRESSION_CI", "ALL_CI", "ALL" }, dataProvider = "getdifferentLoginData", description = "Test Case 255625/246094/246095,NFR Story 247006 ; Roles ~ Support Specialist, Sales Specialist, MSP, IT Admin", enabled = false)
	public final void verifyUserMadeCommunicationPreferencesChanges(String UserName, String UserPassword, String UserName1, String UserPassword1) throws Exception {
		login(UserName, UserPassword);
		SoftAssert softAssert = new SoftAssert();
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		SupportTeamDetailsPage supportTeamDetailsPage = new SupportTeamDetailsPage(PreDefinedActions.getDriver()).getInstance();
		UserPage userPage = new UserPage(PreDefinedActions.getDriver()).getInstance();
		dashboardPage.clickOnElementsOfDashboardPage("logoutButtonWithoutImage");
		softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("mspSettings").equals(getTextLanguage(LanguageCode, "daas_ui", "userProfile.editMyProfile").toUpperCase()), "Edit My Profile text is not present on logged in user's details page refer Test Case 255625/246094/246095");
		dashboardPage.clickOnElementsOfDashboardPage("mspSettings");
		userPage.waitForElementsOfUserPageForinvisibile("invisibleElement");
		supportTeamDetailsPage.verifyElementsOfSupportTeamDetailsPage("communicationPreferencesTab");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("communicationPreferencesTab");
		LOGGER.info("Redirected to logged in support team details page");
		softAssert.assertTrue(supportTeamDetailsPage.getTextOfSupportTeamDetailsPage("communicationPreferencesHeader").equals(getTextLanguage(LanguageCode, "daas_ui", "user.details.system.communications").toUpperCase()), "Communication preferences tile is not present on logged in user's details page refer Test Case 255625/246094/246095");
		softAssert.assertTrue(supportTeamDetailsPage.matchTextOfSupportTeamDetailsPage("incidentsNotificationTitle", getTextLanguage(LanguageCode, "daas_ui", "users.details.communication_preferences.incident_notification")), "Incident notification label is incorrect on logged in user's details page refer Test Case 255625/246094/246095");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("incidentsNotificationEdit");
		LOGGER.info("Clicked on edit notification preferences button");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("accountListDropdownArrow");
		LOGGER.info("Clicked on account incident type dropdown");
		supportTeamDetailsPage.selectSingleIncidentTypeOnSupportTeamDetailsPage("accountAllLocator", "accountLocator");
		LOGGER.info("Selected single incident subtype on account incident type dropdown");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("incidentPreferencesSaveButton");
		LOGGER.info("Clicked on save button of incident preferences popup");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("incidentPreferencesCancelButton");
		LOGGER.info("Clicked on cancel button of incident preferences popup");
		LOGGER.info("User changed Communication Preferences Setting");
		logout();
		login(UserName1, UserPassword1);
		dashboardPage.clickOnElementsOfDashboardPage("logoutButtonWithoutImage");
		softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("mspSettings").equals(getTextLanguage(LanguageCode, "daas_ui", "userProfile.editMyProfile").toUpperCase()), "Edit My Profile text is not present on logged in user's details page refer Test Case 255625/246094/246095");
		dashboardPage.clickOnElementsOfDashboardPage("mspSettings");
		userPage.waitForElementsOfUserPageForinvisibile("invisibleElement");
		supportTeamDetailsPage.verifyElementsOfSupportTeamDetailsPage("communicationPreferencesTab");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("communicationPreferencesTab");
		LOGGER.info("Redirected to logged in support team details page");
		softAssert.assertTrue(supportTeamDetailsPage.getTextOfSupportTeamDetailsPage("communicationPreferencesHeader").equals(getTextLanguage(LanguageCode, "daas_ui", "user.details.system.communications").toUpperCase()), "Communication preferences tile is not present on logged in user's details page refer Test Case 255625/246094/246095");
		softAssert.assertFalse(supportTeamDetailsPage.matchTextOfSupportTeamDetailsPage("incidentsNotificationValue", getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.type.account")), "Incident notification label is present for different logged in IT Admin refer Test Case 255625/246094/246095");
		softAssert.assertAll();
		LOGGER.info("Test Case 255625/246094/246095 verifyUserMadeCommunicationPreferencesChanges executed successfully ");
	}

	/**
	 * This test case validates communication preferences option Receive daily email's default timezone
	 */
	@Test(priority = 20, groups = { "REGRESSION", "REGRESSION_CI", "ALL_CI", "ALL" }, enabled = false, dataProvider = "getLoginData", description = "Test Case 244069/255657,NFR Story 247006 ; Roles ~ MSP, Reseller, IT Admin, Report Admin")
	public final void verifyReceiveDailyTimezoneCheck(String userName, String password) throws Exception {
		login(userName, password);
		SoftAssert softAssert = new SoftAssert();
		SupportTeamDetailsPage supportTeamDetailsPage = new SupportTeamDetailsPage(PreDefinedActions.getDriver()).getInstance();
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		UserPage userPage = new UserPage(PreDefinedActions.getDriver()).getInstance();
		dashboardPage.clickOnElementsOfDashboardPage("logoutButtonWithoutImage");
		softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("mspSettings").equals(getTextLanguage(LanguageCode, "daas_ui", "userProfile.editMyProfile").toUpperCase()), "Edit My Profile text is not present on logged in user's details page refer Test Case  244069/255657");
		dashboardPage.clickOnElementsOfDashboardPage("mspSettings");
		userPage.waitForElementsOfUserPageForinvisibile("invisibleElement");
		supportTeamDetailsPage.verifyElementsOfSupportTeamDetailsPage("communicationPreferencesTab");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("communicationPreferencesTab");
		LOGGER.info("Redirected to logged in support team details page");
		softAssert.assertTrue(supportTeamDetailsPage.matchTextOfSupportTeamDetailsPage("dailyEmailNotificationTitle", getTextLanguage(LanguageCode, "daas_ui", "users.details.communication_preferences.receive_daily_emails_at")), "Daily emails notification label is incorrect on logged in user's details page refer Test Case  244069/255657");
		softAssert.assertTrue(supportTeamDetailsPage.matchTextOfSupportTeamDetailsPage("currentTimeZone", "12"), "Default time zone is not 12 AM someone changed MSP timezone refer Test Case  244069/255657");
		softAssert.assertAll();
		LOGGER.info("Test Case 244069/255657 verifyReceiveDailyTimezoneCheck executed successfully");
	}

	/**
	 * This test case validates Report Admin should be able to change communication preferences option Receive daily email's time
	 */
	@Test(priority = 21, groups = { "REGRESSION", "REGRESSION_CI", "ALL_CI", "ALL" }, dataProvider = "getLoginData", description = "Test Case 255628/255688/246056,NFR Story 247006 ; Roles ~ MSP, Reseller, IT Admin, Report Admin", enabled = false)
	public final void verifyUserCanViewReceiveDailyTimezone(String userName, String password) throws Exception {
		login(userName, password);
		SoftAssert softAssert = new SoftAssert();
		String timeZone = null;
		String timeZoneCode = null;
		SupportTeamDetailsPage supportTeamDetailsPage = new SupportTeamDetailsPage(PreDefinedActions.getDriver()).getInstance();
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		UserPage userPage = new UserPage(PreDefinedActions.getDriver()).getInstance();
		dashboardPage.clickOnElementsOfDashboardPage("logoutButtonWithoutImage");
		softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("mspSettings").equals(getTextLanguage(LanguageCode, "daas_ui", "userProfile.editMyProfile").toUpperCase()), "Edit My Profile text is not present on logged in user's details page refer Test Case 255628/255688");
		dashboardPage.clickOnElementsOfDashboardPage("mspSettings");
		userPage.waitForElementsOfUserPageForinvisibile("invisibleElement");
		supportTeamDetailsPage.verifyElementsOfSupportTeamDetailsPage("communicationPreferencesTab");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("communicationPreferencesTab");
		LOGGER.info("Redirected to logged in support team details page");
		softAssert.assertTrue(supportTeamDetailsPage.matchTextOfSupportTeamDetailsPage("dailyEmailNotificationTitle", getTextLanguage(LanguageCode, "daas_ui", "users.details.communication_preferences.receive_daily_emails_at")), "Daily emails notification label is incorrect on logged in user's details page refer Test Case 255628/255688");
		supportTeamDetailsPage.clickByJavaScriptOnSupportTeamDetailsPage("dailyEmailNotificationEditButton");
		LOGGER.info("Clicked on edit daily email notification button");
		String timeZoneBefore = supportTeamDetailsPage.getTextOfSupportTeamDetailsPage("timezoneDropdown");
		String timeZoneCodeBefore = supportTeamDetailsPage.getTextOfSupportTeamDetailsPage("timezoneCodeDropdown");
		softAssert.assertTrue(supportTeamDetailsPage.verifyElementIsEnableOfSupportTeamDetailsPage("timezoneCodeDropdown"), "Time zone code dropdown is not present refer testcase 255688 ");
		LOGGER.info("Time zone code on daily email notification dropdown is present");
		supportTeamDetailsPage.selectRoleFromDropDownOfSupportTeamDetailsPage("timezoneDropdown", "timezoneDropdownList", timeZoneBefore);
		LOGGER.info("Updated time zone on daily email notification dropdown");
		supportTeamDetailsPage.selectRoleFromDropDownOfSupportTeamDetailsPage("timezoneCodeDropdown", "timezoneCodeDropdownList", timeZoneCodeBefore);
		LOGGER.info("Updated time zone code on daily email notification dropdown");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("dailyEmailNotificationCancelButton");
		LOGGER.info("Clicked on cancel button of daily email notification popup");
		softAssert.assertFalse(supportTeamDetailsPage.getTextOfListOnSupportTeamDetailsPage("currentTimeZone").contains(timeZone), "Cancel functionality for timezone on daily email notification popup is not working correctly refer Test Case 255628");
		softAssert.assertFalse(supportTeamDetailsPage.getTextOfListOnSupportTeamDetailsPage("currentTimeZone").contains(timeZoneCode), "Cancel functionality for timezone code on daily email notification popup is not working correctly refer Test Case 255628");
		softAssert.assertAll();
		LOGGER.info("Test Case 255628/255688/246056 verifyUserCanViewReceiveDailyTimezone executed successfully");
	}

	/**
	 * This test case validates MSP should be able to change communication preferences option Receive daily email's time
	 * @throws Exception
	 */
	@Test(priority = 22, groups = { "REGRESSION", "REGRESSION_CI", "ALL_CI","ALL" }, description = "Test Case 246069,NFR Story 247006 ; Roles ~ MSP")
	public final void verifyReceiveDailyEmailsFieldMSPReseller() throws Exception {
		login("MSP_SETTINGS_EMAIL_SA", "MSP_SETTINGS_PASSWORD_SA");
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		UserPage userPage = new UserPage(PreDefinedActions.getDriver()).getInstance();
		SupportTeamDetailsPage supportTeamDetailsPage = new SupportTeamDetailsPage(PreDefinedActions.getDriver())
				.getInstance();
		SoftAssert softAssert = new SoftAssert();
		dashboardPage.clickOnElementsOfDashboardPage("logoutButtonWithoutImage");
		softAssert.assertTrue(dashboardPage.getTextOfDashboardPage("mspSettings").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "userProfile.editProfile")), "Edit Profile text is not present on logged in user's details page refer Test Case 244088");
		dashboardPage.clickOnElementsOfDashboardPage("mspSettings");
		userPage.waitForElementsOfUserPageForinvisibile("invisibleElement");
		supportTeamDetailsPage.verifyElementsOfSupportTeamDetailsPage("communicationPreferencesTab");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("communicationPreferencesTab");
		sleeper(2000);
		supportTeamDetailsPage.scrollToTheElementsOfSupportTeamDetailsPage("dailyEmailNotificationTitle");
		softAssert.assertTrue(supportTeamDetailsPage.getTextOfSupportTeamDetailsPage("dailyEmailNotificationTitle").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui",
								"users.details.communication_preferences.receive_daily_emails_at")),
				"Daily emails notification label is incorrect on logged in user's details page");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("dailyEmailNotificationEditButton");
		softAssert.assertTrue(supportTeamDetailsPage.verifyElementIsEnableOfSupportTeamDetailsPage("timezoneDropdown"),
				"Time zone code dropdown is not present");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("timezoneDropdown");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("changeTimeZone");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("dailyEmailNotificationSaveButton");
		softAssert.assertTrue(supportTeamDetailsPage.getTextOfSupportTeamDetailsPage("communicationNotificationMessge").equals(getTextLanguage(LanguageCode, "daas_ui","user.update.success").replace("{section}",getTextLanguage(LanguageCode, "daas_ui","user.confirmation.field")).replace("{field}", getTextLanguage(LanguageCode,"daas_ui", "user.confirmation.communication"))),
				"Communication preferences tile is not present on logged in user's details page.");
		sleeper(5000);// required wait click on daily e-mail notification
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("dailyEmailNotificationEditButton");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("timezoneDropdown");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("resetTimeZone");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("dailyEmailNotificationSaveButton");
		sleeper(2000);
		supportTeamDetailsPage.waitForElementsOfSupportTeamDetailsPage("communicationNotificationMessge");
		softAssert.assertTrue(
				supportTeamDetailsPage.getTextOfSupportTeamDetailsPage("communicationNotificationMessge")
						.equals(getTextLanguage(LanguageCode, "daas_ui","user.update.success").replace("{section}",getTextLanguage(LanguageCode, "daas_ui","user.confirmation.field")).replace("{field}", getTextLanguage(LanguageCode,"daas_ui", "user.confirmation.communication"))),
				" Validated MSP user unable to change time and time zone for email communication preferences.");
		softAssert.assertAll();
		LOGGER.info("MSP user able to change time and time zone for email communication preferences.");

	}

	/**This test case validates email content when MSP changes incident status from DaaS UI
	 * Since new domain is being used hence email functionality cannt be tested via automation.
	 * @throws Exception
	 */
	@Test(priority = 23, groups = { "REGRESSION", "REGRESSION_CI", "ALL_CI","ALL" }, description = "Test Case 243172, 242663,243178,247949,245878,246678,246035,243184,243192 NFR Story 247006 ; Roles ~ MSP",enabled=false)
	public final void verifyMSPReceiveEmailNotification() throws Exception {
		login("MSP_EMAIL_NOTIFICATION_EMAIL", "MSP_EMAIL_NOTIFICATION_PASSWORD");
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		IncidentPage incidentPage = new IncidentPage(PreDefinedActions.getDriver()).getInstance();
		IncidentDetailsPage incidentDetailsPage = new IncidentDetailsPage(PreDefinedActions.getDriver()).getInstance();
		UserPage userPage = new UserPage(PreDefinedActions.getDriver()).getInstance();
		SupportTeamDetailsPage supportTeamDetailsPage = new SupportTeamDetailsPage(PreDefinedActions.getDriver())
				.getInstance();
		SoftAssert softAssert = new SoftAssert();
		sleeper(2000);
		dashboardPage.clickByJavaScriptOnDashboardPage("logoutButtonWithoutImage");
		waitForPageLoaded();
		if(dashboardPage.verifyElementsOfDashboardPage("clearCompanyFlexiGlobalFilter")){
			dashboardPage.clickOnElementsOfDashboardPage("clearCompanyFlexiGlobalFilter");
			LOGGER.info("Company selection cleared from Global drodpwn filter.");
			dashboardPage.waitForElementsOfDashboardPage("tableOverlay");
			waitForPageLoaded();
		}else{
			LOGGER.info("Company selection is not applied on Global drodpwn filter.");
		}
		dashboardPage.waitForElementsOfDashboardPage("mspSettings");
		dashboardPage.clickOnElementsOfDashboardPage("mspSettings");
		supportTeamDetailsPage.waitForElementsOfSupportTeamDetailsPage("tableOverlay");
		waitForPageLoaded();
		LOGGER.info("My Profile text is present");
		userPage.waitForElementsOfUserPageForinvisibile("invisibleElement");
		supportTeamDetailsPage.verifyElementsOfSupportTeamDetailsPage("communicationPreferencesTab");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("communicationPreferencesTab");
		softAssert.assertTrue(
				supportTeamDetailsPage.getTextOfSupportTeamDetailsPage("verifyincidentsNotificationTitle")
				.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui",
						"users.details.communication_preferences.incident_notification")),
		"Receive Incident notification label is incorrect on logged in user's details page");
		LOGGER.info("Receive Incident notification label is correct in user's details page");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("incidentsNotificationEdit");
		LOGGER.info("Redirected to logged in support team details page");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("accountListDropdownArrow");
		LOGGER.info("Clicked on incident type dropdown");
		supportTeamDetailsPage.verifyElementIsEnableOfSupportTeamDetailsPage("accountAllLocator");
		LOGGER.info("Selected all incident subtypes on account incident type dropdown");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("accountListDropdownArrow");
		supportTeamDetailsPage.waitForElementsOfSupportTeamDetailsPage(
				"ReceiveEmailNotificationWhenNewIncidentIsReportedAndOneEmailWithAllIncidentsThatAreOpenedInaDayLocator");
		supportTeamDetailsPage.verifyElementIsSelectedOfSupportTeamDetailsPage(
				"ReceiveEmailNotificationWhenNewIncidentIsReportedAndOneEmailWithAllIncidentsThatAreOpenedInaDayLocator");
		supportTeamDetailsPage.clickOnElementsOfSupportTeamDetailsPage("incidentPreferencesSaveButton");

		gotoIncidentTab();
		incidentPage.waitForElementsOfIncidentPage("tableOverlay");
		waitForPageLoaded();

		incidentPage.clearFiltersOfIncidentListPage("clearAllIncidentSearch");
		sleeper(2000);
		incidentPage.enterTextForIncidentPage("idSearchBox",getEnvironmentSpecificData(System.getProperty("environment"),"INCIDENT_ID"));
		sleeper(3000);// click on incident id
		incidentPage.waitForElementsOfIncidentPage("incidentIdSelect");
		incidentPage.waitForElementsOfIncidentPage("tableOverlay");
		incidentPage.clickByJavaScriptOnIncidentPage("incidentIdSelect");
		incidentDetailsPage.clickOnElementOfIncidentDetailsPage("incidentStatusButton");
		incidentDetailsPage.waitForElementOfIncidentDetailsPage("dropdownbutton");
		incidentDetailsPage.clickOnElementOfIncidentDetailsPage("dropdownbutton");
		ArrayList<String> dropdownTextList = incidentDetailsPage.getTextsOfIncidentDetailsPage("dropdownElements");
		String selectedText = incidentDetailsPage.getTextOfElementOnIncidentDetailsPage("dropdownHilighted")
				.toLowerCase();
		for (String dropdownText : dropdownTextList) {
			if (dropdownText.equals(selectedText)) {
				int indexofSelectedElement = dropdownTextList.indexOf(dropdownText);
				int nextIndex = (indexofSelectedElement == dropdownTextList.size() - 1) ? 0
						: indexofSelectedElement + 1;
				incidentDetailsPage.selectAnyValueFromDropdownOnIncidentDetailPage("dropdownElements", nextIndex);
				incidentDetailsPage.clickByJavaScriptOnIncidentDetailsPage("submitStatusButton");
				break;
			}
		}
		sleeper(2000);
		gotoIncidentTab();
		resetTableConfiguration();
		incidentPage.clickOnElementsOfIncidentPage("subTypeBoxBefore");
		incidentPage.waitForElementtobeClickableOfIncidentPage("hpTechpulseSubtype");
		incidentPage.clickOnElementsOfIncidentPage("hpTechpulseSubtype");
		sleeper(2000);
		incidentPage.waitForElementsOfIncidentPage("hpTechPulseIncidentClick");
		incidentPage.clickByJavaScriptOnIncidentPage("hpTechPulseIncidentClick");
		incidentDetailsPage.waitForElementOfIncidentDetailsPage("tableOverlay");
		incidentDetailsPage.clickOnElementOfIncidentDetailsPage("incidentStatusButton");
		incidentDetailsPage.clickOnElementOfIncidentDetailsPage("dropdownbutton");
		incidentDetailsPage.clickOnElementOfIncidentDetailsPage("clickDissmissButton");
		incidentDetailsPage.clickOnElementOfIncidentDetailsPage("submitStatusButton");
		String expectedMailContent = getTextLanguage(LanguageCode, "idm", "sign_in_changed.title") + " "
				+ getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties",
						"com.hp.mpi.icm.title.snowconnectionfailure")
				+ " "
				+ getTextLanguage(
						LanguageCode, "daas_ui", "incident.details.problem_code").replace("{problemCode}", "10005")
				+ " "+ getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties",
						"com.hp.mpi.icm.description.snowconnectionfailure").replace(" Proactive Management ", " ")
								.replace("<{0}>", getEnvironmentSpecificData(environment, "INSTANCE_URL"))
				+ " " + getTextLanguage(LanguageCode, "daas_ui", "users.list.label.company")+": "
				+ getEnvironmentSpecificData(environment, "COMPANY_NAME_EMAIL_NOTIFY") + " "
				+ getTextLanguage(LanguageCode, "daas_ui", "incidents.occurred_at") + ":\n"
				+ generateCustomDate().substring(0, 10) + " " + "See "
				+ getTextLanguage(LanguageCode, "daas_ui", "companies.details.section.details") + " "
				+getTextLanguage(LanguageCode, "lhserver", "global.copyright").replace("%{current_year}", "2019");
		String actualMailContent = supportTeamDetailsPage.verifyIncidentEmailContentforAddedSpace(environment, getCredentials(environment, "MSP_EMAIL_NOTIFICATION_EMAIL"),
				"New HP TechPulse Incident Integration Incident created at", true);
		sleeper(2000);
		softAssert.assertTrue(actualMailContent.equalsIgnoreCase(expectedMailContent),"Mail content does not match");
		sleeper(2000);
		softAssert.assertAll();
		LOGGER.info("Validated msp user able to receive incident email successfully");
	}
	
	/**
	 * This Test Case will verify the Switch Account functionality
	 * 
	 * @throws Exception
	 */
	@Test(priority = 24, groups = { "REGRESSION", "REGRESSION_CI" }, description = "NFR 522924 , TestCase = 528451")
	public final void verifySwitchFunctionality() throws Exception {
		login("SWITCH_ADMIN_EMAIL","SWITCH_ADMIN_PASSWORD");
		SoftAssert softAssert = new SoftAssert();
		DashboardPage dashboardPage = new DashboardPage(PreDefinedActions.getDriver()).getInstance();
		dashboardPage.clickOnElementsOfDashboardPage("userProfileButton");
		dashboardPage.waitForElementsOfDashboardPage("switchAccount");
		dashboardPage.clickOnElementsOfDashboardPage("switchAccount");
		softAssert.assertTrue(dashboardPage.matchTextOfDashboardPage("switchAccountPopupHeader", getTextLanguage(LanguageCode, "daas_ui", "users.switchAccount")), "Switch Account title on popup is incorrect");
		dashboardPage.verifySwitchAccount(LanguageCode);
		softAssert.assertAll();
		LOGGER.info("Switch account functionality verified successfully");
	}
}