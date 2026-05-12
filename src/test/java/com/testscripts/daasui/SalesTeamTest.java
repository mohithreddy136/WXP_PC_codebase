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
import com.daasui.constants.SalesVariables;
import com.daasui.pages.SalesTeamDetailsPage;
import com.daasui.pages.SalesTeamPage;

/**
 * This class implements test cases related to sales team list and details page
 */
public class SalesTeamTest extends CommonTest {
	private static Logger LOGGER = LogManager.getLogger(SalesTeamTest.class);
	public int activePageNumber = 0, firstPageNumber = 0, lastPageNumber = 0, selectedOption = 0, totalCount = 0;
	public boolean previousButtonStatus = false, nextButtonStatus = false;
	ArrayList<String> salesTeamMemberInfo = new ArrayList<String>();
	public static final String salesTeamUser = getEnvironmentSpecificData(System.getProperty("environment"), "SALES_TEAM_TEST_USER_EMAIL");

	// This test case validates title on sales team page
	@Test(priority = 1, groups = { "REGRESSION", "REGRESSION_CI", "SMOKE", "ALL_CI", "ALL" }, description = "NFR 242588")
	public final void verifySalesTeamTitle() {
		try {
			login("RESELLER_HELP_AND_SUPPORT_EMAIL", "RESELLER_HELP_AND_SUPPORT_EMAIL_PASSWORD");
			gotoPartnerTeamTab();
			LOGGER.info("Redirected to sales team list page");
			SalesTeamPage salesTeamPage = new SalesTeamPage(PreDefinedActions.getDriver()).getInstance();

			if (toggleVerification(DashboardVariables.GLOBAL_FILTER_TOGGLE, getCredentials(environment, "RESELLER_PARTNERTEAM_EMAIL"))) {
				salesTeamPage.waitForElementsOfSalesTeamPage("salesTeamTitleOnBreadcrumbs");
				Assert.assertTrue(salesTeamPage.matchTextOfSalesTeamPage("salesTeamTitleOnBreadcrumbs", getTextLanguage(LanguageCode, "daas_ui", "pagetitle.partnerTeam")), "Sales team title doesn't match");
				LOGGER.info("Sales team title is matched");
			}else{
			Assert.assertTrue(salesTeamPage.matchTextOfSalesTeamPage("salesTeamTitle", getTextLanguage(LanguageCode, "daas_ui", "pagetitle.partnerTeam")), "Sales team title doesn't match");
			LOGGER.info("Sales team title is matched");
			}
		} catch (Exception e) {
			Assert.fail("Exception occured in method verifySalesTeamTitle " + e.getMessage());
		}
	}


	// This test case validates add sales team member functionality
	@Test(priority = 2, groups = { "REGRESSION", "REGRESSION_CI", "SMOKE", "ALL_CI", "ALL", "STABILIZATION_STAGING" }, description = "NFR 242588")
	public final void verifyAddFunctionality() throws Exception {
		login("RESELLER_HELP_AND_SUPPORT_EMAIL", "RESELLER_HELP_AND_SUPPORT_EMAIL_PASSWORD");
		SalesTeamPage salesTeamPage = new SalesTeamPage(PreDefinedActions.getDriver()).getInstance();
		String email = generateRandomString(11) + "@mailinator.com";
		String currentUrl, salesTeamMemberID;

		try {
			SoftAssert softAssert = new SoftAssert();
			gotoPartnerTeamTab();
			LOGGER.info("Redirected to sales team list page");
			resetTableConfiguration();
			waitForPageLoaded();

			// Test Case 1 - This test case validates cancel functionality on add sales team member popup
			salesTeamPage.waitUntilElementIsInvisibleOfSalesTeamPage("tableOverlay");
			salesTeamPage.clickOnElementsOfSalesTeamPage("salesAddButton");

			softAssert.assertTrue(salesTeamPage.matchTextOfSalesTeamPage("roleTitle", getTextLanguage(LanguageCode, "daas_ui", "users.details.role")), "Role field title didn't matched on add Sales team member popup");
			softAssert.assertTrue(salesTeamPage.verifyElementIsEnableOfSalesTeamPage("roleDropDown"), "Role dropdown is not enabled");
			
			salesTeamPage.matchTextOfSalesTeamPage("firstNameAddMemberLabel", getTextLanguage(LanguageCode, "daas_ui", "support_team.details.first_name"));
			salesTeamPage.enterTextForSalesTeamPage("firstNameAddMember", SalesVariables.FIRST_NAME);

			salesTeamPage.matchTextOfSalesTeamPage("lastNameAddMemberLabel", getTextLanguage(LanguageCode, "daas_ui", "support_team.details.last_name"));
			salesTeamPage.enterTextForSalesTeamPage("lastNameAddMember", SalesVariables.LAST_NAME);

			salesTeamPage.matchTextOfSalesTeamPage("emailAddMemberLabel", getTextLanguage(LanguageCode, "daas_ui", "support_team.details.email"));
			salesTeamPage.enterTextForSalesTeamPage("emailAddMember", email);

			salesTeamPage.matchTextOfSalesTeamPage("mobileAddMemberLabel", getTextLanguage(LanguageCode, "daas_ui", "support_team.details.phone_mobile"));
			salesTeamPage.enterTextForSalesTeamPage("mobileAddMember", SalesVariables.MOBILE_PHONE);

			salesTeamPage.clickOnElementsOfSalesTeamPage("cancelAddMember");
			LOGGER.info("Verified cancel functionality on add sales team member popup");
			softAssert.assertFalse(salesTeamPage.verifyElementsOfSalesTeamPage("toastNotification"), "Cancel functionality on add sales team member popup does not work correctly");

			// Test Case 2 - This test case validates the add functionality of sales team members
			salesTeamPage.clickOnElementsOfSalesTeamPage("salesAddButton");
			salesTeamPage.enterTextForSalesTeamPage("firstNameAddMember", SalesVariables.FIRST_NAME);
			salesTeamPage.enterTextForSalesTeamPage("lastNameAddMember", SalesVariables.LAST_NAME);
			salesTeamPage.enterTextForSalesTeamPage("emailAddMember", email);
			salesTeamPage.enterTextForSalesTeamPage("mobileAddMember", SalesVariables.MOBILE_PHONE);
			salesTeamPage.clickOnElementsOfSalesTeamPage("addMember");
			LOGGER.info("Verified add functionality on add sales team member popup");
			salesTeamPage.waitForElementsOfSalesTeamPage("toastNotification");
			String notificationMessage = salesTeamPage.getTextOfSalesTeamPage("toastNotification");
			softAssert.assertTrue(notificationMessage.equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "user.created.success")), "Incorrect notification message is generated after adding sales team member");
			salesTeamPage.waitUntilElementIsInvisibleOfSalesTeamPage("tableOverlay");
			salesTeamPage.enterTextForSalesTeamPage("emailTextbox", email);
			sleeper(2000);
			softAssert.assertFalse(salesTeamPage.verifyElementsOfSalesTeamPage("noItemsDisplayText"), "Add functionality for sales team member does not work correctly");

			softAssert.assertAll();
			LOGGER.info("All test cases of add functionality for sales team page have passed");
		} catch (Exception e) {
			Assert.fail("Exception occured in method verifyAddFunctionality " + e.getMessage());
		} finally {
			salesTeamPage.clickOnElementsOfSalesTeamPage("userFirstIdEmail");
			currentUrl = salesTeamPage.getUrlOfCurrentPage();
			salesTeamMemberID = currentUrl.substring(currentUrl.lastIndexOf("/") + 1);
			salesTeamPage.removeSalesTeamMember(environment, salesTeamMemberID, getEnvironment(environment));
		}
	}

	// This test case validates all filters' functionality on sales team list page
	@Test(priority = 4, groups = { "REGRESSION", "REGRESSION_CI", "SMOKE", "ALL_CI", "ALL" }, description = "NFR 242588", enabled = false)
	public final void verifyFilterFunctionality() {
		try {
			SoftAssert softAssert = new SoftAssert();
			login("RESELLER_PARTNERTEAM_EMAIL", "RESELLER_PARTNERTEAM_PASSWORD");
			gotoPartnerTeamTab();
			LOGGER.info("Redirected to sales team list page");
			SalesTeamPage salesTeamPage = new SalesTeamPage(PreDefinedActions.getDriver()).getInstance();

			resetTableConfiguration();
			salesTeamPage.waitForElementsOfSalesTeamPage("tableOverlay");

			// Test Case 1 - This test case validates if the filter functionality is working properly for the searchbox on name column
			softAssert.assertTrue(salesTeamPage.verifySearchValueOnSalesTeam(LanguageCode, "nameTextbox", SalesVariables.INCORRECT_SEARCH_STRING, "noItemsDisplayText", "nameList"), "Incorrect search not working for Name column");
			softAssert.assertTrue(salesTeamPage.verifySearchValueOnSalesTeam(LanguageCode, "nameTextbox", SalesVariables.NAME, "noItemsDisplayText", "nameList"), "Search not working for Name column");

			/*
			 * // Test Case 2 - This test case validates if the filter functionality is working properly for the searchbox on email column
			 * softAssert.assertTrue(salesTeamPage.verifySearchValueOnSalesTeam(LanguageCode, "emailTextbox", SalesVariables.INCORRECT_SEARCH_STRING, "noItemsDisplayText", "emailList"),
			 * "Incorrect search not working for Email column"); softAssert.assertTrue(salesTeamPage.verifySearchValueOnSalesTeam(LanguageCode, "emailTextbox", SalesVariables.EMAIL_SEARCH,
			 * "noItemsDisplayText", "emailList"), "Search not working for Email column");
			 */

			// Test Case 3 - This test case validates if the filter functionality is working properly for the dropdown on role column
			salesTeamPage.clickOnElementsOfSalesTeamPage("rolesFilter");
			softAssert.assertTrue(salesTeamPage.verifyFilterSingleSelect(LanguageCode, "dropdowncheckboxes", "dropdownelementlistlabels", "roleList", "noItemsDisplayText"), "Listbox not working for roles");
			salesTeamPage.clickOnElementsOfSalesTeamPage("clearFilter");
			salesTeamPage.waitForElementsOfSalesTeamPage("tableOverlay");

			/*
			 * // Test Case 4 - This test case validates if the filter functionality is working properly for the dropdown on status column
			 * salesTeamPage.clickOnElementsOfSalesTeamPage("statusFilter"); softAssert.assertTrue(salesTeamPage.verifyFilterSingleSelect(LanguageCode, "dropdowncheckboxes",
			 * "dropdownelementlistlabels", "statusList", "noItemsDisplayText"), "Listbox not working for roles"); salesTeamPage.clickOnElementsOfSalesTeamPage("clearFilter");
			 * salesTeamPage.waitForElementsOfSalesTeamPage("tableOverlay");
			 */

			// Test Case 5 - This test case validates if the filter functionality is working properly for the searchbox on mobile phone column
			softAssert.assertTrue(salesTeamPage.verifySearchValueOnSalesTeam(LanguageCode, "mobilePhoneTextbox", SalesVariables.INCORRECT_SEARCH_STRING, "noItemsDisplayText", "mobilePhoneList"), "Incorrect search not working for mobile phone column");
			softAssert.assertTrue(salesTeamPage.verifySearchValueOnSalesTeam(LanguageCode, "mobilePhoneTextbox", SalesVariables.MOBILE_PHONE_SEARCH, "noItemsDisplayText", "mobilePhoneList"), "Search not working for mobile phone column");

			/*
			 * // Test Case 6 - This test case validates if the filter functionality is working properly for the searchbox on office phone column
			 * softAssert.assertTrue(salesTeamPage.verifySearchValueOnSalesTeam(LanguageCode, "officePhoneTextbox", SalesVariables.INCORRECT_SEARCH_STRING, "noItemsDisplayText",
			 * "officePhoneList"), "Incorrect search not working for office phone column"); softAssert.assertTrue(salesTeamPage.verifySearchValueOnSalesTeam(LanguageCode, "officePhoneTextbox",
			 * SalesVariables.OFFICE_PHONE_SEARCH, "noItemsDisplayText", "officePhoneList"), "Search not working for office phone column");
			 */

			softAssert.assertAll();
			LOGGER.info("All test cases of filter functionality for sales team page have passed");
		} catch (Exception e) {
			Assert.fail("Exception occured in method verifyFilterFunctionality " + e.getMessage());
		}
	}

	// This test case validates pagination functionality on sales team list page
	@Test(priority = 5, groups = { "REGRESSION", "REGRESSION_CI", "SMOKE", "ALL_CI", "ALL" }, description = "NFR 242588", enabled = false)
	public final void verifyPaginationOnSalesTeamPage() {
		try {
			SoftAssert softAssert = new SoftAssert();
			login("RESELLER_PARTNERTEAM_EMAIL", "RESELLER_PARTNERTEAM_PASSWORD");
			gotoPartnerTeamTab();
			LOGGER.info("Redirected to sales team list page");
			SalesTeamPage salesTeamPage = new SalesTeamPage(PreDefinedActions.getDriver()).getInstance();
			salesTeamPage.waitUntilElementIsInvisibleOfSalesTeamPage("tableOverlay");
			softAssert.assertTrue(salesTeamPage.verifyElementIsEnableOfSalesTeamPage("paginationDropdownMenu"), "Pagination Dropdown not available");
			softAssert.assertTrue(salesTeamPage.verifyElementIsClickableOfSalesTeamPage("paginationDropdownMenu"), "Pagination Dropdown not clickable");
			softAssert.assertTrue(salesTeamPage.waitForElementsOfSalesTeamPage("navigationItemDiv"), "Navigation Item are not available");
			getPaginationInfo();
			LOGGER.info("get Pagination Information ");
			softAssert.assertTrue(salesTeamPage.verifyElementIsEnableOfSalesTeamPage("navigationItemActivePage"), "The active page navigation link is not disabled");
			softAssert.assertTrue(salesTeamPage.verifyTotalPageCountChangeAsSelectedOption(selectedOption, totalCount, lastPageNumber), "Total page count not change with selected option");
			softAssert.assertTrue(salesTeamPage.verifyButtonStatusofPagination(activePageNumber, firstPageNumber, lastPageNumber, totalCount, previousButtonStatus, nextButtonStatus), "Button visibility status not as per expectation");
			softAssert.assertTrue(verifySelectedOptionForNewSelection(selectedOption, 50), "Used already Chosen option ");
			if (changeSelectedOption(totalCount, 50)) {
				salesTeamPage.selectElementFromDropDownOfSalesTeamPage("paginationDropdownMenu", "paginationDropdownOptionList", "50");
				LOGGER.info("Change Selected option as 50");
				softAssert.assertTrue(salesTeamPage.verifyTotalPageCountChangeAsSelectedOption(selectedOption, totalCount, lastPageNumber), "Total page count not change with selected option");
				softAssert.assertTrue(salesTeamPage.verifyButtonStatusofPagination(activePageNumber, firstPageNumber, lastPageNumber, totalCount, previousButtonStatus, nextButtonStatus), "Button visibility status not as per expectation");
			} else {
				LOGGER.info("Selected user has less than 50 records");
			}
			getPaginationInfo();
			if (salesTeamPage.canClickNext(activePageNumber, lastPageNumber, nextButtonStatus)) {
				softAssert.assertTrue(salesTeamPage.getButtonEnabilityStatus("navigationItemNext"), "Naviagtion next button is not enable");
				salesTeamPage.clickOnElementsOfSalesTeamPage("navigationItemNext");
				getPaginationInfo();
				if (salesTeamPage.canClickPrevious(activePageNumber, firstPageNumber, previousButtonStatus)) {
					softAssert.assertTrue(salesTeamPage.getButtonEnabilityStatus("navigationItemPrevious"), "Naviagtion previous button is not enable");
					salesTeamPage.clickOnElementsOfSalesTeamPage("navigationItemPrevious");
				} else {
					LOGGER.info("Previous button is disabled");
				}
			} else if (salesTeamPage.canClickPrevious(activePageNumber, firstPageNumber, previousButtonStatus)) {
				softAssert.assertTrue(salesTeamPage.getButtonEnabilityStatus("navigationItemPrevious"), "Naviagtion previous button is not enable");
				salesTeamPage.clickOnElementsOfSalesTeamPage("navigationItemPrevious");
				getPaginationInfo();
				if (salesTeamPage.canClickNext(activePageNumber, lastPageNumber, nextButtonStatus)) {
					softAssert.assertTrue(salesTeamPage.getButtonEnabilityStatus("navigationItemNext"), "Naviagtion next button is not enable");
					salesTeamPage.clickOnElementsOfSalesTeamPage("navigationItemNext");
				} else {
					LOGGER.info("Next button is disabled");
				}
			} else {
				LOGGER.info("Previous and Next button both are disabled");
			}
			softAssert.assertAll();
			LOGGER.info("Verification of sales team page pagination done successfully ");
		} catch (Exception e) {
			Assert.fail("Exception occured in method verifyPaginationOnSupportTeamPage " + e.getMessage());
		}
	}

	// This test case validates details tile present on sales team details page
	@Test(priority = 6, groups = { "REGRESSION", "REGRESSION_CI", "SMOKE", "ALL_CI", "ALL" }, description = "NFR 242587")
	public final void verifyDetailsTile() {
		try {
			SoftAssert softAssert = new SoftAssert();
			login("RESELLER_HELP_AND_SUPPORT_EMAIL", "RESELLER_HELP_AND_SUPPORT_EMAIL_PASSWORD");
			gotoPartnerTeamTab();
			LOGGER.info("Redirected to sales team list page");
			SalesTeamPage salesTeamPage = new SalesTeamPage(PreDefinedActions.getDriver()).getInstance();

			SalesTeamDetailsPage salesTeamDetailsPage = new SalesTeamDetailsPage(PreDefinedActions.getDriver()).getInstance();

			resetTableConfiguration();
			waitForPageLoaded();
			salesTeamPage.enterTextForSalesTeamPage("emailTextbox", getCredentials(environment, "RESELLER_HELP_AND_SUPPORT_EMAIL"));
			salesTeamPage.waitUntilElementIsInvisibleOfSalesTeamPage("tableOverlay");
			salesTeamPage.scrollOnSalesTeamPage("officePhoneText");
			salesTeamMemberInfo = salesTeamPage.getSalesTeamMemberInfo();
			salesTeamPage.clickByJavaScriptOnSalesTeamPage("firstIdName");
			LOGGER.info("Redirected to sales team details page");
			salesTeamDetailsPage.verifyElementsOfSalesTeamDetailsPage("detailsTitle");

			salesTeamDetailsPage.waitUntilElementIsInvisibleOfSalesTeamDetailsPage("tableOverlay");
			// Test Case 1 - This test case validates if the name of member on list and details page is the same
			softAssert.assertTrue(salesTeamDetailsPage.getTextOfSalesTeamDetailsPage("nameLabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "support_team.details.name")), "Name field for sales team member is not present on details tile");
				softAssert.assertTrue(salesTeamDetailsPage.getTextOfSalesTeamDetailsPage("nameValue").equalsIgnoreCase(salesTeamMemberInfo.get(1)), "Name of sales team member on details page does not match with list page");
			
			// Test Case 2 - This test case validates if the email of member on list and details page is the same
			softAssert.assertTrue(salesTeamDetailsPage.getTextOfSalesTeamDetailsPage("emailLabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "users.details.email")), "Email field for sales team member is not present on details tile");
				softAssert.assertTrue(salesTeamDetailsPage.getTextOfSalesTeamDetailsPage("emailValue").equalsIgnoreCase(salesTeamMemberInfo.get(0)), "Email of sales team member on details page does not match with list page");
			
				// Test Case 3 - This test case validates if the mobile phone of member on list and details page is the same
			softAssert.assertTrue(salesTeamDetailsPage.getTextOfSalesTeamDetailsPage("mobileLabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "users.details.mobile_phone")), "Mobile phone number field for sales team member is not present on details tile");
				softAssert.assertTrue(salesTeamDetailsPage.getTextOfSalesTeamDetailsPage("mobileValue").equalsIgnoreCase(salesTeamMemberInfo.get(4)), "Mobile phone of sales team member on details page does not match with list page");
			
			// Test Case 4 - This test case validates if the office phone of member on list and details page is the same
			softAssert.assertTrue(salesTeamDetailsPage.getTextOfSalesTeamDetailsPage("officeLabel").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "users.details.office_phone")), "Office phone number field for sales team member is not present on details tile");
				softAssert.assertTrue(salesTeamDetailsPage.getTextOfSalesTeamDetailsPage("officeValue").equalsIgnoreCase(salesTeamMemberInfo.get(5)), "Office phone of sales team member on details page does not match with list page");	
			
			softAssert.assertAll();
			LOGGER.info("All test cases of verifyDetailsTile on sales team details page have passed successfully");
		} catch (Exception e) {
			Assert.fail("Exception occured in method verifyDetailsTile " + e.getMessage());
		}
	}

	
	/**
	 * This Test Case will validate the Accounts Tile on User Detail Page
	 * @throws Exception
	 */
	@Test(priority = 7, groups = { "REGRESSION", "REGRESSION_CI" }, description = "NFR 242587, NFR 522924 ,TestCase=528536")
	public final void verifyAccountsTile() throws Exception {
		try {
			login("RESELLER_HELP_AND_SUPPORT_EMAIL", "RESELLER_HELP_AND_SUPPORT_EMAIL_PASSWORD");
			SoftAssert softAssert = new SoftAssert();
			gotoPartnerTeamTab();
			LOGGER.info("Redirected to sales team list page");
			SalesTeamPage salesTeamPage = new SalesTeamPage(PreDefinedActions.getDriver()).getInstance();

			SalesTeamDetailsPage salesTeamDetailsPage = new SalesTeamDetailsPage(PreDefinedActions.getDriver()).getInstance();
			
			String selectedValue = null;
			resetTableConfiguration();
			waitForPageLoaded();
			salesTeamPage.waitForElementsOfSalesTeamPage("tableOverlay");
			salesTeamPage.enterTextForSalesTeamPage("emailTextbox", salesTeamUser);
			salesTeamPage.waitForElementsOfSalesTeamPage("tableOverlay");
			salesTeamPage.clickByJavaScriptOnSalesTeamPage("firstIdName");
			LOGGER.info("Redirected to sales team details page");

			waitForPageLoaded();
			salesTeamDetailsPage.waitForElementsOfSalesTeamDetailsPage("roleHeader");
			// Test Case 1 - This test case validates if role of sales team member on details page matches with that of the list page
			softAssert.assertTrue(salesTeamDetailsPage.getTextOfSalesTeamDetailsPage("roleHeader").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "users.details.accounts")), "Title on Accounts tile is incorrect");
			softAssert.assertTrue(salesTeamDetailsPage.matchTextOfSalesTeamDetailsPage("roleMessage", getTextLanguage(LanguageCode, "daas_ui", "users.details.section.role_assignment_description")), "Description on Account tile is incorrect");
			salesTeamDetailsPage.clickOnElementsOfSalesTeamDetailsPage("roleButton");
			softAssert.assertTrue(salesTeamDetailsPage.matchTextOfSalesTeamDetailsPage("rolePopupHeader", getTextLanguage(LanguageCode, "daas_ui", "users.details.roles.modal.title")), "Header of Edit Role popup is incorrect");
			salesTeamDetailsPage.clickOnElementsOfSalesTeamDetailsPage("rolesCancel");
			LOGGER.info("Verified cancel functionality on Accounts tile of sales team details page");
			sleeper(3000);//Added sleeper as role list takes time in loading
			
			// Test Case 2 - This test case validates save functionality on role assignemnt popup
			salesTeamDetailsPage.clickOnElementsOfSalesTeamDetailsPage("roleButton");
			sleeper(1000);
			salesTeamDetailsPage.selectMultipleRolesOnSalesTeamDetailsPage();
			LOGGER.info("Multiple roles selected");
			salesTeamDetailsPage.clickOnElementsOfSalesTeamDetailsPage("rolesSave");
			LOGGER.info("Verified role assignment functionality on sales team details page");
			salesTeamDetailsPage.waitForElementsOfSalesTeamDetailsPage("toastNotificationAccountTile");
			softAssert.assertTrue(salesTeamDetailsPage.matchTextOfSalesTeamDetailsPage("toastNotificationAccountTile", getTextLanguage(LanguageCode, "daas_ui", "user.update.success").replace("{section}", "User's").replace("{field}", "roles")), "Save functionality on Edit Role popup is not working");
			sleeper(2000);
			selectedValue=salesTeamDetailsPage.getElementOfSalesTeamPage("roleValue").getText().replaceAll("/", ",");
			
			// Test Case 3 - This test case validates if the updated role is reflected back on sales team list page
			navigateToBack();
			LOGGER.info("Redirected to sales team list page");
			waitForPageLoaded();
			salesTeamPage.waitForElementsOfSalesTeamPage("tableOverlay");
			softAssert.assertTrue(salesTeamPage.getTextOfSalesTeamPage("firstIdRole").equalsIgnoreCase(selectedValue), "Updated role is not reflected back on sales team list page");
			softAssert.assertAll();
			LOGGER.info("Verification of Accounts tile on sales team details page done successfully");
			
		} catch (Exception e) {
			Assert.fail("Exception occured in method verifyAccountsTile " + e.getMessage());
		}
	}

	// This test case validates identity tile present on sales team details page
	@Test(priority = 8, groups = { "REGRESSION", "REGRESSION_CI", "SMOKE", "ALL_CI", "ALL" }, description = "NFR 242587",enabled=false)
	public final void verifyIdentityTile() {
		try {
			SoftAssert softAssert = new SoftAssert();
			login("RESELLER_PARTNERTEAM_EMAIL", "RESELLER_PARTNERTEAM_PASSWORD");
			gotoPartnerTeamTab();
			LOGGER.info("Redirected to sales team list page");
			SalesTeamPage salesTeamPage = new SalesTeamPage(PreDefinedActions.getDriver()).getInstance();

			SalesTeamDetailsPage salesTeamDetailsPage = new SalesTeamDetailsPage(PreDefinedActions.getDriver()).getInstance();

			resetTableConfiguration();
			waitForPageLoaded();
			salesTeamPage.waitForElementsOfSalesTeamPage("tableOverlay");
			salesTeamPage.enterTextForSalesTeamPage("emailTextbox", SalesVariables.SALES_TEAM_TEST_USER_EMAIL);
			salesTeamPage.waitForElementsOfSalesTeamPage("tableOverlay");
			salesTeamPage.scrollOnSalesTeamPage("txtMobilePhone");
			salesTeamMemberInfo = salesTeamPage.getSalesTeamMemberInfo();
			salesTeamPage.clickByJavaScriptOnSalesTeamPage("firstIdName");
			LOGGER.info("Redirected to sales team details page");

			salesTeamDetailsPage.waitUntilElementIsInvisibleOfSalesTeamDetailsPage("tableOverlay");
			// Test case 1 - This test case validates sales team member details on identity tile
				softAssert.assertTrue(salesTeamDetailsPage.getTextOfSalesTeamDetailsPage("identityName").equalsIgnoreCase(salesTeamMemberInfo.get(0)), "Name on identity tile is incorrect");	
			
				softAssert.assertTrue(salesTeamDetailsPage.getTextOfSalesTeamDetailsPage("identityEmail").equalsIgnoreCase(salesTeamMemberInfo.get(1)), "Email on identity tile is incorrect");
		
			softAssert.assertTrue(salesTeamDetailsPage.matchTextOfSalesTeamDetailsPage("identityEmail", salesTeamMemberInfo.get(2)), "Email on identity tile is incorrect");
			if (!salesTeamDetailsPage.getElementOfSalesTeamPage("identityPhone").getText().equals("")) {
				
					softAssert.assertTrue(salesTeamDetailsPage.getTextOfSalesTeamDetailsPage("identityPhone").equalsIgnoreCase(salesTeamMemberInfo.get(4)) || salesTeamDetailsPage.getTextOfSalesTeamDetailsPage("identityPhone").equalsIgnoreCase(salesTeamMemberInfo.get(5)), "Phone on identity tile is incorrect");	
			
			}
				softAssert.assertTrue(salesTeamDetailsPage.getTextOfSalesTeamDetailsPage("identityStatus").equalsIgnoreCase(salesTeamMemberInfo.get(3)), "Status on identity tile is incorrect");	
			
			LOGGER.info("Verified sales team member's details on identity tile");

			softAssert.assertAll();
			LOGGER.info("All test cases of verifyIdentityTile on sales team page have passed successfully");
		} catch (Exception e) {
			Assert.fail("Exception occured in method verifyIdentityTile " + e.getMessage());
		}
	}

	// This test case validates communication preferences tile present on edit user's profile page
	@Test(priority = 9, groups = { "REGRESSION", "REGRESSION_CI", "SMOKE", "ALL_CI", "ALL" }, description = "NFR 242589", enabled = false)
	public final void verifyCommunicationPreferencesTile() {
		try {
			SoftAssert softAssert = new SoftAssert();
			login("RESELLER_NEW_UI_Companies_EMAIL_COMPANIES", "RESELLER_NEW_UI_Companies_PASSWORD_COMPANIES");
			gotoPartnerTeamTab();
			LOGGER.info("Redirected to sales team list page");
			SalesTeamPage salesTeamPage = new SalesTeamPage(PreDefinedActions.getDriver()).getInstance();

			SalesTeamDetailsPage salesTeamDetailsPage = new SalesTeamDetailsPage(PreDefinedActions.getDriver()).getInstance();

			ArrayList<String> accountSubTypesOnIncidentNotificationPreferencesPopup = new ArrayList<String>(
					Arrays.asList(getTextLanguage(LanguageCode, "daas_ui", "global.all"), getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.subtype.aggregation"), getTextLanguage(LanguageCode, "daas_ui", "download.techPulse_inc"), getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.subtype.licensing"), getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.type.unassigned")));

			ArrayList<String> hardwareChangeSubTypesOnIncidentNotificationPreferencesPopup = new ArrayList<String>(
					Arrays.asList(getTextLanguage(LanguageCode, "daas_ui", "global.all"), getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.subtype.aggregation"), getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.subtype.hdd"), getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.subtype.memory"), getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.type.unassigned")));

			ArrayList<String> hardwareHealthSubTypesOnIncidentNotificationPreferencesPopup = new ArrayList<String>(Arrays.asList(getTextLanguage(LanguageCode, "daas_ui", "global.all"), getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.subtype.aggregation"), getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.subtype.batterydetected"),
					getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.subtype.batterypredictive1"), getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.subtype.hddpredictive"), getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.subtype.hddevent"), getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.subtype.hddstore"),
					getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.subtype.systemthermal"), getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.type.unassigned")));

			ArrayList<String> osHealthSubTypesOnIncidentNotificationPreferencesPopup = new ArrayList<String>(Arrays.asList(getTextLanguage(LanguageCode, "daas_ui", "global.all"), getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.subtype.aggregation"), getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.subtype.companybsod"),
					getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.subtype.cpuhigh"), getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.subtype.memoryhigh"), getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.subtype.osbsod"), getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.subtype.oscrash"), getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.type.unassigned")));

			ArrayList<String> securitySubTypesOnIncidentNotificationPreferencesPopup = new ArrayList<String>(Arrays.asList(getTextLanguage(LanguageCode, "daas_ui", "global.all"), getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.subtype.aggregation"), getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.subtype.antivirus"), getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.subtype.firewall"),
					getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.subtype.heartbeat"), getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.subtype.bromium1"), getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.type.unassigned")));

			ArrayList<String> softwareHealthSubTypesOnIncidentNotificationPreferencesPopup = new ArrayList<String>(Arrays.asList(getTextLanguage(LanguageCode, "daas_ui", "global.all"), getTextLanguage(LanguageCode, "MPI-Incident-management-service-backend-Properties", "com.hp.mpi.icm.subtype.aggregation"), getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.subtype.requiredapps"), getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.type.unassigned")));

			ArrayList<String> unassignedSubTypesOnIncidentNotificationPreferencesPopup = new ArrayList<String>(Arrays.asList(getTextLanguage(LanguageCode, "daas_ui", "global.all"), getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.type.unassigned")));

			String selectedAllIncidentTypes = salesTeamDetailsPage.getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.type.account") + " (" + getTextLanguage(LanguageCode, "daas_ui", "global.all") + "), " + getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.type.hwchange") + " (" + getTextLanguage(LanguageCode, "daas_ui", "global.all") + "), " + getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.type.hardwarehealth") + " ("
					+ getTextLanguage(LanguageCode, "daas_ui", "global.all") + "), " + getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.type.oshealth") + " (" + getTextLanguage(LanguageCode, "daas_ui", "global.all") + "), " + getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.type.security") + " (" + getTextLanguage(LanguageCode, "daas_ui", "global.all") + "), " + getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.type.softwarehealth") + " ("
					+ getTextLanguage(LanguageCode, "daas_ui", "global.all") + "), " + getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.type.unassigned") + " (" + getTextLanguage(LanguageCode, "daas_ui", "global.all") + ")";

			String selectedSpecificIncidentTypes = salesTeamDetailsPage.getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.type.account") + " (" + getTextLanguage(LanguageCode, "lhserver", "settings.preferences.incident_config.label.custom") + "), " + getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.type.hwchange") + " (" + getTextLanguage(LanguageCode, "lhserver", "settings.preferences.incident_config.label.custom") + "), "
					+ getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.type.hardwarehealth") + " (" + getTextLanguage(LanguageCode, "lhserver", "settings.preferences.incident_config.label.custom") + "), " + getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.type.oshealth") + " (" + getTextLanguage(LanguageCode, "lhserver", "settings.preferences.incident_config.label.custom") + "), "
					+ getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.type.security") + " (" + getTextLanguage(LanguageCode, "lhserver", "settings.preferences.incident_config.label.custom") + "), " + getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.type.softwarehealth") + " (" + getTextLanguage(LanguageCode, "lhserver", "settings.preferences.incident_config.label.custom") + "), "
					+ getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.type.unassigned") + " (" + getTextLanguage(LanguageCode, "daas_ui", "global.all") + ")", timeZone = null, timeZoneCode = null;

			resetTableConfiguration();
			waitForPageLoaded();
			salesTeamPage.enterTextForSalesTeamPage("emailTextbox", SalesVariables.RESELLER_EMAIL);
			salesTeamPage.waitForElementsOfSalesTeamPage("tableOverlay");
			salesTeamPage.clickOnElementsOfSalesTeamPage("firstIdName");
			salesTeamPage.waitForElementsOfSalesTeamPage("tableOverlay");
			salesTeamDetailsPage.clickOnElementsOfSalesTeamDetailsPage("communicationPreferencesTab");

			LOGGER.info("Redirected to logged in user's details page");
			softAssert.assertTrue(salesTeamDetailsPage.getTextOfSalesTeamDetailsPage("communicationPreferencesHeader").equals(getTextLanguage(LanguageCode, "daas_ui", "user.details.system.communications").toUpperCase()), "Communication preferences tile is not present on logged in user's details page");
			softAssert.assertTrue(salesTeamDetailsPage.matchTextOfSalesTeamDetailsPage("deviceHealthNotificationTitle", getTextLanguage(LanguageCode, "daas_ui", "users.details.device_health_notify")), "Device health notification label is incorrect on logged in user's details page");
			if (salesTeamDetailsPage.getAttributesOfSalesTeamDetailsPage("deviceHealthNotificationToggle", "aria-checked").equals("true")) {
				softAssert.assertTrue(salesTeamDetailsPage.matchTextOfSalesTeamDetailsPage("deviceHealthNotificationValue", getTextLanguage(LanguageCode, "daas_ui", "global.enabled")), "Device health notification status is disabled even when toggle is 'on' on logged in user's details page");
				salesTeamDetailsPage.clickOnElementsOfSalesTeamDetailsPage("deviceHealthNotificationToggle");
				LOGGER.info("Switched device health notification toggle off");
				softAssert.assertTrue(salesTeamDetailsPage.matchTextOfSalesTeamDetailsPage("deviceHealthNotificationValue", getTextLanguage(LanguageCode, "daas_ui", "global.disabled")), "Device health notification toggle is not updated successfully on logged in user's details page");
			} else {
				softAssert.assertTrue(salesTeamDetailsPage.matchTextOfSalesTeamDetailsPage("deviceHealthNotificationValue", getTextLanguage(LanguageCode, "daas_ui", "global.disabled")), "Device health notification status is enabled even when toggle is 'off' on logged in user's details page");
				salesTeamDetailsPage.clickOnElementsOfSalesTeamDetailsPage("deviceHealthNotificationToggle");
				LOGGER.info("Switched device health notification toggle on");
				softAssert.assertTrue(salesTeamDetailsPage.matchTextOfSalesTeamDetailsPage("deviceHealthNotificationValue", getTextLanguage(LanguageCode, "daas_ui", "global.enabled")), "Device health notification toggle is not updated successfully on logged in user's details page");
			}

			softAssert.assertTrue(salesTeamDetailsPage.matchTextOfSalesTeamDetailsPage("incidentsNotificationTitle", getTextLanguage(LanguageCode, "daas_ui", "users.details.communication_preferences.incident_notification")), "Incident notification label is incorrect on logged in user's details page");
			salesTeamDetailsPage.clickOnElementsOfSalesTeamDetailsPage("incidentsNotificationEdit");
			LOGGER.info("Clicked on edit notification preferences button");
			softAssert.assertTrue(salesTeamDetailsPage.matchTextOfSalesTeamDetailsPage("incidentNotificationPreferencesTitle", getTextLanguage(LanguageCode, "daas_ui", "users.details.communication_preferences.incident_notification_modal_title")), "Title on incident notification popup is incorrect on logged in user's details page");
			softAssert.assertTrue(salesTeamDetailsPage.matchTextOfSalesTeamDetailsPage("incidentNotificationPreferencesSubTitle", getTextLanguage(LanguageCode, "daas_ui", "users.details.communication_preferences.incident_notification_modal_subtitle")), "Subtitle on incident notification popup is incorrect on logged in user's details page");

			softAssert.assertTrue(salesTeamDetailsPage.matchTextOfSalesTeamDetailsPage("accountTitle", getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.type.account").toUpperCase()), "Incident type 'Account' is not present on incident notification preferences popup");
			salesTeamDetailsPage.clickOnElementsOfSalesTeamDetailsPage("accountListDropdownArrow");
			LOGGER.info("Clicked on account incident type dropdown");
			salesTeamDetailsPage.waitForElementsOfSalesTeamDetailsPage("accountLocator");
			softAssert.assertTrue(salesTeamDetailsPage.verifyOptionsOnDropdownForSalesTeamDetailsPage("accountList", accountSubTypesOnIncidentNotificationPreferencesPopup), "Incident subtypes for incident type 'Account' are incorrect");
			salesTeamDetailsPage.selectAllIncidentTypeOfSupportTeamDetailsPage("accountAllLocator");
			LOGGER.info("Selected all incident subtypes on account incident type dropdown");
			salesTeamDetailsPage.clickOnElementsOfSalesTeamDetailsPage("accountListDropdownArrow");

			softAssert.assertTrue(salesTeamDetailsPage.matchTextOfSalesTeamDetailsPage("hardwareChangeTitle", getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.type.hwchange").toUpperCase()), "Incident type 'Hardware Change' is not present on incident notification preferences popup");
			salesTeamDetailsPage.clickOnElementsOfSalesTeamDetailsPage("hardwareChangeListDropdownArrow");
			LOGGER.info("Clicked on hardware change incident type dropdown");
			salesTeamDetailsPage.waitForElementsOfSalesTeamDetailsPage("hardwareChangeLocator");
			softAssert.assertTrue(salesTeamDetailsPage.verifyOptionsOnDropdownForSalesTeamDetailsPage("hardwareChangeList", hardwareChangeSubTypesOnIncidentNotificationPreferencesPopup), "Incident subtypes for incident type 'Hardware Change' are incorrect");
			salesTeamDetailsPage.selectAllIncidentTypeOfSupportTeamDetailsPage("hardwareChangeAllLocator");
			LOGGER.info("Selected all incident subtypes on hardware change incident type dropdown");
			salesTeamDetailsPage.clickOnElementsOfSalesTeamDetailsPage("hardwareChangeListDropdownArrow");

			softAssert.assertTrue(salesTeamDetailsPage.matchTextOfSalesTeamDetailsPage("hardwareHealthTitle", getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.type.hardwarehealth").toUpperCase()), "Incident type 'Hardware Health' is not present on incident notification preferences popup");
			salesTeamDetailsPage.clickOnElementsOfSalesTeamDetailsPage("hardwareHealthListDropdownArrow");
			LOGGER.info("Clicked on hardware health incident type dropdown");
			salesTeamDetailsPage.waitForElementsOfSalesTeamDetailsPage("hardwareHealthLocator");
			softAssert.assertTrue(salesTeamDetailsPage.verifyOptionsOnDropdownForSalesTeamDetailsPage("hardwareHealthList", hardwareHealthSubTypesOnIncidentNotificationPreferencesPopup), "Incident subtypes for incident type 'Hardware Health' are incorrect");
			salesTeamDetailsPage.selectAllIncidentTypeOfSupportTeamDetailsPage("hardwareHealthAllLocator");
			LOGGER.info("Selected all incident subtypes on hardware health incident type dropdown");
			salesTeamDetailsPage.clickOnElementsOfSalesTeamDetailsPage("hardwareHealthListDropdownArrow");

			softAssert.assertTrue(salesTeamDetailsPage.matchTextOfSalesTeamDetailsPage("osHealthTitle", getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.type.oshealth").toUpperCase()), "Incident type 'OS Health' is not present on incident notification preferences popup");
			salesTeamDetailsPage.clickOnElementsOfSalesTeamDetailsPage("osHealthListDropdownArrow");
			LOGGER.info("Clicked on os health incident type dropdown");
			salesTeamDetailsPage.waitForElementsOfSalesTeamDetailsPage("osHealthLocator");
			softAssert.assertTrue(salesTeamDetailsPage.verifyOptionsOnDropdownForSalesTeamDetailsPage("osHealthList", osHealthSubTypesOnIncidentNotificationPreferencesPopup), "Incident subtypes for incident type 'OS Health' are incorrect");
			salesTeamDetailsPage.selectAllIncidentTypeOfSupportTeamDetailsPage("osHealthAllLocator");
			LOGGER.info("Selected all incident subtypes on os health incident type dropdown");
			salesTeamDetailsPage.clickOnElementsOfSalesTeamDetailsPage("osHealthListDropdownArrow");

			softAssert.assertTrue(salesTeamDetailsPage.matchTextOfSalesTeamDetailsPage("securityTitle", getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.type.security").toUpperCase()), "Incident type 'Security' is not present on incident notification preferences popup");
			salesTeamDetailsPage.clickOnElementsOfSalesTeamDetailsPage("securityListDropdownArrow");
			LOGGER.info("Clicked on security incident type dropdown");
			salesTeamDetailsPage.waitForElementsOfSalesTeamDetailsPage("securityLocator");
			softAssert.assertTrue(salesTeamDetailsPage.verifyOptionsOnDropdownForSalesTeamDetailsPage("securityList", securitySubTypesOnIncidentNotificationPreferencesPopup), "Incident subtypes for incident type 'Security' are incorrect");
			salesTeamDetailsPage.selectAllIncidentTypeOfSupportTeamDetailsPage("securityAllLocator");
			LOGGER.info("Selected all incident subtypes on security incident type dropdown");
			salesTeamDetailsPage.clickOnElementsOfSalesTeamDetailsPage("securityListDropdownArrow");

			softAssert.assertTrue(salesTeamDetailsPage.matchTextOfSalesTeamDetailsPage("softwareHealthTitle", getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.type.softwarehealth").toUpperCase()), "Incident type 'Software Health' is not present on incident notification preferences popup");
			salesTeamDetailsPage.clickOnElementsOfSalesTeamDetailsPage("softwareHealthListDropdownArrow");
			LOGGER.info("Clicked on software health incident type dropdown");
			salesTeamDetailsPage.waitForElementsOfSalesTeamDetailsPage("softwareHealthLocator");
			softAssert.assertTrue(salesTeamDetailsPage.verifyOptionsOnDropdownForSalesTeamDetailsPage("softwareHealthList", softwareHealthSubTypesOnIncidentNotificationPreferencesPopup), "Incident subtypes for incident type 'Software Health' are incorrect");
			salesTeamDetailsPage.selectAllIncidentTypeOfSupportTeamDetailsPage("softwareHealthAllLocator");
			LOGGER.info("Selected all incident subtypes on software health incident type dropdown");
			salesTeamDetailsPage.clickOnElementsOfSalesTeamDetailsPage("softwareHealthListDropdownArrow");

			softAssert.assertTrue(salesTeamDetailsPage.matchTextOfSalesTeamDetailsPage("unassignedTitle", getTextLanguage(LanguageCode, "daas_ui", "incidents.com.hp.mpi.icm.type.unassigned").toUpperCase()), "Incident type 'Unassigned' is not present on incident notification preferences popup");
			salesTeamDetailsPage.clickOnElementsOfSalesTeamDetailsPage("unassignedListDropdownArrow");
			salesTeamDetailsPage.waitForElementsOfSalesTeamDetailsPage("unassignedLocator");
			softAssert.assertTrue(salesTeamDetailsPage.verifyOptionsOnDropdownForSalesTeamDetailsPage("unassignedList", unassignedSubTypesOnIncidentNotificationPreferencesPopup), "Incident subtypes for incident type 'Unassigned' are incorrect");
			salesTeamDetailsPage.selectAllIncidentTypeOfSupportTeamDetailsPage("unassignedAllLocator");
			salesTeamDetailsPage.clickOnElementsOfSalesTeamDetailsPage("unassignedListDropdownArrow");

			softAssert.assertTrue(salesTeamDetailsPage.matchTextOfSalesTeamDetailsPage("doNotReceiveEmailNotificationLocator", getTextLanguage(LanguageCode, "daas_ui", "users.details.communication_preferences.incident_notification.option_1")), "Do not receive email notification radio button is not present on incident preferences popup");
			softAssert.assertTrue(salesTeamDetailsPage.matchTextOfSalesTeamDetailsPage("ReceiveEmailNotificationWhenNewIncidentIsReportedLocator", getTextLanguage(LanguageCode, "daas_ui", "users.details.communication_preferences.incident_notification.option_2")), "Receive email notification when new incident is reported radio button is not present incident preferences on popup");
			softAssert.assertTrue(salesTeamDetailsPage.matchTextOfSalesTeamDetailsPage("ReceiveAnEmailNotificationWithAllIncidentsThatAreOpenedInaDayLocator", getTextLanguage(LanguageCode, "daas_ui", "users.details.communication_preferences.incident_notification.option_3")), "Receive an email notification with all incidents that are opened in a day. radio button is not present on incident preferences popup");
			softAssert.assertTrue(salesTeamDetailsPage.matchTextOfSalesTeamDetailsPage("ReceiveEmailNotificationWhenNewIncidentIsReportedAndOneEmailWithAllIncidentsThatAreOpenedInaDayLocator", getTextLanguage(LanguageCode, "daas_ui", "users.details.communication_preferences.incident_notification.option_4")), "Receive email notification when new incident is reported and one email with all incidents that are opened in a day radio button is not present on incident preferences popup");

			salesTeamDetailsPage.clickOnElementsOfSalesTeamDetailsPage("incidentPreferencesSaveButton");
			LOGGER.info("Clicked on save button of incident preferences popup");
			softAssert.assertTrue(salesTeamDetailsPage.verifyElementsOfSalesTeamDetailsPage("toastNotification"), "Toast notification message is not generated when incident preferences are updated");
			softAssert.assertTrue(salesTeamDetailsPage.getTextOfSalesTeamDetailsPage("incidentsNotificationValue").equals(selectedAllIncidentTypes), "Save functionality on incident preferences popup is not working correctly when all subtypes of all incident types are selected");

			salesTeamDetailsPage.clickOnElementsOfSalesTeamDetailsPage("incidentsNotificationEdit");
			LOGGER.info("Clicked on edit notification preferences button");

			salesTeamDetailsPage.clickOnElementsOfSalesTeamDetailsPage("accountListDropdownArrow");
			LOGGER.info("Clicked on account incident type dropdown");
			salesTeamDetailsPage.selectSingleIncidentTypeOnSupportTeamDetailsPage("accountAllLocator", "accountLocator");
			LOGGER.info("Selected single incident subtype on account incident type dropdown");
			salesTeamDetailsPage.clickOnElementsOfSalesTeamDetailsPage("accountListDropdownArrow");
			salesTeamDetailsPage.scrollOnSupportTeamPage("hardwareChangeListDropdownArrow");

			salesTeamDetailsPage.clickOnElementsOfSalesTeamDetailsPage("hardwareChangeListDropdownArrow");
			LOGGER.info("Clicked on hardware change incident type dropdown");
			salesTeamDetailsPage.selectSingleIncidentTypeOnSupportTeamDetailsPage("hardwareChangeAllLocator", "hardwareChangeLocator");
			LOGGER.info("Selected single incident subtype on hardware change incident type dropdown");
			salesTeamDetailsPage.clickOnElementsOfSalesTeamDetailsPage("hardwareChangeListDropdownArrow");
			salesTeamDetailsPage.scrollOnSupportTeamPage("hardwareHealthListDropdownArrow");

			salesTeamDetailsPage.clickOnElementsOfSalesTeamDetailsPage("hardwareHealthListDropdownArrow");
			LOGGER.info("Clicked on hardware health incident type dropdown");
			salesTeamDetailsPage.selectSingleIncidentTypeOnSupportTeamDetailsPage("hardwareHealthAllLocator", "hardwareHealthLocator");
			LOGGER.info("Selected single incident subtype on hardware health incident type dropdown");
			salesTeamDetailsPage.clickOnElementsOfSalesTeamDetailsPage("hardwareHealthListDropdownArrow");
			salesTeamDetailsPage.scrollOnSupportTeamPage("osHealthListDropdownArrow");

			salesTeamDetailsPage.clickOnElementsOfSalesTeamDetailsPage("osHealthListDropdownArrow");
			LOGGER.info("Clicked on os health incident type dropdown");
			salesTeamDetailsPage.selectSingleIncidentTypeOnSupportTeamDetailsPage("osHealthAllLocator", "osHealthLocator");
			LOGGER.info("Selected single incident subtype on os health incident type dropdown");
			salesTeamDetailsPage.clickOnElementsOfSalesTeamDetailsPage("osHealthListDropdownArrow");
			salesTeamDetailsPage.scrollOnSupportTeamPage("securityListDropdownArrow");

			salesTeamDetailsPage.clickOnElementsOfSalesTeamDetailsPage("securityListDropdownArrow");
			LOGGER.info("Clicked on security incident type dropdown");
			salesTeamDetailsPage.selectSingleIncidentTypeOnSupportTeamDetailsPage("securityAllLocator", "securityLocator");
			LOGGER.info("Selected single incident subtype on security incident type dropdown");
			salesTeamDetailsPage.clickOnElementsOfSalesTeamDetailsPage("securityListDropdownArrow");
			salesTeamDetailsPage.scrollOnSupportTeamPage("softwareHealthListDropdownArrow");

			salesTeamDetailsPage.clickOnElementsOfSalesTeamDetailsPage("softwareHealthListDropdownArrow");
			LOGGER.info("Clicked on software health incident type dropdown");
			salesTeamDetailsPage.waitForElementsOfSalesTeamDetailsPage("softwareHealthList");
			salesTeamDetailsPage.selectSingleIncidentTypeOnSupportTeamDetailsPage("softwareHealthAllLocator", "softwareHealthLocator");
			LOGGER.info("Selected single incident subtype on software health incident type dropdown");
			salesTeamDetailsPage.scrollOnSupportTeamPage("unassignedListDropdownArrow");

			salesTeamDetailsPage.clickOnElementsOfSalesTeamDetailsPage("unassignedListDropdownArrow");
			LOGGER.info("Clicked on assigned incident type dropdown");
			salesTeamDetailsPage.waitForElementsOfSalesTeamDetailsPage("unassignedList");
			salesTeamDetailsPage.selectSingleIncidentTypeOnSupportTeamDetailsPage("unassignedAllLocator", "unassignedLocator");
			LOGGER.info("Selected single incident subtype on unassigned incident type dropdown");
			salesTeamDetailsPage.clickOnElementsOfSalesTeamDetailsPage("unassignedListDropdownArrow");

			salesTeamDetailsPage.clickOnElementsOfSalesTeamDetailsPage("incidentPreferencesSaveButton");
			LOGGER.info("Clicked on save button of incident preferences popup");
			softAssert.assertTrue(salesTeamDetailsPage.verifyElementsOfSalesTeamDetailsPage("toastNotification"), "Toast notification message is not generated when incident preferences are updated");
			softAssert.assertTrue(salesTeamDetailsPage.getTextOfSalesTeamDetailsPage("incidentsNotificationValue").equals(selectedSpecificIncidentTypes), "Save functionality on incident preferences popup is not working correctly when single subtype of every incident types are selected");

			salesTeamDetailsPage.clickOnElementsOfSalesTeamDetailsPage("incidentsNotificationEdit");
			LOGGER.info("Clicked on edit notification preferences button");

			salesTeamDetailsPage.clickOnElementsOfSalesTeamDetailsPage("accountListDropdownArrow");
			salesTeamDetailsPage.deselectAllIncidentTypeOnSupportTeamDetailsPage("accountAllLocator");
			LOGGER.info("Removed all incident subtype on account incident type dropdown");
			salesTeamDetailsPage.clickOnElementsOfSalesTeamDetailsPage("accountListDropdownArrow");
			salesTeamDetailsPage.scrollOnSupportTeamPage("hardwareChangeListDropdownArrow");

			salesTeamDetailsPage.clickOnElementsOfSalesTeamDetailsPage("hardwareChangeListDropdownArrow");
			salesTeamDetailsPage.deselectAllIncidentTypeOnSupportTeamDetailsPage("hardwareChangeAllLocator");
			LOGGER.info("Removed all incident subtype on hardware change incident type dropdown");
			salesTeamDetailsPage.clickOnElementsOfSalesTeamDetailsPage("hardwareChangeListDropdownArrow");
			salesTeamDetailsPage.scrollOnSupportTeamPage("hardwareHealthListDropdownArrow");

			salesTeamDetailsPage.clickOnElementsOfSalesTeamDetailsPage("hardwareHealthListDropdownArrow");
			salesTeamDetailsPage.deselectAllIncidentTypeOnSupportTeamDetailsPage("hardwareHealthAllLocator");
			LOGGER.info("Removed all incident subtype on hardware health incident type dropdown");
			salesTeamDetailsPage.clickOnElementsOfSalesTeamDetailsPage("hardwareHealthListDropdownArrow");
			salesTeamDetailsPage.scrollOnSupportTeamPage("osHealthListDropdownArrow");

			salesTeamDetailsPage.clickOnElementsOfSalesTeamDetailsPage("osHealthListDropdownArrow");
			salesTeamDetailsPage.deselectAllIncidentTypeOnSupportTeamDetailsPage("osHealthAllLocator");
			LOGGER.info("Removed all incident subtype on os health incident type dropdown");
			salesTeamDetailsPage.clickOnElementsOfSalesTeamDetailsPage("osHealthListDropdownArrow");
			salesTeamDetailsPage.scrollOnSupportTeamPage("securityListDropdownArrow");

			salesTeamDetailsPage.clickOnElementsOfSalesTeamDetailsPage("securityListDropdownArrow");
			salesTeamDetailsPage.deselectAllIncidentTypeOnSupportTeamDetailsPage("securityAllLocator");
			LOGGER.info("Removed all incident subtype on security incident type dropdown");
			salesTeamDetailsPage.clickOnElementsOfSalesTeamDetailsPage("securityListDropdownArrow");
			salesTeamDetailsPage.scrollOnSupportTeamPage("softwareHealthListDropdownArrow");

			salesTeamDetailsPage.clickOnElementsOfSalesTeamDetailsPage("softwareHealthListDropdownArrow");
			salesTeamDetailsPage.deselectAllIncidentTypeOnSupportTeamDetailsPage("softwareHealthAllLocator");
			LOGGER.info("Removed all incident subtype on software health incident type dropdown");
			salesTeamDetailsPage.clickOnElementsOfSalesTeamDetailsPage("softwareHealthListDropdownArrow");
			salesTeamDetailsPage.scrollOnSupportTeamPage("unassignedListDropdownArrow");

			salesTeamDetailsPage.clickOnElementsOfSalesTeamDetailsPage("unassignedListDropdownArrow");
			salesTeamDetailsPage.deselectAllIncidentTypeOnSupportTeamDetailsPage("unassignedAllLocator");
			LOGGER.info("Removed all incident subtype on unassigned incident type dropdown");
			salesTeamDetailsPage.clickOnElementsOfSalesTeamDetailsPage("unassignedListDropdownArrow");

			if (!salesTeamDetailsPage.verifyElementIsSelectedOfSalesTeamDetailsPage("ReceiveEmailNotificationWhenNewIncidentIsReportedRadioButton"))
				salesTeamDetailsPage.clickOnElementsOfSalesTeamDetailsPage("ReceiveEmailNotificationWhenNewIncidentIsReportedRadioButton");
			salesTeamDetailsPage.clickOnElementsOfSalesTeamDetailsPage("incidentPreferencesSaveButton");
			LOGGER.info("Clicked on save button of incident preferences popup");

			softAssert.assertTrue(salesTeamDetailsPage.matchTextOfSalesTeamDetailsPage("validationErrorMessageOnIncidentPreferences", getTextLanguage(LanguageCode, "daas_ui", "users.details.communication_preferences.subtype_selection_error")), "");
			salesTeamDetailsPage.waitForElementsOfSalesTeamDetailsPage("doNotReceiveEmailNotificationRadioButton");
			salesTeamDetailsPage.clickOnElementsOfSalesTeamDetailsPage("doNotReceiveEmailNotificationRadioButton");
			salesTeamDetailsPage.clickOnElementsOfSalesTeamDetailsPage("incidentPreferencesSaveButton");
			LOGGER.info("Clicked on save button of incident preferences popup");
			softAssert.assertTrue(salesTeamDetailsPage.verifyElementsOfSalesTeamDetailsPage("toastNotification"), "Toast notification message is not generated when incident preferences are updated");
			softAssert.assertTrue(salesTeamDetailsPage.getTextOfSalesTeamDetailsPage("incidentsNotificationValue").equals("-"), "Save functionality on incident preferences popup is not working correctly when no incident subtypes are selected");

			salesTeamDetailsPage.clickOnElementsOfSalesTeamDetailsPage("incidentsNotificationEdit");
			LOGGER.info("Clicked on edit notification preferences button");
			salesTeamDetailsPage.clickOnElementsOfSalesTeamDetailsPage("accountListDropdownArrow");
			salesTeamDetailsPage.selectSingleIncidentTypeOnSupportTeamDetailsPage("accountAllLocator", "accountLocator");
			salesTeamDetailsPage.clickOnElementsOfSalesTeamDetailsPage("accountListDropdownArrow");
			salesTeamDetailsPage.scrollOnSupportTeamPage("hardwareChangeListDropdownArrow");

			salesTeamDetailsPage.clickOnElementsOfSalesTeamDetailsPage("hardwareChangeListDropdownArrow");
			salesTeamDetailsPage.selectSingleIncidentTypeOnSupportTeamDetailsPage("hardwareChangeAllLocator", "hardwareChangeLocator");
			salesTeamDetailsPage.clickOnElementsOfSalesTeamDetailsPage("hardwareChangeListDropdownArrow");

			salesTeamDetailsPage.clickOnElementsOfSalesTeamDetailsPage("incidentPreferencesCancelButton");
			LOGGER.info("Clicked on cancel button of incident preferences popup");
			softAssert.assertTrue(salesTeamDetailsPage.getTextOfSalesTeamDetailsPage("incidentsNotificationValue").equals("-"), "Cancel functionality on incident preferences popup is not working correctly");

			softAssert.assertTrue(salesTeamDetailsPage.matchTextOfSalesTeamDetailsPage("dailyEmailNotificationTitle", getTextLanguage(LanguageCode, "daas_ui", "users.details.communication_preferences.receive_daily_emails_at")), "Daily emails notification label is incorrect on logged in user's details page");
			salesTeamDetailsPage.clickByJavaScriptOnSupportTeamDetailsPage("dailyEmailNotificationEditButton");
			LOGGER.info("Clicked on edit daily email notification button");
			String timeZoneBefore = salesTeamDetailsPage.getTextOfSalesTeamDetailsPage("timezoneDropdown");
			String timeZoneCodeBefore = salesTeamDetailsPage.getTextOfSalesTeamDetailsPage("timezoneCodeDropdown");
			timeZone = salesTeamDetailsPage.selectRoleFromDropDownOfSalesTeamDetailsPage("timezoneDropdown", "timezoneDropdownList", timeZoneBefore);
			LOGGER.info("Updated time zone on daily email notification dropdown");
			timeZoneCode = salesTeamDetailsPage.selectRoleFromDropDownOfSalesTeamDetailsPage("timezoneCodeDropdown", "timezoneCodeDropdownList", timeZoneCodeBefore);
			LOGGER.info("Updated time zone code on daily email notification dropdown");
			salesTeamDetailsPage.clickOnElementsOfSalesTeamDetailsPage("dailyEmailNotificationCancelButton");
			LOGGER.info("Clicked on cancel button of daily email notification popup");
			softAssert.assertFalse(salesTeamDetailsPage.getTextOfSalesTeamDetailsPage("currentTimeZone").contains(timeZone), "Cancel functionality for timezone on daily email notification popup is not working correctly");
			softAssert.assertFalse(salesTeamDetailsPage.getTextOfSalesTeamDetailsPage("currentTimeZone").contains(timeZoneCode), "Cancel functionality for timezone code on daily email notification popup is not working correctly");

			salesTeamDetailsPage.clickOnElementsOfSalesTeamDetailsPage("dailyEmailNotificationEditButton");
			LOGGER.info("Clicked on edit daily email notification button");
			timeZone = salesTeamDetailsPage.selectRoleFromDropDownOfSalesTeamDetailsPage("timezoneDropdown", "timezoneDropdownList", timeZoneBefore);
			LOGGER.info("Updated time zone on daily email notification dropdown");
			salesTeamDetailsPage.selectRoleFromDropDownOfSalesTeamDetailsPage("timezoneCodeDropdown", "timezoneCodeDropdownList", timeZoneCodeBefore);
			LOGGER.info("Updated time zone code on daily email notification dropdown");
			salesTeamDetailsPage.clickOnElementsOfSalesTeamDetailsPage("dailyEmailNotificationSaveButton");
			LOGGER.info("Clicked on save button of daily email notification popup");
			softAssert.assertTrue(salesTeamDetailsPage.verifyElementsOfSalesTeamDetailsPage("toastNotification"), "Toast notification is not generated sfter daily email notification time is updated on popup");
			softAssert.assertTrue(salesTeamDetailsPage.matchTextOfSalesTeamDetailsPage("dailyEmailNotificationValue", timeZone), "Save functionality on daily email notification popup is not working correctly");

			softAssert.assertAll();
			LOGGER.info("Verification of communication preferences tile on logged in user's details page done successfully");
		} catch (Exception e) {
			Assert.fail("Exception occured in method verifyCommunicationPreferencesTile " + e.getMessage());
		}
	}

	@Test(priority = 10, groups = { "REGRESSION", "REGRESSION_CI", "SMOKE", "ALL_CI", "ALL" }, description = "NFR 242589", enabled = false)
	public final void verifyEditloggedInUserDetails() throws Exception {
		login("RESELLER_NEW_UI_Companies_EMAIL_COMPANIES", "RESELLER_NEW_UI_Companies_PASSWORD_COMPANIES");
		gotoPartnerTeamTab();
		SalesTeamPage salesTeamPage = new SalesTeamPage(PreDefinedActions.getDriver()).getInstance();

		SoftAssert softAssert = new SoftAssert();
		String firstName = null, lastName = null, mobileNumber = null, officeNumber = null, title = null, address1 = null, address2 = null, state = null, city = null, zipcode = null;
		waitForPageLoaded();
		resetTableConfiguration();
		salesTeamPage.waitForElementsOfSalesTeamPage("tableOverlay");
		salesTeamPage.enterTextForSalesTeamPage("emailTextbox", getCredentials(environment, "RESELLER_NEW_UI_Companies_EMAIL_COMPANIES"));
		salesTeamPage.waitForElementsOfSalesTeamPage("tableOverlay");
		if (salesTeamPage.verifyElementsOfSalesTeamPage("nameList")) {
			List<WebElement> salesTeamList = salesTeamPage.getElementsOfSalesTeamPage("nameList");
			salesTeamPage.clickElementsOfSalesTeamPage(salesTeamList);
			salesTeamPage.waitForElementsOfSalesTeamPage("tableOverlay");

			softAssert.assertTrue(salesTeamPage.verifyElementsOfSalesTeamPage("breadCrumbLink"), "BreadCrumb link is not present");
			;
			softAssert.assertTrue(salesTeamPage.matchTextOfSalesTeamPage("detailsNamePanel", salesTeamPage.getTextOfSalesTeamPage("breadCrumbName")), "Name does not match");
			softAssert.assertTrue(salesTeamPage.matchTextOfSalesTeamPage("detailsEmailPanel", getCredentials(environment, "RESELLER_NEW_UI_Companies_EMAIL_COMPANIES")), "Email does not match");
			softAssert.assertTrue(salesTeamPage.matchTextOfSalesTeamPage("namePanel", getTextLanguage(LanguageCode, "daas_ui", "support_team.details.name")), "Name label does not match");
			softAssert.assertTrue(salesTeamPage.matchTextOfSalesTeamPage("emailPanel", getTextLanguage(LanguageCode, "daas_ui", "support_team.details.email")), "Email label does not match");
			softAssert.assertTrue(salesTeamPage.matchTextOfSalesTeamPage("mobilePanel", getTextLanguage(LanguageCode, "daas_ui", "support_team.details.phone_mobile")), "Mobile Phone label does not match");
			softAssert.assertTrue(salesTeamPage.matchTextOfSalesTeamPage("officePanel", getTextLanguage(LanguageCode, "daas_ui", "support_team.details.phone_office")), "Office Phone label does not match");
			softAssert.assertTrue(salesTeamPage.verifyElementsOfSalesTeamPage("jobTitlePanel"), "Title label is not present");
			softAssert.assertTrue(salesTeamPage.matchTextOfSalesTeamPage("addressPanel", getTextLanguage(LanguageCode, "daas_ui", "global.address")), "Address label does not match");
			softAssert.assertTrue(salesTeamPage.verifyElementsOfSalesTeamPage("languagePanel"), "Language label is not present");
			softAssert.assertTrue(salesTeamPage.matchTextOfSalesTeamPage("passwordPanel", getTextLanguage(LanguageCode, "daas_ui", "global.password")), "Password label does not match");

			salesTeamPage.clickOnElementsOfSalesTeamPage("nameEditButton");
			firstName = salesTeamPage.generateRandomString(11);
			salesTeamPage.enterTextForSalesTeamPage("firstNameTextBox", firstName);
			lastName = salesTeamPage.generateRandomString(11);
			salesTeamPage.enterTextForSalesTeamPage("lastNameTextBox", lastName);
			salesTeamPage.clickOnElementsOfSalesTeamPage("saveButton");
			salesTeamPage.waitForElementsOfSalesTeamPage("toastNotification");
			softAssert.assertTrue(salesTeamPage.matchTextOfSalesTeamPage("detailsNamePanel", firstName + " " + lastName), "Name does not match after change");

			salesTeamPage.clickOnElementsOfSalesTeamPage("mobileEditButton");
			mobileNumber = salesTeamPage.generateRandomNumber();
			salesTeamPage.enterTextForSalesTeamPage("mobileTextBox", mobileNumber);
			salesTeamPage.clickOnElementsOfSalesTeamPage("saveButton");
			salesTeamPage.waitForElementsOfSalesTeamPage("tableOverlay");
			softAssert.assertTrue(salesTeamPage.matchTextOfSalesTeamPage("detailsMobilePanel", mobileNumber), "mobile Number does not match after change");

			salesTeamPage.clickOnElementsOfSalesTeamPage("officeEditButton");
			officeNumber = salesTeamPage.generateRandomNumber();
			salesTeamPage.enterTextForSalesTeamPage("officeTextBox", officeNumber);
			salesTeamPage.clickOnElementsOfSalesTeamPage("saveButton");
			salesTeamPage.waitForElementsOfSalesTeamPage("tableOverlay");
			softAssert.assertTrue(salesTeamPage.matchTextOfSalesTeamPage("detailsOfficePanel", officeNumber), "Office Number does not match after change");

			salesTeamPage.clickOnElementsOfSalesTeamPage("titleEditButton");
			title = salesTeamPage.generateRandomString(11);
			salesTeamPage.enterTextForSalesTeamPage("titleTextBox", title);
			salesTeamPage.clickOnElementsOfSalesTeamPage("saveButton");
			salesTeamPage.waitForElementsOfSalesTeamPage("tableOverlay");
			softAssert.assertTrue(salesTeamPage.matchTextOfSalesTeamPage("detailsTitlePanel", title), "Title does not match after change");

			salesTeamPage.clickOnElementsOfSalesTeamPage("addressEditButton");
			address1 = salesTeamPage.generateRandomString(11);
			address2 = salesTeamPage.generateRandomString(11);
			state = salesTeamPage.generateRandomString(11);
			city = salesTeamPage.generateRandomString(11);
			zipcode = salesTeamPage.generateRandomNumber();
			salesTeamPage.enterTextForSalesTeamPage("address1Textbox", address1);
			salesTeamPage.enterTextForSalesTeamPage("address2Textbox", address2);
			salesTeamPage.selectElementFromDropDownOfSalesTeamPage("dropDownBox", "dropDownList", SalesVariables.EDITPROFILE_COUNTRY);
			salesTeamPage.enterTextForSalesTeamPage("stateTextbox", state);
			salesTeamPage.enterTextForSalesTeamPage("cityTextbox", city);
			salesTeamPage.enterTextForSalesTeamPage("zipcodeTextbox", zipcode);
			salesTeamPage.clickOnElementsOfSalesTeamPage("saveButton");

			salesTeamPage.waitForElementsOfSalesTeamPage("toastNotification");
			softAssert.assertTrue(salesTeamPage.matchTextOfSalesTeamPage("detailsCountry", SalesVariables.EDITPROFILE_COUNTRY), "Country does not match after change");
			softAssert.assertTrue(salesTeamPage.matchTextOfSalesTeamPage("detailsAddress1", address1), "Address 1 does not match after change");
			softAssert.assertTrue(salesTeamPage.matchTextOfSalesTeamPage("detailsAddress2", address2), "Address 2 does not match after change");
			softAssert.assertTrue(salesTeamPage.matchTextOfSalesTeamPage("detailsStateCityZipcode", city + ", " + state + " " + zipcode), "City or state or zipcode does not match after change");

			salesTeamPage.waitForElementsOfSalesTeamPage("tableOverlay");
			salesTeamPage.clickOnElementsOfSalesTeamPage("languageEditButton");
			salesTeamPage.selectElementFromDropDownOfSalesTeamPage("dropDownBox", "dropDownList", SalesVariables.EDITPROFILE_LANGUAGE_CHINESE);
			salesTeamPage.clickOnElementsOfSalesTeamPage("saveButton");
			salesTeamPage.waitForElementsOfSalesTeamPage("toastNotification");
			softAssert.assertTrue(salesTeamPage.matchTextOfSalesTeamPage("detailsLanguagePanel", "日本語"), "Language Text does not match");
			salesTeamPage.clickOnElementsOfSalesTeamPage("languageEditButton");
			salesTeamPage.selectElementFromDropDownOfSalesTeamPage("dropDownBox", "dropDownList", SalesVariables.EDITPROFILE_LANGUAGE_ENGLISH);
			salesTeamPage.clickOnElementsOfSalesTeamPage("saveButton");
			salesTeamPage.waitForElementsOfSalesTeamPage("toastNotification");
			salesTeamPage.verifyElementsOfSalesTeamPage("detailsPassword");
			softAssert.assertTrue(salesTeamPage.matchTextOfSalesTeamPage("detailsPassword", getTextLanguage(LanguageCode, "daas_ui", "users.details.password.edit")), "Password label does not match");
			softAssert.assertTrue(salesTeamPage.matchTextOfSalesTeamPage("hpAccountText", getTextLanguage(LanguageCode, "daas_ui", "users.details.account_type.hpid")), "HP Account text does not match");
			softAssert.assertTrue(salesTeamPage.matchTextOfSalesTeamPage("accountStatus", getTextLanguage(LanguageCode, "daas_ui", "global.active")), "Account status does not match");

			softAssert.assertTrue(salesTeamPage.getTextOfSalesTeamPage("detailsNamePanel").toUpperCase().equals(salesTeamPage.getTextOfSalesTeamPage("avtarName")), "Name does not match with avtar name");
			softAssert.assertTrue(salesTeamPage.matchTextOfSalesTeamPage("avtarEmail", getCredentials(environment, "RESELLER_NEW_UI_Companies_EMAIL_COMPANIES")), "Email does not match");
			softAssert.assertTrue(salesTeamPage.getTextOfSalesTeamPage("avtarPhone").equals(salesTeamPage.getTextOfSalesTeamPage("detailsOfficePanel")), "Mobile does not match with avtar Mobile");

			softAssert.assertTrue(salesTeamPage.getTextOfSalesTeamPage("roleAssignmentHeader").equalsIgnoreCase(getTextLanguage(LanguageCode, "daas_ui", "support_team.details.role_assignment.title").toUpperCase()), "Role Assignment Header does not match");
			softAssert.assertTrue(salesTeamPage.matchTextOfSalesTeamPage("roleAssignmentDescription", getTextLanguage(LanguageCode, "daas_ui", "users.details.role_assignment_description")), "Role Assignment description does not match");
			softAssert.assertTrue(salesTeamPage.matchTextOfSalesTeamPage("rolesLabel", getTextLanguage(LanguageCode, "daas_ui", "users.details.roles")), "Roles Label does not match");
			softAssert.assertTrue(salesTeamPage.matchTextOfSalesTeamPage("role", getTextLanguage(LanguageCode, "daas_ui", "roles.sales_admin")), "Role does not match");
			// Test case to verify upload image
			if (salesTeamPage.verifyElementsOfSalesTeamPage("avtarImagePhoto")) {
				salesTeamPage.clickOnElementsOfSalesTeamPage("avtarImagePhoto");
				salesTeamPage.clickOnElementsOfSalesTeamPage("noPhoto");
				salesTeamPage.clickOnElementsOfSalesTeamPage("avtarSaveButton");
				salesTeamPage.waitForElementsOfSalesTeamPage("tableOverlay");
			}
			salesTeamPage.clickOnElementsOfSalesTeamPage("avtarImageNoPhoto");
			salesTeamPage.clickOnElementsOfSalesTeamPage("photoRadio");
			salesTeamPage.setAttributeOfSalesTeam("fileImport");
			salesTeamPage.enterTextForSalesTeamPage("fileImport", ConstantPath.IMPORT_PATH + SalesVariables.AVTAR_FILENAME_INVALID);
			softAssert.assertTrue(salesTeamPage.matchTextOfSalesTeamPage("fileBrowse", getTextLanguage(LanguageCode, "daas_ui", "users.details.avatar.modal.file.placeholder")), "Invalid upload file test failed");
			salesTeamPage.clickOnElementsOfSalesTeamPage("photoRadio");
			salesTeamPage.enterTextForSalesTeamPage("fileImport", ConstantPath.IMPORT_PATH + SalesVariables.AVTAR_FILENAME);
			sleeper(2000);
			salesTeamPage.clickOnElementsOfSalesTeamPage("avtarSaveButton");
			LOGGER.info("Clicked save button");
			salesTeamPage.waitForElementsOfSalesTeamPage("tableOverlay");

			// Test case to verify no photo upload image
			salesTeamPage.clickOnElementsOfSalesTeamPage("avtarImagePhoto");
			salesTeamPage.clickOnElementsOfSalesTeamPage("noPhoto");
			salesTeamPage.clickOnElementsOfSalesTeamPage("avtarSaveButton");
			salesTeamPage.waitForElementsOfSalesTeamPage("tableOverlay");

			softAssert.assertTrue(salesTeamPage.verifyElementsOfSalesTeamPage("toastNotification"), "Toast notification not displayed");
		} else {
			Assert.fail("Search for logged in user was unsuccessfull");
		}
		softAssert.assertAll();

		LOGGER.info("Method verifySalesTeamEditProfile executed successfully");
	}

	/**
	 * This method will verify the table configuration test cases of sales team list page
	 */
	@Test(priority = 11, description = "NFR 242588", groups = { "REGRESSION", "SMOKE", "REGRESSION_CI", "SMOKE_CI" })
	public final void verifyTableConfigurationTestCases() throws Exception {
		login("RESELLER_HELP_AND_SUPPORT_EMAIL", "RESELLER_HELP_AND_SUPPORT_EMAIL_PASSWORD");
		String tenantID = getValueFromToken("tenant");
		gotoPartnerTeamTab();
		waitForPageLoaded();
		// verifying search service api
		Assert.assertTrue(getStatusCode(getSearchServiceApi(environment) + CommonVariables.DETAILSSEARCHSERVICEAPI + tenantID + CommonVariables.DETAILSSEARCHSERVICEAPI2, SalesVariables.SEARCHSERVICEBODY, "POST", getEnvironment(environment)) == CommonVariables.CODEOK, "Search service failed for Sales Team list page");
		ArrayList<String> id = new ArrayList<String>(Arrays.asList(getTextLanguage(LanguageCode, "daas_ui", "support_team.details.email")));
		ArrayList<String> columnName = new ArrayList<String>(
				Arrays.asList(getTextLanguage(LanguageCode, "daas_ui", "support_team.details.name"), getTextLanguage(LanguageCode, "daas_ui", "support_team.details.email"), getTextLanguage(LanguageCode, "daas_ui", "support_team.details.role"), getTextLanguage(LanguageCode, "daas_ui", "global.form.status"), getTextLanguage(LanguageCode, "daas_ui", "support_team.details.phone_mobile"), getTextLanguage(LanguageCode, "daas_ui", "support_team.details.phone_office")));
		ArrayList<String> checkboxValue = new ArrayList<String>(Arrays.asList("true", "true", "true", "true", "false", "false"));
		verifyTableConfigurationTests(columnName, checkboxValue, id);
	}

	@Test(priority = 12, groups = { "REGRESSION", "SMOKE", "REGRESSION_CI", "SMOKE_CI", "PRODUCTION_CI", "PRODUCTION_SANITY" }, description = "Test Case ID : 280839")
	public final void verifyPartnerTeamListPage() throws Exception {
		login("RESELLER_NEW_UI_EMAIL_US", "RESELLER_NEW_UI_PASSWORD_US");
		SoftAssert softAssert = new SoftAssert();
		SalesTeamPage salesTeamPage = new SalesTeamPage(PreDefinedActions.getDriver()).getInstance();
		gotoPartnerTeamTab();
		LOGGER.info("Redirected to partner team list page");
		waitForPageLoaded();
		salesTeamPage.clearFiltersOfSalesListPage("clearfilter");
		softAssert.assertTrue(salesTeamPage.verifyElementsOfSalesTeamPage("listTable"), "Table on partner team list page is not loaded successfully");
		softAssert.assertTrue(salesTeamPage.verifyElementsOfSalesTeamPage("nameHeader"), "Default column is not present on partner team list page");
		softAssert.assertAll();
		LOGGER.info("Partner team list page is loaded successfully");
	}

	@Test(priority = 13, groups = { "REGRESSION", "SMOKE", "REGRESSION_CI", "SMOKE_CI", "PRODUCTION_CI", "PRODUCTION_SANITY" }, description = "Test Case ID : 280838")
	public final void verifyPartnerTeamDetailsPage() throws Exception {
		login("RESELLER_NEW_UI_EMAIL_US", "RESELLER_NEW_UI_PASSWORD_US");
		SoftAssert softAssert = new SoftAssert();
		SalesTeamPage salesTeamPage = new SalesTeamPage(PreDefinedActions.getDriver()).getInstance();
		SalesTeamDetailsPage salesTeamDetailsPage = new SalesTeamDetailsPage(PreDefinedActions.getDriver()).getInstance();
		gotoPartnerTeamTab();
		LOGGER.info("Redirected to partner team list page");
		waitForPageLoaded();
		salesTeamPage.clearFiltersOfSalesListPage("clearfilter");
		salesTeamDetailsPage.waitForElementsOfSalesTeamDetailsPage("tableOverlay");
		salesTeamPage.clickOnElementsOfSalesTeamPage("firstIdName");
		salesTeamDetailsPage.waitForElementsOfSalesTeamDetailsPage("tableOverlay");
		LOGGER.info("Redirected to partner team details page");
		softAssert.assertTrue(salesTeamDetailsPage.verifyElementsOfSalesTeamDetailsPage("detailsTileHeader"), "Details tile on partner team details page is not loaded successfully");
		salesTeamDetailsPage.scrollOnSupportTeamPage("roleHeader");
		if (toggleVerification(CommonVariables.ONE_EMAIL_MULTIPLE_ACCOUNTS_TOGGLE, getCredentials(environment, "RESELLER_NEW_UI_EMAIL_US")))
			softAssert.assertTrue(salesTeamDetailsPage.verifyElementsOfSalesTeamDetailsPage("roleHeader"), "Account tile on partner team details page is not loaded successfully");
		else {
			softAssert.assertTrue(salesTeamDetailsPage.verifyElementsOfSalesTeamDetailsPage("roleHeader"), "Role assignment tile on partner team details page is not loaded successfully");
		}
		softAssert.assertAll();
		LOGGER.info("Partner team details page is loaded successfully");
	}
}
